// $Id: LoadDataBusiness.java,v 1.2 2006/11/13 08:21:09 suresh Exp $

//#CM693467
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.loaddata;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.system.schedule.LoadDataSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
//#CM693468
/**
 * Designer : K.Mukai <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * Allow this class to provide a screen for loading data.<BR>
 * Set the contents entered via screen for the parameter. Allow the schedule to load the work plan data based on the parameter.<BR>
 * Receive the result from the schedule. Receive true if the process completed normally. <BR>
 * Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 * As the result of the schedule, output the message obtained from the schedule to the screen. <BR>
 * <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * Process by clicking "Start" button(<CODE>btn_Start_Click()</CODE>method)<BR>
 * <BR>
 * <DIR>
 *   Load a data using the info in Input area. <BR>
 * <BR>
 *   [Parameter] *Mandatory Input<BR>
 * <BR>
 * <DIR>
 *   Worker Code* <BR>
 *   Password* <BR>
 *   Planned date* <BR>
 * </DIR>
 * <BR>
 *   [Parameter]+ Require to input one or more parameter(s) <BR>
 * <BR>
 * <DIR>
 *   Each introduced package Ticked off or Not ticked off +<BR>
 * </DIR>
 * <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/06</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:09 $
 * @author  $Author: suresh $
 */
public class LoadDataBusiness extends LoadData implements WMSConstants
{
	//#CM693469
	// Class fields --------------------------------------------------

	//#CM693470
	// Class variables -----------------------------------------------

	//#CM693471
	// Class method --------------------------------------------------

	//#CM693472
	// Constructors --------------------------------------------------

	//#CM693473
	// Public methods ------------------------------------------------

