// $Id: LoginUserMaintenance.java,v 1.2 2006/11/07 06:22:14 suresh Exp $
//#CM664737
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web.login;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM664738
/**
 * The improper, strange class that the tool generated. 

 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:22:14 $
 * @author  $Author: suresh $
 */
public class LoginUserMaintenance extends Page
{

	//#CM664739
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
//#CM664740
//end of class
