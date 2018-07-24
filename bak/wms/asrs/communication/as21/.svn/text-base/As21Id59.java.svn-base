// $Id: As21Id59.java,v 1.2 2006/10/26 01:29:00 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30088
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM30089
/**
 * Composes communication message "SystemRecoveryCompletionRequest" ID=59, request for the completion of 
 * system recovery, according to AS21 communication protocol.<BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:29:00 $
 * @author  $Author: suresh $
 */
public class As21Id59 extends SendIdMessage
{
	//#CM30090
	// Class fields --------------------------------------------------
	//#CM30091
	/**
	 * Length of the communication message "SystemRecoveryCompletionRequest"
	 */
	protected final int LEN_TOTAL = OFF_CONTENT;

	//#CM30092
	// Class variables -----------------------------------------------

	//#CM30093
	// Class method --------------------------------------------------
	//#CM30094
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:29:00 $") ;
	}

	//#CM30095
	// Constructors --------------------------------------------------
	//#CM30096
	/**
	 * Default constructor
	 */
	public As21Id59()
	{
		super() ;
	}

	//#CM30097
	// Public methods ------------------------------------------------
	//#CM30098
	/**
	 * Creates the communication message "SystemRecoveryCompletionRequest".
	 * @return    communication message "SystemRecoveryCompletionRequest"
	 */
	public String getSendMessage() 
	{
		//#CM30099
		// text buffer
		StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT) ;

		//#CM30100
		// -------------------------------------------------
		//#CM30101
		//  Setting for the sending message buffer
		//#CM30102
		// -------------------------------------------------
		//#CM30103
		// ID
		setID("59") ;
		//#CM30104
		// ID segment
		setIDClass("00") ;
		//#CM30105
		// MC sending time
		setSendDate() ;
		//#CM30106
		// AGC sending time
		setAGCSendDate("000000") ;
		//#CM30107
		// text contents
		setContent(mbuf.toString()) ;

		return (getFromBuffer(0, LEN_TOTAL)) ;
	}

	//#CM30108
	// Package methods -----------------------------------------------

	//#CM30109
	// Protected methods ---------------------------------------------

	//#CM30110
	// Private methods -----------------------------------------------

}
//#CM30111
// end of class
