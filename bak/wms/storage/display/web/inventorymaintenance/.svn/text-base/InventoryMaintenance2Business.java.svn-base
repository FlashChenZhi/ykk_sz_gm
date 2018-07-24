// $Id: InventoryMaintenance2Business.java,v 1.2 2006/12/07 08:57:46 suresh Exp $

//#CM567124
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.inventorymaintenance;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckReportFinder;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.schedule.InventoryMaintenanceSCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM567125
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * The Inventory(Information setting details) class. <BR>
 * Display input information on the essential information input screen in the upper display area. <BR>
 * Hand over Parameter to the schedule which does Inventory. <BR>
 * Moreover, do Commit  of Transaction and Rollback on this screen. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Input button is pressed(<CODE>btn_Input_Click</CODE> Method)<BR>
 * <BR>
 * <DIR>
 * 		Set the content input from the input area in Parameter, and the schedule checks the input condition based on the Parameter. <BR>
 * 		Receive True when the result is received from the schedule, and processing Completes normally, <BR>
 * 		and false when the schedule does not do Completed because of the condition error etc.<BR>
 * 		The result of the schedule outputs the message acquired from the schedule to the screen. <BR>
 * 		Add information on the input area to Preset area when the result is true. <BR>
 * 		Update the data of Preset for the modification in information on the input area when you press the input button after the modify button is pressed. <BR>
 * 		<BR>
 * 		[Parameter] *Mandatory input<BR>
 * 		<BR>
 * 		<DIR>
 * 			Item Code* <BR>
 * 			Item Name <BR>
 * 			Packed qty per case*2 <BR>
 * 			Packed qty per bundle <BR>
 * 			Inventory Case qty*3 <BR>
 * 			Inventory Piece qty*3 <BR>
 * 			Location* <BR>
 * 			Expiry Date <BR>
 * 			<BR>
 * 			*2 <BR>
 * 			Inventory Case qty(>0) when entered, Mandatory input <BR>
 * 			*3 <BR>
 * 			The input of one or more is Mandatory condition in case of either Inventory Case qty or Inventory Piece qty.  <BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * 2.Processing when Inventory data update button is pressed(<CODE>btn_InventoryData_Click</CODE> Method)<BR>
 * <BR>
 * <DIR>
 * 		The content of Preset area is set in Parameter, and the schedule does Inventory based on the Parameter. <BR>
 * 		Receive True when the result is received from the schedule, and processing Completes normally, <BR>
 * 		and false when the schedule does not do Completed because of the condition error etc.<BR>
 * 		The result of the schedule outputs the message acquired from the schedule to the screen. <BR>
 * 		<BR>
 * 		[Parameter]<BR>
 * 		<BR>
 * 		<DIR>
 * 			Consignor Code<BR>
 * 			Consignor Name<BR>
 * 			Start Location<BR>
 * 			End Location<BR>
 * 			Item Code<BR>
 * 			Item Name<BR>
 * 			Packed qty per case<BR>
 * 			Packed qty per bundle<BR>
 * 			Inventory Case qty<BR>
 * 			Inventory Piece qty<BR>
 * 			Stock Case qty<BR>
 * 			Stock Piece qty<BR>
 * 			Expiry Date<BR>
 * 			Inventory Work present<BR>
 * 			Last updated date and time<BR>
 * 			Work No.<BR>
 * 			Stock ID<BR>
 * 			Preset Line No..<BR>
 * 			Worker Code<BR>
 * 			Password<BR>
 * 			Terminal No.<BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:46 $
 * @author  $Author: suresh $
 */
public class InventoryMaintenance2Business extends InventoryMaintenance2 implements WMSConstants
{
	//#CM567126
	// Class fields --------------------------------------------------
	//#CM567127
	/**
	 * The key used to hand over line No. for ViewState.
	 */
	public static final String LINENO_KEY = "LINENO_KEY";

	//#CM567128
	/**
	 * The key used to hand over Location for ViewState.
	 */
	public static final String LOCATION_KEY = "LOCATION_KEY";

	//#CM567129
	/**
	 * The key used to hand over Item Code for ViewState.
	 */
	public static final String ITEMCODE_KEY = "ITEMCODE_KEY";

	//#CM567130
	/**
	 * The key used to hand over Item Name for ViewState.
	 */
	public static final String ITEMNAME_KEY = "ITEMNAME_KEY";

	//#CM567131
	/**
	 * The key used to hand over Packed qty per case for ViewState.
	 */
	public static final String ENTERINGQTY_KEY = "ENTERINGQTY_KEY";

	//#CM567132
	/**
	 * The key used to hand over Packed qty per bundle for ViewState.
	 */
	public static final String BUNDLEENTERINGQTY_KEY = "BUNDLEENTERINGQTY_KEY";
	
	//#CM567133
	/**
	 * The key used to hand over Inventory Case qty for ViewState.
	 */
	public static final String INVENTORYCHECKCASEQTY_KEY = "INVENTORYCHECKCASEQTY_KEY";

	//#CM567134
	/**
	 * The key used to hand over Inventory Piece qty for ViewState.
	 */
	public static final String INVENTORYCHECKPIECEQTY_KEY = "INVENTORYCHECKPIECEQTY_KEY";

	//#CM567135
	/**
	 * The key used to hand over Expiry date for ViewState.
	 */
	public static final String USEBYDATE_KEY = "USEBYDATE_KEY";

	//#CM567136
	/**
	 * The key used to hand over Stock Case qty for ViewState.
	 */
	public static final String STOCKCASEQTY_KEY = "STOCKCASEQTY_KEY";
	
	//#CM567137
	/**
	 * The key used to hand over Stock Piece qty for ViewState.
	 */
	public static final String STOCKPIECEQTY_KEY = "STOCKPIECEQTY_KEY";
	
	//#CM567138
	/**
	 * The key used to hand over Last updated date and time for ViewState.
	 */
	public static final String LASTUPDATE_KEY = "LASTUPDATE_KEY";
	
	//#CM567139
	/**
	 * The key used to hand over Work No. for ViewState.
	 */
	public static final String JOBNO_KEY = "JOBNO_KEY";

	//#CM567140
	/**
	 * The key used to hand over Stock ID for ViewState.
	 */
	public static final String STOCKID_KEY = "STOCKID_KEY";	

	//#CM567141
	/**
	 * List cell row number of Concealed Item
	 */
	private static final int HIDDEN = 0;
	
	//#CM567142
	/**
	 * List cell row number of present Inventory Work name
	 */
	private static final int INVENTORYKINDNAME = 3;
	
	//#CM567143
	/**
	 * List cell row number of present Location
	 */
	private static final int LOCATION = 4;
	
	//#CM567144
	/**
	 * List cell row number of present Item code
	 */
	private static final int ITEMCODE = 5;
	
	//#CM567145
	/**
	 * List cell row number of present Packed qty per case
	 */
	private static final int ENTERINGQTY = 6;
	
	//#CM567146
	/**
	 * List cell row number of present Inventory Case qty
	 */
	private static final int INVENTORYCHECKCASEQTY = 7;
	
	//#CM567147
	/**
	 * List cell row number of present Stock Case qty
	 */
	private static final int STOCKCASEQTY = 8;
	
	//#CM567148
	/**
	 * List cell row number of present Expiry Date
	 */
	private static final int USEBYDATE = 9;
	
	//#CM567149
	/**
	 * List cell row number of present Item Name
	 */
	private static final int ITEMNAME = 10;
	
	//#CM567150
	/**
	 * List cell row number of present Packed qty per bundle
	 */
	private static final int BUNDLEENTERINGQTY = 11;
	
	//#CM567151
	/**
	 * List cell row number of present Inventory Piece qty
	 */
	private static final int INVENTORYCHECKPIECEQTY = 12;
	
	//#CM567152
	/**
	 * List cell row number of present Stock Piece qty
	 */
	private static final int STOCKPIECEQTY = 13;



	//#CM567153
	// Class variables -----------------------------------------------

	//#CM567154
	// Class method --------------------------------------------------

	//#CM567155
	// Constructors --------------------------------------------------

	//#CM567156
	// Public methods ------------------------------------------------

