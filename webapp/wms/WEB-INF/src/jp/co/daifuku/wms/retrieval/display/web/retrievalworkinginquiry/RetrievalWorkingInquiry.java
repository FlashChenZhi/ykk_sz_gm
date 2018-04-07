// $Id: RetrievalWorkingInquiry.java,v 1.2 2007/02/07 04:19:33 suresh Exp $

//#CM719538
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalworkinginquiry;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM719539
/**
 * Non variable class generated by a tool. 
 * 
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:33 $
 * @author  $Author: suresh $
 */
public class RetrievalWorkingInquiry extends Page
{

	//#CM719540
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Inquiry = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Inquiry" , "W_Inquiry");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearch = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearch" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalPlanDate" , "W_RetrievalPlanDate");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_RtrivlPlanDate = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_RtrivlPlanDate" , "W_RtrivlPlanDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_View = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_View" , "View");
	public jp.co.daifuku.bluedog.ui.control.FixedListCell lst_SRtrivlWorkingIqrOrder = jp.co.daifuku.bluedog.ui.control.FixedListCellFactory.getInstance("lst_SRtrivlWorkingIqrOrder" , "W_S_RtrivlWorkingIqrOrder");
	public jp.co.daifuku.bluedog.ui.control.FixedListCell lst_SRtrivlWorkingIqrItem = jp.co.daifuku.bluedog.ui.control.FixedListCellFactory.getInstance("lst_SRtrivlWorkingIqrItem" , "W_S_RtrivlWorkingIqrItem");

}
//#CM719541
//end of class
