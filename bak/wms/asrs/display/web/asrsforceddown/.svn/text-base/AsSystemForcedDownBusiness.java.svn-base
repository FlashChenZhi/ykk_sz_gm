// $Id: AsSystemForcedDownBusiness.java,v 1.2 2006/10/26 04:14:30 suresh Exp $

//#CM35284
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsforceddown;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.asrs.schedule.AsWorkOperationSCH;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;

//#CM35285
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * The ASRS compulsion work end class. <BR>
 * set the input contents from the screen to a parameter
 * The schedule does the ASRS compulsion work termination based on the parameter. <BR>
 * Receive true when the result is received from the schedule, and processing is completed normally. 
 * Receive false when it does not complete the schedule because of the condition error etc.<BR>
 * output the schedule result, schedule message to screen<BR>
 * and transaction commit and rollback are done in this screen<BR>
 * <BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.set button click event(<CODE>btn_Setting_Click</CODE>method ) <BR>
 * <BR>
 * <DIR>
 *  The content input from input area is set in the parameter, and the schedule does the ASRS work termination based on the parameter. <BR>
 *  Receive true when the result is received from the schedule, and processing is completed normally. 
 *  Receive false when it does not complete the schedule because of the condition error etc.<BR>
 *  output the schedule result, schedule message to screen<BR>
 * 	<DIR>
 *   [parameter] *mandatory input <BR>
 *   <BR>
 * 		worker code *<BR>
 * 		password *<BR>
 * 		Selection mode(Single forced termination)*<BR>
 * 		list cell.AGC No.<BR>
 * 		terminal no.<BR>
 * 		list cell line no.<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.refresh button process(<CODE>btn_Reflesh_Click</CODE>method ) <BR>
 * <BR>
 * <DIR>
 *  The content input from input area is set in the parameter, and the schedule edits status of all AGC based on the parameter. <BR>
 *  Receive true when the result is received from the schedule, and processing is completed normally. 
 *  Receive false when it does not complete the schedule because of the condition error etc.<BR>
 *  output the schedule result, schedule message to screen<BR>
 *  <BR>
 *  [data for output use] <BR>
 *  <BR>
 * 	<DIR>
 *  	search Box <BR>
 *      AGC No. <BR>
 *      system status <BR>
 *      remaining work qty <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/9/29</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:14:30 $
 * @author  $Author: suresh $
 */
public class AsSystemForcedDownBusiness extends AsSystemForcedDown implements WMSConstants
{
	//#CM35286
	// Class fields --------------------------------------------------

	//#CM35287
	// Class variables -----------------------------------------------

	//#CM35288
	// Class method --------------------------------------------------

	//#CM35289
	// Constructors --------------------------------------------------

	//#CM35290
	// Public methods ------------------------------------------------

