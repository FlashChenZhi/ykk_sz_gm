// $Id: InventoryInquiryBusiness.java,v 1.2 2006/12/07 08:57:48 suresh Exp $

//#CM566956
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.inventoryinquiry;
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
import jp.co.daifuku.wms.storage.display.web.listbox.listinventorylist.ListInventoryListBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.listinventorylocation.ListInventoryLocationBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststoragelocation.ListStorageLocationBusiness;
import jp.co.daifuku.wms.storage.schedule.InventoryInquirySCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM566957
/**
 * Designer : T.Yoshino<BR>
 * Maker : T.Yoshino<BR>
 * <BR>
 * The Inventory Work inquiry screen class.<BR>
 * Hand over Parameter to the schedule which does the Inventory Work inquiry issue.<BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Display button is pressed(<CODE>btn_PDisplay_Click<CODE>) <BR>
 * <BR>
 * <DIR>
 *  Set the content input from the screen in Parameter.  
 *  The schedule retrieves data for the display based on the Parameter. <BR>
 *  Receive the retrieval result from the schedule, and call the Inventory Work inquiry screen. <BR>
 *  <BR>
 *  [Parameter] *Mandatory input<BR>
 *  <DIR>
 *   Consignor Code*<BR>
 *   Start Location<BR>
 *   End Location<BR>
 *   Item Code<BR>
 *   Status<BR>
 *   <DIR>
 *    All<BR>
 *    Stock difference<BR>
 *    Same Stock<BR>
 *   </DIR>
 *  </DIR>
 * </DIR>
 * 
 * <BR>
 * 2.Processing when Print button is pressed(<CODE>btn_Print_Click<CODE>) <BR>
 * <BR>
 * <DIR>
 *  Set the content input from the screen in Parameter.  <BR>
 *  The schedule retrieves and prints data based on the Parameter.  <BR>
 *  The schedule must return true when it succeeds in the print and return false when failing. <BR>
 *  <BR>
 *  [Parameter] *Mandatory input<BR>
 *  <DIR>
 *   Consignor Code*<BR>
 *   Start Location<BR>
 *   End Location<BR>
 *   Item Code<BR>
 *   Status<BR>
 *   <DIR>
 *    All<BR>
 *    Stock difference<BR>
 *    Same Stock<BR>
 *   </DIR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/18</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:48 $
 * @author  $Author: suresh $
 */
public class InventoryInquiryBusiness extends InventoryInquiry implements WMSConstants
{
	//#CM566958
	// Class fields --------------------------------------------------
	
	
	//#CM566959
	// Whether the dialog called from which control is maintained.  : Print button
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";

	//#CM566960
	// Class variables -----------------------------------------------

	//#CM566961
	// Class method --------------------------------------------------

	//#CM566962
	// Constructors --------------------------------------------------

	//#CM566963
	// Public methods ------------------------------------------------

