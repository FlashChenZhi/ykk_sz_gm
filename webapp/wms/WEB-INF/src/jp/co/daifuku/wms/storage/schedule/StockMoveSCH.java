package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.MovementAlterKey;
import jp.co.daifuku.wms.base.dbhandler.MovementHandler;
import jp.co.daifuku.wms.base.dbhandler.MovementSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.Worker;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.storage.report.MovementWriter;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
  * This class executes WEB stock relocation work. <BR>
 * Receive the contents entered via screen as a parameter and execute the process for relocating the stock. <BR>
 * Each method in this class receives a connection object and executes the process for updating the database. <BR>
 * However, each method disables to commit and roll back of transactions. <BR>
 * This class executes the following processes. <BR>
 * <BR>
 * <B>1.Initial Display Process(<CODE>initFind()</CODE>Method)</B> <BR>
 * This method supports operations to obtain the data required for initial display.<BR>
 * Return the results of checking for presence of inventory control package, as well as the initial display data.<BR>
 * Initial display data are as follows.<BR>
 * <BR>
 * If inventory control package is available,
 * <DIR>
 *   -If only one Consignor code exists in the inventory information, Return the corresponding Consignor code and Consignor name.<BR>
 *   -Return null if no corresponding data found, or two or more corresponding data exist. <BR>
 *   -Requiring no search conditions sets null for <CODE>searchParam</CODE>.<BR>
 *   -Obtain the Consignor Name with the latest submitted/added date/time if two or more Consignor Name exist.<BR>
 *   -Use <CODE>StockReportFinder</CODE> to search.<BR>
 *   -Search for the inventory information linked to the Area No. with Area division other than AS/RS.<BR>
 * </DIR>
 * If inventory control package is not available,
 * <DIR>
 *   -Disable to display initial value.<BR>
 * </DIR>
 * <BR>
 * <Search table>
 * <DIR>
 *   DmStock<BR>
 * </DIR>
 * <BR>
 * <Search conditions>
 * <DIR>
 *   Center Inventory<BR>
 * </DIR>
 * <BR>
 * <B>2.Process by clicking on the Input button<BR>(<CODE>check(Connection conn, Parameter checkParam, Parameter[] inputParams)</CODE>Method<BR><CODE>query(Connection conn, Parameter searchParam)</CODE>Method)</B><BR>
 * Enable to use a check method only if there is no inventory package.<BR>
 * Invoke check method in the case where inventory package is available and invoke query method only when the return value is true.<BR>
 * <BR>
 * <Parameter> *Mandatory Input
 * <DIR>
 *   Worker Code* <BR>
 *   Password* <BR>
 *   Consignor code* <BR>
 *   Consignor name<BR>
 *   Item Code*<BR>
 *   Item Name<BR>
 *   Relocation Source Location*<BR>
 *   Expiry Date(If inventory control package is available,Mandatory)<BR>
 *   Packed qty per Case<BR>
 *   Relocation Case Qty<BR>
 *   Relocation Piece Qty<BR>
 *   Relocation Target Location*<BR>
 * </DIR>
 *   <BR>
 * <I>Allow the check method to execute the following processes.<BR></I>
 * Before inputting the entered data into the preset area, check the data for its properness.<BR>
 * Correct input returns true. Incorrect input returns false.<BR>
 * Check the following matters.<BR>
 * <DIR>
 *   -Require to input worker code and password correctly.<BR>
 *   -Require the count of data to be displayed not to exceed the maximum count of data to be displayed.<BR>
 *   -Require to input in the Relocation Case qty or Relocation Piece qty field.<BR>
 *   -Disable to input in the Relocation Case qty, if the Packed qty per Case is 0.<BR>
 *   -Ensure that a single location does not contain both Relocation source location and Relocation target location together.<BR>
 *   -Require total Relocated qty not to overflow.<BR>
 *   -Ensure not to input two or more data in the preset area.<BR>
 *   -Require no existence of the designated location in AS/RS location info. Shelf table check - (<CODE>isAsrsLocation()</CODE> method)<BR>
 * </DIR>
 * <BR>
 * <Returned data>
 * <DIR>
 *   true: correct input<BR>
 *   false: incorrect input<BR>
 * </DIR>
 * <BR>
 * <I>Allow the query method to check and execute the following processes.<BR></I>
 * Check whether the allocation is possible or not. If possible, Obtain the Consignor name and item name.<BR>
 * Invoking this method in the case of inventory package unavailable throws <CODE>ScheduleException</CODE>.<BR>
 * Execute the following processes.<BR>
 * <DIR>
 *   -Require the target stock existing.<BR>
 *   -Require Allocatable qty to be equal to the input relocation qty or more.<BR>
 *   -If any data satisfies the said conditions but with no input of Consignor name and item name, obtain the name from inventory information.<BR>
 * </DIR>
 * <BR>
 * <Returned data>
 * <DIR>
 *   This class implements display info including <CODE>Parameter</CODE>interface.<BR>
 * </DIR>
 * <BR>
 * <B>3.Process by clicking Relocation button(<CODE>startSCH()</CODE>Method)</B> <BR>
 * Receive the contents displayed in the preset area as a parameter and execute the process to relocate the stock. <BR>
 * Return true if the schedule normally completed.<BR>
 * Return false when failed to schedule completely due to condition error or other causes. <BR>
 * Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 * For update, update the inventory information (picking allocation), generate a Stock Relocation Info (Relocation picking), update the Stock Relocation Info (Relocation storage),
 * update/add the inventory information ( process for completing the relocation source and the relocation target), generate the Result data inquiry, and then generating the Worker result, in this order.<BR>
 * Placing a check in the Print Work List option after the schedule succeeded,
 * invoke <CODE>startPrint</CODE> method and start printing.<BR>
 * This method returns true if succeeded or failed to print.<BR>
 * <BR>
 * <Parameter> *Mandatory Input
 * <DIR>
 *   Worker Code* <BR>
 *   Password* <BR>
 *   Consignor code* <BR>
 *   Consignor name <BR>
 *   Item Code* <BR>
 *   Item Name <BR>
 *   Relocation Source Location* <BR>
 *   Expiry Date(If inventory control package is available, Mandatory)<BR>
 *   Packed qty per Case<BR>
 *   Relocation Case Qty<BR>
 *   Relocation Piece Qty<BR>
 *   Relocation Target Location*<BR>
 *   Work Status* <BR>
 *   Division for printing Relocation work list or not* <BR>
 *   Preset Line No*<BR>
 * </DIR>
 * <BR>
 * Execute the processes in the sequence as below.<BR>
 * <BR>
 * 1.Check for process condition
 * <DIR>
 *   -Require not to be in the process of daily update. <BR>
 *   -Ensure to define Worker code and password in the Worker master. <BR>
 *   -Check only the leading value of the array for the values of Worker code and password. <BR>
 *   -Require no existence of the designated location in AS/RS location info. Shelf table check - (<CODE>isAsrsLocation()</CODE> method)<BR>
 * </DIR>
 * 2.Update inventory information (Allocate Picking)
 * <DIR>
 *   -Updating Table:DmStock<BR>
 *   -Lock the stock of the relocation source and allocate it. (<CODE>StockAllocateOperater.stockMovementAllocate</CODE> method)<BR>
 *   -Regard the data with stock updated while allocation failed as an error via other terminal.<BR>
 *   *Generate only an instance if no inventory package, instead of updating DmStock.<BR>
 * </DIR>
 * 3.Update the stock relocation info (relocation storage)
 * <DIR>
 *   -Updating Table:DnMovement<BR>
 *   -Allocating for two or more stock allows to generate two or more relocation info for one work with the same aggregation work No.<BR>
 * </DIR>
 * 4.Update the stock relocation info (relocation storage).
 * <DIR>
 *   -Updating Table:DnMovement<BR>
 *   -Obtain the relocation infor generated at the step 3 using the aggregation work No. and update each obtained info. <BR>
 *   -(<CODE>MovementCompleteOperator.complete</CODE>Method)<BR>
 * </DIR>
 * 5.Update/add the inventory information (Relocation storage) and add the result information.
 * <DIR>
 *   -Updating Table:DmStockDnHostSend<BR>
 *   -Obtain the relocation infor generated at the step 3 using the aggregation work No. and update each obtained info. <BR>
 *   -Lock the stock of the relocation source and relocation target to update it. (<CODE>MovementCompleteOperator.complete</CODE> method)<BR>
 *   -Generate the Result data inquiry by work No. (<CODE>MovementCompleteOperator.complete</CODE> method)<BR>
 * </DIR>
 * 6.Update/Add Worker Result Data Inquiry
 * <DIR>
 *   -Updating Table:DnWorkerResult<BR>
 *   -Update and add the Worker Result data inquiry<BR>
 * </DIR>
 * 7.Print Process
 * <DIR>
 *   -Placing a check in the "Print Work List" option invokes the <CODE>startPrint</CODE> method.<BR>
 * </DIR>
 *
 * <B>4.Print Process(<CODE>startPrint(conn, jobNoVec)</CODE>Method) </B> <BR>
 * Receive the committed Work No. and pass the parameter to the class for printing the Stock Relocation work list.<BR>
 * Search for the printing contents in the Writer class.<BR>
 * Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 * <BR>
 * <Parameter> *Mandatory Input<BR>
 * <DIR>
 *   Work No.(Vector)* <BR>
 * </DIR>
 * <BR>
 * <Check for process condition>
 * <DIR>
 *   None<BR>
 * </DIR>
 * <BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>Y.Okamura</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/10/16 08:33:11 $
 * @author  $Author: suresh $
 */
