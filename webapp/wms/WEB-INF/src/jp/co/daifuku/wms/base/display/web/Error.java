// $Id: Error.java,v 1.2 2006/11/07 06:56:14 suresh Exp $
//#CM664467
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM664468
/**
 * An improper, strange class on the error screen. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:56:14 $
 * @author  $Author: suresh $
 */
public class Error extends Page
{
	//#CM664469
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ErrorMsg1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ErrorMsg1" , "ErrorJspMsg1");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ErrorMsg2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ErrorMsg2" , "ErrorJspMsg2");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Error = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Error" , "In_ErrorJsp");
}
//#CM664470
//end of class
