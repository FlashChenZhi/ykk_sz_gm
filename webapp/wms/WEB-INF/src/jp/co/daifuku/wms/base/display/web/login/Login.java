// $Id: Login.java,v 1.2 2006/11/07 06:24:17 suresh Exp $
//#CM664630
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web.login;

import jp.co.daifuku.bluedog.ui.control.Page;

//#CM664631
/**
 * An improper, strange class on the login screen. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:24:17 $
 * @author  $Author: suresh $
 */
public class Login extends Page
{

	//#CM664632
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Message1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Message1" , "W_LoginMessage1");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Message2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Message2" , "W_LoginMessage2");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LoginID = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LoginID" , "W_LoginID");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_LoginID = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_LoginID" , "W_LoginID");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "Password");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Login = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Login" , "Login");

}
//#CM664633
//end of class
