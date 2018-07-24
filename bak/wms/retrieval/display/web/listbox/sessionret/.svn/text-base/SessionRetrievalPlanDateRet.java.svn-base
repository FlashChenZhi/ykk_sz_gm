package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM712816
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
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM712817
/**
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * Allow this class to search for data in the listbox of Planned date list (Picking Plan). <BR>
 * Receive the search conditions as a parameter, and search through Planned date list. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalPlanDateRet(Connection conn,RetrievalSupportParameter rtParam)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter rtParam)</CODE> method and search for Picking Plan Info. <BR>
 * <BR>
 *   <Search conditions> No mandatory field item<BR>
 *   <DIR>
 *     Consignor Code<BR>
 *     Planned Picking Date<BR>
 *     Start Planned Picking Date<BR>
 *     End Planned Picking Date<BR>
 *    Work status:<BR>
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
 * <TD>2004/10/06</TD>
 * <TD>suresh kayamboo</TD>
 * <TD>New</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:04 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalPlanDateRet extends SessionRet
{
	//#CM712818
	// Class fields --------------------------------------------------

	//#CM712819
	// Class variables -----------------------------------------------

	//#CM712820
	// Class method --------------------------------------------------
	//#CM712821
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:04 $");
	}

	//#CM712822
	// Constructors --------------------------------------------------
	//#CM712823
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param rtParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalPlanDateRet(Connection conn, RetrievalSupportParameter rtParam) throws Exception
	{
		wConn = conn;
		find(rtParam);
	}

	//#CM712824
	// Public methods ------------------------------------------------
	//#CM712825
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

	//#CM712826
	// Package methods -----------------------------------------------

	//#CM712827
	// Protected methods ---------------------------------------------

	//#CM712828
	// Private methods -----------------------------------------------
	//#CM712829
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>RetrievalPlanFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param rtParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 */
	private void find(RetrievalSupportParameter rtParam) throws Exception
	{
		RetrievalPlanSearchKey sKey = new RetrievalPlanSearchKey();
		//#CM712830
		// Set the search conditions. 
		//#CM712831
		//Consignor Code
		if (!StringUtil.isBlank(rtParam.getConsignorCode()))
		{
			sKey.setConsignorCode(rtParam.getConsignorCode());
		}
		//#CM712832
		//Status flag (Use this in Picking Work Maintenance) 
		if (!(StringUtil.isBlank(rtParam.getWorkStatus())))
		{
			if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
			{
				//#CM712833
				// Standby 
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
			}
			else if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
			{
				//#CM712834
				//Start
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_START);
			}
			else if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
			{
				//#CM712835
				// Working 
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING);
			}
			else if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
			{
				//#CM712836
				// Completed 
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
			}
			else if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
			{
				//#CM712837
				// Partially Completed 
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART);
			}
			else if (rtParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_ALL))
			{
				//#CM712838
				// All 
			}
		}
		//#CM712839
		// If the Planned Picking Date is set for: 
		if (!StringUtil.isBlank(rtParam.getRetrievalPlanDate()))
		{
			sKey.setPlanDate(rtParam.getRetrievalPlanDate());
		}
		else
		{
			//#CM712840
			//Start Planned Picking Date
			if (!StringUtil.isBlank(rtParam.getFromRetrievalPlanDate()))
			{
				//#CM712841
				//Set the Start Planned Picking Date. 
				sKey.setPlanDate(rtParam.getFromRetrievalPlanDate());
			}
			if (!StringUtil.isBlank(rtParam.getToRetrievalPlanDate()))
			{
				//#CM712842
				//End Planned Picking Date
				sKey.setPlanDate(rtParam.getToRetrievalPlanDate());
			}
		}

		//#CM712843
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
			//#CM712844
			// Set it using OR condition. 
			sKey.setStatusFlag(search);
		}
		else
		{
			//#CM712845
			// Search for data with status other than Deleted, if nothing is set. 
			sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
		}
		//#CM712846
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


		//#CM712847
		// Item / Order flag 
		//#CM712848
		// Division Item 
		if (RetrievalSupportParameter.ITEMORDERFLAG_ITEM.equals(rtParam.getItemOrderFlag()))
		{
			sKey.setPieceOrderNo("");
			sKey.setCaseOrderNo("");
		}
		//#CM712849
		// Division Order 
		else if (RetrievalSupportParameter.ITEMORDERFLAG_ORDER.equals(rtParam.getItemOrderFlag()))
		{
			sKey.setPieceOrderNo("", "IS NOT NULL", "(", "", "OR");
			sKey.setCaseOrderNo("", "IS NOT NULL", "", ")", "OR");
		}

		//#CM712850
		//Set the Conditions for obtaining data. 
		//#CM712851
		//Planned Picking Date
		sKey.setPlanDateCollect("");
		
		//#CM712852
		//Grouping condition 
		sKey.setPlanDateGroup(1);
		
		//#CM712853
		// Set the sorting order. 
		sKey.setPlanDateOrder(1, true);

		wFinder = new RetrievalPlanFinder(wConn);
		wFinder.open();
		int count = wFinder.search(sKey);
		wLength = count;
		wCurrent = 0;
	}

	//#CM712854
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
				if ((retrievalPlan[i].getPlanDate() != null) && !retrievalPlan[i].getPlanDate().equals(""))
				{
					rtParam[i].setWorkDate(retrievalPlan[i].getPlanDate()); //出庫予定日
					rtParam[i].setRetrievalPlanDate(retrievalPlan[i].getPlanDate()); //出庫予定日
				}
			}
		}
		return rtParam;
	}

}
//#CM712855
//end of class
