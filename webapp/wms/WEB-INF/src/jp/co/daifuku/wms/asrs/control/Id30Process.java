// $Id: Id30Process.java,v 1.2 2006/10/26 03:05:24 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM32945
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id30;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.dbhandler.AisleAlterKey;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.MachineAlterKey;
import jp.co.daifuku.wms.base.dbhandler.MachineHandler;
import jp.co.daifuku.wms.base.dbhandler.MachineSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Machine;
import jp.co.daifuku.wms.base.entity.Station;

//#CM32946
/**
 * This is the machine status report processing class
 * Update the machine status that corresponds machine type code, terminal no 
 * If machine status is updated, change the station status related to that machine to disable or enable
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2006/02/02</TD><TD>Y.Okamura</TD><TD>eWareNavi</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 03:05:24 $
 * @author  $Author: suresh $
 */
public class Id30Process extends IdProcess
{
	//#CM32947
	/**
	 * AGCNo.
	 */
	private int  wAgcNumber;
	
	//#CM32948
	/**
	 * Deummy value will not be used. (used in Hashtable)
	 */
	private static final String DUMMY = "_";

	//#CM32949
	// Class variables -----------------------------------------------

	//#CM32950
	// Class method --------------------------------------------------
	//#CM32951
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 03:05:24 $") ;
	}

	//#CM32952
	// Constructors --------------------------------------------------
	//#CM32953
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id30Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}
	
	//#CM32954
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id30Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM32955
	// Public methods ------------------------------------------------
	//#CM32956
	/**
	 * Change the station status to available or not available. Update the database using handler.
	 * Count machines with status DOWN or OFFLINE
	 *If atleast one with the above status, update the station status to not available
	 *Else update the station status to available
	 * @param  conn	Database connection object
	 * @param  st		station
	 * @throws InvalidStatusException Throw exception if the table update value is not acceptable
	 * @throws InvalidDefineException Throw exception if the table update condition is not acceptable
	 * @throws ReadWriteException     Throw exception if error occurs during database access
	 * @throws NotFoundException      Throw exception if this station is not found in the database
	 */
	public void updateStatus(Connection conn, Station st) throws
									InvalidStatusException,
									InvalidDefineException,
									ReadWriteException,
									NotFoundException
	{
		//#CM32957
		// Gets machine information supporting this station by using MachineHandler.
		MachineHandler mhandl = new MachineHandler(conn);
		MachineSearchKey key = new MachineSearchKey();
		key.setStationNumber(st.getStationNumber());
		Machine[] mstat = (Machine[]) mhandl.find(key);
		
		//#CM32958
		// Checks the status of machine data group obtained.
		// If the error status or off-line status are included in the machine status, 
		// it gives priority to off-line status when storing in memory.
		//#CM32959
		//CMENJP2662$CM If Abnormal and Offline status is available for machine status, give priority to Offline
	int ngCnt = 0;
		int as21Stat = Machine.STATE_ACTIVE;
		for (int i = 0 ; i < mstat.length ; i++)
		{
			switch (mstat[i].getState())
			{
				//#CM32960
				// down
				case Machine.STATE_FAIL:
					ngCnt++;
					if (as21Stat != Machine.STATE_OFFLINE)
					{
						as21Stat = Machine.STATE_FAIL;
					}
					break;
					
					//#CM32961
					// separated
				case Machine.STATE_OFFLINE:
					ngCnt++;
					as21Stat = Machine.STATE_OFFLINE;
					break;
					
				default:
					break;
			}
		}
		
		//#CM32962
		// If the counts of error or off-line exceeded the standard, it renews the status of this station.
		// If the counts remain standard or below, and if current status of the station is anything other than
		// normal, it should alter the status to 'normal'.
		//#CM32963
		//CMENJP2665$CM If not standard, if the current station status is other than normal, change it to normal status
	int mcStat = -1;
		if (ngCnt > Station.STATION_NG_JUDGMENT)
		{
			if (st.getStatus() == Station.STATION_OK)
			{
				if (as21Stat == Machine.STATE_FAIL)
				{
					mcStat = Station.STATION_FAIL;
				}
				else
				{
					mcStat = Station.STATION_NG;
				}
			}
		}
		else
		{
			if (st.getStatus() != Station.STATION_OK)
			{
				//#CM32964
				// Update the station status to available
				mcStat = Station.STATION_OK;
			}
		}
		
		if (mcStat == -1)
		{
			return;
		}
		
		if (st instanceof Aisle)
		{
			AisleHandler aisleh = new AisleHandler(conn);
			AisleAlterKey aisleAKey = new AisleAlterKey();
			aisleAKey.setStationNumber(st.getStationNumber());
			aisleAKey.updateStatus(mcStat);
			aisleh.modify(aisleAKey);
		}
		else if (st instanceof Station)
		{
			StationHandler sth = new StationHandler(conn);
			StationAlterKey stAKey = new StationAlterKey();
			stAKey.setStationNumber(st.getStationNumber());
			stAKey.updateStatus(mcStat);
			sth.modify(stAKey);
		}
	}

	//#CM32965
	// Package methods -----------------------------------------------
	
	//#CM32966
	// Protected methods ---------------------------------------------
	//#CM32967
	/**
	 * Processing the communication message of machine status report.
	 * Based on the model code, machine no. and AGCno. in received communication mesasage, it 
	 * searches <code>MACHINE</code> then updates the corresponding machine information.
	 * It also modifies the status of station this machine belongs to according to the contents of machine information.
	 * Also if the updated station is defined with parent station, the parent station will be modified accordingly.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		//#CM32968
		// Generates the instance of As21Id30.
		As21Id30 id30dt = new As21Id30(rdt) ;

		MachineHandler as21MstatH = new MachineHandler(wConn);
		MachineSearchKey machineKey = new MachineSearchKey();

		//#CM32969
		// Generates the instance of Hashtable.
		Hashtable hashtable = new Hashtable();
		Hashtable parentHash = new Hashtable();

		//#CM32970
		// Process as much report data in received text.
		for (int i = 0; i < id30dt.getNumberOfReports(); i++)
		{
			int imodelCode = Integer.parseInt(id30dt.getModelCode(i));
			int imachineNo = Integer.parseInt(id30dt.getMachineNo(i));

			//#CM32971
			// Search according to the search items (model code, machine no. and AGCno.) as a KEY.
			machineKey.KeyClear();
			machineKey.setMachineType(imodelCode);
			machineKey.setMachineNumber(imachineNo);
			machineKey.setControllerNumber(wAgcNumber);
			Machine machine = (Machine) as21MstatH.findPrimary(machineKey); ;
			if (machine == null)
			{
				//#CM32972
				// Output the log if reported machine information was not found.
				Object[] tObj = new Object[3] ;
				tObj[0] = new Integer(imodelCode);
				tObj[1] = new Integer(imachineNo);
				tObj[2] = new Integer(wAgcNumber);
				//#CM32973
				// 6024026=Reported equipment is not found in machine data. Type={0} Machine No.={1} SRC No.={2}
				RmiMsgLogClient.write(6024026, LogMessage.F_WARN, "Id30Process", tObj);
				continue;
			}
			
			//#CM32974
			// Updates the machine information.
			MachineAlterKey machineAKey = new MachineAlterKey();
			machineAKey.setMachineType(machine.getMachineType());
			machineAKey.setMachineNumber(machine.getMachineNumber());
			machineAKey.setControllerNumber(machine.getControllerNumber());
			switch (id30dt.getCondition(i))
			{
				//#CM32975
				// operating
				case As21Id30.STATE_ACTIVE:
					machineAKey.updateState(Machine.STATE_ACTIVE);
					machineAKey.updateErrorCode(null);
					as21MstatH.modify(machineAKey);
					break;
					
					//#CM32976
					// Ustopped
				case As21Id30.STATE_STOP:
					machineAKey.updateState(Machine.STATE_STOP);
					machineAKey.updateErrorCode(null);
					as21MstatH.modify(machineAKey);
					break;
					
					//#CM32977
					// down
				case As21Id30.STATE_FAIL:
					machineAKey.updateState(Machine.STATE_FAIL);
					machineAKey.updateErrorCode(id30dt.getAbnormalCode(i));
					as21MstatH.modify(machineAKey);
					break;
					
					//#CM32978
					// separated
				case As21Id30.STATE_OFFLINE:
					machineAKey.updateState(Machine.STATE_OFFLINE);
					machineAKey.updateErrorCode(null);
					as21MstatH.modify(machineAKey);
					break;
			}
			
			//#CM32979
			// Stores the updated STATIONNUMBER.
			hashtable.put(machine.getStationNumber(), DUMMY);
		}

		//#CM32980
		// Based on each STATIONNUMBER, looks into the machine status of same STATIONNUMBER using MACHINE table.
		Enumeration e = hashtable.keys();

		//#CM32981
		// Retrieves STATIONNUMBER from Hashtable, then updates the status of Stations based on machine status.
		while (e.hasMoreElements())
		{
			String stnum = e.nextElement().toString();
			
			//#CM32982
			// Updates the station table.
			//#CM32983
			// Generates the station instance with STATIONNUMBER as condition, then updates the status
			//#CM32984
			// based on the machine information.
			Station st = StationFactory.makeStation(wConn, stnum);
			updateStatus(wConn, st);

			//#CM32985
			// In case the parent station no. is included,
			//#CM32986
			// it records the no. in HashTable in order to update the status of parent station later.
			if (!StringUtil.isBlank(st.getParentStationNumber()))
			{
				parentHash.put(st.getParentStationNumber(), DUMMY);
			}
		}

		Enumeration e2 = parentHash.keys();

		StationHandler sth = new StationHandler(wConn);
		StationSearchKey stKey = new StationSearchKey();
		StationAlterKey stAKey = new StationAlterKey();
		while (e2.hasMoreElements())
		{
			//#CM32987
			// If even a single sub station that belongs to the current station is available,
			//#CM32988
			// make current station available
			//#CM32989
			// and if all the sub-station to current station is not available,
			//#CM32990
			// make current station unavailable
			//#CM32991
			// but, if current station exists in the workplace (sendable=false), don't update 
			Station mainSt = StationFactory.makeStation(wConn, e2.nextElement().toString());
			
			stKey.KeyClear();
			stKey.setParentStationNumber(mainSt.getStationNumber());
			stKey.setStatus(Station.STATION_OK);
			//#CM32992
			// if atleast one sub-station is available
			if (sth.count(stKey) > 0)
			{
				//#CM32993
				// if the current station does not exist in workplace
				if (mainSt.isSendable())
				{
					if (mainSt.getStatus() == Station.STATION_NG)
					{
						stAKey.KeyClear();
						stAKey.setStationNumber(mainSt.getStationNumber());
						stAKey.updateStatus(Station.STATION_OK);
						sth.modify(stAKey);
					}
				}
			}
			//#CM32994
			// if all the sub-station is unavailable
			else
			{
				//#CM32995
				// if the current station does not exist in workplace
				if (mainSt.isSendable())
				{
					if (mainSt.getStatus() == Station.STATION_OK)
					{
						stAKey.KeyClear();
						stAKey.setStationNumber(mainSt.getStationNumber());
						stAKey.updateStatus(Station.STATION_NG);
						sth.modify(stAKey);

					}
				}
			}
		}
	}
}
//#CM32996
//end of class

