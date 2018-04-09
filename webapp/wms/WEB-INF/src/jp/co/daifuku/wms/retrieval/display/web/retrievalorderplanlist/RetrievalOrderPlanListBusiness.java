// $Id: RetrievalOrderPlanListBusiness.java,v 1.2 2007/02/07 04:19:23 suresh Exp $

//#CM716481
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalorderplanlist;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalcustomer.ListRetrievalCustomerBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderlist.ListRetrievalOrderListBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderPlanListSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM716482
/**
 * Designer : T.Yoshino<BR>
 * Maker : T.Yoshino<BR>
 * Allow this class of screen to print the Order Picking Plan report. <BR>
 * Pass the parameter to the schedule to print the Order Picking Plan report. <BR>
 * <BR>
 * <DIR>
 * 1. Process by clicking "Display" button (<CODE>btn_PDisplay_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 * 	 Set the contents entered via screen, and
 *  allow the schedule to search for the data for display based on the parameter and display the result on the popup screen. <BR>
 *  <BR>
 *  [Parameter]  *Mandatory Input<BR>
 *  <DIR>
 * 	 Consignor Code *<BR>
 * 	 Start Planned Picking Date<BR>
 * 	 End Planned Picking Date<BR>
 *   Customer Code <BR>
 * 	 Case Order No.<BR>
 *   Piece Order No.<BR>
 * 	 Work status: *<BR>
 * 	</DIR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Print" button (<CODE>btn_PDisplay_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 *  Set the contents entered via screen, and
 *  allow the schedule to search for the databased on the parameter and print it. <BR>
 *  Allow the schedule to return true when printing completed successfully or return false when it failed. <BR>
 *  <BR>
 *  [Parameter]  *Mandatory Input<BR>
 *  <DIR>
 * 	 Consignor Code *<BR>
 * 	 Start Planned Picking Date<BR>
 * 	 End Planned Picking Date<BR>
 *   Customer Code <BR>
 * 	 Case Order No.<BR>
 *   Piece Order No.<BR>
 * 	 Work status: *<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/25</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:23 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderPlanListBusiness extends RetrievalOrderPlanList implements WMSConstants
{
	//#CM716483
	// Class fields --------------------------------------------------
	
	//#CM716484
	// Maintain the control that invokes the dialog: Print button 
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";
	
	//#CM716485
	// Class variables -----------------------------------------------

	//#CM716486
	// Class method --------------------------------------------------

	//#CM716487
	// Constructors --------------------------------------------------

	//#CM716488
	// Public methods ------------------------------------------------

	//#CM716489
	/**
	 * Initialize the screen. 
	 * <BR>
	 * <DIR>
	 *  Field item: name [Initial Value] 
	 *  <DIR>
	 *  Consignor Code       [If there is only one Consignor code that corresponds to the condition, show the Initial Display.] <BR>
	 *  Start Planned Picking Date   [None] <BR>
	 *  End Planned Picking Date   [None] <BR>
	 *  Customer Code  	 [None]  <BR>
	 * 	Case Order No. [None] <BR>
	 *  Piece Order No.  [None] <BR>
	 * 	 Work status: [All] <BR>
	 *  </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM716490
		// Set the initial value for each input field. 
		txt_ConsignorCode.setText(getConsignorCode());
		txt_StrtRtrivlPlanDate.setText("");
		txt_EdRtrivlPlanDate.setText("");
		txt_CustomerCode.setText("");
		txt_CaseOrderNo.setText("");
		txt_PieceOrderNo.setText("");
		pul_WkStsRtrivlPlan.setSelectedIndex(0);
		//#CM716491
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
	}
	//#CM716492
	/**
	 * Invoke this before invoking each control event.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM716493
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM716494
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM716495
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}

	}
	
	//#CM716496
	/**
	 * Returning from the Dialog button invokes this method.
	 * Override the page_ConfirmBack defined for Page. 
	 * <BR>
	 * Summary: Executes the selected process if selected Yes in the dialog. <BR>
	 * <BR>
	 * 1. Check which dialog returns it. <BR>
	 * 2. Check for choice of Yes or No clicked in the dialog box. <BR>
	 * 3. Selecting Yes starts the schedule. <BR>
	 * 4. For print dialog, <BR>
	 *   <DIR>
	 *   4-1. Set the input field for the parameter. <BR>
	 *   4-2. Start the printing schedule. <BR>
	 *   4-3. Display the schedule result in the message area. <BR>
	 *	 </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		try
		{
			//#CM716497
			// Set the focus for the Consignor code. 
			setFocus(txt_ConsignorCode);

			//#CM716498
			// Identify which dialog returns it. 
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM716499
			// Clicking on Yes in the dialog box returns true. 
			//#CM716500
			// Clicking on No in the dialog box returns false. 
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM716501
			// Clicking NO button closes the process. 
			//#CM716502
			// Not required to set any error message here. 
			if (!isExecute)
			{
				return;
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			//#CM716503
			// Ensure to turn the flag OFF after closed the operation of the dialog. 
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM716504
		// Start the printing schedule. 
		Connection conn = null;
		try
		{
			//#CM716505
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM716506
			// Set the input value for the parameter. 
			RetrievalSupportParameter[] param = new RetrievalSupportParameter[1];
			param[0] = createParameter();

			//#CM716507
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalOrderPlanListSCH();
			schedule.startSCH(conn, param);
			
			//#CM716508
			// Set the message. 
			message.setMsgResourceKey(schedule.getMessage());
	
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
	
		}
		finally
		{
			try
			{
				//#CM716509
				// Close the connection. 
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
	
		}
		
	}

	//#CM716510
	/**
	 * Returning from a popup window invokes this method.
	 * Override the page_DlgBack defined for Page. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{

		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM716511
		// Obtain the parameter selected in the listbox. 
		//#CM716512
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM716513
		// Start Planned Picking Date
		Date startretrievalplandate = WmsFormatter.toDate(param.getParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY), this.getHttpRequest().getLocale());
		//#CM716514
		// End Planned Picking Date
		Date endretrievalplandate = WmsFormatter.toDate(param.getParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY), this.getHttpRequest().getLocale());
		//#CM716515
		// Customer Code
		String customercode = param.getParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY);
		//#CM716516
		// Case Order No.
		String caseorderno = param.getParameter(ListRetrievalOrdernoBusiness.CASEORDERNO_KEY);
		//#CM716517
		// Piece Order No.
		String pieceorderno = param.getParameter(ListRetrievalOrdernoBusiness.PIECEORDERNO_KEY);

		//#CM716518
		// Set a value if not empty. 
		//#CM716519
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM716520
		// Start Planned Picking Date
		if (!StringUtil.isBlank(startretrievalplandate))
		{
			txt_StrtRtrivlPlanDate.setDate(startretrievalplandate);
		}
		//#CM716521
		// End Planned Picking Date
		if (!StringUtil.isBlank(endretrievalplandate))
		{
			txt_EdRtrivlPlanDate.setDate(endretrievalplandate);
		}
		//#CM716522
		// Customer Code
		if (!StringUtil.isBlank(customercode))
		{
			txt_CustomerCode.setText(customercode);
		}
		//#CM716523
		// Case Order No.
		if (!StringUtil.isBlank(caseorderno))
		{
			txt_CaseOrderNo.setText(caseorderno);
		}
		//#CM716524
		// Piece Order No. 
		if (!StringUtil.isBlank(pieceorderno))
		{
			txt_PieceOrderNo.setText(pieceorderno);
		}
		//#CM716525
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
	}
	//#CM716526
	// Package methods -----------------------------------------------

	//#CM716527
	// Protected methods ---------------------------------------------
	
	//#CM716528
	/**
	 * Generate a parameter object for which the input value of the input area is set. <BR>
	 * 
	 * @return Parameter object that contains input values in the input area. 
	 */
	protected RetrievalSupportParameter createParameter()
	{
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		
		param.setConsignorCode(txt_ConsignorCode.getText());
		param.setFromRetrievalPlanDate(WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));
		param.setToRetrievalPlanDate(WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));
		param.setCustomerCode(txt_CustomerCode.getText());
		param.setCaseOrderNo(txt_CaseOrderNo.getText());
		param.setPieceOrderNo(txt_PieceOrderNo.getText());
		//#CM716529
		// Work status:
		//#CM716530
		// All 
		if (pul_WkStsRtrivlPlan.getSelectedIndex() == 0)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_ALL);
		}
		//#CM716531
		// Standby 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 1)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		}
		//#CM716532
		// "Started" 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 2)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_STARTED);
		}
		//#CM716533
		// Working 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 3)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_WORKING);
		}
		//#CM716534
		// Partially Completed 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 4)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		}
		//#CM716535
		// Completed 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 5)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
		}
		return param;
	}

	//#CM716536
	// Private methods -----------------------------------------------
	//#CM716537
	/**
	 * Allow this method to obtain the Consignor code from the schedule on the initial display.  <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule.  <BR>
	 * 
	 * @return Consignor Code <BR>
	 *         Return the string of the Consignor code when one corresponding data exists. <BR>
	 *         Return null if 0 or multiple corresponding data exist.  <BR>
	 * 
	 * @throws Exception Report all exceptions. 
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM716538
			// Obtain Connection 	
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			RetrievalSupportParameter param = new RetrievalSupportParameter();

			//#CM716539
			// Obtain the Consignor code from the schedule. 
			WmsScheduler schedule = new RetrievalOrderPlanListSCH();
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
				//#CM716540
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

	//#CM716541
	// Event handler methods -----------------------------------------
	//#CM716542
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716543
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716544
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716545
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

	//#CM716546
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716547
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716548
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716549
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716550
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716551
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716552
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
		//#CM716553
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM716554
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM716555
		// "Search" flag 
		param.setParameter(ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM716556
		// for Order 
		param.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM716557
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do", param, "/progress.do");
	}

	//#CM716558
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrtRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716559
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716560
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtRtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716561
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtRtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716562
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrtRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716563
	/** 
	 * Clicking on the Search Start Planned Picking Date button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Planned Picking Date using this search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  Start Planned Picking Date<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchStrtRtrivlPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM716564
		// Set the search condition in the Search screen of Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM716565
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM716566
		// Start Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));
		//#CM716567
		// Set the Start flag. 
		param.setParameter(ListRetrievalPlanDateBusiness.RANGERETRIEVALPLANDATE_KEY, RetrievalSupportParameter.RANGE_START);
		//#CM716568
		// Set the "Search" flag. 
		param.setParameter(ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM716569
		// for Order 
		param.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM716570
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", param, "/progress.do");
	}

	//#CM716571
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716572
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EdrtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716573
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716574
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdRtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716575
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdRtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716576
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEdRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716577
	/** 
	 * Clicking on the Search End Planned Picking Date button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Planned Picking Date using this search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  End Planned Picking Date<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchEdRtrivlPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM716578
		// Set the search condition in the Search screen of Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM716579
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM716580
		// End Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));
		//#CM716581
		// Set the Close flag. 
		param.setParameter(ListRetrievalPlanDateBusiness.RANGERETRIEVALPLANDATE_KEY, RetrievalSupportParameter.RANGE_END);
		//#CM716582
		// "Search" flag 
		param.setParameter(ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM716583
		// for Order 
		param.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM716584
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", param, "/progress.do");
	}

	//#CM716585
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716586
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716587
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716588
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716589
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716590
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716591
	/** 
	 * Clicking on Search Customer Code button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Customer using this search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  Start Planned Picking Date<BR>
	 *  End Planned Picking Date<BR>
	 *  Customer Code <BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchCustomerCode_Click(ActionEvent e) throws Exception
	{
		//#CM716592
		// Set the search condition in the Search screen of Order No. 
		ForwardParameters param = new ForwardParameters();
		//#CM716593
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM716594
		// Start Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));
		//#CM716595
		// End Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));
		//#CM716596
		// Customer Code
		param.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		//#CM716597
		// "Search" flag (Plan) 
		param.setParameter(ListRetrievalCustomerBusiness.SEARCHCUSTOMER_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM716598
		// for Order 
		param.setParameter(ListRetrievalCustomerBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);

		//#CM716599
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalcustomer/ListRetrievalCustomer.do", param, "/progress.do");
	}

	//#CM716600
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716601
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716602
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716603
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716604
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716605
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCaseOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716606
	/** 
	 * Clicking on the Search Case Order No. button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Order No. using this search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  Start Planned Picking Date<BR>
	 *  End Planned Picking Date<BR>
	 *  Customer Code <BR>
	 *  Case Order No.<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchCaseOrderNo_Click(ActionEvent e) throws Exception
	{
		//#CM716607
		// Set the search condition in the Search screen of Order No. 
		ForwardParameters param = new ForwardParameters();
		//#CM716608
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM716609
		// Start Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));
		//#CM716610
		// End Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));
		//#CM716611
		// Customer Code
		param.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		//#CM716612
		// Case Order No.
		param.setParameter(ListRetrievalOrdernoBusiness.CASEORDERNO_KEY, txt_CaseOrderNo.getText());
		//#CM716613
		// "Search" flag 
		param.setParameter(ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM716614
		// Case/Piece division flag (Case) 
		param.setParameter(ListRetrievalOrdernoBusiness.CASE_PIECE_KEY, RetrievalSupportParameter.LISTBOX_CASE);
		//#CM716615
		// for Order 
		param.setParameter(ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);

		//#CM716616
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do", param, "/progress.do");
	}

	//#CM716617
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PieceOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716618
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716619
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716620
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716621
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716622
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchPieceOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716623
	/** 
	 * Clicking on the Search Piece Order No. button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Order No. using this search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  Start Planned Picking Date<BR>
	 *  End Planned Picking Date<BR>
	 *  Customer Code <BR>
	 *  Piece Order No.<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchPieceOrderNo_Click(ActionEvent e) throws Exception
	{
		//#CM716624
		// Set the search condition in the Search screen of Order No. 
		ForwardParameters param = new ForwardParameters();
		//#CM716625
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM716626
		// Start Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));
		//#CM716627
		// End Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));
		//#CM716628
		// Customer Code
		param.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		//#CM716629
		// Piece Order No.
		param.setParameter(ListRetrievalOrdernoBusiness.PIECEORDERNO_KEY, txt_PieceOrderNo.getText());
		//#CM716630
		// "Search" flag 
		param.setParameter(ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM716631
		// Case/Piece division flag (Piece) 
		param.setParameter(ListRetrievalOrdernoBusiness.CASE_PIECE_KEY, RetrievalSupportParameter.LISTBOX_PIECE);
		//#CM716632
		// for Order 
		param.setParameter(ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM716633
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do", param, "/progress.do");
	}

	//#CM716634
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716635
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WkStsRtrivlPlan_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716636
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WkStsRtrivlPlan_Change(ActionEvent e) throws Exception
	{
	}

	//#CM716637
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716638
	/** 
	 * Clicking on the Display button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the field item entered in the Input area for the parameter and displays the listbox for Storage Plan list in the different screen.<BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		//#CM716639
		// Set the search condition in the screen of Picking Plan list. 
		ForwardParameters forwardParam = new ForwardParameters();
		//#CM716640
		// Consignor Code
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM716641
		// Start Planned Picking Date
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));
		//#CM716642
		// End Planned Picking Date
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));
		//#CM716643
		// Customer Code
		forwardParam.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		//#CM716644
		// Case Order No.
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.CASEORDERNO_KEY, txt_CaseOrderNo.getText());
		//#CM716645
		// Piece Order No.
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.PIECEORDERNO_KEY, txt_PieceOrderNo.getText());
		//#CM716646
		// Work status:
		//#CM716647
		// All 
		if (pul_WkStsRtrivlPlan.getSelectedIndex() == 0)
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_ALL);
		}
		//#CM716648
		// Standby 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 1)
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		}
		//#CM716649
		// "Started" 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 2)
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_STARTED);
		}
		//#CM716650
		// Working 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 3)
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_WORKING);
		}
		//#CM716651
		// Partially Completed 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 4)
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		}
		//#CM716652
		// Completed 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 5)
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
		}

		//#CM716653
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalorderplanlist/ListRetrievalOrderPlanList.do", forwardParam, "/progress.do");

	}

	//#CM716654
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716655
	/** 
	 * Clicking on the Print button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the input field item in the input area for the parameter and obtains the count of data to be printed, and then 
	 * displays the dialog box to allow to confirm to print it or not. <BR>
	 * <BR>
	 * 1. Check for input and the count of print targets. <BR>
	 * 2-1. Display the dialog box to allow to confirm it if the print target data found. <BR>
	 * <DIR>
	 *   There are xxx (number) target data. Do you print it? <BR>
	 * </DIR>
	 * 2-2. If no print target data, obtain the message and display it. <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		//#CM716656
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
		//#CM716657
		// Check for input. 
		txt_ConsignorCode.validate();
		txt_StrtRtrivlPlanDate.validate(false);
		txt_EdRtrivlPlanDate.validate(false);
		txt_CustomerCode.validate(false);
		txt_CaseOrderNo.validate(false);
		txt_PieceOrderNo.validate(false);

		//#CM716658
		// The value of the Start Planned Picking Date is larger than the value of the End Planned Picking Date:
		if (!StringUtil.isBlank(txt_StrtRtrivlPlanDate.getDate()) && !StringUtil.isBlank(txt_EdRtrivlPlanDate.getDate()))
		{
			if (txt_StrtRtrivlPlanDate.getDate().compareTo(txt_EdRtrivlPlanDate.getDate()) > 0)
			{
				//#CM716659
				// 6023108=Starting planned picking date must precede the end date. 
				message.setMsgResourceKey("6023108");
				return;
			}
		}

		Connection conn = null;

		try
		{
			//#CM716660
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM716661
			// Set the schedule parameter. 
			RetrievalSupportParameter param = createParameter();

			//#CM716662
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalOrderPlanListSCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM716663
				// MSG-W0061={0} data corresponded.<BR> Do you print it? 
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM716664
				// Store the fact that the dialog was displayed via Print button. 
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM716665
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());
			}
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM716666
				// Close the connection. 
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
	
		}
	
	}

	//#CM716667
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716668
	/** 
	 * Clicking on the Clear button invokes this. <BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 *  <DIR>
	 *  For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method. 
	 *  </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM716669
		// Set the initial value for each input field. 
		txt_ConsignorCode.setText(getConsignorCode());
		txt_StrtRtrivlPlanDate.setText("");
		txt_EdRtrivlPlanDate.setText("");
		txt_CustomerCode.setText("");
		txt_CaseOrderNo.setText("");
		txt_PieceOrderNo.setText("");
		pul_WkStsRtrivlPlan.setSelectedIndex(0);

		//#CM716670
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);

	}

	//#CM716671
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void Lbl_PieceOrderNo_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM716672
//end of class
