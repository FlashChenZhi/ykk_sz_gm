// $Id: InventoryInquiry.java,v 1.2 2006/12/07 08:57:48 suresh Exp $

//#CM566952
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.inventoryinquiry;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM566953
/**
 * The improper, strange class that the tool generated. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:48 $
 * @author  $Author: suresh $
 */
public class InventoryInquiry extends Page
{

	//#CM566954
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Inquiry = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Inquiry" , "W_Inquiry");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchConsignorCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchConsignorCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StartLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StartLocation" , "W_StartLocation");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StartLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StartLocation" , "W_StartLocation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchStartLocation = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchStartLocation" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromTo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromTo" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EndLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EndLocation" , "W_EndLocation");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_EndLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_EndLocation" , "W_EndLocation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchEndLocation = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchEndLocation" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchItemCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Status = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Status" , "W_Status");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Sts_All = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Sts_All" , "W_Sts_All");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Sts_StockDiff = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Sts_StockDiff" , "W_Sts_StockDiff");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Sts_StockEqualQty = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Sts_StockEqualQty" , "W_Sts_StockEqualQty");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PDisplay = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PDisplay" , "W_P_Display");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Print = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Print" , "W_Print");

}
//#CM566955
//end of class
