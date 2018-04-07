// $Id: DefineReportDataRetrievalBusiness.java,v 1.2 2006/11/13 08:18:44 suresh Exp $

//#CM690191
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.definereportdata;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.NumberTextBox;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.system.schedule.DefineDataParameter;
import jp.co.daifuku.wms.base.system.schedule.DefineReportDataSCH;

//#CM690192
/**
 * Designer :  S.Ishigane <BR>
 * Maker :     T.Uehata <BR>
 * <BR>
 * Allow this class to set a field for reporting data (to set the Picking Result field).<BR>
 * Set the searched value via the schedule for each field item.
 * Set the contents entered via screen for the parameter. Allow the schedule to set the Report Data field (set the Picking Result field) based on the parameter.<BR>
 * <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * Process by clicking on "Add" button (Dialog for confirming to add:OK)(btn_Submit_Click(ActionEvent e)method)<BR>
 * <BR>
 * <DIR>
 *   Display a dialog box to allow to confirm to add or not. If confirmed, add it using the information in the Preset area. <BR>
 * <BR>
 *   [Parameter]+ Require to input if the parameter is ticked to Enabled <BR>
 *   Length, Max Length, and Position of each field item<BR>
 * <BR>
 * <DIR>
 * 	 Planned Picking Date+<BR>
 * 	 Consignor Code +<BR>
 * 	 Consignor Name+<BR>
 * 	 Customer Code+<BR>
 * 	 Customer Name+<BR>
 * 	 Ticket No.+<BR>
 * 	 Ticket Line No.+<BR>
 * 	 Item Code +<BR>
 * 	 Bundle ITF+<BR>
 * 	 Case ITF+<BR>
 * 	 Packed qty per bundle+<BR>
 * 	 Packed Qty per Case+<BR>
 * 	 Item Name+<BR>
 * 	 Picking Qty (Total Bulk Qty) +<BR>
 *   Piece Picking Location+<BR>
 *   Case Picking Location+<BR>
 * 	 Piece Order No.+<BR>
 * 	 Case Order No.+<BR>
 * 	 Result Piece Qty+<BR>
 * 	 Result Case Qty+<BR>
 * 	 Picking Result Date +<BR>
 * 	 Result division+<BR>
 * 	 Expiry Date+<BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:44 $
 * @author  $Author: suresh $
 */
public class DefineReportDataRetrievalBusiness extends DefineReportDataRetrieval implements WMSConstants
{
	private static final String DO_DEFINEREPORT = "/system/definereportdata/DefineReportData.do";
	
	//#CM690193
	// Class fields --------------------------------------------------

	//#CM690194
	// Class variables -----------------------------------------------

	//#CM690195
	// Class method --------------------------------------------------

	//#CM690196
	// Constructors --------------------------------------------------

