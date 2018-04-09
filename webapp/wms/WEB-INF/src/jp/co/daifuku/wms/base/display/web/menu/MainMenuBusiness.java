// $Id: MainMenuBusiness.java,v 1.2 2006/11/07 06:20:44 suresh Exp $

//#CM664765
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web.menu;
import jp.co.daifuku.bluedog.webapp.ActionEvent;

//#CM664766
/** 
 * Loading the main menu. Use MainMenuA.jsp and MainMenuB.jsp together. <br>
 * <i>TSG manages this source. Contact TSG when you need correcting. <i>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:20:44 $
 * @author  $Author: suresh $
 */
public class MainMenuBusiness extends MainMenu
{
	//#CM664767
	// Class fields --------------------------------------------------

	//#CM664768
	// Class variables -----------------------------------------------

	//#CM664769
	// Class method --------------------------------------------------

	//#CM664770
	// Constructors --------------------------------------------------

	//#CM664771
	// Public methods ------------------------------------------------

	//#CM664772
	/** 
	 * Initialize the screen. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
	}

	//#CM664773
	/** 
	 * Override the method of the base class <code>Page</code>. <br>
	 * The reason is to change to ErrorPage without calling the processing of btn_Logout_Click() 
	 * in case of the session time-out when the btn_Logout_Click() event is generated 
	 * though nothing is done in this. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void page_LoginCheck(ActionEvent e) throws Exception
	{
	}

	//#CM664774
	// Package methods -----------------------------------------------

	//#CM664775
	// Protected methods ---------------------------------------------

	//#CM664776
	// Private methods -----------------------------------------------

	//#CM664777
	// Event handler methods -----------------------------------------

	//#CM664778
	/** 
	 * Unused
	 * @param e
	 * @throws Exception
	 */
	public void btn_Logout_Server(ActionEvent e) throws Exception
	{
	}

	//#CM664779
	/** 
	 * The event when the logout button is clicked. <br>
	 * 1. User information is deleted. <br>
	 * 2. Print Session.invalidate() method.<br>
	 * 3. Changes to the login screen. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void btn_Logout_Click(ActionEvent e) throws Exception
	{
		removeUserInfo();
		session.invalidate();
		forward("/jsp/login/Logout.jsp");
	}

}
//#CM664780
//end of class
