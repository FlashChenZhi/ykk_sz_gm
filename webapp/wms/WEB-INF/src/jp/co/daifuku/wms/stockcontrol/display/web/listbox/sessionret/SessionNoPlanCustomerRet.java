// $Id: SessionNoPlanCustomerRet.java,v 1.2 2006/10/04 05:12:12 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret;

//#CM6494
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
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM6495
/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * This is the class to search the data for customer list listbox (shipping result).<BR>
 * Receive search conditions as a parameter to search customer list.<BR>
 * Store the instance to the Session when you use this class. <BR>
 * Delete from the Session after using. <BR>
 * Execute following processes in this class. <BR>
 * <BR>
 * 1.Search Process(<CODE>SessionNoPlanCustomerRet(Connection conn, StockControlParameter param)</CODE>Method) <BR>
 * <DIR>
 *   Execute when listbox screen is initial/default displayed.<BR>
 *   Invoke <CODE>find (StockControlParameter param)</CODE> method and search for the target info. <BR>
 * <BR>
 *   <search conditions> Mandatory item (count)* <BR>
 *   <DIR>
 *     -Consignor code <BR>
 *     -Start picking date <BR>
 *     -End picking date <BR>
 *     -Item code <BR>
 *     -Customer code <BR>
 *     -Work division:Unplanned picking*<BR>
 *   </DIR>
 *   <Search table> <BR>
 *   <DIR>
 *     -DVRESULTVIEW <BR>
 *   </DIR>
 *   <Returned data> <BR>
 *   <DIR>
 *    -Count of the targets<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 
 * 2.Display Process(<CODE>getEntities()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Obtain the data needed to display on the screen.<BR>
 *   1.Obtain the display info that was obtained in the search process.<BR>
 *   Set the search result back to the Result data inquiry View array.<BR>
 *   <Items to be displayed><BR>
 *   <DIR>
 *     -Customer code<BR>
 *     -Customer name<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/12</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:12:12 $
 * @author  $Author: suresh $
 */
public class SessionNoPlanCustomerRet extends SessionRet
{
	//#CM6496
	// Class fields --------------------------------------------------

	//#CM6497
	// Class variables -----------------------------------------------

	//#CM6498
	// Class method --------------------------------------------------
	//#CM6499
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/04 05:12:12 $");
	}

	//#CM6500
	// Constructors --------------------------------------------------
	//#CM6501
	/**
	 * Set the search key and print the SQL statement.<BR>
	 * <DIR>
	 *   <search conditions> Mandatory item (count)* <BR>
	 *   <DIR>
	 *     -Consignor code <BR>
	 *     -Start picking date <BR>
	 *     -End picking date <BR>
	 *     -Item code <BR>
	 * 	   -Customer code <BR>
	 *     -Work division:Unplanned picking* <BR>
	 *   </DIR>
	 *   <Returned data> <BR>
	 *   <DIR>
	 *    -Count of the targets<BR>
	 *   </DIR>
	 * </DIR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter including <code>StockControlParameter</code>search result
	 * @throws Exception Announce this when any exception generates.
	 */
	public SessionNoPlanCustomerRet(Connection conn, StockControlParameter param) throws Exception
	{
		this.wConn = conn;
		
		//#CM6502
		// Search
		find(param);
	}

	//#CM6503
	// Public methods ------------------------------------------------

	//#CM6504
	/**
	 * Return the search result of <CODE> Result data inquiry View</CODE>. <BR>
	 * <DIR>
	 * <search result> <BR>
	 *   <DIR>
	 *     -Customer code<BR>
	 *     -Customer name<BR>
	 *   </DIR>
	 * </DIR>
	 * @return search result of result info View
	 */
	public ResultView[] getEntities()
	{
		ResultView[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray = (ResultView[]) ((ResultViewFinder)wFinder).getEntities(wStartpoint, wEndpoint);
			}
			catch (Exception e)
			{
				//#CM6505
				// Record the error in the log file.
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM6506
	// Package methods -----------------------------------------------

	//#CM6507
	// Protected methods ---------------------------------------------

	//#CM6508
	// Private methods -----------------------------------------------
	//#CM6509
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain searching <code>ResultViewFinder</code> as instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param param      Parameter including <code>StockControlParameter</code>search result
	 * @throws Exception Announce this when any exception generates.
	 */
	private void find(StockControlParameter param) throws Exception
	{
		int count = 0;

		ResultViewSearchKey wkey = new ResultViewSearchKey();
		//#CM6510
		// Execute Search
		if (!StringUtil.isBlank(param.getConsignorCode()))
			wkey.setConsignorCode(param.getConsignorCode());
		//#CM6511
		// Start picking date
		if (!StringUtil.isBlank(param.getFromWorkDate()))
			wkey.setWorkDate(param.getFromWorkDate(), ">=");
		//#CM6512
		// End picking date
		if (!StringUtil.isBlank(param.getToWorkDate()))
			wkey.setWorkDate(param.getToWorkDate(), "<=");
		//#CM6513
		// Item code
		if (!StringUtil.isBlank(param.getItemCode()))
			wkey.setItemCode(param.getItemCode());			
		//#CM6514
		// Customer code
		if (!StringUtil.isBlank(param.getCustomerCode()))
		{
			wkey.setCustomerCode(param.getCustomerCode());
		}
		else
		{
			wkey.setCustomerCode("", "IS NOT NULL");
		}
		//#CM6515
		// Work division(Unplanned picking)
		wkey.setJobType(SystemDefine.JOB_TYPE_EX_RETRIEVAL);

		wkey.setCustomerCodeGroup(1);
		wkey.setCustomerName1Group(2);
		wkey.setCustomerCodeCollect("");
		wkey.setCustomerName1Collect("");
		wkey.setCustomerCodeOrder(1, true);
		wkey.setCustomerName1Order(2, true);
		wFinder = new ResultViewFinder(wConn);
		//#CM6516
		// Cursor open
		wFinder.open();
		count = ((ResultViewFinder)wFinder).search(wkey);
		//#CM6517
		// Initialize
		wLength = count;
		wCurrent = 0;
	}
}
//#CM6518
//end of class
