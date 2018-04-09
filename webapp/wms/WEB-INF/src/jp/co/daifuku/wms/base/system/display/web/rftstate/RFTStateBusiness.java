// $Id: RFTStateBusiness.java,v 1.2 2006/11/13 08:22:10 suresh Exp $

//#CM694919
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.rftstate;

import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.system.schedule.RFTStateSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM694920
/**
 * Designer :  A.Nagasawa <BR>
 * Maker :     A.Nagasawa <BR>
 * <BR>
 * Allow this class to provide a screen for RFT Work Status. <BR>
 * Display each RFT status from the schedule. <BR>
 * Allow the schedule to update the data to Not Worked. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Initial Display <BR>
 * <BR>
 * <DIR>
 *   Show the Initial Display. <BR>
 *   <DIR>
 *     Disable to display any work code with RFT status "Not Worked". <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Process by clicking "Redisplay" button(btn_View_Click(ActionEvent e)method)  <BR>
 * <BR>
 * <DIR>
 *   Display each RFT status.<BR>
 * 	 <DIR>
 *     Disable to display any work code with RFT status "Not Worked". <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 3.Select/Each Line button(lst_SRftWorkStatus_Click(ActionEvent e)method) <BR>
 * <BR>
 * <DIR>
 *   Display a listbox for executing maintenance of RFT status. <BR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/09</TD><TD>A.Nagasawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:22:10 $
 * @author  $Author: suresh $
 */
public class RFTStateBusiness extends RFTState implements WMSConstants
{
	//#CM694921
	// Class fields --------------------------------------------------
	//#CM694922
	// Address of the shift target.
	//#CM694923
	// RFT Work Status Maintenance
	private final static String DO_RFT_STATE = "/system/rftstate/RFTState2.do";
	
	//#CM694924
	// Searching in progress
	private final static String DO_PROCESS = "/progress.do";
	
	//#CM694925
	// Class variables -----------------------------------------------

	//#CM694926
	// Class method --------------------------------------------------

	//#CM694927
	// Constructors --------------------------------------------------

	//#CM694928
	// Public methods ------------------------------------------------

