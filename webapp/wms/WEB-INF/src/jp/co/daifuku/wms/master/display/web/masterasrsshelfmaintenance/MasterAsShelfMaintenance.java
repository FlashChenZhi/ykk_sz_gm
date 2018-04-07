// $Id: MasterAsShelfMaintenance.java,v 1.1.1.1 2006/08/17 09:34:17 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterasrsshelfmaintenance;
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
public class MasterAsShelfMaintenance extends Page
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
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_PassWord = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_PassWord" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouse = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouse" , "W_WareHouse");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_WareHouse = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_WareHouse" , "W_WareHouse");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationStatus = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationStatus" , "W_LocationStatus");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_LocationStatus_Empty = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_LocationStatus_Empty" , "W_LocationStatus_Empty");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_LocationStatus_Empty_PB = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_LocationStatus_Empty_PB" , "W_LocationStatus_Empty_PB");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_LocationStatus_Unit = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_LocationStatus_Unit" , "W_LocationStatus_Unit");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationDetail = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationDetail" , "W_LocationDetail");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Query = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Query" , "P_Query");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationNo" , "W_LocationNo");
	public jp.co.daifuku.bluedog.ui.control.FormatTextBox txt_Location = jp.co.daifuku.bluedog.ui.control.FormatTextBoxFactory.getInstance("txt_Location" , "W_F_Location");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_LocationDetails = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_LocationDetails" , "P_LocationDetails");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ProcessFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ProcessFlag" , "W_ProcessFlag");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "Submit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "P_Modify");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "P_Delete");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationNoFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationNoFlag" , "W_LocationNoFlag");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetLocationNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetLocationNo" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetLocationStatus = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetLocationStatus" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSConsignorcode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSConsignorcode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorName" , "W_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorName" , "W_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSItemCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemName" , "W_ItemName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemName" , "W_ItemName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CasePieceFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CasePieceFlag" , "W_CasePieceFlag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Cpf_Case = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Cpf_Case" , "W_Cpf_Case");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Cpf_Piece = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Cpf_Piece" , "W_Cpf_Piece");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Cpf_AppointOff = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Cpf_AppointOff" , "W_Cpf_AppointOff");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseEntering" , "W_CaseEntering");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseEntering = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseEntering" , "W_CaseEntering");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockCaseQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockCaseQty" , "W_StockCaseQty");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StockCaseQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StockCaseQty" , "W_StcCaseQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseItf" , "W_CaseItf");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CaseItf = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CaseItf" , "W_CaseItf");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BundleEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BundleEntering" , "W_BundleEntering");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_BundleEntering = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_BundleEntering" , "W_BundleEntering");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockPieceQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockPieceQty" , "W_StockPieceQty");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StockPieceQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StockPieceQty" , "W_StcPieseQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BundleItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BundleItf" , "W_BundleItf");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BundleItf = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BundleItf" , "W_BundleItf");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageFlag" , "W_StorageFlag");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_StorageFlag = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_StorageFlag" , "W_StorageFlag");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UseByDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UseByDate" , "W_UseByDate");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UseByDate = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UseByDate" , "W_UseByDate");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageDay = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageDay" , "W_StorageDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_StorageDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_StorageDate" , "W_StorageDate");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageTime" , "W_StrageTime");
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_StorageTime = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_StorageTime" , "W_StorageTime");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Setting = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Setting" , "Setting");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//end of class
