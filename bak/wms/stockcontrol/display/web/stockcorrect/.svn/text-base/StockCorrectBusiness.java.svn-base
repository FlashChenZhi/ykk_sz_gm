// $Id: StockCorrectBusiness.java,v 1.2 2006/10/04 05:05:04 suresh Exp $

//#CM8353
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.stockcorrect;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocation.ListStockLocationBusiness;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;
import jp.co.daifuku.wms.stockcontrol.schedule.StockCorrectSCH;

//#CM8354
/**
 * Designer :<BR>
 * Maker : T.Uehata <BR>
 * <BR>
 * This is inventory correction, delete screen class.<BR>
 * Passes a parameter to execute inventory correction and schedule to be deleted. <BR>
 *  Set the contents input from the screen to the parameter, and the schedule search the data for display based on the parameter. <BR>
 * Execute following processes in this class. <BR>
 * <BR>
 * <BR>
 * 1.Next button press down process( <CODE>btn_Next_Click()</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * Execute input check of the parameter on the contents of input from the screen.<BR>
 * Store the parameter to the viewState and pass to the schedule.<BR>
 * Based on the value received from the schedule and the parameter, move to stock maintenance correction, delate (detail) screen. <BR>
 * <BR>
 * [ViewStateparameter] *Mandatory Input <BR>
 * <BR>
 * Worker Code* <BR>
 * Password* <BR>
 * Consignor code* <BR>
 * Start item code <BR>
 * End Start item code <BR>
 * Start location <BR>
 * End location <BR>
 * Case/PieceDivision* <BR>
 * Display Sequence* <BR>
 * <BR>
 * </DIR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/09/30</TD>
 * <TD>T.Uehata(DFK)</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:05:04 $
 * @author $Author: suresh $
 */
public class StockCorrectBusiness extends StockCorrect implements WMSConstants
{
	//#CM8355
	// Class fields --------------------------------------------------
	//#CM8356
	// Address of the shift target.
	//#CM8357
	// Popup address to search for Consignor.
	private static final String DO_SRCH_CONSIGNOR = "/stockcontrol/listbox/liststockconsignor/ListStockConsignor.do";
	//#CM8358
	// Pop-up address for location list search
	protected static final String DO_SRCH_LOCATION = "/stockcontrol/listbox/liststocklocation/ListStockLocation.do";
	//#CM8359
	// Popup address for searching item list.
	protected static final String DO_SRCH_ITEM = "/stockcontrol/listbox/liststockitem/ListStockItem.do";
	//#CM8360
	// The screen address while invoking the popup screen for searching.
	protected static final String DO_SRCH_PROCESS = "/progress.do";
	//#CM8361
	// Stock modifying, deleting basic info correction deleting address 
	protected static final String DO_CORRECT = "/stockcontrol/stockcorrect/StockCorrect.do";
	//#CM8362
	// Stock modifying, deleting detailed info correction deleting address 
	private static final String DO_CORRECT2 = "/stockcontrol/stockcorrect/StockCorrect2.do";
	
	//#CM8363
	// Worker Code
	protected static final String WORKERCODE = "WORKERCODE";
	//#CM8364
	// Password
	protected static final String PASSWORD = "PASSWORD";
	//#CM8365
	// Case/Piece division name
	protected static final String CPFNAME = "CPFNAME";
	//#CM8366
	// Display Sequence(Code)
	protected static final String VIEWSQCODE = "VIEWSQCODE";
	//#CM8367
	// Display Sequence name
	protected static final String VIEWSQNAME = "VIEWSQNAME";
	//#CM8368
	// message
	protected static final String MESSAGE = "MESSAGE";
	//#CM8369
	// Title
	protected static final String TITLE = "TITLE";
	
	//#CM8370
	// Class variables -----------------------------------------------

	//#CM8371
	// Class method --------------------------------------------------

	//#CM8372
	// Constructors --------------------------------------------------

	//#CM8373
	// Public methods ------------------------------------------------

