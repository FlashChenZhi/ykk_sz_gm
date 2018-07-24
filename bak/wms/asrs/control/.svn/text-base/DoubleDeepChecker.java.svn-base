// $Id: DoubleDeepChecker.java,v 1.2 2006/10/26 02:15:53 suresh Exp $
package jp.co.daifuku.wms.asrs.control;

//#CM32360
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

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

import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.base.entity.InOutResult;
import jp.co.daifuku.wms.asrs.location.CombineZoneSelector;
import jp.co.daifuku.wms.asrs.entity.DoubleDeepShelf;
import jp.co.daifuku.wms.asrs.location.RackToRackMoveSelector;
import jp.co.daifuku.wms.asrs.location.ShelfSelector;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.ZoneSelector;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.BankSelect;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM32361
/**
 * Check storage condition check, picking condition check for double deep application
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/04/21</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/07/28</TD><TD>M.INOUE</TD><TD>Add double deep target</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 02:15:53 $
 * @author  $Author: suresh $
 */
public class DoubleDeepChecker
{
	//#CM32362
	// Class fields --------------------------------------------------

	//#CM32363
	// Class variables -----------------------------------------------
	//#CM32364
	/**
	 * connection object to connect with database
	 */
	protected Connection wConn = null;

	//#CM32365
	/**
	 *handler class for conveyance data
	 */
	protected CarryInformationHandler wCarryInformationHandler = null;

	//#CM32366
	/**
	 * handler class for station data
	 */
	protected StationHandler wStationHandler = null;

	//#CM32367
	/**
	 * handler class for palette data
	 */
	PaletteHandler wPaletteHandler = null;

	//#CM32368
	/**
	 *used in LogWrite in case of exception
	 */
	protected StringWriter wSW = new StringWriter();

	//#CM32369
	/**
	 *used in LogWrite in case of exception
	 */
	protected PrintWriter  wPW = new PrintWriter(wSW);

	//#CM32370
	/**
	 * delimiter to used in MessageDef message parameter in case of exception
	 */
	public String wDelim = MessageResource.DELIM ;

	//#CM32371
	// Class method --------------------------------------------------
	//#CM32372
	/**
	 * returns the version of this class
	 * @return version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 02:15:53 $") ;
	}

	//#CM32373
	// Constructors --------------------------------------------------
	//#CM32374
	/**
	 * Create <code>DoubleDeepChecker</code> class instance
	 * @param conn Database connection info
	 */
	public DoubleDeepChecker(Connection conn)
	{
		//#CM32375
		// Create handler instance
		wConn = conn;
		wCarryInformationHandler = new CarryInformationHandler(wConn) ;
		wStationHandler = new StationHandler(wConn);
		wPaletteHandler = new PaletteHandler(wConn);
	}

