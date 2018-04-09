// $Id: Id0631Operate.java,v 1.2 2006/09/27 03:00:39 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.rft;
//#CM9470
/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ShelfInvalidityException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM9471
/**
 * Designer : K.Shimizu <BR>
 * Maker : K.Shimizu <BR>
 * <BR>
 * Execute processing Unplanned storage result sending from RFT.<BR>
 * Update Stock info.<BR>
 * Implement the Business logic call up from Id0151Process.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/15</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/09/27 03:00:39 $
 * @author  $Author: suresh $
 */
public class Id0631Operate extends IdOperate
{
	//#CM9472
	// Class variables -----------------------------------------------
	//#CM9473
	/**
	 * Process name
	 */
	private static final String PROCESS_NAME = "ID0631";
	//#CM9474
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0631Operate";
	
	//#CM9475
	/**
	 * error detail
	 */
	private String errorDetail = RFTId5631.ErrorDetails.NORMAL;

	//#CM9476
	// Class method --------------------------------------------------
	//#CM9477
	/**
	 * Return the version of this class.<BR>
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/09/27 03:00:39 $";
	}
	//#CM9478
	// Constructors --------------------------------------------------
	//#CM9479
	// Public methods ------------------------------------------------
	//#CM9480
	/**
	 * Execute the process for Unplanned storage result sending.<BR>
	 * Execute submitted processing.<BR>
	 * <BR>
	 * @param	workerCode		Worker Code
	 * @param	rftNo			RFT number
	 * @param  stock           Stock infoEntity（maintenance of electronic statement content）
	 * @param  wrokaTime		Work time
	 * @return					BallITFResponse flag
	*/
	public String doComplete(
		String workerCode,
		String rftNo,
		Stock stock,
		int workTime)
	{
		try
		{
			//#CM9481
			//-----------------
			//#CM9482
			// check in daily process 
			//#CM9483
			//-----------------
			//#CM9484
			// Generate BaseOperate instance
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);

			if (baseOperate.isLoadingDailyUpdate())
			{
				//#CM9485
				// Return Response flag 5: Executing daily Update processing
				return RFTId5631.ANS_CODE_DAILY_UPDATING;
			}

			//#CM9486
			// Execute complete process
			noplanStorageComplete(
					workerCode,
					rftNo,
					stock,
					workTime);

			wConn.commit();
		}
		catch (LockTimeOutException e)
		{			
			//#CM9487
			// SELECT FOR UPDATE Time-out
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				//#CM9488
				// 6006002=Database error occurred.{0}{0}
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9489
			// Response flag：maintaining in process at other work stationit
			return RFTId5631.ANS_CODE_MAINTENANCE ;
		}
		//#CM9490
		// In the case search info is not found
		catch (NotFoundException e)
		{
			String errData = "[ConsignorCode:" + stock.getConsignorCode() +
							" ItemCode:" + stock.getItemCode() +
							" LocationNo:" + stock.getLocationNo() +
							" CasePieceFlag:" + stock.getCasePieceFlag() +
							" UseByDate:" + stock.getUseByDate() +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
			//#CM9491
			// 6026016=No data you try to update is found.{0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9492
			// error detail
			errorDetail = RFTId5631.ErrorDetails.NULL;
			//#CM9493
			// Response flag：error
			return RFTId5631.ANS_CODE_ERROR ;
		}
		//#CM9494
		// In the case error is generated in data access
		catch (ReadWriteException e)
		{
			//#CM9495
			// 6006002=Database error occurred.{0}{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9496
			// error detail
			if(errorDetail.equals(RFTId5631.ErrorDetails.NORMAL))
			{
				errorDetail = RFTId5631.ErrorDetails.DB_ACCESS_ERROR;
			}

			//#CM9497
			// Response flag：error
			return RFTId5631.ANS_CODE_ERROR ;
		}
		//#CM9498
		// In the case overflow error generated when create stock 
		catch (OverflowException e)
		{
			String errData = "[ConsignorCode:" + stock.getConsignorCode() +
							" ItemCode:" + stock.getItemCode() +
							" LocationNo:" + stock.getLocationNo() +
							" CasePieceFlag:" + stock.getCasePieceFlag() +
							" UseByDate:" + stock.getUseByDate() +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
			//#CM9499
			// 6026027=Length overflow. Process was aborted.{0}
			RftLogMessage.print(6026027, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9500
			// error detail
			errorDetail = RFTId5631.ErrorDetails.OVERFLOW;
			//#CM9501
			// Response flag：error
			return RFTId5631.ANS_CODE_ERROR ;
		}
		//#CM9502
		// In the case other error exists
		catch (InvalidDefineException e)
		{
			//#CM9503
			// 6026022=Blank or prohibited character is included in the specified value.{0}
			RftLogMessage.print(6026022, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9504
			// error detail
			errorDetail = RFTId5631.ErrorDetails.PARAMETER_ERROR;
			//#CM9505
			// Response flag：error
			return RFTId5631.ANS_CODE_ERROR;
		}
		catch (DataExistsException e)
		{
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9506
			// Response flag：error
			errorDetail =  RFTId5631.ErrorDetails.DB_UNIQUE_KEY_ERROR;
			return  RFTId5631.ANS_CODE_ERROR;
		}
		catch (UpdateByOtherTerminalException e)
		{
			//#CM9507
			// 6026017=Cannot update. The data you try to update was updated in other process. {0}
			RftLogMessage.printStackTrace(6026017, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9508
			// Response flag：error
			errorDetail =  RFTId5631.ErrorDetails.UPDATE_FINISH;
			return  RFTId5631.ANS_CODE_ERROR;
		}
		catch (SQLException e)
		{
			//#CM9509
			// 6006002=Database error occurred.{0}{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9510
			// error detail		
			errorDetail = RFTId5631.ErrorDetails.DB_ACCESS_ERROR;
			//#CM9511
			// Response flag：error
			return RFTId5631.ANS_CODE_ERROR ;
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "InstockReceiveOperate", e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9512
			// error detail
			errorDetail = RFTId5631.ErrorDetails.INSTACIATE_ERROR;
			return RFTId5631.ANS_CODE_ERROR;
		}
		catch (InvalidStatusException e)
		{
			//#CM9513
			// 6026015=Error occurred during ID process.{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9514
			// error detail
			errorDetail = RFTId5631.ErrorDetails.DB_INVALID_VALUE;
			return RFTId5631.ANS_CODE_ERROR;
		}
		catch (ScheduleException e)
		{
			//#CM9515
			// 6006001=Unexpected error occurred.{0}{0}
			RftLogMessage.print(6006001, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9516
			// error detail
			errorDetail = RFTId5631.ErrorDetails.SCHEDULE_ERROR;
			return RFTId5631.ANS_CODE_ERROR;
		}
		catch (ShelfInvalidityException e)
		{
		    String errData = "[ConsignorCode:" + stock.getConsignorCode() +
							" ItemCode:" + stock.getItemCode() +
							" LocationNo:" + stock.getLocationNo() +
							" CasePieceFlag:" + stock.getCasePieceFlag() +
							" UseByDate:" + stock.getUseByDate() +
		    				" RftNo:" + rftNo +
							" WorkerCode:" + workerCode + "]";
		    
			//#CM9517
			// 6022039=The specified location is in automatic warehouse. Cannot enter. {0}
			RftLogMessage.print(6022039, LogMessage.F_ERROR, CLASS_NAME, errData);

			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6023443, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9518
			// error detail
			errorDetail = RFTId5631.ErrorDetails.SHELF_INVALIDITY;
			return RFTId5631.ANS_CODE_ERROR;
		}
		catch (Exception e)
		{
			//#CM9519
			// 6026015=Error occurred during ID process.{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);

			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9520
			// error detail
			errorDetail = RFTId5631.ErrorDetails.INTERNAL_ERROR;			
			//#CM9521
			// Response flag：error
			return RFTId5631.ANS_CODE_ERROR ;
		}

		
		return RFTId5631.ANS_CODE_NORMAL;
	}

	//#CM9522
	/**
	 * Unplanned storage Result sending [Submit process]<BR>
	 * Unplanned storage Result data inquiry<BR>
	 * <BR>
	 * Lock corresponding data of Stock info. (For exclusion process)<BR>
	 * when time out is generated on locking(in such case as duplicating with allocation of exclusion process)<BR>
	 * Return the response for the error (Allocation in process at other terminal).<BR>
	 * Search through the AS/RS location info via storage location. If corresponding data exist (if assigned to the location in an automated warehouse)<BR>
	 * Return the response for the error (Invalid location entered).<BR>
	 * Add stock qty to Update Stock info.<BR>
	 *  (Replenish it if the same stock exists<BR>
	 * Update corresponding location info to storage location.<BR>
	 * Generate sending Result Info of Unplanned storage completed.<BR>
	 * Search and Worker Result data inquiry update corresponding data.<BR>
	 * <BR>
	 * Search AS/RS location info (Invoke common location search method)<BR>
	 * <BR>
	 * Lock Stock info<BR>
	 * <DIR>
	 *    (search conditions)
	 *    <UL>
	 *    <LI>Location No.=Location of the electronic statement</LI>
	 *    <LI>Item code=JANCode of the electronic statement</LI>
	 *    <LI>Stock status=2:Center Inventory</LI>
	 *    <LI>Expiry Date=Expiry Date of the electronic statement</LI>
	 *    <LI>Consignor code=Consignor code of the electronic statement</LI>
	 *    </UL>
	 * </DIR>
	 * <BR>
	 * Update Stock info (Invoke Common storage method)<BR>
	 * create sending Result Info<BR>
	 * <BR>
	 * Update the Worker Result data inquiry<BR>
	 * <BR>
	 * @param	workerCode		Worker Code
	 * @param	rftNo			RFT number
	 * @param  stock 			Stock infoEntity
	 * @param	workTime		Work time
	 * @throws NotFoundException Announce this when update target data is not found.
	 * @throws InvalidDefineException Announce this when specified value is abnormal (Blank, including prohibited characters).
	 * @throws ReadWriteException Announce this when error generated in connection with database
	 * @throws DataExistsException Announce this when trying to add the same Information already added.
	 * @throws UpdateByOtherTerminalException Announce this when the corresponding data was updated by other terminal.
	 * 			Finding no corresponding data causes NotFoundException depending on the updated contents.
	 * @throws LockTimeOutException Announce this when database lock is not released for specified time.
	 * @throws OverflowException Announce this when digit number of numeric item overflows.
	 * @throws ShelfInvalidityException Announce this when input location is invalid.
	 * @throws ScheduleException check Announce this when unexpected exception occurs in the process.
	 * @throws InvalidStatusException Announce this when any discrepancy is found in the setting value of table update.
	*/
	public void noplanStorageComplete(
			String workerCode,
			String rftNo,
			Stock stock,
			int workTime)
		throws 
			NotFoundException, 
			InvalidDefineException, 
			ReadWriteException, 
			DataExistsException, 
			UpdateByOtherTerminalException,
			LockTimeOutException,
			OverflowException, InvalidStatusException, ScheduleException, ShelfInvalidityException
	{
		
		String stockId = null;

		LocateOperator lOperator = new LocateOperator(wConn);

		if(lOperator.isAsrsLocation(stock.getLocationNo()))
		{
			//#CM9523
			// 6023443=No.{0} The specified location is in automatic warehouse. Cannot enter.
			throw new ShelfInvalidityException();
		}

		if (SystemParameter.withStockManagement())
		{
			
			//#CM9524
			// Lock of Stock info
			Stock findStock = stockSearchForUpdate(stock);
			if (findStock == null)
			{
				//#CM9525
				// new submit/add of Stock info
				stockId = createStock(stock);
			}
			else
			{
				//#CM9526
				// update of Stock info
				stockId = findStock.getStockId();
				updateStock(stock, findStock.getStockId(), findStock.getStockQty(), findStock.getAllocationQty());
			}
		}
		
		//#CM9527
		// Update
		lOperator.modifyLocateStatus(stock.getLocationNo(), PROCESS_NAME);
		//#CM9528
		// Set the area.
		stock.setAreaNo(lOperator.getAreaNo(stock.getLocationNo()));
		
		//#CM9529
		// update (create) of Worker result
		createWorkerResult(workerCode, rftNo, stock.getStockQty(), workTime);
			
		//#CM9530
		// Add of sending Result Information
		createHostsend(stockId, stock, workerCode, rftNo);
	}
	
	//#CM9531
	// Package methods -----------------------------------------------

	//#CM9532
	// Protected methods ---------------------------------------------
	
	//#CM9533
	// Private methods -----------------------------------------------
	//#CM9534
	/**
	 * Update Worker result.
	 * <DIR>
	 *    (Updated contents)
	 *    <TABLE>
	 *      <TR><TD>Work completion date and time</TD>	<TD>Current system date</TD></TR>
	 *      <TR><TD>Work division</TD>		<TD>21:Unplanned storage</TD></TR>
	 *      <TR><TD>Work Quantity</TD>		<TD>+Unplanned Storage Result file</TD></TR>
	 *      <TR><TD>Work No.</TD>		<TD>+1 </TD></TR>
	 *      <TR><TD>Work No.(Order No.)<TD>+1</TD></TR>
	 *      <TR><TD>Work time</TD>		<TD>Work time of the electronic statement</TD></TR>
	 *      <TR><TD>Actual Work time</TD>		<TD>Work time of the electronic statement</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * 
	 * @param	workerCode		Worker Code
	 * @param	rftNo			RFT number
	 * @param	resultQty		Move picking Result file
	 * @param	workTime		Work time
	 * @throws ReadWriteException Announce this when error generated in connection with database
	 */
	private void createWorkerResult(
			String workerCode, 
			String rftNo, 
			int resultQty,
			int workTime) throws ReadWriteException
	{
		BaseOperate bo = new BaseOperate(wConn);
		String workingDate = "";
		try
		{
			workingDate = bo.getWorkingDate();
		}
		catch (NotFoundException e)
		{
			//#CM9535
			// Failing to obtain Work Date from the System definition info
			//#CM9536
			// Throw ReadWriteException
			errorDetail = RFTId5631.ErrorDetails.NULL;
			throw (new ReadWriteException());
		}

		//#CM9537
		// update result per Worker
		WorkerResult wr = new WorkerResult();
		wr.setWorkDate(workingDate);
		wr.setWorkerCode(workerCode);
		wr.setTerminalNo(rftNo);
		wr.setJobType(WorkerResult.JOB_TYPE_EX_STORAGE);
		wr.setWorkCnt(1);
		wr.setOrderCnt(1);
		wr.setWorkQty(resultQty);
		wr.setWorkTime(workTime);
		wr.setRealWorkTime(workTime);
		
		try
		{
			bo.alterWorkerResult(wr);
		}
		catch (NotFoundException e)
		{
			//#CM9538
			// Create new when update target Worker result is not found
			//#CM9539
			// （In such case that daily update is processed during RFT work）
			//#CM9540
			// 6022004=Create new when no matching Worker Result Data is found.{0}
			String errData = "[WorkDate:" + workingDate +
			 				 " JobType:" + WorkerResult.JOB_TYPE_EX_STORAGE +
							 " RftNo:" + rftNo +
							 " WorkerCode:" + workerCode +"]";
			RftLogMessage.print(6022004, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				//#CM9541
				// Worker result create
				bo.createWorkerResult(WorkerResult.JOB_TYPE_EX_STORAGE, workerCode, rftNo);
				//#CM9542
				// Worker result update
				bo.alterWorkerResult(wr);
			}
			catch(NotFoundException e1)
			{
				errorDetail = RFTId5631.ErrorDetails.NULL;
				throw (new ReadWriteException());
			}
			catch (Exception e1)
			{
				//#CM9543
				// Throw ReadWriteException when exception occurs.
				throw (new ReadWriteException());
			}
		}
	}

	//#CM9544
	/**
	 * <BR>
	 * Lock Stock info<BR>
	 *   <DIR>
	 *     Search data with Consignor code, Location No., Item Code, Stock status (center stock), Load size, 
	 *     expiry date (in such case that item (count) is defined as WmsParam to make the stock unique).
	 *     <BR>
	 *     In the case data exists<BR>
	 *	   Lock the corresponding Stock info and return True.<BR>
	 *     <BR>
	 *     Return false when no data exists.<BR>
	 *     <BR>
	 *   </DIR>
	 * <BR>
	 * @param	stock Stock infoEntity         
	 * @throws ReadWriteException Announce this when error generated in connection with database.
	 * @throws LockTimeOutException Announce this when database lock is not released for specified time.
	 * @throws DataExistsException Announce this when trying to add the same Information already added.
	 */
	private Stock stockSearchForUpdate(Stock stock) throws ReadWriteException, LockTimeOutException, DataExistsException
	{
		//#CM9545
		// Generate Instance of handlers for inventory information.
		StockHandler stockHandler = new StockHandler(wConn);
		StockSearchKey searchKey = new StockSearchKey();

		//#CM9546
		// Set search condition
		//#CM9547
		// Consignor code
		searchKey.setConsignorCode(stock.getConsignorCode());
		//#CM9548
		// Location No.
		searchKey.setLocationNo(stock.getLocationNo());
		//#CM9549
		// Item code
		searchKey.setItemCode(stock.getItemCode());
		//#CM9550
		// Stock status＝Center Inventory
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM9551
		// Case/Piece division(Load size)
		//#CM9552
		// Not Designated
		if(stock.getCasePieceFlag().equals(Stock.CASEPIECE_FLAG_NOTHING))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
		}
		//#CM9553
		// Case
		else if(stock.getCasePieceFlag().equals(Stock.CASEPIECE_FLAG_CASE))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
		}
		//#CM9554
		// Piece
		else if(stock.getCasePieceFlag().equals(Stock.CASEPIECE_FLAG_PIECE))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
		}
		//#CM9555
		// Expiry Date
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			if (!StringUtil.isBlank(stock.getUseByDate()))
			{
				searchKey.setUseByDate(stock.getUseByDate());
			}
			else
			{
				searchKey.setUseByDate("", "");
			}
		}
		