public class StockMoveSCH extends AbstractStorageSCH
{

	// Class variables -----------------------------------------------
	/**
	 * Process name
	 */
	private static String wProcessName = "StockMoveSCH";

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2006/10/16 08:33:11 $");
	}
	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public StockMoveSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * Return the Results of checking for presence of inventory control package and/or Expiry Date Control, as well as the initial display data.<BR>
	 * Initial display data are as follows.<BR>
	 * <BR>
	 * If inventory control package is available,<BR>
	 * <DIR>
	 *   If only one Consignor code exists in the inventory information, Return the corresponding Consignor code and Consignor name.<BR>
	 *   Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 *   Requiring no search conditions sets null for <CODE>searchParam</CODE>.<BR>
	 *   Obtain the Consignor Name with the latest submitted/added date/time if two or more Consignor Name exist.<BR>
	 * </DIR>
	 * If inventory control package is not available,<BR>
	 * <DIR>
	 *   Disable to display initial value.<BR>
	 * </DIR>
	 * <BR>
	 * @param conn Instance to maintain database connection.
	 * @param searchParam Instance of <CODE>StorageSupportParameter</CODE> class with conditions to obtain the display data.<BR>
	 *         Designating any instance other than <CODE>StorageSupportParameter</CODE> throws ScheduleException.<BR>
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		// Returned data
		StorageSupportParameter param = new StorageSupportParameter();
		// Set the inventory control package unavailable for the return value (if available, overwrite it to true later).
		param.setStockPackageFlag(false);
		// Expiry Date control flag
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			param.setUseByDateFlag(true);
		}
		else
		{
			param.setUseByDateFlag(false);
		}

		// If inventory control package is available, display the initial display.
		if (isStockPack(conn))
		{

			AreaOperator AreaOperator = new AreaOperator(conn);

			String[] areaNo = null;
			int[] areaType = new int[2];
			areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
			areaType[1] = Area.SYSTEM_DISC_KEY_IDM;

			// Generate inventory info instance of handlers.
			StockSearchKey stockKey = new StockSearchKey();
			StockHandler stockHandler = new StockHandler(conn);

			// Set the search conditions.
			// Status flag: Center Inventory
			stockKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			stockKey.setStockQty(0, ">");
			stockKey.setConsignorCodeGroup(1);
			stockKey.setConsignorCodeCollect("");

			// Obtain the Area other than ASRS and add it to the search conditions.
			// Search for IS NULL if no corresponding area found.
			areaNo = AreaOperator.getAreaNo(areaType);
			stockKey.setAreaNo(areaNo);

			// If only one consignor code exists, obtain the Consignor Code and Consignor Name.
			if (stockHandler.count(stockKey) == 1)
			{
				StockReportFinder nameFinder = new StockReportFinder(conn);

				// Set the Search key.
				stockKey.KeyClear();
				stockKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
				stockKey.setStockQty(0, ">");
				// Area No
				stockKey.setAreaNo(areaNo);
				stockKey.setConsignorCodeCollect();
				stockKey.setConsignorNameCollect();
				stockKey.setRegistDateOrder(1, false);
				// Execute the search.
				nameFinder.open();
				if (nameFinder.search(stockKey) > 0)
				{
					Stock nameStock[] = (Stock[]) nameFinder.getEntities(1);

					if (nameStock != null && nameStock.length != 0)
					{
						// Set the search result for the return value.
						param.setConsignorCode(nameStock[0].getConsignorCode());
						param.setConsignorName(nameStock[0].getConsignorName());
					}
				}
				nameFinder.close();

			}

			// Set the value" Inventory control package available" for the return value.
			param.setStockPackageFlag(true);
		}

		return param;
	}

	/**
	 * Search through the inventory information via input data and set the Consignor name and Item Name for preset area display and return them. <BR>
	 * Enabling expiry date control allows to include additionally expiry date in the search conditions.<BR>
	 * Invoking this method with no inventory package throws exception.<BR>
	 *
	 * @param conn Instance to maintain database connection.
	 * @param searchParam Instance of <CODE>StorageSupportParameter</CODE> class with conditions to obtain the display data.<BR>
	 *         Designating any instance other than <CODE>StorageSupportParameter</CODE> throws ScheduleException.<BR>
	 * @return Parameter[] Data to be displayed in the preset area
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when this method is invoked.
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		// Invoking this method with no inventory package throws exception.
		if (!isStockPack(conn))
		{
			throw new ScheduleException("This method is not supported.");
		}

		// Input data
		StorageSupportParameter param = null;
		// Returned data
		StorageSupportParameter[] resultParam = null;

		param = (StorageSupportParameter) searchParam;

		// Compile the input data for preset display.
		resultParam = addList(conn, param);

		return resultParam;

	}

	/**
	 * Clicking the Input button via Relocation screen allows to use this method.<BR>
	 * This method checks for possibility of entering the input data into preset additionally.<BR>
	 * <DIR>
	 *   -If entered data is correct: true<BR>
	 *   -If entered data is incorrect: false<BR>
	 * </DIR>
	 *
	 * @param conn Instance to maintain database connection.
	 * @param checkParam <CODE>StorageSupportParameter</CODE> class instance with the entered data.
	 * @param inputParams Information in preset area to compare to the entered data
	 * @return boolean Return whether the entered data is correct or not.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when this method is invoked.
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams) throws ReadWriteException, ScheduleException
	{
		// Input data
		StorageSupportParameter param = null;
		// Preset data
		StorageSupportParameter[] paramList = null;

		param = (StorageSupportParameter) checkParam;
		paramList = (StorageSupportParameter[]) inputParams;

		// Check for Worker code and password.
		if (!checkWorker(conn, param))
		{
			return false;
		}

		// Check common for inventory package Available/Not Available (Input check ).
		if (!doCommonCheck(param, paramList))
		{
			// Set the message in the invoking source method.
			return false;
		}

		// Location No
		String wLocationNo = "";
		// Obtain the location.
		wLocationNo = param.getDestLocationNo();

		if (!StringUtil.isBlank(wLocationNo))
		{
			LocateOperator locateOperator = new LocateOperator(conn);
			if (locateOperator.isAsrsLocation(wLocationNo))
			{
				// 6023442=The specified location is in automatic warehouse. Cannot enter.
				wMessage = "6023442";
				return false;
			}
		}

		// If inventory package is available, executes the check.
		if (isStockPack(conn))
		{
			if (!doStockCheck(conn, param, paramList))
			{
				// Set the message in the invoking source method.
				return false;
			}

		}
		// If there is no inventory package, execute the check.
		else
		{
			if (!doNoStockCheck(conn, param, paramList))
			{
				// Set the message in the invoking source method.
				return false;
			}
		}

		// 6001019 = Entry was accepted.
		wMessage = "6001019";
		return true;
	}

	/**
	 * Receive the contents entered via screen as a parameter and start the schedule for the Stock Relocation.<BR>
	 * Assume that two or more data may be input via preset area or others. So, require the parameter to receive them in the form of array.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * Return true if the schedule normally completed, or return false if failed.<BR>
	 *
	 * @param conn Instance to maintain database connection.
	 * @param startParams Array of <CODE>StorageSupportParameter</CODE> class instance with contents of commitment. <BR>
	 *         Designating any instance other than StorageSupportParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *         Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @return boolean Return whether the schedule completed successfully.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		try
		{
			// Check the Daily Update Processing.
			if (isDailyUpdate(conn))
			{
				return false;
			}

			// Preset data to be processed
			StorageSupportParameter[] param = (StorageSupportParameter[]) startParams;

			// Check for Worker code and password.
			if (!checkWorker(conn, param[0]))
			{
				return false;
			}

			// Check Automated Warehouse.
			for (int i = 0; i < param.length; i++)
			{
				LocateOperator locateOperator = new LocateOperator(conn);

				// Location
				String locationno = param[i].getDestLocationNo();

				// Check Location.
				if (!StringUtil.isBlank(locationno))
				{
					if (locateOperator.isAsrsLocation(locationno))
					{
						// 6023443=No.{0} The specified location is in automatic warehouse. Cannot enter.
						wMessage = "6023443" + wDelim + param[i].getRowNo();
						return false;
					}
				}
			}

			// Lock all the stock of the Relocation target in the preset lines together to prevent from deadlocking.
			if (!lockAll(conn, param))
			{
				// 6003006 = Unable to process this data. It has been updated via other work station.
				wMessage = "6003006";
				return false;
			}

			// Obtain Worker name from DmWorker.
			String workername = getWorkerName(conn, param[0].getWorkerCode());

			// Obtain the Work Date from WareNaviSystem.
			WareNaviSystemHandler warenaviHandler = new WareNaviSystemHandler(conn);
			WareNaviSystemSearchKey wsearchKey = new WareNaviSystemSearchKey();
			WareNaviSystem wms = (WareNaviSystem) warenaviHandler.findPrimary(wsearchKey);
			String workdate = getWorkDate(conn);

			// Maintain the JobNo that has been set.
			Vector jobNoVec = new Vector();
			// Work Quantity
			long workqty = 0;
			// Relocated qty (Total Bulk Qty)
			long moveQty = 0;
			// Inventory allocation class
			StockAllocateOperator stkAllocOperator = new StockAllocateOperator();
			// Complete Stock Relocation process class
			MovementCompleteOperator moveCompOperater = new MovementCompleteOperator();
			// Execute every process.
			for (int i = 0; i < param.length; i++)
			{
				// Set Work Date/Worker Name
				param[i].setWorkerName(workername);
				param[i].setWorkDate(workdate);

				// Set the terminal No.
				param[i].setTerminalNumber(param[0].getTerminalNumber());

				// Calculate the Relocated qty (Total Bulk Qty).
				moveQty = param[i].getEnteringQty() * param[i].getMovementCaseQty() + param[i].getMovementPieceQty();

				// Allocate the stock (picking process).
				// Lock the stocks of the Relocation source and the Relocation target.
				String[] location = { param[i].getSourceLocationNo(), param[i].getDestLocationNo()};
				stkAllocOperator.stockSearchForUpdate(conn, param[i].getConsignorCode(), location, param[i].getItemCode(), param[i].getUseByDate());
				// Maintain the ALLOCATIONQTY of the returned stock maintains "Allocated qty" for convenience.
				Stock[] stock =
					stkAllocOperator.stockMovementAllocate(
						conn,
						param[i].getConsignorCode(),
						param[i].getSourceLocationNo(),
						param[i].getItemCode(),
						param[i].getUseByDate(),
						moveQty,
						wProcessName);

				// Failing in allocation triggers error via other terminal.
				if (stock == null || stock.length == 0)
				{
					// 6023309=No.{0} Cannot relocate. The stock is updated by other terminal.
					wMessage = "6023309" + wDelim + param[i].getRowNo();
					return false;
				}

				// If inventory package is not available, set the information entered via screen in the Stock instance.
				if (!isStockPack(conn))
				{
					stock[0].setConsignorName(param[i].getConsignorName());
					stock[0].setItemName1(param[i].getItemName());
					stock[0].setEnteringQty(param[i].getEnteringQty());
				}

				// Generate a Relocation work status (picking process).
				Movement[] move = createMovement(conn, stock, param[i]);


				for (int j = 0; j < move.length; j++)
				{
					// Update the Relocation work status.(Storage Process)
					updateMovement(conn, move[j].getJobNo(), move[j].getPlanQty(), param[i], param[0].getWorkerName());

					// Execute the storage process for the stock (Update and generate the Stock/Result Information).
					if (!moveCompOperater.complete(conn, move[j].getJobNo(), wProcessName))
					{
						// 6023344=Cannot relocate. Stock Qty. in Destination Location will exceed {0}.
						wMessage = "6023344" + wDelim + MAX_STOCK_QTY_DISP;
						return false;
					}

					// Maintain the Work No. that has been set (for printing)
					jobNoVec.add(move[j].getJobNo());
				}

				// Count up the Work Quantity.
				workqty += moveQty;
				if (workqty > WmsParam.WORKER_MAX_TOTAL_QTY)
				{
					workqty = WmsParam.WORKER_MAX_TOTAL_QTY;
				}
			}
			if (workqty > 0)
			{
				// Add the Worker Result data inquiry table (DNWORKERRESULT).
				updateWorkerResult(
					conn,
					param[0].getWorkerCode(),
					workername,
					wms.getWorkDate(),
					param[0].getTerminalNumber(),
					Worker.JOB_TYPE_MOVEMENT_STORAGE,
					(int)workqty);
			}

			// 6021010=Data was committed.
			wMessage = "6021010";

			// If placing a check in the work list print option,
			if (param[0].getMoveWorkListFlag())
			{
				// execute the print process.
				startPrint(conn, jobNoVec);
			}

			return true;
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (LockTimeOutException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (OverflowException e)
		{
			// 6023344=Cannot relocate. Stock Qty. in Destination Location will exceed {0}.
			wMessage = "6023344" + wDelim + MAX_STOCK_QTY_DISP;
			return false;
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * Generate a Relocation work status.<BR>
	 * Allocating two or more stocks generates two or more records for a single aggregation Work No.<BR>
	 *
	 * @param conn Instance to maintain database connection.
	 * @param stock[] Allocated inventory information
	 * @param param StorageSupportParameter with updated information
	 * @return Movement[] Array of the generated Relocation work status
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected Movement[] createMovement(Connection conn, Stock stock[], StorageSupportParameter param) throws ReadWriteException
	{
		MovementHandler moveHandler = new MovementHandler(conn);
		MovementSearchKey moveKey = new MovementSearchKey();
		Movement[] resultMove = null;

		SequenceHandler sequence = new SequenceHandler(conn);
		String job_seqno = "";
		String collectJobNo = "";

		try
		{
			// Generate the Relocation work status for the allocated stock.
			for (int i = 0; i < stock.length; i++)
			{
				Movement move = new Movement();

				// Obtain the Work No.
                job_seqno = sequence.nextJobNo();
				// Set the same aggregation work No. for two or more records.
				if (i == 0)
					collectJobNo = job_seqno;

				move.setJobNo(job_seqno);
				move.setJobType(Movement.JOB_TYPE_MOVEMENT_STORAGE);
				move.setCollectJobNo(collectJobNo);
				move.setStockId(stock[i].getStockId());
				move.setAreaNo(stock[i].getAreaNo());
				move.setLocationNo(stock[i].getLocationNo());
				move.setStatusFlag(Movement.STATUSFLAG_UNSTART);
				move.setBeginningFlag(Movement.BEGINNING_FLAG_STARTED);
				move.setWorkDate(param.getWorkDate());
				move.setConsignorCode(stock[i].getConsignorCode());
				move.setConsignorName(stock[i].getConsignorName());
				move.setSupplierCode(stock[i].getSupplierCode());
				move.setSupplierName1(stock[i].getSupplierName1());
				move.setItemCode(stock[i].getItemCode());
				move.setItemName1(stock[i].getItemName1());
                // Require to set the this time allocated qty for ALLOCATIONQTY of the allocated inventory information.
				move.setPlanQty(stock[i].getAllocationQty());
				// Packed qty per Case: Set the input value.
				move.setEnteringQty(param.getEnteringQty());
				// Set the Packed qty per bundle.
				move.setBundleEnteringQty(stock[i].getBundleEnteringQty());
				move.setCasePieceFlag(stock[i].getCasePieceFlag());
				move.setWorkFormFlag(stock[i].getCasePieceFlag());
				move.setItf(stock[i].getItf());
				move.setBundleItf(stock[i].getBundleItf());
				move.setUseByDate(stock[i].getUseByDate());
				move.setLotNo(stock[i].getLotNo());
				move.setPlanInformation(stock[i].getPlanInformation());
				move.setRetrievalWorkerCode(param.getWorkerCode());
				move.setRetrievalWorkerName(param.getWorkerName());
				move.setRetrievalTerminalNo(param.getTerminalNumber());
				move.setRegistPname(wProcessName);
				move.setLastUpdatePname(wProcessName);

				moveHandler.create(move);
			}

			// Obtain the Relocation work status generated this time.
			if (!StringUtil.isBlank(collectJobNo))
			{
				moveKey.KeyClear();
				moveKey.setCollectJobNo(collectJobNo);
				resultMove = (Movement[]) moveHandler.find(moveKey);
			}

		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

		return resultMove;
	}

	/**
	 * Update the Relocation work status using the corresponding preset data.<BR>
	 * If no corresponding work No. found in the Relocation work status, throw ReadWriteException.
	 *
	 * @param conn Instance to maintain database connection.
	 * @param jobNo Work No. of Relocation work Status to be updated
	 * @param param Preset data to be processed
	 * @param workerName Worker Name
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void updateMovement(Connection conn, String jobNo, int moveQty, StorageSupportParameter param, String workerName) throws ReadWriteException
	{
		MovementHandler moveHandler = new MovementHandler(conn);
		MovementSearchKey moveKey = new MovementSearchKey();
		try
		{
			moveKey.setJobNo(jobNo);
			// Trigger error via other terminal if no corresponding work No.
			if (moveHandler.count(moveKey) != 1)
			{
				// 6023209=No.{0} The data has been updated via other terminal.
				wMessage = "6023209" + wDelim + param.getRowNo();
				throw new ReadWriteException("6023209" + wDelim + param.getRowNo());
			}

			MovementAlterKey moveAltKey = new MovementAlterKey();

			// Set the update condition.
			moveAltKey.setJobNo(jobNo);
			moveAltKey.updateStatusFlag(Movement.STATUSFLAG_COMPLETION);
			moveAltKey.updateResultQty(moveQty);
			moveAltKey.updateResultUseByDate(param.getUseByDate());
			moveAltKey.updateResultLocationNo(param.getDestLocationNo());
			moveAltKey.updateWorkerCode(param.getWorkerCode());
			moveAltKey.updateWorkerName(workerName);
			moveAltKey.updateTerminalNo(param.getTerminalNumber());
			moveAltKey.updateLastUpdatePname(wProcessName);

			// Update the Relocation work status.
			moveHandler.modify(moveAltKey);

		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

	}

	/**
	 * Receive the Work No. with Inventory relocated and then execute the process for printing it.<BR>
	 * Enable to refer to the print result using <CODE>getMessage()</CODE> method.<BR>
	 *
	 * @param conn Instance to maintain database connection.
	 * @param jobNoVec Work No. with Inventory relocated.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	protected void startPrint(Connection conn, Vector jobNoVec) throws ReadWriteException, ScheduleException
	{
		// Set the print condition input via screen for print process class.
		MovementWriter writer = new MovementWriter(conn);
		writer.setJobNo(jobNoVec);

		// Start the printing process.
		if (writer.startPrint())
		{
			// 6021012=Data had been set and the list was printed successfully.
			wMessage = "6021012";
		}
		else
		{
			// 6007042 = Printing failed after setup. Please refer to log.
			wMessage = "6007042";
		}

	}

	/**
	 * Receive the contents entered via screen as a parameter and check for input and exclusion in the preset area.
	 * @param conn Instance to maintain database connection.
	 * @param param RetrievalSupportParameter class instance with contents that were input via screen.
	 * @return Return true when no input error occurs, or return false when input error occurs.
	 * @throws ReadWriteException Throw exception if database error occurs
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 */
	protected boolean lockAll(Connection conn, Parameter[] checkParam) throws ReadWriteException, ScheduleException
	{
		try
		{
			StockSearchKey stockKey = new StockSearchKey();
			StockHandler stockHandler = new StockHandler(conn);
			StorageSupportParameter[] wParam = (StorageSupportParameter[]) checkParam;
			// Joint the conditions per one stock with "OR" and lock all the preset data.
			for (int i = 0 ; i < wParam.length ; i++)
			{
				// Set the search conditions.
				if (i == 0)
				{
					stockKey.setConsignorCode(wParam[i].getConsignorCode(), "=", "((", "", "AND");
				}
				else
				{
					stockKey.setConsignorCode(wParam[i].getConsignorCode(), "=", "(", "", "AND");
				}

				stockKey.setLocationNo(wParam[i].getSourceLocationNo(),"=","(","","OR");
				stockKey.setLocationNo(wParam[i].getDestLocationNo(),"=","",")","AND");

				// If expiry date control is enabled, include expiry date in the search conditions.
				if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
				{
					// Accept blank even if Expiry Date Control is enabled.
					stockKey.setUseByDate(wParam[i].getUseByDate(),"=", "","","AND");
				}
				if (i == wParam.length -1)
				{
					stockKey.setItemCode(wParam[i].getItemCode(), "=", "", "))", "AND");
				}
				else
				{
					stockKey.setItemCode(wParam[i].getItemCode(), "=", "", ")", "OR");
				}
			}

			// Require to be Center Inventory.
			stockKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			stockKey.setStockIdCollect();

			stockHandler.findForUpdate(stockKey);

			return true;
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	// Private methods -----------------------------------------------

	/**
	 * Set the Consignor Name / Item Name for the input data and return it.
	 * @param conn Database connection object
	 * @param param Input data
	 * @return Data to be displayed in the preset area.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private StorageSupportParameter[] addList(Connection conn, StorageSupportParameter param) throws ReadWriteException
	{
		// Returned data
		StorageSupportParameter[] resultParam = new StorageSupportParameter[1];

		// Inputting both Consignor Name and Item Name disables to search.
		if (!StringUtil.isBlank(param.getConsignorName()) && !StringUtil.isBlank(param.getItemName()))
		{
			resultParam[0] = param;
			return resultParam;
		}

		StockReportFinder nameFinder = new StockReportFinder(conn);
		StockSearchKey stockKey = new StockSearchKey();

		// Set the Search key.
		stockKey.setConsignorCode(param.getConsignorCode());
		stockKey.setItemCode(param.getItemCode());
		stockKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		stockKey.setStockQty(0, ">");
		stockKey.setConsignorNameCollect();
		stockKey.setItemName1Collect("");
		stockKey.setRegistDateOrder(1, false);

		// Execute the search.
		nameFinder.open();
		if (nameFinder.search(stockKey) > 0)
		{
			Stock nameStock[] = (Stock[]) nameFinder.getEntities(1);

			if (nameStock != null && nameStock.length != 0)
			{
				// Set the search result for the return value.
				if (StringUtil.isBlank(param.getConsignorName()))
					param.setConsignorName(nameStock[0].getConsignorName());
				if (StringUtil.isBlank(param.getItemName()))
					param.setItemName(nameStock[0].getItemName1());
			}
		}
		nameFinder.close();

		resultParam[0] = param;
		return resultParam;
	}

	/**
	 * Check the entered data.
	 * Check the input data for correctness and correspondence to the preset data.
	 *
	 * @param param Input data
	 * @param paramList Preset data
	 * @return Return whether the entered data is correct or not.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	 
	private boolean doCommonCheck(StorageSupportParameter param, StorageSupportParameter[] paramList) throws ReadWriteException
	{
		// Require the count of data to be displayed not to exceed the maximum count of data to be displayed.
		if (paramList != null && paramList.length == WmsParam.MAX_NUMBER_OF_DISP)
		{
			// 6023096 = More than {0} data exist. Data cannot be entered.
			wMessage = "6023096" + wDelim + MAX_NUMBER_OF_DISP_DISP;
			return false;
		}

		// Require to input in the Relocation Case qty or Relocation Piece qty field.
		if (param.getMovementCaseQty() <= 0 && param.getMovementPieceQty() <= 0)
		{
			// 6023244 = Pleas enter 1 or greater for the relocation case/piece quantity.
			wMessage = "6023244";
			return false;
		}

		// Disable to input in the Relocation Case qty, if the Packed qty per Case is 0.
		if (param.getEnteringQty() == 0 && param.getMovementCaseQty() > 0)
		{
			// 6023238 = Relocation case quantity cannot be entered when the packed quantity per case is 0.
			wMessage = "6023238";
			return false;
		}

		// Ensure that a single location does not contain both Relocation source location and Relocation target location together.
		if (param.getSourceLocationNo().equals(param.getDestLocationNo()))
		{
			// 6023249 = Source location No. of the relocation cannot be entered for destined location No.
			wMessage = "6023249";
			return false;
		}

		// Ensure not to input two or more data in the preset area.
		if (paramList != null)
		{
			for (int i = 0; i < paramList.length; i++)
			{
				if (paramList[i].getConsignorCode().equals(param.getConsignorCode())
					&& paramList[i].getItemCode().equals(param.getItemCode())
					&& paramList[i].getSourceLocationNo().equals(param.getSourceLocationNo())
					&& paramList[i].getDestLocationNo().equals(param.getDestLocationNo()))
				{
					// If expiry date control is enabled, add expiry date to the condition.
					if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY && !paramList[i].getUseByDate().equals(param.getUseByDate()))
					{
						continue;
					}

					// 6023090=The data already exists.
					wMessage = "6023090";
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * If inventory control package is available, obtain the stock and check the entered data.
	 * Check the input data for correctness and correspondence to the preset data.
	 * @param conn Database connection object
	 * @param param Input data
	 * @param paramList Preset data
	 * @return Return whether the entered data is correct or not.
	 * @throws ReadWriteException
	 * @throws ScheduleException
	 */
	private boolean doStockCheck(Connection conn, StorageSupportParameter param, StorageSupportParameter[] paramList) throws ReadWriteException, ScheduleException
	{
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey stockKey = new StockSearchKey();
		Stock[] stock = null;

		// Set the search conditions.
		stockKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		stockKey.setStockQty(0, ">");
		stockKey.setConsignorCode(param.getConsignorCode());
		stockKey.setItemCode(param.getItemCode());
		stockKey.setLocationNo(param.getSourceLocationNo());
		// If expiry date control is enabled, include expiry date in the search conditions.
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			stockKey.setUseByDate(param.getUseByDate());
		}
		// Obtain the stock qty and Allocated qty

		AreaOperator AreaOperator = new AreaOperator(conn);

		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;

		// Obtain the Area other than ASRS and add it to the search conditions.
		// Search for IS NULL if no corresponding area found.
		areaNo = AreaOperator.getAreaNo(areaType);
		stockKey.setAreaNo(areaNo);

		stockKey.setStockQtyCollect("");
		stockKey.setAllocationQtyCollect("");
		stock = (Stock[]) stockHandler.find(stockKey);

		// If there is no target stock,
		if (stock == null || stock.length == 0)
		{
			// 6023251={0} There is no target stock.
			wMessage = "6023251";
			return false;
		}

		// Obtain the Allocatable qty.
		long sumPossibleQty = 0;

		for (int i = 0; i < stock.length; i++)
		{
			// Sum up the allocatable qty.
			sumPossibleQty += stock[i].getAllocationQty();
		}

		// If Allocatable qty is smaller than total planned relocation qty,
		if (sumPossibleQty < (param.getEnteringQty() * param.getMovementCaseQty() + param.getMovementPieceQty()))
		{
			// 6023237=Please enter the allocation quantity or less for the relocation quantity.
			wMessage = "6023237";
			return false;
		}

		// Obtain the total qty of the same stock to be relocated from the preset data.
		if (paramList != null)
		{
			// Relocated qty of the same stock in the preset area.
			long sumListMoveQty = 0;

			// If expiry date control is enabled, include expiry date in the correspondence conditions.
			if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				for (int i = 0; i < paramList.length; i++)
				{
					if (paramList[i].getConsignorCode().equals(param.getConsignorCode())
						&& paramList[i].getItemCode().equals(param.getItemCode())
						&& paramList[i].getSourceLocationNo().equals(param.getSourceLocationNo())
						&& paramList[i].getUseByDate().equals(param.getUseByDate()))
					{
						// Obtain the Total Relocated qty.
						sumListMoveQty += paramList[i].getEnteringQty() * paramList[i].getMovementCaseQty() + paramList[i].getMovementPieceQty();
					}
				}
			}
			// Disabling Expiry Date Control disables to include Expiry date in the correspondence condition.
			else
			{
				for (int i = 0; i < paramList.length; i++)
				{
					if (paramList[i].getConsignorCode().equals(param.getConsignorCode())
						&& paramList[i].getItemCode().equals(param.getItemCode())
						&& paramList[i].getSourceLocationNo().equals(param.getSourceLocationNo()))
					{
						// Obtain the Total Relocated qty.
						sumListMoveQty += paramList[i].getEnteringQty() * paramList[i].getMovementCaseQty() + paramList[i].getMovementPieceQty();
					}
				}
			}

			// Trigger error if the Relocated qty is larger than the allocatable qty.
			if (sumPossibleQty < (sumListMoveQty + (param.getEnteringQty() * param.getMovementCaseQty() + param.getMovementPieceQty())))
			{
				// 6023237=Please enter the allocation quantity or less for the relocation quantity.
				wMessage = "6023237";
				return false;
			}
		}

		return true;
	}

	/**
	 * Check the input data if inventory control package is not available.
	 * Check the input data for correctness and correspondence to the preset data.
	 * @param conn Database connection object
	 * @param param Input data
	 * @param paramList Preset data
	 * @return Return whether the entered data is correct or not.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private boolean doNoStockCheck(Connection conn, StorageSupportParameter param, StorageSupportParameter[] paramList) throws ReadWriteException
	{
		// Check Overflow.
		if ((long) param.getEnteringQty() * (long) param.getMovementCaseQty() + (long) param.getMovementPieceQty() > WmsParam.MAX_STOCK_QTY)
		{
			// 6023058 = Please enter {1} or smaller for {0}.
			// LBL-W0120 = Relocated qty(Total Bulk Qty)
			wMessage = "6023058" + wDelim + DisplayText.getText("LBL-W0120") + wDelim + MAX_STOCK_QTY_DISP;
			return false;
		}
		return true;
	}
}
//end of class
