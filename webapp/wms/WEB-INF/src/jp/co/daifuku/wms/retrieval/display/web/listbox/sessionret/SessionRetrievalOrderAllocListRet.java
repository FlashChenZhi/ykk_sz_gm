package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM712305
/*
 * Created on Oct 19, 2004
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
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.retrieval.dbhandler.RetrievalWorkingInformationFinder;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;
//#CM712306
/**
 * Designer :   Y.Osawa <BR>
 * Maker :   Y.Osawa <BR>
 * <BR>
 * Allow this class to Search through the Work Status Info (Picking) and display the Order Picking allocation data in the listbox. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalOrderAllocListRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Work Status Info(Picking). <BR>
 * <BR>
 *   <Search conditions>Mandatory field item *
 *   <DIR>
 *     Consignor Code*<BR>
 *     Planned Picking Date*<BR>
 *     Start Order No. <BR>
 *     End Order No. <BR>
 *     Case/Piece division (Work Type) *<BR>
 *     (System identification key: any key other than AS/RS (2),  AND Status flag: Standby (0) OR<BR>
 *      System identification key: AS/RS (2) AND Status flag: Working (2))* <BR>
 *      Work type: Picking. (3) *<BR>
 *     Order No.: other than NULL * <BR> 
 *   </DIR>
 *   <Search table> 
 *   <DIR>
 *     DNWORKINFO<BR>
 *   </DIR>
 *   <Searching Order >  <BR>
 *   <DIR>
 *     Batch No. <BR>
 *     Order No.<BR>
 *     Case/Piece division (Work Type) <BR>
 *     Customer Code<BR>
 *   </DIR>
 *   <Aggregation Conditions>  <BR>
 *   <DIR>
 *     Batch No. <BR>
 *     Order No.<BR>
 *     Case/Piece division (Work Type) <BR>
 *     Customer Code<BR>
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
 *   <Field items to be displayed> 
 *   <DIR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     Planned Picking Date <BR>
 *    Batch No. <BR>
 *   Order No.<BR>
 *   Customer Name<BR>
 *     Case/Piece division name <BR>
 *   Customer Code<BR>
 *   Customer Name<BR>
 *   </DIR>
 * </DIR>
 * 
 *
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/19</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author muneendra y
 * @version $Revision 1.2 Oct 19, 2004
 */
public class SessionRetrievalOrderAllocListRet extends SessionRet
{
	//#CM712307
	//Class fields --------------------------------------------------

