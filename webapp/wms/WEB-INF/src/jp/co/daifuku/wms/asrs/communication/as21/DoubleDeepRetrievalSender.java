// $Id: DoubleDeepRetrievalSender.java,v 1.2 2006/10/26 01:06:52 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM31080
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import jp.co.daifuku.wms.asrs.control.DoubleDeepChecker;
import jp.co.daifuku.wms.asrs.dbhandler.ASCarryInformationHandler;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.Palette;

//#CM31081
/**
 * This class operates the system transmission of retrieval instructions including the double deep operaration.
 * It gets the retrieval data to send to AGC from CarryInformation, then releases AGC the retrieval instruction.
 * In case the warehouse is double deep, and when the instructed retrieval is working with the rear rack location,
 * status of the front rack needs to be checked. Therefore, this class also preserves the checking process of 
 * front rack.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/04/08</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:06:52 $
 * @author  $Author: suresh $
 */
public class DoubleDeepRetrievalSender extends RetrievalSender
{
	//#CM31082
	// Class fields --------------------------------------------------

	//#CM31083
	// Class variables -----------------------------------------------
	//#CM31084
	/**
	 * The class which checks the carrying with double deep operation
	 */
	DoubleDeepChecker wDoubleDeepChecker = null;

	//#CM31085
	// Class method --------------------------------------------------
	//#CM31086
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:06:52 $") ;
	}

	//#CM31087
	// Constructors --------------------------------------------------
	//#CM31088
	/**
	 * Create new instance of <code>DoubleDeepRetrievalSender</code>.
	 * The connection will be obtained  from parameter of AS/RS system out of resource.
	 * @throws ReadWriteException : Notifies if exception occured during the database connection.
	 * @throws RemoteException  Exception related to communication generated while executing remote method call
	 */
	public DoubleDeepRetrievalSender() throws ReadWriteException, RemoteException
	{
		super();
		wDoubleDeepChecker = new DoubleDeepChecker(wConn);
	}

	//#CM31089
	// Public methods ------------------------------------------------
	//#CM31090
	/**
	 * Procesing the retrieval instruction.
	 * Read the CarryInformation requesting for the retrieval instruction, then if the carrying is feasible,
	 * @throws Exception : Notifies if retrieval instruction cannot be continued any longer.
	 */
	public void control() throws Exception
	{
		//#CM31091
		// fetch storage/picking + picking station list
		StationHandler stationHandler = new StationHandler(wConn);
		StationSearchKey stationKey = new StationSearchKey();
		//#CM31092
		// set search key
		stationKey.KeyClear();
		stationKey.setSendable(Station.SENDABLE_TRUE);
		stationKey.setModeType(Station.AUTOMATIC_MODE_CHANGE, "!=");
		stationKey.setStationType(Station.STATION_TYPE_OUT, "=", "(", "", "or");
		stationKey.setStationType(Station.STATION_TYPE_INOUT, "=", "", ")", "and");
		stationKey.setStationNumberOrder(1, true);
		//#CM31093
		// search
		Station[] stations= (Station[])stationHandler.find(stationKey);

		//#CM31094
		// fetch the picking instruction request data by station
		//#CM31095
		// to search CarryInfo
		CarryInformationSearchKey carryKey = new CarryInformationSearchKey();
		CarryInformation[] carryArray = null;
		
		//#CM31096
		// to search Palette
		PaletteHandler paletteHandelr = new PaletteHandler(wConn);
		PaletteSearchKey paletteKey = new PaletteSearchKey();
		
		//#CM31097
		// to search WorkInfo
		WorkingInformationHandler workInfoHandelr = new WorkingInformationHandler(wConn);
		WorkingInformationSearchKey workInfoKey = new WorkingInformationSearchKey();
		
		//#CM31098
		// variable to store CarryInfo related to Palette, WorkInfo
		Vector carryVec = new Vector();
		
		for (int i = 0 ; i < stations.length ; i++)
		{
			//#CM31099
			// set search key
			carryKey.KeyClear();
			carryKey.setCarryKeyCollect();
			carryKey.setPaletteIdCollect();
			carryKey.setCarryKind(CarryInformation.CARRYKIND_RETRIEVAL);
			carryKey.setCmdStatus(CarryInformation.CMDSTATUS_START);
			carryKey.setDestStationNumber((String)stations[i].getStationNumber());
			carryKey.setPriorityOrder(1, true);
			carryKey.setGroupNumberOrder(2, true);
			carryKey.setCreateDateOrder(3, true);
			carryKey.setCarryKeyOrder(4, true);
			
			if (wCarryInformationHandler.count(carryKey) > 0)
			{	
				//#CM31100
				// search CarryInfo
				carryArray = (CarryInformation[])wCarryInformationHandler.find(carryKey);
				
				//#CM31101
				// clear the CarryInfo storage variable
				carryVec.clear();
				
				for (int j = 0; j < carryArray.length; j++)
				{
					//#CM31102
					// search Palette related to CarryInfo
					paletteKey.KeyClear();
					paletteKey.setPaletteId(carryArray[j].getPaletteId());
					paletteKey.setStatus(Palette.RETRIEVAL_PLAN);
					
					//#CM31103
					//  search WorkInfo related to CarryInfo
					workInfoKey.KeyClear();
					workInfoKey.setSystemConnKey(carryArray[j].getCarryKey());
					
					if (paletteHandelr.count(paletteKey) > 0 && workInfoHandelr.count(workInfoKey) > 0)
					{
						//#CM31104
						// store CarryInfo related to Palette, WorkInfo
						carryVec.add(carryArray[j]);
					}
				}
				
				CarryInformation[] resultCarryArray = new CarryInformation[carryVec.size()];
				carryVec.copyInto(resultCarryArray);
				
				//#CM31105
				// 1.multiply CarryInformation instance is not generated for the same paletteId
				//#CM31106
				// 2.With the same PaletteId, if the conveyance status is "started" and there are existing status (response wait, instructed, completed, arrival, etc)  
				//#CM31107
				//  don't generate CarryInformation instance
				
				//#CM31108
				// variable to distinct whether the same PaletteId exists or not
				Hashtable keepPaletteId = new Hashtable();
				//#CM31109
				// variable to store picking instruction data
				Vector vec = new Vector();
				
				for (int j = 0; j < resultCarryArray.length; j++)
				{
					Integer pid = new Integer(resultCarryArray[j].getPaletteId());
					if (keepPaletteId.containsKey(pid))
					{
						continue;
					}				
					//#CM31110
					// count conveyance data targeting same palette
					//#CM31111
					// if picking, location movement carry data exist, and storage data exist for the same palette
					//#CM31112
					// don't create CarryInformation instance for this carry data keeping this picking instruction as impossible
					//#CM31113
					// set search key
					carryKey.KeyClear();
					carryKey.setPaletteId(resultCarryArray[j].getPaletteId());
					carryKey.setCarryKind(CarryInformation.CARRYKIND_RETRIEVAL, "=", "(((", "", "or");
					carryKey.setCarryKind(CarryInformation.CARRYKIND_RACK_TO_RACK, "=", "", ")", "and");
					carryKey.setCmdStatus(CarryInformation.CMDSTATUS_START, ">", "(", "))", "or");
					carryKey.setCarryKind(CarryInformation.CARRYKIND_STORAGE, "=", "", "", "or");
					carryKey.setCarryKind(CarryInformation.CARRYKIND_DIRECT_TRAVEL, "=", "", ")", "and");
					if (wCarryInformationHandler.count(carryKey) < 1)
					{
						//#CM31114
						// fetch CarryInfo
						carryKey.KeyClear();
						carryKey.setCarryKey(resultCarryArray[j].getCarryKey());
						CarryInformation carry = (CarryInformation)wCarryInformationHandler.findPrimary(carryKey);
						if (carry != null)
						{
							vec.addElement(carry); 
						} 
					}
					keepPaletteId.put(pid, pid);
				}
				
				CarryInformation[] sendCarryArray = new CarryInformation[vec.size()];
				vec.copyInto(sendCarryArray);
				//#CM31115
				// send picking instruction
				sendCarry(sendCarryArray, (Station)stations[i]);
			}
		}

		//#CM31116
		// fetch location movement request data
		//#CM31117
		// it is possible to read from both DoubleDeepRetrievalSender,AutomaticChangeSender
		//#CM31118
		// lock the side first read and avoid reduntant location movement instruction
		CarryInformation[] carryMove = null;
		ASCarryInformationHandler asCarryInfoHandler = new ASCarryInformationHandler(wConn);
		carryMove = asCarryInfoHandler.getRackMoveInfoForUpdate();
		if (carryMove.length > 0)
		{
			//#CM31119
			// send picking instruction
			sendCarry(carryMove, null);
		}
		//#CM31120
		// commit the transaction to release lock in the location movement data
		wConn.commit();		
		
	}

	//#CM31121
	// Package methods -----------------------------------------------

	//#CM31122
	// Protected methods ---------------------------------------------

	//#CM31123
	/**
	 * Check the conditions of retrieval instructions. Compare the number of carrying data that instuctions have already been 
	 * released and the MAX. number of carrying operations acceptable ; if the number of data is less than operationally 
	 * available work volume, returns 'true'.
	 * Returns 'false' if the data exceeded the available operation number.
	 * @param  cInfo			CarryInformation
	 * @param  destSt			Receiving station
	 * @return					'true' if the data is less than MAX. operation work number to handle, or 'false' if exceeded.
	 * @throws Exception		Notifies if trouble occured while reading database.
	 */
	protected boolean destDetermin(CarryInformation cInfo, Station destSt) throws Exception
	{
		try
		{	
			//#CM31124
			// picking instruction condition check
			//#CM31125
			// The conditions of retrieval instrucitons to check should be changed depending on the transport section of its
			//#CM31126
			// carrying information applicable.
			if (cInfo.getCarryKind() == CarryInformation.CARRYKIND_RACK_TO_RACK)
			{
				//#CM31127
				// In case of location to location move, check should be done only for hte status of group controller.
				//#CM31128
				// By checking the location state of the receiving, determine whether/not the carrying can be carried out.
				Station station = StationFactory.makeStation(wConn, cInfo.getDestStationNumber());
				
				//#CM31129
				// Carrying is not available if the group controller has no on-line environment.				
				GroupController groupController = new GroupController(wConn, station.getControllerNumber());
				if (groupController.getStatus() != GroupController.STATUS_ONLINE)
				{
					return false;
				}
				
				//#CM31130
				// Check the state of the receiving location
				if (station instanceof Shelf)
				{
					Shelf shf = (Shelf)station;
					if (!wDoubleDeepChecker.storageCheck(cInfo, shf))
					{
						return false;
					}
					
					//#CM31131
					// Empty location search is always done at re-storing (storage) in double deep operation.
					// So if re-storing flag of CarryInformation says 're-storing to the same location', change
					// the status to 'no re-storage'.
					//#CM31132
					//CMENJP1744$CM If the CarryInformation restorage flag is set for restorage in the same location, modify without actually restoring
				if (cInfo.getReStoringFlag() == CarryInformation.RESTORING_SAME_LOC)
					{
						//#CM31133
						// to update CarryInfo
						CarryInformationAlterKey carryKey = new CarryInformationAlterKey();
						//#CM31134
						// set search key
						carryKey.KeyClear();
						carryKey.setCarryKey(cInfo.getCarryKey());
						carryKey.updateReStoringFlag(CarryInformation.RESTORING_NOT_SAME_LOC);
						//#CM31135
						// update
						wCarryInformationHandler.modify(carryKey);
					}
				}
				else
				{
					//#CM31136
					// 6026035=Acquired instance is invalid. Instance={0}
					Object[] tObj = new Object[1];
					tObj[0] = station.getClass().getName();
					RmiMsgLogClient.write(6026035, LogMessage.F_ERROR, this.getClass().getName(), tObj);
					carryFailure(cInfo);
					return false;
				}
			}
			else
			{
				//#CM31137
				// In case of retrieval, determine the receiving station and check its status.
				//#CM31138
				// Designation of the receiving station and check the transport route			
				//#CM31139
				// to search Palette
				PaletteHandler paletteHandler = new PaletteHandler(wConn);
				PaletteSearchKey paletteKey = new PaletteSearchKey();
				
				//#CM31140
				// set search key
				paletteKey.KeyClear();
				paletteKey.setPaletteId(cInfo.getPaletteId());
				
				if (wRouteController.retrievalDetermin((Palette)paletteHandler.findPrimary(paletteKey), destSt))
				{
					//#CM31141
					// Replace the receiving station with the one that RouteController class designated.
					if (!destSt.getStationNumber().equals(wRouteController.getDestStation().getStationNumber()))
					{
						destSt = wRouteController.getDestStation();
						//#CM31142
						// Set the designated receiving Station Number to the CarryInformation
						CarryInformationAlterKey carryKey = new CarryInformationAlterKey();
						//#CM31143
						// set search key
						carryKey.KeyClear();
						carryKey.setCarryKey(cInfo.getCarryKey());
						carryKey.updateDestStationNumber(destSt.getStationNumber());
					    //#CM31144
					    // update
						wCarryInformationHandler.modify(carryKey);
					}
				}
				else
				{
					//#CM31145
					// There is no transport route
					return false;
				}
				
				//#CM31146
				// Check the condition with the receiving station
				if (!destRightStation(cInfo, destSt))
				{
					//#CM31147
					// if conditions are not met at the receiving station
					return false;
				}
				
				//#CM31148
				// Check the location status of the sending station
				if (wRouteController.getSrcStation() instanceof Shelf)
				{
					Shelf shf = (Shelf)wRouteController.getSrcStation();
					if (!wDoubleDeepChecker.retrievalCheck(cInfo, shf))
					{
						return false;
					}
				}
				//#CM31149
				// double deep correspondence
				//#CM31150
				//check the current station of the palette when the aisle returns to origin
				else if (wRouteController.getSrcStation() instanceof Aisle)
				{
				    Shelf shf = (Shelf)StationFactory.makeStation(wConn, Integer.toString(cInfo.getPaletteId()));
					if (!wDoubleDeepChecker.retrievalCheck(cInfo, shf))
					{
					    return false;
					}
				}
				else
				{
					//#CM31151
					// 6026035=Acquired instance is invalid. Instance={0}
					Object[] tObj = new Object[1];
					tObj[0] = wRouteController.getSrcStation().getClass().getName();
					RmiMsgLogClient.write(6026035, LogMessage.F_ERROR, this.getClass().getName(), tObj);
					carryFailure(cInfo);
					return false;
				}
			}
			
		}
		//#CM31152
		// occurs in CarryInformation update
		catch (InvalidStatusException e)
		{
			carryFailure(cInfo);
			return false;
		}
		//#CM31153
		// occurs when Station definition is abnormal
		catch (InvalidDefineException e)
		{
			carryFailure(cInfo);
			return false;
		}
		//#CM31154
		// occurs when Station definition is standby
		catch (NotFoundException e)
		{
			carryFailure(cInfo);
			return false;
		}
		
		return true;
	}
	
	//#CM31155
	/**
	 * Check th status of the receiving station.
	 * Facility controller(AGC) , that the receiving station belongs to, has connection on-line.
	 * The receiving station is not requesting for the switch of the work mode.
	 * In case the work mode is in control: If the receiving station handles both storage/retrieval, 
	 * the work mode of the receiving station is 'retrieval'
	 * Operation of receiving station is not suspended.
	 * As for the feasible number of retrieval instructions, check to see if CarryInformation relevant to this.
	 * receiving station exceeded the volume regulated.
	 * No consideration is necessary if this station was the sending station of CarryInformation.
	 * DoubleDeep operation requires the special treatment.
	 * @param  cInfo			CarryInformation
	 * @param  destSt			receiving station
	 * @return					returns 'true' if the carry is feasible or 'false' if not.
	 * @throws Exception		Notifies if trouble occurs in reading/writing in database.
	 */
	protected boolean destRightStation(CarryInformation cInfo, Station destSt) throws Exception
	{
		//#CM31156
		// Carrying is not available if the group controller has no on-line environment		
		GroupController groupControll = new GroupController(wConn, destSt.getControllerNumber());		
		if (groupControll.getStatus() != GroupController.STATUS_ONLINE)
		{
			return false;
		}
		
		//#CM31157
		// Check the work mode of the receiving station
		// If the receiving station handles both storage and retrieve, work mode checking needs to be done.
		// If the station only operates storage, or if there is no work mode control is provided at the station,
		// no checking is necessary.
		//#CM31158
		//CMENJP1757$CM if sending station is storage/picking, mode confirmation is mandatory. if it is storage only with no mode control, mode confirmation is not needed
	if (destSt.getStationType() == Station.STATION_TYPE_INOUT && destSt.getModeType() != Station.NO_MODE_CHANGE)
		{
			//#CM31159
			// If the status of the receiving station corresponds to any of the following, determine that there
			// should be no immdediate retrieval.
			// Work mode is 'requesting to switch work mode' or 'requesting to swithc to retreival/storage mode'
			// Work mode is 'neutral' ofr 'storage'
			//#CM31160
			//CMENJP1758$CM if the work mode change request is either storage or picking mode 
			//#CM31161
			//work mode is neutral or storage
		if ((destSt.getModeRequest() != Station.NO_REQUEST)
			 || (destSt.getCurrentMode() == Station.NEUTRAL)
			 || (destSt.getCurrentMode() == Station.STORAGE_MODE))
			{
				return false;
			}
		}
		
		//#CM31162
		// Check the suspending flag of the sending station
		if (destSt.getSuspend() == Station.SUSPENDING)
		{
			//#CM31163
			// If the station is on suspention, it determines there is no immdediate retrieval.
			return false;
		}
		
		//#CM31164
		// Check the available number of retrieval instruction that can be given (whether/not the CarryInformation
		// relevant to the receiving station exceeded the volume set by regulation)		
		//#CM31165
		// fetch the picking instruction request data by station
		//#CM31166
		// to search CarryInfo
		CarryInformationSearchKey carryKey = new CarryInformationSearchKey();
		CarryInformation[] carryArray = null;

		//#CM31167
		// to search Palette
		PaletteHandler paletteHandelr = new PaletteHandler(wConn);
		PaletteSearchKey paletteKey = new PaletteSearchKey();

		//#CM31168
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
			//#CM31169
			// search
			carryArray = (CarryInformation[])wCarryInformationHandler.find(carryKey);
			
			//#CM31170
			// count picking instruction enabled number count
			int count = 0;

			for (int i = 0; i < carryArray.length; i++)
			{
				//#CM31171
				// set search key
				paletteKey.KeyClear();
				paletteKey.setPaletteId(carryArray[i].getPaletteId());
				//#CM31172
				// add picking instruction enabled number count
				count += paletteHandelr.count(paletteKey);
			}
			
			if (destSt.getMaxPaletteQuantity() <= count)
			{
				//#CM31173
				// The number of retrieval instruction exceeded the available number set by the regulation
				return false;
			}
		}
		
		return true;
	}
}
//#CM31174
//end of class
