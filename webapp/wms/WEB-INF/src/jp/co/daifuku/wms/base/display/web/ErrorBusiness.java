// $Id: ErrorBusiness.java,v 1.2 2006/11/07 06:56:14 suresh Exp $
//#CM664471
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web;
import jp.co.daifuku.bluedog.webapp.ActionEvent;

//#CM664472
/** 
 * The screen class which displays it when the error occurs. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:56:14 $
 * @author  $Author: suresh $
  */
public class ErrorBusiness extends Error
{
	//#CM664473
	// Class fields --------------------------------------------------

	//#CM664474
	// Class variables -----------------------------------------------

	//#CM664475
	// Class method --------------------------------------------------

	//#CM664476
	// Constructors --------------------------------------------------

	//#CM664477
	// Public methods ------------------------------------------------

	//#CM664478
	/** 
	 * Initialize the screen. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
	}

	//#CM664479
	/** 
	 * Override the login check. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void page_LoginCheck(ActionEvent e) throws Exception
	{
	}

	//#CM664480
	// Package methods -----------------------------------------------

	//#CM664481
	// Protected methods ---------------------------------------------

	//#CM664482
	// Private methods -----------------------------------------------

	//#CM664483
	// Event handler methods -----------------------------------------
	public void lbl_ErrorMsg2_Server(ActionEvent e) throws Exception
	{
	}
	public void lbl_ErrorMsg1_Server(ActionEvent e) throws Exception
	{
	}

	public void lbl_Error_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM664484
//end of class
