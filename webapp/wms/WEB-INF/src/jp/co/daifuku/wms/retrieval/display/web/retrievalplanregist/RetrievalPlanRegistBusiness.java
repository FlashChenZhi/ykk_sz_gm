// $Id: RetrievalPlanRegistBusiness.java,v 1.2 2007/02/07 04:19:33 suresh Exp $

//#CM719155
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalplanregist;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
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
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalcustomer.ListRetrievalCustomerBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievallocation.ListRetrievalLocationBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplanregist.ListRetrievalPlanRegistBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalPlanRegistSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM719156
/**
 * Designer : H.Akiyama <BR>
 * Maker : H.Akiyama <BR>
 * <BR>
 * Allow this class to add Picking Plan. <BR>
 * Set the contents entered via screen, and<BR>
 * allow the schedule to add the Picking Plan based on the parameter.<BR>
 * Receive the result from the schedule. Receive true if the process completed normally.<BR>
 * Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 * Output the message obtained from the schedule to the screen, as the schedule result. <BR>
 * Allow this screen to commit and roll back the transaction. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process by clicking on the "Enter" button (<CODE>btn_Input_Click()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *  Set the contents entered via the input area for the parameter, and allow the schedule to check the input condition based on the parameter. <BR>
 *  Receive the result from the schedule. Receive true if the process completed normally.<BR>
 *  Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 *  Output the message obtained from the schedule to the screen, as the schedule result. <BR>
 *  If the result is true, add the info in the input area to the preset area. <BR>
 *  Clicking on the Enter button after clicking the Modify button updates the target preset data to be modified with the info in the input area. <BR>
 *  <BR>
 * 	 [Parameter]  *Mandatory Input<BR>
 * 	<BR>
 * 	<DIR>
 * 		Worker Code* <BR>
 * 		 Password * <BR>
 * 		Consignor Code* <BR>
 * 		Consignor Name <BR>
 * 		Planned Picking Date* <BR>
 * 		Item Code* <BR>
 * 		Item Name <BR>
 *      Case/Piece division *<BR>
 * 		 Picking Location  <BR>
 * 		Case Picking Location <BR>
 * 		 Piece Picking Location  <BR>
 * 		Customer Code*2 <BR>
 * 		Customer Name*2 <BR>
 *      Order No.<BR>
 *      Case Order No.<BR>
 *      Piece Order No.<BR>
 * 		Packed Qty per Case*3 <BR>
 * 		Packed qty per bundle <BR>
 * 		 Host planned Case qty *1 *3<BR>
 * 		Host Planned Piece Qty*1 *4<BR>
 * 		Case ITF <BR>
 * 		Bundle ITF <BR>
 * 		 Ticket No.  <BR>
 * 		Line No <BR>
 *		<BR>
 * 		*1 <BR>
 * 		Select either one for mandatory input. <BR>
 * 		*2 <BR>
 * 		Mandatory input if the Order No. is input.  <BR>
 * 		*3 <BR>
 * 		Mandatory input when Case is selected for Case/Piece division  <BR>
 * 		*4 <BR>
 * 		 Mandatory input for Case/Piece division "Piece" selected <BR>
 * 	</DIR>
 *  <BR>
 *  [Data for Output]  <BR>
 *  <BR>
 * 	<DIR>
 *  	Consignor Code <BR>
 *  	Consignor Name <BR>
 *  	Planned Picking Date <BR>
 *  	Item Code <BR>
 *  	Item Name <BR>
 * 		 Division  <BR>
 *  	 Picking Location  <BR>
 * 		Customer Code <BR>
 * 		Customer Name <BR>
 *      Order No.<BR>
 *  	Packed Qty per Case <BR>
 *  	Packed qty per bundle <BR>
 *  	 Host planned Case qty  <BR>
 *  	Host Planned Piece Qty <BR>
 *  	Case ITF <BR>
 *  	Bundle ITF <BR>
 * 		 Ticket No.  <BR>
 * 		 Line No.  <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Add" button (<CODE>btn_Submit_Click()</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *  Set the contents input via preset area for the parameter, and <BR>
 *  allow the schedule to add the Picking Plan based on the parameter.<BR>
 *  Receive the result from the schedule. Receive true if the process completed normally.<BR>
 *  Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 *  Output the message obtained from the schedule to the screen, as the schedule result. <BR>
 *  Clear the information added in the Preset if the result is true.<BR>
 *  <BR>
 * 	<DIR>
 *   [Parameter]  *Mandatory Input <BR>
 *   <BR>
 * 		Worker Code* <BR>
 * 		 Password * <BR>
 * 		Consignor Code* <BR>
 * 		Consignor Name <BR>
 * 		Planned Picking Date* <BR>
 * 		Item Code* <BR>
 * 		Item Name <BR>
 *      Case/Piece division *<BR>
 * 		 Picking Location  <BR>
 * 		Case Picking Location <BR>
 * 		 Piece Picking Location  <BR>
 * 		Customer Code*2 <BR>
 * 		Customer Name*2 <BR>
 *      Order No.<BR>
 *      Case Order No.<BR>
 *      Piece Order No.<BR>
 * 		Packed Qty per Case*3 <BR>
 * 		Packed qty per bundle <BR>
 * 		 Host planned Case qty *1 *3<BR>
 * 		Host Planned Piece Qty*1 *4<BR>
 * 		Case ITF <BR>
 * 		Bundle ITF <BR>
 * 		 Ticket No.  <BR>
 * 		Line No <BR>
 *  </DIR>
 *  <BR>
 *  <DIR>
 *   Row No. list of listcell <BR>
 * 		<DIR>
 * 		0. Case/Piece division (HIDDEN)  <BR>
 * 		1. "Cancel" button  <BR>
 * 		2. "Modify" button <BR>
 * 		3.Consignor Code <BR>
 * 		4.Planned Picking Date <BR>
 * 		5.Item Code <BR>
 *      6. Division  <BR>
 *      7. Picking Location  <BR>
 *      8.Customer Code <BR>
 * 		9.Order No. <BR>
 * 		10.Packed Qty per Case <BR>
 * 		11. Host planned Case qty  <BR>
 * 		12.Case ITF <BR>
 * 		13. Ticket No.  <BR>
 * 		14.Consignor Name <BR>
 * 		15.Item Name <BR>
 * 		16.Customer Name <BR>
 * 		18.Packed qty per bundle <BR>
 * 		19.Host Planned Piece Qty <BR>
 * 		20.Bundle ITF <BR>
 * 		21. Line No.  <BR>
 * 		</DIR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/27</TD><TD>H.Akiyama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:33 $
 * @author  $Author: suresh $
 */
public class RetrievalPlanRegistBusiness extends RetrievalPlanRegist implements WMSConstants
{
	//#CM719157
	// Class fields --------------------------------------------------

	//#CM719158
	// Address of the shift target. 
	//#CM719159
	/**
	 * Popup Address for searching for Consignor. 
	 */
	protected static final String DO_SEARCH_CONSIGNOR =
		"/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do";

	//#CM719160
	/**
	 * Popup Address for searching for Planned Picking Date 
	 */
	protected static final String DO_SEARCH_RETRIEVALPLANDATE =
		"/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do";

	//#CM719161
	/**
	 * Popup Address for searching for Popup address for searching item list. 
	 */
	protected static final String DO_SEARCH_ITEM =
		"/retrieval/listbox/listretrievalitem/ListRetrievalItem.do";

	//#CM719162
	/**
	 * Popup Address for searching for Picking Location list 
	 */
	protected static final String DO_SEARCH_LOCATION =
		"/retrieval/listbox/listretrievallocation/ListRetrievalLocation.do";

	//#CM719163
	/**
	 * Popup Address for searching for Order No. list 
	 */
	protected static final String DO_SEARCH_ORDER =
		"/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do";

