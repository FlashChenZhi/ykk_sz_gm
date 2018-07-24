// $Id: AsWorkMaintenanceBusiness.java,v 1.3 2006/11/27 04:43:55 kamala Exp $

//#CM37784
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsworkmaintenance;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.schedule.AsWorkMaintenanceSCH;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrswork.ListAsWorkBusiness;
import jp.co.daifuku.wms.asrs.display.web.startstationstatuspopup.StartStationStatusPopupBusiness;
import jp.co.daifuku.wms.asrs.display.web.stopstationstatuspopup.StopStationStatusPopupBusiness;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;

//#CM37785
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * The ASRS work maintenance screen class. <BR>
 * set the input contents from the screen to a parameter
 * The schedule does the work maintenance based on the parameter. <BR>
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
 *  The content input from input area is set in the parameter, and the schedule does the work maintenance based on the parameter. <BR>
 *  Receive true when the result is received from the schedule, and processing is completed normally. 
 *  Receive false when it does not complete the schedule because of the condition error etc.<BR>
 *  output the schedule result, schedule message to screen<BR>
 * 	<DIR>
 *   [parameter] *mandatory input <BR>
 *   <BR>
 * 		worker code *<BR>
 * 		password *<BR>
 * 		warehouse *<BR>
 * 		location no. *<BR>
 * 		terminal no.<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/27 04:43:55 $
 * @author  $Author: kamala $
 */
public class AsWorkMaintenanceBusiness extends AsWorkMaintenance implements WMSConstants
{
	//#CM37786
	// Class fields --------------------------------------------------

	//#CM37787
	// Class variables -----------------------------------------------

	//#CM37788
	// Class method --------------------------------------------------

	//#CM37789
	// Constructors --------------------------------------------------

	//#CM37790
	// Public methods ------------------------------------------------

