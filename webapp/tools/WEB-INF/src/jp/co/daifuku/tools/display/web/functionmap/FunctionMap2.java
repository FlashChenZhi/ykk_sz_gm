// $Id: FunctionMap2.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.functionmap;
import jp.co.daifuku.bluedog.ui.control.Page;

/** <jp>
 * サブメニューボタン設定2画面目の不可変クラスです。
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
public class FunctionMap2 extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "T_FunctionMap");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Back = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Back" , "Back");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FunctionId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FunctionId" , "T_FunctionId");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_FunctionId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_FunctionId" , "T_In_Item");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FunctionName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FunctionName" , "T_FunctionName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_FunctionName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_FunctionName" , "T_In_Item");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ButtonResouceKey = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ButtonResouceKey" , "T_ButtonResourceKey");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ButtonResourceKey = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ButtonResourceKey" , "T_ButtonResourceKey");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_ButtonResouceKey = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_ButtonResouceKey" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_" , "T_ButtonName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_ButtonName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_ButtonName" , "T_R_ButtonName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PageNameResouceKey = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PageNameResouceKey" , "T_PageNameResourceKey");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_PageNameResouceKey = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_PageNameResouceKey" , "T_PageNameResouceKey");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_PageResouceKey = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_PageResouceKey" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PageName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PageName" , "T_PageName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_PageName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_PageName" , "T_R_PageName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_URI = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_URI" , "T_URI");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_URI = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_URI" , "T_URI");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FrameName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FrameName" , "T_FrameName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FrameName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FrameName" , "T_FrameName");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "T_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_BC_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_BC_Delete" , "T_BC_Delete");

}
//end of class