	//#CM690197
	// Public methods ------------------------------------------------
	//#CM690198
	/**
	 * Invoke this before invoking each control event.<BR>
	 * <BR>
	 * <DIR>
	 *  Summary: Displays a dialog.<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM690199
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM690200
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM690201
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM690202
			//Set the path to the help file.
			//#CM690203
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM690204
		// MSG-0009= Do you add it?
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}
	
	//#CM690205
	/**
	 * Show the Initial Display.<BR>
	 * <BR>
	 * Display a for setting the field items of the functions selected via the previous screen. <BR>
	 * <BR>
	 * <DIR>
	 *   Enabled/Disabled: Selected<BR>
	 *   Length [read only] <BR>
	 *   Position<BR> 
	 *   <BR>
	 *   <DIR>
	 *     Obtain Enabled/Disabled field item, Length, and Position from EnvironmentInformation and display them. <BR>
	 *   </DIR>
	 *   <BR>
	 *   Set the cursor to Length of a field item. <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM690206
		// Title
		lbl_SettingName.setResourceKey(this.getViewState().getString("TITLE"));

		//#CM690207
		// Check that the length is read-only. Length.
		txt_RtrivlPlanDateLen.setReadOnly(true);
		txt_CnsgnrCdLen.setReadOnly(true);
		txt_CnsgnrNmLen.setReadOnly(true);
		txt_CustCdLen.setReadOnly(true);
		txt_CustNmLen.setReadOnly(true);
		txt_TicketNoLen.setReadOnly(true);
		txt_TicketLineNoLen.setReadOnly(true);
		txt_ItemCdLen.setReadOnly(true);
		txt_BundleItfLen.setReadOnly(true);
		txt_CaseItfLen.setReadOnly(true);
		txt_BundleEtrLen.setReadOnly(true);
		txt_CaseEtrLen.setReadOnly(true);
		txt_ItemNmLen.setReadOnly(true);
		txt_RtrivlQtyPtlLen.setReadOnly(true);
		txt_PieceRtrivlLctLen.setReadOnly(true);
		txt_CaseRtrivlLctLen.setReadOnly(true);
		txt_PieceOdrNoLen.setReadOnly(true);
		txt_CaseOdrNoLen.setReadOnly(true);
		txt_RsltPieceQtyLen.setReadOnly(true);
		txt_RsltCaseQtyLen.setReadOnly(true);
		txt_RtrivlRsltDateLen.setReadOnly(true);
		txt_RsltFlgLen.setReadOnly(true);
		txt_UseByDateLen.setReadOnly(true);
		
		setInistView();
	}

	//#CM690208
	// Package methods -----------------------------------------------

	//#CM690209
	// Protected methods ---------------------------------------------

	//#CM690210
	// Private methods -----------------------------------------------

	//#CM690211
	/** 
	 * Allow this method to set the initial values for screen.<BR>
	 * <BR>
	 * Summary: Sets up the settings of each field item in the screen from property file.<BR>
	 * 
	 * @throws Exception Report all exceptions.
	 */
	private void setInistView() throws Exception
	{
		//#CM690212
		// Set the initial value of the focus to Planned Picking Date Enabled.
		setFocus(chk_RtrivlPlanDate);

		Connection conn = null;
		try
		{
			//#CM690213
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter initParam = new DefineDataParameter();
			initParam.setSelectDefineReportData(
				DefineDataParameter.SELECTDEFINEREPORTDATA_RETRIEVAL);
			WmsScheduler schedule = new DefineReportDataSCH();
			DefineDataParameter[] param = (DefineDataParameter[]) schedule.query(conn, initParam);

			if (param != null)
			{
				//#CM690214
				// Planned Picking Date: Enabled
				chk_RtrivlPlanDate.setChecked(param[0].getValid_PlanDate());
				//#CM690215
				// Planned Picking Date: Length
				txt_RtrivlPlanDateLen.setText(param[0].getFigure_PlanDate());
				//#CM690216
				// Planned Picking Date: Max Length
				lbl_MaxLenRtrivlPlanDate.setText(param[0].getMaxFigure_PlanDate());
				//#CM690217
				// Planned Picking Date: Position
				txt_RtrivlPlanDatePst.setText(param[0].getPosition_PlanDate());
				
				//#CM690218
				// Consignor Code: Enabled
				chk_CnsgnrCd.setChecked(param[0].getValid_ConsignorCode());
				//#CM690219
				// Consignor Code: Length
				txt_CnsgnrCdLen.setText(param[0].getFigure_ConsignorCode());
				//#CM690220
				// Consignor Code: Max Length
				lbl_MaxLenCnsgnrCd.setText(param[0].getMaxFigure_ConsignorCode());
				//#CM690221
				// Consignor Code: Position
				txt_CnsgnrCdPst.setText(param[0].getPosition_ConsignorCode());
				
				//#CM690222
				// Consignor Name: Enabled
				chk_CnsgnrNm.setChecked(param[0].getValid_ConsignorName());
				//#CM690223
				// Consignor Name: Length
				txt_CnsgnrNmLen.setText(param[0].getFigure_ConsignorName());
				//#CM690224
				// Consignor Name: Max Length
				lbl_MaxLenCnsgnrNm.setText(param[0].getMaxFigure_ConsignorName());
				//#CM690225
				// Consignor Name: Position
				txt_CnsgnrNmPst.setText(param[0].getPosition_ConsignorName());
				
				//#CM690226
				// Customer Code: Enabled
				chk_CustCd.setChecked(param[0].getValid_CustomerCode());
				//#CM690227
				// Customer Code: Length
				txt_CustCdLen.setText(param[0].getFigure_CustomerCode());
				//#CM690228
				// Customer Code: Max Length
				lbl_MaxLenCustCd.setText(param[0].getMaxFigure_CustomerCode());
				//#CM690229
				// Customer Code: Position
				txt_CustCdPst.setText(param[0].getPosition_CustomerCode());
				
				//#CM690230
				// Customer Name: Enabled
				chk_CustNm.setChecked(param[0].getValid_CustomerName());
				//#CM690231
				// Customer Name: Length
				txt_CustNmLen.setText(param[0].getFigure_CustomerName());
				//#CM690232
				// Customer Name: Max Length
				lbl_MaxLenCustNm.setText(param[0].getMaxFigure_CustomerName());
				//#CM690233
				// Customer Name: Position
				txt_CustNmPst.setText(param[0].getPosition_CustomerName());
				
				//#CM690234
				// Ticket No.: Enabled
				chk_TicketNo.setChecked(param[0].getValid_ShippingTicketNo());
				//#CM690235
				// Ticket No.: Length
				txt_TicketNoLen.setText(param[0].getFigure_ShippingTicketNo());
				//#CM690236
				// Ticket No.: Max Length
				lbl_MaxLenTicketNo.setText(param[0].getMaxFigure_ShippingTicketNo());
				//#CM690237
				// Ticket No.: Position
				txt_TicketNoPst.setText(param[0].getPosition_ShippingTicketNo());
				
				//#CM690238
				// Ticket Line No.: Enabled
				chk_TicketLineNo.setChecked(param[0].getValid_ShippingLineNo());
				//#CM690239
				// Ticket Line No.: Length
				txt_TicketLineNoLen.setText(param[0].getFigure_ShippingLineNo());
				//#CM690240
				// Ticket Line No.: Max Length
				lbl_MaxLenTicketLineNo.setText(param[0].getMaxFigure_ShippingLineNo());
				//#CM690241
				// Ticket Line No.: Position
				txt_TicketLineNoPst.setText(param[0].getPosition_ShippingLineNo());
				
				//#CM690242
				// Item Code: Enabled
				chk_ItemCd.setChecked(param[0].getValid_ItemCode());
				//#CM690243
				// Item Code: Length
				txt_ItemCdLen.setText(param[0].getFigure_ItemCode());
				//#CM690244
				// Item Code: Max Length
				lbl_MaxLenItemCd.setText(param[0].getMaxFigure_ItemCode());
				//#CM690245
				// Item Code: Position
				txt_ItemCdPst.setText(param[0].getPosition_ItemCode());
				
				//#CM690246
				// Bundle ITF: Enabled
				chk_BundleItf.setChecked(param[0].getValid_BundleItf());
				//#CM690247
				// Bundle ITF: Length
				txt_BundleItfLen.setText(param[0].getFigure_BundleItf());
				//#CM690248
				// Bundle ITF: Max Length
				lbl_MaxLenBundleItf.setText(param[0].getMaxFigure_BundleItf());
				//#CM690249
				// Bundle ITF: Position
				txt_BundleItfPst.setText(param[0].getPosition_BundleItf());
				
				//#CM690250
				// Case ITF: Enabled
				chk_CaseItf.setChecked(param[0].getValid_Itf());
				//#CM690251
				// Case ITF: Length
				txt_CaseItfLen.setText(param[0].getFigure_Itf());
				//#CM690252
				// Case ITF: Max Length
				lbl_MaxLenCaseItf.setText(param[0].getMaxFigure_Itf());
				//#CM690253
				// Case ITF: Position
				txt_CaseItfPst.setText(param[0].getPosition_Itf());
				
				//#CM690254
				// Packed qty per bundle: Enabled
				chk_BundleEtr.setChecked(param[0].getValid_BundleEnteringQty());
				//#CM690255
				// Packed qty per bundle: Length
				txt_BundleEtrLen.setText(param[0].getFigure_BundleEnteringQty());
				//#CM690256
				// Packed qty per bundle: Max Length
				lbl_MaxLenBundleEtr.setText(param[0].getMaxFigure_BundleEnteringQty());
				//#CM690257
				// Packed qty per bundle: Position
				txt_BundleEtrPst.setText(param[0].getPosition_BundleEnteringQty());
				
				//#CM690258
				// Packed Qty per Case: Enabled
				chk_CaseEtr.setChecked(param[0].getValid_EnteringQty());
				//#CM690259
				// Packed Qty per Case: Length
				txt_CaseEtrLen.setText(param[0].getFigure_EnteringQty());
				//#CM690260
				// Packed Qty per Case: Max Length
				lbl_MaxLenCaseEtr.setText(param[0].getMaxFigure_EnteringQty());
				//#CM690261
				// Packed Qty per Case: Position
				txt_CaseEtrPst.setText(param[0].getPosition_EnteringQty());
				
				//#CM690262
				// Item Name: Enabled
				chk_ItemNm.setChecked(param[0].getValid_ItemName());
				//#CM690263
				// Item Name: Length
				txt_ItemNmLen.setText(param[0].getFigure_ItemName());
				//#CM690264
				// Item Name: Max Length
				lbl_MaxLenItemNm.setText(param[0].getMaxFigure_ItemName());
				//#CM690265
				// Item Name: Position
				txt_ItemNmPst.setText(param[0].getPosition_ItemName());
				
				//#CM690266
				// Picking Qty (Total Bulk Qty): Enabled
				chk_RtrivlQtyPtl.setChecked(param[0].getValid_PlanQty());
				//#CM690267
				// Picking Qty (Total Bulk Qty): Length
				txt_RtrivlQtyPtlLen.setText(param[0].getFigure_PlanQty());
				//#CM690268
				// Picking Qty (Total Bulk Qty): Max Length
				lbl_MaxLenRtrivlQtyPtl.setText(param[0].getMaxFigure_PlanQty());
				//#CM690269
				// Picking Qty (Total Bulk Qty): Position
				txt_RtrivlQtyPtlPst.setText(param[0].getPosition_PlanQty());
				
				//#CM690270
				// Piece Picking Location: Enabled
				chk_PieceRtrivlLct.setChecked(param[0].getValid_PieceLocation());
				//#CM690271
				// Piece Picking Location: Length
				txt_PieceRtrivlLctLen.setText(param[0].getFigure_PieceLocation());
				//#CM690272
				// Piece Picking Location: Max Length
				lbl_MaxLenPieceRtrivlLct.setText(param[0].getMaxFigure_PieceLocation());
				//#CM690273
				// Piece Picking Location: Position
				txt_PieceRtrivlLctPst.setText(param[0].getPosition_PieceLocation());
				
				//#CM690274
				// Case Picking Location: Enabled
				chk_CaseRtrivlLct.setChecked(param[0].getValid_CaseLocation());
				//#CM690275
				// Case Picking Location: Length
				txt_CaseRtrivlLctLen.setText(param[0].getFigure_CaseLocation());
				//#CM690276
				// Case Picking Location: Max Length
				lbl_MaxLenCaseRtrivlLct.setText(param[0].getMaxFigure_CaseLocation());
				//#CM690277
				// Case Picking Location: Position
				txt_CaseRtrivlLctPst.setText(param[0].getPosition_CaseLocation());
				
				//#CM690278
				// Piece Order No.: Enabled
				chk_PieceOdrNo.setChecked(param[0].getValid_PieceOrderNo());
				//#CM690279
				// Piece Order No.: Length
				txt_PieceOdrNoLen.setText(param[0].getFigure_PieceOrderNo());
				//#CM690280
				// Piece Order No.: Max Length
				lbl_MaxLenPieceOdrNo.setText(param[0].getMaxFigure_PieceOrderNo());
				//#CM690281
				// Piece Order No.: Position
				txt_PieceOdrNoPst.setText(param[0].getPosition_PieceOrderNo());
				
				//#CM690282
				// Case Order No.: Enabled
				chk_CaseOdrNo.setChecked(param[0].getValid_CaseOrderNo());
				//#CM690283
				// Case Order No.: Length
				txt_CaseOdrNoLen.setText(param[0].getFigure_CaseOrderNo());
				//#CM690284
				// Case Order No.: Max Length
				lbl_MaxLenCaseOdrNo.setText(param[0].getMaxFigure_CaseOrderNo());
				//#CM690285
				// Case Order No.: Position
				txt_CaseOdrNoPst.setText(param[0].getPosition_CaseOrderNo());
				
				//#CM690286
				// Result Piece Qty: Enabled
				chk_RsltPieceQty.setChecked(param[0].getValid_PieceResultQty());
				//#CM690287
				// Result Piece Qty: Length
				txt_RsltPieceQtyLen.setText(param[0].getFigure_PieceResultQty());
				//#CM690288
				// Result Piece Qty: Max Length
				lbl_MaxLenRsltPieceQty.setText(param[0].getMaxFigure_PieceResultQty());
				//#CM690289
				// Result Piece Qty: Position
				txt_RsltPieceQtyPst.setText(param[0].getPosition_PieceResultQty());
				
				//#CM690290
				// Result Case Qty: Enabled
				chk_RsltCaseQty.setChecked(param[0].getValid_CaseResultQty());
				//#CM690291
				// Result Case Qty: Length
				txt_RsltCaseQtyLen.setText(param[0].getFigure_CaseResultQty());
				//#CM690292
				// Result Case Qty: Max Length
				lbl_MaxLenRsltCaseQty.setText(param[0].getMaxFigure_CaseResultQty());
				//#CM690293
				// Result Case Qty: Position
				txt_RsltCaseQtyPst.setText(param[0].getPosition_CaseResultQty());
				
				//#CM690294
				// Picking Result Date: Enabled
				chk_RtrivlRsltDate.setChecked(param[0].getValid_WorkDate());
				//#CM690295
				// Picking Result Date: Length
				txt_RtrivlRsltDateLen.setText(param[0].getFigure_WorkDate());
				//#CM690296
				// Picking Result Date: Max Length
				lbl_MaxLenRtrivlRsltDate.setText(param[0].getMaxFigure_WorkDate());
				//#CM690297
				// Picking Result Date: Position
				txt_RtrivlRsltDatePst.setText(param[0].getPosition_WorkDate());
				
				//#CM690298
				// Result division: Enabled
				chk_RsltFlg.setChecked(param[0].getValid_ResultFlag());
				//#CM690299
				// Result division: Length
				txt_RsltFlgLen.setText(param[0].getFigure_ResultFlag());
				//#CM690300
				// Result division: Max Length
				lbl_MaxLenRsltFlg.setText(param[0].getMaxFigure_ResultFlag());
				//#CM690301
				// Result division: Position
				txt_RsltFlgPst.setText(param[0].getPosition_ResultFlag());
				
				//#CM690302
				// Expiry Date: Enabled
				chk_UseByDate.setChecked(param[0].getValid_UseByDate());
				//#CM690303
				// Expiry Date: Length
				txt_UseByDateLen.setText(param[0].getFigure_UseByDate());
				//#CM690304
				// Expiry Date: Max Length
				lbl_MaxLenUseByDate.setText(param[0].getMaxFigure_UseByDate());
				//#CM690305
				// Expiry Date: Position
				txt_UseByDatePst.setText(param[0].getPosition_UseByDate());
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
				//#CM690306
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

	//#CM690307
	/** 
	 * Allow this method to check wheter the position is duplicated or not. <BR>
	 * <BR>
	 * Summary: Checks for duplicate position. <BR>
	 * 
	 * @param position Position to check for double occupation.
	 * @return Return false if two or more data occupies a single position.
	 * @throws Exception Report all exceptions.
	 */
	private boolean positionCheck(NumberTextBox position) throws Exception
	{
		int checkCount = 0;

		//#CM690308
		// Planned Picking Date: Position
		if (position.getText().equals(txt_RtrivlPlanDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690309
		// Consignor Code: Position
		if (position.getText().equals(txt_CnsgnrCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690310
		// Consignor Name: Position
		if (position.getText().equals(txt_CnsgnrNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690311
		// Customer Code: Position
		if (position.getText().equals(txt_CustCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690312
		// Customer Name: Position
		if (position.getText().equals(txt_CustNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690313
		// Ticket No.: Position
		if (position.getText().equals(txt_TicketNoPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690314
		// Ticket Line No.: Position
		if (position.getText().equals(txt_TicketLineNoPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690315
		// Item Code: Position
		if (position.getText().equals(txt_ItemCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690316
		// Bundle ITF: Position
		if (position.getText().equals(txt_BundleItfPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690317
		// Case ITF: Position
		if (position.getText().equals(txt_CaseItfPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690318
		// Packed qty per bundle: Position
		if (position.getText().equals(txt_BundleEtrPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690319
		// Packed Qty per Case: Position
		if (position.getText().equals(txt_CaseEtrPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690320
		// Item Name: Position
		if (position.getText().equals(txt_ItemNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690321
		// Picking Qty (Total Bulk Qty): Position
		if (position.getText().equals(txt_RtrivlQtyPtlPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690322
		// Piece Picking Location: Position
		if (position.getText().equals(txt_PieceRtrivlLctPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690323
		// Case Picking Location: Position
		if (position.getText().equals(txt_CaseRtrivlLctPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690324
		// Piece Order No.: Position
		if (position.getText().equals(txt_PieceOdrNoPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690325
		// Case Order No.: Position
		if (position.getText().equals(txt_CaseOdrNoPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690326
		// Result Piece Qty: Position
		if (position.getText().equals(txt_RsltPieceQtyPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690327
		// Result Case Qty: Position
		if (position.getText().equals(txt_RsltCaseQtyPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690328
		// Picking Result Date: Position
		if (position.getText().equals(txt_RtrivlRsltDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690329
		// Result division: Position
		if (position.getText().equals(txt_RsltFlgPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690330
		// Expiry Date: Position
		if (position.getText().equals(txt_UseByDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}

		return true;
	}

	//#CM690331
	/** 
	 * Allow this method to check wheter the position is duplicated or not in a sequential order.<BR>
	 * <BR>
	 * @return Return false if two or more data occupies a single position.
	 * @throws Exception Report all exceptions.
	 */
	private boolean checkRepeat() throws Exception
	{

		boolean repeatChkFlg = true;
		
		if (repeatChkFlg && chk_RtrivlPlanDate.getChecked())
		{
			repeatChkFlg = positionCheck(txt_RtrivlPlanDatePst);
		}


		if (repeatChkFlg && chk_CnsgnrCd.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CnsgnrCdPst);
		}

		if (repeatChkFlg && chk_CnsgnrNm.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CnsgnrNmPst);
		}

		if (repeatChkFlg && chk_CustCd.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CustCdPst);
		}

		if (repeatChkFlg && chk_CustNm.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CustNmPst);
		}


		if (repeatChkFlg && chk_TicketNo.getChecked())
		{
			repeatChkFlg = positionCheck(txt_TicketNoPst);
		}

		if (repeatChkFlg && chk_TicketLineNo.getChecked())
		{
			repeatChkFlg = positionCheck(txt_TicketLineNoPst);
		}

		if (repeatChkFlg && chk_ItemCd.getChecked())
		{
			repeatChkFlg = positionCheck(txt_ItemCdPst);
		}

		if (repeatChkFlg && chk_BundleItf.getChecked())
		{
			repeatChkFlg = positionCheck(txt_BundleItfPst);
		}

		if (repeatChkFlg && chk_CaseItf.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CaseItfPst);
		}

		if (repeatChkFlg && chk_BundleEtr.getChecked())
		{
			repeatChkFlg = positionCheck(txt_BundleEtrPst);
		}

		if (repeatChkFlg && chk_ItemNm.getChecked())
		{
			repeatChkFlg = positionCheck(txt_ItemNmPst);
		}

		if (repeatChkFlg && chk_RtrivlQtyPtl.getChecked())
		{
			repeatChkFlg = positionCheck(txt_RtrivlQtyPtlPst);
		}

		if (repeatChkFlg && chk_PieceRtrivlLct.getChecked())
		{
			repeatChkFlg = positionCheck(txt_PieceRtrivlLctPst);
		}

		if (repeatChkFlg && chk_CaseRtrivlLct.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CaseRtrivlLctPst);
		}

		if (repeatChkFlg && chk_PieceOdrNo.getChecked())
		{
			repeatChkFlg = positionCheck(txt_PieceOdrNoPst);
		}

		if (repeatChkFlg && chk_CaseOdrNo.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CaseOdrNoPst);
		}
		
		if (repeatChkFlg && chk_RsltPieceQty.getChecked())
		{
			repeatChkFlg = positionCheck(txt_RsltPieceQtyPst);
		}

		if (repeatChkFlg && chk_RsltCaseQty.getChecked())
		{
			repeatChkFlg = positionCheck(txt_RsltCaseQtyPst);
		}

		if (repeatChkFlg && chk_RtrivlRsltDate.getChecked())
		{
			repeatChkFlg = positionCheck(txt_RtrivlRsltDatePst);
		}

		if (repeatChkFlg && chk_RsltFlg.getChecked())
		{
			repeatChkFlg = positionCheck(txt_RsltFlgPst);
		}

		if (repeatChkFlg && chk_UseByDate.getChecked())
		{
			repeatChkFlg = positionCheck(txt_UseByDatePst);
		}
		
		return repeatChkFlg;
	}

	//#CM690332
	/** 
	 * Allow this method to check whether a checkbox is ticked off or not.<BR>
	 * <BR>
	 * @return Return false if no item ticked off.
	 * @throws Exception Report all exceptions.
	 */
	private boolean checkCheckBox() throws Exception
	{
		if (!chk_RtrivlPlanDate.getChecked()
			&& !chk_CnsgnrCd.getChecked()
			&& !chk_CnsgnrNm.getChecked()
			&& !chk_CustCd.getChecked()
			&& !chk_CustNm.getChecked()
			&& !chk_TicketNo.getChecked()
			&& !chk_TicketLineNo.getChecked()
			&& !chk_ItemCd.getChecked()
			&& !chk_BundleItf.getChecked()
			&& !chk_CaseItf.getChecked()
			&& !chk_BundleEtr.getChecked()
			&& !chk_CaseEtr.getChecked()
			&& !chk_ItemNm.getChecked()
			&& !chk_RtrivlQtyPtl.getChecked()
			&& !chk_PieceRtrivlLct.getChecked()
			&& !chk_CaseRtrivlLct.getChecked()
			&& !chk_PieceOdrNo.getChecked()
			&& !chk_CaseOdrNo.getChecked()
			&& !chk_RsltPieceQty.getChecked()
			&& !chk_RsltCaseQty.getChecked()
			&& !chk_RtrivlRsltDate.getChecked()
			&& !chk_RsltFlg.getChecked()
			&& !chk_UseByDate.getChecked())
		{
			return false;
		}
		return true;
	}
	//#CM690333
	// Event handler methods -----------------------------------------
	//#CM690334
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690335
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690336
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Back_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690337
	/** 
	 * Clicking on Return button invokes this.<BR>
	 * <BR>
	 * Summary: Return to the previous screen. <BR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		//#CM690338
		// Shift to the original screen.
		forward(DO_DEFINEREPORT);
	}

	//#CM690339
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690340
	/** 
	 * Clicking on Menu button invokes this.<BR>
	 * <BR>
	 * Summary: Allow this method to execute the following processes.<BR>
	 * <BR>
	 * <DIR>
	 *   Shift to the Menu screen.<BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM690341
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Valid1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690342
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DigitsUseLength1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690343
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLength1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690344
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Position1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690345
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Valid2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690346
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DigitsUseLength2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690347
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLength2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690348
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Position2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690349
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690350
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690351
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RtrivlPlanDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690352
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690353
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690354
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690355
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690356
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690357
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690358
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690359
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690360
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690361
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690362
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690363
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690364
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690365
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690366
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690367
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690368
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690369
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690370
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690371
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690372
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690373
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690374
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690375
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690376
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690377
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690378
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690379
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RtrivQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690380
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RtrivlQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690381
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RtrivlQtyPtl_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690382
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlQtyPtlLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690383
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlQtyPtlLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690384
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlQtyPtlLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690385
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRtrivlQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690386
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlQtyPtlPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690387
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlQtyPtlPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690388
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlQtyPtlPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690389
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690390
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690391
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690392
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690393
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690394
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690395
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690396
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690397
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690398
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690399
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PiceRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690400
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PieceRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690401
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PieceRtrivlLct_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690402
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLctLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690403
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLctLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690404
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLctLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690405
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenPieceRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690406
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLctPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690407
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLctPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690408
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLctPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690409
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690410
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CustCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690411
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CustCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690412
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690413
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690414
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690415
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCustCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690416
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690417
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690418
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690419
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690420
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690421
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseRtrivlLct_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690422
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLctLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690423
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLctLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690424
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLctLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690425
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690426
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLctPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690427
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLctPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690428
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLctPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690429
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690430
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CustNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690431
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CustNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690432
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690433
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690434
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690435
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCustNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690436
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690437
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690438
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690439
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PieceOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690440
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PieceOdrNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690441
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PieceOdrNo_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690442
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOdrNoLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690443
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOdrNoLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690444
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOdrNoLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690445
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenPieceOdrNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690446
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOdrNoPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690447
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOdrNoPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690448
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOdrNoPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690449
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_TicketNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690450
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_TicketNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690451
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_TicketNo_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690452
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690453
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690454
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690455
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690456
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690457
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690458
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNoPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690459
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690460
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseOdrNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690461
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseOdrNo_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690462
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOdrNoLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690463
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOdrNoLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690464
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOdrNoLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690465
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseOdrNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690466
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOdrNoPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690467
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOdrNoPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690468
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOdrNoPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690469
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_TicketLineNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690470
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_TicketLineNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690471
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_TicketLineNo_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690472
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketLineNoLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690473
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketLineNoLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690474
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketLineNoLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690475
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenTicketLineNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690476
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketLineNoPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690477
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketLineNoPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690478
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketLineNoPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690479
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ResultPieceQtyTwoByte_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690480
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690481
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltPieceQty_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690482
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690483
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690484
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690485
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRsltPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690486
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690487
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690488
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690489
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690490
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690491
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690492
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690493
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690494
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690495
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690496
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690497
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690498
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690499
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ResultCaseQtyTwoByte_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690500
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690501
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltCaseQty_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690502
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690503
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690504
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690505
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRsltCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690506
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690507
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690508
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690509
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690510
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690511
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleItf_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690512
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690513
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690514
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690515
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenBundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690516
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690517
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690518
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690519
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RtrivlResultDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690520
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RtrivlRsltDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690521
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RtrivlRsltDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690522
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690523
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690524
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690525
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRtrivlRsltDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690526
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690527
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690528
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690529
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690530
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690531
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseItf_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690532
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690533
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690534
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690535
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690536
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690537
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690538
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690539
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ResultFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690540
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltFlg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690541
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltFlg_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690542
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690543
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690544
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690545
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRsltFlg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690546
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690547
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690548
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690549
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690550
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690551
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleEtr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690552
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690553
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690554
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690555
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenBundleEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690556
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690557
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690558
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690559
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690560
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690561
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_UseByDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690562
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690563
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690564
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690565
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenUseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690566
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690567
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690568
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690569
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690570
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690571
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseEtr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690572
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690573
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690574
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690575
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690576
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690577
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690578
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690579
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}


	//#CM690580
	/** 
	 * Clicking on Add button for field items set for reporting data (setting of Picking Result field) invokes this.<BR>
	 * <BR>
	 * Summary: Displays a dialog box to allow to confirm to add it or not. <BR>
	 * <BR>
	 * 1.Check the input area. <BR>
	 *   <DIR>
	 *     -If no check is placed in each checkbox, display a message: "Select the target to be added". <BR>
	 *     -If one or more field items are ticked off: Shift to 2.<BR>
	 *   </DIR>
	 * Display the dialog box for confirming to add. ("Do you add this?") <BR>
	 * <BR>
	 *   <DIR>
	 *     [Dialog for confirming to start: Cancel] <BR>
	 *     <DIR>
	 *       Disable to do anything. <BR>
	 * 	   </DIR>
	 *     [Dialog for confirming to start: OK]
	 *     <DIR>
	 *       Add it using the information in the Preset area. <BR>
	 *       <DIR>
	 *         2.Check the input area. <BR>
	 *         <DIR>
	 *           -If no check is placed in each checkbox, display a message: "Select the target to be added". <BR>
	 *         </DIR>
	 *         3.Check for input in the input item (count) in the input area. (Mandatory Input, number of characters, and attribution of characters) <BR>
	 *         <DIR>
	 *           -Ensure the length is not larger than the max length. <BR>
	 *           -Ensure that the Length is larger than 0. <BR>  
	 *           -Ensure that the Position is larger than 0. <BR>     
	 *           Note) Duplicated position values are acceptable (in such case, combine two or more data fields and report them together). <BR>
	 *         </DIR>
	 *         4.Start the schedule.<BR>
	 *         5.Set the cursor to Length of a field item. <BR>
	 *       </DIR>            
	 *     </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		//#CM690581
		// Set the initial value of the focus to Planned Picking Date Enabled.
		setFocus(chk_RtrivlPlanDate);
		
		//#CM690582
		// Check the checkbox.
		if (!checkCheckBox())
		{
			//#CM690583
			// 6023174=Please select 1 or more items.
			message.setMsgResourceKey("6023174");
			return;
		}
		
		//#CM690584
		// Check for input.
		WmsCheckker.checkDefine(lbl_RetrievalPlanDate, chk_RtrivlPlanDate, txt_RtrivlPlanDateLen, lbl_MaxLenRtrivlPlanDate, txt_RtrivlPlanDatePst);
		WmsCheckker.checkDefine(lbl_ConsignorCode, chk_CnsgnrCd, txt_CnsgnrCdLen, lbl_MaxLenCnsgnrCd, txt_CnsgnrCdPst);
		WmsCheckker.checkDefine(lbl_ConsignorName, chk_CnsgnrNm, txt_CnsgnrNmLen, lbl_MaxLenCnsgnrNm, txt_CnsgnrNmPst);
		WmsCheckker.checkDefine(lbl_CustomerCode, chk_CustCd, txt_CustCdLen, lbl_MaxLenCustCd, txt_CustCdPst);
		WmsCheckker.checkDefine(lbl_CustomerName, chk_CustNm, txt_CustNmLen, lbl_MaxLenCustNm, txt_CustNmPst);
		WmsCheckker.checkDefine(lbl_TicketNo, chk_TicketNo, txt_TicketNoLen, lbl_MaxLenTicketNo, txt_TicketNoPst);
		WmsCheckker.checkDefine(lbl_TicketLineNo, chk_TicketLineNo, txt_TicketLineNoLen, lbl_MaxLenTicketLineNo, txt_TicketLineNoPst);
		WmsCheckker.checkDefine(lbl_ItemCode, chk_ItemCd, txt_ItemCdLen, lbl_MaxLenItemCd, txt_ItemCdPst);
		WmsCheckker.checkDefine(lbl_BundleItf, chk_BundleItf, txt_BundleItfLen, lbl_MaxLenBundleItf, txt_BundleItfPst);
		WmsCheckker.checkDefine(lbl_CaseItf, chk_CaseItf, txt_CaseItfLen, lbl_MaxLenCaseItf, txt_CaseItfPst);
		WmsCheckker.checkDefine(lbl_BundleEntering, chk_BundleEtr, txt_BundleEtrLen, lbl_MaxLenBundleEtr, txt_BundleEtrPst);
		WmsCheckker.checkDefine(lbl_CaseEntering, chk_CaseEtr, txt_CaseEtrLen, lbl_MaxLenCaseEtr, txt_CaseEtrPst);
		WmsCheckker.checkDefine(lbl_ItemName, chk_ItemNm, txt_ItemNmLen, lbl_MaxLenItemNm, txt_ItemNmPst);
		WmsCheckker.checkDefine(lbl_RtrivQtyPtl, chk_RtrivlQtyPtl, txt_RtrivlQtyPtlLen, lbl_MaxLenRtrivlQtyPtl, txt_RtrivlQtyPtlPst);
		WmsCheckker.checkDefine(lbl_PiceRtrivlLct, chk_PieceRtrivlLct, txt_PieceRtrivlLctLen, lbl_MaxLenPieceRtrivlLct, txt_PieceRtrivlLctPst);
		WmsCheckker.checkDefine(lbl_CaseRtrivlLct, chk_CaseRtrivlLct, txt_CaseRtrivlLctLen, lbl_MaxLenCaseRtrivlLct, txt_CaseRtrivlLctPst);
		WmsCheckker.checkDefine(lbl_PieceOrderNo, chk_PieceOdrNo, txt_PieceOdrNoLen, lbl_MaxLenPieceOdrNo, txt_PieceOdrNoPst);
		WmsCheckker.checkDefine(lbl_CaseOrderNo, chk_CaseOdrNo, txt_CaseOdrNoLen, lbl_MaxLenCaseOdrNo, txt_CaseOdrNoPst);
		WmsCheckker.checkDefine(lbl_ResultPieceQtyTwoByte, chk_RsltPieceQty, txt_RsltPieceQtyLen, lbl_MaxLenRsltPieceQty, txt_RsltPieceQtyPst);
		WmsCheckker.checkDefine(lbl_ResultCaseQtyTwoByte, chk_RsltCaseQty, txt_RsltCaseQtyLen, lbl_MaxLenRsltCaseQty, txt_RsltCaseQtyPst);
		WmsCheckker.checkDefine(lbl_RtrivlResultDate, chk_RtrivlRsltDate, txt_RtrivlRsltDateLen, lbl_MaxLenRtrivlRsltDate, txt_RtrivlRsltDatePst);
		WmsCheckker.checkDefine(lbl_ResultFlag, chk_RsltFlg, txt_RsltFlgLen, lbl_MaxLenRsltFlg, txt_RsltFlgPst);
		WmsCheckker.checkDefine(lbl_UseByDate, chk_UseByDate, txt_UseByDateLen, lbl_MaxLenUseByDate, txt_UseByDatePst);
	
		Connection conn = null;
		
		try
		{
			//#CM690585
			// Check for duplicate Position.
			if (!checkRepeat())
			{
				//#CM690586
				// Set the Error Message.
				//#CM690587
				// 6023098=No duplicate of sequential order is allowed.
				message.setMsgResourceKey("6023098");
				return;
			}
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	
			DefineDataParameter[] param = new DefineDataParameter[1];
			param[0] = new DefineDataParameter();
	
			WmsScheduler schedule = new DefineReportDataSCH();
	
			//#CM690588
			// Set the value for the parameter.
			param[0].setSelectDefineReportData(DefineDataParameter.SELECTDEFINEREPORTDATA_RETRIEVAL);
			//#CM690589
			// Planned Picking Date: Enabled
			param[0].setValid_PlanDate(chk_RtrivlPlanDate.getChecked());
			//#CM690590
			// Planned Picking Date: Length
			param[0].setFigure_PlanDate(txt_RtrivlPlanDateLen.getText());
			//#CM690591
			// Planned Picking Date: Max Length
			param[0].setMaxFigure_PlanDate(lbl_MaxLenRtrivlPlanDate.getText());
			//#CM690592
			// Planned Picking Date: Position
			param[0].setPosition_PlanDate(txt_RtrivlPlanDatePst.getText());
			
			//#CM690593
			// Consignor Code: Enabled
			param[0].setValid_ConsignorCode(chk_CnsgnrCd.getChecked());
			//#CM690594
			// Consignor Code: Length
			param[0].setFigure_ConsignorCode(txt_CnsgnrCdLen.getText());
			//#CM690595
			// Consignor Code: Max Length
			param[0].setMaxFigure_ConsignorCode(lbl_MaxLenCnsgnrCd.getText());
			//#CM690596
			// Consignor Code: Position
			param[0].setPosition_ConsignorCode(txt_CnsgnrCdPst.getText());
			
			//#CM690597
			// Consignor Name: Enabled
			param[0].setValid_ConsignorName(chk_CnsgnrNm.getChecked());
			//#CM690598
			// Consignor Name: Length
			param[0].setFigure_ConsignorName(txt_CnsgnrNmLen.getText());
			//#CM690599
			// Consignor Name: Max Length
			param[0].setMaxFigure_ConsignorName(lbl_MaxLenCnsgnrNm.getText());
			//#CM690600
			// Consignor Name: Position
			param[0].setPosition_ConsignorName(txt_CnsgnrNmPst.getText());

			//#CM690601
			// Customer Code: Enabled
			param[0].setValid_CustomerCode(chk_CustCd.getChecked());
			//#CM690602
			// Customer Code: Length
			param[0].setFigure_CustomerCode(txt_CustCdLen.getText());
			//#CM690603
			// Customer Code: Max Length
			param[0].setMaxFigure_CustomerCode(lbl_MaxLenCustCd.getText());
			//#CM690604
			// Customer Code: Position
			param[0].setPosition_CustomerCode(txt_CustCdPst.getText());
			
			//#CM690605
			// Customer Name: Enabled
			param[0].setValid_CustomerName(chk_CustNm.getChecked());
			//#CM690606
			// Customer Name: Length
			param[0].setFigure_CustomerName(txt_CustNmLen.getText());
			//#CM690607
			// Customer Name: Max Length
			param[0].setMaxFigure_CustomerName(lbl_MaxLenCustNm.getText());
			//#CM690608
			// Customer Name: Position
			param[0].setPosition_CustomerName(txt_CustNmPst.getText());

			//#CM690609
			// Ticket No.: Enabled
			param[0].setValid_ShippingTicketNo(chk_TicketNo.getChecked());
			//#CM690610
			// Ticket No.: Length
			param[0].setFigure_ShippingTicketNo(txt_TicketNoLen.getText());
			//#CM690611
			// Ticket No.: Max Length
			param[0].setMaxFigure_ShippingTicketNo(lbl_MaxLenTicketNo.getText());
			//#CM690612
			// Ticket No.: Position
			param[0].setPosition_ShippingTicketNo(txt_TicketNoPst.getText());
			
			//#CM690613
			// Ticket Line No.: Enabled
			param[0].setValid_ShippingLineNo(chk_TicketLineNo.getChecked());
			//#CM690614
			// Ticket Line No.: Length
			param[0].setFigure_ShippingLineNo(txt_TicketLineNoLen.getText());
			//#CM690615
			// Ticket Line No.: Max Length
			param[0].setMaxFigure_ShippingLineNo(lbl_MaxLenTicketLineNo.getText());
			//#CM690616
			// Ticket Line No.: Position
			param[0].setPosition_ShippingLineNo(txt_TicketLineNoPst.getText());
			
			//#CM690617
			// Item Code: Enabled
			param[0].setValid_ItemCode(chk_ItemCd.getChecked());
			//#CM690618
			// Item Code: Length
			param[0].setFigure_ItemCode(txt_ItemCdLen.getText());
			//#CM690619
			// Item Code: Max Length
			param[0].setMaxFigure_ItemCode(lbl_MaxLenItemCd.getText());
			//#CM690620
			// Item Code: Position
			param[0].setPosition_ItemCode(txt_ItemCdPst.getText());
			
			//#CM690621
			// Bundle ITF: Enabled
			param[0].setValid_BundleItf(chk_BundleItf.getChecked());
			//#CM690622
			// Bundle ITF: Length
			param[0].setFigure_BundleItf(txt_BundleItfLen.getText());
			//#CM690623
			// Bundle ITF: Max Length
			param[0].setMaxFigure_BundleItf(lbl_MaxLenBundleItf.getText());
			//#CM690624
			// Bundle ITF: Position
			param[0].setPosition_BundleItf(txt_BundleItfPst.getText());
			
			//#CM690625
			// Case ITF: Enabled
			param[0].setValid_Itf(chk_CaseItf.getChecked());
			//#CM690626
			// Case ITF: Length
			param[0].setFigure_Itf(txt_CaseItfLen.getText());
			//#CM690627
			// Case ITF: Max Length
			param[0].setMaxFigure_Itf(lbl_MaxLenCaseItf.getText());
			//#CM690628
			// Case ITF: Position
			param[0].setPosition_Itf(txt_CaseItfPst.getText());
			
			//#CM690629
			// Packed qty per bundle: Enabled
			param[0].setValid_BundleEnteringQty(chk_BundleEtr.getChecked());
			//#CM690630
			// Packed qty per bundle: Length
			param[0].setFigure_BundleEnteringQty(txt_BundleEtrLen.getText());
			//#CM690631
			// Packed qty per bundle: Max Length
			param[0].setMaxFigure_BundleEnteringQty(lbl_MaxLenBundleEtr.getText());
			//#CM690632
			// Packed qty per bundle: Position
			param[0].setPosition_BundleEnteringQty(txt_BundleEtrPst.getText());
			
			//#CM690633
			// Packed Qty per Case: Enabled
			param[0].setValid_EnteringQty(chk_CaseEtr.getChecked());
			//#CM690634
			// Packed Qty per Case: Length
			param[0].setFigure_EnteringQty(txt_CaseEtrLen.getText());
			//#CM690635
			// Packed Qty per Case: Max Length
			param[0].setMaxFigure_EnteringQty(lbl_MaxLenCaseEtr.getText());
			//#CM690636
			// Packed Qty per Case: Position
			param[0].setPosition_EnteringQty(txt_CaseEtrPst.getText());
			
			//#CM690637
			// Item Name: Enabled
			param[0].setValid_ItemName(chk_ItemNm.getChecked());
			//#CM690638
			// Item Name: Length
			param[0].setFigure_ItemName(txt_ItemNmLen.getText());
			//#CM690639
			// Item Name: Max Length
			param[0].setMaxFigure_ItemName(lbl_MaxLenItemNm.getText());
			//#CM690640
			// Item Name: Position
			param[0].setPosition_ItemName(txt_ItemNmPst.getText());
						
			//#CM690641
			// Picking Qty (Total Bulk Qty): Enabled
			param[0].setValid_PlanQty(chk_RtrivlQtyPtl.getChecked());
			//#CM690642
			// Picking Qty (Total Bulk Qty): Length
			param[0].setFigure_PlanQty(txt_RtrivlQtyPtlLen.getText());
			//#CM690643
			// Picking Qty (Total Bulk Qty): Max Length
			param[0].setMaxFigure_PlanQty(lbl_MaxLenRtrivlQtyPtl.getText());
			//#CM690644
			// Picking Qty (Total Bulk Qty): Position
			param[0].setPosition_PlanQty(txt_RtrivlQtyPtlPst.getText());

			//#CM690645
			// Piece Picking Location: Enabled
			param[0].setValid_PieceLocation(chk_PieceRtrivlLct.getChecked());
			//#CM690646
			// Piece Picking Location: Length
			param[0].setFigure_PieceLocation(txt_PieceRtrivlLctLen.getText());
			//#CM690647
			// Piece Picking Location: Max Length
			param[0].setMaxFigure_PieceLocation(lbl_MaxLenPieceRtrivlLct.getText());
			//#CM690648
			// Piece Picking Location: Position
			param[0].setPosition_PieceLocation(txt_PieceRtrivlLctPst.getText());
			
			//#CM690649
			// Case Picking Location: Enabled
			param[0].setValid_CaseLocation(chk_CaseRtrivlLct.getChecked());
			//#CM690650
			// Case Picking Location: Length
			param[0].setFigure_CaseLocation(txt_CaseRtrivlLctLen.getText());
			//#CM690651
			// Case Picking Location: Max Length
			param[0].setMaxFigure_CaseLocation(lbl_MaxLenCaseRtrivlLct.getText());
			//#CM690652
			// Case Picking Location: Position
			param[0].setPosition_CaseLocation(txt_CaseRtrivlLctPst.getText());

			//#CM690653
			// Piece Order No.: Enabled
			param[0].setValid_PieceOrderNo(chk_PieceOdrNo.getChecked());
			//#CM690654
			// Piece Order No.: Length
			param[0].setFigure_PieceOrderNo(txt_PieceOdrNoLen.getText());
			//#CM690655
			// Piece Order No.: Max Length
			param[0].setMaxFigure_PieceOrderNo(lbl_MaxLenPieceOdrNo.getText());
			//#CM690656
			// Piece Order No.: Position
			param[0].setPosition_PieceOrderNo(txt_PieceOdrNoPst.getText());
			
			//#CM690657
			// Case Order No.: Enabled
			param[0].setValid_CaseOrderNo(chk_CaseOdrNo.getChecked());
			//#CM690658
			// Case Order No.: Length
			param[0].setFigure_CaseOrderNo(txt_CaseOdrNoLen.getText());
			//#CM690659
			// Case Order No.: Max Length
			param[0].setMaxFigure_CaseOrderNo(lbl_MaxLenCaseOdrNo.getText());
			//#CM690660
			// Case Order No.: Position
			param[0].setPosition_CaseOrderNo(txt_CaseOdrNoPst.getText());
			
			//#CM690661
			// Result Piece Qty: Enabled
			param[0].setValid_PieceResultQty(chk_RsltPieceQty.getChecked());
			//#CM690662
			// Result Piece Qty: Length
			param[0].setFigure_PieceResultQty(txt_RsltPieceQtyLen.getText());
			//#CM690663
			// Result Piece Qty: Max Length
			param[0].setMaxFigure_PieceResultQty(lbl_MaxLenRsltPieceQty.getText());
			//#CM690664
			// Result Piece Qty: Position
			param[0].setPosition_PieceResultQty(txt_RsltPieceQtyPst.getText());
			
			//#CM690665
			// Result Case Qty: Enabled
			param[0].setValid_CaseResultQty(chk_RsltCaseQty.getChecked());
			//#CM690666
			// Result Case Qty: Length
			param[0].setFigure_CaseResultQty(txt_RsltCaseQtyLen.getText());
			//#CM690667
			// Result Case Qty: Max Length
			param[0].setMaxFigure_CaseResultQty(lbl_MaxLenRsltCaseQty.getText());
			//#CM690668
			// Result Case Qty: Position
			param[0].setPosition_CaseResultQty(txt_RsltCaseQtyPst.getText());
			
			//#CM690669
			// Picking Result Date: Enabled
			param[0].setValid_WorkDate(chk_RtrivlRsltDate.getChecked());
			//#CM690670
			// Picking Result Date: Length
			param[0].setFigure_WorkDate(txt_RtrivlRsltDateLen.getText());
			//#CM690671
			// Picking Result Date: Max Length
			param[0].setMaxFigure_WorkDate(lbl_MaxLenRtrivlRsltDate.getText());
			//#CM690672
			// Picking Result Date: Position
			param[0].setPosition_WorkDate(txt_RtrivlRsltDatePst.getText());
			
			//#CM690673
			// Result division: Enabled
			param[0].setValid_ResultFlag(chk_RsltFlg.getChecked());
			//#CM690674
			// Result division: Length
			param[0].setFigure_ResultFlag(txt_RsltFlgLen.getText());
			//#CM690675
			// Result division: Max Length
			param[0].setMaxFigure_ResultFlag(lbl_MaxLenRsltFlg.getText());
			//#CM690676
			// Result division: Position
			param[0].setPosition_ResultFlag(txt_RsltFlgPst.getText());
			
			//#CM690677
			// Expiry Date: Enabled
			param[0].setValid_UseByDate(chk_UseByDate.getChecked());
			//#CM690678
			// Expiry Date: Length
			param[0].setFigure_UseByDate(txt_UseByDateLen.getText());
			//#CM690679
			// Expiry Date: Max Length
			param[0].setMaxFigure_UseByDate(lbl_MaxLenUseByDate.getText());
			//#CM690680
			// Expiry Date: Position
			param[0].setPosition_UseByDate(txt_UseByDatePst.getText());

			//#CM690681
			// Start the schedule.
			if (schedule.startSCH(conn, param))
			{
				conn.commit();
			}
			else
			{
				conn.rollback();
			}
	
			if (schedule.getMessage() != null)
			{
				//#CM690682
				// Display a message.
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
				//#CM690683
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

	//#CM690684
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690685
	/** 
	 * Clicking on Clear button for field items set for reporting data (setting of Picking Result field) invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 * <DIR>
	 *   1.Reset the Input area to its initial display (the status as obtained from EnvironmentInformation). <BR>
	 *   2.Set the cursor to Length of a field item. <BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		setInistView();
	}


}
//#CM690686
//end of class
