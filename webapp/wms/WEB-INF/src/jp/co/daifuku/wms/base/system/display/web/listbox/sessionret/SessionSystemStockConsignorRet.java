// $Id: SessionSystemStockConsignorRet.java,v 1.2 2006/11/13 08:21:01 suresh Exp $

//#CM693299
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.listbox.sessionret;
import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM693300
/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * Allow this class to search for the Use this class to search a Consignor list through the inventory information and display it. <BR>
 * Maintain the instance in the session to use this class.
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionSystemStockConsignorRet(Connection conn, SystemControlParameter param)</CODE>method) <BR>
 * <DIR>
 *   Execute this when displaying the listbox screen as default. <BR>
 *   Invoke the <CODE>find(SystemParameter param)</CODE> method and search for the inventory information . <BR>
 * <BR>
 *  <DIR>
 *   <Search conditions> <BR>
 *   <DIR>
 *     Consignor Code <BR>
 *   </DIR>
 *   <Search table> <BR>
 *   <DIR>
 *     DMSTOCK <BR>
 *   </DIR>
 *   <Field items to be extracted> <BR>
 *   <DIR>
 *       Consignor Code <BR>
 *       Consignor Name <BR>
 *       <BR>
 *       Data with inventory status "Center Inventory" <BR>
 *       Stock qty is 1 or larger. <BR>
 *   </DIR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.Process for displaying(<CODE>getEntities()</CODE>method) <BR>
 * <BR>
 * <DIR>
 *   Obtain the data to display on the screen. <BR>
 * <BR>
 *   1.Obtain the display info that was obtained in the search process. <BR>
 *   Set the search result in the inventory information array and return it. <BR>
 * <BR>
 *   <Field items to displayed>
 *   <DIR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/14</TD><TD>K.Toda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:01 $
 * @author  $Author: suresh $
 */
public class SessionSystemStockConsignorRet extends SessionRet
{
	//#CM693301
	// Class fields --------------------------------------------------

	//#CM693302
	// Class variables -----------------------------------------------

	//#CM693303
	// Class method --------------------------------------------------
	//#CM693304
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $, $Date: 2006/11/13 08:21:01 $");
	}

	//#CM693305
	// Constructors --------------------------------------------------
	//#CM693306
	/**
	 * Invoke the <CODE>find(SystemParameter param)</CODE> method for searching. <BR>
	 * Allow the <CODE>find(StockControlParameter param)</CODE> method to set the count of obtained data. <BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * <BR>
	 * <DIR>
	 * <Search conditions> <BR>
	 *   <DIR>
	 *   Consignor Code <BR>
	 *   </DIR>
	 * </DIR>
	 * @param conn       <CODE>Connection</CODE>
	 * @param param      Parameter that contains the <CODE>StockControlParameter</CODE> search result.
	 * @throws Exception Announce when some error occurs during loading data.
	 */
	public SessionSystemStockConsignorRet(Connection conn, SystemParameter param) throws Exception
	{
		this.wConn = conn;
		
		//#CM693307
		// Search
		find(param);
	}

	//#CM693308
	// Public methods ------------------------------------------------
	//#CM693309
	/**
	 * Return the designated number of the search results of the inventory information <CODE> (DMSTOCK)</CODE>.<BR>
	 * <BR>
	 * <DIR>
	 * <Search result> <BR>
	 * <DIR>
	 * Consignor Code <BR>
	 * Consignor Name <BR>
	 * </DIR>
	 * </DIR>
	 * @return Array of search results of inventory information <CODE>(DmStock)</CODE>.
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
				//#CM693310
				//Record the error in the log file.
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM693311
	// Package methods -----------------------------------------------

	//#CM693312
	// Protected methods ---------------------------------------------

	//#CM693313
	// Private methods -----------------------------------------------
	//#CM693314
	/** 
	 * Print the SQL statement based on the input parameter and search for the inventory information <CODE> (Dmstock)</CODE>. <BR>
	 * Maintain the <code>StockFinder</code> for searching as an instance variable. <BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param param      Parameter that contains the <code>SystemParameter</code> search result.
	 * @throws Exception Announce when some error occurs during loading data.
	 */
	private void find(SystemParameter param) throws Exception
	{
		int count = 0;

		StockSearchKey wkey = new StockSearchKey();
		//#CM693315
		// Execute searching.
		//#CM693316
		// Consignor Code 
		if (!StringUtil.isBlank(param.getConsignorCode()))
			wkey.setConsignorCode(param.getConsignorCode());

		wkey.setConsignorCodeGroup(1);
		wkey.setConsignorNameGroup(2);
		wkey.setConsignorCodeCollect("");
		wkey.setConsignorNameCollect("");
		wkey.setConsignorCodeOrder(1, true);
		wkey.setConsignorNameOrder(2, true);
		
		//#CM693317
		// Center Inventory
		wkey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM693318
		// Stock Qty is one or larger.
		wkey.setStockQty(1, ">=");

		wFinder = new StockFinder(wConn);
		//#CM693319
		// Open the cursor.
		wFinder.open();
		count = ((StockFinder)wFinder).search(wkey);
		//#CM693320
		// Initialize.
		wLength = count;
		wCurrent = 0;
	}

}
//#CM693321
//end of class
