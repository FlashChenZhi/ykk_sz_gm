// $Id: As21Id41.java,v 1.2 2006/10/26 01:31:48 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM29832
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

//#CM29833
/**
 * Composes communication message "WorkModeChangeRequestResponse" (response to the request of changing work mode)
 * ID=41, according to AS21communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:31:48 $
 * @author  $Author: suresh $
 */
public class As21Id41 extends SendIdMessage
{
	//#CM29834
	// Class fields --------------------------------------------------
	//#CM29835
	/**
	 * Field of response classification (normaly received)
	 */
	public static final String GENERAL_RESEPTION = "00";

	//#CM29836
	/**
	 * Field of response classification (Error (working))
	 */
	public static final String ERROR_WORKING = "01";

	//#CM29837
	/**
	 * Field of response classification(Error (Station No.))
	 */
	public static final String ERROR_STATION_NO = "02";
	
	//#CM29838
	/**
	 * Length of the station No.
	 */
	protected static final int STATION_NUMBER = 4;
	
	//#CM29839
	/**
	 * Length of the response classification
	 */
	protected static final int REQUEST_CLASSIFICATION = 2;
	
	//#CM29840
	/**
	 * Length of data for the communication message "WorkModeChangeRequestResponse"
	 */
	protected final int LEN_TOTAL = STATION_NUMBER
								+ REQUEST_CLASSIFICATION;
	
	//#CM29841
	// Class variables -----------------------------------------------
	//#CM29842
	/**
	 * Variable which preserves the station No.
	 */
	private String wStationNo;

	//#CM29843
	/**
	 * Variable which preserves the response classification
	 */
	private String wReqinfo;
	
	//#CM29844
	// Class method --------------------------------------------------
	//#CM29845
	/**
	 * Returns the version of this class.
	 * return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/10/26 01:31:48 $");
	}

	//#CM29846
	// Constructors --------------------------------------------------
	//#CM29847
	/**
	 * Default constructor
	 */
	public As21Id41 ()
	{
		super();
	}
	
	//#CM29848
	// Public methods ------------------------------------------------
	//#CM29849
	/**
	 * Creates the communication message "WorkModeChangeRequestResponse".
	 * @return    the communication message "WorkModeChangeRequestResponse"
	 * @throws  InvalidProtocolException Notifies if the value used is not following the communication message protocol.
	 */
	public String getSendMessage() throws InvalidProtocolException
	{
		//#CM29850
		// text buffer
		StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT) ;

		//#CM29851
		//-------------------------------------------------
		//#CM29852
		// Attention! the order of following must be observed!
		//#CM29853
		//-------------------------------------------------
		//#CM29854
		// Station No.
		mbuf.append(wStationNo);
		//#CM29855
		// Response classification
		mbuf.append(wReqinfo);
		//#CM29856
		//-------------------------------------------------
		//#CM29857
		// Setting for sending message buffer
		//#CM29858
		//-------------------------------------------------
		//#CM29859
		// ID
		setID("41");
		//#CM29860
		// ID segment
		setIDClass("00");
		//#CM29861
		// MC sending time
		setSendDate();
		//#CM29862
		// AGC sending time
		setAGCSendDate();
		//#CM29863
		// text content
		setContent(mbuf.toString());
		
		return (getFromBuffer(0, LEN_TOTAL+OFF_CONTENT));
	}

	//#CM29864
	// Package methods -----------------------------------------------

	//#CM29865
	// Protected methods ---------------------------------------------

	//#CM29866
	// Private methods -----------------------------------------------
	//#CM29867
	/**
	 * Setse Station No.
	 * @param stationno StationNo.
	 * @throws InvalidProtocolException : Reports if Station No. is not the allowable length.
	 */
	public void setStationNo(String stationno) throws InvalidProtocolException
	{
		wStationNo = stationno;
		//#CM29868
		// Checks the length of String 
		if(wStationNo.length() != STATION_NUMBER)
		{
			throw new InvalidProtocolException("\n" + "Station No. = " + STATION_NUMBER + "--->" + wStationNo);
		}
	}

	//#CM29869
	/**
	 * Sets the response classification
	 * @param requestclass response classification
	 * @throws InvalidProtocolException : Reports if entered response classification is invalid.
	 */
	public void setRequestClass(String requestclass) throws InvalidProtocolException
	{
		wReqinfo = requestclass;
		if(wReqinfo == GENERAL_RESEPTION)
		{
			//#CM29870
			// normal receipt
		}
		else if(wReqinfo == ERROR_WORKING)
		{
			//#CM29871
			// Error (under working)
		}
		else if(wReqinfo == ERROR_STATION_NO)
		{
			//#CM29872
			// Error (Station No.)
		}
		else
		{
			throw new InvalidProtocolException("\n" + "Exception. Response classification = " + wReqinfo) ;
		}
	}
}
//#CM29873
//end of class

