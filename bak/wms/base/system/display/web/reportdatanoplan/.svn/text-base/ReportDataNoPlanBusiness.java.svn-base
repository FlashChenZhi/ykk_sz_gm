// $Id: ReportDataNoPlanBusiness.java,v 1.2 2006/11/13 08:21:44 suresh Exp $

//#CM694305
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.reportdatanoplan;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.system.schedule.ReportDataNoPlanSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM694306
/**
 * Designer : T.Hondo <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * Allow this class to report data (for reporting the Unplanned Work).<BR>
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
 *		Planned Work Date* <BR>
 *		Stock relocation result data <BR>
 *		Inventory Check result data <BR>
 *		Unplanned storage result data <BR>
 *		Unplanned picking result data <BR>
 * 	</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/06</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:44 $
 * @author  $Author: suresh $
 */
public class ReportDataNoPlanBusiness extends ReportDataNoPlan implements WMSConstants
{
	//#CM694307
	// Class fields --------------------------------------------------

	//#CM694308
	// Class variables -----------------------------------------------

	//#CM694309
	// Class method --------------------------------------------------

	//#CM694310
	// Constructors --------------------------------------------------

	//#CM694311
	// Public methods ------------------------------------------------

	//#CM694312
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
			//#CM694313
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM694314
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM694315
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM694316
			//Set the path to the help file.
			//#CM694317
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM694318
		// MSG-0019= Do you start it?
		btn_Start.setBeforeConfirm("MSG-W0019");
	}

	//#CM694319
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Field item [Initial Value]<BR>
	 * <DIR>
	 *	Worker Code			[None] <BR>
	 *	Password				[None] <BR>
	 *	Planned Work Date				[Work Date] <BR>
	 *	Stock relocation result data		[Not Ticked] <BR>
	 *	Inventory Check result data		[Not Ticked] <BR>
	 *	Unplanned storage result data	[Not Ticked] <BR>
	 *	Unplanned picking result data	[Not Ticked] <BR>
	 * </DIR>
	 *	1.Set the cursor for Worker code. <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM694320
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);
		
		//#CM694321
		// Set the initial value of the Worker Code.
		txt_WorkerCode.setText("");
		//#CM694322
		// Set the initial value of the Password.
		txt_Password.setText("");

		setFirstDisp();
	}

	//#CM694323
	// Package methods -----------------------------------------------

	//#CM694324
	// Protected methods ---------------------------------------------

	//#CM694325
	// Private methods -----------------------------------------------
	//#CM694326
	/**
	 * Showing the initial display invokes this method.
	 * @throws Exception Report all exceptions. 
	 */
	private void setFirstDisp() throws Exception
	{

		Connection conn = null;
		try
		{
			//#CM694327
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			SystemParameter initParam = new SystemParameter();
			WmsScheduler schedule = new ReportDataNoPlanSCH();
			SystemParameter param = (SystemParameter) schedule.initFind(conn, initParam);

			//#CM694328
			// Set the initial value of the Work Date.
			if (param.getPlanDate() != null)
			{
				txt_WorkDate.setDate(WmsFormatter.toDate(param.getPlanDate()));
			}

			//#CM694329
			// Enable to read-only for the field items that does not contain package.
			//#CM694330
			// Relocation Result
			if (!param.getSelectReportMovementData())
			{
				chk_StockMovRsltData.setEnabled(false);
			}
			else
			{
				chk_StockMovRsltData.setEnabled(true);
			}
			//#CM694331
			// Inventory Check Result
			if (!param.getSelectReportInventoryData())
			{
				chk_InvntryRsltData.setEnabled(false);
			}
			else
			{
				chk_InvntryRsltData.setEnabled(true);
			}
			//#CM694332
			// Unplanned Storage Result
			if (!param.getSelectReportNoPlanStorageData())
			{
				chk_NoPlanStorageRsltData.setEnabled(false);
			}
			else
			{
				chk_NoPlanStorageRsltData.setEnabled(true);
			}
			//#CM694333
			// Unplanned Picking Result
			if (!param.getSelectReportNoPlanRetrievalData())
			{
				chk_NoPlanRetrievalRsltData.setEnabled(false);
			}
			else
			{
				chk_NoPlanRetrievalRsltData.setEnabled(true);
			}

			//#CM694334
			// Relocation Result
			chk_StockMovRsltData.setChecked(false);
			//#CM694335
			// Inventory Check Result
			chk_InvntryRsltData.setChecked(false);
			//#CM694336
			// Unplanned Storage Result
			chk_NoPlanStorageRsltData.setChecked(false);
			//#CM694337
			// Unplanned Picking Result
			chk_NoPlanRetrievalRsltData.setChecked(false);
			
			//#CM694338
			// Clear the message.
			lbl_StockMoveMessage.setText(" ");
			lbl_InventoryMessage.setText(" ");
			lbl_NoPlanStorageMessage.setText(" ");
			lbl_NoPlanRetrievalMessage.setText(" ");
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM694339
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

	//#CM694340
	// Event handler methods -----------------------------------------
	//#CM694341
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694342
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694343
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694344
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

	//#CM694345
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694346
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694347
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694348
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694349
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694350
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694351
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694352
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694353
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694354
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694355
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694356
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694357
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694358
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_StockMovRsltData_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694359
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_StockMovRsltData_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694360
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InvntryRsltData_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694361
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_InvntryRsltData_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694362
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Start_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694363
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
		//#CM694364
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);

		//#CM694365
		// Check for input.
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_WorkDate.validate();

		//#CM694366
		// Check for input.
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_WorkDate.validate();

		Connection conn = null;
		ReportDataNoPlanSCH schedule = new ReportDataNoPlanSCH();
		try
		{
			SystemParameter[] param = new SystemParameter[1];
			param[0] = new SystemParameter();

			//#CM694367
			// Obtain the connection.
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM694368
			// Set the value for the parameter.
			//#CM694369
			// Worker Code
			param[0].setWorkerCode(txt_WorkerCode.getText());
			//#CM694370
			// Password
			param[0].setPassword(txt_Password.getText());
			//#CM694371
			// Work date
			param[0].setWorkDate(WmsFormatter.toParamDate(txt_WorkDate.getDate()));
			//#CM694372
			// Relocation Result
			param[0].setSelectReportMovementData(chk_StockMovRsltData.getChecked());
			//#CM694373
			// Inventory Check Result
			param[0].setSelectReportInventoryData(chk_InvntryRsltData.getChecked());
			//#CM694374
			// Unplanned Storage Result
			param[0].setSelectReportNoPlanStorageData(chk_NoPlanStorageRsltData.getChecked());
			//#CM694375
			// Unplanned Picking Result
			param[0].setSelectReportNoPlanRetrievalData(chk_NoPlanRetrievalRsltData.getChecked());

			//#CM694376
			// Start the schedule.
			if (schedule.startSCH(conn, param))
			{
				conn.commit();
				//#CM694377
				// Relocation Result
				chk_StockMovRsltData.setChecked(false);
				//#CM694378
				// Inventory Check Result
				chk_InvntryRsltData.setChecked(false);
				//#CM694379
				// Unplanned Storage Result
				chk_NoPlanStorageRsltData.setChecked(false);
				//#CM694380
				// Unplanned Picking Result
				chk_NoPlanRetrievalRsltData.setChecked(false);
			}
			else
			{
				conn.rollback();
			}

			if (schedule.getMessage() != null)
			{
				//#CM694381
				// Display a message.
				message.setMsgResourceKey(schedule.getMessage());
			}
			if (schedule.getMessageList() != null)
			{
				//#CM694382
				// Display a message.
				String[] messageList = (String[])schedule.getMessageList();
				//#CM694383
				// Clear the message.
				lbl_StockMoveMessage.setText(messageList[0]);
				lbl_InventoryMessage.setText(messageList[1]);
				lbl_NoPlanStorageMessage.setText(messageList[2]);
				lbl_NoPlanRetrievalMessage.setText(messageList[3]);
			}
		}
		catch (Exception ex)
		{
		    message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		    if (schedule.getMessageList() != null)
		    {
			    //#CM694384
			    // Display a message.
			    String[] messageList = (String[])schedule.getMessageList();
			    //#CM694385
			    // Clear the message.
			    lbl_StockMoveMessage.setText(messageList[0]);
			    lbl_InventoryMessage.setText(messageList[1]);
			    lbl_NoPlanStorageMessage.setText(messageList[2]);
			    lbl_NoPlanRetrievalMessage.setText(messageList[3]);
		    }
		}
		finally
		{
			try
			{
				//#CM694386
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

	//#CM694387
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694388
	/** 
	 * Clicking on Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area.<BR>
	 * </BR>
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
		//#CM694389
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);
		
		setFirstDisp();
	}

	//#CM694390
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}
	//#CM694391
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_DataReport_Click(ActionEvent e) throws Exception
	{
	}

	//#CM694392
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_NoPlanStorageRsltData_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694393
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_NoPlanStorageRsltData_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694394
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_NoPlanRetrievalRsltData_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694395
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_NoPlanRetrievalRsltData_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694396
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Select_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694397
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ReportDetals_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694398
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Message_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694399
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StockMoveReport_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694400
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StockMoveMessage_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694401
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InventoryReport_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694402
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InventoryMessage_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694403
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_NoPlanStorageReport_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694404
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_NoPlanStorageMessage_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694405
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_NoPlanRetrievalReport_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694406
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_NoPlanRetrievalMessage_Server(ActionEvent e) throws Exception
	{
	}
}
//#CM694407
//end of class
