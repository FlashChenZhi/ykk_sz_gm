// $Id: AutomaticModeChangeSender.java,v 1.2 2006/10/26 01:09:45 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30589
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.rmi.RmiServAbstImpl;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.control.DoubleDeepChecker;
import jp.co.daifuku.wms.asrs.entity.DoubleDeepShelf;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.HardZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.HardZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WareHouse;

//#CM30590
/**
 * This class handles the process of sending carrying instructions and retrieval instructions and has
 * automatic mode change functions.
 * Retrieves data to send from CarryInformation to AGC, then gives carrying instruction to AGC.
 * When the mode change is necessary, it sends 'mode change request' (ID=41.
 * The target stations of this class are limited: stations with the automatic mode change fucntion.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/19</TD><TD>mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/17</TD><TD>M.INOUE</TD><TD>modify a way to 'wait', from 'sleep()' to 'wait()'</TD></TR>
 * <TR><TD>2004/01/21</TD><TD>M.INOUE</TD><TD>Instruction is not sent if data waiting for response still exist in the station.</TD></TR>
 * <TR><TD>2004/02/16</TD><TD>K.MORI</TD><TD>Correction is made so that the original location will not be open for empty location search any longer in case other pallets have been stored.</TD></TR>
 * <TR><TD>2004/02/19</TD><TD>K.MORI</TD><TD>Corrected the problem of throwing mode switch instrution repeatedly if 2 direct transfers that passes each other.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:09:45 $
 * @author  $Author: suresh $
 */
public class AutomaticModeChangeSender extends RmiServAbstImpl implements java.lang.Runnable
{
	//#CM30591
	// Class fields --------------------------------------------------
	//#CM30592
	/**
	 * Object which binds to the remote object.
	 */
	public static final String OBJECT_NAME = "AutomaticModeChangeSender" ;

	//#CM30593
	// Class variables -----------------------------------------------
	//#CM30594
	/**
	 * Connection to establish with database
	 */
	protected Connection wConn = null;

	//#CM30595
	/**
	 * Handler class for the carrying data manipulation
	 */
	protected CarryInformationHandler wCarryInformationHandler = null;

	//#CM30596
	/**
	 * Handler class for the station data manipulation
	 */
	protected StationHandler wStationHandler = null;

	//#CM30597
	/**
	 * Class to control class convey route
	 */
	protected RouteController wRouteController = null;

	//#CM30598
	/**
	 * Handler class for hard zone search
	 */
	protected HardZoneHandler wHardZoneHandler = null;

	//#CM30599
	/**
	 * Handler class for pallet search
	 */
	protected PaletteHandler wPaletteHandler = null;

	//#CM30600
	/**
	 * Delimiter
	 * Delimiter characters in MessageDef when Exception occurs.
	 */
	public String wDelim = MessageResource.DELIM ;

	//#CM30601
	/**
	 * Sleep Time required when there is convey data but NOT able to send
	 */
	private int wExistSleepTime = 1000;

	//#CM30602
	/**
	 * This flag determines whether/not to terminate AutomaticModeChangeSender class.
	 * When ExitFlag is 'true', it pulls out of infinite loop of the method: run().
	 * In order to update this flag, the method: stop() should be used.
	 */
	private boolean wExitFlag = false;

	//#CM30603
	/**
	 * This flag is used to determine whethter/not AutomaticModeChangeSender should be in wait state.
	 * The flag wRequest changes the state according to the carrying instruction request comes from outside.
	 * This process is not and will not be in wait state as long as wRequest is 'true'.
	 */
	private boolean wRequest = false;

