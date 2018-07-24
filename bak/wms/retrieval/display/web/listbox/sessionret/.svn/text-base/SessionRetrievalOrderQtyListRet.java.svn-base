// $Id: SessionRetrievalOrderQtyListRet.java,v 1.2 2007/02/07 04:19:02 suresh Exp $
package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;

//#CM712581
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Result;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM712582
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * 
 * Allow <CODE>SessionRetrievalOrderQtyListRet</CODE> class to search data for Order Picking Result Report and maintain the search result. <BR>
 * Whenever using this class, maintain the reference to this class, in order to maintain the session with the DB. <BR>
 * After using it, ensure to close the session using the <CODE>closeConnection()</CODE> method. <BR>
 * <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalOrderQtyListRet(Connection conn, RetrievalSupportParameter param)</CODE> Constructor) <BR>
 * 	<DIR>
 * 	Execute at Initial Display of listcell. <BR>
 * 	Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Result info (with Picking and Order No.), and<BR>
 * 	maintain the Order Picking Result list. <BR>
 * 	<BR>
 * 	<Search conditions> *Mandatory Input<BR>
 *  <DIR>
 * 	 Consignor Code*<BR>
 * 	 Start Picking Date<BR>
 * 	 End Picking Date<BR>
 *   Customer Code<BR>
 *   Order No.<BR>
 * 	 Case/Piece division<BR>
 *   Data with work type "Picking" (3) <BR>
 *   </DIR>
 * 	</DIR>
 * 	<BR>
 * 2. Process for obtaining record to be displayed (<CODE>getEntities()</CODE> method) <BR>
 * 	<DIR>
 * 	Obtain the record to be displayed on screen. 
 * 	Based on the search result of the search process, return the data to be displayed as an array of <CODE>RetrievalSupportParameter</CODE> class. <BR>
 * 	<BR>
 * 	 <Field item to be set in the <CODE>RetrievalSupportParameter</CODE> class> <BR>
 *		<DIR>
 *			Consignor Code<BR>
 *			Consignor Name<BR>
 *			Picking Date<BR>
 *			Planned Picking Date<BR>
 *			Customer Code<BR>
 *			Customer Name<BR>
 *			Order No.<BR>
 *			Item Code<BR>
 *			Item Name<BR>
 *			Case/Piece division<BR>
 *			Packed Qty per Case<BR>
 *			Packed qty per bundle<BR>
 *			Planned Work Case Qty<BR>
 *			Planned Work Piece Qty<BR>
 *			 Result Case Qty <BR>
 *			 Result Piece Qty <BR>
 *			Shortage Case Qty<BR>
 *			Shortage Piece Qty<BR>
 *			 Picking Location <BR>
 *			Expiry Date<BR>
 *			 System identification key <BR>
 *			 Area Name <BR>
 * 		</DIR>
 * 	</DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/21</TD><TD>K.Matsuda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:02 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalOrderQtyListRet extends SessionRet
{
	//#CM712583
	// Class fields --------------------------------------------------

	//#CM712584
	// Class variables -----------------------------------------------
	//#CM712585
	/**
	 * Consignor Name to be displayed 
	 */
	private String wConsignorName = "";

	//#CM712586
	// Class method --------------------------------------------------
	//#CM712587
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:02 $");
	}

	//#CM712588
	// Constructors --------------------------------------------------
	//#CM712589
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws ReadWriteException Announce when error occurs on the connection to DB. 
	 */
	public SessionRetrievalOrderQtyListRet(Connection conn, RetrievalSupportParameter param) throws ReadWriteException
	{
		//#CM712590
		// Maintain the connection. 
		wConn = conn;

		//#CM712591
		// Search 
		find(param);
	}

	//#CM712592
	// Public methods ------------------------------------------------
	//#CM712593
	/**
	 * Obtain the specified number of search results of the Result info and return it as an array of Parameter. <BR>
	 * Allow this method to execute the following processes. <BR>
	 * <BR>
	 * <DIR>
	 *   1. Calculate to specify the count of display data to be obtained. <BR>
	 *   2.Obtain the Picking Plan Info from the result set.<BR>
	 *   3.Obtain the display data from the Result info and set in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 *   4. Return information to be displayed. <BR>
	 * </DIR>
	 * 
	 * @return Array of <CODE>RetrievalSupportParameter</CODE> class that contains Result info.
	 */
	public RetrievalSupportParameter[] getEntities()
	{
		ResultView[] resultArray = null;
		RetrievalSupportParameter[] param = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray = (ResultView[]) ((ResultViewFinder) wFinder).getEntities(wStartpoint, wEndpoint);

				//#CM712594
				// Obtain the data for display. 
				param = getDispData(resultArray);
			}
			catch (Exception e)
			{
				//#CM712595
				// Record the error in the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return param;

	}

	//#CM712596
	// Package methods -----------------------------------------------

	//#CM712597
	// Protected methods ---------------------------------------------

	//#CM712598
	// Private methods -----------------------------------------------
	//#CM712599
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>ResultViewFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws ReadWriteException Announce when error occurs on the connection to DB. 
	 */
	private void find(RetrievalSupportParameter param) throws ReadWriteException
	{
		//#CM712600
		// Generate the Finder instance.
		wFinder = new ResultViewFinder(wConn);
		ResultViewSearchKey resultViewSearchKey = new ResultViewSearchKey();

		//#CM712601
		// Set the search conditions.
		//#CM712602
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			resultViewSearchKey.setConsignorCode(param.getConsignorCode());
		}
		//#CM712603
		// Start Picking Date
		if (!StringUtil.isBlank(param.getFromRetrievalDate()))
		{
			resultViewSearchKey.setWorkDate(param.getFromRetrievalDate(), ">=");
		}
		//#CM712604
		// End Picking Date
		if (!StringUtil.isBlank(param.getToRetrievalDate()))
		{
			resultViewSearchKey.setWorkDate(param.getToRetrievalDate(), "<=");
		}
		//#CM712605
		// Customer Code
		if (!StringUtil.isBlank(param.getCustomerCode()))
		{
			resultViewSearchKey.setCustomerCode(param.getCustomerCode());
		}
		//#CM712606
		// Order No.
		if (!StringUtil.isBlank(param.getOrderNo()))
		{
			resultViewSearchKey.setOrderNo(param.getOrderNo());
		}
		else
		{
			resultViewSearchKey.setOrderNo("", "IS NOT NULL");
		}
		//#CM712607
		// Case/Piece division (Work Type) 
		//#CM712608
		// Any division other than All 
		if (!param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
		{
			//#CM712609
			// Case 
			if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				resultViewSearchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_CASE);
			}
			//#CM712610
			// Piece 
			else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				resultViewSearchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_PIECE);
			}
			//#CM712611
			// None 
			else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				resultViewSearchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_NOTHING);
			}
		}

		resultViewSearchKey.setJobType(Result.JOB_TYPE_RETRIEVAL);
		resultViewSearchKey.setStatusFlag(Result.STATUS_FLAG_DELETE, "!=");

		//#CM712612
		// Sort in the ascending order of the Picking Date, Planned Picking Date, Customer Code, Order No., Item Code, Case/Piece division (Work Type), Picking Location, Expiry Date, Added Date/Time, and Result qty. 
		resultViewSearchKey.setWorkDateOrder(1, true);
		resultViewSearchKey.setPlanDateOrder(2, true);
		resultViewSearchKey.setCustomerCodeOrder(3, true);
		resultViewSearchKey.setOrderNoOrder(4, true);
		resultViewSearchKey.setItemCodeOrder(5, true);
		resultViewSearchKey.setWorkFormFlagOrder(6, true);
		resultViewSearchKey.setLocationNoOrder(7, true);
		resultViewSearchKey.setRegistDateOrder(8, true);
		resultViewSearchKey.setResultQtyOrder(9, true);

		//#CM712613
		// Open cursor of Finder. 
		wFinder.open();

		//#CM712614
		// Search (Obtain the count of results) 
		wLength = wFinder.search(resultViewSearchKey);

		//#CM712615
		// Initialize the current Count of displayed data. 
		wCurrent = 0;

		//#CM712616
		// Obtain the Consignor Name. 
		getDisplayConsignorName(param);
	}

	//#CM712617
	/**
	 * Allow this method to set the ResultView Entity in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param result	 Result info 
	 * @return Parameter []  Array of <CODE>RetrievalSupportParameter</CODE> class that has set Result info.
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private RetrievalSupportParameter[] getDispData(ResultView[] result) throws Exception
	{
		RetrievalSupportParameter[] param = new RetrievalSupportParameter[result.length];
		AreaOperator ao = new AreaOperator(wConn);
		for (int i = 0; i < result.length; i++)
		{
			param[i] = new RetrievalSupportParameter();
			//#CM712618
			// Consignor Code
			param[i].setConsignorCode(result[i].getConsignorCode());
			//#CM712619
			// Consignor Name
			param[i].setConsignorName(wConsignorName);
			//#CM712620
			// Picking Date
			param[i].setRetrievalDate(result[i].getWorkDate());
			//#CM712621
			// Planned Picking Date
			param[i].setRetrievalPlanDate(result[i].getPlanDate());
			//#CM712622
			// Customer Code
			param[i].setCustomerCode(result[i].getCustomerCode());
			//#CM712623
			// Customer Name
			param[i].setCustomerName(result[i].getCustomerName1());
			//#CM712624
			// Order No.
			param[i].setOrderNo(result[i].getOrderNo());
			//#CM712625
			// Item Code
			param[i].setItemCode(result[i].getItemCode());
			//#CM712626
			// Item Name
			param[i].setItemName(result[i].getItemName1());
			//#CM712627
			// Packed Qty per Case
			param[i].setEnteringQty(result[i].getEnteringQty());
			//#CM712628
			// Packed qty per piece 
			param[i].setBundleEnteringQty(result[i].getBundleEnteringQty());
			//#CM712629
			// Picking Location 
			param[i].setRetrievalLocation(result[i].getResultLocationNo());
			//#CM712630
			// Expiry Date
			param[i].setUseByDate(result[i].getResultUseByDate());
			//#CM712631
			// Division name: Case 
			param[i].setCasePieceflgName(DisplayUtil.getPieceCaseValue(result[i].getWorkFormFlag()));
			//#CM712632
			// Planned Work Case Qty
			param[i].setPlanCaseQty(
				DisplayUtil.getCaseQty(
					result[i].getPlanQty(),
					result[i].getEnteringQty(),
					result[i].getWorkFormFlag()));
			//#CM712633
			// Planned Work Piece Qty
			param[i].setPlanPieceQty(
				DisplayUtil.getPieceQty(
					result[i].getPlanQty(),
					result[i].getEnteringQty(),
					result[i].getWorkFormFlag()));
			//#CM712634
			// Picking Case Qty 
			param[i].setResultCaseQty(
				DisplayUtil.getCaseQty(
					result[i].getResultQty(),
					result[i].getEnteringQty(),
					result[i].getWorkFormFlag()));
			//#CM712635
			// Picking Piece Qty 
			param[i].setResultPieceQty(
				DisplayUtil.getPieceQty(
					result[i].getResultQty(),
					result[i].getEnteringQty(),
					result[i].getWorkFormFlag()));
			//#CM712636
			// Shortage Case Qty
			param[i].setShortageCaseQty(
				DisplayUtil.getCaseQty(
					result[i].getShortageCnt(),
					result[i].getEnteringQty(),
					result[i].getWorkFormFlag()));
			//#CM712637
			// Shortage Piece Qty
			param[i].setShortagePieceQty(
				DisplayUtil.getPieceQty(
					result[i].getShortageCnt(),
					result[i].getEnteringQty(),
					result[i].getWorkFormFlag()));
			//#CM712638
			// System identification key 
			param[i].setSystemDiscKey(result[i].getSystemDiscKey());

			param[i].setRetrievalAreaName(ao.getAreaName(result[i].getAreaNo()));
		}

		return param;
	}

	//#CM712639
	/**
	 * Obtain the Consignor name to display in the List.<BR>
	 * Search for the Result information with the latest Added Date/Time using the search condition for data to be printed, and <BR>
	 * obtain the Consignor name of the leading data. <BR>
	 * 
	 * @param	param	<CODE>RetrievalSupportParameter</CODE> Class <BR>
	 */
	private void getDisplayConsignorName(RetrievalSupportParameter param) throws ReadWriteException
	{
		//#CM712640
		// Generate the Finder instance.
		ResultViewReportFinder consignorFinder = new ResultViewReportFinder(wConn);
		ResultViewSearchKey resultViewSearchKey = new ResultViewSearchKey();

		//#CM712641
		// Set the search conditions.
		//#CM712642
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			resultViewSearchKey.setConsignorCode(param.getConsignorCode());
		}
		//#CM712643
		// Start Picking Date
		if (!StringUtil.isBlank(param.getFromWorkDate()))
		{
			resultViewSearchKey.setPlanDate(param.getFromWorkDate(), ">=");
		}
		//#CM712644
		// End Picking Date
		if (!StringUtil.isBlank(param.getToWorkDate()))
		{
			resultViewSearchKey.setPlanDate(param.getToWorkDate(), "<=");
		}
		//#CM712645
		// Customer Code
		if (!StringUtil.isBlank(param.getCustomerCode()))
		{
			resultViewSearchKey.setCustomerCode(param.getCustomerCode());
		}
		//#CM712646
		// Order No.
		if (!StringUtil.isBlank(param.getOrderNo()))
		{
			resultViewSearchKey.setOrderNo(param.getOrderNo());
		}
		else
		{
			resultViewSearchKey.setOrderNo("", "IS NOT NULL");
		}
		//#CM712647
		// Case/Piece division
		if (!StringUtil.isBlank(param.getCasePieceflg()))
		{
			//#CM712648
			// Set it by Case/Piece division selected via screen. Note) If "All" is selected, disable to set it. 
			//#CM712649
			// Case 
			if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				resultViewSearchKey.setWorkFormFlag(Result.CASEPIECE_FLAG_CASE);
			}
			//#CM712650
			// Piece 
			else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				resultViewSearchKey.setWorkFormFlag(Result.CASEPIECE_FLAG_PIECE);
			}
			//#CM712651
			// None 
			else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				resultViewSearchKey.setWorkFormFlag(Result.CASEPIECE_FLAG_NOTHING);
			}
		}
		resultViewSearchKey.setJobType(Result.JOB_TYPE_RETRIEVAL);
		resultViewSearchKey.setStatusFlag(Result.STATUS_FLAG_DELETE, "!=");
		resultViewSearchKey.setRegistDateOrder(1, false);

		//#CM712652
		// Search Consignor Name
		consignorFinder.open();
		int nameCount = consignorFinder.search(resultViewSearchKey);
		if (nameCount > 0)
		{
			ResultView[] result = (ResultView[]) consignorFinder.getEntities(1);
			if (result != null && result.length != 0)
			{
				wConsignorName = result[0].getConsignorName();
			}
		}
		consignorFinder.close();
	}
}
//#CM712653
//end of class
