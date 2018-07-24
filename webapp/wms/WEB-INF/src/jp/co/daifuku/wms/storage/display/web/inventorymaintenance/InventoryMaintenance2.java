// $Id: InventoryMaintenance2.java,v 1.2 2006/12/07 08:57:47 suresh Exp $

//#CM567120
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.inventorymaintenance;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM567121
/**
 * The improper, strange class that the tool generated. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:47 $
 * @author  $Author: suresh $
 */
public class InventoryMaintenance2 extends Page
{

	//#CM567122
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Inventory = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Inventory" , "W_Inventory");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Back = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Back" , "Back");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Consignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Consignor" , "W_Consignor");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetCnsgnrCd = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetCnsgnrCd" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetCnsgnrNm = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetCnsgnrNm" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Location = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Location" , "W_Location");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetStartLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetStartLocation" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromTo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromTo" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetEndLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetEndLocation" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearch = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearch" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemName" , "W_ItemName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemName" , "W_ItemName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseEntering" , "W_CaseEntering");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseEntering = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseEntering" , "W_CaseEntering");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BundleEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BundleEntering" , "W_BundleEntering");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_BundleEntering = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_BundleEntering" , "W_BundleEntering");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InventoryCaseQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InventoryCaseQty" , "W_InventoryCaseQty");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_InventoryCaseQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_InventoryCaseQty" , "W_InventoryCaseQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InventoryPieceQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InventoryPieceQty" , "W_InventoryPieceQty");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_InventoryPieceQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_InventoryPieceQty" , "W_InventoryPieceQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationInputArea = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationInputArea" , "W_Location");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Location = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Location" , "W_Location");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UseByDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UseByDate" , "W_UseByDate");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UseByDate = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UseByDate" , "W_UseByDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Input = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Input" , "W_Input");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_InventoryData = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_InventoryData" , "W_InventoryData");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_InventoryMaintenance = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_InventoryMaintenance" , "W_InventoryMaintenance");

}
//#CM567123
//end of class
