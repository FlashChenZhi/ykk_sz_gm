// $Id: As21Id46.java,v 1.2 2006/10/26 01:30:43 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29958
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM29959
/**
 * Composes communication message "RetrievalTriggerResponse" ID=46 according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:30:43 $
 * @author  $Author: suresh $
 */
public class As21Id46 extends SendIdMessage
{
	//#CM29960
	// Class fields --------------------------------------------------
	//#CM29961
	/**
	 * Length of station No.
	 */
	protected static final int STATION_NO = 4 ;

	//#CM29962
	/**
	 * Length of response classification
	 */
	protected static final int RESPONSE_CLASS = 2 ;

	//#CM29963
	/**
	 * Length of the communication message
	 */
	protected final int LEN_TOTAL=STATION_NO+RESPONSE_CLASS ;

	//#CM29964
	// Class variables -----------------------------------------------
	//#CM29965
	/**
	 * Variable that preserves Station No.
	 */
	private String stationNumber ;

	//#CM29966
	/**
	 * Variable that preserves response classification
	 */
	private String responseClassification ;

	//#CM29967
	/**
	 * Field for the response classification (received as normal status)
	 */
	public static final String NORMAL_RESEPTION = "00";

	//#CM29968
	/**
	 * Field for the response classification (Data Error)
	 */
	public static final String DATA_ERROR = "99";

	//#CM29969
	// Class method --------------------------------------------------
	//#CM29970
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:30:43 $") ;
	}

	//#CM29971
	// Constructors --------------------------------------------------
	//#CM29972
	/**
	 * Default constructor
	 */
	public As21Id46()
	{
		super() ;
	}

	//#CM29973
	/**
	 * Generates the instance of this class by specifying Station No. and the
	 * response classification.
	 * @param sn Station No.
	 * @param rc response classification
	 */
	public As21Id46(String sn, String rc)
	{
		super() ;
		stationNumber = sn;
		responseClassification = rc;
	}

	//#CM29974
	// Public methods ------------------------------------------------
	//#CM29975
	/**
	 * Creates the communication message "RetrievalTriggerResponse".
	 * @return    communication message "RetrievalTriggerResponse"
	 * @throws  InvalidProtocolException :Notifies if provided value is not following the communication message protocol.
	 */
	public String getSendMessage() throws InvalidProtocolException
	{
		//#CM29976
		// text buffer
		StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT) ;

		//#CM29977
		//-------------------------------------------------
		//#CM29978
		// Attention! The order of the following must be observed!
		//#CM29979
		//-------------------------------------------------
		//#CM29980
		// Station No.
		mbuf.append(getStation()) ;
		//#CM29981
		// response classification
		mbuf.append(getResponseClass()) ;

		//#CM29982
		//-------------------------------------------------
		//#CM29983
		// Setting for the sending message buffer
		//#CM29984
		//------------------------------------------------
		//#CM29985
		// ID
		setID("46") ;
		//#CM29986
		// ID segment
		setIDClass("00") ;
		//#CM29987
		// MC sending time
		setSendDate() ;
		//#CM29988
		// AGC sending time
		setAGCSendDate("000000") ;
		//#CM29989
		// text contents
		setContent(mbuf.toString()) ;

		return (getFromBuffer(0, LEN_TOTAL+OFF_CONTENT)) ;
	}
	
	//#CM29990
	/**
	 * Sets station No.
	 * @param st Station No.
	 */
	public void setStation(String st) 
	{	
		stationNumber=st;
	}

	//#CM29991
	/**
	 * Sets response classification.
	 * @param re response classification (true: normal end, false: data error)
	 */
	public void setResponseClassification(boolean re)
	{
		if(re)
		{
			//#CM29992
			//Normal End
			responseClassification=NORMAL_RESEPTION;
		}
		else
		{
			//#CM29993
			//Data Error
			responseClassification=DATA_ERROR;
		}
	}

	//#CM29994
	// Package methods -----------------------------------------------

	//#CM29995
	// Protected methods ---------------------------------------------

	//#CM29996
	// Private methods -----------------------------------------------
	//#CM29997
	/**
	 * Acquires station No.
	 * @return Station No
	 * @throws InvalidProtocolException : Reports if station No. is not the allowable length.
	 */
	private String getStation() throws InvalidProtocolException
	{
		if(stationNumber.length()!=STATION_NO)
		{
			throw new InvalidProtocolException("stationNumber="+STATION_NO+"---->"+stationNumber);
		}
		return(stationNumber);
	}
	//#CM29998
	/**
	 * Acquires response classification.
	 * @return response classification
	 */
	private String getResponseClass()
	{
		return(responseClassification);
	}
	
}
//#CM29999
//end of class
