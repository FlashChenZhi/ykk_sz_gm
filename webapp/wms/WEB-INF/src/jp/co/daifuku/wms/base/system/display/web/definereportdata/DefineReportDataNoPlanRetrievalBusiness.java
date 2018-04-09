// $Id: DefineReportDataNoPlanRetrievalBusiness.java,v 1.2 2006/11/13 08:18:46 suresh Exp $

//#CM689493
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

//#CM689494
/**
 * Designer :  S.Ishigane <BR>
 * Maker :     T.Uehata <BR>
 * <BR>
 * Allow this class to set a field for reporting data (to set the Unplanned Picking Result field).<BR>
 * Set the searched value via the schedule for each field item.
 * Set the contents entered via screen for the parameter. Allow the schedule to set the Report Data field (set the Unplanned Picking Result field) based on the parameter.<BR>
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
 * 	 Item Code +<BR>
 * 	 Packed qty per bundle+<BR>
 *   Packed Qty per Case+<BR>
 *   Item Name+<BR>
 *   Picking Qty (Total Bulk Qty) +<BR>
 *   Piece Picking Location +<BR>
 *   Case Picking Location+<BR>
 *   Piece Order No.+<BR>
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
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:46 $
 * @author  $Author: suresh $
 */
public class DefineReportDataNoPlanRetrievalBusiness extends DefineReportDataNoPlanRetrieval implements WMSConstants
{
	private static final String DO_DEFINEREPORT = "/system/definereportdata/DefineReportData.do";

	//#CM689495
	// Class fields --------------------------------------------------

	//#CM689496
	// Class variables -----------------------------------------------

	//#CM689497
	// Class method --------------------------------------------------

	//#CM689498
	// Constructors --------------------------------------------------

