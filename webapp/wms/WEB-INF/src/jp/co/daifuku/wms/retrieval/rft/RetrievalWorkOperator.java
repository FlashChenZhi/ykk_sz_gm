// $Id: RetrievalWorkOperator.java,v 1.3 2007/02/07 04:19:45 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

//#CM721436
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WorkOperator;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;

//#CM721437
/**
 * Designer : Y.Taki <BR>
 * Maker :   <BR>
 * <BR>
 * Define the common function to be invoked from the "Complete Picking"process from RFT.<BR>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:45 $
 * @author  $Author: suresh $
 */
public class RetrievalWorkOperator extends WorkOperator
{
    //#CM721438
    /**
	 * A field that represents a class name.
	 */
    private static final String CLASS_NAME = "RetrievalOperate";

	//#CM721439
	// Constructors --------------------------------------------------
	//#CM721440
	/**
	 * Generate an instance.<BR>
	 * Generating an object requires to initialize it using initialize() method before using this object.
	 * 
	 */
	public RetrievalWorkOperator()
	{
		super();
	}

	//#CM721441
	// Public methods ------------------------------------------------
	//#CM721442
	/**
	 * Picking result data  [ Commit Process] <BR>
	 * Execute the following process for submitting the Work Status.<BR>
	 *
	 * <OL>
	 *  <LI>Lock the target data to update.
	 * ({@link #lockUpdateData(WorkingInformation [] ,String,String,boolean) lockUpdateData()})</LI>
	 *  <LI>Update the work status to "Completed", and 
	 *  generate the related Plan Info, Sending Result Information, and inventory information, and update the data.
	 * ({@link #updateWorkingInformation(String,String,WorkingInformation [] ) updateWorkingInformation()})</LI>
	 *  <LI>Update the Picking Plan Info to the appropriate state after committing the data.
	 * ({@link #updateCompletionStatus(String [] ) updateCompletionStatus()})</LI>
	 *  <LI>Update the Worker Result info.
	 * ({@link #updateWorkerResult(String,String,WorkingInformation [] ) updateWorkerResult()})</LI>
	 *  <LI>Execute the process for shifting the inventory. (Not implemented)</LI>
	 * </OL>
	 *
	 * <BR>
	 * @param	resultData		Picking Work Result info (Array of Work Status Entity)
	 * @param	workerCode		Worker Code
	 * @param	rftNo			RFT No.
	 * @param  isShortage		Shortage flag
	 * @throws UpdateByOtherTerminalException Announce when failing to updatethe data, which was updated via other terminal.
	 * @throws LockTimeOutException Announce when database lock is not cancelled for the specified period.
	 * @throws InvalidStatusException Announce when the setting value to update the table is inconsistent.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws NotFoundException Announce when the Update target data does not exist.
	 * @throws InvalidDefineException Announce when the designated value is abnormal (blank or containing prohibited characters).
	*/
	public void completeWorkingData(
			jp.co.daifuku.wms.base.rft.WorkingInformation[] resultData,
			String workerCode,
			String rftNo,
			boolean isShortage)
		throws UpdateByOtherTerminalException, LockTimeOutException, InvalidStatusException, ReadWriteException, NotFoundException, InvalidDefineException
	{
	    //#CM721443
	    // Lock the target data to update.
		String[] planUkeyList = lockUpdateData(
			resultData,
			rftNo,
			workerCode,
			false);

		//#CM721444
		// Generate a Work Status and the related Plan Info, inventory information, and Sending Result Information and update the data.
		updateWorkingInformation(
			rftNo,
			workerCode,
			resultData,
			isShortage);

		//#CM721445
		// Update the status of the Picking Plan Info.
		updateCompletionStatus(planUkeyList);

		//#CM721446
		// Generate a Worker Result.
		updateWorkerResult(workerCode, rftNo, resultData);
	}

	//#CM721447
	/**
	 * Picking result data  [ Commit Process] <BR>
	 * Execute the following process for submitting the Work Status.<BR>
	 *
	 * <OL>
	 *  <LI>Lock the target data to update (Include the DMLOCATE in this target).
	 * ({@link #lockUpdateData(WorkingInformation [] ,String,String,boolean) lockUpdateData()})</LI>
	 *  <LI>Update the work status to "Completed", and 
	 *  generate the related Plan Info, Sending Result Information, and inventory information, and update the data.
	 * ({@link #updateWorkingInformation(String,String,WorkingInformation [] ) updateWorkingInformation()})</LI>
	 *  <LI>Update the Picking Plan Info to the appropriate state after committing the data.
	 * ({@link #updateCompletionStatus(String [] ) updateCompletionStatus()})</LI>
	 *  <LI>Update the Worker Result info.
	 * ({@link #updateWorkerResult(String,String,WorkingInformation [] ) updateWorkerResult()})</LI>
	 *  <LI>Execute the process for shifting the inventory. (Not implemented)</LI>
	 * </OL>
	 *
	 * <BR>
	 * @param	resultData		Picking Work Result info (Array of Work Status Entity)
	 * @param	workerCode		Worker Code
	 * @param	rftNo			RFT No.
	 * @param  isShortage		Shortage flag
	 * @throws UpdateByOtherTerminalException Announce when failing to updatethe data, which was updated via other terminal.
	 * @throws LockTimeOutException Announce when database lock is not cancelled for the specified period.
	 * @throws InvalidStatusException Announce when the setting value to update the table is inconsistent.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws NotFoundException Announce when the Update target data does not exist.
	 * @throws InvalidDefineException Announce when the designated value is abnormal (blank or containing prohibited characters).
	*/
	public void completePcWorkingData(
			jp.co.daifuku.wms.base.rft.WorkingInformation[] resultData,
			String workerCode,
			String rftNo,
			boolean isShortage)
		throws UpdateByOtherTerminalException, LockTimeOutException, InvalidStatusException, ReadWriteException, NotFoundException, InvalidDefineException
	{
	    startProcessName = new String[2];
	    startProcessName[0] = "ID0234";
	    startProcessName[1] = "ID0236";

		//#CM721448
		// Lock the target data to update.
		String[] planUkeyList = lockUpdateDataForOrderRetrieval(
			resultData,
			rftNo,
			workerCode,
			false);

		//#CM721449
		// Generate a Work Status and the related Plan Info, inventory information, and Sending Result Information and update the data.
		updateWorkingInformationForInPart(
			rftNo,
			workerCode,
			resultData);

		//#CM721450
		// Update the status of the Picking Plan Info.
		updateCompletionStatus(planUkeyList);

		//#CM721451
		// Generate a Worker Result.
		updateWorkerResult(workerCode, rftNo, resultData);
	}

