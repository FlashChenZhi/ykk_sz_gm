// $Id: ListStoragePlanList.java,v 1.2 2006/12/07 08:57:38 suresh Exp $

//#CM569253
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.listbox.liststorageplanlist;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM569254
/**
 * The improper, strange class that the tool generated. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:38 $
 * @author  $Author: suresh $
 */
public class ListStoragePlanList extends Page
{

	//#CM569255
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ListName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ListName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SearchCondition = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SearchCondition" , "W_SearchCondition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Consignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Consignor" , "W_Consignor");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetCnsgnrCd = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetCnsgnrCd" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetCnsgnrNm = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetCnsgnrNm" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StoragePlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StoragePlanDate" , "W_StoragePlanDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_FDateStrt = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_FDateStrt" , "W_F_Date");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromTo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromTo" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_FDateEd = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_FDateEd" , "W_F_Date");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_U = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_U" , "Close");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_U = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_U" , "Pager");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InMsg = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InMsg" , "In_ErrorJsp");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_StoragePlanList = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_StoragePlanList" , "W_StoragePlanList");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_D = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_D" , "Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_D = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_D" , "Close");

}
//#CM569256
//end of class
