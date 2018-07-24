// $Id: LogoutBusiness.java,v 1.2 2006/10/30 05:05:26 suresh Exp $
//#CM53599
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.login;
import jp.co.daifuku.bluedog.webapp.ActionEvent;


//#CM53600
/**
 * Class of the set tool logout screen. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:05:26 $
 * @author  $Author: suresh $
 */
public class LogoutBusiness extends Logout
{
	//#CM53601
	// Class fields --------------------------------------------------

	//#CM53602
	// Class variables -----------------------------------------------

	//#CM53603
	// Class method --------------------------------------------------

	//#CM53604
	// Constructors --------------------------------------------------

	//#CM53605
	// Public methods ------------------------------------------------

	//#CM53606
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
	}

	//#CM53607
	// Package methods -----------------------------------------------

	//#CM53608
	// Protected methods ---------------------------------------------

	//#CM53609
	// Private methods -----------------------------------------------

	//#CM53610
	// Event handler methods -----------------------------------------

	//#CM53611
	/**
	 * It is called when the logout button is pressed.
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Logout_Click(ActionEvent e) throws Exception
	{
		removeUserInfo();
		session.invalidate();
		forward("/jsp/asrs/tool/login/openlogin.html");
	}
	//#CM53612
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Logout_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM53613
//end of class
