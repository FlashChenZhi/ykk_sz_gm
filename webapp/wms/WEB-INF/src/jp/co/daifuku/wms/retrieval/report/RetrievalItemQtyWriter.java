// $Id: RetrievalItemQtyWriter.java,v 1.6 2007/02/07 04:19:35 suresh Exp $
package jp.co.daifuku.wms.retrieval.report;

//#CM591416
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
import jp.co.daifuku.wms.base.dbhandler.ResultViewHandler;
import jp.co.daifuku.wms.base.dbhandler.ResultViewReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM591417
/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * 
 * Allow this class to generate a data file for Item Picking Result report and invoke the class for printing.<BR>
 * Search through the Work Status using the content set in the shchedule class, and generte a data file for the report.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * Execute the process for generating a data file for report. (<CODE>startPrint()</CODE>method)<BR>
 * <DIR>
 *   Search for the Work status.<BR>
 *   Disable to generate data file for report if no corresponding data exists.<BR>
 *   Generate a data file for report if corresponding data exists, and invoke the class for executing printing.<BR>
 *   <BR>
 *    [search conditions] *Mandatory<BR>
 *   <BR>
 *   Consignor Code*<BR>
 *   Start Picking Date<BR>
 *   End Picking Date<BR>
 *   Item Code<BR>
 *   Case/Piece division<BR>
 *   Division: Picking<BR>
 *   <BR>
 *    [Field items to be extracted]  <BR>
 * 	 <BR>
 *   Consignor Code<BR>
 *   Consignor Name<BR>
 *   Picking Date<BR>
 *   Planned Picking Date<BR>
 *   Item Code<BR>
 *   Item Name<BR>
 *   Case/Piece division name<BR>
 *   Packed Qty per Case<BR>
 *   Packed qty per bundle<BR>
 *   Planned Work Case Qty<BR>
 *   Planned Work Piece Qty<BR>
 *   Result Case QTY<BR>
 *   Result Piece QTY<BR>
 *   Shortage Case Qty<BR>
 *   Shortage Piece Qty<BR>
 *   Picking Location<BR>
 *   Expiry Date<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/19</TD><TD>Y.Kubo</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.6 $, $Date: 2007/02/07 04:19:35 $
 * @author  $Author: suresh $
 */
public class RetrievalItemQtyWriter extends CSVWriter
{
	//#CM591418
	// Class fields --------------------------------------------------

	//#CM591419
	// Class variables -----------------------------------------------
	//#CM591420
	/**
	 * Consignor Code
	 */
	private String wConsignorCode;

	//#CM591421
	/**
	 * Start Picking Date
	 */
	private String wFromWorkDate;

	//#CM591422
	/**
	 * End Picking Date
	 */
	private String wToWorkDate;

	//#CM591423
	/**
	 * Item Code
	 */
	private String wItemCode;

	//#CM591424
	/**
	 * Case/Piece division
	 */
	private String wCasePieceFlag;

	//#CM591425
	/**
	 * Consignor Name to be displayed
	 */
	private String wConsignorName = null;

