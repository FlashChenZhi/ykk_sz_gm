// $Id: DailyUpdate.java,v 1.2 2006/11/13 08:18:15 suresh Exp $

//#CM687441
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.dailyupdate;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM687442
/**
 * Non variable class generated by a tool.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:15 $
 * @author  $Author: suresh $
 */
public class DailyUpdate extends Page
{

	//#CM687443
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_DailyUpdate = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_DailyUpdate" , "W_DailyUpdate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkDate" , "W_WorkDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_FDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_FDate" , "W_F_Date");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Upwi = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Upwi" , "W_Upwi");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Delete = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Delete" , "W_Delete");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_WorkInfoOrver = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_WorkInfoOrver" , "W_WorkInfoOrver");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Start = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Start" , "Start");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//#CM687444
//end of class
