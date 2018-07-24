// $Id: Id0830Operate.java,v 1.2 2006/12/07 09:00:09 suresh Exp $
package jp.co.daifuku.wms.storage.rft;
//#CM575969
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ShelfInvalidityException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM575970
/**
 * Designer : Y.Taki<BR>
 * Maker : Y.Taki<BR>
 * <BR>
 * This class processes inventory item inquiry<BR>
 * The business logic from Id0830Process is executed<BR>
 * <BR>
 * Stock info search process (<CODE>getStockDataOfInventoryCheck</CODE> method)<BR>
 * <BR>
 * <DIR>
 *  Fetch the currrent stock info data. In case of multiple data, use Collect option<BR>
 *  If there is no target data, return null<BR>
 *  If target exists, execute Collect (If there are multiple Expiry date, set empty for Expiry date)<BR>
 * </DIR>
 * <BR>
 * Inventory search work info process(<CODE>getInventoryCheckData</CODE> method)<BR>
 * <BR>
 * <DIR>
 *   Using the value in the argument, search Inventory work info<BR>
 *   Return null if there is no corresponding data<BR>
 *   Gather data using Collect condition if there are multiple data corresponding to search conditions.(If multiple Expiry date exists, set null against Expiry date)<BR>
 *   Return as it is if there is single record<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:09 $
 * @author  $Author: suresh $
 */
public class Id0830Operate extends IdOperate
{
	//#CM575971
	// Class fields----------------------------------------------------
	//#CM575972
	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "Id0830Operate";
	//#CM575973
	// Class variables -----------------------------------------------
	//#CM575974
	// Constructors --------------------------------------------------
	//#CM575975
	/**
	 * Constructor
	 */
	public Id0830Operate()
	{
		super();
	}

	//#CM575976
	/**
	 * Pass Db connection info to the constructor
	 * @param conn DBConnection info
	 */
	public Id0830Operate(Connection conn)
	{
		super();
		wConn = conn;
	}
	//#CM575977
	// Class method --------------------------------------------------
	//#CM575978
	/**
	 * Return the version of this class
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:09 $");
	}

	//#CM575979
	/**
	 * Fetch current stock info data. Apply collect condition in case of multiple data<BR>
	 * <BR>
	 * Search using Consignor code, location no., Item code, Area type : Other than AS/RS as search key<BR>
	 * If search returns nothing, return null<BR>
	 * If target data exist, use collect condition<BR>
	 * If multiple Expiry date exist during grouping, set empty for Expiry date<BR>
	 * Set and return the collect result<BR>
	 * <BR>
	 * @param	consignorCode	Consignor code
	 * @param	areaNo			Area no..
	 * @param	locationNo 		Location
	 * @param	scanCode1 		Scan Item code1
	 * @param	scanCode2		Scan Item code2
	 * @return	Stock info entity
	 * @throws OverflowException If the item count exceeds limit
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws ShelfInvalidityException If the input location is not valid
	 * @throws ScheduleException If abnormal error occurs during inventory inquiry
	 */
	public Stock getStockDataOfInventoryCheck(
		String consignorCode,
		String areaNo,
		String locationNo,
		String scanCode1,
		String scanCode2)
		throws OverflowException, ReadWriteException, ScheduleException, ShelfInvalidityException
	{
		//#CM575980
		// Variable to store stock search result
		Stock[] stockData = null;
		//#CM575981
		// Variable to store stock after grouping
		Stock retStockData = null;

		stockData = getStockDataList(consignorCode, areaNo, locationNo, scanCode1, scanCode2);
		
		if (stockData.length == 0)
		{
			return null;
		}
		
		//#CM575982
		// Group stock
		retStockData = collectStockData(stockData);

		if (retStockData.getStockQty() > 0)
		{
			return retStockData;
		}
		else
		{
			return null;
		}
	}

