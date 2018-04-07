// $Id: UnmanagedStockout.java,v 1.2 2007/12/25 03:46:15 administrator Exp $

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
 * @version $Revision: 1.2 $, $Date: 2007/12/25 03:46:15 $
 * @author  $Author: administrator $
 */
public class UnmanagedStockout extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "YKK_LBL_UnmanagedStockout");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockoutCondition = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockoutCondition" , "YKK_LBL_StockoutCondition");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_SearchStockout = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_SearchStockout" , "YKK_RDO_SearchUnmanagedStockoutChecked");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemNo" , "YKK_LBL_ItemNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemNo" , "YKK_TXT_ItemNo");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ItemBrowse = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ItemBrowse" , "YKK_BTN_ItemBrowse_POPUP");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_DesignateLocationStockout = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_DesignateLocationStockout" , "YKK_RDO_DesignateLocationUnmanagedStockout");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationNo" , "W_LocationNo");
	public jp.co.daifuku.bluedog.ui.control.FormatTextBox txt_LocationNo = jp.co.daifuku.bluedog.ui.control.FormatTextBoxFactory.getInstance("txt_LocationNo" , "YKK_TXT_LocationNo");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_AfterThisLocation = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_AfterThisLocation" , "YKK_CHK_AfterThisLocation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "YKK_BTN_Set_POPUP");

}
//end of class
