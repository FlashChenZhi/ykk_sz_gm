// $Id: DefineReportDataStorageBusiness.java,v 1.2 2006/11/13 08:18:39 suresh Exp $

//#CM691943
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

//#CM691944
/**
 * Designer :  S.Ishigane <BR>
 * Maker :     T.Uehata <BR>
 * <BR>
 * Allow this class to set a field for reporting data (to set the Storage Result field).<BR>
 * Set the searched value via the schedule for each field item.
 * Set the contents entered via screen for the parameter. Allow the schedule to set the Report Data field (set the Storage Result field) based on the parameter.<BR>
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
 * 	 Planned Storage Date+<BR>
 * 	 Consignor Code +<BR>
 * 	 Consignor Name+<BR>
 * 	 Item Code +<BR>
 * 	 Bundle ITF+<BR>
 * 	 Case ITF+<BR>
 * 	 Packed qty per bundle+<BR>
 * 	 Packed Qty per Case+<BR>
 * 	 Item Name+<BR>
 * 	 Storage Qty (Total Bulk Qty) +<BR>
 * 	 Piece Storage Location+<BR>
 * 	 Case Storage Location+<BR>
 * 	 Result Piece Qty+<BR>
 * 	 Result Case Qty+<BR>
 * 	 Storage Result Date+<BR>
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
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:39 $
 * @author  $Author: suresh $
 */
public class DefineReportDataStorageBusiness extends DefineReportDataStorage implements WMSConstants
{
	private static final String DO_DEFINEREPORT = "/system/definereportdata/DefineReportData.do";
	//#CM691945
	// Class fields --------------------------------------------------

	//#CM691946
	// Class variables -----------------------------------------------

	//#CM691947
	// Class method --------------------------------------------------

	//#CM691948
	// Constructors --------------------------------------------------