	//#CM721452
	/**
	 * Picking result data  [Process to cancel] <BR>
	 * Execute the following processes as a process for canceling the Picking..
	 *
	 * <OL>
	 *  <LI>Lock the target data to update.
	 * ({@link #lockUpdateData(WorkingInformation [] ,String,String,boolean) lockUpdateData()})</LI>
	 *  <LI>Update the corresponding data in the Work Status, to "Standby".
	 * ({@link #updateWorkingInformation(String,String,WorkingInformation [] ) updateWorkingInformation()})</LI>
	 *  <LI>Update the Picking Plan Info to the appropriate state after canceling the data.
	 * ({@link #updateCompletionStatus(String [] ) updateCompletionStatus()})</LI>
	 * </OL>
	 *
	 * <DIR>
	 * Update Work Status.<BR>
	 *     (search conditions) 
	 *    <UL>
	 *     <LI>Status flag=2:Processing</LI>
	 *     <LI>Work division=3:Picking</LI>
	 *     <LI>Aggregation Work No.</LI>
	 * 	   <LI>Terminal No. = RFT No.</LI>
	 *    </UL>
	 *     (Content to be updated) 
	 *    <TABLE>
	 *      <TR><TD>Status flag</TD>		<TD>Standby:0</TD></TR>
	 *      <TR><TD>Terminal No.</TD>			<TD>Blank</TD></TR>
	 *      <TR><TD>Worker Code</TD>	<TD>Blank</TD></TR>
	 *    </TABLE>
	 *   Require to take account of the case where two or more records may correspond.<BR>
	 * </DIR>
	 *
	 * @param	resultData		Picking Work Result
	 * 							 (Use the value of the Work Status to be updated to update the work type) 
	 * @param	workerCode		Worker Code
	 * @param	rftNo			RFT No.
	 * @throws NotFoundException Announce when the Update target data does not exist.
	 * @throws InvalidDefineException Announce when the designated value is abnormal (blank or containing prohibited characters).
	 * @throws UpdateByOtherTerminalException Announce when failing to updatethe data, which was updated via other terminal.
	 * @throws LockTimeOutException Announce when database lock is not cancelled for the specified period.
	 * @throws InvalidStatusException Announce when the setting value to update the table is inconsistent.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	*/
	public void cancel(
		WorkingInformation[] resultData,
		String workerCode,
		String rftNo)
		throws NotFoundException, InvalidDefineException, UpdateByOtherTerminalException, ReadWriteException, LockTimeOutException, InvalidStatusException
	{
	    //#CM721453
	    // Lock the target data to update.
		String[] planUkeyList = lockUpdateData(
			resultData,
			rftNo,
			workerCode,
			true);

		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationAlterKey akey = new WorkingInformationAlterKey();

		for (int i = 0; i < resultData.length; i ++)
		{
		    skey.KeyClear();
			skey.setCollectJobNo(resultData[i].getCollectJobNo());
			skey.setTerminalNo(rftNo);
			skey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			if (! isMaintenanceCancel)
			{
				skey.setLastUpdatePname(startProcessName);
                skey.setWorkerCode(workerCode);
			}

			WorkingInformationHandler handler = new WorkingInformationHandler(conn);
			WorkingInformation[] wi = (WorkingInformation[])handler.find(skey);

			for (int j = 0; j < wi.length; j ++)
			{
			    akey.KeyClear();
				akey.setJobNo(wi[j].getJobNo());

				//#CM721454
				// Restore the Aggregation Work No. to its former value (Restore it to Work No.).
				akey.updateCollectJobNo(wi[j].getJobNo());
				akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
				akey.updateWorkerCode("");
				akey.updateWorkerName("");
				akey.updateTerminalNo("");
				akey.updateLastUpdatePname(processName);
				handler.modify(akey);
			}

			//#CM721455
			// Overwrite the designated work type with the work type of the actual data.
			//#CM721456
			//  (Required to request the next Item Picking data) 
			resultData[i].setWorkFormFlag(wi[0].getWorkFormFlag());
		}

		//#CM721457
		// Restore the status of the Shipping Plan Info to its former state based on the status of the Work Status.
		updateCompletionStatus(planUkeyList);
	}

	//#CM721458
	// Package methods -----------------------------------------------

	//#CM721459
	// Protected methods ---------------------------------------------
	//#CM721460
	/**
	 * Generate a Work Status and the related Plan Info, inventory information, and Sending Result Information and update the data.
	 *
	 * <OL>
	 * <LI>Update the Work Status.
	 * <DIR>
	 *     (search conditions) 
	 *    <UL>
	 *     <LI>Aggregation Work No.</LI>
	 *     <LI>Status flag=2:Processing</LI>
	 *     <LI>Work division=3:Picking</LI>
	 *     <LI>Planned date</LI>
	 *     <LI>Consignor Code</LI>
	 *     <LI>Worker Code</LI>
	 * 	   <LI>Terminal No.= RFT No.</LI>
	 *    </UL>
	 *     (Sorting order) 
	 *    <UL>
	 *     <LI>Work No.</LI>
	 *    </UL>
	 *     (Content to be updated) 
	 *    <TABLE border="1">
	 *      <TR><TD>Status flag</TD>		<TD>Completed:4</TD></TR>
	 *      <TR><TD>Work Result qty</TD>		<TD>Result qty in the electronic statement</TD></TR>
	 *      <TR><TD>Work shortage qty</TD>		<TD>If the Result qty in the electronic statement does not satisfy the Workable qty, set the shortage qty.</TD></TR>
	 *      <TR><TD>Result Location No.</TD>		<TD>Location No. in the electronic statement</TD></TR>
	 *      <TR><TD>result expiry date</TD>	<TD>Expiry Date in the electronic statement</TD></TR>
	 *      <TR><TD>Last update process name</TD>	<TD>"ID0035" or "ID0037"</TD></TR>
	 *    </TABLE>
	 *   Require to take account of the case where two or more records may correspond.<BR>
	 *   Allot the Result qty of the actually inspected items to the Work Result qty in the corresponding record. Allot the "Workable qty" of items..<BR>
	 *   If excessive, allot it to the end record.<BR>
	 * </DIR>
	 * </LI>
	 * <LI>Update the Qty in the Picking Plan Info.
	 * ({@link #updateRetrievalPlan(WorkingInformation) updateRetrievalPlan()})</LI>
	 * <LI>Update the inventory information.
	 * ({@link #updateStockQty(WorkingInformation) updateStockQty()})</LI>
	 * <LI>Generate a sending Result Info.
	 * ({@link #createResultData(WorkingInformation) createResultData()})</LI>
	 * </OL>
	 *
	 * @param	rftNo			RFT No.
	 * @param	workerCode		Worker Code
	 * @param	resultData		Picking Work Result info
	 * 							 (Use the value of the Work Status to be updated to update the work type) 
	 * @param	isShortage		Shortage flag
	 * @throws InvalidStatusException Announce when the setting value to update the table is inconsistent.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws NotFoundException Announce when the Update target data does not exist.
	 * @throws InvalidDefineException Announce when the designated value is abnormal (blank or containing prohibited characters).
	 */
	public void updateWorkingInformation(
		String rftNo,
		String workerCode,
		WorkingInformation[] resultData,
		boolean isShortage)
		throws InvalidStatusException, ReadWriteException, NotFoundException, InvalidDefineException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();

