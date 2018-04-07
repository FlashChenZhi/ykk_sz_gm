// $Id: As21Id68.java,v 1.2 2006/10/26 01:16:15 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;


//#CM30245
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM30246
/**
 * Processes the communication message "OperationDisplayTrigger" ID=68 according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:16:15 $
 * @author  $Author: suresh $
 */
public class As21Id68 extends ReceiveIdMessage
{
	//#CM30247
	// Class fields --------------------------------------------------
	//#CM30248
	/**
	 * Length of comunication message ID68 (excluding STX, SEQ-No, BCC and ETX)
	 * <p>
	 * <table>
	 * <tr><td>ID:</td><td>2 byte</td></tr>
	 * <tr><td>ID segment:</td><td>2 byte</td></tr>
	 * <tr><td>MC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>MC Key:</td><td>8 byte</td></tr>
	 * <tr><tdz>Station No.:</td><td>4 byte</td></tr>
	 * <tr><tdz>control data:</td><td>30 byte</td></tr>
	 * </table>
	 * </p>
	 */

	//#CM30249
	/**
	 * Length of communication message ID68 (excluding STX, SEQ-No, BCC and ETX)
	 */
	public static final int LEN_ID68 = 58 ;
	
	//#CM30250
	/**
	 * Defines the offset of MC KEY of ID68
	 */
	private static final int OFF_ID68_MCKEY = 0 ;

	//#CM30251
	/**
	 * Length of MC KEY of ID68 (byte)
	 */
	private static final int LEN_ID68_MCKEY = 8 ;
	
	//#CM30252
	/**
	 * Defines the offset of station No. of ID68
	 */
	private static final int OFF_ID68_STATION = OFF_ID68_MCKEY + LEN_ID68_MCKEY ;

	//#CM30253
	/**
	 * Length of station No. of ID68 (byte)
	 */
	private static final int LEN_ID68_STATION = 4 ;
	
	//#CM30254
	/**
	 * Defines the offset of control data of ID68
	 */
	private static final int OFF_ID68_CONTROL_INFO = OFF_ID68_STATION + LEN_ID68_STATION ;

	//#CM30255
	/**
	 * Length of control data of (byte)
	 */
	private static final int LEN_ID68_CONTROL_INFO = 30 ;

	//#CM30256
	// Class variables -----------------------------------------------
	//#CM30257
	/**
	 * Variables for the reservation of communication messages
	 */
	private byte[] wLocalBuffer = new byte[LEN_ID68] ;

	//#CM30258
	// Class method --------------------------------------------------
	//#CM30259
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Data: 2001/05/11 02:33:53 $");
	}
	
	//#CM30260
	// Constructors --------------------------------------------------
	//#CM30261
	/**
	 * Sets the communication message received from AGC; then initializes this class.
	 * @param <code>as21Id68</code>  communication message "OperationDisplayTrigger"
	 */
	public As21Id68(byte[] as21Id68)
	{
		super() ;
		setReceiveMessage(as21Id68) ;
	}

	//#CM30262
	// Public methods ------------------------------------------------
	//#CM30263
	/**
	 * Acquires the MC Key from the communication message "OperationDisplayTrigger".
	 * @return    	MC Key
	 */
	public String getMcKey ()
	{	
		String mcKey = getContent().substring(OFF_ID68_MCKEY, OFF_ID68_MCKEY + LEN_ID68_MCKEY) ;
		return (mcKey);
	}

	//#CM30264
	/**
	 * Acquires the receiving station No. from the communication message "OperationDisplayTrigger".
	 * @return    	receiving station No.
	 */
	public String getStationNo ()
	{
		String stationNo = getContent().substring(OFF_ID68_STATION, OFF_ID68_STATION + LEN_ID68_STATION) ;
		return (stationNo);
	}

	//#CM30265
	/**
	 * Acquires conrtol data from the communication message "OperationDisplayTrigger".
	 * @return    	conrtol data
	 */
	public String getControlInformation ()
	{
		String controlInformation = getContent().substring(OFF_ID68_CONTROL_INFO, OFF_ID68_CONTROL_INFO + LEN_ID68_CONTROL_INFO) ;
		return (controlInformation);
		
	}

	//#CM30266
	/**
	 * Acquires contents of the communication message "OperationDisplayTrigger".
	 * @return contents of communication message "OperationDisplayTrigger"
	 */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM30267
	// Package methods -----------------------------------------------

	//#CM30268
	// Protected methods ---------------------------------------------

	//#CM30269
	// Private methods -----------------------------------------------
	//#CM30270
	/**
	 * ASets communication message received from AGC to the internal buffer.
	 * @param rmsg the communication message "OperationDisplayTrigger"
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM30271
		// Sets data to communication message buffer
		for (int i = 0; i < LEN_ID68; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}
}
//#CM30272
//end of class

