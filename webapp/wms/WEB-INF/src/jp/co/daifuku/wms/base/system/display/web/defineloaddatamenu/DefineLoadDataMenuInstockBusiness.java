// $Id: DefineLoadDataMenuInstockBusiness.java,v 1.2 2006/11/13 08:18:28 suresh Exp $

//#CM687656
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

//#CM687657
/**
 * Designer : T.Uehata <BR>
 * Maker : T.Kuroda <BR>
 * <BR>
 * Allow this class to provide a screen to set a field item for loading data (to set the Receiving Plan field). <BR>
 * Set the contents input via input area for the parameter and pass it to the schedule. Add the setting of Receiving Plan field. <BR>
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
 *   Planned Receiving Date*<BR>
 * 	 Supplier Code*<BR>
 * 	 Ticket No.*<BR>
 * 	 Ticket Line No.*<BR>
 *   Item Code*<BR>
 *   Packed Qty per Case*<BR>
 *   Receiving Qty (Total Bulk Qty)*<BR>
 * 	 TC/DC division*<BR>
 * </DIR>
 * <BR>
 *   [Parameter]+ Require to input if the parameter is ticked to Enabled <BR>
 *   Length, Max Length, and Position of each field item<BR>
 * <BR>
 * <DIR>
 *   Order Date+<BR>
 *   Consignor Code +<BR>
 *   Consignor Name+<BR>
 * 	 Supplier Name+<BR>
 *   Bundle ITF+<BR>
 *   Case ITF+<BR>
 *   Packed qty per bundle+<BR>
 *   Item Name+<BR>
 * 	 (Note))Customer Code+<BR>
 * 	 Customer Name+<BR>
 * </DIR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/04</TD><TD>T.Kuroda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:28 $
 * @author  $Author: suresh $
 */
public class DefineLoadDataMenuInstockBusiness extends DefineLoadDataMenuInstock implements WMSConstants
{
	//#CM687658
	// Class fields --------------------------------------------------

	//#CM687659
	// Class variables -----------------------------------------------

	//#CM687660
	// Class method --------------------------------------------------

	//#CM687661
	// Constructors --------------------------------------------------

	//#CM687662
	// Public methods ------------------------------------------------

