// $Id: TicketNoView.java,v 1.1 2008/01/21 04:12:37 administrator Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Popup;
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
 * @version $Revision: 1.1 $, $Date: 2008/01/21 04:12:37 $
 * @author  $Author: administrator $
 */
public class TicketNoView extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Up = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Up" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_Up = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_Up" , "YKK_BTN_Close");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_TicketNoView = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_TicketNoView" , "YKK_LST_TicketNoView");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Low = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Low" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_Low = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_Low" , "YKK_BTN_Close");

}
//end of class