// $Id: SessionNoPlanConsignorRet.java,v 1.2 2006/10/04 05:12:12 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret;

//#CM6472
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

//#CM6473
/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * This is the class to search and display Consignor list from result View.<BR>
 * Store the instance to the Session when you use this class.
 * Delete from the Session after using. <BR>
 * Execute following processes in this class. <BR>
 * <BR>
 * 1.Search Process(<CODE>SessionNoPlanConsignorRet(Connection conn, StockControlParameter param)</CODE>Method) <BR>
 * <DIR>
 *   Execute when listbox screen is initial/default displayed.<BR>
 *   Invoke <CODE>find(StockControlParameter param)</CODE>Method and execute search of result View.<BR>
 * <BR>
 *  <DIR>
 *   <search conditions> <BR>
 *   <DIR>
 *     Consignor code <BR>
 *   </DIR>
 *   <Search table> <BR>
 *   <DIR>
 *     DVRESULTVIEW <BR>
 *   </DIR>
 *   <<Items to be extracted>> <BR>
 *   <DIR>
 *       Consignor code <BR>
 *       Consignor name <BR>
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
 *   Set the search result back to the Result data inquiry array.<BR>
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
 * <TR><TD>2005/04/12</TD><TD>T.Yoshino</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:12:12 $
 * @author  $Author: suresh $
 */
public class SessionNoPlanConsignorRet extends SessionRet
{
	//#CM6474
	// Class fields --------------------------------------------------

	//#CM6475
	// Class variables -----------------------------------------------

	//#CM6476
	// Class method --------------------------------------------------
	//#CM6477
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $, $Date: 2006/10/04 05:12:12 $");
	}

	//#CM6478
	// Constructors --------------------------------------------------
	//#CM6479
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
	public SessionNoPlanConsignorRet(Connection conn, StockControlParameter param) throws Exception
	{
		this.wConn = conn;
		
		//#CM6480
		// Search
		find(param);
	}

	//#CM6481
	// Public methods ------------------------------------------------
	//#CM6482
	/**
	 * Return the designated number of resurch results of the Result data inquiry<CODE> (DvResultView)</CODE>.<BR>
	 * <BR>
	 * <DIR>
	 * <search result> <BR>
	 * <DIR>
	 * Consignor code <BR>
	 * Consignor name <BR>
	 * </DIR>
	 * </DIR>
	 * @return search result sequence of Result View<CODE>(DvResultView)</CODE>
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
				//#CM6483
				// Record the error in the log file.
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM6484
	// Package methods -----------------------------------------------

	//#CM6485
	// Protected methods ---------------------------------------------

	//#CM6486
	// Private methods -----------------------------------------------
	//#CM6487
	/** 
	 * Print the SQL statement based on the input parameter and search through the Result View <CODE> (DVRESULTVIEW)</CODE>. <BR>
	 * Maintain the <code>StockFinder</code> for searching as an instance variable. <BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param param      Parameter including <CODE>StockControlParameter</CODE>search result
	 * @throws Exception Announce this when any exception generates.
	 */
	private void find(StockControlParameter param) throws Exception
	{
		int count = 0;

		ResultViewSearchKey wkey = new ResultViewSearchKey();
		//#CM6488
		// Execute Search
		//#CM6489
		// Consignor code 
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			wkey.setConsignorCode(param.getConsignorCode());
		}

		wkey.setConsignorCodeGroup(1);
		wkey.setConsignorNameGroup(2);
		wkey.setConsignorCodeCollect("");
		wkey.setConsignorNameCollect("");
		wkey.setConsignorCodeOrder(1, true);
		wkey.setConsignorNameOrder(2, true);
		//#CM6490
		// Work division
		if(param.getSearchFlag().equals(StockControlParameter.SEARCHFLAG_EX_STORAGE))
		{
			wkey.setJobType(ResultView.JOB_TYPE_EX_STORAGE);
		}
		else if(param.getSearchFlag().equals(StockControlParameter.SEARCHFLAG_EX_RETRIEVAL))
		{
			wkey.setJobType(ResultView.JOB_TYPE_EX_RETRIEVAL);
		}

		wFinder = new ResultViewFinder(wConn);
		//#CM6491
		// Cursor open
		wFinder.open();
		count = ((ResultViewFinder)wFinder).search(wkey);
		//#CM6492
		// Initialize
		wLength = count;
		wCurrent = 0;
	}


}
//#CM6493
//end of class
