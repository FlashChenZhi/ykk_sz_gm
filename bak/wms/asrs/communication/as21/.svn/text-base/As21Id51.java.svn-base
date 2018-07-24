// $Id: As21Id51.java,v 1.2 2006/10/26 01:29:39 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30044
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM30045
/**
 * Composes communication message "AccessImpossibleLocationRequest" ID=51, request for the information of 
 * locations which cannnot be accessed, according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:29:39 $
 * @author  $Author: suresh $
 */
public class As21Id51 extends SendIdMessage
{
	//#CM30046
	// Class fields --------------------------------------------------
	//#CM30047
	/**
	 * Length of the communication message "AccessImpossibleLocationRequest"
	 */
	protected final int LEN_TOTAL = OFF_CONTENT;

	//#CM30048
	// Class variables -----------------------------------------------

	//#CM30049
	// Class method --------------------------------------------------
	//#CM30050
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:29:39 $") ;
	}

	//#CM30051
	// Constructors --------------------------------------------------
	//#CM30052
	/**
	 * Default constructor
	 */
	public As21Id51()
	{
		super() ;
	}

	//#CM30053
	// Public methods ------------------------------------------------
	//#CM30054
	/**
	 * Creates the communication message "AccessImpossibleLocationRequest".
	 * @return    the communication message "AccessImpossibleLocationRequest"
	 */
	public String getSendMessage()
	{
		//#CM30055
		//-------------------------------------------------
		//#CM30056
		// Setting for the sending message buffer
		//#CM30057
		//-------------------------------------------------
		//#CM30058
		// ID
		setID("51") ;
		//#CM30059
		// ID segment
		setIDClass("00") ;
		//#CM30060
		// MC sending time
		setSendDate() ;
		//#CM30061
		// AGC sending time
		setAGCSendDate("000000") ;

		return (getFromBuffer(0, LEN_TOTAL)) ;
	}


	//#CM30062
	// Package methods -----------------------------------------------

	//#CM30063
	// Protected methods ---------------------------------------------

	//#CM30064
	// Private methods -----------------------------------------------

}
//#CM30065
//end of class

