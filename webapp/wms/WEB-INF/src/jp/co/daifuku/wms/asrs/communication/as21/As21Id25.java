// $Id: As21Id25.java,v 1.2 2006/10/26 01:37:44 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29428
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM29429
/**
 * Processes communication message "Transport  Command Response, ID=25" according to AS21 communication protocol.
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
public class As21Id25 extends ReceiveIdMessage
{
	//#CM29430
	// Class fields --------------------------------------------------
	//#CM29431
	/**
	 * Length of communication message ID25 (excluding STX, SEQ-No,BCC and ETX)
	 * <p>
	 * <table>
	 * <tr><td>ID:</td><td>2 byte</td></tr>
	 * <tr><td>ID segment:</td><td>2 byte</td></tr>
	 * <tr><td>MC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>MC Key:</td><td>8 byte</td></tr>
	 * <tr><td>Response classification:</td><td>2 byte</td></tr>
	 * </table>
	 * </p>
	 */
	public static final int LEN_ID25 = 26 ;

	//#CM29432
	/**
	 * Defines offset of MC Key
	 */
	private static final int OFF_ID25_MCKEY = 0 ;

	//#CM29433
	/**
	 * Length of MC Key (in byte)
	 */
	private static final int LEN_ID25_MCKEY = 8 ;

	//#CM29434
	/**
	 * Defines offset of response classification
	 */
	private static final int OFF_ID25_CLASS = OFF_ID25_MCKEY + LEN_ID25_MCKEY ;

	//#CM29435
	/**
	 * Length of response classification
	 */
	private static final int LEN_ID25_CLASS = 2 ;

	//#CM29436
	// Class variables -----------------------------------------------
	//#CM29437
	/**
	 * Communication message buffer
	 */
	private byte[] wLocalBuffer = new byte[LEN_ID25] ;

	//#CM29438
	// Class method --------------------------------------------------
     //#CM29439
     /**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:37:44 $") ;
	}

	//#CM29440
	// Constructors --------------------------------------------------
	//#CM29441
	/**
	 * Default constructor
	 */
	public As21Id25 ()
	{
		super();
	}

	//#CM29442
	/**
	 * Passes communication message received fro AGC to the constructor.
	 * Make sure that the message does not include STX, SEQ-No, BCC and ETX.
	 * @param <code>as21Id25</code>  communication message "Transport Command Response" ID=25
	 */
	public As21Id25(byte[] as21Id25)
	{
		super() ;
		setReceiveMessage(as21Id25) ;
	}

	//#CM29443
	// Public methods ------------------------------------------------
	//#CM29444
	/**
	 * Acquires MC Key from the communication message Transport Command Response.
	 * @return    MC Key
	 */
	public String getMcKey ()
	{
		String mcKey = getContent().substring(OFF_ID25_MCKEY, OFF_ID25_MCKEY + LEN_ID25_MCKEY) ;
		return (mcKey);
	}

	//#CM29445
	/**
	 * Acquires response classification from the message Transport Command Response.
	 * @return
	 * <table>
	 * <tr><td>0:</td><td>normal</td></tr>
	 * <tr><td>1:</td><td>load presence error</td></tr>
	 * <tr><td>3:</td><td>multiple set</td></tr>
	 * <tr><td>4:</td><td>out of order</td></tr>
	 * <tr><td>5:</td><td>cutting loose</td></tr>
	 * <tr><td>6:</td><td>turning off-Line</td></tr>
	 * <tr><td>7:</td><td>condition error</td></tr>
	 * <tr><td>11:</td><td>BufferFull</td></tr>
	 * <tr><td>99:</td><td>DataError</td></tr>
	 * </table>
	 * @throws InvalidProtocolException : Reports if numeric value was not provided for response classification.
	 */
	public int getResponseClassification () throws InvalidProtocolException
	{
		int rclass = 99 ;
		String responseClassification = getContent().substring(OFF_ID25_CLASS, OFF_ID25_CLASS + LEN_ID25_CLASS) ;
		try
		{
			rclass =  Integer.parseInt(responseClassification) ;
		}
		catch (Exception e)
		{
			throw (new InvalidProtocolException("Invalid Response:" + responseClassification)) ;
		}
		return (rclass);
	}
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM29446
	// Package methods -----------------------------------------------

	//#CM29447
	// Protected methods ---------------------------------------------
	//#CM29448
	/**
	 * Sets communication message received from AGC to the internal buffer.
	 * @param rmsg communication message received from AGC
	 */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM29449
		// Sets data to communication message buffer
		for (int i=0; i < LEN_ID25; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}

	//#CM29450
	// Private methods -----------------------------------------------

	//#CM29451
	// debug methods -----------------------------------------------

}
//#CM29452
//end of class

