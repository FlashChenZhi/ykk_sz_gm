// $Id: ChangePassword.java,v 1.2 2006/10/30 05:09:50 suresh Exp $
//#CM53659
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.login.toollogin;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM53660
/** <en>
 * This is an invariable class is created by the screen generator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:09:50 $
 * @author  $Author: suresh $
 </en> */
public class ChangePassword extends Page
{

	//#CM53661
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Msg0 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Msg0" , "In_ErrorJsp");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_msg1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_msg1" , "ChangePasswordMsg1");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_msg2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_msg2" , "ChangePasswordMsg2");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_OldPassword = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_OldPassword" , "OldPassword");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_OldPassword = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_OldPassword" , "OldPassword");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_NewPassword = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_NewPassword" , "NewPassword");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_NewPassword = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_NewPassword" , "NewPassword");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ReenterPassword = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ReenterPassword" , "ReenterPassword");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ReenterPassword = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ReenterPassword" , "ReenterPassword");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "Modify");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Next = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Next" , "Next");

}
//#CM53662
//end of class
