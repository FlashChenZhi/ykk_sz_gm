// $Id: RetrievalPlanDeleteBusiness.java,v 1.2 2007/02/07 04:19:30 suresh Exp $

//#CM718110
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalplandelete;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalPlanDeleteSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM718111
/**
 * Designer : K.Mukai <BR>
 * Maker : T.Kuroda <BR>
 * <BR>
 * Allow this class to delete Picking plan data.  <BR>
 * Set the content input via screen for the parameter and execute the process to display it.  <BR>
 * Receive the result from the schedule. Receive true if the process completed normally. <BR>
 * Or, receive false if the schedule failed to complete due to condition error etc.  <BR>
 * Output the message obtained from the schedule to the screen, as the schedule result.  <BR>
 * <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1. For loading page (<CODE>page_Load</CODE> method)  <BR>
 * <BR>
 * <DIR> If only one Consignor Code exists in the Picking Plan Info, display it in the Initial Display.  <BR>
 * <BR>
 * [Parameter]  <BR>
 * <BR>
 * <DIR> None  <BR>
 * </DIR> <BR>
 * [Returned data]  <BR>
 * <BR>
 * <DIR>Consignor Code <BR>
 * </DIR> </DIR> <BR>
 * 2. Process by clicking "Display" button ( <CODE>btn_View_Click</CODE> method)  <BR>
 * <BR>
 * <DIR> Check the field items. If proper, set them for the parameter.  <BR>
 * Set the result of schedule in the Message area and display it.  <BR>
 * <BR>
 * [Parameter]  *Mandatory Input <BR>
 * <BR>
 * <DIR>Worker Code* <BR>
 * Password * <BR>
 * Consignor Code* <BR>
 * Start Planned Picking Date <BR>
 * End Planned Picking Date <BR>
 * Item/Order * <BR>
 * </DIR> <BR>
 * [Returned data]  <BR>
 * <BR>
 * <DIR> Each message  <BR>
 * Consignor Code <BR>
 * Consignor Name <BR>
 * Planned Picking Date <BR>
 * Item/Order  <BR>
 * Update Date/Time (hidden field item)  <BR>
 * Data count (hidden item (count))  <BR>
 * </DIR> </DIR> 3.Process by clicking on Delete button ( <CODE>btn_Delete_Click</CODE> method)  <BR>
 * <BR>
 * <DIR>Pass all data displayed in the preset area as a parameter.  <BR>
 * Based on it, execute the process for deleting. Obtain a message and display it.  <BR>
 * <BR>
 * [Parameter]  *Mandatory Input <BR>
 * <BR>
 * <DIR>Consignor Code* <BR>
 * Planned Picking Date* <BR>
 * Item/Order * <BR>
 * Update Date/Time  <BR>
 * Data count <BR>
 * <BR>
 * </DIR> <BR>
 * [Returned data]  <BR>
 * <BR>
 * <DIR> Each message  <BR>
 * Planned Picking Date <BR>
 * Item/Order  <BR>
 * Update Date/Time  <BR>
 * </DIR> </DIR> <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor>
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/10/25</TD>
 * <TD>T.Kuroda</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:30 $
 * @author $Author: suresh $
 */
public class RetrievalPlanDeleteBusiness extends RetrievalPlanDelete implements WMSConstants
{
	//#CM718112
	// Class fields --------------------------------------------------
	//#CM718113
	/**
	 * Row No. of option checkbox. 
	 */
	private static final int COL_SELECT = 1;

	//#CM718114
	/**
	 * Row No. of Planned Picking Date 
	 */
	private static final int COL_RETRIEVAL_PLAN_DATE = 2;

	//#CM718115
	/**
	 * Row No. of Item/Order 
	 */
	private static final int COL_ITEM_ORDER = 3;

	//#CM718116
	/**
	 * Key of ViewState (Consignor Code) 
	 */
	private static String KEY_CONSIGNOR_CODE = "CONSIGNOR_CODE";

	//#CM718117
	/**
	 * Key of ViewState (Start Planned Picking Date) 
	 */
	private static String KEY_FROM_RETRIEVAL_PLAN_DATE = "FROM_RETRIEVAL_PLAN_DATE";

