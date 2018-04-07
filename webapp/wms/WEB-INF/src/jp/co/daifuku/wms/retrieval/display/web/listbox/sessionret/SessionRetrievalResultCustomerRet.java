package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM713029
/*
 * Created on Oct 6, 2004
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
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.SystemDefine;

//#CM713030
/**  
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * Allow this class to search for data in the listbox of Customer list (Picking Result). <BR>
 * Receive the Receive search conditions as a parameter and search through Customer list. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalResultCustomerRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Result info View. <BR>
 * <BR>
 *   <Search conditions> Mandatory field item *<BR>
 *   <DIR>
 * 	   Consignor Code<BR>
 * 	   Start Planned Picking Date<BR>
 * 	   End Planned Picking Date<BR>
 *     Start Picking Date<BR>
 *     End Picking Date<BR>			
 *     Customer Code<BR>
 *     Work type: Picking *<BR>
 *    Work status:<BR>
 *     Item / Order flag <BR>
 *   </DIR>
 *   <Search table>  <BR>
 *   <DIR>
 *     DVRESULTVIEW<BR>
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
 *     Customer Code<BR>
 *     Customer Name<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/06</TD><TD>Muneendra</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:06 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalResultCustomerRet extends SessionRet
{
	//#CM713031
	// Class fields --------------------------------------------------

	//#CM713032
	// Class variables -----------------------------------------------

	//#CM713033
	// Class method --------------------------------------------------
	//#CM713034
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:06 $");
	}

	//#CM713035
	// Constructors --------------------------------------------------
	//#CM713036
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalResultCustomerRet(Connection conn, RetrievalSupportParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	//#CM713037
	// Public methods ------------------------------------------------
	//#CM713038
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
				resultArray = convertToRetrievalSupportParams(temp);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM713039
	// Package methods -----------------------------------------------

	//#CM713040
	// Protected methods ---------------------------------------------

	//#CM713041
	// Private methods -----------------------------------------------
	//#CM713042
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
		skey.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL);
		//#CM713043
		// Set the search conditions.
		//#CM713044
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}

		//#CM713045
		//Start Planned Picking Date
		if (!StringUtil.isBlank(param.getFromRetrievalPlanDate()))
		{
			skey.setPlanDate(param.getFromRetrievalPlanDate(), ">=");
		}

		//#CM713046
		//End Planned Picking Date
		if (!StringUtil.isBlank(param.getToRetrievalPlanDate()))
		{
			skey.setPlanDate(param.getToRetrievalPlanDate(), "<=");
		}

		//#CM713047
		//Start Picking Date
		if (!StringUtil.isBlank(param.getFromRetrievalDate()))
		{
			skey.setWorkDate(param.getFromRetrievalDate(), ">=");
		}

		//#CM713048
		//End Picking Date
		if (!StringUtil.isBlank(param.getToRetrievalDate()))
		{
			skey.setWorkDate(param.getToRetrievalDate(), "<=");
		}

		//#CM713049
		//Customer Code
		if (!StringUtil.isBlank(param.getCustomerCode()))
		{
			skey.setCustomerCode(param.getCustomerCode());
		}
		else
		{
			skey.setCustomerCode("", "IS NOT NULL");
		}
		//#CM713050
		// Grouping condition 
		skey.setCustomerCodeGroup(1);
		skey.setCustomerName1Group(2);
		//#CM713051
		// Set the Conditions for obtaining data. 
		skey.setCustomerCodeCollect("");
		skey.setCustomerName1Collect("");

		//#CM713052
		// Set the sorting order. 
		skey.setCustomerCodeOrder(1, true);
		skey.setCustomerName1Order(2, true);

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

		//#CM713053
		// Distinquish between Item and Order. 
		if (!StringUtil.isBlank(param.getItemOrderFlag()))
		{
			//#CM713054
			// Item 
			if (param.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ITEM))
			{
				skey.setOrderNo("", "");
			}
			//#CM713055
			// Order 
			else
			{
				skey.setOrderNo("", "IS NOT NULL");
			}
		}
		wFinder = new ResultViewFinder(wConn);
		//#CM713056
		// Open the cursor.
		wFinder.open();
		int count = ((ResultViewFinder) wFinder).search(skey);
		//#CM713057
		// Initialize. 
		wLength = count;
		wCurrent = 0;
	}

	//#CM713058
	/**
	 * Allow this class to set the ResultView Entity in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param retrievalPlan Result info View 
	 * @return RetrievalSupportParameter []  <CODE>RetrievalSupportParameter</CODE> class that has set Result info.
	 */
	private RetrievalSupportParameter[] convertToRetrievalSupportParams(ResultView[] retrievalPlan)
	{
		RetrievalSupportParameter[] stParam = null;

		stParam = new RetrievalSupportParameter[retrievalPlan.length];
		for (int i = 0; i < retrievalPlan.length; i++)
		{
			stParam[i] = new RetrievalSupportParameter();
			stParam[i].setCustomerCode(retrievalPlan[i].getCustomerCode());
			stParam[i].setCustomerName(retrievalPlan[i].getCustomerName1());

		}

		return stParam;
	}
}
//#CM713059
//end of class
