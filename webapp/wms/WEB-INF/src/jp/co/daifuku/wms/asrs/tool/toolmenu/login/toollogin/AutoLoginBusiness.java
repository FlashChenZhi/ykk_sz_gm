// $Id: AutoLoginBusiness.java,v 1.2 2006/10/30 05:09:51 suresh Exp $

//#CM53635
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
import jp.co.daifuku.authentication.session.SessionAutoAuthentication;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.exception.AuthenticationException;
import jp.co.daifuku.logging.AppLogger;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;

//#CM53636
/** <en>
 * Auto Login screen.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:09:51 $
 * @author  $Author: suresh $
 </en> */
public class AutoLoginBusiness extends AutoLogin implements WMSToolConstants
{
	//#CM53637
	// Class fields --------------------------------------------------

	//#CM53638
	// Class variables -----------------------------------------------

	//#CM53639
	// Class method --------------------------------------------------

	//#CM53640
	// Constructors --------------------------------------------------

	//#CM53641
	// Public methods ------------------------------------------------

	//#CM53642
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM53643
		//Clear UserInfo
		if(this.getUserInfo() != null)
		{
			this.setUserInfo(null);
		}
		Connection conn = null;
		SessionAutoAuthentication auth = null;

		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM53644
			//<en>Create Authentication class</en>
			auth = new SessionAutoAuthentication();
			auth.setConn(conn);
			auth.setIPAddress(getHttpRequest().getRemoteAddr());
			auth.setSessionID(getSession().getId());
			//#CM53645
			//<en>When authentication succeeds, it is held in the session.</en>
			setUserInfo(auth.authenticate());
			//#CM53646
			//Get AuthenticationSystem
			AuthenticationSystem authSystem = auth.getAuthenticationSystem();
			//#CM53647
			//<en>AuthenticationSystem is held for the application.</en>
			getServletContext().setAttribute(SessionAuthentication.KEY_AUTH_SYSTEM, authSystem);

			//#CM53648
			//<en>A login success screen are indicated.</en>
			forward("/asrs/tool/login/Login.do"); // 2006/04/28 Updated By N.Sawa
		}
		catch(AuthenticationException ex)
		{
			String msg = ex.getMessage();
			lbl_Message.setText(DispResources.getText(msg));
		}
		catch(Exception ex)
		{
			//#CM53649
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
			//#CM53650
			//MSG-9109=Error occurred. {0}
			this.setAlert("MSG-9109" + "\t" + discription);
			//#CM53651
			//Rollback
			if(conn != null)conn.rollback();
		}	
		finally
		{
			//#CM53652
			//Close the conecction.
			if(conn != null) conn.close();
		}
	}
	
	//#CM53653
	/** <en>
	 * Override the page_LoginCheck method.
	 * @param e ActionEvent
	 </en> */
	public void page_LoginCheck(ActionEvent e) throws Exception
	{
	}

	//#CM53654
	// Package methods -----------------------------------------------

	//#CM53655
	// Protected methods ---------------------------------------------

	//#CM53656
	// Private methods -----------------------------------------------

	//#CM53657
	// Event handler methods -----------------------------------------



}
//#CM53658
//end of class
