// $Id: StorageQtyList.java,v 1.2 2006/12/07 08:57:12 suresh Exp $

//#CM573862
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.storageqtylist;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM573863
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
public class StorageQtyList extends Page
{

	//#CM573864
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_ListOutput = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_ListOutput" , "W_ListOutput");
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
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PDisplay = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PDisplay" , "W_P_Display");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Print = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Print" , "W_Print");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//#CM573865
//end of class
