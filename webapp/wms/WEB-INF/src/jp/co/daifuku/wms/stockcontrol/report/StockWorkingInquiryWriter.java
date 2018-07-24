// $Id: StockWorkingInquiryWriter.java,v 1.5 2006/12/13 09:02:17 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.report;

//#CM9382
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
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM9383
/**
 * Designer : T.Nakai <BR>
 * Maker : T.Nakai <BR>
 * <BR>
 * This class creates a ticket data file of Stock List by Location, and invokes a print execution class.<BR>
 * Searches a Stock Info matching to the content set in the schedule class and creates a ticket data file.<BR>
 * This class processes as follows.<BR>
 * <BR>
 * Creates a ticket data file (<CODE>startPrint()</CODE>Method)<BR>
 * <DIR>
 *   Search for a Stock Info.<BR>
 *   Create no report data file when no corresponding data was found.<BR>
 *   If corresponding data exists, creates a report data file and invokes a print execution class.<BR>
 * <BR>
 *   ＜Search condition＞
 *   <DIR>
 *     Consignor Code<BR>
 *     Start Location<BR>
 *     End Location<BR>
 *     Item Code<BR>
 *     Case/Piece Division<BR>
 *   </DIR>
 *    ＜Extracted item(count)＞ <BR>
 * 	<DIR>
 *     Consignor Code<BR>
 *     Consignor Name<BR>
 *     LocationNo
 *     Item Code<BR>
 *     Item Name<BR>
 *     Packed Qty per Case<BR>
 *     Packed Qty per Bundle<BR>
 *     Stock Case Qty<BR>
 *     Stock Piece Qty<BR>
 *     Allocatable Case Qty<BR>
 *     Allocatable Piece Qty<BR>
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
 * <TR><TD>2004/09/16</TD><TD>T.Nakai</TD><TD>New Creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2006/12/13 09:02:17 $
 * @author  $Author: suresh $
 */
public class StockWorkingInquiryWriter extends CSVWriter
{
	//#CM9384
	// Class fields --------------------------------------------------

	//#CM9385
	// Class variables -----------------------------------------------
	//#CM9386
	/**
	 * Consignor Code
	 */
	private String wConsignorCode;

	//#CM9387
	/**
	 * Start Location
	 */
	private String wFromLocationNo ;

	//#CM9388
	/**
	 * End Location
	 */
	private String wToLocationNo ;

	//#CM9389
	/**
	 * Item Code
	 */
	private String wItemCode ;

	//#CM9390
	/**
	 * Case/Piece division
	 */
	private String wCasePieceFlag ;
	
	//#CM9391
	/**
	 * Expiry Date
	 */	
	private String wUseByDate ;

	//#CM9392
	// Class method --------------------------------------------------
	//#CM9393
	/**
	 * return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.5 $,$Date: 2006/12/13 09:02:17 $");
	}

	//#CM9394
	// Constructors --------------------------------------------------
	//#CM9395
	/**
	 * StockWorkingInquiryWriter Construct Object.<BR>
	 * Set Connection and locale.<BR>
	 * 
	 * @param conn <CODE>Connection</CODE> Connection object with the database
	 */
	public StockWorkingInquiryWriter(Connection conn)
	{
		super(conn);
	}

	//#CM9396
	// Public methods ------------------------------------------------
	//#CM9397
	/**
	 * Set Search value for Consignor Code.
	 * @param consignorcode Consignor Code to be set
	 */
	public void setConsignorCode(String consignorcode)
	{
		wConsignorCode = consignorcode;
	}

	//#CM9398
	/**
	 * Obtain Consignor Code.
	 * @return Consignor Code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM9399
	/**
	 * Set Search value for Start Location.
	 * @param fromLocationNo Start Location to be set
	 */
	public void setFromLocationNo(String fromLocationNo)
	{
		wFromLocationNo = fromLocationNo;
	}

	//#CM9400
	/**
	 * Obtain Start Location.
	 * @return Start Location
	 */
	public String getFromLocationNo()
	{
		return wFromLocationNo;
	}

	//#CM9401
	/**
	 * Set Search value for End Location.
	 * @param tolocationNo End Location to be set
	 */
	public void setToLocationNo(String tolocationNo)
	{
		wToLocationNo = tolocationNo;
	}

	//#CM9402
	/**
	 * Obtain End Location.
	 * @return End Location
	 */
	public String getToLocationNo()
	{
		return wToLocationNo;
	}

	//#CM9403
	/**
	 * Set Search value for Item Code.
	 * @param itemCode Item Code to be set
	 */
	public void setItemCode(String itemCode)
	{
		wItemCode = itemCode;
	}

	//#CM9404
	/**
	 * Obtain Item Code.
	 * @return Item Code
	 */
	public String getItemCode()
	{
		return wItemCode;
	}

	//#CM9405
	/**
	 * Set Search value for Case/Piece division.
	 * @param casepeiceflag Case/Piece division to be set
	 */
	public void setCasePieceFlag(String casepieceflag)
	{
		wCasePieceFlag = casepieceflag;
	}

