// $Id: AsDailyUpdateBusiness.java,v 1.2 2006/10/26 04:12:08 suresh Exp $

//#CM35101
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsdailyupdate;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.schedule.AsDailyUpdateSCH;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.system.display.web.dailyupdate.DailyUpdateBusiness;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM35102
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * The next day update class for the AS/RS package. <BR>
 * The content input from the screen is set in the parameter, and do the next day update as follows based on the parameter.<BR>
 * The schedule must return true in the normal termination and return false by abnormal termination. <BR>
 * <CODE>DailyUpdateBusiness</CODE>Succeed to the class, and in the ASRS package,
 * Only necessary processing is an overriding.
 * <BR>
 * The following process are called in this class<BR>
 * <BR>
 * Processing when Start button is pressed(<CODE>btn_Start_Click</CODE>method )<BR>
 * <BR>
 * <DIR>
 *   Display the dialog box, confirm whether to update it at the same date, and update it as follows in information on input area.<BR>
 * <BR>
 *     [parameter] *mandatory input<BR>
 *    <BR>
 *	  <DIR>
 *       worker code * <BR>
 *       password * <BR>
 *       work date(default display) <BR>
 *       unstarted work info * <BR>
 * 	  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/03/27</TD><TD>K.Toda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:12:08 $
 * @author  $Author: suresh $
 */
public class AsDailyUpdateBusiness extends DailyUpdateBusiness implements WMSConstants
{
	//#CM35103
	// Class fields --------------------------------------------------
	//#CM35104
	/**
	 * Whether the dialog called from which control is maintained: The delivery work check when true.
	 */
	protected static final String DIALOG_KBN = "DIALOG_KBN";

	//#CM35105
	// Class variables -----------------------------------------------

	//#CM35106
	// Class method --------------------------------------------------

	//#CM35107
	// Constructors --------------------------------------------------

	//#CM35108
	// Public methods ------------------------------------------------

