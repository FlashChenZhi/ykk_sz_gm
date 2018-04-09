// $Id: RetrievalPlanModify2Business.java,v 1.2 2007/02/07 04:19:31 suresh Exp $

//#CM718356
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalplanmodify;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalcustomer.ListRetrievalCustomerBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievallocation.ListRetrievalLocationBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalPlanModifySCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM718357
/**
 * Designer : K.Mukai <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this class to modify/delete Picking plan data (to add detail info).  <BR>
 * Display the input info on the upper area of the Typical info input screen and display the data to be output to the preset area in the preset area.  <BR>
 * Pass the parameter to the schedule to modify/delete Picking plan data.  <BR>
 * Allow this screen to commit and roll back the transaction.  <BR>
 * <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>page_Load</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *	 Display the input info on the upper area of the Typical info input screen.  <BR>
 *	 Set the contents obtained from the ViewState for the parameter and obtain the data from the schedule to output it to the preset area.  <BR>
 *	 Display the preset area with the data obtained from the schedule to be output to the preset area, when the process normally completed.  <BR>
 *	 Receive Parameter array with the number of elements 0 if no corresponding data found or receive null when condition error or similar occurs. <BR>
 *	 Output the message obtained from the schedule to the screen, as the schedule result.  <BR>
 *	Obtain the Work Status Info data with status flag Standby only, and display them in the order of Case Picking Location and then Piece Picking Location.  <BR>
 *	<BR>
 *	 [Parameter]  *Mandatory Input<BR>
 *	<BR>
 *	<DIR>
 *		Consignor Code* <BR>
 *		Planned Picking Date* <BR>
 *		Item Code* <BR>
 *		 Case Order No. (for display)  <BR>
 *		 Piece Order No. (for display)  <BR>
 *		 Customer Code (for display)  <BR>
 *		 Case Picking Location (for display)  <BR>
 *		 Piece Picking Location (for display) 
 *	</DIR>
 *	<BR>
 *	 [Returned data] <BR>
 *	<BR>
 *	<DIR>
 *		Consignor Code <BR>
 *		 Consignor Name (with the latest Added Date/Time)  <BR>
 *		Planned Picking Date <BR>
 *		Item Code <BR>
 *		Item Name (Latest Added Date/Time)  <BR>
 *		Case Picking Location<BR>
 *		 Piece Picking Location <BR>
 *		Customer Code <BR>
 *		Customer Name <BR>
 *		Case Order No.<BR>
 *		Piece Order No.<BR>
 *		 Ticket No. (Shipping Ticket No)  <BR>
 *		 Line No. (Shipping ticket line No.)  <BR>
 *		Packed Qty per Case <BR>
 *		Packed qty per bundle <BR>
 *		 Host planned Case qty  <BR>
 *		Host Planned Piece Qty <BR>
 *		Case ITF <BR>
 *		Bundle ITF <BR>
 *		Picking Plan unique key <BR>
 *		Last update date/time <BR>
 *	</DIR>
 *	<BR>
 * </DIR>
 * <BR>
 * 2. Process by clicking on the "Enter" button (<CODE>btn_Input_Click</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *	 Set the contents entered via the input area for the parameter, and allow the schedule to check the input condition based on the parameter.  <BR>
 *	 Receive the result from the schedule. Receive true if the process completed normally. <BR>
 *	Or, receive false if the schedule failed to complete due to condition error etc.  <BR>
 *	 Output the message obtained from the schedule to the screen, as the schedule result.  <BR>
 *	 If the result is true, update the data to be modified in the preset area using the info in the input area.  <BR>
 *	<BR>
 *	 [Parameter]  *Mandatory Input <BR>
 *	<BR>
 *	<DIR>
 *		Consignor Code* <BR>
 *		Planned Picking Date* <BR>
 *		Item Code* <BR>
 *		 Case Picking Location (Mandatory input in either Case or Piece Picking Location) *<BR>
 *		 Piece Picking Location (Mandatory of either Case or Piece Picking Location) *<BR>
 *		Customer Code <BR>
 *		Customer Name <BR>
 *		Case Order No. (Mandatory either Case/Piece Order No.)+ <BR>
 *		 Piece Order No. (Mandatory either Case or Piece Order No.) +<BR>
 *		 Ticket No. (Shipping Ticket No)  <BR>
 *		 Line No. (Shipping ticket line No.)  <BR>
 *		Packed Qty per Case+ <BR>
 *		Packed qty per bundle <BR>
 *		 Planned Case Qty (Mandatory of either Host Planned Case or Piece Qty) * <BR>
 *		 Planned Piece Qty (Mandatory of either Host Planned Case or Piece Qty) * <BR>
 *		Case ITF <BR>
 *		Bundle ITF <BR>
 *		Picking Plan unique key* <BR>
 *		Last update date/time* <BR>
 *		 Preset line No. * <BR>
 *	</DIR>
 * </DIR>
 * <BR>
 * 3. Process by clicking Modify/Add button (<CODE>btn_ModifySubmit_Click</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *	 Set the contents input via preset area for the parameter, and allow the schedule to execute the process for modifying the Shipping plan databased on the parameter. <BR>
 *	 Receive the result from the schedule. If the process completed normally, obtain the data again from the schedule to output to the Preset area, and
 *	display the Preset area again.  <BR>
 *	 Receive null when the schedule failed to complete due to condition error or other causes.  <BR>
 *	 Output the message obtained from the schedule to the screen, as the schedule result.  <BR>
 *	<BR>
 *	 [Parameter]  *Mandatory Input <BR>
 *	<BR>
 *	<DIR>
 *		Worker Code* <BR>
 *		 Password * 
 *		 Case Picking Location (for display) <BR>
 *		 Piece Picking Location (for display) <BR>
 *		 Customer Code (for display)  <BR>
 *		 Case Order No. (for display) <BR>
 *		 Piece Order No. (for display) <BR>
 *		Consignor Code <BR>
 *		Planned Picking Date <BR>
 *		Item Code <BR>
 *		Case Picking Location<BR>
 *		 Piece Picking Location <BR>
 *		Customer Code <BR>
 *		Customer Name <BR>
 *		Case Order No.<BR>
 *		Piece Order No.<BR>
 *		Packed Qty per Case <BR>
 *		Packed qty per bundle <BR>
 *		 Host planned Case qty  <BR>
 *		Host Planned Piece Qty <BR>
 *		Case ITF <BR>
 *		Bundle ITF <BR>
 *		 Ticket No. (Shipping Ticket No)  <BR>
 *		 Line No. (Shipping ticket line No.)  <BR>
 *		Picking Plan unique key* <BR>
 *		Last update date/time* <BR>
 *		Terminal No.* <BR>
 *		 Preset line No. * <BR>
 *	</DIR>
 *	<BR>
 *	 [Returned data]  <BR>
 *	<BR>
 *	<DIR>
 *		Consignor Code <BR>
 *		 Consignor Name (with the latest Added Date/Time)  <BR>
 *		Planned Picking Date <BR>
 *		Item Code <BR>
 *		Item Name (Latest Added Date/Time)  <BR>
 *		Case Picking Location<BR>
 *		 Piece Picking Location <BR>
 *		Customer Code <BR>
 *		Customer Name <BR>
 *		Case Order No.<BR>
 *		Piece Order No.<BR>
 *		Packed Qty per Case <BR>
 *		Packed qty per bundle <BR>
 *		 Host planned Case qty  <BR>
 *		Host Planned Piece Qty <BR>
 *		Case ITF <BR>
 *		Bundle ITF <BR>
 *		 Ticket No. (Shipping Ticket No)  <BR>
 *		 Line No. (Shipping ticket line No.)  <BR>
 *		Picking Plan unique key <BR>
 *		Last update date/time <BR>
 *	</DIR>
 *	<BR>
 * </DIR>
 * <BR>
 * 4.Process by clicking on Delete/Delete All button (<CODE>lst_SShpPlnDaMdDlt_Click</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Set the contents input via preset area for the parameter, and allow the schedule to delete the Shipping plan databased on the parameter.<BR>
 *   Receive the result from the schedule. Receive true if the process completed normally.<BR>
 *   Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 *   Output the message obtained from the schedule to the screen, as the schedule result. <BR>
 *   <BR>
 *   [Parameter]  *Mandatory Input<BR>
 *   <BR>
 *   <DIR>
 *     Worker Code* <BR>
 *     Password * 
 *     Picking Plan unique key* <BR>
 *     Last update date/time* <BR>
 *     Terminal No.* <BR>
 *     Preset line No. * <BR>
 *	 </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/28</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:31 $
 * @author  $Author: suresh $
 */
public class RetrievalPlanModify2Business extends RetrievalPlanModify2 implements WMSConstants
{
	//#CM718358
	// Class fields --------------------------------------------------
    //#CM718359
    //	 Variable that designates a row of listcell 
	//#CM718360
	// Hidden Parameter (listcell) 
	private static final int LST_HDN = 0;
	
	//#CM718361
	// Sequence of hidden parameter 
	//#CM718362
	// Picking Plan unique key (sequence of hidden parameter) 
	private static final int HDNPLANUKEY_IDX = 0;
	//#CM718363
	// Last update date/time (sequence of hidden parameter) 
	private static final int HDNLASTUPDDATE_IDX = 1;
	//#CM718364
	// "Update" flag (sequence of hidden parameter) 
	private static final int HDNUPDATEFLAG_IDX = 2;
	//#CM718365
	// Case Picking Location (sequence of hidden parameter) 
	private static final int HDNCASELOCATION_IDX = 3;
	//#CM718366
	// Piece Picking Location (sequence of hidden parameter) 
	private static final int HDNPIECELOCATION_IDX = 4;
	//#CM718367
	// Customer Code (sequence of hidden parameter) 
	private static final int HDNCUSTOMERCODE_IDX = 5;
	//#CM718368
	// Customer Name (sequence of hidden parameter) 
	private static final int HDNCUSTOMERNAME_IDX = 6;
	//#CM718369
	// Case Order No. (sequence of hidden parameter) 
	private static final int HDNCASEORDERNO_IDX = 7;
	//#CM718370
	// Piece Order No. (sequence of hidden parameter) 
	private static final int HDNPIECEORDERNO_IDX = 8;
	//#CM718371
	// Packed Qty per Case (sequence of hidden parameter) 
	private static final int HDNENTERINGQTY_IDX = 9;
	//#CM718372
	// Packed qty per bundle (sequence of hidden parameter) 
	private static final int HDNBUNDLEENTERINGQTY_IDX = 10;
	//#CM718373
	// Planned Case Qty (sequence of hidden parameter) 
	private static final int HDNPLANCASEQTY_IDX = 11;
	//#CM718374
	// Planned Piece Qty (sequence of hidden parameter) 
	private static final int HDNPLANPIECEQTY_IDX = 12;
	//#CM718375
	// Case ITF (sequence of hidden parameter) 
	private static final int HDNITF_IDX = 13;
	//#CM718376
	// Bundle ITF (sequence of hidden parameter) 
	private static final int HDNBUNDLEITF_IDX = 14;
	//#CM718377
	// Ticket No. (sequence of hidden parameter) 
	private static final int HDNTICKETNO_IDX = 15;
	//#CM718378
	// Ticket Line No. (sequence of hidden parameter) 
	private static final int HDNLINENO_IDX = 16;
	//#CM718379
	// Case/Piece division (sequence of hidden parameter) 
	private static final int HDNIDX_CPF = 17;

