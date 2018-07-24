// $Id: OverflowException.java,v 1.2 2006/11/07 06:01:13 suresh Exp $
package jp.co.daifuku.wms.base.common;

//#CM642774
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM642775
/**
 * Exception used in Insert when the overflow is generated.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/10</TD><TD>Y.Kubo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:01:13 $
 * @author  $Author: suresh $
 */
public class OverflowException extends java.lang.Exception
{
	//#CM642776
	// Class fields --------------------------------------------------

	//#CM642777
	// Class variables -----------------------------------------------

	//#CM642778
	// Class method --------------------------------------------------
	//#CM642779
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:01:13 $");
	}

	//#CM642780
	// Constructors --------------------------------------------------
	//#CM642781
	/**
	 * Construct Exception without specifying detailed Message. 
	 */
	public OverflowException()
	{
		super();
	}

	//#CM642782
	/**
	 * Make the message with exception.
	 * @param msg  Detailed Message
	 */
	public OverflowException(String msg)
	{
		super(msg);
	}
}
//#CM642783
//end of class