	//#CM694929
	/**
	 * Show the Initial Display.<BR>
	 * <BR>
	 * Start the schedule and display each RFT status. <BR>
	 * <DIR>
	 *   Disable to display any work code with RFT status "Not Worked". <BR>
	 * </DIR>
	 * @param e ActionEvent  A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{		
		//#CM694930
		//Process by clicking on "Display" button
		btn_View_Click(e);
	}	

	//#CM694931
	/**
	 * Invoke this before invoking each control event.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM694932
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM694933
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM694934
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM694935
			//Set the path to the help file.
			//#CM694936
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
	}

	//#CM694937
	/**
	 * Returning from a popup window invokes this method.
	 * Override the page_DlgBack defined for Page.
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		//#CM694938
		//Process by clicking on "Display" button
		btn_View_Click(e);
	}

	//#CM694939
	// Package methods -----------------------------------------------

	//#CM694940
	// Protected methods ---------------------------------------------

	//#CM694941
	// Private methods -----------------------------------------------
	//#CM694942
	/**
	 * Set the value for the list cell.
	 * @param viewParam Value to be set in a list cell.
	 * @throws Exception Report all exceptions.
	 */
	private void setList(Parameter[] listParams) throws Exception
	{
		//#CM694943
		// Set the data in the list cell.
		lst_SRftWorkStatus.clearRow();
		SystemParameter[] viewParam = (SystemParameter[])listParams;
		
		for (int i = 0; i < viewParam.length; i++)
		{
			//#CM694944
			// Feed a line.
			lst_SRftWorkStatus.setCurrentRow(i + 1);
			lst_SRftWorkStatus.addRow();
	
			//#CM694945
			// Set the data in the list cell.
			lst_SRftWorkStatus.setValue(0,WmsFormatter.toParamDate(viewParam[i].getLastUpdateDate()));
			lst_SRftWorkStatus.setValue(1,Integer.toString(i+1));			
			lst_SRftWorkStatus.setValue(2,viewParam[i].getRftNo());
			lst_SRftWorkStatus.setValue(3,viewParam[i].getTerminalType());
			lst_SRftWorkStatus.setValue(4,viewParam[i].getRftStatus());
			lst_SRftWorkStatus.setValue(5,viewParam[i].getDisplayWorkerCode());
			lst_SRftWorkStatus.setValue(6,viewParam[i].getWorkerName());
		}
	}	
	//#CM694946
	// Event handler methods -----------------------------------------
	//#CM694947
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694948
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694949
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694950
	/** 
	 * Clicking on Menu button invokes this.<BR>
	 * <DIR>
	 *   Shift to the Menu screen.<BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM694951
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRftWorkStatus_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694952
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRftWorkStatus_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694953
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRftWorkStatus_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694954
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRftWorkStatus_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM694955
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRftWorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694956
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRftWorkStatus_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694957
	/** 
	 * Clicking on "Select" button of RFTS Work Status invokes this. <BR>
	 * <BR>
	 * Summary: Display a popup of the RFT Work Status Maintenance screen and displays the RFT status corresponding of the selected line.  <BR>
	 * <BR>
	 * <DIR>
	 *   Display the RFT Work Status screen.<BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions. 
	 */
	public void lst_SRftWorkStatus_Click(ActionEvent e) throws Exception
	{
		//#CM694958
		// Obtain the current line.
		int index = lst_SRftWorkStatus.getActiveRow();
		lst_SRftWorkStatus.setCurrentRow(index);

		//#CM694959
		// Declare the instance to set the search conditions.
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM694960
		// Set the RFT No.
		forwardParam.setParameter(
			RFTState2Business.RFTNO_KEY,
			lst_SRftWorkStatus.getValue(2));

		//#CM694961
		// Set the Terminal Type.
		forwardParam.setParameter(
			RFTState2Business.TERMINALTYPE_KEY,
			URLEncoder.encode(lst_SRftWorkStatus.getValue(3),"UTF-8"));

		//#CM694962
		// Set the RFT status.
		forwardParam.setParameter(
			RFTState2Business.RFTSTATUS_KEY,
			URLEncoder.encode(lst_SRftWorkStatus.getValue(4),"UTF-8"));
		
		//#CM694963
		// Set the Worker Code.
		forwardParam.setParameter(
			RFTState2Business.WORKERCODE_KEY,
			lst_SRftWorkStatus.getValue(5));

		//#CM694964
		// Set the Worker Name.
		if (lst_SRftWorkStatus.getValue(6) != null)
		{
			forwardParam.setParameter(
					RFTState2Business.WORKERNAME_KEY,
					URLEncoder.encode(lst_SRftWorkStatus.getValue(6),"UTF-8"));
		}
		else
		{
			forwardParam.setParameter(
					RFTState2Business.WORKERNAME_KEY,
					"");
		}

		//#CM694965
		// Display the RFT Work Status Maintenance screen.
		redirect(DO_RFT_STATE, forwardParam, DO_PROCESS);
		
	}

	//#CM694966
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694967
	/** 
	 * Clicking on Display button of RFT Work Status invokes this. <BR>
	 * <BR>
	 * Summary: Display each RFT status.  <BR>
	 * <BR>
	 * <DIR>
	 *   Start the schedule. <BR>
	 * 	 <DIR>
	 *     Disable to display any work code with RFT status "Not Worked". <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			SystemParameter param = new SystemParameter();
			//#CM694968
			// Obtain Connection	 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			//#CM694969
			// Start the schedule.
			WmsScheduler schedule = new RFTStateSCH();
			SystemParameter[] viewParam = (SystemParameter[])schedule.query(conn, param);
		
			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			
			message.setMsgResourceKey(schedule.getMessage());
			//#CM694970
			// Set the list cell.
			setList(viewParam);			
	
		 }
		 catch (Exception ex)
		 {
			 message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		 }
		 finally
		 {
			 try
			 {
				 //#CM694971
				 // Close the connection.
				 if (conn != null)
					 conn.close();
			 }
			 catch (SQLException se)
			 {
				 message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			 }
		 }
	}
}
//#CM694972
//end of class
