// $Id: RetrievalItemCompleteBusiness.java,v 1.3 2007/02/07 04:19:11 suresh Exp $

//#CM713546
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalitemcomplete;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.MessageResources;
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
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalItemCompleteSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM713547
/**
 * Designer : T.Yoshino<BR>
 * Maker : K.Mukai<BR>
 * <BR>
 * Allow this class to complete Item Picking. <BR>
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
 *	 [Parameter]  *Mandatory Input<BR>
 *	<BR>
 *	<DIR>
 *		Worker Code* <BR>
 *		 Password * <BR>
 *		Consignor Code* <BR>
 *		Planned Picking Date* <BR>
 *		Item Code <BR>
 *		 Case/Piece division * <BR>
 *		Input the initial value of Picking qty.  <BR>
 *	</DIR>
 *	<BR>
 *	 [Data for Output]  <BR>
 *	<BR>
 *	<DIR>
 *		Consignor Code <BR>
 *		Consignor Name <BR>
 *		Planned Picking Date <BR>
 *		Item Code <BR>
 *		Item Name <BR>
 *		 Division  <BR>
 *		 Total Picking qty  <BR>
 *		Packed Qty per Case <BR>
 *		Packed qty per bundle <BR>
 *		Planned Work Case Qty <BR>
 *		Planned Work Piece Qty <BR>
 *		 Picking Case Qty  <BR>
 *		 Picking Piece Qty  <BR>
 *		 Picking Location  <BR>
 *		Expiry Date <BR>
 *		Case ITF <BR>
 *		Bundle ITF <BR>
 *	</DIR>
 * </DIR>
 * <BR>
 * 2.Process by clicking on "Complete Picking" button (<CODE>btn_StorageStart_Click()</CODE> method)  <BR>
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
 *		Worker Code* <BR>
 *		 Password * <BR>
 *		Consignor Code* <BR>
 *		Planned Picking Date* <BR>
 *		 Preset.Item Code * <BR>
 *		 Preset.Division * <BR>
 *		 Preset. Picking Case Qty  <BR>
 *		 Preset.Picking Piece Qty  <BR>
 *		 Preset. Picking Location * <BR>
 *		 Preset.Expiry Date  <BR>
 *		 Preset.Case ITF  <BR>
 *		 Preset.Bundle ITF  <BR>
 *		Work No.* <BR>
 *		Last update date/time* <BR>
 *		Terminal No.* <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/25</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:11 $
 * @author  $Author: suresh $
 */
public class RetrievalItemCompleteBusiness extends RetrievalItemComplete implements WMSConstants
{
	//#CM713548
	/**
	 * Case/Piece division: All (for repeated search) 
	 */
	private final static String VK_CASEPIECE_ALL = "VK_CASEPIECE_ALL";
	//#CM713549
	/**
	 * Case/Piece division: Case (Repeated search) 
	 */
	private final static String VK_CASEPIECE_CASE = "VK_CASEPIECE_CASE";
	//#CM713550
	/**
	 * Case/Piece division: Piece (Repeated search) 
	 */
	private final static String VK_CASEPIECE_PIECE = "VK_CASEPIECE_PIECE";
	//#CM713551
	/**
	 * Case/Piece division: None (for repeated search) 
	 */
	private final static String VK_CASEPIECE_APPOINTOFF = "VK_STRGFLG";

	//#CM713552
	// Class fields --------------------------------------------------

	//#CM713553
	// Class variables -----------------------------------------------

	//#CM713554
	// Class method --------------------------------------------------

	//#CM713555
	// Constructors --------------------------------------------------

	//#CM713556
	// Public methods ------------------------------------------------

