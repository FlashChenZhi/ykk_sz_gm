package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
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
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.Worker;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.master.operator.AreaOperator;

/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * <BR>
 * This class allows to execute Normal Storage process.<BR>
 * Receive the contents entered via screen as a parameter and execute the process for Normal Storage.<BR>
 * Each method in this class receives a connection object and executes the process for updating the database.<BR>
 * However, each method disables to commit and roll back of transactions.<BR>
 * This class executes the following processes.<BR>
 * <BR>
 *
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   If only one Consignor Code exists in Work status, return the corresponding Consignor Code.<BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist.<BR>
 *   <BR>
 *   [Search conditions] <BR>
 *   <BR>
 *   Work division "Storage"<BR>
 *   Status flag "Standby"<BR>
 * </DIR>
 * <BR>
 * 2.Process by clicking on Display button (<CODE>Query()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it.<BR>
 *   Return <CODE>Parameter</CODE> array with the number of elements 0 if no corresponding data found. Or, return null when condition error occurs.<BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 *   Table to be searched if the Work Status table(DNWORKINFO)<BR>
 *   Disable to display if the search target count exceeds 1000 (MAX_NUMBER_OF_DISP defined in WMSParam).<BR>
 *   Obtain the Consignor name with the later added date/time needed to display in the list cell header.<BR>
 *   <BR>
 *   [Search conditions] <BR>
 *   <BR>
 *   Status flag "Standby"<BR>
 *   Work division "Storage"<BR>
 *   <BR>
 *   [Parameter] *Mandatory Input<BR>
 *   <BR>
 *   Worker Code* <BR>
 *   Password* <BR>
 *   Consignor code* <BR>
 *   Planned start storage Date <BR>
 *   Planned end storage Date <BR>
 *   Item Code <BR>
 *   Case/Piece division(Work type) <BR>
 *   Check for initial input in the receivable qty.<BR>
 *   <BR>
 *   [Check for process condition] <BR>
 *   <BR>
 *   -Worker Code check process-(<CODE>AbstructStorageSCH()</CODE>Class) <BR>
 *   <BR>
 *   -Check the Planned storage date value for larger or smaller-<BR>
 *   <BR>
 *   -Check Count Displayed- <BR>
 *   <BR>
 *   [Input data]<BR><DIR>
 *   Worker Code<BR>
 *   Password	<BR>
 *   Consignor code <BR>
 *   Planned start storage Date <BR>
 *   Planned end storage Date <BR>
 *   Item Code <BR>
 *   Check for initial input in the receivable qty.<BR>
 *   Storage instruction printing check<BR>
 *   Terminal No.	<BR>
 *   Storage Case Qty<BR>
 *   Storage Piece Qty<BR>
 * 	 Packed qty per bundle<BR>
 *   Packed qty per Case<BR>
 *   Total bulk qty flag<BR>
 *   Storage flag<BR>
 *   Expiry Date<BR>
 *   Case ITF <BR>
 *   Bundle ITF <BR>
 *   Work No.<BR>
 *   Last update date/time<BR>
 *   <BR>
 *   [Returned data] <BR>
 *   <BR>
 *   Consignor code <BR>
 *   Consignor name <BR>
 *   Planned storage date <BR>
 *   Item Code <BR>
 *   Item Name <BR>
 *   Case/Piece division(Work type) name <BR>
 *   Storage Location <BR>
 *   Total Storage Qty <BR>
 *   Packed qty per Case <BR>
 *   Packed qty per bundle <BR>
 *   Storage Case Qty <BR>
 *   Storage Piece Qty <BR>
 *   Pending/additional flag	<BR>
 *   Storage flag	<BR>
 *   Expiry Date <BR>
 *   Case ITF <BR>
 *   Bundle ITF <BR>
 *   Work No. <BR>
 *   Last update date/time <BR>
 * </DIR>
 * <BR>
 * 3.Process by clicking on Add button (<CODE>startSCH()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *   Receive the contents displayed in the preset area as a parameter and execute the process for Normal Storage. <BR>
 *   If the Unplanned Storage Work List print division of the parameter is true when the process completed, start up the class for printing the storage instruction.<BR>
 *   Return true when the process normally completed, or return false when failed to schedule completely due to condition error or other causes. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 *   <BR>
 *   [Parameter] *Mandatory Input  + Optional/ Mandatory Input <BR>
 *   <BR>
 *   Worker Code* <BR>
 *   Password* <BR>
 *   Storage Location <BR>
 *   Expiry Date <BR>
 *   Printing division for Storage Instruction <BR>
 *   Preset line No. <BR>
 *   Work No. <BR>
 *   Last update date/time <BR>
 *   Terminal No. <BR>
 *   <BR>
 *   [Check for process condition] <BR>
 *   <BR>
 *   -Worker Code check process-(<CODE>AbstructStorageSCH()</CODE>Class)<BR>
 *   <BR>
 *   -Check process for executing daily update Process-(<CODE>AbstructStorageSCH()</CODE>Class) <BR>
 *   <BR>
 *   -Check for mandatory input in the storage location.-<BR>
 *   -Shelf table check-(<CODE>isAsrsLocation()</CODE>Method)<BR>
 *   <BR>
 *   -Work Quantity(for Worker result info table Add/Update) Check Overflow.-<BR>
 *   <BR>
 *   [Update/Add Process] <BR>
 *   <BR>
 *   -Check Exclusion.<BR>
 *   Lock all the committed Work status to prevent from deadlocking.<BR>
 *   Search for the data using work No. and Work division (storage) and lock it.<BR>
 *   <BR>
 *   -Update Data.<BR>
 *   <BR>
 *   Update the table in the sequence of Work status, Storage Plan info, and then inventory information to prevent from deadlocking.<BR>
 *   Add or update the Worker Result data inquiry, after completed the storage process for one commitment.<BR>
 *   <BR>
 *   -Update Work Status Table (DNWORKINFO)<BR>
 *   <BR>
 *   1.Update the Status flag of Work status to Completed (Update to Completed, whether completed or shorted).<BR>
 *   2.Update the last Update process name.<BR>
 *   3.Update the Work result qty, work shortage qty, expiry date, and work result location based on the contents of the Received parameter.<BR>
 *     <DIR>
 *     Update the storage qty of the parameter to the work result qty of the Work status.<BR>
 *     For shortage, update the work shortage qty to the value obtained by subtracting the storage qty of the parameter from the possible work qty.<BR>
 *     Update the expiry date of the parameter to the expiry date of the Work status (result_use_by_date).<BR>
 *     Update the storage location of the parameter to the work result location Work status (result_location_no).<BR>
 *   4.Update the Worker code, Worker name, and Terminal No. based on the contents of the Received parameter,<BR>
 *     Do not include the availability of Delete division for Search Worker Name in the search condition.<BR>
 *     </DIR>
 *   <BR>
 *   -Update of Storage Plan information table (DNSTORAGEPLAN) <BR>
 *   <BR>
 *   Search for the data using a status flag (Standby or Partially Completed).
 *   1.Update the storage Plan information status flag.<BR>
 *     <DIR>
 *     Search for the data using the Work status as a plan unique key, and update the status flag of the Storage Plan info.<BR>
 *     If the status of all the obtained data is Completed, update the status to Completed.<BR>
 *     If no data with status Processing, update to Partially Completed.<BR>
 *     </DIR>
 *   2.Update the last Update process name.<BR>
 *   3.Update the storage result qty and the storage shortage qty based on the contents of the Received parameter.<BR>
 *     <DIR>
 *     Sum up the storage qty of the parameter to the storage result qty of the storage Plan info.<BR>
 *     For shortage, update the work shortage qty to the value obtained by subtracting the storage qty of the parameter from the possible storage qty of the work info.<BR>
 *     </DIR>
 *   <BR>
 *   -Completed Storage process-(<CODE>StorageCompleteOperator()</CODE>Class)<BR>
 *   <BR>
 *   Set the work No. and process name.<BR>
 *   Search for Work status using the work No. and update the inventory information.<BR>
 *   Use the process name to add each table.<BR>
 *   <BR>
 *   -Add or update the Worker Result data inquiry table (DNWORKERRESULT).-(<CODE>AbstructStorageSCH()</CODE>Class)<BR>
 *   <BR>
 *   Add or update the Worker Result data inquiry based on the Received contents of the parameter.<BR>
 *   <BR>
 *   [Print Process] <BR>
 *   <BR>
 *   Pass the Work No. to the class for printing storage instruction process.<BR>
 *	 Disables to print with no print data.<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>Y.Kubo</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/16 07:58:29 $
 * @author  $Author: suresh $
 */
public class SetStorageSCH extends AbstractStorageSCH implements WmsScheduler
{
	// Class variables -----------------------------------------------
	/**
	 * Class Name
	 */
	public static String PROCESSNAME = "SetStorageSCH";

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/16 07:58:29 $");
	}

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * If only one Consignor Code exists in Work status, return the corresponding Consignor Code.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Database connection object
	 * @param searchParam Instance of <CODE>StoragePlanParameter</CODE> class with conditions to obtain the display data.<BR>
	 *         Designating any instance other than <CODE>StorageSupportParameter</CODE> throws ScheduleException.<BR>
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		// Search Data
		// Work division(Storage)
		searchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		// Status flag (Standby)
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		StorageSupportParameter dispData = new StorageSupportParameter();

		if (workingHandler.count(searchKey) == 1)
		{
			try
			{
				WorkingInformation working = (WorkingInformation)workingHandler.findPrimary(searchKey);
				dispData.setConsignorCode(working.getConsignorCode());
			}
			catch (NoPrimaryException e)
			{
				return new StorageSupportParameter();
			}
		}

		return dispData;
	}

	/**
	 * Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param conn Instance to maintain database connection.<BR>
	 * @param searchParam Instance of <CODE>StoragePlanParameter</CODE> class with conditions to obtain the display data.<BR>
	 *         Designating any instance other than <CODE>StoragePlanParameter</CODE> throws ScheduleException.<BR>
	 * @return Array of <CODE>StoragePlanParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record found, return the array of the number of elements equal to 0.<BR>
	 *          Return null if the search result count exceeds 1000 or when input condition error occurs.<BR>
	 *          Returning array with element qty 0 (zero) or null allows to obtain the error contents as a message using <CODE>getMessage()</CODE> method.<BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.<BR>
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.<BR>
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		StorageSupportParameter param = (StorageSupportParameter)searchParam;

		// Check the Worker code and password
		if (!checkWorker(conn, param))
		{
			 return null;
		}

		// Check Input Area.
		if (!check(conn, searchParam))
		{
			return null;
		}

		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
		WorkingInformationSearchKey namesearchKey = new WorkingInformationSearchKey();

		// Search Data
		// Work division(Storage )
		searchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		namesearchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		// Status flag (Standby)
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		namesearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);

		// Consignor code
		searchKey.setConsignorCode(param.getConsignorCode());
		namesearchKey.setConsignorCode(param.getConsignorCode());
		// Planned start storage DatePlanned end storage Date
		String fromplandate = param.getFromStoragePlanDate();
		String toplandate = param.getToStoragePlanDate();

		if (!StringUtil.isBlank(fromplandate))
		{
			searchKey.setPlanDate(fromplandate, ">=");
			namesearchKey.setPlanDate(fromplandate, ">=");
		}
		if (!StringUtil.isBlank(toplandate))
		{
			searchKey.setPlanDate(toplandate, "<=");
			namesearchKey.setPlanDate(toplandate, "<=");
		}

		// Item Code
		String itemcode = param.getItemCodeCondition();
		if (!StringUtil.isBlank(itemcode))
		{
			searchKey.setItemCode(itemcode);
			namesearchKey.setItemCode(itemcode);
		}

		// Case/Piece division(Work type)(for search)
		// other than All
		if (!param.getCasePieceflgCondition().equals(StorageSupportParameter.CASEPIECE_FLAG_ALL))
		{
			// Case
			if(param.getCasePieceflgCondition().equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
			{
				searchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			}
			// Piece
			else if(param.getCasePieceflgCondition().equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				searchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
			}
			// None
			else if(param.getCasePieceflgCondition().equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				searchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
			}
		}

		// Sort by Planned Storage Date, Item Code, Case/Piece division (work type), and storage location in ascending order.
		searchKey.setPlanDateOrder(1, true);
		searchKey.setItemCodeOrder(2, true);
		searchKey.setWorkFormFlagOrder(3, true);
		searchKey.setLocationNoOrder(4, true);

		if (!canLowerDisplay(workingHandler.count(searchKey)))
		{
			return returnNoDisplayParameter();
		}

		WorkingInformation[] resultEntity = (WorkingInformation[])workingHandler.find(searchKey);

		// Obtain the Consignor Name with the later added date/time.
		namesearchKey.setRegistDateOrder(1, false);
		WorkingInformation[] working = (WorkingInformation[])workingHandler.find(namesearchKey);
		String consignorname = "";
		if(working != null && working.length != 0)
		{
			consignorname = working[0].getConsignorName();
		}

		Vector vec = new Vector();

		for (int i = 0; i < resultEntity.length; i++)
		{
			StorageSupportParameter dispData = new StorageSupportParameter();

			// Consignor code
			dispData.setConsignorCode(resultEntity[i].getConsignorCode());
			// Consignor Name (Consignor Name with later Added Date/Time)
			dispData.setConsignorName(consignorname);
			// Planned storage date
			dispData.setStoragePlanDate(resultEntity[i].getPlanDate());
			// Item Code
			dispData.setItemCodeL(resultEntity[i].getItemCode());
			// Item Name
			dispData.setItemName(resultEntity[i].getItemName1());
			// Case/Piece division name
			// Obtain Case/Piece division name from Case/Piece division (work type).
			dispData.setCasePieceflgName(DisplayUtil.getPieceCaseValue(resultEntity[i].getWorkFormFlag()));
			// Storage Location
			dispData.setStorageLocation(resultEntity[i].getLocationNo());
			// Total Storage Qty
			dispData.setTotalPlanQty(resultEntity[i].getPlanEnableQty());
			// Packed qty per Case
			dispData.setEnteringQty(resultEntity[i].getEnteringQty());
			// Packed qty per bundle
			dispData.setBundleEnteringQty(resultEntity[i].getBundleEnteringQty());

			// Remaining Case Qty
			dispData.setPlanCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getPlanEnableQty(), resultEntity[i].getEnteringQty(),
					resultEntity[i].getWorkFormFlag()));
			// Remaining Piece Qty
			dispData.setPlanPieceQty(DisplayUtil.getPieceQty(resultEntity[i].getPlanEnableQty(), resultEntity[i].getEnteringQty(),
					resultEntity[i].getWorkFormFlag()));
			// Expiry Date
			dispData.setUseByDate(resultEntity[i].getUseByDate());
			// Case ITF
			dispData.setITF(resultEntity[i].getItf());
			// Bundle ITF
			dispData.setBundleITF(resultEntity[i].getBundleItf());
			// Work No.
			dispData.setJobNo(resultEntity[i].getJobNo());
			// Last update date/time
			dispData.setLastUpdateDate(resultEntity[i].getLastUpdateDate());

			vec.addElement(dispData);
		}

		StorageSupportParameter[] paramArray = new StorageSupportParameter[vec.size()];
		vec.copyInto(paramArray);

		// 6001013 = Data is shown.
		wMessage = "6001013";
		return paramArray;
	}

	/**
	 * Check the contents of the parameter for its properness. According to the contents set for the parameter designated in <CODE>searchParam</CODE>,<BR>
	 * execute the process for checking the input in the parameter. Implement of the check process depends on the class that implements this interface.<BR>
	 * Return true if the contents of the parameter is correct.<BR>
	 * If the contents of the parameter has some problem, return false. Enable to obtain the contents using <CODE>getMessage()</CODE> method.
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param  conn               Database connection object
	 * @param  searchParam        This parameter class includes contents to be checked for input.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 */
	public boolean check(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		StorageSupportParameter param = (StorageSupportParameter)searchParam;

		// Obtain the Planned start storage Date and Planned end storage date from the parameter.
		String fromplandate = param.getFromStoragePlanDate();
		String toplandate = param.getToStoragePlanDate();

		// Planned storage date Check the value for larger or smaller
		if (!StringUtil.isBlank(fromplandate) && !StringUtil.isBlank(toplandate))
		{
			if (fromplandate.compareTo(toplandate) > 0)
			{
				// 6023199 = Starting planned storage date must precede the end planned storage date.
				wMessage = "6023199";
				return false;
			}
		}

		return true;
	}

	/**
	 * Fetch the contents displayed in the preset area as parameter and call the storage setting process<BR>
	 * Return the preset area contents if the process completes normally<BR>
	 * Return null in case of any error<BR>
	 * It is possible to fetch the error details using <CODE>getMessage</CODE> method<BR>
	 * Refer to class for detailed explanation<BR>
	 * @param conn Database connection object
	 * @param startParams parameter that contains contents to be set
	 * @return Return true if schedule process succeeds. Else false
	 * @throws ReadWriteException Throw this exception in case of database error.
	 * @throws ScheduleException  Throw this exception if unexpected error occurs
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		try
		{
			// Check the Worker code and password
			StorageSupportParameter workparam = (StorageSupportParameter)startParams[0];
			if (!checkWorker(conn, workparam))
			{
				return null;
			}

			// Check the Daily Update Processing.
			if (isDailyUpdate(conn))
			{
				return null;
			}

			// Check for exclusion of all the target data.
			if (!lockAll(conn, startParams))
			{
				// 6003006=Unable to process this data. It has been updated via other work station.
				wMessage = "6003006";
				return null;
			}
			// Check for exclusion of the target Center Inventory.
			lockOccupiedStock(conn, startParams);

			// Check for input in the preset area.
			if (!checkList(conn, startParams))
			{
				return null;
			}

			// Work division(Storage )
			String jobtype = WorkingInformation.JOB_TYPE_STORAGE;
			//Process name
			String processname = PROCESSNAME;
			// Worker Code
			String workercode = workparam.getWorkerCode();
			// Worker Name
			WorkerHandler workerHandler = new WorkerHandler(conn);
			WorkerSearchKey searchKey = new WorkerSearchKey();
			searchKey.setWorkerCode(workparam.getWorkerCode());
			// Do not include the availability of Delete division in the search condition.
			Worker worker = (Worker) workerHandler.findPrimary(searchKey);
			String workername = worker.getName();

			WareNaviSystemHandler warenaviHandler = new WareNaviSystemHandler(conn);
			WareNaviSystemSearchKey wsearchKey = new WareNaviSystemSearchKey();

			// Search Data
			WareNaviSystem wms = (WareNaviSystem) warenaviHandler.findPrimary(wsearchKey);
			if (wms == null)
			{
				// 6027006=Data mismatch occurred. Please refer to log. TABLE={0}
				RmiMsgLogClient.write("6006040" + wDelim + "WareNaviSystem", "NoPlanStorageSCH");
				// Throw ScheduleException here.(Do not need to set error message.)
				throw (new ScheduleException());
			}
			// Work Date ( System defined date)
			String sysdate = wms.getWorkDate();

			// Terminal No.
			String terminalno = workparam.getTerminalNumber();

			int workqty = 0;

			// Check Exclusion.
			// Lock all the committed Work status to prevent from deadlocking.
			for (int i = 0; i < startParams.length; i++)
			{
				StorageSupportParameter param = (StorageSupportParameter)startParams[i];

				// Work No.
				String jobno = param.getJobNo();
				// Last update date/time
				Date lastupdate = param.getLastUpdateDate();


				// Preset line No.
				int rowno = param.getRowNo();

				if (!lock(conn, jobno, lastupdate))
				{
					// 6023209 = No.{0} The data has been updated via other terminal.
					wMessage = "6023209" + wDelim + rowno;
					return null;
				}
			}

			// Update the Work status, Storage Plan info, and then inventory information in this sequence to prevent from deadlocking.
			// Add or update the Worker Result data inquiry, after completed the storage process for one commitment.
			for (int i = 0; i < startParams.length; i++)
			{
				StorageSupportParameter param = (StorageSupportParameter)startParams[i];

				// Work No.
				String jobno = param.getJobNo();

				String usebydate = param.getUseByDate();
				String locationno = param.getStorageLocation();

				int enteringqty = param.getEnteringQty();
				int planqty = param.getPlanCaseQty() * enteringqty + param.getPlanPieceQty();
				// For operating shortage
				int inputqty = param.getResultCaseQty() * enteringqty + param.getResultPieceQty();

				// Work Quantity (Totalize the result qty of Work status for one commitment)
				workqty += inputqty;
				// Check for overflow of the Work Quantity
				if (workqty > WmsParam.WORKER_MAX_TOTAL_QTY)
				{
					workqty = WmsParam.WORKER_MAX_TOTAL_QTY;
				}

				// Update Work Status Table (DNWORKINFO)
				if (!updateWorkinginfo(conn, jobno, planqty, inputqty, usebydate, locationno, workercode, workername, terminalno, param.getRemnantFlag()))
				{
					return null;
				}

				// Update of Storage Plan information table (DNSTORAGEPLAN)
				if (!updateStoragePlan(conn, jobno, planqty, inputqty, param.getRemnantFlag(), param.getShortage()))
				{
					return null;
				}

				// Completed Storage process(DMSTOCK, DNHOSTSEND)
				StorageCompleteOperator Storage = new StorageCompleteOperator();
				if (!Storage.complete(conn, jobno, processname))
				{
					return null;
				}

			}

			// Add or update the Worker Result data inquiry table (DNWORKERRESULT).
			// Add or update the Worker Result data inquiry, after completed adding or updating the inventory information table for one commitment.
			updateWorkerResult(conn, workercode, workername, sysdate, terminalno, jobtype, workqty);

			StorageSupportParameter[] viewParam = null;
			// Succeeded in scheduling.
			viewParam = (StorageSupportParameter[])query(conn, workparam);

			// 6001006 = Data is committed.
			wMessage = "6001006";

			return viewParam;
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (OverflowException e)
		{
			// 6023348 = Cannot enter.  Stock Qty. exceeds {0}.。
			wMessage = "6023348" + wDelim + MAX_STOCK_QTY_DISP;
			return null;
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * Check for input in the preset area.<BR>
	 * Check storage location input, overflow, storage qty, shortage or partial delivery<BR>
	 * @param conn Database connection object
	 * @param searchParams Parameter class that contains the contents to be set
	 * @return Check result (normal : true error : false)
	 * @throws ReadWriteException Throw exception if error occurs during database connection
	 * @throws ScheduleException  Throw exception if unexpected error occurs
	 */
	protected boolean checkList(Connection conn, Parameter[] searchParams) throws ReadWriteException, ScheduleException
	{
		for (int i = 0; i < searchParams.length; i++)
		{
			StorageSupportParameter param = (StorageSupportParameter)searchParams[i];

			//  Line No.
			int rowno = param.getRowNo();

			// Check for mandatory input in the storage location.
			String location = param.getStorageLocation();
			if (StringUtil.isBlank(location))
			{
			    if(param.getResultCaseQty() * param.getEnteringQty() + param.getResultPieceQty() != 0){

					// 6023268 = No.{0} Please enter the storage location.
					wMessage = "6023268" + wDelim + rowno;
					return false;
			    }
			}
			else
			{
				LocateOperator locateOperator = new LocateOperator(conn);
				if (locateOperator.isAsrsLocation(location))
				{
					// 6023443=No.{0} The specified location is in automatic warehouse. Cannot enter.
					wMessage = "6023443" + wDelim + rowno;
					return false;
				}
			}

			// Obtain the Packed qty per Case, Storage Case Qty, Storage Piece Qty, and Shortage division from the parameter.
			int enteringqty = param.getEnteringQty();
			int planqty = param.getPlanCaseQty() * enteringqty + param.getPlanPieceQty();
			int caseqty = param.getResultCaseQty();
			int pieceqty = param.getResultPieceQty();
			int inputqty = caseqty * enteringqty + pieceqty;

			// Check for over flow of the Storage Qty.
			if(inputqty > planqty)
			{
				// 6023155=Completed quantity exceeded the expected quantity. Please check and reenter.
				wMessage = "6023155" + wDelim + rowno;
				return false;
			}

			// If the Packed qty per Case is 0 and the Storage Case Qty is any value other than 0:
			if (enteringqty == 0 && caseqty !=0)
			{
				// 6023271 = No.{0} If Packed Qty. per Case is 0
				wMessage = "6023294" + wDelim + rowno;
				return false;
			}

			// When Storage qty is 0
			if (inputqty == 0)
			{
				// Separate delivery
				if (param.getRemnantFlag() == true)
				{
					// 6023343=No.{0} Enter 1 or greater value in Received Case Qty. or Piece Qty. to set Additional Delivery.
					wMessage = "6023382" + wDelim + rowno;
					return false;

				}
				// Shortage
				else if (param.getShortage() == false)
				{
					// 6023117 = No.{0} Planned quantity differs from completed actual quantity. Please check and reenter.
					wMessage = "6023117" + wDelim + rowno;
					return false;
				}
			}
			else if (inputqty != 0)
			{
				if (param.getRemnantFlag() == true && param.getShortage() == true)
				{
					// 6023500=Can't select division or shortage option.
					wMessage = "6023500" + wDelim + rowno;
					return false;
				}

				// Input value is less than planned qty.
				if (inputqty < planqty)
				{
					// Shortage flag (none), Pending/Additional Delivery flag (none)
					if (param.getShortage() == false && param.getRemnantFlag() == false)
					{
						// 6023117 = No.{0} Planned quantity differs from completed actual quantity. Please check and reenter.
						wMessage = "6023117" + wDelim + rowno;
						return false;
					}
				}
				// Ensure that the input value and the planned qty are equal.
				else if (inputqty == planqty)
				{
					//With shortage flag
					if (param.getShortage() == true)
					{
						// 6023119=No.{0} Shortage is designated where there is no shortage. Please check and reenter.
						wMessage = "6023119" + wDelim + rowno;
						return false;
					}
					// With Pending/Additional Delivery flag
					if (param.getRemnantFlag() == true)
					{
						// No.{0} Check the line. Designating the Planned Work Qty and Completed Qty to a same line requires to check and try again to add them.
						wMessage = "6023501" + wDelim + rowno;
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Check for any change by other terminal.
	 * Compare the last update date/time obtained from the current DB with the last update date/time set for the parameter.
	 * Compare both data. If both last updated date/time is equal, regard such data as data not modified via other terminal.
	 * If not equal, regard the data as data that was changed/modified at other work station.
	 * Require to compare the data in the Work status table (DNWORKINFO).
	 * @param conn       Database connection object
	 * @param jobno      Work No.
	 * @param lastupdate Last update date/time
	 * @return Return true when changed already via other terminal, or return false when not changed yet.
	 * Invoking this throws <CODE>ScheduleException</CODE>.
	 * @throws ScheduleException Announce it when this method is invoked.
	 */
	protected boolean lock(Connection conn, String jobno, Date lastupdate) throws ReadWriteException, ScheduleException
	{
		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		// Lock Data.
		// Work No.
		searchKey.setJobNo(jobno);
		// Work division(Storage )
		searchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);

		WorkingInformation[] working = (WorkingInformation[])workingHandler.findForUpdate(searchKey);

		// Work No. does not exist in a data (in the case where data was deleted)
		if (working == null || working.length == 0)
		{
			return false;
		}
		// Execute with status flag other than Standby (when extraction was executed or when the status is changed by executing maintenance)
		if (!(working[0].getStatusFlag()).equals(WorkingInformation.STATUS_FLAG_UNSTART))
		{
			return false;
		}

		// Compare the last update date/time obtained from the current DB with the last update date/time set for the parameter.
		// If not equal, regard the data as data that was changed/modified at other work station.
		if (!working[0].getLastUpdateDate().equals(lastupdate))
		{
			return false;
		}

		return true;
	}

	/**
	 * Update the Work status table.
	 * @param conn Database connection object
	 * @param jobno      Work No.
	 * @param planqty    Possible Work Qty
	 * @param inputqty   Work Result qty
	 * @param usebydate  Expiry Date
	 * @param locationno Work Result Location
	 * @param workercode Worker Code
	 * @param workername Worker Name
	 * @param terminalno Terminal No.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected boolean updateWorkinginfo(Connection conn, String jobno, int planqty, int inputqty, String usebydate, String locationno, String workercode, String workername, String terminalno, boolean remnantFlag) throws ReadWriteException, ScheduleException
	{
		try
		{
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
			WorkingInformationAlterKey alterKey = new WorkingInformationAlterKey();
			WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
			WorkingInformation[] workInfo = null;

			// Set the Work No. and work division (storage).
			alterKey.setJobNo(jobno);
			searchKey.setJobNo(jobno);
			alterKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);

			workInfo = (WorkingInformation[])workingHandler.find(searchKey);
			if (workingHandler.count(searchKey) <= 0)
			{
				return false;
			}

			int pendqty = planqty - inputqty;
			if (pendqty < 0)
				pendqty = 0;
			// 1.Update the Status flag of Work status to Completed (Update to Completed, whether completed or shorted).
			alterKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
			// When Pending/Additional delivery occurs, set the pending qty.
			if (remnantFlag)
			{
				alterKey.updatePendingQty(pendqty);
			}
			// 2.Update the last Update process name.
			alterKey.updateLastUpdatePname(PROCESSNAME);

			// 3.Update the Work result qty, work shortage qty, expiry date, and work result location based on the contents of the Received parameter.
			// Update the storage qty of the parameter to the work result qty of the Work status.
			alterKey.updateResultQty(inputqty);

			// For shortage, update the work shortage qty to the value obtained by subtracting the storage qty of the parameter from the possible work qty.
			if (!remnantFlag)
			{
				if (planqty != inputqty)
				{
					int shortage = planqty - inputqty;
					if (shortage < 0)
						shortage = 0;
					alterKey.updateShortageCnt(shortage);
				}
			}
			// Update the expiry date of the parameter to the expiry date of the Work status (result_use_by_date). (Set it for data with Completed or Shortage)
			alterKey.updateResultUseByDate(usebydate);

			// Update the storage location of the parameter to the work result location Work status (result_location_no).
			alterKey.updateResultLocationNo(locationno);
			// Update the Area.
			LocateOperator locOpe = new LocateOperator(conn);
			AreaOperator areaOpe = new AreaOperator(conn);
			String areaNo = locOpe.getAreaNo(locationno);
			alterKey.updateAreaNo(areaNo);
			alterKey.updateSystemDiscKey(Integer.parseInt(areaOpe.getAreaType(areaNo)));

			// 4.Update the Worker code, Worker name, and Terminal No. based on the contents of the Received parameter,
			alterKey.updateWorkerCode(workercode);
			alterKey.updateWorkerName(workername);
			alterKey.updateTerminalNo(terminalno);

			// Update Data.
			workingHandler.modify(alterKey);
			// Generate Work status of Pending/Additional delivery.
			if (remnantFlag)
			{
				// Work status entity for new data in the case of Pending/Additional delivery
				WorkingInformation newWorkinfo = null;
				// Set the value of later record to be divided.
				newWorkinfo = (WorkingInformation) workInfo[0].clone();
				newWorkinfo.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
				newWorkinfo.setPlanQty(planqty - inputqty);
				newWorkinfo.setPlanEnableQty(planqty - inputqty);
				newWorkinfo.setResultQty(0);
				newWorkinfo.setPendingQty(0);
				newWorkinfo.setTerminalNo("");
				newWorkinfo.setWorkerCode("");
				newWorkinfo.setWorkerName("");
				newWorkinfo.setResultUseByDate("");
				newWorkinfo.setRegistPname(PROCESSNAME);
				newWorkinfo.setLastUpdatePname(PROCESSNAME);
				// Assign a new work No.
				SequenceHandler sh = new SequenceHandler(conn);
				newWorkinfo.setJobNo(sh.nextJobNo());
				// Set the Work No. for Aggregation Work No.
				newWorkinfo.setCollectJobNo(newWorkinfo.getJobNo());
				workingHandler.create(newWorkinfo);

			}
			return true;

		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (DataExistsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	/**
	 * Update the Storage Plan info table.<BR>
	 * @param conn Database connection object<BR>
	 * @param jobno     Work No.
	 * @param planqty   Possible Work Qty
	 * @param inputqty  Work Result qty
	 * @param remnantFlag partial delivery flag
	 * @param shortageFlag shortage flag
	 * @return Update result (normal：true abnormal：false)
	 * @throws ReadWriteException Throw exception if database error occurs
	 * @throws ScheduleException Throw exception if unexpected error occurs
	 */
	protected boolean updateStoragePlan(Connection conn, String jobno, int planqty, int inputqty, boolean remnantFlag,
	boolean shortageFlag) throws ReadWriteException, ScheduleException
	{
		try
		{
			//Search for the Work status.
			WorkingInformationHandler wih = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey wiKey = new WorkingInformationSearchKey();

			wiKey.setJobNo(jobno);
			wiKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
			wiKey.setPlanUkeyCollect();

			WorkingInformation working = (WorkingInformation)wih.findPrimary(wiKey);

			if (working != null)
			{
				String planukey = working.getPlanUkey();

				StoragePlanHandler planHandler = new StoragePlanHandler(conn);
				StoragePlanSearchKey planSearchKey = new StoragePlanSearchKey();

				// Search Data
				// Storage Plan unique key
				planSearchKey.setStoragePlanUkey(planukey);
				// Require to search data using the status flag (Standby, Partially Completed, or Processing).
				String[] status = { StoragePlan.STATUS_FLAG_UNSTART, StoragePlan.STATUS_FLAG_COMPLETE_IN_PART, StoragePlan.STATUS_FLAG_NOWWORKING};
				planSearchKey.setStatusFlag(status);

				StoragePlan storage = (StoragePlan) planHandler.findPrimary(planSearchKey);

				if (storage != null)
				{
					// Shortage quantity this time
					int shortageqty = planqty - inputqty;
					// Previously completed Qty + This time completed Qty
					int resultqty = storage.getResultQty() + inputqty;

					StoragePlanAlterKey alterKey = new StoragePlanAlterKey();
					alterKey.setStoragePlanUkey(planukey);
					alterKey.updateLastUpdatePname(PROCESSNAME);
					alterKey.updateResultQty(resultqty);
					if (shortageqty > 0)
					{
						// When pending/additional storage is generated, shortage qty of this time is made to 0 as it is not shortage.
						if (remnantFlag)
						{
							shortageqty = 0;
						}
						// In the event of shortage
						else if (shortageFlag)
						{
							alterKey.updateShortageCnt(storage.getShortageCnt() + shortageqty);
						}
					}

					// If Planned Qty of Plan information is equal to the total of This time completed Qty + This time shortage Qty + Previously completed Qty + Previous shortage Qty,
					// update the Plan data to Completed.
					if (storage.getPlanQty() == (resultqty + shortageqty + storage.getShortageCnt()))
					{
						alterKey.updateStatusFlag(StoragePlan.STATUS_FLAG_COMPLETION);
					}
					else
					{
						// If there is (are) one or more processing work (s), maintain the status as it is.
						wiKey.KeyClear();
						String[] jobstatus = {StoragePlan.STATUS_FLAG_NOWWORKING};
						wiKey.setStatusFlag(jobstatus);
						wiKey.setJobNo(jobno, "!=");
						wiKey.setPlanUkey(planukey);
						if (wih.count(wiKey) == 0)
						{
							alterKey.updateStatusFlag(StoragePlan.STATUS_FLAG_COMPLETE_IN_PART);
						}
					}

					// Update Data.
					planHandler.modify(alterKey);

					return true;
				}
				else
				{
					// 6006040 = Data mismatch occurred. See log.{0}
					RmiMsgLogClient.write("6006040" + wDelim + planukey, "SetStorageSCH");
					// Throw ScheduleException here.(Do not need to set error message.)
					throw (new ScheduleException());
				}
			}
			else
			{
				// 6006040 = Data mismatch occurred. See log.{0}
				RmiMsgLogClient.write("6006040" + wDelim + jobno, "SetStorageSCH");
				// Throw ScheduleException here.(Do not need to set error message.)
				throw (new ScheduleException());
			}
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//	Private methods -----------------------------------------------
}
//end of class
