package jp.co.daifuku.wms.stockcontrol.schedule;

//#CM11675
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
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM11676
/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida  <BR>
 * <BR>
 * This class allows to modify or delete the WEB stock. <BR>
 * Receive the contents entered via screen as a parameter and execute the process for modifying or deleting the stock. <BR>
 * Each method in this class receives a connection object and executes the process for updating the database. <BR>
 * However, each method disables to commit and roll back of transactions. <BR>
 * This class executes the following processes. <BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method) <BR><BR><DIR>
 *   If only one Consignor code exist in the inventory information, return the corresponding Consignor code. <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist. <BR>
 * <BR>
 *   <Search conditions> <BR><DIR>
 *     Stock status:Center Inventory(2) </DIR></DIR>
 * <BR>
 * 2.Process by clicking on Next button (<CODE>nextCheck()</CODE>Method)<BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter and return the results of check for worker code, password and presence of corresponding data. <BR>
 *   If corresponding data exist in the inventory information and the contents of Worker code and password are correct, return true.<BR>
 *   If no corresponding data found or the contents of the parameter has problem, return false. <BR>
 *   For the contents of error, enable to obtain the contents using <CODE>getMessage()</CODE> method.<BR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR><DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Consignor code* <BR>
 *     Start Item Code <BR>
 *     End Item Code <BR>
 *     Selected Case/Piece division <BR>
 *     Display order <BR></DIR>
 * </DIR>
 * <BR>
 * 3.Process by clicking on Next button (<CODE>Query()</CODE> Method)<BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it.<BR>
 *   Return <CODE>Parameter</CODE> array with the number of elements 0 if no corresponding data found. Or, return null when condition error occurs.<BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 *   If the value of the display order of the parameter is "Item code/Location order (0)", display the Item code, Case/Piece division, and Location No. in this order. <BR>
 *   If the value of the display order of the parameter is "Item code/Storage date order (1)", display the Item code, Case/Piece division, and Storage date in this order. <BR>
 *   Disable to search any stock with stock qty equal to0, allocated qty equal 1 or more, and area division=AS/RS.
 *  * <BR>
 *   <Parameter> *Mandatory Input<BR><DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Consignor code* <BR>
 *     Start Item Code <BR>
 *     End Item Code <BR>
 *     Selected Case/Piece division* <BR>
 *     Display order* <BR></DIR>
 * <BR>
 *   <Returned data> <BR><DIR>
 *     Consignor code <BR>
 *     Consignor name <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Case/Piece division <BR>
 *     Case/Piece division name <BR>
 *     Location No. <BR>
 *     Packed qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Stock Case Qty(Inventory Qty/Packed qty per Case) <BR>
 *     stock piece qty(Inventory Qty%Packed qty per Case) <BR>
 *     Storage Date <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Stock ID <BR>
 *     Last update date/time <BR>
 *     Expiry Date <BR></DIR></DIR>
 * <BR>
 * 4.Process by clicking on the Input button(<CODE>check()</CODE>Method) <BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter and return the result of check for mandatory input, overflow, duplication, and exclusion. <BR>
 *   Inputting any correct value or not modifying the last update date/time the corresponding data returns true. <BR>
 *   Return false when condition error or excusive error occurs. <BR>
 *   Check for duplication using Consignor code, item code, Location No., Case/Piece division, and Expiry date as keys. <BR>
 *   -Targets are info with status flag Center Stock and with Stock quantity 1 or more. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Consignor code* <BR>
 *     Consignor name <BR>
 *     Item Code* <BR>
 *     Item Name <BR>
 *     Case/Piece division* <BR>
 *     Location No.* <BR>
 *     Packed qty per Case* <BR>
 *     Packed qty per bundle <BR>
 *     Stock Case Qty* <BR>
 *     stock piece qty* <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Storage Date* <BR>
 *     Stock ID* <BR>
 *     Last update date/time* <BR>
 *     Expiry Date <BR>
 *     Preset Line No* <BR></DIR>
 * <BR>
 *   <Contents of check for mandatory input> <BR>
 *     1.Require to input Packed qty per case if Case/Piece division is selected to None (0) or to Case (1). <BR>
 *     2.Ensure to input the stock case qty when the Case/Piece division is designated to "Case (1)". <BR>
 *     3.Ensure to input stock piece qty if Case/Piece division is designated to Piece (2). <BR>
 *     4.Ensure that the Packed qty per Case is 1 or larger if any value is entered in the Stock Case qty. <BR>
 *     5.Require that the allocated qty is equal to 0. <BR>
 *     6.Require no existence of the designated location in AS/RS location info. Shelf table check - (<CODE>isAsrsLocation()</CODE> method)<BR>
 *     7.Check for duplication across the preset area. <BR><DIR>
 *       Require to disable to duplicate Consignor code, item code, Location No., Case/Piece division, or expiry date.<BR></DIR>
 *     8.Check for duplication across the inventory information. <BR><DIR>
 *       Require to disable to duplicate Consignor code, item code, Location No., Case/Piece division, or expiry date.<BR>
 *       Targets are info with status flag Center Stock and with Stock quantity 1 or more. <BR></DIR>
 * <BR></DIR>
 * <BR>
 * 5.Process by clicking on Delete button or Delete All button(<CODE>startSCH()</CODE>Method) <BR><BR><DIR>
 *   Receive the contents displayed in the preset area as a parameter andExecute the process for inventory deleting. <BR>
 *   Return true when the process normally completed, orReturn false when failed to schedule completely due to condition error or other causes. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Stock ID* <BR>
 *     Last update date/time* <BR>
 *     Preset Line No* <BR>
 *     Terminal No.* <BR></DIR>
 * <BR>
 *   <Check for process condition> <BR>
 *     1.Ensure that inventory information table of Stock ID exists in the database. <BR>
 *     2.Require to correspond the last update date/time to the last update date/time of the inventory information table. (exclusion check) <BR>
 *     3.Ensure that the Allocated qty is 0. <BR>
 * <BR>
 *   <Update Process> <BR>
 *     -Delete Inventory Information Table (DMSTOCK). <BR>
 *       Execute the process for deleting inventory information linked to Stock ID of the parameter. <BR>
 * <BR>
 *     -Add Sending Result Info Table (DNHOSTSEND) <BR>
 *       Add the Sending Result Info based on the contents of the inventory information deleted this time. <BR>
 * <BR></DIR>
 * <BR>
 * 6.Process by clicking on Modify/Add button(<CODE>startSCHgetParams()</CODE>Method) <BR><BR><DIR>
 *   Receive the contents displayed in the preset area as a parameter and execute the process to modify or add the stock. <BR>
 *   Re-obtain the data to be output to the preset area from database when the process normally completed, and return it. <BR>
 *   Return null when the schedule failed to complete due to condition error or other causes. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Consignor code* <BR>
 *     Consignor name* <BR>
 *     Start Item Code <BR>
 *     End Item Code <BR>
 *     Selected Case/Piece division* <BR>
 *     Display order* <BR>
 *     Item Code* <BR>
 *     Item Name* <BR>
 *     Case/Piece division* <BR>
 *     Location No.* <BR>
 *     Packed qty per Case* <BR>
 *     Packed qty per bundle* <BR>
 *     Stock Case Qty* <BR>
 *     stock piece qty* <BR>
 *     Case ITF* <BR>
 *     Bundle ITF* <BR>
 *     Storage Date* <BR>
 *     Stock ID* <BR>
 *     Last update date/time* <BR>
 *     Expiry Date <BR>
 *     Preset Line No* <BR>
 *     Terminal No.* <BR></DIR>
 * <BR>
 *   <Returned data> <BR><DIR>
 *     Consignor code <BR>
 *     Consignor name <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Case/Piece division <BR>
 *     Case/Piece division name <BR>
 *     Location No. <BR>
 *     Packed qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Stock Case Qty(Inventory Qty/Packed qty per Case) <BR>
 *     stock piece qty(Inventory Qty%Packed qty per Case) <BR>
 *     Storage Date <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Stock ID <BR>
 *     Last update date/time <BR>
 *     Expiry Date <BR></DIR></DIR>
 * <BR>
 *   <Modify/Add process> <BR>
 * <DIR>
 *   <Check for process condition> <BR>
 *     1.Ensure that inventory information table of Stock ID exists in the database. <BR>
 *     2.Require to correspond the last update date/time of the parameter to the last update date/time of the inventory information table. (exclusion check) <BR>
 *     3.Require to input Packed qty per case if Case/Piece division is selected to None (0) or to Case (1). <BR>
 *     4.Ensure to input the stock case qty when the Case/Piece division is designated to "Case (1)". <BR>
 *     5.Ensure to input stock piece qty if Case/Piece division is designated to Piece (2). <BR>
 *     6.Ensure that the inventory Packed qty per Case is 1 or larger if any value is entered in the Case qty. <BR>
 *     7.Ensure that the Allocated qty is 0. <BR>
 *     8.Require no existence of the designated location in AS/RS location info. Shelf table check - (<CODE>isAsrsLocation()</CODE> method)<BR>
 *     9.Check for duplication across the preset area. <BR><DIR>
 *       Require to disable to duplicate Consignor code, item code, Location No., Case/Piece division, or expiry date.<BR></DIR>
 *    10.Check for duplication across the inventory information. <BR><DIR>
 *       Require to disable to duplicate Consignor code, item code, Location No., Case/Piece division, or expiry date.<BR>
 *       Targets are info with status flag Center Stock and with Stock quantity 1 or more. <BR></DIR>
 * <BR>
 *   <Update/Add Process> <BR>
 *     -Update Inventory Information Table (DMSTOCK). <BR>
 *       Consignor code, item code, Location No., Case/Piece division, expiry date of the parameter correspond,  <BR>
 *       Search through the inventory information with which the status flag "Center Stock" and Stock Quantity is 0. <BR>
 *       <When the said target info exists,> <BR><DIR>
 *         1.Delete the inventory information linked to Stock ID parameter. <BR>
 *         2.Update the inventory information obtained in the above process for obtaining based on the contents of the parameter. <BR>
 *       </DIR>
 *       <When the said target info does not exist,> <BR><DIR>
 *         1.Update the inventory information linked to Stock ID parameter based on the contents of the parameter. <BR>
 *       </DIR>
 * <BR>
 *     -Add Sending Result Info Table (DNHOSTSEND) <BR><DIR>
 *       1.Add the Sending Result Info ( maintenance decrease) based on the inventory information linked to Stock ID parameter. <BR>
 *       2.Add the Sending Result Info ( maintenance increase) based on the inventory information contents updated this time. <BR>
 *     </DIR>
 * </DIR>
 * </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/14</TD><TD>S.Yoshida</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/10 05:35:08 $
 * @author  $Author: suresh $
 */
