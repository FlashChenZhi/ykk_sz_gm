// $Id: BusinessClassHelper.java,v 1.2 2006/11/07 06:56:15 suresh Exp $

//#CM664436
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web;

import javax.servlet.http.HttpServletRequest;

//#CM664437
/** 
 * Use with BusinessClass, and define a general method. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/07/31</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:56:15 $
 * @author  $Author: suresh $
  */
public class BusinessClassHelper
{
	//#CM664438
	// Class fields --------------------------------------------------

	//#CM664439
	// Class variables -----------------------------------------------

	//#CM664440
	// Class method --------------------------------------------------
	//#CM664441
	/** 
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:56:15 $") ;
	}

	//#CM664442
	/** 
	 * Make Path to the Help file based on function ID and request of the menu. 
	 * @param functionID Function ID of menu
	 * @param request  HttpServletRequest
	 * @return Path to Help file
	 */
	public static String getHelpPath(String functionID, HttpServletRequest request)
	{
	 	return (request.getContextPath() + "/help/" + request.getLocale().toString() + "/" + functionID + ".pdf");
	}

	//#CM664443
	/** 
	 * Make Path to the submenu based on menu ID. <br>
	 * <code>HttpServletRequest</code> need not be received by the argument because 
	 * ContextPath is added at forward. 
	 * @param menuID Menu ID path to submenu
	 * @return Path to submenu
	 */
	public static String getSubMenuPath(String menuID)
	{
		return ("/menu/SubMenu.do?id=" + menuID);
	}

	//#CM664444
	// Constructors --------------------------------------------------

	//#CM664445
	// Public methods ------------------------------------------------

	//#CM664446
	// Package methods -----------------------------------------------

	//#CM664447
	// Protected methods ---------------------------------------------

	//#CM664448
	// Private methods -----------------------------------------------
}
//#CM664449
//end of class
