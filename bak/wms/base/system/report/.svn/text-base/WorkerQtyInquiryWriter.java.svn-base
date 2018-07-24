// $Id: WorkerQtyInquiryWriter.java,v 1.5 2006/12/13 08:53:36 suresh Exp $

//#CM695402
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.report;

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultHandler;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.base.system.dbhandler.SystemWorkerResultReportFinder;
import jp.co.daifuku.wms.base.system.dbhandler.SystemWorkerResultSearchKey;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;



//#CM695403
/**
 * Designer : suresh kayamboo<BR>
 * Maker 	: suresh kayamboo<BR>
 *
 * <BR>
 * Allow this method to generate a Ticket data file for Result Inquiry by Worker and invoke the class for executing printing.<BR>
 * Search for the worker result info using the contents set in the schedule class and generate a ticket data file.<BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/27</TD><TD>suresh kayamboo</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2006/12/13 08:53:36 $
 * @author  $Author: suresh $
 */
public class WorkerQtyInquiryWriter extends CSVWriter
{
	//#CM695404
	// Class fields --------------------------------------------------

	//#CM695405
	// Class variables -----------------------------------------------
	//#CM695406
	/**
	 * Work type
	 */
	private String wWorkType;

	//#CM695407
	/**
	 * Start work date
	 */
	private String wStartWorkDate;

	//#CM695408
	/**
	 * End Work Date
	 */
	private String wEndWorkDate;

	//#CM695409
	/**
	 * Worker Code
	 */
	private String wWorkerCode;

	//#CM695410
	/**
	 * Unit of View
	 */
	private String wDispType;

	//#CM695411
	/**
	 * Aggregation Conditions
	 */
	private String wDispCond = "";

