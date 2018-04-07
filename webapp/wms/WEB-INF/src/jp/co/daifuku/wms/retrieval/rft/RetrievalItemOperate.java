// $Id: RetrievalItemOperate.java,v 1.3 2007/02/07 04:19:43 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;
//#CM721177
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
import jp.co.daifuku.wms.base.rft.WorkingInformationHandler;
//#CM721178
/**
 * Designer : T.Konishi<BR>
 * Maker : <BR>
 * <BR>
 * Execute the data process responding to the request for Item Picking data.<BR>
 * Obtain the Picking work data, and update one (1) Work data to "Processing".<BR>
 * Implement the business logic to be invoked from Id0340ProcessId0341Operate.<BR>
 * <BR>
 *  !Attention! <BR>
 * Allow the WorkingInformation class and the WorkingInformationHandler class, which are used in this class,
 * to use a RFT Package ("jp.co.daifuku.wms.base.rft" package).<BR>
 *  (Disabling to cast the parent class instance to the child class, require to use WorkingInformationHandler for RFT) <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:43 $
 * @author  $Author: suresh $
 */
public class RetrievalItemOperate extends IdOperate
{
	//#CM721179
	// Class fields----------------------------------------------------
	private static final String CLASS_NAME = "RetrievalItemOperate";
	
	private static final String START_PROCESS_NAME = "ID0340";

	//#CM721180
	//private static final String END_PROCESS_NAME = "RetrievalItemOperate";

	//#CM721181
	// Class variables -----------------------------------------------
	
