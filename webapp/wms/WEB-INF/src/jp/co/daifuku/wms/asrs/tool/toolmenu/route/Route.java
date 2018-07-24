// $Id: Route.java,v 1.2 2006/10/30 05:11:26 suresh Exp $

//#CM53982
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.route;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM53983
/**
 * Improper, strange Class that the tool generated. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:11:26 $
 * @author  $Author: suresh $
 */
public class Route extends Page
{

	//#CM53984
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "AST_Create");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConnectionBeforeStation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConnectionBeforeStation" , "AST_ConnectionBeforeStation");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_ConveyanceOriginStationNo = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_ConveyanceOriginStationNo" , "AST_ConveyanceOriginStationNo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConnectStnumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConnectStnumber" , "AST_ConnectStnumber");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConnectStnumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConnectStnumber" , "AST_ConnectStnumber");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Add = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Add" , "AST_Add");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "AST_Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "AST_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "AST_Cancel");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_Route = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_Route" , "AST_S_Route");

}
//#CM53985
//end of class
