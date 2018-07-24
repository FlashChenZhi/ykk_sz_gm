package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;

/**
 * Designer : K.Mori <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * This class allows to allocate the stock.<BR>
 * Use this class commonly for WEB and RFT processes.
 * Use this class to allocate a stock for stock relocation picking, or inventory check work.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>K.Mori</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/16 07:59:29 $
 * @author  $Author: suresh $
 */
public class StockAllocateOperator extends Object
{
	// Class variables -----------------------------------------------
	/**
	 * Timeout period for locking the database.
	 */
	protected int wTimeOut = 0;

	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/16 07:59:29 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public StockAllocateOperator()
	{
		wTimeOut = 0;
	}

	/**
	 * Initialize this class.
	 * @param timer Timeout period for locking the database.
	 */
	public StockAllocateOperator(int timer)
	{
		wTimeOut = timer;
	}

	// Public methods ------------------------------------------------
	/**
	 * Search for stock using the designated condition.<BR>
	 * Use this to search a stock for Relocation Picking or Inventory Check.<BR>
	 * Return the searched stock list to the invoking source in the form of Stock class array.<BR>
	 * If two or more stocks exist, return them in the sequence of Case, Piece, and None.<BR>
	 * If no stock found using the designated condition, return the array of the Stock class with the number of elements equal to 0.<BR>
	 * Ensure to set Consignor Code, Location No., and Item Code at the Invoking Source.<BR>
	 * Unable to use any fuzzy search character.<BR>
	 * Improper character input or no value set returns ScheduleException<BR>
	 * If Expiry Date Control is defined as Enabled on WMS System parameter,<BR>
	 * include Expiry date in search conditions. If Expiry Date Control is no available, disable to use expiry date as search conditions.<BR>
	 * @param conn        Database connection object
	 * @param consignorCode Consignor codeNo.(Mandatory)
	 * @param locationNo Location No. (mandatory)
	 * @param itemCode Item Code (mandatory)
	 * @param useByDate Expiry Date
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws InvalidDefineException Announce this when wrong value is entered.
	 */
	public Stock[] stockSearch(Connection conn, String consignorCode, String areaNo, String locationNo, String itemCode, String useByDate)
		throws ReadWriteException, InvalidDefineException
	{
		// Check for Input. If improper input, return ScheduleException.
		checkString(consignorCode);
		checkString(locationNo);
		checkString(itemCode);

		StockSearchKey stockKey = new StockSearchKey();
		StockHandler stockHandler = new StockHandler(conn);
		Stock resultStock[] = null;

		// Set the search conditions.
		stockKey.setConsignorCode(consignorCode);
		stockKey.setLocationNo(locationNo);
		if (! StringUtil.isBlank(areaNo))
		{
			stockKey.setAreaNo(areaNo);
		}
		stockKey.setItemCode(itemCode);
		// If expiry date control is enabled, include expiry date in the search conditions.
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			// Accept blank even if Expiry Date Control is enabled.
			stockKey.setUseByDate(useByDate);
		}
		// Require to be Center Inventory.
		stockKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		stockKey.setCasePieceFlagOrder(1, true);

		// Execute search.
		resultStock = (Stock[]) stockHandler.find(stockKey);

		// Return stock class array of the number of elements equal to 0 if no search result.
		if (resultStock.length <= 0)
		{
			return new Stock[0];
		}