	//#CM718380
	/**
	 * Use this key for ViewState to pass Preset line No. 
	 */
	public static final String LINENO_KEY = "LINENO_KEY";

	//#CM718381
	/**
	 * Use this key for ViewState to pass Picking Plan unique key. 
	 */
	public static final String RETRIEVALPLANUKEY_KEY = "RETRIEVALPLANUKEY_KEY";

	//#CM718382
	/**
	 * Use this key for ViewState to pass Last update date/time. 
	 */
	public static final String LASTUPDATEDATE_KEY = "LASTUPDATEDATE_KEY";

	//#CM718383
	// Class variables -----------------------------------------------

	//#CM718384
	// Class method --------------------------------------------------

	//#CM718385
	// Constructors --------------------------------------------------

	//#CM718386
	// Public methods ------------------------------------------------

	//#CM718387
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Summary: shows the Initial Display. <BR>
	 * <BR>
	 * <DIR>
	 *	1. Start the schedule. 
	 *	<DIR>
	 *		 [Parameter]  *Mandatory Input<BR>
	 *		<DIR>
	 *			Consignor Code* <BR>
	 *			Planned Picking Date* <BR>
	 *			Item Code* <BR>
	 *			 Case Order No. (for display)  <BR>
	 *			 Piece Order No. (for display)  <BR>
	 *			 Customer Code (for display)  <BR>
	 *			 Case Picking Location (for display)  <BR>
	 *			 Piece Picking Location (for display) 
	 *		</DIR>
	 *		<BR>
	 *		 [Returned data] <BR>
	 *		<DIR>
	 *			Consignor Code <BR>
	 *			 Consignor Name (with the latest Added Date/Time)  <BR>
	 *			Planned Picking Date <BR>
	 *			Item Code <BR>
	 *			Item Name (Latest Added Date/Time)  <BR>
	 *			Case Picking Location<BR>
	 *			 Piece Picking Location <BR>
	 *			Customer Code <BR>
	 *			Customer Name <BR>
	 *			Case Order No.<BR>
	 *			Piece Order No.<BR>
	 *			Packed Qty per Case <BR>
	 *			Packed qty per bundle <BR>
	 *			 Host planned Case qty  <BR>
	 *			Host Planned Piece Qty <BR>
	 *			Case ITF <BR>
	 *			Bundle ITF <BR>
	 *			 Ticket No. (Shipping Ticket No)  <BR>
	 *			 Line No. (Shipping ticket line No.)  <BR>
	 *			Picking Plan unique key <BR>
	 *			Last update date/time <BR>
	 *		</DIR>
	 *	</DIR>
	 *	2. If the process completed normally, display the info in the Input area and the Preset area, based on the data obtained from the schedule for output to the Preset area. <BR>
	 *	<DIR>
	 *		Field item name in the Input area [Initial Value]  <BR>
	 *		<DIR>
	 *			Consignor Code [Returned data[0].Consignor Code]  <BR>
	 *			 Consignor Name[Returned data[0].Consignor Name]  <BR>
	 *			 Planned Picking Date [Returned data[0].Planned Picking Date]  <BR>
	 *			Item Code [None]  <BR>
	 *			Item Name [None]  <BR>
	 *			Case Picking Location [None]  <BR>
	 *			 Piece Picking Location[None]  <BR>
	 *			Customer Code [None]  <BR>
	 *			Customer Name [None]  <BR>
	 *			 Case Order No.[None]  <BR>
	 *			 Piece Order No.[None]  <BR>
	 *			Packed Qty per Case [None]  <BR>
	 *			 Packed qty per bundle[None]  <BR>
	 *			 Host planned Case qty [None]  <BR>
	 *			Host Planned Piece Qty [None]  <BR>
	 *			 Case ITF[None]  <BR>
	 *			 Bundle ITF[None]  <BR>
	 *			 Ticket No. [None]  <BR>
	 *			 Line No. [None]  <BR>
	 *		</DIR>
	 *		 Preset area field item name  <BR>
	 *		<DIR>
	 *			Case Picking Location <BR>
	 *			Customer Code <BR>
	 *			Case Order No. <BR>
	 *			Packed Qty per Case <BR>
	 *			 Host planned Case qty  <BR>
	 *			Case ITF <BR>
	 *			 Ticket No.  <BR>
	 *			 Piece Picking Location  <BR>
	 *			Customer Name <BR>
	 *			Piece Order No. <BR>
	 *			Packed qty per bundle <BR>
	 *			Host Planned Piece Qty <BR>
	 *			Bundle ITF <BR>
	 *			 Line No.  <BR>
	 *			Picking Plan unique key (Hidden field item)  <BR>
	 *			 Last update date/time(hidden field item)  <BR>
	 *		</DIR>
	 *	</DIR>
	 *	 Receive Parameter array with the number of elements 0 if no corresponding data found or receive null when condition error or similar occurs, and then return to the previous screen. <BR>
	 *	3. Output the message obtained from the schedule to the screen, as the schedule result. <BR>
	 *	4.Set the default value: -1 for the preset line No. of ViewState. <BR>
	 *	5.Disable Enter button and Clear button. <BR>
	 *	6.Initialize the cursor on the Case Picking Location. <BR>
	 *	<BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM718388
		// Title 
		lbl_SettingName.setText(DisplayText.getText(this.getViewState().getString(RetrievalPlanModifyBusiness.TITLE_KEY)));
		//#CM718389
		// Consignor Code
		String consignorcode = this.getViewState().getString(RetrievalPlanModifyBusiness.CONSIGNORCODE_KEY);
		//#CM718390
		// Planned Picking Date
		String retrievalplandate = this.getViewState().getString(RetrievalPlanModifyBusiness.RETRIEVALPLANDATE_KEY);
		//#CM718391
		// Item Code
		String itemcode = this.getViewState().getString(RetrievalPlanModifyBusiness.ITEMCODE_KEY);
		//#CM718392
		// Case Order No.
		String caseorderno = this.getViewState().getString(RetrievalPlanModifyBusiness.CASEORDERNO_KEY);
		//#CM718393
		// Piece Order No.
		String pieceorderno = this.getViewState().getString(RetrievalPlanModifyBusiness.PIECEORDERNO_KEY);
		//#CM718394
		// Customer Code
		String customercode = this.getViewState().getString(RetrievalPlanModifyBusiness.CUSTOMERCODE_KEY);
		//#CM718395
		// Case Picking Location
		String caseretrievallocation = this.getViewState().getString(RetrievalPlanModifyBusiness.CASERETRIEVALLOCATION_KEY);
		//#CM718396
		// Piece Picking Location 
		String pieceretrievallocation = this.getViewState().getString(RetrievalPlanModifyBusiness.PIECERETRIEVALLOCATION_KEY);

		Connection conn = null;

		try
		{
			//#CM718397
			// Prefix a tab for adding detailed information. 
			tab_BscDtlMdfyDlt.setSelectedIndex(2);

			//#CM718398
			// Set for the schedule parameter: 
			RetrievalSupportParameter param = new RetrievalSupportParameter();

			//#CM718399
			// Consignor Code
			param.setConsignorCode(consignorcode);
			//#CM718400
			// Planned Shipping Date 
			param.setRetrievalPlanDate(retrievalplandate);
			//#CM718401
			// Item Code
			param.setItemCode(itemcode);
			//#CM718402
			// Case Order No.
			param.setCaseOrderNoDisp(caseorderno);
			//#CM718403
			// Piece Order No.
			param.setPieceOrderNoDisp(pieceorderno);
			//#CM718404
			// Customer Code
			param.setCustomerCodeDisp(customercode);
			//#CM718405
			// Case Picking Location
			param.setCaseLocationDisp(caseretrievallocation);
			//#CM718406
			// Piece Picking Location 
			param.setPieceLocationDisp(pieceretrievallocation);

			//#CM718407
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalPlanModifySCH();

			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) schedule.query(conn, param);

			//#CM718408
			// When error etc. occurs, receive the relevant message and back to the previous screen. 
			if (viewParam == null || (viewParam != null && viewParam.length == 0))
			{
				//#CM718409
				// Set the message for ViewState. 
				this.getViewState().setString(RetrievalPlanModifyBusiness.MESSAGE_KEY, schedule.getMessage());
				//#CM718410
				// Modify/Delete Detail Info screen -> Basic info setting screen 
				forward("/retrieval/retrievalplanmodify/RetrievalPlanModify.do");

				return;
			}

			//#CM718411
			// If the process completed normally, display the Input area and the Preset area. 
			//#CM718412
			// Input area 
			//#CM718413
			// Consignor Code
			lbl_JavaSetCnsgnrCd.setText(viewParam[0].getConsignorCode());
			//#CM718414
			// Consignor Name
			lbl_JavaSetCnsgnrNm.setText(viewParam[0].getConsignorName());
			//#CM718415
			// Planned Picking Date
			txt_FDate.setDate(WmsFormatter.toDate(viewParam[0].getRetrievalPlanDate()));
			//#CM718416
			// Item Code
			lbl_JavaSetItemCd.setText(viewParam[0].getItemCode());
			//#CM718417
			// Item Name
			lbl_JavaSetItemNm.setText(viewParam[0].getItemName());

			//#CM718418
			// Preset area 
			setList(viewParam);

			//#CM718419
			// Set the message. 
			message.setMsgResourceKey(schedule.getMessage());

			//#CM718420
			// Set the preset line No. in ViewState to determine be in Modified status or not by clicking the Modify button. 
			//#CM718421
			// (Default: -1) 
			this.getViewState().setInt(LINENO_KEY, -1);