	//#CM32376
	// Public methods ------------------------------------------------
	//#CM32377
	/**
	 * Conveyance instruction check
	 * Check whether conveyance instruction check is possible with <code>DoubleDeepShelf</code> instance
	 * if conveyance instruction is not possible, change the receiving station no. of Carry Information to warehouse station no and
	 * change the location no. of the instructed receiving station no. to empty location
	 * If conveyance instruction is possible, return true. else false
	 * @param  cInfo			Conveyance target CarryInformation instance
	 * @param  dShelf			DoubleDeepShelf instance that refers to conveyance location no.
	 * @return 				true if conveyance instruction is possible. else false
	 * @throws ReadWriteException Throw exception if database read fails
	 * @throws InvalidDefineException Throw exception if the modify contents are illegal
	 */
	public boolean storageCheck(CarryInformation cInfo, Shelf shf) throws ReadWriteException, InvalidDefineException, ScheduleException
	{
		Shelf pairShelf = null;
		
		//#CM32378
		// If the conveyance destination is double deep type location, check location status
		if (shf instanceof DoubleDeepShelf)
		{
			DoubleDeepShelf dShelf = (DoubleDeepShelf)shf;
			//#CM32379
			// If the conveyance destination is front side, process it according to depth side location
			if (dShelf.getSide() == BankSelect.NEAR)
			{
				//#CM32380
				// lock so that another task won't update
				ShelfHandler sHandle = new ShelfHandler(wConn);
				ShelfSearchKey sKey = new ShelfSearchKey();
				sKey.setStationNumber(dShelf.getStationNumber());
				Shelf[] shfArray = (Shelf[])sHandle.findForUpdate(sKey);
			
				try
				{
					pairShelf = (Shelf)StationFactory.makeStation(wConn, dShelf.getPairStationNumber());
				}
				catch (SQLException e)
				{
					throw new ReadWriteException(e.getMessage());
				}
				catch (NotFoundException e)
				{
					throw new InvalidDefineException(e.getMessage());
				}
				
				//#CM32381
				// OP side is empty location
				if (pairShelf.getPresence() == Shelf.PRESENCE_EMPTY)
				{
					//#CM32382
					// OP side is inaccessible
					if (pairShelf.getAccessNgFlag() == Shelf.ACCESS_NG)
					{
						//#CM32383
						// Open an allocated shelf
						releaseShelf(cInfo, dShelf);
						return false;
					}
				}
				
				//#CM32384
				// Read storage, picking data CarryInformation from the pair deep location
				//#CM32385
				// If not found, change to current palette and allow storage
				//#CM32386
				// Read storage carry data using pair location (deep location) station no.
				CarryInformationSearchKey ckey = new CarryInformationSearchKey();
				ckey.setCarryKind(CarryInformation.CARRYKIND_STORAGE);
				ckey.setDestStationNumber(dShelf.getPairStationNumber());
				CarryInformation[] carryArray = (CarryInformation[])wCarryInformationHandler.find(ckey);
				
				//#CM32387
				// to search Palette
				PaletteHandler paletteHandelr = new PaletteHandler(wConn);
				PaletteSearchKey paletteKey = new PaletteSearchKey();
				
				//#CM32388
				// to search WorkInfo
				WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(wConn);
				WorkingInformationSearchKey workInfoKey = new WorkingInformationSearchKey();
				
				//#CM32389
				// to store CarryInfo
				Vector carryVec = new Vector();
				
				for (int i = 0; i < carryArray.length; i++)
				{
					//#CM32390
					// set Palette search key
					paletteKey.KeyClear();
					paletteKey.setPaletteId(carryArray[i].getPaletteId());
					
					//#CM32391
					// set WorkInfo search key
					workInfoKey.KeyClear();
					workInfoKey.setSystemConnKey(carryArray[i].getCarryKey());
					
					//#CM32392
					// search
					if (paletteHandelr.count(paletteKey) > 0 && workInfoHandler.count(workInfoKey) > 0)
					{
						carryVec.add(carryArray[i]);
					}	
				}
				
				if (carryVec.size() > 0)
				{
					//#CM32393
					// If storage carry data exist in deep location, keep it until the work is complete
					//#CM32394
					// Change the current carry instruction to NG
					//#CM32395
					// Open an allocated shelf
					releaseShelf(cInfo, dShelf);
					return false;
				}
				
				//#CM32396
				// Read picking, relocation carry data from the deep location station no.
				//#CM32397
				// Decide contents of Carry data related to a pair shelf based on carry data instruction				
				//#CM32398
				// to search CarryInfo
				CarryInformationSearchKey carryKey = new CarryInformationSearchKey();
				
				//#CM32399
				// set search key
				carryKey.KeyClear();
				carryKey.setCarryKind(CarryInformation.CARRYKIND_RETRIEVAL, "=", "(", "", "or");
				carryKey.setCarryKind(CarryInformation.CARRYKIND_RACK_TO_RACK, "=", "", ")", "and");
				carryKey.setSourceStationNumber(pairShelf.getStationNumber());
				carryKey.setCmdStatusOrder(1, false);
				carryKey.setCarryKindOrder(2, true);
				carryKey.setCreateDateOrder(3, true);
				
				if (wCarryInformationHandler.count(carryKey) > 0)
				{
					CarryInformation[] farCarry = (CarryInformation[])wCarryInformationHandler.find(carryKey);
					
					//#CM32400
					// to store CarryInfo
					carryVec.clear();
				
					for (int i = 0; i < farCarry.length; i++)
					{
						//#CM32401
						// set Palette search key
						paletteKey.KeyClear();
						paletteKey.setPaletteId(farCarry[i].getPaletteId());
					
						//#CM32402
						// set WorkInfo search key
						workInfoKey.KeyClear();
						workInfoKey.setSystemConnKey(farCarry[i].getCarryKey());
					
						//#CM32403
						// search
						if (paletteHandelr.count(paletteKey) > 0 && workInfoHandler.count(workInfoKey) > 0)
						{
							carryVec.add(farCarry[i]);
						}	
					}
					
					//#CM32404
					// use top data to decide conveyance status
					CarryInformation carry = (CarryInformation)carryVec.get(0);
					
					//#CM32405
					// If the picking carry data of depth location is stock confirmation, check whether same station work or not
					if (carry.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK)
					{
						//#CM32406
						// if station no. of deep location picking carry data and the storage carry data differs
						//#CM32407
						// don't store until the deep location stock confirmation work is completed						
						//#CM32408
						// to search Palette
						Palette palette = null;
						
						//#CM32409
						// set search key
						paletteKey.KeyClear();
						paletteKey.setCurrentStationNumberCollect();
						paletteKey.setPaletteId(cInfo.getPaletteId());

						//#CM32410
						// search
						try
						{
							palette = (Palette)paletteHandelr.findPrimary(paletteKey);
						}
						catch (NoPrimaryException e)
						{
							throw new ScheduleException(e.getMessage());
						}
						
						if (!palette.getCurrentStationNumber().equals(carry.getDestStationNumber()))
						{
							//#CM32411
							// Open an allocated shelf
							releaseShelf(cInfo, dShelf);
							return false;
						}
					}
					
					//#CM32412
					// Confirm the status of deep location picking carry data
					switch (carry.getCmdStatus())
					{
						//#CM32413
						// allocate
						case CarryInformation.CMDSTATUS_ALLOCATION:
							break;
							
						//#CM32414
						// start
						case CarryInformation.CMDSTATUS_START:
							//#CM32415
							// In case of start, check whether depth shelf carry data will be picked immediately
							//#CM32416
							// Suspend storage if the condition is met
							if (pairCarryCheck(farCarry[0]))
							{
								//#CM32417
								// Open an allocated shelf
								releaseShelf(cInfo, dShelf);
								return false;
							}
							break;
							
						//#CM32418
						// response wait, instructed
						case CarryInformation.CMDSTATUS_WAIT_RESPONSE:
						case CarryInformation.CMDSTATUS_INSTRUCTION:
							//#CM32419
							// Open an allocated shelf
							releaseShelf(cInfo, dShelf);
							return false;
							
						//#CM32420
						// load pickup complete, picking completion
						case CarryInformation.CMDSTATUS_PICKUP:
						case CarryInformation.CMDSTATUS_COMP_RETRIEVAL:
						case CarryInformation.CMDSTATUS_ARRIVAL:
							//#CM32421
							// enable picking instruction since picking work is completed already
							break;
							
						//#CM32422
						// abnormal
						case CarryInformation.CMDSTATUS_ERROR:
							//#CM32423
							// If there is difference in depth shelf, enable carry data instruction since front shelf storage is possible
							break;
					}
				}
			}
		}
		
		//#CM32424
		// picking instruction possible
		return true;
	}

