// $Id: StockCorrect.java,v 1.2 2006/10/04 05:05:06 suresh Exp $

//#CM7850
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.stockcorrect;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM7851
/**
 * This is Non-Variable class generated by tool.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:05:06 $
 * @author  $Author: suresh $
 */
public class StockCorrect extends Page
{

	//#CM7852
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_BscDtlMdfyDlt = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_BscDtlMdfyDlt" , "W_Bsc_DtlMdfyDlt");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchConsignorCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchConsignorCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StartItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StartItemCode" , "W_StartItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StartItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StartItemCode" , "W_StartItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchStartItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchStartItemCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromToItem = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromToItem" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EndItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EndItemCode" , "W_EndItemCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_EndItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_EndItemCode" , "W_EndItemCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchEndItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchEndItemCode" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StartLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StartLocation" , "W_StartLocation");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StartLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StartLocation" , "W_StartLocation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchStartLocation = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchStartLocation" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromToLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromToLocation" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EndLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EndLocation" , "W_EndLocation");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_EndLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_EndLocation" , "W_EndLocation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PsearchEndLocation = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PsearchEndLocation" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CasePieceFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CasePieceFlag" , "W_CasePieceFlag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CpfAll = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CpfAll" , "W_Cpf_All");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CpfCase = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CpfCase" , "W_Cpf_Case");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CpfPiece = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CpfPiece" , "W_Cpf_Piece");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CpfAppointOff = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CpfAppointOff" , "W_Cpf_AppointOff");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DspOrder = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DspOrder" , "W_DspOrder");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ItemCodeLocation = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ItemCodeLocation" , "W_ItemCodeLocation");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ItemCdStrgDate = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ItemCdStrgDate" , "W_ItemCdStrgDate");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_LocationOrder = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_LocationOrder" , "W_LocationOrder");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Next = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Next" , "Next");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//#CM7853
//end of class
