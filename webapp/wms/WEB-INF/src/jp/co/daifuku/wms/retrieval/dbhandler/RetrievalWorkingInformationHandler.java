//#CM709194
//$Id: RetrievalWorkingInformationHandler.java,v 1.3 2007/02/07 04:16:09 suresh Exp $
package jp.co.daifuku.wms.retrieval.dbhandler;

//#CM709195
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
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM709196
/**
 * Allow this class to operate the Work Status (DnWorkInfo).
 * Describe the SQL statement not supported by WorkingInformationHandler.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/20</TD><TD>T.Yamashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:16:09 $
 * @author  $Author: suresh $
 */
public class RetrievalWorkingInformationHandler extends WorkingInformationHandler
{
	//#CM709197
	// Class feilds ------------------------------------------------

	//#CM709198
	// Class variables -----------------------------------------------

	//#CM709199
	// Class method --------------------------------------------------
	//#CM709200
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:16:09 $");
	}

	//#CM709201
	// Constructors --------------------------------------------------
	//#CM709202
	/**
	 * Designate <code>Connection</code> for database connection and generate the instance.
	 * @param conn Connection for database connection 
	 */
	public RetrievalWorkingInformationHandler(Connection conn)
	{
		super(conn);
	}

	//#CM709203
	// Public methods ------------------------------------------------
	//#CM709204
	/**
	 * Combine the DMSTOCK table and the DNWORKINFO table, and lock the corresponding record.
	 * Ensure to commit or rollback the transaction for applications that use this method.
	 * Use Consignor Code, Planned Picking Date, Item Code, Customer Code, and Order No. as keys.
	 * Use setlockPlanData method to construct the SQL condition.
	 * Disable to use "ORDER BY" clause even if designated by SearchKey.
	 * @param startParams Parameter for search
	 * @return true: Normal,  false: Error
	 * @throws ReadWriteException Announce exception occurred in the connection with database as it is.
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
			+ " WHERE JOB_TYPE = '" + WorkingInformation.JOB_TYPE_RETRIEVAL + "'"
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
			//#CM709205
			// 6006002=Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			//#CM709206
			// Throw here the ReadWriteException with error message.
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
				//#CM709207
				// 6006002=Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
				//#CM709208
				// Throw here the ReadWriteException with error message.
				throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));
			}
		}
		return true;
	}

	//#CM709209
	/**
	 * Concatenate the DNWORKINFO table and the DNHOSTSEND table, and 
	 * update the Report flag of the "Not Worked" record to "Sent" in the DNWORKINFO table corresponding to the record updated to "Sent" in the DNHOSTSEND table.
	 * Ensure to commit or rollback the transaction for applications that use this method.
	 * Use Plan unique key and Work date as keys.
	 * @param planUkey Plan unique key
	 * @param workDate Work date
	 * @param pName Last update process name
	 * @return true: Normal,  false: Error
	 * @throws ReadWriteException Announce exception occurred in the connection with database as it is.
	 * @throws NotFoundException Announce when info to be changed is not found.
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
			//#CM709210
			// 6006002=Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			//#CM709211
			// Throw here the ReadWriteException with error message.
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
				//#CM709212
				// 6006002=Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
				//#CM709213
				// Throw here the ReadWriteException with error message.
				throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));
			}
		}
		return true;
	}

	//#CM709214
	// Package methods -----------------------------------------------

	//#CM709215
	// Protected methods ---------------------------------------------

	//#CM709216
	// Private methods -----------------------------------------------
	//#CM709217
	/**
	 * Based on the obtained content of WoringInformation instance, generate a string array for SQL.
	 * @param  wi WorkingInformation instance to be compiled
	 * @return Expression for searching to execute SQL
	 */
	private String setlockPlanData(Parameter[] startParams )
	{
		//#CM709218
		// Input information of the parameter
		RetrievalSupportParameter[] wParam = ( RetrievalSupportParameter[] )startParams;

		String conditionSQL = "( ";
		for (int i = 0;i < wParam.length; i++)
		{
			if (i == 0 )
			{
				conditionSQL += " (";
			}
			else
			{
				conditionSQL += "OR (";
			}			
			//#CM709219
			// Consignor Code
			conditionSQL += "DW.CONSIGNOR_CODE = '" + wParam[i].getConsignorCode() + "'";
			//#CM709220
			// Planned Picking Date
			conditionSQL += " AND " + "DW.PLAN_DATE = '" + wParam[i].getRetrievalPlanDate() + "'";
			//#CM709221
			// Item Code
			conditionSQL += " AND " + "DW.ITEM_CODE = '" + wParam[i].getItemCode() + "'";
			//#CM709222
			// For input info with Case/Piece division "Case":
			if (wParam[i].getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				conditionSQL += " AND " + "DW.WORK_FORM_FLAG = '" + WorkingInformation.CASEPIECE_FLAG_CASE + "'";
			}
			//#CM709223
			// For input info with Case/Piece division "Piece":
			else if (wParam[i].getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				conditionSQL += " AND " + "DW.WORK_FORM_FLAG = '" + WorkingInformation.CASEPIECE_FLAG_PIECE + "'";
			}
			//#CM709224
			// For input info with Case/Piece division "None":
			else if (wParam[i].getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				conditionSQL += " AND " + "DW.WORK_FORM_FLAG = '" + WorkingInformation.CASEPIECE_FLAG_NOTHING + "'";
			}
			//#CM709225
			// Location No.
			if (!wParam[i].getRetrievalLocation().equals(""))
			{
				conditionSQL += " AND " + "DW.LOCATION_NO = '" + wParam[i].getRetrievalLocation() + "'";
			}
			//#CM709226
			// Order No.
			if (!wParam[i].getOrderNo().equals(""))
			{
				conditionSQL += " AND " + "DW.ORDER_NO = '" + wParam[i].getOrderNo() + "'";
			}
			else
			{
				conditionSQL += " AND " + "DW.ORDER_NO IS NULL ";
			}

			conditionSQL += ") ";
		}
		conditionSQL += " )";

		return conditionSQL;
	}

	//#CM709227
	/**
	 * Search for data based on the parameter through database, and return the search result possible to cancel the allocation.
	 * @param key Key for search
	 * @param ckey Key for search (for CarryInformation)
	 * @return Array of  Entity of the searched Stock
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public Entity[] findAllocClear(SearchKey key, SearchKey ckey)
			throws ReadWriteException
	{
		setTableName("DNWORKINFO, CARRYINFO");

		Entity[] entity = null ;
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[4] ;

		try
		{
			stmt = getConnection().createStatement();
			String fmtSQL = "SELECT {0} "
				+ "FROM " + getTableName() + " "
				+ "{1} {2} {3}";

			//#CM709228
			// Compile the search conditions.
			//#CM709229
			// Compile the conditions for obtaining data.
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition();

				if (ckey.getCollectCondition() != null)
				{
					fmtObj[0] = fmtObj[0] + ckey.getCollectCondition();
				}
			}
			else
			{
				if (ckey.getCollectCondition() != null)
				{
					fmtObj[0] = ckey.getCollectCondition();
				}
				else
				{
					//#CM709230
					// Obtain all the field items if nothing is designated for the Conditions for Obtaining Data is None.
					fmtObj[0] = " * ";
				}
			}
			
			//#CM709231
			// Compile the search conditions.
			fmtObj[1] = "WHERE DNWORKINFO.SYSTEM_CONN_KEY = CARRYINFO.CARRYKEY(+)" ;
			fmtObj[1] = fmtObj[1] + " AND ((DNWORKINFO.SYSTEM_DISC_KEY != "  + SystemDefine.SYSTEM_DISC_KEY_ASRS;
			fmtObj[1] = fmtObj[1] + " AND DNWORKINFO.STATUS_FLAG = "  + SystemDefine.STATUS_FLAG_UNSTART + ")";
			fmtObj[1] = fmtObj[1] + " OR (DNWORKINFO.SYSTEM_DISC_KEY = "  + SystemDefine.SYSTEM_DISC_KEY_ASRS;
			fmtObj[1] = fmtObj[1] + " AND DNWORKINFO.STATUS_FLAG = "  + SystemDefine.STATUS_FLAG_NOWWORKING;
			fmtObj[1] = fmtObj[1] + " AND CARRYINFO.CMDSTATUS < "  + CarryInformation.CMDSTATUS_WAIT_RESPONSE + ")) ";

			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = fmtObj[1] + "AND " + key.getReferenceCondition();

				if (ckey.getReferenceCondition() != null)
				{
					fmtObj[1] = fmtObj[1] + " AND " + ckey.getReferenceCondition();
				}
			}
			else
			{
				if (ckey.getReferenceCondition() != null)
				{
					fmtObj[1] = fmtObj[1] + " AND " + ckey.getReferenceCondition();
				}
			}

			//#CM709232
			// Compile the aggregation conditions,			
			if (key.getGroupCondition() != null)
			{
				fmtObj[2] = " GROUP BY " + key.getGroupCondition();

				if (ckey.getGroupCondition() != null)
				{
					fmtObj[2] = fmtObj[2] + ", " + ckey.getGroupCondition();
				}
			}
			else
			{
				if (ckey.getGroupCondition() != null)
				{
					fmtObj[2] = " GROUP BY " + ckey.getGroupCondition();
				}
			}

			//#CM709233
			// Compile the loading sequence.				
			if (key.getSortCondition() != null)
			{
				fmtObj[3] = " ORDER BY " + key.getSortCondition();

				if (ckey.getSortCondition() != null)
				{
					fmtObj[3] = fmtObj[3] + ", " + ckey.getSortCondition();
				}
			}
			else
			{
				if (ckey.getSortCondition() != null)
				{
					fmtObj[3] = " ORDER BY " + ckey.getSortCondition();
				}
			}

			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", getTableName() + " COUNT SQL[" + sqlstring + "]") ;
			rset = stmt.executeQuery(sqlstring) ;
			entity = convertEntities(rset, key, 0) ;
		}
		catch(SQLException e)
		{
			//#CM709234
			//<jp>6006002=Database error occurred.{0}</jp>
			//#CM709235
			//<en>6006002 = Database error occured.  {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6006002, e), getTableName()) ;
			//#CM709236
			//<jp>Throw here the ReadWriteException with error message.</jp>
			//#CM709237
			//<en>Here, throw ReadWriteException with error message attached. </en>
			throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
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
			catch(SQLException e)
			{
				//#CM709238
				//<jp>6006002=Database error occurred.{0}</jp>
				//#CM709239
				//<en>6006002 = Database error occured.  {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6006002, e), getTableName()) ;
				//#CM709240
				//<jp>Throw here the ReadWriteException with error message.</jp>
				//#CM709241
				//<en>Here, throw ReadWriteException with error message attached. </en>
				throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
			}
		}
		return entity ;
	}

	//#CM709242
	/**
	 * Search for data based on the parameter through database, and return the search result.
	 * @param wKey Key for search
	 * @return Array of  Entity of the searched Stock
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int countCarry(WorkingInformationSearchKey wKey, CarryInformationSearchKey cKey)
			throws ReadWriteException
	{
		setTableName("DNWORKINFO, CARRYINFO");

		int wCount = 0 ;
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[3] ;

		try
		{
			stmt = getConnection().createStatement();
			String fmtSQL = "SELECT COUNT({0}) COUNT FROM " + getTableName() + " "
				+ "{1} {2}";
			//#CM709243
			// Compile the conditions for obtaining data.
			if (wKey.getCollectCondition() != null)
			{
				fmtObj[0] = wKey.getCollectCondition() ;
			}
			else
			{
				//#CM709244
				// Obtain all the field items if nothing is designated for the Conditions for Obtaining Data is None.
				fmtObj[0] = " * " ;
			}
			//#CM709245
			// Compile the search conditions.
			fmtObj[1] = "WHERE DNWORKINFO.SYSTEM_CONN_KEY = CARRYINFO.CARRYKEY " ;
			if (wKey.getReferenceCondition() != null)
			{
				fmtObj[1] = fmtObj[1] + "AND " + wKey.getReferenceCondition() ;
			}
			if (cKey.getReferenceCondition() != null)
			{
				fmtObj[1] = fmtObj[1] + "AND " + cKey.getReferenceCondition() ;
			}
			//#CM709246
			// Compile the aggregation conditions,
			if (wKey.getGroupCondition() != null)
			{
				fmtObj[2] = " GROUP BY " + wKey.getGroupCondition() ;
				if (cKey.getGroupCondition() != null)
				{
					fmtObj[2] = " , " + cKey.getGroupCondition() ;
				}
			}
			else
			{
				if (cKey.getGroupCondition() != null)
				{
					fmtObj[2] = " GROUP BY " + cKey.getGroupCondition() ;
				}
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", getTableName() + " COUNT SQL[" + sqlstring + "]") ;
			rset = stmt.executeQuery(sqlstring) ;
			if (rset != null)
			{
				while (rset.next())
				{
					wCount = rset.getInt("COUNT") ;
				}
			}
		}
		catch (SQLException e)
		{
			//#CM709247
			// 6006002=Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM709248
			// Throw here the ReadWriteException with error message.
			throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close() ;
					rset = null ;
				}
				if (stmt != null)
				{
					stmt.close() ;
					stmt = null ;
				}
			}
			catch (SQLException e)
			{
				//#CM709249
				// 6006002=Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM709250
				// Throw here the ReadWriteException with error message.
				throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
			}
		}
		return wCount ;
	}

}
//#CM709251
//end of class
