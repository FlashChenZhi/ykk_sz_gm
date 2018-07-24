// $Id: As21Id40.java,v 1.2 2006/10/26 01:32:06 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29810
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM29811
/**
 * Processes communication message "TransmissionTestRequest" ID=40, according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:32:06 $
 * @author  $Author: suresh $
 */
public class As21Id40 extends ReceiveIdMessage
{
	//#CM29812
	// Class fields --------------------------------------------------
	//#CM29813
	/**
	 * Total number of bytes in ID40 (excluding STX, SEQ-NO, BCC and ETX)
	 * <p>
	 * <table>
	 * <tr><td>ID:</td><td>2 byte</td></tr>
	 * <tr><td>ID segment:</td><td>2 byte</td></tr>
	 * <tr><td>MC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>Test data:</td><td>488 byte</td></tr>
	 */

	//#CM29814
	/**
	 * Total number of bytes in ID40 (excluding STX, SEQ-NO, BCC and ETX)
	 */
	public static final int LEN_ID40 = 504;

	//#CM29815
	/**
	 * Defines offsest of Test data.
	 */
	private static final int OFF_ID40_TEST_DATA = 0;

	//#CM29816
	/**
	 * Length of Test data (in byte)
	 */
	private static final int LEN_ID40_TEST_DATA = 488;

	//#CM29817
	// Class variables -----------------------------------------------
	//#CM29818
	/**
     * Variable that preserves the communication message
     */
	private byte[] wLocalBuffer = new byte[LEN_ID40];

	//#CM29819
	// Class method --------------------------------------------------
    //#CM29820
    /**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/10/26 01:32:06 $");
    }

	//#CM29821
	// Constructors --------------------------------------------------
	//#CM29822
	/**
	 * Sets the communication message received from AGC, then initialize this class.
	 * @param <code>as21Id40</code>  communication message "TransmissionTestRequest"
	 */
	public As21Id40(byte[] as21Id40)
	{
		super() ;
		setReceiveMessage(as21Id40) ;
	}

	//#CM29823
	// Public methods ------------------------------------------------
	//#CM29824
	/**
	 * Acquires the Test Data from the communication message "TransmissionTestRequest".
	 * @return    the communication message "TransmissionTestRequest"
	 */
	public String getTestData ()
	{
		String testData = getContent().substring(OFF_ID40_TEST_DATA,OFF_ID40_TEST_DATA+LEN_ID40_TEST_DATA);
		return (testData);
	}

    //#CM29825
    /**
     * Acquires the contents of the communication message "TransmissionTestRequest"
     * @return contents of the communication message "TransmissionTestRequest"
     */
	public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM29826
	// Package methods -----------------------------------------------

	//#CM29827
	// Protected methods ---------------------------------------------
	//#CM29828
	/**
     * Sets communication message received from AGC to the internal buffer.
     * @param rmsg the communication message "TransmissionTestRequest"
     */
	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM29829
		// Sets data to communication message buffer
		for (int i = 0; i < LEN_ID40; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}

	//#CM29830
	// Private methods -----------------------------------------------

}
//#CM29831
//end of class

