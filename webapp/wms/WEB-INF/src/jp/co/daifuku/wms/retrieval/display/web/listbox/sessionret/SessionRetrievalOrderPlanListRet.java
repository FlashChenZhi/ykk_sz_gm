package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM712546
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
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;
//#CM712547
/**
 * Designer :   Muneendra <BR>
 * Maker :   Muneendra <BR>
 * <BR>
 * Allow this class to Search through the Picking Plan Info and display it in the listbox.<BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalOrderPlanListRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Picking Plan Info. <BR>
 * <BR>
 *   <Search conditions>Mandatory field item *
 *   <DIR>
 *     Consignor Code<BR>
 *     Start Planned Picking Date<BR>
 *     End Planned Picking Date<BR>
 *   Customer Code<BR>
 *     Case Order No.<BR>
 *     Piece Order No.<BR>
 *     Work status:<BR>
 *   </DIR>
 *   <Search table> 
 *   <DIR>
 *     DNRETRIEVALPLAN<BR>
 *   </DIR>
 *   <Searching Order >  <BR>
 *   <DIR>
 *     Planned Picking Date<BR>
 *     Customer Code<BR>
 *     Case Order No.<BR>
 *     Piece Order No.<BR>
 *     Item Code<BR>
 *     Case/Piece division (Work Type) <BR>
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
 *   <Field items to be displayed> 
 *   <DIR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     Planned Picking Date <BR>
 *   Customer Code<BR>
 *   Customer Name<BR>
 *   Case Location<BR>
 *    Piece Location <BR>
 *    Work status:<BR>
 *     Case Order No. <BR>
 *   Piece Order No.<BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Case/Piece division name <BR>
 *     Packed Qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Planned Case Qty  <BR>
 *     Planned Piece Qty  <BR>
 *     Result Case Qty  <BR>
 *     Result Piece Qty  <BR> 
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
public class SessionRetrievalOrderPlanListRet extends SessionRet
{
	//#CM712548
	//Class fields --------------------------------------------------

	//#CM712549
	// Class variables -----------------------------------------------
	//#CM712550
	/**
	 * For obtaining Consignor name 
	 */
	private String wConsignorName = "";
	//#CM712551
	// Class method --------------------------------------------------
	//#CM712552
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:01 $");
	}

	//#CM712553
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalOrderPlanListRet(Connection conn, RetrievalSupportParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}
	
	//#CM712554
	// Public methods ------------------------------------------------
	//#CM712555
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
		RetrievalPlan temp[] = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{	
			try
			{	
				temp = (RetrievalPlan[])((RetrievalPlanFinder)wFinder).getEntities(wStartpoint, wEndpoint);
				resultArray = convertToRetrievalSupportParams(temp);
			}
			catch (Exception e)
			{
				//#CM712556
				// Record the error in the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}
	
	//#CM712557
	// Package methods -----------------------------------------------

	//#CM712558
	// Protected methods ---------------------------------------------

	//#CM712559
	// Private methods -----------------------------------------------
	//#CM712560
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>RetrievalPlanFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param searchParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter searchParam) throws Exception
	{	
		
		RetrievalPlanSearchKey searchkey = new RetrievalPlanSearchKey();
		//#CM712561
		// For obtaining the latest Consignor name 
		RetrievalPlanSearchKey namekey = new RetrievalPlanSearchKey();
		
		//#CM712562
		// Set the search conditions.
		//#CM712563
		// Status flag other than Deleted
		searchkey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
		namekey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");		
		//#CM712564
		// Consignor Code
		searchkey.setConsignorCode(searchParam.getConsignorCode());
		namekey.setConsignorCode(searchParam.getConsignorCode());
		//#CM712565
		// Start Planned Picking Date
		if(!StringUtil.isBlank(searchParam.getFromRetrievalPlanDate())){
			searchkey.setPlanDate(searchParam.getFromRetrievalPlanDate(),">=");
			namekey.setPlanDate(searchParam.getFromRetrievalPlanDate(),">=");
		}		
		//#CM712566
		// End Planned Picking Date
		if(!StringUtil.isBlank(searchParam.getToRetrievalPlanDate()))
		{
			searchkey.setPlanDate(searchParam.getToRetrievalPlanDate(),"<=");
			namekey.setPlanDate(searchParam.getToRetrievalPlanDate(),"<=");
		}
		//#CM712567
		// Customer Code
		if(!StringUtil.isBlank(searchParam.getCustomerCode()))
		{
			searchkey.setCustomerCode(searchParam.getCustomerCode());
			namekey.setCustomerCode(searchParam.getCustomerCode());
		}		
		
		//#CM712568
		// Case Order No. and Piece Order No. 
		if(!StringUtil.isBlank(searchParam.getCaseOrderNo()) && !StringUtil.isBlank(searchParam.getPieceOrderNo()))
		{				
			searchkey.setCaseOrderNo(searchParam.getCaseOrderNo(),"=", "(", "", "OR");
			searchkey.setPieceOrderNo(searchParam.getPieceOrderNo(),"=", "", ")", "AND");
			namekey.setCaseOrderNo(searchParam.getCaseOrderNo(),"=", "(", "", "OR");
			namekey.setPieceOrderNo(searchParam.getPieceOrderNo(),"=", "", ")", "AND");			
		}
		
		//#CM712569
		// Case Order only:		
		if(!StringUtil.isBlank(searchParam.getCaseOrderNo()) && StringUtil.isBlank(searchParam.getPieceOrderNo()))
		{
			searchkey.setCaseOrderNo(searchParam.getCaseOrderNo());
			namekey.setCaseOrderNo(searchParam.getCaseOrderNo());
		}
		
		//#CM712570
		// Data with Piece Order only 
		if(StringUtil.isBlank(searchParam.getCaseOrderNo()) && !StringUtil.isBlank(searchParam.getPieceOrderNo()))
		{
			searchkey.setPieceOrderNo(searchParam.getPieceOrderNo());
			namekey.setPieceOrderNo(searchParam.getPieceOrderNo());
		}
		//#CM712571
		// Both of them are blank 
		if(StringUtil.isBlank(searchParam.getCaseOrderNo()) && StringUtil.isBlank(searchParam.getPieceOrderNo()))
		{
		    //#CM712572
		    //For data at least with Case Order or Piece Order:
			searchkey.setCaseOrderNo("", "IS NOT NULL","(","","OR");
			searchkey.setPieceOrderNo("", "IS NOT NULL","",")","AND");
			namekey.setCaseOrderNo("", "IS NOT NULL","(","","OR");
			namekey.setPieceOrderNo("", "IS NOT NULL","",")","AND");
		}
			
		//#CM712573
		// Work status:
		if(!StringUtil.isBlank(searchParam.getWorkStatus()))
		{
			 if(!searchParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_ALL))
			 {
				searchkey.setStatusFlag(searchParam.getWorkStatus());
				namekey.setStatusFlag(searchParam.getWorkStatus());
			 }			
		}
		
		//#CM712574
		// Set the Conditions for obtaining data. 		
		searchkey.setConsignorCodeCollect("");
		searchkey.setConsignorNameCollect("");
		searchkey.setPlanDateCollect("");
		searchkey.setCaseOrderNoCollect("");
		searchkey.setPieceOrderNoCollect("");		
		searchkey.setItemCodeCollect("");
		searchkey.setItemName1Collect("");		
		searchkey.setCasePieceFlagCollect("");
		searchkey.setEnteringQtyCollect("");
		searchkey.setBundleEnteringQtyCollect("");
		searchkey.setPlanQtyCollect("");
		searchkey.setResultQtyCollect("");
		searchkey.setCustomerCodeCollect("");
		searchkey.setCustomerName1Collect("");
		searchkey.setCaseLocationCollect("");
		searchkey.setPieceLocationCollect("");
		searchkey.setStatusFlagCollect("");
		
		//#CM712575
		// Set the sorting order. 	
		searchkey.setPlanDateOrder(1,true);
		searchkey.setCustomerCodeOrder(2,true);
		searchkey.setCaseOrderNoOrder(3,true);
		searchkey.setPieceOrderNoOrder(4,true);
		searchkey.setItemCodeOrder(5,true);
		searchkey.setCasePieceFlagOrder(6,true);
		searchkey.setCaseLocationOrder(7, true);
		searchkey.setPieceLocationOrder(8, true);

		wFinder = new RetrievalPlanFinder(wConn);
		//#CM712576
		// Open the cursor.
		wFinder.open();
		int count = ((RetrievalPlanFinder)wFinder).search(searchkey);
		//#CM712577
		// Initialize. 
		wLength = count;
		wCurrent = 0;
		
		//#CM712578
		// Obtain the latest Consignor Name. 
		namekey.setConsignorNameCollect("");
		namekey.setRegistDateOrder(1, false);
		
		RetrievalPlanFinder consignorFinder = new RetrievalPlanFinder(wConn);
		consignorFinder.open();
		int nameCount =  consignorFinder.search(namekey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			RetrievalPlan retPlan[] = (RetrievalPlan[]) consignorFinder.getEntities(0,1);

			if (retPlan != null && retPlan.length != 0)
			{
				wConsignorName = retPlan[0].getConsignorName();
			}
		}
		consignorFinder.close();

	}
	

	//#CM712579
	/**
	 * Allow this method to set the RetrievalPlan Entity in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param retrievalPlan Picking Plan Info 
	 * @return RetrievalSupportParameter []  Array of <CODE>RetrievalSupportParameter</CODE> class that has set Picking Plan Info.
	 */
	private RetrievalSupportParameter[] convertToRetrievalSupportParams(RetrievalPlan[] retrievalPlan)
	{	
		RetrievalSupportParameter[] retrievalSupport = new RetrievalSupportParameter[retrievalPlan.length];
		for(int i=0;i<retrievalPlan.length;++i)
		{
			retrievalSupport [i] = new RetrievalSupportParameter();
			
			retrievalSupport [i].setConsignorCode(retrievalPlan[i].getConsignorCode());
			retrievalSupport [i].setConsignorName(wConsignorName);
			retrievalSupport [i].setRetrievalPlanDate(retrievalPlan[i].getPlanDate());
			retrievalSupport [i].setCustomerCode(retrievalPlan[i].getCustomerCode());
			retrievalSupport [i].setCustomerName(retrievalPlan[i].getCustomerName1());
			retrievalSupport [i].setCaseLocation(retrievalPlan[i].getCaseLocation());
			retrievalSupport [i].setPieceLocation(retrievalPlan[i].getPieceLocation());
			retrievalSupport [i].setStatusFlagL(retrievalPlan[i].getStatusFlag());
			retrievalSupport [i].setCaseOrderNo(retrievalPlan[i].getCaseOrderNo());
			retrievalSupport [i].setPieceOrderNo(retrievalPlan[i].getPieceOrderNo());
			retrievalSupport [i].setItemCode(retrievalPlan[i].getItemCode());
			retrievalSupport [i].setItemName(retrievalPlan[i].getItemName1());			
			retrievalSupport [i].setCasePieceflgName(retrievalPlan[i].getCasePieceFlag());
			retrievalSupport [i].setBundleEnteringQty(retrievalPlan[i].getBundleEnteringQty());			
			int entering_Qty = retrievalPlan[i].getEnteringQty();
			retrievalSupport [i].setEnteringQty(entering_Qty);
			
			if(retrievalPlan[i].getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_PIECE))
			{
				retrievalSupport[i].setPlanCaseQty(0);
				retrievalSupport[i].setPlanPieceQty(retrievalPlan[i].getPlanQty());
				retrievalSupport[i].setResultCaseQty(0);
				retrievalSupport[i].setResultPieceQty(retrievalPlan[i].getResultQty());
			}
			else
			{
				retrievalSupport[i].setPlanCaseQty(DisplayUtil.getCaseQty(retrievalPlan[i].getPlanQty(), retrievalPlan[i].getEnteringQty()));
				retrievalSupport[i].setPlanPieceQty(DisplayUtil.getPieceQty(retrievalPlan[i].getPlanQty(), retrievalPlan[i].getEnteringQty()));
				retrievalSupport[i].setResultCaseQty(DisplayUtil.getCaseQty(retrievalPlan[i].getResultQty(), retrievalPlan[i].getEnteringQty()));
				retrievalSupport[i].setResultPieceQty(DisplayUtil.getPieceQty(retrievalPlan[i].getResultQty(), retrievalPlan[i].getEnteringQty()));
			}
				
		}
		return retrievalSupport;
		
	}
	
}
//#CM712580
//end of class
