// $Id: UpdateByOtherTerminalException.java,v 1.2 2006/11/14 06:09:21 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702576
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702577
/**
 * It is previously updated in another Terminal when DB is renewed and use it when it is not possible to update it. 
 * 
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:21 $
 * @author  $Author: suresh $
 */
public class UpdateByOtherTerminalException extends java.lang.Exception
{
	//#CM702578
	// Class fields --------------------------------------------------

	//#CM702579
	// Class variables -----------------------------------------------

	//#CM702580
	// Class method --------------------------------------------------
	//#CM702581
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/14 06:09:21 $") ;
	}

	//#CM702582
	// Constructors --------------------------------------------------
	//#CM702583
	/**
	 * Generate the instance. <BR>
	 * Construct Exception without specifying detailed message. .
	 */
	public UpdateByOtherTerminalException()
	{
		super() ;
	}

	//#CM702584
	/**
	 * Generate the instance. <BR>
	 * Make the exception with the message. 
	 * @param msg  Detailed message
	 */
	public UpdateByOtherTerminalException(String msg)
	{
		super(msg) ;
	}
}
//#CM702585
//end of class
