// $Id: SessionRetrievalItemPlanListRet.java,v 1.2 2007/02/07 04:18:59 suresh Exp $
package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;

//#CM712218
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
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM712219
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * Allow this class to Search through the Picking Plan Info and display it in the listbox.<BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalItemPlanListRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Picking Plan Info. <BR>
 * <BR>
 *   <Search conditions>Mandatory field item *
 *   <DIR>
 *     Consignor Code*<BR>
 *     Start Planned Picking Date<BR>
 *     End Planned Picking Date<BR>
 *     Item Code<BR>
 *     Case/Piece division<BR>
 *     Work status:<BR>
 *   </DIR>
 *   <Search table> 
 *   <DIR>
 *     DNRETRIEVALPLAN<BR>
 *   </DIR>
 *   <Searching Order >  <BR>
 *   <DIR>
 *     Consignor Code<BR>
 *     Planned Picking Date<BR>
 *     Item Code<BR>
 *     Case/Piece division<BR>
 *     Case Picking Location<BR>
 *     Piece Picking Location <BR>
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
 *     Consignor Code<BR>
 *     Consignor Name<BR>
 *     Planned Picking Date<BR>
 *     Item Code<BR>
 *     Item Name<BR>
 *     Case/Piece division (String) <BR>
 *     Planned total qty<BR>
 *     Packed Qty per Case<BR>
 *     Packed qty per bundle<BR>
 *     Host planned Case qty <BR>
 *     Host Planned Piece Qty<BR>
 *     Picking Case Qty <BR>
 *     Picking Piece Qty <BR>
 *     Case Picking Location<BR>
 *     Piece Picking Location <BR>
 *     status (String) <BR>
 *   </DIR>
 * </DIR>
 * 
 *
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/06</TD><TD>Y.Okamura</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:59 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalItemPlanListRet extends SessionRet
{
	//#CM712220
	// Class fields --------------------------------------------------

	//#CM712221
	// Class variables -----------------------------------------------
	//#CM712222
	/**
	 * For obtaining Consignor name 
	 */
	private String wConsignorName = "";

	//#CM712223
	// Class method --------------------------------------------------
	//#CM712224
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:18:59 $");
	}

	//#CM712225
	// Constructors --------------------------------------------------
	//#CM712226
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalItemPlanListRet(Connection conn, RetrievalSupportParameter param) throws Exception
	{
		this.wConn = conn;

		//#CM712227
		// Search 
		find(param);
	}

	//#CM712228
	// Public methods ------------------------------------------------
	//#CM712229
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
	 * @return <CODE>RetrievalSupportParameter</CODE> class that contains Picking Plan Info.
	 */
	public RetrievalSupportParameter[] getEntities()
	{
		RetrievalPlan[] resultArray = null;
		RetrievalSupportParameter[] param = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				//#CM712230
				// Obtain the search result. 
				resultArray = (RetrievalPlan[]) ((RetrievalPlanFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				//#CM712231
				// Set it again in RetrievalSupportParameter 
				param = getDispData(resultArray);

			}
			catch (Exception e)
			{
				//#CM712232
				// 6006002=Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;

		return param;
	}

	//#CM712233
	// Package methods -----------------------------------------------

	//#CM712234
	// Protected methods ---------------------------------------------

	//#CM712235
	// Private methods -----------------------------------------------
	//#CM712236
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>RetrievalPlanFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter param) throws Exception
	{
		int count = 0;

		RetrievalPlanSearchKey retrievalKey = new RetrievalPlanSearchKey();
		RetrievalPlanSearchKey consignorkey = new RetrievalPlanSearchKey();
		//#CM712237
		// Set the search conditions.
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			retrievalKey.setConsignorCode(param.getConsignorCode());
			consignorkey.setConsignorCode(param.getConsignorCode());
		}
		if (!StringUtil.isBlank(param.getFromRetrievalPlanDate()))
		{
			retrievalKey.setPlanDate(param.getFromRetrievalPlanDate(), ">=");
			consignorkey.setPlanDate(param.getFromRetrievalPlanDate(), ">=");
		}
		if (!StringUtil.isBlank(param.getToRetrievalPlanDate()))
		{
			retrievalKey.setPlanDate(param.getToRetrievalPlanDate(), "<=");
			consignorkey.setPlanDate(param.getToRetrievalPlanDate(), "<=");
		}
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			retrievalKey.setItemCode(param.getItemCode());
			consignorkey.setItemCode(param.getItemCode());
		}
		//#CM712238
		// Work Status (other than Deleted for data with division "All") 
		if(param.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
		{
			retrievalKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
			consignorkey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
		}
		else if(param.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
		{
			retrievalKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_START);
			consignorkey.setStatusFlag(RetrievalPlan.STATUS_FLAG_START);
		}
		else if(param.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
		{
			retrievalKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING);
			consignorkey.setStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING);
		}
		else if(param.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
		{
			retrievalKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART);
			consignorkey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART);
		}
		else if(param.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
		{
			retrievalKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
			consignorkey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
		}
		else
		{
			retrievalKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
			consignorkey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
		}
		//#CM712239
		// Specify blank for Order No. 
		retrievalKey.setCaseOrderNo(" ");
		consignorkey.setCaseOrderNo(" ");
		retrievalKey.setPieceOrderNo(" ");
		consignorkey.setPieceOrderNo(" ");

		//#CM712240
		// Searching Order 
		retrievalKey.setConsignorCodeOrder(1, true);
		retrievalKey.setPlanDateOrder(2, true);
		retrievalKey.setItemCodeOrder(3, true);
		retrievalKey.setCasePieceFlagOrder(4, true);
		retrievalKey.setCaseLocationOrder(5, true);
		retrievalKey.setPieceLocationOrder(6, true);

		wFinder = new RetrievalPlanFinder(wConn);
		//#CM712241
		// Open the cursor.
		wFinder.open();
		//#CM712242
		// Execute the search.
		count = ((RetrievalPlanFinder) wFinder).search(retrievalKey);
		//#CM712243
		// Initialize. 
		wLength = count;
		wCurrent = 0;

		//#CM712244
		// Obtain the Consignor Name.
		consignorkey.setConsignorNameCollect("");
		consignorkey.setRegistDateOrder(1, false);

		RetrievalPlanReportFinder consignorFinder = new RetrievalPlanReportFinder(wConn);
		consignorFinder.open();
		int nameCount =  consignorFinder.search(consignorkey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			RetrievalPlan retPlan[] = (RetrievalPlan[]) consignorFinder.getEntities(1);

			if (retPlan != null && retPlan.length != 0)
			{
				wConsignorName = retPlan[0].getConsignorName();
			}
		}
		consignorFinder.close();

	}

	//#CM712245
	/**
	 * Allow this method to set the Picking Plan Info in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param result Picking Plan Info 
	 * @return RetrievalSupportParameter []  Array of <CODE>RetrievalSupportParameter</CODE> class that has set Picking Plan Info.
	 */
	private RetrievalSupportParameter[] getDispData(RetrievalPlan[] result)
	{
		RetrievalSupportParameter[] param = new RetrievalSupportParameter[result.length];

		for (int i = 0; i < result.length; i++)
		{
			param[i] = new RetrievalSupportParameter();
			param[i].setConsignorCode(result[i].getConsignorCode());
			param[i].setConsignorName(wConsignorName);
			param[i].setRetrievalPlanDate(result[i].getPlanDate());
			param[i].setItemCode(result[i].getItemCode());
			param[i].setItemName(result[i].getItemName1());
			param[i].setCasePieceflgName(DisplayUtil.getPieceCaseValue(result[i].getCasePieceFlag()));
			param[i].setTotalPlanQty(result[i].getPlanQty());
			param[i].setEnteringQty(result[i].getEnteringQty());
			param[i].setBundleEnteringQty(result[i].getBundleEnteringQty());
			if(result[i].getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_PIECE))
			{
				param[i].setPlanCaseQty(0);
				param[i].setPlanPieceQty(result[i].getPlanQty());
				param[i].setResultCaseQty(0);
				param[i].setResultPieceQty(result[i].getResultQty());
			}
			else
			{
				param[i].setPlanCaseQty(DisplayUtil.getCaseQty(result[i].getPlanQty(), result[i].getEnteringQty()));
				param[i].setPlanPieceQty(DisplayUtil.getPieceQty(result[i].getPlanQty(), result[i].getEnteringQty()));
				param[i].setResultCaseQty(DisplayUtil.getCaseQty(result[i].getResultQty(), result[i].getEnteringQty()));
				param[i].setResultPieceQty(DisplayUtil.getPieceQty(result[i].getResultQty(), result[i].getEnteringQty()));
			}
			param[i].setCaseLocation(result[i].getCaseLocation());
			param[i].setPieceLocation(result[i].getPieceLocation());
			param[i].setWorkStatusName(DisplayUtil.getRetrievalPlanStatusValue(result[i].getStatusFlag()));

		}

		return param;
	}

}
//#CM712246
//end of class
