// $Id: Machine.java,v 1.2 2006/10/30 05:10:40 suresh Exp $

//#CM53832
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.machine;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM53833
/**
 * Improper, strange Class that the tool generated. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:10:40 $
 * @author  $Author: suresh $
 */
public class Machine extends Page
{

	//#CM53834
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "AST_Create");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AgcNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AgcNumber" , "AST_AgcNumber");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_AGCNo = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_AGCNo" , "AST_AGCNo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MachineCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MachineCode" , "AST_MachineCode");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_MachineTypeCode = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_MachineTypeCode" , "AST_MachineTypeCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MachineNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MachineNumber" , "AST_MachineNumber");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_MachineNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_MachineNumber" , "AST_MachineNumber");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StationNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StationNumber" , "AST_StationNumber");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_StationNumber = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_StationNumber" , "AST_StationNumber");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Add = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Add" , "AST_Add");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "AST_Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "AST_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "AST_Cancel");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_MachineStatus = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_MachineStatus" , "AST_S_MachineStatus");

}
//#CM53835
//end of class
