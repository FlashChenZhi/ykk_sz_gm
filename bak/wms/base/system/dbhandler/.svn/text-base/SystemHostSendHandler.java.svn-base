//#CM699700
//$Id: SystemHostSendHandler.java,v 1.2 2006/10/30 06:39:19 suresh Exp $
package jp.co.daifuku.wms.base.system.dbhandler;

//#CM699701
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.common.DEBUG;

//#CM699702
/**
 * Allow this class to operate the Sending Result Information (HostSend) using the system package.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/09</TD><TD>Nakai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 06:39:19 $
 * @author  $Author: suresh $
 */
public class SystemHostSendHandler extends StockHandler
{
	//#CM699703
	// Class feilds ------------------------------------------------

	//#CM699704
	// Class variables -----------------------------------------------

	//#CM699705
	// Class method --------------------------------------------------
	//#CM699706
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 06:39:19 $");
	}

	//#CM699707
	// Constructors --------------------------------------------------
	//#CM699708
	/**
	 * Designate <code>Connection</code> for database connection and generate an instance.
	 * @param conn Connection for database connection 
	 */
	public SystemHostSendHandler(Connection conn)
	{
		super(conn);
	}

	//#CM699709
	// Public methods ------------------------------------------------
	//#CM699710
	/**
	 * Delete the DNHOSTSEND table that has a Plan unique key with all of its work report flags "Sent" .
	 * Ensure to commit or rollback the transaction for applications that use this method.
	 * @param planUkey Plan unique key
	 * @return true: Normal,  false: Error
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws NotFoundException Announce if no info to be deleted is found.
	 */
	public boolean dropHostSend_MultiPlanUkey(String[] planUkey) throws ReadWriteException, NotFoundException
	{
		Statement stmt = null;
		ResultSet rset = null;
		int count = 0;

		try
		{
			stmt = getConnection().createStatement();

			//#CM699711
			// Construction of IN operator
			String inSql = "";
			for( int i = 0 ; i < planUkey.length ; i++ )
			{
				if( !StringUtil.isBlank(inSql) )
				{
					inSql = inSql + ", ";  
				}
				inSql = inSql + DBFormat.format(planUkey[i]);
			}

			String dropSql = "DELETE FROM DNHOSTSEND " + 
								" WHERE PLAN_UKEY IN ( " + inSql + ")";
DEBUG.MSG("HANDLER", "SystemHostSend Handler DROP SQL[" + dropSql + "]") ;

			count = stmt.executeUpdate(dropSql);
			if (count == 0)
			{
				//#CM699712
				//6006006=There is no data to delete. Table Name: {0}
				Object[] tObj = new Object[1];
				tObj[0] = "DnHostSend";
				RmiMsgLogClient.write(6006006, LogMessage.F_ERROR, "SystemHostSendHandler", tObj);
				throw (new NotFoundException("6006006" + wDelim + tObj[0]));
			}
		}
		catch (SQLException e)
		{
			//#CM699713
			// 6006002=Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			//#CM699714
			// Throw here the ReadWriteException with error message.
			throw (new ReadWriteException("6006002" + wDelim + "DnHostSend"));
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
				//#CM699715
				// 6006002=Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
				//#CM699716
				// Throw here the ReadWriteException with error message.
				throw (new ReadWriteException("6006002" + wDelim + "DnHostSend"));
			}
		}
		return true;
	}

	//#CM699717
	/**
	 * Delete the DNHOSTSEND table that has a Work No. with all of its work report flags "Sent" .
	 * Ensure to commit or rollback the transaction for applications that use this method.
	 * @param jobNo Work No.
	 * @return true: Normal,  false: Error
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws NotFoundException Announce if no info to be deleted is found.
	 */
	public boolean dropHostSend_MultiJobNo(String[] jobNo) throws ReadWriteException, NotFoundException
	{
		Statement stmt = null;
		ResultSet rset = null;
		int count = 0;

		try
		{
			stmt = getConnection().createStatement();

			//#CM699718
			// Construction of IN operator
			String inSql = "";
			for( int i = 0 ; i < jobNo.length ; i++ )
			{
				if( !StringUtil.isBlank(inSql) )
				{
					inSql = inSql + ", ";  
				}
				inSql = inSql + DBFormat.format(jobNo[i]);
			}

			String dropSql = "DELETE FROM DNHOSTSEND " + 
								" WHERE JOB_NO IN ( " + inSql + ")";
DEBUG.MSG("HANDLER", "SystemHostSend Handler DROP SQL[" + dropSql + "]") ;

			count = stmt.executeUpdate(dropSql);
			if (count == 0)
			{
				//#CM699719
				//6006006=There is no data to delete. Table Name: {0}
				Object[] tObj = new Object[1];
				tObj[0] = "DnHostSend";
				RmiMsgLogClient.write(6006006, LogMessage.F_ERROR, "SystemHostSendHandler", tObj);
				throw (new NotFoundException("6006006" + wDelim + tObj[0]));
			}
		}
		catch (SQLException e)
		{
			//#CM699720
			// 6006002=Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			//#CM699721
			// Throw here the ReadWriteException with error message.
			throw (new ReadWriteException("6006002" + wDelim + "DnHostSend"));
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
				//#CM699722
				// 6006002=Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
				//#CM699723
				// Throw here the ReadWriteException with error message.
				throw (new ReadWriteException("6006002" + wDelim + "DnHostSend"));
			}
		}
		return true;
	}

	//#CM699724
	// Package methods -----------------------------------------------

	//#CM699725
	// Protected methods ---------------------------------------------

	//#CM699726
	// Private methods -----------------------------------------------
}
//#CM699727
//end of class
