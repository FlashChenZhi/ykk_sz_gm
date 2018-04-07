package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM712166
/*
 * Created on Sep 30, 2004
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
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;
//#CM712167
/**
 * 
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * Allow this class to search for the data for Item Picking listbox (Picking Plan). <BR>
 * Receive the search conditions as a parameter, and search through Item Picking Report list. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalItemListRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Work Status Info. <BR>
 * <BR>
 *   <Search conditions> Mandatory field item * <BR>
 *   <DIR>
 *     Consignor Code* <BR>
 *     Planned Picking Date* <BR>
 *     Item Code <BR>
 *     Case/Piece division* <BR>
 *     Work status:* <BR>
 *     Data with work type "Picking" (3) *<BR>
 *   </DIR>
 *   <Search table>  <BR>
 *   <DIR>
 *     DNWORKINFO <BR>
 *   </DIR>
 *   <Searching Order >  <BR>
 *   <DIR>
 *     Picking Location <BR>
 *     Item Code<BR>
 *     Case/Piece division<BR>
 *     Result Expiry Date <BR>
 *     Expiry Date<BR>
 *   </DIR>
 * </DIR>
 * 
 * 2. Process for displaying data (<CODE>getEntities()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Obtain the data to display on the screen.  <BR>
 *   1. Obtain the display info that was obtained in the search process.  <BR>
 *   Set the search result in the array of the Work Status Info and return it. <BR>
 * <BR>
 *   <Field items to be displayed> 
 *   <DIR>
 *     Picking Location  <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Case/Piece division <BR>
 *     Case/Piece division name  <BR>
 *     Planned total qty <BR>
 *     Packed Qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Planned Case Qty  <BR>
 *     Planned Piece Qty  <BR>
 *     Result Case Qty  <BR>
 *     Result Piece Qty  <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Expiry Date <BR>
 *     status  <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/19</TD><TD>Toda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:58 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalItemListRet extends SessionRet
{
	//#CM712168
	// Class fields --------------------------------------------------

	//#CM712169
	// Class variables -----------------------------------------------
	//#CM712170
	/**
	 * For obtaining Consignor name 
	 */
	private String wConsignorName = "";
	//#CM712171
	// Class method --------------------------------------------------
	//#CM712172
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:18:58 $");
	}

	//#CM712173
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalItemListRet(Connection conn, RetrievalSupportParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	
	//#CM712174
	// Public methods ------------------------------------------------
	//#CM712175
	/**
	 * Return the specified number of search results of Work Status Info. <BR>
	 * Allow this method to execute the following processes. <BR>
	 * <BR><DIR>
	 *   1. Calculate to specify the count of display data to be obtained. <BR>
	 *   2.Obtain the Work Status Info from the result set.<BR>
	 *   3.Obtain the display data from the Work Status Info, and set it in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 *   4. Return information to be displayed. 
	 * <BR></DIR>
	 * @return <CODE>RetrievalSupportParameter</CODE> class that contains info to be displayed.
	 */
	public Parameter[] getEntities()
	{
		RetrievalSupportParameter[] resultArray = null;
		WorkingInformation temp[] = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{	
			try
			{	
				temp = (WorkingInformation[])((WorkingInformationFinder)wFinder).getEntities(wStartpoint, wEndpoint);
				resultArray = convertToRetrievalSupportParams(temp);
			}
			catch (Exception e)
			{
				//#CM712176
				// Record the error in the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}
	
	//#CM712177
	// Package methods -----------------------------------------------

	//#CM712178
	// Protected methods ---------------------------------------------

	//#CM712179
	// Private methods ----------------------------------------------- 
	//#CM712180
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>WorkingInformationFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param wParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter wParam) throws Exception
	{	
		//#CM712181
		// Generate the Work Status Info search key instance. 
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationSearchKey wConsignorKey = new WorkingInformationSearchKey();
		
		//#CM712182
		// Set the search conditions.
		//#CM712183
		// Work type=Picking(3)
		wKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		wConsignorKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);

		//#CM712184
		// Consignor Code
		if (!StringUtil.isBlank(wParam.getConsignorCode()))
		{
			wKey.setConsignorCode(wParam.getConsignorCode());
			wConsignorKey.setConsignorCode(wParam.getConsignorCode());
		}
		//#CM712185
		// Planned Picking Date
		if (!StringUtil.isBlank(wParam.getRetrievalPlanDate()))
		{
			wKey.setPlanDate(wParam.getRetrievalPlanDate());
			wConsignorKey.setPlanDate(wParam.getRetrievalPlanDate());
		}
		//#CM712186
		// Item Code
		if (!StringUtil.isBlank(wParam.getItemCode()))
		{
			wKey.setItemCode(wParam.getItemCode());
			wConsignorKey.setItemCode(wParam.getItemCode());
		}
		else
		{
			wKey.setItemCode("", "IS NOT NULL");
			wConsignorKey.setItemCode("", "IS NOT NULL");
		}
		//#CM712187
		// Case/Piece division (Work Type) 
		//#CM712188
		// Any division other than All 
		if (!wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
		{
			//#CM712189
			// Case 
			if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
				wConsignorKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			}
			//#CM712190
			// Piece 
			else if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
				wConsignorKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
			}
			//#CM712191
			// None 
			else if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
				wConsignorKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
			}
		}

		if(wParam.getWorkStatus() != null)
		{
			if(wParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
			{
				wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
				wConsignorKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			}
			else if(wParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
			{
				wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_START);
				wConsignorKey.setStatusFlag(WorkingInformation.STATUS_FLAG_START);
			}
			else if(wParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
			{
				wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
				wConsignorKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			}
			else if(wParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
			{
				wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART);
				wConsignorKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART);
			}
			else if(wParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
			{
				wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
				wConsignorKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
			}
			else if(wParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_ALL))
			{
				wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
				wConsignorKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
			}
			else
			{
				wKey.setStatusFlag("*");
				wConsignorKey.setStatusFlag("*");
			}
		}
		else
		{
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
			wConsignorKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		}
		//#CM712192
		// Order No.
		wKey.setOrderNo("", "IS NULL");
		wConsignorKey.setOrderNo("", "IS NULL");
		//#CM712193
		// Search for data other than ASRS work. 
		wKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");
		//#CM712194
		// Start Work flag= "Started" 
		wKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		
		
		//#CM712195
		// Set the display order. 
		wKey.setLocationNoOrder(1, true);
		wKey.setItemCodeOrder(2, true);
		wKey.setWorkFormFlagOrder(3, true);
        wKey.setResultUseByDateOrder(4, true);
		wKey.setUseByDateOrder(5, true);

		wFinder = new WorkingInformationFinder(wConn);
		//#CM712196
		// Open the cursor.
		wFinder.open();
		int count = ((WorkingInformationFinder)wFinder).search(wKey);
		//#CM712197
		// Initialize.  
		wLength = count;
		wCurrent = 0;
		
		//#CM712198
		// Obtain the Consignor Name.
		wConsignorKey.setConsignorNameCollect("");
		wConsignorKey.setRegistDateOrder(1, false);

		WorkingInformationFinder wObj = new WorkingInformationFinder(wConn);
		wObj.open();
		int wNameCnt =  wObj.search(wConsignorKey);
		if (wNameCnt > 0 && wNameCnt <= DatabaseFinder.MAXDISP)
		{
			WorkingInformation wWorkinfo[] = (WorkingInformation[]) wObj.getEntities(0, 1);

			if (wWorkinfo != null && wWorkinfo.length != 0)
			{
				wConsignorName = wWorkinfo[0].getConsignorName();
			}
		}
		wObj.close();
	}
	
	//#CM712199
	/**
	 * Allow this method to set the Workinginformation Entity in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param workingInfomation Work Status Info
	 * @return RetrievalSupportParameter []  Array of <CODE>RetrievalSupportParameter</CODE> class that has set Work Status.
	 */
	private RetrievalSupportParameter[] convertToRetrievalSupportParams(WorkingInformation[] workingInformation)
	{
		RetrievalSupportParameter[] stParam = null;
		
		if (workingInformation == null || workingInformation.length==0)
		{	
		 	return null;
		}

		stParam = new RetrievalSupportParameter[workingInformation.length];
		for (int i = 0; i < workingInformation.length; i++)
		{
				stParam[i] = new RetrievalSupportParameter();
				//#CM712200
				// Consignor Code
				stParam[i].setConsignorCode(workingInformation[i].getConsignorCode());
				//#CM712201
				// Consignor Name
				stParam[i].setConsignorName(wConsignorName);
				//#CM712202
				// Picking Location 
				stParam[i].setRetrievalLocation(workingInformation[i].getLocationNo());
				//#CM712203
				// Item Code
				stParam[i].setItemCode(workingInformation[i].getItemCode());
				//#CM712204
				// Item Name
				stParam[i].setItemName(workingInformation[i].getItemName1());
				//#CM712205
				// Case/Piece division
				stParam[i].setCasePieceflgName(DisplayUtil.getPieceCaseValue(workingInformation[i].getWorkFormFlag()));
				//#CM712206
				// Planned total qty
				stParam[i].setTotalPlanQty(workingInformation[i].getPlanEnableQty());
				//#CM712207
				// Packed Qty per Case
				stParam[i].setEnteringQty(workingInformation[i].getEnteringQty());
				//#CM712208
				// Packed qty per bundle
				stParam[i].setBundleEnteringQty(workingInformation[i].getBundleEnteringQty());
				//#CM712209
				// Planned Work Case Qty
				stParam[i].setPlanCaseQty(DisplayUtil.getCaseQty(workingInformation[i].getPlanEnableQty(), workingInformation[i].getEnteringQty()));
				//#CM712210
				// Planned Work Piece Qty
				stParam[i].setPlanPieceQty(DisplayUtil.getPieceQty(workingInformation[i].getPlanEnableQty(), workingInformation[i].getEnteringQty()));
				//#CM712211
				// Result Case Qty 
				stParam[i].setResultCaseQty(DisplayUtil.getCaseQty(workingInformation[i].getResultQty(), workingInformation[i].getEnteringQty()));
				//#CM712212
				// Result Piece Qty 
				stParam[i].setResultPieceQty(DisplayUtil.getPieceQty(workingInformation[i].getResultQty(), workingInformation[i].getEnteringQty()));
				//#CM712213
				// Case ITF
				stParam[i].setITF(workingInformation[i].getItf());
				//#CM712214
				// Bundle ITF
				stParam[i].setBundleITF(workingInformation[i].getBundleItf());
				//#CM712215
				// Expiry Date
				if(workingInformation[i].getStatusFlag().equals(SystemDefine.STATUS_FLAG_COMPLETION))
				{
				    stParam[i].setUseByDate(workingInformation[i].getResultUseByDate());
				}
				else
				{
				    stParam[i].setUseByDate(workingInformation[i].getUseByDate());
				}
				
				//#CM712216
				// status 
				String statusname = DisplayUtil.getWorkingStatusValue(workingInformation[i].getStatusFlag());
				stParam[i].setWorkStatusName(statusname);  
		}
			
		return stParam;
	}
}
//#CM712217
//end of class
