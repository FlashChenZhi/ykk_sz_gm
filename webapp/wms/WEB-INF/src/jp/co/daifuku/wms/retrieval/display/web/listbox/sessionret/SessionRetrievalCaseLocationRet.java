package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM712025
/*
 * Created on 2004/10/08
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

//#CM712026
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * Allow this class to Search through the Picking Plan Info and display it in the Picking Location Search listbox. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalCaseLocationRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
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
 *     Item Code<BR>
 *     Case/Piece division<BR>
 *     Customer Code<BR>
 *   Case Location<BR>
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
 *     Case Location<BR>
 *   </DIR>
 * </DIR>
 * 
 *
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor>
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/10/08</TD>
 * <TD>suresh kayamboo</TD>
 * <TD>New</TD>
 * </TR>
 * </TABLE> <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:57 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalCaseLocationRet extends SessionRet
{
	//#CM712027
	// Class fields --------------------------------------------------

	//#CM712028
	// Class variables -----------------------------------------------

	//#CM712029
	// Class method --------------------------------------------------
	//#CM712030
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:18:57 $");
	}

	//#CM712031
	// Constructors --------------------------------------------------
	//#CM712032
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param rtParam    Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalCaseLocationRet(Connection conn, RetrievalSupportParameter rtParam) throws Exception
	{
		wConn = conn;
		find(rtParam);
	}
	
	//#CM712033
	// Public methods ------------------------------------------------
	//#CM712034
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
	
	//#CM712035
	// Package methods -----------------------------------------------

	//#CM712036
	// Protected methods ---------------------------------------------

	//#CM712037
	// Private methods -----------------------------------------------
	//#CM712038
	/**
	 * Allow this method to obtain the search conditions from parameter and Search through the Picking Plan Info. <BR>
	 * 
	 * @param rtParam   Parameter to obtain search conditions. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter rtParam) throws Exception
	{
		RetrievalPlanSearchKey sKey = new RetrievalPlanSearchKey();

		//#CM712039
		// Set the search conditions. 
		//#CM712040
		//Consignor Code
		if (!StringUtil.isBlank(rtParam.getConsignorCode()))
		{
			sKey.setConsignorCode(rtParam.getConsignorCode());
		}

		//#CM712041
		// If the Planned Picking Date is set for: 
		if (!StringUtil.isBlank(rtParam.getRetrievalPlanDate()))
		{
			sKey.setPlanDate(rtParam.getRetrievalPlanDate());
		}
		//#CM712042
		// Else process is not available in Basic version. 
		else
		{
			if (!StringUtil.isBlank(rtParam.getFromRetrievalPlanDate()))
			{
				//#CM712043
				//Start Planned Picking Date
				sKey.setPlanDate(rtParam.getFromRetrievalPlanDate(), ">=");
			}
			if (!StringUtil.isBlank(rtParam.getToRetrievalPlanDate()))
			{
				//#CM712044
				//End Planned Picking Date
				sKey.setPlanDate(rtParam.getToRetrievalPlanDate(), "<=");
			}
		}
		//#CM712045
		//Item Code
		if (!StringUtil.isBlank(rtParam.getItemCode()))
		{
			sKey.setItemCode(rtParam.getItemCode());
		}
		//#CM712046
		// Case/Piece division 
		if (!StringUtil.isBlank(rtParam.getCasePieceflg()))
		{

			if (rtParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				//#CM712047
				// Case 
				sKey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_CASE);
			}
			else if (rtParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				//#CM712048
				// Piece 
				sKey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_PIECE);
			}
			else if (rtParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				//#CM712049
				// None 
				sKey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_NOTHING);
			}
			else if (rtParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_MIXED))
			{
				//#CM712050
				// Mixed 
				sKey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_MIX);
			}
			else if (rtParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
			{
				//#CM712051
				// All 
			}
		}

		//#CM712052
		//Customer Code
		if (!StringUtil.isBlank(rtParam.getCustomerCode()))
		{
			sKey.setCustomerCode(rtParam.getCustomerCode());
		}
		//#CM712053
		// Picking Case Location 
		if (!StringUtil.isBlank(rtParam.getCaseLocation()))
		{
			sKey.setCaseLocation(rtParam.getCaseLocation());
		}
		else
		{
			sKey.setCaseLocation("", "IS NOT NULL");
		}

		//#CM712054
		// Set the status flag. 
		if(rtParam.getSearchStatus() != null && rtParam.getSearchStatus().length > 0)
		{
			String[] search = new String[rtParam.getSearchStatus().length];
			for(int i = 0; i < rtParam.getSearchStatus().length; ++i)
			{
				if(rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = RetrievalPlan.STATUS_FLAG_UNSTART;
				}
				else if(rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
				{
					search[i] = RetrievalPlan.STATUS_FLAG_START;
				}
				else if(rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
				{
					search[i] = RetrievalPlan.STATUS_FLAG_NOWWORKING;
				}
				else if(rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if(rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
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
		
		//#CM712055
		//Set the Conditions for obtaining data. 
		//#CM712056
		// Picking Case Location 
		sKey.setCaseLocationCollect("");

		//#CM712057
		//Grouping condition 
		//#CM712058
		// Picking Case Location 
		sKey.setCaseLocationGroup(1);

		//#CM712059
		// Set the sorting order. 
		//#CM712060
		// Picking Case Location 
		sKey.setCaseLocationOrder(1, true);

		wFinder = new RetrievalPlanFinder(wConn);
		//#CM712061
		//Open the cursor.
		wFinder.open();
		int count = wFinder.search(sKey);
		//#CM712062
		//Set count for  wLength. 
		wLength = count;
		wCurrent = 0;
	}

	//#CM712063
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
				//#CM712064
				// Picking Case Location 
				if (!StringUtil.isBlank(retrievalPlan[i].getCaseLocation()))
				{
					rtParam[i].setCaseLocation(retrievalPlan[i].getCaseLocation());
				}
			}
		}

		return rtParam;
	}

}
//#CM712065
//end of class
