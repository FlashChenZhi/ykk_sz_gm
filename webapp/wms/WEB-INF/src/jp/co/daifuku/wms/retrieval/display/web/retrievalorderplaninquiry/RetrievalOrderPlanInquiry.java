// $Id: RetrievalOrderPlanInquiry.java,v 1.2 2007/02/07 04:19:22 suresh Exp $

//#CM716202
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalorderplaninquiry;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM716203
/**
 * Non variable class generated by a tool. 
 * 
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:22 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderPlanInquiry extends Page
{

	//#CM716204
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Inquiry = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Inquiry" , "W_Inquiry");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchConsignorCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchConsignorCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StrtRtrivlPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StrtRtrivlPlanDate" , "W_StrtRtrivlPlanDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_StrtRtrivlPlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_StrtRtrivlPlanDate" , "W_StrtRtrivlPlanDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchStrtRtrivlPlanDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchStrtRtrivlPlanDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromTo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromTo" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EdRtrivlPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EdRtrivlPlanDate" , "W_EdRtrivlPlanDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_EdRtrivlPlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_EdRtrivlPlanDate" , "W_EdRtrivlPlanDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchEdRtrivlPlanDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchEdRtrivlPlanDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerCode" , "W_CustomerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerCode" , "W_CustomerCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchCustomerCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchCustomerCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseOrder = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseOrder" , "W_CaseOrder");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CaseOrderNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CaseOrderNo" , "W_CaseOrderNo");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchCaseOrderNo = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchCaseOrderNo" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PieceOrderNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PieceOrderNo" , "W_PieceOrderNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_PieceOrderNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_PieceOrderNo" , "W_PieceOrderNo");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchPieceOrderNo = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchPieceOrderNo" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkStatus = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkStatus" , "W_WorkStatus");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_WkStsRtrivlPlan = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_WkStsRtrivlPlan" , "W_WkStsRtrivlPlan");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_View = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_View" , "View");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Consignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Consignor" , "W_Consignor");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RConsignorCode" , "W_R_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RConsignorName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RConsignorName" , "W_R_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_RetrievalOrderPlanInquir = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_RetrievalOrderPlanInquir" , "W_RetrievalOrderPlanInquiry");

}
//#CM716205
//end of class
