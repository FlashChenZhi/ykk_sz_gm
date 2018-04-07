// $Id: StartStationStatusBusiness.java,v 1.2 2006/10/26 08:05:27 suresh Exp $

//#CM41198
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.startstationstatus;
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

//#CM41199
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * ASRS station operation setting screen class<BR>
 * set the input contents from the screen to a parameter
 * using this parameter, change the target station status to enable<BR>
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
 * 		job type(operation)*<BR>
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
 * 		job type:operation* <BR>
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
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:05:27 $
 * @author  $Author: suresh $
 */
public class StartStationStatusBusiness extends StartStationStatus implements WMSConstants
{
	//#CM41200
	// Class fields --------------------------------------------------

	//#CM41201
	// Class variables -----------------------------------------------

	//#CM41202
	// Class method --------------------------------------------------

	//#CM41203
	// Constructors --------------------------------------------------

	//#CM41204
	// Public methods ------------------------------------------------

	//#CM41205
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
		//#CM41206
		// set initial focus to worker code
		setFocus(txt_WorkerCode);
		
		//#CM41207
		// combine refresh process
		btn_Reflesh_Click(null);
	}

	//#CM41208
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
			//#CM41209
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);

			//#CM41210
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);

			//#CM41211
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}
		//#CM41212
		// confirmation dialog message MSG-9000= Do you set?
		btn_Setting.setBeforeConfirm("MSG-9000");
	}

	//#CM41213
	// Package methods -----------------------------------------------

	//#CM41214
	// Protected methods ---------------------------------------------

	//#CM41215
	// Private methods -----------------------------------------------

	//#CM41216
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

		//#CM41217
		// call display area clear process
		lst_StartStationStatus.clearRow();

		if (viewParam == null || viewParam.length == 0)
		{
			return;
		}

		//#CM41218
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
			//#CM41219
			// add row
			lst_StartStationStatus.setCurrentRow(lc + 1);
			lst_StartStationStatus.addRow();

			if (viewParam[lc].getSelectMode().equals("0"))
			{
				lst_StartStationStatus.setChecked(1, false);
			}
			else
			{
				lst_StartStationStatus.setChecked(1, true);
			}
			lst_StartStationStatus.setValue(2, viewParam[lc].getStationNo());
			lst_StartStationStatus.setValue(3, viewParam[lc].getDispStationName());
			lst_StartStationStatus.setValue(4, viewParam[lc].getDispModeTypeName());
			lst_StartStationStatus.setValue(5, viewParam[lc].getDispCurrentModeName());
			lst_StartStationStatus.setValue(6, viewParam[lc].getDispControllerStatusName());
			lst_StartStationStatus.setValue(7, viewParam[lc].getDispWorkingStatusName());
			lst_StartStationStatus.setValue(8, Formatter.getNumFormat(viewParam[lc].getWorkingCount()));
				
			//#CM41220
			// add data to tool tip
			ToolTipHelper toolTip = new ToolTipHelper();
			toolTip.add(title_Station, viewParam[lc].getStationNo());
			toolTip.add(title_StationName, viewParam[lc].getDispStationName());
			toolTip.add(title_ModeTypeName, viewParam[lc].getDispModeTypeName());
			toolTip.add(title_CurrentModeName, viewParam[lc].getDispCurrentModeName());
			toolTip.add(title_StatusName, viewParam[lc].getDispControllerStatusName());
			toolTip.add(title_SuspendName, viewParam[lc].getDispWorkingStatusName());
			toolTip.add(title_WorkingCount, Formatter.getNumFormat(viewParam[lc].getWorkingCount()));

			//#CM41221
			// set tool tip	
			lst_StartStationStatus.setToolTip(lc+1, toolTip.getText());

		}
	}

	//#CM41222
	// Event handler methods -----------------------------------------
	//#CM41223
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41224
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41225
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Set_Click(ActionEvent e) throws Exception
	{
	}

	//#CM41226
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41227
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

	//#CM41228
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheck_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41229
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
		//#CM41230
		// set initial focus to worker code
		setFocus(txt_WorkerCode);
		
		for(int lc=1; lc< lst_StartStationStatus.getMaxRows(); lc++)
		{  			
			lst_StartStationStatus.setCurrentRow(lc);
			lst_StartStationStatus.setChecked( 1, true );
		} 
	}

	//#CM41231
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheckClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41232
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
		//#CM41233
		// set initial focus to worker code
		setFocus(txt_WorkerCode);
		
		for(int lc=1; lc< lst_StartStationStatus.getMaxRows(); lc++)
		{  			
			lst_StartStationStatus.setCurrentRow(lc);
			lst_StartStationStatus.setChecked( 1, false );
		} 
	}

	//#CM41234
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StartStationStatus_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM41235
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StartStationStatus_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM41236
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StartStationStatus_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM41237
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StartStationStatus_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM41238
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StartStationStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41239
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StartStationStatus_Change(ActionEvent e) throws Exception
	{
	}

	//#CM41240
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StartStationStatus_Click(ActionEvent e) throws Exception
	{
	}

	//#CM41241
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Setting_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41242
	/** 
	 * called on clicking "Set" button<BR>
	 * <BR>
	 * Abstract :fetch the set target data and change the status to enable<BR>
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
	 * 				job type(operation)*<BR>
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
		//#CM41243
		// set initial focus to worker code
		setFocus(txt_WorkerCode);

		//#CM41244
		//fetch max. line no
		int index = lst_StartStationStatus.getMaxRows();

		Connection conn = null;
		try
		{
			//#CM41245
			// input check
			txt_WorkerCode.validate();
			txt_Password.validate();
			
			//#CM41246
			//save the AGC No. against selected checkbox
			Vector vecParam = new Vector();
			for (int lc=1; lc<index; lc++)
			{
				//#CM41247
				// specify line
				lst_StartStationStatus.setCurrentRow(lc);

				//#CM41248
				// move to next info if selected
				if(!lst_StartStationStatus.getChecked(1) )		continue;
					
				AsScheduleParameter param = new AsScheduleParameter();
				
				//#CM41249
				// worker code
				param.setWorkerCode(txt_WorkerCode.getText());
				//#CM41250
				// password
				param.setPassword(txt_Password.getText());
				
				//#CM41251
				// set process flag in operation screen
				param.setProcessStatus(Integer.toString(Station.NOT_SUSPEND));

				//#CM41252
				// station no.
				param.setStationNo(lst_StartStationStatus.getValue(2));

				//#CM41253
				// terminal no.
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());

				//#CM41254
				// save the line no.
				param.setRowNo(lc);

				vecParam.addElement(param);
			}
			if (vecParam.size() <= 0)
			{
				//#CM41255
				// 6023154 = modification target data does not exist
				message.setMsgResourceKey("6023154");
				return;
			}

			AsScheduleParameter[] paramArray = new AsScheduleParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			WmsScheduler schedule = new StationStatusSCH();
			//#CM41256
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM41257
			// start schedule
			AsScheduleParameter[] viewParam =
				(AsScheduleParameter[]) schedule.startSCHgetParams(conn, paramArray);

			if (viewParam == null)
			{
				conn.rollback();
				//#CM41258
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.commit();

				//#CM41259
				// set listcell
				setList(viewParam);

				//#CM41260
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
				//#CM41261
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

	//#CM41262
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Reflesh_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41263
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
		//#CM41264
		// set initial focus to worker code
		setFocus(txt_WorkerCode);
		
		Connection conn = null;
		try
		{
			//#CM41265
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
		
			//#CM41266
			// set schedule parameter
			AsScheduleParameter param = new AsScheduleParameter();
			//#CM41267
			// set process flag in operation screen
			param.setProcessStatus(Integer.toString(Station.NOT_SUSPEND));
		
			WmsScheduler schedule = new StationStatusSCH();

			AsScheduleParameter[] viewParam = (AsScheduleParameter[]) schedule.query(conn, param);

			//#CM41268
			//display data
			setList(viewParam);
		
			//#CM41269
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
				//#CM41270
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM41271
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Worker_Code_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41272
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41273
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM41274
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM41275
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM41276
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41277
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41278
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM41279
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM41280
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}


}
//#CM41281
//end of class
