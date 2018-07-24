// $Id: AsRetrievalListBusiness.java,v 1.2 2006/10/26 04:47:53 suresh Exp $

//#CM36566
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsretrievallist;
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
import jp.co.daifuku.wms.asrs.schedule.AsRetrievalListSCH;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.WareHouse;

//#CM36567
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * The screen class which does the ASRS picking work list print.<BR>
 * Hand over the parameter to the schedule which does the ASRS picking work list print.<BR>
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
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:47:53 $
 * @author  $Author: suresh $
 */
public class AsRetrievalListBusiness extends AsRetrievalList implements WMSConstants
{
	//#CM36568
	// Class fields --------------------------------------------------

	//#CM36569
	// Class variables -----------------------------------------------

	//#CM36570
	/**
	 * Pulldown Retrieval
	 */
	private static final String STATION_RETRIEVAL = "11";

	//#CM36571
	/**
	 * The dialog called from which control to be maintained:Print button
	 */
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";
	
	//#CM36572
	/**
	 * StationNo maintenance area
	 */
	protected static final String STATIONNO_KEY = "STATIONNO_KEY";

	//#CM36573
	// Class method --------------------------------------------------

	//#CM36574
	// Constructors --------------------------------------------------

	//#CM36575
	// Public methods ------------------------------------------------

