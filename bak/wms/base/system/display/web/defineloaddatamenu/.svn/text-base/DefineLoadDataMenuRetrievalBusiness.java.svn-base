// $Id: DefineLoadDataMenuRetrievalBusiness.java,v 1.2 2006/11/13 08:18:27 suresh Exp $

//#CM687835
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

//#CM687836
/**
 * Designer : T.Uehata <BR>
 * Maker : T.Kuroda <BR>
 * <BR>
 * Allow this class to provide a screen to set a field item for loading data (to set the Picking Plan field). <BR>
 * Set the contents input via input area for the parameter and pass it to the schedule. Add the setting of Picking Plan field. <BR>
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
 *   Planned Picking Date	(mandatory)*<BR>
 *   Item Code	(mandatory)*<BR>
 *   Packed Qty per Case	(mandatory)*<BR>
 *   Picking Qty (Total Bulk Qty) (mandatory)*<BR>
 * </DIR>
 * <BR>
 *   [Parameter]+ Require to input if the parameter is ticked to Enabled <BR>
 *   Length, Max Length, and Position of each field item<BR>
 * <BR>
 * <DIR>
 *   Consignor Code +<BR>
 *   Consignor Name+<BR>
 * 	 Customer Code+<BR>
 * 	 Customer Name+<BR>
 * 	 Ticket No.+<BR>
 * 	 Ticket Line No.+<BR>
 *   Bundle ITF+<BR>
 *   Case ITF+<BR>
 *   Packed qty per bundle+<BR>
 *   Item Name+<BR>
 * 	 Piece Picking Location+<BR>
 * 	 Case Picking Location+<BR>
 * 	 (Note))Piece Order No.+<BR>
 * 	 (Note))Case Order No.+<BR>
 * </DIR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/04</TD><TD>T.Kuroda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:27 $
 * @author  $Author: suresh $
 */
public class DefineLoadDataMenuRetrievalBusiness extends DefineLoadDataMenuRetrieval implements WMSConstants
{
	//#CM687837
	// Class fields --------------------------------------------------

	//#CM687838
	// Class variables -----------------------------------------------

	//#CM687839
	// Class method --------------------------------------------------

	//#CM687840
	// Constructors --------------------------------------------------

