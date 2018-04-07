// $Id: skelten.java,v 1.2 2007/03/07 07:45:22 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Stockout;
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
public class PRStockout extends Page
{

	// Class variables -----------------------------------------------

	/**
	 * ControlID	lbl_SettingName
	 * TemplateKey	In_SettingName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");

	/**
	 * ControlID	btn_Help
	 * TemplateKey	Help
	 * ControlType	LinkButton
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");

	/**
	 * ControlID	message
	 * TemplateKey	OperationMsg
	 * ControlType	Message
	 */
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");

	/**
	 * ControlID	tab
	 * TemplateKey	YKK_TAB_Setting
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "YKK_TAB_Setting");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_PRNo
	 * TemplateKey	YKK-LBL-PRNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PRNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PRNo" , "YKK-LBL-PRNo");

	/**
	 * ControlID	txt_PRNo
	 * TemplateKey	YKK_TXT_PRNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_PRNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_PRNo" , "YKK_TXT_PRNo");

	/**
	 * ControlID	lbl_Status
	 * TemplateKey	YKK_LBL_Status
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Status = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Status" , "YKK_LBL_Status");

	/**
	 * ControlID	txt_Status
	 * TemplateKey	YKK_TXT_Status_RO
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Status = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Status" , "YKK_TXT_Status_RO");

	/**
	 * ControlID	lbl_PRCount
	 * TemplateKey	YKK-LBL-PRCount
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PRCount = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PRCount" , "YKK-LBL-PRCount");

	/**
	 * ControlID	lbl_SuccessCount
	 * TemplateKey	YKK-LBL-SuccessCount
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SuccessCount = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SuccessCount" , "YKK-LBL-SuccessCount");

	/**
	 * ControlID	txt_SuccessCount
	 * TemplateKey	YKK_TXT_SuccessCount_RO
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_SuccessCount = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_SuccessCount" , "YKK_TXT_SuccessCount_RO");

	/**
	 * ControlID	lbl_NotExistsCount
	 * TemplateKey	YKK-LBL-NotExistsCount
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_NotExistsCount = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_NotExistsCount" , "YKK-LBL-NotExistsCount");

	/**
	 * ControlID	txt_NotExistsCount
	 * TemplateKey	YKK_TXT_NotExistsCount_RO
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_NotExistsCount = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_NotExistsCount" , "YKK_TXT_NotExistsCount_RO");

	/**
	 * ControlID	lbl_RetrievaledCount
	 * TemplateKey	YKK-LBL-RetrievaledCount
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievaledCount = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievaledCount" , "YKK-LBL-RetrievaledCount");

	/**
	 * ControlID	txt_RetrievaledCount
	 * TemplateKey	YKK_TXT_RetrievaledCount_RO
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RetrievaledCount = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RetrievaledCount" , "YKK_TXT_RetrievaledCount_RO");

	/**
	 * ControlID	lbl_ShortageCount
	 * TemplateKey	YKK-LBL-ShortageCount
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ShortageCount = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ShortageCount" , "YKK-LBL-ShortageCount");

	/**
	 * ControlID	txt_ShortageCount
	 * TemplateKey	YKK_TXT_ShortageCount_RO
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ShortageCount = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ShortageCount" , "YKK_TXT_ShortageCount_RO");

	/**
	 * ControlID	lbl_QtyNotMatchCount
	 * TemplateKey	YKK-LBL-QtyNotMatchCount
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_QtyNotMatchCount = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_QtyNotMatchCount" , "YKK-LBL-QtyNotMatchCount");

	/**
	 * ControlID	txt_QtyNotMatchCount
	 * TemplateKey	YKK_TXT_QtyNotMatchCount_RO
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_QtyNotMatchCount = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_QtyNotMatchCount" , "YKK_TXT_QtyNotMatchCount_RO");

	/**
	 * ControlID	btn_Input
	 * TemplateKey	YKK_BTN_Input
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Input = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Input" , "YKK_BTN_Input");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	YKK_BTN_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "YKK_BTN_Clear");

	/**
	 * ControlID	btn_Set
	 * TemplateKey	YKK_BTN_Set
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "YKK_BTN_Set");

	/**
	 * ControlID	btn_SelectAll
	 * TemplateKey	YKK_BTN_SelectAll
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SelectAll = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SelectAll" , "YKK_BTN_SelectAll");

	/**
	 * ControlID	btn_UnselectAll
	 * TemplateKey	YKK_BTN_UnselectAll
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_UnselectAll = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_UnselectAll" , "YKK_BTN_UnselectAll");

	/**
	 * ControlID	btn_CancelData
	 * TemplateKey	YKK_BTN_CancelData
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_CancelData = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_CancelData" , "YKK_BTN_CancelData");

	/**
	 * ControlID	lbl_Subdivision
	 * TemplateKey	YKK_LBL_Subdivision
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Subdivision = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Subdivision" , "YKK_LBL_Subdivision");

	/**
	 * ControlID	pul_Subdivision
	 * TemplateKey	YKK_PUL_Subdivision
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Subdivision = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Subdivision" , "YKK_PUL_Subdivision");

	/**
	 * ControlID	lst_PRStockout
	 * TemplateKey	YKK_LST_PrStockout
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_PRStockout = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_PRStockout" , "YKK_LST_PrStockout");

}
//end of class