// $Id: StorageSender.java,v 1.3 2006/11/21 04:22:29 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM31686
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;

import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.HardZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.HardZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.CarryInformation;
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

//#CM31687
/**
 * This class handles the transmission of carry instruction.
 * It gives AGC the carry instruction by acquiring the data from CarryInformation  to send to AGC.
 * It is required that instance of <code>Station</code> the sender should be the station where the transmissions
 * can be received.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/17</TD><TD>M.INOUE</TD><TD>modify a way to 'wait', from 'sleep()' to 'wait()'</TD></TR>
 * <TR><TD>2004/01/21</TD><TD>M.INOUE</TD><TD>Correction is made for process of sending carry instruction:<BR>
 * In case there already exists the data in station that is waiting for response,<BR>
 * the carry instruction will not be sent.
 * </TD></TR>
 * <TR><TD>2004/02/17</TD><TD>M.INOUE</TD><TD>Corrected so that the status of the station (load size, BC data) will be updated.</TD></TR>
 * <TR><TD>2004/05/06</TD><TD>M.INOUE</TD><TD>In case of re-storage data of picking retrieval, there in a target data that carrying status is start</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:22:29 $
 * @author  $Author: suresh $
 */
public class StorageSender extends RmiServAbstImpl implements java.lang.Runnable
{
	//#CM31688
	// Class fields --------------------------------------------------
	//#CM31689
	/**
	 * Object to bind to the remote object
	 */
	public static final String OBJECT_NAME = "StorageSender" ;

	//#CM31690
	// Class variables -----------------------------------------------
	//#CM31691
	/**
	 * Connection for database use
	 */
	protected Connection wConn = null;

	//#CM31692
	/**
	 * Handlerclass for the manipulation of carry data
	 */
	protected CarryInformationHandler wCarryInformationHandler = null;

	//#CM31693
	/**
	 * Handlerclass fot hte manipulation of station data
	 */
	protected StationHandler wStationHandler = null;

	//#CM31694
	/**
	 * Transport route control class
	 */
	protected RouteController wRouteController = null;

	//#CM31695
	/**
	 * Transport route control class
	 */
	protected HardZoneHandler wHardZoneHandler = null;

	//#CM31696
	/**
	 * Delimiter
	 * Delimiter of the parameter in MessageDef when Exception occurs
	 */
	public String wDelim = MessageResource.DELIM ;

	//#CM31697
	/**
	 * Sleep Time needed in case there is carry data but NOT able to send
	 */
	private int wExistSleepTime = 1000;

	//#CM31698
	/**
	 * This flag determines whether/not to terminate the StorageSender class.
	 * If ExitFlag changes to true, it pulls out of the infenite loop in run() method.
	 * In order to update this flag, stop() method should be used.
	 */
	private boolean wExitFlag = false;

	//#CM31699
	/**
	 * This flag is used to determine whether/not StorageSender should shift to wait state.
	 * wRequest flag alters its status depending on the carry instruction request from external.
	 * If wRequest is true, carry instruction sill not shift to wait state.
	 */
	private boolean wRequest = false;

	//#CM31700
	// Class method --------------------------------------------------
 	//#CM31701
 	/**
	 * Retruns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2006/11/21 04:22:29 $") ;
	}

	//#CM31702
	// Constructors --------------------------------------------------
	//#CM31703
	/**
	 * Create new instance of <code>StorageSender</code>.
	 * The connection will be obtained  from parameter of AS/RS system out of resource.
	 * @throws ReadWriteException : Notifies if exception occured during the database connection.
	 * @throws RemoteException  Exception related to communication generated while executing remote method call
	 */
	public StorageSender() throws ReadWriteException, RemoteException
	{
		try
		{
			//#CM31704
			// Obtain the connection with DataBase. Information such as user's name, etc. wil be
			// gained from the resource file.
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
			//#CM31705
			// 6007030=Database error occured. error code={0}
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw (new ReadWriteException("6007030" + wDelim + tObj[0])) ;
		}
	}

	//#CM31706
	// Public methods ------------------------------------------------
	//#CM31707
	/**
	 * Generate the each handler instance whickeih will be used in this class.
	 */
	protected void initHandler()
	{
		//#CM31708
		// Generation of handler instance
		wCarryInformationHandler = new CarryInformationHandler(wConn) ;
		wStationHandler = new StationHandler(wConn);
		wHardZoneHandler = new HardZoneHandler(wConn);
		//#CM31709
		// Generation of the route controller instance
		//#CM31710
		// Conduct the route check at each cycle.
		wRouteController = new RouteController(wConn, true);
	}

	//#CM31711
	/**
	 * Read <code>CarryInformation</code>, abd if the carry instruction is feasible, send the carry instruction
	 * to the facility controller(AGC).
	 * Reading of <code>CarryInformation</code> is done at a certain interval. If the empty location search is
	 * required, conduct the storing location designation.
	 */
	public void run()
	{
		try
		{
			//#CM31712
			// If already registered in RMI Server, terminate.(avoid nultiple start-ups)
			if (this.isbind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME ))
			{
				return;
			}
			//#CM31713
			// Register the carry instruction to the RMI Server
			this.bind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME);

			//#CM31714
			// Load the Sleep Time from the resource file.
			wExistSleepTime = AsrsParam.INSTRUCT_CONTROL_SLEEP_SEC_DATA_EXIST;

			//#CM31715
			// 6020014=Starting transfer instruction sending process.
			RmiMsgLogClient.write(6020014, this.getClass().getName());