		//#CM9556
		// Lock the data.
		int timeout = WmsParam.WMS_DB_LOCK_TIMEOUT;
		Stock findStock[] = (Stock[])stockHandler.findForUpdate(searchKey, timeout);
		
		if (findStock.length == 1)
		{
			return findStock[0];
		}
		else if (findStock.length > 1)
		{
			//#CM9557
			// Regard multiple presence of the target stock as an error.
			throw new DataExistsException();
		}

		return null;
	}

	//#CM9558
	/**
	 * <BR>
	 * Add Stock info.<BR>
	 * @param	stock	Stock infoEntity
	 * @throws ReadWriteException Announce this when error generated in connection with database
	 * @throws InvalidStatusException Announce this when specified value is abnormal (Blank, including prohibited characters).
	 * @throws DataExistsException DataExistsException Announce this when trying to add the same Information already added.
	 * @throws ScheduleException ScheduleException check Process内でIt is informed when unexpected exception occurs.
	 */
	protected String createStock(Stock stock) throws ReadWriteException, InvalidStatusException, DataExistsException, ScheduleException
	{

			StockHandler stockHandler = new StockHandler(wConn);

			//#CM9559
			// Obtain each unique key needed to add.
			SequenceHandler sequence = new SequenceHandler(wConn);
			//#CM9560
			// Stock ID
			String stockId_seqno = sequence.nextStockId();
			stock.setStockId(stockId_seqno);
			//#CM9561
			// Area No.
			//#CM9562
			// Obtain the Area No. from the location info.(2005/06/14 Add By:T.T)
			LocateOperator lOperator = new LocateOperator(wConn);
			stock.setAreaNo(lOperator.getAreaNo(stock.getLocationNo()));
			
			//#CM9563
			// Stock status(Center Inventory)
			stock.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM9564
			// Allocatable Qty
			stock.setAllocationQty(stock.getStockQty());
			//#CM9565
			// Storage Planned Qty
			stock.setPlanQty(0);
			//#CM9566
			// on Storage Date
			stock.setInstockDate(new Date());
			//#CM9567
			// Submit/Add process name
			stock.setRegistPname(PROCESS_NAME);
			//#CM9568
			// last Update process name
			stock.setLastUpdatePname(PROCESS_NAME);
			//#CM9569
			// Add data
			stockHandler.create(stock);

			return stockId_seqno;
	}

	//#CM9570
	/**
	 * <BR>
	 * Update Stock info.<BR>
	 * @param	stock	 Stock infoEntity
	 * @param  stockId  Stock ID
	 * @param  stockQty Stock qty
	 * @param  allocationQty Allocatable Qty
	 * @throws ReadWriteException Announce this when error generated in connection with database
	 * @throws OverflowException Announce this when digit number of numeric item overflows.
	 */
	protected boolean updateStock(Stock stock, String stockId, int stockQty, int allocationQty) throws ReadWriteException, OverflowException
	{
		try
		{		
			//#CM9571
			// Execute overflow check of stock qty
			if (WmsParam.MAX_STOCK_QTY < (long)stockQty + (long)stock.getStockQty())
			{
				//#CM9572
				// Throw OverflowException here
				throw (new OverflowException());
			}
			
			//#CM9573
			// Update item name
			//#CM9574
			// Disable to update the allocated qty and the storage planned qty for the data in process of allocation likely to be updated.

            //#CM9575
            // Generate Instance of handlers for inventory information.			
			StockHandler stockHandler = new StockHandler(wConn);
			StockAlterKey alterKey = new StockAlterKey();
			
			//#CM9576
			// Set the Update condition.
			//#CM9577
			// Stock ID
			alterKey.setStockId(stockId);
		    //#CM9578
		    // Set the update value.
			//#CM9579
			// Item name
			alterKey.updateItemName1(stock.getItemName1());
			//#CM9580
			// Stock qty
			alterKey.updateStockQty(stockQty + stock.getStockQty());
			//#CM9581
			// Allocatable Qty
			alterKey.updateAllocationQty(allocationQty + stock.getStockQty());
			//#CM9582
			// on Storage Date
			alterKey.updateInstockDate(new Date());
			//#CM9583
			// search conditions of Stock info
			if (!WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				alterKey.updateUseByDate(stock.getUseByDate());
			}
			//#CM9584
			// Consignor name
			alterKey.updateConsignorName(stock.getConsignorName());
			//#CM9585
			// Packed qty per case
			alterKey.updateEnteringQty(stock.getEnteringQty());
			//#CM9586
			// packed qty per bundle
			alterKey.updateBundleEnteringQty(stock.getBundleEnteringQty());
			//#CM9587
			// Case ITF
			alterKey.updateItf(stock.getItf());
			//#CM9588
			// Bundle ITF
			alterKey.updateBundleItf(stock.getBundleItf());
			//#CM9589
			// last Update process name
			alterKey.updateLastUpdatePname(PROCESS_NAME);

			//#CM9590
			// Update the data.
			stockHandler.modify(alterKey);

			return true;
			
		}
		catch (InvalidDefineException e)
		{
			errorDetail = RFTId5631.ErrorDetails.PARAMETER_ERROR;
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			errorDetail = RFTId5631.ErrorDetails.NULL;
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			errorDetail = RFTId5631.ErrorDetails.DB_ACCESS_ERROR;
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	//#CM9591
	/**
	 * Add sending Result Info table (DNHOSTSEND)<BR> 
	 * <BR>     
	 * sending Result Information is added based on the contents of the received. <BR>
	 * <BR>
	 * @param stockid     Stock ID
	 * @param stock  	   Stock infoEntity
	 * @param workerCode  Worker Code
	 * @param rftNo		   RFT number
	 * @throws ReadWriteException Announce this when error generated in connection with database
	 * @throws ScheduleException  Announce this when unexpected exception occurs in the schedule.
	 */
	protected boolean createHostsend(
		String stockid,
        Stock stock,
        String workerCode,
        String rftNo)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM9592
			// Generate instance for sending Result Info handlers.
			HostSendHandler hostsendHandler = new HostSendHandler(wConn);
			HostSend hostsend = new HostSend();
			BaseOperate bo = new BaseOperate(wConn);

			//#CM9593
			// Obtain each unique key needed to add.
			SequenceHandler sequence = new SequenceHandler(wConn);
			//#CM9594
			// Work No.
			String job_seqno = sequence.nextJobNo();
			//#CM9595
			// Work Date
			hostsend.setWorkDate(bo.getWorkingDate());
			//#CM9596
			// Work No.
			hostsend.setJobNo(job_seqno);
			//#CM9597
			// Work division
			hostsend.setJobType(HostSend.JOB_TYPE_EX_STORAGE);
			//#CM9598
			// Aggregation Work No. (equal to Work No.)
			hostsend.setCollectJobNo(job_seqno);
			//#CM9599
			// Status flag (Completed)
			hostsend.setStatusFlag(HostSend.STATUS_FLAG_COMPLETION);
			//#CM9600
			// Start Work flag (Already Started)
			hostsend.setBeginningFlag(HostSend.BEGINNING_FLAG_STARTED);
			//#CM9601
			// Plan unique key
			hostsend.setPlanUkey("");
			//#CM9602
			// Stock ID
			hostsend.setStockId(stockid);
			//#CM9603
			// Area No.
			hostsend.setAreaNo(stock.getAreaNo());
			//#CM9604
			// Location No.
			hostsend.setLocationNo(stock.getLocationNo());
			//#CM9605
			// Planned date
			hostsend.setPlanDate(bo.getWorkingDate());
			//#CM9606
			// Consignor code
			hostsend.setConsignorCode(stock.getConsignorCode());
			//#CM9607
			// Consignor name
			hostsend.setConsignorName(stock.getConsignorName());
			//#CM9608
			// Supplier code
			hostsend.setSupplierCode("");
			//#CM9609
			// Supplier name
			hostsend.setSupplierName1("");
			//#CM9610
			// Receiving ticketNo.
			hostsend.setInstockTicketNo("");
			//#CM9611
			// Receiving ticketLine No.
			hostsend.setInstockLineNo(0);
			//#CM9612
			// Customer code
			hostsend.setCustomerCode("");
			//#CM9613
			// Customer name
			hostsend.setCustomerName1("");
			//#CM9614
			// Shipping Ticket No
			hostsend.setShippingTicketNo("");
			//#CM9615
			// Shipping ticket line No.
			hostsend.setShippingLineNo(0);
			//#CM9616
			// Item code
			hostsend.setItemCode(stock.getItemCode());
			//#CM9617
			// Item name
			hostsend.setItemName1(stock.getItemName1());
			//#CM9618
			// Planned work qty(Host Planned Qty)
			hostsend.setHostPlanQty(0);
			//#CM9619
			// Planned work qty
			hostsend.setPlanQty(0);
			//#CM9620
			// possible Work Qty
			hostsend.setPlanEnableQty(0);
			//#CM9621
			// Work Result file
			hostsend.setResultQty(stock.getStockQty());
			//#CM9622
			// work shortage qty
			hostsend.setShortageCnt(0);
			//#CM9623
			// Pending Qty
			hostsend.setPendingQty(0);
			//#CM9624
			// Packed qty per case
			hostsend.setEnteringQty(stock.getEnteringQty());
			//#CM9625
			// packed qty per bundle
			hostsend.setBundleEnteringQty(stock.getBundleEnteringQty());
			//#CM9626
			// Case/Piece division(Load size)
			hostsend.setCasePieceFlag(stock.getCasePieceFlag());
			//#CM9627
			// Case/Piece division(Work type)
			hostsend.setWorkFormFlag(stock.getCasePieceFlag());
			//#CM9628
			// Case ITF
			hostsend.setItf(stock.getItf());
			//#CM9629
			// Bundle ITF
			hostsend.setBundleItf(stock.getBundleItf());
			//#CM9630
			// TC/DC division
			hostsend.setTcdcFlag(HostSend.TCDC_FLAG_DC);
			//#CM9631
			// Expiry Date
			hostsend.setUseByDate(stock.getUseByDate());
			//#CM9632
			// Lot No.
			hostsend.setLotNo("");
			//#CM9633
			// Plan information Comment
			hostsend.setPlanInformation("");
			//#CM9634
			// Order No.
			hostsend.setOrderNo("");
			//#CM9635
			// Order Date
			hostsend.setOrderingDate("");
			//#CM9636
			// Expiry Date
			hostsend.setResultUseByDate(stock.getUseByDate());
			//#CM9637
			// Lot No.
			hostsend.setResultLotNo("");
			//#CM9638
			// Work Result Location
			hostsend.setResultLocationNo(stock.getLocationNo());
			//#CM9639
			// Work Report flag (Standby reporting)
			hostsend.setReportFlag(HostSend.REPORT_FLAG_NOT_SENT);
			//#CM9640
			// Batch No(Schedule No.)
            String batch_seqno = sequence.nextNoPlanBatchNo();
			hostsend.setBatchNo(batch_seqno);
			AreaOperator areaOpe = new AreaOperator(wConn);
			//#CM9641
			// System distinct key
			hostsend.setSystemDiscKey(Integer.parseInt(areaOpe.getAreaType(stock.getAreaNo())));
			//#CM9642
			// Worker Code
			hostsend.setWorkerCode(workerCode);
			//#CM9643
			// Worker Name
			hostsend.setWorkerName(bo.getWorkerName(workerCode));
			//#CM9644
			// Terminal No., RFTNo.
			hostsend.setTerminalNo(rftNo);
			//#CM9645
			// Plan information Added Date/Time
			hostsend.setPlanRegistDate(null);
			//#CM9646
			// Added Date/Time
			hostsend.setRegistDate(new Date());
			//#CM9647
			// Submit/Add process name
			hostsend.setRegistPname(PROCESS_NAME);
			//#CM9648
			// last update date/time
			hostsend.setLastUpdateDate(new Date());
			//#CM9649
			// last Update process name
			hostsend.setLastUpdatePname(PROCESS_NAME);

			//#CM9650
			// Add data
			hostsendHandler.create(hostsend);

			return true;

		}
		catch (DataExistsException e)
		{
			errorDetail = RFTId5631.ErrorDetails.DB_UNIQUE_KEY_ERROR;
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			errorDetail = RFTId5631.ErrorDetails.DB_INVALID_VALUE;
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			errorDetail = RFTId5631.ErrorDetails.DB_ACCESS_ERROR;
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			errorDetail = RFTId5631.ErrorDetails.NULL;
			throw new ReadWriteException(e.getMessage());
		}
		catch (NumberFormatException e) 
		{
			throw new ScheduleException(e.getMessage());
		} 
	}

	//#CM9651
	/**
	 * Obtain error detail
	 * 
	 * @return	error detail
	 */
	public String getErrorDetails()
	{
		return errorDetail;
	}
}
//#CM9652
//end of class
