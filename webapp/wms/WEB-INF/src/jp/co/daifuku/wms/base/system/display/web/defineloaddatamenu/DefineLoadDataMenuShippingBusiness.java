// $Id: DefineLoadDataMenuShippingBusiness.java,v 1.2 2006/11/13 08:18:26 suresh Exp $

//#CM688017
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.defineloaddatamenu;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.system.schedule.DefineDataParameter;
import jp.co.daifuku.wms.base.system.schedule.DefineLoadDataMenuSCH;

//#CM688018
/**
 * Designer : K.Mukai <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * Allow this class to provide a screen to set a field item for loading data (to set the Shipping Plan field). <BR>
 * Set the contents input via input area for the parameter and pass it to the schedule. Add the setting of Shipping Plan field. <BR>
 * Receive the result from the schedule. Receive true if the process completed normally. <BR>
 * Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 * As the result of the schedule, output the message obtained from the schedule to the screen. <BR>
 *
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * Process by clicking "Add" button(<CODE>btn_Submit_Click()</CODE>method)<BR>
 * <BR>
 * <DIR>
 *   Add the data using the info in Input area. <BR>
 * <BR>
 *   [Parameter] *Mandatory Input<BR>
 *   Length, Max Length, and Position of each field item<BR>
 * <BR>
 * <DIR>
 *   Planned Shipping Date	(mandatory)*<BR>
 *   Customer Code (mandatory)*<BR>
 *   Ticket No.	(mandatory)*<BR>
 *   Ticket Line No.	(mandatory)*<BR>
 *   Item Code	(mandatory)*<BR>
 *   Packed Qty per Case	(mandatory)*<BR>
 *   Shipping Qty (Total Bulk Qty) (mandatory)*<BR>
 *   TC/DC division	(mandatory)*<BR>
 *   Supplier Code (mandatory)*<BR>
 *   Supplier Name	(mandatory)*<BR>
 *   Receiving Ticket No. (mandatory)*<BR>
 *   Receiving Ticket Line No. (mandatory)*<BR>
 * </DIR>
 * <BR>
 *   [Parameter]+ Require to input if the parameter is ticked to Enabled <BR>
 *   Length, Max Length, and Position of each field item<BR>
 * <BR>
 * <DIR>
 *   Order Date+<BR>
 *   Consignor Code +<BR>
 *   Consignor Name+<BR>
 *   Customer Name+<BR>
 *   Bundle ITF+<BR>
 *   Case ITF+<BR>
 *   Packed qty per bundle+<BR>
 *   Item Name+<BR>
 *   (Note))Supplier Code+<BR>
 *   Supplier Name+<BR>
 *   (Note))Receiving Ticket No.+<BR>
 *   (Note))Receiving Ticket Line No.+<BR>
 * </DIR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/10</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:26 $
 * @author  $Author: suresh $
 */
