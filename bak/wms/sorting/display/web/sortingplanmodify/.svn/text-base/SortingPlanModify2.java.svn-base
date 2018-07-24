// $Id: SortingPlanModify2.java,v 1.1.1.1 2006/08/17 09:34:32 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.sortingplanmodify;
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:32 $
 * @author  $Author: mori $
 */
public class SortingPlanModify2 extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_BscDtlMdfyDlt = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_BscDtlMdfyDlt" , "W_Bsc_DtlMdfyDlt");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Back = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Back" , "Back");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Consignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Consignor" , "W_Consignor");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetConsignorCode" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetConsgnorName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetConsgnorName" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PickingPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PickingPlanDate" , "W_PickingPlanDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_FSortingPlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_FSortingPlanDate" , "W_F_Date");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Item = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Item" , "W_Item");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetItemCode" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetItemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetItemName" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseEntering" , "W_CaseEntering");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetCaseEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetCaseEntering" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseItf" , "W_CaseItf");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetCaseItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetCaseItf" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BundleEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BundleEntering" , "W_BundleEntering");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetBundleEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetBundleEntering" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BundleItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BundleItf" , "W_BundleItf");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetBundleItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetBundleItf" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CrossDCTwoByte = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CrossDCTwoByte" , "W_CrossDCTwoByte");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CrossDCFlagDC = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CrossDCFlagDC" , "W_CrossDCFlag_DC");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CrossDCFlagCross = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CrossDCFlagCross" , "W_CrossDCFlag_Cross");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerCode" , "W_CustomerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerCode" , "W_CustomerCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchCustomerCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchCustomerCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerName" , "W_CustomerName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerName" , "W_CustomerName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ShippingTicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ShippingTicketNo" , "W_ShippingTicketNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ShippingTicketNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ShippingTicketNo" , "W_shippingTicketNo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ShpTktLineNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ShpTktLineNo" , "W_ShpTktLineNo");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ShippingTicketLineNo = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ShippingTicketLineNo" , "W_ShpTktLineNo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_HostPlanCaseQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_HostPlanCaseQty" , "W_HostCasePlanQty");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PlanCaseQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PlanCaseQty" , "W_PlanCaseQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_HostPlanPieceQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_HostPlanPieceQty" , "W_HostPiesePlanQty");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PlanPieceQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PlanPieceQty" , "W_PlanPieceQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CasePickingPlace = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CasePickingPlace" , "W_CasePickingPlace");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CasePickingPlace = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CasePickingPlace" , "W_CasePickingPlace");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchCasePickingPlace = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchCasePickingPlace" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PlanPieceQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PlanPieceQty" , "W_PiecePickingPlace");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_PiecePickingPlace = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_PiecePickingPlace" , "W_PiecePickingPlace");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchPiecePickingPlace = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchPiecePickingPlace" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SupplierCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SupplierCode" , "W_SupplierCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SupplierCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SupplierCode" , "W_SupplierCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchSupplierCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchSupplierCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SupplierName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SupplierName" , "W_SupplierName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SupplierName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SupplierName" , "W_SupplierName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InstockTicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InstockTicketNo" , "W_InstockTicketNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_InstockTicketNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_InstockTicketNo" , "W_InstockTicketNo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InstockTicketLineNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InstockTicketLineNo" , "W_InstkTktLineNo");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_InstockTicketLineNo = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_InstockTicketLineNo" , "W_InstkTktLineNo");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Input = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Input" , "W_Input");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ModifySubmit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ModifySubmit" , "W_ModifySubmit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllDelete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllDelete" , "W_AllDelete");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_PickingPlanModify = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_PickingPlanModify" , "W_PickingPlanModify");

}
//end of class
