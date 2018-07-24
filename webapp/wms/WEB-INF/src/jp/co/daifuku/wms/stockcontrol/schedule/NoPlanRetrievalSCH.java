package jp.co.daifuku.wms.stockcontrol.schedule;

//#CM10967
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
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
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.stockcontrol.report.NoPlanRetrievalWriter;

//#CM10968
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda  <BR>
 * <BR>
 * This class executes WEB Unplanned retrieval. <BR>
 * Receive the contents entered via screen as a parameter and execute the process for Unplanned Retrieval. <BR>
 * Each method in this class receives a connection object and executes the process for updating the database. <BR>
 * However, each method disables to commit and roll back of transactions. <BR>
 * This class executes the following processes. <BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method) <BR><BR><DIR>
 *   If only one Consignor code exist in the inventory information, return the corresponding Consignor code. <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist. <BR>
 * <BR>
 *   <Search conditions> <BR><DIR>
 *     Stock status:Center Inventory(2) <BR>
 *     Stock Qty:More than 1 <BR>
 *     inventory information.Area No =Area master info .Area No  <BR>
 *     Area master info .Area Division=Other than AS/RS </DIR></DIR>
 * <BR>
 * 2.Check Process by clicking display button(<CODE>check()</CODE>Method)<BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it.<BR>
 *   Return <CODE>Parameter</CODE> array with the number of elements 0 if no corresponding data found. Or, return null when condition error occurs.<BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 *   Display the Item code, Case/Piece division, and Location No. in this order. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR><DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Consignor code* <BR>
 *     Item Code <BR>
 *     Case/Piece division* <BR>
 *     Start Location No.. <BR>
 *     End Location No.. <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     customer code <BR>
 *     customer name <BR></DIR>
 * <BR>
 *   <Returned data> <BR><DIR>
 *     Consignor code <BR>
 *     Consignor name <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Case/Piece division <BR>
 *     Location No. <BR>
 *     Packed qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Allocatable Packed qty per Case((Stock Qty-allocated qty)/Packed qty per Case) <BR>
 *     Allocatable Packed qty per Piece((Stock Qty-allocated qty)%Packed qty per Case) <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Expiry Date <BR>
 *     Stock ID <BR>
 *     Last update date/time <BR></DIR></DIR>
 * <BR>
 * 3.Process by clicking on Start Picking button (<CODE>startSCHgetParams()</CODE>Method) <BR><BR><DIR>
 *   Receive the contents displayed in the preset area as a parameter and execute the process for Unplanned retrieval. <BR>
 *   If the Unplanned picking work list print division of the parameter is True when completed the process, start-up the class for printing the Unplanned picking work list. <BR>
 *   Re-obtain the data to be output to the preset area from database when the process normally completed, and return it. <BR>
 *   Return null when the schedule failed to complete due to condition error or other causes. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 *   <BR>
 *   Receive only the update target data as a parameter. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Consignor code* <BR>
 *     Consignor name* <BR>
 *     Item Code* <BR>
 *     Item Name* <BR>
 *     Case/Piece division* <BR>
 *     Location No.* <BR>
 *     Packed qty per Case* <BR>
 *     Packed qty per bundle* <BR>
 *     CaseShipping qty* <BR>
 *     PieceShipping qty* <BR>
 *     Case ITF* <BR>
 *     Bundle ITF* <BR>
 *     Expiry Date <BR>
 *     Print the Unplanned picking work report.division* <BR>
 *     division All* <BR>
 *     Stock ID* <BR>
 *     Line No. <BR>
 *     Last update date/time* <BR></DIR>
 * <BR>
 *   <Returned data> <BR><DIR>
 *     Consignor code <BR>
 *     Consignor name <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Case/Piece division <BR>
 *     Location No. <BR>
 *     Packed qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Allocatable Packed qty per Case((Stock Qty-allocated qty)/Packed qty per Case) <BR>
 *     Allocatable Packed qty per Piece((Stock Qty-allocated qty)%Packed qty per Case) <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Expiry Date <BR>
 *     Stock ID <BR>
 *     Last update date/time <BR></DIR></DIR>
 * <BR>
 *   <Shipping Start process> <BR>
 * <DIR>
 *   <Check for process condition> <BR>
 *     1.Ensure to define Worker code and password in the Worker master. <BR><DIR>
 *       Check only the leading value of the array for the values of Worker code and password. <BR></DIR>
 *     2.Ensure that inventory information table of Stock ID exists in the database. <BR>
 *     3.Require to correspond the last update date/time of the parameter to the last update date/time of the inventory information table. (exclusion check) <BR>
 *     4.Check Input Value Process(<CODE>AbstructStockControlSCH()</CODE>Class) <BR>
 *     5.Input any value not more than allocatable qty for picking qty.<BR>
 * <BR>
 *   <Update/Add Process> <BR>
 *     -Update Inventory Information Table (DMSTOCK). <BR>
 *       Update the inventory information linked to Stock ID parameterbased on the contents of the Received parameter, <BR>
 *       Execute the retrieval process for allocatable qty for data with All parameter division True. <BR>
 *       1.Decrease the picking qty from the stock and update the stock qty. <BR>
 *       2.Update the last Update process name. <BR>
 *       3.Update the last picking date. (Set the System date.) <BR>
 * <BR>
 *     -Add Sending Result Info Table (DNHOSTSEND) <BR>
 *       Add the Sending Result Info based on the inventory information contents updated this time. <BR>
 * <BR>
 *     -Update/Add Worker Result Data Inquiry Table (DNWORKERRESULT). <BR>
 *       Add the Worker Result data inquiry based on the contents in the parameter. <BR></DIR>
 * </DIR>
 * </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/22</TD><TD>K.Toda</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/10 05:47:54 $
 * @author  $Author: suresh $
 */
