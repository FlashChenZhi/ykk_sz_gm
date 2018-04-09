// $Id: DefineReportDataRetrieval.java,v 1.2 2006/11/13 08:18:44 suresh Exp $

//#CM690187
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.definereportdata;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM690188
/**
 * Non variable class generated by a tool.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:44 $
 * @author  $Author: suresh $
 */
public class DefineReportDataRetrieval extends Page
{

	//#CM690189
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_RptClmSetRtrivl = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_RptClmSetRtrivl" , "W_RptClm_SetRtrivl");
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
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalPlanDate" , "W_RetrievalPlanDate");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_RtrivlPlanDate = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_RtrivlPlanDate" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RtrivlPlanDateLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RtrivlPlanDateLen" , "W_RtrivlPlanDateLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenRtrivlPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenRtrivlPlanDate" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RtrivlPlanDatePst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RtrivlPlanDatePst" , "W_RtrivlPlanDatePst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemName" , "W_ItemName");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_ItemNm = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_ItemNm" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ItemNmLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ItemNmLen" , "W_ItemNameLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenItemNm = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenItemNm" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ItemNmPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ItemNmPst" , "W_ItemNamePosition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CnsgnrCd = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CnsgnrCd" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CnsgnrCdLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CnsgnrCdLen" , "W_CnsgnrCdLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCnsgnrCd = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCnsgnrCd" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CnsgnrCdPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CnsgnrCdPst" , "W_CnsgnrCdPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RtrivQtyPtl = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RtrivQtyPtl" , "W_RtrivlQtyPtl");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_RtrivlQtyPtl = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_RtrivlQtyPtl" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RtrivlQtyPtlLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RtrivlQtyPtlLen" , "W_RtrvlQtyPtlLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenRtrivlQtyPtl = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenRtrivlQtyPtl" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RtrivlQtyPtlPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RtrivlQtyPtlPst" , "W_RtrvlQtyPtlPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorName" , "W_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CnsgnrNm = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CnsgnrNm" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CnsgnrNmLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CnsgnrNmLen" , "W_CnsgnrNmLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCnsgnrNm = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCnsgnrNm" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CnsgnrNmPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CnsgnrNmPst" , "W_CnsgnrNmPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PiceRtrivlLct = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PiceRtrivlLct" , "W_PiceRtrivlLct");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_PieceRtrivlLct = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_PieceRtrivlLct" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PieceRtrivlLctLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PieceRtrivlLctLen" , "W_PiceRtrivlLctLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenPieceRtrivlLct = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenPieceRtrivlLct" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PieceRtrivlLctPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PieceRtrivlLctPst" , "W_PiceRtrivlLctPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerCode" , "W_CustomerCode");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CustCd = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CustCd" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CustCdLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CustCdLen" , "W_CustCdLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCustCd = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCustCd" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CustCdPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CustCdPst" , "W_CustCdPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseRtrivlLct = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseRtrivlLct" , "W_CaseRtrivlLct");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CaseRtrivlLct = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CaseRtrivlLct" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseRtrivlLctLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseRtrivlLctLen" , "W_CaseRtrivlLctLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCaseRtrivlLct = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCaseRtrivlLct" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseRtrivlLctPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseRtrivlLctPst" , "W_CaseRtrivlLctPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerName" , "W_CustomerName");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CustNm = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CustNm" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CustNmLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CustNmLen" , "W_CustNmLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCustNm = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCustNm" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CustNmPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CustNmPst" , "W_CustNmPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PieceOrderNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PieceOrderNo" , "W_PieceOrderNo");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_PieceOdrNo = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_PieceOdrNo" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PieceOdrNoLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PieceOdrNoLen" , "W_PiceOdrNoLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenPieceOdrNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenPieceOdrNo" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PieceOdrNoPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PieceOdrNoPst" , "W_PiceOdrNoPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TicketNo" , "W_TicketNo");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_TicketNo = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_TicketNo" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_TicketNoLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_TicketNoLen" , "W_TicketNoLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenTicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenTicketNo" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_TicketNoPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_TicketNoPst" , "W_TicketNoPosition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseOrderNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseOrderNo" , "W_CaseOrder");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CaseOdrNo = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CaseOdrNo" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseOdrNoLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseOdrNoLen" , "W_CaseOdrNoLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCaseOdrNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCaseOdrNo" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseOdrNoPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseOdrNoPst" , "W_CaseOdrNoPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TicketLineNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TicketLineNo" , "W_TicketLineNo");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_TicketLineNo = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_TicketLineNo" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_TicketLineNoLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_TicketLineNoLen" , "W_TktLineNoLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenTicketLineNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenTicketLineNo" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_TicketLineNoPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_TicketLineNoPst" , "W_TktLineNoPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ResultPieceQtyTwoByte = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ResultPieceQtyTwoByte" , "W_ResultPieceQtyTwoByte");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_RsltPieceQty = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_RsltPieceQty" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RsltPieceQtyLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RsltPieceQtyLen" , "W_RsltPiceQtyLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenRsltPieceQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenRsltPieceQty" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RsltPieceQtyPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RsltPieceQtyPst" , "W_RsltPiceQtyPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_ItemCd = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_ItemCd" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ItemCdLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ItemCdLen" , "W_ItemCodeLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenItemCd = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenItemCd" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ItemCdPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ItemCdPst" , "W_ItemCodePosition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ResultCaseQtyTwoByte = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ResultCaseQtyTwoByte" , "W_ResultCaseQtyTwoByte");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_RsltCaseQty = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_RsltCaseQty" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RsltCaseQtyLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RsltCaseQtyLen" , "W_RsltCaseQtyLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenRsltCaseQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenRsltCaseQty" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RsltCaseQtyPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RsltCaseQtyPst" , "W_RsltCaseQtyPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BundleItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BundleItf" , "W_BundleItf");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_BundleItf = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_BundleItf" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_BundleItfLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_BundleItfLen" , "W_BundleItfLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenBundleItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenBundleItf" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_BundleItfPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_BundleItfPst" , "W_BundleItfPosition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RtrivlResultDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RtrivlResultDate" , "W_RtrivlRsltDate");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_RtrivlRsltDate = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_RtrivlRsltDate" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RtrivlRsltDateLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RtrivlRsltDateLen" , "W_RtrivlRsltDateLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenRtrivlRsltDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenRtrivlRsltDate" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RtrivlRsltDatePst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RtrivlRsltDatePst" , "W_RtrivlRsltDatePst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseItf" , "W_CaseItf");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CaseItf = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CaseItf" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseItfLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseItfLen" , "W_CaseItfLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCaseItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCaseItf" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseItfPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseItfPst" , "W_CaseItfPosition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ResultFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ResultFlag" , "W_ResultFlag");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_RsltFlg = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_RsltFlg" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RsltFlgLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RsltFlgLen" , "W_ResultFlagLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenRsltFlg = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenRsltFlg" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RsltFlgPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RsltFlgPst" , "W_ResultFlagPosition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BundleEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BundleEntering" , "W_BundleEntering");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_BundleEtr = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_BundleEtr" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_BundleEtrLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_BundleEtrLen" , "W_BdlEtrLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenBundleEtr = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenBundleEtr" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_BundleEtrPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_BundleEtrPst" , "W_BdlEtrPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UseByDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UseByDate" , "W_UseByDate");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_UseByDate = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_UseByDate" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_UseByDateLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_UseByDateLen" , "W_UseByDateLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenUseByDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenUseByDate" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_UseByDatePst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_UseByDatePst" , "W_UseByDatePosition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseEntering" , "W_CaseEntering");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CaseEtr = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CaseEtr" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseEtrLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseEtrLen" , "W_CaseEtrLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenCaseEtr = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenCaseEtr" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseEtrPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseEtrPst" , "W_CaseEtrPst");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "Submit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//#CM690190
//end of class
