// $Id: ReceiveIdMessage.java,v 1.2 2006/10/26 00:55:54 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM31380
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM31381
/**
 * This is the superclass which retrieves the common parts of each communication message
 * according to AS21 communicaiton protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 00:55:54 $
 * @author  $Author: suresh $
 */
public abstract class ReceiveIdMessage extends IdMessage
{
	//#CM31382
	// Class fields --------------------------------------------------
	//#CM31383
	/**
	 * This is the definition for identifying the invalid MCkeys.
	 */
	protected static final String NULL_MC_KEY = "00000000" ;

	//#CM31384
	// Class variables -----------------------------------------------

	//#CM31385
	// Class method --------------------------------------------------
	//#CM31386
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 00:55:54 $") ;
	}

	//#CM31387
	// Constructors --------------------------------------------------
	//#CM31388
	/**
	 * Create instance. Internal buffer can be cleared by ' '.
	 */
	public ReceiveIdMessage()
	{
		super() ;
	}

	//#CM31389
	// Public methods ------------------------------------------------
	//#CM31390
	/**
	 * Set the communication message received from AGC to the internal buffer
	 * @param  rmsg   communication message
	 */
	protected abstract void setReceiveMessage(byte[] rmsg) ;

	//#CM31391
	// Package methods -----------------------------------------------

	//#CM31392
	// Protected methods ---------------------------------------------

	//#CM31393
	// Private methods -----------------------------------------------

}
//#CM31394
//end of class

