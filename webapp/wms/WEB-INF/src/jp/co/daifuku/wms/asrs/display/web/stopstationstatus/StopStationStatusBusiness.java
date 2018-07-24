// $Id: StopStationStatusBusiness.java,v 1.2 2006/10/26 08:08:23 suresh Exp $

//#CM41530
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.stopstationstatus;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.asrs.schedule.StationStatusSCH;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.entity.Station;

//#CM41531
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * ASRS station suspend setting screen class<BR>
 * set the input contents from the screen to a parameter
 * use the parameter to change the station status to suspend<BR>
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
 * 		job type(suspend)*<BR>
 * 		list cell.station no.<BR>
 * 		terminal no.<BR>
 * 		list cell line no.<BR>
 *  </DIR>
 *  [data for output use] <BR>
 *  <BR>
 * 	<DIR>
 *      select check box <BR>
 *      station no. <BR>
 *  	station name <BR>
 *      operation mode name <BR>
 *      work mode name <BR>
 *      machine status name <BR>
 *      work status <BR>
 *      work qty <BR>
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
 * 		job type:suspend* <BR>
 *  </DIR>
 *  <BR>
 *  [data for output use] <BR>
 *  <BR>
 * 	<DIR>
 *      select check box <BR>
 *      station no. <BR>
 *  	station name <BR>
 *      operation mode name <BR>
 *      work mode name <BR>
 *      machine status name <BR>
 *      work status <BR>
 *      work qty <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/9/29</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:08:23 $
 * @author  $Author: suresh $
 */
public class StopStationStatusBusiness extends StopStationStatus implements WMSConstants
{
	//#CM41532
	// Class fields --------------------------------------------------

	//#CM41533
	// Class variables -----------------------------------------------

	//#CM41534
	// Class method --------------------------------------------------

	//#CM41535
	// Constructors --------------------------------------------------

	//#CM41536
	// Public methods ------------------------------------------------

	//#CM41537
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
		//#CM41538
		// set initial focus to worker code
		setFocus(txt_WorkerCode);
		
