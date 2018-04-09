// $Id: InventoryInquiryWriter.java,v 1.6 2006/12/13 09:04:09 suresh Exp $
package jp.co.daifuku.wms.storage.report;

//#CM574502
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.storage.dbhandler.StorageInventoryCheckReportFinder;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM574503
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * This class creates the inventory work list report data file and calls the print process<BR>
 * With the conditions specified in the schedule class, this class searches the inventory check table
 * and creates data file for printing <BR>
 * <BR>
 * This class processes the following <BR>
 * <BR>
 * Process that creates the report data file (<CODE>startPrint</CODE> method) <BR>
 * <DIR>
 *   Search Inventory Check table<BR>
 *   Data file for printing is not created if target data does not exist. <BR>
 *   If target data exists, this class creates data file for printing and calls print process. <BR>
 * <BR>
 *   <Search Condition> * Required Input
 *   <DIR>
 *     Consignor code *<BR>
 *     Start Location<BR>
 *     End Location<BR>
 *     Item code<BR>
 *     Status *<BR>
 *     Process Flag : Yet to Commit<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/07</TD><TD>Y.Okamura</TD><TD>Create new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.6 $, $Date: 2006/12/13 09:04:09 $
 * @author  $Author: suresh $
 */
public class InventoryInquiryWriter extends CSVWriter
{
	//#CM574504
	// Class fields --------------------------------------------------

	//#CM574505
	// Class variables -----------------------------------------------
	//#CM574506
	/**
	 * Consignor code
	 */
	private String wConsignorCode;

	//#CM574507
	/**
	 * Start Location
	 */
	private String wFromLocation;

	//#CM574508
	/**
	 * End Location
	 */
	private String wToLocation;

	//#CM574509
	/**
	 * Item code
	 */
	private String wItemCode;

	//#CM574510
	/**
	 * Process Flag
	 */
	private String wStatusFlag;

	//#CM574511
	/**
	 * Consignor name
	 */
	private String wConsignorName = "";

	//#CM574512
	// Class method --------------------------------------------------
	//#CM574513
	/**
	 * Returns version of this class
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.6 $,$Date: 2006/12/13 09:04:09 $");
	}

	//#CM574514
	// Constructors --------------------------------------------------
	//#CM574515
	/**
	 * Construct InventoryInquiryWriter object<BR>
	 * Set connection and locale <BR>
	 * @param conn <CODE>Connection</CODE> database connection object
	 */
	public InventoryInquiryWriter(Connection conn)
	{
		super(conn);
	}

	//#CM574516
	// Public methods ------------------------------------------------
	//#CM574517
	/**
	 * Set search value to Consignor code
	 * @param pConsignorcode String Consignor code
	 */
	public void setConsignorCode(String pConsignorcode)
	{
		wConsignorCode = pConsignorcode;
	}

	//#CM574518
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM574519
	/**
	 * Set search value to Start Location
	 * @param pFromLocation String Start Location
	 */
	public void setFromLocation(String pFromLocation)
	{
		wFromLocation = pFromLocation;
	}

	//#CM574520
	/**
	 * Fetch Start Location
	 * @return Start Location
	 */
	public String getFromLocation()
	{
		return wFromLocation;
	}

	//#CM574521
	/**
	 * Set search value to End Location
	 * @param pToLocation String End Location
	 */
	public void setToLocation(String pToLocation)
	{
		wToLocation = pToLocation;
	}

	//#CM574522
	/**
	 * Fetch End Location
	 * @return End Location
	 */
	public String getToLocation()
	{
		return wToLocation;
	}

	//#CM574523
	/**
	 * Set search value to Item code
	 * @param pItemCode String Item code
	 */
	public void setItemCode(String pItemCode)
	{
		wItemCode = pItemCode;
	}

