//#CM44814
//$Id: AsNoPlanStorageSCH.java,v 1.2 2006/10/30 00:53:58 suresh Exp $

//#CM44815
/*
 * Copyright 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule;

import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.dbhandler.ASStationHandler;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.report.AsNoPlanStorageWriter;
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
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM44816
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * Class to do the WEB ASRS No plan storage setting processing. <BR>
 * Receive the content input from the screen as parameter, and do the making processing of ASRS No plan storage Work information.  <BR>
 * Do not do Comment-rollback of the transaction though each Method of this Class must wear the Connection object<BR>
 * and do the update processing of the receipt data base.<BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Initial display processing(< CODE>initFind() </ CODE > method) <BR>
 * <BR>
 * <DIR>
 * 	Return Consignor Code when Status retrieves from Inventory information is Stand By<BR>
 *	and only same Consignor Code exists. <BR>
 * 	Return null when two or more Consignor Code exists. <BR>
 *	<BR>
 *	<Search condition> <BR>
 *	<DIR>
 *     Stock status is center stocking.<BR>
 *     Stock qty is one or more. <BR>
 *     AS/RS Stock <BR>
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
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.Input button pressing processing(< CODE>check() </ CODE > method)<BR>
 * <BR>
 * <DIR>
 *   Receive the content input from the screen as parameter, do an mandatory check and Overflow check and the repetition check, and return the check result. <BR>
 *   Return true when pertinent data of same line No. does not exist in the inventory information and return false when the condition error occurs and pertinent data exists. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 *   <BR>
 *   [parameter] *Mandatory Input  +Any one is Mandatory Input <BR>
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
 * </DIR>
 * <BR>
 * 3.StorageProcessing when Start button is pressed(<CODE>startSCH()</CODE>Method) <BR><BR>
 * <BR>
 * <DIR>
 *   Receive the content displayed in the filter area as parameter, and do the ASRS No plan storage setting processing. <BR>
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
 *   -Do the decision processing of Storage shelf. -<BR>
 *   <BR>
 *   -Registration of work information table (DNWORKINFO)-<BR>
 *   <DIR>
 *     Process Registration of work information by the content of the input.  <BR>
 *   </DIR>  
 *   <BR>
 *   -Registration of transportation table (CARRYINFO)-<BR>
 *     <DIR>
 *     Register the transportation table by the content of the input. <BR>
 *     </DIR>  
 *   <BR>
 *   -Registration and renewal of stock information table (DMSTOCK)-<BR>
 *   <BR>
 *   <DIR>
 *     Register the stock information table by the content of the input. <BR>
 *     [Processing condition check] <BR>
 *     <BR>
 *     -Overflow check-<BR>  
 *     <BR>
 *   </DIR>
 *   -Registration of palette table (PALETTE)- <BR>
 *   <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/03</TD><TD>C.Kaminishizono</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 00:53:58 $
 * @author  $Author: suresh $
 */
public class AsNoPlanStorageSCH extends AbstractAsrsControlSCH  {

	//#CM44817
	// Class variables -----------------------------------------------
	//#CM44818
	/**
	 * Class Name(No plan storage Submit( ASRS ))
	 */
	public static String CLASS_STORAGE = "AsNoPlanStorageSCH";

