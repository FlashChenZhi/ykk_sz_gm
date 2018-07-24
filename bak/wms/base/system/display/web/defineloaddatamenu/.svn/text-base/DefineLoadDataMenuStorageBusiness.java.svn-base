// $Id: DefineLoadDataMenuStorageBusiness.java,v 1.2 2006/11/13 08:18:24 suresh Exp $

//#CM688486
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

//#CM688487
/**
 * Designer : T.Uehata <BR>
 * Maker : T.Kuroda <BR>
 * <BR>
 * Allow this class to provide a screen to set a field item for loading data (to set the Storage Plan field). <BR>
 * Set the contents input via input area for the parameter and pass it to the schedule. Add the setting of Storage Plan field. <BR>
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
 *   Planned Storage Date	(mandatory)*<BR>
 *   Item Code	(mandatory)*<BR>
 *   Packed Qty per Case	(mandatory)*<BR>
 *   Storage Qty (Total Bulk Qty) (mandatory)*<BR>
 * </DIR>
 * <BR>
 *   [Parameter]+ Require to input if the parameter is ticked to Enabled <BR>
 * <BR>
 * <DIR>
 *   Consignor Code +<BR>
 *   Consignor Name+<BR>
 *   Bundle ITF+<BR>
 *   Case ITF+<BR>
 *   Packed qty per bundle+<BR>
 *   Item Name+<BR>
 * 	 Piece Storage Location+<BR>
 * 	 Case Storage Location+<BR>
 * </DIR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/04</TD><TD>T.Kuroda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:24 $
 * @author  $Author: suresh $
 */
public class DefineLoadDataMenuStorageBusiness extends DefineLoadDataMenuStorage implements WMSConstants
{
	//#CM688488
	// Class fields --------------------------------------------------

	//#CM688489
	// Class variables -----------------------------------------------

	//#CM688490
	// Class method --------------------------------------------------

	//#CM688491
	// Constructors --------------------------------------------------

	//#CM688492
	// Public methods ------------------------------------------------
	
