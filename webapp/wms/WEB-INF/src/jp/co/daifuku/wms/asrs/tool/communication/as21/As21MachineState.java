// $Id: As21MachineState.java,v 1.2 2006/10/30 01:46:09 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.communication.as21 ;

//#CM46969
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntityHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAs21MachineAlterKey;
import jp.co.daifuku.wms.asrs.tool.equipment.MachineState;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;

//#CM46970
/**<en>
 * This class controls the machine status according to the AS21 protocol.
 * It controls the status of machines themselves, and the conceptional states of 
 * availability such as error code, off-line, etc.
 * In order to retrieve the latest status, please utilize the <code>ToolAs21MachineStateHandler</code>
 * and retrieve hte latest instance.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:46:09 $
 * @author  $Author: suresh $
 </en>*/
public class As21MachineState extends MachineState
{
	//#CM46971
	// Class fields -----------------------------------------------
	//#CM46972
	/**<en>
	 * Field of protocol type (A21 protocol)
	 </en>*/
	public static final String PROTOCOL_AS21 = "AS21" ;

	//#CM46973
	/**<en>
	 * Field of machine status (disconnected)
	 </en>*/	
	public static final int STATE_DISCONNECT = -1 ;
	
	//#CM46974
	/**<en>
	 * Field of machine status  (active)
	 </en>*/
	public static final int STATE_ACTIVE = 0 ;
	
	//#CM46975
	/**<en>
	 * Field of machine status (stopped)
	 </en>*/
	public static final int STATE_STOP = 1 ;
	
	//#CM46976
	/**<en>
	 * Field of machine status (failure)
	 </en>*/
	public static final int STATE_FAIL = 2 ;
	
	//#CM46977
	/**<en>
	 * Field of machine status (off-line)
	 </en>*/
	public static final int STATE_OFFLINE = 3 ;

	//#CM46978
	// Class variables -----------------------------------------------
	//#CM46979
	/**<en>
	 * station no.
	 </en>*/
	protected String wStationNumber = null;
	
	//#CM46980
	/**<en>
	 * machine type
	 </en>*/
	protected int wType = 0;
	
	//#CM46981
	/**<en>
	 * machine no.
	 </en>*/
	protected int wNumber = 0;
	
	//#CM46982
	/**<en>
	 * machine status
	 </en>*/
	protected int wState = 0;
	
	//#CM46983
	/**<en>
	 * error code
	 </en>*/
	protected String wErrCode = null;
	
	//#CM46984
	/**<en>
	 * controller no.
	 </en>*/
	protected int wControllerNumber = 0;
	
	//#CM46985
	/**<en>
	 * instance handler
	 </en>*/
	protected ToolEntityHandler wHandler = null ;

	//#CM46986
	// Class method --------------------------------------------------
    //#CM46987
    /**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:46:09 $") ;
	}
	
	//#CM46988
	// Constructors --------------------------------------------------
	//#CM46989
	/**<en>
	 * Constructs the As21MachineState object.
	 </en>*/
	public As21MachineState()
	{
	}

	//#CM46990
	/**<en>
	 * Create the <code>As21MachineState</code> instance according to the machine type, controller no.
	 * and the machine no.<BR>
	 * @param  type :type of <code>Machine</code> to create
	 * @param  num  :machine no. of <code>Machine</code> to create
	 * @param  cnt  :controller no. of <code>Machine</code> to create
	 </en>*/
	public As21MachineState(int type, int num, int cnt)
	{
		setType(type) ;
		setNumber(num) ;
		setControllerNumber(cnt);
	}
	
	//#CM46991
	/**<en>
	 * Based on the machine type and the machine no., generate the instance of 
	 * <code>As21MachineState</code>.<BR>
	 * @param  type :type of <code>MachineState</code> creating
	 * @param  num  :machine no. of <code>MachineState</code> creating
	 </en>*/
	public As21MachineState(int type, int num)
	{
		setType(type) ;
		setNumber(num) ;
	}

	//#CM46992
	// Public methods ------------------------------------------------
	//#CM46993
	/**<en>
	 * Set the value of station no.
	 * @param stno :station no. to set
	 </en>*/
	public void setStationNumber(String stno)
	{
		wStationNumber = stno ;
	}

