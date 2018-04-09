// $Id: As21Id50.java,v 1.2 2006/10/26 01:29:59 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30030
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM30031
/**
 * Composes communication message "Message Data " ID=50 according to AS21 communication protocol.<BR>
 * <FONT SIZE="5" COLOR="RED"> NOT implemented; please be careful.</FONT>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:29:59 $
 * @author  $Author: suresh $
 */
public class As21Id50 extends SendIdMessage
{
	//#CM30032
	// Class fields --------------------------------------------------

	//#CM30033
	// Class variables -----------------------------------------------

	//#CM30034
	// Class method --------------------------------------------------
    //#CM30035
    /**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:29:59 $") ;
	}

	//#CM30036
	// Constructors --------------------------------------------------

	//#CM30037
	// Public methods ------------------------------------------------
	//#CM30038
	/**
	 * Creates the communication message Message Data.
	 * @param <code>messageData</code> Sets data defined by each System.
	 * @return    the communication message "Message Data"
	 */
	public String getAs21Id50 ( String messageData )
	{
		String as21Id50 = "";
		return (as21Id50);
	}

	//#CM30039
	/**
	 * Returns strings.
	 * @return "not yet attendes"
	 */
	public String getSendMessage()
	{
		String value = "not yet attendes";
		return value;
	}

	//#CM30040
	// Package methods -----------------------------------------------

	//#CM30041
	// Protected methods ---------------------------------------------

	//#CM30042
	// Private methods -----------------------------------------------

}
//#CM30043
//end of class

