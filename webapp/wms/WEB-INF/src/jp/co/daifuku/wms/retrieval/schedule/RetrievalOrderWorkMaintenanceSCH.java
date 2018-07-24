package jp.co.daifuku.wms.retrieval.schedule;

//#CM724406
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdMessage;
import jp.co.daifuku.wms.base.rft.WorkDetails;

//#CM724407
/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida  <BR>
 * <BR>
 * Allow this class to execute maintenance of WEB Order Picking Work.  <BR>
 * Receive the contents entered via screen as a parameter, and execute the process for maintenance of the Order Picking Work.  <BR>
 * Allow each method contained in this class to receive the Connection object and execute the process for updating the database, but
 * disable to commit nor roll-back the transaction.  <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *   Return the corresponding Consignor Code, if only one Consignor Code exists in the Work Status Info(Picking).  <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
 * <BR>
 *   <Search conditions> <BR>
 *   <DIR>
 *     Status flag:other than "Deleted" (9)  <BR>
 *     Work type: Picking. (3)  <BR>
 *     Order No.: other than NULL  <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Display" button (<CODE>query</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it. <BR>
 *   If no corresponding data is found, return <CODE>Parameter</CODE> array with number of elements equal to 0. Return null when condition error occurs. <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error.<BR>
 *   Display data in the order of Item Code, Case/Piece division, Picking Location, Status, and then Order No.  <BR>
 * <BR>
 *   <Search conditions> <BR>
 *   <DIR>
 *     Status flag:other than "Deleted" (9)  <BR>
 *     Work type: Picking. (3)  <BR>
 *     Order No.: other than NULL  <BR>
 *   </DIR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR>
 *   <DIR>
 *     Worker Code* <BR>
 *     Password * <BR>
 *     Consignor Code* <BR>
 *     Work status:* <BR>
 *     Planned Picking Date* <BR>
 *     Customer Code <BR>
 *     Order No. <BR>
 *     Case/Piece division* <BR>
 *   </DIR>
 * <BR>
 *   <Returned data> <BR>
 *   <DIR>
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
 *     Expiry Date (result_use_by_date) <BR>
 *     Case/Piece division<BR>
 *     Case/Piece division name <BR>
 *     Order No. <BR>
 *     Customer Code <BR>
 *     Customer Name <BR>
 *     Result Report <BR>
 *     Work place <BR>
 *     Worker Code <BR>
 *     Worker Name <BR>
 *     Work No. <BR>
 *     Last update date/time <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 3. Process by clicking on Modify/Add button or Cancel All Working button (<CODE>startSCHgetParams</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *   Receive the contents displayed in the preset area, as a parameter, and execute the process of the Picking Work maintenance.  <BR>
 *   Clicking on 0 button for parameter executes Modify or Submit/Add process. Clicking on 1 button executes Cancel All Working process.  <BR>
 *   Re-obtain the data to be output to the preset area from database when the process normally completed, and return it.  <BR>
 *   Return null when the schedule failed to complete due to condition error or other causes.  <BR>
 *   If changed the Working data status, search through the RFT Work Status Info and update the electronic statement field item of the working terminal to null. <BR>
 *   Search for the data preservation information under Working. If the corresponding data when such data is found, delete its record.<BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error. <BR>
 *   Update the Work Status Info, Plan info, and then Inventory Info in this sequence. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR>
 *   <DIR>
 *     Worker Code* <BR>
 *     Password * <BR>
 *     Work No.* <BR>
 *     Consignor Code* (for search through Preset again)  <BR>
 *     Work status:* (for search through Preset again)  <BR>
 *     Planned Picking Date* (for search through Preset again)  <BR>
 *     Packed Qty per Case* <BR>
 *     Result Case Qty  <BR>
 *     Result Piece Qty  <BR>
 *     Picking Location  <BR>
 *     Work status (listcell) * <BR>
 *     Expiry Date <BR>
 *     Order No. <BR>
 *     Customer Code <BR>
 *     Last update date/time* <BR>
 *     Button type * <BR>
 *     Terminal No.* <BR>
 *     Case/Piece division* (for search through Preset again)  <BR>
 *     Preset line No. * <BR>
 *   </DIR>
 * <BR>
 *   <Returned data> <BR>
 *   <DIR>
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
 *     Expiry Date <BR>
 *     Case/Piece division<BR>
 *     Case/Piece division name <BR>
 *     Order No. <BR>
 *     Customer Code <BR>
 *     Customer Name <BR>
 *     Result Report <BR>
 *     Work place <BR>
 *     Worker Code <BR>
 *     Worker Name <BR>
 *     Work No. <BR>
 *     Last update date/time <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 *   <Modify/Add process>  <BR>
 * <DIR>
 *   <Check Process Condition>  <BR>
 *     1.Require that the Worker Code and the Password are defined in the Worker master.  <BR><DIR>
 *       Check only the leading value of the array for the values of Worker code and password.  <BR></DIR>
 *     2. Ensure that the Work Status Info table of work No. exist in the database.  <BR>
 *     3.Require the parameter's Last update date/time to be consistent with the Last update date/time in the Work Status Info table (Check Exclusion.).  <BR>
 *     4. Ensure that AS/RS work is in status other than Completed. <BR>
 * <BR>
 *   <Update Process> <BR>
 *     -Update the Picking Plan Info table (DNRETRIEVALPLAN).  <BR>
 *       1. To update the status flag Standby to Working or Started:  <BR><DIR>
 *         Only for Picking Plan Info data with status flag Standby, update the status flag to Working.  <BR></DIR>
 *       2. To update the status flag Working to Standby:  <BR><DIR>
 *         Update the status flag.<BR><DIR>
 *           - All the linked Work Status Info with Standby: Standby  <BR>
 *           - If there is one or more data with status Working in the linked Work Status Info: Working  <BR>
 *           - Regard data with both status Standby and Completed in its linked Work Status Info as Partially Completed.  <BR></DIR></DIR><BR>
 *       3. To update status flag Working to Completed, or Standby to Completed  <BR><DIR>
 *         Update the status flag.<BR><DIR>
 *           - If all the linked Work Status Info is Completed: Completed  <BR>
 *           - If there is one or more data with status Working in the linked Work Status Info: Working  <BR>
 *           - Regard data with both status Standby and Completed in its linked Work Status Info as Partially Completed.  <BR>
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
 *       To update to Completed, update and add the Worker Result data inquiry based on the contents in the parameter.  <BR>
 * <BR>
 *     - Delete the Working data file of the terminal which updated the data. <BR>
 * <BR>
 *   <Process to cancel all "Working">  <BR>
 * <DIR>
 *   <Check Process Condition>  <BR>
 *     1.Require that the Worker Code and the Password are defined in the Worker master.  <BR><DIR>
 *       Check only the leading value of the array for the values of Worker code and password.  <BR></DIR>
 *     2. Ensure that the Work Status Info table of work No. exist in the database.  <BR>
 *     3. Ensure that the status flag of the corresponding Work Status Info table is Working.  <BR>
 *     4. Require that the value of the Last update date/time matches the value of the last update date/time in the Work Status Info table. (Check Exclusion.)  <BR>
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
 *     - Delete the Working data file of the terminal which updated the data. <BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/19</TD><TD>S.Yoshida</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:20:01 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderWorkMaintenanceSCH extends AbstractRetrievalSCH
{

	//#CM724408
	// Class variables -----------------------------------------------
	//#CM724409
	/**
	 * Process name 
	 */
	protected static final String wProcessName = "RetrievalWorkMaintenance";

	//#CM724410
	/**
	 * Specify the details of work type to delete a Working data file. 
	 */
	protected String workDetails = WorkDetails.RETRIEVAL_ORDER;
	
	//#CM724411
	// Class method --------------------------------------------------
	//#CM724412
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:20:01 $");
	}
	//#CM724413
	// Constructors --------------------------------------------------
	//#CM724414
	/**
	 * Initialize this class. 
	 */
	public RetrievalOrderWorkMaintenanceSCH()
	{
		wMessage = null;
	}

	//#CM724415
	// Public methods ------------------------------------------------
	//#CM724416
	/**
	 * Allow this method to support the operation to obtain the data required for initial display. <BR>
	 * For Work Status Info with only one Consignor Code exists, return the corresponding Consignor Code. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. 
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed<BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. <BR>
	 * @return Class that implements the <CODE>RetrievalSupportParameter</CODE> interface that contains the search result. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		//#CM724417
		// Set the corresponding Consignor code. 
		RetrievalSupportParameter wParam = new RetrievalSupportParameter();

		//#CM724418
		// Generate instance of Work Status Info handlers. 
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformation[] wWorkinfo = null;

		//#CM724419
		// Set the search conditions. 
		//#CM724420
		// Status flag:other than "Deleted" 
		wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		//#CM724421
		// Work type: Picking. (3) 
		wKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM724422
		// Order No.: other than NULL 
		wKey.setOrderNo("", "IS NOT NULL");
		//#CM724423
		// Set the Consignor Code for grouping condition. 
		wKey.setConsignorCodeGroup(1);
		wKey.setConsignorCodeCollect("DISTINCT");
		
		//#CM724424
		// If the count is one: 
		if (wObj.count(wKey) == 1)
		{
			//#CM724425
			// Obtain the count of the corresponding Consignor Code data. 
			wWorkinfo = (WorkingInformation[]) wObj.find(wKey);
	
			if (wWorkinfo != null && wWorkinfo.length == 1)
			{
				//#CM724426
				// Obtain the corresponding Consignor Code and set it for the return parameter. 
				wParam.setConsignorCode(wWorkinfo[0].getConsignorCode());
			}
		}
		return wParam;
	}

	//#CM724427
	/**
	 * Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it. <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed<BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. <BR>
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record is found, return the array with number of elements equal to 0. <BR>
	 *          If the number of search results exceeds the Max count of data allowable to display, or if error occurs with the input condition, return null. <BR>
	 *          Returning array with the number of elements 0 or null enables to obtain the error content as a message using the <CODE>getMessage</CODE> method. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		//#CM724428
		// search result
		RetrievalSupportParameter[] wSParam = null;
		//#CM724429
		// Consignor Name
		String wConsignorName = "";

		//#CM724430
		// Generate instance of Work Status Info handlers. 
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationSearchKey wNameKey = new WorkingInformationSearchKey();
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformation[] wWorkinfo = null;
		WorkingInformation[] wWorkinfoName = null;

		
			//#CM724431
			// Search conditions for parameters 
			RetrievalSupportParameter wParam = (RetrievalSupportParameter)searchParam;

			//#CM724432
			// Check the Worker code and password 
			if (!checkWorker(conn, wParam))
			{
				return null;
			}
			
			//#CM724433
			// Check for mandatory input. 
			if (StringUtil.isBlank(wParam.getWorkerCode()) ||
			    StringUtil.isBlank(wParam.getPassword()) ||
			    StringUtil.isBlank(wParam.getConsignorCode()) ||
			    StringUtil.isBlank(wParam.getRetrievalPlanDate()))
			{
				throw (new ScheduleException("mandatory error!"));
			}
			
			//#CM724434
			// Set the search conditions. 
			//#CM724435
			// Work type: Picking. (3) 
			wKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			wNameKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			//#CM724436
			// Consignor Code
			wKey.setConsignorCode(wParam.getConsignorCode());
			wNameKey.setConsignorCode(wParam.getConsignorCode());
			//#CM724437
			// Work status:
			if (wParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_ALL))
			{
				//#CM724438
				// Display the Standby, Started, Working, or Completed data if work status of the parameter is All. 
				wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "<=", "(", "", "OR");
				wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION, "=", "", ")", "AND");
				wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "<=", "(", "", "OR");
				wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION, "=", "", ")", "AND");
			}
			else if (wParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
			{
				//#CM724439
				// Completed 
				wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
				wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
			}
			else if (wParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
			{
				//#CM724440
				// Standby 
				wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
				wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			}
			else if (wParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
			{
				//#CM724441
				// "Started" 
				wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_START);
				wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_START);
			}
			else if (wParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
			{
				//#CM724442
				// Working 
				wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
				wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			}
			//#CM724443
			// Planned Picking Date
			wKey.setPlanDate(wParam.getRetrievalPlanDate());
			wNameKey.setPlanDate(wParam.getRetrievalPlanDate());
			//#CM724444
			// Customer Code
			if (!StringUtil.isBlank(wParam.getCustomerCode()))
			{
				wKey.setCustomerCode(wParam.getCustomerCode());
				wNameKey.setCustomerCode(wParam.getCustomerCode());
			}
			//#CM724445
			// Order No.
			if (!StringUtil.isBlank(wParam.getOrderNo()))
			{
				wKey.setOrderNo(wParam.getOrderNo());
				wNameKey.setOrderNo(wParam.getOrderNo());
			}
			else
			{
				wKey.setOrderNo("", "IS NOT NULL");
				wNameKey.setOrderNo("", "IS NOT NULL");
			}
			//#CM724446
			// Case/Piece division
			if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				//#CM724447
				// Case 
				wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
				wNameKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			}
			else if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				//#CM724448
				// Piece 
				wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
				wNameKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
			}
			else if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				//#CM724449
				// None 
				wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
				wNameKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
			}

			//#CM724450
			// Set the sorting order: in the order of Item Code, Case/Piece division, Picking Location, Status, Expiry Date, and then Order No. 
			wKey.setItemCodeOrder(1, true);
			wKey.setWorkFormFlagOrder(2, true);
			wKey.setLocationNoOrder(3, true);
			wKey.setStatusFlagOrder(4, true);
		    wKey.setResultUseByDateOrder(5, true);
			wKey.setUseByDateOrder(6, true);
			wKey.setOrderNoOrder(7, true);

			//#CM724451
			// Obtain the data count of the corresponding Work Status Info data. 
			
			if(!canLowerDisplay(wObj.count(wKey)))
			{
				return returnNoDisplayParameter();
			}
						
			//#CM724452
			// Obtain the Work Status Info. 
			wWorkinfo = (WorkingInformation[]) wObj.find(wKey);

			//#CM724453
			// Obtain the Consignor Name with the latest Added Date/Time. 
			wNameKey.setRegistDateOrder(1, false);
			wWorkinfoName = (WorkingInformation[]) wObj.find(wNameKey);
			if (wWorkinfoName != null && wWorkinfoName.length > 0)
			{
				wConsignorName = wWorkinfoName[0].getConsignorName();
			}

			//#CM724454
			// Generate a Vector instance 
			Vector vec = new Vector();

			//#CM724455
			// Set the search result for the return parameter. 
			for (int i = 0; i < wWorkinfo.length; i++)
			{
				RetrievalSupportParameter wTemp = new RetrievalSupportParameter();
				//#CM724456
				// Consignor Code
				wTemp.setConsignorCode(wWorkinfo[i].getConsignorCode());
				//#CM724457
				// Consignor Name
				wTemp.setConsignorName(wConsignorName);
				//#CM724458
				// Planned Picking Date
				wTemp.setRetrievalPlanDate(wWorkinfo[i].getPlanDate());
				//#CM724459
				// Item Code
				wTemp.setItemCode(wWorkinfo[i].getItemCode());
				//#CM724460
				// Item Name
				wTemp.setItemName(wWorkinfo[i].getItemName1());
				//#CM724461
				// Packed Qty per Case
				wTemp.setEnteringQty(wWorkinfo[i].getEnteringQty());
				//#CM724462
				// Packed qty per bundle
				wTemp.setBundleEnteringQty(wWorkinfo[i].getBundleEnteringQty());
				
				//#CM724463
				// Planned Case Qty 
				wTemp.setPlanCaseQty(DisplayUtil.getCaseQty(wWorkinfo[i].getPlanEnableQty(), wWorkinfo[i].getEnteringQty(),wWorkinfo[i].getWorkFormFlag()));
				//#CM724464
				// Planned Piece Qty 
				wTemp.setPlanPieceQty(DisplayUtil.getPieceQty(wWorkinfo[i].getPlanEnableQty(), wWorkinfo[i].getEnteringQty(),wWorkinfo[i].getWorkFormFlag()));
				//#CM724465
				// Result Case Qty 
				wTemp.setResultCaseQty(DisplayUtil.getCaseQty(wWorkinfo[i].getResultQty(), wWorkinfo[i].getEnteringQty(),wWorkinfo[i].getWorkFormFlag()));
				//#CM724466
				// Result Piece Qty 
				wTemp.setResultPieceQty(DisplayUtil.getPieceQty(wWorkinfo[i].getResultQty(), wWorkinfo[i].getEnteringQty(),wWorkinfo[i].getWorkFormFlag()));
				
				//#CM724467
				// Picking Location 
				wTemp.setRetrievalLocation(wWorkinfo[i].getLocationNo());
				//#CM724468
				// Status flag
				if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART))
				{
					//#CM724469
					// Standby 
					wTemp.setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
				}
				else if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START))
				{
					//#CM724470
					// "Started" 
					wTemp.setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_STARTED);
				}
				else if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
				{
					//#CM724471
					// Working 
					wTemp.setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_WORKING);
				}
				else if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
				{
					//#CM724472
					// Completed 
					wTemp.setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
				}
				//#CM724473
				// Expiry Date
				if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
				{
					wTemp.setUseByDate(wWorkinfo[i].getResultUseByDate());
				}
				else
				{
					wTemp.setUseByDate(wWorkinfo[i].getUseByDate());
				}
				//#CM724474
				// Case/Piece division
				if (wWorkinfo[i].getWorkFormFlag().equals(WorkingInformation.CASEPIECE_FLAG_CASE))
				{
					//#CM724475
					// Case 
					wTemp.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
					wTemp.setCasePieceflgName(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_CASE));
				}
				else if (wWorkinfo[i].getWorkFormFlag().equals(WorkingInformation.CASEPIECE_FLAG_PIECE))
				{
					//#CM724476
					// Piece 
					wTemp.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
					wTemp.setCasePieceflgName(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_PIECE));
				}
				else if (wWorkinfo[i].getWorkFormFlag().equals(WorkingInformation.CASEPIECE_FLAG_NOTHING))
				{
					//#CM724477
					// None 
					wTemp.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
					wTemp.setCasePieceflgName(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_NOTHING));
				}
			    //#CM724478
			    // Result Report flag 
			    if (wWorkinfo[i].getReportFlag().equals(WorkingInformation.REPORT_FLAG_NOT_SENT))
			    {
				    //#CM724479
				    // Not Reported 
				    wTemp.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);
				    wTemp.setReportFlagName(DisplayUtil.getReportFlagValue(WorkingInformation.REPORT_FLAG_NOT_SENT));
			    }
			    else
			    {
				    //#CM724480
				    // Reported 
				    wTemp.setReportFlag(WorkingInformation.REPORT_FLAG_SENT);
				    wTemp.setReportFlagName(DisplayUtil.getReportFlagValue(WorkingInformation.REPORT_FLAG_SENT));
			    }
				if(!wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
				{
					//#CM724481
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
					//#CM724482
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
				
				//#CM724483
				// Order No.
				wTemp.setOrderNo(wWorkinfo[i].getOrderNo());
				//#CM724484
				// Customer Code
				wTemp.setCustomerCode(wWorkinfo[i].getCustomerCode());
				//#CM724485
				// Customer Name
				wTemp.setCustomerName(wWorkinfo[i].getCustomerName1());
				//#CM724486
				// Worker Code
				wTemp.setWorkerCode(wWorkinfo[i].getWorkerCode());
				//#CM724487
				// Worker Name
				wTemp.setWorkerName(wWorkinfo[i].getWorkerName());
				//#CM724488
				// Work No.
				wTemp.setJobNo(wWorkinfo[i].getJobNo());
				//#CM724489
				// Last update date/time
				wTemp.setLastUpdateDate(wWorkinfo[i].getLastUpdateDate());
				vec.addElement(wTemp);
			}

			wSParam = new RetrievalSupportParameter[vec.size()];
			vec.copyInto(wSParam);

			//#CM724490
			// 6001013 = Data is shown. 
			wMessage = "6001013";
			
			return wSParam;
		
	}
	
	//#CM724491
	/**
	 * Receive the contents entered via screen as a parameter, and start the schedule for maintenance of the Order Picking Work. <BR>
	 * Assume that two or more data may be input, including setting via preset area. So, require the parameter to receive them as an array. <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * Search for the data preservation information under Working. If the corresponding data when such data is found, delete its record.<BR>
	 * If changed the Working data status, search through the RFT Work Status Info and update the electronic statement field item of the working terminal to null. <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents <BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> instance throws <CODE>ScheduleException</CODE>. <BR>
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record is found, return the array with number of elements equal to 0. <BR>
	 *          If the number of search results exceeds the Max count of data allowable to display, or if error occurs with the input condition, return null. <BR>
	 *          Returning array with the number of elements 0 or null enables to obtain the error content as a message using the <CODE>getMessage</CODE> method. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{

		try
		{
			RetrievalSupportParameter[] wParam = (RetrievalSupportParameter[]) startParams;
			
			//#CM724492
			// Check the Worker code and password 
			if (!checkWorker(conn, wParam[0]))
			{
				return null;
			}

			//#CM724493
			// Check the Daily Update Processing. 
			if (isDailyUpdate(conn))
			{
				return null;
			}
			
			//#CM724494
			// Check for exclusion of all the target data. 
			if (!lockAll(conn, startParams))
			{
				//#CM724495
				// 6003006=Unable to process this data. It has been updated via other work station. 
				wMessage = "6003006";
				return null;
			}

			//#CM724496
			// Check for input and exclusion of the preset area. 
			if (!checkList(conn, wParam))
			{
				return null;
			}
			
			//#CM724497
			// Obtain the Worker Name. 
			String workername = getWorkerName(conn, wParam[0].getWorkerCode());
			//#CM724498
			// Work Date (System defined date) 
			String wSysDate = getWorkDate(conn);

            //#CM724499
            // Work Quantity 
			int wWorkQty = 0;
			
			ArrayList terminalList = new ArrayList();
			
			for (int i = 0; i < wParam.length; i++)
			{
				//#CM724500
				// Set Worker name. 
				wParam[i].setWorkerName(workername);
				
				//#CM724501
				// Clicking on the Cancel All Working button: 
				if (!StringUtil.isBlank(wParam[0].getButtonType()) &&
				    wParam[0].getButtonType().equals(RetrievalSupportParameter.BUTTON_ALLWORKINGCLEAR))
				{
					//#CM724502
					// Change the status flag of the parameter to Standby. 
					wParam[i].setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
				}

				//#CM724503
				// Update the Work Status Info. (Obtain the Work Status Info before updated.) 
				WorkingInformation wWorkinfo = (WorkingInformation)updateWorkinfo(conn, wParam[i], wParam[0]);

				if (wWorkinfo != null)
				{
					//#CM724504
					// Generate the instance of the class for the process to complete Picking. 
					RetrievalCompleteOperator wCompObj = new RetrievalCompleteOperator();
					
					//#CM724505
					// Update the Picking Plan Info. 
					updateRetrievalPlan(conn, wParam[i], wWorkinfo);
					
					//#CM724506
					// Changing the status from Standby to Completed or Started to Completed , or Working to Completed, 
					//#CM724507
					// updates the Inventory Info (DMSTOCK) and adds the Result data (HOSTSEND) from the Complete process class. 
					if (wParam[i].getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION) &&
					    (wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART) ||
					     wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START) ||
					     wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING)))
					{
						//#CM724508
						// Process for completing  
						wCompObj.complete(conn, wParam[i].getJobNo(), wProcessName);
						
						//#CM724509
						// Check for overflow of the Work Quantity 
						int inputQty = wParam[i].getEnteringQty() * wParam[i].getResultCaseQty() + wParam[i].getResultPieceQty();
						wWorkQty = addWorkQty(wWorkQty,inputQty);
						
					}

					//#CM724510
					// To update from Completed to Completed, generate a result to offset the resided result. 
					if (wParam[i].getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION) &&
					    wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
					{
						//#CM724511
						// To update the Worker result update, add up the work qty that has done this time. 
						//#CM724512
						// Use the formula (This time result qty) - (Result qty in the Work Status Info before change. If the obtained value is negative, adopt its modulus to sum it up. 
						wWorkQty += addWorkQty(wWorkQty,Math.abs((wParam[i].getEnteringQty() * wParam[i].getResultCaseQty() + 
						                      wParam[i].getResultPieceQty()) - wWorkinfo.getResultQty()));
									 
						//#CM724513
						// Generate a result to offset the resided result. 
						negateHostSendData(conn, wWorkinfo, wParam[0], wSysDate);
						
						//#CM724514
						// Generate the result. (Updating from Completed to Completed modifies the result only. Disable to change stock) 
						createHostSendData(conn, wWorkinfo, wParam[i], wSysDate);
					}
					
					String rftNo = wWorkinfo.getTerminalNo();
					//#CM724515
					// If the work status before change is Working and terminal No. is not blank, 
					if (wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING)
							&& ! StringUtil.isBlank(rftNo))
					{
						//#CM724516
						// To delete the Working data file, 
						//#CM724517
						// generate the RFT Machine No. List. 
						if (!terminalList.contains(rftNo))
						{
							terminalList.add(rftNo);
						}
					}
				}
			}
			
			if (wWorkQty > 0)
			{

				//#CM724518
				// Add the Worker Result data inquiry table (DNWORKERRESULT). 
				updateWorkerResult(conn, wParam[0].getWorkerCode(), wParam[0].getWorkerName(),
					               wSysDate, wParam[0].getTerminalNumber(), WorkingInformation.JOB_TYPE_RETRIEVAL, wWorkQty);
			}

			//#CM724519
			// Initialize the PackageManager required in the process to delete a Working data file. 
			PackageManager.initialize(conn);
			//#CM724520
			// Delete the Working data file. 
			IdMessage.deleteWorkingDataFile(terminalList,
					WorkingInformation.JOB_TYPE_RETRIEVAL,
					workDetails,
					wProcessName,
					conn);

			//#CM724521
			// Search through the Picking Plan Info again. 
			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[])this.query(conn, wParam[0]);

			//#CM724522
			// 6001018=Updated. 
			wMessage = "6001018";

			//#CM724523
			// Return the latest Picking Plan Info 
			return viewParam;
		}			
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM724524
	// Package methods -----------------------------------------------

	//#CM724525
	// Protected methods ---------------------------------------------

	//#CM724526
	/**
	 * Receive the contents entered via screen as a parameter and check for input and exclusion in the preset area. 
	 * @param conn Instance to maintain database connection. 
	 * @param checkParam RetrievalSupportParameter class instance with contents that were input via screen. 
	 * @return Return true when no input error occurs, or return false when input error occurs. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	protected boolean checkList(Connection conn, Parameter[] checkParam) throws ReadWriteException, ScheduleException
	{

		//#CM724527
		// Generate instance of Work Status Info handlers. 
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformation wWorkinfo = null;

		try
		{
			//#CM724528
			// Contents of Preset area 
			RetrievalSupportParameter[] wParam = (RetrievalSupportParameter[]) checkParam;
			//#CM724529
			// entered Picking qty 
			long wResultQty = 0;
			//#CM724530
			// Planned Picking Qty 
			long wPlanQty = 0;

			for (int i = 0; i < wParam.length; i++)
			{
				//#CM724531
				// Set the search conditions. 
				wKey.KeyClear();
				//#CM724532
				// Work No.
				wKey.setJobNo(wParam[i].getJobNo());
				//#CM724533
				// Obtain the search result of the Work Status Info. 
				wWorkinfo = (WorkingInformation) wObj.findPrimaryForUpdate(wKey);

				if (wWorkinfo != null)
				{
					//#CM724534
					// Check Exclusion. 
					if (!wParam[i].getLastUpdateDate().equals(wWorkinfo.getLastUpdateDate()))
					{
						//#CM724535
						// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
						wMessage = "6023209" + wDelim + wParam[i].getRowNo();
						return false;
					}
					
					//#CM724536
					// Data of Work Status Info with Completed and nothing in Completed Location was completed with shortage upon its allocation.
					//#CM724537
					// Therefore, disable its maintenance. 
					if (wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
					{
						if (StringUtil.isBlank(wWorkinfo.getResultLocationNo()))
						{
							//#CM724538
							// 6023499=No.{0} Maintenance can't be done since the picking work is completed with shortage. 
							wMessage = "6023499" + wDelim + wParam[i].getRowNo();
							return false;
						}
					}
					//#CM724539
					// Disable any maintenance other than Complete for ASRS work. 
					if (wWorkinfo.getSystemDiscKey() == WorkingInformation.SYSTEM_DISC_KEY_ASRS
					 && !wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
					{
						//#CM724540
						// 6023273=No.{0} {1}
						//#CM724541
						// 6023413=If you work with AS/RS, work maintenance is unavailable. Please execute maintenance via AS/RS Work Maintenance screen. 
						wMessage = "6023273" + wDelim + wParam[i].getRowNo() + wDelim
								+ MessageResource.getMessage("6023413");
						return false;
					}
					
					//#CM724542
					// Disable maintenance for the reported data. 
					if (wWorkinfo.getReportFlag().equals(WorkingInformation.REPORT_FLAG_SENT))
					{
						if ((!wParam[ i ].getStatusFlagL().equals( RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION)) 
						|| !wParam[ i ].getStatusFlagL().equals( RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
						{
							//#CM724543
							// 6023364=No. {0} Cannot change. The data was already reported. 
							wMessage = "6023364" + wDelim + wParam[ i ].getRowNo();
							return false;
						}
						else
						{
							//#CM724544
							// Compute the Picking qty. 
							wResultQty = ( long )wParam[ i ].getResultCaseQty() * ( long )wParam[ i ].getEnteringQty() + ( long )wParam[ i ].getResultPieceQty();
							if (wResultQty != wWorkinfo.getResultQty())
							{
								//#CM724545
								// 6023364=No. {0} Cannot change. The data was already reported. 
								wMessage = "6023364" + wDelim + wParam[ i ].getRowNo();
								return false;
							}
						}
					}
					//#CM724546
					// For update the status "Completed" to other than "Completed": 
					if (wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION)
						&& !wParam[i].getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
					{
						//#CM724547
						// 6023492=No {0} Status can't be changed since picking is already completed. 
						wMessage = "6023492" + wDelim + wParam[i].getRowNo();
						return false;
					}

					//#CM724548
					// When Packed Qty per Case is 0, Check for input of Picking Case Number 
					if (wParam[i].getEnteringQty() == 0 && wParam[i].getResultCaseQty() > 0)
					{
						//#CM724549
						// 6023299=No.{0} If Packed Qty. per Case is 0, you cannot enter Picking Case Qty. 
						wMessage = "6023299" + wDelim + wParam[i].getRowNo();
						return false;
					}

					//#CM724550
					// Compute the Picking qty. 
					wResultQty = (long)wParam[i].getResultCaseQty() * (long)wParam[i].getEnteringQty() + (long)wParam[i].getResultPieceQty();
					//#CM724551
					// Calculate the planned qty. 
					wPlanQty = (long)wWorkinfo.getPlanEnableQty();

					if (wResultQty > 0 && wParam[0].getButtonType().equals(RetrievalSupportParameter.BUTTON_MODIFYSUBMIT))
					{
						//#CM724552
						// For data with Status flag = Completed  AND Picking qty > Planned Picking Qty: 
						if (wParam[i].getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION) && wResultQty > wPlanQty)
						{
							//#CM724553
							// 6023155=Completed quantity exceeded the expected quantity. Please check and reenter. 
							wMessage = "6023155" + wDelim + wParam[i].getRowNo();
							return false;
						}

						//#CM724554
						// Status flag = Standby  AND Picking qty > 0 
						if (wParam[i].getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED) && wResultQty > 0)
						{
							//#CM724555
							// 6023208=No.{0} Picking quantity cannot be entered when status is standby. 
							wMessage = "6023208" + wDelim + wParam[i].getRowNo();
							return false;
						}

						//#CM724556
						// Status flag = Started  AND Picking qty > 0 
						if (wParam[i].getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_STARTED) && wResultQty > 0)
						{
							//#CM724557
							// 6023318=No.{0} If status is Started, you cannot enter Picking Qty. 
							wMessage = "6023318" + wDelim + wParam[i].getRowNo();
							return false;
						}

						//#CM724558
						// Status flag = Working  AND Picking qty > 0 
						if (wParam[i].getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_WORKING) && wResultQty > 0)
						{
							//#CM724559
							// 6023319=No.{0} If status is Working, you cannot enter Picking Qty. 
							wMessage = "6023319" + wDelim + wParam[i].getRowNo();
							return false;
						}

						//#CM724560
						// Check for overflow. 
						if (wResultQty > WmsParam.MAX_TOTAL_QTY)
						{
							//#CM724561
							// 6023272=No.{0} Please enter {2} or smaller for {1}. 
							wMessage =
								"6023272" + wDelim + wParam[i].getRowNo() + wDelim + DisplayText.getText("LBL-W0198") + wDelim + MAX_TOTAL_QTY_DISP;
							return false;
						}

						//#CM724562
						// For update the status Completed to Completed: ensure that (Picking qty) < = (workable Qty) - (Pending Qty) 
						if (wParam[i].getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION)
							&& wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION)
							&& wResultQty > wWorkinfo.getPlanEnableQty() - wWorkinfo.getPendingQty())
						{
							//#CM724563
							// 6023320=No.{0} Pending work remains. Please enter the value lower than {1} in Total Picking Piece Qty. 
							wMessage = "6023320" + wDelim + wParam[i].getRowNo() + wDelim + (wWorkinfo.getPlanEnableQty() - wWorkinfo.getPendingQty());
							return false;
						}
						
					}
				}
				else
				{
					//#CM724564
					// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
					wMessage = "6023209" + wDelim + wParam[i].getRowNo();
					return false;
				}
			}
		}		
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		
		return true;
	}

	//#CM724565
	/**
	 * Generate info that offsets the existing Result Sent Information, when the maintenance completed. 
	 * @param conn        Instance to maintain database connection. 
	 * @param oldWorkInfo Work Status Info before change 
	 * @param inputParam  RetrievalSupportParameter class instance with contents that were input via screen. 
	 * @param workDate    Work date
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected void negateHostSendData(Connection conn, WorkingInformation oldWorkInfo, RetrievalSupportParameter inputParam, String workDate)
		throws ReadWriteException, ScheduleException
	{
		
		//#CM724566
		// Generate instance for the Result Sent Information handlers. 
		HostSendHandler wObj = new HostSendHandler(conn);
		HostSend wHostSend = null;
		
		try
		{
			//#CM724567
			// Set the value to be modified. 
			//#CM724568
			// Result qty
			oldWorkInfo.setResultQty(-oldWorkInfo.getResultQty());
			//#CM724569
			// Shortage Qty
			oldWorkInfo.setShortageCnt(-oldWorkInfo.getShortageCnt());
			//#CM724570
			// Terminal No.
			oldWorkInfo.setTerminalNo(inputParam.getTerminalNumber());
			//#CM724571
			// Worker Code
			oldWorkInfo.setWorkerCode(inputParam.getWorkerCode());
			//#CM724572
			// Worker Name
			oldWorkInfo.setWorkerName(inputParam.getWorkerName());
			//#CM724573
			// Last update process name
			oldWorkInfo.setLastUpdatePname(wProcessName);
			//#CM724574
			// Report flag: Not Sent 
			oldWorkInfo.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);
			
			//#CM724575
			// Generate entity of the Result Sent Information from Entity of Work Status Info. 
			wHostSend = new HostSend(oldWorkInfo, workDate,wProcessName);
			
			//#CM724576
			// Add Result Sent Information (change/modify result) 
			wObj.create(wHostSend);
		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM724577
	/**
	 * Generate a result of updating from Completed to Completed. 
	 * @param conn        Instance to maintain database connection. 
	 * @param oldWorkInfo Work Status Info before change 
	 * @param inputParam  RetrievalSupportParameter class instance with contents that were input via screen. 
	 * @param workDate    Work date
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected void createHostSendData(Connection conn, WorkingInformation oldWorkInfo, RetrievalSupportParameter inputParam, String workDate)
		throws ReadWriteException, ScheduleException
	{
		
		//#CM724578
		// Generate instance for the Result Sent Information handlers. 
		HostSendHandler wObj = new HostSendHandler(conn);
		HostSend wHostSend = null;
		
		try
		{
			//#CM724579
			// Compute the Picking Result qty. 
			int wResultQty = inputParam.getEnteringQty() * inputParam.getResultCaseQty() + inputParam.getResultPieceQty();
			//#CM724580
			// Compute the Shortage Qty. 
			int wShrtQty = oldWorkInfo.getPlanEnableQty() - wResultQty - oldWorkInfo.getPendingQty();
			
			//#CM724581
			// Set the value to be modified. 
			//#CM724582
			// Result qty
			oldWorkInfo.setResultQty(wResultQty);
			//#CM724583
			// Shortage Qty
			oldWorkInfo.setShortageCnt(wShrtQty);
			//#CM724584
			// Expiry Date
			oldWorkInfo.setResultUseByDate(inputParam.getUseByDate());
			//#CM724585
			// Terminal No.
			oldWorkInfo.setTerminalNo(inputParam.getTerminalNumber());
			//#CM724586
			// Worker Code
			oldWorkInfo.setWorkerCode(inputParam.getWorkerCode());
			//#CM724587
			// Worker Name
			oldWorkInfo.setWorkerName(inputParam.getWorkerName());
			//#CM724588
			// Report flag: Not Sent 
			oldWorkInfo.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);
			//#CM724589
			// Last update process name
			oldWorkInfo.setLastUpdatePname(wProcessName);
			
			//#CM724590
			// Generate entity of the Result Sent Information from Entity of Work Status Info. 
			wHostSend = new HostSend(oldWorkInfo, workDate, wProcessName);
			
			//#CM724591
			// Add Result Sent Information (change/modify result) 
			wObj.create(wHostSend);
		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	//#CM724592
	/**
	 * Execute the process for updating the Work Status Info. 
	 * @param conn Instance to maintain database connection. 
	 * @param param      RetrievalSupportParameter class instance with contents that were input via screen. 
	 * @param inputParam RetrievalSupportParameter class instance with contents that were input via screen. 
	 *                   (Maintain the leading value of the array) 
	 * @return Instance of WorkingInformation class with contents of Work Status Info before update. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected WorkingInformation updateWorkinfo(Connection conn, RetrievalSupportParameter param, RetrievalSupportParameter inputParam)
		throws ReadWriteException, ScheduleException
	{

		//#CM724593
		// Generate instance of Work Status Info handlers. 
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformationAlterKey wAKey = new WorkingInformationAlterKey();
		WorkingInformationSearchKey wSKey = new WorkingInformationSearchKey();
		WorkingInformation wWorkinfo = null;
		//#CM724594
		// Work Result qty
		int wResultQty = 0;

		try
		{
			//#CM724595
			// Set the search conditions. 
			//#CM724596
			// Work No.
			wSKey.setJobNo(param.getJobNo());
			//#CM724597
			// Obtain the search result of the Work Status Info. 
			wWorkinfo = (WorkingInformation) wObj.findPrimary(wSKey);

			if (wWorkinfo != null)
			{
				//#CM724598
				// Set the update condition. 
				//#CM724599
				// Work No.
				wAKey.setJobNo(param.getJobNo());

				//#CM724600
				// Set the update value. 
				if (!wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
				{
					//#CM724601
					// Update it to "Started". 
					if (param.getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
					{
						wAKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_START);
						wAKey.updateWorkerCode("");
						wAKey.updateWorkerName("");
						wAKey.updateTerminalNo("");
					}
					//#CM724602
					// Change to Standby. 
					else if (param.getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
					{
						wAKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
						wAKey.updateWorkerCode("");
						wAKey.updateWorkerName("");
						wAKey.updateTerminalNo("");
					}
					//#CM724603
					// Change the status to Working.
					else if (param.getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
					{
						wAKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
					}
				}
				//#CM724604
				// Last update process name
				wAKey.updateLastUpdatePname(wProcessName);

				//#CM724605
				// For data with status flag "Completed": 
				if (param.getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
				{
					//#CM724606
					// Calculate the work result qty. 
					wResultQty = param.getEnteringQty() * param.getResultCaseQty() + param.getResultPieceQty();
					//#CM724607
					// Work Result Qty (Sum up the Work Result qty of parameter) 
					wAKey.updateResultQty(wResultQty);
					//#CM724608
					// Shortage Qty (subtract both Work Result qty and Pending Qty together from the workable Qty) 
					wAKey.updateShortageCnt(wWorkinfo.getPlanEnableQty() - wResultQty - wWorkinfo.getPendingQty());
					//#CM724609
					// Expiry Date
					wAKey.updateResultUseByDate(param.getUseByDate());
					//#CM724610
					// Picking Location 
					wAKey.updateResultLocationNo(wWorkinfo.getLocationNo());
					//#CM724611
					// Status flag
					wAKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
					//#CM724612
					// Terminal No.
					wAKey.updateTerminalNo(inputParam.getTerminalNumber());
					//#CM724613
					// Worker Code
					wAKey.updateWorkerCode(inputParam.getWorkerCode());
					//#CM724614
					// Worker Name
					wAKey.updateWorkerName(inputParam.getWorkerName());
				}

				//#CM724615
				// Update Work Status Info.
				wObj.modify(wAKey);

				return wWorkinfo;
			}
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		
		return null;
	}

	//#CM724616
	/**
	 * Execute the process for updating the Picking Plan Info. 
	 * @param conn Instance to maintain database connection. 
	 * @param param RetrievalSupportParameter class instance with contents that were input via screen. 
	 * @param oldWorkInfo Work Status Info before update 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected void updateRetrievalPlan(Connection conn, RetrievalSupportParameter param, WorkingInformation oldWorkInfo)
		throws ReadWriteException, ScheduleException
	{

		//#CM724617
		// Generate instance of handlers for Picking Plan information. 
		RetrievalPlanHandler wRObj = new RetrievalPlanHandler(conn);
		RetrievalPlanAlterKey wRAKey = new RetrievalPlanAlterKey();
		RetrievalPlanSearchKey wRSKey = new RetrievalPlanSearchKey();
		RetrievalPlan wRetrieval = null;
		//#CM724618
		// Generate instance of Work Status Info handlers. 
		WorkingInformationHandler wWObj = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey wWSKey = new WorkingInformationSearchKey();
		WorkingInformation[] wWorkinfo = null;
		//#CM724619
		// Status flag to be updated
		String wStatusFlag = "";
		
		try
		{
			//#CM724620
			// Set the search conditions. 
			//#CM724621
			// Picking Plan unique key
			wRSKey.setRetrievalPlanUkey(oldWorkInfo.getPlanUkey());
			//#CM724622
			// Obtain the search result of the locked Picking Plan Info. 
			wRetrieval = (RetrievalPlan) wRObj.findPrimaryForUpdate(wRSKey);

			if (wRetrieval != null)
			{
				//#CM724623
				// Set the search conditions. 
				//#CM724624
				// Plan unique key 
				wWSKey.setPlanUkey(oldWorkInfo.getPlanUkey());
				//#CM724625
				// Obtain the search result of the Work Status Info. 
				wWorkinfo = (WorkingInformation[]) wWObj.find(wWSKey);
				
				//#CM724626
				// Set the update condition. 
				//#CM724627
				// Picking Plan unique key
				wRAKey.setRetrievalPlanUkey(oldWorkInfo.getPlanUkey());

				//#CM724628
				// Set the update value. 
				//#CM724629
				// Last update process name
				wRAKey.updateLastUpdatePname(wProcessName);


				//#CM724630
				// Update the Status flag. 

				//#CM724631
				// Change to "Standby" 
				if (param.getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
				{
					//#CM724632
					// Standby 
					wStatusFlag = RetrievalPlan.STATUS_FLAG_UNSTART;
					boolean end = false;
					if (wWorkinfo != null)
					{
						//#CM724633
						// If one or more data with status Working exists, regard the status as Working. 
						for (int i = 0; i < wWorkinfo.length; i++)
						{
							if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
							{
								wStatusFlag = RetrievalPlan.STATUS_FLAG_NOWWORKING;
								end = true;
								break;
							}
						}
						if (!end)
						{
							//#CM724634
							// For data with no "Working" status and only with "Started" status, regard it as "Starte
							for (int i = 0; i < wWorkinfo.length; i++)
							{
								if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START))
								{
									wStatusFlag = RetrievalPlan.STATUS_FLAG_START;
									end = true;
									break;
								}
							}
						}
						if (!end)
						{
							//#CM724635
							// If there is no data with Working or Partially Completed but one more data with status Completed exists, regard the status as Partially Completed. 
							for (int i = 0; i < wWorkinfo.length; i++)
							{
								if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
								{
									wStatusFlag = RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART;
									end = true;
									break;
								}
							}
						}
						//#CM724636
						// Update the Status flag. 
						wRAKey.updateStatusFlag(wStatusFlag);
					}
				}
				//#CM724637
				// Change to "Started". 
				else if (param.getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
				{
					//#CM724638
					// "Started" 
					wStatusFlag = RetrievalPlan.STATUS_FLAG_START;
					boolean end = false;
					if (wWorkinfo != null)
					{
						//#CM724639
						// If one or more data with status Working exists, regard the status as Working. 
						for (int i = 0; i < wWorkinfo.length; i++)
						{
							if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
							{
								wStatusFlag = RetrievalPlan.STATUS_FLAG_NOWWORKING;
								end = true;
								break;
							}
						}
						if (!end)
						{
							//#CM724640
							// For data with no "Working" status and only with "Started" status, regard it as "Starte
							for (int i = 0; i < wWorkinfo.length; i++)
							{
								if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START))
								{
									wStatusFlag = RetrievalPlan.STATUS_FLAG_START;
									end = true;
									break;
								}
							}
						}
						if (!end)
						{
							//#CM724641
							// If there is no data with Working or Partially Completed but one more data with status Completed exists, regard the status as Partially Completed. 
							for (int i = 0; i < wWorkinfo.length; i++)
							{
								if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
								{
									wStatusFlag = RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART;
									end = true;
									break;
								}
							}
						}
						//#CM724642
						// Update the Status flag. 
						wRAKey.updateStatusFlag(wStatusFlag);
					}
				}
				//#CM724643
				// Change to "Working" 
				else if (param.getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
				{
					//#CM724644
					// Update the Status flag. 
					wRAKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING);
				}
				//#CM724645
				// Change to "Completed" 
				else if (param.getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
				{
					//#CM724646
					// Total Shortage Qty 
					int wTotalShrt = 0;
				
					if (wWorkinfo != null)
					{
						int resultQty = 0;
						//#CM724647
						// Compute the Shortage Qty. 
						for (int i = 0; i < wWorkinfo.length; i++)
						{
							wTotalShrt += wWorkinfo[i].getShortageCnt();
							resultQty += (wWorkinfo[i].getShortageCnt() + wWorkinfo[i].getResultQty());
						}
						//#CM724648
						//Update the Status flag. 
						//#CM724649
						// Completed 
						wStatusFlag = RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART;
						boolean end = false;
						//#CM724650
						// If one or more data with status Working exists, regard the status as Working. 
						for (int i = 0; i < wWorkinfo.length; i++)
						{
							if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
							{
								wStatusFlag = RetrievalPlan.STATUS_FLAG_NOWWORKING;
								end = true;
								break;
							}
						}
						if (!end)
						{
							//#CM724651
							// For data with no Working status and only with Started status, regard it as Started. 
							for (int i = 0; i < wWorkinfo.length; i++)
							{
								if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START))
								{
									wStatusFlag = RetrievalPlan.STATUS_FLAG_START;
									end = true;
									break;
								}
							}
						}
					}

					//#CM724652
					// Calculate the parameter result qty. 
					int wParamResultQty = param.getEnteringQty() * param.getResultCaseQty() + param.getResultPieceQty();
					//#CM724653
					// Calculate the result qty (Subtract the difference between the source result qty and the result qty of the parameter from the source result qty). 
					int wResultQty = wRetrieval.getResultQty() - (oldWorkInfo.getResultQty() - wParamResultQty);
					//#CM724654
					// Result qty
					wRAKey.updateResultQty(wResultQty);
					//#CM724655
					// Total shortage qty in the linked Work Status Info 
					wRAKey.updateShortageCnt(wTotalShrt);
					//#CM724656
					// For data with all values of Quantity "Completed", update it "All Completed". 
					if ((wResultQty + wTotalShrt) == wRetrieval.getPlanQty())
					{
						wStatusFlag = RetrievalPlan.STATUS_FLAG_COMPLETION;
					}
					//#CM724657
					// Update the Status flag. 
					wRAKey.updateStatusFlag(wStatusFlag);
				}

				//#CM724658
				// Update the Picking Plan Info.
				wRObj.modify(wRAKey);
			}
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		
	}
	
	//#CM724659
	// Private methods -----------------------------------------------

}
//#CM724660
//end of class
