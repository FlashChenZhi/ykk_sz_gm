// $Id: DefineLoadDataMenuBusiness.java,v 1.2 2006/11/13 08:18:29 suresh Exp $

//#CM687563
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.defineloaddatamenu;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.system.schedule.DefineLoadDataMenuSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM687564
/**
 * Designer : K.Mukai <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * Allow this class to provide a screen to set field items for loading data (to set the field items). <BR>
 * Set the contents input via input area for the parameter and pass it to the schedule. Shift to the second screen. <BR>
 * Receive the result from the schedule. Receive true if the process completed normally. <BR>
 * Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 * As the result of the schedule, output the message obtained from the schedule to the screen. <BR>
 *
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * Process by clicking "Next" button(<CODE>btn_Next_Click()</CODE>method)<BR>
 * <BR>
 * <DIR>
 *   Shift using the info in the Input area. <BR>
 * <BR>
 *   [Parameter] *Mandatory Input<BR>
 * <BR>
 * <DIR>
 *   Worker Code* <BR>
 *   Password* <BR>
 * </DIR>
 * <BR>
 *   [Parameter]+ Require to select one or more parameter(s) <BR>
 * <BR>
 * <DIR>
 *   Select each introduced package +<BR>
 * </DIR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/06</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:29 $
 * @author  $Author: suresh $
 */
public class DefineLoadDataMenuBusiness extends DefineLoadDataMenu implements WMSConstants
{
	//#CM687565
	// Class fields --------------------------------------------------

	//#CM687566
	// Class variables -----------------------------------------------

	//#CM687567
	// Class method --------------------------------------------------

	//#CM687568
	// Constructors --------------------------------------------------

	//#CM687569
	// Public methods ------------------------------------------------

