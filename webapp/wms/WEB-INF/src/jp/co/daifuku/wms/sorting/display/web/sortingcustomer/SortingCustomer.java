// $Id: SortingCustomer.java,v 1.1.1.1 2006/08/17 09:34:32 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.sortingcustomer;
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:32 $
 * @author  $Author: mori $
 */
public class SortingCustomer extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Set = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Set" , "W_Set");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchConsignorCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchConsignorCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PickingPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PickingPlanDate" , "W_PickingPlanDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_PickingPlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_PickingPlanDate" , "W_PickingPlanDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchPickingPlanDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchPickingPlanDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchItemCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CasePieceFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CasePieceFlag" , "W_CasePieceFlag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CpfAllAnd = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CpfAllAnd" , "W_CpfAllAnd");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CpfAllDist = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CpfAllDist" , "W_CpfAllDist");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CpfCase = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CpfCase" , "W_Cpf_Case");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CpfPiece = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CpfPiece" , "W_Cpf_Piece");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CrossDCTwoByte = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CrossDCTwoByte" , "W_CrossDCTwoByte");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CrossDCFlagAll = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CrossDCFlagAll" , "W_CrossDCFlag_All");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CrossDCFlagCross = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CrossDCFlagCross" , "W_CrossDCFlag_Cross");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CrossDCFlagDC = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CrossDCFlagDC" , "W_CrossDCFlag_DC");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PickingWorkList = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PickingWorkList" , "W_PickingWorkList");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CommonUseEvent = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CommonUseEvent" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_View = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_View" , "View");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PickingStart = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PickingStart" , "W_PickingStart");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ListClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ListClear" , "W_ListClear");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SConsignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SConsignor" , "W_Consignor");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_JavaSetConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_JavaSetConsignorCode" , "W_R_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_JavaSetConsignorName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_JavaSetConsignorName" , "W_R_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SPickingPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SPickingPlanDate" , "W_PickingPlanDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_JavaSetPickingDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_JavaSetPickingDate" , "W_R_PickingPlanDate");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_SortingCustomer = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_SortingCustomer" , "W_S_SortingCustomer");

}
//end of class
