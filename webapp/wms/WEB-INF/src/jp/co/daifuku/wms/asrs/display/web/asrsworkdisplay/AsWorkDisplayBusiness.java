// $Id: AsWorkDisplayBusiness.java,v 1.2 2006/10/26 04:57:31 suresh Exp $

//#CM37545
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsworkdisplay;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.StringTokenizer;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstockdetailnobtn.ListAsStockDetailNoBtnBusiness;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.asrs.schedule.AsWorkDisplaySCH;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.PulldownHelper;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;

//#CM37546
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * The ASRS work instruction screen class. <BR>
 * set the input contents from the screen to a parameter
 * The schedule does completion and disbursement completion Process based on the parameter. <BR>
 * Receive true when the result is received from the schedule, and processing is completed normally. 
 * Receive false when it does not complete the schedule because of the condition error etc.<BR>
 * output the schedule result, schedule message to screen<BR>
 * <BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.Process when End button is pressed (<CODE>btn_Complete_Click</CODE>method ) <BR>
 * <BR>
 * <DIR>
 *  The content input from input area is set in the parameter, and the schedule completes it based on the parameter Process. <BR>
 *  Receive true when the result is received from the schedule, and processing is completed normally. 
 *  Receive false when it does not complete the schedule because of the condition error etc.<BR>
 *  output the schedule result, schedule message to screen<BR>
 * 	<DIR>
 *   [parameter] *mandatory input <BR>
 *   <BR>
 * 		worker code<BR>
 * 		password<BR>
 * 		station no.<BR>
 * 		ViewState.conveyance key<BR>
 * 		Push button:End button<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.Process when Expend Complete button is pressed (<CODE>btn_ExpendComplete_Click</CODE>method ) <BR>
 * <BR>
 * <DIR>
 *  It sets from input area in the parameter of the input content, and the schedule disbursement completes it based on the parameter Process. <BR>
 *  Receive true when the result is received from the schedule, and processing is completed normally. 
 *  Receive false when it does not complete the schedule because of the condition error etc.<BR>
 *  output the schedule result, schedule message to screen<BR>
 * 	<DIR>
 *   [parameter] *mandatory input <BR>
 *   <BR>
 * 		worker code<BR>
 * 		password<BR>
 * 		station no.<BR>
 * 		ViewState.conveyance key<BR>
 * 		Push button:Force retrieval End button<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/16</TD><TD>Y.Okamura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:57:31 $
 * @author  $Author: suresh $
 */
public class AsWorkDisplayBusiness extends AsWorkDisplay implements WMSConstants
{
	//#CM37547
	// Class fields --------------------------------------------------
	//#CM37548
	// list cell line definition(L_XXX)
	//#CM37549
	// first step
	//#CM37550
	/**
	 * list cell line definition:line no.
	 */
	protected final int L_ROW_NO = 1;
	//#CM37551
	/**
	 * list cell line definition:consignor code
	 */
	protected final int L_CONSIGNOR_CODE = 2;
	//#CM37552
	/**
	 * list cell line definition:item code
	 */
	protected final int L_ITEM_CODE = 3;
	//#CM37553
	/**
	 * list cell line definition:packed qty per case
	 */
	protected final int L_ENTERING_QTY = 4;
	//#CM37554
	/**
	 * list cell line definition:stock case qty
	 */
	protected final int L_STOCK_CASE_QTY = 5;
	//#CM37555
	/**
	 * list cell line definition:plan case qty
	 */
	protected final int L_WORK_CASE_QTY = 6;
	//#CM37556
	/**
	 * list cell line definition: Case/Piece flag
	 */
	protected final int L_CASE_PIECE_FLAG = 7;
	//#CM37557
	/**
	 * list cell line definition:storage date
	 */
	protected final int L_INSTOCK_DATE = 8;
	//#CM37558
	// The second step
	//#CM37559
	/**
	 * list cell line definition:consignor name
	 */
	protected final int L_CONSIGNOR_NAME = 9;
	//#CM37560
	/**
	 * list cell line definition:item name
	 */
	protected final int L_ITEM_NAME = 10;
	//#CM37561
	/**
	 * list cell line definition:packed qty per piece
	 */
	protected final int L_BUNDLE_ENTERING_QTY = 11;
	//#CM37562
	/**
	 * list cell line definition:stock piece qty
	 */
	protected final int L_STOCK_PIECE_QTY = 12;
	//#CM37563
	/**
	 * list cell line definition:plan piece qty
	 */
	protected final int L_WORK_PIECE_QTY = 13;
	//#CM37564
	/**
	 * list cell line definition:storage time
	 */
	protected final int L_INSTOCK_TIME = 14;
	
