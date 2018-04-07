// $Id: DailyUpdateBusiness.java,v 1.2 2006/11/13 08:18:15 suresh Exp $

//#CM687445
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.dailyupdate;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.system.schedule.DailyUpdateSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM687446
/**
 * Designer :  A.Nagasawa <BR>
 * Maker :     A.Nagasawa <BR>
 * <BR>
 * Allow this class to provide a screen for daily update. <BR>
 * Set the contents entered via screen for the parameter. Allow the schedule to execute the daily update based on the parameter.<BR>
 * Allow the schedule to return true when the schedule completed printing successfully or return false when it failed.
 * <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * Process by clicking "Start" button (Dialog for confirming to start: OK) (btn_Start_Click(ActionEvent e) method)<BR>
 * <BR>
 * <DIR>
 *   Display a dialog box to allow to confirm to execute the daily update or not. If confirmed, execute the daily update using the information in the Input area. <BR>
 * <BR>
 *     [Parameter] *Mandatory Input<BR>
 *    <BR>
 *	  <DIR>
 *       Worker Code* <BR>
 *       Password* <BR>
 *       Work Date (Display the default) <BR>
 *       Status "Not Worked"* <BR>
 * 	  </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/09</TD><TD>A.Nagasawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:15 $
 * @author  $Author: suresh $
 */
public class DailyUpdateBusiness extends DailyUpdate implements WMSConstants
{
	//#CM687447
	// Class fields --------------------------------------------------
	//#CM687448
	// Maintain the control that invoked the dialog: Check for picking work if true.
	protected static final String DIALOG_KBN = "DIALOG_KBN";

	//#CM687449
	// Class variables -----------------------------------------------

	//#CM687450
	// Class method --------------------------------------------------

	//#CM687451
	// Constructors --------------------------------------------------

	//#CM687452
	// Public methods ------------------------------------------------

