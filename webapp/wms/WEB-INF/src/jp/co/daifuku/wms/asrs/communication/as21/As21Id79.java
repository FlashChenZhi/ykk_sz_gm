// $Id: As21Id79.java,v 1.2 2006/10/26 01:12:03 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30372
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM30373
/**
 * Processes the communication message "SystemRecoveryCompletionResponse" ID=79 according to 
 * AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:12:03 $
 * @author  $Author: suresh $
 */
public class As21Id79 extends ReceiveIdMessage
{
	//#CM30374
	// Class fields --------------------------------------------------
	//#CM30375
	/**
	 * Length of communication message ID79 (excluding STX, SEQ-No, BCC and ETX)
	 * <p>
	 * <table>
	 * <tr><td>ID:</td><td>2 byte</td></tr>
	 * <tr><td>ID segment:</td><td>2 byte</td></tr>
	 * <tr><td>MC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>response classification:</td><td>2 byte</td></tr>
	 * </table>
	 * </p>
	 */

	//#CM30376
	/**
	 * Length of the ccommunication message ID79 (excluding STX, SEQ-No, BCC and ETX)
	 */
	public static final int LEN_ID79 = 18 ;

	//#CM30377
	/**
	 * Defines the offset for response classification of ID79
	 */
	private static final int OFF_ID79_CLASS = 0;
	//#CM30378
	/**
	 * Length of response classification ID79 (byte)
	 */
	private static final int LEN_ID79_CLASS = 2 ;
	
	//#CM30379
	/**
	 * "Received in Normal status" in response classification
	 */
	public static final String NORMAL_RECEIVE="00";
	//#CM30380
	// Class variables -----------------------------------------------
	//#CM30381
	/**
	 * Variable for the preservation of communication messages
	 */
	private byte[] wLocalBuffer = new byte[LEN_ID79] ;

	//#CM30382
	// Class method --------------------------------------------------
	//#CM30383
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Data: 2001/05/11 02:33:53 $");
	}
	
	//#CM30384
	// Constructors --------------------------------------------------
	//#CM30385
	/**
	 * Default constructor
	 */
	public As21Id79()
	{
		super() ;
	}
	
	//#CM30386
	/**
	 * Sets the communication message received from AGC; then implements the initialization
	 * of this class.
	 * @param <code>as21Id79</code>  the communication message "SystemRecoveryCompletionResponse"
	 */
	public As21Id79(byte[] as21Id79)
	{
		super();
		setReceiveMessage(as21Id79);
	}

	//#CM30387
	// Public methods ------------------------------------------------
	//#CM30388
	/**
	 * Acquires the response classification from the communication message "SystemRecoveryCompletionResponse"
	 * @return	the response classification	of the message "SystemRecoveryCompletionResponse" (00: recieved normal status)
	 * <table>
	 * <tr><td>0:</td><td>received in normal status</td></tr>
	 * </table>
	 */
	public String getResponseClassification()
	{
		String responseClassification = "99";
		responseClassification = getContent().substring(OFF_ID79_CLASS, OFF_ID79_CLASS + LEN_ID79_CLASS) ;
		return (responseClassification);
	}
	
	//#CM30389
	/**
	 * Acquires the content of the communication message "SystemRecoveryCompletionResponse"
	 * @return the content of message text "SystemRecoveryCompletionResponse"
	 */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}
	
	//#CM30390
	// Package methods -----------------------------------------------

	//#CM30391
	// Protected methods ---------------------------------------------
	//#CM30392
	/**
	 * Sets communication message received from AGC to the internal buffer.
	 * @param rmsg the communication message "SystemRecoveryCompletionResponse"
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM30393
		// Sets data to communication message buffer
		for (int i = 0; i < LEN_ID79; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}

	//#CM30394
	// Private methods -----------------------------------------------

}
//#CM30395
//end of class

