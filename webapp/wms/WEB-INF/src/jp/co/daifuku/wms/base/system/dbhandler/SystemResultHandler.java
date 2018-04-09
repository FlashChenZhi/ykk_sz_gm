//#CM699728
//$Id: SystemResultHandler.java,v 1.2 2006/10/30 06:39:19 suresh Exp $
package jp.co.daifuku.wms.base.system.dbhandler;

//#CM699729
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

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.wms.base.dbhandler.ResultHandler;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.common.DEBUG;

//#CM699730
/**
 * Allow this class to operate the result info (Result).<BR>
 * Describe a SQL that cannot be executed by a typical handler.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/25</TD><TD>M.Inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 06:39:19 $
 * @author  $Author: suresh $
 */
public class SystemResultHandler extends ResultHandler
{
	//#CM699731
	// Class feilds ------------------------------------------------

	//#CM699732
	// Class variables -----------------------------------------------
	//#CM699733
	/**
	 * Connection instance for database connection.
	 * Disable to control any transaction in this class.
	 */
	protected Connection wConn;

	//#CM699734
	// Class method --------------------------------------------------
	//#CM699735
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 06:39:19 $");
	}

	//#CM699736
	// Constructors --------------------------------------------------
	//#CM699737
	/**
	 * Designate <code>Connection</code> for database connection and generate an instance.
	 * @param conn Connection for database connection 
	 */
	public SystemResultHandler(Connection conn)
	{
		super(conn);
		setConnection(conn);
	}
	
	//#CM699738
	/**
	 * Set a <code>Connection</code> for database connection.
	 * @param conn Connection to be set
	 */
	public void setConnection(Connection conn)
	{
		wConn = conn;
	}
	//#CM699739
	// Public methods ------------------------------------------------
	//#CM699740
	/**
	 * Search through the HostSend data and insert the data only with all flags "Sent" into the Result.
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int moveHostResultData() throws ReadWriteException
	{
		Statement stmt = null;
		ResultSet rset = null;
		int moveCnt = 0;

		try
		{
			stmt = wConn.createStatement();
			String insertSQL = "INSERT INTO DNRESULT ";
			String countSQL = "SELECT COUNT(*) COUNT FROM ";
			String selectSQL =
				"SELECT "
					+ "DNHOSTSEND.WORK_DATE,"
					+ "DNHOSTSEND.JOB_NO,"
					+ "DNHOSTSEND.JOB_TYPE,"
					+ "DNHOSTSEND.COLLECT_JOB_NO,"
					+ "DNHOSTSEND.STATUS_FLAG,"
					+ "DNHOSTSEND.BEGINNING_FLAG,"
					+ "DNHOSTSEND.PLAN_UKEY,"
					+ "DNHOSTSEND.STOCK_ID,"
					+ "DNHOSTSEND.AREA_NO,"
					+ "DNHOSTSEND.ZONE_NO,"
					+ "DNHOSTSEND.LOCATION_NO,"
					+ "DNHOSTSEND.PLAN_DATE,"
					+ "DNHOSTSEND.CONSIGNOR_CODE,"
					+ "DNHOSTSEND.CONSIGNOR_NAME,"
					+ "DNHOSTSEND.SUPPLIER_CODE,"
					+ "DNHOSTSEND.SUPPLIER_NAME1,"
					+ "DNHOSTSEND.INSTOCK_TICKET_NO,"
					+ "DNHOSTSEND.INSTOCK_LINE_NO,"
					+ "DNHOSTSEND.CUSTOMER_CODE,"
					+ "DNHOSTSEND.CUSTOMER_NAME1,"
					+ "DNHOSTSEND.SHIPPING_TICKET_NO,"
					+ "DNHOSTSEND.SHIPPING_LINE_NO,"
					+ "DNHOSTSEND.ITEM_CODE,"
					+ "DNHOSTSEND.ITEM_NAME1,"
					+ "DNHOSTSEND.HOST_PLAN_QTY,"
					+ "DNHOSTSEND.PLAN_QTY,"
					+ "DNHOSTSEND.PLAN_ENABLE_QTY,"
					+ "DNHOSTSEND.RESULT_QTY,"
					+ "DNHOSTSEND.SHORTAGE_CNT,"
					+ "DNHOSTSEND.PENDING_QTY,"
					+ "DNHOSTSEND.ENTERING_QTY,"
					+ "DNHOSTSEND.BUNDLE_ENTERING_QTY,"
					+ "DNHOSTSEND.CASE_PIECE_FLAG,"
					+ "DNHOSTSEND.WORK_FORM_FLAG,"
					+ "DNHOSTSEND.ITF,"
					+ "DNHOSTSEND.BUNDLE_ITF,"
					+ "DNHOSTSEND.TCDC_FLAG,"
					+ "DNHOSTSEND.USE_BY_DATE,"
					+ "DNHOSTSEND.LOT_NO,"
					+ "DNHOSTSEND.PLAN_INFORMATION,"
					+ "DNHOSTSEND.ORDER_NO,"
					+ "DNHOSTSEND.ORDER_SEQ_NO,"
					+ "DNHOSTSEND.ORDERING_DATE,"
					+ "DNHOSTSEND.RESULT_USE_BY_DATE,"
					+ "DNHOSTSEND.RESULT_LOT_NO,"
					+ "DNHOSTSEND.RESULT_LOCATION_NO,"
					+ "DNHOSTSEND.REPORT_FLAG,"
					+ "DNHOSTSEND.BATCH_NO,"
					+ "DNHOSTSEND.SYSTEM_CONN_KEY,"
					+ "DNHOSTSEND.SYSTEM_DISC_KEY,"
					+ "DNHOSTSEND.WORKER_CODE,"
					+ "DNHOSTSEND.WORKER_NAME,"
					+ "DNHOSTSEND.TERMINAL_NO,"
					+ "DNHOSTSEND.PLAN_REGIST_DATE,"
					+ "sysdate,"
					+ "DNHOSTSEND.REGIST_PNAME,"
					+ "DNHOSTSEND.LAST_UPDATE_DATE ,"
					+ "DNHOSTSEND.LAST_UPDATE_PNAME "
					+   "FROM dnhostsend, "
					+       "(SELECT MIN(REPORT_FLAG) FLAG, PLAN_UKEY FROM "
					+           "( "
					+               "SELECT REPORT_FLAG, PLAN_UKEY FROM DNHOSTSEND "
					+             "UNION "
					+               "SELECT REPORT_FLAG, PLAN_UKEY FROM DNWORKINFO WHERE "
					+                   "STATUS_FLAG <> " + DBFormat.format(WorkingInformation.STATUS_FLAG_DELETE) + " AND "
					+                   "STATUS_FLAG <> " + DBFormat.format(WorkingInformation.STATUS_FLAG_COMPLETION)
					+           ") GROUP BY PLAN_UKEY"
					+       ") DH WHERE DNHOSTSEND.PLAN_UKEY = DH.PLAN_UKEY AND FLAG = "
					+ DBFormat.format(HostSend.REPORT_FLAG_SENT);

			//#CM699741
			// Execute the SQL.
			rset = stmt.executeQuery(countSQL +"("+ selectSQL+")");
			int count = 0;
			//#CM699742
			// Disable to do anything if the count of the target data is 0.
			while (rset.next())
			{
				count = rset.getInt("COUNT");
			}
			if( count > 0 )
			{
DEBUG.MSG("HANDLER", "SystemResult Handler COUNT SQL[" + insertSQL +"("+ selectSQL+")" + "]") ;
				//#CM699743
				// Execute the process for shifting.
				moveCnt = stmt.executeUpdate(insertSQL +"("+ selectSQL+")");
			}
		}
		catch (SQLException e)
		{

			//#CM699744
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			//#CM699745
			//Throw here the ReadWriteException with error message.
			throw (new ReadWriteException("6007039" + wDelim + "DnResult"));
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
				//#CM699746
				//6006002=Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
				//#CM699747
				//Throw here the ReadWriteException with error message.
				throw (new ReadWriteException("6007039" + wDelim + "DnResult"));
			}
		}
		return moveCnt;
	}

	//#CM699748
	/**
	 * Search through the HostSend data and insert the data only with all flags "Sent" into the Result. (for unplanned storage/picking)
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int moveHostResultNoPlanData() throws ReadWriteException
	{
		Statement stmt = null;
		ResultSet rset = null;
		int moveCnt = 0;

		try
		{
			stmt = wConn.createStatement();
			String insertSQL = "INSERT INTO DNRESULT ";
			String countSQL = "SELECT COUNT(*) COUNT FROM ";
			String selectSQL =
				"SELECT "
					+ "DNHOSTSEND.WORK_DATE,"
					+ "DNHOSTSEND.JOB_NO,"
					+ "DNHOSTSEND.JOB_TYPE,"
					+ "DNHOSTSEND.COLLECT_JOB_NO,"
					+ "DNHOSTSEND.STATUS_FLAG,"
					+ "DNHOSTSEND.BEGINNING_FLAG,"
					+ "DNHOSTSEND.PLAN_UKEY,"
					+ "DNHOSTSEND.STOCK_ID,"
					+ "DNHOSTSEND.AREA_NO,"
					+ "DNHOSTSEND.ZONE_NO,"
					+ "DNHOSTSEND.LOCATION_NO,"
					+ "DNHOSTSEND.PLAN_DATE,"
					+ "DNHOSTSEND.CONSIGNOR_CODE,"
					+ "DNHOSTSEND.CONSIGNOR_NAME,"
					+ "DNHOSTSEND.SUPPLIER_CODE,"
					+ "DNHOSTSEND.SUPPLIER_NAME1,"
					+ "DNHOSTSEND.INSTOCK_TICKET_NO,"
					+ "DNHOSTSEND.INSTOCK_LINE_NO,"
					+ "DNHOSTSEND.CUSTOMER_CODE,"
					+ "DNHOSTSEND.CUSTOMER_NAME1,"
					+ "DNHOSTSEND.SHIPPING_TICKET_NO,"
					+ "DNHOSTSEND.SHIPPING_LINE_NO,"
					+ "DNHOSTSEND.ITEM_CODE,"
					+ "DNHOSTSEND.ITEM_NAME1,"
					+ "DNHOSTSEND.HOST_PLAN_QTY,"
					+ "DNHOSTSEND.PLAN_QTY,"
					+ "DNHOSTSEND.PLAN_ENABLE_QTY,"
					+ "DNHOSTSEND.RESULT_QTY,"
					+ "DNHOSTSEND.SHORTAGE_CNT,"
					+ "DNHOSTSEND.PENDING_QTY,"
					+ "DNHOSTSEND.ENTERING_QTY,"
					+ "DNHOSTSEND.BUNDLE_ENTERING_QTY,"
					+ "DNHOSTSEND.CASE_PIECE_FLAG,"
					+ "DNHOSTSEND.WORK_FORM_FLAG,"
					+ "DNHOSTSEND.ITF,"
					+ "DNHOSTSEND.BUNDLE_ITF,"
					+ "DNHOSTSEND.TCDC_FLAG,"
					+ "DNHOSTSEND.USE_BY_DATE,"
					+ "DNHOSTSEND.LOT_NO,"
					+ "DNHOSTSEND.PLAN_INFORMATION,"
					+ "DNHOSTSEND.ORDER_NO,"
					+ "DNHOSTSEND.ORDER_SEQ_NO,"
					+ "DNHOSTSEND.ORDERING_DATE,"
					+ "DNHOSTSEND.RESULT_USE_BY_DATE,"
					+ "DNHOSTSEND.RESULT_LOT_NO,"
					+ "DNHOSTSEND.RESULT_LOCATION_NO,"
					+ "DNHOSTSEND.REPORT_FLAG,"
					+ "DNHOSTSEND.BATCH_NO,"
					+ "DNHOSTSEND.SYSTEM_CONN_KEY,"
					+ "DNHOSTSEND.SYSTEM_DISC_KEY,"
					+ "DNHOSTSEND.WORKER_CODE,"
					+ "DNHOSTSEND.WORKER_NAME,"
					+ "DNHOSTSEND.TERMINAL_NO,"
					+ "DNHOSTSEND.PLAN_REGIST_DATE,"
					+ "sysdate,"
					+ "DNHOSTSEND.REGIST_PNAME,"
					+ "DNHOSTSEND.LAST_UPDATE_DATE ,"
					+ "DNHOSTSEND.LAST_UPDATE_PNAME "
					+   "FROM dnhostsend, "
					+       "(SELECT MIN(REPORT_FLAG) FLAG, JOB_NO FROM "
					+           "( "
					+               "SELECT REPORT_FLAG, JOB_NO FROM DNHOSTSEND WHERE "
					+                   "JOB_TYPE = " + DBFormat.format(HostSend.JOB_TYPE_INVENTORY_PLUS) + " OR "
					+                   "JOB_TYPE = " + DBFormat.format(HostSend.JOB_TYPE_INVENTORY_MINUS) + " OR "
					+                   "JOB_TYPE = " + DBFormat.format(HostSend.JOB_TYPE_MAINTENANCE_PLUS) + " OR "
					+                   "JOB_TYPE = " + DBFormat.format(HostSend.JOB_TYPE_MAINTENANCE_MINUS) + " OR "					
					+                   "JOB_TYPE = " + DBFormat.format(HostSend.JOB_TYPE_MOVEMENT_STORAGE) + " OR "
					+                   "JOB_TYPE = " + DBFormat.format(HostSend.JOB_TYPE_EX_STORAGE) + " OR "
					+                   "JOB_TYPE = " + DBFormat.format(HostSend.JOB_TYPE_EX_RETRIEVAL) + " OR "
					+                   "JOB_TYPE = " + DBFormat.format(HostSend.JOB_TYPE_ASRS_INVENTORY_CHECK)
					+           ") GROUP BY JOB_NO"
					+       ") DH WHERE DNHOSTSEND.JOB_NO = DH.JOB_NO AND FLAG = "
					+ DBFormat.format(HostSend.REPORT_FLAG_SENT);

			//#CM699749
			// Execute the SQL.
			rset = stmt.executeQuery(countSQL +"("+ selectSQL+")");
			int count = 0;
			//#CM699750
			// Disable to do anything if the count of the target data is 0.
			while (rset.next())
			{
				count = rset.getInt("COUNT");
			}
			if( count > 0 )
			{
DEBUG.MSG("HANDLER", "SystemResult Handler COUNT SQL[" + insertSQL +"("+ selectSQL+")" + "]") ;
				//#CM699751
				// Execute the process for shifting.
				moveCnt = stmt.executeUpdate(insertSQL +"("+ selectSQL+")");
			}
		}
		catch (SQLException e)
		{

			//#CM699752
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			//#CM699753
			//Throw here the ReadWriteException with error message.
			throw (new ReadWriteException("6007039" + wDelim + "DnResult"));
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
				//#CM699754
				//6006002=Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
				//#CM699755
				//Throw here the ReadWriteException with error message.
				throw (new ReadWriteException("6007039" + wDelim + "DnResult"));
			}
		}
		return moveCnt;
	}	
	//#CM699756
	// Package methods -----------------------------------------------

	//#CM699757
	// Protected methods ---------------------------------------------

	//#CM699758
	// Private methods -----------------------------------------------
}
//#CM699759
//end of class
