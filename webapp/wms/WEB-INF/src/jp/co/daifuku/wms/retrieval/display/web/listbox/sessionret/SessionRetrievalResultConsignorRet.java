package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM713003
/*
 * Created on Oct 5, 2004
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
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM713004
/**
 * 
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * Allow this class to search for data in the listbox of Consignor list (Picking Result). <BR>
 * Receive the search conditions as a parameter, and search through Consignor list. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalResultConsignorRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Result info View. <BR>
 * <BR>
 *   <Search conditions> Mandatory field item *<BR>
 *   <DIR>
 *     Consignor Code<BR>
 *     Work type: Picking* <BR>
 *    Work status:<BR>
 *    Item / Order flag <BR>
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
 *     Consignor Code<BR>
 *     Consignor Name<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2008/10/05</TD><TD>Muneendra</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:06 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalResultConsignorRet extends SessionRet
{
	//#CM713005
	// Class fields --------------------------------------------------

	//#CM713006
	// Class variables -----------------------------------------------

	//#CM713007
	// Class method --------------------------------------------------
	//#CM713008
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:06 $");
	}

	//#CM713009
	// Constructors --------------------------------------------------
	//#CM713010
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalResultConsignorRet(Connection conn, RetrievalSupportParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	//#CM713011
	// Public methods ------------------------------------------------
	//#CM713012
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
		ResultView temp[] = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				temp = (ResultView[]) ((ResultViewFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				resultArray = convertToretrievalSupportParams(temp);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;

	}

	//#CM713013
	// Package methods -----------------------------------------------

	//#CM713014
	// Protected methods ---------------------------------------------

	//#CM713015
	// Private methods -----------------------------------------------
	//#CM713016
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>ResultViewFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter param) throws Exception
	{
		ResultViewSearchKey skey = new ResultViewSearchKey();
		//#CM713017
		// Execute searching.
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		else
		{
			skey.setConsignorCode("", "IS NOT NULL");
		}

		//#CM713018
		// Set the work type to Picking. 
		skey.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL);
		//#CM713019
		// Grouping condition 
		skey.setConsignorCodeGroup(1);
		skey.setConsignorNameGroup(2);
		//#CM713020
		// Set the Conditions for obtaining data. 
		skey.setConsignorCodeCollect("");
		skey.setConsignorNameCollect("");

		//#CM713021
		// Set the sorting order. 
		skey.setConsignorCodeOrder(1, true);
		skey.setConsignorNameOrder(2, true);

		if (param.getSearchStatus() != null && param.getSearchStatus().length > 0)
		{
			String[] search = new String[param.getSearchStatus().length];
			for (int i = 0; i < param.getSearchStatus().length; ++i)
			{
				if (param.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = ResultView.STATUS_FLAG_UNSTART;
				}
				else if (param.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
				{
					search[i] = ResultView.STATUS_FLAG_START;
				}
				else if (param.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
				{
					search[i] = ResultView.STATUS_FLAG_NOWWORKING;
				}
				else if (param.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = ResultView.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if (param.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = ResultView.STATUS_FLAG_COMPLETION;
				}
				else
				{
					search[i] = "*";
				}
			}
			skey.setStatusFlag(search);
		}
		else
		{
			skey.setStatusFlag(ResultView.STATUS_FLAG_DELETE, "!=");
		}
		//#CM713022
		// Distinquish between Item and Order. 
		if (!StringUtil.isBlank(param.getItemOrderFlag()))
		{
			//#CM713023
			// Item 
			if (param.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ITEM))
			{
				skey.setOrderNo("", "");
			}
			//#CM713024
			// Order 
			else
			{
				skey.setOrderNo("", "IS NOT NULL");
			}
		}
		wFinder = new ResultViewFinder(wConn);
		//#CM713025
		// Open the cursor.
		wFinder.open();
		int count = ((ResultViewFinder) wFinder).search(skey);
		//#CM713026
		// Initialize. 
		wLength = count;
		wCurrent = 0;

	}

	//#CM713027
	/**
	 * Allow this class to set the ResultView Entity in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param resultView Result info View 
	 * @return RetrievalSupportParameter []  <CODE>RetrievalSupportParameter</CODE> class that has set Result info.
	 */
	private RetrievalSupportParameter[] convertToretrievalSupportParams(ResultView[] resultView)
	{
		RetrievalSupportParameter[] stParam = null;

		stParam = new RetrievalSupportParameter[resultView.length];
		for (int i = 0; i < resultView.length; ++i)
		{
			stParam[i] = new RetrievalSupportParameter();
			stParam[i].setConsignorCode(resultView[i].getConsignorCode());
			stParam[i].setConsignorName(resultView[i].getConsignorName());
		}

		return stParam;

	}

}
//#CM713028
//end of class
