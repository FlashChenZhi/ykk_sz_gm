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
public class ErroMessageInfo extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "YKK_LBL_ErroMessageInfo");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "YKK_TAB_SearchReport");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DateTimeRange = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DateTimeRange" , "YKK_LBL_DateTimeRange");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Star = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Star" , "YKK_LBL_Star");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DateFrom = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DateFrom" , "YKK_LBL_Date");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_DateFrom = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_DateFrom" , "YKK_TXT_DateFrom");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TimeFrom = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TimeFrom" , "YKK_LBL_Time");
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_TimeFrom = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_TimeFrom" , "YKK_TXT_TimeFrom");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_to = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_to" , "YKK_LBL_to");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DateTo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DateTo" , "YKK_LBL_Date");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_DateTo = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_DateTo" , "YKK_TXT_DateTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TimeTo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TimeTo" , "YKK_LBL_Time");
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_TimeTo = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_TimeTo" , "YKK_TXT_TimeTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MessageDivision = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MessageDivision" , "YKK_LBL_MessageDivision");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_MessageDivision = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_MessageDivision" , "YKK_PUL_ErroMessageDivision");
	public jp.co.daifuku.bluedog.ui.control.SubmitLabel slb_Download = jp.co.daifuku.bluedog.ui.control.SubmitLabelFactory.getInstance("slb_Download" , "W_Download");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Show = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Show" , "YKK_BTN_Show_POPUP");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Print = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Print" , "YKK_BTN_Print");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_CSV = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_CSV" , "YKK_BTN_CSV");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "YKK_BTN_Clear");

}
//end of class
