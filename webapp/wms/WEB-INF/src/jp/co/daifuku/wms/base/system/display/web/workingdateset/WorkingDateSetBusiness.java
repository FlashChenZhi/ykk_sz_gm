// $Id: WorkingDateSetBusiness.java,v 1.2 2006/11/13 08:22:25 suresh Exp $

//#CM695171
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.workingdateset;
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
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.system.schedule.WorkingDateSetSCH;

//#CM695172
/**
 * Designer : B.Shibayama <BR>
 * Maker    : B.Shibayama <BR>
 * <BR>
 * Allow this class to provide a screen for setting the Work Date.<BR>
 * Set the contents input via screen for the parameter and pass it to the schedule. Execute the process for setting.<BR>
 * Receive the result from the schedule. Receive true if the process completed normally.<BR>
 * Or, receive false if the schedule failed to complete due to condition error etc.<BR>
 * As the result of the schedule, output the message obtained from the schedule to the screen.<BR>
 * <BR>
 * Allow this class to execute the following processes.<BR>
 * 
 * 1.for loading a page (<CODE>page_Load</CODE> method) <BR>
 * <BR>
 * <DIR>
 *  Display the current work sate in the initial display. <BR>
 *  <BR>
 *   [Parameter]  <BR>
 *  <BR>
 *   <DIR>
 *       None<BR>
 *   </DIR>
 *  <BR>
 *  [Returned data]  <BR>
 *  <BR>
 *    <DIR>
 *       Message<BR>
 *       Work date <BR>
 *    </DIR>
 * </DIR>
 * <BR>
 * 2.Process by clicking "Add" button(<CODE>btn_Submit_Click</CODE>method)<BR>
 * <BR>
 * <DIR>
 *   Check the field items. If proper, set them for the parameter.<BR>
 *   Set the result of schedule in the Message area and display it.<BR>
 * <BR>
 *   [Parameter] *Mandatory Input <BR>
 * <BR>
 *   <DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Work date* <BR>
 *   </DIR>
 * <BR>
 *    [Returned data] <BR>
 * <BR>
 *    <DIR>
 *     Each message
 *    </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/20</TD><TD>B.Shibayama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:22:25 $
 * @author  $Author: suresh $
 */
public class WorkingDateSetBusiness extends WorkingDateSet implements WMSConstants
{
	//#CM695173
	// Class fields --------------------------------------------------

	//#CM695174
	// Class variables -----------------------------------------------

	//#CM695175
	// Class method --------------------------------------------------

	//#CM695176
	// Constructors --------------------------------------------------

	//#CM695177
	// Public methods ------------------------------------------------

	//#CM695178
	/**
	 * Completing reading a screen invokes this. <BR>
	 * <BR>
	 * Summary: Shows the Initial Display. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Display the screen title. <BR>
	 *    2.Start the schedule. <BR>
	 *    3.Set the cursor for Worker code. <BR>
	 * </DIR>
	 * <BR>
	 * Field item [Initial Value] <BR>
	 * <BR>
	 * Worker Code [None] <BR>
	 * Password [None] <BR>
	 * Work Date [System definitions, Work date] <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		
		//#CM695179
		// Show the Initial Display.
		txt_WorkerCode.setText("");
		txt_Password.setText("");

		setWorkingDate(e);

	}

	//#CM695180
	/**
	* Invoke this before invoking each control event. <BR>
	* <BR>
	* <DIR>
	*  Summary: Displays a dialog. <BR>
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
			//#CM695181
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM695182
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM695183
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM695184
			//Set the path to the help file.
			//#CM695185
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM695186
		//Display a dialog: MSG-0009= "Do you add it?"
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}

	//#CM695187
	// Package methods -----------------------------------------------

	//#CM695188
	// Protected methods ---------------------------------------------

	//#CM695189
	// Private methods -----------------------------------------------
	//#CM695190
	/**
	* Clicking on Page Load button and Clear button invokes this.<BR>
	* <DIR>
	* Summary: Sets the Work Date. <BR>
	* <DIR>
	*      1.Start the schedule. <BR>
	*      2.Display the obtained data. <BR>
	* </DIR>
	* </DIR>
	* 
	* @param e ActionEvent A class to store event information.
	* @throws Exception Report all exceptions.
	*/
	private void setWorkingDate(ActionEvent e) throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM695191
			// Set the cursor for Worker code.
			setFocus(txt_WorkerCode);
			
