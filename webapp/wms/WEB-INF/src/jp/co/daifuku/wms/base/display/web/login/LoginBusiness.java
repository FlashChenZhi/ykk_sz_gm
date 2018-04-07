// $Id: LoginBusiness.java,v 1.2 2006/11/07 06:24:02 suresh Exp $
//#CM664634
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web.login;

import java.sql.Connection;
import jp.co.daifuku.authentication.AuthenticationSystem;
import jp.co.daifuku.authentication.session.SessionAuthentication;
import jp.co.daifuku.exception.AuthenticationException;
import jp.co.daifuku.logging.AppLogger;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;

// #CM664635
/**
 * The login screen class.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/09/13</TD>
 * <TD>Kawashima</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:24:02 $
 * @author $Author: suresh $
 */
public class LoginBusiness extends Login implements WMSConstants
{
	// #CM664636
	// Class fields --------------------------------------------------

	// #CM664637
	// Class variables -----------------------------------------------

	// #CM664638
	// Class method --------------------------------------------------

	// #CM664639
	// Constructors --------------------------------------------------

	// #CM664640
	// Public methods ------------------------------------------------

	// #CM664647
	// Event handler methods -----------------------------------------
	// #CM664648
	/**
	 * The processing when the login button is pressed.
	 * 
	 * @param e
	 *            ActionEvent The class which stores information on the event.
	 * @throws Exception
	 *             Reports on all exceptions.
	 */
	public void btn_Login_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		SessionAuthentication auth = null;

		if (txt_LoginID.getText() == null || txt_LoginID.getText().equals(""))
		{
			// #CM664649
			// MSG-9064=Input user ID.
			this.setAlert("MSG-9064");
			setFocus(txt_LoginID);
			return;
		}
		// else if (password == null || password.equals(""))
		// {
		// // #CM664650
		// // MSG-9065=Input the password.
		// this.setAlert("MSG-9065");
		// setFocus(txt_Password);
		// return;
		// }

		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			// #CM664651
			// Confirmation class generation
			auth = new SessionAuthentication();
			auth.setConn(conn);
			String test = getHttpRequest().getRemoteAddr();
			auth.setIPAddress(getHttpRequest().getRemoteAddr());
			auth.setSessionID(getSession().getId());
			auth.setUserID(txt_LoginID.getText());
			auth.setPassword(txt_Password.getText());

			// #CM664652
			// When attestation -> succeeds, it maintains the session.
			setUserInfo(auth.authenticate());
			// #CM664653
			// Acquire Authentication System
			AuthenticationSystem authSystem = auth.getAuthenticationSystem();
			// #CM664654
			// AuthenticationSystem is maintained in the application.
			getServletContext().setAttribute(
					SessionAuthentication.KEY_AUTH_SYSTEM, authSystem);

			// #CM664655
			// Interval of expiration date
			if (auth.getResult() == SessionAuthentication.RET_PASS_EXPIRES)
			{
				String pass_expires = (String) auth.getReason().get(0);
				getViewState().setString("PASS_EXPIRES", pass_expires);
				getViewState().setString("USER_ID", txt_LoginID.getText());
				// #CM664656
				// Changes to the password change screen.
				forward("/login/ChangePassword.do");
			}
			else
			{
				// #CM664657
				// The main menu and the log in success screen are displayed.
				if (authSystem.getMainMenuType())
				{
					// #CM664658
					// Menu of large icon
					forward("/jsp/frameB.html");
				}
				else
				{
					// #CM664659
					// Menu of small icon
					forward("/jsp/frameA.html");
				}
			}

		}
		catch (AuthenticationException ex)
		{
			String msg = ex.getMessage();
			// #CM664660
			// When you invalidly login the same user login
			// #CM664661
			// It changes to LoginCheck.jsp when a capable number of log in is
			// effectively exceeded using the same user log in.
			if (auth.getResult() == SessionAuthentication.RET_LOGINCOUNT_OVER)
			{
				getViewState().setString("USER_ID", txt_LoginID.getText());
				forward("/login/LoginCheck.do");
			}
			else
			{
				this.setAlert(msg);
				setFocus(txt_LoginID);
			}
		}
		catch (Exception ex)
		{
			// #CM664662
			// 6006001=The error not anticipated occurred. {0}
			AppLogger.write(6006001, ex, this.getClass());
			String discription = "";
			if (ex.getCause() != null)
			{
				discription = ex.getCause().toString();
			}
			else
			{
				discription = ex.getMessage();
			}
			// #CM664663
			// MSG-9109=The error occurred. {0}
			this.setAlert("MSG-9109" + "\t" + discription);
			// #CM664664
			// Rollback
			if (conn != null)
				conn.rollback();
		}
		finally
		{
			if (conn != null)
			{
				conn.commit();
				conn.close();
			}
		}
	}

	// #CM664641
	/**
	 * Initialize the screen.
	 * 
	 * @param e
	 *            ActionEvent The class which stores information on the event.
	 * @throws Exception
	 *             Reports on all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// #CM664642
		// Clear User Info
		if (this.getUserInfo() != null)
		{
			this.setUserInfo(null);
		}

		setFocus(txt_LoginID);
	}

	// #CM664644
	// Package methods -----------------------------------------------

	// #CM664645
	// Protected methods ---------------------------------------------

	// #CM664646
	// Private methods -----------------------------------------------

	// #CM664643
	/**
	 * Override the login check.
	 * 
	 * @param e
	 *            ActionEvent The class which stores information on the event.
	 * @throws Exception
	 *             Reports on all exceptions.
	 */
	public void page_LoginCheck(ActionEvent e) throws Exception
	{
	}

	// #CM664665
	/**
	 * Submit and confirm if 'Enter' is pushed in the text box.
	 * 
	 * @param e
	 *            ActionEvent The class which stores information on the event.
	 * @throws Exception
	 *             Reports on all exceptions.
	 */
	public void txt_LoginID_EnterKey(ActionEvent e) throws Exception
	{
		btn_Login_Click(null);
	}

	// #CM664666
	/**
	 * Submit and confirm if 'Enter' is pushed in the text box.
	 * 
	 * @param e
	 *            ActionEvent The class which stores information on the event.
	 * @throws Exception
	 *             Reports on all exceptions.
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
		btn_Login_Click(null);
	}
}
// #CM664667
// end of class
