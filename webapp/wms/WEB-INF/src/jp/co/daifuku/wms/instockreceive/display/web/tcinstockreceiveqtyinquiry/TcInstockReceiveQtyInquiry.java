// $Id: TcInstockReceiveQtyInquiry.java,v 1.1.1.1 2006/08/17 09:34:13 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.tcinstockreceiveqtyinquiry;
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:13 $
 * @author  $Author: mori $
 */
public class TcInstockReceiveQtyInquiry extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Inquiry = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Inquiry" , "W_Inquiry");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchConsignorCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchConsignorCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StrtInstkAcpDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StrtInstkAcpDate" , "W_StrtInstkAcpDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_StrtInstkAcpDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_StrtInstkAcpDate" , "W_StrtInstkAcpDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchStrtInstkAcpDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchStrtInstkAcpDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromToInstkAcpDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromToInstkAcpDate" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EdInstkAcpDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EdInstkAcpDate" , "W_EdInstkAcpDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_EdInstkAcpDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_EdInstkAcpDate" , "W_EdInstkAcpDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchEdInstkAcpDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchEdInstkAcpDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SupplierCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SupplierCode" , "W_SupplierCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SupplierCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SupplierCode" , "W_SupplierCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchSupplierCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchSupplierCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerCode" , "W_CustomerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerCode" , "W_CustomerCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchCustomerCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchCustomerCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StartTicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StartTicketNo" , "W_StartTicketNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StartTicketNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StartTicketNo" , "W_StartTicketNo");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchStartTicketNo = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchStartTicketNo" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromToTicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromToTicketNo" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EndTicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EndTicketNo" , "W_EndTicketNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_EndTicketNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_EndTicketNo" , "W_EndTicketNo");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchEndTicketNo = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchEndTicketNo" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchItemCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DspOrder = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DspOrder" , "W_DspOrder");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_IsdInstkAcpDate = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_IsdInstkAcpDate" , "W_Isd_InstkAcpDate");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_IsdInstkPlanDate = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_IsdInstkPlanDate" , "W_Isd_InstkPlanDate");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TicTkt = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TicTkt" , "W_Tic_Tkt");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TicItem = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TicItem" , "W_Tic_Item");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_View = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_View" , "View");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Consignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Consignor" , "W_Consignor");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RConsignorCode" , "W_R_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RConsignorName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RConsignorName" , "W_R_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_STcInstkRcvQtyIqr = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_STcInstkRcvQtyIqr" , "W_S_TcInstockReceiveQtyInquiry");

}
//end of class
