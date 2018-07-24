// $Id: RegistCustomerMaster.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.registcustomermaster;
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
public class RegistCustomerMaster extends Page
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
	public jp.co.daifuku.bluedog.ui.control.Label lbl_customerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_customerCode" , "W_CustomerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_customerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_customerCode" , "W_CustomerCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_CustomerSearch = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_CustomerSearch" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_customerName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_customerName" , "W_CustomerName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_customerName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_customerName" , "W_CustomerName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_postalCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_postalCode" , "W_PostalCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_postalCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_postalCode" , "W_PostalCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_prefecture = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_prefecture" , "W_Prefecture");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_prefecture = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_prefecture" , "W_Prefecture");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_address = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_address" , "W_Address");
	public jp.co.daifuku.bluedog.ui.control.TextArea txa_address = jp.co.daifuku.bluedog.ui.control.TextAreaFactory.getInstance("txa_address" , "W_Address");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_address2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_address2" , "W_Address2");
	public jp.co.daifuku.bluedog.ui.control.TextArea txa_address2 = jp.co.daifuku.bluedog.ui.control.TextAreaFactory.getInstance("txa_address2" , "W_Address2");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_contact1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_contact1" , "W_Contact1");
	public jp.co.daifuku.bluedog.ui.control.TextArea txa_contact1 = jp.co.daifuku.bluedog.ui.control.TextAreaFactory.getInstance("txa_contact1" , "W_Contact1");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_contact2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_contact2" , "W_Contact2");
	public jp.co.daifuku.bluedog.ui.control.TextArea txa_contact2 = jp.co.daifuku.bluedog.ui.control.TextAreaFactory.getInstance("txa_contact2" , "W_Contact2");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_contact3 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_contact3" , "W_Contact3");
	public jp.co.daifuku.bluedog.ui.control.TextArea txa_contact3 = jp.co.daifuku.bluedog.ui.control.TextAreaFactory.getInstance("txa_contact3" , "W_Contact3");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "Submit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//end of class
