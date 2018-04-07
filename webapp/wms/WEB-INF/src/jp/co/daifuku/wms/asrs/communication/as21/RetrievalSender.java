// $Id: RetrievalSender.java,v 1.2 2006/10/26 00:55:01 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM31442
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Vector;

import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.dbhandler.ASCarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.rmi.RmiServAbstImpl;
//#CM31443
/**
 * This class sends the retrieval instruction based on the AS21 communication ptrotocol.<br>
 * It retrieves the CarryInformation, which is the sending object as retrieval instruction.
 * If the conditions for retrieval are met, the retrieval instruction will be sent to the target AGC.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/17</TD><TD>M.INOUE</TD><TD>modify a way to 'wait', from 'sleep()' to 'wait()'</TD></TR>
 * <TR><TD>2004/03/18</TD><TD>M.INOUE</TD><TD>Corrected the process of sending carry instructions over the possible quantity. </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 00:55:01 $
 * @author  $Author: suresh $
 */
public class RetrievalSender extends RmiServAbstImpl implements java.lang.Runnable
{
	//#CM31444
	// Class fields --------------------------------------------------
	//#CM31445
	/**
	 * Object name to bind to the remote object
	 */
	public static final String OBJECT_NAME = "RetrievalSender" ;

	//#CM31446
	// Class variables -----------------------------------------------
	//#CM31447
	/**
	 * Connection for database use
	 */
	protected Connection wConn = null;

	//#CM31448
	/**
	 * Default value for delivering timing of instructions; actually set by the caller
	 */
	protected static int wSleepTime = 1000 ;

	//#CM31449
	/**
	 * number of instructions to set in each retrieval instruction text
	 */
	private static int SEND_MAX = 2;

	//#CM31450
	/**
	 * Handler class to manipulate the carrying data 
	 */
	protected CarryInformationHandler wCarryInformationHandler = null;

	//#CM31451
	/**
	 * Handler class to manipulate the Station data
	 */
	protected StationHandler wStationHandler = null;

	//#CM31452
	/**
	 * control class of transport route
	 */
	protected RouteController wRouteController = null;

	//#CM31453
	/**
	 * Delimiter
	 * Deliniter of the parameter for the MessageDef when Exception occured
	 */
	public String wDelim = MessageResource.DELIM ;

	//#CM31454
	/**
	 * Sleep time when ther is carrying data but not able to send
	 */
	private int wExistSleepTime = 1000;

	//#CM31455
	/**
	 * This flag is to determine whether/not to terminate the RetrievalSender class.
	 * When ExitFlag changed to true, it pulls out of the infinite loop in run() method.
	 * For the updating of this flag, stop() method should be used.
	 */
	private boolean wExitFlag = false;

	//#CM31456
	/**
	 * This flag is to determine whether/not RetrievalSender should be in wait state.
	 * wRequest flag alters its status according to the retrieval requests from the external.
	 * When wRequest is true, the processing of the retrieval instruction will not change to wait state.
	 */
	private boolean wRequest = false;

