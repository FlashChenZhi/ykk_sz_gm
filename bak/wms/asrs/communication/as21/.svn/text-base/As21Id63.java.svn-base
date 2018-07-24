// $Id: As21Id63.java,v 1.2 2006/10/26 01:17:29 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30166
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM30167
/**
 * Processes the communication message "WorkModeChangeCompletionReport" ID=63 according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:17:29 $
 * @author  $Author: suresh $
 */
public class As21Id63 extends ReceiveIdMessage
{
	//#CM30168
	// Class fields --------------------------------------------------

	//#CM30169
	// Comment for field
	//#CM30170
	/**
	 *Length of ID63 (excluding STX, SEQ-No, BCC and ETX)
	 */
	public static final int LEN_ID63 = 21 ;

	//#CM30171
	/**
	 *Defines offset of the station No. of ID63.
	 */
	private static final int OFF_ID63_STATION = 0 ;
	//#CM30172
	/**
	 *Length of station No. of ID63 (byte)
	 */
	private static final int LEN_ID63_STATION = 4 ;

	//#CM30173
	/**
	 *Defines offset of completion mode of ID63.
	 */
	private static final int OFF_ID63_MODE = OFF_ID63_STATION + LEN_ID63_STATION ;
	//#CM30174
	/**
	 *Length of completion mode of ID63 (byte)
	 */
	private static final int LEN_ID63_MODE = 1 ;

	//#CM30175
	// Class variables -----------------------------------------------
	//#CM30176
	/**
	 * Variables for the preservation of communication messages
	 */
	private byte wLocalBuffer[] = new byte[LEN_ID63] ;

	//#CM30177
	// Class method --------------------------------------------------
	//#CM30178
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:17:29 $") ;
	}

	//#CM30179
	// Constructors --------------------------------------------------
	//#CM30180
	/**
	 * Sets the communication message received from AGC; then initializes this class.
	 * @param <code>as21Id63</code>  the communication message "WorkModeChangeCompletionReport"
	 */
	public As21Id63(byte[] as21Id63)
	{
		super() ;
		setReceiveMessage(as21Id63) ;
	}

	//#CM30181
	// Public methods ------------------------------------------------
	//#CM30182
	/**
	 * Acquires the station No. from the report of completion of work mode change.
	 * @return    the station No. the work mode change completed.
	 */
	public String getStationNo()
	{
		String stationNo = getContent().substring(OFF_ID63_STATION, OFF_ID63_STATION + LEN_ID63_STATION);
		return (stationNo);
	}

	//#CM30183
	/**
	 * Acquires the completion mode from the report of completion of work mode change.
	 * @return    	Completion Mode<BR>
	 * 				1:Storage<BR>
	 *				2:Retrieval
	 * @throws InvalidProtocolException : Reports if numeric value was not provided for completion mode.
	 */
	public int getCompletionMode() throws InvalidProtocolException
	{

		int rclass;
		String completionMode = getContent().substring(OFF_ID63_MODE, OFF_ID63_MODE + LEN_ID63_MODE) ;
		try
		{
			rclass =  Integer.parseInt(completionMode) ;
		}
		catch (Exception e)
		{
			throw (new InvalidProtocolException("Invalid Response:" + completionMode)) ;
		}
		return (rclass);
	
	}
	
	//#CM30184
	/**
	 * Acquires the contents of the communication message "WorkModeChangeCompletionReport".
	 * @return contents of the communication message "WorkModeChangeCompletionReport"
	 */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM30185
	// Package methods -----------------------------------------------

	//#CM30186
	// Protected methods ---------------------------------------------
	//#CM30187
	/**
	 * Sets communication message received from AGC to the internal buffer.
	 * @param rmsg the communication message "WorkModeChangeCompletionReport"
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM30188
		// Sets data to communication message buffer
		for (int i=0; i < LEN_ID63; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}

	//#CM30189
	// Private methods -----------------------------------------------

}
//#CM30190
// end of class

