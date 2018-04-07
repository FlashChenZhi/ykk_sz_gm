// $Id: Error.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web;
import jp.co.daifuku.bluedog.ui.control.Page;

/** <jp>
 * エラー画面の不可変クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:10 $
 * @author  $Author: mori $
 </jp> */
/** <en>
 * The invariable class of the Error Screen.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:10 $
 * @author  $Author: mori $
 </en> */
public class Error extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ErrorMsg1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ErrorMsg1" , "ErrorJspMsg1");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ErrorMsg2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ErrorMsg2" , "ErrorJspMsg2");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Error = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Error" , "In_ErrorJsp");

}
//end of class
