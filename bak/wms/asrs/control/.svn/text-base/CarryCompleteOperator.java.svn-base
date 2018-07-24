// $Id: CarryCompleteOperator.java,v 1.2 2006/10/26 02:14:53 suresh Exp $
package jp.co.daifuku.wms.asrs.control;

//#CM32024
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.report.WorkMaintenanceWriter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.ASInventoryCheckAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ASInventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleAlterKey;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.InOutResultHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
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
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.utility.WareNaviSystemManager;

//#CM32025
/**
 * This is a utility class of shared use for the classes which processes storage and retrieval control related issues.
 * All methods are declared in static; it provides the parameter with generated database Connection
 * when calling the method which containes database processing.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/08</TD><TD>inoue</TD><TD><code>dropRetrievalAll</code>Modification was made so that the process will
 * be proceeded after clarifying whether the method owns carry data to delete or not.
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD><code>dropRetrieval</code>Corrected the problem of pallet status retaining
 * as "reserved for retrieval" while pallets are being retrieved/stored during Retrieval method.</TD></TR>
 * <TR><TD>2004/04/01</TD><TD>inoue</TD><TD>Corrected the problem of creating restorage data for the  empty location check data and the error location data</TD></TR>
 * <TR><TD>2004/05/07</TD><TD>inoue</TD><TD>Change the Retrieval Detail of CarryInfo data that is multi allocated from Picking to Unit, when CarryInfo is canceled</TD></TR>
 * <TR><TD>2005/11/08</TD><TD>Y.Okamura</TD><TD>For WareNavi</TD></TR>
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 02:14:53 $
 * @author  $Author: suresh $
 */

public class CarryCompleteOperator
{
	//#CM32026
	// Class fields --------------------------------------------------
	//#CM32027
	/**
	 * Define work that does not create a result as a <code>String</code>
	 * Set job type defined by <code>InOutResult</code> class in string array
	 * set the following work as default. if addition becomes necessary, add to the array
	 */
	private static final String[] NO_RESULT_OPERATIONS =
	{
//#CM32028
//		comment out the following if empty locaiton confirmation functionality is implemented
//#CM32029
//		// empty location check
//#CM32030
//		InOutResult.KIND_FREECHECK
	};
	
	//#CM32031
	/**
	 * class name
	 */
	private String CLASSNAME = "CarryCompleteOperator";

	//#CM32032
	/**
	 * conveyance data operation class
	 */
	private CarryInformationHandler cih = null;
	//#CM32033
	/**
	 * conveyance data search key
	 */
	private CarryInformationSearchKey ciKey = null;
	//#CM32034
	/**
	 * conveyance data update key
	 */
	private CarryInformationAlterKey ciAKey = null;
	
	//#CM32035
	/**
	 * palette operation class
	 */
	private PaletteHandler plh = null;
	//#CM32036
	/**
	 * palette search key
	 */
	private PaletteSearchKey plKey = null;
	//#CM32037
	/**
	 * palette update key
	 */
	private PaletteAlterKey plAKey = null;
		
	//#CM32038
	/**
	 * work info operation class
	 */
	private WorkingInformationHandler wih = null;
	//#CM32039
	/**
	 * work info search key
	 */
	private WorkingInformationSearchKey wiKey = null;
	//#CM32040
	/**
	 * work info update key
	 */
	private WorkingInformationAlterKey wiAKey = null;
	
	//#CM32041
	/**
	 * stock info operation class
	 */
	private StockHandler stkh = null;
	//#CM32042
	/**
	 * stock info search key
	 */
	private StockSearchKey stkKey = null;
	//#CM32043
	/**
	 * stock info update key
	 */
	private StockAlterKey stkAKey = null;
	
	//#CM32044
	/**
	 * location info operation class
	 */
	private ShelfHandler shelfHandl = null;
	//#CM32045
	/**
	 * location info search key
	 */
	private ShelfSearchKey shelfKey = null;
	//#CM32046
	/**
	 * location info update key
	 */
	private ShelfAlterKey shelfAKey = null;
	
	//#CM32047
	/**
	 * storage plan info operation class
	 */
	private StoragePlanHandler sph = null;
	//#CM32048
	/**
	 * storage plan info search key
	 */
	private StoragePlanSearchKey spkey = null;
	//#CM32049
	/**
	 * storage plan info update key
	 */
	private StoragePlanAlterKey spAKey = null;
	
	//#CM32050
	/**
	 * picking plan info operation class
	 */
	private RetrievalPlanHandler rph = null;
	//#CM32051
	/**
	 * picking plan info search key
	 */
	private RetrievalPlanSearchKey rpKey = null;
	//#CM32052
	/**
	 * picking plan info update key
	 */
	private RetrievalPlanAlterKey rpAKey = null;

	//#CM32053
	/**
	 * result send operation class
	 */
	private HostSendHandler hostSendh = null;
	//#CM32054
	/**
	 * result send entity
	 */
	private HostSend host = null;
	
	//#CM32055
	/**
	 * storage/picking result operation class
	 */
	private InOutResultHandler iorh = null;

	//#CM32056
	/**
	 * work completion type : shortage
	 */
	public static final String COMPLETE_SHORTAGE = "0";
	//#CM32057
	/**
	 * work completion type : normal
	 */
	public static final String COMPLETE_NORMAL = "1";
	//#CM32058
	/**
	 * work completion type : empty shipping, shortage completion
	 */
	public static final String COMPLETE_ERROR_SHORTAGE = "2";
		
	//#CM32059
	/**
	 * delimiter
	 */
	protected static String wDelim = MessageResource.DELIM ;

