// $Id: RetrievalItemStartBusiness.java,v 1.2 2007/02/07 04:19:17 suresh Exp $

//#CM714979
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalitemstart;
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
import jp
	.co
	.daifuku
	.wms
	.retrieval
	.display
	.web
	.listbox
	.listretrievalconsignor
	.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp
	.co
	.daifuku
	.wms
	.retrieval
	.display
	.web
	.listbox
	.listretrievalplandate
	.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalItemStartSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM714980
/**
 * Designer : T.Yoshino<BR>
 * Maker : K.Mukai<BR>
 * <BR>
 * Allow this class to start Item Picking via listing work. <BR>
 * Set the contents entered via screen, and<BR>
 *  allow the schedule to set starting the Item Picking based on the parameter.<BR>
 * Receive the result from the schedule. Receive true if the process completed normally.<BR>
 * Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 * Output the message obtained from the schedule to the screen, as the schedule result. <BR>
 * Allow this screen to commit and roll back the transaction. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process by clicking "Display" button (<CODE>btn_View_Click()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *  Set the contents entered via the input area for the parameter, and allow the schedule to check the input condition based on the parameter. <BR>
 *  Receive the result from the schedule. Receive true if the process completed normally.<BR>
 *  Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 *  Output the message obtained from the schedule to the screen, as the schedule result. <BR>
 *  If the result is true, display the info of the Input area in the preset area.<BR>
 *  <BR>
 * 	 [Parameter]  *Mandatory Input<BR>
 * 	<BR>
 * 	<DIR>
 * 		Worker Code* <BR>
 * 		 Password * <BR>
 * 		Consignor Code* <BR>
 * 		Planned Picking Date* <BR>
 * 		Item Code <BR>
 * 		 Case/Piece division * <BR>
 * 	</DIR>
 *  <BR>
 *  [Data for Output]  <BR>
 *  <BR>
 * 	<DIR>
 *  	Consignor Code <BR>
 *  	Consignor Name <BR>
 * 		Planned Picking Date <BR>
 *  	Item Code <BR>
 *  	Item Name <BR>
 * 		 Division  <BR>
 * 		 Total Picking qty  <BR>
 *  	Packed Qty per Case <BR>
 * 		Packed qty per bundle <BR>
 * 		Planned Work Case Qty <BR>
 * 		Planned Work Piece Qty <BR>
 *  	 Picking Location  <BR>
 *  	Expiry Date <BR>
 * 		Case ITF <BR>
 * 		Bundle ITF <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2. Process by clicking on "Start Picking" button (<CODE>btn_RetrievalStart_Click()</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *  Set the field items ticked in the checkbox of the preset area for the parameter, and <BR>
 *   allow the schedule to start the Picking based on the parameter.<BR>
 *  Receive the result from the schedule. Receive true if the process completed normally.<BR>
 *  Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 *  Output the message obtained from the schedule to the screen, as the schedule result. <BR>
 * 	<BR>
 *   [Parameter]  *Mandatory Input <BR>
 *   <BR>
 *   <DIR>
 *   	Worker Code* <BR>
 *   	 Password * <BR>
 *   	 Preset.Consignor Code * <BR>
 *   	 Preset.Planned Picking Date * <BR>
 *   	 Preset.Item Code * <BR>
 *   	 Division * <BR>
 * 		 Preset line No.  <BR>
 *   	Work No.* <BR>
 * 	 	Last update date/time* <BR>
 * 		Terminal No. <BR>
 *   	Print the Relocation Work report.  <BR>
 *   </DIR>
 *  <BR>
 *  [Data for Output]  <BR>
 *  <BR>
 * 	<DIR>
 *  	Consignor Code <BR>
 *  	Consignor Name <BR>
 * 		Planned Picking Date <BR>
 *  	Item Code <BR>
 *  	Item Name <BR>
 * 		 Case/Piece division  <BR>
 * 		 Total Picking qty  <BR>
 *  	Packed Qty per Case <BR>
 * 		Packed qty per bundle <BR>
 * 		Planned Work Case Qty <BR>
 * 		Planned Work Piece Qty <BR>
 *  	 Picking Location  <BR>
 *  	Expiry Date <BR>
 * 		Case ITF <BR>
 * 		Bundle ITF <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/25</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:17 $
 * @author  $Author: suresh $
 */
