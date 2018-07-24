// $Id: RetrievalOrderCompleteBusiness.java,v 1.3 2007/02/07 04:19:21 suresh Exp $

//#CM715781
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalordercomplete;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.ListCellColumn;
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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderCompleteSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM715782
/**
 * Designer : T.Yoshino<BR>
 * Maker :T.Hondo<BR>
 * <BR>
 * Allow this class of screen to complete Order Picking via listing work. <BR>
 * Pass the parameter to the schedule to complete Order Picking. <BR>
 * Allow this screen to commit and roll back the transaction. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process by clicking "Display" button (<CODE>btn_View_Click()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *	 Set the contents entered via screen, and allow the schedule to search for data to be displayed based on the parameter.<BR>
 *	 Receive data to be output to the preset area from the schedule and output it to the preset area. <BR>
 *	<BR>
 *	 [Parameter]  *Mandatory Input<BR>
 *	<BR>
 *	<DIR>
 *		Worker Code* <BR>
 *		 Password * <BR>
 *		Consignor Code* <BR>
 *		Planned Picking Date* <BR>
 *		Order No.* <BR>
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
 *		Order No. <BR>
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
 * 2. Process by clicking "Add" button (<CODE>btn_RetrievalConplete_Click()</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *	 Set the contents input via preset area for the parameter, and allow the schedule to complete the Order Picking based on the parameter.<BR>
 *	 Receive the result from the schedule. Receive true if the process completed normally.<BR>
 *	Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 *	 Output the message obtained from the schedule to the screen, as the schedule result. <BR>
 *	<BR>
 *	 [Parameter]  *Mandatory input + either one ofMandatory input options  <BR>
 *	<BR>
 *	<DIR>
 *		Worker Code* <BR>
 *		 Password * <BR>
 *		Consignor Code <BR>
 *		Consignor Name <BR>
 *		Planned Picking Date <BR>
 *		Order No. <BR>
 *		 Preset.Item Code  <BR>
 *		 Preset.Item Name  <BR>
 *		 Preset.Division  <BR>
 *		 Preset.Total Picking qty  <BR>
 *		 Preset.Packed Qty per Case  <BR>
 *		 Preset.Packed qty per bundle  <BR>
 *		 Preset.Planned Work Case Qty  <BR>
 *		 Preset.Planned Work Piece Qty  <BR>
 *		 Preset. Picking Case Qty + <BR>
 *		 Preset.Picking Piece Qty + <BR>
 *		 Preset. Picking Location  <BR>
 *		 Preset.Expiry Date  <BR>
 *		 Preset.Case ITF  <BR>
 *		 Preset.Bundle ITF  <BR>
 *		Work No. <BR>
 *		Last update date/time <BR>
 *	</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/25</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:21 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderCompleteBusiness extends RetrievalOrderComplete implements WMSConstants
{
	//#CM715783
	// Class fields --------------------------------------------------
	//#CM715784
	// Key for maintaining the Case/Piece division (All) 
	private final static String FLAGALL = "FLAGALL";

	//#CM715785
	// Key for maintaining the Case/Piece division (Case) 
	private final static String FLAGCASE = "FLAGCASE";

	//#CM715786
	// Key for maintaining the Case/Piece division (Piece) 
	private final static String FLAGPIECE = "FLAGPIECE";

	//#CM715787
	// Key for maintaining the Case/Piece division (None) 
	private final static String FLAGAPPOINTOFF = "FLAGAPPOINTOFF";

	//#CM715788
	// Key for maintaining the Work No. 
	private final static String JOBNO = "JOBNO";

	//#CM715789
	// Key for maintaining the Last update date/time 
	private final static String LASTUPDATE = "LASTUPDATE";

	//#CM715790
	// Class variables -----------------------------------------------

	//#CM715791
	// Class method --------------------------------------------------

	//#CM715792
	// Constructors --------------------------------------------------

	//#CM715793
	// Public methods ------------------------------------------------

	//#CM715794
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Summary: shows the Initial Display. <BR>
	 * <BR>
	 * <DIR>
	 *    1. Initialize the input area. <BR>
	 *    2. Start the schedule.  <BR>
	 *    3.Disable the Complete Picking button, Clear Picking Qty button, and Clear List button.  <BR>
	 *    4.Set the cursor on the Work Code. <BR>
	 * </DIR>
	 * <BR>
	 * Field item [Initial Value]  <BR>
	 * <BR>
	 * Worker Code			 [None]  <BR>
	 * Password 			 [None]  <BR>
	 * Consignor Code			 [Search Value]  <BR>
	 * Planned Picking Date			 [None]  <BR>
	 * Order No.			 [None]  <BR>
	 * Case/Piece division 	 [All]  <BR>
	 * Input the initial value of Picking qty. 	 [Not Ticked]  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM715795
		// Disable to click a button. 
		//#CM715796
		// "Complete Picking" button 
		btn_RetrievalConplete.setEnabled(false);
		//#CM715797
		// "Clear Picking Qty" button 
		btn_RetrievalQtyClear.setEnabled(false);
		//#CM715798
		// "Clear List" button 
		btn_ListClear.setEnabled(false);

		//#CM715799
		// Show the initial display. 
		setFirstDisp();
	}

	//#CM715800
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
			//#CM715801
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM715802
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM715803
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}

		//#CM715804
		// MSG-W0020 = Do you complete it? 
		btn_RetrievalConplete.setBeforeConfirm("MSG-W0020");
		//#CM715805
		// MSG-W0012 = This clears the list input info. Do you confirm it? 
		btn_ListClear.setBeforeConfirm("MSG-W0012");
	}

	//#CM715806
	/**
	 * Returning from a popup window invokes this method.
	 * Override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE>. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM715807
		// Obtain the parameter selected in the listbox. 
		//#CM715808
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM715809
		// Planned Picking Date
		String retrievalplandate = param.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM715810
		// Order No.
		String orderno = param.getParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY);

		//#CM715811
		// Set a value if not empty. 
		//#CM715812
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM715813
		// Planned Picking Date
		if (!StringUtil.isBlank(retrievalplandate))
		{
			txt_RtrivlPlanDate.setDate(WmsFormatter.toDate(retrievalplandate));
		}
		//#CM715814
		// Order No.
		if (!StringUtil.isBlank(orderno))
		{
			txt_OrderNo.setText(orderno);
		}

		//#CM715815
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM715816
	// Package methods -----------------------------------------------

	//#CM715817
	// Protected methods ---------------------------------------------
	//#CM715818
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

		lst_RetrievalOrderComplete.setCurrentRow(rowNo);

		if(!checker.checkContainNgText(
				lst_RetrievalOrderComplete.getValue(8) ,
				rowNo,
				lst_RetrievalOrderComplete.getListCellColumn(8).getResourceKey() )
				)
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		return true;
		
	}

	//#CM715819
	// Private methods -----------------------------------------------
	//#CM715820
	/**
	 * Invoke this method to display/clear the initial display. 
	 * @throws Exception Report all exceptions. 
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM715821
			// Set the cursor on the Worker code. 
			setFocus(txt_WorkerCode);

			//#CM715822
			// Consignor Code
			txt_ConsignorCode.setText("");
			//#CM715823
			// Planned Picking Date
			txt_RtrivlPlanDate.setText("");
			//#CM715824
			// Order No.
			txt_OrderNo.setText("");
			//#CM715825
			// Case/Piece division 
			rdo_CpfAll.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);
			//#CM715826
			// Remove check from Execute initial input of Picking qty. 
			chk_CommonUse.setChecked(false);

			//#CM715827
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalOrderCompleteSCH();

			RetrievalSupportParameter param = (RetrievalSupportParameter) schedule.initFind(conn, null);
			//#CM715828
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
				//#CM715829
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

	//#CM715830
	/**
	 * Set the value for the preset area. 
	 * 
	 * @param listParams Parameter object that contains info to be displayed in the preset area.  
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(Parameter[] listParams) throws Exception
	{
		//#CM715831
		// Delete all lines. 
		lst_RetrievalOrderComplete.clearRow();

		RetrievalSupportParameter param = (RetrievalSupportParameter) listParams[0];

		//#CM715832
		// Consignor Code (read-only) 
		txt_RConsignorCode.setText(param.getConsignorCode());
		//#CM715833
		// Consignor Name (read-only) 
		txt_RConsignorName.setText(param.getConsignorName());

		//#CM715834
		// Planned Picking Date (read-only) 
		txt_RRtrivlPlanDate.setDate(WmsFormatter.toDate(param.getRetrievalPlanDate()));
		//#CM715835
		// Order No. (read-only) 
		txt_ROrderNo.setText(param.getOrderNo());

		RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) listParams;

		String label_retrievallocation = DisplayText.getText("LBL-W0172");
		String label_caseitf = DisplayText.getText("LBL-W0010");
		String label_bundleitf = DisplayText.getText("LBL-W0006");

		for (int i = 0; i < viewParam.length; i++)
		{
			//#CM715836
			// Add a line. 
			lst_RetrievalOrderComplete.setCurrentRow(i + 1);

			lst_RetrievalOrderComplete.addRow();

			//#CM715837
			// Compile the ToolTip data. 
			ToolTipHelper toolTip = new ToolTipHelper();
			toolTip.add(label_retrievallocation, viewParam[i].getRetrievalLocation());
			toolTip.add(label_caseitf, viewParam[i].getITF());
			toolTip.add(label_bundleitf, viewParam[i].getBundleITF());

			//#CM715838
			// Set the TOOL TIP. 	
			lst_RetrievalOrderComplete.setToolTip(lst_RetrievalOrderComplete.getCurrentRow(), toolTip.getText());

			lst_RetrievalOrderComplete.setValue(1, viewParam[i].getItemCode());
			lst_RetrievalOrderComplete.setValue(2, viewParam[i].getCasePieceflgName());
			lst_RetrievalOrderComplete.setValue(3, WmsFormatter.getNumFormat(viewParam[i].getTotalRetrievalQty()));
			lst_RetrievalOrderComplete.setValue(4, WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));
			lst_RetrievalOrderComplete.setValue(5, WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));

			//#CM715839
			// Work No.
			String jobno = viewParam[i].getJobNo();
			//#CM715840
			// Last update date/time
			String lastupdate = WmsFormatter.getTimeStampString(viewParam[i].getLastUpdateDate());

			//#CM715841
			// Connect parameters and maintain the connected parameters in the listcell. 
			//#CM715842
			// Use to check for consistency between the parameter value and the input value. 
			//#CM715843
			// Show the initial display of Picking qty 
			if (chk_CommonUse.getChecked())
			{
				//#CM715844
				// Picking Case Qty + Picking Piece Qty + Work No. + Last update date/time 
				List list = new Vector();
				list.add(WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				list.add(WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
				list.add(jobno);
				list.add(lastupdate);
				lst_RetrievalOrderComplete.setValue(0, CollectionUtils.getConnectedString(list));

				//#CM715845
				// Set the values of Planned qty for Picking Case Qty and Picking Piece Qty. 
				lst_RetrievalOrderComplete.setValue(6, WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				lst_RetrievalOrderComplete.setValue(13, WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
			}
			else
			{
				//#CM715846
				// Picking Case Qty + Picking Piece Qty + Work No. + Last update date/time 
				List list = new Vector();
				list.add("");
				list.add("");
				list.add(jobno);
				list.add(lastupdate);
				lst_RetrievalOrderComplete.setValue(0, CollectionUtils.getConnectedString(list));

				//#CM715847
				// Set blank for Picking Case Qty and Picking Piece Qty. 
				lst_RetrievalOrderComplete.setValue(6, "");
				lst_RetrievalOrderComplete.setValue(13, "");
			}

			lst_RetrievalOrderComplete.setValue(7, viewParam[i].getRetrievalLocation());
			lst_RetrievalOrderComplete.setValue(8, viewParam[i].getUseByDate());
			lst_RetrievalOrderComplete.setValue(9, viewParam[i].getITF());
			lst_RetrievalOrderComplete.setValue(10, viewParam[i].getItemName());
			lst_RetrievalOrderComplete.setValue(11, WmsFormatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
			lst_RetrievalOrderComplete.setValue(12, WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
			lst_RetrievalOrderComplete.setValue(14, viewParam[i].getBundleITF());

			//#CM715848
			// Maintain it in ViewState. 
			//#CM715849
			// Work No.
			this.getViewState().setString(JOBNO, jobno);
			//#CM715850
			// Last update date/time
			this.getViewState().setString(LASTUPDATE, lastupdate);

		}

		//#CM715851
		// Enable to click a button. 
		//#CM715852
		// "Complete Picking" button 
		btn_RetrievalConplete.setEnabled(true);
		//#CM715853
		// "Clear Picking Qty" button 
		btn_RetrievalQtyClear.setEnabled(true);
		//#CM715854
		// "Clear List" button 
		btn_ListClear.setEnabled(true);

	}

	//#CM715855
	/** 
	 * Return 0 if the value of the listcell is null character. <BR>
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

	//#CM715856
	// Event handler methods -----------------------------------------
	//#CM715857
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

	//#CM715858
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
		//#CM715859
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM715860
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM715861
		// "Search" flag 
		param.setParameter(ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM715862
		// Work Status (Data with "Started" in the Picking Plan Info) 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_STARTED);
		param.setParameter(ListRetrievalConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		//#CM715863
		// for Order 
		param.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM715864
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do", param, "/progress.do");
	}

	//#CM715865
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
		//#CM715866
		// Set the search condition in the Search screen of Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM715867
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM715868
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM715869
		// "Search" flag 
		param.setParameter(ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM715870
		// Work Status (Data with "Started" in the Picking Plan Info) 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_STARTED);
		param.setParameter(ListRetrievalPlanDateBusiness.WORKSTATUSRETRIEVALPLANDATE_KEY, search);
		//#CM715871
		// for Order 
		param.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM715872
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", param, "/progress.do");
	}

	//#CM715873
	/**
	 * Clicking on the Search Order No. button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Order No. list using this search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]  <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Planned Picking Date <BR>
	 *       Order No. <BR>
	 *       Case/Piece division <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchOrderNo_Click(ActionEvent e) throws Exception
	{
		//#CM715874
		// Set the search condition in the Search screen of Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM715875
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM715876
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM715877
		// Order No.
		param.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, txt_OrderNo.getText());
		//#CM715878
		// Case/Piece flag 
		param.setParameter(ListRetrievalOrdernoBusiness.CASE_PIECE_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
		//#CM715879
		// "Search" flag 
		param.setParameter(ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM715880
		// Work Status (Data with "Started" in the Picking Plan Info) 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_STARTED);
		param.setParameter(ListRetrievalOrdernoBusiness.WORKSTATUSORDERNO_KEY, search);
		//#CM715881
		// for Order 
		param.setParameter(ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM715882
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do", param, "/progress.do");
	}

	//#CM715883
	/**
	 * Clicking on the Display button invokes this.  <BR>
	 * <BR>
	 * Summary: Searches through Picking Plan Info using field items input in the Input area as conditions and displays data in the Preset area. <BR>
	 * <BR>
	 * <DIR>
	 *	1. Check for input in the input field item in the input area. (Mandatory Input, number of characters, and attribution of characters)  <BR>
	 *	2. Start the schedule.  <BR>
	 *	3. Display it in the preset area.  <BR>
	 *	<BR>
	 *	<DIR>
	 *		- Initial Input of Picking qty is ticked: Set the planned qty in the Worker input area.  <BR>
	 *		<BR>
	 *		- Initial Input of Picking qty is not ticked: Set Null in the Worker input area.  <BR>
	 *	</DIR>
	 *	<BR>
	 *	4.Enable the Complete Picking button, the Clear Picking Qty button, and the Clear List button.  <BR>
	 *	5. Maintain the contents of the input area.  <BR>
	 * </DIR>
	 * <BR>
	 * <DIR>
	 *	 [Row No. list of listcell]  <BR>
	 *	<BR>
	 *	<DIR>
	 *		1.Item Code <BR>
	 *		2. Division  <BR>
	 *		3. Total Picking qty  <BR>
	 *		4.Packed Qty per Case <BR>
	 *		5.Planned Work Case Qty <BR>
	 *		6. Picking Case Qty  <BR>
	 *		7. Picking Location  <BR>
	 *		8.Expiry Date <BR>
	 *		9.Case ITF <BR>
	 *		10.Item Name <BR>
	 *		11.Packed qty per bundle <BR>
	 *		12.Planned Work Piece Qty <BR>
	 *		13. Picking Piece Qty  <BR>
	 *		14.Bundle ITF <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		//#CM715884
		// Set focus for the Worker Code. 
		setFocus(txt_WorkerCode);

		Connection conn = null;
		try
		{
			//#CM715885
			// Clear the preset area when input error occurs. 
			btn_ListClear_Click(e);

			//#CM715886
			// Check for input. 
			txt_WorkerCode.validate();
			txt_Password.validate();
			txt_ConsignorCode.validate();
			txt_RtrivlPlanDate.validate();
			txt_OrderNo.validate();

			//#CM715887
			// Set for the schedule parameter: 
			RetrievalSupportParameter param = new RetrievalSupportParameter();
			//#CM715888
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM715889
			// Password 
			param.setPassword(txt_Password.getText());
			//#CM715890
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM715891
			// Planned Picking Date
			param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
			//#CM715892
			// Order No.
			param.setOrderNo(txt_OrderNo.getText());
			//#CM715893
			// Case/Piece division (All) 
			if (rdo_CpfAll.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
			}
			//#CM715894
			// Case/Piece division (Case) 
			else if (rdo_CpfCase.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
			}
			//#CM715895
			// Case/Piece division (Piece) 
			else if (rdo_CpfPiece.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
			}
			//#CM715896
			// Case/Piece division (None) 
			else if (rdo_CpfAppointOff.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
			}

			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalOrderCompleteSCH();
			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) schedule.query(conn, param);

			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM715897
			// Set the listcell. 
			setList(viewParam);

			//#CM715898
			// Maintain it in ViewState. 
			//#CM715899
			// Case/Piece division 
			this.getViewState().setBoolean(FLAGALL, rdo_CpfAll.getChecked());
			this.getViewState().setBoolean(FLAGCASE, rdo_CpfCase.getChecked());
			this.getViewState().setBoolean(FLAGPIECE, rdo_CpfPiece.getChecked());
			this.getViewState().setBoolean(FLAGAPPOINTOFF, rdo_CpfAppointOff.getChecked());

			//#CM715900
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
				//#CM715901
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

	//#CM715902
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
		//#CM715903
		// Clear the Input area. 
		setFirstDisp();
	}

	//#CM715904
	/** 
	 * Clicking on Complete Picking button invokes this. <BR>
	 * <BR>
	 * Summary: Completes the Picking Using the info in the Preset area.<BR>
	 * <BR>
	 * <DIR>
	 *	1. Check the input area. <BR>
	 *	<DIR>
	 *		<BR>
	 *		- If no value (null) is input in the Quantity input field, display the message "There is no target data to update". <BR>
	 *		<BR>
	 *		- If any value is input, display the dialog box to allow to confirm :"Do you commit this shipping?".<BR>
	 *		<BR>
	 *		 [Dialog for confirming: Cancel] <BR>
	 *		<BR>
	 *		<DIR>
	 *			 Disable to do anything. <BR>
	 *		</DIR>
	 *		<BR>
	 *		 [Dialog for confirming: OK] <BR>
	 *		<BR>
	 *		<DIR>
	 *			1. Check for input in the input item (count) in the preset area. <BR>
	 *			2. Start the schedule. <BR>
	 *			3. Load the updated info into the Preset area again and display it <BR>
	 *		</DIR>
	 *	</DIR>
	 *	<BR>
	 * </DIR>
	 * <BR>
	 * <DIR>
	 *	 [Row No. list of listcell] <BR>
	 *	<BR>
	 *	<DIR>
	 *		1.Item Code<BR>
	 *		2. Division <BR>
	 *		3. Total Picking qty <BR>
	 *		4.Packed Qty per Case<BR>
	 *		5.Planned Work Case Qty<BR>
	 *		6. Picking Case Qty <BR>
	 *		7. Picking Location <BR>
	 *		8.Expiry Date<BR>
	 *		9.Case ITF<BR>
	 *		10.Item Name<BR>
	 *		11.Packed qty per bundle<BR>
	 *		12.Planned Work Piece Qty<BR>
	 *		13. Picking Piece Qty <BR>
	 *		14.Bundle ITF<BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_RetrievalConplete_Click(ActionEvent e) throws Exception
	{
		//#CM715905
		// Set focus for the Worker Code. 
		setFocus(txt_WorkerCode);

		//#CM715906
		// Check for input. 
		txt_WorkerCode.validate();
		txt_Password.validate();

		//#CM715907
		// Obtain the title of the listcell and store it in the ListCellColumn. 
		ArrayList lst = (ArrayList) lst_RetrievalOrderComplete.getListCellColumns();
		ListCellColumn[] List_Title = new ListCellColumn[lst.size() + 1];
		for (int l = 0; l < lst.size(); l++)
		{
			ListCellColumn listtemp = (ListCellColumn) lst.get(l);
			List_Title[l + 1] = new ListCellColumn();
			List_Title[l + 1] = (ListCellColumn) listtemp;
		}

		//#CM715908
		// Obtain the maximum (max) number of lines. 
		int index = lst_RetrievalOrderComplete.getMaxRows();

		for (int i = 1; i < index; i++)
		{
			try
			{
				//#CM715909
				// Designate the line. 
				lst_RetrievalOrderComplete.setCurrentRow(i);
				//#CM715910
				// Validate the field items in the designated row (Expiry Date), except for mandatory input. 
				lst_RetrievalOrderComplete.validate(8, false);

				//#CM715911
				// Check the input characters for eWareNavi. 
				if (!checkContainNgText(i))
				{
					return;
				}
			}
			catch (ValidateException ve)
			{
				//#CM715912
				// Set the message. 
				String errorMessage = MessageResources.getText(ve.getErrorNo(), ve.getBinds().toArray());
				//#CM715913
				// 6023273 = No.{0}{1}
				throw new ValidateException("6023273", Integer.toString(i), errorMessage);
			}
		}

		Connection conn = null;
		try
		{
			Vector vecParam = new Vector(index);
			for (int i = 1; i < index; i++)
			{
				//#CM715914
				// Designate the line. 
				lst_RetrievalOrderComplete.setCurrentRow(i);

				//#CM715915
				// For data with blank in Picking Case Qty and Picking Piece, disable to target such data for update. 
				if (lst_RetrievalOrderComplete.getValue(6).equals("") && lst_RetrievalOrderComplete.getValue(13).equals(""))
				{
					continue;
				}

				//#CM715916
				// Set for the schedule parameter: 
				RetrievalSupportParameter param = new RetrievalSupportParameter();
				//#CM715917
				// Worker Code
				param.setWorkerCode(txt_WorkerCode.getText());
				//#CM715918
				// Password 
				param.setPassword(txt_Password.getText());
				//#CM715919
				// Consignor Code (read-only) 
				param.setConsignorCode(txt_RConsignorCode.getText());
				//#CM715920
				// Planned Picking Date (read-only) 
				//#CM715921
				// Translate the data type from Date type into String type (YYYYMMDD). 
				param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_RRtrivlPlanDate.getDate()));
				//#CM715922
				// Order No. (read-only) 
				param.setOrderNo(txt_ROrderNo.getText());
				//#CM715923
				// Case/Piece division (ViewState) 
				//#CM715924
				// All 
				if (this.getViewState().getBoolean(FLAGALL))
				{
					param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
				}
				//#CM715925
				// Case 
				else if (this.getViewState().getBoolean(FLAGCASE))
				{
					param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
				}
				//#CM715926
				// Piece 
				else if (this.getViewState().getBoolean(FLAGPIECE))
				{
					param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
				}
				//#CM715927
				// None 
				else if (this.getViewState().getBoolean(FLAGAPPOINTOFF))
				{
					param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
				}

				//#CM715928
				// Maintain the line No. 
				param.setRowNo(i);
				//#CM715929
				// Item Code
				param.setItemCode(lst_RetrievalOrderComplete.getValue(1));
				//#CM715930
				// Total Picking qty 
				param.setTotalPlanQty(WmsFormatter.getInt(lst_RetrievalOrderComplete.getValue(3)));
				//#CM715931
				// Packed Qty per Case
				param.setEnteringQty(WmsFormatter.getInt(lst_RetrievalOrderComplete.getValue(4)));
				//#CM715932
				// Planned Work Case Qty
				param.setPlanCaseQty(WmsFormatter.getInt(lst_RetrievalOrderComplete.getValue(5)));
				//#CM715933
				// Picking Case Qty 
				param.setRetrievalCaseQty(getListCellNum(lst_RetrievalOrderComplete.getValue(6)));
				//#CM715934
				// Picking Piece Qty 
				param.setRetrievalPieceQty(getListCellNum(lst_RetrievalOrderComplete.getValue(13)));
				//#CM715935
				// Picking Location 
				param.setRetrievalLocation(lst_RetrievalOrderComplete.getValue(7));
				//#CM715936
				// Expiry Date
				param.setUseByDate(lst_RetrievalOrderComplete.getValue(8));
				//#CM715937
				// Case ITF
				param.setITF(lst_RetrievalOrderComplete.getValue(9));
				//#CM715938
				// Item Name
				param.setItemName(lst_RetrievalOrderComplete.getValue(10));
				//#CM715939
				// Packed qty per bundle
				param.setBundleEnteringQty(WmsFormatter.getInt(lst_RetrievalOrderComplete.getValue(11)));
				//#CM715940
				// Planned Work Piece Qty
				param.setPlanPieceQty(WmsFormatter.getInt(lst_RetrievalOrderComplete.getValue(12)));

				//#CM715941
				// Bundle ITF
				param.setBundleITF(lst_RetrievalOrderComplete.getValue(14));

				//#CM715942
				// Work No.
				param.setJobNo(CollectionUtils.getString(2, lst_RetrievalOrderComplete.getValue(0)));
				//#CM715943
				// Last update date/time
				//#CM715944
				// Translate the data type from String type (YYYYMMDDHHMMSS) into Date type. 
				Date lastupdate = WmsFormatter.getTimeStampDate(CollectionUtils.getString(3, lst_RetrievalOrderComplete.getValue(0)));
				param.setLastUpdateDate(lastupdate);
				//#CM715945
				// Terminal No.
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());

				vecParam.addElement(param);
			}
			if (vecParam.size() <= 0)
			{
				//#CM715946
				// 6023154=There is no data to update. 
				message.setMsgResourceKey("6023154");
				return;
			}

			RetrievalSupportParameter[] paramArray = new RetrievalSupportParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			WmsScheduler schedule = new RetrievalOrderCompleteSCH();
			//#CM715947
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM715948
			// Start the schedule. 
			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) schedule.startSCHgetParams(conn, paramArray);

			if (viewParam == null)
			{
				conn.rollback();
				//#CM715949
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());

			}
			else
			{
				conn.commit();
				//#CM715950
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());
				//#CM715951
				// If the target data goes out, clear the preset area. 
				btn_ListClear_Click(e);

				if (viewParam.length != 0)
				{
					//#CM715952
					// Set the listcell. 
					setList(viewParam);
				}
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
				//#CM715953
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

	//#CM715954
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
		//#CM715955
		// Set focus for the Worker Code. 
		setFocus(txt_WorkerCode);

		//#CM715956
		// Set the listcell. 
		for (int i = 1; i < lst_RetrievalOrderComplete.getMaxRows(); i++)
		{
			lst_RetrievalOrderComplete.setCurrentRow(i);
			lst_RetrievalOrderComplete.setValue(6, (""));
			lst_RetrievalOrderComplete.setValue(13, (""));

			//#CM715957
			// Clear the parameter maintained in a listcell. 
			//#CM715958
			// Shipping Case Qty + Shipping Piece Qty + Work No. + Last update date/time 
			List list = new Vector();
			list.add("");
			list.add("");
			//#CM715959
			// Work No. (ViewState) 
			list.add(this.getViewState().getString(JOBNO));
			//#CM715960
			// Last update date/time (ViewState) 
			list.add(this.getViewState().getString(LASTUPDATE));
			lst_RetrievalOrderComplete.setValue(0, CollectionUtils.getConnectedString(list));
		}
	}

	//#CM715961
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
		//#CM715962
		// Delete all lines. 
		lst_RetrievalOrderComplete.clearRow();

		//#CM715963
		// Consignor Code (read-only) 
		txt_RConsignorCode.setText("");
		//#CM715964
		// Consignor Name (read-only) 
		txt_RConsignorName.setText("");
		//#CM715965
		// Planned Picking Date (read-only) 
		txt_RRtrivlPlanDate.setText("");
		//#CM715966
		// Order No. (read-only) 
		txt_ROrderNo.setText("");

		//#CM715967
		// Disable to click a button. 
		//#CM715968
		// "Complete Picking" button 
		btn_RetrievalConplete.setEnabled(false);
		//#CM715969
		// "Clear Picking Qty" button 
		btn_RetrievalQtyClear.setEnabled(false);
		//#CM715970
		// "Clear List" button 
		btn_ListClear.setEnabled(false);

		//#CM715971
		// Set focus for the Worker Code. 
		setFocus(txt_WorkerCode);
	}
	//#CM715972
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715973
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715974
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_EndSet_Click(ActionEvent e) throws Exception
	{
	}

	//#CM715975
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715976
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715977
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715978
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715979
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715980
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM715981
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715982
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715983
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715984
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715985
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM715986
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715987
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715988
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715989
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715990
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM715991
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715992
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715993
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715994
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715995
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715996
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715997
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_OrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715998
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715999
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716000
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716001
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716002
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716003
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716004
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716005
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM716006
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716007
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM716008
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716009
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM716010
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716011
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM716012
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RtrvlFirstInput_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716013
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716014
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Change(ActionEvent e) throws Exception
	{
	}

	//#CM716015
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716016
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716017
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_RetrievalConplete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716018
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_RetrievalQtyClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716019
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716020
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716021
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716022
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716023
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716024
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716025
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716026
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716027
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716028
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716029
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDateT_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716030
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716031
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RRtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716032
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RRtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716033
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_OrderNoT_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716034
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ROrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716035
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ROrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716036
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ROrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716037
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ROrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716038
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderComplete_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716039
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderComplete_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716040
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderComplete_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716041
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderComplete_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM716042
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderComplete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716043
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderComplete_Change(ActionEvent e) throws Exception
	{
	}

	//#CM716044
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderComplete_Click(ActionEvent e) throws Exception
	{
	}


}
//#CM716045
//end of class