			//#CM718422
			// Disable to click a button. 
			//#CM718423
			// "Enter" button 
			btn_Input.setEnabled(false);
			//#CM718424
			// "Clear" button 
			btn_Clear.setEnabled(false);

			//#CM718425
			// Set the cursor on the Line No. 
			setFocus(txt_CaseRtrivlLct);

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM718426
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

	//#CM718427
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
			//#CM718428
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM718429
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM718430
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
		//#CM718431
		// Dialog to allow to confirm before clicking on the "Modify/Add" button: "Do you modify and add this?" 
		btn_ModifySubmit.setBeforeConfirm("MSG-W0033");

		//#CM718432
		// Dialog to allow to confirm before clicking on the "Delete All" button: "Do you delete all the data?"
		btn_AllDelete.setBeforeConfirm("MSG-W0031");
	}

	//#CM718433
	/**
	 * Returning from a popup window invokes this method.
	 * Override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE>. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM718434
		// Obtain the parameter selected in the listbox. 
		//#CM718435
		// Case Picking Location
		String caseretrievallocation = param.getParameter(ListRetrievalLocationBusiness.CASERETRIEVALLOCATION_KEY);
		//#CM718436
		// Piece Picking Location 
		String pieceretrievallocation = param.getParameter(ListRetrievalLocationBusiness.PIECERETRIEVALLOCATION_KEY);
		//#CM718437
		// Customer Code
		String customercode = param.getParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY);
		//#CM718438
		// Customer Name
		String customername = param.getParameter(ListRetrievalCustomerBusiness.CUSTOMERNAME_KEY);
		//#CM718439
		// Case Order No.
		String caseorderno = param.getParameter(ListRetrievalOrdernoBusiness.CASEORDERNO_KEY);
		//#CM718440
		// Piece Order No.
		String pieceorderno = param.getParameter(ListRetrievalOrdernoBusiness.PIECEORDERNO_KEY);

		//#CM718441
		// Set a value if not empty. 
		//#CM718442
		// Case Picking Location
		if (!StringUtil.isBlank(caseretrievallocation))
		{
			txt_CaseRtrivlLct.setText(caseretrievallocation);
		}
		//#CM718443
		// Piece Picking Location 
		if (!StringUtil.isBlank(pieceretrievallocation))
		{
			txt_PieceRtrivlLct.setText(pieceretrievallocation);
		}
		//#CM718444
		// Customer Code
		if (!StringUtil.isBlank(customercode))
		{
			txt_CustomerCode.setText(customercode);
		}
		//#CM718445
		// Customer Name
		if (!StringUtil.isBlank(customername))
		{
			txt_CustomerName.setText(customername);
		}
		//#CM718446
		// Case Order No.
		if (!StringUtil.isBlank(caseorderno))
		{
			txt_CaseOrderNo.setText(caseorderno);
		}
		//#CM718447
		// Piece Order No.
		if (!StringUtil.isBlank(pieceorderno))
		{
			txt_PieceOrderNo.setText(pieceorderno);
		}

		//#CM718448
		// Set the cursor on the Line No. 
		setFocus(txt_CaseRtrivlLct);
	}

	//#CM718449
	// Package methods -----------------------------------------------

	//#CM718450
	// Protected methods ---------------------------------------------
	//#CM718451
	/**
	 * Check for input. 
	 * Set the message if any error and return false. 
	 * 
	 * @return true: Normal, false: Abnormal 
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
		if (!checker.checkContainNgText(txt_CaseRtrivlLct))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_PieceRtrivlLct))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_CustomerCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_CustomerName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_CaseOrderNo))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_PieceOrderNo))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_CaseItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_BundleItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_TicketNo))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
	
		return true;
		
	}

	//#CM718452
	// Private methods -----------------------------------------------
	//#CM718453
	/**
	 * Allow this method to set the Parameter value in the preset area. <BR>
	 * <BR>
	 * Summary: Sets the Parameter value for preset area. <BR>
	 * <DIR>
	 *	 Hidden field item 
	 *	<DIR>
	 *		0. Shipping Plan unique key  <BR>
	 *		1.Last update date/time <BR>
	 *	</DIR>
	 * </DIR>
	 * <BR>
	 * <DIR>
	 *	 [Parameter]  *Mandatory Input<BR>
	 *	<DIR>
	 *		Array of the Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has Contents of Preset area <BR>
	 *	</DIR>
	 * </DIR>
	 * @param param Array of the Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has Contents of Preset area 
	 * @throws Exception Report all exceptions.  
	 */
	private void setList(RetrievalSupportParameter[] param) throws Exception
	{
		for (int i = 0; i < param.length; i++)
		{
			//#CM718454
			// Add a line. 
			lst_SRetrievalPlanModify.addRow();
			//#CM718455
			// Set the current line. 
			lst_SRetrievalPlanModify.setCurrentRow(i + 1);
			//#CM718456
			// Compile the ToolTip data. 
			ToolTipHelper toolTip = new ToolTipHelper();
			//#CM718457
			// Customer Name
			toolTip.add(DisplayText.getText("LBL-W0036"), param[i].getCustomerName());
			//#CM718458
			// Case ITF
			toolTip.add(DisplayText.getText("LBL-W0010"), param[i].getITF());
			//#CM718459
			// Bundle ITF
			toolTip.add(DisplayText.getText("LBL-W0006"), param[i].getBundleITF());
			//#CM718460
			// Ticket No. 
			toolTip.add(DisplayText.getText("LBL-W0266"), param[i].getShippingTicketNo());
			//#CM718461
			// Line No. 
			toolTip.add(DisplayText.getText("LBL-W0109"), param[i].getShippingLineNo());
			//#CM718462
			// Set the TOOL TIP in the current line. 
			lst_SRetrievalPlanModify.setToolTip(i + 1, toolTip.getText());

			//#CM718463
			// Prepare to setup the Hidden field items. 
			List list = new Vector();
			//#CM718464
			// Picking Plan unique key
			list.add(param[i].getRetrievalPlanUkey());
			//#CM718465
			// Last update date/time
			list.add(WmsFormatter.getTimeStampString(param[i].getLastUpdateDate()));
			//#CM718466
			// "Update" flag 
			list.add(RetrievalSupportParameter.UPDATEFLAG_NO);
			//#CM718467
			// Case Picking Location
			list.add(param[i].getCaseLocation());
			//#CM718468
			// Piece Picking Location 
			list.add(param[i].getPieceLocation());
			//#CM718469
			// Customer Code
			list.add(param[i].getCustomerCode());
			//#CM718470
			// Customer Name
			list.add(param[i].getCustomerName());
			//#CM718471
			// Case Order No.
			list.add(param[i].getCaseOrderNo());
			//#CM718472
			// Piece Order No.
			list.add(param[i].getPieceOrderNo());
			//#CM718473
			// Packed Qty per Case
			list.add(WmsFormatter.getNumFormat(param[i].getEnteringQty()));
			//#CM718474
			// Packed qty per bundle
			list.add(WmsFormatter.getNumFormat(param[i].getBundleEnteringQty()));
			//#CM718475
			// Planned Case Qty 
			list.add(WmsFormatter.getNumFormat(param[i].getPlanCaseQty()));
			//#CM718476
			// Planned Piece Qty 
			list.add(WmsFormatter.getNumFormat(param[i].getPlanPieceQty()));
			//#CM718477
			// Case ITF
			list.add(param[i].getITF());
			//#CM718478
			// Bundle ITF
			list.add(param[i].getBundleITF());
			//#CM718479
			// Ticket No. 
			list.add(param[i].getShippingTicketNo());
			//#CM718480
			// Ticket Line No. 
			list.add(WmsFormatter.getNumFormat(param[i].getShippingLineNo()));
			
			
			//#CM718481
			// Set the hidden field item. 
			lst_SRetrievalPlanModify.setValue(0, CollectionUtils.getConnectedString(list));

			//#CM718482
			// Case Picking Location
			lst_SRetrievalPlanModify.setValue(3, param[i].getCaseLocation());
			//#CM718483
			// Customer Code
			lst_SRetrievalPlanModify.setValue(4, param[i].getCustomerCode());
			//#CM718484
			// Case Order No.
			lst_SRetrievalPlanModify.setValue(5, param[i].getCaseOrderNo());
			//#CM718485
			// Packed Qty per Case
			lst_SRetrievalPlanModify.setValue(6, WmsFormatter.getNumFormat(param[i].getEnteringQty()));
			//#CM718486
			// Host planned Case qty 
			lst_SRetrievalPlanModify.setValue(7, WmsFormatter.getNumFormat(param[i].getPlanCaseQty()));
			//#CM718487
			// Case ITF
			lst_SRetrievalPlanModify.setValue(8, param[i].getITF());
			//#CM718488
			// Ticket No. 
			lst_SRetrievalPlanModify.setValue(9, param[i].getShippingTicketNo());
			//#CM718489
			// Piece Picking Location 
			lst_SRetrievalPlanModify.setValue(10, param[i].getPieceLocation());
			//#CM718490
			// Customer Name
			lst_SRetrievalPlanModify.setValue(11, param[i].getCustomerName());
			//#CM718491
			// Piece Order No.
			lst_SRetrievalPlanModify.setValue(12, param[i].getPieceOrderNo());
			//#CM718492
			// Packed qty per bundle
			lst_SRetrievalPlanModify.setValue(13, WmsFormatter.getNumFormat(param[i].getBundleEnteringQty()));
			//#CM718493
			// Host Planned Piece Qty
			lst_SRetrievalPlanModify.setValue(14, WmsFormatter.getNumFormat(param[i].getPlanPieceQty()));
			//#CM718494
			// Bundle ITF
			lst_SRetrievalPlanModify.setValue(15, param[i].getBundleITF());
			//#CM718495
			// Line No. 
			lst_SRetrievalPlanModify.setValue(16, Integer.toString(param[i].getShippingLineNo()));

		}
	}

	//#CM718496
	/**
	 * Allow this method to set the preset area data in the Parameter class.  <BR>
	 * <BR>
	 * Summary: Sets the preset area data in the Parameter class.  <BR>
	 * <BR>
	 * 1. Set all preset data, if any new input, in the Parameter class (Preset line No. to be modified = -1).  <BR>
	 * 2. If the input data is in process of modifying, set the preset data other than modify target line in the Parameter class.  <BR>
	 * 3. Clicking on the Clicking Modify or Add button sets all the preset data in the Parameter class. (Line No. of preset to be modified = -1)  <BR>
	 * <DIR>
	 *	 [Parameter]  *Mandatory Input <BR>
	 *	<DIR>
	 *		 Target preset line No. for updating * <BR>
	 *	</DIR>
	 *	 [Returned data]  <BR>
	 *	<DIR>
	 *		Array of the Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has Contents of Preset area  <BR>
	 *	</DIR>
	 * </DIR>
	 * @param lineno Listcell line No. 
	 * @throws Exception Report all exceptions.  
	 * @return Array of the Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has Contents of Preset area 
	 */
	private RetrievalSupportParameter[] setListParam(int lineno) throws Exception
	{

		Vector vecParam = new Vector();

		//#CM718497
		// Terminal No.
		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
		String teminalNumber = userHandler.getTerminalNo();
		
		for (int i = 1; i < lst_SRetrievalPlanModify.getMaxRows(); i++)
		{
			//#CM718498
			// Exclude the line to be modified. 
			if (i == lineno)
			{
				continue;
			}

			//#CM718499
			// Designate the line. 
			lst_SRetrievalPlanModify.setCurrentRow(i);

			//#CM718500
			// Disable to set any data not changed. 
			if (lineno < 0
				&& CollectionUtils.getString(
					HDNUPDATEFLAG_IDX,
					lst_SRetrievalPlanModify.getValue(0)).equals(
					RetrievalSupportParameter.UPDATEFLAG_NO))
			{
				continue;
			}

			//#CM718501
			// Set for the schedule parameter: 
			RetrievalSupportParameter param = new RetrievalSupportParameter();
			//#CM718502
			// Info in the Input area info 
			//#CM718503
			// Consignor Code
			param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
			//#CM718504
			// Consignor Name
			param.setConsignorName(lbl_JavaSetCnsgnrNm.getText());
			//#CM718505
			// Planned Picking Date
			param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_FDate.getDate()));
			//#CM718506
			// Item Code
			param.setItemCode(lbl_JavaSetItemCd.getText());
			//#CM718507
			// Item Name
			param.setItemName(lbl_JavaSetItemNm.getText());

			//#CM718508
			// Info in the Preset area 
			//#CM718509
			// Case Picking Location
			param.setCaseLocation(lst_SRetrievalPlanModify.getValue(3));
			//#CM718510
			// Customer Code	
			param.setCustomerCode(lst_SRetrievalPlanModify.getValue(4));
			//#CM718511
			// Case Order No.	
			param.setCaseOrderNo(lst_SRetrievalPlanModify.getValue(5));
			//#CM718512
			// Packed Qty per Case
			param.setEnteringQty(WmsFormatter.getInt(lst_SRetrievalPlanModify.getValue(6)));
			//#CM718513
			// Host planned Case qty 
			param.setPlanCaseQty(WmsFormatter.getInt(lst_SRetrievalPlanModify.getValue(7)));
			//#CM718514
			// Case ITF
			param.setITF(lst_SRetrievalPlanModify.getValue(8));
			//#CM718515
			// Ticket No. 	
			param.setShippingTicketNo(lst_SRetrievalPlanModify.getValue(9));
			//#CM718516
			// Piece Picking Location 
			param.setPieceLocation(lst_SRetrievalPlanModify.getValue(10));
			//#CM718517
			// Customer Name	
			param.setCustomerName(lst_SRetrievalPlanModify.getValue(11));
			//#CM718518
			// Piece Order No.	
			param.setPieceOrderNo(lst_SRetrievalPlanModify.getValue(12));
			//#CM718519
			// Packed qty per bundle
			param.setBundleEnteringQty(WmsFormatter.getInt(lst_SRetrievalPlanModify.getValue(13)));
			//#CM718520
			// Host Planned Piece Qty
			param.setPlanPieceQty(WmsFormatter.getInt(lst_SRetrievalPlanModify.getValue(14)));
			//#CM718521
			// Bundle ITF
			param.setBundleITF(lst_SRetrievalPlanModify.getValue(15));
			//#CM718522
			// Line No. 	
			param.setShippingLineNo(Integer.parseInt(lst_SRetrievalPlanModify.getValue(16)));
			
			//#CM718523
			// Case Order No. (for display) 
			param.setCaseOrderNoDisp(this.getViewState().getString(RetrievalPlanModifyBusiness.CASEORDERNO_KEY));
			//#CM718524
			// Piece Order No. (for display) 
			param.setPieceOrderNoDisp(this.getViewState().getString(RetrievalPlanModifyBusiness.PIECEORDERNO_KEY));
			//#CM718525
			// Customer Code (for display) 
			param.setCustomerCodeDisp(this.getViewState().getString(RetrievalPlanModifyBusiness.CUSTOMERCODE_KEY));
			//#CM718526
			// Case Picking Location (for display) 
			param.setCaseLocationDisp(this.getViewState().getString(RetrievalPlanModifyBusiness.CASERETRIEVALLOCATION_KEY));
			//#CM718527
			// Piece Picking Location (for display) 
			param.setPieceLocationDisp(this.getViewState().getString(RetrievalPlanModifyBusiness.PIECERETRIEVALLOCATION_KEY));

			//#CM718528
			// Picking Plan unique key (Hidden field item) 
			param.setRetrievalPlanUkey(CollectionUtils.getString(0, lst_SRetrievalPlanModify.getValue(0)));
			//#CM718529
			// Last update date/time(hidden field item) 
			param.setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(1, lst_SRetrievalPlanModify.getValue(0))));
	

			//#CM718530
			// Maintain the line No. 
			param.setRowNo(i);
			
			//#CM718531
			// Worker Code
			param.setWorkerCode(this.getViewState().getString(RetrievalPlanModifyBusiness.WORKERCODE_KEY));
			//#CM718532
			// Terminal No.
			param.setTerminalNumber(teminalNumber);

			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			//#CM718533
			// Set a value if any preset data to be set. 
			RetrievalSupportParameter[] listparam = new RetrievalSupportParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			//#CM718534
			// Set null if no preset data to be set. 
			return null;
		}
	}

	//#CM718535
	/**
	 * Allow this method to set the modified data of the input area for the preset area.  <BR>
	 * <BR>
	 * Summary: Sets the data to be modified in the input area for the preset area.  <BR>
	 * <DIR>
	 *	 Hidden field item 
	 *	<DIR>
	 *		0. Shipping Plan unique key  <BR>
	 *		1.Last update date/time <BR>
	 *	</DIR>
	 * </DIR>
	 * <BR>
	 * @throws Exception Report all exceptions.  
	 */
	private void setList() throws Exception
	{
		boolean updateflag = updateCheck();

		//#CM718536
		// Compile the ToolTip data. 
		ToolTipHelper toolTip = new ToolTipHelper();
		//#CM718537
		// Customer Name
		toolTip.add(DisplayText.getText("LBL-W0036"), txt_CustomerName.getText());
		//#CM718538
		// Case ITF
		toolTip.add(DisplayText.getText("LBL-W0010"), txt_CaseItf.getText());
		//#CM718539
		// Bundle ITF
		toolTip.add(DisplayText.getText("LBL-W0006"), txt_BundleItf.getText());
		//#CM718540
		// Ticket No. 
		toolTip.add(DisplayText.getText("LBL-W0266"), txt_TicketNo.getText());
		//#CM718541
		// Line No. 
		toolTip.add(DisplayText.getText("LBL-W0109"), txt_LineNo.getText());
		//#CM718542
		// Set the TOOL TIP in the current line. 
		lst_SRetrievalPlanModify.setToolTip(this.getViewState().getInt(LINENO_KEY), toolTip.getText());

		//#CM718543
		// Prepare to setup the Hidden field items. 
		//#CM718544
		// Hidden Parameter 
		ArrayList list = (ArrayList)CollectionUtils.getList(lst_SRetrievalPlanModify.getValue(0));

		//#CM718545
		// Picking Plan unique key
		list.set(HDNPLANUKEY_IDX, this.getViewState().getString(RETRIEVALPLANUKEY_KEY));
		//#CM718546
		// Last update date/time
		list.set(HDNLASTUPDDATE_IDX, this.getViewState().getString(LASTUPDATEDATE_KEY));
		//#CM718547
		// "Modify" flag 
		if (updateflag)
		{
			list.set(HDNUPDATEFLAG_IDX, RetrievalSupportParameter.UPDATEFLAG_YES);
		}
		else
		{
			list.set(HDNUPDATEFLAG_IDX, RetrievalSupportParameter.UPDATEFLAG_NO);
		}
		//#CM718548
		// Set the Shipping Plan unique key, Last update date/time, and Modify flag for the hidden field item. 
		lst_SRetrievalPlanModify.setValue(0, CollectionUtils.getConnectedString(list));
		//#CM718549
		// Case Picking Location
		lst_SRetrievalPlanModify.setValue(3, txt_CaseRtrivlLct.getText());
		//#CM718550
		// Customer Code
		lst_SRetrievalPlanModify.setValue(4, txt_CustomerCode.getText());
		//#CM718551
		// Case Order No.
		lst_SRetrievalPlanModify.setValue(5, txt_CaseOrderNo.getText());
		//#CM718552
		// Packed Qty per Case
		if (StringUtil.isBlank(txt_CaseEntering.getText()))
		{
			lst_SRetrievalPlanModify.setValue(6, "0");
		}
		else
		{
			lst_SRetrievalPlanModify.setValue(6, WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));
		}
		//#CM718553
		// Host planned Case qty 
		if (StringUtil.isBlank(txt_HostCasePlanQty.getText()))
		{
			lst_SRetrievalPlanModify.setValue(7, "0");
		}
		else
		{
			lst_SRetrievalPlanModify.setValue(7, WmsFormatter.getNumFormat(txt_HostCasePlanQty.getInt()));
		}
		//#CM718554
		// Case ITF
		lst_SRetrievalPlanModify.setValue(8, txt_CaseItf.getText());
		//#CM718555
		// Ticket No. 
		lst_SRetrievalPlanModify.setValue(9, txt_TicketNo.getText());
		//#CM718556
		// Piece Picking Location 
		lst_SRetrievalPlanModify.setValue(10, txt_PieceRtrivlLct.getText());
		//#CM718557
		// Customer Name
		lst_SRetrievalPlanModify.setValue(11, txt_CustomerName.getText());
		//#CM718558
		// Piece Order No.
		lst_SRetrievalPlanModify.setValue(12, txt_PieceOrderNo.getText());
		//#CM718559
		// Packed qty per bundle
		if (StringUtil.isBlank(txt_BundleEntering.getText()))
		{
			lst_SRetrievalPlanModify.setValue(13, "0");
		}
		else
		{
			lst_SRetrievalPlanModify.setValue(13, WmsFormatter.getNumFormat(txt_BundleEntering.getInt()));
		}
		//#CM718560
		// Host Planned Piece Qty
		if (StringUtil.isBlank(txt_HostPiesePlanQty.getText()))
		{
			lst_SRetrievalPlanModify.setValue(14, "0");
		}
		else
		{
			lst_SRetrievalPlanModify.setValue(14, WmsFormatter.getNumFormat(txt_HostPiesePlanQty.getInt()));
		}
		//#CM718561
		// Bundle ITF
		lst_SRetrievalPlanModify.setValue(15, txt_BundleItf.getText());
		//#CM718562
		// Line No. 
		lst_SRetrievalPlanModify.setValue(16, Integer.toString(txt_LineNo.getInt()));
	}

	//#CM718563
	/**
	 * Allow this method to determine whether anything changed in the input area and the preset area or not. <BR>
	 * <BR>
	 * Summary: Compares the values between the input area and the preset area, and returns true if any change is found. <BR>
	 * 
	 * @return true if any changed, otherwise false 
	 * @throws Exception Report all exceptions. 
	 */
	private boolean updateCheck() throws Exception
	{
		//#CM718564
		// Case Location
		if (!txt_CaseRtrivlLct
			.getText()
			.equals(CollectionUtils.getString(HDNCASELOCATION_IDX, lst_SRetrievalPlanModify.getValue(0))))
		{
			return true;
		}
		//#CM718565
		// Piece Location 
		if (!txt_PieceRtrivlLct
			.getText()
			.equals(CollectionUtils.getString(HDNPIECELOCATION_IDX, lst_SRetrievalPlanModify.getValue(0))))
		{
			return true;
		}
		//#CM718566
		// Customer Code
		if (!txt_CustomerCode
			.getText()
			.equals(CollectionUtils.getString(HDNCUSTOMERCODE_IDX, lst_SRetrievalPlanModify.getValue(0))))
		{
			return true;
		}
		//#CM718567
		// Customer Name
		if (!txt_CustomerName
			.getText()
			.equals(CollectionUtils.getString(HDNCUSTOMERNAME_IDX, lst_SRetrievalPlanModify.getValue(0))))
		{
			return true;
		}
		//#CM718568
		// Case Order No.
		if (!txt_CaseOrderNo
			.getText()
			.equals(CollectionUtils.getString(HDNCASEORDERNO_IDX, lst_SRetrievalPlanModify.getValue(0))))
		{
			return true;
		}
		//#CM718569
		// Piece Order No.
		if (!txt_PieceOrderNo
			.getText()
			.equals(CollectionUtils.getString(HDNPIECEORDERNO_IDX, lst_SRetrievalPlanModify.getValue(0))))
		{
			return true;
		}
		//#CM718570
		// Packed Qty per Case
		if (txt_CaseEntering.getInt() !=
			WmsFormatter.getInt(CollectionUtils.getString(HDNENTERINGQTY_IDX, lst_SRetrievalPlanModify.getValue(0))))
		{
			return true;
		}
		//#CM718571
		// Packed qty per bundle
		if (txt_BundleEntering.getInt() !=
			WmsFormatter.getInt(CollectionUtils.getString(HDNBUNDLEENTERINGQTY_IDX, lst_SRetrievalPlanModify.getValue(0))))
		{
			return true;
		}
		//#CM718572
		// Planned Case Qty 
		if (txt_HostCasePlanQty.getInt() !=
			WmsFormatter.getInt(CollectionUtils.getString(HDNPLANCASEQTY_IDX, lst_SRetrievalPlanModify.getValue(0))))
		{
			return true;
		}
		//#CM718573
		// Planned Piece Qty 
		if (txt_HostPiesePlanQty.getInt() !=
			WmsFormatter.getInt(CollectionUtils.getString(HDNPLANPIECEQTY_IDX, lst_SRetrievalPlanModify.getValue(0))))
		{
			return true;
		}
		//#CM718574
		// Case ITF
		if (!txt_CaseItf
			.getText()
			.equals(CollectionUtils.getString(HDNITF_IDX, lst_SRetrievalPlanModify.getValue(0))))
		{
			return true;
		}
		//#CM718575
		// Bundle ITF
		if (!txt_BundleItf
			.getText()
			.equals(CollectionUtils.getString(HDNBUNDLEITF_IDX, lst_SRetrievalPlanModify.getValue(0))))
		{
			return true;
		}
		//#CM718576
		// Ticket No. 
		if (!txt_TicketNo
			.getText()
			.equals(CollectionUtils.getString(HDNTICKETNO_IDX, lst_SRetrievalPlanModify.getValue(0))))
		{
			return true;
		}
		//#CM718577
		// Ticket Line No. 
		if (txt_LineNo.getInt() != 
			  WmsFormatter.getInt(CollectionUtils.getString(HDNLINENO_IDX, lst_SRetrievalPlanModify.getValue(0))))
		{
			return true;
		}


		return false;
	}
	
	//#CM718578
	// Event handler methods -----------------------------------------
	//#CM718579
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718580
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718581
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Back_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718582
	/**
	 * Clicking on the Return button invokes this. <BR>
	 * <BR>
	 * Summary: Shifts to the screen of Modify/Delete Picking Plan Data (basic information setting). <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		//#CM718583
		// Modify/Delete Detail Info screen -> Basic info setting screen 
		forward("/retrieval/retrievalplanmodify/RetrievalPlanModify.do");
	}

	//#CM718584
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718585
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

	//#CM718586
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718587
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718588
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718589
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718590
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718591
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718592
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718593
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Item_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718594
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718595
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718596
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718597
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718598
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLct_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718599
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLct_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718600
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLct_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718601
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCaseRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718602
	/** 
	 * Clicking on the Search Case Picking Location button invokes this.  <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Picking Location list using this search condition.  <BR>
	 * <BR>
	 * <DIR>
	 *	 [Parameter]  *Mandatory Input <BR>
	 *	<DIR>
	 *		Consignor Code <BR>
	 *		Case Picking Location <BR>
	 *	</DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchCaseRtrivlLct_Click(ActionEvent e) throws Exception
	{
		//#CM718603
		// Set the search condition in the Search screen of Picking Location. 
		ForwardParameters param = new ForwardParameters();
		//#CM718604
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetCnsgnrCd.getText());
		//#CM718605
		// Case Picking Location
		param.setParameter(ListRetrievalLocationBusiness.CASERETRIEVALLOCATION_KEY, txt_CaseRtrivlLct.getText());
		//#CM718606
		// Case Picking Location/Piece Picking Location flag 
		param.setParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY, RetrievalSupportParameter.LISTBOX_CASE);
		//#CM718607
		// Work Status (other than Deleted) 
		//#CM718608
		// Do not require to set.
		//#CM718609
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievallocation/ListRetrievalLocation.do", param, "/progress.do");
	}

	//#CM718610
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PiceRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718611
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718612
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLct_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718613
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLct_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718614
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLct_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718615
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchPiceRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718616
	/** 
	 * Clicking on the Search Piece Picking Location button invokes this.  <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Picking Location list using this search condition.  <BR>
	 * <BR>
	 * <DIR>
	 *	 [Parameter]  *Mandatory Input <BR>
	 *	<DIR>
	 *		Consignor Code <BR>
	 *		 Piece Picking Location  <BR>
	 *	</DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchPiceRtrivlLct_Click(ActionEvent e) throws Exception
	{
		//#CM718617
		// Set the search condition in the Search screen of Picking Location. 
		ForwardParameters param = new ForwardParameters();
		//#CM718618
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetCnsgnrCd.getText());
		//#CM718619
		// Piece Picking Location 
		param.setParameter(ListRetrievalLocationBusiness.PIECERETRIEVALLOCATION_KEY, txt_PieceRtrivlLct.getText());
		//#CM718620
		// Case Picking Location/Piece Picking Location flag 
		param.setParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY, RetrievalSupportParameter.LISTBOX_PIECE);
		//#CM718621
		// Work Status (other than Deleted) 
		//#CM718622
		// Do not require to set.
		//#CM718623
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievallocation/ListRetrievalLocation.do", param, "/progress.do");
	}

	//#CM718624
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718625
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718626
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718627
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718628
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718629
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718630
	/** 
	 * Clicking on Search Customer Code button invokes this.  <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Customer list using this search condition.  <BR>
	 * <BR>
	 * <DIR>
	 *	 [Parameter]  *Mandatory Input <BR>
	 *	<DIR>
	 *		Consignor Code <BR>
	 *		Customer Code <BR>
	 *	</DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchCustomerCode_Click(ActionEvent e) throws Exception
	{
		//#CM718631
		// Set the search condition in the Search screen of Customer. 
		ForwardParameters param = new ForwardParameters();
		//#CM718632
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetCnsgnrCd.getText());
		//#CM718633
		// Customer Code
		param.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		//#CM718634
		// "Search" flag 
		param.setParameter(ListRetrievalCustomerBusiness.SEARCHCUSTOMER_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM718635
		// Work Status (other than Deleted) 
		//#CM718636
		// Do not require to set.
		//#CM718637
		// Both Order/Item 
		param.setParameter(ListRetrievalCustomerBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ALL);

		//#CM718638
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalcustomer/ListRetrievalCustomer.do", param, "/progress.do");
	}

	//#CM718639
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718640
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718641
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718642
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718643
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718644
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseOrder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718645
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718646
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718647
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718648
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718649
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCaseOrder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718650
	/** 
	 * Clicking on the Search Case Order No. button invokes this.  <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Order No. list using this search condition.  <BR>
	 * <BR>
	 * <DIR>
	 *	 [Parameter]  *Mandatory Input <BR>
	 *	<DIR>
	 *		Consignor Code <BR>
	 *		Case Order No. <BR>
	 *	</DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchCaseOrder_Click(ActionEvent e) throws Exception
	{
		//#CM718651
		// Set the search condition in the Search screen of Order No. 
		ForwardParameters param = new ForwardParameters();
		//#CM718652
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetCnsgnrCd.getText());
		//#CM718653
		// Case Order No.
		param.setParameter(ListRetrievalOrdernoBusiness.CASEORDERNO_KEY, txt_CaseOrderNo.getText());
		//#CM718654
		// Case/Piece flag 
		param.setParameter(ListRetrievalOrdernoBusiness.CASE_PIECE_KEY, RetrievalSupportParameter.LISTBOX_CASE);
		//#CM718655
		// Work Status (other than Deleted) 
		//#CM718656
		// Do not require to set.
		//#CM718657
		// search key
		param.setParameter(ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM718658
		// Both Order/Item 
		param.setParameter(ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ALL);

		//#CM718659
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do", param, "/progress.do");
	}

	//#CM718660
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PieceOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718661
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718662
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718663
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718664
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718665
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchPieceOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718666
	/** 
	 * Clicking on the Search Piece Order No. button invokes this.  <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Order No. list using this search condition.  <BR>
	 * <BR>
	 * <DIR>
	 *	 [Parameter]  *Mandatory Input <BR>
	 *	<DIR>
	 *		Consignor Code <BR>
	 *		Piece Order No. <BR>
	 *	</DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchPieceOrderNo_Click(ActionEvent e) throws Exception
	{
		//#CM718667
		// Set the search condition in the Search screen of Order No. 
		ForwardParameters param = new ForwardParameters();
		//#CM718668
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetCnsgnrCd.getText());
		//#CM718669
		// Piece Order No.
		param.setParameter(ListRetrievalOrdernoBusiness.PIECEORDERNO_KEY, txt_PieceOrderNo.getText());
		//#CM718670
		// Case/Piece flag 
		param.setParameter(ListRetrievalOrdernoBusiness.CASE_PIECE_KEY, RetrievalSupportParameter.LISTBOX_PIECE);
		//#CM718671
		// Work Status (other than Deleted) 
		//#CM718672
		// Do not require to set.
		//#CM718673
		// search key
		param.setParameter(ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM718674
		// Both Order/Item 
		param.setParameter(ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ALL);

		//#CM718675
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do", param, "/progress.do");
	}

	//#CM718676
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_TicketNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718677
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718678
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718679
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718680
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718681
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LineNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718682
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_LineNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718683
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_LineNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718684
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_LineNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718685
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718686
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718687
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718688
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718689
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_HostCasePlanQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718690
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostCasePlanQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718691
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostCasePlanQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718692
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostCasePlanQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718693
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718694
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718695
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718696
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718697
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718698
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718699
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718700
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718701
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718702
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_HostPiesePlanQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718703
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostPiesePlanQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718704
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostPiesePlanQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718705
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostPiesePlanQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718706
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718707
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718708
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718709
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718710
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718711
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Input_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718712
	/**
	 * Clicking on the Enter button invokes this.  <BR>
	 * <BR>
	 * Summary: Searches through Picking Plan Info using field items input in the Input area as conditions and displays data in the Preset area. <BR>
	 * <BR>
	 * <DIR>
	 *	1.Set the cursor on the Case Picking Location.  <BR>
	 *	2. Check for mandatory input in the items (count) of the input area. (mandatory input, Number of characters, character properties).  <BR>
	 *	3. Start the schedule.  <BR>
	 *	<DIR>
	 *		 [Parameter]  *Mandatory Input <BR>
	 *		<DIR>
	 *		Consignor Code* <BR>
	 *      Consignor Name<BR>
	 *		Planned Picking Date* <BR>
	 *		Item Code* <BR>
	 *      Item Name<BR>
	 *		 Case Picking Location (Mandatory input in either Case or Piece Picking Location) * <BR>
	 *		 Piece Picking Location (Mandatory of either Case or Piece Picking Location) * <BR>
	 *		Customer Code <BR>
	 *		Customer Name <BR>
	 *		Case Order No.(mandatory: either Case Order No. or Piece Order No.)* <BR>
	 *		 Piece Order No. (Mandatory either Case or Piece Order No.) * <BR>
	 *		 Packed Qty per Case (Mandatory when Host planned Case qty is input) * <BR>
	 *		Packed qty per bundle <BR>
	 *		Host Planned Case Qty (Mandatory of either Host Planned Case or Piece Qty) * <BR>
	 *		Host Planned Piece Qty (Mandatory of either Host Planned Case or Piece Qty) * <BR>
	 *		Case ITF <BR>
	 *		Bundle ITF <BR>
	 *		 Ticket No. (Shipping Ticket No)  <BR>
	 *		 Line No. (Shipping ticket line No.)  <BR>
	 *		Picking Plan unique key* <BR>
	 *		Last update date/time* <BR>
	 *		 Preset line No. * <BR>
	 *		</DIR>
	 *	</DIR>
	 *	<BR>
	 *	4.If the schedule result is true, update the target data to be modified in the preset area, using the info in the input area,  <BR>
	 *	5. If the schedule result is true, return the color of the modifying target line to its default color.  <BR>
	 *	6. If the schedule result is true, set the ViewState preset line No. for the default (default value: -1).  <BR>
	 *	7. If the schedule result is true, disable the Enter button and the Clear button  <BR>
	 *	8. Clear the contents of the input area if the schedule result is true. <BR>
	 *	9. Output the message obtained from the schedule to the screen.  <BR>
	 *	<BR>
	 *	 Row No. list of listcell  <BR>
	 *	<DIR>
	 *		3.Case Picking Location<BR>
	 *		4.Customer Code <BR>
	 *		5.Case Order No.<BR>
	 *		6.Packed Qty per Case <BR>
	 *		7. Host planned Case qty  <BR>
	 *		8.Case ITF <BR>
	 *		9. Ticket No. (Shipping Ticket No)  <BR>
	 *		10. Piece Picking Location <BR>
	 *		11.Customer Name <BR>
	 *		12.Piece Order No.<BR>
	 *		13.Packed qty per bundle <BR>
	 *		14.Host Planned Piece Qty <BR>
	 *		15.Bundle ITF <BR>
	 *		16. Line No. (Shipping ticket line No.)  <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		//#CM718713
		// Set the cursor on the Case Picking Location. 
		setFocus(txt_CaseRtrivlLct);

		//#CM718714
		// Pattern matching characters 
		txt_CaseRtrivlLct.validate(false);
		txt_PieceRtrivlLct.validate(false);
		txt_CustomerCode.validate(false);
		txt_CustomerName.validate(false);
		txt_CaseOrderNo.validate(false);
		txt_PieceOrderNo.validate(false);
		txt_CaseEntering.validate(false);
		txt_HostCasePlanQty.validate(false);
		txt_CaseItf.validate(false);
		txt_BundleEntering.validate(false);
		txt_HostPiesePlanQty.validate(false);
		txt_BundleItf.validate(false);
		txt_TicketNo.validate(false);
		txt_LineNo.validate(false);

		//#CM718715
		// Check the input characters for eWareNavi. 
		if (!checkContainNgText())
		{
			return;
		}

		Connection conn = null;

		try
		{
			//#CM718716
			// Set for the schedule parameter: 
			//#CM718717
			// Input area 
			RetrievalSupportParameter param = new RetrievalSupportParameter();
			//#CM718718
			// Shipping Plan unique key 
			param.setRetrievalPlanUkey(this.getViewState().getString(RETRIEVALPLANUKEY_KEY));
			//#CM718719
			// Last update date/time
			param.setLastUpdateDate(WmsFormatter.getTimeStampDate(this.getViewState().getString(LASTUPDATEDATE_KEY)));
			//#CM718720
			// Consignor Code
			param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
			//#CM718721
			// Consignor Name
			param.setConsignorName(lbl_JavaSetCnsgnrNm.getText());
			//#CM718722
			// Planned Picking Date
			param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_FDate.getDate()));
			//#CM718723
			// Item Code
			param.setItemCode(lbl_JavaSetItemCd.getText());
			//#CM718724
			// Item Name
			param.setItemName(lbl_JavaSetItemNm.getText());
			//#CM718725
			// Case Picking Location
			param.setCaseLocation(txt_CaseRtrivlLct.getText());
			//#CM718726
			// Piece Picking Location 
			param.setPieceLocation(txt_PieceRtrivlLct.getText());
			//#CM718727
			// Customer Code
			param.setCustomerCode(txt_CustomerCode.getText());
			//#CM718728
			// Customer Name
			param.setCustomerName(txt_CustomerName.getText());
			//#CM718729
			// Case Order No.
			param.setCaseOrderNo(txt_CaseOrderNo.getText());
			//#CM718730
			// Piece Order No.
			param.setPieceOrderNo(txt_PieceOrderNo.getText());
			//#CM718731
			// Packed Qty per Case
			param.setEnteringQty(txt_CaseEntering.getInt());
			//#CM718732
			// Packed qty per bundle
			param.setBundleEnteringQty(txt_BundleEntering.getInt());
			//#CM718733
			// Host planned Case qty 
			param.setPlanCaseQty(txt_HostCasePlanQty.getInt());
			//#CM718734
			// Host Planned Piece Qty
			param.setPlanPieceQty(txt_HostPiesePlanQty.getInt());
			//#CM718735
			// Case ITF
			param.setITF(txt_CaseItf.getText());
			//#CM718736
			// Bundle ITF
			param.setBundleITF(txt_BundleItf.getText());
			//#CM718737
			// Ticket No. 
			param.setShippingTicketNo(txt_TicketNo.getText());
			//#CM718738
			// Line No. 
			param.setShippingLineNo(txt_LineNo.getInt());
			
			//#CM718739
			// Set for the schedule parameter: 
			//#CM718740
			// Preset area 
			RetrievalSupportParameter[] listparam = null;

			//#CM718741
			// Set null if no data in the preset area. 
			if (lst_SRetrievalPlanModify.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				//#CM718742
				// Set the value, if any data exist. 
				listparam = setListParam(this.getViewState().getInt(LINENO_KEY));
			}

			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalPlanModifySCH();

			if (schedule.check(conn, param, listparam))
			{
				//#CM718743
				// Update the data in the line to be modified. 
				lst_SRetrievalPlanModify.setCurrentRow(this.getViewState().getInt(LINENO_KEY));
				//#CM718744
				// Set the values of the Input area in the preset area. 
				setList();
				//#CM718745
				// Return the selected line color to the default. 
				lst_SRetrievalPlanModify.resetHighlight();

				//#CM718746
				// Restore the status to Default. 
				this.getViewState().setInt(LINENO_KEY, -1);

				//#CM718747
				// Disable to click a button. 
				//#CM718748
				// "Enter" button 
				btn_Input.setEnabled(false);
				//#CM718749
				// "Clear" button 
				btn_Clear.setEnabled(false);

				//#CM718750
				// Clear all field items in the Input area. 
				btn_Clear_Click(e);
			}

			//#CM718751
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
				//#CM718752
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

	//#CM718753
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718754
	/**
	 * Clicking on the Clear button invokes this.  <BR>
	 * <BR>
	 * Summary: Clears the input area.  <BR>
	 * <BR>
	 * <DIR>
	 *	1. Return the field item in the input area to the initial value.  <BR>
	 *	<DIR>
	 *		- For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method.  <BR>
	 *	</DIR>
	 *	2.Set the cursor on the Case Picking Location.  <BR>
	 *	3. Maintain the contents of preset area.  <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM718755
		// Set the initial value. 
		txt_CaseRtrivlLct.setText("");
		txt_PieceRtrivlLct.setText("");
		txt_CustomerCode.setText("");
		txt_CustomerName.setText("");
		txt_CaseOrderNo.setText("");
		txt_PieceOrderNo.setText("");
		txt_CaseEntering.setText("");
		txt_BundleEntering.setText("");
		txt_HostCasePlanQty.setText("");
		txt_HostPiesePlanQty.setText("");
		txt_CaseItf.setText("");
		txt_BundleItf.setText("");
		txt_TicketNo.setText("");
		txt_LineNo.setText("");

		//#CM718756
		// Set the cursor on the Case Picking Location. 
		setFocus(txt_CaseRtrivlLct);
	}

	//#CM718757
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ModifySubmit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718758
	/**
	 * Clicking on Modify/Add button invokes this.  <BR>
	 * <BR>
	 * Summary: Modifies/Adds the Picking plan data using the info in the Preset area. <BR>
	 * <BR>
	 * <DIR>
	 *	1.Set the cursor on the Case Picking Location.  <BR>
	 *	2. Display the dialog box to allow to confirm to modify/add it or not.  <BR>
	 *	<DIR>
	 *		 Do you modify and add this?  <BR>
	 *		 [Dialog for confirming: Cancel]  <BR>
	 *		<DIR>
	 *			 Disable to do anything.
	 *		</DIR>
	 *		 [Dialog for confirming: OK] <BR>
	 *		<DIR>
	 *			1. Start the schedule. <BR>
	 *			<DIR>
	 *				 [Parameter] <BR>
	 *				<DIR>
	 *					Worker Code* <BR>
	 *					 Password * 
	 *					 Case Picking Location (for display) <BR>
	 *					 Piece Picking Location (for display) <BR>
	 *					 Customer Code (for display)  <BR>
	 *					 Case Order No. (for display) <BR>
	 *					 Piece Order No. (for display) <BR>
	 *					Consignor Code <BR>
	 *					Planned Picking Date <BR>
	 *					Item Code <BR>
	 *					Case Picking Location<BR>
	 *					 Piece Picking Location <BR>
	 *					Customer Code <BR>
	 *					Customer Name <BR>
	 *					Case Order No.<BR>
	 *					Piece Order No.<BR>
	 *					Packed Qty per Case <BR>
	 *					Packed qty per bundle <BR>
	 *					 Host planned Case qty  <BR>
	 *					Host Planned Piece Qty <BR>
	 *					Case ITF <BR>
	 *					Bundle ITF <BR>
	 *					 Ticket No. (Shipping Ticket No)  <BR>
	 *					 Line No. (Shipping ticket line No.)  <BR>
	 *					Picking Plan unique key* <BR>
	 *					Last update date/time* <BR>
	 *					Terminal No.* <BR>
	 *					 Preset line No. * <BR>
	 *				</DIR>
	 *				<BR>
	 *				 [Returned data] <BR>
	 *				<DIR>
	 *					Consignor Code <BR>
	 *					 Consignor Name (with the latest Added Date/Time)  <BR>
	 *					Planned Picking Date <BR>
	 *					Item Code <BR>
	 *					Item Name (Latest Added Date/Time)  <BR>
	 *					Case Picking Location<BR>
	 *					 Piece Picking Location <BR>
	 *					Customer Code <BR>
	 *					Customer Name <BR>
	 *					Case Order No.<BR>
	 *					Piece Order No.<BR>
	 *					Packed Qty per Case <BR>
	 *					Packed qty per bundle <BR>
	 *					 Host planned Case qty  <BR>
	 *					Host Planned Piece Qty <BR>
	 *					Case ITF <BR>
	 *					Bundle ITF <BR>
	 *					 Ticket No. (Shipping Ticket No)  <BR>
	 *					 Line No. (Shipping ticket line No.)  <BR>
	 *					Picking Plan unique key <BR>
	 *					Last update date/time <BR>
	 *				</DIR>
	 *			</DIR>
	 *			<BR>
	 *			2. Display the Preset data again based on the returned data if the schedule result is true.<BR>
	 *			3. Clear all the field items in the Input area if the schedule result is true.<BR>
	 *			4. If the schedule result is true, set the ViewState preset line No. for the default (default value: -1). <BR>
	 *			5. Disable the Enter button and the Clear button if the schedule result is true.<BR>
	 *			6. If the schedule result is true, return the color of the modifying target line to its default color. <BR>
	 *			7. Output the message obtained from the schedule to the screen. <BR>
	 *		</DIR>
	 *	</DIR>
	 *	<BR>
	 *	 Row No. list of listcell <BR>
	 *	<DIR>
	 *		3.Case Picking Location<BR>
	 *		4.Customer Code <BR>
	 *		5.Case Order No.<BR>
	 *		6.Packed Qty per Case <BR>
	 *		7. Host planned Case qty  <BR>
	 *		8.Case ITF <BR>
	 *		9. Ticket No. (Shipping Ticket No)  <BR>
	 *		10. Piece Picking Location <BR>
	 *		11.Customer Name <BR>
	 *		12.Piece Order No.<BR>
	 *		13.Packed qty per bundle <BR>
	 *		14.Host Planned Piece Qty <BR>
	 *		15.Bundle ITF <BR>
	 *		16. Line No. (Shipping ticket line No.)  <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_ModifySubmit_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM718759
			// Set the cursor on the Case Picking Location. 
			setFocus(txt_CaseRtrivlLct);

			//#CM718760
			// Set for the schedule parameter: 
			RetrievalSupportParameter[] param = null;
			//#CM718761
			// Set all the data in the preset area. 
			param = setListParam(-1);

			if (param == null || param.length <= 0)
			{
				//#CM718762
				// Set the message. 
				//#CM718763
				// 6003013=There was no target data to modify. 
				message.setMsgResourceKey("6003013");
				return;
			}
			//#CM718764
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalPlanModifySCH();

			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) schedule.startSCHgetParams(conn, param);

			//#CM718765
			// When error etc. occurs, receive the relevant message and quit. 
			//#CM718766
			// Disable to roll back any data even if its length is 0, because re-search fails to return data at all in some case. 
			//#CM718767
			// In such case, ensure that the Count of data of the preset area becomes 0. 
			if (viewParam == null)
			{
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM718768
			// Commit it if completed successfully. 
			conn.commit();

			//#CM718769
			// If the process completed normally, display the Preset area again based on the returned data. 
			//#CM718770
			// Delete the previous Preset area. 
			lst_SRetrievalPlanModify.clearRow();

			//#CM718771
			// Preset area 
			setList(viewParam);

			//#CM718772
			// Clear the Input area. 
			btn_Clear_Click(e);

			//#CM718773
			// Restore the status to Default. 
			this.getViewState().setInt(LINENO_KEY, -1);

			//#CM718774
			// Disable to click a button. 
			//#CM718775
			// "Enter" button 
			btn_Input.setEnabled(false);
			//#CM718776
			// "Clear" button 
			btn_Clear.setEnabled(false);

			//#CM718777
			// Return the selected line color to the default. 
			lst_SRetrievalPlanModify.resetHighlight();
			
			//#CM718778
			// The following processes are not required depending on the specifications.  
			//#CM718779
			// Disable the buttons in the Preset if the count of the preset data becomes 0. 
			if(viewParam != null && viewParam.length == 0)
			{
				btn_ModifySubmit.setEnabled(false);
				btn_AllDelete.setEnabled(false);
			}

			//#CM718780
			// Set the message. 
			message.setMsgResourceKey(schedule.getMessage());

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
				//#CM718781
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

	//#CM718782
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllDelete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718783
	/**
	 * Clicking on Delete All button invokes this. <BR>
	 * <BR>
	 * Summary: Deletes all info displayed in the preset area.<BR>
	 * <BR>
	 * <DIR>
	 *	1.Display a dialog box to allow to confirm to delete all the info in the preset area.<BR>
	 *	<DIR>
	 *		 Do you delete all the data? <BR>
	 *		 [Dialog for confirming: Cancel] <BR>
	 *		<DIR>
	 *			 Disable to do anything.
	 *		</DIR>
	 *		 [Dialog for confirming: OK] <BR>
	 *		<DIR>
	 *			1. Start the schedule. <BR>
	 *			<DIR>
	 *				 [Parameter]  *Mandatory Input<BR>
	 *				<DIR>
	 *					Picking Plan unique key* <BR>
	 *					Last update date/time* <BR>
	 *					 Preset line No. * <BR>
	 *				</DIR>
	 *			</DIR>
	 *			2. Clear all the field items in the Input area if the schedule result is true.<BR>
	 *			3. Delete all info displayed in the Preset if the schedule result is true.<BR>
	 *			4.Set the default (initial value: -1) for the preset line No. of ViewState if the schedule result is true.<BR>
	 *			5.Disable the Modify/Add button, the Delete All button, the Enter button, and the Clear button if the schedule result is true.<BR>
	 *			6. Output the message obtained from the schedule to the screen. <BR>
	 *			7.Set the cursor on the Case Picking Location. 
	 *		</DIR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_AllDelete_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM718784
			// Set for the schedule parameter: 
			RetrievalSupportParameter[] listparam = null;

			Vector vecParam = new Vector();

			for (int i = 1; i < lst_SRetrievalPlanModify.getMaxRows(); i++)
			{
				//#CM718785
				// Designate the line. 
				lst_SRetrievalPlanModify.setCurrentRow(i);

				//#CM718786
				// Set for the schedule parameter: 
				RetrievalSupportParameter param = new RetrievalSupportParameter();
				//#CM718787
				// Preset line No. 
				param.setRowNo(i);
				//#CM718788
				// Picking Plan unique key
				param.setRetrievalPlanUkey(CollectionUtils.getString(0, lst_SRetrievalPlanModify.getValue(0)));
				//#CM718789
				// Last update date/time
				param.setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(1, lst_SRetrievalPlanModify.getValue(0))));

				vecParam.addElement(param);
			}

			if (vecParam.size() > 0)
			{
				//#CM718790
				// Set a value if any preset data to be set. 
				listparam = new RetrievalSupportParameter[vecParam.size()];
				vecParam.copyInto(listparam);
			}
			else
			{
				//#CM718791
				// Set null if no preset data to be set. 
				listparam = null;
			}

			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalPlanModifySCH();

			if (schedule.startSCH(conn, listparam))
			{
				//#CM718792
				// Commit it if the process completed successfully. 
				conn.commit();

				//#CM718793
				// If the result is true, clear all field items in the input area and the preset area. 
				//#CM718794
				// Clear the Input area. 
				btn_Clear_Click(e);

				//#CM718795
				// Delete all lines in the preset area.
				lst_SRetrievalPlanModify.clearRow();

				//#CM718796
				// Return the modified status to the default. 
				this.getViewState().setInt(LINENO_KEY, -1);

				//#CM718797
				// Disable to click a button. 
				//#CM718798
				// "Enter" button 
				btn_Input.setEnabled(false);
				//#CM718799
				// "Clear" button 
				btn_Clear.setEnabled(false);
				//#CM718800
				// "Modify/Add" button 
				btn_ModifySubmit.setEnabled(false);
				//#CM718801
				// "Delete All" button 
				btn_AllDelete.setEnabled(false);

				//#CM718802
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				//#CM718803
				// Roll-back when closing with error. 
				conn.rollback();

				//#CM718804
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());
			}

			//#CM718805
			// Set the cursor on the Case Picking Location. 
			setFocus(txt_CaseRtrivlLct);
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM718806
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

	//#CM718807
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRetrievalPlanModify_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718808
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRetrievalPlanModify_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718809
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRetrievalPlanModify_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718810
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRetrievalPlanModify_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM718811
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRetrievalPlanModify_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718812
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRetrievalPlanModify_Change(ActionEvent e) throws Exception
	{
	}

	//#CM718813
	/**
	 * Clicking on Delete/Modify button invokes this. <BR>
	 * <BR>
	 * Summary of Delete button: Deletes the corresponding data in the Preset area. <BR>
	 * <BR>
	 * <DIR>
	 *	1.Display a dialog box to allow to confirm to delete the info in the preset area.<BR>
	 *	<DIR>
	 *		 Do you delete it? <BR>
	 *		 [Dialog for confirming: Cancel] <BR>
	 *		<DIR>
	 *			 Disable to do anything.
	 *		</DIR>
	 *		 [Dialog for confirming: OK] <BR>
	 *		<DIR>
	 *			1. Start the schedule. <BR>
	 *			<DIR>
	 *				 [Parameter]  *Mandatory Input<BR>
	 *				<DIR>
	 *					 Preset line No.  <BR>
	 *					Picking Plan unique key* <BR>
	 *					Last update date/time* <BR>
	 *				</DIR>
	 *			</DIR>
	 *			2. Clear all the field items in the Input area if the schedule result is true.<BR>
	 *			3.Delete the corresponding data in the Preset if the schedule result is true.<BR>
	 *			4.Set the default (initial value: -1) for the preset line No. of ViewState if the schedule result is true.<BR>
	 *			5. Disable the Enter button and the Clear button if the schedule result is true.<BR>
	 *			6.If no info exists in the Preset area, disable the Modify/Add button and the Delete All button. <BR>
	 *			7. Output the message obtained from the schedule to the screen. <BR>
	 *			8.Set the cursor on the Case Picking Location. <BR>
	 *		</DIR>
	 *	</DIR>
	 * </DIR>
	 * <BR>
	 * <BR>
	 * Summary of Modify button: Changes the status of the corresponding data in the preset area to Modified status. <BR>
	 * <BR>
	 * <DIR>
	 *	1. Display the selected info on the upper input area. <BR>
	 *	2. Change the color of the selected info to light yellow. <BR>
	 *	3.Set the current line for the preset line No. of ViewState. <BR>
	 *	4.Set the cursor on the Case Picking Location. <BR>
	 *	5.Enable Enter button and Clear button. <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRetrievalPlanModify_Click(ActionEvent e) throws Exception
	{
		//#CM718814
		// Upon clicking on "Delete" button: 
		if (lst_SRetrievalPlanModify.getActiveCol() == 1)
		{
			Connection conn = null;
			try
			{
				//#CM718815
				// Set the current line. 
				lst_SRetrievalPlanModify.setCurrentRow(lst_SRetrievalPlanModify.getActiveRow());

				//#CM718816
				// Set for the schedule parameter: 
				RetrievalSupportParameter[] param = new RetrievalSupportParameter[1];
				param[0] = new RetrievalSupportParameter();

				//#CM718817
				// Preset line No. 
				param[0].setRowNo(lst_SRetrievalPlanModify.getActiveRow());
				//#CM718818
				// Picking Plan unique key
				param[0].setRetrievalPlanUkey(CollectionUtils.getString(0, lst_SRetrievalPlanModify.getValue(0)));
				//#CM718819
				// Last update date/time
				param[0].setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(1, lst_SRetrievalPlanModify.getValue(0))));

				conn = ConnectionManager.getConnection(DATASOURCE_NAME);

				WmsScheduler schedule = new RetrievalPlanModifySCH();

				if (schedule.startSCH(conn, param))
				{
					//#CM718820
					// Commit it if the process completed successfully. 
					conn.commit();

					//#CM718821
					// Clear the Input area. 
					btn_Clear_Click(e);

					//#CM718822
					// Delete the deleted Preset line. 
					lst_SRetrievalPlanModify.removeRow(lst_SRetrievalPlanModify.getActiveRow());

					//#CM718823
					// Return the modified status to the default. 
					this.getViewState().setInt(LINENO_KEY, -1);

					//#CM718824
					// Disable to click a button. 
					//#CM718825
					// "Enter" button 
					btn_Input.setEnabled(false);
					//#CM718826
					// "Clear" button 
					btn_Clear.setEnabled(false);

					//#CM718827
					// Return the selected line color to the default. 
					lst_SRetrievalPlanModify.resetHighlight();

					//#CM718828
					// If no info exists in the Preset area, disable the Modify/Add button, the Delete All button, the Enter button, and the Clear button. 
					if (lst_SRetrievalPlanModify.getMaxRows() == 1)
					{
						//#CM718829
						// Disable to click a button. 
						//#CM718830
						// "Modify/Add" button 
						btn_ModifySubmit.setEnabled(false);
						//#CM718831
						// "Delete All" button 
						btn_AllDelete.setEnabled(false);
					}

					//#CM718832
					// Set the message. 
					message.setMsgResourceKey(schedule.getMessage());
				}
				else
				{
					//#CM718833
					// Roll-back when closing with error. 
					conn.rollback();
					//#CM718834
					// Set the message. 
					message.setMsgResourceKey(schedule.getMessage());
				}

				//#CM718835
				// Set the cursor on the Case Picking Location. 
				setFocus(txt_CaseRtrivlLct);
			}
			catch (Exception ex)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
			}
			finally
			{
				try
				{
					//#CM718836
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
		//#CM718837
		// Upon clicking on Modify button (Execute operations to modify): 
		else if (lst_SRetrievalPlanModify.getActiveCol() == 2)
		{
			//#CM718838
			// Set the current line. 
			lst_SRetrievalPlanModify.setCurrentRow(lst_SRetrievalPlanModify.getActiveRow());
			//#CM718839
			// Case Picking Location
			txt_CaseRtrivlLct.setText(lst_SRetrievalPlanModify.getValue(3));
			//#CM718840
			// Customer Code
			txt_CustomerCode.setText(lst_SRetrievalPlanModify.getValue(4));
			//#CM718841
			// Case Order No.
			txt_CaseOrderNo.setText(lst_SRetrievalPlanModify.getValue(5));
			//#CM718842
			// Packed Qty per Case
			txt_CaseEntering.setText(lst_SRetrievalPlanModify.getValue(6));
			//#CM718843
			// Host planned Case qty 
			txt_HostCasePlanQty.setText(lst_SRetrievalPlanModify.getValue(7));
			//#CM718844
			// Case ITF
			txt_CaseItf.setText(lst_SRetrievalPlanModify.getValue(8));
			//#CM718845
			// Ticket No. 
			txt_TicketNo.setText(lst_SRetrievalPlanModify.getValue(9));
			//#CM718846
			// Piece Picking qty 
			txt_PieceRtrivlLct.setText(lst_SRetrievalPlanModify.getValue(10));
			//#CM718847
			// Customer Name
			txt_CustomerName.setText(lst_SRetrievalPlanModify.getValue(11));
			//#CM718848
			// Piece Order No.
			txt_PieceOrderNo.setText(lst_SRetrievalPlanModify.getValue(12));
			//#CM718849
			// Packed qty per bundle
			txt_BundleEntering.setText(lst_SRetrievalPlanModify.getValue(13));
			//#CM718850
			// Host Planned Piece Qty
			txt_HostPiesePlanQty.setText(lst_SRetrievalPlanModify.getValue(14));
			//#CM718851
			// Bundle ITF
			txt_BundleItf.setText(lst_SRetrievalPlanModify.getValue(15));
			//#CM718852
			// Line No. 
			txt_LineNo.setText(lst_SRetrievalPlanModify.getValue(16));

			//#CM718853
			// Store in ViewState. 
			//#CM718854
			// Set the preset line No. in ViewState to determine be in Modified status or not by clicking the Modify button. 
			this.getViewState().setInt(LINENO_KEY, lst_SRetrievalPlanModify.getActiveRow());
			//#CM718855
			// Picking Plan unique key (Hidden field item) 
			this.getViewState().setString(RETRIEVALPLANUKEY_KEY, CollectionUtils.getString(0, lst_SRetrievalPlanModify.getValue(0)));
			//#CM718856
			// Last update date/time(hidden field item) 
			this.getViewState().setString(LASTUPDATEDATE_KEY, CollectionUtils.getString(1, lst_SRetrievalPlanModify.getValue(0)));

			//#CM718857
			// Change the color of modified line to Yellow. 
			lst_SRetrievalPlanModify.setHighlight(lst_SRetrievalPlanModify.getActiveRow());

			//#CM718858
			// Enable "Clear" button. 
			//#CM718859
			// "Enter" button 
			btn_Input.setEnabled(true);
			//#CM718860
			// "Clear" button 
			btn_Clear.setEnabled(true);

			//#CM718861
			// Set the cursor on the Case Picking Location. 
			setFocus(txt_CaseRtrivlLct);
		}
	}
}
//#CM718862
//end of class
