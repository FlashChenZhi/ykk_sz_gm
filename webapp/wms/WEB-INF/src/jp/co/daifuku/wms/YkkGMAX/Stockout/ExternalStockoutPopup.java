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
public class ExternalStockoutPopup extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalNo" , "YKK_LBL_RetrievalNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RetrievalNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RetrievalNo" , "YKK_TXT_RetrievalNo_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ExternalCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ExternalCode" , "YKK_LBL_ExternalCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ExternalCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ExternalCode" , "YKK_TXT_ExternalCode_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemNo" , "YKK_LBL_ItemNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemNo" , "YKK_TXT_ItemNo_RO");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemName" , "YKK_TXT_ItemName_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ColorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ColorCode" , "YKK_LBL_ColorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ColorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ColorCode" , "YKK_TXT_ColorCode_RO");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Reshow = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Reshow" , "YKK_BTN_Reshow");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Up = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Up" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ExternalStockoutPopup = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ExternalStockoutPopup" , "YKK_LST_ExternalStockoutPopup");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Low = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Low" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "YKK_BTN_Set_NA");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close" , "YKK_BTN_Close");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EnableToStockoutCount = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EnableToStockoutCount" , "YKK_LBL_CheckedCount");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Slash1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Slash1" , "YKK_LBL_Slash");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockoutCount = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockoutCount" , "YKK_LBL_StockoutCount");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_EnableToStockoutCount = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_EnableToStockoutCount" , "YKK_TXT_EnableToStockoutCount_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Slash2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Slash2" , "YKK_LBL_Slash");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StockoutCount = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StockoutCount" , "YKK_TXT_StockoutCount_RO");

}
//end of class