	//#CM687663
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
			//#CM687664
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM687665
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM687666
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM687667
			//Set the path to the help file.
			//#CM687668
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM687669
		// MSG-0009= Do you add it?
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}
	
	//#CM687670
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Set the cursor to Planned Receiving Date (mandatory) Position. <BR>
	 * <DIR>
	 *   Field item [Initial Value] <BR>
	 *   <DIR>
	 *     Planned Receiving Date      	(mandatory) Enabled [Ticked] <BR>
	 * 	   Supplier Code		(mandatory) Enabled [Ticked] <BR>
	 * 	   Ticket No.			(mandatory) Enabled [Ticked] <BR>
	 * 	   Ticket Line No.		(mandatory) Enabled [Ticked] <BR>
	 *     Item Code      	(mandatory) Enabled [Ticked] <BR>
	 *     Packed Qty per Case      	(mandatory) Enabled [Ticked] <BR>
	 *     Receiving Qty (Total Bulk Qty)	(mandatory) Enabled [Ticked] <BR>
	 * 	   TC/DC division	(mandatory) Enabled [Ticked] <BR>
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
		//#CM687671
		// Title
		lbl_SettingName.setResourceKey(this.getViewState().getString("TITLE"));

		dataLoad();
	}

	//#CM687672
	// Package methods -----------------------------------------------

	//#CM687673
	// Protected methods ---------------------------------------------

	//#CM687674
	// Private methods -----------------------------------------------
	//#CM687675
	/**
	 * Clicking on Clear button when screen starts-up invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 * <DIR>
	 *   1.Return the item (count) in the input area to the initial value. <BR>
	 *   <DIR>
	 *     For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method. <BR>
	 *   </DIR>
	 *   2.Set the cursor to Planned Receiving Date (mandatory) Position. <BR>
	 * </DIR>
	 * @throws Exception Report all exceptions.
	 */
	private void dataLoad() throws Exception
	{
		//#CM687676
		// Set the initial value of the focus to Planned Receiving Date (mandatory) Position.
		setFocus(txt_InstkPlanDtReqPst);

		Connection conn = null;
		try
		{
			//#CM687677
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter initParam = new DefineDataParameter();
			initParam.setSelectDefineLoadData(DefineDataParameter.SELECTDEFINELOADDATA_INSTOCK);
			
			WmsScheduler schedule = new DefineLoadDataMenuSCH();
			DefineDataParameter[] param = (DefineDataParameter[]) schedule.query(conn, initParam);

			if (param != null)
			{
				//#CM687678
				// Planned Receiving Date (mandatory): Length
				txt_InstkPlanDtReqLen.setText(param[0].getFigure_PlanDate());
				//#CM687679
				// Planned Receiving Date (mandatory): Max Length
				lbl_MaxLenInstkPlanDtReq.setText(param[0].getMaxFigure_PlanDate());
				//#CM687680
				// Planned Receiving Date (mandatory): Position
				txt_InstkPlanDtReqPst.setText(param[0].getPosition_PlanDate());

				//#CM687681
				// Order Date: Enabled
				chk_OrderDate.setChecked(param[0].getValid_OrderingDate());
				//#CM687682
				// Order Date: Length
				txt_OrderDateLen.setText(param[0].getFigure_OrderingDate());
				//#CM687683
				// Order Date: Max Length
				lbl_MaxLenOrderDate.setText(param[0].getMaxFigure_OrderingDate());
				//#CM687684
				// Order Date: Position
				txt_OrderDatePst.setText(param[0].getPosition_OrderingDate());
				
				//#CM687685
				// Consignor Code: Enabled
				chk_CnsgnrCd.setChecked(param[0].getValid_ConsignorCode());
				//#CM687686
				// Consignor Code: Length
				txt_CnsgnrCdLen.setText(param[0].getFigure_ConsignorCode());
				//#CM687687
				// Consignor Code: Max Length
				lbl_MaxLenCnsgnrCd.setText(param[0].getMaxFigure_ConsignorCode());
				//#CM687688
				// Consignor Code: Position
				txt_CnsgnrCdPst.setText(param[0].getPosition_ConsignorCode());
				
				//#CM687689
				// Consignor Name: Enabled
				chk_CnsgnrNm.setChecked(param[0].getValid_ConsignorName());
				//#CM687690
				// Consignor Name: Length
				txt_CnsgnrNmLen.setText(param[0].getFigure_ConsignorName());
				//#CM687691
				// Consignor Name: Max Length
				lbl_MaxLenCnsgnrNm.setText(param[0].getMaxFigure_ConsignorName());
				//#CM687692
				// Consignor Name: Position
				txt_CnsgnrNmPst.setText(param[0].getPosition_ConsignorName());
				
				//#CM687693
				// Supplier Code (mandatory): Length
				txt_SupplierCdReqLen.setText(param[0].getFigure_SupplierCode());
				//#CM687694
				// Supplier Code (mandatory): Max Length
				lbl_MaxLenSupplierCdReq.setText(param[0].getMaxFigure_SupplierCode());
				//#CM687695
				// Supplier Code (mandatory): Position
				txt_SupplierCdReqPst.setText(param[0].getPosition_SupplierCode());

				//#CM687696
				// Supplier Name: Enabled
				chk_NSupplierNm.setChecked(param[0].getValid_SupplierName());
				//#CM687697
				// Supplier Name: Length
				txt_SupplierNmLen.setText(param[0].getFigure_SupplierName());
				//#CM687698
				// Supplier Name: Max Length
				lbl_MaxLenSupplierNm.setText(param[0].getMaxFigure_SupplierName());
				//#CM687699
				// Supplier Name: Position
				txt_SupplierNmPst.setText(param[0].getPosition_SupplierName());
				
				//#CM687700
				// Ticket No. (mandatory): Length
				txt_TktNoReqLen.setText(param[0].getFigure_ShippingTicketNo());
				//#CM687701
				// Ticket No. (mandatory): Max Length
				lbl_MaxLenTktNoReq.setText(param[0].getMaxFigure_ShippingTicketNo());
				//#CM687702
				// Ticket No. (mandatory): Position
				txt_TktNoReqPst.setText(param[0].getPosition_ShippingTicketNo());
				
				//#CM687703
				// Ticket Line No. (mandatory): Length
				txt_TktLineNoReqLen.setText(param[0].getFigure_ShippingLineNo());
				//#CM687704
				// Ticket Line No. (mandatory): Max Length
				lbl_MaxLenTktLineNoReq.setText(param[0].getMaxFigure_ShippingLineNo());
				//#CM687705
				// Ticket Line No. (mandatory): Position
				txt_TktLineNoReqPst.setText(param[0].getPosition_ShippingLineNo());

				//#CM687706
				// Item Code (mandatory): Length
				txt_ItemCdReqLen.setText(param[0].getFigure_ItemCode());
				//#CM687707
				// Item Code (mandatory): Max Length
				lbl_MaxLenItemCdReq.setText(param[0].getMaxFigure_ItemCode());
				//#CM687708
				// Item Code (mandatory): Position
				txt_ItemCdReqPst.setText(param[0].getPosition_ItemCode());
				
				//#CM687709
				// Bundle ITF: Enabled
				chk_BundleItf.setChecked(param[0].getValid_BundleItf());
				//#CM687710
				// Bundle ITF: Length
				txt_BundleItfLen.setText(param[0].getFigure_BundleItf());
				//#CM687711
				// Bundle ITF: Max Length
				lbl_MaxLenBundleItf.setText(param[0].getMaxFigure_BundleItf());
				//#CM687712
				// Bundle ITF: Position
				txt_BundleItfPst.setText(param[0].getPosition_BundleItf());
				
				//#CM687713
				// Case ITF: Enabled
				chk_CaseItf.setChecked(param[0].getValid_Itf());
				//#CM687714
				// Case ITF: Length
				txt_CaseItfLen.setText(param[0].getFigure_Itf());
				//#CM687715
				// Case ITF: Max Length
				lbl_MaxLenCaseItf.setText(param[0].getMaxFigure_Itf());
				//#CM687716
				// Case ITF: Position
				txt_CaseItfPst.setText(param[0].getPosition_Itf());
				
				//#CM687717
				// Packed qty per bundle: Enabled
				chk_BundleEtr.setChecked(param[0].getValid_BundleEnteringQty());
				//#CM687718
				// Packed qty per bundle: Length
				txt_BundleEtrLen.setText(param[0].getFigure_BundleEnteringQty());
				//#CM687719
				// Packed qty per bundle: Max Length
				lbl_MaxLenBundleEtr.setText(param[0].getMaxFigure_BundleEnteringQty());
				//#CM687720
				// Packed qty per bundle: Position
				txt_BundleEtrPst.setText(param[0].getPosition_BundleEnteringQty());
				
				//#CM687721
				// Packed Qty per Case (mandatory): Length
				txt_CaseEtrReqLen.setText(param[0].getFigure_EnteringQty());
				//#CM687722
				// Packed Qty per Case (mandatory): Max Length
				lbl_MaxLenCaseEtrReq.setText(param[0].getMaxFigure_EnteringQty());
				//#CM687723
				// Packed Qty per Case (mandatory): Position
				txt_CaseEtrReqPst.setText(param[0].getPosition_EnteringQty());

				//#CM687724
				// Item Name: Enabled
				chk_NItemNm.setChecked(param[0].getValid_ItemName());
				//#CM687725
				// Item Name: Length
				txt_ItemNmLen.setText(param[0].getFigure_ItemName());
				//#CM687726
				// Item Name: Max Length
				lbl_MaxLenItemNm.setText(param[0].getMaxFigure_ItemName());
				//#CM687727
				// Item Name: Position
				txt_ItemNmPst.setText(param[0].getPosition_ItemName());
				
				//#CM687728
				// Receiving Qty (Total Bulk Qty) (mandatory): Length
				txt_InstkQtyPtlLenReq.setText(param[0].getFigure_PlanQty());
				//#CM687729
				// Receiving Qty (Total Bulk Qty) (mandatory): Max Length
				lbl_MaxLenInstkQtyPtlReq.setText(param[0].getMaxFigure_PlanQty());
				//#CM687730
				// Receiving Qty (Total Bulk Qty) (mandatory): Position
				txt_InstkQtyPtlReqPst.setText(param[0].getPosition_PlanQty());
				
				//#CM687731
				// TC/DC division: Length
				txt_TCDCFlgReqLen.setText(param[0].getFigure_TcDcFlag());
				//#CM687732
				// TC/DC division: Max Length
				lbl_MaxLenTCDCFlgReq.setText(param[0].getMaxFigure_TcDcFlag());
				//#CM687733
				// TC/DC division: Position
				txt_TCDCFlgReqPst.setText(param[0].getPosition_TcDcFlag());

				//#CM687734
				// (Note))Customer Code: Enabled
				chk_CustCdAst.setChecked(param[0].getValid_CustomerCode());
				//#CM687735
				// (Note))Customer Code: Length
				txt_CustCdAstLen.setText(param[0].getFigure_CustomerCode());
				//#CM687736
				// (Note))Customer Code: Max Length
				lbl_MaxLenCustCdAst.setText(param[0].getMaxFigure_CustomerCode());
				//#CM687737
				// (Note))Customer Code: Position
				txt_CustCdAstPst.setText(param[0].getPosition_CustomerCode());
				
				//#CM687738
				// Customer Name: Enabled
				chk_CustNmAst.setChecked(param[0].getValid_CustomerName());
				//#CM687739
				// Customer Name: Length
				txt_CustNmAstLen.setText(param[0].getFigure_CustomerName());
				//#CM687740
				// Customer Name: Max Length
				lbl_MaxLenCustNmAst.setText(param[0].getMaxFigure_CustomerName());
				//#CM687741
				// Customer Name: Position
				txt_CustNmAstPst.setText(param[0].getPosition_CustomerName());
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
				//#CM687742
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
	
	//#CM687743
	/** 
	 * Allow this method to check whether a checkbox is ticked off or not.<BR>
	 * <BR>
	 * @return Return false if no item ticked off.
	 * @throws Exception Report all exceptions.
	 */
	private boolean checkCheckBox() throws Exception
	{
		if (!chk_NInstockPlanDtReq.getChecked()
			&& !chk_OrderDate.getChecked()
			&& !chk_CnsgnrCd.getChecked()
			&& !chk_CnsgnrNm.getChecked()
			&& !chk_NSupplierCdReq.getChecked()
			&& !chk_NSupplierCdReq.getChecked()
			&& !chk_NTktNoReq.getChecked()
			&& !chk_NTktLineNoReq.getChecked()
			&& !chk_NItemCdReq.getChecked()
			&& !chk_BundleItf.getChecked()
			&& !chk_CaseItf.getChecked()
			&& !chk_BundleEtr.getChecked()
			&& !chk_NCaseEtrReq.getChecked()
			&& !chk_NItemNm.getChecked()
			&& !chk_NInstkQtyPtlReq.getChecked()
			&& !chk_NTCDCFlgReq.getChecked()
			&& !chk_CustCdAst.getChecked()
			&& !chk_CustNmAst.getChecked() )
		{
			return false;
		}
		return true;
	}

	//#CM687744
	// Event handler methods -----------------------------------------
	//#CM687745
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
		//#CM687746
		// Shift Screen
		forward("/system/defineloaddatamenu/DefineLoadDataMenu.do");	
	}

	//#CM687747
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

	//#CM687748
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
	 *     4.Set the cursor to Planned Receiving Date (mandatory) Length. <BR>
	 *   </DIR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		//#CM687749
		// Set the initial value of the focus to Planned Receiving Date (mandatory) Length.
		setFocus(txt_InstkPlanDtReqPst);

		//#CM687750
		// Check for input.
		WmsCheckker.checkDefine(lbl_InstkPlanDateReq, chk_NInstkQtyPtlReq, txt_InstkPlanDtReqLen, lbl_MaxLenInstkPlanDtReq, txt_InstkPlanDtReqPst);
		WmsCheckker.checkDefine(lbl_OrderDate, chk_OrderDate, txt_OrderDateLen, lbl_MaxLenOrderDate, txt_OrderDatePst);
		WmsCheckker.checkDefine(lbl_ConsignorCode, chk_CnsgnrCd, txt_CnsgnrCdLen, lbl_MaxLenCnsgnrCd, txt_CnsgnrCdPst);
		WmsCheckker.checkDefine(lbl_ConsignorName, chk_CnsgnrNm, txt_CnsgnrNmLen, lbl_MaxLenCnsgnrNm, txt_CnsgnrNmPst);
		WmsCheckker.checkDefine(lbl_SplCdReq, chk_NSupplierCdReq, txt_SupplierCdReqLen, lbl_MaxLenSupplierCdReq, txt_SupplierCdReqPst);
		WmsCheckker.checkDefine(lbl_SplName, chk_NSupplierNm, txt_SupplierNmLen, lbl_MaxLenSupplierNm, txt_SupplierNmPst);
		WmsCheckker.checkDefine(lbl_TicketNoRequired, chk_NTktNoReq, txt_TktNoReqLen, lbl_MaxLenTktNoReq, txt_TktNoReqPst);			
		WmsCheckker.checkDefine(lbl_TktLineNoReq, chk_NTktLineNoReq, txt_TktLineNoReqLen, lbl_MaxLenTktLineNoReq, txt_TktLineNoReqPst);	
		WmsCheckker.checkDefine(lbl_ItemCodeRequired, chk_NItemCdReq, txt_ItemCdReqLen, lbl_MaxLenItemCdReq, txt_ItemCdReqPst);
		WmsCheckker.checkDefine(lbl_BundleItf, chk_BundleItf, txt_BundleItfLen, lbl_MaxLenBundleItf, txt_BundleItfPst);
		WmsCheckker.checkDefine(lbl_CaseItf, chk_CaseItf, txt_CaseItfLen, lbl_MaxLenCaseItf, txt_CaseItfPst);
		WmsCheckker.checkDefine(lbl_BundleEntering, chk_BundleEtr, txt_BundleEtrLen, lbl_MaxLenBundleEtr, txt_BundleEtrPst);
		WmsCheckker.checkDefine(lbl_CaseEtrReq, chk_NCaseEtrReq, txt_CaseEtrReqLen, lbl_MaxLenCaseEtrReq, txt_CaseEtrReqPst);
		WmsCheckker.checkDefine(lbl_ItemName, chk_NItemNm, txt_ItemNmLen, lbl_MaxLenItemNm, txt_ItemNmPst);
		WmsCheckker.checkDefine(lbl_InstkQtyPtlReq, chk_NInstkQtyPtlReq, txt_InstkQtyPtlLenReq, lbl_MaxLenInstkQtyPtlReq, txt_InstkQtyPtlReqPst);
		WmsCheckker.checkDefine(lbl_TCDCRequired, chk_NTCDCFlgReq, txt_TCDCFlgReqLen, lbl_MaxLenTCDCFlgReq, txt_TCDCFlgReqPst);
		WmsCheckker.checkDefine(lbl_CustomerCodeAst, chk_CustCdAst, txt_CustCdAstLen, lbl_MaxLenCustCdAst, txt_CustCdAstPst);
		WmsCheckker.checkDefine(lbl_CustomerName, chk_CustNmAst, txt_CustNmAstLen, lbl_MaxLenCustNmAst, txt_CustNmAstPst);

		//#CM687751
		// Check the checkbox.
		if (!checkCheckBox())
		{
			//#CM687752
			// 6023174=Please select 1 or more items.
			message.setMsgResourceKey("6023174");
			return;
		}

		Connection conn = null;
		try
		{
			DefineDataParameter[] param = new DefineDataParameter[1];
			param[0] = new DefineDataParameter();
			
			//#CM687753
			// Set the value for the parameter.
			param[0].setSelectDefineLoadData(DefineDataParameter.SELECTDEFINELOADDATA_INSTOCK);
			
			//#CM687754
			// Planned Receiving Date (mandatory): Enabled
			param[0].setValid_PlanDate(chk_NInstockPlanDtReq.getChecked());
			//#CM687755
			// Planned Receiving Date (mandatory): Length
			param[0].setFigure_PlanDate(txt_InstkPlanDtReqLen.getText());
			//#CM687756
			// Planned Receiving Date (mandatory): Max Length
			param[0].setMaxFigure_PlanDate(lbl_MaxLenInstkPlanDtReq.getText());
			//#CM687757
			// Planned Receiving Date (mandatory): Position
			param[0].setPosition_PlanDate(txt_InstkPlanDtReqPst.getText());
			
			//#CM687758
			// Order Date: Enabled
			param[0].setValid_OrderingDate(chk_OrderDate.getChecked());
			//#CM687759
			// Order Date: Length
			param[0].setFigure_OrderingDate(txt_OrderDateLen.getText());
			//#CM687760
			// Order Date: Max Length
			param[0].setMaxFigure_OrderingDate(lbl_MaxLenOrderDate.getText());
			//#CM687761
			// Order Date: Position
			param[0].setPosition_OrderingDate(txt_OrderDatePst.getText());
			
			//#CM687762
			// Consignor Code: Enabled
			param[0].setValid_ConsignorCode(chk_CnsgnrCd.getChecked());
			//#CM687763
			// Consignor Code: Length
			param[0].setFigure_ConsignorCode(txt_CnsgnrCdLen.getText());
			//#CM687764
			// Consignor Code: Max Length
			param[0].setMaxFigure_ConsignorCode(lbl_MaxLenCnsgnrCd.getText());
			//#CM687765
			// Consignor Code: Position
			param[0].setPosition_ConsignorCode(txt_CnsgnrCdPst.getText());
			
			//#CM687766
			// Consignor Name: Enabled
			param[0].setValid_ConsignorName(chk_CnsgnrNm.getChecked());
			//#CM687767
			// Consignor Name: Length
			param[0].setFigure_ConsignorName(txt_CnsgnrNmLen.getText());
			//#CM687768
			// Consignor Name: Max Length
			param[0].setMaxFigure_ConsignorName(lbl_MaxLenCnsgnrNm.getText());
			//#CM687769
			// Consignor Name: Position
			param[0].setPosition_ConsignorName(txt_CnsgnrNmPst.getText());
			
			//#CM687770
			// Supplier Code (mandatory): Enabled
			param[0].setValid_SupplierCode(chk_NSupplierCdReq.getChecked());
			//#CM687771
			// Supplier Code (mandatory): Length
			param[0].setFigure_SupplierCode(txt_SupplierCdReqLen.getText());
			//#CM687772
			// Supplier Code (mandatory): Max Length
			param[0].setMaxFigure_SupplierCode(lbl_MaxLenSupplierCdReq.getText());
			//#CM687773
			// Supplier Code (mandatory): Position
			param[0].setPosition_SupplierCode(txt_SupplierCdReqPst.getText());

			//#CM687774
			// Supplier Name: Enabled
			param[0].setValid_SupplierName(chk_NSupplierNm.getChecked());
			//#CM687775
			// Supplier Name: Length
			param[0].setFigure_SupplierName(txt_SupplierNmLen.getText());
			//#CM687776
			// Supplier Name: Max Length
			param[0].setMaxFigure_SupplierName(lbl_MaxLenSupplierNm.getText());
			//#CM687777
			// Supplier Name: Position
			param[0].setPosition_SupplierName(txt_SupplierNmPst.getText());
			
			//#CM687778
			// Ticket No. (mandatory): Enabled
			param[0].setValid_ShippingTicketNo(chk_NTktNoReq.getChecked());
			//#CM687779
			// Ticket No. (mandatory): Length
			param[0].setFigure_ShippingTicketNo(txt_TktNoReqLen.getText());
			//#CM687780
			// Ticket No. (mandatory): Max Length
			param[0].setMaxFigure_ShippingTicketNo(lbl_MaxLenTktNoReq.getText());
			//#CM687781
			// Ticket No. (mandatory): Position
			param[0].setPosition_ShippingTicketNo(txt_TktNoReqPst.getText());
			
			//#CM687782
			// Ticket Line No. (mandatory): Enabled
			param[0].setValid_ShippingLineNo(chk_NTktLineNoReq.getChecked());
			//#CM687783
			// Ticket Line No. (mandatory): Length
			param[0].setFigure_ShippingLineNo(txt_TktLineNoReqLen.getText());
			//#CM687784
			// Ticket Line No. (mandatory): Max Length
			param[0].setMaxFigure_ShippingLineNo(lbl_MaxLenTktLineNoReq.getText());
			//#CM687785
			// Ticket Line No. (mandatory): Position
			param[0].setPosition_ShippingLineNo(txt_TktLineNoReqPst.getText());
			
			//#CM687786
			// Item Code (mandatory): Enabled
			param[0].setValid_ItemCode(chk_NItemCdReq.getChecked());
			//#CM687787
			// Item Code (mandatory): Length
			param[0].setFigure_ItemCode(txt_ItemCdReqLen.getText());
			//#CM687788
			// Item Code (mandatory): Max Length
			param[0].setMaxFigure_ItemCode(lbl_MaxLenItemCdReq.getText());
			//#CM687789
			// Item Code (mandatory): Position
			param[0].setPosition_ItemCode(txt_ItemCdReqPst.getText());
			
			//#CM687790
			// Bundle ITF: Enabled
			param[0].setValid_BundleItf(chk_BundleItf.getChecked());
			//#CM687791
			// Bundle ITF: Length
			param[0].setFigure_BundleItf(txt_BundleItfLen.getText());
			//#CM687792
			// Bundle ITF: Max Length
			param[0].setMaxFigure_BundleItf(lbl_MaxLenBundleItf.getText());
			//#CM687793
			// Bundle ITF: Position
			param[0].setPosition_BundleItf(txt_BundleItfPst.getText());
			
			//#CM687794
			// Case ITF: Enabled
			param[0].setValid_Itf(chk_CaseItf.getChecked());
			//#CM687795
			// Case ITF: Length
			param[0].setFigure_Itf(txt_CaseItfLen.getText());
			//#CM687796
			// Case ITF: Max Length
			param[0].setMaxFigure_Itf(lbl_MaxLenCaseItf.getText());
			//#CM687797
			// Case ITF: Position
			param[0].setPosition_Itf(txt_CaseItfPst.getText());
			
			//#CM687798
			// Packed qty per bundle: Enabled
			param[0].setValid_BundleEnteringQty(chk_BundleEtr.getChecked());
			//#CM687799
			// Packed qty per bundle: Length
			param[0].setFigure_BundleEnteringQty(txt_BundleEtrLen.getText());
			//#CM687800
			// Packed qty per bundle: Max Length
			param[0].setMaxFigure_BundleEnteringQty(lbl_MaxLenBundleEtr.getText());
			//#CM687801
			// Packed qty per bundle: Position
			param[0].setPosition_BundleEnteringQty(txt_BundleEtrPst.getText());
			
			//#CM687802
			// Packed Qty per Case (mandatory): Enabled
			param[0].setValid_EnteringQty(chk_NCaseEtrReq.getChecked());
			//#CM687803
			// Packed Qty per Case (mandatory): Length
			param[0].setFigure_EnteringQty(txt_CaseEtrReqLen.getText());
			//#CM687804
			// Packed Qty per Case (mandatory): Max Length
			param[0].setMaxFigure_EnteringQty(lbl_MaxLenCaseEtrReq.getText());
			//#CM687805
			// Packed Qty per Case (mandatory): Position
			param[0].setPosition_EnteringQty(txt_CaseEtrReqPst.getText());

			//#CM687806
			// Item Name: Enabled
			param[0].setValid_ItemName(chk_NItemNm.getChecked());
			//#CM687807
			// Item Name: Length
			param[0].setFigure_ItemName(txt_ItemNmLen.getText());
			//#CM687808
			// Item Name: Max Length
			param[0].setMaxFigure_ItemName(lbl_MaxLenItemNm.getText());
			//#CM687809
			// Item Name: Position
			param[0].setPosition_ItemName(txt_ItemNmPst.getText());
			
			//#CM687810
			// Receiving Qty (Total Bulk Qty) (mandatory): Enabled
			param[0].setValid_PlanQty(chk_NInstkQtyPtlReq.getChecked());
			//#CM687811
			// Receiving Qty (Total Bulk Qty) (mandatory): Length
			param[0].setFigure_PlanQty(txt_InstkQtyPtlLenReq.getText());
			//#CM687812
			// Receiving Qty (Total Bulk Qty) (mandatory): Max Length
			param[0].setMaxFigure_PlanQty(lbl_MaxLenInstkQtyPtlReq.getText());
			//#CM687813
			// Receiving Qty (Total Bulk Qty) (mandatory): Position
			param[0].setPosition_PlanQty(txt_InstkQtyPtlReqPst.getText());
			
			//#CM687814
			// TC/DC division: Enabled
			param[0].setValid_TcDcFlag(chk_NTCDCFlgReq.getChecked());
			//#CM687815
			// TC/DC division: Length
			param[0].setFigure_TcDcFlag(txt_TCDCFlgReqLen.getText());
			//#CM687816
			// TC/DC division: Max Length
			param[0].setMaxFigure_TcDcFlag(lbl_MaxLenTCDCFlgReq.getText());
			//#CM687817
			// TC/DC division: Position
			param[0].setPosition_TcDcFlag(txt_TCDCFlgReqPst.getText());
			
			//#CM687818
			// (Note))Customer Code: Enabled
			param[0].setValid_CustomerCode(chk_CustCdAst.getChecked());
			//#CM687819
			// (Note))Customer Code: Length
			param[0].setFigure_CustomerCode(txt_CustCdAstLen.getText());
			//#CM687820
			// (Note))Customer Code: Max Length
			param[0].setMaxFigure_CustomerCode(lbl_MaxLenCustCdAst.getText());
			//#CM687821
			// (Note))Customer Code: Position
			param[0].setPosition_CustomerCode(txt_CustCdAstPst.getText());
			
			//#CM687822
			// Customer Name: Enabled
			param[0].setValid_CustomerName(chk_CustNmAst.getChecked());
			//#CM687823
			// Customer Name: Length
			param[0].setFigure_CustomerName(txt_CustNmAstLen.getText());
			//#CM687824
			// Customer Name: Max Length
			param[0].setMaxFigure_CustomerName(lbl_MaxLenCustNmAst.getText());
			//#CM687825
			// Customer Name: Position
			param[0].setPosition_CustomerName(txt_CustNmAstPst.getText());
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			WmsScheduler schedule = new DefineLoadDataMenuSCH();
			
			//#CM687826
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
				//#CM687827
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
				//#CM687828
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

	//#CM687829
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
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		dataLoad();
	}


}
//#CM687830
//end of class
