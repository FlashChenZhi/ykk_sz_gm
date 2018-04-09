//#CM9281
//$Id: NoPlanRetrievalWriter.java,v 1.5 2006/12/13 09:02:17 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.report;

//#CM9282
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.HostSendReportFinder;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;

//#CM9283
/**
 * Designer : Muneendra <BR>
 * Maker : Muneendra <BR>
 * <BR>
 * <BR>
 * <CODE>NoPlanRetrievalWriter</CODE> class creates report Data for file exception picking List and execute print.<BR>
 * Search Stock Info based on the conditions set at <CODE>NoPlanRetrievalListSCH</CODE> class<BR>
 * Make the result as the report file for exception picking List<BR>
 * This class processes as follows.<BR>
 * <BR>
 * a ticket data file creation Process(<CODE>startPrint()</CODE>Method)<BR>
 *	<DIR>
 *	1.Search for Stock Info Count that meets the set condition via <CODE>NoPlanRetrievalListSCH</CODE>class.<BR>
 *	2.Create a report data file if result count is one or more. If it is 0, return false and close.<BR>
 *	3.Start the print process.<BR>
 *  4.Return true when print Process is normal<BR>
 *    Return false when error occurred during the print process.<BR>
 * <BR>
 * 	＜parameter＞*Mandatory Input<BR>
 * 		Consignor Code* <BR>
 * 		Consignor Name <BR>
 * 		Item Code* <BR>
 * 		Item Name <BR>
 * 		Case/Piece division* <BR>
 * 		Start Location<BR>
 * 		End Location<BR>
 * 		Case ITF <BR>
 * 		Bundle ITF <BR>
 * 		Customer Code  <BR>
 * 		Customer Name  <BR>
 * <BR>
 * 	＜Search condition＞<BR>
 * 		Consignor Code <BR>
 * 		Consignor Name <BR>
 * 		Work Date <BR>
 * 		Item Code <BR>
 * 		Item Name <BR>
 * 		Case/Piece division <BR>
 * 		Location№ <BR>
 * 		Packed Qty per Case <BR> 
 * 		Packed Qty per Bundle <BR>
 * 		Stock Case Qty <BR>
 * 		Stock Piece Qty <BR>
 * 		Case ITF <BR>
 * 		Bundle ITF <BR>
 * 		Expiry Date  <BR>
 * <BR>
 *	＜Print data＞<BR>
 * 		Consignor Code <BR>
 * 		Consignor Name <BR>
 * 		Item Code <BR>
 * 		Item Name <BR>
 * 		Division <BR>
 * 		Picking Location <BR>
 * 		Packed Qty per Case <BR>
 * 		Packed Qty per Piece <BR>
 * 		Allocatable Case Qty <BR>
 * 		Allocatable Piece Qty <BR>
 * 		Picking Case Qty <BR>
 * 		Picking Piece Qty <BR>
 * 		Expiry Date<BR>
 * 		Case ITF <BR>
 * 		Piece ITF <BR>
 * 	</DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/18</TD><TD>K.Matsuda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2006/12/13 09:02:17 $
 * @author $Author: suresh $
 */
public class NoPlanRetrievalWriter extends CSVWriter
{
	//#CM9284
	// Class fields --------------------------------------------------

	//#CM9285
	// Class variables -----------------------------------------------
	//#CM9286
	/**
	 * Batch No
	 */
	private String batchNumber;

	//#CM9287
	// Class method --------------------------------------------------
	//#CM9288
	/**
	 * return the version of this class.
	 * 
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.5 $,$Date: 2006/12/13 09:02:17 $");
	}

	//#CM9289
	// Constructors --------------------------------------------------
	//#CM9290
	/**
	 * NoPlanRetrievalWriter Construct Object.
	 * 
	 * @param conn<CODE>Connection</CODE>
	 */
	public NoPlanRetrievalWriter(Connection conn)
	{
		super(conn);
	}

	//#CM9291
	// Public methods ------------------------------------------------
	//#CM9292
	/**
	 * Obtain Batch No.
	 * @return Batch No.
	 */
	public String getBatchNumber()
	{
		return batchNumber;
	}

	//#CM9293
	/**
	 * Set the Batch No.
	 * @param batchNumber Batch No.
	 */
	public void setBatchNumber(String batchNumber)
	{
		this.batchNumber = batchNumber;
	}

