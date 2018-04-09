// $Id: LoginUserMaintenance.java,v 1.2 2006/10/30 05:09:46 suresh Exp $
//#CM53810
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.login.toollogin;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM53811
/** <en>
 * This invariable class is created by the screen generator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:09:46 $
 * @author  $Author: suresh $
 </en> */
public class LoginUserMaintenance extends Page
{

	//#CM53812
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "LoginUserMaintenance");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Msg01 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Msg01" , "LoginUserMntMsg1");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_LoginUserMaintenance = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_LoginUserMaintenance" , "LoginUserMaintenance");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Refresh = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Refresh" , "Reflesh");

}
//#CM53813
//end of class
