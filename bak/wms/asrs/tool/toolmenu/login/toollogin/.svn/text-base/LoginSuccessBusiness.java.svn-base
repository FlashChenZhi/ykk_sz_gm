// $Id: LoginSuccessBusiness.java,v 1.2 2006/10/30 05:09:48 suresh Exp $
//#CM53761
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.login.toollogin;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.system.schedule.WorkingDateSetSCH;


//#CM53762
/** <en>
 * This is login success screen class.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 05:09:48 $
 * @author  $Author: suresh $
 </en> */
public class LoginSuccessBusiness extends LoginSuccess implements WMSToolConstants
{
	//#CM53763
	// Class fields --------------------------------------------------

	//#CM53764
	// Class variables -----------------------------------------------

	//#CM53765
	// Class method --------------------------------------------------

	//#CM53766
	// Constructors --------------------------------------------------

	//#CM53767
	// Public methods ------------------------------------------------

	//#CM53768
	/** <en>
	 * This screen is initialized.
	 * @param e ActionEvent
	 </en> */
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
			txt_WorkDay.setText(WmsFormatter.toDispDate(param.getWorkDate(),this.getHttpRequest().getLocale()));

		}
		catch (Exception ex)
		{
			setAlert("MSG-9100" + MSG_DELIM + MessageResources.getText(ExceptionHandler.getDisplayMessage(ex, this.getClass())));
		}
		finally
		{
			try
			{
				//#CM53769
				//<en>Close connection.</en>
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				setAlert("MSG-9100" + MSG_DELIM + MessageResources.getText(ExceptionHandler.getDisplayMessage(se, this.getClass())));
			}
		}
	}


	//#CM53770
	// Package methods -----------------------------------------------

	//#CM53771
	// Protected methods ---------------------------------------------

	//#CM53772
	// Private methods -----------------------------------------------

	//#CM53773
	// Event handler methods -----------------------------------------

		public void btn_Modify_Click(ActionEvent e) throws Exception
		{
			Connection conn = null;
			try
			{
				if (txt_WorkDay.getText() == null || StringUtil.isBlank(txt_WorkDay.getText()))
				{
					this.setAlert("MSG-W0038");
					return;
				}
				conn = ConnectionManager.getConnection(DATASOURCE_NAME);
				WareNaviSystemAlterKey alterKey = new WareNaviSystemAlterKey();
				alterKey.setSystemNo(WareNaviSystem.SYSTEM_NO); // set record number for update
				alterKey.updateWorkDate(WmsFormatter.toParamDate(txt_WorkDay.getText(),this.getHttpRequest().getLocale() )); // update with new work date
				WareNaviSystemHandler systemhandler = new WareNaviSystemHandler(conn);
				systemhandler.modify(alterKey); // update
				conn.commit();
			}
			catch (Exception ex)
			{
				setAlert("MSG-9100" + MSG_DELIM + MessageResources.getText(ExceptionHandler.getDisplayMessage(ex, this.getClass())));
			}
			finally
			{
				try
				{
					//#CM53774
					//<en>Close connection.</en>
					if (conn != null)
						conn.close();
				}
				catch (SQLException se)
				{
					setAlert("MSG-9100" + MSG_DELIM + MessageResources.getText(ExceptionHandler.getDisplayMessage(se, this.getClass())));
				}
			}
		}
}
//#CM53775
//end of class
