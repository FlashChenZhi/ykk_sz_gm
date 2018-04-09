// $Id: ShippingQtyInquiry.java,v 1.1.1.1 2006/08/17 09:34:29 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.shipping.display.web.shippingqtyinquiry;
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:29 $
 * @author  $Author: mori $
 */
public class ShippingQtyInquiry extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_W_Inquiry = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_W_Inquiry" , "W_Inquiry");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchCnsgnr = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchCnsgnr" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StartShippingDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StartShippingDate" , "W_StartShippingDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_StartShippingDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_StartShippingDate" , "W_StartShippingDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchStartDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchStartDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromToShpDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromToShpDate" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EndShippingDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EndShippingDate" , "W_EndShippingDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_EndShippingDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_EndShippingDate" , "W_EndShippingDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchEndDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchEndDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerCode" , "W_CustomerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerCode" , "W_CustomerCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchCust = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchCust" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TicketNo" , "W_TicketNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TicketNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TicketNo" , "W_TicketNo");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchTkt = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchTkt" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchItem = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchItem" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DspOrder = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DspOrder" , "W_DspOrder");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_SpdShpDate = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_SpdShpDate" , "W_Spd_ShpDate");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_SpdShpPlanDate = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_SpdShpPlanDate" , "W_Spd_ShpPlanDate");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TicTkt = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TicTkt" , "W_Tic_Tkt");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TicItem = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TicItem" , "W_Tic_Item");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_View = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_View" , "View");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SConsignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SConsignor" , "W_Consignor");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SRConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SRConsignorCode" , "W_R_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SRConsignorName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SRConsignorName" , "W_R_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_SShippingResultInquiry = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_SShippingResultInquiry" , "W_S_ShpRsltIqr");

}
//end of class
