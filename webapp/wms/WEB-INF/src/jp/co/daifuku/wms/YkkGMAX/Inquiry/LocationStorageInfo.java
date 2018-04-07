// $Id: skelten.java,v 1.2 2007/03/07 07:45:22 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Inquiry;
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
public class LocationStorageInfo extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "YKK_LBL_LocationStorageInfo");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "YKK_TAB_SearchReport");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Depository = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Depository" , "YKK_LBL_Depository");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Depository = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Depository" , "YKK_PUL_Depository");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationStatus = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationStatus" , "YKK_LBL_LocationStatus");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_UsedLocation = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_UsedLocation" , "YKK_CHK_UsedLocation");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_BlankLocation = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_BlankLocation" , "YKK_CHK_BlankLocation");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkLocation = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkLocation" , "YKK_CHK_WorkLocation");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_ErroLocation = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_ErroLocation" , "YKK_CHK_ErroLocation");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_ForbidLocation = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_ForbidLocation" , "YKK_CHK_ForbidLocation");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CannotCallLocation = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CannotCallLocation" , "YKK_CHK_CannotCallLocation");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WeightReportFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WeightReportFlag" , "YKK_LBL_WeightReportFlag");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_HaveNotReported = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_HaveNotReported" , "YKK_CHK_HaveNotReported");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Reporting = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Reporting" , "YKK_CHK_Reporting");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_HaveReported = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_HaveReported" , "YKK_CHK_HaveReported");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationNo" , "YKK_LBL_LocationNo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Star = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Star" , "YKK_LBL_Star");
	public jp.co.daifuku.bluedog.ui.control.FormatTextBox txt_LocationNoFrom = jp.co.daifuku.bluedog.ui.control.FormatTextBoxFactory.getInstance("txt_LocationNoFrom" , "YKK_TXT_LocationNoFrom");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_to = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_to" , "YKK_LBL_to");
	public jp.co.daifuku.bluedog.ui.control.FormatTextBox txt_LocationNoTo = jp.co.daifuku.bluedog.ui.control.FormatTextBoxFactory.getInstance("txt_LocationNoTo" , "YKK_TXT_LocationNoTo");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_RangeSet = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_RangeSet" , "YKK_CHK_RangeSet");
	public jp.co.daifuku.bluedog.ui.control.SubmitLabel slb_Download = jp.co.daifuku.bluedog.ui.control.SubmitLabelFactory.getInstance("slb_Download" , "W_Download");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Show = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Show" , "YKK_BTN_Show_POPUP");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Print = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Print" , "YKK_BTN_Print");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_CSV = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_CSV" , "YKK_BTN_CSV");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "YKK_BTN_Clear");

}
//end of class