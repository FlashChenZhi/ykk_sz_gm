// $Id: RegistWorkerMaster.java,v 1.2 2006/11/13 08:21:31 suresh Exp $

//#CM693863
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.registworkermaster;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM693864
/**
 * Non variable class generated by a tool.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:31 $
 * @author  $Author: suresh $
 */
public class RegistWorkerMaster extends Page

{

	//#CM693865
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Regist = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Regist" , "W_Regist");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode_T = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode_T" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkerCode_T = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkerCode_T" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password_T = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password_T" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password_T = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password_T" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_RegistWorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkerCode" , "W_RegistWorkerCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearch = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearch" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Name = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Name" , "W_Name");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Name = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Name" , "W_Name");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Furigana = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Furigana" , "W_Furigana");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Furigana = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Furigana" , "W_Furigana");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Gender = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Gender" , "W_Gender");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Gender = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Gender" , "W_Gender");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Jobtype = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Jobtype" , "W_Jobtype");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Jobtype = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Jobtype" , "W_Jobtype");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Access = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Access" , "W_Access");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Access = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Access" , "W_Access");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "W_RegistPassword");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "W_RegistPassword");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Memo1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Memo1" , "W_Memo1");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Memo1 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Memo1" , "W_Memo1");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Memo2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Memo2" , "W_Memo2");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Memo2 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Memo2" , "W_Memo2");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "Submit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//#CM693866
//end of class