	//#CM37565
	// type of date format
	//#CM37566
	/**
	 * type of date format:Edit date(Example:YYYY/MM/DD)
	 */
	protected final int DATE_DISPTYPE_DATE = 0;
	
	//#CM37567
	/**
	 * type of date format:Edit time(Example:hh:mm:ss)
	 */
	protected final int DATE_DISPTYPE_TIME = 1;
	
	//#CM37568
	// Constant for ViewState maintenance
	//#CM37569
	/**
	 * Constant for ViewState maintenance:conveyance key
	 */
	protected final String V_CARRY_KEY = "V_CARRY_KEY";

	//#CM37570
	/**
	 * Constant for ViewState maintenance:location no.
	 */
	protected final String V_LOCATION_KEY = "V_LOCATION_KEY";

	//#CM37571
	/**
	 * Constant for ViewState maintenance:warehouse station no..
	 */
	protected final String V_WH_STNO_KEY = "V_WH_STNO_KEY";

	//#CM37572
	// Class variables -----------------------------------------------

	//#CM37573
	// Class method --------------------------------------------------

	//#CM37574
	// Constructors --------------------------------------------------

	//#CM37575
	// Public methods ------------------------------------------------

	//#CM37576
	/**
	 * screen initialization<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * <BR>
	 * <DIR>
	 *    	1.set cursor in worker code<BR>
	 *		2.initialize input area<BR>
	 *    	3.initialize pulldown <BR>
	 *    	4.Display work instruction information. <BR>
	 * 		<DIR>
	 * 			item[initial value] <BR>
	 * 			<BR>
	 * 			<DIR>
	 * 				worker code[nil] <BR>
	 * 				password[nil] <BR>
	 * 				job no..[nil] <BR>
	 * 				work type[nil] <BR>
	 * 				picking instruction detail[nil] <BR>
	 * 				location[nil] <BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM37577
		// initialize screen display
		setInitDsp();

		//#CM37578
		// connection
		Connection conn = null;
		
		try
		{
			//#CM37579
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

			PulldownData pull = new PulldownData(this.getHttpRequest().getLocale(), userHandler.getTerminalNo());

			//#CM37580
			// edit and set station pulldown
			String[] stnoArray = pull.getStationPulldownData(conn, PulldownData.SATAION_WORK_DISPLAY, false, "");
			if (stnoArray != null && stnoArray.length != 0)
			{
				//#CM37581
				// set pulldown data
				PulldownHelper.setPullDown(pul_StationNo, stnoArray);
				
				//#CM37582
				// Acquire the 0th information on station information (station No.). 
				StringTokenizer stk = new StringTokenizer(stnoArray[0], PulldownData.PDDELIM, false) ;	
				String stno =  stk.nextToken() ;

				//#CM37583
				// Do Retrieval from station no, acquire work display information, and display it. 
				if (!setOperationDisplay(conn, stno))
				
				//#CM37584
				// Do not display the message when it changes the screen first time. 
				message.setMsgResourceKey("");
			}
			else
			{
				//#CM37585
				// Make Pulldown disabled when there is no correspondence station. 
				pul_StationNo.setEnabled(false);
			}
			
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM37586
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM37587
	/**
	 * call this before calling the respective control events<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		
		//#CM37588
		// 	fetch parameter
		String wMenuParam = this.getHttpRequest().getParameter(MENUPARAM);
		
		if (wMenuParam != null)
		{
			//#CM37589
			// screen name
			String wTitle = CollectionUtils.getMenuParam(0, wMenuParam);
			//#CM37590
			// function ID
			String wFunctionID = CollectionUtils.getMenuParam(1, wMenuParam);
			//#CM37591
			// menu ID
			String wMenuID = CollectionUtils.getMenuParam(2, wMenuParam);
			
			//#CM37592
			// save the retrieved parameter in viewstate
			this.getViewState().setString(M_TITLE_KEY, wTitle);
			this.getViewState().setString(M_FUNCTIONID_KEY, wFunctionID);
			this.getViewState().setString(M_MENUID_KEY, wMenuID);
			
			//#CM37593
			//set screen name
			lbl_SettingName.setResourceKey(wTitle);
			
		}
	}

	//#CM37594
	/**
	 * This method is called when returning from popup window<BR>
	 * override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE><BR>
	 * <BR>
	 * overview ::set the cursor in worker code<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		//#CM37595
		// Set focus. 
		setFocus(txt_WorkerCode);
		
	}

	//#CM37596
	// Package methods -----------------------------------------------

	//#CM37597
	// Protected methods ---------------------------------------------
	//#CM37598
	/** 
	 * Initialise the Input area and filtering area. <BR>
	 * <BR>
	 * overview ::initial screen display <BR>
	 * and Set the cursor in the worker code, and make shelf details, completion, and disbursement End button Invalid. <BR>
	 * <DIR>
	 * 		[parameter] <BR>
	 * 		<DIR>
	 * 			item name[initial value] <BR>
	 * 			<DIR>
	 * 				job no..[nil] <BR>
	 * 				work type[nil]<BR>
	 * 				Detailed work instruction[nil] <BR>
	 * 				location[nil] <BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * @throws Exception report all the exceptions
	 */
	protected void setInitDsp() throws Exception
	{
		
		//#CM37599
		// connection
		try
		{
			//#CM37600
			// set focus
			setFocus(txt_WorkerCode);
			
			//#CM37601
			// clear text
			txt_RWorkNo.setText("");
			txt_RWorkType.setText("");
			txt_RRetrievalDetail.setText("");
			txt_FRLocation.setText("");
			
			//#CM37602
			// clear preset area
			lst_WorkDisplay.clearRow();
			
			//#CM37603
			// The location details, completion and disbursement completion are made unuseable.
			btn_LocationDetails.setEnabled(false);
			btn_Complete.setEnabled(false);
			btn_ExpendComplete.setEnabled(false);
			
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}

	}
	
