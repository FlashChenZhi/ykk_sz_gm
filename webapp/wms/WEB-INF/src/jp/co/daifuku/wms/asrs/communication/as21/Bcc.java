// $Id: Bcc.java,v 1.3 2006/11/13 08:30:12 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

import jp.co.daifuku.wms.asrs.common.HexFormat;
//#CM30935
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM30936
/**
 * Creates BBC and checks according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/13 08:30:12 $
 * @author  $Author: suresh $
 */
public class Bcc extends Object
{
	//#CM30937
	// Class fields --------------------------------------------------
	//#CM30938
	/**
	 * Defines the BBC value (string) according to AS21.
	 */
	 protected static String BCC_STRING = "00";

	//#CM30939
	/**
	 * Defines the length of BBC string in comunication message according to AS21.
	 */
	 protected static int BCC_LENGTH = 2;

	//#CM30940
	/**
	 * BCC calcuates from teh beginning of the message to the end (BCC_LENGTH).
	 */
	 private static int BCC_START = 0;

	//#CM30941
	/**
	 * Defines the length of communication message.
	 */
	 //private static int AS21_TEXT_LENGTH = 512;

	//#CM30942
	/**
	 * Defines the length of STX.
	 */
	 //private static int AS21_STX_LENGTH = 1;

	//#CM30943
	/**
	 * Defines the length of ETX.
	 */
	 //private static int AS21_ETX_LENGTH = 1;

	//#CM30944
	// Class variables -----------------------------------------------

	//#CM30945
	// Class method --------------------------------------------------
     //#CM30946
     /**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2006/11/13 08:30:12 $") ;
	}

	//#CM30947
	// Constructors --------------------------------------------------

	//#CM30948
	// Public methods ------------------------------------------------

	//#CM30949
	/**
	 * Creates Bcc value in form of 2 Byte character. ( lower case characters 
	 * appeared during the calculation, e.g., a-f, will be converted to uper case
	 * characters.)
	 * @param	b : communication message to send (Byte array)
	 * @param	makeLength : length of communication message to send
	 * @return  : Bcc string
	 */
	public String make(byte[] b, int makeLength)
	{
		//#CM30950
		// Set the initila value for calculation
		int cBcc = new Byte(b[BCC_START]).intValue();
		//#CM30951
		// calculate sequentially from the following array
    	for( int i=BCC_START+1; i < makeLength; i++){
    		cBcc = cBcc ^ new Byte(b[i]).intValue();
    	}

		HexFormat hformat = new HexFormat(BCC_STRING) ;
		String sBcc = hformat.format(cBcc).toUpperCase() ;
		return(sBcc) ;
	}

	//#CM30952
	/**
	 * Compares the Bcc value included in the received message and the other Bcc value calculated from the 
	 * received text content; use them to check the correctness.
	 * @param	wkb : received communication message (Byte array which excluded STX and ETX)
	 * @param	rcvLength : length of received communication message
	 * @return    Correctness of Bcc
	 */
	public boolean check(byte[] wkb, int rcvLength)
	{
		String sBcc = null;
		//#CM30953
		// Store the Bcc value included in the received communication messages
		byte[] bccByte = new byte[BCC_LENGTH];

		bccByte[0] = wkb[rcvLength -2];
		bccByte[1] = wkb[rcvLength -1];
		String rBcc = new String(bccByte);

		//#CM30954
		// Calls the method for the creation of Bcc.
		sBcc = make(wkb, rcvLength-2);

		if( rBcc.equals( sBcc ) )
		{
			return true;
		}
		else 
		{
			return false;
		}
	}

	//#CM30955
	// Package methods -----------------------------------------------

	//#CM30956
	// Protected methods ---------------------------------------------

	//#CM30957
	// Private methods -----------------------------------------------

}
//#CM30958
//end of class
