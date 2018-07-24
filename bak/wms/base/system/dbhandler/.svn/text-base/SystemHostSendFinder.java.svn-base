//#CM699667
//$Id: SystemHostSendFinder.java,v 1.2 2006/10/30 06:39:19 suresh Exp $
package jp.co.daifuku.wms.base.system.dbhandler ;

//#CM699668
/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.dbhandler.HostSendReportFinder;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM699669
/**
 * Allow this class to search for the DnHostSend table through the database.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/08</TD><TD>T.nakai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 06:39:19 $
 * @author  $Author: suresh $
 */
public class SystemHostSendFinder extends HostSendReportFinder
{
	//#CM699670
	// Class filelds -----------------------------------------------

	//#CM699671
	// Class method --------------------------------------------------
	//#CM699672
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 06:39:19 $") ;
	}

	//#CM699673
	// Constructors --------------------------------------------------
	//#CM699674
	/**
	 * Designate <code>Connection</code> for database connection and generate an instance.
	 * @param conn Connection for database connection 
	 */
	public SystemHostSendFinder(Connection conn)
	{
		super(conn);
	}

	//#CM699675
	// Public methods ------------------------------------------------

	//#CM699676
	/**
	 * Search for the DNHOSTSEND table that has a Plan unique key with all of its work report flags "Sent" .
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int ReportPlanUkeySearch() throws ReadWriteException
	{
		close();
		isNextFlag = true;
		open();
		Object[]  fmtObj = new Object[1];
		int count = 0;
		ResultSet countret  = null ;

		try
		{
			//#CM699677
			// Construction after FROM clause 
			String SelectSql = "FROM DNHOSTSEND, "
							 +     "(SELECT MIN(REPORT_FLAG) FLAG, PLAN_UKEY FROM "
							 +         "( "
							 +             "SELECT REPORT_FLAG, PLAN_UKEY FROM DNHOSTSEND "
							 +           "UNION "
							 +             "SELECT REPORT_FLAG, PLAN_UKEY FROM DNWORKINFO WHERE "
							 +                 "STATUS_FLAG <> " + DBFormat.format(WorkingInformation.STATUS_FLAG_DELETE) + " AND "
							 +                 "STATUS_FLAG <> " + DBFormat.format(WorkingInformation.STATUS_FLAG_COMPLETION)
							 +         ") GROUP BY PLAN_UKEY"
							 +     ") DH WHERE DNHOSTSEND.PLAN_UKEY = DH.PLAN_UKEY AND FLAG = "
							 +  DBFormat.format(HostSend.REPORT_FLAG_SENT);

			//#CM699678
			// Construction of a whole SELECT statement 
			String countSQL = "SELECT COUNT(DNHOSTSEND.PLAN_UKEY) COUNT " + SelectSql;
			String fmtSQL = "SELECT DNHOSTSEND.PLAN_UKEY " + SelectSql;

			String sqlcountstring = SimpleFormat.format(countSQL, fmtObj) ;
DEBUG.MSG("HANDLER", "SystemHostSend Finder SQL[" + sqlcountstring + "]") ;
			countret = p_Statement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", "SystemHostSend Finder SQL[" + sqlstring + "]") ;
			p_ResultSet = p_Statement.executeQuery(sqlstring);
			
		}
		catch (SQLException e)
		{
			//#CM699679
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "SystemHostSendFinder" ) ;
			throw (new ReadWriteException("6006002" + wDelim + "DnHostSend")) ;
		}
		return count;
	}

	//#CM699680
	/**
	 * Search for the DNHOSTSEND table that has a Work No. with all of its work report flags "Sent" .
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int ReportJobNoSearch() throws ReadWriteException
	{
		close();
		isNextFlag = true;
		open();
		Object[]  fmtObj = new Object[1];
		int count = 0;
		ResultSet countret  = null ;

		try
		{
			//#CM699681
			// Construction after FROM clause 
			String SelectSql = "FROM DNHOSTSEND, "
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

			//#CM699682
			// Construction of a whole SELECT statement 
			String countSQL = "SELECT COUNT(DNHOSTSEND.JOB_NO) COUNT " + SelectSql;
			String fmtSQL = "SELECT DNHOSTSEND.JOB_NO " + SelectSql;

			String sqlcountstring = SimpleFormat.format(countSQL, fmtObj) ;
DEBUG.MSG("HANDLER", "SystemHostSend Finder SQL[" + sqlcountstring + "]") ;
			countret = p_Statement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", "SystemHostSend Finder SQL[" + sqlstring + "]") ;
			p_ResultSet = p_Statement.executeQuery(sqlstring);
		}
		catch (SQLException e)
		{
			//#CM699683
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "SystemHostSendFinder" ) ;
			throw (new ReadWriteException("6006002" + wDelim + "DnHostSend")) ;
		}
		return count;
	}

	//#CM699684
	/**
	 * Return the result from searching through database in the form of string array.
	 * @param count Count of the search results
	 * @return String array
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public String[] getPlanUkeyArray( int count ) throws ReadWriteException
	{
		Vector vec = new Vector();
		String[] planUkeyArray = null ;

		try
		{
			//#CM699685
			// Count the data that have been read this time.
			int readCount = 0;
			//#CM699686
			// Flag to determine whether to close the ResultSet or not. Close after reading the end line.
			boolean endFlag = true;

			while ( p_ResultSet.next() )
			{			
				vec.addElement(DBFormat.replace(p_ResultSet.getString("PLAN_UKEY")));

				//#CM699687
				// Escape from WHILE after reading the designated number of data.
				readCount++;
				if ( count <= readCount )
				{
					endFlag = false;
					break;
				}
			}
			//#CM699688
			// Close after reading the end line.
			if ( endFlag )
			{
				isNextFlag = false ;
				close();
			}
			planUkeyArray = new String[vec.size()] ;
			vec.copyInto(planUkeyArray) ;
		}
		catch ( SQLException e )
		{
			//#CM699689
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler( 6006002, e ), "SystemHostSendFinder" );
			throw ( new ReadWriteException( "6006002" + wDelim + "DnHostSend" ) ) ;
		}

		return planUkeyArray ;
	}

	//#CM699690
	/**
	 * Return the result from searching through database in the form of string array (for Unplanned storage/picking).
	 * @param count Count of the search results
	 * @return String array
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public String[] getJobNoArray( int count ) throws ReadWriteException
	{
		Vector vec = new Vector();
		String[] jobNoArray = null ;

		try
		{
			//#CM699691
			// Count the data that have been read this time.
			int readCount = 0;
			//#CM699692
			// Flag to determine whether to close the ResultSet or not. Close after reading the end line.
			boolean endFlag = true;

			while ( p_ResultSet.next() )
			{			
				vec.addElement(DBFormat.replace(p_ResultSet.getString("JOB_NO")));

				//#CM699693
				// Escape from WHILE after reading the designated number of data.
				readCount++;
				if ( count <= readCount )
				{
					endFlag = false;
					break;
				}
			}
			//#CM699694
			// Close after reading the end line.
			if ( endFlag )
			{
				isNextFlag = false ;
				close();
			}
			jobNoArray = new String[vec.size()] ;
			vec.copyInto(jobNoArray) ;
		}
		catch ( SQLException e )
		{
			//#CM699695
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler( 6006002, e ), "SystemHostSendFinder" );
			throw ( new ReadWriteException( "6006002" + wDelim + "DnHostSend" ) ) ;
		}

		return jobNoArray ;
	}

	//#CM699696
	// Package methods -----------------------------------------------

	//#CM699697
	// Protected methods ---------------------------------------------

	//#CM699698
	// Private methods -----------------------------------------------

}
//#CM699699
//end of class
