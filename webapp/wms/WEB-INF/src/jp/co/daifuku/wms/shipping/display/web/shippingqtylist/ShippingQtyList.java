// $Id: ShippingQtyList.java,v 1.1.1.1 2006/08/17 09:34:29 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.shipping.display.web.shippingqtylist;
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
public class ShippingQtyList extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_ListOutput = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_ListOutput" , "W_ListOutput");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchCnsgnr = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchCnsgnr" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StartShippingDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StartShippingDate" , "W_StartShippingDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_StartShippingDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_StartShippingDate" , "W_StartShippingDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchStrtDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchStrtDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromTo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromTo" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EndShippingDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EndShippingDate" , "W_EndShippingDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_EndShippingDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_EndShippingDate" , "W_EndShippingDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchEdDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchEdDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerCode" , "W_CustomerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerCode" , "W_CustomerCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchCust = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchCust" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TicketNo" , "W_TicketNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TicketNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TicketNo" , "W_TicketNo");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchTkt = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchTkt" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchItem = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchItem" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PDisplay = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PDisplay" , "W_P_Display");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Print = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Print" , "W_Print");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//end of class
