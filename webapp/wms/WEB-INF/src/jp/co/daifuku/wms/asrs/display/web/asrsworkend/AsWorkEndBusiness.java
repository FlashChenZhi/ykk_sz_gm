 //#CM37679
 // $Id: AsWorkEndBusiness.java,v 1.2 2006/10/26 04:58:41 suresh Exp $

//#CM37680
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsworkend;
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

//#CM37681
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * The ASRS work end class. <BR>
 * set the input contents from the screen to a parameter
 * The schedule does ASRS work end Process based on the parameter. <BR>
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
 *  Receive true when the result is received from the schedule, and processing is completed normally. <BR>
 *  Receive false when it does not complete the schedule because of the condition error etc.<BR>
 *  output the schedule result, schedule message to screen<BR>
 * 	<DIR>
 *   [parameter] *mandatory input <BR>
 *   <BR>
 * 		worker code*<BR>
 * 		password *<BR>
 * 		select mode *<BR>
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
 * 		classification:End or usual data maintenance* <BR>
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
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:58:41 $
 * @author  $Author: suresh $
 */
public class AsWorkEndBusiness extends AsWorkEnd implements WMSConstants
{
	//#CM37682
	// Class fields --------------------------------------------------

	//#CM37683
	// Class variables -----------------------------------------------

	//#CM37684
	// Class method --------------------------------------------------

	//#CM37685
	// Constructors --------------------------------------------------

	//#CM37686
	// Public methods ------------------------------------------------

