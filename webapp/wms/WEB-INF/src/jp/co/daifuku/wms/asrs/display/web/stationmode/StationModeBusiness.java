// $Id: StationModeBusiness.java,v 1.2 2006/10/26 08:07:34 suresh Exp $

//#CM41363
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.stationmode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.asrs.display.web.listbox.liststationstatus.ListStationStatusBusiness;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.asrs.schedule.StationModeSCH;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.entity.Station;

//#CM41364
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * This is ASRSstation mode setting screen class<BR>
 * set the input contents from the screen to a parameter
 * using that parameter change the station mode<BR>
 * output the schedule result, schedule message to screen<BR>
 * and transaction commit and rollback are done in this screen<BR>
 * <BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.set button click event(<CODE>btn_Setting_Click</CODE>method ) <BR>
 * <BR>
 * <DIR>
 *  set the input area contents to parameter and change station mode<BR>
 *  output the schedule result, schedule message to screen<BR>
 * 	<DIR>
 *   [parameter] *mandatory input <BR>
 *   <BR>
 * 		worker code *<BR>
 * 		password *<BR>
 * 		station no..*<BR>
 * 		selected operation mode*<BR>
 * 		selected work mode*<BR>
 * 		terminal no.<BR>
 *  </DIR>
 *  [data for output use] <BR>
 *  <BR>
 * 	<DIR>
 * 		station no. <BR>
 *  	station name <BR>
 *      machine status <BR>
 *      work status <BR>
 *      work qty <BR>
 *      operation mode name <BR>
 *      work mode name <BR>
 *      work mode change request name<BR>
 *      operation mode <BR>
 *      work mode name <BR>
 *      work mode change request <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.refresh button process(<CODE>btn_Reflesh_Click</CODE>method ) <BR>
 * <BR>
 * <DIR>
 *  set the input area contents to parameter and edit the station status<BR>
 *  output the schedule result, schedule message to screen<BR>
 * 	<DIR>
 *   [parameter] *mandatory input <BR>
 *   <BR>
 * 		stationNo* <BR>
 *  </DIR>
 *  <BR>
 *  [data for output use] <BR>
 *  <BR>
 * 	<DIR>
 * 		station no. <BR>
 *  	station name <BR>
 *      machine status <BR>
 *      work status <BR>
 *      work qty <BR>
 *      operation mode name <BR>
 *      work mode name <BR>
 *      work mode change request name<BR>
 *      operation mode <BR>
 *      work mode name <BR>
 *      work mode change request <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/9/29</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:07:34 $
 * @author  $Author: suresh $
 */
public class StationModeBusiness extends StationMode implements WMSConstants
{
	//#CM41365
	// Class fields --------------------------------------------------
	//#CM41366
	/** 
	 * this key is used to store station no.
	 */
	public static final String DISP_STATIONNO_KEY = "DISP_STATIONNO_KEY";

	//#CM41367
	// Class variables -----------------------------------------------

	//#CM41368
	// Class method --------------------------------------------------

	//#CM41369
	// Constructors --------------------------------------------------

	//#CM41370
	// Public methods ------------------------------------------------

