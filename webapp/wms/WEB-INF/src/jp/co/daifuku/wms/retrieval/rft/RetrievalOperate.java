// $Id: RetrievalOperate.java,v 1.3 2007/02/07 04:19:44 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

//#CM721258
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;

//#CM721259
/**
 * Designer : Y.Taki <BR>
 * Maker :   <BR>
 * <BR>
 * Define the common function to be invoked from the "Complete Picking"process from RFT.<BR>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:44 $
 * @author  $Author: suresh $
 */
public class RetrievalOperate
{
	//#CM721260
	/**
	 * A field that represents a class name.
	 */
	private static final String CLASS_NAME = "RetrievalOperate";

	//#CM721261
	/**
	 * Process name(for Name of Add Process and Name of Last update process)
	 */
	protected String processName = "";

	//#CM721262
	/**
	 * Name of process for starting work (for Name of Add Process and Name of the Last update process)
	 */
	protected String startProcessName = null;

	//#CM721263
	/**
	 * Inventory Control ON/OFF flag
	 */
	boolean WithStockManagement = true;

	//#CM721264
	/**
	 * Connection with database
	 */
	protected Connection conn = null;

	//#CM721265
	// Constructors --------------------------------------------------
	//#CM721266
	/**
	 * Generate an instance.<BR>
	 * Generating an object requires to initialize it using initialize() method before using this object.
	 * 
	 */
	public RetrievalOperate()
	{
		super();
	}

	//#CM721267
	// Public methods ------------------------------------------------
	//#CM721268
	/**
	 * Initialize an instance.<BR>
	 * Set <code>Connection</code> for database connection.<BR>
	 * Set the Inventory Control Available/Unavailable flag<BR>
	 * @param c Connection for database connection 
	 * @throws ReadWriteException Announce when error occurs on the connection to the System definition database.
	 */
	public void initialize(Connection c) throws ReadWriteException
	{
		conn = c;

		WithStockManagement = SystemParameter.withStockManagement();
	}

	//#CM721269
	/**
	 * Set the Name of Process for update.
	 * 
	 * @param name		Name of Process for updating
	 */
	public void setProcessName(String name)
	{
		processName = name;
	}

	//#CM721270
	/**
	 * Set the Name of Process to start work.
	 * 
	 * @param name		Name of process for starting work (Array)
	 */
	public void setStartProcessName(String name)
	{
		startProcessName = name;
	}

	//#CM721271
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
	 * @param	rftNo			RFT No.
	 * @param	workerCode		Worker Code
	 * @throws UpdateByOtherTerminalException
	 * @throws LockTimeOutException
	 * @throws InvalidStatusException
	 * @throws ReadWriteException
	 * @throws NotFoundException
	 * @throws InvalidDefineException
	*/
	protected void completeRetrieval(
		WorkingInformation[] resultData,
		String rftNo,
		String workerCode,
		String completionFlag,
		int workTime,
		int missScanCnt)
		throws
			UpdateByOtherTerminalException,
			LockTimeOutException,
			InvalidStatusException,
			ReadWriteException,
			NotFoundException,
			InvalidDefineException
	{
		//#CM721272
		// Lock the target data to update.
		String[] planUkeyList = lockUpdateData(resultData, rftNo, workerCode, false);

		//#CM721273
		// Generate a Work Status and the related Plan Info, inventory information, and Sending Result Information and update the data.
		updateWorkingInformation(rftNo, workerCode, resultData);

		//#CM721274
		// Update the status of the Picking Plan Info.
		updateCompletionStatus(planUkeyList);

		//#CM721275
		// Generate a Worker Result.
		updateWorkerResult(workerCode, rftNo, null, completionFlag, resultData, workTime, missScanCnt);
	}

