// $Id: ReportDataNoPlan.java,v 1.2 2006/11/13 08:21:45 suresh Exp $

//#CM694301
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.reportdatanoplan;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM694302
/**
 * Non variable class generated by a tool.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:45 $
 * @author  $Author: suresh $
 */
public class ReportDataNoPlan extends Page
{

	//#CM694303
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_DataReport = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_DataReport" , "W_DataReport");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkDate" , "W_WorkDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_WorkDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_WorkDate" , "W_WorkDate1");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Select = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Select" , "W_Select");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ReportDetals = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ReportDetals" , "W_ReportDetals");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Message = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Message" , "W_Message");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_StockMovRsltData = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_StockMovRsltData" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockMoveReport = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockMoveReport" , "W_StockMoveReport");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockMoveMessage = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockMoveMessage" , "W_Message");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_InvntryRsltData = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_InvntryRsltData" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InventoryReport = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InventoryReport" , "W_InventoryReport");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InventoryMessage = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InventoryMessage" , "W_Message");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_NoPlanStorageRsltData = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_NoPlanStorageRsltData" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_NoPlanStorageReport = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_NoPlanStorageReport" , "W_NoPlanStorageReport");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_NoPlanStorageMessage = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_NoPlanStorageMessage" , "W_Message");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_NoPlanRetrievalRsltData = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_NoPlanRetrievalRsltData" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_NoPlanRetrievalReport = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_NoPlanRetrievalReport" , "W_NoPlanRetrievalReport");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_NoPlanRetrievalMessage = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_NoPlanRetrievalMessage" , "W_Message");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Start = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Start" , "Start");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//#CM694304
//end of class