	//#CM41371
	/**
	 * screen initialization<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * <BR>
	 * <DIR>
	 * 		1.set the cursor in worker code<BR>
	 * 		2.screen initialization<BR>
	 * 		<BR>
	 * 		item[initial value] <BR>
	 * 		<DIR>
	 * 			worker code[nil] <BR>
	 * 			password[nil] <BR>
	 * 			station no.[nil] <BR>
	 *  		station name[nil] <BR>
	 * 			machine status[nil] <BR>
	 * 			work status[nil] <BR>
	 * 			work qty[nil] <BR>
	 * 			operation mode[nil] <BR>
	 * 			work mode name[nil] <BR>
	 * 			work mode change request[nil] <BR>
	 * 			operation mode radio button[operation impossible] <BR>
	 * 			work mode radio button[operation impossible] <BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM41372
		// set the focus in worker code input
		setFocus(txt_WorkerCode);
		
		//#CM41373
		// clear process
		btn_Clear_Click(null);
	}

	//#CM41374
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
			//#CM41375
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);

			//#CM41376
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);

			//#CM41377
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}
		//#CM41378
		// confirmation dialog message MSG-9000= Do you set?
		btn_Setting.setBeforeConfirm("MSG-9000");
	}

	//#CM41379
	/**
	 * this method is called<BR>
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
		//#CM41380
		// set the focus in worker code input
		setFocus(txt_WorkerCode);
		
		DialogParameters param = ((DialogEvent) e).getDialogParameters();

		//#CM41381
		// fetch parameter selected from listbox
		//#CM41382
		// station no.
		String wStationNo = param.getParameter(ListStationStatusBusiness.STATIONNO_KEY);
		//#CM41383
		// station name
		String wStationName = param.getParameter(ListStationStatusBusiness.STATIONNAME_KEY);
		//#CM41384
		// operation mode
		String wModeType = param.getParameter(ListStationStatusBusiness.MODETYPE_KEY);
		//#CM41385
		// operation mode name
		String wModeTypeName = param.getParameter(ListStationStatusBusiness.MODETYPENAME_KEY);
		//#CM41386
		// work mode name
		String wCurrentMode = param.getParameter(ListStationStatusBusiness.CURRENTMODE_KEY);
		//#CM41387
		// work mode name
		String wCurrentModeName = param.getParameter(ListStationStatusBusiness.CURRENTMODENAME_KEY);
		//#CM41388
		// machine status name
		String wStatusName = param.getParameter(ListStationStatusBusiness.STATUSNAME_KEY);
		//#CM41389
		// work status name
		String wSuspendName = param.getParameter(ListStationStatusBusiness.SUSPENDNAME_KEY);
		//#CM41390
		// work qty
		String wWorkingCount = param.getParameter(ListStationStatusBusiness.WORKINGCOUNT_KEY);
		//#CM41391
		// work mode change request name
		String wModeRequestName = param.getParameter(ListStationStatusBusiness.MODEREQUESTNAME_KEY);

		//#CM41392
		// set the value if not empty
		//#CM41393
		// station no.
		if (!StringUtil.isBlank(wStationNo))
		{
			lbl_JavaSetStationNo.setText(wStationNo);
			//#CM41394
			// store station no.
			this.getViewState().setString(DISP_STATIONNO_KEY, wStationNo);

			//#CM41395
			// Refresh, Setting, Clear buttons are enabled
			btn_Reflesh.setEnabled(true);
			btn_Setting.setEnabled(true);
			btn_Clear.setEnabled(true);
		}
		//#CM41396
		// station name
		if (!StringUtil.isBlank(wStationName))
		{
			lbl_JavaSetStationName.setText(wStationName);
		}
		//#CM41397
		// machine status name
		if (!StringUtil.isBlank(wStatusName))
		{
			lbl_JavaSetMachineStatus.setText(wStatusName);
		}
		//#CM41398
		// work status name
		if (!StringUtil.isBlank(wSuspendName))
		{
			lbl_JavaSetWorkStatus.setText(wSuspendName);
		}
		//#CM41399
		// work qty
		if (!StringUtil.isBlank(wWorkingCount))
		{
			lbl_JavaSetWorkCnt.setText(Formatter.getNumFormat(Integer.parseInt(wWorkingCount)));
		}
		//#CM41400
		// operation mode name
		if (!StringUtil.isBlank(wModeTypeName))
		{
			lbl_JavaSetWorkOperation.setText(wModeTypeName);
		}
		//#CM41401
		// work mode name
		if (!StringUtil.isBlank(wCurrentModeName))
		{
			lbl_JavaSetWorkMode.setText(wCurrentModeName);
		}
		//#CM41402
		// work mode name
		if (!StringUtil.isBlank(wModeRequestName))
		{
			lbl_JavaSetWorkModeReq.setText(wModeRequestName);
		}
		//#CM41403
		// operation mode
		if (!StringUtil.isBlank(wModeType))
		{
			rdo_WorkOperation_Manual.setEnabled(true);
			rdo_WorkOperation_Auto.setEnabled(true);
			if (Integer.parseInt(wModeType) == Station.AWC_MODE_CHANGE)
			{
				rdo_WorkOperation_Manual.setChecked(true);
				rdo_WorkOperation_Auto.setChecked(false);
				rdo_WorkMode_Storage.setChecked(true);
				rdo_WorkMode_Retrieval.setChecked(true);

				rdo_WorkMode_Storage.setEnabled(true);
				rdo_WorkMode_Retrieval.setEnabled(true);
			}
			else if (Integer.parseInt(wModeType) == Station.AUTOMATIC_MODE_CHANGE)
			{
				rdo_WorkOperation_Manual.setChecked(false);
				rdo_WorkOperation_Auto.setChecked(true);
				rdo_WorkMode_Storage.setChecked(false);
				rdo_WorkMode_Retrieval.setChecked(false);
				
				rdo_WorkMode_Storage.setEnabled(false);
				rdo_WorkMode_Retrieval.setEnabled(false);
			}
			else
			{
				rdo_WorkOperation_Manual.setChecked(true);
				rdo_WorkOperation_Auto.setChecked(false);

				rdo_WorkMode_Storage.setEnabled(true);
				rdo_WorkMode_Retrieval.setEnabled(true);
			}
		}
		//#CM41404
		// work mode name
		if (!StringUtil.isBlank(wCurrentMode))
		{
			if (Integer.parseInt(wCurrentMode) == Station.RETRIEVAL_MODE)
			{
				rdo_WorkMode_Storage.setChecked(false);
				rdo_WorkMode_Retrieval.setChecked(true);
			}
			else
			{
				rdo_WorkMode_Storage.setChecked(true);
				rdo_WorkMode_Retrieval.setChecked(false);
			}
		}
	}
	//#CM41405
	// Package methods -----------------------------------------------

	//#CM41406
	// Protected methods ---------------------------------------------

	//#CM41407
	// Private methods -----------------------------------------------
	//#CM41408
	/** 
	 * Set data to preset area.
	 * @param conn Connection object with data base.
	 * @throws Exception It reports on all exceptions.
	  */
	private void setList(Connection conn) throws Exception
	{

		//#CM41409
		// call display area clear process
		Disp_Clear();
		
		//#CM41410
		// set schedule parameter
		AsScheduleParameter param = new AsScheduleParameter();
		//#CM41411
		// set station no.
		param.setStationNo(this.getViewState().getString(DISP_STATIONNO_KEY));
		WmsScheduler schedule = new StationModeSCH();

		AsScheduleParameter[] viewParam = (AsScheduleParameter[]) schedule.query(conn, param);

		if (viewParam == null || viewParam.length == 0)
		{
			//#CM41412
			// set message
			message.setMsgResourceKey(schedule.getMessage());

			return;
		}

		//#CM41413
		// station no.
		String wStationNo = viewParam[0].getStationNo();
		//#CM41414
		// station name
		String wStationName = viewParam[0].getDispStationName();
		//#CM41415
		// operation mode
		String wModeType = viewParam[0].getModeType();
		//#CM41416
		// operation mode name
		String wModeTypeName = viewParam[0].getDispModeTypeName();
		//#CM41417
		// work mode name
		String wCurrentMode = viewParam[0].getCurrentMode();
		//#CM41418
		// work mode name
		String wCurrentModeName = viewParam[0].getDispCurrentModeName();
		//#CM41419
		// machine status name
		String wStatusName = viewParam[0].getDispControllerStatusName();
		//#CM41420
		// work status name
		String wSuspendName = viewParam[0].getDispWorkingStatusName();
		//#CM41421
		// work qty
		int wWorkingCount = viewParam[0].getWorkingCount();
		//#CM41422
		// work mode change request name
		String wModeRequestName = viewParam[0].getDispWorkingModeRequestName();

		//#CM41423
		// set the value if not empty
		//#CM41424
		// station no.
		lbl_JavaSetStationNo.setText(wStationNo);
		//#CM41425
		// station name
		lbl_JavaSetStationName.setText(wStationName);
		//#CM41426
		// machine status name
		lbl_JavaSetMachineStatus.setText(wStatusName);
		//#CM41427
		// work status name
		lbl_JavaSetWorkStatus.setText(wSuspendName);
		//#CM41428
		// work qty
		lbl_JavaSetWorkCnt.setText(Formatter.getNumFormat(wWorkingCount));
		//#CM41429
		// operation mode name
		lbl_JavaSetWorkOperation.setText(wModeTypeName);
		//#CM41430
		// work mode name
		lbl_JavaSetWorkMode.setText(wCurrentModeName);
		//#CM41431
		// work mode name
		lbl_JavaSetWorkModeReq.setText(wModeRequestName);
		//#CM41432
		// operation mode
		rdo_WorkOperation_Manual.setEnabled(true);
		rdo_WorkOperation_Auto.setEnabled(true);
		if (Integer.parseInt(wModeType) == Station.AWC_MODE_CHANGE)
		{
			rdo_WorkOperation_Manual.setChecked(true);
			rdo_WorkOperation_Auto.setChecked(false);
			rdo_WorkMode_Storage.setChecked(true);
			rdo_WorkMode_Retrieval.setChecked(true);

			rdo_WorkMode_Storage.setEnabled(true);
			rdo_WorkMode_Retrieval.setEnabled(true);
		}
		else if (Integer.parseInt(wModeType) == Station.AUTOMATIC_MODE_CHANGE)
		{
			rdo_WorkOperation_Manual.setChecked(false);
			rdo_WorkOperation_Auto.setChecked(true);
			rdo_WorkMode_Storage.setChecked(false);
			rdo_WorkMode_Retrieval.setChecked(false);

			rdo_WorkMode_Storage.setEnabled(false);
			rdo_WorkMode_Retrieval.setEnabled(false);
		}
		else
		{
			rdo_WorkOperation_Manual.setChecked(true);
			rdo_WorkOperation_Auto.setChecked(false);

			rdo_WorkMode_Storage.setEnabled(true);
			rdo_WorkMode_Retrieval.setEnabled(true);
		}
		//#CM41433
		// work mode name
		if (Integer.parseInt(wCurrentMode) == Station.RETRIEVAL_MODE)
		{
			rdo_WorkMode_Storage.setChecked(false);
			rdo_WorkMode_Retrieval.setChecked(true);
		}
		else
		{
			rdo_WorkMode_Storage.setChecked(true);
			rdo_WorkMode_Retrieval.setChecked(false);
		}

		//#CM41434
		// Refresh, Setting, Clear buttons are enabled
		btn_Reflesh.setEnabled(true);
		btn_Setting.setEnabled(true);
		btn_Clear.setEnabled(true);
		
		//#CM41435
		// set message
		message.setMsgResourceKey(schedule.getMessage());
	}

