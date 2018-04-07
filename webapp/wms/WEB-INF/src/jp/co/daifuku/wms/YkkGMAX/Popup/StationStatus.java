// $Id: StationStatus.java,v 1.1 2008/01/08 03:58:53 administrator Exp $

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
 * @version $Revision: 1.1 $, $Date: 2008/01/08 03:58:53 $
 * @author  $Author: administrator $
 */
public class StationStatus extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SelectAll = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SelectAll" , "YKK_BTN_SelectAll");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_UnselectAll = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_UnselectAll" , "YKK_BTN_UnselectAll");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_StationStatus = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_StationStatus" , "YKK_LST_StationStatus");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_StationStart = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_StationStart" , "YKK_BTN_StationStart");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_StationStop = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_StationStop" , "YKK_BTN_StationStop");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Reshow = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Reshow" , "YKK_BTN_Reshow");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close" , "YKK_BTN_Close");

}
//end of class