//#CM7858
//$Id: StockCorrect2Business.java,v 1.2 2006/10/04 05:05:05 suresh Exp $

//#CM7859
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.stockcorrect;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.utility.DateUtil;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocation.ListStockLocationBusiness;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;
import jp.co.daifuku.wms.stockcontrol.schedule.StockCorrectSCH;

//#CM7860
/**
 * Designer :<BR>
 * Maker : T.Uehata <BR>
 * <BR>
 * This is stock correction, delete (detail info submit/add) class.<BR>
 * Display the input info on the upper area of the Typical info input screen and
 * Dispaly the data to be output to the preset area.<BR>
 * Passes Stock correction parameter to the schedule to be deleted. <BR>
 *  Set the contents input from the screen to the parameter, and the schedule search the data for display based on the parameter. <BR>
 * <BR>
 * Line No. list of the list cell <BR>
 * <DIR>
 * 1.Delete button <BR>
 * 2.Correction button <BR>
 * 3.Item code <BR>
 * 4.Division <BR>
 * 5.Location <BR>
 * 6.Packing qty per Case <BR>
 * 7.Stock Case qty <BR>
 * 8.Case ITF <BR>
 * 9.Storage Date <BR>
 * 10.Expiry Date <BR>
 * 11.Item name <BR>
 * 12.Packing qty per Bundle <BR>
 * 13.Stock Piece qty <BR>
 * 14.Bundle ITF <BR>
 * 15.Storage Time <BR>
 * </DIR> <BR>
 * Execute following processes in this class. <BR>
 * <BR>
 * 1.Initial display process( <CODE>page_Load</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * Display a part of the input info on the upper area of the Typical info input screen. <BR>
 * Set the contents obtained from the ViewState for the parameter and obtain the data needed to output for the preset area from the schedule. <BR>
 * Display the preset area with the data obtained from the schedule to be output to the preset area <BR>
 * If finding no corresponding data, return Parameter array with the number of elements equal to 0 and receive null when condition error or other causes. <BR>
 * Output the message obtained from the schedule to the screen <BR>
 * <BR>
 * [parameter] *Mandatory Input <BR>
 * <BR>
 * <DIR>
 * Worker Code* <BR>
 * Password* <BR>
 * Consignor code* <BR>
 * Start item code <BR>
 * End Start item code <BR>
 * Start location <BR>
 * End location <BR>
 * Case/Piece Division* <BR>
 * Display Sequence* <BR>
 * </DIR>
 * <BR>
 * [Returned data] <BR>
 * <BR>
 * <DIR>
 * Item code <BR>
 * Item name <BR>
 * Division <BR>
 * Location <BR>
 * Packing qty per case <BR>
 * Packing qty per Bundle <BR>
 * Inventory packing qty per case <BR>
 * Inventory packing qty per Piece <BR>
 * Case ITF <BR>
 * Bundle ITF <BR>
 * Storage Date <BR>
 * Storage Time <BR>
 * Expiry Date <BR>
 * Stock ID(Unique Key ) <BR>
 * last update date/time <BR>
 * </DIR> <BR>
 * </DIR> <BR>
 * 2.Process by clicking on the Input button( <CODE>btn_Input_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * Set the and contents entered via the input area for the parameter, and the schedule checks the input condition based on the parameter.<BR>
 * Receive the result from the schedule and receive true when the process normally completed <BR>
 * Receive false when failed to schedule completely due to condition error or otether causes.<BR>
 * Output the message obtained from the schedule to the screen <BR>
 * Aif the result is true <BR>
 * <BR>
 * [parameter] *Mandatory Input <BR>
 * <BR>
 * <DIR>Consignor code* <BR>
 * Consignor name* <BR>
 * Item code* <BR>
 * Item name <BR>
 * Case/Piece Division* <BR>
 * Location*<BR>
 * Packing qty per case <BR>
 * Inventory packing qty per case <BR>
 * Case ITF <BR>
 * Packing qty per Bundle <BR>
 * Inventory packing qty per Piece <BR>
 * Bundle ITF <BR>
 * Storage Date <BR>
 * Storage Time <BR>
 * Expiry Date <BR>
 * last update date/time* <BR>
 * Preset Line No.* <BR>
 * Stock ID(Unique Key )* <BR>
 * </DIR> <BR>
 * </DIR> <BR>
 * <BR>
 * 3.Correction add button press down process( <CODE>btn_ModifySubmit_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * Set the contents input via preset area for the parameter.
 * schedule executes stock data modify processing based on the parameter<BR>
 * Receive theresult from schedule and if the process is completed normally, then<BR>
 * it obtains the data to be output to the preset area again from the schedule and to show the preset area again.<BR>
 * Receive null when failed to schedule completely due to condition error or otether causes. <BR>
 * Output the message obtained from the schedule to the screen <BR>
 * <BR>
 * [parameter] *Mandatory Input <BR>
 * <BR>
 * <DIR>Consignor code* <BR>
 * Consignor name* <BR>
 * Preset.Item code* <BR>
 * Preset.Item name
 * Preset.Case/Piece Division* <BR>
 * Preset.Location* <BR>
 * Preset.Packing qty per case <BR>
 * Preset.Inventory packing qty per case <BR>
 * Preset.Case ITF <BR>
 * Preset.Packing qty per Bundle <BR>
 * Preset.Inventory packing qty per Piece <BR>
 * Preset.Bundle ITF <BR>
 * Preset.Storage Date <BR>
 * Preset.Storage Time <BR>
 * Preset.Expiry Date <BR>
 * Preset.last update date/time* <BR>
 * Preset.Preset Line No.* <BR>
 * Preset.Stock ID(Unique Key )* <BR>
 * </DIR> <BR>
 * [Returned data] <BR>
 * <BR>
 * <DIR>Consignor code <BR>
 * Consignor name <BR>
 * Item code* <BR>
 * Item name* <BR>
 * Case/Piece Division* <BR>
 * Location*<BR>
 * Packing qty per case <BR>
 * Inventory packing qty per case <BR>
 * Case ITF <BR>
 * Packing qty per Bundle <BR>
 * Inventory packing qty per Piece <BR>
 * Bundle ITF <BR>
 * Storage Date <BR>
 * Storage Time <BR>
 * Expiry Date <BR>
 * last update date/time* <BR>
 * Preset Line No.* <BR>
 * Stock ID(Unique Key )* <BR>
 * </DIR> <BR>
 * </DIR> <BR>
 * 4.Delete, All delete button press down process( <CODE>lst_SShpPlnDaMdDlt_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>Set the contents input via preset area for the parameter. The schedule deletes the stock data based on the parameter. <BR>
 * Receive the result from the schedule and receive true when the process normally completed <BR>
 * Receive false when failed to schedule completely due to condition error or otether causes.<BR>
 * Output the message obtained from the schedule to the screen <BR>
 * <BR>
 * [parameter] *Mandatory Input <BR>
 * <BR>
 * <DIR>
 * Preset.Stock ID(Unique Key )* <BR>
 * Preset.last update date/time* <BR>
 * Preset.Preset Line No.* <BR>
 * </DIR>
 * </DIR>
 * <BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/10/01</TD>
 * <TD>T.Uehata</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:05:05 $
 * @author $Author: suresh $
 */
public class StockCorrect2Business extends StockCorrect2 implements WMSConstants
{
	//#CM7861
	// Class fields --------------------------------------------------

	//#CM7862
	// Class variables -----------------------------------------------

	//#CM7863
	// Class method --------------------------------------------------

	//#CM7864
	// Constructors --------------------------------------------------
	//#CM7865
	// Variable that designates a row of list cell
	//#CM7866
	// Hidden parameter(List cell)
	private static final int LSTHDN = 0;
	//#CM7867
	// Delete button(List cell)
	private static final int LSTDELBTN = 1;
	//#CM7868
	// Correction button(List cell)
	private static final int LSTMODBTN = 2;
	//#CM7869
	// Item code(List cell)
	private static final int LSTITEMCD = 3;
	//#CM7870
	// Division(List cell)
	private static final int LSTCPFNAME = 4;
	//#CM7871
	// Location(List cell)
	private static final int LSTLOCATION = 5;
	//#CM7872
	// Packing qty per case(List cell)
	private static final int LSTENTERINGQTY = 6;
	//#CM7873
	// Stock Case Qty(List cell)
	private static final int LSTSTOCKCASEQTY = 7;
	//#CM7874
	// Case ITF(List cell)
	private static final int LSTCASEITF = 8;
	//#CM7875
	// Storage Date(List cell)
	private static final int LSTSTORAGEDATE = 9;
	//#CM7876
	// Expiry Date(List cell)
	private static final int LSTUSEBYDATE = 10;
	//#CM7877
	// Item name(List cell)
	private static final int LSTITEMNAME = 11;
	//#CM7878
	// packed qty per bundle(List cell)
	private static final int LSTBUNDLEENTERINGQTY = 12;
	//#CM7879
	// Stock piece qty(List cell)
	private static final int LSTSTOCKPIECEQTY = 13;
	//#CM7880
	// Bundle ITF(List cell)
	private static final int LSTBUNDLEITF = 14;
	//#CM7881
	// Stock time(List cell)
	private static final int LSTSTORAGETIME = 15;