	//#CM32425
	/**
	 * picking instruction condition check
	 * Check whether picking instruction is possible with refer to the defined <code>DoubleDeepShelf</code>instance
	 * If relocation between front side shelves becomes mandatory before picking instruction, save CarryInformation in database
	 * (Don't do picking instruction)
	 * Return true if picking instruction is enabled. Else false
	 * @param  cInfo			CarryInformation instance of picking instruction target
	 * @param  dShelf			Double deep shelf instance that refers to origin location
	 * @return				True if Carry possible qty is less. False if carry possible qty exceeds
	 * @throws ReadWriteException Throw exception if database read fails
	 * @throws InvalidDefineException Throw exception if the modify contents are illegal
	 */
	public boolean retrievalCheck(CarryInformation cInfo, Shelf shf) throws ReadWriteException, InvalidDefineException, ScheduleException
	{
		//#CM32426
		// If the conveyance destination is double deep type location, check location status
		if (shf instanceof DoubleDeepShelf)
		{
			DoubleDeepShelf dShelf = (DoubleDeepShelf)shf;
			//#CM32427
			// If the specified Double Deep Shelf is front side shelf, enable picking instruction without conditions
			if (dShelf.getSide() == BankSelect.FAR)
			{
				//#CM32428
				// If storage is done in the same pair location, lock the table since picking instruction, convey instruction will happen
				ShelfHandler sHandle = new ShelfHandler(wConn);
				ShelfSearchKey sKey = new ShelfSearchKey();
				sKey.setStationNumber(dShelf.getPairStationNumber());
				Shelf[] shfArray = (Shelf[])sHandle.findForUpdate(sKey);
				int pairPresence = dShelf.getPairPresence();
				if (shfArray != null && shfArray.length >= 0)
				{
					pairPresence = shfArray[0].getPresence();
				}
				
				//#CM32429
				// if the front location is not empty, read conveyance data or location data
				//#CM32430
				// to search CarryInfo
				CarryInformationAlterKey carryAlterKey = new CarryInformationAlterKey();
				CarryInformationSearchKey carryKey = new CarryInformationSearchKey();
				CarryInformation[] carry = null;
				
				//#CM32431
				// to search Palette
				PaletteHandler paletteHandelr = new PaletteHandler(wConn);
				PaletteSearchKey paletteKey = new PaletteSearchKey();
				
				//#CM32432
				// to search WorkInfo
				WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(wConn);
				WorkingInformationSearchKey workInfoKey = new WorkingInformationSearchKey();
				
				//#CM32433
				// to store CarryInfo
				Vector carryVec = new Vector();
				
				//#CM32434
				// if the front side shelf of a pair is not empty
				if (pairPresence != Shelf.PRESENCE_EMPTY)
				{
					//#CM32435
					// skip already scheduled location movement
					//#CM32436
					// set search key
					carryKey.KeyClear();
					carryKey.setSourceStationNumber(dShelf.getPairStationNumber());
					carryKey.setCarryKind(CarryInformation.CARRYKIND_RACK_TO_RACK);
					//#CM32437
					// search
					carry = (CarryInformation[])wCarryInformationHandler.find(carryKey);
					//#CM32438
					// to store CarryInfo
					carryVec.clear();
					
					for (int i = 0; i < carry.length; i++)
					{
						//#CM32439
						// set Palette search key
						paletteKey.KeyClear();
						paletteKey.setPaletteId(carry[i].getPaletteId());
					
						//#CM32440
						// set WorkInfo search key
						workInfoKey.KeyClear();
						workInfoKey.setSystemConnKey(carry[i].getCarryKey());
					
						//#CM32441
						// search
						if (paletteHandelr.count(paletteKey) > 0 && workInfoHandler.count(workInfoKey) > 0)
						{
							carryVec.add(carry[i]);
						}	
					}
					
					if (carryVec.size() > 0)
					{
						return false;
					}

					//#CM32442
					// if the pair shelf is reserved
					if (dShelf.getPairPresence() == Shelf.PRESENCE_RESERVATION)
					{
						//#CM32443
						// read stock confirmation send data using pair shell (frontal side) station no.
						//#CM32444
						// set search key
						carryKey.KeyClear();
						carryKey.setRetrievalDetail(CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK);
						carryKey.setRetrievalStationNumber(dShelf.getPairStationNumber());
						//#CM32445
						// search
						carry = (CarryInformation[])wCarryInformationHandler.find(carryKey);
						//#CM32446
						// to store CarryInfo
						carryVec.clear();
				
						for (int i = 0; i < carry.length; i++)
						{
							//#CM32447
							// set Palette search key
							paletteKey.KeyClear();
							paletteKey.setPaletteId(carry[i].getPaletteId());
					
							//#CM32448
							// set WorkInfo search key
							workInfoKey.KeyClear();
							workInfoKey.setSystemConnKey(carry[i].getCarryKey());
					
							//#CM32449
							// search
							if (paletteHandelr.count(paletteKey) > 0 && workInfoHandler.count(workInfoKey) > 0)
							{
								carryVec.add(carry[i]);
							}	
						}
						
						if (carryVec.size() > 0)
						{
							return true;
						}
					}

					//#CM32450
					// read storage confirmation send data using pair shell (frontal side) station no.
					//#CM32451
					// set search key
					carryKey.KeyClear();
					carryKey.setCarryKind(CarryInformation.CARRYKIND_STORAGE);
					carryKey.setDestStationNumber(dShelf.getPairStationNumber());
					//#CM32452
					// search
					carry = (CarryInformation[])wCarryInformationHandler.find(carryKey);
					//#CM32453
					// to store CarryInfo
					carryVec.clear();
					
					for (int i = 0; i < carry.length; i++)
					{
						//#CM32454
						// set Palette search key
						paletteKey.KeyClear();
						paletteKey.setPaletteId(carry[i].getPaletteId());
					
						//#CM32455
						// set WorkInfo search key
						workInfoKey.KeyClear();
						workInfoKey.setSystemConnKey(carry[i].getCarryKey());
					
						//#CM32456
						// search
						if (paletteHandelr.count(paletteKey) > 0 && workInfoHandler.count(workInfoKey) > 0)
						{
							carryVec.add(carry[i]);
						}	
					}

					if (carryVec.size() > 0)
					{
						//#CM32457
						// If storage carry data exist for frontal shelf, supsend the current picking activity until the storage work is completed
						return false;
					}
					else
					{
						//#CM32458
						// Read picking, relocation carry data for pair location (front shelf)
						//#CM32459
						// From the conveyance status received, give priority in the descending order to relocation data		
						//#CM32460
						// to search Palette
						Palette palette = null;
				
						//#CM32461
						// set search key
						carryKey.KeyClear();
						carryKey.setCarryKind(CarryInformation.CARRYKIND_RETRIEVAL, "=", "(", "", "or");
						carryKey.setCarryKind(CarryInformation.CARRYKIND_RACK_TO_RACK, "=", "", ")", "and");
						carryKey.setSourceStationNumber(dShelf.getPairStationNumber());
						carryKey.setCmdStatusOrder(1, false);
						carryKey.setCarryKindOrder(2, true);
						carryKey.setCreateDateOrder(3, true);
						
						if (wCarryInformationHandler.count(carryKey) > 0)
						{
							carry = (CarryInformation[])wCarryInformationHandler.find(carryKey);
							
							for (int i = 0; i < carry.length; i++)
							{
								//#CM32462
								// set Palette search key
								paletteKey.KeyClear();
								paletteKey.setPaletteId(carry[i].getPaletteId());
					
								//#CM32463
								// set WorkInfo search key
								workInfoKey.KeyClear();
								workInfoKey.setSystemConnKey(carry[i].getCarryKey());
					
								//#CM32464
								// search
								if (paletteHandelr.count(paletteKey) > 0 && workInfoHandler.count(workInfoKey) > 0)
								{
									carryVec.add(carry[i]);
								}	
							}
							
							if (carryVec.size() > 0)
							{		
								//#CM32465
								// use top data to decide conveyance status
								CarryInformation nearCarry = (CarryInformation)carryVec.get(0);
								
								//#CM32466
								// Confirm the picking carry data status for the front shelf
								switch (nearCarry.getCmdStatus())
								{
									//#CM32467
									// allocate
									case CarryInformation.CMDSTATUS_ALLOCATION:
										//#CM32468
										// Since picking is not possible with depth shelf, do relocation in front shelf
										
										//#CM32469
										// set search key
										paletteKey.KeyClear();
										paletteKey.setPaletteId(nearCarry.getPaletteId());
										try
										{
											//#CM32470
											// search
											palette = (Palette)paletteHandelr.findPrimary(paletteKey);
										}
										catch (NoPrimaryException e)
										{
											throw new ScheduleException(e.getMessage());
										}
										rackToRackMoveSchedule(palette);
										
										//#CM32471
										// Do relocation and suspend picking
										return false;
										
									//#CM32472
									// start
									case CarryInformation.CMDSTATUS_START:
										//#CM32473
										// In case of front shelf carry data stock confirmation
										//#CM32474
										// To send picking instruction first in case if there are other works that targets the same station
										//#CM32475
										// Change the priority type to "urgent"
										if (nearCarry.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK)
										{
											if (!cInfo.getDestStationNumber().equals(nearCarry.getDestStationNumber()))
											{
												try
												{
													//#CM32476
													// set search key
													carryAlterKey.KeyClear();
													carryAlterKey.setCarryKey(nearCarry.getCarryKey());
													carryAlterKey.updatePriority(CarryInformation.PRIORITY_EMERGENCY);
													//#CM32477
													// update
													wCarryInformationHandler.modify(carryAlterKey);
												}
												catch (NotFoundException e)
												{
													throw new InvalidDefineException(e.getMessage());
												}
												//#CM32478
												// Don't send picking instruction during update
												return false;
											}
										}
										
										//#CM32479
										// In case of start status, check whether the front shelf picking carry data is stored immediately
										//#CM32480
										// Suspend the current picking
										if (pairCarryCheck(nearCarry))
										{
											return false;
										}
										
										//#CM32481
										// If picking won't be immediate, do relocation of front shelf										
										//#CM32482
										// set search key
										paletteKey.KeyClear();
										paletteKey.setPaletteId(nearCarry.getPaletteId());
										try
										{
											//#CM32483
											// search
											palette = (Palette)paletteHandelr.findPrimary(paletteKey);
										}
										catch (NoPrimaryException e)
										{
											throw new ScheduleException(e.getMessage());
										}
										rackToRackMoveSchedule(palette);
										
										//#CM32484
										// Do relocation and suspend picking
										return false;
										
									//#CM32485
									// response wait, instructed
									case CarryInformation.CMDSTATUS_WAIT_RESPONSE:
									case CarryInformation.CMDSTATUS_INSTRUCTION:
										//#CM32486
										// If the front shelf carry data is already instructed, suspend current picking
										return false;
										
									//#CM32487
									// load pickup complete, picking completion
									case CarryInformation.CMDSTATUS_PICKUP:
									case CarryInformation.CMDSTATUS_COMP_RETRIEVAL:
									case CarryInformation.CMDSTATUS_ARRIVAL:
										//#CM32488
										// enable picking instruction since picking work is completed already
										return true;

									//#CM32489
									// abnormal
									case CarryInformation.CMDSTATUS_ERROR:
										//#CM32490
										// If the fornt location is abnormal, suspend current picking
										return false;
								}
							}
						}
					}
					
					//#CM32491
					// If Carry data does not exist
					//#CM32492
					// Search palette that corresponds to pair shelf and do relocation process
					paletteKey.KeyClear();
					paletteKey.setCurrentStationNumber(dShelf.getPairStationNumber());
					Palette[] plt = (Palette[])wPaletteHandler.find(paletteKey);
					rackToRackMoveSchedule(plt[0]);
					return false;
				}
				//#CM32493
				// If the front shelf is empty, check whether relocation schedule is completed or not
			    else
			    {
			    	//#CM32494
			    	// set search key
					carryKey.KeyClear();
				    carryKey.setSourceStationNumber(dShelf.getStationNumber());
					carryKey.setCarryKind(CarryInformation.CARRYKIND_RACK_TO_RACK);
					//#CM32495
					// search
					carry = (CarryInformation[])wCarryInformationHandler.find(carryKey);
					//#CM32496
					// to store CarryInfo
					carryVec.clear();
					
					for (int i = 0; i < carry.length; i++)
					{
						//#CM32497
						// set Palette search key
						paletteKey.KeyClear();
						paletteKey.setPaletteId(carry[i].getPaletteId());
					
						//#CM32498
						// set WorkInfo search key
						workInfoKey.KeyClear();
						workInfoKey.setSystemConnKey(carry[i].getCarryKey());
					
						//#CM32499
						// search
						if (paletteHandelr.count(paletteKey) > 0 && workInfoHandler.count(workInfoKey) > 0)
						{
							carryVec.add(carry[i]);
						}	
					}
					
				    if (carryVec.size() > 0)
				    {
					    return false;
				    }
			    }
			}
		}
		
		//#CM32500
		// picking instruction enabled
		return true;
	}

