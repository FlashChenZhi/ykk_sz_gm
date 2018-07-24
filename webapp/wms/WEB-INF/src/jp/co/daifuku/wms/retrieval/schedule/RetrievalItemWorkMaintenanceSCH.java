package jp.co.daifuku.wms.retrieval.schedule;

//#CM723482
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.WorkDetails;

//#CM723483
/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida  <BR>
 * <BR>
 * Allow this class to execute maintenance of WEB Item Picking Work.  <BR>
 * Receive the contents entered via screen as a parameter, and execute the process for maintenance of the Item Picking Work Maintenance.  <BR>
 * Allow each methods contained in this class to receive a connection object and executes the process for updating the database, but <BR>
 * disable to commit nor roll-back the transaction.  <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind()</CODE> method)  <BR><BR><DIR>
 *   Return the corresponding Consignor Code, if only one Consignor Code exists in the Work Status Info(Picking).  <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
 * <BR>
 *   <Search conditions> <BR><DIR>
 *     Status flag:other than "Deleted" (9)  <BR>
 *     Work type: Picking. (3)  <BR>
 *     Order No.: NULL  </DIR></DIR>
 * <BR>
 * 2. Process by clicking "Display" button (<CODE>query()</CODE> method) <BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it. <BR>
 *   If no corresponding data is found, return <CODE>Parameter</CODE> array with number of elements equal to 0. Return null when condition error occurs. <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error <BR>
 *   Display data in the order of Item Code, Case/Piece division, Picking Location, and then Status.  <BR>
 * <BR>
 *   <Search conditions> <BR><DIR>
 *     Work type: Picking. (3)  <BR>
 *     Order No.: NULL  </DIR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR><DIR>
 *     Worker Code* <BR>
 *     Password * <BR>
 *     Consignor Code* <BR>
 *     Work status:* <BR>
 *     Planned Picking Date* <BR>
 *     Item Code <BR>
 *     Case/Piece division* <BR></DIR>
 * <BR>
 *   <Returned data> <BR><DIR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     Planned Picking Date <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Packed Qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Planned Case Qty  <BR>
 *     Planned Piece Qty  <BR>
 *     Result Case Qty  <BR>
 *     Result Piece Qty  <BR>
 *     Picking Location  <BR>
 *     Work status (listcell)  <BR>
 *     Expiry Date (result_use_by_date)<BR>
 *     Case/Piece division<BR>
 *     Case/Piece division name <BR>
 *     Result Report flag <BR>
 *     Work place <BR>
 *     Worker Code <BR>
 *     Worker Name <BR>
 *     Work No. <BR>
 *     Last update date/time <BR></DIR></DIR>
 * <BR>
 * 3. Process by clicking Modify/Add, or Cancel All Working button (<CODE>startSCHgetParams()</CODE> method)  <BR><BR><DIR>
 *   Receive the contents displayed in the preset area, as a parameter, and execute the process of the Picking Work maintenance.  <BR>
 *   Clicking on 0 button for parameter executes Modify or Submit/Add process. Clicking on 1 button executes Cancel All Working process.  <BR>
 *   Re-obtain the data to be output to the preset area from database when the process normally completed, and return it.  <BR>
 *   Return null when the schedule failed to complete due to condition error or other causes.  <BR>
 *   If changed the Working data status, search through the RFT Work Status Info and update the electronic statement field item of the working terminal to null. <BR> 
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error  <BR>
 *   Update the Work Status Info, Plan info, and then Inventory Info in this sequence. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker Code* <BR>
 *     Password * <BR>
 *     Work No.* <BR>
 *     Consignor Code* (for search through Preset again)  <BR>
 *     Work status:* (for search through Preset again)  <BR>
 *     Planned Picking Date* (for search through Preset again)  <BR>
 *     Item Code <BR>
 *     Packed Qty per Case <BR>
 *     Result Case Qty  <BR>
 *     Result Piece Qty  <BR>
 *     Picking Location <BR>
 *     Work status (listcell) * <BR>
 *     Expiry Date <BR>
 *     Last update date/time* <BR>
 *     Button type * <BR>
 *     Terminal No.* <BR>
 *     Case/Piece division* (for search through Preset again)  <BR>
 *     Preset line No. * <BR></DIR>
 * <BR>
 *   <Returned data> <BR><DIR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     Planned Picking Date <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Packed Qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Planned Case Qty  <BR>
 *     Planned Piece Qty  <BR>
 *     Result Case Qty  <BR>
 *     Result Piece Qty  <BR>
 *     Picking Location <BR>
 *     Work status (listcell)  <BR>
 *     Expiry Date <BR>
 *     Case/Piece division<BR>
 *     Case/Piece division name <BR>
 *     Worker Code <BR>
 *     Worker Name <BR>
 *     Work No. <BR>
 *     Last update date/time <BR></DIR></DIR>
 * <BR>
 *   <Modify/Add process>  <BR>
 * <DIR>
 *   <Check Process Condition>  <BR>
 *     1.Require that the Worker Code and the Password are defined in the Worker master.  <BR><DIR>
 *       Check only the leading value of the array for the values of Worker code and password.  <BR></DIR>
 *     2. Ensure that the Work Status Info table of work No. exist in the database.  <BR>
 *     3.Require the parameter's Last update date/time to be consistent with the Last update date/time in the Work Status Info table (Check Exclusion.).  <BR>
 * <BR>
 *   <Update Process> <BR>
 *     -Update the Picking Plan Info table (DNRETRIEVALPLAN).  <BR>
 *       1.To update the Status flag Standby to Started or Working:  <BR><DIR>
 *         Only for Picking Plan Info data with status flag Standby, update the status flag to Working.  <BR></DIR>
 *       2. To update the status flag Working to Standby:  <BR><DIR>
 *         Update the status flag.<BR><DIR>
 *           - All the linked Work Status Info with Standby: Standby  <BR>
 *           - If there is one or more data with status Working in the linked Work Status Info: Working  <BR>
 *           - Regard data with both status Standby and Completed in its linked Work Status Info as Partially Completed.  <BR></DIR></DIR> <BR>
 *       3. To update status flag Working to Completed, or Standby to Completed  <BR><DIR>
 *         Update the status flag.<BR><DIR>
 *           - If all the linked Work Status Info is Completed: Completed <BR>
 *           - If there is one or more data with status Working in the linked Work Status Info: Working <BR>
 *           - Regard data with both status Standby and Completed in its linked Work Status Info as Partially Completed. <BR>
 *           </DIR> <BR>
 *         Assign the Picking Result qty of parameter to the Picking Result qty of the Picking Plan Info. <BR>
 *         Update the Picking shortage qty to the value obtained by decreasing the picking result qty of the parameter from the workable picking qty of the Work Status Info.  <BR></DIR>
 *       4. To update status flag Completed to Completed  <BR><DIR>
 *         Update the Picking Result qty to the value of the Picking Result qty of the parameter. <BR>
 *         Add the shortage qty this time to the shortage qty of the Plan info. <BR></DIR>
 *       5. Update the last Update process name.  <BR>
 * <BR>
 *     - Update the Work Status Info Table (DNWORKINFO)  <BR>
 *       1. To update the status flag Standby to Working, or Working to Standby:  <BR><DIR>
 *         Update the status flag to the value of status flag of the parameter.  <BR></DIR>
 *       2. To update status flag Working to Completed, or Standby to Completed:  <BR><DIR>
 *         Sum-up the Picking Result qty of parameter to the Work Result qty of the Work status. <BR>
 *         Update the work shortage qty to the value obtained by decreasing Picking result qty of the parameter from the Planned Work qty.  <BR>
 *         If the expiry date is set for the parameter, update the expiry date to the contents (result_use_by_date) to the value in this content. <BR>
 *         If Picking Location is set for the parameter, update the Location (result_location_no) to the value in this content. <BR>
 *         Note)Allow the Complete Picking Process to update the Inventory Info (DMSTOCK) and add the Result data (HOSTSEND). <BR></DIR>
 *       3. To update status flag Completed to Completed  <BR><DIR>
 *         Assign the Picking Result qty of parameter to the Work Result qty of the Work status. <BR>
 *         Update the work shortage qty to the value obtained by decreasing Picking result qty of the parameter from the Planned Work qty.  <BR>
 *         If Expiry Date and Picking Location are set for the parameter, update the Expiry Date(result_use_by_date) and Picking Location (result_location_no) to the values in this content. <BR>
 *         Note)Update the Result qty in the range within the value (workable Qty) - (Pending Qty).  <BR></DIR>
 *       4. Update the last Update process name.  <BR>
 * <BR>
 *     -Update the Inventory Info table (DNSTOCK) (Execute it in the process for completing Picking). Note) Updating from Completed to Completed disables to change in the Inventory Info.  <BR>
 *      For detail, refer to the <code>RetrievalCompleteOperator</code>.  <BR>
 * <BR>
 *     - Add Result Sent Information Table (DNHOSTSEND)  <BR>
 *       Updating from the status flag from Completed to Completed generates a Result Sent Information to offset the resided result, and then generate the result added on the screen.  <BR>
 *       Changing the status from Standby or Working to Completed generates a Result as Processingto complete the picking. See < code>RetrievalCompleteOperator</code > for details. <BR>
 * <BR>
 *     - Update/Add Worker Result Data Inquiry Table (DNWORKERRESULT).  <BR>
 *       To update to Completed, update and add the Worker Result data inquiry based on the contents in the parameter.  <BR></DIR>
 * <BR>
 *   <Process to cancel all "Working">  <BR>
 * <DIR>
 *   <Check Process Condition>  <BR>
 *     1.Require that the Worker Code and the Password are defined in the Worker master.  <BR><DIR>
 *       Check only the leading value of the array for the values of Worker code and password.  <BR></DIR>
 *     2. Ensure that the Work Status Info table of work No. exist in the database.  <BR>
 *     3. Ensure that the status flag of the corresponding Work Status Info table is Working.  <BR>
 *     4.Require that the value of the Last update date/time matches the value of the last update date/time in the Work Status Info table. (Check Exclusion.)  <BR>
 * <BR>
 *   <Update Process> Update Work Status Info and Plan Info in this sequence. <BR>
 *     - Update the Work Status Info Table (DNWORKINFO)  <BR>
 *       Update the Work Status Info based on the contents of the received parameter.  <BR><DIR>
 *         Update the status flag to Standby.  <BR>
 *         Update the last Update process name.  <BR></DIR>
 * <BR>
 *     -Update the Picking Plan Info table (DNRETRIEVALPLAN).  <BR>
 *       Only for data of Picking Plan Info with Status flag Working, update the Picking Plan Info based on the content of the received parameter. <BR><DIR>
 *         Update as below.  <BR>
 *         - All the linked Work Status Info with Standby: Standby  <BR>
 *         - If there is one or more data with status Working in the linked Work Status Info: Working  <BR>
 *         - Regard data with both status Standby and Completed in its linked Work Status Info as Partially Completed. </DIR>
 * <BR>
 * </DIR>
 * </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/19</TD><TD>S.Yoshida</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:56 $
 * @author  $Author: suresh $
 */
