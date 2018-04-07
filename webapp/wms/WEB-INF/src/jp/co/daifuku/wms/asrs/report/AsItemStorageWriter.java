// $Id: AsItemStorageWriter.java,v 1.5 2006/12/13 09:03:19 suresh Exp $

//#CM43310
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.report;

import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.dbhandler.AsWorkingInformationReportFinder;
import jp.co.daifuku.wms.asrs.entity.AsWorkingInformation;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;

//#CM43311
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * < CODE>AsItemStorageWriter</CODE > class makes the data file for the slit of the ASRS stock work list, and executes the print. <BR>
 * Retrieve the content displayed in the list part on the screen based on the condition set in < CODE>AsItemStorageSCH</CODE > class. <BR>
 * Make the result as a slit file for the ASRS stock work list.  <BR>
 * This class processes as follows. <BR>
 * <BR>
 * Data file making processing for slit(< CODE>startPrint() </ CODE > method) <BR>
 * <DIR>
 *	1.Retrieve the number of cases of the screen input data on the condition set from < CODE>AsItemStorageSCH</CODE > class. <BR>
 *	2.Make the slit file if the result is one or more. Return false and end in case of 0. <BR>
 *	3.Execute the printing job. <BR>
 *	4.Return true when the printing job is normal. <BR>
 *    Return false when the error occurs in the printing job. <BR>
 * <BR>
 * 	<Parameter> * Mandatory input <BR><DIR>
 * 	  Batch No * <BR></DIR>
 * <BR>
 * 	< search condition > <BR><DIR>
 * 	  Batch No <BR></DIR>
 * <BR>
 *  <retrieval order> <BR><DIR>
 *    1.Ascending order of Consignor Code <BR>
 *    2.Ascending order of work result location <BR>
 *    3.Ascending order of Item Code <BR>
 *    4.Ascending order of case piece division <BR></DIR>
 * <BR>
 *	<print data> <BR><DIR>
 *	  Print entry : DB item <BR>
 *	  Consignor : Consignor Code + Consignor Name <BR>
 *	  Stock day : Work day <BR>
 *    Stock shelf : Work result location <BR>
 *	  Item Code : Item Code <BR>
 *	  Item Name : Item Name <BR>
 *    Division: Case piece division <BR>
 *    Qty per case : Qty per case <BR>
 *    Qty per bundle : Qty per bundle <BR>
 *    Storage qty per case : Actual work qty / Qty per case <BR>
 *    Storage qty per piece : Actual work qty % Qty per case <BR>
 *    Case ITF : Case ITF <BR>
 *    Bundle ITF : Bundle ITF <BR>
 *    Expiry date : Work result Expiry date</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/29</TD><TD>K.Toda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2006/12/13 09:03:19 $
 * @author  $Author: suresh $
 */
public class AsItemStorageWriter extends CSVWriter
{
	//#CM43312
	// Class fields --------------------------------------------------

	//#CM43313
	// Class variables -----------------------------------------------
	//#CM43314
	/**
	 * Batch No.
	 */
	protected String wBatchNumber;
	//#CM43315
	/**
	 * Station No.
	 */
	protected String wStationNo;
	//#CM43316
	/**
	 * Start day
	 */
	protected Date wFromDate;
	//#CM43317
	/**
	 * End day
	 */
	protected Date wToDate;

	//#CM43318
	// Class method --------------------------------------------------
	//#CM43319
	/**
	 * Return the version of this class.
	 * 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.5 $,$Date: 2006/12/13 09:03:19 $");
	}

	//#CM43320
	// Constructors --------------------------------------------------
	//#CM43321
	/**
	 * Construct the AsItemStorageWriter object. <BR>
	 * Set the connection. <BR>
	 * 
	 * @param conn Connection object with <CODE>Connection</CODE> database
	 */
	public AsItemStorageWriter(Connection conn)
	{
		super(conn);
	}

	//#CM43322
	// Public methods ------------------------------------------------
	//#CM43323
	/**
	 * Acquire Batch No.
	 * @return Batch No.
	 */
	public String getBatchNumber()
	{
		return wBatchNumber;
	}

	//#CM43324
	/**
	 * Set Batch No.
	 * @param batchNumber Batch No.
	 */
	public void setBatchNumber(String batchNumber)
	{
		wBatchNumber = batchNumber;
	}

