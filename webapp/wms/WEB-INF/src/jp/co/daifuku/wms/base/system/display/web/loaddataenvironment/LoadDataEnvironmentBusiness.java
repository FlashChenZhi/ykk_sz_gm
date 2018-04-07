// $Id: LoadDataEnvironmentBusiness.java,v 1.2 2006/11/13 08:21:15 suresh Exp $

//#CM693576
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.loaddataenvironment;
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
import jp.co.daifuku.wms.base.system.schedule.LoadDataEnvironmentSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM693577
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this class to load data (to set the environment).<BR>
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
 * 		Receiving plan data storage folder* <BR>
 * 		Receiving plan data file name* <BR>
 * 		Storage plan data storage folder* <BR>
 * 		Storage plan data file name* <BR>
 * 		Picking plan data storage folder* <BR>
 * 		Picking plan data file name* <BR>
 * 		Sorting plan data storage folder* <BR>
 * 		Sorting plan data file name* <BR>
 * 		Shipping plan data storage folder* <BR>
 * 		Shipping plan data file name* <BR>
 * 	</DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/06</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:15 $
 * @author  $Author: suresh $
 */
public class LoadDataEnvironmentBusiness extends LoadDataEnvironment implements WMSConstants
{
	//#CM693578
	// Class fields --------------------------------------------------
	//#CM693579
	/** 
	 * button flag (Shipping) 
	 */
	public static final String LOAD_INSTOCK = "11";

	//#CM693580
	/** 
	 * button flag (Storage)
	 */
	public static final String LOAD_STORAGE = "12";

	//#CM693581
	/** 
	 * button flag (Picking)
	 */
	public static final String LOAD_RETRIEVAL = "13";

	//#CM693582
	/** 
	 * button flag (Picking)
	 */
	public static final String LOAD_SHIPPING = "14";

	//#CM693583
	/**
	 * button flag (Sorting)
	 */
	public static final String LOAD_PICKING = "15";

	//#CM693584
	// Class variables -----------------------------------------------

	//#CM693585
	// Class method --------------------------------------------------

	//#CM693586
	// Constructors --------------------------------------------------

	//#CM693587
	// Public methods ------------------------------------------------

