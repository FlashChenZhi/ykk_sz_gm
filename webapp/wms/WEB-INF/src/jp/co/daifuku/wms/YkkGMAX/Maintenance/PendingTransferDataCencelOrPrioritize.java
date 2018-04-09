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
public class PendingTransferDataCencelOrPrioritize extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "YKK_LBL_PendingTransferDataCencelOrPrioritize");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "YKK_TAB_Setting");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Section = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Section" , "YKK_LBL_Section");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Section = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Section" , "YKK_TXT_Section");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Line = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Line" , "YKK_LBL_Line");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Line = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Line" , "YKK_TXT_Line");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalNo" , "YKK_LBL_RetrievalNo");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RetrievalNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RetrievalNo" , "YKK_TXT_RetrievalNo");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Show = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Show" , "YKK_BTN_Show");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_OrderBy = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_OrderBy" , "YKK_LBL_OrderBy");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_WhenNextWorkBegin = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_WhenNextWorkBegin" , "YKK_RDO_WhenNextWorkBegin");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ItemNo = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ItemNo" , "YKK_RDO_ItemNo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DealtContent = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DealtContent" , "YKK_LBL_DealtContent");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Cancel = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Cancel" , "YKK_RDO_Cancel");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Priority = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Priority" , "YKK_RDO_Priority");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_PriorityDivision = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_PriorityDivision" , "YKK_PUL_PriorityDivision");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "YKK_BTN_Set");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SelectAll = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SelectAll" , "YKK_BTN_SelectAll");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_UnselectAll = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_UnselectAll" , "YKK_BTN_UnselectAll");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Up = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Up" , "YKK_Pager");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_PendingTransferDataCencelO = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_PendingTransferDataCencelO" , "YKK_LST_PendingTransferDataCencelOrPrioritize");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Low = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Low" , "YKK_Pager");

}
//end of class
