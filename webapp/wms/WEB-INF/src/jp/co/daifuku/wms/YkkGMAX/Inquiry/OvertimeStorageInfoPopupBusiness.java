// $Id: OvertimeStorageInfoPopupBusiness.java,v 1.1 2007/12/13 06:25:09 administrator Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.YkkGMAX.Inquiry;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.OvertimeStorageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.OvertimeStorageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListProxy.OvertimeStorageInfoListProxy;
import jp.co.daifuku.wms.YkkGMAX.PageController.OvertimeStorageInfoPager;
import jp.co.daifuku.wms.YkkGMAX.PageController.PageController;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;
import jp.co.daifuku.wms.base.common.WMSConstants;

/**
 * Ths screen business logic has to be implemented in this class.<BR>
 * This class is generated by ScreenGenerator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1 $, $Date: 2007/12/13 06:25:09 $
 * @author  $Author: administrator $
 */
public class OvertimeStorageInfoPopupBusiness extends OvertimeStorageInfoPopup implements WMSConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	private final String DIALOG_FLAG = "DIALOG_FLAG";
	private final String OVERTIME_STORAGE_INFO_HEAD = "OVERTIME_STORAGE_INFO_HEAD";
	private OvertimeStorageInfoListProxy listProxy = new OvertimeStorageInfoListProxy(lst_OvertimeStorInfoPopupHead,lst_OvertimeStorageInfoPopup);
	private PageController pageController = new PageController(new OvertimeStorageInfoPager(this,pgr_Up),new OvertimeStorageInfoPager(this,pgr_Low),message);
	/**
	 * Initializes the screen.
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		OvertimeStorageInfoHead head  = (OvertimeStorageInfoHead)session.getAttribute(OVERTIME_STORAGE_INFO_HEAD);
		
		listProxy.setHeadValue(head);
		
		try
		{
			pageController.init();
			pageController.setCountPerPage(30);
			pageController.turnToFirstPage();
			if(pgr_Up.getMax() == 0)
			{
				btn_Print.setEnabled(false);
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
	
	}

	/**
	 * Refered before calling each control event.
	 * @param e ActionEvent
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
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OvertimeStorInfoPopupHead_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OvertimeStorInfoPopupHead_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OvertimeStorInfoPopupHead_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OvertimeStorInfoPopupHead_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OvertimeStorInfoPopupHead_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OvertimeStorInfoPopupHead_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OvertimeStorInfoPopupHead_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *                ActionEvent
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
	 *                ActionEvent
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
	 *                ActionEvent
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
	 * @param e ActionEvent 
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
	 * @param e ActionEvent 
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
	 * @param e ActionEvent 
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
	 * @param e ActionEvent 
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
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		OvertimeStorageInfoHead head = (OvertimeStorageInfoHead)session.getAttribute(OVERTIME_STORAGE_INFO_HEAD);
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection();
			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			int count = centre.getOvertimeStorageInfoCount(head);
			
			setConfirm("YKK-LBL-PrintConfirm" + MessageResource.DELIM + String.valueOf(count));
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

			OvertimeStorageInfoHead head = (OvertimeStorageInfoHead) session
					.getAttribute(OVERTIME_STORAGE_INFO_HEAD);

			String benchmarkObject = "";
			String orderBy = "";
			if (head.getBenchmarkObject().equals("1"))
			{
				benchmarkObject = "入库日时";
			}
			else if (head.getBenchmarkObject().equals("2"))
			{
				benchmarkObject = "受信日时";
			}
			else
			{
				benchmarkObject = "最新更新日时";
			}
			if (head.getOrderBy().equals("1"))
			{
				orderBy = "长期滞留时间";
			}
			else
			{
				orderBy = "物料编号";
			}
			
			String sqlString = "INSERT INTO FNPRINTHEAD (listkey,proc_flag,printer_name,listtype,order_flag,range1,range2,range3,range4)VALUES("
					+ StringUtils.surroundWithSingleQuotes(listKey)
					+ ","
					+ StringUtils.surroundWithSingleQuotes("0")
					+ ","
					+ StringUtils.surroundWithSingleQuotes(printerName)
					+ ","
					+ StringUtils.surroundWithSingleQuotes("2")
					+ ","
					+ StringUtils.surroundWithSingleQuotes(head.getOrderBy())
					+ ","
					+ StringUtils.surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.parseDBToPage(head.getDepositoryType()))
					+ ","
					+ StringUtils.surroundWithSingleQuotes(StringUtils.formatDateFromDBToPage(head.getBenchmarkDate()))
					+ ","
					+ StringUtils.surroundWithSingleQuotes(benchmarkObject)
					+ ","
					+ StringUtils.surroundWithSingleQuotes(orderBy)
					+ ")";
			DBHandler handler = new DBHandler(conn);
			handler.executeUpdate(sqlString, true);

			List entityList = centre.getOvertimeStorageInfoList(head);

			for (int i = 0; i < entityList.size(); i++)
			{
				OvertimeStorageInfoEntity entity = (OvertimeStorageInfoEntity) entityList
						.get(i);

				sqlString = "INSERT INTO FNPRINTBODY (listKey,range1,range2,range3,range4,range5,range6,range7,range8)VALUES("
						+ StringUtils.surroundWithSingleQuotes(listKey)
						+ ","
						+ StringUtils.surroundWithSingleQuotes(StringUtils
								.formatDateFromDBToPage(entity
										.getDateTime()))
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
								.getColor())
						+ ","
						+ StringUtils.surroundWithSingleQuotes(entity
								.getTicketNo())
						+ ","
						+ StringUtils.surroundWithSingleQuotes(entity
								.getBucketNo())
												+ ","
						+ StringUtils.surroundWithSingleQuotes(StringUtils
								.formatLocationNoFromDBToPage(entity
										.getLocationNo()))
						+ ","
						+ StringUtils.surroundWithSingleQuotes(DecimalFormat
								.getIntegerInstance().format(
										entity.getInstockCount())) 
						+ ")";
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
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_Up_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_Up_Click(ActionEvent e) throws Exception
	{
		this.closeWindow();
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OvertimeStorageInfoPopup_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OvertimeStorageInfoPopup_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OvertimeStorageInfoPopup_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OvertimeStorageInfoPopup_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OvertimeStorageInfoPopup_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OvertimeStorageInfoPopup_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OvertimeStorageInfoPopup_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_Low_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_Low_Click(ActionEvent e) throws Exception
	{
		this.closeWindow();
	}

}
//end of class