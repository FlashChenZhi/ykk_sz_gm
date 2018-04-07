// $Id: StartInventoryCheck.java,v 1.2 2006/10/26 08:04:02 suresh Exp $

//#CM41036
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.startinventorycheck;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM41037
/**
 * Class generated by tool. Do not alter.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:04:02 $
 * @author  $Author: suresh $
 */
public class StartInventoryCheck extends Page
{

	//#CM41038
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_WorkSet = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_WorkSet" , "W_WorkSet");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouse = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouse" , "W_WareHouse");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkPlaceStation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkPlaceStation" , "W_WorkPlaceStation");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_11 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_11" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_WareHouse = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_WareHouse" , "W_WareHouse");
	public jp.co.daifuku.bluedog.ui.control.LinkedPullDown pul_WorkPlace = jp.co.daifuku.bluedog.ui.control.LinkedPullDownFactory.getInstance("pul_WorkPlace" , "W_WorkPlace");
	public jp.co.daifuku.bluedog.ui.control.LinkedPullDown pul_Station = jp.co.daifuku.bluedog.ui.control.LinkedPullDownFactory.getInstance("pul_Station" , "W_Station");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchConsignorCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchConsignorCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StartLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StartLocation" , "W_StartLocation");
	public jp.co.daifuku.bluedog.ui.control.FormatTextBox txt_StartLocation = jp.co.daifuku.bluedog.ui.control.FormatTextBoxFactory.getInstance("txt_StartLocation" , "W_F_Location");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchStartLocation = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchStartLocation" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_20 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_20" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EndLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EndLocation" , "W_EndLocation");
	public jp.co.daifuku.bluedog.ui.control.FormatTextBox txt_EndLocation = jp.co.daifuku.bluedog.ui.control.FormatTextBoxFactory.getInstance("txt_EndLocation" , "W_F_Location");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchEndLocation = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchEndLocation" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StartItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StartItemCode" , "W_StartItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StartItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StartItemCode" , "W_StartItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchStartItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchStartItemCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_19 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_19" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EndItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EndItemCode" , "W_EndItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_EndItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_EndItemCode" , "W_EndItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchEndItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchEndItemCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InventoryCheck = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InventoryCheck" , "W_InventoryCheck");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CommonUse = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CommonUse" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Setting = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Setting" , "Setting_ProcessMessage");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//#CM41039
//end of class