	//#CM695412
	// Class method --------------------------------------------------
	//#CM695413
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.5 $,$Date: 2006/12/13 08:53:36 $");
	}

	//#CM695414
	// Constructors --------------------------------------------------
	//#CM695415
	/**
	 * Constructor
	 */
	public WorkerQtyInquiryWriter(Connection conn)
	{
		super(conn);
	}

	//#CM695416
	// Public methods ------------------------------------------------
	//#CM695417
	/**
	 * Set the search value for work type.
	 * @param pWorkType Work type to be set
	 */
	public void setWorkType(String pWorkType)
	{
		wWorkType = pWorkType;
	}

	//#CM695418
	/**
	 * Obtain the work type.
	 * @return Work type
	 */
	public String getWorkType()
	{
		return wWorkType;
	}

	//#CM695419
	/**
	 * Set the search value for Start Work Date.
	 * @param pStartWorkDate Start work date to be set
	 */
	public void setStartWorkDate(String pStartWorkDate)
	{
		wStartWorkDate = pStartWorkDate;
	}

	//#CM695420
	/**
	 * Obtain the start work date.
	 * @return Start work date
	 */
	public String getStartWorkDate()
	{
		return wStartWorkDate;
	}

	//#CM695421
	/**
	 * Set the search value for End Work Date.
	 * @param pEndWorkDate End work date to be set
	 */
	public void setEndWorkDate(String pEndWorkDate)
	{
		wEndWorkDate = pEndWorkDate;
	}

	//#CM695422
	/**
	 * Obtain the End work date.
	 * @return End Work Date
	 */
	public String getEndWorkDate()
	{
		return wEndWorkDate;
	}

	//#CM695423
	/**
	 * Set the search value for Worker Code.
	 * @param pWorkerCode Worker code to be set
	 */
	public void setWorkerCode(String pWorkerCode)
	{
		wWorkerCode = pWorkerCode;
	}

	//#CM695424
	/**
	 * Obtain the Worker code.
	 * @return Worker Code
	 */
	public String getWorkerCode()
	{
		return wWorkerCode;
	}

	//#CM695425
	/**
	 * Set the search value for Unit of View.
	 * @param pDispType Unit of View to be set
	 */
	public void setDispType(String pDispType)
	{
		wDispType = pDispType;
	}

	//#CM695426
	/**
	 * Obtain the unit of View.
	 * @return Unit of View
	 */
	public String getDispType()
	{
		return wDispType;
	}

	//#CM695427
	/**
	 * Generate a CSV file for Result Inquiry by Worker and invoke the class for executing printing.<BR>
	 * Search for the Result info by worker
	 * Disable to print if the search result count is less than 1 (one).<BR>
	 * Return true if succeeded in printing. Return false if failed.<BR>
	 *
	 * @return boolean Result whether the printing succeeded or not.
	 */
	public boolean startPrint()
	{
		SystemWorkerResultReportFinder reportFinder = new SystemWorkerResultReportFinder(wConn);

		try
		{
			SystemWorkerResultSearchKey serchKey = new SystemWorkerResultSearchKey();

			//#CM695428
			// Set the conditions for the Search Key.
			setWorkerResultSearchKey(serchKey);

			//#CM695429
			// If no data exists, disable to execute print process.
			if (reportFinder.search(serchKey, wDispType) <= 0)
			{
				//#CM695430
				// 6003010=No print data found.
				wMessage = "6003010";
				return false;
			}

			//#CM695431
			// Generate a file for output.
			if (!createPrintWriter(FNAME_WORKER_QTY))
			{
				return false;
			}

            //append header
            wStrText.append(HEADER_WORKER_QTY);

			//#CM695432
			// Generate contents of data files until there is no longer the search result.
			SystemParameter[] sysParam = null;

			while (reportFinder.isNext())
			{
				//#CM695433
				// Output every 100 search results.
				sysParam = (SystemParameter[]) reportFinder.getParamsEntities(100, wDispCond);

				//#CM695434
				// Generate the contents to be output to the file.
				for (int i = 0; i < sysParam.length; i++)
				{
					//#CM695435
					// Terminal Name
					String rftNo ="";
					wStrText.append(re + "");
					//#CM695436
					// Start work date
					wStrText.append(ReportOperation.format(wStartWorkDate) + tb);
					//#CM695437
					// End Work Date
					wStrText.append(ReportOperation.format(wEndWorkDate) + tb);
					//#CM695438
					// Work date
					wStrText.append(ReportOperation.format(sysParam[i].getWorkDate()) + tb);
					//#CM695439
					// Worker Code
					wStrText.append(ReportOperation.format(sysParam[i].getWorkerCode()) + tb);
					//#CM695440
					// Worker Name
					wStrText.append(ReportOperation.format(sysParam[i].getWorkerName()) + tb);
					//#CM695441
					// Work type
					wStrText.append(ReportOperation.format(sysParam[i].getSelectWorkDetail()) + tb);
					//#CM695442
					// Start Time
					wStrText.append(ReportOperation.format(sysParam[i].getWorkStartTime()) + tb);
					//#CM695443
					// End Time
					wStrText.append(ReportOperation.format(sysParam[i].getWorkEndTime()) + tb);
					//#CM695444
					// Work Time
					wStrText.append(ReportOperation.format(sysParam[i].getWorkTime()) + tb);
					//#CM695445
					// Work Quantity
					wStrText.append(sysParam[i].getWorkQty() + tb);
					//#CM695446
					// Work Count
					wStrText.append(sysParam[i].getWorkCnt() + tb);
					//#CM695447
					// Work Quantity/h
					wStrText.append(sysParam[i].getWorkQtyPerHour() + tb);
					//#CM695448
					// Work Count/h
					wStrText.append(sysParam[i].getWorkCntPerHour() + tb);
					//#CM695449
					// Terminal Name
					if(StringUtil.isBlank(sysParam[i].getTerminalNumber()))
					{
						rftNo = DisplayText.getText("LBL-W0078");
					}
					else
					{
						rftNo = sysParam[i].getTerminalNumber();
					}
					wStrText.append(ReportOperation.format(rftNo) + tb);
					//#CM695450
					// Aggregation Conditions
					wStrText.append(DisplayUtil.getSelectAggregateCondition(wDispType));

					//#CM695451
					// Output the data to the file.
					wPWriter.print(wStrText);
					wStrText.setLength(0);
				}
			}

			//#CM695452
			// Close the stream.
			wPWriter.close();

			//#CM695453
			// Execute the UCXSingle (Execute printing).
			if (!ReportOperation.executeUCX(JOBID_WORKER_QTY, wFileName))
			{
				//#CM695454
				// Failed to print. See log.
				setMessage("6007034");
				return false;
			}

			//#CM695455
			// If succeeded in printing, move the data file to the backup folder.
			ReportOperation.createBackupFile(wFileName);
		}
		catch (ReadWriteException e)
		{
			//#CM695456
			// 6007034=Printing failed. See log.
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM695457
				// Execute processes for closing the cursor of the opened database.
				reportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM695458
				// Database error occurred. See log.
				setMessage("6007002");
				return false;
			}
		}

		return true;
	}

	//#CM695459
	/**
	 * Obtain the count of the print data.<BR>
	 * Use this search result to determine whether to execute the process for printing.<BR>
	 * Use this method via the schedule class for processing the screen.<BR>
	 *
	 * @return Count of printed data.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int count() throws ReadWriteException
	{
		WorkerResultHandler handler = new WorkerResultHandler(wConn);
		SystemWorkerResultSearchKey searchKey = new SystemWorkerResultSearchKey();
		//#CM695460
		// Set the search conditions and obtain the count.
		setWorkerResultSearchKey(searchKey);
		return handler.count(searchKey);
	}

	//#CM695461
	// Package methods -----------------------------------------------

	//#CM695462
	// Protected methods ---------------------------------------------

	//#CM695463
	// Private methods -----------------------------------------------
	//#CM695464
	/**
	 * Allow this method to set the search conditions for the Result info by worker search key.<BR>
	 *
	 * @param searchKey SystemWorkerResultSearchKey Result info by worker search key
	 */
	private void setWorkerResultSearchKey(SystemWorkerResultSearchKey searchKey)
	{
		try
		{
			if ((wStartWorkDate!= null) && !wStartWorkDate.equals(""))
			{
				if ((wEndWorkDate != null) && !wEndWorkDate.equals(""))
				{
					//#CM695465
					//(WORK_DATE < "XXX" AND WORK_DATE > "XXX")
					searchKey.setWorkDate(wStartWorkDate, ">=", "(", "", "AND");
					searchKey.setWorkDate(wEndWorkDate, "<=", "", ")", "AND");
				}
				else
				{
					//#CM695466
					//WORK_DATE > "XXX"
					searchKey.setWorkDate(wStartWorkDate, ">=");
				}
			}
			else if ((wEndWorkDate != null) && !wEndWorkDate.equals(""))
			{
				//#CM695467
				//WORK_DATE < "XXX"
				searchKey.setWorkDate(wEndWorkDate, "<=");
			}

			if ((wWorkerCode != null) && !wWorkerCode.equals(""))
			{
				searchKey.setWorkerCode(wWorkerCode);
			}

			if ((wWorkType != null) && !wWorkType.equals(""))
			{
				if (wWorkType.equals(SystemParameter.SELECTWORKDETAIL_INSTOCK))
				{
					//#CM695468
					// Receiving : 01
					searchKey.setJobType(SystemDefine.JOB_TYPE_INSTOCK);
				}
				else if (wWorkType.equals(SystemParameter.SELECTWORKDETAIL_STORAGE))
				{
					//#CM695469
					// Storage : 02
					searchKey.setJobType(SystemDefine.JOB_TYPE_STORAGE);
				}
				else if (wWorkType.equals(SystemParameter.SELECTWORKDETAIL_RETRIEVAL))
				{
					//#CM695470
					// Picking : 03
					searchKey.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL);
				}
				else if (wWorkType.equals(SystemParameter.SELECTWORKDETAIL_SORTING))
				{
					//#CM695471
					// Sorting : 04
					searchKey.setJobType(SystemDefine.JOB_TYPE_SORTING);
				}
				else if (wWorkType.equals(SystemParameter.SELECTWORKDETAIL_SHIPPING))
				{
					//#CM695472
					// Shipping : 05
					searchKey.setJobType(SystemDefine.JOB_TYPE_SHIPINSPECTION);
				}
				else if (wWorkType.equals(SystemParameter.SELECTWORKDETAIL_MOVEMENT_RETRIEVAL))
				{
					//#CM695473
					// Relocation for Retrieval : 11
					searchKey.setJobType(SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL);
				}
				else if (wWorkType.equals(SystemParameter.SELECTWORKDETAIL_MOVEMENT_STORAGE))
				{
					//#CM695474
					// Relocation for Storage : 12
					searchKey.setJobType(SystemDefine.JOB_TYPE_MOVEMENT_STORAGE);
				}
				else if (wWorkType.equals(SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDSTORAGE))
				{
					//#CM695475
					// Unplanned Storage
					searchKey.setJobType(SystemDefine.JOB_TYPE_EX_STORAGE);
				}
				else if (wWorkType.equals(SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDRETRIEVAL))
				{
					//#CM695476
					// Unplanned Picking
					searchKey.setJobType(SystemDefine.JOB_TYPE_EX_RETRIEVAL);
				}
				else if (wWorkType.equals(SystemParameter.SELECTWORKDETAIL_MAINTENANCE_INCREASE))
				{
					//#CM695477
					// Increase in Maintenance :31
					searchKey.setJobType(SystemDefine.JOB_TYPE_MAINTENANCE_PLUS);
				}
				else if (wWorkType.equals(SystemParameter.SELECTWORKDETAIL_MAINTENANCE_DECREASE))
				{
					//#CM695478
					// Decrease in Maintenance :32
					searchKey.setJobType(SystemDefine.JOB_TYPE_MAINTENANCE_MINUS);
				}
				else if (wWorkType.equals(SystemParameter.SELECTWORKDETAIL_INVENTORY))
				{
					//#CM695479
					//Inventory Check : 40
					searchKey.setJobType(SystemDefine.JOB_TYPE_INVENTORY);
				}
				else if (wWorkType.equals(SystemParameter.SELECTWORKDETAIL_INVENTORY_INCREASE))
				{
					//#CM695480
					//Increase Inventory Check : 41
					searchKey.setJobType(SystemDefine.JOB_TYPE_INVENTORY_PLUS);
				}
				else if (wWorkType.equals(SystemParameter.SELECTWORKDETAIL_INVENTORY_DECREASE))
				{
					//#CM695481
					//Decrease Inventory Check : 42
					searchKey.setJobType(SystemDefine.JOB_TYPE_INVENTORY_MINUS);
				}

			}

			if (wDispType.equals(SystemParameter.SELECTAGGREGATECONDITION_TERM))
			{
				searchKey.setWorkerCodeGroup(1);
				searchKey.setWorkerCodeCollect("");

				searchKey.setWorkerNameGroup(2);
				searchKey.setWorkerNameCollect("");

				searchKey.setJobTypeGroup(3);
				searchKey.setJobTypeCollect("");

				searchKey.setWorkQtyCollect("SUM");
				searchKey.setWorkCntCollect("SUM");

				wDispCond = SystemParameter.SELECTAGGREGATECONDITION_TERM;

				searchKey.setWorkerCodeOrder(1, true);
				searchKey.setJobTypeOrder(2, true);

			}
			else if (wDispType.equals(SystemParameter.SELECTAGGREGATECONDITION_DAILY))
			{
				//#CM695482
				//Sort by the date order.
				searchKey.setWorkDateCollect("");
				searchKey.setWorkDateGroup(1);

				searchKey.setWorkerCodeCollect("");
				searchKey.setWorkerCodeGroup(2);

				searchKey.setWorkerNameCollect("");
				searchKey.setWorkerNameGroup(3);

				searchKey.setJobTypeCollect("");
				searchKey.setJobTypeGroup(4);

				searchKey.setWorkQtyCollect("SUM");
				searchKey.setWorkCntCollect("SUM");

				wDispCond = SystemParameter.SELECTAGGREGATECONDITION_DAILY;

				//#CM695483
				/**
				 * Set the sorting order.
				 */
				searchKey.setWorkDateOrder(1, true);
				searchKey.setWorkerCodeOrder(2, true);
				searchKey.setJobTypeOrder(3, true);
				searchKey.setWorkStartTimeOrder(4, true);
			}
			else if (wDispType.equals(SystemParameter.SELECTAGGREGATECONDITION_DETAIL))
			{
				//#CM695484
				//All Records
				searchKey.setWorkDateCollect("");
				searchKey.setWorkerCodeCollect("");
				searchKey.setWorkerNameCollect("");
				searchKey.setJobTypeCollect("");
				searchKey.setWorkQtyCollect("");
				searchKey.setWorkCntCollect("");
				searchKey.setTerminalNoCollect("");

				wDispCond = SystemParameter.SELECTAGGREGATECONDITION_DETAIL;

				//#CM695485
				/**
				 * Set the sorting order.
				 */
				searchKey.setWorkDateOrder(1, true);
				searchKey.setWorkerCodeOrder(2, true);
				searchKey.setJobTypeOrder(3, true);
				searchKey.setWorkStartTimeOrder(4, true);
			}
		}
		catch (ReadWriteException re)
		{
		}
	}

}
//#CM695486
//end of class
