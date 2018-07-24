// $Id: AsNoPlanStorageWriter.java,v 1.5 2006/12/13 09:03:18 suresh Exp $

//#CM43529
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
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;

//#CM43530
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * < CODE>AsNoPlanStorageWriter</CODE > class makes the data file for the slit of the work of the stock outside the ASRS schedule list, and executes the print. <BR>
 * Retrieve the content displayed in the list part on the screen based on the condition set on the screen, and make the result as a
 * slit file for the work of the stock outside the ASRS schedule list.  <BR>
 * This class processes it as follows. <BR>
 * <BR>
 * Data file making processing for slit(<CODE>startPrint() </CODE> method)<BR>
 * <DIR>
 *	1.Retrieve the number of cases of the screen input data. <BR>
 *	2.Make the slit file if the result is one or more. Return false and end in case of 0. <BR>
 *	3.Execute the printing job. <BR>
 *	4.Return true when the printing job is normal. <BR>
 *    Return false when the error occurs in the printing job. <BR>
 * <BR>
 * 	<Parameter from Submit screen> * Mandatory Input <BR><DIR>
 * 	  Batch No. * <BR>
 * 	  Storage Station No. * <BR></DIR>
 * <BR>
 * 	<Parameter from list issue screen> * Mandatory Input <BR><DIR>
 * 	  Storage Station No. * <BR>
 * 	  Retrieval Station No. * <BR>
 * 	  Start day & time  <BR>
 * 	  End day & time  <BR></DIR>
 * <BR>
 *  <retrieval order> <BR><DIR>
 *    1.Ascending order of Consignor Code <BR>
 *    2.Ascending order of work result location <BR>
 *    3.Ascending order of Item Code <BR>
 *    4.Ascending order of Case/Piece flag<BR></DIR>
 * <BR>
 *	<Print data> <BR><DIR>
 *	  Print entry: DB item<BR>
 *	  Consignor : Consignor Code + Consignor Name <BR>
 *	  Storage date	: Work date <BR>
 *    Storage shelf : Work result location <BR>
 *	  Item Code	:Item Code <BR>
 *	  Item Name		:Item Name <BR>
 *    Flag			:Case/Piece flag <BR>
 *    Qty per case : Qty per case <BR>
 *    Qty per bundle : Qty per bundle <BR>
 *    Storage Case qty : Actual work qty / Qty per case <BR>
 *    Storage Piece qty :Actual work qty % Qty per case <BR>
 *    Case ITF : Case ITF <BR>
 *    Bundle ITF : Bundle ITF <BR>
 *    Expiry date : Work result Expiry date</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/16</TD><TD>K.Toda</TD><TD>created this class</TD></TR>
 * <TR><TD>2006/03/24</TD><TD>Y.Okamura</TD><TD>Class design correction. </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2006/12/13 09:03:18 $
 * @author  $Author: suresh $
 */
public class AsNoPlanStorageWriter extends CSVWriter
{
	//#CM43531
	// Class fields --------------------------------------------------

	//#CM43532
	// Class variables -----------------------------------------------
	//#CM43533
	/**
	 * Batch No.
	 */
	protected String wBatchNumber;
	//#CM43534
	/**
	 * Storage Station No.
	 */
	protected String wStorageStationNo;
	//#CM43535
	/**
	 * Retrieval Station No.
	 */
	protected String wRetrievalStationNo;
	//#CM43536
	/**
	 * Start day
	 */
	protected Date wFromDate;
	//#CM43537
	/**
	 * End day
	 */
	protected Date wToDate;
	//#CM43538
	/**
	 * List type
	 */
	protected String wListType = null;

	//#CM43539
	// Class method --------------------------------------------------
	//#CM43540
	/**
	 * Return the version of this class. 
	 * 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.5 $,$Date: 2006/12/13 09:03:18 $");
	}

	//#CM43541
	// Constructors --------------------------------------------------
	//#CM43542
	/**
	 * Construct the AsNoPlanStorageWriter object. <BR>
	 * Set the connection. <BR>
	 * 
	 * @param conn Connection object with <CODE>Connection</CODE> database
	 */
	public AsNoPlanStorageWriter(Connection conn)
	{
		super(conn);
	}

	//#CM43543
	// Public methods ------------------------------------------------
	//#CM43544
	/**
	 * Set Batch No.
	 * @param arg Batch No.
	 */
	public void setBatchNumber(String arg)
	{
		wBatchNumber = arg;
	}
	//#CM43545
	/**
	 * Acquire Batch No.
	 * @return Batch No.
	 */
	public String getBatchNumber()
	{
		return wBatchNumber;
	}

	//#CM43546
	/**
	 * Set Storage Station No.
	 * @param arg Storage Station No.
	 */
	public void setStorageStationNo(String arg)
	{
		wStorageStationNo = arg;
	}
	//#CM43547
	/**
	 * Acquire Storage Station No.
	 * @return Storage Station No.
	 */
	public String getStorageStationNo()
	{
		return wStorageStationNo;
	}

