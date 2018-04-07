// $Id: As21Id70.java,v 1.2 2006/10/26 01:14:32 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30273
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM30274
/**
 * Processes the communication message "Message Data" ID=70 according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:14:32 $
 * @author  $Author: suresh $
 */
public class As21Id70 extends ReceiveIdMessage
{
	//#CM30275
	// Class fields --------------------------------------------------
	//#CM30276
	/**
	 * Length of communication message in initial setting(byte)
	 */
	public int LEN_ID70 = 0;

	//#CM30277
	/**
	 * Defines theoffset of Message Data
	 */
	private static final int OFF_ID70_MESSAGE_DATA = 0;

	//#CM30278
	// Class variables -----------------------------------------------
	//#CM30279
	/**
	 * Variable for the reservation of communication messages
	 */
	public byte[] wLocalBuffer;

	//#CM30280
	// Class method --------------------------------------------------
	//#CM30281
	/**
	 * Returns the version of this class.
	 * return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/10/26 01:14:32 $");
	}

	//#CM30282
	// Constructors --------------------------------------------------
	//#CM30283
	/**
	 * Sets the communication message received from AGC; then implements the initialization
	 * of thisclass.
	 * @param <code>as21Id70</code>  the communication message "Message Data"
	 */
	public As21Id70(byte[] as21Id70)
	{
		super();
		setReceiveMessage(as21Id70);
	}

	//#CM30284
	// Public methods ------------------------------------------------
	//#CM30285
	/**
	 * Acquires the Message Data from the communication message "Message Data". Contents are
	 * defined by each system.
	 * @return    	Message Data
	 */
	public String getMessageData()
	{
		int  messageLength = getContent().length();
		String messageData = getContent().substring(OFF_ID70_MESSAGE_DATA, messageLength);
		return (messageData);
	}

	//#CM30286
	/**
	 * Acquires the content of communication message "Message Data".
	 * @return  the content of communication message "Message Data"
	 */
	public String toString()
	{
		return (new String(wLocalBuffer));
	}

	//#CM30287
	// Package methods -----------------------------------------------

	//#CM30288
	// Protected methods ---------------------------------------------
	//#CM30289
	/**
	 * Sets communication message received from AGC to the internal buffer.
	 * @param rmsg communication message "Message Data"
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM30290
		// Length of the communication message received 
		LEN_ID70 = rmsg.length;

		//#CM30291
		// Sets data to communication message buffer
		wLocalBuffer = new byte[LEN_ID70];
		for(int i = 0; i < LEN_ID70; i++)
		{
			wLocalBuffer[i] = rmsg[i];
		}
		wDataBuffer = wLocalBuffer;
	}

	//#CM30292
	// Private methods -----------------------------------------------

}
//#CM30293
//end of class

