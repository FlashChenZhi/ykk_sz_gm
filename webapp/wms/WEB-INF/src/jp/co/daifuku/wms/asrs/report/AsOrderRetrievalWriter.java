// $Id: AsOrderRetrievalWriter.java,v 1.5 2006/12/13 09:03:17 suresh Exp $

//#CM43602
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

//#CM43603
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * < CODE>AsOrderRetrievalWriter</CODE > class makes the data file for the slit of the ASRS order picking list.   Execute the print. <BR>
 * Make the result as a slit file for the ASRS order picking list. <BR>
 * This class processes it as follows. <BR>
 * <BR>
 * Data file making processing for slit(< CODE>startPrint() </ CODE > method)<BR>
 * <DIR>
 *	1.Retrieve the number of cases of work information (DNWORKINFO) on the condition set from the print issue class.  <BR>
 *	2.Make the slit file if the result is one or more. Return false and end in case of 0.  <BR>
 *	3.Execute the printing job. <BR>
 *	4.Return true when the printing job is normal.  <BR>
 *    Return false when the error occurs in the printing job.  <BR>
 * <BR>
 * 	<Parameter>*Mandatory Input <BR><DIR>
 * 	  Batch No.  <BR>
 *    Retrieval Start day & time <BR>
 *    Retrieval End day & time <BR>
 *    Station No. <BR></DIR>
 * <BR>
 * 	<Search condition> <BR><DIR>
 *    Batch No. <BR>
 *    Retrieval Start day & time <BR>
 *    Retrieval End day & time <BR>
 *    Station No. <BR>
 *    Work Flag(delivery) <BR>
 *    Another system identification key(ASRS) <BR>
 *    Excluding the deletion and completion <BR>
 *    Order No. : excluding NULL. <BR></DIR>
 * <BR>
 *  <retrieval order> <BR><DIR>
 *    1.Ascending order of station <BR>
 *    2.Ascending order of Consignor Code <BR>
 *    3.Ascending order of Shipper code <BR>
 *    4.Ascending order of order No. <BR>
 *    5.Ascending order of Work  No. <BR>
 *    6.Ascending order of Item Code <BR>
 *    7.Ascending order of Case/Piece flag <BR>
 *    8.Ascending order of Expiry date <BR></DIR>
 * <BR>
 *	<Print data> <BR><DIR>
 *	  Print entry: DB item<BR>
 *	  Station :Station <BR>
 *	  Consignor :Consignor Code + Consignor Name <BR>
 *    Shipper :Shipper code+Shipper name <BR>
 *	  Work No. :Work No. <BR>
 *    Retrieval Shelf :Work result Location <BR>
 *	  Item Code :Item Code <BR>
 *	  Item Name :Item Name <BR>
 *    Flag :Case/Piece flag <BR>
 *    Expiry date :Expiry date <BR>
 *    Qty per case :Qty per case <BR>
 *    Qty per bundle :Qty per bundle <BR>
 *    Retrieval Case qty :Actual work qty / Qty per case <BR>
 *    Retrieval Piece qty :Actual work qty % Qty per case <BR>
 *    Case ITF	:Case ITF <BR>
 *    Bundle ITF	:Bundle ITF <BR></DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/02/03</TD><TD>K.Toda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2006/12/13 09:03:17 $
 * @author  $Author: suresh $
 */
public class AsOrderRetrievalWriter extends CSVWriter
{
	//#CM43604
	// Class fields --------------------------------------------------

	//#CM43605
	// Class variables -----------------------------------------------
	//#CM43606
	/**
	 * Batch No.
	 */
	protected String wBatchNumber;
	//#CM43607
	/**
	 * Station No.
	 */
	protected String wStationNo;
	//#CM43608
	/**
	 * Start day
	 */
	protected Date wFromDate;
	//#CM43609
	/**
	 * End day
	 */
	protected Date wToDate;

	//#CM43610
	//	 Class method --------------------------------------------------
	//#CM43611
	/**
	 * Return the version of this class. 
	 * 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.5 $,$Date: 2006/12/13 09:03:17 $");
	}

	//#CM43612
	// Constructors --------------------------------------------------
	//#CM43613
	/**
	 * Construct the AsOrderRetrievalWriter object. <BR>
	 * Set the connection. <BR>
	 * 
	 * @param conn Connection object with <CODE>Connection</CODE> database
	 */
	public AsOrderRetrievalWriter(Connection conn)
	{
		super(conn);
	}

	//#CM43614
	// Public methods ------------------------------------------------
	//#CM43615
	/**
	 * Acquire Batch No.
	 * @return Batch No.
	 */
	public String getBatchNumber()
	{
		return wBatchNumber;
	}

	//#CM43616
	/**
	 * Batch No. is set.
	 * @param batchNumber Batch No.
	 */
	public void setBatchNumber(String batchNumber)
	{
		wBatchNumber = batchNumber;
	}

