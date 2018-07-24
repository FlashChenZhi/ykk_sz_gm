// $Id: ReportDataPlanBusiness.java,v 1.2 2006/11/13 08:21:51 suresh Exp $

//#CM694412
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.reportdataplan;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.system.schedule.ReportDataPlanSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM694413
/**
 * Designer : T.Hondo <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * Allow this class to provide a screen for reporting data (for reporting the Planned Work).<BR>
 * Set the contents entered via screen for the parameter.<BR>
 * Allow the schedule to report the result data based on the parameter.<BR>
 * Receive the result from the schedule. Receive true if the process completed normally.<BR>
 * Or, receive false if the schedule failed to complete due to condition error etc.<BR>
 * As the result of the schedule, output the message obtained from the schedule to the screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process by clicking "Start" button(<CODE>btn_Start_Click()</CODE>method)<BR>
 * <BR>
 * <DIR>
 * 	Report the data for the items ticked in each field via screen.<BR>
 * 	Return an error message if condition error etc. occurs.<BR>
 * <BR>
 * 	[Parameter] *Mandatory Input<BR>
 * 	<BR>
 * 	<DIR>
 * 		Worker Code* <BR>
 *		Password* <BR>
 *		Work date* <BR>
 *		Selected <BR>
 *		Report the data during work in process. <BR>
 *		Report the data Not Worked. <BR>
 * 	</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/09</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:51 $
 * @author  $Author: suresh $
 */
public class ReportDataPlanBusiness extends ReportDataPlan implements WMSConstants
{
	//#CM694414
	// Class fields --------------------------------------------------

	//#CM694415
	// Class variables -----------------------------------------------

	//#CM694416
	// Class method --------------------------------------------------

	//#CM694417
	// Constructors --------------------------------------------------

	//#CM694418
	// Public methods ------------------------------------------------