	//#CM43325
	/**
	 * Acquire Station No.
	 * @return Station No.
	 */
	public String getStationNo()
	{
		return wStationNo;
	}

	//#CM43326
	/**
	 * Set retrieval value in Station No.
	 * @param stationNo Station No.
	 */
	public void setStationNo(String stationNo)
	{
		wStationNo = stationNo;
	}

	//#CM43327
	/**
	 * Acquire Start day.
	 * @return Start day
	 */
	public Date getFromDate()
	{
		return wFromDate;
	}

	//#CM43328
	/**
	 * Set the retrieval value in Start day.
	 * @param fromDate Start day
	 */
	public void setFromDate(Date fromDate)
	{
		wFromDate = fromDate;
	}

	//#CM43329
	/**
	 * Acquire End day.
	 * @return End day
	 */
	public Date getToDate()
	{
		return wToDate;
	}

	//#CM43330
	/**
	 * Set the retrieval value in End day.
	 * @param toDate End day
	 */
	public void setToDate(Date toDate)
	{
		wToDate = toDate;
	}

	//#CM43331
	/**
	 * Acquire the number of cases of the print data.<BR>
	 * Use it to judge whether to do the print processing by this retrieval result.<BR>
	 * This method is used by the schedule class which processes the screen.<BR>
	 * 
	 * @return Print data number
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	public int count() throws ReadWriteException
	{
		AsWorkingInformationReportFinder reportFinder = new AsWorkingInformationReportFinder(wConn);

		//#CM43332
		// Retrieval execution
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
		CarryInformationSearchKey carryInfoSearchKey = new CarryInformationSearchKey();

		//#CM43333
		// Set the search condition, and acquire the number.
		if (!StringUtil.isBlank(wBatchNumber))
		{
			workInfoSearchKey.setBatchNo(wBatchNumber);
		}
		if (!StringUtil.isBlank(wFromDate))
		{
			workInfoSearchKey.setRegistDate(wFromDate, ">=");
		}
		if (!StringUtil.isBlank(wToDate))
		{
			workInfoSearchKey.setRegistDate(wToDate, "<=");
		}
		if (!StringUtil.isBlank(wStationNo))
		{
			carryInfoSearchKey.setSourceStationNumber(wStationNo);
		}
		
		workInfoSearchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		workInfoSearchKey.setSystemDiscKey(WorkingInformation.SYSTEM_DISC_KEY_ASRS);
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE,"<>");
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION,"<>");

		return reportFinder.count(workInfoSearchKey, carryInfoSearchKey);
	}

	//#CM43334
	/**
	 * Make the CSV file for the ASRS stock list, and execute the print. <BR>
	 * Make the slit file if the result is one or more. Return false and end in case of 0. <BR>
	 * Execute the printing job.
	 * @return True when the printing job ends normally and False when failing.
	 */
	public boolean startPrint()
	{
		AsWorkingInformationReportFinder reportFinder = new AsWorkingInformationReportFinder(wConn);
		try
		{
			//#CM43335
			// Retrieval execution
			WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
			CarryInformationSearchKey carryInfoSearchKey = new CarryInformationSearchKey();

			//#CM43336
			// Set the order of the retrieval.

			//#CM43337
			// Set the search condition, and acquire the number.
			if (!StringUtil.isBlank(wBatchNumber))
			{
				workInfoSearchKey.setBatchNo(wBatchNumber);
			}
			if (!StringUtil.isBlank(wFromDate))
			{
				workInfoSearchKey.setRegistDate(wFromDate, ">=");
			}
			if (!StringUtil.isBlank(wToDate))
			{
				workInfoSearchKey.setRegistDate(wToDate, "<=");
			}
			if (!StringUtil.isBlank(wStationNo))
			{
				carryInfoSearchKey.setSourceStationNumber(wStationNo);
			}
	
			workInfoSearchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
			workInfoSearchKey.setSystemDiscKey(WorkingInformation.SYSTEM_DISC_KEY_ASRS);
			workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE,"<>");
			workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION,"<>");

			carryInfoSearchKey.setWorkNumberOrder(1, true);
			workInfoSearchKey.setConsignorCodeOrder(2, true);
			workInfoSearchKey.setItemCodeOrder(3, true);
			workInfoSearchKey.setUseByDateOrder(4, true);
			workInfoSearchKey.setCasePieceFlagOrder(5, true);

