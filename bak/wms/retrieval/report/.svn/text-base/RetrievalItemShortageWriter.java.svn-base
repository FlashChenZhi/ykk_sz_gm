// $Id: RetrievalItemShortageWriter.java,v 1.6 2007/02/07 04:19:35 suresh Exp $
package jp.co.daifuku.wms.retrieval.report;

//#CM591499
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoSearchKey;
import jp.co.daifuku.wms.base.entity.ShortageInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM591500
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * Allow this class to generate a data file for Shortage list View (for Item report) and invoke the Print class.<BR>
 * Search for Shortage Info using the contents set in the schedule class and generate a data file for report.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * Execute the process for generating a data file for report. (<CODE>startPrint()</CODE>method)<BR>
 * <DIR>
 *   Search for the Shortage Work status.<BR>
 *   Disable to generate data file for report if no corresponding data exists.<BR>
 *   Generate a data file for report if corresponding data exists, and invoke the class for executing printing.<BR>
 * <BR>
 *   <Search conditions>Mandatory condition*
 *   <DIR>
 *     Batch No..*<BR>
 *     Case/Piece division*<BR>
 *   </DIR>
 *   <BR>
 *   Execute the processes in the order as below.<BR>
 *   <DIR>
 *     1.Set the search conditions and execute the search process.<BR>
 *     2.Disable to print if the search result count is less than 1 (one).<BR>
 *     3.Obtain every 100 search result.<BR>
 *     4.Execute printing..<BR>
 *     5.If succeeded in printing, move the data file to the backup folder.<BR>
 *     6.Return the result whether the printing succeeded or not.<BR>
 *   </DIR>
 * </DIR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/30</TD><TD>K.Toda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.6 $, $Date: 2007/02/07 04:19:35 $
 * @author  $Author: suresh $
 */
public class RetrievalItemShortageWriter extends CSVWriter
{
	//#CM591501
	// Class fields --------------------------------------------------

	//#CM591502
	// Class variables -----------------------------------------------
	//#CM591503
	/**
	 * Batch No.
	 */
	private String wBatchNo;

	//#CM591504
	/**
	 * Case/Piece division
	 */
	private String wCasePiece;

