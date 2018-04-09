package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.MovementHandler;
import jp.co.daifuku.wms.base.dbhandler.MovementSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.master.operator.AreaOperator;

/**
 * Designer : K.Mori <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * This class allows to execute the process for completing the Stock Relocation.<BR>
 * Use this class commonly for WEB and RFT processes.<BR>
 * Decrease the stock qty in the relocation picking target and generate a stock in the relocation storage target.
 * If any stock exist in the relocation storage target, count the stock qty and.
 * If no data, create a new data.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>K.Mori</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/12 07:00:18 $
 * @author  $Author: suresh $
 */
public class MovementCompleteOperator extends AbstractStorageSCH
{
	// Class variables -----------------------------------------------
	/**
	 * Timeout period for locking the database.
	 */
	protected int wTimeOut = 0;

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/12 07:00:18 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public MovementCompleteOperator()
	{
		wTimeOut = 0;
	}

	/**
	 * Initialize this class.
	 * @param timer Timeout period for locking the database.
	 */
	public MovementCompleteOperator(int timer)
	{
		wTimeOut = timer;
	}

	// Public methods ------------------------------------------------
	/**
	 * This method executes Complete process for relocating a stock.<BR>
	 * Execute the process for completing the Stock Relocation Storage for the designated Work No.<BR>
	 * For the system not installed with inventory control package,
	 * only update the Relocation work status and add the Host sending result data, instead of updating the stock.<BR>
	 * Before invoking this method, lock the target stock and update the Stock Relocation work.<BR>
	 * This method processes as below.<BR>
	 * <DIR>
	 * 1. Load the Stock Relocation work status.<BR>
	 * 2. If inventory control package is available, update the inventory information of the Relocation source.<BR>
	 * 3. If inventory control package is available, update or add the inventory information of the Relocation target.<BR>
	 * 4. Add the Sending Result Info.<BR>
	 * </DIR>
	 * @param conn Database connection object
	 * @param jobNo Work No. of the Relocation work Status
	 * @param processname Process name
	 * @return boolean Return whether the process normally completed
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws OverflowException Announce when overflow occurred.
	 */
	public boolean complete(Connection conn, String jobNo, String processname) throws
			ReadWriteException, OverflowException, ScheduleException
	{
		try
		{
			// Load DnMovement.
			MovementSearchKey moveKey = new MovementSearchKey();
			MovementHandler moveHandler = new MovementHandler(conn);
			Movement move = null;

			moveKey.setJobNo(jobNo);
			// Obtain the Relocation info.
			move = (Movement) moveHandler.findPrimary(moveKey);

			// If inventory control package is available,
			if (isStockPack(conn))
			{
				// Execute the process for updating the stock of the Relocation source and the Relocation target.
				if(!updateStock(conn, move, processname))
				{
					return false;
				}
			}
			//Obtain the Work Date from System definition info.
			BaseOperate bo = new BaseOperate(conn);
			String workingDate = "";
			try
			{
				workingDate = bo.getWorkingDate();
			}
			catch (NotFoundException e)
			{
				// Failing to obtain Work Date from the System definition info,
				// throw ReadWriteException.
				throw (new ReadWriteException());
			}

			// Add the Sending Result Info.
			HostSendHandler hostSendHandler = new HostSendHandler(conn);
			HostSend hostSend = createHostSend(conn, move,workingDate, processname);

			hostSendHandler.create(hostSend);

		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (DataExistsException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		return true;
	}

	/**
	 * Search for the stock of the Relocation source and the Relocation target and lock it.<BR>
	 * Use this in the process for relocation stock and Complete Storage.<BR>
	 * Disable to add expiry date to the search conditions if no Expiry Date Control.<BR>
	 *
	 * @param conn Database connection object
	 * @param stockId Stock ID ( Relocation source stock ) (mandatory)
	 * @param toLocation Relocation Target Location No. (mandatory)
	 * @param itemCode Item Code(Mandatory)
	 * @param consignorCode Consignor Code (mandatory)
	 * @param useByDate Expiry Date (mandatory when Expire Date control is available)
	 * @return Stock[] Array of the locked Stock.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws InvalidDefineException Announce this when wrong value is entered.
	 * @throws LockTimeOutException Announce it when lock was not unlocked after the designated time passed.
	 */
	public Stock[] movementSearchForUpdate(Connection conn, String stockId, String toLocation, String itemCode, String consignorCode, String useByDate)
		throws ReadWriteException, LockTimeOutException, InvalidDefineException
	{
		checkString(stockId);
		checkString(toLocation);
		checkString(itemCode);
		checkString(consignorCode);

		StockSearchKey stockKey = new StockSearchKey();
		StockHandler stockHandler = new StockHandler(conn);
		Stock stock[] = null;

		stockKey.setStockId(stockId, "=", "", "", "OR");
		stockKey.setLocationNo(toLocation, "=", "(", "", "AND");
		stockKey.setItemCode(itemCode);
		stockKey.setConsignorCode(consignorCode);
		//If expiry date control is enabled, include expiry date in the search conditions.
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			stockKey.setUseByDate(useByDate);
		}
		stockKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED, "=", "", ")", "AND");

		// If the period for Timout is designated,
		if (wTimeOut == 0)
		{
			stock = (Stock[]) stockHandler.findForUpdate(stockKey);
		}
		//If time is None,
		else
		{
			stock = (Stock[]) stockHandler.findForUpdate(stockKey, wTimeOut);
		}

		return stock;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * Update the Relocation source stock and the Relocation target stock in Relocation work.<BR>
	 * This method processes as below.<BR>
	 * <DIR>
	 *   1.Search for the Relocation source stock and update it.<BR>
	 *   2.Search for the Relocation target in the same stock.<BR>
	 *   3.Replenish if the same stock is also in the Relocation target.<BR>
	 *   4.Create a new stock if no same stock in the Relocation target.<BR>
	 * </DIR>
	 * Disable to add expiry date to the search conditions if no Expiry Date Control.<BR>
	 *
	 * @param conn Connection with database
	 * @param move Relocation work Status
	 * @param processname Process name
	 * @throws ReadWriteException Announce when exception occurred in data connection.
	 * @throws OverflowException Announce when overflow occurs.
	 */
	protected boolean updateStock(Connection conn, Movement move, String processname) throws ReadWriteException, OverflowException
	{
		try
		{
			//Load the DmStock.
			StockAlterKey stockAltKey = new StockAlterKey();
			StockSearchKey stockKey = new StockSearchKey();
			StockHandler stockHandler = new StockHandler(conn);
			Stock sourceStock = null;

			//// Update the Relocation source inventory information. ////
			// Search for the Relocation source stock
			stockKey.setStockId(move.getStockId());
			sourceStock = (Stock) stockHandler.findPrimary(stockKey);
			// Delete 0 Stock
			if ((sourceStock.getStockQty() - move.getPlanQty()) == 0)
			{
				stockKey.KeyClear();
				stockKey.setStockId(sourceStock.getStockId());
				stockHandler.drop(stockKey);
			}
			else
			{
				// Set the search conditions and update info.
				stockAltKey.setStockId(sourceStock.getStockId());

				// Subtract the planned qty of the Relocation work status from the stock qty of the Relocation source stock.
				stockAltKey.updateStockQty(sourceStock.getStockQty() - move.getPlanQty());
				stockAltKey.updateLastUpdatePname(processname);
				stockHandler.modify(stockAltKey);

			}
			// Update the location info of the Relocation source.
			LocateOperator lOperator = new LocateOperator(conn);
			lOperator.modifyLocateStatus(sourceStock.getLocationNo(), processname);

			//// Update the relocation target inventory information. ////
			// Search for presence of Relocation target stock.
			stockKey.KeyClear();
			stockKey.setLocationNo(move.getResultLocationNo());
			stockKey.setItemCode(move.getItemCode());
			stockKey.setCasePieceFlag(move.getCasePieceFlag());
			stockKey.setConsignorCode(move.getConsignorCode());
			// If expiry date control is enabled, include expiry date in the search conditions.
			if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				if (!StringUtil.isBlank(move.getResultUseByDate()))
				{
					stockKey.setUseByDate(move.getResultUseByDate());
				}
				else
				{
					stockKey.setUseByDate("" , "");
				}

			}
			stockKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);

			//// Update if any stock exists. ////
			if (stockHandler.count(stockKey) > 0)
			{
				Stock destStock[] = (Stock[]) stockHandler.find(stockKey);

				//Check the Relocation target stock for overflow.
				if ((long) destStock[0].getStockQty() + (long) move.getPlanQty() > WmsParam.MAX_STOCK_QTY)
				{
					//6023344=Cannot relocate. Stock Qty. in Destination Location will exceed {0}.
					RmiMsgLogClient.write("6023344" + wDelim + MAX_STOCK_QTY_DISP, this.getClass().getName());
					// 6023344=Cannot relocate. Stock Qty. in Destination Location will exceed {0}.
					throw new OverflowException("6023344" + wDelim + MAX_STOCK_QTY_DISP);
				}


				// Clear the search conditions.
				stockAltKey.KeyClear();
				stockAltKey.setStockId(destStock[0].getStockId());
				// Increase the stock qty in the Relocation target stock.
				stockAltKey.updateStockQty(destStock[0].getStockQty() + move.getPlanQty());
				// Increase the Allocatable qty of the Relocation target stock.
				stockAltKey.updateAllocationQty(destStock[0].getAllocationQty() + move.getPlanQty());
				stockAltKey.updateLastUpdatePname(processname);
				stockHandler.modify(stockAltKey);
			}
			//// Create for a new stock.////
			else
			{
				SequenceHandler seqHandler = new SequenceHandler(conn);
                String stockId_seqno = seqHandler.nextStockId();
				Stock destStock = createStock(stockId_seqno, sourceStock.getInstockDate(), processname, move);
				destStock.setAreaNo(lOperator.getAreaNo(destStock.getLocationNo()));
				stockHandler.create(destStock);
			}

			// Update location info of the Relocation target.
			lOperator.modifyLocateStatus(move.getResultLocationNo(), processname);
		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (InvalidDefineException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (NotFoundException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (DataExistsException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (ScheduleException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		return true;
	}

	/**
	 * Generate new stock instance based on the Relocation work status.<BR>
	 * <BR>
	 * @param stockId Stock ID
	 * @param instockDate Storage Date
	 * @param processname Process name
	 * @param move Relocation work Status
	 * @return Stock Stock instance containing information for new creation
	 * @throws ReadWriteException Announce when exception occurred in data connection.
	 */
	protected Stock createStock(String stockId, Date instockDate, String processname, Movement move) throws ReadWriteException
	{
		Stock stock = new Stock();

		try
		{
			stock.setStockId(stockId);
			stock.setAreaNo(move.getAreaNo());
			stock.setLocationNo(move.getResultLocationNo());
			stock.setItemCode(move.getItemCode());
			stock.setItemName1(move.getItemName1());
			stock.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			stock.setStockQty(move.getPlanQty());
			stock.setAllocationQty(move.getPlanQty());
			stock.setPlanQty(0);
			stock.setCasePieceFlag(move.getCasePieceFlag());
			stock.setInstockDate(instockDate);
			stock.setUseByDate(move.getResultUseByDate());
			stock.setLotNo(move.getResultLotNo());
			stock.setPlanInformation(move.getPlanInformation());
			stock.setConsignorCode(move.getConsignorCode());
			stock.setConsignorName(move.getConsignorName());
			stock.setSupplierCode(move.getSupplierCode());
			stock.setSupplierName1(move.getSupplierName1());
			stock.setEnteringQty(move.getEnteringQty());
			stock.setBundleEnteringQty(move.getBundleEnteringQty());
			stock.setItf(move.getItf());
			stock.setBundleItf(move.getBundleItf());
			stock.setRegistPname(processname);
			stock.setLastUpdatePname(processname);

		}
		catch (InvalidStatusException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		return stock;
	}

	// Private methods -----------------------------------------------
	/**
	 * Generate a sending Result Info instance from the Stock Relocation Info.<BR>
	 *
	 * @param move stock relocation info
	 * @param workingDate Work Date
	 * @param processname Process name
	 * @return HostSend Sending Result Information
	 * @throws ReadWriteException Announce when exception occurred in data connection.
	 */
	private HostSend createHostSend(Connection conn, Movement move, String workingDate,String processname)
		throws ReadWriteException, ScheduleException
	{
		HostSend hostSend = new HostSend();
		try
		{
			hostSend.setWorkDate(workingDate);
			hostSend.setPlanDate(move.getWorkDate());
			hostSend.setJobNo(move.getJobNo());
			hostSend.setJobType(move.getJobType());
			hostSend.setCollectJobNo(move.getCollectJobNo());
			hostSend.setStatusFlag(Movement.STATUS_FLAG_COMPLETION );
			hostSend.setBeginningFlag(move.getBeginningFlag());
			hostSend.setStockId(move.getStockId());
			hostSend.setAreaNo(move.getAreaNo());
			hostSend.setLocationNo(move.getLocationNo());
			hostSend.setConsignorCode(move.getConsignorCode());
			hostSend.setConsignorName(move.getConsignorName());
			hostSend.setSupplierCode(move.getSupplierCode());
			hostSend.setSupplierName1(move.getSupplierName1());
			hostSend.setItemCode(move.getItemCode());
			hostSend.setItemName1(move.getItemName1());
			hostSend.setPlanQty(move.getPlanQty());
			hostSend.setResultQty(move.getResultQty());
			hostSend.setShortageCnt(move.getShortageCnt());
			hostSend.setEnteringQty(move.getEnteringQty());
			hostSend.setBundleEnteringQty(move.getBundleEnteringQty());
			hostSend.setCasePieceFlag(move.getCasePieceFlag());
			hostSend.setWorkFormFlag(move.getWorkFormFlag());
			hostSend.setItf(move.getItf());
			hostSend.setBundleItf(move.getBundleItf());
			hostSend.setTcdcFlag(HostSend.TCDC_FLAG_DC);
			hostSend.setUseByDate(move.getUseByDate());
			hostSend.setLotNo(move.getLotNo());
			hostSend.setPlanInformation(move.getPlanInformation());
			hostSend.setResultUseByDate(move.getResultUseByDate());
			hostSend.setResultLotNo(move.getResultLotNo());
			hostSend.setResultLocationNo(move.getResultLocationNo());
			hostSend.setReportFlag(HostSend.REPORT_FLAG_NOT_SENT);
			hostSend.setBatchNo(move.getBatchNo());

			AreaOperator areaOpe = new AreaOperator(conn);
			// set system key
			hostSend.setSystemDiscKey(Integer.parseInt(areaOpe.getAreaType(move.getAreaNo())));

			hostSend.setWorkerCode(move.getWorkerCode());
			hostSend.setWorkerName(move.getWorkerName());
			hostSend.setTerminalNo(move.getTerminalNo());
			hostSend.setPlanRegistDate(move.getRegistDate());
			hostSend.setRegistPname(processname);
			hostSend.setLastUpdatePname(processname);

		}
		catch (InvalidStatusException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (NumberFormatException e)
		{
			throw new ScheduleException(e.getMessage());
		}

		return hostSend;
	}

	/**
	 * This method checks for mandatory input and forbidden character in the entered string.<BR>
	 * Return InvalidDefineException if the entered string is null or empty, or if contains forbidden character.<BR>
	 *
	 * @param str String to be checked
	 * @throws InvalidDefineException Return InvalidDefineException if the entered string is null or empty, or if contains forbidden character.
	 */
	private void checkString(String str) throws InvalidDefineException
	{
		// Throw Exception if the string is blank or null
		if (StringUtil.isBlank(str))
		{
			//  Record the log.
			// 6006023= Cannot update database. No condition to update is set. TABLE={0}
			RmiMsgLogClient.write("6006023" + wDelim + "DnMovement", this.getClass().getName());
			throw new InvalidDefineException("6006023" + wDelim + "DnMovement");
		}
		// Throw Exception if the string includes forbidden character.
		if (str.indexOf(WmsParam.PATTERNMATCHING) != -1)
		{
			// Record the log.
			// 6003106= A prohibition character is contained in {0}.
			RmiMsgLogClient.write("6003106" + wDelim + str, this.getClass().getName());
			throw new InvalidDefineException("6003106" + wDelim + str);
		}
	}

}
//end of class