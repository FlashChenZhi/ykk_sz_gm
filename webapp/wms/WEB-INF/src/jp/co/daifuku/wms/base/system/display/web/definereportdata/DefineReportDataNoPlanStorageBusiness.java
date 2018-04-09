// $Id: DefineReportDataNoPlanStorageBusiness.java,v 1.2 2006/11/13 08:18:45 suresh Exp $

//#CM689861
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

//#CM689862
/**
 * Designer :  S.Ishigane <BR>
 * Maker :     T.Uehata <BR>
 * <BR>
 * Allow this class to set a field for reporting data (to set the Unplanned Storage Result field).<BR>
 * Set the searched value via the schedule for each field item.
 * Set the contents entered via screen for the parameter. Allow the schedule to set the Report Data field (set the Unplanned Storage Result field) based on the parameter.<BR>
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
 * 	 Consignor Code +<BR>
 * 	 Consignor Name+<BR>
 * 	 Item Code +<BR>
 * 	 Bundle ITF+<BR>
 * 	 Case ITF+<BR>
 * 	 Packed qty per bundle+<BR>
 * 	 Packed Qty per Case+<BR>
 * 	 Item Name+<BR>
 *   Piece Storage Location+<BR>
 *   Case Storage Location+<BR>
 * 	 Result Piece Qty+<BR>
 * 	 Result Case Qty+<BR>
 * 	 Storage Result Date+<BR>
 * 	 Expiry Date+<BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:45 $
 * @author  $Author: suresh $
 */
public class DefineReportDataNoPlanStorageBusiness extends DefineReportDataNoPlanStorage implements WMSConstants
{
	private static final String DO_DEFINEREPORT = "/system/definereportdata/DefineReportData.do";
	//#CM689863
	// Class fields --------------------------------------------------

	//#CM689864
	// Class variables -----------------------------------------------

	//#CM689865
	// Class method --------------------------------------------------

	//#CM689866
	// Constructors --------------------------------------------------