	//#CM693474
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
			//#CM693475
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM693476
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM693477
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM693478
			//Set the path to the help file.
			//#CM693479
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM693480
		// MSG-0019= Do you start it?
		btn_Start.setBeforeConfirm("MSG-W0019");
	}

	//#CM693481
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Set the cursor for Worker code. <BR>
	 * <DIR>
	 *   Field item [Initial Value] <BR>
	 *   <DIR>
	 *     Worker Code [None] <BR>
	 *     Password [None] <BR>
	 *     Planned Date [Search Value] <BR>
	 *     Receiving plan data[Not Ticked] <BR>
	 *     Storage plan data[Not Ticked] <BR>
	 *     Decide the Storage Location automatically.[Not Ticked]<BR>
	 *     Picking plan data[Not Ticked] <BR>
	 *     Sorting Plan data[Not Ticked] <BR>
	 *     Shipping plan data[Not Ticked] <BR>
	 *   </DIR>
	 *   <BR>
	 *   Display only items of checkbox which contain package.<BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM693482
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);

		Connection conn = null;
		try
		{
			//#CM693483
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			SystemParameter initParam = new SystemParameter();
			WmsScheduler schedule = new LoadDataSCH();
			SystemParameter param = (SystemParameter) schedule.initFind(conn, initParam);

			//#CM693484
			// Set the initial value of the Worker Code.
			txt_WorkerCode.setText("");
			//#CM693485
			// Set the initial value of the Password.
			txt_Password.setText("");

			//#CM693486
			// Set the initial value of the Planned Date.
			if (param != null)
			{
				txt_PlanDate.setDate(WmsFormatter.toDate(param.getPlanDate()));
			}

			//#CM693487
			// Enable to read-only for the field items that does not contain package.
			//#CM693488
			// Receiving plan data
			if (!param.getSelectLoadInstockData())
			{
				chk_InstockPlanData.setEnabled(false);
			}
			//#CM693489
			// Storage plan data
			if (!param.getSelectLoadStorageData())
			{
				chk_StoragePlanData.setEnabled(false);
				//#CM693490
				// Depend on the Storage package for automated decision-making of Storage Location.
				chk_IdmAuto.setEnabled(false);
			}
			//#CM693491
			// Picking plan data
			if (!param.getSelectLoadRetrievalData())
			{
				chk_RetrievalPlanData.setEnabled(false);
			}
			//#CM693492
			// Sorting Plan data
			if (!param.getSelectLoadSortingData())
			{
				chk_PickingPlanData.setEnabled(false);
			}
			//#CM693493
			// Shipping plan data
			if (!param.getSelectLoadShippingData())
			{
				chk_ShippingPlanData.setEnabled(false);
			}
			//#CM693494
			// Automated decision-making of Storage Location
			if (!param.getSelectLoadIdmAuto())
			{
				chk_IdmAuto.setEnabled(false);
			}

			//#CM693495
			// Default of the checkbox.
			chk_InstockPlanData.setChecked(false);
			chk_StoragePlanData.setChecked(false);
			chk_RetrievalPlanData.setChecked(false);
			chk_PickingPlanData.setChecked(false);
			chk_ShippingPlanData.setChecked(false);
			chk_IdmAuto.setChecked(false);
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM693496
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

	//#CM693497
	// Package methods -----------------------------------------------

	//#CM693498
	// Protected methods ---------------------------------------------

	//#CM693499
	// Private methods -----------------------------------------------
	//#CM693500
	/**
	 * Check whether checkboxes for each field item is properly ticked off.
	 * @param storageCheck Presence of ticking off of Storage plan data
	 * @param idmCheck     Presence of ticking off of Automated decision-making of Storage Location
	 * @return Return true if both items are ticked. Otherwise, return false.
	 * @throws Exception Report all exceptions. 
	 */
	private boolean selectCheck(boolean storageCheck, boolean idmCheck) throws Exception
	{
		if (!storageCheck && idmCheck)
		{
			return false;
		}
		
		return true;
	}
	//#CM693501
	// Event handler methods -----------------------------------------
	//#CM693502
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693503
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693504
	/** 
	 * Clicking on Menu button invokes this. <BR>
	 * <BR>
	 * Shift to the Menu screen.<BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM693505
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693506
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693507
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693508
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693509
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693510
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693511
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693512
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693513
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693514
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693515
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693516
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693517
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693518
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InstockPlanData_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693519
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InstockPlanData_Change(ActionEvent e) throws Exception
	{
	}

	//#CM693520
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_StoragePlanData_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693521
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_StoragePlanData_Change(ActionEvent e) throws Exception
	{
	}

	//#CM693522
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RetrievalPlanData_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693523
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_RetrievalPlanData_Change(ActionEvent e) throws Exception
	{
	}

	//#CM693524
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PickingPlanData_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693525
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_PickingPlanData_Change(ActionEvent e) throws Exception
	{
	}

	//#CM693526
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ShippingPlanData_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693527
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ShippingPlanData_Change(ActionEvent e) throws Exception
	{
	}

	//#CM693528
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Start_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693529
	/** 
	 * Clicking on Start button invokes this. <BR>
	 * <BR>
	 * Summary: Displays a dialog box to allow to confirm to load the data or not. <BR>
	 * <BR>
	 * Display a dialog. <BR>
	 * - Display a dialog box to allow to confirm to start: "Do you start it?" <BR>
	 * <DIR>
	 *   [Dialog for confirming: Cancel] <BR>
	 *   <DIR>
	 *     Disable to do anything. <BR>
	 *   </DIR>
	 *   [Dialog for confirming: OK] <BR>
	 *   <DIR>
	 *     Summary: Loads data using Input area info. <BR>
	 *     1.Check the input area. <BR>
	 *     <DIR>
	 *       If no check is placed in each checkbox, display a message: "Select the start point of the range". <BR>
	 *     </DIR>
	 *     2.Check for input in the input item (count) in the input area. (Mandatory Input, number of characters, and attribution of characters) <BR>
	 *     3.Start the schedule. <BR>
	 *     4.Set the cursor for Worker code. <BR>
	 *     5.Maintain the contents of the input area. <BR>
	 *   </DIR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Start_Click(ActionEvent e) throws Exception
	{
		//#CM693530
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);

		//#CM693531
		// Check for input.
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_PlanDate.validate();

		boolean wCheck_flag = false;

		//#CM693532
		// Display error if no check is placed in all checkboxes.
		//#CM693533
		// Check for input.Receiving plan data
		if (chk_InstockPlanData.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM693534
		// Check for input.Storage plan data
		if (chk_StoragePlanData.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM693535
		// Check for input.Picking plan data
		if (chk_RetrievalPlanData.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM693536
		// Check for input.Sorting Plan data
		if (chk_PickingPlanData.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM693537
		// Check for input.Shipping plan data
		if (chk_ShippingPlanData.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		//#CM693538
		// Check for input.Automated decision-making of Storage Location
		if (chk_IdmAuto.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}		
		
		//#CM693539
		// Error Message
		if (!wCheck_flag)
		{
			//#CM693540
			// 6023232=Please select the data to start.
			message.setMsgResourceKey("6023232");
			return;
		}

		Connection conn = null;
		try
		{
			//#CM693541
			// Obtain the connection.
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			SystemParameter[] param = new SystemParameter[1];
			param[0] = new SystemParameter();

			WmsScheduler schedule = new LoadDataSCH();

			//#CM693542
			// Set the value for the parameter.
			//#CM693543
			// Worker Code
			param[0].setWorkerCode(txt_WorkerCode.getText());
			//#CM693544
			// Password
			param[0].setPassword(txt_Password.getText());
			//#CM693545
			// Planned date
			param[0].setPlanDate(WmsFormatter.toParamDate(txt_PlanDate.getDate()));

			//#CM693546
			// Receiving plan data
			param[0].setSelectLoadInstockData(chk_InstockPlanData.getChecked());

			//#CM693547
			// Storage plan data
			param[0].setSelectLoadStorageData(chk_StoragePlanData.getChecked());
			//#CM693548
			// Decide the Storage Location automatically.
			param[0].setSelectLoadIdmAuto(chk_IdmAuto.getChecked());

			//#CM693549
			// Check whether the field Storage Plan Data is ticked off or not.
			if (!selectCheck(chk_StoragePlanData.getChecked(), chk_IdmAuto.getChecked()))
			{
				//#CM693550
				// 6023392=If the auto storage location decision process is to be performed, also tick off auto loading of the storage plan data.
				message.setMsgResourceKey("6023392");
				return;
			}
			//#CM693551
			// Picking plan data
			param[0].setSelectLoadRetrievalData(chk_RetrievalPlanData.getChecked());

			//#CM693552
			// Sorting Plan data
			param[0].setSelectLoadSortingData(chk_PickingPlanData.getChecked());

			//#CM693553
			// Shipping plan data
			param[0].setSelectLoadShippingData(chk_ShippingPlanData.getChecked());



			//#CM693554
			// Terminal No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param[0].setTerminalNumber(userHandler.getTerminalNo());

			//#CM693555
			// Start the schedule.
			if (schedule.startSCH(conn, param))
			{
				conn.commit();
			}
			else
			{
				conn.rollback();
			}

			if (schedule.getMessage() != null)
			{
				//#CM693556
				// Display a message.
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
				//#CM693557
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

	//#CM693558
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693559
	/** 
	 * Clicking on Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area.<BR>
	 * <BR>
	 * <DIR>
		 *   1.Reset the field items in Input area except for Worker Code and Password to their default value. <BR>
		 *   <DIR>
		 *     For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method. <BR>
		 *   </DIR>
		 *   2.Set the cursor for Worker code. <BR>
		 * </DIR>
		 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM693560
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);

		Connection conn = null;
		try
		{
            //#CM693561
            // Set the Planned date.
            txt_PlanDate.setText("");
   
		    //#CM693562
		    // Default of the checkbox.
		    chk_InstockPlanData.setChecked(false);
		    chk_StoragePlanData.setChecked(false);
		    chk_RetrievalPlanData.setChecked(false);
		    chk_PickingPlanData.setChecked(false);
		    chk_ShippingPlanData.setChecked(false);
		    chk_IdmAuto.setChecked(false);
			
			//#CM693563
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			SystemParameter initParam = new SystemParameter();
			WmsScheduler schedule = new LoadDataSCH();
			SystemParameter param = (SystemParameter) schedule.initFind(conn, initParam);

			//#CM693564
			// Set the Planned date.
			if (param.getPlanDate() != null)
			{
				txt_PlanDate.setDate(WmsFormatter.toDate(param.getPlanDate()));
			}
			else
			{
				txt_PlanDate.setText("");
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
				//#CM693565
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

	//#CM693566
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693567
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}
	//#CM693568
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_DataLoad_Click(ActionEvent e) throws Exception
	{
	}

	//#CM693569
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_IdmAuto_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693570
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_IdmAuto_Change(ActionEvent e) throws Exception
	{
	}
}
//#CM693571
//end of class
