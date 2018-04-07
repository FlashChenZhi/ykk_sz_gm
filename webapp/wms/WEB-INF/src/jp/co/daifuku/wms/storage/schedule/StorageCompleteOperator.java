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
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * <BR>
 * This class allows to execute the process for completing the Storage process.<BR>
 * Receive the work No. and the process name and execute the process to complete the storage. (<CODE>complete()</CODE> method) <BR>
 * Search for the Work status based on Work No. and update the inventory information.<BR>
 * Use Process name for adding each table<BR>
 * This class executes the following processes.<BR>
 * If inventory package is available, execute the processes 1, 2, and 3. <BR>
 * If there is no inventory package, execute the processes 1 and 3. <BR>
 * <BR>
 * <DIR>
 *   1.Standby StorageUpdate Inventory Information Table (DMSTOCK). <BR>
 *   <DIR>
 *     Update the stock qty, Storage planned qty, Stock status, and the last Update process name. <BR>
 *   </DIR>
 *   <BR>
 *   2.Add or Update Inventory Information Table (DMSTOCK). <BR>
 *   <DIR>
 *     Search through the data using Location No., item code, stock status ( Center stock), Case/Piece division, expiry date, Consignor code. If the inventory information exist, lock the target record.<BR>
 *     Include expiry date in the search conditions if it is defined in the WmsParam as an item (count) that makes stock unique. <BR>
 *     <BR>
 *     2-1 When data exists<BR>
 *     Item Name, Inventory Qty, Storage Date/Time, Consignor Name, Packed qty per Case, Packed qty per bundle,Update the Case ITF, bundle ITF, and the last update process name. <BR>
 *     <BR>
 *     [Check for process condition] <BR>
 *     <BR>
 *     -Check Overflow.-<BR>
 *     <BR>
 *     2-2 When no data exists<BR>
 *     Add the inventory information based on the contents of the Received parameter. <BR>
 *     <BR>
 *   </DIR>
 *   3.Add Sending Result Info Table (DNHOSTSEND)(<CODE>AbstructStockControlSCH()</CODE>Class)<BR>
 *     <DIR>
 *     Generate entity of the Sending Result Info from Entity of Work status.<BR>
 *     Set the Work Date ( System defined date) and process name.<BR>
 *     Maintain the Stock ID of Work status same as that in Standby Storage Stock ID in inventory information. Therefore, maintain also the Stock ID of the Sending Result Info same as that in Standby Storage inventory information.<BR>
 *     </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>Y.Kubo</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/16 08:03:54 $
 * @author  $Author: suresh $
 */