	//#CM694419
	/**
	 * Invoke this before invoking each control event.<BR>
	 * <BR>
	 * <DIR>
	 *  Summary: Displays a dialog.<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM694420
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM694421
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM694422
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM694423
			//Set the path to the help file.
			//#CM694424
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM694425
		// MSG-0019= Do you start it?
		btn_Start.setBeforeConfirm("MSG-W0019");
	}

	//#CM694426
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Field item [Initial Value]<BR>
	 * <DIR>
	 *	Worker Code			[None] <BR>
	 *	Password				[None] <BR>
	 *	Work date					[Work Date] <BR>
	 *	Selected					[Not Ticked] <BR>
	 *	Report the data during work in process.	[Not Ticked] <BR>
	 *	Report the data Not Worked.	[Not Ticked] <BR>
	 * </DIR>
	 *	1.Set the cursor for Worker code. <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM694427
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);
		
		//#CM694428
		// Set the initial value of the Worker Code.
		txt_WorkerCode.setText("");
		//#CM694429
		// Set the initial value of the Password.
		txt_Password.setText("");
		
		setFirstDisp();
	}

	//#CM694430
	// Package methods -----------------------------------------------

	//#CM694431
	// Protected methods ---------------------------------------------

	//#CM694432
	// Private methods -----------------------------------------------

	//#CM694433
	/**
	 * Showing the initial display invokes this method.
	 * @throws Exception Report all exceptions. 
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM694434
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			SystemParameter initParam = new SystemParameter();
			WmsScheduler schedule = new ReportDataPlanSCH();
			SystemParameter param = (SystemParameter) schedule.initFind(conn, initParam);

			//#CM694435
			// Set the initial value of the Work Date.
			if (param.getPlanDate() != null)
			{
				txt_WorkDate1.setDate(WmsFormatter.toDate(param.getPlanDate()));
			}

			//#CM694436
			// Disable all field items that does not contain package.
			//#CM694437
			// Receiving Result
			if (!param.getSelectReportInstockData())
			{
				chk_ComUseSelectInstk.setEnabled(false);
				chk_ComUseWorkProgInstk.setEnabled(false);
				chk_ComUseRupwiInstk.setEnabled(false);
			}
			else
			{
				chk_ComUseSelectInstk.setEnabled(true);
				chk_ComUseWorkProgInstk.setEnabled(true);
				chk_ComUseRupwiInstk.setEnabled(true);
			}
			//#CM694438
			// Storage Result
			if (!param.getSelectReportStorageData())
			{
				chk_ComUseSelectStrg.setEnabled(false);
				chk_ComUseWorkProgStrg.setEnabled(false);
				chk_ComUseRupwiStrg.setEnabled(false);
			}
			else
			{
				chk_ComUseSelectStrg.setEnabled(true);
				chk_ComUseWorkProgStrg.setEnabled(true);
				chk_ComUseRupwiStrg.setEnabled(true);
			}
			//#CM694439
			// Picking Result
			if (!param.getSelectReportRetrievalData())
			{
				chk_ComUseSelectRtrivl.setEnabled(false);
				chk_ComUseWorkProgRtrivl.setEnabled(false);
				chk_ComUseRupwiRtrivl.setEnabled(false);
			}
			else
			{
				chk_ComUseSelectRtrivl.setEnabled(true);
				chk_ComUseWorkProgRtrivl.setEnabled(true);
				chk_ComUseRupwiRtrivl.setEnabled(true);
			}
			//#CM694440
			// Sorting Result
			if (!param.getSelectReportSortingData())
			{
				chk_ComUseSelectPick.setEnabled(false);
				chk_ComUseWorkProgPick.setEnabled(false);
				chk_ComUseRupwiPick.setEnabled(false);
			}
			else
			{
				chk_ComUseSelectPick.setEnabled(true);
				chk_ComUseWorkProgPick.setEnabled(true);
				chk_ComUseRupwiPick.setEnabled(true);
			}
			//#CM694441
			// Shipping Result
			if (!param.getSelectReportShippingData())
			{
				chk_ComUseSelectShp.setEnabled(false);
				chk_ComUseWorkProgShp.setEnabled(false);
				chk_ComUseRupwiShp.setEnabled(false);
			}
			else
			{
				chk_ComUseSelectShp.setEnabled(true);
				chk_ComUseWorkProgShp.setEnabled(true);
				chk_ComUseRupwiShp.setEnabled(true);
			}

			//#CM694442
			// Selected
			chk_ComUseSelectInstk.setChecked(false);
			chk_ComUseSelectStrg.setChecked(false);
			chk_ComUseSelectRtrivl.setChecked(false);
			chk_ComUseSelectPick.setChecked(false);
			chk_ComUseSelectShp.setChecked(false);

			//#CM694443
			// Report the data during work in process.
			chk_ComUseWorkProgInstk.setChecked(false);
			chk_ComUseWorkProgStrg.setChecked(false);
			chk_ComUseWorkProgRtrivl.setChecked(false);
			chk_ComUseWorkProgPick.setChecked(false);
			chk_ComUseWorkProgShp.setChecked(false);

			//#CM694444
			// Report the data Not Worked.
			chk_ComUseRupwiInstk.setChecked(false);
			chk_ComUseRupwiStrg.setChecked(false);
			chk_ComUseRupwiRtrivl.setChecked(false);
			chk_ComUseRupwiPick.setChecked(false);
			chk_ComUseRupwiShp.setChecked(false);
			
			lbl_MessageInstk.setText(" ");
			lbl_MessageStrg.setText(" ");
			lbl_MessageRtrivl.setText(" ");
			lbl_MessagePick.setText(" ");
			lbl_MessageShp.setText(" ");

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM694445
				// Close the connection.
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
	
	//#CM694446
	/**
	 * Invoke this method, if error occurs when clicking Start button.
	 * @param param      Field item to be entered via screen.
	 * @throws Exception Report all exceptions. 
	 */
	private void setErrorDisp(SystemParameter param) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM694447
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			SystemParameter initParam = new SystemParameter();
			WmsScheduler schedule = new ReportDataPlanSCH();
			SystemParameter initparam = (SystemParameter) schedule.initFind(conn, initParam);

