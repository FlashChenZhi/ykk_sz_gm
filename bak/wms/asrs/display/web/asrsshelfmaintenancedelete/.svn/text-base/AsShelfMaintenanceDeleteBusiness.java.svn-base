// $Id: AsShelfMaintenanceDeleteBusiness.java,v 1.2 2006/10/26 04:53:31 suresh Exp $

//#CM37178
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsshelfmaintenancedelete;
import java.sql.Connection;
import java.sql.SQLException;
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
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstockdetailnobtn.ListAsStockDetailNoBtnBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocationdetailstatuslist.ListAsLocationDetailStatusListBusiness;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.asrs.schedule.AsShelfMaintenanceDeleteSCH;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.WareHouse;

//#CM37179
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * ASRS Stock maintenance (delete) class.<BR>
 * set the input contents from the screen to a parameter
 * The schedule does the stock maintenance batch deletion based on the parameter. <BR>
 * Receive true when the result is received from the schedule, and processing is completed normally. 
 * Receive false when it does not complete the schedule because of the condition error etc.<BR>
 * output the schedule result, schedule message to screen<BR>
 * and transaction commit and rollback are done in this screen<BR>
 * <BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.set button click event(<CODE>btn_Setting_Click</CODE>method ) <BR>
 * <BR>
 * <DIR>
 *  The content input from input area is set in the parameter, and the schedule does the stock maintenance deletion based on the parameter. <BR>
 *  Receive true when the result is received from the schedule, and processing is completed normally. 
 *  Receive false when it does not complete the schedule because of the condition error etc.<BR>
 *  output the schedule result, schedule message to screen<BR>
 * 	<DIR>
 *   [parameter] *mandatory input <BR>
 *   <BR>
 * 		worker code *<BR>
 * 		password *<BR>
 * 		warehouse*<BR>
 * 		location no. *<BR>
 * 		terminal no.<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/9/29</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:53:31 $
 * @author  $Author: suresh $
 */
public class AsShelfMaintenanceDeleteBusiness extends AsShelfMaintenanceDelete implements WMSConstants
{
	//#CM37180
	// Class fields --------------------------------------------------

	//#CM37181
	// Class variables -----------------------------------------------

	//#CM37182
	// Class method --------------------------------------------------

	//#CM37183
	// Constructors --------------------------------------------------

	//#CM37184
	// Public methods ------------------------------------------------

