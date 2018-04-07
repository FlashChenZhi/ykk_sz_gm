// $Id: InventoryCheckListBusiness.java,v 1.2 2006/10/04 05:04:03 suresh Exp $

//#CM4078
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.inventorychecklist;
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
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockinventorycheck.ListStockInventoryCheckBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocation.ListStockLocationBusiness;
import jp.co.daifuku.wms.stockcontrol.schedule.InventoryListSCH;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM4079
/**
 * Designer : A.Nagasawa<BR>
 * Maker : A.Nagasawa<BR>
 * <BR>
 * This is the screen class to execute Inventory Check list form print.<BR>
 * Pass parameter to the schedule to execute Inventory Check list form process.<BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * <DIR>
 * 1.Display button press down process(<CODE>btn_PDisplay_Click</CODE>)<BR>
 * <BR>
 * <DIR>
 * 	Set the contents input from the screen to the parameter, and
 * 	The schedule searches for the data for display based on the parameter and displays the result on the popup screen.<BR>
 * 	<BR>
 * 	[parameter] *Mandatory Input
 * 	<DIR>
 *      Consignor code*<BR>
 *      Start location<BR>
 *      End location<BR>
 *      Item code<BR>
 *      Printing conditions*<BR>
 *  	<DIR>
 *          Items aggregated in the same location<BR>
 *          Display Stock Piece only when there is Qty printing <BR>
 *		</DIR>
 * 	</DIE>
 * </DIR>
 * <BR>
 * 2.Print button press down process(<CODE>btn_Print_Click</CODE>)<BR>
 * <BR>
 * <DIR>
 *  Set the contents input from the screen to the parameter, and
 *  the schedule search and print the data based on the parameter<BR>
 *  Return true when the schedule completed the print successfully<BR>
 *  <BR>
 *  [parameter] *Mandatory Input<BR>
 *  <DIR>
 * 		Consignor code*<BR>
 * 		Start location<BR>
 *  	End location<BR>
 *  	Item code<BR>
 *  	Printing conditions*<BR>
 *  	<DIR>
 *  		Items aggregated in the same location<BR>
 *  		Display Stock Piece only when there is Qty printing <BR>
 *  	</DIR>
 * 	</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/28</TD><TD>A.Nagasawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:04:03 $
 * @author  $Author: suresh $
 */
public class InventoryCheckListBusiness extends InventoryCheckList implements WMSConstants
{
	//#CM4080
	// Class fields --------------------------------------------------
	
	//#CM4081
	// Maintain the control that invokes the dialog: Print button
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";

	//#CM4082
	// Class variables -----------------------------------------------

	//#CM4083
	// Class method --------------------------------------------------

	//#CM4084
	// Constructors --------------------------------------------------

