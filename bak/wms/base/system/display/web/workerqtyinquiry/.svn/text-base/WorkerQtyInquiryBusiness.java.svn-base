// $Id: WorkerQtyInquiryBusiness.java,v 1.2 2006/11/13 08:22:18 suresh Exp $

//#CM694977
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.workerqtyinquiry;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.PullDownItem;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.system.display.web.listbox.listworkday.ListWorkDayBusiness;
import jp.co.daifuku.wms.base.system.display.web.listbox.listworker.ListWorkerBusiness;
import jp.co.daifuku.wms.base.system.display.web.listbox.listworkerqtyinquiry.ListWorkerQtyInquiryBusiness;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.system.schedule.WorkQtyInquirySCH;

//#CM694978
/**
 * Designer : B.Shibayama <BR>
 * Maker    : B.Shibayama <BR>
 * <BR>
 * Allow this class to provide a screen for inquiring the Result by Worker.<BR>
 * Set the contents input via screen for the parameter and then pass it to the schedule. <BR>
 * Receive the result from the schedule. Receive true if the process completed normally.<BR>
 * Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 * As the result of the schedule, output the message obtained from the schedule to the screen. <BR>
 * <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.For initial loading (<CODE>page_Load</CODE> method)<BR>
 * <BR>
 * <DIR>
 *   Change the work type that can be selected depending on the package in use.
 * <BR>
 *   [Parameter] *Mandatory Input <BR>
 * <BR>
 *   <DIR>
 * 		  None <BR>   
 *   </DIR>
 * <BR>
 *   [Returned data] <BR>
 *  <BR><DIR>
 *        Work type <BR>
 *    </DIR>
 * </DIR>
 * <BR>
 * 2.Process by clicking "Print" button(<CODE>btn_Print_Click</CODE>method)<BR>
 * <BR>
 * <DIR>
 *   Check the field items. If proper, set them for the parameter.<BR>
 *   Execute printing. Set the result of schedule in the Message area and display it.<BR>
 * <BR>
 *   [Parameter] *Mandatory Input <BR>
 * <BR>
 *   <DIR>
 * 		  Work type * <BR>
 *        Start work date <BR>
 *        End Work Date <BR>
 *        Worker Code <BR>
 *        Aggregation Conditions * <BR>    
 *   </DIR>
 * <BR>
 *   [Returned data] <BR>
 *  <BR><DIR>
 *         Message <BR>
 *    </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/16</TD><TD>B.Shibayama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:22:18 $
 * @author  $Author: suresh $
 */
public class WorkerQtyInquiryBusiness extends WorkerQtyInquiry implements WMSConstants
{
	//#CM694979
	// Class fields --------------------------------------------------
	//#CM694980
	/** 
	 * Use this key to pass work type.
	 */
	public static final String WORKDETAILS_KEY = "WORKDETAILS_KEY";

	//#CM694981
	// Maintain the control that invokes the dialog: Print button
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";
	//#CM694982
	// Class variables -----------------------------------------------

	//#CM694983
	// Class method --------------------------------------------------

	//#CM694984
	// Constructors --------------------------------------------------


	//#CM694985
	// Private methods -----------------------------------------------
	
	//#CM694986
	// Public methods ------------------------------------------------

