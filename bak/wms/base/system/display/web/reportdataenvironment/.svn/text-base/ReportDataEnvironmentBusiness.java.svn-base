// $Id: ReportDataEnvironmentBusiness.java,v 1.2 2006/11/13 08:21:37 suresh Exp $

//#CM694017
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.reportdataenvironment;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

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
import jp.co.daifuku.wms.base.system.display.web.listbox.listfolderselect.ListFolderSelectBusiness;
import jp.co.daifuku.wms.base.system.schedule.ReportDataEnvironmentSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM694018
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this class to report data (for setting the environment).<BR>
 * Set the contents entered via screen for the parameter.<BR>
 * Allow the schedule to set the loaded data storage folder and the file name in the EnvironmentInformation file based on the parameter.<BR>
 * Receive the result from the schedule. Receive true if the process completed normally.<BR>
 * Or, receive false if the schedule failed to complete due to condition error etc.<BR>
 * As the result of the schedule, output the message obtained from the schedule to the screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process by clicking "Add" button(<CODE>btn_Submit_Click()</CODE>method)<BR>
 * <BR>
 * <DIR>
 * 	Set the contents input via screen for the EnvironmentInformation file.<BR>
 * 	Return an error message if condition error etc. occurs.<BR>
 * <BR>
 * 	[Parameter] *Mandatory Input<BR>
 * 	<BR>
 * 	<DIR>
 * 		Worker Code* <BR>
 * 		Password* <BR>
 * 		Receiving result data storage folder* <BR>
 * 		Receiving result data file name* <BR>
 * 		Storage result data storage folder* <BR>
 * 		Storage result data file name* <BR>
 * 		Picking result data storage folder* <BR>
 * 		Picking result data file name* <BR>
 * 		Sorting result data storage folder* <BR>
 * 		Sorting result data file name* <BR>
 * 		Shipping result data storage folder* <BR>
 * 		Shipping result data file name* <BR>
 * 		Stock relocation result data storage folder* <BR>
 *		Stock relocation result data file name* <BR>
 *		Inventory Check result data storage folder* <BR>
 *		Inventory Check result data file name* <BR>
 *		Unplanned storage data storage folder* <BR>
 *		Unplanned storage data file name* <BR>
 *		Unplanned picking data storage folder* <BR>
 *		Unplanned picking data file name* <BR>
 * 	</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/06</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:37 $
 * @author  $Author: suresh $
 */
public class ReportDataEnvironmentBusiness extends ReportDataEnvironment implements WMSConstants
{
	//#CM694019
	// Class fields --------------------------------------------------
	//#CM694020
	/** 
	 * button flag (Shipping) 
	 */
	public static final String REPORT_INSTOCK = "21";

	//#CM694021
	/** 
	 * button flag (Storage)
	 */
	public static final String REPORT_STORAGE = "22";

	//#CM694022
	/** 
	 * button flag (Picking)
	 */
	public static final String REPORT_RETRIEVAL = "23";

	//#CM694023
	/** 
	 * button flag (Picking)
	 */
	public static final String REPORT_SHIPPING = "24";

	//#CM694024
	/**
	 * button flag (Sorting)
	 */
	public static final String REPORT_PICKING = "25";

	//#CM694025
	/** 
	 * button flag (Stock Relocation)
	 */
	public static final String REPORT_MOVE = "26";

	//#CM694026
	/**
	 * button flag (Inventory Check)
	 */
	public static final String REPORT_INVENTORY = "27";
	
	//#CM694027
	/** 
	 * button flag (Unplanned Storage)
	 */
	public static final String REPORT_NOPLANSTORAGE = "28";

	//#CM694028
	/** 
	 * button flag (Unplanned Picking)
	 */
	public static final String REPORT_NOPLANRETRIEVAL = "29";
	
	//#CM694029
	// Class variables -----------------------------------------------

	//#CM694030
	// Class method --------------------------------------------------

	//#CM694031
	// Constructors --------------------------------------------------

	//#CM694032
	// Public methods ------------------------------------------------

