// $Id: RetrievalOrderScheduleBusiness.java,v 1.2 2007/02/07 04:19:26 suresh Exp $

//#CM717109
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalorderschedule;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderScheduleSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM717110
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * Allow this class of screen to start the Order Picking. <BR>
 * Pass the parameter to the schedule to set Order Picking. <BR>
 * Allow this screen to commit and roll back the transaction. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process by clicking "Display" button (<CODE>btn_View_Click()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Set the contents entered via screen, and allow the schedule to search for data to be displayed based on the parameter.<BR>
 *   Receive data to be output to the preset area from the schedule and output it to the preset area. <BR>
 *   <BR>
 *   [Parameter]  *Mandatory Input<BR>
 *   <BR>
 *   Worker Code* <BR>
 *   Password * <BR>
 *   Consignor Code* <BR>
 *   Planned Picking Date* <BR>
 *   Start Order No.  <BR>
 *   End Order No.  <BR>
 *   Case/Piece division <BR>
 *   <BR>
 *   [Data for Output]  <BR>
 *   <BR>
 *   Consignor Code <BR>
 *   Consignor Name <BR>
 *   Planned Picking Date <BR>
 *   Order No. <BR>
 *   Case/Piece division (for display)  <BR>
 *   Case/Piece division name (for display)  <BR>
 *   Customer Code <BR>
 *   Customer Name <BR>
 * </DIR>
 * <BR>
 * 2. Process by clicking on "Start Picking" button (<CODE>btn_RetrievalStart_Click()</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *   Set the contents input via preset area for the parameter, and allow the schedule to set the Picking based on the parameter.<BR>
 *   Receive the result from the schedule, and receive the data to be displayed if the process completed normally, or<BR>
 *   Receive null when the schedule failed to complete due to condition error or other causes. <BR>
 *   Output the message obtained from the schedule to the screen, as the schedule result. <BR>
 *   <BR>
 *   [Parameter]  *Mandatory Input <BR>
 *   <BR>
 *   Worker Code* <BR>
 *   Password * <BR>
 *   Consignor Code <BR>
 *   Consignor Name <BR>
 *   Planned Picking Date <BR>
 *   Start Order No.  <BR>
 *   End Order No.  <BR>
 *   Case/Piece division <BR>
 *   Allocation target, storage place  <BR>
 *   Shortage evidence division  <BR>
 *   Line No<BR>
 *   Selected Order No.  <BR>
 *   Selected Case/Piece division  <BR>
 *   Selected Customer Code  <BR>
 *   Terminal No. <BR>
 *   Plan unique key  []  <BR>
 *   Last update date/time []  <BR>
 *   <BR>
 *   [Data for Output]  <BR>
 *   <BR>
 *   Consignor Code <BR>
 *   Consignor Name <BR>
 *   Planned Picking Date <BR>
 *   Order No. <BR>
 *   Case/Piece division (for display)  <BR>
 *   Case/Piece division name (for display)  <BR>
 *   Customer Code <BR>
 *   Customer Name <BR>
 *   Plan unique key  []  <BR>
 *   Last update date/time []  <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/28</TD><TD>C.Kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:26 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderScheduleBusiness extends RetrievalOrderSchedule implements WMSConstants
{
	//#CM717111
	// Class fields --------------------------------------------------
	//#CM717112
	// Hidden Parameter 
	//#CM717113
	// Case/Piece division
	private static final int HDN_CASEPIECE_FLAG = 0;

	//#CM717114
	// for ViewState 
	//#CM717115
	// Consignor Code
	private static final String VS_CONSIGNOR = "CONSIGNOR_CODE";
	//#CM717116
	// Planned Picking Date
	private static final String VS_PLANDATE = "PLAN_DATE";
	//#CM717117
	// Start Order No. 
	private static final String VS_START_ORDERNO = "START_ORDER_NO";
	//#CM717118
	// End Order No. 
	private static final String VS_END_ORDERNO = "END_ORDER_NO";
	//#CM717119
	// Case/Piece division
	private static final String VS_CPFLAG = "VS_CPFLAG";
	
	//#CM717120
	// Class variables -----------------------------------------------

	//#CM717121
	// Class method --------------------------------------------------

	//#CM717122
	// Constructors --------------------------------------------------

	//#CM717123
	// Public methods ------------------------------------------------

	//#CM717124
	/**
	 * Initialize the screen. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM717125
			// Tick the field of Case/Piece division. 
			rdo_CpfAll.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);

			//#CM717126
			// Disable to click a button. 
			setBtnTrueFalse(false);

			//#CM717127
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalOrderScheduleSCH();
			RetrievalSupportParameter param = (RetrievalSupportParameter) schedule.initFind(conn, null);

			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				//#CM717128
				// For data with only one Consignor code, display the initial display. 
				txt_ConsignorCode.setText(param.getConsignorCode());
			}

			//#CM717129
			// Display the area only wthich is maintained in the system, on screen. 
			chk_Allocate_Place_Hira.setVisible(false);
			chk_Allocate_Place_Idm.setVisible(false);
			chk_Allocate_Place_Asrs.setVisible(false);
			chk_Allocate_Place_Hira.setChecked(false);
			chk_Allocate_Place_Idm.setChecked(false);
			chk_Allocate_Place_Asrs.setChecked(false);
			int[] field = param.getSystemDiscKeyArray();
			for (int i = 0; i < field.length; i++)
			{
				if (field[i] == RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS)
				{
					chk_Allocate_Place_Asrs.setVisible(true);
					chk_Allocate_Place_Asrs.setChecked(true);
			
				}
				else if (field[i] == RetrievalSupportParameter.SYSTEM_DISC_KEY_IDM)
				{
					chk_Allocate_Place_Idm.setVisible(true);
					chk_Allocate_Place_Idm.setChecked(true);
			
				}
				else
				{
					chk_Allocate_Place_Hira.setVisible(true);
					chk_Allocate_Place_Hira.setChecked(true);
			
				}
			}

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM717130
				// Close the connection. 
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM717131
	/**
	 * Invoke this before invoking each control event. <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM717132
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM717133
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM717134
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
		//#CM717135
		// Dialog before clicking on Start Picking button to confirm: MSG-W0019= Do you start it? 
		btn_RetrievalStart.setBeforeConfirm("MSG-W0019");

		//#CM717136
		// Clicking the Clear List button displays a dialog box to allow user to confirm: MSG-W0012= Input info in the list is cleared. Do you confirm it? 
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		//#CM717137
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM717138
	/**
	 * Returning from a popup window invokes this method.
	 * Override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE>. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM717139
		// Obtain the parameter selected in the listbox. 
		//#CM717140
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM717141
		// Planned Picking Date
		String retPlan = param.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM717142
		// Start Order No. 
		String startorderNo = param.getParameter(ListRetrievalOrdernoBusiness.STARTORDERNO_KEY);
		//#CM717143
		// End Order No. 
		String endorderNo = param.getParameter(ListRetrievalOrdernoBusiness.ENDORDERNO_KEY);

		//#CM717144
		// Set a value if not empty. 
		//#CM717145
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM717146
		// Planned Picking Date
		if (!StringUtil.isBlank(retPlan))
		{
			txt_RtrivlPlanDate.setDate(WmsFormatter.toDate(retPlan));
		}
		//#CM717147
		// Start Order No. 
		if (!StringUtil.isBlank(startorderNo))
		{
			txt_StartOrderNo.setText(startorderNo);
		}
		//#CM717148
		// End Order No. 
		if (!StringUtil.isBlank(endorderNo))
		{
			txt_EndOrderNo.setText(endorderNo);
		}
	}

	//#CM717149
	// Package methods -----------------------------------------------

	//#CM717150
	// Protected methods ---------------------------------------------

	//#CM717151
	// Private methods -----------------------------------------------
	//#CM717152
	/**
	 * Set the value for the preset area. 
	 * @param listParams Parameter object that contains info to be displayed in the preset area.  
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(Parameter[] listParams) throws Exception
	{
		//#CM717153
		// Delete all lines. 
		lst_RetrievalOrderSchedule.clearRow();
		if (listParams == null || listParams.length <= 0)
		{
			return ;
		}
		RetrievalSupportParameter param = (RetrievalSupportParameter) listParams[0];

		//#CM717154
		// Consignor Code (read-only) 
		txt_JavaSetConsignorCode.setText(param.getConsignorCode());
		//#CM717155
		// Consignor Name (read-only) 
		txt_JavaSetConsignorName.setText(param.getConsignorName());
		//#CM717156
		// Planned Picking Date (read-only) 
		txt_JavaSetPlanDate.setDate(WmsFormatter.toDate(param.getRetrievalPlanDate()));

		RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) listParams;

		for (int i = 0; i < viewParam.length; i++)
		{
			//#CM717157
			// Add a line. 
			lst_RetrievalOrderSchedule.setCurrentRow(i + 1);
			lst_RetrievalOrderSchedule.addRow();
			
			lst_RetrievalOrderSchedule.setValue(2, viewParam[i].getOrderNoL());
			lst_RetrievalOrderSchedule.setValue(3, viewParam[i].getCasePieceflgName());
			lst_RetrievalOrderSchedule.setValue(4, viewParam[i].getCustomerCode());
			lst_RetrievalOrderSchedule.setValue(5, viewParam[i].getCustomerName());

			//#CM717158
			// Set the Hidden field item. 
			lst_RetrievalOrderSchedule.setValue(0, createHiddenList(viewParam[i]));
		}

		//#CM717159
		// Enable to click a button. 
		setBtnTrueFalse(true);
	}

	//#CM717160
	/**
	 * Allow this method to set acceptability to compile buttons. <BR>
	 * <BR>
	 * Summary: Sets to determine whether to compile buttons after receiving false or true. <BR>
	 * @param  arg status(false or true)
	 * @return None 
	 */
	private void setBtnTrueFalse(boolean arg)
	{
		//#CM717161
		// Disable to click a button. 
		//#CM717162
		// "Start Picking" button 
		btn_RetrievalStart.setEnabled(arg);
		//#CM717163
		// "Select All" button 
		btn_AllCheck.setEnabled(arg);
		//#CM717164
		// Cancel All Selected 
		btn_AllCheckClear.setEnabled(arg);
		//#CM717165
		// "Clear List" button 
		btn_ListClear.setEnabled(arg);
	}

	//#CM717166
	/**
	 * Allow this method to concatenate HIDDEN field items into a single string. <BR>
	 * <BR>
	 * HIDDEN field item order list  <BR>
	 * <DIR>
	 *    0:Work No. <BR>
	 *    1:Last update date/time <BR>
	 * </DIR>
	 * 
	 * @param viewParam ShippingParameter <CODE>Parameter</CODE> class that contains HIDDENField item.
	 * @return Connected hidden field item 
	 */
	private String createHiddenList(RetrievalSupportParameter viewParam)
	{
		//#CM717167
		// Generate a string of hidden field item.  
		ArrayList hiddenList = new ArrayList();
		//#CM717168
		// Hidden field item 
		//#CM717169
		// Case/Piece division
		hiddenList.add(HDN_CASEPIECE_FLAG, viewParam.getCasePieceFlagL());
		
		return CollectionUtils.getConnectedString(hiddenList);
	}

	//#CM717170
	/**
	 * Allow this method to obtain the Consignor code from the schedule. <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule. <BR>
	 * <DIR>
	 *   1. If there is only one Consignor Code, return the corresponding Consignor. Otherwise, return null.  <BR>
	 * <DIR>
	 * 
	 * @throws Exception Report all exceptions. 
	 * @return Consignor Code
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM717171
			// Obtain connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM717172
			// Declare the parameter. 
			RetrievalSupportParameter param = new RetrievalSupportParameter();

			//#CM717173
			// Obtain the Consignor code from the schedule. 
			WmsScheduler schedule = new RetrievalOrderScheduleSCH();
			//#CM717174
			// Start the schedule. 
			param = (RetrievalSupportParameter) schedule.initFind(conn, param);

			//#CM717175
			// Data exists in param. 
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
				//#CM717176
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

	//#CM717177
	/**
	 * Allow this method to obtain the Area No. from the selected storage place for allocation.<BR>
	 * 
	 * @throws Exception Report all exceptions. 
	 * @return Area No.
	 */
	private String[] getAllocationArea() throws Exception
	{
		Vector tempVec = new Vector();
		//#CM717178
		// Flat Storage  target 
		if (chk_Allocate_Place_Hira.getChecked())
		{
			tempVec.addElement(Integer.toString(RetrievalSupportParameter.SYSTEM_DISC_KEY_FLOOR));
		}
		//#CM717179
		// IDM target 
		if (chk_Allocate_Place_Idm.getChecked())
		{
			tempVec.addElement(Integer.toString(RetrievalSupportParameter.SYSTEM_DISC_KEY_IDM));
		}
		//#CM717180
		// Target the Automated Warehouse.  
		if (chk_Allocate_Place_Asrs.getChecked())
		{
			tempVec.addElement(Integer.toString(RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS));
		}
		
		String[] allocationArea = new String[tempVec.size()];
		tempVec.copyInto(allocationArea);
		
		return allocationArea;

	}

	//#CM717181
	// Event handler methods -----------------------------------------
	//#CM717182
	/** 
	 * Clicking on the "To Menu" button invokes this. 
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM717183
	/** 
	 * Clicking on the "Search Consignor" button invokes this. 
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PS_ConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM717184
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM717185
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM717186
		// "Search" flag 
		param.setParameter(ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM717187
		// for Order 
		param.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM717188
		// Work status (work status data with "Standby") 
		String[] search = new String[4];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(RetrievalSupportParameter.STATUS_FLAG_STARTED);
		search[2] = new String(RetrievalSupportParameter.STATUS_FLAG_WORKING);
		search[3] = new String(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		param.setParameter(ListRetrievalConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		//#CM717189
		// Schedule flag 
		String[] schFlag = new String[3];
		schFlag[0] = RetrievalSupportParameter.SCHEDULE_UNSTART;
		schFlag[1] = RetrievalSupportParameter.SCHEDULE_CASE_COMPLETION;
		schFlag[2] = RetrievalSupportParameter.SCHEDULE_PIECE_COMPLETION;
		param.setParameter(ListRetrievalConsignorBusiness.SCHEDULE_FLAG_KEY, schFlag);
		
		//#CM717190
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do", param, "/progress.do");
	}

	//#CM717191
	/** 
	 * Clicking on the Search Planned Picking Date button invokes this. 
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PS_RetrievalPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM717192
		// Set the search condition in the Search screen of Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM717193
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM717194
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM717195
		// "Search" flag 
		param.setParameter(ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM717196
		// for Order 
		param.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM717197
		// Work status (work status data with "Standby") 
		String[] search = new String[4];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(RetrievalSupportParameter.STATUS_FLAG_STARTED);
		search[2] = new String(RetrievalSupportParameter.STATUS_FLAG_WORKING);
		search[3] = new String(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		param.setParameter(ListRetrievalPlanDateBusiness.WORKSTATUSRETRIEVALPLANDATE_KEY, search);
		//#CM717198
		// Schedule flag 
		String[] schFlag = new String[3];
		schFlag[0] = RetrievalSupportParameter.SCHEDULE_UNSTART;
		schFlag[1] = RetrievalSupportParameter.SCHEDULE_CASE_COMPLETION;
		schFlag[2] = RetrievalSupportParameter.SCHEDULE_PIECE_COMPLETION;
		param.setParameter(ListRetrievalPlanDateBusiness.SCHEDULE_FLAG_KEY, schFlag);
		
		//#CM717199
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", param, "/progress.do");
	}

	//#CM717200
	/** 
	 * Clicking on the Display button invokes this. <BR>
	 * <BR>
	 * Summary: Searches through Work Status Info using field items input in the Input area as conditions and display data in the Preset area.<BR>
	 * <BR>
	 * <DIR>
	 *   1. Check for input in the input field item in the input area. (Mandatory Input, number of characters, and attribution of characters) <BR>
	 *   2. Start the schedule. <BR>
	 *   3. Display it in the preset area. <BR>
	 *   4.Enable the Start Picking button, Select All button, Cancel All Selected button, and Clear List button. <BR>
	 *   5. Maintain the contents of the input area. <BR>
	 * </DIR>
	 * <BR>
	 * <DIR>
	 *   [Row No. list of listcell] <BR>
	 *   <BR>
	 *   <DIR>
	 *      1. Select <BR>
	 *      2.Order No. <BR>
	 * 		3. Division  <BR>
	 * 		4.Customer Code <BR>
	 * 		5.Customer Name <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */

	public void btn_View_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM717201
			// Clear the preset area when input error occurs. 
			btn_ListClear_Click(e);
	
			//#CM717202
			// Check for input. 
			txt_WorkerCode.validate();
			txt_PassWord.validate();
			txt_ConsignorCode.validate();
			txt_RtrivlPlanDate.validate();
			//#CM717203
			// Pattern matching characters 
			txt_StartOrderNo.validate(false);
			txt_EndOrderNo.validate(false);
	
			//#CM717204
			// Set a search value input via screen for the schedule parameter. 
			RetrievalSupportParameter param = new RetrievalSupportParameter();
			param.setWorkerCode(txt_WorkerCode.getText());
			param.setPassword(txt_PassWord.getText());
			param.setConsignorCode(txt_ConsignorCode.getText());
			param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
			param.setStartOrderNo(txt_StartOrderNo.getText());
			param.setEndOrderNo(txt_EndOrderNo.getText());
			if (rdo_CpfAll.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
			}
			else if (rdo_CpfCase.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_CpfPiece.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
			}
			else
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
			}

			//#CM717205
			// Store the searched values in the ViewState. 
			this.getViewState().setString(VS_CONSIGNOR, txt_ConsignorCode.getText());
			this.getViewState().setString(VS_PLANDATE, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));	
			this.getViewState().setString(VS_START_ORDERNO, txt_StartOrderNo.getText());
			this.getViewState().setString(VS_END_ORDERNO, txt_EndOrderNo.getText());
			if (rdo_CpfAll.getChecked())
			{
				this.getViewState().setString(VS_CPFLAG, RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
			}
			else if (rdo_CpfCase.getChecked())
			{
				this.getViewState().setString(VS_CPFLAG, RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_CpfPiece.getChecked())
			{
				this.getViewState().setString(VS_CPFLAG, RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
			}
			else
			{
				this.getViewState().setString(VS_CPFLAG, RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
			}

			//#CM717206
			// Obtain connection and start the schedule. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			WmsScheduler schedule = new RetrievalOrderScheduleSCH();
			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) schedule.query(conn, param);
	
			//#CM717207
			// When there is no data to be displayed 
			if (viewParam == null || viewParam.length == 0)
			{
				//#CM717208
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
	
			//#CM717209
			// Set in the listcell if any data to display is found. 
			setList(viewParam);
				
			//#CM717210
			// Set the message. 
			message.setMsgResourceKey(schedule.getMessage());
	
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM717211
				// Close the connection. 
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM717212
	/** 
	 * Clicking on the "Clear" button invokes this. 
	 * <BR>
	 * Summary: Initialized field items to be entered via screen. 
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM717213
		// Set the initial value for each input field. 
		//#CM717214
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM717215
		// Planned Picking Date
		txt_RtrivlPlanDate.setText("");
		//#CM717216
		// Start Order No. 
		txt_StartOrderNo.setText("");
		//#CM717217
		// End Order No. 
		txt_EndOrderNo.setText("");
		//#CM717218
		// Case/Piece division
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);

		//#CM717219
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM717220
	/** 
	 * Clicking on the Start Picking button invokes this. <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_RetrievalStart_Click(ActionEvent e) throws Exception
	{
		//#CM717221
		// Check for input. 
		txt_WorkerCode.validate();
		txt_PassWord.validate();
		
		//#CM717222
		// If the allocation target is not ticked, trigger error. 
		if(	!chk_Allocate_Place_Hira.getChecked() && 
			!chk_Allocate_Place_Idm.getChecked() && 
			!chk_Allocate_Place_Asrs.getChecked())
		{
			//#CM717223
			// 6023485=Select allocation target, storage place 
			message.setMsgResourceKey("6023485");
			return;
		}

		//#CM717224
		// Obtain the maximum (max) number of lines. 
		int index = lst_RetrievalOrderSchedule.getMaxRows();

		Connection conn = null;
		try
		{
			//#CM717225
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			Vector vecParam = new Vector(index);
			for (int i = 1; i < index; i++)
			{
				//#CM717226
				// Designate the line. 
				lst_RetrievalOrderSchedule.setCurrentRow(i);
				//#CM717227
				// Disable to set a not-selected line for parameter. 
				if (!lst_RetrievalOrderSchedule.getChecked(1))
				{
					continue;
				}
				//#CM717228
				// Set for the schedule parameter: 
				RetrievalSupportParameter param = new RetrievalSupportParameter();
				//#CM717229
				// --- Worker Code-Password ---
				param.setWorkerCode(txt_WorkerCode.getText());
				param.setPassword(txt_PassWord.getText());
				
				//#CM717230
				// --- Set Search condition for repeated search ---
				//#CM717231
				// Consignor, Planned Picking Date, Order No., and Case/Piece division 
				param.setConsignorCode(this.getViewState().getString(VS_CONSIGNOR));
				param.setRetrievalPlanDate(this.getViewState().getString(VS_PLANDATE));
				param.setStartOrderNo(this.getViewState().getString(VS_START_ORDERNO));
				param.setEndOrderNo(this.getViewState().getString(VS_END_ORDERNO));
				param.setCasePieceflg(this.getViewState().getString(VS_CPFLAG));

				//#CM717232
				// --- Set the data for the preset area. ---
				//#CM717233
				// Allocation target, storage place 
				param.setAllocateField(getAllocationArea());
				//#CM717234
				// Shortage evidence division 
				param.setShortageFlag(chk_Keppin_Chk.getChecked());
				//#CM717235
				// Line No. 
				param.setRowNo(i);
				
				//#CM717236
				// Data in a listcell
				param.setOrderNoL(lst_RetrievalOrderSchedule.getValue(2));
				param.setCasePieceFlagL(lst_RetrievalOrderSchedule.getValue(3));
				param.setCustomerCode(lst_RetrievalOrderSchedule.getValue(4));
				
				//#CM717237
				// Set the hidden field item. (Work No. (0), Last update date/time (1)) 
				String hidden = lst_RetrievalOrderSchedule.getValue(0);
				//#CM717238
				// Case/Piece division 
				param.setCasePieceFlagL(CollectionUtils.getString(HDN_CASEPIECE_FLAG, hidden));

				//#CM717239
				// Terminal No.
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());

				vecParam.addElement(param);
			}
			if (vecParam.size() <= 0)
			{
				//#CM717240
				// 6023154=There is no data to update. 
				message.setMsgResourceKey("6023154");
				return;
			}

			RetrievalSupportParameter[] paramArray = new RetrievalSupportParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			WmsScheduler schedule = new RetrievalOrderScheduleSCH();

			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[])schedule.startSCHgetParams(conn, paramArray);
			//#CM717241
			// Start the schedule. 
			if (viewParam != null)
			{
				//#CM717242
				// Commit the transaction. 
				conn.commit();
				//#CM717243
				// When there is no data to be displayed 
				if (viewParam.length == 0)
				{
					//#CM717244
					// Delete all the Line info. 
					lst_RetrievalOrderSchedule.clearRow();
					//#CM717245
					// Clear the Consignor field in the Preset area. 
					txt_JavaSetConsignorCode.setText("");
					txt_JavaSetConsignorName.setText("");
					txt_JavaSetPlanDate.setText("");
					//#CM717246
					// Disable the buttons in the Preset area. 
					setBtnTrueFalse(false);
					//#CM717247
					// Set the cursor on the Worker code. 
					setFocus(txt_WorkerCode);
				}
				else
				{
					//#CM717248
					// Set the listcell. 
					setList(viewParam);
				}
				//#CM717249
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.rollback();
				//#CM717250
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());
			}
		}
		catch (Exception ex)
		{
				ex.printStackTrace();
				
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM717251
				// Close the connection. 
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM717252
	/**
	 * Clicking on Select All button invokes this.  <BR>
	 * <BR>
	 * Summary: Places checks in all option checkboxes in the Preset area.  <BR>
	 * </BR>
	 * <DIR>
	 *     1.Tick in the option checkbox in the Preset area.  <BR>
	 *     2. Set the cursor on the Worker code.  <BR>
	 *     3. Maintain the contents of the input area.  <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_AllCheck_Click(ActionEvent e) throws Exception
	{
		//#CM717253
		// Place a check in option checkbox. 
		for (int i = 1; i < lst_RetrievalOrderSchedule.getMaxRows(); i++)
		{
			//#CM717254
			// Select a line to work. 
			lst_RetrievalOrderSchedule.setCurrentRow(i);
			lst_RetrievalOrderSchedule.setChecked(1, true);
		}
		//#CM717255
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM717256
	/**
	 * Clicking on the Cancel Select All button invokes this.  <BR>
	 * <BR>
	 * Summary: Clears all checks placed in the option checkbox in the Preset area.  <BR>
	 * </BR>
	 * <DIR>
	 *     1.Clear the checked placed in the option checkbox in the Preset area.  <BR>
	 *     2. Set the cursor on the Worker code.  <BR>
	 *     3. Maintain the contents of the input area.  <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_AllCheckClear_Click(ActionEvent e) throws Exception
	{
		//#CM717257
		// Clear check placed in the option checkbox. 
		for (int i = 1; i < lst_RetrievalOrderSchedule.getMaxRows(); i++)
		{
			//#CM717258
			// Select a line to work. 
			lst_RetrievalOrderSchedule.setCurrentRow(i);
			lst_RetrievalOrderSchedule.setChecked(1, false);
		}
		//#CM717259
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM717260
	/**
	 * Clicking on the Clear List button invokes this.  <BR>
	 * <BR>
	 * Summary: Displays a dialog box to allow to confirm to clear the display of the preset area or not.  <BR>
	 * </BR>
	 * <DIR>
	 *     1. Display the dialog box for confirmation. "This clears the input information in the list. Do you confirm it?"<BR>
	 *       [Dialog for confirming: OK]  <BR>
	 *       <DIR>
	 *         1. Initialize a listcell.  <BR>
	 *         2.Clear the condition of Preset area.  <BR>
	 *         3.Disable Select All button, Cancel All Selected button, Delete button, and Clear List button.  <BR>
	 *         4. Set the cursor on the Worker code.  <BR>
	 *       </DIR>
	 *       [Dialog for confirming: Cancel]  <BR>
	 *       <DIR>
	 *          Disable to do anything.  <BR>
	 *       </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		//#CM717261
		// Delete all the Line info. 
		lst_RetrievalOrderSchedule.clearRow();
		//#CM717262
		// Clear the Consignor field in the Preset area. 
		txt_JavaSetConsignorCode.setText("");
		txt_JavaSetConsignorName.setText("");
		txt_JavaSetPlanDate.setText("");
		//#CM717263
		// Disable the buttons in the Preset area. 
		setBtnTrueFalse(false);

		//#CM717264
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM717265
	/** 
	 * Clicking on the Search Start Order No. Search button invokes this. 
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PS_StartOrderNo_Click(ActionEvent e) throws Exception
	{
		//#CM717266
		// Set the search condition in the Search screen of Order No. 
		ForwardParameters param = new ForwardParameters();
		//#CM717267
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM717268
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM717269
		// Start Order No. 
		param.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, txt_StartOrderNo.getText());
		//#CM717270
		// Case/Piece flag for search
		param.setParameter(ListRetrievalOrdernoBusiness.CASE_PIECE_KEY, "");
		//#CM717271
		// "Search" flag 
		param.setParameter(ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM717272
		// for Order 
		param.setParameter(ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM717273
		// For starting 
		param.setParameter(ListRetrievalOrdernoBusiness.RANGE_KEY, RetrievalSupportParameter.RANGE_START);

		
		//#CM717274
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do", param, "/progress.do");
	}

	//#CM717275
	/** 
	 * Clicking on the Search End Order No. button invokes this. 
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PS_EndOrderNo_Click(ActionEvent e) throws Exception
	{
		//#CM717276
		// Set the search condition in the Search screen of Order No. 
		ForwardParameters param = new ForwardParameters();
		//#CM717277
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM717278
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM717279
		// End Order No. 
		param.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, txt_EndOrderNo.getText());
		//#CM717280
		// Case/Piece flag for search
		param.setParameter(ListRetrievalOrdernoBusiness.CASE_PIECE_KEY, "");
		//#CM717281
		// "Search" flag 
		param.setParameter(ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM717282
		// for Order 
		param.setParameter(ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM717283
		// For the end 
		param.setParameter(ListRetrievalOrdernoBusiness.RANGE_KEY, RetrievalSupportParameter.RANGE_END);

		
		//#CM717284
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do", param, "/progress.do");
	}
	

	//#CM717285
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717286
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717287
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717288
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717289
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717290
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717291
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717292
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717293
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717294
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PassWord_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717295
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717296
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717297
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717298
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717299
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717300
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717301
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717302
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717303
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717304
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PS_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717305
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717306
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717307
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717308
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717309
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PS_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717310
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717311
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717312
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartOrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717313
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartOrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717314
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartOrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717315
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PS_StartOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717316
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717317
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717318
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717319
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndOrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717320
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndOrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717321
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndOrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717322
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PS_EndOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717323
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717324
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717325
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717326
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717327
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717328
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717329
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717330
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717331
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717332
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717333
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717334
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_RetrievalStart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717335
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheck_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717336
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheckClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717337
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717338
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717339
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717340
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717341
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717342
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717343
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717344
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717345
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717346
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717347
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDateT_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717348
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717349
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717350
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717351
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Allocate_Choice_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717352
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Allocate_Place_Hira_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717353
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Allocate_Place_Hira_Change(ActionEvent e) throws Exception
	{
	}

	//#CM717354
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Allocate_Place_Idm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717355
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Allocate_Place_Idm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM717356
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Allocate_Place_Asrs_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717357
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Allocate_Place_Asrs_Change(ActionEvent e) throws Exception
	{
	}

	//#CM717358
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Keppin_Choice_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717359
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Keppin_Chk_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717360
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Keppin_Chk_Change(ActionEvent e) throws Exception
	{
	}

	//#CM717361
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderSchedule_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717362
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderSchedule_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717363
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderSchedule_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717364
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderSchedule_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM717365
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderSchedule_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717366
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderSchedule_Change(ActionEvent e) throws Exception
	{
	}

	//#CM717367
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderSchedule_Click(ActionEvent e) throws Exception
	{
	}


}
//#CM717368
//end of class
