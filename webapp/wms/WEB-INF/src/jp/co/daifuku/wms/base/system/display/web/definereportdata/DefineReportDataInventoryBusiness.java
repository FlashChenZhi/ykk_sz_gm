// $Id: DefineReportDataInventoryBusiness.java,v 1.2 2006/11/13 08:18:47 suresh Exp $

//#CM689225
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

//#CM689226
/**
 * Designer :  D.Hakui <BR>
 * Maker :     D.Hakui <BR>
 * <BR>
 * Allow this class to set a field for reporting data (to set the Inventory Check Result field).<BR>
 * Set the searched value via the schedule for each field item.
 * Set the contents entered via screen for the parameter. Allow the schedule to set the Report Data field (set the Inventory Check Result field) based on the parameter.<BR>
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
 * 	 Item Name+<BR>
 *   Packed Qty per Case+<BR>
 *   System Stock Qty (Total Bulk Qty) +<BR>
 *   Inventory CheckResult Qty (Total Bulk Qty) +<BR>
 *   Inventory Check Location+<BR>
 *   Inventory Check Result Date +<BR>
 *   Expiry Date+<BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:47 $
 * @author  $Author: suresh $
 */
public class DefineReportDataInventoryBusiness extends DefineReportDataInventory implements WMSConstants
{
	private static final String DO_DEFINEREPORT = "/system/definereportdata/DefineReportData.do";
	
	//#CM689227
	// Class fields --------------------------------------------------

	//#CM689228
	// Class variables -----------------------------------------------

	//#CM689229
	// Class method --------------------------------------------------

	//#CM689230
	// Constructors --------------------------------------------------

	//#CM689231
	// Public methods ------------------------------------------------

