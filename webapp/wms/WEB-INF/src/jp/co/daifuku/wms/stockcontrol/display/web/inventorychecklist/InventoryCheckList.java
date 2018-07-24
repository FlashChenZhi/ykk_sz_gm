// $Id: InventoryCheckList.java,v 1.2 2006/10/04 05:04:04 suresh Exp $

//#CM4074
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.inventorychecklist;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM4075
/**
 * This is Non-Variable class generated by tool.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:04:04 $
 * @author  $Author: suresh $
 */
public class InventoryCheckList extends Page
{

	//#CM4076
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_ListOutput = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_ListOutput" , "W_ListOutput");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchCnsgnr = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchCnsgnr" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StartLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StartLocation" , "W_StartLocation");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StartLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StartLocation" , "W_StartLocation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchStrtLct = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchStrtLct" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromTo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromTo" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EndLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EndLocation" , "W_EndLocation");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_EndLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_EndLocation" , "W_EndLocation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchEndLct = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchEndLct" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchItemCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PrintCondition = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PrintCondition" , "W_PrintCondition");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_GrpItemCdStrgDate = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_GrpItemCdStrgDate" , "W_Grp_ItemCdStrgDate");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_GrpGrpOff = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_GrpGrpOff" , "W_Grp_GrpOff");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_QtyPrintOn = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_QtyPrintOn" , "W_QtyPrintOn");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_QtyPrintOff = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_QtyPrintOff" , "W_QtyPrintOff");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PDisplay = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PDisplay" , "W_P_Display");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Print = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Print" , "W_Print");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//#CM4077
//end of class