	//#CM575983
	/**
	 * Fetch data with status flag Standby (0) from inventory info based on specified conditions<BR>
	 * <BR>
	 * Search using Consignor code, location no., item code, area type : Other than AS/RS as key<BR>
	 * If there is no target data, return null <BR>
	 * If target data occurs, group it<BR>
	 * If multiple Expiry date exist during grouping, set empty for Expiry date<BR>
	 * Set the grouping result to return value<BR>
	 * <BR>
	 * @param	consignorCode	Consignor code
	 * @param	areaNo			Area no..
	 * @param	locationNo		Inventory location
	 * @param	itemCode 		Item code
	 * @return	Inventory info entity array
	 * @throws OverflowException If the item count exceeds limit
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws ShelfInvalidityException If the input location is not valid
	 * @throws ScheduleException If abnormal error occurs during inventory inquiry
	*/
	public InventoryCheck getInventoryCheckData(
		String consignorCode,
		String areaNo,
		String locationNo,
		String itemCode)
		throws OverflowException, ReadWriteException, ScheduleException, ShelfInvalidityException
	{
		//#CM575984
		// Variable to store inventory info search result
		InventoryCheck[] inventoryCheckData = null;
		//#CM575985
		// Variable to store stock after grouping
		InventoryCheck retInventoryCheckData = null;
				
		inventoryCheckData = getInventoryCheckDataList(consignorCode, areaNo, locationNo, itemCode);
		if (inventoryCheckData.length == 0)
		{
			return null;
		}
				
		//#CM575986
		// Group inventory info
		retInventoryCheckData = collectInventoryCheckData(inventoryCheckData);
	
		//#CM575987
		// Return the search result
		return retInventoryCheckData;
	}

	//#CM575988
	// Package methods -----------------------------------------------
	//#CM575989
	// Protected methods ---------------------------------------------
	//#CM575990
	/**
	 * Search for next stock data in stock info<BR>
	 * <DIR>
	 *  Stock status = 2:Center stock<BR>
	 *  Consignor code, area, Location, Item code = fetch based on the parameter values<BR>
	 *  Sort order = Case piece flag (descending order)>Expiry date<BR>
	 * </DIR>
	 * Return the data as stock info entity
	 * If there are no values, return empty array<BR>
	 * <BR>
	 * @param	consignorCode	Consignor code
	 * @param	areaNo			Area no..
	 * @param	locationNo 		Location
	 * @param	scanCode1 		Scan Item code1
	 * @param	scanCode2		Scan Item code2
	 * @return	Stock info entity
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws ShelfInvalidityException If the input location is not valid
	 * @throws ScheduleException If abnormal error occurs during inventory inquiry
	*/
	protected Stock[] getStockDataList(
		String consignorCode,
		String areaNo,
		String locationNo,
		String scanCode1,
		String scanCode2)
		throws ReadWriteException, ScheduleException, ShelfInvalidityException
	{
		//#CM575991
		// Array to store the return value
		Stock[] stockData	= null;
		
		StockSearchKey stockKey = new StockSearchKey();
		StockHandler stockHandler = new StockHandler(wConn);
		//#CM575992
		//-----------------
		//#CM575993
		// Search stock info(first time)
		//#CM575994
		//-----------------
		stockKey = getStockSearchKey(consignorCode,areaNo,locationNo);
		stockKey.setItemCode(scanCode1);
		
		stockKey.setCasePieceFlagOrder(1, false);
		stockKey.setUseByDateOrder(2, true);
		
		//#CM575995
		// Start search
		stockData = (Stock[]) stockHandler.find(stockKey);

		//#CM575996
		// If target data exists, return result
		if (stockData != null && stockData.length > 0)
		{
			return stockData;
		}
		
		//#CM575997
		//-----------------
		//#CM575998
		// Search stock info(second time)
		//#CM575999
		//-----------------
		stockKey = getStockSearchKey(consignorCode, areaNo, locationNo);
		stockKey.setItf(scanCode1);
		
		stockKey.setCasePieceFlagOrder(1, false);
		stockKey.setUseByDateOrder(2, true);
		
		//#CM576000
		// Start search
		stockData = (Stock[]) stockHandler.find(stockKey);

		//#CM576001
		// If target data exists, return result
		if (stockData != null && stockData.length > 0)
		{
			return stockData;
		}
		
		//#CM576002
		//-----------------
		//#CM576003
		// Search stock info(third time)
		//#CM576004
		//-----------------
		stockKey = getStockSearchKey(consignorCode, areaNo, locationNo);
		stockKey.setBundleItf(scanCode1);
		
		stockKey.setCasePieceFlagOrder(1, false);
		stockKey.setUseByDateOrder(2, true);
		
		//#CM576005
		// Start search
		stockData = (Stock[]) stockHandler.find(stockKey);

		//#CM576006
		// If target data exists, return result
		if (stockData != null && stockData.length > 0)
		{
			return stockData;
		}
		
		if(!StringUtil.isBlank(scanCode2))
		{
			//#CM576007
			//-----------------
			//#CM576008
			// Search stock info(fourth time)
			//#CM576009
			//-----------------
			stockKey = getStockSearchKey(consignorCode, areaNo, locationNo);
			stockKey.setItemCode(scanCode2);
			
			stockKey.setCasePieceFlagOrder(1, false);
			stockKey.setUseByDateOrder(2, true);
			
			//#CM576010
			// Start search
			stockData = (Stock[]) stockHandler.find(stockKey);

			//#CM576011
			// If target data exists, return result
			if (stockData != null && stockData.length > 0)
			{
				return stockData;
			}
		}

		//#CM576012
		// If there is no search result, return stock class array with request qty 0
		return new Stock[0];
	}

