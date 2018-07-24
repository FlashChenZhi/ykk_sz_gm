// $Id: WorkMaintenancePopupBusiness.java,v 1.1 2007/12/13 06:25:10 administrator Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Maintenance;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.WorkMaintenancePopupEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.WorkMaintenancePopupHead;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.WorkMaintenancePopupListProxy;
import jp.co.daifuku.wms.YkkGMAX.PageController.PageController;
import jp.co.daifuku.wms.YkkGMAX.PageController.WorkMaintenancePopupPager;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;
import jp.co.daifuku.wms.YkkGMAX.fileexporter.CSVGenerator;
import jp.co.daifuku.wms.YkkGMAX.fileexporter.WorkMaintenancePopupCSV;
import jp.co.daifuku.wms.base.common.WMSConstants;

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
public class WorkMaintenancePopupBusiness extends WorkMaintenancePopup
		implements WMSConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	private final String DIALOG_FLAG = "DIALOG_FLAG";

	private final String STATION_AND_STATION_NAME = "STATION_AND_STATION_NAME";

	private final String TRANSFER_TYPE = "TRANSFER_TYPE";

	private final String STATION = "STATION";

	private final String DIVISION = "DIVISION";

	private final String WORK_MAINTENANCE_ENTITY = "WORK_MAINTENANCE_ENTITY";

	private WorkMaintenancePopupListProxy listProxy = new WorkMaintenancePopupListProxy(
			lst_WorkMaintenancePopupHead, lst_WorkMaintenancePopup);

	private PageController pageController = new PageController(
			new WorkMaintenancePopupPager(this, pgr_Up),
			new WorkMaintenancePopupPager(this, pgr_Low), message);

	/**
	 * Initializes the screen.
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		WorkMaintenancePopupHead head = new WorkMaintenancePopupHead();

		head.setTransferType(session.getAttribute(TRANSFER_TYPE).toString());
		head.setStation(session.getAttribute(STATION_AND_STATION_NAME)
				.toString());
		head.setDivision(session.getAttribute(DIVISION).toString());

		listProxy.setHeadValue(head);
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
	 * Refered before calling each control event.
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
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
	public void lst_WorkMaintenancePopupHead_EnterKey(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkMaintenancePopupHead_TabKey(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkMaintenancePopupHead_InputComplete(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkMaintenancePopupHead_ColumClick(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkMaintenancePopupHead_Server(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkMaintenancePopupHead_Change(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkMaintenancePopupHead_Click(ActionEvent e)
			throws Exception
	{
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
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection();
			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			int count = centre.getWorkMaintenancePopupCount(session
					.getAttribute(TRANSFER_TYPE).toString(), session
					.getAttribute(STATION).toString(), session.getAttribute(
					DIVISION).toString());

			setConfirm("YKK-LBL-PrintConfirm" + MessageResource.DELIM
					+ String.valueOf(count));
			getViewState().setBoolean(DIALOG_FLAG, true);
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
				catch (SQLException sqle)
				{
					DebugPrinter.print(DebugLevel.ERROR, sqle.getMessage());
					message.setMsgResourceKey("7200002");
				}
			}
		}
	}

	public void page_ConfirmBack(ActionEvent e) throws Exception
	{

		if (!this.getViewState().getBoolean(DIALOG_FLAG))
		{
			return;
		}
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

			String printerName = centre.getPrinterName(getHttpRequest()
					.getRemoteAddr());
			String listKey = centre.generateLabelKey();

			String sqlString = "INSERT INTO FNPRINTHEAD (listkey,proc_flag,printer_name,listtype,order_flag,range1,range2,range3)VALUES("
					+ StringUtils.surroundWithSingleQuotes(listKey)
					+ ","
					+ StringUtils.surroundWithSingleQuotes("0")
					+ ","
					+ StringUtils.surroundWithSingleQuotes(printerName)
					+ ","
					+ StringUtils.surroundWithSingleQuotes("8")
					+ ","
					+ StringUtils.surroundWithSingleQuotes("0") + ",";
			if (session.getAttribute(TRANSFER_TYPE).toString().equals("1"))
			{
				sqlString += StringUtils.surroundWithSingleQuotes("入出库");
			}
			else if (session.getAttribute(TRANSFER_TYPE).toString().equals("2"))
			{
				sqlString += StringUtils.surroundWithSingleQuotes("入库");
			}
			else
			{
				sqlString += StringUtils.surroundWithSingleQuotes("出库(直行)");
			}
			sqlString += ","
					+ StringUtils.surroundWithSingleQuotes(session
							.getAttribute(STATION_AND_STATION_NAME).toString())
					+ ",";
			if (session.getAttribute(DIVISION).toString().equals("1"))
			{
				sqlString += StringUtils.surroundWithSingleQuotes("最终站台");
			}
			else
			{
				sqlString += StringUtils.surroundWithSingleQuotes("当前站台");
			}
			sqlString += ")";
			DBHandler handler = new DBHandler(conn);
			handler.executeUpdate(sqlString, true);

			List entityList = centre.getWorkMaintenancePopupList(session
					.getAttribute(TRANSFER_TYPE).toString(), session
					.getAttribute(STATION).toString(), session.getAttribute(
					DIVISION).toString());

			for (int i = 0; i < entityList.size(); i++)
			{
				WorkMaintenancePopupEntity entity = (WorkMaintenancePopupEntity) entityList
						.get(i);

				sqlString = "INSERT INTO FNPRINTBODY (listKey,range1,range2,range3,range4,range5,range6,range7,range8,range9,range10,range11)VALUES("
						+ StringUtils.surroundWithSingleQuotes(listKey)
						+ ","
						+ StringUtils.surroundWithSingleQuotes(entity
								.getTransferType())
						+ ","
						+ StringUtils.surroundWithSingleQuotes(entity
								.getStatus())
						+ ","
						+ StringUtils.surroundWithSingleQuotes(entity
								.getMckey())
						+ ","
						+ StringUtils.surroundWithSingleQuotes(entity
								.getStationNo()
								+ ":" + entity.getStationName())
						+ ","
						+ StringUtils.surroundWithSingleQuotes(entity
								.getMotoStationNo()
								+ StringUtils.ToMark
								+ entity.getSakiStationNo())
						+ ","
						+ StringUtils.surroundWithSingleQuotes(StringUtils
								.formatLocationNoFromDBToPage(entity
										.getLocationNo()))
						+ ","
						+ StringUtils.surroundWithSingleQuotes(entity
								.getBucketNo())
						+ ","
						+ StringUtils.surroundWithSingleQuotes(entity
								.getItemCode())
						+ ","
						+ StringUtils.surroundWithSingleQuotes(entity
								.getItemName1()
								+ StringUtils.nextRow
								+ entity.getItemName2()
								+ StringUtils.nextRow + entity.getItemName3())
						+ ","
						+ StringUtils.surroundWithSingleQuotes(entity
								.getColorCode())
						+ ","
						+ StringUtils.surroundWithSingleQuotes(DecimalFormat
								.getIntegerInstance().format(
										entity.getTransferCount())) + ")";
				handler = new DBHandler(conn);
				handler.executeUpdate(sqlString, true);

			}
			message.setMsgResourceKey("7000017");
			conn.commit();
		}

		catch (YKKDBException dbEx)
		{
			String msgString = MessageResources.getText(dbEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7200001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
			try
			{
				if (conn != null)
				{
					conn.rollback();
				}
			}
			catch (SQLException ex)
			{
				DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
				message.setMsgResourceKey("7200002");
			}
		}
		catch (YKKSQLException sqlEx)
		{
			String msgString = MessageResources.getText(sqlEx.getResourceKey());
			DebugPrinter.print(DebugLevel.ERROR, msgString);
			message.setMsgResourceKey("7300001");
			List paramList = new ArrayList();
			paramList.add(msgString);
			message.setMsgParameter(paramList);
			try
			{
				if (conn != null)
				{
					conn.rollback();
				}
			}
			catch (SQLException ex)
			{
				DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
				message.setMsgResourceKey("7200002");
			}
		}
		finally
		{
			if (conn != null)
			{
				try
				{
					conn.close();
				}
				catch (SQLException sqle)
				{
					DebugPrinter.print(DebugLevel.ERROR, sqle.getMessage());
					message.setMsgResourceKey("7200002");
				}
			}
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Close_Up_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Close_Up_Click(ActionEvent e) throws Exception
	{
		this.closeWindow();
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkMaintenancePopup_EnterKey(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkMaintenancePopup_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkMaintenancePopup_InputComplete(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkMaintenancePopup_ColumClick(ActionEvent e)
			throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkMaintenancePopup_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkMaintenancePopup_Change(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_WorkMaintenancePopup_Click(ActionEvent e) throws Exception
	{
		int row = Integer.parseInt(e.getEventArgs().get(0).toString());
		int col = Integer.parseInt(e.getEventArgs().get(1).toString());

		if (col == listProxy.getNO_COLUMN())
		{
			lst_WorkMaintenancePopup.setCurrentRow(row);

			session.setAttribute(WORK_MAINTENANCE_ENTITY, listProxy
					.getWorkMaintenancePopupEntity());

			ForwardParameters param = new ForwardParameters();
			parentRedirect(param);
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Reshow_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Reshow_Click(ActionEvent e) throws Exception
	{
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
	public void btn_Close_Low_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Close_Low_Click(ActionEvent e) throws Exception
	{
		this.closeWindow();
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void slb_Download_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void slb_Download_Click(ActionEvent e) throws Exception
	{
		String path = viewState.getString("file");

		ForwardParameters param = new ForwardParameters();
		param.addParameter("file", path);

		redirect("/jsp/SheetDownLoadDummy.jsp", param);
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_CSV_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_CSV_Click(ActionEvent e) throws Exception
	{
		String root = getServletContext().getRealPath("/csv");
		CSVGenerator generator = new CSVGenerator(new WorkMaintenancePopupCSV(this),
				root);
		try
		{
			String path = generator.generateFile(message);
			if (path.equals(""))
			{
				return;
			}
			viewState.setString("file", path);
			addOnloadScript("slb_Download.click();");
		}
		catch (Exception ex)
		{
			DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
			message.setMsgResourceKey("7500001");
			List paramList = new ArrayList();
			paramList.add(ex.getMessage());
			message.setMsgParameter(paramList);
		}
	}

}
// end of class
