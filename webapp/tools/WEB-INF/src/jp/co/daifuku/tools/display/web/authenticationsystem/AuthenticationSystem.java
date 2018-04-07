// $Id: AuthenticationSystem.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.authenticationsystem;
import jp.co.daifuku.bluedog.ui.control.Page;

/** <jp>
 * システム設定の不可変クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:10 $
 * @author  $Author: mori $
 </jp> */
/** <en>
 * This invariable class is created by the screen generator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:10 $
 * @author  $Author: mori $
 </en> */
public class AuthenticationSystem extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "T_Configure");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LoginMax = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LoginMax" , "T_LoginMax");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_LoginMax = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_LoginMax" , "T_LoginMax");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Unrestriced = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Unrestriced" , "T_Unrestricted");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SameLoginUser = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SameLoginUser" , "T_SameLoginUser");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Permission = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Permission" , "T_Permission");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Deny = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Deny" , "T_Deny");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RestrictionsPlace = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RestrictionsPlace" , "T_RestrictionsPlace");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_EnableCheck = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_EnableCheck" , "T_EnableCheck");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_DisableCheck = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_DisableCheck" , "T_DisableCheck");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MainMenuType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MainMenuType" , "T_MainMenuType");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_LargeIcon = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_LargeIcon" , "T_LargeIcon");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_SmallIcon = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_SmallIcon" , "T_SmallIcon");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FailedLoginAttempts = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FailedLoginAttempts" , "T_FailedLoginAttempts");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_FailedLoginAttempts = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_FailedLoginAttempts" , "T_FailedLoginAttempts");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FailedTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FailedTime" , "T_FailedTime");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_FailedTime = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_FailedTime" , "T_FailedTime");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Unrestriced2 = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Unrestriced2" , "T_Unrestricted");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "T_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "Cancel");

}
//end of class