	//#CM44819
	// Class method --------------------------------------------------
	//#CM44820
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 00:53:58 $");
	}
	//#CM44821
	// Constructors --------------------------------------------------
	//#CM44822
	/**
	 * Initialize this Class. 
	 */
	public AsNoPlanStorageSCH()
	{
		wMessage = null;
	}

	//#CM44823
	/**
	 * Batch No.
	 */
	public static String[] wBachino = new String[255];
	public static int wBachi_Seq ;

	//#CM44824
	// Public methods ------------------------------------------------
	//#CM44825
	/**
	 * Method corresponding to the operation to acquire necessary data when initial is displayed the screen. <BR>
	 * Return corresponding Consignor Code and Consignor Name when only one Consignor Code exists in Inventory information. <BR>
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
		StockReportFinder stockFinder = new StockReportFinder(conn);
		StockSearchKey searchKey = new StockSearchKey();

		//#CM44826
		// Data retrieval
		//#CM44827
		// Stock status(Center Stocking)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM44828
		// Stock qty must be one or more. 
		searchKey.setStockQty(1, ">=");

		//#CM44829
		// Acquire only the stock of AS/RS. 
		AreaOperator areaOpe = new AreaOperator(conn);
		int[] areaType = {Area.SYSTEM_DISC_KEY_ASRS};
		searchKey.setAreaNo(areaOpe.getAreaNo(areaType));

		searchKey.setConsignorCodeCollect();
		searchKey.setConsignorCodeGroup(1);

		AsScheduleParameter dispData = new AsScheduleParameter();

		if (stockFinder.search(searchKey) == 1)
		{
			//#CM44830
			// Data retrieval			
			searchKey.KeyClear();
			//#CM44831
			// Stock status(Center Stocking)
			searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM44832
			// Stock qty must be one or more. 
			searchKey.setStockQty(1, ">=");
			//#CM44833
			// Acquire only the stock of AS/RS. 
			searchKey.setAreaNo(areaOpe.getAreaNo(areaType));
			//#CM44834
			// Acquire Consignor Name that Latest Updated date & time is new. 
			searchKey.setLastUpdateDateOrder(1, false);

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

	//#CM44835
	/** 
	 * Receive the content of the area of the accumulation input from the screen as parameter, do the mandatory check, <BR>
	 * Overflow check, Duplication Check and return the check result.   <BR>
	 * Assume true to be an exclusive error when an occurrence of the condition error and pertinent data exist,<BR>
	 * and return false when pertinent data of same Line  No. does not exist in Inventory information.   <BR>
	 * @param conn Instance to maintain connection with data base. 
	 * @param checkParam Instance of < CODE>AsScheduleParameter</CODE>Class with content of input. 
	 * @param inputParams Array of instance of <CODE>AsScheduleParameter</CODE>Class with content of area of filtering it. 
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams)
		throws ScheduleException, ReadWriteException
	{
		try
		{
			//#CM44836
			// Content of input area
			AsScheduleParameter param = (AsScheduleParameter) checkParam;
			//#CM44837
			// Content of area of filtering it
			AsScheduleParameter[] paramlist = (AsScheduleParameter[]) inputParams;

			//#CM44838
			// Check on Worker code and Password
			if (!checkWorker(conn, param))
			{
				return false;
			}

			String casepieceflag = param.getCasePieceFlag();
			int enteringqty = param.getEnteringQty();
			int bundleenteringqty = param.getBundleEnteringQty();
			int caseqty = param.getStorageCaseQty();
			int pieceqty = param.getStoragePieceQty();
			
			//#CM44839
			// Input Check of Empty Palette
			if (!isCorrectEmptyPB(param.getConsignorCode(), param.getItemCode(), casepieceflag, param.getUseByDate()
													, caseqty, pieceqty, enteringqty, bundleenteringqty))
			{
				return false;
			}

			//#CM44840
			// Input check of Abnormal Location
			if (!checkIrregularCode(param.getConsignorCode(), param.getItemCode()))
			{
				return false;
			}

			//#CM44841
			// Input value check
			if (!storageInputCheck(casepieceflag, enteringqty, caseqty, pieceqty))
			{
				return false;
			}
			
			//#CM44842
			// Overflow check
			long inputqty =
				(long) param.getStorageCaseQty() * (long) param.getEnteringQty()
					+ param.getStoragePieceQty();
			if (inputqty > WmsParam.MAX_STOCK_QTY)
			{
				//#CM44843
				// 6023058 = {0}Input the value below {1}. Check it with Storage qty (total of the rows). 
				wMessage =
					"6023058"
						+ wDelim
						+ DisplayText.getText("LBL-W0377")
						+ wDelim
						+ MAX_STOCK_QTY_DISP;
				return false;
			}

			//#CM44844
			// Display number check
			if (paramlist != null && paramlist.length + 1 > WmsParam.MAX_NUMBER_OF_DISP)
			{
				//#CM44845
				// 6023096 = Because the number of cases exceeds the {0} matter, it is not possible to input it. 
				wMessage = "6023096" + wDelim + MAX_NUMBER_OF_DISP_DISP;
				return false;
			}
			
			//#CM44846
			// When the number of maximum consolidation is exceeded, it is assumed that it makes an error. 
			if (paramlist != null && paramlist.length + 1 > getMaxMixedPalette(conn, param.getWareHouseNo()))
			{
				//#CM44847
				// 6023488=Because the number of consolidation exceeds the number of maximum consolidation, it is not possible to set it. 
				wMessage = "6023488";
				return false;
			}
			
			//#CM44848
			// Checking for duplication
			//#CM44849
			// Make Consignor Code, Item Code, Case/Piece flag, and Expiry date a key and do Checking for duplication. 
			//#CM44850
			// The key to Checking for duplication contains Expiry date when defined in WmsParam as an item which uniquely stocks it. 
			if (paramlist != null)
			{
				String emppbConsignCode = WmsParam.EMPTYPB_CONSIGNORCODE;
				for (int i = 0; i < paramlist.length; i++)
				{
					if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
					{
						if (paramlist[i].getConsignorCode().equals(param.getConsignorCode())
							&& paramlist[i].getItemCode().equals(param.getItemCode())
							&& paramlist[i].getCasePieceFlag().equals(param.getCasePieceFlag())
							&& paramlist[i].getUseByDate().equals(param.getUseByDate()))
						{
							//#CM44851
							// 6023090 = Same data already exists. It is not possible to input it. 
							wMessage = "6023090";
							return false;
						}
					}
					else if (!WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
					{
						if (paramlist[i].getConsignorCode().equals(param.getConsignorCode())
							&& paramlist[i].getItemCode().equals(param.getItemCode())
							&& paramlist[i].getCasePieceFlag().equals(param.getCasePieceFlag()))
						{
							//#CM44852
							// 6023090 = Same data already exists. It is not possible to input it. 
							wMessage = "6023090";
							return false;
						}
					}
					
					//#CM44853
					// Consolidation check of empty palette and other articles
					//#CM44854
					// It is not possible to consolidate it with an empty palette and the other articles. 
					//#CM44855
					// When empty Palette is input, there is working stock after filtering
					if(param.getConsignorCode().equals(emppbConsignCode))
					{
						//#CM44856
						// 6013092=A usual stock and an empty palette cannot be consolidated. 
						wMessage = "6013092";
						return false;
					}
					//#CM44857
					// There is empty Palette after filtering, and when the working stock is input
					else if(paramlist[i].getConsignorCode().equals(emppbConsignCode))
					{
						//#CM44858
						// 6013092=A usual stock and an empty palette cannot be consolidated. 
						wMessage = "6013092";
						return false;
					}
				}
			}
		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}

		//#CM44859
		// 6001019 = The input was accepted. 
		wMessage = "6001019";
		return true;
	}

	//#CM44860
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
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM44861
			// Definition for route check
			RouteController rc = new RouteController(conn, false);
			Stock stock = new Stock() ;

			//#CM44862
			// Check on Worker code and Password
			AsScheduleParameter workparam = (AsScheduleParameter) startParams[0];
			if (!checkWorker(conn, workparam))
			{
				return false;
			}

			//#CM44863
			// Next day update Processing check
			if (isDailyUpdate(conn))
			{
				return false;
			}

			//#CM44864
			// When the number of maximum consolidation is exceeded, it is assumed that it makes an error. 
			//#CM44865
			// Check it here because different Warehouse might been selected when the input button is pressed, and Submit button is pressed. 
			if (startParams != null && startParams.length > getMaxMixedPalette(conn, workparam.getWareHouseNo()))
			{
				//#CM44866
				// 6023488=Because the number of consolidation exceeds the number of maximum consolidation, it is not possible to set it. 
				wMessage = "6023488";
				return false;
			}
			
			AsScheduleParameter[] param = (AsScheduleParameter[]) startParams;

			//#CM44867
			// Online check
			StationHandler stHandler = new StationHandler(conn);
			ASStationHandler AstHandler = new ASStationHandler(conn);
			StationSearchKey stSearchKey = new StationSearchKey();
			GroupControllerHandler wGcHandler = new GroupControllerHandler(conn);
			GroupControllerSearchKey wGcSearchKey = new GroupControllerSearchKey();					
			HardZoneHandler hzHandler = new HardZoneHandler(conn);
			HardZoneSearchKey hzSKey = new HardZoneSearchKey();					
			//#CM44868
			// Station No
			stSearchKey.KeyClear();
			stSearchKey.setStationNumber(param[0].getFromStationNo());

			Station[] rstation = null;
			
			rstation = (Station[]) stHandler.find(stSearchKey);

			if (rstation[0].getControllerNumber() == 0)
			{
				stSearchKey.KeyClear();
				stSearchKey.setParentStationNumber(rstation[0].getStationNumber());
				stSearchKey.setControllerNumberCollect("DISTINCT");

				rstation = (Station[]) AstHandler.getWorkStation(rstation[0].getStationNumber());				
			}
			else
			{				
				//#CM44869
				// Check Status of selection Station. 
				String r_Msg = isStorageStationCheck(conn, rstation[0]);
				if (!StringUtil.isBlank(r_Msg))
				{
					wMessage = r_Msg;
					return false;
				}
			}
			//#CM44870
			// System Status check
			boolean findflag = false;
			for (int i = 0; i < rstation.length; i++)
			{
				wGcSearchKey.KeyClear();
				wGcSearchKey.setControllerNumber(rstation[i].getControllerNumber());
			
				GroupController[] rGroupControll = (GroupController[])wGcHandler.find(wGcSearchKey);
			
				if (rGroupControll[0].getStatus() == GroupController.STATUS_ONLINE)
				{
					findflag = true;
					break;
				}
			}
			if (!findflag)
			{
				//#CM44871
				// Make system Status online. 
				setMessage("6013023");
				return false;
			}

			if (param != null)
			{
				//#CM44872
				// Worker code
				String workercode = param[0].getWorkerCode();
				//#CM44873
				// Worker Name
				String workerName = getWorkerName(conn, workercode);

				//#CM44874
				// Terminal No.
				String terminalno = param[0].getTerminalNumber();
				//#CM44875
				// Work Flag(No plan storage)
				String jobtype = Stock.JOB_TYPE_EX_STORAGE;
				//#CM44876
				// Acquire each registered one mind key. 
				SequenceHandler sequence = new SequenceHandler(conn);
				//#CM44877
				// Batch No.(One setting commonness)
				String batch_seqno = sequence.nextNoPlanBatchNo();
				String schno = sequence.nextScheduleNumber() ;

				//#CM44878
				// Work amount
				int workqty = 0;
				
				//#CM44879
				// Work No.(For transportation file)
				String workno = sequence.nextStorageWorkNumber();
				//#CM44880
				// Palette ID
				int paletteid = sequence.nextPaletteId() ;
				//#CM44881
				// Transportation key
				String carrykey = sequence.nextCarryKey() ;
				//#CM44882
				// Location No. 
				String locationno = null ;
				//#CM44883
				// Shipper code
				String customercode = "" ;
				//#CM44884
				// Shipper name
				String customername = "" ;

				//#CM44885
				// Station information (STATION) retrieval
				StationHandler sthandler = new StationHandler(conn);
				StationSearchKey stkey = new StationSearchKey();
			
				stkey.KeyClear();
				stkey.setStationNumber(param[0].getFromStationNo());
				Station station = (Station) sthandler.findPrimary(stkey);

				hzSKey.KeyClear();
				hzSKey.setHardZoneID(Integer.parseInt(param[0].getHardZone()));
				
				HardZone rHardzone = (HardZone)hzHandler.findPrimary(hzSKey);

				//#CM44886
				// Palette information (for registration & route check) instance making
				Palette plt = createInstancePalette(station, paletteid, rHardzone.getHeight());

				//#CM44887
				// WareHouse information (WAREHOUSE) retrieval
				WareHouseHandler whhandler = new WareHouseHandler(conn);
				WareHouseSearchKey whkey = new WareHouseSearchKey();

				whkey.setStationNumber(param[0].getWareHouseNo());
				WareHouse whouse = (WareHouse) whhandler.findPrimary(whkey);

				//#CM44888
				// Transportation route check and decision processing
				if (rc.storageDeterminSCH(plt, whouse))
				{
					//#CM44889
					// When At the transportation destination is a shelf, Shelf  No. is set in the present location of the palette. 
					if (rc.getDestStation() instanceof Shelf)
					{
						plt.setCurrentStationNumber(rc.getDestStation().getStationNumber());
						locationno = rc.getDestStation().getStationNumber();
					}
				}
				else
				{
					//#CM44890
					// Messege of transportation route NG is acquired. 
					setMessage(getRouteErrorMessage(rc.getRouteStatus()));
					return false;
				}

				//#CM44891
				// When the Work display:End function is provided,  
				//#CM44892
				// Work display of Station makes Status of transportation with Drawing.
				int cmdStatus = 0;
				if (rc.getSrcStation().getOperationDisplay() == Station.OPERATIONINSTRUCTION)
				{
					cmdStatus = CarryInformation.CMDSTATUS_ALLOCATION;
				}
				//#CM44893
				// Otherwise, it becomes [Start]
				else
				{
					cmdStatus = CarryInformation.CMDSTATUS_START;
				}
				
				//#CM44894
				// Decision of data registered in Aisle Station of CarryInfo
				//#CM44895
				// If the transportation destination is a location, Aisle Station No of the location. 
				//#CM44896
				// Set the value if Aisle Station is set in Station if the transportation  
				//#CM44897
				// destination is not a location (Warehouse etc.).
				//#CM44898
				// If nothing is set (Aisle uniting Station), it is assumed null. 
				String carryAisleStno = null;
 				if (rc.getDestStation() instanceof Shelf)
				{
					carryAisleStno = rc.getDestStation().getParentStationNumber();
				}
				else 
				{
					if (rc.getSrcStation().getAisleStationNumber() != null)
					{
						carryAisleStno = rc.getSrcStation().getAisleStationNumber();
					}
				}
	
				//#CM44899
				// Registration of transportation information table (CARRYINFO) 
				//#CM44900
				// Make only one for one setting. 
				if (!createCarryinfo(conn,
					CarryInformation.WORKTYPE_NOPLAN_STORAGE,
					schno,
					locationno,
					workno,
					CarryInformation.RETRIEVALDETAIL_UNKNOWN,
					rc.getSrcStation().getStationNumber(),
					rc.getDestStation().getStationNumber(),
					paletteid,
					cmdStatus,
					CarryInformation.PRIORITY_EMERGENCY,
					CarryInformation.CARRYKIND_STORAGE,
					carrykey,
					carryAisleStno))
				{
					return false;
				}

				//#CM44901
				// Registration of Work display table (OPERATIONDISPLAY)
				//#CM44902
				// Make only one for one setting. 
				//#CM44903
				// Specified Station makes it only when there is Work display. 
				if (rc.getSrcStation().getOperationDisplay() != Station.NOT_OPERATIONDISPLAY)
				{
					//#CM44904
					// Registration of Work display table (OPERATIONDISPLAY)
					if(!createOperationDisplay(conn,
						carrykey,
						rc.getSrcStation().getStationNumber()))
					{
						return false;
					}
				}
				
				//#CM44905
				// Work data and others making processing(Grid number make it.) 
				for (int i = 0; i < param.length; i++)
				{
					//#CM44906
					// Stock ID
					String stockid = sequence.nextStockId();
					//#CM44907
					// Work No.
					String jobno = sequence.nextJobNo();

					//#CM44908
					// Acquisition item edit
					stock.setConsignorCode(param[i].getConsignorCode());
					stock.setConsignorName(param[i].getConsignorName());

					//#CM44909
					// Storage qty
					int inputqty =
						param[i].getStorageCaseQty() * param[i].getEnteringQty()
							+ param[i].getStoragePieceQty();
					
					//#CM44910
					// Work amount Overflow check
					workqty = addWorkQty(workqty, inputqty);

					//#CM44911
					// Registration of work information table (DNWORKINFO) 
					if (!createWorkinfo(conn,
						param[i],
						stock,
						stockid,
						workercode,
						workerName,
						terminalno,
						jobtype,
						CLASS_STORAGE,
						batch_seqno,
						0,
						inputqty,
						locationno,
						jobno,
						customercode,
						customername,
						carrykey,
						station.getWHStationNumber(),
						"",
						getWorkDate(conn),
						new Date()))
					{
						return false;
					}

					//#CM44912
					// Register Inventory information. 
					createStock(conn, 
							 param[i], 
							 inputqty, 
							 stockid, 
							 locationno,
							 0,
							 stockid,
							 "",
						     CLASS_STORAGE,
							 station.getWHStationNumber());

					//#CM44913
					// When Palette Status edit = The input product number is an empty palette product number.
					if (param[i].getItemCode().equals(AsrsParam.EMPTYPB_ITEMKEY))
					{
						//#CM44914
						// Empty palette only when input Stock qty is one
						//#CM44915
						// It becomes like Usual palette to locate it as a steps volume in case of two or more. 
						if(inputqty == 1)
						{
							//#CM44916
							// Set Status to an empty palette. 
							plt.setEmpty(Palette.STATUS_EMPTY);
						}
					}
				}
				
				//#CM44917
				// Registration of palette information table (PALETTE)
				PaletteHandler paletteHandler = new PaletteHandler(conn);
				//#CM44918
				// Registration of data
				paletteHandler.create(plt);

				//#CM44919
				// Making of No plan storageList file
				if(param[0].getListFlg() == true)
				{
					if(startPrint(conn, batch_seqno, rc.getSrcStation().getStationNumber()))
					{
						//#CM44920
						// 6021012 = The print ended normally after it had set it. 
						wMessage = "6021012";
					}
					else
					{
						//#CM44921
						// 6007042=After submitting, It failed in the print. Refer to the log. 
						wMessage = "6007042";
					}
				}
				else
				{
					//#CM44922
					// 6001006 = Submitted.
					wMessage = "6001006";
				}

				return true;

			}
			return false;
		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//#CM44923
	/**
	 * Pass Batch  No. to No plan storageListPrint processing Class. <BR>
	 * Do not do the print processing when there is no print data. <BR>
	 * Return True from No plan storagePrint processing Class when succeeding in print, False when failing. <BR>
	 * It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
	 * @param  conn Connection Connection object with database
	 * @param  batchno Batch No.
	 * @param  stNo Storage Station No.
	 * @return True when processing is normal, False when the schedule processing fails.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean startPrint(Connection conn, String batchno, String stNo) throws ReadWriteException, ScheduleException
	{
		AsNoPlanStorageWriter writer = new AsNoPlanStorageWriter(conn);
		writer.setBatchNumber(batchno);
		writer.setStorageStationNo(stNo);

		//#CM44924
		// Begin the printing job. 
		if (writer.startPrint())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
//#CM44925
//end of class
