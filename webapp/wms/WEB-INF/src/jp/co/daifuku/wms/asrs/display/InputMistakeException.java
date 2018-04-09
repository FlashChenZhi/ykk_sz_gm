// $Id: InputMistakeException.java,v 1.2 2006/10/26 03:10:43 suresh Exp $
package jp.co.daifuku.wms.asrs.display ;

//#CM34542
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM34543
/**
 * This is an exception which is used when incorrect data is entered on the screen.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/03/14</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 03:10:43 $
 * @author  $Author: suresh $
 */
public class InputMistakeException extends java.lang.Exception
{
	//#CM34544
	// Class fields --------------------------------------------------

	//#CM34545
	// Class variables -----------------------------------------------

	//#CM34546
	// Class method --------------------------------------------------
	//#CM34547
	/**
	 * Return the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 03:10:43 $") ;
	}

	//#CM34548
	// Constructors --------------------------------------------------
	//#CM34549
	/**
	 * Construct the exception without specifing the detail message.
	 */
	public InputMistakeException()
	{
		super() ;
	}

	//#CM34550
	/**
	 * Create the exception along with the message.
	 * @param msg  :detail message
	 */
	public InputMistakeException(String msg)
	{
		super(msg) ;
	}
}
//#CM34551
//end of class

