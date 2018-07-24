//#CM44643
//$Id: AsNoPlanRetrievalSCH.java,v 1.2 2006/10/30 01:04:32 suresh Exp $

//#CM44644
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.dbhandler.ASStationHandler;
import jp.co.daifuku.wms.asrs.dbhandler.ASStockHandler;
import jp.co.daifuku.wms.asrs.dbhandler.ASWorkPlaceHandler;
import jp.co.daifuku.wms.asrs.entity.ASShelfStock;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.WorkPlace;
import jp.co.daifuku.wms.asrs.report.AsNoPlanRetrievalWriter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM44645
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono  <BR>
 * <BR>
 * Class to set WEB ASRS No plan retrieval. <BR>
 * Receive the content input from the screen as parameter, and do the ASRS No plan retrieval setting processing. <BR>
 * Do not do the commit and rollback of the transaction though each Method of this Class must <BR>
 * use the connection object and do the update processing of the receipt data base. <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Initial display processing(< CODE>initFind() </ CODE > method) <BR><BR><DIR>
 *   Return corresponding Consignor Code when only one Consignor Code exists in the inventory information.  <BR>
 *   Return null when pertinent data does not exist or it exists by two or more.  <BR>
 * <BR>
 *   <Search condition> <BR><DIR>
 *     Stock status:Center Stocking(2) <BR>
 *     Stock qty : More than 1<BR>
 *     AS/RS Stock </DIR></DIR>
 * <BR>
 * 2.Process when Display button is pressed(<CODE>check()</CODE>Method)<BR><BR><DIR>
 *   The content input from the screen is received as parameter, and filtered, and acquire from the database and return data for the area output. <BR>
 *   Return empty array of <CODE>Parameter</CODE> when pertinent data is not found. Moreover, return null when the condition error etc. occur. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
 *   Display in order of Item Code, Case/Piece flag, and Location No . . Expiry date. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR><DIR>
 *     Worker code* <BR>
 *     Password* <BR>
 *     Warehouse* <BR>
 *     Workshop* <BR>
 *     Station* <BR>
 *     Consignor Code* <BR>
 *     Item Code <BR>
 *     Case/Piece flag* <BR>
 *     Beginning shelfNo. <BR>
 *     End shelfNo. <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Shipper code <BR>
 *     Shipper name <BR></DIR>
 * <BR>
 *   <return data> <BR><DIR>
 *     Warehouse <BR>
 *     Workshop <BR>
 *     Station <BR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Case/Piece flag <BR>
 *     Location No.. <BR>
 *     Qty per case <BR>
 *     Qty per bundle <BR>
 *     Possible drawing case qty((Stock qty -Drawing qty)/Qty per case) <BR>
 *     Possible drawing piece qty((Stock qty -Drawing qty)%Qty per case) <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Expiry date <BR>
 *     Stock ID <BR>
 *     Last updated date and time <BR></DIR></DIR>
 * <BR>
 * 3.Retrieval Processing when Start button is pressed(<CODE>startSCHgetParams()</CODE>Method) <BR><BR><DIR>
 *   Filter and receive the content displayed in the area as parameter, and do the ASRS No plan retrieval setting processing. <BR>
 *   Moreover, start ASRS No plan retrieval work ListPrintClass when No plan retrieval work ListPrintFlag of parameter is True at processing End. <BR>
 *   Filtered and acquired from the data base again and return data for the area output when processing normally Ends. <BR>
 *   Return null when the schedule does not End because of the condition error etc.<BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 *   <BR>
 *   The data received from the screen side as parameter is assumed to be only data to be updated. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker code* <BR>
 *     Password* <BR>
 *     Warehouse* <BR>
 *     Workshop* <BR>
 *     Station* <BR>
 *     Consignor Code* <BR>
 *     Consignor Name* <BR>
 *     Item Code* <BR>
 *     Item Name* <BR>
 *     Case/Piece flag* <BR>
 *     Location No..* <BR>
 *     Qty per case* <BR>
 *     Qty per bundle* <BR>
 *     Case Picking Qty.* <BR>
 *     Piece Picking Qty.* <BR>
 *     Case ITF* <BR>
 *     Bundle ITF* <BR>
 *     Expiry date <BR>
 *     Picking Work List Print Flag* <BR>
 *     All Qty. Flag* <BR>
 *     Stock ID* <BR>
 *     Line No. <BR>
 *     Last updated date and time* <BR></DIR>
 * <BR>
 *   <return data> <BR><DIR>
 *     Warehouse <BR>
 *     Workshop <BR>
 *     Station <BR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Case/Piece flag <BR>
 *     Location No.. <BR>
 *     Qty per case <BR>
 *     Qty per bundle <BR>
 *     Possible drawing case qty((Stock qty -Drawing qty)/Qty per case) <BR>
 *     Possible drawing piece qty((Stock qty -Drawing qty)%Qty per case) <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Expiry date <BR>
 *     Stock ID <BR>
 *     Last updated date and time <BR></DIR></DIR>
 * <BR>
 *   <Picking Start Process> <BR>
 * <DIR>
 *   <Processing condition check> <BR>
 *     1.Worker code and Password must be defined in the worker master.  <BR><DIR>
 *       Only the first value of the array is checked for Worker code and Password. <BR></DIR>
 *     2.The Inventory information table of Stock ID must exist in the data base.  <BR>
 *     3.The value and parameter of Last updated date and time in the Inventory information table must be matching. (exclusive check)<BR>
 *     4.Input value check processing(<CODE>AbstructRetrievalSCH()</CODE>Class) <BR>
 *     5.Input the value below a capable number of Drawing to the number of deliveries. <BR>
 * <BR>
 *   <Update registration processing> <BR>
 *     -Renewal of Inventory information table (DMSTOCK) <BR>
 *       Update it based on the content of parameter which receives Inventory information for the string in Stock ID of parameter. <BR>
 *       Do the delivery setting processing for possible a few minutes of Drawing when several all Flag of parameter is True. <BR>
 *       1.Update the number which can be delivered to the value in which the delivery number is subtracted.  <BR>
 *       2.Renew Last updated Processing name. <BR>
 *       3.Renew The final Retrieval date. (Set the system date. )<BR>
 * </DIR>
 * </DIR>
 * </DIR>
 * <BR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/04</TD><TD>C.Kaminishizono</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:04:32 $
 * @author  $Author: suresh $
 */