	//#CM37604
	/**
	 * Display work information displayed on the screen if the Retrieval display data exist. <BR>
	 * <BR>
	 * overview ::Acquire work information and display it. <BR>
	 * <DIR>
	 * 		[parameter] <BR>
	 * 		<DIR>
	 * 			station no.<BR>
	 * 			worker code<BR>
	 * 			password<BR>
	 * 		</DIR>
	 * </DIR>
	 * @param conn database connection
	 * @param pStno Station No. which has been selected from Pulldown
	 * @return Return true when you display the message and return false otherwise. 
	 * @throws Exception It is notified when the exception is generated. 
	 */
	protected boolean setOperationDisplay(Connection conn, String pStno) throws Exception
	{
		//#CM37605
		// Set Search condition and the parameter, and execute the Retrieval schedule. 
		AsScheduleParameter searchParam = new AsScheduleParameter();
		searchParam.setStationNo(pStno);
		searchParam.setWorkerCode(txt_WorkerCode.getText());
		searchParam.setPassword(txt_Password.getText());
		
		WmsScheduler schedule = new AsWorkDisplaySCH();
		AsScheduleParameter[] viewParam = (AsScheduleParameter[])schedule.query(conn, searchParam);
		
		if (schedule.getMessage() != null)
		{
			//#CM37606
			// set message
			message.setMsgResourceKey(schedule.getMessage());
		}			

		//#CM37607
		// if input area exists
		if(viewParam == null)
		{
			return true;
		}
		//#CM37608
		// End Process when there is no display data. 
		else if (viewParam.length == 0)
		{
			//#CM37609
			// return in case of initial display
			setInitDsp();
			return false;
		}

		//#CM37610
		// display input area, preset area
		setDisplayData(viewParam);
		
		//#CM37611
		// set button property
		setButtonCondition(viewParam[0]);
		
		return true;
			
	}
	
