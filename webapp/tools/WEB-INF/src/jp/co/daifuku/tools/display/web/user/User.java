// $Id: User.java,v 1.1.1.1 2006/08/17 09:33:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.user;
import jp.co.daifuku.bluedog.ui.control.Page;

/** <jp>
 * ユーザ設定の不可変クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 </en> */
public class User extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "T_Configure");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserId" , "T_UserId");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UserId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UserId" , "T_UserId");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Search = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Search" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ProcessingType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ProcessingType" , "T_ProcessingType");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "Submit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "Modify");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "Delete");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserId2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserId2" , "T_UserId");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_UserId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_UserId" , "T_R_UserId");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "T_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "T_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PwdChangeInterval = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PwdChangeInterval" , "T_PwdChangeInterval");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PwdChangeInterval = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PwdChangeInterval" , "T_PwdChangeInterval");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_PwdChangeInterval = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_PwdChangeInterval" , "T_PwdChangeInterval");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PwdExpires = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PwdExpires" , "T_PwdExpires");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_PwdExpires = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_PwdExpires" , "T_PwdExpires");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RoleId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RoleId" , "T_RoleId");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RoleId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RoleId" , "T_RoleId");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Search2 = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Search2" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SameUserLoginMax = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SameUserLoginMax" , "T_SameUserLoginMax");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_SameUserLoginMax = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_SameUserLoginMax" , "T_SameUserLoginMax");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FailedLoginAttempts = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FailedLoginAttempts" , "T_FailedLoginAttempts");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_FailedLoginAttempts = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_FailedLoginAttempts" , "T_FailedLoginAttempts");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserName" , "T_UserName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UserName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UserName" , "T_UserName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Belogning = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Belogning" , "T_Belonging");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Belonging = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Belonging" , "T_Belonging");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BirthDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BirthDate" , "T_BirthDate");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BirthDate = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BirthDate" , "T_BirthDate");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Sex = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Sex" , "T_Sex");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Sex = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Sex" , "T_Sex");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Note = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Note" , "T_Note");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Note = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Note" , "T_Note");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "T_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//end of class
