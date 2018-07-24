
package jp.co.daifuku.wms.storage.schedule;
//#CM322
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
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.storage.dbhandler.StorageInventoryCheckFinder;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;


//#CM323
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * This class allows to execute the process for submitting the Inventory Check.<BR>
 * Receive the contents entered via screen as a parameter and execute process for submitting the inventory check.<BR>
 * Each method in this class receives a connection object and executes the process for updating the database. <BR>
 * However, each method disables to commit and roll back of transactions. <BR>
 * This class executes the following processes. <BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   If only one Consignor Code exists in Inventory Check Work status, return the corresponding Consignor Code. <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist. <BR>
 *   <BR>
 *   [Search conditions] <BR><DIR>
 *   Process flag is Not Submitted <BR></DIR>
 *   <BR>
 * </DIR>
 * <BR>
 * 2.Process by clicking on Display button (<CODE>Query()</CODE> Method)<BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it.<BR>
 *   Return <CODE>Parameter</CODE> array with the number of elements 0 if no corresponding data found. Or, return null when condition error occurs.<BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR><DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Consignor code* <BR>
 *     Start Location No. <BR>
 *     End Location No. <BR></DIR>
 * <BR>
 *   <Returned data> <BR><DIR>
 *     Consignor code <BR>
 *     Consignor name <BR>
 *     Location No. <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Packed qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Inventory Check Case Qty (Inventory Check Inventory Qty/Packed qty per Case) <BR>
 *     Inventory Check Piece Qty(Inventory Check Inventory Qty%Packed qty per Case) <BR>
 *     Stock Case Qty(Inventory Qty/Packed qty per Case) <BR>
 *     stock piece qty(Inventory Qty%Packed qty per Case) <BR>
 *     Expiry Date <BR>
 *     Worker Code <BR>
 *     Worker Name <BR>
 *     Last update date/time <BR></DIR>
 * <BR>
 *   <Display order> <BR><DIR>
 *     Ascending order of Location No., Ascending order of item code, Ascending order of expiry date. <BR></DIR>
 * <BR>
 *   <Inventory Check Work Status search conditions>
 *       Consistent with the entered Consignor Code.
 *       Within the range between entered Start Location No. and End Location No.
 *       Data only with Status flag Not Submitted <BR></DIR>
 * <BR>
 * 3.Process by clicking on Submit Inventory Check Result button (<CODE>startSCHgetParams()</CODE>Method) <BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter and start-up the processes for updating the mandatory info and list. <BR>
 *   Obtain the data to be output in the preset area from the database after this process normally completed, and return it. <BR>
 *   Return null when condition error or excusive error occurs. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Start Location No. <BR>
 *     End Location No. <BR>
 *     Consignor code* <BR>
 *     Consignor name* <BR>
 *     Location No.* <BR>
 *     Item Code* <BR>
 *     Item Name* <BR>
 *     Packed qty per Case* <BR>
 *     Packed qty per bundle* <BR>
 *     Stock Case Qty* <BR>
 *     stock piece qty* <BR>
 *     Inventory Check Case Qty* <BR>
 *     Inventory Check Piece Qty* <BR>
 *     Expiry Date* <BR>
 *     Preset Line No* <BR>
 *     Terminal No.* <BR>
 *     Last update date/time* <BR></DIR>
 * <BR>
 *   <Returned data> <BR><DIR>
 *     Consignor code <BR>
 *     Consignor name <BR>
 *     Location No. <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Packed qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Inventory Check Case Qty (Inventory Check Inventory Qty/Packed qty per Case) <BR>
 *     Inventory Check Piece Qty(Inventory Check Inventory Qty%Packed qty per Case) <BR>
 *     Stock Case Qty(Inventory Qty/Packed qty per Case) <BR>
 *     stock piece qty(Inventory Qty%Packed qty per Case) <BR>
 *     Expiry Date <BR>
 *     Worker Code <BR>
 *     Worker Name <BR></DIR>
 * <BR>
 *   <Contents of check for mandatory input> <BR><DIR>
 *     <inventory information existing> <BR><DIR>
 *       1.Accept only value 0 or blank in the inventory Case qty if the Packed qty per Case is not more than 0. <BR>
 *       2.Ensure that the inventory check qty (total) is 0 or more. <BR>
 *       3.Require to correspond the total stock qty of the parameter (total) to the total stock qty in the inventory information. (exclusion check) <BR></DIR>
 *     <No inventory information> <BR><DIR>
 *       1.Accept only value 0 or blank in the inventory Case qty if the Packed qty per Case is not more than 0. <BR>
 *       2.Ensure that the Inventory Check qty (total) is 1 or larger. <BR>
 *       3.Ensure that the stock qty of the parameter (total) is 0 (zero).(Check Exclusion.) <BR>
 *       4.Ensure to input Item Code and Location No. <BR></DIR>
 * <BR></DIR>
 *   <Update Process> <BR><DIR>
 *     -Update of Inventory Check Work Status table (DNINVENTORYCHECK). <BR><DIR>
 *       Update the process flag to Already Submitted. <BR></DIR>
 *     <No inventory information> <BR><DIR>
 *       -Add Inventory Information Table (DMSTOCK). <BR><DIR>
 *         Create a new inventory information by Case / Piece based on the contents in the parameter. <BR></DIR>
 *       -Add Sending Result Info Table (DNHOSTSEND) <BR><DIR>
 *         Create a new sending Result Info based on the updated inventory information.  <BR></DIR>
 *       -Add the Worker Result data inquiry table (DNWORKERRESULT) to update <BR><DIR>
 *         Add the Worker Result data inquiry based on the added inventory information and update it. <BR></DIR>
 *     </DIR>
 *     <inventory information existing> <BR><DIR>
 *       -Update Inventory Information Table (DMSTOCK). <BR><DIR>
 *         <Increase in the quantity> <BR><DIR>
 *           Obtain the inventory information using the following condition, and sum up the stock quantity of the target inventory information (only increment) <BR>
 *           -Searching/loading condition <BR><DIR>
 *             Concordant with the Consignor Code / Location No. / Item Code / Expiry Date <BR>
 *             Descending order of Case/Piece division. <BR></DIR>
 *         </DIR>
 *         <Decrease in the quantity> <BR><DIR>
 *           Obtain the inventory information by applying the following condition, and decrease the Stock Qty in the target inventory information. <BR>
 *           -Searching/loading condition <BR><DIR>
 *             Concordant with the Consignor Code / Location No. / Item Code / Expiry Date <BR>
 *             Descending order of Case/Piece division. <BR></DIR>
 *         </DIR>
 *       <BR></DIR>
 *       -Add Sending Result Info Table (DNHOSTSEND) <BR><DIR>
 *         Create a new sending Result Info based on the updated inventory information. <BR></DIR>
 *       -Add the Worker Result data inquiry table (DNWORKERRESULT) to update <BR><DIR>
 *         Add the Worker Result data inquiry based on the updated inventory information and update it. <BR></DIR>
 *     </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/12</TD><TD>C.Kaminishizono</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/11 06:24:56 $
 * @author  $Author: suresh $
 */
