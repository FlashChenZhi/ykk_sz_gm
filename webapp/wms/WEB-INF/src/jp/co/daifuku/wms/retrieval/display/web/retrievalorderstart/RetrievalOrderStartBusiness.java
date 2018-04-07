// $Id: RetrievalOrderStartBusiness.java,v 1.2 2007/02/07 04:19:27 suresh Exp $

//#CM717373
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalorderstart;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.Formatter;
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
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderStartSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM717374
/**
 * Designer : M.Inoue <BR>
 * Maker : M.Inoue <BR>
 * <BR>
 * Allow this class of screen to set starting Order Picking via listing work. <BR>
 * Pass the parameter to the schedule to set starting Order Picking. <BR>
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
 *   Order No. <BR>
 *   Case/Piece division * <BR>
 *   <BR>
 *   [Data for Output]  <BR>
 *   <BR>
 *   Consignor Code <BR>
 *   Consignor Name <BR>
 *   Planned Picking Date <BR>
 *   Order No. <BR>
 *   Division  <BR>
 *   Customer Code <BR>
 *   Customer Name <BR>
 *   Work No. [] <BR>
 *   Last update date/time [] <BR>
 * </DIR>
 * <BR>
 * 2. Process by clicking on "Start Picking" button (<CODE>btn_RetrivalStart_Click()</CODE> method)  <BR>
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
 *   Planned Picking Date <BR>
 *   Order No. <BR>
 *   Case/Piece division (Work Type) <BR>
 *   Last update date/time [] <BR>
 *   Work No. [] <BR>
 *   Print type of Order Picking report <BR>
 *   Line No<BR>
 *   <BR>
 *   [Data for Output]  <BR>
 *   <BR>
 *   Consignor Code <BR>
 *   Consignor Name <BR>
 *   Planned Picking Date <BR>
 *   Order No. <BR>
 *   Division  <BR>
 *   Customer Code <BR>
 *   Customer Name <BR>
 *   Work No. [] <BR>
 *   Last update date/time [] <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>M.Inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:27 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderStartBusiness extends RetrievalOrderStart implements WMSConstants
{
	//#CM717375
	// Class fields --------------------------------------------------
	//#CM717376
	// Hidden Parameter 
	//#CM717377
	// Work No. (Number of elements of array) 
	private static final int HDNARYJOBNO = 0;
	//#CM717378
	// Last update date/time (Number of elements of array) 
	private static final int HDNARYLASTUPDATEDATE = 1;
	//#CM717379
	// Work No.
	private static final int HDNJOBNO = 2;
	//#CM717380
	// Last update date/time
	private static final int HDNLASTUPDATEDATE = 3;
	
	//#CM717381
	// for ViewState 
	//#CM717382
	// Consignor Code
	private static final String VSTCONSIGNOR = "CONSIGNOR_CODE";
	//#CM717383
	// Planned Picking Date
	private static final String VSTPLANDATE = "PLAN_DATE";
	//#CM717384
	// Order No.
	private static final String VSTORDERNO = "ORDER_NO";
	//#CM717385
	// Case/Piece division
	private static final String VSTCASEPIECE = "CASE_PIECE_FLG";
	
	
	
	//#CM717386
	// Class variables -----------------------------------------------

	//#CM717387
	// Class method --------------------------------------------------

	//#CM717388
	// Constructors --------------------------------------------------

	//#CM717389
	// Public methods ------------------------------------------------

	//#CM717390
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Summary: shows the Initial Display. <BR>
	 * <BR>
	 * <DIR>
	 *    1. Initialize the input area. <BR>
	 *    2. Start the schedule.  <BR>
	 *    3.Disable the Start Picking button, Select All button, Cancel All Selected button, and Clear List button.  <BR>
	 *    4.Set the cursor on the Work Code. <BR>
	 * </DIR>
	 * <BR>
	 * Field item [Initial Value]  <BR>
	 * <BR>
	 * Worker Code		 [None]  <BR>
	 * Password 		 [None]  <BR>
	 * Consignor Code		 [Search Value]  <BR>
	 * Planned Picking Date   	 [None]  <BR>
	 * Order No.		 [None]  <BR>
	 * Case/Piece division 	 [All]  <BR>
	 * Print the Order Picking report. [true]  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM717391
			// Disable to click a button. 
			setBtnTrueFalse(false);
			
			//#CM717392
			// Set the Case/Piece division to All. 
			rdo_CpfAll.setChecked(true);
			
			//#CM717393
			// Place a check on Print a planned retrieval work report . 
			chk_CommonUse.setChecked(true);

			//#CM717394
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalOrderStartSCH();
			RetrievalSupportParameter param = (RetrievalSupportParameter) schedule.initFind(conn, null);

			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				//#CM717395
				// For data with only one Consignor code, display the initial display. 
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
				//#CM717396
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
	
	//#CM717397
	/**
	 * Invoke this before invoking each control event. <BR>
	 * <BR>
	 * Summary: Displays a dialog. <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM717398
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM717399
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM717400
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
		//#CM717401
		// Dialog before clicking on Start Picking button to confirm: MSG-W0019= Do you start it? 
		btn_RetrivalStart.setBeforeConfirm("MSG-W0019");

		//#CM717402
		// Clicking the Clear List button displays a dialog box to allow user to confirm: MSG-W0012= Input info in the list is cleared. Do you confirm it? 
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		//#CM717403
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM717404
	/**
	 * Returning from a popup window invokes this method.
	 * Override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE>. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM717405
		// Obtain the parameter selected in the listbox. 
		//#CM717406
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM717407
		// Planned Picking Date
		String retPlan = param.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM717408
		// Order No.
		String orderNo = param.getParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY);

		//#CM717409
		// Set a value if not empty. 
		//#CM717410
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM717411
		// Planned Picking Date
		if (!StringUtil.isBlank(retPlan))
		{
			txt_RtrivlPlanDate.setDate(WmsFormatter.toDate(retPlan));
		}
		//#CM717412
		// Order No.
		if (!StringUtil.isBlank(orderNo))
		{
			txt_OrderNo.setText(orderNo);
		}
	}

	//#CM717413
	// Package methods -----------------------------------------------

	//#CM717414
	// Protected methods ---------------------------------------------

	//#CM717415
	// Private methods -----------------------------------------------
	//#CM717416
	/**
	 * Set the value for the preset area. 
	 * @param listParams Parameter object that contains info to be displayed in the preset area.  
	 * @param chk
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(Parameter[] listParams, boolean chk) throws Exception
	{
		//#CM717417
		// Delete all lines. 
		lst_RetrivalOrderStart.clearRow();
		if (listParams == null || listParams.length <= 0)
		{
			return ;
		}
		RetrievalSupportParameter param = (RetrievalSupportParameter) listParams[0];

		//#CM717418
		// Consignor Code (read-only) 
		txt_RConsignorCode.setText(param.getConsignorCode());
		//#CM717419
		// Consignor Name (read-only) 
		txt_RConsignorName.setText(param.getConsignorName());
		//#CM717420
		// Planned Picking Date (read-only) 
		txt_RRtrivlPlanDate.setText(WmsFormatter.toDispDate(param.getRetrievalPlanDate(),this.httpRequest.getLocale()));

		RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) listParams;

		for (int i = 0; i < viewParam.length; i++)
		{
			//#CM717421
			// Add a line. 
			lst_RetrivalOrderStart.setCurrentRow(i + 1);
			lst_RetrivalOrderStart.addRow();
			
			lst_RetrivalOrderStart.setValue(2, viewParam[i].getOrderNo());
			lst_RetrivalOrderStart.setValue(3, viewParam[i].getCasePieceflgName());
			lst_RetrivalOrderStart.setValue(4, viewParam[i].getCustomerCode());
			lst_RetrivalOrderStart.setValue(5, viewParam[i].getCustomerName());

			//#CM717422
			// Set the Hidden field item. 
			lst_RetrivalOrderStart.setValue(0, createHiddenList(viewParam[i]));
		}

		//#CM717423
		// Enable to click a button. 
		setBtnTrueFalse(true);
	}

	//#CM717424
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
		//#CM717425
		// Generate a string of hidden field item.  
		ArrayList hiddenList = new ArrayList();
		//#CM717426
		// Hidden field item 
		//#CM717427
		// Work No. (Number of elements) 
		hiddenList.add(HDNARYJOBNO, Integer.toString(viewParam.getJobNoList().size()));
		
		//#CM717428
		// Last update date/time (Number of elements) 
		hiddenList.add(HDNARYLASTUPDATEDATE, Integer.toString(viewParam.getLastUpdateDateList().size()));
		//#CM717429
		// Work No.
		Vector vecJobNo = viewParam.getJobNoList();
		for(int i = 0; i < vecJobNo.size(); i++)
		{
			hiddenList.add(vecJobNo.get(i));
		}
		
		
		//#CM717430
		// Last update date/time 	
		Vector vecLastDate = viewParam.getLastUpdateDateList();
		for(int i = 0; i < vecLastDate.size(); i++)
		{
			hiddenList.add(WmsFormatter.getTimeStampString((Date)vecLastDate.get(i)));
		}
		return CollectionUtils.getConnectedString(hiddenList);
	}
	//#CM717431
	// Event handler methods -----------------------------------------
	//#CM717432
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717433
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717434
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717435
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
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM717436
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717437
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717438
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717439
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717440
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717441
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717442
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717443
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717444
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717445
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717446
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717447
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717448
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717449
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717450
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717451
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717452
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
		//#CM717453
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM717454
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM717455
		// "Search" flag 
		param.setParameter(ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM717456
		// for Order 
		param.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		
		//#CM717457
		// Work status (work status data with "Standby") 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		param.setParameter(ListRetrievalConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
			 
		//#CM717458
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do", param, "/progress.do");
	}

	//#CM717459
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717460
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717461
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717462
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717463
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717464
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
		//#CM717465
		// Set the search condition in the Search screen of Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM717466
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM717467
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM717468
		// "Search" flag 
		param.setParameter(ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM717469
		// for Order 
		param.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		
		//#CM717470
		// Work status (work status data with "Standby") 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		param.setParameter(ListRetrievalPlanDateBusiness.WORKSTATUSRETRIEVALPLANDATE_KEY, search);
		
		//#CM717471
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", param, "/progress.do");
	}

	//#CM717472
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_OrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717473
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717474
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717475
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717476
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717477
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717478
	/** 
	 * Clicking on the Search Order No. button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Order No. using this search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Planned Picking Date <BR>
	 *       Order No. <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchOrderNo_Click(ActionEvent e) throws Exception
	{
		//#CM717479
		// Set the search condition in the Search screen of Order No. 
		ForwardParameters param = new ForwardParameters();
		//#CM717480
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM717481
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM717482
		// Order No.
		param.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, txt_OrderNo.getText());
		//#CM717483
		// Case/Piece flag for search
		param.setParameter(ListRetrievalOrdernoBusiness.CASE_PIECE_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
		//#CM717484
		// "Search" flag 
		param.setParameter(ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM717485
		// for Order 
		param.setParameter(ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM717486
		// Work status (work status data with "Standby") 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		param.setParameter(ListRetrievalOrdernoBusiness.WORKSTATUSORDERNO_KEY, search);
		
		//#CM717487
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do", param, "/progress.do");
	}

	//#CM717488
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717489
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717490
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717491
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717492
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717493
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717494
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717495
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717496
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717497
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_OrderPikingList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717498
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717499
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Change(ActionEvent e) throws Exception
	{
	}

	//#CM717500
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717501
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
			//#CM717502
			// Clear the preset area when input error occurs. 
			btn_ListClear_Click(e);

			//#CM717503
			// Check for input. 
			txt_WorkerCode.validate();
			txt_Password.validate();
			txt_ConsignorCode.validate();
			txt_RtrivlPlanDate.validate();
			//#CM717504
			// Pattern matching characters 
			txt_RRtrivlPlanDate.validate(false);
			txt_OrderNo.validate(false);

			//#CM717505
			// Set for the schedule parameter: 
			RetrievalSupportParameter param = new RetrievalSupportParameter();
			//#CM717506
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM717507
			// Password 
			param.setPassword(txt_Password.getText());
			//#CM717508
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM717509
			// Planned Picking Date
			param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));

			//#CM717510
			// Order No.
			param.setOrderNo(txt_OrderNo.getText());

			//#CM717511
			// Case/Piece division 
			if (rdo_CpfAll.getChecked())
			{
				//#CM717512
				// All 
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
			}
			else if (rdo_CpfCase.getChecked())
			{
				//#CM717513
				// Case 
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_CpfPiece.getChecked())
			{
				//#CM717514
				// Piece 
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
			}
			else if (rdo_CpfAppointOff.getChecked())
			{
				//#CM717515
				// None 
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
			}

			//#CM717516
			// Store it in ViewState (for repeated search after processed) 
			//#CM717517
			// Consignor Code
			this.getViewState().setString(VSTCONSIGNOR, param.getConsignorCode());
			//#CM717518
			// Planned Shipping Date 
			this.getViewState().setString(VSTPLANDATE, param.getRetrievalPlanDate());
			//#CM717519
			// Order No.
			this.getViewState().setString(VSTORDERNO, param.getOrderNo());
			//#CM717520
			// Case/Piece division
			this.getViewState().setString(VSTCASEPIECE, param.getCasePieceflg());
			

			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalOrderStartSCH();

			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) schedule.query(conn, param);

			if (viewParam == null || viewParam.length == 0)
			{
				//#CM717521
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());

				return;
			}

			//#CM717522
			// Set the listcell. 
			setList(viewParam, chk_CommonUse.getChecked());
			
			//#CM717523
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
				//#CM717524
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

	//#CM717525
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717526
	/**
	 * Clicking on the Clear button invokes this.  <BR>
	 * <BR>
	 * Summary: Clears the input area.  <BR>
	 * </BR>
	 * <DIR>
	 *     1. Clear the field item in Input area.  <BR>
	 *     <DIR>
	 *         Worker Code [Keep as it is]  <BR>
	 *         Password [as it is]  <BR>
	 *         Consignor Code [Search Value]  <BR>
	 *         Planned Picking Date [Clear]  <BR>
	 *         Order No. [Clear]  <BR>
	 *         Case/Piece division[All] <BR>
	 *         Print the Order Picking report. [ture] <BR>
	 *     </DIR>
	 *     2. Set the cursor on the Worker code.  <BR>
	 *     3. Maintain the contents of preset area.  <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM717527
		// Set the initial value for each input field. 
		//#CM717528
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM717529
		// Planned Picking Date
		txt_RtrivlPlanDate.setText("");
		//#CM717530
		// Order No.
		txt_OrderNo.setText("");
		//#CM717531
		// Case/Piece division: All 
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);
		//#CM717532
		// Tick the option of Print Order Picking report ON. 
		chk_CommonUse.setChecked(true);

		//#CM717533
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM717534
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_RetrivalStart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717535
	/** 
	 * Clicking on the Start Picking button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the Picking using the info in the Preset area.<BR>
	 * <BR>
	 * <DIR>
	 *	  1. Set the cursor on the Worker code. <BR>
	 *    2. Display the dialog box to allow to confirm to add it or not. <BR>
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
     *                      Worker Code<BR>
     *                      Password  <BR>
     *                      Consignor Code <BR>
     *                      Planned Picking Date <BR>
     *                      Order No. <BR>
     *                      Case/Piece division name (Work Type) <BR>
	 * 						Print type of Order Picking report <BR>
	 *						 Preset.Work No. []  <BR>
	 *                      Preset.Last update date/time []  <BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2. If the schedule result is other than null, clear the added info in the Input area and the Preset area. <BR>
	 *				3. Display the preset info again in the preset area. <BR>
	 *              4.If the count of the Preset data is 0, disable the Start Picking button, Select All button, the Cancel All Selection button, Clear List button.  <BR>
	 *              5.If the count of the Preset data is 0, clear Consignor Code, Consignor Name, and Planned Picking Date of the preset.  <BR>
	 *    			 If null, output the message obtained from the schedule to the screen. <BR>
	 *			</DIR>
	 *    </DIR>
	 * <BR>
	 * 	 Row No. list of listcell <BR>
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
	public void btn_RetrivalStart_Click(ActionEvent e) throws Exception
	{
		//#CM717536
		// Check for input. 
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		txt_RtrivlPlanDate.validate();

		//#CM717537
		// Obtain the maximum (max) number of lines. 
		int index = lst_RetrivalOrderStart.getMaxRows();

		Connection conn = null;
		try
		{
			Vector vecParam = new Vector(index);
			for (int i = 1; i < index; i++)
			{
				//#CM717538
				// Designate the line. 
				lst_RetrivalOrderStart.setCurrentRow(i);
				//#CM717539
				// Disable to set a not-selected line for parameter. 
				if (!lst_RetrivalOrderStart.getChecked(1))
				{
					continue;
				}
				//#CM717540
				// Set for the schedule parameter: 
				RetrievalSupportParameter param = new RetrievalSupportParameter();
				//#CM717541
				// Worker Code
				param.setWorkerCode(txt_WorkerCode.getText());
				//#CM717542
				// Password 
				param.setPassword(txt_Password.getText());
				
				//#CM717543
				// Set Search Condition (Consignor, Planned Picking Date, Order No., and Case/Piece division) for re-display
				//#CM717544
				// Consignor Code
				param.setConsignorCode(this.getViewState().getString(VSTCONSIGNOR));
				//#CM717545
				// Planned Picking Date
				param.setRetrievalPlanDate(this.getViewState().getString(VSTPLANDATE));
				//#CM717546
				// Order No.
				param.setOrderNo(this.getViewState().getString(VSTORDERNO));
				//#CM717547
				// Case/Piece division 
				param.setCasePieceflg(this.getViewState().getString(VSTCASEPIECE));

				//#CM717548
				// Print the Order Picking report. 
				param.setRetrievalListFlg(chk_CommonUse.getChecked());
				
				//#CM717549
				// Set the hidden field item. (Work No. (0), Last update date/time (1)) 
				String hidden = lst_RetrivalOrderStart.getValue(0);
				String orgAryJobNo = CollectionUtils.getString(HDNARYJOBNO, hidden);
				String orgAryLastDate = CollectionUtils.getString(HDNARYLASTUPDATEDATE, hidden);
				//#CM717550
				// Maintain the scope of the Work No. and Last update date/time as hidden field items, which are variable, in intStrPos and intEndPos. 
				 int intStrPos = HDNJOBNO;
				 int intEndPos = HDNJOBNO + Formatter.getInt(orgAryJobNo);
				 //#CM717551
				 // Obtain the Work No. 
				 Vector orgJobNoList = new Vector();
				 for(int j = intStrPos; j < intEndPos; j++)
				 {
					 orgJobNoList.add(CollectionUtils.getList(hidden).get(j));
				 }
	
	
				 //#CM717552
				 // Obtain the Last update date/time. 
				 Vector orgLastDateList = new Vector();
				 intStrPos = intEndPos;
				 intEndPos = intEndPos + Formatter.getInt(orgAryLastDate);
				 for(int j = intStrPos; j < intEndPos; j++)
				 {
					 String strLastUpDate = (String) CollectionUtils.getList(hidden).get(j);
					 orgLastDateList.add(WmsFormatter.getTimeStampDate(strLastUpDate));
				 }
				
				//#CM717553
				// Work No.
				param.setJobNoList(orgJobNoList);
				//#CM717554
				// Last update date/time
				param.setLastUpdateDateList(orgLastDateList);
				//#CM717555
				// Maintain the line No. 
				param.setRowNo(i);

				vecParam.addElement(param);
			}
			if (vecParam.size() <= 0)
			{
				//#CM717556
				// 6023154=There is no data to update. 
				message.setMsgResourceKey("6023154");
				return;
			}

			RetrievalSupportParameter[] paramArray = new RetrievalSupportParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			WmsScheduler schedule = new RetrievalOrderStartSCH();
			//#CM717557
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);


			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[])schedule.startSCHgetParams(conn, paramArray);
			//#CM717558
			// Start the schedule. 
			if (viewParam != null)
			{
				//#CM717559
				// Commit the transaction. 
				conn.commit();
				//#CM717560
				// When there is no data to be displayed 
				if (viewParam.length == 0)
				{
					//#CM717561
					// Delete all the Line info. 
					lst_RetrivalOrderStart.clearRow();
					//#CM717562
					// Clear the Consignor field in the Preset area. 
					txt_RConsignorCode.setText("");
					txt_RConsignorName.setText("");
					txt_RRtrivlPlanDate.setText("");
					//#CM717563
					// Disable the buttons in the Preset area. 
					setBtnTrueFalse(false);
					//#CM717564
					// Set the cursor on the Worker code. 
					setFocus(txt_WorkerCode);
				}
				else
				{
					//#CM717565
					// Set the listcell. 
					setList(viewParam, chk_CommonUse.getChecked());
				}
				//#CM717566
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.rollback();
				//#CM717567
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
				//#CM717568
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

	//#CM717569
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheck_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717570
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
		//#CM717571
		// Place a check in option checkbox. 
		for (int i = 1; i < lst_RetrivalOrderStart.getMaxRows(); i++)
		{
			//#CM717572
			// Select a line to work. 
			lst_RetrivalOrderStart.setCurrentRow(i);
			lst_RetrivalOrderStart.setChecked(1, true);
		}
		//#CM717573
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM717574
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheckClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717575
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
		//#CM717576
		// Clear check placed in the option checkbox. 
		for (int i = 1; i < lst_RetrivalOrderStart.getMaxRows(); i++)
		{
			//#CM717577
			// Select a line to work. 
			lst_RetrivalOrderStart.setCurrentRow(i);
			lst_RetrivalOrderStart.setChecked(1, false);
		}
		//#CM717578
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM717579
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717580
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
		//#CM717581
		// Delete all the Line info. 
		lst_RetrivalOrderStart.clearRow();
		//#CM717582
		// Clear the Consignor field in the Preset area. 
		txt_RConsignorCode.setText("");
		txt_RConsignorName.setText("");
		txt_RRtrivlPlanDate.setText("");
		//#CM717583
		// Disable the buttons in the Preset area. 
		setBtnTrueFalse(false);
		//#CM717584
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM717585
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717586
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717587
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717588
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717589
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717590
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717591
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717592
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717593
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717594
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrivalPlanDateT_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717595
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717596
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RRtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717597
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RRtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717598
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrivalOrderStart_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717599
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrivalOrderStart_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717600
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrivalOrderStart_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717601
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrivalOrderStart_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM717602
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrivalOrderStart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717603
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrivalOrderStart_Change(ActionEvent e) throws Exception
	{
	}

	//#CM717604
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrivalOrderStart_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717605
	/**
	 * Allow this method to obtain the Consignor code from the schedule. <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule. <BR>
	 * <DIR>
	 *   1. If there is only one Consignor Code, return the corresponding Consignor. Otherwise, return null.  <BR>
	 * <DIR>
	 * @throws Exception Report all exceptions. 
	 *
	 * @return Consignor Code
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM717606
			// Obtain connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM717607
			// Declare the parameter. 
			RetrievalSupportParameter param = new RetrievalSupportParameter();

			//#CM717608
			// Obtain the Consignor code from the schedule. 
			WmsScheduler schedule = new RetrievalOrderStartSCH();
			//#CM717609
			// Start the schedule. 
			param = (RetrievalSupportParameter) schedule.initFind(conn, param);

			//#CM717610
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
				//#CM717611
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
	
	//#CM717612
	/**
	 * Allow this method to set acceptability to compile buttons. <BR>
	 * <BR>
	 * Summary: Sets to determine whether to compile buttons after receiving false or true. <BR>
	 * @param  arg status(false or true)
	 * @return None 
	 */
	private void setBtnTrueFalse(boolean arg)
	{
		//#CM717613
		// Disable to click a button. 
		//#CM717614
		// "Start Storage" button 
		btn_RetrivalStart.setEnabled(arg);
		//#CM717615
		// "Select All" button 
		btn_AllCheck.setEnabled(arg);
		//#CM717616
		// Cancel All Selected 
		btn_AllCheckClear.setEnabled(arg);
		//#CM717617
		// "Clear List" button 
		btn_ListClear.setEnabled(arg);
	}
	//#CM717618
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_StartSet_Click(ActionEvent e) throws Exception
	{
	}


}
//#CM717619
//end of class
