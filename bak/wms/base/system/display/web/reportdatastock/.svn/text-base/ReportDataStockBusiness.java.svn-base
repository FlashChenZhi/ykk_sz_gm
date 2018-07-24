// $Id: ReportDataStockBusiness.java,v 1.2 2006/11/13 08:21:58 suresh Exp $

//#CM694591
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.reportdatastock;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
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
import jp.co.daifuku.wms.base.system.display.web.listbox.listsystemconsignor.ListSystemConsignorBusiness;
import jp.co.daifuku.wms.base.system.schedule.ReportDataStockSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM694592
/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * Allow this class to report the inventory information.<BR>
 * Set the contents entered via screen for the parameter.<BR>
 * Allow the schedule to report the inventory information based on the parameter.<BR>
 * Receive the result from the schedule. Receive true if the process completed normally.<BR>
 * Or, receive false if the schedule failed to complete due to condition error etc.<BR>
 * As the result of the schedule, output the message obtained from the schedule to the screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process by clicking "Start" button(<CODE>btn_Start_Click()</CODE>method)<BR>
 * <BR>
 * <DIR>
 * 	Report the data for the items ticked in each field via screen.<BR>
 * 	Return an error message if condition error etc. occurs.<BR>
 * <BR>
 * 	[Parameter] *Mandatory Input<BR>
 * 	<BR>
 * 	<DIR>
 * 		Worker Code* <BR>
 *		Password* <BR>
 *		Consignor Code <BR>
 *		inventory information data storage folder* <BR>
 *		Inventory information data file name* <BR>
 * 	</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:58 $
 * @author  $Author: suresh $
 */
public class ReportDataStockBusiness extends ReportDataStock implements WMSConstants
{
	//#CM694593
	// Search Consignor
	private final static String DO_SEARCH_CNSGNR = "/system/listbox/listsystemconsignor/ListSystemConsignor.do";
	//#CM694594
	// Searching in progress
	private final static String DO_PROCESS = "/progress.do";
	//#CM694595
	// Class fields --------------------------------------------------
	//#CM694596
	// button flag (inventory information) 
	public static final String REPORT_STOCK = "21";
	//#CM694597
	// Class variables -----------------------------------------------

	//#CM694598
	// Class method --------------------------------------------------

	//#CM694599
	// Constructors --------------------------------------------------

