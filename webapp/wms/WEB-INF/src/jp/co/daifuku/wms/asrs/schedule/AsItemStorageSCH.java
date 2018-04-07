//#CM44453
//$Id: AsItemStorageSCH.java,v 1.2 2006/10/30 01:04:33 suresh Exp $

//#CM44454
/*
 * Copyright 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule;

import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.dbhandler.ASStationHandler;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.report.AsItemStorageWriter;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HardZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.HardZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM44455
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * Class to do the WEB ASRS stock setting processing. <BR>
 * Receive the content input from the screen as parameter, and do the making processing of ASRS stock Work information.  <BR>
 * Do not do commit and rollback of the transaction though each Method of 
 * this Class must use the connection object and do the update processing of the receipt data base. <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Initial display processing(< CODE>initFind() </ CODE > method) <BR>
 * <BR>
 * <DIR>
 * 	Return Consignor Code when Status retrieves unstarted Consignor Code of stock schedule information, <BR>
 *	and only same Consignor Code exists. <BR>
 * 	Return null when two or more Consignor Code exists. <BR>
 *	<BR>
 *	<Search condition> <BR>
 *	<DIR>
 *		Status flag=0:Stand By
 *	</DIR>
 * </DIR>
 * <DIR>
 * 	Do the edit of the list of ASRSWarehouse. <BR>
 *	<BR>
 *	<Search condition> <BR>
 *	<DIR>
 *		From the Warehouse table < warehouse >.  <BR>
 *	</DIR>
 * </DIR>
 * <DIR>
 * 	Do the edit of the list of a hard zone. <BR>
 *	<BR>
 *	<Search condition> <BR>
 *	<DIR>
 *		From the shelf table < shelf >. <BR>
 *      Link information with Warehouse. <BR>
 *	</DIR>
 * </DIR>
 * <DIR>
 * 	Do the edit of the list of the workshop. <BR>
 *	<BR>
 *	<Search condition> <BR>
 *	<DIR>
 *		From the Station table < station >. <BR>
 *      Link information with Warehouse. <BR>
 *	</DIR>
 * </DIR>
 * <DIR>
 * 	Do the edit of the list of Station. <BR>
 *	<BR>
 *	<Search condition> <BR>
 *	<DIR>
 *		From the Station table < station >. <BR>
 *      Link information with workshop. <BR>
 *	</DIR>
 * </DIR>
 * <BR>
 * 2.Input button pressing processing(< CODE>check() </ CODE > method)<BR>
 * <BR>
 * <DIR>
 *   Receive the content input from the screen as parameter, do an mandatory check and Overflow check and the repetition check, and return the check result. <BR>
 *   Return true when pertinent data of same line No. does not exist in the inventory information and return false when the condition error occurs and pertinent data exists. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 *   <BR>
 *   [parameter] *Mandatory Input  +All Mandatory Input <BR>
 *   <DIR>
 *     Worker code* <BR>
 *     Password* <BR>
 *     Warehouse* <BR>
 *     Hard Zone* <BR>
 *     Workshop* <BR>
 *     Station* <BR>
 *     Consignor Code* <BR>
 *     Consignor Name <BR>
 *     Storage plan date* <BR>
 *     Item Code* <BR>
 *     Item Name <BR>
 *     Case/Piece flag* <BR>
 *     Qty per case <BR>
 *     Qty per bundle <BR>
 *     Storage Case qty+ <BR>
 *     Storage Piece qty+ <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Expiry date <BR>
 *   </DIR>
 *   <BR>
 *   [Processing condition check] <BR>
 *   <BR>
 *   -Worker code check processing-(<CODE>AbstructStorageSCH()</CODE>Class) <BR>
 *   <BR>
 *   -Input value check processing-(<CODE>AbstructStorageSCH()</CODE>Class) <BR>
 *   <BR>
 *   -Overflow check- <BR>
 *   <BR>
 *   -Display number check- <BR>
 *   <BR>
 *   -Checking for duplication- <BR>
 *     <DIR>
 *     Make Consignor Code, Storage plan date, Item Code, Case/Piece flag, and Expiry date a key and do Checking for duplication. <BR>
 *     The key to Checking for duplication contains Expiry date when defined in WmsParam as an item which uniquely stocks it. <BR>
 *     </DIR>
 * </DIR>
 * <BR>
 * 3.StorageProcessing when Start button is pressed(<CODE>startSCH()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *   Filter, receive the content displayed in the area as parameter, and do the ASRS stock setting processing. <BR>
 *   Return true when processing is normally completed and return false when it does not complete the schedule because of the condition error etc.<BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 *   <BR>
 *   [parameter] <BR>
 *   <DIR>
 *     Worker code* <BR>
 *     Password* <BR>
 *     Warehouse* <BR>
 *     Hard Zone* <BR>
 *     Workshop* <BR>
 *     Station* <BR>
 *     Consignor Code* <BR>
 *     Consignor Name <BR>
 *     Storage plan date* <BR>
 *     Item Code* <BR>
 *     Item Name <BR>
 *     Case/Piece flag* <BR>
 *     Qty per case <BR>
 *     Qty per bundle <BR>
 *     Storage Case qty+ <BR>
 *     Storage Piece qty+ <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Expiry date <BR>
 *     Terminal No. <BR>
 *   </DIR>
 *   <BR>
 *   [Processing condition check] <BR>
 *   <BR>
 *   -Worker code check processing-(<CODE>AbstructStorageSCH()</CODE>Class)<BR>
 *   <BR>
 *   -Next day update Processing check processing-(<CODE>AbstructStorageSCH()</CODE>Class) <BR>
 *   <BR>
 *   [Update registration processing] <BR>
 *   <BR>
 *   -Do the decision processing of Storage shelf-<BR>
 *   <BR>
 *   -Update or division registration of Work information table (DNWORKINFO)-<BR>
 *   <BR>
 *   <DIR>
 *     1.Acquire stand by information to which stock Plan unique key is corresponding with the lock.  <BR>
 *     2.Process Registration of work information by acquired Work information and the amount of Storage qty. <BR>
 *     3.Do the update processing of acquired Work information. <BR>
 *       3-1.When Storage qty=Work plan qty, Set 0 to Work plan qty, Update Status flag to End. <BR>
 *       3-2.Subtract the amount of Storage qty from Work plan qty, except for the above-mentioned. 
 *   </DIR>  
 *   <BR>
 *   -Renewal of Storage Plan information table (DNSTORAGEPLAN)-<BR>  
 *   <BR>
 *   <DIR>
 *     1.Change Status flag to Working.<BR>
 *     2.Renew Last updated Processing name. <BR>
 *   </DIR>
 *   <BR>
 *   -Registration of transportation table (CARRYINFO)-<BR>
 *   <DIR>
 *     Register the transportation table by the content of the input value and schedule information. <BR>
 *   </DIR>  
 *   <BR>
 *   -Creation of palette table (PALETTE)- <BR>
 *   <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/24</TD><TD>C.Kaminishizono</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:04:33 $
 * @author  $Author: suresh $
 */
