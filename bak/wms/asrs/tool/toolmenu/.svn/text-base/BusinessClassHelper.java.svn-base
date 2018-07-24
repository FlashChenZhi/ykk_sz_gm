package jp.co.daifuku.wms.asrs.tool.toolmenu;

import javax.servlet.http.HttpServletRequest;

// $Id: BusinessClassHelper.java,v 1.2 2006/10/30 04:05:42 suresh Exp $

//#CM52515
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM52516
/** <en>
 * The definition of a general-purpose method to use with BusinessClass is done.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:05:42 $
 * @author  $Author: suresh $
 </en> */
public class BusinessClassHelper
{
	//#CM52517
	// Class fields --------------------------------------------------

	//#CM52518
	// Class variables -----------------------------------------------
	//#CM52519
	// Class method --------------------------------------------------
	//#CM52520
	/** <en>
	 * Return version of this Class.
	 * @return version of this Class and Updated date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 04:05:42 $") ;
	}

	//#CM52521
	// Constructors --------------------------------------------------


	//#CM52522
	// Public methods ------------------------------------------------
	//#CM52523
	/** <en>
	 * A pass to the help file is made from the menu function ID and request.
	 * @param functionID 
	 * @param request  HttpServletRequest
	 </en>*/
	public static String getHelpPath(String functionID, HttpServletRequest request)
	{
	 	return request.getContextPath()+"/help/" + request.getLocale().toString()+"/"+functionID + ".pdf";
	}

	//#CM52524
	// Package methods -----------------------------------------------

	//#CM52525
	// Protected methods ---------------------------------------------

	//#CM52526
	// Private methods -----------------------------------------------
}
//#CM52527
//end of class

