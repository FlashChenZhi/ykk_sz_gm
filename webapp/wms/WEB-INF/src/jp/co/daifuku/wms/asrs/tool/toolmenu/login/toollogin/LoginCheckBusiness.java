// $Id: LoginCheckBusiness.java,v 1.2 2006/10/30 05:09:48 suresh Exp $
//#CM53742
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.login.toollogin;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;

//#CM53743
/** <en>
 * This is login check screen.
 * It is the screen indicated when it exceeds the number which can login.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:09:48 $
 * @author  $Author: suresh $
 </en> */
public class LoginCheckBusiness extends LoginCheck
{
	//#CM53744
	// Class fields --------------------------------------------------

	//#CM53745
	// Class variables -----------------------------------------------

	//#CM53746
	// Class method --------------------------------------------------

	//#CM53747
	// Constructors --------------------------------------------------

	//#CM53748
	// Public methods ------------------------------------------------

	//#CM53749
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
		lbl_Msg01.setText(DispResources.getText("LBL-1004", getViewState().getString("USER_ID")));
	}

	//#CM53750
	/** <en>
	 * Override the page_LoginCheck method.
	 * @param e ActionEvent
	 </en> */
	public void page_LoginCheck(ActionEvent e) throws Exception
	{
	}

	//#CM53751
	// Package methods -----------------------------------------------

	//#CM53752
	// Protected methods ---------------------------------------------

	//#CM53753
	// Private methods -----------------------------------------------

	//#CM53754
	// Event handler methods -----------------------------------------

	//#CM53755
	/** <en>
	 * The process when a LoginWithAnotherUser button was pushed
	 * @param e ActionEvent
	 </en> */	
	public void btn_LoginWithAnotherUser_Click(ActionEvent e) throws Exception
	{
		forward("/asrs/tool/login/toollogin/Login.do"); // 2006/04/28 Updated By N.Sawa
	}

}
//#CM53756
//end of class
