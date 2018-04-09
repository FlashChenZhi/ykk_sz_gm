// $Id: skelten.java,v 1.2 2007/03/07 07:45:22 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Maintenance;
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
public class ErroRangeMaintenance extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "YKK_LBL_ErroRangeMaintenance");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "YKK_TAB_Maintenance");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InOutDivision = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InOutDivision" , "YKK_LBL_InOutDivision");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Stockin = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Stockin" , "YKK_RDO_StockinRangeChecked");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Stockout = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Stockout" , "YKK_RDO_StockoutRange");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_TerminalNoBrowse = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_TerminalNoBrowse" , "YKK_BTN_FnrangeBrowse_POPUP");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TerminalNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TerminalNo" , "YKK_LBL_TerminalNo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Star1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Star1" , "YKK_LBL_Star");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TerminalNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TerminalNo" , "YKK_TXT_TerminalNo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FormerProduceLine1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FormerProduceLine1" , "YKK_LBL_FormerProduceLine1");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Star2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Star2" , "YKK_LBL_Star");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FormerProduceLine1 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FormerProduceLine1" , "YKK_TXT_FormerProduceLine1");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FormerProduceLine2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FormerProduceLine2" , "YKK_LBL_FormerProduceLine2");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Star3 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Star3" , "YKK_LBL_Star");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FormerProduceLine2 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FormerProduceLine2" , "YKK_TXT_FormerProduceLine2");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DealDivision = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DealDivision" , "YKK_LBL_DealDivision");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Add = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Add" , "YKK_BTN_Add");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "YKK_BTN_Modify");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "YKK_BTN_Delete");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TerminalLine1Line2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TerminalLine1Line2" , "YKK_LBL_TerminalLine1Line2");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TerminalNo_RO = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TerminalNo_RO" , "YKK_TXT_TerminalNo_RO");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FormerProduceLine1_RO = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FormerProduceLine1_RO" , "YKK_TXT_FormerProduceLine1_RO");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FormerProduceLine2_RO = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FormerProduceLine2_RO" , "YKK_TXT_FormerProduceLine2_RO");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UnitMaxErro = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UnitMaxErro" , "YKK_LBL_UnitMaxErro");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_UnitMaxErro = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_UnitMaxErro" , "YKK_TXT_UnitMaxErro");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PercentSign1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PercentSign1" , "YKK_LBL_PercentSign");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UnitMinErro = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UnitMinErro" , "YKK_LBL_UnitMinErro");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_UnitMinErro = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_UnitMinErro" , "YKK_TXT_UnitMinErro");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PercentSign2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PercentSign2" , "YKK_LBL_PercentSign");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockinMaxErro = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockinMaxErro" , "YKK_LBL_StockinMaxErro");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StockinMaxErro = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StockinMaxErro" , "YKK_TXT_StockinMaxErro");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PercentSign3 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PercentSign3" , "YKK_LBL_PercentSign");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockinMinErro = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockinMinErro" , "YKK_LBL_StockinMinErro");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StockinMinErro = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StockinMinErro" , "YKK_TXT_StockinMinErro");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PercentSign4 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PercentSign4" , "YKK_LBL_PercentSign");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockoutMaxErro = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockoutMaxErro" , "YKK_LBL_StockoutMaxErro");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StockoutMaxErro = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StockoutMaxErro" , "YKK_TXT_StockoutMaxErro");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PercentSign5 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PercentSign5" , "YKK_LBL_PercentSign");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockoutMinErro = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockoutMinErro" , "YKK_LBL_StockoutMinErro");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StockoutMinErro = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StockoutMinErro" , "YKK_TXT_StockoutMinErro");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PercentSign6 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PercentSign6" , "YKK_LBL_PercentSign");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "YKK_BTN_Set");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "YKK_BTN_Clear");

}
//end of class
