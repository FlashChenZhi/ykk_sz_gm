//#CM8964
//$Id: DeadStockWriter.java,v 1.5 2006/12/13 09:02:19 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.report;

//#CM8965
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM8966
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * 
 * <CODE>DeadStockWriter</CODE> class creates data file for long-term stay item List report and execute print process. <BR>
 * Search Stock Info(DMSTOCK) based on the conditions set at <CODE>DeadStockListSCH</CODE> class and <BR>
 * Create result as the report file for long-term stay item List. <BR>
 * This class processes as follows. <BR>
 * <BR>
 * a ticket data file creation Process(<CODE>startPrint()</CODE>Method) <BR>
 * <DIR>
 *	1.Search for Stock Info(DMSTOCK) Count that meets the set condition via <CODE>DeadStockListSCH</CODE>class. <BR>
 *	2.Create a report data file if result count is one or more. If it is 0, return false and close. <BR>
 *	3.Start the print process. <BR>
 *  4.Return true when print Process is normal. <BR>
 *    Return false when error occurred during the print process. <BR>
 * <BR>
 * 	＜parameter＞*Mandatory Input <BR><DIR>
 * 		Consignor Code * Consignor Code <BR>
 * 		Storage Date * Instock Date <BR>
 * <BR>
 * 	＜Search condition＞ <BR><DIR>
 * 		Stock Packed Qty more than 1 (Stock Qty > 0)<BR>
 * 		Stock status Center Inventory (Status Flag = Occupied) <BR>
 * <BR>
 *	＜Print data＞ <BR><DIR>
 * 		Consignor Code ConsignorCode <BR>
 * 		Consignor Name ConsignorName <BR>
 * 		Storage Date InstockDate <BR>
 * 		Item Code ItemCode <BR>
 * 		Item Name ItemName1 <BR>
 * 		Division CasePieceFlag <BR>
 * 		Location LocationNo <BR>
 * 		Packed Qty per Case EnteringQty <BR>
 * 		Packed Qty per Piece BundleEnteringQty <BR>
 * 		Stock Packed Qty per Case StockCaseQty <BR>
 * 		Stock Packed Qty per Piece StockPieceQty <BR>
 * 		Case ITF Itf<BR>
 * 		Bundle ITF BundleItf<BR>
 * 		Expiry Date UseByDate<BR> 
 * </DIR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/07</TD><TD>K.Toda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2006/12/13 09:02:19 $
 * @author  $Author: suresh $
 */
public class DeadStockWriter extends CSVWriter
{
	//#CM8967
	// Class fields --------------------------------------------------

	//#CM8968
	// Class variables -----------------------------------------------
	//#CM8969
	// Consignor Code
	private String wConsignorCode = "";
	//#CM8970
	// Storage Date(Date type)
	private Date wStorageDate = null;
	//#CM8971
	// Storage Date(Character type)
	private String wStorageDate_s = "";
	//#CM8972
	// Consignor Name
	private String wConsignorName = "";
	
	//#CM8973
	// Class method --------------------------------------------------
	//#CM8974
	/**
	 * return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.5 $,$Date: 2006/12/13 09:02:19 $");
	}

	//#CM8975
	// Constructors --------------------------------------------------
	//#CM8976
	/**
	 * DeadStockWriter Construct Object.<BR>
	 * Set Connection and locale.<BR>
	 * @param conn <CODE>Connection</CODE> Connection object with the database.
	 */
	public DeadStockWriter(Connection conn)
	{
		//#CM8977
		//Invoke CSVWriter constructor
		super(conn);
	}

	//#CM8978
	// Public methods ------------------------------------------------
	//#CM8979
	/**
	 * Set Consignor Code
	 * 
	 * @param ConsignorCode Consignor Code to be set
	 */
	public void setConsignorCode(String consignorCode)
	{
		wConsignorCode = consignorCode;
	}
	
	//#CM8980
	/**
	 * Obtain Consignor Code.
	 * @return Consignor Code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}
	
	//#CM8981
	/**
	 * Set Storage Date(Character type)
	 * @param s Storage Date to be set
	 */
	public void setStorageDateS(String s)
	{
		wStorageDate_s = s;
	}
	
	//#CM8982
	/**
	 * Obtain Storage Date(Character type).
	 * @return Storage Date(Character type)
	 */
	public String getStorageDateS()
	{
		return wStorageDate_s;
	}
	
	//#CM8983
	/**
	 * Set Storage Date
	 * 
	 * @param storageDate Storage Date to be set
	 */
	public void setStorageDate(Date storageDate)
	{
		wStorageDate = storageDate;
	}
	//#CM8984
	/**
	 *  Obtain Storage Date.
	 * @return Storage Date
	 */
	public Date getStorageDate()
	{
		return wStorageDate;
	}
	
