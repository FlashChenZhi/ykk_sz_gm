// $Id: As21MachineState.java,v 1.2 2006/10/26 01:11:42 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30396
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.equipment.MachineState;
import jp.co.daifuku.wms.base.common.EntityHandler;

//#CM30397
/**
 * This class controls the machine states according to AS21 protocol.
 * It controls the status of machines themselves, errorcodes and conceptual availability such as off-line.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:11:42 $
 * @author  $Author: suresh $
 */
public class As21MachineState extends MachineState
{
	//#CM30398
	// Class fields -----------------------------------------------
	//#CM30399
	/**
	 * Filed for protocol type (AS21 protocol)
	 */
	public static final String PROTOCOL_AS21 = "AS21" ;

	//#CM30400
	/**
	 * Field for the machine state (disconnected)
	 */
	public static final int STATE_DISCONNECT = -1 ;
	
	//#CM30401
	/**
	 * Field for the machine state (active)
	 */
	public static final int STATE_ACTIVE = 0 ;
	
	//#CM30402
	/**
	 * Field for the machine state (stopped)
	 */
	public static final int STATE_STOP = 1 ;
	
	//#CM30403
	/**
	 * Field for the machine state (failed)
	 */
	public static final int STATE_FAIL = 2 ;
	
	//#CM30404
	/**
	 * Field for the machine state (off-line)
	 */
	public static final int STATE_OFFLINE = 3 ;

	//#CM30405
	// Class variables -----------------------------------------------
	//#CM30406
	/**
	 * station No.
	 */
	protected String wStationNumber = null;
	
	//#CM30407
	/**
	 * Machine type
	 */
	protected int wType = 0;
	
	//#CM30408
	/**
	 * Number of machine (number)
	 */
	protected int wNumber = 0;
	
	//#CM30409
	/**
	 * State of machine
	 */
	protected int wState = 0;
	
	//#CM30410
	/**
	 * Error code
	 */
	protected String wErrCode = null;
	
	//#CM30411
	/**
	 * Controller No.
	 */
	protected int wControllerNumber = 0;
	
	//#CM30412
	/**
	 * Instance handler
	 */
	protected EntityHandler wHandler = null ;

	//#CM30413
	// Class method --------------------------------------------------
    //#CM30414
    /**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:11:42 $") ;
	}
	
	//#CM30415
	// Constructors --------------------------------------------------
	//#CM30416
	/**
	 * Generates the instance of <code>As21MachineState</code> according to machine type, controller No. and 
	 * the machine number.<BR>
	 * @param  type type of <code>Machine</code> to create
	 * @param  number machine number of <code>Machine</code> to create
	 * @param  cnt controller number of <code>Machine</code> to create
	 */
	public As21MachineState(int type, int num, int cnt)
	{
		setType(type) ;
		setNumber(num) ;
		setControllerNumber(cnt);
	}
	
	//#CM30417
	/**
	 * Generates the instance of <code>As21MachineState</code> according to machine type and machine number.<BR>
	 * @param  type type of <code>MachineState</code> to create
	 * @param  num machine number of <code>MachineState</code> to create
	 */
	public As21MachineState(int type, int num)
	{
		setType(type) ;
		setNumber(num) ;
	}

	//#CM30418
	// Public methods ------------------------------------------------
	//#CM30419
	/**
	 * Sets value of station No.
	 * @param stno station No. to set
	 */
	public void setStationNumber(String stno)
	{
		wStationNumber = stno ;
	}

	//#CM30420
	/**
	 * Gets station No.
	 * @return  station No.
	 */
	public String getStationNumber()
	{
		return wStationNumber ;
	}

	//#CM30421
	/**
	 * Sets the type of machine.
	 * @param type  type of machine
	 */
	public void setType(int type)
	{
		wType = type ;
	}

	//#CM30422
	/**
	 * Gets type of machine.
	 * @return    type of machine
	 */
	public int getType()
	{
		return(wType) ;
	}

	//#CM30423
	/**
	 * Sets the machine number.
	 * @param num  machine number
	 */
	public void setNumber(int num)
	{
		wNumber = num ;
	}

	//#CM30424
	/**
	 * Gets the machine number.
	 * @return    machine number
	 */
	public int getNumber()
	{
		return(wNumber) ;
	}

	//#CM30425
	/**
	 * Sets the state of machine. List of states is defined as fields of this class.
	 * @param stat  machine state
	 */
	public void setState(int stat)
	{
		wState = stat ;
	}

	//#CM30426
	/**
	 * Gets the state of machine. List of states is defined as fields of this class.
	 * @return    state of machine
	 */
	public int getState()
	{
		return(wState) ;
	}

	//#CM30427
	/**
	 * Sets the machine error codes.
	 * @param errcode  the machine error codes
	 */
	public void setErrorCode(String errcode)
	{
		wErrCode = errcode ;
	}

	//#CM30428
	/**
	 * Gets the machine error codes.
	 * @return    the machine error codes
	 */
	public String getErrorCode()
	{
		return(wErrCode) ;
	}

	//#CM30429
	/**
	 * Sets the controller number.
	 * @param gcid  controller number
	 */
	public void setControllerNumber(int gcid)
	{
		wControllerNumber = gcid ;
	}

	//#CM30430
	/**
	 * Gets the controller number.
	 * @return    controller number
	 */
	public int getControllerNumber()
	{
		return(wControllerNumber) ;
	}

	//#CM30431
	/**
	 * Check if the machine is active or not.
	 * @return    'true' if it is active
	 */
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

	//#CM30432
	/**
	 * Checks if the machine is disconnected or not.
	 * @return    'true' if it is disconnected.
	 */
	public boolean isDisconnected()
	{
		return(wState == STATE_DISCONNECT) ;
	}

	//#CM30433
	/**
	 * Sets the handler in order to store and get instances.
	 * @param hndler entity handler
	 */
	public void setHandler(EntityHandler hndler)
	{
		wHandler = hndler ;
	}

	//#CM30434
	/**
	 * Getse the handler in order to store and get instances.
	 * @return entity handler
	 */
	public EntityHandler getHandler()
	{
		return(wHandler);
	}

	//#CM30435
	// Package methods -----------------------------------------------

	//#CM30436
	// Protected methods ---------------------------------------------

	//#CM30437
	// Private methods -----------------------------------------------

}
//#CM30438
//end of class