	//#CM694987
	/**
	 * Completing reading a screen invokes this.<BR>
	 * <BR>
	 * Summary: Shows the Initial Display.<BR>
	 * <BR>
	 * <DIR>
	 *    1.Display the title.<BR>
	 *    2.Initialize the input area.<BR>
	 *    3.Set the cursor to Work Type.<BR>
	 * </DIR>
	 * <BR>
	 * Field item [Initial Value] <BR>
	 * <BR>
	 * Work Type [All] <BR>
	 * Start Work Date [None] <BR>
	 * End Work Date [None] <BR>
	 * Worker Code [None] <BR>
	 * Aggregation Conditions [Display the daily total] <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM694988
		// Set a default value for each Field item.
		//#CM694989
		// Clear the work type.
		pul_WorkDetails.clearItem() ;
		
		txt_StartWorkDate.setText("");
		txt_EndWorkDate.setText("");
		txt_WorkerCode.setText("");

		//#CM694990
		// Aggregation Conditions
		rdo_TermTotal.setChecked(false);
		//#CM694991
		// Display the daily total.
		rdo_DailyTotal.setChecked(true);
		rdo_Detail.setChecked(false);

		//#CM694992
		// Set the cursor to Work Type.
		setFocus(pul_WorkDetails);
		
		Connection conn = null;

		try
		{
       	    //#CM694993
       	    // Obtain the connection.
		    conn = ConnectionManager.getConnection(DATASOURCE_NAME);
		    //#CM694994
		    // Declare the schedule.
		    WmsScheduler schedule = new WorkQtyInquirySCH();
		    //#CM694995
		    // Declare the parameter.
			SystemParameter [] sysParam =null;
		    //#CM694996
		    // Start the schedule.		
		    sysParam = (SystemParameter [])schedule.query(conn, null);
		 
			if(sysParam != null)
			{
				for(int i= 0; i < sysParam.length ; i++)
				{
					//#CM694997
					// Generate pulldown options.
					PullDownItem pItem = new PullDownItem();
					//#CM694998
					// Set a field item for pulldown options.
					pItem.setValue(DisplayUtil.getSelectWorkDetail( sysParam[i].getSelectWorkDetail()));
					//#CM694999
					// Add it to the pulldown.
					pul_WorkDetails.addItem(pItem);
				}
				//#CM695000
				// Display the leading item in the pulldown.
				pul_WorkDetails.setSelectedIndex(0);
			}
			else
			{
				//#CM695001
				// 6006001=Unexpected error occurred.{0}{0}
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
				//#CM695002
				// Close the connection.
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM695003
	/**
	 * Invoke this method when returning from the popup window.<BR>
	 * Override the page_DlgBack defined for Page.<BR>
	 * <BR>
	 * Summary: Obtains the Returned data in the Search screen and set it.<BR>
	 * <BR><DIR>
	 *      1.Obtain the value returned from the Search popup screen.<BR>
	 *      2.Set the screen if the value is not empty.<BR>
	 * <BR></DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{

		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM695004
		// Obtain the parameter selected in the listbox.
		Date startworkdate = WmsFormatter.toDate(param.getParameter(ListWorkDayBusiness.STARTWORKDAY_KEY),this.getHttpRequest().getLocale());
		Date endworkdate = WmsFormatter.toDate(param.getParameter(ListWorkDayBusiness.ENDWORKDAY_KEY),this.getHttpRequest().getLocale());
		String workercode = param.getParameter(ListWorkerBusiness.WORKERCODE_KEY);

		//#CM695005
		// Set a value if not empty.
        //#CM695006
        // Start work date
		if (!StringUtil.isBlank(startworkdate))
		{
			
			txt_StartWorkDate.setDate(startworkdate);
		}

        //#CM695007
        // End Work Date
		if (!StringUtil.isBlank(endworkdate))
		{
			
			txt_EndWorkDate.setDate(endworkdate);
		}

		//#CM695008
		// Worker Code
		if (!StringUtil.isBlank(workercode))
		{
			txt_WorkerCode.setText(workercode);
		}

		//#CM695009
		// Set the cursor to Work Type.
		setFocus(pul_WorkDetails);
	}
	
	//#CM695010
	/** 
	 * Returning from the Dialog button invokes this method.
	 * Override the page_ConfirmBack defined for Page.
	 * <BR>
	 * Summary: Executes the selected process if selected "Yes" in the dialog.<BR>
	 * <BR>
	 * 1.Check which dialog returns it.<BR>
	 * 2.Check for choice of "Yes" or "No" clicked in the dialog box.<BR>
	 * 3.Selecting "Yes" starts the schedule.<BR>
	 * 4.For print dialog,<BR>
	 *   <DIR>
	 *   4-1.Set the parameter in the input field.<BR>
	 *   4-2.Start the printing schedule.<BR>
	 *   4-3.Display the schedule result in the Message area.<BR>
	 *	 </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		try
		{
			//#CM695011
			// Set the cursor to Work Type.
			setFocus(pul_WorkDetails);

			//#CM695012
			// Identify which dialog returns it.
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM695013
			// Clicking on "Yes" in the dialog box returns true.
			//#CM695014
			// Clicking on "No" in the dialog box returns false.
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM695015
			// Clicking "NO" button closes the process.
			//#CM695016
			// Require to set no message here.
			if (!isExecute)
			{
				return;
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			//#CM695017
			// Ensure to turn the flag OFF after closed the operation of the dialog.
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM695018
		// Start the printing schedule.
		Connection conn = null;
		try
		{
			//#CM695019
			// Obtain the connection.
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM695020
			// Set the input value for the parameter.
			SystemParameter[] param = new SystemParameter[1];
			param[0] = createParameter();

			//#CM695021
			// Start the schedule.
			WmsScheduler schedule = new WorkQtyInquirySCH();
			schedule.startSCH(conn, param);
			
			//#CM695022
			// Set the message
			message.setMsgResourceKey(schedule.getMessage());
	
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
	
		}
		finally
		{
			try
			{
				//#CM695023
				// Close the connection.
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
	
		}
	}

	//#CM695024
	/**
	 * Invoke this before invoking each control event. <BR>
	 * <BR>
	 * <DIR>
	 *  Summary: Displays a dialog. <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM695025
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM695026
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM695027
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM695028
			//Set the path to the help file.
			//#CM695029
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
	}
	//#CM695030
	// Package methods -----------------------------------------------

	//#CM695031
	// Protected methods ---------------------------------------------
	//#CM695032
	/** 
	 * Generate a parameter object for which the input value of the input area is set.<BR>
	 * 
	 * @return Parameter object that contains input values in the input area.
	 * @throws Exception Report all exceptions.
	 */
	protected SystemParameter createParameter() throws Exception
	{
		//#CM695033
		// Set the schedule parameter.
		SystemParameter param = new SystemParameter();
		//#CM695034
		// Set the Work Type.
		param.setSelectWorkDetail(getWorkDeTails());
		//#CM695035
		// Start work date
		param.setFromWorkDate(WmsFormatter.toParamDate(txt_StartWorkDate.getDate()));
		//#CM695036
		//	End Work Date
		param.setToWorkDate(WmsFormatter.toParamDate(txt_EndWorkDate.getDate()));
		//#CM695037
		// Worker Code
		param.setWorkerCode(txt_WorkerCode.getText());

		//#CM695038
		//Aggregation Conditions
		if (rdo_TermTotal.getChecked())
		{
			//#CM695039
			// Display the total within a period.
			param.setSelectAggregateCondition(SystemParameter.SELECTAGGREGATECONDITION_TERM);
		}
		else if (rdo_DailyTotal.getChecked())
		{
			//#CM695040
			// Display the daily total.
			param.setSelectAggregateCondition(SystemParameter.SELECTAGGREGATECONDITION_DAILY);
		}
		else
		{
			//#CM695041
			// Display the detail.
			param.setSelectAggregateCondition(SystemParameter.SELECTAGGREGATECONDITION_DETAIL);
		}
		return param;	
	}
	//#CM695042
	// Private methods -----------------------------------------------
	//#CM695043
	/** Allow this method to return the work type of the parameter. <BR>
	 * @param arg Work type of parameter.
	 * @return Work type
	 */
	private String getWorkDeTailsParameter(String arg)
	{
		//#CM695044
		//	Obtain the work type.
		if (arg.equals(SystemParameter.SELECTWORKDETAIL_ALL))
		{
			//#CM695045
			// All
			return SystemParameter.SELECTWORKDETAIL_ALL;
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_INSTOCK))
		{
			//#CM695046
			// Receiving
			return SystemParameter.SELECTWORKDETAIL_INSTOCK;
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_STORAGE))
		{
			//#CM695047
			// Storage
			return SystemParameter.SELECTWORKDETAIL_STORAGE;
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_RETRIEVAL))
		{
			//#CM695048
			// Picking
			return SystemParameter.SELECTWORKDETAIL_RETRIEVAL;
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_SORTING))
		{
			//#CM695049
			// Sorting
			return SystemParameter.SELECTWORKDETAIL_SORTING;
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_SHIPPING))
		{
			//#CM695050
			// Shipping
			return SystemParameter.SELECTWORKDETAIL_SHIPPING;
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_INVENTORY))
		{
			//#CM695051
			// Inventory Check
			return SystemParameter.SELECTWORKDETAIL_INVENTORY;
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_INVENTORY_INCREASE))
		{
			//#CM695052
			// Increase Inventory Check
			return SystemParameter.SELECTWORKDETAIL_INVENTORY_INCREASE;
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_INVENTORY_DECREASE))
		{
			//#CM695053
			// Decrease Inventory Check
			return SystemParameter.SELECTWORKDETAIL_INVENTORY_DECREASE;
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_MOVEMENT_STORAGE))
		{
			//#CM695054
			// Relocation for Storage
			return SystemParameter.SELECTWORKDETAIL_MOVEMENT_STORAGE;
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_MOVEMENT_RETRIEVAL))
		{
			//#CM695055
			// Relocation for Retrieval
			return SystemParameter.SELECTWORKDETAIL_MOVEMENT_RETRIEVAL;
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDSTORAGE))
		{
			//#CM695056
			// Unplanned Storage
			return SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDSTORAGE;
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDRETRIEVAL))
		{
			//#CM695057
			// Unplanned Picking
			return SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDRETRIEVAL;
		}
		else if (arg.equals(SystemParameter.SELECTWORKDETAIL_MAINTENANCE_INCREASE))
		{
			//#CM695058
			// Increase in Maintenance
			return SystemParameter.SELECTWORKDETAIL_MAINTENANCE_INCREASE ;
		}
		else if(arg.equals(SystemParameter.SELECTWORKDETAIL_MAINTENANCE_DECREASE))
		{
			//#CM695059
			// Increase in Maintenance
			return SystemParameter.SELECTWORKDETAIL_MAINTENANCE_DECREASE ;
		}
		else
		{
			return null;
		}

	}
	
	//#CM695060
	/** Invoke a method to obtain parameter from work type selected in the pulldown menu. <BR>
	 * <BR>
	 * <DIR>
	 *  <BR>
	 *   'Obtain strings such as "Shipping'', "Storage", and ''Maintenance".
	 *   Pass the value to the method that obtains parameters and obtain the parameters.
	 * </DIR>
	 * @return Work type
	 * @throws Exception Report all exceptions.
	 */
	private String getWorkDeTails() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM695061
			// Obtain the connection.
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM695062
			// Declare the schedule.
			WmsScheduler schedule = new WorkQtyInquirySCH();
			//#CM695063
			// Declare the parameter.
			SystemParameter [] sysParam =null;
			//#CM695064
			// Start the schedule.		
			sysParam = (SystemParameter [])schedule.query(conn, null);
		 
			if(sysParam != null)
			{
				//#CM695065
				// Obtain the value in the currently selected pulldown menu.
				int i = pul_WorkDetails.getSelectedIndex();
				//#CM695066
				// Invoke a method and obtain a parameter.
				return getWorkDeTailsParameter(sysParam[i].getSelectWorkDetail());
			}
			else
			{
				//#CM695067
				// 6006001=Unexpected error occurred.{0}{0}
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
				//#CM695068
				// Close the connection.
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
		
		return null;	
	}
	//#CM695069
	// Event handler methods -----------------------------------------
	//#CM695070
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695071
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695072
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695073
	/** 
	* Clicking on Menu button invokes this.<BR>
	* <BR>
	* Summary: Shifts to the Menu screen. 
	* 
	* @param e ActionEvent A class to store event information.
	* @throws Exception Report all exceptions.
	*/
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM695074
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkDetails_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695075
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkDetails_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695076
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkDetails_Change(ActionEvent e) throws Exception
	{
	}

	//#CM695077
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartWorkDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695078
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartWorkDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695079
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartWorkDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM695080
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartWorkDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM695081
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrtWk_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695082
	/** 
	 * Clicking on Search button of Start Work Date invokes this.<BR>
	 * <BR>
	 * Summary: Sets the input data for the parameter and displays a listbox for searching the work date.<BR>
	 * <BR>
	 * [Parameter]
	 *  <DIR>
	 *  	Start work date <BR>
	 *      Work type <BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_PSearchStrtWk_Click(ActionEvent e) throws Exception
	{
		//#CM695083
		// Declare the instance to set the search conditions.
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM695084
		// Set the Start Work Date.
		forwardParam.setParameter(
			ListWorkDayBusiness.STARTWORKDAY_KEY,
			WmsFormatter.toParamDate(txt_StartWorkDate.getDate()));
		//#CM695085
		// Work type
		forwardParam.setParameter(WORKDETAILS_KEY,getWorkDeTails());
		//#CM695086
		// Start Work Date
		forwardParam.setParameter(ListWorkDayBusiness.RANGEWORKDAY_KEY,SystemParameter.RANGE_START);
		
		//#CM695087
		// Display the listbox for searching the "Work date".
		redirect("/system/listbox/listworkday/ListWorkDay.do", forwardParam, "/progress.do");

	}

	//#CM695088
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695089
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndWorkDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695090
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndWorkDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695091
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndWorkDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM695092
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndWorkDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM695093
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEdWk_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695094
	/** 
	 * Clicking on Search button of End Work Date invokes this.<BR>
	 * <BR>
	 * Summary: Sets the input data for the parameter and displays a listbox for searching the work date.<BR>
	 * <BR>
	 * [Parameter]
	 *  <DIR>
	 *  	End Work Date <BR>
	 *      Work type <BR>
	 *  </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchEdWk_Click(ActionEvent e) throws Exception
	{

		//#CM695095
		// Declare the instance to set the search conditions.
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM695096
		// Set the End Work Date.
		forwardParam.setParameter(
			ListWorkDayBusiness.ENDWORKDAY_KEY,
			WmsFormatter.toParamDate(txt_EndWorkDate.getDate()));
		//#CM695097
		// Work type
		forwardParam.setParameter(WORKDETAILS_KEY, getWorkDeTails());
        //#CM695098
        // End Work Date
		forwardParam.setParameter(ListWorkDayBusiness.RANGEWORKDAY_KEY,SystemParameter.RANGE_END);

		//#CM695099
		// Display the listbox for searching the "Work date".
		redirect("/system/listbox/listworkday/ListWorkDay.do", forwardParam, "/progress.do");
	}

	//#CM695100
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695101
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695102
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM695103
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM695104
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM695105
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchWkr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695106
	/** 
	 * Clicking on Search button of Worker Code invokes this.<BR>
	 * <BR>
	 * Summary: Searches for worker code.<BR>
	 * <BR>
	 * <DIR>
	 * [Parameter] <BR>
	 * <DIR> 
	 * 		Work type <BR>
	 * 		Start work date <BR>
	 * 		End Work Date <BR>
	 *      Worker Code <BR>
	 * </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchWkr_Click(ActionEvent e) throws Exception
	{
		//#CM695107
		// Set the search conditions into the Worker List screen.
		ForwardParameters param = new ForwardParameters();

		
		//#CM695108
		// Work type
		param.setParameter(WORKDETAILS_KEY, getWorkDeTails());
		//#CM695109
		// Start work date
		param.setParameter(ListWorkDayBusiness.STARTWORKDAY_KEY,WmsFormatter.toParamDate(txt_StartWorkDate.getDate()));
		//#CM695110
		// End Work Date
		param.setParameter(ListWorkDayBusiness.ENDWORKDAY_KEY,WmsFormatter.toParamDate(txt_EndWorkDate.getDate()));
		//#CM695111
		// Worker Code
		param.setParameter(ListWorkerBusiness.WORKERCODE_KEY, txt_WorkerCode.getText());
		//#CM695112
		// Search through the Worker table.
		param.setParameter(ListWorkerBusiness.SEARCHWORKER_KEY, SystemParameter.SEARCHFLAG_RESULT);

		//#CM695113
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listworker/ListWorker.do", param, "/progress.do");
	}

	//#CM695114
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_GroupCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695115
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_TermTotal_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695116
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_TermTotal_Click(ActionEvent e) throws Exception
	{
	}

	//#CM695117
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DailyTotal_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695118
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DailyTotal_Click(ActionEvent e) throws Exception
	{
	}

	//#CM695119
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Detail_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695120
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Detail_Click(ActionEvent e) throws Exception
	{
	}

	//#CM695121
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695122
	/** 
	 * Clicking on Display button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the field item entered in the Input area for the parameter and<BR>
	 *      Summary: Sets the field item entered in the Input area for the parameter and displays a listbox for showing the "Result list by worker" in the form of popup screen.<BR>
	 * <BR>
	 * <DIR>
	 * 1.Set the parameter in the input field. <BR>
	 * 2.Maintain the search conditions. <BR>
	 * 3.Display a listbox for showing the "Result list by worker". <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{

		//#CM695123
		// Listbox for showing the "Result list by worker"
		//#CM695124
		// Declare the instance to set the search conditions.
		ForwardParameters forwardParam = new ForwardParameters();
		//#CM695125
		// Set the Work Type.
		forwardParam.setParameter(WorkerQtyInquiryBusiness.WORKDETAILS_KEY, getWorkDeTails());
		//#CM695126
		// Set the Start Work Date.
		forwardParam.setParameter(
			ListWorkDayBusiness.STARTWORKDAY_KEY,
			WmsFormatter.toParamDate(txt_StartWorkDate.getDate()));
		//#CM695127
		// Set the End Work Date.
		forwardParam.setParameter(
			ListWorkDayBusiness.ENDWORKDAY_KEY,
			WmsFormatter.toParamDate(txt_EndWorkDate.getDate()));
		//#CM695128
		// Set the Worker Code.
		forwardParam.setParameter(ListWorkerBusiness.WORKERCODE_KEY, txt_WorkerCode.getText());
		//#CM695129
		// Find out to which option of Aggregation Conditions is ticked and set it as a condition if found.
		if (rdo_TermTotal.getChecked())
		{
			//#CM695130
			// Display the total within a period.
			forwardParam.setParameter(
				ListWorkerQtyInquiryBusiness.GROUPCONDITION_KEY,
				SystemParameter.SELECTAGGREGATECONDITION_TERM);
		}
		else if (rdo_DailyTotal.getChecked())
		{
			//#CM695131
			// Display the daily total.
			forwardParam.setParameter(
				ListWorkerQtyInquiryBusiness.GROUPCONDITION_KEY,
				SystemParameter.SELECTAGGREGATECONDITION_DAILY);
		}
		else
		{
			//#CM695132
			// Display the detail.
			forwardParam.setParameter(
				ListWorkerQtyInquiryBusiness.GROUPCONDITION_KEY,
				SystemParameter.SELECTAGGREGATECONDITION_DETAIL);
		}

		//#CM695133
		// Display a listbox for searching through the "Result list by worker".
		redirect("/system/listbox/listworkerqtyinquiry/ListWorkerQtyInquiry.do",forwardParam,"/progress.do");
	}

	//#CM695134
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695135
	/** 
	 * Clicking on Print button invokes this.<BR>
	 * <BR>
	 * Summary: Check for input and set the input field item for the parameter, and then start the schedule.<BR>
	 * Allow the schedule to return the number of the entered search conditions.<BR>
	 * <DIR>
	 * 1.Check for input (Mandatory Input, number of characters, and attribution of characters)<BR>
	 * 2.Check for input (Ensure that the value of the Start Work Date is smaller than the value of the End Work Date)<BR>
	 * 3.Set the parameter in the input field.<BR>
	 * 4.Start the print schedule and obtain the count of searched data.<BR>
	 * 5.Display a dialog box to allow to confirm it. it.<BR>
	 * "X (number) data matched. Do you print these data?"<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		//#CM695136
		// Set the cursor to Work Type.
		setFocus(pul_WorkDetails);
		//#CM695137
		// Check for input.
		txt_WorkerCode.validate(false);

		//#CM695138
		//Check wheter the value of the Start Work Date is smaller than the value of the End Work Date.
		if (!StringUtil.isBlank(txt_StartWorkDate.getDate())
			&& !StringUtil.isBlank(txt_EndWorkDate.getDate()))
		{
			if (txt_StartWorkDate.getDate().after(txt_EndWorkDate.getDate()))
			{
				//#CM695139
				// 6023118=Starting work date must precede the end work date.
				message.setMsgResourceKey("6023118");
				return;
			}
		}

		Connection conn = null;

		try
		{
			//#CM695140
			// Obtain the connection.
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM695141
			// Set the input value .
			SystemParameter param = createParameter();

			//#CM695142
			// Start the schedule.
			WmsScheduler schedule = new WorkQtyInquirySCH();

			//#CM695143
			// Start the schedule.	Obtain the count of printed data.	
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM695144
				// Compile the dialog messages.
				//#CM695145
				// MSG-W0061={0} data corresponded.<BR>Do you print it?
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM695146
				// Store the fact that the dialog was displayed via the Print button.
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM695147
				// Set the message
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
				//#CM695148
				// Close the connection.
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

	//#CM695149
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM695150
	/**
	 * Clicking on Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area.<BR>
	 * </BR>
	 * <DIR>
	 *        1.Return the item (count) in the input area to the initial value.<BR>
	 *        <DIR>
	 *        For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method. <BR>
	 *        </DIR>
	 *        2.Set the cursor for Worker code. <BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM695151
		// Set a default value for each Field item.
		//#CM695152
		// Clear the work type.
		pul_WorkDetails.clearItem() ;
		
		txt_StartWorkDate.setText("");
		txt_EndWorkDate.setText("");
		txt_WorkerCode.setText("");

		//#CM695153
		// Aggregation Conditions
		rdo_TermTotal.setChecked(false);
		//#CM695154
		// Display the daily total.
		rdo_DailyTotal.setChecked(true);
		rdo_Detail.setChecked(false);

		//#CM695155
		// Set the cursor to Work Type.
		setFocus(pul_WorkDetails);
		
		Connection conn = null;

		try
		{
			//#CM695156
			// Obtain the connection.
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM695157
			// Declare the schedule.
			WmsScheduler schedule = new WorkQtyInquirySCH();
			//#CM695158
			// Declare the parameter.
			SystemParameter [] sysParam =null;
			//#CM695159
			// Start the schedule.		
			sysParam = (SystemParameter [])schedule.query(conn, null);
		 
			if(sysParam != null)
			{
				for(int i= 0; i < sysParam.length ; i++)
				{
					//#CM695160
					// Generate pulldown options.
					PullDownItem pItem = new PullDownItem();
					//#CM695161
					// Set a field item for pulldown options.
					pItem.setValue(DisplayUtil.getSelectWorkDetail( sysParam[i].getSelectWorkDetail()));
					//#CM695162
					// Add it to the pulldown.
					pul_WorkDetails.addItem(pItem);
				}
				//#CM695163
				// Display the leading item in the pulldown.
				pul_WorkDetails.setSelectedIndex(0);
			}
			else
			{
				//#CM695164
				// 6006001=Unexpected error occurred.{0}{0}
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
				//#CM695165
				// Close the connection.
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}


}
//#CM695166
//end of class
