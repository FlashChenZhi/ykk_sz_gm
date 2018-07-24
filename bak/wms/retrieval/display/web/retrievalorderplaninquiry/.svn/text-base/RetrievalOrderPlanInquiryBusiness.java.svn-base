// $Id: RetrievalOrderPlanInquiryBusiness.java,v 1.2 2007/02/07 04:19:23 suresh Exp $

//#CM716206
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalorderplaninquiry;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalcustomer.ListRetrievalCustomerBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderPlanInquirySCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM716207
/**
 * Designer : K.Mukai <BR>
 * Maker : H.Murakado<BR>
 * <BR>
 * Allow this class of screen to inquire the Order Picking Plan. <BR>
 * Set the contents entered via screen for the parameter, and allow the schedule to search data to be displayed based on the parameter. <BR>
 * Receive data to be output to the preset area from the schedule and output it to the preset area. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process by clicking "Display" button (<CODE>btn_View_Click()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Set the contents entered via screen for the parameter, and allow the schedule to search data to be displayed based on the parameter. <BR>
 *   If no corresponding data is found, receive the <CODE>Parameter</CODE> array with number of elements equal to 0. Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 * <BR>
 *   [Parameter]  *Mandatory Input<BR>
 * <BR>
 *     <DIR>
 *     Consignor Code* <BR>
 *     Start Planned Picking Date <BR>
 *     End Planned Picking Date <BR>
 *     Customer Code <BR>
 *     Case Order No. <BR>
 *     Piece Order No. <BR>
 *     Work status: <BR>
 *     </DIR>
 * <BR>
 *   [Returned data]  <BR>
 * <BR>
 *    <DIR>
 *    Consignor Code <BR>
 *    Consignor Name <BR>
 *    Planned Picking Date <BR>
 *    Case Order No. <BR>
 *    Piece Order No. <BR>
 *    Customer Code <BR>
 *    Customer Name <BR>
 *    Item Code <BR>
 *    Item Name <BR>
 *    Division  <BR>
 *    Packed Qty per Case <BR>
 *    Packed qty per bundle <BR>
 *    Host planned Case qty  <BR>
 *    Host Planned Piece Qty <BR>
 *    Result Case Qty  <BR>
 *    Result Piece Qty  <BR>
 *    Case Picking Location <BR>
 *    Piece Picking Location  <BR>
 *    Case ITF <BR>
 *    Bundle ITF <BR>
 *    status  <BR>
 *    </DIR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/25</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:23 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderPlanInquiryBusiness extends RetrievalOrderPlanInquiry implements WMSConstants
{
	//#CM716208
	// Class fields --------------------------------------------------
	//#CM716209
	// Row No. of listcell 
	//#CM716210
	// Planned Picking Date
	private static final int LSTPLANDATE = 1;
	//#CM716211
	// Customer Code
	private static final int LSTCUSCODE = 2;
	//#CM716212
	// Case Order No.
	private static final int LSTCASEORDER = 3;
	//#CM716213
	// Item Code
	private static final int LSTITEMCODE = 4;
	//#CM716214
	// Division 
	private static final int LSTCASEPIECE = 5;
	//#CM716215
	// Packed Qty per Case
	private static final int LSTENTQTY = 6;
	//#CM716216
	// Host planned Case qty 
	private static final int LSTPLANCASEQTY = 7;
	//#CM716217
	// Result Case Qty 
	private static final int LSTCASEQTY = 8;
	//#CM716218
	// Case Picking Location
	private static final int LSTCASELOCATION = 9;
	//#CM716219
	// Case ITF
	private static final int LSTCASEITF = 10;
	//#CM716220
	// status 
	private static final int LSTSTATUS = 11;
	//#CM716221
	// Customer Name
	private static final int LSTCUSNAME = 12;
	//#CM716222
	// Piece Order No.
	private static final int LSTPIECEORDER = 13;
	//#CM716223
	// Item Name
	private static final int LSTITEMNAME = 14;
	//#CM716224
	// Packed qty per bundle
	private static final int LSTBUNDLEENTQTY = 15;
	//#CM716225
	// Host Planned Piece Qty
	private static final int LSTPLANPIECEQTY = 16;
	//#CM716226
	// Result Piece Qty 
	private static final int LSTPIECEQTY = 17;
	//#CM716227
	// Piece Picking Location 
	private static final int LSTPIECELOCATION = 18;
	//#CM716228
	// Bundle ITF
	private static final int LSTBUNDLEITF = 19;

	//#CM716229
	// Class variables -----------------------------------------------

	//#CM716230
	// Class method --------------------------------------------------

	//#CM716231
	// Constructors --------------------------------------------------

	//#CM716232
	// Public methods ------------------------------------------------

	//#CM716233
	/**
	 * show the Initial Display. <BR>
	 * <BR>
	 * <DIR>
	 *  Field item: name [Initial Value] 
	 *  <DIR>
	 *  Consignor Code                [If there is only one Consignor code that corresponds to the condition, show the Initial Display.]  <BR>
	 *  Start Picking Date                [None]  <BR>
	 *  End Picking Date                [None]  <BR>
	 *  Customer Code			  [None]  <BR>
	 *  Case Order No.         [None]  <BR>
	 *  Piece Order No.         [None]  <BR>
	 *  Work status:                  [All]  <BR>
	 *  </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM716234
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM716235
		// Start Planned Picking Date
		txt_StrtRtrivlPlanDate.setText("");
		//#CM716236
		// End Planned Picking Date
		txt_EdRtrivlPlanDate.setText("");
		//#CM716237
		// Customer Code
		txt_CustomerCode.setText("");
		//#CM716238
		// Case Order No.
		txt_CaseOrderNo.setText("");
		//#CM716239
		// Piece Order No.
		txt_PieceOrderNo.setText("");
		//#CM716240
		// Work status:
		pul_WkStsRtrivlPlan.setSelectedIndex(0);

		//#CM716241
		// Consignor Code (Preset) 
		txt_RConsignorCode.setText("");
		//#CM716242
		// Consignor Name (Preset) 
		txt_RConsignorName.setText("");
		//#CM716243
		// Listcell 
		lst_RetrievalOrderPlanInquir.clearRow();
	}

	//#CM716244
	/**
	 * Invoke this before invoking each control event.  <BR>
	 * <BR>
	 * Summary: This method executes the following processes.<BR>
	 * <BR>
	 * <DIR>
	 *       1. Set the cursor on the Consignor code.  <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM716245
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM716246
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM716247
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}

		//#CM716248
		// Set the cursor on the Consignor code. 		
		setFocus(txt_ConsignorCode);
	}

	//#CM716249
	/**
	 * Returning from a popup window invokes this method.
	 * Override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE>. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM716250
		// Obtain the parameter selected in the listbox. 
		//#CM716251
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM716252
		// Start Planned Picking Date
		Date startplandate = WmsFormatter.toDate(
			param.getParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY),
				this.getHttpRequest().getLocale());
		//#CM716253
		// End Planned Picking Date
		Date endplandate = WmsFormatter.toDate(
			param.getParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY),
				this.getHttpRequest().getLocale());
		//#CM716254
		// Customer Code
		String customercode = param.getParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY);
		//#CM716255
		// Case Order No.
		String caseorder = param.getParameter(ListRetrievalOrdernoBusiness.CASEORDERNO_KEY);
		//#CM716256
		// Piece Order No.
		String pieceorder = param.getParameter(ListRetrievalOrdernoBusiness.PIECEORDERNO_KEY);

		//#CM716257
		// Set a value if not empty. 
		//#CM716258
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM716259
		// Start Planned Picking Date
		if (!StringUtil.isBlank(startplandate))
		{
			txt_StrtRtrivlPlanDate.setDate(startplandate);
		}
		//#CM716260
		// End Planned Picking Date
		if (!StringUtil.isBlank(endplandate))
		{
			txt_EdRtrivlPlanDate.setDate(endplandate);
		}
		//#CM716261
		// Customer Code
		if (!StringUtil.isBlank(customercode))
		{
			txt_CustomerCode.setText(customercode);
		}
		//#CM716262
		// Case Order No.
		if (!StringUtil.isBlank(caseorder))
		{
			txt_CaseOrderNo.setText(caseorder);
		}
		//#CM716263
		// Piece Order No.
		if (!StringUtil.isBlank(pieceorder))
		{
			txt_PieceOrderNo.setText(pieceorder);
		}
	}

	//#CM716264
	// Package methods -----------------------------------------------

	//#CM716265
	// Protected methods ---------------------------------------------

	//#CM716266
	// Private methods -----------------------------------------------
	//#CM716267
	/** 
	 * Allow this method to obtain the Consignor code from the schedule. <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule. <BR>
	 * Allow the schedule to return the corresponding Consignor if the data has only one Consignor Code. 
	 * if there are two or more data of Consignor or the count of data is 0, return null. <BR>
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

			//#CM716268
			// Obtain the Consignor code from the schedule. 
			WmsScheduler schedule = new RetrievalOrderPlanInquirySCH();
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
				//#CM716269
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

	//#CM716270
	// Event handler methods -----------------------------------------
	//#CM716271
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716272
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716273
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716274
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

	//#CM716275
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716276
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716277
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716278
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716279
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716280
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716281
	/** 
	 * Clicking on the Search Consignor Code button invokes this. 
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for Consignor using the search condition. <BR>
	 * <BR>
	 *   [Parameter]  *Mandatory Input<BR>
	 * <BR>
	 *     <DIR>
	 *     Consignor Code <BR>
	 *     </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM716282
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();

		//#CM716283
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM716284
		// Screen information 
		param.setParameter(
			ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);

		//#CM716285
		// Flag to distinguish Order/Item 
		param.setParameter(
			ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG,
			RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		
		//#CM716286
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do",
			param,
			"/progress.do");
	}

	//#CM716287
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrtRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716288
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716289
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtRtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716290
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtRtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716291
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrtRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716292
	/** 
	 * Clicking on the Search Start Planned Picking Date button invokes this. 
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Planned Picking Date using this search condition. <BR>
	 * <BR>
	 *   [Parameter]  *Mandatory Input<BR>
	 * <BR>
	 *     <DIR>
	 *     Consignor Code <BR>
	 *     Start Planned Picking Date <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchStrtRtrivlPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM716293
		// Set the search condition in the Search screen of Start Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();

		//#CM716294
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM716295
		// Start Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));

		//#CM716296
		// Set the Start flag. 
		param.setParameter(
			ListRetrievalPlanDateBusiness.RANGERETRIEVALPLANDATE_KEY,
			RetrievalSupportParameter.RANGE_START);

		//#CM716297
		// "Search" flag 
		param.setParameter(
			ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);

		//#CM716298
		// Flag to distinguish Order/Item 
		param.setParameter(
			ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG,
			RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		
		//#CM716299
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do",
			param,
			"/progress.do");
	}

	//#CM716300
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716301
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EdRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716302
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716303
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdRtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716304
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdRtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716305
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEdRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716306
	/** 
	 * Clicking on the Search End Planned Picking Date button invokes this. 
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Planned Picking Date using this search condition. <BR>
	 * <BR>
	 *   [Parameter]  *Mandatory Input<BR>
	 * <BR>
	 *     <DIR>
	 *     Consignor Code <BR>
	 *     End Planned Picking Date <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchEdRtrivlPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM716307
		// Set the search condition in the Search screen of End Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();

		//#CM716308
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM716309
		// End Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));

		//#CM716310
		// Set the Close flag. 
		param.setParameter(
			ListRetrievalPlanDateBusiness.RANGERETRIEVALPLANDATE_KEY,
			RetrievalSupportParameter.RANGE_END);

		//#CM716311
		// "Search" flag 
		param.setParameter(
			ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);

		//#CM716312
		// Flag to distinguish Order/Item 
		param.setParameter(
			ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG,
			RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		
		//#CM716313
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do",
			param,
			"/progress.do");
	}

	//#CM716314
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseOrder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716315
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716316
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716317
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716318
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716319
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCaseOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716320
	/** 
	 * Clicking on the Search Case Order No. button invokes this. 
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Case Order No. using this search condition. <BR>
	 * <BR>
	 *   [Parameter]  *Mandatory Input<BR>
	 * <BR>
	 *     <DIR>
	 *     Consignor Code <BR>
	 *     Start Planned Picking Date <BR>
	 *     End Planned Picking Date <BR>
	 *     Customer Code <BR>
	 *     Case Order No. <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchCaseOrderNo_Click(ActionEvent e) throws Exception
	{
		//#CM716321
		// Set the search condition in the Search screen of Case Order No. 
		ForwardParameters param = new ForwardParameters();

		//#CM716322
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM716323
		// Start Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));

		//#CM716324
		// End Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));

		//#CM716325
		// Customer Code
		param.setParameter(
			ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY,
			txt_CustomerCode.getText());

		//#CM716326
		// Case Order No.
		param.setParameter(ListRetrievalOrdernoBusiness.CASEORDERNO_KEY, txt_CaseOrderNo.getText());

		//#CM716327
		// Case flag 
		param.setParameter(
			ListRetrievalOrdernoBusiness.CASE_PIECE_KEY,
			RetrievalSupportParameter.LISTBOX_CASE);

		//#CM716328
		// "Search" flag 
		param.setParameter(
			ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);

		//#CM716329
		// Flag to distinguish Order/Item 
		param.setParameter(
			ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG,
			RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		
		//#CM716330
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do",
			param,
			"/progress.do");
	}

	//#CM716331
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PieceOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716332
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716333
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716334
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716335
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716336
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchPieceOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716337
	/** 
	 * Clicking on the Search Piece Order No. button invokes this. 
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Order No. using this search condition. <BR>
	 * <BR>
	 *   [Parameter]  *Mandatory Input<BR>
	 * <BR>
	 *     <DIR>
	 *     Consignor Code <BR>
	 *     Start Planned Picking Date <BR>
	 *     End Planned Picking Date <BR>
	 *     Customer Code <BR>
	 *     Piece Order No. <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchPieceOrderNo_Click(ActionEvent e) throws Exception
	{
		//#CM716338
		// Set the search condition in the Search screen of Piece Order No. 
		ForwardParameters param = new ForwardParameters();

		//#CM716339
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM716340
		// Start Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));

		//#CM716341
		// End Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));

		//#CM716342
		// Customer Code
		param.setParameter(
			ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY,
			txt_CustomerCode.getText());

		//#CM716343
		// Piece Order No.
		param.setParameter(
			ListRetrievalOrdernoBusiness.PIECEORDERNO_KEY,
			txt_PieceOrderNo.getText());

		//#CM716344
		// Piece flag 
		param.setParameter(
			ListRetrievalOrdernoBusiness.CASE_PIECE_KEY,
			RetrievalSupportParameter.LISTBOX_PIECE);

		//#CM716345
		// "Search" flag 
		param.setParameter(
			ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);

		//#CM716346
		// Flag to distinguish Order/Item 
		param.setParameter(
			ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG,
			RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		
		//#CM716347
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do",
			param,
			"/progress.do");
	}

	//#CM716348
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716349
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WkStsRtrivlPlan_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716350
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WkStsRtrivlPlan_Change(ActionEvent e) throws Exception
	{
	}

	//#CM716351
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716352
	/** 
	 * Clicking on the Display button invokes this. <BR>
	 * <BR>
	 * Summary: Displays using field items input in the Input area as conditions and display data in the Preset area.<BR>
	 * <BR>
	 * <DIR>
	 *     Execute the following processes.  <BR>
	 *     1. Check for input in the input field item in the input area.  <BR>
	 *       <DIR>
	 *      (Mandatory input, number of characters, and attribute of character)  <BR>
	 *       (Start Planned Picking Date <= End Planned Picking Date)  <BR>
	 *       </DIR>
	 *     2. Start the schedule.  <BR>
	 *       <DIR>
	 *       [Parameter]  *Mandatory Input<BR>
	 *       <BR>
	 *         <DIR>
	 *         Consignor Code* <BR>
	 *         Start Planned Picking Date <BR>
	 *         End Planned Picking Date <BR>
	 * 		   Customer Code <BR>
	 *         Case Order No. <BR>
	 *         Piece Order No. <BR>
	 * 		   Work status:* <BR>
	 *         </DIR>
	 *       </DIR>
	 *     3. Display the schedule result in the preset area.  <BR>
	 *     4. Set the cursor on the Consignor code.  <BR>
	 * </DIR>
	 * <BR>
	 * <BR>
	 * Row No. list of listcell  <BR>
	 * <DIR>
	 *     1:Planned Picking Date <BR>
	 * 	   2:Customer Code <BR>
	 *     3:Case Order No. <BR>
	 *     4:Item Code <BR>
	 *     5: Division  <BR>
	 *     6:Packed Qty per Case <BR>
	 *     7: Host planned Case qty  <BR>
	 * 	   8: Result Case Qty  <BR>
	 *     9:Case Picking Location <BR>
	 * 	  10:Case ITF <BR>
	 *    11: status  <BR>
	 *    12:Customer Name <BR>
	 *    13:Piece Order No. <BR>
	 *    14:Item Name <BR>
	 *    15:Packed qty per bundle <BR>
	 *    16:Host Planned Piece Qty <BR>
	 *    17: Result Piece Qty  <BR>
	 *    18: Piece Picking Location  <BR>
	 *    19:Bundle ITF <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{

		Connection conn = null;

		try
		{
			//#CM716353
			// Move the cursor to the Consignor code. 
			setFocus(txt_ConsignorCode);

			//#CM716354
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM716355
			// Clear the Preset area. 
			lst_RetrievalOrderPlanInquir.clearRow();
			txt_RConsignorCode.setText("");
			txt_RConsignorName.setText("");

			//#CM716356
			// Check for input. (format, mandatory, prohibited characters) 
			//#CM716357
			// Consignor Code
			txt_ConsignorCode.validate();
			//#CM716358
			// Start Planned Picking Date
			txt_StrtRtrivlPlanDate.validate(false);
			//#CM716359
			// End Planned Picking Date
			txt_EdRtrivlPlanDate.validate(false);
			//#CM716360
			// Customer Code
			txt_CustomerCode.validate(false);
			//#CM716361
			// Case Order No.
			txt_CaseOrderNo.validate(false);
			//#CM716362
			// Piece Order No.
			txt_PieceOrderNo.validate(false);
			//#CM716363
			// Work status:
			pul_WkStsRtrivlPlan.validate(false);
			//#CM716364
			// Check the value of Planned Picking Date for precedence. 
			if (txt_StrtRtrivlPlanDate.getDate() != null && txt_EdRtrivlPlanDate.getDate() != null)
			{

				if (txt_StrtRtrivlPlanDate.getDate().after(txt_EdRtrivlPlanDate.getDate()))
				{
					//#CM716365
					// Display the error message and close it. 
					//#CM716366
					// 6023108=Starting planned picking date must precede the end date. 
					message.setMsgResourceKey("6023108");
					return;
				}
			}

			//#CM716367
			// Set the schedule parameter. 
			RetrievalSupportParameter param = new RetrievalSupportParameter();

			//#CM716368
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM716369
			// Start Planned Picking Date
			param.setFromRetrievalPlanDate(
				WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));
			//#CM716370
			// End Planned Picking Date
			param.setToRetrievalPlanDate(WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));
			//#CM716371
			// Customer Code
			param.setCustomerCode(txt_CustomerCode.getText());
			//#CM716372
			// Case Order No.
			param.setCaseOrderNo(txt_CaseOrderNo.getText());
			//#CM716373
			// Piece Order No.
			param.setPieceOrderNo(txt_PieceOrderNo.getText());

			//#CM716374
			// Work status:
			//#CM716375
			// All 
			if (pul_WkStsRtrivlPlan.getSelectedIndex() == 0)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_ALL);
			}
			//#CM716376
			// Standby 
			else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 1)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
			}
			//#CM716377
			// "Started" 
			else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 2)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_STARTED);
			}
			//#CM716378
			// Working 
			else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 3)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_WORKING);
			}
			//#CM716379
			// Partially Completed 
			else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 4)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
			}
			//#CM716380
			// Completed 
			else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 5)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
			}

			//#CM716381
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalOrderPlanInquirySCH();
			RetrievalSupportParameter[] viewParam =
				(RetrievalSupportParameter[]) schedule.query(conn, param);

			//#CM716382
			// Close the process when some error occurred or no display data was found. 
			if (viewParam == null || viewParam.length == 0)
			{
				//#CM716383
				// Close the connection. 
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM716384
			// Display the data if obtained the display data when the schedule normally completed. 
			//#CM716385
			// Consignor Code (Preset) 
			txt_RConsignorCode.setText(viewParam[0].getConsignorCode());
			//#CM716386
			// Consignor Name (Preset) 
			txt_RConsignorName.setText(viewParam[0].getConsignorName());

			//#CM716387
			// Customer Name
			String label_customername = DisplayText.getText("LBL-W0036");
			//#CM716388
			// Item Name
			String label_itemname = DisplayText.getText("LBL-W0103");
			//#CM716389
			// Host planned Case qty 
			String label_plancaseqty = DisplayText.getText("LBL-W0385");
			//#CM716390
			// Host Planned Piece Qty
			String label_planpieceqty = DisplayText.getText("LBL-W0386");
			//#CM716391
			// Result Case Qty 
			String label_resultcaseqty = DisplayText.getText("LBL-W0418");
			//#CM716392
			// Result Piece Qty 
			String label_resultpieceqty = DisplayText.getText("LBL-W0417");
			//#CM716393
			// Case Picking Location
			String label_caselocation = DisplayText.getText("LBL-W0017");
			//#CM716394
			// Piece Picking Location 
			String label_piecelocation = DisplayText.getText("LBL-W0152");
			//#CM716395
			// Case ITF
			String label_caseitf = DisplayText.getText("LBL-W0338");
			//#CM716396
			// Bundle ITF
			String label_bundleitf = DisplayText.getText("LBL-W0337");
			//#CM716397
			// status 
			String label_status = DisplayText.getText("LBL-W0229");

			//#CM716398
			// Set a value in the listcell. 
			for (int i = 0; i < viewParam.length; i++)
			{

				//#CM716399
				// Add a line. 
				lst_RetrievalOrderPlanInquir.addRow();
				lst_RetrievalOrderPlanInquir.setCurrentRow(i + 1);

				int j = 1;

				//#CM716400
				// Set the search result in each cell. 
				//#CM716401
				// (1st row) 
				//#CM716402
				// Planned Picking Date
				lst_RetrievalOrderPlanInquir.setValue(
					j++,
					WmsFormatter.toDispDate(
						viewParam[i].getRetrievalPlanDate(),
						this.getHttpRequest().getLocale()));

				//#CM716403
				// Customer Code
				lst_RetrievalOrderPlanInquir.setValue(LSTCUSCODE, viewParam[i].getCustomerCode());
				//#CM716404
				// Case Order No.
				lst_RetrievalOrderPlanInquir.setValue(LSTCASEORDER, viewParam[i].getCaseOrderNo());
				//#CM716405
				// Item Code
				lst_RetrievalOrderPlanInquir.setValue(LSTITEMCODE, viewParam[i].getItemCode());
				//#CM716406
				// Division 
				lst_RetrievalOrderPlanInquir.setValue(LSTCASEPIECE, viewParam[i].getCasePieceflgName());
				//#CM716407
				// Packed Qty per Case
				lst_RetrievalOrderPlanInquir.setValue(
					LSTENTQTY,
					Formatter.getNumFormat(viewParam[i].getEnteringQty()));
				//#CM716408
				// Host planned Case qty 
				lst_RetrievalOrderPlanInquir.setValue(
					LSTPLANCASEQTY,
					Formatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				//#CM716409
				// Result Case Qty 
				lst_RetrievalOrderPlanInquir.setValue(
					LSTCASEQTY,
					Formatter.getNumFormat(viewParam[i].getResultCaseQty()));
				//#CM716410
				// Case Picking Location
				lst_RetrievalOrderPlanInquir.setValue(
					LSTCASELOCATION,
					viewParam[i].getCaseLocation());
				//#CM716411
				// Case ITF
				lst_RetrievalOrderPlanInquir.setValue(LSTCASEITF, viewParam[i].getITF());
				//#CM716412
				// status 
				lst_RetrievalOrderPlanInquir.setValue(LSTSTATUS, viewParam[i].getWorkStatusName());

				//#CM716413
				// (2nd row) 
				//#CM716414
				// Customer Name
				lst_RetrievalOrderPlanInquir.setValue(LSTCUSNAME, viewParam[i].getCustomerName());
				//#CM716415
				// Piece Order No.
				lst_RetrievalOrderPlanInquir.setValue(
					LSTPIECEORDER,
					viewParam[i].getPieceOrderNo());
				//#CM716416
				// Item Name
				lst_RetrievalOrderPlanInquir.setValue(LSTITEMNAME, viewParam[i].getItemName());
				//#CM716417
				// Packed qty per bundle
				lst_RetrievalOrderPlanInquir.setValue(
					LSTBUNDLEENTQTY,
					Formatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
				//#CM716418
				// Host Planned Piece Qty
				lst_RetrievalOrderPlanInquir.setValue(
					LSTPLANPIECEQTY,
					Formatter.getNumFormat(viewParam[i].getPlanPieceQty()));
				//#CM716419
				// Result Piece Qty 
				lst_RetrievalOrderPlanInquir.setValue(
					LSTPIECEQTY,
					Formatter.getNumFormat(viewParam[i].getResultPieceQty()));
				//#CM716420
				// Piece Picking Location 
				lst_RetrievalOrderPlanInquir.setValue(
					LSTPIECELOCATION,
					viewParam[i].getPieceLocation());
				//#CM716421
				// Bundle ITF
				lst_RetrievalOrderPlanInquir.setValue(LSTBUNDLEITF, viewParam[i].getBundleITF());

				//#CM716422
				// Set the tool tip (balloon field item). 
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM716423
				// Customer Name
				toolTip.add(label_customername, viewParam[i].getCustomerName());
				//#CM716424
				// Item Name
				toolTip.add(label_itemname, viewParam[i].getItemName());
				//#CM716425
				// Host planned Case qty 
				toolTip.add(label_plancaseqty, Formatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				//#CM716426
				// Host Planned Piece Qty
				toolTip.add(label_planpieceqty, Formatter.getNumFormat(viewParam[i].getPlanPieceQty()));
				//#CM716427
				// Result Case Qty 
				toolTip.add(label_resultcaseqty, Formatter.getNumFormat(viewParam[i].getResultCaseQty()));
				//#CM716428
				// Result Piece Qty 
				toolTip.add(label_resultpieceqty, Formatter.getNumFormat(viewParam[i].getResultPieceQty()));
				//#CM716429
				// Case Picking Location
				toolTip.add(label_caselocation, viewParam[i].getCaseLocation());
				//#CM716430
				// Piece Picking Location 
				toolTip.add(label_piecelocation, viewParam[i].getPieceLocation());
				//#CM716431
				// Case ITF
				toolTip.add(label_caseitf,viewParam[i].getITF());
				//#CM716432
				// Bundle ITF
				toolTip.add(label_bundleitf,viewParam[i].getBundleITF());
				//#CM716433
				// status 
				toolTip.add(label_status,viewParam[i].getWorkStatusName());
				
				lst_RetrievalOrderPlanInquir.setToolTip(i + 1, toolTip.getText());
			}

			//#CM716434
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
				//#CM716435
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

	//#CM716436
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716437
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
		//#CM716438
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM716439
		// Start Planned Picking Date
		txt_StrtRtrivlPlanDate.setText("");
		//#CM716440
		// End Planned Picking Date
		txt_EdRtrivlPlanDate.setText("");
		//#CM716441
		// Customer Code
		txt_CustomerCode.setText("");
		//#CM716442
		// Case Order No.
		txt_CaseOrderNo.setText("");
		//#CM716443
		// Piece Order No.
		txt_PieceOrderNo.setText("");
		//#CM716444
		// Work status:
		pul_WkStsRtrivlPlan.setSelectedIndex(0);
	}

	//#CM716445
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716446
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716447
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716448
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716449
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716450
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716451
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716452
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716453
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716454
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderPlanInquir_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716455
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderPlanInquir_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716456
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderPlanInquir_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716457
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderPlanInquir_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM716458
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderPlanInquir_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716459
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderPlanInquir_Change(ActionEvent e) throws Exception
	{
	}

	//#CM716460
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderPlanInquir_Click(ActionEvent e) throws Exception
	{
	}

	//#CM716461
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716462
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716463
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716464
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716465
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716466
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716467
	/** 
	 * Clicking on Search Customer Code button invokes this. 
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Customer using this search condition. <BR>
	 * <BR>
	 *   [Parameter]  *Mandatory Input<BR>
	 * <BR>
	 *     <DIR>
	 *     Consignor Code <BR>
	 *     Start Planned Picking Date <BR>
	 *     End Planned Picking Date <BR>
	 *     Customer Code <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchCustomerCode_Click(ActionEvent e) throws Exception
	{
		//#CM716468
		// Set the search condition in the Search screen of Customer. 
		ForwardParameters param = new ForwardParameters();

		//#CM716469
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM716470
		// Start Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));

		//#CM716471
		// End Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));

		//#CM716472
		// Customer Code
		param.setParameter(
			ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY,
			txt_CustomerCode.getText());

		//#CM716473
		// Flag to distinguish Order/Item 
		param.setParameter(
			ListRetrievalCustomerBusiness.ORDER_ITEM_FLAG,
			RetrievalSupportParameter.ITEMORDERFLAG_ORDER);

		//#CM716474
		// "Search" flag (Plan) 
		param.setParameter(
			ListRetrievalCustomerBusiness.SEARCHCUSTOMER_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);

		//#CM716475
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalcustomer/ListRetrievalCustomer.do",
			param,
			"/progress.do");
	}

}
//#CM716476
//end of class