	//#CM688493
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
			//#CM688494
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM688495
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM688496
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM688497
			//Set the path to the help file.
			//#CM688498
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM688499
		// MSG-0009= Do you add it?
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}
	
	//#CM688500
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Set the cursor to Planned Storage Date (mandatory) Position. <BR>
	 * <DIR>
	 *   Field item [Initial Value] <BR>
	 *   <DIR>
	 *     Planned Storage Date      	(mandatory) Enabled [Ticked] <BR>
	 *     Item Code      	(mandatory) Enabled [Ticked] <BR>
	 *     Packed Qty per Case      	(mandatory) Enabled [Ticked] <BR>
	 *     Storage Qty (Total Bulk Qty)	(mandatory) Enabled [Ticked] <BR>
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
		//#CM688501
		// Title
		lbl_SettingName.setResourceKey(this.getViewState().getString("TITLE"));

		dataLoad();
	}

	//#CM688502
	// Package methods -----------------------------------------------

	//#CM688503
	// Protected methods ---------------------------------------------

	//#CM688504
	// Private methods -----------------------------------------------
	//#CM688505
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
	 *   2.Set the cursor to Planned Storage Date (mandatory) Position. <BR>
	 * </DIR>
	 * @throws Exception Report all exceptions.
	 */
	private void dataLoad() throws Exception
	{
		//#CM688506
		// Set the initial value of the focus to Planned Storage Date (mandatory) Position.
		setFocus(this.txt_StrgPlanDateReqPst);

		Connection conn = null;
		try
		{
			//#CM688507
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter initParam = new DefineDataParameter();
			initParam.setSelectDefineLoadData(DefineDataParameter.SELECTDEFINELOADDATA_STORAGE);
			WmsScheduler schedule = new DefineLoadDataMenuSCH();
			DefineDataParameter[] param = (DefineDataParameter[]) schedule.query(conn, initParam);

			if (param != null)
			{
				//#CM688508
				// Planned Storage Date (mandatory): Length
				txt_StrgPlanDateReqLen.setText(param[0].getFigure_PlanDate());
				//#CM688509
				// Planned Storage Date (mandatory): Max Length
				lbl_MaxLenStrgPlanDateReq.setText(param[0].getMaxFigure_PlanDate());
				//#CM688510
				// Planned Storage Date (mandatory): Position
				txt_StrgPlanDateReqPst.setText(param[0].getPosition_PlanDate());
				
				//#CM688511
				// Consignor Code: Enabled
				chk_CnsgnrCd.setChecked(param[0].getValid_ConsignorCode());
				//#CM688512
				// Consignor Code: Length
				txt_CnsgnrCdLen.setText(param[0].getFigure_ConsignorCode());
				//#CM688513
				// Consignor Code: Max Length
				lbl_MaxLenConsignorCode.setText(param[0].getMaxFigure_ConsignorCode());
				//#CM688514
				// Consignor Code: Position
				txt_CnsgnrCdPst.setText(param[0].getPosition_ConsignorCode());
				
				//#CM688515
				// Consignor Name: Enabled
				chk_CnsgnrNm.setChecked(param[0].getValid_ConsignorName());
				//#CM688516
				// Consignor Name: Length
				txt_CnsgnrNmLen.setText(param[0].getFigure_ConsignorName());
				//#CM688517
				// Consignor Name: Max Length
				lbl_MaxLenConsignorName.setText(param[0].getMaxFigure_ConsignorName());
				//#CM688518
				// Consignor Name: Position
				txt_CnsgnrNmPst.setText(param[0].getPosition_ConsignorName());
				
				//#CM688519
				// Item Code (mandatory): Length
				txt_ItemCdReqLen.setText(param[0].getFigure_ItemCode());
				//#CM688520
				// Item Code (mandatory): Max Length
				lbl_MaxLenItemCodeReq.setText(param[0].getMaxFigure_ItemCode());
				//#CM688521
				// Item Code (mandatory): Position
				txt_ItemCdReqPst.setText(param[0].getPosition_ItemCode());
				
				//#CM688522
				// Bundle ITF: Enabled
				chk_BundleItf.setChecked(param[0].getValid_BundleItf());
				//#CM688523
				// Bundle ITF: Length
				txt_BundleItfLen.setText(param[0].getFigure_BundleItf());
				//#CM688524
				// Bundle ITF: Max Length
				lbl_MaxLenBundleItf.setText(param[0].getMaxFigure_BundleItf());
				//#CM688525
				// Bundle ITF: Position
				txt_BundleItfPst.setText(param[0].getPosition_BundleItf());
				
				//#CM688526
				// Case ITF: Enabled
				chk_CaseItf.setChecked(param[0].getValid_Itf());
				//#CM688527
				// Case ITF: Length
				txt_CaseItfLen.setText(param[0].getFigure_Itf());
				//#CM688528
				// Case ITF: Max Length
				lbl_MaxLenCaseItf.setText(param[0].getMaxFigure_Itf());
				//#CM688529
				// Case ITF: Position
				txt_CaseItfPst.setText(param[0].getPosition_Itf());
				
				//#CM688530
				// Packed qty per bundle: Enabled
				chk_BundleEtr.setChecked(param[0].getValid_BundleEnteringQty());
				//#CM688531
				// Packed qty per bundle: Length
				txt_BundleEtrLen.setText(param[0].getFigure_BundleEnteringQty());
				//#CM688532
				// Packed qty per bundle: Max Length
				lbl_MaxLenBundleEtr.setText(param[0].getMaxFigure_BundleEnteringQty());
				//#CM688533
				// Packed qty per bundle: Position
				txt_BundleEtrPst.setText(param[0].getPosition_BundleEnteringQty());
				
				//#CM688534
				// Packed Qty per Case (mandatory): Length
				txt_CaseEtrReqLen.setText(param[0].getFigure_EnteringQty());
				//#CM688535
				// Packed Qty per Case (mandatory): Max Length
				lbl_MaxLenCaseEtrReq.setText(param[0].getMaxFigure_EnteringQty());
				//#CM688536
				// Packed Qty per Case (mandatory): Position
				txt_CaseEtrReqPst.setText(param[0].getPosition_EnteringQty());

				//#CM688537
				// Item Name: Enabled
				chk_NItemNm.setChecked(param[0].getValid_ItemName());
				//#CM688538
				// Item Name: Length
				txt_ItemNmLen.setText(param[0].getFigure_ItemName());
				//#CM688539
				// Item Name: Max Length
				lbl_MaxLenItemNm.setText(param[0].getMaxFigure_ItemName());
				//#CM688540
				// Item Name: Position
				txt_ItemNmPst.setText(param[0].getPosition_ItemName());
				
				//#CM688541
				// Storage Qty (Total Bulk Qty) (mandatory): Length
				txt_StrgQtyPtlReqLen.setText(param[0].getFigure_PlanQty());
				//#CM688542
				// Storage Qty (Total Bulk Qty) (mandatory): Max Length
				lbl_MaxLenStrgQtyPtlReq.setText(param[0].getMaxFigure_PlanQty());
				//#CM688543
				// Storage Qty (Total Bulk Qty) (mandatory): Position
				txt_StrgQtyPtlReqPst.setText(param[0].getPosition_PlanQty());
				
				//#CM688544
				// Piece Storage Location: Enabled
				chk_PieceStrgLct.setChecked(param[0].getValid_PieceLocation());
				//#CM688545
				// Piece Storage Location: Length
				txt_PieceStrgLctLen.setText(param[0].getFigure_PieceLocation());
				//#CM688546
				// Piece Storage Location: Max Length
				lbl_MaxLenPieceStrgLct.setText(param[0].getMaxFigure_PieceLocation());
				//#CM688547
				// Piece Storage Length: Position
				txt_PieceStrgLctPst.setText(param[0].getPosition_PieceLocation());
				
				//#CM688548
				// Case Storage Location: Enabled
				chk_CaseStrgLct.setChecked(param[0].getValid_CaseLocation());
				//#CM688549
				// Case Storage Location: Length
				txt_CaseStrgLctLen.setText(param[0].getFigure_CaseLocation());
				//#CM688550
				// Case Storage Location: Max Length
				lbl_MaxLenCaseStrgLct.setText(param[0].getMaxFigure_CaseLocation());
				//#CM688551
				// Case Storage Location: Position
				txt_CaseStrgLctPst.setText(param[0].getPosition_CaseLocation());
				
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
				//#CM688552
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
	
	//#CM688553
	/** 
	 * Allow this method to check whether a checkbox is ticked off or not.<BR>
	 * <BR>
	 * @return Return false if no item ticked off.
	 * @throws Exception Report all exceptions.
	 */
	private boolean checkCheckBox() throws Exception
	{
		if (!chk_NStrgPlanDateReq.getChecked()
				&& !chk_CnsgnrCd.getChecked()
				&& !chk_CnsgnrNm.getChecked()
				&& !chk_NItemCdReq.getChecked()
				&& !chk_BundleItf.getChecked()
				&& !chk_CaseItf.getChecked()
				&& !chk_BundleEtr.getChecked()
				&& !chk_NCaseEtrReq.getChecked()
				&& !chk_NItemNm.getChecked()
				&& !chk_NStrgQtyPtlReq.getChecked()
				&& !chk_PieceStrgLct.getChecked()
				&& !chk_CaseStrgLct.getChecked()
		)
		{
			return false;
		}
		return true;
	}
	
	//#CM688554
	// Event handler methods -----------------------------------------
	//#CM688555
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
		//#CM688556
		// Shift Screen
		forward("/system/defineloaddatamenu/DefineLoadDataMenu.do");
	}

	//#CM688557
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

	//#CM688558
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
	 *     4.Set the cursor to Planned Storage Date (mandatory) Length. <BR>
	 *   </DIR>
	 * </DIR>
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		//#CM688559
		// Set the initial value of the focus to Planned Storage Date (mandatory) Length.
		setFocus(txt_StrgPlanDateReqPst);

		//#CM688560
		// Check for input.
		WmsCheckker.checkDefine(lbl_StrgPlanDateReq, chk_NStrgPlanDateReq, txt_StrgPlanDateReqLen, lbl_MaxLenStrgPlanDateReq, txt_StrgPlanDateReqPst);
		WmsCheckker.checkDefine(lbl_ConsignorCode, chk_CnsgnrCd, txt_CnsgnrCdLen, lbl_MaxLenConsignorCode, txt_CnsgnrCdPst);
		WmsCheckker.checkDefine(lbl_ConsignorName, chk_CnsgnrNm, txt_CnsgnrNmLen, lbl_MaxLenConsignorName, txt_CnsgnrNmPst);
		WmsCheckker.checkDefine(lbl_ItemCodeRequired, chk_NItemCdReq, txt_ItemCdReqLen, lbl_MaxLenItemCodeReq, txt_ItemCdReqPst);
		WmsCheckker.checkDefine(lbl_BundleItf, chk_BundleItf, txt_BundleItfLen, lbl_MaxLenBundleItf, txt_BundleItfPst);
		WmsCheckker.checkDefine(lbl_CaseItf, chk_CaseItf, txt_CaseItfLen, lbl_MaxLenCaseItf, txt_CaseItfPst);
		WmsCheckker.checkDefine(lbl_BundleEntering, chk_BundleEtr, txt_BundleEtrLen, lbl_MaxLenBundleEtr, txt_BundleEtrPst);
		WmsCheckker.checkDefine(lbl_CaseEtrReq, chk_NCaseEtrReq, txt_CaseEtrReqLen, lbl_MaxLenCaseEtrReq, txt_CaseEtrReqPst);
		WmsCheckker.checkDefine(lbl_ItemName, chk_NItemNm, txt_ItemNmLen, lbl_MaxLenItemNm, txt_ItemNmPst);
		WmsCheckker.checkDefine(lbl_StrgQtyPtlReq, chk_NStrgQtyPtlReq, txt_StrgQtyPtlReqLen, lbl_MaxLenStrgQtyPtlReq, txt_StrgQtyPtlReqPst);
		WmsCheckker.checkDefine(lbl_PieceStrgLct, chk_PieceStrgLct, txt_PieceStrgLctLen, lbl_MaxLenPieceStrgLct, txt_PieceStrgLctPst);		
		WmsCheckker.checkDefine(lbl_CaseStrgLct, chk_CaseStrgLct, txt_CaseStrgLctLen, lbl_MaxLenCaseStrgLct, txt_CaseStrgLctPst);

		//#CM688561
		// Check the checkbox.
		if (!checkCheckBox())
		{
			//#CM688562
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

			//#CM688563
			// Set the value for the parameter.
			param[0].setSelectDefineLoadData(DefineDataParameter.SELECTDEFINELOADDATA_STORAGE);

			//#CM688564
			// Planned Storage Date (mandatory): Enabled
			param[0].setValid_PlanDate(chk_NStrgPlanDateReq.getChecked());
			//#CM688565
			// Planned Storage Date (mandatory): Length
			param[0].setFigure_PlanDate(txt_StrgPlanDateReqLen.getText());
			//#CM688566
			// Planned Storage Date (mandatory): Max Length
			param[0].setMaxFigure_PlanDate(lbl_MaxLenStrgPlanDateReq.getText());
			//#CM688567
			// Planned Storage Date (mandatory): Position
			param[0].setPosition_PlanDate(txt_StrgPlanDateReqPst.getText());
			
			//#CM688568
			// Consignor Code: Enabled
			param[0].setValid_ConsignorCode(chk_CnsgnrCd.getChecked());
			//#CM688569
			// Consignor Code: Length
			param[0].setFigure_ConsignorCode(txt_CnsgnrCdLen.getText());
			//#CM688570
			// Consignor Code: Max Length
			param[0].setMaxFigure_ConsignorCode(lbl_MaxLenConsignorCode.getText());
			//#CM688571
			// Consignor Code: Position
			param[0].setPosition_ConsignorCode(txt_CnsgnrCdPst.getText());
			
			//#CM688572
			// Consignor Name: Enabled
			param[0].setValid_ConsignorName(chk_CnsgnrNm.getChecked());
			//#CM688573
			// Consignor Name: Length
			param[0].setFigure_ConsignorName(txt_CnsgnrNmLen.getText());
			//#CM688574
			// Consignor Name: Max Length
			param[0].setMaxFigure_ConsignorName(lbl_MaxLenConsignorName.getText());
			//#CM688575
			// Consignor Name: Position
			param[0].setPosition_ConsignorName(txt_CnsgnrNmPst.getText());
			
			//#CM688576
			// Item Code (mandatory): Enabled
			param[0].setValid_ItemCode(chk_NItemCdReq.getChecked());
			//#CM688577
			// Item Code (mandatory): Length
			param[0].setFigure_ItemCode(txt_ItemCdReqLen.getText());
			//#CM688578
			// Item Code (mandatory): Max Length
			param[0].setMaxFigure_ItemCode(lbl_MaxLenItemCodeReq.getText());
			//#CM688579
			// Item Code (mandatory): Position
			param[0].setPosition_ItemCode(txt_ItemCdReqPst.getText());
			
			//#CM688580
			// Bundle ITF: Enabled
			param[0].setValid_BundleItf(chk_BundleItf.getChecked());
			//#CM688581
			// Bundle ITF: Length
			param[0].setFigure_BundleItf(txt_BundleItfLen.getText());
			//#CM688582
			// Bundle ITF: Max Length
			param[0].setMaxFigure_BundleItf(lbl_MaxLenBundleItf.getText());
			//#CM688583
			// Bundle ITF: Position
			param[0].setPosition_BundleItf(txt_BundleItfPst.getText());
			
			//#CM688584
			// Case ITF: Enabled
			param[0].setValid_Itf(chk_CaseItf.getChecked());
			//#CM688585
			// Case ITF: Length
			param[0].setFigure_Itf(txt_CaseItfLen.getText());
			//#CM688586
			// Case ITF: Max Length
			param[0].setMaxFigure_Itf(lbl_MaxLenCaseItf.getText());
			//#CM688587
			// Case ITF: Position
			param[0].setPosition_Itf(txt_CaseItfPst.getText());
			
			//#CM688588
			// Packed qty per bundle: Enabled
			param[0].setValid_BundleEnteringQty(chk_BundleEtr.getChecked());
			//#CM688589
			// Packed qty per bundle: Length
			param[0].setFigure_BundleEnteringQty(txt_BundleEtrLen.getText());
			//#CM688590
			// Packed qty per bundle: Max Length
			param[0].setMaxFigure_BundleEnteringQty(lbl_MaxLenBundleEtr.getText());
			//#CM688591
			// Packed qty per bundle: Position
			param[0].setPosition_BundleEnteringQty(txt_BundleEtrPst.getText());
			
			//#CM688592
			// Packed Qty per Case (mandatory): Enabled
			param[0].setValid_EnteringQty(chk_NCaseEtrReq.getChecked());
			//#CM688593
			// Packed Qty per Case (mandatory): Length
			param[0].setFigure_EnteringQty(txt_CaseEtrReqLen.getText());
			//#CM688594
			// Packed Qty per Case (mandatory): Max Length
			param[0].setMaxFigure_EnteringQty(lbl_MaxLenCaseEtrReq.getText());
			//#CM688595
			// Packed Qty per Case (mandatory): Position
			param[0].setPosition_EnteringQty(txt_CaseEtrReqPst.getText());

			//#CM688596
			// Item Name (mandatory): Enabled
			param[0].setValid_ItemName(chk_NItemNm.getChecked());
			//#CM688597
			// Item Name (mandatory): Length
			param[0].setFigure_ItemName(txt_ItemNmLen.getText());
			//#CM688598
			// Item Name (mandatory): Max Length
			param[0].setMaxFigure_ItemName(lbl_MaxLenItemNm.getText());
			//#CM688599
			// Item Name (mandatory): Position
			param[0].setPosition_ItemName(txt_ItemNmPst.getText());
			
			//#CM688600
			// Storage Qty (Total Bulk Qty) (mandatory): Enabled
			param[0].setValid_PlanQty(chk_NStrgQtyPtlReq.getChecked());
			//#CM688601
			// Storage Qty (Total Bulk Qty) (mandatory): Length
			param[0].setFigure_PlanQty(txt_StrgQtyPtlReqLen.getText());
			//#CM688602
			// Storage Qty (Total Bulk Qty) (mandatory): Max Length
			param[0].setMaxFigure_PlanQty(lbl_MaxLenStrgQtyPtlReq.getText());
			//#CM688603
			// Storage Qty (Total Bulk Qty) (mandatory): Position
			param[0].setPosition_PlanQty(txt_StrgQtyPtlReqPst.getText());
			
			//#CM688604
			// Piece Storage Location: Enabled
			param[0].setValid_PieceLocation(chk_PieceStrgLct.getChecked());
			//#CM688605
			// Piece Storage: Length
			param[0].setFigure_PieceLocation(txt_PieceStrgLctLen.getText());
			//#CM688606
			// Piece Storage: Max Length
			param[0].setMaxFigure_PieceLocation(lbl_MaxLenStrgQtyPtlReq.getText());
			//#CM688607
			// Piece Storage: Position
			param[0].setPosition_PieceLocation(txt_PieceStrgLctPst.getText());
			
			//#CM688608
			// Case Storage Location: Enabled
			param[0].setValid_CaseLocation(chk_CaseStrgLct.getChecked());
			//#CM688609
			// Case Storage Location: Length
			param[0].setFigure_CaseLocation(txt_CaseStrgLctLen.getText());
			//#CM688610
			// Case Storage Location: Max Length
			param[0].setMaxFigure_CaseLocation(lbl_MaxLenCaseStrgLct.getText());
			//#CM688611
			// Case Storage Location: Position
			param[0].setPosition_CaseLocation(txt_CaseStrgLctPst.getText());
			
			//#CM688612
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
				//#CM688613
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
				//#CM688614
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

	//#CM688615
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
	 *   2.Set the cursor to Planned Storage Date (mandatory) Length. <BR>
	 * </DIR>
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		dataLoad();
	}
}
//#CM688616
//end of class
