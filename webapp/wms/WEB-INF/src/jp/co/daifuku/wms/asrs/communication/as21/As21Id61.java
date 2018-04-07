// $Id: As21Id61.java,v 1.2 2006/10/26 01:28:40 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30112
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM30113
/**
 * Processes the communication message "WorkModeChangeRequest" ID=61 according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:28:40 $
 * @author  $Author: suresh $
 */
public class As21Id61 extends ReceiveIdMessage
{
	//#CM30114
	// Class fields --------------------------------------------------
	//#CM30115
	// Comment for field
	//#CM30116
	/**
	 *Length of ID61 (excluding STX, SEQ-No, BCC and ETX)
	 */
	public static final int LEN_ID61 = 21 ;

	//#CM30117
	/**
	 *Defines offset of station No. of ID61
	 */
	private static final int OFF_ID61_STATION = 0 ;

	//#CM30118
	/**
	 *Length of station No. of ID61(byte)
	 */
	private static final int LEN_ID61_STATION = 4 ;

	//#CM30119
	/**
	 *Defines offset of request classificaiton of ID61
	 */
	private static final int OFF_ID61_REQUEST = OFF_ID61_STATION + LEN_ID61_STATION ;

    //#CM30120
    /**
	 *Length of request classificaton of ID61 (byte)
	 */
	private static final int LEN_ID61_REQUEST = 1 ;

	//#CM30121
	// Class variables -----------------------------------------------
	//#CM30122
	/**
	 * Variable for the preservation of communication messages.
	 */
	private byte wLocalBuffer[] = new byte[LEN_ID61] ;

	//#CM30123
	// Class method --------------------------------------------------
	//#CM30124
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:28:40 $") ;
	}

	//#CM30125
	// Constructors --------------------------------------------------
	//#CM30126
	/**
	 * Sets the communicatin message received from AGC, then initializes this class.
	 * @param <code>as21Id61</code>  the communicatin message WorkModeChangeRequest
	 */
	public As21Id61(byte[] as21Id61)
	{
		super() ;
		setReceiveMessage(as21Id61) ;
	}

	//#CM30127
	// Public methods ------------------------------------------------
	//#CM30128
	/**
	 * Acquires the station No. from WorkModeChangeRequest.
	 * @return    Station No.
	 */
	public String getStationNo ()
	{
		String stationNo = getContent().substring(OFF_ID61_STATION, OFF_ID61_STATION + LEN_ID61_STATION);
		return (stationNo);
	}

	//#CM30129
	/**
	 * Acquires the request classification from "WorkModeChangeRequest".
	 * @return    	request classification<BR>
	 * 				1:storage<BR>
	 *				2:retrieval<BR>
	 *				3:Cancel of storage request<BR>
	 *				4:Cancel of retrieval request
	 * @throws InvalidProtocolException : Reports if numeric value was not provided for request classification.
	 */
	public int getRequestClassification () throws InvalidProtocolException
	{

		int rclass;
		String requestClassification = getContent().substring(OFF_ID61_REQUEST, OFF_ID61_REQUEST + LEN_ID61_REQUEST) ;
		try
		{
			rclass =  Integer.parseInt(requestClassification) ;
		}
		catch (Exception e)
		{
			throw (new InvalidProtocolException("Invalid Response:" + requestClassification)) ;
		}
		return (rclass);
	
	}

	//#CM30130
	/**
	 * Acquires the content of the communication message "WorkModeChangeRequest".
	 * @return content of the communication message "WorkModeChangeRequest"
	 */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM30131
	// Package methods -----------------------------------------------

	//#CM30132
	// Protected methods ---------------------------------------------
	//#CM30133
	/**
	 * Sets the communication message received from AGC to the internal buffer.
	 * @param rmsg the communication message "WorkModeChangeRequest"
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM30134
		// Sets data to communication message buffer
		for (int i=0; i < LEN_ID61; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}

	//#CM30135
	// Private methods -----------------------------------------------

}
//#CM30136
//end of class
