// $Id: StopStationStatusPopupBusiness.java,v 1.2 2006/10/26 08:09:11 suresh Exp $

//#CM41618
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.stopstationstatuspopup;
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

//#CM41619
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * This is the ASRS station suspend setting popup screen class<BR>
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
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:09:11 $
 * @author  $Author: suresh $
 */
public class StopStationStatusPopupBusiness extends StopStationStatusPopup implements WMSConstants
{
	//#CM41620
	// Class fields --------------------------------------------------
	//#CM41621
	/** 
	 * key used to transfer worker code
	 */
	public static final String WORKERCODE_KEY = "WORKERCODE_KEY";

	//#CM41622
	/** 
	 * key used to transfer password
	 */
	public static final String PASSWORD_KEY = "PASSWORD_KEY";

	//#CM41623
	// Class variables -----------------------------------------------

	//#CM41624
	// Class method --------------------------------------------------

	//#CM41625
	// Constructors --------------------------------------------------

	//#CM41626
	// Public methods ------------------------------------------------

	//#CM41627
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
		//#CM41628
		//save to viewstate
		this.getViewState().setString(WORKERCODE_KEY, request.getParameter(WORKERCODE_KEY));
		this.getViewState().setString(PASSWORD_KEY, request.getParameter(PASSWORD_KEY));
		
