package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM712420
/*
 * Created on 2004/10/13
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.retrieval.dbhandler.RetrievalPlanSummarizeFinder;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM712421
/**
 * Designer : M.Koyama <BR>
 * Maker : M.Koyama <BR>
 * <BR>
 * Allow this class to Search through the Picking Plan Info and display it in the listbox of Order No. list. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalOrdernoRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Picking Plan Info. <BR>
 * <BR>
 *   <Search conditions>Mandatory field item *<BR>
 *   <DIR>
 *     Consignor Code<BR>
 *     Planned Picking Date<BR>
 *    Start Order No. <BR>
 *    End Order No. <BR>
 *     Batch No. <BR>
 *     Work status:<BR>
 *     Schedule process flag  <BR>
 *   </DIR>
 *   <Search table> 
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
 *   <Field items to be displayed> 
 *   <DIR>
 *     Order No.<BR>
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
 * <TD>2005/10/28</TD>
 * <TD>M.Koyama</TD>
 * <TD>New</TD>
 * </TR>
 * </TABLE> <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:00 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalOrdernoRet extends SessionRet
{
	//#CM712422
	// Class fields --------------------------------------------------

	//#CM712423
	// Class variables -----------------------------------------------

	//#CM712424
	// Class method --------------------------------------------------
	//#CM712425
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:00 $");
	}

	//#CM712426
	// Constructors --------------------------------------------------
	//#CM712427
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalOrdernoRet(Connection conn, RetrievalSupportParameter rtParam) throws Exception
	{
		wConn = conn;
		find(rtParam);
	}
	
	//#CM712428
	// Public methods ------------------------------------------------
	//#CM712429
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

	//#CM712430
	// Package methods -----------------------------------------------

	//#CM712431
	// Protected methods ---------------------------------------------

	//#CM712432
	// Private methods -----------------------------------------------
	//#CM712433
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>RetrievalPlanSummarizeFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param wParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter rtParam) throws Exception
	{
		RetrievalPlanSearchKey[] caseKey = null;
		
		//#CM712434
		// If Case/Piece division is designated via screen (available via Add Plan screen) 
		if (RetrievalSupportParameter.CASEPIECE_FLAG_CASE.equals(rtParam.getCasePieceflg()))
		{
			caseKey = new RetrievalPlanSearchKey[1];
			caseKey[0] = new RetrievalPlanSearchKey();
			
			String[] casepiece = {RetrievalPlan.CASEPIECE_FLAG_MIX, RetrievalPlan.CASEPIECE_FLAG_CASE};
			setSearchKey(caseKey[0], rtParam, RetrievalPlan.CASEPIECE_FLAG_CASE);
			caseKey[0].setCasePieceFlag(casepiece);
			caseKey[0].setCaseOrderNo("", "IS NOT NULL");
			caseKey[0].setCaseOrderNoCollect();
			
		}
		else if (RetrievalSupportParameter.CASEPIECE_FLAG_PIECE.equals(rtParam.getCasePieceflg()))
		{
			caseKey = new RetrievalPlanSearchKey[1];
			String[] casepiece = {RetrievalPlan.CASEPIECE_FLAG_MIX, RetrievalPlan.CASEPIECE_FLAG_PIECE};
			caseKey[0] = new RetrievalPlanSearchKey();
			setSearchKey(caseKey[0], rtParam, RetrievalPlan.CASEPIECE_FLAG_PIECE);
			caseKey[0].setCasePieceFlag(casepiece);
			caseKey[0].setPieceOrderNo("", "IS NOT NULL");
			caseKey[0].setPieceOrderNoCollect();
			
		}
		else if (RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING.equals(rtParam.getCasePieceflg()))
		{
			caseKey = new RetrievalPlanSearchKey[1];
			caseKey[0] = new RetrievalPlanSearchKey();
			setSearchKey(caseKey[0], rtParam, RetrievalPlan.CASEPIECE_FLAG_NOTHING);
			caseKey[0].setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_NOTHING);
			caseKey[0].setCaseOrderNo("", "IS NOT NULL", "(", "", "OR");
			caseKey[0].setPieceOrderNo("", "IS NOT NULL", "", ")", "AND");
			
		}
		//#CM712435
		// If Case/Piece division is not designated via screen (available via Allocation screen) 
		else
		{
			caseKey = new RetrievalPlanSearchKey[3];
			
			String[] casepiece = {RetrievalPlan.CASEPIECE_FLAG_MIX, RetrievalPlan.CASEPIECE_FLAG_CASE};
			String[] schflag = {RetrievalPlan.SCH_FLAG_UNSTART, RetrievalPlan.SCH_FLAG_PIECE_COMPLETION};
			caseKey[0] = new RetrievalPlanSearchKey();
			setSearchKey(caseKey[0], rtParam, RetrievalPlan.CASEPIECE_FLAG_CASE);
			caseKey[0].setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION, "!=");
			caseKey[0].setCasePieceFlag(casepiece);
			caseKey[0].setCaseOrderNo("", "IS NOT NULL");
			caseKey[0].setSchFlag(schflag);
			caseKey[0].setCaseOrderNoCollect();
			
			String[] casepiece2 = {RetrievalPlan.CASEPIECE_FLAG_MIX, RetrievalPlan.CASEPIECE_FLAG_PIECE};
			String[] schflag2 = {RetrievalPlan.SCH_FLAG_UNSTART, RetrievalPlan.SCH_FLAG_CASE_COMPLETION};
			caseKey[1] = new RetrievalPlanSearchKey();
			setSearchKey(caseKey[1], rtParam, RetrievalPlan.CASEPIECE_FLAG_PIECE);
			caseKey[1].setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION, "!=");
			caseKey[1].setCasePieceFlag(casepiece2);
			caseKey[1].setPieceOrderNo("", "IS NOT NULL");
			caseKey[1].setSchFlag(schflag2);
			caseKey[1].setPieceOrderNoCollect();
			
			caseKey[2] = new RetrievalPlanSearchKey();
			setSearchKey(caseKey[2], rtParam, RetrievalPlan.CASEPIECE_FLAG_NOTHING);
			caseKey[2].setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION, "!=");
			caseKey[2].setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_NOTHING);
			caseKey[2].setCaseOrderNo("", "IS NOT NULL", "(", "", "OR");
			caseKey[2].setPieceOrderNo("", "IS NOT NULL", "", ")", "AND");
			caseKey[2].setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART);
			
		}
		
		wFinder = new RetrievalPlanSummarizeFinder(wConn);
		//#CM712436
		// Open cursor 
		wFinder.open();
		int count = ((RetrievalPlanSummarizeFinder)wFinder).searchOrderNo(caseKey);
		//#CM712437
		//Set count for  wLength. 
		wLength = count;
		wCurrent = 0;
	}
	
	//#CM712438
	/**
	 * Set the conditions input via screen as search conditions. 
	 * @param key Search key to be set
	 * @param rtParam Search conditions to be set
	 * @throws ReadWriteException Announce when error occurs on DB connection. 
	 */
	private void setSearchKey(RetrievalPlanSearchKey key, RetrievalSupportParameter rtParam, String casePieceFlag)
		throws ReadWriteException		
	{
		//#CM712439
		// Set the search conditions. 
		//#CM712440
		//Consignor Code
		if (!StringUtil.isBlank(rtParam.getConsignorCode()))
		{
			key.setConsignorCode(rtParam.getConsignorCode());
		}

		//#CM712441
		// If the Planned Picking Date is set for: 
		if (!StringUtil.isBlank(rtParam.getRetrievalPlanDate()))
		{
			key.setPlanDate(rtParam.getRetrievalPlanDate());
		}

		//#CM712442
		// Case 
		if (casePieceFlag.equals(RetrievalPlan.CASEPIECE_FLAG_CASE))
		{
			//#CM712443
			// If the Order No. is set for: 
			if (!StringUtil.isBlank(rtParam.getOrderNo()))
			{
				key.setCaseOrderNo(rtParam.getOrderNo());
			}
			//#CM712444
			// If the Start Order No. is set for: 
			if (!StringUtil.isBlank(rtParam.getStartOrderNo()))
			{
				key.setCaseOrderNo(rtParam.getStartOrderNo(), ">=");
			}
			//#CM712445
			// If End Order No. is set for: 
			if (!StringUtil.isBlank(rtParam.getEndOrderNo()))
			{
				key.setCaseOrderNo(rtParam.getEndOrderNo(), "<=");
			}
		}
		//#CM712446
		// Piece 
		else if(casePieceFlag.equals(RetrievalPlan.CASEPIECE_FLAG_PIECE))
		{
			//#CM712447
			// If the Order No. is set for: 
			if (!StringUtil.isBlank(rtParam.getOrderNo()))
			{
				key.setPieceOrderNo(rtParam.getOrderNo());
			}		
			//#CM712448
			// If the Start Order No. is set for: 
			if (!StringUtil.isBlank(rtParam.getStartOrderNo()))
			{
				key.setPieceOrderNo(rtParam.getStartOrderNo(), ">=");
			}		
			//#CM712449
			// If End Order No. is set for: 
			if (!StringUtil.isBlank(rtParam.getEndOrderNo()))
			{
				key.setPieceOrderNo(rtParam.getEndOrderNo(), "<=");
			}
		}
		//#CM712450
		// None 
		else if(casePieceFlag.equals(RetrievalPlan.CASEPIECE_FLAG_NOTHING))
		{
			//#CM712451
			// If the Order No. is set for: 
			if (!StringUtil.isBlank(rtParam.getOrderNo()))
			{
				key.setCaseOrderNo(rtParam.getOrderNo(), "=", "(", "", "OR");
				key.setPieceOrderNo(rtParam.getOrderNo(), "=", "", ")", "AND");
			}		
			//#CM712452
			// If the Start Order No. is set for: 
			if (!StringUtil.isBlank(rtParam.getStartOrderNo()))
			{
				key.setCaseOrderNo(rtParam.getStartOrderNo(), ">=", "(", "", "OR");
				key.setPieceOrderNo(rtParam.getStartOrderNo(), ">=", "", ")", "AND");
			}		
			//#CM712453
			// If End Order No. is set for: 
			if (!StringUtil.isBlank(rtParam.getEndOrderNo()))
			{
				key.setCaseOrderNo(rtParam.getEndOrderNo(), "<=", "(", "", "OR");
				key.setPieceOrderNo(rtParam.getEndOrderNo(), "<=", "", ")", "AND");
			}
		}

		//#CM712454
		// Batch No.
		if (!StringUtil.isBlank(rtParam.getBatchNo()))
		{
			key.setHostCollectBatchno(rtParam.getBatchNo());
		}
		
		//#CM712455
		// Work status: other than Deleted 
		key.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
			
	}
	
	//#CM712456
	/**
	 * Allow this class to set the RetrievalPlan Entity in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param ety Picking Plan Info 
	 * @return Parameter []  <CODE>RetrievalSupportParameter</CODE> class that has set Picking Plan Info.
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
				//#CM712457
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
//#CM712458
//end of class