	//#CM721182
	// Class method --------------------------------------------------
	//#CM721183
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $,$Date: 2007/02/07 04:19:43 $";
	}
	
	//#CM721184
	// Constructors --------------------------------------------------
	//#CM721185
	/**
	 * Generate an instance.
	 */
	public RetrievalItemOperate()
	{
		super();
	}

	//#CM721186
	/**
	 * Designate <code>Connection</code> for database connection and generate the instance.
	 * @param conn Connection for database connection 
	 */
	public RetrievalItemOperate(Connection conn)
	{
		super();
		wConn = conn;
	}

	//#CM721187
	// Public methods ------------------------------------------------	
	//#CM721188
	/**
	 * Obtain the the data responded to the request for Item Picking data, and update the status of one (1) workable data to "Processing".<BR>
	 * Update the next data of the base data, to "Processing".
	 * If no corresponding data, ignore the base data and update the first corresponding data.<BR>
	 * If no data still corresponds, return NotFoundException.<BR>
	 * Commit in the source that invokes this method.<BR>
	 * <BR>
	 * <OL>
	 *   <LI>Obtain all workable data from the Work Status.
	 *       ({@link #getWOrkableRetrievalItemData()})<BR>
	 *       Obtaining no corresponding data returns 
	 *       NotFoundException with "8: No corresponding data" as a message.</LI>
	 *   <LI>Obtain the Worker name.
	 *       ({@link BaseOperate#getWorkerName()})</LI>
	 *   <LI>Obtain (Extract) the work data to work actually from them.
	 *       ({@link #pickUpWorkData()})<BR>
	 *       Extract the next data of the base data, or 
	 *       if no corresponding data, ignore the base data and update the first corresponding data.<BR>
	 *       Update the extracted data (Work Status) and the Picking Plan Info to "Processing".<BR>
	 *       Aggregate the extracted data and return the aggregated work status as a return value.</LI>
	 * </OL>
	 * <BR>
	 * @param	processName		Process Name (ID0340)
	 * @param	consignorCode	Consignor Code
	 * @param	planDate		Planned date
	 * @param	areaNo			Area No. (If blank, regard it as Area Control OFF)
	 * @param	zoneNo			Zone No.
	 * @parm   direction		Approach direction
	 * @param	workForm 		Case Piece flag
	 * @param	rftNo			RFT Machine No.
	 * @param	workerCode		Worker Code
	 * @return	Picking Plan Info (Work Status Entity)
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws NotFoundException Announce when workable data is not found.
	 * @throws InvalidDefineException 
	 * @throws LockTimeOutException Announce when database lock is not cancelled for the specified period.
	 * @throws NoPrimaryException 
	 */
	public WorkingInformation startRetrievalItem(
		String consignorCode,
		String planDate,
		String areaNo,
		String zoneNo,
		String direction,
		String workForm,
		String rftNo,
		String workerCode,
		String baseLocation,
		String baseJanCode,
		String baseCasePieceFlag,
		String baseUseByDate,
		String baseBundleITF,
		String baseCaseITF,
		String baseTotalWorkNo)
		throws ReadWriteException, NotFoundException, InvalidDefineException, LockTimeOutException, OverflowException, NoPrimaryException
	{
		//#CM721189
		// Variable for maintaining the result of searching through WorkingInfo
		WorkingInformation[] preWorkingData = null;
		//#CM721190
		// Variable for maintaining the target work data
		WorkingInformation workingData = null;

		try
		{
			preWorkingData = getWorkableRetrievalItemData(
					consignorCode,
					planDate,
					areaNo,
					zoneNo,
					workForm,
					rftNo,
					workerCode,
					direction);
		}
		catch (LockTimeOutException e)
		{
			//#CM721191
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNWORKINFO");
			throw e;
		}
		if (preWorkingData.length == 0)
		{
			//#CM721192
			// When workable data is not found.
			NotFoundException e = new NotFoundException(RFTId5340.ANS_CODE_NULL);
			throw e;
		}
		//#CM721193
		// Obtain from the Worker Name.
		BaseOperate bo = new BaseOperate(wConn);
		String workerName = "";
		try
		{
			workerName = bo.getWorkerName(workerCode);
		}
		catch (NotFoundException e)
		{
			//#CM721194
			// No worker code: Worker master is suspended between the Start process and the Request Data process.
			//#CM721195
			// 6026019=No matching worker data was found in the Worker Master Table. Worker Code: {0}
			RftLogMessage.print(6026019, LogMessage.F_ERROR, CLASS_NAME, workerCode);
			NotFoundException ex = new NotFoundException(RFTId5340.ANS_CODE_ERROR);
			throw ex;
		}
		
		//#CM721196
		// Obtain the data of Picking work to be done actually.
		workingData = pickUpWorkData(rftNo, workerCode, workerName, preWorkingData, 
										baseLocation, baseJanCode, baseCasePieceFlag, baseUseByDate, baseBundleITF, baseCaseITF, baseTotalWorkNo);
		if (workingData == null)
		{
		    //#CM721197
		    // If no data is found, 
		    //#CM721198
		    // clear the data last sent, and then search for the skipped data again.
			workingData = pickUpWorkData(rftNo, workerCode, workerName, preWorkingData, null, null, null, null, null, null, null);	
		}
		
		return workingData;
	}

	//#CM721199
	/**
	 * Return the SearchKey that contains basic search conditions for Item Picking work data.
	 * @param	consignorCode	Consignor Code
	 * @param	planDate		Planned date
	 * @param	areaNo			Area No. (If blank, regard it as Area Control OFF)
	 * @param	zoneNo			Zone No.
	 * @param	workForm	 	Work Type
	 * @return	SearchKey that stores the basic search conditions for Item Picking work data.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public WorkingInformationSearchKey getBaseConditionOfRetrievalItem(
			String consignorCode,
			String planDate,
			String areaNo,
			String zoneNo,
			String workForm) throws ReadWriteException
	{
		WorkingInformationSearchKey pskey = new WorkingInformationSearchKey();
		
		//#CM721200
		// Target the data with Work division "Picking". 
		pskey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM721201
		// Target the data with Start Work flag "1: "Started" ".
		pskey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		pskey.setConsignorCode(consignorCode);
		pskey.setPlanDate(planDate);
		if (!StringUtil.isBlank(areaNo))
		{
			pskey.setAreaNo(areaNo);
		}
		pskey.setZoneNo(zoneNo);
		pskey.setOrderNo("");
		if (!workForm.equals(RFTId0340.WORK_FORM_All))
		{
			pskey.setWorkFormFlag(workForm);
		}
		
		return pskey;
	}
	
	//#CM721202
	/**
	 * Obtain the count of remaining Item Picking works.<BR>
	 * Search through the work status using the following conditions, and obtain the count.<BR>
	 * <DIR>
	 *  <LI>search conditions</LI><BR>
	 *  Work division = 03 (Picking) <BR>
	 *  Status flag = "0: Standby", or "Processing# updated by the worker via own terminal<BR>
	 *  Start Work flag = 1 ("Started") <BR>
	 *  Consignor Code, Planned Picking Date = Obtain from the Parameter.<BR>
	 *  Order No.  = Require to be blank.<BR>
	 *  Work Type  = Obtain from the Parameter (Disable to use it for a search conditions if it is "0: All")<BR>
	 *  <LI>Aggregation Conditions</LI><BR>
	 *  Work Type Item Code Location Expiry Date
	 * </DIR>
	 * <BR>
	 * @param	consignorCode	Consignor Code
	 * @param	planDate		Planned date
	 * @param	areaNo			Area No. (If blank, remove it from the search conditions)
	 * @param	zoneNo			Zone No.
	 * @param	workForm 		Work Type
	 * @param	rftNo		 	RFT No.
	 * @param	workerCode		Worker Code
	 * @return	Total count of Picking Plans
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int countRetrievalItemDataOfWorkable(
		String consignorCode,
		String planDate,
		String areaNo,
		String zoneNo,
		String workForm,
		String rftNo,
		String workerCode)
		throws ReadWriteException
	{
		//#CM721203
		// Set Search Condition
		WorkingInformationSearchKey pskey = getBaseConditionOfRetrievalItem(
		        consignorCode,
		        planDate,
				areaNo,
				zoneNo,
		        workForm);
		WorkingInformationHandler pObj = new WorkingInformationHandler(wConn);
		
		//#CM721204
		// Target the data with Status flag "Standby" or data with work "Processing" via its own terminal. 
		pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		pskey.setWorkerCode(workerCode);
		pskey.setTerminalNo(rftNo, "=", "", "))", "AND");

		//#CM721205
		// Process for aggregating work
		pskey.setWorkFormFlagGroup(1);
		pskey.setLocationNoGroup(2);
		pskey.setUseByDateGroup(3);
		pskey.setWorkFormFlagCollect("");
		pskey.setLocationNoCollect("");
		pskey.setUseByDateCollect("");
		if (SystemParameter.isRftWorkCollect(SystemParameter.JOB_TYPE_RETRIEVAL))
		{
			pskey.setItemCodeGroup(4);
			pskey.setItemCodeCollect("");
			if (! SystemParameter.isRftWorkCollectItemCodeOnly(SystemParameter.JOB_TYPE_RETRIEVAL))
			{
				pskey.setItfGroup(5);
				pskey.setBundleItfGroup(6);
				pskey.setItfCollect("");
				pskey.setBundleItfCollect("");
			}
		}
		else
		{
			pskey.setCollectJobNoGroup(4);
			pskey.setCollectJobNoCollect("");
		}
		return pObj.count(pskey);
	}

	//#CM721206
	/**
	 * Obtain the count of total Item Picking works.<BR>
	 * Search through the work status using the following conditions, and obtain the count.<BR>
	 * <DIR>
	 *  <LI>search conditions</LI><BR>
	 *  Work division = 03 (Picking) <BR>
	 *  Status flag = other than 9 (Deleted)<BR>
	 *  Start Work flag = 1 ("Started") <BR>
	 *  Consignor Code, Planned Picking Date = Obtain from the Parameter.<BR>
	 *  Order No.  = Require to be blank.<BR>
	 *  Case/Piece division = Obtain from the Parameter (Disable to use it for a search conditions if it is "0: All")<BR>
	 *  <LI>Aggregation Conditions</LI><BR>
	 *  Case/Piece division, Item Code, Location, and Expiry Date<BR>
	 * </DIR>
	 * <BR>
	 * @param	consignorCode	Consignor Code
	 * @param	planDate		Planned date
	 * @param	areaNo			Area No. (If blank, remove it from the search conditions)
	 * @param	zoneNo			Zone No.
	 * @param	workForm 		Case Piece flag
	 * @return	Total count of Picking Plans
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int countRetrievalItemDataOfAll(
			String consignorCode,
			String planDate,
			String areaNo,
			String zoneNo,
			String workForm) throws ReadWriteException
	{
		//#CM721207
		// Set Search Condition
		WorkingInformationSearchKey pskey = getBaseConditionOfRetrievalItem(
		        consignorCode,
		        planDate,
				areaNo,
				zoneNo,
		        workForm);
		WorkingInformationHandler pObj = new WorkingInformationHandler(wConn);
			
		//#CM721208
		// Target the data with Status flag other than "Deleted".
		pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		//#CM721209
		// Process for aggregating work
		pskey.setWorkFormFlagGroup(1);
		pskey.setLocationNoGroup(2);
		pskey.setUseByDateGroup(3);
		pskey.setWorkFormFlagCollect("");
		pskey.setLocationNoCollect("");
		pskey.setUseByDateCollect("");
		if (SystemParameter.isRftWorkCollect(SystemParameter.JOB_TYPE_RETRIEVAL))
		{
			pskey.setItemCodeGroup(4);
			pskey.setItemCodeCollect("");
			if (! SystemParameter.isRftWorkCollectItemCodeOnly(SystemParameter.JOB_TYPE_RETRIEVAL))
			{
				pskey.setItfGroup(5);
				pskey.setBundleItfGroup(6);
				pskey.setItfCollect("");
				pskey.setBundleItfCollect("");
			}
		}
		else
		{
			pskey.setCollectJobNoGroup(4);
			pskey.setCollectJobNoCollect("");
		}
		return pObj.count(pskey);
	}

	//#CM721210
	// Package methods -----------------------------------------------
	
	//#CM721211
	// Protected methods ---------------------------------------------
	//#CM721212
	/**
	 * Obtain the all the workable Item Picking data corresponding to the condition.<BR>
	 * Obtain the data attached with "For Update".<BR>
	 * Search through the work status using the following conditions.<BR>
	 * <DIR>
	 *  Work division = 03 (Picking) <BR>
	 *  Status flag = 0: Standby or 2: Processing. Require the data to have the same Worker Code and the same RFTNo.<BR>
	 *  Start Work flag = 1 ("Started") <BR>
	 *  Consignor Code, Planned Picking Date = Obtain from the Parameter.<BR>
	 *  Case/Piece division (Work Type)  = Obtain from the Argument parameter (Disable to use it for a search conditions if it is "0: All")<BR>
	 *  Order No.  = Require to be blank.<BR>
	 *  Area No.  = Obtain from the Parameter (Disable to use it for a search conditions if it is blank)<BR>
	 *  Zone No.  = Obtain from the Parameter.<BR>
	 *  Sorting order: Zone No. > Location > Item Code > Case/Piece division > Expiry Date > ITF > Bundle ITF
	 * </DIR>
	 * Return the obtained data in the form of array of Work Status entity.
	 * Obtaining no corresponding data returns empty array.<BR>
	 * <BR>
	 * @param	consignorCode	Consignor Code
	 * @param	planDate		Planned date
	 * @param	areaNo			Area No. (If blank, remove it from the search conditions)
	 * @param	zoneNo			Zone No.
	 * @param	workForm 		Case Piece flag
	 * @param	rftNo			RFT Machine No.
	 * @param	workerCode		Worker Code
	 * @return	Picking Plan Info (Array of Work Status entity)
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws LockTimeOutException Announce when database lock is not cancelled for the specified period.
	 */
	protected  WorkingInformation[] getWorkableRetrievalItemData(
		String consignorCode,
		String planDate,
		String areaNo,
		String zoneNo,
		String workForm,
		String rftNo,
		String workerCode,
		String direction)
		throws ReadWriteException, LockTimeOutException
	{
		//#CM721213
		// Variable for maintaining the result of searching through WorkingInfo
		WorkingInformation[] workableRetrievalData = null;

		WorkingInformationSearchKey pskey = getBaseConditionOfRetrievalItem(
		        consignorCode,
		        planDate,
				areaNo,
				zoneNo,
		        workForm);
		WorkingInformationHandler pObj = new WorkingInformationHandler(wConn);
		//#CM721214
		//-----------------
		//#CM721215
		// Search through the Work Status.
		//#CM721216
		//-----------------
		//#CM721217
		// Target the data with Work division "Picking". 
		pskey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM721218
		// Target the data with Status flag "Standby" or "Processing" (only for data with the same Worker Code and the same RFTNo.) 
		//#CM721219
		//  SQL statement ---  (DNWORKINFO.STATUS_FLAG = '0' or (DNWORKINFO.STATUS_FLAG = '2' AND DNWORKINFO.WORKER_CODE = 'workerCode' AND DNWORKINFO.TERMINAL_NO = 'rftNo' )) AND
		pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		pskey.setWorkerCode(workerCode);
		pskey.setTerminalNo(rftNo, "=", "", "))", "AND");
		if(StringUtil.isBlank(areaNo))
		{
			if(direction.equals(RFTId0340.DIRECTION_TRUE))
			{
				pskey.setLocationNoOrder(1, true);
			}
			else
			{
				pskey.setLocationNoOrder(1, false);
			}
			pskey.setItemCodeOrder(2, true);
			pskey.setWorkFormFlagOrder(3, true);
			pskey.setUseByDateOrder(4, true);
			pskey.setItfOrder(5, true);
			pskey.setBundleItfOrder(6, true);
			pskey.setCollectJobNoOrder(7, true);
		}
		else
		{
			pskey.setZoneNoOrder(1, true);
			if(direction.equals(RFTId0340.DIRECTION_TRUE))
			{
				pskey.setLocationNoOrder(2, true);
			}
			else
			{
				pskey.setLocationNoOrder(2, false);
			}
			pskey.setItemCodeOrder(3, true);
			pskey.setWorkFormFlagOrder(4, true);
			pskey.setUseByDateOrder(5, true);
			pskey.setItfOrder(6, true);
			pskey.setBundleItfOrder(7, true);
			pskey.setCollectJobNoOrder(8, true);
		}

		try
		{
			workableRetrievalData = (WorkingInformation[]) pObj.findForUpdate(pskey, WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM721220
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNWORKINFO");
			throw e;
		}

		return workableRetrievalData;
	}

	//#CM721221
	/**
	 * Extract the data for the current work from the work target data list.<BR>
	 * Extract the next one work data of the base data from the array of Work Status entity received as a parameter.<BR>
	 * Return the extracted work status as a return value.<BR>
	 * <BR>
	 * Regard the designated Super data respectively as the base of Start Search.
	 * If each super data is not designated, regard the base data as the first one.<BR>
	 * Obtain the next data of the current data (isOldWorkDatamethod).<BR>
	 * Update one of the data not to be aggregated to "Processing", and return the Work Status as a return value.<BR>
	 * Update every one work to "Processing" one by one before aggregation, and aggregate them. Return the aggregated Work Status as a return value.<BR>
	 * <BR>
	 * Return <CODE>null</CODE> if all data was skipped and no data exists.<BR>
	 * <BR>
	 * @param	rftNo			Terminal No.
	 * @param	workerCode	 	Worker Code
	 * @param	workerName		Worker Name
	 * @param  workableDataList	Array of Work Status entity
	 * 							 (Require to sort in the order of Location >Item Code>Case/Piece division>Expiry Date>ITF>Bundle ITF) 
	 * @return	Picking work data (Array of Work Status entity)<BR>
	 * 			Return <CODE>null</CODE> if no corresponding data found.
	 * @throws InvalidDefineException Announce when the designated value is abnormal (blank or containing prohibited characters).
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws OverflowException Announce when the length of value in the numeral field item exceeds its maximum length.
	 * @throws NoPrimaryException 
	 */
	protected  WorkingInformation pickUpWorkData(
		String rftNo,
		String workerCode,
		String workerName,
		WorkingInformation[] workableDataList,
		String baseLocation,
		String baseJanCode,
		String baseCasePieceFlag,
		String baseUseByDate,
		String baseBundleITF,
		String baseCaseITF,
		String baseTotalWorkNo)
		throws InvalidDefineException, ReadWriteException, OverflowException, NoPrimaryException
	{
		//#CM721222
		// Obtain the data that was sent previously.
		WorkingInformation baseData = null;
		WorkingInformationSearchKey wkey = new WorkingInformationSearchKey();
		WorkingInformationHandler handler = new WorkingInformationHandler(wConn);
		
		//#CM721223
		// Set the base data.
		//#CM721224
		// Work division "03: Picking"
		wkey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM721225
		// Status flag other than Deleted 
		wkey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		wkey.setLocationNo(baseLocation);
		wkey.setItemCode(baseJanCode);
		wkey.setWorkFormFlag(baseCasePieceFlag);
		wkey.setUseByDate(baseUseByDate);
		wkey.setBundleItf(baseBundleITF);
		wkey.setItf(baseCaseITF);
		wkey.setJobNo(baseTotalWorkNo);
		
		
		baseData = (WorkingInformation)handler.findPrimary(wkey);
		int i = 0;
		//#CM721226
		// Skip the sent data.
		for (; i < workableDataList.length; i ++)
		{
		    if (baseData == null || ! baseData.isOldWorkData(workableDataList[i]))
		    {
		        break;
		    }
		}
		
		if (i >= workableDataList.length)
		{
		    //#CM721227
		    // Return null if no target data found.
		    return null;
		}

        if (! SystemParameter.isRftWorkCollect(SystemParameter.JOB_TYPE_RETRIEVAL))
		{
            //#CM721228
            // Update the Work target data to "Processing".
            updateToWorking(rftNo, workerCode, workerName, workableDataList[i]);

            //#CM721229
            // Return the obtained data, as it is, if no aggregation is done.
		    return workableDataList[i];
		}
		
		WorkingInformation currentData = (WorkingInformation) workableDataList[i];
		int startIndex = i;
		int endIndex = i;

		for (i ++; i < workableDataList.length; i ++)
		{
		    if (currentData.hasSameKeyForRetrievalItem(workableDataList[i]))
		    {
				try
				{
			        //#CM721230
			        // If the Aggregation key is same, aggregate the work data.
		        	currentData.collect(workableDataList[i]);
		        	endIndex = i;
				}
				catch (OverflowException e)
				{
					//#CM721231
					// 6026028=Overflow occurred during record integration processing. Table Name: {0}
					RftLogMessage.print(6026028, LogMessage.F_ERROR, CLASS_NAME, "DNWORKINFO");
					throw e;
				}
		        
		    }
		    else
		    {
		        //#CM721232
		        // If the key is different, close the aggregation process.
		        break;
		    }
		}
		for( ; startIndex <= endIndex; startIndex++)
		{
			//#CM721233
			// Set the Aggregation Work No.
	        workableDataList[startIndex].setCollectJobNo(workableDataList[endIndex].getJobNo());
	        //#CM721234
	        // Update the aggregated data to "Processing".
	        updateToWorking(rftNo, workerCode, workerName, workableDataList[startIndex]);
		}

        return currentData;
	}

	//#CM721235
	/**
	 * Update the work data to "Processing".<BR>
	 * Update the Work Status and the Picking Plan Info corresponding to the array of the Work Status entity received as a parameter, to "Processing".
	 * <BR>
	 * @param	rftNo			RFT Machine No.
	 * @param	workerCode		Worker Code
	 * @param	workerName		Worker Name
	 * @param  workingData		Work Status Entity
	 * @throws InvalidDefineException	Announce when the designated value is abnormal (blank or containing prohibited characters).
	 * @throws ReadWriteException		Announce when error occurs on the database connection.
	 */
	protected  void updateToWorking(
		String rftNo,
		String workerCode,
		String workerName,
		WorkingInformation workingData)
	throws InvalidDefineException, ReadWriteException
	{
		try
		{
			//#CM721236
			// Update the status to "Processing" (Update the Work Status and the Picking Plan Info).
			//#CM721237
			//-----------------
			//#CM721238
			// Update Work Status.
			//#CM721239
			//-----------------
			WorkingInformationAlterKey workingAlterKey = new WorkingInformationAlterKey();
			//#CM721240
			// Set the Update condition.
			workingAlterKey.setJobNo(workingData.getJobNo());
			//#CM721241
			// Set the content to be updated.
			workingAlterKey.updateCollectJobNo(workingData.getCollectJobNo());
			workingAlterKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			workingAlterKey.updateTerminalNo(rftNo);
			workingAlterKey.updateWorkerCode(workerCode);
			workingAlterKey.updateWorkerName(workerName);
			workingAlterKey.updateLastUpdatePname(START_PROCESS_NAME);
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(wConn);
			//#CM721242
			// "Update" Process
			workingHandler.modify(workingAlterKey);

			//#CM721243
			//-----------------
			//#CM721244
			// Update the Picking Plan Info.
			//#CM721245
			//-----------------
			RetrievalPlanAlterKey retrievalAlterKey = new RetrievalPlanAlterKey();
			//#CM721246
			// Set the Update condition.
			retrievalAlterKey.setRetrievalPlanUkey(workingData.getPlanUkey());
			//#CM721247
			// Set the content to be updated.
			retrievalAlterKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING);
			retrievalAlterKey.updateLastUpdatePname(START_PROCESS_NAME);
			RetrievalPlanHandler retrievalHandler = new RetrievalPlanHandler(wConn);
			//#CM721248
			// "Update" Process
			retrievalHandler.modify(retrievalAlterKey);
		}
		catch (NotFoundException e)
		{
			String errData =
				"[ConsignorCode:"	+ workingData.getConsignorCode()
					+ " PlanDate:"	+ workingData.getPlanDate()
					+ " CasePieceFlag:"	+ workingData.getCasePieceFlag()
					+ " Location:"	+ workingData.getLocationNo()
					+ " ItemCode:"	+ workingData.getItemCode()
					+ " RftNo:"		+ rftNo
					+ " WorkerCode:"+ workerCode	+ "]";
			//#CM721249
			// 6026016=No data you try to update is found. {0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);
			throw new ReadWriteException();
		}
	}

	//#CM721250
	// Private methods -----------------------------------------------

}
//#CM721251
//end of class
