// $Id: Id0230Operate.java,v 1.2 2006/12/07 09:00:13 suresh Exp $
package jp.co.daifuku.wms.storage.rft;
//#CM575005
/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
import jp.co.daifuku.wms.base.rft.WorkingInformationHandler;

//#CM575006
/**
 * Designer : Y.Taki<BR>
 * Maker : Y.Taki<BR>
 * <BR>
 * Process data for storage start request<BR>
 * Fetch storage work data and change the status to "in prcess"<BR>
 * The business logic called from Id0230Process is implemented<BR>
 * <BR>
 * !Caution!<BR>
 * The WorkingInformation class and WorkingInformationHandler uses the RFT package
 * (jp.co.daifuku.wms.base.rft package)<BR>
 * (Since the super class instance can't be cast inside the sub-class, use of RFT's WorkingInformationHandler class becomes mandatory)<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:13 $
 * @author  $Author: suresh $
 */
public class Id0230Operate extends IdOperate
{
	//#CM575007
	// Class fields----------------------------------------------------
	//#CM575008
	/**
	 * Process name
	 */
	private static final String PROCESS_NAME = "ID0230";
	
	//#CM575009
	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "Id0230Operate";

	//#CM575010
	/**
	 * Resource definition (whether grouped or not)
	 */
	final String JOB_COLLECT = "STORAGE_JOBCOLLECT";
	
	//#CM575011
	/**
	 * Character string (for grouping) <BR>
	 * Other than this is not grouped
	 */
	final String STR_TRUE = "TRUE";
	
	//#CM575012
	/**
	 * Resource definition (whether grouped or not)(key used while grouping)
	 */
	final String JOB_COLLECT_KEY = "STORAGE_JOBCOLLECT_KEY";
	
	//#CM575013
	/**
	 * String when grouping key is Item code<BR>
	 * Other than this, the grouping key will be [Item code + ITF + Bundle ITF]
	 */
	final int STR_ITEMCODE = 1;
	//#CM575014
	// Class variables -----------------------------------------------
	//#CM575015
	// Constructors --------------------------------------------------
	//#CM575016
	/**
	 * Constructor
	 */
	public Id0230Operate()
	{
		super();
	}

	//#CM575017
	/**
	 * Pass DBConnection info to constructor
	 * @param conn DBConnection info
	 */
	public Id0230Operate(Connection conn)
	{
		super();
		wConn = conn;
	}
	