			//#CM31716
			// Keep repeating the following process.
			while(wExitFlag == false)
			{
				try
				{
					//#CM31717
					// Reconnecting to DB
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
System.out.println("StorageSender ::: WAIT!!!");
								//#CM31718
								// If there is no request, shift to the wait state.
								if (isRequest() == false)
								{
									this.wait();
								}
								//#CM31719
								// Reset the requesting flag.
								restRequest();
System.out.println("StorageSender ::: Wake UP UP UP!!!");
							}
							catch (Exception e)
							{
								//#CM31720
								// 6026043=Error occurred in transfer instruction task. Exception:{0}
								RmiMsgLogClient.write( new TraceHandler(6026043, e), this.getClass().getName() ) ;
							}
						}
						//#CM31721
						// Go fetch the carry data.
						control();
					}
				}
				catch (NotBoundException e)
				{
					//#CM31722
					// 6024031=Cannot send the transfer request text since SRC is not connected. SRC No.={0}
					RmiMsgLogClient.write(6024031, LogMessage.F_WARN, this.getClass().getName());
				}
				catch (Exception e)
				{
					//#CM31723
					// 6026043=Error occurred in transfer instruction task. Exception:{0}
					RmiMsgLogClient.write( new TraceHandler(6026043, e), this.getClass().getName() ) ;
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
						//#CM31724
						// 6007030=Database error occured. error code={0}
						RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, "StorageSender", tObj);
						wConn = null;
					}
					Thread.sleep(3000);
				}
			}
		}
		catch(Exception e)
		{
			//#CM31725
			// 6026043=Error occurred in transfer instruction task. Exception:{0}
			RmiMsgLogClient.write( new TraceHandler(6026043, e), this.getClass().getName() ) ;
		}
		finally
		{
System.out.println("StorageSender:::::finally!!");
			//#CM31726
			// 6020015=Terminating transfer instruction sending process.
			RmiMsgLogClient.write(6020015, this.getClass().getName());
			try
			{
				//#CM31727
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
				//#CM31728
				// 6007030=Database error occured. error code={0}
				RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, "StorageSender", tObj);
			}
		}
	}

	//#CM31729
	/**
	 * Activate the carry instruction in wait state.
	 */
	public synchronized void wakeup()
	{
		this.notify();
	}

	//#CM31730
	/**
	 * For this transmission of carry instruction, submit request to immediately check its carry data.
	 * If requesting via RMI Server, please use this method.
	 */
	public synchronized void write(Object[] params)
	{
		//#CM31731
		// Preset the request flag.
		setRequest();
		//#CM31732
		// release from wait state
		this.notify();
	}

	//#CM31733
	/**
	 * Terminate this process.
	 * It terminates if external calls for this method.
	 */
	public synchronized void stop()
	{
		//#CM31734
		// Release the wait state of this thread.
		this.notify();
		//#CM31735
		// Reniew the flag status so that the loop of this thread would terminate.
		wExitFlag = true;
	}

	//#CM31736
	/**
	 * Processing the carry instruction.
	 * Load the CarryInformation requesting for carry instruction; if carry can be carried out, send the
	 * carry instruction of facility controller (AGC)
	 * @throws Exception : Notifies if carry instruction cannot be continued any longer.
	 */
	public void control() throws Exception
	{
		CarryInformation cInfo = null;
		try
		{
			//#CM31737
			// Number of times no data was found in attempt of searching carry instruction of each station
			int counStorageDataExist = 0;
			//#CM31738
			// Number of stations
			int countStations = 1;

			//#CM31739
			// In search for the carry instruction data of each station;
			//#CM31740
			// and in comparing numbers of times that data was not found and the number of stations,
			//#CM31741
			// if no data is founde at all stations, get through control() and shift to Sleep.
			//#CM31742
			// to search Station
			StationHandler stationHandler = new StationHandler(wConn);
			StationSearchKey stationKey = new StationSearchKey();

			while(countStations > counStorageDataExist)
			{
				//#CM31743
				// Get list of stations handling both storage/retrieval + storage only station
				//#CM31744
				// set search key
				stationKey.KeyClear();
				stationKey.setSendable(Station.SENDABLE_TRUE);
				stationKey.setModeType(Station.AUTOMATIC_MODE_CHANGE, "!=");
				stationKey.setStationType(Station.STATION_TYPE_IN, "=", "(", "", "or");
				stationKey.setStationType(Station.STATION_TYPE_INOUT, "=", "", ")", "and");
				stationKey.setStationNumberOrder(1, true);
				//#CM31745
				// search
				Station[] stations= (Station[])stationHandler.find(stationKey);

				countStations = stations.length;
				//#CM31746
				// Loop to the end of the list of stations available of storing
				for (int i = 0; i < countStations ; i++)
				{
					Station sourceSt = stations[i];
					//#CM31747
					// Read the cary data in start state
					cInfo = getCarryInfo(sourceSt);
					if (cInfo == null)
					{
                        System.out.println("No data::::::::::::"+sourceSt.getStationNumber()+";;;"+sourceSt.getCarryKey());
						counStorageDataExist = counStorageDataExist + 1;
						//#CM31748
						// Fix the transaction in order to release the locked resource obtained from
						// determination of the location, etc.
						wConn.commit();
						//#CM31749
						// No suche carry data is found.
						continue;
					}
					//#CM31750
					// If the carry data exists, check the conditions and create the carry instruction.
					//#CM31751
					// Check the state of sending station.
					if (soueceRightStation(cInfo, sourceSt))
					{
						//#CM31752
						// Determination of the receiving location and carry route check
						if (destDetermin(cInfo, sourceSt))
						{
							//#CM31753
							// Send the text of carry instruction.
							sendText(cInfo, GroupController.getInstance(wConn, sourceSt.getControllerNumber()));
							counStorageDataExist = 0;
						}
						else
						{
							//#CM31754
							//6022035 = destination could not be determined where the carry can be carried out
							RmiMsgLogClient.write(6022035, "StorageSender");
						}
					}
					//#CM31755
					// If the process is operated for existing carry data, regardless of the results of sending/receiving,
					//#CM31756
					// Fix the transaction in order to release the locked resource ontained by the determination of location.
					wConn.commit();
				}

				//#CM31757
				// Terminating the thread
				if (wExitFlag == true) break;

				//#CM31758
				// If there are carry data but NOT able to send, let sleep for a while.
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
			//#CM31759
			// 6007030=Database error occured. error code={0}
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw (new ReadWriteException("6007030" + wDelim + tObj[0])) ;
		}
	}

	//#CM31760
	// Package methods -----------------------------------------------

	//#CM31761
	// Protected methods ---------------------------------------------

	//#CM31762
	/**
	 * Read the carry data existing in specified station. If no carry data is found, it returns null.
	 * If the station has arrival report, read the CarryInformation according to the carry key (mckey) recorded in station.
	 * If it is dummy arrival, search the CarryInformation designating this station to be the sending station, then select one
	 * with the oldest date of carry creation.
	 * If BC data and the load size are recorded in the station'S arrival data, set these data as the BC data and load size
	 * of pallet that CarryInformation will refer to.
	 * If failed to renew the Pallet instance, change the CarryInformation already read to 'error' and return null.
	 * If there is no arrival report at the station, load the CarryInformation designating this specified station to be the
	 * sender.
	 * @param  chStation	Sending station with which to confirm the carry type
	 * @return				CarryInformation
	 * @throws Exception	Notifies if trouble occurs in reading/writing in database.
	 */
	protected CarryInformation getCarryInfo(Station chStation) throws Exception
	{
		CarryInformationSearchKey cskey = new CarryInformationSearchKey();
		CarryInformation[] carry = null;
		CarryInformation wCarry = null;

		//#CM31763
		// to search, update Palette
		PaletteHandler paletteHandelr = new PaletteHandler(wConn);
		PaletteSearchKey paletteKey = new PaletteSearchKey();
		PaletteAlterKey paletteAlterKey = new PaletteAlterKey();

		//#CM31764
		// to search WorkInfo
		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(wConn);
		WorkingInformationSearchKey workInfoKey = new WorkingInformationSearchKey();

		//#CM31765
		// to store CarryInfo
		Vector carryVec = new Vector();

        //#CM31766
		// If there is a data waiting for reponse in station, the instruction will not be sent.
		//#CM31767
		// set search key
		cskey.KeyClear();
        cskey.setSourceStationNumber(chStation.getStationNumber());
        cskey.setCmdStatus(CarryInformation.CMDSTATUS_WAIT_RESPONSE);
        //#CM31768
        // search
		carry = (CarryInformation[])wCarryInformationHandler.find(cskey);
		carryVec.clear();
		for (int i = 0; i < carry.length; i++)
		{
			//#CM31769
			// set Palette search key
			paletteKey.KeyClear();
			paletteKey.setPaletteId(carry[i].getPaletteId());

			//#CM31770
			// set WorkInfo search key
			workInfoKey.KeyClear();
			workInfoKey.setSystemConnKey(carry[i].getCarryKey());

			//#CM31771
			// search
			if (paletteHandelr.count(paletteKey) > 0 && workInfoHandler.count(workInfoKey) > 0)
			{
				carryVec.add(carry[i]);
			}
		}

        if (carryVec.size() >= 1)
        {
            return null;
        }
        cskey.KeyClear() ;
		//#CM31772
		// Check whether/not the station has the arrival report.
		if (chStation.getArrivalCheck() == Station.ARRIVALCHECK_ON)
		{
			//#CM31773
			// If the carry key is found, get hte CarryInformation based on that.
			String carryKey = chStation.getCarryKey();
			//#CM31774
			// Based on the carry key at the station, get one out of CarryInformation table that meets the conditions.
			//#CM31775
			// Take no action as no arrival is reported to the station.
			if (StringUtil.isBlank(carryKey))
			{
				return null;
			}
			//#CM31776
			// Station received the dummy arrival.
			else if (carryKey.equals(AsrsParam.DUMMY_MCKEY))
			{
				try
				{
					//#CM31777
					// SQL differs based on the conveyance instruction enabled/disabled
					if (chStation.getReStoringInstruction() == Station.AWC_STORAGE_SEND)
					{
						//#CM31778
						// set search key
						cskey.KeyClear();
						cskey.setCarryKind(CarryInformation.CARRYKIND_STORAGE, "=", "(", "", "or");
						cskey.setCarryKind(CarryInformation.CARRYKIND_DIRECT_TRAVEL, "=", "", ")", "and");
						cskey.setCmdStatus(CarryInformation.CMDSTATUS_START, "=", "(", "", "or");
						cskey.setCmdStatus(CarryInformation.CMDSTATUS_ARRIVAL, "=", "", ")", "and");
						cskey.setSourceStationNumber(chStation.getStationNumber());
						cskey.setCreateDateOrder(1, true);
						cskey.setCarryKeyOrder(2, true);
					}
					else
					{
						//#CM31779
						// set search key
						cskey.KeyClear();
						cskey.setCarryKind(CarryInformation.CARRYKIND_STORAGE, "=", "(", "", "or");
						cskey.setCarryKind(CarryInformation.CARRYKIND_DIRECT_TRAVEL, "=", "", ")", "and");
						cskey.setCmdStatus(CarryInformation.CMDSTATUS_START);
						cskey.setSourceStationNumber(chStation.getStationNumber());
						cskey.setCreateDateOrder(1, true);
						cskey.setCarryKeyOrder(2, true);
					}

					if (wCarryInformationHandler.count(cskey) > 0)
					{
						CarryInformation[] carryArray = null;
						//#CM31780
						// search
						carryArray = (CarryInformation[])wCarryInformationHandler.find(cskey);

						//#CM31781
						// to store CarryInfo
						carryVec.clear();

						for (int j = 0; j < carryArray.length; j++)
						{
							//#CM31782
							// search Palette related to CarryInfo
							paletteKey.KeyClear();
							paletteKey.setPaletteId(carryArray[j].getPaletteId());

							//#CM31783
							//  search WorkInfo related to CarryInfo
							workInfoKey.KeyClear();
							workInfoKey.setSystemConnKey(carryArray[j].getCarryKey());

							if (paletteHandelr.count(paletteKey) > 0 && workInfoHandler.count(workInfoKey) > 0)
							{
								//#CM31784
								// store CarryInfo related to Palette, WorkInfo
								carryVec.add(carryArray[j]);
							}
						}

						if (carryVec.size() > 0)
						{
							//#CM31785
							// use the first one
							wCarry = (CarryInformation)carryVec.get(0);
						}
					}

					if (wCarry != null)
					{
						//#CM31786
						// Set the BC data and the load sizewhich are  preserved in Station to the pallet.
						if (!StringUtil.isBlank(chStation.getBCData()))
						{
							if (!(chStation.getBCData().equals("")))
							{
								//#CM31787
								// set search key
								paletteAlterKey.KeyClear();
								paletteAlterKey.setPaletteId(wCarry.getPaletteId());
								paletteAlterKey.updateBcData(chStation.getBCData());
								//#CM31788
								// update
								paletteHandelr.modify(paletteAlterKey);
							}
						}
						if (chStation.getHeight() > 0)
						{
							//#CM31789
							// set search key
							paletteAlterKey.KeyClear();
							paletteAlterKey.setPaletteId(wCarry.getPaletteId());
							paletteAlterKey.updateHeight(chStation.getHeight());
							//#CM31790
							// update
							paletteHandelr.modify(paletteAlterKey);
						}
					}
					return wCarry;
				}
				catch (InvalidDefineException e)
				{
					carryFailure(wCarry);
					return null;
				}
				catch (NotFoundException e)
				{
					carryFailure(wCarry);
					return null;
				}
			}
			//#CM31791
			// Based on the McKey at Station, get one out of CarryInformation table that meets the conditions.
			else
			{
				//#CM31792
				// There is a target data that CarryInformation status is only CARRYING_START
				cskey.setCmdStatus(CarryInformation.CMDSTATUS_START);
				cskey.setCarryKey(carryKey) ;
				//#CM31793
				// Getting the applicable CarryInfo
				carry = (CarryInformation[])wCarryInformationHandler.find(cskey) ;

				//#CM31794
				// to store CarryInfo
				carryVec.clear();

				for (int i = 0; i < carry.length; i++)
				{
					//#CM31795
					// set Palette search key
					paletteKey.KeyClear();
					paletteKey.setPaletteId(carry[i].getPaletteId());

					//#CM31796
					// set WorkInfo search key
					workInfoKey.KeyClear();
					workInfoKey.setSystemConnKey(carry[i].getCarryKey());

					//#CM31797
					// search
					if (paletteHandelr.count(paletteKey) > 0 && workInfoHandler.count(workInfoKey) > 0)
					{
						carryVec.add(carry[i]);
					}
				}

				carryVec.copyInto(carry);

				//#CM31798
				// No such data is found.
				if (carryVec.size() == 0)
				{
					//#CM31799
					// Outputting the warning message
					//#CM31800
					// 6026066=No transfer data [MCKey={0}] for the specified MCKey. Deleted from the station [ST No.={1}].
					Object[] tobj = new Object[2];
					tobj[0] = carryKey;
					tobj[1] = chStation.getStationNumber();
					RmiMsgLogClient.write(6026066, LogMessage.F_ERROR, this.getClass().getName(), tobj);
					//#CM31801
					// If specified carry data of specified mc key cannot be found, delete.
					try
					{
System.out.println("delete if no carry data of specified mckey is found.St No. = " + chStation.getStationNumber());

						//#CM31802
						// to update Station
						StationHandler stationHandelr = new StationHandler(wConn);
						StationAlterKey stationAlterKey = new StationAlterKey();

						//#CM31803
						// set search key
						stationAlterKey.KeyClear();
						stationAlterKey.setStationNumber(chStation.getStationNumber());
						stationAlterKey.updateCarryKey(null);
						stationAlterKey.updateBCData(null);
						stationAlterKey.updateHeight(0);

						//#CM31804
						// update
						stationHandelr.modify(stationAlterKey);
						return null;
					}
					catch (InvalidDefineException e)
					{
						//#CM31805
						// If there is inconsistency with conditions of table updating.
						StringWriter sw = new StringWriter();
						PrintWriter  pw = new PrintWriter(sw);
						e.printStackTrace(pw) ;
						Object[] tObj = new Object[1] ;
						tObj[0] = "Station";
						//#CM31806
						// 6026065=Data mismatch occurred in table update condition. Table={0}
						RmiMsgLogClient.write(6026065, LogMessage.F_ERROR, this.getClass().getName(), tObj);

						return null;
					}
					catch (NotFoundException e)
					{
						//#CM31807
						// If the data to delete cannot be found
						StringWriter sw = new StringWriter();
						PrintWriter  pw = new PrintWriter(sw);
						e.printStackTrace(pw) ;
						Object[] tObj = new Object[1] ;
						tObj[0] = "Station";
						//#CM31808
						// 6006006=There is no data to delete. Table Name: {0}
						RmiMsgLogClient.write(6006006, LogMessage.F_ERROR, this.getClass().getName(), tObj);
						return null;
					}
				}

				if (carry[0] instanceof CarryInformation)
				{
					CarryInformation arrivalCarry = (CarryInformation)carry[0];
					//#CM31809
					// Return the CarryInformation acquired by the carry key that is recorded as arrival data.
					try
					{
						//#CM31810
						// Set the BC data and load size data preserved by the station to the target Palette of sending data.
						if (!StringUtil.isBlank(chStation.getBCData()))
						{
							if (!(chStation.getBCData().equals("")))
							{
								//#CM31811
								// set search key
								paletteAlterKey.KeyClear();
								paletteAlterKey.setPaletteId(arrivalCarry.getPaletteId());
								paletteAlterKey.updateBcData(chStation.getBCData());
								//#CM31812
								// update
								paletteHandelr.modify(paletteAlterKey);
							}
						}
						if (chStation.getHeight() > 0)
						{
							//#CM31813
							// set search key
							paletteAlterKey.KeyClear();
							paletteAlterKey.setPaletteId(arrivalCarry.getPaletteId());
							paletteAlterKey.updateHeight(chStation.getHeight());
							//#CM31814
							// update
							paletteHandelr.modify(paletteAlterKey);
						}
					}
					catch (InvalidDefineException e)
					{
						carryFailure(arrivalCarry);
					}
					catch (NotFoundException e)
					{
						carryFailure(arrivalCarry);
					}
					return arrivalCarry ;
				}
				else
				{
					return null;
				}
			}
		}
		else
		{
			//#CM31815
			// SQL differs based on the conveyance instruction enabled/disabled
			if (chStation.getReStoringInstruction() == Station.AWC_STORAGE_SEND)
			{
				//#CM31816
				// set search key
				cskey.KeyClear();
				cskey.setCarryKind(CarryInformation.CARRYKIND_STORAGE, "=", "(", "", "or");
				cskey.setCarryKind(CarryInformation.CARRYKIND_DIRECT_TRAVEL, "=", "", ")", "and");
				cskey.setCmdStatus(CarryInformation.CMDSTATUS_START, "=", "(", "", "or");
				cskey.setCmdStatus(CarryInformation.CMDSTATUS_ARRIVAL, "=", "", ")", "and");
				cskey.setSourceStationNumber(chStation.getStationNumber());
				cskey.setCreateDateOrder(1, true);
				cskey.setCarryKeyOrder(2, true);
			}
			else
			{
				//#CM31817
				// set search key
				cskey.KeyClear();
				cskey.setCarryKind(CarryInformation.CARRYKIND_STORAGE, "=", "(", "", "or");
				cskey.setCarryKind(CarryInformation.CARRYKIND_DIRECT_TRAVEL, "=", "", ")", "and");
				cskey.setCmdStatus(CarryInformation.CMDSTATUS_START);
				cskey.setSourceStationNumber(chStation.getStationNumber());
				cskey.setCreateDateOrder(1, true);
				cskey.setCarryKeyOrder(2, true);
			}

			if (wCarryInformationHandler.count(cskey) > 0)
			{
				CarryInformation[] carryArray = null;
				//#CM31818
				// search
				carryArray = (CarryInformation[])wCarryInformationHandler.find(cskey);

				//#CM31819
				// to store CarryInfo
				carryVec.clear();

				for (int j = 0; j < carryArray.length; j++)
				{
					//#CM31820
					// search Palette related to CarryInfo
					paletteKey.KeyClear();
					paletteKey.setPaletteId(carryArray[j].getPaletteId());

					//#CM31821
					//  search WorkInfo related to CarryInfo
					workInfoKey.KeyClear();
					workInfoKey.setSystemConnKey(carryArray[j].getCarryKey());

					if (paletteHandelr.count(paletteKey) > 0 && workInfoHandler.count(workInfoKey) > 0)
					{
						//#CM31822
						// store CarryInfo related to Palette, WorkInfo
						carryVec.add(carryArray[j]);
					}
				}
				if (carryVec.size() > 0)
				{
					return (CarryInformation)carryVec.get(0);
				}
			}

			return wCarry;
		}
	}

	//#CM31823
	/**
	 * Check the status of the sending station.
	 * Facility controller (AGC) ,that sending station belongs to, is normal.
	 * Sending station is not requesting to switch the work mode.
	 * In case the work mode is in control: If the sending station handles both storage/retrieval,
	 * the work mode of the sending station is 'storage'
	 *   Except if the detail of retrieval instruction is unset(new storage), the work mode of the Station won't have to be checked.
	 * As for MAX. number of carry instructions, check to see if CarryInformation, relevant to this sending station has not
	 * exceeded.
	 * No consideration is requried if the applicable station is the receiver in CarryInformation
	 * Operation of the sending station is not suspended.
	 * @param  cInfo			CarryInformation
	 * @param  sourceSt		Sending station
	 * @return					Return true if it's available mad 'false' if not.
	 * @throws Exception		Notifies if trouble occurs in reading/writing in database.
	 */
	protected boolean soueceRightStation(CarryInformation cInfo, Station sourceSt) throws Exception
	{
		//#CM31824
		// Check the sending station.
		//#CM31825
		// Is AGC normal, which should control the sending station?
		GroupController groupControll = new GroupController(wConn, sourceSt.getControllerNumber());

System.out.println("StorageSender GroupController Is AGC controles over sending statio = "+ groupControll.getStatus());
		if (groupControll.getStatus() != GroupController.STATUS_ONLINE)
		{
			return false;
		}

		//#CM31826
		// If the detail of retrieval instruction is unset, it means that is a new storage.
		//#CM31827
		// Check the work mode of the receiving
		if (cInfo.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_UNKNOWN)
		{
			//#CM31828
			// If the sending station handles both storage/retrieval, the mode needs to be checked. If the station only
			// handles storage, or if the station has no control over work mode, checking of mode is not necessary.
			if (sourceSt.getStationType() == Station.STATION_TYPE_INOUT && sourceSt.getModeType() != Station.NO_MODE_CHANGE)
			{
				//#CM31829
				// Whether/not the sending station received the request of work mode switching.
				//#CM31830
				// Is the work mode of this sending station 'storage'? (neutral or retrirval are NOT acceptable)
System.out.println("StorageSender Station Is work mode of the sending station 'storage'? = " + sourceSt.getCurrentMode() + "this station number = " + sourceSt.getStationNumber());
System.out.println("StorageSender Whether/not this sending station received the request of work mode switching = " + sourceSt.getModeRequest());
				if(    (sourceSt.getModeRequest() != Station.NO_REQUEST)
				    || (sourceSt.getCurrentMode() == Station.NEUTRAL )
				    || (sourceSt.getCurrentMode() == Station.RETRIEVAL_MODE )  )
				{
					return false;
				}
			}
		}

		//#CM31831
		// If the status of this carru data 'arrival', this data is already included in the number of carrying instruction given;
		//#CM31832
		// therefore subtract 1 from the result and compare.

		//#CM31833
		// to search CarryInfo
		CarryInformationSearchKey carryKey = new CarryInformationSearchKey();

		//#CM31834
		// set search key
		carryKey.KeyClear();
		carryKey.setPaletteIdCollect();
		int[] cmd = {CarryInformation.CMDSTATUS_INSTRUCTION, CarryInformation.CMDSTATUS_WAIT_RESPONSE,
					  CarryInformation.CMDSTATUS_COMP_RETRIEVAL, CarryInformation.CMDSTATUS_ARRIVAL};
		carryKey.setCmdStatus(cmd);
		carryKey.setSourceStationNumber(sourceSt.getStationNumber());

		if (cInfo.getCmdStatus() == CarryInformation.CMDSTATUS_ARRIVAL)
		{
			//#CM31835
			// Whether/not the CarryInformation relevant to the sending station exceeded the regulated volume.
			if (sourceSt.getMaxInstruction() <= wCarryInformationHandler.count(carryKey) - 1)
			{
				//#CM31836
				// Exceeding the MAX. number of carry information
				return false;
			}
		}
		else
		{
System.out.println("MAX. numbers of pallet of StorageSender Station?= " + sourceSt.getMaxPaletteQuantity());
System.out.println("Current pallet numbers of StorageSender Station?= " + wCarryInformationHandler.count(carryKey));
			//#CM31837
			// Whether/not the CarryInformation relevant to the sending station exceeded the regulated volume.
			if (sourceSt.getMaxInstruction() <= wCarryInformationHandler.count(carryKey))
			{
				//#CM31838
				// Exceeding the MAX. number of carry information
System.out.println("StorageSender exceeding the MAX. number of carry information" );
				return false;
			}
		}

		//#CM31839
		// Whether/not the operation of sending station is on suspention.
System.out.println("StorageSender Whether/not the operation of sending station is on suspention. = " + sourceSt.getSuspend());
		if (sourceSt.getSuspend() == Station.SUSPENDING)
		{
			return false;
		}
		return true;
	}

	//#CM31840
	/**
	 * Check the transport route. In case the receiver is the station that cannot receive transmission, e.g. warehouse or
	 * workshop and has no on-line environment, determination of destination must be made and the destination of cInfo must
	 * be renewed.
	 * @param  cInfo		CarryInformation
	 * @param  sourceSt	Sending station
	 * @return				If the route is normal and acceptablle of the carry, it returns 'true'. If no destination is found,
	 *                      or if the the selected transport route is not acceptable, it returns 'false'.
	 * @throws Exception	Notifies if trouble occurs in reading/writing in database.
	 */
	protected boolean destDetermin(CarryInformation cInfo, Station sourceSt) throws Exception
	{
		try
		{
			//#CM31841
			//to search Stock
			StockAlterKey stockAkey = new StockAlterKey();
			StockHandler stockHandle = new StockHandler(wConn);

			Station destSt = StationFactory.makeStation(wConn, cInfo.getDestStationNumber());
System.out.println("No. of receiving station in StorageSender destDetermin = " + cInfo.getDestStationNumber());
			if (destSt instanceof Shelf)
			{
				//#CM31842
				// Though in case the carry is destined to the shelf, if sending station conducts the load size checking
				// and if the carry data is other than inventory checking:
				// Once again the determination of the location is necessary. change the destination to the warehouse.
				//#CM31843
				//CMENJP2177$CM modify warehouse sending station to re-confirm the location
			if (sourceSt.getLoadSizeCheck() == Station.LOADSIZECHECK_ON && cInfo.getRetrievalDetail() != CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK)
				{
					//#CM31844
					// Change the presence of load on the shelf to 'empty' of station initially planned for this storage.
					Shelf shf = (Shelf)destSt;

					//#CM31845
					// to update Shelf
					ShelfHandler shelfHandler = new ShelfHandler(wConn);
					ShelfAlterKey shelfAlterKeyk = new ShelfAlterKey();
					//#CM31846
					// set search key
					shelfAlterKeyk.KeyClear();
					shelfAlterKeyk.setStationNumber(shf.getStationNumber());
					shelfAlterKeyk.updatePresence(Shelf.PRESENCE_EMPTY);
					//#CM31847
					// update
					shelfHandler.modify(shelfAlterKeyk);

					//#CM31848
					// Change the destination to the warehouse.
					destSt = (WareHouse)StationFactory.makeStation(wConn, destSt.getWHStationNumber());
System.out.println("StorageSender The station No. that empty location to be searched once again. = " + destSt);
				}
			}
			//#CM31849
			// Determination of the destination and check the transport route
			//#CM31850
			// Temporarily replace the sending station for route checking.

			//#CM31851
			// to search, update Palette
			PaletteHandler paletteHandelr = new PaletteHandler(wConn);
			PaletteSearchKey paletteKey = new PaletteSearchKey();
			PaletteAlterKey paletteAlterKey = new PaletteAlterKey();
			Palette palette = null;
			//#CM31852
			// set search key
			paletteKey.KeyClear();
			paletteKey.setPaletteId(cInfo.getPaletteId());
			//#CM31853
			// search
			palette = (Palette)paletteHandelr.findPrimary(paletteKey);

			//#CM31854
			// substitute
			palette.setCurrentStationNumber(sourceSt.getStationNumber());

			if (wRouteController.storageDetermin(palette, sourceSt, destSt))
			{
System.out.println("Designation of destination and tranport route check in StorageSender destDetermin  ST No. = " + destSt.getStationNumber());
System.out.println("replace hte destination with the one RouteController has selected in StorageSender destDetermin ST No. = " + (wRouteController.getDestStation().getStationNumber()));
				//#CM31855
				// Replace the destination with the new oner that RouteController has selected.
				if (!destSt.getStationNumber().equals(wRouteController.getDestStation().getStationNumber()))
				{
					//#CM31856
					// Set the receiving StationNumber and aisle StaitonNumber in CarryInformation.
System.out.println("In StorageSender destDetermin, set receiving StationNumber and aisle StationNumber in CarryInformation");

					//#CM31857
					// to update CarryInfo
					CarryInformationHandler carryInfoHandler = new CarryInformationHandler(wConn);
					CarryInformationAlterKey carryAlterKey = new CarryInformationAlterKey();
					//#CM31858
					// set search key
					carryAlterKey.KeyClear();
					carryAlterKey.setCarryKey(cInfo.getCarryKey());
					carryAlterKey.updateDestStationNumber(wRouteController.getDestStation().getStationNumber());
					carryAlterKey.updateAisleStationNumber(wRouteController.getDestStation().getAisleStationNumber());
					//#CM31859
					// update
					carryInfoHandler.modify(carryAlterKey);
					//#CM31860
					//to reflect the contents modified by the conveyance send instructions (if not reset, Warehouse no. remains as it is)
					cInfo.setDestStationNumber(wRouteController.getDestStation().getStationNumber());

					//#CM31861
					// update Palette
					//#CM31862
					// set search key
					paletteAlterKey.KeyClear();
					paletteAlterKey.setPaletteId(cInfo.getPaletteId());
					paletteAlterKey.updateCurrentStationNumber(wRouteController.getDestStation().getStationNumber());
					paletteAlterKey.updateRefixDate(new java.util.Date());
					//#CM31863
					// update
					paletteHandelr.modify(paletteAlterKey);

					//#CM31864
					// if the work type is storage, unplanned storage, don't update stock
					if((!cInfo.getWorkType().equals(CarryInformation.WORKTYPE_PLAN_STORAGE))
					&& (!cInfo.getWorkType().equals(CarryInformation.WORKTYPE_NOPLAN_STORAGE)))
					{
						//#CM31865
						//update Stock
						stockAkey.KeyClear();
						stockAkey.setPaletteid(palette.getPaletteId());
						stockAkey.updateLocationNo(wRouteController.getDestStation().getStationNumber());
						stockHandle.modify(stockAkey);
					}
				}

				if (destSt instanceof Shelf)
				{
					//#CM31866
					// Get the load size data to send to equipment from the location/hard zone data.
					//#CM31867
					// Set the acquired value to the pallet.
					Shelf shf = (Shelf)destSt;
					HardZoneSearchKey key = new HardZoneSearchKey();
					key.setHardZoneID(shf.getHardZoneID());
					HardZone[] hzones = (HardZone[])wHardZoneHandler.find(key);
					if (hzones.length == 0)
					{
						//#CM31868
						// In case the hard zone data cannot be found, set the status of carry data "error".
						carryFailure(cInfo);
						Object[] tObj = new Object[2];
						tObj[0] = shf.getStationNumber();
						tObj[1] = Integer.toString(shf.getHardZoneID());
						//#CM31869
						// 6026070=Hard zone of the specified location is not found. LocationNo.:{0} Hard zone ID:{1}
						RmiMsgLogClient.write(6026070, LogMessage.F_ERROR, "StorageSender", tObj);
						return false;
					}
					//#CM31870
					// Replace the pallet size with the size of retrieved hard zone.

					//#CM31871
					// update Palette
					//#CM31872
					// set search key
					paletteAlterKey.KeyClear();
					paletteAlterKey.setPaletteId(cInfo.getPaletteId());
					paletteAlterKey.updateHeight(hzones[0].getHeight());
					//#CM31873
					// update
					paletteHandelr.modify(paletteAlterKey);
				}
			}
			else
			{
System.out.println("StorageSender Check for determination of receiving station/carry route determined unacceptable by destDetermin.");
				return false;
			}
		}
		//#CM31874
		// Occurs if there is any error in updated data.
		catch (InvalidStatusException e)
		{
			carryFailure(cInfo);
			return false;
		}
		//#CM31875
		// Occurs if there is any error in updated data.
		catch (InvalidDefineException e)
		{
			carryFailure(cInfo);
			return false;
		}
		//#CM31876
		// Occurs in StationFactory.makeStation
		catch (NotFoundException e)
		{
			carryFailure(cInfo);
			return false;
		}

		//#CM31877
		// Able to send carry instruction
		return true;
	}

	//#CM31878
	/**
	 * Edit the text of carry instruction and send.
	 * The transmission of carry instruction text will be sent via RMI. If it is normally done, alter the carry state
	 * to 'waiting for reply'.
	 * @param  cInfo			instance of CarryInformation
	 * @param  gc				instatnce of GroupController to which the carry instruction text is sent to
	 * @throws Exception		Notifies if trouble occurs in reading/writing in database.
	 * @throws Exception		Notifies if there are any errors with the updated instance.
	 */
	protected void sendText(CarryInformation cInfo, GroupController gc) throws Exception
	{
		Object[] param = new Object[2];
		DecimalFormat fmt = new DecimalFormat("00");

		try
		{
			//#CM31879
			// If it is possible to send the carry data, send the text then change the carry state to 'waiting for reply'.
			//#CM31880
			// Creating the communciation message to send from CarryInfomation
			As21Id05 id05 = new As21Id05(cInfo);
			String sendMsg = id05.getSendMessage();
System.out.println("StorageSender Communication message length of carry instruction = " + sendMsg.length());
System.out.println("StorageSender Communication message of carry instruction = " + sendMsg);
			//#CM31881
			// Call the write method of As21Sender using RMI.
			RmiSendClient rmiSndC = new RmiSendClient();
			param[0] = sendMsg;

			//#CM31882
			// Renew the status of CarryInformation, which the instruciton has just been sent, to 'waiting for reply'.
			//#CM31883
			// to update CarryInfo
			CarryInformationHandler carryInfoHandler = new CarryInformationHandler(wConn);
			CarryInformationAlterKey carryAlterKey = new CarryInformationAlterKey();
			//#CM31884
			// set search key
			carryAlterKey.KeyClear();
			carryAlterKey.setCarryKey(cInfo.getCarryKey());
			carryAlterKey.updateCmdStatus(CarryInformation.CMDSTATUS_WAIT_RESPONSE);

			//#CM31885
			// update
			carryInfoHandler.modify(carryAlterKey);

			//#CM31886
			//Commit the transaction before sending the text.
			wConn.commit();

			rmiSndC.write("AGC"+ fmt.format(gc.getNumber()), param);
			rmiSndC = null;
			param[0] = null;
		}
		//#CM31887
		// Occurs if trouble occurs when fixing the transaction.
		catch (SQLException e)
		{
			StringWriter sw = new StringWriter();
			PrintWriter  pw = new PrintWriter(sw);
			e.printStackTrace(pw) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = new Integer(e.getErrorCode());
			tObj[1] = sw.toString() ;
			//#CM31888
			// 6007030=Database error occured. error code={0}
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw new ReadWriteException("6007030" + wDelim + tObj[0]) ;
		}
		//#CM31889
		// Occurs if there are any error when editing text.
		catch (InvalidProtocolException e)
		{
			//#CM31890
			// 6026042=Trouble occurred in obtaining transfer data. Exception:{0}
			RmiMsgLogClient.write( new TraceHandler(6026042, e), this.getClass().getName() ) ;
			carryFailure(cInfo);
		}
		//#CM31891
		// Occurs if there are any error in updated data.
		catch (InvalidStatusException e)
		{
			//#CM31892
			// 6026042=Trouble occurred in obtaining transfer data. Exception:{0}
			RmiMsgLogClient.write( new TraceHandler(6026042, e), this.getClass().getName() ) ;
			carryFailure(cInfo);
		}
		//#CM31893
		// Occurs if there are any error in updated data.
		catch (InvalidDefineException e)
		{
			//#CM31894
			// 6026042=Trouble occurred in obtaining transfer data. Exception:{0}
			RmiMsgLogClient.write( new TraceHandler(6026042, e), this.getClass().getName() ) ;
			carryFailure(cInfo);
		}
		//#CM31895
		// Occurs if there are any error in updated data.
		catch (IOException e)
		{
			//#CM31896
			// Communication message could not be sent out.
			//#CM31897
			// 6026058=Cannot send the text. {0}
			RmiMsgLogClient.write( new TraceHandler(6026058, e), this.getClass().getName() ) ;
			carryFailure(cInfo);
		}
	}

	//#CM31898
	/**
	 * Alter the carry status of the specified CarryInforamtion instance to 'error'.
	 * Once changed to error, this CarryInformation will be used for carry any longer.
	 * Also in order to mirror the updated contents, fix the transaction within this method.
	 * @param  failureTarget	instance of CarryInformation for this carry
	 * @throws Exception		Notifies if trouble occurs in reading/writing in database.
	 * @throws Exception		Notifies if there are any errors with the updated instance.
	 */
	protected void carryFailure(CarryInformation failureTarget) throws Exception
	{
		try
		{
			if (failureTarget != null)
			{
				//#CM31899
				// Rollback the updates made up to this moment.
				wConn.rollback();
				//#CM31900
				// Renew the status to 'error'.
				//#CM31901
				// to update CarryInfo
				CarryInformationHandler carryInfoHandler = new CarryInformationHandler(wConn);
				CarryInformationAlterKey carryAlterKey = new CarryInformationAlterKey();
				//#CM31902
				// set search key
				carryAlterKey.KeyClear();
				carryAlterKey.setCarryKey(failureTarget.getCarryKey());
				carryAlterKey.updateCmdStatus(CarryInformation.CMDSTATUS_ERROR);

				//#CM31903
				// update
				carryInfoHandler.modify(carryAlterKey);

				//#CM31904
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
			//#CM31905
			// 6007030=Database error occured. error code={0}
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw new ReadWriteException("6007030" + wDelim + tObj[0]) ;
		}
	}

	//#CM31906
	/**
	 * Change the reqest flag to 'requesting'.
	 * If the flag states 'requesting', carry instruciton sill not be in wait state.
	 */
	protected void setRequest()
	{
		wRequest = true;
	}

	//#CM31907
	/**
	 * Change the reqest flag to 'no request'.
	 * If the flag states 'no request', carry instruciton should shift to wait state.
	 */
	protected void restRequest()
	{
		wRequest = false;
	}

	//#CM31908
	/**
	 * Returns the current status of the request flag.
	 * It returns 'true' if the flag is requesting and 'false' if it is not.
	 * @return 'true' if the flag is requesting and 'false' if it is not
	 */
	protected boolean isRequest()
	{
		return wRequest;
	}

	//#CM31909
	// Private methods -----------------------------------------------

}
//#CM31910
//end of class
