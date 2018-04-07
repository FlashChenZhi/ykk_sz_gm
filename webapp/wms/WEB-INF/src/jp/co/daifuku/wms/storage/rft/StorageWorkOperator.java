// $Id: StorageWorkOperator.java,v 1.2 2006/12/07 09:00:01 suresh Exp $
package jp.co.daifuku.wms.storage.rft;
//#CM576963
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WorkOperator;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;
import jp.co.daifuku.wms.storage.schedule.StockAllocateOperator;
import jp.co.daifuku.wms.storage.schedule.StorageCompleteOperator;


//#CM576964
/**
 * Designer : K.Shimizu <BR>
 * Maker : K.Shimizu <BR>
 * <BR>
 * This is the Storage result send process from RFT<BR>
 * There are three types are process namely Normal, Shortage and Cancel<BR>
 * Execute the business logic called from Id0231Process<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/11</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:01 $
 * @author  $Author: suresh $
 */
public class StorageWorkOperator extends WorkOperator
{
	//#CM576965
	// Class variables -----------------------------------------------
	//#CM576966
	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "Id0023Operate";

	//#CM576967
	// Class method --------------------------------------------------
	//#CM576968
	/**
	 * Return the version of this class
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/12/07 09:00:01 $";
	}
	
	//#CM576969
	// Constructors --------------------------------------------------
	//#CM576970
	/**
	 * Constructor
	 */
	public StorageWorkOperator()
	{
		super();
	}
	
	//#CM576971
	/**
	 * Pass Db connection info to the constructor
	 * @param c <code>conn</code> DBConnection info
	 */
	public StorageWorkOperator(Connection c)
	{
		super();
		conn = c;
	}
	
	//#CM576972
	// Public methods ------------------------------------------------
	
	//#CM576973
	// Package methods -----------------------------------------------

	//#CM576974
	// Protected methods ---------------------------------------------
	//#CM576975
	/**
	 * Update Storage plan info based on updated Work info<BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Storage plan unique key=Fetch from Work info</LI>
	 *     <LI>Status flag!=9:Delete</LI>
	 *    </UL>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Work result qty</TD>		<TD>Result qty of telegraph data</TD></TR>
	 *      <TR><TD>Work pending qty</TD>		<TD>Set shortage qty if shortage occurs</TD></TR>
	 *      <TR><TD>Expiry date</TD>		<TD>Expiry date of telegram data</TD></TR>
	 *      <TR><TD>Last update Process name</TD>	<TD>"ID0231"</TD></TR>
	 *    </TABLE>
	 *   Add work result qty, work pending qty of work info based on storage result qty, storage shortage qty<BR>
	 * </DIR>
	 * 
	 * @param wi Work info target to update
	 * @throws InvalidStatusException If multiple data occurs while searching with unique key
	 * @throws ReadWriteException		If abnormal error occurs in database connection
	 * @throws NotFoundException		If target data does not exist for update
	 * @throws InvalidDefineException	If the defined parameter value differs (restricted characters)
	 */
	public void updatePlanInformation(WorkingInformation wi)
		throws 	InvalidStatusException, ReadWriteException, NotFoundException, InvalidDefineException
	{
		try
		{
			//#CM576976
			// Search corresponding storage plan info
			StoragePlanSearchKey skey = new StoragePlanSearchKey();
			skey.setStoragePlanUkey(wi.getPlanUkey());
			skey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");
			StoragePlanHandler handler = new StoragePlanHandler(conn);
			StoragePlan plan = (StoragePlan) handler.findPrimaryForUpdate(skey);

			//#CM576977
			// Update storage plan info
			StoragePlanAlterKey akey = new StoragePlanAlterKey();
			akey.setStoragePlanUkey(plan.getStoragePlanUkey());
			akey.updateResultQty(plan.getResultQty() + wi.getResultQty());
			akey.updateShortageCnt(plan.getShortageCnt() + wi.getShortageCnt());
			akey.updateLastUpdatePname(processName);
			handler.modify(akey);
		}
		catch (NoPrimaryException e)
		{
			String errString = "[Table:DnStoragePlan" + 
								" STORAGE_PLAN_UKEY = " + wi.getPlanUkey() +"]";
			//#CM576978
			// 6026020=Multiple records are found in a search by the unique key. {0}
			RftLogMessage.print(6026020, LogMessage.F_ERROR, CLASS_NAME, errString);
			//#CM576979
			// Change to appropriate exception
			throw new InvalidStatusException();
		}
	}
	
