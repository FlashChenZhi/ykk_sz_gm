// $Id: AsItemRetrievalWriter.java,v 1.5 2006/12/13 09:03:19 suresh Exp $

//#CM43240
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

//#CM43241
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * <CODE>AsItemRetrievalWriter</CODE> class makes the data file for the slit of item ASRS picking list. Execute the print.<BR>
 * Make the result as a slit file for item ASRS picking list.<BR>
 * This class processes it as follows.<BR>
 * <BR>
 * Data file making processing for slit(< CODE>startPrint() </ CODE > method)<BR>
 * <DIR>
 *	1.Retrieve the number of cases of work information (DNWORKINFO) on the condition set from the print issue class.<BR>
 *	2.Make the slit file if the result is one or more. Return false and end in case of 0.<BR>
 *	3.Execute the printing job.<BR>
 *	4.Return true when the printing job is normal.<BR>
 *    Return false when the error occurs in the printing job.<BR>
 * <BR>
 * 	<Parameter> * Mandatory input <BR><DIR>
 * 	  Batch No  <BR>
 *    Retrieval beginning date <BR>
 *    Retrieval end date <BR>
 *    Station No. <BR></DIR>
 * <BR>
 * 	<search condition> <BR><DIR>
 *    Batch No <BR>
 *    Retrieval beginning date <BR>
 *    Retrieval end date <BR>
 *    Station No. <BR>
 *    Work division(delivery) <BR>
 *    Another system identification key(ASRS) <BR>
 *    Excluding the deletion and completion <BR>
 *    Order No. is NULL. <BR></DIR>
 * <BR>
 *  <retrieval by order> <BR><DIR>
 *    1.Ascending order of station <BR>
 *    2.Ascending order of shipper code <BR>
 *    3.Ascending order of work No. <BR>
 *    4.Ascending order of commodity code <BR>
 *    5.Ascending order of case peace division <BR>
 *    6.Ascending order at Expiry date <BR></DIR>
 * <BR>
 *	<print data> <BR><DIR>
 *	  Print entry: DB item <BR>
 *	  Station: Station <BR>
 *	  Consignor: Consignor code + consignor name <BR>
 *	  Work No.: Work No. <BR>
 *    Delivery shelf: Work result location <BR>
 *	  Commodity code: Commodity code <BR>
 *	  Brand name: Brand name <BR>
 *    Division: Case piece division <BR>
 *	  Due out day: Due out day <BR>
 *    Expiry date: Expiry date <BR>
 *    Qty per case : Qty per case <BR>
 *    Qty per bundle : Qty per bundle <BR>
 *    Retrieval case qty :Actual work qty / Qty per case <BR>
 *    Retrieval piece qty :Actual work qty % Qty per case <BR>
 *    Case ITF : Case ITF <BR>
 *    Bundle ITF : Bundle ITF <BR></DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/02/03</TD><TD>K.Toda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2006/12/13 09:03:19 $
 * @author  $Author: suresh $
 */
public class AsItemRetrievalWriter extends CSVWriter
{
	//#CM43242
	// Class fields --------------------------------------------------

	//#CM43243
	// Class variables -----------------------------------------------
	//#CM43244
	/**
	 * Batch No.
	 */
	protected String wBatchNumber;
	//#CM43245
	/**
	 * Station No.
	 */
	protected String wStationNo;
	//#CM43246
	/**
	 * Start day
	 */
	protected Date wFromDate;
	//#CM43247
	/**
	 * End day
	 */
	protected Date wToDate;

	//#CM43248
	// Class method --------------------------------------------------
	//#CM43249
	/**
	 * Return the version of this class.
	 * 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.5 $,$Date: 2006/12/13 09:03:19 $");
	}

	//#CM43250
	// Constructors --------------------------------------------------
	//#CM43251
	/**
	 * AsItemRetrievalWriter Construct the object.<BR>
	 * Set the connection. <BR>
	 * 
	 * @param conn <CODE>Connection</CODE> Connection object with data base
	 */
	public AsItemRetrievalWriter(Connection conn)
	{
		super(conn);
	}

	//#CM43252
	// Public methods ------------------------------------------------
	//#CM43253
	/**
	 * Acquire batch No.
	 * @return Batch No.
	 */
	public String getBatchNumber()
	{
		return wBatchNumber;
	}

	//#CM43254
	/**
	 * Batch No. is set.
	 * @param str Batch No.
	 */
	public void setBatchNumber(String str)
	{
		wBatchNumber = str;
	}

