// $Id: WorkMaintenance.java,v 1.1 2007/12/13 06:25:09 administrator Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Maintenance;
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
 * @version $Revision: 1.1 $, $Date: 2007/12/13 06:25:09 $
 * @author  $Author: administrator $
 */
public class WorkMaintenance extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "YKK_LBL_WorkMaintenance");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "YKK_TAB_Maintenance");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TransferType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TransferType" , "YKK_LBL_TransferType");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_StockinAndStockout = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_StockinAndStockout" , "YKK_RDO_StockinAndStockoutChecked");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Stockin = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Stockin" , "YKK_RDO_Stockin");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_StockoutSTtoST = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_StockoutSTtoST" , "YKK_RDO_StockoutSTtoST");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Station = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Station" , "YKK_LBL_Station");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Station = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Station" , "YKK_PUL_Station");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FinalStation = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FinalStation" , "YKK_RDO_FinalStationChecked");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CurrentStation = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CurrentStation" , "YKK_RDO_CurrentStation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Show = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Show" , "YKK_BTN_Show_POPUP");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PickOut = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PickOut" , "YKK_BTN_PickOut");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_WorkEnd = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_WorkEnd" , "YKK_BTN_WorkEnd");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TransferStartStation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TransferStartStation" , "YKK_LBL_TransferStartStation");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TransferStartStation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TransferStartStation" , "YKK_TXT_TransferStartStation_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TransferDestinationStation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TransferDestinationStation" , "YKK_LBL_TransferDestinationStation");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TransferDestinationStation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TransferDestinationStation" , "YKK_TXT_TransferDestinationStation_RO");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_STBreakOffStart = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_STBreakOffStart" , "YKK_BTN_STBreakOffStart");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MCKEY = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MCKEY" , "YKK_LBL_MCKEY");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_MCKEY = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_MCKEY" , "YKK_TXT_MCKEY_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationNo" , "YKK_LBL_LocationNo");
	public jp.co.daifuku.bluedog.ui.control.FormatTextBox txt_LocationNo = jp.co.daifuku.bluedog.ui.control.FormatTextBoxFactory.getInstance("txt_LocationNo" , "YKK_TXT_LocationNo_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TransferType1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TransferType1" , "YKK_LBL_TransferType");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TransferType = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TransferType" , "YKK_TXT_TransferType_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_OrderDetail = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_OrderDetail" , "YKK_LBL_OrderDetail");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_OrderDetail = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_OrderDetail" , "YKK_TXT_OrderDetail_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TransferStatus = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TransferStatus" , "YKK_LBL_TransferStatus");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TransferStatus = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TransferStatus" , "YKK_TXT_TransferStatus_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BucketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BucketNo" , "YKK_LBL_BucketNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BucketNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BucketNo" , "YKK_TXT_BucketNo_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ColorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ColorCode" , "YKK_LBL_ColorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ColorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ColorCode" , "YKK_TXT_ColorCode_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TicketNo" , "YKK_LBL_TicketNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TicketNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TicketNo" , "YKK_TXT_TicketNo_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "YKK_LBL_ItemNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "YKK_TXT_ItemNo_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InstockCount = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InstockCount" , "YKK_LBL_InstockCount");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_InstockCount = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_InstockCount" , "YKK_TXT_InstockCount_RO");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemName" , "YKK_TXT_ItemName_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockoutRetrievalInfo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockoutRetrievalInfo" , "YKK_LBL_StockoutRetrievalInfo");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Up = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Up" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_WorkMaintenance = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_WorkMaintenance" , "YKK_LST_WorkMaintenance");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Low = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Low" , "YKK_Pager");

}
//end of class
