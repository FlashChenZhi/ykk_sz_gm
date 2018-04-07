// $Id: AsWorkStartBusiness.java,v 1.2 2006/10/26 05:15:53 suresh Exp $

//#CM37980
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsworkstart;
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

//#CM37981
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * The ASRSStart work class. <BR>
 * set the input contents from the screen to a parameter
 * The schedule processes ASRSStart work based on the parameter. <BR>
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
 *  The content input from input area is set in the parameter, and the schedule processes ASRSStart work based on the parameter. <BR>
 *  Receive true when the result is received from the schedule, and processing is completed normally. <BR>
 *  Receive false when it does not complete the schedule because of the condition error etc.<BR>
 *  output the schedule result, schedule message to screen<BR>
 * 	<DIR>
 *   [parameter] *mandatory input <BR>
 *   <BR>
 * 		worker code *<BR>
 * 		password *<BR>
 * 		select mode (start)*<BR>
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
 * 	<DIR>
 *   [parameter] *mandatory input <BR>
 *   <BR>
 * 		classification:Start work* <BR>
 *  </DIR>
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
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:15:53 $
 * @author  $Author: suresh $
 */
public class AsWorkStartBusiness extends AsWorkStart implements WMSConstants
{
	//#CM37982
	// Class fields --------------------------------------------------

	//#CM37983
	// Class variables -----------------------------------------------

	//#CM37984
	// Class method --------------------------------------------------

	//#CM37985
	// Constructors --------------------------------------------------

	//#CM37986
	// Public methods ------------------------------------------------

