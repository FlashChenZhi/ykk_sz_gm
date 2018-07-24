// $Id: As21Id23.java,v 1.2 2006/10/26 01:37:45 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29369
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM29370
/**
 * Processes communication message "Response to Work completion, ID=23" according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:37:45 $
 * @author  $Author: suresh $
 */
public class As21Id23 extends ReceiveIdMessage
{
	//#CM29371
	// Class fields --------------------------------------------------
	//#CM29372
	/**
	 * Indicates 'normal end' of response classification.
	 */
	public static final String NORMAL_END = "00";

	//#CM29373
	/**
	 * Indicates 'NOT able to end' of response classification.
	 */
	public static final String END_IMPOSS = "01";

	//#CM29374
	/**
	 * Indicates 'data error' of response classification.
	 */
	public static final String DATA_ERR = "99";

	//#CM29375
	/**
	 * Length of communication message ID23 (excluding STX, SEQ-NO, BCC and ETX)
	 * <p>
	 * <table>
	 * <tr><td>ID:</td><td>2 byte</td></tr>
	 * <tr><td>ID segment:</td><td>2 byte</td></tr>
	 * <tr><td>MC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>response classification:</td><td>2 byte</td></tr>
	 * <tr><td>model CODE:</td><td>2 byte</td></tr>
	 * <tr><td>machine code:</td><td>4 byte</tr></tr>
	 * </table>
	 * </p>
	 */
	private static final int LEN_ID23 = 24;

	//#CM29376
	/**
	 * Defines offset in response classification.
	 */
	private static final int OFF_ID23_RESPONSE_CLASS = 0;

	//#CM29377
	/**
	 * Length of response classification (in byte)
	 */
	private static final int LEN_ID23_RESPONSE_CLASS = 2;

	//#CM29378
	/**
	 * Defines offset of model code.
	 */
	private static final int OFF_ID23_MODEL_CODE = OFF_ID23_RESPONSE_CLASS+LEN_ID23_RESPONSE_CLASS;

	//#CM29379
	/**
	 * Length of model code (in byte)
	 */
	private static final int LEN_ID23_MODEL_CODE = 2;

	//#CM29380
	/**
	 * Defines offset of machine code#.
	 */
	private static final int OFF_ID23_MACHINE_CODE = OFF_ID23_MODEL_CODE+LEN_ID23_MODEL_CODE;

	//#CM29381
	/**
	 * Length of machine code# in byte
	 */
	private static final int LEN_ID23_MACHINE_CODE = 4;

	//#CM29382
	// Class variables -----------------------------------------------
	//#CM29383
	/**
	 * Communication message buffer
	 */
    private byte[] wLocalBuffer = new byte[LEN_ID23];

	//#CM29384
	// Class method --------------------------------------------------
    //#CM29385
    /**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/10/26 01:37:45 $");
    }

	//#CM29386
	// Constructors --------------------------------------------------
	//#CM29387
	/**
	 * Default constructor
	 */
	public As21Id23 ()
	{
		super();
	}

	//#CM29388
	/**
	 * Passes communication message received from AGC to the constructor.
	 * @param <code>as21Id23</code>  communication message of Work Completion Response, ID=23
	 */
	public As21Id23(byte[] as21Id23)
	{
		super();
		setReceiveMessage(as21Id23);
	}

	//#CM29389
	// Public methods ------------------------------------------------
	//#CM29390
	/**
	 * Acquires response classification from communication message of Work Completion Response.
	 * @return    00:normal end  01:NOT able to end  99: error in data
	 */
	public String getResponseClassification()
	{
		String responseClassification = DATA_ERR ;
		responseClassification = getContent().substring(OFF_ID23_RESPONSE_CLASS, OFF_ID23_RESPONSE_CLASS + LEN_ID23_RESPONSE_CLASS) ;
		return (responseClassification);
	}

	//#CM29391
	/**
	 * Acquires model code from the communication message Work Completion Response.
	 * @return    model code
	 */
	public String getModelCode()
	{
		String modelCode = getContent().substring(OFF_ID23_MODEL_CODE,OFF_ID23_MODEL_CODE + LEN_ID23_MODEL_CODE);
	
		return (modelCode);
	}

	//#CM29392
	/**
	 * Acquires machine code # from the communication message Work Completion Response.
	 * @return    machine code #
	 */
	public String getMachineNo()
	{
	
		String machineNo = getContent().substring(OFF_ID23_MACHINE_CODE,OFF_ID23_MACHINE_CODE + LEN_ID23_MACHINE_CODE); 
		return (machineNo);
	}

	//#CM29393
	/**
	 * Acquires content of communication message Work Completion Response.
	 * @return Text content of Work Completion Response.
	 */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM29394
	// Package methods -----------------------------------------------

	//#CM29395
	// Protected methods ---------------------------------------------
	//#CM29396
	/**
	 * Sets communication message reveived fro AGC to internal buffer.
	 * @param rmsg communication message reveived fro AGC
	 */
    protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM29397
		// Sets data to communication message buffer
		for (int i=0; i < LEN_ID23; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}

	//#CM29398
	// Private methods -----------------------------------------------

}
//#CM29399
//end of class