	//#CM37612
	/**
	 * Set information on the parameter on the screen. <BR>
	 * <BR>
	 * Abstract :Clear the filtering area, set first data in input area 
	 * and others in filtering area.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the display data in input area. <BR>
	 * 		2.Maintain ViewState information. <BR>
	 * 		3.Edit data for the balloon display. <BR>
	 * 		4.Set data in the every particular item of the list cell. <BR>
	 * </DIR>
	 * <DIR>
	 * 		[list cell line no. list]
	 * 		<DIR>
	 * 			1.line no.<BR>
	 * 			2.consignor code <BR>
	 * 			3.item code <BR>
	 * 			4.packed qty per case <BR>
	 * 			5.stock case qty <BR>
	 * 			6.plan case qty<BR>
	 * 			7.classification <BR>
	 * 			8.storage date <BR>
	 * 			9.consignor name <BR>
	 * 			10.item name <BR>
	 * 			11.packed qty per piece <BR>
	 * 			12.stock piece qty<BR>
	 * 			13.plan piece qty<BR>
	 * 			14.storage time <BR>
	 *	 	</DIR>
	 * </DIR>
	 * <BR>
	 * @param viewParam display data array
	 */
	protected void setDisplayData (AsScheduleParameter[] viewParam)
	{
		//#CM37613
		// eliminate all lines
		lst_WorkDisplay.clearRow();

		//#CM37614
		// set input area display
		//#CM37615
		// job no..
		txt_RWorkNo.setText(viewParam[0].getWorkingNo());
		//#CM37616
		// work type
		txt_RWorkType.setText(viewParam[0].getWorkingType());
		//#CM37617
		// picking instruction detail
		if( !StringUtil.isBlank(viewParam[0].getRetrievalDetail()) )
		{
			txt_RRetrievalDetail.setText(viewParam[0].getRetrievalDetail());
		}
		else
		{
			txt_RRetrievalDetail.setText("");
		}
		//#CM37618
		// location no.
		txt_FRLocation.setString(DisplayText.formatLocationnumber(viewParam[0].getLocationNo()));
		
		//#CM37619
		// set viewstate
		//#CM37620
		// used when the Finish button is pressed
		this.getViewState().setString(V_CARRY_KEY, viewParam[0].getCarryKey());
		//#CM37621
		// Maintain location no of no edit. Use it when you acquire the shelf details. 
		this.getViewState().setString(V_LOCATION_KEY, viewParam[0].getLocationNo());
		//#CM37622
		// Use it when you acquire the shelf details. 
		this.getViewState().setString(V_WH_STNO_KEY, viewParam[0].getWareHouseNo());
		
		//#CM37623
		// Acquire the name of balloon help For display. 
		String consignorName = DisplayText.getText("LBL-W0026");
		String itemName = DisplayText.getText("LBL-W0103");
		ToolTipHelper toolTip = null;

		//#CM37624
		// set preset area data
		for (int i = 1; i < viewParam.length; i++)
		{
			//#CM37625
			//add row
			//#CM37626
			//fetch last row
			int countnum = lst_WorkDisplay.getMaxRows();
			lst_WorkDisplay.setCurrentRow(countnum);
			lst_WorkDisplay.addRow();

			setList(L_ROW_NO, Integer.toString(lst_WorkDisplay.getMaxRows() - 1));
			setList(L_CONSIGNOR_CODE, viewParam[i].getConsignorCode());
			setList(L_CONSIGNOR_NAME, viewParam[i].getConsignorName());
			setList(L_ITEM_CODE, viewParam[i].getItemCode());
			setList(L_ITEM_NAME, viewParam[i].getItemName());
			setList(L_ENTERING_QTY, viewParam[i].getEnteringQty());
			setList(L_BUNDLE_ENTERING_QTY, viewParam[i].getBundleEnteringQty());
			setList(L_STOCK_CASE_QTY, viewParam[i].getStockCaseQty());
			setList(L_STOCK_PIECE_QTY, viewParam[i].getStockPieceQty());
			setList(L_WORK_CASE_QTY, viewParam[i].getPlanCaseQty());
			setList(L_WORK_PIECE_QTY, viewParam[i].getPlanPieceQty());
			setList(L_CASE_PIECE_FLAG, viewParam[i].getCasePieceFlagNameDisp());
			setList(L_INSTOCK_DATE, viewParam[i].getInStockDate(), DATE_DISPTYPE_DATE);
			setList(L_INSTOCK_TIME, viewParam[i].getInStockDate(), DATE_DISPTYPE_TIME);
			
			//#CM37627
			// Set the balloon help display data. 
			toolTip = new ToolTipHelper();
			toolTip.add(consignorName, viewParam[i].getConsignorName());
			toolTip.add(itemName, viewParam[i].getItemName());
			lst_WorkDisplay.setToolTip(lst_WorkDisplay.getCurrentRow(), toolTip.getText());

		}
		
	}
	
