// $Id: DefineReportDataSortingBusiness.java,v 1.2 2006/11/13 08:18:41 suresh Exp $

//#CM691126
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

//#CM691127
/**
 * Designer :  S.Ishigane <BR>
 * Maker :     D.Hakui <BR>
 * <BR>
 * Allow this class to set a field for reporting data (to set the Sorting Result field).<BR>
 * Set the searched value via the schedule for each field item.
 * Set the contents entered via screen for the parameter. Allow the schedule to set the Report Data field (set the Sorting Result field) based on the parameter.<BR>
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
 * 	 Planned Sorting Date+<BR>
 * 	 Consignor Code +<BR>
 * 	 Consignor Name+<BR>
 * 	 Customer Code+<BR>
 * 	 Customer Name+<BR>
 * 	 Shipping Ticket No+<BR>
 * 	 Shipping ticket line No.+<BR>
 * 	 Item Code +<BR>
 * 	 Bundle ITF+<BR>
 * 	 Case ITF+<BR>
 * 	 Packed qty per bundle+<BR>
 * 	 Packed Qty per Case+<BR>
 * 	 Item Name+<BR>
 * 	 Sorting Qty (Total Bulk Qty) +<BR>
 * 	 Piece Sorting Place+<BR>
 * 	 Case Sorting Place+<BR>
 * 	 Cross/DC division+<BR>
 *   Supplier Code+<BR>
 * 	 Supplier Name+<BR>
 * 	 Receiving Ticket No.+<BR>
 * 	 Receiving Ticket Line No.+<BR>
 * 	 Result Piece Qty+<BR>
 * 	 Result Case Qty+<BR>
 * 	 Sorting Result Date +<BR>
 * 	 Result division+<BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:41 $
 * @author  $Author: suresh $
 */
public class DefineReportDataSortingBusiness extends DefineReportDataSorting implements WMSConstants
{
	private static final String DO_DEFINEREPORT = "/system/definereportdata/DefineReportData.do";

	//#CM691128
	// Class fields --------------------------------------------------

	//#CM691129
	// Class variables -----------------------------------------------

	//#CM691130
	// Class method --------------------------------------------------

	//#CM691131
	// Constructors --------------------------------------------------

	//#CM691132
	// Public methods ------------------------------------------------