	//#CM43548
	/**
	 * Set Retrieval Station No.
	 * @param arg Retrieval Station No.
	 */
	public void setRetrievalStationNo(String arg)
	{
		wRetrievalStationNo = arg;
	}
	//#CM43549
	/**
	 * Acquire Retrieval Station No.
	 * @return Retrieval Station No.
	 */
	public String getRetrievalStationNo()
	{
		return wRetrievalStationNo;
	}

	//#CM43550
	/**
	 * Set the retrieval value in Start day. 
	 * @param date Start day
	 */
	public void setFromDate(Date date)
	{
		wFromDate = date;
	}
	//#CM43551
	/**
	 * Acquire Start day.
	 * @return Start day
	 */
	public Date getFromDate()
	{
		return wFromDate;
	}

	//#CM43552
	/**
	 * Set the retrieval value in End day. 
	 * @param date End day
	 */
	public void setToDate(Date date)
	{
		wToDate = date;
	}
	//#CM43553
	/**
	 * Acquire End day. 
	 * @return End day
	 */
	public Date getToDate()
	{
		return wToDate;
	}

	//#CM43554
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
		AsWorkingInformationReportFinder reportFinder = new AsWorkingInformationReportFinder(wConn);

		//#CM43555
		// Retrieval execution
		WorkingInformationSearchKey wiKey = new WorkingInformationSearchKey();
		CarryInformationSearchKey ciKey = new CarryInformationSearchKey();

		setSearchKey(wiKey, ciKey);

