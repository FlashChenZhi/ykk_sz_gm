// $Id: AsStorageListBusiness.java,v 1.2 2006/10/26 04:55:34 suresh Exp $

//#CM37339
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsstoragelist;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.DateOperator;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.asrs.schedule.AsStorageListSCH;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.WareHouse;

//#CM37340
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * The screen class which does the ASRSStorage work list print.<BR>
 * Hand over the parameter to the schedule which does the ASRS Storage work list print.<BR>
 * <BR>
 * <DIR>
 * 		1.Processing when Print button is pressed(<CODE>btn_Print_Click<CODE>) <BR>
 * 		<BR>
 * 		<DIR>
 * 			set the input contents from the screen to a parameter
 * 			The schedule retrieves and prints data based on the parameter. <BR>
 * 			The schedule must return true when it succeeds in the print and return false when failing. <BR>
 * 			<BR>
 * 			[parameter] *mandatory input<BR>
 * 			<DIR>
 * 				stationNo*<BR>
 * 				Retrieval start date/time<BR>
 * 				Retrieval end date/time<BR>
 * 				Work flag*<BR>
 * 			</DIR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:55:34 $
 * @author  $Author: suresh $
 */
public class AsStorageListBusiness extends AsStorageList implements WMSConstants
{
	//#CM37341
	// Class fields --------------------------------------------------

	//#CM37342
	// Class variables -----------------------------------------------
	private static final String STATION_STORAGE = "10";

	//#CM37343
	/**
	 * The dialog called from which control to be maintained:Print button
	 */
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";
	
	//#CM37344
	/**
	 * StationNo maintenance area
	 */
	protected static final String STATIONNO_KEY = "STATIONNO_KEY";

	//#CM37345
	// Class method --------------------------------------------------

	//#CM37346
	// Constructors --------------------------------------------------

	//#CM37347
	// Public methods ------------------------------------------------

