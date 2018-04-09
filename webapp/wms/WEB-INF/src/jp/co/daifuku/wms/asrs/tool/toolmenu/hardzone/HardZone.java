// $Id: HardZone.java,v 1.2 2006/10/30 04:10:36 suresh Exp $

//#CM52820
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.hardzone;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM52821
/**
 * Improper, strange Class that the tool generated. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:10:36 $
 * @author  $Author: suresh $
 */
public class HardZone extends Page
{

	//#CM52822
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Create = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Create" , "AST_Create");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouseNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouseNumber" , "AST_WareHouseNumber");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_StoreAs = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_StoreAs" , "AST_StoreAs");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ZoneId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ZoneId" , "AST_ZoneId");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_HardZoneId = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_HardZoneId" , "AST_HardZoneId");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Height = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Height" , "AST_Height");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_Height = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_Height" , "AST_Height");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CarryName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CarryName" , "AST_CarryName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_LoadSizeName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_LoadSizeName" , "AST_LoadSizeName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range" , "AST_Range");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Bank = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Bank" , "AST_Bank");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FBank = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FBank" , "AST_FBank");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphen = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphen" , "AST_Hyphen2");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TBank = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TBank" , "AST_TBank");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Bay = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Bay" , "AST_Bay");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FBay = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FBay" , "AST_FBay");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphen2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphen2" , "AST_Hyphen2");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TBay = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TBay" , "AST_TBay");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Level = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Level" , "AST_Level");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FLevel = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FLevel" , "AST_FLevel");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphen3 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphen3" , "AST_Hyphen2");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TLevel = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TLevel" , "AST_TLevel");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ZonePriority = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ZonePriority" , "AST_ZonePriority");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ZonePriority2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ZonePriority2" , "AST_ZonePriority2");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ZonePriority2 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ZonePriority2" , "AST_ZonePriority2");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ZonePriority3 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ZonePriority3" , "AST_ZonePriority3");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ZonePriority3 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ZonePriority3" , "AST_ZonePriority3");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ZonePriority4 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ZonePriority4" , "AST_ZonePriority4");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ZonePriority4 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ZonePriority4" , "AST_ZonePriority4");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ZonePriority5 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ZonePriority5" , "AST_ZonePriority5");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ZonePriority5 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ZonePriority5" , "AST_ZonePriority5");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ZonePriority6 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ZonePriority6" , "AST_ZonePriority6");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ZonePriority6 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ZonePriority6" , "AST_ZonePriority6");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ZonePriority7 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ZonePriority7" , "AST_ZonePriority7");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ZonePriority7 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ZonePriority7" , "AST_ZonePriority7");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ZonePriority8 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ZonePriority8" , "AST_ZonePriority8");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ZonePriority8 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ZonePriority8" , "AST_ZonePriority8");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ZonePriority9 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ZonePriority9" , "AST_ZonePriority9");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ZonePriority9 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ZonePriority9" , "AST_ZonePriority9");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ZonePriority10 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ZonePriority10" , "AST_ZonePriority10");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ZonePriority10 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ZonePriority10" , "AST_ZonePriority10");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Add = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Add" , "AST_Add");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "AST_Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "AST_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "AST_Cancel");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_HardZone = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_HardZone" , "AST_S_HardZone");

}
//#CM52823
//end of class
