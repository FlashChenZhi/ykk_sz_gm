// $Id: SessionNoPlanDateRet.java,v 1.2 2006/10/04 05:12:12 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret;

//#CM6519
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


//#CM6520
/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * This is the class to search the data for the work date list listbox search process.<BR>
 * Receive search conditions as a parameter to search the work date list.<BR>
 * Store the instance to the Session when you use this class.
 * Delete from the Session after using.<BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * 1.Search Process(<CODE>SessionNoPlanDateRet(Connection conn, StockControlParameter param)</CODE>Method)<BR>
 * <DIR>
 *   Execute when listbox screen is initial/default displayed.<BR>
 *   Invoke <CODE>find (StockControlParameter param)</CODE> method and search for the Result data inquiry View.<BR>
 * <BR>
 *   <search conditions> Mandatory item (count)*<BR>
 *   <DIR>
 *     -Consignor code<BR>
 *     -Work Date<BR>
 *     -Work division:Unplanned storage, Unplanned picking<BR>
 *   </DIR>
 *   <Search table> <BR>
 *   <DIR>
 *     -DVRESULTVIEW <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.Display Process(<CODE>getEntities()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Execute when listbox screen is initial/default displayed.<BR>
 *   Obtain the data needed to display on the screen.<BR>
 *   1.Obtain the display info that was obtained in the search process.<BR>
 *   Set the search result back to <CODE>StockControlParameter</CODE> array.<BR>
 * <BR>
 *   <Items to be displayed>
 *   <DIR>
 *     -Work Date<BR>
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
public class SessionNoPlanDateRet extends SessionRet
{
	//#CM6521
	// Class fields --------------------------------------------------

	//#CM6522
	// Class variables -----------------------------------------------

	//#CM6523
	// Class method --------------------------------------------------
	//#CM6524
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/04 05:12:12 $");
	}

	//#CM6525
	// Constructors --------------------------------------------------
	//#CM6526
	/**
	 * Invoke <CODE>find(StockControlParameter param)</CODE> Method to execute search.<BR>
	 * Invoke the count of obtained data by <CODE>find (StockControlParameter param)</CODE> method.<BR>
	 *  Require to invoke the <CODE>getEntities</CODE> method to obtain the search result.<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter including <code>StockControlParameter</code>search result
	 * @throws Exception Announce this when any exception generates.
	 */
	public SessionNoPlanDateRet(Connection conn, StockControlParameter param) throws Exception
	{
		this.wConn = conn;

		//#CM6527
		// Search
		find(param);
	}

	//#CM6528
	// Public methods ------------------------------------------------
	//#CM6529
	/**
	 * Return the designated number of <CODE>DvResultView</CODE> search results.<BR>
	 * <BR>
	 * <search result><BR>
	 * <DIR>
	 * -Work Date<BR>
	 * </DIR>
	 * @return search result of DvResultView
	 */
	public ResultView[] getEntities()
	{
		ResultView[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray = (ResultView[]) ((ResultViewFinder) wFinder).getEntities(wStartpoint, wEndpoint);
			}
			catch (Exception e)
			{
				//#CM6530
				// 6006002=Database error occurred.{0}{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM6531
	// Package methods -----------------------------------------------

	//#CM6532
	// Protected methods ---------------------------------------------

	//#CM6533
	// Private methods -----------------------------------------------
	//#CM6534
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

		//#CM6535
		// Consignor code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		
		if (!StringUtil.isBlank(param.getFromWorkDate()))
		{
			//#CM6536
			//Start Work Date
			skey.setWorkDate(param.getFromWorkDate());
		}
		
		if (!StringUtil.isBlank(param.getToWorkDate()))
		{
			//#CM6537
			//End Work Date
			skey.setWorkDate(param.getToWorkDate());
		}

		skey.setWorkDateGroup(1);
		skey.setWorkDateCollect("");
		skey.setWorkDateOrder(1, true);

		//#CM6538
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
		//#CM6539
		// Cursor open
		wFinder.open();
		count = ((ResultViewFinder) wFinder).search(skey);
		//#CM6540
		// Initialize
		wLength = count;
		wCurrent = 0;
	}
}
//#CM6541
//end of class