	//#CM718118
	/**
	 * Key of ViewState (End Planned Picking Date) 
	 */
	private static String KEY_TO_RETRIEVAL_PLAN_DATE = "TO_RETRIEVAL_PLAN_DATE";

	//#CM718119
	/**
	 * Key of ViewState (Item/Order) 
	 */
	private static String ITEM_ORDER = "ITEM_ORDER";

	//#CM718120
	/**
	 * Key of ViewState (flag for printing the deleted list report) 
	 */
	private static String LIST_FLAG = "LIST_FLAG";	

	//#CM718121
	/**
	 * Hidden row of listcell 
	 */
	private static int COL_HIDDEN = 0;
	
	//#CM718122
	/**
	 * Hidden item key (Picking Plan unique key) 
	 */
	private static int HIDDEN_UKEY = 0;
	
	//#CM718123
	/**
	 * hidden field item Key (Update Date/Time) 
	 */
	private static int HIDDEN_UPDATE = 1;
	
	//#CM718124
	/**
	 * hidden field item Key (Data count) 
	 */
	private static int HIDDEN_DATA_COUNT = 2;
	
	//#CM718125
	/**
	 * hidden field item Key (Item/Order division) 
	 */
	private static int HIDDEN_ITEM_ORDER = 3;

	//#CM718126
	// Class variables -----------------------------------------------

	//#CM718127
	// Class method --------------------------------------------------

	//#CM718128
	// Constructors --------------------------------------------------

	//#CM718129
	// Public methods ------------------------------------------------

	//#CM718130
	/**
	 * Completing reading a screen invokes this.  <BR>
	 * <BR>
	 * Summary: shows the Initial Display.  <BR>
	 * <BR>
	 * <DIR>
	 * 1. Initialize the input area.  <BR>
	 * 2. Start the schedule.  <BR>
	 * 3.Disable Select All button, Cancel All Selected button, Delete button, and Clear List button.  <BR>
	 * 4.Set the cursor on the Work Code.  <BR>
	 * </DIR> <BR>
	 * Field item [Initial Value]  <BR>
	 * <BR>
	 * Worker Code [None]  <BR>
	 * Password [None]  <BR>
	 * Consignor Code [Search Value]  <BR>
	 * Start Planned Picking Date [None]  <BR>
	 * End Planned Picking Date [None]  <BR>
	 * Item/Order [All]  <BR>
	 * Print the Deleted Picking Plan list report. [true]  <BR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM718131
		// Set the initial value for each input field. 
		this.txt_WorkerCode.setText("");
		this.txt_Password.setText("");

		//#CM718132
		// Consignor Code
		this.txt_ConsignorCode.setText(getConsignorCode());
		this.txt_StrtRtrivlPlanDate.setText("");
		this.txt_EdRtrivlPlanDate.setText("");
		this.rdo_ItemOrderAll.setChecked(true);
		this.chk_CommonUse.setChecked(true);

		//#CM718133
		// Disable the buttons in the Preset area. 
		setBtnTrueFalse(false);

		//#CM718134
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM718135
	/**
	 * Invoke this before invoking each control event.  <BR>
	 * <BR>
	 * <DIR> Summary: Displays a dialog.  <BR>
	 * </DIR> <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(Constants.MENUPARAM);
		if (menuparam != null)
		{
			//#CM718136
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM718137
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM718138
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
		//#CM718139
		// Display a dialog. MSG-W0007= Do you delete it? 
		btn_Delete.setBeforeConfirm("MSG-W0007");
		//#CM718140
		// Display a dialog. MSG-W0012=This clears the list input info. Do you confirm it? 
		btn_ListClear.setBeforeConfirm("MSG-W0012");
	}

	//#CM718141
	/**
	 * Returning from a popup window invokes this method. <BR>
	 * Override the page_DlgBack defined for Page.  <BR>
	 * <BR>
	 * Summary: Obtains the Returned data in the Search screen and set it.  <BR>
	 * <BR>
	 * <DIR>
	 * 1. Obtain the value returned from the Search popup screen.  <BR>
	 * 2. Set the screen if the value is not empty.  <BR>
	 * <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM718142
		// Obtain the parameter selected in the listbox. 
		String consignorCode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		Date startRetrievalPlanDate = WmsFormatter.toDate(param.getParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY), this.getHttpRequest().getLocale());
		Date endRetrievalPlanDate = WmsFormatter.toDate(param.getParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY), this.getHttpRequest().getLocale());

		//#CM718143
		// Consignor Code
		if (!StringUtil.isBlank(consignorCode))
		{
			txt_ConsignorCode.setText(consignorCode);
		}
		//#CM718144
		// Start Planned Picking Date
		if (!StringUtil.isBlank(startRetrievalPlanDate))
		{
			this.txt_StrtRtrivlPlanDate.setDate(startRetrievalPlanDate);
		}
		//#CM718145
		// End Planned Picking Date
		if (!StringUtil.isBlank(endRetrievalPlanDate))
		{
			this.txt_EdRtrivlPlanDate.setDate(endRetrievalPlanDate);
		}

		//#CM718146
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM718147
	// Package methods -----------------------------------------------

	//#CM718148
	// Protected methods ---------------------------------------------

	//#CM718149
	// Private methods -----------------------------------------------

	//#CM718150
	/**
	 * Allow this method to obtain the Consignor code from the schedule.  <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule.  <BR>
	 * <DIR>1. If there is only one Consignor Code, return the corresponding Consignor. Otherwise, return null.  <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM718151
			// Obtain connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM718152
			// Declare the parameter. 
			RetrievalSupportParameter param = new RetrievalSupportParameter();
			param.setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);

			//#CM718153
			// Obtain the Consignor code from the schedule. 
			WmsScheduler schedule = new RetrievalPlanDeleteSCH();
			//#CM718154
			// Start the schedule. 
			param = (RetrievalSupportParameter) schedule.initFind(conn, param);

			if (param != null)
			{
				return param.getConsignorCode();
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM718155
				// Close the connection. 
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}

		return null;
	}

	//#CM718156
	/**
	 * Allow this method to set acceptability to compile buttons.  <BR>
	 * <BR>
	 * Summary: Sets to determine whether to compile buttons after receiving false or true.  <BR>
	 * 
	 * @param arg status(false or true)
	 * @return None 
	 */
	private void setBtnTrueFalse(final boolean arg)
	{
		//#CM718157
		// Buttons in the Preset area 
		this.btn_AllCheck.setEnabled(arg);
		this.btn_AllCheckClear.setEnabled(arg);
		this.btn_Delete.setEnabled(arg);
		this.btn_ListClear.setEnabled(arg);
	}