	//#CM32501
	// Package methods -----------------------------------------------

	//#CM32502
	// Protected methods ---------------------------------------------
	//#CM32503
	/**
	 * Verify whether the instructed carry data is immediately picked. If the following conditions confirm, return true. Even if one fails, return false
	 * * The first carry data
	 * * If the group controller to which the conveyance station belongs is other than online
	 * * If the conveyance station is picking mode
	 * * If the conveyance station is not under suspension
	 * * If the conveyance station picking instruction is within Carry Information possible qty
	 * @param  cInfo			target CarryInformation
	 * @return					If picking carry data return true. Else false
	 * @throws ReadWriteException Throw exception if database read fails
	 * @throws InvalidDefineException Throw exception if the modify contents are illegal
	 */
	protected boolean pairCarryCheck(CarryInformation cInfo) throws ReadWriteException, InvalidDefineException
	{
		//#CM32504
		// to search CarryInfo
		CarryInformationSearchKey carryKey = new CarryInformationSearchKey();
		CarryInformation[] carryArray = null;
		
		//#CM32505
		// to search Palette
		PaletteHandler paletteHandelr = new PaletteHandler(wConn);
		PaletteSearchKey paletteKey = new PaletteSearchKey();
				
		//#CM32506
		// to search WorkInfo
		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(wConn);
		WorkingInformationSearchKey workInfoKey = new WorkingInformationSearchKey();
		
		//#CM32507
		// to store CarryInfo
		Vector carryVec = new Vector();
		
		//#CM32508
		// set search key
		carryKey.KeyClear();
		
		//#CM32509
		// If the conveyance station is same and conveyance status is "start"
		carryKey.setDestStationNumber(cInfo.getDestStationNumber());
		carryKey.setCmdStatus(CarryInformation.CMDSTATUS_START);

		//#CM32510
		// sort by create date/time in ascending order
		carryKey.setCreateDateOrder(1, true);
		carryKey.setCarryKeyOrder(2, true);

		//#CM32511
		// read conveyance data
		CarryInformation[] carry = (CarryInformation[])wCarryInformationHandler.find(carryKey);
		
		//#CM32512
		// to store CarryInfo
		carryVec.clear();
					
		for (int i = 0; i < carry.length; i++)
		{
			//#CM32513
			// set Palette search key
			paletteKey.KeyClear();
			paletteKey.setPaletteId(carry[i].getPaletteId());
					
			//#CM32514
			// set WorkInfo search key
			workInfoKey.KeyClear();
			workInfoKey.setSystemConnKey(carry[i].getCarryKey());
					
			//#CM32515
			// search
			if (paletteHandelr.count(paletteKey) > 0 && workInfoHandler.count(workInfoKey) > 0)
			{
				carryVec.add(carry[i]);
			}	
		}
		
		CarryInformation[] resultCarryArray = new CarryInformation[carryVec.size()];
		carryVec.copyInto(resultCarryArray);
		
		if (carryVec.size() == 0)
		{
			//#CM32516
			// Throw exception if the carry data can't be found
			//#CM32517
			// 6026078=The instructed instance {0} can't be found
			Object[] tObj = new Object[1];
			tObj[0] = "CarryInformation";
			RmiMsgLogClient.write(6026078, LogMessage.F_WARN, this.getClass().getName(), tObj);
			throw new InvalidDefineException("6026078" + wDelim + tObj[0]);
		}
		
		//#CM32518
		// If the Carry data returned from the method is same as the search result
		//#CM32519
		// Confirm whether the specified carrday data is the first picking data
		if (resultCarryArray[0].getCarryKey().equals(cInfo.getCarryKey()))
		{
			Station destSt = null;
			try
			{
				destSt = StationFactory.makeStation(wConn, resultCarryArray[0].getDestStationNumber());
			}
			catch (SQLException e)
			{
				throw new ReadWriteException(e.getMessage());
			}
			catch (NotFoundException e)
			{
				throw new InvalidDefineException(e.getMessage());
			}
			
			//#CM32520
			// Picking carry condition check
			//#CM32521
			// Conveyance not possible if the group controller to which the conveyance station belongs is other than online
			if (destSt.getControllerNumber() != GroupController.STATUS_ONLINE)
			{
				return false;
			}
			
			//#CM32522
			// Confirm picking conveyance station work mode
			//#CM32523
			// if sending station is storage/picking, mode confirmation is mandatory. if it is storage only with no mode control, mode confirmation is not needed
			if (destSt.getStationType() == Station.STATION_TYPE_INOUT && destSt.getModeType() != Station.NO_MODE_CHANGE)
			{
				//#CM32524
				// If the conveyance station is of the following status, decide that immediate picking is not done
				//#CM32525
				// if the work mode change request is either storage or picking mode 
				//#CM32526
				//work mode is neutral or storage
				if ((destSt.getModeRequest() != Station.NO_REQUEST)
				 || (destSt.getCurrentMode() == Station.NEUTRAL)
				 || (destSt.getCurrentMode() == Station.STORAGE_MODE))
				{
					return false;
				}
			}
			
			//#CM32527
			// Confirm the "under suspension" flag of picking conveyance station
			if (destSt.getSuspend() == Station.SUSPENDING)
			{
				//#CM32528
				// Decide not to pick immediately if under suspension
				return false;
			}
			
			//#CM32529
			// set search key
			carryKey.KeyClear();
			carryKey.setPaletteIdCollect();
			int[] cmd = {CarryInformation.CMDSTATUS_INSTRUCTION, CarryInformation.CMDSTATUS_WAIT_RESPONSE, CarryInformation.CMDSTATUS_PICKUP,
						  CarryInformation.CMDSTATUS_COMP_RETRIEVAL, CarryInformation.CMDSTATUS_ARRIVAL};
			carryKey.setCmdStatus(cmd);
			carryKey.setDestStationNumber(destSt.getStationNumber(), "=", "(", "", "or");
			carryKey.setSourceStationNumber(destSt.getStationNumber(), "=", "", ")", "and");

			if (wCarryInformationHandler.count(carryKey) > 0)
			{
				//#CM32530
				// search
				carryArray = (CarryInformation[])wCarryInformationHandler.find(carryKey);
			
				//#CM32531
				// picking instruction enabled number count
				int count = 0;

				for (int i = 0; i < carryArray.length; i++)
				{
					//#CM32532
					// set search key
					paletteKey.KeyClear();
					paletteKey.setPaletteId(carryArray[i].getPaletteId());
					//#CM32533
					// add to picking instruction enabled number count
					count += paletteHandelr.count(paletteKey);
				}
			
				if (destSt.getMaxPaletteQuantity() <= count)
				{
					//#CM32534
					// The number of retrieval instruction exceeded the available number set by the regulation
					return false;
				}
			}
		}
		else
		{
			return false;
		}
		
		//#CM32535
		// Return true if the first data is picking instruction enabled
		return true;
	}

