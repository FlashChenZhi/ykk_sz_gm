package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM712066
/*
 * Created on 2004/10/13
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
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM712067
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * Allow this class to Search through the Picking Plan Info and display it in the Order No. Search listbox. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalCaseOrdernoRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Picking Plan Info. <BR>
 * <BR>
 *   <Search conditions>Mandatory field item *<BR>
 *   <DIR>
 *     Consignor Code<BR>
 *     Planned Picking Date<BR>
 *     Start Planned Picking Date<BR>
 *     End Planned Picking Date<BR>
 *     Customer Code<BR>
 *     Item Code<BR>
 *    Picking Case Location <BR>
 *    Picking Piece Location <BR>
 *     Case/Piece division<BR>
 *   Case Order No.<BR>
 *     Work status:<BR>
 *   </DIR>
 *   <Search table> <BR>
 *   <DIR>
 *     DNRETRIEVALPLAN<BR>
 *   </DIR>
 * </DIR>
 * 
 * 2. Process for displaying data (<CODE>getEntities()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Obtain the data to display on the screen. <BR>
 *   Obtain the display info that was obtained in the search process. <BR>
 *   Set the search result in the <CODE>RetrievalSupportParameter</CODE> array and return it. <BR>
 * <BR>
 *   <Field items to be displayed> <BR>
 *   <DIR>
 *     Case Order No.<BR>
 *   </DIR>
 * </DIR>
 * 
 * <BR>
 * 
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor>
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/10/13</TD>
 * <TD>suresh kayamboo</TD>
 * <TD>New</TD>
 * </TR>
 * </TABLE> <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:57 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalCaseOrdernoRet extends SessionRet
{
	//#CM712068
	// Class fields --------------------------------------------------

	//#CM712069
	// Class variables -----------------------------------------------

	//#CM712070
	// Class method --------------------------------------------------
	//#CM712071
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:18:57 $");
	}

	//#CM712072
	// Constructors --------------------------------------------------
	//#CM712073
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param rtParam    Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalCaseOrdernoRet(Connection conn, RetrievalSupportParameter rtParam) throws Exception
	{
		wConn = conn;
		find(rtParam);
	}
	
	//#CM712074
	// Public methods ------------------------------------------------
	//#CM712075
	/**
	 * Obtain the specified number of search results of the Picking Plan Info and return as an array of Parameter. <BR>
	 * Allow this method to execute the following processes. <BR>
	 * <BR>
	 * <DIR>
	 *   1. Calculate to specify the count of display data to be obtained. <BR>
	 *   2.Obtain the Picking Plan Info from the result set.<BR>
	 *   3.Obtain the display data from the Picking Plan Info, and set it in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 *   4. Return information to be displayed. <BR>
	 * </DIR>
	 * 
	 * @return <CODE>RetrievalSupportParameter</CODE> class that contains Picking Plan Info.
	 */
	public Parameter[] getEntities()
	{
		RetrievalSupportParameter[] resultArray = null;
		RetrievalPlan[] retrievalPlan = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				retrievalPlan = (RetrievalPlan[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (RetrievalSupportParameter[]) convertToStorageSupportParams(retrievalPlan);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM712076
	// Package methods -----------------------------------------------

	//#CM712077
	// Protected methods ---------------------------------------------

	//#CM712078
	// Private methods -----------------------------------------------
	//#CM712079
	/**
	 * Allow this method to obtain the search conditions from parameter and Search through the Picking Plan Info. <BR>
	 * 
	 * @param rtParam   Parameter to obtain search conditions. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter rtParam) throws Exception
	{
		RetrievalPlanSearchKey sKey = new RetrievalPlanSearchKey();

		//#CM712080
		// Set the search conditions. 
		//#CM712081
		//Consignor Code
		if (!StringUtil.isBlank(rtParam.getConsignorCode()))
		{
			sKey.setConsignorCode(rtParam.getConsignorCode());
		}

		//#CM712082
		// If the Planned Picking Date is set for: 
		if (!StringUtil.isBlank(rtParam.getRetrievalPlanDate()))
		{
			sKey.setPlanDate(rtParam.getRetrievalPlanDate());
		}
		//#CM712083
		// Else process is not available in Basic version. 
		else
		{
			if (!StringUtil.isBlank(rtParam.getFromRetrievalPlanDate()))
			{
				//#CM712084
				//Set the Start Planned Picking Date. 
				sKey.setPlanDate(rtParam.getFromRetrievalPlanDate(), ">=");
			}
			if (!StringUtil.isBlank(rtParam.getToRetrievalPlanDate()))
			{
				//#CM712085
				// Set the End Planned Picking Date. 
				sKey.setPlanDate(rtParam.getToRetrievalPlanDate(), "<=");
			}
		}
		//#CM712086
		//Customer Code
		if (!StringUtil.isBlank(rtParam.getCustomerCode()))
		{
			sKey.setCustomerCode(rtParam.getCustomerCode());
		}

		//#CM712087
		// Set the Item Code. 
		if (!StringUtil.isBlank(rtParam.getItemCode()))
		{
			sKey.setItemCode(rtParam.getItemCode());
		}

		//#CM712088
		// Picking Case Location 
		if (!StringUtil.isBlank(rtParam.getCaseLocation()))
		{
			sKey.setCaseLocation(rtParam.getCaseLocation());
		}
		//#CM712089
		// Picking Piece Location 
		if (!StringUtil.isBlank(rtParam.getPieceLocation()))
		{
			sKey.setPieceLocation(rtParam.getPieceLocation());
		}

		//#CM712090
		// Case/Piece division 
		if (!StringUtil.isBlank(rtParam.getCasePieceflg()))
		{

			if (rtParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				//#CM712091
				// Case 
				sKey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_CASE);
			}
			else if (rtParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				//#CM712092
				// Piece 
				sKey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_PIECE);
			}
			else if (rtParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				//#CM712093
				// None 
				sKey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_NOTHING);
			}
			else if (rtParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_MIXED))
			{
				//#CM712094
				// Case, Piece 
				sKey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_MIX);
			}
			else if (rtParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
			{
				//#CM712095
				// All 
				//#CM712096
				//DO NOTHING
			}
		}
		//#CM712097
		// Set the status flag. (Search for a specific status) 
		if (!StringUtil.isBlank(rtParam.getWorkStatus()))
		{
			if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
			{
				//#CM712098
				// Standby 
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
			}
			else if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
			{
				//#CM712099
				//Start
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_START);
			}
			else if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
			{
				//#CM712100
				// Working 
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING);
			}
			else if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
			{
				//#CM712101
				// Partially Completed 
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART);
			}
			else if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
			{
				//#CM712102
				// Completed 
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
			}
			else if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_ALL))
			{
				//#CM712103
				// All 
			}
		}

		//#CM712104
		//Case Order No.
		if (!StringUtil.isBlank(rtParam.getCaseOrderNo()))
		{
			sKey.setCaseOrderNo(rtParam.getCaseOrderNo());
		}
		else
		{
			sKey.setCaseOrderNo("", "IS NOT NULL");
		}

		//#CM712105
		// Set the status flag. (Search for multiple status) 
		if (rtParam.getSearchStatus() != null && rtParam.getSearchStatus().length > 0)
		{
			String[] search = new String[rtParam.getSearchStatus().length];
			for (int i = 0; i < rtParam.getSearchStatus().length; ++i)
			{
				if (rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = RetrievalPlan.STATUS_FLAG_UNSTART;
				}
				else if (rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
				{
					search[i] = RetrievalPlan.STATUS_FLAG_START;
				}
				else if (rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
				{
					search[i] = RetrievalPlan.STATUS_FLAG_NOWWORKING;
				}
				else if (rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if (rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = RetrievalPlan.STATUS_FLAG_COMPLETION;
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
			sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
		}

		//#CM712106
		//Set the Conditions for obtaining data. 
		//#CM712107
		//Case Order No.
		sKey.setCaseOrderNoCollect("");

		//#CM712108
		//Grouping condition 
		//#CM712109
		//Case Order No.
		sKey.setCaseOrderNoGroup(1);

		//#CM712110
		// Set the sorting order. 
		//#CM712111
		//Case Order No.
		sKey.setCaseOrderNoOrder(1, true);


		wFinder = new RetrievalPlanFinder(wConn);
		//#CM712112
		// Open cursor 
		wFinder.open();
		int count = wFinder.search(sKey);
		//#CM712113
		//Set count for  wLength. 
		wLength = count;
		wCurrent = 0;

	}
	
	//#CM712114
	/**
	 * Allow this method to set the RetrievalPlan Entity in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param ety Picking Plan Info 
	 * @return Parameter []  Array of <CODE>RetrievalSupportParameter</CODE> class that has set Picking Plan Info.
	 */
	private Parameter[] convertToStorageSupportParams(Entity[] ety)
	{
		RetrievalSupportParameter[] rtParam = null;
		RetrievalPlan[] retrievalPlan = (RetrievalPlan[]) ety;
		if ((retrievalPlan != null) && (retrievalPlan.length != 0))
		{
			rtParam = new RetrievalSupportParameter[retrievalPlan.length];
			for (int i = 0; i < retrievalPlan.length; i++)
			{
				//#CM712115
				//Case Order No.
				rtParam[i] = new RetrievalSupportParameter();
				if (!StringUtil.isBlank(retrievalPlan[i].getCaseOrderNo()))
				{
					rtParam[i].setCaseOrderNo(retrievalPlan[i].getCaseOrderNo()); //ケースオーダーNo
				}
			}
		}

		return rtParam;
	}

}
//#CM712116
//end of class