	//#CM37628
	/**
	 * Set effective of completion and disbursement End button Invalid. <BR>
	 * <BR>
	 * Abstract :Set effective and invalidate End button and disbursement End button by the transportation flag. <BR>
	 * <BR>
	 * @param viewParam Display data
	 */
	protected void setButtonCondition(AsScheduleParameter viewParam) 
	{
		//#CM37629
		// Make Button effective when station work display exists, and there is End button. 
		if(viewParam.getOperationDisplay() == AsScheduleParameter.OPERATION_INSTRUCTION)
		{
			//#CM37630
			// Decide right or wrong of use of Button by the transportation flag.
			//#CM37631
			// The transportation flag is Storage disbursement unnecessary.
			int carrykind = viewParam.getCarryKind();
			if(carrykind == AsScheduleParameter.CARRY_KIND_STORAGE)
			{
				//#CM37632
				//Enable pressing End button. 
				btn_Complete.setEnabled(true);
				//#CM37633
				//Disbursement completion the button is made not to be able to press it. 
				btn_ExpendComplete.setEnabled(false);
			}
			//#CM37634
			// When the transportation flag is picking, it is possible to disburse it. 
			else if(carrykind == AsScheduleParameter.CARRY_KIND_RETRIEVAL)
			{
				//#CM37635
				//Enable pressing End button. 
				btn_Complete.setEnabled(true);
				//#CM37636
				//Enable pressing Disburse End button. 
				btn_ExpendComplete.setEnabled(true);
			}
			//#CM37637
			// Going directly the transportation flag
			else if (carrykind == AsScheduleParameter.CARRY_KIND_DIRECT_TRAVEL)
			{
				//#CM37638
				// Only End button : at direct transportation former station. (Storage treatment)
				if (viewParam.getFromStationNo().equals(viewParam.getStationNo()))
				{
					//#CM37639
					//Enable pressing End button. 
					btn_Complete.setEnabled(true);
					//#CM37640
					//Disbursement completion the button is made not to be able to press it. 
					btn_ExpendComplete.setEnabled(false);
				}
				//#CM37641
				// For picking side station
				else
				{
					//#CM37642
					//Enable pressing End button. 
					btn_Complete.setEnabled(true);
					//#CM37643
					//Enable pressing Disburse End button. 
					btn_ExpendComplete.setEnabled(true);
				}
			}
		}
		else
		{
			//#CM37644
			//End button becomes unuseable.
			btn_Complete.setEnabled(false);
			//#CM37645
			//Disbursement completion the button is made not to be able to press it. 
			btn_ExpendComplete.setEnabled(false);
		}
		
		if (!StringUtil.isBlank(txt_FRLocation.getText()))
		{
			//#CM37646
			// When things except location no are displayed, the shelf details should be not able to be pressed. 
			if (!txt_FRLocation.getText().equals(txt_FRLocation.getString()))
			{
				btn_LocationDetails.setEnabled(true);
			}
			else
			{
				btn_LocationDetails.setEnabled(false);
			}
		}
		
	}

	//#CM37647
	/**
	 * Set a numeric item in the list cell. 
	 * @param columnNo line no. of item to be set
	 * @param value value to be set
	 */
	protected void setList(int columnNo, int value)
	{
		setList(columnNo, WmsFormatter.getNumFormat(value));
	}

	//#CM37648
	/**
	 * Set the date item in the list cell. 
	 * @param columnNo line no. of item to be set
	 * @param value value to be set
	 * @param dispType type of date format
	 */
	protected void setList(int columnNo, Date value, int dispType)
	{
		//#CM37649
		// edit date
		if (dispType == DATE_DISPTYPE_DATE)
		{
			setList(columnNo, WmsFormatter.getDateFormat(value, ""));
		}
		//#CM37650
		// edit time
		else if (dispType == DATE_DISPTYPE_TIME)
		{
			setList(columnNo, WmsFormatter.getTimeFormat(value, ""));

		}
	}
	
	//#CM37651
	/**
	 * Set the character string item in the list cell. 
	 * @param columnNo line no. of item to be set
	 * @param value value to be set
	 */
	protected void setList(int columnNo, String value)
	{
		lst_WorkDisplay.setValue(columnNo, value);
	}
	
