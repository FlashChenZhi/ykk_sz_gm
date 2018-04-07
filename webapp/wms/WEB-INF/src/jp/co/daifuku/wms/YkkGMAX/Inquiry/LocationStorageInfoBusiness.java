// $Id: LocationStorageInfoBusiness.java,v 1.1 2007/12/13 06:25:09 administrator Exp $

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
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.LocationStorageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.LocationStorageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;
import jp.co.daifuku.wms.YkkGMAX.fileexporter.CSVGenerator;
import jp.co.daifuku.wms.YkkGMAX.fileexporter.LocationStorageInfoCSV;
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
 * @version $Revision: 1.1 $, $Date: 2007/12/13 06:25:09 $
 * @author $Author: administrator $
 */
public class LocationStorageInfoBusiness extends LocationStorageInfo implements
		WMSConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	private final String DIALOG_FLAG = "DIALOG_FLAG";

	private final String LOCATION_STORAGE_INFO_HEAD = "LOCATION_STORAGE_INFO_HEAD";

	/**
	 * Initializes the screen.
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		txt_LocationNoTo.setReadOnly(true);
		session.setAttribute(LOCATION_STORAGE_INFO_HEAD,
				new LocationStorageInfoHead());
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
	public void lbl_LocationStatus_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void chk_UsedLocation_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void chk_UsedLocation_Change(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void chk_BlankLocation_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void chk_BlankLocation_Change(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void chk_WorkLocation_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void chk_WorkLocation_Change(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void chk_ErroLocation_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void chk_ErroLocation_Change(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void chk_ForbidLocation_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void chk_ForbidLocation_Change(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void chk_CannotCallLocation_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void chk_CannotCallLocation_Change(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_LocationNo_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_LocationNoFrom_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_LocationNoFrom_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_LocationNoFrom_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_to_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_LocationNoTo_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_LocationNoTo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_LocationNoTo_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void chk_RangeSet_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void chk_RangeSet_Change(ActionEvent e) throws Exception
	{
		txt_LocationNoTo.setReadOnly(!chk_RangeSet.getChecked());
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
		setHeadSession();
		redirect("/YkkGMAX/Inquiry/LocationStorageInfoPopup.do", null,
				"/progress.do");
	}

	private void setHeadSession()
	{
		LocationStorageInfoHead head = new LocationStorageInfoHead();
		ArrayList locationStatus = new ArrayList();
		if (chk_UsedLocation.getChecked())
		{
			locationStatus.add("实货位");
		}
		if (chk_BlankLocation.getChecked())
		{
			locationStatus.add("空货位");
		}
		if (chk_WorkLocation.getChecked())
		{
			locationStatus.add("作业货位");
		}
		if (chk_ErroLocation.getChecked())
		{
			locationStatus.add("异常货位");
		}
		if (chk_ForbidLocation.getChecked())
		{
			locationStatus.add("禁止货位");
		}
		if (chk_CannotCallLocation.getChecked())
		{
			locationStatus.add("访问不可货位");
		}
		head.setLocationStatus(locationStatus);
		ArrayList weightReportFlags = new ArrayList();
		if(chk_HaveNotReported.getChecked())
		{
			weightReportFlags.add("未报告");
		}
		if(chk_Reporting.getChecked())
		{
			weightReportFlags.add("报告中");
		}
		if(chk_HaveReported.getChecked())
		{
			weightReportFlags.add("报告完成");
		}
		head.setWeightReportFlag(weightReportFlags);
		head.setDepo(pul_Depository.getSelectedValue());
		head.setLocationNoFrom(StringUtils
				.formatLocationNoFromPageToDB(txt_LocationNoFrom.getText()));
		if (chk_RangeSet.getChecked())
		{
			head.setRangeSet(true);
			head.setLocationNoTo(StringUtils
					.formatLocationNoFromPageToDB(txt_LocationNoTo.getText()));
		}
		else
		{
			head.setRangeSet(false);
		}
		session.setAttribute(LOCATION_STORAGE_INFO_HEAD, head);
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
		setHeadSession();
		LocationStorageInfoHead head = (LocationStorageInfoHead) session
				.getAttribute(LOCATION_STORAGE_INFO_HEAD);
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection();
			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			int count = centre.getLocationStorageInfoCount(head);

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

			LocationStorageInfoHead head = (LocationStorageInfoHead) session
					.getAttribute(LOCATION_STORAGE_INFO_HEAD);

			String locationStatus = "";
			for (int i = 0; i < head.getLocationStatus().size(); i++)
			{
				locationStatus += (String) head.getLocationStatus().get(i)
						+ "、";
			}
			if (locationStatus.length() > 0)
			{
				locationStatus = locationStatus.substring(0, locationStatus
						.length() - 1);
			}
			String weightReportFlags = "";
			for (int i = 0; i < head.getWeightReportFlag().size(); i++)
			{
				weightReportFlags += (String) head.getWeightReportFlag().get(i)
						+ "、";
			}
			if (weightReportFlags.length() > 0)
			{
				weightReportFlags = weightReportFlags.substring(0, weightReportFlags
						.length() - 1);
			}
			String locationNoRange = "";
			if (head.isRangeSet())
			{
				locationNoRange = StringUtils.formatLocationNoFromDBToPage(head
						.getLocationNoFrom())
						+ "～"
						+ StringUtils.formatLocationNoFromDBToPage(head
								.getLocationNoTo());
			}
			else
			{
				locationNoRange = StringUtils.formatLocationNoFromDBToPage(head
						.getLocationNoFrom());
			}

			String sqlString = "INSERT INTO FNPRINTHEAD (listkey,proc_flag,printer_name,listtype,order_flag,range1,range2,range3,range4)VALUES("
					+ StringUtils.surroundWithSingleQuotes(listKey)
					+ ","
					+ StringUtils.surroundWithSingleQuotes("0")
					+ ","
					+ StringUtils.surroundWithSingleQuotes(printerName)
					+ ","
					+ StringUtils.surroundWithSingleQuotes("3")
					+ ","
					+ StringUtils.surroundWithSingleQuotes("0")
					+ ","
					+ StringUtils.surroundWithSingleQuotes(head.getDepo())
					+ ","
					+ StringUtils.surroundWithSingleQuotes(StringUtils
							.formatDateAndTimeFromDBToPage(locationStatus))
					+ ","
					+ StringUtils.surroundWithSingleQuotes(locationNoRange)
					+ ","
					+ StringUtils.surroundWithSingleQuotes(weightReportFlags)
					+ ")";
			DBHandler handler = new DBHandler(conn);
			handler.executeUpdate(sqlString, true);

			List entityList = centre.getLocationStorageInfoList(head);

			for (int i = 0; i < entityList.size(); i++)
			{
				LocationStorageInfoEntity entity = (LocationStorageInfoEntity) entityList
						.get(i);

				sqlString = "INSERT INTO FNPRINTBODY (listKey,range1,range2,range3,range4,range5,range6,range7,range8,range9)VALUES("
						+ StringUtils.surroundWithSingleQuotes(listKey)
						+ ","
						+ StringUtils.surroundWithSingleQuotes(StringUtils
								.formatLocationNoFromDBToPage(entity
										.getLocationNo())
								+ ":" + entity.getLocationStatus())
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
						+ StringUtils.surroundWithSingleQuotes(DecimalFormat
								.getIntegerInstance().format(
										entity.getInstockCount()))
						+ ","
						+ StringUtils.surroundWithSingleQuotes(StringUtils
								.formatDateAndTimeFromDBToPage(entity
										.getStockinDateTime())) 
						+ ","
						+ StringUtils.surroundWithSingleQuotes(entity.getWeightReportFlag())
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
		setHeadSession();
		String root = getServletContext().getRealPath("/csv");
		CSVGenerator generator = new CSVGenerator(new LocationStorageInfoCSV(
				this), root);
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

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		ClearPage();
	}

	private void ClearPage()
	{
		chk_BlankLocation.setChecked(false);
		chk_CannotCallLocation.setChecked(false);
		chk_ErroLocation.setChecked(false);
		chk_ForbidLocation.setChecked(false);
		chk_UsedLocation.setChecked(false);
		chk_WorkLocation.setChecked(false);
		chk_RangeSet.setChecked(false);
		txt_LocationNoFrom.setText("");
		txt_LocationNoTo.setText("");
		txt_LocationNoTo.setReadOnly(true);
		pul_Depository.setSelectedIndex(0);

	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_Depository_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pul_Depository_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void pul_Depository_Change(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_Star_Server(ActionEvent e) throws Exception
	{
	}
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WeightReportFlag_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_HaveNotReported_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_HaveNotReported_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Reporting_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Reporting_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_HaveReported_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_HaveReported_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void slb_Download_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void slb_Download_Click(ActionEvent e) throws Exception
	{
		String path = viewState.getString("file");
		
		ForwardParameters param = new ForwardParameters();
		param.addParameter("file", path);
		
		redirect("/jsp/SheetDownLoadDummy.jsp",param);
	}


}
// end of class
