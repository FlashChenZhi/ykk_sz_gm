package jp.co.daifuku.wms.storage.schedule;

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
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.storage.entity.TotalStock;

/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * This class allows to execute Inventory Check process.<BR>
 * Receive the contents entered via screen as a parameter and execute the process for inventory check info.<BR>
 * Each method in this class receives a connection object and executes the process for updating the database. <BR>
 * However, each method disables to commit and roll back of transactions. <BR>
 * This class executes the following processes. <BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   1-1 Obtain the count of Consignor codes from the inventory information<BR>
 *   <BR>
 *   <DIR>
 *   [Search conditions] <BR><DIR>
 *     With status flag "Center Stock" <BR>
 *     Stock qty is 1 or larger <BR>
 *     inventory information.Area No =Area master info .Area No  <BR>
 *     Area master info .Area Division=Other than AS/RS <BR></DIR>
 *   </DIR><BR>
 *   1-2 Obtain the count of Consignor codes from the inventory check work status.<BR>
 *   <DIR>
 *   [Search conditions] <BR><DIR>
 *   Process flag is Not Submitted <BR></DIR>
 *   </DIR><BR>
 *   1-3 If only one Consignor code exists in the inventory information and the inventory check work status, return the corresponding Consignor Code. <BR>
 *	     Return null if no corresponding data found, or two or more corresponding data exist.<BR>
 *   <BR>
 * </DIR>
 * <BR>
 * 2.Process by clicking on Next button (<CODE>nextCheck()</CODE>Method)<BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter and return the results of check for worker code, password and presence of corresponding data. <BR>
 *   If corresponding data exist in the inventory information or the Inventory Check Work status and the contents of Worker code and password are correct, return true.<BR>
 *   Return false if no corresponding data exist in the inventory information or Inventory Check Work status, or if the contents of parameter has problem. <BR>
 *   For the contents of error, enable to obtain the contents using <CODE>getMessage()</CODE> method.<BR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR><DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Consignor code* <BR>
 *     Start Location No. <BR>
 *     End Location No. <BR></DIR>
 *   <System not-acceptable condition> <BR><DIR>
 *     With status flag "Center Stock" <BR>
 *     Stock qty is 1 or larger <BR></DIR>
 * </DIR>
 * <BR>
 * 3.Process by clicking on Next button (<CODE>Query()</CODE> Method)<BR><BR><DIR>
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
 *     Start Location No. <BR>
 *     End Location No. <BR>
 *     Inventory check work presence <BR>
 *     Inventory check work presence name <BR>
 *     Location No. <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Packed qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Total Inventory Check Qty <BR>
 *     Inventory Check Case Qty (Inventory Check Inventory Qty/Packed qty per Case) <BR>
 *     Inventory Check Piece Qty(Inventory Check Inventory Qty%Packed qty per Case) <BR>
 *     Total Stock Qty <BR>
 *     Stock Case Qty(Inventory Qty/Packed qty per Case) <BR>
 *     stock piece qty(Inventory Qty%Packed qty per Case) <BR>
 *     Work No. <BR>
 *     Last update date/time <BR>
 *     Expiry Date <BR></DIR>
 *   <Display order> <BR><DIR>
 *     Ascending order of Location No., Ascending order of item code, Ascending order of expiry date. <BR></DIR>
 *   <Display condition> <BR><DIR>
 *     Inventory check work presence <BR><DIR>
 *       If the target info exists in Inventory Check Work status, return "Added ". <BR>
 *       Return "Not Added" if not found. <BR></DIR>
 *     Inventory Check Case Qty, Piece qty <BR><DIR>
 *       Compile via Inventory Check Work Status when targeted information exists in the Inventory Check Work Status. <BR>
 *       Compile 0(zero) if not found. <BR></DIR></DIR>
 *     Stock Case Qty, Piece qty <BR><DIR>
 *       Compile via Inventory Check Work Status when targeted information exists in the Inventory Check Work Status. <BR>
 *       If not existing, compile via inventory information. <BR></DIR>
 *   <inventory information Search/Aggregation conditions> <BR><DIR>
 *       Consistent with the entered Consignor Code.
 *       Within the range between entered Start Location No. and End Location No.
 *       With status flag "Center Stock" <BR>
 *       Stock qty is 1 or larger <BR>
 *       Aggregated by Consignor Code / Location No. / Item Code / Expiry Date<BR></DIR>
 *   <Inventory Check Work Status search conditions><BR><DIR>
 *       Consistent with the entered Consignor Code.
 *       Within the range between entered Start Location No. and End Location No.
 *       Data only with Status flag Not Submitted <BR></DIR>
 *   <Area master Information search conditions><BR><DIR>
 *       Corresponding to the Area No. of inventory information
 *       Area division is Other than AS/RS<BR></DIR>
 * <BR></DIR>
 * 4.Process by clicking on the Input button:When new input(<CODE>check()</CODE>Method) <BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter and return the result of check for mandatory input, overflow, duplication, and exclusion. <BR>
 *   Re-obtain the data to be output to the preset area from database when the process normally completed, and return it. <BR>
 *   Return null when condition error or excusive error occurs. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 * <BR>
 *   <Input Parameter> *Mandatory Input <BR><DIR>
 *     Consignor code* <BR>
 *     Consignor name <BR>
 *     Location No.* <BR>
 *     Item Code* <BR>
 *     Item Name <BR>
 *     Packed qty per Case* <BR>
 *     Stock Case Qty* <BR>
 *     stock piece qty* <BR>
 *     Inventory Check Case Qty <BR>
 *     Inventory Check Piece Qty <BR>
 *     Expiry Date <BR>
 *     Preset Line No* <BR></DIR>
 *   <check parameter> *Mandatory Input <BR><DIR>
 *     Consignor code* <BR>
 *     Consignor name* <BR>
 *     Inventory check work presence* <BR>
 *     Item Code* <BR>
 *     Item Name* <BR>
 *     Location No.* <BR>
 *     Packed qty per Case* <BR>
 *     Packed qty per bundle* <BR>
 *     Stock Case Qty* <BR>
 *     stock piece qty* <BR>
 *     Inventory Check Case Qty* <BR>
 *     Inventory Check Piece Qty* <BR>
 *     Expiry Date* <BR>
 *     Preset Line No* <BR></DIR>
 * <BR>
 *   <Contents of check for mandatory input> <BR><DIR>
 *     1.Check for duplication across the inventory information. <BR>
 *     2.Check for duplication across the inventory check work status. <BR>
 *     3.Check for duplication across the preset area. <BR>
 *     4.Ensure that the Packed qty per case is 1 or more if the inventory case qty is 1 or more. <BR>
 *     5.Ensure that inventory check qty (total) is 1 or more. <BR>
 *     6.Ensure to input Item Code and Location No. <BR>
 *     7.Require no existence of the designated location in AS/RS location info. Shelf table check - (<CODE>isAsrsLocation()</CODE> method)<BR>
 *     Note) Check for duplication. Consignor Code / Location No. / Item Code / Expiry Date <BR></DIR>
 * <BR></DIR>
 * <BR>
 * 5.Process by clicking on the Input button:When correnction made from list display(<CODE>startSCHgetParams()</CODE>Method) <BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter and return the result of check for mandatory input, overflow, duplication, and exclusion. <BR>
 *   Re-obtain the data to be output to the preset area from database when the process normally completed, and return it. <BR>
 *   Return null when condition error or excusive error occurs. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 * <BR>
 *   <Input Parameter> *Mandatory Input <BR><DIR>
 *     Consignor code* <BR>
 *     Consignor name <BR>
 *     Location No.* <BR>
 *     Item Code* <BR>
 *     Item Name <BR>
 *     Packed qty per Case* <BR>
 *     Stock Case Qty* <BR>
 *     stock piece qty* <BR>
 *     Inventory Check Case Qty <BR>
 *     Inventory Check Piece Qty <BR>
 *     Expiry Date <BR>
 *     Preset Line No* <BR></DIR>
 *   <Returned data> <BR><DIR>
 *     Consignor code <BR>
 *     Consignor name <BR>
 *     Inventory check work presence <BR>
 *     Inventory check work presence name <BR>
 *     Location No. <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Packed qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Total Inventory Check Qty <BR>
 *     Inventory Check Case Qty (Inventory Check Inventory Qty/Packed qty per Case) <BR>
 *     Inventory Check Piece Qty(Inventory Check Inventory Qty%Packed qty per Case) <BR>
 *     Total Stock Qty <BR>
 *     Stock Case Qty(Inventory Qty/Packed qty per Case) <BR>
 *     stock piece qty(Inventory Qty%Packed qty per Case) <BR>
 *     Expiry Date <BR>
 *     Work No. <BR>
 *     Last update date/time <BR>
 *     Preset Line No* <BR>
 *     Note) If accepting it in the input check, obtain the latest stock case qty and the piece qty again from the inventory information, and set it for the return data. <BR></DIR>
 * <BR>
 *   <Contents of check for mandatory input> <BR><DIR>
 *     <inventory information existing> <BR><DIR>
 *       1.Accept only value 0 or blank in the inventory Case qty if the Packed qty per Case is not more than 0. <BR>
 *       2.Ensure that the inventory check qty (total) is 0 or more. <BR>
 *       3.Ensure that Inventory Check qty (total) is equal to allocated qty or larger. <BR></DIR>
 *       4.Require no existence of the designated location in AS/RS location info. Shelf table check - (<CODE>isAsrsLocation()</CODE> method)<BR>
 *     <No inventory information> <BR><DIR>
 *       1.Accept only value 0 or blank in the inventory Case qty if the Packed qty per Case is not more than 0. <BR>
 *       2.Ensure that the Inventory Check qty (total) is 1 or larger. <BR></DIR>
 *   <BR></DIR>
 * </DIR>
 * <BR>
 * 6.Process by clicking on Delete button(<CODE>startSCH()</CODE>Method) <BR><BR><DIR>
 *   Receive the contents displayed in the preset area as a parameter and execute the process to delete the inventory check work status. <BR>
 *   Return true when the process normally completed, or return false when failed to schedule completely due to condition error or other causes. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Consignor code* <BR>
 *     Item Code* <BR>
 *     Location No.* <BR>
 *     Expiry Date* <BR>
 *     Preset Line No* <BR>
 *     Terminal No.* <BR>
 *     Inventory check work presence* <BR>
 *     Button type: Delete* <BR></DIR>
 * <BR>
 *   Note) Execute the following checks and updating process only with inventory check work presence "Added". <BR>
 *   For Inventory check work with Inventory check work presence "Not Added", which is new and to be created and has not been added, return true as Completed normally. <BR>
 *   <Check for process condition> <BR>
 *     1.Consignor code-Location No-Item Code-Ensure that inventory check work status table of the expiry date exist in the database. <BR>
 * <BR>
 *   <Update Process> <BR>
 *     -Delete Inventory Check Work Status Table (DNINVENTORYCHECK) <BR>
 *       Execute the process for deleting Inventory Check work status linked with Consignor Code, Location No., Item Code, and expiry date for the parameter. <BR>
 *       Update the process flag to Delete. <BR>
 * <BR></DIR>
 * <BR>
 * 7.Process by clicking on Update Inventory Check Data button (<CODE>startSCH()</CODE>Method) <BR><BR><DIR>
 *   Receive the contents displayed in the preset area as a parameter and execute the process to update the inventory check work status. <BR>
 *   Return true when the process normally completed, or return false when failed to schedule completely due to condition error or other causes. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Consignor code* <BR>
 *     Consignor name* <BR>
 *     Inventory check work presence* <BR>
 *     Item Code* <BR>
 *     Item Name <BR>
 *     Location No.* <BR>
 *     Packed qty per Case* <BR>
 *     Packed qty per bundle* <BR>
 *     Stock Case Qty* <BR>
 *     stock piece qty* <BR>
 *     Inventory Check Case Qty* <BR>
 *     Inventory Check Piece Qty* <BR>
 *     Expiry Date* <BR>
 *     Preset Line No* <BR>
 *     Terminal No.* <BR>
 *     Button type: Add, Modify* <BR></DIR>
 * <BR>
 *   <Modify/Add process> <BR>
 * <DIR>
 *   <Check for process condition> <BR>
 *     1.Accept only value 0 or blank in the inventory Case qty if the Packed qty per Case is not more than 0. <BR>
 *     <inventory information existing> <BR><DIR>
 *       2.Ensure that the inventory check qty (total) is 0 or more. <BR>
 *       3.Ensure that Inventory Check qty (total) is equal to allocated qty or larger. <BR></DIR>
 *     <No inventory information> <BR><DIR>
 *       2.Ensure that the Inventory Check qty (total) is 1 or larger. <BR></DIR>
 * <BR>
 *   <Update Process> <BR>
 *     <Data with Inventory Check Work presence "Not Added"> <BR><DIR>
 *       -Addition of the Inventory Check Work Status table(DNINVENTORYCHECK)<BR>
 *       1.Create a new Inventory Check work status based on the contents in the parameter. <BR></DIR>
 *     <Inventory check work presence "Added"> <BR><DIR>
 *       -Update of Inventory Check Work Status table (DNINVENTORYCHECK). <BR>
 *       1.Update the Inventory Check work status based on the contents of the parameter. <BR><DIR>
 *       Update the Inventory Check Case Qty / Case qty. <BR>
 *       Update the Work Code / Worker name. <BR></DIR></DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/12</TD><TD>C.Kaminishizono</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/12 05:32:13 $
 * @author  $Author: suresh $
 */

