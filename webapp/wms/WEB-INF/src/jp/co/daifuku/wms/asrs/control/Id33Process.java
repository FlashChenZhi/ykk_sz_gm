// $Id: Id33Process.java,v 1.2 2006/10/26 03:03:58 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM33072
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id33;
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.asrs.location.CombineZoneSelector;
import jp.co.daifuku.wms.asrs.location.RackToRackMoveSelector;
import jp.co.daifuku.wms.asrs.location.ShelfSelector;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.ZoneSelector;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.HardZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.HardZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.InOutResultAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InOutResultHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.InOutResult;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.utility.WareNaviSystemManager;
//#CM33073
/**
 * This class processes the work completion report. It generates CarryInformation from mc key, then
 * processes the arrival according to the transport section.<BR>
 * When the transport section is storage, it processes the completion of storage work according to the classification of completion. <BR>
 * When the transport section is retrieval, it processes the completion of retrieval work according to the classification of completion.
 * If CarryInformation was the carry data of empty location check, it deletes the palette for the empty location check.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/05/11</TD><TD>Inoue</TD><TD>In case of multi allocated palette's storage completion,kick the retrieval sender</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 03:03:58 $
 * @author  $Author: suresh $
 */
public class Id33Process extends IdProcess
{
	//#CM33074
	// Class fields --------------------------------------------------
	//#CM33075
	// Transport section (storage)
	private static final int TRANS_CLASS_IN = 1 ;
	//#CM33076
	// Transport section (retrieval)
	private static final int TRANS_CLASS_OUT = 2 ;
	//#CM33077
	// Transport section (location to location move - retrieval)
	private static final int TRANS_CLASS_MOVE_OUT = 4 ;
	//#CM33078
	// Transport section (location to location move - storage)
	private static final int TRANS_CLASS_MOVE_IN = 5 ;

	//#CM33079
	// Classification of completion (normal)
	private static  final int COMP_CLASS_NORMAL = 0 ;
	//#CM33080
	// Classification of completion (double occupation)
	private static  final int COMP_CLASS_DUP = 1 ;
	//#CM33081
	//Classification of completion (empty retrieval)
	private static  final int COMP_CLASS_EMPTY = 2 ;
	//#CM33082
	// Classification of completion (load size unmatch)
	private static  final int COMP_CLASS_DIM = 3 ;
	//#CM33083
	// Classification of completion (empty location check - empty)
	private static  final int COMP_CLASS_COMPARE_EMPTY = 7 ;
	//#CM33084
	// Classification of completion (empty location check - loaded)
	private static  final int COMP_CLASS_COMPARE_FULL = 8 ;
	//#CM33085
	// Classification of completion (cancel)
	private static  final int COMP_CLASS_CANCEL = 9 ;

	//#CM33086
	// Delimiter
	public static String wDelim = MessageResource.DELIM ;

	//#CM33087
	// AGCNo
	private int wAgcNumber ;
	
	//#CM33088
	/**
	 * class name
	 */
	private final String CLASSNAME = "Id33Process";

	//#CM33089
	// Class variables -----------------------------------------------
	
