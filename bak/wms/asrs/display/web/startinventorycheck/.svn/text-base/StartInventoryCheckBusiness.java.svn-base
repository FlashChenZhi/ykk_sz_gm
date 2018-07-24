// $Id: StartInventoryCheckBusiness.java,v 1.3 2006/10/26 08:59:32 suresh Exp $

//#CM41040
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.startinventorycheck;
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
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocation.ListAsLocationBusiness;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.asrs.schedule.StartInventoryCheckSCH;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WareHouse;

//#CM41041
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * This is the ASRS stock confirmation start screen class<BR>
 * set the screen contents to a parameter and start stock confirmation<BR>
 * output the schedule result, schedule message to screen<BR>
 * and transaction commit and rollback are done in this screen<BR>
 * <BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.set button click event(<CODE>btn_Setting_Click</CODE>method ) <BR>
 * <BR>
 * <DIR>
 *  set the input contents to a parameter and start stock confirmation<BR>
 *  output the schedule result, schedule message to screen<BR>
 * 	<DIR>
 *   [parameter] *mandatory input <BR>
 *   <BR>
 * 		worker code <BR>
 * 		warehouse <BR>
 * 		workplace <BR>
 * 		station no. <BR>
 * 		consignor code <BR>
 * 		start location <BR>
 * 		end location <BR>
 * 		start item code <BR>
 *		end item code <BR>
 *		list print flag <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/10/26 08:59:32 $
 * @author  $Author: suresh $
 */
public class StartInventoryCheckBusiness extends StartInventoryCheck implements WMSConstants
{
	//#CM41042
	// Class fields --------------------------------------------------
	//#CM41043
	/**
	 * pulldown stock confirm
	 */
	private static final String STATION_INVENTORY_CHECK = "18";

	//#CM41044
	// Class variables -----------------------------------------------

	//#CM41045
	// Class method --------------------------------------------------

	//#CM41046
	// Constructors --------------------------------------------------

	//#CM41047
	// Public methods ------------------------------------------------

	//#CM41048
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
	 * 			consignor code[if search results a single consignor, display it] <BR>
	 * 			start location[nil] <BR>
	 * 			end location[nil] <BR>
	 * 			start item code[nil] <BR>
	 * 			end item code[nil] <BR>
	 * 			print stock confirmation work list[true] <BR>
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
			//#CM41049
			// save preset area line no. in ViewState to decide the change status
			//#CM41050
			// (default:-1)
			this.getViewState().setInt("LineNo", -1);

			//#CM41051
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM41052
			// fetch Http Locale
			Locale locale = this.getHttpRequest().getLocale();

			WmsScheduler schedule = new StartInventoryCheckSCH();