	//#CM591505
	// Class method --------------------------------------------------
	//#CM591506
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.6 $,$Date: 2007/02/07 04:19:35 $");
	}

	//#CM591507
	// Constructors --------------------------------------------------
	//#CM591508
	/**
	 * Construct the RetrievalItemShortageWriter object.<BR>
	 * Set the connection and the locale.<BR>
	 * @param conn <CODE>Connection</CODE> Database connection object
	 */
	public RetrievalItemShortageWriter(Connection conn)
	{
		super(conn);
	}

	//#CM591509
	// Public methods ------------------------------------------------
	//#CM591510
	/**
	 * Set a value to be searched in the Batch No.
	 * @param pBatchNo Batch No. to be set
	 */
	public void setBatchNo(String pBatchNo)
	{
		wBatchNo = pBatchNo ;
	}

	//#CM591511
	/**
	 * Obtain the Batch No.
	 * @return Batch No.
	 */
	public String getBatchNo()
	{
		return wBatchNo;
	}

	//#CM591512
	/**
	 * Set a value to be searched in the Case/Piece division.
	 * @param pBatchNo Case/Piece division to be set
	 */
	public void setCasePiece(String pCasePiece)
	{
		wCasePiece = pCasePiece ;
	}

	//#CM591513
	/**
	 * Case Obtain the Piece division.
	 * @return Case/Piece division
	 */
	public String getCasePiece()
	{
		return wCasePiece;
	}
	
	//#CM591514
	/**
	 * Generate a Shortage list (for Item) CSV file and invoke the class for printing.<BR>
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
			//#CM591515
			// Execute searching.
			ShortageInfoSearchKey shortageKey = new ShortageInfoSearchKey();

			//#CM591516
			// Set the search conditions.
			setShortageInfoSearchKey(shortageKey);

			//#CM591517
			// If no data exists, disable to execute print process.
			if (shortageFinder.search(shortageKey) <= 0)
			{
				//#CM591518
				// 6003010=No print data found.
				wMessage = "6003010";
				return false;
			}

			//#CM591519
			// Generate a file for output.
			if (!createPrintWriter(FNAME_SHORTAGE_ITEM))
			{
				return false;
			}

			//#CM591520
			// Generate contents of data files until there is no longer the search result.
			ShortageInfo[] shortageInfo = null;
			while (shortageFinder.isNext())
			{
				//#CM591521
				// Output every 100 search results.
				shortageInfo = (ShortageInfo[]) shortageFinder.getEntities(100);

				for (int i = 0; i < shortageInfo.length; i++)
				{
					wStrText.append(re + "");

                    //#CM591522
                    // Case/Piece division (input via screen)
					if (wCasePiece.equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
					{
						wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_CASE)) + tb);
					}
					else if (wCasePiece.equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
					{
						wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_PIECE)) + tb);
					}
					else if (wCasePiece.equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
					{
						wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_NOTHING)) + tb);
					}
					else
					{
						//#CM591523
						// RDB-W0007=All
						wStrText.append(ReportOperation.format(DisplayText.getText("RDB-W0007")) + tb);
					}
                    
					//#CM591524
					// Consignor Code
					wStrText.append(ReportOperation.format(shortageInfo[i].getConsignorCode()) + tb);
					//#CM591525
					// Consignor Name
					wStrText.append(ReportOperation.format(shortageInfo[i].getConsignorName()) + tb);
					//#CM591526
					// Planned Picking Date
					wStrText.append(ReportOperation.format(shortageInfo[i].getPlanDate()) + tb);
					//#CM591527
					// Item Code
					wStrText.append(ReportOperation.format(shortageInfo[i].getItemCode()) + tb);
					//#CM591528
					// Item Name
					wStrText.append(ReportOperation.format(shortageInfo[i].getItemName1()) + tb);
					//#CM591529
					// Case/Piece division	
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(shortageInfo[i].getCasePieceFlag())) + tb);
					//#CM591530
					// Planned total qty
					wStrText.append(shortageInfo[i].getPlanQty() + tb);
					//#CM591531
					// Packed Qty per Case
					wStrText.append(shortageInfo[i].getEnteringQty() + tb);
					//#CM591532
					// Packed qty per bundle
					wStrText.append(shortageInfo[i].getBundleEnteringQty() + tb);
					//#CM591533
					// Planned Case QTY
					wStrText.append(DisplayUtil.getCaseQty(
							shortageInfo[i].getPlanQty(),
							shortageInfo[i].getEnteringQty(),
							shortageInfo[i].getCasePieceFlag())
						+ tb);
					//#CM591534
					// Planned Piece QTY
					wStrText.append(DisplayUtil.getPieceQty(
							shortageInfo[i].getPlanQty(),
							shortageInfo[i].getEnteringQty(),
							shortageInfo[i].getCasePieceFlag())
						+ tb);
					//#CM591535
					// Shortage Qty
					wStrText.append(shortageInfo[i].getShortageCnt() + tb);
					//#CM591536
					// Picking Location
					if (shortageInfo[i].getCasePieceFlag().equals(SystemDefine.CASEPIECE_FLAG_CASE))
					{
						//#CM591537
						// Case Picking Location
						wStrText.append(ReportOperation.format(shortageInfo[i].getCaseLocation()) + tb);
					}
					else
					{
						//#CM591538
						// Piece Picking Location
						wStrText.append(ReportOperation.format(shortageInfo[i].getPieceLocation()) + tb);
					}

					//#CM591539
					// Output the data to the file.
					wPWriter.print(wStrText);
					wStrText.setLength(0);
				}
			}

			//#CM591540
			// Close the stream.
			wPWriter.close();

			//#CM591541
			// Execute UCXSingle (Execute printing).
			if (!executeUCX(JOBID_SHORTAGE_ITEM))
			{
				//#CM591542
				// Failed to print. See log.
				return false;
			}

			//#CM591543
			// If succeeded in printing, move the data file to the backup folder.
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException re)
		{
			//#CM591544
			// 6007034=Printing failed. See log.
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM591545
				// Execute processes for closing the cursor for the opened database.
				shortageFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM591546
				// Database error occurred. See log.
				setMessage("6007002");
				return false;
			}
		}

		return true;
	}

	//#CM591547
	// Package methods -----------------------------------------------

	//#CM591548
	// Protected methods ---------------------------------------------

	//#CM591549
	// Private methods -----------------------------------------------
	//#CM591550
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
		//#CM591551
		// Set the search conditions.
		//#CM591552
		// Batch No.
		searchKey.setBatchNo(wBatchNo);
		//#CM591553
		// Case/Piece division
		if (wCasePiece.equals(SystemDefine.CASEPIECE_FLAG_CASE))
		{
			searchKey.setCasePieceFlag(wCasePiece);
		}
		else if (wCasePiece.equals(SystemDefine.CASEPIECE_FLAG_PIECE))
		{
			searchKey.setCasePieceFlag(wCasePiece);
		}
		else if (wCasePiece.equals(SystemDefine.CASEPIECE_FLAG_NOTHING))
		{
			searchKey.setCasePieceFlag(wCasePiece);
		}
		else
		{
			//#CM591554
			// Disable to set.
		}
		
		//#CM591555
		// COLLECT conditions
		searchKey.setConsignorCodeCollect("");
		searchKey.setConsignorNameCollect("");
		searchKey.setPlanDateCollect("");
		searchKey.setItemCodeCollect("");
		searchKey.setItemName1Collect("");
		searchKey.setCasePieceFlagCollect("");
		searchKey.setEnteringQtyCollect("");
		searchKey.setBundleEnteringQtyCollect("");
		searchKey.setPlanQtyCollect("");
		searchKey.setShortageCntCollect("");
		searchKey.setCaseLocationCollect("");
		searchKey.setPieceLocationCollect("");

		//#CM591556
		// Set the search order.
		searchKey.setConsignorCodeOrder(1, true);
		searchKey.setPlanDateOrder(2, true);
		searchKey.setItemCodeOrder(3, true);

	}
}
//#CM591557
//end of class
