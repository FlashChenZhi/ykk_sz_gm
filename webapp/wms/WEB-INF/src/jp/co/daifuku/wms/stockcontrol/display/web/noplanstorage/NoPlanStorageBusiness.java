// $Id: NoPlanStorageBusiness.java,v 1.2 2006/10/04 05:04:47 suresh Exp $

//#CM7295
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.noplanstorage;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Label;
import jp.co.daifuku.bluedog.ui.control.NumberTextBox;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocation.ListStockLocationBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockmaintenance.ListStockMaintenanceBusiness;
import jp.co.daifuku.wms.stockcontrol.schedule.NoPlanStorageSCH;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM7296
/**
 * Designer : A.Nagasawa <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * This is Unplanned Normal Storage class.<BR>
 * Set the contents input from the screen to the parameter, and<BR>
 * schedule executes Unplanned Normal Storage based on the parameter<BR>
 * Receive the result from the schedule and receive true when the process normally completed<BR>
 * Receive false when failed to schedule completely due to condition error or other causes.<BR>
 * Output the message obtained from the schedule to the screen<BR>
 *  Set the contents input from the screen to the parameter, and the schedule search the data for display based on the parameter.<BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * 1.Process by clicking on the Input button(<CODE>btn_Input_Click()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *  Set the and contents entered via the input area for the parameter, and the schedule checks the input condition based on the parameter.<BR>
 *  Receive the result from the schedule and receive true when the process normally completed<BR>
 *  Receive false when failed to schedule completely due to condition error or other causes.<BR>
 *  Output the message obtained from the schedule to the screen<BR>
 *  Add input area info to the preset area if the schedule result is true.<BR>
 *  Clicking on the Input button after clicking the Modify button updates the target preset data to be modified with the info in the input area.<BR>
 *  <BR>
 * 	[parameter] *Mandatory Input<BR>
 * 	<BR>
 * 	<DIR>
 * 		Worker Code* <BR>
 * 		Password* <BR>
 * 		Consignor code* <BR>
 * 		Consignor name <BR>
 * 		Item code* <BR>
 * 		Item name <BR>
 * 		Case/PieceDivision* <BR>
 * 		Store Location* <BR>
 * 		Packing qty per case*2 <BR>
 * 		Store Case qty*2 *4 <BR>
 * 		Case ITF <BR>
 * 		packed qty per bundle <BR>
 *		Storage Piece Qty*3 *4 <BR>
 *		Bundle ITF <BR>
 *		Expiry Date <BR>
 *		Prints Unplanned storage list.<BR>
 *		<BR>
 * 		*2 <BR>
 * 		When Case/PieceDivision is 1:Case, Mandatory Input <BR>
 * 		*3 <BR>
 * 		When Case/PieceDivision is 2:Piece, Mandatory Input <BR>
 * 		*4 <BR>
 * 		When Case/PieceDivision is 0:None, <BR>
 *		storage case qty <BR>
 * 		case qty x packed qty per case+ piece qty should not be 0.(>0) <BR>
 * 	</DIR>
 *  <BR>
 *  [Output data] <BR>
 *  <BR>
 * 	<DIR>
 *  	Consignor code <BR>
 *  	Consignor name <BR>
 *  	Item code <BR>
 *  	Item name <BR>
 *  	Case/Piece division <BR>
 *  	Store Location <BR>
 *  	Packing qty per case <BR>
 *  	packed qty per bundle <BR>
 *  	Store Case qty <BR>
 *  	Storage Piece Qty <BR>
 *  	Case ITF <BR>
 *  	Bundle ITF <BR>
 *  	Expiry Date <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.Start Storage button press down process(<CODE>btn_StorageStart_Click()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *  Set the contents input via preset area for the parameter.The schedule executes Unplanned storage setting based on the parameter.<BR>
 *  Receive the result from the schedule and receive true when the process normally completed<BR>
 *  Receive false when failed to schedule completely due to condition error or other causes.<BR>
 *  Output the message obtained from the schedule to the screen<BR>
 *  <BR>
 *  [parameter] *Mandatory Input <BR>
 *  <BR>
 * 	<DIR>
 *  	Worker Code* <BR>
 *  	Password* <BR>
 *      Terminal No.<BR>
 *      List Print Type<BR>
 *  	Consignor code <BR>
 *  	Consignor name <BR>
 *  	Item code <BR>
 *  	Item name <BR>
 *  	Case/Piece division <BR>
 *  	Store Location <BR>
 *  	Packing qty per case <BR>
 *  	packed qty per bundle <BR>
 *  	Store Case qty <BR>
 *  	Storage Piece Qty <BR>
 *  	Case ITF <BR>
 *  	Bundle ITF <BR>
 *  	Expiry Date <BR>
 *      Line No.<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/29</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:04:47 $
 * @author  $Author: suresh $
 */
public class NoPlanStorageBusiness extends NoPlanStorage implements WMSConstants
{
	//#CM7297
	// Class fields --------------------------------------------------

	//#CM7298
	// Class variables -----------------------------------------------

	//#CM7299
	// Class method --------------------------------------------------

	//#CM7300
	// Constructors --------------------------------------------------

	//#CM7301
	// Public methods ------------------------------------------------

