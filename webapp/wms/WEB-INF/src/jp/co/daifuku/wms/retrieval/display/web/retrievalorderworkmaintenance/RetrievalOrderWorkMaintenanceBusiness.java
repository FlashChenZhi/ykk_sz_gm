// $Id: RetrievalOrderWorkMaintenanceBusiness.java,v 1.3 2007/02/07 04:19:29 suresh Exp $

//#CM717706
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalorderworkmaintenance;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.ListCellColumn;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalcustomer.ListRetrievalCustomerBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderWorkMaintenanceSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM717707
/**
 * Designer : H.Akiyama <BR>
 * Maker : H.Akiyama <BR>
 * <BR>
 * Allow this class of screen to execute maintenance of Picking Work. <BR>
 * Pass the parameter to the schedule to execute maintenance of Picking Work Maintenance. <BR>
 * Allow this screen to commit and roll back the transaction. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process by clicking "Display" button (<CODE>btn_View_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 *  Set the contents entered via screen, and
 *  allow the schedule to search for the data to be displayed, based on the parameter.<BR>
 *  Receive the search result from the schedule, and output it to the Preset area. <BR>
 *  <BR>
 *  [Parameter]  *Mandatory Input<BR>
 *  <DIR>
 *   	Worker Code* <BR>
 *   	 Password * <BR>
 *   	Consignor Code* <BR>
 *   	 Work status:* <BR>
 *   	Planned Picking Date* <BR>
 *   	Customer Code <BR>
 *   	Order No. <BR>
 *   	 Case/Piece division * <BR>
 *  </DIR>
 * 
 *  [Returned data] <BR>
 *  <DIR>
 *   	Consignor Code <BR>
 *   	Consignor Name <BR>
 *   	Planned Picking Date <BR>
 *   	Item Code <BR>
 *   	Item Name <BR>
 *   	 Case/Piece division <BR>
 *   	Case/Piece division name<BR>
 *   	Packed Qty per Case <BR>
 *   	Packed qty per bundle <BR>
 *   	Planned Work Case Qty <BR>
 *   	Planned Work Piece Qty <BR>
 *   	 Result Case Qty  <BR>
 *   	 Result Piece Qty  <BR>
 *   	 Picking Location  <BR>
 *   	Status flag <BR>
 *   	Expiry Date (result_use_by_date) <BR>
 *   	Order No. <BR>
 *   	Customer Code <BR>
 *   	Customer Name <BR>
 *   	 Result Report  <BR>
 *   	 Work place  <BR>
 * 		 <Hidden field item> <BR>
 *   	Work No. <BR>
 *   	Last update date/time <BR>
 *  </DIR>
 * </DIR>
 * 
 * <BR>
 * 2. Process by clicking Modify/Add button(<CODE>btn_ModifySubmit_Click</CODE>)  <BR>
 * <BR>
 * <DIR>
 *  Set the contents input via preset area for the parameter, and 
 *  allow the schedule to execute maintenance of the Picking Work based on the parameter.<BR>
 *  Receive true when the process normally completed, or receive false when the schedule failed to complete due to condition error or other causes.  <BR>
 *  Output the message obtained from the schedule to the screen and display the Preset area again. <BR>
 *  <BR>
 *  [Parameter]  *Mandatory Input<BR>
 *  <DIR>
 * 
 *   	Worker Code* <BR>
 *   	 Password * <BR>
 *   	Consignor Code* (for search through Preset again)  <BR>
 *   	Status flag* (for search through Preset again)  <BR>
 *   	Planned Picking Date* (for search through Preset again)  <BR>
 *   	Packed Qty per Case <BR>
 *   	 Picking Case Qty  <BR>
 *   	 Picking Piece Qty  <BR>
 *   	 Picking Location <BR>
 *   	 status <BR>
 *   	Expiry Date <BR>
 *      Packed Qty per Case<BR>
 *   	Order No. <BR>
 *   	Customer Code <BR>
 *   	 Button type * <BR>
 *   	Terminal No.*<BR>
 *   	 Case/Piece division * (for search through Preset again)  <BR>
 *   	 Preset line No. *<BR>
 * 		 <Hidden field item>  <BR>
 *   	Work No.* <BR>
 *   	Last update date/time* <BR>
 *  </DIR>
 * 
 *  [Returned data] <BR>
 *  <DIR>
 *   	Consignor Code <BR>
 *   	Consignor Name <BR>
 *   	Planned Picking Date <BR>
 *   	Item Code <BR>
 *   	Item Name <BR>
 *   	Packed Qty per Case <BR>
 *   	Packed qty per bundle <BR>
 *   	Planned Work Case Qty <BR>
 *   	Planned Work Piece Qty <BR>
 *   	 Result Case Qty  <BR>
 *   	 Result Piece Qty  <BR>
 *   	 Picking Location <BR>
 *   	Status flag <BR>
 *   	Expiry Date <BR>
 *   	 Case/Piece division <BR>
 *   	Case/Piece division name<BR>
 *   	Order No. <BR>
 *   	Customer Code <BR>
 *   	Customer Name <BR>
 *   	 Result Report  <BR>
 *   	 Work place  <BR>
 * 		 <Hidden field item>  <BR>
 *   	Work No. <BR>
 *   	Last update date/time <BR>
 *  </DIR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>H.Akiyama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:29 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderWorkMaintenanceBusiness extends RetrievalOrderWorkMaintenance implements WMSConstants
{
	//#CM717708
	// Class fields --------------------------------------------------
	//#CM717709
	/**
	 * For ViewState:Worker Code
	 */
	protected static final String WORKERCODE = "WORKER_CODE";

	//#CM717710
	/**
	 * For ViewState: Password 
	 */
	protected static final String PASSWORD = "PASSWORD";

	//#CM717711
	/**
	 * For ViewState:Consignor Code
	 */
	protected static final String CONSIGNORCODE = "CONSIGNOR_CODE";

	//#CM717712
	/**
	 * For ViewState:Consignor Name
	 */
	protected static final String CONSIGNORNAME = "CONSIGNOR_NAME";

	//#CM717713
	/**
	 * For ViewState: Work status:
	 */
	protected static final String WORKSTATUS = "WORK_STATUS";

	//#CM717714
	/**
	 * For ViewState:Planned Picking Date
	 */
	protected static final String RETRIEVALPLANDATE = "RETRIEVAL_PLAN_DATE";

	//#CM717715
	/**
	 * For ViewState:Customer Code
	 */
	protected static final String CUSTOMERCODE = "CUSTOMER_CODE";

	//#CM717716
	/**
	 * For ViewState:Order No.
	 */
	protected static final String ORDERNO = "ORDER_NO";

	//#CM717717
	/**
	 * For ViewState: Case/Piece division 
	 */
	protected static final String CASEPIECEFLAG = "CASE_PIECE_FLAG";
	
	//#CM717718
	/**
	 * For ViewState: System identification key 
	 */
	public static final String SYSTEM_DISC_KEY = "SYSTEM_DISC_KEY";
	
	//#CM717719
	/**
	 * for HIDDENField item: 0 = Picking Case Qty 
	 */
	protected static final int HIDDEN0 = 0;

	//#CM717720
	/**
	 * for HIDDENField item: 1 = Picking Piece Qty 
	 */
	protected static final int HIDDEN1 = 1;

	//#CM717721
	/**
	 * for HIDDENField item: 2 = status 
	 */
	protected static final int HIDDEN2 = 2;

	//#CM717722
	/**
	 * for HIDDENField item: 3 = Expiry Date
	 */
	protected static final int HIDDEN3 = 3;

	//#CM717723
	/**
	 * for HIDDENField item: 4 = Work No. 
	 */
	protected static final int HIDDEN4 = 4;

	//#CM717724
	/**
	 * for HIDDENField item: 5 = Last update date/time
	 */
	protected static final int HIDDEN5 = 5;
	
	//#CM717725
	/**
	 * for HIDDENField item: 6 = Work place 
	 */
	protected static final int HIDDEN6 = 6;

	//#CM717726
	// Status flag
	//#CM717727
	/**
	 * For Input area pulldown: Work Status flag "All"  = 0
	 */
	protected static final int STATUSFLAGALL = 0;

	//#CM717728
	/**
	 * For Input area pulldown: Work Status flag "Standby"  = 1
	 */
	protected static final int STATUSFLAGUNSTARTED = 1;

	//#CM717729
	/**
	 * For Input area pulldown: Work Status flag "Started"  = 2 
	 */
	protected static final int STATUSFLAGSTARTED = 2;

	//#CM717730
	/**
	 * For Input area pulldown: Work Status flag "Working" = 3 
	 */
	protected static final int STATUSFLAGWORKING = 3;

	//#CM717731
	/**
	 * For Input area pulldown: Work Completed Status flag "Completed"  = 4
	 */
	protected static final int STATUSFLAGCOMPLETION = 4;

	//#CM717732
	/**
	 * For Preset area pulldown: work status flag Standby  = 0
	 */
	protected static final String L_STATUSFLAGUNSTARTED = "0";

	//#CM717733
	/**
	 * For Preset area pulldown: work status flag Started  = 1
	 */
	protected static final String L_STATUSFLAGSTARTED = "1";

	//#CM717734
	/**
	 * For Preset area pulldown: work status flag Working  = 2
	 */
	protected static final String L_STATUSFLAGWORKING = "2";

	//#CM717735
	/**
	 * For Preset area pulldown: Work Completed Status flag Completed  = 3
	 */
	protected static final String L_STATUSFLAGCOMPLETION = "3";

	//#CM717736
	// Class variables -----------------------------------------------

	//#CM717737
	// Class method --------------------------------------------------

	//#CM717738
	// Constructors --------------------------------------------------

	//#CM717739
	// Public methods ------------------------------------------------

	//#CM717740
	/**
	 * show the Initial Display. <BR>
	 * <BR>
	 * <DIR>
	 *  Field item: name [Initial Value] 
	 *  <DIR>
	 *  Worker Code    		 [None] <BR>
	 *  Password       		 [None] <BR>
	 *  Consignor Code      		 [If there is only one Consignor code that corresponds to the condition, show the Initial Display.] <BR>
	 *  Work status:        		 [All] <BR>
	 *  Planned Picking Date      		 [None] <BR>
	 *  Customer Code      		 [None] <BR>
	 *  Order No.      		 [None] <BR>
	 *  Case/Piece division     	 [All] <BR>
	 *  "Modify/Add" button 			 [Unavailable ] <BR>
	 *  Cancel All Working button	 [Unavailable ] <BR>
	 *  "Clear List" button 		 [Unavailable ] <BR>
	 *  Cursor         		 [Worker Code] 
	 *  </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM717741
		// Set the initial value for each input field. 
		txt_WorkerCode.setText("");
		txt_Password.setText("");
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM717742
		// Work Status Pulldown: "All" 
		pul_WorkStatusRetrieval.setSelectedIndex(0);
		txt_RtrivlPlanDate.setText("");
		txt_CustomerCode.setText("");
		txt_OrderNo.setText("");
		//#CM717743
		// Case/Piece division radio box: All 
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);

		//#CM717744
		// Disable "Modify/Add" button, "Cancel All Working" button, and "Clear List" button.
		btn_ModifySubmit.setEnabled(false);
		btn_AllWorkingClear.setEnabled(false);
		btn_ListClear.setEnabled(false);

		//#CM717745
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM717746
	/**
	 * Invoke this before invoking each control event.  <BR>
	 * <BR>
	 * Summary: This method executes the following processes.<BR>
	 * <BR>
	 * <DIR>
	 *       1.Output a message before clicking a button.  <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM717747
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM717748
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM717749
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}

		//#CM717750
		// Message before clicking on "Modify/Add" button 
		//#CM717751
		// MSG-W0008=Do you modify and add it? 
		btn_ModifySubmit.setBeforeConfirm("MSG-W0033");

		//#CM717752
		// Message before clicking on Cancel All Working button 
		//#CM717753
		// MSG-W0013= Updates to Not Worked Status. 
		btn_AllWorkingClear.setBeforeConfirm("MSG-W0013");

		//#CM717754
		// Message before clicking on "Clear List" button 
		//#CM717755
		// MSG-W0012 = This clears the list input info. Do you confirm it? 
		btn_ListClear.setBeforeConfirm("MSG-W0012");

	}

	//#CM717756
	/**
	 * Returning from a popup window invokes this method.<BR>
	 * Override <CODE>page_DlgBack</CODE> defined in the <CODE>Page</CODE> class. 
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM717757
		// Obtain the parameter selected in the listbox. 
		//#CM717758
		// Consignor Code
		String consignorCode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM717759
		// Planned Picking Date
		Date retrievalPlanDate = WmsFormatter.toDate(param.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY));
		//#CM717760
		// Customer Code
		String customerCode = param.getParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY);
		//#CM717761
		// Order No.
		String orderNo = param.getParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY);

		//#CM717762
		// Set a value if not empty. 
		//#CM717763
		// Consignor Code
		if (!StringUtil.isBlank(consignorCode))
		{
			txt_ConsignorCode.setText(consignorCode);
		}
		//#CM717764
		// Planned Picking Date
		if (!StringUtil.isBlank(retrievalPlanDate))
		{
			txt_RtrivlPlanDate.setDate(retrievalPlanDate);
		}
		//#CM717765
		// Customer Code
		if (!StringUtil.isBlank(customerCode))
		{
			txt_CustomerCode.setText(customerCode);
		}
		//#CM717766
		// Order No.
		if (!StringUtil.isBlank(orderNo))
		{
			txt_OrderNo.setText(orderNo);
		}

		//#CM717767
		// Set focus for Worker Code. 
		setFocus(txt_WorkerCode);
	}

	//#CM717768
	// Package methods -----------------------------------------------

	//#CM717769
	// Protected methods ---------------------------------------------
	//#CM717770
	/**
	 * Check for input. <BR>
	 * Set the message if any error and return false. 
	 * 
	 * @param rowNo Line No. to be Checked 
	 * @return true: Normal, false: Abnormal 
	 */
	protected boolean checkContainNgText(int rowNo)
	{
		
		WmsCheckker checker = new WmsCheckker();

		lst_SRtrivlOdrWkMtn.setCurrentRow(rowNo);

		if(!checker.checkContainNgText(
				lst_SRtrivlOdrWkMtn.getValue(8) ,
				rowNo,
				lst_SRtrivlOdrWkMtn.getListCellColumn(8).getResourceKey() )
				)
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		return true;
		
	}

	//#CM717771
	// Private methods -----------------------------------------------

	//#CM717772
	// Event handler methods -----------------------------------------
	//#CM717773
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717774
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717775
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717776
	/** 
	 * Clicking on the Menu button invokes this. <BR>
	 * <BR>
	 * Summary: Shifts to the Menu screen.  
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		//#CM717777
		// Shift to the Menu screen. 
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM717778
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717779
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717780
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717781
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717782
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717783
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717784
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717785
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717786
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717787
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717788
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717789
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717790
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717791
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717792
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717793
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717794
	/** 
	 * Clicking on the Search Consignor button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for Consignor using the search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM717795
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM717796
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM717797
		// "Search" flag: Work 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM717798
		// for Order 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM717799
		// Display the Search Consignor listbox.
		redirect("/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do", forwardParam, "/progress.do");
	}

	//#CM717800
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717801
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatusRetrieval_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717802
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatusRetrieval_Change(ActionEvent e) throws Exception
	{
	}

	//#CM717803
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717804
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717805
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717806
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717807
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717808
	/** 
	 * Clicking on the Search Planned Picking Date button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Planned Shipping Date using this search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 * 	 Work status:<BR>
	 *  Planned Picking Date<BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchRtrivlPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM717809
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM717810
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM717811
		// Work status:
		//#CM717812
		// Pulldown: All 
		if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGALL)
		{
			String[] search = new String[1];
			//#CM717813
			// All 
			search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_ALL);
			forwardParam.setParameter(ListRetrievalPlanDateBusiness.WORKSTATUSRETRIEVALPLANDATE_KEY, search);
		}
		//#CM717814
		// Pulldown: Standby 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGUNSTARTED)
		{
			String[] search = new String[2];
			//#CM717815
			// StandbyPartially Completed 
			search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
			search[1] = new String(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
			forwardParam.setParameter(ListRetrievalPlanDateBusiness.WORKSTATUSRETRIEVALPLANDATE_KEY, search);
		}
		//#CM717816
		// Pulldown: "Started" 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGSTARTED)
		{
			String[] search = new String[1];
			//#CM717817
			// "Started" 
			search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_STARTED);
			forwardParam.setParameter(ListRetrievalPlanDateBusiness.WORKSTATUSRETRIEVALPLANDATE_KEY, search);
		}
		//#CM717818
		// Pulldown: Working 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGWORKING)
		{
			String[] search = new String[1];
			//#CM717819
			// Working 
			search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_WORKING);
			forwardParam.setParameter(ListRetrievalPlanDateBusiness.WORKSTATUSRETRIEVALPLANDATE_KEY, search);
		}
		//#CM717820
		// Pulldown: Completed 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGCOMPLETION)
		{
			String[] search = new String[2];
			//#CM717821
			// Completed, Partially Completed 
			search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
			search[1] = new String(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
			forwardParam.setParameter(ListRetrievalPlanDateBusiness.WORKSTATUSRETRIEVALPLANDATE_KEY, search);
		}

		//#CM717822
		// Set the Planned Picking date. 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM717823
		// "Search" flag 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM717824
		// for Order 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM717825
		// Display the listbox for searching for Planned Picking Date. 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", forwardParam, "/progress.do");
	}

	//#CM717826
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717827
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717828
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717829
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717830
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717831
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717832
	/** 
	 * Clicking on the Search Customer button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Customer using this search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 * 	 Work status:<BR>
	 *  Planned Picking Date<BR>
	 *  Customer Code<BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchCustomerCode_Click(ActionEvent e) throws Exception
	{
		//#CM717833
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM717834
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM717835
		// Pulldown: All 
		if (pul_WorkStatusRetrieval.getSelectedIndex() != STATUSFLAGALL)
		{

			//#CM717836
			// Pulldown: Standby 
			 if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGUNSTARTED)
			{
				String[] search = new String[2];
				//#CM717837
				// StandbyPartially Completed 
				search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
				search[1] = new String(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(ListRetrievalCustomerBusiness.WORKSTATUSCUSTOMER_KEY, search);
			}
			//#CM717838
			// Pulldown: "Started" 
			else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGSTARTED)
			{
				String[] search = new String[1];
				//#CM717839
				// "Started" 
				search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_STARTED);
				forwardParam.setParameter(ListRetrievalCustomerBusiness.WORKSTATUSCUSTOMER_KEY, search);
			}
			//#CM717840
			// Pulldown: Working 
			else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGWORKING)
			{
				String[] search = new String[1];
				//#CM717841
				// Working 
				search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_WORKING);
				forwardParam.setParameter(ListRetrievalCustomerBusiness.WORKSTATUSCUSTOMER_KEY, search);
			}
			//#CM717842
			// Pulldown: Completed 
			else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGCOMPLETION)
			{
				String[] search = new String[2];
				//#CM717843
				// Completed, Partially Completed 
				search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
				search[1] = new String(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(ListRetrievalCustomerBusiness.WORKSTATUSCUSTOMER_KEY, search);
			}
		}

		//#CM717844
		// Set the Planned Picking date. 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM717845
		// Set the Customer Code. 
		forwardParam.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		//#CM717846
		// "Search" flag: Work 
		forwardParam.setParameter(ListRetrievalCustomerBusiness.SEARCHCUSTOMER_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM717847
		// for Order 
		forwardParam.setParameter(ListRetrievalCustomerBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM717848
		// Display the listbox for searching for Customer. 
		redirect("/retrieval/listbox/listretrievalcustomer/ListRetrievalCustomer.do", forwardParam, "/progress.do");
	}

	//#CM717849
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_OrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717850
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717851
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717852
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717853
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717854
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717855
	/** 
	 * Clicking on the Search Order No. button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Customer using this search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 * 	 Work status:<BR>
	 *  Planned Picking Date<BR>
	 *  Customer Code<BR>
	 *  Order No.<BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchOrderNo_Click(ActionEvent e) throws Exception
	{
		//#CM717856
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM717857
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
	

		//#CM717858
		// Pulldown: All 
		if (pul_WorkStatusRetrieval.getSelectedIndex() != STATUSFLAGALL)
		{

			//#CM717859
			// Pulldown: Standby 
			if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGUNSTARTED)
			{
				String[] search = new String[2];
				//#CM717860
				// StandbyPartially Completed 
				search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
				search[1] = new String(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(ListRetrievalOrdernoBusiness.WORKSTATUSORDERNO_KEY, search);
			}
			//#CM717861
			// Pulldown: "Started" 
			else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGSTARTED)
			{
				String[] search = new String[1];
				//#CM717862
				// "Started" 
				search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_STARTED);
				forwardParam.setParameter(ListRetrievalOrdernoBusiness.WORKSTATUSORDERNO_KEY, search);
			}
			//#CM717863
			// Pulldown: Working 
			else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGWORKING)
			{
				String[] search = new String[1];
				//#CM717864
				// Working 
				search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_WORKING);
				forwardParam.setParameter(ListRetrievalOrdernoBusiness.WORKSTATUSORDERNO_KEY, search);
			}
			//#CM717865
			// Pulldown: Completed 
			else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGCOMPLETION)
			{
				String[] search = new String[2];
				//#CM717866
				// Completed, Partially Completed 
				search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
				search[1] = new String(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(ListRetrievalOrdernoBusiness.WORKSTATUSORDERNO_KEY, search);
			}
		}
	

		//#CM717867
		// Set the Planned Picking date. 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM717868
		// Set the Customer Code. 
		forwardParam.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		//#CM717869
		// Set the Order No. 
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, txt_OrderNo.getText());
		//#CM717870
		// Case/Piece flag : All 
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.CASE_PIECE_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
		//#CM717871
		// "Search" flag 
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM717872
		// for Order 
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM717873
		// Display the listbox for searching for Customer. 
		redirect("/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do", forwardParam, "/progress.do");

	}

	//#CM717874
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717875
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717876
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717877
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717878
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717879
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717880
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717881
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717882
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717883
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717884
	/** 
	 * Clicking on the Display button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the field item entered in the Input area for the parameter and starts the schedule. 
	 * Display the search result of this schedule in the preset area. <BR>
	 * This method processes as below. <BR>
	 * <BR>
	 * <DIR>
	 * 1. Check for input (Mandatory Input, number of characters, and attribution of characters) <BR>
	 * 2. Set the input field for the parameter. <BR>
	 * 3. Start the schedule. (Execute searching.) <BR>
	 * 4.Store the search conditions in ViewState. <BR>
	 * 5.Display a field item to be displayed, if any, in the preset area.<BR>
	 * </DIR>
	 * <BR>
	 * <DIR>
	 * Row No. list of listcell 
	 * <DIR>
	 * 0: Hidden field item <BR>
	 * 1: Item Code<BR>
	 * 2: Division <BR>
	 * 3: Packed Qty per Case<BR>
	 * 4: Planned Work Case Qty<BR>
	 * 5: Picking Case Qty <BR>
	 * 6: Picking Location <BR>
	 * 7: status <BR>
	 * 8: Expiry Date<BR>
	 * 9: Order No.<BR>
	 * 10: Customer Code<BR>
	 * 11: Result Report name <BR>
	 * 12: Work place 
	 * 13: Item Name<BR>
	 * 14: Packed qty per bundle<BR>
	 * 15: Planned Work Piece Qty<BR>
	 * 16: Picking Piece Qty <BR>
	 * 17: Customer Name<BR>
	 * </DIR>
	 * </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		//#CM717885
		// Disable "Modify/Add" button, "Cancel All Working" button, and "Clear List" button.
		btn_ModifySubmit.setEnabled(false);
		btn_AllWorkingClear.setEnabled(false);
		btn_ListClear.setEnabled(false);

		//#CM717886
		// Delete the Preset area. 
		listCellClear();

		//#CM717887
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);

		//#CM717888
		// Check for input. (format, mandatory, prohibited characters) 
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		pul_WorkStatusRetrieval.validate();
		txt_RtrivlPlanDate.validate();
		txt_CustomerCode.validate(false);
		txt_OrderNo.validate(false);

		Connection conn = null;

		try
		{
			//#CM717889
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM717890
			// Set the schedule parameter. 
			RetrievalSupportParameter param = new RetrievalSupportParameter();
			//#CM717891
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM717892
			// Password 
			param.setPassword(txt_Password.getText());
			//#CM717893
			// Consignor Code 
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM717894
			// Work Status: All 
			if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGALL)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_ALL);
			}
			//#CM717895
			// Work Status: Standby 
			else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGUNSTARTED)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
			}
			//#CM717896
			// Work Status: "Started" 
			else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGSTARTED)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_STARTED);
			}
			//#CM717897
			// Work Status: Working 
			else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGWORKING)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_WORKING);
			}
			//#CM717898
			// Work Status: Completed 
			else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGCOMPLETION)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
			}
			//#CM717899
			// Planned Picking Date
			param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
			//#CM717900
			// Customer Code
			param.setCustomerCode(txt_CustomerCode.getText());
			//#CM717901
			// Order No.
			param.setOrderNo(txt_OrderNo.getText());
			//#CM717902
			// Case/Piece division 
			//#CM717903
			// All 
			if (rdo_CpfAll.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
			}
			//#CM717904
			// Case 
			else if (rdo_CpfCase.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
			}
			//#CM717905
			// Piece 
			else if (rdo_CpfPiece.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
			}
			//#CM717906
			// None 
			else if (rdo_CpfAppointOff.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
			}

			//#CM717907
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalOrderWorkMaintenanceSCH();
			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) schedule.query(conn, param);

			//#CM717908
			// Close the process when some error occurred or no display data was found. 
			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM717909
			// Maintain the searched values in the ViewState. 
			setViewState(viewParam[0]);
			//#CM717910
			// Display the search result in the preset area.
			addList(viewParam);

			//#CM717911
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
				//#CM717912
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
	}

	//#CM717913
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717914
	/** 
	 * Clicking on the Clear button invokes this. <BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 *  <DIR>
	 *  Return the field items in the input area to the initial value. Disable to clear Worker Code and Password. <BR>
	 *  For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method. 
	 *  </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM717915
		// Set the initial value for each input field. 
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM717916
		// Work Status Pulldown: "All" 
		pul_WorkStatusRetrieval.setSelectedIndex(0);
		txt_RtrivlPlanDate.setText("");
		txt_CustomerCode.setText("");
		txt_OrderNo.setText("");
		//#CM717917
		// Case/Piece division radio box: All 
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);

		//#CM717918
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM717919
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ModifySubmit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717920
	/** 
	 * Clicking on Modify/Add button invokes this. <BR>
	 * This process is almost same as the process by clicking on Cancel All Working button. Execute the process using the <CODE>updateList(String)</CODE> method. <BR>
	 * Allow this method to invoke the <CODE>updateList(String)</CODE> method. <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_ModifySubmit_Click(ActionEvent e) throws Exception
	{
		updateList(RetrievalSupportParameter.BUTTON_MODIFYSUBMIT);
	}

	//#CM717921
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllWorkingClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717922
	/** 
	 * Cancel All Working button invokes this. <BR>
	 * This process is almost same as the process by clicking on Modify/Add button. Execute the process using the <CODE>updateList(String)</CODE> method. <BR>
	 * Allow this method to invoke the <CODE>updateList(String)</CODE> method. <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_AllWorkingClear_Click(ActionEvent e) throws Exception
	{
		updateList(RetrievalSupportParameter.BUTTON_ALLWORKINGCLEAR);
	}

	//#CM717923
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717924
	/** 
	 * Clicking on the Clear List button invokes this. <BR>
	 * <BR>
	 * Summary: Clears all the displayed data in the Preset area. <BR>
	 * Allow this method to execute the following processes. <BR>
	 * <BR>
	 * <DIR>
	 * 1.Clear the display of the Preset area. <BR>
	 * 2. Set the cursor on the Worker code. <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		//#CM717925
		// Delete the Preset area. 
		listCellClear();
		//#CM717926
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM717927
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717928
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717929
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717930
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717931
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717932
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717933
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717934
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717935
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717936
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDateT_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717937
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717938
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RRtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717939
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RRtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717940
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRtrivlOdrWkMtn_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717941
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRtrivlOdrWkMtn_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717942
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRtrivlOdrWkMtn_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717943
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRtrivlOdrWkMtn_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM717944
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRtrivlOdrWkMtn_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717945
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRtrivlOdrWkMtn_Change(ActionEvent e) throws Exception
	{
	}

	//#CM717946
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRtrivlOdrWkMtn_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717947
	// private method -------------------------------------------------------	
	//#CM717948
	/** 
	 * Allow this method to obtain the Consignor code from the schedule. <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule. <BR>
	 * Allow the schedule to return the corresponding Consignor if the data has only one Consignor Code. 
	 * if there are two or more data of Consignor or the count of data is 0, return null. <BR>
	 * 
	 * @throws Exception Report all exceptions. 
	 * 
	 * @return Consignor Code
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			RetrievalSupportParameter param = new RetrievalSupportParameter();

			//#CM717949
			// Obtain the Consignor code from the schedule. 
			WmsScheduler schedule = new RetrievalOrderWorkMaintenanceSCH();
			param = (RetrievalSupportParameter) schedule.initFind(conn, param);

			//#CM717950
			// For data with only one Consignor code, display the initial display. 			
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
				//#CM717951
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

	//#CM717952
	/** 
	 * Allow this method to maintain the search conditions in the ViewState. <BR>
	 * <BR>
	 * Summary: Maintains the search conditions in ViewState. <BR>
	 * <BR>
	 * ViewState key list 
	 * <DIR>
	 * WORKER_CODE: Worker Code <BR>
	 * PASSWORD: Password <BR>
	 * CONSITNOR_CODE: Consignor Code<BR>
	 * CONSITNOR_NAME: Consignor Name<BR>
	 * WORK_STATUS: work status <BR>
	 * RETRIEVAL_PLAN_DATE: Planned Picking Date <BR>
	 * CUSTOMER_CODE: Customer Code <BR>
	 * ORDER_NO: Order No. <BR>
	 * CASE_PIECE_FLAG: Case/Piece flag <BR>
	 * SYSTEM_DISC_KEY: Work place <BR>
	 * </DIR>
	 * 
	 * @param param Parameter []  Info to be displayed in the preset area.
	 * @throws Exception Report all exceptions. 
	 */
	private void setViewState(RetrievalSupportParameter param) throws Exception
	{
		//#CM717953
		// Worker Code
		this.getViewState().setString(WORKERCODE, txt_WorkerCode.getText());

		//#CM717954
		// Password 
		this.getViewState().setString(PASSWORD, txt_Password.getText());

		//#CM717955
		// Consignor Code
		this.getViewState().setString(CONSIGNORCODE, txt_ConsignorCode.getText());

		//#CM717956
		// Consignor Name
		this.getViewState().setString(CONSIGNORNAME, param.getConsignorName());

		//#CM717957
		// Work status:
		if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGALL)
		{
			//#CM717958
			// All 
			this.getViewState().setString(WORKSTATUS, RetrievalSupportParameter.STATUS_FLAG_ALL);
		}
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGUNSTARTED)
		{
			//#CM717959
			// Standby 
			this.getViewState().setString(WORKSTATUS, RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		}
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGSTARTED)
		{
			//#CM717960
			// "Started" 
			this.getViewState().setString(WORKSTATUS, RetrievalSupportParameter.STATUS_FLAG_STARTED);
		}
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGWORKING)
		{
			//#CM717961
			// Working 
			this.getViewState().setString(WORKSTATUS, RetrievalSupportParameter.STATUS_FLAG_WORKING);
		}
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGCOMPLETION)
		{
			//#CM717962
			// Completed 
			this.getViewState().setString(WORKSTATUS, RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
		}
		//#CM717963
		// Planned Picking Date
		this.getViewState().setString(RETRIEVALPLANDATE, txt_RtrivlPlanDate.getText());

		//#CM717964
		// Customer Code
		this.getViewState().setString(CUSTOMERCODE, txt_CustomerCode.getText());

		//#CM717965
		// Order No.
		this.getViewState().setString(ORDERNO, txt_OrderNo.getText());

		//#CM717966
		// Case/Piece division 
		//#CM717967
		// All 
		if (rdo_CpfAll.getChecked())
		{
			this.getViewState().setString(CASEPIECEFLAG, RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM717968
		// Case 
		else if (rdo_CpfCase.getChecked())
		{
			this.getViewState().setString(CASEPIECEFLAG, RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM717969
		// Piece 
		else if (rdo_CpfPiece.getChecked())
		{
			this.getViewState().setString(CASEPIECEFLAG, RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM717970
		// None 
		else if (rdo_CpfAppointOff.getChecked())
		{
			this.getViewState().setString(CASEPIECEFLAG, RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM717971
		// Work place 
		if (param.getSystemDiscKey() == (RetrievalSupportParameter.SYSTEM_DISC_KEY_FLOOR))
		{
			this.getViewState().setInt(SYSTEM_DISC_KEY, RetrievalSupportParameter.SYSTEM_DISC_KEY_FLOOR);
		}
		else if(param.getSystemDiscKey() == (RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS))
		{
			this.getViewState().setInt(SYSTEM_DISC_KEY, RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS);
		}

	}

	//#CM717972
	/** 
	 * Allow this method to map the search results in the preset area. <BR>
	 * <BR>
	 * Summary: Enables to display the search result obtained from the schedule, in the preset area. <BR>
	 * Display the Consignor Code, Consignor Name, Planned Shipping Date, and listcell in the preset area.<BR>
	 * <BR>
	 * Row No. list of listcell 
	 * <DIR>
	 * 0: Hidden field item <BR>
	 * 	<DIR>
	 * 		0: Picking Case Qty <BR>
	 * 		1: Picking Piece Qty <BR>
	 * 		2: status <BR>
	 * 		3: Expiry Date<BR>
	 * 		4: Work No. <BR>
	 * 		5: Last update date/time<BR>
	 * 	</DIR>
	 * 1: Item Code<BR>
	 * 2: Division <BR>
	 * 3: Packed Qty per Case<BR>
	 * 4: Planned Work Case Qty<BR>
	 * 5: Picking Case Qty <BR>
	 * 6: Picking Location <BR>
	 * 7: status <BR>
	 * 8: Expiry Date<BR>
	 * 9: Order No.<BR>
	 * 10: Customer Code<BR>
	 * 11: Result Report name <BR>
	 * 12: Work place <BR>
	 * 13: Item Name<BR>
	 * 14: Packed qty per bundle<BR>
	 * 15: Planned Work Piece Qty<BR>
	 * 16: Picking Piece Qty <BR>
	 * 17: Customer Name<BR>
	 * </DIR>
	 * 
	 * @param param Parameter []  Info to be displayed in the preset area.
	 * @throws Exception Report all exceptions. 
	 */
	private void addList(RetrievalSupportParameter[] param) throws Exception
	{
		RetrievalSupportParameter[] viewParam = param;

		//#CM717973
		// Consignor Code
		txt_RConsignorCode.setText(this.getViewState().getString(CONSIGNORCODE));
		//#CM717974
		// Consignor Name
		txt_RConsignorName.setText(this.getViewState().getString(CONSIGNORNAME));
		//#CM717975
		// Planned Picking Date
		txt_RRtrivlPlanDate.setDate(WmsFormatter.toDate(this.getViewState().getString(RETRIEVALPLANDATE), this.getHttpRequest().getLocale()));

		String label_itemname = DisplayText.getText("LBL-W0103");
		String label_usebydate = DisplayText.getText("LBL-W0270");
		String label_orderno = DisplayText.getText("LBL-W0127");
		String label_customercode = DisplayText.getText("LBL-W0034");
		String label_customername = DisplayText.getText("LBL-W0036");
		String label_reportflag = DisplayText.getText("LBL-W0389");
		String label_workercode = DisplayText.getText("LBL-W0274");
		String label_workername = DisplayText.getText("LBL-W0276");
		//#CM717976
		// Work place 
		String label_systemdisckey = DisplayText.getText("LBL-W0504");
		//#CM717977
		// LBL-W0719=----
		String noDisp = DisplayText.getText("LBL-W0719");

		//#CM717978
		// Set a value in the listcell. 
		for (int i = 0; i < viewParam.length; i++)
		{
			//#CM717979
			// Add a line in a listcell. 
			lst_SRtrivlOdrWkMtn.addRow();
			//#CM717980
			// Select the line to set a value. 
			lst_SRtrivlOdrWkMtn.setCurrentRow(lst_SRtrivlOdrWkMtn.getMaxRows() - 1);

			//#CM717981
			// Connect the Hidden field items. 
			String hidden = createHiddenList(viewParam[i]);

			//#CM717982
			// Set the search result in each cell. 
			//#CM717983
			// Hidden field item 
			lst_SRtrivlOdrWkMtn.setValue(0, hidden);
			//#CM717984
			// Item Code
			lst_SRtrivlOdrWkMtn.setValue(1, viewParam[i].getItemCode());
			//#CM717985
			// Case/Piece division 
			lst_SRtrivlOdrWkMtn.setValue(2, viewParam[i].getCasePieceflgName());
			//#CM717986
			// Packed Qty per Case
			lst_SRtrivlOdrWkMtn.setValue(3, WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));
			//#CM717987
			// Planned Work Case Qty
			lst_SRtrivlOdrWkMtn.setValue(4, WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
			//#CM717988
			// Picking Case Qty 
			lst_SRtrivlOdrWkMtn.setValue(5, WmsFormatter.getNumFormat(viewParam[i].getResultCaseQty()));
			//#CM717989
			// Picking Location  
			lst_SRtrivlOdrWkMtn.setValue(6, WmsFormatter.toDispLocation(viewParam[i].getRetrievalLocation(),
	                viewParam[i].getSystemDiscKey()));

			//#CM717990
			// status 
			if (viewParam[i].getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
			{
				//#CM717991
				// Standby 
				lst_SRtrivlOdrWkMtn.setValue(7, L_STATUSFLAGUNSTARTED);
			}
			else if (viewParam[i].getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
			{
				//#CM717992
				// "Started" 
				lst_SRtrivlOdrWkMtn.setValue(7, L_STATUSFLAGSTARTED);
			}
			else if (viewParam[i].getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
			{
				//#CM717993
				// Working 
				lst_SRtrivlOdrWkMtn.setValue(7, L_STATUSFLAGWORKING);
			}
			else if (viewParam[i].getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
			{
				//#CM717994
				// Completed 
				lst_SRtrivlOdrWkMtn.setValue(7, L_STATUSFLAGCOMPLETION);
			}

			//#CM717995
			// Expiry Date
			lst_SRtrivlOdrWkMtn.setValue(8, viewParam[i].getUseByDate());
			//#CM717996
			// Order No.
			lst_SRtrivlOdrWkMtn.setValue(9, viewParam[i].getOrderNo());
			//#CM717997
			// Customer Code
			lst_SRtrivlOdrWkMtn.setValue(10, viewParam[i].getCustomerCode());
			//#CM717998
			// Result Report 
			lst_SRtrivlOdrWkMtn.setValue(11, viewParam[i].getReportFlagName());
			//#CM717999
			// Work place 
			lst_SRtrivlOdrWkMtn.setValue(12, viewParam[i].getSystemDiscKeyName());	
			//#CM718000
			// Item Name
			lst_SRtrivlOdrWkMtn.setValue(13, viewParam[i].getItemName());
			//#CM718001
			// Packed qty per bundle
			lst_SRtrivlOdrWkMtn.setValue(14, WmsFormatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
			//#CM718002
			// Planned Work Piece Qty
			lst_SRtrivlOdrWkMtn.setValue(15, WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
			//#CM718003
			// Picking Piece Qty 
			lst_SRtrivlOdrWkMtn.setValue(16, WmsFormatter.getNumFormat(viewParam[i].getResultPieceQty()));
			//#CM718004
			// Customer Name
			lst_SRtrivlOdrWkMtn.setValue(17, viewParam[i].getCustomerName());

			//#CM718005
			// Set the tool tip. 
			ToolTipHelper toolTip = new ToolTipHelper();
			//#CM718006
			// Item Name
			if (StringUtil.isBlank(viewParam[i].getItemName()))
			{
				toolTip.add(label_itemname,"");
			}
			else
			{
				toolTip.add(label_itemname, viewParam[i].getItemName());
			}
			//#CM718007
			// Expiry Date
			if (StringUtil.isBlank(viewParam[i].getUseByDate()))
			{
				toolTip.add(label_usebydate,"");
			}
			else
			{
				toolTip.add(label_usebydate, viewParam[i].getUseByDate());
			}
			//#CM718008
			// Order No.
			if (StringUtil.isBlank(viewParam[i].getOrderNo()))
			{
				toolTip.add(label_orderno,"");
			}
			else
			{
				toolTip.add(label_orderno, viewParam[i].getOrderNo());
			}
			
			//#CM718009
			// Customer Code
			if (StringUtil.isBlank(viewParam[i].getCustomerCode()))
			{
				toolTip.add(label_customercode,"");
			}
			else
			{
				toolTip.add(label_customercode, viewParam[i].getCustomerCode());
			}
			
			//#CM718010
			// Customer Name
			if (StringUtil.isBlank(viewParam[i].getCustomerName()))
			{
				toolTip.add(label_customername,"");
			}
			else
			{
				toolTip.add(label_customername, viewParam[i].getCustomerName());
			}
			
			//#CM718011
			// Result Report 
			if (StringUtil.isBlank(viewParam[i].getReportFlagName()))
			{
				toolTip.add(label_reportflag, "");
			}
			else
			{
				toolTip.add(label_reportflag, viewParam[i].getReportFlagName());
			}
			
			//#CM718012
			// Worker Code
			//#CM718013
			// Worker Name
			if (viewParam[i].getSystemDiscKey() == RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS)
			{
				toolTip.add(label_workercode, noDisp);
				toolTip.add(label_workername, noDisp);
			}
			else
			{
				if (StringUtil.isBlank(viewParam[i].getWorkerCode()))
				{
					toolTip.add(label_workercode,"");
				}
				else
				{
					toolTip.add(label_workercode, viewParam[i].getWorkerCode());
				}
				if (StringUtil.isBlank(viewParam[i].getWorkerName()))
				{
					toolTip.add(label_workername,"");
				}
				else
				{
					toolTip.add(label_workername, viewParam[i].getWorkerName());
				}
			}
			//#CM718014
			// Work place 
			toolTip.add(label_systemdisckey, viewParam[i].getSystemDiscKeyName());
			lst_SRtrivlOdrWkMtn.setToolTip(lst_SRtrivlOdrWkMtn.getCurrentRow(), toolTip.getText());

		}
		//#CM718015
		// Enable Modify/Add button, Cancel All Working button, and Clear List button. 
		btn_ModifySubmit.setEnabled(true);
		btn_AllWorkingClear.setEnabled(true);
		btn_ListClear.setEnabled(true);
	}

	//#CM718016
	/**
	 * Allow this method to concatenate HIDDEN field items into a single string. <BR>
	 * <BR>
	 * HIDDEN field item order list 
	 * <DIR>
	 * 		0: Picking Case Qty <BR>
	 * 		1: Picking Piece Qty <BR>
	 * 		2: status <BR>
	 * 		3: Expiry Date<BR>
	 * 		4: Work No. <BR>
	 * 		5: Last update date/time<BR>
	 * </DIR>
	 * 
	 * @param viewParam RetrievalSupportParameter <CODE>Parameter</CODE> class that contains HIDDENField item.
	 * @return Connected hidden field item 
	 */
	private String createHiddenList(RetrievalSupportParameter viewParam)
	{
		String hidden = null;

		//#CM718017
		// Generate a string of Hidden field item.  
		ArrayList hiddenList = new ArrayList();
		//#CM718018
		// Picking Case Qty 
		hiddenList.add(HIDDEN0, WmsFormatter.getNumFormat(viewParam.getResultCaseQty()));
		//#CM718019
		// Picking Piece Qty 
		hiddenList.add(HIDDEN1, WmsFormatter.getNumFormat(viewParam.getResultPieceQty()));
		//#CM718020
		// status 
		if (viewParam.getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
		{
			//#CM718021
			// Standby 
			hiddenList.add(HIDDEN2, L_STATUSFLAGUNSTARTED);
		}
		else if (viewParam.getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
		{
			//#CM718022
			// "Started" 
			hiddenList.add(HIDDEN2, L_STATUSFLAGSTARTED);
		}
		else if (viewParam.getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
		{
			//#CM718023
			// Working 
			hiddenList.add(HIDDEN2, L_STATUSFLAGWORKING);
		}
		else if (viewParam.getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
		{
			//#CM718024
			// Completed 
			hiddenList.add(HIDDEN2, L_STATUSFLAGCOMPLETION);
		}
		//#CM718025
		// Expiry Date
		hiddenList.add(HIDDEN3, viewParam.getUseByDate());
		//#CM718026
		// Work No.
		hiddenList.add(HIDDEN4, viewParam.getJobNo());
		//#CM718027
		// Last update date/time
		hiddenList.add(HIDDEN5, WmsFormatter.getTimeStampString(viewParam.getLastUpdateDate()));
		//#CM718028
		// Work place 
		hiddenList.add(HIDDEN6, new Integer(viewParam.getSystemDiscKey()));
		//#CM718029
		// Connect the Hidden field items. 
		hidden = CollectionUtils.getConnectedString(hiddenList);

		return hidden;
	}

	//#CM718030
	/** 
	 * Clicking on Modify/Add button or Cancel All Working button invokes this. <BR>
	 * <BR>
	 * Summary: Updatew the target data only on the lines of Preset area. <BR>
	 * Set the info necessary to update the data maintained in the preset area, and start the schedule. <BR>
	 * If the schedule normally completed, display again, in the preset area, the search result obtained using the same conditions as ones used when clicking on the Display button. <BR>
	 * Display a message in the message area if not completed successfully. <BR>
	 * Disable to update any data in the Preset area. Display them as they are. <BR>
	 * Allow this method to commit and roll back the transaction. <BR>
	 * Throw as it is in the event of <CODE>NumberFormatException</CODE>, and display the error. <BR>
	 * <BR>
	 * Allow this method to execute the following processes. <BR>
	 * <DIR>
	 * 1. Check the input field items (Mandatory, Character type, and prohibited characters) <BR>
	 * 2. Check for presence of the target data to update. <BR>
	 * 3. Set the info to be updated for the parameter and start the schedule. <BR>
	 * 4. Commit or roll-back the transaction. <BR>
	 * 5. Display the Preset area. <BR>
	 * </DIR>
	 * <BR>
	 * Update the parameter list.(* Mandatory Input)<BR>
	 * <DIR>
	 *   	Worker Code* <BR>
	 *   	 Password * <BR>
	 *   	Consignor Code* (for search through Preset again)  <BR>
	 *   	Status flag* (for search through Preset again)  <BR>
	 *   	Planned Picking Date* (for search through Preset again)  <BR>
	 *   	Packed Qty per Case <BR>
	 *   	 Picking Case Qty  <BR>
	 *   	 Picking Piece Qty  <BR>
	 *   	 Picking Location <BR>
	 *   	 status <BR>
	 *   	Expiry Date <BR>
	 *      Packed Qty per Case<BR>
	 *   	Order No. <BR>
	 *   	Customer Code <BR>
	 *   	 Button type * <BR>
	 *   	Terminal No.*<BR>
	 *   	 Case/Piece division * (for search through Preset again)  <BR>
	 *   	 Preset line No. *<BR>
	 * 		 <Hidden field item>  <BR>
	 *   	Work No.* <BR>
	 *   	Last update date/time* <BR>
	 * </DIR>
	 * 
	 * @param pButtonType String Type of the clicked button 
	 * @throws Exception Report all exceptions. <BR>
	 */
	private void updateList(String pButtonType) throws Exception
	{
		//#CM718031
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);

		//#CM718032
		// Check for input of Worker Code and password. 
		txt_WorkerCode.validate();
		txt_Password.validate();

		Connection conn = null;

		try
		{
			//#CM718033
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM718034
			// Obtain the title of the listcell and store it in the ListCellColumn. 
			ArrayList lst = (ArrayList) lst_SRtrivlOdrWkMtn.getListCellColumns();
			ListCellColumn[] List_Title = new ListCellColumn[lst.size() + 1];
			for (int l = 0; l < lst.size(); l++)
			{
				ListCellColumn listtemp = (ListCellColumn) lst.get(l);
				List_Title[l + 1] = new ListCellColumn();
				List_Title[l + 1] = (ListCellColumn) listtemp;
			}

			//#CM718035
			// Check the elements of the preset, and set values in the Vector of the lines only to be updated. 
			Vector vecParam = new Vector(lst_SRtrivlOdrWkMtn.getMaxRows());
			for (int i = 1; i < lst_SRtrivlOdrWkMtn.getMaxRows(); i++)
			{
				//#CM718036
				// Select a line to work. 
				lst_SRtrivlOdrWkMtn.setCurrentRow(i);

				//#CM718037
				// Check for input in the preset area. (Picking Case qty, Status, Expiry Date, and Picking Piece Qty) 
				//#CM718038
				// Picking Case Qty 
				lst_SRtrivlOdrWkMtn.validate(5, false);
				//#CM718039
				// status 
				lst_SRtrivlOdrWkMtn.validate(7, true);
				//#CM718040
				// Expiry Date
				lst_SRtrivlOdrWkMtn.validate(8, false);
				//#CM718041
				// Picking Piece Qty 
				lst_SRtrivlOdrWkMtn.validate(16, false);

				//#CM718042
				// Check the input characters for eWareNavi. 
				if (!checkContainNgText(i))
				{
					return;
				}

				//#CM718043
				// If the data is target to update, set a value in Vector to execute the process. 
				if (isChangeData(pButtonType))
				{
					RetrievalSupportParameter param = new RetrievalSupportParameter();

					//#CM718044
					// Set the Worker Code and the Password. 
					param.setWorkerCode(txt_WorkerCode.getText());
					param.setPassword(txt_Password.getText());

					//#CM718045
					// Set Search condition for repeated search
					//#CM718046
					// (Consignor Code, work status, Planned Picking Date, Item Code, and Case/Piece division) 
					//#CM718047
					// Consignor Code
					param.setConsignorCode(this.getViewState().getString(CONSIGNORCODE));
					//#CM718048
					// Work status:
					param.setWorkStatus(this.getViewState().getString(WORKSTATUS));
					//#CM718049
					// Planned Picking Date
					param.setRetrievalPlanDate(WmsFormatter.toParamDate(this.getViewState().getString(RETRIEVALPLANDATE), this.getHttpRequest().getLocale()));
					//#CM718050
					// Customer Code
					param.setCustomerCode(this.getViewState().getString(CUSTOMERCODE));
					//#CM718051
					// Order No.
					param.setOrderNo(this.getViewState().getString(ORDERNO));
					//#CM718052
					// Case/Piece division 
					param.setCasePieceflg(this.getViewState().getString(CASEPIECEFLAG));

					//#CM718053
					// Set the a value for update. 
					//#CM718054
					// (Picking Case Qty, Picking Piece Qty, Picking Location status, Expiry Date, Packed Qty per Case, Clicked button, Preset line, and Terminal No.) 
					//#CM718055
					// Picking Case Qty 	
					param.setResultCaseQty(WmsFormatter.getInt(lst_SRtrivlOdrWkMtn.getValue(5)));
					//#CM718056
					// Picking Piece Qty 
					param.setResultPieceQty(WmsFormatter.getInt(lst_SRtrivlOdrWkMtn.getValue(16)));
					//#CM718057
					// status 
					//#CM718058
					// status : Standby 
					if (lst_SRtrivlOdrWkMtn.getValue(7).equals(L_STATUSFLAGUNSTARTED))
					{
						param.setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
					}
					//#CM718059
					// status : "Started" 
					else if (lst_SRtrivlOdrWkMtn.getValue(7).equals(L_STATUSFLAGSTARTED))
					{
						param.setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_STARTED);
					}
					//#CM718060
					// status : Working 
					else if (lst_SRtrivlOdrWkMtn.getValue(7).equals(L_STATUSFLAGWORKING))
					{
						param.setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_WORKING);
					}
					//#CM718061
					// status : Completed 
					else if (lst_SRtrivlOdrWkMtn.getValue(7).equals(L_STATUSFLAGCOMPLETION))
					{
						param.setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
					}
					//#CM718062
					// Expiry Date
					param.setUseByDate(lst_SRtrivlOdrWkMtn.getValue(8));
					//#CM718063
					// Packed Qty per Case
					param.setEnteringQty(WmsFormatter.getInt(lst_SRtrivlOdrWkMtn.getValue(3)));
					//#CM718064
					// Button for clicking 
					param.setButtonType(pButtonType);
					//#CM718065
					// Preset line 
					param.setRowNo(lst_SRtrivlOdrWkMtn.getCurrentRow());
					//#CM718066
					// Terminal No.
					UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
					param.setTerminalNumber(userHandler.getTerminalNo());

					//#CM718067
					// Set the Hidden field item. 
					//#CM718068
					// (Work No. (4), Last update date/time (5)) 
					String hidden = lst_SRtrivlOdrWkMtn.getValue(0);
					param.setJobNo(CollectionUtils.getString(HIDDEN4, hidden));
					param.setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(HIDDEN5, hidden)));
					param.setSystemDiscKeyName(CollectionUtils.getString(HIDDEN6, hidden));
					vecParam.addElement(param);
				}
			}

			//#CM718069
			// Close the process if the number of element equal to 0: "No target data to update"
			if (vecParam.size() == 0)
			{
				message.setMsgResourceKey("6023154");
				return;
			}

			//#CM718070
			// Copy the value to the parameter. 
			RetrievalSupportParameter[] paramArray = new RetrievalSupportParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			//#CM718071
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalOrderWorkMaintenanceSCH();
			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) schedule.startSCHgetParams(conn, paramArray);

			//#CM718072
			// If the returned data is null, determin that the update failed. 
			//#CM718073
			// Roll-back and set a message. (with the preset area that keeps displaying the former data) 
			if (viewParam == null)
			{
				//#CM718074
				// Roll-back 
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM718075
			// Update is successful if the number of elements of the returned data is 0 or more. 
			//#CM718076
			// Commit the transaction and display the preset area again. 
			else if (viewParam.length >= 0)
			{
				//#CM718077
				// Commit 
				conn.commit();
				//#CM718078
				// Display the data if obtained the display data when the schedule normally completed. 
				listCellClear();
				if (viewParam.length > 0)
				{
					//#CM718079
					// Display the Preset area again. 
					addList(viewParam);
				}
			}

			//#CM718080
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
				//#CM718081
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

	//#CM718082
	/** 
	 * Allow this method to check that the input info in the preset area is the target to update. <BR>
	 * <BR>
	 * Summary: Examines whether the info input in the preset area is the target to update or not, based on the type of the clicked button. 
	 * Return true if it is an update target, or false if it is not an update target <BR>
	 * 
	 * @param pButtonType Type of the clicked button 
	 * @throws Exception Report all exceptions. 
	 * @return true if it is an update target, or false if it is not an update target 
	 */
	private boolean isChangeData(String pButtonType) throws Exception
	{
		//#CM718083
		// Clicking on "Update/Add" button: 
		if (pButtonType.equals(RetrievalSupportParameter.BUTTON_MODIFYSUBMIT))
		{
			//#CM718084
			// Obtain the Hidden field item. 
			String hidden = lst_SRtrivlOdrWkMtn.getValue(0);
			//#CM718085
			// Picking Case Qty 
			String orgCaseQty = CollectionUtils.getString(HIDDEN0, hidden);
			//#CM718086
			// Picking Piece Qty 
			String orgPieceQty = CollectionUtils.getString(HIDDEN1, hidden);
			//#CM718087
			// status 
			String orgStatus = CollectionUtils.getString(HIDDEN2, hidden);
			//#CM718088
			// Expiry Date
			String orgUseByDate = CollectionUtils.getString(HIDDEN3, hidden);

			//#CM718089
			// Disable to process data in which input text has not been changed at all. 
			//#CM718090
			// Picking Case Qty 
			if (!lst_SRtrivlOdrWkMtn.getValue(5).equals(orgCaseQty))
			{
				return true;
			}
			//#CM718091
			// status 
			else if (!lst_SRtrivlOdrWkMtn.getValue(7).equals(orgStatus))
			{
				return true;
			}
			//#CM718092
			// Expiry Date
			//#CM718093
			// Do not regard the status as "Modified" except for status other than "completed". 
			else if (lst_SRtrivlOdrWkMtn.getValue(7).equals(L_STATUSFLAGCOMPLETION)
			 && !lst_SRtrivlOdrWkMtn.getValue(8).equals(orgUseByDate))
			{
				return true;
			}
			//#CM718094
			// Picking Piece Qty 
			else if (!lst_SRtrivlOdrWkMtn.getValue(16).equals(orgPieceQty))
			{
				return true;
			}
			//#CM718095
			// If nothing has been changed: 
			else
			{
				return false;
			}
		}
		//#CM718096
		// Clicking on Cancel All Working button: 
		else
		{
			//#CM718097
			// Obtain the Hidden field item. 
			String hidden = lst_SRtrivlOdrWkMtn.getValue(0);
			String orgStatus = CollectionUtils.getString(HIDDEN2, hidden);

			//#CM718098
			// If the data status of initial display is "Standby" or "Completed", disable to execute process.
			if (orgStatus.equals(L_STATUSFLAGUNSTARTED) || orgStatus.equals(L_STATUSFLAGCOMPLETION))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}
	//#CM718099
	/** 
	 * Allow this method to clear the preset area. <BR>
	 * <BR>
	 * Summary: Clears listcells and read-only field items in the Preset area and disable to click on buttons in the preset area. 
	 * Return true if it is an update target, or false if it is not an update target <BR>
	 * 
	 * @throws Exception Report all exceptions. 
	 */
	private void listCellClear() throws Exception
	{
		//#CM718100
		// Delete the Preset area. 
		//#CM718101
		// Delete the read-only field item. 
		txt_RConsignorCode.setText("");
		txt_RConsignorName.setText("");
		txt_RRtrivlPlanDate.setText("");

		//#CM718102
		// Delete the Preset area. 
		lst_SRtrivlOdrWkMtn.clearRow();

		//#CM718103
		// Disable "Modify/Add" button, "Cancel All Working" button, and "Clear List" button.
		btn_ModifySubmit.setEnabled(false);
		btn_AllWorkingClear.setEnabled(false);
		btn_ListClear.setEnabled(false);
	}
	//#CM718104
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Maintenance_Click(ActionEvent e) throws Exception
	{
	}


}
//#CM718105
//end of class