	//#CM687570
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
			//#CM687571
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM687572
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM687573
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM687574
			//Set the path to the help file.
			//#CM687575
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
	}
	
	//#CM687576
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Set the initial value of the cursor to Worker Code. <BR>
	 * <DIR>
	 *   Field item [Initial Value] <BR>
	 *   <DIR>
	 *     Receiving plan data [Selected] <BR>
	 *     Storage plan data [Not Selected] <BR>
	 *     Picking plan data [Not Selected] <BR>
	 *     Sorting Plan data [Not Selected] <BR>
	 *     Shipping plan data [Not Selected] <BR>
	 *   </DIR>
	 *   <BR>
	 *   Display only the radio button of field item to which a package is introduced.<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM687577
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);

		Connection conn = null;
		try
		{
			//#CM687578
			// Set the title when returning from the second screen.
			if (!StringUtil.isBlank(getViewState().getString("TITLE")))
			{
				lbl_SettingName.setResourceKey(getViewState().getString("TITLE"));
			}
			//#CM687579
			// Set up the tab so as to raise its left side.
			tab_LodClm_Set.setSelectedIndex(1);

			//#CM687580
			// Obtain the Worker Code that was set in the original screen, via the ViewState.
			if (!StringUtil.isBlank(getViewState().getString("WORKERCODE")))
			{
				txt_WorkerCode.setText(getViewState().getString("WORKERCODE"));
			}
			//#CM687581
			// Obtain the password that was set in the original screen, via the ViewState.
			if (!StringUtil.isBlank(getViewState().getString("PASSWORD")))
			{
				txt_Password.setText(getViewState().getString("PASSWORD"));
			}
			//#CM687582
			// Obtain the selected item that was set in the original screen, via the ViewState.
			if (!StringUtil.isBlank(getViewState().getString("SELECT")))
			{
				if (getViewState()
					.getString("SELECT")
					.equals(SystemParameter.SELECTDEFINELOADDATA_INSTOCK))
				{
					rdo_Plan_Instock.setChecked(true);
					rdo_Plan_Storage.setChecked(false);
					rdo_Plan_Retrieval.setChecked(false);
					rdo_Plan_Picking.setChecked(false);
					rdo_Plan_Shipping.setChecked(false);
				}
				else if (
					getViewState().getString("SELECT").equals(
						SystemParameter.SELECTDEFINELOADDATA_STORAGE))
				{
					rdo_Plan_Instock.setChecked(false);
					rdo_Plan_Storage.setChecked(true);
					rdo_Plan_Retrieval.setChecked(false);
					rdo_Plan_Picking.setChecked(false);
					rdo_Plan_Shipping.setChecked(false);
				}
				else if (
					getViewState().getString("SELECT").equals(
						SystemParameter.SELECTDEFINELOADDATA_RETRIEVAL))
				{
					rdo_Plan_Instock.setChecked(false);
					rdo_Plan_Storage.setChecked(false);
					rdo_Plan_Retrieval.setChecked(true);
					rdo_Plan_Picking.setChecked(false);
					rdo_Plan_Shipping.setChecked(false);
				}
				else if (
					getViewState().getString("SELECT").equals(
						SystemParameter.SELECTDEFINELOADDATA_SORTING))
				{
					rdo_Plan_Instock.setChecked(false);
					rdo_Plan_Storage.setChecked(false);
					rdo_Plan_Retrieval.setChecked(false);
					rdo_Plan_Picking.setChecked(true);
					rdo_Plan_Shipping.setChecked(false);
				}
				else if (
					getViewState().getString("SELECT").equals(
						SystemParameter.SELECTDEFINELOADDATA_SHIPPING))
				{
					rdo_Plan_Instock.setChecked(false);
					rdo_Plan_Storage.setChecked(false);
					rdo_Plan_Retrieval.setChecked(false);
					rdo_Plan_Picking.setChecked(false);
					rdo_Plan_Shipping.setChecked(true);
				}
			}

			//#CM687583
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			SystemParameter initParam = new SystemParameter();
			WmsScheduler schedule = new DefineLoadDataMenuSCH();
			SystemParameter param = (SystemParameter) schedule.initFind(conn, initParam);

			//#CM687584
			// Enable to read-only for the field items that does not contain package.
			//#CM687585
			// Receiving plan data
			if (!param.getSelectLoadInstockData())
			{
				rdo_Plan_Instock.setEnabled(false);
			}
			//#CM687586
			// Storage plan data
			if (!param.getSelectLoadStorageData())
			{
				rdo_Plan_Storage.setEnabled(false);
			}
			//#CM687587
			// Picking plan data
			if (!param.getSelectLoadRetrievalData())
			{
				rdo_Plan_Retrieval.setEnabled(false);
			}
			//#CM687588
			// Sorting Plan data
			if (!param.getSelectLoadSortingData())
			{
				rdo_Plan_Picking.setEnabled(false);
			}
			//#CM687589
			// Shipping plan data
			if (!param.getSelectLoadShippingData())
			{
				rdo_Plan_Shipping.setEnabled(false);
			}

			if (StringUtil.isBlank(getViewState().getString("SELECT")))
			{
				//#CM687590
				// Default setting of radio button.
				if (rdo_Plan_Instock.getEnabled())
				{
					rdo_Plan_Instock.setChecked(true);
					rdo_Plan_Storage.setChecked(false);
					rdo_Plan_Retrieval.setChecked(false);
					rdo_Plan_Picking.setChecked(false);
					rdo_Plan_Shipping.setChecked(false);
				}
				else if (rdo_Plan_Storage.getEnabled())
				{
					rdo_Plan_Instock.setChecked(false);
					rdo_Plan_Storage.setChecked(true);
					rdo_Plan_Retrieval.setChecked(false);
					rdo_Plan_Picking.setChecked(false);
					rdo_Plan_Shipping.setChecked(false);
				}
				else if (rdo_Plan_Retrieval.getEnabled())
				{
					rdo_Plan_Instock.setChecked(false);
					rdo_Plan_Storage.setChecked(false);
					rdo_Plan_Retrieval.setChecked(true);
					rdo_Plan_Picking.setChecked(false);
					rdo_Plan_Shipping.setChecked(false);
				}
				else if (rdo_Plan_Picking.getEnabled())
				{
					rdo_Plan_Instock.setChecked(false);
					rdo_Plan_Storage.setChecked(false);
					rdo_Plan_Retrieval.setChecked(false);
					rdo_Plan_Picking.setChecked(true);
					rdo_Plan_Shipping.setChecked(false);
				}
				else if (rdo_Plan_Shipping.getEnabled())
				{
					rdo_Plan_Instock.setChecked(false);
					rdo_Plan_Storage.setChecked(false);
					rdo_Plan_Retrieval.setChecked(false);
					rdo_Plan_Picking.setChecked(false);
					rdo_Plan_Shipping.setChecked(true);
				}
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
				//#CM687591
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

	//#CM687592
	// Package methods -----------------------------------------------

	//#CM687593
	// Protected methods ---------------------------------------------

	//#CM687594
	// Private methods -----------------------------------------------

	//#CM687595
	// Event handler methods -----------------------------------------
	//#CM687596
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687597
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687598
	/** 
	 * Clicking on Menu button invokes this. <BR>
	 * <BR>
	 * Shift to the Menu screen.<BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM687599
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687600
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687601
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687602
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687603
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM687604
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687605
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687606
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM687607
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM687608
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Plan_Instock_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687609
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Plan_Instock_Click(ActionEvent e) throws Exception
	{
	}

	//#CM687610
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Plan_Storage_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687611
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Plan_Storage_Click(ActionEvent e) throws Exception
	{
	}

	//#CM687612
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Plan_Retrieval_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687613
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Plan_Retrieval_Click(ActionEvent e) throws Exception
	{
	}

	//#CM687614
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Plan_Picking_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687615
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Plan_Picking_Click(ActionEvent e) throws Exception
	{
	}

	//#CM687616
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Plan_Shipping_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687617
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Plan_Shipping_Click(ActionEvent e) throws Exception
	{
	}

	//#CM687618
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Next_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687619
	/** 
	 * Clicking on Next button invokes this. <BR>
	 * <BR>
	 * Summary: Shift to the Setting screen from a field item in the Input area. <BR>
	 * <BR>
	 * 1.Check for input in the input item (count) in the input area. (Mandatory Input, number of characters, and attribution of characters) <BR>
	 * 2.Shift to the next screen (Setting screen). <BR>
	 * 3.Maintain the contents of the input area. <BR>
	 * 4.Set the initial value of the cursor to Worker Code. <BR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Next_Click(ActionEvent e) throws Exception
	{
		//#CM687620
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);

		//#CM687621
		// Check for input.
		txt_WorkerCode.validate();
		txt_Password.validate();

		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			SystemParameter param = new SystemParameter();

			//#CM687622
			// Obtain the connection.

			WmsScheduler schedule = new DefineLoadDataMenuSCH();

			//#CM687623
			// Set the value for the parameter.
			//#CM687624
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM687625
			// Password
			param.setPassword(txt_Password.getText());

			//#CM687626
			// Receiving plan data
			if (rdo_Plan_Instock.getChecked())
			{
				param.setSelectDefineLoadData(SystemParameter.SELECTDEFINELOADDATA_INSTOCK);
			}
			//#CM687627
			// Storage plan data
			if (rdo_Plan_Storage.getChecked())
			{
				param.setSelectDefineLoadData(SystemParameter.SELECTDEFINELOADDATA_STORAGE);
			}
			//#CM687628
			// Picking plan data
			if (rdo_Plan_Retrieval.getChecked())
			{
				param.setSelectDefineLoadData(SystemParameter.SELECTDEFINELOADDATA_RETRIEVAL);
			}
			//#CM687629
			// Sorting Plan data
			if (rdo_Plan_Picking.getChecked())
			{
				param.setSelectDefineLoadData(SystemParameter.SELECTDEFINELOADDATA_SORTING);
			}
			//#CM687630
			// Shipping plan data
			if (rdo_Plan_Shipping.getChecked())
			{
				param.setSelectDefineLoadData(SystemParameter.SELECTDEFINELOADDATA_SHIPPING);
			}

			if (schedule.nextCheck(conn, param))
			{
				//#CM687631
				// Receiving plan data
				if (rdo_Plan_Instock.getChecked())
				{
					forward("/system/defineloaddatamenu/DefineLoadDataMenuInstock.do");
				}
				//#CM687632
				// Storage plan data
				else if (rdo_Plan_Storage.getChecked())
				{
					forward("/system/defineloaddatamenu/DefineLoadDataMenuStorage.do");
				}
				//#CM687633
				// Picking plan data
				else if (rdo_Plan_Retrieval.getChecked())
				{
					forward("/system/defineloaddatamenu/DefineLoadDataMenuRetrieval.do");
				}
				//#CM687634
				// Sorting Plan data
				else if (rdo_Plan_Picking.getChecked())
				{
					forward("/system/defineloaddatamenu/DefineLoadDataMenuSorting.do");
				}
				//#CM687635
				// Shipping plan data
				else if (rdo_Plan_Shipping.getChecked())
				{
					//#CM687636
					// Shit to the screen for setting a field item for loading the data (Picking).
					forward("/system/defineloaddatamenu/DefineLoadDataMenuShipping.do");
				}

				//#CM687637
				// Store the worker code entered via screen into ViewState.
				getViewState().setString("WORKERCODE", txt_WorkerCode.getText());
				//#CM687638
				// Store the password entered via screen into ViewState.
				getViewState().setString("PASSWORD", txt_Password.getText());
				//#CM687639
				// Store the field items selected via screen into ViewState.
				getViewState().setString("SELECT", param.getSelectDefineLoadData());
				//#CM687640
				// Store the screen title into the ViewState.
				getViewState().setString("TITLE", lbl_SettingName.getResourceKey());
			}
			else
			{
				if (schedule.getMessage() != null)
				{
					//#CM687641
					// Display a message.
					message.setMsgResourceKey(schedule.getMessage());
				}
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			//#CM687642
			// Set the initial value of the focus to Worker Code.
			setFocus(txt_WorkerCode);
			try
			{
				//#CM687643
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

	//#CM687644
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687645
	/** 
	 * Clicking on Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area.<BR>
	 * <BR>
	 * <DIR>
	 *   Reset the field items in Input area except for Worker Code and Password to their default value.<BR>
	 *   <DIR>
	 *     For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method. <BR>
	 *   </DIR>
	 *   Set the initial value of the cursor to Worker Code. <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM687646
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);

		Connection conn = null;
		try
		{
			//#CM687647
			// Default setting of radio button.
			if (rdo_Plan_Instock.getEnabled())
			{
				rdo_Plan_Instock.setChecked(true);
				rdo_Plan_Storage.setChecked(false);
				rdo_Plan_Retrieval.setChecked(false);
				rdo_Plan_Picking.setChecked(false);
				rdo_Plan_Shipping.setChecked(false);
			}
			else if (rdo_Plan_Storage.getEnabled())
			{
				rdo_Plan_Instock.setChecked(false);
				rdo_Plan_Storage.setChecked(true);
				rdo_Plan_Retrieval.setChecked(false);
				rdo_Plan_Picking.setChecked(false);
				rdo_Plan_Shipping.setChecked(false);
			}
			else if (rdo_Plan_Retrieval.getEnabled())
			{
				rdo_Plan_Instock.setChecked(false);
				rdo_Plan_Storage.setChecked(false);
				rdo_Plan_Retrieval.setChecked(true);
				rdo_Plan_Picking.setChecked(false);
				rdo_Plan_Shipping.setChecked(false);
			}
			else if (rdo_Plan_Picking.getEnabled())
			{
				rdo_Plan_Instock.setChecked(false);
				rdo_Plan_Storage.setChecked(false);
				rdo_Plan_Retrieval.setChecked(false);
				rdo_Plan_Picking.setChecked(true);
				rdo_Plan_Shipping.setChecked(false);
			}
			else if (rdo_Plan_Shipping.getEnabled())
			{
				rdo_Plan_Instock.setChecked(false);
				rdo_Plan_Storage.setChecked(false);
				rdo_Plan_Retrieval.setChecked(false);
				rdo_Plan_Picking.setChecked(false);
				rdo_Plan_Shipping.setChecked(true);
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
				//#CM687648
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

	//#CM687649
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM687650
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

}
//#CM687651
//end of class
