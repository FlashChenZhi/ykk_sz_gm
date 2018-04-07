// $Id: RegistBatchMaster.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.registbatchmaster;
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:19 $
 * @author  $Author: mori $
 */
public class RegistBatchMaster extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_Regist");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_MstWorkerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_MstWorkerCode" , "W_WorkerCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PassWord = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PassWord" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_MstPassWord = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_MstPassWord" , "W_Password");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_MstConsignor = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_MstConsignor" , "W_MST_ConsignorMaster");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_MstSupplier = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_MstSupplier" , "W_MST_SupplierMaster");
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_MstItem = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_MstItem" , "W_MST_ItemMaster");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_MstSubmit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_MstSubmit" , "Submit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_MstClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_MstClear" , "Clear");

}
//end of class
