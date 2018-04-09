// $Id: RetrievalShortageInquiryWriter.java,v 1.5 2006/12/13 08:53:36 suresh Exp $

//#CM695324
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.report;
import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoSearchKey;
import jp.co.daifuku.wms.base.entity.ShortageInfo;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;

//#CM695325
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * Allow this method to generate a Ticket data file for shortage check list and invoke the class for executing printing.<BR>
 * Search for shortage info using the contents set in the schedule class and generate a ticket data file.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * Execute the process for generating a ticket data file.(<CODE>startPrint()</CODE>method)<BR>
 * <DIR>
 *   Search for the Shortage Work status.<BR>
 *   Disable to generate ticket data file if no corresponding data exists.<BR>
 *   Generate a ticket data file if corresponding data exists, and invoke the class for executing printing.<BR>
 * <BR>
 *   <Search Conditions> Mandatory conditions*
 *   <DIR>
 *     Added date*<BR>
 *     Consignor Code<BR>
 *     Aggregation Conditions*<BR>
 *   </DIR>
 *   <BR>
 *   Execute the processes in the order as below.<BR>
 *   <DIR>
 *     1.Set the search conditions and execute the search process.<BR>
 *     <DIR>
 *     -View by Inventory: Aggregate the inventory data by item and search for them.<BR>
 *     </DIR>
 *     <DIR>
 *     -View by Plan: Search for the plans without aggregating them.<BR>
 *     </DIR>
 *     2.Disable to print if the search result count is less than 1 (one).<BR>
 *     3.Obtain every 100 search result.<BR>
 *     4.Execute printing.<BR>
 *     5.If succeeded in printing, move the data file to the backup folder.<BR>
 *     6.Return the result whether the printing succeeded or not.<BR>
 *   </DIR>
 * </DIR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/02/09</TD><TD>Y.Okamura</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2006/12/13 08:53:36 $
 * @author  $Author: suresh $
 */
public class RetrievalShortageInquiryWriter extends CSVWriter
{
	//#CM695326
	// Class fields --------------------------------------------------
	//#CM695327
	/**
	 * View by: Item
	 */
	public static final String DISP_TYPE_ITEM = "DISP_TYPE_ITEM";

	//#CM695328
	/**
	 * View by: Plan data
	 */
	public static final String DISP_TYPE_PLAN = "DISP_TYPE_PLAN";

	//#CM695329
	// Class variables -----------------------------------------------
	//#CM695330
	/**
	 * Added date
	 */
	private Date wRegistDate;

	//#CM695331
	/**
	 * Consignor Code
	 */
	private String wConsignorCode;

	//#CM695332
	/**
	 * Unit of View
	 */
	private String wDispType;

