// $Id: AsOperationCycleBusiness.java,v 1.2 2006/10/26 04:46:43 suresh Exp $

//#CM36437
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsoperationcycle;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Date;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsoperationcycle.ListAsOperationCycleBusiness;
import jp.co.daifuku.wms.asrs.common.DateOperator;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;

//#CM36438
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * The ASRS operation results information screen class. <BR>
 * Inquire ASRS operation results information. <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:46:43 $
 * @author  $Author: suresh $
 */
public class AsOperationCycleBusiness extends AsOperationCycle implements WMSConstants
{
	//#CM36439
	// Class fields --------------------------------------------------

	//#CM36440
	// Class variables -----------------------------------------------

	//#CM36441
	// Class method --------------------------------------------------

	//#CM36442
	// Constructors --------------------------------------------------

	//#CM36443
	// Public methods ------------------------------------------------

	//#CM36444
	/**
	 * screen initialization<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * <BR>
	 * <DIR>
	 * 		1.initialize pulldown<BR>
	 * 		2.initialize input area<BR>
	 * 		3.set the cursor in warehouse<BR>
	 * 		<BR>
	 * 		item[initial value] <BR>
	 * 		<DIR>
	 * 			start date[nil] <BR>
	 * 			start time[nil] <BR>
	 * 			end date[nil] <BR>
	 * 			end time[nil] <BR>
	 * 			RM No.[nil] <BR>
	 * 			total no. of operation[nil] <BR>
	 * 			Storage Operations[nil] <BR>
	 * 			Picking Operations[nil] <BR>
	 * 			storage item qty[nil] <BR>
	 * 			picking item qty[nil] <BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM36445
		// Initialize the screen
		setInitView(true);

		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

		Connection conn = null;

		try
		{
			//#CM36446
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM36447
			// fetch Http Locale
			Locale locale = this.getHttpRequest().getLocale();

			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			//#CM36448
			// pull down display data(Warehouse)
			String[] areaid = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);

			//#CM36449
			// set pulldown data
			PulldownHelper.setPullDown(pul_WareHouse, areaid);
			//#CM36450
			// set initial focus to warehouse
			setFocus(pul_WareHouse);
		}
		catch (Exception ex)
		{
			//#CM36451
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
				//#CM36452
				// close connection
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM36453
	/**
	 * This method is called when returning from popup window<BR>
	 * override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE><BR>
	 * <BR>
	 * overview ::fetch the value from search screen and set it<BR>
	 * <BR>
	 * <DIR>
	 *      1.fetch the value set from popup search screen<BR>
	 *      2.set value if it is not null<BR>
	 *      3.set the cursor in warehouse input<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		
		//#CM36454
		// fetch parameter selected from listbox
		//#CM36455
		// RM No
		String rmno = param.getParameter(ListAsOperationCycleBusiness.RMNO_KEY);
		//#CM36456
		// total no. of operation
		String totalcount = param.getParameter(ListAsOperationCycleBusiness.TOTALCOUNT_KEY);
		//#CM36457
		// Storage Operations
		String storagecount = param.getParameter(ListAsOperationCycleBusiness.STORAGECOUNT_KEY);
		//#CM36458
		// Picking Operations
		String retrievalcount = param.getParameter(ListAsOperationCycleBusiness.RETRIEVALCOUNT_KEY);
		//#CM36459
		// storage item qty
		String storageitemcount = param.getParameter(ListAsOperationCycleBusiness.STORAGEITEMCOUNT_KEY);
		//#CM36460
		// picking item qty
		String retrievalitemcount = param.getParameter(ListAsOperationCycleBusiness.RETRIEVALITEMCOUNT_KEY);

		//#CM36461
		// set the value if not empty
		//#CM36462
		// RM No
		if (!StringUtil.isBlank(rmno))
		{
			lbl_JavaSetMachineNo.setText(rmno);
		}
		//#CM36463
		// total no. of operation
		if (!StringUtil.isBlank(totalcount))
		{
			lbl_JavaSetTotalCarryQty.setText(totalcount);
		}
		//#CM36464
		// Storage Operations
		if (!StringUtil.isBlank(storagecount))
		{
			lbl_JavaSetStorageCarryQty.setText(storagecount);
		}
		//#CM36465
		// Picking Operations
		if (!StringUtil.isBlank(retrievalcount))
		{
			lbl_JavaSetRetrievalCarryQty.setText(retrievalcount);
		}
		//#CM36466
		// storage item qty
		if (!StringUtil.isBlank(storageitemcount))
		{
			lbl_JavaSetStorageItemQty.setText(storageitemcount);
		}
		//#CM36467
		// picking item qty
		if (!StringUtil.isBlank(retrievalitemcount))
		{
			lbl_JavaSetRetrievalItemQty.setText(retrievalitemcount);
		}

		//#CM36468
		// if RM no. is displayed, the results are displayed
		//#CM36469
		// enable details button by item and clear button
		boolean disp = false;
		if (!StringUtil.isBlank(lbl_JavaSetMachineNo.getText()))
		{
			disp = true;
		}

		//#CM36470
		// details button by item
		btn_ItemDetails.setEnabled(disp);
		//#CM36471
		// clear button
		btn_Clear.setEnabled(disp);
		//#CM36472
		// set the cursor in warehouse input
		setFocus(pul_WareHouse);
	}

	//#CM36473
	/**
	 * call this before calling the respective control events<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM36474
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM36475
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM36476
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}

		//#CM36477
		// set the cursor in warehouse input
		setFocus(pul_WareHouse);
	}

	//#CM36478
	// Package methods -----------------------------------------------

	//#CM36479
	// Protected methods ---------------------------------------------

	//#CM36480
	// Private methods -----------------------------------------------
	//#CM36481
	/**
	 * This method initializes screen<BR>
	 * <BR>
	 * overview ::initial screen display <BR>
	 * <DIR>
	 * 		[parameter] <BR>
	 * 		<DIR>
	 * 			The date range is initialized for wkrClrFlg true and do not initialize the date range for false.  <BR>
	 * 			<DIR>
	 * 				item name[initial value] <BR>
	 * 				<DIR>
	 * 					start date[nil/as it is, while clicking clear button] <BR>
	 * 					start time[nil/as it is, while clicking clear button] <BR>
	 * 					end date[nil/as it is, while clicking clear button] <BR>
	 * 					end time[nil/as it is, while clicking clear button] <BR>
	 * 					RM No.[nil] <BR>
	 * 					total no. of operation[nil] <BR>
	 * 					Storage Operations[nil] <BR>
	 * 					Picking Operations[nil] <BR>
	 * 					storage item qty[nil] <BR>
	 * 					picking item qty[nil] <BR>
	 * 				</DIR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * @param wkrClrFlg Clear Flag
	 * @throws Exception report all the exceptions
	 */
	private void setInitView(boolean wkrClrFlg) throws Exception
	{
		//#CM36482
		// Initialize the input item. 
		if (wkrClrFlg)
		{
			//#CM36483
			// start date
			txt_DateFrom.setText("");
			//#CM36484
			// start time
			txt_TimeFrom.setText("");
			//#CM36485
			// end date
			txt_DateTo.setText("");
			//#CM36486
			// end time
			txt_TimeTo.setText("");
		}

		//#CM36487
		// RM No
		lbl_JavaSetMachineNo.setText("");
		//#CM36488
		// total no. of operation
		lbl_JavaSetTotalCarryQty.setText("");
		//#CM36489
		// Storage Operations
		lbl_JavaSetStorageCarryQty.setText("");
		//#CM36490
		// Picking Operations
		lbl_JavaSetRetrievalCarryQty.setText("");
		//#CM36491
		// storage item qty
		lbl_JavaSetStorageItemQty.setText("");
		//#CM36492
		// picking item qty
		lbl_JavaSetRetrievalItemQty.setText("");

		//#CM36493
		// Nullify the button. 
		//#CM36494
		// details button by item
		btn_ItemDetails.setEnabled(false);
		//#CM36495
		// clear button
		btn_Clear.setEnabled(false);
	}

