//#CM699943
//$Id: SystemWorkingInformationFinder.java,v 1.2 2006/10/30 06:39:16 suresh Exp $
package jp.co.daifuku.wms.base.system.dbhandler ;

//#CM699944
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
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;

//#CM699945
/**
 * Allow this class to search for the WorkingInformation table through the database.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/08</TD><TD>T.nakai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 06:39:16 $
 * @author  $Author: suresh $
 */
public class SystemWorkingInformationFinder extends WorkingInformationReportFinder
{
	//#CM699946
	// Class filelds -----------------------------------------------

	//#CM699947
	// Class method --------------------------------------------------
	//#CM699948
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 06:39:16 $") ;
	}

	//#CM699949
	// Constructors --------------------------------------------------
	//#CM699950
	/**
	 * Designate <code>Connection</code> for database connection and generate an instance.
	 * @param conn Connection for database connection 
	 */
	public SystemWorkingInformationFinder(Connection conn)
	{
		super(conn);
	}

	//#CM699951
	// Public methods ------------------------------------------------

	//#CM699952
	/**
	 * Combine DNWORKINFO table and DNINSTOCKPLAN table and search for the plan unique key for the work status corresponding to the plan unique key for the Receiving plan.
	 * Use the work date and the status flag as keys but use PlanUkeySearch method to construct SQL conditions and execute searching.
	 * @param workDate Work date
	 * @param status Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int InstockPlanUkeySearch(String workDate, String[] status) throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status);
		return PlanUkeySearch("DNINSTOCKPLAN", "INSTOCK_PLAN_UKEY", sql);
	}

	//#CM699953
	/**
	 * Combine DNWORKINFO table and DNINSTOCKPLAN table and search for the plan unique key for the work status corresponding to the plan unique key for the Receiving plan.
	 * Use the work date, the added date, and the status flag as keys but use PlanUkeySearch method to construct SQL conditions and execute searching.
	 * @param workDate Work date
	 * @param status1 Status flag
	 * @param deleteDate Boundary date for Delete
	 * @param status2 Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int InstockPlanUkeySearch(String workDate, String[] status1, String deleteDate, String[] status2)
																						throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status1, deleteDate, status2);
		return PlanUkeySearch("DNINSTOCKPLAN", "INSTOCK_PLAN_UKEY", sql);
	}

	//#CM699954
	/**
	 * Combine DNWORKINFO table and DNSTORAGEPLAN table and search for the plan unique key for the work status corresponding to the plan unique key for the Storage plan.
	 * Use the work date and the status flag as keys but use PlanUkeySearch method to construct SQL conditions and execute searching.
	 * @param workDate Work date
	 * @param status Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int StoragePlanUkeySearch(String workDate, String[] status) throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status);
		return PlanUkeySearch("DNSTORAGEPLAN", "STORAGE_PLAN_UKEY", sql);
	}

	//#CM699955
	/**
	 * Combine DNWORKINFO table and DNSTORAGEPLAN table and search for the plan unique key for the work status corresponding to the plan unique key for the Storage plan.
	 * Use the work date, the added date, and the status flag as keys but use PlanUkeySearch method to construct SQL conditions and execute searching.
	 * @param workDate Work date
	 * @param status1 Status flag
	 * @param deleteDate Boundary date for Delete
	 * @param status2 Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int StoragePlanUkeySearch(String workDate, String[] status1, String deleteDate, String[] status2)
																						throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status1, deleteDate, status2);
		return PlanUkeySearch("DNSTORAGEPLAN", "STORAGE_PLAN_UKEY", sql);
	}

	//#CM699956
	/**
	 * Combine DNWORKINFO table and DNRETRIEVALPLAN table and search for the plan unique key for the work status corresponding to the plan unique key for the Picking plan.
	 * Use the work date and the status flag as keys but use PlanUkeySearch method to construct SQL conditions and execute searching.
	 * @param workDate Work date
	 * @param status Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int RetrievalPlanUkeySearch(String workDate, String[] status) throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status);
		return PlanUkeySearch("DNRETRIEVALPLAN", "RETRIEVAL_PLAN_UKEY", sql);
	}

	//#CM699957
	/**
	 * Combine DNWORKINFO table and DNRETRIEVALPLAN table and search for the plan unique key for the work status corresponding to the plan unique key for the Picking plan.
	 * Use the work date, the added date, and the status flag as keys but use PlanUkeySearch method to construct SQL conditions and execute searching.
	 * @param workDate Work date
	 * @param status1 Status flag
	 * @param deleteDate Boundary date for Delete
	 * @param status2 Status flag and Schedule flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int RetrievalPlanUkeySearch(String workDate, String[] status1, String deleteDate, String[][] status2)
																						throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status1, deleteDate, status2);
		return PlanUkeySearch("DNRETRIEVALPLAN", "RETRIEVAL_PLAN_UKEY", sql);
	}

	//#CM699958
	/**
	 * Combine DNWORKINFO table and DNRETRIEVALPLAN table and search for the plan unique key for the work status corresponding to the plan unique key for the Picking plan.
	 * Use the work date, the added date, and the status flag as keys but use PlanUkeySearch method to construct SQL conditions and execute searching.
	 * @param workDate Work date
	 * @param status1 Status flag
	 * @param deleteDate Boundary date for Delete
	 * @param status2 Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int RetrievalPlanUkeySearch(String workDate, String[] status1, String deleteDate, String[] status2)
																						throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status1, deleteDate, status2);
		return PlanUkeySearch("DNRETRIEVALPLAN", "RETRIEVAL_PLAN_UKEY", sql);
	}

	//#CM699959
	/**
	 * Combine DNWORKINFO table and DNSORTINGPLAN table and search for the plan unique key for the work status corresponding to the plan unique key for the Sorting plan.
	 * Use the work date and the status flag as keys but use PlanUkeySearch method to construct SQL conditions and execute searching.
	 * @param workDate Work date
	 * @param status Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int SortingPlanUkeySearch(String workDate, String[] status) throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status);
		return PlanUkeySearch("DNSORTINGPLAN", "SORTING_PLAN_UKEY", sql);
	}

	//#CM699960
	/**
	 * Combine DNWORKINFO table and DNSORTINGPLAN table and search for the plan unique key for the work status corresponding to the plan unique key for the Sorting plan.
	 * Use the work date, the added date, and the status flag as keys but use PlanUkeySearch method to construct SQL conditions and execute searching.
	 * @param workDate Work date
	 * @param status1 Status flag
	 * @param deleteDate Boundary date for Delete
	 * @param status2 Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int SortingPlanUkeySearch(String workDate, String[] status1, String deleteDate, String[] status2)
																						throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status1, deleteDate, status2);
		return PlanUkeySearch("DNSORTINGPLAN", "SORTING_PLAN_UKEY", sql);
	}

	//#CM699961
	/**
	 * Combine DNWORKINFO table and DNSHIPPINGPLAN table and search for the plan unique key for the work status corresponding to the plan unique key for the Shipping plan.
	 * Use the work date and the status flag as keys but use PlanUkeySearch method to construct SQL conditions and execute searching.
	 * @param workDate Work date
	 * @param status Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int ShippingPlanUkeySearch(String workDate, String[] status) throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status);
		return PlanUkeySearch("DNSHIPPINGPLAN", "SHIPPING_PLAN_UKEY", sql);
	}

	//#CM699962
	/**
	 * Combine DNWORKINFO table and DNSHIPPINGPLAN table and search for the plan unique key for the work status corresponding to the plan unique key for the Shipping plan.
	 * Use the work date, the added date, and the status flag as keys but use PlanUkeySearch method to construct SQL conditions and execute searching.
	 * @param workDate Work date
	 * @param status1 Status flag
	 * @param deleteDate Boundary date for Delete
	 * @param status2 Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int ShippingPlanUkeySearch(String workDate, String[] status1, String deleteDate, String[] status2)
																						throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status1, deleteDate, status2);
		return PlanUkeySearch("DNSHIPPINGPLAN", "SHIPPING_PLAN_UKEY", sql);
	}

	//#CM699963
	/**
	 * Return the result from searching through database in the form of string array.
	 * @param count Count of the search results
	 * @return String array
	 * @throws ReadWriteException Announce exception occurred in the connection with database as it is.
	 */
	public String[] getPlanUkeyArray( int count ) throws ReadWriteException
	{
		Vector vec = new Vector();
		String[] planUkeyArray = null ;

		try
		{
			//#CM699964
			// Count the data that have been read this time.
			int readCount = 0;
			//#CM699965
			// Flag to determine whether to close the ResultSet or not. Close after reading the end line.
			boolean endFlag = true;

			while (p_ResultSet.next())
			{			
				vec.addElement(DBFormat.replace(p_ResultSet.getString("PLAN_UKEY")));

				//#CM699966
				// Escape from WHILE after reading the designated number of data.
				readCount++;
				if ( count <= readCount )
				{
					endFlag = false;
					break;
				}
			}
			//#CM699967
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
			//#CM699968
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler( 6006002, e ), "SystemWorkingInformationFinder" );
			throw ( new ReadWriteException( "6006002" + wDelim + "DnWorkInfo" ) ) ;
		}

		return planUkeyArray ;
	}

	//#CM699969
	// Package methods -----------------------------------------------

	//#CM699970
	// Protected methods ---------------------------------------------

	//#CM699971
	// Private methods -----------------------------------------------

	//#CM699972
	/**
	 * Combine DNWORKINFO table and DNxxxPLAN table and search for the plan unique key for the work status corresponding to the plan unique key for the Plan info.
	 * @param planTable Table name of Plan data
	 * @param planUkeyName Field item name of unique key for Plan data.
	 * @param workDate Work date
	 * @param status Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private int PlanUkeySearch(String planTable, String planUkeyName, String sql) throws ReadWriteException
	{
		close();
		isNextFlag = true;
		open();
		Object[]  fmtObj = new Object[1];
		int count = 0;
		ResultSet countret  = null ;

		try
		{
			//#CM699973
			// Construction after FROM clause 
			String SelectSql = "FROM " + planTable + " PLN, DNWORKINFO WRK WHERE " +
								sql + " AND PLN." + planUkeyName + " = WRK.PLAN_UKEY";

			//#CM699974
			// Construction of a whole SELECT statement 
			String countSQL = "SELECT COUNT(WRK.PLAN_UKEY) COUNT " + SelectSql;
			String fmtSQL = "SELECT DISTINCT WRK.PLAN_UKEY " + SelectSql;

			String sqlcountstring = SimpleFormat.format(countSQL, fmtObj) ;
DEBUG.MSG("HANDLER", "SystemWorkingInformation Finder COUNT SQL[" + sqlcountstring + "]") ;
			countret = p_Statement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", "SystemWorkingInformation Finder SQL[" + sqlstring + "]") ;
			p_ResultSet = p_Statement.executeQuery(sqlstring);
		}
		catch (SQLException e)
		{
			//#CM699975
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "SystemWorkingInformationFinder" ) ;
			throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo")) ;
		}
		return count;
	}

	//#CM699976
	/**
	 * Generate a search SQL using the planned date and the status flag in the DNxxxPLAN table as search conditions.
	 * @param selDate Planned date
	 * @param status Status flag
	 * @return Statement of search conditions
	 */
	private String PlanDateConditions(String selDate, String[] status)
	{
		return "PLN.PLAN_DATE <= " + DBFormat.format(selDate) + " AND ( " + StatusFlagConditions(status) + ") ";
	}

	//#CM699977
	/**
	 * Generate a search SQL using the planned date, the added date, the status flag, and the schedule flag in the DNxxxPLAN table as search conditions.
	 * @param selDate1 Planned date for Condition 1
	 * @param status1 Status flag for Condition 1
	 * @param selDate2 Added date for Condition 2
	 * @param status2 Status flag and Schedule flag for Condition 2
	 * @return Statement of search conditions
	 */
	private String PlanDateConditions(String selDate1, String[] status1, String selDate2, String[][] status2)
	{
		String selectFmt1 = "PLN.PLAN_DATE <= " + DBFormat.format(selDate1) +
							" AND ( " + StatusFlagConditions(status1) + ") ";

		String selectFmt2 = "PLN.PLAN_DATE <= " + DBFormat.format(selDate2) +
							" AND ( " + StatusFlagConditions(status2) + ") ";

		String selectFmt = "((" + selectFmt1 + ") OR (" + selectFmt2 + "))" ;   

		return selectFmt;
	}

	//#CM699978
	/**
	 * Generate a search SQL using the planned date, the added date, and the status flag in the DNxxxPLAN table as search conditions.
	 * @param selDate1 Planned date for Condition 1
	 * @param status1 Status flag for Condition 1
	 * @param selDate2 Added date for Condition 2
	 * @param status2 Status flag for Condition 2
	 * @return Statement of search conditions
	 */
	private String PlanDateConditions(String selDate1, String[] status1, String selDate2, String[] status2)
	{
		String selectFmt1 = "PLN.PLAN_DATE <= " + DBFormat.format(selDate1) +
							" AND ( " + StatusFlagConditions(status1) + ") ";

		String selectFmt2 = "PLN.PLAN_DATE <= " + DBFormat.format(selDate2) +
							" AND ( " + StatusFlagConditions(status2) + ") ";

		String selectFmt = "((" + selectFmt1 + ") OR (" + selectFmt2 + "))" ;   

		return selectFmt;
	}

	//#CM699979
	/**
	 * Generate a search condition (OR) for a status flag in the DNxxxPLAN table.
	 * @param status Status flag
	 * @return A search statement for a status flag
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private String StatusFlagConditions(String[] status)
	{
		//#CM699980
		// Construction of the conditional statement of status flag to be searched in the Plan info
		String statusSql = "";
		for( int i = 0 ; i < status.length ; i++ )
		{
			if( !StringUtil.isBlank(statusSql) )
			{
				statusSql = statusSql + " OR ";
			}
			statusSql = statusSql + "PLN.STATUS_FLAG = " + DBFormat.format(status[i]) ;
		}
		return statusSql;
	}

	//#CM699981
	/**
	 * Generate a search condition (OR) for a status flag in the DNxxxPLAN table.
	 * Connect them using AND if any schedule flag linked to the status flag exists.
	 * @param status Status flag and Schedule flag
	 * @return A search statement for a status flag
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private String StatusFlagConditions(String[][] status)
	{
		//#CM699982
		// Construction of the conditional statement of status flag to be searched in the Plan info
		String statusSql = "";
		for( int i = 0 ; i < status.length ; i++ )
		{
			if( !StringUtil.isBlank(statusSql) )
			{
				statusSql = statusSql + " OR ";
			}
			//#CM699983
			// if the schedule flag exists.
			if((status[i].length > 1)
			&& (status[i][1] != null))
			{
				statusSql = statusSql + "(PLN.STATUS_FLAG = " + DBFormat.format(status[i][0])  + " AND ";
				statusSql = statusSql + "PLN.SCH_FLAG = " + DBFormat.format(status[i][1]) + ")";
			}
			else
			{
				statusSql = statusSql + "PLN.STATUS_FLAG = " + DBFormat.format(status[i][0]) ;
			}
		}
		return statusSql;
	}
}
//#CM699984
//end of class