	//#CM37791
	/**
	 * screen initialization<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * <BR>
	 * <DIR>
	 * 		1.initialize input area<BR>
	 * 		2.initialize pulldown<BR>
	 * 		<BR>
	 * 		item[initial value] <BR>
	 * 		<DIR>
	 * 			worker code[nil] <BR>
	 * 			password[nil] <BR>
	 * 			process [uninstructed]<BR>
	 * 			conveyance origin[nil] <BR>
	 * 			conveyance destination[nil] <BR>
	 * 			location no.[nil] <BR>
	 * 			conveyance type[nil] <BR>
	 * 			conveyance status[nil] <BR>
	 * 			work type[nil] <BR>
	 * 			picking instruction detail[nil] <BR>
	 * 			job no.[nil] <BR>
	 * 			conveyance key[nil] <BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM37792
		// Initialize the screen
		setInitView(true);

		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

		Connection conn = null;

		try
		{
			//#CM37793
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM37794
			// fetch Http Locale
			Locale locale = this.getHttpRequest().getLocale();
			Locale.setDefault(locale);

			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			//#CM37795
			// pull down display data(workplace)
			String[] sagyoba = pull.getSagyobaPulldownData(conn, PulldownData.STATION_WORK_MNT, false, "");
			//#CM37796
			// pull down display data(station)
			String[] stno = pull.getStationPulldownData(conn, PulldownData.STATION_WORK_MNT, false, "");

			//#CM37797
			// set pulldown data
			PulldownHelper.setPullDown(pul_WorkPlace, sagyoba);
			PulldownHelper.setLinkedPullDown(pul_Station, stno);

			//#CM37798
			// add child pull down
			pul_WorkPlace.addChild(pul_Station);
		}
		catch (Exception ex)
		{
			//#CM37799
			// rollback connection
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
				//#CM37800
				// close connection
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM37801
	/**
	 * This method is called when returning from popup window<BR>
	 * override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE><BR>
	 * <BR>
	 * overview ::fetch the value from search screen and set it<BR>
	 * <BR>
	 * <DIR>
	 *      1.fetch the value set from popup search screen<BR>
	 *      2.set value if it is not null<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();

		//#CM37802
		// conveyance key
		String strCarryKey = param.getParameter(ListAsWorkBusiness.CARRYKEY_KEY);
		//#CM37803
		// set the value if not empty
		if (!StringUtil.isBlank(strCarryKey))
		{
			//#CM37804
			// fetch parameter selected from listbox
			//#CM37805
			// conveyance origin
			String strSourceStation = param.getParameter(ListAsWorkBusiness.SOURCESTATION_KEY);
			//#CM37806
			// conveyance destination
			String strDestStation = param.getParameter(ListAsWorkBusiness.DESTSTATION_KEY);
			//#CM37807
			// location no.
			String strLocationNo = param.getParameter(ListAsWorkBusiness.LOCATIONNO_KEY);
			//#CM37808
			// job type
			String strCarryKind = param.getParameter(ListAsWorkBusiness.CARRYKIND_KEY);
			//#CM37809
			// picking instruction detail
			String strRetrievalDetail = param.getParameter(ListAsWorkBusiness.RETRIEVALDETAIL_KEY);
			//#CM37810
			// work type
			String strWorkType = param.getParameter(ListAsWorkBusiness.WORKTYPE_KEY);
			//#CM37811
			// work status
			String strCmdStatus = param.getParameter(ListAsWorkBusiness.CMDSTATUS_KEY);
			//#CM37812
			// job no.
			String strWorkNo = param.getParameter(ListAsWorkBusiness.WORKNUMBER_KEY);

			//#CM37813
			// conveyance origin
			lbl_JavaSetCarryForm.setText(strSourceStation);
			//#CM37814
			// conveyance destination
			lbl_JavaSetCarryTo.setText(strDestStation);
			//#CM37815
			// location no.
			lbl_JavaSetLocationNo.setText(strLocationNo);
			//#CM37816
			// job type
			lbl_JavaSetWorkFlag.setText(strCarryKind);
			//#CM37817
			// picking instruction detail
			lbl_JavaSetRetrievalDetails.setText(strRetrievalDetail);
			//#CM37818
			// work type
			lbl_JavaSetWorkClass.setText(strWorkType);
			//#CM37819
			// work status
			lbl_JavaSetWorkStatus.setText(strCmdStatus);
			//#CM37820
			// job no.
			lbl_JavaSetWorkNo.setText(strWorkNo);
			//#CM37821
			// conveyance key
			lbl_JavaSetCarryKey.setText(strCarryKey);

			//#CM37822
			// Set button
			btn_Setting.setEnabled(true);
			//#CM37823
			// refresh
			btn_Reflesh.setEnabled(true);
			//#CM37824
			// clear
			btn_Clear.setEnabled(true);
			//#CM37825
			// STsuspend
			btn_Station_Stop.setEnabled(true);
			//#CM37826
			// SToperation
			btn_Station_Start.setEnabled(true);
		}
	}

	//#CM37827
	/**
	 * call this before calling the respective control events<BR>
	 * <BR>
	 * overview ::display confirmation dialog<BR>
	 * and set the cursor in worker code<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM37828
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM37829
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM37830
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}
		//#CM37831
		// clicking "Set" button displays "Do you set?"
		btn_Setting.setBeforeConfirm("MSG-9000");
		//#CM37832
		// Confirmation dialog message when Clear button is pressed "Do you want to cancel?"
		btn_Clear.setBeforeConfirm("MSG-9004");

		//#CM37833
		// set the cursor in worker code input
		setFocus(txt_WorkderCode);
	}

	//#CM37834
	// Package methods -----------------------------------------------

	//#CM37835
	// Protected methods ---------------------------------------------

	//#CM37836
	// Private methods -----------------------------------------------

	//#CM37837
	/**
	 * This method initializes screen<BR>
	 * <BR>
	 * overview ::initial screen display <BR>
	 * and Be effective, and set the button of input area invalidly. <BR>
	 * <BR>
	 * <DIR>
	 * 		[parameter] <BR>
	 * 		<DIR>
	 * 			Do not initialize worker code/password for wkrClrFlg true, and do not initialize worker code/password for false. <BR>
	 * 			<DIR>
	 * 				item name[initial value] <BR>
	 * 				<DIR>
	 * 					worker code[nil/as it is, while clicking clear button] <BR>
	 * 					password[nil/as it is, while clicking clear button] <BR>
	 * 					process [uninstructed]<BR>
	 * 					conveyance origin[nil] <BR>
	 * 					conveyance destination[nil] <BR>
	 * 					location no.[nil] <BR>
	 * 					conveyance type[nil] <BR>
	 * 					conveyance status[nil] <BR>
	 * 					work type[nil] <BR>
	 * 					picking instruction detail[nil] <BR>
	 * 					job no.[nil] <BR>
	 * 					conveyance key[nil] <BR>
	 * 				</DIR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * @param wkrClrFlg Clear Flag
	 * @throws Exception report all the exceptions
	 */
	private void setInitView(boolean wkrClrFlg) throws Exception
	{
		//#CM37838
		// Initialize the input item. 
		if (wkrClrFlg)
		{
			//#CM37839
			// worker code
			txt_WorkderCode.setText("");
			//#CM37840
			// password
			txt_Password.setText("");
		}
			
		//#CM37841
		// Reset the Unspecified, End and Delete Tracking.
		rdo_Maintenance_NoSend.setChecked(false);
		rdo_Maintenance_Complete.setChecked(false);
		rdo_Maintenance_CarryDelete.setChecked(false);

		//#CM37842
		// Set the unspecified.
		rdo_Maintenance_NoSend.setChecked(true);

		//#CM37843
		// conveyance origin
		lbl_JavaSetCarryForm.setText("");
		//#CM37844
		// conveyance destination
		lbl_JavaSetCarryTo.setText("");
		//#CM37845
		// location no.
		lbl_JavaSetLocationNo.setText("");
		//#CM37846
		// conveyance type
		lbl_JavaSetWorkFlag.setText("");
		//#CM37847
		// picking instruction detail
		lbl_JavaSetRetrievalDetails.setText("");
		//#CM37848
		// work type
		lbl_JavaSetWorkClass.setText("");
		//#CM37849
		// conveyance status
		lbl_JavaSetWorkStatus.setText("");
		//#CM37850
		// job no.
		lbl_JavaSetWorkNo.setText("");
		//#CM37851
		// conveyance key
		lbl_JavaSetCarryKey.setText("");

		//#CM37852
		// Nullify the button of input area. 
		//#CM37853
		// Set button
		btn_Setting.setEnabled(false);
		//#CM37854
		// refresh
		btn_Reflesh.setEnabled(false);
		//#CM37855
		// clear
		btn_Clear.setEnabled(false);
		//#CM37856
		// STsuspend
		btn_Station_Stop.setEnabled(false);
		//#CM37857
		// SToperation
		btn_Station_Start.setEnabled(true);
	}

