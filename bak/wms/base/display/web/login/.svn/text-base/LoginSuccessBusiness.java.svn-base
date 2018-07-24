// $Id: LoginSuccessBusiness.java,v 1.2 2006/11/07 06:23:01 suresh Exp $
//#CM664691
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web.login;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.system.schedule.WorkingDateSetSCH;


//#CM664692
/**
 * The login success screen class. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:23:01 $
 * @author  $Author: suresh $
 */
public class LoginSuccessBusiness extends LoginSuccess implements WMSConstants
{
	//#CM664693
	// Class fields --------------------------------------------------

	//#CM664694
	// Class variables -----------------------------------------------

	//#CM664695
	// Class method --------------------------------------------------

	//#CM664696
	// Constructors --------------------------------------------------

	//#CM664697
	// Public methods ------------------------------------------------

	//#CM664698
	/**
	 * Initialize the screen. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();
			UserInfoHandler userhandler = new UserInfoHandler(userinfo);

			lbl_Msg01.setText(DispResources.getText("LBL-1002", userhandler.getUserName()));
			WorkingDateSetSCH wordateSCH = new WorkingDateSetSCH();
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			SystemParameter param = (SystemParameter)wordateSCH.initFind(conn, null);
//			txt_WorkDay.setText(WmsFormatter.toDispDate(param.getWorkDate(),this.getHttpRequest().getLocale()));

		}
		catch (Exception ex)
		{
			setAlert("MSG-9100" + MSG_DELIM + MessageResources.getText(ExceptionHandler.getDisplayMessage(ex, this.getClass())));
		}
		finally
		{
			try
			{
				//#CM664699
				// Close the connection
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				setAlert("MSG-9100" + MSG_DELIM + MessageResources.getText(ExceptionHandler.getDisplayMessage(se, this.getClass())));
			}
		}
	}


	//#CM664700
	// Package methods -----------------------------------------------

	//#CM664701
	// Protected methods ---------------------------------------------

	//#CM664702
	// Private methods -----------------------------------------------

	//#CM664703
	// Event handler methods -----------------------------------------

	//#CM664704
	/**
	 * When the correction button is pressed, it is called. 
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception Reports on all exceptions. 
	 */
//	public void btn_Modify_Click(ActionEvent e) throws Exception
//	{
//		Connection conn = null;
//		try
//		{
//			if (txt_WorkDay.getText() == null || StringUtil.isBlank(txt_WorkDay.getText()))
//			{
//				this.setAlert("MSG-W0038");
//				return;
//			}
//			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
//			WareNaviSystemAlterKey alterKey = new WareNaviSystemAlterKey();
//			alterKey.setSystemNo(WareNaviSystem.SYSTEM_NO); // set record number for update
//			alterKey.updateWorkDate(WmsFormatter.toParamDate(txt_WorkDay.getText(),this.getHttpRequest().getLocale() )); // update with new work date
//			WareNaviSystemHandler systemhandler = new WareNaviSystemHandler(conn);
//			systemhandler.modify(alterKey); // update
//			conn.commit();
//		}
//		catch (Exception ex)
//		{
//			setAlert("MSG-9100" + MSG_DELIM + MessageResources.getText(ExceptionHandler.getDisplayMessage(ex, this.getClass())));
//		}
//		finally
//		{
//			try
//			{
//				//#CM664705
//				// Connection close
//				if (conn != null)
//					conn.close();
//			}
//			catch (SQLException se)
//			{
//				setAlert("MSG-9100" + MSG_DELIM + MessageResources.getText(ExceptionHandler.getDisplayMessage(se, this.getClass())));
//			}
//		}
//	}	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Msg01_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Msg02_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM664706
//end of class
