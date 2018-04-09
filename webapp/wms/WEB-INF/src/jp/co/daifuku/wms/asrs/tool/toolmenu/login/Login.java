// $Id: Login.java,v 1.2 2006/10/30 05:05:27 suresh Exp $

//#CM53555
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.login;
import jp.co.daifuku.bluedog.ui.control.Page;

//#CM53556
/**
 * Improper, strange Class that the tool generated. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:05:27 $
 * @author  $Author: suresh $
 */
public class Login extends Page
{

	//#CM53557
	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Production = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Production" , "AST_Production");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ProductionNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ProductionNumber" , "AST_ProductionNumber");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ProductionNumber = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ProductionNumber" , "AST_ProductionNumber");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Next = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Next" , "AST_BC_Next");

}
//#CM53558
//end of class
