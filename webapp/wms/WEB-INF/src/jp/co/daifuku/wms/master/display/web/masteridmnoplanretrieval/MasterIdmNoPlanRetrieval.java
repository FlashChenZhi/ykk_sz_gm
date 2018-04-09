// $Id: MasterIdmNoPlanRetrieval.java,v 1.2 2006/11/10 00:34:55 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masteridmnoplanretrieval;
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
 * @version $Revision: 1.2 $, $Date: 2006/11/10 00:34:55 $
 * @author  $Author: suresh $
 */
public class MasterIdmNoPlanRetrieval extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab W_Set = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("W_Set" , "W_Set");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txtl_WorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txtl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchCnsgnr = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchCnsgnr" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchItem = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchItem" , "W_PSearchCheck");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CasePieceFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CasePieceFlag" , "W_CasePieceFlag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Cpf_All = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Cpf_All" , "W_Cpf_All");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Cpf_Case = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Cpf_Case" , "W_Cpf_Case");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Cpf_Piece = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Cpf_Piece" , "W_Cpf_Piece");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Cpf_AppointOff = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Cpf_AppointOff" , "W_Cpf_AppointOff");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StartLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StartLocation" , "W_StartLocation");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StartBank = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StartBank" , "W_StartBank");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphn = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphn" , "W_Hyphn");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StartBay = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StartBay" , "W_StartBay");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphn2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphn2" , "W_Hyphn");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StartLevel = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StartLevel" , "W_StartLevel");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchStrt = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchStrt" , "W_PSearchCheck");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromTo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromTo" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EndLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EndLocation" , "W_EndLocation");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_EndBank = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_EndBank" , "W_EndBank");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphn3 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphn3" , "W_Hyphn");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_EndBay = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_EndBay" , "W_EndBay");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphn4 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphn4" , "W_Hyphn");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_EndLevel = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_EndLevel" , "W_EndLevel");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchEd = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchEd" , "W_PSearchCheck");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseItf" , "W_CaseItf");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CaseItf = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CaseItf" , "W_CaseItf");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchCase = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchCase" , "W_PSearchCheck");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BundleItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BundleItf" , "W_BundleItf");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BundleItf = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BundleItf" , "W_BundleItf");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchBdl = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchBdl" , "W_PSearchCheck");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerCode" , "W_CustomerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerCode" , "W_CustomerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerName" , "W_CustomerName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerName" , "W_CustomerName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Idmschretwoli = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Idmschretwoli" , "W_Unschretwoli");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CommonUse = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CommonUse" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_View = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_View" , "View");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_RetrievalStart = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_RetrievalStart" , "W_RetrievalStart");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_RetrievalQtyClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_RetrievalQtyClear" , "W_RetrievalQtyClear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllCheck = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllCheck" , "W_AllCheck");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllCheckClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllCheckClear" , "W_AllCheckClear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ListClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ListClear" , "W_ListClear");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Consignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Consignor" , "W_Consignor");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RConsignorCode" , "W_R_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RConsignorName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RConsignorName" , "W_R_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_INoPlanRetrieval = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_INoPlanRetrieval" , "W_I_NoPlanRetrieval");

}
//end of class
