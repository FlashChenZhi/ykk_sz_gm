// $Id: ListIdmCaseItf.java,v 1.1.1.1 2006/08/17 09:34:09 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.idm.display.web.listbox.listidmcaseitf;
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:09 $
 * @author  $Author: mori $
 */
public class ListIdmCaseItf extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ListName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ListName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_1" , "W_SearchCondition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Consignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Consignor" , "W_Consignor");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetConsignorCode" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetConsignorName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetConsignorName" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Item = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Item" , "W_ItemCode");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetItemCode" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetItemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetItemName" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Flag" , "W_Flag");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetFlag" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Location = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Location" , "W_LocationRange");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetFromLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetFromLocation" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_11 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_11" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetToLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetToLocation" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_U = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_U" , "Close");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_U = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_U" , "Pager");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InMsg = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InMsg" , "In_ErrorJsp");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ListStockCaseItf = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ListStockCaseItf" , "W_ListStockCaseItf");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_D = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_D" , "Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_D = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_D" , "Close");

}
//end of class
