package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM712117
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
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.retrieval.dbhandler.RetrievalWorkingInformationFinder;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;
//#CM712118
/**
 * Designer :   Y.Osawa <BR>
 * Maker :   Y.Osawa <BR>
 * <BR>
 * Allow this class to Search through the Work Status Info (Picking) and display the Item Picking allocation data in the listbox. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalItemAllocListRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Work Status Info(Picking). <BR>
 * <BR>
 *   <Search conditions>Mandatory field item *
 *   <DIR>
 *     Consignor Code*<BR>
 *     Planned Picking Date*<BR>
 *     Start Item Code <BR>
 *     End Item Code <BR>
 *     Case/Piece division (Work Type) <BR>
 *     (System identification key: any key other than AS/RS (2),  AND Status flag: Standby (0) OR<BR>
 *      System identification key: AS/RS (2) AND Status flag: Working (2))* <BR>
 *      Work type: Picking. (3) * <BR>
 *     Order No.: NULL * <BR> 
 *   </DIR>
 *   <Search table> 
 *   <DIR>
 *     DNWORKINFO<BR>
 *   </DIR>
 *   <Searching Order >  <BR>
 *   <DIR>
 *     Batch No. <BR>
 *     Item Code<BR>
 *     Case/Piece division (Work Type) <BR>
 *     Picking Location <BR>
 *   </DIR>
 *   <Aggregation Conditions>  <BR>
 *   <DIR>
 *     Batch No. <BR>
 *     Item Code<BR>
 *     Case/Piece division (Work Type) <BR>
 *     Picking Location <BR>
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
 *   Item Code<BR>
 *   Item Name<BR>
 *     Case/Piece division name <BR>
 *    Picking Location <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/19</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author muneendra y
 * @version $Revision 1.2 Oct 19, 2004
 */
public class SessionRetrievalItemAllocListRet extends SessionRet
{
	//#CM712119
	//Class fields --------------------------------------------------

