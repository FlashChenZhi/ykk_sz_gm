// $Id: FunctionOrder.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.functionorder;
import jp.co.daifuku.bluedog.ui.control.Page;

/** <jp>
 * サブメニュー表示順変更の不可変クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:10 $
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:10 $
 * @author  $Author: mori $
 </en> */
public class FunctionOrder extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "T_Configure");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MainMenu = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MainMenu" , "T_MainMenu");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_MainMenu = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_MainMenu" , "T_MainMenu");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_View = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_View" , "View");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FunctionOrder = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FunctionOrder" , "T_FunctionOrder");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DisplayItem = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DisplayItem" , "T_DisplayItem");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_NotUseItem = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_NotUseItem" , "T_NotUseItem");
	public jp.co.daifuku.bluedog.ui.control.ImageButton img_ArrowUp = jp.co.daifuku.bluedog.ui.control.ImageButtonFactory.getInstance("img_ArrowUp" , "T_ArrowUp");
	public jp.co.daifuku.bluedog.ui.control.ImageButton img_ArrowDown = jp.co.daifuku.bluedog.ui.control.ImageButtonFactory.getInstance("img_ArrowDown" , "T_ArrowDown");
	public jp.co.daifuku.bluedog.ui.control.ListBox ltb_FunctionOrder = jp.co.daifuku.bluedog.ui.control.ListBoxFactory.getInstance("ltb_FunctionOrder" , "T_FunctionOrder");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Add = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Add" , "T_Add");
	public jp.co.daifuku.bluedog.ui.control.ImageButton img_ArrowLeft = jp.co.daifuku.bluedog.ui.control.ImageButtonFactory.getInstance("img_ArrowLeft" , "T_ArrowLeft");
	public jp.co.daifuku.bluedog.ui.control.ImageButton img_ArrowRight = jp.co.daifuku.bluedog.ui.control.ImageButtonFactory.getInstance("img_ArrowRight" , "T_ArrowRight");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Delete = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Delete" , "T_Delete");
	public jp.co.daifuku.bluedog.ui.control.ListBox ltb_FunctionOrder2 = jp.co.daifuku.bluedog.ui.control.ListBoxFactory.getInstance("ltb_FunctionOrder2" , "T_FunctionOrder");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "T_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "Cancel");

}
//end of class
