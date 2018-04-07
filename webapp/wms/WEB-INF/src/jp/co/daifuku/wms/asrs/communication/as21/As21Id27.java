// $Id: As21Id27.java,v 1.2 2006/10/26 01:35:10 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29491
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM29492
/**
 * Processes communication messages "Request for receiving station change, ID=27" according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:35:10 $
 * @author  $Author: suresh $
 */
public class As21Id27 extends ReceiveIdMessage
{
	//#CM29493
	// Class fields --------------------------------------------------
	//#CM29494
	/**
	 * Length of communicarion message ID27 (excluding STX, SEQ-No, BCC and ETX).
	 * <p>
	 * <table>
	 * <tr><td>ID:</td><td>2 byte</td></tr>
	 * <tr><td>ID segment:</td><td>2 byte</td></tr>
	 * <tr><td>MC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>MC Key:</td><td>8 byte</td></tr>
	 * <tr><td>location No.:</td><td>12 byte</td></tr>
	 * <tr><td>station No.:</td><td>4 byte</td></tr>
	 * <tr><td>AGC Data:</td><td>6 byte</td></tr>
	 * </table>
	 * </p>
	 */
	public static final int LEN_ID27 = 46 ;

	//#CM29495
	/**
	 * Defines offset of MC Key.
	 */
	private static final int OFF_ID27_MCKEY = 0 ;

	//#CM29496
	/**
	 * Length of MC Key (in byte)
	 */
	private static final int LEN_ID27_MCKEY = 8 ;

	//#CM29497
	/**
	 * Defines offset of location No.
	 */
	private static final int OFF_ID27_LOCATION = OFF_ID27_MCKEY + LEN_ID27_MCKEY ;

	//#CM29498
	/**
	 * Length of location No. (in byte)
	 */
	private static final int LEN_ID27_LOCATION = 12 ;

	//#CM29499
	/**
	 * Defines offset of station No.
	 */
	private static final int OFF_ID27_STATION = OFF_ID27_LOCATION + LEN_ID27_LOCATION ;

	//#CM29500
	/**
	 * Length of station No. (in byte)
	 */
	private static final int LEN_ID27_STATION = 4 ;

	//#CM29501
	/**
	 * Defines offset of AGC DATA
	 */
	private static final int OFF_ID27_AGC_DATA = OFF_ID27_STATION + LEN_ID27_STATION ;

	//#CM29502
	/**
	 * Length of AGC DATA, in byte
	 */
	private static final int LEN_ID27_AGC_DATA = 6 ;

	//#CM29503
	// Class variables -----------------------------------------------
	//#CM29504
	/**
	 * communication message
	 */
	private byte[] wLocalBuffer = new byte[LEN_ID27] ;

	//#CM29505
	// Class method --------------------------------------------------
	//#CM29506
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:35:10 $") ;
	}

	//#CM29507
	// Constructors --------------------------------------------------
	//#CM29508
	/**
	 * Default constructor
	 */
	public As21Id27()
	{
		super() ;
	}

	//#CM29509
	/**
	 * Passes the message received from AGC to the constructor.
	 * @param <code>as21Id27</code> communiation message "Request for receiving station change" ID=27 
	 */
	public As21Id27(byte[] as21Id27)
	{
		super() ;
		setReceiveMessage(as21Id27) ;
	}

	//#CM29510
	// Public methods ------------------------------------------------
	//#CM29511
	/**
	 * Acquires MC Key from communication message Request for receiving station change.
	 * @return    MC Key
	 */
	public String getMcKey ()
	{
		String mcKey = getContent().substring(OFF_ID27_MCKEY, OFF_ID27_MCKEY + LEN_ID27_MCKEY) ;
		return (mcKey);
	}

	//#CM29512
	/**
	 * Acquires location No. from communication message Request for receiving station change.
	 * @return    Location No.
	 */
	public String getLocationNumber()
	{
		String locationNo = getContent().substring(OFF_ID27_LOCATION, OFF_ID27_LOCATION + LEN_ID27_LOCATION) ;
		return (locationNo);
	}

	//#CM29513
	/**
	 * Acquires station No. from communication message Request for receiving station change.
	 * @return    Station No.
	 */
	public String getStationNumber ()
	{
		String stationNo = getContent().substring(OFF_ID27_STATION, OFF_ID27_STATION + LEN_ID27_STATION) ;
		return (stationNo);
	}

	//#CM29514
	/**
	 * Acquires AGC Data from the message, Request for receiving station change.
	 * @return    AGC Data
	 */
	public String getAgcData ()
	{
		String agcData = getContent().substring(OFF_ID27_AGC_DATA, OFF_ID27_AGC_DATA + LEN_ID27_AGC_DATA) ;
		return (agcData);
	}

	//#CM29515
	/**
	 * Returns the string representaion of conmmunication message Requesting the receiving station change.
	 * @return	  onmmunication message Requesting the receiving station change.
	 */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM29516
	// Package methods -----------------------------------------------

	//#CM29517
	// Protected methods ---------------------------------------------
	//#CM29518
	/**
	 * Sets communication message received from AGC to the internal buffer.
	 * @param rmsg communication message received from AGC 
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM29519
		// Sets data to communication message buffer
		for (int i=0; i < LEN_ID27; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}

	//#CM29520
	// Private methods -----------------------------------------------

}
//#CM29521
//end of class