	//#CM8374
	/**
	 * Invoke this before invoking each control event. <BR>
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
			//#CM8375
			// Obtain parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM8376
			// Store to ViewState
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM8377
			// Set the screen name
			lbl_SettingName.setResourceKey(title);
		}
	}

	//#CM8378
	/**
	 * Initialize the screen <BR>
	 * <BR>
	 * Summary:Execute screen Title setting and initialize input area.<BR>
	 * <BR>
	 * <DIR>
	 * 1.Initialize the screen. <BR>
	 * -Refer to <CODE>setInitView(boolean clear)</CODE>Method in regard with the initial value.<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM8379
		// Execute advance copy of basic info setting tab
		tab_BscDtlMdfyDlt.setSelectedIndex(1);
		
		//#CM8380
		// Set Title when returning from 2nd screen.
		if (!StringUtil.isBlank(this.viewState.getString(TITLE)))
		{
			lbl_SettingName.setResourceKey(this.viewState.getString(TITLE));
		}
		
		//#CM8381
		// Set the initial value for each input field.
		setInitView(true);
		
		//#CM8382
		// When stock maintenance correct, delete (detaile) and return button is pressed down
		//#CM8383
		// Set the value if certain value is set to the ViewState.
		//#CM8384
		// Worker Code
		if (!StringUtil.isBlank(this.viewState.getString(WORKERCODE)))
		{
			txt_WorkerCode.setText(this.viewState.getString(WORKERCODE));
		}
		//#CM8385
		// Password
		if (!StringUtil.isBlank(this.getViewState().getString(PASSWORD)))
		{
			txt_Password.setText(this.getViewState().getString(PASSWORD));
		}
		//#CM8386
		// Consignor code
		if (!StringUtil.isBlank(this.getViewState().getString(ListStockConsignorBusiness.CONSIGNORCODE_KEY)))
		{
			txt_ConsignorCode.setText(this.getViewState().getString(ListStockConsignorBusiness.CONSIGNORCODE_KEY));
		}
		//#CM8387
		// Start item code
		if (!StringUtil.isBlank(this.getViewState().getString(ListStockItemBusiness.STARTITEMCODE_KEY)))
		{
			txt_StartItemCode.setText(this.getViewState().getString(ListStockItemBusiness.STARTITEMCODE_KEY));
		}
		//#CM8388
		// End item code
		if (!StringUtil.isBlank(this.getViewState().getString(ListStockItemBusiness.ENDITEMCODE_KEY)))
		{
			txt_EndItemCode.setText(this.getViewState().getString(ListStockItemBusiness.ENDITEMCODE_KEY));
		}
		//#CM8389
		// Start location
		if (!StringUtil.isBlank(this.getViewState().getString(ListStockLocationBusiness.STARTLOCATION_KEY)))
		{
			txt_StartLocation.setText(this.getViewState().getString(ListStockLocationBusiness.STARTLOCATION_KEY));
		}
		//#CM8390
		// End location
		if (!StringUtil.isBlank(this.getViewState().getString(ListStockLocationBusiness.ENDLOCATION_KEY)))
		{
			txt_EndLocation.setText(this.getViewState().getString(ListStockLocationBusiness.ENDLOCATION_KEY));
		}
		//#CM8391
		// Case/Piece Division
		if (!StringUtil.isBlank(this.getViewState().getString(ListStockLocationBusiness.CASEPIECEFLAG_KEY)))
		{
			String cpf = this.getViewState().getString(ListStockLocationBusiness.CASEPIECEFLAG_KEY);

			if (cpf.equals(StockControlParameter.CASEPIECE_FLAG_ALL))
			{
				//#CM8392
				// Case/Piece Division = When it is all
				//#CM8393
				// when it comes to here, execute nothing as setting is ready by now 
			}
			else if (cpf.equals(StockControlParameter.CASEPIECE_FLAG_CASE))
			{
				//#CM8394
				// Case/Piece Division = When it is Case
				rdo_CpfAll.setChecked(false);
				rdo_CpfCase.setChecked(true);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAppointOff.setChecked(false);

			}
			else if (cpf.equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
			{
				//#CM8395
				// Case/Piece Division = When it is Piece
				rdo_CpfAll.setChecked(false);
				rdo_CpfCase.setChecked(false);
				rdo_CpfPiece.setChecked(true);
				rdo_CpfAppointOff.setChecked(false);
			}
			else if (cpf.equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				//#CM8396
				// Case/Piece Division = When it is None
				rdo_CpfAll.setChecked(false);
				rdo_CpfCase.setChecked(false);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAppointOff.setChecked(true);
			}
		}
		//#CM8397
		// Display Sequence
		if (!StringUtil.isBlank(this.getViewState().getString(VIEWSQCODE)))
		{
			String vsc = this.getViewState().getString(VIEWSQCODE);

			if (vsc.equals(StockControlParameter.DSPORDER_ITEM_LOCATION))
			{
				//#CM8398
				// Display Sequence = When the sequence is 1.Item code, 2.Location
				//#CM8399
				// when it comes to here, execute nothing as setting is ready by now 
			}
			else if (vsc.equals(StockControlParameter.DSPORDER_ITEM_STORAGEDATE))
			{
				//#CM8400
				// Display Sequence = When the sequence is 1.Item code, 2.Storage Date
				rdo_ItemCodeLocation.setChecked(false);
				rdo_ItemCdStrgDate.setChecked(true);
				rdo_LocationOrder.setChecked(false);
			}
			else if (vsc.equals(StockControlParameter.DSPORDER_LOCATION))
			{
				//#CM8401
				// Display Sequence = When it is the sequence of Location
				rdo_ItemCodeLocation.setChecked(false);
				rdo_ItemCdStrgDate.setChecked(false);
				rdo_LocationOrder.setChecked(true);
			}
		}

		//#CM8402
		// error message
		if (!StringUtil.isBlank(this.getViewState().getString(MESSAGE)))
		{
			//#CM8403
			// Display error message
			message.setMsgResourceKey(this.getViewState().getString(MESSAGE));
			//#CM8404
			// Clear ViewState message after displaying the message
			this.getViewState().setString(MESSAGE, "");
		}
	}

	//#CM8405
	/**
	 * Invoke this method when returning from the popup window.<BR>
	 * Override <CODE>page_DlgBack</CODE> defined on <CODE>Page</CODE>.<BR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent)e).getDialogParameters();
		
		//#CM8406
		// Obtain the parameter selected in the listbox.
		//#CM8407
		// Consignor code
		String _consignorcode = param.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM8408
		// Start item code
		String _startitemcode = param.getParameter(ListStockItemBusiness.STARTITEMCODE_KEY);
		//#CM8409
		// End item code
		String _enditemcode = param.getParameter(ListStockItemBusiness.ENDITEMCODE_KEY);
		//#CM8410
		// Start location
		String _startlocation = param.getParameter(ListStockLocationBusiness.STARTLOCATION_KEY);
		//#CM8411
		// End location
		String _endlocation = param.getParameter(ListStockLocationBusiness.ENDLOCATION_KEY);
		
		//#CM8412
		// Set the value to the screen when obtained each parameter is not empty.
		//#CM8413
		// Consignor code
		if (!StringUtil.isBlank(_consignorcode))
		{
			txt_ConsignorCode.setText(_consignorcode);
		}
		//#CM8414
		// Start item code
		if (!StringUtil.isBlank(_startitemcode))
		{
			txt_StartItemCode.setText(_startitemcode);
		}
		//#CM8415
		// End item code
		if (!StringUtil.isBlank(_enditemcode))
		{
			txt_EndItemCode.setText(_enditemcode);
		}
		//#CM8416
		// Start location
		if (!StringUtil.isBlank(_startlocation))
		{
			txt_StartLocation.setText(_startlocation);
		}
		//#CM8417
		// End location
		if (!StringUtil.isBlank(_endlocation))
		{
			txt_EndLocation.setText(_endlocation);
		}

		//#CM8418
		// Set cursor to the Worker Code
		setFocus(txt_WorkerCode);
	}

	//#CM8419
	// Package methods -----------------------------------------------

	//#CM8420
	// Protected methods ---------------------------------------------

	//#CM8421
	// Private methods -----------------------------------------------

	//#CM8422
	/**
	 * This method obtains the Consignor code from the schedule on the initial display. <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule. <BR>
	 * 
	 * @return Consignor code <BR>
	 *         Return the string of the Consignor code when one corresponding data exists. <BR>
	 *         Return empty character when 0 or multiple corresponding data exist. <BR>
	 * 
	 * @throws Exception Reports all the exceptions.
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM8423
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StockControlParameter param = new StockControlParameter();

			//#CM8424
			// Obtain Consignor code from schedule
			WmsScheduler schedule = new StockCorrectSCH();
			param = (StockControlParameter) schedule.initFind(conn, param);

			if (param != null)
			{
				return param.getConsignorCode();
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			//#CM8425
			// Close the connection
			if (conn != null)
			{
				conn.close();
			}
		}
		return "";
	}

	//#CM8426
	/**
	 * This is the method to initialize the screen.<BR>
	 * <BR>
	 * Summary: Shows initial display of the screen. <BR>
	 * <DIR>
	 * [parameter] <BR>
	 * <DIR>
	 * Initialize Worker Code/Password if the result of clearFlg is true, if false not initialize it.<BR>
	 * <DIR>
	 * Item name [Initial value]<BR>
	 * <DIR>
	 * Worker Code [Keep it as it is when None/Clear button is pressed] <BR>
	 * Password [Keep it as it is when None/Clear button is pressed] <BR>
	 * Consignor code [ For only one Consignor corresponding, execute the initial display] <BR>
	 * Start item code [None] <BR>
	 * End item code [None] <BR>
	 * Start location [None]<BR>
	 * End location [None] <BR>
	 * Case/Piece Division [Select "all"] <BR>
	 * Display Sequence ["Item Code, Location sequence" Selection] <BR>
	 * <BR>
	 * </DIR>
	 * </DIR>
	 * </DIR>
	 * @param clearFlg Clear flag
	 * @throws Exception Reports all the exceptions.
	 */
	private void setInitView(boolean clearFlg) throws Exception
	{
		//#CM8427
		// Initialize input subject
		if (clearFlg)
		{
			//#CM8428
			// Worker Code
			txt_WorkerCode.setText("");
			//#CM8429
			// Password
			txt_Password.setText("");
		}
		//#CM8430
		// Consignor code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM8431
		// Start item code
		txt_StartItemCode.setText("");
		//#CM8432
		// End item code
		txt_EndItemCode.setText("");
		//#CM8433
		// Start location
		txt_StartLocation.setText("");
		//#CM8434
		// End location
		txt_EndLocation.setText("");
		//#CM8435
		// Case/Piece Division
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);
		//#CM8436
		// Display Sequence
		rdo_ItemCodeLocation.setChecked(true);
		rdo_ItemCdStrgDate.setChecked(false);
		rdo_LocationOrder.setChecked(false);