	//#CM8985
	/**
	 * Obtain print data count.<BR>
	 * Use it to determine for the search result to allow printing.<BR>
	 * Use this method from a schedule class for display.<BR>
	 * 
	 * @return Count of print data
	 * @throws ReadWriteException Inform when data connection error occurs.
	 */
	public int count() throws ReadWriteException
	{
		StockHandler instockHandle = new StockHandler(wConn);
		StockSearchKey searchKey = new StockSearchKey();
		//#CM8986
		// Set Search condition and obtain Count
		setDeadStockSearchKey(searchKey);
		return instockHandle.count(searchKey);

	}
	
	//#CM8987
	/**
	 * Create the CSV file for long-term stay item List and invoke print execute class.<BR>
	 * Search Stock Info from the search condition input from Search screen.
	 * Not execute print process when search result is less than one.<BR>
	 * Return true when print Process is normal and return false when print Process is failed.<BR>
	 * Execute processing in the following sequence.<BR>
	 * <BR>
	 * <DIR>
	 *   1.Set Search condition and execute search Process<BR>
	 *   2.Not execute print process when search result is less than one.<BR>
	 *   3.Obtain every 100 search results and output them to a file.<BR>
	 *   4.Execute print process<BR>
	 *   5.Execute relocation of data file to backup folder when print was successfully executed.<BR>
	 *   6.return if print was successfully executed.<BR>
	 * </DIR>
	 * 
	 * @return boolean if print was successfully executed.
	 */
	public boolean startPrint()
	{
		StockReportFinder sFinder = new StockReportFinder(wConn);
		try
		{
			//#CM8988
			// Execute search
			StockSearchKey sKey = new StockSearchKey();
			//#CM8989
			// Set a search key.
			setDeadStockSearchKey(sKey);
			//#CM8990
			// Not execute print process when there is no data.
			if (sFinder.search(sKey) <= 0)
			{
				//#CM8991
				// 6003010 = Print data was not found.
				wMessage = "6003010";
				return false;
			}
			//#CM8992
			// Obtain Consignor Name.
			getDisplayConsignorName();
			
			//#CM8993
			// Create a file for output.
			if (!createPrintWriter(FNAME_ITEM_DEADSTOCK))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_ITEM_DEADSTOCK);
			
			String[] asAreaNo = null;
			try
			{
				//#CM8994
				// Obtain AS/RS Area No.of the area in order to execute hyphenation
				AreaOperator ao = new AreaOperator(wConn);
				int[] asSystemDiscKey = {Area.SYSTEM_DISC_KEY_ASRS};
				asAreaNo = ao.getAreaNo(asSystemDiscKey);
			}
			catch (ScheduleException se)
			{
				//#CM8995
				// Error is not generated when there is no AS/RS Area.
			}
			
			//#CM8996
			// Create the content of the data file until search result is lost.
			Stock[] stock = null;
			while (sFinder.isNext())
			{
				//#CM8997
				// Output every 100 search results.
				stock = (Stock[]) sFinder.getEntities(100);
				//#CM8998
				// Create contents to be output to the file
				for (int i = 0; i < stock.length; i++)
				{
					wStrText.append(re + "");

					//#CM8999
					// Consignor Code
					wStrText.append(ReportOperation.format(stock[i].getConsignorCode()) + tb);
					//#CM9000
					// Consignor Name
					wStrText.append(ReportOperation.format(wConsignorName) + tb);
					//#CM9001
					// Storage Date
					wStrText.append(WmsFormatter.toParamDate(stock[i].getInstockDate()) + tb);
					//#CM9002
					// Storage Time
					wStrText.append(WmsFormatter.getTimeFormat(stock[i].getInstockDate(), "") + tb);
					//#CM9003
					// Item Code
					wStrText.append(ReportOperation.format(stock[i].getItemCode()) + tb);
					//#CM9004
					// Item Name
					wStrText.append(ReportOperation.format(stock[i].getItemName1()) + tb);
					//#CM9005
					// Division
					wStrText.append(ReportOperation.format(
							DisplayUtil.getPieceCaseValue(stock[i].getCasePieceFlag()))+ tb);
					//#CM9006
					// Location 
					wStrText.append(ReportOperation.format(
							WmsFormatter.toDispLocation(
							stock[i].getLocationNo(), stock[i].getAreaNo(), asAreaNo)) + tb);
					//#CM9007
					// Packed Qty per Case
					wStrText.append(stock[i].getEnteringQty() + tb);
					//#CM9008
					// Packed Qty per Bundle
					wStrText.append(stock[i].getBundleEnteringQty() + tb);
					//#CM9009
					// Stock Case Qty
					wStrText.append(DisplayUtil.getCaseQty(
							stock[i].getStockQty(), stock[i].getEnteringQty(), stock[i].getCasePieceFlag()) + tb);
					//#CM9010
					// Stock Piece Qty
					wStrText.append(DisplayUtil.getPieceQty(
							stock[i].getStockQty(), stock[i].getEnteringQty(), stock[i].getCasePieceFlag()) + tb);
					//#CM9011
					// Case ITF
					wStrText.append(ReportOperation.format(stock[i].getItf()) + tb);
					//#CM9012
					// Bundle ITF
					wStrText.append(ReportOperation.format(stock[i].getBundleItf()) + tb);
					//#CM9013
					// Expiry Date
					wStrText.append(ReportOperation.format(stock[i].getUseByDate()) + tb);
					//#CM9014
					// Output the data to a file.
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
				}
			}
			//#CM9015
			// Close the stream
			wPWriter.close();

			if (!executeUCX(JOBID_ITEM_DEADSTOCK))
			{
				//#CM9016
				// Printing failed after setup. Please refer to log.
				return false;
			}

			//#CM9017
			// Relocate a data file successfully printed to a buckup folder.
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException e)
		{
			//#CM9018
			// 6007034 = Printing failed after setup. Please refer to log.
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM9019
				// Execute close Process of opened database cursor.
				sFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM9020
				// Database error occurred.Please refer to log.
				setMessage("6007002");
				return false;
			}
		}
		return true;
	}

	//#CM9021
	// Package methods -----------------------------------------------

	//#CM9022
	// Protected methods ---------------------------------------------

	//#CM9023
	// Private methods -----------------------------------------------

	//#CM9024
	/**
	 * This Method sets Consignor Code and Storage Date to Search Key. <BR>
	 * Set also the field name of data coming out by print. <BR>
	 * 
	 * @param sKey Search key of Stock Info
	 * @throws ReadWriteException Inform when data connection error occurs.
	 */
	private void setDeadStockSearchKey(StockSearchKey sKey) throws ReadWriteException
	{
		//#CM9025
		// Consignor Code
		if (!StringUtil.isBlank(wConsignorCode))
		{
			sKey.setConsignorCode(wConsignorCode);
		}

		if (!StringUtil.isBlank(getStorageDate()))
		{
			sKey.setInstockDate(getStorageDate(), "<");
		}
		
		//#CM9026
		//Stock Packed Qty > 0
		sKey.setStockQty(0, ">");
		//#CM9027
		//status = Center Inventory
		sKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);

		//#CM9028
		// Display Order
		//#CM9029
		// Consignor Code
		sKey.setConsignorCodeCollect("");
		//#CM9030
		// Consignor Name
		sKey.setConsignorNameCollect("");
		//#CM9031
		// Storage Date
		sKey.setInstockDateCollect("");
		//#CM9032
		// Item Code
		sKey.setItemCodeCollect("");
		//#CM9033
		// Item Name
		sKey.setItemName1Collect("");
		//#CM9034
		// Case/Piece division
		sKey.setCasePieceFlagCollect("");
		//#CM9035
		// Location
		sKey.setLocationNoCollect("");
		//#CM9036
		// Case Qty
		sKey.setEnteringQtyCollect("");
		//#CM9037
		// Piece Qty
		sKey.setBundleEnteringQtyCollect("");
		//#CM9038
		// Stock Qty
		sKey.setStockQtyCollect("");
		//#CM9039
		// Case ITF
		sKey.setItfCollect("");
		//#CM9040
		// Bundle ITF
		sKey.setBundleItfCollect("");
		//#CM9041
		// Expiry Date
		sKey.setUseByDateCollect("");
		//#CM9042
		// Area No. 
		sKey.setAreaNoCollect("");
		//#CM9043
		// Display Sequence
		sKey.setInstockDateOrder(1, true);
		sKey.setItemCodeOrder(2, true);
		sKey.setCasePieceFlagOrder(3, true);
		sKey.setLocationNoOrder(4, true);

	}

	//#CM9044
	/**
	 * This method obtains Consignor Name from DMSTOCK table.<BR>
	 * Read the newest data according to the registration date if there is different Consignor Name with the same Consignor Code.
	 * 
	 * @throws ReadWriteException Inform when data connection error occurs.
	 */
	private void getDisplayConsignorName() throws ReadWriteException
	{
		//#CM9045
		// Generate FinderInstance
		StockReportFinder consignorFinder = new StockReportFinder(wConn);
		StockSearchKey sKey = new StockSearchKey();
		
		//#CM9046
		// Set Search condition.
		//#CM9047
		// Consignor Code
		if (!StringUtil.isBlank(wConsignorCode))
		{
			sKey.setConsignorCode(wConsignorCode);
		}

		//#CM9048
		// Stock Packed Qty > 0
		sKey.setStockQty(0, ">");
		//#CM9049
		// status = Center Inventory
		sKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		sKey.setRegistDateOrder(1, false);

		//#CM9050
		// Consignor NameSearch
		consignorFinder.open();
		if (consignorFinder.search(sKey) > 0)
		{
			Stock[] stock = (Stock[]) consignorFinder.getEntities(1);

			if (stock != null && stock.length != 0)
			{
				wConsignorName = stock[0].getConsignorName();
			}
		}
		consignorFinder.close();
	}

}