	//#CM37185
	/**
	 * screen initialization<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * <BR>
	 * <DIR>
	 * 		1.initialize pulldown<BR>
	 * 		2.initialize input area<BR>
	 * 		3.set the cursor in worker code<BR>
	 * 		<BR>
	 * 		item[initial value] <BR>
	 * 		<DIR>
	 * 			worker code[nil]<BR>
	 * 			password[nil]<BR>
	 * 			result location checkbox[true] <BR>
	 * 			Empty Palette[false]<BR>
	 * 			Error location[false] <BR>
	 * 			location[nil] <BR>
	 * 			location status[nil] <BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;

		try
		{
			Locale locale = this.httpRequest.getLocale();
			//#CM37186
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	
			//#CM37187
			//set terminal no.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo)this.getUserInfo());
			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());
	
			//#CM37188
			// pull down display data(warehouse)
			String[] whno = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);
			//#CM37189
			//set pulldown data
			PulldownHelper.setPullDown(pul_WareHouse, whno);
	
			//#CM37190
			// initial display
			setFirstDisp();
		}
		catch (Exception ex)
		{
			//#CM37191
			// rollback connection
			if (conn != null)
			{
				conn.rollback();
			}
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM37192
				// close connection
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM37193
	/**
	 * call this before calling the respective control events<BR>
	 * <BR>
	 * overview ::display confirmation dialog<BR>
	 * and set the cursor in worker code<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM37194
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM37195
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM37196
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}
		//#CM37197
		// Confirmation dialog when Delete button is pressed "Do you want to Delete?"
		btn_Setting.setBeforeConfirm("MSG-W0007");

		//#CM37198
		// set the cursor in worker code input
		setFocus(txt_WorkerCode);
	}

	//#CM37199
	// Package methods -----------------------------------------------

	//#CM37200
	/**
	 * This method is called when returning from popup window<BR>
	 * override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE><BR>
	 * <BR>
	 * overview ::fetch the value from search screen and set it<BR>
	 * <BR>
	 * <DIR>
	 *      1.fetch the value set from popup search screen<BR>
	 *      2.set value if it is not null<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM37201
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			DialogParameters param = ((DialogEvent) e).getDialogParameters();
			//#CM37202
			// location no.
			String location = param.getParameter(ListAsLocationDetailStatusListBusiness.LOCATION_KEY);
			if (!StringUtil.isBlank(location))
			{
				txt_Location.setText(DisplayText.formatLocationnumber(location));
				//#CM37203
				// Check and edit status of selection location
				checkLocation(conn, DisplayText.formatLocationnumber(location));
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
				//#CM37204
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}

	}

	//#CM37205
	// Protected methods ---------------------------------------------

	//#CM37206
	// Private methods -----------------------------------------------

	//#CM37207
	/**
	 * This method initializes screen<BR>
	 * <BR>
	 * overview ::initial screen display <BR>
	 * and set the cursor in worker code<BR>
	 * <DIR>
	 * 		[parameter] <BR>
	 * 		<DIR>
	 * 			item name[initial value] <BR>
	 * 			<DIR>
	 * 				result location checkbox[true] <BR>
	 * 				Empty Palette[false]<BR>
	 * 				Error location[false] <BR>
	 * 				location[nil] <BR>
	 * 				location status[nil] <BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * @throws Exception report all the exceptions
	 */
	private void setFirstDisp() throws Exception
	{
		//#CM37208
		// warehouse
		pul_WareHouse.setSelectedIndex(0);

		//#CM37209
		// location status(empty location)
		chk_LocationStatus_Unit.setChecked(true);
		//#CM37210
		// location status(Empty Palette)
		chk_LocationStatus_Empty_PB.setChecked(false);
		//#CM37211
		// location status(result location)
		chk_LocationStatus_Abn.setChecked(false);
		
		//#CM37212
		// location no.
		txt_Location.setText("");
		//#CM37213
		// location status
		lbl_JavaSetLocationStatus.setText("");

		//#CM37214
		// set the cursor in worker code input
		setFocus(txt_WorkerCode);
	}

