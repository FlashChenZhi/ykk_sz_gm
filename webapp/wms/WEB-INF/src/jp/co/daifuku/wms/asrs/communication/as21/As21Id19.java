// $Id: As21Id19.java,v 1.2 2006/10/26 01:37:46 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29246
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM29247
/**
 * Composes communication message "transmission test request ID=19" according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:37:46 $
 * @author  $Author: suresh $
 */
public class As21Id19 extends SendIdMessage
{
	//#CM29248
	// Class fields --------------------------------------------------
	//#CM29249
	/**
	 * Length of test data
	 */
	protected static final int TEST_DATA	= 488 ;

	//#CM29250
	/**
	 * Length of communication message
	 */
	protected final int LEN_TOTAL = TEST_DATA;

	//#CM29251
	// Class variables -----------------------------------------------
	//#CM29252
	/**
	 * Variable which perserves test data
	 */
	private String test;

	//#CM29253
	// Class method --------------------------------------------------
	//#CM29254
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:37:46 $") ;
	}
	//#CM29255
	// Constructors --------------------------------------------------
	//#CM29256
	/**
	 * Default constructor
	 */
	public As21Id19()
	{
		super() ;
	}

	 //#CM29257
	 /**
	 * Generates the instance of this class by specifying the test data.
	 * @param  test     Test data
	 */
	public As21Id19(String testData)
	{
		super() ;
		test=testData;
	}

	//#CM29258
	// Public methods ------------------------------------------------
	//#CM29259
	/**
	 * Creates communication message requesting the transmission test.
     * @return    communication message requesting the transmission test
     * @throws  InvalidProtocolException : Notifies if communication message includes improper contents in protocol aspect.
	 */
	public String getSendMessage() throws InvalidProtocolException
	{
		//#CM29260
		// text buffer
		StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT) ;
		//#CM29261
		// TestData
		mbuf.append(getTestData()) ;
		//#CM29262
		//-------------------------------------------------
		//#CM29263
		// Setting for the sending message buffer
		//#CM29264
		//-------------------------------------------------
		//#CM29265
		// id
		setID("19") ;
		//#CM29266
		// id segment
		setIDClass("00") ;
		//#CM29267
		// time sent
		setSendDate() ;
		//#CM29268
		// time sent to AGC
		setAGCSendDate("000000") ;
		//#CM29269
		// content of text
		setContent(mbuf.toString()) ;

		return (getFromBuffer(0, OFF_CONTENT + LEN_TOTAL)) ;
	}

	//#CM29270
	/**
	 * Sets the test data
	 * @param st test data
	 */
	public void setTestData(String st)
	{
		test=st;
	}

	//#CM29271
	// Package methods -----------------------------------------------

	//#CM29272
	// Protected methods ---------------------------------------------

	//#CM29273
	// Private methods -----------------------------------------------
	
	//#CM29274
	/**
	 * Gets the test data.
	 * @return test data 
	 * @throws InvalidProtocolException : Reports if test data is not the allowable length.
	 */
	private String getTestData() throws InvalidProtocolException
	{
		if(test.getBytes().length!=TEST_DATA)
		{
			throw new InvalidProtocolException("test="+TEST_DATA+"---->"+test);
		}
		else
		{
			return test;
		}
	}
}
//#CM29275
//end of class