	//#CM43617
	/**
	 * Acquire Station No.
	 * @return Station No.
	 */
	public String getStationNumber()
	{
		return wStationNo;
	}

	//#CM43618
	/**
	 * Station No is set. 
	 * @param stationNo Station No.
	 */
	public void setStationNumber(String stationNo)
	{
		wStationNo = stationNo;
	}

	//#CM43619
	/**
	 * Acquire Start day. 
	 * @return Start day
	 */
	public Date getFromDate()
	{
		return wFromDate;
	}

	//#CM43620
	/**
	 * Set the retrieval value in Start day. 
	 * @param fromDate Start day
	 */
	public void setFromDate(Date fromDate)
	{
		wFromDate = fromDate;
	}

	//#CM43621
	/**
	 * Acquire End day. 
	 * @return End day
	 */
	public Date getToDate()
	{
		return wToDate;
	}

	//#CM43622
	/**
	 * Set the retrieval value in End day. 
	 * @param toDate End day
	 */
	public void setToDate(Date toDate)
	{
		wToDate = toDate;
	}

	//#CM43623
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

		//#CM43624
		// Retrieval execution
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
		CarryInformationSearchKey carryInfoSearchKey = new CarryInformationSearchKey();

		//#CM43625
		// Set the search condition. 
		setSearchKey(workInfoSearchKey, carryInfoSearchKey);

