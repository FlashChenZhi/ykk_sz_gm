// $Id: WareHouse.java,v 1.2 2006/10/30 05:23:51 suresh Exp $

//#CM54744
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.warehouse;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM54745
/**
<kt> * The improper, strange class that the tool generated.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:23:51 $
 * @author  $Author: suresh $
 */
public class WareHouse extends Page
{

	//#CM54746
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Create = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Create" , "AST_Create");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouseNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouseNumber" , "AST_WareHouseNumber");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_WareHouseNumber = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_WareHouseNumber" , "AST_WareHouseNumber");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StationNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StationNumber" , "AST_StationNumber");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StNumber" , "AST_StNumber");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouseName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouseName" , "AST_WareHouseName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WareHouseName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WareHouseName" , "AST_WareHouseName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouseType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouseType" , "AST_WareHouseType");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AutomaticWareHouseSystem = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AutomaticWareHouseSystem" , "AST_AutomaticWareHouseSystem");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FloorLoadingWareHouseSys = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FloorLoadingWareHouseSys" , "AST_FloorLoadingWareHouseSystem");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AWCUseType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AWCUseType" , "AST_AWCUseType");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Open = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Open" , "AST_Open");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Close = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Close" , "AST_Close");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxMixedQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxMixedQty" , "AST_MaxMixedQty");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_MaxMixedQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_MaxMixedQty" , "AST_MaxMixedQty");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Add = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Add" , "AST_Add");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "AST_Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "AST_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "AST_Cancel");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_WareHouse = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_WareHouse" , "AST_S_WareHouse");

}
//#CM54747
//end of class
