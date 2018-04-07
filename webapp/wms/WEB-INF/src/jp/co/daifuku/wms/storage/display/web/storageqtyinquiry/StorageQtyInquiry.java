// $Id: StorageQtyInquiry.java,v 1.2 2006/12/07 08:57:12 suresh Exp $

//#CM573655
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.storageqtyinquiry;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM573656
/**
 * The improper, strange class that the tool generated. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:12 $
 * @author  $Author: suresh $
 */
public class StorageQtyInquiry extends Page
{

	//#CM573657
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Inquiry = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Inquiry" , "W_Inquiry");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchConsignorCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchConsignorCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StartStorageDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StartStorageDate" , "W_StartStorageDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_StartStorageDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_StartStorageDate" , "W_StartStorageDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchStartStorageDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchStartStorageDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromTo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromTo" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EndStorageDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EndStorageDate" , "W_EndStorageDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_EndStorageDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_EndStorageDate" , "W_EndStorageDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchEndStorageDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchEndStorageDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchItemCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CasePieceFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CasePieceFlag" , "W_CasePieceFlag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Cpf_All = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Cpf_All" , "W_Cpf_All");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Cpf_Case = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Cpf_Case" , "W_Cpf_Case");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Cpf_Piece = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Cpf_Piece" , "W_Cpf_Piece");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Cpf_AppointOff = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Cpf_AppointOff" , "W_Cpf_AppointOff");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DspOrder = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DspOrder" , "W_DspOrder");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_StorageDate = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_StorageDate" , "W_StorageDate");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_StoragePlanDate = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_StoragePlanDate" , "W_StoragePlanDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_View = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_View" , "View");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Consignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Consignor" , "W_Consignor");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RConsignorCode" , "W_R_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RConsignorName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RConsignorName" , "W_R_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_SStorageQtyInquiry = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_SStorageQtyInquiry" , "W_S_StorageQtyInquiry");

}
//#CM573658
//end of class