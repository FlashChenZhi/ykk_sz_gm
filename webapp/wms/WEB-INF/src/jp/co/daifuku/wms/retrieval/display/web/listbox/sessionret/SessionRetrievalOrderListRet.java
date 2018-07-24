// $Id: SessionRetrievalOrderListRet.java,v 1.2 2007/02/07 04:19:00 suresh Exp $
package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;

//#CM712353
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM712354
/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida <BR>
 * <BR>
 * Allow this class to Search through the Work Status and display it in the listbox. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalOrderListRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 *  <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Work Status Info. <BR>
 * <BR>
 *   <Search conditions>*Mandatory field item  
 *   <DIR>
 *      Work type: Picking. (3) *<BR>
 *     Start Work flag: "Started" (1)*<BR>
 *     Consignor Code* <BR>
 *     Planned Picking Date <BR>
 *     Order No. <BR>
 *     Case/Piece division  <BR>
 *     Work status:<BR>
 *     Data with system identification key other than AS/RS <BR>
 *   </DIR>
 * <BR>
 *   <Search table>  
 *   <DIR>
 *     DNWORKINFO <BR>
 *   </DIR>
 *   <Searching Order >  <BR>
 *   <DIR>
 *     Customer Code<BR>
 *     Order No.<BR>
 *     Picking Location <BR>
 *     Case/Piece division (Work Type) <BR>
 *     Item Code<BR>
 *     Result Expiry Date <BR>
 *     Expiry Date<BR>
 *   </DIR>
 * </DIR>
 * 2. Process for displaying data (<CODE>getEntities()</CODE> method) <BR><BR><DIR>
 *   Obtain the data to display on the screen. <BR>
 *   Obtain the display info that was obtained in the search process. <BR>
 *   Set the search result in the <CODE>RetrievalSupportParameter</CODE> array and return it. <BR>
 * <BR>
 *   <Field items to be displayed>  <DIR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     Planned Picking Date <BR>
 *     Order No. <BR>
 *     Case/Piece division  <BR>
 *     Customer Code <BR>
 *     Customer Name <BR>
 *     Picking Location  <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Packed Qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Total Planned Picking qty  <BR>
 *     Planned Picking Case Qty  <BR>
 *     Planned Picking Piece Qty  <BR>
 *     Picking Result Case Qty  <BR>
 *     Picking Result Piece Qty  <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Expiry Date <BR>
 *     Status flag <BR></DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>S.Yoshida</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:00 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalOrderListRet extends SessionRet
{
	//#CM712355
	// Class fields --------------------------------------------------

	//#CM712356
	// Class variables -----------------------------------------------
	//#CM712357
	/**
	 * For obtaining Consignor name 
	 */
	private String wConsignorName = "";

	//#CM712358
	// Class method --------------------------------------------------
	//#CM712359
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:00 $");
	}

	//#CM712360
	// Constructors --------------------------------------------------
	//#CM712361
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalOrderListRet(Connection conn, RetrievalSupportParameter param) throws Exception
	{
		//#CM712362
		// Maintain Connection 
		this.wConn = conn;
		//#CM712363
		// Search 
		find(param);
	}

	//#CM712364
	// Public methods ------------------------------------------------
	//#CM712365
	/**
	 * Return the specified number of search results of Work Status Info. <BR>
	 * Allow this method to execute the following processes. <BR>
	 * <BR><DIR>
	 *   1. Calculate to specify the count of display data to be obtained. <BR>
	 *   2.Obtain the Work Status Info from the result set.<BR>
	 *   3.Obtain the display data from the Work Status Info, and set it in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 *   4. Return information to be displayed. 
	 * <BR></DIR>
	 * @return Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 */
	public RetrievalSupportParameter[] getEntities()
	{
		WorkingInformation[] resultArray = null;
		RetrievalSupportParameter[] wParam = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray = (WorkingInformation[]) ((WorkingInformationFinder) wFinder).getEntities(wStartpoint,wEndpoint);
				//#CM712366
				// Obtain the data for display. 
				wParam = getDispData(resultArray);
			}
			catch (Exception e)
			{
				//#CM712367
				// Record the error in the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return wParam;
	}

	//#CM712368
	// Package methods -----------------------------------------------

	//#CM712369
	// Protected methods ---------------------------------------------

	//#CM712370
	// Private methods -----------------------------------------------
	//#CM712371
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>WorkingInformationFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param wParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter wParam) throws Exception
	{
		
		int wCnt = 0;

        //#CM712372
        // Generate the Work Status Info search key instance. 
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationSearchKey wConsignorKey = new WorkingInformationSearchKey();
		
		//#CM712373
		// Set the search conditions.
		//#CM712374
		// Work type=Picking(3)
		wKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		wConsignorKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM712375
		// Start Work flag="Started" (1)
		wKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		wConsignorKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		//#CM712376
		// Consignor Code
		if (!StringUtil.isBlank(wParam.getConsignorCode()))
		{
			wKey.setConsignorCode(wParam.getConsignorCode());
			wConsignorKey.setConsignorCode(wParam.getConsignorCode());
		}
		//#CM712377
		// Planned Picking Date
		if (!StringUtil.isBlank(wParam.getRetrievalPlanDate()))
		{
			wKey.setPlanDate(wParam.getRetrievalPlanDate());
			wConsignorKey.setPlanDate(wParam.getRetrievalPlanDate());
		}
		//#CM712378
		// Order No.
		if (!StringUtil.isBlank(wParam.getOrderNo()))
		{
			wKey.setOrderNo(wParam.getOrderNo());
			wConsignorKey.setOrderNo(wParam.getOrderNo());
		}
		else
		{
			wKey.setOrderNo("", "IS NOT NULL");
			wConsignorKey.setOrderNo("", "IS NOT NULL");
		}
		//#CM712379
		// Case/Piece division (Work Type) 
		//#CM712380
		// Any division other than All 
		if (!wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
		{
			//#CM712381
			// Case 
			if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
				wConsignorKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			}
			//#CM712382
			// Piece 
			else if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
				wConsignorKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
			}
			//#CM712383
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
		//#CM712384
		// Search for data other than ASRS work. 
		wKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");
		wConsignorKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");
		//#CM712385
		// Set the display order. 
		wKey.setCustomerCodeOrder(1, true);
		wKey.setOrderNoOrder(2, true);
		wKey.setLocationNoOrder(3, true);
		wKey.setWorkFormFlagOrder(4, true);
		wKey.setItemCodeOrder(5, true);
		wKey.setResultUseByDateOrder(6, true);	
        wKey.setUseByDateOrder(7, true);

		wFinder = new WorkingInformationFinder(wConn);
		//#CM712386
		// Open the cursor.
		wFinder.open();
		//#CM712387
		// Execute the search.
		wCnt = ((WorkingInformationFinder) wFinder).search(wKey);
		//#CM712388
		// Initialize. 
		wLength = wCnt;
		wCurrent = 0;

		//#CM712389
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

	//#CM712390
	/**
	 * Allow this class to set the Work Status Info in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * @param wWorkinfo Work Status Info
	 * @return RetrievalSupportParameter []  <CODE>RetrievalSupportParameter</CODE> class that has set Work Status.
	 */
	private RetrievalSupportParameter[] getDispData(WorkingInformation[] wWorkinfo)
	{
		
		RetrievalSupportParameter[] wParam = new RetrievalSupportParameter[wWorkinfo.length];

		for (int i = 0; i < wWorkinfo.length; i++)
		{
			wParam[i] = new RetrievalSupportParameter();
			//#CM712391
			// Consignor Code
			wParam[i].setConsignorCode(wWorkinfo[i].getConsignorCode());
			//#CM712392
			// Consignor Name
			wParam[i].setConsignorName(wConsignorName);
			//#CM712393
			// Planned Picking Date
			wParam[i].setRetrievalPlanDate(wWorkinfo[i].getPlanDate());
			//#CM712394
			// Order No.
			wParam[i].setOrderNo(wWorkinfo[i].getOrderNo());
			//#CM712395
			// Case/Piece division 
			wParam[i].setCasePieceflg(wWorkinfo[i].getWorkFormFlag());
			//#CM712396
			// Case/Piece division name
			wParam[i].setCasePieceflgName(DisplayUtil.getPieceCaseValue(wWorkinfo[i].getWorkFormFlag()));
			//#CM712397
			// Customer Code
			wParam[i].setCustomerCode(wWorkinfo[i].getCustomerCode());
			//#CM712398
			// Customer Name
			wParam[i].setCustomerName(wWorkinfo[i].getCustomerName1());
			//#CM712399
			// Picking Location 
			wParam[i].setRetrievalLocation(wWorkinfo[i].getLocationNo());
			//#CM712400
			// Item Code
			wParam[i].setItemCode(wWorkinfo[i].getItemCode());
			//#CM712401
			// Item Name
			wParam[i].setItemName(wWorkinfo[i].getItemName1());
			//#CM712402
			// Packed Qty per Case
			wParam[i].setEnteringQty(wWorkinfo[i].getEnteringQty());
			//#CM712403
			// Packed qty per bundle
			wParam[i].setBundleEnteringQty(wWorkinfo[i].getBundleEnteringQty());
			//#CM712404
			// Total Planned Picking qty 
			wParam[i].setTotalPlanQty(wWorkinfo[i].getPlanEnableQty());
			//#CM712405
			// Case/Piece division: Piece 
			if (wWorkinfo[i].getWorkFormFlag().equals(WorkingInformation.CASEPIECE_FLAG_PIECE))
			{
				//#CM712406
				// Planned Picking Case Qty 
				wParam[i].setPlanCaseQty(0);
				//#CM712407
				// Planned Picking Piece Qty 
				wParam[i].setPlanPieceQty(wWorkinfo[i].getPlanEnableQty());
				//#CM712408
				// Picking Result Case Qty 
				wParam[i].setResultCaseQty(0);
				//#CM712409
				// Picking Result Piece Qty 
				wParam[i].setResultPieceQty(wWorkinfo[i].getResultQty());
			}
			else
			{
				//#CM712410
				// Planned Picking Case Qty 
				wParam[i].setPlanCaseQty(DisplayUtil.getCaseQty(wWorkinfo[i].getPlanEnableQty(), wWorkinfo[i].getEnteringQty()));
				//#CM712411
				// Planned Picking Piece Qty 
				wParam[i].setPlanPieceQty(DisplayUtil.getPieceQty(wWorkinfo[i].getPlanEnableQty(), wWorkinfo[i].getEnteringQty()));
				//#CM712412
				// Picking Result Case Qty 
				wParam[i].setResultCaseQty(DisplayUtil.getCaseQty(wWorkinfo[i].getResultQty(), wWorkinfo[i].getEnteringQty()));
				//#CM712413
				// Picking Result Piece Qty 
				wParam[i].setResultPieceQty(DisplayUtil.getPieceQty(wWorkinfo[i].getResultQty(), wWorkinfo[i].getEnteringQty()));
			}
			//#CM712414
			// Case ITF
			wParam[i].setITF(wWorkinfo[i].getItf());
			//#CM712415
			// Bundle ITF
			wParam[i].setBundleITF(wWorkinfo[i].getBundleItf());
			//#CM712416
			// Expiry Date
			if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
			{
				wParam[i].setUseByDate(wWorkinfo[i].getResultUseByDate());
			}
			else
			{
				wParam[i].setUseByDate(wWorkinfo[i].getUseByDate());
			}

			//#CM712417
			// Status flag
			wParam[i].setWorkStatus(wWorkinfo[i].getStatusFlag());
			//#CM712418
			// Status flag name 
			wParam[i].setWorkStatusName(DisplayUtil.getWorkingStatusValue(wWorkinfo[i].getStatusFlag()));
		}

		return wParam;
	}

}
//#CM712419
//end of class
