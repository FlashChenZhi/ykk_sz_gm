// $Id: ChangePasswordBusiness.java,v 1.2 2006/10/30 05:09:50 suresh Exp $
//#CM53663
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.login.toollogin;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;


import jp.co.daifuku.authentication.AbstractAuthentication;
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
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;

//#CM53664
/** <en>
 * It is a password change screen class.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/06/26</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:09:50 $
 * @author  $Author: suresh $
 </en> */
public class ChangePasswordBusiness extends ChangePassword implements WMSToolConstants
{
	//#CM53665
	// Class fields --------------------------------------------------

	//#CM53666
	// Class variables -----------------------------------------------

	//#CM53667
	// Class method --------------------------------------------------

	//#CM53668
	// Constructors --------------------------------------------------

	//#CM53669
	// Public methods ------------------------------------------------

	//#CM53670
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
	
		String day = getViewState().getString("PASS_EXPIRES");
		String msg = DispResources.getText("LBL-9103", day);
		lbl_Msg0.setText(msg);
		
		//#CM53671
		//setUserInfo(new UserInfo());
	}


	//#CM53672
	// Package methods -----------------------------------------------

	//#CM53673
	// Protected methods ---------------------------------------------

	//#CM53674
	// Private methods -----------------------------------------------

	//#CM53675
	// Event handler methods -----------------------------------------

	//#CM53676
	/** <en>
	 * It is called when a Next button is clicked.
	 * @param e ActionEvent
	 </en> */
	public void btn_Next_Click(ActionEvent e) throws Exception
	{
		//#CM53677
		//<en>A login success screen are indicated.</en>
		forward("/asrs/tool/login/Login.do"); // 2006/04/28 Updated By N.Sawa
	}

	//#CM53678
	/** <en>
	 *  It is called when a Modify button is clicked.
	 * @param e ActionEvent
	 </en> */
	public void btn_Modify_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			String oldPassword = txt_OldPassword.getText();
			String newPassword = txt_NewPassword.getText();
			String reenterPassword = txt_ReenterPassword.getText();
			String userID = getViewState().getString("USER_ID");
	
			//#CM53679
			//Mandatory Input check
			if(oldPassword.equals(""))
			{
				//#CM53680
				//OldPassword does not check validity. 
				setAlert("MSG-9059");
				return;
			}		
			//#CM53681
			//Mandatory Input check and validity check
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
				//#CM53682
				//MSG-9055=New Password and Confirmation Password are different. 
				setAlert("MSG-9055");
				return;
			}
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			SessionAuthentication auth = new SessionAuthentication();
			
			//#CM53683
			//Attestation failure
			if(!auth.authenticate(conn, userID, oldPassword))
			{
				//#CM53684
				//Input current Password correctly. 
				setAlert("MSG-9066");
				return;
			}
			
			//#CM53685
			//It makes it with FTTB forName the instance. 
			PasswordRule rule = new PasswordRule();
			rule.setOldPassword(oldPassword);
			rule.setNewPassword(newPassword);
			rule.setUserID(userID);
			//#CM53686
			//rule.setLocation(this.getSession().getId());
			if(!rule.validatePassword())
			{
				setAlert(rule.getReason());
				return;
			}
			else
			{
				UserInfoHandler userhandle = new UserInfoHandler((DfkUserInfo)this.getUserInfo()); 
				//#CM53687
				// Update interval
				int interval = userhandle.getPwdChangeInterval();
				//#CM53688
				//Present Password expiration date
				Date expireDate = userhandle.getExpires();

				String pwdExDate = "";
				Date pwdExpireDate = null;
				//#CM53689
				//Do the expiration date indefinitely when the interval is -1. 
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
					//#CM53690
					//Password expiration date
					pwdExpireDate = cal.getTime();
					pwdExDate = DBFormat.format(pwdExpireDate);
				}
				//#CM53691
				//---- Data on DB is corrected.  ----
				BaseHandler handle = new BaseHandler();
				handle.modify("_update-0001b", new String[] { newPassword, pwdExDate, userID }, conn);

				//#CM53692
				//---- Data on Session is corrected. ----
				userhandle.changePassword(newPassword, pwdExpireDate);
				//#CM53693
				//Comment
				conn.commit();

				//#CM53694
				// 6000503= Password was changed..User={0}/{1}->{2}
				String msg = "6000503" + MSG_DELIM + userID + MSG_DELIM + oldPassword + MSG_DELIM + newPassword;
				//#CM53695
				// Authentication log writing (Change password)
				Logger.auth(userID, getHttpRequest().getRemoteAddr(), AbstractAuthentication.AUTHLOG_CHANGE_PASSWORD, msg);
				//#CM53696
				//MSG-9063= Password was changed..
				this.setAlert("MSG-9063");
			}
		}
		catch(Exception ex)
		{
			//#CM53697
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
			//#CM53698
			//MSG-9109=Error occurred. {0}
			this.setAlert("MSG-9109" + "\t" + discription);
			//#CM53699
			//Rollback
			if(conn != null)conn.rollback();
		}
		finally
		{
			//#CM53700
			//Close the Connection
			if(conn != null) conn.close();
		}
	}
}
//#CM53701
//end of class