	//#CM43255
	/**
	 * Acquire Station No.
	 * @return Station No.
	 */
	public String getStationNumber()
	{
		return wStationNo;
	}

	//#CM43256
	/**
	 * Set the Station No.
	 * @param str Station No.
	 */
	public void setStationNumber(String str)
	{
		wStationNo = str;
	}

	//#CM43257
	/**
	 * Acquire the Start day.
	 * @return Start day
	 */
	public Date getFromDate()
	{
		return wFromDate;
	}

	//#CM43258
	/**
	 * Set the retrieval value on the Start day.
	 * @param fromDate Start day
	 */
	public void setFromDate(Date fromDate)
	{
		wFromDate = fromDate;
	}

	//#CM43259
	/**
	 * Acquire the end day.
	 * @return End day
	 */
	public Date getToDate()
	{
		return wToDate;
	}

	//#CM43260
	/**
	 * Set the retrieval value on the end day.
	 * @param toDate End day
	 */
	public void setToDate(Date toDate)
	{
		wToDate = toDate;
	}

	//#CM43261
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

		//#CM43262
		// Retrieval execution
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
		CarryInformationSearchKey carryInfoSearchKey = new CarryInformationSearchKey();

		//#CM43263
		// Set the search condition.
		setSearchKey(workInfoSearchKey, carryInfoSearchKey);

