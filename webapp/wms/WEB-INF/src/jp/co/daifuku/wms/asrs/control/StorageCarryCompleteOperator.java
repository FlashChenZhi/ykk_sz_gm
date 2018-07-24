// $Id: StorageCarryCompleteOperator.java,v 1.2 2006/10/26 02:18:46 suresh Exp $
package jp.co.daifuku.wms.asrs.control;

//#CM33607
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.ASInventoryCheckAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ASInventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleAlterKey;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.InOutResultHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.ASInventoryCheck;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.InOutResult;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.utility.WareNaviSystemManager;

//#CM33608
/**
 * This class processes the AS/RS storage completion
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/05/02</TD><TD>Y.Okamura</TD><TD>created this class</TD></TR>
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 02:18:46 $
 * @author  $Author: suresh $
 */
public class StorageCarryCompleteOperator
{
	//#CM33609
	// Class fields --------------------------------------------------
	//#CM33610
	/**
	 * Define work that does not create a result as a <code>String</code>
	 * Set job type defined by <code>InOutResult</code> class in string array
	 * set the following work as default. if addition becomes necessary, add to the array
	 */
	private static final String[] NO_RESULT_OPERATIONS =
	{
//#CM33611
//		comment out the following if empty locaiton confirmation functionality is implemented
//#CM33612
//		 // empty location check
//#CM33613
//		 InOutResult.KIND_FREECHECK
	};
	
	//#CM33614
	/**
	 * class name
	 */
	private String CLASSNAME = "StorageCarryCompleteOperator";

	//#CM33615
	/**
	 * conveyance data operation class
	 */
	private CarryInformationHandler cih = null;
	//#CM33616
	/**
	 * conveyance data search key
	 */
	private CarryInformationSearchKey ciKey = null;
	//#CM33617
	/**
	 * conveyance data update key
	 */
	private CarryInformationAlterKey ciAKey = null;
	
	//#CM33618
	/**
	 * palette operation class
	 */
	private PaletteHandler plh = null;
	//#CM33619
	/**
	 * palette search key
	 */
	private PaletteSearchKey plKey = null;
	//#CM33620
	/**
	 * palette update key
	 */
	private PaletteAlterKey plAKey = null;
		
	//#CM33621
	/**
	 * work info operation class
	 */
	private WorkingInformationHandler wih = null;
	//#CM33622
	/**
	 * work info search key
	 */
	private WorkingInformationSearchKey wiKey = null;
	//#CM33623
	/**
	 * work info update key
	 */
	private WorkingInformationAlterKey wiAKey = null;
	
	//#CM33624
	/**
	 * stock info operation class
	 */
	private StockHandler stkh = null;
	//#CM33625
	/**
	 * stock info search key
	 */
	private StockSearchKey stkKey = null;
	//#CM33626
	/**
	 * stock info update key
	 */
	private StockAlterKey stkAKey = null;
	
	//#CM33627
	/**
	 * storage plan info operation class
	 */
	private StoragePlanHandler sph = null;
	//#CM33628
	/**
	 * storage plan info search key
	 */
	private StoragePlanSearchKey spkey = null;
	//#CM33629
	/**
	 * storage plan info update key
	 */
	private StoragePlanAlterKey spAKey = null;
	
	//#CM33630
	/**
	 * result send operation class
	 */
	private HostSendHandler hostSendh = null;
	//#CM33631
	/**
	 * result send entity
	 */
	private HostSend host = null;
	
	//#CM33632
	/**
	 * storage/picking result operation class
	 */
	private InOutResultHandler iorh = null;
	
	//#CM33633
	/**
	 * work completion type : shortage
	 */
	public static final String COMPLETE_SHORTAGE = "0";
	//#CM33634
	/**
	 * work completion type : normal
	 */
	public static final String COMPLETE_NORMAL = "1";
		
	//#CM33635
	/**
	 * delimiter
	 */
	protected static String wDelim = MessageResource.DELIM ;

