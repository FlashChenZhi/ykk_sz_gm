// $Id: LoginCheck.java,v 1.2 2006/11/07 06:23:45 suresh Exp $
//#CM664668
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web.login;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM664669
/**
 * A login check screen not changeable class. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:23:45 $
 * @author  $Author: suresh $
 */
public class LoginCheck extends Page
{

	//#CM664670
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Msg01 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Msg01" , "W_In_LoginChkMsg1");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Msg02 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Msg02" , "W_LoginChkMsg2");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_LoginWithAnotherUser = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_LoginWithAnotherUser" , "LoginWithAnotherUser");
	public jp.co.daifuku.bluedog.ui.control.LinkLabel llb_LoginUserList = jp.co.daifuku.bluedog.ui.control.LinkLabelFactory.getInstance("llb_LoginUserList" , "W_LoginUserList");

}
//#CM664671
//end of class