	//#CM567157
	/**
	 * It is called when initial data of the screen is displayed.<BR>
	 * <BR>
	 * Outline:Display initial data in the screen. <BR>
	 * <BR>
	 * <DIR>
	 * 	 1.Start Schedule.
	 *   <DIR>
	 *  	[Parameter] *Mandatory input<BR>
	 *   	<DIR>
	 * 			Consignor Code* <BR>
	 * 			Start Location <BR>
	 * 			End Location <BR>
	 *		</DIR>
	 * 	 </DIR>
	 *	 2.Display the input area and Preset area based on Preset area Output data acquired from the schedule when processing normally Completes. <BR>
	 *   <DIR>
	 *   	Input area Item name [Initial value]<BR>
	 *   	<DIR>
	 * 			Consignor Code[Essential information.Consignor Code] <BR>
	 * 			Consignor Name[Return Data[0].Consignor Name] <BR>
	 * 			Start Location[Essential information.Start Location] <BR>
	 * 			End Location[Essential information.End Location] <BR>
	 * 			Item Code[None] <BR>
	 * 			Item Name[None] <BR>
	 * 			Packed qty per case[None] <BR>
	 * 			Packed qty per bundle[None] <BR>
	 * 			Inventory Case qty[None] <BR>
	 * 			Inventory Piece qty[None] <BR>
	 * 			Location[None] <BR>
	 * 			Expiry Date[None] <BR>
	 * 	 	</DIR>
	 *   	Preset areaItem Name[Initial value]<BR>
	 *   	<DIR>
	 * 			Present Inventory Work Name[Add/Not] <BR>
	 * 			Location[Return Data.Location] <BR>
	 * 			Item Code[Return Data.Item Code] <BR>
	 * 			Item Name[Return Data.Item Name] <BR>
	 * 			Packed qty per case[Return Data.Packed qty per case] <BR>
	 * 			Packed qty per bundle[Return Data.Packed qty per bundle] <BR>
	 * 			Inventory Case qty[Return Data.Inventory Case qty] <BR>
	 * 			Inventory Piece qty[Return Data.Inventory Piece qty] <BR>
	 * 			Stock Case qty[Return Data.Stock Case qty] <BR>
	 * 			Stock Piece qty[Return Data.Stock Piece qty] <BR>
	 * 			Expiry Date[Return Data.Expiry Date] <BR>
	 * 	 	</DIR>
	 *   </DIR>
	 *	 Null is received when the condition error etc. occur, and it returns to the previous screen in empty array Parameters when pertinent data is not found. <BR>
	 *   3.The result of the schedule outputs the message acquired from the schedule to the screen. <BR>
	 *   4.Initial value of Preset Line No. of ViewState : Set -1.<BR>
	 *   5.Set the cursor in Item Code initially.<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM567158
		// Title
		lbl_SettingName.setResourceKey(this.getViewState().getString(InventoryMaintenanceBusiness.TITLE_KEY));		

		Connection conn = null;

		try
		{
			//#CM567159
			// The tab of the detailed information setting is put out before.
			tab_Inventory.setSelectedIndex(2);

			//#CM567160
			// Receive Parameter to which the Essential information setting is input. 
			String consignorcode =
				this.getViewState().getString(InventoryMaintenanceBusiness.CONSIGNORCODE_KEY);
			String startlocation =
				this.getViewState().getString(InventoryMaintenanceBusiness.STARTLOCATION_KEY);
			String endlocation =
				this.getViewState().getString(InventoryMaintenanceBusiness.ENDLOCATION_KEY);
				
			//#CM567161
			// Set in schedule Parameter.
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM567162
			// Consignor Code
			param.setConsignorCode(consignorcode);
			//#CM567163
			// Start Location
			param.setFromLocation(startlocation);
			//#CM567164
			// End Location
			param.setToLocation(endlocation);

			//#CM567165
			// Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new InventoryMaintenanceSCH();

			StorageSupportParameter[] viewParam =
				(StorageSupportParameter[]) schedule.query(conn, param);

			//#CM567166
			// Display the input area and Preset area when processing normally Completes.
			//#CM567167
			// Input area
			//#CM567168
			// Consignor Code
			lbl_JavaSetCnsgnrCd.setText(consignorcode);
			//#CM567169
			// Start Location
			lbl_JavaSetStartLocation.setText(startlocation);
			//#CM567170
			// End Location
			lbl_JavaSetEndLocation.setText(endlocation);
			
			//#CM567171
			// The inventory information of Consignor Name -> Acquire it in order of priority of Inventory information. 
			//#CM567172
			// Stock information retrieval
			String stockConsignorName = dispStockConsignorName(conn, param);
			if (!StringUtil.isBlank(stockConsignorName))
			{
				//#CM567173
				// Display latest Consignor Name. 
				lbl_JavaSetCnsgnrNm.setText(stockConsignorName);
			}
			else
			{
				//#CM567174
				// Inventory information retrieval
				String inventoryConsignorName = dispInventoryConsignorName(conn, param);
				if (!StringUtil.isBlank(inventoryConsignorName))
				{
					//#CM567175
					// Display latest Consignor Name. 
					lbl_JavaSetCnsgnrNm.setText(inventoryConsignorName);
				}
				else
				{
					//#CM567176
					// Display latest Consignor Name. 
					lbl_JavaSetCnsgnrNm.setText("");
				}
			}

			//#CM567177
			// Preset area
			setList(viewParam);

			//#CM567178
			// Invalidate the Inventory data update button when Preset information does not exist. 
			if (lst_InventoryMaintenance.getMaxRows() == 1)
			{
				//#CM567179
				// Invalidate the button pressing. 
				//#CM567180
				// Inventory data update button
				btn_InventoryData.setEnabled(false);
			}

			//#CM567181
			// Set Message
			message.setMsgResourceKey(schedule.getMessage());

			//#CM567182
			// To decide whether Status is Modify when the Modify button is pressed, Preset Line No is set in ViewState.
			//#CM567183
			// (Default : -1)
			this.getViewState().setInt(LINENO_KEY, -1);

			//#CM567184
			// Set the cursor in Item Code. 
			setFocus(txt_ItemCode);
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM567185
				// Close the connection
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

	//#CM567186
	/**
	 * It is called before the call of each control event. <BR>
	 * <BR>
	 * Outline:Display the Confirmation dialog.<BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM567187
			//Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM567188
			//Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM567189
			//Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
		//#CM567190
		// Confirmation Dialog "Do you want to update it?" when Inventory data update button is pressed.
		btn_InventoryData.setBeforeConfirm("MSG-W0028");
	}

	//#CM567191
	/**
	 * When returning from the pop up window, this Method is called. <BR>
	 * Override the <CODE>page_DlgBack</CODE> set in <CODE>Page</CODE>.<BR>
	 * <BR>
	 * Outline:Acquire and set Return Data of the retrieval screen. <BR>
	 * <BR>
	 * <DIR>
	 *      1.Acquire the value returned from the retrieval screen of pop up. <BR>
	 *      2.Set it on the screen when the value is not empty. <BR>
	 *      3.Set the cursor in Item Code. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM567192
		// Parameter selected from list box is acquired. 
		//#CM567193
		// Item Code
		String itemcode = param.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM567194
		// Item Name
		String itemname = param.getParameter(ListStorageItemBusiness.ITEMNAME_KEY);

		//#CM567195
		// Set the value when it is not empty. 
		//#CM567196
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM567197
		// Item Name
		if (!StringUtil.isBlank(itemname))
		{
			txt_ItemName.setText(itemname);
		}

		//#CM567198
		// Set the cursor in Item Code. 
		setFocus(txt_ItemCode);
	}

	//#CM567199
	// Package methods -----------------------------------------------

	//#CM567200
	// Protected methods ---------------------------------------------
	//#CM567201
	/**
	 * Do the Input Check.<BR>
	 * <BR>
	 * Outline : Set Message and Return false when abnormality is found. <BR>
	 * <BR>
	 * @return Result of Input Check (true:Normal false:Abnormal) 
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
		if (!checker.checkContainNgText(txt_ItemCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ItemName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_Location))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_UseByDate))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		return true;
		
	}

	//#CM567202
	/**
	 * Clear the Input area.<BR>
	 * <BR>
	 * Outline : The cursor is set in Item Code, and Clears each Item of Input area. <BR>
	 * <BR>
	 */
	public void clearInputArea()
	{
		//#CM567203
		// Make the Item useable.
		//#CM567204
		// To Modify existing data when you cannot input Item Code
		//#CM567205
		// Clear only the InventoryInput area.
		setFocus(txt_ItemCode);
		txt_InventoryCaseQty.setText("");
		txt_InventoryPieceQty.setText("");
		txt_ItemCode.setText("");
		txt_ItemName.setText("");
		txt_CaseEntering.setText("");
		txt_BundleEntering.setText("");
		txt_InventoryCaseQty.setText("");
		txt_InventoryPieceQty.setText("");
		txt_Location.setText("");
		txt_UseByDate.setText("");
	}

	//#CM567206
	// Private methods -----------------------------------------------

	//#CM567207
	/**
	 * Method to acquire latest Consignor Name from the stock. <BR>
	 * <BR>
	 * Outline : Retrieve the inventory information on the acquisition condition, and return latest Consignor Name. <BR>
	 * Return the empty string if Consignor does not exist.<BR>
	 * <BR>
	 * @param conn Connection Instance to maintain connection with data base
	 * @param param StorageSupportParameter Parameter which keeps acquisition condition
	 * @return Consignor Name
	 * @throws Exception It reports on all exceptions. 
	 */
	private String dispStockConsignorName(Connection conn,StorageSupportParameter param) throws Exception
	{
		String consignorName = "";
		
		StockReportFinder stockFinder = new StockReportFinder(conn);
		StockSearchKey nameKey = new StockSearchKey();
		
		//#CM567208
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			nameKey.setConsignorCode(param.getConsignorCode());
		}

		//#CM567209
		// Start Location No.
		if (!StringUtil.isBlank(param.getFromLocation()))
		{
			nameKey.setLocationNo(param.getFromLocation(), ">=");
		}

		//#CM567210
		// End Location No.
		if (!StringUtil.isBlank(param.getToLocation()))
		{
			nameKey.setLocationNo(param.getToLocation(), "<=");
		}

		//#CM567211
		// Stock status : Central stock
		nameKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM567212
		// Stock qty is one or more. 
		nameKey.setStockQty(0, ">");
		
		nameKey.setConsignorNameCollect("");
		nameKey.setLastUpdateDateOrder(1, false);
		