		return resultStock;
	}

	/**
	 * Search for stock using the designated condition and lock the searched data.<BR>
	 * Lock the record of the searched stock to avoid updating by other process.<BR>
	 * Control unlocking at the invoking source.<BR>
	 * Return the searched stock list to the invoking source in the form of Stock class array.<BR>
	 * If two or more stocks exist, return them in the sequence of Case, Piece, and None.<BR>
	 * If no stock found using the designated condition, return the array of the Stock class with the number of elements equal to 0.<BR>
	 * Require the invoking Source to set the Consignor Code, Location No. (multiple designation acceptable), and Item Code.<BR>
	 * Unable to use any fuzzy search character.<BR>
	 * Improper character input or no value set returns ScheduleException<BR>
	 * If Expiry Date Control is defined as Enabled on WMS System parameter,<BR>
	 * include Expiry date in search conditions. If Expiry Date Control is no available, disable to use expiry date as search conditions.<BR>
	 * @param conn        Database connection object
	 * @param consignorCode Consignor codeNo.(Mandatory)
	 * @param locationNo Location No. (mandatory)
	 * @param itemCode Item Code (mandatory)
	 * @param useByDate Expiry Date
	 * @return Stock[] Array of the inventory that matches the search conditions.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws InvalidDefineException Announce this when wrong value is entered.
	 * @throws LockTimeOutException Announce it when the lock has not been unlocked after the designated time passed.
	 */
	public Stock[] stockSearchForUpdate(Connection conn, String consignorCode, String[] locationNo, String itemCode, String useByDate)
		throws ReadWriteException, LockTimeOutException, InvalidDefineException
	{
		// Check for Input. If improper input, return ScheduleException.
		checkString(consignorCode);
		for (int i = 0; i < locationNo.length; i++)
		{
			checkString(locationNo[i]);
		}
		checkString(itemCode);

		StockSearchKey stockKey = new StockSearchKey();
		StockHandler stockHandler = new StockHandler(conn);
		Stock resultStock[] = null;

		// Set the search conditions.
		stockKey.setConsignorCode(consignorCode);
		stockKey.setLocationNo(locationNo);
		stockKey.setItemCode(itemCode);
		// If expiry date control is enabled, include expiry date in the search conditions.
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			// Accept blank even if Expiry Date Control is enabled.
			stockKey.setUseByDate(useByDate);
		}
		//  Require to be Center Inventory.
		stockKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		stockKey.setCasePieceFlagOrder(1, true);

		// Execute search.
		if (wTimeOut != 0)
		{
			resultStock = (Stock[]) stockHandler.findForUpdate(stockKey, wTimeOut);
		}
		else
		{
			resultStock = (Stock[]) stockHandler.findForUpdate(stockKey);

		}
		// Return stock class array of the number of elements equal to 0 if no search result.
		if (resultStock == null || resultStock.length == 0)
		{
			return new Stock[0];
		}

		return resultStock;
	}

	/**
	 * Allocate the Stock using the designated condition and lock the record.<BR>
	 * Control unlocking at the invoking source.<BR>
	 * Use this method to allocate for Stock Relocation picking.<BR>
	 * Return the searched stock list to the invoking source in the form of Stock class array.<BR>
	 * Allocate the stock in the sequence of Case, Piece, and None, if two or more stocks found.<BR>
	 * <U>Require to allow the invoking source to know the allocated qty. So,
	 * set the allocated qty this time for the ALLOCATIONQTY of the stock to be returned to the Invoking Source.</U><BR>
	 * If no stock found using the designated condition, return the array of the Stock class with the number of elements equal to 0.<BR>
	 * When total stock qty does not satisfy the allocated qty even if the stock exists when searched using Consignor Code, Location No., and Item Code (expiry date if Expiry Date Control is enabled).<BR>
	 * return the array of the number of elements equal to 0.<BR>
	 * Ensure to set Consignor Code, Location No., and Item Code at the Invoking Source.<BR>
	 * Unable to use any fuzzy search character.<BR>
	 * Improper character input or no value set returns ScheduleException<BR>
	 * If Expiry Date Control is defined as Enabled on WMS System parameter,<BR>
	 * include Expiry date in search conditions. If Expiry Date Control is no available, disable to use expiry date as search conditions.<BR>
	 * <BR>
	 * For the system not installed with inventory control package,
	 * generate Stock instance using the designated condition instead of searching the stock, and return it to the invoking source.
	 * Then, set Case for Case/Piece division in such case.<BR>
	 * <BR>
	 * This method executes processes in the sequence as below.<BR>
	 * <DIR>
	 *   -If inventory package is not available:
	 *   <DIR>
	 *     1.Generate Stock instance and return it.
	 *   </DIR>
	 *   -If inventory package is available:
	 *   <DIR>
	 *     1.Search for stock using the designated condition and lock it.<BR>
	 *     2.If there is no stock that corresponds to the designated condition, return the array of the number of elements 0.<BR>
	 *     3.Check whether the obtained stock satisfies the allocated qty.<BR>
	 *     4.If the obtained stock does not satisfy the Allocatable qty, return 0 array as the number of elements.<BR>
	 *     5.Execute allocating process and update the DmStock table.<BR>
	 *     6.Set the allocated qty for AllocateQty of the array of the updated stock, for convenience, and return it.<BR>
	 *   </DIR>
	 * </DIR>
	 * @param conn        Database connection object
	 * @param consignorCode Consignor codeNo.(Mandatory)
	 * @param locationNo Location No. (mandatory)
	 * @param itemCode Item Code (mandatory)
	 * @param useByDate Expiry Date
	 * @param movePlanQty planned relocation qty
	 * @return Stock[] Array of Updated inventory information
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws InvalidDefineException Announce this when wrong value is entered.
	 * @throws LockTimeOutException Announce it when the lock has not been unlocked after the designated time passed.
	 */
	public Stock[] stockMovementAllocate(
		Connection conn,
		String consignorCode,
		String locationNo,
		String itemCode,
		String useByDate,
		long movePlanQty,
		String processName)
		throws ReadWriteException, LockTimeOutException, InvalidDefineException
	{
		StockHandler stockHandler = new StockHandler(conn);
		StockAlterKey stockKey = new StockAlterKey();
		Stock movementStock[] = null;

		WareNaviSystemSearchKey wmsKey = new WareNaviSystemSearchKey();
		WareNaviSystemHandler wmsHandler = new WareNaviSystemHandler(conn);
		WareNaviSystem wms[] = null;
		wms = (WareNaviSystem[]) wmsHandler.find(wmsKey);

		// Generate stock instance to generate Relocation work status, for no inventory package, and return it
		if (!wms[0].getStockPack().equals(WareNaviSystem.PACKAGE_FLAG_ADDON))
		{
			// Check the strings and generate the Stock instance.
			movementStock = new Stock[1];
			movementStock[0] = createStockInstance(consignorCode, locationNo, itemCode, useByDate, (int)movePlanQty);
			return movementStock;
		}

		// Search for the stock and execute the process, if inventory package is available.
		// Check the strings here and lock the data and search.
		String[] checkLocationNo = { locationNo };
		Stock wStock[] = stockSearchForUpdate(conn, consignorCode, checkLocationNo, itemCode, useByDate);

		// Disable to process if no stock corresponding to the designated condition.
		if (wStock.length == 0)
		{
			return wStock;
		}

		// Obtain the total Allocatable qty.
		long sumPossibleQty = 0;

		for (int i = 0; i < wStock.length; i++)
		{
			// Sum up the allocatable qty of each stock.
			sumPossibleQty += wStock[i].getAllocationQty();
		}

		// If stock qty is shorted, disable to process. Return the array of the number of elements equal to 0.
		if (sumPossibleQty < movePlanQty)
		{
			return new Stock[0];
		}

		try
		{
			Vector resultVec = new Vector();
			// Allocate the obtained stock.
			for (int i = 0; i < wStock.length; i++)
			{
				// Allocated qty for each stock
				int planQty = 0;

				// Allocatable qty of each stock
				int allocationQty = wStock[i].getAllocationQty();
				// If Allocatable qty is 0 or smaller, skip to read this stock.
				if (allocationQty <= 0)
				{
					continue;
				}

				// If Allocatable qty exceeds the Total allocated qty, allocate the total allocated qty to this stock.
				if (allocationQty > movePlanQty)
				{
					planQty = (int)movePlanQty;
				}
				// If Allocatable qty is equal to the total allocated qty or smaller, allocate the Allocatable qty to this stock.
				else
				{
					planQty = allocationQty;
				}
				// Update the stock (Replenish the allocated qty).
				stockKey.KeyClear();
				stockKey.setStockId(wStock[i].getStockId());
				// Update Allocatable Qty.
				stockKey.updateAllocationQty(wStock[i].getAllocationQty() - planQty);
				stockKey.updateLastUpdatePname(processName);
				stockHandler.modify(stockKey);

				// Set only this time Allocated qty for Allocated qty for convenience.
				// Use this to set it for the planned qty of the Relocation work status. (No added to DB)
				wStock[i].setAllocationQty(planQty);
				// Set the Updated Stock for an array.
				resultVec.add(wStock[i]);

				// Decrease the planned relocation qty
				movePlanQty -= planQty;

				// Go out of the loop when the allocation completed.
				if (movePlanQty == 0)
				{
					break;
				}

			}
			movementStock = new Stock[resultVec.size()];
			resultVec.copyInto(movementStock);

		}
		catch (InvalidDefineException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (NotFoundException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		return movementStock;
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
			// Record the log.
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

	/**
	 * Generate Stock instance to generate Relocation work status using a condition designated by the parameter.<BR>
	 * Disable to add to DB.<BR>
	 * This method processes as below.<BR>
	 * <DIR>
	 *   1.Check for input (mandatory input or forbidden character)<BR>
	 *   2.Generate a stock instance.<BR>
	 * </DIR>
	 * Consignor Code, Location No., Item Code are mandatory.<BR>
	 * Enabling expiry date control requires to input Expiry date as mandatory item.<BR>
	 *
	 * @param consignorCode Consignor code(Mandatory)
	 * @param locationNo Location No.(Mandatory)
	 * @param itemCode Item Code (mandatory)
	 * @param useByDate Expiry Date
	 * @param movePlanQty planned relocation qty
	 * @return Instance of Stock class
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws InvalidDefineException Announce this exception when error occurs in the input string.
	 */
	private Stock createStockInstance(String consignorCode, String locationNo, String itemCode, String useByDate, int movePlanQty)
		throws ReadWriteException, InvalidDefineException
	{
		// Stock instance for generating Relocation work status create.
		Stock movementStock = new Stock();

		try
		{
			// Check for input.
			checkString(consignorCode);
			checkString(locationNo);
			checkString(itemCode);

			// Location No.
			movementStock.setLocationNo(locationNo);
			// Item Code
			movementStock.setItemCode(itemCode);
			// Stock status
			movementStock.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			// Stock Qty (= planned relocation qty)
			movementStock.setStockQty(movePlanQty);
			// Allocated qty. Store the allocated qty in the data storage specific to Allocatable qty, in order to generate a Relocation work status of the Invoking Source.
			movementStock.setAllocationQty(movePlanQty);
			// Case/Piece division(Load Size):Case
			movementStock.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
			// Expiry Date
			movementStock.setUseByDate(useByDate);
			//Consignor code
			movementStock.setConsignorCode(consignorCode);

		}
		catch (InvalidStatusException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		return movementStock;
	}

}