	//#CM576013
	/**
	 * Search for next stock data in stock info<BR>
	 * <DIR>
	 *  Stock status = 2:Center stock<BR>
	 *  Consignor code, Item code = fetch based on the parameter values<BR>
	 *  Sort order = Storagedate/time (descending order)<BR>
	 *  Area master info. area type = 2: Other than ASRS<BR>
	 * </DIR>
	 * Return received data as stock info entity array<BR>
	 * If there are no values, return empty array<BR>
	 * <BR>
	 * @param	consignorCode	Consignor code
	 * @param	scanCode1 		Scan Item code1
	 * @param	scanCode2		Scan Item code2
	 * @return	Stock info entity array
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws ShelfInvalidityException If the input location is not valid
	 * @throws ScheduleException If abnormal error occurs during inventory inquiry
	*/
	protected Stock[] getStockDataList(
		String consignorCode,
		String scanCode1,
		String scanCode2)
		throws ReadWriteException, ScheduleException, ShelfInvalidityException
	{
		//#CM576014
		// Array to store the return value
		Stock[] stockData	= null;
		
		StockSearchKey stockKey = new StockSearchKey();
		StockHandler stockHandler = new StockHandler(wConn);
		//#CM576015
		//-----------------
		//#CM576016
		// Search stock info(first time)
		//#CM576017
		//-----------------
		stockKey = getStockSearchKey(consignorCode, null, null);
		stockKey.setItemCode(scanCode1);
		
		stockKey.setInstockDateOrder(1, false);
		
		//#CM576018
		// Start search
		stockData = (Stock[]) stockHandler.find(stockKey);

		//#CM576019
		// If target data exists, return result
		if (stockData != null && stockData.length > 0)
		{
			return stockData;
		}
		
		//#CM576020
		// -----------------
		//#CM576021
		// Search stock info(second time)
		//#CM576022
		//-----------------
		stockKey = getStockSearchKey(consignorCode, null, null);
		stockKey.setItf(scanCode1);
		
		stockKey.setInstockDateOrder(1, false);
		
		//#CM576023
		// Start search
		stockData = (Stock[]) stockHandler.find(stockKey);

		//#CM576024
		// If target data exists, return result
		if (stockData != null && stockData.length > 0)
		{
			return stockData;
		}
		
		//#CM576025
		// -----------------
		//#CM576026
		// Search stock info(third time)
		//#CM576027
		//-----------------
		stockKey = getStockSearchKey(consignorCode, null, null);
		stockKey.setBundleItf(scanCode1);
		
		stockKey.setInstockDateOrder(1, false);
		
		//#CM576028
		// Start search
		stockData = (Stock[]) stockHandler.find(stockKey);

		//#CM576029
		// If target data exists, return result
		if (stockData != null && stockData.length > 0)
		{
			return stockData;
		}
		
		if(!StringUtil.isBlank(scanCode2))
		{
			//#CM576030
			// -----------------
			//#CM576031
			// Search stock info(fourth time)
			//#CM576032
			//-----------------
			stockKey = getStockSearchKey(consignorCode, null, null);
			stockKey.setItemCode(scanCode2);
			
			stockKey.setInstockDateOrder(1, false);
			
			//#CM576033
			// Start search
			stockData = (Stock[]) stockHandler.find(stockKey);
			//#CM576034
			// If target data exists, return result			
			if (stockData != null && stockData.length > 0)
			{
				return stockData;
			}
		}

		//#CM576035
		// Return the search result
		return new Stock[0];
	}