	//#CM574524
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return wItemCode;
	}

	//#CM574525
	/**
	 * Set search value to Process Flag
	 * @param pStatusFlag String Process Flag
	 */
	public void setStatusFlag(String pStatusFlag)
	{
		wStatusFlag = pStatusFlag;
	}

	//#CM574526
	/**
	 * Fetch Status
	 * @return Status
	 */
	public String getStatusFlag()
	{
		return wStatusFlag;
	}

	//#CM574527
	/**
	 * Fetch print data count <BR>
	 * Used to decide whether to call the print process or not based on search result<BR>
	 * @return print data count
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	public int count() throws ReadWriteException
	{
		StorageInventoryCheckReportFinder inventoryFinder = new StorageInventoryCheckReportFinder(wConn);
		InventoryCheckSearchKey searchKey = new InventoryCheckSearchKey();
		//#CM574528
		// Set search conditions and fetch count
		setWorkInfoSearchKey(searchKey);
		
		//#CM574529
		// Normally Handler is used to fetch count
		//#CM574530
		// Handler class will not be created to fetch count for difference display, same qty display
		//#CM574531
		// use search of ReportFinder
		
		return getDispCount(inventoryFinder, searchKey);

	}
	
	//#CM574532
	/**
	 * Create inventory work list csv file and call print class<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set search conditions and execute search<BR>
	 * 		2.If the search result is less than 1, don't print<BR>
	 * 		3.Fetch search result in lots of 100<BR>
	 * 		4.Print<BR>
	 * 		5.If print succeeds, move the data file to backup folder<BR>
	 * 		6.Return whether the print is successful or not<BR>
	 * </DIR>
	 * @return execution result (Print success:true Print failed:false)
	 */
	public boolean startPrint()
	{
		StorageInventoryCheckReportFinder inventoryFinder = new StorageInventoryCheckReportFinder(wConn);

		try
		{
			//#CM574533
			// execute search
			InventoryCheckSearchKey inventoryKey = new InventoryCheckSearchKey();

			//#CM574534
			// set search conditions
			setWorkInfoSearchKey(inventoryKey);

			//#CM574535
			// location, item code, expiry date
			inventoryKey.setLocationNoOrder(1, true);
			inventoryKey.setItemCodeOrder(2, true);
			inventoryKey.setUseByDateOrder(3, true);
			
			//#CM574536
			// Don't call print process if data does not exist
			if (getDispCount(inventoryFinder, inventoryKey) <= 0)
			{
				//#CM574537
				// 6003010 = Print data was not found.
				wMessage = "6003010";
				return false;
			}

			//#CM574538
			// Fetch consignor name
			getConsignorName();

			//#CM574539
			// create output file
			if (!createPrintWriter(FNAME_INVENTORY_WORK))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_INVENTORY_WORK);
			
			//#CM574540
			// Create data file until the search results are done
			InventoryCheck[] inventory = null;
			while (inventoryFinder.isNext())
			{
				inventory = (InventoryCheck[]) inventoryFinder.getEntities(100);

				for (int i = 0; i < inventory.length; i++)
				{
					wStrText.append(re + "");

					//#CM574541
					// Consignor code
					wStrText.append(ReportOperation.format(inventory[i].getConsignorCode()) + tb);
					//#CM574542
					// Consignor name
					wStrText.append(ReportOperation.format(wConsignorName) + tb);
					//#CM574543
					// Location
					wStrText.append(ReportOperation.format(inventory[i].getLocationNo()) + tb);
					//#CM574544
					// Item code
					wStrText.append(ReportOperation.format(inventory[i].getItemCode()) + tb);
					//#CM574545
					// Item name
					wStrText.append(ReportOperation.format(inventory[i].getItemName1()) + tb);
					//#CM574546
					// Packed qty per case
					wStrText.append(inventory[i].getEnteringQty() + tb);
					//#CM574547
					// Packed qty per bundle
					wStrText.append(inventory[i].getBundleEnteringQty() + tb);
					//#CM574548
					// Inventory Check Case Qty
					wStrText.append(DisplayUtil.getCaseQty(inventory[i].getResultStockQty(), inventory[i].getEnteringQty(), inventory[i].getCasePieceFlag()) + tb);
					//#CM574549
					// Inventory Check Piece Qty
					wStrText.append(DisplayUtil.getPieceQty(inventory[i].getResultStockQty(), inventory[i].getEnteringQty(), inventory[i].getCasePieceFlag()) + tb);
					//#CM574550
					// Stock Case Qty
					wStrText.append(DisplayUtil.getCaseQty(inventory[i].getStockQty(), inventory[i].getEnteringQty(), inventory[i].getCasePieceFlag()) + tb);
					//#CM574551
					// Stock Piece Qty
					wStrText.append(DisplayUtil.getPieceQty(inventory[i].getStockQty(), inventory[i].getEnteringQty(), inventory[i].getCasePieceFlag()) + tb);
					//#CM574552
					// Expiry date
					wStrText.append(ReportOperation.format(inventory[i].getUseByDate()));

					//#CM574553
					// Output data to file
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);

				}
			}

			//#CM574554
			// Close the stream
			wPWriter.close();

			//#CM574555
			// Execute UCXSingle. (Print)
			if (!executeUCX(JOBID_INVENTORY_WORK))
			{
				//#CM574556
				// Print failed. Refer to the log
				return false;
			}

			//#CM574557
			// If print succeeds, move the data file to backup folder
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException e)
		{
			//#CM574558
			// 6007034 = Print failed. Refer to the log
			setMessage("6007034");

			return false;
		}
		finally
		{
			try
			{
				//#CM574559
				// Close the database cursor
				inventoryFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM574560
				// Database error occurred. Refer to the log
				setMessage("6007002");
				return false;
			}
		}

		return true;
	}

	//#CM574561
	// Package methods -----------------------------------------------

	//#CM574562
	// Protected methods ---------------------------------------------

	//#CM574563
	// Private methods -----------------------------------------------
	//#CM574564
	/**
	 * This method sets the search conditions for inventory work search
	 * @param searchKey InventoryCheckSearchKey search key for inventory check
	 * @return search key with search conditions
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	private InventoryCheckSearchKey setWorkInfoSearchKey(InventoryCheckSearchKey searchKey) throws ReadWriteException
	{
		//#CM574565
		// Set search key
		if (!StringUtil.isBlank(wConsignorCode))
		{
			searchKey.setConsignorCode(wConsignorCode);
		}
		if (!StringUtil.isBlank(wFromLocation))
		{
			searchKey.setLocationNo(wFromLocation, ">=");
		}
		if (!StringUtil.isBlank(wToLocation))
		{
			searchKey.setLocationNo(wToLocation, "<=");
		}
		if (!StringUtil.isBlank(wItemCode))
		{
			searchKey.setItemCode(wItemCode);
		}
		//#CM574566
		// Process Flag : Yet to Commit
		searchKey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);

		return searchKey;
	}

	//#CM574567
	/**
	 * Method to fetch the most recently registered consignor name<BR>
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	private void getConsignorName() throws ReadWriteException
	{
		//#CM574568
		// Create finder instance
		StorageInventoryCheckReportFinder nameFinder = new StorageInventoryCheckReportFinder(wConn);
		InventoryCheckSearchKey nameKey = new InventoryCheckSearchKey();

		setWorkInfoSearchKey(nameKey);
		nameKey.setConsignorNameCollect("");
		nameKey.setRegistDateOrder(1, false);
		//#CM574569
		// Execute search
		nameFinder.open();
		if (getDispCount(nameFinder, nameKey) > 0)
		{
			InventoryCheck inventory[] = (InventoryCheck[]) nameFinder.getEntities(1);

			if (inventory != null && inventory.length != 0)
			{
				wConsignorName = inventory[0].getConsignorName();
			}
		}
		nameFinder.close();

	}

	//#CM574570
	/**
	 * Method to fetch display count<BR>
	 * The fetch type depends on the display type<BR>
	 * @param finder StorageInventoryCheckReportFinder StorageInventoryCheckReportFinder instance
	 * @param skey InventoryCheckSearchKey search key
	 * @return search result count
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	private int getDispCount(StorageInventoryCheckReportFinder finder, InventoryCheckSearchKey skey) throws ReadWriteException
	{
		int dispCnt = 0;
		//#CM574571
		// In case of all display
		if (wStatusFlag == null || wStatusFlag.equals(StorageSupportParameter.DISP_STATUS_ALL))
		{
			dispCnt = ((StorageInventoryCheckReportFinder) finder).search(skey);
		}
		//#CM574572
		// In case of difference display
		else if (wStatusFlag.equals(StorageSupportParameter.DISP_STATUS_DIFFERENCE))
		{
			dispCnt = ((StorageInventoryCheckReportFinder) finder).search(skey, StorageSupportParameter.DISP_STATUS_DIFFERENCE);
		}
		//#CM574573
		// In case of same display
		else if (wStatusFlag.equals(StorageSupportParameter.DISP_STATUS_EQUAL))
		{
			dispCnt = ((StorageInventoryCheckReportFinder) finder).search(skey, StorageSupportParameter.DISP_STATUS_EQUAL);
		}

		return dispCnt;

	}
}
//#CM574574
//end of class
