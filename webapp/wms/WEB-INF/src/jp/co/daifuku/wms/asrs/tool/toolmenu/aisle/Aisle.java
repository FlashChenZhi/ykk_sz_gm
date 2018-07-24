// $Id: Aisle.java,v 1.2 2006/10/30 04:06:45 suresh Exp $

//#CM52355
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.aisle;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM52356
/**
 * Improper, strange Class that the tool generated. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:06:45 $
 * @author  $Author: suresh $
 */
public class Aisle extends Page
{

	//#CM52357
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "AST_Create");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouseNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouseNumber" , "AST_WareHouseNumber");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_StoreAs = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_StoreAs" , "AST_StoreAs");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StationNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StationNumber" , "AST_StationNumber");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StNumber" , "AST_StNumber");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AisleNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AisleNumber" , "AST_AisleNumber");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_AisleNumber = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_AisleNumber" , "AST_AisleNumber");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AgcNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AgcNumber" , "AST_AgcNumber");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_AGCNo = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_AGCNo" , "AST_AGCNo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range" , "AST_Range");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Bank = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Bank" , "AST_Bank");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FBank = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FBank" , "AST_FBank");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphen1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphen1" , "AST_Hyphen2");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TBank = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TBank" , "AST_TBank");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AislePosition = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AislePosition" , "AST_AislePosition");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FAislePosition = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FAislePosition" , "AST_FAislePosition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_And = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_And" , "AST_And");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TAislePosition = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TAislePosition" , "AST_TAislePosition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Space = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Space" , "AST_Space");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Bay = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Bay" , "AST_Bay");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FBay = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FBay" , "AST_FBay");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphen2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphen2" , "AST_Hyphen2");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TBay = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TBay" , "AST_TBay");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AislePositionMsg = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AislePositionMsg" , "AST_AislePositionMsg");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Level = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Level" , "AST_Level");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FLevel = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FLevel" , "AST_FLevel");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphen3 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphen3" , "AST_Hyphen2");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TLevel = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TLevel" , "AST_TLevel");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Add = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Add" , "AST_Add");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "AST_Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "AST_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "AST_Cancel");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_Aisle = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_Aisle" , "AST_S_Aisle");

}
//#CM52358
//end of class
