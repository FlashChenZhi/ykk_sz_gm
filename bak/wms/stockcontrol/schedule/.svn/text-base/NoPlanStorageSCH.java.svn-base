package jp.co.daifuku.wms.stockcontrol.schedule;

//#CM11262
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.stockcontrol.report.NoPlanStorageWriter;

//#CM11263
/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * <BR>
 * This class allows to execute Unplanned Normal Storage process. <BR>
 * Receive the contents entered via screen as a parameter and execute the process for Unplanned Normal Storage. <BR>
 * Each method in this class receives a connection object and executes the process for updating the database. However, <BR>
 * However, each method disables to commit and roll back of transactions. <BR>
 * This class executes the following processes. <BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   -Initial Display Process-(<CODE>AbstructStockControlSCH()</CODE>Class)<BR>  
 * </DIR>
 * <BR>
 * 2.Process by clicking on the Input button(<CODE>check()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and check for mandatory, overflow and duplication and return the checking results. <BR>
 *   Return true when no corresponding data with the corresponding line No. exists in the inventory information,Return false when condition error occurs or corresponding data exists. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 *   <BR>
 *   [Parameter] *Mandatory Input  +additional Mandatory Input <BR>
 *   <BR>
 *   Worker Code* <BR>
 *   Password* <BR>
 *   Consignor code* <BR>
 *   Consignor name <BR>
 *   Item Code* <BR>
 *   Item Name <BR>
 *   Case/Piece division* <BR>
 *   Storage Location* <BR>
 *   Packed qty per Case <BR>
 *   Packed qty per bundle <BR>
 *   Storage Case Qty+ <BR>
 *   Storage Piece Qty+ <BR>
 *   Case ITF <BR>
 *   Bundle ITF <BR>
 *   Expiry Date <BR>
 *   <BR>
 *   [Check for process condition] <BR>
 *   <BR>
 *   -Worker Code check process-(<CODE>AbstructStockControlSCH()</CODE>Class) <BR>
 *   <BR>
 *   -Check Input Value Process-(<CODE>AbstructStockControlSCH()</CODE>Class) <BR>
 *   <BR>
 *   -Check Overflow.- <BR>
 *   <BR>
 *   -Shelf table check-(<CODE>isAsrsLocation()</CODE>Method)<BR>
 *   <BR>
 *   -Check Count Displayed- <BR>
 *   <BR>
 *   -Duplicate Check- <BR>
 *   <BR>
 *     <DIR>
 *     Check for duplication using Consignor code, Location No., item code, Case/Piece division, and Expiry date as keys.<BR>
 *     Include the Expiry date as a duplication check key if it is defined as an item to make the stock unique in WmsParam. <BR>
 *     </DIR>
 * </DIR>
 * <BR>
 * 3.Start Storage button pressing down(<CODE>startSCH()</CODE>Method) <BR><BR>
 * <BR>
 * <DIR>
 *   Receive the contents displayed in the preset area as a parameter andExecute the Unplanned storage setting process. <BR>
 *   If the Unplanned Storage Work List print division of the parameter is true when completed the process, start-up the class for printing the Unplanned Storage Work List.<BR>
 *   Return true when the process normally completed, or return false when failed to schedule completely due to condition error or other causes. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 *   <BR>
 *   [Parameter] <BR>
 *   <BR>
 *   Worker Code <BR>
 *   Password <BR>
 *   Consignor code <BR>
 *   Consignor name <BR>
 *   Item Code <BR>
 *   Item Name <BR>
 *   Case/Piece division <BR>
 *   Storage Location <BR>
 *   Packed qty per Case <BR>
 *   Packed qty per bundle <BR>
 *   Storage Case Qty <BR>
 *   Storage Piece Qty <BR>
 *   Case ITF <BR>
 *   Bundle ITF <BR>
 *   Expiry Date <BR>
 *   Unplanned storage work List Print Type <BR>
 *   Terminal No. <BR>
 *   <BR>
 *   [Check for process condition] <BR>
 *   <BR>
 *   -Worker Code check process-(<CODE>AbstructStockControlSCH()</CODE>Class)<BR>
 *   <BR>
 *   -Check process for executing daily update Process-(<CODE>AbstructStorageSCH()</CODE>Class) <BR>
 *   <BR>
 *   -Shelf table check-(<CODE>isAsrsLocation()</CODE>Method)<BR>
 *   <BR>
 *   [Update/Add Process] <BR>
 *   <BR>
 *   -Add or Update Inventory Information Table (DMSTOCK).-<BR>
 *   <BR>
 *   <DIR>
 *     Search for the data using Consignor code, Location No., item code, stock status ( Center stock), Case/Piece division, and expiry date. If the inventory information exist, lock the target record.<BR>
 *     Include expiry date in the search conditions if it is defined in the WmsParam as an item (count) that makes stock unique. <BR>
 *     <BR>
 *     When there exists data<BR>
 *     Item Name, Inventory Qty, Storage Date/Time, Consignor Name, Packed qty per Case, Packed qty per bundle,Update the Case ITF, bundle ITF, and the last update process name.<BR>
 *	   Disable to update the allocated qty and the storage planned qty for the data in process of allocation likely to be updated.<BR>
 *     <BR>
 *     [Check for process condition] <BR>
 *     <BR>
 *     -Check Overflow.-<BR>  
 *     <BR>
 *     When no data exists<BR>
 *     Add the inventory information based on the contents of the Received parameter.<BR>
 *     <BR>
 *   </DIR>
 *   -Add Sending Result Info Table (DNHOSTSEND)-(<CODE>AbstructStockControlSCH()</CODE>Class)<BR>  
 *   <BR>
 *   <DIR>
 *     Add the Sending Result Information based on the contents of the Received parameter.<BR>
 *   </DIR>  
 *   <BR>
 *   -Add or update the Worker Result data inquiry table (DNWORKERRESULT).-(<CODE>AbstructStockControlSCH()</CODE>Class)<BR>  
 *   <BR>
 *   <DIR>
 *     Add or update the Worker Result data inquiry based on the Received contents of the parameter.<BR>
 *   </DIR>
 *   <BR>
 *   [Print Process] <BR>
 *   <BR>
 *   Pass the batch No. to the class for printing the Unplanned storage list.<BR>
 *	 Disables to print with no print data.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/14</TD><TD>Y.Kubo</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/10 05:38:04 $
 * @author  $Author: suresh $
 */
public class NoPlanStorageSCH extends AbstractStockControlSCH
{
	//#CM11264
	//	Class variables -----------------------------------------------
	//#CM11265
	/**
	 * Class Name(Unplannned storage)
	 */
	public static String PROCESSNAME = "NoPlanStorageSCH";

	//#CM11266
	// Class method --------------------------------------------------
	//#CM11267
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/10 05:38:04 $");
	}
	//#CM11268
	// Constructors --------------------------------------------------
	//#CM11269
	/**
	 * Initialize this class.
	 */
	public NoPlanStorageSCH()
	{
		wMessage = null;
	}

	//#CM11270
	// Public methods ------------------------------------------------
	//#CM11271
	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * If only one Consignor Code exists in the inventory information, return the corresponding Consignor Code and Consignor Name.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Instance to maintain database connection.
	 * @param searchParam <CODE>StockControlParameter</CODE> class instance with conditions to obtain the display data.<BR>
	 *         Designating any instance other than <CODE>StockControlParameter</CODE> throws ScheduleException.<BR>
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		
		AreaOperator AreaOperator = new AreaOperator(conn);
		
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
		
		StockReportFinder stockFinder = new StockReportFinder(conn);
		StockSearchKey searchKey = new StockSearchKey();

		StockControlParameter dispData = new StockControlParameter();

		//#CM11272
		// Search Data
		//#CM11273
		// Stock status(Center Inventory)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM11274
		// Ensure that the stock qty is 1 or more.
		searchKey.setStockQty(1, ">=");
		//#CM11275
		// Obtain the Area other than ASRS and add it to the search conditions.
		//#CM11276
		// Search for IS NULL if no corresponding area found.
		areaNo = AreaOperator.getAreaNo(areaType);
		searchKey.setAreaNo(areaNo);
		
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");
		
		if (stockFinder.search(searchKey) == 1)
		{
			
			//#CM11277
			// Search Data			
			searchKey.KeyClear();
			//#CM11278
			// Stock status(Center Inventory)
			searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM11279
			// Ensure that the stock qty is 1 or more.
			searchKey.setStockQty(1, ">=");
			//#CM11280
			// Obtain the Consignor Name with the later added date/time.
			searchKey.setRegistDateOrder(1, false);
			//#CM11281
			// Area No
			searchKey.setAreaNo(areaNo);

			searchKey.setConsignorCodeCollect();
			searchKey.setConsignorNameCollect();

			if (stockFinder.search(searchKey) > 0)
			{
				
				Stock[] consignorname = (Stock[]) stockFinder.getEntities(1);

				dispData.setConsignorCode(consignorname[0].getConsignorCode());
				dispData.setConsignorName(consignorname[0].getConsignorName());
				
			}
		}
		stockFinder.close();

		return dispData;

	}

	//#CM11282
	/** 
	 * Receive the contents entered via screen as a parameter and <BR>
	 * Check for mandatory, overflow and duplication and return the checking results. <BR>
	 * Return true when no corresponding data with the corresponding line No. exists in the inventory information, <BR>
	 * Regard such a case as an exclusion error where condition error occurs or the corresponding data exists, and return false. <BR>
	 * @param conn Instance to maintain database connection.
	 * @param checkParam Array of <CODE>StockControlParameter</CODE> class instance with input contents. <BR>
	 *        Designating any instance other than StockControlParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *        Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @param inputParams Array of <CODE>StockControlParameter</CODE> class instance with preset area contents. <BR>
	 *        Designating any instance other than StockControlParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *        Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @return true: if the input content is proper  false: if the input content is not proper
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams) throws ScheduleException, ReadWriteException
	{
		try
		{
			//#CM11283
			// Contents of the input area
			StockControlParameter param = (StockControlParameter) checkParam;
			//#CM11284
			// Contents of Preset area
			StockControlParameter[] paramlist = (StockControlParameter[]) inputParams;

			//#CM11285
			// Check the Worker code and password
			if (!checkWorker(conn, param))
			{
				 return false;
			}
			
			String casepieceflag = param.getCasePieceFlag();
			int enteringqty = param.getEnteringQty();
			int caseqty = param.getStorageCaseQty();
			int pieceqty = param.getStoragePieceQty();
			String locationno = param.getLocationNo();
			
			//#CM11286
			// Check Input Value
			if (!storageInputCheck(casepieceflag, enteringqty, caseqty, pieceqty))
			{
				return false;
			}
			
			//#CM11287
			// Check Location.
			if (!StringUtil.isBlank(locationno))
			{
				LocateOperator locateOperator = new LocateOperator(conn);
				if (locateOperator.isAsrsLocation(locationno))
				{
					//#CM11288
					// 6023442=The specified location is in automatic warehouse. Cannot enter.
					wMessage = "6023442";
					return false;
				}
			}

			//#CM11289
			// Check Overflow.
			long inputqty = (long)param.getStorageCaseQty() * (long)param.getEnteringQty() + (long)param.getStoragePieceQty();
			if (inputqty > WmsParam.MAX_STOCK_QTY)
			{
				//#CM11290
				// 6023058 = Please enter {1} or smaller for {0}.By checking with Storage qty(Total Bulk Qty)
				wMessage = "6023058" + wDelim + DisplayText.getText("LBL-W0377") + wDelim + MAX_STOCK_QTY_DISP;
				return false;
			}
			
			//#CM11291
			// Check Count Displayed
			if (paramlist != null && paramlist.length + 1 > WmsParam.MAX_NUMBER_OF_DISP)
			{
				//#CM11292
				// 6023096 = More than {0} data exist. Data cannot be entered.
				wMessage = "6023096" + wDelim + WmsParam.MAX_NUMBER_OF_DISP;
				return false;
			}

			//#CM11293
			// Duplicate Check
			//#CM11294
			// Check for duplication using Consignor code, Location No., item code, Case/Piece division, and Expiry date as keys.
			//#CM11295
			// Include the Expiry date as a duplication check key if it is defined as an item to make the stock unique in WmsParam.
			if (paramlist != null)
			{
				for (int i = 0; i < paramlist.length; i++)
				{
					//#CM11296
					// Expiry Date control is disabled
					if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
					{
						if (paramlist[i].getConsignorCode().equals(param.getConsignorCode()) &&
							paramlist[i].getLocationNo().equals(param.getLocationNo()) &&
							paramlist[i].getItemCode().equals(param.getItemCode()) &&
							paramlist[i].getCasePieceFlag().equals(param.getCasePieceFlag()) &&
							paramlist[i].getUseByDate().equals(param.getUseByDate()))
						{
							//#CM11297
							// 6023090 = The data already exists.
							wMessage = "6023090";
							return false;
						}
					}
					//#CM11298
					// Expiry date control is disabled
					else
					{
						if (paramlist[i].getConsignorCode().equals(param.getConsignorCode()) &&
							paramlist[i].getLocationNo().equals(param.getLocationNo()) &&
							paramlist[i].getItemCode().equals(param.getItemCode()) &&
							paramlist[i].getCasePieceFlag().equals(param.getCasePieceFlag()))
						{
							//#CM11299
							// 6023090 = The data already exists.
							wMessage = "6023090";
							return false;
						}
						
					}
				}
			}
		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}

		//#CM11300
		// 6001019 = Entry was accepted.
		wMessage = "6001019";
		return true;
	}

	//#CM11301
	/**
	 * Receive the contents entered via screen as a parameter and start the schedule for Unplanned Normal Storage.<BR>
	 * Assume that two or more data may be input via preset area or others. So, require the parameter to receive them in the form of array.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * Return true if the schedule normally completed, or return false if failed.
	 * @param conn Instance to maintain database connection.
	 * @param startParams Array of <CODE>StockControlParameter</CODE> class instance with commitment contents. <BR>
	 *        Designating any instance other than StockControlParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *        Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM11302
			// Check the Worker code and password
			StockControlParameter workparam = (StockControlParameter)startParams[0];
			if (!checkWorker(conn, workparam))
			{
				return false;
			}
			
			//#CM11303
			// Check the Daily Update Processing.
			if (isDailyUpdate(conn))
			{
				return false;
			}	
			
			StockControlParameter[] param = (StockControlParameter[]) startParams;

			if (param != null)
			{
				//#CM11304
				// Worker Code
				String workercode = param[0].getWorkerCode();
				//#CM11305
				// Worker Name
				String workername = getWorkerName(conn, workercode);

				//#CM11306
				// Work Date ( System defined date)
				String sysdate = getWorkDate(conn);
								
				//#CM11307
				// Terminal No.
				String terminalno = param[0].getTerminalNumber();
				//#CM11308
				// Work division(Unplannned storage)
				String jobtype = Stock.JOB_TYPE_EX_STORAGE;
				//#CM11309
				// Obtain each unique key to add.
				SequenceHandler sequence = new SequenceHandler(conn);
				//#CM11310
				// Batch No.(Common for one commitment)
				String batch_seqno = sequence.nextNoPlanBatchNo();
				//#CM11311
				// Set the current date/time for storage date/time. (common for one commitment)
				Date instockdate = new Date();
				//#CM11312
				// TC/DC division (Set the DC)
				String tcdckbn = HostSend.TCDC_FLAG_DC;

				int workqty = 0;
				
				try
				{
					//#CM11313
					// Lock Process(inventory information,  Location info)
					lockStockData(conn, (StockControlParameter[])startParams);	
				}
				catch(LockTimeOutException e)
				{
					//#CM11314
					// 6027008 = This data is not treatable because it is updating it with other terminals.
					wMessage = "6027008";
					return false;
				}
		
				//#CM11315
				// Check Automated Warehouse.
				for (int i = 0; i < param.length; i++)
				{

					LocateOperator locateOperator = new LocateOperator(conn);

					//#CM11316
					// Location
					String locationno = param[i].getLocationNo();
					
					//#CM11317
					// Check Location.
					if (!StringUtil.isBlank(locationno))
					{
						if (locateOperator.isAsrsLocation(locationno))
						{
							//#CM11318
							// 6023443=No.{0} The specified location is in automatic warehouse. Cannot enter.
							wMessage = "6023443" + wDelim + param[i].getRowNo();
							return false;
						}
					}
				}
				
				for (int i = 0; i < param.length; i++)
				{
					//#CM11319
					// Storage qty
					int inputqty = param[i].getStorageCaseQty() * param[i].getEnteringQty() + param[i].getStoragePieceQty();
					//#CM11320
					// Work Quantity (Totalize the result qty of Work status for one commitment)
					workqty = addWorkQty(workqty, inputqty);
					
					//#CM11321
					// Add or Update Inventory Information Table (DMSTOCK).
					String newstockid = "";
					try
					{
						//#CM11322
						// Add the inventory information.
						//#CM11323
						// If stock data exists, sum up the stock qty (replenishment storage).
						//#CM11324
						// Generate a new inventory information if no stock data found. (new storage)
						newstockid = processStockData(conn, param[i], inputqty, instockdate);
					}
					catch(DataExistsException e)
					{
						try
						{
							//#CM11325
							// If create a new data in the same condition via other station,
							//#CM11326
							// Creating a new data at one terminal generates a unique restriction error at another terminal.
							//#CM11327
							// In such case, invoke the Stock Add process again and execute the replenishment storage.
							newstockid = processStockData(conn, param[i], inputqty, instockdate);
						}
						catch(OverflowException ex)
						{
							//#CM11328
							// 6023348 = Cannot enter.  Stock Qty. exceeds {0}.
							wMessage = "6023348" + wDelim + MAX_STOCK_QTY_DISP;
							return false;
						}
					}
					catch(OverflowException e)
					{
						//#CM11329
						// 6023348 = Cannot enter.  Stock Qty. exceeds {0}.
						wMessage = "6023348" + wDelim + MAX_STOCK_QTY_DISP;
						return false;
					}
					
					//#CM11330
					// Update the Location info of the stored location.
					LocateOperator lOperator = new LocateOperator(conn);
					lOperator.modifyLocateStatus(param[i].getLocationNo(), PROCESSNAME);

					//#CM11331
					// Set the storage location area.
					param[i].setAreaNo(lOperator.getAreaNo(param[i].getLocationNo()));
					
					//#CM11332
					// Add Sending Result Info Table (DNHOSTSEND)
					if(!createHostsend(conn, param[i], newstockid, workercode, workername, sysdate, terminalno, jobtype, PROCESSNAME, batch_seqno, tcdckbn, inputqty))
					{
						return false;
					}
				}
				
				//#CM11333
				// Add or update the Worker Result data inquiry table (DNWORKERRESULT).
				//#CM11334
				// Add or update the Worker Result data inquiry, after completed adding or updating the inventory information table for one commitment.
				updateWorkerResult(conn, workercode, workername, sysdate, terminalno, jobtype, workqty);
								
				//#CM11335
				// Generate a file for Unplanned Storage Result list.
				if(param[0].getListFlg() == true)
				{
					if(startPrint(conn, batch_seqno))
					{
						//#CM11336
						// 6021012 = Data had been set and the list was printed successfully.
						wMessage = "6021012";
					}
					else
					{
						//#CM11337
						// 6007042 = Printing failed after setup. Please refer to log.
						wMessage = "6007042";
					}
				}
				else
				{
					//#CM11338
					// 6001006 = Data was committed.
					wMessage = "6001006";
				}
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (DataExistsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM11339
	// Package methods -----------------------------------------------

	//#CM11340
	// Protected methods ---------------------------------------------
	//#CM11341
	/**
	 * Pass the batch No. to the class for printing the Unplanned storage list.<BR>
	 * Disables to print with no print data.<BR>
	 * If successfully printed, receive true from the class for printing the Unplanned storage. If failed, receive false and<BR>
	 * return the result.<BR>
	 * Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
	 * @param  conn Connection Database connection object
	 * @param  batchno Batch No.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 */
	protected boolean startPrint(Connection conn, String batchno) throws ReadWriteException, ScheduleException
	{
		NoPlanStorageWriter writer = new NoPlanStorageWriter(conn);
		writer.setBatchNumber(batchno);

		//#CM11342
		// Start the printing process.
		if (writer.startPrint())
		{
			return true;
		}
		else
		{
			return false;
		}

	}
	
	//#CM11343
	/**
	 * Add or Update the Inventory Information table
	 * @param  conn        Instance to maintain database connection.
	 * @param  param       StockControlParameter class instance with contents that were input via screen.
	 * @param  inputqty    Storage qty
	 * @param  instockdate Storage Date/Time
	 * @throws NoPrimaryException Announce when the defined information is abnormal.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws DataExistsException Announce this when the same Information has been already added.
	 * @throws OverflowException Announce when overflow occurs.
	 * @return Stock ID
	 */
	private String processStockData(Connection conn, StockControlParameter param, int inputqty, Date instockdate
		) throws NoPrimaryException, ReadWriteException, DataExistsException, OverflowException
	{
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey searchKey = new StockSearchKey();
		
		//#CM11344
		// Search Data
		//#CM11345
		// Consignor code
		searchKey.setConsignorCode(param.getConsignorCode());
		//#CM11346
		// Location No.
		searchKey.setLocationNo(param.getLocationNo());
		//#CM11347
		// Item Code
		searchKey.setItemCode(param.getItemCode());
		//#CM11348
		// Stock status(Center Inventory)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM11349
		// Case/Piece division(Load Size)
		//#CM11350
		// None
		if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
		}
		//#CM11351
		// Case
		else if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_CASE))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
		}
		//#CM11352
		// Piece
		else if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
		}
		//#CM11353
		// If the search conditions for inventory information include expiry date,
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			if (!StringUtil.isBlank(param.getUseByDate()))
			{
				searchKey.setUseByDate(param.getUseByDate());
			}
			else
			{
				searchKey.setUseByDate("", "");
			}
		}
	
		//#CM11354
		// Lock Data.
		Stock stock = (Stock)stockHandler.findPrimaryForUpdate(searchKey);
	
		//#CM11355
		// Stock ID
		String stockid = "";
		//#CM11356
		// If there is no stock data, (new storage)
		if(stock == null)
		{
			//#CM11357
			// Add Inventory Information Table (DMSTOCK).
			stockid = createStock(conn, param, inputqty, instockdate);
			
		}
		//#CM11358
		// If stock data exists, (replenishment storage)
		else
		{
			stockid = stock.getStockId();
			//#CM11359
			// Stock Qty = Stock Qty + Storage qty
			int totalstockqty = stock.getStockQty() + inputqty;
			//#CM11360
			// Check for overflow of the stock qty.
			if (totalstockqty > WmsParam.MAX_STOCK_QTY)
			{
				//#CM11361
				// 6026025 = Cannot set. Stock Qty. exceeds {0}.
				RmiMsgLogClient.write("6026025" + wDelim + WmsParam.MAX_STOCK_QTY, "NoPlanStorageSCH");
				//#CM11362
				// Throw OverflowException here.(Do not need to set error message.)
				throw (new OverflowException());
			}
			
			//#CM11363
			//Allocatable qty
			int allocationQty = stock.getAllocationQty() + inputqty;
			//#CM11364
			// Update Inventory Information Table (DMSTOCK).
			updateStock(conn, param, stockid, totalstockqty, allocationQty, instockdate);
			
		}
		return stockid;
	}

		
	//#CM11365
	/**
	 * Add the inventory information table.
	 * @param  conn        Instance to maintain database connection.
	 * @param  param       StockControlParameter class instance with contents that were input via screen.
	 * @param  inputqty    Storage qty
	 * @param  instockdate Storage Date/Time
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @return Stock ID
	 */
	protected String createStock(Connection conn, StockControlParameter param, int inputqty, Date instockdate)
																						throws ReadWriteException
	{
		try
		{
			StockHandler stockHandler = new StockHandler(conn);
			Stock stock = new Stock();

			//#CM11366
			// Obtain each unique key to add.
			SequenceHandler sequence = new SequenceHandler(conn);
			//#CM11367
			// Stock ID
			String stockId_seqno = sequence.nextStockId();
			stock.setStockId(stockId_seqno);
			//#CM11368
			// Plan unique key
			stock.setPlanUkey("");
			//#CM11369
			// Area No.
			try
			{
				//#CM11370
				// Obtain the Area No. from the location info.(2005/06/14 Add By:T.T)
				LocateOperator lOperator = new LocateOperator(conn);
				stock.setAreaNo(lOperator.getAreaNo(param.getLocationNo()));
			}
			catch (ScheduleException e)
			{
				throw new ReadWriteException(e.getMessage());
			}
			//#CM11371
			// Location No.
			stock.setLocationNo(param.getLocationNo());
			//#CM11372
			// Item Code
			stock.setItemCode(param.getItemCode());
			//#CM11373
			// Item Name
			stock.setItemName1(param.getItemName());
			//#CM11374
			// Stock status(Center Inventory)
			stock.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM11375
			// stock qty (storage qty)
			stock.setStockQty(inputqty);
			//#CM11376
			// Allocatable qty
			stock.setAllocationQty(inputqty);
			//#CM11377
			// Storage Planned Qty
			stock.setPlanQty(0);
			//#CM11378
			// Case/Piece division(Load Size)
			//#CM11379
			// None
			if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				stock.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
			}
			//#CM11380
			// Case
			else if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_CASE))
			{
				stock.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
			}
			//#CM11381
			// Piece
			else if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
			{
				stock.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
			}
		
			//#CM11382
			// Storage Date/Time
			stock.setInstockDate(instockdate);
			//#CM11383
			// Last Picking Date
			stock.setLastShippingDate("");
			//#CM11384
			// Expiry Date
			stock.setUseByDate(param.getUseByDate());
			//#CM11385
			// Lot No.
			stock.setLotNo("");
			//#CM11386
			// Plan information Comment
			stock.setPlanInformation("");
			//#CM11387
			// Consignor code
			stock.setConsignorCode(param.getConsignorCode());
			//#CM11388
			// Consignor name
			stock.setConsignorName(param.getConsignorName());
			//#CM11389
			// Supplier Code
			stock.setSupplierCode("");
			//#CM11390
			// Supplier Name
			stock.setSupplierName1("");
			//#CM11391
			// Packed qty per Case
			stock.setEnteringQty(param.getEnteringQty());
			//#CM11392
			// Packed qty per bundle
			stock.setBundleEnteringQty(param.getBundleEnteringQty());
			//#CM11393
			// Case ITF
			stock.setItf(param.getITF());
			//#CM11394
			// Bundle ITF
			stock.setBundleItf(param.getBundleITF());
			//#CM11395
			// Name of Add Process
			stock.setRegistPname(PROCESSNAME);
			//#CM11396
			// Last update process name
			stock.setLastUpdatePname(PROCESSNAME);

			//#CM11397
			// Data addition
			stockHandler.create(stock);

			return stockId_seqno;
		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM11398
	/**
	 * Update the inventory information table.
	 * @param  conn        Instance to maintain database connection.
	 * @param  param       StockControlParameter class instance with contents that were input via screen.
	 * @param  stockid     Stock ID
	 * @param  totalstockqty    Stock Qty
	 * @param  allocationqty    Allocatable qty
	 * @param  instockdate Storage Date/Time
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 */
	protected boolean updateStock(Connection conn, StockControlParameter param, String stockid,
			int totalstockqty, int allocationqty, Date instockdate) throws ReadWriteException
	{
		try
		{
			StockHandler stockHandler = new StockHandler(conn);
			StockAlterKey alterKey = new StockAlterKey();

			//#CM11399
			// Item Name, Inventory Qty, Storage Date/Time, Consignor Name, Packed qty per Case, Packed qty per bundle,Update the Case ITF, bundle ITF, and the last update process name.
			//#CM11400
			// Disable to update the allocated qty and the storage planned qty for the data in process of allocation likely to be updated.
			//#CM11401
			// Allocatable qty��
				
			//#CM11402
			// Set the Stock ID.
			alterKey.setStockId(stockid);
		
			//#CM11403
			// Item Name
			alterKey.updateItemName1(param.getItemName());
			//#CM11404
			// Stock Qty
			alterKey.updateStockQty(totalstockqty);
			//#CM11405
			// Allocatable Qty
			alterKey.updateAllocationQty(allocationqty);
			//#CM11406
			// Storage Date/Time
			alterKey.updateInstockDate(instockdate);
			//#CM11407
			// If search conditions for the inventory information does not include any expiry date, 
			if (!WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				alterKey.updateUseByDate(param.getUseByDate());
			}
			//#CM11408
			// Consignor name
			alterKey.updateConsignorName(param.getConsignorName());
			//#CM11409
			// Packed qty per Case
			alterKey.updateEnteringQty(param.getEnteringQty());
			//#CM11410
			// Packed qty per bundle
			alterKey.updateBundleEnteringQty(param.getBundleEnteringQty());
			//#CM11411
			// Case ITF
			alterKey.updateItf(param.getITF());
			//#CM11412
			// Bundle ITF
			alterKey.updateBundleItf(param.getBundleITF());
			//#CM11413
			// Last update process name
			alterKey.updateLastUpdatePname(PROCESSNAME);

			//#CM11414
			// Update Data.
			stockHandler.modify(alterKey);

			return true;
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM11415
	/**
	 * Lock the inventory information to be updated.
	 * Generate array of Stock ID from the array of parameter designated by argument.
	 * Lock the corresponding inventory information.
	 * <DIR>
	 *    (search conditions)
	 *    <UL>
	 *     <LI>Consignor code</LI>
	 *     <LI>Location No.</LI>
	 *     <LI>Item Code</LI>
	 *     <LI>Case/Piece division</LI>
	 *     <LI>Stock status[Center Inventory]</LI>
	 *     <LI>Expiry Date(Expiry Date control is disabled)</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param  conn        Instance to maintain database connection.
	 * @param	param		Screen Info Array
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws LockTimeOutException Announce when database is unlocked for the specified time.
	 * @throws NoPrimaryException Announce this if used when the definition information is wrong.
	 */
	protected void lockStockData(Connection conn, StockControlParameter[] param)
		throws NoPrimaryException, ReadWriteException,LockTimeOutException
	{
		String[] stockIdList = null;
		Vector vec = new Vector();
		String[] LocationNoList = null;
		Vector vec2 = new Vector();
		String[] AreaNoList = null;
		Vector vec3 = new Vector();
		
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey searchKey = new StockSearchKey();
		
		LocateHandler locateHandler = new LocateHandler(conn);
		LocateSearchKey locatesearchkey = new LocateSearchKey();
		
		//#CM11416
		// Obtain the relocation rack area No. from WmsParam.
		String w_AreaNo = WmsParam.IDM_AREA_NO;
		
		//#CM11417
		// Obtain the corresponding Stock ID from the inventory information if any input info via screen already reside.
		for (int i = 0; i < param.length; i ++)
		{			
			searchKey.KeyClear();
			
			//#CM11418
			// Search Data
			//#CM11419
			// Consignor code
			searchKey.setConsignorCode(param[i].getConsignorCode());
			//#CM11420
			// Location No.
			searchKey.setLocationNo(param[i].getLocationNo());
			//#CM11421
			// Item Code
			searchKey.setItemCode(param[i].getItemCode());
			//#CM11422
			// Stock status(Center Inventory)
			searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM11423
			// Case/Piece division(Load Size)
			//#CM11424
			// None
			if(param[i].getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
			}
			//#CM11425
			// Case
			else if(param[i].getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_CASE))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
			}
			//#CM11426
			// Piece
			else if(param[i].getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
			}
			//#CM11427
			// If the search conditions for inventory information include expiry date,
			if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				if (!StringUtil.isBlank(param[i].getUseByDate()))
				{
					searchKey.setUseByDate(param[i].getUseByDate());
				}
				else
				{
					searchKey.setUseByDate("", "");
				}
			}
			
			//#CM11428
			// Search Existing Info.
			Stock stock = (Stock)stockHandler.findPrimary(searchKey);
			
			//#CM11429
			// Reserve in Vector
			if (stock != null)
			{
				vec.addElement(stock.getStockId());				
			}
			//#CM11430
			// Search Location Info
			locatesearchkey.KeyClear();
			locatesearchkey.setAreaNo(w_AreaNo);
			locatesearchkey.setLocationNo(param[i].getLocationNo());
			//#CM11431
			// Search Existing Location Info.
			Locate locate = (Locate)locateHandler.findPrimary(locatesearchkey);
		
			//#CM11432
			// Reserve in Vector
			if (locate != null)
			{
				vec2.addElement(locate.getLocationNo());				
				vec3.addElement(locate.getAreaNo());				
			}
		}
		
		//#CM11433
		// If any existing inventory information resides,
		if (vec.size() > 0 )
		{
			stockIdList = new String[vec.size()];
			vec.copyInto(stockIdList);
		
			searchKey.KeyClear();
			searchKey.setStockId(stockIdList);
		
			//#CM11434
			// Lock all the inventory information reserved in Vector.
			stockHandler.findForUpdateNowait(searchKey);
		}
		//#CM11435
		// If any existing location info resides,
		if (vec2.size() > 0 )
		{
			LocationNoList = new String[vec2.size()];
			vec2.copyInto(LocationNoList);
			AreaNoList = new String[vec3.size()];
			vec3.copyInto(AreaNoList);
		
			locatesearchkey.KeyClear();
			locatesearchkey.setAreaNo(AreaNoList);
			locatesearchkey.setLocationNo(LocationNoList);
		
			//#CM11436
			// Lock all the location info reserved in Vector.
			locateHandler.findForUpdateNowait(locatesearchkey);
		}
	}
	
	//#CM11437
	// Private methods -----------------------------------------------
	
}
//#CM11438
//end of class