	//#CM7882
	// Hidden parameter sequence
	//#CM7883
	// Stock ID(Hidden parameter sequence)
	private static final int HDNSTOCKID_IDX = 0;
	//#CM7884
	// last update date/time(Hidden parameter sequence)
	private static final int HDNLASTUPDDATE_IDX = 1;
	//#CM7885
	// Update flag(Hidden parameter sequence)
	private static final int HDNUPDATEFLAG_IDX = 2;
	//#CM7886
	// Item code(Hidden parameter sequence)
	private static final int HDNITEMCODE_IDX = 3;
	//#CM7887
	// Item name(Hidden parameter sequence)
	private static final int HDNITEMNAME_IDX = 4;
	//#CM7888
	// Case/PieceDivision(Hidden parameter sequence)
	private static final int HDNCASEPIECEFLAG_IDX = 5;
	//#CM7889
	// LocationNo(Hidden parameter sequence)
	private static final int HDNLOCATION_IDX = 6;
	//#CM7890
	// Packing qty per case(Hidden parameter sequence)
	private static final int HDNENTERINGQTY_IDX = 7;
	//#CM7891
	// packed qty per bundle(Hidden parameter sequence)
	private static final int HDNBUNDLEENTERINGQTY_IDX = 8;
	//#CM7892
	// Stock Case Qty(Hidden parameter sequence)
	private static final int HDNISTOCCASEQTY_IDX = 9;
	//#CM7893
	// Stock piece qty(Hidden parameter sequence)
	private static final int HDNSTOCKPIECEQTY_IDX = 10;
	//#CM7894
	// Case ITF(Hidden parameter sequence)
	private static final int HDNITF_IDX = 11;
	//#CM7895
	// Bundle ITF(Hidden parameter sequence)
	private static final int HDNBUNDLEITF_IDX = 12;
	//#CM7896
	// Storage Date(Hidden parameter sequence)
	private static final int HDNINSTOCKDATE_IDX = 13;
	//#CM7897
	// Stock time(Hidden parameter sequence)
	private static final int HDNINSTOCKTIME_IDX = 14;
	//#CM7898
	// Expiry Date(Hidden parameter sequence)
	private static final int HDNUSEBYDATE_IDX = 15;

	//#CM7899
	// KEY for viewState ( hidden parameter)
	//#CM7900
	// Preset Line No.
	private static final String LINENO = "LINENO";
	//#CM7901
	// Stock ID
	private static final String STOCKID = "STOCKID";
	//#CM7902
	// last update date/time
	private static final String LASTUPDDATE = "LASTUPDDATE";
	//#CM7903
	// Update flag(Hidden parameter sequence)
	private static final String UPDATEFLAG = "UPDATEFLAG";

	//#CM7904
	// Public methods ------------------------------------------------

	//#CM7905
	/**
	 * Invoke this before invoking each control event. <BR>
	 * <BR>
	 * Summary: Displays a dialog. <BR>
	 * <BR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM7906
			// Obtain parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM7907
			// Store to ViewState
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM7908
			// Set the screen name
			lbl_SettingName.setResourceKey(title);
		}
		//#CM7909
		// Clicking on the Modify/Add button invokes dialog to confirm: " Do you modify and add this? "
		btn_ModifySubmit.setBeforeConfirm("MSG-W0033");

		//#CM7910
		// Clicking on the Delete All button button invokes dialog to confirm: " Do you delete all data? "
		btn_AllDelete.setBeforeConfirm("MSG-W0031");
	}

	//#CM7911
	/**
	 * Initialize the screen <BR>
	 * <BR>
	 * Summary: Shows initial display of the screen. <BR>
	 * <BR>
	 * <DIR>
	 * 1.Start the schedule. <BR>
	 * <DIR>
	 * [parameter] *Mandatory Input <BR>
	 * <BR>
	 * <DIR>
	 * Worker Code* <BR>
	 * Password* <BR>
	 * Consignor code* <BR>
	 * Start item code <BR>
	 * End Start item code <BR>
	 * Start location <BR>
	 * End location <BR>
	 * Case/Piece Division* <BR>
	 * Display Sequence* <BR>
	 * </DIR> <BR>
	 * [Returned data] <BR>
	 * <BR>
	 * <DIR>
	 * Item code <BR>
	 * Item name <BR>
	 * Division <BR>
	 * Location <BR>
	 * Packing qty per case <BR>
	 * Packing qty per Bundle <BR>
	 * Inventory packing qty per case <BR>
	 * Inventory packing qty per Piece <BR>
	 * Case ITF <BR>
	 * Bundle ITF <BR>
	 * Storage Date <BR>
	 * Storage Time <BR>
	 * Expiry Date <BR>
	 * Stock ID(Unique Key ) <BR>
	 * last update date/time <BR>
	 * </DIR> <BR>
	 * </DIR>
	 * 2.Receive Parameter array with the number of elements 0 if no corresponding data found <BR>
	 * <BR>
	 * 3.Output the message obtained from the schedule to the screen <BR>
	 * <BR>
	 * 4.Set the initial/default value:-1 for the preset line No. of ViewState. <BR>
	 * <BR>
	 * 5.Disable input button/Clear button.<BR>
	 * <BR>
	 * 6.Initialize the cursor. <BR>
	 * <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM7912
		// Title
		lbl_SettingName.setText(
			DisplayText.getText(this.getViewState().getString(StockCorrectBusiness.TITLE)));

		Connection conn = null;

		try
		{

			//#CM7913
			// Execute advance copy of detail info correction, delete tab
			tab_Bsc_DtlMdfyDlt.setSelectedIndex(2);

			//#CM7914
			// Set to the schedule parameter
			StockControlParameter param = new StockControlParameter();
			//#CM7915
			// Worker Code
			param.setWorkerCode(this.getViewState().getString(StockCorrectBusiness.WORKERCODE));
			//#CM7916
			// Password
			param.setPassword(this.getViewState().getString(StockCorrectBusiness.PASSWORD));

			//#CM7917
			// Consignor code
			param.setConsignorCode(
				this.getViewState().getString(ListStockConsignorBusiness.CONSIGNORCODE_KEY));
			//#CM7918
			// Start item code
			param.setFromItemCode(
				this.getViewState().getString(ListStockItemBusiness.STARTITEMCODE_KEY));
			//#CM7919
			// End item code
			param.setToItemCode(
				this.getViewState().getString(ListStockItemBusiness.ENDITEMCODE_KEY));
			//#CM7920
			// Start location
			param.setFromLocationNo(
				this.getViewState().getString(ListStockLocationBusiness.STARTLOCATION_KEY));
			//#CM7921
			// End location
			param.setToLocationNo(
				this.getViewState().getString(ListStockLocationBusiness.ENDLOCATION_KEY));
			//#CM7922
			// Case/Piece division
			param.setSelectCasePieceFlag(
				this.getViewState().getString(ListStockLocationBusiness.CASEPIECEFLAG_KEY));
			//#CM7923
			// Display Sequence
			param.setDspOrder(this.getViewState().getString(StockCorrectBusiness.VIEWSQCODE));

			//#CM7924
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new StockCorrectSCH();

			//#CM7925
			// Obtain the data to be displayed in the preset area.
			StockControlParameter[] viewparam =
				(StockControlParameter[]) schedule.query(conn, param);

			//#CM7926
			// Receive the message when error or similar occurs and return to the basic info setting screen.
			if (viewparam == null || (viewparam != null && viewparam.length == 0))
			{
				//#CM7927
				// Set massage to ViewState
				this.getViewState().setString(StockCorrectBusiness.MESSAGE, schedule.getMessage());
				//#CM7928
				// Detail information correction, delete screen-> the basic info setting screen
				forward(StockCorrectBusiness.DO_CORRECT);

				return;
			}

			//#CM7929
			// input area
			//#CM7930
			// Consignor code
			lbl_JavaSetConsignorCode.setText(
				this.getViewState().getString(ListStockConsignorBusiness.CONSIGNORCODE_KEY));
			//#CM7931
			// Consignor name
			lbl_JavaSetConsignorName.setText(viewparam[0].getConsignorName());

			//#CM7932
			// Display the obtained data in the list cell of the preset area.
			setList(viewparam);

			//#CM7933
			// Initilize input area
			inputAreaClear();

			//#CM7934
			// Set the message
			message.setMsgResourceKey(schedule.getMessage());

			//#CM7935
			// When no data at preset line
			if (lst_SStockCorrect.getMaxRows() == 1)
			{
				//#CM7936
				// Disables modify submit/add and all delete buttons.
				//#CM7937
				// Add Correction button
				btn_ModifySubmit.setEnabled(false);
				//#CM7938
				// All delete button
				btn_AllDelete.setEnabled(false);
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
				//#CM7939
				// Close the connection
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

	//#CM7940
	/**
	 * Invoke this method when returning from the popup window. <BR>
	 * Override <CODE>page_DlgBack</CODE> defined on <CODE>Page</CODE>.<BR>
	 * 
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		//#CM7941
		// Obtain parameter from listbox
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM7942
		// Item code
		String _itemcode = param.getParameter(ListStockItemBusiness.ITEMCODE_KEY);
		//#CM7943
		// Item code
		String _itemname = param.getParameter(ListStockItemBusiness.ITEMNAME_KEY);
		//#CM7944
		// Location
		String _location = param.getParameter(ListStockLocationBusiness.LOCATION_KEY);

		//#CM7945
		// Set each parameter for the screen if each obtained parameter is not empty.
		//#CM7946
		// Item code
		if (!StringUtil.isBlank(_itemcode))
		{
			txt_ItemCode.setText(_itemcode);
		}
		//#CM7947
		// Item name
		if (!StringUtil.isBlank(_itemname))
		{
			txt_ItemName.setText(_itemname);
		}
		//#CM7948
		// Location
		if (!StringUtil.isBlank(_location))
		{
			txt_Location.setText(_location);
		}

	}
	//#CM7949
	// Package methods -----------------------------------------------

	//#CM7950
	// Protected methods ---------------------------------------------
	//#CM7951
	/**
	 * Execute input check.
	 * Set the message if any error and return false.
	 * 
	 * @return true:Normal false: Abnormal
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
		if (!checker.checkContainNgText(txt_ItemCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ItemName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_Location))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_CaseItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_BundleItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_UseByDate))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
	
		return true;
		
	}

	//#CM7952
	// Private methods -----------------------------------------------
	//#CM7953
	/**
	 * Make input area initial/default status<BR>
	 * <BR>
	 * Summary:Empty Input area, and Input button-Disable the Clear button. <BR>
	 * <BR>
	 * 
	 * @throws Exception Reports all the exceptions.
	 */
	private void inputAreaClear() throws Exception
	{
		//#CM7954
		// Clear the input field.
		inputDataClear();

		//#CM7955
		// Disable the buttons in the input area.
		//#CM7956
		// Input button
		btn_Input.setEnabled(false);
		//#CM7957
		// Clear button
		btn_Clear.setEnabled(false);

		//#CM7958
		// Reset the Modify status ( preset line No) to the initial value.
		this.viewState.setInt(LINENO, -1);

		//#CM7959
		// Return the color of the selected line to initial/default value
		lst_SStockCorrect.resetHighlight();

		//#CM7960
		// Set the cursor for item code.
		setFocus(txt_ItemCode);
	}

