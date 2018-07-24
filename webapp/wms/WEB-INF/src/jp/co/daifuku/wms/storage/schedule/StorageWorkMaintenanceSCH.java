package jp.co.daifuku.wms.storage.schedule;

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
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkerHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.Worker;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdMessage;
import jp.co.daifuku.wms.master.operator.AreaOperator;

/**
 * Designer : M.Inoue <BR>
 * Maker : M.Inoue  <BR>
 * <BR>
 * This class executes WEB storage work maintenance. <BR>
 * Receive the contents entered via screen as a parameter and execute the process for storage work maintenance. <BR>
 * Each method in this class receives a connection object and executes the process for updating the database. <BR>
 * However, each method disables to commit and roll back of transactions. <BR>
 * This class executes the following processes. <BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method) <BR><BR><DIR>
 *   If only one Consignor code exist in the Work status ( storage), return the corresponding Consignor code. <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist. <BR>
 * <BR>
 *   <Search conditions> <BR><DIR>
 *     Status flag other than Deleted<BR>
 *     Work division "Storage" </DIR></DIR>
 * <BR>
 * 2.Process by clicking on Display button (<CODE>Query()</CODE> Method)<BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it.<BR>
 *   Return <CODE>Parameter</CODE> array with the number of elements 0 if no corresponding data found. Or, return null when condition error occurs.<BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 *   Display Item code, storage location, status, expiry date, and Case/Piece division in this order. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR><DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Consignor code* <BR>
 *     Work Status* <BR>
 *     Planned storage date* <BR>
 *     Item Code <BR>
 *     Case/Piece division*<BR></DIR>
 * <BR>
 *   <Returned data> <BR><DIR>
 *     Consignor code <BR>
 *     Consignor name <BR>
 *     Planned storage date <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Packed qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Planned Work Case Qty <BR>
 *     Planned Work Piece Qty <BR>
 *     Result Case Qty <BR>
 *     Result Piece Qty <BR>
 *     Storage Location(result_location)<BR>
 *     Status flag <BR>
 *     Expiry Date (result_use_by_date)<BR>
 *     Case/Piece division<BR>
 *     Case/Piece division name<BR>
 *     Result report(Not Reported/Reported)<BR>
 *     Worker Code <BR>
 *     Worker Name <BR>
 *     Work No. <BR>
 *     Last update date/time <BR></DIR></DIR>
 * <BR>
 * 3.Process by clicking Modify, Submit/Add, or Cancel All Processing button (<CODE>startSCHgetParams()</CODE> method) <BR><BR><DIR>
 *   Receive the contents displayed in the preset area as a parameter and execute the process for storage work maintenance. <BR>
 *   Clicking 0 for the button type of parameter executes Modify or Submit/Add process. Clicking 1 executes Cancel All Processing process. <BR>
 *   If changed the processing data status, search for the RFT Work status and update the electronic statement item (count) of the working terminal to null.<BR>
 *   Re-obtain the data to be output to the preset area from database when the process normally completed, and return it. <BR>
 *   Return null when the schedule failed to complete due to condition error or other causes. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 *   Update the Work status, Plan info, and then inventory information in this sequence.<BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Work No.* <BR>
 *     Consignor code <BR>
 *     Status flag* <BR>
 *     Planned storage date* <BR>
 *     Item Code <BR>
 *     Packed qty per Case <BR>
 *     Storage Case Qty <BR>
 *     Storage Piece Qty <BR>
 *     Storage Location<BR>
 *     Status<BR>
 *     Expiry Date <BR>
 *     Last update date/time* <BR>
 *     Button type* <BR>
 *     Terminal No.*<BR>
 *     Case/Piece division*<BR>
 *     Preset Line No*<BR></DIR>
 * <BR>
 *   <Returned data> <BR><DIR>
 *     Consignor code <BR>
 *     Consignor name <BR>
 *     Planned storage date <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Packed qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Planned Work Case Qty <BR>
 *     Planned Work Piece Qty <BR>
 *     Result Case Qty <BR>
 *     Result Piece Qty <BR>
 *     Storage Location<BR>
 *     Status flag <BR>
 *     Expiry Date <BR>
 *     Case/Piece division<BR>
 *     Case/Piece division name<BR>
 *     Result report(Not Reported/Reported)<BR>
 *     Worker Code <BR>
 *     Worker Name <BR>
 *     Work No. <BR>
 *     Last update date/time <BR></DIR></DIR>
 * <BR>
 *   <Modify/Add process> <BR>
 * <DIR>
 *   <Check for process condition> <BR>
 *     1.Ensure to define Worker code and password in the Worker master. <BR><DIR>
 *       Check only the leading value of the array for the values of Worker code and password. <BR></DIR>
 *     2.Ensure that the Work status table work No. exist in the database. <BR>
 *     3.Require to correspond the last update date/time  of the parameter to the last update date/time of the work status table. (exclusion check) <BR>
 *     4.Ensure that AS/RS work is in status other than Completed.<BR>
 *     5.Ensure that the designated location does not exist in the AS/RS location info. <BR>
 * <BR>
 *   <Update Process> <BR>
 *     -Update of Storage Plan information table (DNSTORAGEPLAN) <BR>
 *       1.To update the status flag Standby to Processing: <BR><DIR>
 *         update the status flag to Processing only for the status flag Standby in the storage Plan info. <BR></DIR>
 *       2.To update the status flag Processing to Standby: <BR><DIR>
 *         Only for data with "Processing" status flag of the storage Plan info, update the status flag to Standby. <BR></DIR>
 *       3.To update status flag Processing to Completed, or Standby to Completed: <BR><DIR>
 *         Update the status flag.<BR><DIR>
 *           -If all the linked work status is Completed: Completed<BR>
 *           -If there is one or more data with status Work in process in the linked work status: Work in process<BR>
 *           -Regard the data with both status Standby and Completed in its linked work status as Partially Completed.<BR>
 *           </DIR> <BR>
 *         Assign the storage result qty of the parameter to the storage result qty of the storage Plan info. <BR>
 *         Update the storage shortage qty to the value obtained by decreasing the storage result qty of the parameter from the storage the possible work qty of the Work status. <BR></DIR>
 *       4.To update status flag Completed to Completed: <BR><DIR>
 *         Update the storage result qty to the value of storage result qty of the parameter. <BR>
 *         Add the this time shortage qty to the shortage qty of the Plan info.<BR></DIR>
 *       5.Update the last Update process name. <BR>
 * <BR>
 *     -Update Work Status Table (DNWORKINFO) <BR>
 *       1.To update the status flag Standby to Processing, or Processing to Standby: <BR><DIR>
 *         Update the status flag to the value of status flag of the parameter. <BR></DIR>
 *       2.To update status flag Processing to Completed, or Standby to Completed: <BR><DIR>
 *         Sum up the storage result qty of the parameter to the work result qty of the Work status. <BR>
 *         Update the work shortage qty to the value obtained by decreasing storage result qty of the parameter from the Planned Work qty. <BR>
 *         If the expiry date is set for the parameter, update the expiry date with the contents (result_use_by_date). <BR>
 *         Setting the storage location for the parameter allows to update the location (result_location_no) with the contents. <BR>
 *         Note) Update the inventory information (DMSTOCK) or add the Result data inquiry (HOSTSEND) in the process for completing a storage. <BR></DIR>
 *       3.To update status flag Completed to Completed: <BR><DIR>
 *         Assign the storage result qty of the parameter to the work result qty of the work status. <BR>
 *         Update the work shortage qty to the value obtained by decreasing storage result qty of the parameter from the Planned Work qty. <BR>
 *         If expiry date and storage location are set for the parameter, update the expiry date (result_use_by_date) and storage location (result_location_no) with the contents <BR>
 *         Note) The update range of result qty = the possible work qty - (minus) pending qty <BR></DIR>
 *       4.Update the last Update process name. <BR>
 * <BR>
 *     -Update Inventory Information Table (DNSTOCK) (Update in the Complete Storage process) Note) Disable to modify the inventory information when changing from "Completed" to "Completed ". <BR>
 *      For details, enable to refer to <code>StorageCompleteOperator</code>.
 * <BR>
 *     -Add Sending Result Info Table (DNHOSTSEND) <BR>
 *       Updating from the status flag from "Completed" to "Completed" generates a sending Result Info to offset the resided result, and then generate the result added on the screen.
 *       Updating from "Standby" or "Processing" to "Completed" generates a result with status Processing to complete the Storage. For details, enable to refer to <code>StorageCompleteOperator</code>.<BR>
 * <BR>
 *     -Update/Add Worker Result Data Inquiry Table (DNWORKERRESULT). <BR>
 *       Updating to "Completed" updates and adds the Worker Result data inquiry based on the contents in the parameter. <BR></DIR>
 * <BR>
 *   <Cancel Processing All> <BR>
 * <DIR>
 *   <Check for process condition> <BR>
 *     1.Ensure to define Worker code and password in the Worker master. <BR><DIR>
 *       Check only the leading value of the array for the values of Worker code and password. <BR></DIR>
 *     2.Ensure that the Work status table work No. exist in the database. <BR>
 *     3.Ensure that the status flag of the corresponding Work status table is Processing. <BR>
 *     4.Require to correspond the last update date/time to the last update date/time of the work status table. (exclusion check) <BR>
 * <BR>
 *   <Update Process> Update the work status and then the plan information in this order.<BR>
 *     -Update Work Status Table (DNWORKINFO) <BR>
 *       Update the Work status based on the contents of the Received parameter. <BR><DIR>
 *         Update the status flag to Standby. <BR>
 *         Update the last Update process name. <BR></DIR>
 *     -Update of Storage Plan information table (DNSTORAGEPLAN) <BR>
 *       Only for data with "Processing" status flag of the storage Plan info, update the storage Plan info based on the contents of the Received parameter. <BR><DIR>
 *         Update as below.
 *         -If all the linked work status is Completed: Completed
 *         -If there is one or more data with status Work in process in the linked work status: Work in process
 *         -Regard the data with both status Standby and Completed in its linked work status as Partially Completed.</DIR>
 * <BR>
 * </DIR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>M.Inoue</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/18 06:54:27 $
 * @author  $Author: suresh $
 */