public class NoPlanRetrievalSCH extends AbstractStockControlSCH 
{

	//#CM10969
	// Class variables -----------------------------------------------
	//#CM10970
	/**
	 * Class Name(Shipping inspection)
	 */
	public static String CLASS_RETRIEVAL = "NoPlanRetrievalSCH";
	
	//#CM10971
	// Class method --------------------------------------------------
	//#CM10972
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ( "$Revision: 1.2 $,$Date: 2006/10/10 05:47:54 $" );
	}
	//#CM10973
	// Constructors --------------------------------------------------
	//#CM10974
	/**
	 * Initialize this class.
	 */
	public NoPlanRetrievalSCH()
	{
		wMessage = null;
	}

	//#CM10975
	// Public methods ------------------------------------------------
	//#CM10976
	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * If only one Consignor code exist in the inventory information, return the corresponding Consignor code.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Instance to maintain database connection.
	 * @param searchParam <CODE>StockControlParameter</CODE> class instance with conditions to obtain the display data.<BR>
	 *         Designating any instance other than <CODE>StockControlParameter</CODE> throws ScheduleException.<BR>
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

		//#CM10977
		// Search Data
		//#CM10978
		// Stock status(Center Inventory)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM10979
		// Ensure that the stock qty is 1 or more.
		searchKey.setStockQty(1, ">=");

		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");
		//#CM10980
		// Obtain the Area other than ASRS and add it to the search conditions.
		//#CM10981
		// Search for IS NULL if no corresponding area found.
		areaNo = AreaOperator.getAreaNo(areaType);
		searchKey.setAreaNo(areaNo);

		if (stockFinder.search(searchKey) == 1)
		{
			//#CM10982
			// Search Data			
			searchKey.KeyClear();
			//#CM10983
			// Stock status(Center Inventory)
			searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM10984
			// Ensure that the stock qty is 1 or more.
			searchKey.setStockQty(1, ">=");
			//#CM10985
			// Obtain the Consignor Name with the later added date/time.
			searchKey.setRegistDateOrder(1, false);
			//#CM10986
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
	
	//#CM10987
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

		StockControlParameter param = (StockControlParameter)searchParam;

		//#CM10988
		// Check the Worker code and password
		if (!checkWorker(conn, param))
		{
			 return null;
		}

		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey searchKey = new StockSearchKey();
		StockSearchKey namesearchKey = new StockSearchKey();

		//#CM10989
		// Search Data
		//#CM10990
		// Stock status(Center Inventory)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM10991
		// Ensure that the stock qty is 1 or more.
		searchKey.setStockQty(1, ">=");

		//#CM10992
		// Consignor code
		searchKey.setConsignorCode(param.getConsignorCodeDisp());
		namesearchKey.setConsignorCode(param.getConsignorCodeDisp());
		//#CM10993
		// Item Code
		String itemcode = param.getItemCodeDisp();
		if (!StringUtil.isBlank(itemcode))
		{
			searchKey.setItemCode(itemcode);
			namesearchKey.setItemCode(itemcode);
		}
		//#CM10994
		// Case/Piece division(Work type)
		//#CM10995
		// other than All
		if (!param.getCasePieceFlagDisp().equals(StockControlParameter.CASEPIECE_FLAG_ALL))
		{
			//#CM10996
			// Case
			if(param.getCasePieceFlagDisp().equals(StockControlParameter.CASEPIECE_FLAG_CASE))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
			}
			//#CM10997
			// Piece
			else if(param.getCasePieceFlagDisp().equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
			}
			//#CM10998
			// None
			else if(param.getCasePieceFlagDisp().equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
			}
		}
		
		//#CM10999
		// Start Location
		String fromlocation = param.getFromLocationNoDisp();
		if (!StringUtil.isBlank(fromlocation))
		{
			searchKey.setLocationNo(fromlocation, ">=");
			namesearchKey.setLocationNo(fromlocation, ">=");
		}
		//#CM11000
		// End Location
		String tolocation = param.getToLocationNoDisp();
		if (!StringUtil.isBlank(tolocation))
		{
			searchKey.setLocationNo(tolocation, "<=");
			namesearchKey.setLocationNo(tolocation, "<=");
		}
		//#CM11001
		// Case ITF
		String itf = param.getITFDisp();
		if (!StringUtil.isBlank(itf))
		{
			searchKey.setItf(itf);
			namesearchKey.setItf(itf);
		}
		//#CM11002
		// BundleITF
		String bundleitf = param.getBundleITFDisp();
		if (!StringUtil.isBlank(bundleitf))
		{
			searchKey.setBundleItf(bundleitf);
			namesearchKey.setBundleItf(bundleitf);
		}		

		AreaOperator AreaOperator = new AreaOperator(conn);
		
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;

		//#CM11003
		// Obtain the Area other than ASRS and add it to the search conditions.
		//#CM11004
		// Search for IS NULL if no corresponding area found.
		areaNo = AreaOperator.getAreaNo(areaType);
		searchKey.setAreaNo(areaNo);

		//#CM11005
		// Display in the order of Item code, Case/Piece division, and then Location.
		searchKey.setItemCodeOrder(1, true);
		searchKey.setCasePieceFlagOrder(2, true);
		searchKey.setLocationNoOrder(3, true);
		
		//#CM11006
		// Obtain the count of data to be displayed.
		if(!canLowerDisplay(stockHandler.count(searchKey)))
		{
			return returnNoDisplayParameter();
		}
		//#CM11007
		// Execute the search.
		Stock[] resultEntity = (Stock[])stockHandler.find(searchKey);
	
		//#CM11008
		// Obtain the Consignor Name with the later added date/time.
		namesearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM11009
		// Ensure that the stock qty is 1 or more.
		namesearchKey.setStockQty(1, ">=");
		namesearchKey.setLastUpdateDateOrder(1, false);
		Stock[] stock = (Stock[])stockHandler.find(namesearchKey);
		String consignorname = "";
		if(stock != null && stock.length != 0)
		{
			consignorname = stock[0].getConsignorName();
		}	

		Vector vec = new Vector();

		for (int i = 0; i < resultEntity.length; i++)
		{
			StockControlParameter dispData = new StockControlParameter();
			//#CM11010
			// Consignor code
			dispData.setConsignorCode(resultEntity[i].getConsignorCode());
			//#CM11011
			// Consignor Name (Consignor Name with later Added Date/Time) 
			dispData.setConsignorName(consignorname);
			//#CM11012
			// Item Code
			dispData.setItemCode(resultEntity[i].getItemCode());
			//#CM11013
			// Item Name
			dispData.setItemName(resultEntity[i].getItemName1());
			//#CM11014
			// Case/Piece division
			dispData.setCasePieceFlag(resultEntity[i].getCasePieceFlag());
			//#CM11015
			// Case/Piece division name
			//#CM11016
			// Obtain Case/Piece division name from Case/Piece division (work type).
			String casepiecename = DisplayUtil.getPieceCaseValue(resultEntity[i].getCasePieceFlag());
			dispData.setCasePieceFlagName(casepiecename);
			//#CM11017
			// shipping Location
			dispData.setLocationNo(resultEntity[i].getLocationNo()); 			
			//#CM11018
			// Packed qty per Case
			dispData.setEnteringQty(resultEntity[i].getEnteringQty());
			//#CM11019
			// Packed qty per bundle
			dispData.setBundleEnteringQty(resultEntity[i].getBundleEnteringQty());
			//#CM11020
			// Set the quotient of allocatable qty divided by case for allocatable case qty.
			dispData.setAllocateCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getAllocationQty(),
					resultEntity[i].getEnteringQty(), resultEntity[i].getCasePieceFlag()));
			//#CM11021
			// Set the remainder of allocatable qty divided by case for allocatable piece qty.
			dispData.setAllocatePieceQty(DisplayUtil.getPieceQty(resultEntity[i].getAllocationQty(),
					resultEntity[i].getEnteringQty(), resultEntity[i].getCasePieceFlag()));		
			//#CM11022
			// Case ITF
			dispData.setITF(resultEntity[i].getItf());
			//#CM11023
			// BundleITF
			dispData.setBundleITF(resultEntity[i].getBundleItf());			
			//#CM11024
			// Expiry Date
			dispData.setUseByDate(resultEntity[i].getUseByDate());
			//#CM11025
			// Inventory ID
			dispData.setStockId(resultEntity[i].getStockId());
			//#CM11026
			// Last update date/time
			dispData.setLastUpdateDate(resultEntity[i].getLastUpdateDate());
			vec.addElement(dispData);
		}

		StockControlParameter[] paramArray = new StockControlParameter[vec.size()];
		vec.copyInto(paramArray);

		//#CM11027
		// 6001013 = Data is shown.
		wMessage = "6001013";
		return paramArray;
	}

	//#CM11028
	/**
	 * Receive the contents entered via screen as a parameter and start the schedule for Unplanned Retrieval.<BR>
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
			//#CM11029
			// Check the Worker code and password
			StockControlParameter workparam = (StockControlParameter)startParams[0];
			if (!checkWorker(conn, workparam))
			{
				return null;
			}

			//#CM11030
			// Check the Daily Update Processing.
			if (isDailyUpdate(conn))
			{
				return null;
			}	

			//#CM11031
			// Check for input in the preset area.
			if (!checkList(startParams))
			{
				return null;
			}

			try
			{
				//#CM11032
				// Lock Process(inventory information,  Location info)
				lockStockData(conn, (StockControlParameter[])startParams);	
			}
			catch(LockTimeOutException e)
			{
				//#CM11033
				// 6027008 = This data is not treatable because it is updating it with other terminals.
				wMessage = "6027008";
				return null;
			}

			//#CM11034
			// Obtain Worker Info
			//#CM11035
			// Worker Code
			String workercode = workparam.getWorkerCode();
			//#CM11036
			// Worker Name
			String workername = getWorkerName(conn, workparam.getWorkerCode());

			//#CM11037
			// Work Date ( System defined date)
			String sysdate = getWorkDate(conn);
			
			//#CM11038
			// Parameter for generating Result data inquiry
			//#CM11039
			// Terminal No.
			String terminalno = workparam.getTerminalNumber();
			//#CM11040
			// Work division(Unplanned picking)
			String jobtype = SystemDefine.JOB_TYPE_EX_RETRIEVAL;
			//#CM11041
			// Obtain each unique key to add.
			SequenceHandler sequence = new SequenceHandler(conn);
			//#CM11042
			// Batch No.(Common for one commitment)
			String batch_seqno = sequence.nextNoPlanBatchNo();
			//#CM11043
			// TCDC division(DC)
			String tcdckbn = SystemDefine.TCDC_FLAG_DC ;
			
			//#CM11044
			// This variable sums up this time work quantity.
			int workqty = 0;
			//#CM11045
			// Execute the process for the data extracted by the parameter.
			for (int i = 0; i < startParams.length; i++)
			{
				StockControlParameter param = (StockControlParameter)startParams[i];
				String stockid = param.getStockId();
				int retrievalqty = 0;

				//#CM11046
				// Placing a check in All division for parameter enables to pick the allocatable qty.
				if(param.getTotalFlag() == true)
				{
					retrievalqty = param.getAllocateCaseQty() * param.getEnteringQty() + param.getAllocatePieceQty();
				}
				else
				{
					retrievalqty = param.getRetrievalCaseQty() * param.getEnteringQty() + param.getRetrievalPieceQty();
				}
				
				//#CM11047
				// Work Quantity (Totalize the result qty of Work status for one commitment)
				workqty = addWorkQty(workqty, retrievalqty);
				
				//#CM11048
				// Update inventory information table
				if (!updateStock(conn, stockid, retrievalqty, param.getLastUpdateDate(), param.getRowNo()))
				{
					return null;
				}
				
				//#CM11049
				// Update the location info of the location picked.(2005/06/14 Add By:T.T)
				LocateOperator lOperator = new LocateOperator(conn);
				lOperator.modifyLocateStatus(param.getLocationNo(), CLASS_RETRIEVAL);
					
				//#CM11050
				// Set the area of the picked location.
				param.setAreaNo(lOperator.getAreaNo(param.getLocationNo()));
				
				//#CM11051
				// Add Sending Result Info Table (DNHOSTSEND)
				if (!createHostsend(conn, param, stockid, workercode, workername, sysdate, terminalno, jobtype, CLASS_RETRIEVAL, batch_seqno, tcdckbn, retrievalqty))
				{
					return null;
				}
			}

			//#CM11052
			// Add the Worker Result data inquiry table (DNWORKERRESULT).
			updateWorkerResult(conn, workercode, workername, sysdate, terminalno, jobtype, workqty);
	
			//#CM11053
			// Succeeded in scheduling.
			StockControlParameter[] viewParam = (StockControlParameter[])this.query(conn, workparam);

			//#CM11054
			// Generate a file for Unplanned Storage Result list.
			if(workparam.getListFlg() == true)
			{
				NoPlanRetrievalWriter writer = new NoPlanRetrievalWriter(conn);
				writer.setBatchNumber(batch_seqno);
				
				if(writer.startPrint())
				{
					//#CM11055
					// 6021012 = Data had been set and the list was printed successfully.
					wMessage = "6021012";
				}
				else
				{
					//#CM11056
					// 6007042 = Printing failed after setup. Please refer to log.
					wMessage = "6007042";
				}
			}
			else
			{
				//#CM11057
				// 6001006 = Data was committed.
				wMessage = "6001006";
			}
	
			return viewParam;
			
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM11058
	// Protected methods ---------------------------------------------
	//#CM11059
	/** 
	 * Check for input in the preset area.<BR>
	 * Invoking this throws <CODE>ScheduleException</CODE>.
	 * @param searchParams Array of <CODE>StockControlParameter</CODE> class instance with commitment contents.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 * @return true: if the input content is proper  false: if the input content is not proper
	 */
	protected boolean checkList(Parameter[] searchParams) throws ReadWriteException, ScheduleException
	{
		for (int i = 0; i < searchParams.length; i++)
		{
			StockControlParameter param = (StockControlParameter)searchParams[i];

			//#CM11060
			// Obtain the Packed qty per case, stock qty, picking qty ( screen input data) from the parameter.
			String casepieceflag = param.getCasePieceFlag();
			int enteringqty = param.getEnteringQty();
			int caseqty = param.getRetrievalCaseQty();
			int pieceqty = param.getRetrievalPieceQty();
						
			//#CM11061
			// Line No.
			int rowno = param.getRowNo();		
			
			long allocaionqty = (long)param.getAllocateCaseQty() * (long)enteringqty + (long)param.getAllocatePieceQty();
			
			//#CM11062
			// If the allocatable qty is 0, 
			if ( allocaionqty == 0)
			{
				//#CM11063
				// 6023380=No.{0} Allocatable Qty. is 0
				wMessage = "6023380" + wDelim + rowno;
				return false;
			}
			
			//#CM11064
			// Placing a check placed in All picking disables to execute the following checks.
			if(param.getTotalFlag())
			{
				continue;
			}
			
			//#CM11065
			// Check Input Value
			if (!stockRetrievalInputCheck(casepieceflag, enteringqty, caseqty, pieceqty, rowno))
			{
				return false;
			}
			
			//#CM11066
			// Check Overflow.
			long retrievalqty = (long)param.getRetrievalCaseQty() * (long)param.getEnteringQty() + (long)param.getRetrievalPieceQty();
			//#CM11067
			// If the picking qty is set to 1 or more,
			if (retrievalqty > 0)
			{
				//#CM11068
				// Picking qty ( input data) is larger than Allocatable qty
				if ( retrievalqty > allocaionqty )
				{
					//#CM11069
					// 6023216=No.{0} Please enter the allocation quantity or less for the picking quantity at the picking work.
					wMessage = "6023216" + wDelim + rowno;
					return false;
				}
			}
			if (retrievalqty > WmsParam.MAX_STOCK_QTY)
			{
				//#CM11070
				// 6023272 = No.{0} Please enter {2} or smaller for {1}.
				wMessage = "6023272" + wDelim + rowno + wDelim + DisplayText.getText("LBL-W0420") + wDelim + MAX_STOCK_QTY_DISP;
				return false;
			}

		}
		return true;
	}

	//#CM11071
	// Package methods -----------------------------------------------

	//#CM11072
	// Protected methods ---------------------------------------------

	//#CM11073
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
	 * @param	param Array of <CODE>StockControlParameter</CODE> class instance with commitment contents.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws LockTimeOutException Announce when database is unlocked for the specified time.
	 * @throws NoPrimaryException Announce when the defined information is abnormal.
	 */
	protected void lockStockData(Connection conn, StockControlParameter[] param)
		throws ReadWriteException, LockTimeOutException, NoPrimaryException
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
		
		//#CM11074
		// Obtain the relocation rack area No. from WmsParam.
		String w_AreaNo = WmsParam.IDM_AREA_NO;
		
		//#CM11075
		// Obtain the corresponding Stock ID from the inventory information if any input info via screen already reside.
		for (int i = 0; i < param.length; i ++)
		{			
			skey.KeyClear();
			
			//#CM11076
			// Search Existing Info.
			skey.setStockId(param[i].getStockId());
			Stock stock = (Stock)shandler.findPrimary(skey);
			
			//#CM11077
			// Reserve in Vector
			if (stock != null)
			{
				vec.addElement(stock.getStockId());
			
				//#CM11078
				// Search Location Info
				locatesearchkey.KeyClear();
				locatesearchkey.setAreaNo(w_AreaNo);
				locatesearchkey.setLocationNo(stock.getLocationNo());
				//#CM11079
				// Search Existing Location Info.
				Locate locate = (Locate)locateHandler.findPrimary(locatesearchkey);
			
				//#CM11080
				// Reserve in Vector
				if (locate != null)
				{
					vec2.addElement(locate.getLocationNo());				
					vec3.addElement(locate.getAreaNo());				
				}
			}
		}
		
		//#CM11081
		// If any existing inventory information resides,
		if (vec.size() > 0 )
		{
			stockIdList = new String[vec.size()];
			vec.copyInto(stockIdList);
		
			skey.KeyClear();
			skey.setStockId(stockIdList);
		
			//#CM11082
			// Lock all the inventory information reserved in Vector.
			shandler.findForUpdateNowait(skey);
		}
		//#CM11083
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
		
			//#CM11084
			// Lock all the location info reserved in Vector.
			locateHandler.findForUpdateNowait(locatesearchkey);
		}
	}
	
	//#CM11085
	// Private methods -----------------------------------------------
	//#CM11086
	/**
	 * Update the inventory information table.<BR>
	 * @param conn Database connection object<BR>
	 * @param stockid        Inventory ID
	 * @param retrievalqty   Shipping qty
	 * @param lastupdatedate Last update date/time
	 * @param rowno		      Line No.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 */
	private boolean updateStock(Connection conn, String stockid, int retrievalqty, Date lastupdatedate, int rowno) 
																		throws ReadWriteException, ScheduleException
	{
		try
		{
			StockHandler stockHandler = new StockHandler(conn);
			StockSearchKey stocksearchKey = new StockSearchKey();
			//#CM11087
			// Search Data
			//#CM11088
			// Inventory ID
			stocksearchKey.setStockId(stockid);
			//#CM11089
			// Inventory Status(Center Inventory)
			stocksearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM11090
			// Stock qty is 1 or larger
			stocksearchKey.setStockQty(1, ">=");

			Stock stock = (Stock)stockHandler.findPrimary(stocksearchKey);
			
			if (stock != null)
			{
				if (!stock.getLastUpdateDate().equals(lastupdatedate))
				{
					//#CM11091
					// Close the process when error via other terminal occurs.
					//#CM11092
					// 6023209 = No.{0} The data has been updated via other terminal.
					wMessage = "6023209" + wDelim + rowno;
					return false;
				}
				
				//#CM11093
				// Decrease the picking qty of the parameter from the stock qty of the inventory information and then calculate the stock qty after picking.
				int resultqty = stock.getStockQty() - retrievalqty;

				//#CM11094
				// If the stock qty is 0 (zero) after picking, deletes the stock.
				if (resultqty == 0)
				{
					stocksearchKey.KeyClear();
					stocksearchKey.setStockId(stock.getStockId());
					stockHandler.drop(stocksearchKey);
				}
				//#CM11095
				// If stock qty is more than 0, update the stock qty based on the contents of the Received parameter stock qty. 
				else
				{
					StockAlterKey stockAlterKey = new StockAlterKey();
					stockAlterKey.setStockId(stockid);
					stockAlterKey.updateStockQty(resultqty);
					stockAlterKey.updateAllocationQty(stock.getAllocationQty() - retrievalqty);
					stockAlterKey.updateLastUpdatePname(CLASS_RETRIEVAL);
					
					stockHandler.modify(stockAlterKey);
				}

				return true;
			}
			else
			{
				//#CM11096
				// Close the process when error via other terminal occurs.
				//#CM11097
				// 6023209 = No.{0} The data has been updated via other terminal.
				wMessage = "6023209" + wDelim + rowno;
				return false;
			}
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
}
//#CM11098
//end of class
