// $Id: SessionNoPlanItemRet.java,v 1.2 2006/10/04 05:12:11 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret;

//#CM6542
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
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM6543
/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * This is the class to search and display item list from Stock info.<BR>
 * Store the instance to the Session when you use this class. Delete from the Session after using.<BR> 
 * Execute following processes in this class.<BR> 
 * <BR>
 * 1.Search Process(<CODE>SessionStockItemRet(Connection conn, StockControlParameter param)</CODE>Method) <BR>
 * <DIR>
 *   Execute when listbox screen is initial/default displayed.<BR>
 *   Invoke <CODE>find (StockControlParameter param)</CODE> method and search for the Stock info. <BR>
 * <BR>
 *  <DIR>
 *   [search conditions] <BR>
 *   <DIR>
 *     Consignor code <BR>
 *     Start Work Date <BR>
 *     End Work Date <BR>
 *     Item code <BR>
 * 	   Work division: Unplanned storage, Unplanned picking <BR>
 *   </DIR>
 *   [Search table] <BR>
 * 	   DVRESULTVIEW <BR>
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
 * <TR><TD>2005/04/12</TD><TD>T.Yoshino</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:12:11 $
 * @author  $Author: suresh $
 */
public class SessionNoPlanItemRet extends SessionRet
{
	//#CM6544
	// Class fields --------------------------------------------------

	//#CM6545
	// Class variables -----------------------------------------------

	//#CM6546
	// Class method --------------------------------------------------
	//#CM6547
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/04 05:12:11 $");
	}

	//#CM6548
	// Constructors --------------------------------------------------
	//#CM6549
	/**
	 * Invoke <CODE>find(StockControlParameter param)</CODE> Method to execute search. <BR>
	 * Invoke the count of obtained data by <CODE>find (StockControlParameter param)</CODE> method. <BR>
	 *  Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * <BR>
	 * <DIR>
	 * [search conditions] <BR>
	 * <DIR>
	 * Consignor code <BR>
	 * Start Work Date <BR>
	 * End Work Date <BR>
	 * Item code <BR>
	 * Work division   <BR>
	 * </DIR>
	 * </DIR>
	 * @param conn       <CODE>Connection</CODE>
	 * @param param      Parameter including <CODE>StockControlParameter</CODE>search result
	 * @throws Exception Announce this when any exception generates.
	 */
	public SessionNoPlanItemRet(Connection conn,	StockControlParameter param) throws Exception
	{
		this.wConn = conn;
		
		//#CM6550
		// Search
		find(param);
	}

	//#CM6551
	// Public methods ------------------------------------------------
	//#CM6552
	/**
	 * Return the designated number of resurch results of the Result data inquiry<CODE> (DvResultView)</CODE>.<BR>
	 * <BR>
	 * <DIR>
	 * [search result] <BR>
	 * <DIR>
	 * Item code <BR>
	 * Item name <BR>
	 * </DIR>
	 * </DIR>
	 * @return search result sequence of result info <CODE>(DVRESULTVIEW)</CODE>
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
				//#CM6553
				// Record the error in the log file.
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM6554
	// Package methods -----------------------------------------------

	//#CM6555
	// Protected methods ---------------------------------------------

	//#CM6556
	// Private methods -----------------------------------------------
	//#CM6557
	/**
	 * Print the SQL statement based on the input parameter.<BR>
	 * Maintain the <code>StockFinder</code> for searching as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result.<BR>
	 * @param param      <code>StockControlParameter</code> including search result
	 * @throws Exception Announce this when any exception generates.
	 */
	private void find(StockControlParameter param) throws Exception
	{
		int count = 0;

		ResultViewSearchKey skey = new ResultViewSearchKey();

		//#CM6558
		// Consignor code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		//#CM6559
		// Start picking date
		if (!StringUtil.isBlank(param.getFromWorkDate()))
		{
			skey.setWorkDate(param.getFromWorkDate(), ">=");
		}
		//#CM6560
		// End picking date
		if (!StringUtil.isBlank(param.getToWorkDate()))
		{
			skey.setWorkDate(param.getToWorkDate(), "<=");
		}
		//#CM6561
		// Item code
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			skey.setItemCode(param.getItemCode());			
		}
		
		skey.setItemCodeGroup(1);
		skey.setItemName1Group(2);
		skey.setItemCodeCollect("");
		skey.setItemName1Collect("");
		skey.setItemCodeOrder(1, true);
		skey.setItemName1Order(2, true);
		
		//#CM6562
		// Work division
		if(param.getSearchFlag().equals(StockControlParameter.SEARCHFLAG_EX_STORAGE))
		{
			skey.setJobType(ResultView.JOB_TYPE_EX_STORAGE);
		}
		else if(param.getSearchFlag().equals(StockControlParameter.SEARCHFLAG_EX_RETRIEVAL))
		{
			skey.setJobType(ResultView.JOB_TYPE_EX_RETRIEVAL);
		}
		wFinder = new ResultViewFinder(wConn);
		//#CM6563
		// Cursor open
		wFinder.open();
		count = ((ResultViewFinder) wFinder).search(skey);
		//#CM6564
		// Initialize
		wLength = count;
		wCurrent = 0;
	}
}
//#CM6565
//end of class