public class InventoryCompleteSCH extends AbstractStorageSCH
{

	//#CM324
	// Class variables -----------------------------------------------
	//#CM325
	/**
	 * Class Name(Inventory Check)
	 */
	public static String wProcessName = "InventoryCompleteSCH";

	//#CM326
	/**
	 * Inventory Check Work Status search class
	 */
	private InventoryCheckHandler wInventoryHandler = null;

	//#CM327
	/**
	 * Inventory Check Work Status search key
	 */
	private InventoryCheckSearchKey wInventoryKey = null;

	//#CM328
	// Class method --------------------------------------------------
	//#CM329
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/11 06:24:56 $");
	}
	//#CM330
	// Constructors --------------------------------------------------
	//#CM331
	/**
	 * Initialize this class.
	 */
	public InventoryCompleteSCH()
	{
		wMessage = null;
	}

	//#CM332
	// Public methods ------------------------------------------------

	//#CM333
	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * If only one Consignor Code exists in Inventory Check Work status, return the corresponding Consignor Code.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.<BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.<BR>
	 * @param conn Database connection object
	 * @param locale <CODE>Locale</CODE> object for which Area Code is set.
	 * @param searchParam This Class inherits the <CODE>Parameter</CODE> class with search conditions
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		InventoryCheckHandler invcheckHandler = new InventoryCheckHandler(conn);
		InventoryCheckSearchKey invsearchKey = new InventoryCheckSearchKey();
		StorageSupportParameter param = new StorageSupportParameter();

		try
		{
			//#CM334
			// Search for the inventory check work status (Consignor code).
			//#CM335
			// Process flag(Not Submitted)
			invsearchKey.KeyClear();
			invsearchKey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
			invsearchKey.setConsignorCodeGroup(1);
			invsearchKey.setConsignorCodeCollect("");

			if (invcheckHandler.count(invsearchKey) == 1)
			{
				//#CM336
				// Search for the Consignor Code.
				InventoryCheck wInvCheck = (InventoryCheck) invcheckHandler.findPrimary(invsearchKey);

				//#CM337
				// Set the search result for the return value.
				param.setConsignorCode(wInvCheck.getConsignorCode());
			}

		}
		catch (NoPrimaryException pe)
		{
			return param;
		}
		return param;
	}

	//#CM338
	/**
	 * Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it. <BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ". <BR>
	 * Invoking this process in unavailable case throws <CODE>ScheduleException</CODE>. <BR>
	 * @param conn Instance to maintain database connection.
	 * @param locale Use this to obtain the Area Code and a value localized to display.
	 * @param searchParam Instance of <CODE>StorageSupportParameter</CODE> class with conditions to obtain the display data.
	 *         Designating any instance other than <CODE>StorageSupportParameter</CODE> throws ScheduleException.
	 * @return Array of <CODE>StorageSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record found, return the array of the number of elements equal to 0.<BR>
	 *          Return null when input condition error occurs.<BR>
	 *          Returning null allows the <CODE>getMessage()</CODE> method to obtain the error content as a message.<BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		StorageSupportParameter sparam = (StorageSupportParameter) searchParam;

		InventoryCheckHandler invcheckHandler = new InventoryCheckHandler(conn);
		InventoryCheckSearchKey invsearchKey = new InventoryCheckSearchKey();
		InventoryCheckSearchKey namesearchKey = new InventoryCheckSearchKey();

		if (!checkWorker(conn, sparam))
		{
			return new StorageSupportParameter[0];
		}

		invsearchKey.KeyClear();
		namesearchKey.KeyClear();

		//#CM339
		// Consignor code
		if (!StringUtil.isBlank(sparam.getConsignorCode()))
		{
			invsearchKey.setConsignorCode(sparam.getConsignorCode());
			namesearchKey.setConsignorCode(sparam.getConsignorCode());
		}

		//#CM340
		// Start Location No.
		if (!StringUtil.isBlank(sparam.getFromLocation()))
		{
			invsearchKey.setLocationNo(sparam.getFromLocation(), ">=");
			namesearchKey.setLocationNo(sparam.getFromLocation(), ">=");
		}

		//#CM341
		// End Location No.
		if (!StringUtil.isBlank(sparam.getToLocation()))
		{
			invsearchKey.setLocationNo(sparam.getToLocation(), "<=");
			namesearchKey.setLocationNo(sparam.getToLocation(), "<=");
		}

		//#CM342
		// Data only with Process flag Not Submitted
		invsearchKey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
		namesearchKey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);

		//#CM343
		// Obtain the count.
		if (!canLowerDisplay(invcheckHandler.count(invsearchKey)))
		{
			return returnNoDisplayParameter();
		}

		invsearchKey.setLocationNoOrder(1, true);
		invsearchKey.setItemCodeOrder(2, true);
		invsearchKey.setUseByDateOrder(3, true);
		//#CM344
		// Obtain the target info.
		InventoryCheck[] wInvCheck = (InventoryCheck[]) invcheckHandler.find(invsearchKey);

		//#CM345
		// Obtain the Consignor Name.
		String consignorname = "";
		namesearchKey.setConsignorNameCollect("");
		namesearchKey.setRegistDateOrder(1, false);

		StorageInventoryCheckFinder consignorFinder = new StorageInventoryCheckFinder(conn);
		consignorFinder.open();
		int nameCount = ((StorageInventoryCheckFinder) consignorFinder).search(namesearchKey);
		if (nameCount > 0 && nameCount <= WmsParam.MAX_NUMBER_OF_DISP_LISTBOX)
		{
			InventoryCheck winfo[] = (InventoryCheck[]) ((StorageInventoryCheckFinder) consignorFinder).getEntities(0, 1);

			if (winfo != null && winfo.length != 0)
			{
				consignorname = winfo[0].getConsignorName();
			}
		}
		consignorFinder.close();

		Vector vec = new Vector();

		for (int lc = 0; lc < wInvCheck.length; lc++)
		{
			StorageSupportParameter wparam = new StorageSupportParameter();

			//#CM346
			// Compile it via Inventory Check work status.
			//#CM347
			// Consignor code
			wparam.setConsignorCode(wInvCheck[lc].getConsignorCode());
			//#CM348
			// Consignor name
			wparam.setConsignorName(consignorname);
			//#CM349
			// Location No.
			wparam.setLocation(wInvCheck[lc].getLocationNo());
			//#CM350
			// Item Code
			wparam.setItemCode(wInvCheck[lc].getItemCode());
			//#CM351
			// Item Name
			wparam.setItemName(wInvCheck[lc].getItemName1());
			//#CM352
			// Packed qty per Case
			wparam.setEnteringQty(wInvCheck[lc].getEnteringQty());
			//#CM353
			// Packed qty per bundle
			wparam.setBundleEnteringQty(wInvCheck[lc].getBundleEnteringQty());
			//#CM354
			// Inventory Check Case Qty
			wparam.setInventoryCheckCaseQty(DisplayUtil.getCaseQty(wInvCheck[lc].getResultStockQty(), wInvCheck[lc].getEnteringQty(),
					wInvCheck[lc].getCasePieceFlag()));
			//#CM355
			// Inventory Check Piece Qty(
			wparam.setInventoryCheckPieceQty(DisplayUtil.getPieceQty(wInvCheck[lc].getResultStockQty(), wInvCheck[lc].getEnteringQty(),
					wInvCheck[lc].getCasePieceFlag()));
			//#CM356
			// Stock Case Qty
			wparam.setStockCaseQty(DisplayUtil.getCaseQty(wInvCheck[lc].getStockQty(), wInvCheck[lc].getEnteringQty(),
					wInvCheck[lc].getCasePieceFlag()));
			//#CM357
			// stock piece qty
			wparam.setStockPieceQty(DisplayUtil.getPieceQty(wInvCheck[lc].getStockQty(), wInvCheck[lc].getEnteringQty(),
					wInvCheck[lc].getCasePieceFlag()));
			//#CM358
			// Expiry Date
			wparam.setUseByDate(wInvCheck[lc].getUseByDate());
			//#CM359
			// Worker Code
			wparam.setWorkerCode(wInvCheck[lc].getWorkerCode());
			//#CM360
			// Worker Name
			wparam.setWorkerName(wInvCheck[lc].getWorkerName());
			//#CM361
			// Last update date/time
			wparam.setLastUpdateDate(wInvCheck[lc].getLastUpdateDate());
			//#CM362
			// Work No.
			wparam.setJobNo(wInvCheck[lc].getJobNo());

			//#CM363
			// Store it in the VECTOR area.
			vec.addElement(wparam);
		}

		//#CM364
		// Parameter area for return
		StorageSupportParameter[] rparam = new StorageSupportParameter[vec.size()];
		vec.copyInto(rparam);

		//#CM365
		// 6001013 = Data is shown.
		wMessage = "6001013";

		return rparam;
	}

	//#CM366
	/**
	 * Receive the contents entered via screen as a parameter and start-up the processes for updating the mandatory info and list. <BR>
	 * Obtain the data to be output in the preset area from the database after this process normally completed, and return it. <BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ". <BR>
	 * Invoking this process in unavailable case throws <CODE>ScheduleException</CODE>. <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{

		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey stocksearchKey = new StockSearchKey();
		StockAlterKey stockalterKey = new StockAlterKey();
		Stock[] wStock = null;
		int workqty_plus = 0;
		int workqty_minus = 0;

		try
		{
			//#CM367
			// Check Daily Maintenance Processing
			if (isDailyUpdate(conn))
			{
				wMessage = "6023321";
				return null;
			}

			//#CM368
			// Check the Worker code and password
			StorageSupportParameter workparam = (StorageSupportParameter) startParams[0];
			if (!checkWorker(conn, workparam))
			{
				return null;
			}

			//#CM369
			// Lock all the stock of the Relocation target in the preset lines together to prevent from deadlocking.
			if (!this.lockAll(conn, startParams))
			{
				//#CM370
				// 6003006 = Unable to process this data. It has been updated via other work station.
				wMessage = "6003006";
				return null;
			}

			//#CM371
			// Worker Code
			String workercode = workparam.getWorkerCode();
			//#CM372
			// Obtain the Worker name.
			String workername = getWorkerName(conn,workparam.getWorkerCode());

			//#CM373
			// Terminal No.
			String terminalno = workparam.getTerminalNumber();

			//#CM374
			// Update key for Inventory Check Work Status
			InventoryCheckAlterKey invalterKey = new InventoryCheckAlterKey();
			//#CM375
			// Initialize Inventory Check Work Status Search Class.
			setInventoryCheckHandler(conn);

			for (int lc = 0; lc < startParams.length; lc++)
			{
				StorageSupportParameter sparam = (StorageSupportParameter) startParams[lc];

				//#CM376
				// Check Mandatory Input.
				//#CM377
				// Consignor Code / Item Code / Location No. / Packed qty per Case
				if (StringUtil.isBlank(workparam.getConsignorCode())
				|| StringUtil.isBlank(sparam.getItemCode())
				|| StringUtil.isBlank(sparam.getLocation())
				|| (sparam.getInventoryCheckCaseQty() > 0 && sparam.getEnteringQty() == 0))
				{
					throw (new ScheduleException("mandatory error!"));
				}

				//#CM378
				// Check for exclusion of the Inventory Check work status.
				InventoryCheck wInvCheck = lock(sparam.getJobNo(), sparam.getLastUpdateDate());
				if(wInvCheck == null)
				{
					//#CM379
					// 6023209 = No.{0} The data has been updated via other terminal.
					wMessage = "6023209" + wDelim + sparam.getRowNo();
					return null;
				}

				//#CM380
				// Obtain the inventory information.  (exclusive control)
				//#CM381
				// Concordant with the Consignor Code / Location No. / Item Code / Expiry Date
				//#CM382
				// Data only with status flag Center Stock
				stocksearchKey.KeyClear();
				stocksearchKey.setConsignorCode(workparam.getConsignorCode());
				stocksearchKey.setLocationNo(sparam.getLocation());
				stocksearchKey.setItemCode(sparam.getItemCode());
				//#CM383
				// If expiry date control is enabled, include expiry date in the search conditions.
				if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
				{
					stocksearchKey.setUseByDate(sparam.getUseByDate());
				}
				stocksearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
				stocksearchKey.setCasePieceFlagOrder(1, false);
				wStock = (Stock[]) stockHandler.findForUpdate(stocksearchKey);

				//#CM384
				// Maintain the Sum of stock qty.
				int nStockQty = 0;
				//#CM385
				// Maintain the accumulation of the Allocatable qty.
				int nAllocationQty = 0;
				for (int slc = 0; slc < wStock.length; slc++)
				{
					nStockQty += wStock[slc].getStockQty();
					nAllocationQty += wStock[slc].getAllocationQty();

				}

				//#CM386
				// If no inventory information exists
				if (wStock.length == 0)
				{
					//#CM387
					// Check Input Value
					if ((sparam.getInventoryCheckCaseQty() * sparam.getEnteringQty() + sparam.getInventoryCheckPieceQty() <= 0))
					{
						//#CM388
						// 6023313 = No.{0} Enter 1 or more value in Inventory Check Case Qty./Inventory Check Piece Qty. field.
						wMessage = "6023313" + wDelim + sparam.getRowNo();
						return null;
					}
				}
				//#CM389
				// If inventory information existing.
				else
				{
					//#CM390
					// Check for change or modification to the stock qty since the Inventory Check input.
					if ((sparam.getStockCaseQty() * sparam.getEnteringQty() + sparam.getStockPieceQty()) != nStockQty)
					{
						//#CM391
						// 6023316 = No.{0} Stock qty. was changed after the input of Inventory Check. (Enter Inventory Check again.)
						wMessage = "6023316" + wDelim + sparam.getRowNo();
						return null;
					}

					if ((sparam.getInventoryCheckCaseQty() * sparam.getEnteringQty() + sparam.getInventoryCheckPieceQty()) < (nStockQty - nAllocationQty))
					{
						//#CM392
						// 6023314 = No.{0} Enter {2} or more value in {1} field.
						//#CM393
						// LBL-W0395=Inventory Check Qty(Total Bulk Qty) LBL-W0396=allocated qty
						wMessage = "6023314" + wDelim + sparam.getRowNo() + wDelim + DisplayText.getText("LBL-W0395") + wDelim + DisplayText.getText("LBL-W0396");
						return null;
					}
				}

				//#CM394
				// Check the input qty for overflow.
				if ((sparam.getInventoryCheckCaseQty() * sparam.getEnteringQty() + sparam.getInventoryCheckPieceQty() > WmsParam.MAX_STOCK_QTY))
				{
					//#CM395
					// 6023315 = No.{0} Enter {2} or less value in {1} field.
					//#CM396
					// LBL-W0395=Inventory Check Qty(Total Bulk Qty) Example:{2}=999,999,999
					wMessage = "6023315" + wDelim + sparam.getRowNo() + wDelim + DisplayText.getText("LBL-W0395") + wDelim + MAX_STOCK_QTY_DISP;
					return null;
				}

				//#CM397
				// Update the Inventory Check work status.
				//#CM398
				// Update the Process flag to Submitted.
				//#CM399
				// Update Terminal No.
				//#CM400
				// Update of last Update process name
				invalterKey.KeyClear();
				invalterKey.setJobNo(sparam.getJobNo());
				invalterKey.updateStatusFlag(InventoryCheck.STATUS_FLAG_DECISION);
				invalterKey.updateTerminalNo(terminalno);
				invalterKey.updateLastUpdatePname(wProcessName);
				wInventoryHandler.modify(invalterKey);

				if (wStock.length <= 0)
				{
					//#CM401
					// If no inventory information found, execute process for adding the inventory information.
					//#CM402
					// Add the stock by dividing it into Case stock and Piece stock.
					int wCaseQty = (DisplayUtil.getCaseQty(wInvCheck.getResultStockQty(),
							wInvCheck.getEnteringQty())) * wInvCheck.getEnteringQty();
					if (wCaseQty > 0)
					{
						//#CM403
						// Add the Case inventory information.
						if (!createStock(conn, sparam, wInvCheck, wCaseQty, Stock.CASEPIECE_FLAG_CASE,
								workercode, workername, getWorkDate(conn), terminalno))
							return null;
						//#CM404
						// To update the Worker result update, add up the work qty that has done this time.
						workqty_plus = addWorkQty(workqty_plus,wCaseQty);
					}

					//#CM405
					// Inventory Check Piece Qty(Inventory Check Inventory Qty%Packed qty per Case)
					int wPieceQty = DisplayUtil.getPieceQty(wInvCheck.getResultStockQty(), wInvCheck.getEnteringQty());
					if (wPieceQty > 0)
					{
						//#CM406
						// Add the Piece inventory information.
						if (!createStock(conn, sparam, wInvCheck, wPieceQty, Stock.CASEPIECE_FLAG_PIECE, workercode,
								workername, getWorkDate(conn), terminalno))
							return null;
						//#CM407
						// To update the Worker result update, add up the work qty that has done this time.
						workqty_plus = addWorkQty(workqty_plus,wPieceQty);
					}

					//#CM408
					// Update the location data of the added location.
					LocateOperator lOperator = new LocateOperator(conn);
					lOperator.modifyLocateStatus(wInvCheck.getLocationNo(), wProcessName);

				}
				else
				{
					//#CM409
					// Execute the process for updating the difference from Inventory Check work status, if inventory information exists.

					//#CM410
					// If the Inventory Check Inventory Qty is 0, delete the corresponding inventory information.
					if (wInvCheck.getResultStockQty() <= 0)
					{
						for (int i = 0; i < wStock.length; i++)
						{
							stocksearchKey.KeyClear();
							stocksearchKey.setStockId(wStock[i].getStockId());
							stockHandler.drop(stocksearchKey);
						}

						//#CM411
						// Update the Location info of the deleted location.
						LocateOperator lOperator = new LocateOperator(conn);
						lOperator.modifyLocateStatus(wInvCheck.getLocationNo(), wProcessName);
					}
					//#CM412
					// If increased in the stock or replenished to 0 stock:
					else if (nStockQty <= wInvCheck.getResultStockQty() || nStockQty == 0)
					{
						int addStockQty = wInvCheck.getResultStockQty() - nStockQty;

						//#CM413
						// Sum up the increase into the leading inventory information.
						stockalterKey.KeyClear();
						stockalterKey.setStockId(wStock[0].getStockId());

						stockalterKey.updateStockQty(wStock[0].getStockQty() + addStockQty);
						stockalterKey.updateAllocationQty(wStock[0].getAllocationQty() + addStockQty);
						stockalterKey.updateLastUpdatePname(wProcessName);
						//#CM414
						// Replenishing to 0 stock updates the Consignor Name, Item Name, Case / Packed qty per bundle, ITF, Expiry Date, and Storage Date.
						if (nStockQty == 0)
						{
							stockalterKey.updateConsignorName(wInvCheck.getConsignorName());
							stockalterKey.updateItemName1(wInvCheck.getItemName1());
							stockalterKey.updateEnteringQty(wInvCheck.getEnteringQty());
							stockalterKey.updateBundleEnteringQty(wInvCheck.getBundleEnteringQty());
							stockalterKey.updateItf(wInvCheck.getItf());
							stockalterKey.updateBundleItf(wInvCheck.getBundleItf());
							stockalterKey.updateUseByDate(wInvCheck.getUseByDate());
							stockalterKey.updateInstockDate(new Date());
						}

						stockHandler.modify(stockalterKey);

						//#CM415
						// Update the updated location in the location info.
						LocateOperator lOperator = new LocateOperator(conn);
						lOperator.modifyLocateStatus(wStock[0].getLocationNo(), wProcessName);

						//#CM416
						// Execute the process for adding the Host sending Information.(Increase in Inventory Check)
						if (!createResult(conn,
							wStock[0],
							wInvCheck.getJobNo(),
							workercode,
							workername,
							getWorkDate(conn),
							terminalno,
							SystemDefine.JOB_TYPE_INVENTORY_PLUS,
							wProcessName,
							" ",
							Stock.TCDC_FLAG_DC,
							addStockQty))
							return null;

						//#CM417
						// To update the Worker result update, add up the work qty that has done this time.
						workqty_plus = addWorkQty(workqty_plus,addStockQty);
					}
					//#CM418
					// If decreased in the stock:
					else
					{
						//#CM419
						// Maintain the possible decrease qty.
						int subStockQty = nStockQty - wInvCheck.getResultStockQty();

						for (int ulc = 0; ulc < wStock.length; ulc++)
						{
							//#CM420
							// Close the loop when the decrease process completed.
							if (subStockQty <= 0)
								break;

							//#CM421
							// Calculate the Decreased qty for this stock
							int allocatableQty = wStock[ulc].getAllocationQty();
							int modStockQty = 0;
							//#CM422
							// If the possible decrease qty is smaller than Allocatable qty:
							//#CM423
							// Make the decrease qty to be processed at this time equal to the possible decrease qty. Accordingly, make the possible decrease qty to 0 (zero).
							if (allocatableQty >= subStockQty)
							{
								modStockQty = subStockQty;
								subStockQty = 0;
							}
							else
							{
								modStockQty = allocatableQty;
								subStockQty -= allocatableQty;
							}

							//#CM424
							// Decrease possible qty of the stock qty in the inventory information.
							stockalterKey.KeyClear();
							stockalterKey.setStockId(wStock[ulc].getStockId());

							stockalterKey.updateStockQty(wStock[ulc].getStockQty() - modStockQty);
							stockalterKey.updateAllocationQty(wStock[ulc].getAllocationQty() - modStockQty);
							stockalterKey.updateLastUpdatePname(wProcessName);

							stockHandler.modify(stockalterKey);

							//#CM425
							// Update the location info of the decreased location.
							LocateOperator lOperator = new LocateOperator(conn);
							lOperator.modifyLocateStatus(wStock[ulc].getLocationNo(), wProcessName);

							//#CM426
							// Execute the process for adding the Host sending info ( Decrease Inventory Check).
							if (!createResult(conn,
								wStock[ulc],
								wInvCheck.getJobNo(),
								workercode,
								workername,
								getWorkDate(conn),
								terminalno,
								SystemDefine.JOB_TYPE_INVENTORY_MINUS,
								wProcessName,
								" ",
								Stock.TCDC_FLAG_DC,
								modStockQty))
								return null;

							//#CM427
							// To update the Worker result update, add up the work qty that has done this time.
							workqty_minus = addWorkQty(workqty_minus,modStockQty);
						}
						//#CM428
						// Failings to decrease from the inventory information resets the error.
						if (subStockQty > 0)
						{
							//#CM429
							// 6023316 = No.{0} Stock qty. was changed after the input of Inventory Check. (Enter Inventory Check again.)
							wMessage = "6023316" + wDelim + sparam.getRowNo();
							return null;
						}
					}
				}
			}

			//#CM430
			// Add or update the Worker by work division.
			if (workqty_plus >= 0)
			{
				updateWorkerResult(conn, workercode, workername, getWorkDate(conn), terminalno, SystemDefine.JOB_TYPE_INVENTORY_PLUS, workqty_plus);
			}
			if (workqty_minus > 0)
			{
				updateWorkerResult(conn, workercode, workername, getWorkDate(conn), terminalno, SystemDefine.JOB_TYPE_INVENTORY_MINUS, workqty_minus);
			}

			//#CM431
			// Obtain the data to re-display the succeeded schedule.
			StorageSupportParameter[] viewParam = (StorageSupportParameter[]) this.query(conn, workparam);

			//#CM432
			// 6001014 = Completed.
			wMessage = "6001014";

			return viewParam;

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

	//#CM433
	// Package methods -----------------------------------------------

	//#CM434
	// Protected methods ---------------------------------------------

	//#CM435
	// Private methods -----------------------------------------------

	//#CM436
	/**
	 * Initialize the Inventory Check work status search class.
	 */
	private void setInventoryCheckHandler(Connection conn)
	{
		wInventoryHandler = new InventoryCheckHandler(conn);
		wInventoryKey = new InventoryCheckSearchKey();
	}

	//#CM437
	/**
	 * Search for the Inventory Check work status to be updated and lock it.<BR>
	 * Invoke a method to generate instance before invoking this method.<BR>
	 * Return true if successfully searched and locked the data to be updated. Return false if failed to search due to error via other terminal.<BR>
	 * Regard as error via other terminal, if:<BR>
	 * <DIR>
	 *   -no result obtained from search through the Inventory Check work status; or<BR>
	 *   -the searched data was obtained from search through the Inventory Check work status but it has a different update date/time from the last update date/time shown in the screen.
	 * </DIR>
	 *
	 * @param pJobNo Update the Work No.
	 * @param pLastUpdateDate Update the Last update date/time.
	 * @return  return the result whether successfully searched the data to be updated and locked it
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected InventoryCheck lock(String pJobNo, Date pLastUpdateDate) throws ReadWriteException
	{
		//#CM438
		// Search for the Work status.
		wInventoryKey.KeyClear();
		wInventoryKey.setJobNo(pJobNo);
		InventoryCheck inventory = null;
		try
		{
			inventory = (InventoryCheck) wInventoryHandler.findPrimaryForUpdate(wInventoryKey);
		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		//#CM439
		// No corresponding data exists in the info (updated via other work station)
		if (inventory == null)
			return null;

		//#CM440
		// The last update date/time of shipping Plan info is not consistent (updated at other work station).
		if (!inventory.getLastUpdateDate().equals(pLastUpdateDate))
			return null;

		return inventory;

	}

	//#CM441
	/**
	 * Add the inventory information table (DMSTOCK). <BR>
	 * <BR>
	 * Add the inventory information based on the contents of the Received parameter. <BR>
	 * <BR>
	 * @param conn        Instance to maintain database connection.
	 * @param wparam      Input info
	 * @param vinvcheck   InventoryCheck(Inventory Check Work Status) class Instance
	 * @param stockqty    Stock Quantity
	 * @param cpflag      Case/Piece division
	 * @param workercode  Worker Code
	 * @param workername  Worker Name
	 * @param sysdate     Work Date ( System defined date)
	 * @param terminalno  Terminal No.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected boolean createStock(
		Connection conn,
		StorageSupportParameter wparam,
		InventoryCheck vinvcheck,
		int stockqty,
		String cpflag,
		String workercode,
		String workername,
		String sysdate,
		String terminalno)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			String wJobNo = vinvcheck.getJobNo();

			StockHandler stockHandler = new StockHandler(conn);
			Stock rStock = new Stock();

			SequenceHandler sqObj = new SequenceHandler(conn);
            //#CM442
            //Sequence
            String stockId_seqno = sqObj.nextStockId();
			//#CM443
			// Stock ID
			rStock.setStockId(stockId_seqno);
			//#CM444
			// Plan unique key
			rStock.setPlanUkey("");
			//#CM445
			// Area No
			LocateOperator lOperator = new LocateOperator(conn);
			rStock.setAreaNo(lOperator.getAreaNo(vinvcheck.getLocationNo()));
			//#CM446
			// Location No
			rStock.setLocationNo(vinvcheck.getLocationNo());
			//#CM447
			// Item Code
			rStock.setItemCode(vinvcheck.getItemCode());
			//#CM448
			// Item Name
			rStock.setItemName1(vinvcheck.getItemName1());
			//#CM449
			// Status flag ( Center Inventory)
			rStock.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM450
			// Stock Quantity
			rStock.setStockQty(stockqty);
			//#CM451
			// Allocatable Qty
			rStock.setAllocationQty(stockqty);
			//#CM452
			// Planned Qty
			rStock.setPlanQty(0);
			//#CM453
			// Case/Piece division
			rStock.setCasePieceFlag(cpflag);
			//#CM454
			// Storage Date/Time
			rStock.setInstockDate(new Date());
			//#CM455
			// Last shipping date
			rStock.setLastShippingDate("");
			//#CM456
			// Expiry Date
			rStock.setUseByDate(vinvcheck.getUseByDate());
			//#CM457
			// Lot No.
			rStock.setLotNo("");
			//#CM458
			// Plan information Comment
			rStock.setPlanInformation("");
			//#CM459
			// Consignor code
			rStock.setConsignorCode(vinvcheck.getConsignorCode());
			//#CM460
			// Consignor name
			rStock.setConsignorName(vinvcheck.getConsignorName());
			//#CM461
			// Supplier Code
			rStock.setSupplierCode("");
			//#CM462
			// Supplier Name
			rStock.setSupplierName1("");
			//#CM463
			// Packed qty per Case
			rStock.setEnteringQty(vinvcheck.getEnteringQty());
			//#CM464
			// Packed qty per bundle
			rStock.setBundleEnteringQty(vinvcheck.getBundleEnteringQty());
			//#CM465
			// Case ITF
			rStock.setItf(vinvcheck.getItf());
			//#CM466
			// BundleITF
			rStock.setBundleItf(vinvcheck.getBundleItf());
			//#CM467
			// Solved by the added date/time Handler.
			//#CM468
			// Name of Add Process
			rStock.setRegistPname(wProcessName);
			//#CM469
			// Last update date/time was solved by Handler.
			//#CM470
			// Last update process name
			rStock.setLastUpdatePname(wProcessName);

			//#CM471
			// Data addition
			stockHandler.create(rStock);

			//#CM472
			// Stock Quantity(Planned Work Qty of Result Information(Host Planned Qty))
			rStock.setStockQty(0);

			//#CM473
			// Execute the process for adding the Host sending Information.(Increase in Inventory Check)
			if (!createResult(conn,
				rStock,
				wJobNo,
				workercode,
				workername,
				sysdate,
				terminalno,
				SystemDefine.JOB_TYPE_INVENTORY_PLUS,
				wProcessName,
				" ",
				Stock.TCDC_FLAG_DC,
				stockqty))
			{
				return false;
			}

			return true;

		}
		catch (DataExistsException e)
		{
			//#CM474
			// 6023316 = No.{0} Stock qty. was changed after the input of Inventory Check. (Enter Inventory Check again.)
			wMessage = "6023316" + wDelim + wparam.getRowNo();
			return false;
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

	//#CM475
	/**
	 * Add the Sending Result Info table (DNHOSTSEND) <BR>
	 * <BR>
	 * Add the Sending Result Information based on the contents of the Received parameter. <BR>
	 * <BR>
	 * @param conn        Instance to maintain database connection.
	 * @param vstock      Instance of Stock class
	 * @param jobno       Work No.
	 * @param workercode  Worker Code
	 * @param workername  Worker Name
	 * @param sysdate     Work Date ( System defined date)
	 * @param terminalno  Terminal No.
	 * @param jobtype     Work division
	 * @param processname Process name
	 * @param batchno     Batch No.
	 * @param tcdckbn	   TCDC division
	 * @param inputqyt    Result qty
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected boolean createResult(
		Connection conn,
		Stock vstock,
		String jobno,
		String workercode,
		String workername,
		String sysdate,
		String terminalno,
		String jobtype,
		String processname,
		String batchno,
		String tcdckbn,
		int inputqty)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			HostSendHandler hostsendHandler = new HostSendHandler(conn);
			HostSend hostsend = new HostSend();

			//#CM476
			// Work Date
			hostsend.setWorkDate(sysdate);
			//#CM477
			// Work No.
			hostsend.setJobNo(jobno);
			//#CM478
			// Work division
			hostsend.setJobType(jobtype);
			//#CM479
			// Aggregation Work No. (equal to Work No.)
			hostsend.setCollectJobNo(jobno);
			//#CM480
			// Status flag (Completed)
			hostsend.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
			//#CM481
			// Start Work flag (Started)
			hostsend.setBeginningFlag(SystemDefine.BEGINNING_FLAG_STARTED);
			//#CM482
			// Plan unique key
			hostsend.setPlanUkey("");
			//#CM483
			// Stock ID
			hostsend.setStockId(vstock.getStockId());
			//#CM484
			// Area No.
			hostsend.setAreaNo(vstock.getAreaNo());
			//#CM485
			// Location No.
			hostsend.setLocationNo(vstock.getLocationNo());
			//#CM486
			// Planned date
			hostsend.setPlanDate("");
			//#CM487
			// Consignor code
			hostsend.setConsignorCode(vstock.getConsignorCode());
			//#CM488
			// Consignor name
			hostsend.setConsignorName(vstock.getConsignorName());
			//#CM489
			// Supplier Code
			hostsend.setSupplierCode("");
			//#CM490
			// Supplier Name
			hostsend.setSupplierName1("");
			//#CM491
			// Receiving ticket No.
			hostsend.setInstockTicketNo("");
			//#CM492
			// Receiving ticket Line No.
			hostsend.setInstockLineNo(0);
			//#CM493
			// customer code
			hostsend.setCustomerCode("");
			//#CM494
			// customer name
			hostsend.setCustomerName1("");
			//#CM495
			// Shipping Ticket No
			hostsend.setShippingTicketNo("");
			//#CM496
			// Shipping ticket line No.
			hostsend.setShippingLineNo(0);
			//#CM497
			// Item Code
			hostsend.setItemCode(vstock.getItemCode());
			//#CM498
			// Item Name
			hostsend.setItemName1(vstock.getItemName1());
			//#CM499
			// For the purpose of use for System Stock Qty of Report-Data (stock qty before Inventory Check)
			hostsend.setHostPlanQty(vstock.getStockQty());
			//#CM500
			// Work Planned Qty
			hostsend.setPlanQty(0);
			//#CM501
			// Possible Work Qty
			hostsend.setPlanEnableQty(0);
			//#CM502
			// Work Result qty
			hostsend.setResultQty(inputqty);
			//#CM503
			// Work shortage qty
			hostsend.setShortageCnt(0);
			//#CM504
			// Pending Qty
			hostsend.setPendingQty(0);
			//#CM505
			// Packed qty per Case
			hostsend.setEnteringQty(vstock.getEnteringQty());
			//#CM506
			// Packed qty per bundle
			hostsend.setBundleEnteringQty(vstock.getBundleEnteringQty());
			//#CM507
			// Case/Piece division(Load Size)
			hostsend.setCasePieceFlag(vstock.getCasePieceFlag());
			//#CM508
			// Case/Piece division(Work type)
			hostsend.setWorkFormFlag(vstock.getCasePieceFlag());
			//#CM509
			// Case ITF
			hostsend.setItf(vstock.getItf());
			//#CM510
			// Bundle ITF
			hostsend.setBundleItf(vstock.getBundleItf());
			//#CM511
			// TC/DC Division
			hostsend.setTcdcFlag(tcdckbn);
			//#CM512
			// Expiry Date
			hostsend.setUseByDate(vstock.getUseByDate());
			//#CM513
			// Lot No.
			hostsend.setLotNo("");
			//#CM514
			// Plan information Comment
			hostsend.setPlanInformation("");
			//#CM515
			// Order No.
			hostsend.setOrderNo("");
			//#CM516
			// Order Date
			hostsend.setOrderingDate("");
			//#CM517
			// Expiry Date
			hostsend.setResultUseByDate(vstock.getUseByDate());
			//#CM518
			// Lot No.
			hostsend.setResultLotNo("");
			//#CM519
			// Work Result Location
			hostsend.setResultLocationNo(vstock.getLocationNo());
			//#CM520
			// Work Report flag (Not Reported)
			hostsend.setReportFlag(SystemDefine.REPORT_FLAG_NOT_SENT);
			//#CM521
			// Batch No.(Schedule No.)
			hostsend.setBatchNo(batchno);
			AreaOperator areaOpe = new AreaOperator(conn);
			hostsend.setSystemDiscKey(Integer.parseInt(areaOpe.getAreaType(vstock.getAreaNo())));
			//#CM522
			// Worker Code
			hostsend.setWorkerCode(workercode);
			//#CM523
			// Worker Name
			hostsend.setWorkerName(workername);
			//#CM524
			// Terminal No.RFTNo.
			hostsend.setTerminalNo(terminalno);
			//#CM525
			// Plan information Added Date/Time
			hostsend.setPlanRegistDate(new Date());
			//#CM526
			// Added Date/Time
			hostsend.setRegistDate(new Date());
			//#CM527
			// Name of Add Process
			hostsend.setRegistPname(processname);
			//#CM528
			// Last update date/time
			hostsend.setLastUpdateDate(new Date());
			//#CM529
			// Last update process name
			hostsend.setLastUpdatePname(processname);

			//#CM530
			// Data addition
			hostsendHandler.create(hostsend);

			return true;

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
		catch (NumberFormatException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM531
	/**
	 * Receive the contents entered via screen as a parameter and check for input and exclusion in the preset area.
	 * @param conn Instance to maintain database connection.
	 * @param param RetrievalSupportParameter class instance with contents that were input via screen.
	 * @return Return true when no input error occurs, or return false when input error occurs.
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 */
	protected boolean lockAll(Connection conn, Parameter[] checkParam) throws ReadWriteException, ScheduleException
	{
		StorageSupportParameter[] wParam = (StorageSupportParameter[]) checkParam;

		//#CM532
		// Lock the Inventory Check Work status.
		InventoryCheckSearchKey inventoryKey = new InventoryCheckSearchKey();
		InventoryCheckHandler inventoryHandle = new InventoryCheckHandler(conn);

		//#CM533
		// Update using Work No.
		Vector jobVec = new Vector();
		for (int i = 0; i < wParam.length; i++)
		{
			jobVec.addElement(wParam[i].getJobNo());
		}

		String[] jobNoArry = new String[jobVec.size()];
		jobVec.copyInto(jobNoArry);
		//#CM534
		// Set the Search key and lock the target record.
		inventoryKey.setJobNo(jobNoArry);
		inventoryHandle.findForUpdate(inventoryKey);

		//#CM535
		// Lock the inventory information.
		StockSearchKey stockKey = new StockSearchKey();
		StockHandler stockHandler = new StockHandler(conn);
		//#CM536
		// Joint the conditions per one stock with "OR" and lock all the preset data.
		for (int i = 0; i < wParam.length; i++)
		{
			//#CM537
			// Set the search conditions.
			if (i == 0)
			{
				stockKey.setConsignorCode(wParam[i].getConsignorCode(), "=", "((", "", "AND");
			}
			else
			{
				stockKey.setConsignorCode(wParam[i].getConsignorCode(), "=", "(", "", "AND");
			}

			stockKey.setLocationNo(wParam[i].getLocation());

			//#CM538
			// If expiry date control is enabled, include expiry date in the search conditions.
			if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				//#CM539
				// Accept blank even if Expiry Date Control is enabled.
				stockKey.setUseByDate(wParam[i].getUseByDate(), "=", "", "", "AND");
			}

			if (i == wParam.length - 1)
			{
				stockKey.setItemCode(wParam[i].getItemCode(), "=", "", "))", "AND");
			}
			else
			{
				stockKey.setItemCode(wParam[i].getItemCode(), "=", "", ")", "OR");
			}
		}

		//#CM540
		// Require to be Center Inventory.
		stockKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM541
		// Conditions to obtain data
		stockKey.setStockIdCollect();
		//#CM542
		// Execute search.
		stockHandler.findForUpdate(stockKey);

		return true;
	}

}
//#CM543
//end of class