	//#CM576980
	/**
	 * Based of the Work info updated using Plan unique key, change status flag of Storage plan info<BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Storage plan unique key=Work info.Plan unique key (There is one storage plan info for each corresponding work info)
	 *     <LI>Status flag!=9:Delete</LI>
	 *    </UL>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Status flag</TD>		<TD>0:Standby 2:In process 3:Partial completion 4:Complete</TD></TR>
	 *      <TR><TD>Last update Process name</TD>	<TD>"ID0231"</TD></TR>
	 *    </TABLE>
	 *   Update status flag<BR>
	 *   Search Work info using the target data's storage plan unique key<BR>
	 *   <UL>
	 *   <LI>If all the target data is "Standby", update status to 0:Standby</LI>
	 *   <LI>If atleast one record of Work info is in process, update to 2: In process</LI>
	 *   <LI>If records of Work info is a mixture of "Standby" and "Completed" update to 3:Partial completion</LI>
	 *   <LI>Update to 4:Complete if all the work is completed</LI>
	 *   </UL>
	 * </DIR>
	 * @param planUkey Plan unique key list
	 * @throws ReadWriteException		If abnormal error occurs in database connection
	 * @throws NotFoundException		If target data does not exist for update
	 * @throws InvalidDefineException	If the defined parameter value differs (restricted characters)
	 */
	protected void updateCompletionStatus(String[] planUkey) throws ReadWriteException, NotFoundException, InvalidDefineException
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

			StoragePlanHandler sHandler = new StoragePlanHandler(conn);
			StoragePlanAlterKey akey = new StoragePlanAlterKey();
			akey.setStoragePlanUkey(planUkey[i]);
			akey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");