		//#CM41539
		// combine refresh process
		btn_Reflesh_Click(null);
	}

	//#CM41540
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
			//#CM41541
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);

			//#CM41542
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);

			//#CM41543
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}
		//#CM41544
		// confirmation dialog message MSG-9000= Do you set?
		btn_Setting.setBeforeConfirm("MSG-9000");
	}

	//#CM41545
	// Package methods -----------------------------------------------

	//#CM41546
	// Protected methods ---------------------------------------------

	//#CM41547
	// Private methods -----------------------------------------------

	//#CM41548
	/** 
	 * Set data to List cell.<BR>
	 * Moreover, ToolTip is edited.<BR>
	 * <DIR>
	 * 		[List cell row number list]
	 * 		<DIR>
	 * 			1.Selection check box <BR>
	 * 			2.Station No. <BR>
	 * 			3.Station Name <BR>
	 * 			4.Operation Mode <BR>
	 * 			5.Work Mode <BR>
	 * 			6.Machine Status <BR>
	 * 			7.Work Status <BR>
	 * 			8.Work Vol. <BR>
	 *	 	</DIR>
	 * </DIR>
	 * @param viewParam Display data.
	 * @throws Exception It reports on all exceptions.
	  */
	private void setList(AsScheduleParameter[] viewParam) throws Exception
	{

		//#CM41549
		// call display area clear process
		lst_StopStationStatus.clearRow();

		if (viewParam == null || viewParam.length == 0)
		{
			return;
		}

		//#CM41550
		// used in tool tip
		String title_Station = DisplayText.getText("LBL-W0523");
		String title_StationName = DisplayText.getText("LBL-W0524");
		String title_ModeTypeName = DisplayText.getText("LBL-W0526");
		String title_CurrentModeName = DisplayText.getText("LBL-W0527");
		String title_StatusName = DisplayText.getText("LBL-W0525");
		String title_SuspendName = DisplayText.getText("LBL-W0281");
		String title_WorkingCount = DisplayText.getText("LBL-W0271");

		for (int lc=0; lc< viewParam.length; lc++)
		{
			//#CM41551
			// add row
			lst_StopStationStatus.setCurrentRow(lc + 1);
			lst_StopStationStatus.addRow();

			if (viewParam[lc].getSelectMode().equals("0"))
			{
				lst_StopStationStatus.setChecked(1, false);
			}
			else
			{
				lst_StopStationStatus.setChecked(1, true);
			}
			lst_StopStationStatus.setValue(2, viewParam[lc].getStationNo());
			lst_StopStationStatus.setValue(3, viewParam[lc].getDispStationName());
			lst_StopStationStatus.setValue(4, viewParam[lc].getDispModeTypeName());
			lst_StopStationStatus.setValue(5, viewParam[lc].getDispCurrentModeName());
			lst_StopStationStatus.setValue(6, viewParam[lc].getDispControllerStatusName());
			lst_StopStationStatus.setValue(7, viewParam[lc].getDispWorkingStatusName());
			lst_StopStationStatus.setValue(8, Formatter.getNumFormat(viewParam[lc].getWorkingCount()));
				
			//#CM41552
			// add data to tool tip
			ToolTipHelper toolTip = new ToolTipHelper();
			toolTip.add(title_Station, viewParam[lc].getStationNo());
			toolTip.add(title_StationName, viewParam[lc].getDispStationName());
			toolTip.add(title_ModeTypeName, viewParam[lc].getDispModeTypeName());
			toolTip.add(title_CurrentModeName, viewParam[lc].getDispCurrentModeName());
			toolTip.add(title_StatusName, viewParam[lc].getDispControllerStatusName());
			toolTip.add(title_SuspendName, viewParam[lc].getDispWorkingStatusName());
			toolTip.add(title_WorkingCount, Formatter.getNumFormat(viewParam[lc].getWorkingCount()));

			//#CM41553
			// set tool tip	
			lst_StopStationStatus.setToolTip(lc+1, toolTip.getText());

		}
	}

	//#CM41554
	// Event handler methods -----------------------------------------
	//#CM41555
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41556
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41557
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Set_Click(ActionEvent e) throws Exception
	{
	}

	//#CM41558
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41559
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

	//#CM41560
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheck_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41561
	/**
	 * This is called when "Select All" button is clicked <BR>
	 * <BR>
	 * overview ::check all the contents of list cell<BR>
	 * and set cursor in worker code<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_AllCheck_Click(ActionEvent e) throws Exception
	{
		//#CM41562
		// set initial focus to worker code
		setFocus(txt_WorkerCode);
		
		for(int lc=1; lc< lst_StopStationStatus.getMaxRows(); lc++)
		{  			
			lst_StopStationStatus.setCurrentRow(lc);
			lst_StopStationStatus.setChecked( 1, true );
		} 
	}

	//#CM41563
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheckClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41564
	/** 
	 * This is called when "All cancel" button is clicked <BR>
	 * <BR>
	 * overview ::remove the check from listcell content checkbox<BR>
	 * and set cursor in worker code<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_AllCheckClear_Click(ActionEvent e) throws Exception
	{
		//#CM41565
		// set initial focus to worker code
		setFocus(txt_WorkerCode);
		
		for(int lc=1; lc< lst_StopStationStatus.getMaxRows(); lc++)
		{  			
			lst_StopStationStatus.setCurrentRow(lc);
			lst_StopStationStatus.setChecked( 1, false );
		} 
	}

	//#CM41566
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StopStationStatus_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM41567
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StopStationStatus_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM41568
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StopStationStatus_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM41569
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StopStationStatus_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM41570
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StopStationStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41571
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StopStationStatus_Change(ActionEvent e) throws Exception
	{
	}

	//#CM41572
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StopStationStatus_Click(ActionEvent e) throws Exception
	{
	}

	//#CM41573
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Setting_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41574
	/** 
	 * called on clicking "Set" button<BR>
	 * <BR>
	 * Abstract :fetch target data and call suspend status change process<BR>
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
	 * 				job type(suspend)*<BR>
	 * 				list cell.station no.<BR>
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
		//#CM41575
		// set initial focus to worker code
		setFocus(txt_WorkerCode);

		//#CM41576
		//fetch max. line no
		int index = lst_StopStationStatus.getMaxRows();

		Connection conn = null;
		try
		{
			//#CM41577
			// input check
			txt_WorkerCode.validate();
			txt_Password.validate();
			
			//#CM41578
			//save the AGC No. against selected checkbox
			Vector vecParam = new Vector();
			for (int lc=1; lc<index; lc++)
			{
				//#CM41579
				// specify line
				lst_StopStationStatus.setCurrentRow(lc);

				//#CM41580
				// move to next info if selected
				if(!lst_StopStationStatus.getChecked(1) )		continue;
					
				AsScheduleParameter param = new AsScheduleParameter();
				
				//#CM41581
				// worker code
				param.setWorkerCode(txt_WorkerCode.getText());
				//#CM41582
				// password
				param.setPassword(txt_Password.getText());
				
				//#CM41583
				// set process flag in suspend screen
				param.setProcessStatus(Integer.toString(Station.SUSPENDING));

				//#CM41584
				// station no.
				param.setStationNo(lst_StopStationStatus.getValue(2));

				//#CM41585
				// terminal no.
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());

				//#CM41586
				// save the line no.
				param.setRowNo(lc);

				vecParam.addElement(param);
			}
			if (vecParam.size() <= 0)
			{
				//#CM41587
				// 6023154 = modification target data does not exist
				message.setMsgResourceKey("6023154");
				return;
			}

			AsScheduleParameter[] paramArray = new AsScheduleParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			WmsScheduler schedule = new StationStatusSCH();
			//#CM41588
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM41589
			// start schedule
			AsScheduleParameter[] viewParam =
				(AsScheduleParameter[]) schedule.startSCHgetParams(conn, paramArray);

			if (viewParam == null)
			{
				conn.rollback();
				//#CM41590
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.commit();

				//#CM41591
				// set listcell
				setList(viewParam);

				//#CM41592
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
				//#CM41593
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

	//#CM41594
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Reflesh_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41595
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
		//#CM41596
		// set the focus in worker code input
		setFocus(txt_WorkerCode);
		
		Connection conn = null;
		try
		{
			//#CM41597
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
		
			//#CM41598
			// set schedule parameter
			AsScheduleParameter param = new AsScheduleParameter();
			//#CM41599
			// set process flag in suspend screen
			param.setProcessStatus(Integer.toString(Station.SUSPENDING));
		
			WmsScheduler schedule = new StationStatusSCH();

			AsScheduleParameter[] viewParam = (AsScheduleParameter[]) schedule.query(conn, param);

			//#CM41600
			//display data
			setList(viewParam);
		
			//#CM41601
			// set message
			message.setMsgResourceKey(schedule.getMessage());
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM41602
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM41603
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Worker_Code_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41604
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41605
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM41606
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM41607
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM41608
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41609
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41610
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM41611
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM41612
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}


}
//#CM41613
//end of class