	//#CM9406
	/**
	 * Obtain Case/Piece division.
	 * @return Case/Piece division
	 */
	public String getCasePieceFlag()
	{
		return wCasePieceFlag;
	}

	//#CM9407
	/**
	 * Set Search value for Expiry Date.
	 * @param casepeiceflag Expiry Date  to be set
	 */
	public void setUseByDate(String usebydate)
	{
		wUseByDate = usebydate;
	}

	//#CM9408
	/**
	 * Obtain the Expiry Date.
	 * @return Expiry Date
	 */
	public String getUseByDate()
	{
		return wUseByDate;
	}
	
	//#CM9409
	/**
	 * Obtain print data count.<BR>
	 * Use it to determine for the search result to allow printing.<BR>
	 * Use this method from a schedule class for display.<BR>
	 * 
	 * @return Count of print data
	 * @throws ReadWriteException Inform when data connection error occurs.
	 * @throws ScheduleException  Inform when unexpected exception occurs in the check Process.
	 */
	public int count() throws ReadWriteException, ScheduleException
	{
		int count = 0;
		
		StockHandler instockHandle = new StockHandler(wConn);
		StockSearchKey searchKey = new StockSearchKey();
		//#CM9410
		// Set Search condition and obtain Count.
		setStockSearchKey(searchKey);
		count = instockHandle.count(searchKey);

		return count;

	}
	
	//#CM9411
	/**
	 * create CSV file for stock list per Location and invoke Start printing calss.<BR>
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
			//#CM9412
			// Execute search
			StockSearchKey stockSearchKey = new StockSearchKey();

			//#CM9413
			// Set a search key.
			stockSearchKey = setStockSearchKey(stockSearchKey);

			//#CM9414
			// Display Sequence
			stockSearchKey.setLocationNoOrder(1,true);
			stockSearchKey.setItemCodeOrder(2,true);
			stockSearchKey.setCasePieceFlagOrder(3, true);
			stockSearchKey.setUseByDateOrder(4, true);
			
			//#CM9415
			// Not execute print process when there is no data.
			if(stockReportFinder.search(stockSearchKey) <= 0 )
			{
				//#CM9416
				// 6003010 = Print data was not found.
				wMessage = "6003010";
				return false;
			}

			if(!createPrintWriter(FNAME_ITEM_LOCATIONWORK))
			{
				return false;
			}
			
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_ITEM_LOCATIONWORK);
            
			String consignorName = getConsignorName();

			//#CM9417
			// Create the content of the data file until search result is lost.
			Stock[] stock = null;
			while (stockReportFinder.isNext())
			{
				//#CM9418
				// Output every 100 search results.
				stock = (Stock[])stockReportFinder.getEntities(100);

				//#CM9419
				// Create contents to be output to the file.
				for (int i = 0; i < stock.length; i++)
				{
					//#CM9420
					// Preserve Allocatable Qty
					int allocateqty = stock[i].getAllocationQty();
					//#CM9421
					// Preserve Case/Piece division
					String casepieceFlag = stock[i].getCasePieceFlag();
					//#CM9422
					// Preserve Packed Qty per Case
					int enteringQty = stock[i].getEnteringQty();
					
					wStrText.append(re + "");
					//#CM9423
					// Consignor Code
					wStrText.append(ReportOperation.format(stock[i].getConsignorCode()) + tb);
					//#CM9424
					// Consignor Name
					wStrText.append(ReportOperation.format(consignorName) + tb);
					//#CM9425
					// Location No.
					wStrText.append(ReportOperation.format(stock[i].getLocationNo()) + tb);
					//#CM9426
					// Item Code
					wStrText.append(ReportOperation.format(stock[i].getItemCode()) + tb);
					//#CM9427
					// Item Name
					wStrText.append(ReportOperation.format(stock[i].getItemName1()) + tb);
					//#CM9428
					// Packed Qty per Case
					wStrText.append(enteringQty + tb);
					//#CM9429
					// Packed Qty per Bundle
					wStrText.append(stock[i].getBundleEnteringQty() + tb);
					
					//#CM9430
					// Stock Case Qty
					wStrText.append(DisplayUtil.getCaseQty(stock[i].getStockQty(), enteringQty, casepieceFlag) + tb);
					//#CM9431
					// Stock Piece Qty
					wStrText.append(DisplayUtil.getPieceQty(stock[i].getStockQty(), enteringQty, casepieceFlag) + tb);
					//#CM9432
					// Allocatable Case Qty
					wStrText.append(DisplayUtil.getCaseQty(allocateqty, enteringQty, casepieceFlag) + tb);
					//#CM9433
					// Allocatable Piece Qty
					wStrText.append(DisplayUtil.getPieceQty(allocateqty, enteringQty, casepieceFlag) + tb);
					
					//#CM9434
					// Division
					wStrText.append(DisplayUtil.getPieceCaseValue(casepieceFlag) + tb);
					//#CM9435
					// Case ITF
					wStrText.append(stock[i].getItf() + tb);
					//#CM9436
					// Bundle ITF
					wStrText.append(stock[i].getBundleItf() + tb);
					//#CM9437
					// Storage Date
					wStrText.append(DateOperator.changeDate(stock[i].getInstockDate()) + tb);
					//#CM9438
					// Storage Time
					wStrText.append(DateOperator.changeDateTime(stock[i].getInstockDate()).substring(11) + tb);
					//#CM9439
					// Expiry Date
					wStrText.append(ReportOperation.format(stock[i].getUseByDate()));

					//#CM9440
					// Output the data to a file.
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
				}
			}

			//#CM9441
			// Close the stream
			wPWriter.close();

			//#CM9442
			// Execute UCXSingle. (Start printing)
			if (!executeUCX(JOBID_ITEM_LOCATIONWORK))
			{
				//#CM9443
				// Printing failed after setup. Please refer to log.
				return false;
			}

			//#CM9444
			// Relocate a data file successfully printed to a buckup folder.
			ReportOperation.createBackupFile(wFileName);

			//#CM9445
			// 6001010=Print completed successfully.
			setMessage("6001010");
		}
		catch (ReadWriteException e)
		{
			//#CM9446
			// 6007034 = Printing failed after setup. Please refer to log.
			setMessage("6007034");

			return false;
		}
		catch (ScheduleException e)
		{
			//#CM9447
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
				//#CM9448
				// Database error occurred. Please refer to log.
				setMessage("6007002");
				return false;
			}
		}

		return true;
	}

	//#CM9449
	// Package methods -----------------------------------------------

	//#CM9450
	// Protected methods ---------------------------------------------

	//#CM9451
	// Private methods -----------------------------------------------
	//#CM9452
	/**
	 * This is a method to set a search condition for Stock search key.<BR>
	 * 
	 * @param searchKey StockSearchKey Search key of Stock Info
	 * @return Search key of Stock Info
	 * @throws ReadWriteException Inform when data connection error occurs.
	 * @throws ScheduleException  Inform when unexpected exception occurs in the check Process.
	 */
	private StockSearchKey setStockSearchKey(StockSearchKey searchKey) throws ReadWriteException, ScheduleException
	{
		//#CM9453
		// Consignor Code
		if(!StringUtil.isBlank(wConsignorCode))
		{
			searchKey.setConsignorCode(wConsignorCode);
		}
		//#CM9454
		// Start Location
		if(!StringUtil.isBlank(wFromLocationNo))
		{
			searchKey.setLocationNo(wFromLocationNo, ">=");
		}
		//#CM9455
		// End Location
		if(!StringUtil.isBlank(wToLocationNo))
		{
			searchKey.setLocationNo(wToLocationNo, "<=");
		}
		//#CM9456
		// Item Code
		if(!StringUtil.isBlank(wItemCode))
		{
			searchKey.setItemCode(wItemCode);
		}
		//#CM9457
		// Case/Piece division
		if(!StringUtil.isBlank(wCasePieceFlag))
		{
			searchKey.setCasePieceFlag(wCasePieceFlag);
		}
		//#CM9458
		// Stock status(Center Inventory)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM9459
		// stock qty must be 1 or more.
		searchKey.setStockQty(1, ">=");

		AreaOperator AreaOperator = new AreaOperator(wConn);	
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
		//#CM9460
		// Obtain Area other than ASRS and add to a search condition.
		//#CM9461
		// Search IS NULL when no corresponding area found
		areaNo = AreaOperator.getAreaNo(areaType);
		searchKey.setAreaNo(areaNo);
		
		return searchKey;
	}
	
