// $Id: As21Id47.java,v 1.2 2006/10/26 01:30:21 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30000
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM30001
/**
 * Composes communication message "RetrievalTriggerRepetitionRequest" ID=47 (request for the repetition of 
 * retrieval trigger) according to AS21 communication protocol.<BR>
 * <FONT SIZE="5" COLOR="RED"> Not implemented; please be careful.</FONT>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:30:21 $
 * @author  $Author: suresh $
 */
public class As21Id47 extends SendIdMessage
{
	//#CM30002
	// Class fields --------------------------------------------------
	//#CM30003
	/**
	 * Length of station No. (in byte)
	 */
	private static final int LEN_STATION_NO = 4 ;
	
	//#CM30004
	/**
	 * Length of respective communication message "RetrievalTriggerRepetitionRequest"
	 */
	private static final int LEN_TOTAL = LEN_STATION_NO ;

	//#CM30005
	// Class variables -----------------------------------------------
	//#CM30006
	/**
	 * Variable for the preservation of station No.
	 */
	private static String stationNo ;

	//#CM30007
	// Class method --------------------------------------------------
	//#CM30008
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:30:21 $") ;
	}

	//#CM30009
	// Constructors --------------------------------------------------
	//#CM30010
	/**
	 * Default constructor
	 */
	public As21Id47 ()
	{
		super() ;
	}

	//#CM30011
	/**
	 * Sets the station No. for the automatic retrieval operation driven by
	 * auto-retrieval trigger; then initializes this class.
	 * @param <code>stno</code> Station No. for automatic retrieval operation
	 * using retrieval trigger.
	 */
	public As21Id47 ( String stno )
	{
		super() ;
		stationNo=stno;
	}
	
	//#CM30012
	// Public methods ------------------------------------------------
	//#CM30013
	/**
	 * Creates the communication message "RetrievalTriggerRepetitionRequest".
	 * <p> </p>required to release RetrievalTriggerRepetitionRequest
	 * <ul>
	 * <li>Station No
	 * </ul>
	 * <p> acquires data from the Station No that has been passed in constructor.
	 * </p>
	 * @return	  communication message "RetrievalTriggerRepetitionRequest"
	 * @throws InvalidProtocolException : Notifies if provided value is not following the communication message protocol.
	 */
	public String getSendMessage() throws InvalidProtocolException
	{
		//#CM30014
		// text buffer
		StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT) ;

		//#CM30015
		// Station No.
		mbuf.append(getStationNo());
		//#CM30016
		//-------------------------------------------------
		//#CM30017
		// Setting for the sending message buffer
		//#CM30018
		//-------------------------------------------------
		//#CM30019
		// ID
		setID("47") ;
		//#CM30020
		// ID segment
		setIDClass("00") ;
		//#CM30021
		// MC sending time
		setSendDate() ;
		//#CM30022
		// AGC sending time
		setAGCSendDate("000000") ;
		//#CM30023
		// text contents
		setContent(mbuf.toString()) ;

		return (getFromBuffer(0, LEN_TOTAL+OFF_CONTENT)) ;
	}

	//#CM30024
	/**
	 * Sets station No.
	 * @param str Station No.
	 */
	public void setStationNo(String str)
	{
		stationNo=str;
	}

	//#CM30025
	// Package methods -----------------------------------------------

	//#CM30026
	// Protected methods ---------------------------------------------

	//#CM30027
	// Private methods -----------------------------------------------
	//#CM30028
	/**
	 * Acquires station No. from the communication message "RetrievalTriggerRepetitionRequest"
	 * @return    StationNo
	 * @throws InvalidProtocolException : Reports if station No. is not the allowable length.
	 */
	private String getStationNo() throws InvalidProtocolException
	{
		if(stationNo.length()==LEN_STATION_NO)
		{
			return(stationNo);
		}
		else
		{
			throw new InvalidProtocolException("stationNo="+LEN_STATION_NO+"--->"+stationNo);
		}
	}

}
//#CM30029
//end of class