	//#CM35291
	/**
	 * screen initialization<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * <BR>
	 * <DIR>
	 * 		1.initialize input area<BR>
	 * 		2.set the cursor in worker code<BR>
	 * 		<BR>
	 * 		item[initial value] <BR>
	 * 		<DIR>
	 * 			worker code[nil] <BR>
	 * 			password[nil] <BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM35292
		// set initial focus to worker code
		setFocus(txt_WorkerCode);
		
		Connection conn = null;
		try
		{
			//#CM35293
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM35294
			//display data
			setList(conn, null);
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM35295
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM35296
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
			//#CM35297
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM35298
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM35299
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}
		//#CM35300
		// Confirmation dialog when Submit button is pressed "Want to finish the work?"
		btn_Setting.setBeforeConfirm("MSG-W0064");
	}
	
	//#CM35301
	/**
	 * This method is called when returning from popup window<BR>
	 * override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE><BR>
	 * <BR>
	 * overview ::set the cursor in worker code<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		//#CM35302
		// set the focus in worker code input
		setFocus(txt_WorkerCode);
	}

	//#CM35303
	// Package methods -----------------------------------------------

	//#CM35304
	// Protected methods ---------------------------------------------

	//#CM35305
	// Private methods -----------------------------------------------
	//#CM35306
	/** 
	 * Set data to List cell.<BR>
	 * <DIR>
	 * 		[List cell row number list]
	 * 		<DIR>
	 * 			0.Select Mode(hidden) <BR>
	 * 			1.Selection check box <BR>
	 * 			2.SRC No. <BR>
	 * 			3.System Status <BR>
	 * 			4.Remaining Works <BR>
	 *	 	</DIR>
	 * </DIR>
	 * @param conn Connection object with data base.
	 * @param selectlist Checked list data.
	 * @throws Exception It reports on all exceptions.
	  */
	private void setList(Connection conn, List selectlist) throws Exception
	{
		//#CM35307
		// eliminate all lines
		lst_AsrsForcedDown.clearRow();

		//#CM35308
		// set schedule parameter
		AsScheduleParameter param = new AsScheduleParameter();

		WmsScheduler schedule = new AsWorkOperationSCH();
		//#CM35309
		// The single work end flag is set. 
		param.setSelectMode(AsScheduleParameter.ASRS_WORKING_ONLYEND);

		AsScheduleParameter[] viewParam = (AsScheduleParameter[]) schedule.query(conn, param);

		if (viewParam == null || viewParam.length == 0)
		{
			//#CM35310
			// set message
			message.setMsgResourceKey(schedule.getMessage());

			return;
		}

		//#CM35311
		// set message
		message.setMsgResourceKey(schedule.getMessage());
		//#CM35312
		//eliminate all lines
		lst_AsrsForcedDown.clearRow();

		for(int i = 0; i < viewParam.length; i++)
		{
			AsScheduleParameter dispData = viewParam[i];
			String statusname = dispData.getSystemStatus();
			int count = lst_AsrsForcedDown.getMaxRows();
			lst_AsrsForcedDown.setCurrentRow(count);
			lst_AsrsForcedDown.addRow();

			//#CM35313
			//Check the marked data.
			lst_AsrsForcedDown.setValue(0, dispData.getSelectCurrentMode());
			if(selectlist == null)
			{
				if(dispData.getSelectCurrentMode().equals("1"))
				{
					lst_AsrsForcedDown.setChecked(1,true);
				}
			}
			else
			{
				if(selectlist.contains(dispData.getAgcNo()))
				{
					lst_AsrsForcedDown.setChecked(1,true);
				}
			}

			lst_AsrsForcedDown.setValue(2, dispData.getAgcNo());
			lst_AsrsForcedDown.setValue(3, statusname);
			lst_AsrsForcedDown.setValue(4, Integer.toString(dispData.getWorkingCount()));
		}
		//#CM35314
		// set message
		message.setMsgResourceKey(schedule.getMessage());
	}

	//#CM35315
	// Event handler methods -----------------------------------------
	//#CM35316
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35317
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35318
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Set_Click(ActionEvent e) throws Exception
	{
	}

	//#CM35319
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35320
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