	//#CM46994
	/**<en>
	 * Retrieve the station no.
	 * @return  station no.
	 </en>*/
	public String getStationNumber()
	{
		return wStationNumber ;
	}

	//#CM46995
	/**<en>
	 * Set the machine type.
	 * @param type  :machine type
	 </en>*/
	public void setType(int type)
	{
		wType = type ;
	}

	//#CM46996
	/**<en>
	 * Retrieve the machine type.
	 * @return    machine type
	 </en>*/
	public int getType()
	{
		return(wType) ;
	}

	//#CM46997
	/**<en>
	 * Sets the machine number.
	 * @param num  machine number
	 </en>*/
	public void setNumber(int num)
	{
		wNumber = num ;
	}

	//#CM46998
	/**<en>
	 * Gets the machine number.
	 * @return    machine number
	 </en>*/
	public int getNumber()
	{
		return(wNumber) ;
	}

	//#CM46999
	/**<en>
	 * Set the machine status. The list of status is defined as a field of this class.
	 * @param stat  :set the status of the machine.
	 </en>*/
	public void setState(int stat)
	{
		wState = stat ;
	}

	//#CM47000
	/**<en>
	 * Retrieve the machine status. The list of the status is defined as a field of this class.
	 * @return    machine status
	 </en>*/
	public int getState()
	{
		return(wState) ;
	}

	//#CM47001
	/**<en>
	 * Sets the machine error codes.
	 * @param errcode  the machine error codes
	 </en>*/
	public void setErrorCode(String errcode)
	{
		wErrCode = errcode ;
	}

	//#CM47002
	/**<en>
	 * Gets the machine error codes.
	 * @return    the machine error codes
	 </en>*/
	public String getErrorCode()
	{
		return(wErrCode) ;
	}

	//#CM47003
	/**<en>
	 * Sets the controller number.
	 * @param gcid  controller number
	 </en>*/
	public void setControllerNumber(int gcid)
	{
		wControllerNumber = gcid ;
	}

	//#CM47004
	/**<en>
	 * Gets the controller number.
	 * @return    controller number
	 </en>*/
	public int getControllerNumber()
	{
		return(wControllerNumber) ;
	}

	//#CM47005
	/**<en>
	 * Check if the machine is active or not.
	 * @return    'true' if it is active
	 </en>*/
	public boolean isRunning()
	{
		switch (wState)
		{
			case STATE_ACTIVE:
			case STATE_STOP :
			case STATE_FAIL :
				return(true) ;
				
			case STATE_DISCONNECT :
			case STATE_OFFLINE :
				break;
		}
		
		return(false);
	}

	//#CM47006
	/**<en>
	 * Checks if the machine is disconnected or not.
	 * @return    'true' if it is disconnected.
	 </en>*/
	public boolean isDisconnected()
	{
		return(wState == STATE_DISCONNECT) ;
	}

	//#CM47007
	/**<en>
	 * Sets the handler in order to store and get instances.
	 * @param hndler entity handler
	 </en>*/
	public void setHandler(ToolEntityHandler hndler)
	{
		wHandler = hndler ;
	}

	//#CM47008
	/**<en>
	 * Getse the handler in order to store and get instances.
	 * @return entity handler
	 </en>*/
	public ToolEntityHandler getHandler()
	{
		return(wHandler) ;
	}

	//#CM47009
	/**<en>
	 * Alter the machine status to discnnected (indefinite) .
	 * Database will be updated by using handler.
	 * @throws InvalidDefineException :Notifies if there are any inconsistent update conditions for the table.
	 * @throws ReadWriteException     :Notifies if any trouble occurred in accessing data.
	 * @throws NotFoundException      :Notifies if this station cannot be found in database.
	 </en>*/
	public void unknown() throws InvalidDefineException, ReadWriteException, NotFoundException
	{
		ToolAs21MachineAlterKey ak = new ToolAs21MachineAlterKey();
		ak.setType(this.getType());
		ak.setNumber(this.getNumber());
		ak.setControllerNumber(this.getControllerNumber());
		ak.updateStatus(STATE_DISCONNECT);
		ak.updateErrorCode(null);
		getHandler().modify(ak);
		setState(STATE_DISCONNECT);
		setErrorCode(null);
	}

