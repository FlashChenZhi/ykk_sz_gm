// $Id: MasterAsNoPlanStorage.java,v 1.1.1.1 2006/08/17 09:34:16 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterasrsnoplanstorage;
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:16 $
 * @author  $Author: mori $
 */
public class MasterAsNoPlanStorage extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Set = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Set" , "W_Set");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PassWord = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PassWord" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouse = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouse" , "W_WareHouse");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_WareHouse = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_WareHouse" , "W_WareHouse");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Zone = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Zone" , "W_Zone");
	public jp.co.daifuku.bluedog.ui.control.LinkedPullDown pul_Zone = jp.co.daifuku.bluedog.ui.control.LinkedPullDownFactory.getInstance("pul_Zone" , "W_Zone");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkPlace = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkPlace" , "W_WorkPlace");
	public jp.co.daifuku.bluedog.ui.control.LinkedPullDown pul_WorkPlace = jp.co.daifuku.bluedog.ui.control.LinkedPullDownFactory.getInstance("pul_WorkPlace" , "W_WorkPlace");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Station = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Station" , "W_Station");
	public jp.co.daifuku.bluedog.ui.control.LinkedPullDown pul_Station = jp.co.daifuku.bluedog.ui.control.LinkedPullDownFactory.getInstance("pul_Station" , "W_Station");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchConsignorCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchConsignorCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorName" , "W_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorName" , "W_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchItemCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemName" , "W_ItemName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemName" , "W_ItemName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CasePieceFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CasePieceFlag" , "W_CasePieceFlag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Cpf_Case = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Cpf_Case" , "W_Cpf_Case");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Cpf_Piece = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Cpf_Piece" , "W_Cpf_Piece");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Cpf_AppointOff = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Cpf_AppointOff" , "W_Cpf_AppointOff");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseEntering" , "W_CaseEntering");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseEntering = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseEntering" , "W_CaseEntering");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageCaseQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageCaseQty" , "W_StrageCaseQty");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StrgCaseQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StrgCaseQty" , "W_StrgCaseQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseItf" , "W_CaseItf");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CaseItf = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CaseItf" , "W_CaseItf");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BundleEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BundleEntering" , "W_BundleEntering");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PieceEntering = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PieceEntering" , "W_BundleEntering");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StoragePieceQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StoragePieceQty" , "W_StragePieseQty");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StrgPieceQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StrgPieceQty" , "W_StrgPieseQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BundleItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BundleItf" , "W_BundleItf");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BundleItf = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BundleItf" , "W_BundleItf");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UseByDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UseByDate" , "W_UseByDate");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UseByDate = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UseByDate" , "W_UseByDate");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Unschstwoli = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Unschstwoli" , "W_Unschstwoli");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CommonUse = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CommonUse" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Input = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Input" , "W_Input");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_StorageStart = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_StorageStart" , "W_StorageStart");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllCancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllCancel" , "W_ListClear");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_NoPlanStorage = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_NoPlanStorage" , "W_NoPlanStorage");

}
//end of class