	//#CM37215
	/**
	 * Status of the shelf is checked, and whether it is possible to maintain it is decided.
	 * @param conn instance to store database connection
	 * @param p_location location no. 
	 * @return Check result(true:It is possible to maintain it. false:It is not possible to maintain it. ) 
	 * @throws Exception report all the exceptions
	 */
	private boolean checkLocation(Connection conn, String p_location) throws Exception
	{
		WareHouseHandler wWhHandler = new WareHouseHandler(conn);
		WareHouseSearchKey wWhSearchKey = new WareHouseSearchKey();
		ShelfHandler wShHandler = new ShelfHandler(conn);
		ShelfSearchKey wShSearchKey = new ShelfSearchKey();

		wWhSearchKey.KeyClear();
		//#CM37216
		// Acquire the Warehouse division from Warehouse Station No.
		wWhSearchKey.setStationNumber(pul_WareHouse.getSelectedValue());
		
		WareHouse wWareHouse = (WareHouse)wWhHandler.findPrimary(wWhSearchKey);
				
		wShSearchKey.KeyClear();
		//#CM37217
		// warehouse type + input location no.
		String currstnumber = DisplayText.formatLocation(wWareHouse.getWareHouseNumber(), p_location);
		//#CM37218
		// warehouse type + input location no.
		wShSearchKey.setStationNumber(currstnumber);
		
		Shelf wShelf = (Shelf)wShHandler.findPrimary(wShSearchKey);

		if (wShelf == null)
		{
			//#CM37219
			// Please enter the existing location no.
			this.message.setMsgResourceKey("6013090");
			lbl_JavaSetLocationStatus.setText("");
			return false;
		}

		//#CM37220
		// In accessible location is assumed to be It is not possible to maintain it. 
		if (wShelf.getAccessNgFlag() == Shelf.ACCESS_NG)
		{		
			//#CM37221
			// The location is inaccessible. Unable to make corrections.
			this.message.setMsgResourceKey("6013160");	
			lbl_JavaSetLocationStatus.setText(DisplayText.getText("FINDUTIL_ACCESSNGFLAG"));
			return false;
		}

		//#CM37222
		// check restricted location
		if (wShelf.getStatus() == Shelf.STATUS_NG)
		{		
			//#CM37223
			// The location is restricted. Unable to make corrections.
			this.message.setMsgResourceKey("6013159");	
			lbl_JavaSetLocationStatus.setText(DisplayText.getText("FINDUTIL_UNAVAILABLE"));
			return false;
		}
		
		//#CM37224
		// Check the location status.
		if (wShelf.getPresence() == (Shelf.PRESENCE_RESERVATION))
		{
			//#CM37225
			// The specified location cannot be maintained because of the reserved location.
			this.message.setMsgResourceKey("6013158");	
			lbl_JavaSetLocationStatus.setText(DisplayText.getText("FINDUTIL_WORK"));
			return false;
		}
		
		//#CM37226
		// Check the location status.
		if (wShelf.getPresence() == (Shelf.PRESENCE_EMPTY))
		{
			//#CM37227
			// Stock does not exist in the specified location. 
			this.message.setMsgResourceKey("6013134");	
			lbl_JavaSetLocationStatus.setText(DisplayText.getText("FINDUTIL_EMPTY"));
			return false;
		}

		Palette[] wPalette = null;
		//#CM37228
		// If the location is occupied, check the allcation status and erroe location.
		if (wShelf.getPresence() == Shelf.PRESENCE_STORAGED)
		{
			PaletteHandler wPlHandler = new PaletteHandler(conn);
			PaletteSearchKey wPlSearchKey = new PaletteSearchKey();
			
			//#CM37229
			// set the search condition
			wPlSearchKey.KeyClear();
			wPlSearchKey.setCurrentStationNumber(currstnumber);
			
			wPalette = (Palette[])wPlHandler.find(wPlSearchKey);
			
			int pltstatus = wPalette[0].getStatus();
			switch (pltstatus)
			{
				//#CM37230
				// occupied location
				case Palette.REGULAR:
					break;
				//#CM37231
				// reserved for storage
				case Palette.STORAGE_PLAN:
				//#CM37232
				// reserved for retrieval
				case Palette.RETRIEVAL_PLAN:
				//#CM37233
				// being retrieved
				case Palette.RETRIEVAL:
					//#CM37234
					// Specified location is allocated at moment.
					lbl_JavaSetLocationStatus.setText(DisplayText.getText("FINDUTIL_WORK"));
					this.message.setMsgResourceKey("6013135");	
					return false;
				//#CM37235
				// error
				case Palette.IRREGULAR:
					break;
			}
		}

		//#CM37236
		// Retrieve the status of the location no. entered via screen.
		//#CM37237
		//Inaccessible location
		String str = "";
		if ( wShelf.getAccessNgFlag() == Shelf.ACCESS_OK )
		{
			//#CM37238
			//Restricted location
			if ( wShelf.getStatus() == Shelf.STATUS_NG )
			{
				str = DisplayText.getText("FINDUTIL_UNAVAILABLE");
			}
			else
			{
				//#CM37239
				// Empth location
				if ( wShelf.getPresence() == Shelf.PRESENCE_EMPTY )
				{
					str = DisplayText.getText("FINDUTIL_EMPTY");
				}
				//#CM37240
				// Occupied location
				else if ( wShelf.getPresence() == Shelf.PRESENCE_STORAGED )
				{

					//#CM37241
					// In case the location is occupied and restricted, it is displayed as restricted location.
					if ( wPalette[0].getStatus() == Shelf.STATUS_NG )
					{
						str = DisplayText.getText("FINDUTIL_UNAVAILABLE");
					}
					//#CM37242
					//Retrieval in progress (shown as working location)
					else if(wPalette[0].getStatus() == Palette.RETRIEVAL_PLAN ||
					wPalette[0].getStatus() == Palette.RETRIEVAL)
					{
						str = DisplayText.getText("FINDUTIL_WORK");
					}
					else
					{
						//#CM37243
						//Empty pallet
						if(wPalette[0].getEmpty() == Palette.STATUS_EMPTY)
						{
							str = DisplayText.getText("FINDUTIL_EMPTYPALETTE");
						}
						else
						{
							//#CM37244
							//Error location
							if(wPalette[0].getStatus() == Palette.IRREGULAR)
							{
								str = DisplayText.getText("FINDUTIL_IRREGULAR");
							}
							//#CM37245
							//Occupied location
							else
							{
								str = DisplayText.getText("FINDUTIL_STORAGED");
							}
						}
					}
				}
				//#CM37246
				// Reserved location (shown as working location)
				else if ( wShelf.getPresence() == Shelf.PRESENCE_RESERVATION )
				{
					str = DisplayText.getText("FINDUTIL_WORK");
				}
			}
		}
		//#CM37247
		// Inacessible location
		else
		{
			//#CM37248
			//In case the location is inaccessible and restricted at the same time, it is dispalyed as
			//restricted location. (Status of restriction is represented with priority)
			if ( wShelf.getStatus() == Shelf.STATUS_NG)
			{
				str = DisplayText.getText("FINDUTIL_UNAVAILABLE");
			}
			else
			{
				str = DisplayText.getText("FINDUTIL_ACCESSNGFLAG");
			}
		}
		lbl_JavaSetLocationStatus.setText(str);
		
		return true;
	}

	
	//#CM37249
	// Event handler methods -----------------------------------------
	//#CM37250
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

