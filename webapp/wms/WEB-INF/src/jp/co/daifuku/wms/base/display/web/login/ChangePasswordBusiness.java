// $Id: ChangePasswordBusiness.java,v 1.2 2006/11/07 06:24:40 suresh Exp $
//#CM664591
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web.login;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;


import jp.co.daifuku.authentication.AbstractAuthentication;
import jp.co.daifuku.authentication.AuthenticationSystem;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.PasswordRule;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.authentication.session.SessionAuthentication;
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.dbhandler.BaseHandler;
import jp.co.daifuku.logging.AppLogger;
import jp.co.daifuku.logging.Logger;
import jp.co.daifuku.wms.base.common.WMSConstants;

//#CM664592
/**
 * The password change screen class. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/06/26</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:24:40 $
 * @author  $Author: suresh $
 */
public class ChangePasswordBusiness extends ChangePassword implements WMSConstants
{
	//#CM664593
	// Class fields --------------------------------------------------

	//#CM664594
	// Class variables -----------------------------------------------

	//#CM664595
	// Class method --------------------------------------------------

	//#CM664596
	// Constructors --------------------------------------------------

	//#CM664597
	// Public methods ------------------------------------------------

	//#CM664598
	/**
	 * Initialize the screen. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
	
		String day = getViewState().getString("PASS_EXPIRES");
		String msg = DispResources.getText("LBL-9103", day);
		lbl_Msg0.setText(msg);
	}


	//#CM664599
	// Package methods -----------------------------------------------

	//#CM664600
	// Protected methods ---------------------------------------------

	//#CM664601
	// Private methods -----------------------------------------------

	//#CM664602
	// Event handler methods -----------------------------------------

	//#CM664603
	/**
	 * When the button is pressed as follows, it is called. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void btn_Next_Click(ActionEvent e) throws Exception
	{
		AuthenticationSystem authSystem = (AuthenticationSystem)getServletContext().getAttribute(SessionAuthentication.KEY_AUTH_SYSTEM);
		//#CM664604
		// The main menu and the log in success screen are displayed. 
		if (authSystem.getMainMenuType())
		{
			//#CM664605
			// Menu of large icon
			forward("/jsp/frameB.html");
		}
		else
		{
			//#CM664606
			// Menu of small icon
			forward("/jsp/frameA.html");
		}

	}

	//#CM664607
	/**
	 * When the correction button is pressed, it is called. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void btn_Modify_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			String oldPassword = txt_OldPassword.getText();
			String newPassword = txt_NewPassword.getText();
			String reenterPassword = txt_ReenterPassword.getText();
			String userID = getViewState().getString("USER_ID");
	
			//#CM664608
			// Mandatory input check
			if(oldPassword.equals(""))
			{
				//#CM664609
				// OldPassword does not check validity. 
				setAlert("MSG-9059");
				return;
			}		
			//#CM664610
			// Mandatory input check and validity check
			try
			{
				txt_NewPassword.validate(true);
				txt_ReenterPassword.validate(true);
			}
			catch(ValidateException ve)
			{
				setAlert("MSG-9100" + MSG_DELIM + MessageResources.getText(ve.getErrorNo(),ve.getBinds().toArray()));
				return ;
			}

			if(!newPassword.equals(reenterPassword))
			{
				//#CM664611
				// MSG-9055=The input password and the password for the confirmation are different. 
				setAlert("MSG-9055");
				return;
			}
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			SessionAuthentication auth = new SessionAuthentication();
			
			//#CM664612
			// Confirmation failure
			if(!auth.authenticate(conn, userID, oldPassword))
			{
				//#CM664613
				// Input the present password correctly. 
				setAlert("MSG-9066");
				return;
			}
			
			//#CM664614
			// Made with FTTB forName instance. 
			PasswordRule rule = new PasswordRule();
			rule.setOldPassword(oldPassword);
			rule.setNewPassword(newPassword);
			rule.setUserID(userID);

			if(!rule.validatePassword())
			{
				setAlert(rule.getReason());
				return;
			}
			else
			{
				UserInfoHandler userhandle = new UserInfoHandler((DfkUserInfo)this.getUserInfo()); 
				//#CM664615
				// Update interval
				int interval = userhandle.getPwdChangeInterval();
				//#CM664616
				// Present password expiration date
				Date expireDate = userhandle.getExpires();

				String pwdExDate = "";
				Date pwdExpireDate = null;
				//#CM664617
				// Do the expiration date indefinitely when the interval is -1. 
				if(interval == 0 || interval == -1)
				{
					pwdExDate = null;
					pwdExpireDate = null;
				}
				else
				{
					Calendar cal = Calendar.getInstance();
					cal.setTime(expireDate);
					cal.add(Calendar.DATE, interval);
					//#CM664618
					// Password expiration date
					pwdExpireDate = cal.getTime();
					pwdExDate = DBFormat.format(pwdExpireDate);
				}
				//#CM664619
				// ---- Data on DB is corrected.  ----
				BaseHandler handle = new BaseHandler();
				handle.modify("_update-0001b", new String[] { newPassword, pwdExDate, userID }, conn);

				//#CM664620
				// ---- Data on Session is corrected.  ----
				userhandle.changePassword(newPassword, pwdExpireDate);
				//#CM664621
				//Commit
				conn.commit();

				//#CM664622
				// 6000503=The password was changed. User={0}/{1}->{2}
				String msg = "6000503" + MSG_DELIM + userID + MSG_DELIM + oldPassword + MSG_DELIM + newPassword;
				//#CM664623
				// Output to the confirmation log. 
				Logger.auth(userID, getHttpRequest().getRemoteAddr(), AbstractAuthentication.AUTHLOG_CHANGE_PASSWORD, msg);
				//#CM664624
				// MSG-9063=The password was changed. 
				this.setAlert("MSG-9063");
			}
		}
		catch(Exception ex)
		{
			//#CM664625
			// 6006001=The error not anticipated occurred. {0}
			AppLogger.write(6006001, ex, this.getClass());
			String discription = "";
			if(ex.getCause() != null)
			{
				discription = ex.getCause().toString();
			}
			else
			{ 
				discription = ex.getMessage();
			}
			//#CM664626
			// MSG-9109=The error occurred. {0}
			this.setAlert("MSG-9109" + "\t" + discription);
			//#CM664627
			// Rollback
			if(conn != null)conn.rollback();
		}
		finally
		{
			//#CM664628
			// Connection close
			if(conn != null) conn.close();
		}
	}
}
//#CM664629
//end of class
