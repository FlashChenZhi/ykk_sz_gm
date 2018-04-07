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
public class ExceptionStockoutPopup extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockoutCondition = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockoutCondition" , "YKK_LBL_StockoutCondition");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StockoutCondition = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StockoutCondition" , "YKK_TXT_StockoutCondition_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockoutRange = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockoutRange" , "YKK_LBL-StockoutRange");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StockoutRange = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StockoutRange" , "YKK_TXT_StockoutRange_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_OrderBy = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_OrderBy" , "YKK_LBL_OrderBy");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_LocationNo = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_LocationNo" , "YKK_RDO_LocationNoChcked");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ItemNo = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ItemNo" , "YKK_RDO_ItemNo");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_StockinTime = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_StockinTime" , "YKK_RDO_StockinTime");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Line1Line2 = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Line1Line2" , "YKK_RDO_Line1Line2");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Show = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Show" , "YKK_BTN_Show");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockoutStation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockoutStation" , "YKK_LBL_StockoutStation");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_StockoutStation = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_StockoutStation" , "YKK_PUL_StockoutStation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SelectAll = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SelectAll" , "YKK_BTN_SelectAll");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_UnselectAll = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_UnselectAll" , "YKK_BTN_UnselectAll");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set_Up = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set_Up" , "YKK_BTN_Set_NA");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_Up = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_Up" , "YKK_BTN_Close");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Up = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Up" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ExceptionStockoutPopup = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ExceptionStockoutPopup" , "YKK_LST_ExceptionStockoutPopup");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Low = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Low" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set_Low = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set_Low" , "YKK_BTN_Set_NA");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_Low = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_Low" , "YKK_BTN_Close");

}
//end of class
