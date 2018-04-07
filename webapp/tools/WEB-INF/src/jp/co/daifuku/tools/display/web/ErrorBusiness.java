// $Id: ErrorBusiness.java,v 1.1.1.1 2006/08/17 09:33:10 mori Exp $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.tools.display.web;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.UserInfo;

/** <jp>
 * エラー発生時に表示する画面クラスです。
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
 * It is a screen class to indicate at the time of the error occurrence.
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
public class ErrorBusiness extends Error
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/** <jp>
	 * 画面の初期化を行います。

	 * @param e ActionEvent
	 </jp> */
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
	}


	/** <jp>
	 * ログインチェックをオーバライドします。
	 * @param e ActionEvent
	 </jp> */
	/** <en>
	 * Override the page_LoginCheck method.
	 * @param e ActionEvent
	 </en> */
	public void page_LoginCheck(ActionEvent e) throws Exception
	{
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	// Event handler methods -----------------------------------------
	public void lbl_ErrorMsg2_Server(ActionEvent e) throws Exception
	{
	}
	public void lbl_ErrorMsg1_Server(ActionEvent e) throws Exception
	{
	}

	public void lbl_Error_Server(ActionEvent e) throws Exception
	{
	}


}
//end of class
