// $Id: AsUnavailableLocationBusiness.java,v 1.2 2006/10/26 04:56:31 suresh Exp $

//#CM37459
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsunavailablelocation;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstockdetailnobtn.ListAsStockDetailNoBtnBusiness;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.asrs.schedule.AsUnavailableLocationSCH;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.entity.WareHouse;

//#CM37460
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * The ASRS restricted location setting screen class.<BR>
 * set the input contents from the screen to a parameter
 * The schedule changes to status or restricted location status which can use a corresponding location based on the parameter Process.<BR>
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
 *  The content input from input area is set in the parameter, and the schedule changes shelf status based on the parameter Process. <BR>
 *  Receive true when the result is received from the schedule, and processing is completed normally. 
 *  Receive false when it does not complete the schedule because of the condition error etc.<BR>
 *  output the schedule result, schedule message to screen<BR>
 * 	<DIR>
 *   [parameter] *mandatory input <BR>
 *   <BR>
 * 		worker code<BR>
 * 		password<BR>
 * 		warehouseNo<BR>
 * 		location no.<BR>
 * 		Change status<BR>
 * 		terminal no.<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/9/29</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:56:31 $
 * @author  $Author: suresh $
 */
public class AsUnavailableLocationBusiness extends AsUnavailableLocation implements WMSConstants
{
	//#CM37461
	// Class fields --------------------------------------------------

	//#CM37462
	// Class variables -----------------------------------------------

	//#CM37463
	// Class method --------------------------------------------------

	//#CM37464
	// Constructors --------------------------------------------------

	//#CM37465
	// Public methods ------------------------------------------------

	//#CM37466
	/**
	 * screen initialization<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * <BR>
	 * <DIR>
	 * 		1.initialize pulldown <BR>
	 * 		2.set the cursor in worker code<BR>
	 * 		<BR>
	 * 		item[initial value] <BR>
	 * 		<DIR>
	 * 			worker code[nil]<BR>
	 * 			password[nil]<BR>
	 * 			location[nil]<BR>
	 * 			status[enabled]<BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM37467
		// set the focus in worker code input
		setFocus(txt_WorkerCode);
		
		Connection conn = null;
		try
		{
			//#CM37468
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM37469
			// fetch Http Locale
			Locale locale = this.getHttpRequest().getLocale();

			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			//#CM37470
			// pull down display data(storage area)
			String[] areaid = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);

			//#CM37471
			// set pulldown data
			PulldownHelper.setPullDown(pul_WareHouse, areaid);
			//#CM37472
			// initial display
			setFirstDisp();

		}
		catch (Exception ex)
		{
			//#CM37473
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
				//#CM37474
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

	//#CM37475
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
		if (menuparam != null)
		{
			//#CM37476
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);

			//#CM37477
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);

			//#CM37478
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}
		//#CM37479
		// confirmation dialog message MSG-9000= Do you set?
		btn_Setting.setBeforeConfirm("MSG-9000");
	}

	//#CM37480
	// Package methods -----------------------------------------------

	//#CM37481
	// Protected methods ---------------------------------------------
	//#CM37482
	// Private methods -----------------------------------------------
	//#CM37483
	/**
	 * This method initializes screen<BR>
	 * <BR>
	 * overview ::initial screen display <BR>
	 * <DIR>
	 * 		[parameter] <BR>
	 * 		<DIR>
	 * 			item name[initial value] <BR>
	 * 			<DIR>
	 * 				location[nil] <BR>
	 * 				location status[enabled] <BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * @throws Exception report all the exceptions
	 */
	private void setFirstDisp() throws Exception
	{
		//#CM37484
		// location no.
		txt_location.setText("");
		//#CM37485
		// Clear status
		rdo_Status_Possible.setChecked(true);
		rdo_Status_Prohibition.setChecked(false);
	}

	//#CM37486
	// Event handler methods -----------------------------------------
	//#CM37487
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