public class StorageCompleteOperator extends AbstractStorageSCH
{
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/16 08:03:54 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public StorageCompleteOperator()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * Invoke this method in the Normal Storage process.<BR>
	 * Search for the Work status table based on Work No. and update the inventory information table.<BR>
	 * Generate a result in the Sending Result Info table.<BR>
	 * @param conn        Database connection object
	 * @param jobno       Work No.
	 * @param processname Process name
	 * @return Process result (normal：true)
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 * @throws OverflowException Announce when overflow occurred.
	 */
	public boolean complete(Connection conn, String jobno, String processname) throws ReadWriteException, ScheduleException, OverflowException
	{
		try
		{
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey workingsearchKey = new WorkingInformationSearchKey();

			// Search Data
			// Work No.
			workingsearchKey.setJobNo(jobno);

			WorkingInformation workinfo = (WorkingInformation) workingHandler.findPrimary(workingsearchKey);
			if (workinfo == null)
			{
				// 6006040 = Data mismatch occurred. See log.{0}
				RmiMsgLogClient.write("6006040" + wDelim + jobno, "StorageCompleteOperator");
				// Throw ScheduleException here.(Do not need to set error message.)
				throw (new ScheduleException());

			}
			// Stock ID
			String stockid = workinfo.getStockId();
			// Work Result qty
			int resultqty = workinfo.getResultQty();

			// Standby StorageUpdate Inventory Information Table (DMSTOCK).
			StockHandler stockHandler = new StockHandler(conn);
			StockAlterKey stockalterKey = new StockAlterKey();
			StockSearchKey stocksearchKey = new StockSearchKey();

			// Search Data
			// Stock ID
			stocksearchKey.setStockId(stockid);
			// Stock status(Standby Storage)
			stocksearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_RECEIVINGRESERVE);

			// Locking the data in Work status disable to lock any Standby Storage stock.
			Stock stock = (Stock)stockHandler.findPrimary(stocksearchKey);
			if (stock == null)
			{
				// 6006040 = Data mismatch occurred. {0} See log.{0}
				RmiMsgLogClient.write("6006040" + wDelim + stockid, "StorageCompleteOperator");
				// Throw ScheduleException here.(Do not need to set error message.)
				throw (new ScheduleException());
			}

			// Update the stock qty, Storage planned qty, Stock status, and the last Update process name.
			stockalterKey = new StockAlterKey();
			// Set the Stock ID.
			stockalterKey.setStockId(stockid);
			// Stock Qty
			stockalterKey.updateStockQty(stock.getStockQty() + resultqty);
			// Storage Planned Qty = Storage Planned Qty - (Work Result qty + Shortage Qty)
			int storageplanqty = stock.getPlanQty() - (resultqty + workinfo.getShortageCnt());
			stockalterKey.updatePlanQty(storageplanqty);

			// When Storage planned qty becomes 0, change the Stock status to Completed.
			if (storageplanqty <= 0)
			{
				stockalterKey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
			}
			// Last update process name
			stockalterKey.updateLastUpdatePname(processname);

			// Update Data.
			stockHandler.modify(stockalterKey);

			// Add or Update of Center inventory information table (DMSTOCK).

			// If inventory package is available:
			if (isStockPack(conn))
			{
				try
				{
					// Add the inventory information.
					// If stock data exists, sum up the stock qty (replenishment storage).
					// Generate a new inventory information if no stock data found. (new storage)
					processStockData(conn, resultqty, workinfo, processname);
				}
				catch(DataExistsException e)
				{
					// If create a new data in the same condition via other station,
					// Creating a new data at one terminal generates a unique restriction error at another terminal.
					// In such case, invoke the Stock Add process again and execute the replenishment storage.
					processStockData(conn, resultqty, workinfo, processname);
				}

				// Update the Location Info with Completed Storage.
				LocateOperator lOperator = new LocateOperator(conn);
				lOperator.modifyLocateStatus(workinfo.getResultLocationNo(), processname);
			}

			// Add Sending Result Info Table (DNHOSTSEND)

			// Generate entity of the Sending Result Info from Entity of Work status.
			// Set the Work Date ( System defined date) and process name.
			// Maintain the Stock ID of Work status same as that in Standby Storage Stock ID in inventory information. Therefore, maintain also the Stock ID of the Sending Result Info same as that in Standby Storage inventory information.
			HostSendHandler hostsendHandler = new HostSendHandler(conn);

			HostSend hostsend = new HostSend(workinfo, getWorkDate(conn), processname);

			// Data addition
			hostsendHandler.create(hostsend);

			return true;

		}
		catch (DataExistsException e)
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
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	/**
	 * Add or Update the Inventory Information table
	 * @param  conn        Instance to maintain database connection.
	 * @param  resultqty   Storage qty
	 * @param  workinfo    Instance of WorkingInformation class with contents of Work Status.
	 * @param  processname Process name
	 * @return process result (normal : true)
	 * @throws NoPrimaryException Announce when the defined information is abnormal.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws DataExistsException Announce this when the same Information has been already added.
	 * @throws OverflowException Announce when overflow occurs.

	 */
	protected boolean processStockData(
		Connection conn,
		int resultqty,
		WorkingInformation workinfo,
		String processname) throws NoPrimaryException, ReadWriteException, DataExistsException, OverflowException
	{
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey searchKey = new StockSearchKey();

		// Search Data
		searchKey.KeyClear();
		// Location No.
		searchKey.setLocationNo(workinfo.getResultLocationNo());
		// Item Code
		searchKey.setItemCode(workinfo.getItemCode());
		// Stock status(Center Inventory)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		// Case/Piece division(Load Size)
		searchKey.setCasePieceFlag(workinfo.getCasePieceFlag());
		// To use expiry date as a key to make a stock unique,
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			searchKey.setUseByDate(workinfo.getResultUseByDate());
		}
		// Consignor code
		searchKey.setConsignorCode(workinfo.getConsignorCode());

		//  Lock Data.
		Stock orgStock = (Stock) stockHandler.findPrimaryForUpdate(searchKey);

