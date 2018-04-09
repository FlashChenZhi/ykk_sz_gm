package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM713270
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
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM713271
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * Allow this class to search for data in the listbox of Order No. list (Picking Work). <BR>
 * Receive the search conditions as a parameter, and search through Order No. list. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalWorkInfoOrdernoRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Work Status Info. <BR>
 * <BR>
 *   <Search conditions> Mandatory field item *<BR>
 *   <DIR>
 *     Consignor Code<BR>
 *     Work type: Picking*<BR>
 *   Planned Picking Date<BR>
 *   Start Planned Picking Date<BR>
 *   End Planned Picking Date<BR>
 *   Customer Code<BR>
 *   Item Code<BR>
 *   Picking Location <BR>
 *   Order No.<BR>
 *     Case/Piece division<BR>
 *   Work status:<BR> 
 *     System identification key <BR>
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
 *     Order No.<BR>
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
 * <TD></TD>
 * </TR>
 * </TABLE> <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:09 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalWorkInfoOrderNoRet extends SessionRet
{
	//#CM713272
	// Class fields --------------------------------------------------

	//#CM713273
	// Class variables -----------------------------------------------

	//#CM713274
	// Class method --------------------------------------------------
	//#CM713275
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:09 $");
	}

	//#CM713276
	// Constructors --------------------------------------------------
	//#CM713277
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param rtParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalWorkInfoOrderNoRet(Connection conn, RetrievalSupportParameter rtParam) throws Exception
	{
		wConn = conn;
		find(rtParam);
	}

	//#CM713278
	// Public methods ------------------------------------------------
	//#CM713279
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
		WorkingInformation[] workingInformation = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				workingInformation = (WorkingInformation[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (RetrievalSupportParameter[]) convertToStorageSupportParams(workingInformation);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM713280
	// Package methods -----------------------------------------------

	//#CM713281
	// Protected methods ---------------------------------------------

	//#CM713282
	// Private methods -----------------------------------------------
	//#CM713283
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>WorkingInformationFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param rtParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter rtParam) throws Exception
	{
		WorkingInformationSearchKey sKey = new WorkingInformationSearchKey();
		sKey.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL);
		//#CM713284
		// Set the search conditions. 
		//#CM713285
		//Consignor Code
		if (!StringUtil.isBlank(rtParam.getConsignorCode()))
		{
			sKey.setConsignorCode(rtParam.getConsignorCode());
		}

		//#CM713286
		// If the Planned Picking Date is set for: 
		if (!StringUtil.isBlank(rtParam.getRetrievalPlanDate()))
		{
			sKey.setPlanDate(rtParam.getRetrievalPlanDate());
		}
		//#CM713287
		// Else process is not available in Basic version. 
		else
		{
			if (!StringUtil.isBlank(rtParam.getFromRetrievalPlanDate()))
			{
				//#CM713288
				//Set the Start Planned Picking Date. 
				sKey.setPlanDate(rtParam.getFromRetrievalPlanDate(), ">=");
			}
			if (!StringUtil.isBlank(rtParam.getToRetrievalPlanDate()))
			{
				//#CM713289
				//End Planned Picking Date
				sKey.setPlanDate(rtParam.getToRetrievalPlanDate(), "<=");
			}
		}
		//#CM713290
		//Customer Code
		if (!StringUtil.isBlank(rtParam.getCustomerCode()))
		{
			sKey.setCustomerCode(rtParam.getCustomerCode());
		}

		//#CM713291
		// Set the Item Code. 
		if (!StringUtil.isBlank(rtParam.getItemCode()))
		{
			sKey.setItemCode(rtParam.getItemCode());
		}

		if (!StringUtil.isBlank(rtParam.getCasePieceflg()))
		{
			if (rtParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
			{
				//#CM713292
				// Picking Location 
				if (!StringUtil.isBlank(rtParam.getRetrievalLocation()))
					sKey.setLocationNo(rtParam.getRetrievalLocation());
				//#CM713293
				//Order No.
				if (!StringUtil.isBlank(rtParam.getOrderNo()))
					sKey.setOrderNo(rtParam.getOrderNo());
				else
					sKey.setOrderNo("", "IS NOT NULL");
			}
			else if (rtParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				//#CM713294
				// Picking Location 
				if (!StringUtil.isBlank(rtParam.getRetrievalLocation()))
					sKey.setLocationNo(rtParam.getRetrievalLocation());
				//#CM713295
				//Order No.
				if (!StringUtil.isBlank(rtParam.getOrderNo()))
					sKey.setOrderNo(rtParam.getOrderNo());
				else
					sKey.setOrderNo("", "IS NOT NULL");
				//#CM713296
				// None 
				sKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
			}
			else if (rtParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				//#CM713297
				// Picking Case Location 
				if (!StringUtil.isBlank(rtParam.getCaseLocation()))
					sKey.setLocationNo(rtParam.getCaseLocation());
				//#CM713298
				//Case Order No.
				if (!StringUtil.isBlank(rtParam.getCaseOrderNo()))
					sKey.setOrderNo(rtParam.getCaseOrderNo());
				else
					sKey.setOrderNo("", "IS NOT NULL");
				//#CM713299
				// Case 
				sKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			}
			else
			{
				//#CM713300
				// Picking Piece Location 
				if (!StringUtil.isBlank(rtParam.getPieceLocation()))
					sKey.setLocationNo(rtParam.getPieceLocation());
				//#CM713301
				//Piece Order No.
				if (!StringUtil.isBlank(rtParam.getPieceOrderNo()))
					sKey.setOrderNo(rtParam.getPieceOrderNo());
				else
					sKey.setOrderNo("", "IS NOT NULL");
				//#CM713302
				// Piece 
				sKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
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

		//#CM713303
		// Order No 
		if (!StringUtil.isBlank(rtParam.getOrderNo()))
		{
			sKey.setOrderNo(rtParam.getOrderNo());
		}
		else
		{
			sKey.setOrderNo("", "IS NOT NULL");
		}
		
		//#CM713304
		// If the Area type is other than ASRS Work, search for works other than ASRS Work and exclude also the data that evdenced the Shortage 
		if (RetrievalSupportParameter.AREA_TYPE_FLAG_NOASRS.equals(rtParam.getAreaTypeFlag()))
		{
		    sKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");
		    sKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		}
		
		//#CM713305
		// For data with the allocation flag Allocated, search for the allocated data only. 
		if (RetrievalSupportParameter.ALLOCATION_COMPLETION.equals(rtParam.getAllocationFlag()))
		{
			//#CM713306
			// System identification key: any key other than AS/RS(2) and Status flag: Standby(0),  OR
			sKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=", "((", "", "AND");
			sKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "", ")", "OR");
			//#CM713307
			// System identification key: AS/RS(2) and Status flag: Working. (2) 
			sKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "=", "(", "", "AND");
			sKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "", "))", "AND");
		}

		//#CM713308
		//Set the Conditions for obtaining data. 
		//#CM713309
		//Order No.
		sKey.setOrderNoCollect("");

		//#CM713310
		//Grouping condition 
		//#CM713311
		//Order No.
		sKey.setOrderNoGroup(1);

		//#CM713312
		// Set the sorting order. 
		//#CM713313
		//Order No.
		sKey.setOrderNoOrder(1, true);
		//#CM713314
		// Distinquish between Item and Order. 
		if (!StringUtil.isBlank(rtParam.getItemOrderFlag()))
		{
			//#CM713315
			// Item 
			if (rtParam.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ITEM))
			{
				sKey.setOrderNo("", "");
			}
			//#CM713316
			// Order 
			else
			{
				sKey.setOrderNo("", "IS NOT NULL");
			}
		}

		wFinder = new WorkingInformationFinder(wConn);
		//#CM713317
		//Open the cursor.
		wFinder.open();
		int count = wFinder.search(sKey);
		//#CM713318
		//Set count for  wLength. 
		wLength = count;
		wCurrent = 0;

	}

	//#CM713319
	/**
	 * Allow this class to set the Workinginformation Entity in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param ety Work Status Info
	 * @return Parameter []  <CODE>RetrievalSupportParameter</CODE> class that has set Work Status.
	 */
	private Parameter[] convertToStorageSupportParams(Entity[] ety)
	{
		RetrievalSupportParameter[] rtParam = null;
		WorkingInformation[] workingInformation = (WorkingInformation[]) ety;
		if ((workingInformation != null) && (workingInformation.length != 0))
		{
			rtParam = new RetrievalSupportParameter[workingInformation.length];
			for (int i = 0; i < workingInformation.length; i++)
			{
				//#CM713320
				//Order No.
				rtParam[i] = new RetrievalSupportParameter();
				if (!StringUtil.isBlank(workingInformation[i].getOrderNo()))
				{
					rtParam[i].setOrderNo(workingInformation[i].getOrderNo()); //ケースオーダーNo
				}
			}
		}
		return rtParam;

	}

}
//#CM713321
//end of class
