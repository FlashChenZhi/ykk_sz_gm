//#CM566534
//$Id: StorageWorkingInformationHandler.java,v 1.2 2006/12/07 08:56:13 suresh Exp $
package jp.co.daifuku.wms.storage.dbhandler ;

//#CM566535
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM566536
/**
 * The class to operate work information (WorkingInformation). <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/20</TD><TD>T.Yamashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:56:13 $
 * @author  $Author: suresh $
 */
public class StorageWorkingInformationHandler extends WorkingInformationHandler
{
	//#CM566537
	// Class feilds ------------------------------------------------

	//#CM566538
	// Class variables -----------------------------------------------

	//#CM566539
	// Class method --------------------------------------------------
	//#CM566540
	/**
	 * Return this class Version. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:56:13 $");
	}

	//#CM566541
	// Constructors --------------------------------------------------
	//#CM566542
	/**
	 * Generate the instance specifying <code>Connection</code> for the database connection. 
	 * @param conn Connection Connection to database
	 */
	public StorageWorkingInformationHandler(Connection conn)
	{
		super(conn);
	}

	//#CM566543
	// Public methods ------------------------------------------------
	//#CM566544
	/**
	 * Join the DMSTOCK table and the DNWORKINFO table, and lock the corresponding record. 
	 * @param startParams Parameter[] Parameter for retrieval
	 * @return Execution result (true : Normal false : Abnormal) 
	 * @throws ReadWriteException Notify the exception generated by the connection with the data base as it is. 
	 */
	public boolean lockPlanData(Parameter[] startParams ) throws ReadWriteException
	{
		Statement stmt = null;
		ResultSet rset = null;
		Object[] fmtObj = new Object[2];
		String[] ukey = null;
		Vector vec = new Vector();
		String wPlanUkey = "";

		try
		{
			stmt = getConnection().createStatement();

			fmtObj[0] = setlockPlanData(startParams);

			String fmtSQL = "SELECT DW.PLAN_UKEY PLAN_UKEY FROM DNWORKINFO DW"
			+ " WHERE JOB_TYPE = '" + WorkingInformation.JOB_TYPE_STORAGE + "'"
			+ " AND {0} AND DW.STATUS_FLAG !='" + WorkingInformation.STATUS_FLAG_DELETE + "' FOR UPDATE ";

			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
			rset = stmt.executeQuery(sqlstring);
			if (rset == null || !rset.next())
			{
				return true;
			}
			while (rset.next())
			{
				wPlanUkey	= rset.getString("PLAN_UKEY");
				vec.addElement (wPlanUkey);
			}
			ukey = new String[vec.size()];
			vec.copyInto(ukey);

			WorkingInformationSearchKey searchkey = new WorkingInformationSearchKey();
			searchkey.setPlanUkey(ukey);
			searchkey.setJobNoCollect();
			if (!lock(searchkey))
			{
				return false;
	
			}
		}
		catch (SQLException e)
		{
			//#CM566545
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			//#CM566546
			// Here, throw ReadWriteException with the error message. 
			throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch (SQLException e)
			{
				//#CM566547
				// 6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
				//#CM566548
				// Here, throw ReadWriteException with the error message. 
				throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));
			}
		}
		return true;
	}

	//#CM566549
	/**
	 * The DNWORKINFO table and the DNHOSTSEND table are united, and it has transmitted in the DNHOSTSEND table, and transmitted and renew the unwork report flag of 
	 * the record in the DNWORKINFO table corresponding to the renewed record. 
	 * @param planUkey String Plan Unique key
	 * @param workDate String Work date
	 * @param pName String Last update processing name
	 * @return Execution result (true : Normal false : Abnormal) 
	 * @throws ReadWriteException Notify the exception generated by the connection with the data base as it is. 
	 * @throws NotFoundException It is notified when information which should be changed is not found. 
	 */
	public boolean updateReportFlag( String planUkey, String workDate, String pName )
															throws ReadWriteException, NotFoundException
	{
		Statement stmt = null;
		ResultSet rset = null;
		Object[] fmtObj = new Object[4];
		int count = 0;

		try
		{
			String countSQL = "SELECT COUNT(PLAN_UKEY) COUNT FROM DNHOSTSEND WHERE PLAN_UKEY = {0} AND WORK_DATE = {1}"
			+ " AND REPORT_FLAG = '" + SystemDefine.REPORT_FLAG_SENT + "'";

			String fmtSQL = "UPDATE DNWORKINFO SET REPORT_FLAG = '" + SystemDefine.REPORT_FLAG_SENT + "',"
			+ " LAST_UPDATE_DATE = {2}, LAST_UPDATE_PNAME = {3}"
			+ " WHERE JOB_NO IN ( SELECT JOB_NO FROM DNHOSTSEND WHERE PLAN_UKEY = {0} AND WORK_DATE = {1}"
			+ " AND REPORT_FLAG = '" + SystemDefine.REPORT_FLAG_SENT + "' )";

			fmtObj[0] = DBFormat.format(planUkey);
			fmtObj[1] = DBFormat.format(workDate);
			fmtObj[2] = DBFormat.format(new Date());
			fmtObj[3] = DBFormat.format(pName);
			String countsqlstring = SimpleFormat.format(countSQL, fmtObj);
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);

			stmt = getConnection().createStatement();
			rset = stmt.executeQuery(countsqlstring);
			if (rset == null)
			{
				return false;
			}
			while (rset.next())
			{
				count = rset.getInt("COUNT");
			}
			if( count > 0 )
			{
				count = stmt.executeUpdate(sqlstring);
			}
		}
		catch (SQLException e)
		{
			//#CM566550
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			//#CM566551
			// Here, throw ReadWriteException with the error message. 
			throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch (SQLException e)
			{
				//#CM566552
				// 6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
				//#CM566553
				// Here, throw ReadWriteException with the error message. 
				throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));
			}
		}
		return true;
	}

	//#CM566554
	// Package methods -----------------------------------------------

	//#CM566555
	// Protected methods ---------------------------------------------

	//#CM566556
	// Private methods -----------------------------------------------
	//#CM566557
	/**
	 * Generate SQL sentence based on the acquired search condition. 
	 * @param startParams Parameter[] Parameter for retrieval
	 * @return Generated SQL sentence
	 */
	private String setlockPlanData(Parameter[] startParams )
	{
		//#CM566558
		// Input information on parameter
		StorageSupportParameter[] wParam = ( StorageSupportParameter[] )startParams;

		String conditionSQL = "( ";
		for (int i = 0;i < wParam.length; i++)
		{
			if (i == 0 )
				conditionSQL += " (";
			else
				conditionSQL += "OR (";
			
			//#CM566559
			// Consignor Code
			conditionSQL += "DW.CONSIGNOR_CODE = '" + wParam[i].getConsignorCode() + "'";
			//#CM566560
			// Storage Plan date
			conditionSQL += " AND " + "DW.PLAN_DATE = '" + wParam[i].getStoragePlanDate() + "'";
			//#CM566561
			// Item Code
			conditionSQL += " AND " + "DW.ITEM_CODE = '" + wParam[i].getItemCode() + "'";
			//#CM566562
			// Case/Piece flag
			conditionSQL += " AND " + "DW.WORK_FORM_FLAG = '" + wParam[i].getCasePieceflg() + "'";
			//#CM566563
			// Storage Shelf No.
			if (!wParam[i].getStorageLocation().equals(""))
			{
				conditionSQL += " AND " + "DW.LOCATION_NO = '" + wParam[i].getStorageLocation() + "'";
			}
			else
			{
				conditionSQL += " AND " + "DW.LOCATION_NO IS NULL ";
			}
			conditionSQL += ") ";
		}
		conditionSQL += " )";

		return conditionSQL;
	}

}
//#CM566564
//end of class
