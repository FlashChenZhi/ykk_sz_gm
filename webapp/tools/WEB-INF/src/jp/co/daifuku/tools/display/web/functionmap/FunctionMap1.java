// $Id: FunctionMap1.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web.functionmap;
import jp.co.daifuku.bluedog.ui.control.Page;

/** <jp>
 * サブメニューボタン設定1画面目の不可変クラスです。
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
public class FunctionMap1 extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "T_FunctionMap");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MainMenu = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MainMenu" , "T_MainMenu");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_MainMenu = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_MainMenu" , "T_MainMenu");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_View = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_View" , "View");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FunctionMapInfoMsg1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FunctionMapInfoMsg1" , "T_FunctionMapInfoMsg1");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FunctionMapInfoMsg2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FunctionMapInfoMsg2" , "T_FunctionMapInfoMsg2");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_FunctionMap = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_FunctionMap" , "T_FunctionMap");

}
//end of class
