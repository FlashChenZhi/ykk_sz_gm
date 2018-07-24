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
public class ForbidLocationSetting extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "YKK_LBL_ForbidLocationSetting");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "YKK_TAB_Setting");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Bank = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Bank" , "YKK_LBL_Bank");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Bank = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Bank" , "YKK_PUL_Bank");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Show = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Show" , "YKK_BTN_Show_NA");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BayLevel = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BayLevel" , "YKK_LBL_BayLevel");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationNo" , "YKK_LBL_LocationNo");
	public jp.co.daifuku.bluedog.ui.control.FormatTextBox txt_LocationNoFrom = jp.co.daifuku.bluedog.ui.control.FormatTextBoxFactory.getInstance("txt_LocationNoFrom" , "YKK_TXT_LocationNoFrom_NoBank");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_to = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_to" , "YKK_LBL_to");
	public jp.co.daifuku.bluedog.ui.control.FormatTextBox txt_LocationNoTo = jp.co.daifuku.bluedog.ui.control.FormatTextBoxFactory.getInstance("txt_LocationNoTo" , "YKK_TXT_LocationNoTo_NoBank");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_RangeSet = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_RangeSet" , "YKK_CHK_RangeSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BasePoint = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BasePoint" , "YKK_LBL_BasePoint");
	public jp.co.daifuku.bluedog.ui.control.FormatTextBox txt_BasePoint = jp.co.daifuku.bluedog.ui.control.FormatTextBoxFactory.getInstance("txt_BasePoint" , "YKK_TXT_BasePoint");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Show1 = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Show1" , "YKK_BTN_Show_NA");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Status = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Status" , "YKK_LBL_Status");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ForbidLocation = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ForbidLocation" , "YKK_RDO_ForbidLocationChecked");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_UsefulLocation = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_UsefulLocation" , "YKK_RDO_UsefulLocation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "YKK_BTN_Set_NA");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "YKK_BTN_Clear");

}
//end of class