	//#CM689499
	// Public methods ------------------------------------------------
	//#CM689500
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
			//#CM689501
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM689502
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM689503
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM689504
			//Set the path to the help file.
			//#CM689505
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM689506
		// MSG-0009= Do you add it?
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}
	
	//#CM689507
	/**
	 * Show the Initial Display.<BR>
	 * <BR>
	 * Display a for setting the field items of the functions selected via the previous screen. <BR>
	 * <BR>
	 * <DIR>
	 *   Enabled/Disabled: Selected<BR>
	 *   Length [read-only] <BR>
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
		//#CM689508
		// Title
		lbl_SettingName.setResourceKey(this.getViewState().getString("TITLE"));

		//#CM689509
		// Check that the length is read-only. Length.
		txt_CnsgnrCdLen.setReadOnly(true);
		txt_CnsgnrNmLen.setReadOnly(true);
		txt_CustCdLen.setReadOnly(true);
		txt_CustNmLen.setReadOnly(true);
		txt_ItemCdLen.setReadOnly(true);
		txt_BundleItfLen.setReadOnly(true);
		txt_CaseItfLen.setReadOnly(true);
		txt_BundleEtrLen.setReadOnly(true);
		txt_CaseEtrLen.setReadOnly(true);
		txt_ItemNmLen.setReadOnly(true);
		txt_PieceRtrivlLctLen.setReadOnly(true);
		txt_CaseRtrivlLctLen.setReadOnly(true);
		txt_RsltPieceQtyLen.setReadOnly(true);
		txt_RsltCaseQtyLen.setReadOnly(true);
		txt_RtrivlRsltDateLen.setReadOnly(true);
		txt_UseByDateLen.setReadOnly(true);

		setInistView();
	}

	//#CM689510
	// Package methods -----------------------------------------------

	//#CM689511
	// Protected methods ---------------------------------------------

	//#CM689512
	// Private methods -----------------------------------------------

	//#CM689513
	/** 
	 * Allow this method to set the initial values for screen.<BR>
	 * <BR>
	 * Summary: Sets up the settings of each field item in the screen from property file.<BR>
	 * 
	 * @throws Exception Report all exceptions.
	 */
	private void setInistView() throws Exception
	{
		//#CM689514
		// Set the initial value of the focus to Consignor Code Enabled.
		setFocus(chk_CnsgnrCd);

		Connection conn = null;
		try
		{
			//#CM689515
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter initParam = new DefineDataParameter();
			initParam.setSelectDefineReportData(
				DefineDataParameter.SELECTDEFINEREPORTDATA_NOPLANRETRIEVAL);
			WmsScheduler schedule = new DefineReportDataSCH();
			DefineDataParameter[] param = (DefineDataParameter[]) schedule.query(conn, initParam);

			if (param != null)
			{
				//#CM689516
				// Consignor Code: Enabled
				chk_CnsgnrCd.setChecked(param[0].getValid_ConsignorCode());
				//#CM689517
				// Consignor Code: Length
				txt_CnsgnrCdLen.setText(param[0].getFigure_ConsignorCode());
				//#CM689518
				// Consignor Code: Max Length
				lbl_MaxLenCnsgnrCd.setText(param[0].getMaxFigure_ConsignorCode());
				//#CM689519
				// Consignor Code: Position
				txt_CnsgnrCdPst.setText(param[0].getPosition_ConsignorCode());

				//#CM689520
				// Consignor Name: Enabled
				chk_CnsgnrNm.setChecked(param[0].getValid_ConsignorName());
				//#CM689521
				// Consignor Name: Length
				txt_CnsgnrNmLen.setText(param[0].getFigure_ConsignorName());
				//#CM689522
				// Consignor Name: Max Length
				lbl_MaxLenCnsgnrNm.setText(param[0].getMaxFigure_ConsignorName());
				//#CM689523
				// Consignor Name: Position
				txt_CnsgnrNmPst.setText(param[0].getPosition_ConsignorName());

				//#CM689524
				// Customer Code: Enabled
				chk_CustCd.setChecked(param[0].getValid_CustomerCode());
				//#CM689525
				// Customer Code: Length
				txt_CustCdLen.setText(param[0].getFigure_CustomerCode());
				//#CM689526
				// Customer Code: Max Length
				lbl_MaxLenCustCd.setText(param[0].getMaxFigure_CustomerCode());
				//#CM689527
				// Customer Code: Position
				txt_CustCdPst.setText(param[0].getPosition_CustomerCode());

				//#CM689528
				// Customer Name: Enabled
				chk_CustNm.setChecked(param[0].getValid_CustomerName());
				//#CM689529
				// Customer Name: Length
				txt_CustNmLen.setText(param[0].getFigure_CustomerName());
				//#CM689530
				// Customer Name: Max Length
				lbl_MaxLenCustNm.setText(param[0].getMaxFigure_CustomerName());
				//#CM689531
				// Customer Name: Position
				txt_CustNmPst.setText(param[0].getPosition_CustomerName());

				//#CM689532
				// Item Code: Enabled
				chk_ItemCd.setChecked(param[0].getValid_ItemCode());
				//#CM689533
				// Item Code: Length
				txt_ItemCdLen.setText(param[0].getFigure_ItemCode());
				//#CM689534
				// Item Code: Max Length
				lbl_MaxLenItemCd.setText(param[0].getMaxFigure_ItemCode());
				//#CM689535
				// Item Code: Position
				txt_ItemCdPst.setText(param[0].getPosition_ItemCode());

				//#CM689536
				// Bundle ITF: Enabled
				chk_BundleItf.setChecked(param[0].getValid_BundleItf());
				//#CM689537
				// Bundle ITF: Length
				txt_BundleItfLen.setText(param[0].getFigure_BundleItf());
				//#CM689538
				// Bundle ITF: Max Length
				lbl_MaxLenBundleItf.setText(param[0].getMaxFigure_BundleItf());
				//#CM689539
				// Bundle ITF: Position
				txt_BundleItfPst.setText(param[0].getPosition_BundleItf());

				//#CM689540
				// Case ITF: Enabled
				chk_CaseItf.setChecked(param[0].getValid_Itf());
				//#CM689541
				// Case ITF: Length
				txt_CaseItfLen.setText(param[0].getFigure_Itf());
				//#CM689542
				// Case ITF: Max Length
				lbl_MaxLenCaseItf.setText(param[0].getMaxFigure_Itf());
				//#CM689543
				// Case ITF: Position
				txt_CaseItfPst.setText(param[0].getPosition_Itf());

				//#CM689544
				// Packed qty per bundle: Enabled
				chk_BundleEtr.setChecked(param[0].getValid_BundleEnteringQty());
				//#CM689545
				// Packed qty per bundle: Length
				txt_BundleEtrLen.setText(param[0].getFigure_BundleEnteringQty());
				//#CM689546
				// Packed qty per bundle: Max Length
				lbl_MaxLenBundleEtr.setText(param[0].getMaxFigure_BundleEnteringQty());
				//#CM689547
				// Packed qty per bundle: Position
				txt_BundleEtrPst.setText(param[0].getPosition_BundleEnteringQty());

				//#CM689548
				// Packed Qty per Case: Enabled
				chk_CaseEtr.setChecked(param[0].getValid_EnteringQty());
				//#CM689549
				// Packed Qty per Case: Length
				txt_CaseEtrLen.setText(param[0].getFigure_EnteringQty());
				//#CM689550
				// Packed Qty per Case: Max Length
				lbl_MaxLenCaseEtr.setText(param[0].getMaxFigure_EnteringQty());
				//#CM689551
				// Packed Qty per Case: Position
				txt_CaseEtrPst.setText(param[0].getPosition_EnteringQty());

				//#CM689552
				// Item Name: Enabled
				chk_ItemNm.setChecked(param[0].getValid_ItemName());
				//#CM689553
				// Item Name: Length
				txt_ItemNmLen.setText(param[0].getFigure_ItemName());
				//#CM689554
				// Item Name: Max Length
				lbl_MaxLenItemNm.setText(param[0].getMaxFigure_ItemName());
				//#CM689555
				// Item Name: Position
				txt_ItemNmPst.setText(param[0].getPosition_ItemName());

				//#CM689556
				// Piece Picking Location: Enabled
				chk_PieceRtrivlLct.setChecked(param[0].getValid_PieceLocation());
				//#CM689557
				// Piece Picking Location: Length
				txt_PieceRtrivlLctLen.setText(param[0].getFigure_PieceLocation());
				//#CM689558
				// Piece Picking Location: Max Length
				lbl_MaxLenPieceRtrivlLct.setText(param[0].getMaxFigure_PieceLocation());
				//#CM689559
				// Piece Picking Location: Position
				txt_PieceRtrivlLctPst.setText(param[0].getPosition_PieceLocation());

				//#CM689560
				// Case Picking Location: Enabled
				chk_CaseRtrivlLct.setChecked(param[0].getValid_CaseLocation());
				//#CM689561
				// Case Picking Location: Length
				txt_CaseRtrivlLctLen.setText(param[0].getFigure_CaseLocation());
				//#CM689562
				// Case Picking Location: Max Length
				lbl_MaxLenCaseRtrivlLct.setText(param[0].getMaxFigure_CaseLocation());
				//#CM689563
				// Case Picking Location: Position
				txt_CaseRtrivlLctPst.setText(param[0].getPosition_CaseLocation());

				//#CM689564
				// Result Piece Qty: Enabled
				chk_RsltPieceQty.setChecked(param[0].getValid_PieceResultQty());
				//#CM689565
				// Result Piece Qty: Length
				txt_RsltPieceQtyLen.setText(param[0].getFigure_PieceResultQty());
				//#CM689566
				// Result Piece Qty: Max Length
				lbl_MaxLenRsltPieceQty.setText(param[0].getMaxFigure_PieceResultQty());
				//#CM689567
				// Result Piece Qty: Position
				txt_RsltPieceQtyPst.setText(param[0].getPosition_PieceResultQty());

				//#CM689568
				// Result Case Qty: Enabled
				chk_RsltCaseQty.setChecked(param[0].getValid_CaseResultQty());
				//#CM689569
				// Result Case Qty: Length
				txt_RsltCaseQtyLen.setText(param[0].getFigure_CaseResultQty());
				//#CM689570
				// Result Case Qty: Max Length
				lbl_MaxLenRsltCaseQty.setText(param[0].getMaxFigure_CaseResultQty());
				//#CM689571
				// Result Case Qty: Position
				txt_RsltCaseQtyPst.setText(param[0].getPosition_CaseResultQty());

				//#CM689572
				// Picking Result Date: Enabled
				chk_RtrivlRsltDate.setChecked(param[0].getValid_WorkDate());
				//#CM689573
				// Picking Result Date: Length
				txt_RtrivlRsltDateLen.setText(param[0].getFigure_WorkDate());
				//#CM689574
				// Picking Result Date: Max Length
				lbl_MaxLenRtrivlRsltDate.setText(param[0].getMaxFigure_WorkDate());
				//#CM689575
				// Picking Result Date: Position
				txt_RtrivlRsltDatePst.setText(param[0].getPosition_WorkDate());

				//#CM689576
				// Expiry Date: Enabled
				chk_UseByDate.setChecked(param[0].getValid_UseByDate());
				//#CM689577
				// Expiry Date: Length
				txt_UseByDateLen.setText(param[0].getFigure_UseByDate());
				//#CM689578
				// Expiry Date: Max Length
				lbl_MaxLenUseByDate.setText(param[0].getMaxFigure_UseByDate());
				//#CM689579
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
				//#CM689580
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
	


	//#CM689581
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

		//#CM689582
		// Consignor Code: Position
		if (position.getText().equals(txt_CnsgnrCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689583
		// Consignor Name: Position
		if (position.getText().equals(txt_CnsgnrNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689584
		// Customer Code: Position
		if (position.getText().equals(txt_CustCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689585
		// Customer Name: Position
		if (position.getText().equals(txt_CustNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689586
		// Item Code: Position
		if (position.getText().equals(txt_ItemCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689587
		// Bundle ITF: Position
		if (position.getText().equals(txt_BundleItfPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689588
		// Case ITF: Position
		if (position.getText().equals(txt_CaseItfPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689589
		// Packed qty per bundle: Position
		if (position.getText().equals(txt_BundleEtrPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689590
		// Packed Qty per Case: Position
		if (position.getText().equals(txt_CaseEtrPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689591
		// Item Name: Position
		if (position.getText().equals(txt_ItemNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689592
		// Piece Picking Location: Position
		if (position.getText().equals(txt_PieceRtrivlLctPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689593
		// Case Picking Location: Position
		if (position.getText().equals(txt_CaseRtrivlLctPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689594
		// Result Piece Qty: Position
		if (position.getText().equals(txt_RsltPieceQtyPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689595
		// Result Case Qty: Position
		if (position.getText().equals(txt_RsltCaseQtyPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689596
		// Picking Result Date: Position
		if (position.getText().equals(txt_RtrivlRsltDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689597
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

	//#CM689598
	/** 
	 * Allow this method to check wheter the position is duplicated or not in a sequential order.<BR>
	 * <BR>
	 * @return Return false if two or more data occupies a single position.
	 * @throws Exception Report all exceptions.
	 */
	private boolean checkRepeat() throws Exception
	{

		boolean repeatChkFlg = true;

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

		if (repeatChkFlg && chk_PieceRtrivlLct.getChecked())
		{
			repeatChkFlg = positionCheck(txt_PieceRtrivlLctPst);
		}

		if (repeatChkFlg && chk_CaseRtrivlLct.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CaseRtrivlLctPst);
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

		if (repeatChkFlg && chk_UseByDate.getChecked())
		{
			repeatChkFlg = positionCheck(txt_UseByDatePst);
		}
		
		return repeatChkFlg;
	}

	//#CM689599
	/** 
	 * Allow this method to check whether a checkbox is ticked off or not.<BR>
	 * <BR>
	 * @return Return false if no item ticked off.
	 * @throws Exception Report all exceptions.
	 */
	private boolean checkCheckBox() throws Exception
	{
		if (!chk_CnsgnrCd.getChecked()
			&& !chk_CnsgnrNm.getChecked()
			&& !chk_CustCd.getChecked()
			&& !chk_CustNm.getChecked()
			&& !chk_ItemCd.getChecked()
			&& !chk_BundleItf.getChecked()
			&& !chk_CaseItf.getChecked()
			&& !chk_BundleEtr.getChecked()
			&& !chk_CaseEtr.getChecked()
			&& !chk_ItemNm.getChecked()
			&& !chk_PieceRtrivlLct.getChecked()
			&& !chk_CaseRtrivlLct.getChecked()
			&& !chk_RsltPieceQty.getChecked()
			&& !chk_RsltCaseQty.getChecked()
			&& !chk_RtrivlRsltDate.getChecked()
			&& !chk_UseByDate.getChecked())
		{
			return false;
		}
		return true;
	}
	//#CM689600
	// Event handler methods -----------------------------------------
	//#CM689601
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689602
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689603
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Back_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689604
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
		//#CM689605
		// Shift to the original screen.
		forward(DO_DEFINEREPORT);
	}

	//#CM689606
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689607
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

	//#CM689608
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Valid1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689609
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DigitsUseLength1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689610
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLength1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689611
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Position1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689612
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Valid2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689613
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DigitsUseLength2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689614
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLength2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689615
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Position2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689616
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689617
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689618
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689619
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689620
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689621
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689622
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689623
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689624
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689625
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689626
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689627
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689628
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689629
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689630
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689631
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689632
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689633
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689634
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689635
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689636
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689637
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689638
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689639
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689640
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689641
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689642
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689643
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689644
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689645
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689646
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PiceRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689647
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PieceRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689648
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PieceRtrivlLct_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689649
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLctLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689650
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLctLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689651
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLctLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689652
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenPieceRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689653
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLctPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689654
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLctPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689655
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLctPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689656
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689657
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CustCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689658
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CustCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689659
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689660
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689661
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689662
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCustCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689663
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689664
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689665
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689666
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689667
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689668
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseRtrivlLct_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689669
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLctLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689670
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLctLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689671
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLctLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689672
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689673
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLctPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689674
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLctPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689675
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLctPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689676
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689677
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CustNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689678
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CustNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689679
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689680
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689681
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689682
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCustNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689683
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689684
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689685
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689686
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ResultPieceQtyTwoByte_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689687
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689688
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltPieceQty_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689689
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689690
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689691
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689692
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRsltPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689693
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689694
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689695
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689696
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689697
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689698
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689699
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689700
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689701
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689702
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689703
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689704
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689705
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689706
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ResultCaseQtyTwoByte_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689707
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689708
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltCaseQty_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689709
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689710
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689711
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689712
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRsltCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689713
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689714
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689715
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689716
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689717
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689718
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleItf_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689719
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689720
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689721
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689722
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenBundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689723
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689724
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689725
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689726
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RtrivlResultDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689727
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RtrivlRsltDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689728
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RtrivlRsltDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689729
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689730
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689731
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689732
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRtrivlRsltDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689733
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689734
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689735
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689736
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689737
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689738
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseItf_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689739
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689740
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689741
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689742
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689743
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689744
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689745
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689746
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689747
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689748
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleEtr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689749
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689750
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689751
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689752
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenBundleEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689753
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689754
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689755
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689756
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689757
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689758
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_UseByDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689759
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689760
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689761
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689762
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenUseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689763
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689764
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689765
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689766
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689767
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689768
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseEtr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689769
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689770
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689771
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689772
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689773
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689774
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689775
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689776
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}


	//#CM689777
	/** 
	 * Clicking on Add button for field items set for reporting data (setting of Unplanned Picking Result field) invokes this.<BR>
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
		//#CM689778
		// Set the initial value of the focus to Consignor Code Enabled.
		setFocus(chk_CnsgnrCd);
		
		//#CM689779
		// Check the checkbox.
		if (!checkCheckBox())
		{
			//#CM689780
			// 6023174=Please select 1 or more items.
			message.setMsgResourceKey("6023174");
			return;
		}
		
		//#CM689781
		// Check for input.
		WmsCheckker.checkDefine(lbl_ConsignorCode, chk_CnsgnrCd, txt_CnsgnrCdLen, lbl_MaxLenCnsgnrCd, txt_CnsgnrCdPst);
		WmsCheckker.checkDefine(lbl_ConsignorName, chk_CnsgnrNm, txt_CnsgnrNmLen, lbl_MaxLenCnsgnrNm, txt_CnsgnrNmPst);
		WmsCheckker.checkDefine(lbl_CustomerCode, chk_CustCd, txt_CustCdLen, lbl_MaxLenCustCd, txt_CustCdPst);
		WmsCheckker.checkDefine(lbl_CustomerName, chk_CustNm, txt_CustNmLen, lbl_MaxLenCustNm, txt_CustNmPst);
		WmsCheckker.checkDefine(lbl_ItemCode, chk_ItemCd, txt_ItemCdLen, lbl_MaxLenItemCd, txt_ItemCdPst);
		WmsCheckker.checkDefine(lbl_BundleItf, chk_BundleItf, txt_BundleItfLen, lbl_MaxLenBundleItf, txt_BundleItfPst);
		WmsCheckker.checkDefine(lbl_CaseItf, chk_CaseItf, txt_CaseItfLen, lbl_MaxLenCaseItf, txt_CaseItfPst);
		WmsCheckker.checkDefine(lbl_BundleEntering, chk_BundleEtr, txt_BundleEtrLen, lbl_MaxLenBundleEtr, txt_BundleEtrPst);
		WmsCheckker.checkDefine(lbl_CaseEntering, chk_CaseEtr, txt_CaseEtrLen, lbl_MaxLenCaseEtr, txt_CaseEtrPst);
		WmsCheckker.checkDefine(lbl_ItemName, chk_ItemNm, txt_ItemNmLen, lbl_MaxLenItemNm, txt_ItemNmPst);
		WmsCheckker.checkDefine(lbl_PiceRtrivlLct, chk_PieceRtrivlLct, txt_PieceRtrivlLctLen, lbl_MaxLenPieceRtrivlLct, txt_PieceRtrivlLctPst);
		WmsCheckker.checkDefine(lbl_CaseRtrivlLct, chk_CaseRtrivlLct, txt_CaseRtrivlLctLen, lbl_MaxLenCaseRtrivlLct, txt_CaseRtrivlLctPst);
		WmsCheckker.checkDefine(lbl_ResultPieceQtyTwoByte, chk_RsltPieceQty, txt_RsltPieceQtyLen, lbl_MaxLenRsltPieceQty, txt_RsltPieceQtyPst);
		WmsCheckker.checkDefine(lbl_ResultCaseQtyTwoByte, chk_RsltCaseQty, txt_RsltCaseQtyLen, lbl_MaxLenRsltCaseQty, txt_RsltCaseQtyPst);
		WmsCheckker.checkDefine(lbl_RtrivlResultDate, chk_RtrivlRsltDate, txt_RtrivlRsltDateLen, lbl_MaxLenRtrivlRsltDate, txt_RtrivlRsltDatePst);
		WmsCheckker.checkDefine(lbl_UseByDate, chk_UseByDate, txt_UseByDateLen, lbl_MaxLenUseByDate, txt_UseByDatePst);

		Connection conn = null;

		try
		{
			//#CM689782
			// Check for duplicate Position.
			if (!checkRepeat())
			{
				//#CM689783
				// Set the Error Message.
				//#CM689784
				// 6023098=No duplicate of sequential order is allowed.
				message.setMsgResourceKey("6023098");
				return;
			}

			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter[] param = new DefineDataParameter[1];
			param[0] = new DefineDataParameter();

			WmsScheduler schedule = new DefineReportDataSCH();

			//#CM689785
			// Set the value for the parameter.
			param[0].setSelectDefineReportData(DefineDataParameter.SELECTDEFINEREPORTDATA_NOPLANRETRIEVAL);

			//#CM689786
			// Consignor Code: Enabled
			param[0].setValid_ConsignorCode(chk_CnsgnrCd.getChecked());
			//#CM689787
			// Consignor Code: Length
			param[0].setFigure_ConsignorCode(txt_CnsgnrCdLen.getText());
			//#CM689788
			// Consignor Code: Max Length
			param[0].setMaxFigure_ConsignorCode(lbl_MaxLenCnsgnrCd.getText());
			//#CM689789
			// Consignor Code: Position
			param[0].setPosition_ConsignorCode(txt_CnsgnrCdPst.getText());

			//#CM689790
			// Consignor Name: Enabled
			param[0].setValid_ConsignorName(chk_CnsgnrNm.getChecked());
			//#CM689791
			// Consignor Name: Length
			param[0].setFigure_ConsignorName(txt_CnsgnrNmLen.getText());
			//#CM689792
			// Consignor Name: Max Length
			param[0].setMaxFigure_ConsignorName(lbl_MaxLenCnsgnrNm.getText());
			//#CM689793
			// Consignor Name: Position
			param[0].setPosition_ConsignorName(txt_CnsgnrNmPst.getText());

			//#CM689794
			// Customer Code: Enabled
			param[0].setValid_CustomerCode(chk_CustCd.getChecked());
			//#CM689795
			// Customer Code: Length
			param[0].setFigure_CustomerCode(txt_CustCdLen.getText());
			//#CM689796
			// Customer Code: Max Length
			param[0].setMaxFigure_CustomerCode(lbl_MaxLenCustCd.getText());
			//#CM689797
			// Customer Code: Position
			param[0].setPosition_CustomerCode(txt_CustCdPst.getText());

			//#CM689798
			// Customer Name: Enabled
			param[0].setValid_CustomerName(chk_CustNm.getChecked());
			//#CM689799
			// Customer Name: Length
			param[0].setFigure_CustomerName(txt_CustNmLen.getText());
			//#CM689800
			// Customer Name: Max Length
			param[0].setMaxFigure_CustomerName(lbl_MaxLenCustNm.getText());
			//#CM689801
			// Customer Name: Position
			param[0].setPosition_CustomerName(txt_CustNmPst.getText());

			//#CM689802
			// Item Code: Enabled
			param[0].setValid_ItemCode(chk_ItemCd.getChecked());
			//#CM689803
			// Item Code: Length
			param[0].setFigure_ItemCode(txt_ItemCdLen.getText());
			//#CM689804
			// Item Code: Max Length
			param[0].setMaxFigure_ItemCode(lbl_MaxLenItemCd.getText());
			//#CM689805
			// Item Code: Position
			param[0].setPosition_ItemCode(txt_ItemCdPst.getText());

			//#CM689806
			// Bundle ITF: Enabled
			param[0].setValid_BundleItf(chk_BundleItf.getChecked());
			//#CM689807
			// Bundle ITF: Length
			param[0].setFigure_BundleItf(txt_BundleItfLen.getText());
			//#CM689808
			// Bundle ITF: Max Length
			param[0].setMaxFigure_BundleItf(lbl_MaxLenBundleItf.getText());
			//#CM689809
			// Bundle ITF: Position
			param[0].setPosition_BundleItf(txt_BundleItfPst.getText());

			//#CM689810
			// Case ITF: Enabled
			param[0].setValid_Itf(chk_CaseItf.getChecked());
			//#CM689811
			// Case ITF: Length
			param[0].setFigure_Itf(txt_CaseItfLen.getText());
			//#CM689812
			// Case ITF: Max Length
			param[0].setMaxFigure_Itf(lbl_MaxLenCaseItf.getText());
			//#CM689813
			// Case ITF: Position
			param[0].setPosition_Itf(txt_CaseItfPst.getText());

			//#CM689814
			// Packed qty per bundle: Enabled
			param[0].setValid_BundleEnteringQty(chk_BundleEtr.getChecked());
			//#CM689815
			// Packed qty per bundle: Length
			param[0].setFigure_BundleEnteringQty(txt_BundleEtrLen.getText());
			//#CM689816
			// Packed qty per bundle: Max Length
			param[0].setMaxFigure_BundleEnteringQty(lbl_MaxLenBundleEtr.getText());
			//#CM689817
			// Packed qty per bundle: Position
			param[0].setPosition_BundleEnteringQty(txt_BundleEtrPst.getText());

			//#CM689818
			// Packed Qty per Case: Enabled
			param[0].setValid_EnteringQty(chk_CaseEtr.getChecked());
			//#CM689819
			// Packed Qty per Case: Length
			param[0].setFigure_EnteringQty(txt_CaseEtrLen.getText());
			//#CM689820
			// Packed Qty per Case: Max Length
			param[0].setMaxFigure_EnteringQty(lbl_MaxLenCaseEtr.getText());
			//#CM689821
			// Packed Qty per Case: Position
			param[0].setPosition_EnteringQty(txt_CaseEtrPst.getText());

			//#CM689822
			// Item Name: Enabled
			param[0].setValid_ItemName(chk_ItemNm.getChecked());
			//#CM689823
			// Item Name: Length
			param[0].setFigure_ItemName(txt_ItemNmLen.getText());
			//#CM689824
			// Item Name: Max Length
			param[0].setMaxFigure_ItemName(lbl_MaxLenItemNm.getText());
			//#CM689825
			// Item Name: Position
			param[0].setPosition_ItemName(txt_ItemNmPst.getText());

			//#CM689826
			// Piece Picking Location: Enabled
			param[0].setValid_PieceLocation(chk_PieceRtrivlLct.getChecked());
			//#CM689827
			// Piece Picking Location: Length
			param[0].setFigure_PieceLocation(txt_PieceRtrivlLctLen.getText());
			//#CM689828
			// Piece Picking Location: Max Length
			param[0].setMaxFigure_PieceLocation(lbl_MaxLenPieceRtrivlLct.getText());
			//#CM689829
			// Piece Picking Location: Position
			param[0].setPosition_PieceLocation(txt_PieceRtrivlLctPst.getText());

			//#CM689830
			// Case Picking Location: Enabled
			param[0].setValid_CaseLocation(chk_CaseRtrivlLct.getChecked());
			//#CM689831
			// Case Picking Location: Length
			param[0].setFigure_CaseLocation(txt_CaseRtrivlLctLen.getText());
			//#CM689832
			// Case Picking Location: Max Length
			param[0].setMaxFigure_CaseLocation(lbl_MaxLenCaseRtrivlLct.getText());
			//#CM689833
			// Case Picking Location: Position
			param[0].setPosition_CaseLocation(txt_CaseRtrivlLctPst.getText());

			//#CM689834
			// Result Piece Qty: Enabled
			param[0].setValid_PieceResultQty(chk_RsltPieceQty.getChecked());
			//#CM689835
			// Result Piece Qty: Length
			param[0].setFigure_PieceResultQty(txt_RsltPieceQtyLen.getText());
			//#CM689836
			// Result Piece Qty: Max Length
			param[0].setMaxFigure_PieceResultQty(lbl_MaxLenRsltPieceQty.getText());
			//#CM689837
			// Result Piece Qty: Position
			param[0].setPosition_PieceResultQty(txt_RsltPieceQtyPst.getText());

			//#CM689838
			// Result Case Qty: Enabled
			param[0].setValid_CaseResultQty(chk_RsltCaseQty.getChecked());
			//#CM689839
			// Result Case Qty: Length
			param[0].setFigure_CaseResultQty(txt_RsltCaseQtyLen.getText());
			//#CM689840
			// Result Case Qty: Max Length
			param[0].setMaxFigure_CaseResultQty(lbl_MaxLenRsltCaseQty.getText());
			//#CM689841
			// Result Case Qty: Position
			param[0].setPosition_CaseResultQty(txt_RsltCaseQtyPst.getText());

			//#CM689842
			// Picking Result Date: Enabled
			param[0].setValid_WorkDate(chk_RtrivlRsltDate.getChecked());
			//#CM689843
			// Picking Result Date: Length
			param[0].setFigure_WorkDate(txt_RtrivlRsltDateLen.getText());
			//#CM689844
			// Picking Result Date: Max Length
			param[0].setMaxFigure_WorkDate(lbl_MaxLenRtrivlRsltDate.getText());
			//#CM689845
			// Picking Result Date: Position
			param[0].setPosition_WorkDate(txt_RtrivlRsltDatePst.getText());

			//#CM689846
			// Expiry Date: Enabled
			param[0].setValid_UseByDate(chk_UseByDate.getChecked());
			//#CM689847
			// Expiry Date: Length
			param[0].setFigure_UseByDate(txt_UseByDateLen.getText());
			//#CM689848
			// Expiry Date: Max Length
			param[0].setMaxFigure_UseByDate(lbl_MaxLenUseByDate.getText());
			//#CM689849
			// Expiry Date: Position
			param[0].setPosition_UseByDate(txt_UseByDatePst.getText());

			//#CM689850
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
				//#CM689851
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
				//#CM689852
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

	//#CM689853
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689854
	/** 
	 * Clicking on Clear button for field items set for reporting data (setting of Unplanned Picking Result field) invokes this.<BR>
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

	//#CM689855
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_RptClmSetNoPlanRtrivl_Click(ActionEvent e) throws Exception
	{
	}
}
//#CM689856
//end of class
