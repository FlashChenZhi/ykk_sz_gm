// $Id: SessionStockItemRet.java,v 1.2 2006/10/04 05:12:11 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret;

//#CM6590
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM6591
/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * <BR>
 * This is the class to search and display item list from Stock info.<BR>
 * Store the instance to the Session when you use this class. Delete from the Session after using.<BR> 
 * Execute following processes in this class.<BR> 
 * <BR>
 * 1.Search Process(<CODE>SessionStockItemRet(Connection conn, StockControlParameter param)</CODE>Method) <BR>
 * <DIR>
 *   Execute when listbox screen is initial/default displayed. <BR>
 *   Invoke <CODE>find (StockControlParameter param)</CODE> method and search for the Stock info. <BR>
 * <BR>
 *  <DIR>
 *   [search conditions]  Mandatory item (count)* <BR>
 *   <DIR>
 *     Consignor code <BR>
 *     Item code <BR>
 *     Start item code <BR>
 *     End item code <BR>
 *     Start Location No. <BR>
 *     Closed Location No. <BR>
 *     Stock status is in Center Inventory *<BR>
 *     Stock qty shall be 1 or more*<BR>
 *   </DIR>
 *   [Search table] <BR>
 *   <DIR>
 *     DMSTOCK <BR>
 *   </DIR>
 *   [<Items to be extracted>] <BR>
 *   <DIR>
 *     Item code <BR>
 *     Item name <BR>
 *   </DIR>
 *  </DIR>
 * </DIR>
 * 
 * 2.Display Process(<CODE>getEntities()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *   Obtain the data needed to display on the screen. <BR>
 * <BR>
 *   1.Obtain the display info that was obtained in the search process. <BR>
 *   Set the search result back to the inventory information array. <BR>
 * <BR>
 *   [Item to be displayed]
 *   <DIR>
 *     Item code <BR>
 *     Item name <BR>
 *   </DIR>
 * </DIR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/18</TD><TD>K.Toda</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:12:11 $
 * @author  $Author: suresh $
 */
public class SessionStockItemRet extends SessionRet
{
	//#CM6592
	// Class fields --------------------------------------------------

	//#CM6593
	// Class variables -----------------------------------------------

	//#CM6594
	// Class method --------------------------------------------------
	//#CM6595
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/04 05:12:11 $");
	}

	//#CM6596
	// Constructors --------------------------------------------------
	//#CM6597
	/**
	 * Invoke <CODE>find(StockControlParameter param)</CODE> Method to execute search. <BR>
	 * Invoke the count of obtained data by <CODE>find (StockControlParameter param)</CODE> method. <BR>
	 *  Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * <BR>
	 * <DIR>
	 * [search conditions] <BR>
	 * <DIR>
	 * Consignor code <BR>
	 * Item code <BR>
	 * Start item code <BR>
	 * End item code <BR>
	 * Start Location No. <BR>
	 * Closed Location No. <BR>
     * Stock status is in Center Inventory  <BR>
     * Stock qty shall be 1 or more <BR>
	 * </DIR>
	 * </DIR>
	 * @param conn       <CODE>Connection</CODE>
	 * @param param      Parameter including <CODE>StockControlParameter</CODE>search result
	 * @throws Exception Announce this when any exception generates.
	 */
	public SessionStockItemRet(Connection conn,	StockControlParameter param) throws Exception
	{
		this.wConn = conn;
		
		//#CM6598
		// Search
		find(param);
	}

	//#CM6599
	// Public methods ------------------------------------------------
	//#CM6600
	/**
	 * Return the designated number of resurch results of the stock info <CODE> (DMSTOCK)</CODE>.<BR>
	 * <BR>
	 * <DIR>
	 * [search result] <BR>
	 * <DIR>
	 * Item code <BR>
	 * Item name <BR>
	 * </DIR>
	 * </DIR>
	 * @return search result sequence of Stock info<CODE>(DMSTOCK)</CODE>
	 */
	public Stock[] getEntities()
	{
		Stock[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray = (Stock[]) ((StockFinder)wFinder).getEntities(wStartpoint, wEndpoint);
			}
			catch (Exception e)
			{
				//#CM6601
				// Record the error in the log file.
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM6602
	// Package methods -----------------------------------------------

	//#CM6603
	// Protected methods ---------------------------------------------

	//#CM6604
	// Private methods -----------------------------------------------
	//#CM6605
	/** 
	 * Print the SQL statement based on the input parameter and search for the Stock info<CODE> (Dmstock)</CODE>. <BR>
	 * Maintain the <code>StockFinder</code> for searching as an instance variable. <BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param param      Parameter including <CODE>StockControlParameter</CODE>search result
	 * @throws Exception Announce this when any exception generates.
	 */
	private void find(StockControlParameter param) throws Exception
	{
		int count = 0;

		StockSearchKey searchKey = new StockSearchKey();
		//#CM6606
		// Execute Search
		//#CM6607
		// Consignor code 
		if (!StringUtil.isBlank(param.getConsignorCode()))
			searchKey.setConsignorCode(param.getConsignorCode());
		//#CM6608
		// if item code is set
		if(!StringUtil.isBlank(param.getItemCode()))
		{
			searchKey.setItemCode(param.getItemCode());
		}
		//#CM6609
		// When Start Item code or End item code is set
		else
		{
			//#CM6610
			// Require to set the  Start Item code
			if(!StringUtil.isBlank(param.getFromItemCode()))
			{
				searchKey.setItemCode(param.getFromItemCode());
			}
			//#CM6611
			// Require to set the End item code.
			if(!StringUtil.isBlank(param.getToItemCode()))
			{
				searchKey.setItemCode(param.getToItemCode());
			}
		}
		//#CM6612
		// Start Location No.
		if (!StringUtil.isBlank(param.getFromLocationNo()))
			searchKey.setLocationNo(param.getFromLocationNo(), ">=");
		//#CM6613
		// Closed Location No.
		if (!StringUtil.isBlank(param.getToLocationNo()))
			searchKey.setLocationNo(param.getToLocationNo(), "<=");
		//#CM6614
		// Stock status(Center Inventory)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM6615
		// Stock qty shall be 1 or more
		searchKey.setStockQty(1, ">=");
		
		if (StockControlParameter.AREA_TYPE_FLAG_NOASRS.equals(param.getAreaTypeFlag()))
		{
			AreaOperator AreaOperator = new AreaOperator(wConn);
			
			String[] areaNo = null;
			int[] areaType = new int[2];
			areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
			areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
			
			//#CM6616
			// Obtain the Area other than ASRS and add it to the search conditions.
			//#CM6617
			// Search IS NULL if no corresponding area found.
			areaNo = AreaOperator.getAreaNo(areaType);
			searchKey.setAreaNo(areaNo);
		}

		searchKey.setItemCodeGroup(1);
		searchKey.setItemName1Group(2);
		searchKey.setItemCodeCollect("");
		searchKey.setItemName1Collect("");
		searchKey.setItemCodeOrder(1, true);
		searchKey.setItemName1Order(2, true);
		wFinder = new StockFinder(wConn);
		//#CM6618
		// Cursor open
		wFinder.open();
		count = ((StockFinder)wFinder).search(searchKey);
		//#CM6619
		// Initialize
		wLength = count;
		wCurrent = 0;
	}


}
//#CM6620
//end of class
