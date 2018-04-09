// $Id: LoginUserList.java,v 1.2 2006/10/30 05:09:47 suresh Exp $
//#CM53776
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.login.toollogin;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM53777
/** <en>
 * This is an invariable class of the login user list screen.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:09:47 $
 * @author  $Author: suresh $
 </en> */
public class LoginUserList extends Page
{

	//#CM53778
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ListName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ListName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btnClose_U = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_U" , "Close");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_LoginUserList = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_LoginUserList" , "LoginUserList");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btnClose_D = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_D" , "Close");

}
//#CM53779
//end of class
