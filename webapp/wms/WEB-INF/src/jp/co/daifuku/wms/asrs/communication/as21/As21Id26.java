// $Id: As21Id26.java,v 1.2 2006/10/26 01:35:10 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29453
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM29454
/**
 * Processes communication message "Arrival Report, ID=26" acording to AS21 communication protocol.
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
public class As21Id26 extends ReceiveIdMessage
{
	//#CM29455
	// Class fields --------------------------------------------------
	//#CM29456
	/**
	 * Length of message ID26 (excluding STX, SEQ-No, BCC and ETX)
	 * <p>
	 * <table>
	 * <tr><td>ID:</td><td>2 byte</td></tr>
	 * <tr><td>ID segment:</td><td>2 byte</td></tr>
	 * <tr><td>MC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>MC Key:</td><td>8 byte</td></tr>
	 * <tr><td>station number:</td><td>4 byte</td></tr>
	 * <tr><td>Load size:</td><td>2 byte</td></tr>
	 * <tr><td>Load presence:</td><td>1 byte</td></tr>
	 * <tr><td>BC Data:</td><td>30 byte</td></tr>
	 * <tr><td>control information:</td><td>30 byte</td></tr>
	 * </table>
	 * </p>
	 */
	public static final int LEN_ID26 = 91 ;
	
	//#CM29457
	/**
	 * Defines offset of MC Key
	 */
	private static final int OFF_ID26_MCKEY = 0 ;

	//#CM29458
	/**
	 * Length of MC Key
	 */
	private static final int LEN_ID26_MCKEY = 8 ;

	//#CM29459
	/**
	 * Defines offset of station
	 */
	private static final int OFF_ID26_STATION = OFF_ID26_MCKEY + LEN_ID26_MCKEY ;

	//#CM29460
	/**
	 * Length of station
	 */
	private static final int LEN_ID26_STATION = 4 ;

	//#CM29461
	/**
	 * Defines offset of load size
	 */
	private static final int OFF_ID26_DIM_INFO = OFF_ID26_STATION + LEN_ID26_STATION ;

	//#CM29462
	/**
	 * Length of load size
	 */
	private static final int LEN_ID26_DIM_INFO = 2 ;

	//#CM29463
	/**
	 * Defines offset of load presence
	 */
	private static final int OFF_ID26_PRESENCE_INFO = OFF_ID26_DIM_INFO + LEN_ID26_DIM_INFO ;

	//#CM29464
	/**
	 * Length of load presence
	 */
	private static final int LEN_ID26_PRESENCE_INFO = 1 ;

	//#CM29465
	/**
	 * Defines offset of BC Data
	 */
	private static final int OFF_ID26_BC_DATA = OFF_ID26_PRESENCE_INFO + LEN_ID26_PRESENCE_INFO ;

	//#CM29466
	/**
	 * Length of BC Data
	 */
	private static final int LEN_ID26_BC_DATA = 30 ;

	//#CM29467
	/**
	 * Defines offset of control information
	 */
	private static final int OFF_ID26_CONTROL_INFO = OFF_ID26_BC_DATA + LEN_ID26_BC_DATA ;

	//#CM29468
	/**
	 * Length of control information
	 */
	private static final int LEN_ID26_CONTROL_INFO = 30 ;

	//#CM29469
	/**
	 * Field to indicate the presence of load.
	 */
	public static final String C_PRESENCE = "1" ;
	
	//#CM29470
	// Class variables -----------------------------------------------
	//#CM29471
	/**
	 * Communication message buffer
	 */
	private byte[] wLocalBuffer = new byte[LEN_ID26] ;

	//#CM29472
	// Class method --------------------------------------------------
     //#CM29473
     /**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:35:10 $") ;
	}
	//#CM29474
	// Constructors --------------------------------------------------
	//#CM29475
	/**
	 * Default constructor
	 */
	public As21Id26 ()
	{
		super();
	}

	//#CM29476
	/**
	 * Passes communication message received from AGC to the constructor.
	 * @param <code>as21Id26</code>  communication message "Arrival Report ID=26"
	 */
	public As21Id26(byte[] as21Id26)
	{
		super() ;
		setReceiveMessage(as21Id26) ;
	}

	//#CM29477
	// Public methods ------------------------------------------------
	//#CM29478
	/**
	 * Acquires MC Key from communication message Arrival Report.
	 * @return    MC Key
	 */
	public String getMcKey()
	{
		String mcKey = getContent().substring(OFF_ID26_MCKEY, OFF_ID26_MCKEY + LEN_ID26_MCKEY) ;
		return (mcKey);
	}

	//#CM29479
	/**
	 * Acquires station number from communication message Arrival Report.
	 * @return    Station No.
	 */
	public String getStationNumber()
	{
		String stationNo = getContent().substring(OFF_ID26_STATION, OFF_ID26_STATION + LEN_ID26_STATION) ;
		return (stationNo);
	}

	//#CM29480
	/**
	 * Acquires load size from communication message communication message Arrival Report.
	 * @return   detected results for load size
	 */
	public String getDimensionInformation()
	{
		String dimensionInformation = getContent().substring(OFF_ID26_DIM_INFO, OFF_ID26_DIM_INFO + LEN_ID26_DIM_INFO) ;
		return (dimensionInformation);
	}

	//#CM29481
	/**
	 * Acquires load presence from communication message Arrival Report.
	 * @return    Load presence to convey will be stored. True: no load presence False: there are load presence
	 */
	public boolean getLoad ()
	{
		String wp = getContent().substring(OFF_ID26_PRESENCE_INFO, OFF_ID26_PRESENCE_INFO + LEN_ID26_PRESENCE_INFO) ;
		return (wp.equals(C_PRESENCE)) ;
	}

	//#CM29482
	/**
	 * Acquires BC data from the communication message ArrivalReport.
	 * @return    BC Data (Bar code data)
	 */
	public String getBcData ()
	{
		String bcData = getContent().substring(OFF_ID26_BC_DATA, OFF_ID26_BC_DATA + LEN_ID26_BC_DATA) ;
		return (bcData);
	}

	//#CM29483
	/**
	 * Acquires control information from message"ArrivalReport".
	 * @return    control information
	 */
	public String getControlInformation()
	{
		String controlInformation = getContent().substring(OFF_ID26_CONTROL_INFO, OFF_ID26_CONTROL_INFO + LEN_ID26_CONTROL_INFO) ;
		return (controlInformation);
	}

	//#CM29484
	/**
	 * Acquires the contents of communication message "Arrival Report".
	 * @return  text content of retrieval instruction response
	 */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM29485
	// Package methods -----------------------------------------------

	//#CM29486
	// Protected methods ---------------------------------------------

	//#CM29487
	// Private methods -----------------------------------------------
	//#CM29488
	/**
	 * Sets communication message received from AGC to the internal buffer.
	 * @param rmsg message received from AGC 
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM29489
		// Sets data to communication message buffer
		for (int i=0; i < LEN_ID26; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}

}
//#CM29490
//end of class