	//#CM33636
	// Class method --------------------------------------------------
	//#CM33637
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 02:18:46 $") ;
	}

	//#CM33638
	// Constructors --------------------------------------------------

	//#CM33639
	// Public methods ------------------------------------------------
	//#CM33640
	/**
	 * Update stock of carry data. Update stock based on CarryInformation contents
	 * Calcualte stock qty based on Storage/Picking type of WorkInformation stored in CarryInformation
	 * if the stock qty becomes 0 after update, delete the stock
	 * when work info is not found in Stock, create a new stock using storage data
	 * After updating the WorkInformation from CarryInformation contents, If all the
	 * stock is deleted, create an empty palette data with the same palette ID
	 * If the CarryInformation is stock confirmation, if the stock data does not
	 * exist in the same aisle, modify the flag from "stock confirmation" to "standby"
	 * @param conn connection object for database connection
	 * @param ci   CarryInformation instance for stock update
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException throw exception when modify, delete data is not available
	 * @throws InvalidDefineException thrown when there is error in update condition
     * @throws InvalidStatusException throw error when the search condition is out of range
	 * @throws ScheduleException  Throw any exception occurred inside schedule process
	 */
	public void updateStock(Connection conn, CarryInformation ci)
															throws ReadWriteException,
																   NotFoundException,
																   InvalidDefineException,
																   InvalidStatusException,
																   ScheduleException
	{
		generateWorkInfo(conn);
		generatePalette(conn);
		generateStock(conn);
		
		//#CM33641
		// Fetch work info, palette info, stock info from carry info
		wiKey.KeyClear();
		wiKey.setSystemConnKey(ci.getCarryKey());
		WorkingInformation[] winfos = (WorkingInformation[]) wih.find(wiKey);
		
		Palette pl = getPalette(conn, ci.getPaletteId());
		
		//#CM33642
		// use it during stock update
		boolean necessityUpdate = true;

		//#CM33643
		// Checks the In/Out classificaiton in Carry data. If the classification is either 'storage' or
		//#CM33644
		// 'direct travel', and if following values are set in retrieval instruction detail, it determines
		//#CM33645
		// the data is the returned storage data and does not update stocks.
		//#CM33646
		// 0.inventory check
		//#CM33647
		// 1.unit retrieval
		//#CM33648
		// 3.replenishment storage
		if  ((ci.getCarryKind() == CarryInformation.CARRYKIND_STORAGE)
		  || (ci.getCarryKind() == CarryInformation.CARRYKIND_DIRECT_TRAVEL))
		{
			switch (ci.getRetrievalDetail())
			{
				case CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK:
				case CarryInformation.RETRIEVALDETAIL_UNIT:
				case CarryInformation.RETRIEVALDETAIL_PICKING:
				case CarryInformation.RETRIEVALDETAIL_ADD_STORING:
					necessityUpdate = false;
					break;
			}
		}
		
		if (necessityUpdate)
		{
			//#CM33649
			// Create result data for work where result is mandatory
			if (!isNoResultOperation(ci.getWorkType()))
			{
				insertResult(conn, ci, pl, winfos, ci.getWorkType());
			}

			//#CM33650
			// Update upon storage work info completion, create result data
			if (winfos[0].getJobType().equals(WorkingInformation.JOB_TYPE_STORAGE) 
			  || winfos[0].getJobType().equals(WorkingInformation.JOB_TYPE_EX_STORAGE))
			{
				//#CM33651
				// Drop destination station from result in case of storage
				completeWork(conn, ci.getCarryKey(), ci.getCarryKind(), ci.getDestStationNumber(), ci.getPaletteId());
			}
			//#CM33652
			// error in case of picking
			else
			{
				throw new ScheduleException();
			}
			
			//#CM33653
			// When there is no stock assigned to a Palette ID, move it to empty palette
			if (!isExistStock(conn, pl.getPaletteId()))
			{
				insertEmptyPalette(conn, pl);
			}

			//#CM33654
			// Check table related to stock confirmation, empty location confirmation and update it
			updateInventoryCheckInfo(conn, ci);
		}

		//#CM33655
		// pile up empty palette
		//#CM33656
		// if stock qty is 1 and item code is empty palette
		stkKey.KeyClear();
		stkKey.setPaletteid(pl.getPaletteId());
		stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		Stock[] stocks = (Stock[]) stkh.find(stkKey);
		if (stocks.length == 1
			&& stocks[0].getItemCode().equals(AsrsParam.EMPTYPB_ITEMKEY))
		{
			if (stocks[0].getStockQty() > 1)
			{
				//#CM33657
				// load to empty palette
				//#CM33658
				// Alters the pallet status to 'normal'.
				updatePaletteEmpty(conn, pl.getPaletteId(), Palette.NORMAL);
			}
			else
			{
				//#CM33659
				// Expose to empty palette
				//#CM33660
				// Alters the pallet status to 'empty'.
				updatePaletteEmpty(conn, pl.getPaletteId(), Palette.STATUS_EMPTY);
			}
		}
		//#CM33661
		// If stocks are mixed and loaded in the pallet,
		else if (stocks.length > 1)
		{
			for (int i = 0 ; i < stocks.length ; i ++)
			{
				//#CM33662
				// Normal stock exists besides empty palette stock
				//#CM33663
				// Delete empty palette stock
				if (stocks[i].getItemCode().equals(AsrsParam.EMPTYPB_ITEMKEY))
				{
					if (stocks[i].getStockQty() == 1)
					{
						//#CM33664
						// Delete stock
						stkKey.KeyClear();
						stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
						stkKey.setStockId(stocks[i].getStockId());
						stkh.drop(stkKey);
						
						//#CM33665
						// Eliminate palette with no stock
						if (!isExistStock(conn, stocks[i].getPaletteid()))
						{
							plKey.KeyClear();
							plKey.setPaletteId(stocks[i].getPaletteid());
							plh.drop(plKey);
						}
						else
						{
							//#CM33666
							// Alters the pallet status to 'normal'.
							updatePaletteEmpty(conn, pl.getPaletteId(), Palette.NORMAL);
						}

						break;
					}
					else
					{
						//#CM33667
						// Alters the pallet status to 'normal'.
						updatePaletteEmpty(conn, pl.getPaletteId(), Palette.NORMAL);
					}
				}
			}
		}
	}
	
	//#CM33668
	/**
	 * Set class name
	 * If this method is not used, the last updated class name will be this class
	 * @param str class name
	 */
	public void setClassName(String str)
	{
		CLASSNAME = str;
	}
	
	//#CM33669
	// Package methods -----------------------------------------------

	//#CM33670
	// Protected methods ---------------------------------------------
	//#CM33671
	/**
	 * Do stock confirmation, empty location check, update
	 * Stock confirmation, empty location confirmation if there is no work data, set aisle to "standby"
	 *If stock confirmation data, emptylocation confirmation data does not exist in the same schedule number of the target Carry data, change the stock confirmation info status
	 * @param conn database connection
	 * @param ci   Carry data
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException throw exception if the info for modification does not exist
	 */
	protected void updateInventoryCheckInfo(Connection conn, CarryInformation ci) 
																	throws NotFoundException, ReadWriteException
	{
		//#CM33672
		// Checks the inventory check data; if there is no data of inventory check jobs or data of empty location check jobs, 
		// switch the status to 'to be checked'.
		updateAisleInventoryCheckOff(conn, ci);

		//#CM33673
		// It searches in CarryInfor according to the schedule no. in Carry data as a key.
		//#CM33674
		// If the carry data for incentory check or empty location check exist in the same schedule no., 
		//#CM33675
		// it should alter the Status of InventoryCheck table of that schedule no. to 'to be processed.'
		updateASInventoryCheckStatusOff(conn, ci);
	}
	
	//#CM33676
	/**
	 * Edits the In/Out result data and registers the outcome in database.
	 * The result data will be edited based on the WorkInformation preserved in CarryInformation and CarryInformation.
	 * @param conn 	:Connection with database
	 * @param ci 		:carry data
	 * @param pl       :palette preserved in carryInformation
	 * @param winfos   :workInformation preserved in carryInformation
	 * @param worktype	:work type
	 * @throws ReadWriteException :Notifies if exception occured when processing for database.
	 */
	protected void insertResult(Connection conn, 
									CarryInformation ci, 
									Palette pl, 
									WorkingInformation[] winfos, 
									String worktype)
							throws ReadWriteException
	{
		generateStock(conn);

		String stno = "";
		String locno = "";
		switch (ci.getCarryKind())
		{
			//#CM33677
			// srtorage
			case CarryInformation.CARRYKIND_STORAGE:
				stno = ci.getSourceStationNumber();
				locno = ci.getDestStationNumber();
				break;
				
			//#CM33678
			// retrieval
			case CarryInformation.CARRYKIND_RETRIEVAL:
				stno = ci.getDestStationNumber();
				//#CM33679
				//To make the stock confirmation work location no., same as origin location no, set picking location no.
				if(ci.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK)
				{
					locno = ci.getRetrievalStationNumber();
				}
				else
				{
					locno = pl.getCurrentStationNumber();
				}
				break;
				
			//#CM33680
			// location to location move
			case CarryInformation.CARRYKIND_RACK_TO_RACK:
				stno = ci.getDestStationNumber();
				locno = pl.getCurrentStationNumber();
				break;
		}
		
		Stock stk = null;
		Date createDate = new Date();
		InOutResult ioResult = new InOutResult();
		int resultKind = -1;
		//#CM33681
		// Create work info, result data
		for (int i = 0; i < winfos.length; i++)
		{
			//#CM33682
			//Fetch corresponding stock
			stk = getStock(conn, winfos[i].getStockId());
			
			int inoutQty = 0;
			//#CM33683
			// srtorage, no plan storage
			if (winfos[i].getJobType().equals(WorkingInformation.JOB_TYPE_STORAGE)
			  || winfos[i].getJobType().equals(WorkingInformation.JOB_TYPE_EX_STORAGE))
			{
				//#CM33684
				// if the conveyance status is instructed in case of direct travel
				//#CM33685
				// Assume that stock addition is completed and create result data by subtracting
				if ((ci.getCarryKind() == CarryInformation.CARRYKIND_DIRECT_TRAVEL)
					&& (ci.getCmdStatus() == CarryInformation.CMDSTATUS_INSTRUCTION))
				{
					stno = ci.getSourceStationNumber();
					locno = ci.getDestStationNumber();
					inoutQty = 0 - winfos[i].getPlanEnableQty();
					resultKind = InOutResult.RETRIEVAL;

				}
				else
				{
					//#CM33686
					// direct travel
					if (ci.getCarryKind() == CarryInformation.CARRYKIND_DIRECT_TRAVEL)
					{
						stno = ci.getDestStationNumber();
						locno = ci.getSourceStationNumber();
					}
					inoutQty = winfos[i].getPlanEnableQty();
					resultKind = InOutResult.STORAGE;
				}
					
			}
			//#CM33687
			// retrieval, no plan retrieval, check inventory
			else if (winfos[i].getJobType().equals(WorkingInformation.JOB_TYPE_RETRIEVAL)
				  || winfos[i].getJobType().equals(WorkingInformation.JOB_TYPE_EX_RETRIEVAL)
				  || winfos[i].getJobType().equals(WorkingInformation.JOB_TYPE_ASRS_INVENTORY_CHECK))
			{
				inoutQty = 0 - winfos[i].getPlanEnableQty();
				resultKind = InOutResult.RETRIEVAL;
			}
			
			ioResult.setStoreDate(createDate);
			ioResult.setResultKind(resultKind);
			ioResult.setConsignorCode(winfos[i].getConsignorCode());
			ioResult.setConsignorName(winfos[i].getConsignorName());
			ioResult.setItemCode(winfos[i].getItemCode());
			ioResult.setLotNumber(winfos[i].getLotNo());
			ioResult.setWorkType(worktype);
			ioResult.setStationNumber(stno);
			ioResult.setWHStationNumber(pl.getWHStationNumber());
			ioResult.setAisleStationNumber(ci.getAisleStationNumber());
			ioResult.setEnteringQty(winfos[i].getEnteringQty());
			ioResult.setBundleEnteringQty(winfos[i].getBundleEnteringQty());
			ioResult.setInOutQuantity(inoutQty);
			ioResult.setWorkNumber(ci.getWorkNumber());
			ioResult.setPaletteKind(pl.getPaletteTypeId());
			ioResult.setLocationNumber(locno);
			ioResult.setScheduleNumber(ci.getScheduleNumber());
			ioResult.setPaletteId(pl.getPaletteId());
			ioResult.setCarryKey(ci.getCarryKey());
			ioResult.setItemName1(winfos[i].getItemName1());
			ioResult.setReStoring(0);
			//#CM33688
			// If the storage date has not been set, set the current date of system.
			if (StringUtil.isBlank(stk.getInstockDate()))
			{
				ioResult.setInCommingDate(new Date());
			}
			else
			{
				ioResult.setInCommingDate(stk.getInstockDate());
			}
			ioResult.setStatus(InOutResult.UNPROCESSED);
			ioResult.setOrderNumber("");
			ioResult.setLineNumber(0);
			ioResult.setReport(InOutResult.DO_REPORT);
			
			//#CM33689
			// Result of empty pallet will not be reported.
			if (winfos.length == 1)
			{
				//#CM33690
				// When storing
				if (winfos[0].getJobType().equals(WorkingInformation.JOB_TYPE_STORAGE)
				|| winfos[0].getJobType().equals(WorkingInformation.JOB_TYPE_EX_STORAGE))
				{
					if (stk.getItemCode().equals(AsrsParam.EMPTYPB_ITEMKEY)
						&& winfos[0].getPlanEnableQty() == 1)
					{
						ioResult.setReport(InOutResult.NOT_REPORT);
					}
				}
				//#CM33691
				// When retrieving
				else
				{
					if (stk.getItemCode().equals(AsrsParam.EMPTYPB_ITEMKEY)
						&& stk.getAllocationQty() == 0
						&& winfos[0].getPlanEnableQty() == 1)
					{
						ioResult.setReport(InOutResult.NOT_REPORT);
					}
				}
			}
			
			//#CM33692
			// Reuslt will not be reported in case of retrieval form error locations.
			if (winfos[i].getItemCode().equals(AsrsParam.IRREGULAR_ITEMKEY))
			{
				ioResult.setReport(InOutResult.NOT_REPORT);
			}
			
			try
			{
				generateInOutResult(conn);
				iorh.create(ioResult);
			}
			catch(DataExistsException e)
			{
				throw new ReadWriteException(e.getMessage());
			}
			
		}
	}

	//#CM33693
	/**
	 * Create stock info related to empty palette
	 * @param conn database connection
	 * @param pl  palette info
	 * @throws ReadWriteException throw any database access exception
	 * @throws InvalidDefineException Exception thrown if there is any problem during database update
     * @throws InvalidStatusException throw error when the search condition is out of range
	 */
	protected void insertEmptyPalette(Connection conn, Palette pl) throws ReadWriteException, InvalidDefineException, InvalidStatusException
	{
		generateStock(conn);
		generatePalette(conn);
		try
		{
			SequenceHandler seqHandler = new SequenceHandler(conn);
			String stockId_seq = seqHandler.nextStockId();
			//#CM33694
			// Generaete the Stock instance.
			//#CM33695
			// Registering the empty pallet data.
			Stock stk = new Stock();
			stk.setStockId(stockId_seq);
			stk.setPaletteid(pl.getPaletteId());
			stk.setAreaNo(pl.getWHStationNumber());
			stk.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			stk.setConsignorCode(WmsParam.EMPTYPB_CONSIGNORCODE);
			stk.setItemCode(AsrsParam.EMPTYPB_ITEMKEY);
			stk.setLocationNo(pl.getCurrentStationNumber());
			stk.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
			stk.setStockQty(1);
			stk.setAllocationQty(1);
			stk.setBundleEnteringQty(1);
			stk.setInstockDate(new Date());
			stk.setRegistPname(CLASSNAME);
			stk.setLastUpdatePname(CLASSNAME);
			stkh.create(stk); 
			
			//#CM33696
			// Generates the Palette instance.
			Palette plt = new Palette();
			plt.setPaletteId(pl.getPaletteId());
			plt.setPaletteTypeId(pl.getPaletteTypeId());
			plt.setCurrentStationNumber(pl.getCurrentStationNumber());
			plt.setWHStationNumber(pl.getWHStationNumber());
			plt.setAllocation(Palette.ALLOCATED);
			plt.setHeight(pl.getHeight());
			plt.setBcData(pl.getBcData());
			plt.setEmpty(Palette.STATUS_EMPTY);
			plh.create(plt);
			
		}
		catch (DataExistsException de)
		{
			//#CM33697
			// The data already exists with same pallet ID.
			throw new InvalidDefineException(de.getMessage());
		}
	}
	
	//#CM33698
	/**
	 * Call work completion process related to conveyance key<BR>
	 * Complete work info and modify stock<BR>
	 * and create result send info for the target work info, and modify plan info<BR>
	 * 
	 * @param conn           database connection
	 * @param carryKey       conveyance key
	 * @param carryKind      Carry type of Carry data
	 * @param resultLocation location no. to set in work info
	 * @param paletteId      Palette ID
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException Throw if data to modify does not exist
	 * @throws InvalidDefineException Exception thrown if there is any problem during database update
	 * @throws ScheduleException  Throw any exception occurred inside schedule process
	 */
	protected void completeWork(Connection conn, String carryKey, int carryKind, String resultLocation, int paletteId)
											throws ReadWriteException,
													NotFoundException,
													InvalidDefineException,
													ScheduleException
	{
		generateWorkInfo(conn);
		
		WorkingInformation[] winfos = null;
		wiKey.KeyClear();
		wiKey.setSystemConnKey(carryKey);
		wiKey.setPlanUkeyOrder(1, true);
		winfos = (WorkingInformation[]) wih.find(wiKey);
		WorkingInformation upedWinfo = null;
		for (int i = 0; i < winfos.length; i++)
		{
			//#CM33699
			// Complete work info and create result
			upedWinfo = completeWorkinfo(conn, winfos[i], resultLocation);
			//#CM33700
			// update stock
			updateStock(conn, upedWinfo, carryKind, paletteId);
			
			//#CM33701
			// Modify related plan info
			//#CM33702
			// After modifying work info related to same plan info, modify plan info
			if (winfos.length != 1)
			{
				if (i == 0)
				{
					continue;
				}
				else if (!winfos[i-1].getPlanUkey().equals(winfos[i].getPlanUkey()))
				{
					updatePlan(conn, winfos[i-1].getPlanUkey(), winfos[i-1].getJobType());
				}
			}
			
			if (i == (winfos.length - 1))
			{
				updatePlan(conn, winfos[i].getPlanUkey(), winfos[i].getJobType());
			}
		}
	}
	
	//#CM33703
	/**
	 * Complete work info
	 * Create result at the same time
	 * @param conn           database connection
	 * @param winfo          Work info related to carry data
	 * @param resultLocation location no. to set in work info
	 * @return Work info after modification
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException throw exception if data does not exist
	 * @throws InvalidDefineException Exception thrown if there is any problem during database update
	 */
	protected WorkingInformation completeWorkinfo(Connection conn, WorkingInformation winfo, String resultLocation)
						throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		generateWorkInfo(conn);
		generateHostSend(conn);
		
		try
		{
			//#CM33704
			// fetch work date
			WareNaviSystemManager wmsManager = new WareNaviSystemManager(conn);
			String workDate = wmsManager.getWorkDate();
		
			//#CM33705
			// Modify work info (complete process)
			wiAKey.KeyClear();
			wiAKey.setJobNo(winfo.getJobNo());
			wiAKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
			wiAKey.updateResultQty(winfo.getPlanEnableQty());
			wiAKey.updateResultUseByDate(winfo.getUseByDate());
			wiAKey.updateResultLotNo(winfo.getLotNo());
			wiAKey.updateResultLocationNo(resultLocation);
			wiAKey.updateLastUpdatePname(CLASSNAME);
			wih.modify(wiAKey);
			
			//#CM33706
			// Recreate instance to create result data
			winfo.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
			winfo.setResultQty(winfo.getPlanEnableQty());
			winfo.setResultUseByDate(winfo.getUseByDate());
			winfo.setResultLotNo(winfo.getLotNo());
			winfo.setResultLocationNo(resultLocation);
			winfo.setLastUpdatePname(CLASSNAME);
			
			//#CM33707
			// Do not create result data in case of empty palette or error location
			if ((winfo.getConsignorCode().equals(WmsParam.EMPTYPB_CONSIGNORCODE) 
			&& winfo.getItemCode().equals(AsrsParam.EMPTYPB_ITEMKEY))
			|| (winfo.getConsignorCode().equals(WmsParam.IRREGULAR_CONSIGNORCODE)
			&& winfo.getItemCode().equals(AsrsParam.IRREGULAR_ITEMKEY)))
			{
				return winfo;
			}
			
			//#CM33708
			// Create result send info
			host = new HostSend(winfo, workDate, CLASSNAME);
			hostSendh.create(host);
		}
		catch (DataExistsException e)
		{
			//#CM33709
			// Do nothing to create result info
		}
		catch (ScheduleException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

		return winfo;
	}
	
	//#CM33710
	/**
	 * Update stock related to work info
	 * 
	 * @param conn           database connection
	 * @param winfo          Work info related to carry data
	 * @param carryKind      Carry type of Carry data
	 * @param paletteId      Palette ID
	 * @throws InvalidDefineException Exception thrown if there is any problem during database update
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException If the target stock does not exist in database, throw exception during update, delete
	 * @throws ScheduleException  Throw any exception occurred inside schedule process
	 */
	protected void updateStock(Connection conn, WorkingInformation winfo, int carryKind, int paletteId)
																throws InvalidDefineException,
																		ReadWriteException,
																		NotFoundException,
																		ScheduleException
	{
		//#CM33711
		// Search stock related to work
		Stock stk = getStock(conn, winfo.getStockId());
		
		//#CM33712
		// If the work info is "storage" or carry data is "direct  transfer", add stock qty
		if (winfo.getJobType().equals(WorkingInformation.JOB_TYPE_STORAGE)
			|| winfo.getJobType().equals(WorkingInformation.JOB_TYPE_EX_STORAGE) 
			|| carryKind == CarryInformation.CARRYKIND_DIRECT_TRAVEL)
		{
			storageStock(conn, winfo, stk, paletteId);
			
		}
		//#CM33713
		// error in case of picking
		else
		{
			throw new ScheduleException();
		}
	}

	//#CM33714
	/**
	 * Call the update process for plan info based on specified plan unique key<BR>
	 * If the work detail is other than storage, process is not done in the sense that plan info does not exist<BR>
	 * Fetch work info related to unique key, if the result  is less than plan qty, partial completion.
         * if all the plan is completed, complete work<BR>
	 * 
	 * @param conn database connection
	 * @param planUkey plan unique key
	 * @param jobType work type
	 * @throws ReadWriteException Throw exception if it occurs during database access
	 */
	protected void updatePlan (Connection conn, String planUkey, String jobType) throws ReadWriteException
	{
		//#CM33715
		// If other than Storage, this process is not called since plan info does not exist
		if (jobType.equals(WorkingInformation.JOB_TYPE_STORAGE))
		{
			updateStoragePlan(conn, planUkey);
		}
		else
		{
			return;
		}
	}
	
	//#CM33716
	/**
	 * Fetch work info, result info related to unique key<BR>
	 * and return 0 if there is no work info<BR>
	 * 
	 * @param conn     database connection
	 * @param planUkey plan unique key
	 * @return result qty grouped by unique key
	 * @throws ReadWriteException Throw exception if it occurs during database access
	 */
	protected int getResultQty (Connection conn, String planUkey) throws ReadWriteException
	{
		generateWorkInfo(conn);
		
		wiKey.KeyClear();
		wiKey.setPlanUkey(planUkey);
		wiKey.setResultQtyCollect("SUM");
		wiKey.setPlanUkeyGroup(1);
		
		WorkingInformation winfo = null;

		try
		{
			winfo = (WorkingInformation) wih.findPrimary(wiKey);
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		
		//#CM33717
		// Return 0 result if there is no work info
		if (winfo == null)
		{
			return 0;
		}
		
		return winfo.getResultQty();
	}
	
	//#CM33718
	/**
	 * Fetch shortage qty from work info using plan unique key<BR>
	 * and return 0 if there is no work info<BR>
	 * 
	 * @param conn     database connection
	 * @param planUkey plan unique key
	 * @return shortage qty grouped by plan unique key
	 * @throws ReadWriteException Throw exception if it occurs during database access
	 */
	protected int getShortageQty (Connection conn, String planUkey) throws ReadWriteException
	{
		generateWorkInfo(conn);
		
		wiKey.KeyClear();
		wiKey.setPlanUkey(planUkey);
		wiKey.setShortageCntCollect("SUM");
		wiKey.setPlanUkeyGroup(1);
		
		WorkingInformation winfo = null;
		
		try
		{
			winfo = (WorkingInformation) wih.findPrimary(wiKey);
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		
		//#CM33719
		// return 0 shortage if there is no workinfo record
		if (winfo == null)
		{
			return 0;
		}
		
		return winfo.getShortageCnt();
	}
	
	
	//#CM33720
	/**
	 * Fetch result qty from work info related to storage plan info and update storage plan info<BR>
	 * If the result qty is insufficient, do partial complete. Else modify to complete<BR>
	 * To change the status to partially completed, search work info with the same plan unique key. If completed or working status data exist, do not update<BR>
	 * 
	 * @param conn     database connection
	 * @param planUkey plan unique key
	 * @throws ReadWriteException Throw exception if it occurs during database access
	 */
	protected void updateStoragePlan (Connection conn, String planUkey) throws ReadWriteException
	{
		//#CM33721
		// fetch work result qty from workinfo
		int resultQty = getResultQty(conn, planUkey);
		int shortageQty = getShortageQty(conn, planUkey);

		generateStoragePlan(conn);
		
		//#CM33722
		// fetch storage plan info
		spkey.KeyClear();
		spkey.setStoragePlanUkey(planUkey);
		StoragePlan sp = null;
		try
		{
			sp = (StoragePlan) sph.findPrimary(spkey);
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		
		//#CM33723
		// update storage plan info
		spAKey.KeyClear();
		spAKey.setStoragePlanUkey(planUkey);
		//#CM33724
		//if the result qty is lesser than plan, make it partially completed
		if ((resultQty + shortageQty) < sp.getPlanQty())
		{
			//#CM33725
			// If there is no started or working data in work info
			wiKey.KeyClear();
			wiKey.setPlanUkey(planUkey);
			String[] status = {WorkingInformation.STATUS_FLAG_NOWWORKING, WorkingInformation.STATUS_FLAG_START};
			wiKey.setStatusFlag(status);
			if (wih.count(wiKey) == 0)
			{
				spAKey.updateStatusFlag(StoragePlan.STATUS_FLAG_COMPLETE_IN_PART);
			}
		}
		else
		{
			spAKey.updateStatusFlag(StoragePlan.STATUS_FLAG_COMPLETION);
		}
		
		spAKey.updateResultQty(resultQty);
		spAKey.updateShortageCnt(shortageQty);
		spAKey.updateLastUpdatePname(CLASSNAME);
		try
		{
			sph.modify(spAKey);
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	//#CM33726
	/**
	 * Calculate stock work qty and update stock<BR>
	 *Complete work with shortage, if the stock quantity is not appropriate change status to complege<BR>
	 * Below center stock update process
	 * <DIR>
	 *   �EUpdate waiting for storage status to center stock<BR>
	 *   �EAdd stock qty<BR>
	 *   �ESubtract plan qty<BR>
	 *   �ESet current date/time if storage date is not set<BR>
	 *   �EAdd allocation qty (in case of direct transfer don't add)<BR>
	 * </DIR>
	 * 
	 * @param conn       database connection
	 * @param workQty    work qty
	 * @param stk        target stock for update
	 * @param paletteId  Palette ID
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException If the target stock does not exist in database, throw exception during update, delete
	 * @throws InvalidDefineException Exception thrown if there is any problem during database update
	 * @throws ScheduleException  Throw any exception occurred inside schedule process
	 */
	protected void storageStock(Connection conn, WorkingInformation wi, Stock stk, int paletteId)
															throws ReadWriteException,
																   NotFoundException,
																   InvalidDefineException,
																   ScheduleException
	{
		generateStock(conn);
		
		//#CM33727
		// Seek plan qty from plan stock
		int planQty = stk.getPlanQty() - (wi.getResultQty() + wi.getShortageCnt());
		if (planQty < 0)
		{
			planQty = 0;
		}
		
		//#CM33728
		// Update plan stock
		if (planQty == 0)
		{
			//#CM33729
			// If the plan qty is 0, complete plan stock
			//#CM33730
			//and, complete stock without a plan unique key (created with unplanned storage)
			//#CM33731
			// Delete since it violates the constraint
			if (!StringUtil.isBlank(stk.getPlanUkey()))
			{
				stkAKey.KeyClear();
				stkAKey.setStockId(stk.getStockId());
				stkAKey.updatePlanQty(planQty);
				stkAKey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
				stkh.modify(stkAKey);
			}
			else
			{
				stkKey.KeyClear();
				stkKey.setStockId(stk.getStockId());
				stkh.drop(stkKey);
			}
		}
		else
		{
			stkAKey.KeyClear();
			stkAKey.setStockId(stk.getStockId());
			stkAKey.updatePlanQty(planQty);
			stkh.modify(stkAKey);
		}
		
		//#CM33732
		// Update center stock
		try
		{
			try
			{
				//#CM33733
				// Register stock info
				//#CM33734
				// If stock data exist, add stock qty (product increase)
				//#CM33735
				//If stock data exist, add new stock info(new storage)
				processStockData(conn, wi, paletteId);
			}
			catch(DataExistsException e)
			{
				processStockData(conn, wi, paletteId);
			}
		}
		catch(DataExistsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//#CM33736
	/**
	 * stock info Add or update records to table
	 * @param conn       database connection
	 * @param workinfo  WorkingInformation class instance that holds work info details
	 * @param paletteId  Palette ID
	 * @return Return true if normal
	 * @throws ReadWriteException throw any abnormal database connection error
	 * @throws ScheduleException  Throw any exception occurred inside schedule process
	 * @throws DataExistsException Throw exception if the same info is already added
	 */
	protected boolean processStockData(
		Connection conn,
		WorkingInformation wi,
		int paletteId) throws ReadWriteException, ScheduleException, DataExistsException
	{
		try
		{
			//#CM33737
			// Search existing stock (lock during search)
			stkKey.KeyClear();
			stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			stkKey.setConsignorCode(wi.getConsignorCode());
			stkKey.setItemCode(wi.getItemCode());
			stkKey.setLocationNo(wi.getResultLocationNo());
			stkKey.setCasePieceFlag(wi.getCasePieceFlag());
			if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				stkKey.setUseByDate(wi.getResultUseByDate());
			}

			Stock orgStock = (Stock) stkh.findPrimaryForUpdate(stkKey);

			if(wi.getResultQty() != 0)
			{
				//#CM33738
				// If stock data does not exist(new storage)
				if (orgStock == null)
				{
					//#CM33739
					// Add to stock info table (DMSTOCK)
					createStock(conn, wi.getResultQty(), wi, paletteId);
				
				}
				//#CM33740
				// If stock data exist (product increase)
				else
				{
					//#CM33741
					// Update stock info table (DMSTOCK)
					updateStock(orgStock, wi.getResultQty(), wi);
				}
			}
			
			return true;
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM33742
	/**
	 * Register with stock info table
	 * @param  conn        instance to store database connection
	 * @param  resultqty   stock qty
	 * @param  workinfo    WorkingInformation class instance that holds work info details
	 * @param  paletteId   Palette ID
	 * @return Return true if normal
	 * @throws ReadWriteException throw any abnormal database connection error
	 * @throws DataExistsException Throw exception if the same info is already added
	 */
	protected boolean createStock(
		Connection conn,
		int resultqty,
		WorkingInformation wi,
		int paletteId) throws ReadWriteException, DataExistsException
	{
		try
		{
			Stock stk = new Stock();

			//#CM33743
			// Fetch unique key to register
			SequenceHandler sequence = new SequenceHandler(conn);
			String stockId_seqno = sequence.nextStockId();
			stk.setStockId(stockId_seqno);
			//#CM33744
			// area no.
			try
			{
				//#CM33745
				// Fetch area no. that relates to result location info
				LocateOperator lOperator = new LocateOperator(conn);
				stk.setAreaNo(lOperator.getAreaNo(wi.getResultLocationNo()));
			}
			catch (ScheduleException e)
			{
				throw new ReadWriteException(e.getMessage());
			}
			stk.setLocationNo(wi.getResultLocationNo());
			stk.setItemCode(wi.getItemCode());
			stk.setItemName1(wi.getItemName1());
			//#CM33746
			// Stock status(center stock)
			stk.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM33747
			// stock qty(storage qty)
			stk.setStockQty(resultqty);
			stk.setAllocationQty(resultqty);
			stk.setPlanQty(0);
			stk.setCasePieceFlag(wi.getWorkFormFlag());
			stk.setInstockDate(new Date());
			stk.setUseByDate(wi.getResultUseByDate());
			stk.setConsignorCode(wi.getConsignorCode());
			stk.setConsignorName(wi.getConsignorName());
			stk.setEnteringQty(wi.getEnteringQty());
			stk.setBundleEnteringQty(wi.getBundleEnteringQty());
			stk.setItf(wi.getItf());
			stk.setBundleItf(wi.getBundleItf());
			stk.setPaletteid(paletteId);
			stk.setRegistPname(CLASSNAME);
			stk.setLastUpdatePname(CLASSNAME);

			stkh.create(stk);

			return true;
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	//#CM33748
	/**
	 * Update stock info table
	 * @param  stock	Stock info of product increase
	 * @param  resultQty	storage qty
	 * @param  workinfo     WorkingInformation class instance that holds work info details
	 * @return Return true if normal
	 * @throws ReadWriteException throw any abnormal database connection error
	 * @throws ScheduleException  Throw any exception occurred inside schedule process
	 */
	protected boolean updateStock(
			Stock stock,
			int resultQty,
			WorkingInformation workinfo) throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM33749
			//Check stock qty overflow
			long totalStockQty = (long)stock.getStockQty() + (long)resultQty;
			if (totalStockQty > WmsParam.MAX_STOCK_QTY)
			{
				//#CM33750
				// 6026025=Cannot set. Stock Qty. exceeds {0}.
				RmiMsgLogClient.write("6026025" + wDelim + WmsParam.MAX_STOCK_QTY, "StorageCompleteOperator");
				throw new ScheduleException();
			}

			//#CM33751
			//Update item name, stock qty, picking possible qty, storage date/time, consignor name, packed qty/case, packed qty/bundle,
			//#CM33752
			//Case ITF, Bundle ITF, last update process name
			stkAKey.KeyClear();
			stkAKey.setStockId(stock.getStockId());
			stkAKey.updateItemName1(workinfo.getItemName1());
			stkAKey.updateStockQty((int)totalStockQty);
			stkAKey.updateAllocationQty(stock.getAllocationQty() + resultQty);
			stkAKey.updateInstockDate(new Date());
			//#CM33753
			//Update Expiry data if expiry date is not included in the stock info search condition
			if (!WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				stkAKey.updateUseByDate(workinfo.getResultUseByDate());
			}
			stkAKey.updateConsignorName(workinfo.getConsignorName());
			stkAKey.updateEnteringQty(workinfo.getEnteringQty());
			stkAKey.updateBundleEnteringQty(workinfo.getBundleEnteringQty());
			stkAKey.updateItf(workinfo.getItf());
			stkAKey.updateBundleItf(workinfo.getBundleItf());
			stkAKey.updateLastUpdatePname(CLASSNAME);
			stkh.modify(stkAKey);

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
	
	//#CM33754
	// Private methods -----------------------------------------------
	//#CM33755
	/**
	 * Examines whether/not the specified work type will not create the result data.
	 * The work which will not create the result data is defined in static variable <code>NO_RESULT_OPERATIONS</code>
	 * of this class. If the work type is registered by that variable, it returns true and false if not.
	 * @param workType work type -it needs to be the value defined by work type of <code>InOutResult</code>.
	 * @return :If specified workType will not create the resuld data, it returns true, or false if not.
	 */
	private boolean isNoResultOperation(String workType)
	{
		for (int i = 0 ; i < NO_RESULT_OPERATIONS.length ; i++)
		{
			if (workType.equals(NO_RESULT_OPERATIONS[i]))
			{
				return true;
			}
		}
		
		return false;
	}
	
	//#CM33756
	/**
	 * Search stock using palette id as key
	 * Return true if corresponding stock info does not exist
	 * Return false if corresponding stock info exist
	 * @param conn database connection
	 * @param paletteID Palette ID
	 * @return Whether stock info exist or not
	 * @throws ReadWriteException
	 */
	private boolean isExistStock(Connection conn, int paletteID) throws ReadWriteException
	{
		generateStock(conn);
		stkKey.KeyClear();
		stkKey.setPaletteid(paletteID);
		stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		if (stkh.count(stkKey) == 0)
		{
			return false;
		}
		
		return true;
	}

	//#CM33757
	/**
	 * Change the status of palette
	 * 
	 * @param conn      database connection
	 * @param paletteId Palette ID
	 * @param status    Palette status
	 * @throws ReadWriteException throw any abnormal database connection error
	 * @throws InvalidDefineException Throw exception if the update contents are not set
	 * @throws NotFoundException throw exception if the info for modification does not exist
	 */
	private void updatePaletteEmpty (Connection conn, int paletteId, int status) 
																throws ReadWriteException,
																		InvalidDefineException,
																		NotFoundException
	{
		generatePalette(conn);

		plAKey.KeyClear();
		plAKey.updateEmpty(status);
		plAKey.setPaletteId(paletteId);
		plh.modify(plAKey);
	}

	//#CM33758
	/**
	 * Change the stock confirmation flag to OFF in aisle info<BR>
	 * 
	 * If the instructed carry data is either stock confirmation or empty
	 * location confirmation, update the stock confirmation flag in aisle
	 * info to stock confirmation standby when stock confirmation or empty
         * location confirmation carry data does not exist
	 * 
	 * @param conn   connection object for database connection
	 * @param ci     Carry data
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException throw exception if the info for modification does not exist
	 */
	private void updateAisleInventoryCheckOff(Connection conn, CarryInformation ci)
																		throws  ReadWriteException,
																				NotFoundException
	{
		//#CM33759
		// If the CarryInforrmation is either inventory check data or empty location check data, count the other inventory check data. 
		if ((ci.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK)
		 || (ci.getPriority() == CarryInformation.PRIORITY_CHECK_EMPTY))
		{
			generateCarryInfo(conn);
			ciKey.KeyClear();
			ciKey.setAisleStationNumber(ci.getAisleStationNumber());
			ciKey.setCarryKind(CarryInformation.CARRYKIND_RETRIEVAL);
			ciKey.setCmdStatus(CarryInformation.CMDSTATUS_ARRIVAL, "<");
			ciKey.setRetrievalDetail(CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK, "=", "(", "", "OR");
			ciKey.setPriority(CarryInformation.PRIORITY_CHECK_EMPTY, "=", "", ")", "AND");
			
			if (cih.count(ciKey) <= 0)
			{
				//#CM33760
				// Change empty location check flag to OFF in aisle table
				updateAisleInventoryCheckFlag(conn, 
											 ci.getAisleStationNumber(), 
											 Station.NOT_INVENTORYCHECK);
			}
		}
	}
	
	//#CM33761
	/**
	 * Change the stock confirmation flag of aisle info according to instructed value<BR>
	 * 
	 * @param conn               connection object for database connection
	 * @param aileStNo           aisle station no.
	 * @param inventoryCheckFlag stock confirmation flag change value
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException throw exception if the info for modification does not exist
	 */
	private void updateAisleInventoryCheckFlag(Connection conn, String aisleStNo, int inventoryCheckFlag)
																		throws  ReadWriteException,
																				NotFoundException
	{
		try
		{
			AisleHandler aisleh = new AisleHandler(conn);
			AisleAlterKey ak = new AisleAlterKey();

			ak.setStationNumber(aisleStNo);
			ak.updateInventoryCheckFlag(inventoryCheckFlag);
			aisleh.modify(ak);
		}
		catch (InvalidDefineException ie)
		{
			throw new ReadWriteException(ie.getMessage());
		}
	}

	//#CM33762
	/**
	 * If the specified CarryInformation was the inventory check data or the empty location check carry data,
	 * check if the carry data for inventory check or empty location check exist in the same schedule no.
	 * as CarryInformation.
	 * If such data cannot be found, alter the Status of InventoryCheck table 'to be checked'.
	 * @param conn   : Connection with database
	 * @param ci     : carry data
	 * @throws ReadWriteException :Notifies if exception occured when processing for database.
	 * @throws NotFoundException  :Notifies if data to modify cannot be found.
	 */
	private void updateASInventoryCheckStatusOff(Connection conn, CarryInformation ci)
																		throws  ReadWriteException,
																				NotFoundException
	{
		//#CM33763
		// If CarryInforrmation was the data for inventory check or empty location check data, count otherinventory check data.
		if ((ci.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK)
		 || (ci.getPriority() == CarryInformation.PRIORITY_CHECK_EMPTY))
		{
			ciKey.KeyClear();
			ciKey.setScheduleNumber(ci.getScheduleNumber());
			ciKey.setCarryKind(CarryInformation.CARRYKIND_RETRIEVAL);
			ciKey.setCmdStatus(CarryInformation.CMDSTATUS_ARRIVAL, "<");
			ciKey.setRetrievalDetail(CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK, "=", "(", "", "OR");
			ciKey.setPriority(CarryInformation.PRIORITY_CHECK_EMPTY, "=", "", ")", "AND");

			if (cih.count(ciKey) == 0)
			{
				try
				{
					ASInventoryCheckHandler    invChkHandler   = new ASInventoryCheckHandler(conn);
					ASInventoryCheckAlterKey  invChkAKey   = new ASInventoryCheckAlterKey();

					invChkAKey.setScheduleNumber(ci.getScheduleNumber());
					invChkAKey.updateStatus(ASInventoryCheck.SCHOFF);
					invChkHandler.modify(invChkAKey);
				}
				catch (Exception se)
				{
					throw new ReadWriteException(se.getMessage());
				}
			}
		}
	}
	
	//#CM33764
	/**
	 * fetch Palette ID from Palette
	 * @param conn      database connection
	 * @param paletteId Palette ID
	 * @return palette instance
	 * @throws ReadWriteException throw any database access exception
	 */
	private Palette getPalette(Connection conn, int paletteId) throws ReadWriteException
	{
		generatePalette(conn);
		plKey.KeyClear();
		plKey.setPaletteId(paletteId);
		try
		{
			return (Palette) plh.findPrimary(plKey);
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	//#CM33765
	/**
	 * fetch Stock ID from stock
	 * @param conn    database connection
	 * @param stockId stock id
	 * @return stock instance
	 * @throws ReadWriteException throw any database access exception
	 */
	private Stock getStock(Connection conn, String stockId) throws ReadWriteException
	{
		generateStock(conn);
		stkKey.KeyClear();
		stkKey.setStockId(stockId);
		try
		{
			return (Stock) stkh.findPrimary(stkKey);
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	//#CM33766
	/**
	 * generate conveyance data operation class
	 * don't recreate if instance already exists
	 * @param conn database connection
	 */
	private void generateCarryInfo(Connection conn)
	{
		if (cih == null)
			cih = new CarryInformationHandler(conn);

		if (ciKey == null)
			ciKey = new CarryInformationSearchKey();

		if (ciAKey == null)
			ciAKey = new CarryInformationAlterKey();
	}

	//#CM33767
	/**
	 * generate palette operation class
	 * don't recreate if instance already exists
	 * @param conn database connection
	 */
	private void generatePalette(Connection conn)
	{
		if (plh == null)
			plh = new PaletteHandler(conn);

		if (plKey == null)
			plKey = new PaletteSearchKey();

		if (plAKey == null)
			plAKey = new PaletteAlterKey();
	}

	//#CM33768
	/**
	 * generate work info operation class
	 * don't recreate if instance already exists
	 * @param conn database connection
	 */
	private void generateWorkInfo(Connection conn)
	{
		if (wih == null)
			wih = new WorkingInformationHandler(conn);

		if (wiKey == null)
			wiKey = new WorkingInformationSearchKey();

		if (wiAKey == null)
			wiAKey = new WorkingInformationAlterKey();
	}

	//#CM33769
	/**
	 *generates stock operation class instance
	 * don't recreate if instance already exists
	 * @param conn database connection
	 */
	private void generateStock(Connection conn)
	{
		if (stkh == null)
			stkh = new StockHandler(conn);

		if (stkKey == null)
			stkKey = new StockSearchKey();

		if (stkAKey == null)
			stkAKey = new StockAlterKey();
	}
	
	//#CM33770
	/**
	 * Generate result send info class
	 * don't recreate if instance already exists
	 * @param conn database connection
	 */
	private void generateHostSend(Connection conn)
	{
		if (hostSendh == null)
			hostSendh = new HostSendHandler(conn);
	}
	
	//#CM33771
	/**
	 * Generate storage/picking result class
	 * don't recreate if instance already exists
	 * @param conn database connection
	 */
	private void generateInOutResult(Connection conn)
	{
		if (iorh == null)
			iorh = new InOutResultHandler(conn);
	}
	
	//#CM33772
	/**
	 * generate storage plan info operation class
	 * don't recreate if instance already exists
	 * @param conn database connection
	 */
	private void generateStoragePlan(Connection conn)
	{
		if (sph == null)
			sph = new StoragePlanHandler(conn);
		if (spkey == null)
			spkey = new StoragePlanSearchKey();
		if (spAKey == null)
			spAKey = new StoragePlanAlterKey();
	}
}
//#CM33773
//end of class