		//#CM41629
		// combine refresh process
		btn_Reflesh_Click(null);
	}

	//#CM41630
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
		//#CM41631
		// station suspend setting
		lbl_ListName.setText(DisplayText.getText("TLE-W0920"));

		//#CM41632
		// confirmation dialog message MSG-9000= Do you set?
		btn_Setting.setBeforeConfirm("MSG-9000");
	}

	//#CM41633
	// Package methods -----------------------------------------------

	//#CM41634
	// Protected methods ---------------------------------------------

	//#CM41635
	// Private methods -----------------------------------------------

	//#CM41636
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
		//#CM41637
		// call display area clear process
		lst_StopStationStatus.clearRow();

		if (viewParam == null || viewParam.length == 0)
		{
			return;
		}

		//#CM41638
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
			//#CM41639
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
				
			//#CM41640
			// add data to tool tip
			ToolTipHelper toolTip = new ToolTipHelper();
			toolTip.add(title_Station, viewParam[lc].getStationNo());
			toolTip.add(title_StationName, viewParam[lc].getDispStationName());
			toolTip.add(title_ModeTypeName, viewParam[lc].getDispModeTypeName());
			toolTip.add(title_CurrentModeName, viewParam[lc].getDispCurrentModeName());
			toolTip.add(title_StatusName, viewParam[lc].getDispControllerStatusName());
			toolTip.add(title_SuspendName, viewParam[lc].getDispWorkingStatusName());
			toolTip.add(title_WorkingCount, Formatter.getNumFormat(viewParam[lc].getWorkingCount()));

			//#CM41641
			// set tool tip	
			lst_StopStationStatus.setToolTip(lc+1, toolTip.getText());
		}
	}

	//#CM41642
	// Event handler methods -----------------------------------------
	//#CM41643
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41644
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41645
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
		//#CM41646
		// return to origin screen
		parentRedirect(null);
	}

	//#CM41647
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheck_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41648
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
		for(int lc=1; lc< lst_StopStationStatus.getMaxRows(); lc++)
		{  			
			lst_StopStationStatus.setCurrentRow(lc);
			lst_StopStationStatus.setChecked( 1, true );
		} 
		message.setMsgResourceKey("6001013");
	}

	//#CM41649
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheckClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41650
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
		for(int lc=1; lc< lst_StopStationStatus.getMaxRows(); lc++)
		{  			
			lst_StopStationStatus.setCurrentRow(lc);
			lst_StopStationStatus.setChecked( 1, false );
		} 
		message.setMsgResourceKey("6001013");
	}

	//#CM41651
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StopStationStatus_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM41652
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StopStationStatus_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM41653
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StopStationStatus_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM41654
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StopStationStatus_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM41655
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StopStationStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41656
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StopStationStatus_Change(ActionEvent e) throws Exception
	{
	}

	//#CM41657
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StopStationStatus_Click(ActionEvent e) throws Exception
	{
	}

	//#CM41658
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Setting_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41659
	/** 
	 * called on clicking "Set" button<BR>
	 * <BR>
	 * Abstract :fetch target data and call suspend status change process<BR>
	 * <BR>
	 * <DIR>
	 * 		1.fetch target data for setting<BR>
	 * 		2.start scheduler<BR>
	 * 		<DIR>
	 * 			[parameter] *mandatory items<BR>
	 * 			<DIR>
	 * 				ViewState.worker code *<BR>
	 * 				ViewState.password *<BR>
	 * 				job type(suspend)*<BR>
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
		//#CM41660
		//fetch max. line no
		int index = lst_StopStationStatus.getMaxRows();

		Connection conn = null;
		try
		{
			AsScheduleParameter param = new AsScheduleParameter();
			WmsScheduler schedule = new StationStatusSCH();
			//#CM41661
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			//#CM41662
			// worker code, password input check
			param.setWorkerCode(this.getViewState().getString(WORKERCODE_KEY));
			param.setPassword(this.getViewState().getString(PASSWORD_KEY));
			
			if (!schedule.check(conn,param))
			{
				conn.rollback();
			}
			else
			{
				//#CM41663
				//save the AGC No. against selected checkbox
				Vector vecParam = new Vector();
				for (int lc=1; lc<index; lc++)
				{
					//#CM41664
					// specify line
					lst_StopStationStatus.setCurrentRow(lc);

					//#CM41665
					// move to next info if selected
					if(!lst_StopStationStatus.getChecked(1) )		continue;
					
					param = new AsScheduleParameter();
				
					param.setWorkerCode(this.getViewState().getString(WORKERCODE_KEY));
					param.setPassword(this.getViewState().getString(PASSWORD_KEY));
				
					//#CM41666
					// set process flag in suspend screen
					param.setProcessStatus(Integer.toString(Station.SUSPENDING));

					//#CM41667
					// station no.
					param.setStationNo(lst_StopStationStatus.getValue(2));

					//#CM41668
					// terminal no.
					UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
					param.setTerminalNumber(userHandler.getTerminalNo());

					//#CM41669
					// save the line no.
					param.setRowNo(lc);

					vecParam.addElement(param);
				}
				if (vecParam.size() <= 0)
				{
					//#CM41670
					// 6023154 = modification target data does not exist
					message.setMsgResourceKey("6023154");
					return;
				}

				AsScheduleParameter[] paramArray = new AsScheduleParameter[vecParam.size()];
				vecParam.copyInto(paramArray);

				//#CM41671
				// start schedule
				AsScheduleParameter[] viewParam =
					(AsScheduleParameter[]) schedule.startSCHgetParams(conn, paramArray);

				if (viewParam == null)
				{
					conn.rollback();
				}
				else
				{
					conn.commit();

					//#CM41672
					// set listcell
					setList(viewParam);
				}
			}
			//#CM41673
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
				//#CM41674
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

	//#CM41675
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Reflesh_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41676
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
			//#CM41677
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
		
			//#CM41678
			// set schedule parameter
			AsScheduleParameter param = new AsScheduleParameter();
			//#CM41679
			// set process flag in suspend screen
			param.setProcessStatus(Integer.toString(Station.SUSPENDING));
		
			WmsScheduler schedule = new StationStatusSCH();

			AsScheduleParameter[] viewParam = (AsScheduleParameter[]) schedule.query(conn, param);

			//#CM41680
			//display data
			setList(viewParam);

			//#CM41681
			// check worder code and password during initial display
			if (e == null)
			{
				//#CM41682
				// worker code
				param.setWorkerCode(this.getViewState().getString(WORKERCODE_KEY));
				//#CM41683
				// password
				param.setPassword(this.getViewState().getString(PASSWORD_KEY));
				
				schedule.check(conn,param);
			}
			
			//#CM41684
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
				//#CM41685
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}
	//#CM41686
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM41687
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
		//#CM41688
		// return to origin screen
		parentRedirect(null);
	}


}
//#CM41689
//end of class
