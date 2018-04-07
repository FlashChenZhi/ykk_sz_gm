// $Id: InOutStationOperator.java,v 1.2 2006/10/26 08:40:25 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM42451
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.asrs.control.CarryCompleteOperator;

//#CM42452
/**<en>
 * Defined in this class of station is the behaviour of station which handles both storage and retrieval.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/10/25</TD><TD>K.Mori</TD><TD>For eWareNavi</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:40:25 $
 * @author  $Author: suresh $
 </en>*/
public class InOutStationOperator extends StationOperator
{
	//#CM42453
	// Class fields --------------------------------------------------

	//#CM42454
	// Class variables -----------------------------------------------
	//#CM42455
	/**<en>
	 * class name
	 </en>*/
	private final String CLASS_NAME = "InOutStationOperator";

	//#CM42456
	// Class method --------------------------------------------------
	//#CM42457
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:40:25 $") ;
	}

	//#CM42458
	// Constructors --------------------------------------------------
	//#CM42459
	/**<en>
	 * Creates a nw instance of <code>Station</code>. If the instance which already has the defined
	 * station is needed, please use <code>StationFactory</code> class.
	 * @param  conn     :Connection with database
	 * @param  snum     :own station no. preserved
	 * @throws ReadWriteException     : Notifies if any trouble occured in data access. 
	 * @see StationFactory
	 </en>*/
	public InOutStationOperator(Connection conn, String snum) throws ReadWriteException
	{
		super(conn, snum);
	}

	//#CM42460
	/**
	 * Make the instance of new <code>InOutStationOperator</code>.
	 * Maintain the Station instance passed by the argument.
	 * @param conn Database connection
	 * @param st   Station instance
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 */
	public InOutStationOperator(Connection conn, Station st) throws ReadWriteException
	{
		super(conn, st);
	}

	//#CM42461
	/**<en>
	 * This is the arrival process at the station which handles both storage and retrieval.
	 * Update process of the carry data arrived.
	 * Data will be updated according to the transport section in the carry data received.
	 * @param ci :CarryInformation to update
	 * @param plt:Palette instance
	 * @throws InvalidDefineException :Notifies if the station does not operate arrival report and if they
	 * received storage, direct travel of dummy arrival data.
	 * @throws ReadWriteException     :Notifies if any exception occur in processing with database.
	 * @throws NotFoundException      :Notifies if there is no such data.
	 </en>*/
	public void arrival(CarryInformation ci, Palette plt) throws InvalidDefineException, 
																	 ReadWriteException,
																	 NotFoundException
	{
		if (ci.getCarryKey().equals(AsrsParam.DUMMY_MCKEY))
		{
			//#CM42462
			//<en> Checks to see whether/not there was the arrival check at station.</en>
			if (getStation().isArrivalCheck())
			{
				//#CM42463
				//<en> If the station operates the arrival reporting, it records the arrival data.</en>
				registArrival(ci.getCarryKey(), plt);
				
				//#CM42464
				//<en> Requests to transmit the carry instruction.</en>
				carryRequest();
			}
			else
			{
				//#CM42465
				//<en> If the station does not operate arrival reports, arrival of dummy pallets are invalid.</en>
				//#CM42466
				//<en> 6024019=No arrival report for the station. Dummy arrival is invalid. ST No={0} mckey={1}</en>
				Object[] tObj = new Object[2] ;
				tObj[0] = getStationNumber();
				tObj[1] = ci.getCarryKey();
				RmiMsgLogClient.write(6024019, LogMessage.F_WARN, "InOutStationOperator", tObj);
				throw (new InvalidDefineException("6024019" + wDelim + tObj[0] + wDelim + tObj[1])) ;
			}
		}
		else
		{
			//#CM42467
			//<en> Branches the process according to the transport sections in CarryInformation.</en>
			switch (ci.getCarryKind())
			{
				//#CM42468
				//<en> If storing , it records the arrival data in station.</en>
				case CarryInformation.CARRYKIND_STORAGE:
					//#CM42469
					//<en> Checks to see whether/not there is arrival checks at the station.</en>
					if (getStation().isArrivalCheck())
					{
						//#CM42470
						//<en> If the station operates arrival reporting, thne records hte arrival data.</en>
						registArrival(ci.getCarryKey(), plt);
						
						//#CM42471
						//<en> And requests to transmit the carry instruction.</en>
						carryRequest();
					}
					else
					{
						//#CM42472
						//<en> If the station does not operate the arival reporting, te arrival of dummy pallet is invalid.</en>
						//#CM42473
						//<en> 6024020=No arrival report for the station. Storage arrival is invalid. ST No={0} mckey={1}</en>
						Object[] tObj = new Object[2] ;
						tObj[0] = getStationNumber();
						tObj[1] = ci.getCarryKey();
						RmiMsgLogClient.write(6024020, LogMessage.F_NOTICE, "InOutStationOperator", tObj);
						throw (new InvalidDefineException("6024020" + wDelim + tObj[0] + wDelim + tObj[1])) ;
					}
					break;

				//#CM42474
				//<en> In case of direct travel, if receiving station no. is the same as current station(arrival station no.),</en>
				//<en> it determines the load has arrived atdestination, therefore it deletesthe carry data.</en>
				//<en> If there are different no., it determines there still follows another carryinfg process, therefore</en>
				//<en> it records the arrival data.</en>
				//#CM42475
				//<en>Judge the arrival at the CMENJP3878$CM transportation destination, and delete the transportation data.</en>
				//#CM42476
				//<en>Assume the one with the following transportation, and record the arrival data when not agreeing. </en>
			case CarryInformation.CARRYKIND_DIRECT_TRAVEL:
					if (ci.getDestStationNumber().equals(getStationNumber()))
					{
						//#CM42477
						//<en> If the sattion does not operate on-line indication, or it only operates on-line indication,</en>
						//<en> arrival process should be carried out.</en>
						if ((getOperationDisplay() == Station.NOT_OPERATIONDISPLAY)
						 || (getOperationDisplay() == Station.OPERATIONDISPONLY))
						{
							//#CM42478
							//<en> Deletes the carry data (handled just as unit retrieval transfer)</en>
							CarryCompleteOperator carryOperate = new CarryCompleteOperator();
							carryOperate.setClassName(CLASS_NAME);
							carryOperate.unitRetrieval(wConn, ci, true);
						}
					}
					else
					{
						//#CM42479
						//<en> Check to see whether/not there is arrival checikng at station.</en>
						if (getStation().isArrivalCheck())
						{
							//#CM42480
							//<en> If the station operates the arrival reporting, it should record the arrival data.</en>
							registArrival(ci.getCarryKey(), plt);
							
							//#CM42481
							//<en> Requests to submit the carry instruction.</en>
							carryRequest();

							//#CM42482
							//<en> Modify the carry status to 'arrival'.</en>
							//#CM42483
							//<en> Modify the arrival date to the present day and time.</en>
							CarryInformationHandler chandl = new CarryInformationHandler(wConn);
							CarryInformationAlterKey altkey = new CarryInformationAlterKey();
							altkey.setCarryKey(ci.getCarryKey());
							altkey.updateCmdStatus(CarryInformation.CMDSTATUS_ARRIVAL);
							altkey.updateArrivalDate(new java.util.Date());
							chandl.modify(altkey);							
						}
						else
						{
							//#CM42484
							//<en> If the station does not operate the arrival reporting, the arrival of direct traveling</en>
							//<en> pallet of start point of carry is invalid.</en>
							//#CM42485
							//<en> 6024021=Arrival of the station without arrival report is invalid. ST No={0} mckey={1}</en>
							Object[] tObj = new Object[2] ;
							tObj[0] = getStationNumber();
							tObj[1] = ci.getCarryKey();
							RmiMsgLogClient.write(6024021, LogMessage.F_NOTICE, "InOutStationOperator", tObj);
							throw (new InvalidDefineException("6024021" + wDelim + tObj[0] + wDelim + tObj[1])) ;
						}
					}
					break;

				//#CM42486
				//<en> If retrieving, call the retrieval arrival process.</en>
				case CarryInformation.CARRYKIND_RETRIEVAL:
					updateArrival(ci);
					break;

				//#CM42487
				//<en> Or it returns exception for all other transport sections.</en>
				default:
					//#CM42488
					//<en> 6024018=Attribute {1} value of acquired instance {0} is invalid. {1}={2}</en>
					Object[] tObj = new Object[3] ;
					tObj[0] = "CarryInfomation";
					tObj[1] = "CarryKind";
					tObj[2] = new Integer(ci.getCarryKind()) ;
					RmiMsgLogClient.write(6024018, LogMessage.F_WARN, "InOutStationOperator", tObj);
					throw (new InvalidDefineException("6024018" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2])) ;
			}
		}
	}

	//#CM42489
	/**<en>
	 * Processing the retrieval data for hte arrival of retrieval station.
	 * It updates the carry data at the station which handles both storage and retrieval.
	 * Data will be updated according to the retrieval instruction detail in the carry data received.
	 * @param ci :CarryInformation to update
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in definition.
	 * @throws ReadWriteException     :Notifies if exception occured in processing with database.
	 * @throws NotFoundException      :Notifies if there is no such data. 
	 </en>*/
	public void updateArrival(CarryInformation ci) throws
													InvalidDefineException,
													ReadWriteException,
													NotFoundException
	{
		try
		{
			CarryCompleteOperator carryOperate = new CarryCompleteOperator();
			carryOperate.setClassName(CLASS_NAME);
			//#CM42490
			//<en> Branches the process according to the retrieval instruction detail.</en>
			switch (ci.getRetrievalDetail())
			{
				//#CM42491
				//<en> Unit retrieval</en>
				case CarryInformation.RETRIEVALDETAIL_UNIT: 
					if (getStation().isReStoringOperation())
					{
						//#CM42492
						//<en> Update of unit retrieval stocks, deleting carry data (with data creation for actual in/out)</en>
						carryOperate.unitRetrieval(wConn, ci, true);
					}
					else
					{
						//#CM42493
						//<en> Update of unit retrieval stocks, deleting carry data (with no data creation for actual in/out)</en>
						carryOperate.unitRetrieval(wConn, ci, false);
					}
					break;

				//#CM42494
				//<en> Pick retrieval, replenishing storage, inventory checking</en>
				case CarryInformation.RETRIEVALDETAIL_PICKING: 
				case CarryInformation.RETRIEVALDETAIL_ADD_STORING: 
				case CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK:
					//#CM42495
					//<en> Update the stock quantity.</en>
					carryOperate.updateStock(wConn, ci);

					CarryInformationHandler chandl = new CarryInformationHandler(wConn);
					PaletteHandler phandl = new PaletteHandler(wConn);

					//#CM42496
					//<en> Modify the carry status to 'arrival'.</en>
					CarryInformationAlterKey cinfoAltKey = new CarryInformationAlterKey();
					cinfoAltKey.setCarryKey(ci.getCarryKey());
					cinfoAltKey.updateCmdStatus(CarryInformation.CMDSTATUS_ARRIVAL);
					
					//#CM42497
					//<en> Renew CarryInformation.</en>
					//#CM42498
					//<en> Select this station for sending station no.</en>
					cinfoAltKey.updateSourceStationNumber(getStationNumber());
					//#CM42499
					//<en> Modify the transport section to 'storage'.</en>
					cinfoAltKey.updateCarryKind(CarryInformation.CARRYKIND_STORAGE);
					//#CM42500
					//<en> Modify the arrival date to present day and time.</en>
					cinfoAltKey.updateArrivalDate(new Date());
					
					//#CM42501
					//<en>in case of retrieval station number is blank</en>
					//<en>modify carryinformation's deststation to Palette's currentstation number</en>
					//#CM42502
					//<en>Make the present location of the CMENJP3903$CM palette a transportation destination. </en>
					//#CM42503
					//<en>The warning log is output because the operation not bounding irregularly. </en>
				if (StringUtil.isBlank(ci.getRetrievalStationNumber()))
					{
						//#CM42504
						//<en> 6020033=Output for debug: {0}</en>
						Object[] tObj = new Object[1] ;
						tObj[0] = "RetrievalStationNumber Blank CmdStatus() = " + Integer.toString(ci.getCmdStatus());
						RmiMsgLogClient.write(6020033, LogMessage.F_WARN, "InOutStationOperator", tObj);

						PaletteSearchKey skey = new PaletteSearchKey();
						skey.setPaletteId(ci.getPaletteId());
						Palette[] palette = (Palette[])phandl.find(skey);

						cinfoAltKey.setDestStationNumber(palette[0].getCurrentStationNumber());
					}
					else
					{
						//#CM42505
						//<en> Select retrieval station no.(location no.) for receiving station.</en>
						cinfoAltKey.updateDestStationNumber(ci.getRetrievalStationNumber());
					}
					chandl.modify(cinfoAltKey);

					//#CM42506
					//<en> Rarely on the eWareNavi side.  at the time of necessary the transportation instruction
					//#CM42507
					// The arrival data is recorded at the station in case of the station with the arrival report. </en>
					//#CM42508
					//<en> Record the arrival data if carry instruction is required on eWareNavi side 
					//#CM42509
					// and the station operates the arrival reporting.</en>
					if (getStation().getReStoringInstruction() == Station.AWC_STORAGE_SEND 
						&& getStation().isArrivalCheck())
					{
						registArrival(ci.getCarryKey(), new Palette());
						
						//#CM42510
						//<en> Requests to submit the carry instruction.</en>
						carryRequest();
					}
					
					//#CM42511
					//<en> Modify the pallet to 'storing'.</en>
					PaletteAlterKey pakey = new PaletteAlterKey();
					pakey.setPaletteId(ci.getPaletteId());
					pakey.updateStatus(Palette.STORAGE_PLAN);
					pakey.updateRefixDate(new Date());
					phandl.modify(pakey);					
					break;

				default:
					//#CM42512
					//<en> 6024018=Attribute {1} value of acquired instance {0} is invalid. {1}={2}</en>
					Object[] tObj = new Object[3] ;
					tObj[0] = "CarryInfomation";
					tObj[1] = "RetrievalDetail";
					tObj[2] = new Integer(ci.getRetrievalDetail()) ;
					RmiMsgLogClient.write(6024018, LogMessage.F_WARN, "InOutStationOperator", tObj);
					throw (new InvalidDefineException("6024018" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2])) ;
			}
		}
		catch (InvalidStatusException e)
		{
			throw new InvalidDefineException(e.getMessage());
		}
	}

	//#CM42513
	/**<en>
	 * Update processing of on-line indication and job instruction for this station.
	 * Following procedures are taken to update the job instruction for station which handles both storage and retrieval.
	 * (when the transport section is 'storage')
	 *   1.Modify the carry state in carry data to 'start'.
	 *   2.Delete data of on-line indication(<code>OperationDisplay</code>) that matches the CARRYKEY of carry data.
	 *   3.Then submits the request for carry instruction.
	 * (when the transport section is 'retrieval')
	 *   1.Delete data of on-line indiciaton(<code>OperationDisplay</code>) that matches the CARRYKEY of carry data.
	 *   2.Submit the MC work vompletion report.
	 * (when transport section is 'direct travel')
	 *   1.If the status of carry data is 'allocated', follow the same proccess as storage.
	 *   2.If the status of carry data is anything other than 'allocated', follow the process for retrieval.
	 * If this method is called for the station which has the attribute of no on-line indication, 
	 * it notifies InvalidDefineException.
	 * @param ci :objective CarryInformation instance
	 * @throws InvalidDefineException :Notifies if there are any data inconsistency in the instance.
	 * @throws ReadWriteException     :Notifies if any rtouble occured in data access. 
	 * @throws NotFoundException      :Notifies if there is no such data. 
	 </en>*/
	public void operationDisplayUpdate(CarryInformation ci) throws
																	InvalidDefineException, 
																	ReadWriteException,
																 	NotFoundException
	{
		CarryInformationHandler cih = new CarryInformationHandler(wConn);
		CarryInformationAlterKey ciAKey = new CarryInformationAlterKey();
		OperationDisplayHandler odh = new OperationDisplayHandler(wConn);
		OperationDisplaySearchKey odKey = new OperationDisplaySearchKey();

		//#CM42514
		//<en> This method is valid only for stations which operates the on-line indication.</en>
		if (getOperationDisplay() == Station.OPERATIONINSTRUCTION)
		{
			switch (ci.getCarryKind())
			{
				//#CM42515
				//<en> If storing, modify the carry status of carry data to 'start' and delete the data of</en>
				//<en> on-line indication.</en>
				case CarryInformation.CARRYKIND_STORAGE:
					//#CM42516
					//<en> alter the status of carry data to 'start'</en>
					ciAKey.setCarryKey(ci.getCarryKey());
					ciAKey.updateCmdStatus(CarryInformation.CMDSTATUS_START);
					cih.modify(ciAKey);

					//#CM42517
					//<en> Deleting the on-line indicaiton data</en>
					odKey.setCarryKey(ci.getCarryKey());
					odh.drop(odKey);
					
					//#CM42518
					//<en> Requests to submit the carry instruction.</en>
					carryRequest();
					break;

				//#CM42519
				//<en> If retrieving, delete the data of on-line indication.</en>
				//#CM42520
				//<en> Then submit the MC work completion report.</en>
				case CarryInformation.CARRYKIND_RETRIEVAL:
					odKey.setCarryKey(ci.getCarryKey());
					odh.drop(odKey);
					
					try
					{
						SystemTextTransmission.id45send(ci, wConn);
					}
					catch (Exception e)
					{
						throw new ReadWriteException(e.getMessage());
					}
					break;

				//#CM42521
				//<en> In case of direct travel, carry out the process according to the transfer status</en>
				case CarryInformation.CARRYKIND_DIRECT_TRAVEL:
					switch (ci.getCmdStatus())
					{
						//#CM42522
						//<en> If carry status is 'allocation', handle just as the storage.</en>
						//#CM42523
						//<en> Modify the carry status of carry data to 'start' and delete data of on-line indication.</en>
						case CarryInformation.CMDSTATUS_ALLOCATION:
							//#CM42524
							//<en> alter the status of carry data to 'start'</en>
							ciAKey.setCarryKey(ci.getCarryKey());
							ciAKey.updateCmdStatus(CarryInformation.CMDSTATUS_START);
							cih.modify(ciAKey);
							
							//#CM42525
							//<en> Deleting the on-line indicaiton data</en>
							odKey.setCarryKey(ci.getCarryKey());
							odh.drop(odKey);
							
							//#CM42526
							//<en> Then submits the request for carry instruction.</en>
							carryRequest();
							break;
							
						//#CM42527
						//<en> If the status indicates any other status, handle as retrieval.</en>
						//#CM42528
						//<en> Therefore, delete data of on-line indication.</en>
						//#CM42529
						//<en> Then submit the MC work completion report.</en>
						default:
							odKey.setCarryKey(ci.getCarryKey());
							odh.drop(odKey);
							try
							{
								SystemTextTransmission.id45send(ci, wConn);
							}
							catch (Exception e)
							{
								throw new ReadWriteException(e.getMessage());
							}
							break;
					}
					break;

				default:
					//#CM42530
					//<en> For all other status other than above, their work types are unavailable for processing.</en>
					//#CM42531
					//<en> It therefore throws exception.</en>
					throw new InvalidDefineException("");
			}
		}
		else
		{
			//#CM42532
			//<en> If this method is called for the station which does not operate the on-line indications, </en>
			//#CM42533
			//<en> throws exception.</en>
			throw new InvalidDefineException("");
		}
	}

	//#CM42534
	// Public methods ------------------------------------------------

	//#CM42535
	// Package methods -----------------------------------------------

	//#CM42536
	// Protected methods ---------------------------------------------

	//#CM42537
	// Private methods -----------------------------------------------

}
//#CM42538
//end of class

