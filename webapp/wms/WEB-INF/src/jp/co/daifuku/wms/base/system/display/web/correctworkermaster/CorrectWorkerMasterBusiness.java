// $Id: CorrectWorkerMasterBusiness.java,v 1.2 2006/11/13 08:18:07 suresh Exp $

//#CM687363
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.correctworkermaster;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.system.display.web.listbox.listworker.ListWorkerBusiness;
import jp.co.daifuku.wms.base.system.schedule.CorrectWorkerMasterSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM687364
/**
 * Designer : B.Shibayama <BR>
 * Maker    : B.Shibayama <BR>
 * <BR>
 * Allow this screen to modify or delete Worker master (input conditions).<BR>
 * Set the contents input via screen for the parameter and then pass it to the schedule. Shift to the second screen. <BR>
 * Receive the result from the schedule. Receive true if the process completed normally. <BR>
 * Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 * As the result of the schedule, output the message obtained from the schedule to the screen. <BR>
 * <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process by clicking "Next" button(<CODE>btn_Next_Click</CODE>method)<BR>
 * <BR>
 * <DIR>
 *   Check the field items. If proper, set them for the parameter. <BR>
 *   If the result of the schedule is true, maintain it in the viewState and shift to the next screen. <BR>
 *   Display the message when error occurs during the schedule process. <BR>
 * <BR>
 *   [Parameter] *Mandatory Input <BR>
 * <BR>
 *   <DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *   </DIR>
 * <BR>
 *    [Returned data] <BR>
 *    <BR>
 *    <DIR>
 *     Message
 *    </DIR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/09</TD><TD>B.Shibayama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 *
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:07 $
 * @author  $Author: suresh $
 */
public class CorrectWorkerMasterBusiness extends CorrectWorkerMaster implements WMSConstants
{
	//#CM687365
	// Class fields --------------------------------------------------

	//#CM687366
	// Class variables -----------------------------------------------

	//#CM687367
	// Class method --------------------------------------------------

	//#CM687368
	// Constructors --------------------------------------------------

	//#CM687369
	// Public methods ------------------------------------------------
	