		return reportFinder.count(wiKey, ciKey);
	}

	//#CM43556
	/**
	 * Make the CSV file for the list of the stock outside the ASRS schedule, and call the print execution class. <BR>
	 * Retrieve work information on the search condition input on the screen. <BR>
	 * Do not print when the retrieval result is less than one. <BR>
	 * @return True when the printing job ends normally and False when failing.
	 */
	public boolean startPrint()
	{
		AsWorkingInformationReportFinder reportFinder = new AsWorkingInformationReportFinder(wConn);

		try
		{
			//#CM43557
			// Retrieval execution
			WorkingInformationSearchKey wiKey = new WorkingInformationSearchKey();
			CarryInformationSearchKey ciKey = new CarryInformationSearchKey();

			setSearchKey(wiKey, ciKey);

			ciKey.setWorkNumberOrder(1, true);
			wiKey.setConsignorCodeOrder(2, true);
			wiKey.setItemCodeOrder(3, true);
			wiKey.setUseByDateOrder(4, true);
			wiKey.setCasePieceFlagOrder(5, true);

			//#CM43558
			// Do not do the print processing when there are no data in WorkingInformation and the CarryInfo table. 
			if (reportFinder.search(wiKey, ciKey) <= 0)
			{
				//#CM43559
				// 6003010 = There was no print data. 
				wMessage = "6003010";
				return false;
			}

			//#CM43560
			// The output file is made. 
			if (!createPrintWriter(FNAME_ASRSSTORAGE))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_ASRSSTORAGE);

			//#CM43561
			// Make the data file 100 until the retrieval result is lost.
			AsWorkingInformation workInfo[] = null;
			while (reportFinder.isNext())
			{
				//#CM43562
				// It acquires it from the retrieval result to 100. 
				workInfo = (AsWorkingInformation[]) reportFinder.getEntities(100);
				for (int i = 0; i < workInfo.length; i++)
				{
					//#CM43563
					// List part edit
					wStrText.append(re + "");
					//#CM43564
					// Station No
					wStrText.append(ReportOperation.format(wStorageStationNo) + tb);
					//#CM43565
					// Work No.
					wStrText.append(ReportOperation.format(workInfo[i].getWorkNumber()) + tb);
					//#CM43566
					// Storage shelf
					String LocationNo = DisplayText.formatLocation(workInfo[i].getLocationNo());
					wStrText.append(ReportOperation.format(LocationNo) + tb);
					//#CM43567
					// Consignor Code
					wStrText.append(ReportOperation.format(workInfo[i].getConsignorCode()) + tb);
					//#CM43568
					// Consignor Name
					wStrText.append(ReportOperation.format(workInfo[i].getConsignorName()) + tb);
					//#CM43569
					// Item Code
					wStrText.append(ReportOperation.format(workInfo[i].getItemCode()) + tb);
					//#CM43570
					// Item Name
					wStrText.append(ReportOperation.format(workInfo[i].getItemName1()) + tb);
					//#CM43571
					// Expiry date
					wStrText.append(ReportOperation.format(workInfo[i].getUseByDate()) + tb);
					//#CM43572
					// Results case
					wStrText.append(
						DisplayUtil.getCaseQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty(), workInfo[i].getCasePieceFlag()) + tb);
					//#CM43573
					// Results piece
					wStrText.append(
						DisplayUtil.getPieceQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty(), workInfo[i].getCasePieceFlag()) + tb);
					//#CM43574
					// Case/Piece flag
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(workInfo[i].getCasePieceFlag())) + tb);
					//#CM43575
					// Case ITF
					wStrText.append(ReportOperation.format(workInfo[i].getItf()) + tb);
					//#CM43576
					// Bundle ITF
					wStrText.append(ReportOperation.format(workInfo[i].getBundleItf()) + tb);
					//#CM43577
					// Qty per case
					wStrText.append(workInfo[i].getEnteringQty() + tb);
					//#CM43578
					// Qty per bundle
					wStrText.append(workInfo[i].getBundleEnteringQty() + tb);
					//#CM43579
					// Entry
					wPWriter.print(wStrText);
					wStrText.setLength(0);
				}
			}
			//#CM43580
			// Close the stream.
			wPWriter.close();
			//#CM43581
			// UCXSingle is executed. 
			if (!executeUCX(JOBID_ASRSSTORAGE))
			{
				//#CM43582
				// It failed in the print. Refer to the log.  
				return false;
			}

			//#CM43583
			// When succeeding in the print, the data file is moved to the backup folder.
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException e)
		{
			//#CM43584
			// It failed in the print. Refer to the log. 
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM43585
				// Do the close processing of the opening data base cursor. 
				reportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM43586
				// The data base error occurred. Refer to the log. 
				setMessage("6007002");
				return false;
			}
		}
		return true;
	}

	//#CM43587
	// Package methods -----------------------------------------------

	//#CM43588
	// Protected methods ---------------------------------------------
	//#CM43589
	/** 
	 * Set the Where condition. 
	 * @param workInfoSearchKey Retrieval key to work information
	 * @param carryInfoSearchKey Retrieval key to transportation information
	 * @throws ReadWriteException It is notified when the error occurs when connecting it with the data base. 
	 */
	protected void setSearchKey(WorkingInformationSearchKey wiKey, CarryInformationSearchKey ciKey) throws ReadWriteException
	{
		//#CM43590
		// When Batch  No. is set
		//#CM43591
		// In a word, when calling it from a set screen
		if (!StringUtil.isBlank(wBatchNumber))
		{
			wiKey.setBatchNo(wBatchNumber);
		}
		//#CM43592
		// The rest
		//#CM43593
		// In a word, when calling it from the list issue screen
		else
		{
			//#CM43594
			// The piling up increase stock, and transportation Flag is the one of the delivery. 
			ciKey.setRetrievalDetail(CarryInformation.RETRIEVALDETAIL_ADD_STORING, "=", "((", "", "AND");
			ciKey.setCarryKind(CarryInformation.CARRYKIND_RETRIEVAL);
			ciKey.setDestStationNumber(wRetrievalStationNo, "=", "", ")", "OR");
			//#CM43595
			// The piling up increase stock, and transportation Flag is the one of the stock.
			//#CM43596
			// The stock station is set in the transportation origin when there is work display operation, and
			//#CM43597
			// the state that the stock side station is set and the delivery are according to timing when there is no work display operation
			//#CM43598
			// and is a state that it boils and the station is set. 
			ciKey.setRetrievalDetail(CarryInformation.RETRIEVALDETAIL_ADD_STORING, "=", "(", "", "AND");
			ciKey.setCarryKind(CarryInformation.CARRYKIND_STORAGE);
			ciKey.setSourceStationNumber(wRetrievalStationNo, "=", "(", "", "OR");
			ciKey.setSourceStationNumber(wStorageStationNo, "=", "", "))", "OR");
			//#CM43599
			// The initial storing, and transportation Flag is the one of the stock. (stock from setting of stock outside schedule)
			ciKey.setRetrievalDetail(CarryInformation.RETRIEVALDETAIL_UNKNOWN, "=", "(", "", "AND");
			ciKey.setCarryKind(CarryInformation.CARRYKIND_STORAGE);
			ciKey.setSourceStationNumber(wStorageStationNo, "=", "", "))", "AND");

			if (!StringUtil.isBlank(wFromDate))
			{
				ciKey.setCreateDate(wFromDate, ">=");
			}
			if (!StringUtil.isBlank(wToDate))
			{
				ciKey.setCreateDate(wToDate, "<=");
			}
		}
		wiKey.setJobType(WorkingInformation.JOB_TYPE_EX_STORAGE);
		wiKey.setSystemDiscKey(WorkingInformation.SYSTEM_DISC_KEY_ASRS);
		wiKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");

	}

	//#CM43600
	// Private methods -----------------------------------------------
}
//#CM43601
//end of class
