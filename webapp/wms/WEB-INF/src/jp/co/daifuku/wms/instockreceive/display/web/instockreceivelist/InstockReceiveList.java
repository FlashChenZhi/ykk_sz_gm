// $Id: InstockReceiveList.java,v 1.1.1.1 2006/08/17 09:34:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.instockreceivelist;
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:11 $
 * @author  $Author: mori $
 */
public class InstockReceiveList extends Page
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
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InstockPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InstockPlanDate" , "W_InstockPlanDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_InstockPlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_InstockPlanDate" , "W_InstockPlanDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchInstockPlanDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchInstockPlanDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SupplierCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SupplierCode" , "W_SupplierCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SupplierCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SupplierCode" , "W_SupplierCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchSupplierCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchSupplierCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchItemCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CrossDCTwoByte = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CrossDCTwoByte" , "W_CrossDCTwoByte");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CrossDCFlagAll = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CrossDCFlagAll" , "W_CrossDCFlag_All");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CrossDCFlagCross = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CrossDCFlagCross" , "W_CrossDCFlag_Cross");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CrossDCFlagDC = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CrossDCFlagDC" , "W_CrossDCFlag_DC");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DspOrder = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DspOrder" , "W_DspOrder");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TicTkt = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TicTkt" , "W_Tic_Tkt");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TicItem = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TicItem" , "W_Tic_Item");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkStatus = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkStatus" , "W_WorkStatus");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_WorkStatus = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_WorkStatus" , "W_WorkStatus");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PDisplay = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PDisplay" , "W_P_Display");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Print = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Print" , "W_Print");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//end of class
