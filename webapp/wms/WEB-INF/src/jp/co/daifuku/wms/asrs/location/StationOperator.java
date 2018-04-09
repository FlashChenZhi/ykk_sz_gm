// $Id: StationOperator.java,v 1.2 2006/10/26 08:32:26 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM43064
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.dbhandler.MachineHandler;
import jp.co.daifuku.wms.base.dbhandler.MachineSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Machine;
import jp.co.daifuku.wms.base.entity.CarryInformation;

//#CM43065
/**<en>
 * The class which collected processing which operates it to station information. <BR> 
 * Offer the methods such as the mode switch, interrupting, and renewing the station.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/22</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:32:26 $
 * @author  $Author: suresh $
 </en>*/
public class StationOperator extends Entity
{
	//#CM43066
	// Class fields --------------------------------------------------
	//#CM43067
	/**
	 * Database connection
	 */
	Connection wConn = null;
	
	//#CM43068
	/**
	 * Station instance
	 */
	Station wStation = null;
	

	//#CM43069
	// Class variables -----------------------------------------------	
	//#CM43070
	/**<en>
	 * Delimiter
	 * This is the delimiter of the parameter for MessageDef when Exception occured.
	 * 	ex. String msginfo = "9000000" + wDelim + "Palette" + wDelim + "Stock" ;
	 </en>*/
	public String wDelim = MessageResource.DELIM ;

