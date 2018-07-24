// $Id: StartStationStatusPopupBusiness.java,v 1.2 2006/10/26 08:06:22 suresh Exp $

//#CM41286
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.startstationstatuspopup;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.asrs.schedule.StationStatusSCH;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.entity.Station;

//#CM41287
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * ASRS station operation setting popup screen class<BR>
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
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:06:22 $
 * @author  $Author: suresh $
 */
public class StartStationStatusPopupBusiness extends StartStationStatusPopup implements WMSConstants
{
	//#CM41288
	// Class fields --------------------------------------------------
	//#CM41289
	/** 
	 * key used to transfer worker code
	 */
	public static final String WORKERCODE_KEY = "WORKERCODE_KEY";

	//#CM41290
	/** 
	 * key used to transfer password
	 */
	public static final String PASSWORD_KEY = "PASSWORD_KEY";

	//#CM41291
	// Class variables -----------------------------------------------

	//#CM41292
	// Class method --------------------------------------------------

	//#CM41293
	// Constructors --------------------------------------------------

	//#CM41294
	// Public methods ------------------------------------------------

	//#CM41295
	/**
	 * screen initialization<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * <BR>
	 * <DIR>
	 * 		1.set workcode, password in viewstate info<BR>
	 * 		2.display list cell<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM41296
		//save to viewstate
		this.getViewState().setString(WORKERCODE_KEY, request.getParameter(WORKERCODE_KEY));
		this.getViewState().setString(PASSWORD_KEY, request.getParameter(PASSWORD_KEY));
		
		//#CM41297
		// combine refresh process
		btn_Reflesh_Click(null);
	}

	//#CM41298
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
		//#CM41299
		// station operation setting
		lbl_ListName.setText(DisplayText.getText("TLE-W0919"));

		//#CM41300
		// confirmation dialog message MSG-9000= Do you set?
		btn_Setting.setBeforeConfirm("MSG-9000");
	}

	//#CM41301
	// Package methods -----------------------------------------------

	//#CM41302
	// Protected methods ---------------------------------------------

	//#CM41303
	// Private methods -----------------------------------------------

	//#CM41304
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
		//#CM41305
		// call display area clear process
		lst_StartStationStatus.clearRow();

		if (viewParam == null || viewParam.length == 0)
		{
			return;
		}

		//#CM41306
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
			//#CM41307
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
				
			//#CM41308
			// add data to tool tip
			ToolTipHelper toolTip = new ToolTipHelper();
			toolTip.add(title_Station, viewParam[lc].getStationNo());
			toolTip.add(title_StationName, viewParam[lc].getDispStationName());
			toolTip.add(title_ModeTypeName, viewParam[lc].getDispModeTypeName());
			toolTip.add(title_CurrentModeName, viewParam[lc].getDispCurrentModeName());
			toolTip.add(title_StatusName, viewParam[lc].getDispControllerStatusName());
			toolTip.add(title_SuspendName, viewParam[lc].getDispWorkingStatusName());
			toolTip.add(title_WorkingCount, Formatter.getNumFormat(viewParam[lc].getWorkingCount()));

			//#CM41309
			// set tool tip	
			lst_StartStationStatus.setToolTip(lc+1, toolTip.getText());
		}
	}

	//#CM41310
	// Event handler methods -----------------------------------------
	//#CM41311
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41312
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41313
	/** 
	 * call the [Close] button click event process <BR>
	 *  <BR>
	 * close this screen and move back to caller screen<BR>
	 *  <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		//#CM41314
		// return to origin screen
		parentRedirect(null);
	}

	//#CM41315
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheck_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41316
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
		for(int lc=1; lc< lst_StartStationStatus.getMaxRows(); lc++)
		{  			
			lst_StartStationStatus.setCurrentRow(lc);
			lst_StartStationStatus.setChecked( 1, true );
		}
		message.setMsgResourceKey("6001013");
	}

	//#CM41317
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheckClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41318
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
		for(int lc=1; lc< lst_StartStationStatus.getMaxRows(); lc++)
		{  			
			lst_StartStationStatus.setCurrentRow(lc);
			lst_StartStationStatus.setChecked( 1, false );
		} 
		message.setMsgResourceKey("6001013");
	}

	//#CM41319
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StartStationStatus_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM41320
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StartStationStatus_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM41321
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StartStationStatus_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM41322
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StartStationStatus_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM41323
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StartStationStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41324
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StartStationStatus_Change(ActionEvent e) throws Exception
	{
	}

	//#CM41325
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StartStationStatus_Click(ActionEvent e) throws Exception
	{
	}

	//#CM41326
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Setting_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41327
	/** 
	 * called on clicking "Set" button<BR>
	 * <BR>
	 * Abstract :fetch the set target data and change the status to enable<BR>
	 * <BR>
	 * <DIR>
	 * 		1.fetch target data for setting<BR>
	 * 		2.start scheduler<BR>
	 * 		<DIR>
	 * 			[parameter] *mandatory items<BR>
	 * 			<DIR>
	 * 				ViewState.worker code *<BR>
	 * 				ViewState.password *<BR>
	 * 				job type(operation)*<BR>
	 * 				list cell.station no.<BR>
	 * 				terminal no.<BR>
	 * 				list cell line no.<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * 		3.refresh list cell<BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Setting_Click(ActionEvent e) throws Exception
	{
		//#CM41328
		//fetch max. line no
		int index = lst_StartStationStatus.getMaxRows();

		Connection conn = null;
		try
		{
			AsScheduleParameter param = new AsScheduleParameter();
			WmsScheduler schedule = new StationStatusSCH();
			//#CM41329
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			//#CM41330
			// worker code, password input check
			param.setWorkerCode(this.getViewState().getString(WORKERCODE_KEY));
			param.setPassword(this.getViewState().getString(PASSWORD_KEY));
			
			if (!schedule.check(conn,param))
			{
				conn.rollback();
			}
			else
			{
				//#CM41331
				// save the AGC No. against selected checkbox
				Vector vecParam = new Vector();
				for (int lc=1; lc<index; lc++)
				{
					//#CM41332
					// specify line
					lst_StartStationStatus.setCurrentRow(lc);
					
					//#CM41333
					// move to next info if selected
					if(!lst_StartStationStatus.getChecked(1) )		continue;
					
					param = new AsScheduleParameter();
					
					param.setWorkerCode(this.getViewState().getString(WORKERCODE_KEY));
					param.setPassword(this.getViewState().getString(PASSWORD_KEY));
					
					//#CM41334
					// set process flag in operation screen
					param.setProcessStatus(Integer.toString(Station.NOT_SUSPEND));
					
					//#CM41335
					// station no.
					param.setStationNo(lst_StartStationStatus.getValue(2));
					
					//#CM41336
					// terminal no.
					UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
					param.setTerminalNumber(userHandler.getTerminalNo());
					
					//#CM41337
					// save the line no.
					param.setRowNo(lc);
					
					vecParam.addElement(param);
				}
				if (vecParam.size() <= 0)
				{
					//#CM41338
					// 6023154 = modification target data does not exist
					message.setMsgResourceKey("6023154");
					return;
				}

				AsScheduleParameter[] paramArray = new AsScheduleParameter[vecParam.size()];
				vecParam.copyInto(paramArray);

				//#CM41339
				// start schedule
				AsScheduleParameter[] viewParam =
					(AsScheduleParameter[]) schedule.startSCHgetParams(conn, paramArray);

				if (viewParam == null)
				{
					conn.rollback();
					//#CM41340
					// set message
					message.setMsgResourceKey(schedule.getMessage());
				}
				else
				{
					conn.commit();

					//#CM41341
					// set listcell
					setList(viewParam);
				}
			}
			//#CM41342
			// set message
			message.setMsgResourceKey(schedule.getMessage());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			conn.rollback();
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM41343
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

	//#CM41344
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Reflesh_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41345
	/** 
	 * call when the refresh button is pressed<BR>
	 * <BR>
	 * Abstract :refresh list cell<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Reflesh_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM41346
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
		
			//#CM41347
			// set schedule parameter
			AsScheduleParameter param = new AsScheduleParameter();
			//#CM41348
			// set process flag in operation screen
			param.setProcessStatus(Integer.toString(Station.NOT_SUSPEND));
			
			WmsScheduler schedule = new StationStatusSCH();

			AsScheduleParameter[] viewParam = (AsScheduleParameter[]) schedule.query(conn, param);
			
			//#CM41349
			//display data
			setList(viewParam);
			
			//#CM41350
			// check worder code and password during initial display
			if (e == null)
			{
				//#CM41351
				// worker code
				param.setWorkerCode(this.getViewState().getString(WORKERCODE_KEY));
				//#CM41352
				// password
				param.setPassword(this.getViewState().getString(PASSWORD_KEY));
				
				schedule.check(conn,param);
			}
		
			//#CM41353
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
				//#CM41354
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}


	//#CM41355
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41356
	/** 
	 * call the [Close] button click event process <BR>
	 *  <BR>
	 * close this screen and move back to caller screen<BR>
	 *  <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM41357
		// return to origin screen
		parentRedirect(null);
	}


}
//#CM41358
//end of class