			//#CM694448
			// Return to the status just before clicking on Start button.
			//#CM694449
			// Receiving Result
			if (!initparam.getSelectReportInstockData())
			{
				chk_ComUseSelectInstk.setEnabled(false);
			}
			else
			{
				if (param.getSelectReportInstockData())
				{
					chk_ComUseSelectInstk.setChecked(true);
					chk_ComUseWorkProgInstk.setEnabled(true);
					if (param.getSelectReportInstockData_InProgress())
					{
						chk_ComUseWorkProgInstk.setChecked(true);
						chk_ComUseRupwiInstk.setEnabled(true);
						chk_ComUseRupwiInstk.setChecked(param.getSelectReportInstockData_Unworking());
					}
				}
			}
			//#CM694450
			// Storage Result
			if (!initparam.getSelectReportStorageData())
			{
				chk_ComUseSelectStrg.setEnabled(false);
			}
			else
			{
				if (param.getSelectReportStorageData())
				{
					chk_ComUseSelectStrg.setChecked(true);
					chk_ComUseRupwiStrg.setEnabled(true);
					chk_ComUseRupwiStrg.setChecked(param.getSelectReportStorageData_Unworking());
				}
			}
			//#CM694451
			// Picking Result
			if (!initparam.getSelectReportRetrievalData())
			{
				chk_ComUseSelectRtrivl.setEnabled(false);
			}
			else
			{
				if (param.getSelectReportRetrievalData())
				{
					chk_ComUseSelectRtrivl.setChecked(true);
					chk_ComUseRupwiRtrivl.setEnabled(true);
					chk_ComUseRupwiRtrivl.setChecked(param.getSelectReportRetrievalData_Unworking());
				}
			}
			//#CM694452
			// Sorting Result
			if (!initparam.getSelectReportSortingData())
			{
				chk_ComUseSelectPick.setEnabled(false);
			}
			else
			{
				if (param.getSelectReportSortingData())
				{
					chk_ComUseSelectPick.setChecked(true);
					chk_ComUseRupwiPick.setEnabled(true);
					chk_ComUseRupwiPick.setChecked(param.getSelectReportSortingData_Unworking());
				}
			}
			//#CM694453
			// Shipping Result
			if (!initparam.getSelectReportShippingData())
			{
				chk_ComUseSelectShp.setEnabled(false);
			}
			else
			{
				if (param.getSelectReportShippingData())
				{
					chk_ComUseSelectShp.setChecked(true);
					chk_ComUseWorkProgShp.setEnabled(true);
					if (param.getSelectReportShippingData_InProgress())
					{
						chk_ComUseWorkProgShp.setChecked(true);
						chk_ComUseRupwiShp.setEnabled(true);
						chk_ComUseRupwiShp.setChecked(param.getSelectReportShippingData_Unworking());
					}
				}
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
				//#CM694454
				// Close the connection.
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
	
	//#CM694455
	/**
	 * Check whether checkboxes for each field item is properly ticked off.
	 * @param select      Checkbox for selecting
	 * @param workProg    Checkbox for reporting data with work Processing
	 * @param rupwi       Checkbox for reporting data Not Worked.
	 * @throws Exception  Report all exceptions. 
	 */
	private boolean selectCheck(boolean select, boolean workProg, boolean rupwi) throws Exception
	{
		if (workProg || rupwi)
		{
			if (!select)
			{
				return false;
			}
		}
		
		return true;
	}
	
	//#CM694456
	// Event handler methods -----------------------------------------
	//#CM694457
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694458
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694459
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694460
	/** 
	 * Clicking on Menu button invokes this.<BR>  
	 * Summary: Shifts to the Menu screen.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM694461
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694462
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694463
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694464
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694465
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694466
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694467
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694468
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694469
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694470
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694471
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkDate1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694472
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkDate1_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694473
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkDate1_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694474
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Select_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694475
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ReportDetals_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694476
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkProgress_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694477
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Rupwi_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694478
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InstockResult_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694479
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseRupwiInstk_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694480
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseRupwiInstk_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694481
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageResult_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694482
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_HyphnStrg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694483
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseRupwiStrg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694484
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseRupwiStrg_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694485
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalResult_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694486
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_HyphnRtrivl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694487
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseRupwiRtrivl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694488
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseRupwiRtrivl_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694489
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PickingResult_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694490
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_HyphnPick_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694491
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseRupwiPick_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694492
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseRupwiPick_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694493
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ShippingResult_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694494
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseRupwiShp_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694495
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseRupwiShp_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694496
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Start_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694497
	/** 
	 * Clicking on Start button invokes this.
	 * <BR>
	 * Summary: Displays a dialog box to allow to confirm to report the data or not. <BR>
	 * <BR>
	 * <DIR>
	 * 	1.Display a dialog box to allow to confirm to start: "Do you start it?" <BR>
	 * 	<DIR>
	 * 		1.Check the input area. <BR>
	 *		- If no check is placed in each checkbox, display a message: "Select the start target". <BR>
	 *		- If one or more field items are ticked off: Display a dialog box to allow to confirm to start ("Do you start?") <BR>
	 *		2.Set the cursor for Worker code. <BR>
	 * <BR>
	 * 		[Dialog for confirming: Cancel]<BR>
	 * 		<DIR>
	 * 			Disable to do anything.<BR>
	 * 		</DIR>
	 * 		[Dialog for confirming: OK]<BR>
	 * 		<DIR>
	 * 		Summary: Reports data using the info of the Input area.<BR>
	 * 			1.Check the field items entered in the Input area (Mandatory Input, number of characters, and attribution of characters)<BR>
	 * 			2.Start the schedule.<BR>
	 * 				<DIR>
	 * 					- Report the data for the ticked items. <BR>
	 *					- If report error occurs: Display the error message. <BR>
	 *					- If reporting completed: Display a message announcing that reporting completed. <BR>
	 * 				</DIR>
	 * 			3.Maintain the contents of the input area.<BR>
	 * 		</DIR>
	 * 	</DIR>	
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Start_Click(ActionEvent e) throws Exception
	{
		//#CM694498
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);

		//#CM694499
		// Check for input.
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_WorkDate1.validate();
		
		//#CM694500
		// Initialize the message.
		lbl_MessageInstk.setText(" ");
		lbl_MessageStrg.setText(" ");
		lbl_MessageRtrivl.setText(" ");
		lbl_MessagePick.setText(" ");
		lbl_MessageShp.setText(" ");

		Connection conn = null;
		ReportDataPlanSCH schedule = new ReportDataPlanSCH();
		try
		{			
			SystemParameter[] param = new SystemParameter[1];
			param[0] = new SystemParameter();
			
			//#CM694501
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM694502
			// Set the value for the parameter.
			boolean returnFlag = false; 
			
			//#CM694503
			// Worker Code
			param[0].setWorkerCode(txt_WorkerCode.getText());
			//#CM694504
			// Password
			param[0].setPassword(txt_Password.getText());
			//#CM694505
			// Planned Work Date
			param[0].setWorkDate(WmsFormatter.toParamDate(txt_WorkDate1.getDate()));

			//#CM694506
			// Receiving Result: Selected
			param[0].setSelectReportInstockData(chk_ComUseSelectInstk.getChecked());
			//#CM694507
			// Report the Receiving result during work in process.
			param[0].setSelectReportInstockData_InProgress(chk_ComUseWorkProgInstk.getChecked());
			//#CM694508
			// Report the Receiving Result Not Worked.
			param[0].setSelectReportInstockData_Unworking(chk_ComUseRupwiInstk.getChecked());
			//#CM694509
			// Check whether the field Select is ticked off or not.
			if (!selectCheck(chk_ComUseSelectInstk.getChecked(), chk_ComUseWorkProgInstk.getChecked(), chk_ComUseRupwiInstk.getChecked()))
			{
				//#CM694510
				// 6023355=Please tick option to report.
				message.setMsgResourceKey("6023355");
				lbl_MessageInstk.setText(MessageResource.getMessage("6023355"));
				returnFlag = true;
			}
			
			//#CM694511
			// Storage Result: Selected
			param[0].setSelectReportStorageData(chk_ComUseSelectStrg.getChecked());
			//#CM694512
			// Report the work in progress of Storage result.
			param[0].setSelectReportStorageData_InProgress(chk_ComUseWorkProgStrg.getChecked());
			//#CM694513
			// Execute the process for reporting Storage Result Not Worked.
			param[0].setSelectReportStorageData_Unworking(chk_ComUseRupwiStrg.getChecked());
			//#CM694514
			// Check whether the field Select is ticked off or not.
			if (!selectCheck(chk_ComUseSelectStrg.getChecked(), chk_ComUseWorkProgStrg.getChecked(), chk_ComUseRupwiStrg.getChecked()))
			{
				//#CM694515
				// 6023355=Please tick option to report.
				message.setMsgResourceKey("6023355");
				lbl_MessageStrg.setText(MessageResource.getMessage("6023355"));
				returnFlag = true;
			}
			
			//#CM694516
			// Picking Result: Selected
			param[0].setSelectReportRetrievalData(chk_ComUseSelectRtrivl.getChecked());
			//#CM694517
			// Report the Picking result during work in process.
			param[0].setSelectReportRetrievalData_InProgress(chk_ComUseWorkProgRtrivl.getChecked());
			//#CM694518
			// Report the Picking Result Not Worked.
			param[0].setSelectReportRetrievalData_Unworking(chk_ComUseRupwiRtrivl.getChecked());
			//#CM694519
			// Check whether the field Select is ticked off or not.
			if (!selectCheck(chk_ComUseSelectRtrivl.getChecked(), chk_ComUseWorkProgRtrivl.getChecked(), chk_ComUseRupwiRtrivl.getChecked()))
			{
				//#CM694520
				// 6023355=Please tick option to report.
				message.setMsgResourceKey("6023355");
				lbl_MessageRtrivl.setText(MessageResource.getMessage("6023355"));
				returnFlag = true;
			}
			
			//#CM694521
			// Sorting Result: Selected
			param[0].setSelectReportSortingData(chk_ComUseSelectPick.getChecked());
			//#CM694522
			// Report the Sorting result during work in process.
			param[0].setSelectReportSortingData_InProgress(chk_ComUseWorkProgPick.getChecked());
			//#CM694523
			// Report the Sorting Result Not Worked.
			param[0].setSelectReportSortingData_Unworking(chk_ComUseRupwiPick.getChecked());
			//#CM694524
			// Check whether the field Select is ticked off or not.
			if (!selectCheck(chk_ComUseSelectPick.getChecked(), chk_ComUseWorkProgPick.getChecked(), chk_ComUseRupwiPick.getChecked()))
			{
				//#CM694525
				// 6023355=Please tick option to report.
				message.setMsgResourceKey("6023355");
				lbl_MessagePick.setText(MessageResource.getMessage("6023355"));
				returnFlag = true;
			}
			
			//#CM694526
			// Shipping Result: Selected
			param[0].setSelectReportShippingData(chk_ComUseSelectShp.getChecked());
			//#CM694527
			// Report the Shipping result during work in process.
			param[0].setSelectReportShippingData_InProgress(chk_ComUseWorkProgShp.getChecked());
			//#CM694528
			// Report the Shipping Result Not Worked.
			param[0].setSelectReportShippingData_Unworking(chk_ComUseRupwiShp.getChecked());
			//#CM694529
			// Check whether the field Select is ticked off or not.
			if (!selectCheck(chk_ComUseSelectShp.getChecked(), chk_ComUseWorkProgShp.getChecked(), chk_ComUseRupwiShp.getChecked()))
			{
				//#CM694530
				// 6023355=Please tick option to report.
				message.setMsgResourceKey("6023355");
				lbl_MessageShp.setText(MessageResource.getMessage("6023355"));
				returnFlag = true;
			}
			
			if (returnFlag)
			{
				return;
			}

			//#CM694531
			// Start the schedule.
			if (schedule.startSCH(conn, param))
			{
				conn.commit();
				//#CM694532
				// Selected
				chk_ComUseSelectInstk.setChecked(false);
				chk_ComUseSelectStrg.setChecked(false);
				chk_ComUseSelectRtrivl.setChecked(false);
				chk_ComUseSelectPick.setChecked(false);
				chk_ComUseSelectShp.setChecked(false);

				//#CM694533
				// Report the data during work in process.
				chk_ComUseWorkProgInstk.setChecked(false);
				chk_ComUseWorkProgStrg.setChecked(false);
				chk_ComUseWorkProgRtrivl.setChecked(false);
				chk_ComUseWorkProgPick.setChecked(false);
				chk_ComUseWorkProgShp.setChecked(false);

				//#CM694534
				// Report the data Not Worked.
				chk_ComUseRupwiInstk.setChecked(false);
				chk_ComUseRupwiStrg.setChecked(false);
				chk_ComUseRupwiRtrivl.setChecked(false);
				chk_ComUseRupwiPick.setChecked(false);
				chk_ComUseRupwiShp.setChecked(false);
			}
			else
			{
				conn.rollback();
			}

			if (schedule.getMessage() != null)
			{
				//#CM694535
				// Display a message.
				message.setMsgResourceKey(schedule.getMessage());
			}
			if (schedule.getMessageList() != null)
			{
				//#CM694536
				// Display a message.
				String[] messageList = (String[])schedule.getMessageList();
				lbl_MessageInstk.setText(messageList[0]);
				lbl_MessageStrg.setText(messageList[1]);
				lbl_MessageRtrivl.setText(messageList[2]);
				lbl_MessagePick.setText(messageList[3]);
				lbl_MessageShp.setText(messageList[4]);
			}
		}
		catch (Exception ex)
		{
   			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
			if (schedule.getMessageList() != null)
			{
				//#CM694537
				// Display a message.
				String[] messageList = (String[])schedule.getMessageList();
				lbl_MessageInstk.setText(messageList[0]);
				lbl_MessageStrg.setText(messageList[1]);
				lbl_MessageRtrivl.setText(messageList[2]);
				lbl_MessagePick.setText(messageList[3]);
				lbl_MessageShp.setText(messageList[4]);
			}
		}
		finally
		{
			try
			{
				//#CM694538
				// Close the connection.
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
	}

	//#CM694539
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694540
	/** 
	 * Clicking on Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Return the item (count) in the input area to the initial value.<BR>
	 * 		<DIR>
	 * 			- For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method.
	 * 		</DIR>
	 * 		2.Set the cursor for Worker code. <BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM694541
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);

		setFirstDisp();
	}

	//#CM694542
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694543
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseSelectInstk_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694544
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseSelectInstk_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694545
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseWorkProgInstk_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694546
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseWorkProgInstk_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694547
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseSelectStrg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694548
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseSelectStrg_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694549
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseSelectRtrivl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694550
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseSelectRtrivl_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694551
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseSelectPick_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694552
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseSelectPick_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694553
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseSelectShp_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694554
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseSelectShp_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694555
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseWorkProgShp_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694556
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseWorkProgShp_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694557
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkDate_Server(ActionEvent e) throws Exception
	{
	}
	//#CM694558
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseWorkProgStrg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694559
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseWorkProgStrg_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694560
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseWorkProgRtrivl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694561
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseWorkProgRtrivl_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694562
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseWorkProgPick_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694563
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ComUseWorkProgPick_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694564
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_DataReport_Click(ActionEvent e) throws Exception
	{
	}

	//#CM694565
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Message_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694566
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MessageInstk_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694567
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MessageStrg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694568
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MessageRtrivl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694569
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MessagePick_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694570
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MessageShp_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694571
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_7_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694572
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_9_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694573
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_9_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694574
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_9_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694575
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_9_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694576
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_11_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694577
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_12_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694578
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_12_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694579
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_12_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694580
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_12_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694581
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_8_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694582
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_13_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694583
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_13_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694584
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_13_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694585
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkDate1_Server(ActionEvent e) throws Exception
	{
	}

}
//#CM694586
//end of class
