// $Id: MasterStockMove.java,v 1.1.1.1 2006/08/17 09:34:18 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterstockmove;
import jp.co.daifuku.bluedog.ui.control.Page;

/**
 * ツールが生成した不可変クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:18 $
 * @author  $Author: mori $
 */
public class MasterStockMove extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_StockMove = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_StockMove" , "W_StockMove");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchConsignor = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchConsignor" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorName" , "W_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorName" , "W_ConsignorName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchItem = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchItem" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PStockSearch = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PStockSearch" , "W_P_StockSearch");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemName" , "W_ItemName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemName" , "W_ItemName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromMoveLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromMoveLocation" , "W_FromMoveLocation");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_MoveFromLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_MoveFromLocation" , "W_MoveFromLocation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchFrmMovLct = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchFrmMovLct" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UseByDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UseByDate" , "W_UseByDate");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UseByDate = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UseByDate" , "W_UseByDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchUseByDate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchUseByDate" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseEntering = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseEntering" , "W_CaseEntering");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CaseEntering = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CaseEntering" , "W_CaseEntering");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MoveCaseQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MoveCaseQty" , "W_MoveCaseQty");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_MovCaseQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_MovCaseQty" , "W_MovCaseQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MovePossibleCaseQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MovePossibleCaseQty" , "W_MovePossibleCaseQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetMoveCaseQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetMoveCaseQty" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MovePieceQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MovePieceQty" , "W_MovePieceQty");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_MovPieseQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_MovPieseQty" , "W_MovPieseQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MovePossiblePeaceQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MovePossiblePeaceQty" , "W_MovePossiblePeaceQty");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetMovePeaceQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetMovePeaceQty" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ToMoveLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ToMoveLocation" , "W_ToMoveLocation");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_MoveToLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_MoveToLocation" , "W_MoveToLocation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchToMovLct = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchToMovLct" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MoveWorkingList = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MoveWorkingList" , "W_MoveWorkingList");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CommonUse = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CommonUse" , "W_CommonUse");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Input = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Input" , "W_Input");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Relocate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Relocate" , "Relocate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ListClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ListClear" , "W_ListClear");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_StockMove = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_StockMove" , "W_StockMove");

}
//end of class
