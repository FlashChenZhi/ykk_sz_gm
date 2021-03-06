// $Id: BucketView.java,v 1.1 2007/12/30 05:17:35 administrator Exp $

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
 * @version $Revision: 1.1 $, $Date: 2007/12/30 05:17:35 $
 * @author  $Author: administrator $
 */
public class BucketView extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BucketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BucketNo" , "YKK_LBL_BucketNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BucketNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BucketNo" , "YKK_TXT_BucketNo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LooseSearch = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LooseSearch" , "YKK_LBL_LooseSearch");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_SameToFront = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_SameToFront" , "YKK_RDO_SameToFrontChecked");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_SameToAnywhere = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_SameToAnywhere" , "YKK_RDO_SameToAnywhere");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Show = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Show" , "YKK_BTN_Show");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Up = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Up" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_Up = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_Up" , "YKK_BTN_Close");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_BucketView = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_BucketView" , "YKK_LST_BucketView");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Low = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Low" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_Low = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_Low" , "YKK_BTN_Close");

}
//end of class