	//#CM43071
	// Class method --------------------------------------------------
	//#CM43072
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:32:26 $") ;
	}

	//#CM43073
	// Constructors --------------------------------------------------
	//#CM43074
	/**
	 * Make the instance of new <code>StationOperator</code>.
	 * Station No passed by the argument . Generate and maintain the Station or more instance.
	 * @param conn Database connection
	 * @param stno Station No.
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 */
	public StationOperator(Connection conn, String stno) throws ReadWriteException
	{
		wConn = conn;
		wStation = getStation(conn, stno);
	}

	//#CM43075
	/**
	 * Make the instance of new <code>StationOperator</code>.
	 * Maintain the Station instance passed by the argument.
	 * @param conn Database connection
	 * @param st   Station instance
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 */
	public StationOperator(Connection conn, Station st) throws ReadWriteException
	{
		wConn = conn;
		wStation = st;
	}

	//#CM43076
	// Public methods ------------------------------------------------
	//#CM43077
	/**
	 * Return Station instance.
	 * @return Station instance
	 */
	public Station getStation()
	{
		return wStation;
	}
	
	//#CM43078
	/**<en>
	 * Modifies the station status to 'available' or to 'unavailable'. Updates the database using handler.
	 * It counts the number of machines on error or off-line status. 
	 * If there is one or more machine error or off-line, it updates the station status 'unavailable'.
	 * If there is no error or off-line machine at all, it updates the station status 'available'.
     * @throws InvalidStatusException :Notifies if there are any inconsistency in set values for table updates.
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
	 * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
	 * @throws NotFoundException      :Notifies if this station was not found in database.
	 </en>*/
	public void updateStatus() throws
									InvalidStatusException,
									InvalidDefineException,
									ReadWriteException,
									NotFoundException
	{
		//#CM43079
		//<en> Gets machine information supporting this station by using MachineHandler.</en>
		MachineHandler mhandl = new MachineHandler(wConn);
		MachineSearchKey key = new MachineSearchKey();
		key.setStationNumber(wStation.getStationNumber());
		Machine[] mstat = (Machine[])mhandl.find(key);
		
		//#CM43080
		//<en> Checks the status of machine data group obtained.</en>
		//<en> If the error status or off-line status are included in the machine status, </en>
		//<en> it gives priority to off-line status when storing in memory.</en>
		//#CM43081
		//<en>It gives priority to cutting off when abnormality and cutting off are included in the state of the CMENJP4319$CM equipment and it memorizes it. </en>
	int cnt = 0;
		int stat = Machine.STATE_ACTIVE;
		for (int i = 0 ; i < mstat.length ; i++)
		{
			switch (mstat[i].getState())
			{
				case Machine.STATE_FAIL:
					cnt++;
					if (stat != Machine.STATE_OFFLINE) stat = Machine.STATE_FAIL;
					break;
					
				case Machine.STATE_OFFLINE:
					cnt++;
					stat = Machine.STATE_OFFLINE;
					break;
					
				default:
					break;
			}
		}
		
		//#CM43082
		//<en> If the counts of error or off-line exceeded the standard, it renews the status of this station.</en>
		//<en> If the counts remain standard or below, and if current status of the station is anything other than</en>
		//<en> normal, it should alter the status to 'normal'.</en>
		//#CM43083
		//<en>Change normally if the state of a present station is normal below the CMENJP4320$CM standard. </en>
	StationAlterKey altkey = new StationAlterKey();
		StationHandler handl = new StationHandler(wConn);

		if (cnt > Station.STATION_NG_JUDGMENT)
		{
			if (wStation.getStatus() == Station.STATION_OK)
			{
				if (stat == Machine.STATE_FAIL)
				{
					altkey.setStationNumber(wStation.getStationNumber());
					altkey.updateStatus(Station.STATION_FAIL);
					handl.modify(altkey);
				}
				else
				{
					altkey.setStationNumber(wStation.getStationNumber());
					altkey.updateStatus(Station.STATION_NG);
					handl.modify(altkey);
				}
			}
		}
		else
		{
			if (wStation.getStatus() != Station.STATION_OK)
			{
				//#CM43084
				//<en> Update the status of the station to 'available'.</en>
				altkey.setStationNumber(wStation.getStationNumber());
				altkey.updateStatus(Station.STATION_OK);
				handl.modify(altkey);
			}
		}
	}

	//#CM43085
	/**<en>
	 * Alters the suspention flag. It updates the database using handler.
	 * @param sus :suspention flag
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
	 * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
	 * @throws NotFoundException      :Notifies if this station was not found in database.
	 </en>*/
	public void alterSuspend(boolean sus) throws
											InvalidDefineException,
											ReadWriteException,
											NotFoundException
	{
		StationAlterKey altkey = new StationAlterKey();
		altkey.setStationNumber(wStation.getStationNumber());
		if (sus)
		{
			altkey.updateSuspend(Station.SUSPENDING);
		}
		else
		{
			altkey.updateSuspend(Station.NOT_SUSPEND);
		}
		StationHandler handl = new StationHandler(wConn);
		handl.modify(altkey);
	}

	//#CM43086
	/**<en>
	 * Alters the end-use station. It updates the database using handler.
	 * @param  lst :final end-use station
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
	 * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
	 * @throws NotFoundException      :Notifies if this station was not found in database.
	 </en>*/
	public void alterLastUsedStation(String lstno) throws
													InvalidDefineException, 
													ReadWriteException, 
													NotFoundException
	{
		StationAlterKey altkey = new StationAlterKey();
		altkey.setStationNumber(wStation.getStationNumber());
		altkey.updateLastUsedStationNumber(lstno);
		StationHandler handl = new StationHandler(wConn);
		handl.modify(altkey);
	}

	//#CM43087
	/**<en>
	 * Records the MCKEY and the load size information of carry data in station.
     * Commit a database transaction in this method.
	 * <Reason>
	 * Normally after this method, the carry instruction processing is requested to send carry isntruction task.
	 * In that case the transmission of carry isntruction may be get delayed depending on the timing of Commit.
	 * Therefore the contents of database needs to be fixed at this point.
	 * @param ci :CarryInformation to update
	 * @param plt:Palette instance
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
	 * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
	 * @throws NotFoundException      :Notifies if this station was not found in database.
	 </en>*/
	public void registArrival(String carrykey, Palette plt) throws 
																		InvalidDefineException,
																		ReadWriteException,
																		NotFoundException
	{
		StationAlterKey altkey = new StationAlterKey();
		altkey.setStationNumber(getStationNumber());
		altkey.updateCarryKey(carrykey);
		altkey.updateBCData(plt.getBcData());
		
		//#CM43088
		//<en> If the station operates load size checking, set the load size the Palette preserves.</en>
		if (getStation().isLoadSizeCheck())
		{
			altkey.updateHeight(plt.getHeight());
		}
		StationHandler handl = new StationHandler(wConn);
		handl.modify(altkey);

        //#CM43089
		//<en>commit the transaction</en>
        try
        {
			wConn.commit();
        }
        catch(SQLException e)
        {
            throw new ReadWriteException(e.getMessage());
        }
	}

	//#CM43090
	/**<en>
	 * Clears the CARRYKEY, load size information and BC data in station.
	 * This will be used when arrival information is unnecessary due to data cancel or receiving of 
	 * reply to carry instructions, etc..
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
	 * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
	 * @throws NotFoundException      :Notifies if data to delete cannot be found.
	 </en>*/
	public void dropArrival() throws InvalidDefineException, ReadWriteException, NotFoundException
	{		
		StationAlterKey altkey = new StationAlterKey();
		altkey.setStationNumber(wStation.getStationNumber());
		altkey.updateCarryKey(null);
		altkey.updateBCData(null);
		altkey.updateHeight(0);		
		StationHandler handl = new StationHandler(wConn);
		handl.modify(altkey);
	}

	//#CM43091
	/**<en>
	 * Processes the arrived carry data.
	 * This method is implemented by sub class on demand.
	 * @param ci :target CarryInformation
	 * @param plt:target Palette
	 * @throws InvalidDefineException :Notifies if there are any data inconcistency in instance.
	 * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
	 * @throws NotFoundException      :Notifies if there is no such data.
	 </en>*/
	public void arrival(CarryInformation ci, Palette plt) throws InvalidDefineException, 
																	 ReadWriteException,
																	 NotFoundException
	{
		//#CM43092
		//<en> This is not a proper call. The on-line indication cannot be updated in this station.</en>
		throw new InvalidDefineException("This Station is Not arrival operation"); 
	}

	//#CM43093
	/**<en>
	 * Processes the on-line indications and its updates.
	 * This method is implemented by sub class on demand.
	 * If this method is called in <code>Station</code>, InvalidDefineException will be notified.
	 * @param ci :target CarryInformation
	 * @throws InvalidDefineException :Notifies if there are any data inconcistency in instance.
	 * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
	 * @throws NotFoundException      :Notifies if there is no such data.
	 </en>*/
	public void operationDisplayUpdate(CarryInformation ci) throws InvalidDefineException, 
																	 ReadWriteException,
																	 NotFoundException
	{
		//#CM43094
		//<en> This is not a proper call. The on-line indication cannot be updated in this station.</en>
		throw new InvalidDefineException("This Station is Not Operation Display"); 
	}

	//#CM43095
	/**<en>
	 * Updates the carry data.
	 * This method is implemented by sub class on demand.
	 * @param ci :target CarryInformation
	 * @throws InvalidDefineException :Notifies if there are any data inconcistency in instance.
	 * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
	 * @throws NotFoundException      :Notifies if there is no such data.
	 </en>*/
	public void updateArrival(CarryInformation ci) throws InvalidDefineException,
																	 ReadWriteException,
																	 NotFoundException
	{
		//#CM43096
		//<en> This is not a proper call. The on-line indication cannot be updated in this station.</en>
		throw new InvalidDefineException("This Station is Not updateArrival operation"); 
	}
	
	//#CM43097
	// Package methods -----------------------------------------------

	//#CM43098
	// Protected methods ---------------------------------------------
	//#CM43099
	/**
	 * Return Station No.
	 * @return Station No.
	 */
	protected String getStationNumber()
	{
		return wStation.getStationNumber();		
	}
	
	//#CM43100
	/**
	 * Return the work display operation.
	 * @return Work display operation
	 */
	protected int getOperationDisplay()
	{
		return wStation.getOperationDisplay();		
	}
	
	//#CM43101
	/**<en>
	 * Submits request for carry instruction processing to the transmit task of carry instruction.
	 * This method will be used when the carry isntruction needs to be sent aftere the arrival process
	 * at station.
	 * Normally the class derived from this class will use this after the arrival processing.
	 * Alternatives between carry isntruction and automatic mode switching carry isntruction for the
	 * transmission will be selected according to the mode swtich type of this station.
	 * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
	 </en>*/
	protected void carryRequest() throws ReadWriteException
	{
		try
		{
			//#CM43102
			//<en> If it is the automatic mode switching station,</en>
			//#CM43103
			//<en> it submits the message in order to pulls the automatic mode switch </en>
			//#CM43104
			//<en> carry instruction out from wait().</en>
			if (wStation.getModeType() == Station.AUTOMATIC_MODE_CHANGE)
			{
				//#CM43105
				//<en> Sends request message to AutomaticModeChangeSender.</en>
				RmiSendClient rmiSndC = new RmiSendClient();
				Object[] param = new Object[2];
				param[0] = null;
				rmiSndC.write("AutomaticModeChangeSender", param);
			}
			else
			{
				//#CM43106
				//<en> Sends request message to StorageSender.</en>
				RmiSendClient rmiSndC = new RmiSendClient();
				Object[] param = new Object[2];
				param[0] = null;
				rmiSndC.write("StorageSender", param);
			}
		}
		catch (Exception e)
		{
			//#CM43107
			//<en> Taking the structure of the call source (arrival mthod) into account, </en>
			//#CM43108
			//<en> it throws ReadWriteException.</en>
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM43109
	// Private methods -----------------------------------------------
	//#CM43110
	/**
	 * Acquire station information from the data base and generate the Station instance. 
	 * @param conn Connection with database
	 * @param stno Station No of station.
	 * @return Station instance
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 */
	private Station getStation(Connection conn, String stno) throws ReadWriteException
	{
		StationSearchKey key = new StationSearchKey();
		key.setStationNumber(stno);
		StationHandler wStationHandler = new StationHandler(conn);
		Entity[] ent = wStationHandler.find(key);
		
		return (Station)ent[0];		
	}
}
//#CM43111
//end of class
