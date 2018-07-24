// $Id: DeadStockListBusiness.java,v 1.2 2006/10/04 05:03:56 suresh Exp $

//#CM3981
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.deadstocklist;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockdeadstock.ListStockDeadStockBusiness;
import jp.co.daifuku.wms.stockcontrol.schedule.DeadStockListSCH;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;


//#CM3982
/**
 * Designer : Yoshino<BR>
 * Maker : Yoshino <BR>
 * 
 * This is old inventory list print screen class.<BR>
 * Pass the parameter to the schedule class that executes old inventory list print.<BR>
 * <BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * 1.Display button press down process(<CODE>btn_PDisplay_Click()</CODE> Method)<BR>
 * <BR>
 * <DIR>Set the contents input from the screen to the parameter, and the schedule search the data for display based on the parameter. <BR>
 * Receive the data for old inventory (display) screen from schedule and invoke old inventory (display) screen.<BR>
 * <BR>
 * [parameter] *Mandatory Input<BR>
 * Consignor code*<BR>
 * Storage Date*<BR>
 * <BR>
 * </DIR><BR>
 * 2.Print button press down process(<CODE>btn_Print_Click()</CODE>Method)<BR>
 * <BR>
 * <DIR>Set the contents input from the screen to the parameter, and the schedule search and print the data based on the parameter. <BR>
 * Return true when the schedule completed the print successfully <BR>
 * <BR>
 * [parameter] *Mandatory Input<BR>
 * Consignor code*<BR>
 * Storage Date*<BR>
 * </DIR> <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD></TR>
 * <TR><TD>2004/09/30</TD>
 * <TD>T.Yoshino</TD>
 * <TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:03:56 $
 * @author  $Author: suresh $
 */
public class DeadStockListBusiness extends DeadStockList implements WMSConstants
{
	//#CM3983
	// Class fields --------------------------------------------------
	
	//#CM3984
	// Maintain the control that invokes the dialog: Print button
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";

	//#CM3985
	// Class variables -----------------------------------------------

	//#CM3986
	// Class method --------------------------------------------------

	//#CM3987
	// Constructors --------------------------------------------------