	//#CM9294
	/**
	 * Create the CSV file for exception picking List and execute print. <BR>
	 * Create a report data file if result count is one or more. If it is 0, return false and close. <BR>
	 * Start the print process. <BR>
	 * Return true when the print process completed normally. <BR>
	 * Return false when error occurred during the print process. <BR>
	 * 
	 * @return boolean if print was successfully executed.
	 */
	public boolean startPrint()
	{
		HostSendReportFinder reportFinder = new HostSendReportFinder(wConn);
		try
		{
			//#CM9295
			// Return error when no Batch No. exists.
			if(batchNumber==null)
			{
				wMessage = "6007034";
				return false;
			}
			//#CM9296
			// Generate Object
			HostSendSearchKey searchKey = new HostSendSearchKey();
			//#CM9297
			// Set Batch No. and Work Status for a search condition.
			searchKey.setBatchNo(batchNumber);
			searchKey.setJobType(HostSend.JOB_TYPE_EX_RETRIEVAL);
			
			//#CM9298
			// Set the data retrieval order.
			searchKey.setConsignorCodeCollect("");
			searchKey.setConsignorNameCollect("");
			searchKey.setWorkDateCollect("");
			searchKey.setItemCodeCollect("");
			searchKey.setItemName1Collect("");
			searchKey.setCasePieceFlagCollect("");
			searchKey.setLocationNoCollect("");
			searchKey.setEnteringQtyCollect("");
			searchKey.setBundleEnteringQtyCollect("");
			searchKey.setBundleItfCollect("");
			searchKey.setItfCollect("");
			searchKey.setUseByDateCollect("");
			searchKey.setResultQtyCollect("");
			searchKey.setCustomerCodeCollect("");
			searchKey.setCustomerName1Collect("");
			
			//#CM9299
			// Set the sort order.			
			searchKey.setItemCodeOrder(1, true);
			searchKey.setCasePieceFlagOrder(2, true);
			searchKey.setLocationNoOrder(3, true);

			//#CM9300
			// No target data
			if (reportFinder.search(searchKey) <= 0)
			{
				//#CM9301
				// Print data was not found.
				wMessage = "6003010";
				return false;
			}
			
			if(!createPrintWriter(FNAME_EX_PICKING))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_EX_PICKING);
			
			HostSend hostSend[] = null;
			//#CM9302
			// Create the content of data file per 100 data until search result is lost.
			while (reportFinder.isNext())
			{
				//#CM9303
				// Retrieve up to 100 data of the search result.
				hostSend = (HostSend[]) reportFinder.getEntities(100);
				for (int i = 0; i < hostSend.length; i++)
				{
					
					wStrText.append(re + "");
					//#CM9304
					// Consignor Code
					wStrText.append(ReportOperation.format(hostSend[i].getConsignorCode()) + tb);
					//#CM9305
					// Consignor Name
					wStrText.append(ReportOperation.format(hostSend[i].getConsignorName()) + tb);
					//#CM9306
					// Work Date
					wStrText.append(ReportOperation.format(hostSend[i].getWorkDate()) + tb);
					//#CM9307
					// Customer Code
					wStrText.append(ReportOperation.format(hostSend[i].getCustomerCode()) + tb);
					//#CM9308
					// Customer Name
					wStrText.append(ReportOperation.format(hostSend[i].getCustomerName1()) + tb);
					//#CM9309
					// Item Code
					wStrText.append(ReportOperation.format(hostSend[i].getItemCode()) + tb);
					//#CM9310
					// Item Name
					wStrText.append(ReportOperation.format(hostSend[i].getItemName1()) + tb);
					//#CM9311
					// Case/Piece division
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(hostSend[i].getCasePieceFlag())) + tb);
					//#CM9312
					// Location№
					wStrText.append(ReportOperation.format(hostSend[i].getLocationNo()) + tb);
					//#CM9313
					// Packed Qty per Case
					wStrText.append(ReportOperation.format(Integer.toString(hostSend[i].getEnteringQty()))+ tb);
					//#CM9314
					// Packed Qty per Bundle
					wStrText.append(ReportOperation.format(Integer.toString(hostSend[i].getBundleEnteringQty()))+ tb);

					//#CM9315
					// The same qty as the Result Qty only when Case/Piece division is Piece
					//#CM9316
					// Except it, make the Stock Case Qty as Result Qty/Received Qty and  make  the Stock Piece Qty as remainder of the Result Qty/Received Qty (residue calculation).

					
					//#CM9317
					// Result Case Qty
					wStrText.append(DisplayUtil.getCaseQty(hostSend[i].getResultQty(), hostSend[i].getEnteringQty(), hostSend[i].getCasePieceFlag()) + tb);
					//#CM9318
					// Result Piece Qty
					wStrText.append(DisplayUtil.getPieceQty(hostSend[i].getResultQty(), hostSend[i].getEnteringQty(), hostSend[i].getCasePieceFlag()) + tb);
					
					//#CM9319
					// Case ITF
					wStrText.append(ReportOperation.format(hostSend[i].getItf()) + tb);
					//#CM9320
					// Bundle ITF
					wStrText.append(ReportOperation.format(hostSend[i].getBundleItf()) + tb);
					//#CM9321
					// Expiry Date
					wStrText.append(ReportOperation.format(hostSend[i].getUseByDate()) + tb);
					//#CM9322
					// writing
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
				}
			}
			//#CM9323
			// Close Stream
			wPWriter.close();
			
			//#CM9324
			// Execute UCXSingle
			if (!executeUCX(JOBID_EX_PICKING))
			{
				//#CM9325
				//Printing failed after setup. Please refer to log. 
				return false;
			}
			
			//#CM9326
			// Execute relocation data file to backup holder.
			ReportOperation.createBackupFile(wFileName);
		}
		catch (ReadWriteException e)
		{
			//#CM9327
			// Printing failed after setup. Please refer to log.
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM9328
				// Execute close Process of opened database cursor
				reportFinder.close();
			}
			catch (ReadWriteException e)
			{

				//#CM9329
				// Database error occurred.Please refer to log.
				setMessage("6007002");
				return false;			
			}
		}
		return true;
	}

	
	//#CM9330
	// Package methods -----------------------------------------------

	//#CM9331
	// Protected methods ---------------------------------------------

	//#CM9332
	// Private methods -----------------------------------------------

}
