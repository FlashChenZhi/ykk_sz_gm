// $Id: ListIdmEmpLocation.java,v 1.1.1.1 2006/08/17 09:34:09 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.idm.display.web.listbox.listidmemplocation;
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
public class ListIdmEmpLocation extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ListName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ListName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_9 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_9" , "W_SearchCondition");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationNo" , "W_LocationNo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetLocation" , "W_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_U = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_U" , "Close");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_U = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_U" , "Pager");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InMsg = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InMsg" , "In_ErrorJsp");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ListLocationSearch = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ListLocationSearch" , "W_LocationSearch");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_D = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_D" , "Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_D = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_D" , "Close");

}
//end of class