	//#CM3988
	// Public methods ------------------------------------------------
	//#CM3989
	/**
	 * Initialize the screen<BR>
	 * <DIR>
	 * Clicking on the Summary: Display the initial display. Clear button allows not to invoke page_load. <BR>
	 * <BR>
	 * Item name [Initial value]<BR>
	 * <DIR>
	 * Consignor code [Execute initial display when there is only one corresponding Consignor] <BR>
	 * Storage Date [None]<BR>
	 * </DIR>
	 * <BR>
	 *  Set the focus for the Consignor code.<BR>
	 * </DIR>
	 *
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		
		//#CM3990
		// Set the initial value for each input field.
		//#CM3991
		// Consignor code
		txt_ConsignorCode.setText(getConsignorCode());

		//#CM3992
		// Move the cursor to the Consignor code.
		setFocus(txt_ConsignorCode);
	}
	//#CM3993
	/**
	* Invoke this before invoking each control event.<BR>
	* <BR>
	* <DIR>
	*  Summary: Displays a dialog. <BR>
	* </DIR>
	* 
	* @param e ActionEvent This is the class to store event info.
	* @throws Exception Reports all the exceptions.
	*/
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM3994
			// Obtain parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM3995
			// Store to ViewState
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM3996
			// Set the screen name
			lbl_SettingName.setResourceKey(title);
		}

	}
	
	//#CM3997
	/**
	 * Returning from the Dialog button invokes this method.
	 * Override page_ConfirmBack defined on the page.
	 * <BR>
	 * Summary: When select "yes" in the dialog<BR>
	 * <BR>
	 * 1.Set the focus for the Consignor code.<BR>
	 * 2.Check which dialog returns it.<BR>
	 * 3.Check for selection of "Yes " or "No " clicked in the dialog box.<BR>
	 * 4.Selecting "Yes " starts the schedule.<BR>
	 * 5.In the case of print dialog<BR>
	 *   <DIR>
	 *   5-1.Set the parameter in the input field.<BR>
	 *   5-2.Start print schedule.<BR>
	 *   5-3.Show schedule result to the message area.<BR>
	 *	 </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		try
		{
			//#CM3998
			// Set the focus for the Consignor code.
			setFocus(txt_ConsignorCode);

			//#CM3999
			// Check which dialog returns it.
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM4000
			// Clicking "Yes" in the dialog box returns true.
			//#CM4001
			// Clicking "No" in the dialog box returns false.
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM4002
			// Clicking "NO " button closes the process.
			//#CM4003
			// Message set here is not necessary
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
			//#CM4004
			// Turn the flag off after closed the operation of the dialog.
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM4005
		// Start print schedule
		Connection conn = null;
		try
		{
			//#CM4006
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM4007
			// Set the input value for the parameter.
			StockControlParameter[] param = new StockControlParameter[1];
			param[0] = createParameter();

			//#CM4008
			// Start schedule
			WmsScheduler schedule = new DeadStockListSCH();
			schedule.startSCH(conn, param);
			
			//#CM4009
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
				//#CM4010
				// Close the connection
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
	
	//#CM4011
	/**
	 * Invoke this method when returning from the popup window
	 * Override page_DlgBack defined on the page.
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM4012
		// Obtain the parameter selected in the listbox.
		//#CM4013
		// Consignor code
		String consignorcode = param.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);

		//#CM4014
		// Set the value if not empty.
		//#CM4015
		// Consignor code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}

		//#CM4016
		// Set the focus for the Consignor code.
		setFocus(txt_ConsignorCode);
	}
	//#CM4017
	// Package methods -----------------------------------------------

	//#CM4018
	// Protected methods ---------------------------------------------
	//#CM4019
	/** 
	 * Set the value input via screen for parameter.<BR>
	 * @return Parameter object including input value of Input area.<BR> 
	 */
	protected StockControlParameter createParameter()
  	{
		StockControlParameter param = new StockControlParameter();
		
		//#CM4020
		// Consignor code
	 	param.setConsignorCode(txt_ConsignorCode.getText());
		//#CM4021
		// Storage Date
		param.setStorageDate(txt_StorageDate.getDate());
		return param;
 	 }
	
	//#CM4022
	// Private methods -----------------------------------------------
	//#CM4023
	/**
	 * This method obtains the Consignor code from the schedule on the initial display. <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule. <BR>
	 * 
	 * @return Consignor code <BR>
	 *         Return the string of the Consignor code when one corresponding data exists. <BR>
	 *         Return empty character when 0 or multiple corresponding data exist. <BR>
	 * 
	 * @throws Exception
	 *             Reports all the exceptions.
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StockControlParameter param = new StockControlParameter();

			//#CM4024
			// Obtain the Consignor code from the schedule.
			WmsScheduler schedule = new DeadStockListSCH();
			param = (StockControlParameter) schedule.initFind(conn, param);

			if (param != null)
			{
				return param.getConsignorCode();
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			//#CM4025
			// Close the connection
			if (conn != null)
			{
				conn.close();
			}
		}
		return "";
	}
	//#CM4026
	// Event handler methods -----------------------------------------
	//#CM4027
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4028
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4029
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4030
	/** 
	 * Clicking on the Clicking the Menu button invokes this.<BR>
	 * Summary: Move to menu screen.<BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM4031
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4032
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4033
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM4034
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM4035
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM4036
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4037
	/** 
	 * Clicking on the Clicking the Consignor search button invokes this.<BR>
	 * Summary: This Method set search conditions to the parameter, and<BR>
	 * Display the Consignor list search listbox using the search conditions.<BR>
	 * [parameter] *Mandatory Input
	 * <DIR>
	 * Consignor code<BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		//#CM4038
		// Set the search condition for the Search Consignor screen.
		ForwardParameters param = new ForwardParameters();
		//#CM4039
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM4040
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststockconsignor/ListStockConsignor.do",
			param,
			"/progress.do");
	}

	//#CM4041


	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4042
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4043
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM4044
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM4045
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Osil_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4046
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4047
	/** 
	 * Clicking on the Display button invokes this.<BR>
	 * Summary: Sets the input item (count) in the input area for the parameter and displays old inventory list listbox in another screen.<BR>
	 * Display Listbox.<BR><BR>
	 * [parameter] *Mandatory Input<BR>
	 * <DIR>
	 * Consignor code*<BR>
	 * Storage Date*<BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		//#CM4048
		// Declare the instance to set the search conditions.
		ForwardParameters forwardParam = new ForwardParameters();
		//#CM4049
		// Set Consignor code
		forwardParam.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM4050
		// Set Storage Date
		forwardParam.setParameter(ListStockDeadStockBusiness.STORAGEDATE_KEY, WmsFormatter.toParamDate(txt_StorageDate.getDate()));	
		//#CM4051
		// Display the old inventory list search listbox.
		redirect("/stockcontrol/listbox/liststockdeadstock/ListStockDeadStock.do", forwardParam, "/progress.do");
	}

	//#CM4052
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4053
	/** 
	 * Clicking on the Print button invokes this.<BR>
	 * Summary: Sets the input item (count) in the input area for the parameter and obtains the print count
	 * Displays the dialog box to allow to confirm to print it or not.<BR>
	 * <BR>
	 * 1.Check for input and the count of print targets.<BR>
	 * 2-1.Displays the dialog box to allow to confirm it if the print target data found.<BR>
	 * <DIR>
	 *   "There are xxx (number) target data. Do you print it?"<BR>
	 * </DIR>
	 * 2-2.If no print target data<BR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		//#CM4054
		// Set the focus for the Consignor code.
		setFocus(txt_ConsignorCode);
		//#CM4055
		// Execute input check.
		if(!checkInputData())
		{
			return;
		}

		Connection conn = null;
		try
		{
			//#CM4056
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM4057
			// Set the schedule parameter
			StockControlParameter param = createParameter();
			
			//#CM4058
			// Start schedule
			WmsScheduler schedule = new DeadStockListSCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM4059
				// Hit MSG-W0061={0} count(s)<BR>Do you print it out?
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM4060
				// Store the fact that the dialog was displayed via the Print button.
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM4061
				// Set the message
				message.setMsgResourceKey(schedule.getMessage());
			}
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM4062
				// Close the connection
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

	//#CM4063
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4064
	/**
	 * Clicking on the Clear button invokes this.<BR>
	 * Summary: Clears the input area.<BR>
	 * <BR>
	 * <DIR>
	 * Subject name
	 *  <DIR>
	 *  	Consignor code  [Show prior one page when there is only one corresponding Consignor]<BR>
	 *      Storage Date      [None]<BR>
	 *  </DIR>
	 *  <BR>
	 *  Initialize the focus for the Consignor code.<BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM4065
		// Set the initial value.
		//#CM4066
		// Consignor code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM4067
		// Storage Date
		txt_StorageDate.setText("");

		//#CM4068
		// Set the focus for the Consignor code.
		setFocus(txt_ConsignorCode);
	}

	//#CM4069
	/** 
	 * This method checks for input.<BR>
	 * Correct input returns true<BR>
	 * <BR>
	 * 
	 * @throws Exception Reports all the exceptions.
	 * 
	 * @return Result of input check(true:OK  false:NG)
	 */
	private boolean checkInputData() throws Exception
	{
		//#CM4070
		// input check
		//#CM4071
		// Consignor code
		txt_ConsignorCode.validate();
		//#CM4072
		// Start shipping planned date
		txt_StorageDate.validate();

		return true;

	}
}
//#CM4073
//end of class
