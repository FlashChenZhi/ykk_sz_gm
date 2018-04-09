// $Id: StoragePlanModify.java,v 1.2 2006/12/07 08:57:15 suresh Exp $

//#CM572610
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.storageplanmodify;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM572611
/**
 * The improper, strange class that the tool generated. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:15 $
 * @author  $Author: suresh $
 */
public class StoragePlanModify extends Page
{

	//#CM572612
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Bsc_DtlMdfyDlt = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Bsc_DtlMdfyDlt" , "W_Bsc_DtlMdfyDlt");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchCnsgnr = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchCnsgnr" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StoragePlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StoragePlanDate" , "W_StoragePlanDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_StoragePlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_StoragePlanDate" , "W_StoragePlanDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchStrgPlanDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchStrgPlanDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PStrgPlanSrch = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PStrgPlanSrch" , "W_P_StrgPlanSrch");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchItem = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchItem" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseStrgLct = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseStrgLct" , "W_CaseStrgLct");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CaseStorageLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CaseStorageLocation" , "W_CaseStorageLocation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchCaseStrgLct = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchCaseStrgLct" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PiceStrgLct = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PiceStrgLct" , "W_PiceStrgLct");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_PieseStorageLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_PieseStorageLocation" , "W_PieseStorageLocation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchPieceStrgLct = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchPieceStrgLct" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Regist_Kind = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Regist_Kind" , "W_Regist_Kind");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Rgst_Kind_Strg = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Rgst_Kind_Strg" , "W_Rgst_Kind_Strg");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Rgst_Kind_Inst = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Rgst_Kind_Inst" , "W_Rgst_Kind_Inst");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Next = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Next" , "Next");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//#CM572613
//end of class
