// $Id: AutoLogin.java,v 1.2 2006/11/07 06:25:27 suresh Exp $

//#CM664558
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web.login;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM664559
/**
 * An automatic log in screen not changeable class. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:25:27 $
 * @author  $Author: suresh $
 */
public class AutoLogin extends Page
{

	//#CM664560
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Message = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Message" , "In_ErrorJsp");

}
//#CM664561
//end of class
