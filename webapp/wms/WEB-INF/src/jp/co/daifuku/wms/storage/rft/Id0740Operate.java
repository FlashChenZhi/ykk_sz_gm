// $Id: Id0740Operate.java,v 1.2 2006/12/07 09:00:10 suresh Exp $
package jp.co.daifuku.wms.storage.rft;
//#CM575567
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.MovementAlterKey;
import jp.co.daifuku.wms.base.dbhandler.MovementHandler;
import jp.co.daifuku.wms.base.dbhandler.MovementSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.DataColumn;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.Idutils;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.master.operator.AreaOperator;
//#CM575568
/**
 * Designer : Y.Taki<BR>
 * Maker : Y.Taki<BR>
 * <BR>
 * Relocation storage start request process from RFT<BR>
 * The business logic called from Id0740Process is executed <BR>
 * <BR>
 * Relocation storage data search process (<CODE>findMovementStrageData()</CODE> method)<BR>
 * <BR>
 * <DIR>
 *  Throw NotFoundException if there is no corresponding data<BR>
 *  If corresponding data exist, update data with same Collect Job no. in storage<BR>
 *  And group that data and return as relocation info entity as single record
 *  If target data exist with multiple Collect Job no., group with Collect Job no. position 
 *  Search Relocation storage possible data from relocation info <BR>
 *  <BR>
 *  If [Relocation Job no.] is empty, search using Consignor code+JAN code+Expiry date as key <BR>
 *  If not empty search using Collect Job no.<BR>
 * </DIR>
 * <BR>
 * Relocation storage list file create process (<CODE>createAnsFile()</CODE> method)<BR>
 * <BR>
 * <DIR>
 *  Write the received relocation info to specified file in order<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/16</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:10 $
 * @author  $Author: suresh $
 */
public class Id0740Operate extends IdOperate
{
	//#CM575569
	// Class fields----------------------------------------------------
	//#CM575570
	/**
	 * Process name
	 */
	private static final String PROCESS_NAME = "ID0740";
	
	//#CM575571
	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "Id0740Operate";
	//#CM575572
	// Class variables -----------------------------------------------
	//#CM575573
	// Constructors --------------------------------------------------