public class DefineLoadDataMenuShippingBusiness
	extends DefineLoadDataMenuShipping
	implements WMSConstants
{
	//#CM688019
	// Class fields --------------------------------------------------

	//#CM688020
	// Class variables -----------------------------------------------

	//#CM688021
	// Class method --------------------------------------------------

	//#CM688022
	// Constructors --------------------------------------------------

	//#CM688023
	// Public methods ------------------------------------------------

	//#CM688024
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
			//#CM688025
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM688026
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM688027
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM688028
			//Set the path to the help file.
			//#CM688029
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM688030
		// MSG-0009= Do you add it?
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}

	//#CM688031
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Set the cursor to Planned Shipping date (mandatory) Position. <BR>
	 * <DIR>
	 *   Field item [Initial Value] <BR>
	 *   <DIR>
	 *     Planned Shipping Date      (mandatory) Enabled [Ticked] <BR>
	 *     Customer Code    (mandatory) Enabled [Ticked] <BR>
	 *     Customer Name      (mandatory) Enabled [Ticked] <BR>
	 *     Ticket No.         (mandatory) Enabled [Ticked] <BR>
	 *     Ticket Line No.       (mandatory) Enabled [Ticked] <BR>
	 *     Item Code      (mandatory) Enabled [Ticked] <BR>
	 *     Packed Qty per Case      (mandatory) Enabled [Ticked] <BR>
	 *     Shipping Qty (Total Bulk Qty) (mandatory) Enabled [Ticked] <BR>
	 *     TC/DC division       (mandatory) Enabled [Ticked] <BR>
	 *   </DIR>
	 *   <BR>
	 *   Obtain the Length and Position that are mandatory items (count), from EnvironmentInformation, and display them. <BR>
	 *   Obtain Enabled, Length, and Position that are not mandatory items (count), from EnvironmentInformationMandatory item (count)<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM688032
		// Title
		lbl_SettingName.setResourceKey(this.getViewState().getString("TITLE"));

		//#CM688033
		// Set the initial value of the focus to Planned Shipping date (mandatory) Position.
		setFocus(txt_ShpPlanDateReqPst);

		Connection conn = null;
		try
		{
			//#CM688034
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter initParam = new DefineDataParameter();
			initParam.setSelectDefineLoadData(DefineDataParameter.SELECTDEFINELOADDATA_SHIPPING);
			WmsScheduler schedule = new DefineLoadDataMenuSCH();
			DefineDataParameter[] param = (DefineDataParameter[]) schedule.query(conn, initParam);

			if (param != null)
			{
				//#CM688035
				// Planned Shipping date (mandatory): Length
				txt_ShpPlanDateReqLen.setText(param[0].getFigure_PlanDate());
				//#CM688036
				// Planned Shipping date (mandatory): Max Length
				lbl_MaxLenShpPlanDateReq.setText(param[0].getMaxFigure_PlanDate());
				//#CM688037
				// Planned Shipping date (mandatory): Position
				txt_ShpPlanDateReqPst.setText(param[0].getPosition_PlanDate());
				//#CM688038
				// Order Date: Enabled
				chk_CommonUseOrderDate.setChecked(param[0].getValid_OrderingDate());
				//#CM688039
				// Order Date: Length
				txt_OdrgDateLen.setText(param[0].getFigure_OrderingDate());
				//#CM688040
				// Order Date: Max Length
				lbl_MaxLenOrderDate.setText(param[0].getMaxFigure_OrderingDate());
				//#CM688041
				// Order Date: Position
				txt_OdrgDatePst.setText(param[0].getPosition_OrderingDate());
				//#CM688042
				// Consignor Code: Enabled
				chk_CommonUseConsignorCode.setChecked(param[0].getValid_ConsignorCode());
				//#CM688043
				// Consignor Code: Length
				txt_CnsgnrCdLen.setText(param[0].getFigure_ConsignorCode());
				//#CM688044
				// Consignor Code: Max Length
				lbl_MaxLenConsignorCode.setText(param[0].getMaxFigure_ConsignorCode());
				//#CM688045
				// Consignor Code: Position
				txt_CnsgnrCdPst.setText(param[0].getPosition_ConsignorCode());
				//#CM688046
				// Consignor Name: Enabled
				chk_CommonUseConsignorName.setChecked(param[0].getValid_ConsignorName());
				//#CM688047
				// Consignor Name: Length
				txt_CnsgnrNmLen.setText(param[0].getFigure_ConsignorName());
				//#CM688048
				// Consignor Name: Max Length
				lbl_MaxLenConsignorName.setText(param[0].getMaxFigure_ConsignorName());
				//#CM688049
				// Consignor Name: Position
				txt_CnsgnrNmPst.setText(param[0].getPosition_ConsignorName());
				//#CM688050
				// Customer Name: Enabled
				chk_CommonUseCustNm.setChecked(param[0].getValid_CustomerName());
				//#CM688051
				// Customer Name: Length
				txt_CustNmLen.setText(param[0].getFigure_CustomerName());
				//#CM688052
				// Customer Name: Max Length
				lbl_MaxLenCustNm.setText(param[0].getMaxFigure_CustomerName());
				//#CM688053
				// Customer Name: Position
				txt_CustNmPst.setText(param[0].getPosition_CustomerName());
				//#CM688054
				// Item Name: Enabled
				chk_CommonUseItemName.setChecked(param[0].getValid_ItemName());
				//#CM688055
				// Item Name: Length
				txt_ItemNmLen.setText(param[0].getFigure_ItemName());
				//#CM688056
				// Item Name: Max Length
				lbl_MaxLenItemName.setText(param[0].getMaxFigure_ItemName());
				//#CM688057
				// Item Name: Position
				txt_ItemNmPst.setText(param[0].getPosition_ItemName());
				//#CM688058
				// Customer Code (mandatory): Length
				txt_CustCdReqLen.setText(param[0].getFigure_CustomerCode());
				//#CM688059
				// Customer Code (mandatory): Max Length
				lbl_MaxLenCustCdReq.setText(param[0].getMaxFigure_CustomerCode());
				//#CM688060
				// Customer Code (mandatory): Position
				txt_CustCdReqPst.setText(param[0].getPosition_CustomerCode());
				//#CM688061
				// Ticket No. (mandatory): Length
				txt_TktNoReqLen.setText(param[0].getFigure_ShippingTicketNo());
				//#CM688062
				// Ticket No. (mandatory): Max Length
				lbl_MaxLenTicketNoRequired.setText(param[0].getMaxFigure_ShippingTicketNo());
				//#CM688063
				// Ticket No. (mandatory): Position
				txt_TktNoReqPst.setText(param[0].getPosition_ShippingTicketNo());
				//#CM688064
				// Ticket Line No. (mandatory): Length
				txt_TktLineNoReqLen.setText(param[0].getFigure_ShippingLineNo());
				//#CM688065
				// Ticket Line No. (mandatory): Max Length
				lbl_MaxLenTktLineNoReq.setText(param[0].getMaxFigure_ShippingLineNo());
				//#CM688066
				// Ticket Line No. (mandatory): Position
				txt_TktLineNoReqPst.setText(param[0].getPosition_ShippingLineNo());
				//#CM688067
				// Item Code (mandatory): Length
				txt_ItemCdReqLen.setText(param[0].getFigure_ItemCode());
				//#CM688068
				// Item Code (mandatory): Max Length
				lbl_MaxLenItemCodeRequired.setText(param[0].getMaxFigure_ItemCode());
				//#CM688069
				// Item Code (mandatory): Position
				txt_ItemCdReqPst.setText(param[0].getPosition_ItemCode());
				//#CM688070
				// Bundle ITF: Enabled
				chk_CommonUseBundleItem.setChecked(param[0].getValid_BundleItf());
				//#CM688071
				// Bundle ITF: Length
				txt_BundleItfLength.setText(param[0].getFigure_BundleItf());
				//#CM688072
				// Bundle ITF: Max Length
				lbl_MaxLenBundleItem.setText(param[0].getMaxFigure_BundleItf());
				//#CM688073
				// Bundle ITF: Position
				txt_BundleItfPosition.setText(param[0].getPosition_BundleItf());
				//#CM688074
				// Case ITF: Enabled
				chk_CommonUseCaseItf.setChecked(param[0].getValid_Itf());
				//#CM688075
				// Case ITF: Length
				txt_CaseItfLength.setText(param[0].getFigure_Itf());
				//#CM688076
				// Case ITF: Max Length
				lbl_MaxLenCaseItf.setText(param[0].getMaxFigure_Itf());
				//#CM688077
				// Case ITF: Position
				txt_CaseItfPosition.setText(param[0].getPosition_Itf());
				//#CM688078
				// Packed qty per bundle: Enabled
				chk_CommonUseBundleEntering.setChecked(param[0].getValid_BundleEnteringQty());
				//#CM688079
				// Packed qty per bundle: Length
				txt_BdlEtrLen.setText(param[0].getFigure_BundleEnteringQty());
				//#CM688080
				// Packed qty per bundle: Max Length
				lbl_MaxLenBundleEntering.setText(param[0].getMaxFigure_BundleEnteringQty());
				//#CM688081
				// Packed qty per bundle: Position
				txt_BdlEtrPst.setText(param[0].getPosition_BundleEnteringQty());
				//#CM688082
				// Packed Qty per Case (mandatory): Length
				txt_CaseEtrReqLen.setText(param[0].getFigure_EnteringQty());
				//#CM688083
				// Packed Qty per Case (mandatory): Max Length
				lbl_MaxLenCaseEtrReq.setText(param[0].getMaxFigure_EnteringQty());
				//#CM688084
				// Packed Qty per Case (mandatory): Position
				txt_CaseEtrReqPst.setText(param[0].getPosition_EnteringQty());
				//#CM688085
				// Shipping Qty (Total Bulk Qty) (mandatory): Length
				txt_ShpQtyPtlLenReq.setText(param[0].getFigure_PlanQty());
				//#CM688086
				// Shipping Qty (Total Bulk Qty) (mandatory): Max Length
				lbl_MaxLenShpQtyPtlReq.setText(param[0].getMaxFigure_PlanQty());
				//#CM688087
				// Shipping Qty (Total Bulk Qty) (mandatory): Position
				txt_ShpQtyPtlPstReq.setText(param[0].getPosition_PlanQty());
				//#CM688088
				// TC/DC division (mandatory): Length
				txt_TCDCFlgReqLen.setText(param[0].getFigure_TcDcFlag());
				//#CM688089
				// TC/DC division (mandatory): Max Length
				lbl_MaxLenTCDCRequired.setText(param[0].getMaxFigure_TcDcFlag());
				//#CM688090
				// TC/DC division (mandatory): Position
				txt_TCDCFlgReqPst.setText(param[0].getPosition_TcDcFlag());
				//#CM688091
				// Supplier Code: Enabled
				chk_CommonUseSplCodeAst.setChecked(param[0].getValid_SupplierCode());
				//#CM688092
				// Supplier Code: Length
				txt_SplCdAstLen.setText(param[0].getFigure_SupplierCode());
				//#CM688093
				// Supplier Code: Max Length
				lbl_MaxLenSupplierCodeAst.setText(param[0].getMaxFigure_SupplierCode());
				//#CM688094
				// Supplier Code: Position
				txt_SplCdAstPst.setText(param[0].getPosition_SupplierCode());
				//#CM688095
				// Supplier Name: Enabled
				chk_CommonUseSplNameAst.setChecked(param[0].getValid_SupplierName());
				//#CM688096
				// Supplier Name: Length
				txt_SplNmAstLen.setText(param[0].getFigure_SupplierName());
				//#CM688097
				// Supplier Name: Max Length
				lbl_MaxLenSupplierNameAst.setText(param[0].getMaxFigure_SupplierName());
				//#CM688098
				// Supplier Name: Position
				txt_SplNmAstPst.setText(param[0].getPosition_SupplierName());
				//#CM688099
				// Receiving Ticket No.: Enabled
				chk_CommonUseInstkTktNoAst.setChecked(param[0].getValid_InstockTicketNo());
				//#CM688100
				// Receiving Ticket No.: Length
				txt_InstkTktNoAstLen.setText(param[0].getFigure_InstockTicketNo());
				//#CM688101
				// Receiving Ticket No.: Max Length
				lbl_MaxLenInstockTicketNoAst.setText(param[0].getMaxFigure_InstockTicketNo());
				//#CM688102
				// Receiving Ticket No.: Position
				txt_InstkTktNoAstPst.setText(param[0].getPosition_InstockTicketNo());
				//#CM688103
				// Receiving Ticket Line No.: Enabled
				chk_CommonUseInstkTktLNoAst.setChecked(param[0].getValid_InstockLineNo());
				//#CM688104
				// Receiving Ticket Line No.: Length
				txt_InstkTktLineAstLen.setText(param[0].getFigure_InstockLineNo());
				//#CM688105
				// Receiving Ticket Line No.: Max Length
				lbl_MaxLenInstkTktLineNoAst.setText(param[0].getMaxFigure_InstockLineNo());
				//#CM688106
				// Receiving Ticket Line No.: Position
				txt_InstkTktLineAstPst.setText(param[0].getPosition_InstockLineNo());
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
				//#CM688107
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

	//#CM688108
	// Package methods -----------------------------------------------

	//#CM688109
	// Protected methods ---------------------------------------------

	//#CM688110
	// Private methods -----------------------------------------------
	//#CM688111
	/** 
	 * Allow this method to check whether a checkbox is ticked off or not.<BR>
	 * <BR>
	 * @return Return false if no item ticked off.
	 * @throws Exception Report all exceptions.
	 */
	private boolean checkCheckBox() throws Exception
	{
		if (!chk_CommonNotUseShpPDateReq.getChecked()
				&& !chk_CommonUseOrderDate.getChecked()
				&& !chk_CommonUseConsignorCode.getChecked()
				&& !chk_CommonUseConsignorName.getChecked()
				&& !chk_CommonNotUseCustCdReq.getChecked()
				&& !chk_CommonUseCustNm.getChecked()
				&& !chk_CommonNotUseTicketNoRequir.getChecked()
				&& !chk_CommonNotUseTktLineNoReq.getChecked()
				&& !chk_CommonNotUseItemCodeRequir.getChecked()
				&& !chk_CommonUseBundleItem.getChecked()
				&& !chk_CommonUseCaseItf.getChecked()
				&& !chk_CommonUseBundleEntering.getChecked()
				&& !chk_CommonNotUseCaseEtrReq.getChecked()
				&& !chk_CommonUseItemName.getChecked()
				&& !chk_CommonNotUseShpQtyPtlReq.getChecked()
				&& !chk_CommonNotUseTCDCRequired.getChecked()
				&& !chk_CommonUseSplCodeAst.getChecked()
				&& !chk_CommonUseSplNameAst.getChecked()
				&& !chk_CommonUseSplCodeAst.getChecked()
				&& !chk_CommonUseInstkTktNoAst.getChecked()
				&& !chk_CommonUseInstkTktLNoAst.getChecked()
			)
		{
			return false;
		}
		return true;
	}

	//#CM688112
	// Event handler methods -----------------------------------------
	
	//#CM688113
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
		//#CM688114
		// Shift Screen
		forward("/system/defineloaddatamenu/DefineLoadDataMenu.do");
	}

	//#CM688115
	/** 
	 * Clicking on Menu button invokes this. <BR>
	 * <BR>
	 * Shift to the Menu screen.<BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM688116
	/** 
	 * Clicking on Add button invokes this. <BR>
	 * <BR>
	 * Summary: Displays a dialog box to allow to confirm to add it or not. <BR>
	 * <BR>
	 * Display a dialog. <BR>
	 * - Display the dialog box for confirming to add. "Do you add this?" <BR>
	 * <DIR>
	 *   [Dialog for confirming: Cancel] <BR>
	 *   <DIR>
	 *     Disable to do anything. <BR>
	 *   </DIR>
	 *   [Dialog for confirming: OK] <BR>
	 *   <DIR>
	 *     Summary: Adds data using Input area info. <BR>
	 *     1.Check for input. <BR>
	 *     <DIR>
	 *       If no check is placed in each checkbox, display a message: "Select the target to be added". <BR>
	 *     </DIR>
	 *     2.Check for input in the input item (count) in the input area. (Mandatory Input, number of characters, and attribution of characters) <BR>
	 *     <DIR>
	 *       - Ensure the length is not larger than the max length. <BR>
	 *       - Ensure that the Length is larger than 0. <BR>
	 *       - Ensure that the Position is larger than 0. <BR>
	 *       *Accept duplicate Position values (In such case, load the same data for each specified item field). <BR>
	 *     </DIR>
	 *     3.Start the schedule. <BR>
	 *     4.Set the cursor to Planned Shipping date (mandatory) Length. <BR>
	 *   </DIR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		//#CM688117
		// Set the initial value of the focus to Planned Shipping date (mandatory) Length.
		setFocus(txt_ShpPlanDateReqPst);
		
		//#CM688118
		// Check for input.
		WmsCheckker.checkDefine(lbl_ShpPlanDateReq, chk_CommonNotUseShpPDateReq, txt_ShpPlanDateReqLen, lbl_MaxLenShpPlanDateReq, txt_ShpPlanDateReqPst);
		WmsCheckker.checkDefine(lbl_OrderDate, chk_CommonUseOrderDate, txt_OdrgDateLen, lbl_MaxLenOrderDate, txt_OdrgDatePst);
		WmsCheckker.checkDefine(lbl_ConsignorCode, chk_CommonUseConsignorCode, txt_CnsgnrCdLen, lbl_MaxLenConsignorCode, txt_CnsgnrCdPst);
		WmsCheckker.checkDefine(lbl_ConsignorName, chk_CommonUseConsignorName, txt_CnsgnrNmLen, lbl_MaxLenConsignorName, txt_CnsgnrNmPst);
		WmsCheckker.checkDefine(lbl_CustCdReq, chk_CommonNotUseCustCdReq, txt_CustCdReqLen, lbl_MaxLenCustCdReq, txt_CustCdReqPst);
		WmsCheckker.checkDefine(lbl_CustomerName, chk_CommonUseCustNm, txt_CustNmLen, lbl_MaxLenCustNm, txt_CustNmPst);
		WmsCheckker.checkDefine(lbl_TicketNoRequired, chk_CommonNotUseTicketNoRequir, txt_TktNoReqLen, lbl_MaxLenTicketNoRequired, txt_TktNoReqPst);
		WmsCheckker.checkDefine(lbl_TktLineNoReq, chk_CommonNotUseTktLineNoReq, txt_TktLineNoReqLen, lbl_MaxLenTktLineNoReq, txt_TktLineNoReqPst);
		WmsCheckker.checkDefine(lbl_ItemCodeRequired, chk_CommonNotUseItemCodeRequir, txt_ItemCdReqLen, lbl_MaxLenItemCodeRequired, txt_ItemCdReqPst);
		WmsCheckker.checkDefine(lbl_BundleItf, chk_CommonUseBundleItem, txt_BundleItfLength, lbl_MaxLenBundleItem, txt_BundleItfPosition);
		WmsCheckker.checkDefine(lbl_CaseItf, chk_CommonUseCaseItf, txt_CaseItfLength, lbl_MaxLenCaseItf, txt_CaseItfPosition);
		WmsCheckker.checkDefine(lbl_BundleEntering, chk_CommonUseBundleEntering, txt_BdlEtrLen, lbl_MaxLenBundleEntering, txt_BdlEtrPst);
		WmsCheckker.checkDefine(lbl_CaseEtrReq, chk_CommonNotUseCaseEtrReq, txt_CaseEtrReqLen, lbl_MaxLenCaseEtrReq, txt_CaseEtrReqPst);
		WmsCheckker.checkDefine(lbl_ItemName, chk_CommonUseItemName, txt_ItemNmLen, lbl_MaxLenItemName, txt_ItemNmPst);
		WmsCheckker.checkDefine(lbl_ShpQtyPtlReq, chk_CommonNotUseShpQtyPtlReq, txt_ShpQtyPtlLenReq, lbl_MaxLenShpQtyPtlReq, txt_ShpQtyPtlPstReq);
		WmsCheckker.checkDefine(lbl_TCDCRequired, chk_CommonNotUseTCDCRequired, txt_TCDCFlgReqLen, lbl_MaxLenTCDCRequired, txt_TCDCFlgReqPst);
		WmsCheckker.checkDefine(lbl_SupplierCodeAst, chk_CommonUseSplCodeAst, txt_SplCdAstLen, lbl_MaxLenSupplierCodeAst, txt_SplCdAstPst);
		WmsCheckker.checkDefine(lbl_SupplierName, chk_CommonUseSplNameAst, txt_SplNmAstLen, lbl_MaxLenSupplierNameAst, txt_SplNmAstPst);
		WmsCheckker.checkDefine(lbl_InstockTicketNoAst, chk_CommonUseInstkTktNoAst, txt_InstkTktNoAstLen, lbl_MaxLenInstockTicketNoAst, txt_InstkTktNoAstPst);
		WmsCheckker.checkDefine(lbl_InstkTktLineNoAst, chk_CommonUseInstkTktLNoAst, txt_InstkTktLineAstLen, lbl_MaxLenInstkTktLineNoAst, txt_InstkTktLineAstPst);

		//#CM688119
		// Check the checkbox.
		if (!checkCheckBox())
		{
			//#CM688120
			// 6023174=Please select 1 or more items.
			message.setMsgResourceKey("6023174");
			return;
		}
		
		Connection conn = null;
		try
		{
			DefineDataParameter[] param = new DefineDataParameter[1];
			param[0] = new DefineDataParameter();

			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new DefineLoadDataMenuSCH();

			//#CM688121
			// Set the value for the parameter.
			param[0].setSelectDefineLoadData(DefineDataParameter.SELECTDEFINELOADDATA_SHIPPING);

			//#CM688122
			// Planned Shipping date (mandatory): Enabled
			param[0].setValid_PlanDate(true);
			//#CM688123
			// Planned Shipping date (mandatory): Length
			param[0].setFigure_PlanDate(txt_ShpPlanDateReqLen.getText());
			//#CM688124
			// Planned Shipping date (mandatory): Max Length
			param[0].setMaxFigure_PlanDate(lbl_MaxLenShpPlanDateReq.getText());
			//#CM688125
			// Planned Shipping date (mandatory): Position
			param[0].setPosition_PlanDate(txt_ShpPlanDateReqPst.getText());
			//#CM688126
			// Order Date: Enabled
			param[0].setValid_OrderingDate(chk_CommonUseOrderDate.getChecked());
			//#CM688127
			// Order Date: Length
			param[0].setFigure_OrderingDate(txt_OdrgDateLen.getText());
			//#CM688128
			// Order Date: Max Length
			param[0].setMaxFigure_OrderingDate(lbl_MaxLenOrderDate.getText());
			//#CM688129
			// Order Date: Position
			param[0].setPosition_OrderingDate(txt_OdrgDatePst.getText());
			//#CM688130
			// Consignor Code: Enabled
			param[0].setValid_ConsignorCode(chk_CommonUseConsignorCode.getChecked());
			//#CM688131
			// Consignor Code: Length
			param[0].setFigure_ConsignorCode(txt_CnsgnrCdLen.getText());
			//#CM688132
			// Consignor Code: Max Length
			param[0].setMaxFigure_ConsignorCode(lbl_MaxLenConsignorCode.getText());
			//#CM688133
			// Consignor Code: Position
			param[0].setPosition_ConsignorCode(txt_CnsgnrCdPst.getText());
			//#CM688134
			// Consignor Name: Enabled
			param[0].setValid_ConsignorName(chk_CommonUseConsignorName.getChecked());
			//#CM688135
			// Consignor Name: Length
			param[0].setFigure_ConsignorName(txt_CnsgnrNmLen.getText());
			//#CM688136
			// Consignor Name: Max Length
			param[0].setMaxFigure_ConsignorName(lbl_MaxLenConsignorName.getText());
			//#CM688137
			// Consignor Name: Position
			param[0].setPosition_ConsignorName(txt_CnsgnrNmPst.getText());
			//#CM688138
			// Customer Code (mandatory): Enabled
			param[0].setValid_CustomerCode(true);
			//#CM688139
			// Customer Code (mandatory): Length
			param[0].setFigure_CustomerCode(txt_CustCdReqLen.getText());
			//#CM688140
			// Customer Code (mandatory): Max Length
			param[0].setMaxFigure_CustomerCode(lbl_MaxLenCustCdReq.getText());
			//#CM688141
			// Customer Code (mandatory): Position
			param[0].setPosition_CustomerCode(txt_CustCdReqPst.getText());
			//#CM688142
			// Customer Name (mandatory): Enabled
			param[0].setValid_CustomerName(chk_CommonUseCustNm.getChecked());
			//#CM688143
			// Customer Name (mandatory): Length
			param[0].setFigure_CustomerName(txt_CustNmLen.getText());
			//#CM688144
			// Customer Name (mandatory): Max Length
			param[0].setMaxFigure_CustomerName(lbl_MaxLenCustNm.getText());
			//#CM688145
			// Customer Name (mandatory): Position
			param[0].setPosition_CustomerName(txt_CustNmPst.getText());
			//#CM688146
			// Ticket No. (mandatory): Enabled
			param[0].setValid_ShippingTicketNo(true);
			//#CM688147
			// Ticket No. (mandatory): Length
			param[0].setFigure_ShippingTicketNo(txt_TktNoReqLen.getText());
			//#CM688148
			// Ticket No. (mandatory): Max Length
			param[0].setMaxFigure_ShippingTicketNo(lbl_MaxLenTicketNoRequired.getText());
			//#CM688149
			// Ticket No. (mandatory): Position
			param[0].setPosition_ShippingTicketNo(txt_TktNoReqPst.getText());
			//#CM688150
			// Ticket Line No. (mandatory): Enabled
			param[0].setValid_ShippingLineNo(true);
			//#CM688151
			// Ticket Line No. (mandatory): Length
			param[0].setFigure_ShippingLineNo(txt_TktLineNoReqLen.getText());
			//#CM688152
			// Ticket Line No. (mandatory): Max Length
			param[0].setMaxFigure_ShippingLineNo(lbl_MaxLenTktLineNoReq.getText());
			//#CM688153
			// Ticket Line No. (mandatory): Position
			param[0].setPosition_ShippingLineNo(txt_TktLineNoReqPst.getText());
			//#CM688154
			// Item Code (mandatory): Enabled
			param[0].setValid_ItemCode(true);
			//#CM688155
			// Item Code (mandatory): Length
			param[0].setFigure_ItemCode(txt_ItemCdReqLen.getText());
			//#CM688156
			// Item Code (mandatory): Max Length
			param[0].setMaxFigure_ItemCode(lbl_MaxLenItemCodeRequired.getText());
			//#CM688157
			// Item Code (mandatory): Position
			param[0].setPosition_ItemCode(txt_ItemCdReqPst.getText());
			//#CM688158
			// Bundle ITF: Enabled
			param[0].setValid_BundleItf(chk_CommonUseBundleItem.getChecked());
			//#CM688159
			// Bundle ITF: Length
			param[0].setFigure_BundleItf(txt_BundleItfLength.getText());
			//#CM688160
			// Bundle ITF: Max Length
			param[0].setMaxFigure_BundleItf(lbl_MaxLenBundleItem.getText());
			//#CM688161
			// Bundle ITF: Position
			param[0].setPosition_BundleItf(txt_BundleItfPosition.getText());
			//#CM688162
			// Case ITF: Enabled
			param[0].setValid_Itf(chk_CommonUseCaseItf.getChecked());
			//#CM688163
			// Case ITF: Length
			param[0].setFigure_Itf(txt_CaseItfLength.getText());
			//#CM688164
			// Case ITF: Max Length
			param[0].setMaxFigure_Itf(lbl_MaxLenCaseItf.getText());
			//#CM688165
			// Case ITF: Position
			param[0].setPosition_Itf(txt_CaseItfPosition.getText());
			//#CM688166
			// Packed qty per bundle: Enabled
			param[0].setValid_BundleEnteringQty(chk_CommonUseBundleEntering.getChecked());
			//#CM688167
			// Packed qty per bundle: Length
			param[0].setFigure_BundleEnteringQty(txt_BdlEtrLen.getText());
			//#CM688168
			// Packed qty per bundle: Max Length
			param[0].setMaxFigure_BundleEnteringQty(lbl_MaxLenBundleEntering.getText());
			//#CM688169
			// Packed qty per bundle: Position
			param[0].setPosition_BundleEnteringQty(txt_BdlEtrPst.getText());
			//#CM688170
			// Packed Qty per Case (mandatory): Enabled
			param[0].setValid_EnteringQty(true);
			//#CM688171
			// Packed Qty per Case (mandatory): Length
			param[0].setFigure_EnteringQty(txt_CaseEtrReqLen.getText());
			//#CM688172
			// Packed Qty per Case (mandatory): Max Length
			param[0].setMaxFigure_EnteringQty(lbl_MaxLenCaseEtrReq.getText());
			//#CM688173
			// Packed Qty per Case (mandatory): Position
			param[0].setPosition_EnteringQty(txt_CaseEtrReqPst.getText());
			//#CM688174
			// Item Name: Enabled
			param[0].setValid_ItemName(chk_CommonUseItemName.getChecked());
			//#CM688175
			// Item Name: Length
			param[0].setFigure_ItemName(txt_ItemNmLen.getText());
			//#CM688176
			// Item Name: Max Length
			param[0].setMaxFigure_ItemName(lbl_MaxLenItemName.getText());
			//#CM688177
			// Item Name: Position
			param[0].setPosition_ItemName(txt_ItemNmPst.getText());
			//#CM688178
			// Shipping Qty (Total Bulk Qty) (mandatory): Enabled
			param[0].setValid_PlanQty(true);
			//#CM688179
			// Shipping Qty (Total Bulk Qty) (mandatory): Length
			param[0].setFigure_PlanQty(txt_ShpQtyPtlLenReq.getText());
			//#CM688180
			// Shipping Qty (Total Bulk Qty) (mandatory): Max Length
			param[0].setMaxFigure_PlanQty(lbl_MaxLenShpQtyPtlReq.getText());
			//#CM688181
			// Shipping Qty (Total Bulk Qty) (mandatory): Position
			param[0].setPosition_PlanQty(txt_ShpQtyPtlPstReq.getText());
			//#CM688182
			// TC/DC division (mandatory): Enabled
			param[0].setValid_TcDcFlag(true);
			//#CM688183
			// TC/DC division (mandatory): Length
			param[0].setFigure_TcDcFlag(txt_TCDCFlgReqLen.getText());
			//#CM688184
			// TC/DC division (mandatory): Max Length
			param[0].setMaxFigure_TcDcFlag(lbl_MaxLenTCDCRequired.getText());
			//#CM688185
			// TC/DC division (mandatory): Position
			param[0].setPosition_TcDcFlag(txt_TCDCFlgReqPst.getText());
			//#CM688186
			// Supplier Code: Enabled
			param[0].setValid_SupplierCode(chk_CommonUseSplCodeAst.getChecked());
			//#CM688187
			// Supplier Code: Length
			param[0].setFigure_SupplierCode(txt_SplCdAstLen.getText());
			//#CM688188
			// Supplier Code: Max Length
			param[0].setMaxFigure_SupplierCode(lbl_MaxLenSupplierCodeAst.getText());
			//#CM688189
			// Supplier Code: Position
			param[0].setPosition_SupplierCode(txt_SplCdAstPst.getText());
			//#CM688190
			// Supplier Name: Enabled
			param[0].setValid_SupplierName(chk_CommonUseSplNameAst.getChecked());
			//#CM688191
			// Supplier Name: Length
			param[0].setFigure_SupplierName(txt_SplNmAstLen.getText());
			//#CM688192
			// Supplier Name: Max Length
			param[0].setMaxFigure_SupplierName(lbl_MaxLenSupplierNameAst.getText());
			//#CM688193
			// Supplier Name: Position
			param[0].setPosition_SupplierName(txt_SplNmAstPst.getText());
			//#CM688194
			// Receiving Ticket No.: Enabled
			param[0].setValid_InstockTicketNo(chk_CommonUseInstkTktNoAst.getChecked());
			//#CM688195
			// Receiving Ticket No.: Length
			param[0].setFigure_InstockTicketNo(txt_InstkTktNoAstLen.getText());
			//#CM688196
			// Receiving Ticket No.: Max Length
			param[0].setMaxFigure_InstockTicketNo(lbl_MaxLenInstockTicketNoAst.getText());
			//#CM688197
			// Receiving Ticket No.: Position
			param[0].setPosition_InstockTicketNo(txt_InstkTktNoAstPst.getText());
			//#CM688198
			// Receiving Ticket Line No.: Enabled
			param[0].setValid_InstockLineNo(chk_CommonUseInstkTktLNoAst.getChecked());
			//#CM688199
			// Receiving Ticket Line No.: Length
			param[0].setFigure_InstockLineNo(txt_InstkTktLineAstLen.getText());
			//#CM688200
			// Receiving Ticket Line No.: Max Length
			param[0].setMaxFigure_InstockLineNo(lbl_MaxLenInstkTktLineNoAst.getText());
			//#CM688201
			// Receiving Ticket Line No.: Position
			param[0].setPosition_InstockLineNo(txt_InstkTktLineAstPst.getText());

			//#CM688202
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
				//#CM688203
				// Display a message.
				message.setMsgResourceKey(schedule.getMessage());
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
				//#CM688204
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

	//#CM688205
	/** 
	 * Clicking on Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 * <DIR>
	 *   1.Return the item (count) in the input area to the initial value. <BR>
	 *   <DIR>
	 *     For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method. <BR>
	 *   </DIR>
	 *   2.Set the cursor to Planned Shipping date (mandatory) Length. <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM688206
		// Set the initial value of the focus to Planned Shipping date (mandatory) Length.
		setFocus(txt_ShpPlanDateReqPst);

		Connection conn = null;
		try
		{
			//#CM688207
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter initParam = new DefineDataParameter();
			initParam.setSelectDefineLoadData(DefineDataParameter.SELECTDEFINELOADDATA_SHIPPING);
			WmsScheduler schedule = new DefineLoadDataMenuSCH();
			DefineDataParameter[] param = (DefineDataParameter[]) schedule.query(conn, initParam);

			if (param != null)
			{
				//#CM688208
				// Planned Shipping date (mandatory): Length
				txt_ShpPlanDateReqLen.setText(param[0].getFigure_PlanDate());
				//#CM688209
				// Planned Shipping date (mandatory): Max Length
				lbl_MaxLenShpPlanDateReq.setText(param[0].getMaxFigure_PlanDate());
				//#CM688210
				// Planned Shipping date (mandatory): Position
				txt_ShpPlanDateReqPst.setText(param[0].getPosition_PlanDate());
				//#CM688211
				// Order Date: Enabled
				chk_CommonUseOrderDate.setChecked(param[0].getValid_OrderingDate());
				//#CM688212
				// Order Date: Length
				txt_OdrgDateLen.setText(param[0].getFigure_OrderingDate());
				//#CM688213
				// Order Date: Max Length
				lbl_MaxLenOrderDate.setText(param[0].getMaxFigure_OrderingDate());
				//#CM688214
				// Order Date: Position
				txt_OdrgDatePst.setText(param[0].getPosition_OrderingDate());
				//#CM688215
				// Consignor Code: Enabled
				chk_CommonUseConsignorCode.setChecked(param[0].getValid_ConsignorCode());
				//#CM688216
				// Consignor Code: Length
				txt_CnsgnrCdLen.setText(param[0].getFigure_ConsignorCode());
				//#CM688217
				// Consignor Code: Max Length
				lbl_MaxLenConsignorCode.setText(param[0].getMaxFigure_ConsignorCode());
				//#CM688218
				// Consignor Code: Position
				txt_CnsgnrCdPst.setText(param[0].getPosition_ConsignorCode());
				//#CM688219
				// Consignor Name: Enabled
				chk_CommonUseConsignorName.setChecked(param[0].getValid_ConsignorName());
				//#CM688220
				// Consignor Name: Length
				txt_CnsgnrNmLen.setText(param[0].getFigure_ConsignorName());
				//#CM688221
				// Consignor Name: Max Length
				lbl_MaxLenConsignorName.setText(param[0].getMaxFigure_ConsignorName());
				//#CM688222
				// Consignor Name: Position
				txt_CnsgnrNmPst.setText(param[0].getPosition_ConsignorName());
				//#CM688223
				// Customer Code (mandatory): Length
				txt_CustCdReqLen.setText(param[0].getFigure_CustomerCode());
				//#CM688224
				// Customer Code (mandatory): Max Length
				lbl_MaxLenCustCdReq.setText(param[0].getMaxFigure_CustomerCode());
				//#CM688225
				// Customer Code (mandatory): Position
				txt_CustCdReqPst.setText(param[0].getPosition_CustomerCode());
				//#CM688226
				// Customer Name: Enabled
				chk_CommonUseCustNm.setChecked(param[0].getValid_CustomerName());
				//#CM688227
				// Customer Name: Length
				txt_CustNmLen.setText(param[0].getFigure_CustomerName());
				//#CM688228
				// Customer Name: Max Length
				lbl_MaxLenCustNm.setText(param[0].getMaxFigure_CustomerName());
				//#CM688229
				// Customer Name: Position
				txt_CustNmPst.setText(param[0].getPosition_CustomerName());
				//#CM688230
				// Ticket No. (mandatory): Length
				txt_TktNoReqLen.setText(param[0].getFigure_ShippingTicketNo());
				//#CM688231
				// Ticket No. (mandatory): Max Length
				lbl_MaxLenTicketNoRequired.setText(param[0].getMaxFigure_ShippingTicketNo());
				//#CM688232
				// Ticket No. (mandatory): Position
				txt_TktNoReqPst.setText(param[0].getPosition_ShippingTicketNo());
				//#CM688233
				// Ticket Line No. (mandatory): Length
				txt_TktLineNoReqLen.setText(param[0].getFigure_ShippingLineNo());
				//#CM688234
				// Ticket Line No. (mandatory): Max Length
				lbl_MaxLenTktLineNoReq.setText(param[0].getMaxFigure_ShippingLineNo());
				//#CM688235
				// Ticket Line No. (mandatory): Position
				txt_TktLineNoReqPst.setText(param[0].getPosition_ShippingLineNo());
				//#CM688236
				// Item Code (mandatory): Length
				txt_ItemCdReqLen.setText(param[0].getFigure_ItemCode());
				//#CM688237
				// Item Code (mandatory): Max Length
				lbl_MaxLenItemCodeRequired.setText(param[0].getMaxFigure_ItemCode());
				//#CM688238
				// Item Code (mandatory): Position
				txt_ItemCdReqPst.setText(param[0].getPosition_ItemCode());
				//#CM688239
				// Bundle ITF: Enabled
				chk_CommonUseBundleItem.setChecked(param[0].getValid_BundleItf());
				//#CM688240
				// Bundle ITF: Length
				txt_BundleItfLength.setText(param[0].getFigure_BundleItf());
				//#CM688241
				// Bundle ITF: Max Length
				lbl_MaxLenBundleItem.setText(param[0].getMaxFigure_BundleItf());
				//#CM688242
				// Bundle ITF: Position
				txt_BundleItfPosition.setText(param[0].getPosition_BundleItf());
				//#CM688243
				// Case ITF: Enabled
				chk_CommonUseCaseItf.setChecked(param[0].getValid_Itf());
				//#CM688244
				// Case ITF: Length
				txt_CaseItfLength.setText(param[0].getFigure_Itf());
				//#CM688245
				// Case ITF: Max Length
				lbl_MaxLenCaseItf.setText(param[0].getMaxFigure_Itf());
				//#CM688246
				// Case ITF: Position
				txt_CaseItfPosition.setText(param[0].getPosition_Itf());
				//#CM688247
				// Packed qty per bundle: Enabled
				chk_CommonUseBundleEntering.setChecked(param[0].getValid_BundleEnteringQty());
				//#CM688248
				// Packed qty per bundle: Length
				txt_BdlEtrLen.setText(param[0].getFigure_BundleEnteringQty());
				//#CM688249
				// Packed qty per bundle: Max Length
				lbl_MaxLenBundleEntering.setText(param[0].getMaxFigure_BundleEnteringQty());
				//#CM688250
				// Packed qty per bundle: Position
				txt_BdlEtrPst.setText(param[0].getPosition_BundleEnteringQty());
				//#CM688251
				// Packed Qty per Case (mandatory): Length
				txt_CaseEtrReqLen.setText(param[0].getFigure_EnteringQty());
				//#CM688252
				// Packed Qty per Case (mandatory): Max Length
				lbl_MaxLenCaseEtrReq.setText(param[0].getMaxFigure_EnteringQty());
				//#CM688253
				// Packed Qty per Case (mandatory): Position
				txt_CaseEtrReqPst.setText(param[0].getPosition_EnteringQty());
				//#CM688254
				// Item Name: Enabled
				chk_CommonUseItemName.setChecked(param[0].getValid_ItemName());
				//#CM688255
				// Item Name: Length
				txt_ItemNmLen.setText(param[0].getFigure_ItemName());
				//#CM688256
				// Item Name: Max Length
				lbl_MaxLenItemName.setText(param[0].getMaxFigure_ItemName());
				//#CM688257
				// Item Name: Position
				txt_ItemNmPst.setText(param[0].getPosition_ItemName());
				//#CM688258
				// Shipping Qty (Total Bulk Qty) (mandatory): Length
				txt_ShpQtyPtlLenReq.setText(param[0].getFigure_PlanQty());
				//#CM688259
				// Shipping Qty (Total Bulk Qty) (mandatory): Max Length
				lbl_MaxLenShpQtyPtlReq.setText(param[0].getMaxFigure_PlanQty());
				//#CM688260
				// Shipping Qty (Total Bulk Qty) (mandatory): Position
				txt_ShpQtyPtlPstReq.setText(param[0].getPosition_PlanQty());
				//#CM688261
				// TC/DC division (mandatory): Length
				txt_TCDCFlgReqLen.setText(param[0].getFigure_TcDcFlag());
				//#CM688262
				// TC/DC division (mandatory): Max Length
				lbl_MaxLenTCDCRequired.setText(param[0].getMaxFigure_TcDcFlag());
				//#CM688263
				// TC/DC division (mandatory): Position
				txt_TCDCFlgReqPst.setText(param[0].getPosition_TcDcFlag());
				//#CM688264
				// Supplier Code: Length
				txt_SplCdAstLen.setText(param[0].getFigure_SupplierCode());
				//#CM688265
				// Supplier Code: Max Length
				lbl_MaxLenSupplierCodeAst.setText(param[0].getMaxFigure_SupplierCode());
				//#CM688266
				// Supplier Code: Position
				txt_SplCdAstPst.setText(param[0].getPosition_SupplierCode());
				//#CM688267
				// Supplier Name: Length
				txt_SplNmAstLen.setText(param[0].getFigure_SupplierName());
				//#CM688268
				// Supplier Name: Max Length
				lbl_MaxLenSupplierNameAst.setText(param[0].getMaxFigure_SupplierName());
				//#CM688269
				// Supplier Name: Position
				txt_SplNmAstPst.setText(param[0].getPosition_SupplierName());
				//#CM688270
				// Receiving Ticket No.: Length
				txt_InstkTktNoAstLen.setText(param[0].getFigure_InstockTicketNo());
				//#CM688271
				// Receiving Ticket No.: Max Length
				lbl_MaxLenInstockTicketNoAst.setText(param[0].getMaxFigure_InstockTicketNo());
				//#CM688272
				// Receiving Ticket No.: Position
				txt_InstkTktNoAstPst.setText(param[0].getPosition_InstockTicketNo());
				//#CM688273
				// Receiving Ticket Line No.: Length
				txt_InstkTktLineAstLen.setText(param[0].getFigure_InstockLineNo());
				//#CM688274
				// Receiving Ticket Line No.: Max Length
				lbl_MaxLenInstkTktLineNoAst.setText(param[0].getMaxFigure_InstockLineNo());
				//#CM688275
				// Receiving Ticket Line No.: Position
				txt_InstkTktLineAstPst.setText(param[0].getPosition_InstockLineNo());
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
				//#CM688276
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
}
//#CM688277
//end of class
