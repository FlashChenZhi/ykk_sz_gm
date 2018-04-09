// $Id: RegistConsignorMaster.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.registconsignormaster;
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:19 $
 * @author  $Author: mori $
 */
public class RegistConsignorMaster extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_Regist");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_workerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_workerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_workerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_workerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_consignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_consignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_consignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_consignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Search = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Search" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_consignorName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_consignorName" , "W_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_consignorName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_consignorName" , "W_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_regist = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_regist" , "Submit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//end of class