	//#CM575018
	// Class method --------------------------------------------------
	//#CM575019
	/**
	 * Returns the version of this class
	 * @return version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:13 $");
	}

	//#CM575020
	/**
	 * Fetch the storage start request data and change the status to "in process" if work is possible<BR>
	 * Commit is done at the caller method<BR>
	 * <BR>
	 * Fetch work possible data from work info. (getWorkableStorageData)<BR>
	 * If data fetching is not possible, fetch data from storage info to decide on the response flag. (getStorageData)<BR>
	 * <DIR>
	 *  If data fetching is not possile even from storage info, return 8: Target data does not exist with NotFoundException<BR>
	 *  If there is atleast one data with status "in process" throw NotFoundException with message 1:Work is engaged in another terminal.<BR>
	 *  If all the work is already completed  throw NotFoundException with message 2: <BR>
	 * </DIR>
	 * <BR>
	 * Fetch the storage work data to be processed(pickUpWorkData)<BR>
	 * <DIR>
	 *   Take one work data <BR>
	 *   Fetch grouping enable or disable option from parameter file and gather Job no. in case of grouping <BR>
	 *   (use the first Jon no)<BR>
	 *   Update DB upon gathering Job no.<BR>
	 * </DIR>
	 * <BR>
	 * Update Work info and Storage plan info to "in process"<BR>
	 * <BR>
	 * Collect the extracted data and return Work info<BR>
	 * <DIR>
	 *  If the Expiry date differs, set the smaller one<BR>
	 * </DIR>
	 * <BR>
	 * @param consignorCode Consignor code<BR>
	 * @param planDate Plan Date<BR>
	 * @param itemCode Item code<BR>
	 * @param casePieceFlag Case piece flag<BR>
	 * @param rftNo RFT No.<BR>
	 * @param workerCode Worker code<BR>
	 * @return	Storage plan info  (Work info entity) <BR>
	 * @throws ReadWriteException If abnormal error occurs in database connection<BR>
	 * @throws NotFoundException If data with work possibility does not exist<BR>
	 * @throws InvalidDefineException If the specified value is abnormal (empty, illegal characters)<BR>
	 * @throws LockTimeOutException If the database lock is not removed after specified time<BR>
	 * @throws OverflowException If the item count exceeds limit<BR>
	*/
	public WorkingInformation startStorage(
		String consignorCode,
		String planDate,
		String itemCode,
		String convertedJanCode,
		String casePieceFlag,
		String rftNo,
		String workerCode)
		throws ReadWriteException, NotFoundException, InvalidDefineException, LockTimeOutException, OverflowException
	{
		//#CM575021
		// Variable to store the WorkingInfo search result
		WorkingInformation[] preWorkingData = null;
		//#CM575022
		// Variable to store work data
		WorkingInformation[] workingData = null;
		//#CM575023
		// Variable to store work data after grouping
		WorkingInformation retWorkingData = null;

		try
		{
			preWorkingData = getWorkableStorageData(
					consignorCode,
					planDate,
					itemCode,
					convertedJanCode,
					casePieceFlag,
					rftNo,
					workerCode);
		}
		catch (LockTimeOutException e)
		{
			//#CM575024
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNWORKINFO");
			throw e;
		}
		if (preWorkingData == null || preWorkingData.length == 0)
		{
			//#CM575025
			// Process when there is no corresponding data
			preWorkingData = null;
			preWorkingData = getStorageData(consignorCode, planDate, itemCode, casePieceFlag);
			//#CM575026
			// If target data does not exist set the status flag to 8: Target data does not exist
			if (preWorkingData.length == 0)
			{
				NotFoundException e = new NotFoundException(RFTId5230.ANS_CODE_NULL);
				throw e;
			}
			//#CM575027
			// If "in process" or "started" data exist in the target data set status flag to 1: In process in another terminal and return
			for (int i = 0; i < preWorkingData.length; i++)
			{
				if (preWorkingData[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING)
					|| preWorkingData[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START))
				{
					NotFoundException e = new NotFoundException(RFTId5230.ANS_CODE_WORKING);
					throw e;
				}
			}
			//#CM575028
			// If "in process" or "started" data does not exist in the target data set status flag to 2: Work already completed and return
			NotFoundException e = new NotFoundException(RFTId5230.ANS_CODE_COMPLETION);
			throw e;
		}
		//#CM575029
		// Fetch worker name
		BaseOperate bo = new BaseOperate(wConn);
		String workerName = "";
		try
		{
			workerName = bo.getWorkerName(workerCode);
		}
		catch (NotFoundException e)
		{
			//#CM575030
			// Worker start with no worker code (worker master data is not available until data request)
			//#CM575031
			// 6026019=No matching worker data was found in the Worker Master Table. Worker Code: {0}
			RftLogMessage.print(6026019, LogMessage.F_ERROR, CLASS_NAME, workerCode);
			NotFoundException ex = new NotFoundException(RFTId5230.ANS_CODE_ERROR);
			throw ex;
		}
		
		//#CM575032
		// Fetch the Storage work data
		workingData = pickUpWorkingData(preWorkingData);
		
		//#CM575033
		// Update the extracted data to "in process"
		try
		{
			updateToWorking(rftNo, workerCode, workerName, workingData);
		}
		catch (NotFoundException e)
		{
			String errData =
				"[ConsignorCode:"	+ consignorCode
					+ " PlanDate:"	+ planDate
					+ " ItemCode:"	+ itemCode
					+ " RftNo:"		+ rftNo
					+ " WorkerCode:"+ workerCode	+ "]";
			//#CM575034
			// 6026016=No data you try to update is found. {0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);
			//#CM575035
			// Return the Status flag as "in maintenance"
			NotFoundException ex = new NotFoundException(RFTId5230.ANS_CODE_MAINTENANCE);
			throw ex;
		}
		
		//#CM575036
		// Collect the extracted data
		retWorkingData = collectWorkData(workingData);

		return retWorkingData;
	}