			boolean existCompleteData = false;
			boolean existUnstartData = false;
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
				else
				{
					existWorkingData = true;
					break;
				}
			}
			if (existWorkingData || (! existCompleteData && ! existUnstartData))
			{
				//#CM576981
				// If in process, complete or standby data exist
				//#CM576982
				// Don't update status flag
				continue;
			}
			if (existCompleteData)
			{
				if (existUnstartData)
				{
					//#CM576983
					// If complete data and standby data both exist update status to partially completed
					akey.updateStatusFlag(
						StoragePlan.STATUS_FLAG_COMPLETE_IN_PART);
				}
				else
				{
					//#CM576984
					// If complete data alone exist update status to completed
					akey.updateStatusFlag(
						StoragePlan.STATUS_FLAG_COMPLETION);
				}
			}
			else
			{
				//#CM576985
				// If standby data alone exist update status to standby
				akey.updateStatusFlag(StoragePlan.STATUS_FLAG_UNSTART);
			}
			akey.updateLastUpdatePname(processName);
			sHandler.modify(akey);
		}
	}
	
	//#CM576986
	/**
	 * Update Stock info based on modified Work info and create result send info<BR>
	 * <DIR>
	 *    Use storage complete method<BR>
	 *    Update stock info and Create result send info with Storage completion method.<BR>
	 * </DIR>
	 * @param wi Work info target to update
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws LockTimeOutException Stock info If time out occurs during lock
	 * @throws ScheduleException If exception occurs during storage complete process
	 * @throws InvalidDefineException If the paramter value is abnormal
	 * @throws OverflowException If the item count exceeds limit
	 */
	protected void updateStockQty(WorkingInformation wi)
		throws ReadWriteException, LockTimeOutException, ScheduleException, InvalidDefineException, OverflowException
	{
		try
		{
			String locationNo[] = {wi.getResultLocationNo()};
			//#CM576987
			// Stock info lock
			StockAllocateOperator stockAllocateOperator = 
				new StockAllocateOperator(WmsParam.WMS_DB_LOCK_TIMEOUT);
			stockAllocateOperator.stockSearchForUpdate(
					conn,
					wi.getConsignorCode(),
					locationNo,
					wi.getItemCode(),
					wi.getResultUseByDate() );
		}
		catch (InvalidDefineException e)
		{
			//#CM576988
			// if the value is abnormal
			String errData =
				"<ConsignorCode[" + wi.getConsignorCode()
					+ "] locationNo[" + wi.getResultLocationNo()
					+ "] itemCode[" + wi.getItemCode() + "]>";
			throw new InvalidDefineException(errData);
		}
		
		//#CM576989
		// storage complete process (Update stock info, result send info)
		StorageCompleteOperator storageCompleteOperator = new StorageCompleteOperator();
		storageCompleteOperator.complete(conn, wi.getJobNo(), processName);
	}
	
	//#CM576990
	/**
	 * Update worker result<BR>
	 * <DIR>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Work complete date/time</TD>	<TD>Current system date</TD></TR>
	 *      <TR><TD>Work qty</TD>		<TD>+Work completed result qty</TD></TR>
	 *      <TR><TD>Work count</TD>		<TD>+1 </TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @param	resultQty		Storage complete qty
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	protected void createWorkerResult(
			String workerCode, 
			String rftNo, 
			int resultQty) throws ReadWriteException
	{
		BaseOperate bo = new BaseOperate(conn);
		String workingDate = "";
		try
		{
			workingDate = bo.getWorkingDate();
		}
		catch (NotFoundException e)
		{
			//#CM576991
			// If work date can't be retrieved from system define info
			//#CM576992
			// throw ReadWriteException
			throw (new ReadWriteException());
		}

		//#CM576993
		// update result by worker
		WorkerResult wr = new WorkerResult();
		wr.setWorkDate(workingDate);
		wr.setWorkerCode(workerCode);
		wr.setTerminalNo(rftNo);
		wr.setJobType(WorkerResult.JOB_TYPE_STORAGE);
		wr.setWorkCnt(1);
		wr.setWorkQty(resultQty);
		try
		{
			bo.alterWorkerResult(wr);
		}
		catch (NotFoundException e)
		{
			//#CM576994
			// If target worker result does not exist, create new
			//#CM576995
			// (If daily update is in process in RFT)
			//#CM576996
			// 6022004=No matching Worker Result Data is found. A new data will be created. {0}
			String errData = "[WorkDate:" + workingDate +
			 				 " JobType:" + WorkerResult.JOB_TYPE_STORAGE +
							 " RftNo:" + rftNo +
							 " WorkerCode:" + workerCode +"]";
			RftLogMessage.print(6022004, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				//#CM576997
				// create worker result
				bo.createWorkerResult(WorkerResult.JOB_TYPE_STORAGE, workerCode, rftNo);
				//#CM576998
				// update worker result
				bo.alterWorkerResult(wr);
			}
			catch (Exception e1)
			{
				//#CM576999
				// If exception occurs throw ReadWriteException
				throw (new ReadWriteException());
			}
		}
	}
	
	//#CM577000
	/**
	 * Lock target data and fetch plan unique key list<BR>
	 * Work info, Storage plan info lock<BR>
	 * <DIR>
	 *    Work info<BR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Collect Job no.(Product identification code of telegraph data)</LI>
	 *     <LI>Job type=2:Storage</LI>
	 *     <LI>Status flag=2:In process</LI>
	 * 	   <LI>Worker code=Worker code(Worker code of telegraph data)</LI>
	 * 	   <LI>terminal no.=RFT no.(RFT no. of telegraph data)</LI>
	 *    </UL>
	 *    Storage plan info<BR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Storage plan unique key=Fetch from Work info</LI>
	 *     <LI>Status flag!=9:Delete</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param	collectJobNo	Collect Job no.
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @return	Storage plan unique key array matching conditions
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws LockTimeOutException If the database lock is not removed after specified time
	 */
	protected String[] lockUpdateData(
		String collectJobNo,
		String workerCode,
		String rftNo) throws ReadWriteException, LockTimeOutException
	{
	    String collectJobNoList[] = {collectJobNo};
	    
		//#CM577001
		// Lock WorkingInformation
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		skey.setCollectJobNo(collectJobNo);
		skey.setWorkerCode(workerCode);
		skey.setTerminalNo(rftNo);
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		skey.setJobType(jobType);
		skey.setCollectJobNoOrder(1, true);
		skey.setJobNoOrder(2, true);

		WorkingInformationHandler handler =	new WorkingInformationHandler(conn);
		try
		{
			handler.findForUpdate(skey, WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM577002
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNWORKINFO");
			throw e;
		}

		String[] planUkeyList = getPlanUkeyList(collectJobNoList, workerCode, rftNo);

		try
		{
			//#CM577003
			// Lock Storage plan info
			lockPlanInformation(planUkeyList);
		}
		catch (LockTimeOutException e)
		{
			//#CM577004
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNSTORAGEPLAN");
			throw e;
		}
		
		return planUkeyList;
	}
	
	//#CM577005
	/**
	 * Update Work info <BR>
	 * @param	pWorkInfo Work info target to update
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws InvalidStatusException If multiple data occurs while searching with unique key
	 * @throws DataExistsException If similar data already exist while trying to add relocation info
	 */
	protected void createWorkInfo(
				WorkingInformation pWorkInfo) throws ReadWriteException, InvalidStatusException, DataExistsException
	
	{
		SequenceHandler sequence = new SequenceHandler(conn);
		
		WorkingInformation winfo = new WorkingInformation();
		
		//#CM577006
		// Job no..
		winfo.setJobNo(sequence.nextJobNo());
		//#CM577007
		// Job type:Storage
		winfo.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		//#CM577008
		// Collect Job no..
		winfo.setCollectJobNo(pWorkInfo.getCollectJobNo());
		winfo.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		winfo.setBeginningFlag(pWorkInfo.getBeginningFlag());
		winfo.setPlanUkey(pWorkInfo.getPlanUkey());
		winfo.setStockId(pWorkInfo.getStockId());
		winfo.setAreaNo(pWorkInfo.getAreaNo());
		winfo.setLocationNo(pWorkInfo.getLocationNo());
		winfo.setPlanDate(pWorkInfo.getPlanDate());
		winfo.setConsignorCode(pWorkInfo.getConsignorCode());
		winfo.setConsignorName(pWorkInfo.getConsignorName());
		winfo.setSupplierCode(pWorkInfo.getSupplierCode());
		winfo.setSupplierName1(pWorkInfo.getSupplierName1());
		winfo.setInstockTicketNo(pWorkInfo.getInstockTicketNo());
		winfo.setCustomerCode(pWorkInfo.getCustomerCode());
		winfo.setCustomerName1(pWorkInfo.getCustomerName1());
		winfo.setShippingTicketNo(pWorkInfo.getShippingTicketNo());
		winfo.setShippingLineNo(pWorkInfo.getShippingLineNo());
		winfo.setItemCode(pWorkInfo.getItemCode());
		winfo.setItemName1(pWorkInfo.getItemName1());
		winfo.setHostPlanQty(pWorkInfo.getHostPlanQty());
		winfo.setPlanQty(pWorkInfo.getPlanEnableQty() - pWorkInfo.getResultQty());
		winfo.setPlanEnableQty(pWorkInfo.getPlanEnableQty() - pWorkInfo.getResultQty());
		winfo.setResultQty(0);
		winfo.setShortageCnt(0);
		winfo.setPendingQty(0);
		winfo.setEnteringQty(pWorkInfo.getEnteringQty());
		winfo.setBundleEnteringQty(pWorkInfo.getBundleEnteringQty());
		winfo.setCasePieceFlag(pWorkInfo.getCasePieceFlag());
		winfo.setWorkFormFlag(pWorkInfo.getWorkFormFlag());
		winfo.setItf(pWorkInfo.getItf());
		winfo.setBundleItf(pWorkInfo.getBundleItf());
		winfo.setTcdcFlag(pWorkInfo.getTcdcFlag());
		winfo.setUseByDate(pWorkInfo.getUseByDate());
		winfo.setLotNo(pWorkInfo.getLotNo());
		winfo.setPlanInformation(pWorkInfo.getPlanInformation());
		winfo.setOrderNo(pWorkInfo.getOrderNo());
		winfo.setOrderingDate(pWorkInfo.getOrderingDate());
		winfo.setResultUseByDate("");
		winfo.setResultLotNo("");
		winfo.setResultLocationNo("");
		winfo.setReportFlag(pWorkInfo.getReportFlag());
		winfo.setBatchNo(pWorkInfo.getBatchNo());
		winfo.setWorkerCode("");
		winfo.setWorkerName("");
		winfo.setTerminalNo("");
		winfo.setPlanRegistDate(pWorkInfo.getPlanRegistDate());
		winfo.setRegistPname(processName);
		winfo.setLastUpdatePname(processName);
		
		WorkingInformationHandler workHandler = new WorkingInformationHandler(conn);
		
		workHandler.create(winfo);
		
		
		
	}

    //#CM577009
    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.WorkOperator#updateWorkingInformation(java.lang.String, java.lang.String, jp.co.daifuku.wms.base.rft.WorkingInformation[], boolean)
     * Not used
     */
    public void updateWorkingInformation(String rftNo, String workerCode, WorkingInformation[] workinfo, boolean isShortage) throws InvalidStatusException, ReadWriteException, NotFoundException, InvalidDefineException, DataExistsException, UpdateByOtherTerminalException
    {
    }

    //#CM577010
    /**
     * Lock data with status flag other than Delete based on the Storage plan unique key specified in the argument
     * @param  planUKeyList  Storage plan unique key array
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws LockTimeOutException If the database lock is not removed after specified time
     */
    protected void lockPlanInformation(String[] planUKeyList) throws ReadWriteException, LockTimeOutException
    {
		//#CM577011
		// Lock Storage plan info
		StoragePlanHandler sHandler = new StoragePlanHandler(conn);
		StoragePlanSearchKey sskey = new StoragePlanSearchKey();

		sskey.setStoragePlanUkey(planUKeyList);
		sskey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");		
		sHandler.findForUpdate(sskey ,WmsParam.WMS_DB_LOCK_TIMEOUT);
    }

	//#CM577012
	// Private methods -----------------------------------------------

}
//#CM577013
//end of class
