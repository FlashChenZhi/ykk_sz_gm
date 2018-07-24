// $Id: RetrievalOrderShortageWriter.java,v 1.6 2007/02/07 04:19:37 suresh Exp $
package jp.co.daifuku.wms.retrieval.report;

//#CM591826
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

//#CM591827
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * Allow this class to generate a data file for Shortage list View (for Order report) and invoke the Print class.<BR>
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
 *     Case/Piece division<BR>
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
 * @version $Revision: 1.6 $, $Date: 2007/02/07 04:19:37 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderShortageWriter extends CSVWriter
{
	//#CM591828
	// Class fields --------------------------------------------------

	//#CM591829
	// Class variables -----------------------------------------------
	//#CM591830
	/**
	 * Batch No.
	 */
	private String wBatchNo;

	//#CM591831
	/**
	 * Case/Piece division
	 */
	private String wCasePiece;

	//#CM591832
	// Class method --------------------------------------------------
	//#CM591833
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.6 $,$Date: 2007/02/07 04:19:37 $");
	}

	//#CM591834
	// Constructors --------------------------------------------------
	//#CM591835
	/**
	 * Construct the RetrievalOrderShortageWriter object.<BR>
	 * Set the connection and the locale.<BR>
	 * @param conn <CODE>Connection</CODE> Database connection object
	 */
	public RetrievalOrderShortageWriter(Connection conn)
	{
		super(conn);
	}

	//#CM591836
	// Public methods ------------------------------------------------
	//#CM591837
	/**
	 * Set a value to be searched in the Batch No.
	 * @param pBatchNo Batch No. to be set
	 */
	public void setBatchNo(String pBatchNo)
	{
		wBatchNo = pBatchNo ;
	}

	//#CM591838
	/**
	 * Obtain the Batch No.
	 * @return Batch No.
	 */
	public String getBatchNo()
	{
		return wBatchNo;
	}

	//#CM591839
	/**
	 * Set a value to be searched in the Case/Piece division.
	 * @param pBatchNo Case/Piece division to be set
	 */
	public void setCasePiece(String pCasePiece)
	{
		wCasePiece = pCasePiece ;
	}

	//#CM591840
	/**
	 * Case Obtain the Piece division.
	 * @return Case/Piece division
	 */
	public String getCasePiece()
	{
		return wCasePiece;
	}
	
	//#CM591841
	/**
	 * Generate a Shortage list (for Order) CSV file and invoke the class for printing.<BR>
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
			//#CM591842
			// Execute searching.
			ShortageInfoSearchKey shortageKey = new ShortageInfoSearchKey();

			//#CM591843
			// Set the search conditions.
			setShortageInfoSearchKey(shortageKey);

			//#CM591844
			// If no data exists, disable to execute print process.
			if (shortageFinder.search(shortageKey) <= 0)
			{
				//#CM591845
				// 6003010=No print data found.
				wMessage = "6003010";
				return false;
			}

			//#CM591846
			// Generate a file for output.
			if (!createPrintWriter(FNAME_SHORTAGE_ORDER))
			{
				return false;
			}

			//#CM591847
			// Generate contents of data files until there is no longer the search result.
			ShortageInfo[] shortageInfo = null;
			while (shortageFinder.isNext())
			{
				//#CM591848
				// Output every 100 search results. 
				shortageInfo = (ShortageInfo[]) shortageFinder.getEntities(100);

				for (int i = 0; i < shortageInfo.length; i++)
				{
					wStrText.append(re + "");

                    //#CM591849
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
						//#CM591850
						// RDB-W0007=All
						wStrText.append(ReportOperation.format(DisplayText.getText("RDB-W0007")) + tb);
					}
                    
					//#CM591851
					// Consignor Code
					wStrText.append(ReportOperation.format(shortageInfo[i].getConsignorCode()) + tb);
					//#CM591852
					// Consignor Name
					wStrText.append(ReportOperation.format(shortageInfo[i].getConsignorName()) + tb);
					//#CM591853
					// Planned Picking Date
					wStrText.append(ReportOperation.format(shortageInfo[i].getPlanDate()) + tb);
					//#CM591854
					// Order No.
					if (shortageInfo[i].getCasePieceFlag().equals(SystemDefine.CASEPIECE_FLAG_CASE))
					{
						//#CM591855
						// Case Order No.
						wStrText.append(ReportOperation.format(shortageInfo[i].getCaseOrderNo()) + tb);						
					}
					else
					{
						//#CM591856
						// Piece Order No.
						wStrText.append(ReportOperation.format(shortageInfo[i].getPieceOrderNo()) + tb);						
					}
					//#CM591857
					// Customer Code
					wStrText.append(ReportOperation.format(shortageInfo[i].getCustomerCode()) + tb);
					//#CM591858
					// Customer Name
					wStrText.append(ReportOperation.format(shortageInfo[i].getCustomerName1()) + tb);					
					//#CM591859
					// Item Code
					wStrText.append(ReportOperation.format(shortageInfo[i].getItemCode()) + tb);
					//#CM591860
					// Item Name
					wStrText.append(ReportOperation.format(shortageInfo[i].getItemName1()) + tb);
					//#CM591861
					// Case/Piece division
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(shortageInfo[i].getCasePieceFlag())) + tb);
					//#CM591862
					// Planned total qty
					wStrText.append(shortageInfo[i].getPlanQty() + tb);
					//#CM591863
					// Shortage Qty
					wStrText.append(shortageInfo[i].getShortageCnt() + tb);
					//#CM591864
					// Picking Location
					if (shortageInfo[i].getCasePieceFlag().equals(SystemDefine.CASEPIECE_FLAG_CASE))
					{
						//#CM591865
						// Case Picking Location
						wStrText.append(ReportOperation.format(shortageInfo[i].getCaseLocation()) + tb);
					}
					else
					{
						//#CM591866
						// Piece Picking Location
						wStrText.append(ReportOperation.format(shortageInfo[i].getPieceLocation()) + tb);
					}

					//#CM591867
					// Output the data to the file.
					wPWriter.print(wStrText);
					wStrText.setLength(0);
				}
			}

			//#CM591868
			// Close the stream.
			wPWriter.close();

			//#CM591869
			// Execute UCXSingle (Execute printing).
			if (!executeUCX(JOBID_SHORTAGE_ORDER))
			{
				//#CM591870
				// Failed to print. See log.
				return false;
			}

			//#CM591871
			// If succeeded in printing, move the data file to the backup folder.
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException re)
		{
			//#CM591872
			// 6007034=Printing failed. See log.
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM591873
				// Execute processes for closing the cursor for the opened database.
				shortageFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM591874
				// Database error occurred. See log.
				setMessage("6007002");
				return false;
			}
		}

		return true;
	}

	//#CM591875
	// Package methods -----------------------------------------------

	//#CM591876
	// Protected methods ---------------------------------------------

	//#CM591877
	// Private methods -----------------------------------------------
	//#CM591878
	/**
	 * Allow this method to set the search conditions for the Shortage info search key.<BR>
	 * 
	 * @param searchKey ShortageInfoSearchKey Shortage info search key
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private void setShortageInfoSearchKey(ShortageInfoSearchKey searchKey)
		throws ReadWriteException
	{
		//#CM591879
		// Set the search conditions.
		//#CM591880
		// Batch No.
		searchKey.setBatchNo(wBatchNo);
		//#CM591881
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
			//#CM591882
			// No setting
		}
		
		//#CM591883
		// COLLECT conditions
		searchKey.setConsignorCodeCollect("");
		searchKey.setConsignorNameCollect("");
		searchKey.setPlanDateCollect("");
		searchKey.setCaseOrderNoCollect("");
		searchKey.setPieceOrderNoCollect("");
		searchKey.setCustomerCodeCollect("");
		searchKey.setCustomerName1Collect("");
		searchKey.setItemCodeCollect("");
		searchKey.setItemName1Collect("");
		searchKey.setCasePieceFlagCollect("");
		searchKey.setPlanQtyCollect("");
		searchKey.setShortageCntCollect("");
		searchKey.setCaseLocationCollect("");
		searchKey.setPieceLocationCollect("");

		//#CM591884
		// Set the search order.
		searchKey.setConsignorCodeOrder(1, true);
		searchKey.setPlanDateOrder(2, true);
		searchKey.setCaseOrderNoOrder(3, true);
		searchKey.setPieceOrderNoOrder(4, true);
		searchKey.setCustomerCodeOrder(5, true);
		searchKey.setItemCodeOrder(6, true);

	}
}
//#CM591885
//end of class