	//#CM41436
	// Event handler methods -----------------------------------------
	//#CM41437
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41438
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41439
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}

	//#CM41440
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41441
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

	//#CM41442
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Station_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41443
	/** 
	 * called when ST list button is clicked<BR>
	 * <BR>
	 * overview ::display station status list box<BR>
	 * <BR>
	 * [parameter]<BR>
	 * <DIR>
	 *   terminal no.<BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_Station_Click(ActionEvent e) throws Exception
	{
		//#CM41444
		// set search conditions in station status list
		ForwardParameters param = new ForwardParameters();
		
		//#CM41445
		// terminal no.
		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
		param.setParameter(ListStationStatusBusiness.TERMINALNO_KEY, userHandler.getTerminalNo());

		//#CM41446
		// station status list display
		redirect("/asrs/listbox/liststationstatus/ListStationStatus.do", param, "/progress.do");
	}

	//#CM41447
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Reflesh_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41448
	/** 
	 * call when the refresh button is pressed<BR>
	 * <BR>
	 * Abstract :refresh display area<BR>
	 * and set the cursor in worker code<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Reflesh_Click(ActionEvent e) throws Exception
	{
		//#CM41449
		// set the focus in worker code input
		setFocus(txt_WorkerCode);
		
		Connection conn = null;
		try
		{
			//#CM41450
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM41451
			//display data
			setList(conn);
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM41452
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM41453
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_2_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41454
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetStationNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41455
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_3_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41456
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetStationName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41457
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MachineStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41458
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetMachineStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41459
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41460
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetWorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41461
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkCnt_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41462
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetWorkCnt_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41463
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_OperationMode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41464
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetWorkOperation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41465
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkMode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41466
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetWorkMode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41467
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkModeReq_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41468
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetWorkModeReq_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41469
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_OperationModeChr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41470
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_WorkOperation_Manual_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41471
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_WorkOperation_Manual_Click(ActionEvent e) throws Exception
	{
	}

	//#CM41472
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_WorkOperation_Auto_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41473
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_WorkOperation_Auto_Click(ActionEvent e) throws Exception
	{
	}

	//#CM41474
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkModeChr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41475
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_WorkMode_Storage_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41476
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_WorkMode_Storage_Click(ActionEvent e) throws Exception
	{
	}

	//#CM41477
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_WorkMode_Retrieval_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41478
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_WorkMode_Retrieval_Click(ActionEvent e) throws Exception
	{
	}

	//#CM41479
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Setting_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41480
	/** 
	 * called on clicking "Set" button<BR>
	 * <BR>
	 * Abstract :set input area info to parameter and change station mode<BR>
	 * <BR>
	 * <DIR>
	 * 		1.set the cursor in worker code<BR>
	 * 		2.start scheduler<BR>
	 * 		<DIR>
	 * 			[parameter] *mandatory items<BR>
	 * 			<DIR>
	 * 				worker code *<BR>
	 * 				password *<BR>
	 * 				station no..*<BR>
	 * 				selected operation mode*<BR>
	 * 				selected work mode*<BR>
	 * 				terminal no.<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * 		3.refresh display area<BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Setting_Click(ActionEvent e) throws Exception
	{
		//#CM41481
		// set the focus in worker code input
		setFocus(txt_WorkerCode);
		
		Connection conn = null;
		try
		{
			//#CM41482
			// input check
			txt_WorkerCode.validate();
			txt_Password.validate();
			
			Vector vecParam = new Vector();
			
			AsScheduleParameter param = new AsScheduleParameter();
			
			//#CM41483
			// worker code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM41484
			// password
			param.setPassword(txt_Password.getText());
			
			//#CM41485
			// set station no.
			param.setStationNo(this.getViewState().getString(DISP_STATIONNO_KEY));
			
			//#CM41486
			// selected operation mode
			//#CM41487
			// operation mode name
			if (rdo_WorkOperation_Manual.getChecked())
			{
				param.setModeType(Integer.toString(Station.AWC_MODE_CHANGE));
			}
			else if (rdo_WorkOperation_Auto.getChecked())
			{
				param.setModeType(Integer.toString(Station.AUTOMATIC_MODE_CHANGE));
			}
			//#CM41488
			// work mode name
			if (rdo_WorkMode_Storage.getChecked())
			{
				param.setCurrentMode(Integer.toString(Station.STORAGE_MODE));
			}
			else if (rdo_WorkMode_Retrieval.getChecked())
			{
				param.setCurrentMode(Integer.toString(Station.RETRIEVAL_MODE));
			}

			//#CM41489
			// terminal no.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());

			vecParam.addElement(param);

			if (vecParam.size() <= 0)
			{
				//#CM41490
				// 6023154 = modification target data does not exist
				message.setMsgResourceKey("6023154");
				return;
			}

			AsScheduleParameter[] paramArray = new AsScheduleParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			WmsScheduler schedule = new StationModeSCH();
			//#CM41491
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM41492
			// start schedule
			AsScheduleParameter[] viewParam =
				(AsScheduleParameter[]) schedule.startSCHgetParams(conn, paramArray);

			if (viewParam == null)
			{
				conn.rollback();
				//#CM41493
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.commit();

				//#CM41494
				// set display area
				setList(conn);

				//#CM41495
				// set message
				message.setMsgResourceKey(schedule.getMessage());

			}
		}
		catch (Exception ex)
		{
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
				//#CM41496
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

	//#CM41497
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41498
	/**
	 * called when clear button is clicked<BR>
	 * <BR>
	 * Abstract :clear the input area<BR>
	 * <BR>
	 * <DIR>
	 * 		1.return display area, viewstate to initial state<BR>
	 * 		2.set the cursor in worker code<BR>
	 * 		<DIR>
	 *   	item name[initial value]<BR>
	 *   		<DIR>
	 * 				worker code[as it is]<BR>
	 * 				password[as it is]<BR>
	 * 				station no.[nil] <BR>
	 *  			station name[nil] <BR>
	 * 				machine status[nil] <BR>
	 * 				work status[nil] <BR>
	 * 				work qty[nil] <BR>
	 * 				operation mode[nil] <BR>
	 * 				work mode name[nil] <BR>
	 * 				work mode change request[nil] <BR>
	 * 				operation mode radio button[operation impossible] <BR>
	 * 				work mode radio button[operation impossible] <BR>
	 * 	 		</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM41499
		// set the focus in worker code input
		setFocus(txt_WorkerCode);
		
		//#CM41500
		// clear station no.
		this.getViewState().setString(DISP_STATIONNO_KEY, "");

		//#CM41501
		// call display area clear process
		Disp_Clear();
	}

	//#CM41502
	/**
	 * clear the display area<BR>
	 * <BR>
	 * Abstract :clear the items and enable, disable buttons<BR>
	 * <BR>
	 * @throws Exception report all the exceptions
	 */
	public void Disp_Clear() throws Exception
	{
		//#CM41503
		// refresh, setting, clear button can't be used
		btn_Reflesh.setEnabled(false);
		btn_Setting.setEnabled(false);
		btn_Clear.setEnabled(false);		
		//#CM41504
		// station no.
		lbl_JavaSetStationNo.setText("");
		//#CM41505
		// station name
		lbl_JavaSetStationName.setText("");
		//#CM41506
		// machine status name
		lbl_JavaSetMachineStatus.setText("");
		//#CM41507
		// work status name
		lbl_JavaSetWorkStatus.setText("");
		//#CM41508
		// work qty
		lbl_JavaSetWorkCnt.setText("");
		//#CM41509
		// operation mode name
		lbl_JavaSetWorkOperation.setText("");
		//#CM41510
		// work mode name
		lbl_JavaSetWorkMode.setText("");
		//#CM41511
		// work mode name
		lbl_JavaSetWorkModeReq.setText("");
		//#CM41512
		// operation mode
		rdo_WorkOperation_Manual.setChecked(false);
		rdo_WorkOperation_Manual.setEnabled(false);
		rdo_WorkOperation_Auto.setChecked(false);
		rdo_WorkOperation_Auto.setEnabled(false);
		//#CM41513
		// work mode name
		rdo_WorkMode_Storage.setEnabled(false);
		rdo_WorkMode_Retrieval.setEnabled(false);
		rdo_WorkMode_Storage.setChecked(false);
		rdo_WorkMode_Retrieval.setChecked(false);
	}
	//#CM41514
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Set_Click(ActionEvent e) throws Exception
	{
	}


	//#CM41515
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Worker_Code_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41516
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41517
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM41518
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM41519
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM41520
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41521
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41522
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM41523
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM41524
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}


}
//#CM41525
//end of class