	//#CM30604
	// Class method --------------------------------------------------
	//#CM30605
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:09:45 $") ;
	}

	//#CM30606
	// Constructors --------------------------------------------------
	//#CM30607
	/**
	 * Create new instance of <code>AutomaticModeChangeSender</code>.
	 * The connection will be obtained  from parameter of AS/RS system out of resource.
	 * @throws ReadWriteException : Notifies if exception occured during the database connection.
	 * @throws RemoteException  Exception related to communication generated while executing remote method call
	 */
	public AutomaticModeChangeSender() throws ReadWriteException, RemoteException
	{
		try
		{
			//#CM30608
			// Establishes the connection with DataBase. Users name and other information 
			// is obtained from the resource file.
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
			//#CM30609
			// 6007030=Database error occured. error code={0}
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw (new ReadWriteException("6007030" + wDelim + tObj[0])) ;
		}
	}

	//#CM30610
	// Public methods ------------------------------------------------
	//#CM30611
	/**
	 * Generates handler instances respectively to be used in this class.
	 */
	protected void initHandler()
	{
		//#CM30612
		// Generation of handler instance
		wCarryInformationHandler = new CarryInformationHandler(wConn) ;
		wStationHandler = new StationHandler(wConn);
		wHardZoneHandler = new HardZoneHandler(wConn);
		wPaletteHandler = new PaletteHandler(wConn);
		//#CM30613
		// Generation of route controller instance
		//#CM30614
		// Checks routes each time.
		wRouteController = new RouteController(wConn, true);
	}

	//#CM30615
	/**
	 * Reads <code>CarryInformation</code>, then if it is capable of giving carryring instruction,
	 * it sends the carying instruction to the facility controller(AGC) if possible.
	 * Reading of <code>CarryInformation</code> is carried at certain intarvals. If the empty location
	 * search is needed, it designates the storing location.
	 */
	public void run()
	{
		try
		{
			//#CM30616
			// Loads Sleep Time from the resource file.
			wExistSleepTime = AsrsParam.INSTRUCT_CONTROL_SLEEP_SEC_DATA_EXIST;

			//#CM30617
			// Terminates in case it has already been registered in RMI Server.(avoiding the multiple start-ups)
			if (this.isbind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME ))
			{
				return;
			}
			
			//#CM30618
			// Registers this class in RMI Server.
			this.bind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME);
			
			//#CM30619
			// 6020018=Starting automatic mode change sender.
			RmiMsgLogClient.write(6020018, this.getClass().getName());
			
			//#CM30620
			// Keep operating the following.
			while(wExitFlag == false)
			{
				try
				{
					//#CM30621
					// Reestablishes the connection with DB
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
System.out.println("AutomaticModeChangeSender ::: WAIT!!!");
								//#CM30622
								// If no request is made, puts on wait state.
								if (isRequest() == false)
								{
									this.wait();
								}
								//#CM30623
								// Resets the request flag.
								restRequest();
System.out.println("AutomaticModeChangeSender ::: Wake UP UP UP!!!");
							}
							catch (Exception e)
							{
								//#CM30624
								//Records errors in log file.
								//#CM30625
								// 6026043=Error occurred in transfer instruction task. Exception:{0}
								RmiMsgLogClient.write( new TraceHandler(6026043, e), this.getClass().getName() ) ;
							}
						}
						
						//#CM30626
						// Fetch the carrying data.
						control();
					}
				}
				catch (NotBoundException e)
				{
					//#CM30627
					// Carry request could not be sent to the send text task.
					//#CM30628
					// 6024031=Cannot send the transfer request text since SRC is not connected. SRC No.={0}
					RmiMsgLogClient.write(6024031, LogMessage.F_WARN, this.getClass().getName());
				}
				catch (Exception e)
				{
					//#CM30629
					//Records errors in log file.
					//#CM30630
					// 6026043=Error occurred in transfer instruction task. Exception:{0}
					RmiMsgLogClient.write( new TraceHandler(6026043, e), this.getClass().getName() ) ;
				}
				finally
				{
					try
					{
						//#CM30631
						// Rolls back if the connection with database is valid.
						if (wConn != null)
						{ 
							wConn.rollback();
						}
					}
					catch (SQLException e)
					{
						//#CM30632
						//Records errors in log file.
						//#CM30633
						// 6007030=Database error occured. error code={0}
						RmiMsgLogClient.write( new TraceHandler(6007030, e), this.getClass().getName() ) ;
						wConn = null;
					}
					Thread.sleep(3000);
				}
			}
		}
		catch(Exception e)
		{
			//#CM30634
			// 6026043=Error occurred in transfer instruction task. Exception:{0}
			RmiMsgLogClient.write( new TraceHandler(6026043, e), this.getClass().getName() ) ;
		}
		finally
		{
System.out.println("AutomaticModeChangeSender ::: finally");
			//#CM30635
			// 6020019=Terminating automatic mode change sender.
			RmiMsgLogClient.write(6020019, this.getClass().getName());
			try
			{
				//#CM30636
				// Deletes the registration from the RMI Server.
				this.unbind();
				//#CM30637
				// Rolls back if the connection with database is valid.
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
				//#CM30638
				// 6007030=Database error occured. error code={0}
				RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			}
		}
	}

	//#CM30639
	/**
	 * Activates hte carrying instruction on Wait state.
	 */
	public synchronized void wakeup() 
	{
		this.notify();
	}

	//#CM30640
	/**
	 * Requests for the immediate confirmation of carrying data to this process of sending carrying instruction
	 * with automatic mode switch function.
	 * If requesting via RMI Server, please use this method.
	 */
	public synchronized void write(Object[] params) 
	{
		//#CM30641
		// Sets the request flag.
		setRequest();
		//#CM30642
		// Cancels the wait state.
		this.notify();
	}

	//#CM30643
	/**
	 * Terminates the process.
	 * It teminates the process if this method is called externally.
	 */
	public synchronized void stop()
	{
		//#CM30644
		// Cancels the wait state of this thread.
		this.notify();
		//#CM30645
		// Updates the flag so that the looping of this thread should discontine.
		wExitFlag = true;
	}

	//#CM30646
	/**
	 * Processes the carrying instruction.
	 * Reads the CarryInformation which is requesting the carrying instruction; if it is capable of carrying,
	 * is sends the carrying instruciton to the facility controller(AGC).
	 * @throws Exception :Notifies if the processing of carrying instruction cannot be continued.
	 */
	public void control() throws Exception
	{
		try
		{
			//#CM30647
			// Number of attempts the data was not found of carrying instruction per station
			int counStorageDataExist = 0;
			//#CM30648
			// Number of stations
			int countStations = 1;

			//#CM30649
			//For Station search
			StationHandler stationHandler = new StationHandler(wConn);
			StationSearchKey key = new StationSearchKey();
			
			//#CM30650
			// In attempt of searching data of carrying instruction per station,
			//#CM30651
			// and in comparing the number of times the data was not found and the number of stations,
			//#CM30652
			// if no data was found at all station, get through control() and enter Sleep state.
			while (countStations > counStorageDataExist)
			{
				//#CM30653
				// Gets a list of stations with automatic mode switch.
				key.KeyClear();
				key.setSendable(Station.SENDABLE_TRUE);
				key.setModeType(Station.AUTOMATIC_MODE_CHANGE);
				Station[] sts = (Station[])stationHandler.find(key);

				countStations = sts.length;
				
				//#CM30654
				// Loops until the end of station list that is available for storage.
				// Normally it gives priority of reading storage data, however,
				// in case the work mode has been switched to the retrieval mode or in case the retrieval instruction
				// has been sent, retrieval data will be read with priority.
				//#CM30655
				//CMENJP1414$CM Usually storage data is read with priority
				//#CM30656
				//When the mode is changed to picking or if picking instructions are send, read the picking data in priority
			for (int i = 0; i < countStations ; i++)
				{
					//#CM30657
					// Gets carrying data, then either gives carry (retrieval) instruction or switches the work mode.
					boolean sendflag = checkAndSend(sts[i]);
					if (sendflag)
					{
						//#CM30658
						// If carrying data existed and the process is carried out accordingly, regardless of the 
						// results of sending process, commit transaction in order to release locked resource
						// which were obtained from location designation or mode switching.
						//#CM30659
						//CMENJP1416$CM commit the transaction to release the lock put by location confirmation process, mode change process
					wConn.commit();
						counStorageDataExist = 0;
					}
					else
					{
						//#CM30660
						// If there is no carrying data of either storage not retrieval,
						//#CM30661
						// increase the counts of sesarched stations.
						counStorageDataExist = counStorageDataExist + 1;
					}
				}

				//#CM30662
				// End of thread
				if (wExitFlag == true) break;

				//#CM30663
				// If the carrying data does exist but NOT able to send, let Sleep for a moment.
				synchronized(this)
				{
					this.wait(wExistSleepTime);
				}
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
			//#CM30664
			// 6007030=Database error occured. error code={0}
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw (new ReadWriteException("6007030" + wDelim + tObj[0])) ;
		}
	}

	//#CM30665
	// Package methods -----------------------------------------------

	//#CM30666
	// Protected methods ---------------------------------------------
	//#CM30667
	/**
	 * Conducts search for the carrying data which coresponds to specified <code>Station</code> object.
	 * If the data is found, it either requests the work mode switch or gives carrying instrucion(retrieval).
	 * Returns 'true' if the request of work mode switch or carrying instrucion(retrieval) is sent.
	 * Returns 'false' neither text was sent. 
	 * @param  st  :Sending station with which to confirm the type of carrying 
	 * @return : 'true' if work mode change is requested or retrieval instruction is sent; 'false' if neither was sent.
	 * @throws Exception : Notifies if trouble occured in reading/writing database.
	 * @throws Exception : Notifies if trouble occured in sending text concerning mode switching.
	 * @throws Exception : Notifies if exceptions occurred during the mode checking of the station.
	 */
	protected boolean checkAndSend(Station st) throws Exception
	{
		CarryInformationSearchKey cskey = new CarryInformationSearchKey();
		CarryInformation wCarry = null;
		
		//#CM30668
		// to search, update palette
		PaletteHandler paletteHandelr = new PaletteHandler(wConn);
		PaletteSearchKey paletteKey = new PaletteSearchKey();
		
		//#CM30669
		// to search WorkInfo
		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(wConn);
		WorkingInformationSearchKey workInfoKey = new WorkingInformationSearchKey();

		//#CM30670
		// to store CarryInfo
		Vector carryVec = new Vector();
		
		if (st.getReStoringInstruction() == Station.AWC_STORAGE_SEND)
		{
			cskey.KeyClear();
			cskey.setCarryKind(CarryInformation.CARRYKIND_STORAGE, "=", "(((", "", "OR");
			cskey.setCarryKind(CarryInformation.CARRYKIND_DIRECT_TRAVEL, "=", "", ")", "AND");
			cskey.setCmdStatus(CarryInformation.CMDSTATUS_START, "=", "(", "", "OR");
			cskey.setCmdStatus(CarryInformation.CMDSTATUS_ARRIVAL, "=", "", ")", "AND");
			cskey.setSourceStationNumber((String)st.getStationNumber(), "=", "", ")", "OR");
			cskey.setCarryKind(CarryInformation.CARRYKIND_RETRIEVAL, "=", "(", "", "AND");
			cskey.setCmdStatus(CarryInformation.CMDSTATUS_START);
			cskey.setDestStationNumber((String)st.getStationNumber(),"=", "", "))", "AND");
			cskey.setPriorityOrder(1, true);
			cskey.setCarryKeyOrder(2, true);
			cskey.setCreateDateOrder(3, true);
		}
		else
		{
			cskey.KeyClear();
			cskey.setCarryKind(CarryInformation.CARRYKIND_STORAGE, "=", "(((", "", "OR");
			cskey.setCarryKind(CarryInformation.CARRYKIND_DIRECT_TRAVEL, "=", "", ")", "AND");
			cskey.setCmdStatus(CarryInformation.CMDSTATUS_START, "=", "(", ")", "AND");
			cskey.setSourceStationNumber((String)st.getStationNumber(), "=", "", ")", "OR");
			cskey.setCarryKind(CarryInformation.CARRYKIND_RETRIEVAL, "=", "(", "", "AND");
			cskey.setCmdStatus(CarryInformation.CMDSTATUS_START);
			cskey.setDestStationNumber((String)st.getStationNumber(),"=", "", "))", "AND");
			cskey.setPriorityOrder(1, true);
			cskey.setCarryKeyOrder(2, true);
			cskey.setCreateDateOrder(3, true);
		}

		if (wCarryInformationHandler.count(cskey) > 0)
		{
			CarryInformation[] carryArray = null;
			//#CM30671
			// search
			carryArray = (CarryInformation[])wCarryInformationHandler.find(cskey);
							
			//#CM30672
			// to store CarryInfo
			carryVec.clear();
					
			for (int j = 0; j < carryArray.length; j++)
			{
				//#CM30673
				// search palette related to CarryInfo
				paletteKey.KeyClear();
				paletteKey.setPaletteId(carryArray[j].getPaletteId());
				
				//#CM30674
				// change the conveyance flag to "picking" while doing picking
				if(carryArray[j].getCarryKind() == CarryInformation.CARRYKIND_RETRIEVAL)
				{
					paletteKey.setStatus(Palette.RETRIEVAL_PLAN);
				}

				//#CM30675
				//  search WorkInfo related to CarryInfo
				workInfoKey.KeyClear();
				workInfoKey.setSystemConnKey(carryArray[j].getCarryKey());
					
				if (paletteHandelr.count(paletteKey) > 0 && workInfoHandler.count(workInfoKey) > 0)
				{
					//#CM30676
					// save CarryInfo related Palette,WorkInfo
					carryVec.add(carryArray[j]);
				}
			}
											
			if (carryVec.size() > 0)
			{
				//#CM30677
				// use the first one
				wCarry = (CarryInformation)carryVec.get(0);
			}
		}
		
		if (wCarry == null)
		{
			return false;
		}
		
		//#CM30678
		// Allocates the process in accordance with transport sections.
		switch (wCarry.getCarryKind())
		{
			case CarryInformation.CARRYKIND_STORAGE:
			case CarryInformation.CARRYKIND_DIRECT_TRAVEL:
				//#CM30679
				// Switching the mode to storage, or sending carrying instruction
				storageCheckAndSend(st, wCarry);
				break;
			case CarryInformation.CARRYKIND_RETRIEVAL:
				//#CM30680
				// Switching the mode to retrieval, or sending retrieval instruction
				retrievalCheckAndSend(st, wCarry);
				break;
		}
		
		return true;
	}

	//#CM30681
	/**
	 * Requests to switch the storage mode, or gives carry instruction according to specified <code>Station</code>
	 * or <code>CarryInformation</code>
	 * If it is not in the state of sending carrying instructtion, e.g., on retirval mode or requesting the mode switch, 
	 * sends instruction to change work mode(ID=42).
	 * If it is capable of sending carrying instruction, it does send the instruction after passing the storing condition checks.
	 * @param  st Sending station with which to confirm the type of carry.
	 * @param  storageInfo :Instance of <code>CarryInformation</code>, to carry
	 * @throws Exception :Notifies if trouble occured in reading/writing database.
	 * @throws Exception :Notifies if trouble occured in sending text concerning mode switching.

	 * @throws Exception :Notifies if exceptions occurred during the mode checking of the station.
	 */
	protected void storageCheckAndSend(Station st, CarryInformation storageInfo) throws Exception
	{
		//#CM30682
		// Flag to indicate whether/not carrying instruction data is to be sent.
		boolean carrySend = false;

		//#CM30683
		//Instruction is not sent if data waiting for response still exist in the station.	
		//#CM30684
		// to search CarryInfo
		CarryInformationSearchKey cskey = new CarryInformationSearchKey();
		
		cskey.KeyClear();
		cskey.setSourceStationNumber(st.getStationNumber());
		cskey.setCmdStatus(CarryInformation.CMDSTATUS_WAIT_RESPONSE);
		if (wCarryInformationHandler.count(cskey) >=1)
		{
			return;
		}

		//#CM30685
		// Branch: check items differs accoding to their own tansport section.
		if (storageInfo.getCarryKind() == CarryInformation.CARRYKIND_STORAGE)
		{
			//#CM30686
			// In case of sotrage, confirms if it is the data of returning storate.
			switch (storageInfo.getRetrievalDetail())
			{
				case CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK:
				case CarryInformation.RETRIEVALDETAIL_UNIT:
				case CarryInformation.RETRIEVALDETAIL_PICKING:
				case CarryInformation.RETRIEVALDETAIL_ADD_STORING:
					//#CM30687
					// If it is a returning storage data, give instruction as it is without checking the mode.
					carrySend = true;
					//#CM30688
					// double deep correspondence
					Station destStation = StationFactory.makeStation(wConn,
					storageInfo.getDestStationNumber());
					if (destStation instanceof DoubleDeepShelf) 
					{
						Shelf destShelf = (Shelf) destStation;
						DoubleDeepChecker ddChecker = new DoubleDeepChecker(wConn);
						if (!ddChecker.storageCheck(storageInfo, destShelf)) 
						{
							carrySend = false;
						}
					}
					break;
					
				default:
					//#CM30689
					//Check the condition of sending station.
					if (soueceRightStation(storageInfo, st))
					{
						//#CM30690
						// If it is a new storage data, check or switch mode for 'storage'.
						if (storageCheck(st))
						{
							carrySend = true;
						}
					}
					break;
			}
		}
		else
		{
			//#CM30691
			//If direct transfer work exists with this station and if creation date/time of the data is older, 
			//#CM30692
			//the mode is not switched,the carry is not conducted and the carry is determined impossible.		
			//#CM30693
			//to search CarryInfo
			CarryInformationSearchKey cskey2 = new CarryInformationSearchKey();
			
			cskey2.KeyClear();
			cskey2.setCarryKind(CarryInformation.CARRYKIND_DIRECT_TRAVEL);
			cskey2.setDestStationNumber(st.getStationNumber());
			cskey2.setCmdStatus(CarryInformation.CMDSTATUS_START);
			CarryInformation[] carray = (CarryInformation[])wCarryInformationHandler.find(cskey2);
			for (int i = 0 ; i < carray.length ; i++)
			{
				//#CM30694
				//Compare creation date of the direct transfer data that designates this station as a destination with that of this direct transfer data. 
				//#CM30695
				//If the creation date of loaded data is older, the mode will not be switched this time.
				if (carray[i].getCreateDate().compareTo(storageInfo.getCreateDate()) < 0)
				{
System.out.println("Skip the process as there is the direct transfer data for this station. StNo:::"+st.getStationNumber());
					return ;
				}
			}
			
			//#CM30696
			//Check both carry source and destination in case of direct transfer. 
			//#CM30697
			//Check the condition of sending station.
			if (soueceRightStation(storageInfo, st))
			{
				//#CM30698
				//Switch the mode of carry source adn destination concurrently if it is the direct transfer data.
				Station dest = StationFactory.makeStation(wConn, storageInfo.getDestStationNumber());
				if (storageCheckDirectTrancefar(st, dest))
				{
					carrySend = true;
				}
			}
		}
		
		//#CM30699
		// If carrying instruction can be sent, checks the data sent for carrying instustion then sending of process.
		if (carrySend)
		{
System.out.println("STATION = " + st.getStationNumber() + " STORAGE SEND OK");
			//#CM30700
			// Proces sending of carrying instruction
			storageSend(storageInfo, st);
		}
	}

	//#CM30701
	/**
	 * Conduct search of retrieval data which corresponds to the specified <code>Station</code>object.
	 * If the data is found, either request for switching retrieval work mode is made or releases retrieval instruction.
	 * If the retrieval instruction cannot be sent(storage mode or requesting to switch mode), it sends the request for
	 * work mode change(ID=42).
	 * If the retrieval instruction can be sent, it sends the instruction after passing all other condition checks for retrieval.
	 * Returns 'true' if either the request of retrieval work mode switch is made or retrieval instruction is sent.
	 * Returns 'false' if no text was sent.
	 * @param  st : Sending station with which to confirm the type of carry
	 * @return :Returns 'true' if there are any data concerning retrieval instruction. Returns 'false' if there is none.
	 * @throws Exception :Notifies if trouble occured in reading/writing database.
	 * @throws Exception :Notifies if trouble occured in sending text concerning mode switching.
	 * @throws Exception :Notifies if updating of station mode failed.
	 */
	protected void retrievalCheckAndSend(Station st, CarryInformation retrievalInfo) throws Exception
	{
		//#CM30702
		// to search CarryInfo
		CarryInformationSearchKey carryKey = new CarryInformationSearchKey();
		CarryInformation[] carryArray = null;
		
		//#CM30703
		// to search Palette
		PaletteSearchKey pSkey = new PaletteSearchKey();
		PaletteHandler pHandler = new PaletteHandler(wConn);
		pSkey.setPaletteId(retrievalInfo.getPaletteId());					
		Palette palette = (Palette)pHandler.findPrimary(pSkey);
		
		//#CM30704
		// to search WorkInfo
		WorkingInformationHandler workInfoHandelr = new WorkingInformationHandler(wConn);
		WorkingInformationSearchKey workInfoKey = new WorkingInformationSearchKey();
		
		//#CM30705
		// variable to store CarryInfo related to Palette, WorkInfo
		Vector carryVec = new Vector();
		
		//#CM30706
		// Check the condition of receiving station.
		if (destRightStation(st))
		{
			//#CM30707
			// In case of retrieval data, checks or switches retrieval mode
			if (retrievalCheck(st))
			{
				//#CM30708
				// double deep correspondence
				DoubleDeepChecker ddChecker = new DoubleDeepChecker(wConn);
				Station currStation = StationFactory.makeStation(wConn, palette.getCurrentStationNumber());
				Shelf currShelf = (Shelf)currStation;
				if (ddChecker.retrievalCheck(retrievalInfo, currShelf))
				{
					System.out.println("STATION = " + st.getStationNumber() + " RETRIVAL SEND OK");
					//#CM30709
					//Send retrieval instruction
					retrievalSend(retrievalInfo, st);
				}
				else
				{
System.out.println("出庫指示送信できず"+st.getStationNumber());
					carryKey.KeyClear();
					carryKey.setCarryKeyCollect();
					carryKey.setPaletteIdCollect();
					carryKey.setCarryKind(CarryInformation.CARRYKIND_RACK_TO_RACK);
					carryKey.setCmdStatus(CarryInformation.CMDSTATUS_START);
					carryKey.setCreateDateOrder(1, true);
					
					//#CM30710
					// search CarryInfo
					carryArray = (CarryInformation[])wCarryInformationHandler.find(carryKey);
					
					//#CM30711
					// to store CarryInfo
					carryVec.clear();
					
					for (int j = 0; j < carryArray.length; j++)
					{
						//#CM30712
						// search Palette related to CarryInfo
						pSkey.KeyClear();
						pSkey.setPaletteId(carryArray[j].getPaletteId());
						pSkey.setStatus(Palette.RETRIEVAL_PLAN);
						
						//#CM30713
						//  search WorkInfo related to CarryInfo
						workInfoKey.KeyClear();
						workInfoKey.setSystemConnKey(carryArray[j].getCarryKey());
						
						if (pHandler.count(pSkey) > 0 && workInfoHandelr.count(workInfoKey) > 0)
						{
							//#CM30714
							// store CarryInfo related to Palette, WorkInfo
							carryVec.add(carryArray[j]);
						}
					}
						
					CarryInformation[] resultCarryArray = new CarryInformation[carryVec.size()];
					carryVec.copyInto(resultCarryArray);
						
					Vector vec = new Vector();
					Hashtable keepPaletteId = new Hashtable();
						
					for (int j = 0; j < carryVec.size(); j++)
					{
						//#CM30715
						// Not generate more than just one CarryInformation instance which has the same pallet ID.
						Integer pid = new Integer(resultCarryArray[j].getPaletteId());
						if (keepPaletteId.containsKey(pid))
						{
							continue;
						}
						else
						{
							carryKey.KeyClear();
							carryKey.setCarryKey(((CarryInformation)carryVec.get(j)).getCarryKey());
							CarryInformation[] carry = (CarryInformation[])wCarryInformationHandler.find(carryKey);
							if (carry.length > 0)
							{
								vec.addElement(carry[0]); 
							} 
						}
						keepPaletteId.put(pid, pid);
					}
					carryArray = new CarryInformation[vec.size()];
					vec.copyInto(carryArray);
									
					CarryInformation[] RackToRackCarryArray = new CarryInformation[vec.size()];

					if (RackToRackCarryArray != null && RackToRackCarryArray.length > 0)
					{
						for(int i = 0 ; i < RackToRackCarryArray.length ; i++)
						{
							Station aisle = StationFactory.makeStation(wConn, RackToRackCarryArray[i].getAisleStationNumber());
							GroupController groupControll = new GroupController(wConn, aisle.getControllerNumber());
	
							//#CM30716
							// while aisle station is enabled and the system status is "online", send message for location movement
							if (aisle.getStatus() ==Station.STATION_OK)
							{
								if (groupControll.getStatus() == GroupController.STATUS_ONLINE)
								{
									CarryInformation sendArray[] = new CarryInformation[1];
									sendArray[0] = RackToRackCarryArray[i];
System.out.println("棚間移動指示を送信!!!!!!!!!!!");
									sendRetrievalText(sendArray, groupControll);
								}
								else
								{
System.out.println("システム状態がオフラインのため送信できず");
								}
							}
							else
							{
System.out.println("アイルの状態が使用不可のため棚間移動指示送信できず");
							}
						}
					}
				}
			}
		}
	}
	
	//#CM30717
	/**
	 * Examines whether/not the work mode of specified object <code>Station</code> allows the carrying instruction.
	 * If carrying instruction cannot be sent (due to retrieval mode or requesting to switch mode), it sends the work mode 
	 * change request (ID=42).
	 * Returns 'true' if carrying instruction can be sent.
	 * @param  st Sending station with which to confirm the type of carry
	 * @return Returns 'true' if the work mode accepts the carrying instruction, or 'false' if not.
	 *         Returns 'false' also if mode switching is requested.
	 * @throws Exception :Notifies if trouble occured in reading/writing database.
	 * @throws Exception :Notifies if trouble occured in sending text concerning mode switching.
	 * @throws Exception :Notifies if updating of station mode failed.
	 */
	protected boolean storageCheck(Station st) throws Exception
	{
		//#CM30718
		// to search Station
		StationHandler sh = new StationHandler(wConn);
		StationAlterKey sk = new StationAlterKey();
		
		if (st.getCurrentMode() == Station.STORAGE_MODE)
		{
			//#CM30719
			// If the work is on storage mode and if mode switching has not been requested, instruction can be sent.
			if (st.getModeRequest() == Station.NO_REQUEST)
			{
				return true;
			}
			else
			{
				//#CM30720
				// If the mode switching is being requested, nothing will be done.
				return false;
			}
		}
		else if (st.getCurrentMode() == Station.RETRIEVAL_MODE)
		{
			if (st.getModeRequest() == Station.NO_REQUEST)
			{
				//#CM30721
				// If the work is on retrieval mode and if mode switching has not been requested, counts the data of 
				// retrieval instructions already sent and counts the data of storage instructions already sent.  
				// If there is none at all, requests for mode switching.
				//#CM30722
				//CMENJP1451$CM Mode change request is taken if there is no pending picking instruction or storage instruction
			int cnt = wCarryInformationHandler.count(RetrievCount(st)) + wCarryInformationHandler.count(StorageCount(st));
				if (cnt == 0)
				{
					//#CM30723
					// Switch to storage mode, then updates database.
					sk.KeyClear();
					sk.setStationNumber(st.getStationNumber());
					sk.updateModeRequest(Station.STORAGE_REQUEST);
					sk.updateModeRequestTime(new java.util.Date());
					sh.modify(sk);
					
					//#CM30724
					//Commit the transaction before sending the text.
					wConn.commit();
					
					//#CM30725
					// Send instruction to switch on storage mode.
					SystemTextTransmission.id42send(st, Integer.toString(As21Id42.CLASS_STORAGE_EMG), wConn);
				}
				
				//#CM30726
				// It switches the mode, but returns 'false' as carryind instruction cannot be accepted.
				return false;
			}
			else
			{
				//#CM30727
				// If the request of switching mode is being made, nothing will be done.
				return false;
			}
		}
		else if (st.getCurrentMode() == Station.NEUTRAL)
		{
			if (st.getModeRequest() == Station.NO_REQUEST)
			{
				//#CM30728
				// If the work is in neutral mode and the request of switching mode has not been made, counts the 
				//#CM30729
				// data of retrieval instruction already sent, then requests for the mode switch if there is none.
				int cnt = wCarryInformationHandler.count(RetrievCount(st));
				if (cnt == 0)
				{
					//#CM30730
					// Switches to storage mode, then updates database.
					sk.KeyClear();
					sk.setStationNumber(st.getStationNumber());
					sk.updateModeRequest(Station.STORAGE_REQUEST);
					sk.updateModeRequestTime(new java.util.Date());
					sh.modify(sk);
					
					//#CM30731
					//Commit the transaction before sending the text.
					wConn.commit();
					
					//#CM30732
					// Sends the mode switch instruction to storage,
					SystemTextTransmission.id42send(st, Integer.toString(As21Id42.CLASS_STORAGE_EMG), wConn);
				}
				
				//#CM30733
				// Mode will be switched; but it returns 'false' as carrying instruction cannot be sent.
				return false;
			}
			else
			{
				//#CM30734
				// If the request of mode switching is being made, nothing will be done.
				return false;
			}
		}
		else
		{
			throw new InvalidDefineException("Invalid Mode");
		}
	}

	//#CM30735
	/**
	 * Examine wheather the work mode of specified <code>Station</code> object is available state for carry 
	 * instruction or not.This method checks the mode for direct transfer and switches mode.
	 * Unless the work mode is unavailable state of carry instruction such as retrieval mode or requesting for 
	 * mode switch, switch mode instruction (ID=42) will be sent.
	 * And if the receiving station is operated by automatic mode switching function, switch mode instruction 
	 * will be sent to the receiving station also.
	 * If the work mode is in normal status and if available for carry instruction, it returns true.
	 * If mode has been swithced or if the station is unable to switch modes, it returns false.
	 * @param	st	  Sending station that should be checked if the carry exists or not
	 * @param	dest  Receiving station that should be checked if the carry exists or not
	 * @return	It returns true if the work mode is available state for carry instructioin and returns false if not.
	 * 			It returns false also if the mode switch has been requested.
	 * @throws Exception	Notify if any trouble occurred in writing/reading of database. 
	 * @throws Exception	Notify if any trouble occurred in sending text for mode switch.
	 * @throws Exception	Notify if it failed to update the mode of the station.
	 */
	protected boolean storageCheckDirectTrancefar(Station st, Station dest) throws Exception
	{
		GroupController groupControll = new GroupController(wConn, dest.getControllerNumber());
		
		//#CM30736
		// to search CarryInfo
		CarryInformationSearchKey carryKey = new CarryInformationSearchKey();
		
		//#CM30737
		// to modify station
		StationHandler sh = new StationHandler(wConn);
		StationAlterKey sk = new StationAlterKey();
		
		if (st.getCurrentMode() == Station.STORAGE_MODE)
		{
			//#CM30738
			// set search key
			carryKey.KeyClear();
			int[] cmd = {CarryInformation.CMDSTATUS_INSTRUCTION, CarryInformation.CMDSTATUS_WAIT_RESPONSE, 
						  CarryInformation.CMDSTATUS_COMP_RETRIEVAL, CarryInformation.CMDSTATUS_ARRIVAL};
			carryKey.setCmdStatus(cmd);
			carryKey.setDestStationNumber(dest.getStationNumber());
			
			//#CM30739
			//Sendable if sending station is on storage mode and if mode switching is unrequested.
			if (st.getModeRequest() == Station.NO_REQUEST)
			{
				//#CM30740
				// If the sending station is sendable, check the status of receiving station.
				//#CM30741
				// If receiving station also operates the automatic mode switching, send the mode switching instruction.
				//#CM30742
				// Carrying is not available if the group controller that receiving station belongs to is not on-line.
				if (groupControll.getStatus() != GroupController.STATUS_ONLINE)
				{
					return false;
				}
				
				//#CM30743
				// Checks the interrupt flag of the receiving station.
				if (dest.getSuspend() == Station.SUSPENDING)
				{
					//#CM30744
					// If it has been interrupted,ite determinates that immediate retrieval will not be carried out.
					return false;
				}
				
				//#CM30745
				// Checks the available number of retrieval instrucitons (whether/not the carry information related to 
				// the receiving station exceeded the regulation.)
				if (dest.getMaxPaletteQuantity() <= wCarryInformationHandler.count(carryKey))
				{
					//#CM30746
					// Exceeded the available number of retrieval instruction.
					return false;
				}
				
				//#CM30747
				// Branch the mode check processing of receiving station according to the mode switch type of receiving station. 
				if (dest.getModeType() == Station.AUTOMATIC_MODE_CHANGE)
				{
					//#CM30748
					//Check the mode switch and send if the station operates the automatic mode switching.
					if (retrievalCheck(dest))
					{
						//#CM30749
						//Condition of receiving station is checked and found accpeptable
						return true;
					}
					else
					{
						//#CM30750
						//Wheather the receiving station mode is "send switching instruction" or "unable to switch"
						return false;
					}
				}
				else
				{
					//#CM30751
					// Checks the work mode of the receiving station.
					// The work mode needs to be checked if receiving station is used for both storage and retrieval.
					// If it is used for storage only, or if no mode control is conducted,no checking is necessary.
					//#CM30752
					//CMENJP1477$CM If the destination station is for both storage and picking, mode confirmation is needed. Else if the station is for storage only with no mode control, mode confirmation is not done.
				if (dest.getStationType() == Station.STATION_TYPE_INOUT && dest.getModeType() != Station.NO_MODE_CHANGE)
					{
						//#CM30753
						// If the status of receiving station falls on one of the following, it determines that no immdediate
						// retrieval will be carried out.
						// Requesting to switch the work mode of storage/retrieval
						// THe work mode is neutral or storage.
						//#CM30754
						//CMENJP1478$CM if the work mode change request is either storage or picking mode 
						//#CM30755
						//work mode is neutral or storage
					if ((dest.getModeRequest() != Station.NO_REQUEST)
						 || (dest.getCurrentMode() == Station.NEUTRAL)
						 || (dest.getCurrentMode() == Station.STORAGE_MODE))
						{
							return false;
						}
					}
					return true;
				}
			}
			else
			{
				//#CM30756
				// If the mode switching is being requested, nothing will be done.
				return false;
			}
		}
		else if (st.getCurrentMode() == Station.RETRIEVAL_MODE)
		{
			if (st.getModeRequest() == Station.NO_REQUEST)
			{
				//#CM30757
				// set search key
				carryKey.KeyClear();
				int[] cmd = {CarryInformation.CMDSTATUS_INSTRUCTION, CarryInformation.CMDSTATUS_WAIT_RESPONSE, 
							  CarryInformation.CMDSTATUS_COMP_RETRIEVAL, CarryInformation.CMDSTATUS_ARRIVAL};
				carryKey.setCmdStatus(cmd);
				carryKey.setDestStationNumber(st.getStationNumber());
				//#CM30758
				// If the work is on retrieval mode and if mode switching has not been requested, counts the data of 
				//#CM30759
				// retrieval instructions already sent. If there is none at all, requests for mode switching.
				int cnt = wCarryInformationHandler.count(carryKey);
				if (cnt == 0)
				{
					//#CM30760
					// Switches to storage mode, then updates database.
					sk.KeyClear();
					sk.setStationNumber(st.getStationNumber());
					sk.updateModeRequest(Station.STORAGE_REQUEST);
					sk.updateModeRequestTime(new java.util.Date());
					sh.modify(sk);
					//#CM30761
					//st.requestStorageMode();
					
					//#CM30762
					//Commit the transaction before sending the text.
					wConn.commit();
					
					//#CM30763
					// Send instruction to switch on storage mode.
					SystemTextTransmission.id42send(st, Integer.toString(As21Id42.CLASS_STORAGE_EMG), wConn);
				}
				
				//#CM30764
				//If the receiving station oeprates automatic mode switching, check the mode and send.
				if (dest.getModeType() == Station.AUTOMATIC_MODE_CHANGE)
				{
					//#CM30765
					// Switch only when the group controller that receiving station belongs to are connected on-line.
					if (groupControll.getStatus() == GroupController.STATUS_ONLINE)
					{
						return true;
					}
				}
				
				//#CM30766
				// It switches the mode, but returns 'false' as carryind instruction cannot be accepted.
				return false;
			}
			else
			{
				//#CM30767
				// If the mode switching is being requested, nothing will be done.
				return false;
			}
		}
		else if (st.getCurrentMode() == Station.NEUTRAL)
		{
			if (st.getModeRequest() == Station.NO_REQUEST)
			{
				//#CM30768
				// set search key
				carryKey.KeyClear();
				int[] cmd = {CarryInformation.CMDSTATUS_INSTRUCTION, CarryInformation.CMDSTATUS_WAIT_RESPONSE, 
							  CarryInformation.CMDSTATUS_COMP_RETRIEVAL, CarryInformation.CMDSTATUS_ARRIVAL};
				carryKey.setCmdStatus(cmd);
				carryKey.setDestStationNumber(st.getStationNumber());
				
				//#CM30769
				// If the work is in neutral mode and the request of switching mode has not been made, counts the 
				//#CM30770
				// data of retrieval instruction already sent, then requests for the mode switch if there is none.
				int cnt = wCarryInformationHandler.count(carryKey);
				if (cnt == 0)
				{
					//#CM30771
					// Switches to storage mode, then updates database.
					sk.KeyClear();
					sk.setStationNumber(st.getStationNumber());
					sk.updateModeRequest(Station.STORAGE_REQUEST);
					sk.updateModeRequestTime(new java.util.Date());
					sh.modify(sk);
					
					//#CM30772
					//Commit the transaction before sending the text.
					wConn.commit();
					
					//#CM30773
					// Send instruction to switch on storage mode.
					SystemTextTransmission.id42send(st, Integer.toString(As21Id42.CLASS_STORAGE_EMG), wConn);
				}
				
				//#CM30774
				//If the receiving station oeprates automatic mode switching, check the mode and send.
				if (dest.getModeType() == Station.AUTOMATIC_MODE_CHANGE)
				{
					//#CM30775
					// Switch only when the group controller that receiving station belongs to are connected on-line.
					if (groupControll.getStatus() == GroupController.STATUS_ONLINE)
					{
						return true;
					}
				}
				
				//#CM30776
				// It switches the mode, but returns 'false' as carryind instruction cannot be accepted.
				return false;
			}
			else
			{
				//#CM30777
				// If the mode switching is being requested, nothing will be done.
				return false;
			}
		}
		else
		{
			throw new InvalidDefineException("Invalid Mode");
		}
	}

	//#CM30778
	/**
	 * Examines whether/not the work mode of specified object <code>Station</code> allows the carrying instruction.
	 * If carrying instruction cannot be sent (due to retrieval mode or requesting to switch mode), it sends the work mode 
	 * change request (ID=42).
	 * Returns 'true' if carrying instruction can be sent.
	 * @param  st Sending station with which to confirm the type of carry
	 * @return Returns 'true' if the work mode accepts the retrieval instruction, , or 'false' if not.
	 *         Returns 'false' also if mode switching is requested.
	 * @throws Exception :Notifies if trouble occured in reading/writing database.
	 * @throws Exception :Notifies if updating of station mode failed.
	 */
	protected boolean retrievalCheck(Station st) throws Exception
	{
		//#CM30779
		// to search CarryInfo
		CarryInformationSearchKey carryKey = new CarryInformationSearchKey();
		
		//#CM30780
		// to modify station
		StationHandler sh = new StationHandler(wConn);
		StationAlterKey sk = new StationAlterKey();
		
		//#CM30781
		// set search key
		carryKey.KeyClear();
		carryKey.setPaletteIdCollect();
		int[] cmd = {CarryInformation.CMDSTATUS_INSTRUCTION, CarryInformation.CMDSTATUS_WAIT_RESPONSE, 
					  CarryInformation.CMDSTATUS_COMP_RETRIEVAL, CarryInformation.CMDSTATUS_ARRIVAL};
		carryKey.setCmdStatus(cmd);
		carryKey.setSourceStationNumber(st.getStationNumber());
		
		if (st.getCurrentMode() == Station.STORAGE_MODE)
		{
			if (st.getModeRequest() == Station.NO_REQUEST)
			{
				//#CM30782
				// If the work is on storage mode and the request of switching mode has not been made, 
				//#CM30783
				// counts the carrying instruction data already sent; or send request if there are none at all.
				int cnt = wCarryInformationHandler.count(carryKey);
				if (cnt == 0)
				{
					//#CM30784
					// Switch the work mode to 'retrieval'; then update the database.
					sk.KeyClear();
					sk.setStationNumber(st.getStationNumber());
					sk.updateModeRequest(Station.RETRIEVAL_REQUEST);
					sk.updateModeRequestTime(new java.util.Date());
					sh.modify(sk);
					
					//#CM30785
					//Commit the transaction before sending the text.
					wConn.commit();
					
					//#CM30786
					// Sends the work mode switch request (ID=42).
					SystemTextTransmission.id42send(st, WmsFormatter.getNumFormat(As21Id42.CLASS_RETRIEVAL_EMG), wConn);
				}
				
				//#CM30787
				// Mode will be switched, but it returns 'false' as the retrieval instruction cannot be accepted.
				return false;
			}
			else
			{
				//#CM30788
				// If the request of mode switching is being made, nothing will be done.
				return false;
			}
		}
		else if (st.getCurrentMode() == Station.RETRIEVAL_MODE)
		{
			//#CM30789
			// If the work mode is on 'retrieval' and the request of mode switch has not been made,
			// the instruction can be sent.
			if (st.getModeRequest() == Station.NO_REQUEST)
			{
				return true;
			}
			else
			{
				//#CM30790
				// If the request of mode switching is being made, nothing will be done.
				return false;
			}
		}
		else if (st.getCurrentMode() == Station.NEUTRAL)
		{
			if (st.getModeRequest() == Station.NO_REQUEST)
			{
				//#CM30791
				// If the work mode is neutral and the request of switching mode has not been made, counts the 
				//#CM30792
				// data of carrying instruction already sent, then requests for the mode switch if there is none.
				int cnt = wCarryInformationHandler.count(carryKey);
				if (cnt == 0)
				{
					//#CM30793
					// Switch to 'retrieval' mode then update the database.
					sk.KeyClear();
					sk.setStationNumber(st.getStationNumber());
					sk.updateModeRequest(Station.RETRIEVAL_REQUEST);
					sk.updateModeRequestTime(new java.util.Date());
					sh.modify(sk);

					//#CM30794
					//Commit the transaction before sending the text.
					wConn.commit();
					
					//#CM30795
					// Sends mode switch request for 'retrieval'.
					SystemTextTransmission.id42send(st, WmsFormatter.getNumFormat(As21Id42.CLASS_RETRIEVAL_EMG), wConn);
				}
				
				//#CM30796
				// It switches the mode, but returns 'false' since retrieval instruction cannot be sent.
				return false;
			}
			else
			{
				//#CM30797
				// If the request of mode switching is being made, nothing will be done.
				return false;
			}
		}
		else
		{
			throw new InvalidDefineException("Invalid Mode");
		}
	}

	//#CM30798
	/**
	 * Sends the carry instruction for the specified <code>CarryInformation</code>.
	 * Checks the status of carrying data and stations; if carrying can be done, ID-05 should be sent.
	 * @param ci 			Object<code>CarryInformation</code> which indicates the carrying data.
	 * @param sourceSt		Sending station with which to confirm the type of carry
	 * @throws Exception	Notifies if trouble occured in reading/writing database.
	 */
	protected void storageSend(CarryInformation ci, Station sourceSt) throws Exception
	{
		GroupController groupControll = new GroupController(wConn, sourceSt.getControllerNumber());
		
		CarryInformation sendcarry = null;
		
		//#CM30799
		// Checks the arrived data in need of sending the carrying data with priority.
		//#CM30800
		//CMENJP1516$CM confirm arrival data
		if (sourceSt.getArrivalCheck() == Station.ARRIVALCHECK_ON)
		{
			sendcarry = getTopStorageInfo(ci, sourceSt);
		}
		else
		{
			sendcarry = ci;
		}
		
		if (sendcarry != null)
		{
System.out.println("SEND CarryKey = " + ci.getCarryKey());
			//#CM30801
			// Designates the receiving station and checks the tranport route.
			if (destStorageDetermin(ci, sourceSt))
			{
System.out.println("destStorageDetermin OK key = " + ci.getCarryKey());
				
				//#CM30802
				// Sends the carrying instruciton text.
				sendStorageText(ci, groupControll);
			}
		}
	}

	//#CM30803
	/**
	 * Reads the carrying data existing at specified station.
	 * 1. If no arrival data is recorded in the station, returns null.
	 * 2. If dummy arrivalg data is recorded in the station, 
	 *    it returns received carrying data along with the load size and BC data recorded at the station.
	 * 3. If the regular carry key is recorded at the station, carrying data will be obtained by that carry key,
	 *    then returns it along with the load size and BC data recorded at the station.
	 * If updating of load size and BC data failed, loaded data of CarryInformation changes error and returns null.
	 * @param  ci : object <code>CarryInformation</code> to carry
	 * @param  st : sending station to check the arrival data.
	 * @return : object<code>CarryInformation</code> carried with priority as a result of refering to arrival data.
	 * @throws Exception Notifies if trouble occured in reading/writing database.
	 */
	protected CarryInformation getTopStorageInfo(CarryInformation ci, Station st) throws Exception
	{
		PaletteAlterKey pAkey = new PaletteAlterKey();
		PaletteHandler pHandler = new PaletteHandler(wConn);
		
		StationOperator sOpe = new StationOperator(wConn, ci.getSourceStationNumber());
		
System.out.println("Arrival check ");
		if (StringUtil.isBlank(st.getCarryKey()))
		{
System.out.println("NOT Arrival ");
			//#CM30804
			// Null if there is no arrival data.
			return null;
		}
		else if (st.getCarryKey().equals(AsrsParam.DUMMY_MCKEY))
		{
			//#CM30805
			// Dammy of arrival data is sent to the station.
			try
			{
				//#CM30806
				// BC data and the load size that preserved by the station should be set together with this pallet sending.
				if (!StringUtil.isBlank(st.getBCData()))
				{
					if (!(st.getBCData().equals("")))
					{
						pAkey.KeyClear();
						pAkey.setPaletteId(ci.getPaletteId());
						pAkey.updateBcData(st.getBCData());
						pHandler.modify(pAkey);
					}
				}
				
				if (st.getHeight() > 0)
				{
					pAkey.KeyClear();
					pAkey.setPaletteId(ci.getPaletteId());
					pAkey.updateHeight(st.getHeight());
					pHandler.modify(pAkey);
				}
			}
			catch (InvalidDefineException e)
			{
				carryFailure(ci);
			}
			catch (NotFoundException e)
			{
				carryFailure(ci);
			}
			return ci;
		}
		else
		{
			//#CM30807
			// Based on the Mc Key of the station, obtains 1 key out of CarryInformation table which would meet the conditions.
			CarryInformationSearchKey key = new CarryInformationSearchKey();
			key.setCarryKey(st.getCarryKey()) ;
			CarryInformation arrivalCarry[] = (CarryInformation[])wCarryInformationHandler.find(key) ;
			
			//#CM30808
			// No such data is found.
			if (arrivalCarry.length == 0)
			{
				//#CM30809
				// Outputs a warning message.
				//#CM30810
				// 6026066=No transfer data [MCKey={0}] for the specified MCKey. Deleted from the station [ST No.={1}].
				Object[] tobj = new Object[2];
				tobj[0] = st.getCarryKey();
				tobj[1] = st.getStationNumber();
				RmiMsgLogClient.write(6026066, LogMessage.F_ERROR, this.getClass().getName(), tobj);
				
				//#CM30811
				// Deletes if there is no carrying data of specified Mc Key.
				try
				{
					sOpe.dropArrival();
				}
				catch (InvalidDefineException e)
				{
					//#CM30812
					// If there is inconsistency with conditions of table updates
					StringWriter sw = new StringWriter();
					PrintWriter  pw = new PrintWriter(sw);
					e.printStackTrace(pw) ;
					Object[] tObj = new Object[1] ;
					tObj[0] = "Station";
					//#CM30813
					// 6026065=Data mismatch occurred in table update condition. Table={0}
					RmiMsgLogClient.write(6026065, LogMessage.F_ERROR, this.getClass().getName(), tObj);
				}
				catch (NotFoundException e)
				{
					//#CM30814
					// If no data is found to delete
					StringWriter sw = new StringWriter();
					PrintWriter  pw = new PrintWriter(sw);
					e.printStackTrace(pw) ;
					Object[] tObj = new Object[1] ;
					tObj[0] = "Station";
					//#CM30815
					// 6006006=There is no data to delete. Table Name: {0}
					RmiMsgLogClient.write(6006006, LogMessage.F_ERROR, this.getClass().getName(), tObj);
				}
				return ci;
			}
			else
			{
				//#CM30816
				// Returns CarryInformation which has been obtained from the carry key recorded as arrival data.
				try
				{
					//#CM30817
					// Set the BC data and load size preserved at the station along with this pallet sending.
					if (!StringUtil.isBlank(st.getBCData()))
					{
						if (!(st.getBCData().equals("")))
						{
							pAkey.KeyClear();
							pAkey.setPaletteId(arrivalCarry[0].getPaletteId());
							pAkey.updateBcData(st.getBCData());
							pHandler.modify(pAkey);
						}
					}
					if (st.getHeight() > 0)
					{
						pAkey.KeyClear();
						pAkey.setPaletteId(arrivalCarry[0].getPaletteId());
						pAkey.updateHeight(st.getHeight());
						pHandler.modify(pAkey);
					}
				}
				catch (InvalidDefineException e)
				{
					carryFailure(arrivalCarry[0]);
				}
				catch (NotFoundException e)
				{
					carryFailure(arrivalCarry[0]);
				}
				
				//#CM30818
				// Replaces the CarryInformation with newly obtained data
				return arrivalCarry[0];
			}
		}
	}

	//#CM30819
	/**
	 * Checks the status of sending station.
	 * Facility controller (AGC) that sending station belongs to is normal.
	 * As for the available number of carrying instructions, need to check whether/not the CarryInformation to this
	 * sending station has exceeded the regulated volume.
	 * No condsideration for CarryInformation is necessary if applicable station is the receiver.
	 * Sending station is not interrupted.
	 * @param  cInfo		Carry Information
	 * @param  sourceSt	sending station
	 * @return				'true' if it is verified that carrying can be carried out, 'false' if not.
	 * @throws Exception 	Notifies if trouble occured in reading/writing database.
	 */
	protected boolean soueceRightStation(CarryInformation cInfo, Station sourceSt) throws Exception
	{
		//#CM30820
		// to search CarryInfo
		CarryInformationSearchKey carryKey = new CarryInformationSearchKey();
		
		//#CM30821
		// set search key
		carryKey.KeyClear();
		carryKey.setPaletteIdCollect();
		int[] cmd = {CarryInformation.CMDSTATUS_INSTRUCTION, CarryInformation.CMDSTATUS_WAIT_RESPONSE, 
					  CarryInformation.CMDSTATUS_COMP_RETRIEVAL, CarryInformation.CMDSTATUS_ARRIVAL};
		carryKey.setCmdStatus(cmd);
		carryKey.setSourceStationNumber(sourceSt.getStationNumber());
		
		GroupController groupControll = new GroupController(wConn, sourceSt.getControllerNumber());
		
		//#CM30822
		// Checks the sending station.
		//#CM30823
		// Is AGC, which controls the sending station, is in normal status?
		if (groupControll.getStatus() != GroupController.STATUS_ONLINE)
		{
			return false;
		}

		//#CM30824
		// If the status of this carrying data 'arrival', it has been included in the number of carrying instructions;
		//#CM30825
		// subtract 1 from the results then compare:
		if (cInfo.getCmdStatus() == CarryInformation.CMDSTATUS_ARRIVAL)
		{
			//#CM30826
			// whether/not the number of CarryInformation related to this sending station exceeded the regulated volume?
			if (sourceSt.getMaxInstruction() <= wCarryInformationHandler.count(carryKey) - 1)
			{
				//#CM30827
				// Exceeded the available number of carying instructions
				return false;
			}
		}
		else
		{
			//#CM30828
			// whether/not the number of CarryInformation related to this sending station exceeded the regulated volume?
			if (sourceSt.getMaxInstruction() <= wCarryInformationHandler.count(carryKey))
			{
				//#CM30829
				// Exceeded the available number of carying instructions
				return false;
			}
		}
		
		//#CM30830
		// whether/not the operation of sending station interrupted?
		if (sourceSt.getSuspend() == Station.SUSPENDING)
		{
			return false;
		}
		
		return true;
	}

	//#CM30831
	/**
	 * Checks the tranport route. If the destination of this carrying is warehouse or workshop and no data can be sent,
	 * it designates the receiving location and updates the destination of cInfo.
	 * If the route to the acvailable carrying is normal, it returns 'true'. If no receiving location is found, it returns
	 * 'false'.
	 * @param  cInfo		Carry Information
	 * @param  sourceSt	sending stastion
	 * @return				Instance of sending station if the route of available carrying is normal; null if no sending 
	 * @throws Exception 	Notifies if trouble occured in reading/writing database.
	 */
	protected boolean destStorageDetermin(CarryInformation cInfo, Station sourceSt) throws Exception
	{
		try
		{
			//#CM30832
			// to search CarryInfo
			CarryInformationHandler cHandler = new CarryInformationHandler(wConn) ;
			CarryInformationAlterKey cAkey = new CarryInformationAlterKey();
			
			//#CM30833
			// to search Palette
			PaletteSearchKey pSkey = new PaletteSearchKey();
			PaletteAlterKey pAkey = new PaletteAlterKey();
			PaletteHandler pHandler = new PaletteHandler(wConn);
			
			//#CM30834
			// to search Stock
			StockAlterKey stockAkey = new StockAlterKey();
			StockHandler stockHandle = new StockHandler(wConn);
		
			pSkey.setPaletteId(cInfo.getPaletteId());
			Palette palette = (Palette)pHandler.findPrimary(pSkey);
			
			Station destSt = StationFactory.makeStation(wConn, cInfo.getDestStationNumber());
			if (destSt instanceof Shelf)
			{
				//#CM30835
				// If the destination is a location, if sending station conducts load size checking and if the carrying data
				// was anything but the inventory checking;
				// Reallocation of the location is necessary. Changes the destination to the warehouse.
				//#CM30836
				//CMENJP1546$CM modify warehouse sending station to re-confirm the location
			if ((sourceSt.getLoadSizeCheck() == Station.LOADSIZECHECK_ON)
					&& (cInfo.getRetrievalDetail() != CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK))
				{
					//#CM30837
					// Add a confirmation process wheather other pallets have been stored in that location or not before 
					//#CM30838
					// emptying the original receiving location. 
					boolean otherPalette = false;
					PaletteSearchKey pkey = new PaletteSearchKey();
					pkey.setCurrentStationNumber(destSt.getStationNumber());
					Palette[] plts = (Palette[])wPaletteHandler.find(pkey);
					for (int i = 0 ; i < plts.length ; i++)
					{
						//#CM30839
						// If the location No. of the pallet matches and if it differs from the ID of pallet to be carried this time,
						//there exists a different pallet in the location. 
						//#CM30840
						//CMENJP1549$CM if the conveyance target palette's palette id differs, a different palette
						//#CM30841
						// exists in this location.
					if (plts[i].getPaletteId() != palette.getPaletteId())
						{
							otherPalette = true;
						}
					}
					
					if (otherPalette == false)
					{
						//#CM30842
						// Change the load presence of the destined location to 'OFF, empty' initially planned for this storage.
						Shelf shf = (Shelf)destSt;
						ShelfAlterKey sAkey = new ShelfAlterKey();
						ShelfHandler sHandler = new ShelfHandler(wConn);
						sAkey.KeyClear();
						sAkey.setStationNumber(shf.getStationNumber());
						sAkey.updatePresence(Shelf.PRESENCE_EMPTY);
						sHandler.modify(sAkey);
					}

					//#CM30843
					// Change the destination to the warehouse.
					destSt = (WareHouse)StationFactory.makeStation(wConn, destSt.getWHStationNumber());
				}
			}
			
			//#CM30844
			// Designates the receiving location and checks the tranport route.
			//#CM30845
			// FOr the route checking, replace teh sending station temporarily.
			palette.setCurrentStationNumber(sourceSt.getStationNumber());
System.out.println("cInfo.getPalette() = " + palette.getCurrentStationNumber() );
System.out.println("destSt = " + destSt.getStationNumber() );
			if (wRouteController.storageDetermin(palette, sourceSt, destSt))
			{
				//#CM30846
				// double deep correspondence
				if (wRouteController.getDestStation() instanceof DoubleDeepShelf)
				{
					Shelf destShelf = (Shelf)wRouteController.getDestStation();
					DoubleDeepChecker ddChecker = new DoubleDeepChecker(wConn);
					if (!ddChecker.storageCheck(cInfo, destShelf))
					{
						return false;	
					}
				}
				
				//#CM30847
				// Replaces the destination to the one that RouteController class designated.
				if (!destSt.getStationNumber().equals(wRouteController.getDestStation().getStationNumber()))
				{
					//#CM30848
					// Sets the Receiving StationNumber and Aisle StaitonNumber to the CarryInformation.
					cAkey.KeyClear();
					cAkey.setCarryKey(cInfo.getCarryKey());
					cAkey.updateDestStationNumber(wRouteController.getDestStation().getStationNumber());
					cAkey.updateAisleStationNumber(wRouteController.getDestStation().getAisleStationNumber());
					cHandler.modify(cAkey);
					
					//#CM30849
					// to reflect the contents modified by the conveyance send instructions (if not reset, Warehouse no. remains as it is)
					cInfo.setDestStationNumber(wRouteController.getDestStation().getStationNumber());
					
					pAkey.KeyClear();
					pAkey.setPaletteId(palette.getPaletteId());
					pAkey.updateCurrentStationNumber(wRouteController.getDestStation().getStationNumber());
					pAkey.updateRefixDate(new java.util.Date());
					pHandler.modify(pAkey);
					
					//#CM30850
					// if the work type is storage, unplanned storage, don't update stock
					if((!cInfo.getWorkType().equals(CarryInformation.WORKTYPE_PLAN_STORAGE))
					&& (!cInfo.getWorkType().equals(CarryInformation.WORKTYPE_NOPLAN_STORAGE)))
					{
						//#CM30851
						// set destination location no. in stock info
						stockAkey.KeyClear();
						stockAkey.setPaletteid(palette.getPaletteId());
						stockAkey.updateLocationNo(wRouteController.getDestStation().getStationNumber());
						stockHandle.modify(stockAkey);
					}
				}
				
				if (destSt instanceof Shelf)
				{
					//#CM30852
					// Get the load size data to send to equipment from the location/hard zone data.
					//#CM30853
					// Set the acquired value to the pallet.
					Shelf shf = (Shelf)destSt;
					HardZoneSearchKey key = new HardZoneSearchKey();
					key.setHardZoneID(shf.getHardZoneID());
					HardZone[] hzones = (HardZone[])wHardZoneHandler.find(key);
					if (hzones.length == 0)
					{
						//#CM30854
						// Set the status of carry data "error" if hard zone data cannot be found.
						carryFailure(cInfo);
						Object[] tObj = new Object[2];
						tObj[0] = shf.getStationNumber();
						tObj[1] = Integer.toString(shf.getHardZoneID());
						//#CM30855
						// 6026070=The hard zone that is defined for specified location does not exist in zone definition. Location No:{0} Hard zone ID:{1}
						RmiMsgLogClient.write(6026070, LogMessage.F_ERROR, this.getClass().getName(), tObj);
						return false;
					}
					
					//#CM30856
					// Replace the size of pallet with the size of hard zone.
					pAkey.KeyClear();
					pAkey.setPaletteId(palette.getPaletteId());
					pAkey.updateHeight(hzones[0].getHeight());
					pHandler.modify(pAkey);
				}
			}
			else
			{
System.out.println("DEST CHECK FALSE");
				return false;
			}
		}
		//#CM30857
		// Occurs if there is any error data when updating data.
		catch (InvalidStatusException e)
		{
			carryFailure(cInfo);
			return false;
		}
		//#CM30858
		// Occurs if there is any error with conditions of updating data.
		catch (InvalidDefineException e)
		{
			carryFailure(cInfo);
			return false;
		}
		//#CM30859
		// Occurs in StationFactory.makeStation
		catch (NotFoundException e)
		{
			carryFailure(cInfo);
			return false;
		}
		//#CM30860
		// Occurs in StationFactory.makeStation
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		
		//#CM30861
		// Available for carrying instruction
		return true;
	}

	//#CM30862
	/**
	 * Edits the text of carring instruction and sends it.
	 * Text of carrying instruction is sent via RMI. If normally done, it changes the state of carrying to
	 * 'waiting for a response'.
	 * @param  cInfo		Instance of CarryInformation to carry
	 * @param  gc			Instance of GroupController, to where the carrying instruction text is sent
	 * @throws Exception 	Notifies if trouble occured in reading/writing database.
	 * @throws Exception 	
	 */
	protected void sendStorageText(CarryInformation cInfo, GroupController gc) throws Exception
	{
		Object[] param = new Object[2];
		DecimalFormat fmt = new DecimalFormat("00");

		try
		{
			//#CM30863
			// to search CarryInfo
			CarryInformationHandler cHandler = new CarryInformationHandler(wConn) ;
			CarryInformationAlterKey cAkey = new CarryInformationAlterKey();
			
			//#CM30864
			// If carrying data is available for sending, sends it in text style. Then changes the state of carrying
			// to 'waiting for a response'.
			// Creates tje communication message to send from CarryInfomation.
			//#CM30865
			//CMENJP1567$CM make telegraphic send message from CarryInformation
		As21Id05 id05 = new As21Id05(cInfo);
			String sendMsg = id05.getSendMessage();
System.out.println("AutomaticModeChangeSender SEND TEXT = " + sendMsg);

			//#CM30866
			// Calls Write method of As21Sender using RMI.
			RmiSendClient rmiSndC = new RmiSendClient();
			param[0] = sendMsg;
			
			//#CM30867
			// Updates the CarryInformation, to which the carrying instruction sent, to 'waiting for a response'.
			cAkey.KeyClear();
			cAkey.setCarryKey(cInfo.getCarryKey());
			cAkey.updateCmdStatus(CarryInformation.CMDSTATUS_WAIT_RESPONSE);
			cHandler.modify(cAkey);

			//#CM30868
			//Commit the transaction before sending the text.
			wConn.commit();
			
			//#CM30869
			// Send the carry instruction.
			rmiSndC.write("AGC"+ fmt.format(gc.getNumber()), param);
			rmiSndC = null;
			param[0] = null;
		}
		//#CM30870
		// Occurs if there are any trouble during the transaztion fix.
		catch (SQLException e)
		{
			StringWriter sw = new StringWriter();
			PrintWriter  pw = new PrintWriter(sw);
			e.printStackTrace(pw) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = new Integer(e.getErrorCode());
			tObj[1] = sw.toString() ;
			//#CM30871
			// 6007030=Database error occured. error code={0}
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw new ReadWriteException("6007030" + wDelim + tObj[0]) ;
		}
		//#CM30872
		// Occurs if there are any error during the text editing.
		catch (InvalidProtocolException e)
		{
			//#CM30873
			// 6026042=Trouble occurred in obtaining transfer data. Exception:{0}
			RmiMsgLogClient.write( new TraceHandler(6026042, e), this.getClass().getName() ) ;
			carryFailure(cInfo);
		}
		//#CM30874
		// Occurs if there were any contents error in data updating.
		catch (InvalidStatusException e)
		{
			//#CM30875
			// 6026042=Trouble occurred in obtaining transfer data. Exception:{0}
			RmiMsgLogClient.write( new TraceHandler(6026042, e), this.getClass().getName() ) ;
			carryFailure(cInfo);
		}
		//#CM30876
		// Occurs if there were any contents error in data updating.
		catch (InvalidDefineException e)
		{
			//#CM30877
			// 6026042=Trouble occurred in obtaining transfer data. Exception:{0}
			RmiMsgLogClient.write( new TraceHandler(6026042, e), this.getClass().getName() ) ;
			carryFailure(cInfo);
		}
		//#CM30878
		// Occurs if there are any error in data updating.
		catch (IOException e)
		{
			//#CM30879
			// 6026058=Cannot send the text. {0}
			//#CM30880
			//CMENJP1581$CM 6026058=can't send the text {0}
			RmiMsgLogClient.write( new TraceHandler(6026058, e), this.getClass().getName() ) ;
			carryFailure(cInfo);
		}
	}

	//#CM30881
	/**
	 * Prcesses sending of retrieval instruction to the specified <code>CarryInformation</code>.
	 * Checks the carrying information and the status of the station; if all available for the carrying instruction, 
	 * ID=12 will be sent out.
	 * @param ci 			Object<code>CarryInformation</code> which indicates the carrying data.
	 * @param sourceSt		Receiving station
	 * @throws Exception 	Notifies if trouble occured in reading/writing database.
	 */
	protected void retrievalSend(CarryInformation ci, Station destSt) throws Exception
	{
		GroupController groupControll = new GroupController(wConn, destSt.getControllerNumber());
		
		//#CM30882
		// Designates the destination of this carry, then checks the tranport route.
		if (destRetrievalDetermin(ci, destSt))
		{
			CarryInformation[] carray = new CarryInformation[1];
			carray[0] = ci;
			//#CM30883
			// Sends the text of retrieval instruction.
			sendRetrievalText(carray, groupControll);
		}
	}

	//#CM30884
	/**
	 * Checks the conditions of retrieval instruction. Compares the number of carrying data currently sompleted with 
	 * the availble number of carrying. If actual number of data is below the available number of data, returns 'true'.
	 * If it exceeded, returns 'false'.
	 * @param  cInfo			CarryInformation
	 * @param  destSt			receiving station
	 * @return					returns 'true' if carrying can be carried out, 'false' if not.
	 * @throws Exception 		Notifies if trouble occured in reading/writing database.
	 */
	protected boolean destRetrievalDetermin(CarryInformation cInfo, Station destSt) throws Exception
	{
		try
		{
			//#CM30885
			// to search CarryInfo
			CarryInformationHandler cHandler = new CarryInformationHandler(wConn) ;
			CarryInformationAlterKey cAkey = new CarryInformationAlterKey();
			
			//#CM30886
			// to search Palette
			PaletteSearchKey pSkey = new PaletteSearchKey();
			PaletteHandler pHandler = new PaletteHandler(wConn);
			
			pSkey.KeyClear();
			pSkey.setPaletteId(cInfo.getPaletteId());					
			Palette palette = (Palette)pHandler.findPrimary(pSkey);
			
			//#CM30887
			// Only if the carry data of this retrieval is for unit retrieval, check to see if there is CarryInformation for
			// the same pallet. 
			// If a unit retrieval is instructed under follwing conditions, stocks will be removed at the arrival report.
			// Therefore, they are not regarded as a target of retrieval instruction.
			// If carry status is "Start" and if retrieval instruction detail is either "Picking", "Inventory check" or "Replenishment".
			//#CM30888
			//CMENJP1586$CM check whether CarryInformation for same palette exists
			//#CM30889
			// if the conveyance status is "started", picking instruction is "picking" or "stock confirmation", storage qty increase "CarryInformation" exist
			//#CM30890
			// if unit picking instruction is done first, arrival report will become "complete removal". so it is not made picking instruction target
		if (cInfo.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_UNIT)
			{
				CarryInformationSearchKey key = new CarryInformationSearchKey();
				//#CM30891
				// Search the carry data of same pallet IDs that the carry status is "Start" and its retrieval instruciton detail
				//#CM30892
				//is either"Inventory check","Picking" or "Replenishment". 
				key.KeyClear();
				key.setPaletteId(palette.getPaletteId());
				key.setCmdStatus(CarryInformation.CMDSTATUS_START);
				int[] details = new int[3];
				details[0] = CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK;
				details[1] = CarryInformation.RETRIEVALDETAIL_PICKING;
				details[2] = CarryInformation.RETRIEVALDETAIL_ADD_STORING;
				key.setRetrievalDetail(details);
				CarryInformation[] cntinfo = (CarryInformation[])wCarryInformationHandler.find(key);
				if (cntinfo.length > 0)
				{
					//#CM30893
					// In case the CarryInformation exists, the carry data will not be included in target of this retrieval instruction.
					return false;
				}
			}
			
			//#CM30894
			// Check the carry route
			if (wRouteController.retrievalDetermin(palette, destSt))
			{
				//#CM30895
				// Replaces the destination to the one that RouteController class designated.
				if (!destSt.getStationNumber().equals(wRouteController.getDestStation().getStationNumber()))
				{
					destSt = wRouteController.getDestStation();
					try
					{
						//#CM30896
						// Set the StationNumber of determined receiving station in CarryInformation.
						cAkey.KeyClear();
						cAkey.setCarryKey(cInfo.getCarryKey());
						cAkey.updateDestStationNumber(destSt.getStationNumber());
						cHandler.modify(cAkey);

					}
					catch (InvalidDefineException e)
					{
						carryFailure(cInfo);
						return false;
					}
					catch (NotFoundException e)
					{
						carryFailure(cInfo);
						return false;
					}
				}
				return true;
			}
			else
			{
				//#CM30897
				// If carry route was checked and found unacceptable 
				return false;
			}
		}
		//#CM30898
		// Notifies if specified station no. is invalid.
		catch (InvalidDefineException e)
		{
			carryFailure(cInfo);
			return false;
		}
	}

	//#CM30899
	/**
	 * Checks the status of receiving station.
	 * Facility controller(AGC) , that the receiving station belongs to, has connection on-line.
	 * Operation of the receiving station has not been interrupted.
	 * As for the available number of retrieval instructions, it checks whether/not the carry information related to
	 * the receiving station exceeded the regulation. 
	 * No consideration is necessary for carry information that applicable station is the sending side.
	 * @param  destSt			receiving station
	 * @return					'true' if the carrying can be carried out, or 'false' if not.
	 * @throws Exception		Notifies if trouble occured in reading/writing database.
	 */
	protected boolean destRightStation(Station destSt) throws Exception
	{
		//#CM30900
		// to search CarryInfo
		CarryInformationSearchKey carryKey = new CarryInformationSearchKey();

		//#CM30901
		// set search key
		carryKey.KeyClear();
		int[] cmd = {CarryInformation.CMDSTATUS_INSTRUCTION, CarryInformation.CMDSTATUS_WAIT_RESPONSE, 
					  CarryInformation.CMDSTATUS_COMP_RETRIEVAL, CarryInformation.CMDSTATUS_ARRIVAL};
		carryKey.setCmdStatus(cmd);
		carryKey.setDestStationNumber(destSt.getStationNumber());
		
		GroupController groupControll = new GroupController(wConn, destSt.getControllerNumber());
		//#CM30902
		// Carrying is not available if the group controller that receiving station belongs to is not on-line.
		if (groupControll.getStatus() != GroupController.STATUS_ONLINE)
		{
			return false;
		}

		//#CM30903
		// Checks the interrupt flag of the receiving station.
		if (destSt.getSuspend() == Station.SUSPENDING)
		{
			//#CM30904
			// If it has been interrupted,ite determinates that immediate retrieval will not be carried out.
			return false;
		}
		
		//#CM30905
		// Checks the available number of retrieval instrucitons (whether/not the carry information related to 
		// the receiving station exceeded the regulation.)
		if (destSt.getMaxPaletteQuantity() <= wCarryInformationHandler.count(carryKey))
		{
			//#CM30906
			// Exceeded the available number of retrieval instruction.
			return false;
		}
		return true;
	}

	//#CM30907
	/**
	 * Edits the text of retrieval instruction and sends it.
	 * Text of retrieval instruction is sent via RMI. If correctly done, it changes the state of carrying to
	 * 'waiting for a response'.
	 * @param  cInfoArray		Array of CarryInformation to carry
	 * @param  gc				Instance of GroupController, to where the retrieval instruction text is sent
	 * @throws Exception 		Notifies if trouble occured in reading/writing database.
	 * @throws Exception 		Notifies if trouble occured in editing the messages.
	 */
	protected void sendRetrievalText(CarryInformation[] cInfoArray, GroupController gc) throws Exception
	{
		Object[] param = new Object[2];
		DecimalFormat fmt = new DecimalFormat("00");

		try
		{
			//#CM30908
			// to search CarryInfo
			CarryInformationHandler cHandler = new CarryInformationHandler(wConn) ;
			CarryInformationAlterKey cAkey = new CarryInformationAlterKey();
			
			//#CM30909
			// to search Palette
			PaletteSearchKey pSkey = new PaletteSearchKey();
			PaletteAlterKey pAkey = new PaletteAlterKey();
			PaletteHandler pHandler = new PaletteHandler(wConn);
		
			//#CM30910
			// Edits the text of retrieval instruction to send 
			As21Id12 id12 = new As21Id12(cInfoArray);
			String sendMsg = id12.getSendMessage();
System.out.println("AutomaticModeChangeSender SEND TEXT = " + sendMsg);

			//#CM30911
			// Calls Write method of As21Sender, using RMI
			RmiSendClient rmiSndC = new RmiSendClient();
			param[0] = sendMsg;
			
			//#CM30912
			// If the CarryInformation filled requirements for the retrieval instrucitons, updates the 
			// state to 'waiting for response'.
			// Change the status of pallet to 'being retrieved'
			//#CM30913
			//CMENJP1604$CM set palette status to "picking"
		for (int i = 0 ; i < cInfoArray.length ; i++)
			{
				cAkey.KeyClear();
				cAkey.setCarryKey(cInfoArray[i].getCarryKey());
				cAkey.updateCmdStatus(CarryInformation.CMDSTATUS_WAIT_RESPONSE);
				cHandler.modify(cAkey);

				pSkey.KeyClear();
				pSkey.setPaletteId(cInfoArray[i].getPaletteId());					
				Palette palette = (Palette)pHandler.findPrimary(pSkey);

				pAkey.KeyClear();
				pAkey.setPaletteId(palette.getPaletteId());
				pAkey.updateStatus(Palette.RETRIEVAL);
				pAkey.updateRefixDate(new Date());
				pHandler.modify(pAkey);
			}
			//#CM30914
			//Commit the transaction before sending the text.
			wConn.commit();
			
			//#CM30915
			// Send the retrieval instruction.
			rmiSndC.write("AGC"+ fmt.format(gc.getNumber()), param);
			rmiSndC = null;
		}
		//#CM30916
		// Occurs if there was any error while updating data.
		catch (InvalidStatusException e)
		{
			for (int i = 0 ; i < cInfoArray.length ; i++)
			{
				carryFailure(cInfoArray[i]);
			}
		}
		catch (InvalidProtocolException e)
		{
			//#CM30917
			// 6026045=Error occurred in picking instruction task. Exception:{0}
			RmiMsgLogClient.write( new TraceHandler(6026045, e), this.getClass().getName() ) ;
			for (int i = 0 ; i < cInfoArray.length ; i++)
			{
				carryFailure(cInfoArray[i]);
			}
		}
		//#CM30918
		// Occurs if there has been any troubles at transaction fix.
		catch (SQLException e)
		{
			StringWriter sw = new StringWriter();
			PrintWriter  pw = new PrintWriter(sw);
			e.printStackTrace(pw) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = new Integer(e.getErrorCode());
			tObj[1] = sw.toString() ;
			//#CM30919
			// 6007030=Database error occured. error code={0}
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw new ReadWriteException("6007030" + wDelim + tObj[0]) ;
		}
	}

	//#CM30920
	/**
	 * Changes the carrying state of instance CarryInforamtion specified to 'error'.
	 * Once the state is changed to error, this CarryInformation will not be the object of carrying.
	 * Also, in order to reflect the contents updated, the transaction is fixed in this method.
	 * @param  failureTarget	Instance of CarryInformation
	 * @throws Exception 		Notifies if trouble occured in reading/writing database.
	 * @throws Exception 		Notifies if there was irregularity in updates of instances.
	 */
	protected void carryFailure(CarryInformation failureTarget) throws Exception
	{
		try
		{	
			if (failureTarget != null)
			{
				//#CM30921
				// Rolls back the contents updated until present.
				wConn.rollback();
				
				//#CM30922
				//to update CarryInfo
				CarryInformationHandler cHandler = new CarryInformationHandler(wConn) ;
				CarryInformationAlterKey cAkey = new CarryInformationAlterKey();
				
				//#CM30923
				// Updates the status to 'error'
				cAkey.KeyClear();
				cAkey.setCarryKey(failureTarget.getCarryKey());
				cAkey.updateCmdStatus(CarryInformation.CMDSTATUS_ERROR);
				cHandler.modify(cAkey);

				//#CM30924
				// Fix the transaction.
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
			//#CM30925
			// 6007030=Database error occured. error code={0}
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw new ReadWriteException("6007030" + wDelim + tObj[0]) ;
		}
	}

	//#CM30926
	/**
	 * Turns the request flag to 'requesting'.
	 * If the request flag is 'requesting', the process of carrying instruction with automatic mode switch will
	 * not be in wait state.
	 */
	protected void setRequest() 
	{
		wRequest = true;
	}

	//#CM30927
	/**
	 * Change the reqest flag to 'no request'.
	 * If the flag states 'no request', the process of carrying instruction with automatic mode switchn should shift to wait state.
	 */
	protected void restRequest() 
	{
		wRequest = false;
	}

	//#CM30928
	/**
	 * Returns the current state of request flag. 
	 * If the flag is 'requesting' it returns 'true'. or 'false' if not.
	 * @return If the flag is 'requesting' it returns 'true'. or 'false' if not.
	 */
	protected boolean isRequest() 
	{
		return wRequest;
	}

	//#CM30929
	// Private methods -----------------------------------------------

	//#CM30930
	/**
	 * to seek picking instructed qty from the database, search conveyance status with [response wait],[instructed],
	 * [completed],[arrival] status and return the result qty (CarryInfo data qty)
	 * @param  st	receiving station
	 * @throws ReadWriteException throw any database connect exception
	 */
	private CarryInformationSearchKey RetrievCount(Station st) throws ReadWriteException
	{
		//#CM30931
		// to search CarryInfo
		CarryInformationSearchKey carryKey = new CarryInformationSearchKey();
	
		carryKey.KeyClear();
		int[] cmd = {CarryInformation.CMDSTATUS_INSTRUCTION, CarryInformation.CMDSTATUS_WAIT_RESPONSE, 
					  CarryInformation.CMDSTATUS_COMP_RETRIEVAL, CarryInformation.CMDSTATUS_ARRIVAL};
		carryKey.setCmdStatus(cmd);
		carryKey.setDestStationNumber(st.getStationNumber());
		
		return carryKey;
	}
	
	//#CM30932
	/**
	 *  to seek picking instructed qty from the database, search conveyance status with [response wait],[instructed],
	 * [completed],[arrival] status and return the result qty (CarryInfo data qty)
	 * @param  st	sending station
	 * @throws ReadWriteException throw any database connect exception
	 */
	private CarryInformationSearchKey StorageCount(Station st) throws ReadWriteException
	{
		//#CM30933
		// to search CarryInfo
		CarryInformationSearchKey carryKey = new CarryInformationSearchKey();
	
		carryKey.KeyClear();
		int[] cmd = {CarryInformation.CMDSTATUS_INSTRUCTION, CarryInformation.CMDSTATUS_WAIT_RESPONSE, 
					  CarryInformation.CMDSTATUS_COMP_RETRIEVAL, CarryInformation.CMDSTATUS_ARRIVAL};
		carryKey.setCmdStatus(cmd);
		carryKey.setSourceStationNumber(st.getStationNumber());
		
		return carryKey;
	}
}
//#CM30934
//end of class