	//#CM37488
	/** 
	 * This method is called when the location detail button is clicked<BR>
	 * <BR>
	 * overview ::set search condition in parameter, Displays the location detail list box. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *    	warehouse No<BR>
	 *    	warehouse name<BR>
	 *      location<BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_LocationDetails_Click(ActionEvent e) throws Exception
	{
		//#CM37489
		//Set the search condition in the location details list display. 
		ForwardParameters param = new ForwardParameters();
		param.setParameter(ListAsStockDetailNoBtnBusiness.WAREHOUSE_KEY, pul_WareHouse.getSelectedValue());
		param.setParameter(ListAsStockDetailNoBtnBusiness.WAREHOUSENAME_KEY, 
												pul_WareHouse.getSelectedItem().getText());
		param.setParameter(ListAsStockDetailNoBtnBusiness.LOCATION_KEY, DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_location.getString()));

		//#CM37490
		//Shelf details list display
		redirect("/asrs/listbox/listasrsstockdetailnobtn/ListAsStockDetailNoBtn.do", param, "/progress.do");	
	}

	//#CM37491
	/** 
	 * It is called when Submit Button is pressed.<BR>
	 * <BR>
	 * Abstract :Change location status by information on input area Process. <BR>
	 * <BR>
	 * <DIR>
	 *	  1.set cursor in worker code<BR>
	 *    2.Display the dialog box, and confirm whether to set it. <BR>
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
	 * 						warehouse No<BR>
	 * 						location no.<BR>
	 * 						Change status<BR>
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
		//#CM37492
		// set the focus in worker code input
		setFocus(txt_WorkerCode);

		//#CM37493
		// input check
		txt_WorkerCode.validate();
		txt_Password.validate();
		
		//#CM37494
		// location no. input check (mandatory)
		txt_location.validate();

		Connection conn = null;

		try
		{
			//#CM37495
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			//#CM37496
			//save the AGC No. against selected checkbox
			Vector vecParam = new Vector();
			AsScheduleParameter param = new AsScheduleParameter();
			
			//#CM37497
			// worker code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM37498
			// password
			param.setPassword(txt_Password.getText());
			
			//#CM37499
			// set warehouse no.
			param.setWareHouseNo(pul_WareHouse.getSelectedValue());
			//#CM37500
			// set location no.
			param.setLocationNo(DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_location.getString()));
			//#CM37501
			// set change status
			if (rdo_Status_Possible.getChecked())
			{
				param.setSelectLocationStatus(AsScheduleParameter.ASRS_LOCATIONSTATUS_POSSIBLE);
			}
			else if (rdo_Status_Prohibition.getChecked())
			{
				param.setSelectLocationStatus(AsScheduleParameter.ASRS_LOCATIONSTATUS_PROHIBITION);
			}
			//#CM37502
			// terminal no.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());

			vecParam.addElement(param);

			AsScheduleParameter[] paramArray = new AsScheduleParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			WmsScheduler schedule = new AsUnavailableLocationSCH();
		
			//#CM37503
			// start schedule
			if (schedule.startSCH(conn, paramArray))
			{
				//#CM37504
				// commit if the result is true
				conn.commit();
				//#CM37505
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.rollback();
				//#CM37506
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}
		}
		catch (Exception ex)
		{
			//#CM37507
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
				//#CM37508
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

	//#CM37509
	/**
	 * called when clear button is clicked<BR>
	 * <BR>
	 * Abstract :clear the input area<BR>
	 * <BR>
	 * <DIR>
	 * 		1.set the cursor in worker code<BR>
	 * 		2.return input area items to initial state<BR>
	 * 		<DIR>
	 *   	item name[initial value]<BR>
	 *   		<DIR>
	 * 				worker code[as it is]<BR>
	 * 				password[as it is]<BR>
	 * 				warehouse[The first data]
	 * 				location[nil] <BR>
	 * 				location status[enabled] <BR>
	 * 	 		</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM37510
		// set the focus in worker code input
		setFocus(txt_WorkerCode);
		
		pul_WareHouse.setSelectedIndex(0);
		setFirstDisp();
	}

	//#CM37511
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37512
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37513
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Set_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37514
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37515
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Worker_Code_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37516
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37517
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37518
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37519
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM37520
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37521
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37522
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37523
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37524
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM37525
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37526
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37527
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Change(ActionEvent e) throws Exception
	{
	}

	//#CM37528
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LocationNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37529
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_location_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37530
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_location_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37531
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_location_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37532
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_LocationDetails_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37533
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Status_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37534
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Status_Prohibition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37535
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Status_Prohibition_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37536
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Status_Possible_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37537
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Status_Possible_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37538
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Setting_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37539
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM37540
//end of class
