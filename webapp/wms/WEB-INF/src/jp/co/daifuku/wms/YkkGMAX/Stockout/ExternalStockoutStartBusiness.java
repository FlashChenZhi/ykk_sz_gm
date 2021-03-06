// $Id: skeltenBusiness.java,v 1.2 2007/03/07 07:45:23 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Stockout;

import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListHandler.SystemIdSortableHandler;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.ExternalStockoutStartListProxy;
import jp.co.daifuku.wms.YkkGMAX.PageController.ExternalStockoutStartPager;
import jp.co.daifuku.wms.YkkGMAX.PageController.PageController;
import jp.co.daifuku.wms.YkkGMAX.PulldownManager.PulldownManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;
import jp.co.daifuku.wms.YkkGMAX.resident.AfterStockoutRequestProcessor;
import jp.co.daifuku.wms.YkkGMAX.resident.ExternalStockoutReleaseRequestProcessor;
import jp.co.daifuku.wms.YkkGMAX.resident.ExternalStockoutStartRequestProcessor;
import jp.co.daifuku.wms.YkkGMAX.resident.ProcessorInvoker;
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
 * @version $Revision: 1.2 $, $Date: 2007/03/07 07:45:23 $
 * @author $Author: suresh $
 */
public class ExternalStockoutStartBusiness extends ExternalStockoutStart
		implements WMSConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	private final String DIALOG_FLAG1 = "DIALOG_FLAG1";

	private final String DIALOG_FLAG = "DIALOG_FLAG";

	private final String SECTION = "SECTION";

	private final String ORDER_MODE = "ORDER_MODE";

	private final String RETRIEVAL_NO = "RETRIEVAL_NO";

	private final String ORDER_NO = "ORDER_NO";

	private final String EXTERNAL_STOCKOUT_START_LIST = "EXTERNAL_STOCKOUT_START_LIST";

	private final PageController pageController = new PageController(
			new ExternalStockoutStartPager(this, pgr_Up),
			new ExternalStockoutStartPager(this, pgr_Low), message);

	private final ExternalStockoutStartListProxy listProxy = new ExternalStockoutStartListProxy(
			lst_ExternalStockoutStart, this);

	/**
	 * Initializes the screen.
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		try
		{

			PulldownManager.FillRetrievalStationPullDown(
					pul_DesignateStockoutStation, true);

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
		InitPage();
	}

	private void InitPage()
	{
		session.setAttribute(EXTERNAL_STOCKOUT_START_LIST, new ArrayList());

		txt_SelectedUnitCount.setInt(0);
		txt_TotalStockOutQty.setLong(0);
	}

	private void InitDlg()
	{
		this.getViewState().setBoolean(DIALOG_FLAG1, false);
		this.getViewState().setBoolean(DIALOG_FLAG, false);
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

	public void page_ConfirmBack(ActionEvent e) throws Exception
	{

		if (this.getViewState().getBoolean(DIALOG_FLAG))
		{
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString())
					.booleanValue();
			if (!isExecute)
			{
				return;
			}
			message.setMsgResourceKey("7000034");
			ArrayList externalStockoutStartList = (ArrayList) session
					.getAttribute(EXTERNAL_STOCKOUT_START_LIST);

			ProcessorInvoker pi = new ProcessorInvoker(message);
			if (externalStockoutStartList != null
					&& externalStockoutStartList.size() > 0)
			{
				pi.addProcessor(new ExternalStockoutStartRequestProcessor(
						externalStockoutStartList, pul_DesignateStockoutStation
								.getSelectedValue(), rdo_Auto.getChecked()));
			}
			if (pi.run())
			{
				pi = new ProcessorInvoker(message);
				pi.addProcessor(new AfterStockoutRequestProcessor());
				pi.run();
			}

			lst_ExternalStockoutStart.clearRow();
			pageController.clear();
			InitPage();
		}
		else if (this.getViewState().getBoolean(DIALOG_FLAG1))
		{
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString())
					.booleanValue();
			if (!isExecute)
			{
				return;
			}
			message.setMsgResourceKey("7000034");
			ArrayList externalStockoutStartList = (ArrayList) session
					.getAttribute(EXTERNAL_STOCKOUT_START_LIST);

			ProcessorInvoker pi = new ProcessorInvoker(message);
			if (externalStockoutStartList != null
					&& externalStockoutStartList.size() > 0)
			{
				pi.addProcessor(new ExternalStockoutReleaseRequestProcessor(
						externalStockoutStartList));
			}
			pi.run();

			lst_ExternalStockoutStart.clearRow();
			pageController.clear();
			InitPage();
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
	public void lbl_Section_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pul_Section_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pul_Section_Change(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_RetrievalNo_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_RetrievalNo_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_RetrievalNo_AutoCompleteItemClick(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_RetrievalNo_AutoComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_RetrievalNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_RetrievalNo_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_RetrievalNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_OrderNo_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_OrderNo_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_OrderNo_AutoCompleteItemClick(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_OrderNo_AutoComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_OrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_OrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_OrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Show_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Show_Click(ActionEvent e) throws Exception
	{
		session.setAttribute(SECTION, pul_Section.getSelectedValue());
		session.setAttribute(ORDER_NO, txt_OrderNo.getText());
		session.setAttribute(RETRIEVAL_NO, txt_RetrievalNo.getText());

		if (rdo_ItemNo.getChecked())
		{
			session.setAttribute(ORDER_MODE, "1");
		}
		else
		{
			session.setAttribute(ORDER_MODE, "2");
		}
		InitPage();

		try
		{
			pageController.init();
			pageController.setCountPerPage(30);
			pageController.turnToFirstPage();

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
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_OrderBy_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_ItemNo_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_ItemNo_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_StockinTime_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_StockinTime_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_SelectAllUnit_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_SelectAllUnit_Click(ActionEvent e) throws Exception
	{
		ArrayList stockoutStartList = (ArrayList) session
				.getAttribute(EXTERNAL_STOCKOUT_START_LIST);
		for (int i = 1; i < lst_ExternalStockoutStart.getMaxRows(); i++)
		{
			lst_ExternalStockoutStart.setCurrentRow(i);

			if (listProxy.getU().equals(StringUtils.Circle)
					&& !listProxy.getCheckBox())
			{
				SystemIdSortableHandler.insert(stockoutStartList, listProxy
						.getStockoutStartEntity());

				listProxy.setCheckBox(true);

				txt_SelectedUnitCount
						.setInt(txt_SelectedUnitCount.getInt() + 1);
				txt_TotalStockOutQty.setLong(txt_TotalStockOutQty.getLong()
						+ listProxy.getStockoutCount());
			}
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_SelectAll_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_SelectAll_Click(ActionEvent e) throws Exception
	{
		ArrayList stockoutStartList = (ArrayList) session
				.getAttribute(EXTERNAL_STOCKOUT_START_LIST);
		for (int i = 1; i < lst_ExternalStockoutStart.getMaxRows(); i++)
		{
			lst_ExternalStockoutStart.setCurrentRow(i);

			if (!listProxy.getCheckBox())
			{
				SystemIdSortableHandler.insert(stockoutStartList, listProxy
						.getStockoutStartEntity());

				listProxy.setCheckBox(true);

				if (listProxy.getU().equals(StringUtils.Circle))
				{
					txt_SelectedUnitCount
							.setInt(txt_SelectedUnitCount.getInt() + 1);
				}
				txt_TotalStockOutQty.setLong(txt_TotalStockOutQty.getLong()
						+ listProxy.getStockoutCount());
			}
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_UnselectAll_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_UnselectAll_Click(ActionEvent e) throws Exception
	{
		ArrayList stockoutStartList = (ArrayList) session
				.getAttribute(EXTERNAL_STOCKOUT_START_LIST);
		for (int i = 1; i < lst_ExternalStockoutStart.getMaxRows(); i++)
		{
			lst_ExternalStockoutStart.setCurrentRow(i);

			if (listProxy.getCheckBox())
			{
				SystemIdSortableHandler.remove(stockoutStartList, listProxy
						.getStockoutStartEntity());

				listProxy.setCheckBox(false);

				if (listProxy.getU().equals(StringUtils.Circle))
				{
					txt_SelectedUnitCount
							.setInt(txt_SelectedUnitCount.getInt() - 1);
				}
				txt_TotalStockOutQty.setLong(txt_TotalStockOutQty.getLong()
						- listProxy.getStockoutCount());
			}
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Set_Up_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Set_Up_Click(ActionEvent e) throws Exception
	{
		InitDlg();
		ArrayList externalStockoutStartList = (ArrayList) session
				.getAttribute(EXTERNAL_STOCKOUT_START_LIST);
		if (externalStockoutStartList.size() <= 0)
		{
			message.setMsgResourceKey("7000025");
			return;
		}
		setConfirm("YKK-LBL-SetConfirm");
		getViewState().setBoolean(DIALOG_FLAG, true);
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pgr_Low_First(ActionEvent e) throws Exception
	{
		try
		{
			pageController.turnToFirstPage();
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
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pgr_Low_Last(ActionEvent e) throws Exception
	{
		try
		{
			pageController.turnToLastPage();
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
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pgr_Low_Next(ActionEvent e) throws Exception
	{
		try
		{
			pageController.turnToNextPage();
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
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pgr_Low_Prev(ActionEvent e) throws Exception
	{
		try
		{
			pageController.turnToPreviousPage();
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
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pgr_Up_First(ActionEvent e)
	{
		try
		{
			pageController.turnToFirstPage();
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
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pgr_Up_Last(ActionEvent e)
	{
		try
		{
			pageController.turnToLastPage();
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
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pgr_Up_Next(ActionEvent e)
	{
		try
		{
			pageController.turnToNextPage();
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
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pgr_Up_Prev(ActionEvent e)
	{
		try
		{
			pageController.turnToPreviousPage();
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
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_ExternalStockoutStart_EnterKey(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_ExternalStockoutStart_TabKey(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_ExternalStockoutStart_InputComplete(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_ExternalStockoutStart_ColumClick(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_ExternalStockoutStart_Server(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_ExternalStockoutStart_Change(ActionEvent e)
			throws Exception
	{
		int row = Integer.parseInt(e.getEventArgs().get(0).toString());
		int col = Integer.parseInt(e.getEventArgs().get(1).toString());
		ArrayList stockoutStartList = (ArrayList) session
				.getAttribute(EXTERNAL_STOCKOUT_START_LIST);

		if (col == listProxy.getCHECK_BOX_COLUMN())
		{
			lst_ExternalStockoutStart.setCurrentRow(row);

			if (listProxy.getCheckBox())
			{
				SystemIdSortableHandler.insert(stockoutStartList, listProxy
						.getStockoutStartEntity());

				if (listProxy.getU().equals(StringUtils.Circle))
				{
					txt_SelectedUnitCount
							.setInt(txt_SelectedUnitCount.getInt() + 1);

				}
				txt_TotalStockOutQty.setLong(txt_TotalStockOutQty.getLong()
						+ listProxy.getStockoutCount());
			}
			else
			{
				SystemIdSortableHandler.remove(stockoutStartList, listProxy
						.getStockoutStartEntity());

				if (listProxy.getU().equals(StringUtils.Circle))
				{
					txt_SelectedUnitCount
							.setInt(txt_SelectedUnitCount.getInt() - 1);

				}
				txt_TotalStockOutQty.setLong(txt_TotalStockOutQty.getLong()
						- listProxy.getStockoutCount());
			}
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_ExternalStockoutStart_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Set_Low_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Set_Low_Click(ActionEvent e) throws Exception
	{
		btn_Set_Up_Click(null);
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_ReleaseStorage_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_ReleaseStorage_Click(ActionEvent e) throws Exception
	{
		InitDlg();
		ArrayList externalStockoutStartList = (ArrayList) session
				.getAttribute(EXTERNAL_STOCKOUT_START_LIST);
		if (externalStockoutStartList.size() <= 0)
		{
			message.setMsgResourceKey("7000026");
			return;
		}
		setConfirm("YKK-LBL-SetConfirm");
		getViewState().setBoolean(DIALOG_FLAG1, true);
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_DesignateStockoutStation_Server(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pul_DesignateStockoutStation_Server(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pul_DesignateStockoutStation_Change(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_Auto_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_Auto_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_Picking_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_Picking_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_SelectedUnitCount_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_EqualsMark_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_SelectedUnitCount_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_SelectedUnitCount_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_SelectedUnitCount_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_TotalStockOutQty_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_EqualsMark_2_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_TotalStockOutQty_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_TotalStockOutQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_TotalStockOutQty_TabKey(ActionEvent e) throws Exception
	{
	}

}
// end of class