		//#CM8437
		// Move focus to Worker Code
		setFocus(txt_WorkerCode);
	}
	

	//#CM8438
	// Event handler methods -----------------------------------------
	//#CM8439
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8440
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8441
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8442
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

	//#CM8443
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8444
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8445
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8446
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8447
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8448
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8449
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8450
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8451
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8452
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8453
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8454
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8455
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8456
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8457
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8458
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PsearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8459
	/**
	 * Clicking on the Search Consignor Code button invokes this. <BR>
	 * Summary: This Method set search conditions to the parameter, and, 
	 * display Consignor code search listbox by these search conditions.<BR>
	 * <BR>
	 * <DIR>
	 * [parameter] <BR>
	 * <DIR>
	 * Consignor code <BR>
	 * </DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PsearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM8460
		// Set the search condition for the Search Consignor screen.
		ForwardParameters param = new ForwardParameters();

		//#CM8461
		// Consignor code
		param.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM8462
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM8463
		// In process screen->Result screen 
		redirect(DO_SRCH_CONSIGNOR, param, DO_SRCH_PROCESS);
	}

	//#CM8464
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_StartItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8465
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8466
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8467
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8468
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8469
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PsearchStartItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8470
	/**
	 * Clicking Start on the Search Item Code button invokes this. <BR>
	 * Summary: This Method set search conditions to the parameter, and, 
	 * Display Item list search  the listbox with the search condition<BR>
	 * <BR>
	 * <DIR>
	 * [parameter] <BR>
	 * <DIR>
	 * Consignor code <BR>
	 * Start item code <BR>
	 * </DIR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PsearchStartItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM8471
		// Set the search conditions to the Item Code list search screen.
		ForwardParameters param = new ForwardParameters();

		//#CM8472
		// Consignor code
		param.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM8473
		// Start item code
		param.setParameter(ListStockItemBusiness.STARTITEMCODE_KEY, txt_StartItemCode.getText());

		//#CM8474
		// Set start flag
		param.setParameter(
			ListStockItemBusiness.RANGEITEMCODE_KEY,
			StockControlParameter.RANGE_START);

		//#CM8475
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM8476
		// In process screen->Result screen 
		redirect(DO_SRCH_ITEM, param, DO_SRCH_PROCESS);
	}

	//#CM8477
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_FromToItem_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8478
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_EndItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8479
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8480
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8481
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8482
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8483
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PsearchEndItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8484
	/**
	 * End clicking on the Search Item Code button invokes this. <BR>
	 * Summary: This Method set search conditions to the parameter, and
	 * Item code list searches Display in the listbox with the search conditions.<BR>
	 * <BR>
	 * <DIR>
	 * [parameter] <BR>
	 * <DIR>
	 * Consignor code <BR>
	 * End item code <BR>
	 * </DIR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PsearchEndItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM8485
		// Set the search conditions to the Item Code list search screen.
		ForwardParameters param = new ForwardParameters();

		//#CM8486
		// Consignor code
		param.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM8487
		// End item code
		param.setParameter(ListStockItemBusiness.ENDITEMCODE_KEY, txt_EndItemCode.getText());

		//#CM8488
		// Set the Close flag.
		param.setParameter(
			ListStockItemBusiness.RANGEITEMCODE_KEY,
			StockControlParameter.RANGE_END);

		//#CM8489
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM8490
		// In process screen->Result screen 
		redirect(DO_SRCH_ITEM, param, DO_SRCH_PROCESS);
	}

	//#CM8491
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_StartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8492
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8493
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8494
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8495
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8496
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PsearchStartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8497
	/**
	 * Clicking on the Search Start Location button invokes this. <BR>
	 * Summary: This Method set search conditions to the parameter, and
	 * Display the location list search listbox using the search conditions. <BR>
	 * <BR>
	 * <DIR>
	 * [parameter] <BR>
	 * <DIR>
	 * Consignor code <BR>
	 * Start item code <BR>
	 * End item code <BR>
	 * Start location <BR>
	 * </DIR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PsearchStartLocation_Click(ActionEvent e) throws Exception
	{
		//#CM8498
		// Set the search condition for the Search Location List screen.
		ForwardParameters param = new ForwardParameters();

		//#CM8499
		// Consignor code
		param.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM8500
		// Start item code
		param.setParameter(ListStockItemBusiness.STARTITEMCODE_KEY, txt_StartItemCode.getText());

		//#CM8501
		// End item code
		param.setParameter(ListStockItemBusiness.ENDITEMCODE_KEY, txt_EndItemCode.getText());

		//#CM8502
		// Start location
		param.setParameter(ListStockLocationBusiness.STARTLOCATION_KEY, txt_StartLocation.getText());

		//#CM8503
		// Set start flag
		param.setParameter(
			ListStockLocationBusiness.RANGELOCATION_KEY,
			StockControlParameter.RANGE_START);
		
		//#CM8504
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM8505
		// In process screen->Result screen 
		redirect(DO_SRCH_LOCATION, param, DO_SRCH_PROCESS);
	}

	//#CM8506
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_FromToLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8507
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_EndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8508
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8509
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8510
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8511
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8512
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PsearchEndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8513
	/**
	 * Clicking on the Search End Location button invokes this. <BR>
	 * Summary: This Method set search conditions to the parameter, and
	 * Display the location list search listbox using the search conditions. <BR>
	 * <BR>
	 * <DIR>
	 * [parameter] <BR>
	 * <DIR>
	 * Consignor code <BR>
	 * Start item code <BR>
	 * End item code <BR>
	 * End location <BR>
	 * </DIR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PsearchEndLocation_Click(ActionEvent e) throws Exception
	{
		//#CM8514
		// Set the search condition for the Search Location List screen.
		ForwardParameters param = new ForwardParameters();
		
		//#CM8515
		// Consignor code
		param.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM8516
		// Start item code
		param.setParameter(ListStockItemBusiness.STARTITEMCODE_KEY, txt_StartItemCode.getText());

		//#CM8517
		// End item code
		param.setParameter(ListStockItemBusiness.ENDITEMCODE_KEY, txt_EndItemCode.getText());

		//#CM8518
		// End location
		param.setParameter(ListStockLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getText());

		//#CM8519
		// Set the Close flag.
		param.setParameter(
			ListStockLocationBusiness.RANGELOCATION_KEY,
			StockControlParameter.RANGE_END);
		
		//#CM8520
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM8521
		// In process screen->Result screen 
		redirect(DO_SRCH_LOCATION, param, DO_SRCH_PROCESS);
	}

	//#CM8522
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8523
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Dcd_All_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8524
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Dcd_All_Click(ActionEvent e) throws Exception
	{
	}

	//#CM8525
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_Case_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8526
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_Case_Click(ActionEvent e) throws Exception
	{
	}

	//#CM8527
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_Piece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8528
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_Piece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM8529
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_AppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8530
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_AppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM8531
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_DspOrder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8532
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_ItemCodeLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8533
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_ItemCodeLocation_Click(ActionEvent e) throws Exception
	{
	}

	//#CM8534
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_ItemCdStrgDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8535
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_ItemCdStrgDate_Click(ActionEvent e) throws Exception
	{
	}

	//#CM8536
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_LocationOrder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8537
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_LocationOrder_Click(ActionEvent e) throws Exception
	{
	}

	//#CM8538
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Next_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8539
	/**
	 * Clicking Next button invokes this.<BR>
	 * <BR>
	 * Summary:Based on the input Item of Input area as condition, move to stock maintenance correction, delate (detail) screen.<BR>
	 * <BR>
	 * <DIR>
	 * 1.Check input field of input area (mandatory input)<BR>
	 * 2.Start schedule <BR>
	 * <DIR>
	 * [parameter] *Mandatory Input <BR>
	 * <DIR>
	 * Worker Code* <BR>
	 * Password* <BR>
	 * Consignor code* <BR>
	 * Start item code <BR>
	 * End item code <BR>
	 * Start location <BR>
	 * End location <BR>
	 * Case/Piece Division* <BR>
	 * Display Sequence* <BR>
	 * </DIR>
	 * </DIR>
	 * 3.If the schedule result is true, the screen will shift to the next (planned adding details) screen.<BR>
	 * <DIR>
	 * [ViewStateparameter] *Mandatory Input <BR>
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
	 * <BR>
	 * </DIR>
	 * </DIR>
	 * Output the message obtained from the schedule to the screen <BR>
	 * <BR>
	 * 4.Maintain the contents of the input area is still maintained. <BR>
	 * <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Next_Click(ActionEvent e) throws Exception
	{
		//#CM8540
		// Set the cursor to the worker code
		setFocus(txt_WorkerCode);

		//#CM8541
		// mandatory input check
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		//#CM8542
		// Check the item code size
		if(!StringUtil.isBlank(txt_StartItemCode.getText()) && !StringUtil.isBlank(txt_EndItemCode.getText()))
		{
			if(txt_StartItemCode.getText().compareTo(txt_EndItemCode.getText()) > 0)
			{
				//#CM8543
				// Display the error message and close it.
				//#CM8544
				// 6023109=Starting item code must be smaller than the end item code.
				message.setMsgResourceKey("6023109");
				return ;
			}
		}
		//#CM8545
		// Execute checking of Location size
		if(!StringUtil.isBlank(txt_StartLocation.getText()) && !StringUtil.isBlank(txt_EndLocation.getText()))
		{
			if(txt_StartLocation.getText().compareTo(txt_EndLocation.getText()) > 0)
			{
				//#CM8546
				// Display the error message and close it.
				//#CM8547
				// 6023124=Starting location No. must precede the end location No.
				message.setMsgResourceKey("6023124");
				return ;
			}
		}
		
		//#CM8548
		// Set parameter
		StockControlParameter scparam = new StockControlParameter();
		
		//#CM8549
		// Worker Code
		scparam.setWorkerCode(txt_WorkerCode.getText());
		//#CM8550
		// Password
		scparam.setPassword(txt_Password.getText());
		//#CM8551
		// Consignor code
		scparam.setConsignorCode(txt_ConsignorCode.getText());
		//#CM8552
		// Start item code
		scparam.setFromItemCode(txt_StartItemCode.getText());
		//#CM8553
		// End item code
		scparam.setToItemCode(txt_EndItemCode.getText());
		//#CM8554
		// Start location
		scparam.setFromLocationNo(txt_StartLocation.getText());
		//#CM8555
		// End location
		scparam.setToLocationNo(txt_EndLocation.getText());
		//#CM8556
		// Case/Piece Division
		String cpf = "";
		if (rdo_CpfAll.getChecked())
		{
			cpf = StockControlParameter.CASEPIECE_FLAG_ALL;
		}
		else if (rdo_CpfCase.getChecked())
		{
			cpf = StockControlParameter.CASEPIECE_FLAG_CASE;
		}
		else if (rdo_CpfPiece.getChecked())
		{
			cpf = StockControlParameter.CASEPIECE_FLAG_PIECE;
		}
		else if (rdo_CpfAppointOff.getChecked())
		{
			cpf = StockControlParameter.CASEPIECE_FLAG_NOTHING;
		}
		scparam.setSelectCasePieceFlag(cpf);
		//#CM8557
		// Display Sequence
		String vsq = "";
		if (rdo_ItemCodeLocation.getChecked())
		{
			vsq = StockControlParameter.DSPORDER_ITEM_LOCATION;
		}
		else if (rdo_ItemCdStrgDate.getChecked())
		{
			vsq = StockControlParameter.DSPORDER_ITEM_STORAGEDATE;
		}
		else if (rdo_LocationOrder.getChecked())
		{
			vsq = StockControlParameter.DSPORDER_LOCATION;
		}
		scparam.setDspOrder(vsq);
		

		Connection conn = null;
		
		//#CM8558
		// Generate schedule class instance
		WmsScheduler schedule = new StockCorrectSCH();
		
		try
		{
			//#CM8559
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			//#CM8560
			// if no problem found in the parameter check of schedule class
			//#CM8561
			// Set screen parameter to the viewState and move to stock maintenance, delete (detail).
			if (schedule.nextCheck(conn,scparam))
			{
			
				//#CM8562
				// Worker Code
				viewState.setString(WORKERCODE, txt_WorkerCode.getText());
				//#CM8563
				// Password
				viewState.setString(PASSWORD, txt_Password.getText());
				//#CM8564
				// Consignor code
				viewState.setString(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
				//#CM8565
				// Start item code
				viewState.setString(ListStockItemBusiness.STARTITEMCODE_KEY, txt_StartItemCode.getText());
				//#CM8566
				// End item code
				viewState.setString(ListStockItemBusiness.ENDITEMCODE_KEY, txt_EndItemCode.getText());
				//#CM8567
				// Start location
				viewState.setString(ListStockLocationBusiness.STARTLOCATION_KEY, txt_StartLocation.getText());
				//#CM8568
				// End location
				viewState.setString(ListStockLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getText());
				//#CM8569
				// Case/Piece Division
				viewState.setString(ListStockLocationBusiness.CASEPIECEFLAG_KEY, cpf);
				//#CM8570
				// Display Sequence
				viewState.setString(VIEWSQCODE, vsq);
				//#CM8571
				// Title
				viewState.setString(TITLE, lbl_SettingName.getResourceKey());
				
				//#CM8572
				// basic info setting screen -> Detail info setting screen delete screen
				forward(DO_CORRECT2);
				
				if (schedule.getMessage() != null)
				{
					//#CM8573
					// Set the message
					message.setMsgResourceKey(schedule.getMessage());
				}
				
			}
			else
			{
				//#CM8574
				// When no targe data is found, display error message.
				if (schedule.getMessage() != null)
				{
					message.setMsgResourceKey(schedule.getMessage());	
				}
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
				//#CM8575
				// Connection close
				if (conn != null)
				{
					//#CM8576
					// Add 2005/08/25 Y.Kawai		
					conn.rollback();
					//#CM8577
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

	//#CM8578
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8579
	/**
	 * Clicking on the Clear button invokes this. <BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 * <DIR>
	 * 1.Initialize the screen. <BR>
	 * Refer to <CODE>setInitView(boolean clear)</CODE>Method in regard with the initial value<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM8580
		// Invoke the method to display the initial display.
		setInitView(false);
	}

	//#CM8581
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8582
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM8583
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8584
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM8585
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8586
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}
	//#CM8587
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8588
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

}
//#CM8589
//end of class
