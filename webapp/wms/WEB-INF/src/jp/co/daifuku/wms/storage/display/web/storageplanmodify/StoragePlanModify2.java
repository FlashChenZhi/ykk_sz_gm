// $Id: StoragePlanModify2.java,v 1.2 2006/12/07 08:57:15 suresh Exp $

//#CM572614
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.storageplanmodify;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM572615
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
public class StoragePlanModify2 extends Page
{

	//#CM572616
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_BscDtlMdfyDlt = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_BscDtlMdfyDlt" , "W_Bsc_DtlMdfyDlt");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Back = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Back" , "Back");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Consignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Consignor" , "W_Consignor");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetCnsgnrCd = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetCnsgnrCd" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetCnsgnrNm = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetCnsgnrNm" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StoragePlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StoragePlanDate" , "W_StoragePlanDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_FDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_FDate" , "W_F_Date");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Item = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Item" , "W_Item");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetItemCd = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetItemCd" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetItemNm = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetItemNm" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Regist_Kind = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Regist_Kind" , "W_Regist_Kind");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetRegistKind = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetRegistKind" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseStrgLct = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseStrgLct" , "W_CaseStrgLct");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CaseStorageLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CaseStorageLocation" , "W_CaseStorageLocation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchCaseStrgLct = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchCaseStrgLct" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PiceStrgLct = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PiceStrgLct" , "W_PiceStrgLct");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_PieseStorageLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_PieseStorageLocation" , "W_PieseStorageLocation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchPieceStrgLct = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchPieceStrgLct" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseEntering" , "W_CaseEntering");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseEntering = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseEntering" , "W_CaseEntering");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_HostCasePlanQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_HostCasePlanQty" , "W_HostCasePlanQty");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_HostCasePlanQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_HostCasePlanQty" , "W_HostCasePlanQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BundleEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BundleEntering" , "W_BundleEntering");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_BundleEntering = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_BundleEntering" , "W_BundleEntering");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_HostPiesePlanQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_HostPiesePlanQty" , "W_HostPiesePlanQty");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_HostPiesePlanQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_HostPiesePlanQty" , "W_HostPiesePlanQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseItf" , "W_CaseItf");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CaseItf = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CaseItf" , "W_CaseItf");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BundleItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BundleItf" , "W_BundleItf");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BundleItf = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BundleItf" , "W_BundleItf");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Input = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Input" , "W_Input");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ModifySubmit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ModifySubmit" , "W_ModifySubmit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllDelete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllDelete" , "W_AllDelete");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_StoragePlanModify = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_StoragePlanModify" , "W_StoragePlanModify");

}
//#CM572617
//end of class
