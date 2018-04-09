// $Id: MasterDefineLoadDataMenuItem.java,v 1.1.1.1 2006/08/17 09:34:17 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterdefineloaddatamenu;
import jp.co.daifuku.bluedog.ui.control.Page;

/**
 * ツールが生成した不可変クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:17 $
 * @author  $Author: mori $
 */
public class MasterDefineLoadDataMenuItem extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_LodClmSetPick = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_LodClmSetPick" , "W_LodClm_SetItem");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Back = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Back" , "Back");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ValidLeft1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ValidLeft1" , "W_Valid");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DigitsUseLength1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DigitsUseLength1" , "W_DigitsUseLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLength1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLength1" , "W_MaxLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Position1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Position1" , "W_Position");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCodeReq");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_ConsignorCode = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_ConsignorCode" , "W_CommonNotUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CnsgnrCdLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CnsgnrCdLen" , "W_CnsgnrCdLenReq");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenConsignorCode" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CnsgnrCdPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CnsgnrCdPst" , "W_CnsgnrCdPstReq");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCodeRequired = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCodeRequired" , "W_ItemCodeRequired");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_ItemCodeRequired = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_ItemCodeRequired" , "W_CommonNotUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ItemCodeLength = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ItemCodeLength" , "W_ItemCdReqLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenItemCode" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ItemCodePosition = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ItemCodePosition" , "W_ItemCdReqPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MstItemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MstItemName" , "W_ItemName");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_ItemName = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_ItemName" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ItemNameLength = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ItemNameLength" , "W_ItemNameLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenItemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenItemName" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ItemNamePosition = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ItemNamePosition" , "W_ItemNamePosition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MstCaseEtrReq = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MstCaseEtrReq" , "W_CaseEtrReq");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CaseEnteringReq = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CaseEnteringReq" , "W_CommonNotUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseEtrReqLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseEtrReqLen" , "W_CaseEtrReqLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCaseEtrReq = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCaseEtrReq" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseEtrReqPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseEtrReqPst" , "W_CaseEtrReqPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MstBundleEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MstBundleEntering" , "W_BundleEntering");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_BundleEntering = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_BundleEntering" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_BdlEtrLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_BdlEtrLen" , "W_BdlEtrLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenBdlEtr = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenBdlEtr" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_BdlEtrPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_BdlEtrPst" , "W_BdlEtrPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MstCaseItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MstCaseItf" , "W_CaseItf");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CaseItf = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CaseItf" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseItfLength = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseItfLength" , "W_CaseItfLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCaseItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCaseItf" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseItfPosition = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseItfPosition" , "W_CaseItfPosition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MstBundleItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MstBundleItf" , "W_BundleItf");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_BundleItf = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_BundleItf" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_BundleItfLength = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_BundleItfLength" , "W_BundleItfLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenBundleItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenBundleItf" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_BundleItfPosition = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_BundleItfPosition" , "W_BundleItfPosition");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "Submit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//end of class