		WorkingInformationHandler handler =
			new WorkingInformationHandler(conn);

		for (int i = 0; i < resultData.length; i++)
		{
			//#CM721461
			// Search through the DB.
			skey.KeyClear();
			skey.setConsignorCode(resultData[i].getConsignorCode());
			skey.setPlanDate(resultData[i].getPlanDate());
			skey.setTerminalNo(rftNo);
			skey.setWorkerCode(workerCode);
			skey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			skey.setCollectJobNoOrder(1, true);
			skey.setJobNoOrder(2, true);
			//#CM721462
			// Aggregation Work No.
			skey.setCollectJobNo(resultData[i].getCollectJobNo());
			WorkingInformation[] wi = (WorkingInformation[]) handler.find(skey);

			if (wi.length <= 0)
			{
				String errData = "[ConsignorCode:" + resultData[i].getConsignorCode() +
								" PlanDate:" + resultData[i].getPlanDate() +
								" CollectJobNo:" + resultData[i].getCollectJobNo() +
								" RftNo:" + resultData[i].getTerminalNo() +
								" WorkerCode:" + resultData[i].getWorkerCode() +"]";
				//#CM721463
				// 6026016=No data you try to update is found. {0}
				RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);
				throw new NotFoundException();
			}

			//#CM721464
			// Maintain the Result qty of Picking Result info.
			int workQty = resultData[i].getResultQty();

