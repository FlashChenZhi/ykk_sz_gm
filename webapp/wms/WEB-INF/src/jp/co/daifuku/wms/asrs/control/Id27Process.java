// $Id: Id27Process.java,v 1.2 2006/10/26 03:06:20 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM32870
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id27;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.dbhandler.AisleAlterKey;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
//#CM32871
/**
 * This is the conveyance change request class
 * The process is called based on the CarryInformation conveyance type
 * In case of storage, look for other storage locations and send modify instructions.
 * If not found but reject station define file exist, use it as alternate.
 * If nothing is found, send conveyance change instruction as desabled. Picking, direct
 * transfer and relocation process can't be done
 * Send conveyance point modify instruction as disabled
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2006/02/02</TD><TD>Y.Okamura</TD><TD>eWareNavi�Ή�</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 03:06:20 $
 * @author  $Author: suresh $
 */
public class Id27Process extends IdProcess
{
	//#CM32872
	// Class fields --------------------------------------------------

	//#CM32873
	// Class variables -----------------------------------------------
	//#CM32874
	/**
	 * Preserves the target AGCNO.
	 */
	 private int  wAgcNumber;

	//#CM32875
	// Class method --------------------------------------------------
	//#CM32876
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 03:06:20 $") ;
	}

	//#CM32877
	// Constructors --------------------------------------------------
	//#CM32878
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id27Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM32879
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id27Process(int num)
	{
		super() ;
		wAgcNumber = num ;
	}

	//#CM32880
	// Public methods ------------------------------------------------

	//#CM32881
	// Package methods -----------------------------------------------

	//#CM32882
	// Protected methods ---------------------------------------------
	//#CM32883
	/**
	 * Processing the desination change request.
	 * According to the MC Key in received communication message, it searches <code>CarryInformation</code> and 
	 * processes respectively.
	 * However the call source needs to commit or rollback the transaction, as they are not done here.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		CarryInformation ci ;
		As21Id27 id27dt = new As21Id27(rdt) ;
		CarryInformationHandler cih = new CarryInformationHandler(wConn) ;
		CarryInformationSearchKey cskey = new CarryInformationSearchKey() ;

		//#CM32884
		// Logging that desination change request was received.
        Object[] tObj = new Object[1] ;
		tObj[0] = id27dt.getMcKey() ;
		//#CM32885
		// 6022026=Transfer destination change request is received. mckey={0}
		//#CM32886
		// 6022026=Transfer destination change request is received. mckey={0}
		RmiMsgLogClient.write(6022026, LogMessage.F_NOTICE, this.getClass().getName(), tObj);

		//#CM32887
		// Sets MC key as a search condition.
		String mckey = id27dt.getMcKey() ;
		cskey.setCarryKey(mckey) ;
		
		//#CM32888
		// Obtains the corresponding CarryInfo.
		CarryInformation[] earr = (CarryInformation[]) cih.find(cskey) ;
		
		//#CM32889
		// There is no corresponding data.
		if(earr.length == 0)
		{
			tObj = new Object[1] ;
			tObj[0] = mckey;
			//#CM32890
			// 6026038=Transfer data for the designated MCKey does not exist. mckey={0}
			RmiMsgLogClient.write(6026038 , LogMessage.F_ERROR, "Id27Process", tObj);
			return ;
		}

		if (earr[0] instanceof CarryInformation)
		{
			ci = (CarryInformation)earr[0] ;
			//#CM32891
			// Checks the transport section.
			switch (ci.getCarryKind())
			{
				//#CM32892
				// Storage
				case CarryInformation.CARRYKIND_STORAGE:
					storageChange(id27dt, ci) ;
					break;
					
				//#CM32893
				// Picking, Direct transfer, Relocation
				case CarryInformation.CARRYKIND_RETRIEVAL:
				case CarryInformation.CARRYKIND_DIRECT_TRAVEL:
				case CarryInformation.CARRYKIND_RACK_TO_RACK:
				default:
					//#CM32894
					// In a normal system, picking, direct transfer and relocation conveyance point change instruction is not possible
			        tObj = new Object[2] ;
					tObj[0] = new Integer(ci.getCarryKind()) ;
					tObj[1] = new Integer(ci.getCarryKey()) ;
					//#CM32895
					// 6022027=Transfer destination change request cannot be processed. Transfer category={0} mckey={1}
					RmiMsgLogClient.write(6022027, LogMessage.F_NOTICE, this.getClass().getName(), tObj);
					break;
			}
		}
		else
		{
			tObj[0] = "CarryInformation" ;
			//#CM32896
			// 6006008=Object other than {0} was returned.
			RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, "Id27Process", tObj);
			throw (new InvalidProtocolException("6006008" + wDelim + tObj[0])) ;
		}
	}

	//#CM32897
	// Private methods -----------------------------------------------
	//#CM32898
	/**
	 * Modification process when storing. It searches the location other than the aisle the storage was attempted this time.
	 * If the alternative location is determined, the instruction of alternative location must be sent to AGC. 
	 * If there is no alterenative locations, and if the reject station is defined in sending station,
	 * it shoud send submit the instruction for alternative location to the reject station.
	 * If there is no wither alternative locations or stations, it should send the instuction with no alterenative destinations.
	 * @param  Id27dt :Contents of destination change request
	 * @param  ci     :Carry data
	 * @throws  Exception  :in case any error occured
	 */
	private void storageChange(As21Id27 Id27dt, CarryInformation ci) throws Exception
	{
		//#CM32899
		// Definition of variable -------------------------------------
		CarryInformationHandler cih = new CarryInformationHandler(wConn) ;
		CarryInformationAlterKey cakey = new CarryInformationAlterKey();
		
		PaletteSearchKey pSKey = new PaletteSearchKey();
		PaletteAlterKey pAKey = new PaletteAlterKey();
		PaletteHandler pHandle = new PaletteHandler(wConn);
		
		StockAlterKey stkAKey = new StockAlterKey();
		StockHandler stkHandle = new StockHandler(wConn);
					
		ShelfAlterKey shelfAKey = new ShelfAlterKey();
		ShelfHandler shelfHandle = new ShelfHandler(wConn);
		
		AisleAlterKey aileAKey = new AisleAlterKey();
		AisleHandler aileHandle = new AisleHandler(wConn);
		
		//#CM32900
		// Obtaining the instance of sending station
		Station fromSt = StationFactory.makeStation(wConn, ci.getSourceStationNumber());
		//#CM32901
		// Obtaining the instance of receiving station
		Station destSt = StationFactory.makeStation(wConn, ci.getDestStationNumber());
		
		try
		{
			if (destSt instanceof Shelf)
			{
				pSKey.KeyClear();
				pSKey.setPaletteId(ci.getPaletteId());
				Palette pl = (Palette) pHandle.findPrimary(pSKey);
				
				boolean isUpdateAisle = false;
				Shelf destShelf = (Shelf) destSt;
				Station destAisle = StationFactory.makeStation(wConn, destShelf.getParentStationNumber());
				if (destAisle.getStatus() == Station.STATION_OK)
				{
					//#CM32902
					// Alter the status of this aisle, attempted for the storage this time, to 'unavailable'
					//#CM32903
					// so that it should be excluded from empty location search.
					aileAKey.KeyClear();
					aileAKey.setStationNumber(destAisle.getStationNumber());
					aileAKey.updateStatus(Station.STATION_NG);
					aileHandle.modify(aileAKey);
					
					isUpdateAisle = true;					
				}
				
				//#CM32904
				// Set the sending station to current position of pallet in order to search the empty location again.
				pl.setCurrentStationNumber(fromSt.getStationNumber());
				
				//#CM32905
				// Search the alternative location.
				RouteController rc = new RouteController(wConn);
				if (rc.storageDetermin(pl, destShelf.getWHStationNumber()))
				{
					//#CM32906
					// Alter the searched alternative location or the reject station to destined station.
					cakey.KeyClear();
					cakey.setCarryKey(ci.getCarryKey());
					cakey.updateDestStationNumber(rc.getDestStation().getStationNumber());
					cih.modify(cakey);

					if (rc.getDestStation() instanceof Shelf)
					{
						//#CM32907
						// If alternative location is determined, aisle satation must be also updated.
						cakey.KeyClear();
						cakey.updateAisleStationNumber(rc.getDestStation().getParentStationNumber());
						cakey.setCarryKey(ci.getCarryKey());
						cih.modify(cakey);

						pAKey.KeyClear();
						pAKey.setPaletteId(pl.getPaletteId());
						pAKey.updateCurrentStationNumber(rc.getDestStation().getStationNumber());
						pHandle.modify(pAKey);
						
						stkAKey.KeyClear();
						stkAKey.setPaletteid(pl.getPaletteId());
						stkAKey.updateLocationNo(rc.getDestStation().getStationNumber());
						stkHandle.modify(stkAKey);
					}
					else
					{
						//#CM32908
						// If reject station is selected as alternate destination, transport section must be 
						//#CM32909
						// altered to 'direct travel'.
						cakey.KeyClear();
						cakey.setCarryKey(ci.getCarryKey());
						cakey.updateCarryKind(CarryInformation.CARRYKIND_DIRECT_TRAVEL);
						cih.modify(cakey);

						pAKey.KeyClear();
						pAKey.setPaletteId(pl.getPaletteId());
						pAKey.updateCurrentStationNumber(fromSt.getStationNumber());
						pHandle.modify(pAKey);
						
						stkAKey.KeyClear();
						stkAKey.setPaletteid(pl.getPaletteId());
						stkAKey.updateLocationNo(fromSt.getStationNumber());
						stkHandle.modify(stkAKey);
					}
				}
				else
				{
					//#CM32910
					// Set brank in desetination in order to submit there is no receiving station.
					cakey.KeyClear();
					cakey.setCarryKey(ci.getCarryKey());
					cakey.updateDestStationNumber(" ");
					cakey.updateCmdStatus(CarryInformation.CMDSTATUS_ERROR);
					cih.modify(cakey);
				}
				
				//#CM32911
				// If the aisle was kept 'unavailable' temporarily, then reset to 'available' again.
				if (isUpdateAisle)
				{
					aileAKey.KeyClear();
					aileAKey.setStationNumber(destAisle.getStationNumber());
					aileAKey.updateStatus(Station.STATION_OK);
					aileHandle.modify(aileAKey);					
				}

				//#CM32912
				// Set back the previous receiving location to empty location.
				shelfAKey.KeyClear();
				shelfAKey.setStationNumber(destShelf.getStationNumber());
				shelfAKey.updatePresence(Shelf.PRESENCE_EMPTY);
				shelfHandle.modify(shelfAKey);				
			}
			else
			{
				//#CM32913
				// Exception if the receiving station generated was anything other than the location.
				//#CM32914
				// Set brank in desetination in order to submit there is no receiving station.
				cakey.KeyClear();
				cakey.setCarryKey(ci.getCarryKey());
				cakey.updateDestStationNumber(" ");
				cakey.updateCmdStatus(CarryInformation.CMDSTATUS_ERROR);
				cih.modify(cakey);
				
				//#CM32915
				// 6026035=Acquired instance is invalid. Instance={0}
				Object[] tObj = new Object[1] ;
				tObj[0] = destSt.getClass().getName() ;
				RmiMsgLogClient.write(6026035, LogMessage.F_WARN, this.getClass().getName(), tObj);
				throw new InvalidDefineException ("6026035" + wDelim + tObj[0]) ;
			}
		}
		catch (Exception e)
		{
			//#CM32916
			// Set brank in desetination in order to submit there is no receiving station.
			cakey.KeyClear();
			cakey.updateDestStationNumber(" ");
			cakey.updateCmdStatus(CarryInformation.CMDSTATUS_ERROR);
			cakey.setCarryKey(ci.getCarryKey());
			cih.modify(cakey);			
		}
		finally
		{
			CarryInformationSearchKey ciKey = new CarryInformationSearchKey();
			ciKey.setCarryKey(ci.getCarryKey());
			CarryInformation resultCi = (CarryInformation) cih.findPrimary(ciKey);
			//#CM32917
			// Submit the instruction of destination change.
			SystemTextTransmission.id08send(resultCi, Id27dt.getAgcData(), wConn) ;
			
			Object[] tObj = new Object[2] ;
			tObj[0] = fromSt.getStationNumber() ;
			tObj[1] = resultCi.getDestStationNumber() ;
			//#CM32918
			// 6022028=Transfer destination change instruction is sent. ST No. {0} ---> {1}
			RmiMsgLogClient.write(6022028, LogMessage.F_NOTICE, this.getClass().getName(), tObj);
		}
	}
}
//#CM32919
//end of class