public class InventoryMaintenanceSCH extends AbstractStorageSCH
{
	// Class variables -----------------------------------------------
	/**
	 * Class Name(Inventory Check)
	 */
	public static String CLASS_NAME = "InventoryMaintenanceSCH";
	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/12 05:32:13 $");
	}
	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public InventoryMaintenanceSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------

	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * If only one Consignor Code exists in inventory information and Inventory Check Work status, return the * corresponding Consignor Code.<BR>
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
		AreaOperator AreaOperator = new AreaOperator(conn);

		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;

		// Returned parameter
		StorageSupportParameter resultData = new StorageSupportParameter();

		// Set null for the Consignor Code of the returned parameter.
		resultData.setConsignorCode(null);

		// Start Search Inventory Info
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey stockSearchKey = new StockSearchKey();
		Stock stock = new Stock();

		// Set the search conditions.
		stockSearchKey.KeyClear();
		stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		stockSearchKey.setStockQty(0, ">");
		// Obtain the Area other than ASRS and add it to the search conditions.
		// Search for IS NULL if no corresponding area found.
		areaNo = AreaOperator.getAreaNo(areaType);
		stockSearchKey.setAreaNo(areaNo);

		stockSearchKey.setConsignorCodeGroup(1);
		stockSearchKey.setConsignorCodeCollect("");

		//Obtain the count.
		int stockCount = stockHandler.count(stockSearchKey);

		// Start searching for Inventory Check Information
		InventoryCheckHandler inventoryHandler = new InventoryCheckHandler(conn);
		InventoryCheckSearchKey inventorySearchKey = new InventoryCheckSearchKey();
		InventoryCheck inventoryCheck = new InventoryCheck();

		// Set the search conditions.
		inventorySearchKey.KeyClear();
		inventorySearchKey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
		inventorySearchKey.setConsignorCodeGroup(1);
		inventorySearchKey.setConsignorCodeCollect("");

		//Obtain the count.
		int inventoryCount = inventoryHandler.count(inventorySearchKey);

		// Null if two or more corresponding data exist, or search if the count is 1.
		if (stockCount > 1 || inventoryCount > 1)
		{
			return resultData;
		}

		try
		{
		// Only if both inventory information / Inventory Check work status exist and they have the same code, return the Consignor Code.			if (stockCount == 1 && inventoryCount == 1)
			if (stockCount == 1 && inventoryCount == 1)
			{
				stock = (Stock)stockHandler.findPrimary(stockSearchKey);
				inventoryCheck = (InventoryCheck)inventoryHandler.findPrimary(inventorySearchKey);
				if (stock.getConsignorCode().equals(inventoryCheck.getConsignorCode()))
				{
					resultData.setConsignorCode(stock.getConsignorCode());
				}
			}
			else if (stockCount == 1)
			{
				stock = (Stock)stockHandler.findPrimary(stockSearchKey);
				resultData.setConsignorCode(stock.getConsignorCode());
			}
			else if (inventoryCount == 1)
			{
				inventoryCheck = (InventoryCheck)inventoryHandler.findPrimary(inventorySearchKey);
				resultData.setConsignorCode(inventoryCheck.getConsignorCode());
			}
			// Return Parameter that is set for Consignor Code.
			return resultData;
		}
		catch (NoPrimaryException e)
		{
			return resultData;
		}
	}

	/**
	 * Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it. <BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ". <BR>
	 * Invoking this process in unavailable case throws <CODE>ScheduleException</CODE>. <BR>
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

		AreaOperator AreaOperator = new AreaOperator(conn);

		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;

		//Translate the type of startParams.
		StorageSupportParameter param = (StorageSupportParameter) searchParam;

		// inventory information search object
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey stockSearchKey = new StockSearchKey();

		// Object to search Inventory Check Information
		InventoryCheckHandler inventoryHandler = new InventoryCheckHandler(conn);
		InventoryCheckSearchKey inventorySearchKey = new InventoryCheckSearchKey();

		// Set the search conditions.
		setSearchKey(param, stockSearchKey, inventorySearchKey, false);

		// Obtain any area other than ASRS and add it to search conditions (Add it here because there is no connection in setSearchKey method).
		//#CM641
		// Search for IS NULL if no corresponding area found.
		areaNo = AreaOperator.getAreaNo(areaType);
		stockSearchKey.setAreaNo(areaNo);

		//#CM642
		// Obtain the inventory information and the count.
		int stockCount = stockHandler.count(stockSearchKey);
		Stock[] stock = (Stock[]) stockHandler.find(stockSearchKey);

		//#CM643
		// Obtain the Inventory Check Information and the count.
		int inventoryCount = inventoryHandler.count(inventorySearchKey);
		InventoryCheck[] inventoryCheck = (InventoryCheck[]) inventoryHandler.find(inventorySearchKey);

		//#CM644
		// Area to store the compiled inventory information and Inventory Check Information
		Vector vec = new Vector();

		//#CM645
		// Flag to determine to compile the inventory information or the Inventory Check Information (true: Compile the inventory information, or false: Compile the Inventory Check Information)
		boolean editFlag = false;

		//#CM646
		// inventory information and Inventory Check Information are compiled based on the search result.
		//#CM647
		// If if there are two or more data with the same Key item (count), compile the Inventory Check Information.
		for (int slc = 0, ilc = 0;;)
		{
			//#CM648
			// Parameter to store the compiled inventory information and Inventory Check Information
			StorageSupportParameter editParam = new StorageSupportParameter();

			//#CM649
			// Break if completed compiling both the inventory information and the inventory check info.
			if (slc >= stockCount && ilc >= inventoryCount)
			{
				break;
			}
			//#CM650
			// Close the inventory information.
			else if (slc >= stockCount)
			{
				editFlag = false;
			}
			//#CM651
			// Close Inventory Check Work Status
			else if (ilc >= inventoryCount)
			{
				editFlag = true;
			}
			else
			{
				//#CM652
				// Compare each Key item (count) and determine to compile the inventory information or Inventory Check Information.
				if (stock[slc].getConsignorCode().compareTo(inventoryCheck[ilc].getConsignorCode()) < 0)
				{
					editFlag = true;
				}
				else if ((stock[slc].getConsignorCode().equals(inventoryCheck[ilc].getConsignorCode()))
					&& (stock[slc].getLocationNo().compareTo(inventoryCheck[ilc].getLocationNo()) < 0))
				{
					editFlag = true;
				}
				else if ((stock[slc].getConsignorCode().equals(inventoryCheck[ilc].getConsignorCode()))
					&& (stock[slc].getLocationNo().equals(inventoryCheck[ilc].getLocationNo()))
					&& (stock[slc].getItemCode().compareTo(inventoryCheck[ilc].getItemCode()) < 0))
				{
					editFlag = true;
				}
				else
				{
					//#CM653
					// Determine whether Expiry Date Control is enabled or disabled.
					if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
					{
						if ((stock[slc].getConsignorCode().equals(inventoryCheck[ilc].getConsignorCode()))
							&& (stock[slc].getLocationNo().equals(inventoryCheck[ilc].getLocationNo()))
							&& (stock[slc].getItemCode().equals(inventoryCheck[ilc].getItemCode()))
							&& (stock[slc].getUseByDate().compareTo(inventoryCheck[ilc].getUseByDate()) != 0))
						{
							editFlag = true;
						}
						else
						{
							if ((stock[slc].getConsignorCode().equals(inventoryCheck[ilc].getConsignorCode()))
								&& (stock[slc].getLocationNo().equals(inventoryCheck[ilc].getLocationNo()))
								&& (stock[slc].getItemCode().equals(inventoryCheck[ilc].getItemCode()))
								&& (stock[slc].getUseByDate().equals(inventoryCheck[ilc].getUseByDate())))
							{
								//#CM654
								// To Next Stock Process
								slc++;
							}
							editFlag = false;
						}
					}
					else
					{
						if ((stock[slc].getConsignorCode().equals(inventoryCheck[ilc].getConsignorCode()))
							&& (stock[slc].getLocationNo().equals(inventoryCheck[ilc].getLocationNo()))
							&& (stock[slc].getItemCode().equals(inventoryCheck[ilc].getItemCode())))
						{
							//#CM655
							// To Next Stock Process
							slc++;
						}
						editFlag = false;
					}
				}
			}

			if (editFlag)
			{
				//#CM656
				// Compile in the inventory information.
				editParam = (StorageSupportParameter) MakeToStock(conn, param, stock[slc]);
				//#CM657
				// To Next Stock Process
				slc++;
			}
			else
			{
				//#CM658
				// Compile it via Inventory Check work status.
				editParam = (StorageSupportParameter) MakeToInventory(param, inventoryCheck[ilc]);
				//#CM659
				// To Next Inventory Check Work
				ilc++;
			}
			//#CM660
			// Store the compiled parameter into the VECTOR area.
			vec.addElement(editParam);
		}

		//#CM661
		// 6001013 = Data is shown.
		wMessage = "6001013";

		//#CM662
		// Returned parameter
		StorageSupportParameter[] returnParam = new StorageSupportParameter[vec.size()];
		vec.copyInto(returnParam);

		return returnParam;
	}

	/**
	 * Execute the process for checking the input in the parameter. Implement of the check process depends on the class that implements this interface. <BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ". <BR>
	 * Invoking this process in unavailable case throws <CODE>ScheduleException</CODE>. <BR>
	 * @param conn Instance to store database connection
	 * @param checkParam <CODE>StorageSupportParameter</CODE> class instance to hold input area contents
	 *         If anything other than <CODE>StorageSupportParameter</CODE> is specified throw Schedule Exception
	 * @param inputParams <CODE>StorageSupportParameter</CODE> class instance that stores preset area contents
	 *         If anything other than <CODE>StorageSupportParameter</CODE> is specified throw Schedule Exception
	 * @return Return true if the contents of the parameter is correct. Else false
	 * @throws ReadWriteException Throw when error occurs during database connection.
	 * @throws ScheduleException Throw when exception occur during check process
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams) throws ReadWriteException, ScheduleException
	{
		try
		{
			// Contents of the input area
			StorageSupportParameter param = (StorageSupportParameter) checkParam;
			// Contents of Preset area
			StorageSupportParameter[] paramList = (StorageSupportParameter[]) inputParams;

			// Check for mandatory input in the Case Qty when entering the Packed qty per Case.
			if (param.getInventoryCheckCaseQty() > 0 && param.getEnteringQty() == 0)
			{
				// 6023019 = Please enter 1 or greater in the packed quantity per case.
				wMessage = "6023019";
				return false;
			}

			// Check the input qty for overflow.
			if (((long) param.getInventoryCheckCaseQty() * (long) param.getEnteringQty() + (long) param.getInventoryCheckPieceQty() > WmsParam.MAX_STOCK_QTY))
			{
				// 6023058 = Please enter {1} or smaller for {0}. LBL-W0395
				wMessage = "6023058" + wDelim + DisplayText.getText("LBL-W0395") + wDelim + MAX_STOCK_QTY_DISP;
				return false;
			}

			// Location No
			String wLocationNo = "";
			// Obtain the location.
			wLocationNo = param.getLocation();

			if (!StringUtil.isBlank(wLocationNo))
			{
				LocateOperator locateOperator = new LocateOperator(conn);
				if (locateOperator.isAsrsLocation(wLocationNo))
				{
					// 6023442=The specified location is in automatic warehouse.
					wMessage = "6023442";
					return false;
				}
			}

			// Obtain the inventory information.
			TotalStock stock = (TotalStock) getStock(conn, param);

			if (stock.getTotalStockQty() <= 0)
			{
				// If no inventory information exists
				if ((param.getInventoryCheckCaseQty() * param.getEnteringQty() + param.getInventoryCheckPieceQty() <= 0))
				{
					// 6023310=Enter 1 or more value in Inventory Check Case Qty./Inventory Check Piece Qty. field.
					wMessage = "6023310";
					return false;
				}
			}
			// Check for overflow of the stock qty.
			else if (stock.getTotalStockQty() > WmsParam.MAX_STOCK_QTY)
			{
				// 6023348=Cannot enter.  Stock Qty. exceeds {0}.
				wMessage = "6023348" + wDelim + MAX_STOCK_QTY_DISP;
				return false;
			}
			else
			{
				// If inventory information existing.
				if ((param.getInventoryCheckCaseQty() * param.getEnteringQty() + param.getInventoryCheckPieceQty()) < (stock.getTotalStockQty() - stock.getTotalAllocationQty()))
				{
					// 6023057:Please enter {1} or greater for {0}.
					wMessage = "6023057" + wDelim + DisplayText.getText("LBL-W0395") + wDelim + DisplayText.getText("LBL-W0396");
					return false;
				}
			}

			// Check the Count of Inventory Check Information.
			int inventoryCount = searchInventory(conn, param);

			// For creating a new data, check that there is no same data in both inventory information and Inventory Check Information.
			if(StringUtil.isBlank(param.getStockID()) && StringUtil.isBlank(param.getJobNo()))
			{
				if(inventoryCount > 0)
				{
					// 6023312 = The same inventory check data exists. (Solve the problem by modifying data.)
					wMessage = "6023312";
					return false;
				}
				if(stock.getTotalStockQty() > 0)
				{
					// 6023311 = The same inventory data exists. (Solve the problem by modifying data.) 					wMessage = "6023311";
					wMessage = "6023311";
					return false;
				}
			}

			// Check for duplication across the preset area.
			if (paramList != null)
			{
				for (int i = 0; i < paramList.length; i++)
				{
					if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
					{
						if (paramList[i].getConsignorCode().equals(param.getConsignorCode()) &&
							paramList[i].getLocation().equals(param.getLocation()) &&
							paramList[i].getItemCode().equals(param.getItemCode()) &&
							paramList[i].getUseByDate().equals(param.getUseByDate()))
						{
							// 6023312 = The same inventory check data exists. (Solve the problem by modifying data.)
							wMessage = "6023312";
							return false;
						}
					}
					else
					{
						if (paramList[i].getConsignorCode().equals(param.getConsignorCode()) &&
							paramList[i].getLocation().equals(param.getLocation()) &&
							paramList[i].getItemCode().equals(param.getItemCode()))
						{
							// 6023312 =  6023312 = The same inventory check data exists. (Solve the problem by modifying data.)
							wMessage = "6023312";
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
		// 6001019 = Entry was accepted.
		wMessage = "6001019";
		return true;
	}

	/**
	 * Check the contents of the parameter for its properness. According to the contents set for the parameter designated in <CODE>checkParam</CODE>,  <BR>
	 * execute the process for checking the input in the parameter. Implement of the check process depends on the class that implements this interface. <BR>
	 * Return true if the contents of the parameter is correct. <BR>
	 * If the contents of the parameter has some problem, return false. Enable to obtain the contents using <CODE>getMessage()</CODE> method. <BR>
	 * This method implements input check while shifting from the first screen to the second screen between two screens. <BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ". <BR>
	 * Invoking this process in unavailable case throws <CODE>ScheduleException</CODE>. <BR>
	 * @param conn Database connection object
	 * @param checkParam This parameter class includes contents to be checked for input.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 */
	public boolean nextCheck(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
		// Translate Type of checkParam.
		StorageSupportParameter param = (StorageSupportParameter) checkParam;

		// inventory information search object
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey stockSearchKey = new StockSearchKey();

		// Object to search Inventory Check Information
		InventoryCheckHandler inventoryHandler = new InventoryCheckHandler(conn);
		InventoryCheckSearchKey inventorySearchKey = new InventoryCheckSearchKey();

		// Check the Worker code and password.
		if (!checkWorker(conn, param))
		{
			return false;
		}

		// Set the search conditions.
		setSearchKey(param, stockSearchKey, inventorySearchKey, true);

		//  Check for the count.
		if (!canLowerDisplay(stockHandler.count(stockSearchKey) + inventoryHandler.count(inventorySearchKey)))
		{
			// If total of inventory information and Inventory Check Information is 0, create a new data.
			if (returnNoDisplayParameter() == null)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Receive the contents entered via screen as a parameter and start-up the processes for updating the mandatory info and list. <BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ". <BR>
	 * Invoking this process in unavailable case throws <CODE>ScheduleException</CODE>. <BR>
	 * @param conn Instance to maintain database connection.
	 * @param startParams Array of <CODE>ShippingParameter</CODE> class with contents of commitment. <BR>
	 *         Designating any instance other than <CODE>ShippingParameter</CODE> instance throws ScheduleException. <BR>
	 *         Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @return Array of <CODE>ShippingParameter</CODE> instance with search result. <BR>
	 *          If no corresponding record found, return the array of the number of elements equal to 0. <BR>
	 *          Return null when input condition error occurs. <BR>
	 *          Returning null allows the <CODE>getMessage()</CODE> method to obtain the error content as a message.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		try
		{
			// Input information of the parameter
			StorageSupportParameter[] inputParam = (StorageSupportParameter[]) startParams;
			//Execute the common check process for operating the DB.
			if (!commonUpdateCheck(conn, inputParam))
			{
				return false;
			}

			// Process by Delete button
			for (int i = 0; i < startParams.length; i++)
			{
				// Lock / Check Exclusion
				if(!lock(conn, inputParam[i].getJobNo(), inputParam[i].getLastUpdateDate()))
				{
					// 6003006 = Unable to process this data. It has been updated via other work station.
					wMessage = "6003006";
					return false;
				}
				// Only when there is targeted info, delete the Inventory Check work status.
				if (searchInventory(conn, inputParam[i]) > 0)
				{
					inventoryDelete(conn, inputParam[i]);
				}
				else
				{
					// 6003006 = Unable to process this data. It has been updated via other work station.
					wMessage = "6003006";
					return false;
				}
				// 6001005=Deleted.
				wMessage = "6001005";
			}
			return true;
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	/**
	 * Check the contents of the parameter for its properness. According to the contents set for the parameter designated in <CODE>startParams</CODE>, <BR>
	 * execute the process for checking the input in the parameter. Implement of the check process depends on the class that implements this interface. <BR>
	 * Obtain the data to be output in the preset area from the database and the input contents after this process normally completed, and return it. <BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ". <BR>
	 * Invoking this process in unavailable case throws <CODE>ScheduleException</CODE>. <BR>
	 * @param conn Instance to store database connection
	 * @param startParams <CODE>StorageSupportParameter</CODE> class instance that stores setting contents<BR>
	 *         Throw <Code>ScheduleException</Code> if anything other than StorageSupportParameter instance is specified<BR>
	 * @return <CODE>StorageSupportParameter</CODE> instance array to hold preset area output<BR>
	 *         Return null if error occurs during the process<BR>
	 *         If null is returned , fetch the error details using <CODE>getMessage</CODE> method <BR>
	 * @throws ReadWriteException Throw when error occurs during database connection.
	 * @throws ScheduleException Throw when exception occur during check process
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		try
		{
			//Input information of the parameter
			StorageSupportParameter[] inputParam = (StorageSupportParameter[]) startParams;
			// Execute the common check process for operating the DB.
			if (!commonUpdateCheck(conn, inputParam))
			{
				return null;
			}

			// Check Automated Warehouse.
			for (int i = 0; i < inputParam.length; i++)
			{

				LocateOperator locateOperator = new LocateOperator(conn);

				// Location
				String locationno = inputParam[i].getLocation();

				// Check Location.
				if (!StringUtil.isBlank(locationno))
				{
					if (locateOperator.isAsrsLocation(locationno))
					{
						// 6023443=No.{0} The specified location is in automatic warehouse. Cannot enter.
						wMessage = "6023443" + wDelim + inputParam[i].getRowNo();
						return null;
					}
				}
			}

			// Worker Result: for Work Quantity
			int totalQty = 0;

			for (int i = 0; i < inputParam.length; i++)
			{
				if(inputParam[i].getInventoryKind().equals(StorageSupportParameter.INVENTORY_KIND_FIND))
				{
					// Lock / Check Exclusion
					if(!lock(conn, inputParam[i].getJobNo(), inputParam[i].getLastUpdateDate()))
					{
						// 6023209=No.{0} The data has been updated via other terminal.
						wMessage = "6023209" + wDelim + inputParam[i].getRowNo();
						return null;
					}
				}

				// Obtain the target inventory information.
				TotalStock stock = (TotalStock) getStock(conn, inputParam[i]);

				if (stock.getTotalStockQty() > WmsParam.MAX_STOCK_QTY)
				{
					// 6023273=No.{0} {1}
					// 6023348=Cannot enter.  Stock Qty. exceeds {0}.
					wMessage = "6023273" + wDelim + inputParam[i].getRowNo() +
									wDelim + MessageResource.getMessage("6023348" + wDelim + MAX_STOCK_QTY_DISP);
					return null;
				}

				// Obtain the Count of Inventory Check Information.
				int inventoryCount = searchInventory(conn, inputParam[i]);

				// For creating a new data, check that there is no same data in both inventory information and Inventory Check Information.
				if(StringUtil.isBlank(inputParam[i].getStockID()) && StringUtil.isBlank(inputParam[i].getJobNo()))
				{

					if (inventoryCount > 0)
					{
						// 6023273=No.{0} {1}
						// 6023312 = The same inventory check data exists. (Solve the problem by modifying data.)
						wMessage = "6023273" + wDelim + inputParam[i].getRowNo() + wDelim + MessageResource.getMessage("6023312");
						return null;
					}
					if (stock.getTotalStockQty() > 0)
					{
						// 6023273=No.{0} {1}
						// 6023311 = The same inventory data exists. (Solve the problem by modifying data.)
						wMessage = "6023273" + wDelim + inputParam[i].getRowNo() + wDelim + MessageResource.getMessage("6023311");
						return null;
					}

				}

				// Only when Inventory Check Information exists, update Process of Inventory Check Work status.
				if (inventoryCount > 0)
				{
					inventoryModify(conn, inputParam[i], stock);
				}
				// Create a new Inventory Check Information.
				else
				{
					inventoryCreate(conn, inputParam[i], stock);
				}
				//  Work Quantity (Totalize the result qty of Work status for one commitment)
				int inventoryQty = inputParam[i].getInventoryCheckCaseQty()
						* inputParam[i].getEnteringQty()
						+ inputParam[i].getInventoryCheckPieceQty();
				totalQty = addWorkQty(totalQty, inventoryQty);
			}
			// Add or update the Worker by work division.
			if (totalQty > 0)
			{
				updateWorkerResult(conn, inputParam[0].getWorkerCode(),
									getWorkerName(conn, inputParam[0].getWorkerCode()),
									getWorkDate(conn), inputParam[0].getTerminalNumber(),
									SystemDefine.JOB_TYPE_INVENTORY, totalQty);
			}

			// Search again the Storage Plan information.
			StorageSupportParameter[] viewParam = (StorageSupportParameter[]) this.query(conn, inputParam[0]);

			// 6021010=Data was committed.
			wMessage = "6021010";

			// Return the repeated search result.
			return viewParam;
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (DataExistsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * Check for any change by other terminal.
	 * Compare the last update date/time obtained from the current DB with the last update date/time set for the parameter.
	 * Compare both data. If both last updated date/time is equal, regard such data as data not modified via other terminal.
	 * If not equal, regard the data as data that was changed/modified via other work station.
	 * Require to compare the data in the Work status table (DNWORKINFO).
	 * @param conn       Database connection object
	 * @param jobno      Work No.
	 * @param lastupdate Last update date/time
	 * @return Return true when changed already via other terminal, or return false when not changed yet.
	 * Invoking this throws <CODE>ScheduleException</CODE>.
	 * @throws ScheduleException Announce it when this method is invoked.
	 */

	protected boolean lock(Connection conn, String jobno, Date lastupdate) throws ReadWriteException, ScheduleException
	{
		InventoryCheckHandler inventoryHandler = new InventoryCheckHandler(conn);
		InventoryCheckSearchKey inventorySearchKey = new InventoryCheckSearchKey();

		// Lock Data.
		// Work No.
		inventorySearchKey.KeyClear();
		inventorySearchKey.setJobNo(jobno);
		InventoryCheck[] inventoryCheck = (InventoryCheck[]) inventoryHandler.findForUpdate(inventorySearchKey);

		// Work No. does not exist in a data (in the case where data was deleted)
		if (inventoryCheck == null || inventoryCheck.length == 0)
		{
			return false;
		}
		// Compare the last update date/time obtained from the current DB with the last update date/time set for the parameter.
		// If not equal, regard the data as data that was changed/modified at other work station.
		if (!inventoryCheck[0].getLastUpdateDate().equals(lastupdate))
		{
			return false;
		}
		return true;
	}

	/**
	 * Receive the contents entered via screen as a parameter and check for input and exclusion in the preset area.
	 * @param conn Instance to maintain database connection.
	 * @param param RetrievalSupportParameter class instance with contents that were input via screen.
	 * @return Return true when no input error occurs, or return false when input error occurs.
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 */
	protected boolean lockAll(Connection conn, Parameter[] checkParam) throws ReadWriteException, ScheduleException
	{
		try
		{
			StorageSupportParameter[] param = (StorageSupportParameter[]) checkParam;
			// Joint the conditions per one stock with "OR" and lock all the preset data.
			// Lock the Inventory Check Work status.
			InventoryCheckSearchKey inventorySearchKey = new InventoryCheckSearchKey();
			InventoryCheckHandler InventoryHandler = new InventoryCheckHandler(conn);

			// Update using Work No.
			Vector jobVec = new Vector();
			for (int i = 0; i < param.length; i++)
			{
				jobVec.addElement(param[i].getJobNo());
			}

			String[] jobNoArry = new String[jobVec.size()];
			jobVec.copyInto(jobNoArry);
			//  Set the Search key and lock the target record.
			inventorySearchKey.setJobNo(jobNoArry);
			InventoryHandler.findForUpdate(inventorySearchKey);

			if (param == null || param.length <= 0)
			{
				return true;
			}
			for (int i = 0 ; i < param.length ; i++)
			{
				// Set the search conditions.
				if (i == 0)
				{
					inventorySearchKey.setConsignorCode(param[i].getConsignorCode(), "=", "((", "", "AND");
				}
				else
				{
					inventorySearchKey.setConsignorCode(param[i].getConsignorCode(), "=", "(", "", "AND");
				}

				inventorySearchKey.setLocationNo(param[i].getSourceLocationNo(),"=","","","AND");

				// If expiry date control is enabled, include expiry date in the search conditions.
				if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
				{
					// Accept blank even if Expiry Date Control is enabled.
					inventorySearchKey.setUseByDate(param[i].getUseByDate(),"=", "","","AND");
				}
				if (i == param.length -1)
				{
					inventorySearchKey.setItemCode(param[i].getItemCode(), "=", "", "))", "AND");
				}
				else
				{
					inventorySearchKey.setItemCode(param[i].getItemCode(), "=", "", ")", "OR");
				}
			}
			// Not Submitted
			inventorySearchKey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);

			InventoryHandler.findForUpdate(inventorySearchKey);
			return true;
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	// Private methods -----------------------------------------------

	/**
	 * Set the Inventory Check Information.<BR>
	 * Clicking on the Next button invokes this.<BR>
	 * Set the Inventory Check Information linked with screen contents for the parameter for screen display and return it.<BR>
	 * @param  sparam         Parameter
	 * @param  inventoryCheck Inventory Check Information
	 * @return Parameter
	 */
	protected StorageSupportParameter MakeToInventory(StorageSupportParameter param, InventoryCheck inventoryCheck)
	{
		StorageSupportParameter returnParam = new StorageSupportParameter();

		// Consignor code
		returnParam.setConsignorCode(inventoryCheck.getConsignorCode());
		// Consignor name
		returnParam.setConsignorName(inventoryCheck.getConsignorName());
		// Start Location No.
		returnParam.setFromLocation(param.getFromLocation());
		// End Location No.
		returnParam.setToLocation(param.getToLocation());
		// Inventory check work presence(Existence)
		returnParam.setInventoryKind(StorageSupportParameter.INVENTORY_KIND_FIND);
		// Inventory Check Work Presence Identification (Added)
		returnParam.setInventoryKindName(DisplayText.getText("LBL-W0391"));
		// Location No.
		returnParam.setLocation(inventoryCheck.getLocationNo());
		// Item Code
		returnParam.setItemCode(inventoryCheck.getItemCode());
		// Item Name
		returnParam.setItemName(inventoryCheck.getItemName1());
		// Packed qty per Case
		returnParam.setEnteringQty(inventoryCheck.getEnteringQty());
		// Packed qty per bundle
		returnParam.setBundleEnteringQty(inventoryCheck.getBundleEnteringQty());
		// Total Inventory Check Qty
		returnParam.setTotalInventoryCheckQty(inventoryCheck.getResultStockQty());
		// Inventory Check Case Qty (Inventory Check Inventory Qty/Packed qty per Case)
		returnParam.setInventoryCheckCaseQty(DisplayUtil.getCaseQty(inventoryCheck.getResultStockQty(), inventoryCheck.getEnteringQty()));
		// Inventory Check Piece Qty(Inventory Check Inventory Qty%Packed qty per Case)
		returnParam.setInventoryCheckPieceQty(DisplayUtil.getPieceQty(inventoryCheck.getResultStockQty(), inventoryCheck.getEnteringQty()));
		// Total Stock Qty
		returnParam.setTotalStockQty(inventoryCheck.getStockQty());
		// Stock Case Qty(Inventory Qty/Packed qty per Case)
		returnParam.setTotalStockCaseQty(DisplayUtil.getCaseQty(inventoryCheck.getStockQty(), inventoryCheck.getEnteringQty()));
		// stock piece qty(Inventory Qty%Packed qty per Case)
		returnParam.setTotalStockPieceQty(DisplayUtil.getPieceQty(inventoryCheck.getStockQty(), inventoryCheck.getEnteringQty()));
		// Expiry Date
		returnParam.setUseByDate(inventoryCheck.getUseByDate());
		// Last update date/time
		returnParam.setLastUpdateDate(inventoryCheck.getLastUpdateDate());
		// Work No.
		returnParam.setJobNo(inventoryCheck.getJobNo());

		return returnParam;
	}

	/**
	 * Search for the inventory information.<BR>
	 * Clicking on the Next button invokes this.<BR>
	 * Repeat to search for the inventory information and aggregate the stock qty based on the inventory information linked with the screen contents.<BR>
	 * @param  conn        Connection
	 * @param  param       Parameter
	 * @param  stockData   inventory information
	 * @return Parameter
	 * @throws ReadWriteException
	 */
	protected StorageSupportParameter MakeToStock(Connection conn, StorageSupportParameter param, Stock stockData) throws ReadWriteException
	{
		StorageSupportParameter returnParam = new StorageSupportParameter();

		// inventory information search object
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey stockSearchKey = new StockSearchKey();

		// Set the search conditions for inventory information.
		stockSearchKey.KeyClear();
		stockSearchKey.setConsignorCode(stockData.getConsignorCode());
		stockSearchKey.setLocationNo(stockData.getLocationNo());
		stockSearchKey.setItemCode(stockData.getItemCode());
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			stockSearchKey.setUseByDate(stockData.getUseByDate(), "=", "", "", "AND");
		}
		stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		stockSearchKey.setStockQty(0, ">");
		stockSearchKey.setLastUpdateDateOrder(1, false);

		// Search inventory information.
		Stock[] stock = (Stock[]) stockHandler.find(stockSearchKey);

		// Variable for aggregation and sum up
		long stockQty = 0;
		int enteringQty = 0;
		int bundleEnteringQty = 0;
		long stockCaseQty = 0;
		long stockPieceQty = 0;
		String consignorName = null;
		String itemName = null;
		String useByDate = null;

		for (int i = 0; i < stock.length; i++)
		{
			if (consignorName == null)
			{
				consignorName = stock[i].getConsignorName();
			}
			if (itemName == null)
			{
				itemName = stock[i].getItemName1();
			}
			if (enteringQty == 0 && stock[i].getEnteringQty() > 0)
			{
				enteringQty = stock[i].getEnteringQty();
			}
			if (bundleEnteringQty == 0 && stock[i].getBundleEnteringQty() > 0)
			{
				bundleEnteringQty = stock[i].getBundleEnteringQty();
			}
			if (!WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				if (useByDate == null)
				{
					useByDate = stock[i].getUseByDate();
				}
			}
			// Sum up the Inventory Qty ( Case).
			stockCaseQty += DisplayUtil.getCaseQty(stock[i].getStockQty(), stock[i].getEnteringQty());
			// Sum up the stock qty ( piece).
			stockPieceQty += DisplayUtil.getPieceQty(stock[i].getStockQty(), stock[i].getEnteringQty());
			stockQty += stock[i].getStockQty();
		}
		// Consignor code
		returnParam.setConsignorCode(stockData.getConsignorCode());
		// Consignor name
		returnParam.setConsignorName(consignorName);
		// Start Location No.
		returnParam.setFromLocation(param.getFromLocation());
		//  End Location No.
		returnParam.setToLocation(param.getToLocation());
		// Inventory Check Work Presence (None)
		returnParam.setInventoryKind(StorageSupportParameter.INVENTORY_KIND_NOTHING);
		//#CM791
		// Inventory Check Work presence identification (None)
		returnParam.setInventoryKindName(DisplayText.getText("LBL-W0394"));
		// Location No.
		returnParam.setLocation(stockData.getLocationNo());
		// Item Code
		returnParam.setItemCode(stockData.getItemCode());
		//  Item Name
		returnParam.setItemName(itemName);
		// Packed qty per Case
		returnParam.setEnteringQty(enteringQty);
		//  Packed qty per bundle
		returnParam.setBundleEnteringQty(bundleEnteringQty);
		// Total Inventory Check Qty
		returnParam.setTotalInventoryCheckQty(0);
		//Inventory Check Case Qty (Inventory Check Inventory Qty/Packed qty per Case)
		returnParam.setInventoryCheckCaseQty(0);
		//Inventory Check Piece Qty(Inventory Check Inventory Qty%Packed qty per Case)
		returnParam.setInventoryCheckPieceQty(0);
		// Total Stock Qty
		returnParam.setTotalStockQty(stockQty);
		// Stock Case Qty(Inventory Qty/Packed qty per Case)
		returnParam.setTotalStockCaseQty(stockCaseQty);
		// stock piece qty(Inventory Qty%Packed qty per Case)
		returnParam.setTotalStockPieceQty(stockPieceQty);
		if (!WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			// Expiry Date
			returnParam.setUseByDate(useByDate);
		}
		else
		{
			// Expiry Date
			returnParam.setUseByDate(stockData.getUseByDate());
		}
		// Stock ID
		returnParam.setStockID(stockData.getStockId());

		return returnParam;
	}

	/**
	 * Search for the inventory information.<BR>
	 * Clicking on the Input button or Update button invokes this.<BR>
	 * Obtain the inventory information linked with Input contents and preset area contents and sum up the stock qty and the allocated qty.<BR>
	 * @param  conn        Connection
	 * @param  searchParam Parameter
	 * @return inventory information
	 * @throws ReadWriteException
	 */
	protected TotalStock getStock(Connection conn, StorageSupportParameter searchParam) throws ReadWriteException
	{
		// Translate the type of searchParam.
		StorageSupportParameter param = (StorageSupportParameter) searchParam;

		// inventory information search object
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey stockSearchKey = new StockSearchKey();

		// Set the search conditions for inventory information.
		stockSearchKey.KeyClear();
		stockSearchKey.setConsignorCode(param.getConsignorCode());
		stockSearchKey.setLocationNo(param.getLocation());
		stockSearchKey.setItemCode(param.getItemCode());
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			stockSearchKey.setUseByDate(param.getUseByDate(), "=", "", "", "AND");
		}
		stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		stockSearchKey.setStockQty(0, ">");
		stockSearchKey.setLastUpdateDateOrder(1, false);

		//Search inventory information.
		Stock[] stock = (Stock[]) stockHandler.find(stockSearchKey);

		//aggregation variable
		long stockQty = 0;
		long allocationQty = 0;
		int enteringQty = 0;
		int bundleEnteringQty = 0;
		String itf = "";
		String bundleItf = "";

		// Object for return
		TotalStock editStock = new TotalStock();

		for (int i = 0; i < stock.length; i++)
		{
			//Sum up the stock qty.
			stockQty += stock[i].getStockQty();
			// Sum up the Allocatable qty.
			allocationQty += stock[i].getAllocationQty();
			// Packed qty per Case
			if (enteringQty == 0)
			{
				enteringQty = stock[i].getEnteringQty();
			}
			// Packed qty per bundle
			if (bundleEnteringQty == 0)
			{
				bundleEnteringQty = stock[i].getBundleEnteringQty();
			}
			if (itf.equals("") && stock[i].getItf() != null)
			{
				itf = stock[i].getItf();
			}
			if (bundleItf.equals("") && stock[i].getBundleItf() != null)
			{
				bundleItf = stock[i].getBundleItf();
			}
		}
		// Stock Qty
		editStock.setTotalStockQty(stockQty);
		// Allocatable Qty
		editStock.setTotalAllocationQty(allocationQty);
		// Packed qty per Case
		editStock.setEnteringQty(enteringQty);
		// Packed qty per bundle
		editStock.setBundleEnteringQty(bundleEnteringQty);
		// Case ITF
		editStock.setItf(itf);
		// BundleITF
		editStock.setBundleItf(bundleItf);

		return editStock;
	}

	/**
	 * Set the search conditions for inventory information and Inventory Check Information.<BR>
	 * Set the Consignor Code, Start Location No., End Location No. that were obtained from the parameter for the search conditions.<BR>
	 * Set Center Stock for the status and any value 1 or larger for stock qty as search conditions of inventory information.<BR>
	 * Set the Status of Inventory Check Information to Not Submitted as search conditions.<BR>
	 * Invoking from nextCheck, however, allows to set stock qty0 for inventory check info search conditions.<BR>
	 * @param  searchParam        Parameter
	 * @param  stockSearchKey     Object to store the search conditions for inventory information.
	 * @param  inventorySearchKey This object stores search conditions for Inventory Check Information
	 * @param  searchFlag         Invoking method determination flag
	 * @throws ReadWriteException
	 */
	private void setSearchKey(Parameter searchParam, StockSearchKey stockSearchKey,
								InventoryCheckSearchKey inventorySearchKey, boolean searchFlag) throws ReadWriteException
	{

		//Translate the type of startParams.
		StorageSupportParameter param = (StorageSupportParameter) searchParam;

		// Area no. search condition
		String[] areaNo = {WmsParam.FLOOR_AREA_NO, WmsParam.IDM_AREA_NO};

		//  Set the search conditions for inventory information and Inventory Check Information.
		stockSearchKey.KeyClear();
		inventorySearchKey.KeyClear();

		// Consignor code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			stockSearchKey.setConsignorCode(param.getConsignorCode());
			inventorySearchKey.setConsignorCode(param.getConsignorCode());
		}
		// Start Location No.
		if (!StringUtil.isBlank(param.getFromLocation()))
		{
			stockSearchKey.setLocationNo(param.getFromLocation(), ">=");
			inventorySearchKey.setLocationNo(param.getFromLocation(), ">=");
		}
		// End Location No.
		if (!StringUtil.isBlank(param.getToLocation()))
		{
			stockSearchKey.setLocationNo(param.getToLocation(), "<=");
			inventorySearchKey.setLocationNo(param.getToLocation(), "<=");
		}

		// Area no.
		stockSearchKey.setAreaNo(areaNo);

		// Set the inventory information specific search conditions.
		// Stock status:Center Inventory

		stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		// Stock qty is 1 or larger
		stockSearchKey.setStockQty(0, ">");

		// Aggregate using Consignor Code / Location No. / Item Code.
		stockSearchKey.setConsignorCodeGroup(1);
		stockSearchKey.setLocationNoGroup(2);
		stockSearchKey.setItemCodeGroup(3);
		stockSearchKey.setConsignorCodeCollect("");
		stockSearchKey.setLocationNoCollect("");
		stockSearchKey.setItemCodeCollect("");
		//  Define Loading Sequence.
		stockSearchKey.setConsignorCodeOrder(1, true);
		stockSearchKey.setLocationNoOrder(2, true);
		stockSearchKey.setItemCodeOrder(3, true);

		//If expiry date control is enabled, include expiry date in the search conditions.
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			stockSearchKey.setUseByDateGroup(4);
			stockSearchKey.setUseByDateCollect("");
			stockSearchKey.setUseByDateOrder(4, true);
		}
		// Set the search conditions specific to Inventory Check Information.
		// Data only with Process flag Not Submitted
		inventorySearchKey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
		// Only the data with stock qty equal to 0 (Avoid to obtain such a data that also exists in inventory information, when calculating the Count of Inventory Check Information)
		if (searchFlag)
		{
			inventorySearchKey.setStockQty(0);
		}
		// Define Loading Sequence.
		inventorySearchKey.setConsignorCodeOrder(1, true);
		inventorySearchKey.setLocationNoOrder(2, true);
		inventorySearchKey.setItemCodeOrder(3, true);
		inventorySearchKey.setUseByDateOrder(4, true);
	}

	/**
	 * Search for the Inventory Check Information.<BR>
	 * @param  conn        Connection
	 * @param  searchParam Parameter
	 * @return number of Count
	 * @throws ReadWriteException
	 */
	protected int searchInventory(Connection conn, Parameter searchParam) throws ReadWriteException
	{
		// Translate the type of searchParam.
		StorageSupportParameter param = (StorageSupportParameter) searchParam;

		// Object to search Inventory Check Information
		InventoryCheckHandler inventoryHandler = new InventoryCheckHandler(conn);
		InventoryCheckSearchKey inventorySearchKey = new InventoryCheckSearchKey();

		// Set the Inventory Check work status search conditions.
		inventorySearchKey.KeyClear();
		inventorySearchKey.setConsignorCode(param.getConsignorCode());
		inventorySearchKey.setItemCode(param.getItemCode());
		inventorySearchKey.setLocationNo(param.getLocation());
		inventorySearchKey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			inventorySearchKey.setUseByDate(param.getUseByDate());
		}
		// Obtain the count.
		return inventoryHandler.count(inventorySearchKey);
	}

	/**
	 * Execute the common check process for operating the DB.<BR>
	 * @param  conn        Connection
	 * @param  checkParams Parameter
	 * @return decision
	 * @throws ReadWriteException
	 */
	protected boolean commonUpdateCheck(Connection conn, Parameter[] checkParams)
											throws ReadWriteException, ScheduleException
	{
		// Input information of the parameter
		StorageSupportParameter[] param = (StorageSupportParameter[]) checkParams;
		// Check the Worker code and password.
		if (!checkWorker(conn, param[0]))
		{
			return false;
		}
		// Check Daily Maintenance Processing
		if (isDailyUpdate(conn))
		{
			return false;
		}

		// Lock all the stock of the Relocation target in the preset lines together to prevent from deadlocking.
		if (!this.lockAll(conn, param))
		{
			//6003006 = Unable to process this data. It has been updated via other work station.
			wMessage = "6003006";
			return false;
		}
		return true;
	}

	/**
	 * Execute the process for deleting the inventory check info.<BR>
	 * @param  conn        Connection
	 * @param  updateParam Parameter
	 * @param  stock       inventory information
	 * @throws ReadWriteException
	 */
	private void inventoryDelete(Connection conn, Parameter deleteParam)
		throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		// Translate the type of searchParam.
		StorageSupportParameter param = (StorageSupportParameter) deleteParam;

		// Object to delete Inventory Check Information
		InventoryCheckHandler invcheckHandler = new InventoryCheckHandler(conn);
		InventoryCheckAlterKey inventoryAlterKey = new InventoryCheckAlterKey();

		// Set the Key item (count).
		setAlterKey(param, inventoryAlterKey);

		// Process flag(Deleted)
		inventoryAlterKey.updateStatusFlag(InventoryCheck.STATUS_FLAG_DELETE);
		//Terminal No.
		inventoryAlterKey.updateTerminalNo(param.getTerminalNumber());
		//Worker Code
		inventoryAlterKey.updateWorkerCode(param.getWorkerCode());
		// Worker Name
		inventoryAlterKey.updateWorkerName(getWorkerName(conn, param.getWorkerCode()));
		// Last update process name
		inventoryAlterKey.updateLastUpdatePname(CLASS_NAME);
		//Update Inventory Check Information
		invcheckHandler.modify(inventoryAlterKey);
	}

	/**
	 * Execute the process for updating the Inventory Check Information.<BR>
	 * @param  conn        Connection
	 * @param  updateParam Parameter
	 * @param  stock       inventory information
	 * @throws ReadWriteException Throw this exception if database connection error occurs
	 * @throws InvalidDefineException Throw this exception if the parameter values have invalid characters
	 * @throws NotFoundException If the modify info does not exist, throw exception
	 */
	protected void inventoryModify(Connection conn, Parameter updateParam, TotalStock stock)
		throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		// Translate the type of searchParam.
		StorageSupportParameter param = (StorageSupportParameter) updateParam;

		// Object to update the Inventory Check Information.
		InventoryCheckHandler invcheckHandler = new InventoryCheckHandler(conn);
		InventoryCheckAlterKey inventoryAlterKey = new InventoryCheckAlterKey();

		// Set the Key item (count).
		setAlterKey(param, inventoryAlterKey);

		// Inventory Check result qty(Inventory CheckCase Qty * Packed qty per Case + Inventory Check Piece qty)
		inventoryAlterKey.updateResultStockQty((param.getInventoryCheckCaseQty() * param.getEnteringQty() + param.getInventoryCheckPieceQty()));

		// Allocatable Qty
		inventoryAlterKey.updateAllocationQty((int)stock.getTotalAllocationQty());
		// Stock Qty
		inventoryAlterKey.updateStockQty((int)stock.getTotalStockQty());
		//Terminal No.
		inventoryAlterKey.updateTerminalNo(param.getTerminalNumber());
		// Worker Code
		inventoryAlterKey.updateWorkerCode(param.getWorkerCode());
		//  Worker Name
		inventoryAlterKey.updateWorkerName(getWorkerName(conn, param.getWorkerCode()));
		// Last update process name
		inventoryAlterKey.updateLastUpdatePname(CLASS_NAME);
		// Update Inventory Check Information
		invcheckHandler.modify(inventoryAlterKey);
	}

	/**
	 * Execute the process for adding the Inventory Check Information.<BR>
	 * @param  conn        Connection
	 * @param  inputParam Parameter
	 * @param  stock       inventory information
	 * @throws ReadWriteException Throw this exception if database connection error occurs
	 * @throws InvalidDefineException Throw this exception if the parameter values have invalid characters
	 * @throws DataExistsException If the modify info does not exist, throw exception
	 */
	protected void inventoryCreate(Connection conn, Parameter inputParam, TotalStock stock)
		throws ReadWriteException, InvalidStatusException, DataExistsException
	{
		// Translate the type of searchParam.
		StorageSupportParameter param = (StorageSupportParameter) inputParam;

		// This object adds Inventory Check Information.
		InventoryCheckHandler invcheckHandler = new InventoryCheckHandler(conn);
		InventoryCheck inventoryCheck = new InventoryCheck();

		// Work No.
		SequenceHandler sequence = new SequenceHandler(conn);
		inventoryCheck.setJobNo(sequence.nextJobNo());
		// Stock ID
		inventoryCheck.setStockId("");
		// Area No
		inventoryCheck.setAreaNo("");
		// Location No
		inventoryCheck.setLocationNo(param.getLocation());
		// Process flag(Not Submitted)
		inventoryCheck.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
		//  Inventory Check Result barcode
		inventoryCheck.setInvcheckBcr(param.getItemCode());
		// Item Code
		inventoryCheck.setItemCode(param.getItemCode());
		// Item Code
		inventoryCheck.setItemName1(param.getItemName());
		// Stock Qty
		inventoryCheck.setStockQty((int)stock.getTotalStockQty());
		// Allocated qty
		inventoryCheck.setAllocationQty((int)stock.getTotalAllocationQty());
		// Work Planned Qty
		inventoryCheck.setPlanQty(0);
		//Inventory Check Result Qty(Inventory CheckCase Qty * Packed qty per Case + Inventory Check Piece qty)
		inventoryCheck.setResultStockQty(param.getInventoryCheckCaseQty() * param.getEnteringQty() + param.getInventoryCheckPieceQty());
		//Case/Piece division
		inventoryCheck.setCasePieceFlag(InventoryCheck.CASEPIECE_FLAG_NOTHING);
		//Storage Date
		inventoryCheck.setInstockDate("");
		// Last Picking Date
		inventoryCheck.setLastShippingDate("");
		// Expiry Date
		inventoryCheck.setUseByDate(param.getUseByDate());
		// Lot No.
		inventoryCheck.setLotNo("");
		// Plan information Comment
		inventoryCheck.setPlanInformation("");
		// Consignor code
		inventoryCheck.setConsignorCode(param.getConsignorCode());
		//Consignor name
		inventoryCheck.setConsignorName(param.getConsignorName());
		// Supplier Code
		inventoryCheck.setSupplierCode("");
		// Supplier Name
		inventoryCheck.setSupplierName1("");
		// Packed qty per Case
		inventoryCheck.setEnteringQty(param.getEnteringQty());
		// Packed qty per bundle
		inventoryCheck.setBundleEnteringQty(param.getBundleEnteringQty());
		// Case ITF
		inventoryCheck.setItf(stock.getItf());
		// BundleITF
		inventoryCheck.setBundleItf(stock.getBundleItf());
		//  Worker Code
		inventoryCheck.setWorkerCode(param.getWorkerCode());
		// Worker Name
		inventoryCheck.setWorkerName(getWorkerName(conn, param.getWorkerCode()));
		// Terminal No.
		inventoryCheck.setTerminalNo(param.getTerminalNumber());
		// Added Date/Time Execute this in Handler. So, do not require to set.
		// Name of Add Process
		inventoryCheck.setRegistPname(CLASS_NAME);
		// Last update date/time Execute this in Handler. So, do not require to set.
		//Last update process name
		inventoryCheck.setLastUpdatePname(CLASS_NAME);
		invcheckHandler.create(inventoryCheck);
	}

	/**
	 * Update the Inventory Check Information and set the key item (count) for Delete process.<BR>
	 * @param  keyParam         Parameter
	 * @param  inventoryAlterKey  Object to store Key item (count)
	 * @throws ReadWriteException Throws this exception if error occurs during database access
	 */
	protected void setAlterKey(Parameter keyParam, InventoryCheckAlterKey inventoryAlterKey)
		throws ReadWriteException
	{
		// Translate the type of searchParam.
		StorageSupportParameter param = (StorageSupportParameter) keyParam;
		inventoryAlterKey.KeyClear();
		inventoryAlterKey.setConsignorCode(param.getConsignorCode());
		inventoryAlterKey.setItemCode(param.getItemCode());
		inventoryAlterKey.setLocationNo(param.getLocation());
		inventoryAlterKey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			inventoryAlterKey.setUseByDate(param.getUseByDate());
		}
	}
}
//end of class