			//#CM721465
			// Update data one by one allotting the Result qty.
			for (int j = 0; j < wi.length; j ++)
			{
				WorkingInformationAlterKey akey = new WorkingInformationAlterKey();
				akey.setJobNo(wi[j].getJobNo());
				akey.setConsignorCode(wi[j].getConsignorCode());
				akey.setPlanDate(wi[j].getPlanDate());
				akey.setTerminalNo(rftNo);
				akey.setWorkerCode(workerCode);

				int resultQty = workQty;

				if (resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_UNWORK))
				{
					//#CM721466
					// Restore the status of the data not-worked to "Not Worked".

					//#CM721467
					// Restore the Aggregation Work No. to its former value (Restore it to Work No.).
					akey.updateCollectJobNo(wi[j].getJobNo());
					akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
					akey.updateWorkerCode("");
					akey.updateWorkerName("");
					akey.updateTerminalNo("");
					akey.updateLastUpdatePname(processName);
					handler.modify(akey);
				}
				else
				{
					//#CM721468
					// Set the value to be updated.
					//#CM721469
					// For the next data, if any, with Result qty of the Picking Result info larger than the Workable qty:
					if (j < wi.length - 1 && resultQty > wi[j].getPlanEnableQty())
					{
						//#CM721470
						// Set the Workable qty for the Result qty.
						resultQty = wi[j].getPlanEnableQty();
					}
					workQty -= resultQty;

					akey.updateResultQty(resultQty);
					wi[j].setResultQty(resultQty);
					if (resultQty < wi[j].getPlanEnableQty())
					{
						akey.updateShortageCnt(wi[j].getPlanEnableQty() - resultQty);
						wi[j].setShortageCnt(wi[j].getPlanEnableQty() - resultQty);
					}
					akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
					wi[j].setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
					akey.updateResultUseByDate(resultData[i].getResultUseByDate());
					wi[j].setResultUseByDate(resultData[i].getResultUseByDate());
					akey.updateResultLocationNo(resultData[i].getLocationNo());
					wi[j].setResultLocationNo(resultData[i].getLocationNo());
					akey.updateLastUpdatePname(processName);

					//#CM721471
					// Update the DB.
					handler.modify(akey);

					//#CM721472
					// Update the Qty in the Shipping Plan Info.
					updatePlanInformation(wi[j]);
					//#CM721473
					// Update the Qty in the inventory information.
					updateStockQty(wi[j]);
					//#CM721474
					// Generate a sending Result Info.
					createResultData(wi[j]);
				}
			}

			//#CM721475
			// Overwrite the designated work type with the work type of the actual data.
			//#CM721476
			//  (Required to request the next Item Picking data) 
			resultData[i].setWorkFormFlag(wi[0].getWorkFormFlag());
		}
	}

	//#CM721477
	/**
	 * Generate a Work Status of the completed work and the related Work Status, Picking Plan Info, inventory information, and Sending Result Information and update the data.
	 * 
	 * For data with "Not Worked" Work, execute the process for cancel.
	 *
	 * <OL>
	 * <LI>Update the Work Status.
	 * <DIR>
	 *     (search conditions) 
	 *    <UL>
	 *     <LI>Aggregation Work No.</LI>
	 *     <LI>Status flag=2:Processing</LI>
	 *     <LI>Work division=3:Picking</LI>
	 *     <LI>Planned date</LI>
	 *     <LI>Worker Code</LI>
	 *     <LI>Item Code</LI>
	 * 	   <LI>Terminal No.= RFT No.</LI>
	 *    </UL>
	 *     (Sorting order) 
	 *    <UL>
	 *     <LI>Work No.</LI>
	 *    </UL>
	 *     (Content to be updated) 
	 *    <TABLE border="1">
	 *      <TR><TD>Status flag</TD>		<TD>Completed:4</TD></TR>
	 *      <TR><TD>Work Result qty</TD>		<TD>Result qty in the electronic statement</TD></TR>
	 *      <TR><TD>Pending Qty</TD>		    <TD>Workable qty - Result qty in the electronic statement</TD></TR>
	 *      <TR><TD>Result Location No.</TD>		<TD>Location No. in the electronic statement</TD></TR>
	 *      <TR><TD>result expiry date</TD>	<TD>Expiry Date in the electronic statement</TD></TR>
	 *      <TR><TD>Last update process name</TD>	<TD>"ID0235"</TD></TR>
	 *    </TABLE>
	 *   Require to take account of the case where two or more records may correspond.<BR>
	 *   Allot the Result qty of the actually inspected items to the Work Result qty in the corresponding record. Allot the "Workable qty" of items..<BR>
	 *   If excessive, allot it to the end record.<BR>
	 *   For box-change (the instructed qty is larger than the Result qty), regard the box-changed data as "Completed" and create a new work status for the work not executed Picking.
	 *   Contain the following items in the record to be generated. Require to the other data to be same as the source work status.
	 *     (Contents to be generated) 
	 *    <TABLE border="1">
	 *      <TR><TD>Work No.</TD>			<TD>Numbering</TD></TR>
	 *      <TR><TD>Status flag</TD>		<TD>Processing</TD></TR>
	 *      <TR><TD>Work Planned qty </TD>		<TD>Source Workable qty - Source Work Result qty</TD></TR>
	 *      <TR><TD>Workable qty</TD>		<TD>Source Workable qty - Source Work Result qty</TD></TR>
	 *      <TR><TD>Work Result qty</TD>		<TD>0</TD></TR>
	 *      <TR><TD>Work shortage qty</TD>		<TD>0</TD></TR>
	 *      <TR><TD>Pending Qty</TD>			<TD>0</TD></TR>
	 *      <TR><TD>Order Split No.</TD>	<TD>Source Order Split No. + 1</TD></TR>
	 *      <TR><TD>Name of Add Process</TD>		<TD>"ID0235"</TD></TR>
	 *      <TR><TD>Last update process name</TD>	<TD>"ID0235"</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * </LI>
	 * <LI>Update the Qty in the Picking Plan Info.
	 * ({@link #updateRetrievalPlan(WorkingInformation) updateRetrievalPlan()})</LI>
	 * <LI>Update the inventory information.
	 * ({@link #updateStockQty(WorkingInformation) updateStockQty()})</LI>
	 * <LI>Generate a sending Result Info.
	 * ({@link #createResultData(WorkingInformation) createResultData()})</LI>
	 * <LI>Update the Order Split No.
	 * ({@link #incrementOrderSeqNo(String, String, String, String, String, String) incrementOrderSeqNo()})</LI>
	 * </OL>
	 *
	 * @param	rftNo			RFT No.
	 * @param	workerCode		Worker Code
	 * @param	resultData		Picking Work Result info
	 * 							 (Use the value of the Work Status to be updated to update the work type) 
	 * @throws InvalidStatusException Announce when the setting value to update the table is inconsistent.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws NotFoundException Announce when the Update target data does not exist.
	 * @throws InvalidDefineException Announce when the designated value is abnormal (blank or containing prohibited characters).
	 */
	protected void updateWorkingInformationForInPart(
		String rftNo,
		String workerCode,
		WorkingInformation[] resultData)
		throws InvalidStatusException, ReadWriteException, NotFoundException, InvalidDefineException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();

		WorkingInformationHandler handler =
			new WorkingInformationHandler(conn);

		Hashtable orderSeqNo = new Hashtable();

		//#CM721478
		// Obtain the Order Split No.

		for (int i = 0; i < resultData.length; i ++)
		{
		    //#CM721479
		    // Ignore the data of box-change completed (Committed : 5)
		    if(resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_DECISION_PROSSING))
		    {
		        continue;
		    }
			//#CM721480
			// Search through the DB.
			skey.KeyClear();
			skey.setConsignorCode(resultData[i].getConsignorCode());
			skey.setPlanDate(resultData[i].getPlanDate());
			skey.setTerminalNo(rftNo);
			skey.setWorkerCode(workerCode);
			skey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			skey.setCollectJobNoOrder(1, true);
			skey.setJobNoOrder(2, true);
			//#CM721481
			// Aggregation Work No.
			skey.setCollectJobNo(resultData[i].getCollectJobNo());
			WorkingInformation[] wi = (WorkingInformation[]) handler.find(skey);
			WorkingInformationAlterKey akey = new WorkingInformationAlterKey();

			if (wi.length <= 0)
			{
				String errData = "[ConsignorCode:" + resultData[i].getConsignorCode() +
								" PlanDate:" + resultData[i].getPlanDate() +
								" CollectJobNo:" + resultData[i].getCollectJobNo() +
								" RftNo:" + resultData[i].getTerminalNo() +
								" WorkerCode:" + resultData[i].getWorkerCode() +"]";
				//#CM721482
				// 6026016=No data you try to update is found. {0}
				RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);
				throw new NotFoundException();
			}

			//#CM721483
			// Maintain the Result qty of Picking Result info.
			int workQty = resultData[i].getResultQty();

			//#CM721484
			// Update data one by one allotting the Result qty.
			for (int j = 0; j < wi.length; j ++)
			{
				if (resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_UNWORK))
				{
					//#CM721485
					// Restore the status of the data not-worked to "Not Worked".

					akey.KeyClear();
					akey.setJobNo(wi[j].getJobNo());

					//#CM721486
					// Restore the Aggregation Work No. to its former value (Restore it to Work No.).
					akey.updateCollectJobNo(wi[j].getJobNo());
					akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
					akey.updateWorkerCode("");
					akey.updateWorkerName("");
					akey.updateTerminalNo("");
					akey.updateLastUpdatePname(processName);
					handler.modify(akey);
				}
				else
				{
					//#CM721487
					// Update the Result of "Work Completed" data.
					akey.KeyClear();
					akey.setJobNo(wi[j].getJobNo());
					akey.setConsignorCode(wi[j].getConsignorCode());
					akey.setPlanDate(wi[j].getPlanDate());
					akey.setTerminalNo(rftNo);
					akey.setWorkerCode(workerCode);

					int resultQty = workQty;
					//#CM721488
					// Set the value to be updated.
					//#CM721489
					// For the next data, if any, with Result qty of the Picking Result info larger than the Workable qty:
					if (j < wi.length - 1 && resultQty > wi[j].getPlanEnableQty())
					{
						//#CM721490
						// Set the Workable qty for the Result qty.
						resultQty = wi[j].getPlanEnableQty();

					}
					workQty -= resultQty;

					akey.updateResultQty(resultQty);
					wi[j].setResultQty(resultQty);
					if (resultQty < wi[j].getPlanEnableQty())
					{
						//#CM721491
						// For Shortage:
						akey.updateShortageCnt(wi[j].getPlanEnableQty() - resultQty);
						wi[j].setShortageCnt(wi[j].getPlanEnableQty() - resultQty);
					}
					akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
					wi[j].setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
					akey.updateResultUseByDate(resultData[i].getResultUseByDate());
					wi[j].setResultUseByDate(resultData[i].getResultUseByDate());
					akey.updateResultLocationNo(resultData[i].getLocationNo());
					wi[j].setResultLocationNo(resultData[i].getLocationNo());
					akey.updateLastUpdatePname(processName);

					int seqNo;
					try
					{
						seqNo = Integer.parseInt((String) orderSeqNo.get(wi[j].getOrderNo()));
					}
					catch (Exception e)
					{
						seqNo = getNextOrderSeqNo(wi[j].getConsignorCode(),wi[j].getPlanDate(),wi[j].getOrderNo());
						orderSeqNo.put(wi[j].getOrderNo(), Integer.toString(seqNo));
					}
					//#CM721492
					// Set the Order Split No.
					wi[j].setOrderSeqNo(seqNo);
					akey.updateOrderSeqNo(seqNo);

					//#CM721493
					// Update the DB.
					handler.modify(akey);

					//#CM721494
					// Update the Qty in the Shipping Plan Info.
					updatePlanInformation(wi[j]);
					//#CM721495
					// Update the Qty in the inventory information.
					updateStockQty(wi[j]);
					//#CM721496
					// Generate a sending Result Info.
					createResultData(wi[j]);
				}
			}

			//#CM721497
			// Overwrite the designated work type with the work type of the actual data.
			//#CM721498
			//  (Required to request the next Item Picking data) 
			resultData[i].setWorkFormFlag(wi[0].getWorkFormFlag());
		}
	}

	//#CM721499
	/**
	 * Based on the updated Picking Plan unique key of work status, update the Status flag of the Picking Plan Info.
	 * <DIR>
	 *     (search conditions) 
	 *    <UL>
	 *     <LI>Picking Plan unique key = Work Status.Plan unique key  (One Shipping Plan Info corresponds the work status) </LI>
	 *     <LI>Status flag != "Deleted"</LI>
	 *    </UL>
	 *     (Content to be updated) 
	 *    <TABLE>
	 *      <TR><TD>Status flag</TD>		<TD>Depend on Work Status.</TD></TR>
	 *      <TR><TD>Last update process name</TD>	<TD>"ID0037"</TD></TR>
	 *    </TABLE>
	 *   Update the Status flag.<BR>
	 *   If all the work completed, update the data to "Completed".<BR>
	 *   If there is one or more processing work, regard the data as "Processing".<BR>
	 *   Otherwise, update the status to "Partially Completed". <BR>
	 * </DIR>
	 *
	 * @param	planUkey				Picking Plan unique key list
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws NotFoundException Announce when the Update target data does not exist.
	 * @throws InvalidDefineException Announce when the designated value is abnormal (blank or containing prohibited characters).
	 */
	protected void updateCompletionStatus(
	        String[] planUkey)
		throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		for (int i = 0; i < planUkey.length; i++)
		{
			WorkingInformationSearchKey skey =
				new WorkingInformationSearchKey();
			skey.setPlanUkey(planUkey[i]);

			WorkingInformation[] workinfo;
			WorkingInformationHandler wHandler =
				new WorkingInformationHandler(conn);
			workinfo = (WorkingInformation[]) wHandler.find(skey);

			RetrievalPlanHandler sHandler = new RetrievalPlanHandler(conn);
			RetrievalPlanAlterKey akey = new RetrievalPlanAlterKey();
			akey.setRetrievalPlanUkey(planUkey[i]);
			akey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");

			boolean existCompleteData = false;
			boolean existUnstartData = false;
			boolean existWorkingData = false;
			for (int j = 0; j < workinfo.length; j ++)
			{
				String status = workinfo[j].getStatusFlag();
				if (status.equals(WorkingInformation.STATUS_FLAG_COMPLETION))
				{
					existCompleteData = true;
				}
				else if (status.equals(WorkingInformation.STATUS_FLAG_UNSTART))
				{
					existUnstartData = true;
				}
				else
				{
					existWorkingData = true;
					break;
				}
			}
			if (existWorkingData || (! existCompleteData && ! existUnstartData))
			{
				//#CM721500
				// If there is one or more data with status "Processing" or if there is no data with "Completed" or "Standby", 
				//#CM721501
				// disable to update the Status flag.
				//#CM721502
				//  (Generally, such case is impossible) 
				continue;
			}
			if (existCompleteData)
			{
				if (existUnstartData)
				{
					//#CM721503
					// Update the data with both "Completed" and "Standby" status, to "Partially Completed".
					akey.updateStatusFlag(
						RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART);
				}
				else
				{
					//#CM721504
					// If the status of all the existing data is "Completed", update to "Completed".
					akey.updateStatusFlag(
					    RetrievalPlan.STATUS_FLAG_COMPLETION);
				}
			}
			else
			{
				//#CM721505
				// If the status of all the existing data is "Standby", update it to "Standby".
				akey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
			}
			akey.updateLastUpdatePname(processName);
			sHandler.modify(akey);
		}
	}

	//#CM721506
	/**
	 * Based on the updated work status, update the Stock qty and Allocated qty of the inventory info.
	 * <DIR>
	 *     (search conditions) 
	 *    <UL>
	 *     <LI>Stock ID = Work Status.Stock ID
	 *    </UL>
	 *     (Content to be updated) 
	 *    <TABLE border="1">
	 *      <TR><TD>stock qty</TD>			<TD> (-) (Work Status.Result qty + Work Status.Shortage Qty) </TD></TR>
	 *      <TR><TD>Allocated qty</TD>			<TD> (-) (Work Status.Result qty + Work Status.Shortage Qty) </TD></TR>
	 *      <TR><TD>Status flag</TD>		<TD>For data with Stock qty and Allocated qty equal to 0 or smaller, regard it as "Completed". Otherwise, regard it as "Picking Plan" (Only for data with no Inventory Control)</TD></TR>
	 *      <TR><TD>Last update process name</TD>	<TD>"ID0037"</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 *
	 * @param wi Picking Work the Result data to be updated
	 * @throws InvalidStatusException Announce when the setting value to update the table is inconsistent.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws NotFoundException Announce when the Update target data does not exist.
	 * @throws InvalidDefineException Announce when the designated value is abnormal (blank or containing prohibited characters).
	 */
	protected void updateStockQty(
	        WorkingInformation wi)
		throws
			InvalidStatusException,
			ReadWriteException,
			NotFoundException,
			InvalidDefineException
	{
		try
		{
			//#CM721507
			// Subtract the Work qty from the stock qty.
			StockSearchKey skey = new StockSearchKey();
			StockHandler handler = new StockHandler(conn);

			skey.setStockId(wi.getStockId());
			Stock stock = (Stock) handler.findPrimaryForUpdate(skey);

			//#CM721508
			// Subtract the Result qty and the Shortage Qty from the stock qty to get "Stock qty after Picking".
			//#CM721509
			// Include the Shortage Qty in the decrease value for Picking.
			int stockqty = stock.getStockQty() - (wi.getResultQty() + wi.getShortageCnt());

			//#CM721510
			// For data with Stock package "Available":
			if (WithStockManagement)
			{
				//#CM721511
				// Delete the inventory of which stock qty becomes 0 resulting from Picking.
				if (stockqty == 0)
				{
					skey.KeyClear();
					skey.setStockId(wi.getStockId());
					handler.drop(skey);
				}
				//#CM721512
				// Update the Stock Qty, If some stock remains after Picking.
				else
				{
					StockAlterKey akey = new StockAlterKey();
					akey.setStockId(wi.getStockId());
					akey.updateStockQty(stockqty);
					akey.updateLastUpdatePname(processName);
					handler.modify(akey);
				}

				//#CM721513
				// Update the Information of Location where Picking work has been done.(2005/06/14 Add By:T.T)
				try
				{
					LocateOperator lOperator = new LocateOperator(conn);
					lOperator.modifyLocateStatus(wi.getLocationNo(), processName);
				}
				catch (ScheduleException e)
				{
					String errString = "[Table:DmLocate" + " LOCATION_NO = " + wi.getLocationNo() + "]";
					//#CM721514
					// 6026020=Multiple records are found in a search by the unique key. {0}
					RftLogMessage.print(6026020, LogMessage.F_ERROR, CLASS_NAME, errString);
					//#CM721515
					// Change to an appropreate Exception.
					throw new InvalidStatusException();
				}
			}
			//#CM721516
			// For data with Stock package "Not Available":
			else
			{
				StockAlterKey akey = new StockAlterKey();
				akey.setStockId(wi.getStockId());
				akey.updateStockQty(stockqty);
				if (stockqty == 0)
				{
					//#CM721517
					// Stock status(9:Completed)Update
					akey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
				}
				akey.updateLastUpdatePname(processName);
				handler.modify(akey);
			}

		}
		catch (NoPrimaryException e)
		{
			String errString = "[Table:DmStock" +
								" STOCK_ID = " + wi.getStockId() +"]";
			//#CM721518
			// 6026020=Multiple records are found in a search by the unique key. {0}
			RftLogMessage.print(6026020, LogMessage.F_ERROR, CLASS_NAME, errString);
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM721519
	/**
	 * Lock the target data to update, and return the list of Picking Plan unique key of the data.<BR>
	 * Target the following tables for update: Work Status, Picking Plan Info, and
	 * inventory information (with Inventory Control "ON" and no cancel)
	 * <DIR>
	 *     (search conditions) 
	 *    <UL>
	 *     <LI>Aggregation Work No.</LI>
	 *     <LI>Work division = Picking</LI>
	 *    </UL>
	 * </DIR>
	 *
	 * @param	workingInformation	Aggregation Work No.
	 * @param	rftNo			RFT No.
	 * @param	workerCode		Worker Code
	 * @param	isCancel		Flag to determine the Commit Process to be cancelled or not
	 * 							 (Disable to lock any inventory for cancel) 
	 * @return					Array of Picking Plan unique key corresponding to the condition
	 * @throws UpdateByOtherTerminalException Announce when failing to updatethe data, which was updated via other terminal.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws LockTimeOutException Announce when database lock is not cancelled for the specified period.
	 */
	protected String[] lockUpdateData(
		WorkingInformation[] workingInformation,
		String rftNo,
		String workerCode,
		boolean isCancel) throws UpdateByOtherTerminalException, ReadWriteException, LockTimeOutException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationHandler handler = new WorkingInformationHandler(conn);

		String[] collectJobNoList = new String[workingInformation.length];
		for (int i = 0; i < workingInformation.length; i ++)
		{
		    collectJobNoList[i] = workingInformation[i].getCollectJobNo();
		}

		skey.setCollectJobNo(collectJobNoList);
		skey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);

		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION,"!=");
		skey.setCollectJobNoOrder(1, true);
		skey.setJobNoOrder(2, true);

		//#CM721520
		// Lock the WorkingInformation.
		WorkingInformation[] wi = null;
		try
		{
			wi = (WorkingInformation[])handler.findForUpdate(skey, WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM721521
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNWORKINFO");
			throw e;
		}


		//#CM721522
		// Check whether other terminal updated the data.
		if (wi.length <= 0)
		{
	        throw new UpdateByOtherTerminalException();
		}

		if (! isMaintenanceCancel)
		{
			for (int i = 0; i < wi.length; i ++)
			{
			    //#CM721523
			    // For data updated to "Processing" via any other terminal.
			    if (! rftNo.equals(wi[i].getTerminalNo()))
			    {
			        throw new UpdateByOtherTerminalException();
			    }

			    //#CM721524
			    // If the worker updated to "Processing" is different:
                if (isMaintenanceCancel = false)
                {
                    if (! workerCode.equals(wi[i].getWorkerCode()))
                    {
                        throw new UpdateByOtherTerminalException();
                    }
                }

			    //#CM721525
			    // For data with status other than "Processing":
			    if (! wi[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
			    {
			        throw new UpdateByOtherTerminalException();
			    }

				//#CM721526
				// If the last Update Process name does not correspond the process for requesting data:
				Vector tempVec = new Vector();
				for (int j = 0; j < startProcessName.length; j++)
				{
					tempVec.addElement(startProcessName[j]);
				}
				if (!tempVec.contains(wi[i].getLastUpdatePname()))
				{
					throw new UpdateByOtherTerminalException();
				}
			}
		}

		//#CM721527
		// Generate a list of the Picking Plan unique key.
		String[] planUkeyList = getPlanUkeyList(collectJobNoList, rftNo, workerCode);

		//#CM721528
		// Lock the Picking Plan Info.
		RetrievalPlanHandler rHandler = new RetrievalPlanHandler(conn);
		RetrievalPlanSearchKey rskey = new RetrievalPlanSearchKey();

		rskey.setRetrievalPlanUkey(planUkeyList);
		rskey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
		try
		{
			rHandler.findForUpdate(rskey, WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM721529
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNRETRIEVALPLAN");
			throw e;
		}

		//#CM721530
		// Lock the inventory information to be updated.
		if (WithStockManagement && ! isCancel)
		{
		    //#CM721531
		    // Add a process to lock the inventory with Inventory Control, except for process for cancel.
		    lockStockData(wi);
		}

		return planUkeyList;
	}

	//#CM721532
	/**
	 * Lock the target data to update, and return the list of Picking Plan unique key of the data.<BR>
	 * Lock all the works of the corresponding Order No. to number the Partial Order No.
	 * Target the following tables for update: Work Status, Picking Plan Info, and
	 * inventory information (with Inventory Control "ON" and no cancel)
	 * <DIR>
	 *     (search conditions) 
	 *    <UL>
	 *     <LI>Consignor Code</LI>
	 *     <LI>Planned Picking Date</LI>
	 *     <LI>Order No.</LI>
	 *     <LI>Work division = Picking</LI>
	 *    </UL>
	 * </DIR>
	 *
	 * @param	workingInformation	Order No.
	 * @param	rftNo			RFT No.
	 * @param	workerCode		Worker Code
	 * @param	isCancel		Flag to determine the Commit Process to be cancelled or not
	 * 							 (Disable to lock any inventory for cancel) 
	 * @return					Array of Picking Plan unique key corresponding to the condition
	 * @throws UpdateByOtherTerminalException Announce when failing to updatethe data, which was updated via other terminal.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws LockTimeOutException Announce when database lock is not cancelled for the specified period.
	 */
	protected String[] lockUpdateDataForOrderRetrieval(
		WorkingInformation[] workingInformation,
		String rftNo,
		String workerCode,
		boolean isCancel) throws UpdateByOtherTerminalException, ReadWriteException, LockTimeOutException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationHandler handler = new WorkingInformationHandler(conn);

		Hashtable ht = new Hashtable();
		for (int i = 0; i < workingInformation.length; i ++)
		{
		    ht.put(workingInformation[i].getOrderNo(), workingInformation[i].getOrderNo());
		}
		String[] orderNoList = new String[ht.size()];
	    int k = 0;
		for (Enumeration e = ht.elements(); e.hasMoreElements();)
		{
		    orderNoList[k] = (String) e.nextElement();
		    k ++;
		}

		skey.setConsignorCode(workingInformation[0].getConsignorCode());
		skey.setPlanDate(workingInformation[0].getPlanDate());
		skey.setOrderNo(orderNoList);
		skey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);

		//#CM721533
		// Lock the WorkingInformation.
		WorkingInformation[] wi = null;
		try
		{
			wi = (WorkingInformation[])handler.findForUpdate(skey, WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM721534
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNWORKINFO");
			throw e;
		}


		String[] collectJobNoList = new String[workingInformation.length];
		for (int i = 0; i < workingInformation.length; i ++)
		{
		    collectJobNoList[i] = workingInformation[i].getCollectJobNo();
		}

		skey.KeyClear();
		skey.setCollectJobNo(collectJobNoList);
		skey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		skey.setCollectJobNoOrder(1, true);
		skey.setJobNoOrder(2, true);

		wi = (WorkingInformation[]) handler.find(skey);

		//#CM721535
		// Check whether other terminal updated the data.
		if (wi.length <= 0)
		{
	        throw new UpdateByOtherTerminalException();
		}

		for (int i = 0; i < wi.length; i ++)
		{
		    //#CM721536
		    // For data updated to "Processing" via any other terminal.
		    if (! rftNo.equals(wi[i].getTerminalNo()))
		    {
		        throw new UpdateByOtherTerminalException();
		    }

		    //#CM721537
		    // If the worker updated to "Processing" is different:
		    if (! workerCode.equals(wi[i].getWorkerCode()))
		    {
		        throw new UpdateByOtherTerminalException();
		    }

		    //#CM721538
		    // For data with status other than "Processing":
		    if (! wi[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
		    {
		        throw new UpdateByOtherTerminalException();
		    }

			//#CM721539
			// If the last Update Process name does not correspond the process for requesting data:
			Vector tempVec = new Vector();
			for (int j = 0; j < startProcessName.length; j++)
			{
				tempVec.addElement(startProcessName[j]);
			}
			if (!tempVec.contains(wi[i].getLastUpdatePname()))
			{
				throw new UpdateByOtherTerminalException();
			}
		}

		//#CM721540
		// Generate a list of the Picking Plan unique key.
		String[] planUkeyList = getPlanUkeyList(collectJobNoList, rftNo, workerCode);

		//#CM721541
		// Lock the Picking Plan Info.
		RetrievalPlanHandler rHandler = new RetrievalPlanHandler(conn);
		RetrievalPlanSearchKey rskey = new RetrievalPlanSearchKey();

		rskey.setRetrievalPlanUkey(planUkeyList);
		rskey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
		try
		{
			rHandler.findForUpdate(rskey, WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM721542
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNRETRIEVALPLAN");
			throw e;
		}

		//#CM721543
		// Lock the inventory information to be updated.
		if (WithStockManagement && ! isCancel)
		{
		    //#CM721544
		    // Add a process to lock the inventory with Inventory Control, except for process for cancel.
		    lockStockData(wi);
		}

		return planUkeyList;
	}

	//#CM721545
	/**
	 * Lock the target inventory information and Location information to update.<BR>
	 * Generate a Stock ID array from the Work Status array designated by the argument, and 
	 * lock the corresponding inventory information.
	 * <DIR>
	 *     (search conditions) 
	 *    <UL>
	 *     <LI>Stock ID</LI>
	 *    </UL>
	 * </DIR>
	 *
	 * @param	workinfo		Array of Work Status
	 * @throws LockTimeOutException Announce when database lock is not cancelled for the specified period.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void lockStockLocateData(WorkingInformation[] workinfo)
		throws ReadWriteException, LockTimeOutException
	{
		String[] stockIdList = new String[workinfo.length];
		String[] LocationNoList = null;
		Vector vec2 = new Vector();
		String[] AreaNoList = null;
		Vector vec3 = new Vector();

		//#CM721546
		// Obtain the relocation rack area No. from WmsParam.
		String w_AreaNo = WmsParam.IDM_AREA_NO;

		LocateHandler locateHandler = new LocateHandler(conn);
		LocateSearchKey locatesearchkey = new LocateSearchKey();
		for (int i = 0; i < workinfo.length; i ++)
		{
			stockIdList[i] = workinfo[i].getStockId();
			//#CM721547
			// Search Location Info
			locatesearchkey.KeyClear();
			locatesearchkey.setAreaNo(w_AreaNo);
			locatesearchkey.setLocationNo(workinfo[i].getLocationNo());
			//#CM721548
			// Search Existing Location Info.
			try
			{
				Locate locate = (Locate)locateHandler.findPrimary(locatesearchkey);
				//#CM721549
				// Reserve it in the Vector.
				if (locate != null)
				{
					vec2.addElement(locate.getLocationNo());
					vec3.addElement(locate.getAreaNo());
				}
			}
			catch (NoPrimaryException e)
			{
				//#CM721550
				// 6006002=Database error occurred.{0}
				RftLogMessage.print(6006002, LogMessage.F_WARN, CLASS_NAME, "DMLOCATE");
				throw new ReadWriteException();
			}
		}
		StockSearchKey skey = new StockSearchKey();
		skey.setStockId(stockIdList);

		StockHandler shandler = new StockHandler(conn);
		try
		{
			shandler.findForUpdate(skey, WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM721551
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DMSTOCK");
			throw e;
		}

		//#CM721552
		// If any existing location info resides,
		if (vec2.size() > 0 )
		{
			LocationNoList = new String[vec2.size()];
			vec2.copyInto(LocationNoList);
			AreaNoList = new String[vec3.size()];
			vec3.copyInto(AreaNoList);

			locatesearchkey.KeyClear();
			locatesearchkey.setAreaNo(AreaNoList);
			locatesearchkey.setLocationNo(LocationNoList);

			try
			{
				//#CM721553
				// Lock all the location info reserved in Vector.
				locateHandler.findForUpdate(locatesearchkey, WmsParam.WMS_DB_LOCK_TIMEOUT);
			}
			catch (LockTimeOutException e)
			{
				//#CM721554
				// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
				RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DMLOCATE");
				throw e;
			}
		}
	}

	//#CM721555
	/**
	 * Based on the updated work status, update the Picking Plan Info.<BR>
	 * Disable to update any Status flag in this method.<BR>
	 *  (Use the <CODE>updateCompletionStatus()</CODE> method to update the Status flag) <BR>
	 * <DIR>
	 *     (search conditions) 
	 *    <UL>
	 *     <LI>Picking Plan unique key = Work Status.Picking Plan unique key  (One Shipping Plan Info corresponds the work status) 
	 *     <LI>Status flag != "Deleted"
	 *    </UL>
	 *     (Content to be updated) 
	 *    <TABLE>
	 *      <TR><TD>Work Result qty</TD>		<TD>(+) ID0037.Result qty</TD></TR>
	 *      <TR><TD>Work shortage qty</TD>		<TD>(+) ID0037.Shortage Qty</TD></TR>
	 *      <TR><TD>Last update process name</TD>	<TD>"ID0037" or "ID0037"</TD></TR>
	 *    </TABLE>
	 *   Sum up the Result qty and the Shortage Qty of the Work Status respectively corresponding to the Result qty and the Shortage Qty.<BR>
	 *   Disable to update Status flag here.<BR>
	 * </DIR>
	 *
	 * @param wi	Picking Work the Result data to be updated
	 * @throws InvalidStatusException Announce when the setting value to update the table is inconsistent.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws InvalidDefineException Announce when the designated value is abnormal (blank or containing prohibited characters).
	 */
	public void updatePlanInformation(
	        WorkingInformation wi)
		throws
			InvalidStatusException,
			ReadWriteException,
			NotFoundException,
			InvalidDefineException
	{
		try
		{
			//#CM721556
			// Search for the corresponding picking plan info.
			RetrievalPlanSearchKey skey = new RetrievalPlanSearchKey();
			skey.setRetrievalPlanUkey(wi.getPlanUkey());
			skey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
			RetrievalPlan plan = null;
			RetrievalPlanHandler handler = new RetrievalPlanHandler(conn);
			plan = (RetrievalPlan) handler.findPrimaryForUpdate(skey);

			//#CM721557
			// Update the searched Picking Plan Info.
			RetrievalPlanAlterKey akey = new RetrievalPlanAlterKey();
			akey.setRetrievalPlanUkey(wi.getPlanUkey());
			akey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
			akey.updateResultQty(plan.getResultQty() + wi.getResultQty());
			akey.updateShortageCnt(plan.getShortageCnt() + wi.getShortageCnt());
			akey.updateLastUpdatePname(processName);
			handler.modify(akey);
		}
		catch (NoPrimaryException e)
		{
			String errString = "[Table:DnRetrievalPlan" +
								" RETRIEVAL_PLAN_UKEY = " + wi.getPlanUkey() +"]";
			//#CM721558
			// 6026020=Multiple records are found in a search by the unique key. {0}
			RftLogMessage.print(6026020, LogMessage.F_ERROR, CLASS_NAME, errString);
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM721559
	/**
	 * Obtain the Partial Order No. that will be completed next time, from the work data with the designated Order No.<BR>
	 * If there is no work with status "Completed", return "00".<BR>
	 * Return the value of "the maximum Order Split No. of the "Completed" work data" + 1.<BR>
	 * If the maximum Order Split No. of the "Completed" work data is "99", return "99" as it is.
	 *
	 * @param consignorCode Consignor Code
	 * @param planDate Planned Work Date
	 * @param orderNo	Order No.
	 * @return			Order Split No.
	 * @throws ReadWriteException	Announce in the event of database error.
	 */
	protected int getNextOrderSeqNo(String consignorCode, String planDate, String orderNo) throws ReadWriteException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
		skey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		skey.setConsignorCode(consignorCode);
		skey.setPlanDate(planDate);
		skey.setOrderNo(orderNo);

		WorkingInformationHandler wHandler =
			new WorkingInformationHandler(conn);
		int count = wHandler.count(skey);
		if (count == 0)
		{
			//#CM721560
			// If there is no "Completed" data, return "00".
			return 0;
		}

		//#CM721561
		// Obtain the maximum value of "Completed" data if exists.
		skey.setOrderSeqNoCollect("MAX");

		WorkingInformation[] workinfo = (WorkingInformation[]) wHandler.find(skey);
		if (workinfo != null && workinfo.length > 0)
		{
			int orderSeqNo = workinfo[0].getOrderSeqNo();
			if (orderSeqNo < 99)
			{
				return orderSeqNo + 1;
			}
			else
			{
				//#CM721562
				// If the Split Order No. is 99, use the value 99 as it is.
				return 99;
			}
		}

		//#CM721563
		// Use 0 (zero) if no "Completed" data found (Using MAX must not lead to such case)
		return 0;
	}

    //#CM721564
    /**
     * Lock the Picking Plan Info with status other than Deleted that has Picking Plan unique key designated by argument.
     *
     * @param	planUKeyList [] 	Array of Picking Plan unique key
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws LockTimeOutException Announce when database lock is not cancelled for the specified period.
     */
    protected void lockPlanInformation(String[] planUKeyList) throws ReadWriteException, LockTimeOutException
    {
		//#CM721565
		// Lock the Picking Plan Info.
		RetrievalPlanHandler rHandler = new RetrievalPlanHandler(conn);
		RetrievalPlanSearchKey rskey = new RetrievalPlanSearchKey();

		rskey.setRetrievalPlanUkey(planUKeyList);
		rskey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
		rHandler.findForUpdate(rskey, WmsParam.WMS_DB_LOCK_TIMEOUT);
    }

	//#CM721566
	/**
	 * Generate a Worker Result.
	 * <DIR>
	 *     (search conditions) 
	 *    <UL>
	 *     <LI>Work date = WMSWork date</LI>
	 *     <LI>Worker Code</LI>
	 *     <LI>RFTNo</LI>
	 *     <LI>Work division</LI>
	 *    </UL>
	 *     (Content to be updated) 
	 *    <TABLE border="1">
	 *      <TR><TD>Work Count</TD>		<TD>(+) Data count</TD></TR>
	 *      <TR><TD>Total of Work qty</TD>			<TD>(+) Result qty</TD></TR>
	 *      <TR><TD>Work End Date</TD>	<TD>Date/time on system</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * If there is no corresponding worker result, create it and update again.
	 *
	 * @param	workerCode			Worker Code
	 * @param	rftNo				Terminal No.
	 * @param	resultData			Array of Work Result info (Work Status Entity)
	 * @throws NotFoundException Announce when the Update target data does not exist.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void updateWorkerResult(
	        String workerCode,
	        String rftNo,
	        jp.co.daifuku.wms.base.rft.WorkingInformation[] resultData) throws NotFoundException, ReadWriteException
	{
		//#CM721567
		// Process to set the value excluded the Not-Worked data removed for the resultData.
		Vector vec = new Vector();
		for(int i = 0; i < resultData.length; i++)
		{
			//#CM721568
			// Exclude Work Status with "Committed" and "Not Worked".
			if(resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_COMPLETION)
					|| resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_SHORTAGE))
			{
				vec.addElement(resultData[i]);
			}
		}
		jp.co.daifuku.wms.base.rft.WorkingInformation[] workinfo =
			new jp.co.daifuku.wms.base.rft.WorkingInformation[vec.size()];
		vec.copyInto(workinfo);

		super.updateWorkerResult(workerCode, rftNo, workinfo, false);
	}
}
