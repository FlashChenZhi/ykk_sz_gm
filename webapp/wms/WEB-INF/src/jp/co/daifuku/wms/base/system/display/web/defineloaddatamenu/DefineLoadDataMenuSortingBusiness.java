// $Id: DefineLoadDataMenuSortingBusiness.java,v 1.2 2006/11/13 08:18:25 suresh Exp $

//#CM688282
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

//#CM688283
/**
 * Designer : T.Uehata <BR>
 * Maker : T.Kuroda <BR>
 * <BR>
 * Allow this class to provide a screen to set a field item for loading data (to set the Sorting Plan field). <BR>
 * Set the contents input via input area for the parameter and pass it to the schedule. Add the setting of Sorting Plan field. <BR>
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
 *   Planned Sorting Date	(mandatory)*<BR>
 * 	 Customer Code (mandatory)*<BR>
 *   Item Code	(mandatory)*<BR>
 *   Packed Qty per Case	(mandatory)*<BR>
 *   Sorting Qty (Total Bulk Qty) (mandatory)*<BR>
 * 	 Piece Sorting Place (mandatory)*<BR>
 * 	 Cross/DC division (mandatory)*<BR>
 * </DIR>
 * <BR>
 *   [Parameter]+ Require to input if the parameter is ticked to Enabled <BR>
 *   Length, Max Length, and Position of each field item<BR>
 * <BR>
 * <DIR>
 *   Consignor Code +<BR>
 *   Consignor Name+<BR>
 * 	 Customer Name+<BR>
 * 	 Shipping Ticket No+<BR>
 * 	 Shipping ticket line No.+<BR>
 *   Bundle ITF+<BR>
 *   Case ITF+<BR>
 *   Packed qty per bundle+<BR>
 *   Item Name+<BR>
 * 	 Case Sorting Place+<BR>
 * 	 (Note))Supplier Code+<BR>
 * 	 Supplier Name+<BR>
 * 	 (Note))Receiving Ticket No.+<BR>
 * 	 (Note))Receiving Ticket Line No. +<BR>
 * </DIR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/04</TD><TD>T.Kuroda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:25 $
 * @author  $Author: suresh $
 */
public class DefineLoadDataMenuSortingBusiness extends DefineLoadDataMenuSorting implements WMSConstants
{
	//#CM688284
	// Class fields --------------------------------------------------

	//#CM688285
	// Class variables -----------------------------------------------

	//#CM688286
	// Class method --------------------------------------------------

	//#CM688287
	// Constructors --------------------------------------------------

	//#CM688288
	// Public methods ------------------------------------------------

