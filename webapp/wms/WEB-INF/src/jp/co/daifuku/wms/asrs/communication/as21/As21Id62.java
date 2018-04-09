// $Id: As21Id62.java,v 1.2 2006/10/26 01:18:07 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30137
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM30138
/**
 * Processes the communication message "WorkModeChangeCommandResponse" ID=62, response to the command to 
 * change the work mode, according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:18:07 $
 * @author  $Author: suresh $
 */
public class As21Id62 extends ReceiveIdMessage
{
	//#CM30139
	// Class fields --------------------------------------------------
	//#CM30140
	/**
	 *Length of communication message ID62 (excluding STX, SEQ-No, BCC and ETX)
	 */
	public static final int LEN_ID62 = 22 ;

	//#CM30141
	/**
	 *Defines offset of station No. of ID62
	 */
	private static final int OFF_ID62_STATION = 0 ;

	//#CM30142
	/**
	 *Length of station No. of ID62 (byte)
	 */
	private static final int LEN_ID62_STATION = 4 ;

	//#CM30143
	/**
	 *Defines offset of resopnse classification of ID62
	 */
	private static final int OFF_ID62_REQUEST = OFF_ID62_STATION + LEN_ID62_STATION ;

    //#CM30144
    /**
	 *Length of response classification of ID62 (byte)
	 */
	private static final int LEN_ID62_REQUEST = 2 ;

	//#CM30145
	/**
	 *Defines response classification for "received as normal status"
	 */
	public static final String NORMAL = "00" ;

	//#CM30146
	/**
	 *Defines response classification for "error (mode is being changed)"
	 */
	public static final String MODE_CHANGE = "01" ;

	//#CM30147
	/**
	 *Defines response classification for "error (station No. error)
	 */
	public static final String STATION_ERROR = "02" ;

	//#CM30148
	/**
	 *Defines response classification for "error (instruction mode)"
	 */
	public static final String MISION_MODE = "03" ;

	//#CM30149
	/**
	 *Defines response classification for "error (carrying data exists)"
	 */
	public static final String H_Mode = "04" ;

	//#CM30150
	// Class variables -----------------------------------------------
	//#CM30151
	/**
	 * Variable for the preservation of communication messages
	 */
	private byte wLocalBuffer[] = new byte[LEN_ID62] ;

	//#CM30152
	// Class method --------------------------------------------------
	//#CM30153
	/** Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:18:07 $") ;
	}

	//#CM30154
	// Constructors --------------------------------------------------
	//#CM30155
	/**
	 * Sets the communication message received from AGC; then initializes this class.
	 * @param <code>as21Id62</code>  the communication message "WorkModeChangeCommandResponse"
	 */
	public As21Id62(byte[] as21Id62)
	{
		super() ;
		setReceiveMessage(as21Id62) ;
	}

	//#CM30156
	// Public methods ------------------------------------------------
	//#CM30157
	/**
	 * Acquires the station No. from the reponse to WorkModeChangeCommand.
	 * @return    Station No.
	 */
	public String getStationNo ()
	{
		String stationNo = getContent().substring(OFF_ID62_STATION, OFF_ID62_STATION + LEN_ID62_STATION);
		return (stationNo);
	}

	//#CM30158
	/**
	 * Acquires response classification from "WorkModeChangeCommandResponse".
	 * @return		Response classification<BR>
	 * 		    	00:Received as normal status<BR>
	 *				01:Error(Mode is being changed)<BR>
	 *				02:Error(Station No. Error)<BR>
	 *				03:Error(Instruction Mode)<BR>
	 *				04:Error(Carrying data exists)
	 */
	public String getResponseClassification()
	{
		String responseClassification = getContent().substring(OFF_ID62_REQUEST, OFF_ID62_REQUEST + LEN_ID62_REQUEST) ;

		return (responseClassification);
	}

	//#CM30159
	/**
	 * Acquires the contents of the communication message "WorkModeChangeCommandResponse".
	 * @return  contents of he communication message "WorkModeChangeCommandResponse"
	 */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM30160
	// Package methods -----------------------------------------------

	//#CM30161
	// Protected methods ---------------------------------------------
	//#CM30162
	/**
	 * Sets communication message received from AGC to the internal buffer.
	 * @param rmsg the communication message "WorkModeChangeCommandResponse"
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM30163
		// Sets data to communication message buffer
		for (int i=0; i < LEN_ID62; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}

	//#CM30164
	// Private methods -----------------------------------------------

}
//#CM30165
//end of class

