// $Id: DefineReportDataShippingBusiness.java,v 1.2 2006/11/13 08:18:42 suresh Exp $

//#CM690691
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

//#CM690692
/**
 * Designer :  A.Nagasawa <BR>
 * Maker :     K.Mukai <BR>
 * <BR>
 * Allow this class to provide a screen to set a field item for reporting data (to set the Shipping Result field).<BR>
 * Set the searched value via the schedule for each field item.
 * Set the contents entered via screen for the parameter. Allow the schedule to set the Report Data field (set the Shipping Result field) based on the parameter.<BR>
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
 * 	 Planned Shipping Date+<BR>
 * 	 Order Date+<BR>
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
 * 	 Planned Shipping Qty (Total Bulk Qty) +<BR>
 *   TC/DC division+<BR>
 *   Supplier Code+<BR>
 * 	 Supplier Name+<BR>
 * 	 Receiving Ticket No.+<BR>
 * 	 Receiving Ticket Line No.+<BR>
 * 	 Shipping Qty (Total Bulk Qty) +<BR>
 * 	 Shipping Result Date +<BR>
 * 	 Result division+<BR>
 * 	 Expiry Date+<BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:42 $
 * @author  $Author: suresh $
 */
public class DefineReportDataShippingBusiness
	extends DefineReportDataShipping
	implements WMSConstants
{
	//#CM690693
	// Class fields --------------------------------------------------

	//#CM690694
	// Class variables -----------------------------------------------

	//#CM690695
	// Class method --------------------------------------------------

	//#CM690696
	// Constructors --------------------------------------------------

	//#CM690697
	// Public methods ------------------------------------------------
	//#CM690698
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
			//#CM690699
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM690700
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM690701
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM690702
			//Set the path to the help file.
			//#CM690703
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM690704
		// MSG-0009= Do you add it?
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}

	//#CM690705
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
		//#CM690706
		// Set the initial value of the focus to Planned Shipping Date Enabled.
		setFocus(chk_CommonUseShpPlanDate);

		//#CM690707
		// Title
		lbl_SettingName.setResourceKey(this.getViewState().getString("TITLE"));

		//#CM690708
		// Check that the length is read-only. Length.
		txt_ShpPlanDateLen.setReadOnly(true);
		txt_OdrgDateLen.setReadOnly(true);
		txt_CnsgnrCdLen.setReadOnly(true);
		txt_CnsgnrNmLen.setReadOnly(true);
		txt_CustCdLen.setReadOnly(true);
		txt_CustNmLen.setReadOnly(true);
		txt_TicketNoLength.setReadOnly(true);
		txt_TktLineNoLen.setReadOnly(true);
		txt_ItemCodeLength.setReadOnly(true);
		txt_BundleItfLength.setReadOnly(true);
		txt_CaseItfLength.setReadOnly(true);
		txt_BdlEtrLen.setReadOnly(true);
		txt_CaseEtrLen.setReadOnly(true);
		txt_ItemNameLength.setReadOnly(true);
		txt_ShpPlanQtyPtlLen.setReadOnly(true);
		txt_TCDCFlagLength.setReadOnly(true);
		txt_SplCdLen.setReadOnly(true);
		txt_SplNmLen.setReadOnly(true);
		txt_InstkTktNoLen.setReadOnly(true);
		txt_InstkTktLineNoLen.setReadOnly(true);
		txt_ShpQtyPtlLen.setReadOnly(true);
		txt_ShpRsltDateLen.setReadOnly(true);
		txt_ResultFlagLength.setReadOnly(true);
		txt_UseByDateLength.setReadOnly(true);

		Connection conn = null;
		try
		{
			//#CM690709
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter initParam = new DefineDataParameter();
			initParam.setSelectDefineReportData(
				DefineDataParameter.SELECTDEFINEREPORTDATA_SHIPPING);
			WmsScheduler schedule = new DefineReportDataSCH();
			DefineDataParameter[] param = (DefineDataParameter[]) schedule.query(conn, initParam);

			if (param != null)
			{
				//#CM690710
				// Planned Shipping Date: Enabled
				chk_CommonUseShpPlanDate.setChecked(param[0].getValid_PlanDate());
				//#CM690711
				// Planned Shipping Date: Length
				txt_ShpPlanDateLen.setText(param[0].getFigure_PlanDate());
				//#CM690712
				// Planned Shipping Date: Max Length
				lbl_JavaSetShpPlanDate.setText(param[0].getMaxFigure_PlanDate());
				//#CM690713
				// Planned Shipping Date: Position
				txt_ShpPlanDatePst.setText(param[0].getPosition_PlanDate());
				
				//#CM690714
				// Order Date: Enabled
				chk_CommonUseOdd.setChecked(param[0].getValid_OrderingDate());
				//#CM690715
				// Order Date: Length
				txt_OdrgDateLen.setText(param[0].getFigure_OrderingDate());
				//#CM690716
				// Order Date: Max Length
				lbl_JavaSetOdd.setText(param[0].getMaxFigure_OrderingDate());
				//#CM690717
				// Order Date: Position
				txt_OdrgDatePst.setText(param[0].getPosition_OrderingDate());
				
				//#CM690718
				// Consignor Code: Enabled
				chk_CommonUseCnsgnrCd.setChecked(param[0].getValid_ConsignorCode());
				//#CM690719
				// Consignor Code: Length
				txt_CnsgnrCdLen.setText(param[0].getFigure_ConsignorCode());
				//#CM690720
				// Consignor Code: Max Length
				lbl_JavaSetCnsgnrCd.setText(param[0].getMaxFigure_ConsignorCode());
				//#CM690721
				// Consignor Code: Position
				txt_CnsgnrCdPst.setText(param[0].getPosition_ConsignorCode());
				
				//#CM690722
				// Consignor Name: Enabled
				chk_CommonUseCnsgnrNm.setChecked(param[0].getValid_ConsignorName());
				//#CM690723
				// Consignor Name: Length
				txt_CnsgnrNmLen.setText(param[0].getFigure_ConsignorName());
				//#CM690724
				// Consignor Name: Max Length
				lbl_JavaSetCnsgnrNm.setText(param[0].getMaxFigure_ConsignorName());
				//#CM690725
				// Consignor Name: Position
				txt_CnsgnrNmPst.setText(param[0].getPosition_ConsignorName());

				//#CM690726
				// Customer Code: Enabled
				chk_CommonUseCustCd.setChecked(param[0].getValid_CustomerCode());
				//#CM690727
				// Customer Code: Length
				txt_CustCdLen.setText(param[0].getFigure_CustomerCode());
				//#CM690728
				// Customer Code: Max Length
				lbl_JavaSetCustCd.setText(param[0].getMaxFigure_CustomerCode());
				//#CM690729
				// Customer Code: Position
				txt_CustCdPst.setText(param[0].getPosition_CustomerCode());

				//#CM690730
				// Customer Name: Enabled
				chk_CommonUseCustNm.setChecked(param[0].getValid_CustomerName());
				//#CM690731
				// Customer Name: Length
				txt_CustNmLen.setText(param[0].getFigure_CustomerName());
				//#CM690732
				// Customer Name: Max Length
				lbl_JavaSetCustNm.setText(param[0].getMaxFigure_CustomerName());
				//#CM690733
				// Customer Name: Position
				txt_CustNmPst.setText(param[0].getPosition_CustomerName());

				//#CM690734
				// Ticket No.: Enabled
				chk_CommonUseTktNo.setChecked(param[0].getValid_ShippingTicketNo());
				//#CM690735
				// Ticket No.: Length
				txt_TicketNoLength.setText(param[0].getFigure_ShippingTicketNo());
				//#CM690736
				// Ticket No.: Max Length
				lbl_JavaSetTktNo.setText(param[0].getMaxFigure_ShippingTicketNo());
				//#CM690737
				// Ticket No.: Position
				txt_TicketNoPosition.setText(param[0].getPosition_ShippingTicketNo());

				//#CM690738
				// Ticket Line No.: Enabled
				chk_CommonUseTktLineNo.setChecked(param[0].getValid_ShippingLineNo());
				//#CM690739
				// Ticket Line No.: Length
				txt_TktLineNoLen.setText(param[0].getFigure_ShippingLineNo());
				//#CM690740
				// Ticket Line No.: Max Length
				lbl_JavaSetTktLineNo.setText(param[0].getMaxFigure_ShippingLineNo());
				//#CM690741
				// Ticket Line No.: Position
				txt_TktLineNoPst.setText(param[0].getPosition_ShippingLineNo());

				//#CM690742
				// Item Code: Enabled
				chk_TktLineNoPstItemCo.setChecked(param[0].getValid_ItemCode());
				//#CM690743
				// Item Code: Length
				txt_ItemCodeLength.setText(param[0].getFigure_ItemCode());
				//#CM690744
				// Item Code: Max Length
				lbl_JavaSetItemCo.setText(param[0].getMaxFigure_ItemCode());
				//#CM690745
				// Item Code: Position
				txt_ItemCodePosition.setText(param[0].getPosition_ItemCode());

				//#CM690746
				// Bundle ITF: Enabled
				chk_CommonUseBdlItf.setChecked(param[0].getValid_BundleItf());
				//#CM690747
				// Bundle ITF: Length
				txt_BundleItfLength.setText(param[0].getFigure_BundleItf());
				//#CM690748
				// Bundle ITF: Max Length
				lbl_JavaSetBdlItf.setText(param[0].getMaxFigure_BundleItf());
				//#CM690749
				// Bundle ITF: Position
				txt_BundleItfPosition.setText(param[0].getPosition_BundleItf());

				//#CM690750
				// Case ITF: Enabled
				chk_CommonUseCaseItf.setChecked(param[0].getValid_Itf());
				//#CM690751
				// Case ITF: Length
				txt_CaseItfLength.setText(param[0].getFigure_Itf());
				//#CM690752
				// Case ITF: Max Length
				lbl_JavaSetCaseItf.setText(param[0].getMaxFigure_Itf());
				//#CM690753
				// Case ITF: Position
				txt_CaseItfPosition.setText(param[0].getPosition_Itf());

				//#CM690754
				// Packed qty per bundle: Enabled
				chk_CommonUseBdlEtr.setChecked(param[0].getValid_BundleEnteringQty());
				//#CM690755
				// Packed qty per bundle: Length
				txt_BdlEtrLen.setText(param[0].getFigure_BundleEnteringQty());
				//#CM690756
				// Packed qty per bundle: Max Length
				lbl_JavaSetBdlEtr.setText(param[0].getMaxFigure_BundleEnteringQty());
				//#CM690757
				// Packed qty per bundle: Position
				txt_BdlEtrPst.setText(param[0].getPosition_BundleEnteringQty());

				//#CM690758
				// Packed Qty per Case: Enabled
				chk_CommonUseCaseEtr.setChecked(param[0].getValid_EnteringQty());
				//#CM690759
				// Packed Qty per Case: Length
				txt_CaseEtrLen.setText(param[0].getFigure_EnteringQty());
				//#CM690760
				// Packed Qty per Case: Max Length
				lbl_JavaSetCaseEtr.setText(param[0].getMaxFigure_EnteringQty());
				//#CM690761
				// Packed Qty per Case: Position
				txt_CaseEtrPst.setText(param[0].getPosition_EnteringQty());

				//#CM690762
				// Item Name: Enabled
				chk_W_CommonUseItemNm.setChecked(param[0].getValid_ItemName());
				//#CM690763
				// Item Name: Length
				txt_ItemNameLength.setText(param[0].getFigure_ItemName());
				//#CM690764
				// Item Name: Max Length
				lbl_JavaSetItemNm.setText(param[0].getMaxFigure_ItemName());
				//#CM690765
				// Item Name: Position
				txt_ItemNamePosition.setText(param[0].getPosition_ItemName());

				//#CM690766
				// Planned Shipping Qty (Total Bulk Qty): Enabled
				chk_CommonUseShpPlanPtl.setChecked(param[0].getValid_PlanQty());
				//#CM690767
				// Planned Shipping Qty (Total Bulk Qty): Length
				txt_ShpPlanQtyPtlLen.setText(param[0].getFigure_PlanQty());
				//#CM690768
				// Planned Shipping Qty (Total Bulk Qty): Max Length
				lbl_JavaSetShpPlanPtl.setText(param[0].getMaxFigure_PlanQty());
				//#CM690769
				// Planned Shipping Qty (Total Bulk Qty): Position
				txt_ShpPlanQtyPtlPst.setText(param[0].getPosition_PlanQty());

				//#CM690770
				// TC/DC division: Enabled
				chk_CommonUseTCDC.setChecked(param[0].getValid_TcDcFlag());
				//#CM690771
				// TC/DC division: Length
				txt_TCDCFlagLength.setText(param[0].getFigure_TcDcFlag());
				//#CM690772
				// TC/DC division: Max Length
				lbl_JavaSetTCDC.setText(param[0].getMaxFigure_TcDcFlag());
				//#CM690773
				// TC/DC division: Position
				txt_TCDCFlagPosition.setText(param[0].getPosition_TcDcFlag());

				//#CM690774
				// Supplier Code: Enabled
				chk_CommonUseSplCd.setChecked(param[0].getValid_SupplierCode());
				//#CM690775
				// Supplier Code: Length
				txt_SplCdLen.setText(param[0].getFigure_SupplierCode());
				//#CM690776
				// Supplier Code: Max Length
				lbl_JavaSetSplCd.setText(param[0].getMaxFigure_SupplierCode());
				//#CM690777
				// Supplier Code: Position
				txt_SplCdPst.setText(param[0].getPosition_SupplierCode());

				//#CM690778
				// Supplier Name: Enabled
				chk_CommonUseSplNm.setChecked(param[0].getValid_SupplierName());
				//#CM690779
				// Supplier Name: Length
				txt_SplNmLen.setText(param[0].getFigure_SupplierName());
				//#CM690780
				// Supplier Name: Max Length
				lbl_JavaSetSpl.setText(param[0].getMaxFigure_SupplierName());
				//#CM690781
				// Supplier Name: Position
				txt_SplNmPstNm.setText(param[0].getPosition_SupplierName());

				//#CM690782
				// Receiving Ticket No.: Enabled
				chk_CommonUseInstkTkt.setChecked(param[0].getValid_InstockTicketNo());
				//#CM690783
				// Receiving Ticket No.: Length
				txt_InstkTktNoLen.setText(param[0].getFigure_InstockTicketNo());
				//#CM690784
				// Receiving Ticket No.: Max Length
				lbl_JavaSetInstkTkt.setText(param[0].getMaxFigure_InstockTicketNo());
				//#CM690785
				// Receiving Ticket No.: Position
				txt_InstkTktNoPst.setText(param[0].getPosition_InstockTicketNo());

				//#CM690786
				// Receiving Ticket Line No.: Enabled
				chk_CommonUseInstkTktLine.setChecked(param[0].getValid_InstockLineNo());
				//#CM690787
				// Receiving Ticket Line No.: Length
				txt_InstkTktLineNoLen.setText(param[0].getFigure_InstockLineNo());
				//#CM690788
				// Receiving Ticket Line No.: Max Length
				lbl_JavaSetInstkTktLine.setText(param[0].getMaxFigure_InstockLineNo());
				//#CM690789
				// Receiving Ticket Line No.: Position
				txt_InstkTktLineNoPst.setText(param[0].getPosition_InstockLineNo());

				//#CM690790
				// Shipping Qty (Total Bulk Qty): Enabled
				chk_CommonUseShpPtl.setChecked(param[0].getValid_PieceResultQty());
				//#CM690791
				// Shipping Qty (Total Bulk Qty): Length
				txt_ShpQtyPtlLen.setText(param[0].getFigure_PieceResultQty());
				//#CM690792
				// Shipping Qty (Total Bulk Qty): Max Length
				lbl_JavaSetShpPtl.setText(param[0].getMaxFigure_PieceResultQty());
				//#CM690793
				// Shipping Qty (Total Bulk Qty): Position
				txt_ShpQtyPtlPst.setText(param[0].getPosition_PieceResultQty());

				//#CM690794
				// Shipping Result Date: Enabled
				chk_CommonUseShpRslt.setChecked(param[0].getValid_WorkDate());
				//#CM690795
				// Shipping Result Date: Length
				txt_ShpRsltDateLen.setText(param[0].getFigure_WorkDate());
				//#CM690796
				// Shipping Result Date: Max Length
				lbl_JavaSetShpRslt.setText(param[0].getMaxFigure_WorkDate());
				//#CM690797
				// Shipping Result Date: Position
				txt_ShpRsltDatePst.setText(param[0].getPosition_WorkDate());

				//#CM690798
				// Result division: Enabled
				chk_CommonUseRsltFlg.setChecked(param[0].getValid_ResultFlag());
				//#CM690799
				// Result division: Length
				txt_ResultFlagLength.setText(param[0].getFigure_ResultFlag());
				//#CM690800
				// Result division: Max Length
				lbl_JavaSetRsltFlg.setText(param[0].getMaxFigure_ResultFlag());
				//#CM690801
				// Result division: Position
				txt_ResultFlagPosition.setText(param[0].getPosition_ResultFlag());

				//#CM690802
				// Expiry Date: Enabled
				chk_CommonUseUseByDate.setChecked(param[0].getValid_UseByDate());
				//#CM690803
				// Expiry Date: Length
				txt_UseByDateLength.setText(param[0].getFigure_UseByDate());
				//#CM690804
				// Expiry Date: Max Length
				lbl_JavaSetUseByDate.setText(param[0].getMaxFigure_UseByDate());
				//#CM690805
				// Expiry Date: Position
				txt_UseByDatePosition.setText(param[0].getPosition_UseByDate());
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
				//#CM690806
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

	//#CM690807
	// Package methods -----------------------------------------------

	//#CM690808
	// Protected methods ---------------------------------------------

	//#CM690809
	// Private methods -----------------------------------------------
	//#CM690810
	/** 
	 * Allow this method to check wheter the position is duplicated or not in a sequential order.<BR>
	 * <BR>
	 * @return Return false if two or more data occupies a single position.
	 * @throws Exception Report all exceptions.
	 */
	private boolean checkRepeat() throws Exception
	{
		boolean repeatChkFlg = true;
		
		//#CM690811
		// Planned Shipping Date
		if (repeatChkFlg && chk_CommonUseShpPlanDate.getChecked())
		{
			repeatChkFlg = positionCheck(txt_ShpPlanDatePst);
		}

		//#CM690812
		// Order Date
		if (repeatChkFlg && chk_CommonUseOdd.getChecked())
		{
			repeatChkFlg = positionCheck(txt_OdrgDatePst);
		}

		//#CM690813
		// Consignor Code
		if (repeatChkFlg && chk_CommonUseCnsgnrCd.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CnsgnrCdPst);
		}
		
		//#CM690814
		// Consignor Name
		if (repeatChkFlg && chk_CommonUseCnsgnrNm.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CnsgnrNmPst);
		}
		
		//#CM690815
		// Customer Code
		if (repeatChkFlg && chk_CommonUseCustCd.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CustCdPst);
		}

		//#CM690816
		// Customer Name
		if (repeatChkFlg && chk_CommonUseCustNm.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CustNmPst);
		}

		//#CM690817
		// Ticket No.
		if (repeatChkFlg && chk_CommonUseTktNo.getChecked())
		{
			repeatChkFlg = positionCheck(txt_TicketNoPosition);
		}

		//#CM690818
		// Ticket Line No.
		if (repeatChkFlg && chk_CommonUseTktLineNo.getChecked())
		{
			repeatChkFlg = positionCheck(txt_TktLineNoPst);
		}

		//#CM690819
		// Item Code
		if (repeatChkFlg && chk_TktLineNoPstItemCo.getChecked())
		{
			repeatChkFlg = positionCheck(txt_ItemCodePosition);
		}

		//#CM690820
		// Bundle ITF
		if (repeatChkFlg && chk_CommonUseBdlItf.getChecked())
		{
			repeatChkFlg = positionCheck(txt_BundleItfPosition);
		}

		//#CM690821
		// Case ITF
		if (repeatChkFlg && chk_CommonUseCaseItf.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CaseItfPosition);
		}

		//#CM690822
		// Packed qty per bundle
		if (repeatChkFlg && chk_CommonUseBdlEtr.getChecked())
		{
			repeatChkFlg = positionCheck(txt_BdlEtrPst);
		}

		//#CM690823
		// Packed Qty per Case
		if (repeatChkFlg && chk_CommonUseCaseEtr.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CaseEtrPst);
		}

		//#CM690824
		// Item Name
		if (repeatChkFlg && chk_W_CommonUseItemNm.getChecked())
		{
			repeatChkFlg = positionCheck(txt_ItemNamePosition);
		}

		//#CM690825
		// Planned Shipping Qty (Total Bulk Qty)
		if (repeatChkFlg && chk_CommonUseShpPlanPtl.getChecked())
		{
			repeatChkFlg = positionCheck(txt_ShpPlanQtyPtlPst);
		}

		//#CM690826
		// TC/DC division
		if (repeatChkFlg && chk_CommonUseTCDC.getChecked())
		{
			repeatChkFlg = positionCheck(txt_TCDCFlagPosition);
		}

		//#CM690827
		// Supplier Code
		if (repeatChkFlg && chk_CommonUseSplCd.getChecked())
		{
			repeatChkFlg = positionCheck(txt_SplCdPst);
		}

		//#CM690828
		// Supplier Name
		if (repeatChkFlg && chk_CommonUseSplNm.getChecked())
		{
			repeatChkFlg = positionCheck(txt_SplNmPstNm);
		}

		//#CM690829
		// Receiving Ticket No.
		if (repeatChkFlg && chk_CommonUseInstkTkt.getChecked())
		{
			repeatChkFlg = positionCheck(txt_InstkTktNoPst);
		}

		//#CM690830
		// ReceivingTicket Line No.
		if (repeatChkFlg && chk_CommonUseInstkTktLine.getChecked())
		{
			repeatChkFlg = positionCheck(txt_InstkTktLineNoPst);
		}

		//#CM690831
		// Shipping Qty (Total Bulk Qty)
		if (repeatChkFlg && chk_CommonUseShpPtl.getChecked())
		{
			repeatChkFlg = positionCheck(txt_ShpQtyPtlPst);
		}

		//#CM690832
		// Shipping Result Date
		if (repeatChkFlg && chk_CommonUseShpRslt.getChecked())
		{
			repeatChkFlg = positionCheck(txt_ShpRsltDatePst);
		}
		
		//#CM690833
		// Result division
		if (repeatChkFlg && chk_CommonUseRsltFlg.getChecked())
		{
			repeatChkFlg = positionCheck(txt_ResultFlagPosition);
		}

		//#CM690834
		// Expiry Date
		if (repeatChkFlg && chk_CommonUseUseByDate.getChecked())
		{
			repeatChkFlg = positionCheck(txt_UseByDatePosition);
		}
		
		return repeatChkFlg;
		
	}

	//#CM690835
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

		//#CM690836
		// Planned Shipping Date: Position
		if (position.getText().equals(txt_ShpPlanDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690837
		// Order Date: Position
		if (position.getText().equals(txt_OdrgDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690838
		// Consignor Code: Position
		if (position.getText().equals(txt_CnsgnrCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690839
		// Consignor Name: Position
		if (position.getText().equals(txt_CnsgnrNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690840
		// Customer Code: Position
		if (position.getText().equals(txt_CustCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690841
		// Customer Name: Position
		if (position.getText().equals(txt_CustNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690842
		// Ticket No.: Position
		if (position.getText().equals(txt_TicketNoPosition.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690843
		// Ticket Line No.: Position
		if (position.getText().equals(txt_TktLineNoPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690844
		// Item Code: Position
		if (position.getText().equals(txt_ItemCodePosition.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690845
		// Bundle ITF: Position
		if (position.getText().equals(txt_BundleItfPosition.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690846
		// Case ITF: Position
		if (position.getText().equals(txt_CaseItfPosition.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690847
		// Packed qty per bundle: Position
		if (position.getText().equals(txt_BdlEtrPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690848
		// Packed Qty per Case: Position
		if (position.getText().equals(txt_CaseEtrPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690849
		// Item Name: Position
		if (position.getText().equals(txt_ItemNamePosition.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690850
		// Planned Shipping Qty (Total Bulk Qty): Position
		if (position.getText().equals(txt_ShpPlanQtyPtlPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690851
		// TC/DC division: Position
		if (position.getText().equals(txt_TCDCFlagPosition.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690852
		// Supplier Code: Position
		if (position.getText().equals(txt_SplCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690853
		// Supplier Name: Position
		if (position.getText().equals(txt_SplNmPstNm.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690854
		// Receiving Ticket No.: Position
		if (position.getText().equals(txt_InstkTktNoPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690855
		// Receiving Ticket Line No.: Position
		if (position.getText().equals(txt_InstkTktLineNoPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690856
		// Shipping Qty (Total Bulk Qty): Position
		if (position.getText().equals(txt_ShpQtyPtlPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690857
		// Shipping Result Date: Position
		if (position.getText().equals(txt_ShpRsltDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690858
		// Result division: Position
		if (position.getText().equals(txt_ResultFlagPosition.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM690859
		// Expiry Date: Position
		if (position.getText().equals(txt_UseByDatePosition.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}

		return true;
	}


	//#CM690860
	// Event handler methods -----------------------------------------
	//#CM690861
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

	//#CM690862
	/** 
	 * Clicking on Add button for field items set for reporting data (setting of Shipping Result field) invokes this.<BR>
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
		//#CM690863
		// Set the initial value of the focus to Planned Shipping Date Enabled.
		setFocus(chk_CommonUseShpPlanDate);

		boolean wCheck_flag = false;

		//#CM690864
		// Display error if no check is placed in all checkboxes.
		//#CM690865
		// Planned Shipping Date
		if (chk_CommonUseShpPlanDate.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690866
		// Order Date
		if (chk_CommonUseOdd.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690867
		// Consignor Code
		if (chk_CommonUseCnsgnrCd.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690868
		// Consignor Name
		if (chk_CommonUseCnsgnrNm.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690869
		// Customer Code
		if (chk_CommonUseCustCd.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690870
		// Customer Name
		if (chk_CommonUseCustNm.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690871
		// Ticket No.
		if (chk_CommonUseTktNo.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690872
		// Ticket Line No.
		if (chk_CommonUseTktLineNo.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690873
		// Item Code
		if (chk_TktLineNoPstItemCo.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690874
		// Bundle ITF
		if (chk_CommonUseBdlItf.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690875
		// Case ITF
		if (chk_CommonUseCaseItf.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690876
		// Packed qty per bundle
		if (chk_CommonUseBdlEtr.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690877
		// Packed Qty per Case
		if (chk_CommonUseCaseEtr.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690878
		// Item Name
		if (chk_W_CommonUseItemNm.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690879
		// Planned Shipping Qty (Total Bulk Qty)
		if (chk_CommonUseShpPlanPtl.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690880
		// TC/DC division
		if (chk_CommonUseTCDC.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690881
		// Supplier Code
		if (chk_CommonUseSplCd.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690882
		// Supplier Name
		if (chk_CommonUseSplNm.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690883
		// Receiving Ticket No.
		if (chk_CommonUseInstkTkt.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690884
		// ReceivingTicket Line No.
		if (chk_CommonUseInstkTktLine.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690885
		// Shipping Qty (Total Bulk Qty)
		if (chk_CommonUseShpPtl.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690886
		// Shipping Result Date
		if (chk_CommonUseShpRslt.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690887
		// Work division
		if (chk_CommonUseRsltFlg.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM690888
		// Expiry Date
		if (chk_CommonUseUseByDate.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}

		//#CM690889
		// Error Message
		if (!wCheck_flag)
		{
			//#CM690890
			// 6023174=Please select 1 or more items.
			message.setMsgResourceKey("6023174");
			return;
		}

		//#CM690891
		// Check for input.
		//#CM690892
		// Planned Shipping Date
		WmsCheckker.checkDefine(lbl_ShippingPlanDate, chk_CommonUseShpPlanDate, txt_ShpPlanDateLen, lbl_JavaSetShpPlanDate, txt_ShpPlanDatePst);
		//#CM690893
		// Order Date
		WmsCheckker.checkDefine(lbl_OrderDate, chk_CommonUseOdd, txt_OdrgDateLen, lbl_JavaSetOdd, txt_OdrgDatePst);
		//#CM690894
		// Consignor Code
		WmsCheckker.checkDefine(lbl_ConsignorCode, chk_CommonUseCnsgnrCd, txt_CnsgnrCdLen, lbl_JavaSetCnsgnrCd, txt_CnsgnrCdPst);		
		//#CM690895
		// Consignor Name
		WmsCheckker.checkDefine(lbl_ConsignorName, chk_CommonUseCnsgnrNm, txt_CnsgnrNmLen, lbl_JavaSetCnsgnrNm, txt_CnsgnrNmPst);
		//#CM690896
		// Customer Code
		WmsCheckker.checkDefine(lbl_CustomerCode, chk_CommonUseCustCd, txt_CustCdLen, lbl_JavaSetCustCd, txt_CustCdPst);
		//#CM690897
		// Customer Name
		WmsCheckker.checkDefine(lbl_CustomerName, chk_CommonUseCustNm, txt_CustNmLen, lbl_JavaSetCustNm, txt_CustNmPst);
		//#CM690898
		// Ticket No.
		WmsCheckker.checkDefine(lbl_TicketNo, chk_CommonUseTktNo, txt_TicketNoLength, lbl_JavaSetTktNo, txt_TicketNoPosition);
		//#CM690899
		// Ticket Line No.
		WmsCheckker.checkDefine(lbl_TicketLineNo, chk_CommonUseTktLineNo, txt_TktLineNoLen, lbl_JavaSetTktLineNo, txt_TktLineNoPst);
		//#CM690900
		// Item Code
		WmsCheckker.checkDefine(lbl_ItemCode, chk_TktLineNoPstItemCo, txt_ItemCodeLength, lbl_JavaSetItemCo, txt_ItemCodePosition);
		//#CM690901
		// Bundle ITF
		WmsCheckker.checkDefine(lbl_BundleItf, chk_CommonUseBdlItf, txt_BundleItfLength, lbl_JavaSetBdlItf, txt_BundleItfPosition);
		//#CM690902
		// Case ITF
		WmsCheckker.checkDefine(lbl_CaseItf, chk_CommonUseCaseItf, txt_CaseItfLength, lbl_JavaSetCaseItf, txt_CaseItfPosition);
		//#CM690903
		// Packed qty per bundle
		WmsCheckker.checkDefine(lbl_BundleEntering, chk_CommonUseBdlEtr, txt_BdlEtrLen, lbl_JavaSetBdlEtr, txt_BdlEtrPst);
		//#CM690904
		// Packed Qty per Case
		WmsCheckker.checkDefine(lbl_CaseEntering, chk_CommonUseCaseEtr, txt_CaseEtrLen, lbl_JavaSetCaseEtr, txt_CaseEtrPst);
		//#CM690905
		// Item Name
		WmsCheckker.checkDefine(lbl_ItemName, chk_W_CommonUseItemNm, txt_ItemNameLength, lbl_JavaSetItemNm, txt_ItemNamePosition);
		//#CM690906
		// Planned Shipping Qty (Total Bulk Qty)
		WmsCheckker.checkDefine(lbl_ShpPlanQtyPtl, chk_CommonUseShpPlanPtl, txt_ShpPlanQtyPtlLen, lbl_JavaSetShpPlanPtl, txt_ShpPlanQtyPtlPst);
		//#CM690907
		// TC/DC division
		WmsCheckker.checkDefine(lbl_TCDCFlag, chk_CommonUseTCDC, txt_TCDCFlagLength, lbl_JavaSetTCDC, txt_TCDCFlagPosition);
		//#CM690908
		// Supplier Code
		WmsCheckker.checkDefine(lbl_SupplierCode, chk_CommonUseSplCd, txt_SplCdLen, lbl_JavaSetSplCd, txt_SplCdPst);
		//#CM690909
		// Supplier Name
		WmsCheckker.checkDefine(lbl_SupplierName, chk_CommonUseSplNm, txt_SplNmLen, lbl_JavaSetSpl, txt_SplNmPstNm);
		//#CM690910
		// Receiving Ticket No.
		WmsCheckker.checkDefine(lbl_InstockTicketNo, chk_CommonUseInstkTkt, txt_InstkTktNoLen, lbl_JavaSetInstkTkt, txt_InstkTktNoPst);
		//#CM690911
		// ReceivingTicket Line No.
		WmsCheckker.checkDefine(lbl_InstkTktLineNo, chk_CommonUseInstkTktLine, txt_InstkTktLineNoLen, lbl_JavaSetInstkTktLine, txt_InstkTktLineNoPst);
		//#CM690912
		// Shipping Qty (Total Bulk Qty)
		WmsCheckker.checkDefine(lbl_ShpQtyPtl, chk_CommonUseShpPtl, txt_ShpQtyPtlLen, lbl_JavaSetShpPtl, txt_ShpQtyPtlPst);
		//#CM690913
		// Shipping Result Date
		WmsCheckker.checkDefine(lbl_ShippingResultDate, chk_CommonUseShpRslt, txt_ShpRsltDateLen, lbl_JavaSetShpRslt, txt_ShpRsltDatePst);
		//#CM690914
		// Result division
		WmsCheckker.checkDefine(lbl_ResultFlag, chk_CommonUseRsltFlg, txt_ResultFlagLength, lbl_JavaSetRsltFlg, txt_ResultFlagPosition);
		//#CM690915
		// Expiry Date
		WmsCheckker.checkDefine(lbl_UseByDate, chk_CommonUseUseByDate, txt_UseByDateLength, lbl_JavaSetUseByDate, txt_UseByDatePosition);

		//#CM690916
		// Check for duplicate Position.
		if (!checkRepeat())
		{
			//#CM690917
			// Set the Error Message.
			//#CM690918
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
			param[0].setSelectDefineReportData(DefineDataParameter.SELECTDEFINEREPORTDATA_SHIPPING);

			WmsScheduler schedule = new DefineReportDataSCH();

			//#CM690919
			// Set the value for the parameter.
			param[0].setSelectDefineReportData(DefineDataParameter.SELECTDEFINEREPORTDATA_SHIPPING);
			//#CM690920
			// Planned Shipping Date: Enabled
			param[0].setValid_PlanDate(chk_CommonUseShpPlanDate.getChecked());
			//#CM690921
			// Planned Shipping Date: Length
			param[0].setFigure_PlanDate(txt_ShpPlanDateLen.getText());
			//#CM690922
			// Planned Shipping Date: Max Length
			param[0].setMaxFigure_PlanDate(lbl_JavaSetShpPlanDate.getText());
			//#CM690923
			// Planned Shipping Date: Position
			param[0].setPosition_PlanDate(txt_ShpPlanDatePst.getText());
			//#CM690924
			// Order Date: Enabled
			param[0].setValid_OrderingDate(chk_CommonUseOdd.getChecked());
			//#CM690925
			// Order Date: Length
			param[0].setFigure_OrderingDate(txt_OdrgDateLen.getText());
			//#CM690926
			// Order Date: Max Length
			param[0].setMaxFigure_OrderingDate(lbl_JavaSetOdd.getText());
			//#CM690927
			// Order Date: Position
			param[0].setPosition_OrderingDate(txt_OdrgDatePst.getText());
			//#CM690928
			// Consignor Code: Enabled
			param[0].setValid_ConsignorCode(chk_CommonUseCnsgnrCd.getChecked());
			//#CM690929
			// Consignor Code: Length
			param[0].setFigure_ConsignorCode(txt_CnsgnrCdLen.getText());
			//#CM690930
			// Consignor Code: Max Length
			param[0].setMaxFigure_ConsignorCode(lbl_JavaSetCnsgnrCd.getText());
			//#CM690931
			// Consignor Code: Position
			param[0].setPosition_ConsignorCode(txt_CnsgnrCdPst.getText());
			//#CM690932
			// Consignor Name: Enabled
			param[0].setValid_ConsignorName(chk_CommonUseCnsgnrNm.getChecked());
			//#CM690933
			// Consignor Name: Length
			param[0].setFigure_ConsignorName(txt_CnsgnrNmLen.getText());
			//#CM690934
			// Consignor Name: Max Length
			param[0].setMaxFigure_ConsignorName(lbl_JavaSetCnsgnrNm.getText());
			//#CM690935
			// Consignor Name: Position
			param[0].setPosition_ConsignorName(txt_CnsgnrNmPst.getText());
			//#CM690936
			// Customer Code: Enabled
			param[0].setValid_CustomerCode(chk_CommonUseCustCd.getChecked());
			//#CM690937
			// Customer Code: Length
			param[0].setFigure_CustomerCode(txt_CustCdLen.getText());
			//#CM690938
			// Customer Code: Max Length
			param[0].setMaxFigure_CustomerCode(lbl_JavaSetCustCd.getText());
			//#CM690939
			// Customer Code: Position
			param[0].setPosition_CustomerCode(txt_CustCdPst.getText());
			//#CM690940
			// Customer Name: Enabled
			param[0].setValid_CustomerName(chk_CommonUseCustNm.getChecked());
			//#CM690941
			// Customer Name: Length
			param[0].setFigure_CustomerName(txt_CustNmLen.getText());
			//#CM690942
			// Customer Name: Max Length
			param[0].setMaxFigure_CustomerName(lbl_JavaSetCustNm.getText());
			//#CM690943
			// Customer Name: Position
			param[0].setPosition_CustomerName(txt_CustNmPst.getText());
			//#CM690944
			// Ticket No.: Enabled
			param[0].setValid_ShippingTicketNo(chk_CommonUseTktNo.getChecked());
			//#CM690945
			// Ticket No.: Length
			param[0].setFigure_ShippingTicketNo(txt_TicketNoLength.getText());
			//#CM690946
			// Ticket No.: Max Length
			param[0].setMaxFigure_ShippingTicketNo(lbl_JavaSetTktNo.getText());
			//#CM690947
			// Ticket No.: Position
			param[0].setPosition_ShippingTicketNo(txt_TicketNoPosition.getText());
			//#CM690948
			// Ticket Line No.: Enabled
			param[0].setValid_ShippingLineNo(chk_CommonUseTktLineNo.getChecked());
			//#CM690949
			// Ticket Line No.: Length
			param[0].setFigure_ShippingLineNo(txt_TktLineNoLen.getText());
			//#CM690950
			// Ticket Line No.: Max Length
			param[0].setMaxFigure_ShippingLineNo(lbl_JavaSetTktLineNo.getText());
			//#CM690951
			// Ticket Line No.: Position
			param[0].setPosition_ShippingLineNo(txt_TktLineNoPst.getText());
			//#CM690952
			// Item Code: Enabled
			param[0].setValid_ItemCode(chk_TktLineNoPstItemCo.getChecked());
			//#CM690953
			// Item Code: Length
			param[0].setFigure_ItemCode(txt_ItemCodeLength.getText());
			//#CM690954
			// Item Code: Max Length
			param[0].setMaxFigure_ItemCode(lbl_JavaSetItemCo.getText());
			//#CM690955
			// Item Code: Position
			param[0].setPosition_ItemCode(txt_ItemCodePosition.getText());
			//#CM690956
			// Bundle ITF: Enabled
			param[0].setValid_BundleItf(chk_CommonUseBdlItf.getChecked());
			//#CM690957
			// Bundle ITF: Length
			param[0].setFigure_BundleItf(txt_BundleItfLength.getText());
			//#CM690958
			// Bundle ITF: Max Length
			param[0].setMaxFigure_BundleItf(lbl_JavaSetBdlItf.getText());
			//#CM690959
			// Bundle ITF: Position
			param[0].setPosition_BundleItf(txt_BundleItfPosition.getText());
			//#CM690960
			// Case ITF: Enabled
			param[0].setValid_Itf(chk_CommonUseCaseItf.getChecked());
			//#CM690961
			// Case ITF: Length
			param[0].setFigure_Itf(txt_CaseItfLength.getText());
			//#CM690962
			// Case ITF: Max Length
			param[0].setMaxFigure_Itf(lbl_JavaSetCaseItf.getText());
			//#CM690963
			// Case ITF: Position
			param[0].setPosition_Itf(txt_CaseItfPosition.getText());
			//#CM690964
			// Packed qty per bundle: Enabled
			param[0].setValid_BundleEnteringQty(chk_CommonUseBdlEtr.getChecked());
			//#CM690965
			// Packed qty per bundle: Length
			param[0].setFigure_BundleEnteringQty(txt_BdlEtrLen.getText());
			//#CM690966
			// Packed qty per bundle: Max Length
			param[0].setMaxFigure_BundleEnteringQty(lbl_JavaSetBdlEtr.getText());
			//#CM690967
			// Packed qty per bundle: Position
			param[0].setPosition_BundleEnteringQty(txt_BdlEtrPst.getText());
			//#CM690968
			// Packed Qty per Case: Enabled
			param[0].setValid_EnteringQty(chk_CommonUseCaseEtr.getChecked());
			//#CM690969
			// Packed Qty per Case: Length
			param[0].setFigure_EnteringQty(txt_CaseEtrLen.getText());
			//#CM690970
			// Packed Qty per Case: Max Length
			param[0].setMaxFigure_EnteringQty(lbl_JavaSetCaseEtr.getText());
			//#CM690971
			// Packed Qty per Case: Position
			param[0].setPosition_EnteringQty(txt_CaseEtrPst.getText());
			//#CM690972
			// Item Name: Enabled
			param[0].setValid_ItemName(chk_W_CommonUseItemNm.getChecked());
			//#CM690973
			// Item Name: Length
			param[0].setFigure_ItemName(txt_ItemNameLength.getText());
			//#CM690974
			// Item Name: Max Length
			param[0].setMaxFigure_ItemName(lbl_JavaSetItemNm.getText());
			//#CM690975
			// Item Name: Position
			param[0].setPosition_ItemName(txt_ItemNamePosition.getText());
			//#CM690976
			// Planned Shipping Qty (Total Bulk Qty): Enabled
			param[0].setValid_PlanQty(chk_CommonUseShpPlanPtl.getChecked());
			//#CM690977
			// Planned Shipping Qty (Total Bulk Qty): Length
			param[0].setFigure_PlanQty(txt_ShpPlanQtyPtlLen.getText());
			//#CM690978
			// Planned Shipping Qty (Total Bulk Qty): Max Length
			param[0].setMaxFigure_PlanQty(lbl_JavaSetShpPlanPtl.getText());
			//#CM690979
			// Planned Shipping Qty (Total Bulk Qty): Position
			param[0].setPosition_PlanQty(txt_ShpPlanQtyPtlPst.getText());
			//#CM690980
			// TC/DC division: Enabled
			param[0].setValid_TcDcFlag(chk_CommonUseTCDC.getChecked());
			//#CM690981
			// TC/DC division: Length
			param[0].setFigure_TcDcFlag(txt_TCDCFlagLength.getText());
			//#CM690982
			// TC/DC division: Max Length
			param[0].setMaxFigure_TcDcFlag(lbl_JavaSetTCDC.getText());
			//#CM690983
			// TC/DC division: Position
			param[0].setPosition_TcDcFlag(txt_TCDCFlagPosition.getText());
			//#CM690984
			// Supplier Code: Enabled
			param[0].setValid_SupplierCode(chk_CommonUseSplCd.getChecked());
			//#CM690985
			// Supplier Code: Length
			param[0].setFigure_SupplierCode(txt_SplCdLen.getText());
			//#CM690986
			// Supplier Code: Max Length
			param[0].setMaxFigure_SupplierCode(lbl_JavaSetSplCd.getText());
			//#CM690987
			// Supplier Code: Position
			param[0].setPosition_SupplierCode(txt_SplCdPst.getText());
			//#CM690988
			// Supplier Name: Enabled
			param[0].setValid_SupplierName(chk_CommonUseSplNm.getChecked());
			//#CM690989
			// Supplier Name: Length
			param[0].setFigure_SupplierName(txt_SplNmLen.getText());
			//#CM690990
			// Supplier Name: Max Length
			param[0].setMaxFigure_SupplierName(lbl_JavaSetSpl.getText());
			//#CM690991
			// Supplier Name: Position
			param[0].setPosition_SupplierName(txt_SplNmPstNm.getText());
			//#CM690992
			// Receiving Ticket No.: Enabled
			param[0].setValid_InstockTicketNo(chk_CommonUseInstkTkt.getChecked());
			//#CM690993
			// Receiving Ticket No.: Length
			param[0].setFigure_InstockTicketNo(txt_InstkTktNoLen.getText());
			//#CM690994
			// Receiving Ticket No.: Max Length
			param[0].setMaxFigure_InstockTicketNo(lbl_JavaSetInstkTkt.getText());
			//#CM690995
			// Receiving Ticket No.: Position
			param[0].setPosition_InstockTicketNo(txt_InstkTktNoPst.getText());
			//#CM690996
			// Receiving Ticket Line No.: Enabled
			param[0].setValid_InstockLineNo(chk_CommonUseInstkTktLine.getChecked());
			//#CM690997
			// Receiving Ticket Line No.: Length
			param[0].setFigure_InstockLineNo(txt_InstkTktLineNoLen.getText());
			//#CM690998
			// Receiving Ticket Line No.: Max Length
			param[0].setMaxFigure_InstockLineNo(lbl_JavaSetInstkTktLine.getText());
			//#CM690999
			// Receiving Ticket Line No.: Position
			param[0].setPosition_InstockLineNo(txt_InstkTktLineNoPst.getText());
			//#CM691000
			// Shipping Qty (Total Bulk Qty): Enabled
			param[0].setValid_PieceResultQty(chk_CommonUseShpPtl.getChecked());
			//#CM691001
			// Shipping Qty (Total Bulk Qty): Length
			param[0].setFigure_PieceResultQty(txt_ShpQtyPtlLen.getText());
			//#CM691002
			// Shipping Qty (Total Bulk Qty): Max Length
			param[0].setMaxFigure_PieceResultQty(lbl_JavaSetShpPtl.getText());
			//#CM691003
			// Shipping Qty (Total Bulk Qty): Position
			param[0].setPosition_PieceResultQty(txt_ShpQtyPtlPst.getText());
			//#CM691004
			// Shipping Result Date: Enabled
			param[0].setValid_WorkDate(chk_CommonUseShpRslt.getChecked());
			//#CM691005
			// Shipping Result Date: Length
			param[0].setFigure_WorkDate(txt_ShpRsltDateLen.getText());
			//#CM691006
			// Shipping Result Date: Max Length
			param[0].setMaxFigure_WorkDate(lbl_JavaSetShpRslt.getText());
			//#CM691007
			// Shipping Result Date: Position
			param[0].setPosition_WorkDate(txt_ShpRsltDatePst.getText());
			//#CM691008
			// Result division: Enabled
			param[0].setValid_ResultFlag(chk_CommonUseRsltFlg.getChecked());
			//#CM691009
			// Result division: Length
			param[0].setFigure_ResultFlag(txt_ResultFlagLength.getText());
			//#CM691010
			// Result division: Max Length
			param[0].setMaxFigure_ResultFlag(lbl_JavaSetRsltFlg.getText());
			//#CM691011
			// Result division: Position
			param[0].setPosition_ResultFlag(txt_ResultFlagPosition.getText());
			//#CM691012
			// Expiry Date: Enabled
			param[0].setValid_UseByDate(chk_CommonUseUseByDate.getChecked());
			//#CM691013
			// Expiry Date: Length
			param[0].setFigure_UseByDate(txt_UseByDateLength.getText());
			//#CM691014
			// Expiry Date: Max Length
			param[0].setMaxFigure_UseByDate(lbl_JavaSetUseByDate.getText());
			//#CM691015
			// Expiry Date: Position
			param[0].setPosition_UseByDate(txt_UseByDatePosition.getText());

			//#CM691016
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
				//#CM691017
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
				//#CM691018
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

	//#CM691019
	/** 
	 * Clicking on Clear button for field items set for reporting data (setting of Shipping Result field) invokes this.<BR>
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
		//#CM691020
		// Set the initial value of the focus to Planned Shipping Date Length.
		setFocus(chk_CommonUseShpPlanDate);

		Connection conn = null;
		try
		{
			//#CM691021
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter initParam = new DefineDataParameter();
			initParam.setSelectDefineReportData(
				DefineDataParameter.SELECTDEFINEREPORTDATA_SHIPPING);
			WmsScheduler schedule = new DefineReportDataSCH();
			DefineDataParameter[] param = (DefineDataParameter[]) schedule.query(conn, initParam);

			if (param != null)
			{
				//#CM691022
				// Planned Shipping Date: Enabled
				chk_CommonUseShpPlanDate.setChecked(param[0].getValid_PlanDate());
				//#CM691023
				// Planned Shipping Date: Length
				txt_ShpPlanDateLen.setText(param[0].getFigure_PlanDate());
				//#CM691024
				// Planned Shipping Date: Max Length
				lbl_JavaSetShpPlanDate.setText(param[0].getMaxFigure_PlanDate());
				//#CM691025
				// Planned Shipping Date: Position
				txt_ShpPlanDatePst.setText(param[0].getPosition_PlanDate());
				//#CM691026
				// Order Date: Enabled
				chk_CommonUseOdd.setChecked(param[0].getValid_OrderingDate());
				//#CM691027
				// Order Date: Length
				txt_OdrgDateLen.setText(param[0].getFigure_OrderingDate());
				//#CM691028
				// Order Date: Max Length
				lbl_JavaSetOdd.setText(param[0].getMaxFigure_OrderingDate());
				//#CM691029
				// Order Date: Position
				txt_OdrgDatePst.setText(param[0].getPosition_OrderingDate());
				//#CM691030
				// Consignor Code: Enabled
				chk_CommonUseCnsgnrCd.setChecked(param[0].getValid_ConsignorCode());
				//#CM691031
				// Consignor Code: Length
				txt_CnsgnrCdLen.setText(param[0].getFigure_ConsignorCode());
				//#CM691032
				// Consignor Code: Max Length
				lbl_JavaSetCnsgnrCd.setText(param[0].getMaxFigure_ConsignorCode());
				//#CM691033
				// Consignor Code: Position
				txt_CnsgnrCdPst.setText(param[0].getPosition_ConsignorCode());
				//#CM691034
				// Consignor Name: Enabled
				chk_CommonUseCnsgnrNm.setChecked(param[0].getValid_ConsignorName());
				//#CM691035
				// Consignor Name: Length
				txt_CnsgnrNmLen.setText(param[0].getFigure_ConsignorName());
				//#CM691036
				// Consignor Name: Max Length
				lbl_JavaSetCnsgnrNm.setText(param[0].getMaxFigure_ConsignorName());
				//#CM691037
				// Consignor Name: Position
				txt_CnsgnrNmPst.setText(param[0].getPosition_ConsignorName());
				//#CM691038
				// Customer Code: Enabled
				chk_CommonUseCustCd.setChecked(param[0].getValid_CustomerCode());
				//#CM691039
				// Customer Code: Length
				txt_CustCdLen.setText(param[0].getFigure_CustomerCode());
				//#CM691040
				// Customer Code: Max Length
				lbl_JavaSetCustCd.setText(param[0].getMaxFigure_CustomerCode());
				//#CM691041
				// Customer Code: Position
				txt_CustCdPst.setText(param[0].getPosition_CustomerCode());
				//#CM691042
				// Customer Name: Enabled
				chk_CommonUseCustNm.setChecked(param[0].getValid_CustomerName());
				//#CM691043
				// Customer Name: Length
				txt_CustNmLen.setText(param[0].getFigure_CustomerName());
				//#CM691044
				// Customer Name: Max Length
				lbl_JavaSetCustNm.setText(param[0].getMaxFigure_CustomerName());
				//#CM691045
				// Customer Name: Position
				txt_CustNmPst.setText(param[0].getPosition_CustomerName());
				//#CM691046
				// Ticket No.: Enabled
				chk_CommonUseTktNo.setChecked(param[0].getValid_ShippingTicketNo());
				//#CM691047
				// Ticket No.: Length
				txt_TicketNoLength.setText(param[0].getFigure_ShippingTicketNo());
				//#CM691048
				// Ticket No.: Max Length
				lbl_JavaSetTktNo.setText(param[0].getMaxFigure_ShippingTicketNo());
				//#CM691049
				// Ticket No.: Position
				txt_TicketNoPosition.setText(param[0].getPosition_ShippingTicketNo());
				//#CM691050
				// Ticket Line No.: Enabled
				chk_CommonUseTktLineNo.setChecked(param[0].getValid_ShippingLineNo());
				//#CM691051
				// Ticket Line No.: Length
				txt_TktLineNoLen.setText(param[0].getFigure_ShippingLineNo());
				//#CM691052
				// Ticket Line No.: Max Length
				lbl_JavaSetTktLineNo.setText(param[0].getMaxFigure_ShippingLineNo());
				//#CM691053
				// Ticket Line No.: Position
				txt_TktLineNoPst.setText(param[0].getPosition_ShippingLineNo());
				//#CM691054
				// Item Code: Enabled
				chk_TktLineNoPstItemCo.setChecked(param[0].getValid_ItemCode());
				//#CM691055
				// Item Code: Length
				txt_ItemCodeLength.setText(param[0].getFigure_ItemCode());
				//#CM691056
				// Item Code: Max Length
				lbl_JavaSetItemCo.setText(param[0].getMaxFigure_ItemCode());
				//#CM691057
				// Item Code: Position
				txt_ItemCodePosition.setText(param[0].getPosition_ItemCode());
				//#CM691058
				// Bundle ITF: Enabled
				chk_CommonUseBdlItf.setChecked(param[0].getValid_BundleItf());
				//#CM691059
				// Bundle ITF: Length
				txt_BundleItfLength.setText(param[0].getFigure_BundleItf());
				//#CM691060
				// Bundle ITF: Max Length
				lbl_JavaSetBdlItf.setText(param[0].getMaxFigure_BundleItf());
				//#CM691061
				// Bundle ITF: Position
				txt_BundleItfPosition.setText(param[0].getPosition_BundleItf());
				//#CM691062
				// Case ITF: Enabled
				chk_CommonUseCaseItf.setChecked(param[0].getValid_Itf());
				//#CM691063
				// Case ITF: Length
				txt_CaseItfLength.setText(param[0].getFigure_Itf());
				//#CM691064
				// Case ITF: Max Length
				lbl_JavaSetCaseItf.setText(param[0].getMaxFigure_Itf());
				//#CM691065
				// Case ITF: Position
				txt_CaseItfPosition.setText(param[0].getPosition_Itf());
				//#CM691066
				// Packed qty per bundle: Enabled
				chk_CommonUseBdlEtr.setChecked(param[0].getValid_BundleEnteringQty());
				//#CM691067
				// Packed qty per bundle: Length
				txt_BdlEtrLen.setText(param[0].getFigure_BundleEnteringQty());
				//#CM691068
				// Packed qty per bundle: Max Length
				lbl_JavaSetBdlEtr.setText(param[0].getMaxFigure_BundleEnteringQty());
				//#CM691069
				// Packed qty per bundle: Position
				txt_BdlEtrPst.setText(param[0].getPosition_BundleEnteringQty());
				//#CM691070
				// Packed Qty per Case: Enabled
				chk_CommonUseCaseEtr.setChecked(param[0].getValid_EnteringQty());
				//#CM691071
				// Packed Qty per Case: Length
				txt_CaseEtrLen.setText(param[0].getFigure_EnteringQty());
				//#CM691072
				// Packed Qty per Case: Max Length
				lbl_JavaSetCaseEtr.setText(param[0].getMaxFigure_EnteringQty());
				//#CM691073
				// Packed Qty per Case: Position
				txt_CaseEtrPst.setText(param[0].getPosition_EnteringQty());
				//#CM691074
				// Item Name: Enabled
				chk_W_CommonUseItemNm.setChecked(param[0].getValid_ItemName());
				//#CM691075
				// Item Name: Length
				txt_ItemNameLength.setText(param[0].getFigure_ItemName());
				//#CM691076
				// Item Name: Max Length
				lbl_JavaSetItemNm.setText(param[0].getMaxFigure_ItemName());
				//#CM691077
				// Item Name: Position
				txt_ItemNamePosition.setText(param[0].getPosition_ItemName());
				//#CM691078
				// Planned Shipping Qty (Total Bulk Qty): Enabled
				chk_CommonUseShpPlanPtl.setChecked(param[0].getValid_PlanQty());
				//#CM691079
				// Planned Shipping Qty (Total Bulk Qty): Length
				txt_ShpPlanQtyPtlLen.setText(param[0].getFigure_PlanQty());
				//#CM691080
				// Planned Shipping Qty (Total Bulk Qty): Max Length
				lbl_JavaSetShpPlanPtl.setText(param[0].getMaxFigure_PlanQty());
				//#CM691081
				// Planned Shipping Qty (Total Bulk Qty): Position
				txt_ShpPlanQtyPtlPst.setText(param[0].getPosition_PlanQty());
				//#CM691082
				// TC/DC division: Enabled
				chk_CommonUseTCDC.setChecked(param[0].getValid_TcDcFlag());
				//#CM691083
				// TC/DC division: Length
				txt_TCDCFlagLength.setText(param[0].getFigure_TcDcFlag());
				//#CM691084
				// TC/DC division: Max Length
				lbl_JavaSetTCDC.setText(param[0].getMaxFigure_TcDcFlag());
				//#CM691085
				// TC/DC division: Position
				txt_TCDCFlagPosition.setText(param[0].getPosition_TcDcFlag());
				//#CM691086
				// Supplier Code: Enabled
				chk_CommonUseSplCd.setChecked(param[0].getValid_SupplierCode());
				//#CM691087
				// Supplier Code: Length
				txt_SplCdLen.setText(param[0].getFigure_SupplierCode());
				//#CM691088
				// Supplier Code: Max Length
				lbl_JavaSetSplCd.setText(param[0].getMaxFigure_SupplierCode());
				//#CM691089
				// Supplier Code: Position
				txt_SplCdPst.setText(param[0].getPosition_SupplierCode());
				//#CM691090
				// Supplier Name: Enabled
				chk_CommonUseSplNm.setChecked(param[0].getValid_SupplierName());
				//#CM691091
				// Supplier Name: Length
				txt_SplNmLen.setText(param[0].getFigure_SupplierName());
				//#CM691092
				// Supplier Name: Max Length
				lbl_JavaSetSpl.setText(param[0].getMaxFigure_SupplierName());
				//#CM691093
				// Supplier Name: Position
				txt_SplNmPstNm.setText(param[0].getPosition_SupplierName());
				//#CM691094
				// Receiving Ticket No.: Enabled
				chk_CommonUseInstkTkt.setChecked(param[0].getValid_InstockTicketNo());
				//#CM691095
				// Receiving Ticket No.: Length
				txt_InstkTktNoLen.setText(param[0].getFigure_InstockTicketNo());
				//#CM691096
				// Receiving Ticket No.: Max Length
				lbl_JavaSetInstkTkt.setText(param[0].getMaxFigure_InstockTicketNo());
				//#CM691097
				// Receiving Ticket No.: Position
				txt_InstkTktNoPst.setText(param[0].getPosition_InstockTicketNo());
				//#CM691098
				// Receiving Ticket Line No.: Enabled
				chk_CommonUseInstkTktLine.setChecked(param[0].getValid_InstockLineNo());
				//#CM691099
				// Receiving Ticket Line No.: Length
				txt_InstkTktLineNoLen.setText(param[0].getFigure_InstockLineNo());
				//#CM691100
				// Receiving Ticket Line No.: Max Length
				lbl_JavaSetInstkTktLine.setText(param[0].getMaxFigure_InstockLineNo());
				//#CM691101
				// Receiving Ticket Line No.: Position
				txt_InstkTktLineNoPst.setText(param[0].getPosition_InstockLineNo());
				//#CM691102
				// Shipping Qty (Total Bulk Qty): Enabled
				chk_CommonUseShpPtl.setChecked(param[0].getValid_PieceResultQty());
				//#CM691103
				// Shipping Qty (Total Bulk Qty): Length
				txt_ShpQtyPtlLen.setText(param[0].getFigure_PieceResultQty());
				//#CM691104
				// Shipping Qty (Total Bulk Qty): Max Length
				lbl_JavaSetShpPtl.setText(param[0].getMaxFigure_PieceResultQty());
				//#CM691105
				// Shipping Qty (Total Bulk Qty): Position
				txt_ShpQtyPtlPst.setText(param[0].getPosition_PieceResultQty());
				//#CM691106
				// Shipping Result Date: Enabled
				chk_CommonUseShpRslt.setChecked(param[0].getValid_WorkDate());
				//#CM691107
				// Shipping Result Date: Length
				txt_ShpRsltDateLen.setText(param[0].getFigure_WorkDate());
				//#CM691108
				// Shipping Result Date: Max Length
				lbl_JavaSetShpRslt.setText(param[0].getMaxFigure_WorkDate());
				//#CM691109
				// Shipping Result Date: Position
				txt_ShpRsltDatePst.setText(param[0].getPosition_WorkDate());
				//#CM691110
				// Result division: Enabled
				chk_CommonUseRsltFlg.setChecked(param[0].getValid_ResultFlag());
				//#CM691111
				// Result division: Length
				txt_ResultFlagLength.setText(param[0].getFigure_ResultFlag());
				//#CM691112
				// Result division: Max Length
				lbl_JavaSetRsltFlg.setText(param[0].getMaxFigure_ResultFlag());
				//#CM691113
				// Result division: Position
				txt_ResultFlagPosition.setText(param[0].getPosition_ResultFlag());
				//#CM691114
				// Expiry Date: Enabled
				chk_CommonUseUseByDate.setChecked(param[0].getValid_UseByDate());
				//#CM691115
				// Expiry Date: Length
				txt_UseByDateLength.setText(param[0].getFigure_UseByDate());
				//#CM691116
				// Expiry Date: Max Length
				lbl_JavaSetUseByDate.setText(param[0].getMaxFigure_UseByDate());
				//#CM691117
				// Expiry Date: Position
				txt_UseByDatePosition.setText(param[0].getPosition_UseByDate());
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
				//#CM691118
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

	//#CM691119
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
		//#CM691120
		// Shift to the original screen.
		forward("/system/definereportdata/DefineReportData.do");
	}

}
//#CM691121
//end of class
