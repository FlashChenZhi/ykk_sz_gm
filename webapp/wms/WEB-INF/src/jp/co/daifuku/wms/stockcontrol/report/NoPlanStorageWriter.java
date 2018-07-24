//#CM9333
//$Id: NoPlanStorageWriter.java,v 1.5 2006/12/13 09:02:17 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.report;

//#CM9334
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

//#CM9335
/**
 * Designer : Muneendra <BR>
 * Maker : Muneendra <BR>
 * <BR>
 * <CODE>NoPlanStorageWriter</CODE> class creates a report data file for Exceptional Storage List and prints it.<BR>
 * Search the database for Stock Info that meets the search condition set in <CODE>NoPlanStorageListSCH</CODE> class, and<BR>
 * Creates a ticket data file for Exceptional Storage list from the search result.<BR>
 * This class processes as follows.<BR>
 * <BR>
 * a ticket data file creation Process(<CODE>startPrint()</CODE>Method)<BR>
 *	<DIR>
 *	1.Search for Stock Info Count that meets the set condition via <CODE>NoPlanStorageListSCH</CODE>class.<BR>
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
 * 		Storage Location*<BR>
 * 		Packed Qty per Case <BR> 
 * 		Packed Qty per Bundle <BR>
 * 		Storage Case Qty <BR>
 * 		Storage Piece Qty <BR>
 * 		Case ITF <BR>
 * 		Bundle ITF <BR>
 * 		Expiry Date  <BR>
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
 * 		Storage Location <BR>
 * 		Packed Qty per Case <BR>
 * 		Packed Qty per Piece <BR>
 * 		Storage Case Qty <BR>
 * 		Storage Piece Qty <BR>
 * 		Case ITF <BR>
 * 		Piece ITF <BR>
 * 		Expiry Date<BR>
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
public class NoPlanStorageWriter extends CSVWriter
{
	//#CM9336
	// Class fields --------------------------------------------------

	//#CM9337
	// Class variables -----------------------------------------------
	//#CM9338
	/**
	 * Batch No
	 */
	private String batchNumber;

	//#CM9339
	// Class method --------------------------------------------------
	//#CM9340
	/**
	 * return the version of this class.
	 * 
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.5 $,$Date: 2006/12/13 09:02:17 $");
	}

	//#CM9341
	// Constructors --------------------------------------------------
	//#CM9342
	/**
	 * NoPlanStorageWriter Construct Object.
	 * 
	 * @param conn<CODE>Connection</CODE>
	 */
	public NoPlanStorageWriter(Connection conn)
	{
		super(conn);
	}

	//#CM9343
	// Public methods ------------------------------------------------
	//#CM9344
	/**
	 * Obtain the Batch No.
	 * @return Batch No.
	 */
	public String getBatchNumber()
	{
		return batchNumber;
	}

	//#CM9345
	/**
	 * Set the Batch No.
	 * @param batchNumber Batch No.
	 */
	public void setBatchNumber(String batchNumber)
	{
		this.batchNumber = batchNumber;
	}

	//#CM9346
	/**
	 * Create a CSV file for Exceptional Storage List and start printing. <BR>
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
					
			//#CM9347
			// Return error when no Batch No. exists.
			if(batchNumber==null)
			{
				wMessage = "6007034";
				return false;
				
			}
			//#CM9348
			// Generate Object
			HostSendSearchKey searchKey = new HostSendSearchKey();
			//#CM9349
			// Set Batch No. and Work Status for a search condition.
			searchKey.setBatchNo(batchNumber);
			searchKey.setJobType(HostSend.JOB_TYPE_EX_STORAGE);
			//#CM9350
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
			//#CM9351
			// Set the sort order.
			searchKey.setConsignorCodeOrder(1, true);
			searchKey.setItemCodeOrder(2, true);
			searchKey.setCasePieceFlagOrder(3, true);
			searchKey.setLocationNoOrder(4, true);

			//#CM9352
			// No target data
			if (reportFinder.search(searchKey) <= 0)
			{
				//#CM9353
				// Print data was not found.
				wMessage = "6003010";
				return false;
			}

			if(!createPrintWriter(FNAME_EX_STRAGE))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_EX_STRAGE);
            
			HostSend hostSend[] = null;

			while (reportFinder.isNext())
			{
				//#CM9354
				// Retrieve up to 100 data of the search result.
				hostSend = (HostSend[]) reportFinder.getEntities(100);
				for (int i = 0; i < hostSend.length; i++)
				{
					//#CM9355
					// Print the data on a different page by Conseignor Code.
					wStrText.append(re + "");
					//#CM9356
					// Consignor Code
					wStrText.append(ReportOperation.format(hostSend[i].getConsignorCode()) + tb);
					//#CM9357
					// Consignor Name
					wStrText.append(ReportOperation.format(hostSend[i].getConsignorName()) + tb);
					//#CM9358
					// Work Date
					wStrText.append(ReportOperation.format(hostSend[i].getWorkDate()) + tb);
					//#CM9359
					// Item Code
					wStrText.append(ReportOperation.format(hostSend[i].getItemCode()) + tb);
					//#CM9360
					// Item Name
					wStrText.append(ReportOperation.format(hostSend[i].getItemName1()) + tb);
					//#CM9361
					// Case/Piece division
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(hostSend[i].getCasePieceFlag())) + tb);
					//#CM9362
					// Location№
					wStrText.append(ReportOperation.format(hostSend[i].getLocationNo()) + tb);
					//#CM9363
					// Packed Qty per Case
					wStrText.append(ReportOperation.format(Integer.toString(hostSend[i].getEnteringQty()))+ tb);
					//#CM9364
					// Packed Qty per Bundle
					wStrText.append(ReportOperation.format(Integer.toString(hostSend[i].getBundleEnteringQty())) + tb);
					
					//#CM9365
					// The same qty as the Result Qty only when Case/Piece division is Piece
					//#CM9366
					// In other cases, Stock Case Qty comes to Result Qty/Received Qty and Stock Piece Qty comes to a remainder of Result Qty/Received Qty (Residue calculation).
					
					//#CM9367
					// Result Case Qty
					wStrText.append(DisplayUtil.getCaseQty(hostSend[i].getResultQty(), hostSend[i].getEnteringQty(), hostSend[i].getCasePieceFlag()) + tb);
					//#CM9368
					// Result Piece Qty
					wStrText.append(DisplayUtil.getPieceQty(hostSend[i].getResultQty(), hostSend[i].getEnteringQty(), hostSend[i].getCasePieceFlag()) + tb);
					
					//#CM9369
					// Case ITF
					wStrText.append(ReportOperation.format(hostSend[i].getItf()) + tb);
					//#CM9370
					// Bundle ITF
					wStrText.append(ReportOperation.format(hostSend[i].getBundleItf()) + tb);
					//#CM9371
					// Expiry Date
					wStrText.append(ReportOperation.format(hostSend[i].getUseByDate()) + tb);
					//#CM9372
					// writing
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
				}
			}
			//#CM9373
			// Close Stream
			wPWriter.close();
			 //#CM9374
			 // Execute UCXSingle			
			 if (!executeUCX(JOBID_EX_STRAGE)) {
			     //#CM9375
			     // Printing failed after setup. Please refer to log. 
			 	return false;
			 }
			ReportOperation.createBackupFile(wFileName);
		}
		catch (ReadWriteException e)
		{
			//#CM9376
			// Printing failed after setup. Please refer to log.
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM9377
				// Execute close Process of opened database cursor.
				reportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM9378
				// Database error occurred.Please refer to log.
				setMessage("6007002");
				return false;			
			}
		}
		return true;
	}
	
	//#CM9379
	// Package methods -----------------------------------------------

	//#CM9380
	// Protected methods ---------------------------------------------

	//#CM9381
	// Private methods -----------------------------------------------

}