	//#CM31457
	// Class method --------------------------------------------------
 	//#CM31458
 	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 00:55:01 $") ;
	}

	//#CM31459
	// Constructors --------------------------------------------------
	//#CM31460
	/**
	 * Create new instance of <code>RetrievalSender</code>.
	 * The connection will be obtained  from parameter of AS/RS system out of resource.
	 * @throws ReadWriteException : Notifies if exception occured during the database connection.
	 * @throws RemoteException  Exception related to communication generated while executing remote method call
	 */
	public RetrievalSender() throws ReadWriteException, RemoteException
	{
		try
		{
			//#CM31461
			// Establish the connection with DataBase. Users name is obtained from the resource file.
			wConn = AsrsParam.getConnection();
			restRequest();
			initHandler();
		}
		catch (SQLException e)
		{
			StringWriter sw = new StringWriter();
			PrintWriter  pw = new PrintWriter(sw);
			e.printStackTrace(pw) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = new Integer(e.getErrorCode());
			tObj[1] = sw.toString() ;
			//#CM31462
			// 6007030=Database error occured. error code={0}
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw (new ReadWriteException("6007030" + wDelim + tObj[0])) ;
		}
	}

	//#CM31463
	// Public methods ------------------------------------------------
	//#CM31464
	/**
	 * Generates the each handeler instance to be used by this class.
	 */
	protected void initHandler()
	{
		//#CM31465
		// Generate the handler instance
		wCarryInformationHandler = new CarryInformationHandler(wConn) ;
		wStationHandler = new StationHandler(wConn);
		//#CM31466
		// Generate the route controller instance
		//#CM31467
		// Conduct the route checking at every cycle.
		wRouteController = new RouteController(wConn, true);
	}

	//#CM31468
	/**
	 * Read <code>CarryInformation</code>, and send th retrieval instruction to the facility controller(AGC)
	 * if the retrieval is feasible.
	 * Readidng of <code>CarryInformation</code> is done at a certain interval. If the transmission of instruction
	 * is not available, e.g. in case of carrying to the workshops etc., the station will be designated.
	 */
	public void run()
	{
		try
		{

			//#CM31469
			// Terminate if it is already registered in RMI Server (avoiding the multiple start-ups.)
			if (this.isbind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME ))
			{
				return;
			}
			//#CM31470
			// Refgister the retrieval instruction to RMI Server
			this.bind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME);

			//#CM31471
			// Load hte Sleep Time from the resource file
			wExistSleepTime = AsrsParam.INSTRUCT_CONTROL_SLEEP_SEC_DATA_EXIST;

			//#CM31472
			// Start up the transmission of the retrieval instruction
			RmiMsgLogClient.write(6020016, this.getClass().getName());
			
			//#CM31473
			// Repeat the following
			while(wExitFlag == false)
			{
				try
				{
					//#CM31474
					// connecting again to DB
					if (wConn == null)
					{
						wConn = AsrsParam.getConnection();
					}
					
					while (wExitFlag == false)
					{
						synchronized(this)
						{
							try
							{
System.out.println("RetrievalSender ::: WAIT!!!");
								//#CM31475
								// If request has not been made, move to the wait state.
								if (isRequest() == false)
								{
									this.wait();
								}
								//#CM31476
								// Reset the requesting flag.
								restRequest();
System.out.println("RetrievalSender ::: Wake UP UP UP!!!");
							}
							catch (Exception e)
							{
								//#CM31477
								// 6026045=Error occurred in picking instruction task. Exception:{0}
								RmiMsgLogClient.write( new TraceHandler(6026045, e), this.getClass().getName() ) ;
							}
						}
						//#CM31478
						// Go fetch the carrying data
						control();
					}
				}
				catch (NotBoundException e)
				{
					//#CM31479
					// 6024031=Cannot send the transfer request text since SRC is not connected. SRC No.={0}
					RmiMsgLogClient.write(6024031, LogMessage.F_WARN, this.getClass().getName());
				}
				catch (Exception e)
				{
					//#CM31480
					// 6026045=Error occurred in picking instruction task. Exception:{0}
					RmiMsgLogClient.write( new TraceHandler(6026045, e), this.getClass().getName() ) ;
				}
				finally
				{
					try
					{
						if (wConn != null)
						{ 
							wConn.rollback();
						}
					}
					catch (SQLException e)
					{
						Object[] tObj = new Object[1];
						tObj[0] = Integer.toString(e.getErrorCode());
						//#CM31481
						// 6007030=Database error occured. error code={0}
						RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, "RetrievalSender", tObj);
						wConn = null;
					}
					Thread.sleep(3000);
				}
			}
		}
		catch(Exception e)
		{
			//#CM31482
			// 6026045=Error occurred in picking instruction task. Exception:{0}
			RmiMsgLogClient.write( new TraceHandler(6026045, e), this.getClass().getName() ) ;
		}
		finally
		{
System.out.println("RetrievalSender:::::finally!!");
			//#CM31483
			// 6020017=Terminating picking instruction sending process.
			RmiMsgLogClient.write(6020017, this.getClass().getName());
			try
			{
				//#CM31484
				// Delete the registration from RMI Server
				this.unbind();
				if (wConn != null)
				{ 
					wConn.rollback();
					wConn.close(); 
				}
			}
			catch (SQLException e)
			{
				Object[] tObj = new Object[1];
				tObj[0] = Integer.toString(e.getErrorCode());
				//#CM31485
				// 6007030=Database error occured. error code={0}
				RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, "RetrievalSender", tObj);
			}
		}
	}

	//#CM31486
	/**
	 * Activate the retrieval instruction in wait state.
	 */
	public synchronized void wakeup() 
	{
		this.notify();
	}

	//#CM31487
	/**
	 * To this retrieval instruction, make a request to immediately check the carrying data.
	 * Please use this method if kicking via RMI Server.
	 */
	public synchronized void write(Object[] params) 
	{
		//#CM31488
		// As there is a possibility that thread is not in wait state,
		//#CM31489
		// set the requesting flag beforehand.
		setRequest();
		//#CM31490
		// release from waiting
		this.notify();
	}

	//#CM31491
	/**
	 * Terminate the process.
	 * The process terminate when this method is called externally.
	 */
	public synchronized void stop()
	{
		//#CM31492
		// Release the wait state of this thread.
		this.notify();
		//#CM31493
		// Update hte flag so that loop of this thread should terminate.
		wExitFlag = true;
	}

	//#CM31494
	/**
	 * Procesing the retrieval instruction.
	 * Read the CarryInformation requesting for the retrieval instruction, then if the carrying is feasible,
	 * @throws Exception : Notifies if retrieval instruction cannot be continued any longer.
	 */
	public void control() throws Exception
	{
		//#CM31495
		// Number of attempts of searching data of retrieval instruction of each station and no data was found
		int countRetrievaDataExist = 0;
		//#CM31496
		// Number of stations
		int countStations = 1;

		//#CM31497
		// Number of times that went searching the data of retrieval instructions for each station (and could not
		//#CM31498
		// find data) to be compared to the number of stations; if no data is left at all stations, it goes out
		//#CM31499
		// the control() and get into Sleep state.
		while(countStations > countRetrievaDataExist)
		{
			//#CM31500
			// Get a list of station operating both storage and the retrieval + station operating retrieval only.
			StationHandler stationHandler = new StationHandler(wConn);
			StationSearchKey stationKey = new StationSearchKey();
			//#CM31501
			// set search key
			stationKey.KeyClear();
			stationKey.setSendable(Station.SENDABLE_TRUE);
			stationKey.setModeType(Station.AUTOMATIC_MODE_CHANGE, "!=");
			stationKey.setStationType(Station.STATION_TYPE_OUT, "=", "(", "", "or");
			stationKey.setStationType(Station.STATION_TYPE_INOUT, "=", "", ")", "and");
			stationKey.setStationNumberOrder(1, true);
			//#CM31502
			// search
			Station[] stations= (Station[])stationHandler.find(stationKey);	
			countStations = stations.length;
			
			//#CM31503
			// fetch the picking instruction request data by station
			//#CM31504
			// to search CarryInfo
			CarryInformationSearchKey carryKey = new CarryInformationSearchKey();
			CarryInformation[] carryArray = null;
		
			//#CM31505
			// to search Palette
			PaletteHandler paletteHandelr = new PaletteHandler(wConn);
			PaletteSearchKey paletteKey = new PaletteSearchKey();
		
			//#CM31506
			// to search WorkInfo
			WorkingInformationHandler workInfoHandelr = new WorkingInformationHandler(wConn);
			WorkingInformationSearchKey workInfoKey = new WorkingInformationSearchKey();
			
			//#CM31507
			// variable to store CarryInfo related to Palette, WorkInfo
			Vector carryVec = new Vector();
			
			for (int i = 0 ; i < countStations ; i++)
			{
				//#CM31508
				// Get data of retrieval instruciton request from each station
				//#CM31509
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
					//#CM31510
					// search CarryInfo
					carryArray = (CarryInformation[])wCarryInformationHandler.find(carryKey);
				
					//#CM31511
					// to store CarryInfo
					carryVec.clear();
				
					for (int j = 0; j < carryArray.length; j++)
					{
						//#CM31512
						// search Palette related to CarryInfo
						paletteKey.KeyClear();
						paletteKey.setPaletteId(carryArray[j].getPaletteId());
						paletteKey.setStatus(Palette.RETRIEVAL_PLAN);
					
						//#CM31513
						//  search WorkInfo related to CarryInfo
						workInfoKey.KeyClear();
						workInfoKey.setSystemConnKey(carryArray[j].getCarryKey());
					
						if (paletteHandelr.count(paletteKey) > 0 && workInfoHandelr.count(workInfoKey) > 0)
						{
							//#CM31514
							// store CarryInfo related to Palette, WorkInfo
							carryVec.add(carryArray[j]);
						}
					}
					
					CarryInformation[] resultCarryArray = new CarryInformation[carryVec.size()];
					carryVec.copyInto(resultCarryArray);
				
					//#CM31515
					// 1.multiply CarryInformation instance is not generated for the same paletteId
					//#CM31516
					// 2.With the same PaletteId, if the conveyance status is "started" and there are existing status (response wait, instructed, completed, arrival, etc)  
					//#CM31517
					//  don't generate CarryInformation instance
				
					//#CM31518
					// variable to distinct whether the same PaletteId exists or not
					Hashtable keepPaletteId = new Hashtable();
					//#CM31519
					// variable to store picking instruction data
					Vector vec = new Vector();
				
					for (int j = 0; j < resultCarryArray.length; j++)
					{
						Integer pid = new Integer(resultCarryArray[j].getPaletteId());
						if (keepPaletteId.containsKey(pid))
						{
							continue;
						}				
						//#CM31520
						// count conveyance data targeting same palette
						//#CM31521
						// if picking carry data exist, and storage data exist for the same palette
						//#CM31522
						// don't create CarryInformation instance for this carry data keeping this picking instruction as impossible
						//#CM31523
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
							//#CM31524
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
					
					if (vec.size() != 0)
					{
						CarryInformation[] sendCarryArray = new CarryInformation[vec.size()];
						vec.copyInto(sendCarryArray);
						//#CM31525
						// send picking instruction
						sendCarry(sendCarryArray, (Station)stations[i]);
						countRetrievaDataExist = 0;
					}
					else
					{
						//#CM31526
						// count up if picking instruction search returns no data
						countRetrievaDataExist = countRetrievaDataExist + 1;
					}
				}
				else
				{
					//#CM31527
					// count up if picking instruction search returns no data
					countRetrievaDataExist = countRetrievaDataExist + 1;
				}
			}

			//#CM31528
			// Terminating thread
			if (wExitFlag == true) break;

			//#CM31529
			// If there is carrying data but not able to send, let Sleep for a while.
			synchronized(this)
			{
				this.wait(wExistSleepTime);
			}
		}
	}

	//#CM31530
	// Package methods -----------------------------------------------

	//#CM31531
	// Protected methods ---------------------------------------------
	//#CM31532
	/**
	 * Check the conditions of the specified intance array of CarryInformation, then give retrieval instruction
	 * to the group controller.
	 * @param  chkArray	array of retrirval data/location to location move
	 * @param  destSt		receiving station
	 * @throws Exception	Notifies if trouble occurs in reading/writing in database.
	 */
	protected void sendCarry(CarryInformation[] chkArray, Station destSt) throws Exception
	{
		CarryInformation[] sendArray = new CarryInformation[2];
		CarryInformation[] cInfoArray = null;

		try
		{
			//#CM31533
			// Designate the receiving station for acquired array of CarryInformation, then check the conditions.
			cInfoArray = getSendCarryArray(chkArray, destSt);
			//#CM31534
			// Fix the transaction before transmission. As the workshop information may have been reniewed, ensure
			//#CM31535
			// to commit even if there is no carrying data.
			wConn.commit();
			if (cInfoArray.length > 0)
			{
				//#CM31536
				// Repeat as many transmissions as the retrieve instructions provided.
				//#CM31537
				// Create the cmmunication messages to send from CarryInfomation.
				//#CM31538
				// Retrieve instructions can be put together in set of 2.
				sendArray[0] = cInfoArray[0];				
				if (cInfoArray.length == SEND_MAX)
				{
					//#CM31539
					// double deep correspondence
					//#CM31540
					// send only one if the destination station is NULL during location movement
					if (destSt != null)
					{
System.out.println("Work no. of RetrievalSender ::: 2 cInfoArray[0] = " + cInfoArray[0].getWorkNumber());

						//#CM31541
						// to search CarryInfo
						CarryInformationSearchKey carryKey = new CarryInformationSearchKey();

						//#CM31542
						// set search key
						carryKey.KeyClear();
						int[] cmd = {CarryInformation.CMDSTATUS_INSTRUCTION, CarryInformation.CMDSTATUS_WAIT_RESPONSE, 
									  CarryInformation.CMDSTATUS_COMP_RETRIEVAL, CarryInformation.CMDSTATUS_ARRIVAL};
						carryKey.setCmdStatus(cmd);
						carryKey.setDestStationNumber(destSt.getStationNumber());
						if ( wCarryInformationHandler.count(carryKey) + 1 >= destSt.getMaxPaletteQuantity())
						{
System.out.println("2nd data transmission will overrun the max. number of carry instruction allowable.");
							sendArray[1] = null;
						}
						else
						{
							sendArray[1] = cInfoArray[1];
System.out.println("Work no. of RetrievalSender ::: 2 cInfoArray[1] = " + cInfoArray[1].getWorkNumber());
						}
					}
					else
					{
						sendArray[1] = null;
					}
				}
				else
				{
					sendArray[1] = null;
System.out.println("Work no. of RetrievalSender ::: 2 cInfoArray[0] = " + cInfoArray[0].getWorkNumber());
				}
				
				//#CM31543
				// Get the instance of GroupController from Station in order to send the text of retrieveal instruction.
				// In case of location to location moves, receiving station should be got from Pallet since the receiving
				// station has not been set for those moves.
				//#CM31544
				//CMENJP2010$CM Fetch conveyance destination station from Palette since destination station is not set during location movement
			if (destSt != null)
				{
					sendText(sendArray, GroupController.getInstance(wConn, destSt.getControllerNumber()));
				}
				else
				{
					try
					{
						//#CM31545
						// to search Palette
						PaletteHandler paletteHandelr = new PaletteHandler(wConn);
						PaletteSearchKey paletteKey = new PaletteSearchKey();
						Palette palette = null;
						
						//#CM31546
						// set search key
						paletteKey.KeyClear();
						paletteKey.setPaletteId(sendArray[0].getPaletteId());
						
						//#CM31547
						// search
						palette = (Palette)paletteHandelr.findPrimary(paletteKey);
						
						destSt = StationFactory.makeStation(wConn, palette.getCurrentStationNumber());
					}
					//#CM31548
					// If exception occurs, change the state of CarryInformation to error.
					catch (InvalidDefineException e)
					{
						for (int i = 0 ; i < sendArray.length ; i++)
						{
							carryFailure(sendArray[i]);
						}
					}
					catch (NotFoundException e)
					{
						for (int i = 0 ; i < sendArray.length ; i++)
						{
							carryFailure(sendArray[i]);
						}
					}
					sendText(sendArray, GroupController.getInstance(wConn, destSt.getControllerNumber()));
				}
			}
			//#CM31549
			// double deep correspondence
			//#CM31550
			// send conveyance instructions when location movement data is created
			else
			{
				//#CM31551
				// it is possible to read from both DoubleDeepRetrievalSender,AutomaticChangeSender
				//#CM31552
				// lock the side first read and avoid reduntant location movement instruction
				CarryInformation[] carryMove = null;
				ASCarryInformationHandler asCarryInfoHandler = new ASCarryInformationHandler(wConn);
				carryMove = asCarryInfoHandler.getRackMoveInfoForUpdate();
				if (carryMove.length > 0)
				{
					//#CM31553
					// send picking instruction
					sendCarry(carryMove, null);
				}
			}
		}
		catch (SQLException e)
		{
			//#CM31554
			// Exception at the committment
			StringWriter sw = new StringWriter();
			PrintWriter  pw = new PrintWriter(sw);
			e.printStackTrace(pw) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = new Integer(e.getErrorCode());
			tObj[1] = sw.toString() ;
			//#CM31555
			// 6007030=Database error occured. error code={0}
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw (new ReadWriteException("6007030" + wDelim + tObj[0])) ;
		}
	}

	//#CM31556
	/**
	 * Process of checking the carrying conditions for the array of acquired instance of CarryInformation and for the 
	 * receiving station.
	 * If data transmissions is not available at the destination of the load, e.g. workshop, etc. where there is no such
	 * on-line environment, the receiving station must be newly designated in process. The no. of newly designated 
	 * receiving station of the CarryInformation should replace the former destination.
	 * Then return the caller the array of feasible CarryInformation
	 * If the carrying is NOT possible with all CarryInformation, return the array of element number0.
	 * In case there is any inconsistency within CarryInformation data, change the state of applicable CarryInformation 
	 * to error.
	 * @param  carray		Array of CarryIformation with retrieval requests
	 * @param  destSt		receiving station
	 * @return				Return the list of feasible CarryInformations
	 * @throws Exception	Notifies if trouble occurs in reading/writing in database.
	 */
	protected CarryInformation[] getSendCarryArray(CarryInformation[] carry, Station destSt) throws Exception
	{
		//#CM31557
		// to search Palette
		PaletteHandler paletteHandelr = new PaletteHandler(wConn);
		PaletteSearchKey paletteKey = new PaletteSearchKey();
		Palette palette = null;
		
		//#CM31558
		// to search WorkInfo
		WorkingInformationHandler workInfoHandelr = new WorkingInformationHandler(wConn);
		WorkingInformationSearchKey workInfoKey = new WorkingInformationSearchKey();
		
		//#CM31559
		// variable to store CarryInfo related to Palette, WorkInfo
		Vector carryVec = new Vector();
		
		//#CM31560
		// Secure the area of equivalent size as CarryInformation provided for condition check.
		CarryInformation[] checkArray = new CarryInformation[carry.length];
		int pathCount = 0;
		for (int i = 0 ; i < checkArray.length ; i++)
		{
			//#CM31561
			// Only if the carry data of this retrieval specifies unit retrieval, check to see if the CarryInformation for
			// the same pallet ID exists. 
			// If there is CarryInformation that carry status is "Start" and retrieval instruction detail is either
			// picking, inventory check or replenishment
			// If uniti retrieval is instructed first, stocks will be removed at arrival report. Therefore they will
			// not be included in target of retrieval instruction.
			//#CM31562
			//CMENJP2016$CM check whether CarryInformation for same palette exists
			//#CM31563
			// if the conveyance status is "started", picking instruction is "picking" or "stock confirmation", storage qty increase "CarryInformation" exist
			//#CM31564
			// if unit picking instruction is done first, arrival report will become "complete removal". so it is not made picking instruction target
		if (carry[i].getCarryKind() != CarryInformation.CARRYKIND_RACK_TO_RACK)
			{
				if (carry[i].getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_UNIT)
				{
					CarryInformationSearchKey key = new CarryInformationSearchKey();
					//#CM31565
					// earch the carry data of same pallet IDs that the carry status is "Start" and its retrieval instruciton detail
					//#CM31566
					// is either"Inventory check","Picking" or "Replenishment". 
					key.setPaletteId(carry[i].getPaletteId());
					key.setCmdStatus(CarryInformation.CMDSTATUS_START);
					int[] details = new int[3];
					details[0] = CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK;
					details[1] = CarryInformation.RETRIEVALDETAIL_PICKING;
					details[2] = CarryInformation.RETRIEVALDETAIL_ADD_STORING;
					key.setRetrievalDetail(details);

					CarryInformation[] cntinfo = (CarryInformation[])wCarryInformationHandler.find(key);
					
					//#CM31567
					// to store CarryInfo
					carryVec.clear();
				
					for (int j = 0; j < cntinfo.length; j++)
					{
						//#CM31568
						// search Palette related to CarryInfo
						paletteKey.KeyClear();
						paletteKey.setPaletteId(cntinfo[j].getPaletteId());

						//#CM31569
						//  search WorkInfo related to CarryInfo
						workInfoKey.KeyClear();
						workInfoKey.setSystemConnKey(cntinfo[j].getCarryKey());
						
						int paletteCount = 0;
						try
						{	
							//#CM31570
							// error if Palette search returns 0 records
							paletteCount = paletteHandelr.count(paletteKey);
							if (paletteCount == 0)
							{
								Object[] tObj = new Object[1] ;
								tObj[0] = "PALETTE";
								RmiMsgLogClient.write(6006041, LogMessage.F_ERROR, this.getClass().getName(), tObj);
								throw (new NotFoundException("6006041" + wDelim + tObj[0]));
							}
						}
						catch(NotFoundException e)
						{
							//#CM31571
							//Here, throw ReadWriteException with error message attached. 
							throw (new ReadWriteException("6006039" + wDelim + "Palette")) ;
						}

						if (paletteCount > 0 && workInfoHandelr.count(workInfoKey) > 0)
						{
							//#CM31572
							// store CarryInfo related to Palette, WorkInfo
							carryVec.add(cntinfo[j]);
						}
					}
					
					CarryInformation[] resultCarryArray = new CarryInformation[carryVec.size()];
					carryVec.copyInto(resultCarryArray);

					if (resultCarryArray.length > 0)
					{
						//#CM31573
						// In case the CarryInformation exists, the carry data will not be included in target of this retrieval instruction.
						continue;
					}
				}
			}

			//#CM31574
			// Determination of the destination
			if (!destDetermin(carry[i], destSt))
			{
				//#CM31575
				// It terminates as any inadequacy found; this is to check the conditions for sending retrieval instruction
				//#CM31576
				// to the same station.
				break;
			}
			
			try
			{
				//#CM31577
				// If allocated pallet was not on teh shelf,
						
				//#CM31578
				// set search key
				paletteKey.KeyClear();
				paletteKey.setPaletteId(carry[0].getPaletteId());
						
				//#CM31579
				// search
				palette = (Palette)paletteHandelr.findPrimary(paletteKey);
				
				if (!(StationFactory.makeStation(wConn, palette.getCurrentStationNumber()) instanceof Shelf))
				{
System.out.println("Located other place than shelves.");
					continue;
				}
			}
			catch (InvalidDefineException e)
			{
				carryFailure(carry[i]);
			}
			catch (NotFoundException e)
			{
				//#CM31580
				// 6026061=Modification failed. Detail={0}
				Object[] tObj = new Object[1] ;
				tObj[0] = e.getMessage() ;
				RmiMsgLogClient.write(6026061, LogMessage.F_ERROR, this.getClass().getName(), tObj);
				continue;
			}
			catch (SQLException e)
			{
				//#CM31581
				// makeStation error
				StringWriter sw = new StringWriter();
				PrintWriter  pw = new PrintWriter(sw);
				e.printStackTrace(pw) ;
				Object[] tObj = new Object[2] ;
				tObj[0] = new Integer(e.getErrorCode());
				tObj[1] = sw.toString() ;
				//#CM31582
				// 6007030=Database error occured. error code={0}
				RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
				throw (new ReadWriteException("6007030" + wDelim + tObj[0])) ;
			}
			checkArray[pathCount++] = carry[i];
		}
		
		//#CM31583
		// In order to only return CarryInformation which met the conditions, secure the area of that portion.
		CarryInformation[] rCarryinfoArray = new CarryInformation[pathCount];
		for (int k = 0 ; k < pathCount ; k++)
		{
			rCarryinfoArray[k] = checkArray[k];
		}
		
		return rCarryinfoArray;
	}

	//#CM31584
	/**
	 * Check the conditions of retireval instructions. Compare the number of carry data which instructions already
	 * released with the MAX. number of carry feasible; if the number of the former has not reached the latter,
	 * it returns 'true'.
	 * It returns 'false' if the former exceeded the MAX. number of carrys feasible.
	 * @param  cInfo			CarryInformation
	 * @param  destSt			receiving station
	 * @return					return 'true' if the carry is feasible; 'false' if it is not.
	 * @throws Exception		Notifies if trouble occurs in reading/writing in database.
	 */
	protected boolean destDetermin(CarryInformation cInfo, Station destSt) throws Exception
	{
		try
		{
			//#CM31585
			// Determination of the destination, transport route checking		
			//#CM31586
			// to search Palette
			PaletteHandler paletteHandelr = new PaletteHandler(wConn);
			PaletteSearchKey paletteKey = new PaletteSearchKey();
			Palette palette = null;
						
			//#CM31587
			// set search key
			paletteKey.KeyClear();
			paletteKey.setPaletteId(cInfo.getPaletteId());
						
			//#CM31588
			// search
			palette = (Palette)paletteHandelr.findPrimary(paletteKey);
			
			if (wRouteController.retrievalDetermin(palette, destSt))
			{
			}
			else
			{
				//#CM31589
				// Transport route cannot be found
				return false;
			}
			
			//#CM31590
			// After determining the receiving station, check the conditions of the station.
			return destRightStation(cInfo, destSt);
		}
		//#CM31591
		// Notifies if specified station no. is invalid.
		catch (InvalidDefineException e)
		{
			carryFailure(cInfo);
			return false;
		}
	}

	//#CM31592
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
	 * @param  cInfo			CarryInformation
	 * @param  destSt			receiving station
	 * @return					returns 'true' if the carry is feasible or 'false' if not.
	 * @throws Exception		Notifies if trouble occurs in reading/writing in database.
	 */
	protected boolean destRightStation(CarryInformation cInfo, Station destSt) throws Exception
	{
		//#CM31593
		// If the group controller that the receiving station belongs to has no connection on-line, the carry 
		// cannot carried out.
		GroupController groupControll = new GroupController(wConn, destSt.getControllerNumber());		
		if (groupControll.getStatus() != GroupController.STATUS_ONLINE)
		{
			return false;
		}
		
		//#CM31594
		// Check the work mode of the receiving station.
		// If this receiving station handles both storage and retrieval, its work mode needs to be checked.
		// If this station only handles storage, or if no work mode control is done, checking is unnecessary. 
		//#CM31595
		//CMENJP2036$CM if sending station is storage/picking, mode confirmation is mandatory. if it is storage only with no mode control, mode confirmation is not needed
	if (destSt.getStationType() == Station.STATION_TYPE_INOUT && destSt.getModeType() != Station.NO_MODE_CHANGE)
		{
			//#CM31596
			// If condition of this receiving station applies to any of the following, determines that immediate 
			// retrieval will not be made.
			// Requesting for the work mode to switch to 'storage' or 'retrieval'
			// Work mode is in 'neutral' or 'storage'
			//#CM31597
			//CMENJP2037$CM if the work mode change request is either storage or picking mode 
			//#CM31598
			//work mode is neutral or storage
		if ((destSt.getModeRequest() != Station.NO_REQUEST)
			 || (destSt.getCurrentMode() == Station.NEUTRAL)
			 || (destSt.getCurrentMode() == Station.STORAGE_MODE))
			{
				return false;
			}
		}
		
		//#CM31599
		// Check the suspention flag of the receiving station.
		if (destSt.getSuspend() == Station.SUSPENDING)
		{
			//#CM31600
			// If the operation is suspended, it determines that immediate retrieval will not be made.
System.out.println("In RetrievalSender destRightStation, if the suspention flag of receiving station states 'invalid': StationNo.= " + destSt.getStationNumber());
			return false;
		}
System.out.println("In RetrievalSender destRightStation, if the suspention flag of receiving station states 'valid': StationNo.= " + destSt.getStationNumber());

		//#CM31601
		// Check the MAX. number of retrieval instructions acceptable ( whether/not CarryInformation relevant to the receiving
		// station exceeded the regulated volume)
		
		//#CM31602
		// fetch the picking instruction request data by station
		//#CM31603
		// to search CarryInfo
		CarryInformationSearchKey carryKey = new CarryInformationSearchKey();
		CarryInformation[] carryArray = null;

		//#CM31604
		// to search Palette
		PaletteHandler paletteHandelr = new PaletteHandler(wConn);
		PaletteSearchKey paletteKey = new PaletteSearchKey();

		//#CM31605
		// set search key
		carryKey.KeyClear();
		carryKey.setPaletteIdCollect();
		int[] cmd = {CarryInformation.CMDSTATUS_INSTRUCTION, CarryInformation.CMDSTATUS_WAIT_RESPONSE, 
					  CarryInformation.CMDSTATUS_COMP_RETRIEVAL, CarryInformation.CMDSTATUS_ARRIVAL};
		carryKey.setCmdStatus(cmd);
		carryKey.setDestStationNumber(destSt.getStationNumber());
		
		if (wCarryInformationHandler.count(carryKey) > 0)
		{
			//#CM31606
			// search
			carryArray = (CarryInformation[])wCarryInformationHandler.find(carryKey);
			
			//#CM31607
			// picking instruction enabled number count
			int count = 0;

			for (int i = 0; i < carryArray.length; i++)
			{
				//#CM31608
				// set search key
				paletteKey.KeyClear();
				paletteKey.setPaletteId(carryArray[i].getPaletteId());
				//#CM31609
				// add to picking instruction enabled number count
				count += paletteHandelr.count(paletteKey);
			}
System.out.println("RetrievalSender ::: "+ destSt.getStationNumber() + "MAX. number (MAX) = " + destSt.getMaxPaletteQuantity());
System.out.println("RetrievalSender ::: "+ destSt.getStationNumber() + "Current number (Current) = " + count);

			if (destSt.getMaxPaletteQuantity() <= count)
			{
				//#CM31610
				// The number of retrieval instruction exceeded the available number set by the regulation
System.out.println("RetrievalSender ::: Exceeding the MAX. number of retrieval instructions  ");				
				return false;
			}
		}
		return true;
	}

	//#CM31611
	/**
	 * Edit the text of retrieval instructions and send it.
	 * The transmission of retrieval instruction text will sent via RMI. Normally, it alters the carry state of this
	 * CarryInformation to 'wait for reply'.
	 * @param  cInfoArray		Array of CarryInformation
	 * @param  gc				instance of GroupController which the carry instructions text sends to
	 * @throws Exception		Notifies if trouble occurs in reading/writing in database.
	 * @throws Exception		It will be notified if trouble occurred in sending text to send task.
	 */
	protected void sendText(CarryInformation[] cInfoArray, GroupController gc) throws Exception
	{
		Object[] param = new Object[2];
		DecimalFormat fmt = new DecimalFormat("00");

		try
		{
			//#CM31612
			// Edit the sending text of the retrieval instruction
			As21Id12 id12 = new As21Id12(cInfoArray);
			String sendMsg = id12.getSendMessage();
			
			//#CM31613
			// Call the write method of AS21Sender using RMI
			RmiSendClient rmiSndC = new RmiSendClient();
			param[0] = sendMsg;
			
			//#CM31614
			// Update the status of CarryInstruction that is possible for retrieval instruction to "wait for response",
			//#CM31615
			// then change the pallet status to "retrieval in progress".			
			//#CM31616
			// to update CarryInfo
			CarryInformationHandler carryInfoHandler = new CarryInformationHandler(wConn);
			CarryInformationAlterKey carryAlterKey = new CarryInformationAlterKey();
			
			//#CM31617
			// to update Palette
			PaletteHandler paletteHandelr = new PaletteHandler(wConn);
			PaletteAlterKey paletteAlterKey = new PaletteAlterKey();
			
			for (int i = 0 ; i < cInfoArray.length ; i++)
			{
				if (cInfoArray[i] != null)
				{
					//#CM31618
					// Update the status of CarryInstruction if it is possible for retrieval instruction					
					//#CM31619
					// set search key
					carryAlterKey.KeyClear();
					carryAlterKey.setCarryKey(cInfoArray[i].getCarryKey());
					carryAlterKey.updateCmdStatus(CarryInformation.CMDSTATUS_WAIT_RESPONSE);
					//#CM31620
					// update
					carryInfoHandler.modify(carryAlterKey);

					//#CM31621
					// Set the pallet status to "retrieval in progress".				
					//#CM31622
					// set search key
					paletteAlterKey.KeyClear();
					paletteAlterKey.setPaletteId(cInfoArray[i].getPaletteId());
					paletteAlterKey.updateStatus(Palette.RETRIEVAL);
					paletteAlterKey.updateRefixDate(new java.util.Date());
					//#CM31623
					// update
					paletteHandelr.modify(paletteAlterKey);
				}
			}
			//#CM31624
			//Commit the transaction before sending the text.
			wConn.commit();
			
			//#CM31625
			// Select AGC to send 
			rmiSndC.write("AGC"+ fmt.format(gc.getNumber()), param);
			rmiSndC = null;
		}
		//#CM31626
		// Occurs if there are any contents error in updated data.
		catch (InvalidStatusException e)
		{
			for (int i = 0 ; i < cInfoArray.length ; i++)
			{
				carryFailure(cInfoArray[i]);
			}
		}
		catch (InvalidProtocolException e)
		{
			//#CM31627
			// 6026045=Error occurred in picking instruction task. Exception:{0}
			RmiMsgLogClient.write( new TraceHandler(6026045, e), this.getClass().getName() ) ;
			
			for (int i = 0 ; i < cInfoArray.length ; i++)
			{
				carryFailure(cInfoArray[i]);
			}
		}
		//#CM31628
		// Occurs if trouble occured when fixing the transaction.
		catch (SQLException e)
		{
			StringWriter sw = new StringWriter();
			PrintWriter  pw = new PrintWriter(sw);
			e.printStackTrace(pw) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = new Integer(e.getErrorCode());
			tObj[1] = sw.toString() ;
			//#CM31629
			// 6007030=Database error occured. error code={0}
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw new ReadWriteException("6007030" + wDelim + tObj[0]) ;
		}
	}

	//#CM31630
	/**
	 * Alters the carry state of specified instance of CarryInforamtion to 'error'.
	 * Once the state changes to error, this CarryInformation will not be used for carry any longer.
	 * Also in order to mirror the updates, fix the transaction within this method.
	 * @param  failureTarget	Instance of CarryInformation for the carry
	 * @throws Exception		 : Notifies if trouble occured in reading/writing in database.
	 */
	protected void carryFailure(CarryInformation failureTarget) throws Exception
	{
		try
		{
			if (failureTarget != null)
			{
				//#CM31631
				// Rolls back the contents of update made up to this moment.
				wConn.rollback();
				//#CM31632
				// Update the status to error
				
				//#CM31633
				// to update CarryInfo
				CarryInformationHandler carryInfoHandler = new CarryInformationHandler(wConn);
				CarryInformationAlterKey carryAlterKey = new CarryInformationAlterKey();
				//#CM31634
				// set search key
				carryAlterKey.KeyClear();
				carryAlterKey.setCarryKey(failureTarget.getCarryKey());
				carryAlterKey.updateCmdStatus(CarryInformation.CMDSTATUS_ERROR);

				//#CM31635
				// update
				carryInfoHandler.modify(carryAlterKey);
				
				//#CM31636
				// Fix the transaction
				wConn.commit();
			}
		}
		catch (SQLException e)
		{
			StringWriter sw = new StringWriter();
			PrintWriter  pw = new PrintWriter(sw);
			e.printStackTrace(pw) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = new Integer(e.getErrorCode());
			tObj[1] = sw.toString() ;
			//#CM31637
			// 6007030=Database error occured. error code={0}
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw new ReadWriteException("6007030" + wDelim + tObj[0]) ;
		}
	}

	//#CM31638
	/**
	 * Alter the request flag to 'requesting'.
	 * If the flag says 'requesting', the retrieval instruction will not shift to wait state.
	 */
	protected void setRequest() 
	{
		wRequest = true;
	}

	//#CM31639
	/**
	 * Change the reqest flag to 'no request'.
	 * If the flag states 'no request', retrieval instruciton should shift to wait state.
	 */
	protected void restRequest() 
	{
		wRequest = false;
	}

	//#CM31640
	/**
	 * Return the current status of the request flag.
	 * If this flag is 'requesting', it returns 'true' or 'false' if not.
	 * @return if request flag is 'requesting', 'true' or 'flkse' if not
     */
	protected boolean isRequest() 
	{
		return wRequest;
	}

	//#CM31641
	// Private methods -----------------------------------------------

}
//#CM31642
//end of class