	//#CM7961
	/**
	 * Make input area to initial/default status<BR>
	 * <BR>
	 * Summary: Clears all out. <BR>
	 * <BR>
	 *  
	 * @throws Exception Reports all the exceptions.
	 */
	private void inputDataClear() throws Exception
	{
		//#CM7962
		// Item code
		txt_ItemCode.setText("");
		//#CM7963
		// Item name
		txt_ItemName.setText("");
		//#CM7964
		// Case/Piece division
		rdo_CpfCase.setChecked(true);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);
		//#CM7965
		// Location
		txt_Location.setText("");
		//#CM7966
		// Packing qty per case
		txt_CaseEntering.setText("");
		//#CM7967
		// Stock Case Qty
		txt_StockCaseQty.setText("");
		//#CM7968
		// Case ITF
		txt_CaseItf.setText("");
		//#CM7969
		// packed qty per bundle
		txt_BundleEntering.setText("");
		//#CM7970
		// Stock piece qty
		txt_StockPieceQty.setText("");
		//#CM7971
		// Bundle ITF
		txt_BundleItf.setText("");
		//#CM7972
		// Storage Date
		txt_StorageDate.setText("");
		//#CM7973
		// Stock time
		txt_StorageTime.setText("");
		//#CM7974
		// Expiry Date
		txt_UseByDate.setText("");
	}

	//#CM7975
	/**
	 * This method obtains the Case/Piece division from the Case/Piece division. <BR>
	 * <BR>
	 * Summary: Translates Case/Piece division name into Case/Piece division. <BR>
	 * Return empty character if the argument is null. <BR>
	 * <BR>
	 * [parameter] <BR>
	 * <DIR>Case/Piece division name <BR>
	 * </DIR> <BR>
	 * [Returned data] <BR>
	 * <DIR>Case/Piece division <BR>
	 * </DIR>
	 * 
	 * @param name String Case/Piece division name 
	 * @return String Case/Piece division
	 * @throws Exception Reports all the exceptions.
	 */
	private String getCpfNameToCode(String name) throws Exception
	{
		//#CM7976
		// Return empty character if Case/Piece division = NULL.
		if (name == null)
		{
			return "";
		}

		if (name.equals(DisplayText.getText("RDB-W0008")))
		{
			//#CM7977
			// Case/Piece Division name = When it is Case
			return StockControlParameter.CASEPIECE_FLAG_CASE;
		}
		else if (name.equals(DisplayText.getText("RDB-W0009")))
		{
			//#CM7978
			// Case/Piece Division name = When it is Piece
			return StockControlParameter.CASEPIECE_FLAG_PIECE;
		}
		else if (name.equals(DisplayText.getText("RDB-W0010")))
		{
			//#CM7979
			// Case/Piece Division name = When it is None
			return StockControlParameter.CASEPIECE_FLAG_NOTHING;
		}

		//#CM7980
		// Return empty character if not existing name.
		return "";
	}

	//#CM7981
	/**
	 * This method obtains the Case/Piece division name from the Case/Piece division. <BR>
	 * <BR>
	 * Summary: Translates Case/Piece division into Case/Piece division name. <BR>
	 * If improper Case/Piece division designated <BR>
	 * <BR>
	 * [parameter] <BR>
	 * <DIR>Case/Piece division <BR>
	 * </DIR> <BR>
	 * [Returned data] <BR>
	 * <DIR>Case/Piece division name <BR>
	 * </DIR>
	 * 
	 * @param CasePiceCode Case/Piece division
	 * @return String Case/Piece division name
	 * @throws Exception Reports all the exceptions.
	 */
	private String getCodetoCpfName(String CasePiceCode) throws Exception
	{
		if (CasePiceCode.equals(StockControlParameter.CASEPIECE_FLAG_CASE))
		{
			return DisplayText.getText("RDB-W0008");
		}
		if (CasePiceCode.equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
		{
			return DisplayText.getText("RDB-W0009");
		}
		if (CasePiceCode.equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
		{
			return DisplayText.getText("RDB-W0010");
		}
		//#CM7982
		// Return empty character if no existing code.
		return "";
	}

	//#CM7983
	// Common Method------------------------------------------

	//#CM7984
	/**
	 * This method sets the preset area data in the Parameter class. <BR>
	 * <BR>
	 * Summary: Sets the preset area data in the Parameter class. <BR>
	 * <BR>
	 * 1.If it is new input <BR>
	 * 2.If the input data is in process of modifying <BR>
	 * 3.If Correction Add button is pressed down, sets all the preset data in the Parameter class (Preset line No. to be modified=-1).<BR>
	 * <DIR>
	 * [parameter] *Mandatory Input <BR>
	 * <DIR>
	 * The preset line No. to be modified (Set "-1" if no target to be modifed).<BR>
	 * </DIR>
	 * [Returned data] <BR>
	 * <DIR>
	 * Array of <CODE>StockControlParameter</CODE> class instance with the contents in the preset area. <BR>
	 * </DIR>
	 * </DIR>
	 * 
	 * @param lineno Line No. of the list cell
	 * @return StockControlParameter[] Parameter object array of <CODE>StockControlParameter</CODE> that has the contents of the Preset area
	 * @throws Exception Reports all the exceptions.
	 */
	private StockControlParameter[] setListParam(int lineno) throws Exception
	{
		Vector vecParam = new Vector();
		
		//#CM7985
		// Terminal No.
		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
		String terminalNumber = userHandler.getTerminalNo();

		for (int i = 1; i < lst_SStockCorrect.getMaxRows(); i++)
		{
			//#CM7986
			// Not process for modify target line
			if (i == lineno)
			{
				continue;
			}

			//#CM7987
			// Designate the line to be compiled.
			lst_SStockCorrect.setCurrentRow(i);

			//#CM7988
			// Not set for not modified data
			if (lineno < 0
				&& CollectionUtils.getString(
					HDNUPDATEFLAG_IDX,
					lst_SStockCorrect.getValue(LSTHDN)).equals(
					StockControlParameter.UPDATEFLAG_NO))
			{
				continue;
			}

			//#CM7989
			// Set to the schedule parameter
			StockControlParameter param = new StockControlParameter();

			//#CM7990
			// Contents of Preset area List cell
			//#CM7991
			// Preset Line No.
			param.setRowNo(lst_SStockCorrect.getCurrentRow());
			//#CM7992
			// Item code
			param.setItemCode(lst_SStockCorrect.getValue(LSTITEMCD));
			//#CM7993
			// Division
			param.setCasePieceFlag(getCpfNameToCode(lst_SStockCorrect.getValue(LSTCPFNAME)));
			//#CM7994
			// Location
			param.setLocationNo(lst_SStockCorrect.getValue(LSTLOCATION));
			//#CM7995
			// Packing qty per case
			param.setEnteringQty(Formatter.getInt(lst_SStockCorrect.getValue(LSTENTERINGQTY)));
			//#CM7996
			// Stock Case Qty
			param.setStockCaseQty(Formatter.getInt(lst_SStockCorrect.getValue(LSTSTOCKCASEQTY)));
			//#CM7997
			// Case ITF
			param.setITF(lst_SStockCorrect.getValue(LSTCASEITF));
			//#CM7998
			// Storage Date and storage Time
			param.setStorageDate(
				WmsFormatter.getTimeStampDate(
					lst_SStockCorrect.getValue(LSTSTORAGEDATE),
					lst_SStockCorrect.getValue(LSTSTORAGETIME),
					this.httpRequest.getLocale()));
			//#CM7999
			// Expiry Date
			param.setUseByDate(lst_SStockCorrect.getValue(LSTUSEBYDATE));
			//#CM8000
			// Item name
			param.setItemName(lst_SStockCorrect.getValue(LSTITEMNAME));
			//#CM8001
			// packed qty per bundle
			param.setBundleEnteringQty(
				Formatter.getInt(lst_SStockCorrect.getValue(LSTBUNDLEENTERINGQTY)));
			//#CM8002
			// Stock piece qty
			param.setStockPieceQty(Formatter.getInt(lst_SStockCorrect.getValue(LSTSTOCKPIECEQTY)));
			//#CM8003
			// Bundle ITF
			param.setBundleITF(lst_SStockCorrect.getValue(LSTBUNDLEITF));

			//#CM8004
			// hidden item (count)
			//#CM8005
			// Stock ID(Unique Key )
			param.setStockId(
				CollectionUtils.getString(HDNSTOCKID_IDX, lst_SStockCorrect.getValue(LSTHDN)));
			//#CM8006
			// last update date/time
			param.setLastUpdateDate(
				WmsFormatter.getTimeStampDate(
					CollectionUtils.getString(
						HDNLASTUPDDATE_IDX,
						lst_SStockCorrect.getValue(LSTHDN))));

			//#CM8007
			// Set item (count) except preset area
			//#CM8008
			// Worker Code
			param.setWorkerCode(this.viewState.getString(StockCorrectBusiness.WORKERCODE));
			//#CM8009
			// Password
			param.setPassword(this.viewState.getString(StockCorrectBusiness.PASSWORD));
			//#CM8010
			// Consignor code
			param.setConsignorCode(lbl_JavaSetConsignorCode.getText());
			//#CM8011
			// Consignor name
			param.setConsignorName(lbl_JavaSetConsignorName.getText());
			//#CM8012
			// Terminal No.
			param.setTerminalNumber(terminalNumber);

			//#CM8013
			// The following parameters are mandatory to display again.
			//#CM8014
			// Start item code
			param.setFromItemCode(
				this.getViewState().getString(ListStockItemBusiness.STARTITEMCODE_KEY));
			//#CM8015
			// End item code
			param.setToItemCode(
				this.getViewState().getString(ListStockItemBusiness.ENDITEMCODE_KEY));
			//#CM8016
			// Start location
			param.setFromLocationNo(
				this.getViewState().getString(ListStockLocationBusiness.STARTLOCATION_KEY));
			//#CM8017
			// End location
			param.setToLocationNo(
				this.getViewState().getString(ListStockLocationBusiness.ENDLOCATION_KEY));
			//#CM8018
			// Case/Piece division
			param.setSelectCasePieceFlag(
				this.getViewState().getString(ListStockLocationBusiness.CASEPIECEFLAG_KEY));
			//#CM8019
			// Display Sequence
			param.setDspOrder(this.getViewState().getString(StockCorrectBusiness.VIEWSQCODE));

			//#CM8020
			// Add 1 line in one batch
			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			//#CM8021
			// Set the value if any preset data to be set.
			StockControlParameter[] listparam = new StockControlParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			//#CM8022
			// Set null if no preset data to be set.
			return null;
		}
	}

	//#CM8023
	/**
	 * This method sets the Parameter value in the preset area. <BR>
	 * <BR>
	 * Summary: Sets the Parameter value for preset area. <BR>
	 * <DIR>
	 * hidden item (count) 
	 * <DIR>
	 * 0.Stock ID(Unique Key ) <BR>
	 * 1.last update date/time <BR>
	 * </DIR>
	 * </DIR> <BR>
	 * <DIR>
	 * [parameter] *Mandatory Input <BR>
	 * <DIR>
	 * Array of <CODE>StockControlParameter</CODE> class instance with the contents in the preset area. <BR>
	 * </DIR>
	 * </DIR>
	 * 
	 * @param param StockControlParameter[] Preset area info.
	 * @throws Exception Reports all the exceptions.
	 */
	private void setList(StockControlParameter[] param) throws Exception
	{
		//#CM8024
		// Obtain the locale.
		Locale locale = this.getHttpRequest().getLocale();

		//#CM8025
		// ToolTipSubject name
		String ttparam01 = DisplayText.getText("LBL-W0103");

		for (int i = 0; i < param.length; i++)
		{
			//#CM8026
			// Add line
			lst_SStockCorrect.addRow();

			//#CM8027
			// Designate the line to be compiled.
			lst_SStockCorrect.setCurrentRow(i + 1);

			//#CM8028
			// Hidden parameter
			Vector hdnpara = new Vector();
			//#CM8029
			// Hidden parameter.Stock ID(Unique Key )
			hdnpara.add(param[i].getStockId());
			//#CM8030
			// Hidden parameter.last update date/time
			hdnpara.add(WmsFormatter.getTimeStampString(param[i].getLastUpdateDate()));
			//#CM8031
			// Hidden parameter.Update flag
			hdnpara.add(StockControlParameter.UPDATEFLAG_NO);
			//#CM8032
			// Hidden parameter.Item code
			hdnpara.add(param[i].getItemCode());
			//#CM8033
			// Hidden parameter.Item name
			hdnpara.add(param[i].getItemName());
			//#CM8034
			// Hidden parameter.Case/PieceDivision
			hdnpara.add(param[i].getCasePieceFlag());
			//#CM8035
			// Hidden parameter.Location No
			hdnpara.add(param[i].getLocationNo());
			//#CM8036
			// Hidden parameter.Packing qty per case
			hdnpara.add(Formatter.getNumFormat(param[i].getEnteringQty()));
			//#CM8037
			// Hidden parameter.packed qty per bundle
			hdnpara.add(Formatter.getNumFormat(param[i].getBundleEnteringQty()));
			//#CM8038
			// Hidden parameter.Stock Case Qty
			hdnpara.add(Formatter.getNumFormat(param[i].getStockCaseQty()));
			//#CM8039
			// Hidden parameter.Stock piece qty
			hdnpara.add(Formatter.getNumFormat(param[i].getStockPieceQty()));
			//#CM8040
			// Hidden parameter.Case ITF
			hdnpara.add(param[i].getITF());
			//#CM8041
			// Hidden parameter.Bundle ITF
			hdnpara.add(param[i].getBundleITF());
			//#CM8042
			// Hidden parameter.Storage Date
			hdnpara.add(
				WmsFormatter.toDispDate(
					WmsFormatter.toParamDate(param[i].getStorageDate()),
					locale));
			//#CM8043
			// Hidden parameter.Storage Time
			hdnpara.add(WmsFormatter.getTimeFormat(param[i].getStorageDate(), ""));
			//#CM8044
			// Hidden parameter.Expiry Date
			hdnpara.add(param[i].getUseByDate());

			//#CM8045
			// Store hidden parameter to the list cell
			lst_SStockCorrect.setValue(LSTHDN, CollectionUtils.getConnectedString(hdnpara));
			//#CM8046
			// Item code
			lst_SStockCorrect.setValue(LSTITEMCD, param[i].getItemCode());
			//#CM8047
			// Division
			lst_SStockCorrect.setValue(LSTCPFNAME, param[i].getCasePieceFlagName());
			//#CM8048
			// Location
			lst_SStockCorrect.setValue(LSTLOCATION, param[i].getLocationNo());
			//#CM8049
			// Packing qty per case
			lst_SStockCorrect.setValue(
				LSTENTERINGQTY,
				Formatter.getNumFormat(param[i].getEnteringQty()));
			//#CM8050
			// Stock Case Qty
			lst_SStockCorrect.setValue(
				LSTSTOCKCASEQTY,
				Formatter.getNumFormat(param[i].getStockCaseQty()));
			//#CM8051
			// Case ITF
			lst_SStockCorrect.setValue(LSTCASEITF, param[i].getITF());
			//#CM8052
			// Storage Date
			lst_SStockCorrect.setValue(
				LSTSTORAGEDATE,
				WmsFormatter.toDispDate(
					WmsFormatter.toParamDate(param[i].getStorageDate()),
					locale));
			//#CM8053
			// Expiry Date
			lst_SStockCorrect.setValue(LSTUSEBYDATE, param[i].getUseByDate());
			//#CM8054
			// Item name
			lst_SStockCorrect.setValue(LSTITEMNAME, param[i].getItemName());
			//#CM8055
			// packed qty per bundle
			lst_SStockCorrect.setValue(
				LSTBUNDLEENTERINGQTY,
				Formatter.getNumFormat(param[i].getBundleEnteringQty()));
			//#CM8056
			// Stock piece qty
			lst_SStockCorrect.setValue(
				LSTSTOCKPIECEQTY,
				Formatter.getNumFormat(param[i].getStockPieceQty()));
			//#CM8057
			// Bundle ITF
			lst_SStockCorrect.setValue(LSTBUNDLEITF, param[i].getBundleITF());
			//#CM8058
			// Stock time
			lst_SStockCorrect.setValue(
				LSTSTORAGETIME,
				WmsFormatter.getTimeFormat(param[i].getStorageDate(), ""));

			//#CM8059
			// Compile the data for ToolTip
			ToolTipHelper toolTip = new ToolTipHelper();
			//#CM8060
			// Item name
			toolTip.add(ttparam01, param[i].getItemName());
			//#CM8061
			// Expiry Date
			toolTip.add(DisplayText.getText("LBL-W0270"), param[i].getUseByDate());
			
			//#CM8062
			// Set the ToolTip for the target line.
			lst_SStockCorrect.setToolTip(i + 1, toolTip.getText());
		}
	}

	//#CM8063
	/**
	 * This method sets the modified data of the input area for the preset are. <BR>
	 * <BR>
	 * Summary: Sets the data to be modified in the input area for the preset area. <BR>
	 * <DIR>
	 * hidden item (count) <BR>
	 * <DIR>
	 * 0.Stock ID(Unique Key ) <BR>
	 * 1.last update date/time <BR>
	 * </DIR>
	 * </DIR>
	 * 
	 * @param storageTime Storage Time
	 * @throws Exception Reports all the exceptions.
	 */
	private void setListRow(String storageTime) throws Exception
	{
		boolean updateflag = updateCheck();

		//#CM8064
		// Compile the data for ToolTip
		ToolTipHelper toolTip = new ToolTipHelper();
		//#CM8065
		// Item name
		toolTip.add(DisplayText.getText("LBL-W0103"), txt_ItemName.getText());
		//#CM8066
		// Set ToolTip
		lst_SStockCorrect.setToolTip(this.getViewState().getInt(LINENO), toolTip.getText());

		//#CM8067
		// Hidden parameter
		ArrayList hdnpara = (ArrayList)CollectionUtils.getList(lst_SStockCorrect.getValue(LSTHDN));

		//#CM8068
		// Hidden parameter.Stock ID(Unique Key)
		hdnpara.set(HDNSTOCKID_IDX,this.viewState.getString(STOCKID));
		//#CM8069
		// Hidden parameter.last update date/time
		hdnpara.set(HDNLASTUPDDATE_IDX,this.viewState.getString(LASTUPDDATE));
		//#CM8070
		// Correction flag
		if (updateflag)
		{
			hdnpara.set(HDNUPDATEFLAG_IDX,StockControlParameter.UPDATEFLAG_YES);
		}
		else
		{
			hdnpara.set(HDNUPDATEFLAG_IDX,StockControlParameter.UPDATEFLAG_NO);
		}
		//#CM8071
		// Store hidden parameter to list cell
		lst_SStockCorrect.setValue(LSTHDN, CollectionUtils.getConnectedString(hdnpara));

		//#CM8072
		// Item code
		lst_SStockCorrect.setValue(LSTITEMCD, txt_ItemCode.getText());
		//#CM8073
		// Item name
		lst_SStockCorrect.setValue(LSTITEMNAME, txt_ItemName.getText());
		//#CM8074
		// Case/Piece division
		if (rdo_CpfCase.getChecked())
		{
			lst_SStockCorrect.setValue(LSTCPFNAME, getCodetoCpfName(StockControlParameter.CASEPIECE_FLAG_CASE));
		}
		else if (rdo_CpfPiece.getChecked())
		{
			lst_SStockCorrect.setValue(LSTCPFNAME, getCodetoCpfName(StockControlParameter.CASEPIECE_FLAG_PIECE));
		}
		else if (rdo_CpfAppointOff.getChecked())
		{
			lst_SStockCorrect.setValue(LSTCPFNAME, getCodetoCpfName(StockControlParameter.CASEPIECE_FLAG_NOTHING));
		}

		//#CM8075
		// Location
		lst_SStockCorrect.setValue(LSTLOCATION, txt_Location.getText());
		//#CM8076
		// Packing qty per case
		lst_SStockCorrect.setValue(LSTENTERINGQTY, txt_CaseEntering.getText());
		//#CM8077
		// Stock Case Qty
		lst_SStockCorrect.setValue(LSTSTOCKCASEQTY, txt_StockCaseQty.getText());
		//#CM8078
		// Case ITF
		lst_SStockCorrect.setValue(LSTCASEITF, txt_CaseItf.getText());
		//#CM8079
		// packed qty per bundle
		lst_SStockCorrect.setValue(LSTBUNDLEENTERINGQTY, txt_BundleEntering.getText());
		//#CM8080
		// Stock piece qty
		lst_SStockCorrect.setValue(LSTSTOCKPIECEQTY, txt_StockPieceQty.getText());
		//#CM8081
		// Bundle ITF
		lst_SStockCorrect.setValue(LSTBUNDLEITF, txt_BundleItf.getText());
		//#CM8082
		// Storage Date
		lst_SStockCorrect.setValue(LSTSTORAGEDATE, txt_StorageDate.getText());
		//#CM8083
		// Stock time
		if (!StringUtil.isBlank(txt_StorageTime.getTime()))
		{
			lst_SStockCorrect.setValue(LSTSTORAGETIME, txt_StorageTime.getText());
		}
		else
		{
			lst_SStockCorrect.setValue(LSTSTORAGETIME, storageTime);
		}
		//#CM8084
		// Expiry Date
		lst_SStockCorrect.setValue(LSTUSEBYDATE, txt_UseByDate.getText());
	}

	//#CM8085
	/**
	 * This method determines the changed area in the input area and the input area.<BR>
	 * <BR>
	 * Summary: Compares the input area with the preset area<BR>
	 * 
	 * @return true if there is boolean change otherwise false
	 * @throws Exception Reports all the exceptions.
	 */
	private boolean updateCheck() throws Exception
	{
		//#CM8086
		// Item code
		if (!txt_ItemCode.getText() 
			.equals(CollectionUtils.getString(HDNITEMCODE_IDX, lst_SStockCorrect.getValue(LSTHDN))))
		{
			return true;
		}
		//#CM8087
		// Item name
		if (!txt_ItemName
			.getText()
			.equals(CollectionUtils.getString(HDNITEMNAME_IDX, lst_SStockCorrect.getValue(LSTHDN))))
		{
			return true;
		}
		//#CM8088
		// Case/Piece division
		if (rdo_CpfCase.getChecked())
		{
			if (!StockControlParameter.CASEPIECE_FLAG_CASE
				.equals(
					CollectionUtils.getString(
						HDNCASEPIECEFLAG_IDX,
						lst_SStockCorrect.getValue(LSTHDN))))
			{
				return true;
			}
		}
		else if (rdo_CpfPiece.getChecked())
		{
			if (!StockControlParameter.CASEPIECE_FLAG_PIECE
				.equals(
					CollectionUtils.getString(
						HDNCASEPIECEFLAG_IDX,
						lst_SStockCorrect.getValue(LSTHDN))))
			{
				return true;
			}
		}
		else if (rdo_CpfAppointOff.getChecked())
		{
			if (!StockControlParameter.CASEPIECE_FLAG_NOTHING
				.equals(
					CollectionUtils.getString(
						HDNCASEPIECEFLAG_IDX,
						lst_SStockCorrect.getValue(LSTHDN))))
			{
				return true;
			}
		}
		//#CM8089
		// Location
		if (!txt_Location
			.getText()
			.equals(CollectionUtils.getString(HDNLOCATION_IDX, lst_SStockCorrect.getValue(LSTHDN))))
		{
			return true;
		}
		//#CM8090
		// Packing qty per case
		if (!txt_CaseEntering
			.getText()
			.equals(
				CollectionUtils.getString(HDNENTERINGQTY_IDX, lst_SStockCorrect.getValue(LSTHDN))))
		{
			return true;
		}
		//#CM8091
		// packed qty per bundle
		if (!txt_BundleEntering
			.getText()
			.equals(
				CollectionUtils.getString(
					HDNBUNDLEENTERINGQTY_IDX,
					lst_SStockCorrect.getValue(LSTHDN))))
		{
			return true;
		}
		//#CM8092
		// Stock Case Qty
		if (!txt_StockCaseQty
			.getText()
			.equals(
				CollectionUtils.getString(
					HDNISTOCCASEQTY_IDX,
					lst_SStockCorrect.getValue(LSTHDN))))
		{
			return true;
		}
		//#CM8093
		// Stock piece qty
		if (!txt_StockPieceQty
			.getText()
			.equals(
				CollectionUtils.getString(
					HDNSTOCKPIECEQTY_IDX,
					lst_SStockCorrect.getValue(LSTHDN))))
		{
			return true;
		}
		//#CM8094
		// Case ITF
		if (!txt_CaseItf
			.getText()
			.equals(CollectionUtils.getString(HDNITF_IDX, lst_SStockCorrect.getValue(LSTHDN))))
		{
			return true;
		}
		//#CM8095
		// Bundle ITF
		if (!txt_BundleItf
			.getText()
			.equals(
				CollectionUtils.getString(HDNBUNDLEITF_IDX, lst_SStockCorrect.getValue(LSTHDN))))
		{
			return true;
		}
		//#CM8096
		// Expiry Date
		if (!txt_UseByDate
			.getText()
			.equals(
				CollectionUtils.getString(HDNUSEBYDATE_IDX, lst_SStockCorrect.getValue(LSTHDN))))
		{
			return true;
		}
		//#CM8097
		// Storage Date
		if (!txt_StorageDate
			.getText()
			.equals(
				CollectionUtils.getString(HDNINSTOCKDATE_IDX, lst_SStockCorrect.getValue(LSTHDN))))
		{
			return true;
		}
		//#CM8098
		// Stock time
		if (!txt_StorageTime
			.getText()
			.equals(
				CollectionUtils.getString(HDNINSTOCKTIME_IDX, lst_SStockCorrect.getValue(LSTHDN))))
		{
			return true;
		}

		return false;
	}
	//#CM8099
	// Event handler methods -----------------------------------------
	//#CM8100
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8101
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8102
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Back_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8103
	/**
	 * Clicking on the Return button invokes this.<BR>
	 * <BR>
	 * Summary:The screen will shift to the stock correction,Delete (basic information setting) screen.<BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		//#CM8104
		// Detail information correction, delete screen-> the basic info setting screen
		forward(StockCorrectBusiness.DO_CORRECT);
	}

	//#CM8105
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8106
	/**
	 * Clicking on the Clicking the Menu button invokes this.<BR>
	 * <BR>
	 * Summary: Move to menu screen.<BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM8107
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8108
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_JavaSetConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8109
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_JavaSetConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8110
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_Item_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8111
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8112
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8113
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8114
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8115
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8116
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8117
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8118
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8119
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8120
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_Location_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8121
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Location_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8122
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Location_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8123
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Location_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8124
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Location_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8125
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PsearchLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8126
	/**
	 * Clicking on the Location button invokes this. <BR>
	 * Summary: This Method set search conditions to the parameter, andDisplay the location list search listbox using the search conditions. <BR>
	 * <BR>
	 * [parameter] <BR>
	 * <DIR>
	 * Consignor code <BR>
	 * Location <BR>
	 * </DIR></BR>
	 * 
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PsearchLocation_Click(ActionEvent e) throws Exception
	{
		//#CM8127
		// Set the search condition for the Search Location List screen.
		ForwardParameters param = new ForwardParameters();

		//#CM8128
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			lbl_JavaSetConsignorCode.getText());
		//#CM8129
		// Location
		param.setParameter(ListStockLocationBusiness.LOCATION_KEY, txt_Location.getText());

		//#CM8130
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM8131
		// In process screen->Result screen 
		redirect(
			StockCorrectBusiness.DO_SRCH_LOCATION,
			param,
			StockCorrectBusiness.DO_SRCH_PROCESS);
	}

	//#CM8132
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8133
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8134
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_CaseEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8135
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_CaseEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8136
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_StockCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8137
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void ID1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8138
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void ID1_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8139
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void ID1_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8140
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8141
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8142
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_CaseItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8143
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_CaseItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8144
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_CaseItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8145
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8146
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8147
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_BundleEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8148
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_BundleEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8149
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_StockPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8150
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StockPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8151
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StockPieceQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8152
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StockPieceQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8153
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8154
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8155
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_BundleItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8156
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_BundleItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8157
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_BundleItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8158
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_StorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8159
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8160
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StorageDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8161
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StorageDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8162
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_StrageTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8163
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StorageTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8164
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StorageTime_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8165
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StorageTime_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8166
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8167
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8168
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_UseByDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8169
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_UseByDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8170
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_UseByDate_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8171
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Input_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8172
	/**
	 * Clicking on the Input button invokes this. <BR>
	 * <BR>
	 * Summary:Execute Stock info search by the input item of the input area as its condition and disply it in preset.<BR>
	 * <BR>
	 * <DIR>
	 * 1.Set cursor to item code.<BR>
	 * 2.Execute checking of input area input field. (mandatory input, number of characters and character attribution)<BR>
	 * 3.Start schedule <BR>
	 * <DIR>
	 * [parameter] *Mandatory Input <BR>
	 * <DIR>
	 * Consignor code* <BR>
	 * Consignor name* <BR>
	 * Item code* <BR>
	 * Item name* <BR>
	 * Case/Piece Division* <BR>
	 * Location*<BR>
	 * Packing qty per case <BR>
	 * Inventory packing qty per case <BR>
	 * Case ITF <BR>
	 * Packing qty per Bundle <BR>
	 * Inventory packing qty per Piece <BR>
	 * Bundle ITF <BR>
	 * Storage Date <BR>
	 * Storage Time <BR>
	 * Expiry Date <BR>
	 * last update date/time* <BR>
	 * Preset Line No.* <BR>
	 * Stock ID(Unique Key )* <BR>
	 * </DIR>
	 * </DIR> <BR>
	 * 4.If the schedule result is true, Update correction target data of the preset in the input area info.<BR>
	 * 5.If the schedule result is true, retun the color of the correction target line to the original color.<BR>
	 * 6.If the schedule result is true, then set the default (initial/default value:-1) for the preset line No. of ViewState. <BR>
	 * 7.If the schedule result is true, revoke input, clear button.<BR>
	 * 8.If the schedule result is true, then clear the contents of the input area.<BR>
	 * 9.Output the message obtained from the schedule to the screen. <BR>
	 * <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		//#CM8173
		// Set cursor to Item code.
		setFocus(txt_ItemCode);

		//#CM8174
		// Check for mandatory input.
		//#CM8175
		// Item code
		txt_ItemCode.validate();
		//#CM8176
		// Location
		txt_Location.validate();
		//#CM8177
		// Storage Date
		txt_StorageDate.validate();

		//#CM8178
		// Execute pattern matching.
		//#CM8179
		// Item name
		txt_ItemName.validate(false);
		//#CM8180
		// Case ITF
		txt_CaseItf.validate(false);
		//#CM8181
		// packed qty per bundle
		txt_BundleEntering.validate(false);
		//#CM8182
		// Bundle ITF
		txt_BundleItf.validate(false);
		//#CM8183
		// Stock time
		txt_StorageTime.validate(false);
		//#CM8184
		// Expiry Date
		txt_UseByDate.validate(false);

		//#CM8185
		// Input characters check for eWareNavi
		if (!checkContainNgText())
		{
			return;
		}

		Connection conn = null;

		try
		{
			//#CM8186
			// Set to the schedule parameter
			//#CM8187
			// Input area
			StockControlParameter param = new StockControlParameter();

			//#CM8188
			// Preset Line No.
			param.setRowNo(this.getViewState().getInt(LINENO));
			//#CM8189
			// Stock ID(Unique Key )
			param.setStockId(this.viewState.getString(STOCKID));
			//#CM8190
			// last update date/time
			param.setLastUpdateDate(
				WmsFormatter.getTimeStampDate(this.getViewState().getString(LASTUPDDATE)));
			//#CM8191
			// Consignor code
			param.setConsignorCode(lbl_JavaSetConsignorCode.getText());
			//#CM8192
			// Consignor name
			param.setConsignorName(lbl_JavaSetConsignorName.getText());
			//#CM8193
			// Item code
			param.setItemCode(txt_ItemCode.getText());
			//#CM8194
			// Division
			if (rdo_CpfCase.getChecked())
			{
				param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_CASE);
				param.setCasePieceFlagName(getCpfNameToCode(StockControlParameter.CASEPIECE_FLAG_CASE));
			}
			else if (rdo_CpfPiece.getChecked())
			{
				param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_PIECE);
				param.setCasePieceFlagName(getCpfNameToCode(StockControlParameter.CASEPIECE_FLAG_PIECE));
			}
			else if (rdo_CpfAppointOff.getChecked())
			{
				param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_NOTHING);
				param.setCasePieceFlagName(getCpfNameToCode(StockControlParameter.CASEPIECE_FLAG_NOTHING));
			}
			//#CM8195
			// Location
			param.setLocationNo(txt_Location.getText());
			//#CM8196
			// Packing qty per case
			param.setEnteringQty(Formatter.getInt(txt_CaseEntering.getText()));
			//#CM8197
			// Stock Case Qty
			param.setStockCaseQty(Formatter.getInt(txt_StockCaseQty.getText()));
			//#CM8198
			// Case ITF
			param.setITF(txt_CaseItf.getText());
			//#CM8199
			// Storage Date+Storage Time
			//#CM8200
			//Aggregate Storage Date and storage Time and set it to the parameter.
			DateUtil dateutil = new DateUtil();
			Date storageDate = dateutil.getDate(txt_StorageDate.getDate(), txt_StorageTime.getTime());
			if (storageDate == null) storageDate = new Date();
			param.setStorageDate(storageDate);
			//#CM8201
			// Expiry Date
			param.setUseByDate(txt_UseByDate.getText());
			//#CM8202
			// Item name
			param.setItemName(txt_ItemName.getText());
			//#CM8203
			// packed qty per bundle
			param.setBundleEnteringQty(Formatter.getInt(txt_BundleEntering.getText()));
			//#CM8204
			// Stock piece qty
			param.setStockPieceQty(Formatter.getInt(txt_StockPieceQty.getText()));
			//#CM8205
			// Bundle ITF
			param.setBundleITF(txt_BundleItf.getText());
			//#CM8206
			// Stock time
			//#CM8207
			//param.set(txt_.getText();

			//#CM8208
			// Set to the schedule parameter
			//#CM8209
			// Preset area
			StockControlParameter[] listparam = null;

			//#CM8210
			// Set null if no data in the preset area.
			if (lst_SStockCorrect.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				//#CM8211
				// Set the value
				listparam = setListParam(this.getViewState().getInt(LINENO));
			}

			//#CM8212
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new StockCorrectSCH();

			//#CM8213
			// Execute input check and existence check at schedule class.
			if (schedule.check(conn, param, listparam))
			{
				//#CM8214
				// Update the data at correction target line.
				lst_SStockCorrect.setCurrentRow(this.getViewState().getInt(LINENO));
				//#CM8215
				// Set Input area value to preset.
				setListRow(WmsFormatter.getTimeFormat(storageDate, ""));

				//#CM8216
				// Return the status of preset line selection to default(-1).
				this.getViewState().setInt(LINENO, -1);

				//#CM8217
				// Initialize input area.
				inputAreaClear();

				//#CM8218
				// Set the message
				message.setMsgResourceKey(schedule.getMessage());
			}

			if (schedule.getMessage() != null)
			{
				//#CM8219
				// Set the message
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
				//#CM8220
				// Close the connection
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

	//#CM8221
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8222
	/**
	 * Clicking on the Clear button invokes this. <BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 * <DIR>
	 * 1.Return the item (count) in the input area to the initial value. <BR>
	 * <DIR>
	 * -Refer to <CODE>inputAreaClear()</CODE>Method in regard with the initial value.<BR>
	 * </DIR> 
	 * 2.Set cursor to item code. <BR>
	 * 3.Maintain the contents of preset area. <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM8223
		// Empty entered data
		inputDataClear();
		//#CM8224
		// Set focus to the Item code
		setFocus(txt_ItemCode);
	}

	//#CM8225
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_ModifySubmit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8226
	/**
	 * Clicking Correction add button invokes this.<BR>
	 * <BR>
	 * Summary:Execute correct addition of inventory data by the information of the Preset area.<BR>
	 * <BR>
	 * <DIR>
	 * 1.Set cursor to item code. <BR>
	 * 2.Display the dialog box to confirm if correct addition is made or not. <BR>
	 * <DIR>
	 * "Do you make correct addition?"  <BR>
	 * [Check dialog cancel] <BR>
	 * <DIR>
	 * Disable 
	 * </DIR>
	 * [Check dialog OK] <BR>
	 * <DIR>
	 * 1.Start the schedule. <BR>
	 * <DIR>
	 * [parameter] *Mandatory Input <BR>
	 * <BR>
	 * <DIR>
	 * Consignor code* <BR>
	 * Consignor name* <BR>
	 * Preset.Item code* <BR>
	 * Preset.Case/Piece Division* <BR>
	 * Preset.Location* <BR>
	 * Preset.Packing qty per case <BR>
	 * Preset.Inventory packing qty per case <BR>
	 * Preset.Case ITF <BR>
	 * Preset.Packing qty per Bundle <BR>
	 * Preset.Inventory packing qty per Piece <BR>
	 * Preset.Bundle ITF <BR>
	 * Preset.Storage Date <BR>
	 * Preset.Storage Time <BR>
	 * Preset.Expiry Date <BR>
	 * Preset.last update date/time* <BR>
	 * Preset.Preset Line No.* <BR>
	 * Preset.Stock ID(Unique Key )* <BR>
	 * </DIR>
	 * <BR>
	 * [Returned data] <BR>
	 * <BR>
	 * <DIR>
	 * Consignor code <BR>
	 * Consignor name <BR>
	 * Item code* <BR>
	 * Item name* <BR>
	 * Case/Piece Division* <BR>
	 * Location*<BR>
	 * Packing qty per case <BR>
	 * Inventory packing qty per case <BR>
	 * Case ITF <BR>
	 * Packing qty per Bundle <BR>
	 * Inventory packing qty per Piece <BR>
	 * Bundle ITF <BR>
	 * Storage Date <BR>
	 * Storage Time <BR>
	 * Expiry Date <BR>
	 * last update date/time* <BR>
	 * Preset Line No.* <BR>
	 * Stock ID(Unique Key)* <BR>
	 * </DIR>
	 * </DIR> <BR>
	 * <BR>
	 * 2. If the result of the schedule is true, then display the preset info again based on the returned data.<BR>
	 * 3.If the result of the schedule is true, then clear the contents of the input area<BR>
	 * 4.If the result of the schedule is true, then set the default (initial/default value:-1) for the preset line No. of ViewState.<BR>
	 * 5.If the schedule result is true, revoke input, clear button. <BR>
	 * 6.If the result of the schedule is true, then retun the color of the correction target line to the original color <BR>
	 * 7.Output the message obtained from the schedule to the screen. <BR>
	 * </DIR>
	 * </DIR> <BR>
	 * 
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_ModifySubmit_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM8227
			// Set cursor to item code
			setFocus(txt_ItemCode);

			//#CM8228
			// Set to the schedule parameter
			StockControlParameter[] param = null;
			//#CM8229
			// Set all the data in the preset area.
			param = setListParam(-1);

			if (param == null || param.length <= 0)
			{
				//#CM8230
				// Set the message
				//#CM8231
				// 6003103=The format of {0} is wrong.
				message.setMsgResourceKey("6003013");
				return;
			}

			//#CM8232
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM8233
			// Generate the instance of stock modify or delete schedule class
			WmsScheduler schedule = new StockCorrectSCH();

			//#CM8234
			// Execute stock correction process (Returns teh display data after correction)
			StockControlParameter[] viewParam =
				(StockControlParameter[]) schedule.startSCHgetParams(conn, param);
			
			//#CM8235
			// Receive the message amd ended when error or similar occurs.
			if (viewParam == null)
			{
				//#CM8236
				// Execute rollback of the Connection
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM8237
			// Commit when the process normally completed.
			conn.commit();

			//#CM8238
			// Display Preset area again based on the Returned data when the process normally completed.
			//#CM8239
			// Delete Preset area of the previous time
			lst_SStockCorrect.clearRow();

			//#CM8240
			// Re-display Preset area
			setList(viewParam);

			//#CM8241
			// Clear Input area
			inputAreaClear();

			//#CM8242
			// Return default:-1 for the preset line No.
			this.getViewState().setInt(LINENO, -1);

			//#CM8243
			// Set the message
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			ex.printStackTrace();

			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM8244
				// Close the connection
				if (conn != null)
				{
					//#CM8245
					// Add 2005/08/25 Y.Kawai		
					conn.rollback();
					//#CM8246
					// End 2005/08/25
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM8247
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_AllDelete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8248
	/**
	 * Clicking All delete button invokes this.<BR>
	 * <BR>
	 * Summary:Detele all the display info of preset.<BR>
	 * <BR>
	 * <DIR>
	 * 1.Display the dialog box to confirm if detelation of all the display info of preset is made or not.<BR>
	 * <DIR>
	 * "Do you delete all data?" <BR>
	 * [Check dialog cancel] <BR>
	 * <DIR>
	 * Disable 
	 * </DIR>
	 * [Check dialog OK] <BR>
	 * <DIR>
	 * 1.Start the schedule. <BR>
	 * <DIR>
	 * [parameter] *Mandatory Input <BR>
	 * <DIR>
	 * Stock ID(Unique Key)* <BR>
	 * last update date/time* <BR>
	 * Target Line No.* <BR>
	 * </DIR>
	 * </DIR>
	 * 2.If the result of the schedule is true, then clear the contents of the input area <BR>
	 * 3.If the result of the schedule is true, delete all the display info of preset.<BR>
	 * 4.If the result of the schedule is true, Set the default (initial/default value:-1) for the preset line No. of ViewState. <BR>
	 * 5.If the result of the schedule is true, revoke modify submit/add, full delation, input and Clear buttons. <BR>
	 * 6.Output the message obtained from the schedule to the screen. <BR>
	 * 7.Set cursor to item code. <BR>
	 * </DIR> <BR>
	 * </DIR> <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_AllDelete_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM8249
			// Set to the schedule parameter
			StockControlParameter[] listparam = null;

			Vector vecParam = new Vector();

			//#CM8250
			// Terminal No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			String teminalNumber = userHandler.getTerminalNo();
			//#CM8251
			// Set the parameter of preset line.
			for (int i = 1; i < lst_SStockCorrect.getMaxRows(); i++)
			{
				//#CM8252
				// Designate the line.
				lst_SStockCorrect.setCurrentRow(i);

				//#CM8253
				// Obtain 1 line data.
				StockControlParameter param = new StockControlParameter();

				//#CM8254
				// Worker Code
				param.setWorkerCode(this.viewState.getString(StockCorrectBusiness.WORKERCODE));
				//#CM8255
				// Password
				param.setPassword(this.viewState.getString(StockCorrectBusiness.PASSWORD));
				//#CM8256
				// Target Line No.
				param.setRowNo(i);
				//#CM8257
				// Stock ID(Unique Key)
				param.setStockId(
					CollectionUtils.getString(HDNSTOCKID_IDX, lst_SStockCorrect.getValue(LSTHDN)));
				//#CM8258
				// last update date/time
				param.setLastUpdateDate(
					WmsFormatter.getTimeStampDate(
						CollectionUtils.getString(
							HDNLASTUPDDATE_IDX,
							lst_SStockCorrect.getValue(LSTHDN))));
				//#CM8259
				// Terminal No.
				param.setTerminalNumber(teminalNumber);

				//#CM8260
				// Set data in one line to the array.
				vecParam.addElement(param);
			}

			if (vecParam.size() > 0)
			{
				//#CM8261
				// Set the value when preset line is found.
				listparam = new StockControlParameter[vecParam.size()];
				vecParam.copyInto(listparam);
			}

			//#CM8262
			// Generate the instance of stock modify or delete schedule class.
			WmsScheduler schedule = new StockCorrectSCH();

			//#CM8263
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM8264
			// Execute delete process
			if (schedule.startSCH(conn, listparam))
			{
				//#CM8265
				// Commit when process is completed normally.
				conn.commit();

				//#CM8266
				// All delete preset lines
				lst_SStockCorrect.clearRow();

				//#CM8267
				// Clear Input area
				inputAreaClear();

				//#CM8268
				// Disable pressing buttons
				//#CM8269
				// Correction add button
				btn_ModifySubmit.setEnabled(false);
				//#CM8270
				// All delete button
				btn_AllDelete.setEnabled(false);

				if (schedule.getMessage() != null)
				{
					//#CM8271
					// Set the message
					message.setMsgResourceKey(schedule.getMessage());
				}
			}
			else
			{
				//#CM8272
				// Rollback is made when abnormal end is generated.
				conn.rollback();
				//#CM8273
				// Set the message
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
				//#CM8274
				// Close the connection
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

	//#CM8275
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_SStockCorrect_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8276
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_SStockCorrect_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8277
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_SStockCorrect_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8278
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_SStockCorrect_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM8279
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_SStockCorrect_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8280
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_SStockCorrect_Change(ActionEvent e) throws Exception
	{
	}

	//#CM8281
	/**
	 * Clicking on Delete, Correction button invokes this.<BR>
	 * <BR>
	 * Delete buttonSummary:Delete corresponding data.<BR>
	 * <BR>
	 * <DIR>
	 * 1.Display the dialog box to allow to confirm to check the preset info.<BR>
	 * <DIR>
	 * "Do you delete this?" <BR>
	 * [Check dialog cancel] <BR>
	 * <DIR>
	 * Disable </DIR> [Check dialog OK] <BR>
	 * <DIR>
	 * 1.Start the schedule. <BR>
	 * <DIR>
	 * [parameter] *Mandatory Input <BR>
	 * <DIR>
	 * Preset Line No. <BR>
	 * Stock ID(Unique Key )* <BR>
	 * last update date/time* <BR>
	 * </DIR>
	 * </DIR>
	 * 2.If the result of the schedule is true, then clear the contents of the input area. <BR>
	 * 3.If the result of the schedule is true, then clear the correspondent data of preset<BR>
	 * 4. If the result of the schedule is true,Set the default (initial/default value:-1) for the preset line No. of ViewState.<BR>
	 * 5.If the result of the schedule is true, revoke modify submit/add, full delation buttons.<BR>
	 * 6.When no Preset info is found, disable the modify submit/add, all delate button.<BR>
	 * 7.Output the message obtained from the schedule to the screen. <BR>
	 * 8.Set cursor to item code. <BR>
	 * </DIR>
	 * </DIR>
	 * </DIR> <BR>
	 * <BR>
	 * Summary of Modify button: Change the status of the corresponding data in the preset area to Modify. <BR>
	 * <BR>
	 * <DIR>
	 * 1.Displaies the selected info in the upper input area. <BR>
	 * 2.Change the color of Selected info section to light yellow. <BR>
	 * 3.Set correction target line to the Preset Line No. of the ViewState.  <BR>
	 * 4.Set cursor to item code. <BR>
	 * 5.Enable Input button, Clear button.<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void lst_SStockCorrect_Click(ActionEvent e) throws Exception
	{
		//#CM8282
		// Click Delete button
		if (lst_SStockCorrect.getActiveCol() == LSTDELBTN)
		{
			Connection conn = null;

			try
			{
				//#CM8283
				// Delete button handles the data at the line pressed down.
				lst_SStockCorrect.setCurrentRow(lst_SStockCorrect.getActiveRow());

				//#CM8284
				// Set to the schedule parameter
				StockControlParameter[] param = new StockControlParameter[1];
				param[0] = new StockControlParameter();

				//#CM8285
				// Worker Code
				param[0].setWorkerCode(this.viewState.getString(StockCorrectBusiness.WORKERCODE));
				//#CM8286
				// Password
				param[0].setPassword(this.viewState.getString(StockCorrectBusiness.PASSWORD));
				//#CM8287
				// Preset Line No.
				param[0].setRowNo(lst_SStockCorrect.getCurrentRow());
				//#CM8288
				// Stock ID(Unique Key)
				param[0].setStockId(
					CollectionUtils.getString(HDNSTOCKID_IDX, lst_SStockCorrect.getValue(0)));
				//#CM8289
				// Final update time
				param[0].setLastUpdateDate(
					WmsFormatter.getTimeStampDate(
						CollectionUtils.getString(
							HDNLASTUPDDATE_IDX,
							lst_SStockCorrect.getValue(LSTHDN))));
				//#CM8290
				// Terminal No.
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param[0].setTerminalNumber(userHandler.getTerminalNo());

				//#CM8291
				// Generate the instance of stock modify or delete schedule class.
				WmsScheduler schedule = new StockCorrectSCH();

				//#CM8292
				// Obtain the connection
				conn = ConnectionManager.getConnection(DATASOURCE_NAME);

				//#CM8293
				// Execute delete process
				if (schedule.startSCH(conn, param))
				{
					//#CM8294
					// Commit when process is completed normally.
					conn.commit();

					//#CM8295
					// Delete the deleted preset line.
					lst_SStockCorrect.removeRow(lst_SStockCorrect.getActiveRow());

					//#CM8296
					// Clear input area
					inputAreaClear();

					//#CM8297
					// When no Preset info is found, disable the modify submit/add, all delate button.
					if (lst_SStockCorrect.getMaxRows() == 1)
					{
						//#CM8298
						// Disable pressing buttons
						//#CM8299
						// Correction add button
						btn_ModifySubmit.setEnabled(false);
						//#CM8300
						// All delete button
						btn_AllDelete.setEnabled(false);
					}
					if (schedule.getMessage() != null)
					{
						//#CM8301
						// Set the message
						message.setMsgResourceKey(schedule.getMessage());
					}
				}
				else
				{
					//#CM8302
					// Rollback is made when abnormal end is generated.
					conn.rollback();
					//#CM8303
					// Set the message
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
					//#CM8304
					// Close the connection
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
		//#CM8305
		// When Correction button is clicked
		else if (lst_SStockCorrect.getActiveCol() == LSTMODBTN)
		{
			//#CM8306
			// Process the data at teh line where Correction button is pressed.
			lst_SStockCorrect.setCurrentRow(lst_SStockCorrect.getActiveRow());

			//#CM8307
			// Develop the contents at Preset area to Input area.
			//#CM8308
			// Item code
			txt_ItemCode.setText(lst_SStockCorrect.getValue(LSTITEMCD));
			//#CM8309
			// Case/Piece division
			if (getCpfNameToCode(lst_SStockCorrect.getValue(LSTCPFNAME)).equals(StockControlParameter.CASEPIECE_FLAG_CASE))
			{
				//#CM8310
				// Case
				rdo_CpfCase.setChecked(true);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAppointOff.setChecked(false);
			}
			else if (getCpfNameToCode(lst_SStockCorrect.getValue(LSTCPFNAME)).equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
			{
				//#CM8311
				// Piece
				rdo_CpfCase.setChecked(false);
				rdo_CpfPiece.setChecked(true);
				rdo_CpfAppointOff.setChecked(false);
			}
			else if (getCpfNameToCode(lst_SStockCorrect.getValue(LSTCPFNAME)).equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				//#CM8312
				// None
				rdo_CpfCase.setChecked(false);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAppointOff.setChecked(true);
			}
			//#CM8313
			// Location
			txt_Location.setText(lst_SStockCorrect.getValue(LSTLOCATION));
			//#CM8314
			// Packing qty per case
			txt_CaseEntering.setText(lst_SStockCorrect.getValue(LSTENTERINGQTY));
			//#CM8315
			// Stock Case Qty
			txt_StockCaseQty.setText(lst_SStockCorrect.getValue(LSTSTOCKCASEQTY));
			//#CM8316
			// Case ITF
			txt_CaseItf.setText(lst_SStockCorrect.getValue(LSTCASEITF));
			//#CM8317
			// Storage Date
			txt_StorageDate.setText(lst_SStockCorrect.getValue(LSTSTORAGEDATE));
			//#CM8318
			// Expiry Date
			txt_UseByDate.setText(lst_SStockCorrect.getValue(LSTUSEBYDATE));
			//#CM8319
			// Item name
			txt_ItemName.setText(lst_SStockCorrect.getValue(LSTITEMNAME));
			//#CM8320
			// packed qty per bundle
			txt_BundleEntering.setText(lst_SStockCorrect.getValue(LSTBUNDLEENTERINGQTY));
			//#CM8321
			// Stock piece qty
			txt_StockPieceQty.setText(lst_SStockCorrect.getValue(LSTSTOCKPIECEQTY));
			//#CM8322
			// Bundle ITF
			txt_BundleItf.setText(lst_SStockCorrect.getValue(LSTBUNDLEITF));
			//#CM8323
			// Stock time
			txt_StorageTime.setText(lst_SStockCorrect.getValue(LSTSTORAGETIME));

			//#CM8324
			// Set Hidden parameter to ViewState
			//#CM8325
			// Correction selected line
			this.viewState.setInt(LINENO, lst_SStockCorrect.getActiveRow());
			//#CM8326
			// Stock ID
			this.viewState.setString(
				STOCKID,
				CollectionUtils.getString(HDNSTOCKID_IDX, lst_SStockCorrect.getValue(LSTHDN)));
			//#CM8327
			// last update date/time
			this.viewState.setString(
				LASTUPDDATE,
				CollectionUtils.getString(HDNLASTUPDDATE_IDX, lst_SStockCorrect.getValue(LSTHDN)));
			//#CM8328
			// Update flag
			this.viewState.setString(
				UPDATEFLAG,
				CollectionUtils.getString(HDNUPDATEFLAG_IDX, lst_SStockCorrect.getValue(LSTHDN)));

			//#CM8329
			// Change the color of correction target line to yellow.
			lst_SStockCorrect.setHighlight(lst_SStockCorrect.getActiveRow());

			//#CM8330
			// Enable the buttons inside the input area.
			//#CM8331
			// Input button
			btn_Input.setEnabled(true);
			//#CM8332
			// Clear button
			btn_Clear.setEnabled(true);

			//#CM8333
			// Set cursor to item code
			setFocus(txt_ItemCode);
		}
	}

	//#CM8334
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8335
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PsearchItem_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8336
	/**
	 * Clicking on the Search Item Code button invokes this. <BR>
	 * Summary: This Method set search conditions to the parameter, and Display the Item code list search listbox with the search condition.<BR>
	 * <BR>
	 * [parameter] <BR>
	 * <DIR>
	 * Consignor code <BR>
	 * Item code <BR>
	 * </DIR></BR>
	 * 
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PsearchItem_Click(ActionEvent e) throws Exception
	{
		//#CM8337
		// Set the search condition for the Search Location List screen.
		ForwardParameters param = new ForwardParameters();

		//#CM8338
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			lbl_JavaSetConsignorCode.getText());

		//#CM8339
		// Item code
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());

		//#CM8340
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM8341
		// In process screen->Result screen 
		redirect(StockCorrectBusiness.DO_SRCH_ITEM, param, StockCorrectBusiness.DO_SRCH_PROCESS);
	}

	//#CM8342
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8343
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StockCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8344
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StockCaseQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8345
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StockCaseQty_TabKey(ActionEvent e) throws Exception
	{
	}
	//#CM8346
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8347
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM8348
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8349
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM8350
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8351
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}


}
//#CM8352
//end of class