public class StockCorrectSCH extends AbstractStockControlSCH
{

	//#CM11677
	// Class variables -----------------------------------------------
	//#CM11678
	/**
	 * Class Name(Modify/Delete Stock)
	 */
	public static String CLASS_NAME = "StockCorrectSCH";

	//#CM11679
	// Class method --------------------------------------------------
	//#CM11680
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ( "$Revision: 1.2 $,$Date: 2006/10/10 05:35:08 $" );
	}
	//#CM11681
	// Constructors --------------------------------------------------
	//#CM11682
	/**
	 * Initialize this class.
	 */
	public StockCorrectSCH()
	{
		wMessage = null;
	}

	//#CM11683
	// Public methods ------------------------------------------------
	//#CM11684
	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * If only one Consignor code exist in the inventory information, return the corresponding Consignor code.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Instance to maintain database connection.
	 * @param searchParam <CODE>StockControlParameter</CODE> class instance with conditions to obtain the display data.<BR>
	 *         </CODE>Designating any instance other than <CODE>StockControlParameter throws ScheduleException.<BR>
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind( Connection conn, Parameter searchParam ) throws ReadWriteException, ScheduleException
	{
		
		AreaOperator AreaOperator = new AreaOperator(conn);
		
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;

		StockReportFinder stockFinder = new StockReportFinder(conn);
		StockSearchKey searchKey = new StockSearchKey();

		StockControlParameter dispData = new StockControlParameter();

		//#CM11685
		// Search Data
		//#CM11686
		// Stock status(Center Inventory)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM11687
		// Ensure that the stock qty is 1 or more.
		searchKey.setStockQty(1, ">=");
		//#CM11688
		// Obtain the Area other than ASRS and add it to the search conditions.
		//#CM11689
		// Search for IS NULL if no corresponding area found.
		areaNo = AreaOperator.getAreaNo(areaType);
		searchKey.setAreaNo(areaNo);

		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		if (stockFinder.search(searchKey) == 1)
		{
			//#CM11690
			// Search Data			
			searchKey.KeyClear();
			//#CM11691
			// Stock status(Center Inventory)
			searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM11692
			// Ensure that the stock qty is 1 or more.
			searchKey.setStockQty(1, ">=");
			//#CM11693
			// Obtain the Consignor Name with the later added date/time.
			searchKey.setRegistDateOrder(1, false);
			//#CM11694
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
	
	//#CM11695
	/**
	 * Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param conn Instance to maintain database connection.
	 * @param searchParam <CODE>StockControlParameter</CODE> class instance with conditions to obtain the display data.<BR>
	 *         Designating any instance other than <CODE>StockControlParameter</CODE> throws ScheduleException.<BR>
	 * @return Array of <CODE>StockControlParameter</CODE> instance with search results.<BR>
	 *          If no corresponding record found, return the array of the number of elements equal to 0.<BR>
	 *          Return null if the search result count exceeds 1000 or when input condition error occurs.<BR>
	 *          Returning array with element qty 0 (zero) or null allows to obtain the error contents as a message using <CODE>getMessage()</CODE> method.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter[] query( Connection conn, Parameter searchParam ) throws ReadWriteException, ScheduleException
	{
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey stockSearchKey = new StockSearchKey();
		StockSearchKey nameSearchKey = new StockSearchKey();

		StockControlParameter sparam = (StockControlParameter)searchParam;

		//#CM11696
		// Obtain the target inventory information from the input info.
		//#CM11697
		// Obtain Shipping Planned info with the following conditions.
		//#CM11698
		// Concordant with the Consignor code of the Parameter
		//#CM11699
		// Equal to the value of Start item code of the parameter or larger (only when it is entered)			
		//#CM11700
		// Equal to the value of End item code of the parameter or larger (only if any data input)			
		//#CM11701
		// Concordant with the Case/Piece division of the Parameter
		//#CM11702
		// Stock status:Center Inventory
		//#CM11703
		// Stock qty is 1 or larger
		stockSearchKey.KeyClear();

		//#CM11704
		// Consignor code
		if (!StringUtil.isBlank(sparam.getConsignorCode()))
		{
			stockSearchKey.setConsignorCode(sparam.getConsignorCode());
		}

		//#CM11705
		// Start Item Code
		if (!StringUtil.isBlank(sparam.getFromItemCode()))
		{
			stockSearchKey.setItemCode(sparam.getFromItemCode(), ">=");
		}

		//#CM11706
		// End Item Code
		if (!StringUtil.isBlank(sparam.getToItemCode()))
		{
			stockSearchKey.setItemCode(sparam.getToItemCode(), "<=");
		}
		//#CM11707
		// Start Location No.
		if (!StringUtil.isBlank(sparam.getFromLocationNo()))
		{
			stockSearchKey.setLocationNo(sparam.getFromLocationNo(), ">=");
		}

		//#CM11708
		// End Location No.
		if (!StringUtil.isBlank(sparam.getToLocationNo()))
		{
			stockSearchKey.setLocationNo(sparam.getToLocationNo(), "<=");
		}

		//#CM11709
		// Selected Case/Piece division
		//#CM11710
		// Selecting All disables to accept any search conditions.
		if (!sparam.getSelectCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_ALL))
		{
			stockSearchKey.setCasePieceFlag(sparam.getSelectCasePieceFlag());
		}

		//#CM11711
		// Stock status:Center Inventory
		stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM11712
		// Stock qty is 1 or larger
		stockSearchKey.setStockQty(0, ">");
		
		AreaOperator AreaOperator = new AreaOperator(conn);
		
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;

		//#CM11713
		// Obtain the Area other than ASRS and add it to the search conditions.
		//#CM11714
		// Search for IS NULL if no corresponding area found.
		areaNo = AreaOperator.getAreaNo(areaType);
		stockSearchKey.setAreaNo(areaNo);

		//#CM11715
		// Define obtaining sequience with the Display order conditions of the parameter.
		//#CM11716
		// In sequence of Item Code, Location No
		if (sparam.getDspOrder().equals(StockControlParameter.DSPORDER_ITEM_LOCATION))
		{
			//#CM11717
			// Item Code
			stockSearchKey.setItemCodeOrder(1, true);
			//#CM11718
			// Case/Piece division
			stockSearchKey.setCasePieceFlagOrder(2, true);
			//#CM11719
			// Location No
			stockSearchKey.setLocationNoOrder(3, true);
		}
		//#CM11720
		// Item Code-Order of Storage Date
		else if (sparam.getDspOrder().equals(StockControlParameter.DSPORDER_ITEM_STORAGEDATE))
		{
			//#CM11721
			// Item Code
			stockSearchKey.setItemCodeOrder(1, true);
			//#CM11722
			// Case/Piece division
			stockSearchKey.setCasePieceFlagOrder(2, true);
			//#CM11723
			// Storage Date
			stockSearchKey.setInstockDateOrder(3, true);
			//#CM11724
			// Location No
			stockSearchKey.setLocationNoOrder(4, true);
		}
		//#CM11725
		// Location No sequence
		else if (sparam.getDspOrder().equals(StockControlParameter.DSPORDER_LOCATION))
		{
			//#CM11726
			// Location No
			stockSearchKey.setLocationNoOrder(1, true);
			//#CM11727
			// Item Code
			stockSearchKey.setItemCodeOrder(2, true);
			//#CM11728
			// Case/Piece division
			stockSearchKey.setCasePieceFlagOrder(3, true);
		}
		
		//#CM11729
		// Obtain Count Displayed
		if (!canLowerDisplay(stockHandler.count(stockSearchKey)))
		{
			return returnNoDisplayParameter();
		}
		//#CM11730
		// Obtain the target info.
		Stock[] wStock = (Stock[])stockHandler.find(stockSearchKey);

		//#CM11731
		// Obtain the Consignor name.
		nameSearchKey.KeyClear();
		nameSearchKey.setConsignorCode(sparam.getConsignorCode());
		nameSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		nameSearchKey.setStockQty(0, ">");
		nameSearchKey.setAreaNo(areaNo);
		
		nameSearchKey.setLastUpdateDateOrder(1, false);
		
		Stock[] nameGet = (Stock[])stockHandler.find(nameSearchKey);
		
		String wNameConsignorName = nameGet[0].getConsignorName();
		
		Vector	vec = new Vector();
				
		//#CM11732
		// Loop the process for the obtained info.
		for (int lc = 0; lc < wStock.length; lc++)
		{
			StockControlParameter wparam = new StockControlParameter();

			//#CM11733
			// Consignor code
			wparam.setConsignorCode(wStock[lc].getConsignorCode());
			//#CM11734
			// Consignor name
			wparam.setConsignorName(wNameConsignorName);
			//#CM11735
			// Item Code
			wparam.setItemCode(wStock[lc].getItemCode());
			//#CM11736
			// Item Name
			wparam.setItemName(wStock[lc].getItemName1());
			//#CM11737
			// Case/Piece division
			wparam.setCasePieceFlag(wStock[lc].getCasePieceFlag());
			//#CM11738
			// Case/Piece division name
			wparam.setCasePieceFlagName(DisplayUtil.getPieceCaseValue(wStock[lc].getCasePieceFlag()));
			//#CM11739
			// Location No
			wparam.setLocationNo(wStock[lc].getLocationNo());
			//#CM11740
			// Packed qty per Case
			wparam.setEnteringQty(wStock[lc].getEnteringQty());
			//#CM11741
			// Packed qty per bundle
			wparam.setBundleEnteringQty(wStock[lc].getBundleEnteringQty());
			//#CM11742
			// Stock Case Qty
			wparam.setStockCaseQty(DisplayUtil.getCaseQty(wStock[lc].getStockQty(), wStock[lc].getEnteringQty(), wStock[lc].getCasePieceFlag()));
			//#CM11743
			// stock piece qty
			wparam.setStockPieceQty(DisplayUtil.getPieceQty(wStock[lc].getStockQty(), wStock[lc].getEnteringQty(), wStock[lc].getCasePieceFlag()));
			//#CM11744
			// Storage Date
			wparam.setStorageDate(wStock[lc].getInstockDate());
			//#CM11745
			// Case ITF
			wparam.setITF(wStock[lc].getItf());
			//#CM11746
			// BundleITF
			wparam.setBundleITF(wStock[lc].getBundleItf());
			//#CM11747
			// Stock ID
			wparam.setStockId(wStock[lc].getStockId());
			//#CM11748
			// Expiry Date
			wparam.setUseByDate(wStock[lc].getUseByDate());
			//#CM11749
			// Last update date/time
			wparam.setLastUpdateDate(wStock[lc].getLastUpdateDate());
			 
			vec.addElement(wparam);
		}

		//#CM11750
		// Parameter area for return
		StockControlParameter[] rparam = new StockControlParameter[vec.size()];
		vec.copyInto(rparam);

		//#CM11751
		// 6001013 = Data is shown.
		wMessage = "6001013";

		return rparam;
	}

	//#CM11752
	/** 
	 * Receive the contents entered via screen as a parameter and <BR>
	 * Check for mandatory, overflow and duplication and return the checking results. <BR>
	 * If no same corresponding data exists in the inventory information, return true.  <BR>
	 * Regard such a case as an exclusion error where condition error occurs or the corresponding data exists, and return false. <BR>
	 * @param conn Instance to maintain database connection.
	 * @param checkParam Array of <CODE>StockControlParameter</CODE> class instance with input contents. <BR>
	 *        Designating any instance other than StockControlParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *        Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @param inputParams Array of <CODE>StockControlParameter</CODE> class instance with preset area contents. <BR>
	 *        Designating any instance other than StockControlParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *        Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 * @return true: if the input content is proper  false: if the input content is not proper
	 */
	public boolean check( Connection conn, Parameter checkParam, Parameter[] inputParams )
		throws ReadWriteException, ScheduleException
	{
		//#CM11753
		// Contents of the input area
		StockControlParameter param = (StockControlParameter)checkParam;
		//#CM11754
		// Contents of Preset area
		StockControlParameter[] paramlist = (StockControlParameter[]) inputParams;
			
		String casepieceflag = param.getCasePieceFlag();
		int enteringqty = param.getEnteringQty();
		int caseqty = param.getStockCaseQty();
		int pieceqty = param.getStockPieceQty();
		String locationno = param.getLocationNo();
		
		//#CM11755
		// Check Input Value
		if (!stockInputCheck(casepieceflag, enteringqty, caseqty, pieceqty))
		{
			return false;
		}
		//#CM11756
		// Check Location.
		if (!StringUtil.isBlank(locationno))
		{
			LocateOperator locateOperator = new LocateOperator(conn);
			if (locateOperator.isAsrsLocation(locationno))
			{
				//#CM11757
				// 6023442=The specified location is in automatic warehouse. Cannot enter.
				wMessage = "6023442";
				return false;
			}
		}
		//#CM11758
		// Check Overflow.
		long inputqty = (long)param.getStockCaseQty() * (long)param.getEnteringQty() + (long)param.getStockPieceQty();
		if (inputqty > WmsParam.MAX_STOCK_QTY)
		{
			//#CM11759
			// 6023058 = Please enter {1} or smaller for {0}.By checking with Stock Qty(Total Bulk Qty)
			wMessage = "6023058" + wDelim + DisplayText.getText("LBL-W0378") + wDelim + MAX_STOCK_QTY_DISP;
			return false;
		}

		//#CM11760
		// Check Exclusion.	
		if (!modifycheck(conn, param))
		{
			return false ;		
		}
		//#CM11761
		// Duplicate Check
		//#CM11762
		// Check for duplication using Consignor code, Location No., item code, Case/Piece division, and Expiry date as keys.
		//#CM11763
		// Include the Expiry date as a duplication check key if it is defined as an item to make the stock unique in WmsParam.
		if (paramlist != null)
		{
			for (int i = 0; i < paramlist.length; i++)
			{
				//#CM11764
				// Expiry Date control is disabled
				if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
				{
					if (paramlist[i].getConsignorCode().equals(param.getConsignorCode()) &&
						paramlist[i].getLocationNo().equals(param.getLocationNo()) &&
						paramlist[i].getItemCode().equals(param.getItemCode()) &&
						paramlist[i].getCasePieceFlag().equals(param.getCasePieceFlag()) &&
						paramlist[i].getUseByDate().equals(param.getUseByDate()))
					{
						//#CM11765
						// 6023090 = The data already exists.
						wMessage = "6023090";
						return false;
					}
				}
				//#CM11766
				// Expiry date control is disabled
				else
				{
					if (paramlist[i].getConsignorCode().equals(param.getConsignorCode()) &&
						paramlist[i].getLocationNo().equals(param.getLocationNo()) &&
						paramlist[i].getItemCode().equals(param.getItemCode()) &&
						paramlist[i].getCasePieceFlag().equals(param.getCasePieceFlag()))
					{
						//#CM11767
						// 6023090 = The data already exists.
						wMessage = "6023090";
						return false;
					}
				}
			}
		}
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey stockSearchKey = new StockSearchKey();

		stockSearchKey.KeyClear();

		//#CM11768
		// Check for presence of the same information using Consignor code + Item code + Location No. + Case/Piece division.
		//#CM11769
		// The targets are the info with stock status Center stock and with stock qty 1 or more.
		//#CM11770
		// Include the Expiry date as a duplication check key if it is defined as an item to make the stock unique in WmsParam.
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			stockSearchKey.setConsignorCode(param.getConsignorCode());		
			stockSearchKey.setLocationNo(param.getLocationNo());
			stockSearchKey.setItemCode(param.getItemCode());
			stockSearchKey.setCasePieceFlag(param.getCasePieceFlag());
			stockSearchKey.setUseByDate(param.getUseByDate());
			stockSearchKey.setStockId(param.getStockId(), "!=");
			stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			stockSearchKey.setStockQty(0, ">");
		}
		else
		{
			stockSearchKey.setConsignorCode(param.getConsignorCode());		
			stockSearchKey.setLocationNo(param.getLocationNo());
			stockSearchKey.setItemCode(param.getItemCode());
			stockSearchKey.setCasePieceFlag(param.getCasePieceFlag());
			stockSearchKey.setStockId(param.getStockId(), "!=");
			stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			stockSearchKey.setStockQty(0, ">");
		}

		if (stockHandler.count(stockSearchKey) > 0)
		{
			//#CM11771
			// 6023290 = Data with the same item and storage type exists.
			wMessage = "6023290";
			return false ;		
		}
		//#CM11772
		// 6001019 = Entry was accepted.
		wMessage = "6001019";
		return true;
	}

	//#CM11773
	/**
	 * Receive the contents entered via screen as a parameter and return the result of check for Worker code, password, and presence of corresponding data. <BR>
	 * Return true if corresponding data exist and the contents of Worker code and password are correct.<BR>
	 * If no corresponding data found or the contents of the parameter has problem, return false.
	 * For the contents of error, enable to obtain the contents using <CODE>getMessage()</CODE> method.<BR>
	 * @param conn Database connection object
	 * @param checkParam Array of <CODE>StockControlParameter</CODE> class instance with input contents. <BR>
	 *        Designating any instance other than StockControlParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *        Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 * @return true: if the input content is proper  false: if the input content is not proper
	 */
	public boolean nextCheck( Connection conn, Parameter checkParam ) throws ReadWriteException, ScheduleException
	{
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey stockSearchKey = new StockSearchKey();

		StockControlParameter sparam = (StockControlParameter)checkParam;

		//#CM11774
		// Obtain the Worker code and password from the parameter.
		if (!checkWorker(conn, sparam))
		{
			return false;
		}
		
		//#CM11775
		// Obtain the target inventory information from the input info.
		//#CM11776
		// Obtain the Shipping Planned info with the following conditions.
		//#CM11777
		// Concordant with the Consignor code of the Parameter
		//#CM11778
		// Equal to the value of Start item code of the parameter or larger (only when it is entered)			
		//#CM11779
		// Equal to the value of End item code of the parameter or larger (only if any data input)			
		//#CM11780
		// Consistent with the Case/Piece divition selected for the parameter
		//#CM11781
		// Stock status:Center Inventory
		//#CM11782
		// Stock qty is 1 or larger
		stockSearchKey.KeyClear();

		//#CM11783
		// Consignor code
		if (!StringUtil.isBlank(sparam.getConsignorCode()))
		{
			stockSearchKey.setConsignorCode(sparam.getConsignorCode());
		}

		//#CM11784
		// Start Item Code
		if (!StringUtil.isBlank(sparam.getFromItemCode()))
		{
			stockSearchKey.setItemCode(sparam.getFromItemCode(), ">=");
		}

		//#CM11785
		// End Item Code
		if (!StringUtil.isBlank(sparam.getToItemCode()))
		{
			stockSearchKey.setItemCode(sparam.getToItemCode(), "<=");
		}

		//#CM11786
		// Start Location No.
		if (!StringUtil.isBlank(sparam.getFromLocationNo()))
		{
			stockSearchKey.setLocationNo(sparam.getFromLocationNo(), ">=");
		}

		//#CM11787
		// End Location No.
		if (!StringUtil.isBlank(sparam.getToLocationNo()))
		{
			stockSearchKey.setLocationNo(sparam.getToLocationNo(), "<=");
		}

		//#CM11788
		// Selected Case/Piece division
		//#CM11789
		// Selecting All disables to accept any search conditions.
		if (!sparam.getSelectCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_ALL))
		{
			stockSearchKey.setCasePieceFlag(sparam.getSelectCasePieceFlag());
		}

		//#CM11790
		// Stock status:Center Inventory
		stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM11791
		// Stock qty is 1 or larger
		stockSearchKey.setStockQty(0, ">");
		
		//#CM11792
		// Obtain the count of the target info.
		return canLowerDisplay(stockHandler.count(stockSearchKey));
	}

	//#CM11793
	/**
	 * Receive the contents entered via screen as a parameter and start the schedule for deleting the stock.<BR>
	 * Assume that two or more data may be input via preset area or others. So, require the parameter to receive them in the form of array.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * Return true if the schedule normally completed, or return false if failed.
	 * @param conn Instance to maintain database connection.
	 * @param startParams Array of <CODE>StockControlParameter</CODE> class instance with commitment contents. <BR>
	 *         Designating any instance other than StockControlParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *         Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 */
	public boolean startSCH( Connection conn, Parameter[] startParams )
		throws ReadWriteException, ScheduleException
	{
		try
		{			
			//#CM11794
			// Check the Worker code and password
			StockControlParameter wparam = (StockControlParameter)startParams[0];
			if (!checkWorker(conn, wparam))
			{
				return false;
			}
			//#CM11795
			// Check the Daily Update Processing.
			if (isDailyUpdate(conn))
			{
				return false;
			}	
			
			//#CM11796
			// Worker Code
			String workercode = wparam.getWorkerCode();
			//#CM11797
			// Obtain the Worker name.
			String workername = getWorkerName(conn, workercode);

			//#CM11798
			// Terminal No.
			String terminalno = wparam.getTerminalNumber();
								
			try
			{
				//#CM11799
				// Lock Process(inventory information,  Location info)
				lockStockDataForDel(conn, (StockControlParameter[])startParams);		
			}
			catch(LockTimeOutException e)
			{
				//#CM11800
				// 6027008 = This data is not treatable because it is updating it with other terminals.
				wMessage = "6027008";
				return false;
			}
			
			for (int lc=0; lc<startParams.length; lc++)
			{
				StockControlParameter sparam = (StockControlParameter)startParams[lc];
				//#CM11801
				// Generate inventory info instance of handlers.
				StockSearchKey stockSearchKey = new StockSearchKey();
				StockHandler stockObj = new StockHandler( conn );
				Stock wStock = null;

				//#CM11802
				// Set the search conditions.
				//#CM11803
				// Stock ID
				stockSearchKey.setStockId(sparam.getStockId());
			
				//#CM11804
				// Obtain the search result count of the inventory information.
				wStock = (Stock)stockObj.findPrimaryForUpdate(stockSearchKey);
				if (wStock != null)
				{
					if (!wStock.getLastUpdateDate().equals(sparam.getLastUpdateDate()))
					{
						//#CM11805
						// Close the process when error via other terminal occurs.
						//#CM11806
						// 6023209 = No.{0} The data has been updated via other terminal.
						wMessage = "6023209" + wDelim + sparam.getRowNo();
						return false;
					}
				}
				else
				{
					//#CM11807
					// Close the process when error via other terminal occurs.
					//#CM11808
					// 6023209 = No.{0} The data has been updated via other terminal.
					wMessage = "6023209" + wDelim + sparam.getRowNo();
					return false;
				}

				//#CM11809
				// Check for the allocated qty.
				if (wStock.getStockQty() - wStock.getAllocationQty() > 0)
				{
					//#CM11810
					// The stock has been already allocated. Unable to modify/delete.
					//#CM11811
					// 6023291 = No.{0} Allocated stock. Neither Modification nor Delete is allowed.
					wMessage = "6023291" + wDelim + sparam.getRowNo();
					return false;
				}
				
				//#CM11812
				// Work Date ( System defined date)
				String sysdate = getWorkDate(conn);
				
				//#CM11813
				// Set the area of the deleted location.
				LocateOperator lOperator = new LocateOperator(conn);
				wStock.setAreaNo(lOperator.getAreaNo(wStock.getLocationNo()));
				
				//#CM11814
				// Add the Host sending info.
				if (!createHostSend(conn, wStock, sparam.getStockId(), workercode,
									workername, sysdate, terminalno,
									SystemDefine.JOB_TYPE_MAINTENANCE_MINUS,
									CLASS_NAME, " ", Stock.TCDC_FLAG_DC))
				{					
					return false;
				}

				//#CM11815
				// Add the Worker Result data inquiry.
				updateWorkerResult(conn, workercode,workername, sysdate, terminalno,SystemDefine.JOB_TYPE_MAINTENANCE_MINUS,wStock.getStockQty());
			
				//#CM11816
				// Delete the inventory information.
				try
				{
					stockSearchKey.KeyClear();
					stockSearchKey.setStockId(sparam.getStockId());
					
					stockObj.drop(stockSearchKey);
				}
				catch (NotFoundException e)
				{
					//#CM11817
					// Close the process when error via other terminal occurs.
					//#CM11818
					// 6023209 = No.{0} The data has been updated via other terminal.
					wMessage = "6023209" + wDelim + sparam.getRowNo();
					return false;
				}
				
				//#CM11819
				// Update the Location info of the deleted location.
				lOperator.modifyLocateStatus(wStock.getLocationNo(), CLASS_NAME);
			}
			
			//#CM11820
			// 6001005 = Deleted.
			wMessage = "6001005";
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		return true;
	}

	//#CM11821
	/**
	 * Receive the contents entered via screen as a parameter and start the schedule for modifying the stock.<BR>
	 * Assume that two or more data may be input via preset area or others. So, require the parameter to receive them in the form of array.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * Return true if the schedule normally completed, or return false if failed.
	 * @param conn Instance to maintain database connection.
	 * @param startParams Array of <CODE>StockControlParameter</CODE> class instance with commitment contents. <BR>
	 *         Designating any instance other than StockControlParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *         Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 * @return Array of <CODE>StockControlParameter</CODE> instance with search results.<BR>
	 */
	public Parameter[] startSCHgetParams( Connection conn, Parameter[] startParams )
		throws ReadWriteException, ScheduleException
	{
		try
		{			
			StockControlParameter wparam = (StockControlParameter)startParams[0];
			//#CM11822
			// Obtain the Worker code and password from the parameter.
			if (!checkWorker(conn, wparam))
			{
				return null;
			}
			//#CM11823
			// Check the Daily Update Processing.
			if (isDailyUpdate(conn))
			{
				return null;
			}
			
			//#CM11824
			// Contents of the input area
			StockControlParameter[] param = (StockControlParameter[])startParams;
			
			//#CM11825
			// Check Automated Warehouse.
			for (int i = 0; i < param.length; i++)
			{

				LocateOperator locateOperator = new LocateOperator(conn);

				//#CM11826
				// Location
				String locationno = param[i].getLocationNo();
				
				//#CM11827
				// Check Location.
				if (!StringUtil.isBlank(locationno))
				{
					if (locateOperator.isAsrsLocation(locationno))
					{
						//#CM11828
						// 6023443=No.{0} The specified location is in automatic warehouse. Cannot enter.
						wMessage = "6023443" + wDelim + param[i].getRowNo();
						return null;
					}
				}
			}

			//#CM11829
			// Worker Code
			String workercode = wparam.getWorkerCode();
			//#CM11830
			// Obtain the Worker name.
			String workername = getWorkerName(conn, workercode);

			//#CM11831
			// Terminal No.
			String terminalno = wparam.getTerminalNumber();
							
			try
			{
				//#CM11832
				// Lock Process(inventory information, Location info)
				lockStockDataForRep(conn, (StockControlParameter[])startParams);		
			}
			catch(LockTimeOutException e)
			{
				//#CM11833
				// 6027008 = This data is not treatable because it is updating it with other terminals.
				wMessage = "6027008";
				return null;
			}

			for (int lc=0; lc<startParams.length; lc++)
			{
				StockControlParameter sparam = (StockControlParameter)startParams[lc];
				//#CM11834
				// Generate inventory info instance of handlers.
				StockSearchKey stockSearchKey = new StockSearchKey();
				StockAlterKey stockAlterKey = new StockAlterKey();
				StockHandler stockObj = new StockHandler( conn );
				Stock wStock = null;

				//#CM11835
				// Set the search conditions.
				//#CM11836
				// Stock ID
				stockSearchKey.setStockId(sparam.getStockId());
			
				//#CM11837
				// Obtain the search result count of the inventory information.
				wStock = (Stock)stockObj.findPrimaryForUpdate(stockSearchKey);
				if (wStock != null)
				{
					if (!wStock.getLastUpdateDate().equals(sparam.getLastUpdateDate()))
					{
						//#CM11838
						// Close the process when error via other terminal occurs.
						//#CM11839
						// 6023209 = No.{0} The data has been updated via other terminal.
						wMessage = "6023209" + wDelim + sparam.getRowNo();
						return null;
					}
				}
				else
				{
					//#CM11840
					// Close the process when error via other terminal occurs.
					//#CM11841
					// 6023209 = No.{0} The data has been updated via other terminal.
					wMessage = "6023209" + wDelim + sparam.getRowNo();
					return null;
				}				
				
				stockSearchKey.KeyClear();

				//#CM11842
				// Check for presence of same info using Consignor code + Item code + Location NO. + Case/Piece division.
				//#CM11843
				// The targets are the info with stock status Center stock and with stock qty 1 or more.
				//#CM11844
				// Include the Expiry date as a duplication check key if it is defined as an item to make the stock unique in WmsParam.
				if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
				{
					stockSearchKey.setConsignorCode(sparam.getConsignorCode());		
					stockSearchKey.setLocationNo(sparam.getLocationNo());
					stockSearchKey.setItemCode(sparam.getItemCode());
					stockSearchKey.setCasePieceFlag(sparam.getCasePieceFlag());
					stockSearchKey.setUseByDate(sparam.getUseByDate());
					stockSearchKey.setStockId(sparam.getStockId(), "!=");
					stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
					stockSearchKey.setStockQty(0, ">");
				}
				else
				{
					stockSearchKey.setConsignorCode(sparam.getConsignorCode());		
					stockSearchKey.setLocationNo(sparam.getLocationNo());
					stockSearchKey.setItemCode(sparam.getItemCode());
					stockSearchKey.setCasePieceFlag(sparam.getCasePieceFlag());
					stockSearchKey.setStockId(sparam.getStockId(), "!=");
					stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
					stockSearchKey.setStockQty(0, ">");
				}

				if (stockObj.count(stockSearchKey) > 0)
				{
					//#CM11845
					// 6023273=No.{0} {1}
					//#CM11846
					// 6023290 = Data with the same item and storage type exists.
					wMessage = "6023273" + wDelim + sparam.getRowNo() + wDelim + MessageResource.getMessage("6023290");
					return null;
				}
				//#CM11847
				// Check for the allocated qty.
				if (wStock.getStockQty() - wStock.getAllocationQty() > 0)
				{
					//#CM11848
					// The stock has been already allocated. Unable to modify/delete.
					//#CM11849
					// 6023291 = No.{0} Allocated stock. Neither Modification nor Delete is allowed.
					wMessage = "6023291" + wDelim + sparam.getRowNo();
					return null;
				}

				//#CM11850
				// Check for change in each item (count) value.
				//#CM11851
				//  Item Code
				//#CM11852
				//  Item Name
				//#CM11853
				//  Case/Piece division
				//#CM11854
				//  Location No.
				//#CM11855
				//  Packed qty per Case
				//#CM11856
				//  Packed qty per bundle
				//#CM11857
				//  Stock Case Qty
				//#CM11858
				//  stock piece qty
				//#CM11859
				//  Case ITF
				//#CM11860
				//  Bundle ITF
				//#CM11861
				//  Storage Date
				//#CM11862
				//  Expiry Date
				if ((sparam.getItemCode().equals(wStock.getItemCode()))			&&
					(sparam.getItemName().equals(wStock.getItemName1()))			&&
					(sparam.getCasePieceFlag().equals(wStock.getCasePieceFlag()))	&&
					(sparam.getLocationNo().equals(wStock.getLocationNo()))			&&
					sparam.getEnteringQty() == wStock.getEnteringQty()				&&
					sparam.getBundleEnteringQty() == wStock.getBundleEnteringQty()	&&
					(sparam.getStockCaseQty() * sparam.getEnteringQty() +
					 sparam.getStockPieceQty()) == wStock.getStockQty()				&&
					(sparam.getITF().equals(wStock.getItf()))						&&
					(sparam.getBundleITF().equals(wStock.getBundleItf()))			&&
					sparam.getStorageDate() == wStock.getInstockDate()				&&
					(sparam.getUseByDate().equals(wStock.getUseByDate())))
				{
					//#CM11863
					// Disable to process for shifting due to no change.
					continue ;
				}
				
				//#CM11864
				// Work Date ( System defined date)
				String sysdate = getWorkDate(conn);
	
				//#CM11865
				// Set the value before modified.
				StockControlParameter mdparam = new StockControlParameter();
				mdparam.setStockId(wStock.getStockId());
				LocateOperator lOperator = new LocateOperator(conn);
				mdparam.setAreaNo(lOperator.getAreaNo(wStock.getLocationNo()));
				mdparam.setLocationNo(wStock.getLocationNo());
				mdparam.setConsignorCode(wStock.getConsignorCode());
				mdparam.setConsignorName(wStock.getConsignorName());
				mdparam.setItemCode(wStock.getItemCode());
				mdparam.setItemName(wStock.getItemName1());
				mdparam.setStockQty(wStock.getStockQty());
				mdparam.setEnteringQty(wStock.getEnteringQty());
				mdparam.setBundleEnteringQty(wStock.getBundleEnteringQty());
				mdparam.setCasePieceFlag(wStock.getCasePieceFlag());
				mdparam.setITF(wStock.getItf());
				mdparam.setBundleITF(wStock.getBundleItf());
				mdparam.setUseByDate(wStock.getUseByDate());							

				//#CM11866
				// Execute the process for adding the Host sending info ( maintenance decrease): Before modified
				if (!createHostsend(conn, mdparam, mdparam.getStockId(), workercode,
									workername, sysdate, terminalno,
									SystemDefine.JOB_TYPE_MAINTENANCE_MINUS,
									CLASS_NAME, " ", Stock.TCDC_FLAG_DC, mdparam.getStockQty()))	
				{
					return new StockControlParameter[0];
				}

				//#CM11867
				// Add the Worker Result data inquiry.
				updateWorkerResult(conn, workercode,workername, sysdate, terminalno,SystemDefine.JOB_TYPE_MAINTENANCE_MINUS,mdparam.getStockQty());
			
				//#CM11868
				// Define the area for storing the Stock ID for updating.
				String wModStockId = "";
				
				//#CM11869
				// Modify the inventory information.
				try
				{
					stockSearchKey.KeyClear();

					//#CM11870
					// Consignor code, item code, Location No., Case/Piece division, expiry date of the parameter correspond, 
					//#CM11871
					// Search through the inventory information with which the status flag "Center Stock" and Stock Quantity is 0.				
					stockSearchKey.setConsignorCode(sparam.getConsignorCode());		
					stockSearchKey.setLocationNo(sparam.getLocationNo());
					stockSearchKey.setItemCode(sparam.getItemCode());
					stockSearchKey.setCasePieceFlag(sparam.getCasePieceFlag());
					stockSearchKey.setStockId(sparam.getStockId(), "!=");
					stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
					stockSearchKey.setStockQty(0, "<=");
					if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
					{
						stockSearchKey.setUseByDate(sparam.getUseByDate());
					}
					
					stockSearchKey.setLastUpdateDateOrder(1, false);
					
					Stock[] modStock = (Stock[])stockObj.findForUpdate(stockSearchKey);
					
					//#CM11872
					// When the said target info does not exist,
					if (modStock == null || modStock.length <= 0)
					{
						//#CM11873
						// Update the Stock ID info of the parameter with the input info.
						wModStockId = sparam.getStockId();
					}
					else
					{
						//#CM11874
						// When the said target info exists,
						//#CM11875
						// Delete the inventory information obtained by the parameter stock ID.
						stockSearchKey.KeyClear();
						stockSearchKey.setStockId(sparam.getStockId());
				
						stockObj.drop(stockSearchKey);
						
						
						//#CM11876
						// Update the inventory information obtained in the above process for obtaining using the input info.
						wModStockId = modStock[0].getStockId();
					}

					//#CM11877
					// Update the Stock ID info of the parameter with the input info.
					stockAlterKey.KeyClear();
					//#CM11878
					// Update using stock ID.
					stockAlterKey.setStockId(wModStockId);
					//#CM11879
					// Consignor name
					stockAlterKey.updateConsignorName(sparam.getConsignorName());
					//#CM11880
					// Item Code
					stockAlterKey.updateItemCode(sparam.getItemCode());
					//#CM11881
					// Item Name
					stockAlterKey.updateItemName1(sparam.getItemName());
					//#CM11882
					// Case/Piece division
					stockAlterKey.updateCasePieceFlag(sparam.getCasePieceFlag());
					//#CM11883
					// Location No.
					stockAlterKey.updateLocationNo(sparam.getLocationNo());
					//#CM11884
					// Packed qty per Case
					stockAlterKey.updateEnteringQty(sparam.getEnteringQty());
					//#CM11885
					// Packed qty per bundle
					stockAlterKey.updateBundleEnteringQty(sparam.getBundleEnteringQty());
					//#CM11886
					// Stock Qty
					stockAlterKey.updateStockQty(sparam.getStockCaseQty() * sparam.getEnteringQty() +
													sparam.getStockPieceQty());
					//#CM11887
					// Allocatable qty (Set here the stock qty. Any stock already allocated is out of target here.)
					stockAlterKey.updateAllocationQty(sparam.getStockCaseQty() * sparam.getEnteringQty() +
														sparam.getStockPieceQty());
					//#CM11888
					// Case ITF
					stockAlterKey.updateItf(sparam.getITF());
					//#CM11889
					// Bundle ITF
					stockAlterKey.updateBundleItf(sparam.getBundleITF());
					//#CM11890
					// Storage Date
					stockAlterKey.updateInstockDate(sparam.getStorageDate());
					//#CM11891
					// Expiry Date
					stockAlterKey.updateUseByDate(sparam.getUseByDate());
					//#CM11892
					//last update pname
					stockAlterKey.updateLastUpdatePname(CLASS_NAME);
					//#CM11893
					// Area No.
					//#CM11894
					// Obtain the Area No. from the location info.
					stockAlterKey.updateAreaNo(lOperator.getAreaNo(sparam.getLocationNo()));
					
					stockObj.modify(stockAlterKey);
				}
				catch (NotFoundException e)
				{
					//#CM11895
					// Close the process when error via other terminal occurs.
					//#CM11896
					// 6023209 = No.{0} The data has been updated via other terminal.
					wMessage = "6023209" + wDelim + sparam.getRowNo();
					return null;
				}
				catch (InvalidDefineException e)
				{
					throw new ScheduleException(e.getMessage());
				}

				//#CM11897
				// Update the location info of the location before changed.(2005/06/13 Add By:T.T)
				lOperator.modifyLocateStatus(wStock.getLocationNo(), CLASS_NAME);
				
				//#CM11898
				// Update the location info of the location after changed.(2005/06/13 Add By:T.T)
				lOperator.modifyLocateStatus(sparam.getLocationNo(), CLASS_NAME);
						
				//#CM11899
				// Set the work result qty after modified.
				int workQty = sparam.getStockCaseQty() * sparam.getEnteringQty() +
												sparam.getStockPieceQty();
				//#CM11900
				// Set the area of the location after modified.
				sparam.setAreaNo(lOperator.getAreaNo(sparam.getLocationNo()));			

				//#CM11901
				// Execute the process for adding the Host sending info ( maintenance increase): After modified
				if (!createHostsend(conn, sparam, wModStockId, workercode,workername, sysdate, terminalno,
						SystemDefine.JOB_TYPE_MAINTENANCE_PLUS,CLASS_NAME, " ", Stock.TCDC_FLAG_DC, workQty))
				{
					return null;
				}

				//#CM11902
				// Add the Worker Result data inquiry.
				updateWorkerResult(conn, workercode,workername, sysdate, terminalno,
						SystemDefine.JOB_TYPE_MAINTENANCE_PLUS, workQty);
			}

			//#CM11903
			// Compile the latest display info.
			StockControlParameter[] viewParam = (StockControlParameter[])this.query(conn, wparam);
			
			//#CM11904
			// 6001004 = Modified.
			wMessage = "6001004";
			
			//#CM11905
			// Return the latest shipping Plan info.
			return viewParam;
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM11906
	// Package methods -----------------------------------------------

	//#CM11907
	// Protected methods ---------------------------------------------

	//#CM11908
	// Private methods -----------------------------------------------
	//#CM11909
	/** 
	 * Receive the contents entered via screen as a parameter and return the exclusion check result. <BR>
	 * Search through the inventory information using the stock ID of the parameter as a key. If no corresponding data exists, <BR>
	 * Regard any data with latest update date/time updated as exclusion error and return false. <BR>
	 * Return true if corresponding data exist and no changed in the last update date/time. <BR>
	 * Return false if corresponding data exist and the allocated qty is 1 or more. <BR>
	 * @param conn Instance to maintain database connection.
	 * @param checkParam Array of <CODE>StockControlParameter</CODE> class instance with input contents. <BR>
	 *        Designating any instance other than StockControlParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *        Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 * @return Return true if corresponding data exist and no changed in the last update date/time, or return false if the corresponding data exists and the allocated qty is 1 or more.
	 */
	public boolean modifycheck(Connection conn, Parameter checkParam) throws ScheduleException
	{
		//#CM11910
		// Generate inventory info instance of handlers.
		StockSearchKey wKey = new StockSearchKey();
		StockHandler wObj = new StockHandler(conn);
		Stock wStock = null;
		
		try
		{
			//#CM11911
			// Search conditions for parameters
			StockControlParameter wParam = (StockControlParameter)checkParam;
			
			//#CM11912
			// Set the search conditions.
			//#CM11913
			// Stock ID
			wKey.setStockId(wParam.getStockId());
			
			//#CM11914
			// Obtain the search result count of the inventory information.
			wStock = (Stock)wObj.findPrimary(wKey);
			
			if (wStock != null)
			{
				if (!wStock.getLastUpdateDate().equals(wParam.getLastUpdateDate()))
				{
					//#CM11915
					// Close the process when error via other terminal occurs.
					//#CM11916
					// 6023209 = No.{0} The data has been updated via other terminal.
					wMessage = "6023209" + wDelim + wParam.getRowNo();
					return false;
				}
			}
			else
			{
				//#CM11917
				// Close the process when error via other terminal occurs.
				//#CM11918
				// 6023209 = No.{0} The data has been updated via other terminal.
				wMessage = "6023209" + wDelim + wParam.getRowNo();
				return false;
			}

			//#CM11919
			// Check for the allocated qty.
			if (wStock.getStockQty() - wStock.getAllocationQty() > 0)
			{
				//#CM11920
				// The stock has been already allocated. Unable to modify/delete.
				//#CM11921
				// 6023291 = No.{0} Allocated stock. Neither Modification nor Delete is allowed.
				wMessage = "6023291" + wDelim + wParam.getRowNo();
				return false;
			}
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		return true;
	}

	//#CM11922
	/**
	 * Lock the inventory information to be updated.
	 * Generate array of Stock ID from the array of parameter designated by argument. And then,
	 * lock the corresponding inventory information.
	 * <DIR>
	 *    (search conditions)
	 *    <UL>
	 *     <LI>Stock ID</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param conn Instance to maintain database connection.
	 * @param param Array of <CODE>StockControlParameter</CODE> class instance <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws LockTimeOutException Announce when database is unlocked for the specified time.
	 * @throws NoPrimaryException Announce when the defined information is abnormal.
	 */
	protected void lockStockDataForRep(Connection conn, StockControlParameter[] param)
		throws NoPrimaryException, ReadWriteException,LockTimeOutException
	{
		String[] stockIdList = null;
		Vector vec = new Vector();
		String[] LocationNoList = null;
		Vector vec2 = new Vector();
		String[] AreaNoList = null;
		Vector vec3 = new Vector();
		
		StockHandler shandler = new StockHandler(conn);
		StockSearchKey skey = new StockSearchKey();
		
		LocateHandler locateHandler = new LocateHandler(conn);
		LocateSearchKey locatesearchkey = new LocateSearchKey();
		
		//#CM11923
		// Obtain the relocation rack area No. from WmsParam.
		String w_AreaNo = WmsParam.IDM_AREA_NO;
		
		//#CM11924
		// Obtain the corresponding Stock ID from the inventory information if any input info via screen already reside.
		for (int i = 0; i < param.length; i ++)
		{			
			skey.KeyClear();
			
			//#CM11925
			// Search Existing Info.
			skey.setStockId(param[i].getStockId());
			Stock stock = (Stock)shandler.findPrimary(skey);
			
			//#CM11926
			// Reserve in Vector
			if (stock != null)
			{
				vec.addElement(stock.getStockId());
				//#CM11927
				// Check whether the Location No. has been modified.
				if (!param[i].getLocationNo().equals(stock.getLocationNo()))
				{
					//#CM11928
					// Search Location Info before change
					locatesearchkey.KeyClear();
					locatesearchkey.setAreaNo(w_AreaNo);
					locatesearchkey.setLocationNo(stock.getLocationNo());
					//#CM11929
					// Search Existing Location Info.
					Locate locate = (Locate)locateHandler.findPrimary(locatesearchkey);
				
					//#CM11930
					// Reserve in Vector
					if (locate != null)
					{
						vec2.addElement(locate.getLocationNo());				
						vec3.addElement(locate.getAreaNo());				
					}
					//#CM11931
					// Search Location Info after changed.
					locatesearchkey.KeyClear();
					locatesearchkey.setAreaNo(w_AreaNo);
					locatesearchkey.setLocationNo(param[i].getLocationNo());
					//#CM11932
					// Search Existing Location Info.
					locate = (Locate)locateHandler.findPrimary(locatesearchkey);
				
					//#CM11933
					// Reserve in Vector
					if (locate != null)
					{
						vec2.addElement(locate.getLocationNo());				
						vec3.addElement(locate.getAreaNo());				
					}
				}
			}
		}
		
		//#CM11934
		// If any existing inventory information resides,
		if (vec.size() > 0 )
		{
			stockIdList = new String[vec.size()];
			vec.copyInto(stockIdList);
		
			skey.KeyClear();
			skey.setStockId(stockIdList);
		
			 //#CM11935
			 // Lock all the inventory information reserved in Vector.
			shandler.findForUpdateNowait(skey);
		}
		//#CM11936
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
		
			//#CM11937
			// Lock all the location info reserved in Vector.
			locateHandler.findForUpdateNowait(locatesearchkey);
		}
	}
	
	//#CM11938
	/**
	 * Lock the inventory information to be updated.
	 * Generate array of Stock ID from the array of parameter designated by argument. And then,
	 * lock the corresponding inventory information.
	 * <DIR>
	 *    (search conditions)
	 *    <UL>
	 *     <LI>Stock ID</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param conn Instance to maintain database connection.
	 * @param param Array of <CODE>StockControlParameter</CODE> class instance <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws LockTimeOutException Announce when database is unlocked for the specified time.
	 * @throws NoPrimaryException Announce when the defined information is abnormal.
	 */
	protected void lockStockDataForDel(Connection conn, StockControlParameter[] param)
		throws NoPrimaryException, ReadWriteException,LockTimeOutException
	{
		String[] stockIdList = null;
		Vector vec = new Vector();
		String[] LocationNoList = null;
		Vector vec2 = new Vector();
		String[] AreaNoList = null;
		Vector vec3 = new Vector();
		
		StockHandler shandler = new StockHandler(conn);
		StockSearchKey skey = new StockSearchKey();
		
		LocateHandler locateHandler = new LocateHandler(conn);
		LocateSearchKey locatesearchkey = new LocateSearchKey();
		
		//#CM11939
		// Obtain the relocation rack area No. from WmsParam.
		String w_AreaNo = WmsParam.IDM_AREA_NO;
		
		//#CM11940
		// Obtain the corresponding Stock ID from the inventory information if any input info via screen already reside.
		for (int i = 0; i < param.length; i ++)
		{			
			skey.KeyClear();
			
			//#CM11941
			// Search Existing Info.
			skey.setStockId(param[i].getStockId());
			Stock stock = (Stock)shandler.findPrimary(skey);
			
			//#CM11942
			// Reserve in Vector
			if (stock != null)
			{
				vec.addElement(stock.getStockId());
			
				//#CM11943
				// Search Location Info before change
				locatesearchkey.KeyClear();
				locatesearchkey.setAreaNo(w_AreaNo);
				locatesearchkey.setLocationNo(stock.getLocationNo());
				//#CM11944
				// Search Existing Location Info.
				Locate locate = (Locate)locateHandler.findPrimary(locatesearchkey);
			
				//#CM11945
				// Reserve in Vector
				if (locate != null)
				{
					vec2.addElement(locate.getLocationNo());				
					vec3.addElement(locate.getAreaNo());				
				}
			}
		}
		
		//#CM11946
		// If any existing inventory information resides,
		if (vec.size() > 0 )
		{
			stockIdList = new String[vec.size()];
			vec.copyInto(stockIdList);
		
			skey.KeyClear();
			skey.setStockId(stockIdList);
		
			//#CM11947
			// Lock all the inventory information reserved in Vector.
			shandler.findForUpdateNowait(skey);
		}
		//#CM11948
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
		
			//#CM11949
			// Lock all the location info reserved in Vector.
			locateHandler.findForUpdateNowait(locatesearchkey);
		}
	}

	
	//#CM11950
	/**
	 * Add the Sending Result Info table (DNHOSTSEND) <BR> 
	 * <BR>     
	 * Add the Sending Result Information based on the contents of the Received parameter. <BR>
	 * <BR>
	 * @param conn        Instance to maintain database connection.
	 * @param vstock      Instance of Stock class
	 * @param stockid     Stock ID
	 * @param workercode  Worker Code
	 * @param workername  Worker Name
	 * @param sysdate     Work Date ( System defined date)
	 * @param terminalno  Terminal No.
	 * @param jobtype     Work division
	 * @param processname Process name
	 * @param batchno     Batch No.
	 * @param tcdckbn	   TCDC division
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 */
	protected boolean createHostSend(
		Connection conn,
		Stock vstock,
		String stockid,
		String workercode,
		String workername,
		String sysdate,
		String terminalno,
		String jobtype,
		String processname,
		String batchno,
		String tcdckbn)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			HostSendHandler hostsendHandler = new HostSendHandler(conn);
			HostSend hostsend = new HostSend();

			//#CM11951
			// Obtain each unique key to add.
			SequenceHandler sequence = new SequenceHandler(conn);
			//#CM11952
			// Work No.
			String jobno = sequence.nextJobNo();
			//#CM11953
			// Result qty
			int inputqty = vstock.getStockQty();

			//#CM11954
			// Work Date
			hostsend.setWorkDate(sysdate);
			//#CM11955
			// Work No.
			hostsend.setJobNo(jobno);
			//#CM11956
			// Work division
			hostsend.setJobType(jobtype);
			//#CM11957
			// Aggregation Work No. (equal to Work No.)
			hostsend.setCollectJobNo(jobno);
			//#CM11958
			// Status flag (Completed)
			hostsend.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
			//#CM11959
			// Start Work flag (Started)
			hostsend.setBeginningFlag(SystemDefine.BEGINNING_FLAG_STARTED);
			//#CM11960
			// Plan unique key
			hostsend.setPlanUkey("");
			//#CM11961
			// Stock ID
			hostsend.setStockId(stockid);
			//#CM11962
			// Area No.
			hostsend.setAreaNo(vstock.getAreaNo());
			//#CM11963
			// Location No.
			hostsend.setLocationNo(vstock.getLocationNo());
			//#CM11964
			// Planned date
			hostsend.setPlanDate(sysdate);
			//#CM11965
			// Consignor code
			hostsend.setConsignorCode(vstock.getConsignorCode());
			//#CM11966
			// Consignor name
			hostsend.setConsignorName(vstock.getConsignorName());
			//#CM11967
			// Supplier Code
			hostsend.setSupplierCode("");
			//#CM11968
			// Supplier Name
			hostsend.setSupplierName1("");
			//#CM11969
			// Receiving ticket No.
			hostsend.setInstockTicketNo("");
			//#CM11970
			// Receiving ticket Line No.
			hostsend.setInstockLineNo(0);
			//#CM11971
			// customer code
			hostsend.setCustomerCode("");
			//#CM11972
			// customer name
			hostsend.setCustomerName1("");
			//#CM11973
			// Shipping Ticket No
			hostsend.setShippingTicketNo("");
			//#CM11974
			// Shipping ticket line No.
			hostsend.setShippingLineNo(0);
			//#CM11975
			// Item Code
			hostsend.setItemCode(vstock.getItemCode());
			//#CM11976
			// Item Name
			hostsend.setItemName1(vstock.getItemName1());
			//#CM11977
			// Work Planned Qty(Host Planned Qty)
			hostsend.setHostPlanQty(0);
			//#CM11978
			// Work Planned Qty
			hostsend.setPlanQty(0);
			//#CM11979
			// Possible Work Qty
			hostsend.setPlanEnableQty(0);
			//#CM11980
			// Work Result qty
			hostsend.setResultQty(inputqty);
			//#CM11981
			// Work shortage qty
			hostsend.setShortageCnt(0);
			//#CM11982
			// Pending Qty
			hostsend.setPendingQty(0);
			//#CM11983
			// Packed qty per Case
			hostsend.setEnteringQty(vstock.getEnteringQty());
			//#CM11984
			// Packed qty per bundle
			hostsend.setBundleEnteringQty(vstock.getBundleEnteringQty());
			//#CM11985
			// Case/Piece division(Load Size)
			hostsend.setCasePieceFlag(vstock.getCasePieceFlag());
			//#CM11986
			// Case/Piece division(Work type)
			hostsend.setWorkFormFlag(vstock.getCasePieceFlag());
			//#CM11987
			// Case ITF
			hostsend.setItf(vstock.getItf());
			//#CM11988
			// Bundle ITF
			hostsend.setBundleItf(vstock.getBundleItf());
			//#CM11989
			// TC/DC Division
			hostsend.setTcdcFlag(tcdckbn);
			//#CM11990
			// Expiry Date
			hostsend.setUseByDate(vstock.getUseByDate());
			//#CM11991
			// Lot No.
			hostsend.setLotNo("");
			//#CM11992
			// Plan information Comment
			hostsend.setPlanInformation("");
			//#CM11993
			// Order No.
			hostsend.setOrderNo("");
			//#CM11994
			// Order Date
			hostsend.setOrderingDate("");
			//#CM11995
			// Expiry Date
			hostsend.setResultUseByDate(vstock.getUseByDate());
			//#CM11996
			// Lot No.
			hostsend.setResultLotNo("");
			//#CM11997
			// Work Result Location
			hostsend.setResultLocationNo(vstock.getLocationNo());
			//#CM11998
			// Work report flag(Sent)
			hostsend.setReportFlag(SystemDefine.REPORT_FLAG_SENT);
			//#CM11999
			// Batch No.(Schedule No.)
			hostsend.setBatchNo(batchno);

			AreaOperator areaOpe = new AreaOperator(conn);
			//#CM12000
			// System distinct key
			hostsend.setSystemDiscKey(Integer.parseInt(areaOpe.getAreaType(vstock.getAreaNo())));
			//#CM12001
			// Worker Code
			hostsend.setWorkerCode(workercode);
			//#CM12002
			// Worker Name
			hostsend.setWorkerName(workername);
			//#CM12003
			// Terminal No.RFTNo.
			hostsend.setTerminalNo(terminalno);
			//#CM12004
			// Plan information Added Date/Time
			hostsend.setPlanRegistDate(new Date());
			//#CM12005
			// Added Date/Time
			hostsend.setRegistDate(new Date());
			//#CM12006
			// Name of Add Process
			hostsend.setRegistPname(processname);
			//#CM12007
			// Last update date/time
			hostsend.setLastUpdateDate(new Date());
			//#CM12008
			// Last update process name
			hostsend.setLastUpdatePname(processname);

			//#CM12009
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

}
//#CM12010
//end of class