		if(resultqty != 0)
		{
			// If there is no stock data, (new storage)
			if (orgStock == null)
			{
				// Add Inventory Information Table (DMSTOCK).
				createStock(conn, resultqty, workinfo, processname);

			}
			// If stock data exists, (replenishment storage)
			else
			{
				// Update Inventory Information Table (DMSTOCK).
				updateStock(conn, resultqty, orgStock, workinfo, processname);
			}
		}
		return true;
	}

	/**
	 * Add the inventory information table.
	 * @param  conn        Instance to maintain database connection.
	 * @param  resultqty   Stock Qty
	 * @param  workinfo    Instance of WorkingInformation class with contents of Work Status.
	 * @param  processname Process name
	 * @return return true or false
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws DataExistsException Announce this when the same Information has been already added.
	 */
	protected boolean createStock(
		Connection conn,
		int resultqty,
		WorkingInformation workinfo,
		String processname) throws ReadWriteException, DataExistsException
	{
		try
		{
			StockHandler stockHandler = new StockHandler(conn);
			Stock stock = new Stock();

			// Obtain each unique key to add.
			SequenceHandler sequence = new SequenceHandler(conn);
			// Stock ID
			String stockId_seqno = sequence.nextStockId();
			stock.setStockId(stockId_seqno);
			// Plan unique key
			stock.setPlanUkey("");
			// Area No.
			try
			{
				// Obtain the Area No. from the location info.
				LocateOperator lOperator = new LocateOperator(conn);
				stock.setAreaNo(lOperator.getAreaNo(workinfo.getResultLocationNo()));
			}
			catch (ScheduleException e)
			{
				throw new ReadWriteException(e.getMessage());
			}
			// Location No.
			stock.setLocationNo(workinfo.getResultLocationNo());
			// Item Code
			stock.setItemCode(workinfo.getItemCode());
			// Item Name
			stock.setItemName1(workinfo.getItemName1());
			// Stock status(Center Inventory)
			stock.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			// stock qty (storage qty)
			stock.setStockQty(resultqty);
			// Allocatable Qty
			stock.setAllocationQty(resultqty);
			// Storage Planned Qty
			stock.setPlanQty(0);
			// Case/Piece division(Load Size)
			stock.setCasePieceFlag(workinfo.getCasePieceFlag());
			// Set the current date/time for storage date/time.
			Date instockdate = new Date();
			// Storage Date/Time
			stock.setInstockDate(instockdate);
			// Last Picking Date
			stock.setLastShippingDate("");
			// Expiry Date
			stock.setUseByDate(workinfo.getResultUseByDate());
			// Lot No.
			stock.setLotNo("");
			// Plan information Comment
			stock.setPlanInformation("");
			// Consignor code
			stock.setConsignorCode(workinfo.getConsignorCode());
			// Consignor name
			stock.setConsignorName(workinfo.getConsignorName());
			// Supplier Code
			stock.setSupplierCode("");
			// Supplier Name
			stock.setSupplierName1("");
			// Packed qty per Case
			stock.setEnteringQty(workinfo.getEnteringQty());
			// Packed qty per bundle
			stock.setBundleEnteringQty(workinfo.getBundleEnteringQty());
			// Case ITF
			stock.setItf(workinfo.getItf());
			// Bundle ITF
			stock.setBundleItf(workinfo.getBundleItf());
			// Name of Add Process
			stock.setRegistPname(processname);
			// Last update process name
			stock.setLastUpdatePname(processname);

			// Data addition
			stockHandler.create(stock);

			return true;
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	/**
	 * Update the inventory information table.
	 * @param  conn          Instance to maintain database connection.
	 * @param  resultQty	  Storage qty
 	 * @param  stock		  inventory information to be replenished
	 * @param  workinfo      Instance of WorkingInformation class with contents of Work Status.
	 * @param  processname   Process name
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected boolean updateStock(
			Connection conn,
			int resultQty,
			Stock stock,
			WorkingInformation workinfo,
			String processname) throws ReadWriteException, OverflowException
	{
		try
		{
			int totalStockQty = stock.getStockQty() + resultQty;
			// Check for overflow of the stock qty.
			if (totalStockQty > WmsParam.MAX_STOCK_QTY)
			{
				// 6026025 = Cannot set. Stock Qty. exceeds {0}.
				RmiMsgLogClient.write("6026025" + wDelim + MAX_STOCK_QTY_DISP, "StorageCompleteOperator");
				// Throw OverflowException here.(Do not need to set error message.)
				throw (new OverflowException());
			}

			StockHandler stockHandler = new StockHandler(conn);
			StockAlterKey alterKey = new StockAlterKey();

			// Item Name, Inventory Qty, Storage Date/Time, Consignor Name, Packed qty per Case, Packed qty per bundle,
			// Update the Case ITF, bundle ITF, and the last update process name.
			alterKey.setStockId(stock.getStockId());
			// Area No.
			try
			{
				// Obtain the Area No. from the location info.
				LocateOperator lOperator = new LocateOperator(conn);
				alterKey.updateAreaNo(lOperator.getAreaNo(workinfo.getResultLocationNo()));
			}
			catch (ScheduleException e)
			{
				throw new ReadWriteException(e.getMessage());
			}
			// Item Name
			alterKey.updateItemName1(workinfo.getItemName1());
			// Stock Qty
			alterKey.updateStockQty(totalStockQty);
			// Allocatable Qty
			alterKey.updateAllocationQty(stock.getAllocationQty() + resultQty);
			// Storage Date/Time
			alterKey.updateInstockDate(new Date());
			// Only when search conditions for inventory information includes no expiry date, update the expiry date.
			if (!WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				alterKey.updateUseByDate(workinfo.getResultUseByDate());
			}
			// Consignor name
			alterKey.updateConsignorName(workinfo.getConsignorName());
			// Packed qty per Case
			alterKey.updateEnteringQty(workinfo.getEnteringQty());
			// Packed qty per bundle
			alterKey.updateBundleEnteringQty(workinfo.getBundleEnteringQty());
			// Case ITF
			alterKey.updateItf(workinfo.getItf());
			// Bundle ITF
			alterKey.updateBundleItf(workinfo.getBundleItf());
			// Last update process name
			alterKey.updateLastUpdatePname(processname);
			// Update Data.
			stockHandler.modify(alterKey);

			return true;
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	// Private methods -----------------------------------------------

}
//end of class