	//#CM575037
	// Package methods -----------------------------------------------
	//#CM575038
	// Protected methods ---------------------------------------------
	//#CM575039
	/**
	 * Fetch the Storage possible work from Work info<BR>
	 * Fetch the work using For Update<BR>
	 * Search Work info with the following conditions<BR>
	 * <DIR>
	 *   Job type = 02(Storage)<BR>
	 *   Status flag = 0:Standby or 2: in process with same Worker code and RFT no.<BR>
	 *   Work start flag = 1 (Started)<BR>
	 *   Consignor code, Storage Plan Date,Item code = fetch based on the parameter values<BR>
	 *   Case piece flag = fetch based on the parameter values (In case of 0:All, don't include in search condition)<BR>
	 *   Sort order = Location>Case piece flag>Expiry date>ITF>Bundle ITF<BR>
	 * </DIR>
	 * Return the fetched values as Work info enity array<BR>
	 * If there are no values, return empty array<BR>
	 * <BR>
	 * @param	consignorCode	Consignor code
	 * @param	planDate		Plan Date
	 * @param	itemCode		Item code
	 * @param	convertedJanCode 	Converted code from ITF to JAN
	 * @param	casePieceFlag 	Case piece flag
	 * @param	rftNo			RFT No.
	 * @param	workerCode		Worker code
	 * @return	Storage plan info(Work info entity array)
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws LockTimeOutException If the database lock is not removed after specified time
	 */
	protected WorkingInformation[] getWorkableStorageData(
		String consignorCode,
		String planDate,
		String itemCode,
		String convertedJanCode,
		String casePieceFlag,
		String rftNo,
		String workerCode)
		throws ReadWriteException, LockTimeOutException
	{		
		try
		{
			//#CM575040
			// Variable to store the WorkingInfo search result
			WorkingInformation[] workableStorageData = null;
		
			WorkingInformationHandler pObj = new WorkingInformationHandler(wConn);
			
			//#CM575041
			//-----------------
			//#CM575042
			// Search Work info (first time)
			//#CM575043
			// Search using the scanned code as JAN code
			//#CM575044
			//-----------------
			WorkingInformationSearchKey skey = getBaseCondition(consignorCode,planDate,casePieceFlag,rftNo,workerCode);		
			skey.setItemCode(itemCode);		
			int timeout = WmsParam.WMS_DB_LOCK_TIMEOUT;
			workableStorageData = (WorkingInformation[]) pObj.findForUpdate(skey,  timeout);
			if (workableStorageData != null && workableStorageData.length > 0)
			{
				return workableStorageData;
			}

			//#CM575045
			//-----------------
			//#CM575046
			// Search Work info (second time)
			//#CM575047
			// Search using the scanned code as Case ITF
			//#CM575048
			//-----------------
			skey = getBaseCondition(consignorCode,planDate,casePieceFlag,rftNo,workerCode);
			skey.setItf(itemCode);
			workableStorageData = (WorkingInformation[]) pObj.findForUpdate(skey,timeout);
			if (workableStorageData != null && workableStorageData.length > 0)
			{
				return workableStorageData;
			}

			//#CM575049
			//-----------------
			//#CM575050
			// Search Work info (third time)
			//#CM575051
			// Search using the scanned code as Bundle ITF
			//#CM575052
			//-----------------
			skey = getBaseCondition(consignorCode,planDate,casePieceFlag,rftNo,workerCode);
			skey.setBundleItf(itemCode);
			workableStorageData = (WorkingInformation[]) pObj.findForUpdate(skey,timeout);
			if (workableStorageData != null && workableStorageData.length > 0)
			{
				return workableStorageData;
			}
		
			if (!StringUtil.isBlank(convertedJanCode))
			{				
				//#CM575053
				//-----------------
				//#CM575054
				// Search Work info  (fourth time)
				//#CM575055
				// Search using the scanned code as ITF to JAN converted code
				//#CM575056
				//-----------------
				skey = getBaseCondition(consignorCode,planDate,casePieceFlag,rftNo,workerCode);
				skey.setItemCode(convertedJanCode);
				workableStorageData = (WorkingInformation[]) pObj.findForUpdate(skey,timeout);
				if (workableStorageData != null && workableStorageData.length > 0)
				{
					return workableStorageData;
				}
			}
			
		}
		catch (LockTimeOutException e)
		{
			//#CM575057
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNWORKINFO");
			throw e;
		}
		return null;
	}
	
