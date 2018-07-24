// $Id: MasterPlanLoadData.java,v 1.1.1.1 2006/08/17 09:34:18 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterplanloaddata;
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:18 $
 * @author  $Author: mori $
 */
public class MasterPlanLoadData extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_DataLoad = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_DataLoad" , "W_DataLoad");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PlanDate" , "W_PlanDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_PlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_PlanDate" , "W_PlanDate");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_InstockPlanData = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_InstockPlanData" , "W_InstockPlanData");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_StoragePlanData = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_StoragePlanData" , "W_StoragePlanData");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_IdmAuto = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_IdmAuto" , "W_IdmAuto");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_RetrievalPlanData = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_RetrievalPlanData" , "W_RetrievalPlanData");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_PickingPlanData = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_PickingPlanData" , "W_PickingPlanData");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_ShippingPlanData = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_ShippingPlanData" , "W_ShippingPlanData");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Start = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Start" , "Start");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//end of class
