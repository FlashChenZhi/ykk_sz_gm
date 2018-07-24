// $Id: As21Id24.java,v 1.2 2006/10/26 01:37:44 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29400
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM29401
/**
 * Processes the communication message "Response to carry data cancel, ID=24" according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:37:44 $
 * @author  $Author: suresh $
 */
public class As21Id24 extends ReceiveIdMessage
{
	//#CM29402
	// Class fields --------------------------------------------------
	//#CM29403
	/**
	 * Length of communication message ID24 (excluding STX, SEQ-No, BCC and ETX).
	 * <p>
	 * <table>
	 * <tr><td>ID:</td><td>2 byte</td></tr>
	 * <tr><td>ID segment:</td><td>2 byte</td></tr>
	 * <tr><td>MC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>MC Key:</td><td>8 byte</td></tr>
	 * <tr><td>Cancel result:</td><td>2 byte</td></tr>
	 * </table>
	 * </p>
	 */
	public static final int LEN_ID24 = 26 ;

	//#CM29404
	/**
	 * Field to indicate the result of Cancel (normal end)
	 */
	public static final String RESULT_COMP_NORMAL = "00" ;

	//#CM29405
	/**
	 * Field to indicate the result of Cancel ( NOT able to cancel,
	 * as corresponding data has already started being carried.)
	 */
	public static final String RESULT_NOT_CANCEL = "01" ;

	//#CM29406
	/**
	 * Field to indicate the result of Cancel ( no corresponding data found)
	 */
	public static final String RESULT_NOT_DATA = "02" ;

	//#CM29407
	/**
	 * Defines offset of MC Key.
	 */
	private static final int OFF_ID24_MCKEY = 0 ;

	//#CM29408
	/**
	 * Length of MC Key (in byte)
	 */
	private static final int LEN_ID24_MCKEY = 8 ;

	//#CM29409
	/**
	 * Defines offset of Cancel results
	 */
	private static final int OFF_ID24_RESULT = OFF_ID24_MCKEY + LEN_ID24_MCKEY ;

	//#CM29410
	/**
	 * Length of Cancel result (in byte)
	 */
	private static final int LEN_ID24_RESULT = 2 ;

	//#CM29411
	// Class variables -----------------------------------------------
	//#CM29412
	/**
	 * Communication message buffer
	 */
	private byte[] wLocalBuffer = new byte[LEN_ID24] ;

	//#CM29413
	// Class method --------------------------------------------------
	//#CM29414
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:37:44 $") ;
	}

	//#CM29415
	// Constructors --------------------------------------------------
	//#CM29416
	/**
	 * Default constructor
	 */
	public As21Id24()
	{
		super() ;
	}
	
	//#CM29417
	/**
	 * Passes communication message received from AGC to the constructor.
	 * @param <code>as21Id24</code>  communication message "Response to carry data cancel"ID=24
	 */
	public As21Id24(byte[] as21Id24)
	{
		super() ;
		setReceiveMessage(as21Id24) ;
	}

	//#CM29418
	// Public methods ------------------------------------------------
	//#CM29419
	/**
	 * Acquires MC Key from the communication message "Response to carry data cancel".
	 * @return    MC Key
	 */
	public String getMcKey ()
	{
		String mcKey = getContent().substring(OFF_ID24_MCKEY, OFF_ID24_MCKEY + LEN_ID24_MCKEY) ;;
		return (mcKey);
	}
	//#CM29420
	/**
	 * Acquires the result of cancel from the communication message "Response to carry data cancel"
	 * @return    00:normal end<BR>
	 *            01:NOT able to cancel; corresponding data has already started being carried.<BR>
	 *			  02:No corresponding data is found.
	 */
	public String getCancellationResults()
	{
		String cancellationResults = getContent().substring(OFF_ID24_RESULT, OFF_ID24_RESULT + LEN_ID24_RESULT);
		return (cancellationResults);
	}

	//#CM29421
	/**
	 * Returns string representation of the communication message "Response to carry data cancel"
	 * @return string representation of the communication message "Response to carry data cancel"
	 */
	public String toString()
	{
		return (new String(wLocalBuffer));
	}

	//#CM29422
	// Package methods -----------------------------------------------

	//#CM29423
	// Protected methods ---------------------------------------------
	//#CM29424
	/**
	 * Sets communication message received from AGC to the internal buffer.
	 * @param rmsg communication message received from AGC 
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM29425
		// Sets data to communication message buffer
		for (int i=0; i < LEN_ID24; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}

	//#CM29426
	// Private methods -----------------------------------------------

}
//#CM29427
//end of class

