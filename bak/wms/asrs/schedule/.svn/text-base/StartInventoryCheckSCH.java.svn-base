// $Id: StartInventoryCheckSCH.java,v 1.2 2006/10/30 00:42:03 suresh Exp $

//#CM46219
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule ;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.dbhandler.ASStockReportFinder;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.report.StartInventoryCheckWriter;
import jp.co.daifuku.wms.asrs.dbhandler.ASWorkPlaceHandler;
import jp.co.daifuku.wms.asrs.location.WorkPlace;
import jp.co.daifuku.wms.asrs.location.AisleOperator;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.dbhandler.AisleAlterKey;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM46220
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda  <BR>
 * <BR>
 * Class to do WEBStock confirmationStart setting Processing.  <BR>
 * Receive the content input from the screen as parameter, and do Stock confirmationStart setting Processing.  <BR>
 * Do not do Comment-rollback of the transaction though each Method of this Class must use the Connection object and<BR>
 * do the update processing of the receipt data base.   <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Initial display processing(< CODE>initFind() </ CODE > method) <BR><BR><DIR>
 *   Return corresponding Consignor Code when only one Consignor Code exists in the inventory information.  <BR>
 *   Return null when pertinent data does not exist or it exists by two or more.  <BR>
 * <BR>
 *   <Search condition> <BR><DIR>
 *     Stock status:Center Stocking(2) <BR>
 *     Stock qty : More than 1 </DIR></DIR>
 * <BR>
 * 2.Processing when Submit button is pressed.(<CODE>startSCHgetParams()</CODE>Method) <BR><BR><DIR>
 *   Receive the content displayed in the filtering area as parameter, and do Stock confirmationStart setting Processing.  <BR>
 *   Filtered and acquired from the data base again and return data for the area output when processing normally Ends. <BR>
 *   Return null when the schedule does not End because of the condition error etc.<BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker code* <BR>
 *     Password* <BR>
 *     WarehouseFlag* <BR>
 *     Workshop* <BR>
 *     Station No* <BR>
 *     Consignor Code* <BR>
 *     Beginning shelfNo <BR>
 *     End shelfNo <BR>
 *     Start Item Code <BR>
 *     End Item Code <BR></DIR>
 * <BR>
 *   <return data> <BR><DIR>
 *     true or false <BR></DIR>
 * <BR>
 *   <Stock confirmationStart Processing> <BR>
 * <DIR>
 *   <Processing condition check> <BR>
 *     -The setting is enabled.  <BR>
 * <BR>
 *   <StartProcessing> <BR><DIR>
 *     1.To prevent multi Drawing by other schedules while scheduling it before schedule Drawing <BR>
 *       The Confirming Stock flag of Aisle information is renewed to Confirming Stock, and commit does the transaction.  <BR>
 *     2.Register Stock confirmation information (<CODE>inventorycheck</CODE>).  Do CommentProcessing after Processing. <BR><DIR>
 *       -Schedule No:Schedule No numbered <BR>
 *       -Update date:System date <BR>
 *       -Submit type :Stock confirmation(1) <BR>
 *       -Warehouse Station No:Station No to which screen is input <BR>
 *       -Beginning shelfNo:Beginning shelf No to which screen is input <BR>
 *       -End shelfNo:End shelf No to which screen is input <BR>
 *       -Start Item Code:Start Item Code to which screen is input <BR>
 *       -End Item Code:End Item Code to which screen is input <BR></DIR>
 *     3.Do Drawing of the possible Stock confirmation stock from the range of specified Shelf No., Item code, and Lot No. <BR><DIR>
 *       -To the following Palette for the shelf which cannot be accessed and the shelf which cannot be used <BR>
 *       -Do not draw it when the Abnormal shelf is drawn.  <BR>
 *       -The traveling route is checked and decided.  <BR>
 *       -Unit Delivery exclusive use Station is off the subject when the transportation destination is Workshop, and OK of the transportation destination is Confirming Stock.  <BR>
 *       -Make Work information(<CODE>dnworkinfo</CODE>) based on Inventory information(<CODE>dmstock</CODE>).  <BR>
 *       -Make transportation information (<CODE>carryinfo</CODE>).  <BR><DIR>
 *         -The delivery instruction is detailed. :Stock confirmation <BR></DIR>
 *       -Renew Stock Status of Palette information (<CODE>palette</CODE>) in the Picking reservation and renew Drawing qty to Drawing settlement respectively.  <BR></DIR></DIR></DIR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/06</TD><TD>K.Toda</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 00:42:03 $
 * @author  $Author: suresh $
 */

public class StartInventoryCheckSCH extends AbstractAsrsControlSCH
{
	//#CM46221
	// Class fields --------------------------------------------------
	//#CM46222
	/**
	 * Palette information operation Class
	 */
	protected PaletteHandler wPltHandler = null;
	//#CM46223
	/**
	 * Palette information Update key
	 */
	protected PaletteAlterKey wPltAltKey = null;
	//#CM46224
	/**
	 * Palette information Operation key
	 */
	protected PaletteSearchKey wPltKey = null;
	//#CM46225
	/**
	 * Work information operation Class
	 */
	protected WorkingInformationHandler wWorkHandler = null;
	//#CM46226
	/**
	 * Transportation data operation Class
	 */
	protected CarryInformationHandler wCarryHandler = null;
	

	//#CM46227
	// Class variables -----------------------------------------------
	//#CM46228
	/**
	 * Class Name(Stock confirmation setting)
	 */
	public static String CLASS_INVENTORYCHECK = "StartInventoryCheckSCH";

