// $Id: ItemWorkingInquiryWriter.java,v 1.5 2006/12/13 09:02:18 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.report;

//#CM9211
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.DateOperator;
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

//#CM9212
/**
 * Designer : T.Nakai <BR>
 * Maker : T.Nakai <BR>
 * <BR>
 * This is the class to create reporting data file for stock List per item and to invoke print execute class.<BR>
 * Search a Stock Info matching to the content set in the schedule class and creates a ticket data file.<BR>
 * This class processes as follows.<BR>
 * <BR>
 * a ticket data file creation Process(<CODE>startPrint()</CODE>Method)<BR>
 * <DIR>
 *   Search for a Stock Info.<BR>
 *   Create no report data file when no corresponding data was found.<BR>
 *   If corresponding data exists, creates a report data file and invokes a print execution class .<BR>
 * <BR>
 *   ＜Search condition＞
 *   <DIR>
 *     Consignor Code<BR>
 *     Start Item Code<BR>
 *     End Item Code<BR>
 *     Case/Piece Division<BR>
 *     Center Inventory<BR>
 *     Stock Qty：1 or more<BR>
 *   </DIR>
 *    ＜Extracted item(count)＞ <BR>
 * 	<DIR>
 *     Consignor Code<BR>
 *     Consignor Name<BR>
 *     Item Code<BR>
 *     Item Name<BR>
 *     Packed Qty per Case<BR>
 *     Packed Qty per Bundle<BR>
 *     Stock Case Qty<BR>
 *     Stock Piece Qty<BR>
 *     Allocatable Case Qty<BR>
 *     Allocatable Piece Qty<BR>
 *     LocationNo
 *     Division<BR>
 *     Case ITF<BR>
 *     Bundle ITF<BR> 
 *     Storage Date
 *  </DIR>
 * </DIR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/24</TD><TD>T.Nakai</TD><TD>New Creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2006/12/13 09:02:18 $
 * @author  $Author: suresh $
 */
public class ItemWorkingInquiryWriter extends CSVWriter
{
	//#CM9213
	// Class fields --------------------------------------------------

	//#CM9214
	// Class variables -----------------------------------------------
	//#CM9215
	/**
	 * Consignor Code
	 */
	private String wConsignorCode;

	//#CM9216
	/**
	 * Start Item Code
	 */
	private String wFromItemCode;

	//#CM9217
	/**
	 * End Item Code
	 */
	private String wToItemCode;

	//#CM9218
	/**
	 * Case/Piece division
	 */
	private String wCasePieceFlag;

	//#CM9219
	// Class method --------------------------------------------------
	//#CM9220
	/**
	 * return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.5 $,$Date: 2006/12/13 09:02:18 $");
	}

	//#CM9221
	// Constructors --------------------------------------------------
	//#CM9222
	/**
	 * ShippingWriter Construct Object.<BR>
	 * Set Connection and locale.<BR>
	 * @param conn <CODE>Connection</CODE> Connection object with that database.
	 */
	public ItemWorkingInquiryWriter(Connection conn)
	{
		super(conn);
	}

	//#CM9223
	// Public methods ------------------------------------------------
	//#CM9224
	/**
	 * Set Serch value for Consignor Code.
	 * @param consignorcode Consignor Code to be set
	 */
	public void setConsignorCode(String consignorcode)
	{
		wConsignorCode = consignorcode;
	}

	//#CM9225
	/**
	 * Obtain Consignor Code.
	 * @return Consignor Code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM9226
	/**
	 * Set Search value for Start Item Code.
	 * @param fromItemCode Set Start Item Code
	 */
	public void setFromItemCode(String fromItemCode)
	{
		wFromItemCode = fromItemCode;
	}

	//#CM9227
	/**
	 * Obtain Start Item Code.
	 * @return Start Item Code
	 */
	public String getFromItemCode()
	{
		return wFromItemCode;
	}

	//#CM9228
	/**
	 * Set Search value for End Item Code.
	 * @param toItemCode Set End Item Code
	 */
	public void setToItemCode(String toItemCode)
	{
		wToItemCode = toItemCode;
	}