	//#CM36576
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
	 * 			Retrieval start date/time[nil]<BR>
	 * 			Retrieval end date/time[nil]<BR>
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
			//#CM36577
			// To judge whether it is update status by the Modify button pressing, line No is set in ViewState. 
			//#CM36578
			// (default:-1)
			this.getViewState().setInt("LineNo", -1);
			//#CM36579
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM36580
			// fetch Http Locale
			Locale locale = this.getHttpRequest().getLocale();

			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			//#CM36581
			// pull down display data(storage area)
			String[] areaid = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);
			//#CM36582
			// pull down display data(workplace)
			String[] sagyoba = pull.getSagyobaPulldownData(conn, STATION_RETRIEVAL, false, "");
			//#CM36583
			// pull down display data(station)
			String[] stno = pull.getStationPulldownData(conn, STATION_RETRIEVAL, false, "");
			//#CM36584
			// pull down display data(work type)
			String[] jobtype = pull.getWorkType(CarryInformation.CARRYKIND_RETRIEVAL);

			//#CM36585
			// set pulldown data
			PulldownHelper.setPullDown(pul_WareHouse, areaid);
			PulldownHelper.setLinkedPullDown(pul_WorkPlace, sagyoba);
			PulldownHelper.setLinkedPullDown(pul_Station, pul_WorkPlace, stno);
			PulldownHelper.setPullDown(pul_WorkType, jobtype);

			//#CM36586
			// add child pull down
			pul_WareHouse.addChild(pul_WorkPlace);
			pul_WorkPlace.addChild(pul_Station);
			//#CM36587
			// Initial focus in Warehouse
			setFocus(pul_WareHouse);
			//#CM36588
			// initial display info
			btn_Clear_Click(null);
		}
		catch (Exception ex)
		{
			//#CM36589
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
				//#CM36590
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

	//#CM36591
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
			//#CM36592
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM36593
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM36594
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}

	}

	//#CM36595
	// Package methods -----------------------------------------------

	//#CM36596
	// Protected methods ---------------------------------------------

	//#CM36597
	// Private methods -----------------------------------------------

	//#CM36598
	// Event handler methods -----------------------------------------
	//#CM36599
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36600
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36601
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Print_Click(ActionEvent e) throws Exception
	{
	}

	//#CM36602
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36603
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

	//#CM36604
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36605
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36606
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Change(ActionEvent e) throws Exception
	{
	}

	//#CM36607
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkPlace_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36608
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkPlace_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36609
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkPlace_Change(ActionEvent e) throws Exception
	{
	}

	//#CM36610
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Station_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36611
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Station_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36612
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Station_Change(ActionEvent e) throws Exception
	{
	}

	//#CM36613
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DayTimeRange_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36614
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromDay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36615
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36616
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM36617
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM36618
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36619
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36620
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromTime_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM36621
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FromTime_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM36622
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36623
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ToDay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36624
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36625
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM36626
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM36627
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ToTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36628
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36629
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToTime_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM36630
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ToTime_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM36631
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkType_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36632
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkType_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36633
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkType_Change(ActionEvent e) throws Exception
	{
	}

	//#CM36634
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36635
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
		//#CM36636
		// set the cursor in warehouse input
		setFocus(pul_WareHouse);
		//#CM36637
		// input check
		txt_FromDate.validate(false);
		txt_FromTime.validate(false);
		txt_ToDate.validate(false);
		txt_ToTime.validate(false);
		
		
		Connection conn = null;
		
		try
		{
			//#CM36638
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM36639
			// Start date/time is smaller than the end date/time. 
			Date mintime = DateOperator.parse("000000", "HHmmss");
			Date maxtime = DateOperator.parse("235959", "HHmmss");
			ASFindUtil findutil = new ASFindUtil(conn);
			//#CM36640
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
					//#CM36641
					// enter start date
					message.setMsgResourceKey("6023445");
					return;
				}
			}
			//#CM36642
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
					//#CM36643
					// enter end date
					message.setMsgResourceKey("6023446");
					return;
				}
			}
			//#CM36644
			// check start date/time and end date/time
			if (!StringUtil.isBlank(txt_FromDate.getDate()))
			{
				if (!StringUtil.isBlank(txt_ToDate.getDate()))
				{
					if (fromdate.after(todate))
					{
						//#CM36645
						// Make Retrieval start date/time the date before Retrieval end date/time. 
						message.setMsgResourceKey("6023182");
						return;
					}
				}
			}

			//#CM36646
			// save the station no.
			this.getViewState().setString(STATIONNO_KEY, pul_Station.getSelectedValue());				

			//#CM36647
			// Set the schedule parameter. 
			AsScheduleParameter param = createParameter(conn);
			
			//#CM36648
			//start schedule
			WmsScheduler schedule = new AsRetrievalListSCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM36649
				// MSG-W0061={0}The matter corresponded. <BR> Do print?
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM36650
				// Memorize the dialog display by Print button. 
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM36651
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
				//#CM36652
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

	//#CM36653
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36654
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
		//#CM36655
		// Clear display information
		//#CM36656
		// warehouse
		pul_WareHouse.setSelectedIndex(0);
		//#CM36657
		// workplace
		pul_WorkPlace.setSelectedIndex(0);
		//#CM36658
		// station
		pul_Station.setSelectedIndex(0);
		//#CM36659
		// start date
		txt_FromDate.setText("");
		//#CM36660
		// Start time
		txt_FromTime.setText("");
		//#CM36661
		// end date
		txt_ToDate.setText("");
		//#CM36662
		// End time
		txt_ToTime.setText("");
		//#CM36663
		// work type
		pul_WorkType.setSelectedIndex(0);

		//#CM36664
		// set the cursor in warehouse input
		setFocus(pul_WareHouse);
	}
	
	//#CM36665
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
			//#CM36666
			// set the cursor in warehouse input
			setFocus(pul_WareHouse);

			//#CM36667
			// Check from which dialog return. 
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM36668
			// True when pushed button is [True]
			//#CM36669
			// False when [No] is pressed
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM36670
			// End processing when [No] is pressed. 
			//#CM36671
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
			//#CM36672
			// Turn off the flag because the operation of the dialog ended. 
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM36673
		// Start the print schedule. 
		Connection conn = null;
		try
		{
			//#CM36674
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM36675
			// set input value to parameter
			AsScheduleParameter[] param = new AsScheduleParameter[1];
			param[0] = createParameter(conn);
			
			//#CM36676
			//start schedule
			WmsScheduler schedule = new AsRetrievalListSCH();
			schedule.startSCH(conn, param);
			
			//#CM36677
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
				//#CM36678
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
	
	//#CM36679
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

		//#CM36680
		// Work station
		param.setStationNo(this.getViewState().getString(STATIONNO_KEY));
		Date mintime = DateOperator.parse("000000", "HHmmss");
		Date maxtime = DateOperator.parse("235959", "HHmmss");
		//#CM36681
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
		//#CM36682
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
		//#CM36683
		// work type
		param.setJobType(pul_WorkType.getSelectedValue());
		return param;
	}
	//#CM36684
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DayTimeStart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36685
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DayTimeEnd_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM36686
//end of class
