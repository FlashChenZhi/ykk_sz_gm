// $Id: ListStorageItem.java,v 1.2 2006/12/07 08:57:41 suresh Exp $

//#CM568474
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM568475
/**
 * The improper, strange class that the tool generated. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:41 $
 * @author  $Author: suresh $
 */
public class ListStorageItem extends Page
{

	//#CM568476
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ListName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ListName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_U = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_U" , "Close");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_U = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_U" , "Pager");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InMsg = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InMsg" , "In_ErrorJsp");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ItemSearch = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ItemSearch" , "W_ItemSearch");
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_D = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_D" , "Pager");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_D = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_D" , "Close");

}
//#CM568477
//end of class
