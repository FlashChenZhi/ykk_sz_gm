package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM711995
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
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM711996
/**
 * Designer : M.Koyama <BR>
 * Maker : M.Koyama <BR>
 * <BR>
 * Allow this class to search data for the listbox for searching Batch No. (Picking Plan). <BR>
 * Receive the search conditions as a parameter, and search through Batch No. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalBatchNoRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Picking Plan Info. <BR>
 * <BR>
 *   <Search conditions> No mandatory field item <BR>
 *   <DIR>
 *     Consignor Code <BR>
 *   Planned Picking Date <BR>
 *     Batch No. <BR>
 *     Item - Order flag <BR>
 *   </DIR>
 *   <Search table>  <BR>
 *   <DIR>
 *     DNRETRIEVALPLAN <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2. Process for displaying data (<CODE>getEntities()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Obtain the data to display on the screen. <BR>
 *   1. Obtain the display info that was obtained in the search process. <BR>
 *   Set the search result in the <CODE>Parameter</CODE> array and return it. <BR>
 * <BR>
 *   <Field items to be displayed> 
 *   <DIR>
 *     Batch No.<BR>
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
 * <TD>2005/10/28</TD>
 * <TD>M.Koyama</TD>
 * <TD>New</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:57 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalBatchNoRet extends SessionRet
{
	//#CM711997
	// Class fields --------------------------------------------------

	//#CM711998
	// Class variables -----------------------------------------------

	//#CM711999
	// Class method --------------------------------------------------
	//#CM712000
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:18:57 $");
	}

	//#CM712001
	// Constructors --------------------------------------------------
	//#CM712002
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param rtParam    Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalBatchNoRet(Connection conn, RetrievalSupportParameter rtParam) throws Exception
	{
		wConn = conn;
		find(rtParam);
	}

	//#CM712003
	// Public methods ------------------------------------------------
	//#CM712004
	/**
	 * Return the search result of <CODE>DnRetrievalPlan</CODE>. <BR>
	 * <DIR>
	 * <Search result> <BR>
	 * - Batch No. <BR>
	 * </DIR>
	 * @return Search result of DnRetrievalPlan 
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
				resultArray = (RetrievalSupportParameter[]) convertToRetrievalSupportParams(retrievalPlan);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
		
	}

	//#CM712005
	// Package methods -----------------------------------------------

	//#CM712006
	// Protected methods ---------------------------------------------

	//#CM712007
	// Private methods -----------------------------------------------
	//#CM712008
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>RetrievalPlanFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param rtParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter rtParam) throws Exception
	{
		RetrievalPlanSearchKey sKey = new RetrievalPlanSearchKey();
		//#CM712009
		// Set the search conditions. 
		//#CM712010
		//Consignor Code
		if (!StringUtil.isBlank(rtParam.getConsignorCode()))
		{
			sKey.setConsignorCode(rtParam.getConsignorCode());
		}
		//#CM712011
		// If the Planned Picking Date is set for: 
		if (!StringUtil.isBlank(rtParam.getRetrievalPlanDate()))
		{
			sKey.setPlanDate(rtParam.getRetrievalPlanDate());
		}
		//#CM712012
		//If the Batch No. is set for:
		if (!StringUtil.isBlank(rtParam.getBatchNo()))
		{
			sKey.setHostCollectBatchno(rtParam.getBatchNo());
		}
		sKey.setHostCollectBatchno("", "IS NOT NULL");

		//#CM712013
		// Status flag
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
			//#CM712014
			// Set it using OR condition. 
			sKey.setStatusFlag(search);
		}
		else
		{
			//#CM712015
			// Search for data with status other than Deleted, if nothing is set. 
			sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
		}
		
		//#CM712016
		// Item / Order flag 
		//#CM712017
		// Division Item 
		if (rtParam.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ITEM))
		{
			sKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_CASE, "=", "((", "", "AND");
			sKey.setCaseOrderNo("", "IS NULL");
			sKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "(", "", "OR");
			sKey.setSchFlag(RetrievalPlan.SCH_FLAG_PIECE_COMPLETION, "=", "", "))", "OR");

			sKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_PIECE, "=", "(", "", "AND");
			sKey.setPieceOrderNo("", "IS NULL");
			sKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "(", "", "OR");
			sKey.setSchFlag(RetrievalPlan.SCH_FLAG_CASE_COMPLETION, "=", "", "))", "OR");

			sKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_NOTHING, "=", "(", "", "AND");
			sKey.setCaseOrderNo("", "IS NULL");
			sKey.setPieceOrderNo("", "IS NULL");
			sKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "(", "", "OR");
			sKey.setSchFlag(RetrievalPlan.SCH_FLAG_PIECE_COMPLETION, "=", "", "))", "OR");

			sKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_MIX, "=", "(", "", "AND");
			sKey.setCaseOrderNo("", "IS NULL", "(", "", "AND");
			sKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "(", "", "OR");
			sKey.setSchFlag(RetrievalPlan.SCH_FLAG_PIECE_COMPLETION, "=", "", "))", "OR");
			sKey.setPieceOrderNo("", "IS NULL", "(", "", "AND");
			sKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "(", "", "OR");
			sKey.setSchFlag(RetrievalPlan.SCH_FLAG_CASE_COMPLETION, "=", "", "))))", "OR");

		}
		//#CM712018
		// Division Order 
		else if (rtParam.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ORDER))
		{
			sKey.setCaseOrderNo("", "IS NOT NULL", "((", "", "AND");
			sKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_PIECE, "!=");
			sKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "(", "", "OR");
			sKey.setSchFlag(RetrievalPlan.SCH_FLAG_PIECE_COMPLETION, "=", "", "))", "OR");

			sKey.setPieceOrderNo("", "IS NOT NULL", "(", "", "AND");
			sKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_CASE, "!=");
			sKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "(", "", "OR");
			sKey.setSchFlag(RetrievalPlan.SCH_FLAG_CASE_COMPLETION, "=", "", ")))", "AND");
		}

		//#CM712019
		//Set the Conditions for obtaining data. 
		//#CM712020
		//Batch No.
		sKey.setHostCollectBatchnoCollect("");
		//#CM712021
		//Grouping condition 
		sKey.setHostCollectBatchnoGroup(1);
		//#CM712022
		// Set the sorting order. 
		sKey.setHostCollectBatchnoOrder(1, true);

		wFinder = new RetrievalPlanFinder(wConn);
		wFinder.open();
		int count = wFinder.search(sKey);
		wLength = count;
		wCurrent = 0;
	}

	//#CM712023
	/**
	 * Allow this method to set the RetrievalPlan Entity in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param ety Picking Plan Info 
	 * @return Parameter []  Array of <CODE>RetrievalSupportParameter</CODE> class that has set Picking Plan Info.
	 */
	private Parameter[] convertToRetrievalSupportParams(Entity[] ety)
	{
		RetrievalSupportParameter[] rtParam = null;
		RetrievalPlan[] retrievalPlan = (RetrievalPlan[]) ety;
		if ((retrievalPlan != null) && (retrievalPlan.length != 0))
		{
			rtParam = new RetrievalSupportParameter[retrievalPlan.length];
			for (int i = 0; i < retrievalPlan.length; i++)
			{
				rtParam[i] = new RetrievalSupportParameter();
				rtParam[i].setBatchNo(retrievalPlan[i].getHostCollectBatchno()); //バッチNo
			}
		}
		return rtParam;
	}

}
//#CM712024
//end of class