	//#CM32536
	/**
	 * Create carry data for relocation with refer to specified palette
	 * Add to a database so that the created CarryInformation targets picking information
	 * Carry data Decide storage location while creating
	 * @param  Palette Target palette for relocation
	 * @return Carry data for relocation
	 * @throws ReadWriteException Throw exception if database read fails
	 * @throws InvalidDefineException Throw exception if the modify contents are illegal
	 */
	protected CarryInformation rackToRackMoveSchedule(Palette plt) throws ReadWriteException, InvalidDefineException
	{
		CarryInformation newCarryInfo = null;
		Station aile = null;

		String sourceShelf = "";

		try
		{
			//#CM32537
			// Process conveyance confirmation and determine conveyance storage location
			//#CM32538
			// Since the location is determined from the same aisle, fetch aisle station no. and set to current position
			sourceShelf = plt.getCurrentStationNumber();
			Shelf shelf = (Shelf)StationFactory.makeStation(wConn, plt.getCurrentStationNumber());
			aile = StationFactory.makeStation(wConn, shelf.getParentStationNumber());
			plt.setCurrentStationNumber(aile.getStationNumber());
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new InvalidDefineException(e.getMessage());
		}

		try
		{	
			WareHouse wareHouse = (WareHouse)StationFactory.makeStation(wConn, aile.getWHStationNumber());
			Station destStation = findRackToRackMove(plt, wareHouse);

			if (destStation != null)
			{
				//#CM32539
				// Call location decide process
				//#CM32540
				// Create carry data if the relocation destination location is found
				newCarryInfo = new CarryInformation();

				//#CM32541
				// If destination location is not found, depth location instruction cancellation will keep the palette status
				//#CM32542
				// The palette status is modified after destination location is decided
				//#CM32543
				// Change palette status to picking reserved, allocation status to allocated and update last update date/time
				//#CM32544
				// to search, update Palette
				PaletteHandler paletteHandelr = new PaletteHandler(wConn);
				PaletteAlterKey paletteAlterKey = new PaletteAlterKey();
				
				//#CM32545
				// set search key
				paletteAlterKey.KeyClear();
				paletteAlterKey.setPaletteId(plt.getPaletteId());
				paletteAlterKey.updateAllocation(Palette.ALLOCATED);
				paletteAlterKey.updateStatus(Palette.RETRIEVAL_PLAN);
				paletteAlterKey.updateRefixDate(new java.util.Date());
				//#CM32546
				// update
				paletteHandelr.modify(paletteAlterKey);

				//#CM32547
				// to search Stock
				StockHandler stockHandler = new StockHandler(wConn);
				StockSearchKey stockKey = new StockSearchKey();
				Stock[] stock = null;
				//#CM32548
				// set search key
				stockKey.KeyClear();
				stockKey.setPaletteid(plt.getPaletteId());

				//#CM32549
				// search
				stock = (Stock[])stockHandler.find(stockKey);

				WorkingInformation[] warray = new WorkingInformation[stock.length];

				for (int i = 0 ; i < stock.length ; i++)
				{
					//#CM32550
					// generate WorkInformation instance
					WorkingInformation wi = new WorkingInformation();
					//#CM32551
					// job type
					wi.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
					//#CM32552
					// stock id
					wi.setStockId(stock[i].getStockId());
					//#CM32553
					// item code
					wi.setItemCode(stock[i].getItemCode());
					//#CM32554
					// lot no.
					wi.setLotNo(stock[i].getLotNo());
					//#CM32555
					// Work qty = 0
					wi.setResultQty(0);
					warray[i] = wi;
				}

				//#CM32556
				// add decision flag
				boolean createFlag = false;

				//#CM32557
				// to add WorkInfo
				WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(wConn);
				WorkingInformationSearchKey workKey = new WorkingInformationSearchKey();

				//#CM32558
				// generate CarryInformation instance
 				SequenceHandler sequence = new SequenceHandler(wConn);
				String carryKey = sequence.nextCarryKey();
				
				//#CM32559
				// check CarryInfo existance
				CarryInformationSearchKey carrySearchKey = new CarryInformationSearchKey();
				carrySearchKey.setCarryKey(carryKey);
				if (wCarryInformationHandler.count(carrySearchKey) > 0)
				{
					//#CM32560
					//check WorkInfo existance
					for (int i = 0 ; i < warray.length ; i++)
					{
						WorkingInformation workInfo = warray[i];
						workKey.KeyClear();
						workKey.setSystemConnKey(carryKey);
						workKey.setItemCode(workInfo.getItemCode());
						workKey.setLotNo(workInfo.getLotNo());
						if (workInfoHandler.count(workKey) < 1)
						{
							createFlag = true;
						}
					}
					
					if (!createFlag)
					{
						//#CM32561
						// If both exists, resgisteration is impossible.
						Object[] tObj = new Object[1] ;
						tObj[0] = "CarryInformationHandler" ;
						String classname = (String)tObj[0];
						RmiMsgLogClient.write(6006007, LogMessage.F_ERROR, classname, tObj);
						throw new DataExistsException("6006007");
					}
					
					//#CM32562
					// add to WorkInfo table
					for (int i = 0 ; i < warray.length ; i++)
					{
						WorkingInformation workInfo = warray[i];

						workInfo.setJobNo(sequence.nextJobNo());
						workInfo.setSystemConnKey(carryKey);
						workInfoHandler.create(workInfo);
					}
				}
				else
				{
					//#CM32563
					// add to CarryInfo table
					newCarryInfo.setCarryKey(carryKey);
					newCarryInfo.setSourceStationNumber(sourceShelf);

					//#CM32564
					// created date/time
					newCarryInfo.setCreateDate(new java.util.Date());
					//#CM32565
					// work type
					newCarryInfo.setWorkType(InOutResult.WORKTYPE_MOVE_FROM);
					//#CM32566
					// target palette
					newCarryInfo.setPaletteId(plt.getPaletteId());
					//#CM32567
					//destination station no.
					newCarryInfo.setDestStationNumber(destStation.getStationNumber());
					//#CM32568
					// aisle station no
					newCarryInfo.setAisleStationNumber(destStation.getParentStationNumber());
					//#CM32569
					// group no.
					newCarryInfo.setGroupNumber(0);
					//#CM32570
					//conveyance status : start
					newCarryInfo.setCmdStatus(CarryInformation.CMDSTATUS_START);
					//#CM32571
					// priority type
					newCarryInfo.setPriority(CarryInformation.PRIORITY_EMERGENCY);
					//#CM32572
					// restorage flag
					newCarryInfo.setReStoringFlag(CarryInformation.RESTORING_SAME_LOC);
					//#CM32573
					// carry type : location movement
					newCarryInfo.setCarryKind(CarryInformation.CARRYKIND_RACK_TO_RACK);
					//#CM32574
					// picking instruction detail
					newCarryInfo.setRetrievalDetail(CarryInformation.RETRIEVALDETAIL_UNIT);
					//#CM32575
					// job no.
					newCarryInfo.setWorkNumber(null);                              
					//#CM32576
					// arrival time:null
					newCarryInfo.setArrivalDate(null);
					//#CM32577
					// control information
					newCarryInfo.setControlInfo(null);
					//#CM32578
					// cancel flag : no cancel request
					newCarryInfo.setCancelRequest(CarryInformation.CANCELREQUEST_UNDEMAND);
					//#CM32579
					// cancel request date/time
					newCarryInfo.setCancelRequestDate(null);
					//#CM32580
					// schedule no.
					newCarryInfo.setScheduleNumber(null);
					
					wCarryInformationHandler.create(newCarryInfo);
					
					//#CM32581
					// add to WorkInfo table
					for (int i = 0 ; i < warray.length ; i++)
					{
						WorkingInformation workInfo = warray[i];

						workInfo.setJobNo(sequence.nextJobNo());
						workInfo.setSystemConnKey(carryKey);
						workInfoHandler.create(workInfo);
					}
				}
			}
			else
			{
				return null;
			}
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (DataExistsException e)
		{
			throw new InvalidDefineException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new InvalidDefineException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new InvalidDefineException(e.getMessage());
		}

		return newCarryInfo;
	}

	//#CM32582
	/**
	 * Search empty location for relocation and return it
	 * @param  plt Target palette for relocation
	 * @param  wh Conveyance warehouse instance
	 * @return <code>Shel</code> instance for relocation
	 * @throws ReadWriteException Throw exception if database read fails
	 * @throws InvalidDefineException Throw exception if the modify contents are illegal
	 */
	protected Shelf findRackToRackMove(Palette plt, WareHouse wh) throws ReadWriteException, InvalidDefineException
	{
		//#CM32583
		// Search soft zone, hard zone
		ZoneSelector zn = new CombineZoneSelector(wConn);

		//#CM32584
		// Decide empty location search methodology (empty location search class for relocation)
		ShelfSelector dpt = new RackToRackMoveSelector(wConn, zn);
		
		//#CM32585
		// Empty location search
		Shelf location = dpt.select(plt, wh);
		if (location != null)
		{
			try
			{
				//#CM32586
				// Reserve the search result location for storage
				//#CM32587
				// to update Shelf
				ShelfHandler shelfHandler = new ShelfHandler(wConn);
				ShelfAlterKey shelfAlterKeyk = new ShelfAlterKey();
				//#CM32588
				// set search key
				shelfAlterKeyk.KeyClear();
				shelfAlterKeyk.setStationNumber(location.getStationNumber());
				shelfAlterKeyk.updatePresence(Shelf.PRESENCE_RESERVATION);
				//#CM32589
				// update
				shelfHandler.modify(shelfAlterKeyk);
			}
			catch (NotFoundException e)
			{
				throw new InvalidDefineException(e.getMessage());
			}
			return location;
		}
		
		return null;
	}

	//#CM32590
	/**
	 * CarryInformation Change the conveyance point from location to warehouse and specified location to empty
	 * @param  cInfo			Carry data
	 * @param  shf				Open <code>Shelf</code> instance
	 * @throws ReadWriteException Throw exception if database read fails
	 * @throws InvalidDefineException Throw exception if the modify contents are illegal
	 */
	private void releaseShelf(CarryInformation cInfo, Shelf shf) throws ReadWriteException, InvalidDefineException
	{
		try
		{
			//#CM32591
			// Since storage into picking location is mandatory during stock confirmation
			//#CM32592
			// Don't open location
			if (cInfo.getRetrievalDetail() != CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK)
			{
				//#CM32593
				// to update CarryInfo
				CarryInformationHandler carryInfoHandler = new CarryInformationHandler(wConn);
				CarryInformationAlterKey carryAlterKey = new CarryInformationAlterKey();
				//#CM32594
				// set search key
				carryAlterKey.KeyClear();
				carryAlterKey.setCarryKey(cInfo.getCarryKey());
				carryAlterKey.updateDestStationNumber(shf.getWHStationNumber());
				carryAlterKey.updateAisleStationNumber(null);
				//#CM32595
				// update
				carryInfoHandler.modify(carryAlterKey);
				
				//#CM32596
				// to update Shelf
				ShelfHandler shelfHandler = new ShelfHandler(wConn);
				ShelfAlterKey shelfAlterKeyk = new ShelfAlterKey();
				//#CM32597
				// set search key
				shelfAlterKeyk.KeyClear();
				shelfAlterKeyk.setStationNumber(shf.getStationNumber());
				shelfAlterKeyk.updatePresence(Shelf.PRESENCE_EMPTY);
				//#CM32598
				// update
				shelfHandler.modify(shelfAlterKeyk);
			}
		}
		catch (NotFoundException e)
		{
			throw new InvalidDefineException(e.getMessage());
		}
	}

	//#CM32599
	// Private methods -----------------------------------------------

}
//#CM32600
//end of class