	//#CM691133
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
			//#CM691134
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM691135
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM691136
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM691137
			//Set the path to the help file.
			//#CM691138
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM691139
		// MSG-0009= Do you add it?
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}

	//#CM691140
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
		//#CM691141
		// Title
		lbl_SettingName.setResourceKey(this.getViewState().getString("TITLE"));

		//#CM691142
		// Check that the length is read-only. Length.
		txt_PickPlanDateLen.setReadOnly(true);
		txt_CnsgnrCdLen.setReadOnly(true);
		txt_CnsgnrNmLen.setReadOnly(true);
		txt_CustCdLen.setReadOnly(true);
		txt_CustNmLen.setReadOnly(true);
		txt_ShpTicketNoLen.setReadOnly(true);
		txt_ShpTicketLineNoLen.setReadOnly(true);
		txt_ItemCdLen.setReadOnly(true);
		txt_BundleItfLen.setReadOnly(true);
		txt_CaseItfLen.setReadOnly(true);
		txt_BundleEtrLen.setReadOnly(true);
		txt_CaseEtrLen.setReadOnly(true);
		txt_ItemNmLen.setReadOnly(true);
		txt_PickQtyPtlLen.setReadOnly(true);
		txt_PiecePickPlaceLen.setReadOnly(true);
		txt_CasePickPlaceLen.setReadOnly(true);
		txt_CrossDCFlgLen.setReadOnly(true);
		txt_SupplierCdLen.setReadOnly(true);
		txt_SupplierNmLen.setReadOnly(true);
		txt_InstkTicketNoLen.setReadOnly(true);
		txt_InstkTickeLineNoLen.setReadOnly(true);
		txt_RsltPieceQtyLen.setReadOnly(true);
		txt_RsltCaseQtyLen.setReadOnly(true);
		txt_PickRsltDateLen.setReadOnly(true);
		txt_RsltFlgLen.setReadOnly(true);

		setInistView();
	}

	//#CM691143
	/** 
	 * Allow this method to set the initial values for screen.<BR>
	 * <BR>
	 * Summary: Sets up the settings of each field item in the screen from property file.<BR>
	 * 
	 * @throws Exception Report all exceptions.
	 */
	private void setInistView() throws Exception
	{

		//#CM691144
		// Set the initial value of the focus to Planned Sorting Date Enabled.
		setFocus(chk_PickPlanDate);
		
		Connection conn = null;
		try
		{
			//#CM691145
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter initParam = new DefineDataParameter();
			initParam.setSelectDefineReportData(
				DefineDataParameter.SELECTDEFINEREPORTDATA_SORTING);
			WmsScheduler schedule = new DefineReportDataSCH();
			DefineDataParameter[] param = (DefineDataParameter[]) schedule.query(conn, initParam);

			if (param != null)
			{
				//#CM691146
				// Planned Sorting Date: Enabled
				chk_PickPlanDate.setChecked(param[0].getValid_PlanDate());
				//#CM691147
				// Planned Sorting Date: Length
				txt_PickPlanDateLen.setText(param[0].getFigure_PlanDate());
				//#CM691148
				// Planned Sorting Date: Max Length
				lbl_MaxLenPickPlanDate.setText(param[0].getMaxFigure_PlanDate());
				//#CM691149
				// Planned Sorting Date: Position
				txt_PickPlanDatePst.setText(param[0].getPosition_PlanDate());
				
				//#CM691150
				// Consignor Code: Enabled
				chk_CnsgnrCd.setChecked(param[0].getValid_ConsignorCode());
				//#CM691151
				// Consignor Code: Length
				txt_CnsgnrCdLen.setText(param[0].getFigure_ConsignorCode());
				//#CM691152
				// Consignor Code: Max Length
				lbl_MaxLenCnsgnrCd.setText(param[0].getMaxFigure_ConsignorCode());
				//#CM691153
				// Consignor Code: Position
				txt_CnsgnrCdPst.setText(param[0].getPosition_ConsignorCode());
				
				//#CM691154
				// Consignor Name: Enabled
				chk_CnsgnrNm.setChecked(param[0].getValid_ConsignorName());
				//#CM691155
				// Consignor Name: Length
				txt_CnsgnrNmLen.setText(param[0].getFigure_ConsignorName());
				//#CM691156
				// Consignor Name: Max Length
				lbl_MaxLenCnsgnrNm.setText(param[0].getMaxFigure_ConsignorName());
				//#CM691157
				// Consignor Name: Position
				txt_CnsgnrNmPst.setText(param[0].getPosition_ConsignorName());
				
				//#CM691158
				// Supplier Code: Enabled
				chk_SupplierCd.setChecked(param[0].getValid_SupplierCode());
				//#CM691159
				// Supplier Code: Length
				txt_SupplierCdLen.setText(param[0].getFigure_SupplierCode());
				//#CM691160
				// Supplier Code: Max Length
				lbl_MaxLenSupplierCd.setText(param[0].getMaxFigure_SupplierCode());
				//#CM691161
				// Supplier Code: Position
				txt_SupplierCdPst.setText(param[0].getPosition_SupplierCode());
				
				//#CM691162
				// Supplier Name: Enabled
				chk_SupplierNm.setChecked(param[0].getValid_SupplierName());
				//#CM691163
				// Supplier Name: Length
				txt_SupplierNmLen.setText(param[0].getFigure_SupplierName());
				//#CM691164
				// Supplier Name: Max Length
				lbl_MaxLenSupplierNm.setText(param[0].getMaxFigure_SupplierName());
				//#CM691165
				// Supplier Name: Position
				txt_SupplierNmPst.setText(param[0].getPosition_SupplierName());

				
				//#CM691166
				// Customer Code: Enabled
				chk_CustCd.setChecked(param[0].getValid_CustomerCode());
				//#CM691167
				// Customer Code: Length
				txt_CustCdLen.setText(param[0].getFigure_CustomerCode());
				//#CM691168
				// Customer Code: Max Length
				lbl_MaxLenCustCd.setText(param[0].getMaxFigure_CustomerCode());
				//#CM691169
				// Customer Code: Position
				txt_CustCdPst.setText(param[0].getPosition_CustomerCode());
				
				//#CM691170
				// Customer Name: Enabled
				chk_CustNm.setChecked(param[0].getValid_CustomerName());
				//#CM691171
				// Customer Name: Length
				txt_CustNmLen.setText(param[0].getFigure_CustomerName());
				//#CM691172
				// Customer Name: Max Length
				lbl_MaxLenCustNm.setText(param[0].getMaxFigure_CustomerName());
				//#CM691173
				// Customer Name: Position
				txt_CustNmPst.setText(param[0].getPosition_CustomerName());
				
				
				//#CM691174
				// Shipping Ticket No.: Enabled
				chk_ShpTicketNo.setChecked(param[0].getValid_ShippingTicketNo());
				//#CM691175
				// Shipping Ticket No.: Length
				txt_ShpTicketNoLen.setText(param[0].getFigure_ShippingTicketNo());
				//#CM691176
				// Shipping Ticket No.: Max Length
				lbl_MaxLenShpTicketNo.setText(param[0].getMaxFigure_ShippingTicketNo());
				//#CM691177
				// Shipping Ticket No.: Position
				txt_ShpTicketNoPst.setText(param[0].getPosition_ShippingTicketNo());
				
				//#CM691178
				// Shipping ticket line No.: Enabled
				chk_ShpTicketLineNo.setChecked(param[0].getValid_ShippingLineNo());
				//#CM691179
				// Shipping ticket line No.: Length
				txt_ShpTicketLineNoLen.setText(param[0].getFigure_ShippingLineNo());
				//#CM691180
				// Shipping ticket line No.: Max Length
				lbl_MaxLenShpTicketLineNo.setText(param[0].getMaxFigure_ShippingLineNo());
				//#CM691181
				// Shipping ticket line No.: Position
				txt_ShpTicketLineNoPst.setText(param[0].getPosition_ShippingLineNo());
				
				//#CM691182
				// Item Code: Enabled
				chk_ItemCd.setChecked(param[0].getValid_ItemCode());
				//#CM691183
				// Item Code: Length
				txt_ItemCdLen.setText(param[0].getFigure_ItemCode());
				//#CM691184
				// Item Code: Max Length
				lbl_MaxLenItemCd.setText(param[0].getMaxFigure_ItemCode());
				//#CM691185
				// Item Code: Position
				txt_ItemCdPst.setText(param[0].getPosition_ItemCode());
				
				//#CM691186
				// Bundle ITF: Enabled
				chk_BundleItf.setChecked(param[0].getValid_BundleItf());
				//#CM691187
				// Bundle ITF: Length
				txt_BundleItfLen.setText(param[0].getFigure_BundleItf());
				//#CM691188
				// Bundle ITF: Max Length
				lbl_MaxLenBundleItf.setText(param[0].getMaxFigure_BundleItf());
				//#CM691189
				// Bundle ITF: Position
				txt_BundleItfPst.setText(param[0].getPosition_BundleItf());
				
				//#CM691190
				// Case ITF: Enabled
				chk_CaseItf.setChecked(param[0].getValid_Itf());
				//#CM691191
				// Case ITF: Length
				txt_CaseItfLen.setText(param[0].getFigure_Itf());
				//#CM691192
				// Case ITF: Max Length
				lbl_MaxLenCaseItf.setText(param[0].getMaxFigure_Itf());
				//#CM691193
				// Case ITF: Position
				txt_CaseItfPst.setText(param[0].getPosition_Itf());
				
				//#CM691194
				// Packed qty per bundle: Enabled
				chk_BundleEtr.setChecked(param[0].getValid_BundleEnteringQty());
				//#CM691195
				// Packed qty per bundle: Length
				txt_BundleEtrLen.setText(param[0].getFigure_BundleEnteringQty());
				//#CM691196
				// Packed qty per bundle: Max Length
				lbl_MaxLenBundleEtr.setText(param[0].getMaxFigure_BundleEnteringQty());
				//#CM691197
				// Packed qty per bundle: Position
				txt_BundleEtrPst.setText(param[0].getPosition_BundleEnteringQty());
				
				//#CM691198
				// Packed Qty per Case: Enabled
				chk_CaseEtr.setChecked(param[0].getValid_EnteringQty());
				//#CM691199
				// Packed Qty per Case: Length
				txt_CaseEtrLen.setText(param[0].getFigure_EnteringQty());
				//#CM691200
				// Packed Qty per Case: Max Length
				lbl_MaxLenCaseEtr.setText(param[0].getMaxFigure_EnteringQty());
				//#CM691201
				// Packed Qty per Case: Position
				txt_CaseEtrPst.setText(param[0].getPosition_EnteringQty());
				
				//#CM691202
				// Item Name: Enabled
				chk_ItemNm.setChecked(param[0].getValid_ItemName());
				//#CM691203
				// Item Name: Length
				txt_ItemNmLen.setText(param[0].getFigure_ItemName());
				//#CM691204
				// Item Name: Max Length
				lbl_MaxLenItemNm.setText(param[0].getMaxFigure_ItemName());
				//#CM691205
				// Item Name: Position
				txt_ItemNmPst.setText(param[0].getPosition_ItemName());
				
				//#CM691206
				// Sorting Qty (Total Bulk Qty): Enabled				
				chk_PickQtyPtl.setChecked(param[0].getValid_PlanQty());
				//#CM691207
				// Sorting Qty (Total Bulk Qty): Length
				txt_PickQtyPtlLen.setText(param[0].getFigure_PlanQty());
				//#CM691208
				// Sorting Qty (Total Bulk Qty): Max Length				
				lbl_MaxLenPickQtyPtl.setText(param[0].getMaxFigure_PlanQty());
				//#CM691209
				// Sorting Qty (Total Bulk Qty): Position				
				txt_PickQtyPtlPst.setText(param[0].getPosition_PlanQty());

				//#CM691210
				// Piece Sorting Place: Enabled
				chk_PiecePickPlace.setChecked(param[0].getValid_PieceLocation());
				//#CM691211
				// Piece Sorting Place: Length
				txt_PiecePickPlaceLen.setText(param[0].getFigure_PieceLocation());
				//#CM691212
				// Piece Sorting Place: Max Length
				lbl_MaxLenPiecePickPlace.setText(param[0].getMaxFigure_PieceLocation());
				//#CM691213
				// Piece Sorting Place: Position
				txt_PiecePickPlacePst.setText(param[0].getPosition_PieceLocation());
				
				//#CM691214
				// Piece Sorting Place: Enabled
				chk_CasePickPlace.setChecked(param[0].getValid_CaseLocation());
				//#CM691215
				// Piece Sorting Place: Length
				txt_CasePickPlaceLen.setText(param[0].getFigure_CaseLocation());
				//#CM691216
				// Piece Sorting Place: Max Length
				lbl_MaxLenCasePickPlace.setText(param[0].getMaxFigure_CaseLocation());
				//#CM691217
				// Piece Sorting Place: Position
				txt_CasePickPlacePst.setText(param[0].getPosition_CaseLocation());
				
				//#CM691218
				// TC/DC division: Enabled
				chk_CrossDCFlg.setChecked(param[0].getValid_TcDcFlag());
				//#CM691219
				// TC/DC division: Length
				txt_CrossDCFlgLen.setText(param[0].getFigure_TcDcFlag());
				//#CM691220
				// TC/DC division: Max Length
				lbl_MaxLenCrossDCFlg.setText(param[0].getMaxFigure_TcDcFlag());
				//#CM691221
				// TC/DC division: Position
				txt_CrossDCFlgPst.setText(param[0].getPosition_TcDcFlag());
				
				//#CM691222
				// Receiving Ticket No.: Enabled
				chk_InstkTicketNo.setChecked(param[0].getValid_InstockTicketNo());
				//#CM691223
				// Receiving Ticket No.: Length
				txt_InstkTicketNoLen.setText(param[0].getFigure_InstockTicketNo());
				//#CM691224
				// Receiving Ticket No.: Max Length
				lbl_MaxLenInstkTicketNo.setText(param[0].getMaxFigure_InstockTicketNo());
				//#CM691225
				// Receiving Ticket No.: Position
				txt_InstkTicketNoPst.setText(param[0].getPosition_InstockTicketNo());
				
				//#CM691226
				// Receiving Ticket Line No.: Enabled
				chk_InstkTickeLineNo.setChecked(param[0].getValid_InstockLineNo());
				//#CM691227
				// Receiving Ticket Line No.: Length
				txt_InstkTickeLineNoLen.setText(param[0].getFigure_InstockLineNo());
				//#CM691228
				// Receiving Ticket Line No.: Max Length
				lbl_MaxLenInstkTickeLineNo.setText(param[0].getMaxFigure_InstockLineNo());
				//#CM691229
				// Receiving Ticket Line No.: Position
				txt_InstkTickeLineNoPst.setText(param[0].getPosition_InstockLineNo());
				
				//#CM691230
				// Result Piece Qty: Enabled
				chk_RsltPieceQty.setChecked(param[0].getValid_PieceResultQty());
				//#CM691231
				// Result Piece Qty: Length
				txt_RsltPieceQtyLen.setText(param[0].getFigure_PieceResultQty());
				//#CM691232
				// Result Piece Qty: Max Length
				lbl_MaxLenRsltPieceQty.setText(param[0].getMaxFigure_PieceResultQty());
				//#CM691233
				// Result Piece Qty: Position
				txt_RsltPieceQtyPst.setText(param[0].getPosition_PieceResultQty());
				
				//#CM691234
				// Result Case Qty: Enabled
				chk_RsltCaseQty.setChecked(param[0].getValid_CaseResultQty());
				//#CM691235
				// Result Case Qty: Length
				txt_RsltCaseQtyLen.setText(param[0].getFigure_CaseResultQty());
				//#CM691236
				// Result Case Qty: Max Length
				lbl_MaxLenRsltCaseQty.setText(param[0].getMaxFigure_CaseResultQty());
				//#CM691237
				// Result Case Qty: Position
				txt_RsltCaseQtyPst.setText(param[0].getPosition_CaseResultQty());		
				
				//#CM691238
				// Sorting Result Date: Enabled
				chk_PickRsltDate.setChecked(param[0].getValid_WorkDate());
				//#CM691239
				// Sorting Result Date: Length
				txt_PickRsltDateLen.setText(param[0].getFigure_WorkDate());
				//#CM691240
				// Sorting Result Date: Max Length
				lbl_MaxLenPickRsltDate.setText(param[0].getMaxFigure_WorkDate());
				//#CM691241
				// Sorting Result Date: Position
				txt_PickRsltDatePst.setText(param[0].getPosition_WorkDate());
				
				//#CM691242
				// Result division: Enabled
				chk_RsltFlg.setChecked(param[0].getValid_ResultFlag());
				//#CM691243
				// Result division: Length
				txt_RsltFlgLen.setText(param[0].getFigure_ResultFlag());
				//#CM691244
				// Result division: Max Length
				lbl_MaxLenRsltFlg.setText(param[0].getMaxFigure_ResultFlag());
				//#CM691245
				// Result division: Position
				txt_ResultFlgPosition.setText(param[0].getPosition_ResultFlag());
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
				//#CM691246
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

	//#CM691247
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

		//#CM691248
		// Planned Sorting Date: Position
		if (position.getText().equals(txt_PickPlanDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691249
		// Consignor Code: Position
		if (position.getText().equals(txt_CnsgnrCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691250
		// Consignor Name: Position
		if (position.getText().equals(txt_CnsgnrNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691251
		// Customer Code: Position
		if (position.getText().equals(txt_CustCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691252
		// Customer Name: Position
		if (position.getText().equals(txt_CustNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691253
		// Shipping Ticket No.: Position
		if (position.getText().equals(txt_ShpTicketNoPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691254
		// Shipping ticket line No.: Position
		if (position.getText().equals(txt_ShpTicketLineNoPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691255
		// Item Code: Position
		if (position.getText().equals(txt_ItemCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691256
		// Bundle ITF: Position
		if (position.getText().equals(txt_BundleItfPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691257
		// Case ITF: Position
		if (position.getText().equals(txt_CaseItfPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691258
		// Packed qty per bundle: Position
		if (position.getText().equals(txt_BundleEtrPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691259
		// Packed Qty per Case: Position
		if (position.getText().equals(txt_CaseEtrPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691260
		// Item Name: Position
		if (position.getText().equals(txt_ItemNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691261
		// Sorting Qty (Total Bulk Qty): Position
		if (position.getText().equals(txt_PickQtyPtlPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691262
		// Piece Sorting Place: Position
		if (position.getText().equals(txt_PiecePickPlacePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691263
		// Case Sorting Place: Position
		if (position.getText().equals(txt_CasePickPlacePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691264
		// TC/DC division: Position
		if (position.getText().equals(txt_CrossDCFlgPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691265
		// Supplier Code: Position
		if (position.getText().equals(txt_SupplierCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691266
		// Supplier Name: Position
		if (position.getText().equals(txt_SupplierNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691267
		// Receiving Ticket No.: Position
		if (position.getText().equals(txt_InstkTicketNoPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691268
		// Receiving Ticket Line No.: Position
		if (position.getText().equals(txt_InstkTickeLineNoPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691269
		// Sorting Result Date: Position
		if (position.getText().equals(txt_PickRsltDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691270
		// Result division: Position
		if (position.getText().equals(txt_ResultFlgPosition.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}

		//#CM691271
		// Result Piece Qty: Position
		if (position.getText().equals(txt_RsltPieceQtyPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM691272
		// Result Case Qty: Position
		if (position.getText().equals(txt_RsltCaseQtyPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		return true;
	}
	
	//#CM691273
	/** 
	 * Allow this method to check wheter the position is duplicated or not in a sequential order.<BR>
	 * <BR>
	 * @return Return false if two or more data occupies a single position.
	 * @throws Exception Report all exceptions.
	 */
	private boolean checkRepeat() throws Exception
	{

		boolean repeatChkFlg = true;
		
		if (repeatChkFlg && chk_PickPlanDate.getChecked())
		{
			repeatChkFlg = positionCheck(txt_PickPlanDatePst);
		}

		if (repeatChkFlg && chk_CnsgnrCd.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CnsgnrCdPst);
		}

		if (repeatChkFlg && chk_CnsgnrNm.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CnsgnrNmPst);
		}
		
		if (repeatChkFlg && chk_SupplierCd.getChecked())
		{
			repeatChkFlg = positionCheck(txt_SupplierCdPst);
		}
		
		if (repeatChkFlg && chk_CustCd.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CustCdPst);
		}

		if (repeatChkFlg && chk_SupplierNm.getChecked())
		{
			repeatChkFlg = positionCheck(txt_SupplierNmPst);
		}

		if (repeatChkFlg && chk_CustNm.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CustNmPst);
		}

		if (repeatChkFlg && chk_ShpTicketNo.getChecked())
		{
			repeatChkFlg = positionCheck(txt_ShpTicketNoPst);
		}

		if (repeatChkFlg && chk_ShpTicketLineNo.getChecked())
		{
			repeatChkFlg = positionCheck(txt_ShpTicketLineNoPst);
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

		if (repeatChkFlg && chk_CaseEtr.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CaseEtrPst);
		}

		if (repeatChkFlg && chk_ItemNm.getChecked())
		{
			repeatChkFlg = positionCheck(txt_ItemNmPst);
		}

		if (repeatChkFlg && chk_PickQtyPtl.getChecked())
		{
			repeatChkFlg = positionCheck(txt_PickQtyPtlPst);
		}

		if (repeatChkFlg && chk_CrossDCFlg.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CrossDCFlgPst);
		}

		if (repeatChkFlg && chk_InstkTicketNo.getChecked())
		{
			repeatChkFlg = positionCheck(txt_InstkTicketNoPst);
		}

		if (repeatChkFlg && chk_InstkTickeLineNo.getChecked())
		{
			repeatChkFlg = positionCheck(txt_InstkTickeLineNoPst);
		}

		if (repeatChkFlg && chk_PickRsltDate.getChecked())
		{
			repeatChkFlg = positionCheck(txt_PickRsltDatePst);
		}

		if (repeatChkFlg && chk_RsltFlg.getChecked())
		{
			repeatChkFlg = positionCheck(txt_ResultFlgPosition);
		}

		if (repeatChkFlg && chk_PiecePickPlace.getChecked())
		{
			repeatChkFlg = positionCheck(txt_PiecePickPlacePst);
		}
		
		if (repeatChkFlg && chk_CasePickPlace.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CasePickPlacePst);
		}

		if (repeatChkFlg && chk_RsltPieceQty.getChecked())
		{
			repeatChkFlg = positionCheck(txt_RsltPieceQtyPst);
		}
		
		if (repeatChkFlg && chk_RsltCaseQty.getChecked())
		{
			repeatChkFlg = positionCheck(txt_RsltCaseQtyPst);
		}
		return repeatChkFlg;
		
	}

	//#CM691274
	/** 
	 * Allow this method to check whether a checkbox is ticked off or not.<BR>
	 * <BR>
	 * @return Return false if no item ticked off.
	 * @throws Exception Report all exceptions.
	 */
	private boolean checkCheckBox() throws Exception
	{
		if (!chk_PickPlanDate.getChecked()
			&& !chk_CnsgnrCd.getChecked()
			&& !chk_CnsgnrNm.getChecked()
			&& !chk_SupplierCd.getChecked()
			&& !chk_SupplierNm.getChecked()
			&& !chk_ShpTicketNo.getChecked()
			&& !chk_ShpTicketLineNo.getChecked()
			&& !chk_ItemCd.getChecked()
			&& !chk_BundleItf.getChecked()
			&& !chk_CaseItf.getChecked()
			&& !chk_BundleEtr.getChecked()
			&& !chk_CaseEtr.getChecked()
			&& !chk_ItemNm.getChecked()
			&& !chk_PickQtyPtl.getChecked()
			&& !chk_CrossDCFlg.getChecked()
			&& !chk_CustCd.getChecked()
			&& !chk_CustNm.getChecked()
			&& !chk_InstkTicketNo.getChecked()
			&& !chk_InstkTickeLineNo.getChecked()
			&& !chk_PickRsltDate.getChecked()
			&& !chk_RsltFlg.getChecked()
			&& !chk_PiecePickPlace.getChecked()
			&& !chk_CasePickPlace.getChecked()
			&& !chk_RsltPieceQty.getChecked()
			&& !chk_RsltCaseQty.getChecked())
		{
			return false;
		}
		return true;
	}
	//#CM691275
	// Package methods -----------------------------------------------

	//#CM691276
	// Protected methods ---------------------------------------------

	//#CM691277
	// Private methods -----------------------------------------------

	//#CM691278
	// Event handler methods -----------------------------------------
	//#CM691279
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691280
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691281
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Back_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691282
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
		//#CM691283
		// Shift to the original screen.
		forward(DO_DEFINEREPORT);
	}

	//#CM691284
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691285
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

	//#CM691286
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Valid1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691287
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DigitsUseLength1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691288
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLength1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691289
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Position1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691290
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Valid2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691291
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DigitsUseLength2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691292
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLength2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691293
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Position2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691294
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PickingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691295
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PickPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691296
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PickPlanDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691297
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickPlanDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691298
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickPlanDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691299
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickPlanDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691300
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenPickPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691301
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickPlanDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691302
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickPlanDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691303
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickPlanDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691304
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PickingQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691305
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PickQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691306
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PickQtyPtl_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691307
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickQtyPtlLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691308
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickQtyPtlLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691309
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickQtyPtlLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691310
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenPickQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691311
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickQtyPtlPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691312
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickQtyPtlPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691313
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickQtyPtlPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691314
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691315
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691316
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691317
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691318
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691319
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691320
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691321
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691322
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691323
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691324
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PiecePickingPlace_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691325
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PiecePickPlace_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691326
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PiecePickPlace_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691327
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PiecePickPlaceLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691328
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PiecePickPlaceLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691329
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PiecePickPlaceLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691330
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenPiecePickPlace_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691331
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PiecePickPlacePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691332
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PiecePickPlacePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691333
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PiecePickPlacePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691334
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691335
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691336
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691337
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691338
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691339
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691340
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691341
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691342
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691343
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691344
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePickingPlace_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691345
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CasePickPlace_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691346
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CasePickPlace_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691347
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CasePickPlaceLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691348
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CasePickPlaceLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691349
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CasePickPlaceLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691350
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCasePickPlace_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691351
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CasePickPlacePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691352
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CasePickPlacePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691353
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CasePickPlacePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691354
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691355
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CustCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691356
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CustCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691357
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691358
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691359
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691360
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCustCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691361
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691362
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691363
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691364
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CrossDCFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691365
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CrossDCFlg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691366
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CrossDCFlg_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691367
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CrossDCFlgLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691368
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CrossDCFlgLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691369
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CrossDCFlgLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691370
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCrossDCFlg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691371
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CrossDCFlgPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691372
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CrossDCFlgPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691373
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CrossDCFlgPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691374
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691375
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CustNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691376
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CustNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691377
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691378
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691379
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691380
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCustNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691381
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691382
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691383
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691384
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SupplierCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691385
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_SupplierCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691386
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_SupplierCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691387
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691388
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691389
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691390
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenSupplierCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691391
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691392
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691393
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691394
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ShippingTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691395
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ShpTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691396
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ShpTicketNo_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691397
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpTicketNoLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691398
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpTicketNoLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691399
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpTicketNoLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691400
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenShpTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691401
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpTicketNoPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691402
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpTicketNoPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691403
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpTicketNoPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691404
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SupplierName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691405
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_SupplierNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691406
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_SupplierNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691407
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691408
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691409
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691410
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenSupplierNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691411
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691412
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691413
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691414
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ShpTktLineNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691415
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ShpTicketLineNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691416
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ShpTicketLineNo_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691417
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpTicketLineNoLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691418
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpTicketLineNoLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691419
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpTicketLineNoLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691420
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenShpTicketLineNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691421
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpTicketLineNoPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691422
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpTicketLineNoPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691423
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpTicketLineNoPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691424
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InstockTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691425
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InstkTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691426
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InstkTicketNo_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691427
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkTicketNoLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691428
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkTicketNoLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691429
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkTicketNoLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691430
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenInstkTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691431
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkTicketNoPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691432
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkTicketNoPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691433
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkTicketNoPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691434
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691435
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691436
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691437
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691438
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691439
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691440
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691441
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691442
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691443
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691444
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InstkTktLineNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691445
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InstkTickeLineNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691446
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InstkTickeLineNo_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691447
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkTickeLineNoLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691448
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkTickeLineNoLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691449
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkTickeLineNoLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691450
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenInstkTickeLineNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691451
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkTickeLineNoPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691452
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkTickeLineNoPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691453
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkTickeLineNoPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691454
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691455
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691456
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleItf_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691457
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691458
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691459
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691460
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenBundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691461
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691462
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691463
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691464
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ResultPieceQtyTwoByte_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691465
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691466
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltPieceQty_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691467
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691468
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691469
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691470
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRsltPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691471
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691472
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691473
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691474
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691475
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691476
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseItf_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691477
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691478
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691479
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691480
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691481
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691482
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691483
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691484
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ResultCaseQtyTwoByte_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691485
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691486
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltCaseQty_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691487
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691488
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691489
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691490
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRsltCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691491
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691492
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691493
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691494
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691495
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691496
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleEtr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691497
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691498
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691499
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691500
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenBandleEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691501
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691502
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691503
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691504
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PickingResultDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691505
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PickRsltDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691506
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PickRsltDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691507
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickRsltDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691508
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickRsltDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691509
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickRsltDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691510
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenPickRsltDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691511
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickRsltDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691512
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickRsltDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691513
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickRsltDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691514
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691515
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691516
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseEtr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691517
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691518
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691519
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691520
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691521
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691522
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691523
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691524
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ResultFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691525
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltFlg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691526
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltFlg_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691527
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TsltFlgLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691528
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TsltFlgLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691529
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TsltFlgLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691530
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRsltFlg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691531
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ResultFlagPosition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691532
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ResultFlagPosition_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691533
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ResultFlagPosition_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691534
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691535
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691536
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM691537
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNameLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691538
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNameLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691539
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNameLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691540
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691541
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691542
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691543
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691544
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691545
	/** 
	 * Clicking on Add button for field items set for reporting data (setting of Sorting Result field) invokes this.<BR>
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
		//#CM691546
		// Set the initial value of the focus to Planned Sorting Date Enabled.
	    setFocus(chk_PickPlanDate);
	    
		//#CM691547
		// Check the checkbox.
		if (!checkCheckBox())
		{
			//#CM691548
			// 6023174=Please select 1 or more items.
			message.setMsgResourceKey("6023174");
			return;
		}

		//#CM691549
		// Check for input.
		WmsCheckker.checkDefine(lbl_PickingPlanDate, chk_PickPlanDate, txt_PickPlanDateLen, lbl_MaxLenPickPlanDate, txt_PickPlanDatePst);
		WmsCheckker.checkDefine(lbl_ConsignorCode, chk_CnsgnrCd, txt_CnsgnrCdLen, lbl_MaxLenCnsgnrCd, txt_CnsgnrCdPst);
		WmsCheckker.checkDefine(lbl_ConsignorName, chk_CnsgnrNm, txt_CnsgnrNmLen, lbl_MaxLenCnsgnrNm, txt_CnsgnrNmPst);
		WmsCheckker.checkDefine(lbl_CustomerCode, chk_CustCd, txt_CustCdLen, lbl_MaxLenCustCd, txt_CustCdPst);
		WmsCheckker.checkDefine(lbl_CustomerName, chk_CustNm, txt_CustNmLen, lbl_MaxLenCustNm, txt_CustNmPst);
		WmsCheckker.checkDefine(lbl_ShippingTicketNo, chk_ShpTicketNo, txt_ShpTicketNoLen, lbl_MaxLenShpTicketNo, txt_ShpTicketNoPst);
		WmsCheckker.checkDefine(lbl_ShpTktLineNo, chk_ShpTicketLineNo, txt_ShpTicketLineNoLen, lbl_MaxLenShpTicketLineNo, txt_ShpTicketLineNoPst);
		WmsCheckker.checkDefine(lbl_ItemCode, chk_ItemCd, txt_ItemCdLen, lbl_MaxLenItemCd, txt_ItemCdPst);
		WmsCheckker.checkDefine(lbl_BundleItf, chk_BundleItf, txt_BundleItfLen, lbl_MaxLenBundleItf, txt_BundleItfPst);
		WmsCheckker.checkDefine(lbl_CaseItf, chk_CaseItf, txt_CaseItfLen, lbl_MaxLenCaseItf, txt_CaseItfPst);
		WmsCheckker.checkDefine(lbl_BundleEntering, chk_BundleEtr, txt_BundleEtrLen, lbl_MaxLenBundleEtr, txt_BundleEtrPst);
		WmsCheckker.checkDefine(lbl_CaseEntering, chk_CaseEtr, txt_CaseEtrLen, lbl_MaxLenCaseEtr, txt_CaseEtrPst);
		WmsCheckker.checkDefine(lbl_ItemName, chk_ItemNm, txt_ItemNmLen, lbl_MaxLenItemNm, txt_ItemNmPst);
		WmsCheckker.checkDefine(lbl_PickingQtyPtl, chk_PickQtyPtl, txt_PickQtyPtlLen, lbl_MaxLenPickQtyPtl, txt_PickQtyPtlPst);
		WmsCheckker.checkDefine(lbl_PiecePickingPlace, chk_PiecePickPlace, txt_PiecePickPlaceLen, lbl_MaxLenPiecePickPlace, txt_PiecePickPlacePst);
		WmsCheckker.checkDefine(lbl_CasePickingPlace, chk_CasePickPlace, txt_CasePickPlaceLen, lbl_MaxLenCasePickPlace, txt_CasePickPlacePst);
		WmsCheckker.checkDefine(lbl_CrossDCFlg, chk_CrossDCFlg, txt_CrossDCFlgLen, lbl_MaxLenCrossDCFlg, txt_CrossDCFlgPst);
		WmsCheckker.checkDefine(lbl_SupplierCode, chk_SupplierCd, txt_SupplierCdLen, lbl_MaxLenSupplierCd, txt_SupplierCdPst);
		WmsCheckker.checkDefine(lbl_SupplierName, chk_SupplierNm, txt_SupplierNmLen, lbl_MaxLenSupplierNm, txt_SupplierNmPst);
		WmsCheckker.checkDefine(lbl_InstockTicketNo, chk_InstkTicketNo, txt_InstkTicketNoLen, lbl_MaxLenInstkTicketNo, txt_InstkTicketNoPst);
		WmsCheckker.checkDefine(lbl_InstkTktLineNo, chk_InstkTickeLineNo, txt_InstkTickeLineNoLen, lbl_MaxLenInstkTickeLineNo, txt_InstkTickeLineNoPst);
		WmsCheckker.checkDefine(lbl_ResultPieceQtyTwoByte, chk_RsltPieceQty, txt_RsltPieceQtyLen, lbl_MaxLenRsltPieceQty, txt_RsltPieceQtyPst);
		WmsCheckker.checkDefine(lbl_ResultCaseQtyTwoByte, chk_RsltCaseQty, txt_RsltCaseQtyLen, lbl_MaxLenRsltCaseQty, txt_RsltCaseQtyPst);
		WmsCheckker.checkDefine(lbl_PickingResultDate, chk_PickRsltDate, txt_PickRsltDateLen, lbl_MaxLenPickRsltDate, txt_PickRsltDatePst);
		WmsCheckker.checkDefine(lbl_ResultFlg, chk_RsltFlg, txt_RsltFlgLen, lbl_MaxLenRsltFlg, txt_ResultFlgPosition);

		//#CM691550
		// Check for duplicate Position.
		if (!checkRepeat())
		{
			//#CM691551
			// Set the Error Message.
			//#CM691552
			// 6023098=No duplicate of sequential order is allowed.
			message.setMsgResourceKey("6023098");
			return;
		}
		
		Connection conn = null;
		
		try
		{
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	
			DefineDataParameter[] param = new DefineDataParameter[1];
			param[0] = new DefineDataParameter();
			param[0].setSelectDefineReportData(DefineDataParameter.SELECTDEFINEREPORTDATA_SORTING);
	
			WmsScheduler schedule = new DefineReportDataSCH();
	
			//#CM691553
			// Planned Sorting Date: Enabled
			param[0].setValid_PlanDate(chk_PickPlanDate.getChecked());
			//#CM691554
			// Planned Sorting Date: Length
			param[0].setFigure_PlanDate(txt_PickPlanDateLen.getText());
			//#CM691555
			// Planned Sorting Date: Max Length
			param[0].setMaxFigure_PlanDate(lbl_MaxLenPickPlanDate.getText());
			//#CM691556
			// Planned Sorting Date: Position
			param[0].setPosition_PlanDate(txt_PickPlanDatePst.getText());
			//#CM691557
			// Consignor Code: Enabled
			param[0].setValid_ConsignorCode(chk_CnsgnrCd.getChecked());
			//#CM691558
			// Consignor Code: Length
			param[0].setFigure_ConsignorCode(txt_CnsgnrCdLen.getText());
			//#CM691559
			// Consignor Code: Max Length
			param[0].setMaxFigure_ConsignorCode(lbl_MaxLenCnsgnrCd.getText());
			//#CM691560
			// Consignor Code: Position
			param[0].setPosition_ConsignorCode(txt_CnsgnrCdPst.getText());
			//#CM691561
			// Consignor Name: Enabled
			param[0].setValid_ConsignorName(chk_CnsgnrNm.getChecked());
			//#CM691562
			// Consignor Name: Length
			param[0].setFigure_ConsignorName(txt_CnsgnrNmLen.getText());
			//#CM691563
			// Consignor Name: Max Length
			param[0].setMaxFigure_ConsignorName(lbl_MaxLenCnsgnrNm.getText());
			//#CM691564
			// Consignor Name: Position
			param[0].setPosition_ConsignorName(txt_CnsgnrNmPst.getText());
			//#CM691565
			// Supplier Code: Enabled
			param[0].setValid_SupplierCode(chk_SupplierCd.getChecked());
			//#CM691566
			// Supplier Code: Length
			param[0].setFigure_SupplierCode(txt_SupplierCdLen.getText());
			//#CM691567
			// Supplier Code: Max Length
			param[0].setMaxFigure_SupplierCode(lbl_MaxLenSupplierCd.getText());
			//#CM691568
			// Supplier Code: Position
			param[0].setPosition_SupplierCode(txt_SupplierCdPst.getText());
			//#CM691569
			// Supplier Name: Enabled
			param[0].setValid_SupplierName(chk_SupplierNm.getChecked());
			//#CM691570
			// Supplier Name: Length
			param[0].setFigure_SupplierName(txt_SupplierNmLen.getText());
			//#CM691571
			// Supplier Name: Max Length
			param[0].setMaxFigure_SupplierName(lbl_MaxLenSupplierNm.getText());
			//#CM691572
			// Supplier Name: Position
			param[0].setPosition_SupplierName(txt_SupplierNmPst.getText());
			//#CM691573
			// Shipping Ticket No.: Enabled
			param[0].setValid_ShippingTicketNo(chk_ShpTicketNo.getChecked());
			//#CM691574
			// Shipping Ticket No.: Length
			param[0].setFigure_ShippingTicketNo(txt_ShpTicketNoLen.getText());
			//#CM691575
			// Shipping Ticket No.: Max Length
			param[0].setMaxFigure_ShippingTicketNo(lbl_MaxLenShpTicketNo.getText());
			//#CM691576
			// Shipping Ticket No.: Position
			param[0].setPosition_ShippingTicketNo(txt_ShpTicketNoPst.getText());
			//#CM691577
			// Shipping ticket line No.: Enabled
			param[0].setValid_ShippingLineNo(chk_ShpTicketLineNo.getChecked());
			//#CM691578
			// Shipping ticket line No.: Length
			param[0].setFigure_ShippingLineNo(txt_ShpTicketLineNoLen.getText());
			//#CM691579
			// Shipping ticket line No.: Max Length
			param[0].setMaxFigure_ShippingLineNo(lbl_MaxLenShpTicketLineNo.getText());
			//#CM691580
			// Shipping ticket line No.: Position
			param[0].setPosition_ShippingLineNo(txt_ShpTicketLineNoPst.getText());
			//#CM691581
			// Item Code: Enabled
			param[0].setValid_ItemCode(chk_ItemCd.getChecked());
			//#CM691582
			// Item Code: Length
			param[0].setFigure_ItemCode(txt_ItemCdLen.getText());
			//#CM691583
			// Item Code: Max Length
			param[0].setMaxFigure_ItemCode(lbl_MaxLenItemCd.getText());
			//#CM691584
			// Item Code: Position
			param[0].setPosition_ItemCode(txt_ItemCdPst.getText());
			//#CM691585
			// Bundle ITF: Enabled
			param[0].setValid_BundleItf(chk_BundleItf.getChecked());
			//#CM691586
			// Bundle ITF: Length
			param[0].setFigure_BundleItf(txt_BundleItfLen.getText());
			//#CM691587
			// Bundle ITF: Max Length
			param[0].setMaxFigure_BundleItf(lbl_MaxLenBundleItf.getText());
			//#CM691588
			// Bundle ITF: Position
			param[0].setPosition_BundleItf(txt_BundleItfPst.getText());
			//#CM691589
			// Case ITF: Enabled
			param[0].setValid_Itf(chk_CaseItf.getChecked());
			//#CM691590
			// Case ITF: Length
			param[0].setFigure_Itf(txt_CaseItfLen.getText());
			//#CM691591
			// Case ITF: Max Length
			param[0].setMaxFigure_Itf(lbl_MaxLenCaseItf.getText());
			//#CM691592
			// Case ITF: Position
			param[0].setPosition_Itf(txt_CaseItfPst.getText());
			//#CM691593
			// Packed qty per bundle: Enabled
			param[0].setValid_BundleEnteringQty(chk_BundleEtr.getChecked());
			//#CM691594
			// Packed qty per bundle: Length
			param[0].setFigure_BundleEnteringQty(txt_BundleEtrLen.getText());
			//#CM691595
			// Packed qty per bundle: Max Length
			param[0].setMaxFigure_BundleEnteringQty(lbl_MaxLenBundleEtr.getText());
			//#CM691596
			// Packed qty per bundle: Position
			param[0].setPosition_BundleEnteringQty(txt_BundleEtrPst.getText());
			//#CM691597
			// Packed Qty per Case: Enabled
			param[0].setValid_EnteringQty(chk_CaseEtr.getChecked());
			//#CM691598
			// Packed Qty per Case: Length
			param[0].setFigure_EnteringQty(txt_CaseEtrLen.getText());
			//#CM691599
			// Packed Qty per Case: Max Length
			param[0].setMaxFigure_EnteringQty(lbl_MaxLenCaseEtr.getText());
			//#CM691600
			// Packed Qty per Case: Position
			param[0].setPosition_EnteringQty(txt_CaseEtrPst.getText());
			//#CM691601
			// Item Name: Enabled
			param[0].setValid_ItemName(chk_ItemNm.getChecked());
			//#CM691602
			// Item Name: Length
			param[0].setFigure_ItemName(txt_ItemNmLen.getText());
			//#CM691603
			// Item Name: Max Length
			param[0].setMaxFigure_ItemName(lbl_MaxLenItemNm.getText());
			//#CM691604
			// Item Name: Position
			param[0].setPosition_ItemName(txt_ItemNmPst.getText());
			//#CM691605
			// Sorting Qty (Total Bulk Qty): Enabled
			param[0].setValid_PlanQty(chk_PickQtyPtl.getChecked());
			//#CM691606
			// Sorting Qty (Total Bulk Qty): Length
			param[0].setFigure_PlanQty(txt_PickQtyPtlLen.getText());
			//#CM691607
			// Sorting Qty (Total Bulk Qty): Max Length
			param[0].setMaxFigure_PlanQty(lbl_MaxLenPickQtyPtl.getText());
			//#CM691608
			// Sorting Qty (Total Bulk Qty): Position
			param[0].setPosition_PlanQty(txt_PickQtyPtlPst.getText());
			//#CM691609
			// TC/DC division: Enabled
			param[0].setValid_TcDcFlag(chk_CrossDCFlg.getChecked());
			//#CM691610
			// TC/DC division: Length
			param[0].setFigure_TcDcFlag(txt_CrossDCFlgLen.getText());
			//#CM691611
			// TC/DC division: Max Length
			param[0].setMaxFigure_TcDcFlag(lbl_MaxLenCrossDCFlg.getText());
			//#CM691612
			// TC/DC division: Position
			param[0].setPosition_TcDcFlag(txt_CrossDCFlgPst.getText());
			//#CM691613
			// Customer Code: Enabled
			param[0].setValid_CustomerCode(chk_CustCd.getChecked());
			//#CM691614
			// Customer Code: Length
			param[0].setFigure_CustomerCode(txt_CustCdLen.getText());
			//#CM691615
			// Customer Code: Max Length
			param[0].setMaxFigure_CustomerCode(lbl_MaxLenCustCd.getText());
			//#CM691616
			// Customer Code: Position
			param[0].setPosition_CustomerCode(txt_CustCdPst.getText());
			//#CM691617
			// Customer Name: Enabled
			param[0].setValid_CustomerName(chk_CustNm.getChecked());
			//#CM691618
			// Customer Name: Length
			param[0].setFigure_CustomerName(txt_CustNmLen.getText());
			//#CM691619
			// Customer Name: Max Length
			param[0].setMaxFigure_CustomerName(lbl_MaxLenCustNm.getText());
			//#CM691620
			// Customer Name: Position
			param[0].setPosition_CustomerName(txt_CustNmPst.getText());
			//#CM691621
			// Receiving Ticket No.: Enabled
			param[0].setValid_InstockTicketNo(chk_InstkTicketNo.getChecked());
			//#CM691622
			// Receiving Ticket No.: Length
			param[0].setFigure_InstockTicketNo(txt_InstkTicketNoLen.getText());
			//#CM691623
			// Receiving Ticket No.: Max Length
			param[0].setMaxFigure_InstockTicketNo(lbl_MaxLenInstkTicketNo.getText());
			//#CM691624
			// Receiving Ticket No.: Position
			param[0].setPosition_InstockTicketNo(txt_InstkTicketNoPst.getText());
			//#CM691625
			// Receiving Ticket Line No.: Enabled
			param[0].setValid_InstockLineNo(chk_InstkTickeLineNo.getChecked());
			//#CM691626
			// Receiving Ticket Line No.: Length
			param[0].setFigure_InstockLineNo(txt_InstkTickeLineNoLen.getText());
			//#CM691627
			// Receiving Ticket Line No.: Max Length
			param[0].setMaxFigure_InstockLineNo(lbl_MaxLenInstkTickeLineNo.getText());
			//#CM691628
			// Receiving Ticket Line No.: Position
			param[0].setPosition_InstockLineNo(txt_InstkTickeLineNoPst.getText());
			//#CM691629
			// Sorting Result Date: Enabled
			param[0].setValid_WorkDate(chk_PickRsltDate.getChecked());
			//#CM691630
			// Sorting Result Date: Length
			param[0].setFigure_WorkDate(txt_PickRsltDateLen.getText());
			//#CM691631
			// Sorting Result Date: Max Length
			param[0].setMaxFigure_WorkDate(lbl_MaxLenPickRsltDate.getText());
			//#CM691632
			// Sorting Result Date: Position
			param[0].setPosition_WorkDate(txt_PickRsltDatePst.getText());
			//#CM691633
			// Result division: Enabled
			param[0].setValid_ResultFlag(chk_RsltFlg.getChecked());
			//#CM691634
			// Result division: Length
			param[0].setFigure_ResultFlag(txt_RsltFlgLen.getText());
			//#CM691635
			// Result division: Max Length
			param[0].setMaxFigure_ResultFlag(lbl_MaxLenRsltFlg.getText());
			//#CM691636
			// Result division: Position
			param[0].setPosition_ResultFlag(txt_ResultFlgPosition.getText());
			//#CM691637
			// Piece Sorting Place: Enabled
			param[0].setValid_PieceLocation(chk_PiecePickPlace.getChecked());
			//#CM691638
			// Piece Sorting Place: Length
			param[0].setFigure_PieceLocation(txt_PiecePickPlaceLen.getText());
			//#CM691639
			// Piece Sorting Place: Max Length
			param[0].setMaxFigure_PieceLocation(lbl_MaxLenPiecePickPlace.getText());
			//#CM691640
			// Piece Sorting Place: Position
			param[0].setPosition_PieceLocation(txt_PiecePickPlacePst.getText());
			//#CM691641
			// Case Sorting Place: Enabled
			param[0].setValid_CaseLocation(chk_CasePickPlace.getChecked());
			//#CM691642
			// Case Sorting Place: Length
			param[0].setFigure_CaseLocation(txt_CasePickPlaceLen.getText());
			//#CM691643
			// Case Sorting Place: Max Length
			param[0].setMaxFigure_CaseLocation(lbl_MaxLenCasePickPlace.getText());
			//#CM691644
			// Case Sorting Place: Position
			param[0].setPosition_CaseLocation(txt_CasePickPlacePst.getText());
			//#CM691645
			// Result Piece Qty: Enabled
			param[0].setValid_PieceResultQty(chk_RsltPieceQty.getChecked());
			//#CM691646
			// Result Piece Qty: Length
			param[0].setFigure_PieceResultQty(txt_RsltPieceQtyLen.getText());
			//#CM691647
			// Result Piece Qty: Max Length
			param[0].setMaxFigure_PieceResultQty(lbl_MaxLenRsltPieceQty.getText());
			//#CM691648
			// Result Piece Qty: Position
			param[0].setPosition_PieceResultQty(txt_RsltPieceQtyPst.getText());
			//#CM691649
			// Result Case Qty: Enabled
			param[0].setValid_CaseResultQty(chk_RsltCaseQty.getChecked());
			//#CM691650
			// Result Case Qty: Length
			param[0].setFigure_CaseResultQty(txt_RsltCaseQtyLen.getText());
			//#CM691651
			// Result Case Qty: Max Length
			param[0].setMaxFigure_CaseResultQty(lbl_MaxLenRsltCaseQty.getText());
			//#CM691652
			// Result Case Qty: Position
			param[0].setPosition_CaseResultQty(txt_RsltCaseQtyPst.getText());

			//#CM691653
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
				//#CM691654
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
				//#CM691655
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

	//#CM691656
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691657
	/** 
	 * Clicking on Clear button for field items set for reporting data (setting of Sorting Result field) invokes this.<BR>
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
	//#CM691658
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenBundleEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691659
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691660
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691661
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691662
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691663
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691664
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM691665
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CrossDCFlg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691666
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ResultFlg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691667
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ResultFlgPosition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM691668
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ResultFlgPosition_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM691669
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ResultFlgPosition_TabKey(ActionEvent e) throws Exception
	{
	}

}

//#CM691670
//end of class
