// $Id: AsNoPlanRetrievalWriter.java,v 1.5 2006/12/13 09:03:18 suresh Exp $

//#CM43457
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

//#CM43458
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * < CODE>AsNoPlanRetrievalWriter</CODE > class makes the data file for the slit of the ASRS delivery work list, and executes the print. <BR>
 * Retrieve work information (DNWORKINFO) based on the condition set in < CODE>AsNoPlanRetrievalSCH</CODE > class. <BR>
 * Make the result as a slit file for the ASRS schedule going out warehouse work list.  <BR>
 * This class processes it as follows. <BR>
 * <BR>
 * Data file making processing for slit(< CODE>startPrint() </ CODE > method)<BR>
 * <DIR>
 *	1.Retrieve the number of cases of work information (DNWORKINFO) on the condition set from < CODE>AsNoPlanRetrievalSCH</CODE > class. <BR>
 *	2.Make the slit file if the result is one or more. Return false and end in case of 0. <BR>
 *	3.Execute the printing job. <BR>
 *	4.Return true when the printing job is normal. <BR>
 *    Return false when the error occurs in the printing job. <BR>
 * <BR>
 * 	<Parameter> * Mandatory Input <BR><DIR>
 * 	  Batch No  <BR>
 *    At retrieval Start day <BR>
 *    At retrieval End day <BR>
 *    Station No. <BR></DIR>
 * <BR>
 * 	<Search condition> <BR><DIR>
 *    Batch No. <BR>
 *    At retrieval Start day <BR>
 *    At retrieval End day <BR>
 *    Station No. <BR>
 *    Work Flag(exception delivery) <BR>
 *    Another system identification key(ASRS) <BR>
 *    Excluding the deletion and completion <BR></DIR>
 * <BR>
 *  <retrieval order> <BR><DIR>
 *    1.Ascending order of station <BR>
 *    2.Ascending order of Consignor Code <BR>
 *    3.Ascending order of code at shipment destination <BR>
 *    4.Ascending order of Work  No. <BR>
 *    5.Ascending order of Item Code <BR>
 *    6.Ascending order of Case/Piece flag <BR>
 *    7.Ascending order of Expiry date <BR></DIR>
 * <BR>
 *	<Print data> <BR><DIR>
 *	  Print entry: DB item <BR>
 *	  Station: Station <BR>
 *	  Consignor : Consignor Code + Consignor Name <BR>
 *    Consignor : Consignor code + Consignor name <BR>
 *	  Work No. : Work No. <BR>
 *    Retrieval shelf : Work result location <BR>
 *	  Item Code	:Item Code <BR>
 *	  Item Name		:Item Name <BR>
 *    Flag			:Case/Piece flag <BR>
 *    Expiry date		:Expiry date <BR>
 *    Qty per case	:Qty per case <BR>
 *    Qty per bundle	:Qty per bundle <BR>
 *    Retrieval case qty : Actual work qty / Qty per case <BR>
 *    Retrieval piece qty : Actual work qty % Qty per case <BR>
 *    Case ITF : Case ITF <BR>
 *    Bundle ITF : Bundle ITF<BR></DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/11</TD><TD>K.Toda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2006/12/13 09:03:18 $
 * @author  $Author: suresh $
 */
public class AsNoPlanRetrievalWriter extends CSVWriter
{
	//#CM43459
	// Class fields --------------------------------------------------

	//#CM43460
	// Class variables -----------------------------------------------
	//#CM43461
	/**
	 * Batch No.
	 */
	protected String wBatchNumber;
	//#CM43462
	/**
	 * Station No.
	 */
	protected String wStationNo;
	//#CM43463
	/**
	 * Start day
	 */
	protected Date wFromDate;
	//#CM43464
	/**
	 * End day
	 */
	protected Date wToDate;

	//#CM43465
	// Class method --------------------------------------------------
	//#CM43466
	/**
	 * Return the version of this class. 
	 * 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.5 $,$Date: 2006/12/13 09:03:18 $");
	}

	//#CM43467
	// Constructors --------------------------------------------------
	//#CM43468
	/**
	 * Construct the AsNoPlanRetrievalWriter object. <BR>
	 * Set the connection. <BR>
	 * 
	 * @param conn Connection object with < CODE>Connection</CODE > database
	 */
	public AsNoPlanRetrievalWriter(Connection conn)
	{
		super(conn);
	}

	//#CM43469
	// Public methods ------------------------------------------------
	//#CM43470
	/**
	 * Batch No. is set.
	 * @param batchNumber Batch No.
	 */
	public void setBatchNumber(String batchNumber)
	{
		wBatchNumber = batchNumber;
	}
	//#CM43471
	/**
	 * Acquire Batch No.
	 * @return Batch No.
	 */
	public String getBatchNumber()
	{
		return wBatchNumber;
	}

	//#CM43472
	/**
	 * Acquire Station No.
	 * @return Station No.
	 */
	public String getStationNumber()
	{
		return wStationNo;
	}
	//#CM43473
	/**
	 * Station No is set.
	 * @param stationNo Station No.
	 */
	public void setStationNumber(String stationNo)
	{
		wStationNo = stationNo;
	}

