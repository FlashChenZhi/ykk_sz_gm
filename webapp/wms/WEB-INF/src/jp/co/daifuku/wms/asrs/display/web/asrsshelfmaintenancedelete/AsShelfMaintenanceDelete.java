// $Id: AsShelfMaintenanceDelete.java,v 1.2 2006/10/26 04:51:36 suresh Exp $

//#CM37174
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsshelfmaintenancedelete;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM37175
/**
 * Class generated by tool. Do not alter.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:51:36 $
 * @author  $Author: suresh $
 */
public class AsShelfMaintenanceDelete extends Page
{

	//#CM37176
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Set = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Set" , "W_Set");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PassWord = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PassWord" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_PassWord = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_PassWord" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouse = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouse" , "W_WareHouse");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_WareHouse = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_WareHouse" , "W_WareHouse");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationStatus = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationStatus" , "W_LocationStatus");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_LocationStatus_Unit = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_LocationStatus_Unit" , "W_LocationStatus_Unit");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_LocationStatus_Empty_PB = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_LocationStatus_Empty_PB" , "W_LocationStatus_Empty_PB");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_LocationStatus_Abn = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_LocationStatus_Abn" , "W_LocationStatus_Abn");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationDetail = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationDetail" , "W_LocationDetail");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Query = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Query" , "P_Query");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationNo" , "W_LocationNo");
	public jp.co.daifuku.bluedog.ui.control.FormatTextBox txt_Location = jp.co.daifuku.bluedog.ui.control.FormatTextBoxFactory.getInstance("txt_Location" , "W_F_E_Location");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_LocationDetails = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_LocationDetails" , "P_LocationDetails");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationStatusDisp = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationStatusDisp" , "W_LocationStatus");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetLocationStatus = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetLocationStatus" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Setting = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Setting" , "Setting");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//#CM37177
//end of class