//$Id: SortingPlanList.java,v 1.1.1.1 2006/08/17 09:34:32 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.sortingplanlist;
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
public class SortingPlanList extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_ListOutput = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_ListOutput" , "W_ListOutput");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchConsignorCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchConsignorCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PicknigPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PicknigPlanDate" , "W_PickingPlanDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_PickingPlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_PickingPlanDate" , "W_PickingPlanDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchPickingPlanDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchPickingPlanDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchItemCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label W_CasePieceFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("W_CasePieceFlag" , "W_CasePieceFlag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CpfAll = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CpfAll" , "W_Cpf_All");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CpfCase = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CpfCase" , "W_Cpf_Case");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CpfPiece = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CpfPiece" , "W_Cpf_Piece");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CpfCasePiece = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CpfCasePiece" , "W_Cpf_CasePiece");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CrossDCTwoByte = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CrossDCTwoByte" , "W_CrossDCTwoByte");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CrossDCFlagAll = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CrossDCFlagAll" , "W_CrossDCFlag_All");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CrossDCFlagCross = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CrossDCFlagCross" , "W_CrossDCFlag_Cross");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CrossDCFlagDC = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CrossDCFlagDC" , "W_CrossDCFlag_DC");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkStatus = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkStatus" , "W_WorkStatus");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_WorkStatusSorting = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_WorkStatusSorting" , "W_WorkStatusSorting");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Display = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Display" , "W_P_Display");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Print = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Print" , "W_Print");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//end of class