	//#CM693588
	/**
	 * Initialize the screen.<BR>
	 * <BR>
	 * Field item [Initial Value]<BR>
	 * <DIR>
	 * 	Receiving plan data storage folder	[Obtain from EnvironmentInformation] <BR>
	 * 	Receiving plan data file name	[Obtain from EnvironmentInformation] <BR>
	 * 	Storage plan data storage folder	[Obtain from EnvironmentInformation] <BR>
	 * 	Storage plan data file name	[Obtain from EnvironmentInformation] <BR>
	 * 	Picking plan data storage folder	[Obtain from EnvironmentInformation] <BR>
	 * 	Picking plan data file name	[Obtain from EnvironmentInformation] <BR>
	 * 	Sorting plan data storage folder	[Obtain from EnvironmentInformation] <BR>
	 * 	Sorting plan data file name	[Obtain from EnvironmentInformation] <BR>
	 * 	Shipping plan data storage folder	[Obtain from EnvironmentInformation] <BR>
	 * 	Shipping plan data file name	[Obtain from EnvironmentInformation] <BR>
	 * </DIR>
	 * 1.Set the cursor for Worker code.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM693589
		// Show the Initial Display.
		StartDisp();
	}

	//#CM693590
	/**
	 * Invoke this before invoking each control event. <BR>
	 * <BR>
	 * Summary: Allow this method to execute the following processes.<BR>
	 * <BR>
	 * <DIR>
	 *		Clicking on Add button displays a dialog. <BR>
	 * <DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM693591
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM693592
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM693593
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM693594
			//Set the path to the help file.
			//#CM693595
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		
		//#CM693596
		// Display the dialog for confirming to add.
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}

	//#CM693597
	/**
	 * Override the page_DlgBack defined for Page.
	 *Invoke this method to return after clicking on "Search" button.
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM693598
		// Obtain the parameter selected in the listbox.
		String folder = param.getParameter(ListFolderSelectBusiness.FOLDER_KEY);
		String btnflug = param.getParameter(ListFolderSelectBusiness.BTNFLUG_KEY);

		if (btnflug != null && !btnflug.equals(""))
		{
			if (btnflug.equals(LOAD_INSTOCK))
			{
				//#CM693599
				// Set a value if the folder for storing the Receiving plan data is not empty.
				if (!StringUtil.isBlank(folder))
				{
					txt_InstkPlanFld.setText(folder);
				}
			}
			else if (btnflug.equals(LOAD_STORAGE))
			{
				//#CM693600
				// Set a value if the folder for storing the Storage plan data is not empty.
				if (!StringUtil.isBlank(folder))
				{
					txt_StrgPlanFld.setText(folder);
				}
			}
			else if (btnflug.equals(LOAD_RETRIEVAL))
			{
				//#CM693601
				// Set a value if the folder for storing the Picking data is not empty.
				if (!StringUtil.isBlank(folder))
				{
					txt_RtrivlPlanFld.setText(folder);
				}
			}
			else if (btnflug.equals(LOAD_PICKING))
			{
				//#CM693602
				// Set a value if the folder for storing the Sorting plan data is not empty.
				if (!StringUtil.isBlank(folder))
				{
					txt_PickPlanFld.setText(folder);
				}
			}
			else if (btnflug.equals(LOAD_SHIPPING))
			{
				//#CM693603
				// Set a value if the folder for storing the Shipping plan data is not empty.
				if (!StringUtil.isBlank(folder))
				{
					txt_ShpPlanFld.setText(folder);
				}
			}
		}
		
		//#CM693604
		//Set focus for Worker Code. 
		setFocus(txt_WorkerCode);
	}
	//#CM693605
	// Package methods -----------------------------------------------

	//#CM693606
	// Protected methods ---------------------------------------------

	//#CM693607
	// Private methods -----------------------------------------------
	//#CM693608
	/**
	 * Show the Initial Display.
	 * @throws Exception Report all exceptions.
	 */
	private void StartDisp() throws Exception
	{
		//#CM693609
		// Set focus for Worker Code. 
		setFocus(txt_WorkerCode);
		Connection conn = null;
		try
		{
			//#CM693610
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new LoadDataEnvironmentSCH();

			//#CM693611
			// Read the environment info for Initial Display.
			SystemParameter param = (SystemParameter) schedule.initFind(conn, null);

			//#CM693612
			// Enable to input only for the field items that contain packages.
			//#CM693613
			// Receiving plan data
			if (!param.getSelectLoadInstockData())
			{
				txt_InstkPlanFld.setReadOnly(true);
				txt_InstkPlanFileNm.setReadOnly(true);
				btn_ReferenceInstk.setEnabled(false);
			}
			else
			{
				//#CM693614
				// Receiving plan data storage folder
				txt_InstkPlanFld.setText(param.getFolder_LoadInstockData());
				//#CM693615
				// Receiving plan data file name
				txt_InstkPlanFileNm.setText(param.getFileName_LoadInstockData());
			}
			//#CM693616
			// Storage plan data
			if (!param.getSelectLoadStorageData())
			{
				txt_StrgPlanFld.setReadOnly(true);
				txt_StrgPlanFileNm.setReadOnly(true);
				btn_ReferenceStrg.setEnabled(false);
			}
			else
			{
				//#CM693617
				// Storage plan data storage folder
				txt_StrgPlanFld.setText(param.getFolder_LoadStorageData());
				//#CM693618
				// Storage plan data file name
				txt_StrgPlanFileNm.setText(param.getFileName_LoadStorageData());
			}
			//#CM693619
			// Picking plan data
			if (!param.getSelectLoadRetrievalData())
			{
				txt_RtrivlPlanFld.setReadOnly(true);
				txt_RtrivlPlanFileNm.setReadOnly(true);
				btn_ReferenceRtrivl.setEnabled(false);
			}
			else
			{
				//#CM693620
				// Picking plan data storage folder
				txt_RtrivlPlanFld.setText(param.getFolder_LoadRetrievalData());
				//#CM693621
				// Picking plan data file name
				txt_RtrivlPlanFileNm.setText(param.getFileName_LoadRetrievalData());
			}
			//#CM693622
			// Sorting Plan data
			if (!param.getSelectLoadSortingData())
			{
				txt_PickPlanFld.setReadOnly(true);
				txt_PickPlanFileNm.setReadOnly(true);
				btn_ReferencePick.setEnabled(false);
			}
			else
			{
				//#CM693623
				// Sorting plan data storage folder
				txt_PickPlanFld.setText(param.getFolder_LoadSortingData());
				//#CM693624
				// Sorting plan data file name
				txt_PickPlanFileNm.setText(param.getFileName_LoadSortingData());
			}
			//#CM693625
			// Shipping plan data
			if (!param.getSelectLoadShippingData())
			{
				txt_ShpPlanFld.setReadOnly(true);
				txt_ShpPlanFileNm.setReadOnly(true);
				btn_ReferenceShp.setEnabled(false);
			}
			else
			{
				//#CM693626
				// Shipping plan data storage folder
				txt_ShpPlanFld.setText(param.getFolder_LoadShippingData());
				//#CM693627
				// Shipping plan data file name
				txt_ShpPlanFileNm.setText(param.getFileName_LoadShippingData());
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
				//#CM693628
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

	//#CM693629
	// Event handler methods -----------------------------------------
	//#CM693630
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693631
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693632
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693633
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

	//#CM693634
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693635
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693636
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693637
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693638
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693639
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693640
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693641
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693642
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693643
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693644
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InstkPlanFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693645
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693646
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693647
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanFld_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693648
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693649
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferenceInstk_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693650
	/** 
	 * Clicking on Refer button of the Receiving plan data storage folder invokes this.<BR>
	 * Summary: Displays the view of Receiving plan data storage folder and select a folder to store it.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ReferenceInstk_Click(ActionEvent e) throws Exception
	{
		//#CM693651
		// Set the parameter to be passed to the next screen.
		ForwardParameters param = new ForwardParameters();
		//#CM693652
		// Receiving plan data storage folder
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_InstkPlanFld.getText());
		//#CM693653
		// "Refer" button flag
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, LOAD_INSTOCK);

		//#CM693654
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");
	}

	//#CM693655
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InstkPlanFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693656
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693657
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693658
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693659
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkPlanFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693660
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrgPlanFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693661
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPlanFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693662
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPlanFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693663
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPlanFld_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693664
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPlanFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693665
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferenceStrg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693666
	/** 
	 * Clicking on Refer button of the Storage plan data storage folder invokes this.<BR>
	 * Summary: Displays the view of Storage plan data storage folder and select a folder to store it.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ReferenceStrg_Click(ActionEvent e) throws Exception
	{
		//#CM693667
		// Set the parameter to be passed to the next screen.
		ForwardParameters param = new ForwardParameters();
		//#CM693668
		// Storage plan data storage folder
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_StrgPlanFld.getText());
		//#CM693669
		// "Refer" button flag
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, LOAD_STORAGE);

		//#CM693670
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");
	}

	//#CM693671
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrgPlanFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693672
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPlanFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693673
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPlanFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693674
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPlanFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693675
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPlanFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693676
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RtrivlPlanFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693677
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693678
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693679
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanFld_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693680
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693681
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferenceRtrivl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693682
	/** 
	 * Clicking on Refer button of the Picking plan data storage folder invokes this.<BR>
	 * Summary: Displays the view of Picking plan data storage folder and select a folder to store it.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ReferenceRtrivl_Click(ActionEvent e) throws Exception
	{
		//#CM693683
		// Set the parameter to be passed to the next screen.
		ForwardParameters param = new ForwardParameters();
		//#CM693684
		// Picking plan data storage folder
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_RtrivlPlanFld.getText());
		//#CM693685
		// "Refer" button flag
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, LOAD_RETRIEVAL);

		//#CM693686
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");
	}

	//#CM693687
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RtrivlPlanFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693688
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693689
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693690
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693691
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693692
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PickPlanDataFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693693
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickPlanFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693694
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickPlanFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693695
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickPlanFld_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693696
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickPlanFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693697
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferencePick_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693698
	/** 
	 * Clicking on Refer button of the Sorting plan data storage folder invokes this.<BR>
	 * Summary: Displays the view of Sorting Plan data storage folder and select a folder to store it.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ReferencePick_Click(ActionEvent e) throws Exception
	{
		//#CM693699
		// Set the parameter to be passed to the next screen.
		ForwardParameters param = new ForwardParameters();
		//#CM693700
		// Sorting plan data storage folder
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_PickPlanFld.getText());
		//#CM693701
		// "Refer" button flag
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, LOAD_PICKING);

		//#CM693702
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");
	}

	//#CM693703
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PickPlanDataFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693704
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickPlanFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693705
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickPlanFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693706
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickPlanFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693707
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickPlanFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693708
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ShpPlanDataFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693709
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpPlanFld_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693710
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpPlanFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693711
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpPlanFld_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693712
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpPlanFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693713
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferenceShp_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693714
	/** 
	 * Clicking on Refer button of the Shipping plan data storage folder invokes this.<BR>
	 * Summary: Displays the view of Shipping plan data storage folder and select a folder to store it.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ReferenceShp_Click(ActionEvent e) throws Exception
	{
		//#CM693715
		// Set the parameter to be passed to the next screen.
		ForwardParameters param = new ForwardParameters();
		//#CM693716
		// Shipping plan data storage folder
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_ShpPlanFld.getText());
		//#CM693717
		// "Refer" button flag
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, LOAD_SHIPPING);

		//#CM693718
		// Processing screen ->"Result" screen
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");
	}

	//#CM693719
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ShpPlanFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693720
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpPlanFileNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693721
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpPlanFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693722
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpPlanFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693723
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShpPlanFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693724
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693725
	/** 
	 * Clicking on Add button invokes this.
	 * <BR>
	 * Summary: Displays a dialog box to allow to confirm to set the environment or not.<BR>
	 * <BR>
	 * <DIR>
	 * 	1.Set the cursor for Worker code. <BR>
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
	 * 			
	 * 		</DIR>
	 * 	</DIR>	
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		//#CM693726
		// Set focus for Worker Code. 
		setFocus(txt_WorkerCode);
		//#CM693727
		// Check for input.
		txt_WorkerCode.validate();
		txt_Password.validate();

		Connection conn = null;
		try
		{
			//#CM693728
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new LoadDataEnvironmentSCH();

			//#CM693729
			// Read the environment info for Initial Display.
			SystemParameter sysparam = (SystemParameter) schedule.initFind(conn, null);

			//#CM693730
			// Receiving package
			if (sysparam.getSelectLoadInstockData())
			{
				txt_InstkPlanFld.validate();
				txt_InstkPlanFileNm.validate();
			}

			//#CM693731
			// Storage package
			if (sysparam.getSelectLoadStorageData())
			{
				txt_StrgPlanFld.validate();
				txt_StrgPlanFileNm.validate();
			}

			//#CM693732
			// Picking package
			if (sysparam.getSelectLoadRetrievalData())
			{
				txt_RtrivlPlanFld.validate();
				txt_RtrivlPlanFileNm.validate();
			}

			//#CM693733
			// Sorting package
			if (sysparam.getSelectLoadSortingData())
			{
				txt_PickPlanFld.validate();
				txt_PickPlanFileNm.validate();
			}

			//#CM693734
			// Shipping package
			if (sysparam.getSelectLoadShippingData())
			{
				txt_ShpPlanFld.validate();
				txt_ShpPlanFileNm.validate();
			}

			Vector vecParam = new Vector(1);

			SystemParameter param = new SystemParameter();

			//#CM693735
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM693736
			// Password
			param.setPassword(txt_Password.getText());
			//#CM693737
			// Receiving plan data storage folder
			param.setFolder_LoadInstockData(txt_InstkPlanFld.getText());
			//#CM693738
			// Receiving plan data file name
			param.setFileName_LoadInstockData(txt_InstkPlanFileNm.getText());
			//#CM693739
			// Storage plan data storage folder
			param.setFolder_LoadStorageData(txt_StrgPlanFld.getText());
			//#CM693740
			// Storage plan data file name
			param.setFileName_LoadStorageData(txt_StrgPlanFileNm.getText());
			//#CM693741
			// Picking plan data storage folder
			param.setFolder_LoadRetrievalData(txt_RtrivlPlanFld.getText());
			//#CM693742
			// Picking plan data file name
			param.setFileName_LoadRetrievalData(txt_RtrivlPlanFileNm.getText());
			//#CM693743
			// Sorting plan data storage folder
			param.setFolder_LoadSortingData(txt_PickPlanFld.getText());
			//#CM693744
			// Sorting plan data file name
			param.setFileName_LoadSortingData(txt_PickPlanFileNm.getText());
			//#CM693745
			// Shipping plan data storage folder
			param.setFolder_LoadShippingData(txt_ShpPlanFld.getText());
			//#CM693746
			// Shipping plan data file name
			param.setFileName_LoadShippingData(txt_ShpPlanFileNm.getText());
			//#CM693747
			// Receiving plan data package flag
			param.setSelectLoadInstockData(sysparam.getSelectLoadInstockData());
			//#CM693748
			// Storage plan data package flag
			param.setSelectLoadStorageData(sysparam.getSelectLoadStorageData());
			//#CM693749
			// Picking plan data package flag
			param.setSelectLoadRetrievalData(sysparam.getSelectLoadRetrievalData());
			//#CM693750
			// Sorting plan data package flag
			param.setSelectLoadSortingData(sysparam.getSelectLoadSortingData());
			//#CM693751
			// Shipping plan data package flag
			param.setSelectLoadShippingData(sysparam.getSelectLoadShippingData());

			vecParam.addElement(param);

			SystemParameter[] paramArray = new SystemParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			//#CM693752
			// Start the schedule.
			if (schedule.startSCH(conn, paramArray))
			{
				//#CM693753
				// Set the message
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				//#CM693754
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
				//#CM693755
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

	//#CM693756
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693757
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
	 * 		2.Set the cursor for Worker code. <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM693758
		// Clear the screen.
		StartDisp();
	}
}
//#CM693759
//end of class
