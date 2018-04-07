// $Id: AsLocationWorkingInquiryWriter.java,v 1.5 2006/12/13 09:03:19 suresh Exp $

//#CM43370
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.report;

import java.sql.Connection;

import jp.co.daifuku.common.DateOperator;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;

//#CM43371
/**
 * Designer : Y.Osawa <BR>
 * Maker : Y.Osawa <BR>
 * <BR>
 * The class to make the data file for the slit of the stock list according to the ASRS location, and to call the print execution class. <BR>
 * Retrieve the inventory information by the content set in the schedule class, and make the data file for the slit. <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * Data file making processing for slit(<CODE>startPrint() </CODE> method)<BR>
 * <DIR>
 *   Retrieve the inventory information. <BR>
 *   Do not make the data file for the slit when pertinent data does not exist. <BR>
 *   Make the data file for the slit when pertinent data exists, and call the print execution class. <BR>
 * <BR>
 *   <Search condition>
 *   <DIR>
 *     Warehouse(area No.) <BR>
 *     Consignor Code<BR>
 *     Beginning shelf<BR>
 *     End shelf<BR>
 *     Item Code<BR>
 *     Case/Piece flag<BR>
 *   </DIR>
 *    <extraction item> <BR>
 * 	<DIR>
 *     Warehouse (area No.)<BR>
 *     Consignor Code<BR>
 *     Consignor Name<BR>
 *     Shelf No.
 *     Item Code<BR>
 *     Item Name<BR>
 *     Qty per case<BR>
 *     Qty per bundle<BR>
 *     Stock case qty<BR>
 *     Stock piece qty<BR>
 *     Possible drawing case qty<BR>
 *     Possible drawing piece qty<BR>
 *     Flag<BR>
 *     Case ITF<BR>
 *     Bundle ITF<BR> 
 *     Storage date<BR>
 *     Storage time<BR>
 *     Expiry date<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/16</TD><TD>T.Nakai</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2006/12/13 09:03:19 $
 * @author  $Author: suresh $
 */
public class AsLocationWorkingInquiryWriter extends CSVWriter
{
	//#CM43372
	// Class fields --------------------------------------------------

	//#CM43373
	// Class variables -----------------------------------------------
	//#CM43374
	/**
	 * Area No. (Warehouse Station No.)
	 */
	private String wAreaNo;
	
	//#CM43375
	/**
	 * Consignor Code
	 */
	private String wConsignorCode;

	//#CM43376
	/**
	 * Beginning shelf
	 */
	private String wFromLocationNo ;

	//#CM43377
	/**
	 * End shelf
	 */
	private String wToLocationNo ;

	//#CM43378
	/**
	 * Item Code
	 */
	private String wItemCode ;

	//#CM43379
	/**
	 * Case/Piece flag
	 */
	private String wCasePieceFlag ;
	
	//#CM43380
	/**
	 * Expiry date
	 */	
	private String wUseByDate ;

