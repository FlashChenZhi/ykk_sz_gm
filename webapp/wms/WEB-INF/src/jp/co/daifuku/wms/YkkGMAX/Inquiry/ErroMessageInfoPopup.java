// $Id: skelten.java,v 1.2 2007/03/07 07:45:22 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Inquiry;
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
public class ErroMessageInfoPopup extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.FixedListCell lst_ErroMessageInfoPopupHead = jp.co.daifuku.bluedog.ui.control.FixedListCellFactory.getInstance("lst_ErroMessageInfoPopupHead" , "YKK_LST_ErroMessageInfoPopupHead");
	public jp.co.daifuku.bluedog.ui.control.SubmitLabel slb_Download = jp.co.daifuku.bluedog.ui.control.SubmitLabelFactory.getInstance("slb_Download" , "W_Download");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Up = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Up" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Print = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Print" , "YKK_BTN_Print");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_CSV = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_CSV" , "YKK_BTN_CSV");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_Up = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_Up" , "YKK_BTN_Close");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ErroMessageInfoPopup = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ErroMessageInfoPopup" , "YKK_LST_ErroMessageInfoPopup");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Low = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Low" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_Low = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_Low" , "YKK_BTN_Close");

}
//end of class