			AsScheduleParameter param = (AsScheduleParameter) schedule.initFind(conn, null);

			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			//#CM41053
			// pull down display data(storage area)
			String[] areaid = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);
			//#CM41054
			// pull down display data(workplace)
			String[] sagyoba = pull.getSagyobaPulldownData(conn, STATION_INVENTORY_CHECK, true, "");
			//#CM41055
			// pull down display data(station)
			String[] stno = pull.getStationPulldownData(conn, STATION_INVENTORY_CHECK, true, "");

			//#CM41056
			// set pulldown data
			PulldownHelper.setPullDown(pul_WareHouse, areaid);
			PulldownHelper.setLinkedPullDown(pul_WorkPlace, sagyoba);
			PulldownHelper.setLinkedPullDown(pul_Station, pul_WorkPlace, stno);

			//#CM41057
			// add child pull down
			pul_WareHouse.addChild(pul_WorkPlace);
			pul_WorkPlace.addChild(pul_Station);

			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				//#CM41058
				// display customer code in the init screen, when there is single record
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
			
			//#CM41059
			// disable button click
			//#CM41060
			// Set button
			btn_Setting.setEnabled(true);
			//#CM41061
			// clear button
			btn_Clear.setEnabled(true);

			//#CM41062
			// initial display
			setFirstDisp();

		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM41063
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM41064
	/**
	 * call this before calling the respective control events<BR>
	 * <BR>
	 * overview ::display confirmation dialog<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		
		if(menuparam != null)
		{
			//#CM41065
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);

			//#CM41066
			//save to viewstate
			this.getViewState().setString(M_MENUID_KEY, menuID);

			//#CM41067
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}

		//#CM41068
		// clicking "Set" button displays "Do you set?"
		btn_Setting.setBeforeConfirm("MSG-9000");

	}

	//#CM41069
	/**
	 * This method is called when returning from popup window<BR>
	 * override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE><BR>
	 * <BR>
	 * overview ::fetch the value from search screen and set it<BR>
	 * <BR>
	 * <DIR>
	 *      1.fetch the value set from popup search screen<BR>
	 *      2.set value if it is not null<BR>
	 *      3.set the cursor in worker code<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		
		//#CM41070
		// fetch parameter selected from listbox
		//#CM41071
		// consignor code
		String consignorcode = param.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM41072
		// start location
		String startlocation = param.getParameter(ListAsLocationBusiness.STARTLOCATION_KEY);
		//#CM41073
		// end location
		String endlocation = param.getParameter(ListAsLocationBusiness.ENDLOCATION_KEY);
		//#CM41074
		// start item code
		String startitem = param.getParameter(ListAsItemBusiness.STARTITEM_KEY);
		//#CM41075
		// end item code
		String enditem = param.getParameter(ListAsItemBusiness.ENDITEM_KEY);

		//#CM41076
		// set the value if not empty
		//#CM41077
		// consignor code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM41078
		// start location
		if (!StringUtil.isBlank(startlocation))
		{
			txt_StartLocation.setText(DisplayText.formatLocationnumber(startlocation));
		}
		//#CM41079
		// end location
		if (!StringUtil.isBlank(endlocation))
		{
			txt_EndLocation.setText(DisplayText.formatLocationnumber(endlocation));
		}
		//#CM41080
		// start item code
		if (!StringUtil.isBlank(startitem))
		{
			txt_StartItemCode.setText(startitem);		
		}
		//#CM41081
		// end item code
		if (!StringUtil.isBlank(enditem))
		{
			txt_EndItemCode.setText(enditem);		
		}
		
		//#CM41082
		// set the cursor in worker code input
		setFocus(txt_WorkerCode);
	}

	//#CM41083
	// Package methods -----------------------------------------------

	//#CM41084
	// Protected methods ---------------------------------------------

	//#CM41085
	// Private methods -----------------------------------------------

	//#CM41086
	/** 
	 * method to check input<BR>
	 * <BR>
	 * Abstract :maximum and minimum relation check for location and item code<BR>
	 * if there are no input mistakes return true, else return false<BR>
	 * <BR>
	 * @return input check result(true:OK  false:NG)
	 * @throws Exception report all the exceptions
	 */
	private boolean checkInputData() throws Exception
	{
		//#CM41087
		// input check
		//#CM41088
		//start location should be lesser than end location
		if(!StringUtil.isBlank(txt_StartLocation.getText()) && !StringUtil.isBlank(txt_EndLocation.getText()))
		{
			if(txt_StartLocation.getText().compareTo(txt_EndLocation.getText()) > 0)			{
				//#CM41089
				// 6023357=start location should be lesser than end location
				message.setMsgResourceKey("6023357");
				return false;
			}
		}		
		//#CM41090
		// start item code should be lesser than end item code
		if(!StringUtil.isBlank(txt_StartItemCode.getText()) && !StringUtil.isBlank(txt_EndItemCode.getText()))
		{
			if(txt_StartItemCode.getText().compareTo(txt_EndItemCode.getText()) > 0)			{
				//#CM41091
				// 6023109=start item code should be lesser than end item code
				message.setMsgResourceKey("6023109");
				return false;
			}
		}
		
		return true;

	}
	
	//#CM41092
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
	 * 				consignor code[if search results a single consignor, display it] <BR>
	 * 				start location[nil] <BR>
	 * 				end location[nil] <BR>
	 * 				start item code[nil] <BR>
	 * 				end item code[nil] <BR>
	 * 				print stock confirmation work list[true] <BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * @throws Exception report all the exceptions
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM41093
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			WmsScheduler schedule = new StartInventoryCheckSCH();

			AsScheduleParameter param = (AsScheduleParameter) schedule.initFind(conn, null);

			//#CM41094
			// display customer code in the init screen, when there is single record
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
			else
			{
				txt_ConsignorCode.setText("");
			}
			
			//#CM41095
			// start location
			txt_StartLocation.setText("");
			//#CM41096
			// end location
			txt_EndLocation.setText("");
			//#CM41097
			// start item code
			txt_StartItemCode.setText("");
			//#CM41098
			// end item code
			txt_EndItemCode.setText("");
			//#CM41099
			// check [print stock confirmation work list]
			chk_CommonUse.setChecked(true);
						
			//#CM41100
			// set the cursor in worker code input
			setFocus(txt_WorkerCode);
		}
		catch (Exception ex)
		{
			//#CM41101
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
				//#CM41102
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

	//#CM41103
	/**
	 * create a parameter object with input area value <BR>
	 * @return parameter object containing the input value
	 * @throws Exception report all the exceptions
	 */
	private AsScheduleParameter[] setParam() throws Exception
	{
		Vector vecParam = new Vector();

		//#CM41104
		// set schedule parameter
		AsScheduleParameter param = new AsScheduleParameter();

		//#CM41105
		// warehouse
		param.setWareHouseNo(pul_WareHouse.getSelectedValue());

		if( pul_Station.getSelectedValue().equals(Station.AUTO_SELECT_STATIONNO) )
		{
			//#CM41106
			// workplace
			param.setToStationNo(pul_WorkPlace.getSelectedValue());
		}
		else
		{
			//#CM41107
			// receiving station
			param.setToStationNo(pul_Station.getSelectedValue());
		}
		
		//#CM41108
		// input area info
		//#CM41109
		// worker code
		param.setWorkerCode(txt_WorkerCode.getText());
		//#CM41110
		// password
		param.setPassword(txt_Password.getText());
		//#CM41111
		// terminal no.
		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
		param.setTerminalNumber(userHandler.getTerminalNo());

		//#CM41112
		// list print flag
		param.setListFlg(chk_CommonUse.getChecked());
		//#CM41113
		// consignor code
		param.setConsignorCode(txt_ConsignorCode.getText());
		//#CM41114
		// start location
		param.setFromLocationNo(DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), 
																txt_StartLocation.getString()));
		//#CM41115
		// end location
		param.setToLocationNo(DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), 
																txt_EndLocation.getString()));
		//#CM41116
		// start item code
		param.setStartItemCode(txt_StartItemCode.getText());
		//#CM41117
		// end item code
		param.setEndItemCode(txt_EndItemCode.getText());
		
		vecParam.addElement(param);
	
		if (vecParam.size() > 0)
		{
			//#CM41118
			// set preset area data if value is not null
			AsScheduleParameter[] listparam = new AsScheduleParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			//#CM41119
			// if there is no preset area data, set null
			return null;
		}
	}

	//#CM41120
	// Event handler methods -----------------------------------------
	//#CM41121
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

	//#CM41122
	/** 
	 * called when "consignor code" search button is clicked<BR>
	 * <BR>
	 * overview ::set search condition in parameter and display the search results in consignor list box<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       warehouse No. <BR>
	 *       consignor code<BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM41123
		// set search conditions to consignor search screen
		ForwardParameters param = new ForwardParameters();
		//#CM41124
		// warehouse No
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM41125
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM41126
		// search flag
		param.setParameter(ListAsConsignorBusiness.SEARCHITEM_KEY,
						   		AsScheduleParameter.SEARCHFLAG_STOCK);
		//#CM41127
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsconsignor/ListAsConsignor.do", param, "/progress.do");
	}

	//#CM41128
	/** 
	 * called when "start location" button is clicked<BR>
	 * <BR>
	 * overview ::set search condition in parameter and display the search results in a list box<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] <BR>
	 *    <DIR>
	 *       warehouse No <BR>
	 *       consignor code <BR>
	 *       start location <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_PSearchStartLocation_Click(ActionEvent e) throws Exception
	{
		//#CM41129
		// set search conditions in location search screen
		ForwardParameters param = new ForwardParameters();
		//#CM41130
		// warehouse No
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM41131
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM41132
		// start location
		param.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, 
								DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), 
								txt_StartLocation.getString()));
		//#CM41133
		// start flag
		param.setParameter(ListAsLocationBusiness.RANGELOCATION_KEY, AsScheduleParameter.RANGE_START);
		//#CM41134
		// search flag
		param.setParameter(ListAsLocationBusiness.SEARCHITEM_KEY, AsScheduleParameter.SEARCHFLAG_STOCK);
		//#CM41135
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrslocation/ListAsLocation.do", param, "/progress.do");
	}

	//#CM41136
	/** 
	 * called when "end location" button is clicked<BR>
	 * <BR>
	 * overview ::set search condition in parameter and display the search results in a list box<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       warehouse No <BR>
	 *       consignor code <BR>
	 *       end location <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_PSearchEndLocation_Click(ActionEvent e) throws Exception
	{
		//#CM41137
		// set search conditions in location search screen
		ForwardParameters param = new ForwardParameters();
		//#CM41138
		// warehouse No
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM41139
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM41140
		// end location
		param.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, 
								DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), 
								txt_EndLocation.getString()));
		//#CM41141
		// end flag
		param.setParameter(ListAsLocationBusiness.RANGELOCATION_KEY, AsScheduleParameter.RANGE_END);
		//#CM41142
		// search flag
		param.setParameter(ListAsLocationBusiness.SEARCHITEM_KEY, AsScheduleParameter.SEARCHFLAG_STOCK);

		//#CM41143
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrslocation/ListAsLocation.do", param, "/progress.do");
	}

	//#CM41144
	/** 
	 * Called when the start item code search button is clicked<BR>
	 * <BR>
	 * overview ::set search condition to parameter and display item code list search result based on it<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       warehouse No <BR>
	 *       consignor code <BR>
	 *       start location <BR>
	 *       end location <BR>
	 *       start item code <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_PSearchStartItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM41145
		// set search conditions in location search screen
		ForwardParameters param = new ForwardParameters();
		//#CM41146
		// warehouse No
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM41147
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM41148
		// start location
		param.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, 
								DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), 
								txt_StartLocation.getString()));
		//#CM41149
		// end location
		param.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, 
								DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), 
								txt_EndLocation.getString()));
		//#CM41150
		// start item code
		param.setParameter(ListAsItemBusiness.STARTITEM_KEY, txt_StartItemCode.getText());
		//#CM41151
		// start flag
		param.setParameter(ListAsItemBusiness.RANGEITEM_KEY, AsScheduleParameter.RANGE_START);
		//#CM41152
		// search flag
		param.setParameter(ListAsItemBusiness.SEARCHITEM_KEY, AsScheduleParameter.SEARCHFLAG_STOCK);

		//#CM41153
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsitem/ListAsItem.do", param, "/progress.do");
	}

	//#CM41154
	/** 
	 *called when "start location" button is clicked<BR>
	 * <BR>
	 * overview :: set search condition to parameter and display item code list search result based on it<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       warehouse No <BR>
	 *       consignor code <BR>
	 *       start location <BR>
	 *       end location <BR>
	 *       start item code <BR>
	 *       end item code <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_PSearchEndItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM41155
		// set search conditions in location search screen
		ForwardParameters param = new ForwardParameters();
		//#CM41156
		// warehouse No
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM41157
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM41158
		// start location
		param.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, 
								DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), 
								txt_StartLocation.getString()));
		//#CM41159
		// end location
		param.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, 
								DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), 
								txt_EndLocation.getString()));
		//#CM41160
		// start item code
		param.setParameter(ListAsItemBusiness.STARTITEM_KEY, txt_StartItemCode.getText());
		//#CM41161
		// end item code
		param.setParameter(ListAsItemBusiness.ENDITEM_KEY, txt_EndItemCode.getText());
		//#CM41162
		// end flag
		param.setParameter(ListAsItemBusiness.RANGEITEM_KEY, AsScheduleParameter.RANGE_END);
		//#CM41163
		// search flag
		param.setParameter(ListAsItemBusiness.SEARCHITEM_KEY, AsScheduleParameter.SEARCHFLAG_STOCK);
		//#CM41164
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsitem/ListAsItem.do", param, "/progress.do");
	}

	//#CM41165
	/**
	 * called when "Set" button is clicked<BR>
	 * <BR>
	 * overview ::set using the input contents<BR>
	 * <BR>
	 * <DIR>
	 *	  1.set cursor in worker code<BR>
	 *    2.display a dialog box and confirm it before starting stock confirmation<BR>
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
	 *                      worker code <BR>
	 *                      warehouse <BR>
	 *                      workplace <BR>
	 *                      station no. <BR>
	 *                      consignor code <BR>
	 *                      start location <BR>
	 * 						end location <BR>
	 * 						start item code <BR>
	 *						end item code <BR>
	 *						list print flag <BR>
	 *	 				</DIR>
	 *				</DIR>
	 * 				<BR>
	 * 				2.reset [stock confirmation] when there is no stock confirmation work available for the aisle<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_Setting_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		
		//#CM41166
		// mandatory input check
		txt_WorkerCode.validate();
		txt_Password.validate();
		
		//#CM41167
		// pattern matching character
		txt_ConsignorCode.validate(false);
		txt_StartItemCode.validate(false);
		txt_EndItemCode.validate(false);

		WmsScheduler schedule = new StartInventoryCheckSCH();

		try
		{			
			//#CM41168
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM41169
			// set the cursor in worker code input
			setFocus(txt_WorkerCode);
			
			//#CM41170
			// input check
			if(!checkInputData())
			{
				return;
			}
			
			//#CM41171
			// return the modify status to default
			this.getViewState().setInt("LineNo", -1);

			//#CM41172
			// set schedule parameter
			AsScheduleParameter[] param  = null ;

			//#CM41173
			// set all the data to preset area
			param = setParam();

			//#CM41174
			// start schedule
			if (schedule.startSCH(conn, param))
			{
				//#CM41175
				// commit if the result is true
				conn.commit();

				//#CM41176
				// while sending picking send instruction, use RMI message for picking request
				SendRequestor req = new SendRequestor();
				req.retrieval();

				//#CM41177
				// set message
				message.setMsgResourceKey(schedule.getMessage());

				//#CM41178
				// return the modify status to default
				this.getViewState().setInt("LineNo", -1);

				//#CM41179
				// Set button
				btn_Setting.setEnabled(true);
				//#CM41180
				// clear button
				btn_Clear.setEnabled(true);
			}
			else
			{
				conn.rollback();
				//#CM41181
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}
		}
		catch(Exception ex)
		{		
			ex.printStackTrace();
			//#CM41182
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

				//#CM41183
				// set schedule parameter
				AsScheduleParameter[] param  = null ;

				param = setParam();
				AsScheduleParameter rparam = new AsScheduleParameter();
				rparam = param[0];

				//#CM41184
				// reset [stock confirmation] when there is no stock confirmation work available for the aisle
				schedule.nextCheck(conn, rparam);

				//#CM41185
				// commit transaction
				conn.commit();
				//#CM41186
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

	//#CM41187
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
	 * 				consignor code[if search results a single consignor, display it] <BR>
	 * 				start location[nil] <BR>
	 * 				end location[nil] <BR>
	 * 				start item code[nil] <BR>
	 * 				end item code[nil] <BR>
	 * 				print stock confirmation work list[true] <BR>
	 * 	 		</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM41188
		// Set button
		btn_Setting.setEnabled(true);
		//#CM41189
		// list clear button
		btn_Clear.setEnabled(true);

		//#CM41190
		// return the modify status to default
		this.getViewState().setInt("LineNo", -1);

		//#CM41191
		// initial display
		setFirstDisp();

		//#CM41192
		// set the cursor in worker code input
		setFocus(txt_WorkerCode);
	}


}
//#CM41193
//end of class