	//#CM687370
	/**
	 * Completing reading a screen invokes this.<BR>
     * <BR>
     * Summary: Shows the Initial Display.<BR>
     * <BR>
     * <DIR>
     *    1.Display the title.<BR>
     *    2.Display it if any value is in Worker Code, Password, or Modified Worker Code of the ViewState.<BR>
     *    3.Move the cursor to the Worker Code. <BR>
     * </DIR>
	 * <BR>
     * Field item [Initial Value] <BR>
     * <BR>
     * Worker Code [None] <BR>
     * Password [None] <BR>
     * Modified Worker Code [None] <BR>
     * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
        //#CM687371
        // Set the title when returning from the second screen.
		if( !StringUtil.isBlank(getViewState().getString("TITLE")))
		{
			lbl_SettingName.setResourceKey(getViewState().getString("TITLE"));
		}
		
		//#CM687372
		// Display the tab of the original screen foreground.
		tab_WkrMsrIptMdfy.setSelectedIndex(1);
		
		//#CM687373
		// Worker Code
		if( !StringUtil.isBlank(getViewState().getString("Worker")))
		{
		    txt_WorkerCode_T.setText(getViewState().getString("Worker"));
		}	
		//#CM687374
		// Password
		if( !StringUtil.isBlank(getViewState().getString("Password")))
		{
		    txt_Password_T.setText(getViewState().getString("Password"));
		}
		
		//#CM687375
		// If a value of Worker Code exists in Input area:
		if( !StringUtil.isBlank(getViewState().getString("Workercode")))
		{
			txt_WorkerCode.setText(getViewState().getString("Workercode"));
		}
		else
		{
			txt_WorkerCode.setText("");
		}
        
        //#CM687376
        // Move the cursor to the Worker Code.
		setFocus(txt_WorkerCode_T);
	}

	//#CM687377
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
			//#CM687378
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM687379
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM687380
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM687381
			//Set the path to the help file.
			//#CM687382
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
	}	

	//#CM687383
	// Package methods -----------------------------------------------

	//#CM687384
	// Protected methods ---------------------------------------------

	//#CM687385
	// Private methods -----------------------------------------------

	//#CM687386
	// Event handler methods -----------------------------------------
	//#CM687387
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687388
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687389
	/** 
	 * Clicking on Menu button invokes this.<BR>
	 * <BR>
	 * Summary: Returns to the Menu screen.<BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		//#CM687390
		// Return to the Menu screen.
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM687391
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687392
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687393
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687394
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687395
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM687396
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687397
	/** 
	 * Clicking on Search button of Modified Worker Code invokes this.<BR>
	 * <BR>
	 * Summary: Sets the input data for the parameter and displays a listbox for searching the modified worker.<BR>
	 * <BR><DIR>
	 * [Parameter] <BR>
	 * <BR></DIR> 
	 *      Modified Worker Code <BR>
	 * </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearch_Click(ActionEvent e) throws Exception
	{
		//#CM687398
		// Set the search conditions for the Search Worker listbox.
		ForwardParameters param = new ForwardParameters();
		
		//#CM687399
		// Worker Code
		param.setParameter(ListWorkerBusiness.WORKERCODE_KEY, txt_WorkerCode.getText()); 
        //#CM687400
        // Search through the Worker table.
		param.setParameter(ListWorkerBusiness.SEARCHWORKER_KEY, SystemParameter.SEARCHFLAG_PLAN);
		//#CM687401
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listworker/ListWorker.do", param, "/progress.do");
	}

	//#CM687402
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Next_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687403
	/** 
	 * Clicking on Next button invokes this.<BR>
	 * <BR>
	 * Summary: Displays the Modify screen of the relevant Worker info.<BR>
     * <BR>
     * <DIR>
     *     1.Check for input.<BR>
     *     2.Set the input data for the Parameter.<BR>
     *     3.Start the schedule.<BR>
     *     4.Maintain the data in the ViewState.<BR>
     *     5.Shift to the next screen (Screen for modifying the Worker master).<BR>
     * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Next_Click(ActionEvent e) throws Exception
	{
		//#CM687404
		// Move the cursor to the Worker Code.
		setFocus(txt_WorkerCode_T);
		//#CM687405
		// Check the field items.
		txt_WorkerCode_T.validate();
		txt_Password_T.validate();
		txt_WorkerCode.validate();
		
		Connection conn = null;
		
		try
		{
            //#CM687406
            // Declare the Schedule parameter.
			SystemParameter param = new SystemParameter();
			
			//#CM687407
			// Worker Code 
			param.setWorkerCode(txt_WorkerCode_T.getText());
			//#CM687408
			// Password
			param.setPassword(txt_Password_T.getText());

			//#CM687409
			// Worker Code
			if (txt_WorkerCode.getText().equals("wms"))
			{
				//#CM687410
				// 6023507=The registered worker can't be modified.
				message.setMsgResourceKey("6023507");
				return;
			}
			else
			{
				param.setWorkerCodeB(txt_WorkerCode.getText());
			}
			
            //#CM687411
            // Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			//#CM687412
			// Declare the schedule. 
			WmsScheduler schedule= new CorrectWorkerMasterSCH();
			    
            if(schedule.nextCheck(conn,param))
            {
            	//#CM687413
            	// Set the Worker Code for ViewState.
				getViewState().setString("Worker",txt_WorkerCode_T.getText());
            	//#CM687414
            	// Set the password for ViewState.
				getViewState().setString("Password",txt_Password_T.getText());
            	//#CM687415
            	// Set the Worker Code for ViewState.
				getViewState().setString("Workercode",txt_WorkerCode.getText());
				//#CM687416
				// Set the Screen Title for ViewState. 
				getViewState().setString("TITLE", lbl_SettingName.getResourceKey());
                //#CM687417
                // Shift the screen. 
				forward("/system/correctworkermaster/CorrectWorkerMaster2.do");
            }else
            {
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
				//#CM687418
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

	//#CM687419
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687420
	/**
     * Clicking on Clear button invokes this.<BR>
     * <BR>
     * Summary: Clears the input area.<BR>
     * </BR>
     * <DIR>
     *        1.Return the item (count) in the input area to the initial value.<BR>
     *        <DIR>
     *        For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method. <BR>
     *        </DIR>
     *        2.Set the cursor for Worker code. <BR>
     * </DIR>
     * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
     */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM687421
		// Clear
		txt_WorkerCode.setText("");
        //#CM687422
        // Move the cursor to the Worker Code.
		setFocus(txt_WorkerCode_T);
	}
	
	//#CM687423
	/**
	 * Invoke this method when returning from the popup window.<BR>
	 * Override the page_DlgBack defined for Page.<BR>
	 * <BR>
	 * Summary: Obtains the Returned data in the Search screen and set it.<BR>
	 * <BR><DIR>
	 *      1.Obtain the value returned from the Search popup screen.<BR>
	 *      2.Set the screen if the value is not empty.<BR>
	 * <BR></DIR>
	 * [Returned data] <BR>
	 * <DIR>
	 *     Modified Worker Code <BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	 public void page_DlgBack(ActionEvent e) throws Exception
	 {
		DialogParameters param = ((DialogEvent)e).getDialogParameters();
		//#CM687424
		// Obtain the parameter selected in the listbox.
		String workercode = param.getParameter(ListWorkerBusiness.WORKERCODE_KEY );
	
		//#CM687425
		// Set a value if not empty.
		//#CM687426
		// Modified Worker Code
		if (!StringUtil.isBlank(workercode))
		{
		  	txt_WorkerCode.setText(workercode);
		}
		
        //#CM687427
        // Move the cursor to the Worker Code.
		setFocus(txt_WorkerCode_T);
	 }
	//#CM687428
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687429
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_WkrMsrIptMdfy_Click(ActionEvent e) throws Exception
	{
	}

	//#CM687430
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_T_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687431
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_T_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687432
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_T_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687433
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_T_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687434
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_T_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM687435
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_T_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687436
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_T_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687437
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_T_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687438
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_T_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687439
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_T_InputComplete(ActionEvent e) throws Exception
	{
	}

}
//#CM687440
//end of class