	//#CM694033
	/**
	 * Initialize the screen.<BR>
	 * <BR>
	 * Field item [Initial Value]<BR>
	 * <DIR>
	 *	Receiving result data storage folder		[Obtain from EnvironmentInformation] <BR>
	 *	Receiving result data file name		[Obtain from EnvironmentInformation] <BR>
	 *	Storage result data storage folder		[Obtain from EnvironmentInformation] <BR>
	 *	Storage result data file name		[Obtain from EnvironmentInformation] <BR>
	 *	Picking result data storage folder		[Obtain from EnvironmentInformation] <BR>
	 *	Picking result data file name		[Obtain from EnvironmentInformation] <BR>
	 *	Sorting result data storage folder		[Obtain from EnvironmentInformation] <BR>
	 *	Sorting result data file name		[Obtain from EnvironmentInformation] <BR>
	 *	Shipping result data storage folder		[Obtain from EnvironmentInformation] <BR>
	 *	Shipping result data file name		[Obtain from EnvironmentInformation] <BR>
	 *	Stock relocation result data storage folder	[Obtain from EnvironmentInformation] <BR>
	 *	Stock relocation result data file name	[Obtain from EnvironmentInformation] <BR>
	 *	Inventory Check result data storage folder	[Obtain from EnvironmentInformation] <BR>
	 *	Inventory Check result data file name		[Obtain from EnvironmentInformation] <BR>
	 *	Unplanned storage data storage folder	[Obtain from EnvironmentInformation] <BR>
	 *	Unplanned storage data file name		[Obtain from EnvironmentInformation] <BR>
	 *	Unplanned picking data storage folder	[Obtain from EnvironmentInformation] <BR>
	 *	Unplanned picking data file name		[Obtain from EnvironmentInformation] <BR>
	 * </DIR>
	 * 1.Set the cursor for Worker code.
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM694034
		// Show the Initial Display.
		StartDisp();
	}

	//#CM694035
	/**
	 * Invoke this before invoking each control event. <BR>
	 * <BR>
	 * Summary: Allow this method to execute the following processes.<BR>
	 * <BR>
	 * <DIR>
	 *        Clicking on Add button displays a dialog. <BR>
	 * <DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM694036
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM694037
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM694038
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM694039
			//Set the path to the help file.
			//#CM694040
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM694041
		// Display the dialog for confirming to add.
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}

	//#CM694042
	/**
	 * Override the page_DlgBack defined for Page.
	 *Invoke this method to return after clicking on "Search" button.
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		//#CM694043
		// Set focus for Worker Code. 
		setFocus(txt_WorkerCode);
		
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM694044
		// Obtain the parameter selected in the listbox.
		String folder = param.getParameter(ListFolderSelectBusiness.FOLDER_KEY);
		String btnflug = param.getParameter(ListFolderSelectBusiness.BTNFLUG_KEY);

		if (btnflug != null && !btnflug.equals(""))
		{
			if (btnflug.equals(REPORT_INSTOCK))
			{
				//#CM694045
				// Set a value if the folder for storing the Receiving result data is not empty.
				if (!StringUtil.isBlank(folder))
				{
					txt_InstkRsltFld.setText(folder);
				}
			}
			else if (btnflug.equals(REPORT_STORAGE))
			{
				//#CM694046
				// Set a value if the folder for storing the Storage result data is not empty.
				if (!StringUtil.isBlank(folder))
				{
					txt_StrgRsltFld.setText(folder);
				}
			}
			else if (btnflug.equals(REPORT_RETRIEVAL))
			{
				//#CM694047
				// Set a value if the folder for storing the Picking result data is not empty.
				if (!StringUtil.isBlank(folder))
				{
					txt_RtrivlRsltFld.setText(folder);
				}
			}
			else if (btnflug.equals(REPORT_PICKING))
			{
				//#CM694048
				// Set a value if the folder for storing the Sorting result data is not empty.
				if (!StringUtil.isBlank(folder))
				{
					txt_PickRsltFld.setText(folder);
				}
			}
			else if (btnflug.equals(REPORT_SHIPPING))
			{
				//#CM694049
				// Set a value if the folder for storing the Shipping result data is not empty.
				if (!StringUtil.isBlank(folder))
				{
					txt_ShpRsltFld.setText(folder);
				}
			}
			else if (btnflug.equals(REPORT_MOVE))
			{
				//#CM694050
				// Set a value if the folder for storing the Stock relocation data is not empty.
				if (!StringUtil.isBlank(folder))
				{
					txt_StockMovRsltFld.setText(folder);
				}
			}
			else if (btnflug.equals(REPORT_INVENTORY))
			{
				//#CM694051
				// Set a value if the folder for storing the Inventory Check data is not empty.
				if (!StringUtil.isBlank(folder))
				{
					txt_InvntryRsltFld.setText(folder);
				}
			}
			else if (btnflug.equals(REPORT_NOPLANSTORAGE))
			{
				//#CM694052
				// Set a value if the folder for storing the Unplanned storage result data is not empty.
				if (!StringUtil.isBlank(folder))
				{
					txt_NoPlanStrgFld.setText(folder);
				}
			}
			else if (btnflug.equals(REPORT_NOPLANRETRIEVAL))
			{
				//#CM694053
				// Set a value if the folder for storing the Unplanned picking result data is not empty.
				if (!StringUtil.isBlank(folder))
				{
					txt_NoPlanRtrivlFld.setText(folder);
				}
			}			
		}
	}

	//#CM694054
	// Package methods -----------------------------------------------

	//#CM694055
	// Protected methods ---------------------------------------------

	//#CM694056
	// Private methods -----------------------------------------------
	//#CM694057
	/**
	 * Show the Initial Display.
	 * @throws Exception Report all exceptions.
	 */
	private void StartDisp() throws Exception
	{
		//#CM694058
		// Set focus for Worker Code. 
		setFocus(txt_WorkerCode);
		Connection conn = null;
		try
		{
			//#CM694059
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new ReportDataEnvironmentSCH();
			//#CM694060
			// Read the environment info for Initial Display.
			SystemParameter param = (SystemParameter) schedule.initFind(conn, null);

			//#CM694061
			// Enable to input only for the field items that contain packages.
			//#CM694062
			// Receiving result data
			if (!param.getSelectReportInstockData())
			{
				txt_InstkRsltFld.setReadOnly(true);
				txt_InstkRsltFileNm.setReadOnly(true);
				btn_ReferenceInstk.setEnabled(false);
			}
			else
			{
				//#CM694063
				//Receiving result data storage folder
				txt_InstkRsltFld.setText(param.getFolder_ReportInstockData());
				//#CM694064
				// Receiving result data file name
				txt_InstkRsltFileNm.setText(param.getFileName_ReportInstockData());
			}
			//#CM694065
			// Storage result data
			if (!param.getSelectReportStorageData())
			{
				txt_StrgRsltFld.setReadOnly(true);
				txt_StrgRsltFileNm.setReadOnly(true);
				btn_ReferenceStrg.setEnabled(false);
			}
			else
			{
				//#CM694066
				// Storage result data storage folder
				txt_StrgRsltFld.setText(param.getFolder_ReportStorageData());
				//#CM694067
				// Storage result data file name
				txt_StrgRsltFileNm.setText(param.getFileName_ReportStorageData());
			}
			//#CM694068
			// Picking result data
			if (!param.getSelectReportRetrievalData())
			{
				txt_RtrivlRsltFld.setReadOnly(true);
				txt_RtrivlRsltFileNm.setReadOnly(true);
				btn_ReferenceRtrivl.setEnabled(false);
			}
			else
			{
				//#CM694069
				// Picking result data storage folder
				txt_RtrivlRsltFld.setText(param.getFolder_ReportRetrievalData());
				//#CM694070
				// Picking result data file name
				txt_RtrivlRsltFileNm.setText(param.getFileName_ReportRetrievalData());
			}
			//#CM694071
			// Sorting result data
			if (!param.getSelectReportSortingData())
			{
				txt_PickRsltFld.setReadOnly(true);
				txt_PickRsltFileNm.setReadOnly(true);
				btn_ReferencePick.setEnabled(false);
			}
			else
			{
				//#CM694072
				// Sorting result data storage folder
				txt_PickRsltFld.setText(param.getFolder_ReportSortingData());
				//#CM694073
				// Sorting result data file name
				txt_PickRsltFileNm.setText(param.getFileName_ReportSortingData());
			}
			//#CM694074
			// Shipping result data
			if (!param.getSelectReportShippingData())
			{
				txt_ShpRsltFld.setReadOnly(true);
				txt_ShpRsltFileNm.setReadOnly(true);
				btn_ReferenceShp.setEnabled(false);
			}
			else
			{
				//#CM694075
				// Shipping result data storage folder
				txt_ShpRsltFld.setText(param.getFolder_ReportShippingData());
				//#CM694076
				// Shipping result data file name
				txt_ShpRsltFileNm.setText(param.getFileName_ReportShippingData());
			}
			//#CM694077
			// Stock relocation result data
			if (!param.getSelectReportMovementData())
			{
				txt_StockMovRsltFld.setReadOnly(true);
				txt_StockMovRsltFileNm.setReadOnly(true);
				btn_ReferenceStock.setEnabled(false);
			}
			else
			{
				//#CM694078
				// Stock relocation result data storage folder
				txt_StockMovRsltFld.setText(param.getFolder_ReportMovementData());
				//#CM694079
				// Stock relocation result data file name
				txt_StockMovRsltFileNm.setText(param.getFileName_ReportMovementData());
			}
			//#CM694080
			// Inventory Check result data
			if (!param.getSelectReportInventoryData())
			{
				txt_InvntryRsltFld.setReadOnly(true);
				txt_InvntryRsltFileNm.setReadOnly(true);
				btn_ReferenceInventory.setEnabled(false);
			}
			else
			{
				//#CM694081
				// Inventory Check result data storage folder
				txt_InvntryRsltFld.setText(param.getFolder_ReportInventoryData());
				//#CM694082
				// Inventory Check result data file name
				txt_InvntryRsltFileNm.setText(param.getFileName_ReportInventoryData());
			}
			//#CM694083
			// Unplanned storage result data
			if (!param.getSelectReportNoPlanStorageData())
			{
				txt_NoPlanStrgFld.setReadOnly(true);
				txt_NoPlanStrgFileNm.setReadOnly(true);
				btn_ReferenceNoPlanStrg.setEnabled(false);
			}
			else
			{
				//#CM694084
				// Unplanned storage result data storage folder
				txt_NoPlanStrgFld.setText(param.getFolder_ReportNoPlanStorageData());
				//#CM694085
				// Unplanned storage result data file name
				txt_NoPlanStrgFileNm.setText(param.getFileName_ReportNoPlanStorageData());
			}
			//#CM694086
			// Unplanned picking result data
			if (!param.getSelectReportNoPlanRetrievalData())
			{
				txt_NoPlanRtrivlFld.setReadOnly(true);
				txt_NoPlanRtrivlFileNm.setReadOnly(true);
				btn_ReferenceNoPlanRtrivl.setEnabled(false);
			}
			else
			{
				//#CM694087
				// Unplanned picking result data storage folder
				txt_NoPlanRtrivlFld.setText(param.getFolder_ReportNoPlanRetrievalData());
				//#CM694088
				// Unplanned picking result data file name
				txt_NoPlanRtrivlFileNm.setText(param.getFileName_ReportNoPlanRetrievalData());
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM694089
				// Close the connection.
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

	//#CM694090
	// Event handler methods -----------------------------------------
	//#CM694091
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694092
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694093
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694094
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

	//#CM694095
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694096
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694097
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694098
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694099
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694100
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694101
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694102
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694103
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694104
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694105
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InstkRsltFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694106
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkRsltFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694107
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkRsltFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694108
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkRsltFld_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694109
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkRsltFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694110
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferenceInstk_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694111
	/** 
	 * Clicking on Refer button of the Receiving result data storage folder invokes this.<BR>
	 * Summary: Displays the view of Receiving result data storage folder and select a folder to store it.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ReferenceInstk_Click(ActionEvent e) throws Exception
	{
		//#CM694112
		// Set the parameter to be passed to the next screen.
		ForwardParameters param = new ForwardParameters();
		//#CM694113
		// Receiving result data storage folder
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_InstkRsltFld.getText());
		//#CM694114
		// "Refer" button flag
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, REPORT_INSTOCK);

		//#CM694115
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");
	}

	//#CM694116
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InstkRsltFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694117
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkRsltFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694118
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkRsltFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694119
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkRsltFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694120
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkRsltFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694121
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrgRsltFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694122
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694123
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694124
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltFld_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694125
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694126
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferenceStrg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694127
	/** 
	 * Clicking on Refer button of the Storage result data storage folder invokes this.<BR>
	 * Summary: Displays the view of Storage result data storage folder and select a folder to store it.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ReferenceStrg_Click(ActionEvent e) throws Exception
	{
		//#CM694128
		// Set the parameter to be passed to the next screen.
		ForwardParameters param = new ForwardParameters();
		//#CM694129
		// Storage result data storage folder
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_StrgRsltFld.getText());
		//#CM694130
		// "Refer" button flag
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, REPORT_STORAGE);

		//#CM694131
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");
	}

	//#CM694132
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrgRsltFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694133
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694134
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694135
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694136
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgRsltFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694137
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RtrivlRsltDataFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694138
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694139
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694140
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltFld_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694141
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694142
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferenceRtrivl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694143
	/** 
	 * Clicking on Refer button of the Picking result data storage folder invokes this.<BR>
	 * Summary: Displays the view of Picking result data storage folder and select a folder to store it.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ReferenceRtrivl_Click(ActionEvent e) throws Exception
	{
		//#CM694144
		// Set the parameter to be passed to the next screen.
		ForwardParameters param = new ForwardParameters();
		//#CM694145
		// Picking result data storage folder
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_RtrivlRsltFld.getText());
		//#CM694146
		// "Refer" button flag
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, REPORT_RETRIEVAL);

		//#CM694147
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");
	}

	//#CM694148
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RtrivlRsltFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694149
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694150
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694151
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694152
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlRsltFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694153
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PickRsltDataFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694154
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickRsltFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694155
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickRsltFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694156
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickRsltFld_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694157
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickRsltFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694158
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferencePick_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694159
	/** 
	 * Clicking on Refer button of the Sorting result data storage folder invokes this.<BR>
	 * Summary: Displays the view of Sorting result data storage folder and select a folder to store it.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ReferencePick_Click(ActionEvent e) throws Exception
	{
		//#CM694160
		// Set the parameter to be passed to the next screen.
		ForwardParameters param = new ForwardParameters();
		//#CM694161
		// Sorting result data storage folder
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_PickRsltFld.getText());
		//#CM694162
		// "Refer" button flag
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, REPORT_PICKING);

		//#CM694163
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");
	}

	//#CM694164
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PickRsltDataFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694165
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickRsltFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694166
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickRsltFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694167
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickRsltFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694168
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickRsltFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694169
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ShpRsltFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694170
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpRsltFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694171
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpRsltFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694172
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpRsltFld_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694173
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpRsltFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694174
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferenceShp_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694175
	/** 
	 * Clicking on Refer button of the Shipping result data storage folder invokes this.<BR>
	 * Summary: Displays the view of Shipping result data storage folder and select a folder to store it.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ReferenceShp_Click(ActionEvent e) throws Exception
	{
		//#CM694176
		// Set the parameter to be passed to the next screen.
		ForwardParameters param = new ForwardParameters();
		//#CM694177
		// Shipping result data storage folder
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_ShpRsltFld.getText());
		//#CM694178
		// "Refer" button flag
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, REPORT_SHIPPING);

		//#CM694179
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");
	}

	//#CM694180
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ShpRsltFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694181
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpRsltFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694182
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpRsltFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694183
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpRsltFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694184
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpRsltFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694185
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StockMovRsltFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694186
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockMovRsltFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694187
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockMovRsltFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694188
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockMovRsltFld_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694189
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockMovRsltFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694190
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferenceStock_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694191
	/** 
	 * Clicking on Refer button of the Stock relocation result data storage folder invokes this.<BR>
	 * Summary: Displays the view of Stock relocation result data storage folder and select a folder to store it.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ReferenceStock_Click(ActionEvent e) throws Exception
	{
		//#CM694192
		// Set the parameter to be passed to the next screen.
		ForwardParameters param = new ForwardParameters();
		//#CM694193
		// Stock relocation result data storage folder
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_StockMovRsltFld.getText());
		//#CM694194
		// "Refer" button flag
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, REPORT_MOVE);

		//#CM694195
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");
	}

	//#CM694196
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StockMovRsltFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694197
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockMovRsltFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694198
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockMovRsltFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694199
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockMovRsltFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694200
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockMovRsltFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694201
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InvntryRsltFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694202
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryRsltFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694203
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryRsltFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694204
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryRsltFld_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694205
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryRsltFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694206
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferenceInventory_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694207
	/** 
	 * Clicking on Refer button of the Inventory Check result data storage folder invokes this.<BR>
	 * Summary: Displays the view of Inventory Check result data storage folder and select a folder to store it.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ReferenceInventory_Click(ActionEvent e) throws Exception
	{
		//#CM694208
		// Set the parameter to be passed to the next screen.
		ForwardParameters param = new ForwardParameters();
		//#CM694209
		// Inventory Check result data storage folder
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_InvntryRsltFld.getText());
		//#CM694210
		// "Refer" button flag
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, REPORT_INVENTORY);

		//#CM694211
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");
	}

	//#CM694212
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InvntryRsltFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694213
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryRsltFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694214
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryRsltFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694215
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryRsltFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694216
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InvntryRsltFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694217
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694218
	/** 
	 * Clicking on Add button invokes this.
	 * <BR>
	 * Summary: Displays a dialog box to allow to confirm to set the environment or not.<BR>
	 * <BR>
	 * <DIR>
	 *  1.Set the cursor for Worker code. <BR>
	 *  <BR>
	 * 	2.Display the dialog box for confirming to add. "Do you add this?"<BR>
	 * 	<DIR>
	 * 		[Dialog for confirming: Cancel]<BR>
	 * 		<DIR>
	 * 			Disable to do anything.<BR>
	 * 		</DIR>
	 * 		[Dialog for confirming: OK]<BR>
	 * 		<DIR>
	 * 		Summary: Sets the environment using the info in the Input area.<BR>
	 * 			1.Check for input in the input item (count) in the input area. (Mandatory Input, number of characters, and attribution of characters)<BR>
	 * 			2.Start the schedule.<BR>
	 * 				<DIR>
	 * 					- Update the storage folder and file name of the introduced packages.<BR>
	 * 					 Generate error if the designated folder is not found.<BR>
	 * 					 Display a message: "Designate a proper folder."<BR>
	 * 					- Update the EnvironmentInformation.<BR>
	 * 				</DIR>
	 * 			3.Maintain the contents of the input area.<BR>
	 * 		</DIR>
	 * 	</DIR>	
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		//#CM694219
		// Set focus for Worker Code. 
		setFocus(txt_WorkerCode);
		//#CM694220
		// Check for input.
		txt_WorkerCode.validate();
		txt_Password.validate();

		Connection conn = null;
		try
		{
			//#CM694221
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new ReportDataEnvironmentSCH();
			//#CM694222
			// Read the environment info for Initial Display.
			SystemParameter sysparam = (SystemParameter) schedule.initFind(conn, null);

			//#CM694223
			// Receiving package
			if (sysparam.getSelectReportInstockData())
			{
				txt_InstkRsltFld.validate();
				txt_InstkRsltFileNm.validate();

			}
			//#CM694224
			// Storage package
			if (sysparam.getSelectReportStorageData())
			{
				txt_StrgRsltFld.validate();
				txt_StrgRsltFileNm.validate();
			}
			//#CM694225
			// Picking package
			if (sysparam.getSelectReportRetrievalData())
			{
				txt_RtrivlRsltFld.validate();
				txt_RtrivlRsltFileNm.validate();
			}
			//#CM694226
			// Sorting package
			if (sysparam.getSelectReportSortingData())
			{
				txt_PickRsltFld.validate();
				txt_PickRsltFileNm.validate();
			}
			//#CM694227
			// Shipping package
			if (sysparam.getSelectReportShippingData())
			{
				txt_ShpRsltFld.validate();
				txt_ShpRsltFileNm.validate();
			}
			//#CM694228
			// Stock relocation package
			if (sysparam.getSelectReportMovementData())
			{
				txt_StockMovRsltFld.validate();
				txt_StockMovRsltFileNm.validate();
			}
			//#CM694229
			// Inventory Check package
			if (sysparam.getSelectReportInventoryData())
			{
				txt_InvntryRsltFld.validate();
				txt_InvntryRsltFileNm.validate();
			}
			//#CM694230
			// Unplanned Storage package
			if (sysparam.getSelectReportNoPlanStorageData())
			{
				txt_NoPlanStrgFld.validate();
				txt_NoPlanStrgFileNm.validate();
			}
			//#CM694231
			// Unplanned Picking package
			if (sysparam.getSelectReportNoPlanRetrievalData())
			{
				txt_NoPlanRtrivlFld.validate();
				txt_NoPlanRtrivlFileNm.validate();
			}

			Vector vecParam = new Vector(1);

			SystemParameter param = new SystemParameter();

			//#CM694232
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM694233
			// Password
			param.setPassword(txt_Password.getText());
			//#CM694234
			// Receiving result data storage folder
			param.setFolder_ReportInstockData(txt_InstkRsltFld.getText());
			//#CM694235
			// Receiving result data file name
			param.setFileName_ReportInstockData(txt_InstkRsltFileNm.getText());
			//#CM694236
			// Storage result data storage folder
			param.setFolder_ReportStorageData(txt_StrgRsltFld.getText());
			//#CM694237
			// Storage result data file name
			param.setFileName_ReportStorageData(txt_StrgRsltFileNm.getText());
			//#CM694238
			// Picking result data storage folder
			param.setFolder_ReportRetrievalData(txt_RtrivlRsltFld.getText());
			//#CM694239
			// Picking result data file name
			param.setFileName_ReportRetrievalData(txt_RtrivlRsltFileNm.getText());
			//#CM694240
			// Sorting result data storage folder
			param.setFolder_ReportSortingData(txt_PickRsltFld.getText());
			//#CM694241
			// Sorting result data file name
			param.setFileName_ReportSortingData(txt_PickRsltFileNm.getText());
			//#CM694242
			// Shipping result data storage folder
			param.setFolder_ReportShippingData(txt_ShpRsltFld.getText());
			//#CM694243
			// Shipping result data file name
			param.setFileName_ReportShippingData(txt_ShpRsltFileNm.getText());
			//#CM694244
			// Stock relocation result data storage folder
			param.setFolder_ReportMovementData(txt_StockMovRsltFld.getText());
			//#CM694245
			// Stock relocation result data file name
			param.setFileName_ReportMovementData(txt_StockMovRsltFileNm.getText());
			//#CM694246
			// Inventory Check result data storage folder
			param.setFolder_ReportInventoryData(txt_InvntryRsltFld.getText());
			//#CM694247
			// Inventory Check result data file name
			param.setFileName_ReportInventoryData(txt_InvntryRsltFileNm.getText());
			//#CM694248
			// Unplanned storage result data storage folder
			param.setFolder_ReportNoPlanStorageData(txt_NoPlanStrgFld.getText());
			//#CM694249
			// Unplanned storage result data file name
			param.setFileName_ReportNoPlanStorageData(txt_NoPlanStrgFileNm.getText());
			//#CM694250
			// Unplanned picking result data storage folder
			param.setFolder_ReportNoPlanRetrievalData(txt_NoPlanRtrivlFld.getText());
			//#CM694251
			// Unplanned picking result data file name
			param.setFileName_ReportNoPlanRetrievalData(txt_NoPlanRtrivlFileNm.getText());			
			//#CM694252
			// Receiving result data package flag
			param.setSelectReportInstockData(sysparam.getSelectReportInstockData());
			//#CM694253
			// Storage result data package flag
			param.setSelectReportStorageData(sysparam.getSelectReportStorageData());
			//#CM694254
			// Picking result data package flag
			param.setSelectReportRetrievalData(sysparam.getSelectReportRetrievalData());
			//#CM694255
			// Sorting result data package flag
			param.setSelectReportSortingData(sysparam.getSelectReportSortingData());
			//#CM694256
			// Shipping result data package flag
			param.setSelectReportShippingData(sysparam.getSelectReportShippingData());
			//#CM694257
			// Stock relocation result data package flag
			param.setSelectReportMovementData(sysparam.getSelectReportMovementData());
			//#CM694258
			// Inventory Check result data package flag
			param.setSelectReportInventoryData(sysparam.getSelectReportInventoryData());
			//#CM694259
			// Unplanned storage result data package flag
			param.setSelectReportNoPlanStorageData(sysparam.getSelectReportNoPlanStorageData());
			//#CM694260
			// Unplanned picking result data package flag
			param.setSelectReportNoPlanRetrievalData(sysparam.getSelectReportNoPlanRetrievalData());

			
			vecParam.addElement(param);

			SystemParameter[] paramArray = new SystemParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			//#CM694261
			// Start the schedule.
			if (schedule.startSCH(conn, paramArray))
			{
				//#CM694262
				// Set the message
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				//#CM694263
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
				//#CM694264
				// Close the connection.
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
	}

	//#CM694265
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694266
	/**
	 * Clicking on Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the Input area.<BR>
	 * </BR>
	 * <DIR>
	 * 		1.Return the item (count) in the input area to the initial value.<BR>
	 * 		<DIR>
	 * 			- For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method.
	 * 		</DIR>
	 * 		2.Set the cursor for Worker code.<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		StartDisp();
	}
	//#CM694267
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Set_Click(ActionEvent e) throws Exception
	{
	}

	//#CM694268
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_NoPlanStrgFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694269
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_NoPlanStrgFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694270
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_NoPlanStrgFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694271
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_NoPlanStrgFld_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694272
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_NoPlanStrgFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694273
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferenceNoPlanStrg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694274
	/** 
	 * Clicking on Refer button of the Unplanned storage result data storage folder invokes this.<BR>
	 * Summary: Displays the view of Unplanned storage result data storage folder and select a folder to store it.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ReferenceNoPlanStrg_Click(ActionEvent e) throws Exception
	{
		//#CM694275
		// Set the parameter to be passed to the next screen.
		ForwardParameters param = new ForwardParameters();
		//#CM694276
		// Unplanned storage result data storage folder
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_NoPlanStrgFld.getText());
		//#CM694277
		// "Refer" button flag
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, REPORT_NOPLANSTORAGE);

		//#CM694278
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");		
	}

	//#CM694279
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_NoPlanStrgFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694280
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_NoPlanStrgFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694281
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_NoPlanStrgFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694282
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_NoPlanStrgFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694283
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_NoPlanStrgFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694284
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_NoPlanRtrivlFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694285
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_NoPlanRtrivlFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694286
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_NoPlanRtrivlFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694287
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_NoPlanRtrivlFld_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694288
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_NoPlanRtrivlFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694289
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferenceNoPlanRtrivl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694290
	/** 
	 * Clicking on Refer button of the Unplanned picking result data storage folder invokes this.<BR>
	 * Summary: Displays the view of Unplanned picking result data storage folder and select a folder to store it.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ReferenceNoPlanRtrivl_Click(ActionEvent e) throws Exception
	{
		//#CM694291
		// Set the parameter to be passed to the next screen.
		ForwardParameters param = new ForwardParameters();
		//#CM694292
		// Unplanned picking result data storage folder
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_NoPlanRtrivlFld.getText());
		//#CM694293
		// "Refer" button flag
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, REPORT_NOPLANRETRIEVAL);

		//#CM694294
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");				
	}

	//#CM694295
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_NoPlanRtrivlFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694296
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_NoPlanRtrivlFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694297
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_NoPlanRtrivlFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694298
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_NoPlanRtrivlFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694299
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_NoPlanRtrivlFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}


}
//#CM694300
//end of class