	//#CM575058
	/**
	 * Create search condition to search for work possible data upon storage start request<BR>
	 * The seaerch condition is as below<BR>
	 * <DIR>
	 *   Consignor code, Plan Date, Case piece flag = fetch based on the parameter values<BR>
	 *   Job type=02 (Storage)<BR>
	 *   Status flag = 0:Standby or (2: in processwith same Worker code and RFT no.)<BR>
	 *   Work start flag = 1 (Started)<BR>
	 * </DIR>
	 * <BR>
	 * @param	consignorCode	Consignor code
	 * @param	planDate		Plan Date
	 * @param	casePieceFlag 	Case piece flag
	 * @param	rftNo			RFT No.
	 * @param	workerCode		Worker code
	 * @return	work info search key	Work info search key
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	protected WorkingInformationSearchKey getBaseCondition(
			String consignorCode,
			String planDate,
			String casePieceFlag,
			String rftNo,
			String workerCode) throws ReadWriteException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		
		//#CM575059
		// Job type is storage
		skey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		//#CM575060
		// Target data is with Status flag is either [standby] or [in process] ((Workeer code and RFT no. are same)) 
		//#CM575061
		//  SQL query =  (DNWORKINFO.STATUS_FLAG = '0' or (DNWORKINFO.STATUS_FLAG = '2' AND DNWORKINFO.WORKER_CODE = 'workerCode' AND DNWORKINFO.TERMINAL_NO = 'rftNo' )) AND
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		skey.setWorkerCode(workerCode);
		skey.setTerminalNo(rftNo, "=", "", "))", "AND");
		//#CM575062
		// Work start flag is [1:Started]
		skey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		skey.setConsignorCode(consignorCode);
		skey.setPlanDate(planDate);
		if (!casePieceFlag.equals(RFTId0230.CASE_PIECE_All))
		{
			skey.setWorkFormFlag(casePieceFlag);
		}
		skey.setLocationNoOrder(1, true);
		skey.setWorkFormFlagOrder(2, true);
		skey.setUseByDateOrder(3, true);
		skey.setItfOrder(4, true);
		skey.setBundleItfOrder(5, true);
		skey.setCollectJobNoOrder(6, true);
		return skey;
	}
	
	//#CM575063
	/**
	 * Fetch data from storage info<BR>
	 * Search Work info with the following conditions<BR>
	 * <DIR>
	 *   Consignor code, Storage plan date, Item code, Case piece flag = fetch based on the parameter values<BR>
	 *   Case piece flag = fetch based on the parameter values(In case of 0:All, don't include in search condition)<BR>
	 *   Job type = 02 (Storage)<BR>
	 *   Status flag = Other than 9 (Delete)<BR>
	 *   Work start flag = 1 (Started)<BR>
	 * </DIR>
	 * Return the fetched values as Work info enity array<BR>
	 * If there are no values, return empty array<BR>
	 * <BR>
	 * @param	consignorCode	Consignor code
	 * @param	planDate		Plan Date
	 * @param	itemCode		Item code
	 * @param	casePieceFlag 	Case piece flag
	 * @return	Storage plan info(Work info entity array)
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	protected WorkingInformation[] getStorageData(
		String consignorCode,
		String planDate,
		String itemCode,
		String casePieceFlag)
		throws ReadWriteException
	{
		//#CM575064
		// Variable to store the WorkingInfo search result
		WorkingInformation[] storageData = null;

		WorkingInformationSearchKey pskey = new WorkingInformationSearchKey();
		WorkingInformationHandler pObj = new WorkingInformationHandler(wConn);
		//#CM575065
		//-----------------
		//#CM575066
		// Search Work info
		//#CM575067
		//-----------------
		//#CM575068
		// Job type is storage
		pskey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		//#CM575069
		// Status flag other than [Delete]
		pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");

		//#CM575070
		// Work start flag is [1:Started]
		pskey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		pskey.setConsignorCode(consignorCode);
		pskey.setPlanDate(planDate);
		pskey.setItemCode(itemCode);
		if (!casePieceFlag.equals(RFTId0230.CASE_PIECE_All))
		{
			pskey.setWorkFormFlag(casePieceFlag);
		}

		storageData = (WorkingInformation[]) pObj.find(pskey);

		return storageData;
	}

	//#CM575071
	/**
	 * Extract work data<BR>
	 * Extract one record of work data from Work info entity array<BR>
	 * Fetch the first data of the array<BR>
	 * Return the extracted Work info <BR>
	 * <BR>
	 * Fetch Collect condition enabled/disabled in the parameter file (WMSParam.properties)<BR>
	 * If there is no collect condition, return the first record<BR>
	 * Since 1 record is not possible with collect condition, next collect will fetch the same data <BR>
	 * That time, set the same value of Collect Job no. and update DB<BR>
	 * <DIR>
	 *  <LI>Collect condition when ITF, Bundle ITF data are not grouped</LI><BR>
	 *  [Location, Case piece flag, ITF, Bundle ITF]<BR>
	 *  <LI>Collect condition when ITF, Bundle ITF data are different</LI><BR>
	 *  [Location, Case piece flag]<BR>
	 * </DIR>
	 * <BR>
	 * @param  preWorkingData	Work info entity array(Sorting in the order of Location>Case piece flag>ITF>Bundle ITF)<BR>
	 * @return	Storage work data(Work info entity array)<BR>
	 * @throws ReadWriteException If abnormal error occurs in database connection<BR>
	 * @throws InvalidDefineException If the specified value is abnormal (empty, illegal characters)<BR>
	 * @throws NotFoundException If the target data is not found (generally not possible since the data is already locked)<BR>
	 */
	protected WorkingInformation[] pickUpWorkingData(WorkingInformation[] preWorkingData)
		throws NotFoundException, InvalidDefineException, ReadWriteException
	{
		//#CM575072
		// Store the first data to a Vector
		Vector workingDataList = new Vector();
		workingDataList.add(preWorkingData[0]);

		//#CM575073
		// Work collection process
		if (WmsParam.STORAGE_JOBCOLLECT)
		{
			//#CM575074
			// While recollecting Collect Job No. (collection process)
			int collectKey = WmsParam.STORAGE_JOBCOLLECT_KEY;
			//#CM575075
			// create origin character string to compare
			String comparisonStrA = preWorkingData[0].getLocationNo().trim();
			comparisonStrA = comparisonStrA.concat(preWorkingData[0].getWorkFormFlag().trim());
			comparisonStrA = comparisonStrA.concat(preWorkingData[0].getUseByDate().trim());
			comparisonStrA = comparisonStrA.concat(preWorkingData[0].getItemCode().trim());
			if (collectKey != STR_ITEMCODE)
			{
				comparisonStrA = comparisonStrA.concat(preWorkingData[0].getItf().trim());
				comparisonStrA = comparisonStrA.concat(preWorkingData[0].getBundleItf().trim());
			}
			for (int i = 1; i < preWorkingData.length; i++)
			{
				//#CM575076
				// create target character string to compare
				String comparisonStrB = preWorkingData[i].getLocationNo().trim();
				comparisonStrB = comparisonStrB.concat(preWorkingData[i].getWorkFormFlag().trim());
				comparisonStrB = comparisonStrB.concat(preWorkingData[i].getUseByDate().trim());
				comparisonStrB = comparisonStrB.concat(preWorkingData[i].getItemCode().trim());
				if (collectKey != STR_ITEMCODE)
				{
					comparisonStrB = comparisonStrB.concat(preWorkingData[i].getItf().trim());
					comparisonStrB = comparisonStrB.concat(preWorkingData[i].getBundleItf().trim());
				}
				//#CM575077
				// compare
				if (comparisonStrA.equals(comparisonStrB))
				{
					//#CM575078
					// If the key is same, refetch Collect Job No.
					preWorkingData[i].setCollectJobNo(preWorkingData[0].getCollectJobNo());
					//#CM575079
					//-----------------
					//#CM575080
					// Update Work info  (Update the record that modified Collect Job No.)
					//#CM575081
					//-----------------
					WorkingInformationAlterKey workingAlterKey = new WorkingInformationAlterKey();
					//#CM575082
					// Set update conditions
					workingAlterKey.setJobNo(preWorkingData[i].getJobNo());
					//#CM575083
					// Set update contents
					workingAlterKey.updateCollectJobNo(preWorkingData[0].getCollectJobNo());
					workingAlterKey.updateLastUpdatePname(PROCESS_NAME);
					WorkingInformationHandler workingHandler = new WorkingInformationHandler(wConn);
					//#CM575084
					// Update process
					workingHandler.modify(workingAlterKey);
					//#CM575085
					// Add work
					workingDataList.add(preWorkingData[i]);
				}
				else
				{
					//#CM575086
					// Stop if the key is different
					break;
				}
			}
		}
		WorkingInformation[] workingData = (WorkingInformation[])workingDataList.toArray(new WorkingInformation[workingDataList.size()]);
		return workingData;
	}