	//#CM32060
	// Class method --------------------------------------------------
	//#CM32061
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 02:14:53 $") ;
	}

	//#CM32062
	// Constructors --------------------------------------------------

	//#CM32063
	// Public methods ------------------------------------------------
	//#CM32064
	/**
	 * Update stock of unit picking in carry data
	 * Delete CarryInformation from CarryInformation, WorkInformation, Palette and Stock tables and generate result data
	 * For the work data not included in the stock, create a result with complete removal flag other than normal result
	 * Additionally if isRestoring flag is true, and if balance stock exist, create a plan for restorage
	 * @param conn connection object for database connection
	 * @param ci Carry data
	 * @param isRestoring If true, create a plan for restorage. If false, don't create any restorage plan data
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException Exception is thrown if there is no data for deletion
	 * @throws InvalidDefineException Exception thrown if there is any problem during database update
	 */
	public void unitRetrieval(Connection conn, CarryInformation ci, boolean isRestoring)
															throws ReadWriteException,
																   NotFoundException,
																   InvalidDefineException
	{
		generateCarryInfo(conn);
		generateWorkInfo(conn);
		
		//#CM32065
		// call create process when restorage plan data creation is instructed
		if (isRestoring)
		{
			insertReStoringData(conn, ci);
		}
		
		boolean isCreateResult = false;
		
		//#CM32066
		// If the current work is unit picking or picking
		//#CM32067
		// If the conveyance type is "Direct Transfer", create storage/picking result
		if ((ci.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_UNIT 
		  || ci.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_PICKING)
		  || ci.getCarryKind() == CarryInformation.CARRYKIND_DIRECT_TRAVEL)
		{
			isCreateResult = true;
		}
		
		//#CM32068
		// Set the work detail to "forced removal"
		drop (conn, ci, InOutResult.KIND_EXPENDITURE, isCreateResult, COMPLETE_NORMAL);		
	}
	
	//#CM32069
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
	 * @throws NotFoundException thrown when there is no data for update, delete
	 * @throws InvalidDefineException thrown when there is error in update condition
     * @throws InvalidStatusException throw error when the search condition is out of range
	 */
	public void updateStock(Connection conn, CarryInformation ci)
															throws ReadWriteException,
																   NotFoundException,
																   InvalidDefineException,
																   InvalidStatusException
	{
		generateWorkInfo(conn);
		generatePalette(conn);
		generateStock(conn);
		
		//#CM32070
		// Fetch work info and palette info from carry info
		wiKey.KeyClear();
		wiKey.setSystemConnKey(ci.getCarryKey());
		WorkingInformation[] winfos = (WorkingInformation[]) wih.find(wiKey);
		
		Palette pl = getPalette(conn, ci.getPaletteId());
		
		//#CM32071
		// use it during stock update
		boolean necessityUpdate = true;

		//#CM32072
		// Checks the In/Out classificaiton in Carry data. If the classification is either 'storage' or
		//#CM32073
		// 'direct travel', and if following values are set in retrieval instruction detail, it determines
		//#CM32074
		// the data is the returned storage data and does not update stocks.
		//#CM32075
		// 0.inventory check
		//#CM32076
		// 1.unit retrieval
		//#CM32077
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
			//#CM32078
			// Create result data for work where result is mandatory
			if (!isNoResultOperation(ci.getWorkType()))
			{
				insertResult(conn, ci, pl, winfos, ci.getWorkType());
			}

			//#CM32079
			// Update Storage (except product increase) upon work completion and create result 
			if ((winfos[0].getJobType().equals(WorkingInformation.JOB_TYPE_STORAGE) 
			|| winfos[0].getJobType().equals(WorkingInformation.JOB_TYPE_EX_STORAGE))
			&& (ci.getRetrievalDetail() != CarryInformation.RETRIEVALDETAIL_ADD_STORING))
			{
				//#CM32080
				// In case of storage (except product increase), drop destination station from result
				completeWork(conn, ci.getCarryKey(), ci.getCarryKind(), ci.getDestStationNumber(), COMPLETE_NORMAL);
			}
			//#CM32081
			// Update picking or storage increase work upon completion and create result
			else
			{
				//#CM32082
				// Drop conveyance origin station from result in case of picking or storage increase work
				completeWork(conn, ci.getCarryKey(), ci.getCarryKind(), ci.getSourceStationNumber(), COMPLETE_NORMAL);
			}
			
			//#CM32083
			// When there is no stock assigned to a Palette ID, move it to empty palette
			if (!isExistStock(conn, pl.getPaletteId()))
			{
				insertEmptyPalette(conn, pl);
			}

			//#CM32084
			// Check table related to stock confirmation, empty location confirmation and update it
			updateInventoryCheckInfo(conn, ci);		
		}

		//#CM32085
		// pile up empty palette
		//#CM32086
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
				//#CM32087
				// load to empty palette
				//#CM32088
				// Alters the pallet status to 'normal'.
				updatePaletteEmpty(conn, pl.getPaletteId(), Palette.NORMAL);
			}
			else
			{
				//#CM32089
				// Expose to empty palette
				//#CM32090
				// Alters the pallet status to 'empty'.
				updatePaletteEmpty(conn, pl.getPaletteId(), Palette.STATUS_EMPTY);
			}
		}
		//#CM32091
		// If stocks are mixed and loaded in the pallet,
		else if (stocks.length > 1)
		{
			for (int i = 0 ; i < stocks.length ; i ++)
			{
				//#CM32092
				// Normal stock exists besides empty palette stock
				//#CM32093
				// Delete empty palette stock
				if (stocks[i].getItemCode().equals(AsrsParam.EMPTYPB_ITEMKEY))
				{
					if (stocks[i].getStockQty() == 1)
					{
						//#CM32094
						// Delete stock
						stkKey.KeyClear();
						stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
						stkKey.setStockId(stocks[i].getStockId());
						stkh.drop(stkKey);
						
						//#CM32095
						// Eliminate palette with no stock
						if (!isExistStock(conn, stocks[i].getPaletteid()))
						{
							plKey.KeyClear();
							plKey.setPaletteId(stocks[i].getPaletteid());
							plh.drop(plKey);
						}
						else
						{
							//#CM32096
							// Alters the pallet status to 'normal'.
							updatePaletteEmpty(conn, pl.getPaletteId(), Palette.NORMAL);
						}

						break;
					}
					else
					{
						//#CM32097
						// Alters the pallet status to 'normal'.
						updatePaletteEmpty(conn, pl.getPaletteId(), Palette.NORMAL);
					}
				}
			}
		}
	}
	
	//#CM32098
	/**
	 * Carry info data delete process
	 * ci Delete WorkInformation, Palette, Stock included in CarryInformation from database 
	 * Complete Work info with shortage completion
	 * Create result data with work detail "carry data delete"
	 * @param conn           connection object for database connection
	 * @param ci             Carry data
	 * @param isCreateResult Result data creation   true:Create result data false:Do not create result data
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException throw exception when delete data is not available
	 * @throws InvalidDefineException Exception thrown if there is any problem during database update
	 */
	public void drop(Connection conn, CarryInformation ci, boolean isCreateResult)
															throws ReadWriteException,
																   NotFoundException,
																   InvalidDefineException
	{
		//#CM32099
		// Sets the 'Delete Carry data' to the job type and deletes the Carry data.
		drop (conn, ci, InOutResult.WORKTYPE_CARRYINFODELETE, isCreateResult, COMPLETE_SHORTAGE);
	}

	//#CM32100
	/**
	 * Carry info data delete process
	 * Update carry data delete, work info, stock inof and palette info
	 * while creating result data, use the value instructed with work type
	 * Create work maintenance list if stock exist during forced removal
	 * When sotck confirmation data does not exists in the same aisle when 
	 * CarryInformation is stock confirmation, change the "stock confirmation" flag to "standby"
	 * Delete stock info, palette info and work display info
	 * @param conn           connection object for database connection
	 * @param ci             Carry data
	 * @param worktype       work type
	 * @param isCreateResult Result data creation   true:Create result data false:Do not create result data
	 * @param compDivision   Work completion flag (shortage or normal completion)
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException thrown when there is no data for update, delete
	 * @throws InvalidDefineException Exception thrown if there is any problem during database update
	 */
	public void drop(Connection conn, CarryInformation ci, String worktype, boolean isCreateResult, String compDivision)
															throws ReadWriteException,
																   NotFoundException,
																   InvalidDefineException
	{
		generateCarryInfo(conn);
		generateWorkInfo(conn);
		generatePalette(conn);
		generateStock(conn);

		//#CM32101
		// Fetch palette from carry data
		Palette pl = getPalette(conn, ci.getPaletteId());
		//#CM32102
		// Fetch work info from carry data
		wiKey.KeyClear();
		wiKey.setSystemConnKey(ci.getCarryKey());
		WorkingInformation[] winfos = (WorkingInformation[]) wih.find(wiKey);
		
		//#CM32103
		// Update origin and destination location
		updateShelf(conn, ci, pl.getCurrentStationNumber());

		if (isCompletableWork(conn, ci.getCarryKey()))
		{
			//#CM32104
			// Create result data for the current work
			if (isCreateResult && (compDivision.equals(COMPLETE_NORMAL) || compDivision.equals(COMPLETE_ERROR_SHORTAGE)))
			{
				//#CM32105
				// Do not create result data if not needed
				if (!isNoResultOperation(ci.getWorkType()))
				{
					insertResult(conn, ci, pl, winfos, ci.getWorkType());
				}
			}
			
			//#CM32106
			// Complete the current work info and update stock
			//#CM32107
			// and Create result info for the work and update plan info
			if(ci.getCarryKind() == CarryInformation.CARRYKIND_STORAGE)
			{				
				completeWork(conn, ci.getCarryKey(), ci.getCarryKind(), ci.getDestStationNumber(), compDivision);
			}
			else
			{
				completeWork(conn, ci.getCarryKey(), ci.getCarryKind(), ci.getSourceStationNumber(), compDivision);
			}
		}
		//#CM32108
		// Delete carry data
		ciKey.KeyClear();
		ciKey.setCarryKey(ci.getCarryKey());
		cih.drop(ciKey);
		
		//#CM32109
		// in case of multiple allocation with forced removal, tracking elimination
		//#CM32110
		// Delete carry information on same palette id
		//#CM32111
		// <processing contents>
		//#CM32112
		// 1. Fetch conveyance key and palette id from carryinformation
		//#CM32113
		// 2. Search CarryInfo table with palette id and conveyance key and check whether conflict conveyance key with same palette exist
		//#CM32114
		// When 3. exist, I write in ���O and handle after 4. every Carry data of multi-mortgage.
		//#CM32115
		// I output a work maintenance list for 4. elimination target work
		//#CM32116
		// 5. Update work info, stock info, plan info
		//#CM32117
		// 6. Delete carry data
		//#CM32118
		// 7. Change ASInventoryCheck table status

		//#CM32119
		//Delete multiple allocation from same palette
		//#CM32120
		//Fetch carry data other than current work for same palette id
		ciKey.KeyClear();
		ciKey.setPaletteId(pl.getPaletteId());
		ciKey.setCarryKey(ci.getCarryKey(), "!=");
		//#CM32121
		// In case of multiple allocation
		if(cih.count(ciKey) > 0)
		{
			//#CM32122
			// 6020009=Stock in multiple allocation is deleted. Work No.={0}
			RmiMsgLogClient.write("6020009" + wDelim + ci.getWorkNumber(), "CarryCompleteOperator");

			//#CM32123
			// Setting for work maintenance print
			WorkMaintenanceWriter writer = new WorkMaintenanceWriter(conn);
			writer.setListType(WorkMaintenanceWriter.LIST_DROP);
			//#CM32124
			// Delete tracking data
			writer.setJob("RDB-W0107");

			//#CM32125
			// Delete carry data of multiple allocation, update work info, stock info, plan info related to carry data
			ciKey.setCarryKeyOrder(1, true);
			CarryInformation[] cinfos = (CarryInformation[])cih.find(ciKey);
			for(int i = 0; i < cinfos.length; i++)
			{
				//#CM32126
				// Print the list of work maintenance
				writer.setCarrykey(cinfos[i].getCarryKey());
				writer.setCmdStatus(cinfos[i].getCmdStatus());
				writer.setWorkType(cinfos[i].getWorkType());
				writer.startPrint();

				//#CM32127
				// complete work with multiple allocation and update stock
				//#CM32128
				// and Create result info for the work and update plan info
				//#CM32129
				// Delete stock and palette at the end of this method
				completeWork(conn, cinfos[i].getCarryKey(), cinfos[i].getCarryKind(), cinfos[i].getSourceStationNumber(), COMPLETE_SHORTAGE);
				
				//#CM32130
				// Delete carry data
				ciKey.KeyClear();
				ciKey.setCarryKey(cinfos[i].getCarryKey());
				cih.drop(ciKey);

				//#CM32131
				// Conduct search in Carry Info based on the schedule No. of deleted carry data as a key.
				//#CM32132
				// check to see if the carry data for inventory check or empty location check exist in the same schedule No. 
				//#CM32133
				// If there is no data, change the status of InventoryCheck table of that schedule No. to "Undone".
				updateASInventoryCheckStatusOff(conn, cinfos[i]);

				//#CM32134
				// 6020010=Stock in allocation is deleted. Work No.={0}
				RmiMsgLogClient.write("6020010" + wDelim + cinfos[i].getWorkNumber(), "CarryCompleteOperator");
			}
		}
		
		//#CM32135
		// If other than empty location confirmation, delete stock in palette (InOutResult, DnHostSend)
		if (ci.getPriority() != CarryInformation.PRIORITY_CHECK_EMPTY)
		{
			//#CM32136
			// If the job does not require the record of result, it will not register the resuld data.
			if (isNoResultOperation(ci.getWorkType()) == false)
			{
				//#CM32137
				// Creates the In/Out result data.
				insertRemovedStockResult(conn, ci, worktype);
			}
		}
		
		//#CM32138
		// and delete stock, palette
		//#CM32139
		// check qty since there are chances that stock, palette are deleted in case of picking
		stkKey.KeyClear();
		stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		stkKey.setPaletteid(pl.getPaletteId());
		if (stkh.count(stkKey) > 0)
		{
			stkh.drop(stkKey);
		}
		plKey.KeyClear();
		plKey.setPaletteId(pl.getPaletteId());
		if (plh.count(plKey) > 0)
		{
			plh.drop(plKey);
		}
		
		//#CM32140
		// Delete any work display data
		dropOperationDisplay(conn, ci.getCarryKey(), ci.getCarryKind());
		
		//#CM32141
		// Check table related to stock confirmation, empty location confirmation and update it
		updateInventoryCheckInfo(conn, ci);		
	}
	
	//#CM32142
	/**
	 * Storage Carry info data delete process<BR>
	 * Delete palette, work info, stock info related to Carry data<BR>
	 * and if the carry destination is location , change the location status flag to empty location<BR>
	 * result data is not created
	 * 
	 * 
	 * Currently not being used
	 * 
	 * @param conn connection object for database connection
	 * @param ci   Carry data
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException thrown when there is no data for update, delete
	 * @throws InvalidDefineException Exception thrown if there is any problem during database update
	 */
	private void dropStorage(Connection conn, CarryInformation ci)
															throws ReadWriteException,
																   NotFoundException,
																   InvalidDefineException
	{
		//#CM32143
		// if the destination is location, change to empty location
		updateShelfPresence(conn, ci.getDestStationNumber(), Shelf.PRESENCE_EMPTY);
		
		//#CM32144
		// create respective table usage classes
		generateCarryInfo(conn);
		generateWorkInfo(conn);
		generatePalette(conn);
		generateStock(conn);
				
		//#CM32145
		// Delete stock info related to work info
		wiKey.KeyClear();
		wiKey.setSystemConnKey(ci.getCarryKey());
		WorkingInformation[] winfos = (WorkingInformation[]) wih.find(wiKey);
		for (int i = 0; i < winfos.length; i++)
		{
			//#CM32146
			// Delete stock
			stkKey.KeyClear();
			stkKey.setStockId(winfos[i].getStockId());
			stkh.drop(stkKey);
		}
		
		//#CM32147
		// if there is no single stock info for this palette, delete the palette
		if (!isExistStock(conn, ci.getPaletteId()))
		{
			plKey.KeyClear();
			plKey.setPaletteId(ci.getPaletteId());
			plh.drop(plKey);
		}
		
		//#CM32148
		//Delete carry data, work info
		wiKey.KeyClear();
		wiKey.setSystemConnKey(ci.getCarryKey());
		wih.drop(wiKey);
		ciKey.KeyClear();
		ciKey.setCarryKey(ci.getCarryKey());
		cih.drop(ciKey);
	}

	//#CM32149
	/**
	 * Processing the deletion of retrieval carry data.
	 * While the allocation of Palette is in process, keep Flag on as long as allocation last.
	 * Please call this method if deletion of normal operation is required, e.g., deletion of carry data due to work
	 * maintenance, etc. It should delete CarryInformation contained in ci and WorkInformation from database.
	 * @param conn :Connection with database
	 * @param ci   :carry data
	 * @throws ReadWriteException :Notifies if exception occured when processing for database.
	 * @throws NotFoundException  :Notifies if there are no data to delete or to update.
 	 * @throws InvalidDefineException :Notifies if there are any inconsistency in condition of data update.
	 */
	public void dropRetrieval(Connection conn, CarryInformation ci)
															throws ReadWriteException,
																   NotFoundException,
																   InvalidDefineException
	{
		//#CM32150
		// create respective table usage classes
		generateCarryInfo(conn);
		generateWorkInfo(conn);
		generatePalette(conn);
		generateStock(conn);
		
		try
		{
			CarryInformation checkCarry = null;
			ciKey.KeyClear();
			ciKey.setCarryKey(ci.getCarryKey());
			checkCarry = (CarryInformation) cih.findPrimaryForUpdate(ciKey);
			if (checkCarry == null)
			{
				//#CM32151
				// 6026038=Transfer data for the designated MCKey does not exist. mckey={0}
				Object[] tObj = new Object[1] ;
				tObj[0] = new String(ci.getCarryKey()) ;
				RmiMsgLogClient.write(6026038, LogMessage.F_INFO, "CarryCompleteOperator.dropRetrieval", tObj);
				throw new NotFoundException();
			}
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

		//#CM32152
		// fetch work info, palette related to carry data
		wiKey.KeyClear();
		wiKey.setSystemConnKey(ci.getCarryKey());
		WorkingInformation[] winfos = (WorkingInformation[]) wih.find(wiKey);
		
		Palette pl = getPalette(conn, ci.getPaletteId());

		//#CM32153
		// Checks whether/not the other allocation has not been given.
		ciKey.KeyClear();
		ciKey.setPaletteId(pl.getPaletteId());
		ciKey.setCarryKey(ci.getCarryKey(), "!=");

		//#CM32154
		// if there is no allocation
		//#CM32155
		// update palette and relocation destination 
		if (cih.count(ciKey) == 0)
		{
			//#CM32156
			// update palette
			plAKey.KeyClear();
			plAKey.setPaletteId(pl.getPaletteId());
			plAKey.updateAllocation(Palette.NOT_ALLOCATED);
//#CM32157
//comment out the following if empty locaiton confirmation functionality is implemented
//#CM32158
//			// It returns the location status to 'loaded' if not setting the empty location check.
//#CM32159
//			if (!ci.getWorkType().equals(InOutResult.KIND_FREECHECK))
//#CM32160
//			{
				plAKey.updateRefixDate(new Date());
				//#CM32161
				// if the item code is found the double occupation item codes, it shuold alter to error location.
				stkKey.KeyClear();
				stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
				stkKey.setPaletteid(pl.getPaletteId());
				Stock[] stocks = (Stock[]) stkh.find(stkKey);
				if (stocks[0].getItemCode().equals(AsrsParam.IRREGULAR_ITEMKEY))
				{
					plAKey.updateStatus(Palette.IRREGULAR);
				}
				else
				{
					plAKey.updateStatus(Palette.REGULAR);
				}
//#CM32162
//			}
			plh.modify(plAKey);
			//#CM32163
			// update palette upto this

			//#CM32164
			//In case of relocation data, change destination location into empty
			if(ci.getCarryKind() == CarryInformation.CARRYKIND_RACK_TO_RACK)
			{
				updateShelPresenceForDirect(conn, ci.getDestStationNumber());
			}
		}
		//#CM32165
		// if other allocation exist
		//#CM32166
		// modify palette and carry data
		else
		{
			//#CM32167
			//Do nothing, when status of Palette is storing.
			if (pl.getStatus() != Palette.STORAGE_PLAN)
			{
				//#CM32168
				// Count CarryInformation data that carrykind is RETRIEVAL and cmdstatus is RESPONSE_WAIT or INSTRUCTION 
				//#CM32169
				// or LOAD_PIC_UP_COMPLETION or RETRIVAL_COMPLETION or ARRIVAL
				int intobj[] = new int[5];
				intobj[0] = CarryInformation.CMDSTATUS_WAIT_RESPONSE;
				intobj[1] = CarryInformation.CMDSTATUS_INSTRUCTION;
				intobj[2] = CarryInformation.CMDSTATUS_PICKUP;
				intobj[3] = CarryInformation.CMDSTATUS_COMP_RETRIEVAL;
				intobj[4] = CarryInformation.CMDSTATUS_ARRIVAL;
				ciKey.KeyClear();
				ciKey.setCarryKey(ci.getCarryKey(), "!=");
				ciKey.setCarryKind(CarryInformation.CARRYKIND_RETRIEVAL);
				ciKey.setCmdStatus(intobj);
				ciKey.setPaletteId(pl.getPaletteId());
				if (cih.count(ciKey) == 0)
				{
					//#CM32170
					// change the status of palette to RETRIEVAL_PLAN
					plAKey.KeyClear();
					plAKey.updateStatus(Palette.RETRIEVAL_PLAN);
					plAKey.updateRefixDate(new Date());
					plAKey.setPaletteId(pl.getPaletteId());
					plh.modify(plAKey);
				}
			}

			//#CM32171
			//Change the retrievaldetail of multi allocated carryinfo from Unit to Picking
			//#CM32172
			//CMENJP2333$CMeg.) With multiple allocation with the first one as picking and the second as unit picking, if the first picking is cancelled, change the second unit picking to picking
			ciKey.KeyClear();
			ciKey.setCarryKey(ci.getCarryKey(), "!=");
			ciKey.setPaletteId(pl.getPaletteId());
			ciKey.setRetrievalDetail(CarryInformation.RETRIEVALDETAIL_UNIT);
			ciKey.setCarryKeyCollect();
			if(cih.count(ciKey) > 0)
			{
				//#CM32173
				// In case of multiple allocation, if picking working exist for the same palette id, change that data to picking
				CarryInformation[] cidata = (CarryInformation[])cih.find(ciKey);
				ciAKey.KeyClear();
				ciAKey.setCarryKey(cidata[0].getCarryKey());
				ciAKey.updateRetrievalDetail(CarryInformation.RETRIEVALDETAIL_PICKING);
				cih.modify(ciAKey);
			}
		}

		//#CM32174
		// Setting back the available arrival quantity of retrieval.
		for (int i = 0; i < winfos.length; i++)
		{
			cancelRetrievalStock(conn, winfos[i], ci.getRetrievalDetail());
		}
		
		//#CM32175
		// if stock does not exist with same palette, delete palette
		if (!isExistStock(conn, ci.getPaletteId()))
		{
			plKey.KeyClear();
			plKey.setPaletteId(ci.getPaletteId());
			plh.drop(plKey);
		}

		//#CM32176
		// Change the current work info status to delete
		wiAKey.KeyClear();
		wiAKey.setSystemConnKey(ci.getCarryKey());
		wiAKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_DELETE);
		wiAKey.updateLastUpdatePname(CLASSNAME);
		wih.modify(wiAKey);
		
		//#CM32177
		// Delete current carry info
		ciKey.KeyClear();
		ciKey.setCarryKey(ci.getCarryKey());
		cih.drop(ciKey);

		//#CM32178
		// Check table related to stock confirmation, empty location confirmation and update it
		updateInventoryCheckInfo(conn, ci);
	}

	//#CM32179
	/**
	 * Process the cancelation of retrieval data instruction, and alter the status of specified <code>CarryInformation</code> to 'to be released'.
	 * This <code>CarryInformation</code>, waiting to be released, will be included in the retrieval instructions to submit.
	 * If the carry status of this <code>CarryInformation</code> is anything other than 'wait for response'
	 * or 'already released', it should output the message of caution in the log and it will not process the cancelation.
	 * Detail of update
	 *   CarryInformation
	 *   - alters the carry status to 'start'
	 *   Palette
	 *   - alters the status to 'reserved for retrieval'
	 * @param conn	: Connection with databse
	 * @param ci 	: carry data
	 * @throws ReadWriteException :Notifies if exception occured when processing for database.
	 * @throws NotFoundException  :Notifies if there are no data to delete or to update.
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in condition of data update.
	 */
	public void cancelRetrieval(Connection conn, CarryInformation ci)
															throws ReadWriteException,
																   NotFoundException,
																   InvalidDefineException
	{
		//#CM32180
		// create respective table usage classes
		generateCarryInfo(conn);
		generatePalette(conn);
				
		//#CM32181
		// Checks the carry status.
		switch (ci.getCmdStatus())
		{
			case CarryInformation.CMDSTATUS_WAIT_RESPONSE:
			case CarryInformation.CMDSTATUS_INSTRUCTION:
				//#CM32182
				// Alters the carry status to 'start' if 'wait for repll' or 'instruction released'.
				ciAKey.KeyClear();
				ciAKey.updateCmdStatus(CarryInformation.CMDSTATUS_START);
				ciAKey.setCarryKey(ci.getCarryKey());
				cih.modify(ciAKey);
				
				//#CM32183
				// Alters the pallet status to 'reserved for retrieval'.
				plAKey.KeyClear();
				plAKey.updateStatus(Palette.RETRIEVAL_PLAN);
				plAKey.updateRefixDate(new Date());
				plAKey.setPaletteId(ci.getPaletteId());
				plh.modify(plAKey);

				//#CM32184
				// 6020008=Picking data was canceled. mckey={0}
		        Object[] tObj = new Object[1] ;
				tObj[0] = new String(ci.getCarryKey()) ;
				RmiMsgLogClient.write(6020008, LogMessage.F_INFO, "CarryCompleteOperator", tObj);
				break;

			case CarryInformation.CMDSTATUS_ALLOCATION:
			case CarryInformation.CMDSTATUS_START:
			case CarryInformation.CMDSTATUS_PICKUP:
			case CarryInformation.CMDSTATUS_COMP_RETRIEVAL:
			case CarryInformation.CMDSTATUS_ARRIVAL:
			case CarryInformation.CMDSTATUS_ERROR:
			default:
				//#CM32185
				// If the carry status is anything other than 'wait for response' or 'instruction released', 
				//#CM32186
				// it should output the message of caution in the log, and it will not proces cancelation.
				//#CM32187
				// 6022016=Result of received text cancel request -> Unable to cancel. Data has been transferred. mckey={0}
		        Object[] dObj = new Object[1];
				dObj[0] = new Integer(ci.getCmdStatus());
				RmiMsgLogClient.write(6022016, LogMessage.F_NOTICE, "CarryCompleteOperator", dObj);
				break;
		}
	}
	
	//#CM32188
	/**
	 * Processing the storage data cancelation.
	 *   -updates the carry status to 'start'
	 * @param conn	:Connection with database
	 * @param ci 	:carry data
	 * @throws ReadWriteException :Notifies if exception occured when processing for database.
	 * @throws NotFoundException  :Notifies if there are no data to update.
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in condition of data update.
	 */
	public void cancelStorage(Connection conn, CarryInformation ci)
															throws ReadWriteException,
																   NotFoundException,
																   InvalidDefineException
	{
		generateCarryInfo(conn);
        Object[] tObj = new Object[1] ;

		//#CM32189
		// Checks the status of carry.
		switch(ci.getCmdStatus())
		{
			case CarryInformation.CMDSTATUS_WAIT_RESPONSE:
			case CarryInformation.CMDSTATUS_INSTRUCTION:
				//#CM32190
				// Alters the status of carry to 'start' when wiating for response or the instruction was already released.
				ciAKey.KeyClear();
				ciAKey.updateCmdStatus(CarryInformation.CMDSTATUS_START);
				ciAKey.setCarryKey(ci.getCarryKey());
				cih.modify(ciAKey);

				//#CM32191
				// 6020007=Storage data was canceled. mckey={0}
				tObj[0] = new String(ci.getCarryKey()) ;
				RmiMsgLogClient.write(6020007, LogMessage.F_INFO, "CarryCompleteOperator", tObj);
				break;

			case CarryInformation.CMDSTATUS_ALLOCATION:
			case CarryInformation.CMDSTATUS_START:
			case CarryInformation.CMDSTATUS_PICKUP:
			case CarryInformation.CMDSTATUS_COMP_RETRIEVAL:
			case CarryInformation.CMDSTATUS_ARRIVAL:
			case CarryInformation.CMDSTATUS_ERROR:
			default:
				//#CM32192
				// 6022016=Result of received text cancel request -> Unable to cancel. Data has been transferred. mckey={0}
				tObj[0] = new Integer(ci.getCmdStatus()) ;
				RmiMsgLogClient.write(6022016, LogMessage.F_NOTICE, "CarryCompleteOperator", tObj);
				break;
		}
	}
	
	//#CM32193
	/**
	 * Releasing the allocation of specified carry data. If it is a retrieval data, it also deletes CarryInformation and WorkInformation.
	 * In case of storage, it also deletes Palette and Stock which the CarryInformation refers to.
	 * For the return value, it returns aisle station no. that deleted CarryInformation had preserved.
	 * @param conn    :Connection with database
	 * @param ciarray :List of CarryInformation which the allocation to be released
	 * @return        :List of aisle station no, that deleted CarryInformation ahd preserved
	 * @throws ReadWriteException :Notifies if exception occured when processing for database.
	 * @throws NotFoundException  :Notifies if there are no data to delete.
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in condition of data update.
	 */
	public String[] releaseAllocation(Connection conn, CarryInformation[] ciarray)
															throws  ReadWriteException,
																	NotFoundException,
																	InvalidDefineException
	{
		Hashtable hash = new Hashtable();
		String dummy = "dummy";
		
		for (int i = 0 ; i < ciarray.length ; i++)
		{
			//#CM32194
			// Checks the carry status and deletes CarryInformation of 'allocated' or 'start' status.
			switch (ciarray[i].getCmdStatus())
			{
				case CarryInformation.CMDSTATUS_ALLOCATION:
				case CarryInformation.CMDSTATUS_START:
					switch(ciarray[i].getCarryKind())
					{
						//#CM32195
						// Storage, direct travel
						case CarryInformation.CARRYKIND_STORAGE:
						case CarryInformation.CARRYKIND_DIRECT_TRAVEL:
							//#CM32196
							// Consider storage on return from stock confirmation
							//#CM32197
							// In case of stock confirmation data, change so that it is not deleted
							if(!(ciarray[i].getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK))
							{
								dropStorage(conn, ciarray[i]);
								if (!StringUtil.isBlank(ciarray[i].getAisleStationNumber()))
								{
									hash.put(ciarray[i].getAisleStationNumber(), dummy);
								}
							}
							break;
							
						//#CM32198
						// Retrieval, location to location move
						case CarryInformation.CARRYKIND_RETRIEVAL:
						case CarryInformation.CARRYKIND_RACK_TO_RACK:
							try
							{
								dropRetrieval(conn, ciarray[i]);
							}
							catch (NotFoundException e)
							{
								//#CM32199
								//Don't throw error when there is no target data
							}
							
							if (!StringUtil.isBlank(ciarray[i].getAisleStationNumber()))
							{
								hash.put(ciarray[i].getAisleStationNumber(), dummy);
							}
							break;
							
						default:
							break;
					}
					break;
					
				case CarryInformation.CMDSTATUS_WAIT_RESPONSE:
				case CarryInformation.CMDSTATUS_INSTRUCTION:
				case CarryInformation.CMDSTATUS_PICKUP:
				case CarryInformation.CMDSTATUS_COMP_RETRIEVAL:
				case CarryInformation.CMDSTATUS_ARRIVAL:
				case CarryInformation.CMDSTATUS_ERROR:
				default:
					break;
			}
		}
		
		String[] ailes = new String[hash.size()];
		Enumeration em = hash.keys();
		for (int i = 0 ; em.hasMoreElements() ; i++)
		{
			ailes[i] = (String)em.nextElement();
		}
		return ailes;
		
	}

	//#CM32200
	/**
	 * If the specified CarryInformation is the carry data for empty location check,
	 * check to see if the empty location check carry data exists in the same aisle station as CarryInformation.
	 * If there is no data, alter hte inventory checking flag to 'to be checked'.
	 * @param conn   :Connection with database
	 * @param ci     :carry data
	 * @throws ReadWriteException :Notifies if exception occured when processing for database.
	 * @throws NotFoundException  :Notifies if data to modify cannot be found.
	 */
	public void emptyLocationCheckOff (Connection conn, CarryInformation ci)
																throws  ReadWriteException,
																		NotFoundException
	{
		//#CM32201
		// generate conveyance data operation class
		generateCarryInfo(conn);
		
		//#CM32202
		// If the CarryInforrmation is the empty location check data, count the other empty location check data.
		if (ci.getRetrievalDetail() == CarryInformation.PRIORITY_CHECK_EMPTY)
		{
			ciKey.KeyClear();
			ciKey.setAisleStationNumber(ci.getAisleStationNumber());
			ciKey.setCmdStatus(CarryInformation.CMDSTATUS_COMP_RETRIEVAL, "<");
			ciKey.setPriority(CarryInformation.PRIORITY_CHECK_EMPTY);
			
			if (cih.count(ciKey) <= 0)
			{
				//#CM32203
				// Change empty location check flag to OFF in aisle table
				updateAisleInventoryCheckFlag(conn, 
											 ci.getAisleStationNumber(), 
											 Station.NOT_INVENTORYCHECK);
			}
		}
	}

	//#CM32204
	/**
	 * Do stock confirmation, empty location check, update
	 * Stock confirmation, empty location confirmation if there is no work data, set aisle to "standby"
	 *If stock confirmation data, emptylocation confirmation data does not exist in the same schedule number of the target Carry data, change the stock confirmation info status
	 * @param conn database connection
	 * @param ci   Carry data
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException throw exception if the info for modification does not exist
	 */
	public void updateInventoryCheckInfo(Connection conn, CarryInformation ci) 
																	throws NotFoundException, ReadWriteException
	{
		//#CM32205
		// Checks the inventory check data; if there is no data of inventory check jobs or data of empty location check jobs, 
		// switch the status to 'to be checked'.
		updateAisleInventoryCheckOff(conn, ci);

		//#CM32206
		// It searches in CarryInfor according to the schedule no. in Carry data as a key.
		//#CM32207
		// If the carry data for incentory check or empty location check exist in the same schedule no., 
		//#CM32208
		// it should alter the Status of InventoryCheck table of that schedule no. to 'to be processed.'
		updateASInventoryCheckStatusOff(conn, ci);		
	}
	
	//#CM32209
	/**
	 * Set class name
	 * If this method is not used, the last updated class name will be this class
	 * @param str class name
	 */
	public void setClassName(String str)
	{
		CLASSNAME = str;
	}
	
	//#CM32210
	// Package methods -----------------------------------------------

	//#CM32211
	// Protected methods ---------------------------------------------
	//#CM32212
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
			//#CM32213
			// srtorage
			case CarryInformation.CARRYKIND_STORAGE:
				stno = ci.getSourceStationNumber();
				locno = ci.getDestStationNumber();
				break;
				
			//#CM32214
			// retrieval
			case CarryInformation.CARRYKIND_RETRIEVAL:
				stno = ci.getDestStationNumber();
				//#CM32215
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
				
			//#CM32216
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
		//#CM32217
		// Create work info, result data
		for (int i = 0; i < winfos.length; i++)
		{
			//#CM32218
			//Fetch corresponding stock
			stk = getStock(conn, winfos[i].getStockId());
			
			int inoutQty = 0;
			//#CM32219
			// srtorage, no plan storage
			if (winfos[i].getJobType().equals(WorkingInformation.JOB_TYPE_STORAGE)
			  || winfos[i].getJobType().equals(WorkingInformation.JOB_TYPE_EX_STORAGE))
			{
				//#CM32220
				// if the conveyance status is instructed in case of direct travel
				//#CM32221
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
					//#CM32222
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
			//#CM32223
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
			//#CM32224
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
			
			//#CM32225
			// Result of empty pallet will not be reported.
			if (winfos.length == 1)
			{
				//#CM32226
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
				//#CM32227
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
			
			//#CM32228
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

	//#CM32229
	/**
	 * Edits the In/Out resuld data and registers the outcome in database.
	 * Result data will be edited based on Palette and Stock preserved in CarryInformation or CarryInformation.
	 * This method will be used when all stocks of carrying pallets were deleted , as in unit retrievals or deletion of carry data.
	 * @param conn     :Connection with database
	 * @param ci       :carry data
	 * @param worktype :work type
	 * @throws ReadWriteException : Notifies if exception occured when processing for database.
	 */
	protected void insertRemovedStockResult(Connection conn, CarryInformation ci, String worktype)
															throws ReadWriteException
	{
		generateStock(conn);
		generatePalette(conn);
		
		Palette pl = getPalette(conn, ci.getPaletteId());
		if (pl == null)
		{
			return;
		}
		
		stkKey.KeyClear();
		stkKey.setPaletteid(pl.getPaletteId());
		stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		Stock[] stocks = null;
		stocks = (Stock[]) stkh.find(stkKey);
		
		String stno = "";
		String locno = "";
		switch (ci.getCarryKind())
		{
			//#CM32230
			// srtorage
			case CarryInformation.CARRYKIND_STORAGE:
				stno = ci.getSourceStationNumber();
				locno = ci.getDestStationNumber();
				break;
				
			//#CM32231
			// direct travel
			case CarryInformation.CARRYKIND_DIRECT_TRAVEL:
				stno = ci.getSourceStationNumber();
				locno = ci.getDestStationNumber();
				break;
				
			//#CM32232
			// retrieval
			case CarryInformation.CARRYKIND_RETRIEVAL:
				stno = ci.getDestStationNumber();
				locno = pl.getCurrentStationNumber();
				break;
				
			//#CM32233
			// location to location move
			case CarryInformation.CARRYKIND_RACK_TO_RACK:
				stno = ci.getDestStationNumber();
				locno = pl.getCurrentStationNumber();
				break;
		}
		
		SequenceHandler seq = new SequenceHandler(conn);
		String batchNo = seq.nextNoPlanBatchNo();
		InOutResult ior = new InOutResult();
		Date createDate = new Date();
		boolean isSystemItem = false;
		for (int i = 0; i < stocks.length; i++)
		{
			isSystemItem = false;
			ior.setStoreDate(createDate);
			ior.setResultKind(InOutResult.MAINTENANCE_MINUS);
			ior.setConsignorCode(stocks[i].getConsignorCode());
			ior.setConsignorName(stocks[i].getConsignorName());
			ior.setItemCode(stocks[i].getItemCode());
			ior.setLotNumber(stocks[i].getLotNo());
			ior.setWorkType(worktype);
			ior.setStationNumber(stno);
			ior.setWHStationNumber(pl.getWHStationNumber());
			ior.setAisleStationNumber(ci.getAisleStationNumber());
			ior.setEnteringQty(stocks[i].getEnteringQty());
			ior.setBundleEnteringQty(stocks[i].getBundleEnteringQty());
			ior.setInOutQuantity(0 - stocks[i].getStockQty());
			ior.setWorkNumber(ci.getWorkNumber());
			ior.setPaletteKind(pl.getPaletteTypeId());
			ior.setLocationNumber(locno);
			ior.setScheduleNumber(ci.getScheduleNumber());
			ior.setPaletteId(pl.getPaletteId());
			ior.setCarryKey(ci.getCarryKey());
			ior.setItemName1(stocks[i].getItemName1());
			ior.setReStoring(stocks[i].getRestoring());
			ior.setInCommingDate(stocks[i].getInstockDate());
			ior.setStatus(InOutResult.UNPROCESSED);

			//#CM32234
			// Result of empty pallet wil not be reported.
			if (pl.getEmpty() == Palette.STATUS_EMPTY)
			{
				isSystemItem = true;
			}
			//#CM32235
			// Item codes for error locations will not be included in report result.
			else if (stocks[i].getItemCode().equals(AsrsParam.IRREGULAR_ITEMKEY))
			{
				isSystemItem = true;
			}
			
			if (isSystemItem)
			{
				ior.setReport(InOutResult.NOT_REPORT);
			}
			else
			{
				ior.setReport(InOutResult.DO_REPORT);
			}
			
			ior.setWHStationNumber(pl.getWHStationNumber());

			generateInOutResult(conn);
			try
			{
				iorh.create(ior);
			}
			catch(DataExistsException e)
			{
				throw new ReadWriteException(e.getMessage());
			}

			//#CM32236
			// Don't create report data in case of empty palette or error location
			if (!isSystemItem)
			{
				insertHostSend(conn, stocks[i], WorkingInformation.JOB_TYPE_MAINTENANCE_MINUS, ci.getCarryKey(), batchNo);
			}
		}		
	}

	//#CM32237
	/**
	 * Add to result send table info (DNHOSTSEND) <BR> 
	 * <BR>     
	 * Add result info with respect to received parameter contents<BR>
	 * <BR>
	 * @param conn        instance to store database connection
	 * @param stk         Drop result stock info
	 * @param jobType     Result type (Work type)
	 * @param carryKey    conveyance key
	 * @param batchNo     batch no.
	 * @throws ReadWriteException throw any abnormal database connection error
	 */
	protected void insertHostSend(
		Connection conn,
		Stock stk,
		String jobType,
		String carryKey,
		String batchNo)
		throws ReadWriteException
	{
		String sysDate = "";
		try
		{
			WareNaviSystemManager wmsMng = new WareNaviSystemManager(conn);
			sysDate = wmsMng.getWorkDate();
		}
		catch (ScheduleException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

		try
		{
			HostSendHandler hostsendHandler = new HostSendHandler(conn);
			HostSend hostsend = new HostSend();
			SequenceHandler sequence = new SequenceHandler(conn);
			
			String jobno_seq = sequence.nextJobNo();
			hostsend.setWorkDate(sysDate);
			hostsend.setJobNo(jobno_seq);
			hostsend.setJobType(jobType);
			hostsend.setCollectJobNo(jobno_seq);
			hostsend.setStatusFlag(HostSend.STATUS_FLAG_COMPLETION);
			hostsend.setBeginningFlag(HostSend.BEGINNING_FLAG_STARTED);
			hostsend.setStockId(stk.getStockId());
			hostsend.setAreaNo(stk.getAreaNo());
			hostsend.setLocationNo(stk.getLocationNo());
			hostsend.setConsignorCode(stk.getConsignorCode());
			hostsend.setConsignorName(stk.getConsignorName());
			hostsend.setSupplierCode(stk.getSupplierCode());
			hostsend.setSupplierName1(stk.getSupplierName1());
			hostsend.setItemCode(stk.getItemCode());
			hostsend.setItemName1(stk.getItemName1());
			hostsend.setHostPlanQty(0);
			hostsend.setPlanQty(0);
			hostsend.setPlanEnableQty(0);
			hostsend.setResultQty(stk.getStockQty());
			hostsend.setShortageCnt(0);
			hostsend.setPendingQty(0);
			hostsend.setEnteringQty(stk.getEnteringQty());
			hostsend.setBundleEnteringQty(stk.getBundleEnteringQty());
			hostsend.setCasePieceFlag(stk.getCasePieceFlag());
			hostsend.setWorkFormFlag(stk.getCasePieceFlag());
			hostsend.setItf(stk.getItf());
			hostsend.setBundleItf(stk.getBundleItf());
			hostsend.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
			hostsend.setUseByDate(stk.getUseByDate());
			hostsend.setLotNo(stk.getLotNo());
			hostsend.setPlanInformation(stk.getPlanInformation());
			hostsend.setResultUseByDate(stk.getUseByDate());
			hostsend.setResultLotNo(stk.getLotNo());
			hostsend.setResultLocationNo(stk.getLocationNo());
			hostsend.setSystemDiscKey(HostSend.SYSTEM_DISC_KEY_ASRS);
			hostsend.setSystemConnKey(carryKey);
			hostsend.setBatchNo(batchNo);
			
			//#CM32238
			// Work report flag (Not yet reported)
			hostsend.setReportFlag(HostSend.REPORT_FLAG_NOT_SENT);
			//#CM32239
			// Plan info registering date/time
			hostsend.setPlanRegistDate(null);
			hostsend.setRegistPname(CLASSNAME);
			hostsend.setLastUpdatePname(CLASSNAME);

			//#CM32240
			// Data registering
			hostsendHandler.create(hostsend);
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
	
	//#CM32241
	/**
	 * Create restorage plan data in case of Forced removal<BR>
	 * If the stock subtracted with plan qty is greater than 0, create a restorage plan for the 
	 * mixed load stock that is removed completely from the palette
	 * @param conn database connection
	 * @param ci Carry Info
	 * @throws ReadWriteException Throw exception occurred during database access
	 */
	protected void insertReStoringData(Connection conn, CarryInformation ci)
													throws ReadWriteException
	{
	}
	
	//#CM32242
	/**
	 * Creating the empty pallet data.
	 * @throws ReadWriteException :Notifies if exception occured when processing for database.
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in condition of data update.
     * @throws InvalidStatusException :Notifies if the search condition is invalid.
	 */
	protected void insertEmptyPalette(Connection conn, Palette pl) throws ReadWriteException, InvalidDefineException, InvalidStatusException
	{
		generateStock(conn);
		generatePalette(conn);
		try
		{
			SequenceHandler seqHandler = new SequenceHandler(conn);
			String stockId_seq = seqHandler.nextStockId();
			
			//#CM32243
			// Generaete the Stock instance.
			//#CM32244
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
			
			//#CM32245
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
			//#CM32246
			// The data already exists with same pallet ID.
			throw new InvalidDefineException(de.getMessage());
		}
	}
	
	//#CM32247
	/**
	 * Check if it is a completion work
	 * Make it illegal if same conveyance key has data with multiple work status
	 *Dont call completion process if work info has completed data
	 * 
	 * @param conn     database connection
	 * @param carryKey conveyance key
	 * @return Whether to call completion process or not true:call false:do not call
	 * @throws ReadWriteException throw any database access exception
	 * @throws InvalidDefineException Exception thrown if there is any problem during database update
	 */
	protected boolean isCompletableWork(Connection conn, String carryKey)
									throws ReadWriteException, InvalidDefineException
	{
		generateWorkInfo(conn);
		
		//#CM32248
		// If there is no work info related to carry data or there is work info with multiple status, throw error
		wiKey.KeyClear();
		wiKey.setSystemConnKey(carryKey);
		wiKey.setStatusFlagCollect();
		wiKey.setStatusFlagGroup(1);
		wiKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		if (wih.count(wiKey) != 1)
		{
			throw new InvalidDefineException();
		}
		
		wiKey.KeyClear();
		wiKey.setSystemConnKey(carryKey);
		wiKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
		wiKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		//#CM32249
		// Don't call modify process if work info is already completed
		if (wih.count(wiKey) > 0)
		{
			return false;
		}
		
		return true;
	}
	
	//#CM32250
	/**
	 * Call work completion process related to conveyance key<BR>
	 * Complete work info and modify stock<BR>
	 * and create result send info for the target work info, and modify plan info<BR>
	 * 
	 * @param conn           database connection
	 * @param carryKey       conveyance key
	 * @param carryKind      Carry type of Carry data
	 * @param resultLocation location no. to set in work info
	 * @param compDivision   Work completion flag (shortage or normal completion)
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException throw exception when modify, delete data is not available
	 * @throws InvalidDefineException Exception thrown if there is any problem during database update
	 */
	protected void completeWork(Connection conn, String carryKey, int carryKind, String resultLocation, String compDivision)
											throws ReadWriteException,
													NotFoundException,
													InvalidDefineException
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
			//#CM32251
			// Complete work info and create result
			upedWinfo = completeWorkinfo(conn, winfos[i], resultLocation, compDivision);
			//#CM32252
			// update stock
			updateStock(conn, upedWinfo, carryKind);
			
			//#CM32253
			// Modify related plan info
			//#CM32254
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
	
	//#CM32255
	/**
	 * Complete work info
	 * Create result at the same time
	 * @param conn           database connection
	 * @param winfo          Work info in completion
	 * @param resultLocation Result location no.
	 * @param compDivision   Work completion flag (shortage or normal completion)
	 * @return Work info after modification
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException Throw if there is no data to modify
	 * @throws InvalidDefineException Exception thrown if there is any problem during database update
	 */
	protected WorkingInformation completeWorkinfo(Connection conn, WorkingInformation winfo, String resultLocation, String compDivision)
						throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		generateWorkInfo(conn);
		generateHostSend(conn);
		try
		{
			//#CM32256
			// fetch work date
			WareNaviSystemManager wmsManager = new WareNaviSystemManager(conn);
			String workDate = wmsManager.getWorkDate();
		
			//#CM32257
			// Modify work info (complete process)
			wiAKey.KeyClear();
			wiAKey.setJobNo(winfo.getJobNo());
			wiAKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
			//#CM32258
			// Sum up shortage in case of shortage completion, result qty in case of normal completion
			if (compDivision.equals(COMPLETE_SHORTAGE) || compDivision.equals(COMPLETE_ERROR_SHORTAGE))
			{
				wiAKey.updateShortageCnt(winfo.getPlanEnableQty());
			}
			else
			{
				wiAKey.updateResultQty(winfo.getPlanEnableQty());
			}
			wiAKey.updateResultUseByDate(winfo.getUseByDate());
			wiAKey.updateResultLotNo(winfo.getLotNo());
			wiAKey.updateResultLocationNo(resultLocation);
			wiAKey.updateLastUpdatePname(CLASSNAME);
			wih.modify(wiAKey);
			
			//#CM32259
			// Recreate instance to create result data
			winfo.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
			if (compDivision.equals(COMPLETE_SHORTAGE) || compDivision.equals(COMPLETE_ERROR_SHORTAGE))
			{
				winfo.setShortageCnt(winfo.getPlanEnableQty());
			}
			else
			{
				winfo.setResultQty(winfo.getPlanEnableQty());
			}
			winfo.setResultUseByDate(winfo.getUseByDate());
			winfo.setResultLotNo(winfo.getLotNo());
			winfo.setResultLocationNo(resultLocation);
			winfo.setLastUpdatePname(CLASSNAME);
			
			//#CM32260
			// Do not create result data in case of empty palette or error location
			if ((winfo.getConsignorCode().equals(WmsParam.EMPTYPB_CONSIGNORCODE) 
			&& winfo.getItemCode().equals(AsrsParam.EMPTYPB_ITEMKEY))
			|| (winfo.getConsignorCode().equals(WmsParam.IRREGULAR_CONSIGNORCODE)
			&& winfo.getItemCode().equals(AsrsParam.IRREGULAR_ITEMKEY)))
			{
				return winfo;
			}
			
			//#CM32261
			// Create result send info
			host = new HostSend(winfo, workDate, CLASSNAME);
			//#CM32262
			// Change work report flag to send during stock confirmation
			if(winfo.getJobType().equals(SystemDefine.JOB_TYPE_ASRS_INVENTORY_CHECK))
			{
				//#CM32263
				// sent
				host.setReportFlag(HostSend.REPORT_FLAG_SENT);
			}
			hostSendh.create(host);
		}
		catch (DataExistsException e)
		{
			//#CM32264
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
	
	//#CM32265
	/**
	 * Update stock related to work info
	 * 
	 * @param conn      database connection
	 * @param winfos    Work info related to carry data
	 * @param carryKind Carry type of Carry data
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException Throw exception if the target stock for updation does not exist in database
	 * @throws InvalidDefineException Exception thrown if there is any problem during database update
	 */
	protected void updateStock(Connection conn, WorkingInformation winfo, int carryKind)
																throws InvalidDefineException,
																		ReadWriteException,
																		NotFoundException
	{
		//#CM32266
		// Search stock related to work
		Stock stk = getStock(conn, winfo.getStockId());
		
		//#CM32267
		// If the work info is "storage" or carry data is "direct  transfer", add stock qty
		if (winfo.getJobType().equals(WorkingInformation.JOB_TYPE_STORAGE)
			|| winfo.getJobType().equals(WorkingInformation.JOB_TYPE_EX_STORAGE) 
			|| carryKind == CarryInformation.CARRYKIND_DIRECT_TRAVEL)
		{
			updateStockPlus(conn, winfo.getResultQty(), winfo.getShortageCnt(), stk, carryKind);
			
		}
		//#CM32268
		// Subtract in case of picking
		else
		{
			updateStockMinus(conn, winfo.getResultQty(), winfo.getShortageCnt(), stk);
				
		}
	}

	//#CM32269
	/**
	 * Call the update process for plan info based on specified plan unique key<BR>
	 * If the Work detail is not Storage or Picking, don not process plan info<BR>
	 * Fetch work info based on unique key. If the result qty (result + shortage) 
	 * is smaller than plan qty, do partial complete. If they match, complete work<BR>
	 * 
	 * @param conn     database connection
	 * @param planUkey plan unique key
	 * @param jobType  work type
	 * @throws ReadWriteException Throw exception if it occurs during database access
	 */
	protected void updatePlan (Connection conn, String planUkey, String jobType) throws ReadWriteException
	{
		//#CM32270
		// Dont process plan info if the work is other than storage or picking
		if (jobType.equals(WorkingInformation.JOB_TYPE_STORAGE))
		{
			updateStoragePlan(conn, planUkey);
		}
		else if (jobType.equals(WorkingInformation.JOB_TYPE_RETRIEVAL))
		{
			updateRetrievalPlan(conn, planUkey);
		}
		else
		{
			return;
		}		
	}
	
	//#CM32271
	/**
	 * Fetch work info, result info related to unique key<BR>
	 * and return 0 if there is no work info<BR>
	 * 
	 * @param conn     database connection
	 * @param planUkey plan unique key
	 * @return result qty grouped by unique key
	 * @throws ReadWriteException Throw exception if it occurs during database access
	 */
	protected int getResultQty(Connection conn, String planUkey) throws ReadWriteException
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
		
		//#CM32272
		// Return 0 result if there is no work info
		if (winfo == null)
		{
			return 0;
		}
		
		return winfo.getResultQty(); 
	}
	
	//#CM32273
	/**
	 * Fetch shortage qty from work info using plan unique key<BR>
	 * and return 0 if there is no work info<BR>
	 * 
	 * @param conn     database connection
	 * @param planUkey plan unique key
	 * @return shortage qty grouped by plan unique key
	 * @throws ReadWriteException Throw exception if it occurs during database access
	 */
	protected int getShortageQty(Connection conn, String planUkey) throws ReadWriteException
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
		
		//#CM32274
		// return 0 shortage if there is no workinfo record
		if (winfo == null)
		{
			return 0;
		}
		
		return winfo.getShortageCnt(); 
	}
	
	
	//#CM32275
	/**
	 * Fetch result qty from work info related to storage plan info and update storage plan info<BR>
	 * If the result qty is less than plan, change status to partial completion. Else change to complete<BR>
	 * To change the status to partially completed, search work info with the same plan unique key. If completed or working status data exist, do not update<BR>
	 * 
	 * @param conn     database connection
	 * @param planUkey plan unique key
	 * @throws ReadWriteException Throw exception if it occurs during database access
	 */
	protected void updateStoragePlan (Connection conn, String planUkey) throws ReadWriteException
	{
		//#CM32276
		// fetch work result qty from workinfo
		int resultQty = getResultQty(conn, planUkey);
		int shortageQty = getShortageQty(conn, planUkey);

		generateStoragePlan(conn);
		
		//#CM32277
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
		
		//#CM32278
		// update storage plan info
		spAKey.KeyClear();
		spAKey.setStoragePlanUkey(planUkey);
		//#CM32279
		//if the result qty is lesser than plan, make it partially complete
		if ((resultQty + shortageQty) < sp.getPlanQty())
		{
			//#CM32280
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
	
	//#CM32281
	/**
	 * Fetch result qty from work info related to picking plan info and update picking info<BR>
	 * If the result qty is lesser than plan qty, search work info using the same plan unique key. Else update to complete<BR>
	 * If the plan qty is lesser, update in the following priority. working > started > partially completed<BR>
	 * 
	 * @param conn     database connection
	 * @param planUkey plan unique key
	 * @throws ReadWriteException Throw exception if it occurs during database access
	 */
	protected void updateRetrievalPlan (Connection conn, String planUkey) throws ReadWriteException
	{		
		//#CM32282
		// fetch work result qty from workinfo
		int resultQty = getResultQty(conn, planUkey);
		int shortageQty = getShortageQty(conn, planUkey);

		generateRetrievalPlan(conn);

		//#CM32283
		// fetch picking plan info
		rpKey.KeyClear();
		rpKey.setRetrievalPlanUkey(planUkey);
		RetrievalPlan rp = null;
		try
		{
			rp = (RetrievalPlan) rph.findPrimary(rpKey);
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		
		//#CM32284
		// update picking plan info
		rpAKey.KeyClear();
		rpAKey.setRetrievalPlanUkey(planUkey);
		//#CM32285
		// If the result qty is lesser than plan qty, update the plan info work status from work info work status
		//#CM32286
		// Complete if the result qty is equal or more than plan qty
		//#CM32287
		// If there is atleast one work in progress, change the status to working
		//#CM32288
		// If there is atleast one work that is started, make it started
		//#CM32289
		// except above is "partially completed"
		if ((resultQty + shortageQty) < rp.getPlanQty())
		{
			wiKey.KeyClear();
			wiKey.setPlanUkey(planUkey);
			wiKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
			wiKey.setStatusFlagCollect();
			WorkingInformation[] wi = (WorkingInformation[]) wih.find(wiKey);
			boolean existNowWorking = false;
			boolean existStart = false;
			for (int i = 0; i < wi.length; i++)
			{
				if (wi[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
				{
					existNowWorking = true;
					break;
				}
				else if (wi[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START))
				{
					existStart = true;
				}
			}
			
			if (existNowWorking)
			{
				rpAKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING);
			}
			else if (existStart)
			{
				rpAKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_START);
			}
			else
			{
				rpAKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART);
			}
		}
		else
		{
			rpAKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
		}
		rpAKey.updateResultQty(resultQty);
		rpAKey.updateShortageCnt(shortageQty);
		rpAKey.updateLastUpdatePname(CLASSNAME);
		try
		{
			rph.modify(rpAKey);
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
	
	//#CM32290
	/**
	 * While deleting carry data, update the location status based on carry data
	 * 
	 * @param conn         database connection
	 * @param ci           Carry data
	 * @param shelfNumber  location no.
	 * @throws ReadWriteException throw any abnormal database connection error
	 * @throws InvalidDefineException Throw exception if the update contents are not set
	 * @throws NotFoundException throw exception if the info for modification does not exist
	 */
	protected void updateShelf(Connection conn, CarryInformation ci, String shelfNumber)
												throws ReadWriteException,
														InvalidDefineException,
														NotFoundException
	{
		//#CM32291
		// It releases the shelves according to the carrykind of CarryInformation.
		switch (ci.getCarryKind())
		{
			//#CM32292
			// If it is a storage, it alters the receiving location to 'empty locations'.
			case CarryInformation.CARRYKIND_STORAGE:
				//#CM32293
				// It alters to empty locations only when the location at the recieving station is fixed.
				//#CM32294
				// Taking the storage data before location fix, or the possible case of no destination due
				//#CM32295
				// to double occupation or the empty retrieval into account, the destination will be determined based on the current position of the pallet.
				updateShelfPresence(conn, shelfNumber, Shelf.PRESENCE_EMPTY);
				break;
				
			//#CM32296
			// If it is a retrieval, it alters the sending location to 'empty locations'.
			case CarryInformation.CARRYKIND_RETRIEVAL:
				//#CM32297
				// If the Carry process has not proceeded to retrieval completion or error status, it should alter the sending location to 'empty'.
				if (ci.getCmdStatus() <= CarryInformation.CMDSTATUS_INSTRUCTION
				 || ci.getCmdStatus() == CarryInformation.CMDSTATUS_ERROR)
				{
					updateShelfPresence(conn, shelfNumber, Shelf.PRESENCE_EMPTY);
				}
				//#CM32298
				// If the Carry process has proceeded to completion or further, the location could possibly
				// be yet be released if the retrieval instruction detail is anything other than unit retrieval
				// and if re-storage flag is being re-stored to the same location.
				//#CM32299
				//CMENJP2389$CM If a location is not opened, change picking location to empty location
			else if ((ci.getRetrievalDetail() != CarryInformation.RETRIEVALDETAIL_UNIT)
				 && (ci.getReStoringFlag() == CarryInformation.RESTORING_SAME_LOC))
				{
					updateShelfPresence(conn, shelfNumber, Shelf.PRESENCE_EMPTY);
				}
				break;
				
			//#CM32300
			// If it is a location to location move, it alters the sending location and receiving location to 'empty locations'.
			case CarryInformation.CARRYKIND_RACK_TO_RACK:
				//#CM32301
				//Change destination location to empty location
				updateShelfPresence(conn, shelfNumber, Shelf.PRESENCE_EMPTY);

				//#CM32302
				// In case of relocation data, change destination location into empty
				updateShelPresenceForDirect(conn, ci.getDestStationNumber());
		}
	}
	
	//#CM32303
	/**
	 * Update stock while eliminating picking carry data<BR>
	 * Update method changes based on the picking instruction detail
	 * 
	 * @param conn            database connection
	 * @param winfo           work info to delete
	 * @param retrievalDetail picking instruction detail
	 * @return stock info before update
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException throw exception when modify, delete data is not available
	 * @throws InvalidDefineException Throw exception if database updation fails
	 */
	protected Stock cancelRetrievalStock(Connection conn, WorkingInformation winfo, int retrievalDetail)
																throws ReadWriteException,
																		NotFoundException,
																		InvalidDefineException
	{
		Stock stk = getStock(conn, winfo.getStockId());;

		//#CM32304
		// If replenishing,
		if (retrievalDetail == CarryInformation.RETRIEVALDETAIL_ADD_STORING)
		{
			//#CM32305
			// If loading the cargo mixed, 
			if (stk.getStockQty() == 0)
			{
				if (StringUtil.isBlank(stk.getPlanUkey()))
				{
					//#CM32306
					// Deleting from Stock table.
					stkKey.KeyClear();
					stkKey.setStockId(stk.getStockId());
					stkh.drop(stkKey);
				}
				else
				{
					stkAKey.KeyClear();
					stkAKey.setStockId(stk.getStockId());
					stkAKey.updatePlanQty(stk.getPlanQty() - winfo.getPlanEnableQty());
					stkAKey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
					stkAKey.updateLastUpdatePname(CLASSNAME);
				}
			}
			//#CM32307
			// If it is a replenishiment, 
			else
			{
				//#CM32308
				// update storage plan qty
				stkAKey.KeyClear();
				stkAKey.setStockId(stk.getStockId());
				stkAKey.updatePlanQty(stk.getPlanQty() - winfo.getPlanEnableQty());
				stkAKey.updateLastUpdatePname(CLASSNAME);
				stkh.modify(stkAKey);
			}
		}
		else
		{
			//#CM32309
			// can't update since work qty is zero during stock confirmation
			if (winfo.getPlanEnableQty() == 0)
			{
				return stk;
			}
			//#CM32310
			// If it is a retrieval,
			stkAKey.KeyClear();
			stkAKey.setStockId(stk.getStockId());
			stkAKey.updateAllocationQty(winfo.getPlanEnableQty() + stk.getAllocationQty());
			stkAKey.updateLastUpdatePname(CLASSNAME);
			stkh.modify(stkAKey);
		}
		
		return stk;
	}
	
	//#CM32311
	/**
	 * Delete work display data related to conveyance key
	 * and don't throw any error that occurs while deleting if the carry kind is "direct transfer"
	 * @param conn      database connection
	 * @param carryKey  conveyance key
	 * @param carryKind conveyance type
	 * @throws ReadWriteException Throw exception if it occurs during database access
	 * @throws NotFoundException Throw exception if there is no target data
	 */
	protected void dropOperationDisplay(Connection conn, String carryKey, int carryKind)
												throws ReadWriteException, NotFoundException
	{
		//#CM32312
		// Checks whether/not the data of on-line indication. Delete if there are any.
		OperationDisplayHandler odh = new OperationDisplayHandler(conn);
		OperationDisplaySearchKey odkey = new OperationDisplaySearchKey();
		odkey.setCarryKey(carryKey);
		if (odh.count(odkey) != 0)
		{
			//#CM32313
			// Do not throw exception during direct transfer
			if (carryKind == CarryInformation.CARRYKIND_DIRECT_TRAVEL)
			{
				try
				{
					odh.drop(odkey);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				odh.drop(odkey);
			}
		}
	}

	//#CM32314
	// Private methods -----------------------------------------------
	//#CM32315
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
	
	//#CM32316
	/**
	 * Search stock using palette id as key
	 * Return true if corresponding stock info exist
	 * Return false if corresponding stock info does not exist
	 * @param conn      database connection
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

	//#CM32317
	/**
	 * Change destination location status to empty in case of relocation<BR>
	 * Update destination station status while cancelling carry data
	 * 
	 * @param conn            connection object for database connection
	 * @param destStationNo   receiving station
	 * @throws ReadWriteException throw any database access exception
	 */
	private void updateShelPresenceForDirect(Connection conn, String destStationNo )
																throws ReadWriteException
	{
		try
		{
			Station dirst = StationFactory.makeStation(conn, destStationNo);
			if (dirst instanceof Shelf)
			{
				Shelf dirshf = (Shelf) dirst;
				if(dirshf.getPresence() == Shelf.PRESENCE_RESERVATION)
				{
					updateShelfPresence(conn, dirshf.getStationNumber(), Shelf.PRESENCE_EMPTY);
				}
			}
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
	
	//#CM32318
	/**
	 * Change the status of the location no.
	 * @param conn     database connection
	 * @param stno     location no.
	 * @param presence location status
	 * @throws ReadWriteException throw any abnormal database connection error
	 * @throws InvalidDefineException Throw exception if the update contents are not set
	 * @throws NotFoundException throw exception if the info for modification does not exist
	 */
	private void updateShelfPresence(Connection conn, String stno, int presence)
																throws ReadWriteException,
																		InvalidDefineException,
																		NotFoundException
	{
		generateShelf(conn);
		
		//#CM32319
		// Don't process if target location does not exist
		shelfKey.KeyClear();
		shelfKey.setStationNumber(stno);
		if (shelfHandl.count(shelfKey) == 0)
		{
			return;
		}
		
		shelfAKey.KeyClear();
		shelfAKey.setStationNumber(stno);
		shelfAKey.updatePresence(presence);
		shelfHandl.modify(shelfAKey);		
	}
	
	//#CM32320
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

	//#CM32321
	/**
	 * Change the stock confirmation flag to OFF in aisle info<BR>
	 * 
	 * If the instructed carry data is either stock confirmation or empty location confirmation,
	 * if stock confirmation or empty location confirmation does not exit inside same aisle,
	 * change the stock confirmation flag to standby in aisle info
	 * 
	 * @param conn   connection object for database connection
	 * @param ci     Carry data
	 * @throws ReadWriteException throw any abnormal database connection error
	 * @throws NotFoundException throw exception if the info for modification does not exist
	 */
	private void updateAisleInventoryCheckOff(Connection conn, CarryInformation ci)
																		throws  ReadWriteException,
																				NotFoundException
	{
		//#CM32322
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
				//#CM32323
				// Change the stock confirmation flag to OFF in aisle table
				updateAisleInventoryCheckFlag(conn, 
											 ci.getAisleStationNumber(), 
											 Station.NOT_INVENTORYCHECK);
			}
		}
	}
	
	//#CM32324
	/**
	 * Change the stock confirmation flag of aisle info according to instructed value<BR>
	 * 
	 * @param conn               connection object for database connection
	 * @param aileStNo           aisle station no.
	 * @param inventoryCheckFlag stock confirmation flag change value
	 * @throws ReadWriteException throw any abnormal database connection error
	 * @throws NotFoundException throw exception if the info for modification does not exist
	 */
	private void updateAisleInventoryCheckFlag(Connection conn, String aileStNo, int inventoryCheckFlag)
																		throws  ReadWriteException,
																				NotFoundException
	{
		try
		{
			AisleHandler aileh = new AisleHandler(conn);
			AisleAlterKey ak = new AisleAlterKey();

			ak.setStationNumber(aileStNo);
			ak.updateInventoryCheckFlag(inventoryCheckFlag);
			aileh.modify(ak);
		}
		catch (InvalidDefineException ie)
		{
			throw new ReadWriteException(ie.getMessage());
		}
	}

	//#CM32325
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
		//#CM32326
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
	
	//#CM32327
	/**
	 * Calculate stock work qty and update stock<BR>
	 *Complete work with shortage, if the stock quantity is not appropriate change status to complege<BR>
	 * Below center stock update process
	 * <DIR>
	 *   * Update waiting for storage status to center stock<BR>
	 *   * Add stock qty<BR>
	 *   * Subtract plan qty<BR>
	 *   * Set current date/time if storage date is not set<BR>
	 *   * Add allocation qty (in case of direct transfer don't add)<BR>
	 * </DIR>
	 * 
	 * @param conn        database connection
	 * @param workQty     work qty
	 * @param shortageQty shortage qty
	 * @param stk         target stock for update
	 * @param carryKind   Carry type of Carry data 
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException If the target stock does not exist in database, throw exception
	 * @throws InvalidDefineException Exception thrown if there is any problem during database update
	 */
	private void updateStockPlus(Connection conn, int workQty, int shortageQty, Stock stk, int carryKind)
															throws ReadWriteException,
																   NotFoundException,
																   InvalidDefineException
	{
		generateStock(conn);
		
		//#CM32328
		// seek the storage plan qty after update
		//#CM32329
		// allocate result qty from stock. if 0 or less, set 0
		int planQty = stk.getPlanQty() - (workQty + shortageQty);
		if (planQty < 0)
		{
			planQty = 0;
		}
		//#CM32330
		// seek the stock plan qty after update
		int stockQty = stk.getStockQty() + workQty;

		stkAKey.KeyClear();
		stkAKey.setStockId(stk.getStockId());
		//#CM32331
		// If the stock qty is not appropriate, consider plan as completed with shortage and change status to complete
		//#CM32332
		// if stock already exist, make it center stock
		if (stockQty == 0)
		{
			//#CM32333
			// In case of stock without a plan unique key (unplanned storage)
			//#CM32334
			// delete this
			if (!StringUtil.isBlank(stk.getPlanUkey()))
			{
				if (planQty == 0)
				{
					stkAKey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
				}
			}
			else
			{
				stkKey.KeyClear();
				stkKey.setStockId(stk.getStockId());
				stkh.drop(stkKey);
				return;
			}
		}
		//#CM32335
		// when the stock qty is inappropriate, raise into center stock and make it shortage
		//#CM32336
		// if the plan data is normal completion, change it to center stock
		//#CM32337
		// clear the plan key
		else
		{
			stkAKey.updateStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			stkAKey.updatePlanUkey(null);
		}
		stkAKey.updatePlanQty(planQty);
		stkAKey.updateStockQty(stockQty);
		
		if (carryKind != CarryInformation.CARRYKIND_DIRECT_TRAVEL)
		{
			//#CM32338
			// If direct transfer, adjust available quantity appropriately so that it is not allocated during conveyance
			stkAKey.updateAllocationQty(stk.getAllocationQty() + workQty);
		}
		
		if (StringUtil.isBlank(stk.getInstockDate()))
		{
			stkAKey.updateInstockDate(new Date());
		}
		stkAKey.updateLastUpdatePname(CLASSNAME);
		stkh.modify(stkAKey);
	}
	
	//#CM32339
	/**
	 * Subtract work qty from stock and modify stock<BR>
	 * 
	 * <DIR>
	 *   * If work qty + shortage qty = 0, don't update<BR>
	 *   * If stock qty is 0, delete stock
	 *   <DIR>
	 *     *If the palette from where stock is removed does not contain any stock, delete the palette
	 *   </DIR>
	 *    * If the remaining stock qty is 1 or more, update stock qty
	 * </DIR>
	 * 
	 * @param conn        database connection
	 * @param workQty     work qty
	 * @param shortageQty shortage qty
	 * @param stk         target stock for update
	 * @throws ReadWriteException throw any database access exception
	 * @throws NotFoundException If the target stock does not exist in database, throw exception
	 * @throws InvalidDefineException Exception thrown if there is any problem during database update
	 */
	private void updateStockMinus(Connection conn, int workQty, int shortageQty, Stock stk)
															throws ReadWriteException,
																   NotFoundException,
																   InvalidDefineException
	{
		//#CM32340
		// If work qty + shortage qty = 0, don't update
		//#CM32341
		// work qty becomes 0 during stock confirmation
		if ((workQty + shortageQty) == 0)
		{
			return;
		}
		
		//#CM32342
		// generates stock operation class instance
		generateStock(conn);
		
		//#CM32343
		// Fetch stock qty after work completion
		int stockQty = stk.getStockQty() - (workQty + shortageQty);
		
		//#CM32344
		// Delete stock if stock qty is 0
		if (stockQty <= 0)
		{
			//#CM32345
			// Delete stock
			stkKey.KeyClear();
			stkKey.setStockId(stk.getStockId());
			stkh.drop(stkKey);
			
			//#CM32346
			// Eliminate palette with no stock
			if (!isExistStock(conn, stk.getPaletteid()))
			{
				plKey.KeyClear();
				plKey.setPaletteId(stk.getPaletteid());
				plh.drop(plKey);
			}
		}
		//#CM32347
		// If the stock qty is 1 or more, update stock
		else
		{
			stkAKey.KeyClear();
			stkAKey.setStockId(stk.getStockId());
			stkAKey.updateStockQty(stockQty);
			stkAKey.updateLastUpdatePname(CLASSNAME);
			stkh.modify(stkAKey);
		}
	}
	
	//#CM32348
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
	
	//#CM32349
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
	
	//#CM32350
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

	//#CM32351
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

	//#CM32352
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

	//#CM32353
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
	
	//#CM32354
	/**
	 * generate location info operation class
	 * don't recreate if instance already exists
	 * @param conn database connection
	 */
	private void generateShelf(Connection conn)
	{
		if (shelfHandl == null)
			shelfHandl = new ShelfHandler(conn);

		if (shelfKey == null)
			shelfKey = new ShelfSearchKey();

		if (shelfAKey == null)
			shelfAKey = new ShelfAlterKey();		
	}
	
	//#CM32355
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
	
	//#CM32356
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
	
	//#CM32357
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
	
	//#CM32358
	/**
	 * generate picking plan info operation class
	 * don't recreate if instance already exists
	 * @param conn database connection
	 */
	private void generateRetrievalPlan(Connection conn)
	{
		if (rph == null)
			rph = new RetrievalPlanHandler(conn);
		if (rpKey == null)
			rpKey = new RetrievalPlanSearchKey();
		if (rpAKey == null)
			rpAKey = new RetrievalPlanAlterKey();
	}
}
//#CM32359
//end of class

