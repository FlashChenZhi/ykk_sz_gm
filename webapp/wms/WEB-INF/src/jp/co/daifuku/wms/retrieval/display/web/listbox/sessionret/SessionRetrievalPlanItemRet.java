package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM712856
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
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM712857
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * Allow this class to search for data for the listbox of Item list (Picking Plan). <BR>
 * Receive the search conditions as a parameter, and search through Item list. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalPlanItemRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Picking Plan Info. <BR>
 * <BR>
 *   <Search conditions> Mandatory field item *<BR>
 *   <DIR>
 *     Consignor Code<BR>
 *    Work status:<BR>
 *   Planned Picking Date<BR>
 *     Start Planned Picking Date<BR>
 *     End Planned Picking Date<BR>
 *   Item Code<BR>
 *    Start Item Code <BR>
 *    End Item Code <BR>
 *     Batch No. <BR>
 *     Schedule flag <BR>
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
 *   Set the search result in the Picking Plan Info array and return it. <BR>
 * <BR>
 *   <Field items to be displayed> 
 *   <DIR>
 *     Item Code<BR>
 *     Item Name<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 
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
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:04 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalPlanItemRet extends SessionRet
{
	//#CM712858
	// Class fields --------------------------------------------------

	//#CM712859
	// Class variables -----------------------------------------------

	//#CM712860
	// Class method --------------------------------------------------
	//#CM712861
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:04 $");
	}

	//#CM712862
	// Constructors --------------------------------------------------
	//#CM712863
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalPlanItemRet(Connection conn, RetrievalSupportParameter rtParam) throws Exception
	{
		wConn = conn;
		find(rtParam);
	}

	//#CM712864
	// Public methods ------------------------------------------------
	//#CM712865
	/**
	 * Obtain the specified number of search results of the Picking Plan Info and return it as an array of RetrievalSupportParameter. <BR>
	 * Allow this method to execute the following processes. <BR>
	 * <BR>
	 * <DIR>
	 *   1. Calculate to specify the count of display data to be obtained. <BR>
	 *   2.Obtain the Picking Plan Info from the result set.<BR>
	 *   3.Obtain the display data from the Picking Plan Info, and set it in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 *   4. Return information to be displayed. <BR>
	 * </DIR>
	 * 
	 * @return Array of <CODE>RetrievalSupportParameter</CODE> class that contains Picking Plan Info.
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

	//#CM712866
	// Package methods -----------------------------------------------

	//#CM712867
	// Protected methods ---------------------------------------------

	//#CM712868
	// Private methods -----------------------------------------------
	//#CM712869
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

		//#CM712870
		// Set the search conditions. 
		//#CM712871
		//Consignor Code
		if (!StringUtil.isBlank(rtParam.getConsignorCode()))
		{
			sKey.setConsignorCode(rtParam.getConsignorCode());
		}
		//#CM712872
		// Work Status (Use it in the Picking Work Maintenance) 
		if (!(StringUtil.isBlank(rtParam.getWorkStatus())))
		{
			if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
			{
				//#CM712873
				// Standby 
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
			}
			else if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
			{
				//#CM712874
				//Start
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_START);
			}
			else if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
			{
				//#CM712875
				// Working 
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING);
			}
			else if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
			{
				//#CM712876
				// Completed 
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
			}
			else if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
			{
				//#CM712877
				// Partially Completed 
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART);
			}
			else if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_ALL))
			{
				//#CM712878
				// All 
				//#CM712879
				//do nothing
			}
		}
		//#CM712880
		// If the Planned Picking Date is set for: 
		if (!StringUtil.isBlank(rtParam.getRetrievalPlanDate()))
		{
			sKey.setPlanDate(rtParam.getRetrievalPlanDate());
		}
		else
		{
			//#CM712881
			//Start Planned Picking Date
			if (!StringUtil.isBlank(rtParam.getFromRetrievalPlanDate()))
			{
				//#CM712882
				//Set the Start Planned Picking Date. 
				sKey.setPlanDate(rtParam.getFromRetrievalPlanDate(), ">=");
			}
			if (!StringUtil.isBlank(rtParam.getToRetrievalPlanDate()))
			{
				//#CM712883
				//End Planned Picking Date
				sKey.setPlanDate(rtParam.getToRetrievalPlanDate(), "<=");
			}
		}
		//#CM712884
		// Set the Item Code. 
		if (!StringUtil.isBlank(rtParam.getItemCode()))
		{
			sKey.setItemCode(rtParam.getItemCode());
		}
		//#CM712885
		// Set the Start item code. 
		if (!StringUtil.isBlank(rtParam.getStartItemCode()))
		{
			sKey.setItemCode(rtParam.getStartItemCode(), ">=");
		}
		//#CM712886
		// Set the End item code. 
		if (!StringUtil.isBlank(rtParam.getEndItemCode()))
		{
			sKey.setItemCode(rtParam.getEndItemCode(), "<=");
		}

		//#CM712887
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
			//#CM712888
			// Set it using OR condition. 
			sKey.setStatusFlag(search);
		}
		else
		{
			//#CM712889
			// Search for data with status other than Deleted, if nothing is set. 
			sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
		}
		//#CM712890
		// Set the Batch No. 
		if (!StringUtil.isBlank(rtParam.getBatchNo()))
		{
			sKey.setHostCollectBatchno(rtParam.getBatchNo());
		}
		
		//#CM712891
		// Schedule flag 
		if (rtParam.getScheduleFlagArray() != null && rtParam.getScheduleFlagArray().length > 0)
		{
			String[] schFlag = new String[rtParam.getScheduleFlagArray().length];
			for (int i = 0; i < rtParam.getScheduleFlagArray().length; i++)
			{
				if (rtParam.getScheduleFlagArray()[i].equals(RetrievalSupportParameter.SCHEDULE_UNSTART))
				{
					schFlag[i] = RetrievalPlan.SCH_FLAG_UNSTART;
				}
				else if (rtParam.getScheduleFlagArray()[i].equals(RetrievalSupportParameter.SCHEDULE_CASE_COMPLETION))
				{
					schFlag[i] = RetrievalPlan.SCH_FLAG_CASE_COMPLETION;
				}
				else if (rtParam.getScheduleFlagArray()[i].equals(RetrievalSupportParameter.SCHEDULE_PIECE_COMPLETION))
				{
					schFlag[i] = RetrievalPlan.SCH_FLAG_PIECE_COMPLETION;
				}
				else if (rtParam.getScheduleFlagArray()[i].equals(RetrievalSupportParameter.SCHEDULE_COMPLETION))
				{
					schFlag[i] = RetrievalPlan.SCH_FLAG_COMPLETION;
				}
			}
			sKey.setSchFlag(schFlag);
		}


		//#CM712892
		//Set the Conditions for obtaining data. 
		//#CM712893
		//Item Code
		sKey.setItemCodeCollect("");
		//#CM712894
		//Item Name
		sKey.setItemName1Collect("");

		//#CM712895
		//Grouping condition 
		//#CM712896
		//Item Code
		sKey.setItemCodeGroup(1);
		//#CM712897
		//Item Name
		sKey.setItemName1Group(2);

		//#CM712898
		// Item / Order flag 
		//#CM712899
		// Division Item 
		if (RetrievalSupportParameter.ITEMORDERFLAG_ITEM.equals(rtParam.getItemOrderFlag()))
		{
			sKey.setPieceOrderNo("");
			sKey.setCaseOrderNo("");
		}
		//#CM712900
		// Division Order 
		else if (RetrievalSupportParameter.ITEMORDERFLAG_ORDER.equals(rtParam.getItemOrderFlag()))
		{
			sKey.setPieceOrderNo("", "IS NOT NULL", "(", "", "OR");
			sKey.setCaseOrderNo("", "IS NOT NULL", "", ")", "OR");
		}

		//#CM712901
		// Set the sorting order. 
		sKey.setItemCodeOrder(1, true);
		sKey.setItemName1Order(2, true);

		wFinder = new RetrievalPlanFinder(wConn);
		//#CM712902
		// Open cursor 
		wFinder.open();
		int count = wFinder.search(sKey);
		//#CM712903
		//Set count for  wLength. 
		wLength = count;
		wCurrent = 0;
	}

	//#CM712904
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
				rtParam[i] = new RetrievalSupportParameter();
				if (!StringUtil.isBlank(retrievalPlan[i].getItemCode()))
				{
					//#CM712905
					//Item Code
					rtParam[i].setItemCode(retrievalPlan[i].getItemCode());
					//#CM712906
					//Item Name
					rtParam[i].setItemName(retrievalPlan[i].getItemName1());
				}
			}
		}
		return rtParam;
	}

}
//#CM712907
//end of class
