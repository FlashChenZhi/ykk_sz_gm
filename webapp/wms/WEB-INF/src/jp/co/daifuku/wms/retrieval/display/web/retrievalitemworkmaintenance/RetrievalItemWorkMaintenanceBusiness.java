// $Id: RetrievalItemWorkMaintenanceBusiness.java,v 1.3 2007/02/07 04:19:19 suresh Exp $

//#CM715302
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalitemworkmaintenance;
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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalItemWorkMaintenanceSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM715303
/**
 * Designer : K.Mukai <BR>
 * Maker : K.Mukai<BR>
 * <BR>
 * Allow this class of screen to execute maintenance of Item Picking Work Maintenance. <BR>
 * Pass the parameter to the schedule to execute maintenance of Picking Work Maintenance. <BR>
 * Allow this screen to commit and roll back the transaction. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process by clicking "Display" button (<CODE>btn_View_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 *  Set the contents entered via screen, and
 *   allow the schedule to search for the data to be displayed, based on the parameter.<BR>
 *  Receive the search result from the schedule, and output it to the Preset area. <BR>
 *  <BR>
 *  [Parameter]  *Mandatory Input<BR>
 *  <DIR>
 *   	Worker Code* <BR>
 *   	 Password * <BR>
 *   	Consignor Code* <BR>
 *   	 Work status:* <BR>
 *   	Planned Picking Date* <BR>
 *   	Item Code <BR>
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
 *   	Packed Qty per Case <BR>
 *   	Packed qty per bundle <BR>
 *   	Planned Work Case Qty <BR>
 *   	Planned Work Piece Qty <BR>
 *   	 Picking Case Qty  <BR>
 *   	 Picking Piece Qty  <BR>
 *   	 Picking Location  <BR>
 *   	Status flag <BR>
 *   	Expiry Date (result_use_by_date) <BR>
 *   	 Case/Piece division <BR>
 *   	Case/Piece division name<BR>
 *      Result Report <BR>
 *      Work place <BR>
 *   	Worker Code <BR>
 *   	Worker Name <BR>
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
 *   allow the schedule to execute maintenance of the Picking Work based on the parameter.<BR>
 *  Receive true when the process normally completed, or receive false when the schedule failed to complete due to condition error or other causes.  <BR>
 *  Output the message obtained from the schedule to the screen and display the Preset area again. <BR>
 *  <BR>
 *  [Parameter]  *Mandatory Input<BR>
 *  <DIR>
 *   	Worker Code* <BR>
 *   	 Password * <BR>
 *   	Consignor Code* (for search through Preset again)  <BR>
 *   	Status flag* (for search through Preset again)  <BR>
 *   	Planned Picking Date* (for search through Preset again)  <BR>
 * 		Item Code (for search through Preset again)  <BR>
 *   	Packed Qty per Case <BR>
 *   	 Picking Case Qty  <BR>
 *   	 Picking Piece Qty  <BR>
 *   	 Picking Location <BR>
 *   	 status <BR>
 *   	Expiry Date <BR>
 *   	 Button type * <BR>
 *   	Terminal No.*<BR>
 *   	 Case/Piece division * (for search through Preset again)  <BR>
 *   	 Preset line No. * <BR>
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
 *   	 Picking Case Qty  <BR>
 *   	 Picking Piece Qty  <BR>
 *   	 Picking Location <BR>
 *   	Status flag <BR>
 *   	Expiry Date <BR>
 *   	 Case/Piece division  <BR>
 *   	Case/Piece division name <BR>
 *   	Worker Code <BR>
 *   	Worker Name <BR>
 *   	Work No. <BR>
 *   	Last update date/time <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/25</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:19 $
 * @author  $Author: suresh $
 */
public class RetrievalItemWorkMaintenanceBusiness extends RetrievalItemWorkMaintenance implements WMSConstants
{
	//#CM715304
	/**
	 * For ViewState:Worker Code
	 */
	protected static final String WORKERCODE = "WORKER_CODE";

	//#CM715305
	/**
	 * For ViewState: Password 
	 */
	protected static final String PASSWORD = "PASSWORD";

	//#CM715306
	/**
	 * For ViewState:Consignor Code
	 */
	protected static final String CONSIGNORCODE = "CONSIGNOR_CODE";
	
	//#CM715307
	/**
	 * For ViewState:Consignor Name
	 */
	protected static final String CONSIGNORNAME = "CONSIGNOR_NAME";
	
	//#CM715308
	/**
	 * For ViewState: Work status:
	 */
	protected static final String WORKSTATUS = "WORK_STATUS";

	//#CM715309
	/**
	 * For ViewState:Planned Picking Date
	 */
	protected static final String RETRIEVALPLANDATE = "RETRIEVAL_PLAN_DATE";

	//#CM715310
	/**
	 * For ViewState:Item Code
	 */
	protected static final String ITEMCODE = "ITEM_CODE";

	//#CM715311
	/**
	 * For ViewState: Case/Piece division 
	 */
	protected static final String CASEPIECEFLAG = "CASE_PIECE_FLAG";

	//#CM715312
	/**
	 * For ViewState: System identification key 
	 */
	public static final String SYSTEM_DISC_KEY = "SYSTEM_DISC_KEY";
	