public class AsItemStorageSCH extends AbstractAsrsControlSCH
{

	//#CM44456
	// Class variables -----------------------------------------------
	//#CM44457
	/**
	 * Class Name(Storage Submit)
	 */
	public static String CLASS_NAME = "AsItemStorageSCH";

	//#CM44458
	/**
	 * Storage Plan operation Class
	 */
	protected StoragePlanHandler wStrgPlanh = null;
	//#CM44459
	/**
	 * Storage Plan update key
	 */
	protected StoragePlanAlterKey wStrgPlanAKey = null;
	//#CM44460
	/**
	 * Storage Plan Retrieval key
	 */
	protected StoragePlanSearchKey wStrgPlanSKey = null;

	//#CM44461
	/**
	 * Work information Operation Class
	 */
	protected WorkingInformationHandler wWih = null;
	;
	//#CM44462
	/**
	 * Work information Update key
	 */
	protected WorkingInformationAlterKey wWiAKey = null;
	//#CM44463
	/**
	 * Work information Operation key
	 */
	protected WorkingInformationSearchKey wWiSKey = null;

	//#CM44464
	/**
	 * Inventory information Operation Class
	 */
	protected StockHandler wStkh = null;
	//#CM44465
	/**
	 * Inventory information Update key
	 */
	protected StockAlterKey wStkAKey = null;
	//#CM44466
	/**
	 * Inventory information Operation key
	 */
	protected StockSearchKey wStkSKey = null;
	