	//#CM47010
	/**<en>
	 * Alter the machine status to active. 
	 * Database will be updated by using handler.
	 * @throws InvalidDefineException :Notifies if there are any inconsistent update conditions for the table.
	 * @throws ReadWriteException     :Notifies if any trouble occurred in accessing data.
	 * @throws NotFoundException      :Notifies if this station cannot be found in database.
	 </en>*/
	public void active() throws InvalidDefineException, ReadWriteException, NotFoundException
	{
		ToolAs21MachineAlterKey ak = new ToolAs21MachineAlterKey();
		ak.setType(this.getType());
		ak.setNumber(this.getNumber());
		ak.setControllerNumber(this.getControllerNumber());
		ak.updateStatus(STATE_ACTIVE);
		ak.updateErrorCode(null);
		getHandler().modify(ak);
		setState(STATE_ACTIVE);
		setErrorCode(null);
	}

	//#CM47011
	/**<en>
	 * Alter the machine status to stopped.
	 * Database will be updated by using handler.
	 * @throws InvalidDefineException :Notifies if there are any inconsistent update conditions for the table.
	 * @throws ReadWriteException     :Notifies if any trouble occurred in accessing data.
	 * @throws NotFoundException      :Notifies if this station cannot be found in database.
	 </en>*/
	public void stop() throws InvalidDefineException, ReadWriteException, NotFoundException
	{
		ToolAs21MachineAlterKey ak = new ToolAs21MachineAlterKey();
		ak.setType(this.getType());
		ak.setNumber(this.getNumber());
		ak.setControllerNumber(this.getControllerNumber());
		ak.updateStatus(STATE_STOP);
		ak.updateErrorCode(null);
		getHandler().modify(ak);
		setState(STATE_STOP);
		setErrorCode(null);
	}

	//#CM47012
	/**<en>
	 * Alter the machine status to failure.
	 * Database will be updated by using handler.
	 * @param  errcd :error code
	 * @throws InvalidDefineException :Notifies if there are any inconsistent update conditions for the table.
	 * @throws ReadWriteException     :Notifies if any trouble occurred in accessing data.
	 * @throws NotFoundException      :Notifies if this station cannot be found in database.
	 </en>*/
	public void failure(String errcd) throws InvalidDefineException, ReadWriteException, NotFoundException
	{
		ToolAs21MachineAlterKey ak = new ToolAs21MachineAlterKey();
		ak.setType(this.getType());
		ak.setNumber(this.getNumber());
		ak.setControllerNumber(this.getControllerNumber());
		ak.updateStatus(STATE_FAIL);
		ak.updateErrorCode(errcd);
		getHandler().modify(ak);
		setState(STATE_FAIL);
		setErrorCode(errcd);
	}

	//#CM47013
	/**<en>
	 * Alter the machine status to off-line. 
	 * Database will be updated by using handler.
	 * @throws InvalidDefineException :Notifies if there are any inconsistent update conditions for the table.
	 * @throws ReadWriteException     :Notifies if any trouble occurred in accessing data.
	 * @throws NotFoundException      :Notifies if this station cannot be found in database.
	 </en>*/
	public void offline() throws InvalidDefineException, ReadWriteException, NotFoundException
	{
		ToolAs21MachineAlterKey ak = new ToolAs21MachineAlterKey();
		ak.setType(this.getType());
		ak.setNumber(this.getNumber());
		ak.setControllerNumber(this.getControllerNumber());
		ak.updateStatus(STATE_OFFLINE);
		ak.updateErrorCode(null);
		getHandler().modify(ak);
		setState(STATE_OFFLINE);
		setErrorCode(null);
	}

	//#CM47014
	// Package methods -----------------------------------------------

	//#CM47015
	// Protected methods ---------------------------------------------

	//#CM47016
	// Private methods -----------------------------------------------

}
//#CM47017
//end of class

