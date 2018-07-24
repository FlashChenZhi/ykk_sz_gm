// $Id: AllocateException.java,v 1.2 2006/11/14 06:08:55 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM700007
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM700008
/**
 * Use it when the drawing qty is insufficient because of the object stock while drawing the stock. 
 * 
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:08:55 $
 * @author  $Author: suresh $
 */
public class AllocateException extends java.lang.Exception
{
	//#CM700009
	// Class fields --------------------------------------------------

	//#CM700010
	// Class variables -----------------------------------------------

	//#CM700011
	// Class method --------------------------------------------------
	//#CM700012
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/14 06:08:55 $") ;
	}

	//#CM700013
	// Constructors --------------------------------------------------
	//#CM700014
	/**
	 * Generate the instance. <BR>
	 * Construct Exception without specifying detailed message. 
	 */
	public AllocateException()
	{
		super() ;
	}

	//#CM700015
	/**
	 * Generate the instance. <BR>
	 * Make the exception with the message. 
	 * @param msg  Detailed message
	 */
	public AllocateException(String msg)
	{
		super(msg) ;
	}
	//#CM700016
	// Public methods ------------------------------------------------
	//#CM700017
	// Class method --------------------------------------------------
	//#CM700018
	// Protected methods ---------------------------------------------
	//#CM700019
	// Private methods -----------------------------------------------
}
//#CM700020
//end of class

