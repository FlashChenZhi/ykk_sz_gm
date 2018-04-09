// $Id: ShippingWorkingInquiry.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.shipping.display.web.shippingworkinginquiry;
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class ShippingWorkingInquiry extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Inquiry = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Inquiry" , "W_Inquiry");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchCnsgnr = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchCnsgnr" , "P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ShippingPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ShippingPlanDate" , "W_ShippingPlanDate");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_ShippingPlanDate = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_ShippingPlanDate" , "W_ShippingPlanDate");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_View = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_View" , "View");
	public jp.co.daifuku.bluedog.ui.control.FixedListCell lst_SShpStuIqrShp = jp.co.daifuku.bluedog.ui.control.FixedListCellFactory.getInstance("lst_SShpStuIqrShp" , "W_S_ShpStuIqrShp");
	public jp.co.daifuku.bluedog.ui.control.FixedListCell lst_SShpStuIqrTCShp = jp.co.daifuku.bluedog.ui.control.FixedListCellFactory.getInstance("lst_SShpStuIqrTCShp" , "W_S_ShpStuIqrTCShp");
	public jp.co.daifuku.bluedog.ui.control.FixedListCell lst_SShpStuIqrDCShp = jp.co.daifuku.bluedog.ui.control.FixedListCellFactory.getInstance("lst_SShpStuIqrDCShp" , "W_S_ShpStuIqrDCShp");
	public jp.co.daifuku.bluedog.ui.control.FixedListCell lst_SShpStuIqrCrTCShp = jp.co.daifuku.bluedog.ui.control.FixedListCellFactory.getInstance("lst_SShpStuIqrCrTCShp" , "W_S_ShpStuIqrCrTCShp");

}
//end of class
