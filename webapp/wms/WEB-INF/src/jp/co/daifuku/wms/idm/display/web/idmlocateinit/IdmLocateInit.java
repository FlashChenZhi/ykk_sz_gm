// $Id: IdmLocateInit.java,v 1.1.1.1 2006/08/17 09:34:09 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.idm.display.web.idmlocateinit;
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:09 $
 * @author  $Author: mori $
 */
public class IdmLocateInit extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Set = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Set" , "W_Set");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Select_Establish_Regist = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Select_Establish_Regist" , "W_Select_Establish_Regist");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Select_Establish_Delete = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Select_Establish_Delete" , "W_Select_Establish_Delete");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StartLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StartLocation" , "W_StartLocation");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StartBank = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StartBank" , "W_StartBank");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphn1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphn1" , "W_Hyphn");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StartBay = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StartBay" , "W_StartBay");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphn2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphn2" , "W_Hyphn");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StartLevel = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StartLevel" , "W_StartLevel");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DeleteLocateNoTxt = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DeleteLocateNoTxt" , "W_DeleteLocateNoTxt");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EndLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EndLocation" , "W_EndLocation");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_EndBank = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_EndBank" , "W_EndBank");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphn3 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphn3" , "W_Hyphn");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_EndBay = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_EndBay" , "W_EndBay");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphn4 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphn4" , "W_Hyphn");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_EndLevel = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_EndLevel" , "W_EndLevel");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EmptyLocateSearch = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EmptyLocateSearch" , "W_EmptyLocateSearch");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Search_Priority_Bank = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Search_Priority_Bank" , "W_Search_Priority_Bank");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Search_Priority_Aisle = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Search_Priority_Aisle" , "W_Search_Priority_Aisle");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Establish = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Establish" , "W_Establish");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//end of class