	//#CM689867
	// Public methods ------------------------------------------------
	//#CM689868
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
			//#CM689869
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM689870
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM689871
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM689872
			//Set the path to the help file.
			//#CM689873
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM689874
		// MSG-0009= Do you add it?
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}
	
	//#CM689875
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
		//#CM689876
		// Title
		lbl_SettingName.setResourceKey(this.getViewState().getString("TITLE"));

		//#CM689877
		// Check that the length is read-only. Length.
		txt_CnsgnrCdLen.setReadOnly(true);
		txt_CnsgnrNmLen.setReadOnly(true);
		txt_ItemCdLen.setReadOnly(true);
		txt_BundleItfLen.setReadOnly(true);
		txt_CaseItfLen.setReadOnly(true);
		txt_BundleEtrLen.setReadOnly(true);
		txt_CaseEtrLen.setReadOnly(true);
		txt_ItemNmLen.setReadOnly(true);
		txt_PieceStrgLctLen.setReadOnly(true);
		txt_CaseStrgLctLen.setReadOnly(true);
		txt_RsltPieceQtyLen.setReadOnly(true);
		txt_RsltCaseQtyLen.setReadOnly(true);
		txt_StrgRsltDateLen.setReadOnly(true);
		txt_UseByDateLen.setReadOnly(true);

		setInistView();
	}

	//#CM689878
	// Package methods -----------------------------------------------

	//#CM689879
	// Protected methods ---------------------------------------------

	//#CM689880
	// Private methods -----------------------------------------------

	//#CM689881
	/** 
	 * Allow this method to set the initial values for screen.<BR>
	 * <BR>
	 * Summary: Sets up the settings of each field item in the screen from property file.<BR>
	 * 
	 * @throws Exception Report all exceptions.
	 */
	private void setInistView() throws Exception
	{
		//#CM689882
		// Set the initial value of the focus to Consignor Code Enabled.
		setFocus(chk_CnsgnrCd);

		Connection conn = null;
		try
		{
			//#CM689883
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter initParam = new DefineDataParameter();
			initParam.setSelectDefineReportData(
				DefineDataParameter.SELECTDEFINEREPORTDATA_NOPLANSTORAGE);
			WmsScheduler schedule = new DefineReportDataSCH();
			DefineDataParameter[] param = (DefineDataParameter[]) schedule.query(conn, initParam);

			if (param != null)
			{
				//#CM689884
				// Consignor Code: Enabled
				chk_CnsgnrCd.setChecked(param[0].getValid_ConsignorCode());
				//#CM689885
				// Consignor Code: Length
				txt_CnsgnrCdLen.setText(param[0].getFigure_ConsignorCode());
				//#CM689886
				// Consignor Code: Max Length
				lbl_MaxLenCnsgnrCd.setText(param[0].getMaxFigure_ConsignorCode());
				//#CM689887
				// Consignor Code: Position
				txt_CnsgnrCdPst.setText(param[0].getPosition_ConsignorCode());
				
				//#CM689888
				// Consignor Name: Enabled
				chk_CnsgnrNm.setChecked(param[0].getValid_ConsignorName());
				//#CM689889
				// Consignor Name: Length
				txt_CnsgnrNmLen.setText(param[0].getFigure_ConsignorName());
				//#CM689890
				// Consignor Name: Max Length
				lbl_MaxLenCnsgnrNm.setText(param[0].getMaxFigure_ConsignorName());
				//#CM689891
				// Consignor Name: Position
				txt_CnsgnrNmPst.setText(param[0].getPosition_ConsignorName());
				
				//#CM689892
				// Item Code: Enabled
				chk_ItemCd.setChecked(param[0].getValid_ItemCode());
				//#CM689893
				// Item Code: Length
				txt_ItemCdLen.setText(param[0].getFigure_ItemCode());
				//#CM689894
				// Item Code: Max Length
				lbl_MaxLenItemCd.setText(param[0].getMaxFigure_ItemCode());
				//#CM689895
				// Item Code: Position
				txt_ItemCdPst.setText(param[0].getPosition_ItemCode());
				
				//#CM689896
				// Bundle ITF: Enabled
				chk_BundleItf.setChecked(param[0].getValid_BundleItf());
				//#CM689897
				// Bundle ITF: Length
				txt_BundleItfLen.setText(param[0].getFigure_BundleItf());
				//#CM689898
				// Bundle ITF: Max Length
				lbl_MaxLenBundleItf.setText(param[0].getMaxFigure_BundleItf());
				//#CM689899
				// Bundle ITF: Position
				txt_BundleItfPst.setText(param[0].getPosition_BundleItf());
				
				//#CM689900
				// Case ITF: Enabled
				chk_CaseItf.setChecked(param[0].getValid_Itf());
				//#CM689901
				// Case ITF: Length
				txt_CaseItfLen.setText(param[0].getFigure_Itf());
				//#CM689902
				// Case ITF: Max Length
				lbl_MaxLenCaseItf.setText(param[0].getMaxFigure_Itf());
				//#CM689903
				// Case ITF: Position
				txt_CaseItfPst.setText(param[0].getPosition_Itf());
				
				//#CM689904
				// Packed qty per bundle: Enabled
				chk_BundleEtr.setChecked(param[0].getValid_BundleEnteringQty());
				//#CM689905
				// Packed qty per bundle: Length
				txt_BundleEtrLen.setText(param[0].getFigure_BundleEnteringQty());
				//#CM689906
				// Packed qty per bundle: Max Length
				lbl_MaxLenBundleEtr.setText(param[0].getMaxFigure_BundleEnteringQty());
				//#CM689907
				// Packed qty per bundle: Position
				txt_BundleEtrPst.setText(param[0].getPosition_BundleEnteringQty());

				//#CM689908
				// Packed Qty per Case: Enabled
				chk_CaseEtr.setChecked(param[0].getValid_EnteringQty());
				//#CM689909
				// Packed Qty per Case: Length
				txt_CaseEtrLen.setText(param[0].getFigure_EnteringQty());
				//#CM689910
				// Packed Qty per Case: Max Length
				lbl_MaxLenCaseEtr.setText(param[0].getMaxFigure_EnteringQty());
				//#CM689911
				// Packed Qty per Case: Position
				txt_CaseEtrPst.setText(param[0].getPosition_EnteringQty());

				//#CM689912
				// Item Name: Enabled
				chk_ItemNm.setChecked(param[0].getValid_ItemName());
				//#CM689913
				// Item Name: Length
				txt_ItemNmLen.setText(param[0].getFigure_ItemName());
				//#CM689914
				// Item Name: Max Length
				lbl_MaxLenItemNm.setText(param[0].getMaxFigure_ItemName());
				//#CM689915
				// Item Name: Position
				txt_ItemNmPst.setText(param[0].getPosition_ItemName());

				//#CM689916
				// Piece Storage Location: Enabled
				chk_PieceStrgLct.setChecked(param[0].getValid_PieceLocation());
				//#CM689917
				// Piece Storage Location: Length
				txt_PieceStrgLctLen.setText(param[0].getFigure_PieceLocation());
				//#CM689918
				// Piece Storage Location: Max Length
				lbl_MaxLenPieceStrgLct.setText(param[0].getMaxFigure_PieceLocation());
				//#CM689919
				// Piece Storage Location: Position
				txt_PieceStrgLctPst.setText(param[0].getPosition_PieceLocation());

				//#CM689920
				// Case Storage Location: Enabled
				chk_CaseStrgLct.setChecked(param[0].getValid_CaseLocation());
				//#CM689921
				// Case Storage Location: Length
				txt_CaseStrgLctLen.setText(param[0].getFigure_CaseLocation());
				//#CM689922
				// Case Storage Location: Max Length
				lbl_MaxLenCaseStrgLct.setText(param[0].getMaxFigure_CaseLocation());
				//#CM689923
				// Case Storage Location: Position
				txt_CaseStrgLctPst.setText(param[0].getPosition_CaseLocation());

				//#CM689924
				// Result Piece Qty: Enabled
				chk_RsltPieceQty.setChecked(param[0].getValid_PieceResultQty());
				//#CM689925
				// Result Piece Qty: Length
				txt_RsltPieceQtyLen.setText(param[0].getFigure_PieceResultQty());
				//#CM689926
				// Result Piece Qty: Max Length
				lbl_MaxLenRsltPieceQty.setText(param[0].getMaxFigure_PieceResultQty());
				//#CM689927
				// Result Piece Qty: Position
				txt_RsltPieceQtyPst.setText(param[0].getPosition_PieceResultQty());

				//#CM689928
				// Result Case Qty: Enabled
				chk_RsltCaseQty.setChecked(param[0].getValid_CaseResultQty());
				//#CM689929
				// Result Case Qty: Length
				txt_RsltCaseQtyLen.setText(param[0].getFigure_CaseResultQty());
				//#CM689930
				// Result Case Qty: Max Length
				lbl_MaxLenRsltCaseQty.setText(param[0].getMaxFigure_CaseResultQty());
				//#CM689931
				// Result Case Qty: Position
				txt_RsltCaseQtyPst.setText(param[0].getPosition_CaseResultQty());

				//#CM689932
				// Storage Result Date: Enabled
				chk_StrgRsltDate.setChecked(param[0].getValid_WorkDate());
				//#CM689933
				// Storage Result Date: Length
				txt_StrgRsltDateLen.setText(param[0].getFigure_WorkDate());
				//#CM689934
				// Storage Result Date: Max Length
				lbl_MaxLenStrgRsltDate.setText(param[0].getMaxFigure_WorkDate());
				//#CM689935
				// Storage Result Date: Position
				txt_StrgRsltDatePst.setText(param[0].getPosition_WorkDate());

				//#CM689936
				// Expiry Date: Enabled
				chk_UseByDate.setChecked(param[0].getValid_UseByDate());
				//#CM689937
				// Expiry Date: Length
				txt_UseByDateLen.setText(param[0].getFigure_UseByDate());
				//#CM689938
				// Expiry Date: Max Length
				lbl_MaxLenUseByDate.setText(param[0].getMaxFigure_UseByDate());
				//#CM689939
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
				//#CM689940
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

	//#CM689941
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

		//#CM689942
		// Consignor Code: Position
		if (position.getText().equals(txt_CnsgnrCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689943
		// Consignor Name: Position
		if (position.getText().equals(txt_CnsgnrNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689944
		// Item Code: Position
		if (position.getText().equals(txt_ItemCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689945
		// Bundle ITF: Position
		if (position.getText().equals(txt_BundleItfPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689946
		// Case ITF: Position
		if (position.getText().equals(txt_CaseItfPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689947
		// Packed qty per bundle: Position
		if (position.getText().equals(txt_BundleEtrPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689948
		// Packed Qty per Case: Position
		if (position.getText().equals(txt_CaseEtrPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689949
		// Item Name: Position
		if (position.getText().equals(txt_ItemNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689950
		// Piece Storage Location: Position
		if (position.getText().equals(txt_PieceStrgLctPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689951
		// Case Storage Location: Position
		if (position.getText().equals(txt_CaseStrgLctPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689952
		// Result Piece Qty: Position
		if (position.getText().equals(txt_RsltPieceQtyPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689953
		// Result Case Qty: Position
		if (position.getText().equals(txt_RsltCaseQtyPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689954
		// Storage Result Date: Position
		if (position.getText().equals(txt_StrgRsltDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689955
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

	//#CM689956
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

		if (repeatChkFlg && chk_UseByDate.getChecked())
		{
			repeatChkFlg = positionCheck(txt_UseByDatePst);
		}
		
		return repeatChkFlg;
	}
	
	//#CM689957
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
				&& !chk_ItemCd.getChecked()
				&& !chk_BundleItf.getChecked()
				&& !chk_CaseItf.getChecked()
				&& !chk_BundleEtr.getChecked()
				&& !chk_CaseEtr.getChecked()
				&& !chk_ItemNm.getChecked()
				&& !chk_PieceStrgLct.getChecked()
				&& !chk_CaseStrgLct.getChecked()
				&& !chk_RsltPieceQty.getChecked()
				&& !chk_RsltCaseQty.getChecked()
				&& !chk_StrgRsltDate.getChecked()
				&& !chk_UseByDate.getChecked())
		{
			return false;
		}
		return true;
	}
	//#CM689958
	// Event handler methods -----------------------------------------
	//#CM689959
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689960
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689961
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Back_Server(ActionEvent e) throws Exception
	{
	}


	//#CM689962
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
		//#CM689963
		// Shift to the original screen.
		forward(DO_DEFINEREPORT);
	}

	//#CM689964
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689965
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

	//#CM689966
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Valid1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689967
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DigitsUseLength1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689968
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLength1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689969
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Position1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689970
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Valid2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689971
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DigitsUseLength2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689972
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLength2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689973
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Position2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689974
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ResultPieceQtyTwoByte_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689975
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689976
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltPieceQty_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689977
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689978
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689979
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689980
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRsltPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689981
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689982
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689983
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltPieceQtyPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689984
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689985
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689986
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689987
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689988
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689989
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689990
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689991
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689992
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689993
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689994
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ResultCaseQtyTwoByte_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689995
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689996
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RsltCaseQty_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689997
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689998
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689999
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690000
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenRsltCaseQty_Server(ActionEvent e) throws Exception
	{
	}


	//#CM690001
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690002
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690003
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RsltCaseQtyPst_TabKey(ActionEvent e) throws Exception
	{
	}
	
	//#CM690004
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690005
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690006
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690007
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690008
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690009
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690010
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690011
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690012
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690013
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690014
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageResultDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690015
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_StrgRsltDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690016
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_StrgRsltDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690017
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690018
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690019
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690020
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenStrgRsltDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690021
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690022
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690023
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690024
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690025
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690026
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690027
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690028
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690029
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690030
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690031
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690032
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690033
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690034
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690035
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690036
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleItf_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690037
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690038
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690039
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690040
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenBundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690041
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690042
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690043
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItfPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690044
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690045
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690046
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_UseByDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690047
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690048
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690049
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690050
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenUseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690051
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690052
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690053
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690054
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690055
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690056
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseItf_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690057
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690058
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690059
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690060
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690061
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690062
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690063
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItfPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690064
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690065
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690066
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_BundleEtr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690067
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690068
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690069
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690070
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenBundleEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690071
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690072
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690073
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEtrPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690074
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690075
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690076
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseEtr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690077
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690078
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690079
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690080
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690081
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690082
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690083
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690084
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690085
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690086
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690087
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690088
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690089
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690090
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690091
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690092
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690093
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690094
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PiceStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690095
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PieceStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690096
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PieceStrgLct_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690097
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceStrgLctLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690098
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceStrgLctLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690099
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceStrgLctLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690100
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenPieceStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690101
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceStrgLctPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690102
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceStrgLctPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690103
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceStrgLctPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690104
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690105
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690106
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseStrgLct_Change(ActionEvent e) throws Exception
	{
	}

	//#CM690107
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStrgLctLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690108
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStrgLctLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690109
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStrgLctLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690110
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690111
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStrgLctPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690112
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStrgLctPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM690113
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStrgLctPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM690114
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}


	//#CM690115
	/** 
	 * Clicking on Add button for field items set for reporting data (setting of Unplanned Storage Result field) invokes this.<BR>
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
		//#CM690116
		// Set the initial value of the focus to Consignor Code Enabled.
		setFocus(chk_CnsgnrCd);

		//#CM690117
		// Check the checkbox.
		if (!checkCheckBox())
		{
			//#CM690118
			// 6023174=Please select 1 or more items.
			message.setMsgResourceKey("6023174");
			return;
		}
		
		//#CM690119
		// Check for input.
		WmsCheckker.checkDefine(lbl_ConsignorCode, chk_CnsgnrCd, txt_CnsgnrCdLen, lbl_MaxLenCnsgnrCd, txt_CnsgnrCdPst);
		WmsCheckker.checkDefine(lbl_ConsignorName, chk_CnsgnrNm, txt_CnsgnrNmLen, lbl_MaxLenCnsgnrNm, txt_CnsgnrNmPst);
		WmsCheckker.checkDefine(lbl_ItemCode, chk_ItemCd, txt_ItemCdLen, lbl_MaxLenItemCd, txt_ItemCdPst);
		WmsCheckker.checkDefine(lbl_BundleItf, chk_BundleItf, txt_BundleItfLen, lbl_MaxLenBundleItf, txt_BundleItfPst);
		WmsCheckker.checkDefine(lbl_CaseItf, chk_CaseItf, txt_CaseItfLen, lbl_MaxLenCaseItf, txt_CaseItfPst);
		WmsCheckker.checkDefine(lbl_BundleEntering, chk_BundleEtr, txt_BundleEtrLen, lbl_MaxLenBundleEtr, txt_BundleEtrPst);
		WmsCheckker.checkDefine(lbl_CaseEntering, chk_CaseEtr, txt_CaseEtrLen, lbl_MaxLenCaseEtr, txt_CaseEtrPst);
		WmsCheckker.checkDefine(lbl_ItemName, chk_ItemNm, txt_ItemNmLen, lbl_MaxLenItemNm, txt_ItemNmPst);
		WmsCheckker.checkDefine(lbl_PiceStrgLct, chk_PieceStrgLct, txt_PieceStrgLctLen, lbl_MaxLenPieceStrgLct, txt_PieceStrgLctPst);
		WmsCheckker.checkDefine(lbl_CaseStrgLct, chk_CaseStrgLct, txt_CaseStrgLctLen, lbl_MaxLenCaseStrgLct, txt_CaseStrgLctPst);
		WmsCheckker.checkDefine(lbl_ResultPieceQtyTwoByte, chk_RsltPieceQty, txt_RsltPieceQtyLen, lbl_MaxLenRsltPieceQty, txt_RsltPieceQtyPst);
		WmsCheckker.checkDefine(lbl_ResultCaseQtyTwoByte, chk_RsltCaseQty, txt_RsltCaseQtyLen, lbl_MaxLenRsltCaseQty, txt_RsltCaseQtyPst);
		WmsCheckker.checkDefine(lbl_StorageResultDate, chk_StrgRsltDate, txt_StrgRsltDateLen, lbl_MaxLenStrgRsltDate, txt_StrgRsltDatePst);
		WmsCheckker.checkDefine(lbl_UseByDate, chk_UseByDate, txt_UseByDateLen, lbl_MaxLenUseByDate, txt_UseByDatePst);
	
		Connection conn = null;
		
		try
		{
			//#CM690120
			// Check for duplicate Position.
			if (!checkRepeat())
			{
				//#CM690121
				// Display the error message and close it.
				//#CM690122
				// 6023098=No duplicate of sequential order is allowed.
				message.setMsgResourceKey("6023098");
				return;
			}
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	
			DefineDataParameter[] param = new DefineDataParameter[1];
			param[0] = new DefineDataParameter();
	
			WmsScheduler schedule = new DefineReportDataSCH();
	
			//#CM690123
			// Set the value for the parameter.
			param[0].setSelectDefineReportData(DefineDataParameter.SELECTDEFINEREPORTDATA_NOPLANSTORAGE);

			//#CM690124
			// Consignor Code: Enabled
			param[0].setValid_ConsignorCode(chk_CnsgnrCd.getChecked());
			//#CM690125
			// Consignor Code: Length
			param[0].setFigure_ConsignorCode(txt_CnsgnrCdLen.getText());
			//#CM690126
			// Consignor Code: Max Length
			param[0].setMaxFigure_ConsignorCode(lbl_MaxLenCnsgnrCd.getText());
			//#CM690127
			// Consignor Code: Position
			param[0].setPosition_ConsignorCode(txt_CnsgnrCdPst.getText());
			
			//#CM690128
			// Consignor Name: Enabled
			param[0].setValid_ConsignorName(chk_CnsgnrNm.getChecked());
			//#CM690129
			// Consignor Name: Length
			param[0].setFigure_ConsignorName(txt_CnsgnrNmLen.getText());
			//#CM690130
			// Consignor Name: Max Length
			param[0].setMaxFigure_ConsignorName(lbl_MaxLenCnsgnrNm.getText());
			//#CM690131
			// Consignor Name: Position
			param[0].setPosition_ConsignorName(txt_CnsgnrNmPst.getText());
			
			//#CM690132
			// Item Code: Enabled
			param[0].setValid_ItemCode(chk_ItemCd.getChecked());
			//#CM690133
			// Item Code: Length
			param[0].setFigure_ItemCode(txt_ItemCdLen.getText());
			//#CM690134
			// Item Code: Max Length
			param[0].setMaxFigure_ItemCode(lbl_MaxLenItemCd.getText());
			//#CM690135
			// Item Code: Position
			param[0].setPosition_ItemCode(txt_ItemCdPst.getText());
			
			//#CM690136
			// Bundle ITF: Enabled
			param[0].setValid_BundleItf(chk_BundleItf.getChecked());
			//#CM690137
			// Bundle ITF: Length
			param[0].setFigure_BundleItf(txt_BundleItfLen.getText());
			//#CM690138
			// Bundle ITF: Max Length
			param[0].setMaxFigure_BundleItf(lbl_MaxLenBundleItf.getText());
			//#CM690139
			// Bundle ITF: Position
			param[0].setPosition_BundleItf(txt_BundleItfPst.getText());
			
			//#CM690140
			// Case ITF: Enabled
			param[0].setValid_Itf(chk_CaseItf.getChecked());
			//#CM690141
			// Case ITF: Length
			param[0].setFigure_Itf(txt_CaseItfLen.getText());
			//#CM690142
			// Case ITF: Max Length
			param[0].setMaxFigure_Itf(lbl_MaxLenCaseItf.getText());
			//#CM690143
			// Case ITF: Position
			param[0].setPosition_Itf(txt_CaseItfPst.getText());
			
			//#CM690144
			// Packed qty per bundle: Enabled
			param[0].setValid_BundleEnteringQty(chk_BundleEtr.getChecked());
			//#CM690145
			// Packed qty per bundle: Length
			param[0].setFigure_BundleEnteringQty(txt_BundleEtrLen.getText());
			//#CM690146
			// Packed qty per bundle: Max Length
			param[0].setMaxFigure_BundleEnteringQty(lbl_MaxLenBundleEtr.getText());
			//#CM690147
			// Packed qty per bundle: Position
			param[0].setPosition_BundleEnteringQty(txt_BundleEtrPst.getText());
			
			//#CM690148
			// Packed Qty per Case: Enabled
			param[0].setValid_EnteringQty(chk_CaseEtr.getChecked());
			//#CM690149
			// Packed Qty per Case: Length
			param[0].setFigure_EnteringQty(txt_CaseEtrLen.getText());
			//#CM690150
			// Packed Qty per Case: Max Length
			param[0].setMaxFigure_EnteringQty(lbl_MaxLenCaseEtr.getText());
			//#CM690151
			// Packed Qty per Case: Position
			param[0].setPosition_EnteringQty(txt_CaseEtrPst.getText());
			
			//#CM690152
			// Item Name: Enabled
			param[0].setValid_ItemName(chk_ItemNm.getChecked());
			//#CM690153
			// Item Name: Length
			param[0].setFigure_ItemName(txt_ItemNmLen.getText());
			//#CM690154
			// Item Name: Max Length
			param[0].setMaxFigure_ItemName(lbl_MaxLenItemNm.getText());
			//#CM690155
			// Item Name: Position
			param[0].setPosition_ItemName(txt_ItemNmPst.getText());

			//#CM690156
			// Piece Storage Location: Enabled
			param[0].setValid_PieceLocation(chk_PieceStrgLct.getChecked());
			//#CM690157
			// Piece Storage Location: Length
			param[0].setFigure_PieceLocation(txt_PieceStrgLctLen.getText());
			//#CM690158
			// Piece Storage Location: Max Length
			param[0].setMaxFigure_PieceLocation(lbl_MaxLenPieceStrgLct.getText());
			//#CM690159
			// Piece Storage Location: Position
			param[0].setPosition_PieceLocation(txt_PieceStrgLctPst.getText());
			
			//#CM690160
			// Case Storage Location: Enabled
			param[0].setValid_CaseLocation(chk_CaseStrgLct.getChecked());
			//#CM690161
			// Case Storage Location: Length
			param[0].setFigure_CaseLocation(txt_CaseStrgLctLen.getText());
			//#CM690162
			// Case Storage Location: Max Length
			param[0].setMaxFigure_CaseLocation(lbl_MaxLenCaseStrgLct.getText());
			//#CM690163
			// Case Storage Location: Position
			param[0].setPosition_CaseLocation(txt_CaseStrgLctPst.getText());
			
			//#CM690164
			// Result Piece Qty: Enabled
			param[0].setValid_PieceResultQty(chk_RsltPieceQty.getChecked());
			//#CM690165
			// Result Piece Qty: Length
			param[0].setFigure_PieceResultQty(txt_RsltPieceQtyLen.getText());
			//#CM690166
			// Result Piece Qty: Max Length
			param[0].setMaxFigure_PieceResultQty(lbl_MaxLenRsltPieceQty.getText());
			//#CM690167
			// Result Piece Qty: Position
			param[0].setPosition_PieceResultQty(txt_RsltPieceQtyPst.getText());
			
			//#CM690168
			// Result Case Qty: Enabled
			param[0].setValid_CaseResultQty(chk_RsltCaseQty.getChecked());
			//#CM690169
			// Result Case Qty: Length
			param[0].setFigure_CaseResultQty(txt_RsltCaseQtyLen.getText());
			//#CM690170
			// Result Case Qty: Max Length
			param[0].setMaxFigure_CaseResultQty(lbl_MaxLenRsltCaseQty.getText());
			//#CM690171
			// Result Case Qty: Position
			param[0].setPosition_CaseResultQty(txt_RsltCaseQtyPst.getText());
			
			//#CM690172
			// Storage Result Date: Enabled
			param[0].setValid_WorkDate(chk_StrgRsltDate.getChecked());
			//#CM690173
			// Storage Result Date: Length
			param[0].setFigure_WorkDate(txt_StrgRsltDateLen.getText());
			//#CM690174
			// Storage Result Date: Max Length
			param[0].setMaxFigure_WorkDate(lbl_MaxLenStrgRsltDate.getText());
			//#CM690175
			// Storage Result Date: Position
			param[0].setPosition_WorkDate(txt_StrgRsltDatePst.getText());

			//#CM690176
			// Expiry Date: Enabled
			param[0].setValid_UseByDate(chk_UseByDate.getChecked());
			//#CM690177
			// Expiry Date: Length
			param[0].setFigure_UseByDate(txt_UseByDateLen.getText());
			//#CM690178
			// Expiry Date: Max Length
			param[0].setMaxFigure_UseByDate(lbl_MaxLenUseByDate.getText());
			//#CM690179
			// Expiry Date: Position
			param[0].setPosition_UseByDate(txt_UseByDatePst.getText());

			//#CM690180
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
				//#CM690181
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
				//#CM690182
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

	//#CM690183
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM690184
	/** 
	 * Clicking on Clear button for field items set for reporting data (setting of Unplanned Storage Result field) invokes this.<BR>
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

	//#CM690185
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_RptClm_SetNoPlanStrg_Click(ActionEvent e) throws Exception
	{
	}
}
//#CM690186
//end of class