	//#CM37858
	// Event handler methods -----------------------------------------
	//#CM37859
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37860
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37861
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Maintenance_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37862
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37863
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

	//#CM37864
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WokerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37865
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkderCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37866
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkderCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37867
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkderCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37868
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkderCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM37869
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37870
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37871
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37872
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37873
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM37874
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkPlaceStation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37875
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkPlace_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37876
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkPlace_Change(ActionEvent e) throws Exception
	{
	}

	//#CM37877
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Station_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37878
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Station_Change(ActionEvent e) throws Exception
	{
	}

	//#CM37879
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Query_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37880
	/** 
	 * This method is called upon clicking the inquiry button<BR>
	 * Set the search condition, and display the Carry data list screen in pop up. <BR>
	 * <BR>
	 * <search condition><BR>
	 * <DIR>
	 *   terminal no.<BR>
	 *   workplace<BR>
	 *   station no.<BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_Query_Click(ActionEvent e) throws Exception
	{
		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

		//#CM37881
		// Set the search condition on the carry info retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM37882
		// terminal no
		param.setParameter(ListAsWorkBusiness.TERMINALNO_KEY, userHandler.getTerminalNo());
		//#CM37883
		// workplace
		param.setParameter(ListAsWorkBusiness.WORKPLACE_KEY, pul_WorkPlace.getSelectedValue());
		//#CM37884
		// station
		param.setParameter(ListAsWorkBusiness.STATIONNO_KEY, pul_Station.getSelectedValue());
		//#CM37885
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrswork/ListAsWork.do", param, "/progress.do");
	}

	//#CM37886
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Setting_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37887
	/** 
	 * called on clicking "Set" button<BR>
	 * <BR>
	 * Abstract :Set the screen item in the parameter, and do work maintenance Process. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.start scheduler<BR>
	 * 		<DIR>
	 * 			[parameter] *mandatory items<BR>
	 * 			<DIR>
	 * 				worker code *<BR>
	 * 				password *<BR>
	 * 				Transportation Key*<BR>
	 * 				Process*<BR>
	 * 				terminal no.<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * 		2.Set effective of each button and invalidity again. <BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Setting_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM37888
			//Get the connection.
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			AsScheduleParameter param = new AsScheduleParameter();

			//#CM37889
			// input check
			txt_WorkderCode.validate();
			txt_Password.validate();

			//#CM37890
			// worker code
			param.setWorkerCode(txt_WorkderCode.getText().trim());
			//#CM37891
			// password
			param.setPassword(txt_Password.getText().trim());
			//#CM37892
			// conveyance key
			param.setCarryKey(lbl_JavaSetCarryKey.getText().trim());

			WmsScheduler schedule = new AsWorkMaintenanceSCH();

			//#CM37893
			// Check before maintenance
			if (!schedule.check(conn, param))
			{
				conn.rollback();
				//#CM37894
				// set message
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM37895
			// Acquire the selected processing. 
			String transaction = AsWorkMaintenanceSCH.KIND_UNINSTRUCT;
			if( rdo_Maintenance_NoSend.getChecked() )
			{
				//#CM37896
				// Unspecified
				transaction = AsWorkMaintenanceSCH.KIND_UNINSTRUCT;
			}
			else if( rdo_Maintenance_Complete.getChecked() )
			{
				//#CM37897
				// Force Stop
				transaction = AsWorkMaintenanceSCH.KIND_COMPLETE;
			}
			else if( rdo_Maintenance_CarryDelete.getChecked() )
			{
				//#CM37898
				// Delete Tracking.
				transaction = AsWorkMaintenanceSCH.KIND_TRACKINGDEL;
			}
			//#CM37899
			// Set processing. 
			param.setProcessStatus(transaction);

			//#CM37900
			//Output the work maintenance list to the printer of the terminal which works. 
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo)this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());

			AsScheduleParameter[] params = new AsScheduleParameter[1];
			params[0] = param;

			//#CM37901
			// start schedule
			if (schedule.startSCH(conn, params))
			{
				//#CM37902
				// commit if the result is true
				conn.commit();
				//#CM37903
				// set message
				message.setMsgResourceKey(schedule.getMessage());

				//#CM37904
				// disable button click
				//#CM37905
				// Set button
				btn_Setting.setEnabled(false);
				//#CM37906
				// refresh
				btn_Reflesh.setEnabled(false);
				//#CM37907
				// STsuspend
				btn_Station_Stop.setEnabled(false);
				//#CM37908
				// SToperation
				btn_Station_Start.setEnabled(true);
			}
			else
			{
				conn.rollback();
				//#CM37909
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			//#CM37910
			// rollback connection
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
				//#CM37911
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

	//#CM37912
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Reflesh_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37913
	/** 
	 * call when the refresh button is pressed<BR>
	 * <BR>
	 * Abstract :Display carry info again. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.start scheduler<BR>
	 * 		<DIR>
	 * 			[parameter]<BR>
	 * 			<DIR>
	 * 				conveyance key<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * 		2.Set the display data in the every particular item, and set effective of each button and invalidity again. 
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Reflesh_Click(ActionEvent e) throws Exception
	{
		String strCarryKey = lbl_JavaSetCarryKey.getText();
		if( StringUtil.isBlank(strCarryKey) )
		{
			return;
		}

		Connection conn = null;

		try
		{
			//#CM37914
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			AsScheduleParameter sparam = new AsScheduleParameter();
			sparam.setCarryKey(strCarryKey);
			WmsScheduler schedule = new AsWorkMaintenanceSCH();

			//#CM37915
			// inquiry
			AsScheduleParameter[] ansparam = (AsScheduleParameter[]) schedule.query(conn, sparam);
			if( ansparam == null )
			{
				//#CM37916
				// set message
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM37917
			// Set the display data from carry info. 

			//#CM37918
			// location no.
			String strLocationNo = ansparam[0].getLocationNo();
			if (!StringUtil.isBlank(strLocationNo))
			{
				lbl_JavaSetLocationNo.setText(DisplayText.formatLocation(strLocationNo));
			}

			//#CM37919
			// job type
			lbl_JavaSetWorkFlag.setText(ansparam[0].getJobTypeName());

			//#CM37920
			// Transportation origin / transportation destination
			//#CM37921
			// conveyance origin
			lbl_JavaSetCarryForm.setText(ansparam[0].getFromStationName());
			//#CM37922
			// conveyance destination
			lbl_JavaSetCarryTo.setText(ansparam[0].getToStationName());

			//#CM37923
			// picking instruction detail
			lbl_JavaSetRetrievalDetails.setText(ansparam[0].getDispRetrievalDetail());

			//#CM37924
			// work type
			lbl_JavaSetWorkClass.setText(ansparam[0].getDispWorkType());

			//#CM37925
			// work status
			lbl_JavaSetWorkStatus.setText(ansparam[0].getDispCarringStatusName());

			//#CM37926
			// job no.
			lbl_JavaSetWorkNo.setText(ansparam[0].getWorkingNo());

			//#CM37927
			// conveyance key
			lbl_JavaSetCarryKey.setText(strCarryKey);

			//#CM37928
			// Try to set the button again. 
			//#CM37929
			// Set button
			btn_Setting.setEnabled(true);
			//#CM37930
			// refresh
			btn_Reflesh.setEnabled(true);
			//#CM37931
			// clear
			btn_Clear.setEnabled(true);
			//#CM37932
			// STsuspend
			btn_Station_Stop.setEnabled(true);
			//#CM37933
			// SToperation
			btn_Station_Start.setEnabled(true);
		}
		catch (Exception ex)
		{
			//#CM37934
			// rollback connection
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
				//#CM37935
				// close connection
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM37936
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37937
	/** 
	 * called when clear button is clicked<BR>
	 * <BR>
	 * Abstract :clear the input area<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Return to initial values of the items of input area. <BR>
	 * 		<DIR>
	 *   	item name[initial value]<BR>
	 *   		<DIR>
	 * 				worker code[as it is] <BR>
	 * 				password[as it is] <BR>
	 * 				process [uninstructed]<BR>
	 * 				conveyance origin[nil] <BR>
	 * 				conveyance destination[nil] <BR>
	 * 				location no.[nil] <BR>
	 * 				conveyance type[nil] <BR>
	 * 				conveyance status[nil] <BR>
	 * 				work type[nil] <BR>
	 * 				picking instruction detail[nil] <BR>
	 * 				job no.[nil] <BR>
	 * 				conveyance key[nil] <BR>
	 * 	 		</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM37938
		// Clear Carry Info.
		setInitView(false);
	}

	//#CM37939
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Work_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37940
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Maintenance_NoSend_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37941
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Maintenance_NoSend_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37942
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Maintenance_Complete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37943
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Maintenance_Complete_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37944
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Maintenance_CarryDelete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37945
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Maintenance_CarryDelete_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37946
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Carry_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37947
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Station_Stop_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37948
	/** 
	 * It is called when the STsuspend button is pressed.<BR>
	 * <BR>
	 * overview ::Display ASRSstation suspend setting. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *    	worker code<BR>
	 *    	password<BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_Station_Stop_Click(ActionEvent e) throws Exception
	{
		//#CM37949
		//Set the search condition on station status change setting (suspend setting) screen.
		ForwardParameters param = new ForwardParameters();

		//#CM37950
		// worker code
		param.setParameter(StopStationStatusPopupBusiness.WORKERCODE_KEY, txt_WorkderCode.getText());
		//#CM37951
		// password
		param.setParameter(StopStationStatusPopupBusiness.PASSWORD_KEY, txt_Password.getText());

		//#CM37952
		//in process screen -> result screen
		redirect("/asrs/stopstationstatuspopup/StopStationStatusPopup.do", param, "/progress.do");
	}

	//#CM37953
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Station_Start_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37954
	/** 
	 * When the SToperation button is pressed, it is called. <BR>
	 * <BR>
	 * overview ::Display ASRSstation operation setting. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *    	worker code<BR>
	 *    	password<BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_Station_Start_Click(ActionEvent e) throws Exception
	{
		
		//#CM37955
		//Set the search condition on station status change setting (operation setting) screen for the work information call. 
		ForwardParameters param = new ForwardParameters();
		
		//#CM37956
		// worker code
		param.setParameter(StartStationStatusPopupBusiness.WORKERCODE_KEY, txt_WorkderCode.getText());
		//#CM37957
		// password
		param.setParameter(StartStationStatusPopupBusiness.PASSWORD_KEY, txt_Password.getText());
		
		//#CM37958
		//in process screen -> result screen
		redirect("/asrs/startstationstatuspopup/StartStationStatusPopup.do", param, "/progress.do");
	}

	//#CM37959
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LocationNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37960
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetLocationNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37961
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37962
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetWorkFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37963
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalDetails_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37964
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetRetrievalDetails_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37965
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkClass_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37966
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetWorkClass_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37967
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37968
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetWorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37969
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37970
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetWorkNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37971
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CarryKey_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37972
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCarryKey_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37973
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCarryForm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37974
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCarryTo_Server(ActionEvent e) throws Exception
	{
	}
}
//#CM37975
//end of class