		return reportFinder.count(workInfoSearchKey, carryInfoSearchKey);
	}
	
	//#CM43626
	/**
	 * Make the CSV file for the ASRS order picking list, and call the print execution class. <BR>
	 * Retrieve work information on the search condition input on the screen. <BR>
	 * Do not print when the retrieval result is less than one. <BR>
	 * @return True when the printing job ends normally and False when failing.
	 */
	public boolean startPrint()
	{
		AsWorkingInformationReportFinder reportFinder = new AsWorkingInformationReportFinder(wConn);
		
		try
		{
			//#CM43627
			// Retrieval execution
			WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
			CarryInformationSearchKey carryInfoSearchKey = new CarryInformationSearchKey();
			
			//#CM43628
			// Set the search condition. 
			setSearchKey(workInfoSearchKey, carryInfoSearchKey);

			//#CM43629
			// Set the order of the retrieval. 
			carryInfoSearchKey.setDestStationNumberOrder(1, true);
			workInfoSearchKey.setConsignorCodeOrder(2, true);
			workInfoSearchKey.setCustomerCodeOrder(3, true);
			workInfoSearchKey.setOrderNoOrder(4, true);
			carryInfoSearchKey.setWorkNumberOrder(5, true);
			workInfoSearchKey.setItemCodeOrder(6, true);
			workInfoSearchKey.setCasePieceFlagOrder(7, true);
			workInfoSearchKey.setUseByDateOrder(8, true);
		
			//#CM43630
			// Do not do the print processing when there are no data in WorkingInformation and the CarryInfo table. 
			if(reportFinder.search(workInfoSearchKey, carryInfoSearchKey) <= 0)
			{
				//#CM43631
				// 6003010 = There was no print data. 
				wMessage = "6003010";
				return false;
			}			

			//#CM43632
			// The output file is made. 
			if (!createPrintWriter(FNAME_ASRSORDERRETRIEVAL))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_ASRSORDERRETRIEVAL);
			
			//#CM43633
			// Make the data file 100 until the retrieval result is lost.
			AsWorkingInformation workInfo[] = null ;
			String consignorCode = "";
			String customerCode = "";
			String[] name = {"", ""};
			while (reportFinder.isNext())
			{
				//#CM43634
				// It acquires it from the retrieval result to 100. 
				workInfo = (AsWorkingInformation[]) reportFinder.getEntities(100);
				for (int i = 0; i < workInfo.length; i++)
				{
					wStrText.append(re + "");
					//#CM43635
					// Station No.
					wStrText.append(ReportOperation.format(workInfo[i].getDestStationNumber()) + tb);
					//#CM43636
					// Consignor Code
					wStrText.append(ReportOperation.format(workInfo[i].getConsignorCode()) + tb);
					//#CM43637
					// Consignor Name
					//#CM43638
					// When Consignor Name changes, acquires Latest Consignor Name.
					if(!consignorCode.equals(workInfo[i].getConsignorCode()))
					{
						name = getName(workInfo[i].getConsignorCode(), workInfo[i].getCustomerCode(), workInfo[i].getOrderNo());
					}
					wStrText.append(ReportOperation.format(name[0]) + tb);
					//#CM43639
					// Shipper code
					wStrText.append(ReportOperation.format(workInfo[i].getCustomerCode()) + tb);
					//#CM43640
					// Shipper name
					//#CM43641
					// When Shipper name changes, acquires Latest Shipper name.
					if(!customerCode.equals(workInfo[i].getCustomerCode()))
					{
						name = getName(workInfo[i].getConsignorCode(), workInfo[i].getCustomerCode(), workInfo[i].getOrderNo());
					}
					wStrText.append(ReportOperation.format(name[1]) + tb);
					//#CM43642
					// Order No.
					wStrText.append(ReportOperation.format(workInfo[i].getOrderNo()) + tb);						
					//#CM43643
					// Work No.
					wStrText.append(ReportOperation.format(workInfo[i].getWorkNumber()) + tb);
					//#CM43644
					// Shelf No..
					String LocationNo = DisplayText.formatLocation(workInfo[i].getLocationNo());
					wStrText.append(ReportOperation.format(LocationNo) + tb);
					//#CM43645
					// Item Code
					wStrText.append(ReportOperation.format(workInfo[i].getItemCode()) + tb);
					//#CM43646
					// Item Name
					wStrText.append(ReportOperation.format(workInfo[i].getItemName1()) + tb);
					//#CM43647
					// Case/Piece flag
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(workInfo[i].getCasePieceFlag())) + tb);
					//#CM43648
					// Retrieval Plan Date
					wStrText.append(ReportOperation.format(workInfo[i].getPlanDate()) + tb);
					//#CM43649
					// Expiry date
					wStrText.append(ReportOperation.format(workInfo[i].getUseByDate()) + tb);
					//#CM43650
					// Qty per case
					wStrText.append(ReportOperation.format(Integer.toString(workInfo[i].getEnteringQty()))+ tb);
					//#CM43651
					// Qty per bundle
					wStrText.append(ReportOperation.format(Integer.toString(workInfo[i].getBundleEnteringQty()))+ tb);					
					//#CM43652
					// Results case
					wStrText.append(DisplayUtil.getCaseQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty(), workInfo[i].getCasePieceFlag()) + tb);
					//#CM43653
					// Results piece
					wStrText.append(DisplayUtil.getPieceQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty(), workInfo[i].getCasePieceFlag()) + tb);
					//#CM43654
					// Case ITF
					wStrText.append(ReportOperation.format(workInfo[i].getItf()) + tb);
					//#CM43655
					// Bundle ITF
					wStrText.append(ReportOperation.format(workInfo[i].getBundleItf()));
					//#CM43656
					// Entry
					wPWriter.print(wStrText);
					wStrText.setLength(0);
				}
			}
			//#CM43657
			// Close the stream.
			wPWriter.close();
			
			//#CM43658
			// UCXSingle is executed. 
			if (!executeUCX(JOBID_ASRSORDERRETRIEVAL)) 
			{
				//#CM43659
				//It failed in the print. Refer to the log.  
				return false;
			}
			
			//#CM43660
			// The data file is moved to the backup folder. 
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException e)
		{
			//#CM43661
			// It failed in the print. Refer to the log. 
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM43662
				// Do the close processing of the opening data base cursor. 
				reportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM43663
				// The data base error occurred. Refer to the log. 
				setMessage("6007002");
				return false;			
			}
		}
		return true;
	}
	
	//#CM43664
	// Package methods -----------------------------------------------

	//#CM43665
	// Protected methods ---------------------------------------------

	//#CM43666
	// Private methods -----------------------------------------------
	//#CM43667
	/** 
	 * Set the Where condition. 
	 * @param workInfoSearchKey Retrieval key to work information
	 * @param carryInfoSearchKey Retrieval key to transportation information
	 * @throws ReadWriteException It is notified when the error occurs when connecting it with the data base. 
	 */
	private void setSearchKey(WorkingInformationSearchKey workInfoSearchKey, CarryInformationSearchKey carryInfoSearchKey) throws ReadWriteException
	{
		//#CM43668
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
	
		workInfoSearchKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		workInfoSearchKey.setSystemDiscKey(WorkingInformation.SYSTEM_DISC_KEY_ASRS);
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE,"<>");
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION,"<>");
		workInfoSearchKey.setOrderNo("","IS NOT NULL");				
	}

	//#CM43669
	/**
	 * Acquire Latest Consignor Name and Shipper name. <BR>
	 * 
	 * @param consignorcode Consignor Code to be set
	 * @param customerCode  Shipper Code to be set
	 * @param orderNo       Order No.
	 * @return Latest Consignor Name, Shipper name
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private String[] getName(String consignorCode, String customerCode, String orderNo) throws ReadWriteException
	{
		AsWorkingInformationReportFinder nameFinder;
		//#CM43670
		// Name
		String[] name = {"", ""};
			
		nameFinder = new AsWorkingInformationReportFinder(wConn);
		
		//#CM43671
		// Retrieval execution
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
		CarryInformationSearchKey carryInfoSearchKey = new CarryInformationSearchKey();

		//#CM43672
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
		if (!StringUtil.isBlank(orderNo))
		{
			workInfoSearchKey.setOrderNo(orderNo);
		}

		//#CM43673
		// Set the order of the retrieval. 
		workInfoSearchKey.setRegistDateOrder(1, false);

		//#CM43674
		// Retrieval start
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
//#CM43675
//end of class