public class AsNoPlanRetrievalSCH extends AbstractAsrsControlSCH 
{
	//#CM44646
	// Class variables -----------------------------------------------
	//#CM44647
	/**
	 * Class Name(No plan retrieval Submit ( ASRS ))
	 */
	public static String CLASS_RETRIEVAL = "AsNoPlanRetrievalSCH";

	//#CM44648
	// Class method --------------------------------------------------
	//#CM44649
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:04:32 $");
	}
	//#CM44650
	// Constructors --------------------------------------------------
	//#CM44651
	/**
	 * Initialize this Class. 
	 */
	public AsNoPlanRetrievalSCH()
	{
		wMessage = null;
	}
	
	//#CM44652
	// Public methods ------------------------------------------------
	//#CM44653
	/**
	 * Method corresponding to the operation to acquire necessary data when initial is displayed the screen. <BR>
	 * Return corresponding Consignor Code when only one Consignor Code exists in the inventory information. <BR>
	 * Return null when pertinent data does not exist or it exists by two or more.  <BR>
	 * Set null in < CODE>searchParam</CODE > because it does not need the search condition. 
	 * @param conn Instance to maintain connection with data base. 
	 * @param searchParam Instance of < CODE>AsScheduleParameter</CODE>Class with display data acquisition condition. <BR>
	 *         ScheduleException when specified excluding <CODE>AsScheduleParameter</CODE> instance is slow .. <BR>
	 * @return Class which mounts < CODE>Parameter</CODE > interface where retrieval result is included
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey searchKey = new StockSearchKey();

		//#CM44654
		// Data retrieval
		//#CM44655
		// Stock status(Center Stocking)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM44656
		// Stock qty must be one or more. 
		searchKey.setStockQty(1, ">=");

		//#CM44657
		// Acquire only the stock of AS/RS. 
		AreaOperator areaOpe = new AreaOperator(conn);
		int[] areaType = {Area.SYSTEM_DISC_KEY_ASRS};
		searchKey.setAreaNo(areaOpe.getAreaNo(areaType));

		searchKey.setConsignorCodeCollect(" ");
		searchKey.setConsignorCodeGroup(1);

		int count = stockHandler.count(searchKey);

		AsScheduleParameter dispData = new AsScheduleParameter();

		if (count == 1)
		{
			try
			{
				Stock[] stock = (Stock[]) stockHandler.find(searchKey);
				dispData.setConsignorCode(stock[0].getConsignorCode());
			}
			catch (Exception e)
			{
				return new AsScheduleParameter();
			}
		}
		return dispData;

	}

	//#CM44658
	/**
	 * The content input from the screen is received as parameter, and filtered, and acquire from the database and return data for the area output. <BR>
	 * Refer to the paragraph of the Class explanation for detailed operation. <BR>
	 * @param conn Instance to maintain connection with data base. 
	 * @param searchParam Instance of < CODE>AsScheduleParameter</CODE>Class with display data acquisition condition. 
	 * @return Array of <CODE>AsScheduleParameter</CODE> instance with retrieval result. <BR>
	 *          Return the empty array when not even one pertinent record is found. <BR>
	 *          Return null when the error occurs in the input condition when the retrieval result exceeds 1000. <BR>
	 *          When the array or null of number 0 of elements is returned, the content of the error can be acquired as a message in <CODE>getMessage() </CODE>Method. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter[] query(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{

		AsScheduleParameter param = (AsScheduleParameter) searchParam;

		//#CM44659
		// Check on Worker code and Password
		if (!checkWorker(conn, param))
		{
			return null;
		}

		ASStockHandler stockHandler = new ASStockHandler(conn);
		StockSearchKey searchKey = new StockSearchKey();
		PaletteSearchKey palettesearchKey = new PaletteSearchKey();
		ShelfSearchKey shelfsearchKey = new ShelfSearchKey();

		//#CM44660
		// Data retrieval
		//#CM44661
		// Stock status(Center Stocking)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM44662
		// Stock qty must be one or more. 
		searchKey.setStockQty(1, ">=");

		//#CM44663
		// inaccessible is other than the subject. 
		shelfsearchKey.setAccessNgFlag(Shelf.ACCESS_OK);
		//#CM44664
		// Restricted Location is other than the subject. 
		shelfsearchKey.setStatus(Shelf.STATUS_OK);

		//#CM44665
		// Area No.
		String areano = param.getAreaNo();
		if (!StringUtil.isBlank(areano))
		{
			searchKey.setAreaNo(areano);
		}
		else
		{
			//#CM44666
			// Acquire only the stock of AS/RS. 
			AreaOperator areaOpe = new AreaOperator(conn);
			int[] areaType = {Area.SYSTEM_DISC_KEY_ASRS};
			searchKey.setAreaNo(areaOpe.getAreaNo(areaType));
		}
		
		//#CM44667
		// Consignor Code
		searchKey.setConsignorCode(param.getConsignorCodeDisp());
		//#CM44668
		// Item Code
		String itemcode = param.getItemCodeDisp();
		if (!StringUtil.isBlank(itemcode))
		{
			searchKey.setItemCode(itemcode);
		}
		//#CM44669
		// Case/Piece flag(Work form)
		//#CM44670
		// Excluding all
		if (!param.getCasePieceFlagDisp().equals(AsScheduleParameter.CASEPIECE_FLAG_ALL))
		{
			//#CM44671
			// Case
			if (param.getCasePieceFlagDisp().equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
			}
			//#CM44672
			// Piece
			else if (param.getCasePieceFlagDisp().equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
			}
			//#CM44673
			// Unspecified
			else if (
				param.getCasePieceFlagDisp().equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
			}
		}

		//#CM44674
		// Beginning shelf
		String fromlocation = param.getFromLocationNo() ;
		if (!StringUtil.isBlank(fromlocation))
		{
			searchKey.setLocationNo(fromlocation, ">=");
		}
		//#CM44675
		// End shelf
		String tolocation = param.getToLocationNo() ;
		if (!StringUtil.isBlank(tolocation))
		{
			searchKey.setLocationNo(tolocation, "<=");
		}
		//#CM44676
		// Case ITF
		String itf = param.getITFDisp();
		if (!StringUtil.isBlank(itf))
		{
			searchKey.setItf(itf);
		}
		//#CM44677
		// Bundle ITF
		String bundleitf = param.getBundleITFDisp();
		if (!StringUtil.isBlank(bundleitf))
		{
			searchKey.setBundleItf(bundleitf);
		}

		//#CM44678
		// Display in order of Item Code, Case/Piece flag, Location, Expiry date
		searchKey.setItemCodeOrder(1, true);
		searchKey.setCasePieceFlagOrder(2, true);
		searchKey.setLocationNoOrder(3, true);
		searchKey.setUseByDateOrder(4, true);
	
		if(!canLowerDisplay(stockHandler.count(searchKey, palettesearchKey, shelfsearchKey)))
		{
			return returnNoDisplayParameter();
		}

		ASShelfStock[] resultEntity = (ASShelfStock[]) stockHandler.find(searchKey, palettesearchKey, shelfsearchKey);

		Vector vec = new Vector();

		for (int i = 0; i < resultEntity.length; i++)
		{
			AsScheduleParameter dispData = new AsScheduleParameter();
			//#CM44679
			// Consignor Code
			dispData.setConsignorCode(resultEntity[i].getConsignorCode());
			//#CM44680
			// Item Code
			dispData.setItemCode(resultEntity[i].getItemCode());
			//#CM44681
			// Item Name
			dispData.setItemName(resultEntity[i].getItemName1());
			//#CM44682
			// Case/Piece flag
			dispData.setCasePieceFlag(resultEntity[i].getCasePieceFlag());
			//#CM44683
			// Case/Piece flagName
			//#CM44684
			// Acquire Case/Piece flagName from Case/Piece flag(Work form). 
			String casepiecename =
				DisplayUtil.getPieceCaseValue(resultEntity[i].getCasePieceFlag());
			dispData.setCasePieceFlagNameDisp(casepiecename);
			//#CM44685
			// Retrieval Shelf
			dispData.setLocationNo(resultEntity[i].getLocationNo());
			//#CM44686
			// Qty per case
			dispData.setEnteringQty(resultEntity[i].getEnteringQty());
			//#CM44687
			// Qty per bundle
			dispData.setBundleEnteringQty(resultEntity[i].getBundleEnteringQty());

			//#CM44688
			// When Case/Piece flag is Piece
			if (resultEntity[i].getCasePieceFlag().equals(Stock.CASEPIECE_FLAG_PIECE))
			{
				//#CM44689
				// Set Stock qty -Drawing qty in Possible drawing piece qty. 
				dispData.setAllocatePieceQty(resultEntity[i].getAllocationQty());
			}
			//#CM44690
			// When Case/Piece flag is Case or Unspecified
			else
			{
				//#CM44691
				// Set the quotient into which Stock qty - Drawing qty is divided by Case in Possible drawing case qty. 
				dispData.setAllocateCaseQty(
					DisplayUtil.getCaseQty(
						resultEntity[i].getAllocationQty(), resultEntity[i].getEnteringQty()));
				//#CM44692
				// Set the remainder into which Stock qty - Drawing qty is divided by Case in Possible drawing piece qty. 
				dispData.setAllocatePieceQty(
					DisplayUtil.getPieceQty(
						resultEntity[i].getAllocationQty(), resultEntity[i].getEnteringQty()));
			}

			//#CM44693
			// Case ITF
			dispData.setITF(resultEntity[i].getItf());
			//#CM44694
			// Bundle ITF
			dispData.setBundleITF(resultEntity[i].getBundleItf());
			//#CM44695
			// Expiry date
			dispData.setUseByDate(resultEntity[i].getUseByDate());
			//#CM44696
			// Stock ID
			dispData.setStockId(resultEntity[i].getStockId());
			//#CM44697
			// Last updated date and time
			dispData.setLastUpdateDate(resultEntity[i].getLastUpdateDate());

			vec.addElement(dispData);
		}

		AsScheduleParameter[] paramArray = new AsScheduleParameter[vec.size()];
		vec.copyInto(paramArray);

		//#CM44698
		// 6001013 = Displayed.
		wMessage = "6001013";
		return paramArray;
	}

	//#CM44699
	/**
	 * Receive the content input from the screen as parameter, and begin the schedule of the No plan retrieval setting. <BR>
	 * Receive parameter by the array because the input of two or more of set of straightening data is assumed. <BR>
	 * Refer to the paragraph of the Class explanation for detailed operation. <BR>
	 * Return true when the schedule ends normally and return false when failing.
	 * @param conn Instance to maintain connection with data base. 
	 * @param startParams Array of instance of < CODE>AsScheduleParameter</CODE>Class with set content. 
	 * @return Array of <CODE>AsScheduleParameter</CODE> instance with retrieval result. <BR>
	 *          Return the empty array when not even one pertinent record is found. <BR>
	 *          Return null when the error occurs in the input condition when the retrieval result exceeds 1000. <BR>
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM44700
			// Check on Worker code and Password
			AsScheduleParameter workparam = (AsScheduleParameter) startParams[0];
			if (!checkWorker(conn, workparam))
			{
				return null;
			}

			//#CM44701
			// Online check
			StationHandler stHandler = new StationHandler(conn);
			ASStationHandler AstHandler = new ASStationHandler(conn);
			StationSearchKey stSearchKey = new StationSearchKey();
			GroupControllerHandler wGcHandler = new GroupControllerHandler(conn);
			GroupControllerSearchKey wGcSearchKey = new GroupControllerSearchKey();					
			//#CM44702
			// Station No
			stSearchKey.KeyClear();
			if( workparam.getToStationNo().equals(Station.AUTO_SELECT_STATIONNO) )
			{
				stSearchKey.setStationNumber(workparam.getSagyoba());
			}
			else
			{
				stSearchKey.setStationNumber(workparam.getToStationNo());
			}
			
			Station[] rstation = null;
			
			rstation = (Station[]) stHandler.find(stSearchKey);

			if (rstation[0].getControllerNumber() == 0)
			{
				stSearchKey.KeyClear();
				stSearchKey.setParentStationNumber(rstation[0].getStationNumber());
				stSearchKey.setControllerNumberCollect("DISTINCT");

				rstation = (Station[]) AstHandler.getWorkStation(rstation[0].getStationNumber());
				
			}
			//#CM44703
			//<en> System Status check</en>
			boolean findflag = false;
			for (int plc=0; plc<rstation.length; plc++)
			{
				wGcSearchKey.KeyClear();
				wGcSearchKey.setControllerNumber(rstation[plc].getControllerNumber());
			
				GroupController[] rGroupControll = (GroupController[])wGcHandler.find(wGcSearchKey);
			
				if (rGroupControll[0].getStatus() == GroupController.STATUS_ONLINE)
				{
					findflag = true;
					break;
				}
			}
			if (!findflag)
			{
				//#CM44704
				// Make system Status online. 
				setMessage("6013023");
				return null;
			}

			//#CM44705
			// Next day update Processing check
			if (isDailyUpdate(conn))
			{
				return null;
			}

			//#CM44706
			// Filtered and input is checked. 
			if (!checkList(startParams))
			{
				return null;
			}

			try
			{
				//#CM44707
				// Lock processing(inventory information and Location information)
				boolean _existFlag = lockStockData(conn, (AsScheduleParameter[]) startParams);
				if (!_existFlag)
				{
					//#CM44708
					// 6027008 = This data is not useable because it is updating it with other terminals. 
					wMessage = "6027008";
					return null;
				}
			}
			catch(LockTimeOutException e)
			{
				//#CM44709
				// 6027008 = This data is not useable because it is updating it with other terminals. 
				wMessage = "6027008";
				return null;
			}

			//#CM44710
			// Definition for route check
			RouteController rc = new RouteController(conn, false);

			//#CM44711
			// Worker information acquisition
			//#CM44712
			// Worker code
			String workercode = workparam.getWorkerCode();
			//#CM44713
			// Worker Name
			String workerName = getWorkerName(conn, workercode);

			//#CM44714
			// Parameter for information update 
			//#CM44715
			// Terminal No.
			String terminalno = workparam.getTerminalNumber();
			//#CM44716
			// During the acquisition of transportation destination Station
			String deststation = workparam.getToStationNo();
			//#CM44717
			// Shipper code
			String customercode = workparam.getCustomerCode();
			//#CM44718
			// Shipper name
			String customername = workparam.getCustomerName();
			//#CM44719
			// WarehouseNo
			String warehouse = workparam.getAreaNo();

			//#CM44720
			// Acquire each registered one mind key. 
			SequenceHandler sequence = new SequenceHandler(conn);
			//#CM44721
			// Batch No.(One setting commonness)
			String batch_seqno = sequence.nextNoPlanBatchNo();			
			String schno_seqno = sequence.nextScheduleNumber() ;

			//#CM44722
			// Work amount
			int workqty = 0;
			
			//#CM44723
			// Processed the parameter extraction qty. .
			for (int i = 0; i < startParams.length; i++)
			{
				AsScheduleParameter param = (AsScheduleParameter) startParams[i];

				//#CM44724
				// Stock ID
				String stockid = param.getStockId();
				//#CM44725
				// Location No..
				String locationno = param.getLocationNo();
				//#CM44726
				// Transportation key
				String carrykey_seqno = sequence.nextCarryKey() ;

				//#CM44727
				// The Picking instruction is detailed. (Make it with the unit for the time being. )
				int retrievaldetail = CarryInformation.RETRIEVALDETAIL_UNIT ;
				//#CM44728
				// Transportation originStation No
				String currentstation = null ;

				//#CM44729
				// Work No.
				String jobno_seqno = sequence.nextJobNo();
				//#CM44730
				// Work No.(For transportation file)
				String workno_seqno = sequence.nextRetrievalWorkNumber();

				int retrievalqty = 0;
				//#CM44731
				// Do the picking processing for possible partial Drawing qty when several all Flag of parameter is True. 
				if (param.getTotalFlag() == true)
				{
					retrievalqty =
						param.getAllocateCaseQty() * param.getEnteringQty() + param.getAllocatePieceQty();
				}
				else
				{
					retrievalqty =
						param.getRetrievalCaseQty() * param.getEnteringQty() + param.getRetrievalPieceQty();
				}
				
				//#CM44732
				// Overflow check of Work Qty.
				workqty = addWorkQty(workqty, retrievalqty);

				//#CM44733
				// Retrieval Inventory information(DMSTOCK)
				StockHandler shandler = new StockHandler(conn);
				StockSearchKey skey = new StockSearchKey();
				
				skey.setStockId(stockid);
				Stock stock = (Stock) shandler.findPrimary(skey);
				if (stock == null)
				{
					return null ;
				}
				
				//#CM44734
				// Palette information (PALETTE) retrieval
				PaletteHandler phandler = new PaletteHandler(conn);
				PaletteSearchKey pkey = new PaletteSearchKey();
				
				pkey.setPaletteId(stock.getPaletteid());
				Palette palette = (Palette) phandler.findPrimary(pkey);
				if (palette != null)
				{
					currentstation = palette.getCurrentStationNumber();
				}

				//#CM44735
				// Station information (STATION) retrieval
				Entity tost = null;
				StationSearchKey stkey = new StationSearchKey();
				if( param.getToStationNo().equals(Station.AUTO_SELECT_STATIONNO) )
				{
					//#CM44736
					// Acquire the entity of Workshop in case of the automatic distribution. 
					stkey.setStationNumber(param.getSagyoba());
					ASWorkPlaceHandler wpHandler = new ASWorkPlaceHandler(conn);
					WorkPlace[] wp = (WorkPlace[]) wpHandler.find(stkey);
					tost = wp[0];
				}
				else
				{
					//#CM44737
					// Acquire the entity of Station. 
					stkey.setStationNumber(deststation);
					StationHandler stnHandler = new StationHandler(conn);
					Station st = (Station) stnHandler.findPrimary(stkey);
					tost = st;

					//#CM44738
					// Check Status of selection Station. 
					String r_Msg = isRetrievalStationCheck(conn, (Station)tost, false);
					if (!StringUtil.isBlank(r_Msg))
					{
						//#CM44739
						// 6023273=No.{0} {1}
						wMessage = "6023273" + 
							wDelim + param.getRowNo() + 
							wDelim + MessageResource.getMessage(r_Msg);
						return null;
					}
				}

				//#CM44740
				// Route check processing
				boolean routFlag = false;
				routFlag = rc.retrievalDetermin(palette, (Station)tost, true);
				//#CM44741
				// Processed by the route check in abnormal circumstances as follows. 
				if(!routFlag)
				{ 
					//#CM44742
					// 6023273=No.{0} {1}
					//#CM44743
					// Messege of transportation route NG is acquired. 
					wMessage = "6023273" + 
						wDelim + param.getRowNo() + 
						wDelim + MessageResource.getMessage(getRouteErrorMessage(rc.getRouteStatus()));
					return null;
				}
				//#CM44744
				// During the acquisition of transportation destination Station
				deststation = rc.getDestStation().getStationNumber();
				
				//#CM44745
				// Renewal of Inventory information table (DMSTOCK) 
				if (!updateStock(conn, stockid, retrievalqty, param.getLastUpdateDate(), param.getRowNo()))
				{
					return null;
				}

				//#CM44746
				// Inventory information(DMSTOCK) retrieval
				if (stock != null)
				{
					//#CM44747
					// Renewal of palette table (PALETTE)
					if (!updatePalette(conn, stock.getPaletteid()))
					{
						return null;
					}
				}
				
				//#CM44748
				// Transportation information (CARRYINFO) retrieval
				CarryInformationHandler wCarryHandler = new CarryInformationHandler(conn);
				CarryInformationSearchKey wCarryKey = new CarryInformationSearchKey();

				wCarryKey.setRetrievalStationNumber(locationno);
				wCarryKey.setScheduleNumber(schno_seqno);

				CarryInformation[] rCarry = (CarryInformation[])wCarryHandler.find(wCarryKey);

				//#CM44749
				// Transportation key is returned when already registered. 
				if (rCarry != null && rCarry.length > 0)
				{
					//#CM44750
					// Set Transportation key. (for Work information)
					carrykey_seqno = rCarry[0].getCarryKey();
				}
				else
				{
					//#CM44751
					// Aisle Station No..
					//#CM44752
					// Retrieve the shelf table by Shelf  No.
					ShelfHandler wShelfHandler = new ShelfHandler(conn);
					ShelfSearchKey wShelfKey = new ShelfSearchKey();
				
					wShelfKey.KeyClear();
					wShelfKey.setStationNumber(locationno);
			
					Shelf rShelf = (Shelf)wShelfHandler.findPrimary(wShelfKey);
		
					//#CM44753
					// Registration of transportation table (CARRYINFO)
					if (!createCarryinfo(conn,
						CarryInformation.WORKTYPE_NOPLAN_RETRIEVAL,
						schno_seqno,
						locationno,
						workno_seqno,
						retrievaldetail,
						currentstation,
						deststation,
						stock.getPaletteid(),
						CarryInformation.CMDSTATUS_START,
						CarryInformation.PRIORITY_EMERGENCY,
						CarryInformation.CARRYKIND_RETRIEVAL,
						carrykey_seqno,
						rShelf.getParentStationNumber()))
					{
						return null;
					}
				}

				//#CM44754
				// Registration of work information table (DNWORKINFO)
				if (!createWorkinfo(conn,
					param,
					stock,
					stockid,
					workercode,
					workerName,
					terminalno,
					WorkingInformation.JOB_TYPE_EX_RETRIEVAL,
					CLASS_RETRIEVAL,
					batch_seqno,
					0,
					retrievalqty,
					locationno,
					jobno_seqno,
					customercode,
					customername,
					carrykey_seqno,
					warehouse,
					"",
					getWorkDate(conn),
					new Date()))
				{
					return null;
				}
				
			}

			//#CM44755
			// Do the decision processing of the fractional retrieving or the unit delivery. 
			decideCarryInfo(conn, schno_seqno);

			//#CM44756
			// Schedule success
			AsScheduleParameter[] viewParam = (AsScheduleParameter[]) this.query(conn, workparam);

			//#CM44757
			//  Modifying file of ASRS No plan retrieval List
			if (workparam.getListFlg() == true)
			{
				AsNoPlanRetrievalWriter writer = new AsNoPlanRetrievalWriter(conn);
				writer.setBatchNumber(batch_seqno);

				if (writer.startPrint())
				{
					//#CM44758
					// 6021012 = The print ended normally after it had set it. 
					wMessage = "6021012";
				}
				else
				{
					//#CM44759
					// 6007042=After submitting, It failed in the print. Refer to the log. 
					wMessage = "6007042";
				}
			}
			else
			{
				//#CM44760
				// 6001006 = Submitted.
				wMessage = "6001006";
			}

			return viewParam;

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

	//#CM44761
	// Protected methods ---------------------------------------------
	//#CM44762
	/** 
	 * Filter and do the input check. <BR>
	 * < CODE>ScheduleException</CODE > is slow when called. 
	 * @param  searchParam Instance of < CODE>AsScheduleParameter</CODE>Class with display data acquisition condition. <BR>
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ScheduleException It is notified when this Method is called. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected boolean checkList(Parameter[] searchParams)
	{
		for (int i = 0; i < searchParams.length; i++)
		{
			AsScheduleParameter param = (AsScheduleParameter) searchParams[i];

			//#CM44763
			// Acquire Qty per case, Stock qty , and the number of deliveries (screen input data) from parameter. 
			String casepieceflag = param.getCasePieceFlag();
			int enteringqty = param.getEnteringQty();
			int caseqty = param.getRetrievalCaseQty();
			int pieceqty = param.getRetrievalPieceQty();

			//#CM44764
			// Line No.
			int rowno = param.getRowNo();

			long allocaionqty =
				(long)param.getAllocateCaseQty() * (long)enteringqty + (long)param.getAllocatePieceQty();

			//#CM44765
			// When Allocated Qty is 0
			if (allocaionqty == 0)
			{
				//#CM44766
				// 6023380=Cannot work when No.{0} Allocated Qty is 0.
				wMessage = "6023380" + wDelim + rowno;
				return false;
			}

			//#CM44767
			// Check it only when there is no check in all qty picking. 
			if (param.getTotalFlag())
			{
				continue;
			}

			//#CM44768
			// Input value check
			if (!stockRetrievalInputCheck(casepieceflag, enteringqty, caseqty, pieceqty, rowno))
			{
				return false;
			}

			//#CM44769
			// Overflow check
			long retrievalqty =
				(long) param.getRetrievalCaseQty() * (long) param.getEnteringQty()
					+ param.getRetrievalPieceQty();
			//#CM44770
			// The number of deliveries is one or more when set. 
			if (retrievalqty > 0)
			{
				//#CM44771
				// The number of deliveries (input data) is larger than that of Allocated Qty. 
				if (retrievalqty > allocaionqty)
				{
					//#CM44772
					// 6023216= Input the value of Allocated Qty or less to the number of deliveries when No. {0} is picked. 
					wMessage = "6023216" + wDelim + rowno;
					return false;
				}
			}

			if (retrievalqty > WmsParam.MAX_STOCK_QTY)
			{
				//#CM44773
				// 6023272 = No. {0} Input the value following {2} to {1}. 
				wMessage =
					"6023272"
						+ wDelim
						+ rowno
						+ wDelim
						+ DisplayText.getText("LBL-W0420")
						+ wDelim
						+ MAX_STOCK_QTY_DISP;
				return false;
			}

		}
		return true;
	}
	
	//#CM44774
	/**
	 * Lock Inventory information to be renewed. 
	 * Generate the array of Stock ID from the array of Work information 
	 * specified by the argument, and lock corresponding Inventory information. 
	 * <DIR>
	 *    (Search condition)
	 *    <UL>
	 *     <LI>Stock ID</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param	workinfo Array of Work information
	 * @return If the lock is correctly done, True, False otherwise.
	 * @throws ReadWriteException   Notified when abnormality occurs by the connection with the data base.
	 * @throws LockTimeOutException It is notified when the lock is not opened even if time passes. 
	 */
	protected boolean lockStockData(Connection conn, AsScheduleParameter[] param)
		throws ReadWriteException, LockTimeOutException
	{
		ASStockHandler shandler = new ASStockHandler(conn);
		StockSearchKey skey = new StockSearchKey();
		PaletteSearchKey pkey = new PaletteSearchKey();
		ShelfSearchKey shkey = new ShelfSearchKey();

		//#CM44775
		// Acquire corresponding Stock ID when input information from the screen already exists in Inventory information. 
		for (int i = 0; i < param.length; i++)
		{
			skey.KeyClear();

			//#CM44776
			// Retrieval of existing Inventory information
			skey.setStockId(param[i].getStockId());
			skey.setLastUpdateDate(param[i].getLastUpdateDate());
			shkey.setAccessNgFlag(Shelf.ACCESS_OK);
			shkey.setStatus(Shelf.STATUS_OK);
			ASShelfStock[] stock = (ASShelfStock[]) shandler.findForUpdate(skey, pkey, shkey);
			
			if (stock == null || stock.length == 0)
			{
				return false;
			}
		}
		
		return true;
	}

	//#CM44777
	/**
	 * Renew Restorage flag with Station at the delivery destination of transportation information and a delivery detailed instruction. <BR>
	 * 
	 * [The delivery instruction is detailed. ]
	 * <DIR>
	 *   1.When Allocated Drawing Qty on the palette is 0
	 *   <DIR>
	 *     a.Delivered the unit in case of Station to be able to disburse Station at the delivery destination. <BR>
	 *     b.If Station at the delivery destination is Station of disburse improper, the fractional retrieving is done. 
	 *   </DIR>
	 *   2.When Allocated Drawing Qty on the palette is one or more
	 *   <DIR>
	 *     a.If Station at the delivery destination is Station only for disbursement, the unit is delivered. <BR>
	 *     b.The partial picking, except for the above-mentioned. 
	 *   </DIR>
	 * </DIR>
	 * [Restorage flag]
	 * <DIR>
	 *   1.When delivery instruction is detailed for "Unit delivery"
	 *   <DIR>
	 *     No Restorage
	 *   </DIR>
	 *   2.When The delivery instruction is detailed for "Partial Picking"
	 *   <DIR>
	 *     a.[No Restorage] when there is transportation of re-stock instruction transmission existence<BR>
	 *     b.[Restorage Available] except for the above-mentioned.  
	 *   </DIR>
	 * </DIR>
	 * @param conn   Instance to maintain connection with data base. 
	 * @param pSchNo Schedule No.
	 * @return If the update is correctly done, True, False otherwise.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected void decideCarryInfo(Connection conn, String pSchNo) throws ReadWriteException
	{
		try
		{
			CarryInformationAlterKey ciAKey = new CarryInformationAlterKey();
			CarryInformationSearchKey ciKey = new CarryInformationSearchKey();
			CarryInformationHandler cih = new CarryInformationHandler(conn);
			//#CM44778
			// Acquire transportation information scheduled this time. 
			ciKey.KeyClear();
			ciKey.setScheduleNumber(pSchNo);
			ciKey.setPaletteIdCollect();
			ciKey.setCarryKeyCollect();
			ciKey.setDestStationNumberCollect();

			CarryInformation[] cinfos = (CarryInformation[]) cih.find(ciKey);

			StockSearchKey stkKey = new StockSearchKey();
			StockHandler stkh = new StockHandler(conn);
			for (int i = 0; i < cinfos.length; i++)
			{			
				//#CM44779
				// Acquire Allocated Drawing Qty on the palette. 
				stkKey.KeyClear();
				stkKey.setPaletteid(cinfos[i].getPaletteId());
				stkKey.setPaletteidGroup(1);
				stkKey.setLocationNoGroup(2);
				stkKey.setAllocationQtyCollect("SUM");
				stkKey.setLocationNoCollect();
				Stock stk = (Stock) stkh.findPrimary(stkKey);
				int totalAllocatableQty = stk.getAllocationQty();

				//#CM44780
				// Generate Station of the transportation destination. 
				Station destSt = StationFactory.makeStation(conn, cinfos[i].getDestStationNumber());
				
				boolean isUnitRetrieval = false;
				if (totalAllocatableQty == 0)
				{
					//#CM44781
					// When Drawing qty is 0 the unit delivery is done
					isUnitRetrieval = true;
				}

				//#CM44782
				// The detailed delivery instruction is decided.
				int retrievalDetail = -1;
				int reStoringFlag = -1;
				if (isUnitRetrieval)
				{
					//#CM44783
					// In case of All Qty Picking
					if (!destSt.isRemove())
					{
						//#CM44784
						// When disbursement is improper though work is all qty picking, it is assumed Partial Picking. 
						retrievalDetail = CarryInformation.RETRIEVALDETAIL_PICKING;
					}
					else
					{
						retrievalDetail = CarryInformation.RETRIEVALDETAIL_UNIT;
					}
				}
				else
				{
					if (destSt.isUnitOnly())
					{
						//#CM44785
						// When Station is only disbursement though work is a picking, it is assumed the unit delivery. 
						retrievalDetail = CarryInformation.RETRIEVALDETAIL_UNIT;
					}
					else
					{
						retrievalDetail = CarryInformation.RETRIEVALDETAIL_PICKING;
					}
				}
				
				//#CM44786
				// Restorage flag is decided from the detailed delivery instruction. 
				if (retrievalDetail == CarryInformation.RETRIEVALDETAIL_UNIT)
				{
					//#CM44787
					// When the unit working, it is assumed No Restorage . 
					reStoringFlag = CarryInformation.RESTORING_NOT_SAME_LOC ;
				}
				else
				{
					//#CM44788
					// At picking work
					if (destSt.getReStoringInstruction() == Station.AWC_STORAGE_SEND)
					{
						//#CM44789
						// It is assumed [No Restorage] with eWN for Station which makes data of the re-stock. 
						reStoringFlag = CarryInformation.RESTORING_NOT_SAME_LOC ;
					}
					else
					{
						//#CM44790
						// It is assumed [Restore in same shelf] with eWN for Station which does not make data of the re-stock. 
						reStoringFlag = CarryInformation.RESTORING_SAME_LOC ;
					}
				}
				
				//#CM44791
				// Update transportation information in the content of the above-mentioned data. 
				ciAKey.KeyClear();
				ciAKey.setCarryKey(cinfos[i].getCarryKey());
				ciAKey.updateRetrievalDetail(retrievalDetail);
				ciAKey.updateReStoringFlag(reStoringFlag);
				cih.modify(ciAKey);
			}
			
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

	}
	
	//#CM44792
	// Private methods -----------------------------------------------
	//#CM44793
	/**
	 * Renew the Inventory information table. 
	 * @param conn           Connection object with database
	 * @param stockid        Stock ID
	 * @param retrievalqty   Picking qty
	 * @param lastupdatedate Last updated date
	 * @param rowno          ROWNo.
	 * @return If the update is correctly done, True, False otherwise.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.<BR>
	 * @throws ScheduleException It is notified when this Method is called. 
	 */
	private boolean updateStock(
		Connection conn,
		String stockid,
		int retrievalqty,
		Date lastupdatedate,
		int rowno)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			StockHandler stockHandler = new StockHandler(conn);
			StockSearchKey stocksearchKey = new StockSearchKey();
			StockAlterKey stockAlterKey = new StockAlterKey();

			//#CM44794
			// Data retrieval
			//#CM44795
			// Stock ID
			stocksearchKey.setStockId(stockid);
			//#CM44796
			// Inventory Status (Center Stocking)
			stocksearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM44797
			// Stock qty is more than 1
			stocksearchKey.setStockQty(1, ">=");

			Stock stock = (Stock) stockHandler.findPrimary(stocksearchKey);

			if (!stock.getLastUpdateDate().equals(lastupdatedate))
			{
				//#CM44798
				// Processing end when error at the other end occurs
				//#CM44799
				// 6023209 = No.{0} This data is not useable because it was updated in other terminals. 
				wMessage = "6023209" + wDelim + rowno;
				return false;
			}
			//#CM44800
			// Stock ID is set. 
			stockAlterKey.setStockId(stockid);
			
			//#CM44801
			// Renew Last updated Processing name. 
			stockAlterKey.updateLastUpdatePname(CLASS_RETRIEVAL);

			//#CM44802
			// Renew Stock qty based on the content of received parameter. 
			//#CM44803
			// Subtract Picking qty of parameter from Stock qty of Inventory information. 
			int resultqty = stock.getAllocationQty() - retrievalqty;
			stockAlterKey.updateAllocationQty(resultqty);

			//#CM44804
			// Update of data
			stockHandler.modify(stockAlterKey);

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
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM44805
	/**
	 * Renew the palette information table. <BR>
	 * @param conn Connection object with database<BR>
	 * @param paletteid Palette ID
	 * @return If the update is correctly done, True, False otherwise.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.<BR>
	 * @throws ScheduleException It is notified when this Method is called. 
	 */
	private boolean updatePalette(
		Connection conn,
		int paletteid)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			PaletteHandler paletteHandler = new PaletteHandler(conn);
			PaletteSearchKey palettesearchKey = new PaletteSearchKey();
			PaletteAlterKey paletteAlterKey = new PaletteAlterKey();

			//#CM44806
			// Data retrieval
			//#CM44807
			// Palette ID
			palettesearchKey.setPaletteId(paletteid);

			Palette palette = (Palette) paletteHandler.findPrimary(palettesearchKey);

			//#CM44808
			// Stock ID is set. 
			paletteAlterKey.setPaletteId(paletteid);

			//#CM44809
			// Update it to the delivery reservation when palette Status is only a real shelf. 
			if (palette.getStatus() == Palette.REGULAR || palette.getStatus() == Palette.IRREGULAR)
			{
				//#CM44810
				// Set stock Status : Retrieval reservation.			
				paletteAlterKey.updateStatus(Palette.RETRIEVAL_PLAN);
			}
			//#CM44811
			// Set Drawing qty: Drawing settlement. 
			paletteAlterKey.updateAllocation(Palette.ALLOCATED);

			//#CM44812
			// Update of data
			paletteHandler.modify(paletteAlterKey);

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
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
}
//#CM44813
//end of class
