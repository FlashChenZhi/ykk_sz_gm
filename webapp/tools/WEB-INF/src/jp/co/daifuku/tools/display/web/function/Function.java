// $Id: Function.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.function;
import jp.co.daifuku.bluedog.ui.control.Page;

/** <jp>
 * サブメニュー設定の不可変クラスです。
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
public class Function extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "T_Configure");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MainFunctionId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MainFunctionId" , "T_MainFunctionId");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Search = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Search" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_MainFunctionId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_MainFunctionId" , "T_R_MainFunctionId");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MenuInfoMsg = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MenuInfoMsg" , "T_MenuInfoMsg");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ProcessingType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ProcessingType" , "T_ProcessingType");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "Submit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "Modify");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "Delete");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MainFunctionId2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MainFunctionId2" , "T_MainFunctionId");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_MainFunctionId2 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_MainFunctionId2" , "T_R_MainFunctionId");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FunctionDispNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FunctionDispNumber" , "T_FunctionDispNumber");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_R_FunctionDispNumber = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_R_FunctionDispNumber" , "T_R_FunctionDispNumber");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MenuId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MenuId" , "T_MenuId");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_MenuId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_MenuId" , "T_MenuId");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_MenuId = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_MenuId" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MenuName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MenuName" , "T_MenuName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_MenuName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_MenuName" , "T_R_MenuName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FunctionResourceKey = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FunctionResourceKey" , "T_FunctionResouceKey");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FunctionResourceKey = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FunctionResourceKey" , "T_FunctionResourceKey");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_FunctionResouceKey = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_FunctionResouceKey" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FunctionName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FunctionName" , "T_FunctionName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_FunctionName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_FunctionName" , "T_R_FunctionName");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "T_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//end of class