	//#CM575087
	/**
	 * Update work data status to "in process"<BR>
	 * Using Work info entity received in parameter, update Work info and Storage plan info<BR>
	 * @param	rftNo			RFT No.<BR>
	 * @param	workerCode		Worker code<BR>
	 * @param	workerName		worker name<BR>
	 * @param  workingData		Work info entity array<BR>
	 * @throws ReadWriteException If abnormal error occurs in database connection<BR>
	 * @throws InvalidDefineException If the specified value is abnormal (empty, illegal characters)<BR>
	 * @throws NotFoundException If the target data is not found (generally not possible since the data is already locked)<BR>
	 */
	protected void updateToWorking(
		String rftNo,
		String workerCode,
		String workerName,
		WorkingInformation[] workingData)
		throws NotFoundException, InvalidDefineException, ReadWriteException
	{
		for (int i = 0; i < workingData.length; i++)
		{
			//#CM575088
			// Update to "in process" (update Work info and Storage plan info)
			//#CM575089
			//-----------------
			//#CM575090
			// Update Work info 
			//#CM575091
			//-----------------
			WorkingInformationAlterKey workingAlterKey = new WorkingInformationAlterKey();
			//#CM575092
			// Set update conditions
			workingAlterKey.setJobNo(workingData[i].getJobNo());
			//#CM575093
			// Set update contents
			workingAlterKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			workingAlterKey.updateTerminalNo(rftNo);
			workingAlterKey.updateWorkerCode(workerCode);
			workingAlterKey.updateWorkerName(workerName);
			workingAlterKey.updateLastUpdatePname(PROCESS_NAME);
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(wConn);
			//#CM575094
			// Update process
			workingHandler.modify(workingAlterKey);

			//#CM575095
			//-----------------
			//#CM575096
			// update Storage plan info
			//#CM575097
			//-----------------
			StoragePlanAlterKey storageAlterKey = new StoragePlanAlterKey();
			//#CM575098
			// Set update conditions
			storageAlterKey.setStoragePlanUkey(workingData[i].getPlanUkey());
			//#CM575099
			// Set update contents
			storageAlterKey.updateStatusFlag(StoragePlan.STATUS_FLAG_NOWWORKING);
			storageAlterKey.updateLastUpdatePname(PROCESS_NAME);
			StoragePlanHandler storageHandler = new StoragePlanHandler(wConn);
			//#CM575100
			// Update process
			storageHandler.modify(storageAlterKey);
		}
	}