	//#CM9462
	/**
	 * Obtain Latest Consignor Name.<BR>
	 * 
	 * @return Latest Consignor Name
	 * @throws ReadWriteException Inform when data connection error occurs.
	 * @throws ScheduleException  Inform when unexpected exception occurs during checking.
	 */
	private String getConsignorName() throws ReadWriteException, ScheduleException
	{
		//#CM9463
		// Consignor Name
		String consignorName = "";
		
		StockSearchKey nameSearchKey = new StockSearchKey();
		StockReportFinder nameFinder = new StockReportFinder(wConn);
		
		//#CM9464
		// Set a search condition
		nameSearchKey.KeyClear();
		nameSearchKey.setConsignorCode(wConsignorCode);
		nameSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		nameSearchKey.setStockQty(1, ">=");				
		nameSearchKey.setLastUpdateDateOrder(1, false);
		AreaOperator AreaOperator = new AreaOperator(wConn);	
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
		//#CM9465
		// Obtain Area other than ASRS and add to a search condition.
		//#CM9466
		// Search IS NULL when no corresponding area found
		areaNo = AreaOperator.getAreaNo(areaType);
		nameSearchKey.setAreaNo(areaNo);
		
		//#CM9467
		// Start searching
		nameFinder.open();
		if(nameFinder.search(nameSearchKey) > 0)
		{
			Stock[] nameGet = (Stock[])nameFinder.getEntities(1);
			if (nameGet != null && nameGet.length != 0)
			{
				consignorName = nameGet[0].getConsignorName();
			}	
		}
		nameFinder.close();
		return consignorName;
	}
	

}

//#CM9468
//end of class