	//#CM576036
	/**
	 * Search for next stock data in inventory info<BR>
	 * <DIR>
	 *  Process flag :0 (Standby)<BR>
	 *  Consignor code, Area, Inventory Location, Item code = fetch based on the parameter values<BR>
	 *  Sort order = Case piece flag(descending order)>Expiry date<BR>
	 * </DIR>
	 * Return received data as inventory info entity array<BR>
	 * If there are no values, return empty array<BR>
	 * <BR>
	 * @param	consignorCode	Consignor code
	 * @param	areaNo			Area no..
	 * @param	locationNo 		Location
	 * @param	itemCode 		Item code
	 * @return	Inventory info entity array
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws ShelfInvalidityException If the input location is not valid
	 * @throws ScheduleException If abnormal error occurs during inventory inquiry
	*/
	protected InventoryCheck[] getInventoryCheckDataList(
		String consignorCode,
		String areaNo,
		String locationNo,
		String itemCode)
		throws ReadWriteException, ScheduleException, ShelfInvalidityException
	{
	    LocateOperator lOperator = new LocateOperator(wConn);
		if(lOperator.isAsrsLocation(locationNo))
		{
			//#CM576037
			// 6023443=No.{0} The specified location is in automatic warehouse. Cannot enter.
			throw new ShelfInvalidityException();
		}	
	    
	    
	    //#CM576038
	    // Array to store the return value
		InventoryCheck[] inventoryData	= null;
		
		InventoryCheckSearchKey inventoryKey = new InventoryCheckSearchKey();
		InventoryCheckHandler inventoryHandler = new InventoryCheckHandler(wConn);
		//#CM576039
		//-----------------
		//#CM576040
		// Search inventory info
		//#CM576041
		//-----------------
		//#CM576042
		// Process flag is 0 (Standby)
		inventoryKey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
		inventoryKey.setConsignorCode(consignorCode);
		if (! StringUtil.isBlank(areaNo))
		{
			inventoryKey.setAreaNo(areaNo);
		}
		
		inventoryKey.setLocationNo(locationNo);
		inventoryKey.setItemCode(itemCode);

		inventoryKey.setCasePieceFlagOrder(1, false);
		inventoryKey.setUseByDateOrder(2, true);
		
		//#CM576043
		// Start search
		inventoryData = (InventoryCheck[]) inventoryHandler.find(inventoryKey);

		//#CM576044
		// If there is no search result, return stock class array with request qty 0
		if (inventoryData == null || inventoryData.length == 0)
		{
			return new InventoryCheck[0];
		}

		//#CM576045
		// Return the search result
		return inventoryData;
	}

	//#CM576046
	/**
	 * Gather stock data<BR>
	 * Group the stock info entity array received in parameter<BR>
	 * While grouping, add stock qty and allocation qty. Other than this, make it as the first data of the array<BR>
	 * If Expiry dates differ, set empty<BR>
	 * Return the grouped result as stock info entity<BR>
	 * <BR>
	 * @param	stockData	Stock info entity array
	 * @return	Stock info entity
	 * @throws OverflowException If the item count exceeds limit
	 */
	protected Stock collectStockData(Stock[] stockData) throws OverflowException
	{
		//#CM576047
		// Stock info entity for work
		Stock workStock = null;
		
		for (int i = 0; i < stockData.length; i++)
		{
			if (i==0)
			{
				workStock = stockData[i];
			}
			else
			{
				//#CM576048
				// ICancel if item code differs
				if(! workStock.getItemCode().equals(stockData[i].getItemCode()))
				{
					continue;
				}
				if ((workStock.getStockQty() + stockData[i].getStockQty() <= SystemParameter.MAXSTOCKQTY)
					&& (workStock.getAllocationQty() + stockData[i].getAllocationQty() <= SystemParameter.MAXSTOCKQTY)){
					workStock.setStockQty(workStock.getStockQty() + stockData[i].getStockQty());
					workStock.setAllocationQty(workStock.getAllocationQty() + stockData[i].getAllocationQty());
				}
				else
				{
					//#CM576049
					// 6026028=Overflow occurred during record integration processing. Table Name: {0}
					RftLogMessage.print(6026028, LogMessage.F_ERROR, CLASS_NAME, "DMSTOCK");
					throw new OverflowException();
				}
				if (! workStock.getUseByDate().equals(stockData[i].getUseByDate()))
				{
					workStock.setUseByDate("");
				}
			}
		}
		return workStock;
	}