	//#CM691949
	// Public methods ------------------------------------------------
	//#CM691950
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
			//#CM691951
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM691952
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM691953
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM691954
			//Set the path to the help file.
			//#CM691955
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM691956
		// MSG-0009= Do you add it?
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}
	
	//#CM691957
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
		//#CM691958
		// Title
		lbl_SettingName.setResourceKey(this.getViewState().getString("TITLE"));

		//#CM691959
		// Check that the length is read-only. Length.
		txt_StrgPlanDateLen.setReadOnly(true);
		txt_CnsgnrCdLen.setReadOnly(true);
		txt_CnsgnrNmLen.setReadOnly(true);
		txt_ItemCdLen.setReadOnly(true);
		txt_BundleItfLen.setReadOnly(true);
		txt_CaseItfLen.setReadOnly(true);
		txt_BundleEtrLen.setReadOnly(true);
		txt_CaseEtrLen.setReadOnly(true);
		txt_ItemNmLen.setReadOnly(true);
		txt_StrgQtyPtlLen.setReadOnly(true);
		txt_PieceStrgLctLen.setReadOnly(true);
		txt_CaseStrgLctLen.setReadOnly(true);
		txt_RsltPieceQtyLen.setReadOnly(true);
		txt_RsltCaseQtyLen.setReadOnly(true);
		txt_StrgRsltDateLen.setReadOnly(true);
		txt_RsltFlgLen.setReadOnly(true);
		txt_UseByDateLen.setReadOnly(true);

		setInistView();
	}

	//#CM691960
	// Package methods -----------------------------------------------

	//#CM691961
	// Protected methods ---------------------------------------------

	//#CM691962
	// Private methods -----------------------------------------------

	//#CM691963
	/** 
	 * Allow this method to set the initial values for screen.<BR>
	 * <BR>
	 * Summary: Sets up the settings of each field item in the screen from property file.<BR>
	 * 
	 * @throws Exception Report all exceptions.
	 */
	private void setInistView() throws Exception
	{
		//#CM691964
		// Set the initial value of the focus to Planned Storage Date Enabled.
		setFocus(chk_StrgPlanDate);

		Connection conn = null;
		try
		{
			//#CM691965
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter initParam = new DefineDataParameter();
			initParam.setSelectDefineReportData(
				DefineDataParameter.SELECTDEFINEREPORTDATA_STORAGE);
			WmsScheduler schedule = new DefineReportDataSCH();
			DefineDataParameter[] param = (DefineDataParameter[]) schedule.query(conn, initParam);

			if (param != null)
			{
				//#CM691966
				// Planned Storage Date: Enabled
				chk_StrgPlanDate.setChecked(param[0].getValid_PlanDate());
				//#CM691967
				// Planned Storage Date: Length
				txt_StrgPlanDateLen.setText(param[0].getFigure_PlanDate());
				//#CM691968
				// Planned Storage Date: Max Length
				lbl_MaxLenStrgPlanDate.setText(param[0].getMaxFigure_PlanDate());
				//#CM691969
				// Planned Storage Date: Position
				txt_StrgPlanDatePst.setText(param[0].getPosition_PlanDate());
				
				//#CM691970
				// Consignor Code: Enabled
				chk_CnsgnrCd.setChecked(param[0].getValid_ConsignorCode());
				//#CM691971
				// Consignor Code: Length
				txt_CnsgnrCdLen.setText(param[0].getFigure_ConsignorCode());
				//#CM691972
				// Consignor Code: Max Length
				lbl_MaxLenCnsgnrCd.setText(param[0].getMaxFigure_ConsignorCode());
				//#CM691973
				// Consignor Code: Position
				txt_CnsgnrCdPst.setText(param[0].getPosition_ConsignorCode());
				
				//#CM691974
				// Consignor Name: Enabled
				chk_CnsgnrNm.setChecked(param[0].getValid_ConsignorName());
				//#CM691975
				// Consignor Name: Length
				txt_CnsgnrNmLen.setText(param[0].getFigure_ConsignorName());
				//#CM691976
				// Consignor Name: Max Length
				lbl_MaxLenCnsgnrNm.setText(param[0].getMaxFigure_ConsignorName());
				//#CM691977
				// Consignor Name: Position
				txt_CnsgnrNmPst.setText(param[0].getPosition_ConsignorName());
				
				//#CM691978
				// Item Code: Enabled
				chk_ItemCd.setChecked(param[0].getValid_ItemCode());
				//#CM691979
				// Item Code: Length
				txt_ItemCdLen.setText(param[0].getFigure_ItemCode());
				//#CM691980
				// Item Code: Max Length
				lbl_MaxLenItemCd.setText(param[0].getMaxFigure_ItemCode());
				//#CM691981
				// Item Code: Position
				txt_ItemCdPst.setText(param[0].getPosition_ItemCode());
				
				//#CM691982
				// Bundle ITF: Enabled
				chk_BundleItf.setChecked(param[0].getValid_BundleItf());
				//#CM691983
				// Bundle ITF: Length
				txt_BundleItfLen.setText(param[0].getFigure_BundleItf());
				//#CM691984
				// Bundle ITF: Max Length
				lbl_MaxLenBundleItf.setText(param[0].getMaxFigure_BundleItf());
				//#CM691985
				// Bundle ITF: Position
				txt_BundleItfPst.setText(param[0].getPosition_BundleItf());
				
				//#CM691986
				// Case ITF: Enabled
				chk_CaseItf.setChecked(param[0].getValid_Itf());
				//#CM691987
				// Case ITF: Length
				txt_CaseItfLen.setText(param[0].getFigure_Itf());
				//#CM691988
				// Case ITF: Max Length
				lbl_MaxLenCaseItf.setText(param[0].getMaxFigure_Itf());
				//#CM691989
				// Case ITF: Position
				txt_CaseItfPst.setText(param[0].getPosition_Itf());
				
				//#CM691990
				// Packed qty per bundle: Enabled
				chk_BundleEtr.setChecked(param[0].getValid_BundleEnteringQty());
				//#CM691991
				// Packed qty per bundle: Length
				txt_BundleEtrLen.setText(param[0].getFigure_BundleEnteringQty());
				//#CM691992
				// Packed qty per bundle: Max Length
				lbl_MaxLenBundleEtr.setText(param[0].getMaxFigure_BundleEnteringQty());
				//#CM691993
				// Packed qty per bundle: Position
				txt_BundleEtrPst.setText(param[0].getPosition_BundleEnteringQty());

				//#CM691994
				// Packed Qty per Case: Enabled
				chk_CaseEtr.setChecked(param[0].getValid_EnteringQty());
				//#CM691995
				// Packed Qty per Case: Length
				txt_CaseEtrLen.setText(param[0].getFigure_EnteringQty());
				//#CM691996
				// Packed Qty per Case: Max Length
				lbl_MaxLenCaseEtr.setText(param[0].getMaxFigure_EnteringQty());
				//#CM691997
				// Packed Qty per Case: Position
				txt_CaseEtrPst.setText(param[0].getPosition_EnteringQty());

				//#CM691998
				// Item Name: Enabled
				chk_ItemNm.setChecked(param[0].getValid_ItemName());
				//#CM691999
				// Item Name: Length
				txt_ItemNmLen.setText(param[0].getFigure_ItemName());
				//#CM692000
				// Item Name: Max Length
				lbl_MaxLenItemNm.setText(param[0].getMaxFigure_ItemName());
				//#CM692001
				// Item Name: Position
				txt_ItemNmPst.setText(param[0].getPosition_ItemName());

				//#CM692002
				// Storage Qty (Total Bulk Qty): Enabled
				chk_StrgQtyPtl.setChecked(param[0].getValid_PlanQty());
				//#CM692003
				// Storage Qty (Total Bulk Qty): Length
				txt_StrgQtyPtlLen.setText(param[0].getFigure_PlanQty());
				//#CM692004
				// Storage Qty (Total Bulk Qty): Max Length
				lbl_MaxLenStrgQtyPtl.setText(param[0].getMaxFigure_PlanQty());
				//#CM692005
				// Storage Qty (Total Bulk Qty): Position
				txt_StrgQtyPtlPst.setText(param[0].getPosition_PlanQty());

				//#CM692006
				// Piece Storage Location: Enabled
				chk_PieceStrgLct.setChecked(param[0].getValid_PieceLocation());
				//#CM692007
				// Piece Storage Location: Length
				txt_PieceStrgLctLen.setText(param[0].getFigure_PieceLocation());
				//#CM692008
				// Piece Storage Location: Max Length
				lbl_MaxLenPieceStrgLct.setText(param[0].getMaxFigure_PieceLocation());
				//#CM692009
				// Piece Storage Location: Position
				txt_PieceStrgLctPst.setText(param[0].getPosition_PieceLocation());

				//#CM692010
				// Case Storage Location: Enabled
				chk_CaseStrgLct.setChecked(param[0].getValid_CaseLocation());
				//#CM692011
				// Case Storage Location: Length
				txt_CaseStrgLctLen.setText(param[0].getFigure_CaseLocation());
				//#CM692012
				// Case Storage Location: Max Length
				lbl_MaxLenCaseStrgLct.setText(param[0].getMaxFigure_CaseLocation());
				//#CM692013
				// Case Storage Location: Position
				txt_CaseStrgLctPst.setText(param[0].getPosition_CaseLocation());

				//#CM692014
				// Result Piece Qty: Enabled
				chk_RsltPieceQty.setChecked(param[0].getValid_PieceResultQty());
				//#CM692015
				// Result Piece Qty: Length
				txt_RsltPieceQtyLen.setText(param[0].getFigure_PieceResultQty());
				//#CM692016
				// Result Piece Qty: Max Length
				lbl_MaxLenRsltPieceQty.setText(param[0].getMaxFigure_PieceResultQty());
				//#CM692017
				// Result Piece Qty: Position
				txt_RsltPieceQtyPst.setText(param[0].getPosition_PieceResultQty());

				//#CM692018
				// Result Case Qty: Enabled
				chk_RsltCaseQty.setChecked(param[0].getValid_CaseResultQty());
				//#CM692019
				// Result Case Qty: Length
				txt_RsltCaseQtyLen.setText(param[0].getFigure_CaseResultQty());
				//#CM692020
				// Result Case Qty: Max Length
				lbl_MaxLenRsltCaseQty.setText(param[0].getMaxFigure_CaseResultQty());
				//#CM692021
				// Result Case Qty: Position
				txt_RsltCaseQtyPst.setText(param[0].getPosition_CaseResultQty());

				//#CM692022
				// Storage Result Date: Enabled
				chk_StrgRsltDate.setChecked(param[0].getValid_WorkDate());
				//#CM692023
				// Storage Result Date: Length
				txt_StrgRsltDateLen.setText(param[0].getFigure_WorkDate());
				//#CM692024
				// Storage Result Date: Max Length
				lbl_MaxLenStrgRsltDate.setText(param[0].getMaxFigure_WorkDate());
				//#CM692025
				// Storage Result Date: Position
				txt_StrgRsltDatePst.setText(param[0].getPosition_WorkDate());

				//#CM692026
				// Result division: Enabled
				chk_RsltFlg.setChecked(param[0].getValid_ResultFlag());
				//#CM692027
				// Result division: Length
				txt_RsltFlgLen.setText(param[0].getFigure_ResultFlag());
				//#CM692028
				// Result division: Max Length
				lbl_MaxLenRsltFlg.setText(param[0].getMaxFigure_ResultFlag());
				//#CM692029
				// Result division: Position
				txt_RsltFlgPst.setText(param[0].getPosition_ResultFlag());

				//#CM692030
				// Expiry Date: Enabled
				chk_UseByDate.setChecked(param[0].getValid_UseByDate());
				//#CM692031
				// Expiry Date: Length
				txt_UseByDateLen.setText(param[0].getFigure_UseByDate());
				//#CM692032
				// Expiry Date: Max Length
				lbl_MaxLenUseByDate.setText(param[0].getMaxFigure_UseByDate());
				//#CM692033
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
				//#CM692034
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
	
	//#CM692035
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

		//#CM692036
		// Planned Storage Date: Position
		if (position.getText().equals(txt_StrgPlanDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM692037
		// Consignor Code: Position
		if (position.getText().equals(txt_CnsgnrCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM692038
		// Consignor Name: Position
		if (position.getText().equals(txt_CnsgnrNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM692039
		// Item Code: Position
		if (position.getText().equals(txt_ItemCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM692040
		// Bundle ITF: Position
		if (position.getText().equals(txt_BundleItfPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM692041
		// Case ITF: Position
		if (position.getText().equals(txt_CaseItfPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM692042
		// Packed qty per bundle: Position
		if (position.getText().equals(txt_BundleEtrPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM692043
		// Packed Qty per Case: Position
		if (position.getText().equals(txt_CaseEtrPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM692044
		// Item Name: Position
		if (position.getText().equals(txt_ItemNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM692045
		// Storage Qty (Total Bulk Qty): Position
		if (position.getText().equals(txt_StrgQtyPtlPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM692046
		// Piece Storage Location: Position
		if (position.getText().equals(txt_PieceStrgLctPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM692047
		// Case Storage Location: Position
		if (position.getText().equals(txt_CaseStrgLctPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM692048
		// Result Piece Qty: Position
		if (position.getText().equals(txt_RsltPieceQtyPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM692049
		// Result Case Qty: Position
		if (position.getText().equals(txt_RsltCaseQtyPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM692050
		// Storage Result Date: Position
		if (position.getText().equals(txt_StrgRsltDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM692051
		// Result division: Position
		if (position.getText().equals(txt_RsltFlgPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM692052
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

	//#CM692053
	/** 
	 * Allow this method to check wheter the position is duplicated or not in a sequential order.<BR>
	 * <BR>
	 * @return Return false if two or more data occupies a single position.
	 * @throws Exception Report all exceptions.
	 */
	private boolean checkRepeat() throws Exception
	{

		boolean repeatChkFlg = true;
		
		if (repeatChkFlg && chk_StrgPlanDate.getChecked())
		{
			repeatChkFlg = positionCheck(txt_StrgPlanDatePst);
		}

		if (repeatChkFlg && chk_CnsgnrCd.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CnsgnrCdPst);
		}

		if (repeatChkFlg && chk_CnsgnrNm.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CnsgnrNmPst);
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

		if (repeatChkFlg && chk_StrgQtyPtl.getChecked())
		{
			repeatChkFlg = positionCheck(txt_StrgQtyPtlPst);
		}

		if (repeatChkFlg && chk_PieceStrgLct.getChecked())
		{
			repeatChkFlg = positionCheck(txt_PieceStrgLctPst);
		}

		if (repeatChkFlg && chk_CaseStrgLct.getChecked())
		{
			repeatChkFlg = positionCheck(txt_CaseStrgLctPst);
		}

		if (repeatChkFlg && chk_RsltPieceQty.getChecked())
		{
			repeatChkFlg = positionCheck(txt_RsltPieceQtyPst);
		}

		if (repeatChkFlg && chk_RsltCaseQty.getChecked())
		{
			repeatChkFlg = positionCheck(txt_RsltCaseQtyPst);
		}
		
		if (repeatChkFlg && chk_StrgRsltDate.getChecked())
		{
			repeatChkFlg = positionCheck(txt_StrgRsltDatePst);
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
	
	//#CM692054
	/** 
	 * Allow this method to check whether a checkbox is ticked off or not.<BR>
	 * <BR>
	 * @return Return false if no item ticked off.
	 * @throws Exception Report all exceptions.
	 */
	private boolean checkCheckBox() throws Exception
	{
		if (!chk_StrgPlanDate.getChecked()
				&& !chk_CnsgnrCd.getChecked()
				&& !chk_CnsgnrNm.getChecked()
				&& !chk_ItemCd.getChecked()
				&& !chk_BundleItf.getChecked()
				&& !chk_CaseItf.getChecked()
				&& !chk_BundleEtr.getChecked()
				&& !chk_CaseEtr.getChecked()
				&& !chk_ItemNm.getChecked()
				&& !chk_StrgQtyPtl.getChecked()
				&& !chk_PieceStrgLct.getChecked()
				&& !chk_CaseStrgLct.getChecked()
				&& !chk_RsltPieceQty.getChecked()
				&& !chk_RsltCaseQty.getChecked()
				&& !chk_StrgRsltDate.getChecked()
				&& !chk_RsltFlg.getChecked()
				&& !chk_UseByDate.getChecked())
		{
			return false;
		}
		return true;
	}
	//#CM692055
	// Event handler methods -----------------------------------------
	//#CM692056
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692057
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692058
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Back_Server(ActionEvent e) throws Exception
	{
	}


	//#CM692059
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
		//#CM692060
		// Shift to the original screen.
		forward(DO_DEFINEREPORT);
	}

	//#CM692061
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692062
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

	//#CM692063
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Valid1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692064
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DigitsUseLength1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692065
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLength1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692066
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Position1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692067
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Valid2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692068
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DigitsUseLength2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692069
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLength2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692070
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Position2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692071
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692072
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_StrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692073
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_StrgPlanDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692074
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPlanDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692075
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPlanDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692076
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPlanDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692077
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692078
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPlanDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692079
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPlanDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692080
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPlanDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692081
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ResultPieceQtyTwoByte_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692082
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692083
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltPieceQty_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692084
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692085
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692086
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692087
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRsltPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692088
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692089
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692090
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692091
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692092
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692093
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692094
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692095
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692096
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692097
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692098
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692099
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692100
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692101
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ResultCaseQtyTwoByte_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692102
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692103
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltCaseQty_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692104
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692105
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692106
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692107
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRsltCaseQty_Server(ActionEvent e) throws Exception
	{
	}


	//#CM692108
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692109
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692110
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyPst_TabKey(ActionEvent e) throws Exception
	{
	}
	
	//#CM692111
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692112
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692113
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692114
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692115
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692116
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692117
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692118
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692119
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692120
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692121
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageResultDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692122
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_StrgRsltDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692123
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_StrgRsltDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692124
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692125
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692126
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692127
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenStrgRsltDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692128
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692129
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692130
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692131
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692132
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692133
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692134
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692135
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692136
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692137
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692138
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692139
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692140
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692141
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ResultFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692142
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltFlg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692143
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltFlg_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692144
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692145
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692146
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692147
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRsltFlg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692148
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692149
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692150
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltFlgPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692151
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692152
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692153
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleItf_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692154
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692155
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692156
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692157
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenBundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692158
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692159
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692160
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692161
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692162
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692163
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_UseByDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692164
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692165
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692166
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692167
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenUseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692168
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692169
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692170
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692171
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692172
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692173
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseItf_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692174
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692175
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692176
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692177
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692178
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692179
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692180
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692181
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692182
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692183
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleEtr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692184
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692185
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692186
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692187
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenBundleEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692188
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692189
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692190
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692191
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692192
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692193
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseEtr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692194
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692195
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692196
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692197
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692198
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692199
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692200
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692201
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692202
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692203
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692204
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692205
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692206
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692207
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692208
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692209
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692210
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692211
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrgQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692212
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_StrgQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692213
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_StrgQtyPtl_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692214
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgQtyPtlLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692215
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgQtyPtlLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692216
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgQtyPtlLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692217
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenStrgQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692218
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgQtyPtlPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692219
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgQtyPtlPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692220
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgQtyPtlPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692221
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PiceStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692222
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PieceStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692223
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PieceStrgLct_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692224
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceStrgLctLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692225
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceStrgLctLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692226
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceStrgLctLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692227
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenPieceStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692228
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceStrgLctPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692229
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceStrgLctPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692230
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceStrgLctPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692231
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692232
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692233
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseStrgLct_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692234
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStrgLctLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692235
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStrgLctLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692236
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStrgLctLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692237
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692238
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStrgLctPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692239
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStrgLctPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692240
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStrgLctPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692241
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}


	//#CM692242
	/** 
	 * Clicking on Add button for field items set for reporting data (setting of Storage Result field) invokes this.<BR>
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
	    //#CM692243
	    // Set the initial value of the focus to Planned Storage Date Enabled.
		setFocus(chk_StrgPlanDate);

		//#CM692244
		// Check the checkbox.
		if (!checkCheckBox())
		{
			//#CM692245
			// 6023174=Please select 1 or more items.
			message.setMsgResourceKey("6023174");
			return;
		}
		
		//#CM692246
		// Check for input.
		WmsCheckker.checkDefine(lbl_StoragePlanDate, chk_StrgPlanDate, txt_StrgPlanDateLen, lbl_MaxLenStrgPlanDate, txt_StrgPlanDatePst);
		WmsCheckker.checkDefine(lbl_ConsignorCode, chk_CnsgnrCd, txt_CnsgnrCdLen, lbl_MaxLenCnsgnrCd, txt_CnsgnrCdPst);
		WmsCheckker.checkDefine(lbl_ConsignorName, chk_CnsgnrNm, txt_CnsgnrNmLen, lbl_MaxLenCnsgnrNm, txt_CnsgnrNmPst);
		WmsCheckker.checkDefine(lbl_ItemCode, chk_ItemCd, txt_ItemCdLen, lbl_MaxLenItemCd, txt_ItemCdPst);
		WmsCheckker.checkDefine(lbl_BundleItf, chk_BundleItf, txt_BundleItfLen, lbl_MaxLenBundleItf, txt_BundleItfPst);
		WmsCheckker.checkDefine(lbl_CaseItf, chk_CaseItf, txt_CaseItfLen, lbl_MaxLenCaseItf, txt_CaseItfPst);
		WmsCheckker.checkDefine(lbl_BundleEntering, chk_BundleEtr, txt_BundleEtrLen, lbl_MaxLenBundleEtr, txt_BundleEtrPst);
		WmsCheckker.checkDefine(lbl_CaseEntering, chk_CaseEtr, txt_CaseEtrLen, lbl_MaxLenCaseEtr, txt_CaseEtrPst);
		WmsCheckker.checkDefine(lbl_ItemName, chk_ItemNm, txt_ItemNmLen, lbl_MaxLenItemNm, txt_ItemNmPst);
		WmsCheckker.checkDefine(lbl_StrgQtyPtl, chk_StrgQtyPtl, txt_StrgQtyPtlLen, lbl_MaxLenStrgQtyPtl, txt_StrgQtyPtlPst);
		WmsCheckker.checkDefine(lbl_PiceStrgLct, chk_PieceStrgLct, txt_PieceStrgLctLen, lbl_MaxLenPieceStrgLct, txt_PieceStrgLctPst);
		WmsCheckker.checkDefine(lbl_CaseStrgLct, chk_CaseStrgLct, txt_CaseStrgLctLen, lbl_MaxLenCaseStrgLct, txt_CaseStrgLctPst);
		WmsCheckker.checkDefine(lbl_ResultPieceQtyTwoByte, chk_RsltPieceQty, txt_RsltPieceQtyLen, lbl_MaxLenRsltPieceQty, txt_RsltPieceQtyPst);
		WmsCheckker.checkDefine(lbl_ResultCaseQtyTwoByte, chk_RsltCaseQty, txt_RsltCaseQtyLen, lbl_MaxLenRsltCaseQty, txt_RsltCaseQtyPst);
		WmsCheckker.checkDefine(lbl_StorageResultDate, chk_StrgRsltDate, txt_StrgRsltDateLen, lbl_MaxLenStrgRsltDate, txt_StrgRsltDatePst);
		WmsCheckker.checkDefine(lbl_ResultFlag, chk_RsltFlg, txt_RsltFlgLen, lbl_MaxLenRsltFlg, txt_RsltFlgPst);
		WmsCheckker.checkDefine(lbl_UseByDate, chk_UseByDate, txt_UseByDateLen, lbl_MaxLenUseByDate, txt_UseByDatePst);
	
		Connection conn = null;
		
		try
		{
			//#CM692247
			// Check for duplicate Position.
			if (!checkRepeat())
			{
				//#CM692248
				// Display the error message and close it.
				//#CM692249
				// 6023098=No duplicate of sequential order is allowed.
				message.setMsgResourceKey("6023098");
				return;
			}
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	
			DefineDataParameter[] param = new DefineDataParameter[1];
			param[0] = new DefineDataParameter();
	
			WmsScheduler schedule = new DefineReportDataSCH();
	
			//#CM692250
			// Set the value for the parameter.
			param[0].setSelectDefineReportData(DefineDataParameter.SELECTDEFINEREPORTDATA_STORAGE);
			//#CM692251
			// Planned Storage Date: Enabled
			param[0].setValid_PlanDate(chk_StrgPlanDate.getChecked());
			//#CM692252
			// Planned Storage Date: Length
			param[0].setFigure_PlanDate(txt_StrgPlanDateLen.getText());
			//#CM692253
			// Planned Storage Date: Max Length
			param[0].setMaxFigure_PlanDate(lbl_MaxLenStrgPlanDate.getText());
			//#CM692254
			// Planned Storage Date: Position
			param[0].setPosition_PlanDate(txt_StrgPlanDatePst.getText());
			
			//#CM692255
			// Consignor Code: Enabled
			param[0].setValid_ConsignorCode(chk_CnsgnrCd.getChecked());
			//#CM692256
			// Consignor Code: Length
			param[0].setFigure_ConsignorCode(txt_CnsgnrCdLen.getText());
			//#CM692257
			// Consignor Code: Max Length
			param[0].setMaxFigure_ConsignorCode(lbl_MaxLenCnsgnrCd.getText());
			//#CM692258
			// Consignor Code: Position
			param[0].setPosition_ConsignorCode(txt_CnsgnrCdPst.getText());
			
			//#CM692259
			// Consignor Name: Enabled
			param[0].setValid_ConsignorName(chk_CnsgnrNm.getChecked());
			//#CM692260
			// Consignor Name: Length
			param[0].setFigure_ConsignorName(txt_CnsgnrNmLen.getText());
			//#CM692261
			// Consignor Name: Max Length
			param[0].setMaxFigure_ConsignorName(lbl_MaxLenCnsgnrNm.getText());
			//#CM692262
			// Consignor Name: Position
			param[0].setPosition_ConsignorName(txt_CnsgnrNmPst.getText());
			
			//#CM692263
			// Item Code: Enabled
			param[0].setValid_ItemCode(chk_ItemCd.getChecked());
			//#CM692264
			// Item Code: Length
			param[0].setFigure_ItemCode(txt_ItemCdLen.getText());
			//#CM692265
			// Item Code: Max Length
			param[0].setMaxFigure_ItemCode(lbl_MaxLenItemCd.getText());
			//#CM692266
			// Item Code: Position
			param[0].setPosition_ItemCode(txt_ItemCdPst.getText());
			
			//#CM692267
			// Bundle ITF: Enabled
			param[0].setValid_BundleItf(chk_BundleItf.getChecked());
			//#CM692268
			// Bundle ITF: Length
			param[0].setFigure_BundleItf(txt_BundleItfLen.getText());
			//#CM692269
			// Bundle ITF: Max Length
			param[0].setMaxFigure_BundleItf(lbl_MaxLenBundleItf.getText());
			//#CM692270
			// Bundle ITF: Position
			param[0].setPosition_BundleItf(txt_BundleItfPst.getText());
			
			//#CM692271
			// Case ITF: Enabled
			param[0].setValid_Itf(chk_CaseItf.getChecked());
			//#CM692272
			// Case ITF: Length
			param[0].setFigure_Itf(txt_CaseItfLen.getText());
			//#CM692273
			// Case ITF: Max Length
			param[0].setMaxFigure_Itf(lbl_MaxLenCaseItf.getText());
			//#CM692274
			// Case ITF: Position
			param[0].setPosition_Itf(txt_CaseItfPst.getText());
			
			//#CM692275
			// Packed qty per bundle: Enabled
			param[0].setValid_BundleEnteringQty(chk_BundleEtr.getChecked());
			//#CM692276
			// Packed qty per bundle: Length
			param[0].setFigure_BundleEnteringQty(txt_BundleEtrLen.getText());
			//#CM692277
			// Packed qty per bundle: Max Length
			param[0].setMaxFigure_BundleEnteringQty(lbl_MaxLenBundleEtr.getText());
			//#CM692278
			// Packed qty per bundle: Position
			param[0].setPosition_BundleEnteringQty(txt_BundleEtrPst.getText());
			
			//#CM692279
			// Packed Qty per Case: Enabled
			param[0].setValid_EnteringQty(chk_CaseEtr.getChecked());
			//#CM692280
			// Packed Qty per Case: Length
			param[0].setFigure_EnteringQty(txt_CaseEtrLen.getText());
			//#CM692281
			// Packed Qty per Case: Max Length
			param[0].setMaxFigure_EnteringQty(lbl_MaxLenCaseEtr.getText());
			//#CM692282
			// Packed Qty per Case: Position
			param[0].setPosition_EnteringQty(txt_CaseEtrPst.getText());
			
			//#CM692283
			// Item Name: Enabled
			param[0].setValid_ItemName(chk_ItemNm.getChecked());
			//#CM692284
			// Item Name: Length
			param[0].setFigure_ItemName(txt_ItemNmLen.getText());
			//#CM692285
			// Item Name: Max Length
			param[0].setMaxFigure_ItemName(lbl_MaxLenItemNm.getText());
			//#CM692286
			// Item Name: Position
			param[0].setPosition_ItemName(txt_ItemNmPst.getText());
			
			//#CM692287
			// Storage Qty (Total Bulk Qty): Enabled
			param[0].setValid_PlanQty(chk_StrgQtyPtl.getChecked());
			//#CM692288
			// Storage Qty (Total Bulk Qty): Length
			param[0].setFigure_PlanQty(txt_StrgQtyPtlLen.getText());
			//#CM692289
			// Storage Qty (Total Bulk Qty): Max Length
			param[0].setMaxFigure_PlanQty(lbl_MaxLenStrgQtyPtl.getText());
			//#CM692290
			// Storage Qty (Total Bulk Qty): Position
			param[0].setPosition_PlanQty(txt_StrgQtyPtlPst.getText());
			
			//#CM692291
			// Piece Storage Location: Enabled
			param[0].setValid_PieceLocation(chk_PieceStrgLct.getChecked());
			//#CM692292
			// Piece Storage Location: Length
			param[0].setFigure_PieceLocation(txt_PieceStrgLctLen.getText());
			//#CM692293
			// Piece Storage Location: Max Length
			param[0].setMaxFigure_PieceLocation(lbl_MaxLenPieceStrgLct.getText());
			//#CM692294
			// Piece Storage Location: Position
			param[0].setPosition_PieceLocation(txt_PieceStrgLctPst.getText());
			
			//#CM692295
			// Case Storage Location: Enabled
			param[0].setValid_CaseLocation(chk_CaseStrgLct.getChecked());
			//#CM692296
			// Case Storage Location: Length
			param[0].setFigure_CaseLocation(txt_CaseStrgLctLen.getText());
			//#CM692297
			// Case Storage Location: Max Length
			param[0].setMaxFigure_CaseLocation(lbl_MaxLenCaseStrgLct.getText());
			//#CM692298
			// Case Storage Location: Position
			param[0].setPosition_CaseLocation(txt_CaseStrgLctPst.getText());
			
			//#CM692299
			// Result Piece Qty: Enabled
			param[0].setValid_PieceResultQty(chk_RsltPieceQty.getChecked());
			//#CM692300
			// Result Piece Qty: Length
			param[0].setFigure_PieceResultQty(txt_RsltPieceQtyLen.getText());
			//#CM692301
			// Result Piece Qty: Max Length
			param[0].setMaxFigure_PieceResultQty(lbl_MaxLenRsltPieceQty.getText());
			//#CM692302
			// Result Piece Qty: Position
			param[0].setPosition_PieceResultQty(txt_RsltPieceQtyPst.getText());
			
			//#CM692303
			// Result Case Qty: Enabled
			param[0].setValid_CaseResultQty(chk_RsltCaseQty.getChecked());
			//#CM692304
			// Result Case Qty: Length
			param[0].setFigure_CaseResultQty(txt_RsltCaseQtyLen.getText());
			//#CM692305
			// Result Case Qty: Max Length
			param[0].setMaxFigure_CaseResultQty(lbl_MaxLenRsltCaseQty.getText());
			//#CM692306
			// Result Case Qty: Position
			param[0].setPosition_CaseResultQty(txt_RsltCaseQtyPst.getText());
			
			//#CM692307
			// Storage Result Date: Enabled
			param[0].setValid_WorkDate(chk_StrgRsltDate.getChecked());
			//#CM692308
			// Storage Result Date: Length
			param[0].setFigure_WorkDate(txt_StrgRsltDateLen.getText());
			//#CM692309
			// Storage Result Date: Max Length
			param[0].setMaxFigure_WorkDate(lbl_MaxLenStrgRsltDate.getText());
			//#CM692310
			// Storage Result Date: Position
			param[0].setPosition_WorkDate(txt_StrgRsltDatePst.getText());
			
			//#CM692311
			// Result division: Enabled
			param[0].setValid_ResultFlag(chk_RsltFlg.getChecked());
			//#CM692312
			// Result division: Length
			param[0].setFigure_ResultFlag(txt_RsltFlgLen.getText());
			//#CM692313
			// Result division: Max Length
			param[0].setMaxFigure_ResultFlag(lbl_MaxLenRsltFlg.getText());
			//#CM692314
			// Result division: Position
			param[0].setPosition_ResultFlag(txt_RsltFlgPst.getText());
			
			//#CM692315
			// Expiry Date: Enabled
			param[0].setValid_UseByDate(chk_UseByDate.getChecked());
			//#CM692316
			// Expiry Date: Length
			param[0].setFigure_UseByDate(txt_UseByDateLen.getText());
			//#CM692317
			// Expiry Date: Max Length
			param[0].setMaxFigure_UseByDate(lbl_MaxLenUseByDate.getText());
			//#CM692318
			// Expiry Date: Position
			param[0].setPosition_UseByDate(txt_UseByDatePst.getText());

			//#CM692319
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
				//#CM692320
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
				//#CM692321
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

	//#CM692322
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692323
	/** 
	 * Clicking on Clear button for field items set for reporting data (setting of Receiving Result field) invokes this.<BR>
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
//#CM692324
//end of class
