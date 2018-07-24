// $Id: AutoLoginBusiness.java,v 1.2 2006/11/07 06:25:11 suresh Exp $

//#CM664562
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
import jp.co.daifuku.authentication.session.SessionAutoAuthentication;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.exception.AuthenticationException;
import jp.co.daifuku.logging.AppLogger;
import jp.co.daifuku.wms.base.common.WMSConstants;

//#CM664563
/**
 * Automatic log in screen
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:25:11 $
 * @author  $Author: suresh $
 */
public class AutoLoginBusiness extends AutoLogin implements WMSConstants
{
	//#CM664564
	// Class fields --------------------------------------------------

	//#CM664565
	// Class variables -----------------------------------------------

	//#CM664566
	// Class method --------------------------------------------------

	//#CM664567
	// Constructors --------------------------------------------------

	//#CM664568
	// Public methods ------------------------------------------------

	//#CM664569
	/**
	 * Initialize the screen. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM664570
		// Clear User Info
		if(this.getUserInfo() != null)
		{
			this.setUserInfo(null);
		}
		Connection conn = null;
		SessionAutoAuthentication auth = null;

		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM664571
			// Confirmation class generation
			auth = new SessionAutoAuthentication();
			auth.setConn(conn);
			auth.setIPAddress(getHttpRequest().getRemoteAddr());
			auth.setSessionID(getSession().getId());
			//#CM664572
			// When attestation -> succeeds, it maintains the session. 
			setUserInfo(auth.authenticate());

			AuthenticationSystem authSystem = auth.getAuthenticationSystem();
			//#CM664573
			// AuthenticationSystem is maintained in the application. 
			getServletContext().setAttribute(SessionAuthentication.KEY_AUTH_SYSTEM, authSystem);

			//#CM664574
			// The main menu and the log in success screen are displayed. 
			httpRequest.setAttribute("AUTO_LOGIN_SUCCESS","true");
			if (authSystem.getMainMenuType())
			{
				//#CM664575
				// Menu of large icon
				addOnloadScript("location.href='" + httpRequest.getContextPath() + "/jsp/frameB.html'");
			}
			else
			{
				//#CM664576
				// Menu of small icon
				addOnloadScript("location.href='" + httpRequest.getContextPath() + "/jsp/frameA.html'");
			}
		}
		catch(AuthenticationException ex)
		{
			String msg = ex.getMessage();
			lbl_Message.setText(DispResources.getText(msg));
		}
		catch(Exception ex)
		{
		    //#CM664577
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
			//#CM664578
			// MSG-9109=The error occurred. {0}
			this.setAlert("MSG-9109" + "\t" + discription);
			//#CM664579
			// Rollback
			if(conn != null)conn.rollback();
		}	
		finally
		{
			//#CM664580
			// Connection close
			if(conn != null) conn.close();
		}
	}
	
	//#CM664581
	/**
	 * Override the login check. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void page_LoginCheck(ActionEvent e) throws Exception
	{
	}

	//#CM664582
	// Package methods -----------------------------------------------

	//#CM664583
	// Protected methods ---------------------------------------------

	//#CM664584
	// Private methods -----------------------------------------------

	//#CM664585
	// Event handler methods -----------------------------------------



}
//#CM664586
//end of class
