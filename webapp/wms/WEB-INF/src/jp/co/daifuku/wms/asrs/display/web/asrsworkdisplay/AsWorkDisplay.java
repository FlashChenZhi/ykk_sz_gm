// $Id: AsWorkDisplay.java,v 1.2 2006/10/26 04:56:51 suresh Exp $

//#CM37541
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsworkdisplay;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM37542
/**
 * Class generated by tool. Do not alter.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:56:51 $
 * @author  $Author: suresh $
 */
public class AsWorkDisplay extends Page
{

	//#CM37543
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Maintenance = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Maintenance" , "W_Maintenance");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StationNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StationNo" , "W_Station");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_StationNo = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_StationNo" , "W_StationNo");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Reflesh = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Reflesh" , "Reflesh");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkNo" , "W_WorkNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RWorkNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RWorkNo" , "W_R_WorkNo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkType" , "W_WorkClass");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RWorkType = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RWorkType" , "W_R_WorkType");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalDetails = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalDetails" , "W_RetrievalDetails");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RRetrievalDetail = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RRetrievalDetail" , "W_R_RetrievalDetail");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationNo" , "W_LocationNo");
	public jp.co.daifuku.bluedog.ui.control.FormatTextBox txt_FRLocation = jp.co.daifuku.bluedog.ui.control.FormatTextBoxFactory.getInstance("txt_FRLocation" , "W_F_R_Location");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_LocationDetails = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_LocationDetails" , "P_LocationDetails");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Complete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Complete" , "Complete");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ExpendComplete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ExpendComplete" , "ExpendComplete");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_WorkDisplay = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_WorkDisplay" , "W_WorkDisplay");

}
//#CM37544
//end of class