	//#CM35321
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheck_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35322
	/** 
	 * check all the contents of list cell<BR>
	 * and set cursor in worker code<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_AllCheck_Click(ActionEvent e) throws Exception
	{
		//#CM35323
		// set the focus in worker code input
		setFocus(txt_WorkerCode);
		
		for(int i = 1; i < lst_AsrsForcedDown.getMaxRows(); i++)
		{  			
			lst_AsrsForcedDown.setCurrentRow(i);
			lst_AsrsForcedDown.setChecked( 1, true );
		} 
	}

	//#CM35324
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheckClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35325
	/** 
	 * remove the check from listcell content checkbox<BR>
	 * and set cursor in worker code<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_AllCheckClear_Click(ActionEvent e) throws Exception
	{
		//#CM35326
		// set the focus in worker code input
		setFocus(txt_WorkerCode);
		
		for(int i = 1; i < lst_AsrsForcedDown.getMaxRows(); i++)
		{  			
			lst_AsrsForcedDown.setCurrentRow(i);
			lst_AsrsForcedDown.setChecked( 1, false );
		} 
	}

	//#CM35327
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsForcedDown_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM35328
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsForcedDown_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM35329
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsForcedDown_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM35330
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsForcedDown_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM35331
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsForcedDown_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35332
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsForcedDown_Change(ActionEvent e) throws Exception
	{
	}

	//#CM35333
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsForcedDown_Click(ActionEvent e) throws Exception
	{
	}

	//#CM35334
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Setting_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35335
	/** 
	 * called on clicking "Set" button<BR>
	 * <BR>
	 * Abstract : Acquire the set object data, and do the forced ending processing. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.set the cursor in worker code<BR>
	 * 		2.fetch target data for setting<BR>
	 * 		3.start scheduler<BR>
	 * 		<DIR>
	 * 			[parameter] *mandatory items<BR>
	 * 			<DIR>
	 * 				worker code *<BR>
	 * 				password *<BR>
	 * 				Selection mode(Single forced termination)*<BR>
	 * 				list cell.AGC No.<BR>
	 * 				terminal no.<BR>
	 * 				list cell line no.<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * 		4.refresh list cell
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Setting_Click(ActionEvent e) throws Exception
	{
		//#CM35336
		// set the focus in worker code input
		setFocus(txt_WorkerCode);

		//#CM35337
		//fetch max. line no
		int index = lst_AsrsForcedDown.getMaxRows();

		Connection conn = null;
		try
		{
			//#CM35338
			// input check
			txt_WorkerCode.validate();
			txt_Password.validate();
			
			//#CM35339
			//save the AGC No. against selected checkbox
			List chkList = new ArrayList();
			Vector vecParam = new Vector();
			for (int i = 1; i < index; i++)
			{
				//#CM35340
				// specify line
				lst_AsrsForcedDown.setCurrentRow(i);

				//#CM35341
				// move to next info if selected
				if(!lst_AsrsForcedDown.getChecked(1) )		continue;
					
				AsScheduleParameter param = new AsScheduleParameter();
				
				//#CM35342
				// worker code
				param.setWorkerCode(txt_WorkerCode.getText());
				//#CM35343
				// password
				param.setPassword(txt_Password.getText());

				//#CM35344
				// The single work end flag is set. 
				param.setSelectMode(AsScheduleParameter.ASRS_WORKING_ONLYEND);
				//#CM35345
				//AGC No.
				param.setAgcNo(lst_AsrsForcedDown.getValue(2));
				chkList.add(lst_AsrsForcedDown.getValue(2));

				//#CM35346
				// terminal no.
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());

				//#CM35347
				// save the line no.
				param.setRowNo(i);

				vecParam.addElement(param);
			}
			if (vecParam.size() <= 0)
			{
				//#CM35348
				// 6023154 = modification target data does not exist
				message.setMsgResourceKey("6023154");
				return;
			}

			AsScheduleParameter[] paramArray = new AsScheduleParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			WmsScheduler schedule = new AsWorkOperationSCH();
			//#CM35349
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM35350
			// start schedule
			AsScheduleParameter[] viewParam =
				(AsScheduleParameter[]) schedule.startSCHgetParams(conn, paramArray);

			if (viewParam == null)
			{
				conn.rollback();
				//#CM35351
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.commit();

				//#CM35352
				// set listcell
				setList(conn, chkList);

				//#CM35353
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
				//#CM35354
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

	//#CM35355
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Reflesh_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35356
	/** 
	 * call when the refresh button is pressed<BR>
	 * <BR>
	 * Abstract :refresh list cell<BR>
	 * and set the cursor in worker code<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Reflesh_Click(ActionEvent e) throws Exception
	{
		//#CM35357
		// set the focus in worker code input
		setFocus(txt_WorkerCode);
		
		Connection conn = null;
		try
		{
			//#CM35358
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM35359
			//display data
			setList(conn, null);
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM35360
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM35361
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_RemainWork_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35362
	/** 
	 * It is called when the remainder work list button is pressed.<BR>
	 * <BR>
	 * overview :: Display the remainder work list box. <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_RemainWork_Click(ActionEvent e) throws Exception
	{
		//#CM35363
		// Set the search condition in the remainder work list display. 
		ForwardParameters param = new ForwardParameters();
		
		//#CM35364
		// The remainder work list display
		redirect("/asrs/listbox/listasrsworknobtn/ListAsWorkNoBtn.do", param, "/progress.do");
	}

	//#CM35365
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Worker_Code_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35366
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35367
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM35368
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM35369
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM35370
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35371
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35372
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM35373
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM35374
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}


}
//#CM35375
//end of class