	//#CM719164
	/**
	 * Popup Address for searching for Customer list 
	 */
	protected static final String DO_SEARCH_CUSTOMER =
		"/retrieval/listbox/listretrievalcustomer/ListRetrievalCustomer.do";

	//#CM719165
	/**
	 * Popup Address for searching for Picking Plan 
	 */
	protected static final String DO_SEARCH_RETRIEVALPLAN =
		"/retrieval/listbox/listretrievalplanregist/ListRetrievalPlanRegist.do";

	//#CM719166
	/**
	 * Screen address while invoking the popup screen for searching. 
	 */
	protected static final String DO_SEARCH_PROCESS = "/progress.do";

	//#CM719167
	/**
	 * For ViewState: Preset line No. 
	 */
	protected static final String LINENO = "LINENO";

	//#CM719168
	// Class variables -----------------------------------------------

	//#CM719169
	// Class method --------------------------------------------------

	//#CM719170
	// Constructors --------------------------------------------------

	//#CM719171
	// Public methods ------------------------------------------------

	//#CM719172
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Summary: shows the Initial Display. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Set the default value: -1 for the preset line No. of ViewState. <BR>
	 *    2.Disable Add button and Clear List button. <BR>
	 *    3. Set the initial value of the cursor to Worker Code. <BR>
	 * <BR>
	
	 *    Field item: name [Initial Value] <BR>
	 *    <DIR>
	 *       Consignor Code [If there is only one "Not Worked Consignor of the Shipping Plan Info", show the initial display of Consignor Code.] <BR>
	 *   	 Consignor Name [If Shipping Plan Info contains only one Not Worked Consignor, show the initial display of the Consignor Name] <BR>
	 * 		 Other field items [None]  <BR>
	 * 		 Case/Piece division [Case ]  <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM719173
		// Set the preset line No. in ViewState to determine whether the Modified status was resulted by clicking the Modify button or not. 
		//#CM719174
		// (Default: -1) 
		this.getViewState().setInt(LINENO, -1);

