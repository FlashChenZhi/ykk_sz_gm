// $Id: StorageWorkMaintenance.java,v 1.3 2006/12/07 08:57:10 suresh Exp $

//#CM574120
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.storageworkmaintenance;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM574121
/**
 * The improper, strange class that the tool generated. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/12/07 08:57:10 $
 * @author  $Author: suresh $
 */
public class StorageWorkMaintenance extends Page
{

	//#CM574122
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Maintenance = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Maintenance" , "W_Maintenance");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchCnsgnr = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchCnsgnr" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkStatus = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkStatus" , "W_WorkStatus");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_WorkStatus = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_WorkStatus" , "W_WorkStatus");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StoragePlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StoragePlanDate" , "W_StoragePlanDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_StoragePlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_StoragePlanDate" , "W_StoragePlanDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchStrgPlanDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchStrgPlanDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchItemCd = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchItemCd" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CasePieceFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CasePieceFlag" , "W_CasePieceFlag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CpfAll = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CpfAll" , "W_Cpf_All");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CpfCase = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CpfCase" , "W_Cpf_Case");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CpfPiece = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CpfPiece" , "W_Cpf_Piece");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CpfAppointOff = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CpfAppointOff" , "W_Cpf_AppointOff");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_View = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_View" , "View");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ModifySubmit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ModifySubmit" , "W_ModifySubmit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllWorkingClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllWorkingClear" , "W_AllWorkingClear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ListClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ListClear" , "W_ListClear");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SConsignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SConsignor" , "W_Consignor");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SRConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SRConsignorCode" , "W_R_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SRConsignorName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SRConsignorName" , "W_R_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SStoragePlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SStoragePlanDate" , "W_StoragePlanDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_SRStoragePlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_SRStoragePlanDate" , "W_R_StoragePlanDate");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_SStorageWorkMtn = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_SStorageWorkMtn" , "W_S_StorageWorkMtn");

}
//#CM574123
//end of class
