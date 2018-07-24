// $Id: DoubleDeepStorageSender.java,v 1.2 2006/10/26 01:06:14 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM31175
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.rmi.RemoteException;

import jp.co.daifuku.wms.asrs.control.DoubleDeepChecker;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.entity.Palette;

//#CM31176
/**
 * This class operates the system transmission of carrying instructions including double deep operation.
 * It gest data to send to AGC out of CarryInformation, then releases the carrying instruction to AGC.
 * In case the warehouse is double deep, and when the instructed retrieval is working with the rear rack location,
 * status of the front rack needs to be checked. Therefore, this class also preserves the checking process of 
 * front rack.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/04/08</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:06:14 $
 * @author  $Author: suresh $
 */
public class DoubleDeepStorageSender extends StorageSender
{
	//#CM31177
	// Class fields --------------------------------------------------

	//#CM31178
	// Class variables -----------------------------------------------
	//#CM31179
	/**
	 * The class that checks the carrying when double deep is in the operation
	 */
	DoubleDeepChecker wDoubleDeepChecker = null;

	//#CM31180
	// Class method --------------------------------------------------
	//#CM31181
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:06:14 $") ;
	}

	//#CM31182
	// Constructors --------------------------------------------------
	//#CM31183
	/**
	 * Create new instance of <code>DoubleDeepStorageSender</code>.
	 * The connection will be obtained  from parameter of AS/RS system out of resource.
	 * @throws ReadWriteException : Notifies if exception occured during the database connection.
	 * @throws RemoteException  Exception related to communication generated while executing remote method call
	 */
	public DoubleDeepStorageSender() throws ReadWriteException, RemoteException
	{
		super();
		wDoubleDeepChecker = new DoubleDeepChecker(wConn);
	}

	//#CM31184
	// Public methods ------------------------------------------------

	//#CM31185
	// Package methods -----------------------------------------------

	//#CM31186
	// Protected methods ---------------------------------------------

	//#CM31187
	/**
	 * Designate the location number at the receiving station, then check the condition of carrying instruction.
	 * If the load is destined to racks and the reeiver's aisle is double-deep layout, check to see if the carrying
	 * can be carried out based on the position of the rack.
	 * If carrying is available it returns 'true' and if is not available it returns 'false'.
	 * In case the carrying is not available, change the receiving station number of the received CarryInformation 
	 * back to the warehouse no., then change the Station instance (practically a shelf), specified by the receiving
	 * station number, to the empty location.
	 * @param  cInfo			CarryInformation
	 * @param  sourceSt		sending station
	 * @return 				returns 'true' if carrying instruction can be proceeded; or 'false' if not.
	 * @throws Exception		Notifies if trouble occured in the reading/writing of database.
	 */
	protected boolean destDetermin(CarryInformation cInfo, Station sourceSt) throws Exception
	{
		try
		{
			Station destStation = StationFactory.makeStation(wConn, cInfo.getDestStationNumber());
			if (destStation instanceof Shelf)
			{
				Shelf shf = (Shelf)destStation;
				
				//#CM31188
				// to update Shelf
				ShelfHandler shelfHandler = new ShelfHandler(wConn);
				ShelfAlterKey shelfKey = new ShelfAlterKey();
				
				//#CM31189
				// In case of inventory check data, store them as they are to the designated location.
				//#CM31190
				// As the location will be open when woek completesd, change the status again to 'reserved'.
				if (cInfo.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK)
				{
					//#CM31191
					// set search key
					shelfKey.KeyClear();
					shelfKey.setStationNumber(shf.getStationNumber());
					shelfKey.updatePresence(Shelf.PRESENCE_RESERVATION);
					//#CM31192
					// update
					shelfHandler.modify(shelfKey);
				}
				//#CM31193
				// As empty location reserch will be done except for inventory check data, load presence in the 
				//#CM31194
				// location, originally to-store, of the receiving station should be changed to 'OFF'.
				else
				{
					//#CM31195
					// set search key
					shelfKey.KeyClear();
					shelfKey.setStationNumber(shf.getStationNumber());
					shelfKey.updatePresence(Shelf.PRESENCE_EMPTY);
					//#CM31196
					// update
					shelfHandler.modify(shelfKey);
					
					//#CM31197
					// Change the receiving station to the warehouse.
					destStation = (WareHouse)StationFactory.makeStation(wConn, shf.getWHStationNumber());
				}
			}
			
			//#CM31198
			// Designation of receiving station and check the transport route.			
			//#CM31199
			// to search Palette
			PaletteHandler paletteHandler = new PaletteHandler(wConn);
			PaletteSearchKey paletteKey = new PaletteSearchKey();
			//#CM31200
			// set search key
			paletteKey.KeyClear();
			paletteKey.setPaletteId(cInfo.getPaletteId());
			
			if (wRouteController.storageDetermin((Palette)paletteHandler.findPrimary(paletteKey), sourceSt, destStation))
			{
				//#CM31201
				// Replace the receiving station with the one that RouteController class has determined.
				if (!destStation.getStationNumber().equals(wRouteController.getDestStation().getStationNumber()))
				{
					//#CM31202
					// Set the receiving StationNumber and aisle StaitonNumber to the CarryInformation					
					//#CM31203
					// to update CarryInfo
					CarryInformationAlterKey carryKey = new CarryInformationAlterKey();
					//#CM31204
					// set search key
					carryKey.KeyClear();
					carryKey.setCarryKey(cInfo.getCarryKey());
					carryKey.updateDestStationNumber(wRouteController.getDestStation().getStationNumber());
					carryKey.updateAisleStationNumber(wRouteController.getDestStation().getAisleStationNumber());
					//#CM31205
					// update
					wCarryInformationHandler.modify(carryKey);
				}
				
				//#CM31206
				// In case the destination of the load is a shelf, condition check of carrying with double deep operation
				// should be done.
				if (wRouteController.getDestStation() instanceof Shelf)
				{
					Shelf shf = (Shelf)wRouteController.getDestStation();
					if (!wDoubleDeepChecker.storageCheck(cInfo, shf))
					{
						return false;
					}
				}
			}
			else
			{
				//#CM31207
				// Inappropriate transport route; or no receiving station is found.
				return false;
			}
		}
		//#CM31208
		// if error occurs during data update
		catch (InvalidDefineException e)
		{
			carryFailure(cInfo);
			return false;
		}
		//#CM31209
		// if there no target data for update, StationFactory.makeStation is called
		catch (NotFoundException e)
		{
			carryFailure(cInfo);
			return false;
		}
		
		//#CM31210
		// picking instruction possible
		return true;
	}
	
	//#CM31211
	/**
	 * Checks the status of the sending station.
	 * equipment controller (AGC) that sending station belongs to is normal.
	 * Sending station is not requesting to switch the work mode.
	 * In case the work mode is in control: If the sending station handles both storage/retrieval, 
	 * the work mode of the sending station is 'storage'
	 *   Except if the detail of retrieval instruction is unset(new storage), the work mode of the Station won't have to be checked.
	 * As for the available work number for the carrying instruction, check to see whether/not the CarryInformation 
	 * relevant to sending/receiving stations exceeded the volume set by regulation.
	 * DoubleDeep should be treated as a special operation.
	 * Operation of the sending station is not suspended
	 * @param  cInfo			CarryInformation
	 * @param  sourceSt			Sending station
	 * @return					Returns 'true' if carying is available or 'false' if not.
	 * @throws Exception		Notifies if trouble occurs in the reading/writing of database.
	 */
	protected boolean soueceRightStation(CarryInformation cInfo, Station sourceSt) throws Exception
	{
		//#CM31212
		// Check the sending station
		//#CM31213
		// Whether/not the AGC, which is to control the sending machine, is normal		
		GroupController groupControll = new GroupController(wConn, sourceSt.getControllerNumber());		
		if (groupControll.getStatus() != GroupController.STATUS_ONLINE)
		{
			return false;
		}
		
		//#CM31214
		// If the detail of retrieval instruction has not been set, it means this is a new storage;
		//#CM31215
		// work mode of the sending machine must be checked.
		if (cInfo.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_UNKNOWN)
		{
			//#CM31216
			// If the sending station operates both storage and retrieval, the work mode needs to be checked.
			// If it only handles storage, or if it has no control over work mode, no checking is necessary.
			if (sourceSt.getStationType() == Station.STATION_TYPE_INOUT && sourceSt.getModeType() != Station.NO_MODE_CHANGE)
			{
				//#CM31217
				// Has not the sending station received the work mode switching request?
				//#CM31218
				// Is work mode of the sending station 'storage'? (neutral/retrieval mode are NOT acceptable)
				if(    (sourceSt.getModeRequest() != Station.NO_REQUEST)  
					|| (sourceSt.getCurrentMode() == Station.NEUTRAL )
					|| (sourceSt.getCurrentMode() == Station.RETRIEVAL_MODE )  )
				{
					return false;
				}
			}
		}
		
		//#CM31219
		// to search CarryInfo
		CarryInformationSearchKey carryKey = new CarryInformationSearchKey();
		CarryInformation[] carryArray = null;

		//#CM31220
		// to search Palette
		PaletteHandler paletteHandelr = new PaletteHandler(wConn);
		PaletteSearchKey paletteKey = new PaletteSearchKey();
		
		//#CM31221
		// number
		int count = 0;
		
		//#CM31222
		// set search key
		carryKey.KeyClear();
		carryKey.setPaletteIdCollect();
		int[] cmd = {CarryInformation.CMDSTATUS_INSTRUCTION, CarryInformation.CMDSTATUS_WAIT_RESPONSE, CarryInformation.CMDSTATUS_PICKUP,
					  CarryInformation.CMDSTATUS_COMP_RETRIEVAL, CarryInformation.CMDSTATUS_ARRIVAL};
		carryKey.setCmdStatus(cmd);
		carryKey.setDestStationNumber(sourceSt.getStationNumber(), "=", "(", "", "or");
		carryKey.setSourceStationNumber(sourceSt.getStationNumber(), "=", "", ")", "and");
		
		if (wCarryInformationHandler.count(carryKey) > 0)
		{
			//#CM31223
			// search
			carryArray = (CarryInformation[])wCarryInformationHandler.find(carryKey);

			for (int i = 0; i < carryArray.length; i++)
			{
				//#CM31224
				// set search key
				paletteKey.KeyClear();
				paletteKey.setPaletteId(carryArray[i].getPaletteId());
				//#CM31225
				// add to picking instruction enabled number count
				count += paletteHandelr.count(paletteKey);
			}
			
			//#CM31226
			// If the status of this carrying data 'arrived', itself should be also included in the number of carrying 
			//#CM31227
			// instructions; therefore subtract 1 from the result before comparison.
			if (cInfo.getCmdStatus() == CarryInformation.CMDSTATUS_ARRIVAL)
			{
				if (sourceSt.getMaxPaletteQuantity() <= count - 1)
				{
					//#CM31228
					// Exceeded the available number of carrying operation to handle
					return false;
				}
			}
			else
			{
				if (sourceSt.getMaxInstruction() <= count)
				{
					//#CM31229
					// Exceeded the available number of carrying operation to handle
					return false;
				}
			}
		}
		
		//#CM31230
		// Whether/not the sending station is suspended
		if (sourceSt.getSuspend() == Station.SUSPENDING)
		{
			return false;
		}
		
		return true;
	}

}
//#CM31231
//end of class