	//#CM33090
	// Class method --------------------------------------------------
	//#CM33091
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 03:03:58 $") ;
	}

	//#CM33092
	/**
	 * Normal process of retrieval completion report. It updates Shelf based on the retrieval instruction detail of CarryInforamtion.
	 * Besides when receiving the retrieval completion report text, this method will be used as
	 * a measure to skip texts when status of target CarryInformation was 'wait for response' during the arrival report process.
	 * @param  ci  :CarryInformation updated
	 * @throws InvalidDefineException :Notifies if update conditions or table structure was not correct.
	 * @throws ReadWriteException :Notifies if error occured when accessing database.
	 */
	public static void normalRetrievalCompletion(Connection conn, CarryInformation ci) throws InvalidDefineException, ReadWriteException
	{
		Shelf fromShelf = null;
		Object[] tObj = null;
		PaletteSearchKey pSKey = new PaletteSearchKey();
		PaletteHandler pHandle = new PaletteHandler(conn);

		try
		{
			pSKey.setPaletteId(ci.getPaletteId());
			Palette[] palette =(Palette[])pHandle.find(pSKey);
			 
			Station st = StationFactory.makeStation(conn, palette[0].getCurrentStationNumber());
			if (st instanceof Shelf)
			{
				ShelfAlterKey shelfAKey = new ShelfAlterKey();
				ShelfHandler shelfHandle = new ShelfHandler(conn);
				
				fromShelf = (Shelf)st;
				switch (ci.getRetrievalDetail())
				{
					//#CM33093
					// Releases the load presene in location at unit retrieval.
					case CarryInformation.RETRIEVALDETAIL_UNIT:
						if(ci.getCarryKind() != CarryInformation.CARRYKIND_RACK_TO_RACK)
						{
							shelfAKey.setStationNumber(fromShelf.getStationNumber());
							shelfAKey.updatePresence(Shelf.PRESENCE_EMPTY);
							shelfHandle.modify(shelfAKey);
						}	
						break;
						
					//#CM33094
					// Reserves th location for pick retrieval, inventory check and replenishment and if
					//#CM33095
					// re-storing to the same location.
					case CarryInformation.RETRIEVALDETAIL_PICKING :
					case CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK :
					case CarryInformation.RETRIEVALDETAIL_ADD_STORING  :
						//#CM33096
						//in case of DoubleDeep
						Station aislleStation = StationFactory.makeStation(conn, fromShelf.getParentStationNumber());
						if (aislleStation instanceof Aisle)
						{
							Aisle aisle = (Aisle)aislleStation;
							if (aisle.getDoubleDeepKind() == Aisle.DOUBLE_DEEP)
							{
								//#CM33097
								// Change conveyance origin location to empty location
								shelfAKey.setStationNumber(fromShelf.getStationNumber());
								shelfAKey.updatePresence(Shelf.PRESENCE_EMPTY);
								shelfHandle.modify(shelfAKey);
								
								//#CM33098
								// Carry data that relates to the relocated palette
								if (!StringUtil.isBlank(ci.getRetrievalStationNumber()))
								{
									//#CM33099
									// If picking station no. is registered, reserve the location where the palette initially existed
									Station retrievalStation = StationFactory.makeStation(conn, ci.getRetrievalStationNumber());
									if (retrievalStation instanceof Shelf)
									{
										Shelf retrievalShelf = (Shelf)retrievalStation;
										shelfAKey.setStationNumber(retrievalShelf.getStationNumber());
										shelfAKey.updatePresence(Shelf.PRESENCE_RESERVATION);
										shelfHandle.modify(shelfAKey);
									}
								}
								else
								{
									Station srcStation = StationFactory.makeStation(conn, ci.getSourceStationNumber());
									if (srcStation instanceof Shelf)
									{
										Shelf srcShelf = (Shelf)srcStation;
										//#CM33100
										// In case of stock confirmation, reserve the picking location for storage
										if (ci.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK)
										{
											shelfAKey.setStationNumber(srcShelf.getStationNumber());
											shelfAKey.updatePresence(Shelf.PRESENCE_RESERVATION);
											shelfHandle.modify(shelfAKey);
										}
										else
										{
											//#CM33101
											//In case of re-storage in the same location, reserve the original picking location for storage
											if (ci.getReStoringFlag() == CarryInformation.RESTORING_SAME_LOC)
											{
												shelfAKey.setStationNumber(srcShelf.getStationNumber());
												shelfAKey.updatePresence(Shelf.PRESENCE_RESERVATION);
												shelfHandle.modify(shelfAKey);
											}
											//#CM33102
											// If not returning to original location, make the original picking location as empty
											else
											{
												shelfAKey.setStationNumber(srcShelf.getStationNumber());
												shelfAKey.updatePresence(Shelf.PRESENCE_EMPTY);
												shelfHandle.modify(shelfAKey);
											}
										}
									}
								}
								break;
							}
						}

						//#CM33103
						//In case of re-storage in the same location, reserve the original picking location for storage
						if (ci.getReStoringFlag() == CarryInformation.RESTORING_SAME_LOC)
						{
							shelfAKey.setStationNumber(fromShelf.getStationNumber());
							shelfAKey.updatePresence(Shelf.PRESENCE_RESERVATION);
							shelfHandle.modify(shelfAKey);
						}
						//#CM33104
						// If not returning to original location, make the original picking location as empty
						else
						{
							shelfAKey.setStationNumber(fromShelf.getStationNumber());
							shelfAKey.updatePresence(Shelf.PRESENCE_EMPTY);
							shelfHandle.modify(shelfAKey);
						}
						break;
						
					//#CM33105
					// The retrieval instruction detail of instance obtained was invalid.
					default:
						//#CM33106
						// 6024018=Attribute {1} value of acquired instance {0} is invalid. {1}={2}
						tObj = new Object[3] ;
						tObj[0] = "CarryInfomation";
						tObj[1] = "RetrievalDetail";
						tObj[2] = new Integer(ci.getRetrievalDetail()) ;
						RmiMsgLogClient.write(6024018, LogMessage.F_WARN, "jp.co.daifuku.awc.control.Id33Process", tObj);
				}
				
				CarryInformationAlterKey cakey = new CarryInformationAlterKey();
				CarryInformationHandler carryInfoHandle = new CarryInformationHandler(conn);
				
				//#CM33107
				// Checks the carry status.
				switch (ci.getCmdStatus())
				{
					//#CM33108
					// Wait for response, insturction given
					case CarryInformation.CMDSTATUS_WAIT_RESPONSE:
					case CarryInformation.CMDSTATUS_INSTRUCTION:
						//#CM33109
						// Updates the carry status flag to 'complete'.
						cakey.updateCmdStatus(CarryInformation.CMDSTATUS_COMP_RETRIEVAL);
						cakey.setCarryKey(ci.getCarryKey());
						carryInfoHandle.modify(cakey);
						
						//#CM33110
						// Updates the retrieval location ID.
						//#CM33111
						// Except in case of inventory check data, the value set in stock check schedule will be used; 
						//#CM33112
						// therefore there is no updates. (measures for location to location moves)
						if (ci.getRetrievalDetail() != CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK)
						{
							cakey.updateRetrievalStationNumber(fromShelf.getStationNumber());
							cakey.setCarryKey(ci.getCarryKey());
							carryInfoHandle.modify(cakey);
						}
						else
						{
							if (StringUtil.isBlank(ci.getRetrievalStationNumber()))
							{
								//#CM33113
								// In case of inventory check, retrieval location no. is supposed to 
								//#CM33114
								// have been set; if not, set the location no. here.
								cakey.updateRetrievalStationNumber(fromShelf.getStationNumber());
								cakey.setCarryKey(ci.getCarryKey());
								carryInfoHandle.modify(cakey);
							}
						}
						break;
						
					//#CM33115
					// The retrieval instruction detail of instance obtained was invalid.
					default:
						//#CM33116
						// 6024018=Attribute {1} value of acquired instance {0} is invalid. {1}={2}
						tObj = new Object[3] ;
						tObj[0] = "CarryInfomation";
						tObj[1] = "CmdStatus";
						tObj[2] = new Integer(ci.getCmdStatus()) ;
						RmiMsgLogClient.write(6024018, LogMessage.F_WARN, "jp.co.daifuku.awc.control.Id33Process", tObj);
				}
			}
			else
			{
				//#CM33117
				// Returns exception if the instance generated was not Shelf.
				tObj = new Object[1] ;
				tObj[0] = "Shelf" ;
				//#CM33118
				// 6006008=Object other than {0} was returned.
				RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, "jp.co.daifuku.awc.control.Id33Process", tObj);
				throw new InvalidDefineException("6006008" + wDelim + tObj[0]) ;
			}
		}
		catch (SQLException se)
		{
			throw new ReadWriteException(se.getMessage()) ;
		}
		catch (NotFoundException ne)
		{
			throw new InvalidDefineException(ne.getMessage()) ;
		}

	}

	//#CM33119
	// Constructors --------------------------------------------------
	//#CM33120
	/**
     * Default constructor
     * Set GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id33Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM33121
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id33Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM33122
	// Public methods ------------------------------------------------

	//#CM33123
	// Package methods -----------------------------------------------

	//#CM33124
	// Protected methods ---------------------------------------------
	//#CM33125
	/**
	 * Processes the communication message for work completion.
	 * Based on the MC Key of received communication message, it searches <code>CarryInformation</code>
	 * and processes respectively.
	 * However the call source needs to commit or rollback the transaction, as they are not done here.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		As21Id33 id33dt = new As21Id33(rdt) ;
		
		//#CM33126
		// Processes as much carry data received.
		for (int i=0; i < id33dt.getMcKey().length; i++)
		{
			//#CM33127
			// Allocates hte processes according to the transport section.
			int[] transClass = id33dt.getTransportationClassification() ;
			switch (transClass[i])
			{
				//#CM33128
				// Storage or location to location move (storage)
				case TRANS_CLASS_IN:
				case TRANS_CLASS_MOVE_IN:
					storageCompletion(id33dt, i) ;
					break ;

				//#CM33129
				// Retrieval or location to location move (retrieval)
				case TRANS_CLASS_OUT:
				case TRANS_CLASS_MOVE_OUT:
					retrievalCompletion(id33dt, i) ;
					break ;
					
				//#CM33130
				// Completion classification error
				default:
					//#CM33131
					// 6024016=Invalid transfer category. (Work Completion Report text) Category={0} mckey={1}
					Object[] tObj = new Object[2] ;
					tObj[0] = Integer.toString(transClass[i]);
					tObj[1] = id33dt.getMcKey()[i];
					RmiMsgLogClient.write(6024016, LogMessage.F_WARN, this.getClass().getName(), tObj);
					throw new InvalidProtocolException("6024016" + wDelim + tObj[0] + wDelim + tObj[1]);
			}
		}
	}

	//#CM33132
	/**
	 * Search empty location for relocation and return it
	 * @param  plt Target palette for relocation instance
	 * @param  wh Conveyance warehouse instance
	 * @return <code>Shel</code> instance for relocation
	 * @throws ReadWriteException Throw exception if database read fails
	 * @throws InvalidDefineException Throw exception if the modify contents are illegal
	 */
	protected Shelf findRackToRackMove(Palette plt, WareHouse wh) throws ReadWriteException, InvalidDefineException
	{
		//#CM33133
		// Search soft zone, hard zone
		ZoneSelector zn = new CombineZoneSelector(wConn);
		//#CM33134
		// Decide empty location search methodology (empty location search class for relocation)
		ShelfSelector dpt = new RackToRackMoveSelector(wConn, zn);
		
		ShelfAlterKey sk = new ShelfAlterKey();
		ShelfHandler shelfHandle = new ShelfHandler(wConn);

		try
		{
			Shelf location = dpt.select(plt, wh);

			if (location != null)
			{
				//#CM33135
				// Setting the searched location as 'reserved for storage'.
				sk.setStationNumber(location.getStationNumber());
				sk.updatePresence(Shelf.PRESENCE_RESERVATION);
				shelfHandle.modify(sk);
	
				return location;
			}
			return null;
		}
		catch (ReadWriteException e1)
		{
			throw new InvalidDefineException(e1.getMessage());
		}
		catch (InvalidDefineException e1)
		{
			throw new InvalidDefineException(e1.getMessage());
		}
		catch (NotFoundException e1)
		{
			throw new InvalidDefineException(e1.getMessage());
		}
	}

	//#CM33136
	// Private methods -----------------------------------------------
	//#CM33137
	/**
	 * Processes the storage completion (including the storage of location to location moves).
	 * Branches the process acording to the completion classificaiton of id33dt isntance.
	 * 1. Normal end
	 *    - Releases the reservation of storage shelf and switch the load presernce ON.
	 *    - Compares the storage location and receiving station (To-store location), then clear the
	 *      reservations of locations which do not match.
	 *    - Deletes the carry data.
	 *    - Specifies that current position of pallet as storage locations.
	 *    - If there is no other allocation exist to the pallet, releases the allocation and it modifies
	 *      to loaded location. If allocation exists, reserve it for retrieval.
	 * 2 Double occupation
	 *    - Processing the doube operation.
	 * 3 Load size unmatch
	 *    - Processing the load size unmatch
	 * @param  id33dt  :text content of work completion report
	 * @param  num     :element no. of data arrays for work completion report text detail
	 * @throws  Exception  :in case any error occured
	 */
	private void storageCompletion(As21Id33 id33dt, int num) throws Exception
	{
		//#CM33138
		// Definition of variable-----------------------------------
		CarryInformationHandler wCIHandler = new CarryInformationHandler(wConn) ;
		CarryInformationSearchKey wCIKey = new CarryInformationSearchKey() ;
		CarryInformationAlterKey cakey = new CarryInformationAlterKey();
		
		PaletteSearchKey pSKey = new PaletteSearchKey();
		PaletteAlterKey pakey = new PaletteAlterKey();
		PaletteHandler pHandle = new PaletteHandler(wConn);
		
		int[] compClass = id33dt.getCompletionClassification() ;
		CarryInformation ci ;
		Object[] tObj = null;

		String compStorageLocation = id33dt.getLocationNo()[num];

		//#CM33139
		// Start of process-------------------------------------
		//#CM33140
		// Sets MC key as a search condition.
		wCIKey.setCarryKey(id33dt.getMcKey()[num]) ;
		
		//#CM33141
		// Retrieves corresponding CarryInfo.
		Entity earr[] = wCIHandler.find(wCIKey) ;

		//#CM33142
		// There is no corresponding data.
		if (earr.length == 0)
		{
			//#CM33143
			// 6026038=Transfer data for the designated MCKey does not exist. mckey={0}
			tObj = new Object[1] ;
			tObj[0] = id33dt.getMcKey()[num];
			RmiMsgLogClient.write(6026038 , LogMessage.F_ERROR, this.getClass().getName(), tObj);
			return ;
		}

		if (earr[0] instanceof CarryInformation)
		{
			ci = (CarryInformation)earr[0] ;
		}
		else
		{
			tObj = new Object[1] ;
			tObj[0] = "CarryInformation" ;
			//#CM33144
			// 6006008=Object other than {0} was returned.
			RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw (new InvalidProtocolException("6006008" + wDelim + tObj[0])) ;
		}

		//#CM33145
		// Retrieves the instance of destines station.
		Station toStation = StationFactory.makeStation(wConn, compStorageLocation);
		Shelf toShelf = null;
		if (toStation instanceof Shelf)
		{
			toShelf = (Shelf)toStation ;
		}
		else
		{
			tObj = new Object[1] ;
			tObj[0] = "Shelf" ;
			//#CM33146
			// 6006008=Object other than {0} was returned.
			RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw (new InvalidProtocolException("6006008" + wDelim + tObj[0])) ;
		}

		//#CM33147
		// If carry status of CarryInforamtion is 'wait for resonse', it determines that texts has been skipped;
		//#CM33148
		// it firstly processes the response to carry instruction.
		if (ci.getCmdStatus() == CarryInformation.CMDSTATUS_WAIT_RESPONSE)
		{
			//#CM33149
			// Logging that response text to carry instruction has been skipped.
			//#CM33150
			// 6022021=No reply for transfer instruction. Forcing to complete picking. mckey={0}
			tObj = new Object[1] ;
			tObj[0] = ci.getCarryKey() ;
			RmiMsgLogClient.write(6022021, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			
			//#CM33151
			// Executes the reply to carry instruction for this CarryInformation.
			Id25Process.updateNormalResponce(wConn, ci);
			
			//#CM33152
			// Processing the completion of pick up.
			//#CM33153
			// Checks the on-line indication data, and delete if found any.
			OperationDisplayHandler odh = new OperationDisplayHandler(wConn);
			OperationDisplaySearchKey odkey = new OperationDisplaySearchKey();
			odkey.setCarryKey(ci.getCarryKey());
			if (odh.count(odkey) != 0)
			{
				odh.drop(odkey);
			}
		}
		
		//#CM33154
		// In case the instruction of carry status was given for CarryInforamtion,
		//#CM33155
		// it determines that
		//#CM33156
		// pick completion text was skipped and it processes the pick-up completi
		if (ci.getCmdStatus() == CarryInformation.CMDSTATUS_INSTRUCTION
		 || ci.getCmdStatus() == CarryInformation.CMDSTATUS_ARRIVAL)
		{
			//#CM33157
			// Processing the pick-up completion.
			//#CM33158
			// Checks the on-line indicationdata, the ndelete id any was found.
			OperationDisplayHandler odh = new OperationDisplayHandler(wConn);
			OperationDisplaySearchKey odkey = new OperationDisplaySearchKey();
			odkey.setCarryKey(ci.getCarryKey());
			if (odh.count(odkey) != 0)
			{
				odh.drop(odkey);
			}
		}

		//#CM33159
		// Actual completion process ---------------------------------
		//#CM33160
		// Divides the processes according to the status of completions.
		switch (compClass[num])
		{
			//#CM33161
			// Normal end
			case COMP_CLASS_NORMAL:
				ShelfAlterKey shelfAltKey = new ShelfAlterKey();
				ShelfHandler shelfHandle = new ShelfHandler(wConn);
				//#CM33162
				// Release the reservation of storage location, and turn the load presence ON.
				shelfAltKey.setStationNumber(compStorageLocation);
				shelfAltKey.updatePresence(Shelf.PRESENCE_STORAGED);
				shelfHandle.modify(shelfAltKey);

				//#CM33163
				// Compares the storage location with receiving station.
				//#CM33164
				// If htey do not match, clear the reservation of location.
				if(!compStorageLocation.equals(ci.getDestStationNumber()))
				{
					//#CM33165
					// Releases the reservation of station which was to be carried, and  turn the load presence OFF.
					shelfAltKey.KeyClear();
					shelfAltKey.setStationNumber(ci.getDestStationNumber());
					shelfAltKey.updatePresence(Shelf.PRESENCE_EMPTY);
					shelfHandle.modify(shelfAltKey);
				}
				
				//#CM33166
				// Delete Carry data
				wCIKey.KeyClear();
				wCIKey.setCarryKey(ci.getCarryKey());
				wCIHandler.drop(wCIKey);

				//#CM33167
				// modifies the Current position of pallet to the destination.
				pSKey.KeyClear();
				pSKey.setPaletteId(ci.getPaletteId());
				Palette[] palette = (Palette[])pHandle.find(pSKey);
				Palette pl ;
				pl = palette[0] ;

				//#CM33168
				// double deep correspondence
				//#CM33169
				// In case of relocation completion, move the current palette position to relocation destination
				//#CM33170
				// Update relocation origin location to empty location
				if (ci.getCarryKind() == CarryInformation.CARRYKIND_RACK_TO_RACK)
				{
					String sourceLocation = id33dt.getLtoLocationNo()[num];
					pakey.KeyClear();
					pakey.setPaletteId(ci.getPaletteId());
					pakey.updateCurrentStationNumber(ci.getDestStationNumber());
					pakey.updateRefixDate(new java.util.Date());
					pHandle.modify(pakey);

					//#CM33171
					// Update relocation origin station to empty location
					shelfAltKey.KeyClear();
					shelfAltKey.setStationNumber(sourceLocation);
					shelfAltKey.updatePresence(Shelf.PRESENCE_EMPTY);
					shelfHandle.modify(shelfAltKey);
					//#CM33172
					// Update relocation destination station to result location
					shelfAltKey.KeyClear();
					shelfAltKey.setStationNumber(ci.getDestStationNumber());
					shelfAltKey.updatePresence(Shelf.PRESENCE_STORAGED);
					shelfHandle.modify(shelfAltKey);		
					
					//#CM33173
					//If Carry data that relates to same palette exist, update that carry data origin to relocation destination
					CarryInformationHandler ciHandle = new CarryInformationHandler(wConn);
					CarryInformationSearchKey key = new CarryInformationSearchKey();
					key.setPaletteId(pl.getPaletteId());
					CarryInformation []multiCI = (CarryInformation[])ciHandle.find(key);
					if (multiCI != null && multiCI.length > 0)
					{
						for (int multiCount = 0 ; multiCount < multiCI.length ; multiCount++)
						{
							cakey.KeyClear();
							cakey.setCarryKey(multiCI[multiCount].getCarryKey());
							//#CM33174
							// In case of stock confirmation data, set the original station for picking station no.
							if (multiCI[multiCount].getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK)
							{
								shelfAltKey.KeyClear();
								shelfAltKey.setStationNumber(compStorageLocation);
								shelfAltKey.updatePresence(Shelf.PRESENCE_RESERVATION);
								shelfHandle.modify(shelfAltKey);
								
								cakey.updateRetrievalStationNumber(compStorageLocation);
							}

							pSKey.KeyClear();
							pSKey.setPaletteId(multiCI[multiCount].getPaletteId());
							Palette[] mPalette = (Palette[])pHandle.find(pSKey);
							cakey.updateSourceStationNumber(mPalette[0].getCurrentStationNumber());
							ciHandle.modify(cakey);
						}
					}
					//#CM33175
					//Kick RetrievalSender during relocation completion
					//#CM33176
					// while sending picking send instruction, use RMI message for picking request
					SendRequestor req = new SendRequestor();
					req.retrieval();
				}
				else
				{
					//#CM33177
					// Set the completed storage location for the current palette position
					pakey.KeyClear();
					pakey.setPaletteId(pl.getPaletteId());
					pakey.updateCurrentStationNumber(compStorageLocation);
					pakey.updateRefixDate(new java.util.Date());
					pHandle.modify(pakey);
					
					CarryInformationHandler ciHandle = new CarryInformationHandler(wConn);
					CarryInformationSearchKey key = new CarryInformationSearchKey();
					key.setPaletteId(pl.getPaletteId());
					CarryInformation[] multiCI = (CarryInformation[])ciHandle.find(key);
					if (multiCI != null && multiCI.length > 0)
					{
						for (int multiCount = 0 ; multiCount < multiCI.length ; multiCount++)
						{
							pSKey.KeyClear();
							pSKey.setPaletteId(multiCI[multiCount].getPaletteId());
							Palette[] mPalette = (Palette[])pHandle.find(pSKey);
							
							cakey.KeyClear();
							cakey.setCarryKey(multiCI[multiCount].getCarryKey());
							cakey.updateSourceStationNumber(mPalette[0].getCurrentStationNumber());
							//#CM33178
							// Update with aisle station no.
							ShelfHandler wShelfHandler = new ShelfHandler(wConn);
							ShelfSearchKey wShelfKey = new ShelfSearchKey();				
							wShelfKey.KeyClear();
							wShelfKey.setStationNumber(mPalette[0].getCurrentStationNumber());
					
							try
							{
								Shelf rShelf = (Shelf)wShelfHandler.findPrimary(wShelfKey);
								if(rShelf == null)
								{
									cakey.updateAisleStationNumber(null);
								}
								else
								{
									cakey.updateAisleStationNumber(rShelf.getParentStationNumber());
								}								
							}
							catch (NoPrimaryException e)
							{
								throw new ReadWriteException(e.getMessage());
							}
				
							ciHandle.modify(cakey);							
						}
						//#CM33179
						// Kick picking instruction so that picking occurs in same palette in case of multiple allocation
						//#CM33180
						// while sending picking send instruction, use RMI message for picking request
						SendRequestor req = new SendRequestor();
						req.retrieval();
					}
				}

				//#CM33181
				// Update palette status
				pakey.KeyClear();
				pakey.setPaletteId(pl.getPaletteId());
				
				//#CM33182
				// Checks whether/not there are no other allocation for pallets.
				CarryInformationSearchKey countKey = new CarryInformationSearchKey();
				countKey.setPaletteId(pl.getPaletteId()) ;
				if (wCIHandler.count(countKey) == 0)
				{
					//#CM33183
					// Search stock info from palette
					StockSearchKey sSKey = new StockSearchKey();
					StockHandler sHandle = new StockHandler(wConn);
					sSKey.setPaletteid(pl.getPaletteId());
					Stock[] stocks = (Stock[])sHandle.find(sSKey);
					
					//#CM33184
					// If there are any item codes for double occupations, alter thier states to error locations.
					if (stocks[0].getItemCode().equals(AsrsParam.IRREGULAR_ITEMKEY))
					{
						//#CM33185
						// Alters the location status to error.
						pakey.updateStatus(Palette.IRREGULAR);
					}
					else
					{
						//#CM33186
						// Alter location status to loaded.
						pakey.updateStatus(Palette.REGULAR);
					}
					
					//#CM33187
					// Alters the allocation flag to unallocated.
					pakey.updateAllocation(Palette.NOT_ALLOCATED);
				}
				else
				{
					//#CM33188
					// Alters the status to 'reserved for retrieval'.
					pakey.updateStatus(Palette.RETRIEVAL_PLAN);
					
					//#CM33189
					// while sending picking send instruction, use RMI message for picking request
					SendRequestor req = new SendRequestor();
					req.retrieval();
				}
				
				pakey.updateRefixDate(new java.util.Date());
				pHandle.modify(pakey);
				
				CarryCompleteOperator carryOperator = new CarryCompleteOperator();
				carryOperator.updateInventoryCheckInfo(wConn, ci);
				break ;

			//#CM33190
			// Double occupations
			case COMP_CLASS_DUP:
				doubleStorage(ci, toShelf, id33dt, num) ;
				break ;

			//#CM33191
			// Load size unmatch
			case COMP_CLASS_DIM:
				loadMisalignment(ci, toShelf, id33dt, num) ;
				break ;

			//#CM33192
			// Invalid classification of completion
			default:
				//#CM33193
				// Alters the status of carry data to error.
				cakey.KeyClear();
				cakey.updateCmdStatus(CarryInformation.CMDSTATUS_ERROR);
				cakey.setCarryKey(ci.getCarryKey());
				wCIHandler.modify(cakey);

				//#CM33194
				// Logging of message
				//#CM33195
				// 6024017=Invalid completion category. (Work Completion Report text) Category={0} mckey={1}
				tObj = new Object[2] ;
				tObj[0] = Integer.toString(compClass[num]) ;
				tObj[1] = id33dt.getMcKey()[num] ;
				RmiMsgLogClient.write(6024017, LogMessage.F_WARN, this.getClass().getName(), tObj);
		} // end of switch
	}
	
	//#CM33196
	/**
	 * Procesing the retrieval completion(including the retrievals for location to location moves).
	 * @param  id33dt  :contents of work completion report text
	 * @param  num     :element no. of data arrays for work completion report text detail
	 * @throws  Exception  :in case any error occured
	 */
	private void retrievalCompletion(As21Id33 id33dt, int num) throws Exception
	{
		//#CM33197
		// Definition of variable-------------------------------------
		PaletteSearchKey pSKey = new PaletteSearchKey();
		PaletteHandler pHandle = new PaletteHandler(wConn);
		
		CarryInformationHandler wCIHandler = new CarryInformationHandler(wConn) ;
		CarryInformationSearchKey wCIKey = new CarryInformationSearchKey() ;
		CarryInformationAlterKey cakey = new CarryInformationAlterKey();;
		
		CarryCompleteOperator carryCompOpe = new CarryCompleteOperator();
		carryCompOpe.setClassName(CLASSNAME);
		
		int[] compClass = id33dt.getCompletionClassification() ;
		CarryInformation ci ;
		Object[] tObj = null;

		//#CM33198
		// Start of process -------------------------------------
		//#CM33199
		// Sets MC key as a search condition
		wCIKey.setCarryKey(id33dt.getMcKey()[num]) ;
		
		//#CM33200
		// Retrieves correspondng CarryInfo.
		Entity earr[] = wCIHandler.find(wCIKey) ;
		
		//#CM33201
		// There is no corresponding data.
		if(earr.length == 0)
		{
			//#CM33202
			// 6026038=Transfer data for the designated MCKey does not exist. mckey={0}
			tObj = new Object[1] ;
			tObj[0] = id33dt.getMcKey()[num];
			RmiMsgLogClient.write(6026038 , LogMessage.F_ERROR, this.getClass().getName(), tObj);
			return ;
		}

		if (earr[0] instanceof CarryInformation)
		{
			ci = (CarryInformation)earr[0] ;
		}
		else
		{
			tObj = new Object[1] ;
			tObj[0] = "CarryInformation" ;
			//#CM33203
			// 6006008=Object other than {0} was returned.
			RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw (new InvalidProtocolException("6006008" + wDelim + tObj[0])) ;
		}

		//#CM33204
		// Actual completion process ---------------------------------
		//#CM33205
		// Divides the process according to the status of completion.
		switch (compClass[num])
		{
			//#CM33206
			// Loaded location completion
			//#CM33207
			// After outputting the message, normal processing will be done.
			case COMP_CLASS_COMPARE_FULL:
				pSKey.setPaletteId(ci.getPaletteId());
				Palette[] palette = (Palette[])pHandle.find(pSKey);
			
				//#CM33208
				// 6020006=An occupied location is retrieved in empty location check. Picking Location No.={0}
				tObj = new Object[1] ;
				tObj[0] = palette[0].getCurrentStationNumber() ;
				RmiMsgLogClient.write(6020006, LogMessage.F_INFO, this.getClass().getName(), tObj);

			//#CM33209
			// Normal completion
			case COMP_CLASS_NORMAL:
				normalRetrievalCompletion(wConn, ci);
				break ;

			//#CM33210
			// Empty retrieval
			case COMP_CLASS_EMPTY:
				retrievalError(ci) ;
				break ;

			//#CM33211
			// Empty location completion
			case COMP_CLASS_COMPARE_EMPTY:
				emptyLocationCompletion(ci);
				break ;

			//#CM33212
			// Cancel
			case COMP_CLASS_CANCEL:
				//#CM33213
				// Cancels the retrieval data 
				carryCompOpe.cancelRetrieval(wConn,ci) ;
				break;

			//#CM33214
			// Invalid classificaiotn of completion
			default:
				//#CM33215
				// Alters the status of carry data to error.
				cakey.updateCmdStatus(CarryInformation.CMDSTATUS_ERROR);
				cakey.setCarryKey(ci.getCarryKey());
				wCIHandler.modify(cakey);
				
				//#CM33216
				// Logging of the message
				//#CM33217
				// 6024017=Invalid completion category. (Work Completion Report text) Category={0} mckey={1}
				tObj = new Object[2] ;
				tObj[0] = Integer.toString(compClass[num]) ;
				tObj[1] = id33dt.getMcKey()[num] ;
				RmiMsgLogClient.write(6024017, LogMessage.F_WARN, this.getClass().getName(), tObj);
		} // end of switch
	}

	//#CM33218
	// Private methods -----------------------------------------------
	//#CM33219
	/**
	 * Processes the double occupations.
	 * @param  ci     :carry information
	 * @param  shelf  :Shelf of storage location
	 * @param  id33dt :contents of work completion report text
	 * @param  num    :element no. of data array for work completion report text detail
	 * @throws  Exception  :in case any error occured
	 */
	private void doubleStorage(CarryInformation ci, Shelf shelf, As21Id33 id33dt, int num) throws Exception
	{
		//#CM33220
		// Flag which indicates whether/not there are alternative locations to carry to.
		boolean stat = false;
		
		//#CM33221
		// Definition of variable-------------------------------------
		SequenceHandler sequence = new SequenceHandler(wConn);
		String stockId_seq = sequence.nextStockId();
		int paletteId_seq = sequence.nextPaletteId();

		PaletteSearchKey pSKey = new PaletteSearchKey();
		PaletteAlterKey pAKey = new PaletteAlterKey();
		PaletteHandler pHandle = new PaletteHandler(wConn);

		StockAlterKey sAKey = new StockAlterKey();
		StockHandler sHandle = new StockHandler(wConn);

		pSKey.setPaletteId(ci.getPaletteId());
		Palette[] palette = (Palette[])pHandle.find(pSKey);
		
		String orgLocationNo = palette[0].getCurrentStationNumber();

		//#CM33222
		// Start of the process -------------------------------------

		//#CM33223
		// Generates the palette and Stock tables for double occupations
		//#CM33224
		// Sets the station no.
		Stock stk = new Stock();
		stk.setStockId(stockId_seq);
		stk.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		stk.setAreaNo(palette[0].getWHStationNumber());
		stk.setConsignorCode(WmsParam.IRREGULAR_CONSIGNORCODE);
		stk.setItemCode(AsrsParam.IRREGULAR_ITEMKEY);
		stk.setLocationNo(shelf.getStationNumber());
		stk.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
		stk.setStockQty(1);
		stk.setAllocationQty(1);
		stk.setInstockDate(new Date());
		stk.setPaletteid(paletteId_seq);
		stk.setRegistPname(CLASSNAME);
		stk.setLastUpdatePname(CLASSNAME);

		Palette dpl = new Palette();
		dpl.setPaletteId(paletteId_seq);
		dpl.setCurrentStationNumber(shelf.getStationNumber()) ;
		dpl.setWHStationNumber(palette[0].getWHStationNumber());
		dpl.setStatus(Palette.IRREGULAR);
		dpl.setAllocation(Palette.NOT_ALLOCATED);
		dpl.setPaletteTypeId(palette[0].getPaletteTypeId());
		dpl.setHeight(palette[0].getHeight());
		dpl.setBcData(palette[0].getBcData());
		dpl.setRefixDate(new Date());

		//#CM33225
		// Creates the Palette and Stock tables
		pHandle.create(dpl) ;
		sHandle.create(stk);

		//#CM33226
		// Alters the status of location, attempted to store, to the loaded.
		ShelfAlterKey sk = new ShelfAlterKey();
		ShelfHandler shelfHandle = new ShelfHandler(wConn);
		sk.setStationNumber(shelf.getStationNumber());
		sk.updatePresence(Shelf.PRESENCE_STORAGED);
		shelfHandle.modify(sk);
		
		//#CM33227
		// Search the alternative locations within the same aisle.
		Shelf altloc = getAlternativeLocation(ci, shelf.getStationNumber()) ;
		if (altloc == null)
		{
			Station st = StationFactory.makeStation(wConn, ci.getSourceStationNumber());
			//#CM33228
			// If the aisle station No. is not in defined (due to aisle connected station)
			if (StringUtil.isBlank(st.getAisleStationNumber()))
			{
				//#CM33229
				// If there is no alternative location was found in the same aisle, conduct the empty
				//#CM33230
				// location search all over the warehouse.
				altloc = getAlternativeLocation(ci, ci.getSourceStationNumber()) ;
			}
		}
		
		CarryInformationAlterKey cakey = new CarryInformationAlterKey();
		CarryInformationHandler wCIHandler = new CarryInformationHandler(wConn) ;

		if (altloc != null)
		{
			//#CM33231
			// Get the load size data from location data/hard zone data to send to equipment.
			//#CM33232
			// Then set the acquired value for pallet. 
			HardZoneHandler hhandl = new HardZoneHandler(wConn);
			HardZoneSearchKey key = new HardZoneSearchKey();
			key.setHardZoneID(altloc.getHardZoneID());
			HardZone[] hzones = (HardZone[])hhandl.find(key);
			if (hzones.length == 0)
			{
				//#CM33233
				// Set the status of carry data "error" if there is no hard zone data. 
				//#CM33234
				// 6026070=Hard zone of the specified location is not found. LocationNo.:{0} Hard zone ID:{1}
				Object[] tObj = new Object[2];
				tObj[0] = altloc.getStationNumber();
				tObj[1] = Integer.toString(altloc.getHardZoneID());
				RmiMsgLogClient.write(6026070, LogMessage.F_ERROR, this.getClass().getName(), tObj);
				
				//#CM33235
				// There is no alternative location or reject station. Change the carry status to "error".
				cakey.KeyClear();
				cakey.updateCmdStatus(CarryInformation.CMDSTATUS_ERROR);
				cakey.setCarryKey(ci.getCarryKey());
				wCIHandler.modify(cakey);
				
				//#CM33236
				// Specify sending station as the current location of pallet. 
				pAKey.KeyClear();
				pAKey.updateCurrentStationNumber(ci.getSourceStationNumber());
				pAKey.updateRefixDate(new java.util.Date());
				pAKey.setPaletteId(palette[0].getPaletteId());
				pHandle.modify(pAKey);
				
				//#CM33237
				// Set the location no. with stock info as the origin station
				sAKey.KeyClear();
				sAKey.setPaletteid(palette[0].getPaletteId());
				sAKey.updateLocationNo(ci.getSourceStationNumber());
				sAKey.updateLastUpdatePname(CLASSNAME);
				sHandle.modify(sAKey);
			}
			else
			{
				//#CM33238
				// Update with destination station no. determined by the search.
				cakey.KeyClear();
				cakey.setCarryKey(ci.getCarryKey());
				cakey.updateDestStationNumber(altloc.getStationNumber());
				
				//#CM33239
				// Update with aisle station no.
				cakey.updateAisleStationNumber(altloc.getParentStationNumber());
				wCIHandler.modify(cakey);
				
				//#CM33240
				// Current position of pallet must be altered to the alternative location.
				//#CM33241
				// Do not cancel location in case of relocation
				if(ci.getCarryKind() != CarryInformation.CARRYKIND_RACK_TO_RACK)
				{	
					pAKey.KeyClear();
					pAKey.setPaletteId(palette[0].getPaletteId());
					pAKey.updateCurrentStationNumber(altloc.getStationNumber());
					pAKey.updateRefixDate(new java.util.Date());
					pHandle.modify(pAKey);

					//#CM33242
					// Make stock info related to palette as alternate location
					sAKey.KeyClear();
					sAKey.setPaletteid(palette[0].getPaletteId());
					sAKey.updateLocationNo(altloc.getStationNumber());
					sAKey.updateLastUpdatePname(CLASSNAME);
					sHandle.modify(sAKey);
				}
				
				//#CM33243
				// Replace the size of pallet with the size of retrieved hard zone.
				pAKey.KeyClear();
				pAKey.updateHeight(hzones[0].getHeight());
				pAKey.setPaletteId(palette[0].getPaletteId());
				pHandle.modify(pAKey);			

				//#CM33244
				// Modifies the LocationNumber in result data.
				InOutResultAlterKey ikey = new InOutResultAlterKey();
				InOutResultHandler ioh = new InOutResultHandler(wConn);
				ikey.setCarryKey(ci.getCarryKey());
				ikey.updateLocationNumber(altloc.getStationNumber());
				ioh.modify(ikey);
				stat = true;
			}
		}
		else
		{
			//#CM33245
			// There is no alternative location or reject station. Change the carry status to "error".
			cakey.KeyClear();
			cakey.setCarryKey(ci.getCarryKey());
			cakey.updateCmdStatus(CarryInformation.CMDSTATUS_ERROR);
			wCIHandler.modify(cakey);
			
			//#CM33246
			// Specifies the current position of pallet as sending station.
			pAKey.KeyClear();
			pAKey.setPaletteId(palette[0].getPaletteId());
			pAKey.updateCurrentStationNumber(ci.getSourceStationNumber());
			pAKey.updateRefixDate(new java.util.Date());
			pHandle.modify(pAKey);
			
			//#CM33247
			// Set the location no. with stock info as the origin station
			sAKey.KeyClear();
			sAKey.setPaletteid(palette[0].getPaletteId());
			sAKey.updateLocationNo(ci.getSourceStationNumber());
			sAKey.updateLastUpdatePname(CLASSNAME);
			sHandle.modify(sAKey);
		}
		
		//#CM33248
		// drop result of relocated stock
		insertHostSend(ci.getCarryKey(), palette[0].getPaletteId(), orgLocationNo);
		
		CarryInformationSearchKey ciKey = new CarryInformationSearchKey();
		ciKey.setCarryKey(ci.getCarryKey());
		CarryInformation sendCarry = (CarryInformation) wCIHandler.findPrimary(ciKey);
		
		//#CM33249
		// Submitting the isntruction for the alternative location.
		SystemTextTransmission.id11send(sendCarry, SystemTextTransmission.CALSS_DOBULE_STRAGE, stat, wConn) ;

		//#CM33250
		// Logging of this process.
		Object[] tObj = new Object[3] ;
		tObj[0] = shelf.getStationNumber() ;
		if (stat)
		{
			tObj[1] = sendCarry.getDestStationNumber() ;
		}
		else
		{
			//#CM33251
			// There is no alternative location.
			tObj[1] = "000000000000";
		}
		//#CM33252
		// 6022029=A double occupancy was detected. Destination will be changed. {0} ---> {1} mckey={2}
		tObj[2] = sendCarry.getCarryKey() ;
		RmiMsgLogClient.write(6022029, LogMessage.F_NOTICE, this.getClass().getName(), tObj);
	}
	
	//#CM33253
	/**
	 * Processing the load size unmatch.
	 * @param  ci     :carry information
	 * @param  shelf  :Shelf of storage location
	 * @param  id33dt :contents of work completion report text
	 * @param  num    :element no. of data array for work completion report text detail
	 * @throws  Exception  :in case any error occured
	 */
	private void loadMisalignment(CarryInformation ci, Shelf shelf, As21Id33 id33dt, int num) throws Exception
	{
		//#CM33254
		// Definition of variable-------------------------------------
		ShelfAlterKey sk = new ShelfAlterKey();
		ShelfHandler shelfHandle = new ShelfHandler(wConn);
		
		CarryInformationAlterKey cakey = new CarryInformationAlterKey();
		CarryInformationHandler carryHandle = new CarryInformationHandler(wConn);
		
		PaletteSearchKey pSKey = new PaletteSearchKey();
		PaletteAlterKey pAKey = new PaletteAlterKey();
		PaletteHandler pHandle = new PaletteHandler(wConn);
		
		StockAlterKey sAKey = new StockAlterKey();
		StockHandler sHandle = new StockHandler(wConn);
		
		pSKey.setPaletteId(ci.getPaletteId());
		Palette[] palette = (Palette[])pHandle.find(pSKey);
		
		String orgLocationNo = palette[0].getCurrentStationNumber();
		
		//#CM33255
		// Flag which indicates whether/not there are any alternative destinations.
		boolean stat = false;

		//#CM33256
		// Start of the process -------------------------------------

		//#CM33257
		// Alters the status of this shelf, attempted to store, to empty.
		sk.KeyClear();
		sk.setStationNumber(shelf.getStationNumber());
		sk.updatePresence(Shelf.PRESENCE_EMPTY);
		shelfHandle.modify(sk);

		//#CM33258
		// Retrieves the load size information from the work completion report text and sets.
		palette[0].setHeight(id33dt.getDimension()[num]);

		//#CM33259
		// Search the alternative locations within the same aisle. Selects only locations that matche the 
		//#CM33260
		// specified load size for the subject of search.
		palette[0].setCurrentStationNumber(shelf.getParentStationNumber());
		
		Shelf altloc = getloadMisalignmentAlternativeLocation(palette[0]) ;
		if (altloc == null)
		{
			//#CM33261
			// If there is no alternative location was found in the same aisle, conduct the empty
			//#CM33262
			// location search all over the warehouse.
			palette[0].setCurrentStationNumber(ci.getSourceStationNumber());
			altloc = getloadMisalignmentAlternativeLocation(palette[0]) ;
		}
		if (altloc != null)
		{
			cakey.KeyClear();
			cakey.setCarryKey(ci.getCarryKey());
			cakey.setCarryKey(ci.getCarryKey());
			//#CM33263
			// Update with destination station no. determined by the search.
			cakey.updateDestStationNumber(altloc.getStationNumber());
			//#CM33264
			// Update with aisle station no.
			cakey.updateAisleStationNumber(altloc.getParentStationNumber());
			carryHandle.modify(cakey);

			//#CM33265
			// Current position of pallet must be altered to the alternative location.
			// Thenu update the load size.
			pAKey.KeyClear();
			pAKey.setPaletteId(palette[0].getPaletteId());
			pAKey.updateCurrentStationNumber(altloc.getStationNumber());
			pAKey.updateHeight(id33dt.getDimension()[num]);
			pHandle.modify(pAKey);
			
			//#CM33266
			// Make stock info related to palette as alternate location
			sAKey.KeyClear();
			sAKey.setPaletteid(palette[0].getPaletteId());
			sAKey.updateLocationNo(altloc.getStationNumber());
			sAKey.updateLastUpdatePname(CLASSNAME);
			sHandle.modify(sAKey);
			
			stat = true;
		}
		else
		{
			//#CM33267
			// If there is no alternative location is found, search the reject station.
			Station rjst = getRejectStation(ci.getSourceStationNumber());
			if (rjst != null)
			{
				//#CM33268
				// Update with destination station no. determined by the search.
				cakey.KeyClear();
				cakey.updateDestStationNumber(rjst.getStationNumber());
				cakey.setCarryKey(ci.getCarryKey());
				carryHandle.modify(cakey);
				
				//#CM33269
				// Current position of pallet must be altered to the sending station.
				pAKey.KeyClear();
				pAKey.setPaletteId(palette[0].getPaletteId());
				pAKey.updateCurrentStationNumber(ci.getSourceStationNumber());
				pHandle.modify(pAKey);
				
				//#CM33270
				// Set the location no. of the stock info, related to palette as the origin station
				sAKey.KeyClear();
				sAKey.setPaletteid(palette[0].getPaletteId());
				sAKey.updateLocationNo(ci.getSourceStationNumber());
				sAKey.updateLastUpdatePname(CLASSNAME);
				sHandle.modify(sAKey);
				
				stat = true;
			}
			else
			{
				//#CM33271
				// There is no either alternative location or reject station. Sets the carry status error.
				cakey.KeyClear();
				cakey.updateCmdStatus(CarryInformation.CMDSTATUS_ERROR);
				cakey.setCarryKey(ci.getCarryKey());
				carryHandle.modify(cakey);
				
				//#CM33272
				// Current position of pallet must be altered to the sending station.
				pAKey.KeyClear();
				pAKey.setPaletteId(palette[0].getPaletteId());
				pAKey.updateCurrentStationNumber(ci.getSourceStationNumber());
				pHandle.modify(pAKey);
				
				//#CM33273
				// Set the location no. of the stock info, related to palette as the origin station
				sAKey.KeyClear();
				sAKey.setPaletteid(palette[0].getPaletteId());
				sAKey.updateLocationNo(ci.getSourceStationNumber());
				sAKey.updateLastUpdatePname(CLASSNAME);
				sHandle.modify(sAKey);
				
				stat = false;
			}
		}

		//#CM33274
		// drop result of relocated stock
		insertHostSend(ci.getCarryKey(), palette[0].getPaletteId(), orgLocationNo);
		
		CarryInformationSearchKey ciKey = new CarryInformationSearchKey();
		ciKey.setCarryKey(ci.getCarryKey());
		CarryInformation sendCarry = (CarryInformation) carryHandle.findPrimary(ciKey);
		
		//#CM33275
		// Submitting the instruction for alternative location
		SystemTextTransmission.id11send(sendCarry, SystemTextTransmission.CALSS_LOAD_MISALIGNMENT, stat, wConn) ;

		//#CM33276
		// Logging of this process
		Object[] tObj = new Object[3] ;
		tObj[0] = shelf.getStationNumber() ;
		if (stat)
		{
			tObj[1] = sendCarry.getDestStationNumber() ;
		}
		else
		{
			//#CM33277
			// There is no alternative location
			tObj[1] = "000000000000";
		}
		//#CM33278
		// 6022030=Load size mismatch occurred. Destination will be changed. {0} ---> {1} mckey={2}
		tObj[2] = sendCarry.getCarryKey() ;
		RmiMsgLogClient.write(6022030, LogMessage.F_NOTICE, this.getClass().getName(), tObj);
	}

	//#CM33279
	/**
	 * If location change is generated at completion, create stock of palette contents, maintenance result
	 * Create maintenance decrease (origin location), maintenance increase (destination location) result of the same stock
	 * Use this method after the relocation process is complete
	 * 
	 * @param carryKey conveyance key of substitute instruction
	 * @param paletteId palette of substitute instruction
	 * @param orgLocationNo origin location no.
	 * @throws Exception throw any exception occurred
	 */
	private void insertHostSend(String carryKey, int paletteId, String orgLocationNo) throws Exception
	{
		StockHandler stkh = new StockHandler(wConn);
		StockSearchKey stkKey = new StockSearchKey();
		stkKey.setPaletteid(paletteId);
		stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		Stock[] stocks = (Stock[]) stkh.find(stkKey);
		
		WareNaviSystemManager wms = new WareNaviSystemManager(wConn);
		String sysDate = wms.getWorkDate();
		
		HostSendHandler hostsendHandler = new HostSendHandler(wConn);
		HostSend hostsend = new HostSend();
		SequenceHandler sequence = new SequenceHandler(wConn);
		
		String batch_seq = sequence.nextNoPlanBatchNo();

		for (int i = 0; i < stocks.length; i++)
		{
			if ((stocks[i].getConsignorCode().equals(WmsParam.EMPTYPB_CONSIGNORCODE) 
				&& stocks[i].getItemCode().equals(AsrsParam.EMPTYPB_ITEMKEY))
			||  (stocks[i].getConsignorCode().equals(WmsParam.IRREGULAR_CONSIGNORCODE) 
				&& stocks[i].getItemCode().equals(AsrsParam.IRREGULAR_ITEMKEY)))
			{
				continue;
			}
			String newLocationNo = stocks[i].getLocationNo();
			
			hostsend.setWorkDate(sysDate);
			hostsend.setStatusFlag(HostSend.STATUS_FLAG_COMPLETION);
			hostsend.setBeginningFlag(HostSend.BEGINNING_FLAG_STARTED);
			hostsend.setStockId(stocks[i].getStockId());
			hostsend.setAreaNo(stocks[i].getAreaNo());
			hostsend.setLocationNo(stocks[i].getLocationNo());
			hostsend.setConsignorCode(stocks[i].getConsignorCode());
			hostsend.setConsignorName(stocks[i].getConsignorName());
			hostsend.setSupplierCode(stocks[i].getSupplierCode());
			hostsend.setSupplierName1(stocks[i].getSupplierName1());
			hostsend.setItemCode(stocks[i].getItemCode());
			hostsend.setItemName1(stocks[i].getItemName1());
			hostsend.setHostPlanQty(0);
			hostsend.setPlanQty(0);
			hostsend.setPlanEnableQty(0);
			hostsend.setResultQty(stocks[i].getStockQty());
			hostsend.setShortageCnt(0);
			hostsend.setPendingQty(0);
			hostsend.setEnteringQty(stocks[i].getEnteringQty());
			hostsend.setBundleEnteringQty(stocks[i].getBundleEnteringQty());
			hostsend.setCasePieceFlag(stocks[i].getCasePieceFlag());
			hostsend.setWorkFormFlag(stocks[i].getCasePieceFlag());
			hostsend.setItf(stocks[i].getItf());
			hostsend.setBundleItf(stocks[i].getBundleItf());
			hostsend.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
			hostsend.setUseByDate(stocks[i].getUseByDate());
			hostsend.setLotNo(stocks[i].getLotNo());
			hostsend.setPlanInformation(stocks[i].getPlanInformation());
			hostsend.setResultUseByDate(stocks[i].getUseByDate());
			hostsend.setResultLotNo(stocks[i].getLotNo());
			hostsend.setResultLocationNo(stocks[i].getLocationNo());
			//#CM33280
			// Work report flag (Not yet reported)
			hostsend.setReportFlag(HostSend.REPORT_FLAG_NOT_SENT);
			hostsend.setSystemConnKey(carryKey);
			hostsend.setSystemDiscKey(HostSend.SYSTEM_DISC_KEY_ASRS);
			hostsend.setBatchNo(batch_seq);
			//#CM33281
			// Plan info registering date/time
			hostsend.setPlanRegistDate(null);
			hostsend.setRegistPname(CLASSNAME);
			hostsend.setLastUpdatePname(CLASSNAME);

			//#CM33282
			// subtraction data register (maintenance stock decrease)
			String jobnoMinus_seq = sequence.nextJobNo();
			hostsend.setJobNo(jobnoMinus_seq);
			hostsend.setJobType(WorkingInformation.JOB_TYPE_MAINTENANCE_MINUS);
			hostsend.setCollectJobNo(jobnoMinus_seq);
			hostsend.setLocationNo(orgLocationNo);
			hostsend.setResultLocationNo(orgLocationNo);
			hostsendHandler.create(hostsend);

			//#CM33283
			// data registering of current addition (maintenance stock increase)
			String jobnoPlus_seq = sequence.nextJobNo();
			hostsend.setJobNo(jobnoPlus_seq);
			hostsend.setJobType(WorkingInformation.JOB_TYPE_MAINTENANCE_PLUS);
			hostsend.setCollectJobNo(jobnoPlus_seq);
			hostsend.setLocationNo(newLocationNo);
			hostsend.setResultLocationNo(newLocationNo);
			hostsendHandler.create(hostsend);
		}
	}

	//#CM33284
	/**
	 * Processing the empty retrieval.
	 * Deletes the specified carry data and the pallet data.
	 * There will be not creation of result data.
	 * @param ci :carry data to process the empty retrieval
	 * @throws  Exception  :in case any error occured
	 */
	private void retrievalError(CarryInformation ci) throws Exception
	{
		PaletteSearchKey pSKey = new PaletteSearchKey();
		PaletteHandler pHandle = new PaletteHandler(wConn);
		
		pSKey.setPaletteId(ci.getPaletteId());
		Palette[] palette = (Palette[])pHandle.find(pSKey);
		
		Shelf fromShelf = null;
		Station st = StationFactory.makeStation(wConn, palette[0].getCurrentStationNumber());

		CarryInformationAlterKey cakey = new CarryInformationAlterKey();
		CarryInformationHandler carryHandle = new CarryInformationHandler(wConn);

		CarryCompleteOperator carryCompOpe = new CarryCompleteOperator();

		if (st instanceof Shelf)
		{
			fromShelf = (Shelf) st;
			//#CM33285
			// Submit the altenative location text.
			SystemTextTransmission.id11send(ci, SystemTextTransmission.CALSS_RETRIEVAL_ERROR, false, wConn) ;
			
			//#CM33286
			// In order to record the results, 
			cakey.updateRetrievalStationNumber(fromShelf.getStationNumber());
			cakey.setCarryKey(ci.getCarryKey());
			carryHandle.modify(cakey);
			
			//#CM33287
			// Delete the carry data and create the result data.
			carryCompOpe.drop (wConn, ci, InOutResult.WORKTYPE_EMPTYRETRIEVAL, true, CarryCompleteOperator.COMPLETE_ERROR_SHORTAGE);
			
			//#CM33288
			// Logging of this process
			//#CM33289
			// 6022031=Empty picking occurred. Transfer data will be deleted. Picking location No.={0} mckey={1}
			RmiMsgLogClient.write("6022031" + wDelim + palette[0].getCurrentStationNumber()
											+ wDelim + ci.getCarryKey(),
											this.getClass().getName());
		}
		else
		{
			//#CM33290
			// Returns exception if generated instance was not Shelf.
			//#CM33291
			// 6006008=Object other than {0} was returned.
			RmiMsgLogClient.write("6006008" + wDelim + "Shelf",this.getClass().getName());
			throw new InvalidDefineException("6006008" + wDelim + "Shelf") ;
		}
	}

	//#CM33292
	/**
	 * Processing the completion of empty location.
	 * @param  ci :carry information
	 * @throws  Exception  :in case any error occured
	 */
	private void emptyLocationCompletion(CarryInformation ci) throws Exception
	{
		CarryCompleteOperator carryCompOpe = new CarryCompleteOperator();
		
		//#CM33293
		// Delete the carry data.
		carryCompOpe.drop (wConn, ci, true);
		
		//#CM33294
		// Check the inventory check data, and if there is no data of inventory check works,
		//#CM33295
		// set the status of inventory check 'unprocessed'.
		carryCompOpe.emptyLocationCheckOff(wConn, ci);
	}

	//#CM33296
	/**
	 * Search the alternative location for double occupation.
	 * @param  ci   :CarryInformation instance to determine the destination
	 * @param  stNo :StationNo
	 * @return :instance of receiving station. Or Shelf instance if alternative location was searched. Returns null if there is none.
	 * @throws Exception :Notifies if exception occurs in accessing data.
	 * @throws Exception :Notifies if there are data inconsistency.
	 */
	private Shelf getAlternativeLocation(CarryInformation ci, String stNo) throws Exception
	{
		PaletteSearchKey pSKey = new PaletteSearchKey();
		PaletteHandler pHandle = new PaletteHandler(wConn);
		
		pSKey.setPaletteId(ci.getPaletteId());
		Palette palette = (Palette)pHandle.findPrimary(pSKey);
		palette.setCurrentStationNumber(stNo);
		
		Station st = StationFactory.makeStation(wConn, stNo);
		
		WareHouseSearchKey whSkey = new WareHouseSearchKey();
		WareHouseHandler wWareHouseHandler = new WareHouseHandler(wConn);
		whSkey.setStationNumber(st.getWHStationNumber());
		WareHouse wareHouse = (WareHouse)wWareHouseHandler.findPrimary(whSkey);

		//#CM33297
		//Add empty location search of relocation carry data
		//#CM33298
		// Search soft zone, hard zone
		ZoneSelector zn = new CombineZoneSelector(wConn);
		//#CM33299
		// Decide empty location search methodology (empty location search class for relocation)
		ShelfSelector dpt = new RackToRackMoveSelector(wConn, zn);
		Shelf location = null;
		if(ci.getCarryKind() == CarryInformation.CARRYKIND_RACK_TO_RACK)
		{
			location = findRackToRackMove(palette, wareHouse);
		}
		else
		{
			location = dpt.select(palette, wareHouse);
		}

		if (location != null)
		{
			//#CM33300
			// Alter the status of searched location to 'reserved for storage'.
			ShelfAlterKey shelfAkey = new ShelfAlterKey();
			ShelfHandler shelfHandle = new ShelfHandler(wConn);
			shelfAkey.setStationNumber(location.getStationNumber());
			shelfAkey.updatePresence(Shelf.PRESENCE_RESERVATION);
			shelfHandle.modify(shelfAkey);
			
			return location;
		}
		else
		{
			return null;
		}
	}

	//#CM33301
	/**
	 * Search the alternative location for in case of load size unmatch.
	 * @param  ci :CarryInformation instance which determines the destinations.
	 * @return :instance of receiving instance. Or Shelf instance if alternative location was searched. Returns null if there is none.
	 * @throws Exception :Notifies if exception occurs in accessing data.
	 * @throws Exception :Notifies if there are data inconsistency.
	 */
	private Shelf getloadMisalignmentAlternativeLocation(Palette plt) throws Exception
	{
		ShelfAlterKey sk = new ShelfAlterKey();
		ShelfHandler shelfHandle = new ShelfHandler(wConn);
		
		WareHouseSearchKey key = new WareHouseSearchKey();
		WareHouseHandler wWareHouseHandler = new WareHouseHandler(wConn);
		
		//#CM33302
		// Search soft zone, hard zone
		ZoneSelector zn = new CombineZoneSelector(wConn);
		//#CM33303
		// Decide empty location search methodology (empty location search class for relocation)
		ShelfSelector dpt = new RackToRackMoveSelector(wConn, zn);
		
		Station st = StationFactory.makeStation(wConn, plt.getCurrentStationNumber());		

		key.setStationNumber(st.getWHStationNumber());
		WareHouse wareHouse = (WareHouse)wWareHouseHandler.findPrimary(key);
		
		Shelf location = dpt.select(plt, wareHouse);
		
		if (location != null)
		{
			//#CM33304
			// Setting the searched location as 'reserved for storage'.
			sk.KeyClear();
			sk.setStationNumber(location.getStationNumber());
			sk.updatePresence(Shelf.PRESENCE_RESERVATION);
			shelfHandle.modify(sk);
			
			return location;
		}
		else
		{
			return null;
		}
	}

	//#CM33305
	/**
	 * According to the specified sending station no., it designates hte reject station and returns.
	 * If the reject station was not found, it returns null.
	 * @param  stnum :sending station no.
	 * @return :instance of reject instance. it returns null if undefined.
	 * @throws Exception :Notifies if exception occurs in accessing data.
	 * @throws Exception :Notifies if there are data inconsistency.
	 */
	private Station getRejectStation(String stnum) throws Exception
	{
		Station st = StationFactory.makeStation(wConn, stnum);

		if (StringUtil.isBlank(st.getRejectStationNumber()))
		{
			return null;
		}
		else
		{
			Station rejectSt = StationFactory.makeStation(wConn, st.getRejectStationNumber());
			return rejectSt;
		}
	}
}
//#CM33306
//end of class
