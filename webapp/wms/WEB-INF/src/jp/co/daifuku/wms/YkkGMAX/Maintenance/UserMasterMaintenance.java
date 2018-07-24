// $Id: skelten.java,v 1.2 2007/03/07 07:45:22 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Maintenance;
import jp.co.daifuku.bluedog.ui.control.Page;

/**
 * This class can not be changed and it is generated by ScreenGenerator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/03/07 07:45:22 $
 * @author  $Author: suresh $
 */
public class UserMasterMaintenance extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "YKK_LBL_UserMasterMaintenance");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "YKK_TAB_Maintenance");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserID = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserID" , "YKK_LBL_UserID");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Star = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Star" , "YKK_LBL_Star");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UserID = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UserID" , "YKK_TXT_UserID");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_UserIDBrowser = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_UserIDBrowser" , "YKK_BTN_Search_POPUP");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DealDivision = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DealDivision" , "YKK_LBL_DealDivision");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Add = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Add" , "YKK_BTN_Add");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "YKK_BTN_Modify");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "YKK_BTN_Delete");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserID_RO = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserID_RO" , "YKK_LBL_UserID");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UserID_RO = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UserID_RO" , "YKK_TXT_UserID_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserName" , "AST_UserName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UserName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UserName" , "YKK_TXT_UserName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "YKK_LBL_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "YKK_TXT_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Authorization = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Authorization" , "YKK_LBL_Authorization");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Role = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Role" , "YKK_PUL_Role");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "YKK_BTN_Set");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "YKK_BTN_Clear");

}
//end of class
