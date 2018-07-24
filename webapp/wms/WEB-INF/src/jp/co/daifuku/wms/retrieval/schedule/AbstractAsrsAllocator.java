//#CM722216
//$Id: AbstractAsrsAllocator.java,v 1.3 2007/02/07 04:19:50 suresh Exp $
package jp.co.daifuku.wms.retrieval.schedule;

//#CM722217
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.dbhandler.ASStockHandler;
import jp.co.daifuku.wms.asrs.dbhandler.ASWorkPlaceHandler;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.WorkPlace;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM722218
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * To include the AS/RS inventory in the target when allocating the picking, inherit this class and execute the allocation process. <BR>
 * <BR>
 * Once generated this class, generate the Transport info using the same Schedule No. <BR>
 * <BR>
 * <B>Generate a Picking Instruction Details with allocation in progress, as a Pick-up retrieval. <BR>
 * After completing allocations of all Works, ensure to use the method (<CODE>decideCarryInfo()</CODE>) for updating Transport Info, and
 * commit the Picking instruction Details and Re-storage flag. Determine the station. </B><BR>
 * <BR>
 * Allow this class to override the method of the <CODE>AbstractAllocator</CODE> class. <BR>
 * Differences between this allocation process and Flat Storage allocation process are as follows: <BR>
 * 1.To obtain the target inventory, take account of the status of the Location and the Pallet linked to the inventory. <BR>
 * 2. Allocating AS/RS inventory generates a Transport info. <BR>
 * 3. Allocating AS/RS inventory updates the status in the Work Status Info. 
 * <DIR>
 *   Status flag: "Started" <BR>
 *   Start Work flag: Standby <BR>
 * </DIR>
 * 4. Check the Transport route for the allocated inventory. <BR>
 * <DIR>
 *   Disable to allocate such inventory that was found to be NG by route check 
 * </DIR>
 * 
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/02/27</TD><TD>Y.Okamura</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:50 $
 * @author  $Author: suresh $
 */
public abstract class AbstractAsrsAllocator extends AbstractAllocator
{
	//#CM722219
	// Class variables -----------------------------------------------
	//#CM722220
	/**
	 * Transport info handler 
	 */
	protected CarryInformationHandler wCiHandler = null;
	
	//#CM722221
	/**
	 * Transport info search key 
	 */
	protected CarryInformationSearchKey wCiKey = null;
	
	//#CM722222
	/**
	 * Transport info Update key 
	 */
	protected CarryInformationAlterKey wCarryAlterKey = null;

	//#CM722223
	/**
	 * Pallet handler 
	 */
	protected PaletteHandler wPltHandler = null;
	
	//#CM722224
	/**
	 * Pallet search key 
	 */
	protected PaletteSearchKey wPltKey = null;
	
	//#CM722225
	/**
	 * Pallet Update key 
	 */
	protected PaletteAlterKey wPltAltKey = null;
	
	//#CM722226
	/**
	 * Shelf handler 
	 */
	protected ShelfHandler wSlfHandler = null;
	
	//#CM722227
	/**
	 * Shelf search key 
	 */
	protected ShelfSearchKey wSlfKey = null;
	
	//#CM722228
	/**
	 * Station handler 
	 */
	protected StationHandler wStHandler = null;
	
	//#CM722229
	/**
	 * Station search key 
	 */
	protected StationSearchKey wStKey = null;

	//#CM722230
	/**
	 * AS/RS inventory handler 
	 */
	protected ASStockHandler wAsStkHandler = null;
	
	//#CM722231
	/**
	 * AS/RS work place handler 
	 */
	protected ASWorkPlaceHandler wpHandler = null;
	
	//#CM722232
	/**
	 * Transport route check class 
	 */
	protected RouteController wrc = null;
	
	//#CM722233
	/**
	 * Schedule No. (Number from the Sequence object) 
	 */
	protected String wSchNo_seq = null;
	