	//#CM4085
	// Public methods ------------------------------------------------
	//#CM4086
	/**
	 * Initialize the screen
	 * <BR>
	 * <DIR>
	 * Subject name[initial value]
	 *  <DIR>
	 *  	Consignor code	[Show prior one page when there is only one corresponding Consignor]<BR>
	 *		Start location		[None]<BR>
	 *		End location		[None]<BR>
	 *		Item code	[None]<BR>
	 *		Printing conditions	[Items aggregated in the same location,With Qty printing]<BR>
	 *	</DIR>
	 *	<BR>
	 *  Initialize the focus for the Consignor code.<BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		
		//#CM4087
		// Set the initial value.
		//#CM4088
		// Consignor code
		txt_ConsignorCode.setText(getConsognorCode());
		//#CM4089
		// Start location
		txt_StartLocation.setText("");
		//#CM4090
		// End location
		txt_EndLocation.setText("");
		//#CM4091
		// Item code
		txt_ItemCode.setText("");
		//#CM4092
		// Printing conditions
		rdo_GrpItemCdStrgDate.setChecked(true);
		rdo_GrpGrpOff.setChecked(false);
		rdo_QtyPrintOn.setChecked(true);
		rdo_QtyPrintOff.setChecked(false);

		//#CM4093
		// Set the focus for the Consignor code.
		setFocus(txt_ConsignorCode);
	}

	//#CM4094
	/**
	* Invoke this before invoking each control event. <BR>
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
		if (menuparam != null)
		{
			//#CM4095
			// Obtain parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM4096
			// Store to ViewState
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM4097
			// Set the screen name
			lbl_SettingName.setResourceKey(title);
		}

	}
	
	//#CM4098
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
			//#CM4099
			// Set the focus for the Consignor code.
			setFocus(txt_ConsignorCode);

			//#CM4100
			// Check which dialog returns it.
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM4101
			// Clicking "Yes" in the dialog box returns true.
			//#CM4102
			// Clicking "No" in the dialog box returns false.
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM4103
			// Clicking "NO " button closes the process.
			//#CM4104
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
			//#CM4105
			// Turn the flag off after closed the operation of the dialog.
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM4106
		// Start print schedule
		Connection conn = null;
		try
		{
			//#CM4107
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM4108
			// Set the input value for the parameter.
			StockControlParameter[] param = new StockControlParameter[1];
			param[0] = createParameter();

			//#CM4109
			// Start schedule
			WmsScheduler schedule = new InventoryListSCH();
			schedule.startSCH(conn, param);
			
			//#CM4110
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
				//#CM4111
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

	//#CM4112
	/**
	 * Invoke this method when returning from the popup window.<BR>
	 * Override page_DlgBack defined on the page.
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM4113
		// Obtain the parameter selected in the listbox.
		//#CM4114
		// Consignor code
		String consignorcode = param.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM4115
		// Start location
		String starlocation = param.getParameter(ListStockLocationBusiness.STARTLOCATION_KEY);
		//#CM4116
		// End location
		String endlocation = param.getParameter(ListStockLocationBusiness.ENDLOCATION_KEY);
		//#CM4117
		// Item code
		String itemcode = param.getParameter(ListStockItemBusiness.ITEMCODE_KEY);

		//#CM4118
		// Set the value if not empty.
		//#CM4119
		// Consignor code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM4120
		// Start location
		if (!StringUtil.isBlank(starlocation))
		{
			txt_StartLocation.setText(starlocation);
		}
		//#CM4121
		// End location
		if (!StringUtil.isBlank(endlocation))
		{
			txt_EndLocation.setText(endlocation);
		}
		//#CM4122
		// Item code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM4123
		// Set the focus for the Consignor code.
		setFocus(txt_ConsignorCode);
	}

	//#CM4124
	// Package methods -----------------------------------------------

	//#CM4125
	// Protected methods ---------------------------------------------

	//#CM4126
	/**
	 * Generate parameter object that set input value of the input area<BR>
	 * 
	 * @return Parameter object including input value of Input area.
	 */	
	protected StockControlParameter createParameter()
	{
		StockControlParameter param = new StockControlParameter();
		
		param.setConsignorCode(txt_ConsignorCode.getText());
		param.setFromLocationNo(txt_StartLocation.getText());
		param.setToLocationNo(txt_EndLocation.getText());
		param.setItemCode(txt_ItemCode.getText());
		if (rdo_GrpItemCdStrgDate.getChecked())
		{
			param.setPrintCondition1(
				StockControlParameter.PRINTINGCONDITION_INTENSIVEPRINTING_ON);
		}
		else if (rdo_GrpGrpOff.getChecked())
		{
			param.setPrintCondition1(
				StockControlParameter.PRINTINGCONDITION_INTENSIVEPRINTING_OFF);
		}
		
		if (rdo_QtyPrintOn.getChecked())
		{
			param.setPrintCondition2(StockControlParameter.PRINTINGCONDITION_QTYPRINTING_ON);
		}
		else if (rdo_QtyPrintOff.getChecked())
		{
			param.setPrintCondition2(
				StockControlParameter.PRINTINGCONDITION_QTYPRINTING_OFF);
		}
		return param;
	}

	//#CM4127
	// Private methods -----------------------------------------------
	//#CM4128
	/**
	 * This method obtains the Consignor code from the schedule. <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule. <BR>
	 * 
	 * @return Consignor code <BR>
	 *         Return the string of the Consignor code when one corresponding data exists. <BR>
	 *         Return null if 0 or multiple corresponding data exist. <BR>
	 * 
	 * @throws Exception
	 *             Reports all the exceptions.
	 */
	private String getConsognorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM4129
			// Obtain the connection	
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StockControlParameter param = new StockControlParameter();

			//#CM4130
			// Obtain the Consignor code from the schedule.
			WmsScheduler schedule = new InventoryListSCH();
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
			try
			{
				//#CM4131
				// Close the connection
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
		return null;
	}