	//#CM687453
	/**
	* Initialize the screen.<BR>
	* <BR>
	* <DIR>
	*   Worker Code [None] <BR>
	*   Password [None] <BR>
	*   Work Date [Work date in the System definitions] <BR>
	*   Status "Not Worked" [Delete] <BR>
	* 	<BR>
	*   Set the cursor for Worker code. <BR>
	* </DIR>
	* @param e ActionEvent A class to store event information.
	* @throws Exception Report all exceptions.
	*/
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM687454
		// Show the Initial Display.
		StartDisp();
	}
	//#CM687455
	/**
	 * Invoke this before invoking each control event. <BR>
	 * <BR>
	 * <DIR>
	 *  Summary: Displays a dialog. <BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		System.out.println("!!page_Initialize start!!");
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM687456
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM687457
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM687458
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM687459
			//Set the path to the help file. 
			//#CM687460
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
	}


	//#CM687461
	// Package methods -----------------------------------------------

	//#CM687462
	// Protected methods ---------------------------------------------

	//#CM687463
	// Private methods -----------------------------------------------
	//#CM687464
	/**
	 * Show the Initial Display.
	 * @throws Exception Report all exceptions.
	 */
	private void StartDisp() throws Exception
	{
		//#CM687465
		// Initial Display
		rdo_Delete.setChecked(true);
		rdo_WorkInfoOrver.setChecked(false);
		//#CM687466
		// Set the cursor for Worker code.
		setFocus(txt_WorkerCode);

		Connection conn = null;
		try
		{
			SystemParameter param = new SystemParameter();

			//#CM687467
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new DailyUpdateSCH();
			SystemParameter viewParam = (SystemParameter) schedule.initFind(conn, param);

			//#CM687468
			// Display the initial value of Work Date.
			txt_FDate.setDate(WmsFormatter.toDate(viewParam.getWorkDate()));
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM687469
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
	//#CM687470
	/**
	 * Clicking on Start button invokes this.
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		try
		{
			//#CM687471
			// Identify which dialog returns it.
			//#CM687472
			// Execute the process defined for RFT check when invoking the dialog. 
			if (!this.getViewState().getBoolean(DIALOG_KBN))
			{
				//#CM687473
				// Clicking on "Yes" starts the daily update.
				if (new Boolean(e.getEventArgs().get(0).toString()).booleanValue())
				{
					DialogWorkCheck();
					return ;
				}	
			}
			//#CM687474
			// Execute the process defined for the case where a dialog is invoked by determining a Picking.
			else
			{
				if (new Boolean(e.getEventArgs().get(0).toString()).booleanValue())
				{
			        //#CM687475
			        // Clicking on "Yes" starts the daily update.
					startDailyUpdate();
			    }

				//#CM687476
				// Ensure to change the flag to OFF as the action of dialog completed. 
				this.getViewState().setBoolean(DIALOG_KBN, false);
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
		}
	}

	//#CM687477
	/**
	 * Checking a Picking work dialog invokes this.
	 * @throws Exception Report all exceptions. 
	 */
	public void DialogWorkCheck() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM687478
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			//#CM687479
			// If the corresponding data is found, display a dialog to allow to confirm to continue the daily update.
			WareNaviSystemHandler wnsysHandle = new WareNaviSystemHandler(conn);
			WareNaviSystemSearchKey wnsysSearchKey = new WareNaviSystemSearchKey();
	
			WareNaviSystem wareNaviSystem = (WareNaviSystem)wnsysHandle.findPrimary(wnsysSearchKey);
	
			if (wareNaviSystem.getStockPack().equals(WareNaviSystem.PACKAGE_FLAG_ADDON) &&
				wareNaviSystem.getRetrievalPack().equals(WareNaviSystem.PACKAGE_FLAG_ADDON))
			{
				//#CM687480
				// Search through the Work Status data. If one or more picking data with status other than "Completed" or "Deleted" exists,
				//#CM687481
				// display a dialog to prompt the worker to confirm it.
				WorkingInformationHandler wkinfoHandle = new WorkingInformationHandler(conn);
				WorkingInformationSearchKey wkinfoSearchKey = new WorkingInformationSearchKey();
	
				wkinfoSearchKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL, "=", "((", "", "OR");
				wkinfoSearchKey.setJobType(WorkingInformation.JOB_TYPE_EX_RETRIEVAL, "=", "", ")", "AND");
				wkinfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION, "!=", "(", "", "AND");
				wkinfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=", "", "))", "AND");
	
				if (wkinfoHandle.count(wkinfoSearchKey) > 0)
				{
					//#CM687482
					// Display a dialog for confirming. 
					//#CM687483
					// MSG-W0065= There is a work of picking that has started. Do you confirm to execute the daily maintenance?
					setConfirm("MSG-W0065");
					
					//#CM687484
					// Display a message "Processing".
					message.setMsgResourceKey("6001017");
					
					//#CM687485
					// Store the fact that the dialog was displayed via Picking check. 
					this.getViewState().setBoolean(DIALOG_KBN, true);
				}
				else
				{
					//#CM687486
					// Check that RFT work dialog has been output.
					//#CM687487
					// If it has been already output and Picking is not ticked off, disable to display the dialog.
					RftHandler rftHandler = new RftHandler(conn);
					RftSearchKey rftSearchKey = new RftSearchKey();
					
					rftSearchKey.setJobType(Rft.JOB_TYPE_UNSTART, "!=");
					
					//#CM687488
					// Check for presence of any status other than Standby and output the dialog.
					//#CM687489
					// (In the case where no RFT work dialog appears)
					if (rftHandler.count(rftSearchKey) == 0)
					{
						//#CM687490
						// Display a dialog for confirming.
						//#CM687491
						// MSG-W0019= Do you start it?
						setConfirm("MSG-W0019");
						
						//#CM687492
						// Display a message "Processing".
						message.setMsgResourceKey("6001017");
	
						//#CM687493
						// Store the fact that the dialog was displayed via Picking check.
						this.getViewState().setBoolean(DIALOG_KBN, true);
					}
					else
					{
						startDailyUpdate();					
					}
				}
			}
			else
			{
				//#CM687494
				// Check that RFT work dialog has been output.
				//#CM687495
				// If it has been already output and Picking is not ticked off, disable to display the dialog.
				RftHandler rftHandler = new RftHandler(conn);
				RftSearchKey rftSearchKey = new RftSearchKey();
				
				rftSearchKey.setJobType(Rft.JOB_TYPE_UNSTART, "!=");
				
				//#CM687496
				// Check for presence of any status other than Standby and output the dialog.
				//#CM687497
				// (In the case where no RFT work dialog appears)
				if (rftHandler.count(rftSearchKey) == 0)
				{
					//#CM687498
					// Display a dialog for confirming.
					//#CM687499
					// MSG-W0019= Do you start it?
					setConfirm("MSG-W0019");
					
					//#CM687500
					// Display a message "Processing".
					message.setMsgResourceKey("6001017");
					
					//#CM687501
					// Store the fact that the dialog was displayed via Picking check.
					this.getViewState().setBoolean(DIALOG_KBN, true);
				}
				else
				{
					startDailyUpdate();				
				}
			}
		}
		catch (Exception ex)
		{
			//#CM687502
			// Roll-back the connection.
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
				//#CM687503
				// Close the connection.
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM687504
	/**
	 * Executing a daily update process invokes this.
	 * @throws Exception Report all exceptions.
	 */
	public void startDailyUpdate() throws Exception
	{
		//#CM687505
		// Set the contents of the input area for the Parameter.
		SystemParameter startParams = getInputParam();
		Vector vecParam = new Vector();
		vecParam.add(startParams);

		Connection conn = null;
		try
		{
			SystemParameter[] paramArray = new SystemParameter[vecParam.size()];
			vecParam.copyInto(paramArray);
			//#CM687506
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new DailyUpdateSCH();

			SystemParameter[] sparam =
				(SystemParameter[]) schedule.startSCHgetParams(conn, paramArray);

			//#CM687507
			// Start the schedule.
			if (sparam != null && sparam.length != 0)
			{
				conn.rollback();
				//#CM687508
				// Set the message.
				message.setMsgResourceKey(schedule.getMessage());

				//#CM687509
				// Set the Work Date forward by one day.
				txt_FDate.setDate(WmsFormatter.toDate(sparam[0].getWorkDate()));
			}
			else
			{
				conn.commit();
				//#CM687510
				// Set the message.
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
				//#CM687511
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
	
	//#CM687512
	/**
	 * Return the parameter set with the contents of the Input area for starting the process.
	 * 
	 * @return Parameter that maintains the contents of the input area.
	 */
	protected SystemParameter getInputParam()
	{
		//#CM687513
		// Set the parameter.
		SystemParameter startParams = new SystemParameter();
		//#CM687514
		// Worker Code
		startParams.setWorkerCode(txt_WorkerCode.getText());
		//#CM687515
		// Password
		startParams.setPassword(txt_Password.getText());
		//#CM687516
		// Work date
		startParams.setWorkDate(WmsFormatter.toParamDate(txt_FDate.getDate()));

		//#CM687517
		// Delete.
		if (rdo_Delete.getChecked())
		{
			startParams.setSelectUnworkingInformation(
				SystemParameter.SELECTUNWORKINGINFORMATION_DELETE);
			rdo_Delete.setChecked(true);
			rdo_WorkInfoOrver.setChecked(false);
		}
		//#CM687518
		// Hold over the Work Status.
		else if (rdo_WorkInfoOrver.getChecked())
		{
			startParams.setSelectUnworkingInformation(
				SystemParameter.SELECTUNWORKINGINFORMATION_CARRYOVER);
			rdo_Delete.setChecked(false);
			rdo_WorkInfoOrver.setChecked(true);
		}
		
		return startParams;
		
	}

	//#CM687519
	// Event handler methods -----------------------------------------
	//#CM687520
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687521
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687522
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687523
	/**
	 * Clicking on Menu button invokes this.<BR>
	 * <BR>
	 * Summary: Allow this method to execute the following processes.<BR>
	 * <BR>
	 * <DIR>
	 *   Shift to the Menu screen.<BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM687524
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687525
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687526
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687527
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687528
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM687529
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687530
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687531
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687532
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687533
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM687534
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_WorkDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687535
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_FDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687536
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_FDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687537
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_FDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687538
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_Upwi_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687539
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Delete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687540
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Delete_Click(ActionEvent e) throws Exception
	{
	}

	//#CM687541
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_WorkInfoOrver_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687542
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_WorkInfoOrver_Click(ActionEvent e) throws Exception
	{
	}

	//#CM687543
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Start_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687544
	/**
	 * Clicking on Start button invokes this.<BR>
	 * <BR>
	 * Summary: Displays a dialog box to allow to confirm to execute the daily update or not. <BR>
	 * <BR>
	 * <DIR>
	 *  1.Set the cursor for Worker code. <BR>
	 *   <BR>
	 *  2.Display a dialog box to allow to confirm to start: "Do you start it?" <BR>
	 *   <BR>
	 *   <DIR>
	 *     [Dialog for confirming to start: Cancel] <BR>
	 *     <DIR>
	 *       Disable to do anything. <BR>
	 *       <BR>
	 * 	  </DIR>
	 *     [Dialog for confirming to start: OK]
	 *     <DIR>
	 *       Start the schedule using the information in the Input area. <BR>
	 *       <DIR>
	 * 		   1.Set the input info for the parameter and check for input.<BR>
	 *        <BR>
	 *         2.Start the schedule.<BR>
	 *       </DIR>
	 *     </DIR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Start_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM687545
			// Set the cursor for Worker code.
			setFocus(txt_WorkerCode);
	
			//#CM687546
			// Check Mandatory Input.
			txt_WorkerCode.validate();
			txt_Password.validate();
	
			//#CM687547
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			WmsScheduler schedule = new DailyUpdateSCH();
			if (!schedule.check(conn, getInputParam()))
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
	
			//#CM687548
			// Process for checking dialog while RFT is running.
			RftHandler rftHandler = new RftHandler(conn);
			RftSearchKey rftSearchKey = new RftSearchKey();
			
			rftSearchKey.setJobType(Rft.JOB_TYPE_UNSTART, "!=");
			
			//#CM687549
			// Check for presence of any status other than Standby and output the dialog.
			if (rftHandler.count(rftSearchKey) > 0)
			{
				//#CM687550
				// Display a dialog for confirming.
				//#CM687551
				// MSG-W0066= There is an active handy terminal. Do you confirm it?
				setConfirm("MSG-W0066");
				
				//#CM687552
				// Display a message "Processing".
				message.setMsgResourceKey("6001017");
				
				return;
			}
			//#CM687553
			// If all the machines are Standby disable to output any dialog and go to the method for checking the Picking work.
			else
			{
				DialogWorkCheck();
			}
		}
		catch (Exception ex)
		{
			//#CM687554
			// Roll-back the connection.
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
				//#CM687555
				// Close the connection.
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM687556
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687557
	/**
	 * Clicking on Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 * <DIR>
	 *   1.Clear the field item in Input area.<BR>
	 *   <DIR>
	 *     Disable to clear work code and password. <BR>
	 *   </DIR>
	 *   2.Set the cursor for Worker code. <BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		StartDisp();
	}
}
//#CM687558
//end of class