			//#CM43338
			// Do not do the print processing when there are no data in WorkingInformation and the CarryInfo table.
			if(reportFinder.search(workInfoSearchKey, carryInfoSearchKey) <= 0)
			{
				//#CM43339
				// 6003010 = There was no print data.
				wMessage = "6003010";
				return false;
			}			

			//#CM43340
			// The output file is made.
			if (!createPrintWriter(FNAME_STRAGE_WORK))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_ASRSITEMSTORAGE);

			//#CM43341
			// Make the content of the data file until the retrieval result is lost.
			AsWorkingInformation[] itemStorage = null;
			while (reportFinder.isNext())
			{
				//#CM43342
				// It acquires it from the retrieval result to 100.
				itemStorage = (AsWorkingInformation[]) reportFinder.getEntities(100);
				for (int i = 0; i < itemStorage.length; i++)
				{ 
					//#CM43343
					// List part edit
					wStrText.append(re + "");
					//#CM43344
					// Station No
					wStrText.append(ReportOperation.format(itemStorage[i].getStationNo()) + tb);
					//#CM43345
					// Work No.
					wStrText.append(ReportOperation.format(itemStorage[i].getWorkNumber()) + tb);
					//#CM43346
					// Storage shelf
					String LocationNo = DisplayText.formatLocation(itemStorage[i].getLocationNo());
					wStrText.append(ReportOperation.format(LocationNo) + tb);
					//#CM43347
					// Consignor Code
					wStrText.append(ReportOperation.format(itemStorage[i].getConsignorCode()) + tb);
					//#CM43348
					// Consignor Name
					wStrText.append(ReportOperation.format(itemStorage[i].getConsignorName()) + tb);
					//#CM43349
					// Item Code
					wStrText.append(ReportOperation.format(itemStorage[i].getItemCode()) + tb);
					//#CM43350
					// Item Name
					wStrText.append(ReportOperation.format(itemStorage[i].getItemName1()) + tb);
					//#CM43351
					// Storage plan date
					wStrText.append(ReportOperation.format(itemStorage[i].getPlanDate()) + tb);
					//#CM43352
					// Expiry date
					wStrText.append(ReportOperation.format(itemStorage[i].getUseByDate()) + tb);
					//#CM43353
					// Results case
					wStrText.append(DisplayUtil.getCaseQty(itemStorage[i].getPlanEnableQty(), itemStorage[i].getEnteringQty(), itemStorage[i].getCasePieceFlag()) + tb);
					//#CM43354
					// Results piece
					wStrText.append(DisplayUtil.getPieceQty(itemStorage[i].getPlanEnableQty(), itemStorage[i].getEnteringQty(), itemStorage[i].getCasePieceFlag()) + tb);
					//#CM43355
					// Case/Piece flag
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(itemStorage[i].getCasePieceFlag())) + tb);
					//#CM43356
					// Case ITF
					wStrText.append(ReportOperation.format(itemStorage[i].getItf()) + tb);
					//#CM43357
					// Bundle ITF
					wStrText.append(ReportOperation.format(itemStorage[i].getBundleItf()) + tb);
					//#CM43358
					// Entry
					wPWriter.print(wStrText);
					wStrText.setLength(0);
				}
			}

			//#CM43359
			// Close the stream.
			wPWriter.close();

			//#CM43360
			// UCXSingle is executed. (print execution)
			if (!executeUCX(JOBID_ASRSITEMSTORAGE))
			{
				//#CM43361
				// It failed in the print. Refer to the log.
				return false;
			}

			//#CM43362
			// Move the data file to the backup folder when succeeding in the print.
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException e)
		{
			//#CM43363
			// It failed in the print. Refer to the log.
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM43364
				// Do the close processing of the opening database cursor.
				reportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM43365
				// The database error occurred. Refer to the log.
				setMessage("6007002");
				return false;
			}
		}
		return true;
	}
	
	//#CM43366
	// Package methods -----------------------------------------------

	//#CM43367
	// Protected methods ---------------------------------------------

	//#CM43368
	// Private methods -----------------------------------------------

}
//#CM43369
//end of class
