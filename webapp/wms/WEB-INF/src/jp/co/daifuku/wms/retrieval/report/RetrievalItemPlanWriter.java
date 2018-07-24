// $Id: RetrievalItemPlanWriter.java,v 1.6 2007/02/07 04:19:34 suresh Exp $
package jp.co.daifuku.wms.retrieval.report;

//#CM591339
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
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;

//#CM591340
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * Allow this class to generate a data file for Item Picking Plan report and invoke the class for printing.<BR>
 * Search for Picking Plan Info using the contents set in the schedule class and generate a data file for report.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * Execute the process for generating a data file for report. (<CODE>startPrint()</CODE>method)<BR>
 * <DIR>
 *   Search through the Picking Plan Info.<BR>
 *   Disable to generate data file for report if no corresponding data exists.<BR>
 *   Generate a data file for report if corresponding data exists, and invoke the class for executing printing.<BR>
 * <BR>
 *   <Search conditions>*Mandatory
 *   <DIR>
 *     Consignor Code*<BR>
 *     Start Planned Shipping Date<BR>
 *     End Planned Shipping Date<BR>
 *     Item Code<BR>
 *     Work Status<BR>
 *   </DIR>
 * </DIR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/06</TD><TD>Y.Okamura</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.6 $, $Date: 2007/02/07 04:19:34 $
 * @author  $Author: suresh $
 */
public class RetrievalItemPlanWriter extends CSVWriter
{
	//#CM591341
	// Class fields --------------------------------------------------

	//#CM591342
	// Class variables -----------------------------------------------
	//#CM591343
	/**
	 * Consignor Code
	 */
	private String wConsignorCode;

	//#CM591344
	/**
	 * Start Planned Shipping Date
	 */
	private String wFromPlanDate;

	//#CM591345
	/**
	 * End Planned Shipping Date
	 */
	private String wToPlanDate;

	//#CM591346
	/**
	 * Item Code
	 */
	private String wItemCode;

	//#CM591347
	/**
	 * Work Status
	 */
	private String wStatusFlag;