	//#CM713557
	/**
	 * Invoke this before invoking each control event. <BR>
	 * <BR>
	 * <DIR>
	 * 	 1. Set the initial value of the focus to Worker Code. <BR>
	 * 	 <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM713558
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM713559
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM713560
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}

		//#CM713561
		// MSG-W0020 = Do you complete it? 
		btn_RetrievalComplete.setBeforeConfirm("MSG-W0020");
		//#CM713562
		// MSG-W0012 = This clears the list input info. Do you confirm it? 
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		//#CM713563
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM713564
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Summary: shows the Initial Display. <BR>
	 * <BR>
	 * <DIR>
	 *    1. Initialize the input area. <BR>
	 *    2. Start the schedule.  <BR>
	 *    3.Disable the Complete Picking button, Clear Picking Qty button, and Clear List button.  <BR>
	 * </DIR>
	 * <BR>
	 * Field item [Initial Value]  <BR>
	 * <BR>
	 * <DIR>
	 *    Worker Code			 [None]  <BR>
	 *    Password 			 [None]  <BR>
	 *    Consignor Code			 [Search Value]  <BR>
	 *    Planned Picking Date			 [None]  <BR>
	 *    Item Code			 [None]  <BR>
	 *    Case/Piece division 		 [All]  <BR>
	 *    Input the initial value of Picking qty. 	 [Not Ticked]  <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM713565
		// Disable to click a button. 
		//#CM713566
		// "Complete Picking" button 
		btn_RetrievalComplete.setEnabled(false);
		//#CM713567
		// "Clear Picking Qty" button 
		btn_RetrievalQtyClear.setEnabled(false);
		//#CM713568
		// "Clear List" button 
		btn_ListClear.setEnabled(false);

		//#CM713569
		// Show the initial display. 
		setFirstDisp();
	}

	//#CM713570
	/**
	 * Returning from a popup window invokes this method.
	 * Override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE>. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM713571
		// Obtain the parameter selected in the listbox. 
		//#CM713572
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM713573
		// Planned Picking Date
		String startstorageplandate = param.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM713574
		// Item Code
		String itemcode = param.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);

		//#CM713575
		// Set a value if not empty. 
		//#CM713576
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM713577
		// Planned Picking Date
		if (!StringUtil.isBlank(startstorageplandate))
		{
			txt_RtrivlPlanDate.setDate(WmsFormatter.toDate(startstorageplandate));
		}
		//#CM713578
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
	}

	//#CM713579
	// Package methods -----------------------------------------------

	//#CM713580
	// Protected methods ---------------------------------------------
	//#CM713581
	/**
	 * Check for input. 
	 * Set the message if any error and return false. 
	 * 
	 * @param rowNo Line No. to be Checked 
	 * @return true: Normal, false: Abnormal 
	 */
	protected boolean checkContainNgText(int rowNo)
	{
		
		WmsCheckker checker = new WmsCheckker();
		
		lst_RetrievalItemComplete.setCurrentRow(rowNo);

		if(!checker.checkContainNgText(
				lst_RetrievalItemComplete.getValue(8) ,
				rowNo,
				lst_RetrievalItemComplete.getListCellColumn(8).getResourceKey() )
				)
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		return true;
		
	}

	//#CM713582
	// Private methods -----------------------------------------------

	//#CM713583
	/**
	 * Invoke this method to display/clear the initial display. 
	 * @throws Exception Report all exceptions.  
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM713584
			// Consignor Code
			txt_ConsignorCode.setText("");
			//#CM713585
			// Planned Picking Date
			txt_RtrivlPlanDate.setText("");
			//#CM713586
			// Item Code
			txt_ItemCode.setText("");
			//#CM713587
			// Case/Piece division 
			rdo_CpfAll.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);
			//#CM713588
			// Input the initial value of Picking qty. 
			chk_CommonUse.setChecked(false);

			//#CM713589
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalItemCompleteSCH();

			RetrievalSupportParameter param = (RetrievalSupportParameter) schedule.initFind(conn, null);
			//#CM713590
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
				//#CM713591
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

	//#CM713592
	/**
	 * Set the value for the preset area. 
	 * @param listParams Parameter object that contains values to be set 
	 * @throws Exception Report all exceptions.  
	 */
	private void setList(Parameter[] listParams) throws Exception
	{
		//#CM713593
		// Delete all lines. 
		lst_RetrievalItemComplete.clearRow();

		RetrievalSupportParameter param = (RetrievalSupportParameter) listParams[0];

		//#CM713594
		// Consignor Code (read-only) 
		txt_RConsignorCode.setText(param.getConsignorCode());
		//#CM713595
		// Consignor Name (read-only) 
		txt_RConsignorName.setText(param.getConsignorName());

		//#CM713596
		// Planned Picking Date (read-only) 
		txt_RRtrivlPlanDate.setDate(WmsFormatter.toDate(param.getRetrievalPlanDate()));

		RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) listParams;

