// $Id: SessionStockConsignorRet.java,v 1.2 2006/10/04 05:12:11 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret;

//#CM6566
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

//#CM6567
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * This is the class to search and display Consignor list from Stock info.<BR>
 * Store the instance to the Session when you use this class.
 * Delete from the Session after using. <BR>
 * Execute following processes in this class. <BR>
 * <BR>
 * 1.Search Process(<CODE>SessionStockConsignorRet(Connection conn, StockControlParameter param)</CODE>Method) <BR>
 * <DIR>
 *   Execute when listbox screen is initial/default displayed.<BR>
 *   Invoke <CODE>find (ShippingParameter param)</CODE> method and search for Stock info. <BR>
 * <BR>
 *  <DIR>
 *   <search conditions> <BR>
 *   <DIR>
 *     Consignor code <BR>
 *     Stock status is in Center Inventory<BR>
 *     stock qty is 1 or larger <BR>
 *   </DIR>
 *   <Search table> <BR>
 *   <DIR>
 *     DMSTOCK <BR>
 *   </DIR>
 *   <<Items to be extracted>> <BR>
 *   <DIR>
 *     Consignor code <BR>
 *     Consignor name <BR>
 *   </DIR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.Display Process(<CODE>getEntities()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *   Obtain the data needed to display on the screen. <BR>
 * <BR>
 *   1.Obtain the display info that was obtained in the search process. <BR>
 *   Set the search result back to the inventory information array. <BR>
 * <BR>
 *   <Items to be displayed>
 *   <DIR>
 *     Consignor code <BR>
 *     Consignor name <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/14</TD><TD>K.Toda</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:12:11 $
 * @author  $Author: suresh $
 */
public class SessionStockConsignorRet extends SessionRet
{
	//#CM6568
	// Class fields --------------------------------------------------

	//#CM6569
	// Class variables -----------------------------------------------

	//#CM6570
	// Class method --------------------------------------------------
	//#CM6571
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $, $Date: 2006/10/04 05:12:11 $");
	}

	//#CM6572
	// Constructors --------------------------------------------------
	//#CM6573
	/**
	 * Invoke <CODE>find(StockControlParameter param)</CODE> Method to execute search. <BR>
	 * Invoke the count of obtained data by <CODE>find (StockControlParameter param)</CODE> method. <BR>
	 *  Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * <BR>
	 * <DIR>
	 * <search conditions> <BR>
	 *   <DIR>
	 *   Consignor code <BR>
	 *   </DIR>
	 * </DIR>
	 * @param conn       <CODE>Connection</CODE>
	 * @param param      Parameter including <CODE>StockControlParameter</CODE>search result
	 * @throws Exception Announce this when any exception generates.
	 */
	public SessionStockConsignorRet(Connection conn, StockControlParameter param) throws Exception
	{
		this.wConn = conn;
		
		//#CM6574
		// Search
		find(param);
	}

	//#CM6575
	// Public methods ------------------------------------------------
	//#CM6576
	/**
	 * Return the designated number of resurch results of the stock info <CODE> (DMSTOCK)</CODE>.<BR>
	 * <BR>
	 * <DIR>
	 * <search result> <BR>
	 * <DIR>
	 * Consignor code <BR>
	 * Consignor name <BR>
	 * </DIR>
	 * </DIR>
	 * @return search result sequence of Stock info<CODE>(Dmstock)</CODE>
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
				//#CM6577
				// Record the error in the log file.
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM6578
	// Package methods -----------------------------------------------

	//#CM6579
	// Protected methods ---------------------------------------------

	//#CM6580
	// Private methods -----------------------------------------------
	//#CM6581
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

		StockSearchKey wkey = new StockSearchKey();
		//#CM6582
		// Execute Search
		//#CM6583
		// Consignor code 
		if (!StringUtil.isBlank(param.getConsignorCode()))
			wkey.setConsignorCode(param.getConsignorCode());

		wkey.setConsignorCodeGroup(1);
		wkey.setConsignorNameGroup(2);
		wkey.setConsignorCodeCollect("");
		wkey.setConsignorNameCollect("");
		wkey.setConsignorCodeOrder(1, true);
		wkey.setConsignorNameOrder(2, true);
		
		//#CM6584
		// Center Inventory
		wkey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		wkey.setStockQty(1, ">=");

		if (StockControlParameter.AREA_TYPE_FLAG_NOASRS.equals(param.getAreaTypeFlag()))
		{
			AreaOperator AreaOperator = new AreaOperator(wConn);
			
			String[] areaNo = null;
			int[] areaType = new int[2];
			areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
			areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
			
			//#CM6585
			// Obtain the Area other than ASRS and add it to the search conditions.
			//#CM6586
			// Search IS NULL if no corresponding area found.
			areaNo = AreaOperator.getAreaNo(areaType);
			wkey.setAreaNo(areaNo);
		}

		wFinder = new StockFinder(wConn);
		//#CM6587
		// Cursor open
		wFinder.open();
		count = ((StockFinder)wFinder).search(wkey);
		//#CM6588
		// Initialize
		wLength = count;
		wCurrent = 0;
	}


}
//#CM6589
//end of class