	//#CM695333
	// Class method --------------------------------------------------
	//#CM695334
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.5 $,$Date: 2006/12/13 08:53:36 $");
	}

	//#CM695335
	// Constructors --------------------------------------------------
	//#CM695336
	/**
	 * Construct a RetrievalShortageInquiryWriter object.<BR>
	 * Set the connection and the locale.<BR>
	 * @param conn <CODE>Connection</CODE> Database connection object
	 */
	public RetrievalShortageInquiryWriter(Connection conn)
	{
		super(conn);
	}

	//#CM695337
	// Public methods ------------------------------------------------
	//#CM695338
	/**
	 * Set the search value for Added Date.
	 * @param pRegistDate Added date to be set
	 */
	public void setRegistDate(Date pRegistDate)
	{
		wRegistDate = pRegistDate;
	}

	//#CM695339
	/**
	 * Obtain the added date.
	 * @return Added date
	 */
	public Date getRegistDate()
	{
		return wRegistDate;
	}

	//#CM695340
	/**
	 * Set the search value for the Consignor code.
	 * @param pConsignorcode Consignor code to be set
	 */
	public void setConsignorCode(String pConsignorcode)
	{
		wConsignorCode = pConsignorcode;
	}

	//#CM695341
	/**
	 * Obtain the Consignor code.
	 * @return Consignor Code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM695342
	/**
	 * Set the search value for Unit of View.
	 * @param pDispType Unit of View to be set
	 */
	public void setDispType(String pDispType)
	{
		wDispType = pDispType;
	}

	//#CM695343
	/**
	 * Obtain the unit of View.
	 * @return Unit of View
	 */
	public String getDispType()
	{
		return wDispType;
	}

	//#CM695344
	/**
	 * Obtain the count of the print data.<BR>
	 * Use this search result to determine whether to execute the process for printing.<BR>
	 * Use this method via the schedule class for processing the screen.<BR>
	 *
	 * @return Count of printed data.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int count() throws ReadWriteException
	{
		ShortageInfoHandler handler = new ShortageInfoHandler(wConn);
		ShortageInfoSearchKey searchKey = new ShortageInfoSearchKey();
		//#CM695345
		// Set the search conditions and obtain the count.
		setShortageInfoSearchKey(searchKey);
		return handler.count(searchKey);

	}

	//#CM695346
	/**
	 * Generate a CSV file for Shortage check list and invoke the class for executing printing.<BR>
	 * Search for shortage info using the search conditions input via screen.
	 * Disable to print if the search result count is less than 1 (one).<BR>
	 * Return true if succeeded in printing. Return false if failed.<BR>
	 *
	 * @return boolean Result whether the printing succeeded or not.
	 */
	public boolean startPrint()
	{

		ShortageInfoReportFinder shortageFinder = new ShortageInfoReportFinder(wConn);

		try
		{
			//#CM695347
			// Execute searching.
			ShortageInfoSearchKey shortageKey = new ShortageInfoSearchKey();

			//#CM695348
			// Set the search conditions.
			setShortageInfoSearchKey(shortageKey);

			//#CM695349
			// If no data exists, disable to execute print process.
			if (shortageFinder.search(shortageKey) <= 0)
			{
				//#CM695350
				// 6003010=No print data found.
				wMessage = "6003010";
				return false;
			}

			//#CM695351
			// Generate a file for output.
			if (!createPrintWriter(FNAME_SHORTAGEINFO))
			{
				return false;
			}

            //append header
            wStrText.append(HEADER_SHORTAGEINFO);

			//#CM695352
			// Generate contents of data files until there is no longer the search result.
			ShortageInfo[] shortageInfo = null;
			while (shortageFinder.isNext())
			{
				//#CM695353
				// Output every 100 search results.
				shortageInfo = (ShortageInfo[]) shortageFinder.getEntities(100);

				for (int i = 0; i < shortageInfo.length; i++)
				{
					wStrText.append(re + "");

					//#CM695354
					// Consignor Code
					wStrText.append(ReportOperation.format(shortageInfo[i].getConsignorCode()) + tb);
					//#CM695355
					// Consignor Name
					wStrText.append(ReportOperation.format(shortageInfo[i].getConsignorName()) + tb);
					//#CM695356
					// Planned Picking Date
					wStrText.append(ReportOperation.format(shortageInfo[i].getPlanDate()) + tb);
					//#CM695357
					// Item Code
					wStrText.append(ReportOperation.format(shortageInfo[i].getItemCode()) + tb);
					//#CM695358
					// Item Name
					wStrText.append(ReportOperation.format(shortageInfo[i].getItemName1()) + tb);
					//#CM695359
					// Packed Qty per Case
					wStrText.append(shortageInfo[i].getEnteringQty() + tb);
					//#CM695360
					// Packed qty per bundle
					wStrText.append(shortageInfo[i].getBundleEnteringQty() + tb);
					//#CM695361
					// Planned Case Qty
					wStrText.append(DisplayUtil.getCaseQty(
							shortageInfo[i].getPlanQty(),
							shortageInfo[i].getEnteringQty(),
							shortageInfo[i].getCasePieceFlag())
						+ tb);
					//#CM695362
					// Planned Piece Qty
					wStrText.append(DisplayUtil.getPieceQty(
							shortageInfo[i].getPlanQty(),
							shortageInfo[i].getEnteringQty(),
							shortageInfo[i].getCasePieceFlag())
						+ tb);
					//#CM695363
					// Allocatable Case Qty
					wStrText.append(DisplayUtil.getCaseQty(
							shortageInfo[i].getEnableQty(),
							shortageInfo[i].getEnteringQty(),
							shortageInfo[i].getCasePieceFlag())
						+ tb);
					//#CM695364
					// Allocatable Piece Qty
					wStrText.append(DisplayUtil.getPieceQty(
							shortageInfo[i].getEnableQty(),
							shortageInfo[i].getEnteringQty(),
							shortageInfo[i].getCasePieceFlag())
						+ tb);
					//#CM695365
					// Shortage Case Qty
					wStrText.append(DisplayUtil.getCaseQty(
							shortageInfo[i].getShortageCnt(),
							shortageInfo[i].getEnteringQty(),
							shortageInfo[i].getCasePieceFlag())
						+ tb);
					//#CM695366
					// Shortage Piece Qty
					wStrText.append(DisplayUtil.getPieceQty(
							shortageInfo[i].getShortageCnt(),
							shortageInfo[i].getEnteringQty(),
							shortageInfo[i].getCasePieceFlag())
						+ tb);
					//#CM695367
					// Case Picking Location
					wStrText.append(ReportOperation.format(shortageInfo[i].getCaseLocation()) + tb);
					//#CM695368
					// Piece Picking Location
					wStrText.append(ReportOperation.format(shortageInfo[i].getPieceLocation()) + tb);
					//#CM695369
					// Case Order No.
					wStrText.append(ReportOperation.format(shortageInfo[i].getCaseOrderNo()) + tb);
					//#CM695370
					// Piece Order No.
					wStrText.append(ReportOperation.format(shortageInfo[i].getPieceOrderNo()) + tb);
					//#CM695371
					// Customer
					wStrText.append(ReportOperation.format(shortageInfo[i].getCustomerName1()));

					//#CM695372
					// Output the data to the file.
					wPWriter.print(wStrText);
					wStrText.setLength(0);

				}
			}

			//#CM695373
			// Close the stream.
			wPWriter.close();

			//#CM695374
			// Execute the UCXSingle (Execute printing).
			if (!ReportOperation.executeUCX(JOBID_SHORTAGEINFO, wFileName))
			{
				//#CM695375
				// Failed to print. See log.
				setMessage("6007034");
				return false;
			}

			//#CM695376
			// If succeeded in printing, move the data file to the backup folder.
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException re)
		{
			//#CM695377
			// 6007034=Printing failed. See log.
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM695378
				// Execute processes for closing the cursor of the opened database.
				shortageFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM695379
				// Database error occurred. See log.
				setMessage("6007002");
				return false;
			}
		}

		return true;
	}

	//#CM695380
	// Package methods -----------------------------------------------

	//#CM695381
	// Protected methods ---------------------------------------------

	//#CM695382
	// Private methods -----------------------------------------------
	//#CM695383
	/**
	 * Allow this method to set the search conditions for the Shortage info search key.<BR>
	 *
	 * @param searchKey ShortageInfoSearchKey Shortage info search key
	 * @return Shortage info search key
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private void setShortageInfoSearchKey(ShortageInfoSearchKey searchKey)
		throws ReadWriteException
	{
		//#CM695384
		// Obtain the Batch No. from the added date/time and the consignor code.
		String batchNo = getBatchNo();

		//#CM695385
		// Set the search conditions.
		//#CM695386
		// Batch No.
		searchKey.setBatchNo(batchNo);

		//#CM695387
		// Consignor Code
		if (!StringUtil.isBlank(wConsignorCode))
		{
			searchKey.setConsignorCode(wConsignorCode);
		}

		//#CM695388
		// View by Item: Aggregate items and display them.
		if (wDispType.equals(DISP_TYPE_ITEM))
		{
			//#CM695389
			// Set the group order.
			searchKey.setConsignorCodeGroup(1);
			searchKey.setPlanDateGroup(2);
			searchKey.setItemCodeGroup(3);

			//#CM695390
			// COLLECT conditions
			searchKey.setConsignorCodeCollect("");
			searchKey.setConsignorNameCollect("MAX");
			searchKey.setPlanDateCollect("");
			searchKey.setItemCodeCollect("");
			searchKey.setItemName1Collect("MAX");
			searchKey.setEnteringQtyCollect("MAX");
			searchKey.setBundleEnteringQtyCollect("MAX");
			searchKey.setPlanQtyCollect("SUM");
			searchKey.setEnableQtyCollect("SUM");
			searchKey.setShortageCntCollect("SUM");

			//#CM695391
			// Set the search order.
			searchKey.setConsignorCodeOrder(1, true);
			searchKey.setPlanDateOrder(2, true);
			searchKey.setItemCodeOrder(3, true);

		}
		//#CM695392
		// If no aggregation:
		else
		{
			//#CM695393
			// Set the search order.
			searchKey.setConsignorCodeOrder(1, true);
			searchKey.setConsignorNameOrder(2, true);
			searchKey.setItemCodeOrder(3, true);
			searchKey.setItemName1Order(4, true);
			searchKey.setCaseLocationOrder(5, true);
			searchKey.setPieceLocationOrder(6, true);
		}

	}

	//#CM695394
	/**
	 * Allow this method to obtain the Batch No. from the added date/time and the consignor code.<BR>
	 * @return String Batch No.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private String getBatchNo() throws ReadWriteException
	{
		try
		{
			//#CM695395
			// Set the search conditions.
			ShortageInfoSearchKey shortageKey = new ShortageInfoSearchKey();

			//#CM695396
			// Added date
			if (!StringUtil.isBlank(wRegistDate))
			{
				shortageKey.setRegistDate(wRegistDate);
			}
			//#CM695397
			// Consignor Code
			if (!StringUtil.isBlank(wConsignorCode))
			{
				shortageKey.setConsignorCode(wConsignorCode);
			}

			//#CM695398
			// Group the data by Batch No.
			shortageKey.setBatchNoGroup(1);
			shortageKey.setBatchNoCollect();

			//#CM695399
			// Execute searching.
			ShortageInfoHandler shortageHandler = new ShortageInfoHandler(wConn);
			ShortageInfo result = (ShortageInfo)shortageHandler.findPrimary(shortageKey);

			//#CM695400
			// Obtain the Batch No. from the search result and return it.
			if (result != null)
			{
				return result.getBatchNo();
			}
			else
			{
				return "";
			}
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

}
//#CM695401
//end of class