	//#CM37652
	/**
	 * It completes disbursement Process. <BR>
	 * <BR>
	 * Abstract :Completion and disbursement completion Process are done by information on input area. <BR>
	 * <BR>
	 * <DIR>
	 *	  	1.set cursor in worker code<BR>
	 *		2.start scheduler<BR>
	 *		<DIR>
	 *   		[parameter]<BR>
	 * 			<DIR>
	 * 				worker code<BR>
	 * 				password<BR>
	 * 				station no.<BR>
	 * 				ViewState.conveyance key<BR>
	 * 				Push button<BR>
	 *			</DIR>
	 * 		</DIR>
	 * 		3.refresh the screen display<BR>
	 * </DIR>
	 * @param buttonType Pressed button
	 * @throws Exception report all the exceptions
	 */
	protected void complete(String buttonType) throws Exception
	{
		//#CM37653
		// set the focus in worker code
		setFocus(txt_WorkerCode);
		
		//#CM37654
		// input check
		txt_WorkerCode.validate();
		txt_Password.validate();
		
		Connection conn = null;
		try
		{
			//#CM37655
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			AsScheduleParameter[] param = new AsScheduleParameter[1];
			param[0] = new AsScheduleParameter();
			param[0].setWorkerCode(txt_WorkerCode.getText());
			param[0].setPassword(txt_Password.getText());
			param[0].setStationNo(pul_StationNo.getSelectedValue());
			param[0].setCarryKey(this.getViewState().getString(V_CARRY_KEY));
			param[0].setButtonType(buttonType);
		
			String msgBuf = "";
			WmsScheduler schedule = new AsWorkDisplaySCH();
			if (schedule.startSCH(conn, param))
			{
				conn.commit();
			}
			else
			{
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			
			//#CM37656
			// Maintain the message of completion Process result. 
			msgBuf = schedule.getMessage();
			if (schedule.getMessage() != null)
			{
				//#CM37657
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}			
		
			//#CM37658
			// refresh the screen display
			if(!setOperationDisplay(conn, pul_StationNo.getSelectedValue()))
			{
				//#CM37659
				// Completion Process message
				message.setMsgResourceKey(msgBuf);
			}
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM37660
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}


	//#CM37661
	// Private methods -----------------------------------------------

	//#CM37662
	// Event handler methods -----------------------------------------
	//#CM37663
	/**
	 * called when menu button is clicked<BR>
	 * <BR>
	 * overview ::go back to menu screen<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM37664
	/** 
	 * It is called when the display button is pressed.<BR>
	 * <BR>
	 * overview ::Acquire work information with station which has been selected and display it. <BR>
	 * and set the cursor in worker code<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Reflesh_Click(ActionEvent e) throws Exception
	{
		//#CM37665
		// set the focus in worker code
		setFocus(txt_WorkerCode);
		
		Connection conn = null;
		try
		{
			//#CM37666
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	
			//#CM37667
			// Select station No. which has been selected from Pulldown, and acquire the display data. 
			String stno = pul_StationNo.getSelectedValue();
			setOperationDisplay(conn, stno);
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM37668
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}

		
	}

	//#CM37669
	/** 
	 * This method is called when the location detail button is clicked<BR>
	 * <BR>
	 * overview ::set search condition in parameter,Displays the location detail list box. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *    	warehouse<BR>
	 *      location<BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_LocationDetails_Click(ActionEvent e) throws Exception
	{
		//#CM37670
		// Set the search condition on the shelf details screen. 
		ForwardParameters param = new ForwardParameters();
		param.setParameter(ListAsStockDetailNoBtnBusiness.LOCATION_KEY, this.getViewState().getString(V_LOCATION_KEY));
		param.setParameter(ListAsStockDetailNoBtnBusiness.WAREHOUSE_KEY, this.getViewState().getString(V_WH_STNO_KEY));

		//#CM37671
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsstockdetailnobtn/ListAsStockDetailNoBtn.do", param, "/progress.do");
	}

	//#CM37672
	/** 
	 * It is called when the completion button is pressed.<BR>
	 * <BR>
	 * Abstract :<CODE>complete</CODE> Method is executed. <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_Complete_Click(ActionEvent e) throws Exception
	{
		complete(AsScheduleParameter.BUTTON_COMPLETE);
	}

	//#CM37673
	/** 
	 * When pushed, disbursement completion the button is called. <BR>
	 * <BR>
	 * Abstract :<CODE>complete</CODE> Method is executed. <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_ExpendComplete_Click(ActionEvent e) throws Exception
	{
		complete(AsScheduleParameter.BUTTON_UNIT_COMPLETE);
	}
}
//#CM37674
//end of class