	//#CM712308
	// Class variables -----------------------------------------------
	//#CM712309
	/**
	 * For obtaining Consignor name 
	 */
	private String wConsignorName = "";
	//#CM712310
	// Class method --------------------------------------------------
	//#CM712311
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:18:59 $");
	}

	//#CM712312
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalOrderAllocListRet(Connection conn, RetrievalSupportParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}
	
	//#CM712313
	// Public methods ------------------------------------------------
	//#CM712314
	/**
	 * Return the specified number of search results of Work Status Info. <BR>
	 * Allow this method to execute the following processes. <BR>
	 * <BR><DIR>
	 *   1. Calculate to specify the count of display data to be obtained. <BR>
	 *   2.Obtain the Work Status Info from the result set.<BR>
	 *   3.Obtain the display data from the Work Status Info, and set it in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 *   4. Return information to be displayed. 
	 * <BR></DIR>
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> class that contains info to be displayed.
	 */
	public RetrievalSupportParameter[] getEntities()
	{
		WorkingInformation[] resultArray = null;
		RetrievalSupportParameter[] wParam = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray = (WorkingInformation[]) ((RetrievalWorkingInformationFinder) wFinder).getEntities(wStartpoint,wEndpoint);
				//#CM712315
				// Obtain the data for display. 
				wParam = getDispData(resultArray);
			}
			catch (Exception e)
			{
				//#CM712316
				// Record the error in the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return wParam;
	}
	
	//#CM712317
	// Package methods -----------------------------------------------

	//#CM712318
	// Protected methods ---------------------------------------------

	//#CM712319
	// Private methods -----------------------------------------------
	//#CM712320
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>RetrievalWorkingInformationFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param wParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter wParam) throws Exception
	{		
		int wCnt = 0;

        //#CM712321
        // Generate the Work Status Info search key instance. 
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationSearchKey wConsignorKey = new WorkingInformationSearchKey();
		CarryInformationSearchKey cKey = new CarryInformationSearchKey();
		
		//#CM712322
		// Set the row to be obtained. 
		wKey.setBatchNoCollect("");
		wKey.setOrderNoCollect("");
		wKey.setWorkFormFlagCollect("");
		wKey.setCustomerCodeCollect("");
		wKey.setCustomerName1Collect("MAX");

		//#CM712323
		// Set the search conditions. 
		//#CM712324
		// Work type=Picking(3)
		wKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		wConsignorKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		
		//#CM712325
		// System identification key: any key other than AS/RS(2) and Status flag: Standby(0),  OR
		wKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=", "((", "", "AND");
		wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "", ")", "OR");
		wConsignorKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=", "((", "", "AND");
		wConsignorKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "", ")", "OR");
		//#CM712326
		// System identification key: AS/RS(2) and Status flag: Working. (2) 
		wKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "=", "(", "", "AND");
		wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "", "))", "AND");
		wConsignorKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "=", "(", "", "AND");
		wConsignorKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "", "))", "AND");

		//#CM712327
		// Consignor Code
		if (!StringUtil.isBlank(wParam.getConsignorCode()))
		{
			wKey.setConsignorCode(wParam.getConsignorCode());
			wConsignorKey.setConsignorCode(wParam.getConsignorCode());
		}
		//#CM712328
		// Planned Picking Date
		if (!StringUtil.isBlank(wParam.getRetrievalPlanDate()))
		{
			wKey.setPlanDate(wParam.getRetrievalPlanDate());
			wConsignorKey.setPlanDate(wParam.getRetrievalPlanDate());
		}
		//#CM712329
		// Start Order No. 
		if (!StringUtil.isBlank(wParam.getStartOrderNo()))
		{
			wKey.setOrderNo(wParam.getStartOrderNo(), ">=");
			wConsignorKey.setOrderNo(wParam.getStartOrderNo(), ">=");
		}
		//#CM712330
		// End Order No. 
		if (!StringUtil.isBlank(wParam.getEndOrderNo()))
		{
			wKey.setOrderNo(wParam.getEndOrderNo(), "<=");
			wConsignorKey.setOrderNo(wParam.getEndOrderNo(), "<=");
		}
		//#CM712331
		// For data with both Start and End Order No. blank: 
		if(StringUtil.isBlank(wParam.getStartOrderNo()) && StringUtil.isBlank(wParam.getEndOrderNo()))
		{
			wKey.setOrderNo("", "IS NOT NULL");
			wConsignorKey.setOrderNo("", "IS NOT NULL");
		}
		//#CM712332
		// Case/Piece division (Work Type) 
		//#CM712333
		// Any division other than All 
		if (!wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
		{
			//#CM712334
			// Case 
			if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
				wConsignorKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			}
			//#CM712335
			// Piece 
			else if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
				wConsignorKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
			}
			//#CM712336
			// None 
			else if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
				wConsignorKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
			}
		}
		
		//#CM712337
		// Set the display order. 
		wKey.setBatchNoOrder(1, true);
		wKey.setOrderNoOrder(2, true);
		wKey.setWorkFormFlagOrder(3, true);
		wKey.setCustomerCodeOrder(4, true);

		//#CM712338
		// Set the aggregation conditions. 
		wKey.setBatchNoGroup(1);
		wKey.setOrderNoGroup(2);
		wKey.setWorkFormFlagGroup(3);
		wKey.setCustomerCodeGroup(4);

		wFinder = new RetrievalWorkingInformationFinder(wConn);
		//#CM712339
		// Open the cursor.
		wFinder.open();
		//#CM712340
		// Execute the search.
		wCnt = ((RetrievalWorkingInformationFinder) wFinder).searchAllocClear(wKey, cKey);
		//#CM712341
		// Initialize. 
		wLength = wCnt;
		wCurrent = 0;

		//#CM712342
		// Obtain the Consignor Name.
		wConsignorKey.setConsignorNameCollect("");
		wConsignorKey.setRegistDateOrder(1, false);

		RetrievalWorkingInformationFinder wObj = new RetrievalWorkingInformationFinder(wConn);
		wObj.open();
		int wNameCnt =  wObj.searchAllocClear(wConsignorKey, cKey);
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

	//#CM712343
	/**
	 * Allow this method to set the Work Status Info in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * @param wWorkinfo Work Status Info
	 * @return RetrievalSupportParameter []  <CODE>RetrievalSupportParameter</CODE> class that has set Work Status.
	 */
	private RetrievalSupportParameter[] getDispData(WorkingInformation[] wWorkinfo)
	{		
		RetrievalSupportParameter[] wParam = new RetrievalSupportParameter[wWorkinfo.length];

		for (int i = 0; i < wWorkinfo.length; i++)
		{
			wParam[i] = new RetrievalSupportParameter();
			//#CM712344
			// Consignor Code
			wParam[i].setConsignorCode(wWorkinfo[i].getConsignorCode());
			//#CM712345
			// Consignor Name
			wParam[i].setConsignorName(wConsignorName);
			//#CM712346
			// Planned Picking Date
			wParam[i].setRetrievalPlanDate(wWorkinfo[i].getPlanDate());
			//#CM712347
			// Batch No. 
			wParam[i].setBatchNo(wWorkinfo[i].getBatchNo());
			//#CM712348
			// Order No.
			wParam[i].setOrderNo(wWorkinfo[i].getOrderNo());
			//#CM712349
			// Case/Piece division name
			wParam[i].setCasePieceflgName(DisplayUtil.getPieceCaseValue(wWorkinfo[i].getWorkFormFlag()));
			//#CM712350
			// Customer Code
			wParam[i].setCustomerCode(wWorkinfo[i].getCustomerCode());
			//#CM712351
			// Customer Name
			wParam[i].setCustomerName(wWorkinfo[i].getCustomerName1());
		}

		return wParam;
	}
}
//#CM712352
//end of class
