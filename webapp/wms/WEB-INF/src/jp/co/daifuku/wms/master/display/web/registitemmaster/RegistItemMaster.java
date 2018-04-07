// $Id: RegistItemMaster.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.registitemmaster;
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
public class RegistItemMaster extends Page
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
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ConsignorSearch = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ConsignorSearch" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_itemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_itemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_itemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_itemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ItemSearch = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ItemSearch" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_itemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_itemName" , "W_ItemName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_itemName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_itemName" , "W_ItemName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_caseEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_caseEntering" , "W_CaseEntering");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_caseEntering = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_caseEntering" , "W_CaseEntering");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_caseItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_caseItf" , "W_CaseItf");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_caseItf = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_caseItf" , "W_CaseItf");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_bundleEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_bundleEntering" , "W_BundleEntering");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_bundleEntering = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_bundleEntering" , "W_BundleEntering");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_bundleItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_bundleItf" , "W_BundleItf");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_bundleItf = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_bundleItf" , "W_BundleItf");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "Submit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//end of class