	//#CM37348
	/**
	 * screen initialization<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * <BR>
	 * <DIR>
	 * 		1.initialize pulldown <BR>
	 * 		2.set the cursor in warehouse<BR>
	 * 		<BR>
	 * 		item[initial value] <BR>
	 * 		<DIR>
	 * 			Retrieval start date[nil]<BR>
	 * 			Retrieval Start time[nil]<BR>
	 * 			Retrieval end date[nil]<BR>
	 * 			Retrieval End time[nil]<BR>
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
			//#CM37349
			// To judge whether it is update status by the Modify button pressing, line No is set in ViewState. 
			//#CM37350
			// (default:-1)
			this.getViewState().setInt("LineNo", -1);
			//#CM37351
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM37352
			// fetch Http Locale
			Locale locale = this.getHttpRequest().getLocale();

			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			//#CM37353
			// pull down display data(storage area)
			String[] areaid = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);
			//#CM37354
			// pull down display data(workplace)
			String[] sagyoba = pull.getSagyobaPulldownData(conn, STATION_STORAGE, false, "");
			//#CM37355
			// pull down display data(station)
			String[] stno = pull.getStationPulldownData(conn, STATION_STORAGE, false, "");
			//#CM37356
			// pull down display data(work type)
			String[] jobtype = pull.getWorkType(CarryInformation.CARRYKIND_STORAGE);

			//#CM37357
			// set pulldown data
			PulldownHelper.setPullDown(pul_WareHouse, areaid);
			PulldownHelper.setLinkedPullDown(pul_WorkPlace, sagyoba);
			PulldownHelper.setLinkedPullDown(pul_Station, pul_WorkPlace, stno);
			PulldownHelper.setPullDown(pul_WorkType, jobtype);

			//#CM37358
			// add child pull down
			pul_WareHouse.addChild(pul_WorkPlace);
			pul_WorkPlace.addChild(pul_Station);
			//#CM37359
			// set initial focus to warehouse pulldown
			setFocus(pul_WareHouse);
			//#CM37360
			// initial display info
			btn_Clear_Click(null);
		}
		catch (Exception ex)
		{
			//#CM37361
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
				//#CM37362
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

	//#CM37363
	/**
	 * call this before calling the respective control events<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM37364
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM37365
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM37366
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}

	}

	//#CM37367
	/**
	 * This method is called when returning from the dialog button.<BR>
	 * Override <CODE>page_ConfirmBack</CODE> defined in <CODE>Page</CODE>.<BR>
	 * <BR>
	 * Abstract :Execute the processing of the correspondence when [Yes] is selected by the dialog. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.set the cursor in warehouse
	 * 	    2.Check from which dialog return. <BR>
	 *      3.Check whether the selected item from dialog is [Yes] or [No].<BR>
	 *      4.Begin scheduling when yes is selected. <BR>
	 *      5.Display the result of the schedule in the message area. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		try
		{
			//#CM37368
			// set the cursor in warehouse input
			setFocus(pul_WareHouse);

			//#CM37369
			// Check from which dialog return. 
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM37370
			// True when pushed button is [True]
			//#CM37371
			// False when [No] is pressed
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM37372
			// End processing when [No] is pressed. 
			//#CM37373
			// The set of the message here is unnecessary. 
			if (!isExecute)
			{
				return;
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			//#CM37374
			// Turn off the flag because the operation of the dialog ended. 
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM37375
		// Start the print schedule. 
		Connection conn = null;
		try
		{
			//#CM37376
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM37377
			// set input value to parameter
			AsScheduleParameter[] param = new AsScheduleParameter[1];
			param[0] = createParameter(conn);
			
			//#CM37378
			//start schedule
			WmsScheduler schedule = new AsStorageListSCH();
			schedule.startSCH(conn, param);
			
			//#CM37379
			// set message
			message.setMsgResourceKey(schedule.getMessage());
	
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
	
		}
		finally
		{
			try
			{
				//#CM37380
				// close the connection object
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
	
		}
		
	}
	
	//#CM37381
	// Package methods -----------------------------------------------

	//#CM37382
	// Protected methods ---------------------------------------------

	//#CM37383
	// Private methods -----------------------------------------------

	//#CM37384
	// Event handler methods -----------------------------------------
	//#CM37385
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

	//#CM37386
	/** 
	 * It is called when Print button is pressed.<BR>
	 * <BR>
	 * Abstract :The input item of input area is set in the parameter and display confirmation dialog whether print after the print number is acquired. 
	 * whether print after the print number is acquired. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.set the cursor in warehouse input<BR>
	 * 		2.Check the number of print objects. <BR>
	 * 		3-1.Display confirmation dialog when there is data for print<BR>
	 * 		3-2.Acquire the message when there is no data for the print and display it. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		//#CM37387
		// set the cursor in warehouse input
		setFocus(pul_WareHouse);

		//#CM37388
		// input check
		txt_FromDate.validate(false);
		txt_FromTime.validate(false);
		txt_ToDate.validate(false);
		txt_ToTime.validate(false);
		
		
		Connection conn = null;
		
		try
		{
			//#CM37389
			// set initial focus to warehouse pulldown
			setFocus(pul_WareHouse);
			//#CM37390
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM37391
			// start date/time should be less than end date/time
			Date mintime = DateOperator.parse("000000", "HHmmss");
			Date maxtime = DateOperator.parse("235959", "HHmmss");
			ASFindUtil findutil = new ASFindUtil(conn);
			//#CM37392
			// start date/time
			Date fromdate = new Date(0);
			if (!StringUtil.isBlank(txt_FromDate.getDate()))
			{
				if (!StringUtil.isBlank(txt_FromTime.getTime()))
				{
					fromdate = findutil.getDate(txt_FromDate.getDate(), txt_FromTime.getTime());
				}
				else
				{
					fromdate = findutil.getDate(txt_FromDate.getDate(), mintime);
				}
			}
			else
			{
				if (!StringUtil.isBlank(txt_FromTime.getTime()))
				{
					//#CM37393
					// enter start date
					message.setMsgResourceKey("6023445");
					return;
				}
			}
			//#CM37394
			// end date/time
			Date todate = new Date(0);
			if (!StringUtil.isBlank(txt_ToDate.getDate()))
			{
				if (!StringUtil.isBlank(txt_ToTime.getTime()))
				{
					todate = findutil.getDate(txt_ToDate.getDate(), txt_ToTime.getTime());
				}
				else
				{
					todate = findutil.getDate(txt_ToDate.getDate(), maxtime);
				}
			}
			else
			{
				if (!StringUtil.isBlank(txt_ToTime.getTime()))
				{
					//#CM37395
					// enter end date
					message.setMsgResourceKey("6023446");
					return;
				}
			}
			//#CM37396
			// check start date/time and end date/time
			if (!StringUtil.isBlank(txt_FromDate.getDate()))
			{
				if (!StringUtil.isBlank(txt_ToDate.getDate()))
				{
					if (fromdate.after(todate))
					{
						//#CM37397
						// Make Retrieval start date/time the date before Retrieval end date/time. 
						message.setMsgResourceKey("6023182");
						return;
					}
				}
			}

			//#CM37398
			// save the station no.
			this.getViewState().setString(STATIONNO_KEY, pul_Station.getSelectedValue());				
			
			//#CM37399
			// Set the schedule parameter. 
			AsScheduleParameter param = createParameter(conn);
			
			//#CM37400
			//start schedule
			WmsScheduler schedule = new AsStorageListSCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM37401
				// MSG-W0061={0}The matter corresponded. <BR> Do print?
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM37402
				// Memorize the dialog display by Print button. 
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM37403
				// set message
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
				//#CM37404
				// close the connection object
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
	}

	//#CM37405
	/** 
	 * called when clear button is clicked<BR>
	 * <BR>
	 * Abstract :clear the input area<BR>
	 * and set the cursor in warehouse<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM37406
		// Clear display information
		//#CM37407
		// warehouse
		pul_WareHouse.setSelectedIndex(0);
		//#CM37408
		// workplace
		pul_WorkPlace.setSelectedIndex(0);
		//#CM37409
		// station
		pul_Station.setSelectedIndex(0);
		//#CM37410
		// start date
		txt_FromDate.setText("");
		//#CM37411
		// Start time
		txt_FromTime.setText("");
		//#CM37412
		// end date
		txt_ToDate.setText("");
		//#CM37413
		// End time
		txt_ToTime.setText("");
		//#CM37414
		// work type
		pul_WorkType.setSelectedIndex(0);

		//#CM37415
		// set the cursor in warehouse input
		setFocus(pul_WareHouse);
	}
	
	//#CM37416
	/**
	 * Generate the parameter which sets the input value of input area. <BR>
	 * @param conn Connection object with data base
	 * @return parameter object containing the input value
	 * @throws InvalidStatusException It is notified when status outside the range is set. 
	 */
	protected AsScheduleParameter createParameter(Connection conn) throws InvalidStatusException
	{
		AsScheduleParameter param = new AsScheduleParameter();
		ASFindUtil findutil = new ASFindUtil(conn);

		//#CM37417
		// Work station
		param.setStationNo(this.getViewState().getString(STATIONNO_KEY));
		Date mintime = DateOperator.parse("000000", "HHmmss");
		Date maxtime = DateOperator.parse("235959", "HHmmss");
		//#CM37418
		// start date/time
		if (!StringUtil.isBlank(txt_FromDate.getDate()))
		{
			Date fromdate = new Date(0);
			if (!StringUtil.isBlank(txt_FromTime.getTime()))
			{
				fromdate = findutil.getDate(txt_FromDate.getDate(), txt_FromTime.getTime());
			}
			else
			{
				fromdate = findutil.getDate(txt_FromDate.getDate(), mintime);
			}
			param.setFromDate(fromdate);
		}
		//#CM37419
		// end date/time
		if (!StringUtil.isBlank(txt_ToDate.getDate()))
		{
			Date todate = new Date(0);
			if (!StringUtil.isBlank(txt_ToTime.getTime()))
			{
				todate = findutil.getDate(txt_ToDate.getDate(), txt_ToTime.getTime());
			}
			else
			{
				todate = findutil.getDate(txt_ToDate.getDate(), maxtime);
			}
			param.setToDate(todate);
		}
		//#CM37420
		// work type
		param.setJobType(pul_WorkType.getSelectedValue());
		return param;
	}
	//#CM37421
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37422
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37423
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Print_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37424
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37425
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37426
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37427
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Change(ActionEvent e) throws Exception
	{
	}

	//#CM37428
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkPlace_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37429
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkPlace_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37430
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkPlace_Change(ActionEvent e) throws Exception
	{
	}

	//#CM37431
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Station_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37432
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Station_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37433
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Station_Change(ActionEvent e) throws Exception
	{
	}

	//#CM37434
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DayTimeStart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37435
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37436
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37437
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37438
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37439
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromTime_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37440
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromTime_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37441
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37442
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DayTimeEnd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37443
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37444
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37445
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37446
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37447
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToTime_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37448
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToTime_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37449
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkClass_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37450
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkType_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37451
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkType_Change(ActionEvent e) throws Exception
	{
	}

	//#CM37452
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37453
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM37454
//end of class