		return reportFinder.count(workInfoSearchKey, carryInfoSearchKey);
	}

	//#CM43264
	/**
	 * Make the CSV file for item ASRS picking list, and execute the print. <BR>
	 * Make the slit file if the result is one or more. Return false and end in case of 0. <BR>
	 * Execute the printing job. <BR>
	 * @return True when the printing job ends normally and False when failing.
	 */
	public boolean startPrint()
	{
		AsWorkingInformationReportFinder reportFinder = new AsWorkingInformationReportFinder(wConn);
		
		try
		{
			//#CM43265
			// Retrieval execution
			WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
			CarryInformationSearchKey carryInfoSearchKey = new CarryInformationSearchKey();
			
			//#CM43266
			// Set the search condition.
			setSearchKey(workInfoSearchKey, carryInfoSearchKey);

			//#CM43267
			// Set the order of the retrieval.
			carryInfoSearchKey.setDestStationNumberOrder(1, true);
			workInfoSearchKey.setConsignorCodeOrder(2, true);
			carryInfoSearchKey.setWorkNumberOrder(3, true);
			workInfoSearchKey.setItemCodeOrder(4, true);
			workInfoSearchKey.setCasePieceFlagOrder(5, true);
			workInfoSearchKey.setUseByDateOrder(6, true);
		
			//#CM43268
			// Do not do the print processing when there are no data in WorkingInformation and the CarryInfo table.
			if(reportFinder.search(workInfoSearchKey, carryInfoSearchKey) <= 0)
			{
				//#CM43269
				// 6003010 = There was no print data.
				wMessage = "6003010";
				return false;
			}			

			//#CM43270
			// The output file is made.
			if (!createPrintWriter(FNAME_ASRSITEMRETRIEVAL))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_ASRSITEMRETRIEVAL);
			
			//#CM43271
			// 	Make the data file 100 until the retrieval result is lost.
			AsWorkingInformation workInfo[] = null ;
			String consignorCode = "";
			String[] name = {""};
			while (reportFinder.isNext())
			{
				//#CM43272
				// Acquired from the retrieval result to 100.
				workInfo = (AsWorkingInformation[]) reportFinder.getEntities(100);
				for (int i = 0; i < workInfo.length; i++)
				{
					wStrText.append(re + "");
					//#CM43273
					// Station No.
					wStrText.append(ReportOperation.format(workInfo[i].getDestStationNumber()) + tb);
					//#CM43274
					// Consignor Code
					wStrText.append(ReportOperation.format(workInfo[i].getConsignorCode()) + tb);
					//#CM43275
					// Consignor Name
					//#CM43276
					// When Consignor Name changes, it is the latest acquired Consignor Name.
					if(!consignorCode.equals(workInfo[i].getConsignorCode()))
					{
						name = getName(workInfo[i].getConsignorCode());
					}
					wStrText.append(ReportOperation.format(name[0]) + tb);
					//#CM43277
					// Work No.
					wStrText.append(ReportOperation.format(workInfo[i].getWorkNumber()) + tb);
					//#CM43278
					// Shelf No.
					String LocationNo = DisplayText.formatLocation(workInfo[i].getLocationNo());
					wStrText.append(ReportOperation.format(LocationNo) + tb);
					//#CM43279
					// Item Code
					wStrText.append(ReportOperation.format(workInfo[i].getItemCode()) + tb);
					//#CM43280
					// Item Name
					wStrText.append(ReportOperation.format(workInfo[i].getItemName1()) + tb);
					//#CM43281
					// Case/Piece flag
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(workInfo[i].getCasePieceFlag())) + tb);
					//#CM43282
					// Retrieval Plan Date
					wStrText.append(ReportOperation.format(workInfo[i].getPlanDate()) + tb);
					//#CM43283
					// Expiry date
					wStrText.append(ReportOperation.format(workInfo[i].getUseByDate()) + tb);
					//#CM43284
					// Qty per case
					wStrText.append(ReportOperation.format(Integer.toString(workInfo[i].getEnteringQty()))+ tb);
					//#CM43285
					// Qty per bundle
					wStrText.append(ReportOperation.format(Integer.toString(workInfo[i].getBundleEnteringQty()))+ tb);					
					//#CM43286
					// Results case
					wStrText.append(DisplayUtil.getCaseQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty(), workInfo[i].getCasePieceFlag()) + tb);
					//#CM43287
					// Results piece
					wStrText.append(DisplayUtil.getPieceQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty(), workInfo[i].getCasePieceFlag()) + tb);
					//#CM43288
					// Case ITF
					wStrText.append(ReportOperation.format(workInfo[i].getItf()) + tb);
					//#CM43289
					// Bundle ITF
					wStrText.append(ReportOperation.format(workInfo[i].getBundleItf()));
					//#CM43290
					// Entry
					wPWriter.print(wStrText);
					wStrText.setLength(0);
				}
			}
			//#CM43291
			// Stream close
			wPWriter.close();
			
			//#CM43292
			// UCXSingle is executed.
			if (!executeUCX(JOBID_ASRSITEMRETRIEVAL)) 
			{
				//#CM43293
				//It failed in the print. Refer to the log.
				return false;
			}
			
			//#CM43294
			// The data file is moved to the backup folder.
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException e)
		{
			//#CM43295
			// It failed in the print. Refer to the log.
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM43296
				// Do the close processing of the opening database cursor.
				reportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM43297
				// The database error occurred. Refer to the log.
				setMessage("6007002");
				return false;			
			}
		}
		return true;
	}
	
	//#CM43298
	// Package methods -----------------------------------------------

	//#CM43299
	// Protected methods ---------------------------------------------

	//#CM43300
	// Private methods -----------------------------------------------
	//#CM43301
	/** 
	 * Set the Where condition.
	 * @param workInfoSearchKey Retrieval key to work information
	 * @param carryInfoSearchKey Retrieval key to transportation information
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private void setSearchKey(WorkingInformationSearchKey workInfoSearchKey, CarryInformationSearchKey carryInfoSearchKey) throws ReadWriteException
	{
		//#CM43302
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
		workInfoSearchKey.setOrderNo("");				
	}

	//#CM43303
	/**
	 * Acquire latest Consignor Name.
	 * @param consignorcode Consignor Code
	 * @return Latest Consignor Name
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private String[] getName(String consignorCode) throws ReadWriteException
	{
		AsWorkingInformationReportFinder nameFinder;
		//#CM43304
		// Name
		String[] name = {""};
			
		nameFinder = new AsWorkingInformationReportFinder(wConn);
		
		//#CM43305
		// Retrieval execution
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
		CarryInformationSearchKey carryInfoSearchKey = new CarryInformationSearchKey();

		//#CM43306
		// Set the search condition.
		setSearchKey(workInfoSearchKey, carryInfoSearchKey);
		if (!StringUtil.isBlank(consignorCode))
		{
			workInfoSearchKey.setConsignorCode(consignorCode);
		}

		//#CM43307
		// Set the order of the retrieval.
		workInfoSearchKey.setRegistDateOrder(1, false);

		//#CM43308
		// Retrieval beginning
		nameFinder.open();
		if(nameFinder.search(workInfoSearchKey, carryInfoSearchKey) > 0)
		{
			AsWorkingInformation[]workInfo = (AsWorkingInformation[]) nameFinder.getEntities(1);
			if (workInfo != null && workInfo.length != 0)
			{
				name[0] = workInfo[0].getConsignorName();
			}	
		}
		nameFinder.close();

		return name;
	}
}
//#CM43309
//end of class