	//#CM715313
	/**
	 * for HIDDENField item: 0 = Picking Case Qty 
	 */
	protected static final int HIDDEN0 = 0;

	//#CM715314
	/**
	 * for HIDDENField item: 1 = Picking Piece Qty 
	 */
	protected static final int HIDDEN1 = 1;

	//#CM715315
	/**
	 * for HIDDENField item: 2 = status 
	 */
	protected static final int HIDDEN2 = 2;

	//#CM715316
	/**
	 * for HIDDENField item: 3 = Expiry Date
	 */
	protected static final int HIDDEN3 = 3;

	//#CM715317
	/**
	 * for HIDDENField item: 4 = Work No. 
	 */
	protected static final int HIDDEN4 = 4;

	//#CM715318
	/**
	 * for HIDDENField item: 5 = Last update date/time
	 */
	protected static final int HIDDEN5 = 5;
	
	//#CM715319
	/**
	 * for HIDDENField item: 6 = Work place 
	 */
	protected static final int HIDDEN6 = 6;
	
	//#CM715320
	// Status flag
	//#CM715321
	/**
	 * For Input area pulldown: Work Status flag "All"  = 0
	 */
	protected static final int STATUSFLAGALL = 0;

	//#CM715322
	/**
	 * For Input area pulldown: Work Status flag "Standby"  = 1
	 */
	protected static final int STATUSFLAGUNSTARTED = 1;

	//#CM715323
	/**
	 * For Input area pulldown: Work Status flag "Started"  = 2 
	 */
	protected static final int STATUSFLAGSTARTED = 2;

	//#CM715324
	/**
	 * For Input area pulldown: Work Status flag "Working" = 3 
	 */
	protected static final int STATUSFLAGWORKING = 3;

	//#CM715325
	/**
	 * For Input area pulldown: Work Completed Status flag "Completed"  = 4
	 */
	protected static final int STATUSFLAGCOMPLETION = 4;

	//#CM715326
	/**
	 * For Preset area pulldown: work status flag Standby  = 0
	 */
	protected static final String L_STATUSFLAGUNSTARTED = "0";

	//#CM715327
	/**
	 * For Preset area pulldown: work status flag Started  = 1
	 */
	protected static final String L_STATUSFLAGSTARTED = "1";

	//#CM715328
	/**
	 * For Preset area pulldown: work status flag Working  = 2
	 */
	protected static final String L_STATUSFLAGWORKING = "2";

	//#CM715329
	/**
	 * For Preset area pulldown: Work Completed Status flag Completed  = 3
	 */
	protected static final String L_STATUSFLAGCOMPLETION = "3";

	//#CM715330
	// Class fields --------------------------------------------------
	
	//#CM715331
	// Class variables -----------------------------------------------

	//#CM715332
	// Class method --------------------------------------------------

	//#CM715333
	// Constructors --------------------------------------------------

	//#CM715334
	// Public methods ------------------------------------------------

