// $Id: As21Id39.java,v 1.2 2006/10/26 01:35:07 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29788
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM29789
/**
 * Processes the communication message "TransmissionTestResponse" ID=39, according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:35:07 $
 * @author  $Author: suresh $
 */
public class As21Id39 extends ReceiveIdMessage
{
	//#CM29790
	// Class fields --------------------------------------------------
	//#CM29791
	/**
	 * Length of communication message of ID39 (excluding STX, SEQ-NO, BCC and ETX)
	 * <p>
	 * <table>
	 * <tr><td>ID:</td><td>2 byte</td></tr>
	 * <tr><td>ID segment:</td><td>2 byte</td></tr>
	 * <tr><td>MC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
	 * <tr><td>Test data:</td><td>488 byte</td></tr>
	 * </table>
	 * <p>
	 * Comment for field
	 */

	//#CM29792
	/**
	 * Total number of bytes in ID39 (excluding STX, SEQ-NO, BCC and ETX)
	 */
    public static final int LEN_ID39 = 504;

	//#CM29793
	/**
	 * Defines offset of Test data
	 */
    private static final int OFF_ID39_TEST_DATA = 0;

    //#CM29794
    /**
     * Length of Test data (in byte)
     */
    private static final int LEN_ID39_TEST_DATA=488; 

	//#CM29795
	// Class variables -----------------------------------------------
    //#CM29796
    /**
     * Variable which preserves the communication messages
     */
    private byte[] wLocalBuffer = new byte[LEN_ID39] ;

	//#CM29797
	// Class method --------------------------------------------------
    //#CM29798
    /**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/10/26 01:35:07 $");
    }

	//#CM29799
	// Constructors --------------------------------------------------
	//#CM29800
	/**
	 * Sets the communication message received from  AGC, then initialize this class.
	 * @param <code>as21Id39</code>  communication message "TransmissionTestResponse"
	 */
	public As21Id39(byte[] as21Id39)
	{
		super() ;
		setReceiveMessage(as21Id39) ;
	}

	//#CM29801
	// Public methods ------------------------------------------------
	//#CM29802
	/**
	 * Acquires test data from communication message "TransmissionTestResponse".
	 * @return    Test Data
	 */
	public String getTestData ()
	{
		String testData = getContent().substring(OFF_ID39_TEST_DATA,OFF_ID39_TEST_DATA+LEN_ID39_TEST_DATA);
		return (testData);
	}

	//#CM29803
	/**
	 * Acquires the contents of communication message "TransmissionTestResponse".
	 * @return contents of communication message "TransmissionTestResponse"
	 */
    public String toString()
	{
		return (new String(wLocalBuffer)) ;
	}

	//#CM29804
	// Package methods -----------------------------------------------

	//#CM29805
	// Protected methods ---------------------------------------------
    //#CM29806
    /**
     * Sets communication message received from AGC to the internal buffer.
     * @param rmsg communication message "TransmissionTestResponse"
     */
   	protected void setReceiveMessage(byte[] rmsg)
	{
		//#CM29807
		// Sets data to communication message buffer
		for (int i = 0; i < LEN_ID39; i++)
		{
			wLocalBuffer[i] = rmsg[i] ;
		}
		wDataBuffer = wLocalBuffer ;
	}

	//#CM29808
	// Private methods -----------------------------------------------

}
//#CM29809
//end of class