	//#CM43474
	/**
	 * Acquire Start day.
	 * @return Start day
	 */
	public Date getFromDate()
	{
		return wFromDate;
	}
	//#CM43475
	/**
	 * Set the retrieval value in Start day.
	 * @param fromDate Start day
	 */
	public void setFromDate(Date fromDate)
	{
		wFromDate = fromDate;
	}

	//#CM43476
	/**
	 * Acquire End day.
	 * @return End day
	 */
	public Date getToDate()
	{
		return wToDate;
	}
	//#CM43477
	/**
	 * Set the retrieval value in End day.
	 * @param toDate End day
	 */
	public void setToDate(Date toDate)
	{
		wToDate = toDate;
	}

	//#CM43478
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

		//#CM43479
		// Retrieval execution
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
		CarryInformationSearchKey carryInfoSearchKey = new CarryInformationSearchKey();

		//#CM43480
		// Set the search condition. 
		setSearchKey(workInfoSearchKey, carryInfoSearchKey);

		int s1 = reportFinder.count(workInfoSearchKey, carryInfoSearchKey);
			
		return s1;
	}

	//#CM43481
	/**
	 * Make the CSV file for the ASRS schedule going out warehouse work list, and call the print execution class. <BR>
	 * Retrieve work information on the search condition input on the screen. <BR>
	 * Do not print when the retrieval result is less than one. <BR>
	 * @return True when the printing job ends normally and False when failing.
	 */
	public boolean startPrint()
	{
		AsWorkingInformationReportFinder reportFinder = new AsWorkingInformationReportFinder(wConn);
		
		try
		{
			//#CM43482
			// Retrieval execution
			WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
			CarryInformationSearchKey carryInfoSearchKey = new CarryInformationSearchKey();
			
			//#CM43483
			// Set the search condition. 
			setSearchKey(workInfoSearchKey, carryInfoSearchKey);

			//#CM43484
			// Set the order of the retrieval. 
			carryInfoSearchKey.setDestStationNumberOrder(1, true);
			workInfoSearchKey.setConsignorCodeOrder(2, true);
			workInfoSearchKey.setCustomerCodeOrder(3, true);
			carryInfoSearchKey.setWorkNumberOrder(4, true);
			workInfoSearchKey.setItemCodeOrder(5, true);
			workInfoSearchKey.setCasePieceFlagOrder(6, true);
			workInfoSearchKey.setUseByDateOrder(7, true);
		
			//#CM43485
			// Do not do the print processing when there are no data in WorkingInformation and the CarryInfo table. 
			if(reportFinder.search(workInfoSearchKey, carryInfoSearchKey) <= 0)
			{
				//#CM43486
				// 6003010 = There was no print data.
				wMessage = "6003010";
				return false;
			}			

			//#CM43487
			// The output file is made.
			if (!createPrintWriter(FNAME_ASRSRETRIEVAL))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_ASRSRETRIEVAL);
			
			//#CM43488
			// 	Make the data file 100 until the retrieval result is lost. 
			AsWorkingInformation workInfo[] = null ;
			String consignorCode = "";
			String customerCode = "";
			String[] name = {"", ""};
			while (reportFinder.isNext())
			{
				//#CM43489
				// It acquires it from the retrieval result to 100. 
				workInfo = (AsWorkingInformation[]) reportFinder.getEntities(100);
				for (int i = 0; i < workInfo.length; i++)
				{
					wStrText.append(re + "");
					//#CM43490
					// Station No.
					wStrText.append(ReportOperation.format(workInfo[i].getDestStationNumber()) + tb);
					//#CM43491
					// Consignor Code
					wStrText.append(ReportOperation.format(workInfo[i].getConsignorCode()) + tb);
					//#CM43492
					// Consignor Name
					//#CM43493
					// When Consignor Name changes, it the latest acquires it Consignor Name.
					if(!consignorCode.equals(workInfo[i].getConsignorCode()))
					{
						name = getName(workInfo[i].getConsignorCode(), workInfo[i].getCustomerCode());
					}
					wStrText.append(ReportOperation.format(name[0]) + tb);
					//#CM43494
					// Shipper code
					wStrText.append(ReportOperation.format(workInfo[i].getCustomerCode()) + tb);
					//#CM43495
					// Shipper name
					//#CM43496
					// When Shipper name changes, the latest Shipper name is acquires.
					if(!customerCode.equals(workInfo[i].getCustomerCode()))
					{
						name = getName(workInfo[i].getConsignorCode(), workInfo[i].getCustomerCode());
					}
					wStrText.append(ReportOperation.format(name[1]) + tb);
					//#CM43497
					// Work No.
					wStrText.append(ReportOperation.format(workInfo[i].getWorkNumber()) + tb);
					//#CM43498
					// Shelf No..
					String LocationNo = DisplayText.formatLocation(workInfo[i].getLocationNo());
					wStrText.append(ReportOperation.format(LocationNo) + tb);
					//#CM43499
					// Item Code
					wStrText.append(ReportOperation.format(workInfo[i].getItemCode()) + tb);
					//#CM43500
					// Item Name
					wStrText.append(ReportOperation.format(workInfo[i].getItemName1()) + tb);
					//#CM43501
					// Case/Piece flag
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(workInfo[i].getCasePieceFlag())) + tb);
					//#CM43502
					// Expiry date
					wStrText.append(ReportOperation.format(workInfo[i].getUseByDate()) + tb);
					//#CM43503
					// Qty per case
					wStrText.append(ReportOperation.format(Integer.toString(workInfo[i].getEnteringQty()))+ tb);
					//#CM43504
					// Qty per bundle
					wStrText.append(ReportOperation.format(Integer.toString(workInfo[i].getBundleEnteringQty()))+ tb);					
					//#CM43505
					// Results case
					wStrText.append(DisplayUtil.getCaseQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty(), workInfo[i].getCasePieceFlag()) + tb);
					//#CM43506
					// Results piece
					wStrText.append(DisplayUtil.getPieceQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty(), workInfo[i].getCasePieceFlag()) + tb);
					//#CM43507
					// Case ITF
					wStrText.append(ReportOperation.format(workInfo[i].getItf()) + tb);
					//#CM43508
					// Bundle ITF
					wStrText.append(ReportOperation.format(workInfo[i].getBundleItf()));
					//#CM43509
					// Entry
					wPWriter.print(wStrText);
					wStrText.setLength(0);
				}
			}
			//#CM43510
			// Close the stream.
			wPWriter.close();
			
			//#CM43511
			// UCXSingle is executed.
			if (!executeUCX(JOBID_ASRSRETRIEVAL)) 
			{
				//#CM43512
				//It failed in the print. Refer to the log.
				return false;
			}
			
			//#CM43513
			// The data file is moved to the backup folder. 
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException e)
		{
			//#CM43514
			// It failed in the print. Refer to the log. 
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM43515
				// Do the close processing of the open database cursor.
				reportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM43516
				// The data base error occurred. Refer to the log. 
				setMessage("6007002");
				return false;			
			}
		}
		return true;
	}
	
	//#CM43517
	// Package methods -----------------------------------------------

	//#CM43518
	// Protected methods ---------------------------------------------

	//#CM43519
	// Private methods -----------------------------------------------
	//#CM43520
	/** 
	 * Set the Where condition. 
	 * @param workInfoSearchKey Retrieval key to work information
	 * @param carryInfoSearchKey Retrieval key to transportation information
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private void setSearchKey(WorkingInformationSearchKey workInfoSearchKey, CarryInformationSearchKey carryInfoSearchKey) throws ReadWriteException
	{
		//#CM43521
		// Set the search condition. 
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
			carryInfoSearchKey.setDestStationNumber(wStationNo);
		}

		workInfoSearchKey.setJobType(WorkingInformation.JOB_TYPE_EX_RETRIEVAL);
		workInfoSearchKey.setSystemDiscKey(WorkingInformation.SYSTEM_DISC_KEY_ASRS);
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE,"<>");
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION,"<>");
	}

	//#CM43522
	/**
	 * Acquire latest Consignor Name and Shipper name. <BR>
	 * 
	 * @param consignorcode Consignor Code to be set
	 * @param customerCode Shipper code to be set
	 * @return Latest Consignor Name, Shipper name
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private String[] getName(String consignorCode, String customerCode) throws ReadWriteException
	{
		AsWorkingInformationReportFinder nameFinder;
		//#CM43523
		// Name
		String[] name = {"", ""};
			
		nameFinder = new AsWorkingInformationReportFinder(wConn);
		
		//#CM43524
		// Retrieval execution
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
		CarryInformationSearchKey carryInfoSearchKey = new CarryInformationSearchKey();

		//#CM43525
		// Set the search condition. 
		setSearchKey(workInfoSearchKey, carryInfoSearchKey);
		if (!StringUtil.isBlank(consignorCode))
		{
			workInfoSearchKey.setConsignorCode(consignorCode);
		}
		if (!StringUtil.isBlank(customerCode))
		{
			workInfoSearchKey.setCustomerCode(customerCode);
		}

		//#CM43526
		// Set the order of the retrieval. 
		workInfoSearchKey.setRegistDateOrder(1, false);

		//#CM43527
		// Retrieval beginning
		nameFinder.open();
		if(nameFinder.search(workInfoSearchKey, carryInfoSearchKey) > 0)
		{
			AsWorkingInformation[]workInfo = (AsWorkingInformation[]) nameFinder.getEntities(1);
			if (workInfo != null && workInfo.length != 0)
			{
				name[0] = workInfo[0].getConsignorName();
				name[1] = workInfo[0].getCustomerName1();
			}	
		}
		nameFinder.close();

		return name;
	}
}
//#CM43528
//end of class