	//#CM7302
	/**
	 * Initialize the screen<BR>
	 * <BR>
	 * Summary: Shows initial display of the screen.<BR>
	 * <BR>
	 * <DIR>
	 *    1.When Consignor is one<BR>
	 *    2.Set the cursor to the worker code.<BR>
	 *    2.Place a check on "Print Unplanned Storage Work List ".<BR>
	 *    3.Disable the Preset button (Start Storage; Clear List).<BR>
	 * <BR>
	 *    Subject name [Initial value]<BR>
	 *    <DIR>
	 * 		Worker Code[None] <BR>
	 * 		Password[None] <BR>
	 * 		Consignor code if one Consignor code <BR>
	 * 		Consignor name[Set the Consignor Name of the Stock info, when there is only one count] <BR>
	 * 		Item code [None] <BR>
	 * 		Item name[None] <BR>
	 * 		Case/PieceDivision[Case] <BR>
	 * 		Store Location[None] <BR>
	 * 		Packing qty per case[None] <BR>
	 * 		Store Case qty[None] <BR>
	 * 		Case ITF[None] <BR>
	 * 		packed qty per bundle[None] <BR>
	 * 		Storage Piece Qty[None] <BR>
	 * 		Bundle ITF[None] <BR>
	 * 		Expiry Date[None] <BR>
	 * 		Print the Unplanned Storage Work List [check placed] <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{

		//#CM7303
		// Set the a preset line No. in ViewState to determine the status being in Modify by clicking the Modify button.
		//#CM7304
		// (Default:-1)
		this.getViewState().setInt("LineNo", -1);

		//#CM7305
		// Disable button press down
		//#CM7306
		// Start Storage button
		btn_StorageStart.setEnabled(false);
		//#CM7307
		// List clear button
		btn_ListClear.setEnabled(false);

		//#CM7308
		// Show initial display
		setFirstDisp();
	}

	//#CM7309
	/**
	 * Invoke this before invoking each control event.<BR>
	 * <BR>
	 * Summary: Displays a dialog.<BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM7310
			// Obtain parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM7311
			// Store to ViewState
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM7312
			// Set the screen name
			lbl_SettingName.setResourceKey(title);
		}
		//#CM7313
		// Clicking on the Dialog to confirm when clicking the Start Storage button " Do you set this? "
		btn_StorageStart.setBeforeConfirm("MSG-9000");

		//#CM7314
		// Clicking on the List Clear button invokes dialog to confirm: " This clears the list input info. Do you confirm it? "
		btn_ListClear.setBeforeConfirm("MSG-W0012");
	}

	//#CM7315
	/**
	 * Invoke this method when returning from the popup window.
	 * <BR>
	 * Override <CODE>page_DlgBack</CODE> defined on the <CODE>Page</CODE>.
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM7316
		// Obtain the parameter selected in the listbox.
		//#CM7317
		// Consignor code
		String consignorcode = param.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM7318
		// Consignor name
		String consignorname = param.getParameter(ListStockConsignorBusiness.CONSIGNORNAME_KEY);
		//#CM7319
		// Item code
		String itemcode = param.getParameter(ListStockItemBusiness.ITEMCODE_KEY);
		//#CM7320
		// Item name
		String itemname = param.getParameter(ListStockItemBusiness.ITEMNAME_KEY);
		//#CM7321
		// Case/PieceDivision
		String casepieseflag = param.getParameter(ListStockLocationBusiness.CASEPIECEFLAG_KEY);
		//#CM7322
		// Store Location
		String storagelocation = param.getParameter(ListStockLocationBusiness.LOCATION_KEY);
		//#CM7323
		// Packing qtt of Case
		String entering_qty = param.getParameter(ListStockMaintenanceBusiness.ENTERINGQTY_KEY);
		//#CM7324
		// Packing qtt of Bundle
		String bundle_entering_qty = param.getParameter(ListStockMaintenanceBusiness.BUNDLE_ENTERINGQTY_KEY);
		//#CM7325
		// Case ITF
		String itf = param.getParameter(ListStockMaintenanceBusiness.CASEITF_KEY);
		//#CM7326
		// Bundle ITF
		String boundle_itf = param.getParameter(ListStockMaintenanceBusiness.BUNDLEITF_KEY);
		//#CM7327
		// Expiry Date
		String use_by_date = param.getParameter(ListStockMaintenanceBusiness.USEBYDATE_KEY);
		//#CM7328
		// Set the value if not empty.
		//#CM7329
		// Consignor code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM7330
		// Consignor name
		if (!StringUtil.isBlank(consignorname))
		{
			txt_ConsignorName.setText(consignorname);
		}
		//#CM7331
		// Item code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM7332
		// Item name
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemName.setText(itemname);
		}
		//#CM7333
		// Store Location
		if (!StringUtil.isBlank(storagelocation))
		{
			txt_StorageLocation.setText(storagelocation);
		}
		//#CM7334
		// Packing qtt of Case
		if (!StringUtil.isBlank(entering_qty))
		{
			txt_CaseEntering.setText(entering_qty);
		}
		//#CM7335
		// Packing qtt of Bundle
		if (!StringUtil.isBlank(bundle_entering_qty))
		{
			txt_BundleEntering.setText(bundle_entering_qty);
		}
		//#CM7336
		// Case ITF
		if (!StringUtil.isBlank(itf))
		{
			txt_CaseItf.setText(itf);
		}
		//#CM7337
		// Bundle ITF
		if (!StringUtil.isBlank(boundle_itf))
		{
			txt_BundleItf.setText(boundle_itf);
		}
		//#CM7338
		// Expiry Date
		if (!StringUtil.isBlank(use_by_date))
		{
			txt_UseByDate.setText(use_by_date);
		}

		//#CM7339
		// Set the cursor to the worker code
		setFocus(txt_WorkerCode);
	}

	//#CM7340
	// Package methods -----------------------------------------------

	//#CM7341
	// Protected methods ---------------------------------------------
	//#CM7342
	/**
	 * Execute input check.
	 * Set the message if any error and return false.
	 * 
	 * @return true:Normal false: Abnormal
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
		if (!checker.checkContainNgText(txt_ConsignorCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ConsignorName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
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
		if (!checker.checkContainNgText(txt_StorageLocation))
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

	//#CM7343
	// Private methods -----------------------------------------------

	//#CM7344
	/**
	 * Invoke this method to display the initial display or clear.
	 * @throws Exception Reports all the exceptions. 
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM7345
			// Set the cursor to the worker code
			setFocus(txt_WorkerCode);
			
			//#CM7346
			// Consignor code
			txt_ConsignorCode.setText("");
			//#CM7347
			// Consignor name
			txt_ConsignorName.setText("");
			//#CM7348
			// Item code
			txt_ItemCode.setText("");
			//#CM7349
			// Item name
			txt_ItemName.setText("");
			//#CM7350
			// Case/Piece division(Case)
			rdo_CpfCase.setChecked(true);
			//#CM7351
			// Case/Piece division(Piece)
			rdo_CpfPiece.setChecked(false);
			//#CM7352
			// Case/Piece division(None)
			rdo_CpfAppointOff.setChecked(false);
			//#CM7353
			// Store Location
			txt_StorageLocation.setText("");
			//#CM7354
			// Packing qty per case
			txt_CaseEntering.setText("");
			//#CM7355
			// Store Case qty
			txt_StrgCaseQty.setText("");
			//#CM7356
			// Case ITF
			txt_CaseItf.setText("");
			//#CM7357
			// packed qty per bundle
			txt_BundleEntering.setText("");
			//#CM7358
			// Storage Piece Qty
			txt_StrgPieseQty.setText("");
			//#CM7359
			// Bundle ITF
			txt_BundleItf.setText("");
			//#CM7360
			// Expiry Date
			txt_UseByDate.setText("");
			//#CM7361
			// Place a check on "Print Unplanned Storage Work List ".
			chk_CommonUse.setChecked(true);
		
			//#CM7362
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new NoPlanStorageSCH();

			StockControlParameter param = (StockControlParameter) schedule.initFind(conn, null);
			//#CM7363
			// When Consignor code is 1
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
				txt_ConsignorName.setText(param.getConsignorName());
			}
			else
			{
				txt_ConsignorCode.setText("");
				txt_ConsignorName.setText("");
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
				//#CM7364
				// Connection close
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

	//#CM7365
	/** 
	 * This method checks for the values of case and piece.<BR>
	 * <BR>
	 * Summary: Checks whether case/piece value is 0 or more.<BR>
	 * 
	 * @param figure 	  Using digit to execute value check
	 * @param name 	  Subject name to execute value check
	 * @throws Exception Reports all the exceptions.
	 * @return Return Subject name when itemName using disit and position is not 1 or more.
	 */
	private String checkNumber(NumberTextBox figure, Label name) throws Exception
	{
		String itemName = null;

		if (!StringUtil.isBlank(Integer.toString(figure.getInt())))
		{
			if (figure.getInt() < 0)
			{
				//#CM7366
				// Set the item (count) name for the itemName
				itemName = DisplayText.getText(name.getResourceKey());
				return itemName;
			}
		}
		return itemName;
	}

