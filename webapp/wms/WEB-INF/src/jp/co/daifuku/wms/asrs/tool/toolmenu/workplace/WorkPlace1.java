// $Id: WorkPlace1.java,v 1.2 2006/10/30 05:29:13 suresh Exp $

//#CM54862
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.workplace;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM54863
/**
<kt> * The improper, strange class that the tool generated.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:29:13 $
 * @author  $Author: suresh $
 */
public class WorkPlace1 extends Page
{

	//#CM54864
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_WorkPlace_Create = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_WorkPlace_Create" , "AST_WorkPlace_Create");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouseNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouseNumber" , "AST_WareHouseNumber");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_StoreAs = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_StoreAs" , "AST_StoreAs");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ParentStnumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ParentStnumber" , "AST_ParentStnumber");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ParentStNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ParentStNumber" , "AST_ParentStNumber");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Search = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Search" , "AST_P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ParentstationName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ParentstationName" , "AST_ParentstationName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ParentStationName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ParentStationName" , "AST_ParentStationName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkPlaceTypeChar = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkPlaceTypeChar" , "AST_WorkPlaceTypeChar");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_StandAloneType = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_StandAloneType" , "AST_StandAloneType");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AisleConnectedType = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AisleConnectedType" , "AST_AisleConnectedType");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_MainStation = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_MainStation" , "AST_MainStation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Next = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Next" , "AST_Next");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "AST_Clear");

}
//#CM54865
//end of class
