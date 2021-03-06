// $Id: DefineReportDataSorting.java,v 1.2 2006/11/13 08:18:42 suresh Exp $

//#CM691122
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.definereportdata;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM691123
/**
 * Non variable class generated by a tool.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:42 $
 * @author  $Author: suresh $
 */
public class DefineReportDataSorting extends Page
{

	//#CM691124
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_RptClm_SetPick = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_RptClm_SetPick" , "W_RptClm_SetPick");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Back = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Back" , "Back");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Valid1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Valid1" , "W_Valid");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DigitsUseLength1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DigitsUseLength1" , "W_DigitsUseLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLength1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLength1" , "W_MaxLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Position1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Position1" , "W_Position");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Valid2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Valid2" , "W_Valid");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DigitsUseLength2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DigitsUseLength2" , "W_DigitsUseLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLength2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLength2" , "W_MaxLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Position2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Position2" , "W_Position");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PickingPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PickingPlanDate" , "W_PickingPlanDate");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_PickPlanDate = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_PickPlanDate" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PickPlanDateLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PickPlanDateLen" , "W_PickPlanDateLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenPickPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenPickPlanDate" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PickPlanDatePst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PickPlanDatePst" , "W_PickPlanDatePst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PickingQtyPtl = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PickingQtyPtl" , "W_PickingQtyPtl");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_PickQtyPtl = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_PickQtyPtl" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PickQtyPtlLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PickQtyPtlLen" , "W_PickQtyPtlLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenPickQtyPtl = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenPickQtyPtl" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PickQtyPtlPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PickQtyPtlPst" , "W_PickQtyPtlPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CnsgnrCd = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CnsgnrCd" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CnsgnrCdLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CnsgnrCdLen" , "W_CnsgnrCdLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCnsgnrCd = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCnsgnrCd" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CnsgnrCdPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CnsgnrCdPst" , "W_CnsgnrCdPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PiecePickingPlace = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PiecePickingPlace" , "W_PiecePickingPlace");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_PiecePickPlace = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_PiecePickPlace" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PiecePickPlaceLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PiecePickPlaceLen" , "W_PicePickPlaceLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenPiecePickPlace = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenPiecePickPlace" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PiecePickPlacePst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PiecePickPlacePst" , "W_PicePickPlacePst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorName" , "W_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CnsgnrNm = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CnsgnrNm" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CnsgnrNmLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CnsgnrNmLen" , "W_CnsgnrNmLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCnsgnrNm = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCnsgnrNm" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CnsgnrNmPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CnsgnrNmPst" , "W_CnsgnrNmPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CasePickingPlace = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CasePickingPlace" , "W_CasePickingPlace");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CasePickPlace = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CasePickPlace" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CasePickPlaceLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CasePickPlaceLen" , "W_CasePickPlaceLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCasePickPlace = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCasePickPlace" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CasePickPlacePst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CasePickPlacePst" , "W_CasePickPlacePst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerCode" , "W_CustomerCode");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CustCd = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CustCd" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CustCdLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CustCdLen" , "W_CustCdLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCustCd = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCustCd" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CustCdPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CustCdPst" , "W_CustCdPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CrossDCFlg = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CrossDCFlg" , "W_CrossDCFlag");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CrossDCFlg = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CrossDCFlg" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CrossDCFlgLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CrossDCFlgLen" , "W_CrossDCFlagLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCrossDCFlg = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCrossDCFlg" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CrossDCFlgPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CrossDCFlgPst" , "W_CrossDCFlagPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerName" , "W_CustomerName");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CustNm = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CustNm" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CustNmLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CustNmLen" , "W_CustNmLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCustNm = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCustNm" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CustNmPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CustNmPst" , "W_CustNmPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SupplierCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SupplierCode" , "W_SupplierCode");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_SupplierCd = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_SupplierCd" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_SupplierCdLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_SupplierCdLen" , "W_SplCdLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenSupplierCd = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenSupplierCd" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_SupplierCdPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_SupplierCdPst" , "W_SplCdPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ShippingTicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ShippingTicketNo" , "W_ShippingTicketNo");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_ShpTicketNo = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_ShpTicketNo" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ShpTicketNoLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ShpTicketNoLen" , "W_ShpTktNoLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenShpTicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenShpTicketNo" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ShpTicketNoPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ShpTicketNoPst" , "W_ShpTktNoPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SupplierName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SupplierName" , "W_SupplierName");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_SupplierNm = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_SupplierNm" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_SupplierNmLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_SupplierNmLen" , "W_SplNmLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenSupplierNm = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenSupplierNm" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_SupplierNmPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_SupplierNmPst" , "W_SplNmPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ShpTktLineNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ShpTktLineNo" , "W_ShpTktLineNo");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_ShpTicketLineNo = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_ShpTicketLineNo" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ShpTicketLineNoLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ShpTicketLineNoLen" , "W_ShpTktLineNoLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenShpTicketLineNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenShpTicketLineNo" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ShpTicketLineNoPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ShpTicketLineNoPst" , "W_ShpTktLineNoPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InstockTicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InstockTicketNo" , "W_InstockTicketNo");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_InstkTicketNo = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_InstkTicketNo" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_InstkTicketNoLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_InstkTicketNoLen" , "W_InstkTktNoLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenInstkTicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenInstkTicketNo" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_InstkTicketNoPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_InstkTicketNoPst" , "W_InstkTktNoPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_ItemCd = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_ItemCd" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ItemCdLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ItemCdLen" , "W_ItemCodeLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenItemCd = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenItemCd" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ItemCdPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ItemCdPst" , "W_ItemCodePosition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InstkTktLineNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InstkTktLineNo" , "W_InstkTktLineNo");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_InstkTickeLineNo = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_InstkTickeLineNo" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_InstkTickeLineNoLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_InstkTickeLineNoLen" , "W_InstkTktLineNoLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenInstkTickeLineNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenInstkTickeLineNo" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_InstkTickeLineNoPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_InstkTickeLineNoPst" , "W_InstkTktLineNoPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BundleItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BundleItf" , "W_BundleItf");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_BundleItf = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_BundleItf" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_BundleItfLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_BundleItfLen" , "W_BundleItfLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenBundleItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenBundleItf" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_BundleItfPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_BundleItfPst" , "W_BundleItfPosition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ResultPieceQtyTwoByte = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ResultPieceQtyTwoByte" , "W_ResultPieceQtyTwoByte");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_RsltPieceQty = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_RsltPieceQty" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RsltPieceQtyLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RsltPieceQtyLen" , "W_RsltPiceQtyLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenRsltPieceQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenRsltPieceQty" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RsltPieceQtyPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RsltPieceQtyPst" , "W_RsltPiceQtyPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseItf" , "W_CaseItf");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CaseItf = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CaseItf" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseItfLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseItfLen" , "W_CaseItfLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCaseItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCaseItf" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseItfPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseItfPst" , "W_CaseItfPosition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ResultCaseQtyTwoByte = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ResultCaseQtyTwoByte" , "W_ResultCaseQtyTwoByte");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_RsltCaseQty = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_RsltCaseQty" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RsltCaseQtyLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RsltCaseQtyLen" , "W_RsltCaseQtyLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenRsltCaseQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenRsltCaseQty" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RsltCaseQtyPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RsltCaseQtyPst" , "W_RsltCaseQtyPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BundleEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BundleEntering" , "W_BundleEntering");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_BundleEtr = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_BundleEtr" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_BundleEtrLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_BundleEtrLen" , "W_BdlEtrLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenBundleEtr = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenBundleEtr" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_BundleEtrPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_BundleEtrPst" , "W_BdlEtrPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PickingResultDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PickingResultDate" , "W_PickingResultDate");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_PickRsltDate = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_PickRsltDate" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PickRsltDateLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PickRsltDateLen" , "W_PickRsltDateLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenPickRsltDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenPickRsltDate" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PickRsltDatePst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PickRsltDatePst" , "W_PickRsltDatePst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseEntering" , "W_CaseEntering");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CaseEtr = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CaseEtr" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseEtrLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseEtrLen" , "W_CaseEtrLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCaseEtr = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCaseEtr" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseEtrPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseEtrPst" , "W_CaseEtrPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ResultFlg = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ResultFlg" , "W_ResultFlag");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_RsltFlg = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_RsltFlg" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RsltFlgLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RsltFlgLen" , "W_ResultFlagLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenRsltFlg = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenRsltFlg" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ResultFlgPosition = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ResultFlgPosition" , "W_ResultFlagPosition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemName" , "W_ItemName");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_ItemNm = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_ItemNm" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ItemNmLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ItemNmLen" , "W_ItemNameLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenItemNm = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenItemNm" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ItemNmPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ItemNmPst" , "W_ItemNamePosition");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "Submit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//#CM691125
//end of class
