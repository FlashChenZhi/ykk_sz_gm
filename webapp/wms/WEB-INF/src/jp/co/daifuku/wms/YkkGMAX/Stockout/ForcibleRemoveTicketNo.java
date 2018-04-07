// $Id: skelten.java,v 1.2 2007/03/07 07:45:22 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Stockout;
import jp.co.daifuku.bluedog.ui.control.Page;

/**
 * This class can not be changed and it is generated by ScreenGenerator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/03/07 07:45:22 $
 * @author  $Author: suresh $
 */
public class ForcibleRemoveTicketNo extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "YKK_LBL_ForcibleRemoveTicketNo");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Item = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Item" , "YKK_LBL_Item");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Item = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Item" , "YKK_TXT_Item");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Color = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Color" , "YKK_LBL_Color");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Color = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Color" , "YKK_TXT_Color");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Show = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Show" , "YKK_BTN_Show");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Up = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Up" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ForcibleRemoveTicketNo_Up = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ForcibleRemoveTicketNo_Up" , "YKK_LST_ForcibleRemoveTicketNo_Up");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Low = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Low" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Input = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Input" , "YKK_BTN_Input");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TicketNo" , "YKK_LBL_TicketNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TicketNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TicketNo" , "YKK_TXT_TicketNo_Enter");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_InputTicketNo = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_InputTicketNo" , "YKK_BTN_Input");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ForcibleRemoveTicketNo_Low = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ForcibleRemoveTicketNo_Low" , "YKK_LST_ForcibleRemoveTicketNo_Low");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "YKK_BTN_Set");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "YKK_BTN_Delete");

}
//end of class