	//#CM575574
	// Class method --------------------------------------------------
	//#CM575575
	/**
	 * Return the version of this class
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:10 $");
	}

	//#CM575576
	/**
	 * Fetch Relocation storage possible item data from relocation info<BR>
	 * Commit is done at the caller side<BR>
	 * Fetch Consignor code, Item code, Expiry date, Collect Job no. as parameter<BR>
	 * If Collect job no. is empty, use Consignor code, Item code as parameter,
	 * If Collect Job no. is not empty, use Collect Job no. as parameter and
	 * call Relocation storage possible data search method (getMoveableData) <BR>
	 * <BR>
	 * If data can't be retrieved, call relocation info data search method (getMovementData) <BR>
	 * <DIR>
	 *  Throw NotFoundException if data can't be retrieved during search and set 8 (corresponding data does not exist)<BR>
	 *  Throw NotFoundException if there is atleast one in process record in the fetched record and set 1 (In process in another terminal)<BR>
	 *  Throw NotFoundException if all the work is already completed in retrieved record and set 2 (Work already completed)<BR>
	 * </DIR>
	 * If the Collect Job no. of the fetched data is all same, change the status flag of fetched data to 2: in storage<BR>
	 * And with the next group condition, create relocation info entity array and return <BR>
	 * <DIR>
	 *  Consignor code, Consignor name,Location,Item code, Item name,<BR>
	 *  ITF, Bundle ITF,  Qty per case, Qty per bundle, Expiry date : one record<BR>
	 *  Work plan qty : Sum of fetched data<BR>
	 * </DIR>
	 * If there are multiple Collect Job no. in the retrieved data, group it and return as relocation entity array<BR>
	 * (Don't update status flag)<BR>
	 * <BR>
	 * @param	consignorCode	Consignor code
	 * @param	scanCode1		Scan code 1
	 * @param	scanCode2		Scan code 2
	 * @param	collectJobNo 	Collect Job no.
	 * @param	rftNo			RFT No.
	 * @param	workerCode		Worker code
	 * @return	Relocation info entity array
	 * @throws NotFoundException If data with work possibility does not exist
	 * @throws InvalidDefineException If the specified value is abnormal (empty, illegal characters)
	 * @throws OverflowException If the item count exceeds limit
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws ScheduleException If abnormal exception occurs during relocation storage start process
	 * @throws LockTimeOutException If the database lock is not removed after specified time
	*/
	public Movement[] startMovementStorage(
		String consignorCode,
		String scanCode1,
		String scanCode2,
		String collectJobNo,
		String rftNo,
		String workerCode)
		throws NotFoundException,InvalidDefineException, OverflowException, ReadWriteException, ScheduleException, LockTimeOutException
	{

		//#CM575577
		// Variable to store relocation info search result
		Movement[] movementData = null;
		//#CM575578
		// Variable to store relocation info after grouping
		Movement[] retMovementData = null;
	
		//#CM575579
		//-----------------
		//#CM575580
		// search relocation info  ( first time)
		//#CM575581
		//-----------------
		if (collectJobNo.trim().equals(""))
		{
			//#CM575582
			// If Collect Job no. is empty
			movementData = getMoveableData(consignorCode, scanCode1, scanCode2, rftNo, workerCode);
		}
		else{
			//#CM575583
			// If value is set for Collect Job no.
			movementData = getMoveableData(collectJobNo, rftNo, workerCode );
		}
		if (movementData == null)
		{
			//#CM575584
			// Process when there is no corresponding data in first time
			//#CM575585
			//-----------------
			//#CM575586
			// search relocation info  (2nd time)
			//#CM575587
			//-----------------
			if (collectJobNo.trim().equals(""))
			{
				//#CM575588
				// If Collect Job no. is empty
				movementData = getMovementData(consignorCode, scanCode1);
			}
			else{
				//#CM575589
				// If value is set for Collect Job no.
				movementData = getMovementData(collectJobNo);
			}
			
			//#CM575590
			// If target data does not exist set the status flag to 8: Target data does not exist
			if (movementData == null)
			{
				NotFoundException e = new NotFoundException(RFTId5740.ANS_CODE_NULL);
				throw e;
			}
			//#CM575591
			// If "in process" or "started" data exist in the target data set status flag to 1: In process in another terminal and return
			for (int i = 0; i < movementData.length; i++)
			{
				if (movementData[i].getStatusFlag().equals(Movement.STATUSFLAG_NOWWORKING))
				{
					NotFoundException e = new NotFoundException(RFTId5740.ANS_CODE_WORKING);
					throw e;
				}
			}
			//#CM575592
			// If there is no "in process" data, set status flag to 2: Work already completed and return it 
			NotFoundException e = new NotFoundException(RFTId5740.ANS_CODE_COMPLETION);
			throw e;
		}
		//#CM575593
		// Fetch worker name
		BaseOperate bo = new BaseOperate(wConn);
		String workerName = "";
		try
		{
			workerName = bo.getWorkerName(workerCode);
		}
		catch (NotFoundException e)
		{
			//#CM575594
			// Worker start with no worker code (worker master data is not available until data request)
			//#CM575595
			// 6026019=No matching worker data was found in the Worker Master Table. Worker Code: {0}
			RftLogMessage.print(6026019, LogMessage.F_ERROR, CLASS_NAME, workerCode);
			NotFoundException ex = new NotFoundException(RFTId5740.ANS_CODE_ERROR);
			throw ex;
		}
		
		//#CM575596
		// Collect relocation info
		retMovementData = collectMoveableMovementData(movementData);
		if (retMovementData.length == 1)
		{
			//#CM575597
			//-----------------
			//#CM575598
			// Update relocation info
			//#CM575599
			//-----------------
			MovementAlterKey movementAlterKey = new MovementAlterKey();
			//#CM575600
			// Set update conditions
			movementAlterKey.setCollectJobNo(retMovementData[0].getCollectJobNo());
			//#CM575601
			// Make target data if the status flag is "Waiting for storage" or "in process" (same Worker code and RFT no.)
			movementAlterKey.setStatusFlag(Movement.STATUSFLAG_UNSTART, "=", "(", "", "OR");
			movementAlterKey.setStatusFlag(Movement.STATUSFLAG_NOWWORKING, "=", "(", "", "AND");
			movementAlterKey.setWorkerCode(workerCode);
			movementAlterKey.setTerminalNo(rftNo, "=", "", "))", "AND");
			movementAlterKey.setBeginningFlag(Movement.BEGINNING_FLAG_STARTED);
			//#CM575602
			// Set update contents
			movementAlterKey.updateStatusFlag(Movement.STATUSFLAG_NOWWORKING);
			movementAlterKey.updateTerminalNo(rftNo);
			movementAlterKey.updateWorkerCode(workerCode);
			movementAlterKey.updateWorkerName(workerName);
			movementAlterKey.updateLastUpdatePname(PROCESS_NAME);

			MovementHandler movementHandler = new MovementHandler(wConn);
			try
			{
				//#CM575603
				// Update process
				movementHandler.modify(movementAlterKey);
			}
			catch (NotFoundException e)
			{
				String errData =
					"[ConsignorCode:" + consignorCode
						+ " itemCode:" + scanCode1
						+ " collectJobNo:" + collectJobNo
						+ " RftNo:" + rftNo
						+ " WorkerCode:" + workerCode
						+ "]";
				//#CM575604
				// 6026016=No data you try to update is found. {0}
				RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);
				//#CM575605
				// Return the Status flag as "in maintenance"
				NotFoundException ex = new NotFoundException(RFTId5740.ANS_CODE_ERROR);
				throw ex;
			}

		}
		return retMovementData;
	}

	//#CM575606
	/**
	 * This is the Relocation storage list file creat process
	 * @param  data		Relocation Work info
	 * @param  filename	Relocation storageList file name
	 * @throws IOException If there is any file input/output error
	 */
	public void createAnsFile(Movement[] data, String filename) throws IOException
	{
		//#CM575607
		// Create file writer stream
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename))) ;
		
		//#CM575608
		// Loop only the request count in the array
		for(int i = 0 ; i < data.length ; i++)
		{
			//#CM575609
			// Take out the required data and write to empty file
			//#CM575610
			// Relocation Job no.
			String moveJobNo = Idutils.createDataLeft(data[i].getCollectJobNo(), DataColumn.LEN_MOVE_JOB_NO) ;
			//#CM575611
			// Relocation origin area
			String sourceAreaNo = Idutils.createDataLeft(data[i].getAreaNo(), DataColumn.LEN_AREA_NO) ;
			//#CM575612
			// Origin location
			String sourceLocationNo = Idutils.createDataLeft(data[i].getLocationNo(), DataColumn.LEN_LOCATION) ;
			//#CM575613
			// Relocation storage possible qty
			String movementInstoreAbleQty = Idutils.createDataRight(data[i].getPlanQty(), DataColumn.LEN_PLAN_QTY) ;
			//#CM575614
			// Expiry date
			String useByDate = Idutils.createDataLeft(data[i].getUseByDate(), DataColumn.LEN_USE_BY_DATE) ;
			//#CM575615
			// Picking worker code
			String workerCode = Idutils.createDataLeft(data[i].getRetrievalWorkerCode(), DataColumn.LEN_WORKER_CODE) ;
			
			//#CM575616
			// Connect character string
			StringBuffer buffer = new StringBuffer() ;	// 文字列連結バッファ
			buffer.append(moveJobNo) ;					// 移動作業No.
			buffer.append(sourceAreaNo) ;				// 移動元エリアNo.
			buffer.append(sourceLocationNo) ;			// 移動元ロケーションNo.
			buffer.append(movementInstoreAbleQty) ;		// 移動入庫可能数
			buffer.append(useByDate) ;					// 賞味期限
			buffer.append(workerCode) ;					// 出庫作業者コード
			
			//#CM575617
			// Write to file in a new paragraph
			pw.println(buffer.toString()) ;
		}
		
		//#CM575618
		// Close file writer stream
		pw.close() ;
	}

	//#CM575619
	// Package methods -----------------------------------------------
	//#CM575620
	// Protected methods ---------------------------------------------
	//#CM575621
	/**
	 * Fetch Relocation storage possible data from relocation info<BR>
	 * Search using For Update option<BR>
	 * <BR>
	 * Search relocation info. Search next field items<BR>
	 * If corresponding data exist, return it. Else return empty array<BR>
	 * <DIR>
	 *  Status flag:1(Waiting for storage) or (2:in storage...with same Worker code and RFT no.)<BR>
	 *  Work start flag : 1(Started)<BR>
	 *  Consignor code:Fetch from parameter<BR>
	 *  Item code:Fetch from parameter<BR>
	 *  Area master info. Area type 2: Other than ASRS<BR>
	 * </DIR>
	 * <BR>
	 * @param	consignorCode	Consignor code
	 * @param	scanCode1 		Scan code 1
	 * @param	scanCode2 		Scan code 2
	 * @param	rftNo			RFT No.
	 * @param	workerCode		Worker code
	 * @return	Relocation info entity array
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws ScheduleException If abnormal exception occurs during relocation storage start process
	 * @throws LockTimeOutException If the database lock is not removed after specified time
	*/
	protected Movement[] getMoveableData(
		String consignorCode,
		String scanCode1,
		String scanCode2,
		String rftNo,
		String workerCode
		)
		throws ReadWriteException, ScheduleException, LockTimeOutException
	{
		//#CM575622
		// Array to store the return value
		Movement[] movementData	= null;
		
		MovementSearchKey movementKey = null;
		MovementHandler movementHandler = new MovementHandler(wConn);
		//#CM575623
		//-----------------
		//#CM575624
		// search relocation info  (first time)
		//#CM575625
		// Search using the scanned code as the JAN code
		//#CM575626
		//-----------------
		movementKey = getMovementSearchKey(consignorCode,workerCode,rftNo);
		movementKey.setItemCode(scanCode1);
		
		movementKey.setLocationNoOrder(1, true);
		movementKey.setUseByDateOrder(2, true);
		movementKey.setCollectJobNoOrder(3, true);
		
		//#CM575627
		// Start search
		try{
			movementData = (Movement[]) movementHandler.findForUpdate(movementKey,WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM575628
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNMOVEMENT");
			throw e;
		}

		//#CM575629
		// If there is no search result, return stock class array with request qty 0
		if (movementData != null && movementData.length > 0)
		{
			return movementData;
		}

		//#CM575630
		// If there is no search result, return stock class array with request qty 0
		if (movementData != null && movementData.length > 0)
		{
			return movementData;
		}
		
		//#CM575631
		//-----------------
		//#CM575632
		// search relocation info  (second time)
		//#CM575633
		// Search using the scanned code as Case ITF
		//#CM575634
		//-----------------
		movementKey = getMovementSearchKey(consignorCode,workerCode,rftNo);
		movementKey.setItf(scanCode1);
		
		movementKey.setLocationNoOrder(1, true);
		movementKey.setUseByDateOrder(2, true);
		movementKey.setCollectJobNoOrder(3, true);
		
		//#CM575635
		// Start search
		try{
			movementData = (Movement[]) movementHandler.findForUpdate(movementKey,WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM575636
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNMOVEMENT");
			throw e;
		}

		//#CM575637
		// If there is no search result, return stock class array with request qty 0
		if (movementData != null && movementData.length > 0)
		{
			return movementData;
		}

		//#CM575638
		// If there is no search result, return stock class array with request qty 0
		if (movementData != null && movementData.length > 0)
		{
			return movementData;
		}
		
		//#CM575639
		//-----------------
		//#CM575640
		// search relocation info  (third time)
		//#CM575641
		// Using the scanned code as Bundle ITF, start search
		//#CM575642
		//-----------------
		movementKey = getMovementSearchKey(consignorCode,workerCode,rftNo);
		movementKey.setBundleItf(scanCode1);
		
		movementKey.setLocationNoOrder(1, true);
		movementKey.setUseByDateOrder(2, true);
		movementKey.setCollectJobNoOrder(3, true);
		
		//#CM575643
		// Start search
		try{
			movementData = (Movement[]) movementHandler.findForUpdate(movementKey,WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM575644
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNMOVEMENT");
			throw e;
		}

		//#CM575645
		// If there is no search result, return stock class array with request qty 0
		if (movementData != null && movementData.length > 0)
		{
			return movementData;
		}

		//#CM575646
		// If there is no search result, return stock class array with request qty 0
		if (movementData != null && movementData.length > 0)
		{
			return movementData;
		}
		
		if(!StringUtil.isBlank(scanCode2))
		{
			//#CM575647
			//-----------------
			//#CM575648
			// search relocation info  (fourth time)
			//#CM575649
			// If ITF to JAN converted code is set
			//#CM575650
			// Search using that code as JAN code
			//#CM575651
			//-----------------
			movementKey = getMovementSearchKey(consignorCode,workerCode,rftNo);
			movementKey.setItemCode(scanCode2);
			
			movementKey.setLocationNoOrder(1, true);
			movementKey.setUseByDateOrder(2, true);
			movementKey.setCollectJobNoOrder(3, true);
			
			//#CM575652
			// Start search
			try{
				movementData = (Movement[]) movementHandler.findForUpdate(movementKey,WmsParam.WMS_DB_LOCK_TIMEOUT);
			}
			catch (LockTimeOutException e)
			{
				//#CM575653
				// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
				RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNMOVEMENT");
				throw e;
			}
	
			//#CM575654
			// If there is no search result, return stock class array with request qty 0
			if (movementData != null && movementData.length > 0)
			{
				return movementData;
			}
		}

		//#CM575655
		// If there is no corresponding data
		return null;
	}
	//#CM575656
	/**
	 * Fetch Relocation storage possible data from relocation info (Search using Collect Job no.)<BR>
	 * Search using For Update option<BR>
	 * <BR>
	 * Search using the following items<BR>
	 * If corresponding data exist, return it. Else return empty array<BR>
	 * <DIR>
	 *  Status flag:1(Waiting for storage) or (2:in storage...with same Worker code and RFT no.)<BR>
	 *  Work start flag : 1(Started)<BR>
	 *  Collect Job no.:Fetch from parameter<BR>
	 *  Area master info. Area type 2: Other than ASRS<BR>
	 * </DIR>
	 * <BR>
	 * @param	collectJobNo 	Collect Job no.
	 * @param	rftNo			RFT No.
	 * @param	workerCode		Worker code
	 * @return	Relocation info entity array
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws ScheduleException If abnormal exception occurs during relocation storage start process
	 * @throws LockTimeOutException If the database lock is not removed after specified time
	 */
	protected Movement[] getMoveableData(
		String collectJobNo,
		String rftNo,
		String workerCode
		)
		throws ReadWriteException, ScheduleException, LockTimeOutException
	{
		//#CM575657
		// Array to store the return value
		Movement[] movementData	= null;
		AreaOperator AreaOperator = new AreaOperator(wConn);
		
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
		
		MovementSearchKey movementKey = new MovementSearchKey();
		MovementHandler movementHandler = new MovementHandler(wConn);
		//#CM575658
		//-----------------
		//#CM575659
		// search relocation info
		//#CM575660
		//-----------------
		//#CM575661
		// Make target data if the status flag is "Waiting for storage" or "in process" (same Worker code and RFT no.)
		movementKey.setStatusFlag(Movement.STATUSFLAG_UNSTART, "=", "(", "", "OR");
		movementKey.setStatusFlag(Movement.STATUSFLAG_NOWWORKING, "=", "(", "", "AND");
		movementKey.setWorkerCode(workerCode);
		movementKey.setTerminalNo(rftNo, "=", "", "))", "AND");
		//#CM575662
		// Work start flag is [1:Started]
		movementKey.setBeginningFlag(Movement.BEGINNING_FLAG_STARTED);
		movementKey.setCollectJobNo(collectJobNo);

		//#CM575663
		// Fetch area other than ASRS and append to search condition
		//#CM575664
		// If there is no corresponding data, search with IS NULL
		areaNo = AreaOperator.getAreaNo(areaType);
		movementKey.setAreaNo(areaNo);
		
		//#CM575665
		// Start search
		try{
			movementData = (Movement[]) movementHandler.findForUpdate(movementKey,WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM575666
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNMOVEMENT");
			throw e;
		}

		//#CM575667
		// If there is no search result, return stock class array with request qty 0
		if (movementData == null || movementData.length == 0)
		{
			return null;
		}

		//#CM575668
		// Return the search result
		return movementData;
	}
	
	//#CM575669
	/**
	 * Fetch data with status other than Delete (9) from relocation info using the specified conditions<BR>
	 * <BR>
	 * Search relocation info. Search next field items<BR>
	 * If corresponding data exist, return it. Else return empty array<BR>
	 * <DIR>
	 *  Status flag:Other than 9 (Delete)<BR>
	 *  Work start flag : 1(Started)<BR>
	 *  Consignor code:Fetch from parameter<BR>
	 *  Item code:Fetch from parameter<BR>
	 *  Area master info. Area type 2: Other than ASRS<BR>
	 * </DIR>
	 * <BR>
	 * @param	consignorCode	Consignor code
	 * @param	itemCode1 		Item code
	 * @return	Relocation info entity array
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws ScheduleException If abnormal exception occurs during relocation storage start process
	 */
	protected Movement[] getMovementData(
		String consignorCode,
		String itemCode1
		)
		throws ReadWriteException, ScheduleException
	{
		//#CM575670
		// Array to store the return value
		Movement[] movementData	= null;
		AreaOperator AreaOperator = new AreaOperator(wConn);
		
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
		
		MovementSearchKey movementKey = new MovementSearchKey();
		MovementHandler movementHandler = new MovementHandler(wConn);
		//#CM575671
		//-----------------
		//#CM575672
		// search relocation info
		//#CM575673
		// Search using the scanned code as the JAN code
		//#CM575674
		//-----------------
		//#CM575675
		// Status flag other than [Delete]
		movementKey.setStatusFlag(Movement.STATUSFLAG_DELETE, "<>");
		//#CM575676
		// Work start flag is [1:Started]
		movementKey.setBeginningFlag(Movement.BEGINNING_FLAG_STARTED);
		movementKey.setConsignorCode(consignorCode);
		movementKey.setItemCode(itemCode1);
		
		//#CM575677
		// Fetch area other than ASRS and append to search condition
		//#CM575678
		// If there is no corresponding data, search with IS NULL
		areaNo = AreaOperator.getAreaNo(areaType);
		movementKey.setAreaNo(areaNo);
		
		movementKey.setLocationNoOrder(1, true);
		movementKey.setUseByDateOrder(2, true);
		
		//#CM575679
		// Start search
		movementData = (Movement[]) movementHandler.find(movementKey);

		//#CM575680
		// If there is no search result, return stock class array with request qty 0
		if (movementData != null && movementData.length > 0)
		{
			return movementData;
		}
		//#CM575681
		// Return the search result
		return null;
	}

	//#CM575682
	/**
	 * Fetch data with status other than Delete (9) from relocation info using the specified conditions(Search using Collect Job no.)<BR>
	 * <BR>
	 * Search using the following items
	 * If corresponding data exist, return it. Else return empty array
	 * <DIR>
	 *  Status flag:Other than 9 (Delete)<BR>
	 *  Work start flag : 1(Started)<BR>
	 *  Collect Job no.:Fetch from parameter<BR>
	 *  Area master info. Area type 2: Other than ASRS<BR>
	 * </DIR>
	 * <BR>
	 * @param	collectJobNo 	Collect Job no.<BR>
	 * @return	Relocation info entity array<BR>
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws ScheduleException If abnormal exception occurs during relocation storage start process
	*/
	protected Movement[] getMovementData(String collectJobNo) throws ReadWriteException, ScheduleException
	{
		//#CM575683
		// Array to store the return value
		Movement[] movementData	= null;
		AreaOperator AreaOperator = new AreaOperator(wConn);
		
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
		
		MovementSearchKey movementKey = new MovementSearchKey();
		MovementHandler movementHandler = new MovementHandler(wConn);
		//#CM575684
		//-----------------
		//#CM575685
		// search relocation info
		//#CM575686
		//-----------------
		//#CM575687
		// Status flag other than [Delete]
		movementKey.setStatusFlag(Movement.STATUSFLAG_DELETE, "<>");
		//#CM575688
		// Work start flag is [1:Started]
		movementKey.setBeginningFlag(Movement.BEGINNING_FLAG_STARTED);
		movementKey.setCollectJobNo(collectJobNo);
		
		//#CM575689
		// Fetch area other than ASRS and append to search condition
		//#CM575690
		// If there is no corresponding data, search with IS NULL
		areaNo = AreaOperator.getAreaNo(areaType);
		movementKey.setAreaNo(areaNo);
		
		//#CM575691
		// Start search
		movementData = (Movement[]) movementHandler.find(movementKey);

		//#CM575692
		// If there is no search result, return stock class array with request qty 0
		if (movementData == null || movementData.length == 0)
		{
			return null;
		}

		//#CM575693
		// Return the search result
		return movementData;
	}

	//#CM575694
	/**
	 * Collect relocation info data<BR>
	 * Gather data using Collect Job no. as the key<BR>
	 * While gathering  calculate Work plan qty (Relocation picking qty) and put others in array<BR>
	 * Return the gathered data in a Relocation info entity array<BR>
	 * @param	movementData	Relocation info entity array<BR>
	 * @return	Relocation info entity array<BR>
	 * @throws OverflowException If the item count exceeds limit
	 */
	protected Movement[] collectMoveableMovementData(Movement[] movementData) throws OverflowException
	{
		if (movementData.length == 0)
		{
			return new Movement[0];
		}
		//#CM575695
		// Vector for work use
		Vector workVec = new Vector();
		//#CM575696
		// Relocation info entity for work use
		Movement workMovement = null;
		workMovement = movementData[0];
		for (int i = 1; i < movementData.length; i++)
		{
			if (workMovement.getCollectJobNo().equals(movementData[i].getCollectJobNo()))
			{
				if (workMovement.getPlanQty() + movementData[i].getPlanQty() <= SystemParameter.MAXSTOCKQTY)
				{
					workMovement.setPlanQty(workMovement.getPlanQty() + movementData[i].getPlanQty());
				}
				else
				{
					//#CM575697
					// 6026028=Overflow occurred during record integration processing. Table Name: {0}
					RftLogMessage.print(6026028, LogMessage.F_ERROR, CLASS_NAME, "DNMOVEMENT");
					throw new OverflowException();
				}
			}
			else
			{
				workVec.addElement(workMovement);
				workMovement = movementData[i];
			}
		}
		workVec.addElement(workMovement);
		return (Movement[])workVec.toArray(new Movement[workVec.size()]);
	}

	//#CM575698
	/**
	 * Return search key that stores the conditions to fetch work data from Relocation storage<BR>
	 * Collect conditions are set by the caller<BR>
	 * <BR>
	 * (Search conditions)
	 * <UL>
	 *  <LI>Consignor code</LI>
	 *  <LI>Start flag = Started</LI>
	 *  <LI>Status flag = Standby or work in process in current terminal , worker</LI>
	 *  <LI>Worker code</LI>
	 *  <LI>terminal no. = RFT no.</LI>
	 *  <LI>Area no. = Other than AS/RS</LI>
	 * </UL>
	 * @param consignorCode		Consignor code
	 * @param workerCode			Worker code
	 * @param rftNo				terminal no.
	 * @return	Search key to search work data
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws ScheduleException If abnormal exception occurs during relocation storage start process
	 */
	public MovementSearchKey getMovementSearchKey(
	        String consignorCode,
	        String workerCode,
	        String rftNo) throws ReadWriteException, ScheduleException
	{
		MovementSearchKey movementKey = new MovementSearchKey();

		AreaOperator AreaOperator = new AreaOperator(wConn);
		
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
		
		//#CM575699
		// Make target data if the status flag is "Waiting for storage" or "in process" (same Worker code and RFT no.)
		movementKey.setStatusFlag(Movement.STATUSFLAG_UNSTART, "=", "(", "", "OR");
		movementKey.setStatusFlag(Movement.STATUSFLAG_NOWWORKING, "=", "(", "", "AND");
		movementKey.setWorkerCode(workerCode);
		movementKey.setTerminalNo(rftNo, "=", "", "))", "AND");
		//#CM575700
		// Work start flag is [1:Started]
		movementKey.setBeginningFlag(Movement.BEGINNING_FLAG_STARTED);
		movementKey.setConsignorCode(consignorCode);
		
		//#CM575701
		// Fetch area other than ASRS and append to search condition
		//#CM575702
		// If there is no corresponding data, search with IS NULL
		areaNo = AreaOperator.getAreaNo(areaType);
		movementKey.setAreaNo(areaNo);
		
	    return movementKey;
	}
	//#CM575703
	// Private methods -----------------------------------------------

}
//#CM575704
//end of class
