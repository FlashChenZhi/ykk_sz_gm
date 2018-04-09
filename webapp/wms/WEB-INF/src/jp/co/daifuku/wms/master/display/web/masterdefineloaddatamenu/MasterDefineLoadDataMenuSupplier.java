// $Id: MasterDefineLoadDataMenuSupplier.java,v 1.1.1.1 2006/08/17 09:34:17 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterdefineloaddatamenu;
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:17 $
 * @author  $Author: mori $
 */
public class MasterDefineLoadDataMenuSupplier extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_LodClm_SetSup = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_LodClm_SetSup" , "W_LodClm_SetSup");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Back = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Back" , "Back");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ValidLeft1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ValidLeft1" , "W_Valid");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DigitsUseLength1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DigitsUseLength1" , "W_DigitsUseLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLength1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLength1" , "W_MaxLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Position1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Position1" , "W_Position");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCodeReq");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CnsgnrCd = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CnsgnrCd" , "W_CommonNotUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CnsgnrCdLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CnsgnrCdLen" , "W_CnsgnrCdLenReq");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenConsignorCode" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CnsgnrCdPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CnsgnrCdPst" , "W_CnsgnrCdPstReq");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SupplierCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SupplierCode" , "W_SplCdReq");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_SupplierCode = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_SupplierCode" , "W_CommonNotUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_SupplierCodeLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_SupplierCodeLen" , "W_SplCdReqLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenSUpplierCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenSUpplierCode" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_SupplierCdPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_SupplierCdPst" , "W_SplCdReqPst");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SupplierName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SupplierName" , "W_SupplierName");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_SupplierName = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_SupplierName" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_SupplierNameLen = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_SupplierNameLen" , "W_SplNmLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenSupplierName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenSupplierName" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_SupplierNamePst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_SupplierNamePst" , "W_SplNmPst");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "Submit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//end of class