	//#CM7367
	/**
	 * This method sets the preset area data in the Parameter class.<BR>
	 * <BR>
	 * Summary: Sets the preset area data in the Parameter class.<BR>
	 * <BR>
	 * 1.if it is new input<BR>
	 * 2.If the input data is in process of modifying<BR>
	 * 3.Clicking on the Add button sets all the preset data in the Parameter class. (Preset line No. to be modified=-1)<BR>
	 * <DIR>
	 *   	[parameter] *Mandatory Input<BR>
	 *   	<DIR>
	 * 			Preset Line No. to be corrected* <BR>
	 * 		</DIR>
	 *   	[Returned data]<BR>
	 *   	<DIR>
	 * 			Instance array of <CODE>StockControlParameter</CODE> that has the contents of the Preset area<BR>
	 * 		</DIR>
	 * </DIR>
	 * @param lineno Line No. of the list cell
	 * @return StockControlParameter[] parameter object arry including the data of Preset area
	 * @throws Exception Reports all the exceptions. 
	 */
	private StockControlParameter[] setListParam(int lineno) throws Exception
	{

		Vector vecParam = new Vector();

		for (int i = 1; i < lst_SNoPlanStorage.getMaxRows(); i++)
		{
			//#CM7368
			// Exclude the line to be modefied.
			if (i == lineno)
			{
				continue;
			}

			//#CM7369
			// Designate the line.
			lst_SNoPlanStorage.setCurrentRow(i);

			//#CM7370
			// Set to the schedule parameter
			StockControlParameter param = new StockControlParameter();
			//#CM7371
			// Input area info
			//#CM7372
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM7373
			// Password
			param.setPassword(txt_Password.getText());
			//#CM7374
			// Terminal No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());
			//#CM7375
			// List Print Type
			param.setListFlg(chk_CommonUse.getChecked());
			//#CM7376
			// Consignor code
			param.setConsignorCode(lst_SNoPlanStorage.getValue(3));
			//#CM7377
			// Item code
			param.setItemCode(lst_SNoPlanStorage.getValue(4));
			//#CM7378
			// Case/PieceDivision
			param.setCasePieceFlag(lst_SNoPlanStorage.getValue(0));
			//#CM7379
			// Store Location
			param.setLocationNo(lst_SNoPlanStorage.getValue(6));
			//#CM7380
			// Packing qty per case
			param.setEnteringQty(Formatter.getInt(lst_SNoPlanStorage.getValue(7)));
			//#CM7381
			// Store Case qty
			param.setStorageCaseQty(Formatter.getInt(lst_SNoPlanStorage.getValue(8)));
			//#CM7382
			// Case ITF
			param.setITF(lst_SNoPlanStorage.getValue(9));
			//#CM7383
			// Expiry Date
			param.setUseByDate(lst_SNoPlanStorage.getValue(10));
			//#CM7384
			// Consignor name
			param.setConsignorName(lst_SNoPlanStorage.getValue(11));
			//#CM7385
			// Item name
			param.setItemName(lst_SNoPlanStorage.getValue(12));
			//#CM7386
			// packed qty per bundle
			param.setBundleEnteringQty(Formatter.getInt(lst_SNoPlanStorage.getValue(13)));
			//#CM7387
			// Storage Piece Qty
			param.setStoragePieceQty(Formatter.getInt(lst_SNoPlanStorage.getValue(14)));
			//#CM7388
			// Bundle ITF
			param.setBundleITF(lst_SNoPlanStorage.getValue(15));

			//#CM7389
			// Maintain the line No.
			param.setRowNo(i);

			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			//#CM7390
			// Set the value if any preset data to be set.
			StockControlParameter[] listparam = new StockControlParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			//#CM7391
			// Set null if no preset data to be set.
			return null;
		}
	}