	//#CM576050
	/**
	 * Gather inventory data<BR>
	 * Group Inventory info entity array received in parameter to one record<BR>
	 * While grouping, add inventory result qty and set the remaining items as the first data of the array<BR>
	 * If there are multiple Expiry date, set empty<BR>
	 * Return the grouped result as inventory info entity<BR>
	 * <BR>
	 * @param	inventoryCheckData	Inventory info entity array
	 * @return	Inventory info entity
	 * @throws OverflowException If the item count exceeds limit
	 */
	protected InventoryCheck collectInventoryCheckData(InventoryCheck[] inventoryCheckData) throws OverflowException
	{
		//#CM576051
		// Inventory info entity for work use
		InventoryCheck workInventory = null;
		
		for (int i = 0; i < inventoryCheckData.length; i++)
		{
			if (i==0)
			{
				workInventory = inventoryCheckData[i];
			}
			else
			{
				if (workInventory.getResultStockQty() + inventoryCheckData[i].getResultStockQty() <= SystemParameter.MAXSTOCKQTY)
				{
					workInventory.setResultStockQty(workInventory.getResultStockQty() + inventoryCheckData[i].getResultStockQty());
				}
				else
				{
					//#CM576052
					// 6026028=Overflow occurred during record integration processing. Table Name: {0}
					RftLogMessage.print(6026028, LogMessage.F_ERROR, CLASS_NAME, "DNINVENTORYCHECK");
					throw new OverflowException();
				}
				if (! workInventory.getUseByDate().equals(inventoryCheckData[i].getUseByDate()))
				{
					workInventory.setUseByDate("");
				}
			}
		}
		return workInventory;
	}
	
	//#CM576053
	/**
	 * Fetch the base key to search stock during item inquiry
	 * @param consignorCode Consignor code
	 * @param areaNo Area no.
	 * @param locationNo Location no.
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws ShelfInvalidityException If the input location is not valid
	 * @throws ScheduleException If abnormal error occurs during inventory inquiry
	 */
	private StockSearchKey getStockSearchKey(String consignorCode, String areaNo, String locationNo)
			throws ReadWriteException, ScheduleException, ShelfInvalidityException
	{
		StockSearchKey stockKey = new StockSearchKey();
		//#CM576054
		//-----------------
		//#CM576055
		// Stock info search
		//#CM576056
		//-----------------
		//#CM576057
		// Set target status as center stock
		stockKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		stockKey.setConsignorCode(consignorCode);

		if (! StringUtil.isBlank(locationNo))
		{
			LocateOperator lOperator = new LocateOperator(wConn);
			if(lOperator.isAsrsLocation(locationNo))
			{
				//#CM576058
				// 6023443=No.{0} The specified location is in automatic warehouse. Cannot enter.
				throw new ShelfInvalidityException();
			}
			stockKey.setLocationNo(locationNo);
			
			if (! StringUtil.isBlank(areaNo))
			{
				stockKey.setAreaNo(areaNo);
			}
			else 
			{
				AreaOperator AreaOperator = new AreaOperator(wConn);
				
				String[] areaNoASRS = null;
				int[] areaType = new int[2];
				areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
				areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
				//#CM576059
				// Fetch area other than ASRS and append to search condition
				//#CM576060
				// If there is no corresponding data, search with IS NULL
				areaNoASRS = AreaOperator.getAreaNo(areaType);
				stockKey.setAreaNo(areaNoASRS);
			}
		}

		return stockKey;
	}

	//#CM576061
	// Private methods -----------------------------------------------

}
//#CM576062
//end of class