	//#CM566964
	/**
	 * Initialize the screen. <BR>
	 * Outline:Display initial data in the screen.  <BR>
	 * <BR>
	 * <DIR>
	 *    1.Display the title. <BR>
	 *    2.Initialize the input area. <BR>
	 *    3.Set the cursor in Consignor Code. <BR>
	 * </DIR>
	 * <BR>
	 * Item Name[Initial value] <BR>
	 * <DIR>
	 * 		Consignor Code[Display initial when there is only one corresponding Consignor. ] <BR>
	 * 		Start Location[None]<BR>
	 * 		End Location[None]<BR>
	 * 		Item Code[None]<BR>
	 * 		Status[All]<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM566965
		// Set Initial value in each input field. 
		//#CM566966
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM566967
		// Start Location
		txt_StartLocation.setText("");
		//#CM566968
		// End Location
		txt_EndLocation.setText("");
		//#CM566969
		// Item Code
		txt_ItemCode.setText("");
		//#CM566970
		// Status
		rdo_Sts_All.setChecked(true);
		rdo_Sts_StockDiff.setChecked(false);
		rdo_Sts_StockEqualQty.setChecked(false);

		//#CM566971
		// Cursor transition to Consignor Code
		setFocus(txt_ConsignorCode);

	}

	//#CM566972
	/**
	 * It is called before the call of each control event.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM566973
			// Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM566974
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM566975
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}

	}
	
	//#CM566976
	/**
	 * This Method is called when returning from the dialog button.<BR>
	 * Override <CODE>page_ConfirmBack</CODE> defined in <CODE>Page</CODE>.<BR>
	 * <BR>
	 * Outline : Execute the corresponding processing when "Yes" is selected from the dialog.<BR>
	 * <BR>
	 * <DIR>
	 * 	    1.Check from which dialog returns.<BR>
	 *      2.Check whether "Yes" or "No" was selected from the dialog.<BR>
	 *      3.Start Schedule when [Yes] is selected.<BR>
	 *      4.Display the result of the schedule in the Message area. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		try
		{
			//#CM566977
			// Set focus in Consignor Code
			setFocus(txt_ConsignorCode);

			//#CM566978
			// Check from which dialog return
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM566979
			// True when [Yes] is pressed in dialog
			//#CM566980
			// False when [No] is pressed in dialog
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM566981
			// End processing when [No] is pressed. 
			//#CM566982
			// Setting Message here is unnecessary. 
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
			//#CM566983
			// Turn off the flag because the operation of the dialog ended. 
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM566984
		// Start Print scheduling.
		Connection conn = null;
		try
		{
			//#CM566985
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM566986
			// Set the input value in Parameter. 
			StorageSupportParameter[] param = new StorageSupportParameter[1];
			param[0] = createParameter();

			//#CM566987
			// Start scheduling.
			WmsScheduler schedule = new InventoryInquirySCH();
			schedule.startSCH(conn, param);
			
			//#CM566988
			// Set Message
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
				//#CM566989
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

	//#CM566990
	/**
	 * When returning from the pop up window, this Method is called. <BR>
	 * Override the <CODE>page_DlgBack</CODE> set in <CODE>Page</CODE>.<BR>
	 * <BR>
	 * Outline:Acquire and set Return Data of the retrieval screen. <BR>
	 * <BR>
	 * <DIR>
	 *      1.Acquire the value returned from the retrieval screen of pop up. <BR>
	 *      2.Set it on the screen when the value is not empty. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM566991
		// Parameter selected from list box is acquired. 
		//#CM566992
		// Consignor Code
		String consignorcode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM566993
		// Start Location
		String startlocation = param.getParameter(ListInventoryLocationBusiness.STARTLOCATION_KEY);
		//#CM566994
		// End Location
		String endlocation = param.getParameter(ListInventoryLocationBusiness.ENDLOCATION_KEY);
		//#CM566995
		// Item Code
		String itemcode = param.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM566996
		// Set the value when it is not empty. 
		//#CM566997
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM566998
		// Start Location
		if (!StringUtil.isBlank(startlocation))
		{
			txt_StartLocation.setText(startlocation);
		}
		//#CM566999
		// End Location
		if (!StringUtil.isBlank(endlocation))
		{
			txt_EndLocation.setText(endlocation);
		}
		//#CM567000
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM567001
		// Set focus in Consignor Code
		setFocus(txt_ConsignorCode);

	}

	//#CM567002
	// Package methods -----------------------------------------------

	//#CM567003
	// Protected methods ---------------------------------------------
	
	//#CM567004
	/**
	 * Generate the Parameter object which sets the input value of the input area. <BR>
	 * <BR>
	 * @return Parameter object which contains input value of input area
	 */
	protected StorageSupportParameter createParameter()
	{
		//#CM567005
		// Set schedule Parameter. 
		StorageSupportParameter param = new StorageSupportParameter();
		
		//#CM567006
		// Consignor Code
		param.setConsignorCode(txt_ConsignorCode.getText());
		//#CM567007
		// Start Location
		param.setFromLocation(txt_StartLocation.getText());
		//#CM567008
		// End Location
		param.setToLocation(txt_EndLocation.getText());
		//#CM567009
		// Item Code
		param.setItemCode(txt_ItemCode.getText());
		//#CM567010
		// Status				
		if (rdo_Sts_All.getChecked())
		{
			param.setDispStatus(StorageSupportParameter.DISP_STATUS_ALL);
		}
		else if (rdo_Sts_StockDiff.getChecked())
		{
			param.setDispStatus(StorageSupportParameter.DISP_STATUS_DIFFERENCE);
		}
		
		else if (rdo_Sts_StockEqualQty.getChecked())
		{
			param.setDispStatus(StorageSupportParameter.DISP_STATUS_EQUAL);
		}
		return param;
	}

