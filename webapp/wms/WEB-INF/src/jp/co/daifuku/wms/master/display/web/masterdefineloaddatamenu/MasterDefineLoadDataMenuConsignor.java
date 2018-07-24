// $Id: MasterDefineLoadDataMenuConsignor.java,v 1.1.1.1 2006/08/17 09:34:17 mori Exp $

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
public class MasterDefineLoadDataMenuConsignor extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_LodClm_SetCon = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_LodClm_SetCon" , "W_LodClm_SetCon");
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
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorName" , "W_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CnsgnrName = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CnsgnrName" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CnsgnrName = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CnsgnrName" , "W_CnsgnrNmLen");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxLenConsignorName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxLenConsignorName" , "W_MaxLen");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CnsgnrNmPst = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CnsgnrNmPst" , "W_CnsgnrNmPst");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "Submit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//end of class
