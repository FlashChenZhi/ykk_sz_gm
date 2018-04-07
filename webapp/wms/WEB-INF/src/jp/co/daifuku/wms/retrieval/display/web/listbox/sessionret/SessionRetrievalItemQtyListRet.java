// $Id: SessionRetrievalItemQtyListRet.java,v 1.2 2007/02/07 04:18:59 suresh Exp $
package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;

//#CM712247
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
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM712248
/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * <BR>
 * Allow this class to search for the ResultView information and display it in the listbox.<BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalItemQtyListRet(Connection conn,	 RetrievalSupportParameter param)</CODE> method) <BR>
 * <BR> 
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for ResultView information. <BR>
 *   <BR>
 *   <Search conditions> *Mandatory field item 
 *   <BR>
 *   Consignor Code*<BR>
 *   Start Picking Date<BR>
 *   End Picking Date<BR>
 *   Item Code<BR>
 *   Case/Piece division (Work Type) <BR>
 *   Data with work type "Picking" (3) *<BR>
 *   Order No.: NULL *<BR>
 *   <BR>
 *   <Search table>  <BR>
 *   <DIR>
 *   DVRESULTVIEW <BR>
 *   </DIR>
 *   <Searching Order >  <BR>
 *   <DIR>
 *     Picking Date<BR>
 *     Planned Picking Date<BR>
 *     Item Code<BR>
 *     Case/Piece division (Work Type) <BR>
 *     Picking Location <BR>
 *     Added Date/Time <BR>
 *     Result qty<BR>
 *   </DIR>
 *   <BR>
 * </DIR>
 * 2. Process for displaying data (<CODE>getEntities()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Obtain the data to display on the screen. <BR>
 *   Obtain the display info that was obtained in the search process. <BR>
 *   Set the search result in the <CODE>RetrievalSupportParameter</CODE> array and return it. <BR>
 *   <BR>
 *   [Field items to be displayed] 
 *   <BR>
 *   Consignor Code<BR>
 *   Consignor Name<BR>
 *   Picking Date<BR>
 *   Planned Picking Date<BR>
 *   Item Code<BR>
 *   Item Name<BR>
 *   Case/Piece division name<BR>
 *   Packed Qty per Case<BR>
 *   Packed qty per bundle<BR>
 *   Planned Work Case Qty<BR>
 *   Planned Work Piece Qty<BR>
 *   Result Case Qty <BR>
 *   Result Piece Qty <BR>
 *   Shortage Case Qty<BR>
 *   Shortage Piece Qty<BR>
 *   Picking Location <BR>
 *   Expiry Date<BR>
 *   Case ITF<BR>
 *   Bundle ITF<BR>
 *   System type <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/18</TD><TD>Y.Kubo</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:59 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalItemQtyListRet extends SessionRet
{
	//#CM712249
	// Class fields --------------------------------------------------

	//#CM712250
	// Class variables -----------------------------------------------
	//#CM712251
	/**
	 * For obtaining Consignor name 
	 */
	private String wConsignorName = "";


	//#CM712252
	// Class method --------------------------------------------------
	//#CM712253
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:18:59 $");
	}

	//#CM712254
	// Constructors --------------------------------------------------
	//#CM712255
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * <BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalItemQtyListRet(Connection conn, RetrievalSupportParameter param) throws Exception
	{
		this.wConn = conn;

		//#CM712256
		// Search 
		find(param);
	}

	//#CM712257
	// Public methods ------------------------------------------------
	//#CM712258
	/**
	 * Return the specified number of search results of ResultView information. <BR>
	 * Allow this method to execute the following processes. <BR>
	 * <BR>
	 * <DIR>
	 *   1. Calculate to specify the count of display data to be obtained. <BR>
	 *   2.Obtain the Work Status Info from the result set.<BR>
	 *   3.Obtain the display data from the Work Status Info, and set it in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 *   4. Return information to be displayed. <BR>
	 * </DIR>
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> class that contains info to be displayed.
	 */
	public RetrievalSupportParameter[] getEntities()
	{
		ResultView[] resultArray = null;
		RetrievalSupportParameter[] param = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				//#CM712259
				// Obtain the search result. 
				resultArray = (ResultView[]) ((ResultViewFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				//#CM712260
				// Set it again in RetrievalSupportParameter 
				param = getDispData(resultArray);

			}
			catch (Exception e)
			{
				//#CM712261
				// Record the error in the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;

		return param;
	}

	//#CM712262
	// Package methods -----------------------------------------------

	//#CM712263
	// Protected methods ---------------------------------------------

	//#CM712264
	// Private methods -----------------------------------------------
	//#CM712265
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>ResultViewFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter param) throws Exception
	{
		int count = 0;

		ResultViewSearchKey searchKey = new ResultViewSearchKey();
		ResultViewSearchKey namesearchKey = new ResultViewSearchKey();
		//#CM712266
		// Execute searching.
		//#CM712267
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			searchKey.setConsignorCode(param.getConsignorCode());
			namesearchKey.setConsignorCode(param.getConsignorCode());
		}
		//#CM712268
		// Start Picking Date
		if (!StringUtil.isBlank(param.getFromRetrievalDate()))
		{
			searchKey.setWorkDate(param.getFromRetrievalDate(), ">=");
			namesearchKey.setWorkDate(param.getFromRetrievalDate(), ">=");
		}
		//#CM712269
		// End Picking Date
		if (!StringUtil.isBlank(param.getToRetrievalDate()))
		{
			searchKey.setWorkDate(param.getToRetrievalDate(), "<=");
			namesearchKey.setWorkDate(param.getToRetrievalDate(), "<=");
		}
		//#CM712270
		// Item Code
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			searchKey.setItemCode(param.getItemCode());
			namesearchKey.setItemCode(param.getItemCode());
		}
		//#CM712271
		// Case/Piece division (Work Type) 
		//#CM712272
		// Any division other than All 
	 	if (!param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
	 	{
			//#CM712273
			// Case 
			if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				searchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_CASE);
				namesearchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_CASE);
			}
			//#CM712274
			// Piece 
			else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				searchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_PIECE);
				namesearchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_PIECE);
			}
			//#CM712275
			// None 
			else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				searchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_NOTHING);
				namesearchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_PIECE);
			}
	 	}
	 	//#CM712276
	 	// Work type (Picking) 
		searchKey.setJobType(ResultView.JOB_TYPE_RETRIEVAL);
		namesearchKey.setJobType(ResultView.JOB_TYPE_RETRIEVAL);
		//#CM712277
		// Search for data with Order No.  other than Null. 
		searchKey.setOrderNo("", "");
		namesearchKey.setOrderNo("", "");

		//#CM712278
		// Sort in the ascending order of the Picking Date, Planned Picking Date, Item Code, Case/Piece division (Work Type), Picking Location, Added Date/Time, and Result qty. 
		searchKey.setWorkDateOrder(1, true);
		searchKey.setPlanDateOrder(2, true);
		searchKey.setItemCodeOrder(3, true);
		searchKey.setWorkFormFlagOrder(4, true);
		searchKey.setLocationNoOrder(5,true);
		searchKey.setRegistDateOrder(6, true);
		searchKey.setResultQtyOrder(7, true);
		
		wFinder = new ResultViewFinder(wConn);
		//#CM712279
		// Open the cursor.
		wFinder.open();
		count = ((ResultViewFinder) wFinder).search(searchKey);
		//#CM712280
		// Initialize. 
		wLength = count;
		wCurrent = 0;

		//#CM712281
		// Obtain the Consignor Name with later added date/time. 
		namesearchKey.setConsignorNameCollect("");
		namesearchKey.setRegistDateOrder(1, false);

		ResultViewFinder consignorFinder = new ResultViewFinder(wConn);
		consignorFinder.open();
		int nameCount = consignorFinder.search(namesearchKey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			ResultView resultView[] = (ResultView[]) consignorFinder.getEntities(0, 1);

			if (resultView != null && resultView.length != 0)
			{
				wConsignorName = resultView[0].getConsignorName();
			}
		}
		consignorFinder.close();

	}

	//#CM712282
	/**
	 * Allow this method to set the ResultView information in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param resultview ResultView information 
	 * @return RetrievalSupportParameter []  <CODE>RetrievalSupportParameter</CODE> class that has set Result View information.
	 */
	private RetrievalSupportParameter[] getDispData(ResultView[] resultview) throws Exception
	{
		RetrievalSupportParameter[] param = new RetrievalSupportParameter[resultview.length];
		AreaOperator ao = new AreaOperator(wConn);
		for (int i = 0; i < resultview.length; i++)
		{
			param[i] = new RetrievalSupportParameter();
			//#CM712283
			// Consignor Code
			param[i].setConsignorCode(resultview[i].getConsignorCode());
			//#CM712284
			// Consignor Name
			param[i].setConsignorName(wConsignorName);
			//#CM712285
			// Picking Date
			param[i].setRetrievalDate(resultview[i].getWorkDate());
			//#CM712286
			// Planned Picking Date
			param[i].setRetrievalPlanDate(resultview[i].getPlanDate());
			//#CM712287
			// Item Code
			param[i].setItemCode(resultview[i].getItemCode());
			//#CM712288
			// Item Name
			param[i].setItemName(resultview[i].getItemName1());
			//#CM712289
			// Case/Piece division name
			//#CM712290
			// Obtain Case/Piece division name from Case/Piece division (work type). 
			String casepiecename = DisplayUtil.getPieceCaseValue(resultview[i].getWorkFormFlag());
			param[i].setCasePieceflgName(casepiecename);
			//#CM712291
			// Packed Qty per Case
			param[i].setEnteringQty(resultview[i].getEnteringQty());
			//#CM712292
			// Packed qty per bundle
			param[i].setBundleEnteringQty(resultview[i].getBundleEnteringQty());
			//#CM712293
			// Planned Work Case Qty
			param[i].setPlanCaseQty(
				DisplayUtil.getCaseQty(
					resultview[i].getPlanEnableQty(), 
					resultview[i].getEnteringQty(), 
					resultview[i].getWorkFormFlag()));
			//#CM712294
			// Planned Work Piece Qty
			param[i].setPlanPieceQty(
				DisplayUtil.getPieceQty(
					resultview[i].getPlanEnableQty(), 
					resultview[i].getEnteringQty(), 
					resultview[i].getWorkFormFlag()));
			//#CM712295
			// Result Case Qty 
			param[i].setResultCaseQty(
				DisplayUtil.getCaseQty(
					resultview[i].getResultQty(), 
					resultview[i].getEnteringQty(), 
					resultview[i].getWorkFormFlag()));
			//#CM712296
			// Result Piece Qty 
			param[i].setResultPieceQty(
				DisplayUtil.getPieceQty(
					resultview[i].getResultQty(), 
					resultview[i].getEnteringQty(), 
					resultview[i].getWorkFormFlag()));
			//#CM712297
			// Shortage Case Qty
			param[i].setShortageCaseQty(
				DisplayUtil.getCaseQty(
					resultview[i].getShortageCnt(), 
					resultview[i].getEnteringQty(), 
					resultview[i].getWorkFormFlag()));
			//#CM712298
			// Shortage Piece Qty
			param[i].setShortagePieceQty(
				DisplayUtil.getPieceQty(
					resultview[i].getShortageCnt(), 
					resultview[i].getEnteringQty(), 
					resultview[i].getWorkFormFlag()));
			//#CM712299
			// Picking Location 
			param[i].setRetrievalLocation(resultview[i].getResultLocationNo());
			//#CM712300
			// Expiry Date
			param[i].setUseByDate(resultview[i].getResultUseByDate());
			//#CM712301
			// Case ITF
			param[i].setITF(resultview[i].getItf());
			//#CM712302
			// Bundle ITF
			param[i].setBundleITF(resultview[i].getBundleItf());
			//#CM712303
			// System type 
			param[i].setSystemDiscKey(resultview[i].getSystemDiscKey());

			param[i].setRetrievalAreaName(ao.getAreaName(resultview[i].getAreaNo()));

		}

		return param;
	}

}
//#CM712304
//end of class