	//#CM9229
	/**
	 * Obtain End Item Code.
	 * @return End Item Code
	 */
	public String getToItemCode()
	{
		return wToItemCode;
	}

	//#CM9230
	/**
	 * Set Search value for Case/Piece division.
	 * @param casepeiceflag Case/Piece division to be set
	 */
	public void setCasePieceFlag(String casepieceflag)
	{
		wCasePieceFlag = casepieceflag;
	}

	//#CM9231
	/**
	 * Obtain Case/Piece division.
	 * @return Case/Piece division
	 */
	public String getCasePeiceFlag()
	{
		return wCasePieceFlag;
	}

	//#CM9232
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
		//#CM9233
		// Set Search condition and obtain count.
		setStockSearchKey(searchKey);
		return instockHandle.count(searchKey);

	}

	//#CM9234
	/**
	 * Create the CSV file for stock List per item and execute invoking print execute class.<BR>
	 * Search Work status from the search condition input from Search screen.
	 * Not execute print process when search result is less than one.<BR>
	 * Return true when print Process is normal and return false when print Process is failed.<BR>
	 * 
	 * @return boolean if print was successfully executed.
	 */
	public boolean startPrint()
	{
		StockReportFinder stockReportFinder = new StockReportFinder(wConn);
		try
		{
			//#CM9235
			// Execute search

			StockSearchKey stockSearchKey = new StockSearchKey();

			//#CM9236
			// Set a search key.
			setStockSearchKey(stockSearchKey);

			//#CM9237
			// Display Sequence
			stockSearchKey.setItemCodeOrder(1, true);
			stockSearchKey.setCasePieceFlagOrder(2, true);
			stockSearchKey.setLocationNoOrder(3, true);
			stockSearchKey.setUseByDateOrder(4, true);

			//#CM9238
			// Not execute print process when there is no data.
			if (stockReportFinder.search(stockSearchKey) <= 0)
			{
				//#CM9239
				// 6003010 = Print data was not found.
				wMessage = "6003010";
				return false;
			}

			//#CM9240
			// Obtain AS/RS Area No.of the area in order to display hyphenation 
			String[] asAreaNo = null;
			try
			{
				AreaOperator ao = new AreaOperator(wConn);
				int[] asSystemDiscKey = { Area.SYSTEM_DISC_KEY_ASRS };
				asAreaNo = ao.getAreaNo(asSystemDiscKey);
			}
			catch (ScheduleException se)
			{
				//#CM9241
				// Error is not generated when there is no AS/RS Area.
			}

			if (!createPrintWriter(FNAME_ITEM_ITEMWORK))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_ITEM_ITEMWORK);

			//#CM9242
			// Create the content of the data file until search result is lost.
			Stock[] stock = null;
			while (stockReportFinder.isNext())
			{
				//#CM9243
				// Output every 100 search results.
				stock = (Stock[]) stockReportFinder.getEntities(100);

				//#CM9244
				// Create contents to be output to the file.
				for (int i = 0; i < stock.length; i++)
				{

					wStrText.append(re + "");
					//#CM9245
					// Consignor Code
					wStrText.append(ReportOperation.format(stock[i].getConsignorCode()) + tb);
					//#CM9246
					// Consignor Name
					wStrText.append(ReportOperation.format(stock[i].getConsignorName()) + tb);
					//#CM9247
					// Item Code
					wStrText.append(ReportOperation.format(stock[i].getItemCode()) + tb);
					//#CM9248
					// Item Name
					wStrText.append(ReportOperation.format(stock[i].getItemName1()) + tb);
					//#CM9249
					// Packed Qty per Case
					wStrText.append(stock[i].getEnteringQty() + tb);
					//#CM9250
					// Packed Qty per Bundle
					wStrText.append(stock[i].getBundleEnteringQty() + tb);
					//#CM9251
					// Stock Case Qty
					wStrText.append(
						DisplayUtil.getCaseQty(
							stock[i].getStockQty(),
							stock[i].getEnteringQty(),
							stock[i].getCasePieceFlag())
							+ tb);
					//#CM9252
					// Stock Piece Qty
					wStrText.append(
						DisplayUtil.getPieceQty(
							stock[i].getStockQty(),
							stock[i].getEnteringQty(),
							stock[i].getCasePieceFlag())
							+ tb);
					//#CM9253
					// Allocatable Case Qty
					wStrText.append(
						DisplayUtil.getCaseQty(
							stock[i].getAllocationQty(),
							stock[i].getEnteringQty(),
							stock[i].getCasePieceFlag())
							+ tb);
					//#CM9254
					// Allocatable Piece Qty
					wStrText.append(
						DisplayUtil.getPieceQty(
							stock[i].getAllocationQty(),
							stock[i].getEnteringQty(),
							stock[i].getCasePieceFlag())
							+ tb);
					//#CM9255
					// Division
					wStrText.append(DisplayUtil.getPieceCaseValue(stock[i].getCasePieceFlag()) + tb);
					//#CM9256
					// Location No.
					wStrText.append(
						ReportOperation.format(
							WmsFormatter.toDispLocation(stock[i].getLocationNo(), stock[i].getAreaNo(), asAreaNo))
							+ tb);
					//#CM9257
					// Case ITF
					wStrText.append(stock[i].getItf() + tb);
					//#CM9258
					// Bundle ITF
					wStrText.append(stock[i].getBundleItf() + tb);
					//#CM9259
					// Storage Date
					wStrText.append(DateOperator.changeDate(stock[i].getInstockDate()) + tb);
					//#CM9260
					// Storage Time
					wStrText.append(DateOperator.changeDateTime(stock[i].getInstockDate()).substring(11) + tb);
					//#CM9261
					// Expiry Date
					wStrText.append(ReportOperation.format(stock[i].getUseByDate()));

					//#CM9262
					// Output the data to a file.
					wPWriter.print(wStrText);

					wStrText.setLength(0);

				}
			}

			//#CM9263
			// Close the stream
			wPWriter.close();

			//#CM9264
			// Execute UCXSingle. (Start printing)
			if (!executeUCX(JOBID_ITEM_ITEMWORK))
			{
				//#CM9265
				// Printing failed after setup. Please refer to log.
				return false;
			}

			//#CM9266
			// Relocate a data file successfully printed to a buckup folder.
			ReportOperation.createBackupFile(wFileName);

			//#CM9267
			// 6001010=Print completed successfully.
			setMessage("6001010");
		}
		catch (ReadWriteException e)
		{
			//#CM9268
			// 6007034 = Printing failed after setup. Please refer to log.
			setMessage("6007034");

			return false;
		}
		finally
		{
			try
			{
				stockReportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM9269
				// Database error occurred.Please refer to log.
				setMessage("6007002");
				return false;

			}
		}

		return true;
	}

	//#CM9270
	// Package methods -----------------------------------------------

	//#CM9271
	// Protected methods ---------------------------------------------

	//#CM9272
	// Private methods -----------------------------------------------
	//#CM9273
	/**
	 * This is a method to set a search condition for Stock search key.<BR>
	 * 
	 * @param searchKey StockSearchKey Search key of Stock Info
	 * @throws ReadWriteException Inform when data connection error occurs.
	 */
	private void setStockSearchKey(StockSearchKey serachKey) throws ReadWriteException
	{
		//#CM9274
		// Consignor Code
		if (!StringUtil.isBlank(wConsignorCode))
		{
			serachKey.setConsignorCode(wConsignorCode);
		}
		//#CM9275
		// Start Item Code
		if (!StringUtil.isBlank(wFromItemCode))
		{
			serachKey.setItemCode(wFromItemCode, ">=");
		}
		//#CM9276
		// End Item Code
		if (!StringUtil.isBlank(wToItemCode))
		{
			serachKey.setItemCode(wToItemCode, "<=");
		}
		//#CM9277
		// Case/Piece division
		if (!StringUtil.isBlank(wCasePieceFlag))
		{
			serachKey.setCasePieceFlag(wCasePieceFlag);
		}
		//#CM9278
		// Center Inventory
		serachKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM9279
		// stock qty is 1 or larger
		serachKey.setStockQty(0, ">");

	}
}

//#CM9280
//end of class
