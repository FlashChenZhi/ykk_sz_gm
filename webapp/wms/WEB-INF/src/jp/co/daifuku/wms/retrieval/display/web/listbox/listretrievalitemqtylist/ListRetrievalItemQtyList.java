// $Id: ListRetrievalItemQtyList.java,v 1.2 2007/02/07 04:18:48 suresh Exp $

//#CM710420
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitemqtylist;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM710421
/**
 * Non variable class generated by a tool. 
 * 
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:48 $
 * @author  $Author: suresh $
 */
public class ListRetrievalItemQtyList extends Page
{

	//#CM710422
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ListName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ListName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SearchCondition = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SearchCondition" , "W_SearchCondition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Consignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Consignor" , "W_Consignor");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetCnsgnrCd = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetCnsgnrCd" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetCnsgnrNm = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetCnsgnrNm" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalPlanDate" , "W_RetrievalDate");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_FDateStart = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_FDateStart" , "W_F_Date");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromTo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromTo" , "W_FromTo");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_FDateEnd = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_FDateEnd" , "W_F_Date");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_U = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_U" , "Close");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_U = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_U" , "Pager");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InMsg = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InMsg" , "In_ErrorJsp");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ItemRtrivlRsltList = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ItemRtrivlRsltList" , "W_ItemRtrivlRsltList");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_D = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_D" , "Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_D = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_D" , "Close");

}
//#CM710423
//end of class
