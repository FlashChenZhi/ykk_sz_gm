// $Id: DispatchBusiness.java,v 1.2 2006/11/07 06:56:14 suresh Exp $
//#CM664454
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web;
import jp.co.daifuku.bluedog.webapp.ActionEvent;

//#CM664455
/** 
 * The screen class that the tool generated. 
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
public class DispatchBusiness extends Dispatch
{
	//#CM664456
	// Class fields --------------------------------------------------

	//#CM664457
	// Class variables -----------------------------------------------

	//#CM664458
	// Class method --------------------------------------------------

	//#CM664459
	// Constructors --------------------------------------------------

	//#CM664460
	// Public methods ------------------------------------------------

	//#CM664461
	/**
	 * Initialize the screen. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		this.forward(request.getParameter("PATH"));
	}


	public void page_LoginCheck(ActionEvent e) throws Exception
	{
	}

	//#CM664462
	// Package methods -----------------------------------------------

	//#CM664463
	// Protected methods ---------------------------------------------

	//#CM664464
	// Private methods -----------------------------------------------

	//#CM664465
	// Event handler methods -----------------------------------------
}
//#CM664466
//end of class