		//#CM713597
		// Picking Location 
		String label_retrivallocation = DisplayText.getText("LBL-W0172");
		//#CM713598
		// Expiry Date
		String label_usebydate = DisplayText.getText("LBL-W0270");
		//#CM713599
		// Case ITF
		String label_caseitf = DisplayText.getText("LBL-W0010");
		//#CM713600
		// Bundle ITF
		String label_bundleitf = DisplayText.getText("LBL-W0006");

		for (int i = 0; i < viewParam.length; i++)
		{
			//#CM713601
			// Add a line. 
			lst_RetrievalItemComplete.setCurrentRow(i + 1);

			lst_RetrievalItemComplete.addRow();

			lst_RetrievalItemComplete.setValue(1, viewParam[i].getItemCode());
			lst_RetrievalItemComplete.setValue(2, viewParam[i].getCasePieceflgName());
			lst_RetrievalItemComplete.setValue(3, WmsFormatter.getNumFormat(viewParam[i].getTotalPlanQty()));
			lst_RetrievalItemComplete.setValue(4, WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));
			lst_RetrievalItemComplete.setValue(5, WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));

			//#CM713602
			// Work No.
			String jobno = viewParam[i].getJobNo();
			//#CM713603
			// Last update date/time
			String lastupdate = WmsFormatter.getTimeStampString(viewParam[i].getLastUpdateDate());

			//#CM713604
			// Connect parameters and maintain the connected parameters in the listcell. 
			//#CM713605
			// Use to check for consistency between the parameter value and the input value. 
			//#CM713606
			// Initial display of Shipping qty 
			//#CM713607
			// Show the initial display of Picking qty 
			if (chk_CommonUse.getChecked())
			{
				//#CM713608
				// Picking Case Qty + Picking Piece Qty + Work No. + Last update date/time 
				List list = new Vector();
				list.add(WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				list.add(WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
				list.add(jobno);
				list.add(lastupdate);
				lst_RetrievalItemComplete.setValue(0, CollectionUtils.getConnectedString(list));

				//#CM713609
				// Set the values of Planned qty for Picking Case Qty and Picking Piece Qty. 
				lst_RetrievalItemComplete.setValue(6, WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				lst_RetrievalItemComplete.setValue(13, WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
			}
			else
			{
				//#CM713610
				// Picking Case Qty + Picking Piece Qty + Work No. + Last update date/time 
				List list = new Vector();
				list.add("");
				list.add("");
				list.add(jobno);
				list.add(lastupdate);
				lst_RetrievalItemComplete.setValue(0, CollectionUtils.getConnectedString(list));

				//#CM713611
				// Set blank for Picking Case Qty and Picking Piece Qty. 
				lst_RetrievalItemComplete.setValue(6, (""));
				lst_RetrievalItemComplete.setValue(13, (""));
			}

			lst_RetrievalItemComplete.setValue(7, viewParam[i].getRetrievalLocation());
			lst_RetrievalItemComplete.setValue(8, viewParam[i].getUseByDate());
			lst_RetrievalItemComplete.setValue(9, viewParam[i].getITF());
			lst_RetrievalItemComplete.setValue(10, viewParam[i].getItemName());
			lst_RetrievalItemComplete.setValue(11, WmsFormatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
			lst_RetrievalItemComplete.setValue(12, WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
			lst_RetrievalItemComplete.setValue(14, viewParam[i].getBundleITF());

			//#CM713612
			// Maintain it in ViewState. 
			//#CM713613
			// Work No.
			this.getViewState().setString("JOBNO", jobno);
			//#CM713614
			// Last update date/time
			this.getViewState().setString("LASTUPDATE", lastupdate);
			//#CM713615
			// Case/Piece division
			this.getViewState().setBoolean(VK_CASEPIECE_ALL, rdo_CpfAll.getChecked());
			this.getViewState().setBoolean(VK_CASEPIECE_CASE, rdo_CpfCase.getChecked());
			this.getViewState().setBoolean(VK_CASEPIECE_PIECE, rdo_CpfPiece.getChecked());
			this.getViewState().setBoolean(VK_CASEPIECE_APPOINTOFF, rdo_CpfAppointOff.getChecked());
			//#CM713616
			// Set the tool tip. 
			ToolTipHelper toolTip = new ToolTipHelper();
			//#CM713617
			// Picking Location 
			toolTip.add(label_retrivallocation, viewParam[i].getRetrievalLocation());
			//#CM713618
			// Expiry Date
			toolTip.add(label_usebydate, viewParam[i].getUseByDate());
			//#CM713619
			// Case ITF
			toolTip.add(label_caseitf, viewParam[i].getITF());
			//#CM713620
			// Bundle ITF
			toolTip.add(label_bundleitf, viewParam[i].getBundleITF());
			//#CM713621
			// Set the ToolTip for the current line. 
			lst_RetrievalItemComplete.setToolTip(lst_RetrievalItemComplete.getCurrentRow(), toolTip.getText());

		}

		//#CM713622
		// Enable to click a button. 
		//#CM713623
		// "Complete Picking" button 
		btn_RetrievalComplete.setEnabled(true);
		//#CM713624
		// "Clear Picking Qty" button 
		btn_RetrievalQtyClear.setEnabled(true);
		//#CM713625
		// "Clear List" button 
		btn_ListClear.setEnabled(true);
	}

	//#CM713626
	/** 
	 * Return 0 if the value of the Parameter is null character. <BR>
	 * <BR>
	 * @param num Target string 
	 * @throws Exception Report all exceptions. 
	 */
	private int getListCellNum(String num) throws Exception
	{
		if (num == null || num.equals(""))
		{
			return 0;
		}
		else
		{
			return WmsFormatter.getInt(num);
		}
	}

	//#CM713627
	/**
	 * Allow this method to concatenate HIDDEN field items into a single string. <BR>
	 * <BR>
	 * HIDDEN field item order list  <BR>
	 * <DIR>
	 *    0: Case/Piece division  <BR>
	 *    1:Work No. <BR>
	 *    2:Last update date/time <BR>
	 * </DIR>
	 * 
	 * @param viewParam ShippingParameter <CODE>Parameter</CODE> class that contains HIDDENField item.
	 * @return Connected hidden field item 
	 */
	private String createHiddenList(RetrievalSupportParameter viewParam)
	{
		String hidden = null;

		//#CM713628
		// Generate a string of Hidden field item.  
		ArrayList hiddenList = new ArrayList();

		hiddenList.add(0, viewParam.getCasePieceflg());
		hiddenList.add(1, viewParam.getJobNo());
		hiddenList.add(2, WmsFormatter.getTimeStampString(viewParam.getLastUpdateDate()));

		hidden = CollectionUtils.getConnectedString(hiddenList);

		return hidden;
	}

	//#CM713629
	// Event handler methods -----------------------------------------
	//#CM713630
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

	//#CM713631
	/** 
	 * Clicking on the Search Consignor Code button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for a parameter and displays the Consignor list listbox using the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]  <BR>
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
		//#CM713632
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM713633
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM713634
		// "Search" flag 
		param.setParameter(ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM713635
		// Work Status (Data with "Started" in the Picking Plan Info) 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_STARTED);
		param.setParameter(ListRetrievalConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		//#CM713636
		// For Item 
		param.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM713637
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do", param, "/progress.do");
	}

	//#CM713638
	/** 
	 * Clicking on the Search Planned Picking Date button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Planned Picking Date list using this search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]  <BR>
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
		//#CM713639
		// Set the search condition in the Search screen of Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM713640
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM713641
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM713642
		// "Search" flag 
		param.setParameter(ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM713643
		// Work Status (Data with "Started" in the Picking Plan Info) 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_STARTED);
		param.setParameter(ListRetrievalPlanDateBusiness.WORKSTATUSRETRIEVALPLANDATE_KEY, search);
		//#CM713644
		// For Item 
		param.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM713645
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", param, "/progress.do");
	}

	//#CM713646
	/** 
	 * Clicking on the Search Item Code button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for a parameter and displays the item list listbox using the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]  <BR>
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
		//#CM713647
		// Set the search condition in the Search screen of Item. 
		ForwardParameters param = new ForwardParameters();
		//#CM713648
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM713649
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM713650
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM713651
		// "Search" flag 
		param.setParameter(ListRetrievalItemBusiness.SEARCHITEM_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM713652
		// Work Status (Data with "Started" in the Picking Plan Info) 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_STARTED);
		param.setParameter(ListRetrievalItemBusiness.WORKSTATUSITEM_KEY, search);
		//#CM713653
		// For Item 
		param.setParameter(ListRetrievalItemBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM713654
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalitem/ListRetrievalItem.do", param, "/progress.do");
	}

	//#CM713655
	/** 
	 * Clicking on the Display button invokes this.  <BR>
	 * <BR>
	 * Summary: Searches through Picking Plan Info using field items input in the Input area as conditions and displays data in the Preset area. <BR>
	 * <BR>
	 * <DIR>
	 *	1. Check for input in the input field item in the input area. (Mandatory Input, number of characters, and attribution of characters)  <BR>
	 *	2. Start the schedule.  <BR>
	 *	3. Display it in the preset area.  <BR>
	 *	4.Enable the Complete Picking button, the Clear Picking Qty button, and the Clear List button.  <BR>
	 *	5. Maintain the contents of the input area.  <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM713656
			// Clear the preset area when input error occurs. 
			btn_ListClear_Click(e);

			//#CM713657
			// Check for input. 
			txt_WorkerCode.validate();
			txt_Password.validate();
			txt_ConsignorCode.validate();
			txt_RtrivlPlanDate.validate();
			//#CM713658
			// Pattern matching characters 
			txt_ItemCode.validate(false);

			//#CM713659
			// Set for the schedule parameter: 
			RetrievalSupportParameter param = new RetrievalSupportParameter();
			//#CM713660
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM713661
			// Password 
			param.setPassword(txt_Password.getText());
			//#CM713662
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM713663
			// Planned Picking Date
			param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
			//#CM713664
			// Item Code
			param.setItemCode(txt_ItemCode.getText());

			//#CM713665
			// Case/Piece division 
			if (rdo_CpfAll.getChecked())
			{
				//#CM713666
				// All 
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
			}
			else if (rdo_CpfCase.getChecked())
			{
				//#CM713667
				// Case 
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_CpfPiece.getChecked())
			{
				//#CM713668
				// Piece 
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
			}
			else if (rdo_CpfAppointOff.getChecked())
			{
				//#CM713669
				// None 
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
			}

			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalItemCompleteSCH();

			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) schedule.query(conn, param);

			if (viewParam == null || viewParam.length == 0)
			{
				//#CM713670
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());

				return;
			}

			//#CM713671
			// Set the listcell. 
			setList(viewParam);

			//#CM713672
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
				//#CM713673
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

	//#CM713674
	/** 
	 * Clicking on the Clear button invokes this. <BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 * <DIR>
	 *   1. Initialize the screen. <BR>
	 *     For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method. <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM713675
		// Execute the Clear process. 
		setFirstDisp();
	}

	//#CM713676
	/** 
	 * Clicking on Complete Picking button invokes this.  <BR>
	 * <BR>
	 * Summary: Completes the Picking Using the info in the Preset area. <BR>
	 * <BR>
	 * <DIR>
	 *	1. Check the input area.  <BR>
	 *	<DIR>
	 *		<BR>
	 *		- If no value (null) is input in the Quantity input field, display the message There is no target data to update.  <BR>
	 *		<BR>
	 *		- If any value is input, display the dialog box to allow to confirm :"Do you commit this shipping?". <BR>
	 *		<BR>
	 *		 [Dialog for confirming: Cancel]  <BR>
	 *		<BR>
	 *		<DIR>
	 *			 Disable to do anything.  <BR>
	 *		</DIR>
	 *		<BR>
	 *		 [Dialog for confirming: OK]  <BR>
	 *		<BR>
	 *		<DIR>
	 *			1. Check for input in the input item (count) in the preset area.  <BR>
	 *			2. Start the schedule.  <BR>
	 *			3. Clear the preset area.  <BR>
	 *		</DIR>
	 *	</DIR>
	 *	<BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_RetrievalComplete_Click(ActionEvent e) throws Exception
	{
		//#CM713677
		// Check for mandatory input. 
		txt_WorkerCode.validate();
		txt_Password.validate();

		for (int i = 1; i < lst_RetrievalItemComplete.getMaxRows(); i++)
		{
			try
			{
				//#CM713678
				// Designate the line. 
				lst_RetrievalItemComplete.setCurrentRow(i);
				//#CM713679
				// Validate the field items in the designated row (Expiry Date), except for mandatory input. 
				lst_RetrievalItemComplete.validate(8, false);
				//#CM713680
				// Check the input characters for eWareNavi. 
				if (!checkContainNgText(i))
				{
					return;
				}
			}
			catch (ValidateException ve)
			{
				//#CM713681
				// Set the message. 
				String errorMessage = MessageResources.getText(ve.getErrorNo(), ve.getBinds().toArray());
				//#CM713682
				// 6023273 = No.{0}{1}
				throw new ValidateException("6023273", Integer.toString(i), errorMessage);
			}
		}

		Connection conn = null;

		try
		{
			Vector vecParam = new Vector(lst_RetrievalItemComplete.getMaxRows());
			for (int i = 1; i < lst_RetrievalItemComplete.getMaxRows(); i++)
			{
				//#CM713683
				// Initialize the designated position of a line. 
				lst_RetrievalItemComplete.setCurrentRow(i);
				//#CM713684
				// For data with blank in Picking Case Qty and Picking Piece, disable to target such data for update. 
				if (lst_RetrievalItemComplete.getValue(6).equals("") && lst_RetrievalItemComplete.getValue(13).equals(""))
				{
					continue;
				}

				RetrievalSupportParameter param = new RetrievalSupportParameter();

				//#CM713685
				// Worker Code
				param.setWorkerCode(txt_WorkerCode.getText());
				//#CM713686
				// Password 
				param.setPassword(txt_Password.getText());

				//#CM713687
				// Set the value in the preset area for the parameter. 
				//#CM713688
				// Consignor Code (read-only) 
				param.setConsignorCode(txt_RConsignorCode.getText());
				//#CM713689
				// Planned Picking Date (read-only) 
				//#CM713690
				// Translate the data type from Date type into String type (YYYYMMDD). 
				param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_RRtrivlPlanDate.getDate()));
				//#CM713691
				// Item Code
				param.setItemCode(lst_RetrievalItemComplete.getValue(1));
				//#CM713692
				// Total Picking qty 
				param.setTotalPlanQty(WmsFormatter.getInt(lst_RetrievalItemComplete.getValue(3)));
				//#CM713693
				// Packed Qty per Case
				param.setEnteringQty(WmsFormatter.getInt(lst_RetrievalItemComplete.getValue(4)));
				//#CM713694
				// Planned Work Case Qty
				param.setPlanCaseQty(WmsFormatter.getInt(lst_RetrievalItemComplete.getValue(5)));
				//#CM713695
				// Picking Location 
				param.setRetrievalLocation(lst_RetrievalItemComplete.getValue(7));
				//#CM713696
				// Expiry Date
				param.setUseByDate(lst_RetrievalItemComplete.getValue(8));
				//#CM713697
				// Case ITF
				param.setITF(lst_RetrievalItemComplete.getValue(9));
				//#CM713698
				// Item Name
				param.setItemName(lst_RetrievalItemComplete.getValue(10));
				//#CM713699
				// Packed qty per bundle
				param.setBundleEnteringQty(WmsFormatter.getInt(lst_RetrievalItemComplete.getValue(11)));
				//#CM713700
				// Planned Work Piece Qty
				param.setPlanPieceQty(WmsFormatter.getInt(lst_RetrievalItemComplete.getValue(12)));
				//#CM713701
				// Picking Case Qty 
				param.setResultCaseQty(getListCellNum(lst_RetrievalItemComplete.getValue(6)));
				//#CM713702
				// Picking Piece Qty 
				param.setResultPieceQty(getListCellNum(lst_RetrievalItemComplete.getValue(13)));
				//#CM713703
				// Bundle ITF
				param.setBundleITF(lst_RetrievalItemComplete.getValue(14));

				if (this.getViewState().getBoolean(VK_CASEPIECE_ALL))
				{
					//#CM713704
					// All 
					param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
				}
				else if (this.getViewState().getBoolean(VK_CASEPIECE_APPOINTOFF))
				{
					//#CM713705
					// None 
					param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
				}
				else if (this.getViewState().getBoolean(VK_CASEPIECE_CASE))
				{
					//#CM713706
					// Case 
					param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
				}
				else if (this.getViewState().getBoolean(VK_CASEPIECE_PIECE))
				{
					//#CM713707
					// Piece 
					param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
				}

				//#CM713708
				// Set the hidden field item. (Case/Piece division (0),Work No. (1),Last update date/time (2)) 
				String hidden = lst_RetrievalItemComplete.getValue(0);
				//#CM713709
				// Work No.
				param.setJobNo(CollectionUtils.getString(2, hidden));
				//#CM713710
				// Last update date/time
				//#CM713711
				// Translate the data type from String type (YYYYMMDDHHMMSS) into Date type. 
				param.setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(3, hidden)));
				//#CM713712
				// Terminal No.
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());
				//#CM713713
				// Preset line No. 
				param.setRowNo(lst_RetrievalItemComplete.getCurrentRow());
				vecParam.addElement(param);
			}

			if (vecParam.size() <= 0)
			{
				//#CM713714
				// 6023154=There is no data to update. 
				message.setMsgResourceKey("6023154");
				return;
			}

			//#CM713715
			// Declare the parameter. 
			RetrievalSupportParameter[] paramArray = new RetrievalSupportParameter[vecParam.size()];
			//#CM713716
			// Copy the value to the parameter. 
			vecParam.copyInto(paramArray);

			//#CM713717
			// Generate the instance of schedule class. 
			WmsScheduler schedule = new RetrievalItemCompleteSCH();
			//#CM713718
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM713719
			// Start the schedule. 
			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) schedule.startSCHgetParams(conn, paramArray);

			if (viewParam == null)
			{
				conn.rollback();
				//#CM713720
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.commit();
				//#CM713721
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());
				//#CM713722
				// If the target data goes out, clear the preset area. 
				btn_ListClear_Click(e);

				if (viewParam.length != 0)
				{
					//#CM713723
					// Set the listcell. 
					setList(viewParam);
				}
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
				//#CM713724
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

	//#CM713725
	/** 
	 * Clicking on the Clear Picking Qty button invokes this. <BR>
	 * <BR>
	 * Summary: Clears the Picking Case Qty and the Picking Piece Qty in the preset area.<BR>
	 * <BR>
	 * <DIR>
	 *   1.Clear the field item in the Preset area. <BR>
	 *   2. Maintain the contents of the input area. <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_RetrievalQtyClear_Click(ActionEvent e) throws Exception
	{
		//#CM713726
		// Set the listcell. 
		for (int i = 1; i < lst_RetrievalItemComplete.getMaxRows(); i++)
		{
			lst_RetrievalItemComplete.setCurrentRow(i);
			lst_RetrievalItemComplete.setValue(6, (""));
			lst_RetrievalItemComplete.setValue(13, (""));
		}
	}

	//#CM713727
	/** 
	 * Clicking on the Clear List button invokes this. <BR>
	 * <BR>
	 * Summary: Clears all info displayed in the preset area. <BR>
	 * <BR>
	 *   <DIR>
	 *   1. Display the dialog box for confirmation. <BR>
	 *   <BR>
	 *     <DIR>
	 *     [Dialog for confirming: Cancel] <BR>
	 *     <BR>
	 *       <DIR>
	 *       Disable to do anything. <BR>
	 *       </DIR>
	 *     <BR>
	 *     [Dialog for confirming: OK] <BR>
	 *     <BR>
	 *       <DIR>
	 *       1.Disable the Complete Picking button, Clear Picking Qty button, and Clear List button. <BR>
	 *       2. Maintain the contents of the input area. <BR>
	 *       </DIR>
	 *     </DIR>
	 *   <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		//#CM713728
		// Delete all lines. 
		lst_RetrievalItemComplete.clearRow();

		//#CM713729
		// Consignor Code (read-only) 
		txt_RConsignorCode.setText("");
		//#CM713730
		// Consignor Name (read-only) 
		txt_RConsignorName.setText("");
		//#CM713731
		// Planned Picking Date (read-only) 
		txt_RRtrivlPlanDate.setText("");

		//#CM713732
		// Disable to click a button. 
		//#CM713733
		// "Complete Picking" button 
		btn_RetrievalComplete.setEnabled(false);
		//#CM713734
		// "Clear Picking Qty" button 
		btn_RetrievalQtyClear.setEnabled(false);
		//#CM713735
		// "Clear List" button 
		btn_ListClear.setEnabled(false);
	}
	//#CM713736
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713737
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713738
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_EndSet_Click(ActionEvent e) throws Exception
	{
	}

	//#CM713739
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713740
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713741
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713742
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713743
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713744
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM713745
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713746
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713747
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713748
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713749
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM713750
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713751
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713752
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713753
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713754
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM713755
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713756
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713757
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713758
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713759
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713760
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713761
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713762
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713763
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713764
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713765
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM713766
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713767
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713768
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713769
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM713770
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713771
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM713772
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713773
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM713774
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713775
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM713776
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RtrvlFirstInput_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713777
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713778
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Change(ActionEvent e) throws Exception
	{
	}

	//#CM713779
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713780
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713781
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_RetrievalComplete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713782
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_RetrievalQtyClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713783
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713784
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713785
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713786
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713787
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713788
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM713789
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713790
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713791
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713792
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM713793
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RetrievalPlanDateT_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713794
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713795
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RRtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713796
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RRtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713797
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemComplete_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713798
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemComplete_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713799
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemComplete_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM713800
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemComplete_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM713801
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemComplete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713802
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemComplete_Change(ActionEvent e) throws Exception
	{
	}

	//#CM713803
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalItemComplete_Click(ActionEvent e) throws Exception
	{
	}


}
//#CM713804
//end of class
