// $Id: As21Id78.java,v 1.2 2006/10/26 01:13:42 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30343
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM30344
/**
 * Processes the communication message "SystemRecoveryStartResponse" ID=78, the response to the 
 * start of system recovery, according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:13:42 $
 * @author  $Author: suresh $
 */
public class As21Id78 extends ReceiveIdMessage
{
	//#CM30345
	// Class fields --------------------------------------------------
	//#CM30346
	/**
	 * Length of communication message ID78 (excluding STX, SEQ-No, BCC and ETX)
	 * <p>
	 * <table>
	 * <tr><td>ID:</td><td>2 byte</td></tr>
	 * <tr><td>ID segment:</td><td>2 byte</td></tr>
	 * <tr><td>MC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>Reponse classification:</td><td>2 byte</td></tr>
	 * <tr><td>Error detail:</td><td>2 byte</td></tr>
	 * </table>
	 * </p>
	 */

	//#CM30347
	/**
	 * Length of communication message ID78 (excluding STX, SEQ-No, BCC and ETX)
	 */
	public static final int LEN_ID78 = 20 ;
	
	//#CM30348
	/**
	 * Defines the offset of response classificaiton ID78
	 */
	private static final int OFF_ID78_CLASS = 0 ;

	//#CM30349
	/**
	 * Length of response classification ID78 (byte)
	 */
	private static final int LEN_ID78_CLASS = 2 ;
	
	//#CM30350
	/**
	 * Defines the offset error detail of ID78
	 */
	private static final int OFF_ID78_ERROR = OFF_ID78_CLASS + LEN_ID78_CLASS ;

	//#CM30351
	/**
	 * Length of error detail of ID78 (byte)
	 */
	private static final int LEN_ID78_ERROR = 2 ;

	//#CM30352
	/**
	 * "normal status" of response classification
	 */
	public static final String NORMAL="00";

	//#CM30353
	/**
	 * "AGC error" of response classification
	 */
	public static final String AGC_ERROR="03";

	//#CM30354
	/**
	 * "Data Error" of response classification
	 */
	public static final String DATA_ERROR="99";
	
	//#CM30355
	// Class variables -----------------------------------------------
	//#CM30356
	/**
	 * Variables for the preservation of communication messages
	 */
	private byte[] wLocalBuffer = new byte[LEN_ID78] ;

	//#CM30357
	// Class method --------------------------------------------------
	//#CM30358
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Data: 2001/05/11 02:33:53 $");
	}
	
	//#CM30359
	// Constructors --------------------------------------------------
	//#CM30360
	/**
	 * Default constructor
	 */
	public As21Id78()
	{
		super() ;
	}
	
	//#CM30361
	/**
	 * Sets the communication message received from AGC; then implements the initialization
	 * of this class.
	 * @param <code>as21Id78</code>  communication message "SystemRecoveryStartResponse"
	 */
	public As21Id78(byte[] as21Id78)
	{
		super() ;
		setReceiveMessage(as21Id78) ;
	}

	//#CM30362
	// Public methods ------------------------------------------------
	//#CM30363
	/**
	 * Acquires the response classification from the communication message "SystemRecoveryStartResponse".
	 * @return		response classification of the communication message "SystemRecoveryStartResponse"
	 * 				00:Normal<BR>
	 *				03:AGC status error<BR>
	 *				99:Data Error
	 */
	public String getResponseClassification()
	{
		String responseClassification = "";
		responseClassification = getContent().substring(OFF_ID78_CLASS, OFF_ID78_CLASS + LEN_ID78_CLASS) ;
		return (responseClassification);
	}
	
	//#CM30364
	/**
	 * Acquires the detail of error from the communication message "SystemRecoveryStartResponse". It is valid
	 * only if 03 (AGC status error) is assigned to the response classification.
	 * @return		AGC No. which has the status error (NOT able to start WORK) 
	 */
	public String getErrorDetails() 
	{
		String errorDetail = "00" ;
		if(getResponseClassification().equals(AGC_ERROR))
		{
			errorDetail = getContent().substring(OFF_ID78_ERROR, OFF_ID78_ERROR + LEN_ID78_ERROR) ;
		}
		return (errorDetail) ;
		
	}
	
	//#CM30365
	/**
	 * Acquires content of communication message "SystemRecoveryStartResponse".
	 * @return  content of the text of "SystemRecoveryStartResponse"
	 */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM30366
	// Package methods -----------------------------------------------

	//#CM30367
	// Protected methods ---------------------------------------------
	//#CM30368
	/**
	 * Sets communication message received from AGC to the internal buffer.
	 * @param rmsg the communication message "SystemRecoveryStartResponse"
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM30369
		// Sets data to communication message buffer
		for (int i = 0; i < LEN_ID78; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}
	
	//#CM30370
	// Private methods -----------------------------------------------

}
//#CM30371
//end of class

