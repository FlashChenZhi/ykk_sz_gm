package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM713060
/*
 * Created on 2004/10/07
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM713061
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * Allow this class to search for data in the listbox of Picking Date list (Picking Result). <BR>
 * Receive the search conditions as a parameter, and search through Picking Date list. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalResultDateRet(Connection conn,RetrievalSupportParameter rtParam)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter rtParam)</CODE> method and search for Result info View. <BR>
 * <BR>
 *   <Search conditions> Mandatory field item *<BR>
 *   <DIR>
 *     Consignor Code<BR>
 *     Picking Date<BR>
 *   Start Picking Date<BR>
 *   End Picking Date<BR>
 *    Item / Order flag <BR>
 *     Work type: Picking* <BR>
 *   </DIR>
 *   <Search table>  <BR>
 *   <DIR>
 *     DVRESULTVIEW <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2. Process for displaying data (<CODE>getEntities()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Obtain the data to display on the screen. <BR>
 *   1. Obtain the display info that was obtained in the search process. <BR>
 *   Set the search result in the array of the Result info and return it. <BR>
 * <BR>
 *   <Field items to be displayed> 
 *   <DIR>
 *     Picking Date<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor>
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/10/07</TD>
 * <TD>suresh kayamboo</TD>
 * <TD>New</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:06 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalResultDateRet extends SessionRet
{
	//#CM713062
	// Class fields --------------------------------------------------

	//#CM713063
	// Class variables -----------------------------------------------

	//#CM713064
	// Class method --------------------------------------------------
	//#CM713065
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:06 $");
	}

	//#CM713066
	// Constructors --------------------------------------------------
	//#CM713067
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param rtParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalResultDateRet(Connection conn, RetrievalSupportParameter rtParam) throws Exception
	{
		wConn = conn;
		find(rtParam);
	}

	//#CM713068
	// Public methods ------------------------------------------------
	//#CM713069
	/**
	 * Obtain the specified number of search results of the Result info and return it as an array of RetrievalSupportParameter. <BR>
	 * Allow this method to execute the following processes. <BR>
	 * <BR>
	 * <DIR>
	 *   1. Calculate to specify the count of display data to be obtained. <BR>
	 *   2.Obtain the Result info from the result set.<BR>
	 *   3.Obtain the display data from the Result info and set in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 *   4. Return information to be displayed. <BR>
	 * </DIR>
	 * 
	 * @return Array of <CODE>RetrievalSupportParameter</CODE> class that contains Result info.
	 */
	public Parameter[] getEntities()
	{
		RetrievalSupportParameter[] resultArray = null;
		ResultView[] resultView = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultView = (ResultView[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (RetrievalSupportParameter[]) convertToRetrievalSupportParams(resultView);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM713070
	// Package methods -----------------------------------------------

	//#CM713071
	// Protected methods ---------------------------------------------

	//#CM713072
	// Private methods -----------------------------------------------
	//#CM713073
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>ResultViewFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param rtParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 */
	private void find(RetrievalSupportParameter rtParam) throws Exception
	{
		ResultViewSearchKey sKey = new ResultViewSearchKey();
		//#CM713074
		// Set the search conditions. 
		//#CM713075
		//Consignor Code
		if (!StringUtil.isBlank(rtParam.getConsignorCode()))
		{
			sKey.setConsignorCode(rtParam.getConsignorCode());
		}
		//#CM713076
		// If the Picking Date is set for: 
		if (!StringUtil.isBlank(rtParam.getWorkDate()))
		{
			sKey.setWorkDate(rtParam.getWorkDate());
		}
		else
		{
			//#CM713077
			//Start Picking Date
			if (!StringUtil.isBlank(rtParam.getFromWorkDate()))
			{
				//#CM713078
				//Set the Start Picking Date. 
				sKey.setWorkDate(rtParam.getFromWorkDate());
			}
			if (!StringUtil.isBlank(rtParam.getToWorkDate()))
			{
				//#CM713079
				//End Picking Date
				sKey.setWorkDate(rtParam.getToWorkDate());
			}
		}
		//#CM713080
		// Distinquish between Item and Order. 
		if (!StringUtil.isBlank(rtParam.getItemOrderFlag()))
		{
			//#CM713081
			// Item 
			if (rtParam.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ITEM))
			{
				sKey.setOrderNo("", "");
			}
			//#CM713082
			// Order 
			else
			{
				sKey.setOrderNo("", "IS NOT NULL");
			}
		}
		//#CM713083
		//Set the work type to Picking (03). 
		sKey.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL);

		//#CM713084
		//Set the Conditions for obtaining data. 
		//#CM713085
		//Picking Date
		sKey.setWorkDateCollect("");
		//#CM713086
		//Grouping condition 
		sKey.setWorkDateGroup(1);

		//#CM713087
		// Set the sorting order. 
		sKey.setWorkDateOrder(1, true);

		wFinder = new ResultViewFinder(wConn);

		wFinder.open();

		int count = wFinder.search(sKey);
		wLength = count;
		wCurrent = 0;
	}

	//#CM713088
	/**
	 * Allow this class to set the ResultView Entity in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param ety Result info View 
	 * @return Parameter []  <CODE>RetrievalSupportParameter</CODE> class that has set Result info.
	 */
	private Parameter[] convertToRetrievalSupportParams(Entity[] ety)
	{
		RetrievalSupportParameter[] rtParam = null;
		ResultView[] resultView = (ResultView[]) ety;
		rtParam = new RetrievalSupportParameter[resultView.length];
		for (int i = 0; i < resultView.length; i++)
		{
			rtParam[i] = new RetrievalSupportParameter();
			if (!StringUtil.isBlank(resultView[i].getWorkDate()))
			{
				rtParam[i].setWorkDate(resultView[i].getWorkDate()); //出庫実績日
			}
		}
		return rtParam;
	}

}
//#CM713089
//end of class
