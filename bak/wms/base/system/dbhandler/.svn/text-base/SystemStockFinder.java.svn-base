//#CM699816
//$Id: SystemStockFinder.java,v 1.2 2006/10/30 06:39:17 suresh Exp $
package jp.co.daifuku.wms.base.system.dbhandler ;

//#CM699817
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
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;

//#CM699818
/**
 * Allow this class to search for the DmStock table through the database.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/08</TD><TD>T.nakai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 06:39:17 $
 * @author  $Author: suresh $
 */
public class SystemStockFinder extends StockReportFinder
{
	//#CM699819
	// Class filelds -----------------------------------------------

	//#CM699820
	// Class method --------------------------------------------------
	//#CM699821
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 06:39:17 $") ;
	}

	//#CM699822
	// Constructors --------------------------------------------------
	//#CM699823
	/**
	 * Designate <code>Connection</code> for database connection and generate an instance.
	 * @param conn Connection for database connection 
	 */
	public SystemStockFinder(Connection conn)
	{
		super(conn);
	}

	//#CM699824
	// Public methods ------------------------------------------------

	//#CM699825
	/**
	 * Combine DMSTOCK table, DNWORKINFO table, and DNINSTOCKPLAN table and search for the Stock ID of the inventory information corresponding to the plan unique key for the Receiving plan.
	 * Use the work date and the status flag as keys.Use PlanStockIdSearch method to construct SQL conditions and search it.
	 * @param workDate Work date
	 * @param status Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int InstockPlanStockIdSearch(String workDate, String[] status) throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status);
		return PlanStockIdSearch("DNINSTOCKPLAN", "INSTOCK_PLAN_UKEY", sql);
	}

	//#CM699826
	/**
	 * Combine DMSTOCK table, DNWORKINFO table, and DNINSTOCKPLAN table and search for the Stock ID of the inventory information corresponding to the plan unique key for the Receiving plan.
	 * Use the work date, the added date, and the status flag as keys.Use PlanStockIdSearch method to construct SQL conditions and search it.
	 * @param workDate Work date
	 * @param status1 Status flag
	 * @param deleteDate Boundary date for Delete
	 * @param status2 Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int InstockPlanStockIdSearch(String workDate, String[] status1, String deleteDate, String[] status2)
																						throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status1, deleteDate, status2);
		return PlanStockIdSearch("DNINSTOCKPLAN", "INSTOCK_PLAN_UKEY", sql);
	}

	//#CM699827
	/**
	 * Combine DMSTOCK table, DNWORKINFO table, and DNSTORAGEPLAN table and search for the Stock ID of the inventory information corresponding to the plan unique key for the Storage plan.
	 * Use the work date and the status flag as keys.Use PlanStockIdSearch method to construct SQL conditions and search it.
	 * @param workDate Work date
	 * @param status Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int StoragePlanStockIdSearch(String workDate, String[] status) throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status);
		return PlanStockIdSearch("DNSTORAGEPLAN", "STORAGE_PLAN_UKEY", sql);
	}

	//#CM699828
	/**
	 * Combine DMSTOCK table, DNWORKINFO table, and DNSTORAGEPLAN table and search for the Stock ID of the inventory information corresponding to the plan unique key for the Storage plan.
	 * Use the work date, the added date, and the status flag as keys.Use PlanStockIdSearch method to construct SQL conditions and search it.
	 * @param workDate Work date
	 * @param status1 Status flag
	 * @param deleteDate Boundary date for Delete
	 * @param status2 Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int StoragePlanStockIdSearch(String workDate, String[] status1, String deleteDate, String[] status2)
																						throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status1, deleteDate, status2);
		return PlanStockIdSearch("DNSTORAGEPLAN", "STORAGE_PLAN_UKEY", sql);
	}

	//#CM699829
	/**
	 * Combine DMSTOCK table, the DNWORKINFO table, and DNRETRIEVALPLAN table and search for the Stock ID of the inventory information corresponding to the plan unique key for the Picking plan.
	 * Use the work date and the status flag as keys.Use PlanStockIdSearch method to construct SQL conditions and search it.
	 * @param workDate Work date
	 * @param status Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int RetrievalPlanStockIdSearch(String workDate, String[] status) throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status);
		return PlanStockIdSearch("DNRETRIEVALPLAN", "RETRIEVAL_PLAN_UKEY", sql);
	}

	//#CM699830
	/**
	 * Combine DMSTOCK table, the DNWORKINFO table, and DNRETRIEVALPLAN table and search for the Stock ID of the inventory information corresponding to the plan unique key for the Picking plan.
	 * Use the work date, the added date, and the status flag as keys.Use PlanStockIdSearch method to construct SQL conditions and search it.
	 * @param workDate Work date
	 * @param status1 Status flag
	 * @param deleteDate Boundary date for Delete
	 * @param status2 Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int RetrievalPlanStockIdSearch(String workDate, String[] status1, String deleteDate, String[] status2)
																						throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status1, deleteDate, status2);
		return PlanStockIdSearch("DNRETRIEVALPLAN", "RETRIEVAL_PLAN_UKEY", sql);
	}

	//#CM699831
	/**
	 * Combine DMSTOCK table, DNWORKINFO table, and DNSORTINGPLAN table and search for the Stock ID of the inventory information corresponding to the plan unique key for the Sorting plan.
	 * Use the work date and the status flag as keys.Use PlanStockIdSearch method to construct SQL conditions and search it.
	 * @param workDate Work date
	 * @param status Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int SortingPlanStockIdSearch(String workDate, String[] status) throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status);
		return PlanStockIdSearch("DNSORTINGPLAN", "SORTING_PLAN_UKEY", sql);
	}

	//#CM699832
	/**
	 * Combine DMSTOCK table, DNWORKINFO table, and DNSORTINGPLAN table and search for the Stock ID of the inventory information corresponding to the plan unique key for the Sorting plan.
	 * Use the work date, the added date, and the status flag as keys.Use PlanStockIdSearch method to construct SQL conditions and search it.
	 * @param workDate Work date
	 * @param status1 Status flag
	 * @param deleteDate Boundary date for Delete
	 * @param status2 Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int SortingPlanStockIdSearch(String workDate, String[] status1, String deleteDate, String[] status2)
																						throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status1, deleteDate, status2);
		return PlanStockIdSearch("DNSORTINGPLAN", "SORTING_PLAN_UKEY", sql);
	}

	//#CM699833
	/**
	 * Combine DMSTOCK table, DNWORKINFO table, and DNSHIPPINGPLAN table and search for the Stock ID of the inventory information corresponding to the plan unique key for the Shipping plan.
	 * Use the work date and the status flag as keys.Use PlanStockIdSearch method to construct SQL conditions and search it.
	 * @param workDate Work date
	 * @param status Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int ShippingPlanStockIdSearch(String workDate, String[] status) throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status);
		return PlanStockIdSearch("DNSHIPPINGPLAN", "SHIPPING_PLAN_UKEY", sql);
	}

	//#CM699834
	/**
	 * Combine DMSTOCK table, DNWORKINFO table, and DNSHIPPINGPLAN table and search for the Stock ID of the inventory information corresponding to the plan unique key for the Shipping plan.
	 * Use the work date, the added date, and the status flag as keys.Use PlanStockIdSearch method to construct SQL conditions and search it.
	 * @param workDate Work date
	 * @param status1 Status flag
	 * @param deleteDate Boundary date for Delete
	 * @param status2 Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int ShippingPlanStockIdSearch(String workDate, String[] status1, String deleteDate, String[] status2)
																						throws ReadWriteException
	{
		String sql = PlanDateConditions(workDate, status1, deleteDate, status2);
		return PlanStockIdSearch("DNSHIPPINGPLAN", "SHIPPING_PLAN_UKEY", sql);
	}

	//#CM699835
	/**
	 * Return the result from searching through database in the form of string array.
	 * @param count Count of the search results
	 * @return String array
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public String[] getStockIdArray( int count ) throws ReadWriteException
	{
		Vector vec = new Vector();
		String[] stockIdArray = null ;

		try
		{
			//#CM699836
			// Count the data that have been read this time.
			int readCount = 0;
			//#CM699837
			// Flag to determine whether to close the ResultSet or not. Close after reading the end line.
			boolean endFlag = true;

			while (p_ResultSet.next())
			{			
				vec.addElement(DBFormat.replace(p_ResultSet.getString("STOCK_ID")));

				//#CM699838
				// Escape from WHILE after reading the designated number of data.
				readCount++;
				if ( count <= readCount )
				{
					endFlag = false;
					break;
				}
			}
			//#CM699839
			// Close after reading the end line.
			if ( endFlag )
			{
				isNextFlag = false ;
				close();
			}
			stockIdArray = new String[vec.size()] ;
			vec.copyInto(stockIdArray) ;
		}
		catch ( SQLException e )
		{
			//#CM699840
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler( 6006002, e ), "SystemStockFinder" );
			throw ( new ReadWriteException( "6006002" + wDelim + "DmStock" ) ) ;
		}

		return stockIdArray ;
	}

	//#CM699841
	// Package methods -----------------------------------------------

	//#CM699842
	// Protected methods ---------------------------------------------

	//#CM699843
	// Private methods -----------------------------------------------

	//#CM699844
	/**
	 * Combine DMSTOCK table, DNWORKINFO table, and DNxxxPLAN table and search for the Stock ID of the inventory information corresponding to the plan unique key for the Plan info.
	 * @param planTable Table name of Plan data
	 * @param planUkeyName Field item name of unique key for Plan data.
	 * @param workDate Work date
	 * @param status Status flag
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private int PlanStockIdSearch(String planTable, String planUkeyName, String sql) throws ReadWriteException
	{
		close();
		isNextFlag = true;
		open();
		Object[]  fmtObj = new Object[1];
		int count = 0;
		ResultSet countret  = null ;

		try
		{
			//#CM699845
			// Construction after FROM clause 
			String SelectSql = "FROM " + planTable + " PLN, DNWORKINFO WRK, DMSTOCK STK WHERE " +
								sql + " AND PLN." + planUkeyName + " = WRK.PLAN_UKEY AND WRK.STOCK_ID = STK.STOCK_ID";

			//#CM699846
			// Construction of a whole SELECT statement 
			String countSQL = "SELECT COUNT(STK.STOCK_ID) COUNT " + SelectSql;
			String fmtSQL = "SELECT DISTINCT STK.STOCK_ID " + SelectSql;

			String sqlcountstring = SimpleFormat.format(countSQL, fmtObj) ;
DEBUG.MSG("HANDLER", "SystemStock Finder SQL[" + sqlcountstring + "]") ;
			countret = p_Statement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", "SystemStock Finder SQL[" + sqlstring + "]") ;
			p_ResultSet = p_Statement.executeQuery(sqlstring);
		}
		catch (SQLException e)
		{
			//#CM699847
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "SystemStockFinder" ) ;
			throw (new ReadWriteException("6006002" + wDelim + "DmStock")) ;
		}
		return count;
	}

	//#CM699848
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

	//#CM699849
	/**
	 * Generate a search SQL using the planned date and the status flag in the DNxxxPLAN table as search conditions.
	 * @param selDate1 Planned date for Condition 1
	 * @param status1 Status flag for Condition 1
	 * @param selDate2 Planned date for Condition 2
	 * @param status2 Status flag for Condition 2
	 * @return Statement of search conditions
	 */
	private String PlanDateConditions(String selDate1, String[] status1, String selDate2, String[] status2)
	{
		String selectFmt1 = "PLN.PLAN_DATE <= " + DBFormat.format(selDate1) +
							" AND (" + StatusFlagConditions(status1) + ") ";

		String selectFmt2 = "PLN.PLAN_DATE <= " + DBFormat.format(selDate2) +
							" AND (" + StatusFlagConditions(status2) + ") ";

		String selectFmt = "((" + selectFmt1 + ") OR (" + selectFmt2 + "))" ;   

		return selectFmt;
	}

	//#CM699850
	/**
	 * Generate a search condition (OR) for a status flag in the DNxxxPLAN table.
	 * @param status Status flag
	 * @return A search statement for a status flag
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private String StatusFlagConditions(String[] status)
	{
		//#CM699851
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
}
//#CM699852
//end of class
