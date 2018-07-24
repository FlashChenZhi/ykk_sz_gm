//#CM699985
//$Id: SystemWorkingInformationHandler.java,v 1.2 2006/10/30 06:39:15 suresh Exp $
package jp.co.daifuku.wms.base.system.dbhandler;

//#CM699986
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

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.common.DEBUG;

//#CM699987
/**
 * Allow this class to operate the Work Status (WorkingInformation) using the system package.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/25</TD><TD>Yamashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 06:39:15 $
 * @author  $Author: suresh $
 */
public class SystemWorkingInformationHandler extends WorkingInformationHandler
{
	//#CM699988
	// Class feilds ------------------------------------------------

	//#CM699989
	// Class variables -----------------------------------------------

	//#CM699990
	// Class method --------------------------------------------------
	//#CM699991
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 06:39:15 $");
	}

	//#CM699992
	// Constructors --------------------------------------------------
	//#CM699993
	/**
	 * Designate <code>Connection</code> for database connection and generate an instance.
	 * @param conn Connection for database connection 
	 */
	public SystemWorkingInformationHandler(Connection conn)
	{
		super(conn);
	}

	//#CM699994
	// Public methods ------------------------------------------------
	//#CM699995
	/**
	 * Delete the DNWORKINFO table using the Plan unique key as a key.
	 * Ensure to commit or rollback the transaction for applications that use this method.
	 * Use a plan unique key as a key.
	 * @param PlanUkey Plan unique key
	 * @return true: Normal,  false: Error
	 * @throws ReadWriteException Announce exception occurred in the connection with database as it is.
	 * @throws NotFoundException Announce if no info to be deleted is found.
	 */
	public boolean dropWorkInfo_MultiPlanUkey(String[] PlanUkey) throws ReadWriteException, NotFoundException
	{
		Statement stmt = null;
		ResultSet rset = null;
		int count = 0;

		try
		{
			stmt = getConnection().createStatement();

			//#CM699996
			// Construction of IN operator
			String inSql = "";
			for( int i = 0 ; i < PlanUkey.length ; i++ )
			{
				if( !StringUtil.isBlank(inSql) )
				{
					inSql = inSql + ", ";  
				}
				inSql = inSql + DBFormat.format(PlanUkey[i]);
			}

			String dropSql = "DELETE FROM DNWORKINFO " + 
								" WHERE PLAN_UKEY IN ( " + inSql + ")";
DEBUG.MSG("HANDLER", "SystemWorkingInformation Handler DROP SQL[" + dropSql + "]") ;

			count = stmt.executeUpdate(dropSql);
			if (count == 0)
			{
				//#CM699997
				//6006006=There is no data to delete. Table Name: {0}
				Object[] tObj = new Object[1];
				tObj[0] = "DnWorkInfo";
				RmiMsgLogClient.write(6006006, LogMessage.F_ERROR, "SystemWorkingInformationHandler", tObj);
				throw (new NotFoundException("6006006" + wDelim + tObj[0]));
			}
		}
		catch (SQLException e)
		{
			//#CM699998
			// 6006002=Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			//#CM699999
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
				//#CM700000
				// 6006002=Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
				//#CM700001
				// Throw here the ReadWriteException with error message.
				throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));
			}
		}
		return true;
	}

	//#CM700002
	// Package methods -----------------------------------------------

	//#CM700003
	// Protected methods ---------------------------------------------

	//#CM700004
	// Private methods -----------------------------------------------
}
//#CM700005
//end of class
