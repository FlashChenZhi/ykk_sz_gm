// $Id: LoginCheckBusiness.java,v 1.2 2006/11/07 06:23:31 suresh Exp $
//#CM664672
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web.login;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;

//#CM664673
/**
 * The login check screen class. <BR>
 * The screen displayed when the maximum number of login is exceeded. <BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:23:31 $
 * @author  $Author: suresh $
 */
public class LoginCheckBusiness extends LoginCheck
{
	//#CM664674
	// Class fields --------------------------------------------------

	//#CM664675
	// Class variables -----------------------------------------------

	//#CM664676
	// Class method --------------------------------------------------

	//#CM664677
	// Constructors --------------------------------------------------

	//#CM664678
	// Public methods ------------------------------------------------

	//#CM664679
	/**
	 * Initialize the screen. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		lbl_Msg01.setText(DispResources.getText("LBL-1004", getViewState().getString("USER_ID")));
	}

	//#CM664680
	/**
	 * Override the login check. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void page_LoginCheck(ActionEvent e) throws Exception
	{
	}

	//#CM664681
	// Package methods -----------------------------------------------

	//#CM664682
	// Protected methods ---------------------------------------------

	//#CM664683
	// Private methods -----------------------------------------------

	//#CM664684
	// Event handler methods -----------------------------------------

	//#CM664685
	/**
	 * Processing when "Login With Another User" button is pressed
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void btn_LoginWithAnotherUser_Click(ActionEvent e) throws Exception
	{
		forward("/login/Login.do");
	}

}
//#CM664686
//end of class