	//#CM37251
	/** 
	 * It is called when inquiry button is pressed.<BR>
	 * <BR>
	 * overview ::Display the stock list list box according to location no by the set search condition in parameter and the search condition.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *    	warehouse station no.<BR>
	 *    	warehouse name<BR>
	 *      result location checkbox <BR>
	 *      Empty Palette checkbox <BR>
	 *      Error location checkbox <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_Query_Click(ActionEvent e) throws Exception
	{
		//#CM37252
		// Set the search condition in the stock list according to Location No. 
		ForwardParameters param = new ForwardParameters();
		//#CM37253
		// warehouse station no.
		param.setParameter(ListAsLocationDetailStatusListBusiness.WAREHOUSE_KEY , pul_WareHouse.getSelectedValue());
		//#CM37254
		// warehouse name
		param.setParameter(ListAsLocationDetailStatusListBusiness.WAREHOUSENAME_KEY, pul_WareHouse.getSelectedItem().getText());		
		//#CM37255
		// Acquire location status. 
		Vector statusVec = new Vector();
		//#CM37256
		// result location
		if (chk_LocationStatus_Unit.getChecked())
		{
			statusVec.add(Integer.toString(AsScheduleParameter.STATUS_STORAGED));
		}
		//#CM37257
		// Empty Palette
		if (chk_LocationStatus_Empty_PB.getChecked())
		{
			statusVec.add(Integer.toString(AsScheduleParameter.STATUS_EMPTYPALETTE));
		}
		//#CM37258
		// Error location
		if (chk_LocationStatus_Abn.getChecked())
		{
			statusVec.add(Integer.toString(AsScheduleParameter.STATUS_IRREGULAR));
		}
		String[] statusList = new String[statusVec.size()];
		statusVec.copyInto(statusList);
		param.setParameter(ListAsLocationDetailStatusListBusiness.LOCATIONSTATUS_KEY , statusList);

		//#CM37259
		// Stock list display according to Location No
		redirect("/asrs/listbox/listasrslocationdetailstatuslist/ListAsLocationDetailStatusList.do", param, "/progress.do");
	}

	//#CM37260
	/** 
	 * It is called when Enter Key is pushed in the location text area.<BR>
	 * <BR>
	 * Abstract :Check status of the location, and display it in shelf status. <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void txt_Location_EnterKey(ActionEvent e) throws Exception
	{
		Connection conn = null;
		
		try
		{
			//#CM37261
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			if (!StringUtil.isBlank(txt_Location.getString()))
			{
				checkLocation(conn, txt_Location.getString());
			}
		}
		finally
		{
			try
			{
				//#CM37262
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM37263
	/** 
	 * It is called when Tab Key is pushed in the location text area.<BR>
	 * <BR>
	 * Abstract :Check status of the location, and display it in shelf status. <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void txt_Location_TabKey(ActionEvent e) throws Exception
	{
		Connection conn = null;
		
		try
		{
			//#CM37264
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			if (!StringUtil.isBlank(txt_Location.getString()))
			{
				checkLocation(conn, txt_Location.getString());
			}
		}
		finally
		{
			try
			{
				//#CM37265
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM37266
	/** 
	 * This method is called when the location detail button is clicked<BR>
	 * <BR>
	 * overview ::set search condition in parameter, Displays the location detail list box. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *    	Warehouse No.<BR>
	 *    	warehouse name<BR>
	 *      location<BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_LocationDetails_Click(ActionEvent e) throws Exception
	{
		//#CM37267
		//Set the search condition in the location details list display. 
		ForwardParameters param = new ForwardParameters();
		param.setParameter(ListAsStockDetailNoBtnBusiness.WAREHOUSE_KEY , pul_WareHouse.getSelectedValue());
		param.setParameter(ListAsStockDetailNoBtnBusiness.WAREHOUSENAME_KEY , pul_WareHouse.getSelectedItem().getText());
		param.setParameter(ListAsStockDetailNoBtnBusiness.LOCATION_KEY , DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_Location.getString()));

		//#CM37268
		//Shelf details list display
		redirect("/asrs/listbox/listasrsstockdetailnobtn/ListAsStockDetailNoBtn.do", param, "/progress.do");	
	}

	//#CM37269
	/** 
	 * It is called when Submit Button is pressed.<BR>
	 * <BR>
	 * Abstract :Do stock maintenance deletion Process by information on input area. <BR>
	 * <BR>
	 * <DIR>
	 *	  1.set cursor in worker code<BR>
	 *    2.Display the dialog box, and confirm whether to delete it. <BR>
	 *    <DIR>
	 * 		[confirmation dialog CANCEL]<BR>
	 *			<DIR>
	 *				do nothing
	 *			</DIR>
	 * 		[confirmation dialog OK]<BR>
	 *			<DIR>
	 *				1.start scheduler<BR>
	 *				<DIR>
	 *   				[parameter]<BR>
	 * 					<DIR>
	 * 						worker code<BR>
	 * 						password<BR>
	 * 						warehouse<BR>
	 * 						location no.<BR>
	 * 						terminal no.<BR>
	 *	 				</DIR>
	 *				</DIR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_Setting_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM37270
			// set the cursor in worker code input
			setFocus(txt_WorkerCode);
	
			//#CM37271
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	
			txt_WorkerCode.validate();
			txt_PassWord.validate();
			txt_Location.validate();

			//#CM37272
			// set schedule parameter
			AsScheduleParameter param = new AsScheduleParameter();

			param.setWorkerCode(txt_WorkerCode.getText());
			param.setPassword(txt_PassWord.getText());
			param.setWareHouseNo(pul_WareHouse.getSelectedValue());
			param.setLocationNo(DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_Location.getString()));

			//#CM37273
			// terminal no.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());
	
			WmsScheduler schedule = new AsShelfMaintenanceDeleteSCH();
	
	
			//#CM37274
			// input check
			if (checkLocation(conn, txt_Location.getString()))
			{
				AsScheduleParameter[] paramArray = new AsScheduleParameter[1];
				paramArray[0] = param;
				//#CM37275
				// start schedule
				if (schedule.startSCH(conn, paramArray))
				{
					//#CM37276
					// commit if the result is true
					conn.commit();
					//#CM37277
					// refresh status
					checkLocation(conn, txt_Location.getString());
					//#CM37278
					// set message
					message.setMsgResourceKey(schedule.getMessage());
				}
				else
				{
					conn.rollback();
					//#CM37279
					// set message
					message.setMsgResourceKey(schedule.getMessage());
				}
			}
			else
			{
				conn.rollback();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			//#CM37280
			// rollback connection
			if (conn != null)
			{
				conn.rollback();
			}
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM37281
				// close connection
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM37282
	/**
	 * called when clear button is clicked<BR>
	 * <BR>
	 * Abstract :clear the input area<BR>
	 * <BR>
	 * <DIR>
	 * 		1.return input area items to initial state<BR>
	 * 		2.set the cursor in worker code<BR>
	 * 		<DIR>
	 *   	item name[initial value]<BR>
	 *   		<DIR>
	 * 				worker code[as it is]<BR>
	 * 				password[as it is]<BR>
	 * 				result location check box[true] <BR>
	 * 				Empty Palette[false]<BR>
	 * 				Error location[false] <BR>
	 * 				location[nil] <BR>
	 * 				location status[nil] <BR>
	 * 	 		</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM37283
		// initial display
		setFirstDisp();
	}
}
//#CM37284
//end of class
