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
public class Stockout extends Page
{

	// Class variables -----------------------------------------------

	/**
	 * ControlID	lbl_SettingName
	 * TemplateKey	YKK_LBL_StockOut
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "YKK_LBL_StockOut");

	/**
	 * ControlID	message
	 * TemplateKey	OperationMsg
	 * ControlType	Message
	 */
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	rdo_OtherProcedure
	 * TemplateKey	YKK_RDO_OtherProcedure
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_OtherProcedure = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_OtherProcedure" , "YKK_RDO_OtherProcedure");

	/**
	 * ControlID	txt_Section
	 * TemplateKey	YKK_TXT_Section
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Section = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Section" , "YKK_TXT_Section");

	/**
	 * ControlID	txt_Section2
	 * TemplateKey	YKK_TXT_Section
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Section2 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Section2" , "YKK_TXT_Section");

	/**
	 * ControlID	txt_Section3
	 * TemplateKey	YKK_TXT_Section
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Section3 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Section3" , "YKK_TXT_Section");

	/**
	 * ControlID	txt_Section4
	 * TemplateKey	YKK_TXT_Section
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Section4 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Section4" , "YKK_TXT_Section");

	/**
	 * ControlID	lbl_Line
	 * TemplateKey	YKK_LBL_Line
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Line = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Line" , "YKK_LBL_Line");

	/**
	 * ControlID	txt_Line
	 * TemplateKey	YKK_TXT_Line
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Line = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Line" , "YKK_TXT_Line");

	/**
	 * ControlID	rdo_AssemblyLineWorkingProcedu
	 * TemplateKey	YKK_RDO_AssemblyLineWorkingProcedure
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AssemblyLineWorkingProcedu = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AssemblyLineWorkingProcedu" , "YKK_RDO_AssemblyLineWorkingProcedure");

	/**
	 * ControlID	lbl_LineDivision
	 * TemplateKey	YKK_LBL_LineDivision
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LineDivision = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LineDivision" , "YKK_LBL_LineDivision");

	/**
	 * ControlID	txt_LineDivision
	 * TemplateKey	YKK_TXT_LineDivision
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_LineDivision = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_LineDivision" , "YKK_TXT_LineDivision");

	/**
	 * ControlID	rdo_FinalProcedure
	 * TemplateKey	YKK_RDO_FinalProcedure
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FinalProcedure = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FinalProcedure" , "YKK_RDO_FinalProcedure");

	/**
	 * ControlID	btn_Show
	 * TemplateKey	YKK_BTN_Show
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Show = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Show" , "YKK_BTN_Show");

	/**
	 * ControlID	lbl_WhenNextWorkBegin
	 * TemplateKey	YKK_LBL_WhenNextWorkBegin
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WhenNextWorkBegin = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WhenNextWorkBegin" , "YKK_LBL_WhenNextWorkBegin");

	/**
	 * ControlID	txt_WhenNextWorkBegin
	 * TemplateKey	YKK_TXT_WhenNextWorkBegin
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_WhenNextWorkBegin = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_WhenNextWorkBegin" , "YKK_TXT_WhenNextWorkBegin");

	/**
	 * ControlID	pul_AmPm
	 * TemplateKey	YKK_PUL_AmPm
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_AmPm = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_AmPm" , "YKK_PUL_AmPm");

	/**
	 * ControlID	lbl_OrderBy
	 * TemplateKey	YKK_LBL_OrderBy
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_OrderBy = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_OrderBy" , "YKK_LBL_OrderBy");

	/**
	 * ControlID	rdo_WhenNextWorkBegin
	 * TemplateKey	YKK_RDO_WhenNextWorkBegin
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_WhenNextWorkBegin = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_WhenNextWorkBegin" , "YKK_RDO_WhenNextWorkBegin");

	/**
	 * ControlID	rdo_WhenThisWorkFinishInPlan
	 * TemplateKey	YKK_RDO_WhenThisWorkFinishInPlan
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_WhenThisWorkFinishInPlan = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_WhenThisWorkFinishInPlan" , "YKK_RDO_WhenThisWorkFinishInPlan");

	/**
	 * ControlID	rdo_ItemNo
	 * TemplateKey	YKK_RDO_ItemNo
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ItemNo = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ItemNo" , "YKK_RDO_ItemNo");

	/**
	 * ControlID	rdo_RetrievalNo
	 * TemplateKey	YKK_RDO_RetrievalNo
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_RetrievalNo = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_RetrievalNo" , "YKK_RDO_RetrievalNo");

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
	 * ControlID	btn_Set_Up
	 * TemplateKey	YKK_BTN_Set_POPUP
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set_Up = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set_Up" , "YKK_BTN_Set_POPUP");

	/**
	 * ControlID	pgr_Up
	 * TemplateKey	YKK_Pager
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Up = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Up" , "YKK_Pager");

	/**
	 * ControlID	lst_Stockout
	 * TemplateKey	YKK_LST_Stockout
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_Stockout = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_Stockout" , "YKK_LST_Stockout");

	/**
	 * ControlID	pgr_Low
	 * TemplateKey	YKK_Pager
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Low = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Low" , "YKK_Pager");

	/**
	 * ControlID	btn_Set_Low
	 * TemplateKey	YKK_BTN_Set_POPUP
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set_Low = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set_Low" , "YKK_BTN_Set_POPUP");

	/**
	 * ControlID	btn_Delete
	 * TemplateKey	YKK_BTN_DataDelete
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "YKK_BTN_DataDelete");

	/**
	 * ControlID	txt_StockOutDeletionPassword
	 * TemplateKey	YKK_TXT_StockOutDeletionPassword
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StockOutDeletionPassword = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StockOutDeletionPassword" , "YKK_TXT_StockOutDeletionPassword");

	/**
	 * ControlID	chk_DisplayFinishedRetrieval
	 * TemplateKey	YKK_CHK_DisplayFinishedRetrieval
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_DisplayFinishedRetrieval = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_DisplayFinishedRetrieval" , "YKK_CHK_DisplayFinishedRetrieval");

	/**
	 * ControlID	lbl_ShowShortageCondition
	 * TemplateKey	YKK-LBL-ShowShortageCondition
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ShowShortageCondition = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ShowShortageCondition" , "YKK-LBL-ShowShortageCondition");

	/**
	 * ControlID	rdo_ShowAll
	 * TemplateKey	YKK_RDO_ShowAll
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ShowAll = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ShowAll" , "YKK_RDO_ShowAll");

	/**
	 * ControlID	rdo_NoShortage
	 * TemplateKey	YKK_RDO_NoShortage
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_NoShortage = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_NoShortage" , "YKK_RDO_NoShortage");

	/**
	 * ControlID	rdo_ShortageOnly
	 * TemplateKey	YKK_RDO_ShortageOnly
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ShortageOnly = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ShortageOnly" , "YKK_RDO_ShortageOnly");

}
//end of class