	//#CM712120
	// Class variables -----------------------------------------------
	//#CM712121
	/**
	 * For obtaining Consignor name 
	 */
	private String wConsignorName = "";
	//#CM712122
	// Class method --------------------------------------------------
	//#CM712123
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:18:58 $");
	}

	//#CM712124
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalItemAllocListRet(Connection conn, RetrievalSupportParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}
	
	//#CM712125
	// Public methods ------------------------------------------------
	//#CM712126
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
	public RetrievalSupportParameter[] getEntities()
	{
		WorkingInformation[] resultArray = null;
		RetrievalSupportParameter[] wParam = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray = (WorkingInformation[]) ((WorkingInformationFinder) wFinder).getEntities(wStartpoint,wEndpoint);
				//#CM712127
				// Obtain the data for display. 
				wParam = getDispData(resultArray);
			}
			catch (Exception e)
			{
				//#CM712128
				// Record the error in the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return wParam;
	}
	
	//#CM712129
	// Package methods -----------------------------------------------

	//#CM712130
	// Protected methods ---------------------------------------------

	//#CM712131
	// Private methods -----------------------------------------------
	//#CM712132
	/**
	 * Allow this method to obtain the search conditions from parameter and Search through the Work Status Info. <BR>
	 * @param wParam Parameter to obtain search conditions. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter wParam) throws Exception
	{		
		int wCnt = 0;

        //#CM712133
        // Generate the Work Status Info search key instance. 
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationSearchKey wConsignorKey = new WorkingInformationSearchKey();
		CarryInformationSearchKey cKey = new CarryInformationSearchKey();
		
		//#CM712134
		// Set the row to be obtained. 
		wKey.setBatchNoCollect("");
		wKey.setItemCodeCollect("");
		wKey.setWorkFormFlagCollect("");
		wKey.setLocationNoCollect("");
		wKey.setItemName1Collect("MAX");
		wKey.setSystemDiscKeyCollect("MAX");

		//#CM712135
		// Set the search conditions.
		//#CM712136
		// Work type=Picking(3)
		wKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		wConsignorKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);

		//#CM712137
		// System identification key: any key other than AS/RS(2) and Status flag: Standby(0),  OR
		wKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=", "((", "", "AND");
		wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "", ")", "OR");
		wConsignorKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=", "((", "", "AND");
		wConsignorKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "", ")", "OR");
		//#CM712138
		// System identification key: AS/RS(2) and Status flag: Working. (2) 
		wKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "=", "(", "", "AND");
		wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "", "))", "AND");
		wConsignorKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "=", "(", "", "AND");
		wConsignorKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "", "))", "AND");

		//#CM712139
		// Consignor Code
		if (!StringUtil.isBlank(wParam.getConsignorCode()))
		{
			wKey.setConsignorCode(wParam.getConsignorCode());
			wConsignorKey.setConsignorCode(wParam.getConsignorCode());
		}
		//#CM712140
		// Planned Picking Date
		if (!StringUtil.isBlank(wParam.getRetrievalPlanDate()))
		{
			wKey.setPlanDate(wParam.getRetrievalPlanDate());
			wConsignorKey.setPlanDate(wParam.getRetrievalPlanDate());
		}
		//#CM712141
		// Start Item Code 
		if (!StringUtil.isBlank(wParam.getStartItemCode()))
		{
			wKey.setItemCode(wParam.getStartItemCode(), ">=");
			wConsignorKey.setItemCode(wParam.getStartItemCode(), ">=");
		}
		//#CM712142
		// End Item Code 
		if (!StringUtil.isBlank(wParam.getEndItemCode()))
		{
			wKey.setItemCode(wParam.getEndItemCode(), "<=");
			wConsignorKey.setItemCode(wParam.getEndItemCode(), "<=");
		}
		//#CM712143
		// Order No. (NULL) 
		wKey.setOrderNo("");
		wConsignorKey.setOrderNo("");
		//#CM712144
		// Case/Piece division (Work Type) 
		//#CM712145
		// Any division other than All 
		if (!wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
		{
			//#CM712146
			// Case 
			if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
				wConsignorKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			}
			//#CM712147
			// Piece 
			else if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
				wConsignorKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
			}
			//#CM712148
			// None 
			else if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
				wConsignorKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
			}
		}
		
		//#CM712149
		// Set the display order. 
		wKey.setBatchNoOrder(1, true);
		wKey.setItemCodeOrder(2, true);
		wKey.setWorkFormFlagOrder(3, true);
		wKey.setLocationNoOrder(4, true);

		//#CM712150
		// Set the aggregation conditions. 
		wKey.setBatchNoGroup(1);
		wKey.setItemCodeGroup(2);
		wKey.setWorkFormFlagGroup(3);
		wKey.setLocationNoGroup(4);

		wFinder = new RetrievalWorkingInformationFinder(wConn);
		//#CM712151
		// Open the cursor.
		wFinder.open();
		//#CM712152
		// Execute the search.
		wCnt = ((RetrievalWorkingInformationFinder) wFinder).searchAllocClear(wKey, cKey);
		//#CM712153
		// Initialize. 
		wLength = wCnt;
		wCurrent = 0;

		//#CM712154
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

	//#CM712155
	/**
	 * Allow this method to set the Work Status Info in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * @param wWorkinfo Work Status Info
	 * @return RetrievalSupportParameter []  Array of <CODE>RetrievalSupportParameter</CODE> class that has set Work Status.
	 */
	private RetrievalSupportParameter[] getDispData(WorkingInformation[] wWorkinfo)
	{
		
		RetrievalSupportParameter[] wParam = new RetrievalSupportParameter[wWorkinfo.length];

		for (int i = 0; i < wWorkinfo.length; i++)
		{
			wParam[i] = new RetrievalSupportParameter();
			//#CM712156
			// Consignor Code
			wParam[i].setConsignorCode(wWorkinfo[i].getConsignorCode());
			//#CM712157
			// Consignor Name
			wParam[i].setConsignorName(wConsignorName);
			//#CM712158
			// Planned Picking Date
			wParam[i].setRetrievalPlanDate(wWorkinfo[i].getPlanDate());
			//#CM712159
			// Batch No. 
			wParam[i].setBatchNo(wWorkinfo[i].getBatchNo());
			//#CM712160
			// Item Code
			wParam[i].setItemCode(wWorkinfo[i].getItemCode());
			//#CM712161
			// Item Name
			wParam[i].setItemName(wWorkinfo[i].getItemName1());
			//#CM712162
			// Case/Piece division name
			wParam[i].setCasePieceflgName(DisplayUtil.getPieceCaseValue(wWorkinfo[i].getWorkFormFlag()));
			//#CM712163
			// Picking Location 
			wParam[i].setRetrievalLocation(wWorkinfo[i].getLocationNo());
			//#CM712164
			// System identification key 
			wParam[i].setSystemDiscKey(wWorkinfo[i].getSystemDiscKey());
		}

		return wParam;
	}
}
//#CM712165
//end of class
