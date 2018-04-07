// $Id: CorrectItemMaster2.java,v 1.1.1.1 2006/08/17 09:34:16 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.correctitemmaster;
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:16 $
 * @author  $Author: mori $
 */
public class CorrectItemMaster2 extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_Bsc_DtlMdfyDlt");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Back = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Back" , "Back");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_consignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_consignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Consignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Consignor" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_itemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_itemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Item = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Item" , "W_JavaSet");
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
	public jp.co.daifuku.bluedog.ui.control.Label lbl_lastUpdateDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_lastUpdateDate" , "W_LastUpdateDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_lastUpdateDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_lastUpdateDate" , "W_F_Date");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_lastUseDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_lastUseDate" , "W_LastUseDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_lastUseDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_lastUseDate" , "W_F_Date");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "W_ModifySubmit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "Delete");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//end of class