	//#CM718158
	/**
	 * Allow this method to map the search results in the preset area.  <BR>
	 * <BR>
	 * Summary: Enables to display the search result obtained from the schedule, in the preset area.  <BR>
	 * Display the Consignor Code, Consignor Name, Planned Picking Date, and listcell in the Preset area.<BR>
	 * <BR>
	 * Row No. list of listcell  
	 * <DIR>
	 * 1: Checkbox  <BR>
	 * 2: Planned Picking Date <BR>
	 * 3: Item/Order 
	 * </DIR>
	 * 
	 * @param param Parameter []  Info to be displayed in the preset area.
	 * @throws Exception Report all exceptions. 
	 */
	private void addList(final RetrievalSupportParameter[] param) throws Exception
	{
		//#CM718159
		// Display the search result in the preset area.
		//#CM718160
		// Consignor 
		this.txt_RConsignorCode.setText(param[0].getConsignorCode());
		this.txt_RConsignorName.setText(param[0].getConsignorName());

		//#CM718161
		// Set a value in the listcell. 
		for (int i = 0; i < param.length; i++)
		{
			//#CM718162
			// Add a line in a listcell. 
			this.lst_SRetrievalPlanDelete.addRow();
			//#CM718163
			// Select the line to set a value. 
			this.lst_SRetrievalPlanDelete.setCurrentRow(i + 1);
			//#CM718164
			// Planned Picking Date
			this.lst_SRetrievalPlanDelete.setValue(COL_RETRIEVAL_PLAN_DATE, 
				    WmsFormatter.toDispDate(param[i].getRetrievalPlanDate(), 
				    this.getHttpRequest().getLocale()));
			//#CM718165
			// Item/Order 
			this.lst_SRetrievalPlanDelete.setValue(COL_ITEM_ORDER,
			param[i].getItemOrderFlag());

			//#CM718166
			// Hidden field item 
			Vector hidden = new Vector();
			//#CM718167
			// Picking Plan unique key
			hidden.add(HIDDEN_UKEY, param[i].getRetrievalPlanUkey());
			//#CM718168
			// Update Date 
			hidden.add(HIDDEN_UPDATE, param[i].getLastUpdateDate());
			//#CM718169
			// Data count
			hidden.add(HIDDEN_DATA_COUNT, new Integer(param[i].getDataCount()));
		}
	}

