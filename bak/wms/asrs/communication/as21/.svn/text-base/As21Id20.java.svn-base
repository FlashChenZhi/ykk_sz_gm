// $Id: As21Id20.java,v 1.2 2006/10/26 01:37:46 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29276
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM29277
/**
 * Composes communication message "Response to transmission test ID=20" according to AS21 communication protocol.
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
public class As21Id20 extends SendIdMessage
{
	//#CM29278
	// Class fields --------------------------------------------------
	//#CM29279
	/**
	 * Length of test data
	 */
	private static final int TEST_DATA=488;

	//#CM29280
	/**
	 * Length of respective communication message responding to transmission test
	 */
	private static final int LEN_TOTAL=TEST_DATA;

	//#CM29281
	/**
	 * Variable which preserves test data
	 */
	private static String testdata;

	//#CM29282
	// Class variables -----------------------------------------------

	//#CM29283
	// Class method --------------------------------------------------
	//#CM29284
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:37:46 $") ;
	}

	//#CM29285
	// Constructors --------------------------------------------------
	//#CM29286
	/**
	 * Default constructor
	 */
	public As21Id20 ()
	{
		super() ;
		
	}

	//#CM29287
	/**
	 * Creates communication message responding to transmission test.
	 * @param <code>as21Id20</code>  Passes data abstracted from the responce to transmission test
	 * from AGC, ID=40.
	 */
	public As21Id20 (String td)
	{
		super() ;
		testdata=td;
	}
	
	//#CM29288
	// Public methods ------------------------------------------------
	//#CM29289
	/**
	 * Creates communication message responding to transmission test.
	 * <p><p/> required to release the responce of transmission test
	 * <ul>
	 * <li>Test Data
	 * </ul>
	 * <p> acquires data from test data provided in constructor.
	 * </p>
	 * @return	  communication message responding to transmission test
     * @throws  InvalidProtocolException : Notifies if communication message includes improper contents in protocol aspect.
	 */
    public String getSendMessage() throws InvalidProtocolException
	{
		//#CM29290
		// tesxt buffer
		StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT) ;
		
		mbuf.append(getTestData());
	
		//#CM29291
		//-------------------------------------------------
		//#CM29292
		// Setting for sending message buffer
		//#CM29293
		//-------------------------------------------------
		//#CM29294
		// id
		setID("20") ;
		//#CM29295
		// id segment
		setIDClass("00") ;
		//#CM29296
		// time sent
		setSendDate() ;
		//#CM29297
		// time sent to AGC
		setAGCSendDate("000000") ;
		//#CM29298
		// content of text
		setContent(mbuf.toString()) ;

		return (getFromBuffer(0, LEN_TOTAL + OFF_CONTENT)) ;
	}
	
	//#CM29299
	/**
	 * Sets test data.
	 * @param tt test data
	 */
	public void setTestData(String tt)
	{
		testdata = tt;
	}
	
	//#CM29300
	/**
	 * Gets the test data.
	 * @return test data 
	 * @throws InvalidProtocolException : Reports if test data is not the allowable length.
	 */
	private String getTestData() throws InvalidProtocolException
	{
		if(testdata.length() != TEST_DATA)
		{
			throw new InvalidProtocolException("testdata = " + TEST_DATA + "--->" + testdata);
		}
		return (testdata);
	}			
		
	//#CM29301
	// Package methods -----------------------------------------------

	//#CM29302
	// Protected methods ---------------------------------------------

	//#CM29303
	// Private methods -----------------------------------------------
}
//#CM29304
//end of class