	//#CM688289
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
			//#CM688290
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM688291
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM688292
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM688293
			//Set the path to the help file.
			//#CM688294
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM688295
		// MSG-0009= Do you add it?
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}
	
	//#CM688296
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Set the cursor to Planned Sorting Date (mandatory) Position. <BR>
	 * <DIR>
	 *   Field item [Initial Value] <BR>
	 *   <DIR>
	 *     Planned Sorting Date      	(mandatory) Enabled [Ticked] <BR>
	 *     Customer Code    	(mandatory) Enabled [Ticked] <BR>
	 *     Item Code      	(mandatory) Enabled [Ticked] <BR>
	 *     Packed Qty per Case      	(mandatory) Enabled [Ticked] <BR>
	 *     Item Name        	(mandatory) Enabled [Ticked] <BR>
	 *     Shipping Qty (Total Bulk Qty)	(mandatory) Enabled [Ticked] <BR>
	 *     Piece Sorting Place	(mandatory) Enabled [Ticked] <BR>
	 *     Cross/DC division	(mandatory) Enabled [Ticked] <BR>
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
		//#CM688297
		// Title
		lbl_SettingName.setResourceKey(this.getViewState().getString("TITLE"));

		dataLoad();
	}

	//#CM688298
	// Package methods -----------------------------------------------

	//#CM688299
	// Protected methods ---------------------------------------------

	//#CM688300
	// Private methods -----------------------------------------------
	//#CM688301
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
	 *   2.Set the cursor to Planned Sorting Date (mandatory) Position. <BR>
	 * </DIR>
	 * @throws Exception Report all exceptions.
	 */
	private void dataLoad() throws Exception
	{
		//#CM688302
		// Set the initial value of the focus to Planned Sorting Date (mandatory) Position.
		setFocus(txt_PickPlanDateReqPst);

		Connection conn = null;
		try
		{
			//#CM688303
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter initParam = new DefineDataParameter();
			initParam.setSelectDefineLoadData(DefineDataParameter.SELECTDEFINELOADDATA_SORTING);
			WmsScheduler schedule = new DefineLoadDataMenuSCH();
			DefineDataParameter[] param = (DefineDataParameter[]) schedule.query(conn, initParam);

			if (param != null)
			{
				//#CM688304
				// Planned Sorting Date (mandatory): Length
				txt_PickPlanDateReqLen.setText(param[0].getFigure_PlanDate());
				//#CM688305
				// Planned Sorting Date (mandatory): Max Length
				lbl_MaxLenPickPlanDateReq.setText(param[0].getMaxFigure_PlanDate());
				//#CM688306
				// Planned Sorting Date (mandatory): Position
				txt_PickPlanDateReqPst.setText(param[0].getPosition_PlanDate());

				//#CM688307
				// Consignor Code: Enabled
				chk_CnsgnrCd.setChecked(param[0].getValid_ConsignorCode());
				//#CM688308
				// Consignor Code: Length
				txt_CnsgnrCdLen.setText(param[0].getFigure_ConsignorCode());
				//#CM688309
				// Consignor Code: Max Length
				lbl_MaxLenCnsgnrCd.setText(param[0].getMaxFigure_ConsignorCode());
				//#CM688310
				// Consignor Code: Position
				txt_CnsgnrCdPst.setText(param[0].getPosition_ConsignorCode());
				
				//#CM688311
				// Consignor Name: Enabled
				chk_ConsignorName.setChecked(param[0].getValid_ConsignorName());
				//#CM688312
				// Consignor Name: Length
				txt_CnsgnrNmLen.setText(param[0].getFigure_ConsignorName());
				//#CM688313
				// Consignor Name: Max Length
				lbl_MaxLenConsignorName.setText(param[0].getMaxFigure_ConsignorName());
				//#CM688314
				// Consignor Name: Position
				txt_CnsgnrNmPst.setText(param[0].getPosition_ConsignorName());
				
				//#CM688315
				// Customer Code (mandatory): Length
				txt_CustCdReqLen.setText(param[0].getFigure_CustomerCode());
				//#CM688316
				// Customer Code (mandatory): Max Length
				lbl_MaxLenCustCdReq.setText(param[0].getMaxFigure_CustomerCode());
				//#CM688317
				// Customer Code (mandatory): Position
				txt_CustCdReqPst.setText(param[0].getPosition_CustomerCode());

				//#CM688318
				// Customer Name: Enabled
				chk_NCustNm.setChecked(param[0].getValid_CustomerName());
				//#CM688319
				// Customer Name: Length
				txt_CustNmLen.setText(param[0].getFigure_CustomerName());
				//#CM688320
				// Customer Name: Max Length
				lbl_MaxLenCustNm.setText(param[0].getMaxFigure_CustomerName());
				//#CM688321
				// Customer Name: Position
				txt_CustNmPst.setText(param[0].getPosition_CustomerName());
				
				//#CM688322
				// Shipping Ticket No.: Enabled
				chk_ShpTktNo.setChecked(param[0].getValid_ShippingTicketNo());
				//#CM688323
				// Shipping Ticket No.: Length
				txt_ShpTktNoLen.setText(param[0].getFigure_ShippingTicketNo());
				//#CM688324
				// Shipping Ticket No.: Max Length
				lbl_MaxLenShpTktNo.setText(param[0].getMaxFigure_ShippingTicketNo());
				//#CM688325
				// Shipping Ticket No.: Position
				txt_ShpTktNoPst.setText(param[0].getPosition_ShippingTicketNo());
				
				//#CM688326
				// Shipping ticket line No.: Enabled
				chk_ShpTktLineNo.setChecked(param[0].getValid_ShippingLineNo());
				//#CM688327
				// Shipping ticket line No.: Length
				txt_ShpTktLineNoLen.setText(param[0].getFigure_ShippingLineNo());
				//#CM688328
				// Shipping ticket line No.: Max Length
				lbl_MaxLenShpTktLineNo.setText(param[0].getMaxFigure_ShippingLineNo());
				//#CM688329
				// Shipping ticket line No.: Position
				txt_ShpTktLineNoPst.setText(param[0].getPosition_ShippingLineNo());
				
				//#CM688330
				// Item Code (mandatory): Length
				txt_ItemCdReqLen.setText(param[0].getFigure_ItemCode());
				//#CM688331
				// Item Code (mandatory): Max Length
				lbl_MaxLenItemCdReq.setText(param[0].getMaxFigure_ItemCode());
				//#CM688332
				// Item Code (mandatory): Position
				txt_ItemCdReqPst.setText(param[0].getPosition_ItemCode());
				
				//#CM688333
				// Bundle ITF: Enabled
				chk_BundleItf.setChecked(param[0].getValid_BundleItf());
				//#CM688334
				// Bundle ITF: Length
				txt_BundleItfLen.setText(param[0].getFigure_BundleItf());
				//#CM688335
				// Bundle ITF: Max Length
				lbl_MaxLenBundleItf.setText(param[0].getMaxFigure_BundleItf());
				//#CM688336
				// Bundle ITF: Position
				txt_BundleItfPst.setText(param[0].getPosition_BundleItf());
				
				//#CM688337
				// Case ITF: Enabled
				chk_CaseItf.setChecked(param[0].getValid_Itf());
				//#CM688338
				// Case ITF: Length
				txt_CaseItfLen.setText(param[0].getFigure_Itf());
				//#CM688339
				// Case ITF: Max Length
				lbl_MaxLenCaseItf.setText(param[0].getMaxFigure_Itf());
				//#CM688340
				// Case ITF: Position
				txt_CaseItfPst.setText(param[0].getPosition_Itf());
				
				//#CM688341
				// Packed qty per bundle: Enabled
				chk_BundleEntering.setChecked(param[0].getValid_BundleEnteringQty());
				//#CM688342
				// Packed qty per bundle: Length
				txt_BundleEnteringLen.setText(param[0].getFigure_BundleEnteringQty());
				//#CM688343
				// Packed qty per bundle: Max Length
				lbl_MaxLenBundleEntering.setText(param[0].getMaxFigure_BundleEnteringQty());
				//#CM688344
				// Packed qty per bundle: Position
				txt_BundleEnteringPst.setText(param[0].getPosition_BundleEnteringQty());
				
				//#CM688345
				// Packed Qty per Case (mandatory): Length
				txt_CaseEtrReqLen.setText(param[0].getFigure_EnteringQty());
				//#CM688346
				// Packed Qty per Case (mandatory): Max Length
				lbl_MaxLenCaseEtrReq.setText(param[0].getMaxFigure_EnteringQty());
				//#CM688347
				// Packed Qty per Case (mandatory): Position
				txt_CaseEtrReqPst.setText(param[0].getPosition_EnteringQty());

				//#CM688348
				// Item Name: Enabled
				chk_NItemNm.setChecked(param[0].getValid_ItemName());
				//#CM688349
				// Item Name: Length
				txt_ItemNmLen.setText(param[0].getFigure_ItemName());
				//#CM688350
				// Item Name: Max Length
				lbl_MaxLenItemNm.setText(param[0].getMaxFigure_ItemName());
				//#CM688351
				// Item Name: Position
				txt_ItemNmPst.setText(param[0].getPosition_ItemName());
				
				//#CM688352
				// Sorting Qty (Total Bulk Qty) (mandatory): Length
				txt_PickQtyPtlLenReq.setText(param[0].getFigure_PlanQty());
				//#CM688353
				// Sorting Qty (Total Bulk Qty) (mandatory): Max Length
				lbl_MaxLenPickQtyPtlReq.setText(param[0].getMaxFigure_PlanQty());
				//#CM688354
				// Sorting Qty (Total Bulk Qty) (mandatory): Position
				txt_PickQtyPtlReqPst.setText(param[0].getPosition_PlanQty());
				
				//#CM688355
				// Piece Sorting Place (mandatory): Length
				txt_PiecePickPlaceReqLen.setText(param[0].getFigure_PieceLocation());
				//#CM688356
				// Piece Sorting Place (mandatory): Max Length
				lbl_MaxLenPiecePickPlaceReq.setText(param[0].getMaxFigure_PieceLocation());
				//#CM688357
				// Piece Sorting Place (mandatory): Position
				txt_PiecePickPlaceReqPst.setText(param[0].getPosition_PieceLocation());
				
				//#CM688358
				// Case Sorting Place: Enabled
				chk_CasePickPlacee.setChecked(param[0].getValid_CaseLocation());
				//#CM688359
				// Case Sorting Place: Length
				txt_CasePickPlaceLen.setText(param[0].getFigure_CaseLocation());
				//#CM688360
				// Case Sorting Place: Max Length
				lbl_MaxLenCasePickPlace.setText(param[0].getMaxFigure_CaseLocation());
				//#CM688361
				// Case Sorting Place: Position
				txt_CasePickPlacePst.setText(param[0].getPosition_CaseLocation());
				
				//#CM688362
				// Cross/DC division: Length
				txt_CrossDCFlagReqLen.setText(param[0].getFigure_TcDcFlag());
				//#CM688363
				// Cross/DC division: Max Length
				lbl_MaxLenCrossDCFlagReq.setText(param[0].getMaxFigure_TcDcFlag());
				//#CM688364
				// Cross/DC division: Position
				txt_CrossDCFlagPst.setText(param[0].getPosition_TcDcFlag());

				//#CM688365
				// (Note))Supplier Code: Enabled
				chk_SupplierCdAst.setChecked(param[0].getValid_SupplierCode());
				//#CM688366
				// (Note))Supplier Code: Length
				txt_SupplierCdAstLen.setText(param[0].getFigure_SupplierCode());
				//#CM688367
				// (Note))Supplier Code: Max Length
				lbl_MaxLenSupplierCdAst.setText(param[0].getMaxFigure_SupplierCode());
				//#CM688368
				// (Note))Supplier Code: Position
				txt_SupplierCdAstPst.setText(param[0].getPosition_SupplierCode());
				
				//#CM688369
				// Supplier Name: Enabled
				chk_SupplierNmAst.setChecked(param[0].getValid_SupplierName());
				//#CM688370
				// Supplier Name: Length
				txt_SupplierNmAstLen.setText(param[0].getFigure_SupplierName());
				//#CM688371
				// Supplier Name: Max Length
				lbl_MaxLenSupplierNmAst.setText(param[0].getMaxFigure_SupplierName());
				//#CM688372
				// Supplier Name: Position
				txt_SupplierNmAstPst.setText(param[0].getPosition_SupplierName());
				
				//#CM688373
				// (Note))Receiving Ticket No.: Enabled
				chk_InstkTktNoAst.setChecked(param[0].getValid_InstockTicketNo());
				//#CM688374
				// (Note))Receiving Ticket No.: Length
				txt_InstkTktNoAstLen.setText(param[0].getFigure_InstockTicketNo());
				//#CM688375
				// (Note))Receiving Ticket No.: Max Length
				lbl_MaxLenInstkTktNoAst.setText(param[0].getMaxFigure_InstockTicketNo());
				//#CM688376
				// (Note))Receiving Ticket No.: Position
				txt_InstkTktNoAstPst.setText(param[0].getPosition_InstockTicketNo());
				
				//#CM688377
				// (Note))Receiving Ticket Line No.: Enabled
				chk_InstkTktLineNoAst.setChecked(param[0].getValid_InstockLineNo());
				//#CM688378
				// (Note))Receiving Ticket Line No.: Length
				txt_InstkTktLineNoAstLen.setText(param[0].getFigure_InstockLineNo());
				//#CM688379
				// (Note))Receiving Ticket Line No.: Max Length
				lbl_MaxLenInstkTktLineNoAst.setText(param[0].getMaxFigure_InstockLineNo());
				//#CM688380
				// (Note))Receiving Ticket Line No.: Position
				txt_InstkTktLineNoAstPst.setText(param[0].getPosition_InstockLineNo());
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
				//#CM688381
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
	
	//#CM688382
	/** 
	 * Allow this method to check whether a checkbox is ticked off or not.<BR>
	 * <BR>
	 * @return Return false if no item ticked off.
	 * @throws Exception Report all exceptions.
	 */
	private boolean checkCheckBox() throws Exception
	{
		if (!chk_NPickPlanDateReq.getChecked()
				&& !chk_CnsgnrCd.getChecked()
				&& !chk_ConsignorName.getChecked()
				&& !chk_NCustCdReq.getChecked()
				&& !chk_NCustNm.getChecked()
				&& !chk_ShpTktNo.getChecked()
				&& !chk_ShpTktLineNo.getChecked()
				&& !chk_NItemCdReq.getChecked()
				&& !chk_BundleItf.getChecked()
				&& !chk_CaseItf.getChecked()
				&& !chk_BundleEntering.getChecked()
				&& !chk_NCaseEtrReq.getChecked()
				&& !chk_NItemNm.getChecked()
				&& !chk_NPickQtyPtlReq.getChecked()
				&& !chk_NPiecePickPlaceReq.getChecked()
				&& !chk_CasePickPlacee.getChecked()
				&& !chk_NCrossDCFlagReq.getChecked()
				&& !chk_SupplierCdAst.getChecked()
				&& !chk_SupplierNmAst.getChecked()
				&& !chk_InstkTktNoAst.getChecked()
				&& !chk_InstkTktLineNoAst.getChecked()
			)
		{
			return false;
		}
		return true;
	}
	
	//#CM688383
	// Event handler methods -----------------------------------------
	//#CM688384
	/** 
	 * Clicking on Return button invokes this.<BR>
	 * <BR>
	 * Summary: Return to the previous screen. <BR>
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		//#CM688385
		// Shift Screen
		forward("/system/defineloaddatamenu/DefineLoadDataMenu.do");	
	}

	//#CM688386
	/** 
	 * Clicking on Menu button invokes this. <BR>
	 * <BR>
	 * Shift to the Menu screen.<BR>
	 * <BR>
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM688387
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
	 *     4.Set the cursor to Planned Sorting Date (mandatory) Length. <BR>
	 *   </DIR>
	 * </DIR>
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		//#CM688388
		// Set the initial value of the focus to Planned Sorting Date (mandatory) Length.
		setFocus(txt_PickPlanDateReqPst);

		//#CM688389
		// Check for input.
		WmsCheckker.checkDefine(lbl_PickPlanDateReq, chk_NPickPlanDateReq, txt_PickPlanDateReqLen, lbl_MaxLenPickPlanDateReq, txt_PickPlanDateReqPst);
		WmsCheckker.checkDefine(lbl_ConsignorCode, chk_CnsgnrCd, txt_CnsgnrCdLen, lbl_MaxLenCnsgnrCd, txt_CnsgnrCdPst);
		WmsCheckker.checkDefine(lbl_ConsignorName, chk_ConsignorName, txt_CnsgnrNmLen, lbl_MaxLenConsignorName, txt_CnsgnrNmPst);
		WmsCheckker.checkDefine(lbl_CustCdReq, chk_NCustCdReq, txt_CustCdReqLen, lbl_MaxLenCustCdReq, txt_CustCdReqPst);
		WmsCheckker.checkDefine(lbl_CustNm, chk_NCustNm, txt_CustNmLen, lbl_MaxLenCustNm, txt_CustNmPst);
		WmsCheckker.checkDefine(lbl_ShippingTicketNo, chk_ShpTktNo, txt_ShpTktNoLen, lbl_MaxLenShpTktNo, txt_ShpTktNoPst);			
		WmsCheckker.checkDefine(lbl_ShpTktLineNo, chk_ShpTktLineNo, txt_ShpTktLineNoLen, lbl_MaxLenShpTktLineNo, txt_ShpTktLineNoPst);	
		WmsCheckker.checkDefine(lbl_ItemCodeRequired, chk_NItemCdReq, txt_ItemCdReqLen, lbl_MaxLenItemCdReq, txt_ItemCdReqPst);
		WmsCheckker.checkDefine(lbl_BundleItf, chk_BundleItf, txt_BundleItfLen, lbl_MaxLenBundleItf, txt_BundleItfPst);
		WmsCheckker.checkDefine(lbl_CaseItf, chk_CaseItf, txt_CaseItfLen, lbl_MaxLenCaseItf, txt_CaseItfPst);
		WmsCheckker.checkDefine(lbl_BundleEntering, chk_BundleEntering, txt_BundleEnteringLen, lbl_MaxLenBundleEntering, txt_BundleEnteringPst);
		WmsCheckker.checkDefine(lbl_CaseEtrReq, chk_NCaseEtrReq, txt_CaseEtrReqLen, lbl_MaxLenCaseEtrReq, txt_CaseEtrReqPst);
		WmsCheckker.checkDefine(lbl_ItemName, chk_NItemNm, txt_ItemNmLen, lbl_MaxLenItemNm, txt_ItemNmPst);
		WmsCheckker.checkDefine(lbl_PickingQtyPtlReq, chk_NPickQtyPtlReq, txt_PickQtyPtlLenReq, lbl_MaxLenPickQtyPtlReq, txt_PickQtyPtlReqPst);
		WmsCheckker.checkDefine(lbl_PicePickPlaceReq, chk_NPiecePickPlaceReq, txt_PiecePickPlaceReqLen, lbl_MaxLenPiecePickPlaceReq, txt_PiecePickPlaceReqPst);
		WmsCheckker.checkDefine(lbl_CasePickingPlace, chk_CasePickPlacee, txt_CasePickPlaceLen, lbl_MaxLenCasePickPlace, txt_CasePickPlacePst);
		WmsCheckker.checkDefine(lbl_CrossDCRequired, chk_NCrossDCFlagReq, txt_CrossDCFlagReqLen, lbl_MaxLenCrossDCFlagReq, txt_CrossDCFlagPst);
		WmsCheckker.checkDefine(lbl_SupplierCodeAst, chk_SupplierCdAst, txt_SupplierCdAstLen, lbl_MaxLenSupplierCdAst, txt_SupplierCdAstPst);
		WmsCheckker.checkDefine(lbl_SupplierName, chk_SupplierNmAst, txt_SupplierNmAstLen, lbl_MaxLenSupplierNmAst, txt_SupplierNmAstPst);
		WmsCheckker.checkDefine(lbl_InstockTicketNoAst, chk_InstkTktNoAst, txt_InstkTktNoAstLen, lbl_MaxLenInstkTktNoAst, txt_InstkTktNoAstPst);
		WmsCheckker.checkDefine(lbl_InstkTktLineNoAst, chk_InstkTktLineNoAst, txt_InstkTktLineNoAstLen, lbl_MaxLenInstkTktLineNoAst, txt_InstkTktLineNoAstPst);

		//#CM688390
		// Check the checkbox.
		if (!checkCheckBox())
		{
			//#CM688391
			// 6023174=Please select 1 or more items.
			message.setMsgResourceKey("6023174");
			return;
		}
		
		Connection conn = null;
		try
		{
			DefineDataParameter[] param = new DefineDataParameter[1];
			param[0] = new DefineDataParameter();
			
			//#CM688392
			// Set the value for the parameter.
			param[0].setSelectDefineLoadData(DefineDataParameter.SELECTDEFINELOADDATA_SORTING);

			//#CM688393
			// Planned Sorting Date (mandatory): Enabled
			param[0].setValid_PlanDate(chk_NPickPlanDateReq.getChecked());
			//#CM688394
			// Planned Sorting Date (mandatory): Length
			param[0].setFigure_PlanDate(txt_PickPlanDateReqLen.getText());
			//#CM688395
			// Planned Sorting Date (mandatory): Max Length
			param[0].setMaxFigure_PlanDate(lbl_MaxLenPickPlanDateReq.getText());
			//#CM688396
			// Planned Sorting Date (mandatory): Position
			param[0].setPosition_PlanDate(txt_PickPlanDateReqPst.getText());
			
			//#CM688397
			// Consignor Code: Enabled
			param[0].setValid_ConsignorCode(chk_CnsgnrCd.getChecked());
			//#CM688398
			// Consignor Code: Length
			param[0].setFigure_ConsignorCode(txt_CnsgnrCdLen.getText());
			//#CM688399
			// Consignor Code: Max Length
			param[0].setMaxFigure_ConsignorCode(lbl_MaxLenCnsgnrCd.getText());
			//#CM688400
			// Consignor Code: Position
			param[0].setPosition_ConsignorCode(txt_CnsgnrCdPst.getText());
			
			//#CM688401
			// Consignor Name: Enabled
			param[0].setValid_ConsignorName(chk_ConsignorName.getChecked());
			//#CM688402
			// Consignor Name: Length
			param[0].setFigure_ConsignorName(txt_CnsgnrNmLen.getText());
			//#CM688403
			// Consignor Name: Max Length
			param[0].setMaxFigure_ConsignorName(lbl_MaxLenConsignorName.getText());
			//#CM688404
			// Consignor Name: Position
			param[0].setPosition_ConsignorName(txt_CnsgnrNmPst.getText());
			
			//#CM688405
			// Customer Code: Enabled
			param[0].setValid_CustomerCode(chk_NCustCdReq.getChecked());
			//#CM688406
			// Customer Code: Length
			param[0].setFigure_CustomerCode(txt_CustCdReqLen.getText());
			//#CM688407
			// Customer Code: Max Length
			param[0].setMaxFigure_CustomerCode(lbl_MaxLenCustCdReq.getText());
			//#CM688408
			// Customer Code: Position
			param[0].setPosition_CustomerCode(txt_CustCdReqPst.getText());

			//#CM688409
			// Customer Name: Enabled
			param[0].setValid_CustomerName(chk_NCustNm.getChecked());
			//#CM688410
			// Customer Name: Length
			param[0].setFigure_CustomerName(txt_CustNmLen.getText());
			//#CM688411
			// Customer Name: Max Length
			param[0].setMaxFigure_CustomerName(lbl_MaxLenCustNm.getText());
			//#CM688412
			// Customer Name: Position
			param[0].setPosition_CustomerName(txt_CustNmPst.getText());
			
			//#CM688413
			// Shipping Ticket No.: Enabled
			param[0].setValid_ShippingTicketNo(chk_ShpTktNo.getChecked());
			//#CM688414
			// Shipping Ticket No.: Length
			param[0].setFigure_ShippingTicketNo(txt_ShpTktNoLen.getText());
			//#CM688415
			// Shipping Ticket No.: Max Length
			param[0].setMaxFigure_ShippingTicketNo(lbl_MaxLenShpTktNo.getText());
			//#CM688416
			// Shipping Ticket No.: Position
			param[0].setPosition_ShippingTicketNo(txt_ShpTktNoPst.getText());
			
			//#CM688417
			// Shipping ticket line No.: Enabled
			param[0].setValid_ShippingLineNo(chk_ShpTktLineNo.getChecked());
			//#CM688418
			// Shipping ticket line No.: Length
			param[0].setFigure_ShippingLineNo(txt_ShpTktLineNoLen.getText());
			//#CM688419
			// Shipping ticket line No.: Max Length
			param[0].setMaxFigure_ShippingLineNo(lbl_MaxLenShpTktLineNo.getText());
			//#CM688420
			// Shipping ticket line No.: Position
			param[0].setPosition_ShippingLineNo(txt_ShpTktLineNoPst.getText());
			
			//#CM688421
			// Item Code (mandatory): Enabled
			param[0].setValid_ItemCode(chk_NItemCdReq.getChecked());
			//#CM688422
			// Item Code (mandatory): Length
			param[0].setFigure_ItemCode(txt_ItemCdReqLen.getText());
			//#CM688423
			// Item Code (mandatory): Max Length
			param[0].setMaxFigure_ItemCode(lbl_MaxLenItemCdReq.getText());
			//#CM688424
			// Item Code (mandatory): Position
			param[0].setPosition_ItemCode(txt_ItemCdReqPst.getText());
			
			//#CM688425
			// Bundle ITF: Enabled
			param[0].setValid_BundleItf(chk_BundleItf.getChecked());
			//#CM688426
			// Bundle ITF: Length
			param[0].setFigure_BundleItf(txt_BundleItfLen.getText());
			//#CM688427
			// Bundle ITF: Max Length
			param[0].setMaxFigure_BundleItf(lbl_MaxLenBundleItf.getText());
			//#CM688428
			// Bundle ITF: Position
			param[0].setPosition_BundleItf(txt_BundleItfPst.getText());
			
			//#CM688429
			// Case ITF: Enabled
			param[0].setValid_Itf(chk_CaseItf.getChecked());
			//#CM688430
			// Case ITF: Length
			param[0].setFigure_Itf(txt_CaseItfLen.getText());
			//#CM688431
			// Case ITF: Max Length
			param[0].setMaxFigure_Itf(lbl_MaxLenCaseItf.getText());
			//#CM688432
			// Case ITF: Position
			param[0].setPosition_Itf(txt_CaseItfPst.getText());
			
			//#CM688433
			// Packed qty per bundle: Enabled
			param[0].setValid_BundleEnteringQty(chk_BundleEntering.getChecked());
			//#CM688434
			// Packed qty per bundle: Length
			param[0].setFigure_BundleEnteringQty(txt_BundleEnteringLen.getText());
			//#CM688435
			// Packed qty per bundle: Max Length
			param[0].setMaxFigure_BundleEnteringQty(lbl_MaxLenBundleEntering.getText());
			//#CM688436
			// Packed qty per bundle: Position
			param[0].setPosition_BundleEnteringQty(txt_BundleEnteringPst.getText());
			
			//#CM688437
			// Packed Qty per Case (mandatory): Enabled
			param[0].setValid_EnteringQty(chk_NCaseEtrReq.getChecked());
			//#CM688438
			// Packed Qty per Case (mandatory): Length
			param[0].setFigure_EnteringQty(txt_CaseEtrReqLen.getText());
			//#CM688439
			// Packed Qty per Case (mandatory): Max Length
			param[0].setMaxFigure_EnteringQty(lbl_MaxLenCaseEtrReq.getText());
			//#CM688440
			// Packed Qty per Case (mandatory): Position
			param[0].setPosition_EnteringQty(txt_CaseEtrReqPst.getText());

			//#CM688441
			// Item Name (mandatory): Enabled
			param[0].setValid_ItemName(chk_NItemNm.getChecked());
			//#CM688442
			// Item Name (mandatory): Length
			param[0].setFigure_ItemName(txt_ItemNmLen.getText());
			//#CM688443
			// Item Name (mandatory): Max Length
			param[0].setMaxFigure_ItemName(lbl_MaxLenItemNm.getText());
			//#CM688444
			// Item Name (mandatory): Position
			param[0].setPosition_ItemName(txt_ItemNmPst.getText());
			
			//#CM688445
			// Sorting Qty (Total Bulk Qty) (mandatory): Enabled
			param[0].setValid_PlanQty(chk_NPickQtyPtlReq.getChecked());
			//#CM688446
			// Sorting Qty (Total Bulk Qty) (mandatory): Length
			param[0].setFigure_PlanQty(txt_PickQtyPtlLenReq.getText());
			//#CM688447
			// Sorting Qty (Total Bulk Qty) (mandatory): Max Length
			param[0].setMaxFigure_PlanQty(lbl_MaxLenPickQtyPtlReq.getText());
			//#CM688448
			// Sorting Qty (Total Bulk Qty) (mandatory): Position
			param[0].setPosition_PlanQty(txt_PickQtyPtlReqPst.getText());
			
			//#CM688449
			// Piece Sorting Place: Enabled
			param[0].setValid_PieceLocation(chk_NPiecePickPlaceReq.getChecked());
			//#CM688450
			// Piece Sorting Place: Length
			param[0].setFigure_PieceLocation(txt_PiecePickPlaceReqLen.getText());
			//#CM688451
			// Piece Sorting Place: Max Length
			param[0].setMaxFigure_PieceLocation(lbl_MaxLenPiecePickPlaceReq.getText());
			//#CM688452
			// Piece Sorting Place: Position
			param[0].setPosition_PieceLocation(txt_PiecePickPlaceReqPst.getText());
			
			//#CM688453
			// Case Sorting Place: Enabled
			param[0].setValid_CaseLocation(chk_CasePickPlacee.getChecked());
			//#CM688454
			// Case Sorting Place: Length
			param[0].setFigure_CaseLocation(txt_CasePickPlaceLen.getText());
			//#CM688455
			// Case Sorting Place: Max Length
			param[0].setMaxFigure_CaseLocation(lbl_MaxLenPiecePickPlaceReq.getText());
			//#CM688456
			// Case Sorting Place: Position
			param[0].setPosition_CaseLocation(txt_CasePickPlacePst.getText());
			
			//#CM688457
			// Cross/DC division: Enabled
			param[0].setValid_TcDcFlag(chk_NCrossDCFlagReq.getChecked());
			//#CM688458
			// Cross/DC division: Length
			param[0].setFigure_TcDcFlag(txt_CrossDCFlagReqLen.getText());
			//#CM688459
			// Cross/DC division: Max Length
			param[0].setMaxFigure_TcDcFlag(lbl_MaxLenCrossDCFlagReq.getText());
			//#CM688460
			// Cross/DC division: Position
			param[0].setPosition_TcDcFlag(txt_CrossDCFlagPst.getText());
			
			//#CM688461
			// (Note))Supplier Code: Enabled
			param[0].setValid_SupplierCode(chk_SupplierCdAst.getChecked());
			//#CM688462
			// (Note))Supplier Code: Length
			param[0].setFigure_SupplierCode(txt_SupplierCdAstLen.getText());
			//#CM688463
			// (Note))Supplier Code: Max Length
			param[0].setMaxFigure_SupplierCode(lbl_MaxLenSupplierCdAst.getText());
			//#CM688464
			// (Note))Supplier Code: Position
			param[0].setPosition_SupplierCode(txt_SupplierCdAstPst.getText());
			
			//#CM688465
			// Supplier Name: Enabled
			param[0].setValid_SupplierName(chk_SupplierNmAst.getChecked());
			//#CM688466
			// Supplier Name: Length
			param[0].setFigure_SupplierName(txt_SupplierNmAstLen.getText());
			//#CM688467
			// Supplier Name: Max Length
			param[0].setMaxFigure_SupplierName(lbl_MaxLenSupplierNmAst.getText());
			//#CM688468
			// Supplier Name: Position
			param[0].setPosition_SupplierName(txt_SupplierNmAstPst.getText());
			
			//#CM688469
			// (Note))Receiving Ticket No.: Enabled
			param[0].setValid_InstockTicketNo(chk_InstkTktNoAst.getChecked());
			//#CM688470
			// (Note))Receiving Ticket No.: Length
			param[0].setFigure_InstockTicketNo(txt_InstkTktNoAstLen.getText());
			//#CM688471
			// (Note))Receiving Ticket No.: Max Length
			param[0].setMaxFigure_InstockTicketNo(lbl_MaxLenInstkTktNoAst.getText());
			//#CM688472
			// (Note))Receiving Ticket No.: Position
			param[0].setPosition_InstockTicketNo(txt_InstkTktNoAstPst.getText());
			
			//#CM688473
			// (Note))Receiving Ticket Line No.: Enabled
			param[0].setValid_InstockLineNo(chk_InstkTktLineNoAst.getChecked());
			//#CM688474
			// (Note))Receiving Ticket Line No.: Length
			param[0].setFigure_InstockLineNo(txt_InstkTktLineNoAstLen.getText());
			//#CM688475
			// (Note))Receiving Ticket Line No.: Max Length
			param[0].setMaxFigure_InstockLineNo(lbl_MaxLenInstkTktLineNoAst.getText());
			//#CM688476
			// (Note))Receiving Ticket Line No.: Position
			param[0].setPosition_InstockLineNo(txt_InstkTktLineNoAstPst.getText());
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			WmsScheduler schedule = new DefineLoadDataMenuSCH();
			
			//#CM688477
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
				//#CM688478
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
				//#CM688479
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

	//#CM688480
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
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		dataLoad();
	}
}
//#CM688481
//end of class