		if (stockFinder.search(nameKey) > 0)
		{
			Stock[] stock = (Stock[])stockFinder.getEntities(1);
			consignorName = stock[0].getConsignorName();
		}
		stockFinder.close();
		
		
		return consignorName;
	}

	//#CM567213
	/**
	 * Method to acquire latest Consignor Name from Inventory Work. <BR>
	 * <BR>
	 * Outline : Retrieve Inventory Work information on the acquisition condition, and return latest Consignor Name. <BR>
	 * Return the empty string if Consignor does not exist.<BR>
	 * <BR>
	 * @param conn Connection Instance to maintain connection with data base
	 * @param param StorageSupportParameter Parameter which keeps acquisition condition
	 * @return Consignor Name
	 * @throws Exception It reports on all exceptions. 
	 */
	private String dispInventoryConsignorName(Connection conn,StorageSupportParameter param) throws Exception
	{
		String consignorName = "";
		
		InventoryCheckReportFinder invcheckFinder = new InventoryCheckReportFinder(conn);
		InventoryCheckSearchKey nameKey = new InventoryCheckSearchKey();
		
		//#CM567214
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			nameKey.setConsignorCode(param.getConsignorCode());
		}

		//#CM567215
		// Start Location No.
		if (!StringUtil.isBlank(param.getFromLocation()))
		{
			nameKey.setLocationNo(param.getFromLocation(), ">=");
		}

		//#CM567216
		// End Location No.
		if (!StringUtil.isBlank(param.getToLocation()))
		{
			nameKey.setLocationNo(param.getToLocation(), "<=");
		}

		nameKey.setConsignorNameCollect("");
		nameKey.setRegistDateOrder(1, false);
		
		if (invcheckFinder.search(nameKey) > 0)
		{
			InventoryCheck[] inventory = (InventoryCheck[])invcheckFinder.getEntities(1);
			consignorName = inventory[0].getConsignorName();
		}
		invcheckFinder.close();
		
		return consignorName;
	}			
	
	//#CM567217
	/**
	 * Method which sets the value of All of the Parameter array in Preset area. <BR>
	 * <BR>
	 * Outline : Set the value of the Parameter array in Preset area. <BR>
	 * Use it when you display the retrieval result to the list cell again. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Edit data for the balloon display. <BR>
	 * 		2.Set Concealed Item. <BR>
	 * 		3.Set data in each Item of the list cell. <BR>
	 * 		4.Clear ViewState information.<BR>
	 * </DIR>
	 * <DIR>
	 * 		[Concealed Item]
	 * 		<DIR>
	 * 			0.Inventory Work present <BR>
	 * 			1.Modify flag <BR>
	 * 			2.Location <BR>
	 * 			3.Item Code <BR>
	 * 			4.Item Name <BR>
	 * 			5.Packed qty per case <BR>
	 * 			6.Packed qty per bundle <BR>
	 * 			7.Inventory Case qty <BR>
	 * 			8.Inventory Piece qty <BR>
	 * 			9.Expiry Date <BR>
	 * 			10.Last updated date and time <BR>
	 * 			11.Work No. <BR>
	 * 			12.Stock ID <BR>
	 *	 	</DIR>
	 * </DIR>
	 * <DIR>
	 * 		[List cell row number list]
	 * 		<DIR>
	 * 			0.Concealed Item <BR>
	 * 			3.Present Inventory Work Name <BR>
	 * 			4.Location <BR>
	 * 			5.Item Code <BR>
	 * 			6.Packed qty per case <BR>
	 * 			7.Inventory Case qty <BR>
	 * 			8.Stock Case qty <BR>
	 * 			9.Expiry Date <BR>
	 * 			10.Item Name <BR>
	 * 			11.Packed qty per bundle <BR>
	 * 			12.Inventory Piece qty <BR>
	 * 			13.Stock Piece qty <BR>
	 *	 	</DIR>
	 * </DIR>
	 * <BR>
	 * @param param StorageSupportParameter[] Information displayed in Preset area. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setList(StorageSupportParameter[] param) throws Exception
	{
		for (int i = 0; i < param.length; i++)
		{
			//#CM567218
			// Add row
			lst_InventoryMaintenance.addRow();
			//#CM567219
			// A present line is set. 
			lst_InventoryMaintenance.setCurrentRow(i + 1);
			//#CM567220
			// Data for ToolTip is edited. 
			ToolTipHelper toolTip = new ToolTipHelper();
			//#CM567221
			// Item Name
			toolTip.add(DisplayText.getText("LBL-W0103"), param[i].getItemName());
			//#CM567222
			// Packed qty per case (Packed qty per case of Inventory data) 
			toolTip.add(DisplayText.getText("LBL-W0007"), param[i].getEnteringQty());
			//#CM567223
			// Packed qty per bundle (Packed qty per bundle of Inventory data) 
			toolTip.add(DisplayText.getText("LBL-W0005"), param[i].getBundleEnteringQty());
			//#CM567224
			// Stock Case qty, Stock Piece qty
			if (param[i].getTotalStockQty() == 0)
			{
				toolTip.add(DisplayText.getText("LBL-W0230"), "0");
				toolTip.add(DisplayText.getText("LBL-W0233"), "0");
			}
			else
			{
				toolTip.add(DisplayText.getText("LBL-W0230"),
					WmsFormatter.getNumFormat(param[i].getTotalStockCaseQty()));
				toolTip.add(DisplayText.getText("LBL-W0233"),
					WmsFormatter.getNumFormat(param[i].getTotalStockPieceQty()));
			}

			//#CM567225
			// Expiry Date
			toolTip.add(DisplayText.getText("LBL-W0270"), param[i].getUseByDate());
			//#CM567226
			//Set the tool tip in Current line. 
			lst_InventoryMaintenance.setToolTip(i + 1, toolTip.getText());

			//#CM567227
			// Concealed Item set preparation
			List list = new Vector();
			//#CM567228
			// Inventory Work present
			list.add(param[i].getInventoryKind());
			//#CM567229
			// Modify flag
			list.add(StorageSupportParameter.UPDATEFLAG_NO);
			//#CM567230
			// Location
			list.add(param[i].getLocation());
			//#CM567231
			// Item Code
			list.add(param[i].getItemCode());
			//#CM567232
			// Item Name
			list.add(param[i].getItemName());
			//#CM567233
			// Packed qty per case
			list.add(WmsFormatter.getNumFormat(param[i].getEnteringQty()));
			//#CM567234
			// Packed qty per bundle
			list.add(WmsFormatter.getNumFormat(param[i].getBundleEnteringQty()));
			//#CM567235
			// Inventory Case qty
			if (param[i].getInventoryKind().equals(StorageSupportParameter.INVENTORY_KIND_FIND))
			{
				list.add(WmsFormatter.getNumFormat(param[i].getInventoryCheckCaseQty()));
			}
			else
			{
				list.add("");
			}
			//#CM567236
			// Inventory Piece qty
			if (param[i].getInventoryKind().equals(StorageSupportParameter.INVENTORY_KIND_FIND))
			{
				list.add(WmsFormatter.getNumFormat(param[i].getInventoryCheckPieceQty()));
			}
			else
			{
				list.add("");
			}
			//#CM567237
			// Expiry Date
			list.add(param[i].getUseByDate());
			//#CM567238
			// Last updated date and time
			list.add(WmsFormatter.getTimeStampString(param[i].getLastUpdateDate()));
			//#CM567239
			// Work No.
			list.add(param[i].getJobNo());
			//#CM567240
			// Stock ID
			list.add(param[i].getStockID());
			
			
			//#CM567241
			// Set it in Concealed Item. 
			lst_InventoryMaintenance.setValue(HIDDEN, CollectionUtils.getConnectedString(list));

			//#CM567242
			// Present Inventory Work Name
			lst_InventoryMaintenance.setValue(INVENTORYKINDNAME, param[i].getInventoryKindName());
			//#CM567243
			// Location
			lst_InventoryMaintenance.setValue(LOCATION, param[i].getLocation());
			//#CM567244
			// Item Code
			lst_InventoryMaintenance.setValue(ITEMCODE, param[i].getItemCode());
			//#CM567245
			// Packed qty per case
			lst_InventoryMaintenance.setValue(ENTERINGQTY,
				WmsFormatter.getNumFormat(param[i].getEnteringQty()));
			//#CM567246
			// Inventory Case qty, Inventory Piece qty
			if (param[i].getInventoryKind().equals(StorageSupportParameter.INVENTORY_KIND_FIND))
			{
				lst_InventoryMaintenance.setValue(INVENTORYCHECKCASEQTY,
					WmsFormatter.getNumFormat(param[i].getInventoryCheckCaseQty()));
				lst_InventoryMaintenance.setValue(INVENTORYCHECKPIECEQTY,
					WmsFormatter.getNumFormat(param[i].getInventoryCheckPieceQty()));
			}
			else
			{
				lst_InventoryMaintenance.setValue(INVENTORYCHECKCASEQTY, "");
				lst_InventoryMaintenance.setValue(INVENTORYCHECKPIECEQTY, "");
			}

			//#CM567247
			// Stock Case qty, Stock Piece qty
			if (param[i].getTotalStockQty() == 0)
			{
				lst_InventoryMaintenance.setValue(STOCKCASEQTY, "");
				lst_InventoryMaintenance.setValue(STOCKPIECEQTY, "");
			}
			else
			{
				lst_InventoryMaintenance.setValue(STOCKCASEQTY,
					WmsFormatter.getNumFormat(param[i].getTotalStockCaseQty()));
				lst_InventoryMaintenance.setValue(STOCKPIECEQTY,
					WmsFormatter.getNumFormat(param[i].getTotalStockPieceQty()));
			}
			//#CM567248
			// Expiry Date
			lst_InventoryMaintenance.setValue(USEBYDATE, param[i].getUseByDate());
			//#CM567249
			// Item Name
			lst_InventoryMaintenance.setValue(ITEMNAME, param[i].getItemName());
			//#CM567250
			// Packed qty per bundle
			lst_InventoryMaintenance.setValue(BUNDLEENTERINGQTY,
				WmsFormatter.getNumFormat(param[i].getBundleEnteringQty()));
				
		}
		
		//#CM567251
		// Clear ViewState
		clearViewState();
	}

	//#CM567252
	/**
	 * Method which sets data other than the data of Preset area for the Modification in the Parameter array. <BR>
	 * <BR>
	 * Outline:Set data other than the data of Preset area for the Modification in the Parameter array, and return it. <BR>
	 * Return null when the set data does not exist. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the Preset data which is not the data under the modofication in the Parameter class. <BR>
	 * 		<DIR>
	 *   		[Return Data]<BR>
	 *   		<DIR>
	 * 				Consignor Code<BR>
	 * 				Item Code<BR>
	 * 				Location<BR>
	 * 				Expiry Date<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * @param lineno int Line No. for modification
	 * @return <code>StorageSupportParameter</code> array which maintains information on Preset area. Null when there is no set data. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private StorageSupportParameter[] setListParamater(int lineno) throws Exception
	{

		Vector vecParam = new Vector();

		for (int i = 1; i < lst_InventoryMaintenance.getMaxRows(); i++)
		{
			//#CM567253
			// Exclude the line for the modification. 
			if (i == lineno)
			{
				continue;
			}
			//#CM567254
			// Line specification
			lst_InventoryMaintenance.setCurrentRow(i);

			//#CM567255
			// Set in schedule Parameter.
			StorageSupportParameter param = new StorageSupportParameter();
			//#CM567256
			// Input area information
			//#CM567257
			// Location
			param.setLocation(lst_InventoryMaintenance.getValue(LOCATION));
			//#CM567258
			// Consignor Code
			param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());

			//#CM567259
			// Preset area information
			//#CM567260
			// Item Code
			param.setItemCode(lst_InventoryMaintenance.getValue(ITEMCODE));
			//#CM567261
			// Expiry Date
			param.setUseByDate(lst_InventoryMaintenance.getValue(USEBYDATE));
			
			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			//#CM567262
			// The value is set if there is set Preset data. 
			StorageSupportParameter[] listparam = new StorageSupportParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			//#CM567263
			// Null is set if there is no set Preset data. 
			return null;
		}
	}

	//#CM567264
	/**
	 * Method which sets the data of All of Preset area in the Parameter array. <BR>
	 * <BR>
	 * Outline:Set the data of All of Preset area in the Parameter array, and return it. <BR>
	 * Return null when data does not exist in Preset area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the Preset data in the Parameter class when Modify flag is "1". <BR>
	 * 		<DIR>
	 *   		[Return Data]<BR>
	 *   		<DIR>
	 * 				Consignor Code<BR>
	 * 				Consignor Name<BR>
	 * 				Start Location<BR>
	 * 				End Location<BR>
	 * 				Item Code<BR>
	 * 				Item Name<BR>
	 * 				Packed qty per case<BR>
	 * 				Packed qty per bundle<BR>
	 * 				Inventory Case qty<BR>
	 * 				Inventory Piece qty<BR>
	 * 				Stock Case qty<BR>
	 * 				Stock Piece qty<BR>
	 * 				Expiry Date<BR>
	 * 				Inventory Work present<BR>
	 * 				Last updated date and time<BR>
	 * 				Work No.<BR>
	 * 				Stock ID<BR>
	 * 				Preset Line No..<BR>
	 * 				Worker Code<BR>
	 * 				Password<BR>
	 * 				Terminal No.<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * @return <code>StorageSupportParameter</code> array which maintains information on Preset area. Null when there is no data in Preset area. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private StorageSupportParameter[] setListParam() throws Exception
	{

		Vector vecParam = new Vector();

		for (int i = 1; i < lst_InventoryMaintenance.getMaxRows(); i++)
		{
			//#CM567265
			// Line specification
			lst_InventoryMaintenance.setCurrentRow(i);

			if (CollectionUtils.getString(1, lst_InventoryMaintenance.getValue(HIDDEN))
				.equals(StorageSupportParameter.UPDATEFLAG_YES))
			{
				//#CM567266
				// Set in schedule Parameter.
				StorageSupportParameter param = new StorageSupportParameter();
				//#CM567267
				// Input area information
				//#CM567268
				// Consignor Code
				param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
				//#CM567269
				// Consignor Name
				param.setConsignorName(lbl_JavaSetCnsgnrNm.getText());
				//#CM567270
				// Start Location
				param.setFromLocation(lbl_JavaSetStartLocation.getText());
				//#CM567271
				// End Location
				param.setToLocation(lbl_JavaSetEndLocation.getText());

				//#CM567272
				// Preset area information
				//#CM567273
				// Location
				param.setLocation(lst_InventoryMaintenance.getValue(LOCATION));
				//#CM567274
				// Item Code
				param.setItemCode(lst_InventoryMaintenance.getValue(ITEMCODE));
				//#CM567275
				// Packed qty per case
				param.setEnteringQty(WmsFormatter.getInt(lst_InventoryMaintenance.getValue(ENTERINGQTY)));
				//#CM567276
				// Inventory Case qty
				param.setInventoryCheckCaseQty(WmsFormatter.getInt(lst_InventoryMaintenance.getValue(INVENTORYCHECKCASEQTY)));
				//#CM567277
				// Stock Case qty
				param.setTotalStockCaseQty(WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKCASEQTY)));
				//#CM567278
				// Expiry Date
				param.setUseByDate(lst_InventoryMaintenance.getValue(USEBYDATE));
				//#CM567279
				// Item Name
				param.setItemName(lst_InventoryMaintenance.getValue(ITEMNAME));
				//#CM567280
				// Packed qty per bundle
				param.setBundleEnteringQty(WmsFormatter.getInt(lst_InventoryMaintenance.getValue(BUNDLEENTERINGQTY)));
				//#CM567281
				// Inventory Piece qty
				param.setInventoryCheckPieceQty(WmsFormatter.getInt(lst_InventoryMaintenance.getValue(INVENTORYCHECKPIECEQTY)));
				//#CM567282
				// Stock Piece qty
				param.setTotalStockPieceQty(WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKPIECEQTY)));	
				//#CM567283
				// Concealed Item
				//#CM567284
				// Inventory Work present(Concealed Item)
				param.setInventoryKind(CollectionUtils.getString(0, lst_InventoryMaintenance.getValue(HIDDEN)));
				//#CM567285
				// Last updated date and time(Concealed Item)
				param.setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(10, lst_InventoryMaintenance.getValue(HIDDEN))));
				//#CM567286
				// Work No.(Concealed Item)
				param.setJobNo(CollectionUtils.getString(11, lst_InventoryMaintenance.getValue(HIDDEN)));
				//#CM567287
				// Stock ID(Concealed Item)
				param.setStockID(CollectionUtils.getString(12, lst_InventoryMaintenance.getValue(HIDDEN)));
				
				//#CM567288
				// Line No. is maintained. 
				param.setRowNo(i);
				//#CM567289
				// Worker Code
				param.setWorkerCode(this.getViewState().getString(InventoryMaintenanceBusiness.WORKERCODE_KEY));
				//#CM567290
				// Password
				param.setPassword(this.getViewState().getString(InventoryMaintenanceBusiness.PASSWORD_KEY));

				//#CM567291
				// Terminal No is set. 
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());
				
				vecParam.addElement(param);
			}
		}

		if (vecParam.size() > 0)
		{
			//#CM567292
			// The value is set if there is set Preset data. 
			StorageSupportParameter[] listparam = new StorageSupportParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			//#CM567293
			// Null is set if there is no set Preset data. 
			return null;
		}
	}

	//#CM567294
	/**
	 * Method which sets the value of Parameter in Preset area. <BR>
	 * <BR>
	 * Outline:Set the value of Parameter in Preset area. <BR>
	 * Use it when one data is added or updated when entered new and modifying it. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Edit data for the balloon display. <BR>
	 * 		2.Set Concealed Item. <BR>
	 * 		3.Set data in each Item of the list cell. <BR>
	 * 		4.Clear ViewState information.<BR>
	 * </DIR>
	 * <DIR>
	 * 		[Concealed Item]
	 * 		<DIR>
	 * 			0.Inventory Work present <BR>
	 * 			1.Modify flag <BR>
	 * 			2.Location <BR>
	 * 			3.Item Code <BR>
	 * 			4.Item Name <BR>
	 * 			5.Packed qty per case <BR>
	 * 			6.Packed qty per bundle <BR>
	 * 			7.Inventory Case qty <BR>
	 * 			8.Inventory Piece qty <BR>
	 * 			9.Expiry Date <BR>
	 * 			10.Last updated date and time <BR>
	 * 			11.Work No. <BR>
	 * 			12.Stock ID <BR>
	 *	 	</DIR>
	 * </DIR>
	 * <DIR>
	 * 		[List cell row number list]
	 * 		<DIR>
	 * 			0.Concealed Item <BR>
	 * 			3.Present Inventory Work Name <BR>
	 * 			4.Location <BR>
	 * 			5.Item Code <BR>
	 * 			6.Packed qty per case <BR>
	 * 			7.Inventory Case qty <BR>
	 * 			8.Stock Case qty <BR>
	 * 			9.Expiry Date <BR>
	 * 			10.Item Name <BR>
	 * 			11.Packed qty per bundle <BR>
	 * 			12.Inventory Piece qty <BR>
	 * 			13.Stock Piece qty <BR>
	 *	 	</DIR>
	 * </DIR>
	 * <BR>
	 * @param param StorageSupportParameter[] Information displayed in Preset area. 
	 * @param lineno int Preset Line  No. under modification. -1 when new. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setList(StorageSupportParameter param, int lineno) throws Exception
	{
		//#CM567295
		//  Data for ToolTip is edited. 
		ToolTipHelper toolTip = new ToolTipHelper();
		//#CM567296
		// Item Name
		toolTip.add(DisplayText.getText("LBL-W0103"), txt_ItemName.getText());
		//#CM567297
		// Packed qty per case (Packed qty per case of Inventory data) 
		toolTip.add(DisplayText.getText("LBL-W0007"), txt_CaseEntering.getText());
		//#CM567298
		// Packed qty per bundle (Packed qty per bundle of Inventory data) 
		toolTip.add(DisplayText.getText("LBL-W0005"), txt_BundleEntering.getText());
		//#CM567299
		// Stock Case qty, Stock Piece qty
		if (param.getTotalStockQty() == 0)
		{
			toolTip.add(DisplayText.getText("LBL-W0230"),
			 WmsFormatter.getInt(""));
			toolTip.add(DisplayText.getText("LBL-W0233"),
			 WmsFormatter.getInt(""));
		}
		else
		{
			toolTip.add(DisplayText.getText("LBL-W0230"),
				WmsFormatter.getNumFormat(param.getTotalStockCaseQty()));
			toolTip.add(DisplayText.getText("LBL-W0233"),
				WmsFormatter.getNumFormat(param.getTotalStockPieceQty()));
		}
		//#CM567300
		// Expiry Date
		toolTip.add(DisplayText.getText("LBL-W0270"), param.getUseByDate());
		//#CM567301
		// Set the tool tip in Current line. 
		lst_InventoryMaintenance.setToolTip(lst_InventoryMaintenance.getCurrentRow(),
		toolTip.getText());

		//#CM567302
		// Set it in Concealed Item. 
		List list = new Vector();
		if(lineno == -1)
		{
			//#CM567303
			// Inventory Work present
			list.add(StorageSupportParameter.INVENTORY_KIND_NOTHING);		
		}
		else if(CollectionUtils.getString(0, lst_InventoryMaintenance.getValue(HIDDEN)).equals(StorageSupportParameter.INVENTORY_KIND_NOTHING))
		{		
			//#CM567304
			// Inventory Work present
			list.add(StorageSupportParameter.INVENTORY_KIND_NOTHING);
		}
		else
		{
			//#CM567305
			// Inventory Work present
			list.add(StorageSupportParameter.INVENTORY_KIND_FIND);	
		}
		//#CM567306
		// Modify flag
		list.add(StorageSupportParameter.UPDATEFLAG_YES);
		//#CM567307
		// Location
		list.add("");
		//#CM567308
		// Item Code
		list.add("");
		//#CM567309
		// Item Name
		list.add("");
		//#CM567310
		// Packed qty per case
		list.add("");
		//#CM567311
		// Packed qty per bundle
		list.add("");
		//#CM567312
		// Inventory Case qty
		list.add("");
		//#CM567313
		// Inventory Piece qty
		list.add("");
		//#CM567314
		// Expiry Date
		list.add("");
		//#CM567315
		// Last updated date and time
		list.add(this.getViewState().getString(LASTUPDATE_KEY));
		//#CM567316
		// Work No.
		list.add(this.getViewState().getString(JOBNO_KEY));
		//#CM567317
		// Stock ID
		list.add(this.getViewState().getString(STOCKID_KEY));				
		//#CM567318
		// Set it in Concealed Item. 
		lst_InventoryMaintenance.setValue(HIDDEN, CollectionUtils.getConnectedString(list));

		//#CM567319
		// Present Inventory Work Name
		//#CM567320
		// LBL-W0394=Undone
		if(CollectionUtils.getString(0, lst_InventoryMaintenance.getValue(HIDDEN)).equals(StorageSupportParameter.INVENTORY_KIND_NOTHING))
		{
			lst_InventoryMaintenance.setValue(INVENTORYKINDNAME, DisplayText.getText("LBL-W0394"));
		}
		//#CM567321
		// LBL-W0391=Enter
		else
		{
			lst_InventoryMaintenance.setValue(INVENTORYKINDNAME, DisplayText.getText("LBL-W0391"));
		}
		
		//#CM567322
		// Location
		lst_InventoryMaintenance.setValue(LOCATION, param.getLocation());
		//#CM567323
		// Item Code
		lst_InventoryMaintenance.setValue(ITEMCODE, param.getItemCode());
		//#CM567324
		// Packed qty per case
		lst_InventoryMaintenance.setValue(ENTERINGQTY,
			WmsFormatter.getNumFormat(param.getEnteringQty()));
		//#CM567325
		// Inventory Case qty
		lst_InventoryMaintenance.setValue(INVENTORYCHECKCASEQTY,
			WmsFormatter.getNumFormat(param.getInventoryCheckCaseQty()));
		//#CM567326
		// Stock Case qty
		lst_InventoryMaintenance.setValue(STOCKCASEQTY,lst_InventoryMaintenance.getValue(STOCKCASEQTY));
		//#CM567327
		// Expiry Date
		lst_InventoryMaintenance.setValue(USEBYDATE, param.getUseByDate());
		//#CM567328
		// Item Name
		lst_InventoryMaintenance.setValue(ITEMNAME, param.getItemName());
		//#CM567329
		// Packed qty per bundle
		lst_InventoryMaintenance.setValue(BUNDLEENTERINGQTY,
			WmsFormatter.getNumFormat(param.getBundleEnteringQty()));
		//#CM567330
		// Inventory Piece qty
		lst_InventoryMaintenance.setValue(INVENTORYCHECKPIECEQTY,
			WmsFormatter.getNumFormat(param.getInventoryCheckPieceQty()));
		//#CM567331
		// Stock Piece qty
		lst_InventoryMaintenance.setValue(STOCKPIECEQTY,lst_InventoryMaintenance.getValue(STOCKPIECEQTY));
		
		//#CM567332
		// Clear ViewState
		clearViewState();
	}

	//#CM567333
	/**
	 * Clear ViewState information.<BR>
	 * <BR>
	 */
	private void clearViewState()
	{
		this.getViewState().setInt(LINENO_KEY, -1);
		this.getViewState().setString(LOCATION_KEY,"");
		this.getViewState().setString(ITEMCODE_KEY,"");
		this.getViewState().setString(ITEMNAME_KEY,"");
		this.getViewState().setString(ENTERINGQTY_KEY,"");
		this.getViewState().setString(BUNDLEENTERINGQTY_KEY,"");
		this.getViewState().setString(INVENTORYCHECKCASEQTY_KEY,"");
		this.getViewState().setString(INVENTORYCHECKPIECEQTY_KEY,"");
		this.getViewState().setString(USEBYDATE_KEY,"");
		this.getViewState().setString(LASTUPDATE_KEY, "");
		this.getViewState().setString(JOBNO_KEY, "");
		this.getViewState().setString(STOCKID_KEY, "");
		
	}

	//#CM567334
	// Event handler methods -----------------------------------------
	//#CM567335
	/**
	 * It is called when the Return button is pressed.<BR>
	 * <BR>
	 * Outline:It changes to the Inventory (Essential information Setting) screen. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		//#CM567336
		// Information details setting screen -> Essential information Setting Screen
		forward("/storage/inventorymaintenance/InventoryMaintenance.do");
	}

	//#CM567337
	/**
	 * It is called when the menu button is pressed.<BR>
	 * <BR>
	 * Outline:Change to the menu panel.<BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM567338
	/**
	 * It is called when the retrieval button of Item Code is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the item list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Item Code <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearch_Click(ActionEvent e) throws Exception
	{
		//#CM567339
		// Set the search condition on the Item retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM567340
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			lbl_JavaSetCnsgnrCd.getText());
		//#CM567341
		// Item Code
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM567342
		// Retrieval flag
		param.setParameter(
			ListStorageItemBusiness.SEARCHITEM_KEY,
			StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK);

		//#CM567343
		// Area type flag (Save AS/RS when you display the list. ) 
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM567344
		// Processing Screen -> Result screen
		redirect("/storage/listbox/liststorageitem/ListStorageItem.do", param, "/progress.do");
	}

	//#CM567345
	/**
	 * It is called when the input button is pressed.<BR>
	 * <BR>
	 * Outline : Do Input Check of Input area for new Add, and display it in Preset. <BR>
	 * Do Input Check of Input area at the modification, and update the data of Preset for the modification. <BR>
	 * <BR>
	 * <DIR>
	 *		1.Set the cursor. (Set Item Code only when Item Code is not for reading, otherwise set Inventory Case qty.)<BR>
	 * 		2.Check Input area input Item. (Mandatory input, Number of characters, Character attribute)<BR>
	 * 		3.Start Schedule.<BR>
	 * 		<DIR>
	 *			[Parameter] *Mandatory input <BR>
	 *			<DIR>
	 *				Consignor Code* <BR>
	 *				Consignor Name <BR>
	 *				Item Code* <BR>
	 *				Item Name <BR>
	 *				Packed qty per case* <BR>
	 *				Inventory Case qty* <BR>
	 *				Inventory Piece qty* <BR>
	 *				Location* <BR>
	 *				Expiry Date <BR>
	 *				Preset Line No.. <BR>
	 *			</DIR>
	 *		</DIR>
	 *		When the result of the schedule is true<BR>
	 *		4.Update the modify data of Preset on information of Input area in case of Modification.<BR>
	 *		  Add to Preset on information of Input area in case of new Add. <BR>
	 *		5.Return it based on the color of the line for the modofication in case of modification. <BR>
	 *		6.Set Preset Line No. of ViewState to Default(Initial value:-1). <BR>
	 *		7.Clears the content of Input area.<BR>
	 *		8.Output the Message acquired from the schedule to the screen. <BR>
	 *		When the result of the schedule is false <BR>
	 *		9.Output the error Message acquired from the schedule to the screen. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		int lineno = this.getViewState().getInt(LINENO_KEY);
		
		//#CM567346
		// Set the cursor in Item Code. 
		if (!txt_ItemCode.getReadOnly())
		{
			setFocus(txt_ItemCode);
		}
		else
		{
			setFocus(txt_InventoryCaseQty);
		}
		
		//#CM567347
		// Mandatory Input check
		txt_ItemCode.validate();
		//#CM567348
		// Request the input when both Inventory Case qty and Piece qty are not entered.
		//#CM567349
		// Because it cannot be acquired whether it is not entered from the input field
		//#CM567350
		// Catch and check ValidateException. 
		try
		{
			txt_InventoryCaseQty.validate();
		}
		catch (ValidateException cve)
		{
			try
			{
				txt_InventoryPieceQty.validate();
			}
			catch (ValidateException pve)
			{
				//#CM567351
				// 6023323=Enter {0} or {1}.
				//#CM567352
				// LBL-W0092=Inventory Check Case qty
				//#CM567353
				// LBL-W0094=Inventory Check Piece qty
				message.setMsgResourceKey("6023323" + wDelim  + DispResources.getText("LBL-W0092") + wDelim + DispResources.getText("LBL-W0094"));
				return;
			}
		}
		txt_Location.validate();

		//#CM567354
		// Pattern match character
		txt_ItemName.validate(false);
		txt_CaseEntering.validate(false);
		txt_BundleEntering.validate(false);
		txt_UseByDate.validate(false);

		//#CM567355
		// Input character check for eWareNavi
		if (!checkContainNgText())
		{
			return;
		}

		Connection conn = null;

		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new InventoryMaintenanceSCH();

			//#CM567356
			// Set in schedule Parameter.
			//#CM567357
			// Input area
			StorageSupportParameter param = new StorageSupportParameter();
				
			//#CM567358
			// Line No.
			param.setRowNo(lineno);
			//#CM567359
			// Consignor Code
			param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
			//#CM567360
			// Consignor Name
			param.setConsignorName(lbl_JavaSetCnsgnrNm.getText());
			//#CM567361
			// Item Code
			param.setItemCode(txt_ItemCode.getText());
			//#CM567362
			// Item Name
			param.setItemName(txt_ItemName.getText());
			//#CM567363
			// Packed qty per case
			param.setEnteringQty(txt_CaseEntering.getInt());
			//#CM567364
			// Packed qty per bundle
			param.setBundleEnteringQty(txt_BundleEntering.getInt());
			//#CM567365
			// Inventory Case qty
			param.setInventoryCheckCaseQty(txt_InventoryCaseQty.getInt());
			//#CM567366
			// Inventory Piece qty
			param.setInventoryCheckPieceQty(txt_InventoryPieceQty.getInt());
			
			if(lineno == -1)
			{
				//#CM567367
				// Stock Case qty
				param.setTotalStockCaseQty(0);
				//#CM567368
				// Stock Piece qty
				param.setTotalStockPieceQty(0);
				//#CM567369
				// Total Stock qty
				param.setTotalStockQty(0);
			}
			else
			{
				lst_InventoryMaintenance.setCurrentRow(this.getViewState().getInt(LINENO_KEY));
				//#CM567370
				// Stock Case qty
				param.setTotalStockCaseQty(WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKCASEQTY)));
				//#CM567371
				// Stock Piece qty
				param.setTotalStockPieceQty(WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKPIECEQTY)));
				//#CM567372
				// Total Stock qty
				param.setTotalStockQty((WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKCASEQTY))
						* txt_CaseEntering.getInt() 
						+ WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKPIECEQTY))));
			}

			//#CM567373
			// Location
			param.setLocation(txt_Location.getText());
			//#CM567374
			// Expiry Date
			param.setUseByDate(txt_UseByDate.getText());
			//#CM567375
			// Last updated date and time
			param.setLastUpdateDate(WmsFormatter.getTimeStampDate(this.getViewState().getString(LASTUPDATE_KEY)));
			//#CM567376
			// Work No.
			param.setJobNo(this.getViewState().getString(JOBNO_KEY));
			//#CM567377
			// Stock ID
			param.setStockID(this.getViewState().getString(STOCKID_KEY));
			
			//#CM567378
			// Set in schedule Parameter.
			//#CM567379
			// Preset area
			StorageSupportParameter[] listparam = null;
			//#CM567380
			// Null is set if there is no data in Preset. 
			if (lst_InventoryMaintenance.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				//#CM567381
				// If data exists, the value is set. 
				listparam = setListParamater(this.getViewState().getInt(LINENO_KEY));
			}

			if (schedule.check(conn, param, listparam))
			{
				//#CM567382
				// If the result is true, data is set in Preset area. 
				if (this.getViewState().getInt(LINENO_KEY) == -1)
				{
					//#CM567383
					// Add to Preset in case of new input. 
					lst_InventoryMaintenance.addRow();
					lst_InventoryMaintenance.setCurrentRow(lst_InventoryMaintenance.getMaxRows() - 1);
					setList(param, lineno);
				}
				else
				{
					//#CM567384
					// The data of the line for the modification is updated in case of the input data under the modification.
					lst_InventoryMaintenance.setCurrentRow(this.getViewState().getInt(LINENO_KEY));
					setList(param, lineno);
					//#CM567385
					// Return it based on the color of the selection line. 
					lst_InventoryMaintenance.resetHighlight();
				}
				
				//#CM567386
				// Make Inventory data update button effective. 
				if (lst_InventoryMaintenance.getMaxRows() > 1)
				{
					//#CM567387
					// Make the button pressing effective.
					//#CM567388
					// Inventory data update button
					btn_InventoryData.setEnabled(true);
				}
				//#CM567389
				// Return Status to Default
				this.getViewState().setInt(LINENO_KEY, -1);

				//#CM567390
				// Clear all items of Input area
				txt_ItemCode.setText("");
				txt_ItemName.setText("");
				txt_CaseEntering.setText("");
				txt_BundleEntering.setText("");
				txt_InventoryCaseQty.setText("");
				txt_InventoryPieceQty.setText("");
				txt_Location.setText("");
				txt_UseByDate.setText("");
				
				btn_PSearch.setEnabled(true);
				txt_ItemCode.setReadOnly(false);
				txt_ItemName.setReadOnly(false);
				txt_CaseEntering.setReadOnly(false);
				txt_BundleEntering.setReadOnly(false);
				txt_Location.setReadOnly(false);
				txt_UseByDate.setReadOnly(false);

				setFocus(txt_ItemCode);

			}
			//#CM567391
			// Set Message
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM567392
				// Close Connection
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

	//#CM567393
	/**
	 * It is called when the clear button is pressed.<BR>
	 * <BR>
	 * Outline : Clear the Input area.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the cursor. (Inventory Case qty when modifying it and Item Code when it is not so)<BR>
	 * 		2.Return Initial value Item of Input area. <BR>
	 * 		When modifying it<BR>
	 * 		<DIR>
	 *   		Item Name[Initial value]<BR>
	 *   		<DIR>
	 * 				Item Code[As it is] <BR>
	 * 				Item Name[As it is] <BR>
	 * 				Packed qty per case[As it is] <BR>
	 * 				Packed qty per bundle[As it is] <BR>
	 * 				Inventory Case qty[None] <BR>
	 * 				Inventory Piece qty[None] <BR>
	 * 				Location[As it is] <BR>
	 * 				Expiry Date[As it is] <BR>
	 * 	 		</DIR>
	 *   	</DIR>
	 * 		When not modifying it
	 * 		<DIR>
	 *   		Item Name[Initial value]<BR>
	 *   		<DIR>
	 * 				Item Code[None] <BR>
	 * 				Item Name[None] <BR>
	 * 				Packed qty per case[None] <BR>
	 * 				Packed qty per bundle[None] <BR>
	 * 				Inventory Case qty[None] <BR>
	 * 				Inventory Piece qty[None] <BR>
	 * 				Location[None] <BR>
	 * 				Expiry Date[None] <BR>
	 * 	 		</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		
		//#CM567394
		// Make the Item useable.
		//#CM567395
		// To Modify existing data when you cannot input Item Code
		//#CM567396
		// Clear only the InventoryInput area.
		if (txt_ItemCode.getReadOnly())
		{
			setFocus(txt_InventoryCaseQty);
			txt_InventoryCaseQty.setText("");
			txt_InventoryPieceQty.setText("");
		}
		else
		{
			setFocus(txt_ItemCode);
			txt_ItemCode.setText("");
			txt_ItemName.setText("");
			txt_CaseEntering.setText("");
			txt_BundleEntering.setText("");
			txt_InventoryCaseQty.setText("");
			txt_InventoryPieceQty.setText("");
			txt_Location.setText("");
			txt_UseByDate.setText("");
		}
	}

	//#CM567397
	/**
	 * It is called when Inventory data update button is pressed.<BR>
	 * <BR>
	 * Outline:In information on Preset area, update the Inventory data. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the cursor in Item Code. <BR>
	 * 		2.Display the dialog box, and confirm whether to update the Inventory data. <BR>
	 * 		<DIR>
	 * 			[Confirmation Dialog Cancel]<BR>
	 * 			<DIR>
	 * 				Do Nothing.
	 * 			</DIR>
	 *			[Confirmation Dialog OK]<BR>
	 * 			<DIR>
	 * 				1.Start Schedule.<BR>
	 * 				<DIR>
	 * 					[Parameter]<BR>
	 * 					<DIR>
	 * 						Consignor Code<BR>
	 * 						Consignor Name<BR>
	 * 						Start Location<BR>
	 * 						End Location<BR>
	 * 						Item Code<BR>
	 * 						Item Name<BR>
	 * 						Packed qty per case<BR>
	 * 						Packed qty per bundle<BR>
	 * 						Inventory Case qty<BR>
	 * 						Inventory Piece qty<BR>
	 * 						Stock Case qty<BR>
	 * 						Stock Piece qty<BR>
	 * 						Expiry Date<BR>
	 * 						Inventory Work present<BR>
	 * 						Last updated date and time<BR>
	 * 						Work No.<BR>
	 * 						Stock ID<BR>
	 * 						Preset Line No.<BR>
	 * 						Worker Code<BR>
	 * 						Password<BR>
	 * 						Terminal No.<BR>
	 *					</DIR>
	 * 					<BR>
	 * 					[Return Data]<BR>
	 * 					<DIR>
	 * 						Present Inventory Work Name <BR>
	 * 						Location <BR>
	 * 						Item Code <BR>
	 *						Item Name <BR>
	 *						Packed qty per case <BR>
	 *						Packed qty per bundle <BR>
	 *						Inventory Case qty <BR>
	 *						Inventory Piece qty <BR>
	 *						Stock Case qty <BR>
	 *						Stock Piece qty <BR>
	 *						Expiry Date <BR>
	 *						Inventory Work present <BR>
	 *					</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.Display the data of Preset again based on Essential information Setting when the result of the schedule is true. <BR>
	 *				3.When the result of the schedule is true, Clear all Items of Input area. <BR>
	 *				4.If the result of the schedule is true, Set Preset Line No. of ViewState to Default(Initial value:-1). <BR>
	 *				5.If the result of the schedule is true, Return it based on the color of the line for the modification. <BR>
	 *				6.Output the Message acquired from the schedule to the screen. <BR>
	 *			</DIR>
	 *		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_InventoryData_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM567398
			// Set the cursor in Item Code. 
			setFocus(txt_ItemCode);

			//#CM567399
			// Set in schedule Parameter.
			StorageSupportParameter[] param = null;
			//#CM567400
			// All data of Preset area is set. 
			param = setListParam();
			
			if (param == null || (param != null && param.length == 0))
			{
				//#CM567401
				// 6023154=There is no data to update.
				message.setMsgResourceKey("6023154");
				return;
			}

			//#CM567402
			// Button type
			param[0].setButtonType(StorageSupportParameter.BUTTON_MODIFYSUBMIT);
			
			//#CM567403
			// Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new InventoryMaintenanceSCH();
			
			StorageSupportParameter[] viewParam =
				(StorageSupportParameter[]) schedule.startSCHgetParams(conn, param);

			//#CM567404
			// Receive Message and end when the error etc. occur. 
			if (viewParam == null)
			{
				//#CM567405
				// Rollback the connection. 
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM567406
			// Commit when Completed normally
			conn.commit();

			//#CM567407
			// Input areaClear
			txt_ItemCode.setText("");
			txt_ItemName.setText("");
			txt_CaseEntering.setText("");
			txt_BundleEntering.setText("");
			txt_InventoryCaseQty.setText("");
			txt_InventoryPieceQty.setText("");
			txt_Location.setText("");
			txt_UseByDate.setText("");
							
			btn_PSearch.setEnabled(true);
			txt_ItemCode.setReadOnly(false);
			txt_ItemName.setReadOnly(false);
			txt_CaseEntering.setReadOnly(false);
			txt_BundleEntering.setReadOnly(false);
			txt_Location.setReadOnly(false);
			txt_UseByDate.setReadOnly(false);

			//#CM567408
			// Preset areaClear
			lst_InventoryMaintenance.clearRow();

			//#CM567409
			// Preset area
			setList(viewParam);
			
			//#CM567410
			// Set Message
			message.setMsgResourceKey(schedule.getMessage());

			//#CM567411
			// Invalidate the Inventory data update button when Preset information does not exist. 
			if (lst_InventoryMaintenance.getMaxRows() == 1)
			{
				//#CM567412
				// Invalidate the button pressing. 
				//#CM567413
				// Inventory data update button
				btn_InventoryData.setEnabled(false);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM567414
				// Close Connection
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

	//#CM567415
	/**
	 * It is called when the deletion and the modification button are pressed.<BR>
	 * <BR>
	 * Deletion button Outline : Delete pertinent data of Preset. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Display the dialog box and confirm whether to delete Preset information. <BR>
	 * 		<DIR>
	 * 			[Confirmation Dialog Cancel]<BR>
	 * 			<DIR>
	 * 				Do Nothing.
	 *			</DIR>
	 * 			[Confirmation Dialog OK]<BR>
	 *			<DIR>
	 *				1.Set Parameter of the selection line. <BR>
	 *				<DIR>
	 *   				[Parameter]<BR>
	 *   				<DIR>
	 * 						Worker Code<BR>
	 * 						Password<BR>
	 * 						Terminal No.<BR>
	 * 						Location<BR>
	 * 						Consignor Code<BR>
	 * 						Item Code<BR>
	 * 						Stock Case qty<BR>
	 * 						Stock Piece qty<BR>
	 * 						Expiry Date<BR>
	 * 						Updated day and hour<BR>
	 * 						Work No.<BR>
	 * 						Stock ID<BR>
	 * 						Button type<BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				2.Decide whether to be able to delete it without beginning the schedule and process it when AddFlag is Not Add.<BR>
	 *				3.Start Schedule when AddFlag is Add.<BR>
	 *				4.Clear Input area and Delete pertinent data of Preset when the result of the schedule is true. <BR>
	 *				5.Invalidate Inventory data update button when Preset information does not exist. <BR>
	 *    			6.Output the Message acquired from the schedule to the screen. <BR>
	 *				7.Initialize the input area. <BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * <BR>
	 * Modification button Outline:Make pertinent data of Preset modification Status. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Check whether it is possible to edit the stock total. <BR>
	 * 		2.Display selected information in Input area. <BR>
	 * 		3.Make the selected information part light yellow. <BR>
	 * 		4.Set the line to Preset Line No of ViewState now. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void lst_InventoryMaintenance_Click(ActionEvent e) throws Exception
	{

		//#CM567416
		// Set Cursor
		if (!txt_ItemCode.getReadOnly())
		{
			setFocus(txt_ItemCode);
		}
		else
		{
			setFocus(txt_InventoryCaseQty);
		}
		
		//#CM567417
		// When Delete button is clicked
		if (lst_InventoryMaintenance.getActiveCol() == 1)
		{
			Connection conn = null;
			try
			{
				//#CM567418
				// A present line is set. 
				lst_InventoryMaintenance.setCurrentRow(lst_InventoryMaintenance.getActiveRow());

				//#CM567419
				// Set in schedule Parameter.
				StorageSupportParameter[] param = new StorageSupportParameter[1];

				param[0] = new StorageSupportParameter();

				//#CM567420
				// Decided whether it is possible to delete with Business when AddFlag is Not Add.
				if (CollectionUtils.getString(0, lst_InventoryMaintenance.getValue(HIDDEN)).equals(StorageSupportParameter.INVENTORY_KIND_NOTHING))
				{
					//#CM567421
					// Check whether it is possible to delete it or not
					if (StringUtil.isBlank(lst_InventoryMaintenance.getValue(INVENTORYCHECKCASEQTY)) 
						&& StringUtil.isBlank(lst_InventoryMaintenance.getValue(INVENTORYCHECKPIECEQTY)))
					{
						//#CM567422
						//6023363=Cannot delete. No inventory check data is found.
						message.setMsgResourceKey("6023363");
						return ;
					}
					else if(param[0].getTotalStockCaseQty() != 0 || param[0].getTotalStockPieceQty() != 0)
					{
						//#CM567423
						//6023363=Cannot delete. No inventory check data is found.
						message.setMsgResourceKey("6023363");
						return ;						
					}
				}
				
				//#CM567424
				// Input area information
				//#CM567425
				// Worker Code
				param[0].setWorkerCode(this.getViewState().getString(InventoryMaintenanceBusiness.WORKERCODE_KEY));
				//#CM567426
				// Password
				param[0].setPassword(this.getViewState().getString(InventoryMaintenanceBusiness.PASSWORD_KEY));
				//#CM567427
				// Terminal No is set. 
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param[0].setTerminalNumber(userHandler.getTerminalNo());
				//#CM567428
				// Location
				param[0].setLocation(lst_InventoryMaintenance.getValue(LOCATION));
				//#CM567429
				// Consignor Code
				param[0].setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
				//#CM567430
				// Item Code
				param[0].setItemCode(lst_InventoryMaintenance.getValue(ITEMCODE));
				
				if(lst_InventoryMaintenance.getValue(STOCKCASEQTY) == null 
				|| lst_InventoryMaintenance.getValue(STOCKPIECEQTY) == null)
				{
					//#CM567431
					// Stock Case qty
					param[0].setTotalStockCaseQty(WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKCASEQTY)));
					//#CM567432
					// Stock Piece qty
					param[0].setTotalStockPieceQty(WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKPIECEQTY)));

				}
				else
				{
					//#CM567433
					// Stock Case qty
					param[0].setTotalStockCaseQty(WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKCASEQTY)));
					//#CM567434
					// Stock Piece qty
					param[0].setTotalStockPieceQty(WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKPIECEQTY)));

				}
				//#CM567435
				// Expiry Date
				param[0].setUseByDate(lst_InventoryMaintenance.getValue(USEBYDATE));
				//#CM567436
				// Last updated date and time
				param[0].setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(10, lst_InventoryMaintenance.getValue(HIDDEN))));
				//#CM567437
				// Work No.
				param[0].setJobNo(CollectionUtils.getString(11, lst_InventoryMaintenance.getValue(HIDDEN)));
				//#CM567438
				// Stock ID
				param[0].setStockID(CollectionUtils.getString(12, lst_InventoryMaintenance.getValue(HIDDEN)));
				//#CM567439
				// Button type
				param[0].setButtonType(StorageSupportParameter.BUTTON_DELETESUBMIT);
				
				//#CM567440
				// Decided whether it is possible to delete with Business when AddFlag is Not Add.
				if (CollectionUtils.getString(0, lst_InventoryMaintenance.getValue(HIDDEN)).equals(StorageSupportParameter.INVENTORY_KIND_NOTHING))
				{
					//#CM567441
					// Check whether it is possible to delete it or not
					if (StringUtil.isBlank(lst_InventoryMaintenance.getValue(INVENTORYCHECKCASEQTY)) 
						&& StringUtil.isBlank(lst_InventoryMaintenance.getValue(INVENTORYCHECKPIECEQTY)))
					{
						//#CM567442
						//6023363=Cannot delete. No inventory check data is found.
						message.setMsgResourceKey("6023363");
						return ;
					}
					else if(param[0].getTotalStockCaseQty() != 0 || param[0].getTotalStockPieceQty() != 0)
					{
						//#CM567443
						//6023363=Cannot delete. No inventory check data is found.
						message.setMsgResourceKey("6023363");
						return ;						
					}
					else if(StringUtil.isBlank(lst_InventoryMaintenance.getValue(STOCKCASEQTY)) 
					&& StringUtil.isBlank(lst_InventoryMaintenance.getValue(STOCKPIECEQTY))) 
					{
						btn_PSearch.setEnabled(true);
						//#CM567444
						// Deleted Preset line is deleted. 
						lst_InventoryMaintenance.removeRow(lst_InventoryMaintenance.getActiveRow());
						//#CM567445
						// Input areaClear
						clearInputArea();
						//#CM567446
						// Invalidate the Inventory data update button when Preset information does not exist. 
						if (lst_InventoryMaintenance.getMaxRows() == 1)
						{
							//#CM567447
							// Invalidate the button pressing. 
							//#CM567448
							// Inventory data update button
							btn_InventoryData.setEnabled(false);
						}
						//#CM567449
						// Clear ViewState
						clearViewState();
						//#CM567450
						// 6001005=Deleted.
						message.setMsgResourceKey("6001005");	

						return ;
					}
					else
					{
						//#CM567451
						// Concealed Item set preparation
					 	List list = new Vector();
					 	//#CM567452
					 	// Inventory Work present
					 	list.add(StorageSupportParameter.INVENTORY_KIND_NOTHING);
					 	//#CM567453
					 	// Modify flag
					 	list.add(StorageSupportParameter.UPDATEFLAG_NO);
					 	//#CM567454
					 	// Location
					 	list.add(lst_InventoryMaintenance.getValue(LOCATION));
					 	//#CM567455
					 	// Item Code
					 	list.add(lst_InventoryMaintenance.getValue(ITEMCODE));
					 	//#CM567456
					 	// Item Name
					 	list.add(lst_InventoryMaintenance.getValue(ITEMNAME));
						//#CM567457
						// Packed qty per case
						list.add(WmsFormatter.getNumFormat(WmsFormatter.getInt(lst_InventoryMaintenance.getValue(ENTERINGQTY))));
						//#CM567458
						// Packed qty per bundle
						list.add(WmsFormatter.getNumFormat(WmsFormatter.getInt(lst_InventoryMaintenance.getValue(BUNDLEENTERINGQTY))));
					 	//#CM567459
					 	// Inventory Case qty
					 	list.add("");
					 	//#CM567460
					 	// Inventory Piece qty
					 	list.add("");
					 	//#CM567461
					 	// Expiry Date
					 	list.add(lst_InventoryMaintenance.getValue(USEBYDATE));
						//#CM567462
						// Last updated date and time
						list.add(WmsFormatter.getTimeStampString(param[0].getLastUpdateDate()));
						//#CM567463
						// Work No.
						list.add(param[0].getJobNo());
						//#CM567464
						// Stock ID
						list.add(param[0].getStockID());
					 	//#CM567465
					 	// Set it in Concealed Item. 
					 	lst_InventoryMaintenance.setValue(HIDDEN, CollectionUtils.getConnectedString(list));
						
						//#CM567466
						// Inventory Case qty
						lst_InventoryMaintenance.setValue(INVENTORYCHECKCASEQTY, "");
						//#CM567467
						// Inventory Piece qty
						lst_InventoryMaintenance.setValue(INVENTORYCHECKPIECEQTY, "");
						//#CM567468
						// Stock Case qty
						lst_InventoryMaintenance.setValue(STOCKCASEQTY, WmsFormatter.getNumFormat(param[0].getTotalStockCaseQty()));
						//#CM567469
						// Stock Piece qty
						lst_InventoryMaintenance.setValue(STOCKPIECEQTY, WmsFormatter.getNumFormat(param[0].getTotalStockPieceQty()));

						//#CM567470
						// Clear ViewState
						clearViewState();
						//#CM567471
						// 6001005=Deleted.
						message.setMsgResourceKey("6001005");
						return ;
					}
				}
				else
				{

					conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	
					WmsScheduler schedule = new InventoryMaintenanceSCH();
	
					if (schedule.startSCH(conn, param))
					{
						//#CM567472
						// Commit if processing completes normally.
						conn.commit();
	
						//#CM567473
						// Input areaClear
						clearInputArea();
	
						//#CM567474
						// Decided whether there is data in the stock table. 
						String invcase = lst_InventoryMaintenance.getValue(INVENTORYCHECKCASEQTY);
						String invpiece = lst_InventoryMaintenance.getValue(INVENTORYCHECKPIECEQTY);
						String stockcase = lst_InventoryMaintenance.getValue(STOCKCASEQTY);
						String stockpiece = lst_InventoryMaintenance.getValue(STOCKPIECEQTY);
	
						//#CM567475
						// When the stock exists, it is not possible to change excluding Inventory Case qty and Inventory Piece qty. 
						if ((!StringUtil.isBlank(invcase) && !StringUtil.isBlank(stockcase))
							|| (StringUtil.isBlank(invcase) && !StringUtil.isBlank(stockcase))
						    || (!StringUtil.isBlank(invpiece) && !StringUtil.isBlank(stockpiece))
						    || (StringUtil.isBlank(invpiece) && !StringUtil.isBlank(stockpiece)))
						{
							//#CM567476
							// Concealed Item set preparation
							List list = new Vector();
							//#CM567477
							// Inventory Work present
							list.add(StorageSupportParameter.INVENTORY_KIND_NOTHING);
							//#CM567478
							// Modify flag
							list.add(StorageSupportParameter.UPDATEFLAG_NO);
							//#CM567479
							// Location
							list.add(lst_InventoryMaintenance.getValue(LOCATION));
							//#CM567480
							// Item Code
							list.add(lst_InventoryMaintenance.getValue(ITEMCODE));
							//#CM567481
							// Item Name
							list.add(lst_InventoryMaintenance.getValue(ITEMNAME));
							//#CM567482
							// Packed qty per case
							list.add(WmsFormatter.getNumFormat(WmsFormatter.getInt(lst_InventoryMaintenance.getValue(ENTERINGQTY))));
							//#CM567483
							// Packed qty per bundle
							list.add(WmsFormatter.getNumFormat(WmsFormatter.getInt(lst_InventoryMaintenance.getValue(BUNDLEENTERINGQTY))));
							//#CM567484
							// Inventory Case qty
							list.add("");
							//#CM567485
							// Inventory Piece qty
							list.add("");			
							//#CM567486
							// Expiry Date
							list.add(lst_InventoryMaintenance.getValue(USEBYDATE));
							//#CM567487
							// Last updated date and time
							list.add(WmsFormatter.getTimeStampString(param[0].getLastUpdateDate()));
							//#CM567488
							// Work No.
							list.add(param[0].getJobNo());		
							//#CM567489
							// Stock ID
							list.add(param[0].getStockID());						
							//#CM567490
							// Set it in Concealed Item. 
							lst_InventoryMaintenance.setValue(HIDDEN, CollectionUtils.getConnectedString(list));
							//#CM567491
							// Inventory Case qty
							lst_InventoryMaintenance.setValue(INVENTORYCHECKCASEQTY, "");
							//#CM567492
							// Inventory Piece qty
							lst_InventoryMaintenance.setValue(INVENTORYCHECKPIECEQTY, "");
							//#CM567493
							// AddFlag
							lst_InventoryMaintenance.setValue(INVENTORYKINDNAME,DisplayText.getText("LBL-W0394"));
						}
						else
						{
							//#CM567494
							// Deleted Preset line is deleted. 
							lst_InventoryMaintenance.removeRow(lst_InventoryMaintenance.getActiveRow());
							clearViewState();
						}
	
						//#CM567495
						// Invalidate the Inventory data update button when Preset information does not exist. 
						if (lst_InventoryMaintenance.getMaxRows() == 1)
						{
							//#CM567496
							// Invalidate the button pressing. 
							//#CM567497
							// Inventory data update button
							btn_InventoryData.setEnabled(false);
						}
						clearViewState();
						//#CM567498
						// Set Message
						message.setMsgResourceKey(schedule.getMessage());
					}
					else
					{
						//#CM567499
						// Rollback when terminating abnormally
						conn.rollback();
						//#CM567500
						// Set Message
						message.setMsgResourceKey(schedule.getMessage());
					}
				}

				//#CM567501
				// Set the cursor in Item Code. 
				setFocus(txt_ItemCode);
				//#CM567502
				// Input area can be entered. 
				btn_PSearch.setEnabled(true);
				txt_ItemCode.setReadOnly(false);
				txt_ItemName.setReadOnly(false);
				txt_CaseEntering.setReadOnly(false);
				txt_BundleEntering.setReadOnly(false);
				txt_Location.setReadOnly(false);
				txt_UseByDate.setReadOnly(false);
				//#CM567503
				// Return it based on the color of the selection line. 
				lst_InventoryMaintenance.setHighlight(-1);
			}
			catch (Exception ex)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
			}
			finally
			{
				try
				{
					//#CM567504
					// Close Connection
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
		//#CM567505
		// When Modify button is clicked (Execute Modify)
		else if (lst_InventoryMaintenance.getActiveCol() == 2)
		{

			//#CM567506
			// A present line is set. 
			lst_InventoryMaintenance.setCurrentRow(lst_InventoryMaintenance.getActiveRow());

			//#CM567507
			// Packed qty per case
			//#CM567508
			// Decided whether there is data in the stock table. 
			String invcase = lst_InventoryMaintenance.getValue(INVENTORYCHECKCASEQTY);
			String stockcase = lst_InventoryMaintenance.getValue(STOCKCASEQTY);
			String stockpiece = lst_InventoryMaintenance.getValue(STOCKPIECEQTY);
			String enteringQty = lst_InventoryMaintenance.getValue(ENTERINGQTY);
			
			long stkQty = WmsFormatter.getLong(stockcase) * WmsFormatter.getLong(enteringQty) + WmsFormatter.getLong(stockpiece);
			
			if(stkQty > WmsParam.MAX_STOCK_QTY)
			{
				//#CM567509
				// 6023348=Cannot enter.  Stock Qty. exceeds {0}.
				message.setMsgResourceKey("6023348"	+ wDelim + WmsFormatter.getNumFormat(WmsParam.MAX_STOCK_QTY));
				return;
			}
			
			txt_ItemCode.setReadOnly(false);
			txt_ItemName.setReadOnly(false);
			txt_CaseEntering.setReadOnly(false);
			txt_BundleEntering.setReadOnly(false);
			txt_Location.setReadOnly(false);
			txt_UseByDate.setReadOnly(false);
			btn_PSearch.setEnabled(true);
			
			//#CM567510
			// When the stock exists, it is not possible to change excluding Inventory Case qty and Inventory Piece qty. 
			if (CollectionUtils.getString(0, lst_InventoryMaintenance.getValue(HIDDEN)).equals(StorageSupportParameter.INVENTORY_KIND_FIND) 
				||(WmsFormatter.getInt(stockcase) != 0 || WmsFormatter.getInt(stockpiece) != 0))
			{
				txt_ItemCode.setReadOnly(true);
				txt_ItemName.setReadOnly(true);
				txt_CaseEntering.setReadOnly(true);
				txt_BundleEntering.setReadOnly(true);
				txt_Location.setReadOnly(true);
				txt_UseByDate.setReadOnly(true);
				btn_PSearch.setEnabled(false);
				
				if (!StringUtil.isBlank(stockcase))
				{
					txt_CaseEntering.setReadOnly(true);
				}
				
				if (!StringUtil.isBlank(stockpiece))
				{
					txt_BundleEntering.setReadOnly(true);
				}
				
				//#CM567511
				// Set the cursor in Inventory Case qty. 
				setFocus(txt_InventoryCaseQty);
			}
			else if (!StringUtil.isBlank(invcase) && StringUtil.isBlank(stockcase))
			{
				//#CM567512
				// Set the cursor in Item Code. 
				setFocus(txt_ItemCode);
			}
			//#CM567513
			// Item Code
			txt_ItemCode.setText(lst_InventoryMaintenance.getValue(ITEMCODE));
			//#CM567514
			// Item Name
			txt_ItemName.setText(lst_InventoryMaintenance.getValue(ITEMNAME));
			//#CM567515
			// Packed qty per case
			txt_CaseEntering.setText(lst_InventoryMaintenance.getValue(ENTERINGQTY));
			//#CM567516
			// Packed qty per bundle
			txt_BundleEntering.setText(lst_InventoryMaintenance.getValue(BUNDLEENTERINGQTY));
			//#CM567517
			// Set the value of Stock Case qty and Stock Piece qty when there are no values in Inventory Case qty and Inventory Piece qty. 
			if (StringUtil.isBlank(invcase) && !StringUtil.isBlank(stockcase))
			{
				//#CM567518
				// Inventory Case qty
				txt_InventoryCaseQty.setText(lst_InventoryMaintenance.getValue(STOCKCASEQTY));
				//#CM567519
				// Inventory Piece qty
				txt_InventoryPieceQty.setText(lst_InventoryMaintenance.getValue(STOCKPIECEQTY));
			}
			else
			{
				//#CM567520
				// Inventory Case qty
				txt_InventoryCaseQty.setText(
					lst_InventoryMaintenance.getValue(INVENTORYCHECKCASEQTY));
				//#CM567521
				// Inventory Piece qty
				txt_InventoryPieceQty.setText(
					lst_InventoryMaintenance.getValue(INVENTORYCHECKPIECEQTY));
			}
			//#CM567522
			// Location
			txt_Location.setText(lst_InventoryMaintenance.getValue(LOCATION));
			//#CM567523
			// Expiry Date
			txt_UseByDate.setText(lst_InventoryMaintenance.getValue(USEBYDATE));

			//#CM567524
			// Preserved in ViewState. 
			//#CM567525
			// To decide whether Status is Modify when the Modify button is pressed, Preset Line No is set in ViewState.
			this.getViewState().setInt(LINENO_KEY, lst_InventoryMaintenance.getActiveRow());
			//#CM567526
			// Location(Concealed Item)
			this.getViewState().setString(LOCATION_KEY,
				CollectionUtils.getString(2, lst_InventoryMaintenance.getValue(HIDDEN)));
			//#CM567527
			// Item Code(Concealed Item)
			this.getViewState().setString(ITEMCODE_KEY,
				CollectionUtils.getString(3, lst_InventoryMaintenance.getValue(HIDDEN)));
			//#CM567528
			// Item Name(Concealed Item)
			this.getViewState().setString(ITEMNAME_KEY,
				CollectionUtils.getString(4, lst_InventoryMaintenance.getValue(HIDDEN)));
			//#CM567529
			// Packed qty per case(Concealed Item)
			this.getViewState().setString(ENTERINGQTY_KEY,
				CollectionUtils.getString(5, lst_InventoryMaintenance.getValue(HIDDEN)));
			//#CM567530
			// Packed qty per bundle(Concealed Item)
			this.getViewState().setString(BUNDLEENTERINGQTY_KEY,
				CollectionUtils.getString(6, lst_InventoryMaintenance.getValue(HIDDEN)));
			//#CM567531
			// Inventory Case qty(Concealed Item)
			this.getViewState().setString(INVENTORYCHECKCASEQTY_KEY,
				CollectionUtils.getString(7, lst_InventoryMaintenance.getValue(HIDDEN)));
			//#CM567532
			// Inventory Piece qty(Concealed Item)
			this.getViewState().setString(INVENTORYCHECKPIECEQTY_KEY,
				CollectionUtils.getString(8, lst_InventoryMaintenance.getValue(HIDDEN)));
			//#CM567533
			// Expiry Date(Concealed Item)
			this.getViewState().setString(USEBYDATE_KEY,
				CollectionUtils.getString(9, lst_InventoryMaintenance.getValue(HIDDEN)));
			//#CM567534
			// Last updated date and time(Concealed Item)
			this.getViewState().setString(LASTUPDATE_KEY,
				CollectionUtils.getString(10, lst_InventoryMaintenance.getValue(HIDDEN)));
			//#CM567535
			// Work No.(Concealed Item)
			this.getViewState().setString(JOBNO_KEY,
				CollectionUtils.getString(11, lst_InventoryMaintenance.getValue(HIDDEN)));
			//#CM567536
			// Stock ID(Concealed Item)
			this.getViewState().setString(STOCKID_KEY,
				CollectionUtils.getString(12, lst_InventoryMaintenance.getValue(HIDDEN)));
			//#CM567537
			// Change the modification line to yellow. 
			lst_InventoryMaintenance.setHighlight(lst_InventoryMaintenance.getActiveRow());
		}
	}

}
//#CM567538
//end of class
