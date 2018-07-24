// $Id: CommunicationBusiness.java,v 1.2 2006/11/13 08:17:41 suresh Exp $

//#CM687107
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.communication;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.system.display.web.listbox.listrftnumber.ListRftNumberBusiness;
import jp.co.daifuku.wms.base.system.schedule.CommunicationSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM687108
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this class to provide a screen for inquiring the Communication Trace Log.<BR>
 * Set the contents entered via screen for the parameter. Allow the schedule to search data to be displayed based on the parameter.<BR>
 * Receive data to be output to the preset area from the schedule and output it to the preset area.<BR>
 * Receive the result from the schedule. Receive true if the process completed normally.<BR>
 * Or, receive false if the schedule failed to complete due to condition error etc.<BR>
 * As the result of the schedule, output the message obtained from the schedule to the screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process by clicking "Display" button(<CODE>btn_View_Click()</CODE>method)<BR>
 * <BR>
 * <DIR>
 *	Search through the RFTxxxRftCommunicationTrace.txt using the field items entered in the Input area as conditions. display the search results in the Preset area.<BR>
 *	Return an error message if condition error etc. occurs.<BR>
 * <BR>
 *	[Parameter] *Mandatory Input <BR>
 *	<BR>
 *	<DIR>
 *		RFT Machine No..* <BR>
 *		Starting date/time of searching <BR>
 *		End date/time of searching (time) <BR>
 *		Display condition* <BR>
 *	</DIR>
 *	<BR>
 *	[Data for Output] <BR>
 *	<BR>
 *	<DIR>
 *		Date/Time <BR>
 *		Sent/Received <BR>
 *		Communication Statement <BR>
 *	</DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/06</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:17:41 $
 * @author  $Author: suresh $
 */
public class CommunicationBusiness extends Communication implements WMSConstants
{
	//#CM687109
	// Class fields --------------------------------------------------
	//#CM687110
	/**
	 * Display condition (All)
	 */
	public static final String DSPCONDITION_ALL = "0";

	//#CM687111
	/**
	 * Display condition (Sent)
	 */
	public static final String DSPCONDITION_SEND = "1";

	//#CM687112
	/**
	 * Display condition (Received)
	 */
	public static final String DSPCONDITION_RECEIVE = "2";
	//#CM687113
	// Class variables -----------------------------------------------

	//#CM687114
	// Class method --------------------------------------------------

	//#CM687115
	// Constructors --------------------------------------------------

	//#CM687116
	// Public methods ------------------------------------------------

