// $Id: LoginBusiness.java,v 1.2 2006/10/30 05:09:49 suresh Exp $
//#CM53706
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.login.toollogin;

import java.sql.Connection;
import jp.co.daifuku.authentication.AuthenticationSystem;
import jp.co.daifuku.authentication.session.SessionAuthentication;
import jp.co.daifuku.exception.AuthenticationException;
import jp.co.daifuku.logging.AppLogger;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;


//#CM53707
/** <en>
 * This is login screen.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:09:49 $
 * @author  $Author: suresh $
 </en> */
public class LoginBusiness extends Login implements WMSToolConstants
{
	//#CM53708
	// Class fields --------------------------------------------------

	//#CM53709
	// Class variables -----------------------------------------------

	//#CM53710
	// Class method --------------------------------------------------

	//#CM53711
	// Constructors --------------------------------------------------

	//#CM53712
	// Public methods ------------------------------------------------

	//#CM53713
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM53714
		//Clear UserInfo
		if(this.getUserInfo() != null)
		{
			this.setUserInfo(null);
		}
		
		setFocus(txt_LoginID);
	}

	//#CM53715
	/** <en>
	 * Override the page_LoginCheck method.
	 * @param e ActionEvent
	 </en> */
	public void page_LoginCheck(ActionEvent e) throws Exception
	{
	}

	//#CM53716
	// Package methods -----------------------------------------------

	//#CM53717
	// Protected methods ---------------------------------------------

	//#CM53718
	// Private methods -----------------------------------------------

	//#CM53719
	// Event handler methods -----------------------------------------
	//#CM53720
	/** <en>
	 * It is the event when a login button is pushed.
	 * @param e ActionEvent
	 </en> */
	public void btn_Login_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		SessionAuthentication auth = null;
		String userid = txt_LoginID.getText();
		String password = txt_Password.getText();
		if(userid == null || userid.equals(""))
		{
			//#CM53721
			//<en>MSG-9064=Enter the user ID.</en>
			this.setAlert("MSG-9064");
			setFocus(txt_LoginID);
			return;
		}
		else if(password == null || password.equals(""))
		{
			//#CM53722
			//<en>MSG-9065=Enter the Password.</en>
			this.setAlert("MSG-9065");
			setFocus(txt_Password);
			return;
		}

		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM53723
			//<en>Create Authentication class</en>
			auth = new SessionAuthentication();
			auth.setConn(conn);
			auth.setIPAddress(getHttpRequest().getRemoteAddr());
			auth.setSessionID(getSession().getId());
			auth.setUserID(txt_LoginID.getText());
			auth.setPassword(txt_Password.getText());
			
			//#CM53724
			//<en>When authentication succeeds, it is held in the session.</en>
			setUserInfo(auth.authenticate());
			//#CM53725
			//Get AuthenticationSystem
			AuthenticationSystem authSystem = auth.getAuthenticationSystem();
			//#CM53726
			//<en>AuthenticationSystem is held for the application.</en>
			getServletContext().setAttribute(SessionAuthentication.KEY_AUTH_SYSTEM, authSystem);
			
			//#CM53727
			//<en>A term of validity limit is near at hand.</en>
			if(auth.getResult() == SessionAuthentication.RET_PASS_EXPIRES)
			{
				String pass_expires = (String)auth.getReason().get(0);
				getViewState().setString("PASS_EXPIRES", pass_expires);
				getViewState().setString("USER_ID", txt_LoginID.getText());
				//#CM53728
				//<en>Move to the password change screen.</en>
				forward("/asrs/tool/login/toollogin/ChangePassword.do"); // 2006/04/28 Updated By N.Sawa
			}
			else
			{
				//#CM53729
				//<en>A login success screen are indicated.</en>
				forward("/asrs/tool/login/Login.do"); // 2006/04/28 Updated By N.Sawa
			}
		}
		catch(AuthenticationException ex)
		{
			String msg = ex.getMessage();
			//#CM53730
			//<en>When you invalidly log in the same user log in</en>
			//#CM53731
			//<en>It changes to LoginCheck.jsp when log in Allocated Qty is effectively exceeded the same user log in. </en>
			if(auth.getResult() == SessionAuthentication.RET_LOGINCOUNT_OVER)
			{
				getViewState().setString("USER_ID", txt_LoginID.getText());
				forward("/asrs/tool/login/toollogin/LoginCheck.do"); // 2006/04/28 Updated By N.Sawa
			}
			else
			{
				this.setAlert(msg);
				setFocus(txt_LoginID);
			}
		}
		catch(Exception ex)
		{
			//#CM53732
			//6006001=Unexpected error occurred.{0}
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
			//#CM53733
			//MSG-9109=Error occurred. {0}
			this.setAlert("MSG-9109" + "\t" + discription);
			//#CM53734
			//Rollback
			if(conn != null)conn.rollback();
		}
		finally
		{
			if(conn != null)
			{
				conn.commit();
				conn.close();
			}
		}
	}

	//#CM53735
	/**<en>
	 * A submit is done and certified if an Enter key is stamped in the text box.
	 * @param e
	 * @throws Exception
	 </en>*/
	public void txt_LoginID_EnterKey(ActionEvent e) throws Exception
	{
		btn_Login_Click(null);
	}
	//#CM53736
	/**<en>
	 * A submit is done and certified if an Enter key is stamped in the text box.
	 * @param e
	 * @throws Exception
	 </en>*/
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
		btn_Login_Click(null);
	}
}
//#CM53737
//end of class
