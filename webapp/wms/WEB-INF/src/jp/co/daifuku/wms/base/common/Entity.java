// $Id: Entity.java,v 1.2 2006/11/07 06:00:10 suresh Exp $
package jp.co.daifuku.wms.base.common;

//#CM642718
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM642719
/**
 * The class which becomes parents of system Object entity. Maintain the handler to keep and to acquire the instance. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:00:10 $
 * @author  $Author: suresh $
 */
public class Entity extends Object
{

	//#CM642720
	// Class fields --------------------------------------------------

	//#CM642721
	// Class variables -----------------------------------------------
	//#CM642722
	/**
	 * Instance handler
	 */
	protected EntityHandler wHandler = null ;

	//#CM642723
	// Class method --------------------------------------------------
	//#CM642724
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:00:10 $") ;
	}

	//#CM642725
	// Constructors --------------------------------------------------

	//#CM642726
	// Public methods ------------------------------------------------

	//#CM642727
	// Package methods -----------------------------------------------

	//#CM642728
	// Protected methods ---------------------------------------------

	//#CM642729
	// Private methods -----------------------------------------------

}
//#CM642730
//end of class