	//#CM43381
	// Class method --------------------------------------------------
	//#CM43382
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.5 $,$Date: 2006/12/13 09:03:19 $");
	}

	//#CM43383
	// Constructors --------------------------------------------------
	//#CM43384
	/**
	 * Construct the AsLocationWorkingInquiryWriter object. <BR>
	 * Set the connection. <BR>
	 * 
	 * @param conn Connection object with <CODE>Connection</CODE> database
	 */
	public AsLocationWorkingInquiryWriter(Connection conn)
	{
		super(conn);
	}

	//#CM43385
	// Public methods ------------------------------------------------
	//#CM43386
	/**
	 * Set the retrieval value in area No.(warehouse Station No).
	 * @param areano Set area No.(warehouse Station No)
	 */
	public void setAreaNo(String areano)
	{
		wAreaNo = areano;
	}

	//#CM43387
	/**
	 * Acquire area No.(warehouse Station No).
	 * @return Area No.(warehouse Station No)
	 */
	public String getAreaNo()
	{
		return wAreaNo;
	}

	//#CM43388
	/**
	 * Set the retrieval value in Consignor Code.
	 * @param consignorcode Consignor Code to be set
	 */
	public void setConsignorCode(String consignorcode)
	{
		wConsignorCode = consignorcode;
	}

	//#CM43389
	/**
	 * Acquire Consignor Code.
	 * @return Consignor Code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM43390
	/**
	 * Set the retrieval value in start shelf.
	 * @param fromLocationNo Start shelf to be set
	 */
	public void setFromLocationNo(String fromLocationNo)
	{
		wFromLocationNo = fromLocationNo;
	}

	//#CM43391
	/**
	 * Acquire Start shelf.
	 * @return Beginning shelf
	 */
	public String getFromLocationNo()
	{
		return wFromLocationNo;
	}

	//#CM43392
	/**
	 * Set the retrieval value in End shelf.
	 * @param tolocationNo End shelf to be set
	 */
	public void setToLocationNo(String tolocationNo)
	{
		wToLocationNo = tolocationNo;
	}

	//#CM43393
	/**
	 * Acquire End shelf.
	 * @return End shelf
	 */
	public String getToLocationNo()
	{
		return wToLocationNo;
	}

	//#CM43394
	/**
	 * Set the retrieval value in Item Code.
	 * @param itemCode Item Code to be set
	 */
	public void setItemCode(String itemCode)
	{
		wItemCode = itemCode;
	}

	//#CM43395
	/**
	 * Acquire Item Code.
	 * @return Item Code
	 */
	public String getItemCode()
	{
		return wItemCode;
	}

	//#CM43396
	/**
	 * Set the retrieval value in Case/Piece flag.
	 * @param casepieceflag Case/Piece flag to be set
	 */
	public void setCasePieceFlag(String casepieceflag)
	{
		wCasePieceFlag = casepieceflag;
	}

	//#CM43397
	/**
	 * Acquire Case/Piece flag.
	 * @return Case/Piece flag
	 */
	public String getCasePieceFlag()
	{
		return wCasePieceFlag;
	}

	//#CM43398
	/**
	 * Set the retrieval value in Expiry date.
	 * @param usebydate Expiry date to be set
	 */
	public void setUseByDate(String usebydate)
	{
		wUseByDate = usebydate;
	}

	//#CM43399
	/**
	 * Acquire Expiry date.
	 * @return Expiry date
	 */
	public String getUseByDate()
	{
		return wUseByDate;
	}
	
	//#CM43400
	/**
	 * Acquire the number of cases of the print data. <BR>
	 * Use it to judge whether to do the print processing by this retrieval result. <BR>
	 * This method is used by the schedule class which processes the screen. <BR>
	 * 
	 * @return Print data number
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	public int count() throws ReadWriteException
	{
		StockHandler instockHandle = new StockHandler(wConn);
		StockSearchKey searchKey = new StockSearchKey();
		//#CM43401
		// Set the search condition, and acquire the number.
		setStockSearchKey(searchKey);
		return instockHandle.count(searchKey);

	}
	
	//#CM43402
	/**
	 * Make the CSV file for the stock list according to the ASRS location, and call the print execution class. <BR>
	 * Retrieve work information on the search condition input on the screen. <BR>
	 * Do not print when the retrieval result is less than one. <BR>
	 * @return True when the printing job ends normally and False when failing.
	 */
	public boolean startPrint()
	{
		StockReportFinder reportFinder = new StockReportFinder(wConn);
		try
		{
			//#CM43403
			// Retrieval execution
			StockSearchKey stkKey = new StockSearchKey();

			//#CM43404
			// The retrieval key is set. 
			stkKey = setStockSearchKey(stkKey);

			//#CM43405
			// The order of display
			stkKey.setLocationNoOrder(1,true);
			stkKey.setItemCodeOrder(2,true);
			stkKey.setCasePieceFlagOrder(3, true);
			stkKey.setUseByDateOrder(4, true);
			
			//#CM43406
			// Do not do the print processing when there is no data.
			if(reportFinder.search(stkKey) <= 0 )
			{
				//#CM43407
				// 6003010 = There was no print data.
				wMessage = "6003010";
				return false;
			}

			if(!createPrintWriter(FNAME_ASRSLOCATIONWORKINGINQUIRY))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_ASRSLOCATIONWORKINGINQUIRY);
			
			String consignorName = getConsignorName();

			//#CM43408
			// Make the content of the data file until the retrieval result is lost.
			Stock[] stock = null;
			while (reportFinder.isNext())
			{
				//#CM43409
				// Output 100 retrieval results.
				stock = (Stock[])reportFinder.getEntities(100);

				//#CM43410
				// Make the content output to the data file.
				for (int i = 0; i < stock.length; i++)
				{
					//#CM43411
					// Maintain the number which can be drawn.
					int allocateqty = stock[i].getAllocationQty();
					//#CM43412
					// Maintain Case/Piece flag.
					String casepieceFlag = stock[i].getCasePieceFlag();
					//#CM43413
					// Maintain Qty per case. 
					int enteringQty = stock[i].getEnteringQty();
					
					wStrText.append(re + "");
					//#CM43414
					// Warehouse
					ASFindUtil util = new ASFindUtil(wConn);
					wStrText.append(ReportOperation.format(util.getWareHouseName(stock[i].getAreaNo())) + tb);
					//#CM43415
					// Consignor Code
					wStrText.append(ReportOperation.format(stock[i].getConsignorCode()) + tb);
					//#CM43416
					// Consignor Name
					wStrText.append(ReportOperation.format(consignorName) + tb);
					//#CM43417
					// Location No.
					wStrText.append(ReportOperation.format(
						DisplayText.formatDispLocation(stock[i].getLocationNo())) + tb);
					//#CM43418
					// Item Code
					wStrText.append(ReportOperation.format(stock[i].getItemCode()) + tb);
					//#CM43419
					// Item Name
					wStrText.append(ReportOperation.format(stock[i].getItemName1()) + tb);
					//#CM43420
					// Qty per case
					wStrText.append(enteringQty + tb);
					//#CM43421
					// Qty per bundle
					wStrText.append(stock[i].getBundleEnteringQty() + tb);		
					//#CM43422
					// Stock case qty
					wStrText.append(DisplayUtil.getCaseQty(stock[i].getStockQty(), enteringQty, casepieceFlag) + tb);
					//#CM43423
					// Stock piece qty
					wStrText.append(DisplayUtil.getPieceQty(stock[i].getStockQty(), enteringQty, casepieceFlag) + tb);
					//#CM43424
					// Possible drawing case qty
					wStrText.append(DisplayUtil.getCaseQty(allocateqty, enteringQty, casepieceFlag) + tb);
					//#CM43425
					// Possible drawing piece qty
					wStrText.append(DisplayUtil.getPieceQty(allocateqty, enteringQty, casepieceFlag) + tb);					
					//#CM43426
					// Flag
					wStrText.append(DisplayUtil.getPieceCaseValue(casepieceFlag) + tb);
					//#CM43427
					// Case ITF
					wStrText.append(stock[i].getItf() + tb);
					//#CM43428
					// Bundle ITF
					wStrText.append(stock[i].getBundleItf() + tb);
					//#CM43429
					// Storage date
					wStrText.append(DateOperator.changeDate(stock[i].getInstockDate()) + tb);
					//#CM43430
					// Storage time
					wStrText.append(DateOperator.changeDateTime(stock[i].getInstockDate()).substring(11) + tb);
					//#CM43431
					// Expiry date
					wStrText.append(ReportOperation.format(stock[i].getUseByDate()));

					//#CM43432
					// Output data to the file.
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
				}
			}

			//#CM43433
			// Close the stream.
			wPWriter.close();

			//#CM43434
			// UCXSingle is executed. (print execution)
			if (!executeUCX(JOBID_ASRSLOCATIONWORKINGINQUIRY))
			{
				//#CM43435
				// It failed in the print. Refer to the log.
				return false;
			}

			//#CM43436
			// Move the data file to the backup folder when succeeding in the print.
			ReportOperation.createBackupFile(wFileName);

			//#CM43437
			// 6001010=The print ended normally.
			setMessage("6001010");
		}
		catch (ReadWriteException e)
		{
			//#CM43438
			// 6007034 = It failed in the print. Refer to the log.
			setMessage("6007034");

			return false;
		}
		finally
		{
			try
			{
				reportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM43439
				// The data base error occurred. Refer to the log.
				setMessage("6007002");
				return false;
			}
		}

		return true;
	}

	//#CM43440
	// Package methods -----------------------------------------------

	//#CM43441
	// Protected methods ---------------------------------------------

	//#CM43442
	// Private methods -----------------------------------------------
	//#CM43443
	/**
	 * The method for the set of the search condition in the stock retrieval key. <BR>
	 * 
	 * @param searchKey Retrieval key to <CODE>StockSearchKey</CODE> inventory information
	 * @return Retrieval key to inventory information
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private StockSearchKey setStockSearchKey(StockSearchKey serachKey) throws ReadWriteException
	{
		//#CM43444
		// Warehouse(area No.)
		if(!StringUtil.isBlank(wAreaNo))
		{
			serachKey.setAreaNo(wAreaNo);
		}
		//#CM43445
		// Consignor Code
		if(!StringUtil.isBlank(wConsignorCode))
		{
			serachKey.setConsignorCode(wConsignorCode);
		}
		//#CM43446
		// Beginning shelf
		if(!StringUtil.isBlank(wFromLocationNo))
		{
			serachKey.setLocationNo(wFromLocationNo, ">=");
		}
		//#CM43447
		// End shelf
		if(!StringUtil.isBlank(wToLocationNo))
		{
			serachKey.setLocationNo(wToLocationNo, "<=");
		}
		//#CM43448
		// Item Code
		if(!StringUtil.isBlank(wItemCode))
		{
			serachKey.setItemCode(wItemCode);
		}
		//#CM43449
		// Case/Piece flag
		if(!StringUtil.isBlank(wCasePieceFlag))
		{
			serachKey.setCasePieceFlag(wCasePieceFlag);
		}
		//#CM43450
		// Stock status(central stock)
		serachKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM43451
		// The quantity of stock must be one or more.
		serachKey.setStockQty(1, ">=");

		return serachKey;
	}
	
	//#CM43452
	/**
	 * Acquire latest Consignor Name. <BR>
	 * 
	 * @param searchKey Retrieval key to <CODE>StockSearchKey</CODE> inventory information
	 * @return Latest Consignor Name
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private String getConsignorName() throws ReadWriteException
	{
		//#CM43453
		// Consignor Name
		String consignorName = "";
		
		StockSearchKey nameSearchKey = new StockSearchKey();
		StockReportFinder nameFinder = new StockReportFinder(wConn);
		
		//#CM43454
		// Retrieval condition set
		nameSearchKey.KeyClear();
		nameSearchKey.setAreaNo(wAreaNo);
		nameSearchKey.setConsignorCode(wConsignorCode);
		nameSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		nameSearchKey.setStockQty(1, ">=");				
		nameSearchKey.setLastUpdateDateOrder(1, false);
		
		//#CM43455
		// Retrieval beginning
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
//#CM43456
//end of class