	//#CM37687
	/**
	 * screen initialization<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * <BR>
	 * <DIR>
	 * 		1.set the cursor in worker code<BR>
	 * 		2.initialize input area<BR>
	 * 		3.display list cell<BR>
	 * 		<BR>
	 * 		item[initial value] <BR>
	 * 		<DIR>
	 * 			worker code[nil] <BR>
	 * 			password[nil] <BR>
	 * 			Normal Radio button[true]<BR>
	 * 			Data maintenance radio button[false]<BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM37688
		// set initial focus to worker code
		setFocus(txt_WorkerCode);
		
		Connection conn = null;
		try
		{
			//#CM37689
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM37690
			// Make the ordinary mode to ON status. 
			rdo_WorkingEndUsual.setChecked(true);
			rdo_WorkingEnd_Date.setChecked(false);
			//#CM37691
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
				//#CM37692
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM37693
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
			//#CM37694
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM37695
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM37696
			//set screen name
			lbl_SettingName.setResourceKey(title);

		}
		//#CM37697
		// Confirmation dialog when Submit button is pressed "Want to finish the work?"
		btn_Setting.setBeforeConfirm("MSG-W0064");
	}
	
	//#CM37698
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
		//#CM37699
		// set the focus in worker code input
		setFocus(txt_WorkerCode);
	}

	//#CM37700
	// Package methods -----------------------------------------------

	//#CM37701
	// Protected methods ---------------------------------------------

	//#CM37702
	// Private methods -----------------------------------------------
	//#CM37703
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
		//#CM37704
		// eliminate all lines
		lst_AsrsWorkEnd.clearRow();

		//#CM37705
		// set schedule parameter
		AsScheduleParameter param = new AsScheduleParameter();

		if (rdo_WorkingEndUsual.getChecked())
		{
			//#CM37706
			// The work end is usually set in division. 
			param.setSelectMode(AsScheduleParameter.ASRS_WORKING_END);
		}
		else if (rdo_WorkingEnd_Date.getChecked())
		{
			//#CM37707
			// The end of work flag : The data maintenance is set. 
			param.setSelectMode(AsScheduleParameter.ASRS_WORKING_END_DATAKEEP);
		}
		
		WmsScheduler schedule = new AsWorkOperationSCH();

		AsScheduleParameter[] viewParam = (AsScheduleParameter[]) schedule.query(conn, param);

		if (viewParam == null || viewParam.length == 0)
		{
			//#CM37708
			// set message
			message.setMsgResourceKey(schedule.getMessage());

			return;
		}

		//#CM37709
		// set message
		message.setMsgResourceKey(schedule.getMessage());
		//#CM37710
		//eliminate all lines
		lst_AsrsWorkEnd.clearRow();

		for(int i = 0; i < viewParam.length; i++)
		{
			AsScheduleParameter dispData = viewParam[i];
			String statusname = dispData.getSystemStatus();
			int count = lst_AsrsWorkEnd.getMaxRows();
			lst_AsrsWorkEnd.setCurrentRow(count);
			lst_AsrsWorkEnd.addRow();

			//#CM37711
			//Check the marked data.
			lst_AsrsWorkEnd.setValue(0, dispData.getSelectCurrentMode());
			if(selectlist == null)
			{
				if(dispData.getSelectCurrentMode().equals("1"))
				{
					lst_AsrsWorkEnd.setChecked(1,true);
				}
			}
			else
			{
				if(selectlist.contains(dispData.getAgcNo()))
				{
					lst_AsrsWorkEnd.setChecked(1,true);
				}
			}

			lst_AsrsWorkEnd.setValue(2, dispData.getAgcNo());
			lst_AsrsWorkEnd.setValue(3, statusname);
			lst_AsrsWorkEnd.setValue(4, Integer.toString(dispData.getWorkingCount()));
		}
		//#CM37712
		// set message
		message.setMsgResourceKey(schedule.getMessage());
	}

	//#CM37713
	// Event handler methods -----------------------------------------
	//#CM37714
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37715
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37716
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Set_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37717
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37718
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

	//#CM37719
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheck_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37720
	/** 
	 * check all the contents of list cell<BR>
	 * and set cursor in worker code<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_AllCheck_Click(ActionEvent e) throws Exception
	{
		//#CM37721
		// set the focus in worker code input
		setFocus(txt_WorkerCode);
		
		for(int i = 1; i < lst_AsrsWorkEnd.getMaxRows(); i++)
		{  			
			lst_AsrsWorkEnd.setCurrentRow(i);
			lst_AsrsWorkEnd.setChecked( 1, true );
		} 
	}

	//#CM37722
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_4_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37723
	/** 
	 * remove the check from listcell content checkbox<BR>
	 * and set cursor in worker code<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_4_Click(ActionEvent e) throws Exception
	{
		//#CM37724
		// set the focus in worker code input
		setFocus(txt_WorkerCode);
		
		for(int i = 1; i < lst_AsrsWorkEnd.getMaxRows(); i++)
		{  			
			lst_AsrsWorkEnd.setCurrentRow(i);
			lst_AsrsWorkEnd.setChecked( 1, false );
		} 
	}

	//#CM37725
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Mode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37726
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_WorkingEndUsual_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37727
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_WorkingEndUsual_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37728
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_WorkingEnd_Date_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37729
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_WorkingEnd_Date_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37730
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsWorkEnd_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37731
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsWorkEnd_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37732
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsWorkEnd_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM37733
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsWorkEnd_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM37734
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsWorkEnd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37735
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsWorkEnd_Change(ActionEvent e) throws Exception
	{
	}

	//#CM37736
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsrsWorkEnd_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37737
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Setting_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37738
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
	 * 				select mode *<BR>
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
		//#CM37739
		// set the focus in worker code input
		setFocus(txt_WorkerCode);

		//#CM37740
		//fetch max. line no
		int index = lst_AsrsWorkEnd.getMaxRows();

		Connection conn = null;
		try
		{
			//#CM37741
			// input check
			txt_WorkerCode.validate();
			txt_Password.validate();
			
			//#CM37742
			//save the AGC No. against selected checkbox
			List chkList = new ArrayList();
			Vector vecParam = new Vector();
			for (int i = 1; i < index; i++)
			{
				//#CM37743
				// specify line
				lst_AsrsWorkEnd.setCurrentRow(i);

				//#CM37744
				// move to next info if selected
				if(!lst_AsrsWorkEnd.getChecked(1) )		continue;
					
				AsScheduleParameter param = new AsScheduleParameter();
				
				//#CM37745
				// worker code
				param.setWorkerCode(txt_WorkerCode.getText());
				//#CM37746
				// password
				param.setPassword(txt_Password.getText());

				if (rdo_WorkingEndUsual.getChecked())
				{
					//#CM37747
					// The work end is usually set in division. 
					param.setSelectMode(AsScheduleParameter.ASRS_WORKING_END);
				}
				else if (rdo_WorkingEnd_Date.getChecked())
				{
					//#CM37748
					// The end of work flag : The data maintenance is set. 
					param.setSelectMode(AsScheduleParameter.ASRS_WORKING_END_DATAKEEP);
				}
				//#CM37749
				//AGC No.
				param.setAgcNo(lst_AsrsWorkEnd.getValue(2));
				chkList.add(lst_AsrsWorkEnd.getValue(2));

				//#CM37750
				// terminal no.
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());

				//#CM37751
				// save the line no.
				param.setRowNo(i);

				vecParam.addElement(param);
			}
			if (vecParam.size() <= 0)
			{
				//#CM37752
				// 6023154 = modification target data does not exist
				message.setMsgResourceKey("6023154");
				return;
			}

			AsScheduleParameter[] paramArray = new AsScheduleParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			WmsScheduler schedule = new AsWorkOperationSCH();
			//#CM37753
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM37754
			// start schedule
			AsScheduleParameter[] viewParam =
				(AsScheduleParameter[]) schedule.startSCHgetParams(conn, paramArray);

			if (viewParam == null)
			{
				conn.rollback();
				//#CM37755
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.commit();

				//#CM37756
				// set listcell
				setList(conn, chkList);

				//#CM37757
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
				//#CM37758
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

	//#CM37759
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Reflesh_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37760
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
		//#CM37761
		// set the focus in worker code input
		setFocus(txt_WorkerCode);
		
		Connection conn = null;
		try
		{
			//#CM37762
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM37763
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
				//#CM37764
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM37765
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_RemainWork_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37766
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
		//#CM37767
		// Set the search condition in the remainder work list display. 
		ForwardParameters param = new ForwardParameters();
		
		//#CM37768
		// The remainder work list display
		redirect("/asrs/listbox/listasrsworknobtn/ListAsWorkNoBtn.do", param, "/progress.do");
	}


	//#CM37769
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Worker_Code_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37770
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37771
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37772
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37773
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM37774
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37775
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37776
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37777
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37778
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}


}
//#CM37779
//end of class
