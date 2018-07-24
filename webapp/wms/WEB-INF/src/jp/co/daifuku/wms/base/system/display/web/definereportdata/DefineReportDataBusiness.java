// $Id: DefineReportDataBusiness.java,v 1.2 2006/11/13 08:18:49 suresh Exp $

//#CM688621
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.definereportdata;
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
import jp.co.daifuku.wms.base.system.schedule.DefineReportDataSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM688622
/**
 * Designer :  A.Nagasawa <BR>
 * Maker :     K.Mukai <BR>
 * <BR>
 * Allow this class to provide a screen to set field items for reporting data.<BR>
 * Set the contents entered via screen for the parameter. Allow the schedule to search based on the parameter.<BR>
 * Set a value and shift via the schedule to the next field item.
 * <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * Process by clicking "Next" button(btn_Next_Click(ActionEvent e)method) <BR>
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
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/09</TD><TD>A.Nagasawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:18:49 $
 * @author  $Author: suresh $
 */
public class DefineReportDataBusiness extends DefineReportData implements WMSConstants
{
	//#CM688623
	// Class fields --------------------------------------------------

	//#CM688624
	// Class variables -----------------------------------------------

	//#CM688625
	// Class method --------------------------------------------------

	//#CM688626
	// Constructors --------------------------------------------------

	//#CM688627
	// Public methods ------------------------------------------------

