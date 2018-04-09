// $Id: As21Id58.java,v 1.2 2006/10/26 01:29:20 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30066
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM30067
/**
 * Composes communication message "SystemRecoveryStartRequest" ID=58, request for the system recovery to 
 * start, according to AS21 communication protocol.<BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:29:20 $
 * @author  $Author: suresh $
 */
public class As21Id58 extends SendIdMessage
{
	//#CM30068
	// Class fields --------------------------------------------------
	//#CM30069
	/**
	 * Length of communication message "SystemRecoveryStartRequest"
	 */
	protected final int LEN_TOTAL = OFF_CONTENT;


	//#CM30070
	// Class variables -----------------------------------------------

	//#CM30071
	// Class method --------------------------------------------------
	//#CM30072
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:29:20 $") ;
	}

	//#CM30073
	// Constructors --------------------------------------------------
	//#CM30074
	/**
	 * Default constructor
	 */
	public As21Id58()
	{
		super() ;
	}

	//#CM30075
	// Public methods ------------------------------------------------
	//#CM30076
	/**
	 * Creates the communication message "SystemRecoveryStartRequest".
	 * @return    communication message "SystemRecoveryStartRequest"
	 */
	public String getSendMessage () 
	{
		//#CM30077
		//-------------------------------------------------
		//#CM30078
		// Setting for the sending message buffer
		//#CM30079
		//-------------------------------------------------
		//#CM30080
		// ID
		setID("58") ;
		//#CM30081
		// ID segment
		setIDClass("00") ;
		//#CM30082
		// MC sending time
		setSendDate() ;
		//#CM30083
		// AGC sending time
		setAGCSendDate("000000") ;

		return (getFromBuffer(0, LEN_TOTAL)) ;
	}

	//#CM30084
	// Package methods -----------------------------------------------

	//#CM30085
	// Protected methods ---------------------------------------------

	//#CM30086
	// Private methods -----------------------------------------------

}
//#CM30087
//end of class