	//#CM718170
	/**
	 * Set the value of option checkbox of listcell.
	 * 
	 * @param check Value to be set in the checkbox 
	 */
	private void listCellCheckAllChange(final boolean check)
	{
		for (int i = 1; i < this.lst_SRetrievalPlanDelete.getMaxRows(); i++)
		{
			this.lst_SRetrievalPlanDelete.setCurrentRow(i);
			this.lst_SRetrievalPlanDelete.setChecked(COL_SELECT, check);
		}
		
		setFocus(this.txt_WorkerCode);
	}

	//#CM718171
	// Event handler methods -----------------------------------------
	//#CM718172
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718173
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718174
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718175
	/**
	 * Clicking on the Menu button invokes this.  <BR>
	 * <BR>
	 * Summary: Shifts to the Menu screen. 
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		//#CM718176
		// Shift to the Menu screen. 
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM718177
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718178
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718179
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718180
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718181
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718182
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718183
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718184
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718185
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718186
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718187
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718188
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718189
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718190
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718191
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718192
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718193
	/**
	 * Clicking on the Search Consignor Code button invokes this.  <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for Consignor using the search condition. <BR>
	 * <BR>
	 * [Parameter]  *Mandatory Input <BR>
	 * <BR>
	 * <DIR>Consignor Code <BR>
	 * </DIR> <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM718194
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM718195
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM718196
		// Search through the Plan.
		param.setParameter(ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);

		//#CM718197
		// Work Status (Standby) 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		//#CM718198
		// Set the work status. 
		param.setParameter(ListRetrievalConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);

		//#CM718199
		// Both Order/Item 
		param.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ALL);

		//#CM718200
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do", param, "/progress.do");
	}

	//#CM718201
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lbl_StrtRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718202
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_StrtRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718203
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_StrtRtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718204
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_StrtRtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718205
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchStrtRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718206
	/**
	 * Clicking on the Search Start Planned Picking Date button invokes this.  <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Planned Picking Date using this search condition. <BR>
	 * <BR>
	 * [Parameter]  *Mandatory Input <BR>
	 * <BR>
	 * <DIR>
	 * Consignor Code <BR>
	 * Start Planned Picking Date <BR>
	 * </DIR> <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchStrtRtrivlPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM718207
		// Set the search condition in the Search screen of Start Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();

		//#CM718208
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM718209
		// Start Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(this.txt_StrtRtrivlPlanDate.getDate()));

		//#CM718210
		// Set the Start flag. 
		param.setParameter(ListRetrievalPlanDateBusiness.RANGERETRIEVALPLANDATE_KEY, RetrievalSupportParameter.RANGE_START);

		//#CM718211
		// "Search" flag 
		param.setParameter(
			ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);

		//#CM718212
		// Work Status (Standby) 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		//#CM718213
		// Set the work status. 
		param.setParameter(ListRetrievalPlanDateBusiness.WORKSTATUSRETRIEVALPLANDATE_KEY, search);

		//#CM718214
		// Both Order/Item 
		param.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ALL);

		//#CM718215
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", param, "/progress.do");
	}

	//#CM718216
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718217
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lbl_EdRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718218
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_EdRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718219
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_EdRtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718220
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_EdRtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718221
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchEdRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718222
	/**
	 * Clicking on the Search End Planned Picking Date button invokes this.  <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Planned Picking Date using this search condition. <BR>
	 * <BR>
	 * [Parameter]  *Mandatory Input <BR>
	 * <BR>
	 * <DIR>
	 * Consignor Code <BR>
	 * End Picking Date <BR>
	 * </DIR> <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchEdRtrivlPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM718223
		// Set the search condition in the Search screen of End Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();

		//#CM718224
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM718225
		// End Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(this.txt_EdRtrivlPlanDate.getDate()));

		//#CM718226
		// Set the Close flag. 
		param.setParameter(ListRetrievalPlanDateBusiness.RANGERETRIEVALPLANDATE_KEY, RetrievalSupportParameter.RANGE_END);

		//#CM718227
		// "Search" flag 
		param.setParameter(
			ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);

		//#CM718228
		// Work Status (Standby) 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		//#CM718229
		// Set the work status. 
		param.setParameter(ListRetrievalPlanDateBusiness.WORKSTATUSRETRIEVALPLANDATE_KEY, search);

		//#CM718230
		// Both Order/Item 
		param.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ALL);

		//#CM718231
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", param, "/progress.do");
	}

	//#CM718232
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lbl_ItemOrder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718233
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void rdo_ItemOrderAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718234
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void rdo_ItemOrderAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM718235
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void rdo_ItemOrderItem_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718236
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void rdo_ItemOrderItem_Click(ActionEvent e) throws Exception
	{
	}

	//#CM718237
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void rdo_ItemOrderOrder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718238
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void rdo_ItemOrderOrder_Click(ActionEvent e) throws Exception
	{
	}

	//#CM718239
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lbl_RtrivlPlanDltLst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718240
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void chk_CommonUse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718241
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void chk_CommonUse_Change(ActionEvent e) throws Exception
	{
	}

	//#CM718242
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718243
	/**
	 * Clicking on the Display button invokes this.  <BR>
	 * <BR>
	 * Summary: Displays using field items input in the Input area as conditions and display data in the Preset area. <BR>
	 * <BR>
	 * <DIR>
	 * 1. Set the cursor on the Worker code. <BR>
	 * 2. Check for input in the input field item in the input area.  <BR>
	 * <DIR>
	 * (Mandatory input, number of characters, and attribute of character)  <BR>
	 * (Start Picking Date: any value smaller than values of the End Picking Date)  <BR>
	 * </DIR> 3. Start the schedule.  <BR>
	 * 4. Display the schedule result in the preset area.  <BR>
	 * 5.Enable Select All button, Cancel All Selected button, Delete button, and Clear List button.  <BR>
	 * 6.Maintain the content of conditions of the Input area.  <BR>
	 * </DIR> <BR>
	 * <BR>
	 * Row No. list of listcell  <BR>
	 * <DIR>
	 * 1: Select <BR>
	 * 2:Planned Picking Date <BR>
	 * 3: Item/Order 
	 * </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		//#CM718244
		// Initialize all in the preset area.
		btn_ListClear_Click(e);

		//#CM718245
		// Check for input. (format, mandatory, prohibited characters) 
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();

		//#CM718246
		// The value of the Start Planned Picking Date must smaller than the value of the End Planned Picking Date:
		if (!StringUtil.isBlank(this.txt_StrtRtrivlPlanDate.getDate()) && !StringUtil.isBlank(this.txt_EdRtrivlPlanDate.getDate()))
		{
			if (this.txt_StrtRtrivlPlanDate.getDate().after(this.txt_EdRtrivlPlanDate.getDate()))
			{
				//#CM718247
				// 6023108 = Starting planned picking date must precede the end date. 
				message.setMsgResourceKey("6023108");
				return;
			}
		}

		Connection conn = null;

		try
		{
			//#CM718248
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM718249
			// Declare the parameter. 
			RetrievalSupportParameter param = new RetrievalSupportParameter();

			//#CM718250
			// Set the schedule parameter..
			//#CM718251
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM718252
			// Password 
			param.setPassword(txt_Password.getText());
			//#CM718253
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM718254
			// Start Planned Picking Date
			param.setFromRetrievalPlanDate(WmsFormatter.toParamDate(this.txt_StrtRtrivlPlanDate.getDate()));
			//#CM718255
			// End Planned Picking Date
			param.setToRetrievalPlanDate(WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));
			//#CM718256
			// Item/Order 
			if (this.rdo_ItemOrderAll.getChecked())
			{
				//#CM718257
				// All 
				this.viewState.setString(ITEM_ORDER, RetrievalSupportParameter.ITEMORDERFLAG_ALL);
				param.setItemOrderFlag(RetrievalSupportParameter.ITEMORDERFLAG_ALL);
			}
			else if (this.rdo_ItemOrderOrder.getChecked())
			{
				//#CM718258
				// Order 
				this.viewState.setString(ITEM_ORDER, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
				param.setItemOrderFlag(RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
			}
			else if (this.rdo_ItemOrderItem.getChecked())
			{
				//#CM718259
				// Item 
				this.viewState.setString(ITEM_ORDER, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
				param.setItemOrderFlag(RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
			}

			//#CM718260
			// Declare the schedule. 
			WmsScheduler schedule = new RetrievalPlanDeleteSCH();
			//#CM718261
			// Start the schedule. 
			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) schedule.query(conn, param);

			//#CM718262
			// Close the process when some error occurs or if there is no data to be displayed. 
			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM718263
			// Maintain the searched values in the ViewState. 
			//#CM718264
			// Consignor Code
			this.getViewState().setString(KEY_CONSIGNOR_CODE, txt_ConsignorCode.getText());
			//#CM718265
			// Start Planned Picking Date
			this.getViewState().setString(KEY_FROM_RETRIEVAL_PLAN_DATE, param.getFromRetrievalPlanDate());
			//#CM718266
			// End Planned Picking Date
			this.getViewState().setString(KEY_TO_RETRIEVAL_PLAN_DATE, param.getToRetrievalPlanDate());

			//#CM718267
			// Display the values in the preset area.
			addList(viewParam);

			//#CM718268
			// Enable the buttons in the Preset area. 
			setBtnTrueFalse(true);
			//#CM718269
			// Set the message. 
			message.setMsgResourceKey(schedule.getMessage());
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM718270
				// Close the connection. 
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
	}

	//#CM718271
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718272
	/**
	 * Clicking on the Clear button invokes this.  <BR>
	 * <BR>
	 * Summary: Clears the input area.  <BR>
	 * </BR> <DIR>1. Clear the field item in Input area.  <BR>
	 * <DIR>Worker Code [Keep as it is]  <BR>
	 * Password [as it is]  <BR>
	 * Consignor Code [Search Value]  <BR>
	 * Start Planned Picking Date [Clear]  <BR>
	 * End Planned Picking Date [Clear]  <BR>
	 * Item/Order [All]  <BR>
	 * Print the Deleted Picking Plan list report. [ture] <BR>
	 * </DIR> 2. Set the cursor on the Worker code.  <BR>
	 * 3. Maintain the contents of preset area.  <BR>
	 * </DIR> <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM718273
		// Set the initial value for each input field. 
		//#CM718274
		// Consignor Code
		this.txt_ConsignorCode.setText(getConsignorCode());
		this.txt_StrtRtrivlPlanDate.setText("");
		this.txt_EdRtrivlPlanDate.setText("");
		this.rdo_ItemOrderOrder.setChecked(false);
		this.rdo_ItemOrderItem.setChecked(false);
		this.rdo_ItemOrderAll.setChecked(true);
		this.chk_CommonUse.setChecked(true);

		//#CM718275
		// Set the cursor on the Worker code. 
		setFocus(this.txt_WorkerCode);
	}

	//#CM718276
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_AllCheck_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718277
	/**
	 * Clicking on the Select All button invokes this.  <BR>
	 * <BR>
	 * Summary: Places checks in all option checkboxes in the Preset area.  <BR>
	 * </BR> 
	 * <DIR>
	 * 1.Place a check to the option of which option checkbox is enabled.  <BR>
	 * 2. Set the cursor on the Worker code.  <BR>
	 * 3. Maintain the contents of the input area.  <BR>
	 * </DIR> <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_AllCheck_Click(ActionEvent e) throws Exception
	{
		listCellCheckAllChange(true);
	}

	//#CM718278
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_AllCheckClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718279
	/**
	 * Clicking on the Cancel Select All button invokes this.  <BR>
	 * <BR>
	 * Summary: Clears all checks placed in the option checkbox in the Preset area.  <BR>
	 * </BR>
	 *  <DIR>
	 * 1. Clear the check from the option of which option checkbox is enabled.  <BR>
	 * 2. Set the cursor on the Worker code.  <BR>
	 * 3. Maintain the contents of the input area.  <BR>
	 * </DIR> <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_AllCheckClear_Click(ActionEvent e) throws Exception
	{
		listCellCheckAllChange(false);
	}

	//#CM718280
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Delete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718281
	/**
	 * Clicking on Delete button invokes this.  <BR>
	 * <BR>
	 * Summary: Deletes the picking plan info using the info in the Preset area. <BR>
	 * </BR> 
	 * <DIR>
	 * 1. Set the cursor on the Worker code.  <BR>
	 * 2.Obtain the from viewState and set it for the parameter.  <BR>
	 * 3. Set all info in the preset area for the parameter.  <BR>
	 * 4. Start the schedule.  <BR>
	 * 5. Load again the info obtained from the updated schedule to the preset area, and display it. <BR>
	 * 6.If no info exists in the Preset area, disable the Select All button, Cancel All Selection button, the Delete button, and the Clear List button.  <BR>
	 * </DIR> <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Delete_Click(ActionEvent e) throws Exception
	{
		//#CM718282
		//	  Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);

		//#CM718283
		// Check for input values in Worker Code and Password. 
		txt_WorkerCode.validate();
		txt_Password.validate();

		Connection conn = null;

		try
		{
			//#CM718284
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM718285
			// Check for presence of Deleted data. 
			boolean wCheck = false;

			//#CM718286
			// Check the elements of the preset, and set values in the Vector of the lines only to be updated. 
			Vector vecParam = new Vector(this.lst_SRetrievalPlanDelete.getMaxRows());
			for (int i = 1; i < this.lst_SRetrievalPlanDelete.getMaxRows(); i++)
			{
				//#CM718287
				// Obtain the line to be operated. 
				this.lst_SRetrievalPlanDelete.setCurrentRow(i);

				//#CM718288
				// Execute nothing for the line with no check placed to select. 
				if (!this.lst_SRetrievalPlanDelete.getChecked(COL_SELECT))
				{
					continue ;
				}
				else
				{
					wCheck = true;
				}
				
				RetrievalSupportParameter param = new RetrievalSupportParameter();

				//#CM718289
				// Terminal No.
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());
				//#CM718290
				// Worker Code
				param.setWorkerCode(txt_WorkerCode.getText());
				//#CM718291
				// Set the Password. 
				param.setPassword(txt_Password.getText());
				//#CM718292
				// Set whether to print the Deleted Picking Plan list report. 
				param.setDeleteRetrievalListFlag(chk_CommonUse.getChecked());

				//#CM718293
				// Set Search Condition (Consignor, Start Planned Picking Date, and End Planned Picking Date) for re-display
				param.setConsignorCode(this.getViewState().getString(KEY_CONSIGNOR_CODE));
				param.setFromRetrievalPlanDate(this.getViewState().getString(KEY_FROM_RETRIEVAL_PLAN_DATE));
				param.setToRetrievalPlanDate(this.getViewState().getString(KEY_TO_RETRIEVAL_PLAN_DATE));
				//#CM718294
				// Item/Order division 
				param.setItemOrderFlag(getItemOrderValue(this.lst_SRetrievalPlanDelete.getValue(COL_ITEM_ORDER)));
				
				//#CM718295
				// Planned Picking Date
				param.setRetrievalPlanDate(WmsFormatter.toParamDate(this.lst_SRetrievalPlanDelete.getValue(COL_RETRIEVAL_PLAN_DATE), this.getHttpRequest().getLocale()));
				
				//#CM718296
				// Preset line No. 
				param.setRowNo(i);
				//#CM718297
				// Picking Plan unique key
				param.setRetrievalPlanUkey(CollectionUtils.getString(HIDDEN_UKEY, this.lst_SRetrievalPlanDelete.getValue(COL_HIDDEN)));
				//#CM718298
				// Update Date/Time 
				param.setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(HIDDEN_UPDATE,this.lst_SRetrievalPlanDelete.getValue(COL_HIDDEN))));
				
				vecParam.addElement(param);
			}

			//#CM718299
			// If no option checkbox is ticked: 
			if (wCheck == false)
			{
				//#CM718300
				// 6023111=Please check the data to delete. 
				message.setMsgResourceKey("6023111");
				return;
			}

			//#CM718301
			// Declare the parameter. 
			RetrievalSupportParameter[] paramArray = new RetrievalSupportParameter[vecParam.size()];
			//#CM718302
			// Copy the value to the parameter. 
			vecParam.copyInto(paramArray);

			//#CM718303
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalPlanDeleteSCH();

			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) schedule.startSCHgetParams(conn, paramArray);

			//#CM718304
			// If the returned data is null, determin that the update failed. 
			//#CM718305
			// Roll-back and set a message. (with the preset area that keeps displaying the former data) 
			if (viewParam == null)
			{
				//#CM718306
				// Roll-back 
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			//#CM718307
			// Update is successful if the number of elements of the returned data is 0 or more. 
			//#CM718308
			// Commit the transaction and display the preset area again. 
			else if (viewParam.length >= 0)
			{
				//#CM718309
				// Commit 
				conn.commit();

				//#CM718310
				// When there is no data to be displayed 
				if (viewParam.length == 0)
				{
					//#CM718311
					// Clear data in the preset area. 
					btn_ListClear_Click(e);
				}
				else
				{
					//#CM718312
					// Display the data if obtained the display data when the schedule normally completed. 
					this.lst_SRetrievalPlanDelete.clearRow();
					//#CM718313
					// Display again in the preset area. 
					addList(viewParam);
				}
			}

			//#CM718314
			// Set the message. 
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM718315
				// Close the connection. 
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
	}

	//#CM718316
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718317
	/**
	 * Clicking on the Clear List button invokes this.  <BR>
	 * <BR>
	 * Summary: Displays a dialog box to allow to confirm to clear the display of the preset area or not.  <BR>
	 * </BR>
	 *  <DIR>
	 * 1. Display the dialog box for confirmation. "This clears the input information in the list. Do you confirm it?"<BR>
	 * [Dialog for confirming: OK]  <BR>
	 * <DIR>1. Initialize a listcell.  <BR>
	 * 2.Clear the condition of Preset area.  <BR>
	 * 3.Disable Select All button, Cancel All Selected button, Delete button, and Clear List button.  <BR>
	 * 4. Set the cursor on the Worker code.  <BR>
	 * </DIR> 
	 * [Dialog for confirming: Cancel]  <BR>
	 * <DIR>
	 * Disable to do anything.  <BR>
	 * </DIR> </DIR> <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		//#CM718318
		// Delete all the Line info. 
		this.lst_SRetrievalPlanDelete.clearRow();
		//#CM718319
		// Clear the Consignor field in the Preset area. 
		this.txt_RConsignorCode.setText("");
		this.txt_RConsignorName.setText("");
		
		//#CM718320
		// Disable the buttons in the Preset area. 
		setBtnTrueFalse(false);
		//#CM718321
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM718322
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718323
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718324
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718325
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718326
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718327
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718328
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718329
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718330
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718331
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lbl_ItemOrderT_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718332
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_RItemOrder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718333
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_RItemOrder_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718334
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_RItemOrder_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718335
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void txt_RItemOrder_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718336
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lst_SRetrievalPlanDelete_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718337
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lst_SRetrievalPlanDelete_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718338
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lst_SRetrievalPlanDelete_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718339
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lst_SRetrievalPlanDelete_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM718340
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lst_SRetrievalPlanDelete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718341
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lst_SRetrievalPlanDelete_Change(ActionEvent e) throws Exception
	{
	}

	//#CM718342
	/**
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lst_SRetrievalPlanDelete_Click(ActionEvent e) throws Exception
	{
	}
	//#CM718343
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Delete_Click(ActionEvent e) throws Exception
	{
	}

	//#CM718344
	/**
	 * Return the divisions of the passed Item/Order names. 
	 * 
	 * @param	itemOrderName Item/ Order name 
	 * @return	Item/Order division
	 */
	private String getItemOrderValue(String itemOrderName)
	{
		String value = "";
		
		//#CM718345
		// Item 
		if (itemOrderName.equals(DisplayText.getText("LBL-W0434")))
		{
			value =RetrievalSupportParameter.ITEMORDERFLAG_ITEM;
		}
		//#CM718346
		// Order 
		else if(itemOrderName.equals(DisplayText.getText("LBL-W0435")))
		{
			value = RetrievalSupportParameter.ITEMORDERFLAG_ORDER;
		}
		
		return value;
	}

}
//#CM718347
//end of class