	//#CM694600
	// Public methods ------------------------------------------------
	//#CM694601
	/**
	 * Initialize the screen.
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM694602
		// Disable to allow any user, who logs in as a user other than "worker", to change the folder and the file name for output.
		//#CM694603
		// 
		//#CM694604
		// Enable to be available when logging in using "admin".
		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
		if (!userHandler.getUserRoleID().equals("admin") && userHandler.getTerminalRoleID().equals("guest"))
		{
			txt_RptDataStockFld.setReadOnly(true);
			txt_RptDataStockFileNm.setReadOnly(true);
			btn_Reference.setEnabled(false);
			
		}
		
		//#CM694605
		// Show the Initial Display.
		StartDisp();
	}

	//#CM694606
	/**
	 * Invoke this before invoking each control event.<BR>
	 * <BR>
	 * <DIR>
	 *  Summary: Displays a dialog.<BR>
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
			//#CM694607
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM694608
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM694609
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM694610
			//Set the path to the help file.
			//#CM694611
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		//#CM694612
		// MSG-0019= Do you start it?
		btn_Start.setBeforeConfirm("MSG-W0019");
	}

	//#CM694613
	/**
	 * Override the page_DlgBack defined for Page.
	 *Invoke this method to return after clicking on "Search" button.
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		//#CM694614
		// Set focus for Worker Code. 
		setFocus(txt_WorkerCode);
		
		DialogParameters param = ((DialogEvent) e).getDialogParameters();

		//#CM694615
		// Obtain the parameter selected in the listbox.
		//#CM694616
		// Consignor Code
		String consignorcode = param.getParameter(ListSystemConsignorBusiness.CONSIGNORCODE_KEY);
		String folder = param.getParameter(ListFolderSelectBusiness.FOLDER_KEY);
		String btnflug = param.getParameter(ListFolderSelectBusiness.BTNFLUG_KEY);
		
		//#CM694617
		// Set a value if not empty.
		//#CM694618
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		if (btnflug != null && !btnflug.equals(""))
		{
			if (btnflug.equals(REPORT_STOCK))
			{
				//#CM694619
				// Set a value if the folder for storing the inventory information data is not empty.
				if (!StringUtil.isBlank(folder))
				{
					txt_RptDataStockFld.setText(folder);
				}
			}

		}
	}

	//#CM694620
	// Package methods -----------------------------------------------

	//#CM694621
	// Protected methods ---------------------------------------------

	//#CM694622
	// Private methods -----------------------------------------------
	//#CM694623
	/**
	 * Show the Initial Display.
	 * @throws Exception Report all exceptions.
	 */
	private void StartDisp() throws Exception
	{
		//#CM694624
		// Set focus for Worker Code. 
		setFocus(txt_WorkerCode);
		Connection conn = null;
		
		try
		{
   			txt_ConsignorCode.setText("");
					
			//#CM694625
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			WmsScheduler schedule = new ReportDataStockSCH();
			SystemParameter param = (SystemParameter) schedule.initFind(conn, null);

			//#CM694626
			// Consignor Code
			if(!StringUtil.isBlank(param.getConsignorCode()))
			{
				//#CM694627
				// For data with only one Consignor code, display the initial display.
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
			else
			{
				txt_ConsignorCode.setText("");
			}
			//#CM694628
			// Inventory information data storage folder
			txt_RptDataStockFld.setText(param.getFolder_ReportDataStock());
			//#CM694629
			// Inventory information data file name
			txt_RptDataStockFileNm.setText(param.getFileName_ReportDataStock());

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM694630
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
	//#CM694631
	// Event handler methods -----------------------------------------
	//#CM694632
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694633
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694634
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_ReportDataStock_Click(ActionEvent e) throws Exception
	{
	}

	//#CM694635
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694636
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

	//#CM694637
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694638
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694639
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694640
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694641
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694642
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694643
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694644
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694645
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694646
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694647
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694648
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694649
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694650
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694651
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694652
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694653
	/** 
	 * Clicking on Search Consignor button invokes this.<BR>
	 * Summary: This method sets the search conditions for the parameter and<BR>
	 * displays the Consignor list search listbox using the search conditions.<BR>
	 * [Parameter] *Mandatory Input
	 * <DIR>
	 * Consignor Code<BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_PsearchCnsgnrCd_Click(ActionEvent e) throws Exception
	{
		//#CM694654
		// Set the search conditions into the Search Consignor screen.
		ForwardParameters param = new ForwardParameters();
		//#CM694655
		// Consignor Code
		param.setParameter(ListSystemConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM694656
		// Search flag (inventory information)
		param.setParameter(
			ListSystemConsignorBusiness.SEARCHCONSIGNOR_KEY,
			SystemParameter.SEARCHFLAG_STOCK);
		//#CM694657
		// Processing screen ->"Result" screen
		redirect(DO_SEARCH_CNSGNR, param, DO_PROCESS);
	}

	//#CM694658
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ReportDataStockFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694659
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RptDataStockFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694660
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RptDataStockFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694661
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RptDataStockFld_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694662
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RptDataStockFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694663
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Reference_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694664
	/** 
	 * Clicking on Refer button of the inventory information data storage folder invokes this.<BR>
	 * Summary: Displays the view of inventory information data storage folder and select a folder to store it.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Reference_Click(ActionEvent e) throws Exception
	{
		//#CM694665
		// Set the parameter to be passed to the next screen.
		ForwardParameters param = new ForwardParameters();
		//#CM694666
		// Inventory information data storage folder
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_RptDataStockFld.getText());
		//#CM694667
		// "Refer" button flag
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, REPORT_STOCK);

		//#CM694668
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");		
	}

	//#CM694669
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ReportDataStockFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694670
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RptDataStockFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694671
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RptDataStockFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694672
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RptDataStockFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694673
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RptDataStockFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694674
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Start_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694675
	/** 
	 * Clicking on Start button invokes this.
	 * <BR>
	 * Summary: Displays a dialog box to allow to confirm to report the data or not. <BR>
	 * <BR>
	 * <DIR>
	 * 	1.Display a dialog box to allow to confirm to start: "Do you start it?" <BR>
	 * 	<DIR>
	 * 		[Dialog for confirming: Cancel]<BR>
	 * 		<DIR>
	 * 			Disable to do anything.<BR>
	 * 		</DIR>
	 * 		[Dialog for confirming: OK]<BR>
	 * 		<DIR>
	 * 		Summary: Reports data using the info of the Input area.<BR>
	 * 			1.Check the field items entered in the Input area (Mandatory Input, number of characters, and attribution of characters)<BR>
	 * 			2.Start the schedule.<BR>
	 * 				<DIR>
	 * 					- Report the data for the ticked items. <BR>
	 *					- If report error occurs: Display the error message. <BR>
	 *					- If reporting completed: Display a message announcing that reporting completed. <BR>
	 * 				</DIR>
	 * 			3.Maintain the contents of the input area.<BR>
	 * 		</DIR>
	 * 	</DIR>	
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Start_Click(ActionEvent e) throws Exception
	{
		//#CM694676
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);

		//#CM694677
		// Check for input.
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_RptDataStockFld.validate();
		txt_RptDataStockFileNm.validate();
		
		//#CM694678
		// 2006/07/25 add start T.kishimoto Input check was added.
		txt_ConsignorCode.validate(false);
		//#CM694679
		// 2006/07/25 add end T.Kishimoto

		Connection conn = null;
		try
		{			
			//#CM694680
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new ReportDataStockSCH();
			
			SystemParameter sysparam = (SystemParameter) schedule.initFind(conn, null);
			
			//#CM694681
			// Inventory package
			if (sysparam.getSelectReportDataStock())
			{
				txt_RptDataStockFld.validate();
				txt_RptDataStockFileNm.validate();

			}	
					
			Vector vecParam = new Vector(1);

			SystemParameter param = new SystemParameter();
						
			//#CM694682
			// Set the value for the parameter.
			//#CM694683
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM694684
			// Password
			param.setPassword(txt_Password.getText());
			//#CM694685
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM694686
			// inventory information data storage folder
			param.setFolder_ReportDataStock(txt_RptDataStockFld.getText());
			//#CM694687
			// Inventory information data file name
			param.setFileName_ReportDataStock(txt_RptDataStockFileNm.getText());			

			vecParam.addElement(param);

			SystemParameter[] paramArray = new SystemParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			//#CM694688
			// Start the schedule.
			if (schedule.startSCH(conn, paramArray))
			{
				conn.commit();
			}
			else
			{
				conn.rollback();
			}

			if (schedule.getMessage() != null)
			{
				//#CM694689
				// Display a message.
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
				//#CM694690
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

	//#CM694691
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694692
	/** 
	 * Clicking on Clear button invokes this.<BR>
	 * Summary: Clears the input area.<BR>
	 * <DIR>Initialize the screen. <BR>
	 * For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method. <BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		StartDisp();
	}
}
//#CM694693
//end of class