	//#CM687841
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
			//#CM687842
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM687843
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM687844
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM687845
			//Set the path to the help file.
			//#CM687846
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM687847
		// MSG-0009= Do you add it?
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}
	
	//#CM687848
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Set the cursor to Planned Picking Date (mandatory) Position. <BR>
	 * <DIR>
	 *   Field item [Initial Value] <BR>
	 *   <DIR>
	 *     Planned Picking Date      	(mandatory) Enabled [Ticked] <BR>
	 *     Item Code      	(mandatory) Enabled [Ticked] <BR>
	 *     Packed Qty per Case      	(mandatory) Enabled [Ticked] <BR>
	 *     Shipping Qty (Total Bulk Qty)	(mandatory) Enabled [Ticked] <BR>
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
		//#CM687849
		// Title
		lbl_SettingName.setResourceKey(this.getViewState().getString("TITLE"));

		dataLoad();
	}

	//#CM687850
	// Package methods -----------------------------------------------

	//#CM687851
	// Protected methods ---------------------------------------------

	//#CM687852
	// Private methods -----------------------------------------------
	//#CM687853
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
	 *   2.Set the cursor to Planned Picking Date (mandatory) Position. <BR>
	 * </DIR>
	 * @throws Exception Report all exceptions.
	 */
	private void dataLoad() throws Exception
	{
		//#CM687854
		// Set the initial value of the focus to Planned Picking Date (mandatory) Position.
		setFocus(txt_RetrivlPnDtReqPst);

		Connection conn = null;
		try
		{
			//#CM687855
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter initParam = new DefineDataParameter();
			initParam.setSelectDefineLoadData(DefineDataParameter.SELECTDEFINELOADDATA_RETRIEVAL);
			WmsScheduler schedule = new DefineLoadDataMenuSCH();
			DefineDataParameter[] param = (DefineDataParameter[]) schedule.query(conn, initParam);

			if (param != null)
			{
				//#CM687856
				// Planned Picking Date (mandatory): Length
				txt_RetrivlPnDtReqLen.setText(param[0].getFigure_PlanDate());
				//#CM687857
				// Planned Picking Date (mandatory): Max Length
				lbl_MaxLenRetrivlPlanDateReq.setText(param[0].getMaxFigure_PlanDate());
				//#CM687858
				// Planned Picking Date (mandatory): Position
				txt_RetrivlPnDtReqPst.setText(param[0].getPosition_PlanDate());

				//#CM687859
				// Consignor Code: Enabled
				chk_ConsignorCode.setChecked(param[0].getValid_ConsignorCode());
				//#CM687860
				// Consignor Code: Length
				txt_CnsgnrCdLen.setText(param[0].getFigure_ConsignorCode());
				//#CM687861
				// Consignor Code: Max Length
				lbl_MaxLenConsignorCode.setText(param[0].getMaxFigure_ConsignorCode());
				//#CM687862
				// Consignor Code: Position
				txt_CnsgnrCdPst.setText(param[0].getPosition_ConsignorCode());
				
				//#CM687863
				// Consignor Name: Enabled
				chk_ConsignorName.setChecked(param[0].getValid_ConsignorName());
				//#CM687864
				// Consignor Name: Length
				txt_CnsgnrNmLen.setText(param[0].getFigure_ConsignorName());
				//#CM687865
				// Consignor Name: Max Length
				lbl_MaxLenConsignorName.setText(param[0].getMaxFigure_ConsignorName());
				//#CM687866
				// Consignor Name: Position
				txt_CnsgnrNmPst.setText(param[0].getPosition_ConsignorName());
				
				//#CM687867
				// Customer Code: Enabled
				chk_CustomerCode.setChecked(param[0].getValid_CustomerCode());
				//#CM687868
				// Customer Code: Length
				txt_CustCdLen.setText(param[0].getFigure_CustomerCode());
				//#CM687869
				// Customer Code: Max Length
				lbl_MaxLenCustomerCode.setText(param[0].getMaxFigure_CustomerCode());
				//#CM687870
				// Customer Code: Position
				txt_CustCdPst.setText(param[0].getPosition_CustomerCode());
				
				//#CM687871
				// Customer Name: Enabled
				chk_CustomerName.setChecked(param[0].getValid_CustomerName());
				//#CM687872
				// Customer Name: Length
				txt_CustNmLen.setText(param[0].getFigure_CustomerName());
				//#CM687873
				// Customer Name: Max Length
				lbl_MaxLenCustomerName.setText(param[0].getMaxFigure_CustomerName());
				//#CM687874
				// Customer Name: Position
				txt_CustNmPst.setText(param[0].getPosition_CustomerName());
				
				//#CM687875
				// Ticket No.: Enabled
				chk_TicketNo.setChecked(param[0].getValid_ShippingTicketNo());
				//#CM687876
				// Ticket No.: Length
				txt_TicketNoLength.setText(param[0].getFigure_ShippingTicketNo());
				//#CM687877
				// Ticket No.: Max Length
				lbl_MaxLenTicketNo.setText(param[0].getMaxFigure_ShippingTicketNo());
				//#CM687878
				// Ticket No.: Position
				txt_TicketNoPosition.setText(param[0].getPosition_ShippingTicketNo());
				
				//#CM687879
				// Ticket Line No.: Enabled
				chk_TicketLineNo.setChecked(param[0].getValid_ShippingLineNo());
				//#CM687880
				// Ticket Line No.: Length
				txt_TktLineNoLen.setText(param[0].getFigure_ShippingLineNo());
				//#CM687881
				// Ticket Line No.: Max Length
				lbl_MaxLenTicketLineNo.setText(param[0].getMaxFigure_ShippingLineNo());
				//#CM687882
				// Ticket Line No.: Position
				txt_TktLineNoPst.setText(param[0].getPosition_ShippingLineNo());
				
				//#CM687883
				// Item Code (mandatory): Length
				txt_ItemCdReqLen.setText(param[0].getFigure_ItemCode());
				//#CM687884
				// Item Code (mandatory): Max Length
				lbl_MaxLenItemCodeReq.setText(param[0].getMaxFigure_ItemCode());
				//#CM687885
				// Item Code (mandatory): Position
				txt_ItemCdReqPst.setText(param[0].getPosition_ItemCode());
				
				//#CM687886
				// Bundle ITF: Enabled
				chk_BundleItf.setChecked(param[0].getValid_BundleItf());
				//#CM687887
				// Bundle ITF: Length
				txt_BundleItfLength.setText(param[0].getFigure_BundleItf());
				//#CM687888
				// Bundle ITF: Max Length
				lbl_MaxLenBundleItf.setText(param[0].getMaxFigure_BundleItf());
				//#CM687889
				// Bundle ITF: Position
				txt_BundleItfPosition.setText(param[0].getPosition_BundleItf());
				
				//#CM687890
				// Case ITF: Enabled
				chk_CaseItf.setChecked(param[0].getValid_Itf());
				//#CM687891
				// Case ITF: Length
				txt_CaseItfLength.setText(param[0].getFigure_Itf());
				//#CM687892
				// Case ITF: Max Length
				lbl_MaxLenCaseItf.setText(param[0].getMaxFigure_Itf());
				//#CM687893
				// Case ITF: Position
				txt_CaseItfPosition.setText(param[0].getPosition_Itf());
				
				//#CM687894
				// Packed qty per bundle: Enabled
				chk_BundleEntering.setChecked(param[0].getValid_BundleEnteringQty());
				//#CM687895
				// Packed qty per bundle: Length
				txt_BdlEtrLen.setText(param[0].getFigure_BundleEnteringQty());
				//#CM687896
				// Packed qty per bundle: Max Length
				lbl_MaxLenBundleEntering.setText(param[0].getMaxFigure_BundleEnteringQty());
				//#CM687897
				// Packed qty per bundle: Position
				txt_BdlEtrPst.setText(param[0].getPosition_BundleEnteringQty());
				
				//#CM687898
				// Packed Qty per Case (mandatory): Length
				txt_CaseEtrReqLen.setText(param[0].getFigure_EnteringQty());
				//#CM687899
				// Packed Qty per Case (mandatory): Max Length
				lbl_MaxLenCaseEtrReq.setText(param[0].getMaxFigure_EnteringQty());
				//#CM687900
				// Packed Qty per Case (mandatory): Position
				txt_CaseEtrReqPst.setText(param[0].getPosition_EnteringQty());

				//#CM687901
				// Item Name: Enabled
				chk_NItemName.setChecked(param[0].getValid_ItemName());
				//#CM687902
				// Item Name: Length
				txt_ItemNmLen.setText(param[0].getFigure_ItemName());
				//#CM687903
				// Item Name: Max Length
				lbl_MaxLenItemName.setText(param[0].getMaxFigure_ItemName());
				//#CM687904
				// Item Name: Position
				txt_ItemNmPst.setText(param[0].getPosition_ItemName());
				
				//#CM687905
				// Picking Qty (Total Bulk Qty) (mandatory): Length
				txt_RtrvlQtyPtlReqLen.setText(param[0].getFigure_PlanQty());
				//#CM687906
				// Picking Qty (Total Bulk Qty) (mandatory): Max Length
				lbl_MaxLenRtrvlQtyPtlReq.setText(param[0].getMaxFigure_PlanQty());
				//#CM687907
				// Picking Qty (Total Bulk Qty) (mandatory): Position
				txt_RtrvlQtyPtlReqPst.setText(param[0].getPosition_PlanQty());
				
				//#CM687908
				// Piece Picking Location: Enabled
				chk_PiceRetrvlLct.setChecked(param[0].getValid_PieceLocation());
				//#CM687909
				// Piece Picking Location: Length
				txt_PiceRetrivlLctLen.setText(param[0].getFigure_PieceLocation());
				//#CM687910
				// Piece Picking Location: Max Length
				lbl_MaxLenPieceRetrivlLct.setText(param[0].getMaxFigure_PieceLocation());
				//#CM687911
				// Piece Picking: Length,: Position
				txt_PiceRtrivLctPst.setText(param[0].getPosition_PieceLocation());
				
				//#CM687912
				// Case Picking Location: Enabled
				chk_CaseRtrivlLct.setChecked(param[0].getValid_CaseLocation());
				//#CM687913
				// Case Picking Location: Length
				txt_CaseRtrivlLctLen.setText(param[0].getFigure_CaseLocation());
				//#CM687914
				// Case Picking Location: Max Length
				lbl_MaxLenCaseRtrivlLct.setText(param[0].getMaxFigure_CaseLocation());
				//#CM687915
				// Case Picking Location: Position
				txt_CaseRtrivlLctPst.setText(param[0].getPosition_CaseLocation());
				
				//#CM687916
				// (Note))Piece Order No.: Enabled
				chk_PiceOdrNoAst.setChecked(param[0].getValid_PieceOrderNo());
				//#CM687917
				// (Note))Piece Order No.: Length
				txt_PiceOdrNoAstLen.setText(param[0].getFigure_PieceOrderNo());
				//#CM687918
				// (Note))Piece Order No.: Max Length
				lbl_MaxLenPiceOdrNoAst.setText(param[0].getMaxFigure_PieceOrderNo());
				//#CM687919
				// (Note))Piece Order No.: Position
				txt_PiceOdrNoAstPst.setText(param[0].getPosition_PieceOrderNo());
				
				//#CM687920
				// (Note))Case Order No.: Enabled
				chk_CaseOdrNoAst.setChecked(param[0].getValid_CaseOrderNo());
				//#CM687921
				// (Note))Case Order No.: Length
				txt_CaseOdrNoAstLen.setText(param[0].getFigure_CaseOrderNo());
				//#CM687922
				// (Note))Case Order No.: Max Length
				lbl_MaxLenCaseOdrNoAst.setText(param[0].getMaxFigure_CaseOrderNo());
				//#CM687923
				// (Note))Case Order No.: Position
				txt_CaseOdrNoAstPst.setText(param[0].getPosition_CaseOrderNo());
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
				//#CM687924
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

	//#CM687925
	/** 
	 * Allow this method to check whether a checkbox is ticked off or not.<BR>
	 * <BR>
	 * @return Return false if no item ticked off.
	 * @throws Exception Report all exceptions.
	 */
	private boolean checkCheckBox() throws Exception
	{
		if (!chk_NRetrivlPnDtReq.getChecked()
				&& !chk_ConsignorCode.getChecked()
				&& !chk_ConsignorName.getChecked()
				&& !chk_CustomerCode.getChecked()
				&& !chk_CustomerName.getChecked()
				&& !chk_TicketNo.getChecked()
				&& !chk_TicketLineNo.getChecked()
				&& !chk_NItemCodeReq.getChecked()
				&& !chk_BundleItf.getChecked()
				&& !chk_CaseItf.getChecked()
				&& !chk_BundleEntering.getChecked()
				&& !chk_NCaseEtrReq.getChecked()
				&& !chk_NItemName.getChecked()
				&& !chk_NRtrvlQtyPtlReq.getChecked()
				&& !chk_PiceRetrvlLct.getChecked()
				&& !chk_CaseRtrivlLct.getChecked()
				&& !chk_PiceOdrNoAst.getChecked()
				&& !chk_CaseOdrNoAst.getChecked()
			)
		{
			return false;
		}
		return true;
	}
	
	//#CM687926
	// Event handler methods -----------------------------------------
	//#CM687927
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
		//#CM687928
		// Shift Screen
		forward("/system/defineloaddatamenu/DefineLoadDataMenu.do");
	}

	//#CM687929
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

	//#CM687930
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
		//#CM687931
		// Set the initial value of the focus to Planned Picking Date (mandatory) Length.
		setFocus(txt_RetrivlPnDtReqPst);

		//#CM687932
		// Check for input.
		WmsCheckker.checkDefine(lbl_RetrivlPlanDateReq, chk_NRetrivlPnDtReq, txt_RetrivlPnDtReqLen, lbl_MaxLenRetrivlPlanDateReq, txt_RetrivlPnDtReqPst);
		WmsCheckker.checkDefine(lbl_ConsignorCode, chk_ConsignorCode, txt_CnsgnrCdLen, lbl_MaxLenConsignorCode, txt_CnsgnrCdPst);
		WmsCheckker.checkDefine(lbl_ConsignorName, chk_ConsignorName, txt_CnsgnrNmLen, lbl_MaxLenConsignorName, txt_CnsgnrNmPst);
		WmsCheckker.checkDefine(lbl_CustomerCode, chk_CustomerCode, txt_CustCdLen, lbl_MaxLenCustomerCode, txt_CustCdPst);
		WmsCheckker.checkDefine(lbl_CustomerName, chk_CustomerName, txt_CustNmLen, lbl_MaxLenCustomerName, txt_CustNmPst);
		WmsCheckker.checkDefine(lbl_TicketNo, chk_TicketNo, txt_TicketNoLength, lbl_MaxLenTicketNo, txt_TicketNoPosition);			
		WmsCheckker.checkDefine(lbl_TicketLineNo, chk_TicketLineNo, txt_TktLineNoLen, lbl_MaxLenTicketLineNo, txt_TktLineNoPst);	
		WmsCheckker.checkDefine(lbl_ItemCodeRequired, chk_NItemCodeReq, txt_ItemCdReqLen, lbl_MaxLenItemCodeReq, txt_ItemCdReqPst);
		WmsCheckker.checkDefine(lbl_BundleItf, chk_BundleItf, txt_BundleItfLength, lbl_MaxLenBundleItf, txt_BundleItfPosition);
		WmsCheckker.checkDefine(lbl_CaseItf, chk_CaseItf, txt_CaseItfLength, lbl_MaxLenCaseItf, txt_CaseItfPosition);
		WmsCheckker.checkDefine(lbl_BundleEntering, chk_BundleEntering, txt_BdlEtrLen, lbl_MaxLenBundleEntering, txt_BdlEtrPst);
		WmsCheckker.checkDefine(lbl_CaseEtrReq, chk_NCaseEtrReq, txt_CaseEtrReqLen, lbl_MaxLenCaseEtrReq, txt_CaseEtrReqPst);
		WmsCheckker.checkDefine(lbl_ItemName, chk_NItemName, txt_ItemNmLen, lbl_MaxLenItemName, txt_ItemNmPst);
		WmsCheckker.checkDefine(lbl_RetrivlQtyPtlReq, chk_NRtrvlQtyPtlReq, txt_RtrvlQtyPtlReqLen, lbl_MaxLenRtrvlQtyPtlReq, txt_RtrvlQtyPtlReqPst);
		WmsCheckker.checkDefine(lbl_PiceRetrivlLct, chk_PiceRetrvlLct, txt_PiceRetrivlLctLen, lbl_MaxLenPieceRetrivlLct, txt_PiceRtrivLctPst);
		WmsCheckker.checkDefine(lbl_CaseRtrivlLct, chk_CaseRtrivlLct, txt_CaseRtrivlLctLen, lbl_MaxLenCaseRtrivlLct, txt_CaseRtrivlLctPst);
		WmsCheckker.checkDefine(lbl_PieceOrderNoAst, chk_PiceOdrNoAst, txt_PiceOdrNoAstLen, lbl_MaxLenPiceOdrNoAst, txt_PiceOdrNoAstPst);
		WmsCheckker.checkDefine(lbl_CaseOrderNoAst, chk_CaseOdrNoAst, txt_CaseOdrNoAstLen, lbl_MaxLenCaseOdrNoAst, txt_CaseOdrNoAstPst);

		//#CM687933
		// Check the checkbox.
		if (!checkCheckBox())
		{
			//#CM687934
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

			//#CM687935
			// Set the value for the parameter.
			param[0].setSelectDefineLoadData(DefineDataParameter.SELECTDEFINELOADDATA_RETRIEVAL);

			//#CM687936
			// Planned Picking Date (mandatory): Enabled
			param[0].setValid_PlanDate(chk_NRetrivlPnDtReq.getChecked());
			//#CM687937
			// Planned Picking Date (mandatory): Length
			param[0].setFigure_PlanDate(txt_RetrivlPnDtReqLen.getText());
			//#CM687938
			// Planned Picking Date (mandatory): Max Length
			param[0].setMaxFigure_PlanDate(lbl_MaxLenRetrivlPlanDateReq.getText());
			//#CM687939
			// Planned Picking Date (mandatory): Position
			param[0].setPosition_PlanDate(txt_RetrivlPnDtReqPst.getText());
			
			//#CM687940
			// Consignor Code: Enabled
			param[0].setValid_ConsignorCode(chk_ConsignorCode.getChecked());
			//#CM687941
			// Consignor Code: Length
			param[0].setFigure_ConsignorCode(txt_CnsgnrCdLen.getText());
			//#CM687942
			// Consignor Code: Max Length
			param[0].setMaxFigure_ConsignorCode(lbl_MaxLenConsignorCode.getText());
			//#CM687943
			// Consignor Code: Position
			param[0].setPosition_ConsignorCode(txt_CnsgnrCdPst.getText());
			
			//#CM687944
			// Consignor Name: Enabled
			param[0].setValid_ConsignorName(chk_ConsignorName.getChecked());
			//#CM687945
			// Consignor Name: Length
			param[0].setFigure_ConsignorName(txt_CnsgnrNmLen.getText());
			//#CM687946
			// Consignor Name: Max Length
			param[0].setMaxFigure_ConsignorName(lbl_MaxLenConsignorName.getText());
			//#CM687947
			// Consignor Name: Position
			param[0].setPosition_ConsignorName(txt_CnsgnrNmPst.getText());
			
			//#CM687948
			// Customer Code: Enabled
			param[0].setValid_CustomerCode(chk_CustomerCode.getChecked());
			//#CM687949
			// Customer Code: Length
			param[0].setFigure_CustomerCode(txt_CustCdLen.getText());
			//#CM687950
			// Customer Code: Max Length
			param[0].setMaxFigure_CustomerCode(lbl_MaxLenCustomerCode.getText());
			//#CM687951
			// Customer Code: Position
			param[0].setPosition_CustomerCode(txt_CustCdPst.getText());
			
			//#CM687952
			// Customer Name: Enabled
			param[0].setValid_CustomerName(chk_CustomerName.getChecked());
			//#CM687953
			// Customer Name: Length
			param[0].setFigure_CustomerName(txt_CustNmLen.getText());
			//#CM687954
			// Customer Name: Max Length
			param[0].setMaxFigure_CustomerName(lbl_MaxLenCustomerName.getText());
			//#CM687955
			// Customer Name: Position
			param[0].setPosition_CustomerName(txt_CustNmPst.getText());
			
			//#CM687956
			// Ticket No.: Enabled
			param[0].setValid_ShippingTicketNo(chk_TicketNo.getChecked());
			//#CM687957
			// Ticket No.: Length
			param[0].setFigure_ShippingTicketNo(txt_TicketNoLength.getText());
			//#CM687958
			// Ticket No.: Max Length
			param[0].setMaxFigure_ShippingTicketNo(lbl_MaxLenTicketNo.getText());
			//#CM687959
			// Ticket No.: Position
			param[0].setPosition_ShippingTicketNo(txt_TicketNoPosition.getText());
			
			//#CM687960
			// Ticket Line No.: Enabled
			param[0].setValid_ShippingLineNo(chk_TicketLineNo.getChecked());
			//#CM687961
			// Ticket Line No.: Length
			param[0].setFigure_ShippingLineNo(txt_TktLineNoLen.getText());
			//#CM687962
			// Ticket Line No.: Max Length
			param[0].setMaxFigure_ShippingLineNo(lbl_MaxLenTicketLineNo.getText());
			//#CM687963
			// Ticket Line No.: Position
			param[0].setPosition_ShippingLineNo(txt_TktLineNoPst.getText());
			
			//#CM687964
			// Item Code (mandatory): Enabled
			param[0].setValid_ItemCode(chk_NItemCodeReq.getChecked());
			//#CM687965
			// Item Code (mandatory): Length
			param[0].setFigure_ItemCode(txt_ItemCdReqLen.getText());
			//#CM687966
			// Item Code (mandatory): Max Length
			param[0].setMaxFigure_ItemCode(lbl_MaxLenItemCodeReq.getText());
			//#CM687967
			// Item Code (mandatory): Position
			param[0].setPosition_ItemCode(txt_ItemCdReqPst.getText());
			
			//#CM687968
			// Bundle ITF: Enabled
			param[0].setValid_BundleItf(chk_BundleItf.getChecked());
			//#CM687969
			// Bundle ITF: Length
			param[0].setFigure_BundleItf(txt_BundleItfLength.getText());
			//#CM687970
			// Bundle ITF: Max Length
			param[0].setMaxFigure_BundleItf(lbl_MaxLenBundleItf.getText());
			//#CM687971
			// Bundle ITF: Position
			param[0].setPosition_BundleItf(txt_BundleItfPosition.getText());
			
			//#CM687972
			// Case ITF: Enabled
			param[0].setValid_Itf(chk_CaseItf.getChecked());
			//#CM687973
			// Case ITF: Length
			param[0].setFigure_Itf(txt_CaseItfLength.getText());
			//#CM687974
			// Case ITF: Max Length
			param[0].setMaxFigure_Itf(lbl_MaxLenCaseItf.getText());
			//#CM687975
			// Case ITF: Position
			param[0].setPosition_Itf(txt_CaseItfPosition.getText());
			
			//#CM687976
			// Packed qty per bundle: Enabled
			param[0].setValid_BundleEnteringQty(chk_BundleEntering.getChecked());
			//#CM687977
			// Packed qty per bundle: Length
			param[0].setFigure_BundleEnteringQty(txt_BdlEtrLen.getText());
			//#CM687978
			// Packed qty per bundle: Max Length
			param[0].setMaxFigure_BundleEnteringQty(lbl_MaxLenBundleEntering.getText());
			//#CM687979
			// Packed qty per bundle: Position
			param[0].setPosition_BundleEnteringQty(txt_BdlEtrPst.getText());
			
			//#CM687980
			// Packed Qty per Case (mandatory): Enabled
			param[0].setValid_EnteringQty(chk_NCaseEtrReq.getChecked());
			//#CM687981
			// Packed Qty per Case (mandatory): Length
			param[0].setFigure_EnteringQty(txt_CaseEtrReqLen.getText());
			//#CM687982
			// Packed Qty per Case (mandatory): Max Length
			param[0].setMaxFigure_EnteringQty(lbl_MaxLenCaseEtrReq.getText());
			//#CM687983
			// Packed Qty per Case (mandatory): Position
			param[0].setPosition_EnteringQty(txt_CaseEtrReqPst.getText());

			//#CM687984
			// Item Name (mandatory): Enabled
			param[0].setValid_ItemName(chk_NItemName.getChecked());
			//#CM687985
			// Item Name (mandatory): Length
			param[0].setFigure_ItemName(txt_ItemNmLen.getText());
			//#CM687986
			// Item Name (mandatory): Max Length
			param[0].setMaxFigure_ItemName(lbl_MaxLenItemName.getText());
			//#CM687987
			// Item Name (mandatory): Position
			param[0].setPosition_ItemName(txt_ItemNmPst.getText());
			
			//#CM687988
			// Picking Qty (Total Bulk Qty) (mandatory): Enabled
			param[0].setValid_PlanQty(chk_NRtrvlQtyPtlReq.getChecked());
			//#CM687989
			// Picking Qty (Total Bulk Qty) (mandatory): Length
			param[0].setFigure_PlanQty(txt_RtrvlQtyPtlReqLen.getText());
			//#CM687990
			// Picking Qty (Total Bulk Qty) (mandatory): Max Length
			param[0].setMaxFigure_PlanQty(lbl_MaxLenRtrvlQtyPtlReq.getText());
			//#CM687991
			// Picking Qty (Total Bulk Qty) (mandatory): Position
			param[0].setPosition_PlanQty(txt_RtrvlQtyPtlReqPst.getText());
			
			//#CM687992
			// Piece Picking Location: Enabled
			param[0].setValid_PieceLocation(chk_PiceRetrvlLct.getChecked());
			//#CM687993
			// Piece Picking: Length
			param[0].setFigure_PieceLocation(txt_PiceRetrivlLctLen.getText());
			//#CM687994
			// Piece Picking: Max Length
			param[0].setMaxFigure_PieceLocation(lbl_MaxLenPieceRetrivlLct.getText());
			//#CM687995
			// Piece Picking: Position
			param[0].setPosition_PieceLocation(txt_PiceRtrivLctPst.getText());
			
			//#CM687996
			// Case Picking Location: Enabled
			param[0].setValid_CaseLocation(chk_CaseRtrivlLct.getChecked());
			//#CM687997
			// Case Picking Location: Length
			param[0].setFigure_CaseLocation(txt_CaseRtrivlLctLen.getText());
			//#CM687998
			// Case Picking Location: Max Length
			param[0].setMaxFigure_CaseLocation(lbl_MaxLenCaseRtrivlLct.getText());
			//#CM687999
			// Case Picking Location: Position
			param[0].setPosition_CaseLocation(txt_CaseRtrivlLctPst.getText());
			
			//#CM688000
			// (Note))Piece Order No.: Enabled
			param[0].setValid_PieceOrderNo(chk_PiceOdrNoAst.getChecked());
			//#CM688001
			// (Note))Piece Order No.: Length
			param[0].setFigure_PieceOrderNo(txt_PiceOdrNoAstLen.getText());
			//#CM688002
			// (Note))Piece Order No.: Max Length
			param[0].setMaxFigure_PieceOrderNo(lbl_MaxLenPiceOdrNoAst.getText());
			//#CM688003
			// (Note))Piece Order No.: Position
			param[0].setPosition_PieceOrderNo(txt_PiceOdrNoAstPst.getText());
			
			//#CM688004
			// (Note))Case Order No.
			param[0].setValid_CaseOrderNo(chk_CaseOdrNoAst.getChecked());
			//#CM688005
			// (Note))Case Order No.
			param[0].setFigure_CaseOrderNo(txt_CaseOdrNoAstLen.getText());
			//#CM688006
			// (Note))Case Order No.
			param[0].setMaxFigure_CaseOrderNo(lbl_MaxLenCaseOdrNoAst.getText());
			//#CM688007
			// (Note))Case Order No.
			param[0].setPosition_CaseOrderNo(txt_CaseOdrNoAstPst.getText());
			
			//#CM688008
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
				//#CM688009
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
				//#CM688010
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

	//#CM688011
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
//#CM688012
//end of class