	//#CM46229
	// Class method --------------------------------------------------
	//#CM46230
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 00:42:03 $");
	}

	//#CM46231
	// Constructors --------------------------------------------------
	//#CM46232
	/**
	 * Initialize this Class. 
	 */
	public StartInventoryCheckSCH()
	{
		wMessage = null;
	}

	//#CM46233
	/**
	 * Method corresponding to the operation to acquire necessary data when initial is displayed the screen. <BR>
	 * Return null when pertinent data does not exist or it exists by two or more.  <BR>
	 * Set null in < CODE>searchParam</CODE > because it does not need the search condition. 
	 * @param conn Connection object with database
	 * @param searchParam Class which succeeds to < CODE>Parameter</CODE> Class with Search condition
	 * @return Class which mounts < CODE>Parameter</CODE > interface where retrieval result is included
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey searchKey = new StockSearchKey();

		//#CM46234
		// Data retrieval
		//#CM46235
		// Stock status(Center Stocking)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM46236
		// Stock qty must be one or more. 
		searchKey.setStockQty(1, ">=");

		//#CM46237
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

	//#CM46238
	/**
	 * Receive the content input from the screen as parameter, and begin the No plan storage setting schedule. <BR>
	 * Receive parameter by the array because the input of two or more of set of straightening data is assumed. <BR>
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
		int mk_count = 0;

		ASStockReportFinder stkReportFinder = new ASStockReportFinder(conn);
		
		try
		{
			//#CM46239
			// Check on Worker code and Password
			AsScheduleParameter workparam = (AsScheduleParameter) startParams[0];
			if (!checkWorker(conn, workparam))
			{
				return false;
			}

			//#CM46240
			// Next day update Processing check
			if (isDailyUpdate(conn))
			{
				return false;
			}

			//#CM46241
			// Definition for route check
			RouteController rc = new RouteController(conn, false);
			//#CM46242
			// Acquire each registered one mind key. 
			SequenceHandler sequence = new SequenceHandler(conn);

			//#CM46243
			// Worker information acquisition
			//#CM46244
			// Worker code
			String workercode = workparam.getWorkerCode();
			//#CM46245
			// Worker Name
			String workerName = getWorkerName(conn, workercode);

			//#CM46246
			// WarehouseNo
			String whno = workparam.getWareHouseNo();
			//#CM46247
			// During the acquisition of transportation destination Station
			String deststation = workparam.getToStationNo();

			//#CM46248
			// Consignor Code
			String consignorCode = workparam.getConsignorCode();
			//#CM46249
			// Beginning shelfNo
			String fromLocationNo = workparam.getFromLocationNo();
			//#CM46250
			// End shelfNo
			String toLocationNo = workparam.getToLocationNo();
			//#CM46251
			// Start Item Code
			String startItemCode = workparam.getStartItemCode();
			//#CM46252
			// End Item Code
			String endItemCode = workparam.getEndItemCode();

			//#CM46253
			// Check Status of Station(Workshop).
			if( checkStation(conn, deststation) == false )
			{
				//#CM46254
				// Specified Station or Workshop is not Status of possible Stock confirmation. 
				return false;
			}

			//#CM46255
			// Check whether insertion Picking work data other than Confirming Stock exist within the specified range. 
			if( !checkAisle(conn, whno, deststation, fromLocationNo, toLocationNo) )
			{
				return false;
			}

			//#CM46256
			// To prevent multi Drawing by other schedules while scheduling it before schedule Drawing
			//#CM46257
			// The Confirming Stock flag is renewed, and Comment does the transaction. 
			String[] aisles =  getAisleArray(conn, whno, deststation, fromLocationNo, toLocationNo);
			String[] modifyAisle = modifyInventoryCheckFlag(conn, aisles);
			if( modifyAisle == null )
			{
				//#CM46258
				// 6023454 = It is not possible to set it because it works within the specified range other than Stock confirmation. 
				wMessage = "6023454";
				return false ;
			}
			conn.commit();

			//#CM46259
			// Set the key to acquire the stock which corresponds to the condition of Stock confirmation. 
			SearchKey[] sKey = setAllocationKey(conn, fromLocationNo, toLocationNo, consignorCode
												, startItemCode, endItemCode, deststation);

			//#CM46260
			// Acquisition of Inventory information(Stock) of Stock confirmation
			//#CM46261
			// Cursor opening
			int count = stkReportFinder.searchStock((SearchKey[])sKey);
			//#CM46262
			// Object data none
			if (count <= 0)
			{
				//#CM46263
				// 6023455 = The possible Stock confirmation stock does not exist within the specified range. 
				wMessage = "6023455";
				return false ;
			}

			//#CM46264
			// Comparison key to making transportation data for unit of Palette ID
			int w_svPltId = -99;
			String carrykey_seq = "";

			//#CM46265
			// Memorize the status of check NG on the route of Processing. 
			boolean routeError = false;
			int routeErrorStatus = 0;

			//#CM46266
			// Generate each handler.
			createPaletteHandlerObject(conn);
			createCinfoHandlerObject(conn);
			createWinfoHandlerObject(conn);
			ASWorkPlaceHandler wpHandle = new ASWorkPlaceHandler(conn);
			StationHandler stHandle = new StationHandler(conn);
			StationSearchKey stKey = new StationSearchKey();

			//#CM46267
			// Schedule No numbering
			String schno_seq = sequence.nextScheduleNumber() ;
			//#CM46268
			// Batch No.(One setting commonness)
			String batch_seq = sequence.nextNoPlanBatchNo();			

			//#CM46269
			// 	Do data Processing 100 until the retrieval result is lost. 
			Stock stock[] = null ;
			while (stkReportFinder.isNext())
			{
				//#CM46270
				// It acquires it from the retrieval result to 100. 
				stock = (Stock[]) stkReportFinder.getEntities(100);
				
				if(mk_count == 0)
				{
					//#CM46271
					// Stock confirmation schedule Processing Start
					//#CM46272
					// Stock confirmation information Registration processing
					if (!createInventoryCheck(conn, consignorCode, schno_seq, whno
													, fromLocationNo, toLocationNo
													, startItemCode, endItemCode, deststation))
					{
						return false;
					}
				}
				
				//#CM46273
				// Repeat Processing only by the range stock. 
				for (int i = 0; i < stock.length; i++)
				{
					if (w_svPltId != stock[i].getPaletteid())
					{
						//#CM46274
						// Transportation key
						carrykey_seq = sequence.nextCarryKey() ;
						//#CM46275
						// Work No.
						String workjobno_seq = sequence.nextWorkNumber();
	
						//#CM46276
						// Palette information (PALETTE) retrieval
						wPltKey.KeyClear();
						wPltKey.setPaletteId(stock[i].getPaletteid());
						Palette plt = (Palette) wPltHandler.findPrimary(wPltKey);
						String currentstation = plt.getCurrentStationNumber();
	
						//#CM46277
						// Route check processing
						boolean routFlag = false;
	
						stKey.KeyClear();
						stKey.setStationNumber(deststation);
						Station station = (Station) stHandle.findPrimary(stKey);
						//#CM46278
						// Is Station Workshop?
						if( station.getWorkPlaceType() == Station.NOT_WORKPLACE )
						{
							routFlag = rc.retrievalDetermin(plt, station, true, true, false, true);
						}
						else
						{
							//#CM46279
							// Generate the Workshop instance again when Workshop is specified. 
							stKey.KeyClear();
							stKey.setStationNumber(station.getStationNumber());
							WorkPlace[] wp = (WorkPlace[]) wpHandle.find(stKey);
							routFlag = rc.retrievalDetermin(plt, wp[0], true, true, false, true);
						}
	
						//#CM46280
						// Processed by the route check in abnormal circumstances as follows. 
						if(!routFlag)
						{
							//#CM46281
							// Record information on the transportation route error. 
							routeError = true;
							routeErrorStatus = rc.getRouteStatus();
	
							//#CM46282
							//<en> Traveling route NG does Processing of next information without doing to make an error. </en>
							continue;
						}
					
						//#CM46283
						// Renewal of palette table (PALETTE)
						if (!updatePalette(stock[i].getPaletteid()))
						{
							return false;
						}
				
						//#CM46284
						// Aisle Station No.
						//#CM46285
						// Retrieve the shelf table by Shelf  No.
						ShelfHandler wShelfHandler = new ShelfHandler(conn);
						ShelfSearchKey wShelfKey = new ShelfSearchKey();
					
						wShelfKey.KeyClear();
						wShelfKey.setStationNumber(stock[i].getLocationNo());
				
						Shelf rShelf = (Shelf)wShelfHandler.findPrimary(wShelfKey);
			
						//#CM46286
						// Registration of transportation table (CARRYINFO)
						if (!createCarryinfo(
							stock[i],
							CarryInformation.WORKTYPE_INVENTORYCHECK ,
							workjobno_seq,
							currentstation,
							rc.getDestStation().getStationNumber(),
							carrykey_seq,
							schno_seq,
							rShelf.getParentStationNumber()))
						{
							return false;
						}
						w_svPltId = stock[i].getPaletteid();					
					}
					
					//#CM46287
					// Work No.
					String jobno_seq = sequence.nextJobNo();
	
					//#CM46288
					// Registration of work information table (DNWORKINFO)
					if (!createWorkinfo(
						conn,
						workparam,
						stock[i],
						WorkingInformation.JOB_TYPE_ASRS_INVENTORY_CHECK,
						batch_seq,
						jobno_seq,
						carrykey_seq,
						whno,
						workerName,
						CLASS_INVENTORYCHECK))
					{
						return false;
					}
					mk_count++;
				}
			}

			if (mk_count == 0)
			{
				if( routeError )
				{
					wMessage = getRouteErrorMessage(routeErrorStatus);
				}
				else
				{
					//#CM46289
					// 6023455=The possible Stock confirmation stock does not exist within the specified range. 
					wMessage = "6023455";
				}
				return false ;
			}
			
			//#CM46290
			// Making of Stock confirmation work List file
			if(workparam.getListFlg() == true)
			{
				if(startPrint(conn, batch_seq, deststation))
				{
					//#CM46291
					// 6021012 = The print ended normally after it had set it. 
					wMessage = "6021012";
				}
				else
				{
					//#CM46292
					// 6007042 = After submitting, It failed in the print. Refer to the log. 
					wMessage = "6007042";
				}
			}
			else
			{
				//#CM46293
				// 6001006 = Submitted.
				wMessage = "6001006";
			}
			return true;

		}
		catch (SQLException e)
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
		catch (Exception e)
		{
			throw new ScheduleException(e.getMessage());
		}
		finally
		{
			try
			{
				//#CM46294
				// Do the close processing of the opening data base cursor. 
				stkReportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM46295
				// The data base error occurred. Refer to the log. 
				setMessage("6007002");
				return false;			
			}
		}
	}

	//#CM46296
	/**
	 * In this schedule, restore following information which did Update process Processing. <BR>
	 * Reset of Confirming Stock flag of Aisle information table. <BR>
	 * @param conn Instance to maintain connection with data base. 
	 * @param startParams Array of instance of < CODE>AsScheduleParameter</CODE>Class with set content. 
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public boolean nextCheck(Connection conn, Parameter startParams)
		throws ReadWriteException, ScheduleException
	{
		CarryInformationHandler cryHandle = new CarryInformationHandler(conn);
		CarryInformationSearchKey cryKey = new CarryInformationSearchKey();

		AsScheduleParameter workparam = (AsScheduleParameter) startParams;

		//#CM46297
		// WarehouseNo
		String whno = workparam.getWareHouseNo();
		//#CM46298
		// During the acquisition of transportation destination Station
		String deststation = workparam.getToStationNo();
		//#CM46299
		// Beginning shelfNo
		String frloc = workparam.getFromLocationNo();
		//#CM46300
		// End shelfNo
		String toloc = workparam.getToLocationNo();
		String[] checkAisle = getAisleArray(conn, whno, deststation, frloc, toloc);

		for (int i = 0 ; i < checkAisle.length; i++)
		{
			//#CM46301
			// Check the presence of the Stock confirmation work from the transportation data by correspondence Aisle Station. 
			cryKey.KeyClear();
			cryKey.setAisleStationNumber(checkAisle[i]);
			cryKey.setRetrievalDetail(CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK);

			int Rcount = cryHandle.count(cryKey);

			//#CM46302
			// Also when the transportation data is one, it is assumed that it makes an error. 
			if (Rcount > 0)
			{
				continue;
			}

			String[] recoverAisle = new String[1];
			recoverAisle[0] = checkAisle[i];
			//#CM46303
			// Aisle information Update process
			if (!updateAisle(conn, recoverAisle, false))	
			{
				return false;
			}
		}

		return true;
	}

	//#CM46304
	// Protected methods -----------------------------------------------	

	//#CM46305
	/**
	 * Pass Batch No. to ASRS Stock confirmation work ListPrintProcessing Class. <BR>
	 * Do not do the print processing when there is no print data. <BR>
	 * Return True from ASRS Stock confirmation work ListPrintProcessingClass when succeeding in print and False when failing. <BR>
	 * Return the result. <BR>
	 * It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
	 * @param  conn Connection Connection object with database
	 * @param  batchno Batch No.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return True when processing is normal, False when the schedule processing fails.
	 */
	protected boolean startPrint(Connection conn, String batchno, String deststation) throws ReadWriteException, ScheduleException
	{
		StartInventoryCheckWriter writer = new StartInventoryCheckWriter(conn);
		writer.setBatchNumber(batchno);
		writer.setStationNumber(deststation);

		//#CM46306
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

	//#CM46307
	/**
	 * Generate Palette operation Class. 
	 * Generation Class is as follows. 
	 * Handler
	 * Operation key
	 * Update key
	 * @param  conn Connection Connection object with database
	 */
	protected void createPaletteHandlerObject (Connection conn)
	{
		wPltHandler = new PaletteHandler(conn);
		wPltAltKey = new PaletteAlterKey();
		wPltKey = new PaletteSearchKey();
	}
	
	//#CM46308
	/**
	 * Generate Work information operation Class. 
	 * Generation Class is as follows. 
	 * Handler
	 * @param  conn Connection Connection object with database
	 */
	protected void createWinfoHandlerObject (Connection conn)
	{
		wWorkHandler = new WorkingInformationHandler(conn);
	}
	
	//#CM46309
	/**
	 * Generate Transportation data operation Class. 
	 * Generation Class is as follows. 
	 * Handler
	 * @param  conn Connection Connection object with database
	 */
	protected void createCinfoHandlerObject (Connection conn)
	{
		wCarryHandler = new CarryInformationHandler(conn);
	}
	
	//#CM46310
	// Private methods -----------------------------------------------
	//#CM46311
	/**
	 * Renew the palette information table. <BR>
	 * @param paletteid Palette ID
	 * @return Return True When Update process normally Ends and False when failing.  .
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.<BR>
	 * @throws ScheduleException It is notified when this Method is called. 
	 */
	private boolean updatePalette(int paletteid) throws ReadWriteException, ScheduleException
	{
		try
		{
			wPltKey.KeyClear();
			wPltKey.setPaletteId(paletteid);
			Palette plt = (Palette) wPltHandler.findPrimary(wPltKey);
			
			//#CM46312
			// Update it when Palette is only Real shelf. 
			if (plt.getStatus() == Palette.REGULAR)
			{
				wPltAltKey.KeyClear();
				//#CM46313
				// Stock ID is set. 
				wPltAltKey.setPaletteId(paletteid);
				//#CM46314
				// Set stock Status : Retrieval reservation.			
				wPltAltKey.updateStatus(Palette.RETRIEVAL_PLAN);
				//#CM46315
				// Set Drawing qty: Drawing settlement. 
				wPltAltKey.updateAllocation(Palette.ALLOCATED);
				//#CM46316
				// Update of data
				wPltHandler.modify(wPltAltKey);

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
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM46317
	/**
	 * Registration of transportation information table (CARRYINFO).  <BR> 
	 * <BR>     
	 * Register transportation information based on the content of received parameter.  <BR>
	 * Work information Registration processing for Stock confirmation Processing. <BR>
	 * <BR>
	 * @param stock	   Stock information instance
	 * @param worktype    Work Flag(Work type)
	 * @param jobno       Work No.
	 * @param currentstation Transportation originStation No.
	 * @param deststation At the transportation destinationStation No.
	 * @param carrykey    Transportation key
	 * @param schno	   Schedule No
	 * @param ailestno	   Aisle Station No..
	 * @return Return True When Registration processing normally Ends and False when failing. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private boolean createCarryinfo(
		Stock stock,
		String worktype,
		String jobno,
		String currentstation,
		String deststation,
		String carrykey,
		String schno,
		String ailestno)
		throws ReadWriteException
	{
		try
		{
			CarryInformation carryInfo = new CarryInformation();

			//#CM46318
			// Transportation Key
			carryInfo.setCarryKey(carrykey);
			//#CM46319
			// Palette ID
			carryInfo.setPaletteId(stock.getPaletteid());
			//#CM46320
			// Update date
			carryInfo.setCreateDate(new Date());
			//#CM46321
			// Work type
			carryInfo.setWorkType(worktype);
			//#CM46322
			// Retrieval group No.
			carryInfo.setGroupNumber(000);
			//#CM46323
			// Status of transportation(Start)
			carryInfo.setCmdStatus(CarryInformation.CMDSTATUS_START);
			//#CM46324
			// Priority Flag(usually)
			carryInfo.setPriority(CarryInformation.PRIORITY_NORMAL);
			//#CM46325
			// Restorage flag(Re-Storage)
			carryInfo.setReStoringFlag(CarryInformation.RESTORING_SAME_LOC);
			//#CM46326
			// Transportation Flag(Picking)
			carryInfo.setCarryKind(CarryInformation.CARRYKIND_RETRIEVAL);
			//#CM46327
			// Retrieval Location No. 
			carryInfo.setRetrievalStationNumber(stock.getLocationNo());
			//#CM46328
			// The delivery instruction is detailed. (Stock confirmation)
			carryInfo.setRetrievalDetail(CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK);
			//#CM46329
			// Work No.
			carryInfo.setWorkNumber(jobno);
			//#CM46330
			// Transportation originStation No.
			carryInfo.setSourceStationNumber(currentstation);
			//#CM46331
			// At the transportation destinationStation No.
			carryInfo.setDestStationNumber(deststation);
			//#CM46332
			// Arrival date
			carryInfo.setArrivalDate(null);
			//#CM46333
			// Control information
			carryInfo.setControlInfo("");
			//#CM46334
			// Cancellation demand Flag
			carryInfo.setCancelRequest(CarryInformation.CANCELREQUEST_UNDEMAND);
			//#CM46335
			// Cancellation demand date
			carryInfo.setCancelRequestDate(null);
			//#CM46336
			// Schedule No. 
			carryInfo.setScheduleNumber(schno);
			//#CM46337
			// Aisle Station No..
			carryInfo.setAisleStationNumber(ailestno);
			//#CM46338
			// Final Station No. 
			carryInfo.setEndStationNumber(deststation);
			//#CM46339
			// Abnormal code
			carryInfo.setErrorCode(0);
			//#CM46340
			// Maintenance Terminal No.
			carryInfo.setMaintenanceTerminal("");

			//#CM46341
			// Registration of transportation information
			wCarryHandler.create(carryInfo);
			
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

	//#CM46342
	/**
	 * Registration of work information table (DNWORKINFO). <BR> 
	 * <BR>     
	 * Register work information based on the content of received parameter.  <BR>
	 * Work information Registration processing for Stock confirmation Processing. <BR>
	 * <BR>
	 * @param conn        Instance to maintain connection with data base. 
	 * @param workparam   Instance of AsScheduleParameter class with content input from screen. 
	 * @param stock	      Stock information instance
	 * @param jobtype     Work Flag
	 * @param batchno     Batch No.
	 * @param jobno       Work No.
	 * @param carrykey    Transportation key
	 * @param areano	  Area No.
	 * @param workername  Worker Name
	 * @param processname Processing name
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated is generated. 
	 */
	private boolean createWorkinfo(
		Connection conn, 
		AsScheduleParameter workparam,
		Stock stock,
		String jobtype,
		String batchno,
		String jobno,
		String carrykey,
		String areano,
		String workername,
		String processname)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			WorkingInformation workInfo = new WorkingInformation();

			//#CM46343
			// Work No.
			workInfo.setJobNo(jobno);
			//#CM46344
			// Work Flag
			workInfo.setJobType(jobtype);
			//#CM46345
			// Consolidating Work No.
			workInfo.setCollectJobNo(jobno);
			//#CM46346
			// Status flag:Working
			workInfo.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			//#CM46347
			// Work beginning flag:Started
			workInfo.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			//#CM46348
			// Plan unique key
			workInfo.setPlanUkey("");
			//#CM46349
			// Stock ID
			workInfo.setStockId(stock.getStockId());
			//#CM46350
			// Area No.
			workInfo.setAreaNo(areano);
			//#CM46351
			// Location No..
			workInfo.setLocationNo(stock.getLocationNo());
			//#CM46352
			// Plan Date
			workInfo.setPlanDate(getWorkDate(conn));
			//#CM46353
			// Consignor Code
			workInfo.setConsignorCode(stock.getConsignorCode());
			//#CM46354
			// Consignor Name
			workInfo.setConsignorName(stock.getConsignorName());
			//#CM46355
			// Supplier code
			workInfo.setSupplierCode(stock.getSupplierCode());
			//#CM46356
			// Supplier name
			workInfo.setSupplierName1(stock.getSupplierName1());
			//#CM46357
			// Receiving ticket No.
			workInfo.setInstockTicketNo("");
			//#CM46358
			// Receiving line No.
			workInfo.setInstockLineNo(0);
			//#CM46359
			// Shipper code 
			workInfo.setCustomerCode("");
			//#CM46360
			// Shipper name
			workInfo.setCustomerName1("");
			//#CM46361
			// Item Code
			workInfo.setItemCode(stock.getItemCode());
			//#CM46362
			// Item Name
			workInfo.setItemName1(stock.getItemName1());
			//#CM46363
			// Work plan qty (Host plan qty)
			workInfo.setHostPlanQty(0);
			//#CM46364
			// Work plan qty 
			workInfo.setPlanQty(0);
			//#CM46365
			// Work possible qty 
			workInfo.setPlanEnableQty(0);
			//#CM46366
			// Actual work qty 
			workInfo.setResultQty(0);
			//#CM46367
			// Work shortage qty 
			workInfo.setShortageCnt(0);
			//#CM46368
			// Reserved qty
			workInfo.setPendingQty(0);
			//#CM46369
			// Qty per case
			workInfo.setEnteringQty(stock.getEnteringQty());
			//#CM46370
			// Qty per bundle
			workInfo.setBundleEnteringQty(stock.getBundleEnteringQty());
			//#CM46371
			// Case/Piece flag(Mode of packing)
			workInfo.setCasePieceFlag(stock.getCasePieceFlag());
			//#CM46372
			// Case/Piece flag(Work form)
			workInfo.setWorkFormFlag(stock.getCasePieceFlag());
			//#CM46373
			// Case ITF
			workInfo.setItf(stock.getItf());
			//#CM46374
			// Bundle ITF
			workInfo.setBundleItf(stock.getBundleItf());
			//#CM46375
			// TC/DCFlag
			workInfo.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
			//#CM46376
			// Expiry date
			workInfo.setUseByDate(stock.getUseByDate());
			//#CM46377
			// Lot No.
			workInfo.setLotNo(stock.getLotNo());
			//#CM46378
			// Plan information comment
			workInfo.setPlanInformation(stock.getPlanInformation());
			//#CM46379
			// Order No.
			workInfo.setOrderNo("");
			//#CM46380
			// Order day 
			workInfo.setOrderingDate("");
			//#CM46381
			// Expiry date(Results)
			workInfo.setResultUseByDate("");
			//#CM46382
			// Lot No.(Results)
			workInfo.setResultLotNo("");
			//#CM46383
			// Work resultLocation No..
			workInfo.setResultLocationNo("");
			//#CM46384
			// Unwork report flag
			workInfo.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);
			//#CM46385
			// Batch No.
			workInfo.setBatchNo(batchno);
			//#CM46386
			// Worker code
			workInfo.setWorkerCode(workparam.getWorkerCode());
			//#CM46387
			// Worker Name
			workInfo.setWorkerName(workername);
			//#CM46388
			// Terminal No.
			workInfo.setTerminalNo(workparam.getTerminalNumber());			
			//#CM46389
			// Plan information registration date
			workInfo.setPlanRegistDate(new Date());
			//#CM46390
			// Registration Processing name
			workInfo.setRegistPname(processname);
			//#CM46391
			// Last updated date and time
			workInfo.setLastUpdateDate(new Date());
			//#CM46392
			// Last updated Processing name
			workInfo.setLastUpdatePname(processname);
			//#CM46393
			// Another system connection key
			workInfo.setSystemConnKey(carrykey);
			//#CM46394
			// Another system identification key
			workInfo.setSystemDiscKey(WorkingInformation.SYSTEM_DISC_KEY_ASRS);

			//#CM46395
			// Registration of work information
			wWorkHandler.create(workInfo);
			
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
	}
	
	//#CM46396
	/**
	 * Do Condition check of Station. <BR>
	 * @param conn 	  Connection object with database
	 * @param strStation Station No
	 * @return Return True when you can use Station and False when it is not possible to use it. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when this Method is called. 
	 */
	private boolean checkStation(Connection conn, String strStation) throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM46397
			// Station information retrieval(STATION)
			StationHandler stHandler = new StationHandler(conn);
			StationSearchKey stSearchKey = new StationSearchKey();

			//#CM46398
			// Station No
			stSearchKey.setStationNumber(strStation);
			Station station = (Station) stHandler.findPrimary(stSearchKey);

			//#CM46399
			// Is Station Workshop?
			if( station.getWorkPlaceType() != Station.NOT_WORKPLACE )
			{
				//#CM46400
				// Processing for Workshop
				ASWorkPlaceHandler wpHandle = new ASWorkPlaceHandler(conn);
				stSearchKey.KeyClear();
				stSearchKey.setStationNumber(station.getStationNumber());
				WorkPlace[] wp = (WorkPlace[]) wpHandle.find(stSearchKey);

				String[] stnums = wp[0].getWPStations();
				for( int i = 0 ; i < stnums.length ; i++ )
				{
					if( checkStation(conn, stnums[i]) )
					{
						return true;
					}
				}
				//#CM46401
				//<en> There is no Station which can be transported in Workshop now. </en>
				setMessage("6013133");
				return false;
			}
			else
			{
				//#CM46402
				// Processing when specified Station is Station
				//#CM46403
				//<en> System Status check</en>
				GroupControllerHandler wGcHandler = new GroupControllerHandler(conn);
				GroupControllerSearchKey wGcSearchKey = new GroupControllerSearchKey();
				wGcSearchKey.setControllerNumber(station.getControllerNumber());

				GroupController[] rGroupControll = (GroupController[])wGcHandler.find(wGcSearchKey);
				if (rGroupControll[0].getStatus() != GroupController.STATUS_ONLINE)
				{
					//#CM46404
					//<en> Please set the system status on-line.</en>
					setMessage("6013023");
					return false;
				}
				//#CM46405
				//<en> Check the suspention flag.</en>
				if (station.isSuspend())
				{
					//#CM46406
					//<en> Cannot set as the station is suspended.</en>
					setMessage("6013099");
					return false;
				}
				//#CM46407
				//<en> Check the station type.</en>
				if (station.isOutStation() == false)
				{
					//#CM46408
					//<en> Cannot select the stations that cannot support the retrieval works.</en>
					setMessage("6013111");
					return false;
				}
				//#CM46409
				//<en> Unacceptable if specified station is dedicated for unit retrievals.</en>
				if (station.isUnitOnly())
				{
					//#CM46410
					//<en>Please select a station that permits picking.</en>
					setMessage("6013261");
					return false;
				}
				//#CM46411
				//<en> Check if the work mode is on retrieval.</en>
				if (station.isRetrievalMode() == false)
				{
					//#CM46412
					//<en> Cannot set as the station is not on retrieval mode.</en>
					setMessage("6013112");
					return false;
				}

				return true;
			}
		}
		catch(NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM46413
	/**<en>
	 * Do Aisle within the specified range and do Change to Confirming Stock. 
	 * @param conn Connection object with database
	 * @param aisleno Array of Station No.
	 * @return Change Return the list of done Aisle Station No. <BR>
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when there is injustice in Update condition of the data base. 
	 </en>*/
	private String[] modifyInventoryCheckFlag(Connection conn, String[] aisleno) throws ReadWriteException,
																					ScheduleException
	{
		try
		{
			AisleHandler alHandle = new AisleHandler(conn);
			AisleSearchKey alKey = new AisleSearchKey();
			Vector vec = new Vector();

			for( int i = 0 ; i < aisleno.length ; i++ )
			{
				alKey.KeyClear();
				alKey.setStationNumber(aisleno[i]);
				Aisle al = (Aisle) alHandle.findPrimary(alKey);
				if( al.getInventoryCheckFlag() == Aisle.NOT_INVENTORYCHECK )
				{
					AisleOperator aop = new AisleOperator(conn, aisleno[i]);
					aop.alterInventoryCheckFlag(Aisle.INVENTORYCHECK);
				}
				else
				{
					if( al.getInventoryCheckFlag() != Aisle.INVENTORYCHECK )
					{
						return null;
					}
				}
				vec.add(aisleno[i]);
			}

			//#CM46414
			//<en> If all has been updated, it will return the altered list of aisle station.</en>
			String[] retAisles = new String[vec.size()];
			vec.copyInto(retAisles);

			return retAisles;
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch(NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM46415
	/**<en>
	 * Based on the range of specified Shelf  No., the range of Consignor Code and Item code, and Workshop
	 * Set Key to acquire the possible Stock confirmation stock. 
	 * @param conn          Connection object with database
	 * @param frloc         Beginning shelfNo
	 * @param toloc         End shelfNo
	 * @param consignorCode Consignor Code
	 * @param frItemkey     Start Item Code
	 * @param toItemkey     End Item Code
	 * @param wkst          Workshop
	 * @return Return the key to retrieve the possible Stock confirmation stock. DMSTOCK, PALETTE, and SHELF Operation key. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when Abnormality not anticipated is generated in Processing.
	*</en>*/
	private SearchKey[] setAllocationKey(Connection conn, String frloc, String toloc
													, String consignorCode
													, String frItemCode, String toItemCode
													, String wkst)
													throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM46416
			/* <en>The drawing method at each operation of Warehouse (opening and close) is Change.   </en>*/

			//#CM46417
			/* <en>When the cross is operated, empty Palette does not target Drawing.  </en>*/

			StationHandler stHandle = new StationHandler(conn);
			StationSearchKey stKey = new StationSearchKey();
			stKey.setStationNumber(wkst);
			Station stn = (Station) stHandle.findPrimary(stKey);

			//#CM46418
			// Acquire the WareHouse table from Warehouse Station No of Station and Workshop. 
			WareHouseHandler whHandle = new WareHouseHandler(conn);
			WareHouseSearchKey whKey = new WareHouseSearchKey();
			whKey.setStationNumber(stn.getWHStationNumber());
			WareHouse wh = (WareHouse) whHandle.findPrimary(whKey);


			boolean empSearch = true;
			if (wh.getEmploymentType() == WareHouse.EMPLOYMENT_OPEN)
			{
				//#CM46419
				//<en> Empty pallets are included in target search data if open operation is conducted at the warehouse. </en>
				empSearch = true;
			}
			else if (wh.getEmploymentType() == WareHouse.EMPLOYMENT_CLOSE)
			{
				//#CM46420
				//<en> Empty pallets are not searched if close operation is conducted at the warehouse.</en>
				empSearch = false;
			}

			//#CM46421
			//<en> From all ranges for Aisle uniting Station or Workshop</en>
			//#CM46422
			//<en> Retrieve the stock that Stock confirmationPicking is possible. </en>

			//#CM46423
			// Make Search condition of Inventory information(Stock). 
			StockSearchKey stkKey = new StockSearchKey();

			//#CM46424
			// Stock Status(Center Stocking)
			stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM46425
			// Stock qty is one or more. 
			stkKey.setStockQty(1, ">=");

			//#CM46426
			// Acquire only the stock of AS/RS. 
			AreaOperator areaOpe = new AreaOperator(conn);
			int[] areaType = {Area.SYSTEM_DISC_KEY_ASRS};
			stkKey.setAreaNo(areaOpe.getAreaNo(areaType));

			//#CM46427
			// Consignor Code
			if(!consignorCode.equals("") )
			{
				stkKey.setConsignorCode(consignorCode);
			}
			//#CM46428
			// Start Item Code
			if (!frItemCode.equals(""))
			{
				stkKey.setItemCode(frItemCode, ">=");
			}
			//#CM46429
			// End Item Code
			if (!toItemCode.equals(""))
			{
				stkKey.setItemCode(toItemCode, "<=");
			}

			//#CM46430
			// Make Search condition of Palette information (Palette). 
			PaletteSearchKey pltKey = new PaletteSearchKey();

			//#CM46431
			// Stock Status(Real shelf, Picking reservation, and Picking inside)
			pltKey.setStatus(Palette.REGULAR, "=", "(", "", "OR");
			pltKey.setStatus(Palette.RETRIEVAL_PLAN, "=", "", "", "OR");
			pltKey.setStatus(Palette.RETRIEVAL, "=", "", ")", "AND");
			//#CM46432
			// Is excluded empty Palette?
			if( empSearch == false )
			{
				//#CM46433
				// Empty Palette is off the subject of Stock confirmation. 
				pltKey.setEmpty(Palette.STATUS_EMPTY, "<>");
			}
			//#CM46434
			// Warehouse
			pltKey.setWHStationNumber(wh.getStationNumber());
			//#CM46435
			// Beginning shelf
			if (!frloc.equals(""))
			{
				pltKey.setCurrentStationNumber(frloc, ">=");
			}
			//#CM46436
			// End shelf
			if (!toloc.equals(""))
			{
				pltKey.setCurrentStationNumber(toloc, "<=");
			}

			//#CM46437
			// Make Search condition of shelf information (Shelf). 
			ShelfSearchKey slfKey = new ShelfSearchKey();

			//#CM46438
			// Status can be used. 
			slfKey.setStatus(Shelf.STATUS_OK);
			//#CM46439
			// Only the shelf of being possible to access it
			slfKey.setAccessNgFlag(Shelf.ACCESS_OK);

			if (!StringUtil.isBlank(stn.getAisleStationNumber()))
			{
				//#CM46440
				// The Aisle independence adds parents Station  to Search condition of shelf information. 
				slfKey.setParentStationNumber(stn.getAisleStationNumber());
			}

			//#CM46441
			// Ascending order of Shelf No
			stkKey.setLocationNoOrder(1, true);
			//#CM46442
			// Ascending order of Item Code
			stkKey.setItemCodeOrder(2, true);

			SearchKey[] searchKey = {stkKey, pltKey, slfKey};

			return searchKey;
		}
		catch(NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM46443
	/**<en>
	 * Check whether/not the inventory check work is possible acording to the specified warehouse and the range of location no.<BR>
	 * If the status of the aisles in the specified range does not match teh following, it will return true.<BR>
	 * If any of these conditions match, it returns false.<BR>
	 * <DIR>
	 *    Work data does not exist in the range of specified by location no.<BR>
	 *    RM machine in the empty location check process does no exist in the range specified by location no.<BR>
	 * </DIR>
	 * @param conn :connection object with database
	 * @param whno   :<code>WareHouse</code> insatnce
	 * @param stno   :<code>Station</code> insatnce
	 * @param frloc :start location no.
	 * @param toloc :end location no.
	 * @return Returns true if the parameter check succeeded or returns false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	private boolean checkAisle(Connection conn, String whno, String stno, String frloc, String toloc)
																throws ReadWriteException, ScheduleException
	{
		String ailes[] = getAisleArray(conn, whno, stno, frloc, toloc);
		//#CM46444
		//<en> Whether there is insertion Picking work or not?</en>
		if( !checkCarry(conn, ailes) )
		{
			//#CM46445
			//<en> Currently in storage/retrieval work is in process; Cannot set.</en>
			setMessage("6013125");
			return false;
		}

		return true;
	}

	//#CM46446
	/**<en>
	 * Search and return the array of aisle station according to the specified warehouse and the range of bank.<BR>
	 * If the frloc is space or null, they all will handle as a head bank.<BR>
	 * If toloc is space of null, it should be handled as a final bank.<BR>
	 * @param conn connection object with database
	 * @param wh :<code>WareHouse</code> instance
	 * @param st :<code>Station</code> instance
	 * @param frloc :start location
	 * @param toloc :end location
	 * @return aile[] :array of aisle station no. which exists in the specified range
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException :Notifies if specified location no. is anything other than either blank or null but invalid.<BR>
	 *                             Notifies if definition does not exist in the range specified by warehouse and location no.
	 </en>*/
	private String[] getAisleArray(Connection conn, String whno, String stno, String frloc, String toloc)
															throws ReadWriteException, ScheduleException
	{
		try
		{
			int frbank = 0;
			int tobank = 9999;

			//#CM46447
			// Retrieve Aisle of Warehouse and the range of the shelf with the route. 
			ShelfHandler slfHandle = new ShelfHandler(conn);
			ShelfSearchKey slfKey = new ShelfSearchKey();

			//#CM46448
			// Beginning shelf is specified. 
			if (StringUtil.isBlank(frloc) == false)
			{
				//#CM46449
				// Station No of Beginning shelf
				slfKey.setStationNumber(frloc);
				slfKey.setNBankCollect();
				Shelf shelf = (Shelf) slfHandle.findPrimary(slfKey);
				if (shelf != null)
				{
					frbank = shelf.getNBank();
				}
			}
			//#CM46450
			// End shelf is specified. 
			if (StringUtil.isBlank(toloc) == false)
			{
				//#CM46451
				// Station No of End shelf
				slfKey.KeyClear();
				slfKey.setStationNumber(toloc);
				slfKey.setNBankCollect();
				Shelf shelf = (Shelf) slfHandle.findPrimary(slfKey);
				if (shelf != null)
				{
					tobank = shelf.getNBank();
				}
			}
			String loc_ailes[] = AisleOperator.getAisleStationNumbers(conn, whno, frbank, tobank);

			//#CM46452
			// Retrieve Aisle of input Station with the route. 
			AisleOperator aop = new AisleOperator();
			String[] stn_aisles = aop.getAisleStationNumber(conn, stno);

			//#CM46453
			//<en> Stations to be checked as setup station are those matched the both conditions below ;</en>
			//#CM46454
			//<en> Stations with aisles that have routes from the entered station (acquired as a search result)</en>
			//#CM46455
			//<en> Stations with aisles searched by specific location range.</en>
			Vector vec = new Vector();
			for (int i = 0 ; i < stn_aisles.length ; i ++)
			{
				for (int j = 0 ; j < loc_ailes.length ; j++)
				{
					if (stn_aisles[i].equals(loc_ailes[j]))
					{
						vec.addElement(stn_aisles[i]);
					}
				}
			}
			String resultArray[] = new String[vec.size()];
			vec.copyInto(resultArray);
			return  resultArray;
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM46456
	/**
	 * Transportation information retrieval check<BR>
	 * @param conn Connection object with database
	 * @param checkAisle Aisle No Array acquired from Inventory information
	 * @return Return True when there is no transportation data and False when existing.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	private boolean checkCarry(Connection conn, String[] checkAisle)
											throws ReadWriteException, ScheduleException
	{
		CarryInformationHandler cryHandler = new CarryInformationHandler(conn);
		CarryInformationSearchKey cryKey = new CarryInformationSearchKey();

		cryKey.KeyClear();
		//#CM46457
		// Check the presence of work other than Stock confirmation from the transportation data by correspondence Aisle Station. 
		if (checkAisle != null && checkAisle.length > 0)
		{
			cryKey.setAisleStationNumber(checkAisle);
		}
		cryKey.setRetrievalDetail(CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK, "!=");

		int Rcount = cryHandler.count(cryKey);			

		//#CM46458
		// Also when the transportation data is one, it is assumed that it makes an error. 
		if (Rcount > 0)
		{
			return false;
		}

		return true;
	}

	//#CM46459
	/**
	 * Renew the Aisle information table. <BR>
	 * @param conn         Connection object with database <BR>
	 * @param ailestation  Aisle Station No..
	 * @param upFlag       Update flag true:Confirming Stock set false:Confirming Stock reset
	 * @return Return True When Update process normally Ends, False when failing.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException  It is notified when the exception not anticipated in the check processing is generated. 
	 */
	private boolean updateAisle(Connection conn, String[] ailestation, boolean upFlag)
													throws ReadWriteException, ScheduleException
	{
		try
		{
			AisleHandler aileHandler = new AisleHandler(conn);
			AisleAlterKey aileAlterKey = new AisleAlterKey();

			for (int _plc=0; _plc<ailestation.length; _plc++)
			{
				//#CM46460
				// Station No
				aileAlterKey.KeyClear();
				aileAlterKey.setStationNumber(ailestation[_plc]);
				//#CM46461
				// Renew the Confirming Stock flag. (Confirming Stock)
				if (upFlag)
				{
					aileAlterKey.updateInventoryCheckFlag(Aisle.INVENTORYCHECK);
				}
				else
				{
					aileAlterKey.updateInventoryCheckFlag(Aisle.NOT_INVENTORYCHECK);
				}
				//#CM46462
				// Update of data
				aileHandler.modify(aileAlterKey);
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
	}
}
//#CM46463
//end of class