	//#CM722234
	// Class method --------------------------------------------------
	//#CM722235
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:50 $");
	}
	
	//#CM722236
	// Constructors --------------------------------------------------
	//#CM722237
	/**
	 * Initialize this class. 
	 * Generate handler and similar, and the check class for Transport route respectively to be used to allocate AS/RS,
	 * Schedule No. to be given for the Transport info by this Setting Unit. 
	 * @param conn Database connection
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	public AbstractAsrsAllocator(Connection conn) throws ScheduleException, ReadWriteException
	{
		super(conn);
		
		wCiKey = new CarryInformationSearchKey();
		wCiHandler = new CarryInformationHandler(conn);
		wCarryAlterKey = new CarryInformationAlterKey();

		wPltHandler = new PaletteHandler(conn);
		wPltKey = new PaletteSearchKey();
		wPltAltKey = new PaletteAlterKey();
		
		wSlfHandler = new ShelfHandler(conn);
		wSlfKey = new ShelfSearchKey();
		
		wStKey = new StationSearchKey();
		wStHandler = new StationHandler(conn);

		wAsStkHandler = new ASStockHandler(conn);
		wpHandler = new ASWorkPlaceHandler(conn);
		wrc = new RouteController(conn);
		wSchNo_seq = wSeqHandler.nextScheduleNumber(); 
	}
	
	//#CM722238
	// Public methods ------------------------------------------------

	//#CM722239
	// Protected methods ---------------------------------------------
	//#CM722240
	/**
	 * Allocate the Inventory Info and generate its Work Status Info. <BR>
	 * Subtract the allocated qty from the Inventory Info, and generate a Work Status Info of the allocated data. <BR>
	 * Close this process when completing all the allocation for the requested qty. 
	 * Return the remaining qty if failed to allocate some of the requested qty. <BR>
	 * Example) 
	 * <DIR>
	 *     Requested allocation qty : 100<BR>
	 *     Total Stock Qty : 80<BR>
	 *     Returned value: 20 <BR>
	 * </DIR>
	 * 
	 * For inventory in Automated Warehouse, check the route and generate the transport datawhen it is the inventory in the <BR>
	 * Disable to allocate such inventory that was found to be NG by route check 
	 * 
	 * @param pStocks Allocation target Inventory Info 
	 * @param pAllocateQty Requested allocation qty 
	 * @param pPlan Picking Plan Info to be allocated
	 * @param pWorkFormFlag Work Type to be generated here this time 
	 * @return Remaining allocation qty 
	 * @throws ReadWriteException Announce when error occurs whcn accessing to the database. 
	 * @throws InvalidDefineException Announce when the abnormal information is defined. 
	 * @throws NotFoundException Announce when there is no corresponding data, 
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected int allocateStock(Stock[] pStocks, 
								int pAllocateQty, 
								RetrievalPlan pPlan, 
								String pWorkFormFlag)
								throws ReadWriteException, 
										ScheduleException
	{
		try
		{
			//#CM722241
			// Remaining allocation qty 
			int remainingQty = pAllocateQty;
			String jobNo = null;
			String mcKey = null;
			for (int i = 0; i < pStocks.length; i++)
			{
				//#CM722242
				// Initialize Work No. and Transport Key. 
				jobNo = null;
				mcKey = null; 
				
				//#CM722243
				// For inventory of AS/RS, generate a Transport data. 
				if (getAreaType(pStocks[i].getAreaNo()) == Area.SYSTEM_DISC_KEY_ASRS)
				{
					//#CM722244
					// For inventory other than AS/RS, generate a Transport data and obtain the Transport Key. 
					mcKey = createCarryinfo(pStocks[i]);
					
					//#CM722245
					// If failed to obtain the Transport Key, that is, 
					//#CM722246
					// If failed to generate a transport data, allocate the following inventory.
					if (StringUtil.isBlank(mcKey))
					{
						continue;
					}
					
					try
					{
						//#CM722247
						// Update the Pallet to the "Picking Reserved". 
						wPltAltKey.KeyClear();
						wPltAltKey.setPaletteId(pStocks[i].getPaletteid());
						int[] pltStatus = {Palette.REGULAR, Palette.IRREGULAR};
						wPltAltKey.setStatus(pltStatus);
						wPltAltKey.updateStatus(Palette.RETRIEVAL_PLAN);				
						wPltAltKey.updateAllocation(Palette.ALLOCATED);
						wPltHandler.modify(wPltAltKey);
					}
					catch (NotFoundException e)
					{
						//#CM722248
						// If the inventory is reserved for picking or under working on picking, regard it as no target but disable to trigger error. 
					}
				}
				
				//#CM722249
				// Compute the allocatable inventory qty for this inventory. 
				int allocateQty = 0;
				if (pStocks[i].getAllocationQty() >= remainingQty)
				{
					allocateQty = remainingQty;
				}
				else
				{
					allocateQty = pStocks[i].getAllocationQty();
				}
				remainingQty -= allocateQty;
					
				//#CM722250
				// Update the inventory. 
				//#CM722251
				// Subtract the currently allocated qty from the Allocatable Qty. 
				wStkAltKey.KeyClear();
				wStkAltKey.setStockId(pStocks[i].getStockId());
				wStkAltKey.updateAllocationQty(pStocks[i].getAllocationQty() - allocateQty);
				wStkAltKey.updateLastUpdatePname(CLASS_NAME);
				wStkHandler.modify(wStkAltKey);
					
				//#CM722252
				// Generate the Work Status Info. 
				jobNo = createWorkInfo(pPlan, pStocks[i], allocateQty, pWorkFormFlag);
				
				//#CM722253
				// If Transport data exists, update the Work Status Info. 
				if (!StringUtil.isBlank(mcKey))
				{
					//#CM722254
					// Status flag ("Started") 
					//#CM722255
					// Start Work flag (Standby) 
					wWiAltKey.KeyClear();
					wWiAltKey.setJobNo(jobNo);
					wWiAltKey.updateAreaNo(pStocks[i].getAreaNo());
					wWiAltKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
					wWiAltKey.updateBeginningFlag(WorkingInformation.BEGINNING_FLAG_NOT_STARTED);
					wWiAltKey.updateSystemConnKey(mcKey);
					wWiAltKey.updateSystemDiscKey(WorkingInformation.SYSTEM_DISC_KEY_ASRS);
					wWiAltKey.updateWorkerCode(getWorkerCode());
					wWiAltKey.updateWorkerName(getWorkerName());
					wWiAltKey.updateTerminalNo(getTerminalNo());
					wWiAltKey.updateLastUpdatePname(CLASS_NAME);
					wWiHandler.modify(wWiAltKey);					
				}
				
				//#CM722256
				// Completing allocations closes the inventory loop. 
				if (remainingQty == 0)
				{
					break;
				}
			}
			
			return remainingQty;
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
	
	//#CM722257
	/**
	 * Check whether the inventory is allocatable or not. <BR>
	 * Return true if the inventory is allocatable. <BR>
	 * Disable to check in the case of inventory in Flat Storage or Relocation rack. <BR>
	 * If the linked location is Inaccessible location or Restricted location, return false. <BR>
	 * Check the transport route. 
	 * Return false if the route check result is NG. <BR>
	 * 
	 * @param pStock Inventory Info 
	 * @return Determine whether the inventory is allocatable or not. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected boolean canTransport(Stock pStock) throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM722258
			// If the linked location is Inaccessible location or Restricted location, disable to allocate. 
			wSlfKey.KeyClear();
			wSlfKey.setStationNumber(pStock.getLocationNo());
			wSlfKey.setStatus(Shelf.STATUS_NG, "=", "(", "", "OR");
			wSlfKey.setAccessNgFlag(Shelf.ACCESS_NG, "=", "", ")", "AND");
			if (wSlfHandler.count(wSlfKey) > 0)
			{
				return false;
			}
			
			//#CM722259
			// Obtain the Pallet Info from the Pallet ID of the Inventory Info.
			Palette palette = (Palette) getPaletteData(pStock.getPaletteid());
			//#CM722260
			// Obtain the Work place (Station) information from the Area No. of Inventory Info.
			Station workStation = (Station) getWorkStation(pStock.getAreaNo());
			
			//#CM722261
			// Execute the following processes if error found by checking route 
			if(!wrc.retrievalDetermin(palette, workStation))
			{
				return false;
			}
			else
			{
				return true;
			}			
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

	}

	//#CM722262
	/**
	 * Execute addition of Transport info table (CARRYINFO) or updating.  <BR> 
	 * Return the Transport Key of the Transport info to carry the inventory as argument. <BR>
	 * If Transport info has been already generated for the pallet within this allocation here, aggregate the work. 
	 * When there is no Transport info for the Pallet, execute Transport route check 
	 * and create newly if it is acceptable. <BR>
	 * <B>As for Picking Instruction Details and Re-storage flag, generate them temporarily as Pick-up retrieval and Re-storage enabled. 
	 * After generating all the Transport info, 
	 * require to invoke the <CODE>decideCarry</CODE> method in order to commit the detailed Picking instructions and Re-storage flag.</B>
	 * <BR>
	 * @param pStock Inventory Info instance 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected error occurs. 
	 */
	protected String createCarryinfo(Stock pStock) throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM722263
			// Search for the Transport info generated by this allocation to check for its presence using Schedule No. + Location No. 
			wCiKey.KeyClear();
			wCiKey.setScheduleNumber(wSchNo_seq);
			wCiKey.setPaletteId(pStock.getPaletteid());

			if (wCiHandler.count(wCiKey) > 0)
			{
				//#CM722264
				// If Transport info already exists for the same location as this allocation target here: 
				wCiKey.setCarryKeyCollect();
				CarryInformation ci = (CarryInformation) wCiHandler.findPrimary(wCiKey);
				
				return ci.getCarryKey();
				
			}
			else
			{
				//#CM722265
				// Execute transport route check 
				//#CM722266
				// If not allocatable, skip this inventory and allocate the next inventory. 
				if (!canTransport(pStock))
				{
					return null;
				}
				
				//#CM722267
				// Add a new Transport info. 
				//#CM722268
				// To update Picking Instruction Details and Re-storage flag after completing allocations of all works, 
				//#CM722269
				// generate them here temporarily as Pick-up retrieval and Re-storage enabled. 
				CarryInformation carryInfo = new CarryInformation();
				//#CM722270
				// Obtain each unique key to add. 
				String mcKey_seq = wSeqHandler.nextCarryKey() ;
				//#CM722271
				// Transport Key 
				carryInfo.setCarryKey(mcKey_seq);
				//#CM722272
				// Pallet ID 
				carryInfo.setPaletteId(pStock.getPaletteid());
				//#CM722273
				// Generated Date 
				carryInfo.setCreateDate(new Date());
				//#CM722274
				// Work Type 
				if (getFunctionName().equals(RetrievalSupportParameter.FUNCTION_NAME_ITEM_SCH))
				{
					carryInfo.setWorkType(CarryInformation.WORKTYPE_PLAN_ITEM_RETRIEVAL);
				}
				else
				{
					carryInfo.setWorkType(CarryInformation.WORKTYPE_PLAN_ORDER_RETRIEVAL);
				}
				//#CM722275
				// Picking group No. 
				carryInfo.setGroupNumber(0);
				//#CM722276
				// Transport Status 
				carryInfo.setCmdStatus(CarryInformation.CMDSTATUS_START);
				//#CM722277
				// Preferential division 
				carryInfo.setPriority(CarryInformation.PRIORITY_NORMAL);
				//#CM722278
				// Re-storage flag 
				carryInfo.setReStoringFlag(CarryInformation.RESTORING_SAME_LOC);
				//#CM722279
				// Picking Instruction Details 
				carryInfo.setRetrievalDetail(CarryInformation.RETRIEVALDETAIL_PICKING);
				//#CM722280
				// Transport division 
				carryInfo.setCarryKind(CarryInformation.CARRYKIND_RETRIEVAL);
				//#CM722281
				// Picking Location No. 
				carryInfo.setRetrievalStationNumber(pStock.getLocationNo());
				//#CM722282
				// Work No.
				String workNo_seq = wSeqHandler.nextRetrievalWorkNumber();
				carryInfo.setWorkNumber(workNo_seq);
				//#CM722283
				// transport source station No. 
				carryInfo.setSourceStationNumber(pStock.getLocationNo());
				//#CM722284
				// Transport destination station No. 
				carryInfo.setDestStationNumber(wrc.getDestStation().getStationNumber());
				//#CM722285
				// Cancel Request Division 
				carryInfo.setCancelRequest(CarryInformation.CANCELREQUEST_UNDEMAND);
				//#CM722286
				// Schedule No. 
				carryInfo.setScheduleNumber(wSchNo_seq);
				//#CM722287
				// Aisle Station No. 
				//#CM722288
				// Obtain the Station No. (S/R machine) of the transport source station (Aisle). 
				carryInfo.setAisleStationNumber(wrc.getSrcStation().getStationNumber());
				//#CM722289
				// End Station No. 
				carryInfo.setEndStationNumber(wrc.getDestStation().getStationNumber());

				//#CM722290
				// Add the Transport info.
				wCiHandler.create(carryInfo);

				return mcKey_seq;
			}			
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
	}

	//#CM722291
	/**
	 * Obtain the Picking Work place (Station) information using the Area No. <BR>
	 * 
	 * @param pWHStNo Warehouse station No. 
	 * @return rStation Station information instance 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected Station getWorkStation(String pWHStNo)
		throws ReadWriteException, ScheduleException
	{
		//#CM722292
		// Search for the station linked to the Work place. 
		//#CM722293
		// Obtain the whole work place of the designated warehouse. 
		wStKey.KeyClear();
		wStKey.setWHStationNumber(pWHStNo);
		wStKey.setParentStationNumber("");
		wStKey.setWorkPlaceType(Station.NOT_WORKPLACE, "!=");
		
		//#CM722294
		// Definition error if whole work place is not defined for a Warehouse. 
		if (wpHandler.count(wStKey) == 0)
		{
			//#CM722295
			// Log definition errors.
			//#CM722296
			// 6026062=Definition error. All the workplaces at assigned warehouse does not exist. warehouse = {0} 
			RmiMsgLogClient.write(("6026062" + wDelim + pWHStNo), this.getClass().getName()) ;
			throw new ScheduleException();
		}
			
		WorkPlace[] wp = (WorkPlace[]) wpHandler.find(wStKey);

		return wp[0];
	}

	//#CM722297
	/**
	 * Obtain the Pallet information. <BR>
	 * Search through the Pallet info using the Pallet ID as a key. 
	 * Announce ScheduleException when target pallet was not found. 
	 * 
	 * @param pPltId Pallet ID 
	 * @return Pallet information instance 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected Palette getPaletteData(int pPltId)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			wPltKey.KeyClear();
			wPltKey.setPaletteId(pPltId);
	
			Palette plt = (Palette) wPltHandler.findPrimary(wPltKey);
			if (plt == null)
			{
				//#CM722298
				// Log definition errors.
				//#CM722299
				// 6026063=There is no designated pallet. Pallet ID={0} 
				RmiMsgLogClient.write(("6026063" + wDelim + pPltId), this.getClass().getName()) ;
				throw new ScheduleException();
			}
			return plt;			
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}	
	}

	//#CM722300
	/**
	 * Update the Picking destination station, the detailed Picking instructions, and the Re-storage flag of the Transport info. <BR>
	 * 
	 * [Detailed Picking Instruction] 
	 * <DIR>
	 *   1.If the allocatable qty on Pallet is 0: 
	 *   <DIR>
	 *     a.Unit retrieval if the Picking destination station is a station available for retrieval. <BR>
	 *     b.Pick-up retrieval if the Picking destination station is a station unavailable for retrieval. 
	 *   </DIR>
	 *   2.If the allocatable qty on Pallet is 1 or more: 
	 *   <DIR>
	 *     a.Unit retrieval if the Picking destination station is a station exclusive for retrieval. <BR>
	 *     b. Otherwise: Pick-up retrieval 
	 *   </DIR>
	 * </DIR>
	 * [Re-storage flag] 
	 * <DIR>
	 *   1.For data with Picking Instruction Details Unit retrieval: 
	 *   <DIR>
	 *     Re-storage disabled
	 *   </DIR>
	 *   2.For data with Picking Instruction Details Pick-up retrieval: 
	 *   <DIR>
	 *     a. Re-storage disabled if the sending state of Transport instruction for Re-storage is Sent<BR>
	 *     b. Otherwise: "Re-storage enabled" 
	 *   </DIR>
	 * </DIR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void decideCarryInfo() throws ReadWriteException
	{
		try
		{
			//#CM722301
			// Obtain the Transport info scheduled this time here. 
			wCiKey.KeyClear();
			wCiKey.setScheduleNumber(wSchNo_seq);
			wCiKey.setPaletteIdCollect();
			wCiKey.setCarryKeyCollect();
			wCiKey.setDestStationNumberCollect();

			CarryInformation[] cinfos = (CarryInformation[]) wCiHandler.find(wCiKey);

			for (int i = 0; i < cinfos.length; i++)
			{
				//#CM722302
				// Obtain the Allocatable qty on Pallet. 
				wStkKey.KeyClear();
				wStkKey.setPaletteid(cinfos[i].getPaletteId());
				wStkKey.setPaletteidGroup(1);
				wStkKey.setLocationNoGroup(2);
				wStkKey.setAllocationQtyCollect("SUM");
				wStkKey.setLocationNoCollect();
				Stock stk = (Stock) wStkHandler.findPrimary(wStkKey);
				int totalAllocatableQty = stk.getAllocationQty();

				//#CM722303
				// Generate the Transport destination station. 
				Station destSt = StationFactory.makeStation(wConn, cinfos[i].getDestStationNumber());
				
				boolean isPickRetrieval = false;
				boolean isUnitRetrieval = false;
				if (totalAllocatableQty == 0)
				{
					//#CM722304
					// For Unit retrieval with Allocated qty 0: 
					isUnitRetrieval = true;
				}
				else
				{
					//#CM722305
					// For Pick-up retrieval with Allocated qty other than 0 
					isPickRetrieval = true;
				}

				//#CM722306
				// Determine the Picking Instruction Details. 
				int retrievalDetail = -1;
				int reStoringFlag = -1;
				if (isUnitRetrieval)
				{
					//#CM722307
					// For data with Picking "All qty" 
					if (!destSt.isRemove())
					{
						//#CM722308
						// If working on Piking all qty at the station not available to picking, execute Pick-up retrieval.
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
						//#CM722309
						// If working on Picking at the Picking-only station, regard it as unit retrieval. 
						retrievalDetail = CarryInformation.RETRIEVALDETAIL_UNIT;
					}
					else
					{
						retrievalDetail = CarryInformation.RETRIEVALDETAIL_PICKING;
					}
				}
				
				//#CM722310
				// Determine the Re-storage flag based on the Picking Instruction Details. 
				if (retrievalDetail == CarryInformation.RETRIEVALDETAIL_UNIT)
				{
					//#CM722311
					// Regard it as "Re-storage disabled" for unit work
					reStoringFlag = CarryInformation.RESTORING_NOT_SAME_LOC ;
				}
				else
				{
					//#CM722312
					// When working on Picking: 
					if (destSt.getReStoringInstruction() == Station.AWC_STORAGE_SEND)
					{
						//#CM722313
						// For the station where the re-storage data is generated with eWN, regerd it as Re-storage disabled. 
						reStoringFlag = CarryInformation.RESTORING_NOT_SAME_LOC ;
					}
					else
					{
						//#CM722314
						// For the station where eWN does not generate any re-storage data, regard it as Re-storage in the same location. 
						reStoringFlag = CarryInformation.RESTORING_SAME_LOC ;
					}
				}
				
				//#CM722315
				// Update the Transport info using the data content above stated. 
				wCarryAlterKey.KeyClear();
				wCarryAlterKey.setCarryKey(cinfos[i].getCarryKey());
				wCarryAlterKey.updateRetrievalDetail(retrievalDetail);
				wCarryAlterKey.updateReStoringFlag(reStoringFlag);
				wCiHandler.modify(wCarryAlterKey);
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
	
	//#CM722316
	/**
	 * Update the allocated Picking Plan Info. <BR>
	 * Update the schedule flag and status flag, or the Shortage Qty and Status flag if shortage. <BR>
	 * Update conditions as follows: <BR>
	 * <BR>
	 * [Schedule flag ] 
	 * <DIR>
	 * 1.For Plan data with division other than Mixed: <BR>
	 *   Completed <BR>
	 * 2.For Plan data with Mixed and the schedule flag other than Standby: <BR>
	 *   Completed <BR>
	 * 3.For Plan data with Mixed and the schedule flag Standby: <BR>
	 *   a. For data that is going to be allocated this time here: <BR>
	 *     Case Completed <BR>
	 *   b. Otherwise: <BR>
	 *     Piece Completed <BR>
	 * </DIR>
	 * [Status flag] <BR>
	 * <DIR>
	 * 1. If status flag is Working and the work data contains any data: <BR>
	 *   Working <BR>
	 * </DIR>
	 * <BR>
	 * Unless shortage occurs, disable to execute the following processes. <BR>
	 * <DIR>
	 * 2. Total of Result qty in the Picking Plan Info + Shortage Qty + Shortage Qty this time here is equal to the Work Planned qty: <BR>
	 *   Completed <BR>
	 * 3. Otherwise: <BR>
	 *   Partially Completed <BR>
	 * </DIR>
	 * [Shortage Qty] <BR>
	 * <DIR>
	 * Shortage Qty in the Picking Plan Info + Shortage Qty this time here 
	 * </DIR>
	 * @param pPlan Picking Plan Info to be allocated
	 * @param pWorkFormFlag Work Type
	 * @param shortageQty Shortage Qty
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void allocateRetrievalPlan(RetrievalPlan pPlan, 
										String pWorkFormFlag,
										int pShortageQty) throws ReadWriteException
	{
		try
		{
			wPlanAltKey.KeyClear();
			wPlanAltKey.setRetrievalPlanUkey(pPlan.getRetrievalPlanUkey());
			//#CM722317
			// For Plan data with division other than Mixed, update the schedule flag to Completed. 
			if (!pPlan.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_MIX))
			{
				wPlanAltKey.updateSchFlag(RetrievalPlan.SCH_FLAG_COMPLETION);
			}
			else
			{
				if (!pPlan.getSchFlag().equals(RetrievalPlan.SCH_FLAG_UNSTART))
				{
					//#CM722318
					// For Plan data with division Mixed of which Case or Piece has been already allocated, update the status to Allocation Completed. 
					wPlanAltKey.updateSchFlag(RetrievalPlan.SCH_FLAG_COMPLETION);
				}
				else
				{
					//#CM722319
					// For data with no designated Work Type, regard its allocation as Completed.
					if (pWorkFormFlag == null)
					{
						wPlanAltKey.updateSchFlag(RetrievalPlan.SCH_FLAG_COMPLETION);
					}
					//#CM722320
					// If the current allocation is Case, regard the Case allocation as Completed. 
					else if (pWorkFormFlag.equals(WorkingInformation.CASEPIECE_FLAG_CASE))
					{
						wPlanAltKey.updateSchFlag(RetrievalPlan.SCH_FLAG_CASE_COMPLETION);
					}
					//#CM722321
					// If the current allocation is Piece, regard the Piece allocation as Completed. 
					else
					{
						wPlanAltKey.updateSchFlag(RetrievalPlan.SCH_FLAG_PIECE_COMPLETION);
					}
				}
			}
			
			//#CM722322
			// Sum-up the Shortage Qty. (If no Shortage, sum-up 0) 
			wPlanAltKey.updateShortageCnt(pPlan.getShortageCnt() + pShortageQty);

			//#CM722323
			// Update the Plan data with status flag other than Working (its work is occupied by other terminal). 
			if (!pPlan.getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_NOWWORKING))
			{
				//#CM722324
				// If the corresponding work data includes data with status Working, update the Picking Plan Info to Working, too. 
				wWiKey.KeyClear();
				wWiKey.setPlanUkey(pPlan.getRetrievalPlanUkey());
				wWiKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
				if (wWiHandler.count(wWiKey) > 0)
				{
					wPlanAltKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING);
				}
				//#CM722325
				// For data with "Shortage" 
				else if (pShortageQty > 0)
				{
					//#CM722326
					// Keep the data with status "Started" as it is. 
					if (!pPlan.getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_START))
					{
						//#CM722327
						// Determine, based on the quantity, whether it is "Partially Completed" or "Completed". 
						if ((pPlan.getResultQty() + pPlan.getShortageCnt() + pShortageQty) == pPlan.getPlanQty())
						{
							wPlanAltKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
						}
						else
						{
							wPlanAltKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART);
						}
					}
				}
			}
			wPlanAltKey.updateLastUpdatePname(CLASS_NAME);
			wPlanHandler.modify(wPlanAltKey);
		
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
	
	//#CM722328
	// Private methods -----------------------------------------------
}
//#CM722329
//end of class