	//#CM687117
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Field item [Initial Value]<BR>
	 * <DIR>
	 *	RFT Machine No..		[None] <BR>
	 *	Starting date/time of searching	[None] <BR>
	 *	End date/time of searching (time)	[None] <BR>
	 *	Display condition		[ "All" ] <BR>
	 * </DIR>
	 * <BR>
	 *	1.Set the cursor to RFT Machine No. <BR>
	 *	2.Set the radio button to "All". <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM687118
		// Set focus for RFT Machine No. 
		setFocus(txt_RftMachineNo);
		rdo_DcdAll.setChecked(true);
	}

	//#CM687119
	/**
	 * Invoke this before invoking each control event.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM687120
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM687121
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM687122
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM687123
			//Set the path to the help file.
			//#CM687124
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
	}

	//#CM687125
	/**
	  * Invoke this method when returning from the popup window.<BR>
	  * Override the page_DlgBack defined for Page.<BR>
	  * <BR>
	  * Summary: Sets the data obtained from the Search screen.<BR>
	  * <BR><DIR>
	  *      1.Obtain the value returned from the Search popup screen.<BR>
	  *      2.Set the screen if the value is not empty.<BR>
	  * <BR></DIR>
	  * [Returned data] <BR>
	  * <DIR>
	  *     RFT Machine No.. <BR>
	  * </DIR>
	  * @param e ActionEvent A class to store event information.
	  * @throws Exception Report all exceptions.
	  */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();

		//#CM687126
		//Set focus for RFT Machine No. 
		setFocus(txt_RftMachineNo);
		//#CM687127
		// Obtain the parameter selected in the listbox.
		String rftmachine = param.getParameter(ListRftNumberBusiness.RFTNUMBER_KEY);

		//#CM687128
		// Set a value if the folder for storing the if Machine No. is not empty.
		if (!StringUtil.isBlank(rftmachine))
		{
			txt_RftMachineNo.setText(rftmachine);
		}
	}

	//#CM687129
	// Package methods -----------------------------------------------

	//#CM687130
	// Protected methods ---------------------------------------------

	//#CM687131
	// Private methods -----------------------------------------------
	//#CM687132
	/**
	 * Set the value for the preset area.
	 * @param listParams Value to be set in the preset area.
	 */
	private void setList(Parameter[] listParams)
	{
		//#CM687133
		// Clear the list cell.
		lst_SCmuTreLogIqr.clearRow();

		SystemParameter[] viewParam = (SystemParameter[]) listParams;

		for (int i = 0; i < viewParam.length; i++)
		{
			//#CM687134
			// Add a line to a list cell.
			lst_SCmuTreLogIqr.setCurrentRow(i + 1);
			lst_SCmuTreLogIqr.addRow();

			//#CM687135
			// Set a value in the list cell.
			lst_SCmuTreLogIqr.setValue(1, viewParam[i].getMessageLogDate());

			if (viewParam[i]
				.getCommunication()
				.equals(SystemParameter.SELECTDISPLAYCONDITION_COMMUNICATIONLOG_RECEIVE))
			{
				//#CM687136
				// Received
				lst_SCmuTreLogIqr.setValue(2, DisplayText.getText("RDB-W0053"));
			}
			else if (
				viewParam[i].getCommunication().equals(
					SystemParameter.SELECTDISPLAYCONDITION_COMMUNICATIONLOG_SEND))
			{
				//#CM687137
				// Sent
				lst_SCmuTreLogIqr.setValue(2, DisplayText.getText("RDB-W0052"));
			}

			lst_SCmuTreLogIqr.setValue(3, viewParam[i].getCommunicationMessage());
		}

	}

	//#CM687138
	// Event handler methods -----------------------------------------
	//#CM687139
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687140
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687141
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687142
	/** 
	 * Clicking on Menu button invokes this.<BR>  
	 * Summary: Shifts to the Menu screen.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM687143
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RftMachine_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687144
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RftMachineNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687145
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RftMachineNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687146
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RftMachineNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687147
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RftMachineNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM687148
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687149
	/** 
	 * Clicking on Search button invokes this.
	 * <BR>
	 * Obtain the RFT Machine No. from the RFT Control info and display the view list. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_PSearch_Click(ActionEvent e) throws Exception
	{
		//#CM687150
		// Set the parameter to be passed to the next screen.
		ForwardParameters param = new ForwardParameters();

		//#CM687151
		// Machine No.
		param.setParameter(ListRftNumberBusiness.RFTNUMBER_KEY, txt_RftMachineNo.getText());

		//#CM687152
		// Processing screen -> "Result" screen
		redirect("/system/listbox/listrftnumber/ListRftNumber.do", param, "/progress.do");
	}

	//#CM687153
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SrchStrtDateTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687154
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SerchStartDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687155
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SerchStartDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687156
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SerchStartDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687157
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SearchStartTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687158
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SearchStartTime_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687159
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SearchStartTime_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687160
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687161
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndSearchDateTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687162
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SerchEndDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687163
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SerchEndDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687164
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SerchEndDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687165
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SearchEndTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687166
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SearchEndTime_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687167
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SearchEndTime_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687168
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DspCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687169
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DcdAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687170
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DcdAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM687171
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DcdSend_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687172
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DcdSend_Click(ActionEvent e) throws Exception
	{
	}

	//#CM687173
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DcdReceive_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687174
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DcdReceive_Click(ActionEvent e) throws Exception
	{
	}

	//#CM687175
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687176
	/** 
	 * Clicking on Display button invokes this.
	 * <BR>
	 * Search through the RFTxxxRftCommunicationTrace.txt using the field items entered in the Input area as conditions. display the search results in the View. <BR>
	 * <BR>
	 * <DIR>
	 *	1.Check for input in the input item (count) in the input area. (Mandatory Input, number of characters, and attribution of characters) <BR>
	 *	2.Start the schedule.<BR>
	 * 		- Display the logs in the order of time at which they are written.<BR>
	 *	3.Display it in the preset area. <BR>
	 *	4.Set the cursor to RFT Machine No.<BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		//#CM687177
		// Set focus for Machine No.
		setFocus(txt_RftMachineNo);
		//#CM687178
		// modify end
		
		//#CM687179
		// Check for input.
		txt_RftMachineNo.validate();
		//#CM687180
		// Characters that match the pattern.
		txt_SerchStartDate.validate(false);
		txt_SearchStartTime.validate(false);
		txt_SerchEndDate.validate(false);
		txt_SearchEndTime.validate(false);

		//#CM687181
		// Regard as error if the value of Starting date/time of searching is smaller than the value of End date/time of searching.
		if (txt_SerchStartDate.getDate() != null && txt_SerchEndDate.getDate() != null)
		{
			if (txt_SerchStartDate.getDate().after(txt_SerchEndDate.getDate()))
			{
				//#CM687182
				// 6023182=Starting date of search must precede the end date.
				message.setMsgResourceKey("6023182");
				return;
			}
			else if (txt_SerchStartDate.getDate().equals(txt_SerchEndDate.getDate()))
			{
				if (txt_SearchStartTime.getTime() != null && txt_SearchEndTime.getTime() != null)
				{
					if (txt_SearchStartTime.getTime().after(txt_SearchEndTime.getTime()))
					{
						//#CM687183
						// 6023182=Starting date of search must precede the end date.
						message.setMsgResourceKey("6023182");
						return;
					}
				}
			}
		}

		Connection conn = null;
		try
		{
			//#CM687184
			// Set for the schedule parameter.
			SystemParameter param = new SystemParameter();
			//#CM687185
			// RFT Machine No..
			param.setRftNo(txt_RftMachineNo.getText());
			//#CM687186
			// Starting date of searching
			param.setFromFindDate(WmsFormatter.toParamDate(txt_SerchStartDate.getDate()));
			//#CM687187
			// Starting time of searching
			param.setFromFindTime(WmsFormatter.toParamTime(txt_SearchStartTime.getTime()));
			//#CM687188
			// End date of searching
			param.setToFindDate(WmsFormatter.toParamDate(txt_SerchEndDate.getDate()));
			//#CM687189
			// End time of searching
			param.setToFindTime(WmsFormatter.toParamTime(txt_SearchEndTime.getTime()));
			//#CM687190
			// Radio button
			//#CM687191
			// All
			if (rdo_DcdAll.getChecked())
			{
				param.setSelectDisplayCondition_CommunicationLog(
					SystemParameter.SELECTDISPLAYCONDITION_COMMUNICATIONLOG_ALL);
				rdo_DcdAll.setChecked(true);
				rdo_DcdSend.setChecked(false);
				rdo_DcdReceive.setChecked(false);
			}
			//#CM687192
			// Sent
			else if (rdo_DcdSend.getChecked())
			{
				param.setSelectDisplayCondition_CommunicationLog(
					SystemParameter.SELECTDISPLAYCONDITION_COMMUNICATIONLOG_SEND);
				rdo_DcdAll.setChecked(false);
				rdo_DcdSend.setChecked(true);
				rdo_DcdReceive.setChecked(false);
			}
			//#CM687193
			// Received
			else if (rdo_DcdReceive.getChecked())
			{
				param.setSelectDisplayCondition_CommunicationLog(
					SystemParameter.SELECTDISPLAYCONDITION_COMMUNICATIONLOG_RECEIVE);
				rdo_DcdAll.setChecked(false);
				rdo_DcdSend.setChecked(false);
				rdo_DcdReceive.setChecked(true);
			}

			//#CM687194
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new CommunicationSCH();
			SystemParameter[] viewParam = (SystemParameter[]) schedule.query(conn, param);

			if (viewParam == null || viewParam.length == 0)
			{
				//#CM687195
				// Clear the list cell.
				lst_SCmuTreLogIqr.clearRow();

				message.setMsgResourceKey(schedule.getMessage());	
				return;
			}

			//#CM687196
			// Display a list cell.
			setList(viewParam);
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
				//#CM687197
				// Close the connection.
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
	}

	//#CM687198
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687199
	/** 
	 * Clicking on Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area.<BR>
	 * </BR>
	 * <DIR>
	 * 		1.Return the item (count) in the input area to the initial value.<BR>
	 * 		<DIR>
	 * 			- For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method.
	 * 		</DIR>
	 * 		2.Set the cursor to RFT Machine No. <BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM687200
		// Clear the textbox.
		txt_RftMachineNo.setText("");
		txt_SerchStartDate.setText("");
		txt_SearchStartTime.setText("");
		txt_SerchEndDate.setText("");
		txt_SearchEndTime.setText("");
		//#CM687201
		// Set the radio button to "All".
		rdo_DcdAll.setChecked(true);
		rdo_DcdSend.setChecked(false);
		rdo_DcdReceive.setChecked(false);

		//#CM687202
		// Set focus for Machine No.
		setFocus(txt_RftMachineNo);
	}

	//#CM687203
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SCmuTreLogIqr_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687204
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SCmuTreLogIqr_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687205
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SCmuTreLogIqr_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM687206
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SCmuTreLogIqr_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM687207
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SCmuTreLogIqr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687208
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SCmuTreLogIqr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM687209
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SCmuTreLogIqr_Click(ActionEvent e) throws Exception
	{
	}

}
//#CM687210
//end of class
