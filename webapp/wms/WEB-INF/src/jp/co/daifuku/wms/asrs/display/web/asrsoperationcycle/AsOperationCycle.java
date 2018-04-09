// $Id: AsOperationCycle.java,v 1.2 2006/10/26 04:45:26 suresh Exp $

//#CM36433
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsoperationcycle;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM36434
/**
 * Class generated by tool. Do not alter.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:45:26 $
 * @author  $Author: suresh $
 */
public class AsOperationCycle extends Page
{

	//#CM36435
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Query = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Query" , "W_Query");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouse = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouse" , "W_WareHouse");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_WareHouse = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_WareHouse" , "W_WareHouse");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StartDateTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StartDateTime" , "W_SrchStrtDateTime");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_DateFrom = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_DateFrom" , "W_SerchStartDate");
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_TimeFrom = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_TimeFrom" , "W_SearchStartTime");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromTo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromTo" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EndDateTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EndDateTime" , "W_EndSearchDateTime");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_DateTo = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_DateTo" , "W_SerchEndDate");
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_TimeTo = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_TimeTo" , "W_SearchEndTime");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Query = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Query" , "P_Query");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MachineNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MachineNo" , "W_RMNo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetMachineNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetMachineNo" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TotalCarryQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TotalCarryQty" , "W_TotalCarryQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetTotalCarryQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetTotalCarryQty" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageCarryQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageCarryQty" , "W_StorageCarryQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetStorageCarryQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetStorageCarryQty" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalCarryQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalCarryQty" , "W_RetrievalCarryQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetRetrievalCarryQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetRetrievalCarryQty" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageItemQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageItemQty" , "W_StorageItemQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetStorageItemQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetStorageItemQty" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalItemQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalItemQty" , "W_RetrievalItemQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetRetrievalItemQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetRetrievalItemQty" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ItemDetails = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ItemDetails" , "P_ItemDetails");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//#CM36436
//end of class
