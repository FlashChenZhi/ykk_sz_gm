// $Id: RetrievalItemScheduleBusiness.java,v 1.2 2007/02/07 04:19:16 suresh Exp $

//#CM714707
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalitemschedule;
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
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalItemScheduleSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;


//#CM714708
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * Allow this class of screen to start Item Picking. <BR>
 * Pass the parameter to the schedule to set Item Picking. <BR>
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
 *   Start Item Code  <BR>
 *   End Item Code  <BR>
 *   Case/Piece division 
 *   <BR>
 *   [Data for Output]  <BR>
 *   <BR>
 *   Consignor Code <BR>
 *   Consignor Name <BR>
 *   Planned Picking Date <BR>
 *   Item Code <BR>
 *   Item Name <BR>
 *   Case/Piece division name (for display)  <BR>
 *   Picking Location No  <BR>
 * </DIR>
 * <BR>
 * 2. Process by clicking on "Start Picking" button (<CODE>btn_RetrievalStart_Click()</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *   Set the contents input via preset area for the parameter, and allow the schedule to set the Picking based on the parameter.<BR>
 *   Receive the result from the schedule, and receive the data to be displayed if the process completed normally, or<BR>
 *   receive null when the schedule failed to complete due to condition error or other causes. <BR>
 *   Output the message obtained from the schedule to the screen, as the schedule result. <BR>
 *   <BR>
 *   [Parameter]  *Mandatory Input <BR>
 *   <BR>
 *   Worker Code* <BR>
 *   Password * <BR>
 *   Consignor Code* <BR>
 *   Planned Picking Date* <BR>
 *   Start Item Code  <BR>
 *   End Item Code  <BR>
 *   Selected Consignor Code  <BR>
 *   Selected Planned Picking Date  <BR>
 *   Selected Item Code  <BR>
 *   Selected Case/Piece division  <BR>
 *   Allocation target, storage place of the preset <BR>
 *   Shortage evidence division of the preset area  <BR>
 *   Item Code of the preset area.<BR>
 *   Preset line No. <BR>
 *   Picking Location of the preset area.<BR>
 *   Terminal No.<BR>
 *   <BR>
 *   [Data for Output]  <BR>
 *   <BR>
 *   Consignor Code <BR>
 *   Consignor Name <BR>
 *   Planned Picking Date <BR>
 *   Item Code <BR>
 *   Item Name <BR>
 *   Case/Piece division name (for display)  <BR>
 *   Picking Location No  <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/28</TD><TD>C.Kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:16 $
 * @author  $Author: suresh $
 */
public class RetrievalItemScheduleBusiness extends RetrievalItemSchedule implements WMSConstants
{
	//#CM714709
	// Class fields --------------------------------------------------
	//#CM714710
	// for ViewState 
	//#CM714711
	// Consignor Code
	private static final String VS_CONSIGNOR = "CONSIGNOR_CODE";
	//#CM714712
	// Planned Picking Date
	private static final String VS_PLANDATE = "PLAN_DATE";
	//#CM714713
	// Start Item Code 
	private static final String VS_START_ITEMCODE = "START_ITEM_CODE";
	//#CM714714
	// End Item Code 
	private static final String VS_END_ITEMCODE = "END_ITEM_CODE";
	//#CM714715
	// Case/Piece division 
	private static final String VS_CPFLAG = "VS_CPFLAG";

	//#CM714716
	// Class variables -----------------------------------------------
	//#CM714717
	// Hidden Parameter 
	//#CM714718
	// Case/Piece division 
	private static final int HDN_CASEPIECEFLAG = 0;

	//#CM714719
	// Class method --------------------------------------------------

	//#CM714720
	// Constructors --------------------------------------------------

	//#CM714721
	// Public methods ------------------------------------------------
	//#CM714722
	/**
	 * Initialize the screen. 
	 * <BR>
	 * Field item: name [Initial Value]  <BR>
	 * <DIR>
	 * Worker Code [None] <BR>
	 * Password [None] <BR>
	 * Consignor Code [If there is only one Consignor code that corresponds to the condition, show the Initial Display.]  <BR>
	 * Start Picking Date [None] <BR>
	 * End Picking Date [None] <BR>
	 * Item Code [None] <BR>
	 * Case/Piece division[All] <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM714723
			// Disable to click a button. 
			setBtnTrueFalse(false);
			
			//#CM714724
			// Set Initial Value of Radio button. 
			rdo_CpfAll.setChecked(true);

			//#CM714725
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalItemScheduleSCH();
			RetrievalSupportParameter param = (RetrievalSupportParameter) schedule.initFind(conn, null);

			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				//#CM714726
				// For data with only one Consignor code, display the initial display. 
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
			
			//#CM714727
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
				//#CM714728
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

	//#CM714729
	/**
	 * Invoke this before invoking each control event. <BR>
	 * <DIR>
	 *  Summary: Displays a dialog.  <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM714730
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM714731
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM714732
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
		//#CM714733
		// Dialog before clicking on Start Picking button to confirm: MSG-W0019= Do you start it? 
		btn_RetrievalStart.setBeforeConfirm("MSG-W0019");

		//#CM714734
		// Clicking the Clear List button displays a dialog box to allow user to confirm: MSG-W0012= Input info in the list is cleared. Do you confirm it? 
		btn_ListClear.setBeforeConfirm("MSG-W0012");
		
		//#CM714735
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM714736
	/**
	 * Returning from a popup window invokes this method.
	 * Override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE>. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM714737
		// Obtain the parameter selected in the listbox. 
		//#CM714738
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM714739
		// Planned Picking Date
		String retPlan = param.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM714740
		// Start Item Code 
		String startItemCode = param.getParameter(ListRetrievalItemBusiness.STARTITEMCODE_KEY);
		//#CM714741
		// End Item Code 
		String endItemCode = param.getParameter(ListRetrievalItemBusiness.ENDITEMCODE_KEY);

		//#CM714742
		// Set a value if not empty. 
		//#CM714743
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM714744
		// Planned Picking Date
		if (!StringUtil.isBlank(retPlan))
		{
			txt_RtrivlPlanDate.setDate(WmsFormatter.toDate(retPlan));
		}
		//#CM714745
		// Start Order No. 
		if (!StringUtil.isBlank(startItemCode))
		{
			txt_StartItemCode.setText(startItemCode);
		}
		//#CM714746
		// End Order No. 
		if (!StringUtil.isBlank(endItemCode))
		{
			txt_EndItemCode.setText(endItemCode);
		}
	}

	//#CM714747
	// Package methods -----------------------------------------------

	//#CM714748
	// Protected methods ---------------------------------------------

	//#CM714749
	// Private methods -----------------------------------------------
	//#CM714750
	/**
	 * Set the value for the preset area. 
	 * @param Parameter object that contains info to be displayed in the preset area.  
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(Parameter[] listParams) throws Exception
	{
		//#CM714751
		// Delete all lines. 
		lst_RetrievalItemSchedule.clearRow();
		if (listParams == null || listParams.length <= 0)
		{
			return ;
		}
		RetrievalSupportParameter param = (RetrievalSupportParameter) listParams[0];

		//#CM714752
		// Consignor Code (read-only) 
		txt_JavaSetConsignorCode.setText(param.getConsignorCode());
		//#CM714753
		// Consignor Name (read-only) 
		txt_JavaSetConsignorName.setText(param.getConsignorName());
		//#CM714754
		// Planned Picking Date (read-only) 
		txt_JavaSetPlanDate.setDate(WmsFormatter.toDate(param.getRetrievalPlanDate()));

		RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) listParams;
		
		//#CM714755
		// Item Name
		String label_itemname = DisplayText.getText("LBL-W0103");
		//#CM714756
		// Picking Location 
		String label_retrievallocation = DisplayText.getText("LBL-W0172");

		for (int i = 0; i < viewParam.length; i++)
		{
			//#CM714757
			// Add a line. 
			lst_RetrievalItemSchedule.setCurrentRow(i + 1);
			lst_RetrievalItemSchedule.addRow();
			
			lst_RetrievalItemSchedule.setValue(2, viewParam[i].getItemCode());
			lst_RetrievalItemSchedule.setValue(3, viewParam[i].getCasePieceflgName());
			lst_RetrievalItemSchedule.setValue(4, viewParam[i].getRetrievalLocation());
			lst_RetrievalItemSchedule.setValue(5, viewParam[i].getItemName());
						
			//#CM714758
			// Set the Hidden field item. 
			lst_RetrievalItemSchedule.setValue(0, createHiddenList(viewParam[i]));

			//#CM714759
			// Set the tool tip. 
			ToolTipHelper toolTip = new ToolTipHelper();
			//#CM714760
			// Item Name
			toolTip.add(label_itemname, viewParam[i].getItemName());
			//#CM714761
			// Picking Location 
			toolTip.add(label_retrievallocation, viewParam[i].getRetrievalLocation());

			//#CM714762
			// Set the TOOL TIP in the current line. 
			lst_RetrievalItemSchedule.setToolTip(lst_RetrievalItemSchedule.getCurrentRow(), toolTip.getText());
		}

		//#CM714763
		// Enable to click a button. 
		setBtnTrueFalse(true);
	}

	//#CM714764
	/**
	 * Allow this method to set acceptability to compile buttons. <BR>
	 * <BR>
	 * Summary: Sets to determine whether to compile buttons after receiving false or true. <BR>
	 * @param  arg status(false or true)
	 * @return None 
	 */
	private void setBtnTrueFalse(boolean arg)
	{
		//#CM714765
		// Disable to click a button. 
		//#CM714766
		// "Start Picking" button 
		btn_RetrievalStart.setEnabled(arg);
		//#CM714767
		// "Select All" button 
		btn_AllCheck.setEnabled(arg);
		//#CM714768
		// Cancel All Selected 
		btn_AllCheckClear.setEnabled(arg);
		//#CM714769
		// "Clear List" button 
		btn_ListClear.setEnabled(arg);
	}

	//#CM714770
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
		//#CM714771
		// Generate a string of hidden field item.  
		ArrayList hiddenList = new ArrayList();
		//#CM714772
		// Hidden field item 
		//#CM714773
		// Case/Piece division
		hiddenList.add(HDN_CASEPIECEFLAG, viewParam.getCasePieceFlagL());

		return CollectionUtils.getConnectedString(hiddenList);
	}

	//#CM714774
	/**
	 * Allow this method to obtain the Consignor code from the schedule. <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule. <BR>
	 * <DIR>
	 *   1. If there is only one Consignor Code, return the corresponding Consignor. Otherwise, return null.  <BR>
	 * <DIR>
	 * @throws Exception Report all exceptions. 
	 * @return Consignor Code
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM714775
			// Obtain connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM714776
			// Declare the parameter. 
			RetrievalSupportParameter param = new RetrievalSupportParameter();

			//#CM714777
			// Obtain the Consignor code from the schedule. 
			WmsScheduler schedule = new RetrievalItemScheduleSCH();
			//#CM714778
			// Start the schedule. 
			param = (RetrievalSupportParameter) schedule.initFind(conn, param);

			//#CM714779
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
				//#CM714780
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

	//#CM714781
	/**
	 * Allow this method to obtain the Area No. from the selected storage place for allocation.<BR>
	 * 
	 * @throws Exception Report all exceptions. 
	 * @return Area No.
	 */
	private String[] getAllocationArea() throws Exception
	{
		Vector tempVec = new Vector();
		//#CM714782
		// Flat Storage  target 
		if (chk_Allocate_Place_Hira.getChecked())
		{
			tempVec.addElement(Integer.toString(RetrievalSupportParameter.SYSTEM_DISC_KEY_FLOOR));
		}
		//#CM714783
		// IDM target 
		if (chk_Allocate_Place_Idm.getChecked())
		{
			tempVec.addElement(Integer.toString(RetrievalSupportParameter.SYSTEM_DISC_KEY_IDM));
		}
		//#CM714784
		// Target the Automated Warehouse.  
		if (chk_Allocate_Place_Asrs.getChecked())
		{
			tempVec.addElement(Integer.toString(RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS));
		}
		
		String[] allocationArea = new String[tempVec.size()];
		tempVec.copyInto(allocationArea);
		
		return allocationArea;

	}

	//#CM714785
	// Event handler methods -----------------------------------------
	//#CM714786
	/** 
	 * Clicking on the To Menu button invokes this. <BR>
	 * Summary: Shifts to the Menu screen. 
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM714787
	/** 
	 * Clicking on the Search Consignor button invokes this. 
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PS_ConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM714788
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM714789
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM714790
		// "Search" flag: Plan Info 
		param.setParameter(ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM714791
		// For Item 
		param.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM714792
		// Work Status: 
		String[] search = new String[4];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(RetrievalSupportParameter.STATUS_FLAG_STARTED);
		search[2] = new String(RetrievalSupportParameter.STATUS_FLAG_WORKING);
		search[3] = new String(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		param.setParameter(ListRetrievalConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		//#CM714793
		// Schedule flag 
		String[] schFlag = new String[3];
		schFlag[0] = RetrievalSupportParameter.SCHEDULE_UNSTART;
		schFlag[1] = RetrievalSupportParameter.SCHEDULE_CASE_COMPLETION;
		schFlag[2] = RetrievalSupportParameter.SCHEDULE_PIECE_COMPLETION;
		param.setParameter(ListRetrievalConsignorBusiness.SCHEDULE_FLAG_KEY, schFlag);
		
		//#CM714794
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do", param, "/progress.do");
	}

	//#CM714795
	/** 
	 * Clicking on the Search Planned Picking Date button invokes this. 
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PS_RetrievalPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM714796
		// Set the search condition in the Search screen of Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM714797
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM714798
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM714799
		// "Search" flag 
		param.setParameter(ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM714800
		// For Item 
		param.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM714801
		// Work status (work status data with "Standby") 
		String[] search = new String[4];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(RetrievalSupportParameter.STATUS_FLAG_STARTED);
		search[2] = new String(RetrievalSupportParameter.STATUS_FLAG_WORKING);
		search[3] = new String(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		param.setParameter(ListRetrievalPlanDateBusiness.WORKSTATUSRETRIEVALPLANDATE_KEY, search);
		//#CM714802
		// Schedule flag 
		String[] schFlag = new String[3];
		schFlag[0] = RetrievalSupportParameter.SCHEDULE_UNSTART;
		schFlag[1] = RetrievalSupportParameter.SCHEDULE_CASE_COMPLETION;
		schFlag[2] = RetrievalSupportParameter.SCHEDULE_PIECE_COMPLETION;
		param.setParameter(ListRetrievalPlanDateBusiness.SCHEDULE_FLAG_KEY, schFlag);
		
		//#CM714803
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", param, "/progress.do");
	}

	//#CM714804
	/** 
	 * Clicking on the Search Start Item Code button invokes this. 
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PS_StartItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM714805
		// Set the search condition in the Search screen of Item Code. 
		ForwardParameters param = new ForwardParameters();
		//#CM714806
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM714807
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM714808
		// Start Item Code 
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_StartItemCode.getText());
		//#CM714809
		// "Search" flag 
		param.setParameter(ListRetrievalItemBusiness.SEARCHITEM_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM714810
		// For Item 
		param.setParameter(ListRetrievalItemBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM714811
		// For starting 
		param.setParameter(ListRetrievalItemBusiness.RANGE_KEY, RetrievalSupportParameter.RANGE_START);
		//#CM714812
		// Work status (work status data with "Standby") 
		String[] search = new String[4];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(RetrievalSupportParameter.STATUS_FLAG_STARTED);
		search[2] = new String(RetrievalSupportParameter.STATUS_FLAG_WORKING);
		search[3] = new String(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		param.setParameter(ListRetrievalItemBusiness.WORKSTATUSITEM_KEY, search);
		//#CM714813
		// Schedule flag 
		String[] schFlag = new String[3];
		schFlag[0] = RetrievalSupportParameter.SCHEDULE_UNSTART;
		schFlag[1] = RetrievalSupportParameter.SCHEDULE_CASE_COMPLETION;
		schFlag[2] = RetrievalSupportParameter.SCHEDULE_PIECE_COMPLETION;
		param.setParameter(ListRetrievalItemBusiness.SCHEDULE_FLAG_KEY, schFlag);
		
		//#CM714814
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalitem/ListRetrievalItem.do", param, "/progress.do");
	}

	//#CM714815
	/** 
	 * Clicking on "Search End Item Code" button invokes this.
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PS_EndItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM714816
		// Set the search condition in the Search screen of Item Code. 
		ForwardParameters param = new ForwardParameters();
		//#CM714817
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM714818
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM714819
		// End Item Code 
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_EndItemCode.getText());
		//#CM714820
		// "Search" flag 
		param.setParameter(ListRetrievalItemBusiness.SEARCHITEM_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM714821
		// For Item 
		param.setParameter(ListRetrievalItemBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM714822
		// For the end 
		param.setParameter(ListRetrievalItemBusiness.RANGE_KEY, RetrievalSupportParameter.RANGE_END);
		//#CM714823
		// Work status (work status data with "Standby") 
		String[] search = new String[4];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(RetrievalSupportParameter.STATUS_FLAG_STARTED);
		search[2] = new String(RetrievalSupportParameter.STATUS_FLAG_WORKING);
		search[3] = new String(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		param.setParameter(ListRetrievalItemBusiness.WORKSTATUSITEM_KEY, search);
		//#CM714824
		// Schedule flag 
		String[] schFlag = new String[3];
		schFlag[0] = RetrievalSupportParameter.SCHEDULE_UNSTART;
		schFlag[1] = RetrievalSupportParameter.SCHEDULE_CASE_COMPLETION;
		schFlag[2] = RetrievalSupportParameter.SCHEDULE_PIECE_COMPLETION;
		param.setParameter(ListRetrievalItemBusiness.SCHEDULE_FLAG_KEY, schFlag);
		
		//#CM714825
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalitem/ListRetrievalItem.do", param, "/progress.do");
	}

	//#CM714826
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
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM714827
			// Clear the preset area when input error occurs. 
			btn_ListClear_Click(e);
	
			//#CM714828
			// Check for input. 
			txt_WorkerCode.validate();
			txt_PassWord.validate();
			txt_ConsignorCode.validate();
			txt_RtrivlPlanDate.validate();
			//#CM714829
			// Pattern matching characters 
			txt_StartItemCode.validate(false);
			txt_EndItemCode.validate(false);
	
			//#CM714830
			// Set the search conditions for the schedule parameter.
			RetrievalSupportParameter param = new RetrievalSupportParameter();
			//#CM714831
			// Worker Code-Password
			param.setWorkerCode(txt_WorkerCode.getText());
			param.setPassword(txt_PassWord.getText());
			//#CM714832
			// search conditions
			param.setConsignorCode(txt_ConsignorCode.getText());
			param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
			param.setStartItemCode(txt_StartItemCode.getText());
			param.setEndItemCode(txt_EndItemCode.getText());
			if (rdo_CpfAll.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
				this.getViewState().setString(VS_CPFLAG, RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
			}
			else if (rdo_CpfCase.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
				this.getViewState().setString(VS_CPFLAG, RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_CpfPiece.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
				this.getViewState().setString(VS_CPFLAG, RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
			}
			else if (rdo_CpfAppointOff.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
				this.getViewState().setString(VS_CPFLAG, RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
			}

			//#CM714833
			// Maintain the search conditions in ViewState. 
			this.getViewState().setString(VS_CONSIGNOR, txt_ConsignorCode.getText());
			this.getViewState().setString(VS_PLANDATE, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));	
			this.getViewState().setString(VS_START_ITEMCODE, txt_StartItemCode.getText());
			this.getViewState().setString(VS_END_ITEMCODE, txt_EndItemCode.getText());
	
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	
			WmsScheduler schedule = new RetrievalItemScheduleSCH();
	
			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) schedule.query(conn, param);
	
			if (viewParam == null || viewParam.length == 0)
			{
				//#CM714834
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());
	
				return;
			}
	
			//#CM714835
			// Set the listcell. 
			setList(viewParam);
				
			//#CM714836
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
				//#CM714837
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

	//#CM714838
	/** 
	 * Clicking on the Clear button invokes this. <BR>
	 * 
	 * Summary: Clears the input field item. 
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM714839
		// Set the initial value for each input field. 
		//#CM714840
		// Radio button 
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);
		//#CM714841
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM714842
		// Planned Picking Date
		txt_RtrivlPlanDate.setText("");
		//#CM714843
		// Start Item Code 
		txt_StartItemCode.setText("");
		//#CM714844
		// End Item Code 
		txt_EndItemCode.setText("");

		//#CM714845
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM714846
	/** 
	 * Clicking on the Start Picking button invokes this. <BR>
	 * Set the field items ticked in the checkbox of the preset area for the parameter, and <BR>
	 *  allow the schedule to start the Picking based on the parameter.<BR>
	 * <BR>
	 * <DIR>
	 *   1. Check for input in the Input area. <BR>
	 *   2. Set the contents input via preset area for the parameter. <BR>
	 *   3. Start the schedule. <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_RetrievalStart_Click(ActionEvent e) throws Exception
	{
		//#CM714847
		// Check for input. 
		txt_WorkerCode.validate();
		txt_PassWord.validate();

		//#CM714848
		// If the allocation target is not ticked, trigger error. 
		if(	!chk_Allocate_Place_Hira.getChecked() && 
			!chk_Allocate_Place_Idm.getChecked() && 
			!chk_Allocate_Place_Asrs.getChecked())
		{
			//#CM714849
			// 6023485=Select allocation target, storage place 
			message.setMsgResourceKey("6023485");
			return;
		}
		
		//#CM714850
		// Obtain the maximum (max) number of lines. 
		int index = lst_RetrievalItemSchedule.getMaxRows();

		Connection conn = null;
		try
		{
			//#CM714851
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			Vector vecParam = new Vector(index);
			for (int i = 1; i < index; i++)
			{
				//#CM714852
				// Designate the line. 
				lst_RetrievalItemSchedule.setCurrentRow(i);
				//#CM714853
				// Disable to set a not-selected line for parameter. 
				if (!lst_RetrievalItemSchedule.getChecked(1))
				{
					continue;
				}
				//#CM714854
				// Set for the schedule parameter: 
				RetrievalSupportParameter param = new RetrievalSupportParameter();
				//#CM714855
				// --- Worker Code-Password ---
				param.setWorkerCode(txt_WorkerCode.getText());
				param.setPassword(txt_PassWord.getText());
				//#CM714856
				// --- "Search" condition for repeated search ---
				//#CM714857
				// (Consignor, Planned Picking Date, Item Code, and Case/Piece division) 
				param.setConsignorCode(this.getViewState().getString(VS_CONSIGNOR));
				param.setRetrievalPlanDate(this.getViewState().getString(VS_PLANDATE));
				param.setStartItemCode(this.getViewState().getString(VS_START_ITEMCODE));
				param.setEndItemCode(this.getViewState().getString(VS_END_ITEMCODE));
				param.setCasePieceflg(this.getViewState().getString(VS_CPFLAG));
	
				//#CM714858
				// --- Preset data ---
				//#CM714859
				// Allocation target, storage place 
				param.setAllocateField(getAllocationArea());
				//#CM714860
				// Shortage evidence division 
				param.setShortageFlag(chk_Keppin_Chk.getChecked());
				//#CM714861
				// Maintain the line No. 
				param.setRowNo(i);
				
				//#CM714862
				// Data in a listcell
				param.setItemCodeL(lst_RetrievalItemSchedule.getValue(2));
				param.setRetrievalLocation(lst_RetrievalItemSchedule.getValue(4));

				//#CM714863
				// Set the hidden field item.
				String hidden = lst_RetrievalItemSchedule.getValue(0);
				//#CM714864
				// Case/Piece division 
				param.setCasePieceFlagL(CollectionUtils.getString(HDN_CASEPIECEFLAG, hidden));

				//#CM714865
				// Terminal No.
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());

				vecParam.addElement(param);
			}
			if (vecParam.size() <= 0)
			{
				//#CM714866
				// 6023154=There is no data to update. 
				message.setMsgResourceKey("6023154");
				return;
			}

			RetrievalSupportParameter[] paramArray = new RetrievalSupportParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			WmsScheduler schedule = new RetrievalItemScheduleSCH();

			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[])schedule.startSCHgetParams(conn, paramArray);
			//#CM714867
			// Start the schedule. 
			if (viewParam != null)
			{
				//#CM714868
				// Commit the transaction. 
				conn.commit();
				//#CM714869
				// When there is no data to be displayed 
				if (viewParam.length == 0)
				{
					//#CM714870
					// Delete all the Line info. 
					lst_RetrievalItemSchedule.clearRow();
					//#CM714871
					// Clear the Consignor field in the Preset area. 
					txt_JavaSetConsignorCode.setText("");
					txt_JavaSetConsignorName.setText("");
					txt_JavaSetPlanDate.setText("");
					//#CM714872
					// Disable the buttons in the Preset area. 
					setBtnTrueFalse(false);
					//#CM714873
					// Set the cursor on the Worker code. 
					setFocus(txt_WorkerCode);
				}
				else
				{
					//#CM714874
					// Set the listcell. 
					setList(viewParam);
				}
				//#CM714875
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.rollback();
				//#CM714876
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
				//#CM714877
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

	//#CM714878
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
		//#CM714879
		// Place a check in option checkbox. 
		for (int i = 1; i < lst_RetrievalItemSchedule.getMaxRows(); i++)
		{
			//#CM714880
			// Select a line to work. 
			lst_RetrievalItemSchedule.setCurrentRow(i);
			lst_RetrievalItemSchedule.setChecked(1, true);
		}
		//#CM714881
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM714882
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
		//#CM714883
		// Clear check placed in the option checkbox. 
		for (int i = 1; i < lst_RetrievalItemSchedule.getMaxRows(); i++)
		{
			//#CM714884
			// Select a line to work. 
			lst_RetrievalItemSchedule.setCurrentRow(i);
			lst_RetrievalItemSchedule.setChecked(1, false);
		}
		//#CM714885
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM714886
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
		//#CM714887
		// Delete all the Line info. 
		lst_RetrievalItemSchedule.clearRow();
		//#CM714888
		// Clear the Consignor field in the Preset area. 
		txt_JavaSetConsignorCode.setText("");
		txt_JavaSetConsignorName.setText("");
		txt_JavaSetPlanDate.setText("");
		//#CM714889
		// Disable the buttons in the Preset area. 
		setBtnTrueFalse(false);

		//#CM714890
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM714891
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714892
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714893
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}

	//#CM714894
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714895
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714896
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714897
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714898
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714899
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714900
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PassWord_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714901
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714902
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714903
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714904
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714905
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714906
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714907
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714908
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714909
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714910
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PS_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714911
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714912
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714913
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714914
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714915
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PS_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714916
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714917
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714918
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714919
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714920
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714921
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PS_StartItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714922
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714923
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714924
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714925
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714926
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714927
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714928
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PS_EndItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714929
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714930
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714931
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM714932
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714933
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM714934
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714935
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM714936
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714937
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM714938
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714939
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714940
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_RetrievalStart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714941
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheck_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714942
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheckClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714943
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714944
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714945
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714946
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714947
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714948
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714949
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714950
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714951
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714952
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714953
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDateT_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714954
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714955
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714956
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714957
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Allocate_Choice_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714958
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Allocate_Place_Hira_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714959
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Allocate_Place_Hira_Change(ActionEvent e) throws Exception
	{
	}

	//#CM714960
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Allocate_Place_Idm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714961
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Allocate_Place_Idm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM714962
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Allocate_Place_Asrs_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714963
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Allocate_Place_Asrs_Change(ActionEvent e) throws Exception
	{
	}

	//#CM714964
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Keppin_Choice_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714965
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Keppin_Chk_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714966
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_Keppin_Chk_Change(ActionEvent e) throws Exception
	{
	}

	//#CM714967
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemSchedule_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714968
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemSchedule_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714969
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemSchedule_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714970
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemSchedule_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM714971
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemSchedule_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714972
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemSchedule_Change(ActionEvent e) throws Exception
	{
	}

	//#CM714973
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemSchedule_Click(ActionEvent e) throws Exception
	{
	}


}
//#CM714974
//end of class