	//#CM36496
	// Event handler methods -----------------------------------------
	//#CM36497
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36498
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36499
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Query_Click(ActionEvent e) throws Exception
	{
	}

	//#CM36500
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36501
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

	//#CM36502
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36503
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36504
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Change(ActionEvent e) throws Exception
	{
	}

	//#CM36505
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DayTimeRange_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36506
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DayFrom_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36507
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_DateFrom_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36508
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_DateFrom_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM36509
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_DateFrom_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM36510
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_TimeFrom_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36511
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TimeFrom_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36512
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TimeFrom_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM36513
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TimeFrom_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM36514
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36515
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DayTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36516
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_DateTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36517
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_DateTo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM36518
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_DateTo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM36519
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_TimeTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36520
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TimeTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36521
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TimeTo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM36522
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TimeTo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM36523
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Query_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36524
	/** 
	 * This method is called upon clicking the inquiry button<BR>
	 * <BR>
	 * overview ::Display the RM operation results screen by the set search condition in parameter doing and the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       warehouse <BR>
	 *       Retrieval start date/time<BR>
	 *       Retrieval end date/time<BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_Query_Click(ActionEvent e) throws Exception
	{
		//#CM36525
		// Set the search condition on the RM operation results screen. 
		ForwardParameters param = new ForwardParameters();

		String dateTo = "";
		String timeTo = "";
		if( txt_DateTo.getDate() == null )
		{
			if( txt_TimeTo.getTime() != null )
			{
				dateTo = DateOperator.changeDate(new Date());
				timeTo = txt_TimeTo.getText();
			}
		}
		else
		{
			dateTo = txt_DateTo.getText();
			if( txt_TimeTo.getTime() != null )
			{
				timeTo = txt_TimeTo.getText();
			}
			else
			{
				timeTo = "23:59:59";
			}
		}

		//#CM36526
		// warehouse
		param.setParameter(ListAsOperationCycleBusiness.WAREHOUSE_KEY, pul_WareHouse.getSelectedValue());

		//#CM36527
		// fetch locale
		Locale locale = this.getHttpRequest().getLocale();

		Date d = WmsFormatter.getTimeStampDate(txt_DateFrom.getText(), txt_TimeFrom.getText(), locale);
		//#CM36528
		// start date
		param.setParameter(ListAsOperationCycleBusiness.STARTDATE_KEY, WmsFormatter.toParamDate(d));
		//#CM36529
		// start time
		param.setParameter(ListAsOperationCycleBusiness.STARTTIME_KEY, WmsFormatter.toParamTime(d));

		d = WmsFormatter.getTimeStampDate(dateTo, timeTo, locale);
		//#CM36530
		// end date
		param.setParameter(ListAsOperationCycleBusiness.ENDDATE_KEY, WmsFormatter.toParamDate(d));
		//#CM36531
		// end time
		param.setParameter(ListAsOperationCycleBusiness.ENDTIME_KEY, WmsFormatter.toParamTime(d));

		//#CM36532
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsoperationcycle/ListAsOperationCycle.do", param, "/progress.do");
	}

	//#CM36533
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MachineNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36534
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetMachineNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36535
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_TotalCarryQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36536
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetTotalCarryQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36537
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageCarryQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36538
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetStorageCarryQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36539
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalCarryQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36540
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetRetrievalCarryQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36541
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageItemQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36542
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetStorageItemQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36543
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalItemQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36544
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetRetrievalItemQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36545
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ItemDetails_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36546
	/** 
	 * It is called when the Detail button according to the item is pressed.<BR>
	 * <BR>
	 * overview ::Display the Results details list according to item screen by the set search condition in parameter doing and the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       warehouse <BR>
	 *       Retrieval start date/time<BR>
	 *       Retrieval end date/time<BR>
	 *       RM No.<BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_ItemDetails_Click(ActionEvent e) throws Exception
	{
		//#CM36547
		// Set the search condition on the Results details list according to item screen. 
		ForwardParameters param = new ForwardParameters();
		String dateTo = "";
		String timeTo = "";
		if( txt_DateTo.getDate() == null )
		{
			if( txt_TimeTo.getTime() != null )
			{
				dateTo = DateOperator.changeDate(new Date());
				timeTo = txt_TimeTo.getText();
			}
		}
		else
		{
			dateTo = txt_DateTo.getText();
			if( txt_TimeTo.getTime() != null )
			{
				timeTo = txt_TimeTo.getText();
			}
			else
			{
				timeTo = "23:59:59";
			}
		}

		//#CM36548
		// warehouse
		param.setParameter(ListAsOperationCycleBusiness.WAREHOUSE_KEY, pul_WareHouse.getSelectedValue());

		//#CM36549
		// fetch locale
		Locale locale = this.getHttpRequest().getLocale();

		Date d = WmsFormatter.getTimeStampDate(txt_DateFrom.getText(), txt_TimeFrom.getText(), locale);
		//#CM36550
		// start date
		param.setParameter(ListAsOperationCycleBusiness.STARTDATE_KEY, WmsFormatter.toParamDate(d));
		//#CM36551
		// start time
		param.setParameter(ListAsOperationCycleBusiness.STARTTIME_KEY, WmsFormatter.toParamTime(d));
		d = WmsFormatter.getTimeStampDate(dateTo, timeTo, locale);
		//#CM36552
		// end date
		param.setParameter(ListAsOperationCycleBusiness.ENDDATE_KEY, WmsFormatter.toParamDate(d));
		//#CM36553
		// end time
		param.setParameter(ListAsOperationCycleBusiness.ENDTIME_KEY, WmsFormatter.toParamTime(d));

		//#CM36554
		// RM No
		param.setParameter(ListAsOperationCycleBusiness.RMNO_KEY, lbl_JavaSetMachineNo.getText());

		//#CM36555
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsoperationcycleitem/ListAsOperationCycleItem.do", param, "/progress.do");
	}

	//#CM36556
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36557
	/**
	 * called when clear button is clicked<BR>
	 * <BR>
	 * Abstract :clear the input area<BR>
	 * <BR>
	 * <DIR>
	 * 		1.return input area items to initial state<BR>
	 * 		<DIR>
	 *   	item name[initial value]<BR>
	 *   		<DIR>
	 * 				start date[as it is] <BR>
	 * 				start time[as it is] <BR>
	 * 				end date[as it is] <BR>
	 * 				end time[as it is] <BR>
	 * 				RM No.[nil] <BR>
	 * 				total no. of operation[nil] <BR>
	 * 				Storage Operations[nil] <BR>
	 * 				Picking Operations[nil] <BR>
	 * 				storage item qty[nil] <BR>
	 * 				picking item qty[nil] <BR>
	 * 	 		</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM36558
		// Clear results information. 
		setInitView(false);
	}

	//#CM36559
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartDateTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM36560
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndDateTime_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM36561
//end of class
