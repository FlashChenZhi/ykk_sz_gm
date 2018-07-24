// $Id: As21Id66.java,v 1.2 2006/10/26 01:16:37 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30223
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM30224
/**
 * Processes the communication message "RetrievalTrigger" ID=66 according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:16:37 $
 * @author  $Author: suresh $
 */
public class As21Id66 extends ReceiveIdMessage
{
	//#CM30225
	// Class fields --------------------------------------------------
	//#CM30226
	/**
	 * Length of communication message ID66 (STX, SEQ-No, BCC and ETX)
	 * <p>
	 * <table>
	 * <tr><td>ID:</td><td>2 byte</td></tr>
	 * <tr><td>ID segment:</td><td>2 byte</td></tr>
	 * <tr><td>MC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>Station NO.:</td><td>4 byte</td></tr>
	 * </table>
	 * </p>
	 */

	//#CM30227
	/**
	 * Length of communication message ID66 (excluding STX, SEQ-No, BCC and ETX)
	 */
	public static final int LEN_ID66 = 20 ;
	
	//#CM30228
	/**
	 * Defines the offset of Station No. of ID66.
	 */
	private static final int OFF_ID66_STATION = 0 ;

	//#CM30229
	/**
	 * Length of Station No. of ID66 (byte)
	 */
	private static final int LEN_ID66_STATION = 4 ;

	//#CM30230
	// Class variables -----------------------------------------------
	//#CM30231
	/**
	 * Variables for the preservation of communication messages
	 */
	private byte[] wLocalBuffer = new byte[LEN_ID66] ;

	//#CM30232
	// Class method --------------------------------------------------
	//#CM30233
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Data: 2001/05/11 02:33:53 $");
	}
	
	//#CM30234
	// Constructors --------------------------------------------------
	//#CM30235
	/**
	 * Sets the communication message received from AGC; then initializes this class.
	 * @param <code>as21Id66</code>  communication message "Retrieval Trigger"
	 */
	public As21Id66(byte[] as21Id66)
	{
		super() ;
		setReceiveMessage(as21Id66) ;
	}

	//#CM30236
	// Public methods ------------------------------------------------
	//#CM30237
	/**
	 * Acquires station No., which requests the retrieval, from the communication message "Retrieval Trigger".
	 * @return    	Retrieval station No.
	 */
	public String getStationNo()
	{
		String stationNo = getContent().substring(OFF_ID66_STATION, OFF_ID66_STATION + LEN_ID66_STATION) ;
		return (stationNo);
	}
	
	//#CM30238
	/**
	 * Acquires the contents of communication message "Retrieval Trigger".
	 * @return contents of communication message "Retrieval Trigger"
	 */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM30239
	// Package methods -----------------------------------------------
	
	//#CM30240
	// Protected methods ---------------------------------------------
	//#CM30241
	/**
	 * Sets communication message received from AGC to the internal buffer.
	 * @param rmsg communication message "Retrieval Trigger"
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM30242
		// Sets data to communication message buffer
		for (int i = 0; i < LEN_ID66; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}

	//#CM30243
	// Private methods -----------------------------------------------

}
//#CM30244
//end of class

