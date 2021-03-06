// $Id: skelten.java,v 1.2 2007/03/07 07:45:22 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Stockout;
import jp.co.daifuku.bluedog.ui.control.Page;

/**
 * This class can not be changed and it is generated by ScreenGenerator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/03/07 07:45:22 $
 * @author  $Author: suresh $
 */
public class FlatStockoutPopup extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ListName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ListName" , "YKK_LBL_FlatStockout");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close" , "YKK_BTN_Close");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemNo" , "YKK_LBL_ItemNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemNo" , "YKK_TXT_ItemNo_RO");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemName1 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemName1" , "YKK_TXT_ItemName1_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ColorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ColorCode" , "YKK_LBL_ColorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ColorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ColorCode" , "YKK_TXT_ColorCode_RO");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemName2 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemName2" , "YKK_TXT_ItemName2_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Section = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Section" , "YKK_LBL_Section");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Section = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Section" , "YKK_TXT_Section_RO");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemName3 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemName3" , "YKK_TXT_ItemName3_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WhenNextWorkBegin = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WhenNextWorkBegin" , "YKK_LBL_WhenNextWorkBegin");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_WhenNextWorkBegin = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_WhenNextWorkBegin" , "YKK_TXT_WhenNextWorkBegin_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockoutNecessaryQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockoutNecessaryQty" , "YKK_LBL_StockoutNecessaryQty");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StockoutNecessaryQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StockoutNecessaryQty" , "YKK_TXT_StockoutNecessaryQty_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ManagementRetrievalQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ManagementRetrievalQty" , "YKK_LBL_ManagementRetrievalQty_RO");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ManagementRetrievalQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ManagementRetrievalQty" , "YKK_TXT_ManagementRetrievalQty_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockoutCount = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockoutCount" , "YKK_LBL_StockoutCount");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StockoutCount = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StockoutCount" , "YKK_TXT_StockoutCount_RO");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Up = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Up" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Show = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Show" , "YKK_BTN_Show");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_FlatStockoutPopup_Up = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_FlatStockoutPopup_Up" , "YKK_LST_FlatStockoutPopup_Up");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Low = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Low" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Input_1 = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Input_1" , "YKK_BTN_Input");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TicketNo" , "YKK_LBL_TicketNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TicketNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TicketNo" , "YKK_TXT_TicketNo");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Input_2 = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Input_2" , "YKK_BTN_Input");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "YKK_BTN_Delete");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_FlatStockoutPopup_Low = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_FlatStockoutPopup_Low" , "YKK_LST_FlatStockoutPopup_Low");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "YKK_BTN_Set");

}
//end of class