	//#CM37987
	/**
	 * screen initialization<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * <BR>
	 * <DIR>
	 * 		1.set the cursor in worker code<BR>
	 * 		2.display list cell<BR>
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
		//#CM37988
		// set initial focus to worker code
		setFocus(txt_WorkerCode);
		
		Connection conn = null;
		try
		{
			//#CM37989
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM37990
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
				//#CM37991
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM37992
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
			//#CM37993
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM37994
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM37995
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}
		//#CM37996
		// "start work?" message is displayed on clicking "Setting" button
		btn_Setting.setBeforeConfirm("MSG-W0063");
	}
	
	//#CM37997
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
		//#CM37998
		// set the focus in worker code input
		setFocus(txt_WorkerCode);
	}

	//#CM37999
	// Package methods -----------------------------------------------

	//#CM38000
	// Protected methods ---------------------------------------------

	//#CM38001
	// Private methods -----------------------------------------------
	//#CM38002
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
		//#CM38003
		// eliminate all lines
		lst_AsrsWorkStart.clearRow();

		//#CM38004
		// set schedule parameter
		AsScheduleParameter param = new AsScheduleParameter();
		//#CM38005
		// set workstart flag
		param.setSelectMode(AsScheduleParameter.ASRS_WORKING_START);
		
		WmsScheduler schedule = new AsWorkOperationSCH();

		AsScheduleParameter[] viewParam = (AsScheduleParameter[]) schedule.query(conn, param);

		if (viewParam == null || viewParam.length == 0)
		{
			//#CM38006
			// set message
			message.setMsgResourceKey(schedule.getMessage());

			return;
		}

		//#CM38007
		// set message
		message.setMsgResourceKey(schedule.getMessage());
		//#CM38008
		//eliminate all lines
		lst_AsrsWorkStart.clearRow();

		for(int i = 0; i < viewParam.length; i++)
		{
			AsScheduleParameter dispData = viewParam[i];
			String statusname = dispData.getSystemStatus();
			int count = lst_AsrsWorkStart.getMaxRows();
			lst_AsrsWorkStart.setCurrentRow(count);
			lst_AsrsWorkStart.addRow();

			//#CM38009
			//Check the marked data.
			lst_AsrsWorkStart.setValue(0, dispData.getSelectCurrentMode());
			if(selectlist == null)
			{
				if(dispData.getSelectCurrentMode().equals("1"))
				{
					lst_AsrsWorkStart.setChecked(1,true);
				}
			}
			else
			{
				if(selectlist.contains(dispData.getAgcNo()))
				{
					lst_AsrsWorkStart.setChecked(1,true);
				}
			}

			lst_AsrsWorkStart.setValue(2, dispData.getAgcNo());
			lst_AsrsWorkStart.setValue(3, statusname);
			lst_AsrsWorkStart.setValue(4, Integer.toString(dispData.getWorkingCount()));
		}
		//#CM38010
		// set message
		message.setMsgResourceKey(schedule.getMessage());
	}

	//#CM38011
	// Event handler methods -----------------------------------------
	//#CM38012
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38013
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38014
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Set_Click(ActionEvent e) throws Exception
	{
	}

	//#CM38015
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38016
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

	//#CM38017
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheck_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38018
	/** 
	 * check all the contents of list cell<BR>
	 * and set cursor in worker code<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_AllCheck_Click(ActionEvent e) throws Exception
	{
		//#CM38019
		// set the focus in worker code input
		setFocus(txt_WorkerCode);

		for(int i = 1; i < lst_AsrsWorkStart.getMaxRows(); i++)
		{  			
			lst_AsrsWorkStart.setCurrentRow(i);
			lst_AsrsWorkStart.setChecked( 1, true );
		} 
	}

	//#CM38020
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheckClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38021
	/** 
	 * remove the check from listcell content checkbox<BR>
	 * and set cursor in worker code<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_AllCheckClear_Click(ActionEvent e) throws Exception
	{
		//#CM38022
		// set the focus in worker code input
		setFocus(txt_WorkerCode);

		for(int i = 1; i < lst_AsrsWorkStart.getMaxRows(); i++)
		{  			
			lst_AsrsWorkStart.setCurrentRow(i);
			lst_AsrsWorkStart.setChecked( 1, false );
		} 
	}

	//#CM38023
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsWorkStart_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM38024
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsWorkStart_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM38025
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsWorkStart_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM38026
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsWorkStart_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM38027
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsWorkStart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38028
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsWorkStart_Change(ActionEvent e) throws Exception
	{
	}

	//#CM38029
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsWorkStart_Click(ActionEvent e) throws Exception
	{
	}

	//#CM38030
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Setting_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38031
	/** 
	 * called on clicking "Set" button<BR>
	 * <BR>
	 * Abstract :Acquire the set object data, and do the termination. <BR>
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
	 * 				select mode (start)*<BR>
	 * 				list cell.AGC No.<BR>
	 * 				terminal no.<BR>
	 * 				list cell line no.<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * 		4.refresh list cell<BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Setting_Click(ActionEvent e) throws Exception
	{
		//#CM38032
		// set the focus in worker code input
		setFocus(txt_WorkerCode);

		//#CM38033
		//fetch max. line no
		int index = lst_AsrsWorkStart.getMaxRows();

		Connection conn = null;
		try
		{
			//#CM38034
			// input check
			txt_WorkerCode.validate();
			txt_Password.validate();
			
			//#CM38035
			//save the AGC No. against selected checkbox
			List chkList = new ArrayList();
			Vector vecParam = new Vector();
			for (int i = 1; i < index; i++)
			{
				//#CM38036
				// specify line
				lst_AsrsWorkStart.setCurrentRow(i);

				//#CM38037
				// move to next info if selected
				if(!lst_AsrsWorkStart.getChecked(1) )		continue;
					
				AsScheduleParameter param = new AsScheduleParameter();
				
				//#CM38038
				// worker code
				param.setWorkerCode(txt_WorkerCode.getText());
				//#CM38039
				// password
				param.setPassword(txt_Password.getText());
				
				//#CM38040
				// set workstart flag
				param.setSelectMode(AsScheduleParameter.ASRS_WORKING_START);
				//#CM38041
				//AGC No.
				param.setAgcNo(lst_AsrsWorkStart.getValue(2));
				chkList.add(lst_AsrsWorkStart.getValue(2));

				//#CM38042
				// terminal no.
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());

				//#CM38043
				// save the line no.
				param.setRowNo(i);

				vecParam.addElement(param);
			}
			if (vecParam.size() <= 0)
			{
				//#CM38044
				// 6023154 = modification target data does not exist
				message.setMsgResourceKey("6023154");
				return;
			}

			AsScheduleParameter[] paramArray = new AsScheduleParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			WmsScheduler schedule = new AsWorkOperationSCH();
			//#CM38045
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM38046
			// start schedule
			AsScheduleParameter[] viewParam =
				(AsScheduleParameter[]) schedule.startSCHgetParams(conn, paramArray);

			if (viewParam == null)
			{
				conn.rollback();
				//#CM38047
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.commit();

				//#CM38048
				// set listcell
				setList(conn, chkList);

				//#CM38049
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
				//#CM38050
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

	//#CM38051
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Reflesh_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38052
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
		//#CM38053
		// set the focus in worker code input
		setFocus(txt_WorkerCode);

		Connection conn = null;
		try
		{
			//#CM38054
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM38055
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
				//#CM38056
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM38057
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_RemainWork_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38058
	/** 
	 * It is called when the remainder work list button is pressed.<BR>
	 * <BR>
	 * overview ::Display the remainder work list box. <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_RemainWork_Click(ActionEvent e) throws Exception
	{
		//#CM38059
		// Set the search condition in the remainder work list display. 
		ForwardParameters param = new ForwardParameters();
		
		//#CM38060
		// The remainder work list display
		redirect("/asrs/listbox/listasrsworknobtn/ListAsWorkNoBtn.do", param, "/progress.do");
	}


	//#CM38061
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Worker_Code_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38062
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38063
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM38064
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM38065
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM38066
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38067
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM38068
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM38069
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM38070
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}


}
//#CM38071
//end of class