public class RetrievalItemWorkMaintenanceSCH extends RetrievalOrderWorkMaintenanceSCH
{

	//#CM723484
	// Class variables -----------------------------------------------
	//#CM723485
	/**
	 * Process name 
	 */
	protected static final String wProcessName = "RetrievalWorkMaintenance";

	//#CM723486
	// Class method --------------------------------------------------
	//#CM723487
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:56 $");
	}
	//#CM723488
	// Constructors --------------------------------------------------
	//#CM723489
	/**
	 * Initialize this class. 
	 */
	public RetrievalItemWorkMaintenanceSCH()
	{
		wMessage = null;
		
		//#CM723490
		// Specify the details of work type to delete a Working data file. 
		workDetails = WorkDetails.RETRIEVAL_ITEM;
	}

	//#CM723491
	// Public methods ------------------------------------------------
	//#CM723492
	/**
	 * Allow this method to support the operation to obtain the data required for initial display. <BR>
	 * For Work Status Info with only one Consignor Code exists, return the corresponding Consignor Code. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. 
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed<BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. <BR>
	 * @return Class that implements the <CODE>Parameter</CODE> interface that contains the search result. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		//#CM723493
		// Set the corresponding Consignor code. 
		RetrievalSupportParameter wParam = new RetrievalSupportParameter();

		//#CM723494
		// Generate instance of Work Status Info handlers. 
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformation[] wWorkinfo = null;

		//#CM723495
		// Set the search conditions. 
		//#CM723496
		// Status flag:other than "Deleted" 
		wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		//#CM723497
		// Work type: Picking. (3) 
		wKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM723498
		// Order No.: NULL 
		wKey.setOrderNo("");
		//#CM723499
		// Set the Consignor Code for grouping condition. 
		wKey.setConsignorCodeGroup(1);
		wKey.setConsignorCodeCollect("DISTINCT");
		
		//#CM723500
		// If the count is one: 
		if (wObj.count(wKey) == 1)
		{
			//#CM723501
			// Obtain the count of the corresponding Consignor Code data. 
			wWorkinfo = (WorkingInformation[]) wObj.find(wKey);
	
			if (wWorkinfo != null && wWorkinfo.length == 1)
			{
				//#CM723502
				// Obtain the corresponding Consignor Code and set it for the return parameter. 
				wParam.setConsignorCode(wWorkinfo[0].getConsignorCode());
			}
		}
		return wParam;
	}

	//#CM723503
	/**
	 * Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it. <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed<BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. <BR>
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record is found, return the array with number of elements equal to 0. <BR>
	 *          Return null if the search result count exceeds 1000 or when error occurs with input condition. <BR>
	 *          Returning array with number of elements 0 (zero) or null allows to obtain the error contents as a message using <CODE>getMessage()</CODE> method. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		//#CM723504
		// search result
		RetrievalSupportParameter[] wSParam = null;
		//#CM723505
		// Consignor Name
		String wConsignorName = "";

		//#CM723506
		// Generate instance of Work Status Info handlers. 
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationSearchKey wNameKey = new WorkingInformationSearchKey();
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformation[] wWorkinfo = null;
		WorkingInformation[] wWorkinfoName = null;

		
		//#CM723507
		// Search conditions for parameters 
		RetrievalSupportParameter wParam = (RetrievalSupportParameter)searchParam;

		//#CM723508
		// Check the Worker code and password 
		if (!checkWorker(conn, wParam))
		{
			return null;
		}
			
		//#CM723509
		// Check for mandatory input. 
		if (StringUtil.isBlank(wParam.getWorkerCode()) ||
			StringUtil.isBlank(wParam.getPassword()) ||
			StringUtil.isBlank(wParam.getConsignorCode()) ||
			StringUtil.isBlank(wParam.getRetrievalPlanDate()))
		{
			throw (new ScheduleException("mandatory error!"));
		}
			
		//#CM723510
		// Set the search conditions. 
		//#CM723511
		// Work type: Picking. (3) 
		wKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		wNameKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM723512
		// Order No.: NULL 
		wKey.setOrderNo("");
		wNameKey.setOrderNo("");
		//#CM723513
		// Consignor Code
		wKey.setConsignorCode(wParam.getConsignorCode());
		wNameKey.setConsignorCode(wParam.getConsignorCode());
		//#CM723514
		// Work status:
		if (wParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_ALL))
		{
			//#CM723515
			// Display the Standby, Started, Working, or Completed data if work status of the parameter is All. 
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "<=", "(", "", "OR");
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION, "=", "", ")", "AND");
			wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "<=", "(", "", "OR");
			wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION, "=", "", ")", "AND");
		}
		else if (wParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
		{
			//#CM723516
			// Completed 
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
			wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
		}
		else if (wParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
		{
			//#CM723517
			// Standby 
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		}
		else if (wParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
		{
			//#CM723518
			// "Started" 
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_START);
			wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_START);
		}
		else if (wParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
		{
			//#CM723519
			// Working 
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		}
		//#CM723520
		// Planned Picking Date
		wKey.setPlanDate(wParam.getRetrievalPlanDate());
		wNameKey.setPlanDate(wParam.getRetrievalPlanDate());
		//#CM723521
		// Item Code
		if (!StringUtil.isBlank(wParam.getItemCode()))
		{
			wKey.setItemCode(wParam.getItemCode());
			wNameKey.setItemCode(wParam.getItemCode());
		}
		//#CM723522
		// Case/Piece division
		if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
		{
			//#CM723523
			// Case 
			wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			wNameKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
		}
		else if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM723524
			// Piece 
			wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
			wNameKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
		}
		else if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM723525
			// None 
			wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
			wNameKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
		}
		
		//#CM723526
		// Set the sorting order: in the order of Item Code, Case/Piece division, Picking Location, Status, and then Expiry Date. 
		wKey.setItemCodeOrder(1, true);
		wKey.setWorkFormFlagOrder(2, true);
		wKey.setLocationNoOrder(3, true);
		wKey.setStatusFlagOrder(4, true);
        wKey.setResultUseByDateOrder(5, true);
        wKey.setUseByDateOrder(6, true);

		//#CM723527
		// Obtain the data count of the corresponding Work Status Info data. 
		if(!canLowerDisplay(wObj.count(wKey)))
		{
			return returnNoDisplayParameter();
		}
			
		//#CM723528
		// Obtain the Work Status Info. 
		wWorkinfo = (WorkingInformation[]) wObj.find(wKey);

		//#CM723529
		// Obtain the Consignor Name with the latest Added Date/Time. 
		wNameKey.setRegistDateOrder(1, false);
		wWorkinfoName = (WorkingInformation[]) wObj.find(wNameKey);
		if (wWorkinfoName != null && wWorkinfoName.length > 0)
		{
			wConsignorName = wWorkinfoName[0].getConsignorName();
		}

		//#CM723530
		// Generate a Vector instance 
		Vector vec = new Vector();

		//#CM723531
		// Set the search result for the return parameter. 
		for (int i = 0; i < wWorkinfo.length; i++)
		{
			RetrievalSupportParameter wTemp = new RetrievalSupportParameter();
			//#CM723532
			// Consignor Code
			wTemp.setConsignorCode(wWorkinfo[i].getConsignorCode());
			//#CM723533
			// Consignor Name
			wTemp.setConsignorName(wConsignorName);
			//#CM723534
			// Planned Picking Date
			wTemp.setRetrievalPlanDate(wWorkinfo[i].getPlanDate());
			//#CM723535
			// Item Code
			wTemp.setItemCode(wWorkinfo[i].getItemCode());
			//#CM723536
			// Item Name
			wTemp.setItemName(wWorkinfo[i].getItemName1());
			//#CM723537
			// Packed Qty per Case
			wTemp.setEnteringQty(wWorkinfo[i].getEnteringQty());
			//#CM723538
			// Packed qty per bundle
			wTemp.setBundleEnteringQty(wWorkinfo[i].getBundleEnteringQty());
			//#CM723539
			// Planned Case Qty 
			wTemp.setPlanCaseQty(DisplayUtil.getCaseQty(wWorkinfo[i].getPlanEnableQty(), wWorkinfo[i].getEnteringQty(),wWorkinfo[i].getWorkFormFlag()));
			//#CM723540
			// Planned Piece Qty 
			wTemp.setPlanPieceQty(DisplayUtil.getPieceQty(wWorkinfo[i].getPlanEnableQty(), wWorkinfo[i].getEnteringQty(),wWorkinfo[i].getWorkFormFlag()));
			//#CM723541
			// Result Case Qty 
			wTemp.setResultCaseQty(DisplayUtil.getCaseQty(wWorkinfo[i].getResultQty(), wWorkinfo[i].getEnteringQty(),wWorkinfo[i].getWorkFormFlag()));
			//#CM723542
			// Result Piece Qty 
			wTemp.setResultPieceQty(DisplayUtil.getPieceQty(wWorkinfo[i].getResultQty(), wWorkinfo[i].getEnteringQty(),wWorkinfo[i].getWorkFormFlag()));
			//#CM723543
			// Picking Location 
			wTemp.setRetrievalLocation(wWorkinfo[i].getLocationNo());
			//#CM723544
			// Status flag
			if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART))
			{
				//#CM723545
				// Standby 
				wTemp.setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
			}
			else if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START))
			{
				//#CM723546
				// "Started" 
				wTemp.setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_STARTED);
			}
			else if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
			{
				//#CM723547
				// Working 
				wTemp.setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_WORKING);
			}
			else if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
			{
				//#CM723548
				// Completed 
				wTemp.setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
			}
			//#CM723549
			// Expiry Date
			if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
			{
				wTemp.setUseByDate(wWorkinfo[i].getResultUseByDate());
			}
			else
			{
				wTemp.setUseByDate(wWorkinfo[i].getUseByDate());
			}
			//#CM723550
			// Case/Piece division
			if (wWorkinfo[i].getWorkFormFlag().equals(WorkingInformation.CASEPIECE_FLAG_CASE))
			{
				//#CM723551
				// Case 
				wTemp.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
				wTemp.setCasePieceflgName(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_CASE));
			}
			else if (wWorkinfo[i].getWorkFormFlag().equals(WorkingInformation.CASEPIECE_FLAG_PIECE))
			{
				//#CM723552
				// Piece 
				wTemp.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
				wTemp.setCasePieceflgName(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_PIECE));
			}
			else if (wWorkinfo[i].getWorkFormFlag().equals(WorkingInformation.CASEPIECE_FLAG_NOTHING))
			{
				//#CM723553
				// None 
				wTemp.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
				wTemp.setCasePieceflgName(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_NOTHING));
			}
			//#CM723554
			// Result Report flag 
			if (wWorkinfo[i].getReportFlag().equals(WorkingInformation.REPORT_FLAG_NOT_SENT))
			{
				//#CM723555
				// Not Reported 
				wTemp.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);
				wTemp.setReportFlagName(DisplayUtil.getReportFlagValue(WorkingInformation.REPORT_FLAG_NOT_SENT));
			}
			else
			{
				//#CM723556
				// Reported 
				wTemp.setReportFlag(WorkingInformation.REPORT_FLAG_SENT);
				wTemp.setReportFlagName(DisplayUtil.getReportFlagValue(WorkingInformation.REPORT_FLAG_SENT));
			}
			if(!wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
			{
				//#CM723557
				// Work place 
				if (wWorkinfo[i].getSystemDiscKey() == (RetrievalSupportParameter.SYSTEM_DISC_KEY_FLOOR))
				{
					wTemp.setSystemDiscKey(RetrievalSupportParameter.SYSTEM_DISC_KEY_FLOOR);
					wTemp.setSystemDiscKeyName(DisplayText.getText("LBL-W0078"));
				}			
				else if(wWorkinfo[i].getSystemDiscKey() == (RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS))
				{
					wTemp.setSystemDiscKey(RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS);
					wTemp.setSystemDiscKeyName(DisplayText.getText("LBL-W0078"));
				}
				else if(wWorkinfo[i].getSystemDiscKey() == (RetrievalSupportParameter.SYSTEM_DISC_KEY_IDM))
				{
					wTemp.setSystemDiscKey(RetrievalSupportParameter.SYSTEM_DISC_KEY_IDM);
					wTemp.setSystemDiscKeyName(DisplayText.getText("LBL-W0078"));
				}	
			}
			else
			{

				//#CM723558
				// Work place 
				if (wWorkinfo[i].getSystemDiscKey() == (RetrievalSupportParameter.SYSTEM_DISC_KEY_FLOOR))
				{
					wTemp.setSystemDiscKey(RetrievalSupportParameter.SYSTEM_DISC_KEY_FLOOR);
					wTemp.setSystemDiscKeyName(DisplayUtil.getSystemDiscKeyValue(RetrievalSupportParameter.SYSTEM_DISC_KEY_FLOOR));
				}			
				else if(wWorkinfo[i].getSystemDiscKey() == (RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS))
				{
					wTemp.setSystemDiscKey(RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS);
					wTemp.setSystemDiscKeyName(DisplayUtil.getSystemDiscKeyValue(RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS));
				}
				else if(wWorkinfo[i].getSystemDiscKey() == (RetrievalSupportParameter.SYSTEM_DISC_KEY_IDM))
				{
					wTemp.setSystemDiscKey(RetrievalSupportParameter.SYSTEM_DISC_KEY_IDM);
					wTemp.setSystemDiscKeyName(DisplayUtil.getSystemDiscKeyValue(RetrievalSupportParameter.SYSTEM_DISC_KEY_IDM));
				}	
			}
			
			//#CM723559
			// Worker Code
			wTemp.setWorkerCode(wWorkinfo[i].getWorkerCode());
			//#CM723560
			// Worker Name
			wTemp.setWorkerName(wWorkinfo[i].getWorkerName());
			//#CM723561
			// Work No.
			wTemp.setJobNo(wWorkinfo[i].getJobNo());
			//#CM723562
			// Last update date/time
			wTemp.setLastUpdateDate(wWorkinfo[i].getLastUpdateDate());
			vec.addElement(wTemp);
		}

		wSParam = new RetrievalSupportParameter[vec.size()];
		vec.copyInto(wSParam);

		//#CM723563
		// 6001013 = Data is shown. 
		wMessage = "6001013";
			
		return wSParam;
		
	}

	
	//#CM723564
	// Package methods -----------------------------------------------

	//#CM723565
	// Protected methods ---------------------------------------------
	
	//#CM723566
	// Private methods -----------------------------------------------

}
//#CM723567
//end of class