	//#CM35109
	/**
	 * It is called when default value of the screen is displayed.<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * and set the cursor in worker code<BR>
	 * <BR>
	 * <DIR>
	 *   	item name[initial value]<BR>
	 *   	<DIR>
	 *			worker code [nil] <BR>
	 *   		password [nil] <BR>
	 *   		work date [Day of work of system definition] <BR>
	 *   		Standby information [Delete] <BR>
	 * 	 	</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM35110
		// Default display.
		StartDisp();
	}
	
	//#CM35111
	/**
	 * call this before calling the respective control events<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM35112
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM35113
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM35114
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}
	}

	//#CM35115
	// Package methods -----------------------------------------------

	//#CM35116
	// Protected methods ---------------------------------------------

	//#CM35117
	// Private methods -----------------------------------------------
	//#CM35118
	/**
	 * initial screen display<BR>
	 * @throws Exception report all the exceptions
	 */
	private void StartDisp() throws Exception
	{
		//#CM35119
		// Initial display.	
		rdo_Delete.setChecked(true);
		rdo_WorkInfoOrver.setChecked(false);
		//#CM35120
		// set cursor in worker code
		setFocus(txt_WorkerCode);

		Connection conn = null;
		try
		{
			SystemParameter param = new SystemParameter();

			//#CM35121
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new AsDailyUpdateSCH();
			SystemParameter viewParam = (SystemParameter) schedule.initFind(conn, param);

			//#CM35122
			// Initial display of Working day.
			txt_FDate.setDate(WmsFormatter.toDate(viewParam.getWorkDate()));
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM35123
				// close connection
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

	//#CM35124
	/**
	 * This method is called when returning from the dialog button.<BR>
	 * Override <CODE>page_ConfirmBack</CODE> defined in <CODE>Page</CODE>.<BR>
	 * <BR>
	 * Abstract : Execute the processing of the correspondence when [Yes] is selected by the dialog. <BR>
	 * <BR>
	 * <DIR>
	 * 	    1.Check from which dialog return. <BR>
	 *      <DIR>
	 *      	RFT Check		:Do the delivery work check. <BR>
	 *      	Delivery work check : Do the Next day processing.<BR>
	 *      </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		try
		{
			//#CM35125
			// Check from which dialog return. 
			//#CM35126
			// Processing when dialog is called by RFT check
			if (!this.getViewState().getBoolean(DIALOG_KBN))
			{
				//#CM35127
				// Processing when [Yes] is pressed
				if (new Boolean(e.getEventArgs().get(0).toString()).booleanValue())
				{
					DialogWorkCheck();
					return ;
				}	
			}
			//#CM35128
			// Processing when dialog is called by delivery judgment
			else
			{
				if (new Boolean(e.getEventArgs().get(0).toString()).booleanValue())
				{
			        //#CM35129
			        // Start next day processing when [Yes] is pressed.
					startDailyUpdate();
			    }
			    else
			    {
			        //#CM35130
			        // Do nothing when [No] is pressed.
			    }

				//#CM35131
				// Turn off the flag because the dialog operation ended. 
				this.getViewState().setBoolean(DIALOG_KBN, false);
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
		}
	}

	//#CM35132
	/**
	 * When the delivery work dialog is checked, it is called. <BR>
	 * <BR>
	 * Abstract : Check whether delivery work remains,display confirmation dialog<BR>
	 * <BR>
	 * @throws Exception report all the exceptions
	 */
	public void DialogWorkCheck() throws Exception
	{
		Connection conn = null;
		
		try
		{
			//#CM35133
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	
			//#CM35134
			// The remainder work data dialog check processing
			//#CM35135
			// Refer to the WareNavi_System table. 
			//#CM35136
			// Hereafter, check it when it is the stock management and the delivery package exists. 
			WareNaviSystem wareNaviSystem = new WareNaviSystem();
			WareNaviSystemHandler wnsysHandle = new WareNaviSystemHandler(conn);
			WareNaviSystemSearchKey wnsysSearchKey = new WareNaviSystemSearchKey();
			
			wareNaviSystem = (WareNaviSystem)wnsysHandle.findPrimary(wnsysSearchKey);
	
			if (wareNaviSystem.getStockPack().equals(WareNaviSystem.PACKAGE_FLAG_ADDON) &&
				wareNaviSystem.getRetrievalPack().equals(WareNaviSystem.PACKAGE_FLAG_ADDON))
			{
				//#CM35137
				// When the work information data is retrieved, and as much as one delivery data other than 'Completion' and 'Deletion' exist
				//#CM35138
				// Display the confirmation dialog, and press the confirmation to the worker. 
				WorkingInformationHandler wkinfoHandle = new WorkingInformationHandler(conn);
				WorkingInformationSearchKey wkinfoSearchKey = new WorkingInformationSearchKey();
				
				wkinfoSearchKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL, "=", "((", "", "OR");
				wkinfoSearchKey.setJobType(WorkingInformation.JOB_TYPE_EX_RETRIEVAL, "=", "", ")", "AND");			
				wkinfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION, "!=", "(", "", "AND");
				wkinfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=", "", "))", "AND");
	
				if (wkinfoHandle.count(wkinfoSearchKey) > 0)
				{
					//#CM35139
					// display confirmation dialog
					//#CM35140
					// MSG-W0065 = The work which began to be delivered exists. Is next day processing executed and is it good?
					setConfirm("MSG-W0065");
					
					//#CM35141
					// Display the Message [Processing]
					message.setMsgResourceKey("6001017");
	
					//#CM35142
					// Memorize the dialog display from the delivery check. 
					this.getViewState().setBoolean(DIALOG_KBN, true);
				}
				else
				{
					//#CM35143
					// Check whether the dialog of the RFT work was output. 
					//#CM35144
					// It corresponds so as not to display the dialog when it is output and there is no delivery check. 
					RftHandler rftHandler = new RftHandler(conn);
					RftSearchKey rftSearchKey = new RftSearchKey();
					
					rftSearchKey.setJobType(Rft.JOB_TYPE_UNSTART, "!=");
					
					//#CM35145
					// Check whether data other than StandBy exist, and output the dialog. 
					//#CM35146
					// (When the dialog of the RFT work is not displayed)
					if (rftHandler.count(rftSearchKey) == 0)
					{
						//#CM35147
						// display confirmation dialog
						//#CM35148
						// MSG-W0019=start ?
						setConfirm("MSG-W0019");
						
						//#CM35149
						// [[in process] message display
						message.setMsgResourceKey("6001017");
	
						//#CM35150
						// Memorize the dialog display from the delivery check. 
						this.getViewState().setBoolean(DIALOG_KBN, true);
					}
					else
					{
						startDailyUpdate();
					}
				}
			}
			else
			{
				//#CM35151
				// Check whether the dialog of the RFT work was output. 
				//#CM35152
				// It corresponds so as not to display the dialog when it is output and there is no delivery check. 
				RftHandler rftHandler = new RftHandler(conn);
				RftSearchKey rftSearchKey = new RftSearchKey();
				
				rftSearchKey.setJobType(Rft.JOB_TYPE_UNSTART, "!=");
				
				//#CM35153
				// Check whether data other than StandBy exist, and output the dialog. 
				//#CM35154
				// (When the dialog of the RFT work is not displayed)
				if (rftHandler.count(rftSearchKey) == 0)
				{
					//#CM35155
					// display confirmation dialog
					//#CM35156
					// MSG-W0019=start ?
					setConfirm("MSG-W0019");
					
					//#CM35157
					// Display the Message [Processing]
					message.setMsgResourceKey("6001017");
	
					//#CM35158
					// Memorize the dialog display from the delivery check. 
					this.getViewState().setBoolean(DIALOG_KBN, true);
				}
				else
				{
			        startDailyUpdate();
				}
			}
		}
		catch (Exception ex)
		{
			//#CM35159
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
				//#CM35160
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
	
	//#CM35161
	/**
	 * The method of processing next day update.<BR>
	 * <BR>
	 * Abstract : Start the schedule of next day update.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.start scheduler<BR>
	 * 		<DIR>
	 * 			[parameter] *mandatory items<BR>
	 * 			<DIR>
	 *       		worker code * <BR>
	 *       		password * <BR>
	 *       		work date(default display) <BR>
	 *       		unstarted work info * <BR>
	 *      	</DIR>
	 *      </DIR>
	 * 		2.Advance the work day for the first when the schedule result is normal.<BR>
	 * 		Display error message when abnormal.<BR>
	 * </DIR>
	 * <BR>
	 * @throws Exception report all the exceptions
	 */
	public void startDailyUpdate() throws Exception
	{
		SystemParameter startParams = getInputParam();
		Vector vecParam = new Vector();
		vecParam.add(startParams);

		Connection conn = null;
		try
		{
			SystemParameter[] paramArray = new SystemParameter[vecParam.size()];
			vecParam.copyInto(paramArray);
			//#CM35162
			// fetch connection			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new AsDailyUpdateSCH();

			SystemParameter[] sparam =
				(SystemParameter[]) schedule.startSCHgetParams(conn, paramArray);

			//#CM35163
			// start schedule
			if (sparam != null && sparam.length != 0)
			{
				conn.rollback();
				//#CM35164
				// Set Message
				message.setMsgResourceKey(schedule.getMessage());

				//#CM35165
				// Advance the work day for the first. 
				txt_FDate.setDate(WmsFormatter.toDate(sparam[0].getWorkDate()));
			}
			else
			{
				conn.commit();
				//#CM35166
				// Set Message
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
				//#CM35167
				// close connection
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
	
	//#CM35168
	/**
	 * Set the content of input area when processing begins in the parameter, and return it. <BR>
	 * @return input Parameter which maintains content of area
	 */
	protected SystemParameter getInputParam()
	{
		//#CM35169
		// set parameter
		SystemParameter startParams = new SystemParameter();
		//#CM35170
		// worker code
		startParams.setWorkerCode(txt_WorkerCode.getText());
		//#CM35171
		// password
		startParams.setPassword(txt_Password.getText());
		//#CM35172
		// work date
		startParams.setWorkDate(WmsFormatter.toParamDate(txt_FDate.getDate()));

		//#CM35173
		// Delete
		if (rdo_Delete.getChecked())
		{
			startParams.setSelectUnworkingInformation(
				SystemParameter.SELECTUNWORKINGINFORMATION_DELETE);
			rdo_Delete.setChecked(true);
			rdo_WorkInfoOrver.setChecked(false);
		}
		//#CM35174
		// Carry over work information. 
		else if (rdo_WorkInfoOrver.getChecked())
		{
			startParams.setSelectUnworkingInformation(
				SystemParameter.SELECTUNWORKINGINFORMATION_CARRYOVER);
			rdo_Delete.setChecked(false);
			rdo_WorkInfoOrver.setChecked(true);
		}
		return startParams;
	}
	//#CM35175
	// Event handler methods -----------------------------------------
	//#CM35176
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35177
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35178
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35179
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

	//#CM35180
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35181
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35182
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM35183
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM35184
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM35185
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35186
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35187
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM35188
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM35189
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM35190
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35191
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35192
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM35193
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM35194
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Upwi_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35195
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Delete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35196
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Delete_Click(ActionEvent e) throws Exception
	{
	}

	//#CM35197
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_WorkInfoOrver_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35198
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_WorkInfoOrver_Click(ActionEvent e) throws Exception
	{
	}

	//#CM35199
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Start_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35200
	/** 
	 * It is called when the Start button is pressed.<BR>
	 * <BR>
	 * overview :: Display the dialog box, and confirm whether to update it on the same date. <BR>
	 * <BR>
	 * <DIR>
	 *  1.set cursor in worker code <BR>
	 *   <BR>
	 *  2.Display Start Confirm dialog box.<BR>
	 *   <BR>
	 *   <DIR>
	 *     [Start confirmation dialog cancellation] <BR>
	 *     <DIR>
	 *       Nothing is done. <BR>
	 *       <BR>
	 *     </DIR>
	 *     [Start confirmation dialog OK]
	 *     <DIR>
	 *       Begin the schedule of information on input area. <BR>
	 *     	<DIR>
	 *       1.Input information is set in the parameter and input check. <BR>
	 *       2.start scheduler<BR>
	 *      </DIR>
	 *     </DIR>   
	 *   </DIR> 
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Start_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		
		try
		{
			//#CM35201
			// set cursor in worker code
			setFocus(txt_WorkerCode);
			
			//#CM35202
			// mandatory input check
			txt_WorkerCode.validate();
			txt_Password.validate();
	
			//#CM35203
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	
			WmsScheduler schedule = new AsDailyUpdateSCH();
			if (!schedule.check(conn, getInputParam()))
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
	
			//#CM35204
			// Dialog check processing when RFT is starting
			RftHandler rftHandler = new RftHandler(conn);
			RftSearchKey rftSearchKey = new RftSearchKey();
			
			rftSearchKey.setJobType(Rft.JOB_TYPE_UNSTART, "!=");
			
			//#CM35205
			// Check whether data other than StandBy exist, and output the dialog. 
			if (rftHandler.count(rftSearchKey) > 0)
			{
				//#CM35206
				// display confirmation dialog
				//#CM35207
				// MSG-W0066 = Is it good though the handy terminal under the start exists?
				setConfirm("MSG-W0066");

				//#CM35208
				// Display the Message [Processing]
				message.setMsgResourceKey("6001017");
				
				return;
			}
			//#CM35209
			// Do not output the dialog and to the Delivery work check method for all standby
			else
			{
				DialogWorkCheck();
			}
		}
		catch (Exception ex)
		{
			//#CM35210
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
				//#CM35211
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

	//#CM35212
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35213
	/** 
	 * It is called when a clear button is pressed.<BR>
	 * <BR>
	 * overview ::clear the input area <BR>
	 * <BR>
	 * <DIR>
	 *   1.Clear the item of input area. <BR>
	 *   <DIR>
	 *     Clear neither work code nor the password. <BR>
	 *   </DIR>
	 *   2.set cursor in worker code <BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		StartDisp();
	}
}
//#CM35214
//end of class