	//#CM7392
	/**
	 * Obtain the Case/Piece division of the corresponding picking parameter from the Case/Piece division button in the input area.<BR>
	 * <BR>
	 * @return Case/Piece division
	 * @throws Exception Reports all the exceptions. 
	 */
	private String getCasePieceFlagFromInputArea() throws Exception
	{
		//#CM7393
		// Case/Piece division
		if (rdo_CpfAppointOff.getChecked())
		{
			//#CM7394
			// None
			return StockControlParameter.CASEPIECE_FLAG_NOTHING;
		}
		else if (rdo_CpfCase.getChecked())
		{
			//#CM7395
			// Case
			return StockControlParameter.CASEPIECE_FLAG_CASE;
		}
		else if (rdo_CpfPiece.getChecked())
		{
			//#CM7396
			// Piece
			return StockControlParameter.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}

	//#CM7397
	/**
	 * Obtain the corresponding Case/Piece division from the stock parameter and Case/Piece division.<BR>
	 * <BR>
	 * @param value Picking parameter, Case/Piece division that corresponds to Radio Button.
	 * @return Case/Piece division
	 * @throws Exception Reports all the exceptions.  
	 */
	private String getCasePieceFlag(String value) throws Exception
	{
		//#CM7398
		// Case/Piece division
		if (value.equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM7399
			// None
			return SystemDefine.CASEPIECE_FLAG_NOTHING;
		}
		else if (value.equals(StockControlParameter.CASEPIECE_FLAG_CASE))
		{
			//#CM7400
			// Case
			return SystemDefine.CASEPIECE_FLAG_CASE;
		}
		else if (value.equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM7401
			// Piece
			return SystemDefine.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}

	//#CM7402
	/**
	 * display the contents of the list cell division in the Case/Piece division check box of the input area.<BR>
	 * <BR>
	 * @throws Exception Reports all the exceptions. 
	 */
	private void setCasePieceRBFromList() throws Exception
	{
		if (lst_SNoPlanStorage
			.getValue(0)
			.equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM7403
			// Place a check in None option.
			rdo_CpfAppointOff.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(false);
		}
		else if (
			lst_SNoPlanStorage.getValue(0).equals(
			StockControlParameter.CASEPIECE_FLAG_CASE))
		{
			//#CM7404
			// Check on Case
			rdo_CpfCase.setChecked(true);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);
		}
		else if (
			lst_SNoPlanStorage.getValue(0).equals(
			StockControlParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM7405
			// Check on Piece
			rdo_CpfPiece.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);
		}
	}

	//#CM7406
	/**
	 * This method sets the value in the preset area.<BR>
	 * <BR>
	 * Summary: Sets value for preset area<BR>
	 * @throws Exception Reports all the exceptions. 
	 */
	private void setList() throws Exception
	{
		//#CM7407
		// Compile the data for ToolTip
		ToolTipHelper toolTip = new ToolTipHelper();
		//#CM7408
		// Consignor name
		toolTip.add(DisplayText.getText("LBL-W0026"), txt_ConsignorName.getText());
		//#CM7409
		// Item name
		toolTip.add(DisplayText.getText("LBL-W0103"), txt_ItemName.getText());
		//#CM7410
		// Case ITF
		toolTip.add(DisplayText.getText("LBL-W0010"), txt_CaseItf.getText());
		//#CM7411
		// Bundle ITF
		toolTip.add(DisplayText.getText("LBL-W0006"), txt_BundleItf.getText());
		//#CM7412
		// Expiry Date
		toolTip.add(DisplayText.getText("LBL-W0270"), txt_UseByDate.getText());

		//#CM7413
		// Set ToolTip to current line
		lst_SNoPlanStorage.setToolTip(lst_SNoPlanStorage.getCurrentRow(), toolTip.getText());

		//#CM7414
		// Case/PieceDivision(hidden item (count)
		lst_SNoPlanStorage.setValue(0, getCasePieceFlagFromInputArea());
		//#CM7415
		// Consignor code
		lst_SNoPlanStorage.setValue(3, txt_ConsignorCode.getText());
		//#CM7416
		// Item code
		lst_SNoPlanStorage.setValue(4, txt_ItemCode.getText());
		//#CM7417
		// Division
		lst_SNoPlanStorage.setValue(5,
			DisplayUtil.getPieceCaseValue(getCasePieceFlag(getCasePieceFlagFromInputArea())));
		//#CM7418
		// Store Location
		lst_SNoPlanStorage.setValue(6, txt_StorageLocation.getText());
		//#CM7419
		// Packing qty per case
		if (StringUtil.isBlank(txt_CaseEntering.getText()))
		{
			lst_SNoPlanStorage.setValue(7, "0");
		}
		else
		{
			lst_SNoPlanStorage.setValue(7, WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));

		}
		//#CM7420
		// Store Case qty
		if (StringUtil.isBlank(txt_StrgCaseQty.getText()))
		{
			lst_SNoPlanStorage.setValue(8, "0");
		}
		else
		{
			lst_SNoPlanStorage.setValue(8, WmsFormatter.getNumFormat(txt_StrgCaseQty.getInt()));
		}
		//#CM7421
		// Case ITF
		lst_SNoPlanStorage.setValue(9, txt_CaseItf.getText());
		//#CM7422
		// Expiry Date
		lst_SNoPlanStorage.setValue(10, txt_UseByDate.getText());
		//#CM7423
		// Consignor name
		lst_SNoPlanStorage.setValue(11, txt_ConsignorName.getText());
		//#CM7424
		// Item name
		lst_SNoPlanStorage.setValue(12, txt_ItemName.getText());
		//#CM7425
		// packed qty per bundle
		if (StringUtil.isBlank(txt_BundleEntering.getText()))
		{
			lst_SNoPlanStorage.setValue(13, "0");
		}
		else
		{
			lst_SNoPlanStorage.setValue(13, WmsFormatter.getNumFormat(txt_BundleEntering.getInt()));
		}
		//#CM7426
		// Storage Piece Qty
		if (StringUtil.isBlank(txt_StrgPieseQty.getText()))
		{
			lst_SNoPlanStorage.setValue(14, "0");
		}
		else
		{
			lst_SNoPlanStorage.setValue(14, WmsFormatter.getNumFormat(txt_StrgPieseQty.getInt()));
		}
		//#CM7427
		// Bundle ITF
		lst_SNoPlanStorage.setValue(15, txt_BundleItf.getText());
	}