public class RetrievalItemStartBusiness extends RetrievalItemStart implements WMSConstants
{
	//#CM714981
	// Class fields --------------------------------------------------

	//#CM714982
	// Class variables -----------------------------------------------

	//#CM714983
	// Class method --------------------------------------------------

	//#CM714984
	// Constructors --------------------------------------------------

	//#CM714985
	// Public methods ------------------------------------------------

	//#CM714986
	/**
	 * Invoke this before invoking each control event.  <BR>
	 * <BR>
	 * <DIR>
	 *  Summary: Displays a dialog.  <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM714987
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM714988
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM714989
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}

		//#CM714990
		// Display a dialog. MSG-W0050=Do you start Picking? 
		btn_RetrievalStart.setBeforeConfirm("MSG-W0050");
		//#CM714991
		// Display a dialog. MSG-W0012=This clears the list input info. Do you confirm it? 
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		//#CM714992
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM714993
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Summary: shows the Initial Display. <BR>
	 * <BR>
	 * <DIR>
	 *    1. If only one Consignor exists, display the Consignor Code in a textbox. <BR>
	 *    2. Set the cursor on the Worker code. <BR>
	 *    3. Disable the buttons (Start Picking, Select All, Cancel All Selections, and Clear List) for preset. <BR>
	 * <BR>
	 *    Field item: name [Initial Value] <BR>
	 *    <DIR>
	 * 		Worker Code	 [None]  <BR>
	 * 		 Password 		 [None]  <BR>
	 * 		Consignor Code		 [If one corresponding data exists, set the Consignor Code in the Inventory Info table]  <BR>
	 * 		Planned Picking Date		 [None]  <BR>
	 * 		Item Code		 [None]  <BR>
	 * 		 Case/Piece division[All]  <BR>
	 * 		Print the Item Picking report. [ticked]  <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM714994
		// Disable to click a button. 
		//#CM714995
		// "Start Storage" button 
		btn_RetrievalStart.setEnabled(false);
		//#CM714996
		// Select All 
		btn_AllCheck.setEnabled(false);
		//#CM714997
		// Cancel All Selected 
		btn_AllCheckClear.setEnabled(false);
		//#CM714998
		// "Clear List" button 
		btn_ListClear.setEnabled(false);

		//#CM714999
		// Show the initial display. 
		setFirstDisp();
	}

	//#CM715000
	/**
	 * Returning from a popup window invokes this method.
	 * Override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE>. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM715001
		// Obtain the parameter selected in the listbox. 
		//#CM715002
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM715003
		// Planned Picking Date
		String startstorageplandate =
			param.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM715004
		// Item Code
		String itemcode = param.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);

		//#CM715005
		// Set a value if not empty. 
		//#CM715006
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM715007
		// Planned Picking Date
		if (!StringUtil.isBlank(startstorageplandate))
		{
			txt_RtrivlPlanDate.setDate(
				WmsFormatter.toDate(startstorageplandate));
		}
		//#CM715008
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
	}

	//#CM715009
	// Package methods -----------------------------------------------

	//#CM715010
	// Protected methods ---------------------------------------------

	//#CM715011
	// Private methods -----------------------------------------------

	//#CM715012
	/**
	 * Invoke this method to display/clear the initial display. 
	 * @throws Exception Report all exceptions.  
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM715013
			// Consignor Code
			txt_ConsignorCode.setText("");
			//#CM715014
			// Planned Picking Date
			txt_RtrivlPlanDate.setText("");
			//#CM715015
			// Item Code
			txt_ItemCode.setText("");
			//#CM715016
			// Case/Piece division 
			rdo_CpfAll.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);
			//#CM715017
			// Print the Item Picking report. 
			chk_CommonUse.setChecked(true);
			
			//#CM715018
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			WmsScheduler schedule = new RetrievalItemStartSCH();

			RetrievalSupportParameter param =
				(RetrievalSupportParameter) schedule.initFind(conn, null);
			//#CM715019
			// For data with only one Consignor code, display the initial display. 
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
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
				//#CM715020
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

	//#CM715021
	/**
	 * Set the value for the preset area. 
	 * @param Parameter object that contains info to be displayed in the preset area.  
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(Parameter[] listParams) throws Exception
	{
		//#CM715022
		// Delete all lines. 
		lst_RetrievalItemStart.clearRow();

		RetrievalSupportParameter param = (RetrievalSupportParameter) listParams[0];

		//#CM715023
		// Consignor Code (read-only) 
		txt_RConsignorCode.setText(param.getConsignorCode());
		//#CM715024
		// Consignor Name (read-only) 
		txt_RConsignorName.setText(param.getConsignorName());
		//#CM715025
		// Planned Picking Date (read-only) 
		txt_RRtrivlPlanDate.setDate(WmsFormatter.toDate(param.getRetrievalPlanDate()));

		RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) listParams;
		
		//#CM715026
		// Picking Location 
		String label_retrievallocation = DisplayText.getText("LBL-W0172");
		//#CM715027
		// Expiry Date
		String label_usebydate = DisplayText.getText("LBL-W0270");
		//#CM715028
		// Case ITF
		String label_caseitf = DisplayText.getText("LBL-W0010");
		//#CM715029
		// Bundle ITF
		String label_bundleitf = DisplayText.getText("LBL-W0006");
		
		for (int i = 0; i < viewParam.length; i++)
		{
			//#CM715030
			// Add a line. 
			lst_RetrievalItemStart.setCurrentRow(i + 1);
			lst_RetrievalItemStart.addRow();

			lst_RetrievalItemStart.setValue(2, viewParam[i].getItemCode());
			lst_RetrievalItemStart.setValue(3, viewParam[i].getCasePieceflgName());

			//#CM715031
			// Translate the data type from the numeric type into the string type in the comma format. 
			lst_RetrievalItemStart.setValue(
				4,
				WmsFormatter.getNumFormat(viewParam[i].getTotalPlanQty()));
			lst_RetrievalItemStart.setValue(
				5,
				WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));
			lst_RetrievalItemStart.setValue(
				6,
				WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
			lst_RetrievalItemStart.setValue(7, viewParam[i].getRetrievalLocation());
			lst_RetrievalItemStart.setValue(8, viewParam[i].getUseByDate());
			lst_RetrievalItemStart.setValue(9, viewParam[i].getITF());

			lst_RetrievalItemStart.setValue(10, viewParam[i].getItemName());
			lst_RetrievalItemStart.setValue(
				11,
				WmsFormatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
			lst_RetrievalItemStart.setValue(
				12,
				WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
			lst_RetrievalItemStart.setValue(13, viewParam[i].getBundleITF());

			//#CM715032
			// Connect the Hidden field items. 
			String hidden = createHiddenList(viewParam[i]);
			//#CM715033
			// Set the Hidden field item. 
			lst_RetrievalItemStart.setValue(0, hidden);

			//#CM715034
			// Set the tool tip. 
			ToolTipHelper toolTip = new ToolTipHelper();
			//#CM715035
			// Picking Location 
			toolTip.add(label_retrievallocation, viewParam[i].getRetrievalLocation());
			//#CM715036
			// Expiry Date
			toolTip.add(label_usebydate, viewParam[i].getUseByDate());
			//#CM715037
			// Case ITF
			toolTip.add(label_caseitf, viewParam[i].getITF());
			//#CM715038
			// Bundle ITF
			toolTip.add(label_bundleitf, viewParam[i].getBundleITF());
			//#CM715039
			// Set the ToolTip for the current line. 
			lst_RetrievalItemStart.setToolTip(
				lst_RetrievalItemStart.getCurrentRow(),
				toolTip.getText());
		}

		//#CM715040
		// Enable to click a button. 
		//#CM715041
		// "Start Storage" button 
		btn_RetrievalStart.setEnabled(true);
		//#CM715042
		// Select All 
		btn_AllCheck.setEnabled(true);
		//#CM715043
		// Cancel All Selected 
		btn_AllCheckClear.setEnabled(true);
		//#CM715044
		// "Clear List" button 
		btn_ListClear.setEnabled(true);
	}

	//#CM715045
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
		String hidden = null;

		//#CM715046
		// Generate a string of Hidden field item.  
		ArrayList hiddenList = new ArrayList();

		hiddenList.add(0, viewParam.getJobNo());
		hiddenList.add(1, WmsFormatter.getTimeStampString(viewParam.getLastUpdateDate()));

		hidden = CollectionUtils.getConnectedString(hiddenList);

		return hidden;
	}

	//#CM715047
	// Event handler methods -----------------------------------------
	//#CM715048
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715049
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715050
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715051
	/** 
	 * Clicking on the Menu button invokes this. <BR>
	 * <BR>
	 * Summary: Shifts to the Menu screen. <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM715052
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715053
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715054
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715055
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715056
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM715057
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715058
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715059
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715060
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715061
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM715062
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715063
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715064
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715065
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715066
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM715067
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715068
	/** 
	 * Clicking on the Search Consignor Code button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for a parameter and displays the Consignor list listbox using the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM715069
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM715070
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM715071
		// "Search" flag 
		param.setParameter(
			ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY,
			RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM715072
		// Work status (work status data with "Standby") 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		param.setParameter(ListRetrievalConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		//#CM715073
		// For Item 
		param.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM715074
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do",
			param,
			"/progress.do");
	}

	//#CM715075
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715076
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715077
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715078
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715079
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715080
	/** 
	 * Clicking on the Search Planned Picking Date button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Planned Picking Date using this search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Planned Picking Date <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchRtrivlPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM715081
		// Set the search condition in the Search screen of Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM715082
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM715083
		// Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM715084
		// "Search" flag 
		param.setParameter(
			ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY,
			RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM715085
		// Work status (work status data with "Standby") 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		param.setParameter(ListRetrievalPlanDateBusiness.WORKSTATUSRETRIEVALPLANDATE_KEY, search);
		//#CM715086
		// For Item 
		param.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM715087
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do",
			param,
			"/progress.do");
	}

	//#CM715088
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715089
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715090
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715091
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715092
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM715093
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715094
	/** 
	 * Clicking on the Search Item Code button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for a parameter and displays the item list listbox using the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Planned Picking Date <BR>
	 *       Item Code <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM715095
		// Set the search condition in the Search screen of Item. 
		ForwardParameters param = new ForwardParameters();
		//#CM715096
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM715097
		// Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM715098
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM715099
		// "Search" flag 
		param.setParameter(
			ListRetrievalItemBusiness.SEARCHITEM_KEY,
			RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM715100
		// Work status (work status data with "Standby") 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		param.setParameter(ListRetrievalItemBusiness.WORKSTATUSITEM_KEY, search);
		//#CM715101
		// For Item 
		param.setParameter(ListRetrievalItemBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM715102
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalitem/ListRetrievalItem.do",
			param,
			"/progress.do");
	}

	//#CM715103
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715104
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715105
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM715106
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715107
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM715108
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715109
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM715110
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715111
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM715112
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemPickingList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715113
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715114
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Change(ActionEvent e) throws Exception
	{
	}

	//#CM715115
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715116
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
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM715117
			// Clear the preset area when input error occurs. 
			btn_ListClear_Click(e);

			//#CM715118
			// Check for input. 
			txt_WorkerCode.validate();
			txt_Password.validate();
			txt_ConsignorCode.validate();
			txt_RtrivlPlanDate.validate();
			//#CM715119
			// Pattern matching characters 
			txt_ItemCode.validate(false);

			//#CM715120
			// Set for the schedule parameter: 
			RetrievalSupportParameter param = new RetrievalSupportParameter();
			//#CM715121
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM715122
			// Password 
			param.setPassword(txt_Password.getText());
			//#CM715123
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM715124
			// Planned Picking Date
			param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
			//#CM715125
			// Item Code
			param.setItemCode(txt_ItemCode.getText());

			//#CM715126
			// Case/Piece division 
			if (rdo_CpfAll.getChecked())
			{
				//#CM715127
				// All 
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
			}
			else if (rdo_CpfCase.getChecked())
			{
				//#CM715128
				// Case 
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_CpfPiece.getChecked())
			{
				//#CM715129
				// Piece 
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
			}
			else if (rdo_CpfAppointOff.getChecked())
			{
				//#CM715130
				// None 
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
			}

			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalItemStartSCH();

			RetrievalSupportParameter[] viewParam =
				(RetrievalSupportParameter[]) schedule.query(conn, param);

			if (viewParam == null || viewParam.length == 0)
			{
				//#CM715131
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());

				return;
			}

			//#CM715132
			// Set the listcell. 
			setList(viewParam);

			//#CM715133
			// Maintain the search conditions in ViewState. (Use these conditions to search for data again after Delete Process) 
			//#CM715134
			// Consignor Code
			this.viewState.setString(
				ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
				param.getConsignorCode());
			//#CM715135
			// Planned Picking Date
			this.viewState.setString(
				ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY,
				param.getRetrievalPlanDate());
			//#CM715136
			// Item Code
			this.viewState.setString(ListRetrievalItemBusiness.ITEMCODE_KEY, param.getItemCode());
			//#CM715137
			// Aggregate the display.
			this.viewState.setString("CASEPIECEFLG", param.getCasePieceflg());

			//#CM715138
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
				//#CM715139
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

	//#CM715140
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715141
	/** 
	 * Clicking on the Clear button invokes this. <BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 * <DIR>
	 *    1. Return the field item in the input area to the initial value. <BR>
	 *    <DIR>
	 *  	- For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method. <BR>
	 *    </DIR>
	 *    2. Set the cursor on the Worker code. <BR>
	 *    3. Maintain the contents of preset area. <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM715142
		// Execute the Clear process. 
		setFirstDisp();
	}

	//#CM715143
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_RetrievalStart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715144
	/** 
	 * Clicking on the Start Picking button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the Storage using the info in the Preset area.<BR>
	 * <BR>
	 * <DIR>
	 *	  1. Set the cursor on the Worker code. <BR>
	 *    2.Display the dialog box to allow to confirm to start Picking or not. <BR>
	 *    <DIR>
	 *      Do you start it? <BR>
	 * 		 [Dialog for confirming: Cancel] <BR>
	 *			<DIR>
	 *				 Disable to do anything.
	 *			</DIR>
	 * 		 [Dialog for confirming: OK] <BR>
	 *			<DIR>
	 *				1. Start the schedule. <BR>
	 *				<DIR>
	 *   				 [Parameter] <BR>
	 * 					<DIR>
	 * 						Worker Code <BR>
	 * 						 Password  <BR>
	 * 						 Storage Instruction Print division  <BR>
	 * 						 Preset.Consignor Code  <BR>
	 * 						 Preset.Planned Picking Date  <BR>
	 *						 Preset.Item Code  <BR>
	 *						 Preset.Work No.  <BR>
	 *                      Preset.Last update date/time  <BR>
	 * 						 Preset.Preset line No.  <BR>
	 * 						Terminal No. <BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2. Clear the information in the Preset if the schedule result is true.<BR>
	 *				3. Display the preset info again in the preset area. <BR>
	 *              4.If the count of the Preset data is 0, disable the Start Picking button, Select All button, the Cancel All Selection button, Clear List button.  <BR>
	 *              5.If the count of the Preset data is 0, clear Consignor Code, Consignor Name, and Planned Picking Date of the preset.  <BR>
	 *    			 Output the message obtained from the schedule to the screen, if false. <BR>
	 *			</DIR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_RetrievalStart_Click(ActionEvent e) throws Exception
	{
		//#CM715145
		// Check for mandatory input. 
		txt_WorkerCode.validate();
		txt_Password.validate();

		Connection conn = null;

		try
		{
			//#CM715146
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM715147
			// Check the elements of the preset, and 
			//#CM715148
			// set values in the array of the lines only to be updated. 
			Vector vecParam = new Vector(lst_RetrievalItemStart.getMaxRows());
			for (int i = 1; i < lst_RetrievalItemStart.getMaxRows(); i++)
			{
				//#CM715149
				// Obtain the line to be operated. 
				lst_RetrievalItemStart.setCurrentRow(i);
				//#CM715150
				// Disable to set a not-selected line for parameter. 
				if (!lst_RetrievalItemStart.getChecked(1))
				{
					continue;
				}

				RetrievalSupportParameter param = new RetrievalSupportParameter();

				//#CM715151
				// Worker Code
				param.setWorkerCode(txt_WorkerCode.getText());
				//#CM715152
				// Password 
				param.setPassword(txt_Password.getText());
				//#CM715153
				// Set whether to print the Item Picking report. 
				param.setRetrievalListFlg(chk_CommonUse.getChecked());

				//#CM715154
				// Set Search Condition (Consignor, Planned Picking Date, Item Code, and Case/Piece division) for re-display
				param.setConsignorCode(
					this.getViewState().getString(
						ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY));
				param.setRetrievalPlanDate(
					this.getViewState().getString(
						ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY));
				param.setItemCode(
					this.getViewState().getString(ListRetrievalItemBusiness.ITEMCODE_KEY));
				param.setCasePieceflg(this.getViewState().getString("CASEPIECEFLG"));

				//#CM715155
				// Set the hidden field item. (Work No. (0), Last update date/time (1)) 
				String hidden = lst_RetrievalItemStart.getValue(0);
				param.setJobNo(CollectionUtils.getString(0, hidden));
				//#CM715156
				// Last update date/time
				//#CM715157
				// Translate the data type from String type (YYYYMMDDHHMMSS) into Date type. 
				param.setLastUpdateDate(
					WmsFormatter.getTimeStampDate(CollectionUtils.getString(1, hidden)));

				//#CM715158
				// Terminal No.
				UserInfoHandler userHandler =
					new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());

				//#CM715159
				// Preset line No. 
				param.setRowNo(i);

				vecParam.addElement(param);
			}

			//#CM715160
			// Close the process if no option checkbox is ticked. 
			if (vecParam.size() <= 0)
			{
				//#CM715161
				// 6023028=Please select the target data. 
				message.setMsgResourceKey("6023028");
				return;
			}

			//#CM715162
			// Declare the parameter. 
			RetrievalSupportParameter[] paramArray = new RetrievalSupportParameter[vecParam.size()];
			//#CM715163
			// Copy the value to the parameter. 
			vecParam.copyInto(paramArray);

			//#CM715164
			// Generate the instance of schedule class. 
			WmsScheduler schedule = new RetrievalItemStartSCH();

			//#CM715165
			// Start the schedule. 
			RetrievalSupportParameter[] viewParam =
				(RetrievalSupportParameter[]) schedule.startSCHgetParams(conn, paramArray);

			//#CM715166
			// If the returned data is null, determin that the update failed. 
			//#CM715167
			// Roll-back and set a message. (with the preset area that keeps displaying the former data) 
			if (viewParam == null)
			{
				//#CM715168
				// Roll-back 
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			//#CM715169
			// Update is successful if the number of elements of the returned data is 0 or more. 
			//#CM715170
			// Commit the transaction and display the preset area again. 
			else if (viewParam.length >= 0)
			{
				//#CM715171
				// Commit 
				conn.commit();

				//#CM715172
				// When there is no data to be displayed 
				if (viewParam.length == 0)
				{
					//#CM715173
					// Clear data in the preset area. 
					btn_ListClear_Click(e);
				}
				else
				{
					//#CM715174
					// Display the data if obtained the display data when the schedule normally completed. 
					lst_RetrievalItemStart.clearRow();
					//#CM715175
					// Display the Preset area again. 
					setList(viewParam);
				}
				message.setMsgResourceKey(schedule.getMessage());
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
				//#CM715176
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

	//#CM715177
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheck_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715178
	/** 
	 * Clicking on the Select All button invokes this.  <BR>
	 * <BR>
	 * Summary: makes all field items of the preset info selected. <BR>
	 * <BR>
	 * <DIR>
	 *     1.Tick all options in the preset area: selected.  <BR>
	 *     2. Set the cursor on the Worker code.  <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_AllCheck_Click(ActionEvent e) throws Exception
	{
		//#CM715179
		// Place a check in option checkbox. 
		for (int i = 1; i < lst_RetrievalItemStart.getMaxRows(); i++)
		{
			//#CM715180
			// Select a line to work. 
			lst_RetrievalItemStart.setCurrentRow(i);
			lst_RetrievalItemStart.setChecked(1, true);
		}
	}

	//#CM715181
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheckClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715182
	/** 
	 * Clicking on the Cancel Select All button invokes this.  <BR>
	 * <BR>
	 * Summary: Clear all the checks placed in the selected field items in the preset area.<BR>
	 * <BR>
	 * <DIR>
	 *     1.Clear all checks placed in options in the preset area: not selected.  <BR>
	 *     2. Set the cursor on the Worker code.  <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_AllCheckClear_Click(ActionEvent e) throws Exception
	{
		//#CM715183
		// Clear check placed in the option checkbox. 
		for (int i = 1; i < lst_RetrievalItemStart.getMaxRows(); i++)
		{
			//#CM715184
			// Select a line to work. 
			lst_RetrievalItemStart.setCurrentRow(i);
			lst_RetrievalItemStart.setChecked(1, false);
		}
	}

	//#CM715185
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715186
	/** 
	 * Clicking on the Clear List button invokes this. <BR>
	 * <BR>
	 * Summary: Clears all info displayed in the preset area. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Display a dialog box to allow to confirm to clear the info in the preset area.<BR>
	 *    <DIR>
	 * 	    This clears the list input info. Do you confirm it? <BR>
	 * 		 [Dialog for confirming: Cancel] <BR>
	 *			<DIR>
	 *				 Disable to do anything.
	 *			</DIR>
	 * 		 [Dialog for confirming: OK] <BR>
	 *			<DIR>
	 *				1. Clear all the information displayed in the preset area. <BR>
	 *				2.Disable the Start Picking button, Select All button, Cancel All Selected button, and Clear List button. <BR>
	 *				3. Set the cursor on the Worker code. <BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		//#CM715187
		// Delete all lines. 
		lst_RetrievalItemStart.clearRow();

		//#CM715188
		// Disable to click a button. 
		//#CM715189
		// "Start Picking" button 
		btn_RetrievalStart.setEnabled(false);
		//#CM715190
		// Select All 
		btn_AllCheck.setEnabled(false);
		//#CM715191
		// Cancel All Selected 
		btn_AllCheckClear.setEnabled(false);
		//#CM715192
		// "Clear List" button 
		btn_ListClear.setEnabled(false);

		//#CM715193
		// Clear the Consignor Code, Consignor Name, and Planned Picking Date in the preset area.
		txt_RConsignorCode.setText("");
		txt_RConsignorName.setText("");
		txt_RRtrivlPlanDate.setText("");
	}

	//#CM715194
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715195
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715196
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715197
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715198
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM715199
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715200
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715201
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715202
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM715203
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDateT_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715204
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715205
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RRtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715206
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RRtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715207
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemStart_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715208
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemStart_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715209
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemStart_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM715210
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemStart_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM715211
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemStart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715212
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemStart_Change(ActionEvent e) throws Exception
	{
	}

	//#CM715213
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemStart_Click(ActionEvent e) throws Exception
	{
	}
	//#CM715214
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_StartSet_Click(ActionEvent e) throws Exception
	{
	}


}
//#CM715215
//end of class