		//#CM719175
		// Initialize the Input area. 
		//#CM719176
		// (Clear the Worker Code and the Password) 
		inputDataClear(true);
		//#CM719177
		// Disable to click a button. 
		setBtnTrueFalse(false);
	}

	//#CM719178
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
			//#CM719179
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM719180
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM719181
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
		//#CM719182
		// Dialog to allow to confirm before Clicking on the "Add" button: "Do you add it?" 
		btn_Submit.setBeforeConfirm("MSG-W0009");

		//#CM719183
		// Dialog to allow to confirm before Clicking on the "Clear List" button: "This clears the list input info. Do you confirm it?" 
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		//#CM719184
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM719185
	/**
	 * Returning from a popup window invokes this method.
	 * Override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE>. <BR>
	 * <BR>
	 * Summary: Obtains the Returned data in the Search screen and set it. <BR>
	 * <BR><DIR>
	 *      1. Obtain the value returned from the Search popup screen. <BR>
	 *      2. Set the screen if the value is not empty. <BR>
	 * <BR></DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM719186
		// Obtain the parameter selected in the listbox. 
		//#CM719187
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM719188
		// Consignor Name
		String consignorname = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORNAME_KEY);
		//#CM719189
		// Planned Picking Date
		Date retrievalplandate =
			WmsFormatter.toDate(param.getParameter
				(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY));
		//#CM719190
		// Item Code
		String itemcode = param.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);
		//#CM719191
		// Item Name
		String itemname = param.getParameter(ListRetrievalItemBusiness.ITEMNAME_KEY);
		//#CM719192
		// Customer Code
		String customercode = param.getParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY);
		//#CM719193
		// Customer Name
		String customername = param.getParameter(ListRetrievalCustomerBusiness.CUSTOMERNAME_KEY);
		//#CM719194
		// Order No.
		String orderno = "";
		//#CM719195
		// Picking Location No. 
		String location = "";
		//#CM719196
		// Case/Piece flag 
		String casepieceflag = param.getParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY);
		//#CM719197
		// Packed Qty per Case
		String caseetr = param.getParameter(ListRetrievalPlanRegistBusiness.CASEETR_KEY);
		//#CM719198
		// Packed qty per bundle
		String bundleetr = param.getParameter(ListRetrievalPlanRegistBusiness.BUNDLEETR_KEY);
		//#CM719199
		// Case ITF
		String caseitf = param.getParameter(ListRetrievalPlanRegistBusiness.CASEITF_KEY);
		//#CM719200
		// Bundle ITF
		String bundleitf = param.getParameter(ListRetrievalPlanRegistBusiness.BUNDLEITF_KEY);

		//#CM719201
		// Set the Picking Location. 
		//#CM719202
		// None 
		if (param.getParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY) != null)
		{
			location = param.getParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY);		
		//#CM719203
		// Case 
		}
		else if(param.getParameter(ListRetrievalLocationBusiness.CASERETRIEVALLOCATION_KEY) != null)
		{
			location = param.getParameter(ListRetrievalLocationBusiness.CASERETRIEVALLOCATION_KEY);					
		}
		else if(param.getParameter(ListRetrievalLocationBusiness.PIECERETRIEVALLOCATION_KEY) != null)
		{
			location = param.getParameter(ListRetrievalLocationBusiness.PIECERETRIEVALLOCATION_KEY);		
		}
		//#CM719204
		// Set the Order No. 
		if (param.getParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY) != null)
		{
			orderno = param.getParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY);	
		}
		else if (param.getParameter(ListRetrievalOrdernoBusiness.CASEORDERNO_KEY) != null)
		{
			orderno = param.getParameter(ListRetrievalOrdernoBusiness.CASEORDERNO_KEY);	
		}
		else if (param.getParameter(ListRetrievalOrdernoBusiness.PIECEORDERNO_KEY) != null)
		{
			orderno = param.getParameter(ListRetrievalOrdernoBusiness.PIECEORDERNO_KEY);	
		}

		//#CM719205
		// Set a value if not empty. 
		//#CM719206
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM719207
		// Consignor Name
		if (!StringUtil.isBlank(consignorname))
		{
			txt_ConsignorName.setText(consignorname);
		}
		//#CM719208
		// Planned Picking Date
		if (!StringUtil.isBlank(retrievalplandate))
		{
			txt_RtrivlPlanDate.setDate(retrievalplandate);
		}
		//#CM719209
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM719210
		// Item Name
		if (!StringUtil.isBlank(itemname))
		{
			txt_ItemName.setText(itemname);
		}
		//#CM719211
		// Case/Piece flag 
		if (!StringUtil.isBlank(casepieceflag))
		{
			if (casepieceflag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
				rdo_CpfCase.setChecked(true);
			else if (casepieceflag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
				rdo_CpfPiece.setChecked(true);
			else
				rdo_CpfAppointOff.setChecked(true);
		}
		//#CM719212
		// Picking Location 
		if (!StringUtil.isBlank(location))
		{
			txt_RetrievalLocation.setText(location);
		}
		//#CM719213
		// Customer Code
		if (!StringUtil.isBlank(customercode))
		{
			txt_CustomerCode.setText(customercode);
		}
		//#CM719214
		// Customer Name
		if (!StringUtil.isBlank(customername))
		{
			txt_CustomerName.setText(customername);
		}
		//#CM719215
		// Order No.
		if (!StringUtil.isBlank(orderno))
		{
			txt_OrderNo.setText(orderno);
		}
		//#CM719216
		// Packed Qty per Case
		if (!StringUtil.isBlank(caseetr))
		{
			txt_CaseEntering.setInt(WmsFormatter.getInt(caseetr));
		}
		//#CM719217
		// Packed qty per bundle
		if (!StringUtil.isBlank(bundleetr))
		{
			txt_BundleEntering.setInt(WmsFormatter.getInt(bundleetr));
		}
		//#CM719218
		// Case ITF
		if (!StringUtil.isBlank(caseitf))
		{
			txt_CaseItf.setText(caseitf);
		}
		//#CM719219
		// Bundle ITF
		if (!StringUtil.isBlank(bundleitf))
		{
			txt_BundleItf.setText(bundleitf);
		}

		//#CM719220
		// Set focus for Worker Code. 
		setFocus(txt_WorkerCode);

	}

	//#CM719221
	// Package methods -----------------------------------------------

	//#CM719222
	// Protected methods ---------------------------------------------
	//#CM719223
	/**
	 * Check for input. 
	 * Set the message if any error and return false. 
	 * 
	 * @return true: Normal, false: Abnormal 
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
		if (!checker.checkContainNgText(txt_ConsignorCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ConsignorName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ItemCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ItemName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_RetrievalLocation))
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
		if (!checker.checkContainNgText(txt_OrderNo))
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

	//#CM719224
	// Private methods -----------------------------------------------

	//#CM719225
	/**
	 * Initialize the input area.  <BR>
	 * <BR>
	 * Summary: Makes the Input area empty.  <BR>
	 *      Clear the Worker Code/Password if workerclearFlg is true.
	 *      Leave Worker Code/Password just as it is when workerclearFlg is false. 
	 * 
	 * <DIR>
	 * 	1. Set the cursor on the Worker code.  <BR>
	 *  2. Initialize the input area.  <BR>
	 * </DIR>
	 * <BR>
	 *  
	 * @throws Exception Report all exceptions. 
	 */
	private void inputDataClear(boolean workerclearFlg) throws Exception
	{
		//#CM719226
		// Set the cursor on the Worker Code. 
		setFocus(txt_WorkerCode);

		//#CM719227
		// Clear the Worker Code/Password if workerclearFlg is true.
		if (workerclearFlg)
		{
			//#CM719228
			// Worker Code
			txt_WorkerCode.setText("");
			//#CM719229
			// Password 
			txt_Password.setText("");
		}

		Connection conn = null;

		try
		{
			//#CM719230
			// Consignor Code
			txt_ConsignorCode.setText("");
			//#CM719231
			// Consignor Name
			txt_ConsignorName.setText("");
			//#CM719232
			// Planned Picking Date
			txt_RtrivlPlanDate.setText("");
			//#CM719233
			// Item Code
			txt_ItemCode.setText("");
			//#CM719234
			// Item Name
			txt_ItemName.setText("");
			//#CM719235
			// Case/Piece division (Case): Initial setting 
			rdo_CpfCase.setChecked(true);
			//#CM719236
			// Case/Piece division (Piece) 
			rdo_CpfPiece.setChecked(false);
			//#CM719237
			// Case/Piece division (None) 
			rdo_CpfAppointOff.setChecked(false);
			//#CM719238
			// Picking Location 
			txt_RetrievalLocation.setText("");
			//#CM719239
			// Customer Code
			txt_CustomerCode.setText("");
			//#CM719240
			// Customer Name
			txt_CustomerName.setText("");
			//#CM719241
			// Order No.
			txt_OrderNo.setText("");
			//#CM719242
			// Packed Qty per Case
			txt_CaseEntering.setText("");
			//#CM719243
			// Host planned Case qty 
			txt_HostCasePlanQty.setText("");
			//#CM719244
			// Case ITF
			txt_CaseItf.setText("");
			//#CM719245
			// Packed qty per bundle
			txt_BundleEntering.setText("");
			//#CM719246
			// Host Planned Piece Qty
			txt_HostPiesePlanQty.setText("");
			//#CM719247
			// Bundle ITF
			txt_BundleItf.setText("");
			//#CM719248
			// Ticket No. 
			txt_TicketNo.setText("");
			//#CM719249
			// Line No. 
			txt_LineNo.setText("");
			
			//#CM719250
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalPlanRegistSCH();

			RetrievalSupportParameter param =
				(RetrievalSupportParameter) schedule.initFind(conn, null);
			//#CM719251
			// For data with only one Consignor code, display the initial display. 
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
				txt_ConsignorName.setText(param.getConsignorName());
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
				//#CM719252
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

	//#CM719253
	/**
	 * Allow this method to set the value in the preset area. <BR>
	 * <BR>
	 * Summary: Sets value for preset area <BR>
	 * <DIR>
	 * 	1.Set a value in the preset area. <BR>
	 *  2.Set the default (initial value: -1) for the preset line No. of ViewState.  <BR>
	 * </DIR>
	 * <BR>
	 *  <DIR>
	 *   Row No. list of listcell <BR>
	 * 		<DIR>
	 * 		<DIR>
	 * 		0. Case/Piece division (HIDDEN)  <BR>
	 * 		1. "Cancel" button  <BR>
	 * 		2."Modify" button <BR>
	 * 		3.Consignor Code <BR>
	 * 		4.Planned Picking Date <BR>
	 * 		5.Item Code <BR>
	 *      6. Division  <BR>
	 *      7. Picking Location  <BR>
	 *      8.Customer Code <BR>
	 * 		9.Order No. <BR>
	 * 		10.Packed Qty per Case <BR>
	 * 		11. Host planned Case qty  <BR>
	 * 		12.Case ITF <BR>
	 * 		13. Ticket No.  <BR>
	 * 		14.Consignor Name <BR>
	 * 		15.Item Name <BR>
	 * 		16.Customer Name <BR>
 	 * 		18.Packed qty per bundle <BR>
 	 * 		19.Host Planned Piece Qty <BR>
 	 * 		20.Bundle ITF <BR>
 	 * 		21. Line No.  <BR>
	 * 		</DIR>
	 *   </DIR>
	 * @throws Exception Report all exceptions.  
	 */
	private void setListRow() throws Exception
	{
		//#CM719254
		// Compile the ToolTip data. 
		ToolTipHelper toolTip = new ToolTipHelper();
		//#CM719255
		// Consignor Name
		toolTip.add(DisplayText.getText("LBL-W0026"), txt_ConsignorName.getText());
		//#CM719256
		// Item Name
		toolTip.add(DisplayText.getText("LBL-W0103"), txt_ItemName.getText());
		//#CM719257
		// Customer Code
		toolTip.add(DisplayText.getText("LBL-W0034"), txt_CustomerCode.getText());
		//#CM719258
		// Customer Name
		toolTip.add(DisplayText.getText("LBL-W0036"), txt_CustomerName.getText());
		//#CM719259
		// Case ITF
		toolTip.add(DisplayText.getText("LBL-W0338"), txt_CaseItf.getText());
		//#CM719260
		// Bundle ITF
		toolTip.add(DisplayText.getText("LBL-W0337"), txt_BundleItf.getText());
		//#CM719261
		// Ticket No. 
		toolTip.add(DisplayText.getText("LBL-W0266"), txt_TicketNo.getText());
		//#CM719262
		// Line No. 
		toolTip.add(DisplayText.getText("LBL-W0109"), txt_LineNo.getText());

		//#CM719263
		// Set the TOOL TIP in the current line. 
		lst_SRetrievalPlanRegist.setToolTip(
			lst_SRetrievalPlanRegist.getCurrentRow(),
			toolTip.getText());

		//#CM719264
		// Case/Piece division (HIDDDEN) 
		lst_SRetrievalPlanRegist.setValue(0, getCasePieceFlagFromInputArea());
		//#CM719265
		// Consignor Code
		lst_SRetrievalPlanRegist.setValue(3, txt_ConsignorCode.getText());
		//#CM719266
		// Planned Picking Date
		lst_SRetrievalPlanRegist.setValue(4, txt_RtrivlPlanDate.getText());
		//#CM719267
		// Item Code
		lst_SRetrievalPlanRegist.setValue(5, txt_ItemCode.getText());
		//#CM719268
		// Division 
		lst_SRetrievalPlanRegist.setValue(6,
			DisplayUtil.getPieceCaseValue(getCasePieceFlag(getCasePieceFlagFromInputArea())));
		//#CM719269
		// Picking Location 
		lst_SRetrievalPlanRegist.setValue(7, txt_RetrievalLocation.getText());
		//#CM719270
		// Customer Code
		lst_SRetrievalPlanRegist.setValue(8, txt_CustomerCode.getText());
		//#CM719271
		// Order No.
		lst_SRetrievalPlanRegist.setValue(9, txt_OrderNo.getText());

		//#CM719272
		// Packed Qty per Case
		if (StringUtil.isBlank(txt_CaseEntering.getText()))
		{
			lst_SRetrievalPlanRegist.setValue(10, "0");
		}
		else
		{
			lst_SRetrievalPlanRegist.setValue(10,
				WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));

		}
		//#CM719273
		// Host planned Case qty 
		if (StringUtil.isBlank(txt_HostCasePlanQty.getText()))
		{
			lst_SRetrievalPlanRegist.setValue(11, "0");
		}
		else
		{
			lst_SRetrievalPlanRegist.setValue(11,
				WmsFormatter.getNumFormat(txt_HostCasePlanQty.getInt()));
		}
		//#CM719274
		// Case ITF
		lst_SRetrievalPlanRegist.setValue(12, txt_CaseItf.getText());
		//#CM719275
		// Ticket No. 
		lst_SRetrievalPlanRegist.setValue(13, txt_TicketNo.getText());
		
		//#CM719276
		// Consignor Name
		lst_SRetrievalPlanRegist.setValue(14, txt_ConsignorName.getText());
		//#CM719277
		// Item Name
		lst_SRetrievalPlanRegist.setValue(15, txt_ItemName.getText());
		//#CM719278
		// Customer Name
		lst_SRetrievalPlanRegist.setValue(16, txt_CustomerName.getText());
		//#CM719279
		// Packed qty per bundle
		if (StringUtil.isBlank(txt_BundleEntering.getText()))
		{
			lst_SRetrievalPlanRegist.setValue(17, "0");
		}
		else
		{
			lst_SRetrievalPlanRegist.setValue(17,
				WmsFormatter.getNumFormat(txt_BundleEntering.getInt()));
		}
		//#CM719280
		// Host Planned Piece Qty
		if (StringUtil.isBlank(txt_HostPiesePlanQty.getText()))
		{
			lst_SRetrievalPlanRegist.setValue(18, "0");
		}
		else
		{
			lst_SRetrievalPlanRegist.setValue(18,
				WmsFormatter.getNumFormat(txt_HostPiesePlanQty.getInt()));
		}
		//#CM719281
		// Bundle ITF
		lst_SRetrievalPlanRegist.setValue(19, txt_BundleItf.getText());
		//#CM719282
		// Line No. 
		lst_SRetrievalPlanRegist.setValue(20, Integer.toString(txt_LineNo.getInt()));
		
		//#CM719283
		// reset the modified status to the default. 
		this.getViewState().setInt(LINENO, -1);

	}

	//#CM719284
	/** Allow this method to set the preset area data in the Parameter class. <BR>
	 * <BR>
	 * Summary: Sets the preset area data in the Parameter class. <BR>
	 * <BR>
	 * 1. Set all preset data, if any new input, in the Parameter class (Preset line No. to be modified = -1). <BR>
	 * 2. If the input data is in process of modifying, set the preset data other than modify target line in the Parameter class. <BR>
	 * 3.Clicking on the Add button sets all the Preset data for the Parameter class. (Line No. of preset to be modified = -1) <BR>
	 * <DIR>
	 *   	 [Parameter]  *Mandatory Input<BR>
	 *   	<DIR>
	 * 			 Target preset line No. for updating * <BR>
	 * 		</DIR>
	 *   	 [Returned data] <BR>
	 *   	<DIR>
	 * 			Array of the Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has Contents of Preset area <BR>
	 * 		</DIR>
	 * </DIR>
	 * @param lineno Listcell line No. 
	 * @return Array of the Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has Contents of Preset area 
	 * @throws Exception Report all exceptions.  
	 */
	private RetrievalSupportParameter[] setListParam(int lineno) throws Exception
	{

		Vector vecParam = new Vector();

		for (int i = 1; i < lst_SRetrievalPlanRegist.getMaxRows(); i++)
		{
			//#CM719285
			// Exclude the line to be modified. 
			if (i == lineno)
			{
				continue;
			}

			//#CM719286
			// Designate the line. 
			lst_SRetrievalPlanRegist.setCurrentRow(i);

			//#CM719287
			// Set for the schedule parameter: 
			RetrievalSupportParameter param = new RetrievalSupportParameter();
			//#CM719288
			// Info in the Input area info 
			//#CM719289
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM719290
			// Password 
			param.setPassword(txt_Password.getText());
			//#CM719291
			// Consignor Code
			param.setConsignorCode(lst_SRetrievalPlanRegist.getValue(3));
			//#CM719292
			// Planned Picking Date
			param.setRetrievalPlanDate(
				WmsFormatter.toParamDate(
					lst_SRetrievalPlanRegist.getValue(4),
					this.httpRequest.getLocale()));
			//#CM719293
			// Item Code
			param.setItemCode(lst_SRetrievalPlanRegist.getValue(5));
			//#CM719294
			// Case/Piece division (set via Hidden field item) 
			param.setCasePieceflg(lst_SRetrievalPlanRegist.getValue(0));
			//#CM719295
			// Picking Location 
			param.setRetrievalLocation(lst_SRetrievalPlanRegist.getValue(7));
			//#CM719296
			// Customer Code
			param.setCustomerCode(lst_SRetrievalPlanRegist.getValue(8));
			//#CM719297
			// Order No.
			param.setOrderNo(lst_SRetrievalPlanRegist.getValue(9));
			//#CM719298
			// Packed Qty per Case
			param.setEnteringQty(Formatter.getInt(lst_SRetrievalPlanRegist.getValue(10)));
			//#CM719299
			// Host planned Case qty 
			param.setPlanCaseQty(Formatter.getInt(lst_SRetrievalPlanRegist.getValue(11)));
			//#CM719300
			// Case ITF
			param.setITF(lst_SRetrievalPlanRegist.getValue(12));
			//#CM719301
			// Ticket No. 
			param.setShippingTicketNo(lst_SRetrievalPlanRegist.getValue(13));
			
			//#CM719302
			// Consignor Name
			param.setConsignorName(lst_SRetrievalPlanRegist.getValue(14));
			//#CM719303
			// Item Name
			param.setItemName(lst_SRetrievalPlanRegist.getValue(15));
			//#CM719304
			// Customer Name
			param.setCustomerName(lst_SRetrievalPlanRegist.getValue(16));
			//#CM719305
			// Packed qty per bundle
			param.setBundleEnteringQty(Formatter.getInt(lst_SRetrievalPlanRegist.getValue(17)));
			//#CM719306
			// Host Planned Piece Qty
			param.setPlanPieceQty(Formatter.getInt(lst_SRetrievalPlanRegist.getValue(18)));
			//#CM719307
			// Bundle ITF
			param.setBundleITF(lst_SRetrievalPlanRegist.getValue(19));
			//#CM719308
			// Line No. 
			param.setShippingLineNo(Formatter.getInt(lst_SRetrievalPlanRegist.getValue(20)));
			//#CM719309
			// Terminal No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());

			//#CM719310
			// Maintain the line No. 
			param.setRowNo(i);

			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			//#CM719311
			// Set a value if any preset data to be set. 
			RetrievalSupportParameter[] listparam = new RetrievalSupportParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			//#CM719312
			// Set null if no preset data to be set. 
			return null;
		}
	}

	//#CM719313
	/**
	 * Obtain the corresponding Picking Parameter.Case/Piece Division from the Input area.Case/Piece Division radio button. <BR>
	 * <BR>
	 * @return Case/Piece division 
	 * @throws Exception Report all exceptions.  
	 */
	private String getCasePieceFlagFromInputArea() throws Exception
	{
		//#CM719314
		// Case/Piece division 
		if (rdo_CpfAppointOff.getChecked())
		{
			//#CM719315
			// None 
			return RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING;
		}
		else if (rdo_CpfCase.getChecked())
		{
			//#CM719316
			// Case 
			return RetrievalSupportParameter.CASEPIECE_FLAG_CASE;
		}
		else if (rdo_CpfPiece.getChecked())
		{
			//#CM719317
			// Piece 
			return RetrievalSupportParameter.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}

	//#CM719318
	/**
	 * Obtain the corresponding Case/Piece division from the Picking Parameter.Case/Piece Division. <BR>
	 * <BR>
	 * @param value Picking Parameter.Case/Piece Division that corresponds to the radio button 
	 * @return Case/Piece division 
	 * @throws Exception Report all exceptions.   
	 */
	private String getCasePieceFlag(String value) throws Exception
	{
		//#CM719319
		// Case/Piece division 
		if (value.equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM719320
			// None 
			return SystemDefine.CASEPIECE_FLAG_NOTHING;
		}
		else if (value.equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
		{
			//#CM719321
			// Case 
			return SystemDefine.CASEPIECE_FLAG_CASE;
		}
		else if (value.equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM719322
			// Piece 
			return SystemDefine.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}

	//#CM719323
	/**
	 * Display the contents of Listcell.Division in the Case/Piece division checkbox of the input area. <BR>
	 * <BR>
	 * @throws Exception Report all exceptions.  
	 */
	private void setCasePieceRBFromList() throws Exception
	{
		if (lst_SRetrievalPlanRegist
			.getValue(0)
			.equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM719324
			// Place a check in None option. 
			rdo_CpfAppointOff.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(false);
		}
		else if (
			lst_SRetrievalPlanRegist.getValue(0).equals(
				RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
		{
			//#CM719325
			// Tick the field of Case. 
			rdo_CpfCase.setChecked(true);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);
		}
		else if (
			lst_SRetrievalPlanRegist.getValue(0).equals(
				RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM719326
			// Tick the field of Piece. 
			rdo_CpfPiece.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);
		}
	}

	//#CM719327
	/**
	 * Allow this method to set acceptability to compile buttons. <BR>
	 * <BR>
	 * Summary: Sets to determine whether to compile buttons after receiving false or true. <BR>
	 * @param  arg status(false or true)
	 * @return None 
	 */
	private void setBtnTrueFalse(boolean arg)
	{
		//#CM719328
		// Disable to click a button. 
		//#CM719329
		// "Add" button 
		btn_Submit.setEnabled(arg);
		//#CM719330
		// "Clear List" button 
		btn_ListClear.setEnabled(arg);
	}

	//#CM719331
	// Event handler methods -----------------------------------------
	//#CM719332
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

	//#CM719333
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
		//#CM719334
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM719335
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM719336
		// "Search" flag: Plan 		
		param.setParameter(
			ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM719337
		// Processing screen ->"Result" screen 
		redirect(DO_SEARCH_CONSIGNOR, param, DO_SEARCH_PROCESS);
	}

	//#CM719338
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
	public void btn_PSearchRetrievalPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM719339
		// Set the search condition in the Search screen of Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM719340
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM719341
		// Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM719342
		// "Search" flag 
		param.setParameter(
			ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM719343
		// Processing screen ->"Result" screen 
		redirect(DO_SEARCH_RETRIEVALPLANDATE, param, DO_SEARCH_PROCESS);
	}

	//#CM719344
	/** 
	 * Clicking on the Search Item Code button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for a parameter and displays the item list listbox using the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]  <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 * 		 Planned Picking Date <BR>
	 *       Item Code <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM719345
		// Set the search condition in the Search screen of Item. 
		ForwardParameters param = new ForwardParameters();
		//#CM719346
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM719347
		// Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM719348
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM719349
		// "Search" flag: Work 
		param.setParameter(
			ListRetrievalItemBusiness.SEARCHITEM_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM719350
		// Processing screen ->"Result" screen 
		redirect(DO_SEARCH_ITEM, param, DO_SEARCH_PROCESS);
	}

	//#CM719351
	/** 
	 * Clicking on the Search Picking Plan button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Picking Plan list using this search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]  <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 * 		 Planned Picking Date <BR>
	 *       Item Code <BR>
	 * 		 Case/Piece division  <BR>
	 * 		 Picking Location  <BR>
	 *       Customer Code <BR>
	 *       Order No. <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PRtrivlPlanSrch_Click(ActionEvent e) throws Exception
	{
		//#CM719352
		// Set the search condition in the Search screen of Item. 
		ForwardParameters param = new ForwardParameters();
		//#CM719353
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM719354
		// Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM719355
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM719356
		// Case/Piece division 
		if(rdo_CpfCase.getChecked())
		{
			//#CM719357
			// Case 
			param.setParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		if(rdo_CpfPiece.getChecked())
		{
			//#CM719358
			// Piece 
			param.setParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		if(rdo_CpfAppointOff.getChecked())
		{
			//#CM719359
			// None 
			param.setParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM719360
		// Picking Location 
		param.setParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY,
			txt_RetrievalLocation.getText());
		//#CM719361
		// Customer Code
		param.setParameter(
			ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY,
			txt_CustomerCode.getText());
		//#CM719362
		// Order No.
		param.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, txt_OrderNo.getText());
		//#CM719363
		// Processing screen ->"Result" screen 
		redirect(DO_SEARCH_RETRIEVALPLAN, param, DO_SEARCH_PROCESS);

	}

	//#CM719364
	/** 
	 * Clicking on the Search Picking Location button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Picking Location list using this search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]  <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 * 		 Planned Picking Date <BR>
	 *       Item Code <BR>
	 * 		 Case/Piece division  <BR>
	 * 		 Picking Location  <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchRetrievalLocation_Click(ActionEvent e) throws Exception
	{
		//#CM719365
		// Set the search condition in the Search screen of Item. 
		ForwardParameters param = new ForwardParameters();
		//#CM719366
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM719367
		// Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM719368
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM719369
		// Case/Piece division 
		if(rdo_CpfCase.getChecked())
		{
			//#CM719370
			// Case 
			param.setParameter(ListRetrievalLocationBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		if(rdo_CpfPiece.getChecked())
		{
			//#CM719371
			// Piece 
			param.setParameter(ListRetrievalLocationBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		if(rdo_CpfAppointOff.getChecked())
		{
			//#CM719372
			// None 
			param.setParameter(ListRetrievalLocationBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM719373
		// Picking Location 
		param.setParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY,
			txt_RetrievalLocation.getText());

		//#CM719374
		// Flag to determine Case Picking Location/Piece Picking Location. 
		param.setParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY,
			RetrievalSupportParameter.LISTBOX_RETRIEVAL);

		//#CM719375
		// Processing screen ->"Result" screen 
		redirect(DO_SEARCH_LOCATION, param, DO_SEARCH_PROCESS);

	}

	//#CM719376
	/** 
	 * Clicking on Search Customer Code button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Customer list using this search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]  <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 * 		 Planned Picking Date <BR>
	 *       Item Code <BR>
	 * 		 Case/Piece division  <BR>
	 * 		 Picking Location  <BR>
	 *       Customer Code <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchCustomerCode_Click(ActionEvent e) throws Exception
	{
		//#CM719377
		// Set the search condition in the Search screen of Item. 
		ForwardParameters param = new ForwardParameters();
		//#CM719378
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM719379
		// Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM719380
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM719381
		// Case/Piece division 
		if(rdo_CpfCase.getChecked())
		{
			//#CM719382
			// Case 
			param.setParameter(ListRetrievalLocationBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		if(rdo_CpfPiece.getChecked())
		{
			//#CM719383
			// Piece 
			param.setParameter(ListRetrievalLocationBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		if(rdo_CpfAppointOff.getChecked())
		{
			//#CM719384
			// None 
			param.setParameter(ListRetrievalLocationBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM719385
		// Picking Location 
		param.setParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY,
			txt_RetrievalLocation.getText());
		//#CM719386
		// Customer Code
		param.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, 
			txt_CustomerCode.getText());

		//#CM719387
		// "Search" flag: Plan 
		param.setParameter(
			ListRetrievalCustomerBusiness.SEARCHCUSTOMER_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM719388
		// Processing screen ->"Result" screen 
		redirect(DO_SEARCH_CUSTOMER, param, DO_SEARCH_PROCESS);
	}

	//#CM719389
	/** 
	 * Clicking on the Search Order No. button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Order No. list using this search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]  <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 * 		 Planned Picking Date <BR>
	 *       Item Code <BR>
	 * 		 Case/Piece division  <BR>
	 * 		 Picking Location  <BR>
	 *       Customer Code <BR>
	 *       Order No. <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchOrderNo_Click(ActionEvent e) throws Exception
	{
		//#CM719390
		// Set the search condition in the Search screen of Order No. 
		ForwardParameters param = new ForwardParameters();
		//#CM719391
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM719392
		// Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM719393
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM719394
		// Case/Piece division 
		if(rdo_CpfCase.getChecked())
		{
			//#CM719395
			// Case 
			param.setParameter(ListRetrievalLocationBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		if(rdo_CpfPiece.getChecked())
		{
			//#CM719396
			// Piece 
			param.setParameter(ListRetrievalLocationBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		if(rdo_CpfAppointOff.getChecked())
		{
			//#CM719397
			// None 
			param.setParameter(ListRetrievalLocationBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM719398
		// Case Picking Location
		param.setParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY,
			txt_RetrievalLocation.getText());
		//#CM719399
		// "Search" flag 
		param.setParameter(
			ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM719400
		// Order No.
		param.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, txt_OrderNo.getText());
		//#CM719401
		// Flag to determine Case Order No./Piece Order No. 
		param.setParameter(ListRetrievalOrdernoBusiness.CASE_PIECE_KEY, RetrievalSupportParameter.LISTBOX_RETRIEVAL);
		//#CM719402
		// Customer Code
		param.setParameter(
			ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY,
			txt_CustomerCode.getText());
		//#CM719403
		// Processing screen ->"Result" screen 
		redirect(DO_SEARCH_ORDER, param, DO_SEARCH_PROCESS);
	}

	//#CM719404
	/** 
	 * Clicking on the Enter button invokes this. <BR>
	 * <BR>
	 * Summary: Checks the input field item in the input area and displays it in the preset area. <BR>
	 * <BR>
	 * <DIR>
	 *   1. Set the cursor on the Worker code. <BR>
	 *   2. Check for mandatory input in the items (count) of the input area. (mandatory input, Number of characters, character properties). <BR>
	 *   3. Start the schedule. <BR>
	 * 	 <DIR>
	 * 	 [Parameter]  *Mandatory Input<BR>
	 * 	<BR>
	 * 	<DIR>
	 * 		Worker Code* <BR>
	 * 		 Password * <BR>
	 * 		Consignor Code* <BR>
	 * 		Consignor Name <BR>
	 * 		Planned Picking Date* <BR>
	 * 		Item Code* <BR>
	 * 		Item Name <BR>
	 * 		Case/Piece division* <BR>
	 * 		 Location No.  <BR>
     * 		Case Picking Location <BR>
     * 		 Piece Picking Location  <BR>
	 * 		Customer Code*2 <BR>
	 * 		Customer Name*2 <BR>
	 * 		Order No. <BR>
	 *      Case Order No.<BR>
	 *      Piece Order No.<BR>
	 * 		Packed Qty per Case*3 <BR>
	 * 		Packed qty per bundle <BR>
	 * 		 Host planned Case qty *1 *3<BR>
	 * 		Host Planned Piece Qty*1 *4<BR>
	 * 		Case ITF <BR>
	 * 		Bundle ITF <BR>
	 * 		 Ticket No.  <BR>
	 * 		Line No <BR>
	 *		<BR>
	 * 		*1 <BR>
	 * 		Select either one for mandatory input. <BR>
	 * 		*2 <BR>
	 * 		Mandatory input if the Order No. is input.  <BR>
	 * 		*3 <BR>
	 * 		Mandatory input when Case is selected for Case/Piece division  <BR>
	 * 		*4 <BR>
	 * 		 Mandatory input for Case/Piece division "Piece" selected <BR>
	 * 	</DIR>
	 *   <BR>
	 *   4. Add to the preset area if the schedule result is true. <BR>
	 *     Clicking on the Enter button after clicking the Modify button updates the target preset data to be modified with the info in the input area. <BR>
	 *   5. If the schedule result is true, enable the Add button and the Clear List button. <BR>
	 *   6. If the schedule result is true, set the ViewState preset line No. for the default (default value: -1). <BR>
	 *   7. Output the message obtained from the schedule to the screen. <BR>
	 * 	 8. Maintain the contents of the input area. <BR>
	 *   <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.   
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		//#CM719405
		// Check for mandatory input. 
		//#CM719406
		// Pattern matching 
		//#CM719407
		// Worker Code
		txt_WorkerCode.validate();
		//#CM719408
		// Password 
		txt_Password.validate();
		//#CM719409
		// Consignor Code
		txt_ConsignorCode.validate();
		//#CM719410
		// Consignor Name
		txt_ConsignorName.validate(false);
		//#CM719411
		// Planned Picking Date
		txt_RtrivlPlanDate.validate();
		//#CM719412
		// Item Code
		txt_ItemCode.validate();
		//#CM719413
		// Item Name
		txt_ItemName.validate(false);		
		//#CM719414
		// Picking Location 
		txt_RetrievalLocation.validate(false);

		//#CM719415
		// Start checking for input. 
		//#CM719416
		// Customer Code and Customer Name are mandatory input when Order No. is input 
		if (!StringUtil.isBlank(txt_OrderNo.getText()))
		{
			//#CM719417
			// Customer Code
			txt_CustomerCode.validate();
			//#CM719418
			// Customer Name
			txt_CustomerName.validate(false);
		}
		else
		{
			//#CM719419
			// Customer Code
			txt_CustomerCode.validate(false);
			//#CM719420
			// Customer Name
			txt_CustomerName.validate(false);
		}
		
		//#CM719421
		// Order No.
		txt_OrderNo.validate(false);
		//#CM719422
		// Packed Qty per Case
		txt_CaseEntering.validate(false);
		//#CM719423
		// Host planned Case qty 
		txt_HostCasePlanQty.validate(false);
		//#CM719424
		// Case ITF
		txt_CaseItf.validate(false);
		//#CM719425
		// Packed qty per bundle
		txt_BundleEntering.validate(false);
		//#CM719426
		// Host Planned Piece Qty
		txt_HostPiesePlanQty.validate(false);
		//#CM719427
		// Bundle ITF
		txt_BundleItf.validate(false);
		//#CM719428
		// Ticket No. 
		txt_TicketNo.validate(false);
		//#CM719429
		// Line No. 
		txt_LineNo.validate(false);

		//#CM719430
		// Check the input characters for eWareNavi. 
		if (!checkContainNgText())
		{
			return;
		}
		Connection conn = null;

		try
		{
			//#CM719431
			// Set for the schedule parameter: 
			//#CM719432
			// Input area 
			RetrievalSupportParameter param = new RetrievalSupportParameter();

			//#CM719433
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM719434
			// Password 
			param.setPassword(txt_Password.getText());
			//#CM719435
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM719436
			// Consignor Name
			param.setConsignorName(txt_ConsignorName.getText());
			//#CM719437
			// Planned Picking Date
			param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
			//#CM719438
			// Item Code
			param.setItemCode(txt_ItemCode.getText());
			//#CM719439
			// Item Name
			param.setItemName(txt_ItemName.getText());
			//#CM719440
			// Case/Piece division
			param.setCasePieceflg(getCasePieceFlagFromInputArea());
			//#CM719441
			// Picking Location 
			param.setRetrievalLocation(txt_RetrievalLocation.getText());
			//#CM719442
			// Case Picking Location
			param.setCaseLocation(txt_RetrievalLocation.getText());
			//#CM719443
			// Piece Picking Location 
			param.setPieceLocation(txt_RetrievalLocation.getText());
			//#CM719444
			// Customer Code
			param.setCustomerCode(txt_CustomerCode.getText());
			//#CM719445
			// Customer Name
			param.setCustomerName(txt_CustomerName.getText());
			//#CM719446
			// Order No.
			param.setOrderNo(txt_OrderNo.getText());
			//#CM719447
			// Case Order No.
			param.setCaseOrderNo(txt_OrderNo.getText());
			//#CM719448
			// Piece Order No.
			param.setPieceOrderNo(txt_OrderNo.getText());
			//#CM719449
			// Packed Qty per Case 
			param.setEnteringQty(Formatter.getInt(txt_CaseEntering.getText()));
			//#CM719450
			// Packed qty per bundle 
			param.setBundleEnteringQty(Formatter.getInt(txt_BundleEntering.getText()));
			//#CM719451
			// Host planned Case qty  
			param.setPlanCaseQty(Formatter.getInt(txt_HostCasePlanQty.getText()));
			//#CM719452
			// Host Planned Piece Qty 
			param.setPlanPieceQty(Formatter.getInt(txt_HostPiesePlanQty.getText()));
			//#CM719453
			// Case ITF 
			param.setITF(txt_CaseItf.getText());
			//#CM719454
			// Bundle ITF 
			param.setBundleITF(txt_BundleItf.getText());
			//#CM719455
			// Ticket No. 
			param.setShippingTicketNo(txt_TicketNo.getText());
			//#CM719456
			// Line No. 
			param.setShippingLineNo(txt_LineNo.getInt());
			
			//#CM719457
			// Set the preset area info to pass it to the schedule parameter. 
			//#CM719458
			// For storing the info in the Preset area 
			RetrievalSupportParameter[] listparam = null;

			if (lst_SRetrievalPlanRegist.getMaxRows() == 1)
			{
				//#CM719459
				// Set null if no data in the preset area. 
				listparam = null;
			}
			else
			{
				//#CM719460
				// Set the value, if any data exist. 
				listparam = setListParam(this.getViewState().getInt(LINENO));
			}

			//#CM719461
			// Obtain connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalPlanRegistSCH();

			//#CM719462
			// Execute the schedule using the Connection, the Input area info, the preset area info (exclude modified lines). 
			if (schedule.check(conn, param, listparam))
			{
				//#CM719463
				// Set the data for the preset area if the result is true. 
				if (this.getViewState().getInt(LINENO) == -1)
				{
					//#CM719464
					// Add a new input to the preset. 
					//#CM719465
					// Add a line in the preset area.
					lst_SRetrievalPlanRegist.addRow();
					//#CM719466
					// Make the added line active as a target to compile.
					lst_SRetrievalPlanRegist.setCurrentRow(
						lst_SRetrievalPlanRegist.getMaxRows() - 1);
					//#CM719467
					// Set the data. 
					setListRow();
				}
				else
				{
					//#CM719468
					// If the input data is in process of modifying, update any input data in process of modification on the line to be modified.
					//#CM719469
					// Designate the line to be compiled. 
					lst_SRetrievalPlanRegist.setCurrentRow(this.getViewState().getInt(LINENO));
					//#CM719470
					// Set the data. 
					setListRow();
					//#CM719471
					// Return the selected line color to the default. 
					lst_SRetrievalPlanRegist.resetHighlight();
				}

				//#CM719472
				// Enable the buttons in the Preset area. 
				//#CM719473
				// "Add" button 
				btn_Submit.setEnabled(true);
				//#CM719474
				// "Clear List" button 
				btn_ListClear.setEnabled(true);
			}

			//#CM719475
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
				//#CM719476
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

	//#CM719477
	/** 
	 * Clicking on the Clear button invokes this. <BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 * <DIR>
	 *    1. Return the field item in the input area to the initial value. <BR>
	 *      (Leave the Worker Code/Password as it is) 
	 *    <DIR>
	 *  	- For the initial value, refer to the <CODE>inputDataClear(boolean)</CODE> method. <BR>
	 *    </DIR>
	 *    2. Set the cursor on the Worker code. <BR>
	 *    3. Maintain the contents of preset area. <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM719478
		// Initialize the Input data 
		//#CM719479
		// (Disable to initialize the Worker Code/Password) 
		inputDataClear(false);
	}

	//#CM719480
	/** 
	 * Clicking on Add/Start button invokes this. <BR>
	 * <BR>
	 * Summary: Adds the Picking Plan using the info in the Preset area.<BR>
	 * <BR>
	 * <DIR>
	 *	  1. Set the cursor on the Worker code. <BR>
	 *    2. Display the dialog box to allow to confirm to add it or not. <BR>
	 *    <DIR>
	 *      Do you register? <BR>
	 * 		 [Dialog for confirming: Cancel] <BR>
	 *			<DIR>
	 *				 Disable to do anything.
	 *			</DIR>
	 * 		 [Dialog for confirming: OK] <BR>
	 *			<DIR>
	 *				1. Start the schedule. <BR>
	 *				<DIR>
	 *   				 [Parameter] *Mandatory Input<BR>
	 * 					<DIR>
	 * 						Worker Code* <BR>
	 * 						 Password * <BR>
	 * 						Consignor Code* <BR>
	 * 						Consignor Name* <BR>
	 * 						Planned Picking Date* <BR>
	 * 						Item Code* <BR>
	 * 						Item Name* <BR>
	 * 						Case/Piece division* <BR>
	 * 						 Location No. * <BR>
	 * 						Customer Code* <BR>
	 * 						Customer Name* <BR>
	 * 						Order No.* <BR>
	 * 						Packed Qty per Case* <BR>
	 * 						Packed qty per bundle* <BR>
	 * 						 Planned Case Qty * <BR>
	 * 						 Planned Piece Qty * <BR>
	 * 						Case ITF* <BR>
	 * 						Bundle ITF* <BR>
	 * 						 Ticket No. * <BR>
	 * 						Line No* <BR>
	 * 						Terminal No.* <BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2. Clear the information in the Preset area if the schedule result is true.<BR>
	 *				3. Cancel the Modified status. (Set default: -1 for the ViewState preset line No.) <BR>
	 *    			4. Output the message obtained from the schedule to the screen. <BR>
	 *			</DIR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		//#CM719481
		// Check for mandatory input. 
		//#CM719482
		// Worker Code
		txt_WorkerCode.validate();
		//#CM719483
		// Password 
		txt_Password.validate();

		Connection conn = null;
		try
		{
			//#CM719484
			// Set the cursor on the Worker code. 
			setFocus(txt_WorkerCode);

			//#CM719485
			// for storing data to be updated 
			RetrievalSupportParameter[] param = null;
			//#CM719486
			// Set all the data in the preset area. 
			param = setListParam(-1);

			//#CM719487
			// Obtain connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalPlanRegistSCH();

			//#CM719488
			// Start the schedule. 
			if (schedule.startSCH(conn, param))
			{
				//#CM719489
				// If the result is true, commit it. 
				conn.commit();

				//#CM719490
				// Clear the list in the Preset area. 
				lst_SRetrievalPlanRegist.clearRow();

				//#CM719491
				// reset the modified status to the default. 
				this.getViewState().setInt(LINENO, -1);

				//#CM719492
				// Disable to click a button. 
				//#CM719493
				// "Start Storage" button 
				btn_Submit.setEnabled(false);
				//#CM719494
				// "Clear List" button 
				btn_ListClear.setEnabled(false);
			}
			else
			{
				//#CM719495
				// Roll-back if the result is false. 
				conn.rollback();
			}

			//#CM719496
			// Set the message obtained from the schedule. 
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
				//#CM719497
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

	//#CM719498
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
	 *				2.Disable Add button and Clear List button. <BR>
	 * 				3.Set the default (initial value: -1) for the preset line No. of ViewState. <BR>
	 *				4. Set the cursor on the Worker code. <BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		//#CM719499
		// Delete all lines in the preset area.
		lst_SRetrievalPlanRegist.clearRow();

		//#CM719500
		// Disable to click a button. 
		setBtnTrueFalse(false);

		//#CM719501
		// Set the modified status for the default (-1). 
		this.getViewState().setInt(LINENO, -1);

		//#CM719502
		// Set the cursor on the Worker Code. 
		setFocus(txt_WorkerCode);

	}

	//#CM719503
	/** 
	 * Clicking on the Cancel or Modify button invokes this. <BR>
	 * <BR>
	 * Summary of Cancel button: Clears the corresponding data in the preset area. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Display a dialog box to allow to confirm to clear the info in the preset area.<BR>
	 *    <DIR>
	 *      Do you cancel it? <BR>
	 * 		 [Dialog for confirming: Cancel] <BR>
	 *			<DIR>
	 *				 Disable to do anything.
	 *			</DIR>
	 * 		 [Dialog for confirming: OK] <BR>
	 *			<DIR>
	 *				1. Clear the corresponding data in the input area and the preset area. <BR>
	 * 				2.Set the default (initial value: -1) for the preset line No. of ViewState. <BR>
	 *              3.If no info exists in the Preset area, disable the Add button and the Clear List button. <BR>
	 *				4. Set the cursor on the Worker code. <BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * Summary of Modify button: Changes the status of the corresponding data in the preset area to Modified status. <BR>
	 * <BR>
	 * <DIR>
	 *    1. Display the selected preset info in the upper Input area. <BR>
	 *    2.Change the color of the selected line to light yellow. <BR>
	 *    3.Set the line No. of the line selected for the Preset line No. of ViewState. 
	 *    4. Set the cursor on the Worker code. <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRetrievalPlanRegist_Click(ActionEvent e) throws Exception
	{
		//#CM719504
		// Upon clicking on the "Cancel" button: 
		if (lst_SRetrievalPlanRegist.getActiveCol() == 1)
		{
			//#CM719505
			// Delete report 
			lst_SRetrievalPlanRegist.removeRow(lst_SRetrievalPlanRegist.getActiveRow());

			//#CM719506
			// If no info exists in the Preset area, disable the Add button and the Clear List button. 
			//#CM719507
			// Set null if no data in the preset area. 
			if (lst_SRetrievalPlanRegist.getMaxRows() == 1)
			{
				//#CM719508
				// Disable the buttons in the Preset area. 
				//#CM719509
				// "Add" button 
				btn_Submit.setEnabled(false);
				//#CM719510
				// "Clear List" button 
				btn_ListClear.setEnabled(false);
			}

			//#CM719511
			// Return the modified state to the defaults. (Preset line No. and Background color) 
			this.getViewState().setInt(LINENO, -1);
			lst_SRetrievalPlanRegist.resetHighlight();

		}
		//#CM719512
		// Upon clicking on "Modify" button: 
		else if (lst_SRetrievalPlanRegist.getActiveCol() == 2)
		{
			//#CM719513
			// Display the line on which the button is clicked, in the Input area. 
			//#CM719514
			// Designate the target line to modify. 
			lst_SRetrievalPlanRegist.setCurrentRow(lst_SRetrievalPlanRegist.getActiveRow());

			//#CM719515
			// Consignor Code
			txt_ConsignorCode.setText(lst_SRetrievalPlanRegist.getValue(3));
			//#CM719516
			// Planned Picking Date
			txt_RtrivlPlanDate.setDate(
				WmsFormatter.toDate(
					lst_SRetrievalPlanRegist.getValue(4),
					this.getHttpRequest().getLocale()));
			//#CM719517
			// Item Code
			txt_ItemCode.setText(lst_SRetrievalPlanRegist.getValue(5));
			//#CM719518
			// Case/Piece division 
			setCasePieceRBFromList();
			//#CM719519
			// Picking Location 
			txt_RetrievalLocation.setText(lst_SRetrievalPlanRegist.getValue(7));
			//#CM719520
			// Customer Code
			txt_CustomerCode.setText(lst_SRetrievalPlanRegist.getValue(8));
			//#CM719521
			// Order No. 
			txt_OrderNo.setText(lst_SRetrievalPlanRegist.getValue(9));
			//#CM719522
			// Packed Qty per Case
			txt_CaseEntering.setText(lst_SRetrievalPlanRegist.getValue(10));
			//#CM719523
			// Host planned Case qty 
			txt_HostCasePlanQty.setText(lst_SRetrievalPlanRegist.getValue(11));
			//#CM719524
			// Case ITF
			txt_CaseItf.setText(lst_SRetrievalPlanRegist.getValue(12));
			//#CM719525
			// Ticket No. 
			txt_TicketNo.setText(lst_SRetrievalPlanRegist.getValue(13));
			
			//#CM719526
			// Consignor Name
			txt_ConsignorName.setText(lst_SRetrievalPlanRegist.getValue(14));
			//#CM719527
			// Item Name
			txt_ItemName.setText(lst_SRetrievalPlanRegist.getValue(15));
			//#CM719528
			// Customer Name 
			txt_CustomerName.setText(lst_SRetrievalPlanRegist.getValue(16));
			//#CM719529
			// Packed qty per bundle
			txt_BundleEntering.setText(lst_SRetrievalPlanRegist.getValue(17));
			//#CM719530
			// Host Planned Piece Qty
			txt_HostPiesePlanQty.setText(lst_SRetrievalPlanRegist.getValue(18));
			//#CM719531
			// Bundle ITF
			txt_BundleItf.setText(lst_SRetrievalPlanRegist.getValue(19));
			//#CM719532
			// Line No. 
			txt_LineNo.setText(lst_SRetrievalPlanRegist.getValue(20));
			
			//#CM719533
			// Store in ViewState. 
			//#CM719534
			// Set the preset line No. in ViewState to determine the modified state. 
			this.getViewState().setInt(LINENO, lst_SRetrievalPlanRegist.getActiveRow());
			//#CM719535
			// Change the color of modified line to Light Yellow. 
			lst_SRetrievalPlanRegist.setHighlight(lst_SRetrievalPlanRegist.getActiveRow());
		}

		//#CM719536
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);

	}
	
}
//#CM719537
//end of class
