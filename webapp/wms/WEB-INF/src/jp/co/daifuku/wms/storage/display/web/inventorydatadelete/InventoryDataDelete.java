// $Id: InventoryDataDelete.java,v 1.2 2006/12/07 08:57:49 suresh Exp $

//#CM566825
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.inventorydatadelete;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM566826
/**
 * The improper, strange class that the tool generated. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:49 $
 * @author  $Author: suresh $
 */
public class InventoryDataDelete extends Page
{

	//#CM566827
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Delete = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Delete" , "W_Delete");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchCnsgnr = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchCnsgnr" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StartLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StartLocation" , "W_StartLocation");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StartLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StartLocation" , "W_StartLocation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchStartLct = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchStartLct" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromToLct = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromToLct" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EndLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EndLocation" , "W_EndLocation");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_EndLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_EndLocation" , "W_EndLocation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchEndLct = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchEndLct" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_InvntryItemDlt = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_InvntryItemDlt" , "W_InvntryItemDlt");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "Clear");

}
//#CM566828
//end of class