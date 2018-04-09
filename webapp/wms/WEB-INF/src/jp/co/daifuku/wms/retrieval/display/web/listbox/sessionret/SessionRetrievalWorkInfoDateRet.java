package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM713198
/*
 * Created on 2004/10/06
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
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM713199
/** 
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * Allow this class to search for data for the listbox of Planned Picking Date list (Picking Work). <BR>
 * Receive the search conditions as a parameter, and search through Planned Picking Date list. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalWorkInfoDateRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Work Status Info. <BR>
 * <BR>
 *   <Search conditions> Mandatory field item *<BR>
 *   <DIR>
 *     Consignor Code<BR>
 *     Planned Picking Date<BR>
 *     Start Planned Picking Date<BR>
 *     End Planned Picking Date<BR>
 *    Work status:
 *     Item / Order flag <BR>
 *     System identification key <BR>
 *     Work type: Picking(03)*<BR>
 *   </DIR>
 *   <Search table>  <BR>
 *   <DIR>
 *     DNWORKINFO <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2. Process for displaying data (<CODE>getEntities()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Obtain the data to display on the screen. <BR>
 *   1. Obtain the display info that was obtained in the search process. <BR>
 *   Set the search result in the array of the Work Status Info and return it.<BR>
 * <BR>
 *   <Field items to be displayed> 
 *   <DIR>
 *     Planned Picking Date<BR>
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
 * <TD>2004/10/28</TD>
 * <TD>Muneendra</TD>
 * <TD>New</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:08 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalWorkInfoDateRet extends SessionRet
{
	//#CM713200
	// Class fields --------------------------------------------------

	//#CM713201
	// Class variables -----------------------------------------------

	//#CM713202
	// Class method --------------------------------------------------
	//#CM713203
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:08 $");
	}

	//#CM713204
	// Constructors --------------------------------------------------
	//#CM713205
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalWorkInfoDateRet(Connection conn, RetrievalSupportParameter rtParam) throws Exception
	{
		wConn = conn;
		find(rtParam);
	}

	//#CM713206
	// Public methods ------------------------------------------------
	//#CM713207
	/**
	 * Obtain the specified number of search results of the Work Status Info and return it as an array of RetrievalSupportParameter. <BR>
	 * Allow this method to execute the following processes. <BR>
	 * <BR>
	 * <DIR>
	 *   1. Calculate to specify the count of display data to be obtained. <BR>
	 *   2.Obtain the Work Status Info from the result set.<BR>
	 *   3.Obtain the display data from the Work Status Info, and set it in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 *   4. Return information to be displayed. <BR>
	 * </DIR>
	 * 
	 * @return Array of <CODE>RetrievalSupportParameter</CODE> class that contains Work Status.
	 */
	public Parameter[] getEntities()
	{
		RetrievalSupportParameter[] resultArray = null;
		WorkingInformation[] workInfo = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				workInfo = (WorkingInformation[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (RetrievalSupportParameter[]) convertToRetrievalSupportParams(workInfo);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM713208
	// Package methods -----------------------------------------------

	//#CM713209
	// Protected methods ---------------------------------------------

	//#CM713210
	// Private methods -----------------------------------------------
	//#CM713211
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>WorkingInformationFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter rtParam) throws Exception
	{
		WorkingInformationSearchKey sKey = new WorkingInformationSearchKey();
		sKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM713212
		// Set the search conditions. 
		//#CM713213
		//Consignor Code
		if (!StringUtil.isBlank(rtParam.getConsignorCode()))
		{
			sKey.setConsignorCode(rtParam.getConsignorCode());
		}

		//#CM713214
		// If the Planned Picking Date is set for: 
		if (!StringUtil.isBlank(rtParam.getRetrievalPlanDate()))
		{
			sKey.setPlanDate(rtParam.getRetrievalPlanDate());
		}
		else
		{
			//#CM713215
			//Start Planned Picking Date
			if (!StringUtil.isBlank(rtParam.getFromRetrievalPlanDate()))
			{
				//#CM713216
				//Set the Start Planned Picking Date. 
				sKey.setPlanDate(rtParam.getFromRetrievalPlanDate());
			}
			if (!StringUtil.isBlank(rtParam.getToRetrievalPlanDate()))
			{
				//#CM713217
				//End Planned Picking Date
				sKey.setPlanDate(rtParam.getToRetrievalPlanDate());
			}
		}

		if (rtParam.getSearchStatus() != null && rtParam.getSearchStatus().length > 0)
		{
			String[] search = new String[rtParam.getSearchStatus().length];
			for (int i = 0; i < rtParam.getSearchStatus().length; ++i)
			{
				if (rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = WorkingInformation.STATUS_FLAG_UNSTART;
				}
				else if (rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
				{
					search[i] = WorkingInformation.STATUS_FLAG_START;
				}
				else if (rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
				{
					search[i] = WorkingInformation.STATUS_FLAG_NOWWORKING;
				}
				else if (rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if (rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = WorkingInformation.STATUS_FLAG_COMPLETION;
				}
				else
				{
					search[i] = "*";
				}
			}
			sKey.setStatusFlag(search);
		}
		else
		{
			sKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		}
		//#CM713218
		// Distinquish between Item and Order. 
		if (!StringUtil.isBlank(rtParam.getItemOrderFlag()))
		{
			//#CM713219
			// Item 
			if (rtParam.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ITEM))
			{
				sKey.setOrderNo("", "");
			}
			//#CM713220
			// Order 
			else
			{
				sKey.setOrderNo("", "IS NOT NULL");
			}
		}
		
		//#CM713221
		// If the Area type is other than ASRS Work, search for works other than ASRS Work and exclude also the data that evdenced the Shortage 
		if (RetrievalSupportParameter.AREA_TYPE_FLAG_NOASRS.equals(rtParam.getAreaTypeFlag()))
		{
		    sKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");
		    sKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		}

		//#CM713222
		// For data with the allocation flag Allocated, search for the allocated data only. 
		if (RetrievalSupportParameter.ALLOCATION_COMPLETION.equals(rtParam.getAllocationFlag()))
		{
			//#CM713223
			// System identification key: any key other than AS/RS(2) and Status flag: Standby(0),  OR
			sKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=", "((", "", "AND");
			sKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "", ")", "OR");
			//#CM713224
			// System identification key: AS/RS(2) and Status flag: Working. (2) 
			sKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "=", "(", "", "AND");
			sKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "", "))", "AND");
		}

		//#CM713225
		//Set it for the Conditions for obtaining data. 
		//#CM713226
		//Planned Picking Date
		sKey.setPlanDateCollect("");
		//#CM713227
		//Set it for the grouping condition. 
		sKey.setPlanDateGroup(1);
		//#CM713228
		// Set it in Sorting order. 
		sKey.setPlanDateOrder(1, true);

		wFinder = new WorkingInformationFinder(wConn);

		wFinder.open();
		int count = wFinder.search(sKey);
		wLength = count;
		wCurrent = 0;

	}

	//#CM713229
	/**
	 * Allow this class to set the Workinginformation Entity in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param ety Work Status Info
	 * @return Parameter []  <CODE>RetrievalSupportParameter</CODE> class that has set Work Status.
	 */
	private Parameter[] convertToRetrievalSupportParams(Entity[] ety)
	{
		RetrievalSupportParameter[] rtParam = null;
		WorkingInformation[] retrievalPlan = (WorkingInformation[]) ety;
		if ((retrievalPlan != null) && (retrievalPlan.length != 0))
		{
			rtParam = new RetrievalSupportParameter[retrievalPlan.length];
			for (int i = 0; i < retrievalPlan.length; i++)
			{
				rtParam[i] = new RetrievalSupportParameter();
				if ((retrievalPlan[i].getPlanDate() != null) && !retrievalPlan[i].getPlanDate().equals(""))
				{
					rtParam[i].setRetrievalPlanDate(retrievalPlan[i].getPlanDate()); //出庫予定日
				}
			}
		}
		return rtParam;

	}

}
//#CM713230
//end of class
