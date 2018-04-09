// $Id: WorkPlace2.java,v 1.2 2006/10/30 05:29:12 suresh Exp $

//#CM54989
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.workplace;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM54990
/**
<kt> * The improper, strange class that the tool generated.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:29:12 $
 * @author  $Author: suresh $
 */
public class WorkPlace2 extends Page
{

	//#CM54991
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_WorkPlace_Create = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_WorkPlace_Create" , "AST_WorkPlace_Create");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Back = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Back" , "Back");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouseName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouseName" , "AST_WareHouseName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_WareHouseName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_WareHouseName" , "AST_In_Item");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ParentStnumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ParentStnumber" , "AST_ParentStnumber");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_ParentStationNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_ParentStationNumber" , "AST_In_Item");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ParentstationName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ParentstationName" , "AST_ParentstationName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_ParentStationName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_ParentStationName" , "AST_In_Item");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkPlaceTypeChar = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkPlaceTypeChar" , "AST_WorkPlaceTypeChar");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_WorkPlaceType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_WorkPlaceType" , "AST_In_Item");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StationNumberWorkPlaceNum = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StationNumberWorkPlaceNum" , "AST_StationNumberWorkPlaceNumber");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_StationNoWorkPlaceNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_StationNoWorkPlaceNo" , "AST_In_Item");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Search = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Search" , "AST_P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StationNameWorkPlaceName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StationNameWorkPlaceName" , "AST_StationNameWorkPlaceName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_StNameWorkPlaceName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_StNameWorkPlaceName" , "AST_In_Item");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StationTypeChar = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StationTypeChar" , "AST_StationTypeChar");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_StationType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_StationType" , "AST_In_Item");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AisleStationNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AisleStationNumber" , "AST_AisleStationNumber");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_AisleStationNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_AisleStationNumber" , "AST_In_Item");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AgcNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AgcNumber" , "AST_AgcNumber");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_AGCNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_AGCNumber" , "AST_In_Item");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Add = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Add" , "AST_Add");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "AST_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "AST_Cancel");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_WorkPlace = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_WorkPlace" , "AST_S_WorkPlace");

}
//#CM54992
//end of class
