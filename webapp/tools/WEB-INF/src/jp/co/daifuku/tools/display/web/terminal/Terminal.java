// $Id: Terminal.java,v 1.1.1.1 2006/08/17 09:33:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.terminal;
import jp.co.daifuku.bluedog.ui.control.Page;

/** <jp>
 * 端末設定の不可変クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 </jp> */
/** <en>
 * This invariable class is created by the screen generator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:11 $
 * @author  $Author: mori $
 </en> */
public class Terminal extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "T_Configure");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TerminalNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TerminalNumber" , "T_TerminalNumber");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TerminalNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TerminalNumber" , "T_TerminalNumber");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Search = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Search" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ProcessingType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ProcessingType" , "T_ProcessingType");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "Submit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "Modify");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "Delete");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TerminalNumber2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TerminalNumber2" , "T_TerminalNumber");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_TerminalNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_TerminalNumber" , "T_R_TerminalNumber");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TerminalName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TerminalName" , "T_TerminalName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TerminalName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TerminalName" , "T_TerminalName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_IpAddress = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_IpAddress" , "T_IpAddress");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_IpAddress = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_IpAddress" , "T_IpAddress");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RoleId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RoleId" , "T_RoleId");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RoleId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RoleId" , "T_RoleId");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Search2 = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Search2" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PrinterName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PrinterName" , "T_PrinterName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_PrinterName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_PrinterName" , "T_PrinterName");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "T_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//end of class