	//#CM575101
	/**
	 * Collect work data<BR>
	 * Receive Work info entity array in parameter and group it to a single record<BR>
	 * <DIR>
	 *  <LI>field to set 1 record of array</LI><BR>
	 *  [Collect Job no., Consignor code, Consignor name, Item code, Item name, Bundle ITF, Location, Case piece flag,
	 *   ITF, Bundle ITF, Qty per case, Qty per bundle]<BR>
	 *  <LI>field to set the smallest value of array</LI><BR>
	 *  [Expiry date]<BR>
	 *  <LI>field to set total value</LI><BR>
	 *  [Plan qty, Result qty]<BR>
	 * </DIR>
	 * @param  workingData	Work info entity array<BR>
	 * @return	Storage work data(Work info entity)<BR>
	 * @throws OverflowException If the item count exceeds limit
	 */
	protected WorkingInformation collectWorkData(WorkingInformation[] workingData) throws OverflowException
	{
		//#CM575102
		// Copy the first data
		WorkingInformation retWorkingData = null;
		retWorkingData = (WorkingInformation)workingData[0].clone();
		
		for (int i = 1; i < workingData.length; i++)
		{
			//#CM575103
			// If expiry date is small, renew it
			if (workingData[i].getUseByDate() != "")
			{
				if (retWorkingData.getUseByDate().compareTo(workingData[i].getUseByDate()) > 0)
				{
					retWorkingData.setUseByDate(workingData[i].getUseByDate());
				}
			}
			try
			{
				retWorkingData.collect(workingData[i]);
			}
			catch (OverflowException e)
			{
				//#CM575104
				// 6026028=Overflow occurred during record integration processing. Table Name: {0}
				RftLogMessage.print(6026028, LogMessage.F_ERROR, CLASS_NAME, "DNWORKINFO");
				throw e;
			}
		}

		return retWorkingData;
	}

	//#CM575105
	// Private methods -----------------------------------------------

}
//#CM575106
//end of class