	//#CM715335
	/**
	 * show the Initial Display. <BR>
	 * <BR>
	 * <DIR>
	 *  Field item: name [Initial Value] 
	 *  <DIR>
	 *  Worker Code    		 [None]  <BR>
	 *  Password       		 [None]  <BR>
	 *  Consignor Code      		 [If there is only one Consignor code that corresponds to the condition, show the Initial Display.]  <BR>
	 *  Work status:        		 [All]  <BR>
	 *  Planned Picking Date      		 [None]  <BR>
	 *  Item Code				 [None]  <BR>
	 *  Case/Piece division     	 [All]  <BR>
	 *  "Modify/Add" button 			 [Unavailable ]  <BR>
	 *  Cancel All Working button	 [Unavailable ]  <BR>
	 *  "Clear List" button 		 [Unavailable ]  <BR>
	 *  Cursor         		 [Worker Code]  <BR>
	 *  </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM715336
		// Set the initial value for each input field. 
		txt_WorkerCode.setText("");
		txt_Password.setText("");
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM715337
		// Work Status Pulldown: "All" 
		pul_WorkStatusRetrieval.setSelectedIndex(0);
		txt_RtrivlPlanDate.setText("");
		txt_ItemCode.setText("");
		//#CM715338
		// Case/Piece division radio box: All 
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);

		//#CM715339
		// Disable "Modify/Add" button, "Cancel All Working" button, and "Clear List" button.
		btn_ModifySubmit.setEnabled(false);
		btn_AllWorkingClear.setEnabled(false);
		btn_ListClear.setEnabled(false);
	}

	//#CM715340
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
			//#CM715341
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM715342
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM715343
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}

		//#CM715344
		// Message before clicking on "Modify/Add" button 
		//#CM715345
		// MSG-W0008=Do you modify and add it? 
		btn_ModifySubmit.setBeforeConfirm("MSG-W0033");

		//#CM715346
		// Message before clicking on Cancel All Working button 
		//#CM715347
		// MSG-W0013= Updates to Not Worked Status. 
		btn_AllWorkingClear.setBeforeConfirm("MSG-W0013");

		//#CM715348
		// Message before clicking on "Clear List" button 
		//#CM715349
		// MSG-W0012 = This clears the list input info. Do you confirm it? 
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		//#CM715350
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM715351
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
		//#CM715352
		// Obtain the parameter selected in the listbox. 
		//#CM715353
		// Consignor Code
		String consignorCode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM715354
		// Planned Picking Date
		Date retrievalPlanDate = WmsFormatter.toDate(param.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY));
		//#CM715355
		// Item Code
		String itemCode = param.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);

		//#CM715356
		// Set a value if not empty. 
		//#CM715357
		// Consignor Code
		if (!StringUtil.isBlank(consignorCode))
		{
			txt_ConsignorCode.setText(consignorCode);
		}
		//#CM715358
		// Planned Picking Date
		if (!StringUtil.isBlank(retrievalPlanDate))
		{
			txt_RtrivlPlanDate.setDate(retrievalPlanDate);
		}
		//#CM715359
		// Item Code
		if (!StringUtil.isBlank(itemCode))
		{
			txt_ItemCode.setText(itemCode);
		}
	}

	//#CM715360
	// Package methods -----------------------------------------------

	//#CM715361
	// Protected methods ---------------------------------------------
	//#CM715362
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

		lst_SRtrivlItemWkMtn.setCurrentRow(rowNo);

		if(!checker.checkContainNgText(
				lst_SRtrivlItemWkMtn.getValue(8) ,
				rowNo,
				lst_SRtrivlItemWkMtn.getListCellColumn(8).getResourceKey() )
				)
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		return true;
		
	}

	//#CM715363
	// Private methods -----------------------------------------------

	//#CM715364
	/** 
	 * Allow this method to obtain the Consignor code from the schedule. <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule. <BR>
	 * Allow the schedule to return the corresponding Consignor if the data has only one Consignor Code. <BR>
	 * if there are two or more data of Consignor or the count of data is 0, return null. 
	 * 
	 * @throws Exception Report all exceptions. 
	 * @return Consignor Code
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			RetrievalSupportParameter param = new RetrievalSupportParameter();

			//#CM715365
			// Obtain the Consignor code from the schedule. 
			WmsScheduler schedule = new RetrievalItemWorkMaintenanceSCH();
			param = (RetrievalSupportParameter) schedule.initFind(conn, param);

			//#CM715366
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
				//#CM715367
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

	//#CM715368
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
	 * ITEMCODE: Item Code <BR>
	 * CASE_PIECE_FLAG: Case/Piece flag <BR>
	 * SYSTEM_DISC_KEY: Work place 
	 * </DIR>
	 * 
	 * @param param Parameter []  Info to be displayed in the preset area.
	 * @throws Exception Report all exceptions. 
	 */
	private void setViewState(RetrievalSupportParameter param) throws Exception
	{
		//#CM715369
		// Worker Code
		this.getViewState().setString(WORKERCODE, txt_WorkerCode.getText());

		//#CM715370
		// Password 
		this.getViewState().setString(PASSWORD, txt_Password.getText());

		//#CM715371
		// Consignor Code
		this.getViewState().setString(CONSIGNORCODE, txt_ConsignorCode.getText());
		//#CM715372
		// Consignor Name
		this.getViewState().setString(CONSIGNORNAME, param.getConsignorName());
		//#CM715373
		// Work status:
		if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGALL)
		{
			//#CM715374
			// All 
			this.getViewState().setString(WORKSTATUS, RetrievalSupportParameter.STATUS_FLAG_ALL);
		}
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGUNSTARTED)
		{
			//#CM715375
			// Standby 
			this.getViewState().setString(WORKSTATUS, RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		}
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGSTARTED)
		{
			//#CM715376
			// "Started" 
			this.getViewState().setString(WORKSTATUS, RetrievalSupportParameter.STATUS_FLAG_STARTED);
		}
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGWORKING)
		{
			//#CM715377
			// Working 
			this.getViewState().setString(WORKSTATUS, RetrievalSupportParameter.STATUS_FLAG_WORKING);
		}
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGCOMPLETION)
		{
			//#CM715378
			// Completed 
			this.getViewState().setString(WORKSTATUS, RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
		}
		//#CM715379
		// Planned Picking Date
		this.getViewState().setString(RETRIEVALPLANDATE, txt_RtrivlPlanDate.getText());

		//#CM715380
		// Item Code
		this.getViewState().setString(ITEMCODE, txt_ItemCode.getText());

		//#CM715381
		// Case/Piece division 
		//#CM715382
		// All 
		if (rdo_CpfAll.getChecked())
		{
			this.getViewState().setString(CASEPIECEFLAG, RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM715383
		// Case 
		else if (rdo_CpfCase.getChecked())
		{
			this.getViewState().setString(CASEPIECEFLAG, RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM715384
		// Piece 
		else if (rdo_CpfPiece.getChecked())
		{
			this.getViewState().setString(CASEPIECEFLAG, RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM715385
		// None 
		else if (rdo_CpfAppointOff.getChecked())
		{
			this.getViewState().setString(CASEPIECEFLAG, RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		
		//#CM715386
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

	//#CM715387
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
	 * 9: Result Report name <BR>
	 * 10: Item Name<BR>
	 * 11: Packed qty per bundle<BR>
	 * 12: Planned Work Piece Qty<BR>
	 * 13: Picking Piece Qty <BR>
	 * </DIR>
	 * 
	 * @param param Parameter []  Info to be displayed in the preset area.
	 * @throws Exception Report all exceptions. 
	 */
	private void addList(RetrievalSupportParameter[] param) throws Exception
	{
		RetrievalSupportParameter[] viewParam = param;

		//#CM715388
		// Consignor Code
		txt_RConsignorCode.setText(this.getViewState().getString(CONSIGNORCODE));
		//#CM715389
		// Consignor Name
		txt_RConsignorName.setText(this.getViewState().getString(CONSIGNORNAME));
		//#CM715390
		// Planned Picking Date
		txt_RRtrivlPlanDate.setDate(WmsFormatter.toDate(this.getViewState().getString(RETRIEVALPLANDATE), this.getHttpRequest().getLocale()));


		String label_itemname = DisplayText.getText("LBL-W0103");
		String label_usebydate = DisplayText.getText("LBL-W0270");
        String label_reportflag = DisplayText.getText("LBL-W0389");
		String label_workercode = DisplayText.getText("LBL-W0274");
		String label_workername = DisplayText.getText("LBL-W0276");
		//#CM715391
		// Work place 
		String label_systemdisckey = DisplayText.getText("LBL-W0504");

		//#CM715392
		// LBL-W0719=----
		String noDisp = DisplayText.getText("LBL-W0719");

		//#CM715393
		// Set a value in the listcell. 
		for (int i = 0; i < viewParam.length; i++)
		{
			//#CM715394
			// Add a line in a listcell. 
			lst_SRtrivlItemWkMtn.addRow();
			//#CM715395
			// Select the line to set a value. 
			lst_SRtrivlItemWkMtn.setCurrentRow(lst_SRtrivlItemWkMtn.getMaxRows() - 1);

			//#CM715396
			// Connect the Hidden field items. 
			String hidden = createHiddenList(viewParam[i]);

			//#CM715397
			// Set the search result in each cell. 
			//#CM715398
			// Hidden field item 
			lst_SRtrivlItemWkMtn.setValue(0, hidden);
			//#CM715399
			// Item Code
			lst_SRtrivlItemWkMtn.setValue(1, viewParam[i].getItemCode());
			//#CM715400
			// Case/Piece division 
			lst_SRtrivlItemWkMtn.setValue(2, viewParam[i].getCasePieceflgName());
			//#CM715401
			// Packed Qty per Case
			lst_SRtrivlItemWkMtn.setValue(3, WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));
			//#CM715402
			// Planned Work Case Qty
			lst_SRtrivlItemWkMtn.setValue(4, WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
			//#CM715403
			// Picking Case Qty 
			lst_SRtrivlItemWkMtn.setValue(5, WmsFormatter.getNumFormat(viewParam[i].getResultCaseQty()));
			//#CM715404
			// Picking Location 
			lst_SRtrivlItemWkMtn.setValue(6, WmsFormatter.toDispLocation(viewParam[i].getRetrievalLocation(),
	                viewParam[i].getSystemDiscKey()));

			//#CM715405
			// status 
			if (viewParam[i].getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
			{
				//#CM715406
				// Standby 
				lst_SRtrivlItemWkMtn.setValue(7, L_STATUSFLAGUNSTARTED);
			}
			else if (viewParam[i].getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
			{
				//#CM715407
				// "Started" 
				lst_SRtrivlItemWkMtn.setValue(7, L_STATUSFLAGSTARTED);
			}
			else if (viewParam[i].getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
			{
				//#CM715408
				// Working 
				lst_SRtrivlItemWkMtn.setValue(7, L_STATUSFLAGWORKING);
			}
			else if (viewParam[i].getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
			{
				//#CM715409
				// Completed 
				lst_SRtrivlItemWkMtn.setValue(7, L_STATUSFLAGCOMPLETION);
			}

			//#CM715410
			// Expiry Date
			lst_SRtrivlItemWkMtn.setValue(8, viewParam[i].getUseByDate());
            //#CM715411
            // Result Report 
			lst_SRtrivlItemWkMtn.setValue(9, viewParam[i].getReportFlagName());

			//#CM715412
			// Work place 
			lst_SRtrivlItemWkMtn.setValue(10, viewParam[i].getSystemDiscKeyName());	
			//#CM715413
			// Item Name
			lst_SRtrivlItemWkMtn.setValue(11, viewParam[i].getItemName());
			//#CM715414
			// Packed qty per bundle
			lst_SRtrivlItemWkMtn.setValue(12, WmsFormatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
			//#CM715415
			// Planned Work Piece Qty
			lst_SRtrivlItemWkMtn.setValue(13, WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
			//#CM715416
			// Picking Piece Qty 
			lst_SRtrivlItemWkMtn.setValue(14, WmsFormatter.getNumFormat(viewParam[i].getResultPieceQty()));

			//#CM715417
			// Set the tool tip. 
			ToolTipHelper toolTip = new ToolTipHelper();
			//#CM715418
			// Item Name
			if (StringUtil.isBlank(viewParam[i].getItemName()))
			{
				toolTip.add(label_itemname, "");
			}
			else
			{
				toolTip.add(label_itemname, viewParam[i].getItemName());
			}
			
			//#CM715419
			// Expiry Date
			if (StringUtil.isBlank(viewParam[i].getUseByDate()))
			{
				toolTip.add(label_usebydate, "");
			}
			else
			{
				toolTip.add(label_usebydate, viewParam[i].getUseByDate());
			}
			
			//#CM715420
			// Result Report 
			if (StringUtil.isBlank(viewParam[i].getReportFlagName()))
			{
				toolTip.add(label_reportflag, "");
			}
			else
			{
				toolTip.add(label_reportflag, viewParam[i].getReportFlagName());
			}
			
			//#CM715421
			// Worker Code
			//#CM715422
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
					toolTip.add(label_workercode, "");
				}
				else
				{
					toolTip.add(label_workercode, viewParam[i].getWorkerCode());
				}
				if (StringUtil.isBlank(viewParam[i].getWorkerName()))
				{
					toolTip.add(label_workername, "");
				}
				else
				{
					toolTip.add(label_workername, viewParam[i].getWorkerName());
				}
			}
			//#CM715423
			// Work place 
			toolTip.add(label_systemdisckey, viewParam[i].getSystemDiscKeyName());
			lst_SRtrivlItemWkMtn.setToolTip(lst_SRtrivlItemWkMtn.getCurrentRow(), toolTip.getText());

		}
		//#CM715424
		// Enable Modify/Add button, Cancel All Working button, and Clear List button. 
		btn_ModifySubmit.setEnabled(true);
		btn_AllWorkingClear.setEnabled(true);
		btn_ListClear.setEnabled(true);
	}

	//#CM715425
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
	 *      6: Work place 
	 * </DIR>
	 * 
	 * @param viewParam RetrievalSupportParameter <CODE>Parameter</CODE> class that contains HIDDENField item.
	 * @return Connected hidden field item 
	 */
	private String createHiddenList(RetrievalSupportParameter viewParam)
	{
		String hidden = null;

		//#CM715426
		// Generate a string of Hidden field item.  
		ArrayList hiddenList = new ArrayList();
		//#CM715427
		// Picking Case Qty 
		hiddenList.add(HIDDEN0, WmsFormatter.getNumFormat(viewParam.getResultCaseQty()));
		//#CM715428
		// Picking Piece Qty 
		hiddenList.add(HIDDEN1, WmsFormatter.getNumFormat(viewParam.getResultPieceQty()));
		//#CM715429
		// status 
		if (viewParam.getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
		{
			//#CM715430
			// Standby 
			hiddenList.add(HIDDEN2, L_STATUSFLAGUNSTARTED);
		}
		else if (viewParam.getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
		{
			//#CM715431
			// "Started" 
			hiddenList.add(HIDDEN2, L_STATUSFLAGSTARTED);
		}
		else if (viewParam.getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
		{
			//#CM715432
			// Working 
			hiddenList.add(HIDDEN2, L_STATUSFLAGWORKING);
		}
		else if (viewParam.getStatusFlagL().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
		{
			//#CM715433
			// Completed 
			hiddenList.add(HIDDEN2, L_STATUSFLAGCOMPLETION);
		}
		//#CM715434
		// Expiry Date
		hiddenList.add(HIDDEN3, viewParam.getUseByDate());
		//#CM715435
		// Work No.
		hiddenList.add(HIDDEN4, viewParam.getJobNo());
		//#CM715436
		// Last update date/time
		hiddenList.add(HIDDEN5, WmsFormatter.getTimeStampString(viewParam.getLastUpdateDate()));
		//#CM715437
		// Work place 
		hiddenList.add(HIDDEN6, new Integer(viewParam.getSystemDiscKey()));

		//#CM715438
		// Connect the Hidden field items. 
		hidden = CollectionUtils.getConnectedString(hiddenList);

		return hidden;
	}

	//#CM715439
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
	 *      Result Report <BR>
	 *      Work place <BR>
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
		//#CM715440
		// Check for input of Worker Code and password. 
		txt_WorkerCode.validate();
		txt_Password.validate();

		Connection conn = null;

		try
		{
			//#CM715441
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM715442
			// Obtain the title of the listcell and store it in the ListCellColumn. 
			ArrayList lst = (ArrayList) lst_SRtrivlItemWkMtn.getListCellColumns();
			ListCellColumn[] List_Title = new ListCellColumn[lst.size() + 1];
			for (int l = 0; l < lst.size(); l++)
			{
				ListCellColumn listtemp = (ListCellColumn) lst.get(l);
				List_Title[l + 1] = new ListCellColumn();
				List_Title[l + 1] = (ListCellColumn) listtemp;
			}

			//#CM715443
			// Obtain the Terminal No. 
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			String terminalNo = userHandler.getTerminalNo();
			
			//#CM715444
			// Check the elements of the preset, and set values in the Vector of the lines only to be updated. 
			Vector vecParam = new Vector(lst_SRtrivlItemWkMtn.getMaxRows());
			for (int i = 1; i < lst_SRtrivlItemWkMtn.getMaxRows(); i++)
			{
				//#CM715445
				// Select a line to work. 
				lst_SRtrivlItemWkMtn.setCurrentRow(i);

				//#CM715446
				// Check for input in the preset area. (Picking Case qty, Status, Expiry Date, and Picking Piece Qty) 
				//#CM715447
				// Picking Case Qty 
				lst_SRtrivlItemWkMtn.validate(5, false);
				//#CM715448
				// status 
				lst_SRtrivlItemWkMtn.validate(7, true);
				//#CM715449
				// Expiry Date
				lst_SRtrivlItemWkMtn.validate(8, false);
				//#CM715450
				// Picking Piece Qty 
				lst_SRtrivlItemWkMtn.validate(14, false);

				//#CM715451
				// Check the input characters for eWareNavi. 
				if (!checkContainNgText(i))
				{
					return;
				}

				//#CM715452
				// If the data is target to update, set a value in Vector to execute the process. 
				if (isChangeData(pButtonType))
				{
					RetrievalSupportParameter param = new RetrievalSupportParameter();

					//#CM715453
					// Set the Worker Code and the Password. 
					param.setWorkerCode(txt_WorkerCode.getText());
					param.setPassword(txt_Password.getText());

					//#CM715454
					// Set Search condition for repeated search
					//#CM715455
					// (Consignor Code, work status, Planned Picking Date, Item Code, and Case/Piece division) 
					//#CM715456
					// Consignor Code
					param.setConsignorCode(this.getViewState().getString(CONSIGNORCODE));
					//#CM715457
					// Work status:
					param.setWorkStatus(this.getViewState().getString(WORKSTATUS));
					//#CM715458
					// Planned Picking Date
					param.setRetrievalPlanDate(WmsFormatter.toParamDate(this.getViewState().getString(RETRIEVALPLANDATE), this.getHttpRequest().getLocale()));
					//#CM715459
					// Item Code
					param.setItemCode(this.getViewState().getString(ITEMCODE));
					//#CM715460
					// Case/Piece division 
					param.setCasePieceflg(this.getViewState().getString(CASEPIECEFLAG));

					//#CM715461
					// Set the a value for update. 
					//#CM715462
					// (Picking Case Qty, Picking Piece Qty, Picking Location status, Expiry Date, Packed Qty per Case, Clicked button, Preset line, and Terminal No.) 
					//#CM715463
					// Picking Case Qty 	
					param.setResultCaseQty(WmsFormatter.getInt(lst_SRtrivlItemWkMtn.getValue(5)));
					//#CM715464
					// Picking Piece Qty 
					param.setResultPieceQty(WmsFormatter.getInt(lst_SRtrivlItemWkMtn.getValue(14)));
					//#CM715465
					// status 
					//#CM715466
					// status : Standby 
					if (lst_SRtrivlItemWkMtn.getValue(7).equals(L_STATUSFLAGUNSTARTED))
					{
						param.setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
					}
					//#CM715467
					// status : "Started" 
					else if (lst_SRtrivlItemWkMtn.getValue(7).equals(L_STATUSFLAGSTARTED))
					{
						param.setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_STARTED);
					}
					//#CM715468
					// status : Working 
					else if (lst_SRtrivlItemWkMtn.getValue(7).equals(L_STATUSFLAGWORKING))
					{
						param.setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_WORKING);
					}
					//#CM715469
					// status : Completed 
					else if (lst_SRtrivlItemWkMtn.getValue(7).equals(L_STATUSFLAGCOMPLETION))
					{
						param.setStatusFlagL(RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
					}
					//#CM715470
					// Expiry Date
					param.setUseByDate(lst_SRtrivlItemWkMtn.getValue(8));
					//#CM715471
					// Packed Qty per Case
					param.setEnteringQty(WmsFormatter.getInt(lst_SRtrivlItemWkMtn.getValue(3)));
					//#CM715472
					// Button for clicking 
					param.setButtonType(pButtonType);
					//#CM715473
					// Preset line 
					param.setRowNo(lst_SRtrivlItemWkMtn.getCurrentRow());
					//#CM715474
					// Terminal No.
					param.setTerminalNumber(terminalNo);

					//#CM715475
					// Set the Hidden field item. 
					//#CM715476
					// (Work No. (4), Last update date/time (5)) 
					String hidden = lst_SRtrivlItemWkMtn.getValue(0);
					param.setJobNo(CollectionUtils.getString(HIDDEN4, hidden));
					param.setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(HIDDEN5, hidden)));
					param.setSystemDiscKeyName(CollectionUtils.getString(HIDDEN6, hidden));
					vecParam.addElement(param);
				}
			}

			//#CM715477
			// Close the process if the number of element equal to 0: "No target data to update"
			if (vecParam.size() == 0)
			{
				message.setMsgResourceKey("6023154");
				return;
			}

			//#CM715478
			// Copy the value to the parameter. 
			RetrievalSupportParameter[] paramArray = new RetrievalSupportParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			//#CM715479
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalItemWorkMaintenanceSCH();
			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) schedule.startSCHgetParams(conn, paramArray);

			//#CM715480
			// If the returned data is null, determin that the update failed. 
			//#CM715481
			// Roll-back and set a message. (with the preset area that keeps displaying the former data) 
			if (viewParam == null)
			{
				//#CM715482
				// Roll-back 
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM715483
			// Update is successful if the number of elements of the returned data is 0 or more. 
			//#CM715484
			// Commit the transaction and display the preset area again. 
			else if (viewParam.length >= 0)
			{
				//#CM715485
				// Commit 
				conn.commit();
				//#CM715486
				// Display the data if obtained the display data when the schedule normally completed. 
				listCellClear();
				if (viewParam.length > 0)
				{
					//#CM715487
					// Display the Preset area again. 
					addList(viewParam);
				}
			}

			//#CM715488
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
				//#CM715489
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

	//#CM715490
	/** 
	 * Allow this method to check that the input info in the preset area is the target to update. <BR>
	 * <BR>
	 * Summary: Examines whether the info input in the preset area is the target to update or not, based on the type of the clicked button. 
	 * Return true if it is an update target, or false if it is not an update target <BR>
	 * 
	 * @param pButtonType Type of the clicked button 
	 * @throws Exception Report all exceptions. 
	 */
	private boolean isChangeData(String pButtonType) throws Exception
	{
		//#CM715491
		// Clicking on "Update/Add" button: 
		if (pButtonType.equals(RetrievalSupportParameter.BUTTON_MODIFYSUBMIT))
		{
			//#CM715492
			// Obtain the Hidden field item. 
			String hidden = lst_SRtrivlItemWkMtn.getValue(0);
			//#CM715493
			// Picking Case Qty 
			String orgCaseQty = CollectionUtils.getString(HIDDEN0, hidden);
			//#CM715494
			// Picking Piece Qty 
			String orgPieceQty = CollectionUtils.getString(HIDDEN1, hidden);
			//#CM715495
			// status 
			String orgStatus = CollectionUtils.getString(HIDDEN2, hidden);
			//#CM715496
			// Expiry Date
			String orgUseByDate = CollectionUtils.getString(HIDDEN3, hidden);

			//#CM715497
			// Disable to process data in which input text has not been changed at all. 
			//#CM715498
			// Picking Case Qty 
			if (!lst_SRtrivlItemWkMtn.getValue(5).equals(orgCaseQty))
			{
				return true;
			}
			//#CM715499
			// status 
			else if (!lst_SRtrivlItemWkMtn.getValue(7).equals(orgStatus))
			{
				return true;
			}
			//#CM715500
			// Expiry Date
			//#CM715501
			// Do not regard the status as "Modified" except for status other than "completed". 
			else if (lst_SRtrivlItemWkMtn.getValue(7).equals(L_STATUSFLAGCOMPLETION)
			 && !lst_SRtrivlItemWkMtn.getValue(8).equals(orgUseByDate))
			{
				return true;
			}
			//#CM715502
			// Picking Piece Qty 
			else if (!lst_SRtrivlItemWkMtn.getValue(14).equals(orgPieceQty))
			{
				return true;
			}
			//#CM715503
			// If nothing has been changed: 
			else
			{
				return false;
			}
		}
		//#CM715504
		// Clicking on Cancel All Working button: 
		else
		{
			//#CM715505
			// Obtain the Hidden field item. 
			String hidden = lst_SRtrivlItemWkMtn.getValue(0);
			String orgStatus = CollectionUtils.getString(HIDDEN2, hidden);

			//#CM715506
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
	//#CM715507
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
		//#CM715508
		// Delete the Preset area. 
		//#CM715509
		// Delete the read-only field item. 
		txt_RConsignorCode.setText("");
		txt_RConsignorName.setText("");
		txt_RRtrivlPlanDate.setText("");

		//#CM715510
		// Delete the Preset area. 
		lst_SRtrivlItemWkMtn.clearRow();

		//#CM715511
		// Disable "Modify/Add" button, "Cancel All Working" button, and "Clear List" button.
		btn_ModifySubmit.setEnabled(false);
		btn_AllWorkingClear.setEnabled(false);
		btn_ListClear.setEnabled(false);
	}
	//#CM715512
	// Event handler methods -----------------------------------------
	//#CM715513
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
		//#CM715514
		// Shift to the Menu screen. 
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM715515
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
		//#CM715516
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM715517
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM715518
		// "Search" flag: Work 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM715519
		// For Item 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM715520
		// Display the Search Consignor listbox.
		redirect("/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do", forwardParam, "/progress.do");
	}

	//#CM715521
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
	public void btn_PSearchRetrievalPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM715522
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM715523
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM715524
		// Work status:
		String[] search = null;
		Vector statusVec = new Vector();

		//#CM715525
		// Pulldown: All 
		if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGALL)
		{
			//#CM715526
			// All 
			statusVec.add(RetrievalSupportParameter.STATUS_FLAG_ALL);
		}
		//#CM715527
		// Pulldown: Standby 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGUNSTARTED)
		{
			//#CM715528
			// StandbyPartially Completed 
			statusVec.add(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		}
		//#CM715529
		// Pulldown: "Started" 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGSTARTED)
		{
			//#CM715530
			// "Started" 
			statusVec.add(RetrievalSupportParameter.STATUS_FLAG_STARTED);
		}
		//#CM715531
		// Pulldown: Working 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGWORKING)
		{
			//#CM715532
			// Working 
			statusVec.add(RetrievalSupportParameter.STATUS_FLAG_WORKING);
		}
		//#CM715533
		// Pulldown: Completed 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGCOMPLETION)
		{
			//#CM715534
			// Completed, Partially Completed 
			statusVec.add(RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
		}
		
		search = new String[statusVec.size()];
		statusVec.copyInto(search);

		forwardParam.setParameter(ListRetrievalPlanDateBusiness.WORKSTATUSRETRIEVALPLANDATE_KEY, search);

		//#CM715535
		// Set the Planned Picking date. 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM715536
		// "Search" flag 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM715537
		// For Item 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM715538
		// Display the listbox for searching for Planned Picking Date. 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", forwardParam, "/progress.do");
	}

	//#CM715539
	/** 
	 * Clicking on the Search Item Code button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for Item using the search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 * 	 Work status:<BR>
	 *  Planned Picking Date<BR>
	 *  Item Code<BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM715540
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM715541
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM715542
		// Work status:
		String[] search = null;
		Vector searchVec = new Vector();

		//#CM715543
		// Pulldown: All 
		if (pul_WorkStatusRetrieval.getSelectedIndex() != STATUSFLAGALL)
		{

			//#CM715544
			// Pulldown: Standby 
			if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGUNSTARTED)
			{
				//#CM715545
				// Standby 
				searchVec.add(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
			}
			//#CM715546
			// Pulldown: "Started" 
			else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGSTARTED)
			{
				//#CM715547
				// "Started" 
				searchVec.add(RetrievalSupportParameter.STATUS_FLAG_STARTED);
			}
			//#CM715548
			// Pulldown: Working 
			else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGWORKING)
			{
				//#CM715549
				// Working 
				searchVec.add(RetrievalSupportParameter.STATUS_FLAG_WORKING);
			}
			//#CM715550
			// Pulldown: Completed 
			else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGCOMPLETION)
			{
				//#CM715551
				// Completed 
				searchVec.add(RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
			}
		}
		
		search = new String[searchVec.size()];
		searchVec.copyInto(search);

		forwardParam.setParameter(ListRetrievalItemBusiness.WORKSTATUSITEM_KEY, search);

		//#CM715552
		// Set the Planned Picking date. 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM715553
		// Set the Item Code. 
		forwardParam.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM715554
		// "Search" flag: Work 
		forwardParam.setParameter(ListRetrievalItemBusiness.SEARCHITEM_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM715555
		// For Item 
		forwardParam.setParameter(ListRetrievalItemBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM715556
		// Display the listbox for searching for Customer. 
		redirect("/retrieval/listbox/listretrievalitem/ListRetrievalItem.do", forwardParam, "/progress.do");
	}

	//#CM715557
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
	 * 9: Result Report name <BR>
	 * 10: Work place <BR>
	 * 11: Item Name<BR>
	 * 12: Packed qty per bundle<BR>
	 * 13: Planned Work Piece Qty<BR>
	 * 14: Picking Piece Qty <BR>
	 * </DIR>
	 * </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		//#CM715558
		// Disable "Modify/Add" button, "Cancel All Working" button, and "Clear List" button.
		btn_ModifySubmit.setEnabled(false);
		btn_AllWorkingClear.setEnabled(false);
		btn_ListClear.setEnabled(false);

		//#CM715559
		// Delete the Preset area. 
		listCellClear();

		//#CM715560
		// Check for input. (format, mandatory, prohibited characters) 
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		pul_WorkStatusRetrieval.validate();
		txt_RtrivlPlanDate.validate();
		txt_ItemCode.validate(false);

		Connection conn = null;

		try
		{
			//#CM715561
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			//#CM715562
			// Set the schedule parameter. 
			RetrievalSupportParameter param = new RetrievalSupportParameter();
			//#CM715563
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM715564
			// Password 
			param.setPassword(txt_Password.getText());
			//#CM715565
			// Consignor Code 
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM715566
			// Work Status: All 
			if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGALL)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_ALL);
			}
			//#CM715567
			// Work Status: Standby 
			else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGUNSTARTED)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
			}
			//#CM715568
			// Work Status: "Started" 
			else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGSTARTED)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_STARTED);
			}
			//#CM715569
			// Work Status: Working 
			else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGWORKING)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_WORKING);
			}
			//#CM715570
			// Work Status: Completed 
			else if (pul_WorkStatusRetrieval.getSelectedIndex() == STATUSFLAGCOMPLETION)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
			}
			//#CM715571
			// Planned Picking Date
			param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
			//#CM715572
			// Customer Code
			param.setItemCode(txt_ItemCode.getText());
			//#CM715573
			// Case/Piece division 
			//#CM715574
			// All 
			if (rdo_CpfAll.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
			}
			//#CM715575
			// Case 
			else if (rdo_CpfCase.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
			}
			//#CM715576
			// Piece 
			else if (rdo_CpfPiece.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
			}
			//#CM715577
			// None 
			else if (rdo_CpfAppointOff.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
			}

			//#CM715578
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalItemWorkMaintenanceSCH();
			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) schedule.query(conn, param);

			//#CM715579
			// Close the process when some error occurred or no display data was found. 
			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM715580
			// Maintain the searched values in the ViewState. 
			setViewState(viewParam[0]);
			//#CM715581
			// Display the search result in the preset area.
			addList(viewParam);

			//#CM715582
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
				//#CM715583
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

	//#CM715584
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
		//#CM715585
		// Set the initial value for each input field. 
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM715586
		// Work Status Pulldown: "All" 
		pul_WorkStatusRetrieval.setSelectedIndex(0);
		txt_RtrivlPlanDate.setText("");
		txt_ItemCode.setText("");
		//#CM715587
		// Case/Piece division radio box: All 
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);
	}

	//#CM715588
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

	//#CM715589
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

	//#CM715590
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
		//#CM715591
		// Delete the Preset area. 
		listCellClear();

		//#CM715592
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

}
//#CM715593
//end of class
