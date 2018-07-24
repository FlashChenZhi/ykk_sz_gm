// $Id: StoragePlanDelete.java,v 1.3 2006/12/07 08:57:18 suresh Exp $

//#CM571966
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.storageplandelete;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM571967
/**
 * The improper, strange class that the tool generated. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/12/07 08:57:18 $
 * @author  $Author: suresh $
 */
public class StoragePlanDelete extends Page
{

	//#CM571968
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Delete = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Delete" , "W_Delete");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchCnsgnr = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchCnsgnr" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StrtStrgPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StrtStrgPlanDate" , "W_StrtStrgPlanDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_StrtStrgPlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_StrtStrgPlanDate" , "W_StrtStrgPlanDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchFromStrgDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchFromStrgDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromTo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromTo" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label btn_EndStoragePlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("btn_EndStoragePlanDate" , "W_EndStoragePlanDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_EdStrgPlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_EdStrgPlanDate" , "W_EdStrgPlanDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchToStrgDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchToStrgDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DspGroup = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DspGroup" , "W_DspGroup");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_StrgPlanDateItemCd = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_StrgPlanDateItemCd" , "W_StrgPlanDateItemCd");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Grp_StrgPlanDate = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Grp_StrgPlanDate" , "W_Grp_StrgPlanDate");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Regist_Kind = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Regist_Kind" , "W_Regist_Kind");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Rgst_Kind_All = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Rgst_Kind_All" , "W_Rgst_Kind_All");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Rgst_Kind_Strg = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Rgst_Kind_Strg" , "W_Rgst_Kind_Strg");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Rgst_Kind_Inst = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Rgst_Kind_Inst" , "W_Rgst_Kind_Inst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StrgPlanDltList = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StrgPlanDltList" , "W_StrgPlanDltList");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_StragePlanData = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_StragePlanData" , "W_CommonUseEvent");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_View = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_View" , "View");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllCheck = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllCheck" , "W_AllCheck");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllCheckClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllCheckClear" , "W_AllCheckClear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "Delete");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ListClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ListClear" , "W_ListClear");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Consignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Consignor" , "W_Consignor");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_JavaSetConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_JavaSetConsignorCode" , "W_R_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_JavaSetConsignorName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_JavaSetConsignorName" , "W_R_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_StoragePlanDelete = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_StoragePlanDelete" , "W_StoragePlanDelete");

}
//#CM571969
//end of class
