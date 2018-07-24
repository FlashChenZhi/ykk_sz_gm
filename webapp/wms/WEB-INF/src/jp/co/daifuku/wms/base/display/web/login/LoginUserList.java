// $Id: LoginUserList.java,v 1.2 2006/11/07 06:22:45 suresh Exp $
//#CM664707
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web.login;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM664708
/**
 * A strange class which cannot list the login users. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:22:45 $
 * @author  $Author: suresh $
 */
public class LoginUserList extends Page
{

	//#CM664709
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ListName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ListName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btnClose_U = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_U" , "Close");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_LoginUserList = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_LoginUserList" , "LoginUserList");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btnClose_D = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_D" , "Close");

}
//#CM664710
//end of class
