// $Id: StockMoveMaintenance.java,v 1.3 2006/12/07 08:57:19 suresh Exp $

//#CM571473
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.stockmovemaintenance;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM571474
/**
 * The improper, strange class that the tool generated. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/12/07 08:57:19 $
 * @author  $Author: suresh $
 */
public class StockMoveMaintenance extends Page
{

	//#CM571475
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Set = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Set" , "W_StockMoveMtn");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchConsignor = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchConsignor" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchItem = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchItem" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DspOrder = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DspOrder" , "W_DspOrder");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ItemCode = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FromMoveLocation = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FromMoveLocation" , "W_FromMoveLocation");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DspMoveComplete = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DspMoveComplete" , "W_DspMoveComplete");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CommonUseCompData = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CommonUseCompData" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MoveWorkingList = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MoveWorkingList" , "W_MoveWorkingList");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CommonUseWorkList = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CommonUseWorkList" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_View = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_View" , "View");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ModifySubmit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ModifySubmit" , "W_ModifySubmit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_MovCancelAllSlt = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_MovCancelAllSlt" , "W_MovCancelAllSlt");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_MovCancelAllSltOff = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_MovCancelAllSltOff" , "W_MovCancelAllSltOff");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ListClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ListClear" , "W_ListClear");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Consignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Consignor" , "W_Consignor");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SRConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SRConsignorCode" , "W_R_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SRConsignorName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SRConsignorName" , "W_R_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_SStockMoveMaintenance = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_SStockMoveMaintenance" , "W_StockMoveMaintenance");

}
//#CM571476
//end of class