	//#CM721276
	/**
	 * Picking result data  [ Commit Process] <BR>
	 * Execute the following process for submitting the Work Status.<BR>
	 * 
	 * <OL>
	 *  <LI>Lock the target data to update.
	 * ({@link #lockUpdateDataForOrderRetrieval(WorkingInformation [] ,String,String,boolean) lockUpdateData()})</LI>
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
	 * @param	rftNo			RFT No.
	 * @param	workerCode		Worker Code
	 * @param  orderNo [] 		Array of Order No.
	 * @param	workTime		Work Time
	 * @param	missScanCnt		Count of mis-scanning
	 * @throws UpdateByOtherTerminalException
	 * @throws LockTimeOutException
	 * @throws InvalidStatusException
	 * @throws ReadWriteException
	 * @throws NotFoundException
	 * @throws InvalidDefineException
	*/
	protected void completeRetrievalForOrderRetrieval(
		WorkingInformation[] resultData,
		String rftNo,
		String workerCode,
		String completionFlag,
		int workTime,
		int missScanCnt)
		throws
			UpdateByOtherTerminalException,
			LockTimeOutException,
			InvalidStatusException,
			ReadWriteException,
			NotFoundException,
			InvalidDefineException
	{
		//#CM721277
		// Lock the target data to update.
		String[] planUkeyList = lockUpdateDataForOrderRetrieval(resultData, rftNo, workerCode, false);

		//#CM721278
		// Generate a Work Status and the related Plan Info, inventory information, and Sending Result Information and update the data.
		updateWorkingInformationForInPart(rftNo, workerCode, resultData);

		//#CM721279
		// Update the status of the Picking Plan Info.
		updateCompletionStatus(planUkeyList);

		//#CM721280
		// Process to set the value excluded the Not-Worked data removed for the resultData.
		Vector vec = new Vector();
		String[] decisionOrder = { "", "", "", "" };
		int j = 0;
		for (int i = 0; i < resultData.length; i++)
		{
			//#CM721281
			// Exclude Work Status with "Committed" and "Not Worked".
			if (resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_COMPLETION)
				|| resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_SHORTAGE))
			{
				vec.addElement(resultData[i]);
			}
			else if (resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_DECISION_PROSSING))
			{
				//#CM721282
				// Skip the counted Order No.
				if (resultData[i].getOrderNo().equals(decisionOrder[0])
					|| resultData[i].getOrderNo().equals(decisionOrder[1])
					|| resultData[i].getOrderNo().equals(decisionOrder[2])
					|| resultData[i].getOrderNo().equals(decisionOrder[3]))
				{
					continue;
				}

				decisionOrder[j] = resultData[i].getOrderNo();
				j++;
			}
		}
		WorkingInformation[] tempData = new WorkingInformation[vec.size()];
		vec.copyInto(tempData);

		//#CM721283
		// Generate a Worker Result.
		updateWorkerResult(workerCode, rftNo, decisionOrder, completionFlag, tempData, workTime, missScanCnt);
	}

	//#CM721284
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
	 * @param	rftNo			RFT No.
	 * @param	workerCode		Worker Code
	 * @throws UpdateByOtherTerminalException
	 * @throws LockTimeOutException
	 * @throws InvalidStatusException
	 * @throws NotFoundException
	 * @throws InvalidDefineException
	 * @throws ReadWriteException
	 * @throws IOException
	 * @throws DataExistsException 
	 * @throws IllegalAccessException 
	 */
	protected void changeBox(
		String orderNo,
		String completionFlag,
		WorkingInformation[] resultData,
		String rftNo,
		String workerCode,
		String resultFileName,
		int workTime,
		int missScanCnt)
		throws
			UpdateByOtherTerminalException,
			LockTimeOutException,
			InvalidStatusException,
			NotFoundException,
			InvalidDefineException,
			ReadWriteException,
			IOException,
			DataExistsException,
			IllegalAccessException
	{
		//#CM721285
		// Lock the target data to update.
		String[] planUkeyList = lockUpdateDataForOrderRetrieval(resultData, rftNo, workerCode, false);

		//#CM721286
		// Generate a Work Status and the related Plan Info, inventory information, and Sending Result Information and update the data.
		updateWorkingInformationByChangedBox(orderNo, rftNo, workerCode, resultData, resultFileName);

		//#CM721287
		// Update the status of the Picking Plan Info.
		updateCompletionStatus(planUkeyList);

		//#CM721288
		// Process to set the value excluded the Not-Worked data removed for the resultData.
		Vector vec = new Vector();
		String[] decisionOrder = { "", "", "", "" };

		for (int i = 0; i < resultData.length; i++)
		{
			//#CM721289
			// Exclude the Work Status with "Not Worked".
			if (resultData[i].getOrderNo().equals(orderNo)
				&& (resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_COMPLETION)
					|| resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_SHORTAGE)
					|| resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_BOX_CHANGE)))
			{
				vec.addElement(resultData[i]);
			}
			else if (
				resultData[i].getOrderNo().equals(orderNo)
					&& (!resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_DECISION_PROSSING)))
			{
				decisionOrder[0] = orderNo;
			}

		}
		WorkingInformation[] tempData = new WorkingInformation[vec.size()];
		vec.copyInto(tempData);

		//#CM721290
		// Generate a Worker Result.
		updateWorkerResult(workerCode, rftNo, decisionOrder, completionFlag, tempData, workTime, missScanCnt);
	}

	//#CM721291
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
	 *     <LI>Planned date</LI>
	 *     <LI>Consignor Code</LI>
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
	 * @throws NotFoundException   
	 * @throws InvalidDefineException
	 * @throws UpdateByOtherTerminalException
	 * @throws ReadWriteException
	 * @throws LockTimeOutException
	 * @throws InvalidStatusException
	*/
	public void cancelRetrieval(WorkingInformation[] resultData, String workerCode, String rftNo)
		throws
			NotFoundException,
			InvalidDefineException,
			UpdateByOtherTerminalException,
			ReadWriteException,
			LockTimeOutException
	{
		//#CM721292
		// Lock the target data to update.
		String[] planUkeyList = lockUpdateDataForOrderRetrieval(resultData, rftNo, workerCode, true);

		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationAlterKey akey = new WorkingInformationAlterKey();

		for (int i = 0; i < resultData.length; i++)
		{
			//#CM721293
			// Ignore works with "Committed" only.
			if (resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_DECISION_PROSSING))
			{
				continue;
			}
			skey.KeyClear();
			skey.setCollectJobNo(resultData[i].getCollectJobNo());
			skey.setTerminalNo(rftNo);
			skey.setWorkerCode(workerCode);
			skey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			skey.setLastUpdatePname(startProcessName);

			WorkingInformationHandler handler = new WorkingInformationHandler(conn);
			WorkingInformation[] wi = (WorkingInformation[]) handler.find(skey);

			for (int j = 0; j < wi.length; j++)
			{
				akey.KeyClear();
				akey.setJobNo(wi[j].getJobNo());

				//#CM721294
				// Restore the Aggregation Work No. to its former value (Restore it to Work No.).
				akey.updateCollectJobNo(wi[j].getJobNo());
				akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
				akey.updateWorkerCode("");
				akey.updateWorkerName("");
				akey.updateTerminalNo("");
				akey.updateLastUpdatePname(processName);
				handler.modify(akey);
			}
		}
		//#CM721295
		// Restore the status of the Shipping Plan Info to its former state based on the status of the Work Status.
		updateCompletionStatus(planUkeyList);
	}

	//#CM721296
	// Package methods -----------------------------------------------

	//#CM721297
	// Protected methods ---------------------------------------------
	//#CM721298
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
	 *     <LI>Planned date</LI>
	 *     <LI>Consignor Code</LI>
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
	 *      <TR><TD>Work shortage qty</TD>		<TD>If the Result qty in the electronic statement does not satisfy the Workable qty, set the shortage qty.</TD></TR>
	 *      <TR><TD>Result Location No.</TD>		<TD>Location No. in the electronic statement</TD></TR>
	 *      <TR><TD>result expiry date</TD>	<TD>Expiry Date in the electronic statement</TD></TR>
	 *      <TR><TD>Last update process name</TD>	<TD>"ID0331" or "ID0341"</TD></TR>
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
	 * @throws InvalidStatusException
	 * @throws ReadWriteException
	 * @throws NotFoundException
	 * @throws InvalidDefineException
	 */
	protected void updateWorkingInformation(String rftNo, String workerCode, WorkingInformation[] resultData)
		throws InvalidStatusException, ReadWriteException, NotFoundException, InvalidDefineException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();

		WorkingInformationHandler handler = new WorkingInformationHandler(conn);

		for (int i = 0; i < resultData.length; i++)
		{
			//#CM721299
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
			//#CM721300
			// Aggregation Work No.
			skey.setCollectJobNo(resultData[i].getCollectJobNo());
			WorkingInformation[] wi = (WorkingInformation[]) handler.find(skey);

			if (wi.length <= 0)
			{
				String errData =
					"[ConsignorCode:"
						+ resultData[i].getConsignorCode()
						+ " PlanDate:"
						+ resultData[i].getPlanDate()
						+ " CollectJobNo:"
						+ resultData[i].getCollectJobNo()
						+ " RftNo:"
						+ resultData[i].getTerminalNo()
						+ " WorkerCode:"
						+ resultData[i].getWorkerCode()
						+ "]";
				//#CM721301
				// 6026016=No data you try to update is found. {0}
				RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);
				throw new NotFoundException();
			}

			//#CM721302
			// Maintain the Result qty of Picking Result info.
			int workQty = resultData[i].getResultQty();

			//#CM721303
			// Update data one by one allotting the Result qty.
			for (int j = 0; j < wi.length; j++)
			{
				WorkingInformationAlterKey akey = new WorkingInformationAlterKey();
				akey.setJobNo(wi[j].getJobNo());
				akey.setConsignorCode(wi[j].getConsignorCode());
				akey.setPlanDate(wi[j].getPlanDate());
				akey.setTerminalNo(rftNo);
				akey.setWorkerCode(workerCode);

				int resultQty = workQty;
				//#CM721304
				// Set the value to be updated.
				//#CM721305
				// For the next data, if any, with Result qty of the Picking Result info larger than the Workable qty:
				if (j < wi.length - 1 && resultQty > wi[j].getPlanEnableQty())
				{
					//#CM721306
					// Set the Workable qty for the Result qty.
					resultQty = wi[j].getPlanEnableQty();
				}
				workQty -= resultQty;

				akey.updateCollectJobNo(wi[j].getJobNo());
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

				//#CM721307
				// Update the DB.
				handler.modify(akey);

				//#CM721308
				// Update the Qty in the Shipping Plan Info.
				updateRetrievalPlan(wi[j]);
				//#CM721309
				// Update the Qty in the inventory information.
				updateStockQty(wi[j]);
				//#CM721310
				// Generate a sending Result Info.
				createResultData(wi[j]);
			}

			//#CM721311
			// Overwrite the designated work type with the work type of the actual data.
			//#CM721312
			//  (Required to request the next Item Picking data) 
			resultData[i].setWorkFormFlag(wi[0].getWorkFormFlag());
		}
	}

	//#CM721313
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
	 *     <LI>Planned date</LI>
	 *     <LI>Consignor Code</LI>
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
	 *      <TR><TD>Last update process name</TD>	<TD>"ID0331"</TD></TR>
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
	 *      <TR><TD>Name of Add Process</TD>		<TD>"ID0331"</TD></TR>
	 *      <TR><TD>Last update process name</TD>	<TD>"ID0331"</TD></TR>
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
	 * @throws InvalidStatusException
	 * @throws ReadWriteException
	 * @throws NotFoundException
	 * @throws InvalidDefineException
	 */
	protected void updateWorkingInformationForInPart(String rftNo, String workerCode, WorkingInformation[] resultData)
		throws InvalidStatusException, ReadWriteException, NotFoundException, InvalidDefineException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationHandler handler = new WorkingInformationHandler(conn);
		Hashtable orderSeqNo = new Hashtable();
		for (int i = 0; i < resultData.length; i++)
		{
			//#CM721314
			// Ignore the data of box-change completed (Committed : 5)
			if (resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_DECISION_PROSSING))
			{
				continue;
			}
			//#CM721315
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
			//#CM721316
			// Aggregation Work No.
			skey.setCollectJobNo(resultData[i].getCollectJobNo());
			WorkingInformation[] wi = (WorkingInformation[]) handler.find(skey);
			WorkingInformationAlterKey akey = new WorkingInformationAlterKey();

			if (wi.length <= 0)
			{
				String errData =
					"[ConsignorCode:"
						+ resultData[i].getConsignorCode()
						+ " PlanDate:"
						+ resultData[i].getPlanDate()
						+ " CollectJobNo:"
						+ resultData[i].getCollectJobNo()
						+ " RftNo:"
						+ resultData[i].getTerminalNo()
						+ " WorkerCode:"
						+ resultData[i].getWorkerCode()
						+ "]";
				//#CM721317
				// 6026016=No data you try to update is found. {0}
				RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);
				throw new NotFoundException();
			}

			//#CM721318
			// Maintain the Result qty of Picking Result info.
			int workQty = resultData[i].getResultQty();

			//#CM721319
			// Update data one by one allotting the Result qty.
			for (int j = 0; j < wi.length; j++)
			{
				//#CM721320
				// Restore the status of the data not-worked to "Not Worked".
				if (resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_UNWORK))
				{
					akey.KeyClear();
					akey.setJobNo(wi[j].getJobNo());

					//#CM721321
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
					//#CM721322
					// Update the Result of "Work Completed" data.
					akey.KeyClear();
					akey.setJobNo(wi[j].getJobNo());
					akey.setConsignorCode(wi[j].getConsignorCode());
					akey.setPlanDate(wi[j].getPlanDate());
					akey.setTerminalNo(rftNo);
					akey.setWorkerCode(workerCode);

					int resultQty = workQty;
					//#CM721323
					// Set the value to be updated.
					//#CM721324
					// For the next data, if any, with Result qty of the Picking Result info larger than the Workable qty:
					if (j < wi.length - 1 && resultQty > wi[j].getPlanEnableQty())
					{
						//#CM721325
						// Set the Workable qty for the Result qty.
						resultQty = wi[j].getPlanEnableQty();

					}
					workQty -= resultQty;

					akey.updateCollectJobNo( wi[j].getJobNo() );
					akey.updateResultQty(resultQty);
					wi[j].setResultQty(resultQty);
					if (resultQty < wi[j].getPlanEnableQty())
					{
						//#CM721326
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

					int seqNo = 0;

					if (orderSeqNo.containsKey(wi[j].getOrderNo()))
					{
						seqNo = ((Integer) orderSeqNo.get(wi[j].getOrderNo())).intValue();
					}
					else
					{
						seqNo = getNextOrderSeqNo(wi[j].getConsignorCode(), wi[j].getPlanDate(), wi[j].getOrderNo());
						orderSeqNo.put(wi[j].getOrderNo(), new Integer(seqNo));
					}

					//#CM721327
					// Set the Order Split No.
					wi[j].setOrderSeqNo(seqNo);
					akey.updateOrderSeqNo(seqNo);

					//#CM721328
					// Update the DB.
					handler.modify(akey);
					//#CM721329
					// Update the Qty in the Shipping Plan Info.
					updateRetrievalPlan(wi[j]);
					//#CM721330
					// Update the Qty in the inventory information.
					updateStockQty(wi[j]);
					//#CM721331
					// Generate a sending Result Info.
					createResultData(wi[j]);
				}
			}
		}
	}

	//#CM721332
	/**
	 * Execute the process for Box-change to the work with the designated Order.
	 * Generate a Work Status and the related Work Status, Picking Plan Info, inventory information, and Sending Result Information and update the data.
	 * Divide the records of data with Completion division "Box-change" into "Completed" work and "Not-Worked" work ("Processing" on the Work Status table)
	 * 
	 * Disable to update the "Not Worked" data in the Work Status table. Keep the status "Processing".
	 * Delete the "Completed" Work data from the "Processing" data file.
	 * 
	 * <OL>
	 * <LI>Update the Work Status.
	 * <DIR>
	 *     (search conditions) 
	 *    <UL>
	 *     <LI>Aggregation Work No.</LI>
	 *     <LI>Status flag=2:Processing</LI>
	 *     <LI>Planned date</LI>
	 *     <LI>Consignor Code</LI>
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
	 * @param	orderNo			Box-change Order No.
	 * @param	rftNo			RFT No.
	 * @param	workerCode		Worker Code
	 * @param	resultData		Picking Work Result info
	 * 							 (Use the value of the Work Status to be updated to update the work type) 
	 * @throws InvalidStatusException
	 * @throws NotFoundException
	 * @throws InvalidDefineException
	 * @throws ReadWriteException
	 * @throws IOException
	 * @throws DataExistsException 
	 * @throws IllegalAccessException 
	 */
	protected void updateWorkingInformationByChangedBox(
		String orderNo,
		String rftNo,
		String workerCode,
		WorkingInformation[] resultData,
		String resultFileName)
		throws
			InvalidStatusException,
			NotFoundException,
			InvalidDefineException,
			ReadWriteException,
			IOException,
			DataExistsException,
			IllegalAccessException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();

		WorkingInformationHandler handler = new WorkingInformationHandler(conn);
		Hashtable orderSeqNo = new Hashtable();

		//#CM721333
		// Open the data file for writing.
		String openFileName = WmsParam.DAIDATA + resultFileName;
		RandomAccessFile out = new RandomAccessFile(openFileName, "rw");
		try
		{
			for (int i = 0; i < resultData.length; i++)
			{
				//#CM721334
				// Ignore all the data of works other than the work of the designated Order.
				if (!orderNo.trim().equals(resultData[i].getOrderNo().trim()))
				{
					continue;
				}
				//#CM721335
				// Ignore all the data with Complete flag "Not Worked".
				if (resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_UNWORK))
				{
					continue;
				}
				//#CM721336
				// Ignore works with "Committed" only.
				if (resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_DECISION_PROSSING))
				{
					continue;
				}

				//#CM721337
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
				//#CM721338
				// Aggregation Work No.
				skey.setCollectJobNo(resultData[i].getCollectJobNo());
				WorkingInformation[] wi = (WorkingInformation[]) handler.find(skey);

				if (wi.length <= 0)
				{
					String errData =
						"[ConsignorCode:"
							+ resultData[i].getConsignorCode()
							+ " PlanDate:"
							+ resultData[i].getPlanDate()
							+ " CollectJobNo:"
							+ resultData[i].getCollectJobNo()
							+ " RftNo:"
							+ resultData[i].getTerminalNo()
							+ " WorkerCode:"
							+ resultData[i].getWorkerCode()
							+ "]";
					//#CM721339
					// 6026016=No data you try to update is found. {0}
					RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);
					throw new NotFoundException();
				}

				//#CM721340
				// Maintain the Result qty of Picking Result info.
				int workQty = resultData[i].getResultQty();
				//#CM721341
				// Update data one by one allotting the Result qty.
				for (int j = 0; j < wi.length; j++)
				{
					WorkingInformationAlterKey akey = new WorkingInformationAlterKey();
					akey.setJobNo(wi[j].getJobNo());
					akey.setConsignorCode(wi[j].getConsignorCode());
					akey.setPlanDate(wi[j].getPlanDate());
					akey.setTerminalNo(rftNo);
					akey.setWorkerCode(workerCode);

					int resultQty = workQty;
					//#CM721342
					// Set the value to be updated.
					//#CM721343
					// For the next data, if any, with Result qty of the Picking Result info larger than the Workable qty:
					if (j < wi.length - 1 && resultQty > wi[j].getPlanEnableQty())
					{
						//#CM721344
						// Set the Workable qty for the Result qty.
						resultQty = wi[j].getPlanEnableQty();
					}
					workQty -= resultQty;

					akey.updateCollectJobNo( wi[j].getJobNo() );
					akey.updateResultQty(resultQty);
					wi[j].setResultQty(resultQty);
					if (resultQty < wi[j].getPlanEnableQty())
					{
						//#CM721345
						// Box-change:
						if (resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_BOX_CHANGE))
						{
							akey.updatePendingQty(wi[j].getPlanEnableQty() - resultQty);
							//#CM721346
							// Set the Pending Qty of the current Work Status Entity.
							wi[j].setPendingQty(wi[j].getPlanEnableQty() - resultQty);

							//#CM721347
							// Work Status Entity for new data if splitted
							WorkingInformation newWorkinfo = null;

							//#CM721348
							// Set the value of later record to be divided.
							newWorkinfo = (WorkingInformation) wi[j].clone();
							newWorkinfo.setPlanQty(wi[j].getPlanEnableQty() - resultQty);
							newWorkinfo.setPlanEnableQty(wi[j].getPlanEnableQty() - resultQty);
							newWorkinfo.setResultQty(0);
							newWorkinfo.setPendingQty(0);
							newWorkinfo.setRegistPname(processName);

							//#CM721349
							// Assign a new work No.
							SequenceHandler sh = new SequenceHandler(conn);
							newWorkinfo.setJobNo(sh.nextJobNo());
							//#CM721350
							// Note)Require to inherit the value of Aggregation Work No. in the source record. Therefore, disable to set it. (Disable to use newWorkinfo.setCollectJobNo)
							
							handler.create(newWorkinfo);
						}
						else if (!resultData[i].getStatusFlag().equals(Id5330DataFile.COMPLETION_FLAG_UNWORK))
						{
							//#CM721351
							// For Shortage:
							akey.updateShortageCnt(wi[j].getPlanEnableQty() - resultQty);
							wi[j].setShortageCnt(wi[j].getPlanEnableQty() - resultQty);
						}
					}
					akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
					wi[j].setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
					akey.updateResultUseByDate(resultData[i].getResultUseByDate());
					wi[j].setResultUseByDate(resultData[i].getResultUseByDate());
					akey.updateResultLocationNo(resultData[i].getLocationNo());
					wi[j].setResultLocationNo(resultData[i].getLocationNo());
					akey.updateLastUpdatePname(processName);

					//#CM721352
					// Obtain the Order Split No.
					int seqNo;

					if (orderSeqNo.containsKey(wi[j].getOrderNo()))
					{

						seqNo = ((Integer) orderSeqNo.get(wi[j].getOrderNo())).intValue();
					}
					else
					{
						seqNo = getNextOrderSeqNo(wi[j].getConsignorCode(), wi[j].getPlanDate(), wi[j].getOrderNo());
						orderSeqNo.put(wi[j].getOrderNo(), new Integer(seqNo));
					}

					//#CM721353
					// Set the Order Split No.
					wi[j].setOrderSeqNo(seqNo);
					akey.updateOrderSeqNo(seqNo);

					//#CM721354
					// Update the DB.
					handler.modify(akey);

					//#CM721355
					// Update the Qty in the Shipping Plan Info.
					updateRetrievalPlan(wi[j]);
					//#CM721356
					// Update the Qty in the inventory information.
					updateStockQty(wi[j]);
					//#CM721357
					// Generate a sending Result Info.
					createResultData(wi[j]);
					//#CM721358
					// Update the work data.
					Id5330DataFile.updateDataFileComplete(i, out, resultData);
				}
			}
			//#CM721359
			// Update the data with status "Processing".
			Id5330DataFile dataFile = (Id5330DataFile) PackageManager.getObject("Id5330DataFile");
			dataFile.setFileName(resultFileName);
			dataFile.saveWorkingDataFile(rftNo, resultFileName, resultData.length, conn);
		}
		finally
		{
			//#CM721360
			// Close a file.
			out.close();
		}
	}

	//#CM721361
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
	 *  Update the data with no "Processing" work and with one or more "Started" work, to "Started".<BR>
	 *   Update the data with all works "Completed" and no work " Standby", to "Partially Completed". <BR>
	 * </DIR>
	 * 
	 * @param	planUkey				Picking Plan unique key list
	 * @throws ReadWriteException		Generate a database error.
	 * @throws NotFoundException
	 * @throws InvalidDefineException
	 */
	protected void updateCompletionStatus(String[] planUkey)
		throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		for (int i = 0; i < planUkey.length; i++)
		{
			WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
			skey.setPlanUkey(planUkey[i]);

			WorkingInformation[] workinfo;
			WorkingInformationHandler wHandler = new WorkingInformationHandler(conn);
			workinfo = (WorkingInformation[]) wHandler.find(skey);

			RetrievalPlanHandler sHandler = new RetrievalPlanHandler(conn);
			RetrievalPlanAlterKey akey = new RetrievalPlanAlterKey();
			akey.setRetrievalPlanUkey(planUkey[i]);
			akey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");

			boolean existCompleteData = false;
			boolean existUnstartData = false;
			boolean existStart = false;
			boolean existWorkingData = false;
			for (int j = 0; j < workinfo.length; j++)
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
				else if (status.equals(WorkingInformation.STATUS_FLAG_START))
				{
					existStart = true;
				}
				else
				{
					existWorkingData = true;
					break;
				}
			}
			if (existWorkingData || (!existCompleteData && !existUnstartData && !existStart))
			{
				//#CM721362
				// If there is one or more data with status "Processing" or if there is no data with "Completed", "Standby", or "Started", 
				//#CM721363
				// disable to update the Status flag.
			}
			else if (existStart)
			{
				akey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_START);
			}
			else if (existCompleteData)
			{
				//#CM721364
				// If some aggregation is not "Completed", regard as "Partially Completed".
				RetrievalPlanSearchKey planKey = new RetrievalPlanSearchKey();
				planKey.setRetrievalPlanUkey(planUkey[i]);
				planKey.setSchFlag(RetrievalPlan.SCH_FLAG_COMPLETION);
				if (!existUnstartData && sHandler.count(planKey) != 0)
				{
					akey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
				}
				else
				{
					akey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART);
				}
			}
			else
			{
				akey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
			}
			akey.updateLastUpdatePname(processName);
			sHandler.modify(akey);
		}
	}

	//#CM721365
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
	 *      <TR><TD>Status flag</TD>		<TD>For data with Stock qty and Allocated qty equal to 0 or smaller, regard it as "Completed". Otherwise, regard it as "Picking Plan" (Only for data with no Inventory Control)</TD></TR>
	 *      <TR><TD>Last update process name</TD>	<TD>"ID0037"</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * 
	 * @param wi Picking Work the Result data to be updated
	 * @throws InvalidStatusException
	 * @throws ReadWriteException		Generate a database error.
	 * @throws NotFoundException
	 * @throws InvalidDefineException
	 */
	protected void updateStockQty(WorkingInformation wi)
		throws InvalidStatusException, ReadWriteException, NotFoundException, InvalidDefineException
	{
		try
		{
			//#CM721366
			// Subtract the Work qty from the stock qty.
			StockSearchKey skey = new StockSearchKey();
			StockHandler handler = new StockHandler(conn);

			skey.setStockId(wi.getStockId());
			Stock stock = (Stock) handler.findPrimaryForUpdate(skey);

			//#CM721367
			// Subtract the Result qty and the Shortage Qty from the stock qty to get "Stock qty after Picking".
			//#CM721368
			// Include the Shortage Qty in the decrease value for Picking.
			int stockqty = stock.getStockQty() - (wi.getResultQty() + wi.getShortageCnt());

			//#CM721369
			// For data with Stock package "Available":
			if (WithStockManagement)
			{
				//#CM721370
				// Delete the inventory of which stock qty becomes 0 resulting from Picking.
				if (stockqty == 0)
				{
					skey.KeyClear();
					skey.setStockId(wi.getStockId());
					handler.drop(skey);
				}
				//#CM721371
				// Update the Stock Qty, If some stock remains after Picking.
				else
				{
					StockAlterKey akey = new StockAlterKey();
					akey.setStockId(wi.getStockId());
					akey.updateStockQty(stockqty);
					akey.updateLastUpdatePname(processName);
					handler.modify(akey);
				}

				//#CM721372
				// Update the Information of Location where Picking work has been done.(2005/06/14 Add By:T.T)
				try
				{
					LocateOperator lOperator = new LocateOperator(conn);
					lOperator.modifyLocateStatus(wi.getLocationNo(), processName);
				}
				catch (ScheduleException e)
				{
					String errString = "[Table:DmLocate" + " LOCATION_NO = " + wi.getLocationNo() + "]";
					//#CM721373
					// 6026020=Multiple records are found in a search by the unique key. {0}
					RftLogMessage.print(6026020, LogMessage.F_ERROR, CLASS_NAME, errString);
					//#CM721374
					// Change to an appropreate Exception.
					throw new InvalidStatusException();
				}
			}
			//#CM721375
			// For data with Stock package "Not Available":
			else
			{
				StockAlterKey akey = new StockAlterKey();
				akey.setStockId(wi.getStockId());
				akey.updateStockQty(stockqty);
				if (stockqty == 0)
				{
					//#CM721376
					// Stock status(9:Completed)Update
					akey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
				}
				akey.updateLastUpdatePname(processName);
				handler.modify(akey);
			}

		}
		catch (NoPrimaryException e)
		{
			String errString = "[Table:DmStock" + " STOCK_ID = " + wi.getStockId() + "]";
			//#CM721377
			// 6026020=Multiple records are found in a search by the unique key. {0}
			RftLogMessage.print(6026020, LogMessage.F_ERROR, CLASS_NAME, errString);
			throw new InvalidStatusException();
		}
	}

	//#CM721378
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
	 * @throws UpdateByOtherTerminalException
	 * @throws ReadWriteException		Generate a database error.
	 * @throws LockTimeOutException	Occurrence of lock time-out
	 */
	protected String[] lockUpdateData(
		WorkingInformation[] workingInformation,
		String rftNo,
		String workerCode,
		boolean isCancel)
		throws NotFoundException, UpdateByOtherTerminalException, ReadWriteException, LockTimeOutException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationHandler handler = new WorkingInformationHandler(conn);

		String[] collectJobNoList = new String[workingInformation.length];
		for (int i = 0; i < workingInformation.length; i++)
		{
			collectJobNoList[i] = workingInformation[i].getCollectJobNo();
		}

		skey.setCollectJobNo(collectJobNoList);
		skey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		skey.setCollectJobNoOrder(1, true);
		skey.setJobNoOrder(2, true);

		//#CM721379
		// Lock the WorkingInformation.
		WorkingInformation[] wi = null;
		try
		{
			wi = (WorkingInformation[]) handler.findForUpdate(skey, WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM721380
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNWORKINFO");
			throw e;
		}

		//#CM721381
		// Check whether other terminal updated the data.
		if (wi.length <= 0)
		{
			throw new NotFoundException();
		}

		for (int i = 0; i < wi.length; i++)
		{
			//#CM721382
			// For data updated to "Processing" via any other terminal.
			if (!rftNo.equals(wi[i].getTerminalNo()))
			{
				throw new UpdateByOtherTerminalException();
			}

			//#CM721383
			// If the worker updated to "Processing" is different:
			if (!workerCode.equals(wi[i].getWorkerCode()))
			{
				throw new UpdateByOtherTerminalException();
			}

			//#CM721384
			// For data with status other than "Processing":
			if (!wi[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
			{
				throw new UpdateByOtherTerminalException();
			}

			//#CM721385
			// If the last Update Process name does not correspond the process for requesting data:
			if (!wi[i].getLastUpdatePname().equals(startProcessName))
			{
				throw new UpdateByOtherTerminalException();
			}

		}

		//#CM721386
		// Generate a list of the Picking Plan unique key.
		String[] planUkeyList = getPlanUkeyList(collectJobNoList, rftNo, workerCode);

		//#CM721387
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
			//#CM721388
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNRETRIEVALPLAN");
			throw e;
		}

		//#CM721389
		// Lock the inventory information to be updated.
		if (WithStockManagement && !isCancel)
		{
			//#CM721390
			// Add a process to lock the inventory with Inventory Control, except for process for cancel.
			lockStockData(wi);
		}

		return planUkeyList;
	}

	//#CM721391
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
	 * @throws UpdateByOtherTerminalException
	 * @throws ReadWriteException		Generate a database error.
	 * @throws LockTimeOutException	Occurrence of lock time-out
	 */
	protected String[] lockUpdateDataForOrderRetrieval(
		WorkingInformation[] workingInformation,
		String rftNo,
		String workerCode,
		boolean isCancel)
		throws UpdateByOtherTerminalException, ReadWriteException, LockTimeOutException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationHandler handler = new WorkingInformationHandler(conn);

		Hashtable ht = new Hashtable();
		for (int i = 0; i < workingInformation.length; i++)
		{
			ht.put(workingInformation[i].getOrderNo(), workingInformation[i].getOrderNo());
		}
		String[] orderNoList = new String[ht.size()];
		int k = 0;
		for (Enumeration e = ht.elements(); e.hasMoreElements();)
		{
			orderNoList[k] = (String) e.nextElement();
			k++;
		}

		skey.setConsignorCode(workingInformation[0].getConsignorCode());
		skey.setPlanDate(workingInformation[0].getPlanDate());
		skey.setOrderNo(orderNoList);
		skey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);

		//#CM721392
		// Lock the WorkingInformation.
		WorkingInformation[] wi = null;
		try
		{
			wi = (WorkingInformation[]) handler.findForUpdate(skey, WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM721393
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNWORKINFO");
			throw e;
		}

		String[] collectJobNoList = new String[workingInformation.length];
		for (int i = 0; i < workingInformation.length; i++)
		{
			collectJobNoList[i] = workingInformation[i].getCollectJobNo();
		}

		skey.setCollectJobNo(collectJobNoList);
		skey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION, "!=");
		skey.setCollectJobNoOrder(1, true);
		skey.setJobNoOrder(2, true);

		wi = (WorkingInformation[]) handler.find(skey);

		//#CM721394
		// Check whether other terminal updated the data.
		if (wi.length <= 0)
		{
			throw new UpdateByOtherTerminalException();
		}

		for (int i = 0; i < wi.length; i++)
		{
			//#CM721395
			// For data updated to "Processing" via any other terminal.
			if (!rftNo.equals(wi[i].getTerminalNo()))
			{
				throw new UpdateByOtherTerminalException();
			}

			//#CM721396
			// If the worker updated to "Processing" is different:
			if (!workerCode.equals(wi[i].getWorkerCode()))
			{
				throw new UpdateByOtherTerminalException();
			}

			//#CM721397
			// For data with status other than "Processing":
			if (!wi[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
			{
				throw new UpdateByOtherTerminalException();
			}

			//#CM721398
			// If the last Update Process name does not correspond the process for requesting data:
			if (!wi[i].getLastUpdatePname().equals(startProcessName))
			{
				throw new UpdateByOtherTerminalException();
			}
		}

		//#CM721399
		// Generate a list of the Picking Plan unique key.
		String[] planUkeyList = getPlanUkeyList(collectJobNoList, rftNo, workerCode);

		//#CM721400
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
			//#CM721401
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNRETRIEVALPLAN");
			throw e;
		}

		//#CM721402
		// Lock the inventory information to be updated.
		if (WithStockManagement && !isCancel)
		{
			//#CM721403
			// Add a process to lock the inventory with Inventory Control, except for process for cancel.
			lockStockData(wi);
		}

		return planUkeyList;
	}

	//#CM721404
	/**
	 * Obtain the list of Picking Plan unique keys of the target data to update.
	 * <DIR>
	 *     (search conditions) 
	 *    <UL>
	 *     <LI>Aggregation Work No.</LI>
	 *     <LI>Worker Code</LI>
	 *     <LI>RFTNo</LI>
	 *     <LI>Work division = Picking</LI>
	 *     <LI>Status flag = Processing</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param	collectJobNoList	Aggregation Work No. list
	 * @param	rftNo				RFT No.
	 * @param	workerCode			Worker Code
	 * @return					Array of Shipping Plan unique key corresponding to the condition
	 * @throws ReadWriteException		Generate a database error.
	 */
	protected String[] getPlanUkeyList(String[] collectJobNoList, String rftNo, String workerCode)
		throws ReadWriteException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		skey.setCollectJobNo(collectJobNoList);
		skey.setTerminalNo(rftNo);
		skey.setWorkerCode(workerCode);
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		skey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		skey.setPlanUkeyCollect("DISTINCT");
		skey.setPlanUkeyOrder(1, true);
		WorkingInformationHandler wHandler = new WorkingInformationHandler(conn);
		WorkingInformation[] workinfo = (WorkingInformation[]) wHandler.find(skey);

		String[] planUkey = new String[workinfo.length];
		for (int i = 0; i < workinfo.length; i++)
		{
			planUkey[i] = workinfo[i].getPlanUkey();
		}
		return planUkey;
	}

	//#CM721405
	/**
	 * Lock the inventory information to be updated.<BR>
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
	 * @throws ReadWriteException		Generate a database error.
	 * @throws LockTimeOutException	Occurrence of lock time-out
	 */
	protected void lockStockData(WorkingInformation[] workinfo) throws ReadWriteException, LockTimeOutException
	{
		String[] stockIdList = new String[workinfo.length];
		for (int i = 0; i < workinfo.length; i++)
		{
			stockIdList[i] = workinfo[i].getStockId();
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
			//#CM721406
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DMSTOCK");
			throw e;
		}

	}

	//#CM721407
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
	 * @throws ReadWriteException		Generate a database error.
	 * @throws LockTimeOutException	Occurrence of lock time-out
	 */
	protected void lockStockLocateData(WorkingInformation[] workinfo) throws ReadWriteException, LockTimeOutException
	{
		String[] stockIdList = new String[workinfo.length];
		String[] LocationNoList = null;
		Vector vec2 = new Vector();
		String[] AreaNoList = null;
		Vector vec3 = new Vector();

		LocateHandler locateHandler = new LocateHandler(conn);
		LocateSearchKey locatesearchkey = new LocateSearchKey();
		for (int i = 0; i < workinfo.length; i++)
		{
			stockIdList[i] = workinfo[i].getStockId();
			//#CM721408
			// Search Location Info
			locatesearchkey.KeyClear();
			locatesearchkey.setAreaNo(WmsParam.IDM_AREA_NO);
			locatesearchkey.setLocationNo(workinfo[i].getLocationNo());
			//#CM721409
			// Search Existing Location Info.
			try
			{
				Locate locate = (Locate) locateHandler.findPrimary(locatesearchkey);
				//#CM721410
				// Reserve it in the Vector.
				if (locate != null)
				{
					vec2.addElement(locate.getLocationNo());
					vec3.addElement(locate.getAreaNo());
				}
			}
			catch (NoPrimaryException e)
			{
				//#CM721411
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
			//#CM721412
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DMSTOCK");
			throw e;
		}

		//#CM721413
		// If any existing location info resides,
		if (vec2.size() > 0)
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
				//#CM721414
				// Lock all the location info reserved in Vector.
				locateHandler.findForUpdate(locatesearchkey, WmsParam.WMS_DB_LOCK_TIMEOUT);
			}
			catch (LockTimeOutException e)
			{
				//#CM721415
				// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
				RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DMLOCATE");
				throw e;
			}
		}
	}

	//#CM721416
	/**
	 * Generate a Worker Result.
	 * <DIR>
	 *     (search conditions) 
	 *    <UL>
	 *     <LI>Work date = WMSWork date</LI>
	 *     <LI>Worker Code</LI>
	 *     <LI>RFTNo</LI>
	 *     <LI>Work division = Picking</LI>
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
	 * @param	workingInformation	Picking Work Result info (Array of Work Status Entity)
	 * @throws NotFoundException
	 * @throws ReadWriteException
	 */
	protected void updateWorkerResult(
		String workerCode,
		String rftNo,
		String[] decisionOrder,
		String completionFlag,
		WorkingInformation[] workingInformation,
		int workTime,
		int missScanCnt)
		throws NotFoundException, ReadWriteException
	{
		WorkerResult wr = new WorkerResult();
		BaseOperate bo = new BaseOperate(conn);

		//#CM721417
		// Calculate the Total Result qty.
		int resultQty = 0;
		int realWorkTime = 0;

		for (int i = 0; i < workingInformation.length; i++)
		{
			resultQty += workingInformation[i].getResultQty();
			realWorkTime += ((jp.co.daifuku.wms.base.rft.WorkingInformation) workingInformation[i]).getWorkTime();
		}

		wr.setWorkDate(bo.getWorkingDate());
		wr.setWorkerCode(workerCode);
		wr.setTerminalNo(rftNo);
		wr.setJobType(WorkerResult.JOB_TYPE_RETRIEVAL);
		wr.setWorkCnt(workingInformation.length);
		if (completionFlag.equals(RFTId0331.COMPLETION_FLAG_BOX_CHANGE))
		{
			wr.setOrderCnt(1);
		}
		else if (decisionOrder == null)
		{
			wr.setOrderCnt(workingInformation.length);
		}
		else
		{
			int orderCnt = 0;
			String[] orderNoGroup = { "", "", "", "" };
			for (int i = 0; i < workingInformation.length; i++)
			{
				//#CM721418
				// Skip the "Committed" Order.
				if (workingInformation[i].getOrderNo().equals(decisionOrder[0])
					|| workingInformation[i].getOrderNo().equals(decisionOrder[1])
					|| workingInformation[i].getOrderNo().equals(decisionOrder[2])
					|| workingInformation[i].getOrderNo().equals(decisionOrder[3]))
				{
					continue;
				}
				//#CM721419
				// Skip the counted Orders.
				if (workingInformation[i].getOrderNo().equals(orderNoGroup[0])
					|| workingInformation[i].getOrderNo().equals(orderNoGroup[1])
					|| workingInformation[i].getOrderNo().equals(orderNoGroup[2])
					|| workingInformation[i].getOrderNo().equals(orderNoGroup[3]))
				{
					continue;
				}
				orderNoGroup[orderCnt] = workingInformation[i].getOrderNo();
				orderCnt++;

			}
			wr.setOrderCnt(orderCnt);
		}
		wr.setWorkQty(resultQty);
		wr.setWorkTime(workTime);
		if (realWorkTime != 0)
		{
			wr.setRealWorkTime(realWorkTime);
		}
		else
		{
			wr.setRealWorkTime(workTime);
		}

		wr.setMissScanCnt(missScanCnt);

		try
		{
			bo.alterWorkerResult(wr);
		}
		catch (NotFoundException e)
		{
			String errData =
				"[RftNo:" + workerCode + " WorkerCode:" + rftNo + " JobType:" + WorkerResult.JOB_TYPE_INVENTORY + "]";
			//#CM721420
			// 6026016=No data you try to update is found. {0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				bo.createWorkerResult(wr.getJobType(), wr.getWorkerCode(), wr.getTerminalNo());
				bo.alterWorkerResult(wr);
			}
			catch (NotFoundException e1)
			{
				//#CM721421
				//6006002=Database error occurred.{0}
				RftLogMessage.print(6006002, CLASS_NAME, "DnWorkerResult");
				//#CM721422
				//Throw here the ReadWriteException with error message.
				throw new ReadWriteException("6006002");
			}
			catch (DataExistsException e1)
			{
				//#CM721423
				//6006002=Database error occurred.{0}
				RftLogMessage.print(6006002, CLASS_NAME, "DnWorkerResult");
				//#CM721424
				//Throw here the ReadWriteException with error message.
				throw new ReadWriteException("6006002");
			}
		}
	}

	//#CM721425
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
	 * @throws InvalidStatusException
	 * @throws ReadWriteException		Generate a database error.
	 * @throws NotFoundException
	 * @throws InvalidDefineException
	 */
	protected void updateRetrievalPlan(WorkingInformation wi)
		throws InvalidStatusException, ReadWriteException, NotFoundException, InvalidDefineException
	{
		try
		{
			//#CM721426
			// Search for the corresponding picking plan info.
			RetrievalPlanSearchKey skey = new RetrievalPlanSearchKey();
			skey.setRetrievalPlanUkey(wi.getPlanUkey());
			skey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
			RetrievalPlan plan = null;
			RetrievalPlanHandler handler = new RetrievalPlanHandler(conn);
			plan = (RetrievalPlan) handler.findPrimaryForUpdate(skey);

			//#CM721427
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
			String errString = "[Table:DnRetrievalPlan" + " RETRIEVAL_PLAN_UKEY = " + wi.getPlanUkey() + "]";
			//#CM721428
			// 6026020=Multiple records are found in a search by the unique key. {0}
			RftLogMessage.print(6026020, LogMessage.F_ERROR, CLASS_NAME, errString);

			throw new InvalidStatusException();
		}
	}

	//#CM721429
	/**
	 * Generate Sending Result Info.<BR>
	 * <DIR>
	 *   Obtain the Work date from the WareNavi_System and set it for Work Date.<BR>
	 *   Set the Name of Add Process.<BR>
	 *   Obtain other field items from the Work Status and set them.<BR>
	 * </DIR>
	 * 
	 * @param wi			Work Status
	 * @throws ReadWriteException		Generate a database error.
	 */
	protected void createResultData(WorkingInformation wi) throws ReadWriteException
	{
		HostSend result;
		BaseOperate baseOperate = new BaseOperate(conn);
		try
		{
			result = new HostSend(wi, baseOperate.getWorkingDate(), processName);
			HostSendHandler handler = new HostSendHandler(conn);
			handler.create(result);
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw new ReadWriteException();
		}
	}

	//#CM721430
	/**
	 * Obtain the Partial Order No. that will be completed next time, from the work data with the designated Order No.
	 * If there is no work with status "Completed", return "00".
	 * Return the value of "the maximum Order Split No. of the "Completed" work data" + 1.
	 * If the maximum Order Split No. of the "Completed" work data is "99", return "99" as it is.
	 * 
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
		//#CM721431
		//skey.setConsignorCodeCollect("Max(consignor_code)");
		WorkingInformationHandler wHandler = new WorkingInformationHandler(conn);
		int count = wHandler.count(skey);
		if (count == 0)
		{
			//#CM721432
			// If there is no "Completed" data, return "0".
			return 0;
		}

		//#CM721433
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
				//#CM721434
				// If the Split Order No. is 99, use the value 99 as it is.
				return 99;
			}
		}

		//#CM721435
		// Use 0 (zero) if no "Completed" data found (Using MAX must not lead to such case)
		return 0;
	}

}