	//#CM688628
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
			//#CM688629
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM688630
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM688631
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM688632
			//Set the path to the help file.
			//#CM688633
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
	}
	//#CM688634
	/**
	 * Show the Initial Display.<BR>
	 * <BR>
	 * <DIR>
	 *   Worker Code [None] <BR>
	 *   Password [None] <BR>
	 *   Package for setting [Tick off the initially displayed package / Not ticked off the other displays] <BR>
	 *   <BR>
	 *   Set the cursor for Worker code. <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM688635
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);

		Connection conn = null;
		try
		{
			//#CM688636
			// Set the title when returning from the second screen.
			if (!StringUtil.isBlank(getViewState().getString("TITLE")))
			{
				lbl_SettingName.setResourceKey(getViewState().getString("TITLE"));
			}

			//#CM688637
			// Set up the tab so as to raise its left side.
			tab_RptClmSet.setSelectedIndex(1);

			//#CM688638
			// Obtain the Worker Code that was set in the original screen, via the ViewState.
			if (!StringUtil.isBlank(getViewState().getString("WORKERCODE")))
			{
				txt_WorkerCode.setText(getViewState().getString("WORKERCODE"));
			}
			//#CM688639
			// Obtain the password that was set in the original screen, via the ViewState.
			if (!StringUtil.isBlank(getViewState().getString("PASSWORD")))
			{
				txt_Password.setText(getViewState().getString("PASSWORD"));
			}
			//#CM688640
			// Obtain the selected item that was set in the original screen, via the ViewState.
			if (!StringUtil.isBlank(getViewState().getString("SELECT")))
			{
				if (getViewState()
					.getString("SELECT")
					.equals(SystemParameter.SELECTDEFINEREPORTDATA_INSTOCK))
				{
					rdo_ResultInstock.setChecked(true);
					rdo_ResultStorage.setChecked(false);
					rdo_ResultRetrieval.setChecked(false);
					rdo_ResultPicking.setChecked(false);
					rdo_ResultShipping.setChecked(false);
					rdo_ResultStockMove.setChecked(false);
					rdo_ResultInventory.setChecked(false);
					rdo_ResultNoPlanStrg.setChecked(false);
					rdo_ResultNoPlanRtrivl.setChecked(false);
				}
				else if (
					getViewState().getString("SELECT").equals(
						SystemParameter.SELECTDEFINEREPORTDATA_STORAGE))
				{
					rdo_ResultInstock.setChecked(false);
					rdo_ResultStorage.setChecked(true);
					rdo_ResultRetrieval.setChecked(false);
					rdo_ResultPicking.setChecked(false);
					rdo_ResultShipping.setChecked(false);
					rdo_ResultStockMove.setChecked(false);
					rdo_ResultInventory.setChecked(false);
					rdo_ResultNoPlanStrg.setChecked(false);
					rdo_ResultNoPlanRtrivl.setChecked(false);
				}
				else if (
					getViewState().getString("SELECT").equals(
						SystemParameter.SELECTDEFINEREPORTDATA_RETRIEVAL))
				{
					rdo_ResultInstock.setChecked(false);
					rdo_ResultStorage.setChecked(false);
					rdo_ResultRetrieval.setChecked(true);
					rdo_ResultPicking.setChecked(false);
					rdo_ResultShipping.setChecked(false);
					rdo_ResultStockMove.setChecked(false);
					rdo_ResultInventory.setChecked(false);
					rdo_ResultNoPlanStrg.setChecked(false);
					rdo_ResultNoPlanRtrivl.setChecked(false);
				}
				else if (
					getViewState().getString("SELECT").equals(
						SystemParameter.SELECTDEFINEREPORTDATA_SORTING))
				{
					rdo_ResultInstock.setChecked(false);
					rdo_ResultStorage.setChecked(false);
					rdo_ResultRetrieval.setChecked(false);
					rdo_ResultPicking.setChecked(true);
					rdo_ResultShipping.setChecked(false);
					rdo_ResultStockMove.setChecked(false);
					rdo_ResultInventory.setChecked(false);
					rdo_ResultNoPlanStrg.setChecked(false);
					rdo_ResultNoPlanRtrivl.setChecked(false);
				}
				else if (
					getViewState().getString("SELECT").equals(
						SystemParameter.SELECTDEFINEREPORTDATA_SHIPPING))
				{
					rdo_ResultInstock.setChecked(false);
					rdo_ResultStorage.setChecked(false);
					rdo_ResultRetrieval.setChecked(false);
					rdo_ResultPicking.setChecked(false);
					rdo_ResultShipping.setChecked(true);
					rdo_ResultStockMove.setChecked(false);
					rdo_ResultInventory.setChecked(false);
					rdo_ResultNoPlanStrg.setChecked(false);
					rdo_ResultNoPlanRtrivl.setChecked(false);
				}
				else if (
					getViewState().getString("SELECT").equals(
						SystemParameter.SELECTDEFINEREPORTDATA_MOVEMENT))
				{
					rdo_ResultInstock.setChecked(false);
					rdo_ResultStorage.setChecked(false);
					rdo_ResultRetrieval.setChecked(false);
					rdo_ResultPicking.setChecked(false);
					rdo_ResultShipping.setChecked(false);
					rdo_ResultStockMove.setChecked(true);
					rdo_ResultInventory.setChecked(false);
					rdo_ResultNoPlanStrg.setChecked(false);
					rdo_ResultNoPlanRtrivl.setChecked(false);
				}
				else if (
					getViewState().getString("SELECT").equals(
						SystemParameter.SELECTDEFINEREPORTDATA_INVENTORY))
				{
					rdo_ResultInstock.setChecked(false);
					rdo_ResultStorage.setChecked(false);
					rdo_ResultRetrieval.setChecked(false);
					rdo_ResultPicking.setChecked(false);
					rdo_ResultShipping.setChecked(false);
					rdo_ResultStockMove.setChecked(false);
					rdo_ResultInventory.setChecked(true);
					rdo_ResultNoPlanStrg.setChecked(false);
					rdo_ResultNoPlanRtrivl.setChecked(false);
				}
				else if (
					getViewState().getString("SELECT").equals(
						SystemParameter.SELECTDEFINEREPORTDATA_NOPLANSTORAGE))
				{
					rdo_ResultInstock.setChecked(false);
					rdo_ResultStorage.setChecked(false);
					rdo_ResultRetrieval.setChecked(false);
					rdo_ResultPicking.setChecked(false);
					rdo_ResultShipping.setChecked(false);
					rdo_ResultStockMove.setChecked(false);
					rdo_ResultInventory.setChecked(false);
					rdo_ResultNoPlanStrg.setChecked(true);
					rdo_ResultNoPlanRtrivl.setChecked(false);
				}
				else if (
					getViewState().getString("SELECT").equals(
						SystemParameter.SELECTDEFINEREPORTDATA_NOPLANRETRIEVAL))
				{
					rdo_ResultInstock.setChecked(false);
					rdo_ResultStorage.setChecked(false);
					rdo_ResultRetrieval.setChecked(false);
					rdo_ResultPicking.setChecked(false);
					rdo_ResultShipping.setChecked(false);
					rdo_ResultStockMove.setChecked(false);
					rdo_ResultInventory.setChecked(false);
					rdo_ResultNoPlanStrg.setChecked(false);
					rdo_ResultNoPlanRtrivl.setChecked(true);
				}
			}

			//#CM688641
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			SystemParameter initParam = new SystemParameter();
			WmsScheduler schedule = new DefineReportDataSCH();
			SystemParameter param = (SystemParameter) schedule.initFind(conn, initParam);

			//#CM688642
			// Enable to read-only for the field items that does not contain package.
			//#CM688643
			// Receiving result data
			if (!param.getSelectReportInstockData())
			{
				rdo_ResultInstock.setEnabled(false);
			}
			//#CM688644
			// Storage result data
			if (!param.getSelectReportStorageData())
			{
				rdo_ResultStorage.setEnabled(false);
			}
			//#CM688645
			// Picking result data
			if (!param.getSelectReportRetrievalData())
			{
				rdo_ResultRetrieval.setEnabled(false);
			}
			//#CM688646
			// Sorting result data
			if (!param.getSelectReportSortingData())
			{
				rdo_ResultPicking.setEnabled(false);
			}
			//#CM688647
			// Shipping result data
			if (!param.getSelectReportShippingData())
			{
				rdo_ResultShipping.setEnabled(false);
			}
			//#CM688648
			// Stock relocation result data
			if (!param.getSelectReportMovementData())
			{
				rdo_ResultStockMove.setEnabled(false);
			}
			//#CM688649
			// Inventory Check result data
			if (!param.getSelectReportInventoryData())
			{
				rdo_ResultInventory.setEnabled(false);
			}
			//#CM688650
			// Unplanned storage result data
			if (!param.getSelectReportNoPlanStorageData())
			{
				rdo_ResultNoPlanStrg.setEnabled(false);
			}
			//#CM688651
			// Unplanned picking result data
			if (!param.getSelectReportNoPlanRetrievalData())
			{
				rdo_ResultNoPlanRtrivl.setEnabled(false);
			}			

			if (StringUtil.isBlank(getViewState().getString("SELECT")))
			{
				//#CM688652
				// Default setting of radio button.
				if (rdo_ResultInstock.getEnabled())
				{
					rdo_ResultInstock.setChecked(true);
					rdo_ResultStorage.setChecked(false);
					rdo_ResultRetrieval.setChecked(false);
					rdo_ResultPicking.setChecked(false);
					rdo_ResultShipping.setChecked(false);
					rdo_ResultStockMove.setChecked(false);
					rdo_ResultInventory.setChecked(false);
					rdo_ResultNoPlanStrg.setChecked(false);
					rdo_ResultNoPlanRtrivl.setChecked(false);
				}
				else if (rdo_ResultStorage.getEnabled())
				{
					rdo_ResultInstock.setChecked(false);
					rdo_ResultStorage.setChecked(true);
					rdo_ResultRetrieval.setChecked(false);
					rdo_ResultPicking.setChecked(false);
					rdo_ResultShipping.setChecked(false);
					rdo_ResultStockMove.setChecked(false);
					rdo_ResultInventory.setChecked(false);
					rdo_ResultNoPlanStrg.setChecked(false);
					rdo_ResultNoPlanRtrivl.setChecked(false);
				}
				else if (rdo_ResultRetrieval.getEnabled())
				{
					rdo_ResultInstock.setChecked(false);
					rdo_ResultStorage.setChecked(false);
					rdo_ResultRetrieval.setChecked(true);
					rdo_ResultPicking.setChecked(false);
					rdo_ResultShipping.setChecked(false);
					rdo_ResultStockMove.setChecked(false);
					rdo_ResultInventory.setChecked(false);
					rdo_ResultNoPlanStrg.setChecked(false);
					rdo_ResultNoPlanRtrivl.setChecked(false);
				}
				else if (rdo_ResultPicking.getEnabled())
				{
					rdo_ResultInstock.setChecked(false);
					rdo_ResultStorage.setChecked(false);
					rdo_ResultRetrieval.setChecked(false);
					rdo_ResultPicking.setChecked(true);
					rdo_ResultShipping.setChecked(false);
					rdo_ResultStockMove.setChecked(false);
					rdo_ResultInventory.setChecked(false);
					rdo_ResultNoPlanStrg.setChecked(false);
					rdo_ResultNoPlanRtrivl.setChecked(false);
				}
				else if (rdo_ResultShipping.getEnabled())
				{
					rdo_ResultInstock.setChecked(false);
					rdo_ResultStorage.setChecked(false);
					rdo_ResultRetrieval.setChecked(false);
					rdo_ResultPicking.setChecked(false);
					rdo_ResultShipping.setChecked(true);
					rdo_ResultStockMove.setChecked(false);
					rdo_ResultInventory.setChecked(false);
					rdo_ResultNoPlanStrg.setChecked(false);
					rdo_ResultNoPlanRtrivl.setChecked(false);
				}
				else if (rdo_ResultStockMove.getEnabled())
				{
					rdo_ResultInstock.setChecked(false);
					rdo_ResultStorage.setChecked(false);
					rdo_ResultRetrieval.setChecked(false);
					rdo_ResultPicking.setChecked(false);
					rdo_ResultShipping.setChecked(false);
					rdo_ResultStockMove.setChecked(true);
					rdo_ResultInventory.setChecked(false);
					rdo_ResultNoPlanStrg.setChecked(false);
					rdo_ResultNoPlanRtrivl.setChecked(false);
				}
				else if (rdo_ResultInventory.getEnabled())
				{
					rdo_ResultInstock.setChecked(false);
					rdo_ResultStorage.setChecked(false);
					rdo_ResultRetrieval.setChecked(false);
					rdo_ResultPicking.setChecked(false);
					rdo_ResultShipping.setChecked(false);
					rdo_ResultStockMove.setChecked(false);
					rdo_ResultInventory.setChecked(true);
					rdo_ResultNoPlanStrg.setChecked(false);
					rdo_ResultNoPlanRtrivl.setChecked(false);
				}
				else if (rdo_ResultNoPlanStrg.getEnabled())
				{
					rdo_ResultInstock.setChecked(false);
					rdo_ResultStorage.setChecked(false);
					rdo_ResultRetrieval.setChecked(false);
					rdo_ResultPicking.setChecked(false);
					rdo_ResultShipping.setChecked(false);
					rdo_ResultStockMove.setChecked(false);
					rdo_ResultInventory.setChecked(false);
					rdo_ResultNoPlanStrg.setChecked(true);
					rdo_ResultNoPlanRtrivl.setChecked(false);
				}
				else if (rdo_ResultNoPlanRtrivl.getEnabled())
				{
					rdo_ResultInstock.setChecked(false);
					rdo_ResultStorage.setChecked(false);
					rdo_ResultRetrieval.setChecked(false);
					rdo_ResultPicking.setChecked(false);
					rdo_ResultShipping.setChecked(false);
					rdo_ResultStockMove.setChecked(false);
					rdo_ResultInventory.setChecked(false);
					rdo_ResultNoPlanStrg.setChecked(false);
					rdo_ResultNoPlanRtrivl.setChecked(true);
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
				//#CM688653
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

	//#CM688654
	// Package methods -----------------------------------------------

	//#CM688655
	// Protected methods ---------------------------------------------

	//#CM688656
	// Private methods -----------------------------------------------

	//#CM688657
	// Event handler methods -----------------------------------------
	//#CM688658
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688659
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688660
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688661
	/** 
	 * Clicking on Menu button invokes this.<BR>
	 * <BR>
	 * Summary: Allow this method to execute the following processes.<BR>
	 * <BR>
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

	//#CM688662
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688663
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688664
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688665
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688666
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM688667
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688668
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688669
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM688670
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM688671
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultInstock_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688672
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultInstock_Click(ActionEvent e) throws Exception
	{
	}

	//#CM688673
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultStorage_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688674
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultStorage_Click(ActionEvent e) throws Exception
	{
	}

	//#CM688675
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultRetrieval_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688676
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultRetrieval_Click(ActionEvent e) throws Exception
	{
	}

	//#CM688677
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultPicking_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688678
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultPicking_Click(ActionEvent e) throws Exception
	{
	}

	//#CM688679
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultShipping_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688680
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultShipping_Click(ActionEvent e) throws Exception
	{
	}

	//#CM688681
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultStockMove_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688682
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultStockMove_Click(ActionEvent e) throws Exception
	{
	}

	//#CM688683
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultInventory_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688684
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultInventory_Click(ActionEvent e) throws Exception
	{
	}

	//#CM688685
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Next_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688686
	/** 
	 * Clicking on "Next" button for setting field item for reporting data invokes this.<BR>
	 * <BR>
	 * Summary: Shift to the Setting screen from a field item in the Input area. <BR>
	 * <BR>
	 * <DIR>
	 *   1.Check for input in the input item (count) in the input area. (Mandatory Input, number of characters, and attribution of characters)<BR>
	 *   2.Start the schedule.<BR>
	 *   3.Shift to the next screen (Setting screen).<BR>
	 *   4.Maintain the contents of the input area.<BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Next_Click(ActionEvent e) throws Exception
	{
		//#CM688687
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);

		//#CM688688
		// Check for input.
		txt_WorkerCode.validate();
		txt_Password.validate();

		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			SystemParameter param = new SystemParameter();
			WmsScheduler schedule = new DefineReportDataSCH();

			//#CM688689
			// Set the value for the parameter.
			//#CM688690
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM688691
			// Password
			param.setPassword(txt_Password.getText());

			//#CM688692
			// Receiving result data
			if (rdo_ResultInstock.getChecked())
			{
				param.setSelectDefineReportData(SystemParameter.SELECTDEFINEREPORTDATA_INSTOCK);
			}
			//#CM688693
			// Storage result data
			if (rdo_ResultStorage.getChecked())
			{
				param.setSelectDefineReportData(SystemParameter.SELECTDEFINEREPORTDATA_STORAGE);
			}
			//#CM688694
			// Picking result data
			if (rdo_ResultRetrieval.getChecked())
			{
				param.setSelectDefineReportData(SystemParameter.SELECTDEFINEREPORTDATA_RETRIEVAL);
			}
			//#CM688695
			// Sorting result data
			if (rdo_ResultPicking.getChecked())
			{
				param.setSelectDefineReportData(SystemParameter.SELECTDEFINEREPORTDATA_SORTING);
			}
			//#CM688696
			// Shipping result data
			if (rdo_ResultShipping.getChecked())
			{
				param.setSelectDefineReportData(SystemParameter.SELECTDEFINEREPORTDATA_SHIPPING);
			}
			//#CM688697
			// Stock relocation result data
			if (rdo_ResultStockMove.getChecked())
			{
				param.setSelectDefineReportData(SystemParameter.SELECTDEFINEREPORTDATA_MOVEMENT);
			}
			//#CM688698
			// Inventory Check result data
			if (rdo_ResultInventory.getChecked())
			{
				param.setSelectDefineReportData(SystemParameter.SELECTDEFINEREPORTDATA_INVENTORY);
			}
			//#CM688699
			// Unplanned storage result data
			if (rdo_ResultNoPlanStrg.getChecked())
			{
				param.setSelectDefineReportData(SystemParameter.SELECTDEFINEREPORTDATA_NOPLANSTORAGE);
			}
			//#CM688700
			// Unplanned picking result data
			if (rdo_ResultNoPlanRtrivl.getChecked())
			{
				param.setSelectDefineReportData(SystemParameter.SELECTDEFINEREPORTDATA_NOPLANRETRIEVAL);
			}

			if (schedule.nextCheck(conn, param))
			{
				//#CM688701
				// Receiving result data
				if (rdo_ResultInstock.getChecked())
				{
					//#CM688702
					// Shift to the screen for setting the fields for reporting data (Shipping) .
					forward("/system/definereportdata/DefineReportDataInstock.do");
				}
				//#CM688703
				// Storage result data
				else if (rdo_ResultStorage.getChecked())
				{
					//#CM688704
					// Shift to the screen for setting the fields for reporting data (Storage) .
					forward("/system/definereportdata/DefineReportDataStorage.do");
				}
				//#CM688705
				// Picking result data
				else if (rdo_ResultRetrieval.getChecked())
				{
					//#CM688706
					// Shift to the screen for setting the fields for reporting data (Picking) .
					forward("/system/definereportdata/DefineReportDataRetrieval.do");
				}
				//#CM688707
				// Sorting result data
				else if (rdo_ResultPicking.getChecked())
				{
					//#CM688708
					// Shift to the screen for setting the fields for reporting data (Sorting) .
					forward("/system/definereportdata/DefineReportDataSorting.do");
				}
				//#CM688709
				// Shipping result data
				else if (rdo_ResultShipping.getChecked())
				{
					//#CM688710
					// Shift to the screen for setting the fields for reporting data (Picking) .
					forward("/system/definereportdata/DefineReportDataShipping.do");
				}
				//#CM688711
				// Stock relocation result data
				else if (rdo_ResultStockMove.getChecked())
				{
					//#CM688712
					// Shift to the screen for setting the fields for reporting data (Stock Relocation) .
					forward("/system/definereportdata/DefineReportDataStockMove.do");
				}
				//#CM688713
				// Inventory Check result data
				else if (rdo_ResultInventory.getChecked())
				{
					//#CM688714
					// Shift to the screen for setting the fields for reporting data (Inventory Check) .
					forward("/system/definereportdata/DefineReportDataInventory.do");
				}
				//#CM688715
				// Unplanned storage result data
				else if (rdo_ResultNoPlanStrg.getChecked())
				{
					//#CM688716
					// Shift to the screen for setting the fields for reporting data (Unplanned Storage) .
					forward("/system/definereportdata/DefineReportDataNoPlanStorage.do");
				}
				//#CM688717
				// Unplanned picking result data
				else if (rdo_ResultNoPlanRtrivl.getChecked())
				{
					//#CM688718
					// Shift to the screen for setting the fields for reporting data (Unplanned Picking) .
					forward("/system/definereportdata/DefineReportDataNoPlanRetrieval.do");
				}

				//#CM688719
				// Store the worker code entered via screen into ViewState.
				getViewState().setString("WORKERCODE", txt_WorkerCode.getText());
				//#CM688720
				// Store the password entered via screen into ViewState.
				getViewState().setString("PASSWORD", txt_Password.getText());
				//#CM688721
				// Store the field items selected via screen into ViewState.
				getViewState().setString("SELECT", param.getSelectDefineReportData());
				//#CM688722
				// Store the screen title into the ViewState.
				getViewState().setString("TITLE", lbl_SettingName.getResourceKey());
			}
			else
			{
				if (schedule.getMessage() != null)
				{
					//#CM688723
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
			//#CM688724
			// Set the initial value of the focus to Worker Code.
			setFocus(txt_WorkerCode);
			try
			{
				//#CM688725
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

	//#CM688726
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688727
	/** 
	 * Clicking on Clear button for field items set for reporting data invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 * <DIR>
	 *   1.Clear the field item in Input area.<BR>
	 *   <DIR>
	 *     Disable to clear work code and password. <BR>
	 *   </DIR>
	 *   2.Set the cursor for Worker code. <BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM688728
		// Set the initial value of the focus to Worker Code.
		setFocus(txt_WorkerCode);

		Connection conn = null;
		try
		{
			
			//#CM688729
			// Default setting of radio button.
			if (rdo_ResultInstock.getEnabled())
			{
				rdo_ResultInstock.setChecked(true);
				rdo_ResultStorage.setChecked(false);
				rdo_ResultRetrieval.setChecked(false);
				rdo_ResultPicking.setChecked(false);
				rdo_ResultShipping.setChecked(false);
				rdo_ResultStockMove.setChecked(false);
				rdo_ResultInventory.setChecked(false);
				rdo_ResultNoPlanStrg.setChecked(false);
				rdo_ResultNoPlanRtrivl.setChecked(false);
			}
			else if (rdo_ResultStorage.getEnabled())
			{
				rdo_ResultInstock.setChecked(false);
				rdo_ResultStorage.setChecked(true);
				rdo_ResultRetrieval.setChecked(false);
				rdo_ResultPicking.setChecked(false);
				rdo_ResultShipping.setChecked(false);
				rdo_ResultStockMove.setChecked(false);
				rdo_ResultInventory.setChecked(false);
				rdo_ResultNoPlanStrg.setChecked(false);
				rdo_ResultNoPlanRtrivl.setChecked(false);
			}
			else if (rdo_ResultRetrieval.getEnabled())
			{
				rdo_ResultInstock.setChecked(false);
				rdo_ResultStorage.setChecked(false);
				rdo_ResultRetrieval.setChecked(true);
				rdo_ResultPicking.setChecked(false);
				rdo_ResultShipping.setChecked(false);
				rdo_ResultStockMove.setChecked(false);
				rdo_ResultInventory.setChecked(false);
				rdo_ResultNoPlanStrg.setChecked(false);
				rdo_ResultNoPlanRtrivl.setChecked(false);
			}
			else if (rdo_ResultPicking.getEnabled())
			{
				rdo_ResultInstock.setChecked(false);
				rdo_ResultStorage.setChecked(false);
				rdo_ResultRetrieval.setChecked(false);
				rdo_ResultPicking.setChecked(true);
				rdo_ResultShipping.setChecked(false);
				rdo_ResultStockMove.setChecked(false);
				rdo_ResultInventory.setChecked(false);
				rdo_ResultNoPlanStrg.setChecked(false);
				rdo_ResultNoPlanRtrivl.setChecked(false);
			}
			else if (rdo_ResultShipping.getEnabled())
			{
				rdo_ResultInstock.setChecked(false);
				rdo_ResultStorage.setChecked(false);
				rdo_ResultRetrieval.setChecked(false);
				rdo_ResultPicking.setChecked(false);
				rdo_ResultShipping.setChecked(true);
				rdo_ResultStockMove.setChecked(false);
				rdo_ResultInventory.setChecked(false);
				rdo_ResultNoPlanStrg.setChecked(false);
				rdo_ResultNoPlanRtrivl.setChecked(false);
			}
			else if (rdo_ResultStockMove.getEnabled())
			{
				rdo_ResultInstock.setChecked(false);
				rdo_ResultStorage.setChecked(false);
				rdo_ResultRetrieval.setChecked(false);
				rdo_ResultPicking.setChecked(false);
				rdo_ResultShipping.setChecked(false);
				rdo_ResultStockMove.setChecked(true);
				rdo_ResultInventory.setChecked(false);
				rdo_ResultNoPlanStrg.setChecked(false);
				rdo_ResultNoPlanRtrivl.setChecked(false);
			}
			else if (rdo_ResultInventory.getEnabled())
			{
				rdo_ResultInstock.setChecked(false);
				rdo_ResultStorage.setChecked(false);
				rdo_ResultRetrieval.setChecked(false);
				rdo_ResultPicking.setChecked(false);
				rdo_ResultShipping.setChecked(false);
				rdo_ResultStockMove.setChecked(false);
				rdo_ResultInventory.setChecked(true);
				rdo_ResultNoPlanStrg.setChecked(false);
				rdo_ResultNoPlanRtrivl.setChecked(false);
			}
			else if (rdo_ResultNoPlanStrg.getEnabled())
			{
				rdo_ResultInstock.setChecked(false);
				rdo_ResultStorage.setChecked(false);
				rdo_ResultRetrieval.setChecked(false);
				rdo_ResultPicking.setChecked(false);
				rdo_ResultShipping.setChecked(false);
				rdo_ResultStockMove.setChecked(false);
				rdo_ResultInventory.setChecked(false);
				rdo_ResultNoPlanStrg.setChecked(true);
				rdo_ResultNoPlanRtrivl.setChecked(false);
			}
			else if (rdo_ResultNoPlanRtrivl.getEnabled())
			{
				rdo_ResultInstock.setChecked(false);
				rdo_ResultStorage.setChecked(false);
				rdo_ResultRetrieval.setChecked(false);
				rdo_ResultPicking.setChecked(false);
				rdo_ResultShipping.setChecked(false);
				rdo_ResultStockMove.setChecked(false);
				rdo_ResultInventory.setChecked(false);
				rdo_ResultNoPlanStrg.setChecked(false);
				rdo_ResultNoPlanRtrivl.setChecked(true);
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
				//#CM688730
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

	//#CM688731
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM688732
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_RptClmSet_Click(ActionEvent e) throws Exception
	{
	}

	//#CM688733
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultNoPlanStrg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688734
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultNoPlanStrg_Click(ActionEvent e) throws Exception
	{
	}

	//#CM688735
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultNoPlanRtrivl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688736
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ResultNoPlanRtrivl_Click(ActionEvent e) throws Exception
	{
	}

	//#CM688737
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM688738
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_1_Click(ActionEvent e) throws Exception
	{
	}
}
//#CM688739
//end of class