public class StorageWorkMaintenanceSCH extends AbstractStorageSCH
{

	// Class variables -----------------------------------------------
	/**
	 * Process name
	 */
	protected static final String wProcessName = "StorageWorkMaintenance";

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/18 06:54:27 $");
	}
	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public StorageWorkMaintenanceSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * If only one Consignor Code exists in Work status, return the corresponding Consignor Code.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Instance to maintain database connection.
	 * @param searchParam Instance of <CODE>StorageSupportParameter</CODE> class with conditions to obtain the display data.<BR>
	 *         Designating any instance other than <CODE>StorageSupportParameter</CODE> throws ScheduleException.<BR>
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		// Set the corresponding Consignor code.
		StorageSupportParameter wParam = new StorageSupportParameter();

		// Generate work status instance of handlers.
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformation[] wWorkinfo = null;

		// Set the search conditions.
		// Status flag!="Deleted"
		// Work division storage
		wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		wKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		// Set the Consignor Code for grouping condition.
		wKey.setConsignorCodeGroup(1);
		wKey.setConsignorCodeCollect("DISTINCT");
		if (wObj.count(wKey) == 1)
		{
			// Obtain the count of data with the corresponding consignor code.
			wWorkinfo = (WorkingInformation[])wObj.find(wKey);

			// If the count is one,
			if (wWorkinfo != null && wWorkinfo.length == 1)
			{
				// Obtain the corresponding Consignor Code and set it for the return parameter.
				wParam.setConsignorCode(wWorkinfo[0].getConsignorCode());
			}
		}
		return wParam;
	}

	/**
	 * Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param conn Instance to maintain database connection.
	 * @param searchParam Instance of <CODE>StorageSupportParameter</CODE> class with conditions to obtain the display data.<BR>
	 *         Designating any instance other than <CODE>StorageSupportParameter</CODE> throws ScheduleException.<BR>
	 * @return Array of <CODE>StorageSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record found, return the array of the number of elements equal to 0.<BR>
	 *          Return null if the search result count exceeds 1000 or when input condition error occurs.<BR>
	 *          Returning array with element qty 0 (zero) or null allows to obtain the error contents as a message using <CODE>getMessage()</CODE> method.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		// search result
		StorageSupportParameter[] wSParam = null;
		// Consignor name
		String wConsignorName = "";

		// Generate work status instance of handlers.
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformation[] wWorkinfo = null;
		WorkingInformation[] wWorkinfoName = null;

		// Search conditions for parameters
		StorageSupportParameter wParam = (StorageSupportParameter)searchParam;

		// Check for Worker code and password.
		if (!checkWorker(conn, wParam))
		{
			return null;
		}
		// Check Mandatory Input.
		if (StringUtil.isBlank(wParam.getWorkerCode())
		||	StringUtil.isBlank(wParam.getPassword())
		||	StringUtil.isBlank(wParam.getConsignorCode())
		||	StringUtil.isBlank(wParam.getStoragePlanDate()))
		{
			throw (new ScheduleException("mandatory error!"));
		}

		// Set the search conditions.
		// Consignor code
		wKey.setConsignorCode(wParam.getConsignorCode());
		// Work Status All
		if (wParam.getWorkStatus().equals(StorageSupportParameter.STATUS_FLAG_ALL))
		{
			// Display the Standby, Started, Processing, or Completed data if Work status of the parameter is "All".
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "<=", "(", "", "OR");
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION, "=", "", ")", "AND");
		}
		// completed
		else if(wParam.getWorkStatus().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
		{
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
		}
		// Standby
		else if(wParam.getWorkStatus().equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED))
		{
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		}
		// Start
		else if(wParam.getWorkStatus().equals(StorageSupportParameter.STATUS_FLAG_STARTED))
		{
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_START);
		}
		// Processing
		else if(wParam.getWorkStatus().equals(StorageSupportParameter.STATUS_FLAG_WORKING))
		{
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		}

		// Storage Plan Date
		wKey.setPlanDate(wParam.getStoragePlanDate());

		// Item code
		if (!StringUtil.isBlank(wParam.getItemCode()))
		{
			wKey.setItemCode(wParam.getItemCode());
		}
		// Work Type : Storage
		wKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		// Case Piece division
		if (wParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
		{
			// Case
			wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
		}
		else if (wParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
		{
			//Piece
			wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
		}
		else if (wParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			//None
			wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
		}

		// In the order of Item code, Storage location, Status, Expiry date, and Case/Piece division.
		wKey.setItemCodeOrder(1, true);
		if (wParam.getWorkStatus().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
		{
			wKey.setResultLocationNoOrder(2, true);
		}
		else
		{
			wKey.setLocationNoOrder(2, true);
		}
		wKey.setStatusFlagOrder(3, true);
		// If expiry date control is enabled, add expiry date to the condition.
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			wKey.setResultUseByDateOrder(4, true);
			wKey.setWorkFormFlagOrder(5, true);
		}
		else
		{
			wKey.setWorkFormFlagOrder(4, true);
		}

		// Obtain the data count of the corresponding Work status.
		if (!canLowerDisplay(wObj.count(wKey)))
		{
			return returnNoDisplayParameter();
		}

		// Obtain the Shipping Plan info.
		wWorkinfo = (WorkingInformation[])wObj.find(wKey);

		// Obtain the Consignor name with the latest added date.
		wKey.setRegistDateOrder(1, false);
		wWorkinfoName = (WorkingInformation[])wObj.find(wKey);
		if (wWorkinfoName != null && wWorkinfoName.length > 0)
		{
			wConsignorName = wWorkinfoName[0].getConsignorName();
		}

		// Generate Generation of Vector instance
		Vector vec = new Vector();

		// Set the search result for the return parameter.
		for (int i = 0; i < wWorkinfo.length; i++)
		{
			StorageSupportParameter wTemp = new StorageSupportParameter();
			// Consignor code
			wTemp.setConsignorCode(wWorkinfo[i].getConsignorCode());
			// Consignor name
			wTemp.setConsignorName(wConsignorName);
			// Storage Plan Date
			wTemp.setStoragePlanDate(wWorkinfo[i].getPlanDate());
			// Item Code
			wTemp.setItemCode(wWorkinfo[i].getItemCode());
			// Item Name
			wTemp.setItemName(wWorkinfo[i].getItemName1());
			// Packed qty per Case
			wTemp.setEnteringQty(wWorkinfo[i].getEnteringQty());
			// Packed qty per bundle
			wTemp.setBundleEnteringQty(wWorkinfo[i].getBundleEnteringQty());
			// Work Planned Qty
			wTemp.setTotalPlanQty(wWorkinfo[i].getPlanEnableQty());
			// Work Result qty
			wTemp.setTotalResultQty(wWorkinfo[i].getResultQty());
			// Expiry Date
			if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
			{
				wTemp.setUseByDate(wWorkinfo[i].getResultUseByDate());
			}
			else
			{
				wTemp.setUseByDate("");
			}
			// Set the quotient obtained by dividing the possible work qty by case for Planned work case qty
			wTemp.setPlanCaseQty(DisplayUtil.getCaseQty(wWorkinfo[i].getPlanEnableQty(), wWorkinfo[i].getEnteringQty(),
					wWorkinfo[i].getWorkFormFlag()));
			// Set the remainder obtained by dividing the possible work qty by case for Planned work case qty
			wTemp.setPlanPieceQty(DisplayUtil.getPieceQty(wWorkinfo[i].getPlanEnableQty(), wWorkinfo[i].getEnteringQty(),
					wWorkinfo[i].getWorkFormFlag()));
			// Result Case Qty
			wTemp.setResultCaseQty(DisplayUtil.getCaseQty(wTemp.getTotalResultQty(), wWorkinfo[i].getEnteringQty(),
					wWorkinfo[i].getWorkFormFlag()));
			// Result Piece Qty
			wTemp.setResultPieceQty(DisplayUtil.getPieceQty(wTemp.getTotalResultQty(), wWorkinfo[i].getEnteringQty(),
					wWorkinfo[i].getWorkFormFlag()));
			// Storage Location
			if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
			{
				wTemp.setStorageLocation(wWorkinfo[i].getResultLocationNo());
			}
			else
			{
				wTemp.setStorageLocation(wWorkinfo[i].getLocationNo());
			}
			// Status flag
			if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART))
			{
				// Standby
				wTemp.setStatusFlagL(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
			}
			else if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START))
			{
				// Started
				wTemp.setStatusFlagL(StorageSupportParameter.STATUS_FLAG_STARTED);
			}
			else if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
			{
				// Processing
				wTemp.setStatusFlagL(StorageSupportParameter.STATUS_FLAG_WORKING);
			}
			else if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
			{
				// completed
				wTemp.setStatusFlagL(StorageSupportParameter.STATUS_FLAG_COMPLETION);
			}

			// Case/Piece division
			if (wWorkinfo[i].getWorkFormFlag().equals(WorkingInformation.CASEPIECE_FLAG_CASE))
			{
				// Case
				wTemp.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_CASE);
				wTemp.setCasePieceflgName(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_CASE));
			}
			else if (wWorkinfo[i].getWorkFormFlag().equals(WorkingInformation.CASEPIECE_FLAG_PIECE))
			{
				// Piece
				wTemp.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_PIECE);
				wTemp.setCasePieceflgName(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_PIECE));
			}
			else if (wWorkinfo[i].getWorkFormFlag().equals(WorkingInformation.CASEPIECE_FLAG_NOTHING))
			{
				// None
				wTemp.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
				wTemp.setCasePieceflgName(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_NOTHING));
			}

			// result report flag
			if (wWorkinfo[i].getReportFlag().equals(WorkingInformation.REPORT_FLAG_NOT_SENT))
			{
				// Not Reported
				wTemp.setReportFlag(StorageSupportParameter.REPORT_FLAG_NOT_SENT);
				wTemp.setReportFlagName(DisplayUtil.getReportFlagValue(WorkingInformation.REPORT_FLAG_NOT_SENT));
			}
			else
			{
				// Reported
				wTemp.setReportFlag(StorageSupportParameter.REPORT_FLAG_SENT);
				wTemp.setReportFlagName(DisplayUtil.getReportFlagValue(WorkingInformation.REPORT_FLAG_SENT));
			}
			// Work place
			// Display value with "-" of the status flag other than Completed.
			if(!wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
			{
				wTemp.setSystemDiscKeyName(DisplayText.getText("LBL-W0078"));

			}
			else
			{
				if (wWorkinfo[i].getSystemDiscKey() == (StorageSupportParameter.SYSTEM_DISC_KEY_FLOOR))
				{
					wTemp.setSystemDiscKey(StorageSupportParameter.SYSTEM_DISC_KEY_FLOOR);
					wTemp.setSystemDiscKeyName(DisplayUtil.getSystemDiscKeyValue(StorageSupportParameter.SYSTEM_DISC_KEY_FLOOR));
				}
				else if(wWorkinfo[i].getSystemDiscKey() == (StorageSupportParameter.SYSTEM_DISC_KEY_ASRS))
				{
					wTemp.setSystemDiscKey(StorageSupportParameter.SYSTEM_DISC_KEY_ASRS);
					wTemp.setSystemDiscKeyName(DisplayUtil.getSystemDiscKeyValue(StorageSupportParameter.SYSTEM_DISC_KEY_ASRS));
				}
				else if(wWorkinfo[i].getSystemDiscKey() == (StorageSupportParameter.SYSTEM_DISC_KEY_MOVEMENT_RACK))
				{
					wTemp.setSystemDiscKey(StorageSupportParameter.SYSTEM_DISC_KEY_MOVEMENT_RACK);
					wTemp.setSystemDiscKeyName(DisplayUtil.getSystemDiscKeyValue(StorageSupportParameter.SYSTEM_DISC_KEY_MOVEMENT_RACK));
				}
			}

			// Worker Code
			wTemp.setWorkerCode(wWorkinfo[i].getWorkerCode());
			// Worker Name
			wTemp.setWorkerName(wWorkinfo[i].getWorkerName());
			// Work No.
			wTemp.setJobNo(wWorkinfo[i].getJobNo());
			// Last update date/time
			wTemp.setLastUpdateDate(wWorkinfo[i].getLastUpdateDate());
			vec.addElement(wTemp);
		}

		wSParam = new StorageSupportParameter[vec.size()];
		vec.copyInto(wSParam);

		// 6001013 = Data is shown.
		wMessage = "6001013";

		return wSParam;
	}

	/**
	 * Receive the contents entered via screen as a parameter and start the schedule for storage work maintenance.<BR>
	 * Assume that two or more data may be input via preset area or others. So, require the parameter to receive them in the form of array.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * Return true if the schedule normally completed, or return false if failed.<BR>
	 * If changed the processing data status, search for the RFT Work status and update the electronic statement item (count) of the working terminal to null.<BR>
	 * @param conn Instance to maintain database connection.
	 * @param startParams Array of <CODE>StorageSupportParameter</CODE> class instance with contents of commitment. <BR>
	 *         Designating any instance other than StorageSupportParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *         Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{

		try
		{
			StorageSupportParameter[] wParam = (StorageSupportParameter[])startParams;
			// Check for Worker code and password.
			if (!checkWorker(conn, wParam[0]))
			{
				return null;
			}
			// Check the Daily Update Processing.
			if (isDailyUpdate(conn))
			{
				return null;
			}
			// Check for exclusion of all the target data.
			if (!lockAll(conn, wParam))
			{
				// 6003006 = Unable to process this data. It has been updated via other work station.
				wMessage = "6003006";
				return null;
			}
			// Check for exclusion of the target Center Inventory.
			lockOccupiedStock(conn, startParams);
			// Check for input and exclusion of the preset area.
			if (!checkList(conn, wParam))
			{
				return null;
			}

			WorkerHandler wObj = new WorkerHandler(conn);
			WorkerSearchKey wKey = new WorkerSearchKey();
			wKey.setWorkerCode(wParam[0].getWorkerCode());
			wKey.setDeleteFlag(Worker.DELETE_FLAG_OPERATION);
			Worker[] wWorker = (Worker[])wObj.find(wKey);
			String workerName = wWorker[0].getName();

			// Obtain Work Date
			WareNaviSystemHandler warenaviHandler = new WareNaviSystemHandler(conn);
			WareNaviSystemSearchKey wsearchKey = new WareNaviSystemSearchKey();
			WareNaviSystem wms = (WareNaviSystem)warenaviHandler.findPrimary(wsearchKey);
			if (wms == null)
			{
				// 6027006=Data mismatch occurred. Please refer to log. TABLE={0}
				RmiMsgLogClient.write("6006040" + wDelim + "WareNaviSystem", "StorageWorkMaintenance");
				// Throw ScheduleException here.(Do not need to set error message.)
				throw (new ScheduleException());
			}
			// Work Date ( System defined date)
			String sysdate = getWorkDate(conn);

			int workqty = 0;

			// Terminal list of data of which status "Processing" was cancelled.
			ArrayList terminalList = new ArrayList();

			for (int i = 0; i < wParam.length; i++)
			{
				// Set Worker name.
				wParam[i].setWorkerName(workerName);

				// Clicking on the Cancel All Processing button
				if (!StringUtil.isBlank(wParam[0].getButtonType())
					&& wParam[0].getButtonType().equals(StorageSupportParameter.BUTTON_ALLWORKINGCLEAR))
				{
					// Change the status flag of the parameter to Standby.
					wParam[i].setStatusFlagL(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
				}

				// Update the Work status. (Obtain the Work status before updated.)
				WorkingInformation wWorkinfo = (WorkingInformation)updateWorkinfo(conn, wParam[i], wParam[0]);

				if (wWorkinfo != null)
				{
					// Generate instance for Complete Storage process class.
					StorageCompleteOperator wCompObj = new StorageCompleteOperator();
					// Update the Storage Plan info.
					updateStoragePlan(conn, wParam[i], wWorkinfo);
					// To update from "Standby" to "Completed" or "Started" to "Completed ", or "Processing" to "Completed ",  from the Complete process class
					// Update the inventory information (DMSTOCK) and add the Result data inquiry (HOSTSEND).
					if (wParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION)
						&& (wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART)
							|| wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START)
							|| wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING)))
					{

						//Process for completing
						wCompObj.complete(conn, wParam[i].getJobNo(), wProcessName);
						// To update the Worker result, add up the work qty that has done this time.
						workqty = addWorkQty(workqty, wParam[i].getEnteringQty() * wParam[i].getResultCaseQty() + wParam[i].getResultPieceQty());
					}

					// To update from "Completed" to "Completed", generate a result to offset the resided result.
					if (wParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION)
						&& wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
					{
						// To update the Worker result, add up the work qty that has done this time.
						// Calculate by using the expression "This time result - Result qty of work status before changed". Even if the obtained value is negative, translate it into absolute value for adding up.
						workqty
							= addWorkQty(workqty, Math.abs(
								(wParam[i].getEnteringQty() * wParam[i].getResultCaseQty() + wParam[i].getResultPieceQty())
									- wWorkinfo.getResultQty()));

						// Generate a result to offset the resided result.
						negateHostSendData(conn, wWorkinfo, wParam[i], sysdate);
						// Generate the result. ( Updating from Completed to Completed modifies the result only. Disable to change stock)
						createHostSendData(conn, wWorkinfo, wParam[i], sysdate);
					}

					String rftNo = wWorkinfo.getTerminalNo();
					// If the work status before change is Work in process and terminal No. is not blank,
					if (wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING)
							&& ! StringUtil.isBlank(rftNo))
					{
						// in order to delete the Processing data file,
						// generate the RFT Machine No. List.
						if (! terminalList.contains(rftNo))
						{
							terminalList.add(rftNo);
						}
					}
				}
			}
			if (workqty > 0)
			{

				// Add the Worker Result data inquiry table (DNWORKERRESULT).
				updateWorkerResult(
					conn,
					wParam[0].getWorkerCode(),
					workerName,
					sysdate,
					wParam[0].getTerminalNumber(),
					WorkingInformation.JOB_TYPE_STORAGE,
					workqty);
			}

			// Initialize the PackageManager required in the process to delete a processing data file.
			PackageManager.initialize(conn);
			// Delete the Processing data file.
			IdMessage.deleteWorkingDataFile(terminalList,
					WorkingInformation.JOB_TYPE_STORAGE,
					"",
					wProcessName,
					conn);

			// Search for the Storage Plan information again.
			StorageSupportParameter[] viewParam = (StorageSupportParameter[])this.query(conn, wParam[0]);

			// 6001018 = Updated.
			wMessage = "6001018";

			// Return the last Storage Plan info.
			return viewParam;
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (OverflowException e)
		{
			// 6023348 = Cannot enter.  Stock Qty. exceeds {0}.
			wMessage = "6023348" + wDelim + MAX_STOCK_QTY_DISP;
			return null;
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

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	/**
	 * Receive the contents entered via screen as a parameter and check for input and exclusion in the preset area.
	 * @param conn Instance to maintain database connection.
	 * @param param StorageSupportParameter class instance with contents that were input via screen.
	 * @return Return true when no input error occurs, or return false when input error occurs.
	 * @throws ReadWriteException If abnormal error occurs during database access
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 */
	protected boolean checkList(Connection conn, Parameter[] checkParam) throws ReadWriteException, ScheduleException
	{

		// Generate work status instance of handlers.
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformation wWorkinfo = null;

		try
		{
			// Contents of Preset area
			StorageSupportParameter[] wParam = (StorageSupportParameter[])checkParam;
			// Entered storage qty
			long wResultQty = 0;
			// Storage Planned Qty
			long wPlanQty = 0;
			// Location No
			String wLocationNo = "";

			for (int i = 0; i < wParam.length; i++)
			{
				// Set the search conditions.
				wKey.KeyClear();
				// Work No.
				wKey.setJobNo(wParam[i].getJobNo());
				// Obtain the search result of the Work status.
				wWorkinfo = (WorkingInformation)wObj.findPrimaryForUpdate(wKey);

				if (wWorkinfo != null)
				{
					// Disable maintenance for the reported data.
					if (wWorkinfo.getReportFlag().equals(WorkingInformation.REPORT_FLAG_SENT))
					{
						if ((!wParam[ i ].getStatusFlagL().equals( StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
						|| !wParam[ i ].getStatusFlagL().equals( StorageSupportParameter.STATUS_FLAG_COMPLETION))
						{
							// 6023364=No. {0} Cannot change. The data was already reported.
							wMessage = "6023364" + wDelim + wParam[i].getRowNo();
							return false;
						}
						else
						{
							// Calculate the Storage qty.
							wResultQty = ( long )wParam[ i ].getResultCaseQty() * ( long )wParam[ i ].getEnteringQty() + ( long )wParam[ i ].getResultPieceQty();
							if (wResultQty != wWorkinfo.getResultQty())
							{
								// 6023364=No. {0} Cannot change. The data was already reported.
								wMessage = "6023364" + wDelim + wParam[i].getRowNo();
								return false;
							}
						}
					}

					// Disable any maintenance other than Complete for ASRS work.
					if (wWorkinfo.getSystemDiscKey() == WorkingInformation.SYSTEM_DISC_KEY_ASRS
					 && !wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
					{
						// 6023273=No.{0} {1}
						// 6023413=If you work with AS/RS, work maintenance is unavailable. Execute maintenance via AS/RS work maintenance screen.
						wMessage = "6023273" + wDelim + wParam[i].getRowNo() + wDelim
								+ MessageResource.getMessage("6023413");
						return false;
					}

					// Updating from "Completed" to "status other than Completed"
					if (wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION)
						&& !wParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
					{
						// 6023293 = No.{0} Storage was already completed. Cannot change the status.
						wMessage = "6023293" + wDelim + wParam[i].getRowNo();
						return false;
					}

					// Check for input in the Picking Case qty when the Packed qty per Case is 0.
					if (wParam[i].getEnteringQty() == 0 && wParam[i].getResultCaseQty() > 0)
					{
						// 6023294=No.{0} If Packed Qty. per Case is 0
						wMessage = "6023294" + wDelim + wParam[i].getRowNo();
						return false;
					}

					// Obtain the location.
					wLocationNo = wParam[i].getStorageLocation();

					if (!StringUtil.isBlank(wLocationNo))
					{
						LocateOperator locateOperator = new LocateOperator(conn);
						if (locateOperator.isAsrsLocation(wLocationNo))
						{
							// 6023443=No.{0} The specified location is in automatic warehouse. Cannot enter.
							wMessage = "6023443" + wDelim + wParam[i].getRowNo();
							return false;
						}
					}

					// Calculate the Storage qty.
					wResultQty = (long)wParam[i].getResultCaseQty() * (long)wParam[i].getEnteringQty() + (long)wParam[i].getResultPieceQty();
					// Calculates the planned qty.
					wPlanQty = (long)wWorkinfo.getPlanEnableQty();

					if (wResultQty > 0 && wParam[0].getButtonType().equals(StorageSupportParameter.BUTTON_MODIFYSUBMIT))
					{
						// If Status flag = "Completed" and Storage qty > Planned storage qty
						if (wParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION) && wResultQty > wPlanQty)
						{
							// 6023155 = Completed quantity exceeded the expected quantity. Please check and reenter.
							wMessage = "6023155" + wDelim + wParam[i].getRowNo();
							return false;
						}

						// Status flag= "Standby " and Storage qty>0
						if (wParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED) && wResultQty > 0)
						{
							// 6023295=No.{0} If status is Standby
							wMessage = "6023295" + wDelim + wParam[i].getRowNo();
							return false;
						}

						// Status flag="Started" and Storage qty>0
						if (wParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_STARTED) && wResultQty > 0)
						{
							// 6023296=No.{0} If status is Started
							wMessage = "6023296" + wDelim + wParam[i].getRowNo();
							return false;
						}

						// Status flag="Processing" and Storage qty>0
						if (wParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_WORKING) && wResultQty > 0)
						{
							// 6023297=No.{0} If status is Working
							wMessage = "6023297" + wDelim + wParam[i].getRowNo();
							return false;
						}

						// Check Overflow.
						if (wResultQty > WmsParam.MAX_STOCK_QTY)
						{
							// 6023272 = No.{0} Please enter {2} or smaller for {1}.
							wMessage =
								"6023272" + wDelim + wParam[i].getRowNo() + wDelim + DisplayText.getText("LBL-W0198") + wDelim + MAX_STOCK_QTY_DISP;
							return false;
						}

						// For updating Completed to Completed, check that Storage Qty < = Possible work qty - Pending qty.
						if (wParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION)
							&& wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION)
							&& wResultQty > wWorkinfo.getPlanEnableQty() - wWorkinfo.getPendingQty())
						{
							// 6023298=No.{0} Pending work remains. Please enter the value lower than {1} in Total Storage Piece Qty.
							wMessage =
								"6023298" + wDelim + wParam[i].getRowNo() + wDelim + (wWorkinfo.getPlanEnableQty() - wWorkinfo.getPendingQty());
							return false;
						}
					}
					// Check Exclusion.
					if (!wParam[i].getLastUpdateDate().equals(wWorkinfo.getLastUpdateDate()))
					{
						// 6023209 = No.{0} The data has been updated via other terminal.
						wMessage = "6023209" + wDelim + wParam[i].getRowNo();
						return false;
					}
				}
				else
				{
					// 6023209 = No.{0} The data has been updated via other terminal.
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

	/**
	 * Generate to offset a resided sending Result Info when the maintenance completed.
	 * @param conn       Instance to maintain database connection.
	 * @param oldWorkInfo      Work Status before changed
	 * @param inputParam       StorageSupportParameter class instance with contents that were input via screen.
	 * @param workDate         Work Date
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 */
	protected void negateHostSendData(Connection conn, WorkingInformation oldWorkInfo, StorageSupportParameter inputParam, String workDate)
		throws ReadWriteException, ScheduleException
	{
		// Generate Sending Result Information instance of handlers.
		HostSendHandler wHObj = new HostSendHandler(conn);
		HostSend wHostSend = null;
		try
		{
			oldWorkInfo.setResultQty(-oldWorkInfo.getResultQty());
			oldWorkInfo.setShortageCnt(-oldWorkInfo.getShortageCnt());
			oldWorkInfo.setTerminalNo(inputParam.getTerminalNumber());
			oldWorkInfo.setWorkerCode(inputParam.getWorkerCode());
			oldWorkInfo.setWorkerName(inputParam.getWorkerName());
			oldWorkInfo.setLastUpdatePname(wProcessName);
			// Not Sent
			oldWorkInfo.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);
			// Generate entity of the Sending Result Info from Entity of Work status.
			wHostSend = new HostSend(oldWorkInfo, workDate, wProcessName);
			// Add Sending Result Info (change/modify result)
			wHObj.create(wHostSend);
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

	/**
	 * Generate a result of updating from Completed to Completed.
	 * @param conn       Instance to maintain database connection.
	 * @param oldWorkInfo      Work Status before changed
	 * @param inputParam       StorageSupportParameter class instance with contents that were input via screen.
	 * @param workDate         Work Date
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 */
	protected void createHostSendData(Connection conn, WorkingInformation oldWorkInfo, StorageSupportParameter inputParam, String workDate)
		throws ReadWriteException, ScheduleException
	{
		// Generate Sending Result Information instance of handlers.
		HostSendHandler wHObj = new HostSendHandler(conn);
		HostSend wHostSend = null;
		try
		{
			// Storage Result qty
			int resultQty = inputParam.getEnteringQty() * inputParam.getResultCaseQty() + inputParam.getResultPieceQty();
			// Shortage Qty
			int shrtQty = oldWorkInfo.getPlanEnableQty() - resultQty - oldWorkInfo.getPendingQty();
			oldWorkInfo.setResultQty(resultQty);
			oldWorkInfo.setShortageCnt(shrtQty);
			oldWorkInfo.setResultUseByDate(inputParam.getUseByDate());
			oldWorkInfo.setResultLocationNo(inputParam.getStorageLocation());
			oldWorkInfo.setTerminalNo(inputParam.getTerminalNumber());
			oldWorkInfo.setWorkerCode(inputParam.getWorkerCode());
			oldWorkInfo.setWorkerName(inputParam.getWorkerName());
			// Not Sent
			oldWorkInfo.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);
			oldWorkInfo.setLastUpdatePname(wProcessName);
			// Generate entity of the Sending Result Info from Entity of Work status.
			wHostSend = new HostSend(oldWorkInfo, workDate, wProcessName);
			// Add sending Result Info (change/modify result)
			wHObj.create(wHostSend);
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
	/**
	 * Execute the update process for updating the Work status.
	 * @param conn Instance to maintain database connection.
	 * @param param      StorageSupportParameter class instance with contents that were input via screen.
	 * @param inputParam StorageSupportParameter class instance with contents that were input via screen.
	 *                   (Maintain the leading value of the array has been maintained)
	 * @return Instance of WorkingInformation class with contents of Work Status before modified.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 */
	protected WorkingInformation updateWorkinfo(Connection conn, StorageSupportParameter param, StorageSupportParameter inputParam)
		throws ReadWriteException, ScheduleException
	{

		// Generate Work Status Instance of handlers.
		WorkingInformationHandler wiHandle = new WorkingInformationHandler(conn);
		WorkingInformationAlterKey wiAltKey = new WorkingInformationAlterKey();
		WorkingInformationSearchKey wiSrchKey = new WorkingInformationSearchKey();
		WorkingInformation wWorkinfo = null;
		// Work Result qty
		int wResultQty = 0;

		try
		{
			// Set the search conditions.
			// Work No.
			wiSrchKey.setJobNo(param.getJobNo());
			// Obtain the search result of the Work status.
			wWorkinfo = (WorkingInformation)wiHandle.findPrimary(wiSrchKey);

			if (wWorkinfo != null)
			{
				// Lock other work status linked to the Plan info to prevent from dead-locking.
				// from here
				wiSrchKey.KeyClear();
				wiSrchKey.setPlanUkey(wWorkinfo.getPlanUkey());
				wiSrchKey.setJobNo(param.getJobNo(), "!=");
				// till here

				// Set the update condition
				// Work No.
				wiAltKey.setJobNo(param.getJobNo());

				// Set the update value.
				if (!wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
				{
					// Change to "Started"
					if (param.getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_STARTED))
					{
						wiAltKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_START);
						wiAltKey.updateWorkerCode("");
						wiAltKey.updateWorkerName("");
						wiAltKey.updateTerminalNo("");
					}
					// Change to Standby.
					else if (param.getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED))
					{
						wiAltKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
						wiAltKey.updateCollectJobNo(wWorkinfo.getJobNo());
						wiAltKey.updateWorkerCode("");
						wiAltKey.updateWorkerName("");
						wiAltKey.updateTerminalNo("");

					}
					// Change to Processing
					else if (param.getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_WORKING))
					{
						wiAltKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
					}
				}
				// Last update process name
				wiAltKey.updateLastUpdatePname(wProcessName);

				// If status flag is Completed,
				if (param.getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
				{
					// Calculate the work result qty.
					wResultQty = param.getEnteringQty() * param.getResultCaseQty() + param.getResultPieceQty();
					// Work Result qty(Add up the work result qty of the parameter.)
					wiAltKey.updateResultQty(wResultQty);
					// Shortage Qty(Decrease the work result qty and the pending qty from the possible work qty.)
					wiAltKey.updateShortageCnt(wWorkinfo.getPlanEnableQty() - wResultQty - wWorkinfo.getPendingQty());
					// Expiry Date
					wiAltKey.updateResultUseByDate(param.getUseByDate());
					// Storage Location
					wiAltKey.updateResultLocationNo(param.getStorageLocation());
					// Status flag
					wiAltKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
					// Terminal No.
					wiAltKey.updateTerminalNo(inputParam.getTerminalNumber());
					// Worker Code
					wiAltKey.updateWorkerCode(inputParam.getWorkerCode());
					// Worker Name
					wiAltKey.updateWorkerName(inputParam.getWorkerName());
					// Return the aggregation work No.
					wiAltKey.updateCollectJobNo(wWorkinfo.getJobNo());

					// Update the area info.
					AreaOperator areaOpe = new AreaOperator(conn);
					LocateOperator locOpe = new LocateOperator(conn);
					String areaNo = locOpe.getAreaNo(param.getStorageLocation());
					wiAltKey.updateAreaNo(areaNo);
					wiAltKey.updateSystemDiscKey(Integer.parseInt(areaOpe.getAreaType(areaNo)));

				}

				// Update Work Status.
				wiHandle.modify(wiAltKey);

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

	/**
	 * Execute the process for updating the storage Plan info.
	 * @param conn Instance to maintain database connection.
	 * @param param StorageSupportParameter class instance with contents that were input via screen.
	 * @param oldWorkInfo Work Status before modified
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 */
	protected void updateStoragePlan(Connection conn, StorageSupportParameter param, WorkingInformation oldWorkInfo)
		throws ReadWriteException, ScheduleException
	{

		// Generate Planned Storage Information instance of handlers.
		StoragePlanHandler storagePlanHandle = new StoragePlanHandler(conn);
		StoragePlanAlterKey storagePlanAltKey = new StoragePlanAlterKey();
		StoragePlanSearchKey storagePlanSrchKey = new StoragePlanSearchKey();
		StoragePlan storagePlan = null;
		// Generate work status instance of handlers.
		WorkingInformationHandler wiHandle = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey wiSrchKey = new WorkingInformationSearchKey();
		WorkingInformation[] wWorkinfo = null;
		// Storage result qty of parameter
		int wParamResultQty = 0;
		// Storage Result qty
		int wResultQty = 0;
		try
		{
			// Set the search conditions.
			// Storage Plan unique key
			storagePlanSrchKey.setStoragePlanUkey(oldWorkInfo.getPlanUkey());
			// Obtain the search result of the locked storage Plan info.
			storagePlan = (StoragePlan)storagePlanHandle.findPrimaryForUpdate(storagePlanSrchKey);

			if (storagePlan != null)
			{
				// Set the search conditions.
				// Plan unique key
				wiSrchKey.setPlanUkey(oldWorkInfo.getPlanUkey());
				// Obtain the search result of the Work status.
				wWorkinfo = (WorkingInformation[])wiHandle.find(wiSrchKey);
				// Set the update condition.
				// Storage Plan unique key
				storagePlanAltKey.setStoragePlanUkey(oldWorkInfo.getPlanUkey());

				// Set the update value.
				// Last update process name
				storagePlanAltKey.updateLastUpdatePname(wProcessName);

				// Status flag
				// Updating from "Standby" to "Processing "
				if (storagePlan.getStatusFlag().equals(StoragePlan.STATUS_FLAG_UNSTART)
					&& (param.getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_WORKING)))
				{
					storagePlanAltKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
				}

				// Updating from "Processing" to "Standby ",
				if (storagePlan.getStatusFlag().equals(StoragePlan.STATUS_FLAG_NOWWORKING)
					&& (param.getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED)))
				{
					String wStatusFlag = StoragePlan.STATUS_FLAG_UNSTART;
					if (wWorkinfo != null)
					{
						for (int i = 0; i < wWorkinfo.length; i++)
						{
							// Regard the status of the Plan info as Processing if one or more work data is in status Processing.
							if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
							{
								wStatusFlag = StoragePlan.STATUS_FLAG_NOWWORKING;
								break;
							}
							// If one or more data with status Completed work exists, change the status to Partially Completed.
							if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
							{
								wStatusFlag = StoragePlan.STATUS_FLAG_COMPLETE_IN_PART;
							}
						}
					}
					storagePlanAltKey.updateStatusFlag(wStatusFlag);
				}

				// To update from "Standby" or "Processing" to "Completed ", or "Completed" to "Completed",
				if (param.getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
				{
					int totalShrt = 0;
					String wStatusFlag = StoragePlan.STATUS_FLAG_COMPLETION;
					if (wWorkinfo != null)
					{
						for (int i = 0; i < wWorkinfo.length; i++)
						{
							// Regard the status of the Plan info as Processing if one or more work data is in status Processing.
							if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
							{
								wStatusFlag = StoragePlan.STATUS_FLAG_NOWWORKING;
								break;
							}
							// Set Partially Completed for the status flag of the storage Plan info, if two or more work status reside or if any Standby data exists in the work status.
							if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART))
							{
								wStatusFlag = StoragePlan.STATUS_FLAG_COMPLETE_IN_PART;
							}
							totalShrt = totalShrt + wWorkinfo[i].getShortageCnt();
						}
					}

					storagePlanAltKey.updateStatusFlag(wStatusFlag);
					// Calculate the parameter result qty.
					wParamResultQty = param.getEnteringQty() * param.getResultCaseQty() + param.getResultPieceQty();
					// Calculate the result qty (Subtract the difference between the source result qty and the result qty of the parameter from the source result qty).
					wResultQty = storagePlan.getResultQty() - (oldWorkInfo.getResultQty() - wParamResultQty);
					// Result qty
					storagePlanAltKey.updateResultQty(wResultQty);
					// Total shortage qty in the linked work status
					storagePlanAltKey.updateShortageCnt(totalShrt);
				}

				// Update Storage Plan Information.
				storagePlanHandle.modify(storagePlanAltKey);
			}
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	// Private methods -----------------------------------------------

}
//end of class