	//#CM7428
	// Event handler methods -----------------------------------------
	//#CM7429
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7430
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7431
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7432
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

	//#CM7433
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7434
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7435
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7436
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7437
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7438
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7439
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7440
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7441
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7442
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7443
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7444
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7445
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7446
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7447
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7448
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7449
	/** 
	 * Clicking on the Search Consignor Code button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displays the Consignor list listbox using the search conditions.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] <BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_PSearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		//#CM7450
		// Set the search condition for the Search Consignor screen.
		ForwardParameters param = new ForwardParameters();
		//#CM7451
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM7452
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM7453
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststockconsignor/ListStockConsignor.do",
			param,
			"/progress.do");
	}

	//#CM7454
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7455
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7456
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7457
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7458
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7459
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7460
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7461
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7462
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7463
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7464
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItem_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7465
	/** 
	 * Clicking on the Search Item Code button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displays the item list listbox using the search conditions.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] *Mandatory Input<BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *       Item code <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_PSearchItem_Click(ActionEvent e) throws Exception
	{
		//#CM7466
		// Set the search conditions for the Search Item screen.
		ForwardParameters param = new ForwardParameters();
		//#CM7467
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM7468
		// Item code
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM7469
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM7470
		// In process screen->Result screen 
		redirect("/stockcontrol/listbox/liststockitem/ListStockItem.do", param, "/progress.do");
	}

	//#CM7471
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PStockSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7472
	/** 
	 * Clicking on the Search Stock button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displays the stock inquiry list listbox using the search conditions.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] *Mandatory Input<BR>
	 *    <DIR>
	 *       Consignor code* <BR>
	 *       Item code <BR>
	 *       Case/PieceDivision <BR>
	 *       Store Location <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_PStockSearch_Click(ActionEvent e) throws Exception
	{
		//#CM7473
		// Set the search conditions for the Search Stock screen.
		ForwardParameters param = new ForwardParameters();
		
		//#CM7474
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		//#CM7475
		// Item code
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		
		//#CM7476
		// Case/PieceDivision
		//#CM7477
		// Case
		if (rdo_CpfCase.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM7478
		// Piece
		else if (rdo_CpfPiece.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM7479
		// None
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_NOTHING);
		}
		
		//#CM7480
		// Store Location
		param.setParameter(ListStockLocationBusiness.LOCATION_KEY, txt_StorageLocation.getText());

		//#CM7481
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM7482
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststockmaintenance/ListStockMaintenance.do",
			param,
			"/progress.do");
	}

	//#CM7483
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7484
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7485
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7486
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7487
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7488
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7489
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7490
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CasePieceFlag_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7491
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CasePieceFlag_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7492
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FlagValueCasePiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7493
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7494
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7495
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7496
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7497
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7498
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7499
	/** 
	 * Clicking on the Search Storage Location button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displays the location list listbox using the search conditions.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] <BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *       Item code <BR>
	 *       Case/PieceDivision <BR>
	 *       Store Location <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_PSearchStrg_Click(ActionEvent e) throws Exception
	{
		//#CM7500
		// Set the search condition for the Search Location screen.
		ForwardParameters param = new ForwardParameters();
		//#CM7501
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM7502
		// Item code
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM7503
		// Case
		if (rdo_CpfCase.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM7504
		// Piece
		else if (rdo_CpfPiece.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM7505
		// None
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM7506
		// Store Location
		param.setParameter(ListStockLocationBusiness.LOCATION_KEY, txt_StorageLocation.getText());
		//#CM7507
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM7508
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststocklocation/ListStockLocation.do",
			param,
			"/progress.do");
	}

	//#CM7509
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7510
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7511
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7512
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7513
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrageCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7514
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7515
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgCaseQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7516
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgCaseQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7517
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7518
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7519
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7520
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7521
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7522
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7523
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7524
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7525
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7526
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StragePieseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7527
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPieseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7528
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPieseQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7529
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPieseQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7530
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7531
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7532
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7533
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7534
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7535
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7536
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7537
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7538
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7539
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7540
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Unschstwoli_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7541
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7542
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Change(ActionEvent e) throws Exception
	{
	}

	//#CM7543
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Input_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7544
	/**
	 * Clicking on the Input button invokes this.<BR>
	 * <BR>
	 * Summary: Checks the input item (count) in the input area and displays it in the preset area.<BR>
	 * <BR>
	 * <DIR>
	 *   1.Set the cursor to the worker code.<BR>
	 *   2.Check input field of input area (mandatory input)<BR>
	 *   3.Start schedule<BR>
	 * 	 <DIR>
	 *   	[parameter] *Mandatory Input<BR>
	 *   	<DIR>
	 * 			Worker Code* <BR>
	 * 			Password* <BR>
	 * 			Consignor code* <BR>
	 * 			Consignor name <BR>
	 * 			Item code* <BR>
	 * 			Item name <BR>
	 * 			Case/PieceDivision* <BR>
	 * 			Store Location* <BR>
	 * 			Packing qty per case*2 <BR>
	 * 			Store Case qty*2 *4 <BR>
	 * 			Case ITF <BR>
	 * 			packed qty per bundle <BR>
	 *			Storage Piece Qty*3 *4 <BR>
	 *			Bundle ITF <BR>
	 *			Expiry Date <BR>
	 *			Prints Unplanned storage list.<BR>
	 *		<BR>
	 * 			*2 <BR>
	 * 			When Case/PieceDivision is 1:Case, Mandatory Input <BR>
	 * 			*3 <BR>
	 * 			When Case/PieceDivision is 2:Piece, Mandatory Input <BR>
	 * 			*4 <BR>
	 * 			When Case/PieceDivision is 0:None, <BR>
	 *			storage case qty <BR>
	 * 			case qty x packed qty per case+ piece qty should not be 0(>0) <BR>
	 *	 	</DIR>
	 *   </DIR>
	 *   <BR>
	 *   4.Add to the preset area if the schedule result is true.<BR>
	 *     Clicking on the Input button after clicking the Modify button updates the target preset data to be modified with the info in the input area.<BR>
	 *   5.If the schedule result is trueEnable the Enable List Clear button.<BR>
	 *   6.If the schedule result is true, Set the default (initial/default value:-1) for the preset line No. of ViewState.<BR>
	 *   7.Output the message obtained from the schedule to the screen.<BR>
	 * 	 8.Maintain the contents of the input area is still maintained.<BR>
	 *   <BR>
	 * 	 Line No. list of the list cell<BR>
	 *   <DIR>
	 * 		3.Consignor code <BR>
	 * 		4.Item code <BR>
	 * 		5.Division <BR>
	 * 		6.Store Location <BR>
	 * 		7.Packing qty per case <BR>
	 * 		8.Store Case qty <BR>
	 * 		9.Case ITF <BR>
	 * 		10.Expiry Date <BR>
	 * 		11.Consignor name <BR>
	 * 		12.Item name <BR>
	 * 		13.packed qty per bundle <BR>
	 * 		14.Storage Piece Qty <BR>
	 * 		15.Bundle ITF <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		//#CM7545
		// Set the cursor to the worker code
		setFocus(txt_WorkerCode);

		//#CM7546
		// mandatory input check
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		txt_ItemCode.validate();
		txt_StorageLocation.validate();

		//#CM7547
		// Pattern matching characters
		txt_ConsignorName.validate(false);
		txt_ItemName.validate(false);
		txt_CaseEntering.validate(false);
		txt_StrgCaseQty.validate(false);
		txt_CaseItf.validate(false);
		txt_BundleEntering.validate(false);
		txt_StrgPieseQty.validate(false);
		txt_BundleItf.validate(false);
		txt_UseByDate.validate(false);

		String itemname = null;

		itemname = checkNumber(txt_CaseEntering, lbl_CaseEntering);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_BundleEntering, lbl_BundleEntering);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_StrgCaseQty, lbl_StrageCaseQty);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_StrgPieseQty, lbl_StragePieseQty);

		if (!StringUtil.isBlank(itemname))
		{
			//#CM7548
			// Display the error message and close it.
			//#CM7549
			// 6023057=Please enter {1} or greater for {0}.
			message.setMsgResourceKey("6023057" + wDelim + itemname + wDelim + "0");
			return;
		}

		//#CM7550
		// Input characters check for eWareNavi
		if (!checkContainNgText())
		{
			return;
		}

		Connection conn = null;

		try
		{
			//#CM7551
			// Set to the schedule parameter
			//#CM7552
			// Input area
			StockControlParameter param = new StockControlParameter();
			//#CM7553
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM7554
			// Password
			param.setPassword(txt_Password.getText());
			//#CM7555
			// Consignor code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM7556
			// Consignor name
			param.setConsignorName(txt_ConsignorName.getText());
			//#CM7557
			// Item code
			param.setItemCode(txt_ItemCode.getText());
			//#CM7558
			// Item name
			param.setItemName(txt_ItemName.getText());
			//#CM7559
			// Case/Piece division
			param.setCasePieceFlag(getCasePieceFlagFromInputArea());
			//#CM7560
			// Store Location
			param.setLocationNo(txt_StorageLocation.getText());
			//#CM7561
			// Packing qty per case
			param.setEnteringQty(txt_CaseEntering.getInt());
			//#CM7562
			// Store Case qty
			param.setStorageCaseQty(txt_StrgCaseQty.getInt());
			//#CM7563
			// Case ITF
			param.setITF(txt_CaseItf.getText());
			//#CM7564
			// packed qty per bundle
			param.setBundleEnteringQty(txt_BundleEntering.getInt());
			//#CM7565
			// Storage Piece Qty
			param.setStoragePieceQty(txt_StrgPieseQty.getInt());
			//#CM7566
			// Bundle ITF
			param.setBundleITF(txt_BundleItf.getText());
			//#CM7567
			// Expiry Date
			param.setUseByDate(txt_UseByDate.getText());
			//#CM7568
			// Prints Unplanned storage list.
			param.setListFlg(chk_CommonUse.getChecked());

			//#CM7569
			// Set to the schedule parameter
			//#CM7570
			// Preset area
			StockControlParameter[] listparam = null;

			//#CM7571
			// Set null if no data in the preset area.
			if (lst_SNoPlanStorage.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				//#CM7572
				// Set the value
				listparam = setListParam(this.getViewState().getInt("LineNo"));
			}

			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new NoPlanStorageSCH();

			if (schedule.check(conn, param, listparam))
			{
				//#CM7573
				// Set the data for the preset area if the result is true.
				if (this.getViewState().getInt("LineNo") == -1)
				{
					//#CM7574
					// if it is new input
					lst_SNoPlanStorage.addRow();
					lst_SNoPlanStorage.setCurrentRow(lst_SNoPlanStorage.getMaxRows() - 1);
					setList();
				}
				else
				{
					//#CM7575
					// if it is input data under modifying
					lst_SNoPlanStorage.setCurrentRow(this.getViewState().getInt("LineNo"));
					setList();
					//#CM7576
					// Return selected line color to source
					lst_SNoPlanStorage.resetHighlight();
				}

				//#CM7577
				// Return modify status to default
				this.getViewState().setInt("LineNo", -1);

				//#CM7578
				// Enable to click a button.
				//#CM7579
				// Start Storage button
				btn_StorageStart.setEnabled(true);
				//#CM7580
				// List clear button
				btn_ListClear.setEnabled(true);

			}

			//#CM7581
			// Set the message
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM7582
				// Connection close
				if (conn != null)
				    conn.rollback();
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM7583
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7584
	/**
	 * Clicking on the Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area.<BR>
	 * <BR>
	 * <DIR>
	 *    1.Return the item (count) in the input area to the initial value.<BR>
	 *    <DIR>
	 *  Refer to <CODE>page_Load(ActionEvent e)</CODE>Method in regard with the initial value.<BR>
	 *    </DIR>
	 *    2.Set the cursor to the worker code.<BR>
	 *    3.Maintain the contents of preset area.<BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM7585
		// Execute the Clear process.
		setFirstDisp();
	}

	//#CM7586
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_StorageStart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7587
	/**
	 * Clicking on the Start Storage button invokes this.<BR>
	 * <BR>
	 * Summary:Execute setting of Unplanned storage based on the Preset area info.<BR>
	 * <BR>
	 * <DIR>
	 *	  1.Set the cursor to the worker code.<BR>
	 *    2.Display the dialog box to allow to confirm to start storage or not.<BR>
	 *    <DIR>
	 *      "Do you set it?"<BR>
	 * 		[Check dialog cancel]<BR>
	 *			<DIR>
	 *				Disable
	 *			</DIR>
	 * 		[Check dialog OK]<BR>
	 *			<DIR>
	 *				1.Start the schedule.<BR>
	 *				<DIR>
	 *   				[parameter]<BR>
	 * 					<DIR>
	 * 						Worker Code<BR>
	 * 						Password<BR>
	 * 						Terminal No.<BR>
	 * 						List Print Type<BR>
	 * 						Preset.Consignor code <BR>
	 * 						Preset.Consignor name <BR>
	 *						Preset.Item code <BR>
	 *						Preset.Item name <BR>
	 *						Preset.Division <BR>
	 *						Preset.Store Location <BR>
	 *						Preset.Packing qty per case <BR>
	 *						Preset.packed qty per bundle <BR>
	 *						Preset.Store Case qty <BR>
	 *						Preset.Storage Piece Qty <BR>
	 *						Preset.Case ITF <BR>
	 *						Preset.Bundle ITF <BR>
	 *						Preset.Expiry Date <BR>
	 *						Preset Line No.<BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.When the result of the schedule is true, clear adding info of preset in the input area<BR>
	 *				3.Cancel the Modify status. ( Set default:-1 for the ViewState preset line No.)<BR>
	 *    			Output the message obtained from the schedule to the screen<BR>
	 *			</DIR>
	 *    </DIR>
	 * <BR>
	 * 	 Line No. list of the list cell<BR>
	 *   <DIR>
	 * 		3.Consignor code <BR>
	 * 		4.Item code <BR>
	 * 		5.Division <BR>
	 * 		6.Store Location <BR>
	 * 		7.Packing qty per case <BR>
	 * 		8.Store Case qty <BR>
	 * 		9.Case ITF <BR>
	 * 		10.Expiry Date <BR>
	 * 		11.Consignor name <BR>
	 * 		12.Item name <BR>
	 * 		13.packed qty per bundle <BR>
	 * 		14.Storage Piece Qty <BR>
	 * 		15.Bundle ITF <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_StorageStart_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM7588
			// Set the cursor to the worker code
			setFocus(txt_WorkerCode);

			//#CM7589
			// Set to the schedule parameter
			StockControlParameter[] param = null;
			//#CM7590
			// Set all the data in the preset area.
			param = setListParam(-1);

			//#CM7591
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new NoPlanStorageSCH();

			//#CM7592
			// Start schedule
			if (schedule.startSCH(conn, param))
			{
				//#CM7593
				// if result is true
				conn.commit();
				//#CM7594
				// Set the message
				message.setMsgResourceKey(schedule.getMessage());

				//#CM7595
				// List clear
				lst_SNoPlanStorage.clearRow();

				//#CM7596
				// Return modify status to default
				this.getViewState().setInt("LineNo", -1);

				//#CM7597
				// Disable button press down
				//#CM7598
				// Start Storage button
				btn_StorageStart.setEnabled(false);
				//#CM7599
				// List clear button
				btn_ListClear.setEnabled(false);
			}
			else
			{
				conn.rollback();
				//#CM7600
				// Set the message
				message.setMsgResourceKey(schedule.getMessage());
			}

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
				//#CM7601
				// Connection close
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

	//#CM7602
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7603
	/**
	 * Clicking on the List Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clear all the display info of preset<BR>
	 * <BR>
	 * <DIR>
	 *    1.Display the dialog box to allow to confirm to check the preset info.<BR>
	 *    <DIR>
	 * 	    "This clears the list input info. Do you confirm it?"<BR>
	 * 		[Check dialog cancel]<BR>
	 *			<DIR>
	 *				Disable
	 *			</DIR>
	 * 		[Check dialog OK]<BR>
	 *			<DIR>
	 *				1.Clear all the display info of preset<BR>
	 *				2.Revoke add button, List clear button<BR>
	 *				3.Clear items of input area all together.<BR>
	 * 				4.Set the default (initial/default value:-1) for the preset line No. of ViewState.<BR>
	 *				5.Set the cursor to the worker code.<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		//#CM7604
		// Delete all lines
		lst_SNoPlanStorage.clearRow();

		//#CM7605
		// Disable button press down
		//#CM7606
		// Start Storage button
		btn_StorageStart.setEnabled(false);
		//#CM7607
		// List clear button
		btn_ListClear.setEnabled(false);

		//#CM7608
		// Return modify status to default
		this.getViewState().setInt("LineNo", -1);

		//#CM7609
		// Set the cursor to the worker code
		setFocus(txt_WorkerCode);
	}

	//#CM7610
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanStorage_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7611
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanStorage_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7612
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanStorage_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7613
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanStorage_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM7614
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanStorage_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7615
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanStorage_Change(ActionEvent e) throws Exception
	{
	}

	//#CM7616
	/**
	 * Clicking on the Cancel or Modify button invokes this.<BR>
	 * <BR>
	 * Summary of Cancel button : Clears the corresponding data in the preset area.<BR>
	 * <BR>
	 * <DIR>
	 *    1.Display the dialog box to allow to confirm to check the preset info.<BR>
	 *    <DIR>
	 *      "Do you cancel it?"<BR>
	 * 		[Check dialog cancel]<BR>
	 *			<DIR>
	 *				Disable
	 *			</DIR>
	 * 		[Check dialog OK]<BR>
	 *			<DIR>
	 *				1.Clear corresponding data of preset<BR>
	 * 				2.Set the default (initial/default value:-1) for the preset line No. of ViewState.<BR>
	 *              3.When no Preset info is found, disable the Add button/List Clear button.<BR>
	 *				4.Set the cursor to the worker code.<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * <BR>
	 * Summary of Modify button: Change the status of the corresponding data in the preset area to Modify.<BR>
	 * <BR>
	 * <DIR>
	 *    1.Displaies the selected info in the upper input area.<BR>
	 *    2.Change the color of Selected info section to light yellow.<BR>
	 *    3.Set the current line for the preset line No. of ViewState.
	 *    4.Set the cursor to the worker code.<BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void lst_SNoPlanStorage_Click(ActionEvent e) throws Exception
	{
		//#CM7617
		// when clicking on the Cancel button
		if (lst_SNoPlanStorage.getActiveCol() == 1)
		{
			//#CM7618
			// Delete list
			lst_SNoPlanStorage.removeRow(lst_SNoPlanStorage.getActiveRow());

			//#CM7619
			// When no Preset info is found, disable the Add button/List Clear button.
			//#CM7620
			// Set null if no data in the preset area.
			if (lst_SNoPlanStorage.getMaxRows() == 1)
			{
				//#CM7621
				// Disable button press down
				//#CM7622
				// Start Storage button
				btn_StorageStart.setEnabled(false);
				//#CM7623
				// List clear button
				btn_ListClear.setEnabled(false);
			}

			//#CM7624
			// Return modify status to default
			this.getViewState().setInt("LineNo", -1);

			//#CM7625
			// Return selected line color to source
			lst_SNoPlanStorage.resetHighlight();

			//#CM7626
			// Set the cursor to the worker code
			setFocus(txt_WorkerCode);

		}
		//#CM7627
		// When correction button is clicked (Executing modify operation)
		else if (lst_SNoPlanStorage.getActiveCol() == 2)
		{
			//#CM7628
			// Set the current line.
			lst_SNoPlanStorage.setCurrentRow(lst_SNoPlanStorage.getActiveRow());
			//#CM7629
			// Consignor code
			txt_ConsignorCode.setText(lst_SNoPlanStorage.getValue(3));
			//#CM7630
			// Item code
			txt_ItemCode.setText(lst_SNoPlanStorage.getValue(4));
			//#CM7631
			// Case/PieceDivision
			setCasePieceRBFromList();
			//#CM7632
			// Store Location
			txt_StorageLocation.setText(lst_SNoPlanStorage.getValue(6));
			//#CM7633
			// Packing qty per case
			txt_CaseEntering.setText(lst_SNoPlanStorage.getValue(7));
			//#CM7634
			// Store Case qty
			txt_StrgCaseQty.setText(lst_SNoPlanStorage.getValue(8));
			//#CM7635
			// Case ITF
			txt_CaseItf.setText(lst_SNoPlanStorage.getValue(9));
			//#CM7636
			// Expiry Date
			txt_UseByDate.setText(lst_SNoPlanStorage.getValue(10));
			//#CM7637
			// Consignor name
			txt_ConsignorName.setText(lst_SNoPlanStorage.getValue(11));
			//#CM7638
			// Item name
			txt_ItemName.setText(lst_SNoPlanStorage.getValue(12));
			//#CM7639
			// packed qty per bundle
			txt_BundleEntering.setText(lst_SNoPlanStorage.getValue(13));
			//#CM7640
			// Storage Piece Qty
			txt_StrgPieseQty.setText(lst_SNoPlanStorage.getValue(14));
			//#CM7641
			// Bundle ITF
			txt_BundleItf.setText(lst_SNoPlanStorage.getValue(15));

			//#CM7642
			// Store to ViewState
			//#CM7643
			// Set the a preset line No. in ViewState to determine the status being in Modify by clicking the Modify button.
			this.getViewState().setInt("LineNo", lst_SNoPlanStorage.getActiveRow());

			//#CM7644
			// Change the color of the modified lineto yellow.
			lst_SNoPlanStorage.setHighlight(lst_SNoPlanStorage.getActiveRow());

			//#CM7645
			// Set the cursor to the worker code
			setFocus(txt_WorkerCode);
		}
	}
}
//#CM7646
//end of class