	//#CM591426
	// Class method --------------------------------------------------
	//#CM591427
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.6 $,$Date: 2007/02/07 04:19:35 $");
	}

	//#CM591428
	// Constructors --------------------------------------------------
	//#CM591429
	/**
	 * Construct the RetrievalItemQtyWriter object.<BR>
	 * Set a Connection.<BR>
	 * @param conn <CODE>Connection</CODE> Database connection object
	 * @param locale <CODE>Locale</CODE>  <CODE>Locale</CODE> object for which Area Code is set.
	 */
	public RetrievalItemQtyWriter(Connection conn)
	{
		super(conn);
	}

	//#CM591430
	// Public methods ------------------------------------------------
	//#CM591431
	/**
	 * Set the search value for the Consignor code.
	 * @param pConsignorcode Consignor code to be set
	 */
	public void setConsignorCode(String pConsignorcode)
	{
		wConsignorCode = pConsignorcode;
	}

	//#CM591432
	/**
	 * Obtain the Consignor code.
	 * @return Consignor Code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM591433
	/**
	 * Set a value to be searched in the Start Picking Date.
	 * @param pFromPlanDate Start Picking Date to be set
	 */
	public void setFromWorkDate(String pFromWorkDate)
	{
		wFromWorkDate = pFromWorkDate;
	}

	//#CM591434
	/**
	 * Obtain the Start Picking Date.
	 * @return Start Picking Date
	 */
	public String getFromWorkDate()
	{
		return wFromWorkDate;
	}

	//#CM591435
	/**
	 * Set a value to be searched in the End Picking Date.
	 * @param pToPlanDate End Picking Date to be set
	 */
	public void setToWorkDate(String pToWorkDate)
	{
		wToWorkDate = pToWorkDate;
	}

	//#CM591436
	/**
	 * Obtain the End Picking Date.
	 * @return End Picking Date
	 */
	public String getToWorkDate()
	{
		return wToWorkDate;
	}

	//#CM591437
	/**
	 * Set the search value for the item code.
	 * @param pItemCode Item Code to be set
	 */
	public void setItemCode(String pItemCode)
	{
		wItemCode = pItemCode;
	}

	//#CM591438
	/**
	 * Obtain the item code.
	 * @return Item Code
	 */
	public String getItemCode()
	{
		return wItemCode;
	}

	//#CM591439
	/**
	 * Set the search value for Case/Piece division.
	 * @param pItemCode Case/Piece division to be set
	 */
	public void setCasePieceFlag(String pCasePieceFlag)
	{
		wCasePieceFlag = pCasePieceFlag;
	}

	//#CM591440
	/**
	 * Obtain the Case/Piece division.
	 * @return Case/Piece division
	 */
	public String getCasePieceFlag()
	{
		return wCasePieceFlag;
	}

	//#CM591441
	/**
	 * Obtain the count of the print data.<BR>
	 * Use this to determine to execute the process for printing by this search result.<BR>
	 * Use this method via the schedule class for processing the screen.<BR>
	 * 
	 * @return Count of printed data.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int count() throws ReadWriteException
	{
		ResultViewHandler instockHandle = new ResultViewHandler(wConn);
		ResultViewSearchKey searchKey = new ResultViewSearchKey();
		//#CM591442
		// Set the search conditions and obtain the count.
		setResultViewSearchKey(searchKey);
		return instockHandle.count(searchKey);

	}

	//#CM591443
	/**
	 * Generate a CSV file for Item Picking Result Report and invoke the class for printing.<BR>
	 * Search for the Work status using the search conditions input via screen.
	 * Disable to print if the search result count is less than 1 (one).<BR>
	 * Return true if succeeded in printing. Return false if failed.<BR>
	 * 
	 * @return boolean Result whether the printing succeeded or not.
	 */
	public boolean startPrint()
	{

		ResultViewReportFinder resultviewReportFinder = new ResultViewReportFinder(wConn);

		try
		{
			ResultViewSearchKey searchKey = new ResultViewSearchKey();

			//#CM591444
			// Set the Search key.
			searchKey = setResultViewSearchKey(searchKey);

			searchKey.setWorkDateOrder(1, true);
			searchKey.setPlanDateOrder(2, true);
			searchKey.setItemCodeOrder(3, true);
			searchKey.setWorkFormFlagOrder(4, true);
			searchKey.setLocationNoOrder(5, true);
			searchKey.setRegistDateOrder(6, true);
			searchKey.setResultQtyOrder(7, true);

			//#CM591445
			// Execute searching.
			//#CM591446
			// If no data exists, disable to execute print process.
			if (resultviewReportFinder.search(searchKey) <= 0)
			{
				//#CM591447
				// 6003010=No print data found.
				wMessage = "6003010";
				return false;
			}

			//#CM591448
			// Commit the consignor name.
			wConsignorName = getConsignorName();

			//#CM591449
			// Generate a file to be output.
			if (!createPrintWriter(FNAME_RETRIEVAL_ITEMQTY))
			{
				return false;
			}

			//#CM591450
			// Generate contents of data files until there is no longer the search result.
			ResultView[] resultview = null;
			while (resultviewReportFinder.isNext())
			{
				//#CM591451
				// Output every 100 search results.
				resultview = (ResultView[]) resultviewReportFinder.getEntities(100);

				//#CM591452
				// Generate the contents to be output to the file.
				for (int i = 0; i < resultview.length; i++)
				{
					wStrText.append(re + "");
					//#CM591453
					// Consignor Code
					wStrText.append(ReportOperation.format(resultview[i].getConsignorCode()) + tb);
					//#CM591454
					// Consignor Name
					wStrText.append(ReportOperation.format(wConsignorName) + tb);
					//#CM591455
					// Picking Date
					wStrText.append(ReportOperation.format(resultview[i].getWorkDate()) + tb);
					//#CM591456
					// Planned Picking Date
					wStrText.append(ReportOperation.format(resultview[i].getPlanDate()) + tb);
					//#CM591457
					// Item Code
					wStrText.append(ReportOperation.format(resultview[i].getItemCode()) + tb);
					//#CM591458
					// Item Name
					wStrText.append(ReportOperation.format(resultview[i].getItemName1()) + tb);
					//#CM591459
					// Case/Piece division name
					//#CM591460
					// Obtain the Case/Piece division name from the Case/Piece division (Work Type).
					String casepiecename = DisplayUtil.getPieceCaseValue(resultview[i].getWorkFormFlag());
					wStrText.append(ReportOperation.format(casepiecename) + tb);
					//#CM591461
					// Packed Qty per Case
					wStrText.append(resultview[i].getEnteringQty() + tb);
					//#CM591462
					// Packed qty per bundle
					wStrText.append(resultview[i].getBundleEnteringQty() + tb);
					//#CM591463
					// Planned Work Case Qty
					wStrText.append(
						DisplayUtil.getCaseQty(
							resultview[i].getPlanEnableQty(),
							resultview[i].getEnteringQty(),
							resultview[i].getWorkFormFlag())
							+ tb);
					//#CM591464
					// Planned Work Piece Qty
					wStrText.append(
						DisplayUtil.getPieceQty(
							resultview[i].getPlanEnableQty(),
							resultview[i].getEnteringQty(),
							resultview[i].getWorkFormFlag())
							+ tb);
					//#CM591465
					// Result Case QTY
					wStrText.append(
						DisplayUtil.getCaseQty(
							resultview[i].getResultQty(),
							resultview[i].getEnteringQty(),
							resultview[i].getWorkFormFlag())
							+ tb);
					//#CM591466
					// Result Piece QTY
					wStrText.append(
						DisplayUtil.getPieceQty(
							resultview[i].getResultQty(),
							resultview[i].getEnteringQty(),
							resultview[i].getWorkFormFlag())
							+ tb);
					//#CM591467
					// Shortage Case Qty
					wStrText.append(
						DisplayUtil.getCaseQty(
							resultview[i].getShortageCnt(),
							resultview[i].getEnteringQty(),
							resultview[i].getWorkFormFlag())
							+ tb);
					//#CM591468
					// Shortage Piece Qty
					wStrText.append(
						DisplayUtil.getPieceQty(
							resultview[i].getShortageCnt(),
							resultview[i].getEnteringQty(),
							resultview[i].getWorkFormFlag())
							+ tb);
					//#CM591469
					// Picking Location 
					wStrText.append(
						WmsFormatter.toDispLocation(
							resultview[i].getResultLocationNo(),
							resultview[i].getSystemDiscKey())
							+ tb);
					//#CM591470
					// Expiry Date
					wStrText.append(ReportOperation.format(resultview[i].getResultUseByDate()));

					//#CM591471
					// Output the data to the file.
					wPWriter.print(wStrText);
					wStrText.setLength(0);

				}
			}

			//#CM591472
			// Close the stream.
			wPWriter.close();

			//#CM591473
			// Execute UCXSingle (Execute printing).
			if (!executeUCX(JOBID_RETRIEVAL_ITEMQTY))
			{
				//#CM591474
				// Failed to print. See log.
				return false;
			}

			//#CM591475
			// If succeeded in printing, move the data file to the backup folder.
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException e)
		{
			//#CM591476
			// 6007034=Printing failed. See log.
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM591477
				// Execute processes for closing the cursor for the opened database.
				resultviewReportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM591478
				// Database error occurred. See log.
				setMessage("6007002");
				return false;
			}
		}

		return true;
	}

	//#CM591479
	// Package methods -----------------------------------------------

	//#CM591480
	// Protected methods ---------------------------------------------

	//#CM591481
	// Private methods -----------------------------------------------
	//#CM591482
	/**
	 * Allow this method to set the search conditions for the Work Status search key.<BR>
	 * 
	 * @param searchKey WorkingInformationSearchKey Search key for Work Status
	 * @return Search key for Work Status
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private ResultViewSearchKey setResultViewSearchKey(ResultViewSearchKey serachKey) throws ReadWriteException
	{
		//#CM591483
		// Consignor Code
		if (!StringUtil.isBlank(wConsignorCode))
		{
			serachKey.setConsignorCode(wConsignorCode);
		}
		//#CM591484
		// Start Picking Date
		if (!StringUtil.isBlank(wFromWorkDate))
		{
			serachKey.setWorkDate(wFromWorkDate, ">=");
		}
		//#CM591485
		// End Picking Date
		if (!StringUtil.isBlank(wToWorkDate))
		{
			serachKey.setWorkDate(wToWorkDate, "<=");
		}
		//#CM591486
		// Item Code
		if (!StringUtil.isBlank(wItemCode))
		{
			serachKey.setItemCode(wItemCode);
		}
		//#CM591487
		// Case/Piece division (Work Type) 	
		//#CM591488
		// Set for each Case/Piece division selected via screen. * Selecting "All" disables to set it.
		//#CM591489
		// Case
		if (wCasePieceFlag != null)
		{
			if (wCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				serachKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_CASE);
			}
			//#CM591490
			// Piece
			else if (wCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				serachKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_PIECE);
			}
			//#CM591491
			// "None"
			else if (wCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				serachKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_NOTHING);
			}
		}

		//#CM591492
		// Work division (Picking) 
		serachKey.setJobType(ResultView.JOB_TYPE_RETRIEVAL);
		//#CM591493
		// Search for data with Order No. "null".
		serachKey.setOrderNo("", "");

		return serachKey;

	}

	//#CM591494
	/**
	 * Allow this method to obtain the Consignor name.<BR>
	 * <BR>
	 * Some data may have two or more Consignor Names for Consignor Code.
	 *  Therefore, return the Consignor Name with the latest record of the Added Date/Time.<BR>
	 * 
	 * @return Consignor Name
	 * @throws ReadWriteException Announce in the event of error in accessing to the database.
	 */
	private String getConsignorName() throws ReadWriteException
	{
		String consignorName = "";

		//#CM591495
		// Generate the Finder instance.
		ResultViewReportFinder consignorFinder = new ResultViewReportFinder(wConn);
		ResultViewSearchKey resultViewSearchKey = new ResultViewSearchKey();

		//#CM591496
		// Set the search conditions.
		resultViewSearchKey = setResultViewSearchKey(resultViewSearchKey);

		resultViewSearchKey.setRegistDateOrder(1, false);

		//#CM591497
		// Search Consignor Name
		consignorFinder.open();
		if (consignorFinder.search(resultViewSearchKey) > 0)
		{
			ResultView[] result = (ResultView[]) consignorFinder.getEntities(1);

			if (result != null && result.length != 0)
			{
				consignorName = result[0].getConsignorName();
			}
		}

		consignorFinder.close();
		return consignorName;
	}

}
//#CM591498
//end of class
