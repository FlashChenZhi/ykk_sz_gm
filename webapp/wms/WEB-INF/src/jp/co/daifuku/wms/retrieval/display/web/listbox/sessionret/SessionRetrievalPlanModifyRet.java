package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM712934
/*
 * Created on Oct 22, 2004
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
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;
//#CM712935
/**
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * Allow this class to search for data in the listbox of Picking Plan list (to modify/delete Picking Plan). <BR>
 * Receive the search conditions as a parameter, and search through Picking Plan list (to modify/delete Picking Plan). <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalPlanModifyRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Picking Plan Info. <BR>
 * <BR>
 *   <Search conditions> Mandatory field item *<BR>
 *   <DIR>
 *     Consignor Code*<BR>
 *    Work Status: Standby *<BR>
 *     Planned Picking Date<BR>
 *   Item Code<BR>
 *   Case Order No.<BR>
 *   Piece Order No.<BR>
 *   Customer Code<BR>
 *   Case Location<BR>
 *    Piece Location <BR>
 *   </DIR>
 *   <Search table>  <BR>
 *   <DIR>
 *     DNRETRIEVALPLAN <BR>
 *   </DIR>
 *   <Searching Order >  <BR>
 *   <DIR>
 *     Planned Picking Date<BR>
 *     Item Code<BR>
 *   </DIR>
 *   <Aggregation Conditions>  <BR>
 *   <DIR>
 *     Consignor Code<BR>
 *     Consignor Name<BR>
 *     Planned Picking Date<BR>
 *     Item Code<BR>
 *     Item Name<BR>
 *     Case Picking Location<BR>
 *     Piece Picking Location <BR>
 *     Customer Code<BR>
 *     Customer Name<BR>
 *     Case Order No.<BR>
 *     Piece Order No.<BR>
 *   </DIR>
 * </DIR>
 * 
 * 2. Process for displaying data (<CODE>getEntities()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Obtain the data to display on the screen. <BR>
 *   1. Obtain the display info that was obtained in the search process. <BR>
 *   Set the search result in the Picking Plan Info array and return it. <BR>
 * <BR>
 *   <Field items to be displayed> 
 *   <DIR>
 *     Consignor Code<BR>
 *     Consignor Name<BR>
 *   Planned Picking Date<BR>
 *   Item Code<BR>
 *   Item Name<BR>
 *   Packed Qty per Case<BR>
 *   Packed qty per bundle<BR>
 *   Planned total qty<BR>
 *     Case Location<BR>
 *    Piece Location <BR>
 *     Case Order No.<BR>
 *   Piece Order No.<BR>
 *   Customer Code<BR>
 *   Customer Name<BR>
 *   Case/Piece division name<BR>
 *   Case ITF<BR>
 *   Bundle ITF<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/22</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:05 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalPlanModifyRet extends SessionRet
{
	//#CM712936
	// Class fields --------------------------------------------------

	//#CM712937
	// Class variables -----------------------------------------------

	//#CM712938
	// Class method --------------------------------------------------
	//#CM712939
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:05 $");
	}

	//#CM712940
	// Constructors --------------------------------------------------
	//#CM712941
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalPlanModifyRet(Connection conn, RetrievalSupportParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	//#CM712942
	// Public methods ------------------------------------------------
	//#CM712943
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
		RetrievalPlan temp[] = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				temp = (RetrievalPlan[]) ((RetrievalPlanFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				resultArray = convertToRetrievalSupportParams(temp);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;

	}

	//#CM712944
	// Package methods -----------------------------------------------

	//#CM712945
	// Protected methods ---------------------------------------------

	//#CM712946
	// Private methods -----------------------------------------------
	//#CM712947
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>RetrievalPlanFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param searchParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 */
	private void find(RetrievalSupportParameter searchParam) throws Exception
	{

		RetrievalPlanSearchKey searchkey = new RetrievalPlanSearchKey();
		//#CM712948
		// Status flag: Standby 
		searchkey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
		//#CM712949
		// Set the Consignor code. It is a mandatory field item. 
		if (!StringUtil.isBlank(searchParam.getConsignorCode()))
		{
			searchkey.setConsignorCode(searchParam.getConsignorCode());
		}
		//#CM712950
		//Set the Planned Picking date.
		if (!StringUtil.isBlank(searchParam.getRetrievalPlanDate()))
		{
			searchkey.setPlanDate(searchParam.getRetrievalPlanDate());
		}
		//#CM712951
		// Set the item code. 
		if (!StringUtil.isBlank(searchParam.getItemCode()))
		{
			searchkey.setItemCode(searchParam.getItemCode());
		}

		//#CM712952
		//Case Order No.
		if (!StringUtil.isBlank(searchParam.getCaseOrderNo()))
		{
			searchkey.setCaseOrderNo(searchParam.getCaseOrderNo());
		}
		//#CM712953
		//Piece Order No.
		if (!StringUtil.isBlank(searchParam.getPieceOrderNo()))
		{
			searchkey.setPieceOrderNo(searchParam.getPieceOrderNo());
		}

		//#CM712954
		//Set the customer code.
		if (!StringUtil.isBlank(searchParam.getCustomerCode()))
		{
			searchkey.setCustomerCode(searchParam.getCustomerCode());
		}

		//#CM712955
		//Set the Case Location. 
		if (!StringUtil.isBlank(searchParam.getCaseLocation()))
		{
			searchkey.setCaseLocation(searchParam.getCaseLocation());
		}

		//#CM712956
		//Set the Piece Location. 
		if (!StringUtil.isBlank(searchParam.getPieceLocation()))
		{
			searchkey.setPieceLocation(searchParam.getPieceLocation());
		}

		//#CM712957
		// Set the grouping condition. 
		searchkey.setConsignorCodeGroup(1);
		searchkey.setConsignorNameGroup(2);
		searchkey.setPlanDateGroup(3);
		searchkey.setItemCodeGroup(4);
		searchkey.setItemName1Group(5);
		searchkey.setCaseLocationGroup(6);
		searchkey.setPieceLocationGroup(7);
		searchkey.setCustomerCodeGroup(8);
		searchkey.setCustomerName1Group(9);
		searchkey.setCaseOrderNoGroup(10);
		searchkey.setPieceOrderNoGroup(11);
		//#CM712958
		// Set the Conditions for obtaining data. 		
		searchkey.setConsignorCodeCollect("");
		searchkey.setConsignorNameCollect("");
		searchkey.setPlanDateCollect("");
		searchkey.setItemCodeCollect("");
		searchkey.setItemName1Collect("");
		searchkey.setCaseLocationCollect("");
		searchkey.setPieceLocationCollect("");
		searchkey.setCaseOrderNoCollect("");
		searchkey.setPieceOrderNoCollect("");
		searchkey.setCustomerCodeCollect("");
		searchkey.setCustomerName1Collect("");

		//#CM712959
		// Set the sorting order. 		
		searchkey.setPlanDateOrder(1, true);
		searchkey.setItemCodeOrder(2, true);

		wFinder = new RetrievalPlanFinder(wConn);
		//#CM712960
		// Open the cursor.
		wFinder.open();
		int count = ((RetrievalPlanFinder) wFinder).search(searchkey);
		//#CM712961
		// Initialize. 
		wLength = count;
		wCurrent = 0;

	}

	//#CM712962
	/**
	 * Allow this method to set the RetrievalPlan Entity in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param retrievalPlan Picking Plan Info 
	 * @return RetrievalSupportParameter []  Array of <CODE>RetrievalSupportParameter</CODE> class that has set Picking Plan Info.
	 */
	private RetrievalSupportParameter[] convertToRetrievalSupportParams(RetrievalPlan[] retrievalPlan)
	{
		RetrievalSupportParameter[] retrievalSupport = new RetrievalSupportParameter[retrievalPlan.length];
		for (int i = 0; i < retrievalPlan.length; ++i)
		{
			retrievalSupport[i] = new RetrievalSupportParameter();
			retrievalSupport[i].setRetrievalPlanDate(retrievalPlan[i].getPlanDate());
			retrievalSupport[i].setConsignorCode(retrievalPlan[i].getConsignorCode());
			retrievalSupport[i].setConsignorName(retrievalPlan[i].getConsignorName());
			retrievalSupport[i].setItemCode(retrievalPlan[i].getItemCode());
			retrievalSupport[i].setItemName(retrievalPlan[i].getItemName1());
			retrievalSupport[i].setBundleEnteringQty(retrievalPlan[i].getBundleEnteringQty());

			int entering_Qty = retrievalPlan[i].getEnteringQty();
			retrievalSupport[i].setEnteringQty(entering_Qty);
			int planQty = retrievalPlan[i].getPlanQty();

			//#CM712963
			// Packing qty shall be 0 or more 
			if (entering_Qty > 0)
			{
				retrievalSupport[i].setPlanCaseQty(planQty / entering_Qty);
			}

			retrievalSupport[i].setTotalPlanQty(planQty);

			retrievalSupport[i].setCaseLocation(retrievalPlan[i].getCaseLocation());
			retrievalSupport[i].setPieceLocation(retrievalPlan[i].getPieceLocation());
			retrievalSupport[i].setCaseOrderNo(retrievalPlan[i].getCaseOrderNo());
			retrievalSupport[i].setPieceOrderNo(retrievalPlan[i].getPieceOrderNo());
			retrievalSupport[i].setCustomerCode(retrievalPlan[i].getCustomerCode());
			retrievalSupport[i].setCustomerName(retrievalPlan[i].getCustomerName1());
			retrievalSupport[i].setCasePieceflgName(retrievalPlan[i].getCasePieceFlag());
			retrievalSupport[i].setITF(retrievalPlan[i].getItf());
			retrievalSupport[i].setBundleITF(retrievalPlan[i].getBundleItf());

		}
		return retrievalSupport;

	}

}
//#CM712964
//end of class