			//#CM695192
			// Initialize the Work Date.
			txt_WorkDate1.setText("");

			//#CM695193
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM695194
			// Declare the schedule.
			WmsScheduler schedule= new WorkingDateSetSCH();
			
			//#CM695195
			// Start the schedule.
			SystemParameter param = (SystemParameter) schedule.initFind(conn, null);
			
			if(param == null)
			{
               //#CM695196
               // Display a message.
			   message.setMsgResourceKey(schedule.getMessage());	
			}else
			{
			   txt_WorkDate1.setDate(WmsFormatter.toDate(param.getWorkDate()));
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
				//#CM695197
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

	//#CM695198
	// Event handler methods -----------------------------------------
	//#CM695199
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695200
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695201
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695202
	/** 
	 * Clicking on Menu button invokes this.<BR>
	 * <BR>
	 * Summary: Returns to the Menu screen. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		//#CM695203
		// Return to the Menu screen.
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM695204
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695205
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695206
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM695207
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM695208
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM695209
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695210
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695211
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM695212
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM695213
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM695214
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695215
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkDate1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695216
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkDate1_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM695217
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkDate1_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM695218
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695219
	/** 
	 * Clicking on Add button invokes this.<BR>
	 * <BR>
	 * Summary: Displays a dialog box to allow to confirm to add it or not and then passes the parameter.<BR>
	 * <BR>
	 * <DIR>
	 *    -Display the dialog box for confirming to add. ("Do you add this?") <BR>
	 *    <DIR>
	 *        [Dialog for confirming to add: OK ]<BR>
	 *        <DIR>
	 *            1.Set the cursor for Worker code.<BR>
	 *            2.Check for input in the input item (count) in the input area. (Mandatory Input, number of characters, and attribution of characters)<BR>
	 * 	          3.Set the info in the input area for the Parameter.<BR>
	 *            4.Start the schedule.<BR>
	 *            5.Display a message.<BR>
	 *        </DIR>
	 *        [Dialog for confirming to add: Cancel] <BR>
	 *        <DIR>
	 *             Disable to do anything.<BR>
	 *        </DIR>
	 *    </DIR> 
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception  Report all exceptions. 
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
        //#CM695220
        // Move the cursor to the Worker Code.
		setFocus(txt_WorkerCode);
		
        //#CM695221
        // Check for input.
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_WorkDate1.validate();
		
		Connection conn = null;

		try
		{
			

			//#CM695222
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM695223
			// Set the parameter.
			SystemParameter[] param = new SystemParameter[1];
			param[0] = new SystemParameter();
			//#CM695224
			// Worker Code
			param[0].setWorkerCode(txt_WorkerCode.getText());
			//#CM695225
			// Password
			param[0].setPassword(txt_Password.getText());
			//#CM695226
			// Work date
			param[0].setWorkDate(WmsFormatter.toParamDate(txt_WorkDate1.getDate()));
			
			//#CM695227
			// Declare the schedule. 
			WmsScheduler schedule= new WorkingDateSetSCH();
				
			//#CM695228
			// Start the schedule.		
			if (schedule.startSCH(conn, param))
			{
				//#CM695229
				// Execute committing.
				conn.commit();
			}
			else
			{
				//#CM695230
				// Execute Roll-back.
				conn.rollback();
			}

			//#CM695231
			// Set the message
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM695232
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

	//#CM695233
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695234
	/**
	 * Clicking on Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area.<BR>
	 * </BR>
	 * <DIR>
	 *        1.Set the system definition.work date for the text of the Work Date. <BR>
	 *        2.Set the cursor for the Worker code. <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM695235
		// Private method
		setWorkingDate(e);
	}

}
//#CM695236
//end of class