	//#CM44467
	// Class method --------------------------------------------------
	//#CM44468
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:04:33 $");
	}
	//#CM44469
	// Constructors --------------------------------------------------
	//#CM44470
	/**
	 * Initialize this Class. 
	 */
	public AsItemStorageSCH()
	{
		wMessage = null;
	}

	//#CM44471
	// Public methods ------------------------------------------------
	//#CM44472
	/**
	 * Method corresponding to the operation to acquire necessary data when initial is displayed the screen. <BR>
	 * Return corresponding Consignor Code and Consignor Name when only one Consignor Code exists in Work information. <BR>
	 * Return null when pertinent data does not exist or it exists by two or more.  <BR>
	 * Set null in < CODE>searchParam</CODE > because it does not need the search condition. 
	 * @param conn Instance to maintain connection with data base. 
	 * @param searchParam Instance of < CODE>AsScheduleParameter</CODE>Class with display data acquisition condition. 
	 * @return Class which mounts < CODE>Parameter</CODE > interface where retrieval result is included
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
		WorkingInformationReportFinder wFinder = new WorkingInformationReportFinder(conn);

		//#CM44473
		// Data retrieval
		//#CM44474
		// Status Flag(Storage)
		searchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		//#CM44475
		// Work status(Stand By)
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);

		searchKey.setConsignorCodeCollect();
		searchKey.setConsignorCodeGroup(1);

		AsScheduleParameter dispData = new AsScheduleParameter();

		if (wFinder.search(searchKey) == 1)
		{
			//#CM44476
			// Data retrieval			
			searchKey.KeyClear();
			//#CM44477
			// Status Flag(Storage)
			searchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
			//#CM44478
			// Work status(Stand By)
			searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			//#CM44479
			// Acquire new Consignor Name at the Latest registration date.
			searchKey.setRegistDateOrder(1, false);

			searchKey.setConsignorCodeCollect();
			searchKey.setConsignorNameCollect();

			if (wFinder.search(searchKey) > 0)
			{
				WorkingInformation[] consignorname = (WorkingInformation[]) wFinder.getEntities(1);

				dispData.setConsignorCode(consignorname[0].getConsignorCode());
				dispData.setConsignorName(consignorname[0].getConsignorName());
			}
		}
		wFinder.close();

		return dispData;
	}

	//#CM44480
	/**
	 * Receive the content of the area of the accumulation input from the screen as parameter, <BR>
	 * do mandatory check, Overflow check, Checking for duplication, and return the check result. <BR>
	 * Assume true to be an exclusive error when an occurrence of the condition error and pertinent data exist, <BR>
	 * and return false when pertinent data of same Line  No. does not exist in Inventory information. <BR>
	 * @param conn Instance to maintain connection with data base. 
	 * @param checkParam Instance of < CODE>AsScheduleParameter</CODE>Class with content of input. 
	 * @param inputParams Array of instance of <CODE>AsScheduleParameter</CODE>Class with content of area of filtering it. 
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams) throws ScheduleException
	{
		try
		{
			//#CM44481
			// Content of input area
			AsScheduleParameter param = (AsScheduleParameter) checkParam;
			//#CM44482
			// Content of area of filtering it
			AsScheduleParameter[] paramlist = (AsScheduleParameter[]) inputParams;

			//#CM44483
			// Check on Worker code and Password
			if (!checkWorker(conn, param))
			{
				return false;
			}

			String casepieceflag = param.getCasePieceFlag();
			int caseqty = param.getStorageCaseQty();
			int pieceqty = param.getStoragePieceQty();
			long inputqty = (long) param.getStorageCaseQty() * (long) param.getEnteringQty() + param.getStoragePieceQty();
			long planqty = (long) param.getAllocateCaseQty() * (long) param.getEnteringQty() + param.getAllocatePieceQty();

			//#CM44484
			// Input condition Check (Empty Palette Check)
			if (!isCorrectEmptyPB(param.getConsignorCode(), param.getItemCode(), casepieceflag, param.getUseByDate(), caseqty, pieceqty, param.getEnteringQty(), param.getBundleEnteringQty()))
			{
				if (StringUtil.isBlank(getErrorType()))
				{
					//#CM44485
					// 6023503=This Storage Plan data cannot be stocked in automatic operation Warehouse. Stock it besides automatic operation Warehouse. 
					setMessage("6023503");
				}
				return false;
			}
			
			//#CM44486
			// Input check of abnormal shelf
			if (!checkIrregularCode(param.getConsignorCode(), param.getItemCode()))
			{
				return false;
			}
			
			//#CM44487
			// Make Consignor Code, Storage plan date, Item Code, Case/Piece flag, and Expiry date a key and do Checking for duplication.
			//#CM44488
			// The key to Checking for duplication contains Expiry date when defined in WmsParam as an item which uniquely stocks it. 
			if (paramlist != null)
			{
				for (int i = 0; i < paramlist.length; i++)
				{
					if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
					{
						if (paramlist[i].getConsignorCode().equals(param.getConsignorCode())
							&& paramlist[i].getPlanDate().equals(param.getPlanDate())
							&& paramlist[i].getItemCode().equals(param.getItemCode())
							&& paramlist[i].getCasePieceFlag().equals(param.getCasePieceFlag())
							&& paramlist[i].getUseByDate().equals(param.getUseByDate()))
						{
							//#CM44489
							// 6023090 = Same data already exists. It is not possible to input it. 
							wMessage = "6023090";
							return false;
						}
					}
					else if (!WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
					{
						if (paramlist[i].getConsignorCode().equals(param.getConsignorCode())
							&& paramlist[i].getPlanDate().equals(param.getPlanDate())
							&& paramlist[i].getItemCode().equals(param.getItemCode())
							&& paramlist[i].getCasePieceFlag().equals(param.getCasePieceFlag()))
						{
							//#CM44490
							// 6023090 = Same data already exists. It is not possible to input it. 
							wMessage = "6023090";
							return false;
						}
					}
				}
			}

			//#CM44491
			// When Storage Case qty and Storage Piece qty are 0
			if (caseqty == 0 && pieceqty == 0)
			{
				//#CM44492
				// 6023198 = Storage Case qty or Input the value of one or more to Storage Piece qty. 
				wMessage = "6023198";
				return false;
			}

			//#CM44493
			// When Storage Case qty is not 0, Qty per case is 0
			if (param.getEnteringQty() == 0 && caseqty !=0)
			{
				//#CM44494
				// 6023506 = When Qty per case is 0, {0} cannot be entered.
				wMessage = "6023506" + wDelim + DisplayText.getText("LBL-W0369");
				return false;		
			}

			if (inputqty > planqty)
			{
			    if (planqty != 0) 
			    {
					//#CM44495
					// 6023058 = {0}Input the value below {1}. Check it with Storage qty (total of the rows). 
					wMessage = "6023058" + wDelim + DisplayText.getText("LBL-W0377") + wDelim + WmsFormatter.getNumFormat(planqty);
					return false;
			    } 
			    else 
			    {
					//#CM44496
					// 6023493 = It is not possible to input it because it has already set it for the scheduled quantity.
					wMessage = "6023493";
					return false;      
			    }
			}

			//#CM44497
			// Overflow check
			if (inputqty > WmsParam.MAX_STOCK_QTY)
			{
				//#CM44498
				// 6023058 = {0}Input the value below {1}. Check it with Storage qty (total of the rows). 
				wMessage = "6023058" + wDelim + DisplayText.getText("LBL-W0377") + wDelim + MAX_STOCK_QTY_DISP;
				return false;
			}

			//#CM44499
			// Display number check
			if (paramlist != null && paramlist.length + 1 > WmsParam.MAX_NUMBER_OF_DISP)
			{
				//#CM44500
				// 6023096 = Because the number of cases exceeds the {0} matter, it is not possible to input it. 
				wMessage = "6023096" + wDelim + MAX_NUMBER_OF_DISP_DISP;
				return false;
			}
			
			//#CM44501
			// When the number of maximum consolidation is exceeded, it is assumed that it makes an error. 
			if (paramlist != null && paramlist.length + 1 > getMaxMixedPalette(conn, param.getWareHouseNo()))
			{
				//#CM44502
				// 6023488=Because the number of consolidation exceeds the number of maximum consolidation, it is not possible to set it. 
				wMessage = "6023488";
				return false;
			}

		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}

		//#CM44503
		// 6001019 = The input was accepted. 
		wMessage = "6001019";
		return true;
	}

	//#CM44504
	/**
	 * Receive the content input from the screen as parameter, and begin the No plan storage setting schedule. <BR>
	 * Receive parameter by the array because the input of two or more of set of straightening data is assumed. <BR>
	 * Refer to the paragraph of the Class explanation for detailed operation. <BR>
	 * Return true when the schedule ends normally and return false when failing.
	 * @param conn Instance to maintain connection with data base. 
	 * @param startParams Array of instance of < CODE>AsScheduleParameter</CODE>Class with set content. 
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM44505
			// Check on Worker code and Password
			AsScheduleParameter workparam = (AsScheduleParameter) startParams[0];
			if (!checkWorker(conn, workparam))
			{
				return false;
			}
			
			//#CM44506
			// Next day update Processing check
			if (isDailyUpdate(conn))
			{
				return false;
			}

			//#CM44507
			// When the number of maximum consolidation is exceeded, it is assumed that it makes an error. 
			//#CM44508
			// Check it here because different Warehouse might been selected when the input button is pressed, and Submit button is pressed. 
			if (startParams != null && startParams.length > getMaxMixedPalette(conn, workparam.getWareHouseNo()))
			{
				//#CM44509
				// 6023488=Because the number of consolidation exceeds the number of maximum consolidation, it is not possible to set it. 
				wMessage = "6023488";
				return false;
			}
			
			AsScheduleParameter[] param = (AsScheduleParameter[]) startParams;

			//#CM44510
			// Check whether Status of Station etc. can be stocked. 
			if (!checkSystemStatus(conn, param[0]))
			{
				return false;
			}

			//#CM44511
			// Generate each handler. 
			createHandler(conn);
			//#CM44512
			// Do the lock processing of Work information. 
			try
			{
				if (!workingLock(param))
				{
					//#CM44513
					// 6003006=This data is not useable because it was updated in other terminals. 
					wMessage = "6003006";
					return false;
				}
			}
			catch (LockTimeOutException e)
			{
				//#CM44514
				// 6027008 = This data is not useable because it is updating it with other terminals. 
				wMessage = "6027008";
				return false;
			}

			//#CM44515
			// Maintain the screen data. 
			String workerCode = param[0].getWorkerCode();
			String workerName = getWorkerName(conn, workerCode);
			String terminalNo = param[0].getTerminalNumber();

			//#CM44516
			// Acquire each registered one mind key. 
			SequenceHandler sequence = new SequenceHandler(conn);
			String batch_seqno = sequence.nextStoragePlanBatchNo();
			String schno_seqno = sequence.nextScheduleNumber();
			String workno_seqno = sequence.nextStorageWorkNumber();
			int pltid_seqno = sequence.nextPaletteId();
			String carrykey_seqno = sequence.nextCarryKey();

			//#CM44517
			// To check the route, 
			//#CM44518
			// Make the palette instance from screen input information. 
			//#CM44519
			// Do not register to DB yet. 
			Palette plt = getPalette(conn, pltid_seqno, param[0]);

			//#CM44520
			// Check the route. 
			//#CM44521
			// End processing because null is restored when there is no route. 
			//#CM44522
			// Moreover, information on the palette instance might be rewritten. 
			RouteController rc = new RouteController(conn, false);
			if (!canStorageRoute(conn, rc, plt, param[0].getWareHouseNo()))
			{
				//#CM44523
				// Messege of transportation route NG is acquired. 
				setMessage(getRouteErrorMessage(rc.getRouteStatus()));
				return false;
			}
			
			//#CM44524
			// When the transportation destination is a shelf, Shelf  No. is set. 
			String locationno = null ;
			if (rc.getDestStation() instanceof Shelf)
			{
				locationno = rc.getDestStation().getStationNumber();
			}

			//#CM44525
			// Maintain this total work amount. 
			int workqty = 0;

			//#CM44526
			// Processes the number of cases for filtering. 
			//#CM44527
			// Renew Work information and Inventory information and make it. 
			for (int i = 0; i < param.length; i++)
			{
				//#CM44528
				// Storage qty
				int inputqty = param[i].getStorageCaseQty() * param[i].getEnteringQty() + param[i].getStoragePieceQty();

				//#CM44529
				// The work amount is added and it crowds this time. 
				workqty = addWorkQty(workqty, inputqty);

				//#CM44530
				// Renew Work information. 
				updateWorkinginfo(
					conn,
					param[i].getWorkingNo(),
					carrykey_seqno,
					plt.getWHStationNumber(),
					inputqty,
					locationno,
					param[i].getUseByDate(),
					workerCode,
					workerName,
					terminalNo,
					batch_seqno);
				
				//#CM44531
				// Acquire Work information after updating. 
				WorkingInformation wi = getWorkingInfomation(param[i].getWorkingNo());
				
				//#CM44532
				// Do not do the stock update at this timing. 
				
				//#CM44533
				// Update Storage Plan information. 
				updateStoragePlan(wi.getPlanUkey());
			}

			//#CM44534
			// Make transportation information. 
			createCarryinfo(conn,
				CarryInformation.WORKTYPE_PLAN_STORAGE,
				schno_seqno,
				locationno,
				workno_seqno,
				CarryInformation.RETRIEVALDETAIL_UNKNOWN,
				rc.getSrcStation().getStationNumber(),
				rc.getDestStation().getStationNumber(),
				pltid_seqno,
				getCmdStatus(rc),
				CarryInformation.PRIORITY_EMERGENCY,
				CarryInformation.CARRYKIND_STORAGE,
				carrykey_seqno,
				getAisleStationNo(rc));
			
			//#CM44535
			// Make palette information. 
			PaletteHandler paletteHandler = new PaletteHandler(conn);
			paletteHandler.create(plt);

			//#CM44536
			// Specified Station makes work instruction information only when there is a work display. 
			if (rc.getSrcStation().getOperationDisplay() != Station.NOT_OPERATIONDISPLAY)
			{
				createOperationDisplay(conn, carrykey_seqno, rc.getSrcStation().getStationNumber());
			}

			//#CM44537
			// 6001006 = Submitted.
			wMessage = "6001006";
			
			//#CM44538
			// When Print List is instructed from the screen, Prints the stock instruction sheet for this time. 
			//#CM44539
			// Set the message at the same time. 
			if (param[0].getListFlg())
			{
				startPrint(conn, rc.getSrcStation().getStationNumber(), batch_seqno);
			}

			return true;
		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	//#CM44540
	// Protected methods -----------------------------------------------
	//#CM44541
	/**
	 * Generate the following DB operations Class. <BR>
	 * <DIR>
	 *  Storage Plan<BR>
	 *  Work information<BR>
	 *  Inventory information<BR>
	 * </DIR>
	 * 
	 * @param conn Database connection
	 */
	protected void createHandler(Connection conn)
	{
		wStrgPlanh = new StoragePlanHandler(conn);
		wStrgPlanAKey = new StoragePlanAlterKey();
		wStrgPlanSKey = new StoragePlanSearchKey();

		wWih = new WorkingInformationHandler(conn);
		wWiAKey = new WorkingInformationAlterKey();
		wWiSKey = new WorkingInformationSearchKey();

		wStkh = new StockHandler(conn);
		wStkAKey = new StockAlterKey();
		wStkSKey = new StockSearchKey();		
	}

	//#CM44542
	/**
	 * Do the exclusive operation of the Work information table.  <BR>
	 * @param param[]      AsScheduleParameter array
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when this Method is called. 
	 * @throws LockTimeOutException It is notified when the lock is not opened even if time passes. 
	 */
	protected boolean workingLock(AsScheduleParameter[] param) throws ReadWriteException, ScheduleException, LockTimeOutException
	{
		for (int i = 0; i < param.length; i++)
		{
			wWiSKey.KeyClear();
			//#CM44543
			// Work No
			wWiSKey.setJobNo(param[i].getWorkingNo());
			//#CM44544
			// Last updated date and time
			wWiSKey.setLastUpdateDate(param[i].getLastUpdateDate());

			WorkingInformation[] rWorkInfo = (WorkingInformation[]) wWih.findForUpdateNowait(wWiSKey);
			if (rWorkInfo == null || rWorkInfo.length == 0)
			{
				return false;
			}
		}
		return true;
	}

	//#CM44545
	/**
	 * Check whether Status of the system can be stocked. 
	 * -Be online. 
	 * -Status check of Storage Station
	 * @param conn Database connection
	 * @param param Screen input information
	 * @return true:It is possible to store.  false:It is not possible to store.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected boolean checkSystemStatus(Connection conn, AsScheduleParameter param) throws ReadWriteException
	{
		//#CM44546
		// Do the Status check of Storage Station.
		//#CM44547
		// Online check
		StationHandler stHandler = new StationHandler(conn);
		StationSearchKey stSearchKey = new StationSearchKey();
		stSearchKey.setStationNumber(param.getFromStationNo());
		Station[] frst = (Station[]) stHandler.find(stSearchKey);

		if (frst[0].getControllerNumber() == 0)
		{
			stSearchKey.KeyClear();
			stSearchKey.setParentStationNumber(frst[0].getStationNumber());
			stSearchKey.setControllerNumberCollect("DISTINCT");

			ASStationHandler AstHandler = new ASStationHandler(conn);
			frst = (Station[]) AstHandler.getWorkStation(frst[0].getStationNumber());
		}
		else
		{
			//#CM44548
			// Check Status of selection Station. 
			String r_Msg = isStorageStationCheck(conn, frst[0]);
			if (!StringUtil.isBlank(r_Msg))
			{
				wMessage = r_Msg;
				return false;
			}
		}
		
		//#CM44549
		// System Status check
		GroupControllerHandler gch = new GroupControllerHandler(conn);
		GroupControllerSearchKey gcSKey = new GroupControllerSearchKey();
		boolean findflag = false;
		for (int i = 0; i < frst.length; i++)
		{
			gcSKey.KeyClear();
			gcSKey.setControllerNumber(frst[i].getControllerNumber());

			GroupController[] gc = (GroupController[]) gch.find(gcSKey);

			if (gc[0].getStatus() == GroupController.STATUS_ONLINE)
			{
				findflag = true;
				break;
			}
		}
		
		if (!findflag)
		{
			//#CM44550
			// Make system Status online. 
			setMessage("6013023");
			return false;
		}

		return true;
	}

	//#CM44551
	/**
	 * Acquire the palette instance to set necessary information from Screen input information. 
	 * In this Method, do not add to DB. 
	 * @param conn Database connection
	 * @param pleId Palette ID of generated palette
	 * @param param Screen input information
	 * @return Generated palette instance
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected Palette getPalette(Connection conn, int pleId, AsScheduleParameter param) throws ReadWriteException
	{
		try
		{
			//#CM44552
			// Retrieve Station information with WarehouseStNo to acquire StNo now. 
			StationHandler sthandler = new StationHandler(conn);
			StationSearchKey stkey = new StationSearchKey();
			stkey.setStationNumber(param.getFromStationNo());
			Station dispSt = (Station) sthandler.findPrimary(stkey);

			//#CM44553
			// To acquire load high information from the zone of the screen specification and to set it in the palette
			//#CM44554
			// Acquire Hard Zone information. 
			HardZoneHandler hzHandler = new HardZoneHandler(conn);
			HardZoneSearchKey hzSKey = new HardZoneSearchKey();
			hzSKey.setHardZoneID(Integer.parseInt(param.getHardZone()));
			HardZone rHardzone = (HardZone) hzHandler.findPrimary(hzSKey);

			Palette plt = createInstancePalette(dispSt, pleId, rHardzone.getHeight());

			return plt;
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM44555
	/**
	 * Check the route to store it. 
	 * Return True when there is a route. 
	 * Return False when there is no route. 
	 * <B>Moreover, change the present location of palette information to the transportation destination shelf when transportation destination is a shelf. </B>
	 * 
	 * @param conn   Database connection
	 * @param rc     Transportation route control Class
	 * @param whStNo Warehouse Station No. of the transportation destination.
	 * @return Return True when there is route which can be transported, False when otherwise.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean canStorageRoute(Connection conn, RouteController rc, Palette plt, String whStNo) throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM44556
			// Check the stock transportation route. 
			//#CM44557
			// Check the route from Warehouse information and palette information. 
			WareHouseHandler whhandler = new WareHouseHandler(conn);
			WareHouseSearchKey whkey = new WareHouseSearchKey();
			whkey.setStationNumber(whStNo);
			WareHouse whouse = (WareHouse) whhandler.findPrimary(whkey);

			if (rc.storageDeterminSCH(plt, whouse))
			{
				//#CM44558
				// When the transportation destination is a shelf, Shelf  No. is set in the present location of the palette. 
				if (rc.getDestStation() instanceof Shelf)
				{
					plt.setCurrentStationNumber(rc.getDestStation().getStationNumber());
				}
			}
			else
			{
				return false;
			}

			return true;
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	//#CM44559
	/**
	 * Work information is acquired by the key of Work No. 
	 * @param pJobNo Work No.
	 * @return Work information
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected WorkingInformation getWorkingInfomation(String pJobNo) throws ReadWriteException
	{
		try
		{
			//#CM44560
			// Acquire processing object Work information. 
			wWiSKey.KeyClear();
			wWiSKey.setJobNo(pJobNo);
			WorkingInformation workInfo = (WorkingInformation) wWih.findPrimary(wWiSKey);
			
			return workInfo;			
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM44561
	/**
	 * Renew the Work information table. 
	 * @param conn Connection object with database
	 * @param pJobNo      Work No.
	 * @param pCarryKey   Transportation key
	 * @param pAreaNo     Working Area  No.
	 * @param pInputQty   Actual work qty 
	 * @param pLocationNo Work resultLocation
	 * @param pUseByDate  Expiry date
	 * @param pWorkerCode Worker code
	 * @param pWorkerName Worker name
	 * @param pTerminalNo Terminal No.
	 * @param pBatchNo    Batch No. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected void updateWorkinginfo(
		Connection conn,
		String pJobNo,
		String pCarryKey,
		String pAreaNo,
		int pInputQty,
		String pLocationNo,
		String pUseByDate,
		String pWorkerCode,
		String pWorkerName,
		String pTerminalNo,
		String pBatchNo)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			WorkingInformation wi = getWorkingInfomation(pJobNo);
				
			//#CM44562
			// Request the reservation this time. 
			int pendingQty = wi.getPlanEnableQty() - pInputQty;
			if (pendingQty < 0)
			{
				pendingQty = 0;
			}

			if (pendingQty == 0)
			{
				//#CM44563
				//Update process for setting this time
				//#CM44564
				// Update Work  No. to the key. 
				wWiAKey.KeyClear();
				wWiAKey.setJobNo(wi.getJobNo());
				wWiAKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
				wWiAKey.updateAreaNo(pAreaNo);
				wWiAKey.updateLocationNo(pLocationNo);
				wWiAKey.updateSystemConnKey(pCarryKey);
				wWiAKey.updateSystemDiscKey(WorkingInformation.SYSTEM_DISC_KEY_ASRS);
				wWiAKey.updateUseByDate(pUseByDate);
				wWiAKey.updateWorkerCode(pWorkerCode);
				wWiAKey.updateWorkerName(pWorkerName);
				wWiAKey.updateTerminalNo(pTerminalNo);
				wWiAKey.updateBatchNo(pBatchNo);
				wWiAKey.updateLastUpdatePname(CLASS_NAME);
				//#CM44565
				// Update of data
				wWih.modify(wWiAKey);
			}
			else
			{
				//#CM44566
				//When paying it in installments
				//#CM44567
				// Make the data which becomes Working. 
				//#CM44568
				// Copy the data of the division origin. (data before it updates it)
				WorkingInformation newWorkinfo = (WorkingInformation) wi.clone();;
				//#CM44569
				// Allocate new Work No. 
				SequenceHandler sh = new SequenceHandler(conn);
				String jobNo_seq = sh.nextJobNo();
				newWorkinfo.setJobNo(jobNo_seq);
				newWorkinfo.setCollectJobNo(jobNo_seq);
				newWorkinfo.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
				newWorkinfo.setAreaNo(pAreaNo);
				newWorkinfo.setLocationNo(pLocationNo);
				newWorkinfo.setUseByDate(pUseByDate);
				newWorkinfo.setSystemConnKey(pCarryKey);
				newWorkinfo.setSystemDiscKey(WorkingInformation.SYSTEM_DISC_KEY_ASRS);
				newWorkinfo.setPlanEnableQty(pInputQty);
				newWorkinfo.setWorkerCode(pWorkerCode);
				newWorkinfo.setWorkerName(pWorkerName);
				newWorkinfo.setTerminalNo(pTerminalNo);
				newWorkinfo.setBatchNo(pBatchNo);// 印刷のため、バッチNo.を更新します
				newWorkinfo.setRegistPname(CLASS_NAME);
				newWorkinfo.setLastUpdatePname(CLASS_NAME);
				wWih.create(newWorkinfo);
				
				//#CM44570
				// Update Work  No. to the key. 
				wWiAKey.KeyClear();
				wWiAKey.setJobNo(wi.getJobNo());
				wWiAKey.updatePlanEnableQty(wi.getPlanEnableQty() - pInputQty);
				wWiAKey.updateLastUpdatePname(CLASS_NAME);
				//#CM44571
				// Update of data
				wWih.modify(wWiAKey);
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
		catch (DataExistsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//#CM44572
	/**
	 * Update Storage Plan information to Working. 
	 * @param planUkey Plan unique key which updates it
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected void updateStoragePlan(String planUkey) throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM44573
			// Storage Plan unique key and do the update processing to the key. 
			wStrgPlanAKey.KeyClear();
			wStrgPlanAKey.setStoragePlanUkey(planUkey);
			wStrgPlanAKey.updateStatusFlag(StoragePlan.STATUS_FLAG_NOWWORKING);
			wStrgPlanAKey.updateLastUpdatePname(CLASS_NAME);
			wStrgPlanh.modify(wStrgPlanAKey);
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//#CM44574
	/**
	 * Decide Status of transportation of making transportation information from Transportation originStation.
	 * Return "Begin" by "Drawing" besides when there is work display operation. 
	 * @param rc Stock transportation route
	 * @return Status of transportation
	 */
	protected int getCmdStatus(RouteController rc)
	{
		int cmdStatus = 0;
		if (rc.getSrcStation().getOperationDisplay() == Station.OPERATIONINSTRUCTION)
		{
			cmdStatus = CarryInformation.CMDSTATUS_ALLOCATION;
		}
		else
		{
			cmdStatus = CarryInformation.CMDSTATUS_START;
		}
		
		return cmdStatus;
	}
	
	//#CM44575
	/**
	 * Aisle Acquire Station No of the transportation destination from transportation route information.  <BR>
	 * If the transportation destination is available in Shelf, That shelf's Aisle Station No.., 
	 * Set the value if aisle Station is not set in the shelf (Warehouse etc.) but the transportation destination Station.
	 * Return null when nothing is being set by uniting the aisle by parents Station.
	 * @param rc Stock transportation route
	 * @return At the transportation destinationAisle Station No..
	 */
	protected String getAisleStationNo(RouteController rc)
	{
		String aisleStNo = null;
		if (rc.getDestStation() instanceof Shelf)
		{
			aisleStNo = rc.getDestStation().getParentStationNumber();
		}
		else
		{
			if (rc.getSrcStation().getAisleStationNumber() != null)
			{
				aisleStNo = rc.getSrcStation().getAisleStationNumber();
			}
		}
		
		return aisleStNo;
	}

	//#CM44576
	/**
	 * Pass Batch  No. to ASRS stock list issue processing Class. <BR>
	 * Do not do the print processing when there is no print data. <BR>
	 * It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
	 * @param  conn Connection Connection object with database
	 * @param  batchno Batch No.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected void startPrint(Connection conn, String stationno, String batchno) throws ReadWriteException, ScheduleException
	{
		AsItemStorageWriter writer = new AsItemStorageWriter(conn);
		writer.setBatchNumber(batchno);
		writer.setStationNo(stationno);

		//#CM44577
		// Begin the printing job. 
		if (writer.startPrint())
		{
			//#CM44578
			// 6021012 = The print ended normally after it had set it. 
			wMessage = "6021012";
		}
		else
		{
			//#CM44579
			// 6007042=After submitting, It failed in the print. Refer to the log. 
			wMessage = "6007042";
		}
	}
	//#CM44580
	// Private methods -----------------------------------------------
}
//#CM44581
//end of class
