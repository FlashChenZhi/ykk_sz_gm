// $Id: skelten.java,v 1.2 2007/03/07 07:45:22 suresh Exp $

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
 * @version $Revision: 1.2 $, $Date: 2007/03/07 07:45:22 $
 * @author  $Author: suresh $
 */
public class LocationView extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_EmptyUp = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_EmptyUp" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_Up = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_Up" , "YKK_BTN_Close");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EmptyLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EmptyLocation" , "YKK_LBL_EmptyLocation");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_EmptyLocationView = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_EmptyLocationView" , "YKK_LST_EmptyLocationView");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_EmptyLow = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_EmptyLow" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Up = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Up" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_OtherLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_OtherLocation" , "YKK_LBL_OtherLocation");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_LocationView = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_LocationView" , "YKK_LST_LocationView");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Low = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Low" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_Low = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_Low" , "YKK_BTN_Close");

}
//end of class