	//#CM4132
	// Event handler methods -----------------------------------------
	//#CM4133
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4134
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4135
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4136
	/** 
	 * Clicking on the Clicking the Menu button invokes this.<BR>
	 * <BR>
	 * Summary: Move to menu screen. 
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM4137
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4138
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4139
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM4140
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM4141
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM4142
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4143
	/** 
	 * Clicking Consignor search button invokes this.<BR>
	 * <BR>
	 * Summary: This Method set search conditions to the parameter, and, 
	 * Display the Consignor search listbox using the search conditions.<BR>
	 * <BR>
	 * [parameter]*Mandatory Input
	 *  <DIR>
	 *  Consignor code<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		//#CM4144
		// Set the search condition for the Search Consignor screen.
		ForwardParameters param = new ForwardParameters();
		//#CM4145
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM4146
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM4147
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststockconsignor/ListStockConsignor.do",
			param,
			"/progress.do");
	}

	//#CM4148
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4149
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4150
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM4151
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM4152
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM4153
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrtLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4154
	/** 
	 * Clicking on the Search Start Location button invokes this.<BR>
	 * <BR>
	 * Summary: This Method set search conditions to the parameter, and, 
	 * Display the location search listbox using the search conditions.<BR>
	 * <BR>
	 * [parameter]*Mandatory Input
	 *  <DIR>
	 * 	Consignor code<BR>
	 *  Start location<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchStrtLct_Click(ActionEvent e) throws Exception
	{
		//#CM4155
		//Set the search condition for the Start search Location screen.
		ForwardParameters param = new ForwardParameters();
		//#CM4156
		//Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM4157
		// Start location
		param.setParameter(
			ListStockLocationBusiness.STARTLOCATION_KEY,
			txt_StartLocation.getText());
		//#CM4158
		// Set start flag
		param.setParameter(
			ListStockLocationBusiness.RANGELOCATION_KEY,
			StockControlParameter.RANGE_START);

		//#CM4159
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM4160
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststocklocation/ListStockLocation.do",
			param,
			"/progress.do");
	}

	//#CM4161
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4162
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4163
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4164
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM4165
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM4166
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM4167
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEndLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4168
	/** 
	 * Clicking on the Search End Location button invokes this.<BR>
	 * <BR>
	 * Summary: This Method set search conditions to the parameter, and, 
	 * Display the location search listbox using the search conditions.<BR>
	 * <BR>
	 * [parameter]*Mandatory Input
	 *  <DIR>
	 * 	Consignor code<BR>
	 *  End location<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchEndLct_Click(ActionEvent e) throws Exception
	{
		//#CM4169
		// Set the search condition for the Search End Location screen.
		ForwardParameters param = new ForwardParameters();
		//#CM4170
		//Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM4171
		// End location
		param.setParameter(ListStockLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getText());
		//#CM4172
		// Set the Close flag.
		param.setParameter(
			ListStockLocationBusiness.RANGELOCATION_KEY,
			StockControlParameter.RANGE_END);

		//#CM4173
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM4174
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststocklocation/ListStockLocation.do",
			param,
			"/progress.do");
	}

	//#CM4175
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4176
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4177
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM4178
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM4179
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM4180
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4181
	/** 
	 * Clicking on the Search Item button invokes this.<BR>
	 * <BR>
	 * Summary: This Method set search conditions to the parameter, and, 
	 * Display the item search listbox using the search conditions.<BR>
	 * <BR>
	 * [parameter]*Mandatory Input
	 *  <DIR>
	 * 	Consignor code<BR>
	 * 	Start location<BR>
	 *  End location<BR>
	 * 	Item code<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM4182
		// Set the search conditions for the Search Item screen.
		ForwardParameters param = new ForwardParameters();
		//#CM4183
		//Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM4184
		// Start location
		param.setParameter(
			ListStockLocationBusiness.STARTLOCATION_KEY,
			txt_StartLocation.getText());
		//#CM4185
		// End location
		param.setParameter(ListStockLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getText());
		//#CM4186
		// Item code
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM4187
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM4188
		// In process screen->Result screen 
		redirect("/stockcontrol/listbox/liststockitem/ListStockItem.do", param, "/progress.do");
	}

	//#CM4189
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PrintCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4190
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_GrpItemCdStrgDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4191
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_GrpItemCdStrgDate_Click(ActionEvent e) throws Exception
	{
	}

	//#CM4192
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_GrpGrpOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4193
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_GrpGrpOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM4194
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_QtyPrintOn_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4195
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_QtyPrintOn_Click(ActionEvent e) throws Exception
	{
	}

	//#CM4196
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_QtyPrintOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4197
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_QtyPrintOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM4198
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4199
	/** 
	 * Clicking on the Display button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the input item (count) in the input area for the parameter and displays the inventory check list form print list listbox in another screen.<BR>
	 * <BR>
	 * [parameter] *Mandatory Input<BR>
	 * <DIR>
	 *  Consignor code*<BR>
	 *	Start location<BR>
	 *	End location<BR>
	 *	Item code<BR>
	 *	Printing conditions*<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		//#CM4200
		// Declare the instance to set the search conditions.
		ForwardParameters forwardParam = new ForwardParameters();
		//#CM4201
		//Consignor code
		forwardParam.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM4202
		// Start location
		forwardParam.setParameter(
			ListStockLocationBusiness.STARTLOCATION_KEY,
			txt_StartLocation.getText());
		//#CM4203
		// End location
		forwardParam.setParameter(
			ListStockLocationBusiness.ENDLOCATION_KEY,
			txt_EndLocation.getText());
		//#CM4204
		// Item code
		forwardParam.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM4205
		// Printing conditions(Items aggregated in the same location)
		if (rdo_GrpItemCdStrgDate.getChecked())
		{
			forwardParam.setParameter(
				ListStockInventoryCheckBusiness.PRINTINGCONDITION_INTENSIVEPRINTING,
				StockControlParameter.PRINTINGCONDITION_INTENSIVEPRINTING_ON);
		}
		//#CM4206
		// Printing conditions(Aggregation None)
		else if (rdo_GrpGrpOff.getChecked())
		{
			forwardParam.setParameter(
				ListStockInventoryCheckBusiness.PRINTINGCONDITION_INTENSIVEPRINTING,
				StockControlParameter.PRINTINGCONDITION_INTENSIVEPRINTING_OFF);
		}
		//#CM4207
		// Printing conditions(Qty printing)
		if (rdo_QtyPrintOn.getChecked())
		{
			forwardParam.setParameter(
				ListStockInventoryCheckBusiness.PRINT_CHECK,
				StockControlParameter.PRINTINGCONDITION_QTYPRINTING_ON);
		}
		else if (rdo_QtyPrintOff.getChecked())
		{
			forwardParam.setParameter(
				ListStockInventoryCheckBusiness.PRINT_CHECK,
				StockControlParameter.PRINTINGCONDITION_QTYPRINTING_OFF);
		}

		//#CM4208
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststockinventorycheck/ListStockInventoryCheck.do",
			forwardParam,
			"/progress.do");
	}

	//#CM4209
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4210
	/** 
	 * Clicking on the Print button invokes this.<BR>
	 * <BR>
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
		//#CM4211
		// Set the focus for the Consignor code.
		setFocus(txt_ConsignorCode);
		//#CM4212
		// Input check
		txt_ConsignorCode.validate();
		txt_StartLocation.validate(false);
		txt_EndLocation.validate(false);
		txt_ItemCode.validate(false);

		//#CM4213
		// Start Location is smaller than End Location
		if (!StringUtil.isBlank(txt_StartLocation.getText())
			&& !StringUtil.isBlank(txt_EndLocation.getText()))
		{
			if (txt_StartLocation.getText().compareTo(txt_EndLocation.getText()) > 0)
			{
				//#CM4214
				// 6023124=Starting location No. must precede the end location No.
				message.setMsgResourceKey("6023124");
				return;
			}
		}
		Connection conn = null;
		try
		{
			//#CM4215
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM4216
			// Set the schedule parameter
			StockControlParameter param = createParameter();
			//#CM4217
			// Start schedule
			WmsScheduler schedule = new InventoryListSCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM4218
				// Hit MSG-W0061={0} count(s)<BR>Do you print it out?
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM4219
				// Store the fact that the dialog was displayed via the Print button.
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM4220
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
				//#CM4221
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

	//#CM4222
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4223
	/** 
	 * Clicking on the Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area.<BR>
	 * <BR>
	 *  <DIR>
	 *  Refer to <CODE>page_Load(ActionEvent e)</CODE>Method in regard with the initial value.
	 *  </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM4224
		// Set the initial value.
		//#CM4225
		// Consignor code
		txt_ConsignorCode.setText(getConsognorCode());
		//#CM4226
		// Start location
		txt_StartLocation.setText("");
		//#CM4227
		// End location
		txt_EndLocation.setText("");
		//#CM4228
		// Item code
		txt_ItemCode.setText("");
		//#CM4229
		// Printing conditions
		rdo_GrpItemCdStrgDate.setChecked(true);
		rdo_GrpGrpOff.setChecked(false);
		rdo_QtyPrintOn.setChecked(true);
		rdo_QtyPrintOff.setChecked(false);

		//#CM4230
		// Set the focus for the Consignor code.
		setFocus(txt_ConsignorCode);
	}
}
//#CM4231
//end of class