	//#CM689232
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
			//#CM689233
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM689234
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM689235
			//Set the path to the help file.
			btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM689236
		// MSG-0009= Do you add it?
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}

	//#CM689237
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
		//#CM689238
		// Title
		lbl_SettingName.setResourceKey(this.getViewState().getString("TITLE"));
		
		//#CM689239
		// Check that the length is read-only. Length.
		txt_CnsgnrCdLen.setReadOnly(true);
		txt_CnsgnrNmLen.setReadOnly(true);
		txt_ItemCdLen.setReadOnly(true);
		txt_ItemNmLen.setReadOnly(true);
		txt_CaseEtrLen.setReadOnly(true);
		txt_SysStockQtyPtlLen.setReadOnly(true);
		txt_InvntRsltQtyPtlLen.setReadOnly(true);
		txt_InvntryLctLen.setReadOnly(true);
		txt_InvntryRsltDateLen.setReadOnly(true);
		txt_UseByDateLen.setReadOnly(true);

		setInistView();
	}

	//#CM689240
	// Package methods -----------------------------------------------

	//#CM689241
	// Protected methods ---------------------------------------------

	//#CM689242
	// Private methods -----------------------------------------------
	//#CM689243
	/** 
	 * Allow this method to set the initial values for screen.<BR>
	 * <BR>
	 * Summary: Sets up the settings of each field item in the screen from property file.<BR>
	 * 
	 * @throws Exception Report all exceptions.
	 */
	private void setInistView() throws Exception
	{
		//#CM689244
		// Set the initial value of the focus to Consignor Code Enabled.
		setFocus(chk_CnsgnrCd);

		Connection conn = null;
		try
		{
			//#CM689245
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			DefineDataParameter initParam = new DefineDataParameter();
			initParam.setSelectDefineReportData(DefineDataParameter.SELECTDEFINEREPORTDATA_INVENTORY);
			WmsScheduler schedule = new DefineReportDataSCH();
			DefineDataParameter[] param = (DefineDataParameter[]) schedule.query(conn, initParam);

			if (param != null)
			{
				//#CM689246
				//	Consignor Code (Enabled)
				chk_CnsgnrCd.setChecked(param[0].getValid_ConsignorCode());
				//#CM689247
				//	Consignor Code (Length)
				txt_CnsgnrCdLen.setText(param[0].getFigure_ConsignorCode());
				//#CM689248
				//	Consignor Code (Max Length)
				lbl_MaxLenCnsgnrCd.setText(param[0].getMaxFigure_ConsignorCode());
				//#CM689249
				//	Consignor Code (Position)
				txt_CnsgnrCdPst.setText(param[0].getPosition_ConsignorCode());
				
				//#CM689250
				//	Consignor Name (Enabled)
				chk_CnsgnrNm.setChecked(param[0].getValid_ConsignorName());
				//#CM689251
				//	Consignor Name (Length)
				txt_CnsgnrNmLen.setText(param[0].getFigure_ConsignorName());
				//#CM689252
				//	Consignor Name (Max Length)
				lbl_MaxLenCnsgnrNm.setText(param[0].getMaxFigure_ConsignorName());
				//#CM689253
				//	Consignor Name (Position)
				txt_CnsgnrNmPst.setText(param[0].getPosition_ConsignorName());
				
				//#CM689254
				//	Item Code (Enabled)
				chk_ItemCd.setChecked(param[0].getValid_ItemCode());
				//#CM689255
				//	Item Code (Length)
				txt_ItemCdLen.setText(param[0].getFigure_ItemCode());
				//#CM689256
				//	Item Code (Max Length)
				lbl_MaxLenItemCd.setText(param[0].getMaxFigure_ItemCode());
				//#CM689257
				//	Item Code (Position)
				txt_ItemCdPst.setText(param[0].getPosition_ItemCode());
				
				//#CM689258
				//	Item Name (Enabled)
				chk_ItemNm.setChecked(param[0].getValid_ItemName());
				//#CM689259
				//	Item Name (Length)
				txt_ItemNmLen.setText(param[0].getFigure_ItemName());
				//#CM689260
				//	Item Name (Max Length)
				lbl_MaxLenItemNm.setText(param[0].getMaxFigure_ItemName());
				//#CM689261
				//	Item Name (Position)
				txt_ItemNmPst.setText(param[0].getPosition_ItemName());
				
				//#CM689262
				//	Packed Qty per Case (Enabled)
				chk_CaseEtr.setChecked(param[0].getValid_EnteringQty());
				//#CM689263
				//	Packed Qty per Case (Length)
				txt_CaseEtrLen.setText(param[0].getFigure_EnteringQty());
				//#CM689264
				//	Packed Qty per Case (Max Length)
				lbl_MaxLenCaseEtr.setText(param[0].getMaxFigure_EnteringQty());
				//#CM689265
				//	Packed Qty per Case (Position)
				txt_CaseEtrPst.setText(param[0].getPosition_EnteringQty());
				
				//#CM689266
				//	System Stock Qty (Total Bulk Qty) (Enabled)
				chk_SysStockQtyPtl.setChecked(param[0].getValid_PlanQty());
				//#CM689267
				//	System Stock Qty (Total Bulk Qty)(Length)
				txt_SysStockQtyPtlLen.setText(param[0].getFigure_PlanQty());
				//#CM689268
				//	System Stock Qty (Total Bulk Qty) (Max Length)
				lbl_MaxLenSysStockQtyPtl.setText(param[0].getMaxFigure_PlanQty());
				//#CM689269
				//	System Stock Qty (Total Bulk Qty) (Position)
				txt_SysStockQtyPtlPos.setText(param[0].getPosition_PlanQty());
				
				//#CM689270
				//	Inventory CheckResult Qty (Total Bulk Qty) (Enabled)
				chk_InvntRsltQtyPtl.setChecked(param[0].getValid_CaseResultQty());
				//#CM689271
				//	Inventory CheckResult Qty (Total Bulk Qty)(Length)
				txt_InvntRsltQtyPtlLen.setText(param[0].getFigure_CaseResultQty());
				//#CM689272
				//	Inventory CheckResult Qty (Total Bulk Qty) (Max Length)
				lbl_MaxLenInvntRsltQtyPtl.setText(param[0].getMaxFigure_CaseResultQty());
				//#CM689273
				//	Inventory CheckResult Qty (Total Bulk Qty) (Position)
				txt_InvntRsltQtyPtlPst.setText(param[0].getPosition_CaseResultQty());
				
				//#CM689274
				//	Inventory Check Location (Enabled)
				chk_InvntryLct.setChecked(param[0].getValid_CaseLocation());
				//#CM689275
				//	Inventory Check Location (Length)
				txt_InvntryLctLen.setText(param[0].getFigure_CaseLocation());
				//#CM689276
				//	Inventory Check Location (Max Length)
				lbl_MaxLenInvntryLct.setText(param[0].getMaxFigure_CaseLocation());
				//#CM689277
				//	Inventory Check Location (Position)
				txt_InvntryLctPst.setText(param[0].getPosition_CaseLocation());
				
				//#CM689278
				//	Inventory Check Result Date (Enabled)
				chk_InvntryRsltDate.setChecked(param[0].getValid_WorkDate());
				//#CM689279
				//	Inventory Check Result Date (Length)
				txt_InvntryRsltDateLen.setText(param[0].getFigure_WorkDate());
				//#CM689280
				//	Inventory Check Result Date (Max Length)
				lbl_MaxLenInvntryRsltDate.setText(param[0].getMaxFigure_WorkDate());
				//#CM689281
				//	Inventory Check Result Date (Position)
				txt_InvntryRsltDatePst.setText(param[0].getPosition_WorkDate());

				//#CM689282
				//	Expiry Date (Enabled)
				chk_UseByDate.setChecked(param[0].getValid_UseByDate());
				//#CM689283
				//	Expiry Date (Length)
				txt_UseByDateLen.setText(param[0].getFigure_UseByDate());
				//#CM689284
				//	Expiry Date (Max Length)
				lbl_MaxLenUseByDate.setText(param[0].getMaxFigure_UseByDate());
				//#CM689285
				//	Expiry Date (Position)
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
				//#CM689286
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
	//#CM689287
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

		//#CM689288
		// Consignor Code: Position
		if (position.getText().equals(txt_CnsgnrCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689289
		// Consignor Name: Position
		if (position.getText().equals(txt_CnsgnrNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689290
		// Item Code: Position
		if (position.getText().equals(txt_ItemCdPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689291
		// Item Name: Position
		if (position.getText().equals(txt_ItemNmPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689292
		// Packed Qty per Case: Position
		if (position.getText().equals(txt_CaseEtrPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689293
		// System Stock Qty: Position
		if (position.getText().equals(txt_SysStockQtyPtlPos.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689294
		// Inventory CheckResult Qty (Total Bulk Qty): Position
		if (position.getText().equals(txt_InvntRsltQtyPtlPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689295
		// Inventory Check Location: Position
		if (position.getText().equals(txt_InvntryLctPst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}

		//#CM689296
		// Inventory Check Result Date: Position
		if (position.getText().equals(txt_InvntryRsltDatePst.getText()))
		{
			checkCount++;
			if (checkCount >= 2)
			{
				return false;
			}
		}
		//#CM689297
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
	//#CM689298
	// Event handler methods -----------------------------------------
	//#CM689299
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689300
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689301
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Back_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689302
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
		//#CM689303
		// Shift to the original screen.
		forward(DO_DEFINEREPORT);
	}

	//#CM689304
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689305
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

	//#CM689306
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Valid_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689307
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DigitsUseLength_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689308
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689309
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Position_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689310
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689311
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689312
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689313
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689314
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689315
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689316
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689317
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689318
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689319
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689320
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689321
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689322
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CnsgnrNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689323
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689324
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689325
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689326
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689327
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689328
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689329
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CnsgnrNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689330
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689331
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689332
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemCd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689333
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689334
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689335
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689336
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689337
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689338
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689339
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCdPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689340
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689341
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689342
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ItemNm_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689343
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689344
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689345
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689346
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689347
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689348
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689349
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemNmPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689350
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689351
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689352
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CaseEtr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689353
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689354
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689355
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689356
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenCaseEtr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689357
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689358
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689359
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEtrPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689360
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SystemStockQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689361
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_SysStockQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689362
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_SysStockQtyPtl_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689363
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SysStockQtyPtlLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689364
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SysStockQtyPtlLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689365
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SysStockQtyPtlLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689366
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenSysStockQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689367
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SysStockQtyPtlPos_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689368
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SysStockQtyPtlPos_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689369
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SysStockQtyPtlPos_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689370
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InvntryRsltQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689371
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InvntRsltQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689372
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InvntRsltQtyPtl_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689373
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntRsltQtyPtlLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689374
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntRsltQtyPtlLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689375
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntRsltQtyPtlLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689376
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenInvntRsltQtyPtl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689377
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntRsltQtyPtlPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689378
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntRsltQtyPtlPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689379
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntRsltQtyPtlPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689380
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InventoryLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689381
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InvntryLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689382
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InvntryLct_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689383
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryLctLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689384
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryLctLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689385
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryLctLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689386
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenInvntryLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689387
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryLctPst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689388
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryLctPst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689389
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryLctPst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689390
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InvntryRsltDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689391
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InvntryRsltDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689392
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InvntryRsltDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689393
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryRsltDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689394
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryRsltDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689395
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryRsltDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689396
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenInvntryRsltDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689397
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryRsltDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689398
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryRsltDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689399
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryRsltDatePst_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689400
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689401
	/** 
	 * Clicking on Add button for field items set for reporting data (setting of Inventory Check Result field) invokes this.<BR>
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
		//#CM689402
		// Set the initial value of the focus to Consignor Code Enabled.
		setFocus(chk_CnsgnrCd);

		//#CM689403
		// Check for input.
		//#CM689404
		// Display error if no check is placed in all checkboxes.
		//#CM689405
		// Consignor Code
		if (!chk_CnsgnrCd.getChecked() &&
				//#CM689406
				//	Consignor Name
				!chk_CnsgnrNm.getChecked() &&
				//#CM689407
				//	Item Code
				!chk_ItemCd.getChecked() &&
				//#CM689408
				//	Item Name
				!chk_ItemNm.getChecked() &&
				//#CM689409
				//	Packed Qty per Case
				!chk_CaseEtr.getChecked() &&
				//#CM689410
				//	System Stock Qty (Total Bulk Qty)
				!chk_SysStockQtyPtl.getChecked() &&
				//#CM689411
				//	Inventory CheckResult Qty (Total Bulk Qty)
				!chk_InvntRsltQtyPtl.getChecked() &&
				//#CM689412
				//	Inventory Check Location
				!chk_InvntryLct.getChecked() &&
				//#CM689413
				//	Inventory Check Result Date (Enabled)
				!chk_InvntryRsltDate.getChecked() &&
				//#CM689414
				//	Expiry Date
				!chk_UseByDate.getChecked()  
				)
		{
			//#CM689415
			// 6023174=Please select 1 or more items.
			message.setMsgResourceKey("6023174");
			return;
		}
		
		//#CM689416
		// Check for input.
		//#CM689417
		// Consignor Code
		WmsCheckker.checkDefine(lbl_ConsignorCd, chk_CnsgnrCd, txt_CnsgnrCdLen, lbl_MaxLenCnsgnrCd, txt_CnsgnrCdPst);
		//#CM689418
		// Consignor Name
		WmsCheckker.checkDefine(lbl_ConsignorNm, chk_CnsgnrNm, txt_CnsgnrNmLen, lbl_MaxLenCnsgnrNm, txt_CnsgnrNmPst);
		//#CM689419
		// Item Code
		WmsCheckker.checkDefine(lbl_ItemCd, chk_ItemCd, txt_ItemCdLen, lbl_MaxLenItemCd, txt_ItemCdPst);
		//#CM689420
		// Item Name
		WmsCheckker.checkDefine(lbl_ItemNm, chk_ItemNm, txt_ItemNmLen, lbl_MaxLenItemNm, txt_ItemNmPst);
		//#CM689421
		// Packed Qty per Case: Length
		WmsCheckker.checkDefine(lbl_CaseEntering, chk_CaseEtr, txt_CaseEtrLen, lbl_MaxLenCaseEtr, txt_CaseEtrPst);
		//#CM689422
		// System Stock Qty (Total Bulk Qty)
		WmsCheckker.checkDefine(lbl_SystemStockQty, chk_SysStockQtyPtl, txt_SysStockQtyPtlLen, lbl_MaxLenSysStockQtyPtl, txt_SysStockQtyPtlPos);
		//#CM689423
		// Inventory CheckResult qty
		WmsCheckker.checkDefine(lbl_InvntryRsltQtyPtl, chk_InvntRsltQtyPtl, txt_InvntRsltQtyPtlLen, lbl_MaxLenInvntRsltQtyPtl, txt_InvntRsltQtyPtlPst);
		//#CM689424
		//	Inventory Check Location
		WmsCheckker.checkDefine(lbl_InventoryLct, chk_InvntryLct, txt_InvntryLctLen, lbl_MaxLenInvntryLct, txt_InvntryLctPst);
		//#CM689425
		//	Inventory Check Result Date
		WmsCheckker.checkDefine(lbl_InvntryRsltDate, chk_InvntryRsltDate, txt_InvntryRsltDateLen, lbl_MaxLenInvntryRsltDate, txt_InvntryRsltDatePst);
		//#CM689426
		//	Expiry Date
		WmsCheckker.checkDefine(lbl_UseByDate, chk_UseByDate, txt_UseByDateLen, lbl_MaxLenUseByDate, txt_UseByDatePst);

		//#CM689427
		// Check for duplication.
		boolean positionCheckFlag = true;

		if (positionCheckFlag && chk_CnsgnrCd.getChecked())
		{
			positionCheckFlag = positionCheck(txt_CnsgnrCdPst);
		}
		if (positionCheckFlag && chk_CnsgnrNm.getChecked())
		{
			positionCheckFlag = positionCheck(txt_CnsgnrNmPst);
		}
		if (positionCheckFlag && chk_ItemCd.getChecked())
		{
			positionCheckFlag = positionCheck(txt_ItemCdPst);
		}
		if (positionCheckFlag && chk_ItemNm.getChecked())
		{
			positionCheckFlag = positionCheck(txt_ItemNmPst);
		}
		if (positionCheckFlag && chk_CaseEtr.getChecked())
		{
			positionCheckFlag = positionCheck(txt_CaseEtrPst);
		}
		if (positionCheckFlag && chk_SysStockQtyPtl.getChecked())
		{
			positionCheckFlag = positionCheck(txt_SysStockQtyPtlPos);
		}
		if (positionCheckFlag && chk_InvntRsltQtyPtl.getChecked())
		{
			positionCheckFlag = positionCheck(txt_InvntRsltQtyPtlPst);
		}
		if (positionCheckFlag && chk_InvntryLct.getChecked())
		{
			positionCheckFlag = positionCheck(txt_InvntryLctPst);
		}
		if (positionCheckFlag && chk_InvntryRsltDate.getChecked())
		{
			positionCheckFlag = positionCheck(txt_InvntryRsltDatePst);
		}
		if (positionCheckFlag && chk_UseByDate.getChecked())
		{
			positionCheckFlag = positionCheck(txt_UseByDatePst);
		}
				
		//#CM689428
		// Return error if duplicate "Position" exist.
		if (!positionCheckFlag)
		{
			//#CM689429
			// Display the error message and close it.
			//#CM689430
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

			WmsScheduler schedule = new DefineReportDataSCH();

			//#CM689431
			// Set the parameter.
			param[0].setSelectDefineReportData(DefineDataParameter.SELECTDEFINEREPORTDATA_INVENTORY);
			//#CM689432
			// Consignor Code: Enabled
			param[0].setValid_ConsignorCode(chk_CnsgnrCd.getChecked());
			//#CM689433
			// Consignor Code: Length
			param[0].setFigure_ConsignorCode(txt_CnsgnrCdLen.getText());
			//#CM689434
			// Consignor Code: Max Length
			param[0].setMaxFigure_ConsignorCode(lbl_MaxLenCnsgnrCd.getText());
			//#CM689435
			// Consignor Code: Position
			param[0].setPosition_ConsignorCode(txt_CnsgnrCdPst.getText());
			
			//#CM689436
			// Consignor Name: Enabled
			param[0].setValid_ConsignorName(chk_CnsgnrNm.getChecked());
			//#CM689437
			// Consignor Name: Length
			param[0].setFigure_ConsignorName(txt_CnsgnrNmLen.getText());
			//#CM689438
			// Consignor Name: Max Length
			param[0].setMaxFigure_ConsignorName(lbl_MaxLenCnsgnrNm.getText());
			//#CM689439
			// Consignor Name: Position
			param[0].setPosition_ConsignorName(txt_CnsgnrNmPst.getText());
			
			//#CM689440
			// Item Code: Enabled
			param[0].setValid_ItemCode(chk_ItemCd.getChecked());
			//#CM689441
			// Item Code: Length
			param[0].setFigure_ItemCode(txt_ItemCdLen.getText());
			//#CM689442
			// Item Code: Max Length
			param[0].setMaxFigure_ItemCode(lbl_MaxLenItemCd.getText());
			//#CM689443
			// Item Code: Position
			param[0].setPosition_ItemCode(txt_ItemCdPst.getText());

			//#CM689444
			// Item Name: Enabled
			param[0].setValid_ItemName(chk_ItemNm.getChecked());
			//#CM689445
			// Item Name: Length
			param[0].setFigure_ItemName(txt_ItemNmLen.getText());
			//#CM689446
			// Item Name: Max Length
			param[0].setMaxFigure_ItemName(lbl_MaxLenItemNm.getText());
			//#CM689447
			// Item Name: Position
			param[0].setPosition_ItemName(txt_ItemNmPst.getText());

			//#CM689448
			// Packed Qty per Case: Enabled
			param[0].setValid_EnteringQty(chk_CaseEtr.getChecked());
			//#CM689449
			// Packed Qty per Case: Length
			param[0].setFigure_EnteringQty(txt_CaseEtrLen.getText());
			//#CM689450
			// Packed Qty per Case: Max Length
			param[0].setMaxFigure_EnteringQty(lbl_MaxLenCaseEtr.getText());
			//#CM689451
			// Packed Qty per Case: Position
			param[0].setPosition_EnteringQty(txt_CaseEtrPst.getText());
			
			//#CM689452
			// System Stock Qty (Total Bulk Qty): Enabled
			param[0].setValid_PlanQty(chk_SysStockQtyPtl.getChecked());
			//#CM689453
			// System Stock Qty (Total Bulk Qty): Length
			param[0].setFigure_PlanQty(txt_SysStockQtyPtlLen.getText());
			//#CM689454
			// System Stock Qty (Total Bulk Qty): Max Length
			param[0].setMaxFigure_PlanQty(lbl_MaxLenSysStockQtyPtl.getText());
			//#CM689455
			// System Stock Qty (Total Bulk Qty): Position
			param[0].setPosition_PlanQty(txt_SysStockQtyPtlPos.getText());
						
			//#CM689456
			// Inventory CheckResult Qty (Total Bulk Qty): Enabled
			param[0].setValid_CaseResultQty(chk_InvntRsltQtyPtl.getChecked());
			//#CM689457
			// Inventory CheckResult Qty (Total Bulk Qty): Length
			param[0].setFigure_CaseResultQty(txt_InvntRsltQtyPtlLen.getText());
			//#CM689458
			// Inventory CheckResult Qty (Total Bulk Qty): Max Length
			param[0].setMaxFigure_CaseResultQty(lbl_MaxLenInvntRsltQtyPtl.getText());
			//#CM689459
			// Inventory CheckResult Qty (Total Bulk Qty): Position
			param[0].setPosition_CaseResultQty(txt_InvntRsltQtyPtlPst.getText());
			
			//#CM689460
			// Inventory Check Location: Enabled
			param[0].setValid_CaseLocation(chk_InvntryLct.getChecked());
			//#CM689461
			// Inventory Check Location: Length
			param[0].setFigure_CaseLocation(txt_InvntryLctLen.getText());
			//#CM689462
			// Inventory Check Location: Max Length
			param[0].setMaxFigure_CaseLocation(lbl_MaxLenInvntryLct.getText());
			//#CM689463
			// Inventory Check Location: Position
			param[0].setPosition_CaseLocation(txt_InvntryLctPst.getText());
			
			//#CM689464
			// Inventory Check Result Date: Enabled
			param[0].setValid_WorkDate(chk_InvntryRsltDate.getChecked());
			//#CM689465
			// Inventory Check Result Date: Length
			param[0].setFigure_WorkDate(txt_InvntryRsltDateLen.getText());
			//#CM689466
			// Inventory Check Result Date: Max Length
			param[0].setMaxFigure_WorkDate(lbl_MaxLenInvntryRsltDate.getText());
			//#CM689467
			// Inventory Check Result Date: Position
			param[0].setPosition_WorkDate(txt_InvntryRsltDatePst.getText());

			//#CM689468
			// Expiry Date: Enabled
			param[0].setValid_UseByDate(chk_UseByDate.getChecked());
			//#CM689469
			// Expiry Date: Length
			param[0].setFigure_UseByDate(txt_UseByDateLen.getText());
			//#CM689470
			// Expiry Date: Max Length
			param[0].setMaxFigure_UseByDate(lbl_MaxLenUseByDate.getText());
			//#CM689471
			// Expiry Date: Position
			param[0].setPosition_UseByDate(txt_UseByDatePst.getText());

			//#CM689472
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
				//#CM689473
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
				//#CM689474
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

	//#CM689475
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689476
	/** 
	 * Clicking on Clear button for field items set for reporting data (setting of Inventory Check Result field) invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 * <DIR>
	 *   1.Reset the Input area to its initial display (the status as obtained from EnvironmentInformation). <BR>
	 *   2.Set the cursor to Length of a field item. <BR>
	 * </DIR>

	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		setInistView();
	}
	//#CM689477
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_RptClmSetInvntry_Click(ActionEvent e) throws Exception
	{
	}

	//#CM689478
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689479
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689480
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_UseByDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM689481
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689482
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689483
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDateLen_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM689484
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MaxLenUseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689485
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM689486
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM689487
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDatePst_TabKey(ActionEvent e) throws Exception
	{
	}
}
//#CM689488
//end of class