	//#CM591348
	// Class method --------------------------------------------------
	//#CM591349
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.6 $,$Date: 2007/02/07 04:19:34 $");
	}

	//#CM591350
	// Constructors --------------------------------------------------
	//#CM591351
	/**
	 * Construct the RetrievalItemPlanWriter object.<BR>
	 * Set the connection and the locale.<BR>
	 * @param conn <CODE>Connection</CODE> Database connection object
	 */
	public RetrievalItemPlanWriter(Connection conn)
	{
		super(conn);
	}

	//#CM591352
	// Public methods ------------------------------------------------
	//#CM591353
	/**
	 * Set the search value for the Consignor code.
	 * @param pConsignorcode Consignor code to be set
	 */
	public void setConsignorCode(String pConsignorcode)
	{
		wConsignorCode = pConsignorcode;
	}

	//#CM591354
	/**
	 * Obtain the Consignor code.
	 * @return Consignor Code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM591355
	/**
	 * Set a value to be searched in the Start Planned Shipping Date.
	 * @param pFromPlanDate Start Planned Shipping Date to be set
	 */
	public void setFromPlanDate(String pFromPlanDate)
	{
		wFromPlanDate = pFromPlanDate;
	}

	//#CM591356
	/**
	 * Obtain the Start Planned Shipping Date.
	 * @return Start Planned Shipping Date
	 */
	public String getFromPlanDate()
	{
		return wFromPlanDate;
	}

	//#CM591357
	/**
	 * Set a value to be searched in the End Planned Shipping Date.
	 * @param pToPlanDate End Planned Shipping Date to be set
	 */
	public void setToPlanDate(String pToPlanDate)
	{
		wToPlanDate = pToPlanDate;
	}

	//#CM591358
	/**
	 * Obtain the End Planned Shipping Date.
	 * @return End Planned Shipping Date
	 */
	public String getToPlanDate()
	{
		return wToPlanDate;
	}

	//#CM591359
	/**
	 * Set the search value for the item code.
	 * @param pItemCode Item Code to be set
	 */
	public void setItemCode(String pItemCode)
	{
		wItemCode = pItemCode;
	}

	//#CM591360
	/**
	 * Obtain the item code.
	 * @return Item Code
	 */
	public String getItemCode()
	{
		return wItemCode;
	}

	//#CM591361
	/**
	 * Set a value to be searched in the Work Status.
	 * @param pStatusFlag Work Status to be set
	 */
	public void setStatusFlag(String pStatusFlag)
	{
		wStatusFlag = pStatusFlag;
	}

	//#CM591362
	/**
	 * Obtain the work status.
	 * @return Work Status
	 */
	public String getStatusFlag()
	{
		return wStatusFlag;
	}
	
	//#CM591363
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
		RetrievalPlanHandler instockHandle = new RetrievalPlanHandler(wConn);
		RetrievalPlanSearchKey searchKey = new RetrievalPlanSearchKey();
		//#CM591364
		// Set the search conditions and obtain the count.
		setRetrievalPlanSearchKey(searchKey);
		return instockHandle.count(searchKey);

	}

	//#CM591365
	/**
	 * Generate a CSV file for Item Picking Plan Report and invoke the class for printing.<BR>
	 * Search for the Work status using the search conditions input via screen.
	 * Disable to print if the search result count is less than 1 (one).<BR>
	 * Return true if succeeded in printing. Return false if failed.<BR>
	 * Execute the processes in the order as below.<BR>
	 * <BR>
	 * <DIR>
	 *   1.Set the search conditions and execute the search process.<BR>
	 *   2.Disable to print if the search result count is less than 1 (one).<BR>
	 *   3.Obtain every 100 search results and output them to the file.<BR>
	 *   4.Execute printing..<BR>
	 *   5.If succeeded in printing, move the data file to the backup folder.<BR>
	 *   6.Return the result whether the printing succeeded or not.<BR>
	 * </DIR>
	 * 
	 * @return boolean Result whether the printing succeeded or not.
	 */
	public boolean startPrint()
	{

		RetrievalPlanReportFinder retrievalPlanFinder = new RetrievalPlanReportFinder(wConn);

		try
		{
			//#CM591366
			// Execute searching.
			RetrievalPlanSearchKey retrievalSearchKey = new RetrievalPlanSearchKey();

			//#CM591367
			// Set the search conditions.
			setRetrievalPlanSearchKey(retrievalSearchKey);
			//#CM591368
			// Set the search order.
			retrievalSearchKey.setConsignorCodeOrder(1, true);
			retrievalSearchKey.setPlanDateOrder(2, true);
			retrievalSearchKey.setItemCodeOrder(3, true);
			retrievalSearchKey.setCasePieceFlagOrder(4, true);
			retrievalSearchKey.setCaseLocationOrder(5, true);
			retrievalSearchKey.setPieceLocationOrder(6, true);

			//#CM591369
			// If no data exists, disable to execute print process.
			if (retrievalPlanFinder.search(retrievalSearchKey) <= 0)
			{
				//#CM591370
				// 6003010=No print data found.
				wMessage = "6003010";
				return false;
			}

			//#CM591371
			// Generate a file for output.
			if(!createPrintWriter(FNAME_RETRIEVAL_ITEMPLAN))
			{
				return false;
			}
			
			//#CM591372
			// Obtain the Name.
			String consignorName = "";
			//#CM591373
			// Generate contents of data files until there is no longer the search result.
			RetrievalPlan[] retPlan = null;
			while (retrievalPlanFinder.isNext())
			{
				//#CM591374
				// FTTB Output every 100 search results.
				retPlan = (RetrievalPlan[]) retrievalPlanFinder.getEntities(100);

				//#CM591375
				// Generate the contents to be output to the file.
				for (int i = 0; i < retPlan.length; i++)
				{
					wStrText.append(re + "");

					//#CM591376
					// Consignor Code
					wStrText.append(ReportOperation.format(retPlan[i].getConsignorCode()) + tb);
					//#CM591377
					// Consignor Name
					if (i == 0 || !wConsignorCode.equals(retPlan[i].getConsignorCode()))
					{
						//#CM591378
						// Rewrite the Consignor Code.
						wConsignorCode = retPlan[i].getConsignorCode();
						//#CM591379
						// Obtain the Consignor name.
						consignorName = getConsignorName();
					}
					wStrText.append(ReportOperation.format(consignorName) + tb);
					//#CM591380
					// Planned Picking Date
					wStrText.append(ReportOperation.format(retPlan[i].getPlanDate()) + tb);
					//#CM591381
					// Item Code
					wStrText.append(ReportOperation.format(retPlan[i].getItemCode()) + tb);
					//#CM591382
					// Item Name
					wStrText.append(ReportOperation.format(retPlan[i].getItemName1()) + tb);
					//#CM591383
					// Division
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(retPlan[i].getCasePieceFlag())) + tb);
					//#CM591384
					// Packed Qty per Case
					wStrText.append(retPlan[i].getEnteringQty() + tb);
					//#CM591385
					// Packed qty per bundle
					wStrText.append(retPlan[i].getBundleEnteringQty() + tb);
					//#CM591386
					// Host Planned Case QTY
					wStrText.append(DisplayUtil.getCaseQty(retPlan[i].getPlanQty(), retPlan[i].getEnteringQty(),retPlan[i].getCasePieceFlag()) + tb);
					//#CM591387
					// Host Planned Piece Qty
					wStrText.append(DisplayUtil.getPieceQty(retPlan[i].getPlanQty(), retPlan[i].getEnteringQty(),retPlan[i].getCasePieceFlag()) + tb);
					//#CM591388
					// Picking Case QTY
					wStrText.append(DisplayUtil.getCaseQty(retPlan[i].getResultQty(), retPlan[i].getEnteringQty(),retPlan[i].getCasePieceFlag()) + tb);
					//#CM591389
					// Picking Piece QTY
					wStrText.append(DisplayUtil.getPieceQty(retPlan[i].getResultQty(), retPlan[i].getEnteringQty(),retPlan[i].getCasePieceFlag()) + tb);
					//#CM591390
					// Case Picking Location
					wStrText.append(ReportOperation.format(retPlan[i].getCaseLocation()) + tb);
					//#CM591391
					// Piece Picking Location
					wStrText.append(ReportOperation.format(retPlan[i].getPieceLocation()) + tb);
					//#CM591392
					// Work Status
					wStrText.append(ReportOperation.format(DisplayUtil.getRetrievalPlanStatusValue(retPlan[i].getStatusFlag())));

					//#CM591393
					// Output the data to the file.
					wPWriter.print(wStrText);
					wStrText.setLength(0);
				}
			}

			//#CM591394
			// Close the stream.
			wPWriter.close();

			//#CM591395
			// Execute UCXSingle (Execute printing).
			if (!executeUCX(JOBID_RETRIEVAL_ITEMPLAN))
			{
				//#CM591396
				// Failed to print. See log.
				return false;
			}

			//#CM591397
			// If succeeded in printing, move the data file to the backup folder.
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException re)
		{
			//#CM591398
			// 6007034=Printing failed. See log.
			setMessage("6007034");

			return false;
		}
		finally
		{
			try
			{
				//#CM591399
				// Execute processes for closing the cursor for the opened database.
				retrievalPlanFinder.close();
			}
			catch (ReadWriteException e)
			{				
				setMessage("6007002");
				return false;			
			}
			
		}

		return true;
	}

	//#CM591400
	// Package methods -----------------------------------------------

	//#CM591401
	// Protected methods ---------------------------------------------

	//#CM591402
	// Private methods -----------------------------------------------
	//#CM591403
	/**
	 * Allow this method to set the search conditions for the Picking Plan Info search key.<BR>
	 * 
	 * @param searchKey "Search" key for Picking Plan Info
	 * @return "Search" key for Picking Plan Info
	 * @throws ReadWriteException Announce in the event of error in accessing to the database.
	 */
	private RetrievalPlanSearchKey setRetrievalPlanSearchKey(RetrievalPlanSearchKey searchKey) throws ReadWriteException
	{
		//#CM591404
		// Set the Search key.
		//#CM591405
		// Consignor Code
		if (!StringUtil.isBlank(wConsignorCode))
		{
			searchKey.setConsignorCode(wConsignorCode);
		}
		//#CM591406
		// Start Planned Picking Date
		if (!StringUtil.isBlank(wFromPlanDate))
		{
			searchKey.setPlanDate(wFromPlanDate, ">=");
		}
		//#CM591407
		// End Planned Picking Date
		
		if (!StringUtil.isBlank(wToPlanDate))
		{
			searchKey.setPlanDate(wToPlanDate, "<=");
		}
		//#CM591408
		// Item Code
		if (!StringUtil.isBlank(wItemCode))
		{
			searchKey.setItemCode(wItemCode);
		}
		//#CM591409
		// Work Status
		if (!StringUtil.isBlank(wStatusFlag))
		{
			searchKey.setStatusFlag(wStatusFlag);
		}
		else
		{
			//#CM591410
			// For "Search All", search for data other than "Deleted" data.
			searchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
		}
		//#CM591411
		// Data with Order No. "blank"
		searchKey.setCaseOrderNo("", "");
		searchKey.setPieceOrderNo("", "");

		return searchKey;
	}

	//#CM591412
	/**
	 * Allow this method to obtain the Consignor name.<BR>
	 * <BR>
	 * Some data may have two or more Consignor Names for Consignor Code.
	 *  Therefore, return the Customer Name with the latest record of the Added Date/Time.<BR>
	 * Obtain it using the RetrievalPlanReportFinder class.<BR>
	 * 
	 * @return Consignor Name
	 * @throws ReadWriteException Announce in the event of error in accessing to the database.
	 */
	private String getConsignorName() throws ReadWriteException
	{
		String consignorName = "";

		//#CM591413
		// Set the Search key.
		RetrievalPlanSearchKey nameKey = new RetrievalPlanSearchKey();
		RetrievalPlanReportFinder nameFinder = new RetrievalPlanReportFinder(wConn);

		setRetrievalPlanSearchKey(nameKey);
		nameKey.setConsignorNameCollect("");
		nameKey.setRegistDateOrder(1, false);
		//#CM591414
		// Execute the search.
		nameFinder.open();
		
		if (nameFinder.search(nameKey) > 0)
		{
			RetrievalPlan winfo[] = (RetrievalPlan[]) ((RetrievalPlanReportFinder) nameFinder).getEntities(1);

			if (winfo != null && winfo.length != 0)
			{
				consignorName = winfo[0].getConsignorName();
			}
		}
		nameFinder.close();

		return consignorName;
	}

}
//#CM591415
//end of class