	//#CM567011
	// Private methods -----------------------------------------------
	//#CM567012
	/**
	 * Method to acquire Consignor Code from the schedule.  <BR>
	 * <BR>
	 * Outline : Return Consignor Code acquired from the schedule. <BR>
	 * <DIR>
	 *   1.Return correspondence Consignor when there is only one Consignor Code. Return the empty string when it is not so.  <BR>
	 * </DIR>
	 * <BR>
	 * @return Consignor Code
	 * @throws Exception It reports on all exceptions. 
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM567013
			// Acquire Consignor Code from the schedule. 
			WmsScheduler schedule = new InventoryInquirySCH();
			param = (StorageSupportParameter) schedule.initFind(conn, param);

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
			//#CM567014
			// Close the connection
			if (conn != null)
			{
				conn.close();
			}
		}
		return "";
	}
	//#CM567015
	// Event handler methods -----------------------------------------
	//#CM567016
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567017
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567018
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567019
	/** 
	 * It is called when the menu button is pressed.<BR>
	 * <BR>
	 * Outline : Change to the menu panel.<BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM567020
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567021
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567022
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM567023
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM567024
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM567025
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567026
	/** 
	 * It is called when the Consignor retrieval button is pressed.<BR>
	 * Outline : Set the search condition in Parameter, and display the Consignor list retrieval list box by the search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory input
	 * <BR>
	 * <DIR>
	 * 		Consignor Code <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PsearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM567027
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM567028
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM567029
		// Retrieval flag
		param.setParameter(ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY, StorageSupportParameter.SEARCH_INVENTORY);
		//#CM567030
		// Processing Screen -> Result screen
		redirect("/storage/listbox/liststorageconsignor/ListStorageConsignor.do", param, "/progress.do");
	}

	//#CM567031
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567032
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567033
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM567034
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM567035
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM567036
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchStartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567037
	/** 
	 * It is called when the Start Location retrieval button is pressed.<BR>
	 * Outline : Set the search condition in Parameter, and display the Location list retrieval list box by the search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory input
	 * <BR>
	 * <DIR>
	 * 		Consignor Code <BR>
	 * 		Start Location <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PsearchStartLocation_Click(ActionEvent e) throws Exception
	{
		//#CM567038
		//Set the search condition on the Start Location retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM567039
		//Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM567040
		// Start Location
		param.setParameter(ListInventoryLocationBusiness.LOCATION_KEY, txt_StartLocation.getText());
		//#CM567041
		// The beginning flag (Location range flag) is set. 
		param.setParameter(ListInventoryLocationBusiness.RANGELOCATION_KEY, StorageSupportParameter.RANGE_START);
		//#CM567042
		// Retrieval flag
		param.setParameter(ListInventoryLocationBusiness.SEARCHLOCATION_KEY, StorageSupportParameter.SEARCH_INVENTORY);

		//#CM567043
		// Processing Screen -> Result screen
		redirect("/storage/listbox/listinventorylocation/ListInventoryLocation.do", param, "/progress.do");
	}

	//#CM567044
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567045
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567046
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567047
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM567048
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM567049
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM567050
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchEndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567051
	/** 
	 * It is called when the End Location retrieval button is pressed.<BR>
	 * Outline : Set the search condition in Parameter, and display the Location list retrieval list box by the search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory input
	 * <BR>
	 * <DIR>
	 * 		Consignor Code <BR>
	 * 		End Location <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PsearchEndLocation_Click(ActionEvent e) throws Exception
	{
		//#CM567052
		// Set the search condition on the Location retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM567053
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM567054
		// End Location
		param.setParameter(ListInventoryLocationBusiness.LOCATION_KEY, txt_EndLocation.getText());
		//#CM567055
		// End flag(Location range flag)
		param.setParameter(ListInventoryLocationBusiness.RANGELOCATION_KEY, StorageSupportParameter.RANGE_END);
		//#CM567056
		// Retrieval flag
		param.setParameter(ListInventoryLocationBusiness.SEARCHLOCATION_KEY, StorageSupportParameter.SEARCH_INVENTORY);

		//#CM567057
		// Processing Screen -> Result screen
		redirect("/storage/listbox/listinventorylocation/ListInventoryLocation.do", param, "/progress.do");
	}

	//#CM567058
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567059
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567060
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM567061
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM567062
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM567063
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567064
	/** 
	 * It is called when the Item retrieval button is pressed.<BR>
	 * Outline : Set the search condition in Parameter, and display the Item list retrieval list box by the search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory input
	 * <BR>
	 * <DIR>
	 * 		Consignor Code <BR>
	 * 		Start Location <BR>
	 * 		End Location <BR>
	 * 		Item Code <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PsearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM567065
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM567066
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM567067
		// Start Location
		param.setParameter(ListStorageLocationBusiness.STARTSTORAGELOCATION_KEY, txt_StartLocation.getText());
		//#CM567068
		// End Location
		param.setParameter(ListStorageLocationBusiness.ENDSTORAGELOCATION_KEY, txt_EndLocation.getText());
		//#CM567069
		// Item Code
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM567070
		// Retrieval flag
		param.setParameter(ListStorageItemBusiness.SEARCHITEM_KEY, StorageSupportParameter.SEARCH_INVENTORY);
		//#CM567071
		// Processing Screen -> Result screen
		redirect("/storage/listbox/liststorageitem/ListStorageItem.do", param, "/progress.do");
	}

	//#CM567072
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Status_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567073
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Sts_All_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567074
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Sts_All_Click(ActionEvent e) throws Exception
	{
	}

	//#CM567075
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Sts_StockDiff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567076
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Sts_StockDiff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM567077
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Sts_StockEqualQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567078
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Sts_StockEqualQty_Click(ActionEvent e) throws Exception
	{
	}

	//#CM567079
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567080
	/** 
	 * It is called when the display button is pressed.<BR>
	 * <BR>
	 * Outline : Set input Item of the input area in Parameter, and display the Inventory Work list list box on another screen. <BR>
	 * <BR>
	 * [Parameter] *Mandatory input<BR>
	 * <BR>
	 * <DIR>
	 * 		Consignor Code*<BR>
	 * 		Start Location<BR>
	 * 		End Location<BR>
	 * 		Item Code<BR>
	 * 		Status<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{

		//#CM567081
		// The instance to set the search condition is declared. 
		ForwardParameters forwardParam = new ForwardParameters();
		//#CM567082
		// Set Consignor Code
		forwardParam.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM567083
		// Set Start Location
		forwardParam.setParameter(ListInventoryLocationBusiness.STARTLOCATION_KEY, txt_StartLocation.getText());
		//#CM567084
		// Set End Location
		forwardParam.setParameter(ListInventoryLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getText());
		//#CM567085
		// Set Item Code
		forwardParam.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM567086
		// Set Status
		if (rdo_Sts_All.getChecked())
		{
			forwardParam.setParameter(ListInventoryListBusiness.DISP_STATUS_KEY, StorageSupportParameter.DISP_STATUS_ALL);
		}
		else if (rdo_Sts_StockDiff.getChecked())
		{
			forwardParam.setParameter(ListInventoryListBusiness.DISP_STATUS_KEY, StorageSupportParameter.DISP_STATUS_DIFFERENCE);
		}
		else if (rdo_Sts_StockEqualQty.getChecked())
		{
			forwardParam.setParameter(ListInventoryListBusiness.DISP_STATUS_KEY, StorageSupportParameter.DISP_STATUS_EQUAL);
		}
		//#CM567087
		// Display the Inventory Work list box. 
		redirect("/storage/listbox/listinventorylist/ListInventoryList.do", forwardParam, "/progress.do");
	}

	//#CM567088
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567089
	/** 
	 * It is called when the Clear button is pressed.<BR>
	 * <BR>
	 * Outline:Clear the Input area. <BR>
	 * <BR>
	 * <DIR>
	 *     1.Clear the items of Input area.<BR>
	 *     <DIR>
	 *         Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 *         Start Location[Clear] <BR>
	 *         End Location[Clear] <BR>
	 *         Item Code[Clear] <BR>
	 *         Status[All] <BR>
	 *     </DIR>
	 *     2.Set the cursor in Consignor Code.  <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM567090
		// Set Initial value
		//#CM567091
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM567092
		// Start Location
		txt_StartLocation.setText("");
		//#CM567093
		// End Location
		txt_EndLocation.setText("");
		//#CM567094
		// Item Code
		txt_ItemCode.setText("");
		//#CM567095
		// Set focus in Consignor Code
		setFocus(txt_ConsignorCode);
		//#CM567096
		// Status
		rdo_Sts_All.setChecked(true);
		rdo_Sts_StockDiff.setChecked(false);
		rdo_Sts_StockEqualQty.setChecked(false);
	}

	//#CM567097
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567098
	/** 
	 * It is called when Print button is pressed.<BR>
	 * <BR>
	 * Outline : Display the Confirmation dialog whether to print or not  <BR>
	 * after input Item of input area is set in Parameter and print qty is acquired.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the cursor in Consignor Code. <BR>
	 * 		2.Check the Input item of Input area.<BR>
	 * 		3.Check the print qty objects.<BR>
	 * 		4-1.Display the Confirmation dialog when there is data for print. <BR>
	 * 		4-2.Acquire Message when there is no data for the print and display it. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		//#CM567099
		// Set focus in Consignor Code
		setFocus(txt_ConsignorCode);
		
		//#CM567100
		// Do the Input Check.
		if (!checkInputData())
		{
			return;
		}
		//#CM567101
		// Start Location is smaller than End Location
		if (!StringUtil.isBlank(txt_StartLocation.getText()) && !StringUtil.isBlank(txt_EndLocation.getText()))
		{
			if (txt_StartLocation.getText().compareTo(txt_EndLocation.getText()) > 0)
			{
				//#CM567102
				// 6023124=Starting location No. must precede the end location No.
				message.setMsgResourceKey("6023124");
				return;
			}
		}
		Connection conn = null;
		try
		{
			//#CM567103
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			StorageSupportParameter param = createParameter();
			
			//#CM567104
			// Start scheduling.
			WmsScheduler schedule = new InventoryInquirySCH();

			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM567105
				// MSG-W0061={0} data match. Do you print?
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM567106
				// Memorize the dialog display by Print button. 
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM567107
				// Set Message
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
				//#CM567108
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

	//#CM567109
	/** 
	 * Method for Doing the input check. <BR>
	 * <BR>
	 * Outline : Return true if no mistake is found and false if the mistake are found in the input. <BR>
	 * <BR>
	 * @return Result of Input Check(Normal : true Abnormal : false)
	 * @throws Exception It reports on all exceptions. 
	 */
	private boolean checkInputData() throws Exception
	{
		//#CM567110
		// Input Check
		//#CM567111
		// Consignor Code
		txt_ConsignorCode.validate();
		//#CM567112
		// Start Location
		txt_StartLocation.validate(false);
		//#CM567113
		// End Location
		txt_EndLocation.validate(false);
		//#CM567114
		// Item Code
		txt_ItemCode.validate(false);

		return true;

	}

}
//#CM567115
//end of class
