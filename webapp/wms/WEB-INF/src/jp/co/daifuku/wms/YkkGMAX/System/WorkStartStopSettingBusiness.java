// $Id: WorkStartStopSettingBusiness.java,v 1.1 2007/12/13 06:25:10 administrator Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.System;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.YkkGMAX.Entities.WorkStartStopSettingEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.WorkStartStopSettingListProxy;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;
import jp.co.daifuku.wms.YkkGMAX.resident.ProcessorInvoker;
import jp.co.daifuku.wms.YkkGMAX.resident.WorkStartRequestProcessor;
import jp.co.daifuku.wms.YkkGMAX.resident.WorkStopRequestProcessor;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;

/**
 * Ths screen business logic has to be implemented in this class.<BR>
 * This class is generated by ScreenGenerator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/02/13</TD>
 * <TD>N.Sawa(DFK)</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.1 $, $Date: 2007/12/13 06:25:10 $
 * @author $Author: administrator $
 */
public class WorkStartStopSettingBusiness extends WorkStartStopSetting
		implements WMSConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	private final String DIALOG_FLAG1 = "DIALOG_FLAG1";

	private final String DIALOG_FLAG2 = "DIALOG_FLAG2";

	private final String DIALOG_FLAG3 = "DIALOG_FLAG3";

	private WorkStartStopSettingListProxy listProxy = new WorkStartStopSettingListProxy(
			lst_WorkStartStopSetting);

	/**
	 * Initializes the screen.
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		lst_WorkStartStopSetting.clearRow();
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			List workStartStopSettingList = centre
					.getWorkStartStopSettingList();
			for (int i = 0; i < workStartStopSettingList.size(); i++)
			{
				WorkStartStopSettingEntity entity = (WorkStartStopSettingEntity) workStartStopSettingList
						.get(i);

				lst_WorkStartStopSetting.addRow();
				lst_WorkStartStopSetting.setCurrentRow(lst_WorkStartStopSetting
						.getMaxRows() - 1);
				listProxy.setRowValueByEntity(entity);
			}

		}
		catch (YKKDBException dbEx)
		{
			String msgString = MessageResources.getText(dbEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7200001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
		catch (YKKSQLException sqlEx)
		{
			String msgString = MessageResources.getText(sqlEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7300001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
		}
		finally
		{
			if (conn != null)
			{
				try
				{
					conn.close();
				}
				catch (SQLException ex)
				{
					DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
					message.setMsgResourceKey("7200002");
				}
			}
		}
	}

	/**
	 * Refered before calling each control event.
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			// #CM37828
			// fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			// #CM37829
			// save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			// #CM37830
			// set screen name
			// lbl_SettingName.setResourceKey(title);
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	// Event handler methods -----------------------------------------
	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState()
				.getString(M_MENUID_KEY)));
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_Mode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_Normal_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_Normal_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_DataHolding_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_DataHolding_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkStartStopSetting_EnterKey(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkStartStopSetting_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkStartStopSetting_InputComplete(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkStartStopSetting_ColumClick(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkStartStopSetting_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkStartStopSetting_Change(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkStartStopSetting_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Online_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Online_Click(ActionEvent e) throws Exception
	{
		InitDlg();
		checkHasChecked();
		setConfirm("YKK-LBL-SetConfirm");
		getViewState().setBoolean(DIALOG_FLAG1, true);
	}

	private void checkHasChecked()
	{
		boolean hasChecked = false;
		for (int i = 1; i < this.lst_WorkStartStopSetting.getMaxRows(); i++)
		{
			lst_WorkStartStopSetting.setCurrentRow(i);
			if (listProxy.getCheck())
			{
				hasChecked = true;
			}
		}
		if (!hasChecked)
		{
			message.setMsgResourceKey("7000013");
			return;
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Offline_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Offline_Click(ActionEvent e) throws Exception
	{
		InitDlg();
		checkHasChecked();
		if (rdo_Normal.getChecked())
		{
			for (int i = 1; i < this.lst_WorkStartStopSetting.getMaxRows(); i++)
			{
				lst_WorkStartStopSetting.setCurrentRow(i);
				if (listProxy.getCheck() && listProxy.getWorkCount() > 0)
				{
					message.setMsgResourceKey("7000014");
					return;
				}
			}
		}
		setConfirm("YKK-LBL-SetConfirm");
		getViewState().setBoolean(DIALOG_FLAG2, true);
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_ForceOffline_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_ForceOffline_Click(ActionEvent e) throws Exception
	{
		InitDlg();
		checkHasChecked();
		setConfirm("YKK-LBL-SetConfirm");
		getViewState().setBoolean(DIALOG_FLAG3, true);

	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_WorkView_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_WorkView_Click(ActionEvent e) throws Exception
	{
		redirect("/YkkGMAX/Popup/WorkView.do", null, "/progress.do");
	}

	public void page_ConfirmBack(ActionEvent e) throws Exception
	{

		if (this.getViewState().getBoolean(DIALOG_FLAG1))
		{
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString())
					.booleanValue();
			if (!isExecute)
			{
				return;
			}
			message.setMsgResourceKey("7000034");
			ProcessorInvoker pi = new ProcessorInvoker(message);
			pi.addProcessor(new WorkStartRequestProcessor());
			pi.run();
		}
		else if (this.getViewState().getBoolean(DIALOG_FLAG2))
		{
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString())
					.booleanValue();
			if (!isExecute)
			{
				return;
			}
			message.setMsgResourceKey("7000034");
			ProcessorInvoker pi = new ProcessorInvoker(message);
			pi.addProcessor(new WorkStopRequestProcessor(rdo_DataHolding
					.getChecked()));
			pi.run();
		}
		else if (this.getViewState().getBoolean(DIALOG_FLAG3))
		{
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString())
					.booleanValue();
			if (!isExecute)
			{
				return;
			}
			message.setMsgResourceKey("7000034");
			Connection conn = null;
			try
			{
				conn = ConnectionManager.getConnection();

				ASRSInfoCentre centre = new ASRSInfoCentre(conn);

				for (int i = 1; i < this.lst_WorkStartStopSetting.getMaxRows(); i++)
				{
					lst_WorkStartStopSetting.setCurrentRow(i);
					if (listProxy.getCheck())
					{
						centre.forceStopController(listProxy.getControllerNo());
					}
				}
			    message.setMsgResourceKey("7400002");
				conn.commit();
			}
			catch (YKKDBException dbEx)
			{
				String msgString = MessageResources.getText(dbEx
						.getResourceKey());
				DebugPrinter.print(DebugLevel.ERROR, msgString);
				message.setMsgResourceKey("7200001");
				List paramList = new ArrayList();
				paramList.add(msgString);
				message.setMsgParameter(paramList);
			}
			catch (YKKSQLException sqlEx)
			{
				String msgString = MessageResources.getText(sqlEx
						.getResourceKey());
				DebugPrinter.print(DebugLevel.ERROR, msgString);
				message.setMsgResourceKey("7300001");
				List paramList = new ArrayList();
				paramList.add(msgString);
				message.setMsgParameter(paramList);
			}
			finally
			{
				if (conn != null)
				{
					try
					{
						conn.close();
					}
					catch (SQLException ex)
					{
						DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
						message.setMsgResourceKey("7200002");
					}
				}
			}
		}
		page_Load(null);
	}

	private void InitDlg()
	{
		this.getViewState().setBoolean(DIALOG_FLAG1,false);
		this.getViewState().setBoolean(DIALOG_FLAG2,false);
		this.getViewState().setBoolean(DIALOG_FLAG3,false);
	}

}
// end of class
