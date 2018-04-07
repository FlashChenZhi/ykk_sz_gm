// $Id: ListStockInventoryCheckBusiness.java,v 1.2 2006/10/04 05:11:10 suresh Exp $

//#CM5153
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockinventorycheck;
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocation.ListStockLocationBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret.SessionInventoryListRet;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM5154
/**
 * Designer : A.Nagasawa <BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * This is Inventory Check list form print (display) listbox class.
 * Search for the data using Consignor code<BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * 1.Initial screen(<CODE>page_Load(ActionEvent e)</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *  Search for the data using Consignor code<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>A.Nagasawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:11:10 $
 * @author  $Author: suresh $
 */
public class ListStockInventoryCheckBusiness
	extends ListStockInventoryCheck
	implements WMSConstants
{
	//#CM5155
	// Class fields --------------------------------------------------
	//#CM5156
	/** 
	 * Use this key to pass the print condition (quantity print).
	 */
	public static final String PRINT_CHECK = "PRINT_CHECK";

	//#CM5157
	/** 
	 * Use this key to pass the print condition (the same location item aggregation).
	 */
	public static final String PRINTINGCONDITION_INTENSIVEPRINTING =
		"PRINTINGCONDITION_INTENSIVEPRINTING";

	//#CM5158
	// Class variables -----------------------------------------------

	//#CM5159
	// Class method --------------------------------------------------

	//#CM5160
	// Constructors --------------------------------------------------

	//#CM5161
	// Public methods ------------------------------------------------
	//#CM5162
	/**
	 * Initialize the screen <BR>
	 * <DIR>
	 * Item <BR>
	 *	<DIR>
	 *	  Location<BR>
	 *	  Item code<BR>
	 *    Division<BR>
	 *    Packing qty per Case<BR>
	 *    Stock Case qty<BR>
	 *    Case ITF<BR>
	 *    Expiry Date<BR>
	 *    Item name<BR>
	 *    Packing qty per Bundle<BR>
	 *    Stock Piece qty<BR>
	 *    Bundle ITF<BR>
	 *	</DIR>
	 *</DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM5163
		// Set the screen name.
		//#CM5164
		// Inventory Check list
		lbl_ListName.setText(DisplayText.getText("TLE-W0085"));

		//#CM5165
		// Obtain parameter
		//#CM5166
		// Consignor code
		String consignorcode = request.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM5167
		// Start location
		String startlocation = request.getParameter(ListStockLocationBusiness.STARTLOCATION_KEY);
		//#CM5168
		// End location
		String endlocation = request.getParameter(ListStockLocationBusiness.ENDLOCATION_KEY);
		//#CM5169
		// Item code
		String itemcode = request.getParameter(ListStockItemBusiness.ITEMCODE_KEY);
		//#CM5170
		// Printing conditions(Aggregating conditions)
		String printingcondition = request.getParameter(PRINTINGCONDITION_INTENSIVEPRINTING);
		//#CM5171
		// Printing conditions(Qty printing )
		String printcheck = request.getParameter(PRINT_CHECK);

		viewState.setString(PRINT_CHECK, printcheck);
		viewState.setString(PRINTINGCONDITION_INTENSIVEPRINTING, printingcondition);
		
		
		//#CM5172
		// Check the Consignor code for mandatory input and forbidden character.
		if (!WmsCheckker.consignorCheck(consignorcode, lst_InventoryList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM5173
		// Check the Start location for forbidden character.
		if (!WmsCheckker.charCheck(startlocation, lst_InventoryList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM5174
		// Check the End location for forbidden character.
		if (!WmsCheckker.charCheck(endlocation, lst_InventoryList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM5175
		// Location area check
		if (!WmsCheckker
			.rangeLocationCheck(
				startlocation,
				endlocation,
				lst_InventoryList,
				pgr_U,
				pgr_D,
				lbl_InMsg))
		{
			return;
		}

		//#CM5176
		// Check the Item code for forbidden character.
		if (!WmsCheckker.charCheck(itemcode, lst_InventoryList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM5177
		// Show search conditions
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		lbl_JavaSetGroupFlag.setText(DisplayUtil.getPrintCondition(printingcondition));

		//#CM5178
		// Close the connection of the object remained at the Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM5179
			// Close the connection
			sRet.closeConnection();
			//#CM5180
			// Delete from the Session
			this.getSession().removeAttribute("LISTBOX");
		}
		//#CM5181
		// Obtain the connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM5182
		// Set the parameter.
		StockControlParameter stockparam = new StockControlParameter();
		//#CM5183
		// Consignor code
		stockparam.setConsignorCode(consignorcode);
		//#CM5184
		// Start location
		stockparam.setFromLocationNo(startlocation);
		//#CM5185
		// End location
		stockparam.setToLocationNo(endlocation);
		//#CM5186
		// Item code
		stockparam.setItemCode(itemcode);
		//#CM5187
		// Aggregating conditions
		stockparam.setPrintCondition1(printingcondition);
		//#CM5188
		// Generate SessionInventoryList instance.
		SessionInventoryListRet listbox = new SessionInventoryListRet(conn, stockparam);
		//#CM5189
		// Maintain the listbox in the Session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM5190
	// Package methods -----------------------------------------------

	//#CM5191
	// Protected methods ---------------------------------------------

	//#CM5192
	// Private methods -----------------------------------------------
	//#CM5193
	/**
	 * Method to change the page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Reports all the exceptions.
	 */
	private void setList(SessionInventoryListRet listbox, String actionName) throws Exception
	{
		
		//#CM5194
		// Set the Page info.
		listbox.setActionName(actionName);

		//#CM5195
		// Obtain the search result if aggregation is enabled.
		StockControlParameter[] stockparam = listbox.getEntities();
		//#CM5196
		// for storing objects of search results in the case where items aggregated in the same location are found
		StockControlParameter[] result = null;

		int len = 0;
		if (stockparam != null)
		{
			//#CM5197
			// If aggregation is enabled
			if(viewState.getString(PRINTINGCONDITION_INTENSIVEPRINTING).
				equals(StockControlParameter.PRINTINGCONDITION_INTENSIVEPRINTING_ON))
			{
				result =  getValues(stockparam) ;
			}
				len = stockparam.length;
		}
		
		if (len > 0)
		{
			//#CM5198
			// Set the Consignor name for the search conditions.
			lbl_JavaSetCnsgnrNm.setText(stockparam[0].getConsignorName());

			//#CM5199
			// Set the value for the pager.
			//#CM5200
			// Maximum Count
			pgr_U.setMax(listbox.getLength());
			//#CM5201
			// Display Counts per 1 page
			pgr_U.setPage(listbox.getCondition());
			//#CM5202
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM5203
			// Maximum Count
			pgr_D.setMax(listbox.getLength());
			//#CM5204
			// Display Counts per 1 page
			pgr_D.setPage(listbox.getCondition());
			//#CM5205
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM5206
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM5207
			// Delete all lines.
			lst_InventoryList.clearRow();
			
			//#CM5208
			// Item name
			String label_itemname = DisplayText.getText("LBL-W0103");
			
			//#CM5209
			// If aggregation is enabled
			if(viewState.getString(PRINTINGCONDITION_INTENSIVEPRINTING).
				equals(StockControlParameter.PRINTINGCONDITION_INTENSIVEPRINTING_ON))
			{
				String casePieceFlagName = "";
				for (int i = 0; i < len; i++)
				{
					//#CM5210
					// Obtain the tailing line.
					int count = lst_InventoryList.getMaxRows();
					//#CM5211
					// Add line
					lst_InventoryList.addRow();
	
					//#CM5212
					// Move to the end line.
					lst_InventoryList.setCurrentRow(count);
					lst_InventoryList.setValue(0, result[i].getCasePieceFlag());
					lst_InventoryList.setValue(1, result[i].getLocationNo());
					lst_InventoryList.setValue(2, result[i].getItemCode());
					if(StringUtil.isBlank(result[i].getCasePieceFlag()))
					{
						//#CM5213
						// Negative "- " if no needed to display the Case/Piece division name.
						casePieceFlagName = DisplayText.getText("LBL-W0078");						
					}
					else
					{
						casePieceFlagName = result[i].getCasePieceFlagName();
					}
					lst_InventoryList.setValue(3, casePieceFlagName);
					lst_InventoryList.setValue(
						4,
						WmsFormatter.getNumFormat(result[i].getEnteringQty()));
					//#CM5214
					// Display the stock case qty only in the mode Print qty enabled.
					if (viewState.getString(PRINT_CHECK)
						.equals(StockControlParameter.PRINTINGCONDITION_QTYPRINTING_ON))
					{
						lst_InventoryList.setValue(5,
							WmsFormatter.getNumFormat(result[i].getTotalStockCaseQty()));
					}
					lst_InventoryList.setValue(6, result[i].getITF());
					lst_InventoryList.setValue(7, result[i].getUseByDate());
					lst_InventoryList.setValue(8, result[i].getItemName());
					lst_InventoryList.setValue(
						9,
						WmsFormatter.getNumFormat(result[i].getBundleEnteringQty()));
					//#CM5215
					// Display Stock Piece only when there is Qty printing 
					if (viewState.getString(PRINT_CHECK)
						.equals(StockControlParameter.PRINTINGCONDITION_QTYPRINTING_ON))
					{
						lst_InventoryList.setValue(10,
							WmsFormatter.getNumFormat(result[i].getTotalStockPieceQty()));
					}
					lst_InventoryList.setValue(11, result[i].getBundleITF());
	
					//#CM5216
					// Compile the data for ToolTip
					ToolTipHelper toolTip = new ToolTipHelper();
					//#CM5217
					// Item name
					toolTip.add(label_itemname, result[i].getItemName());
	
					//#CM5218
					// Set ToolTip to current line
					lst_InventoryList.setToolTip(lst_InventoryList.getCurrentRow(), toolTip.getText());
				}
			}
			else
			{
				for (int i = 0; i < len; i++)
				{
					//#CM5219
					// Obtain the tailing line.
					int count = lst_InventoryList.getMaxRows();
					//#CM5220
					// Add line
					lst_InventoryList.addRow();
	
					//#CM5221
					// Move to the end line.
					lst_InventoryList.setCurrentRow(count);
					lst_InventoryList.setValue(0, stockparam[i].getCasePieceFlag());
					lst_InventoryList.setValue(1, stockparam[i].getLocationNo());
					lst_InventoryList.setValue(2, stockparam[i].getItemCode());
					lst_InventoryList.setValue(3, stockparam[i].getCasePieceFlagName());
					lst_InventoryList.setValue(
						4,
						WmsFormatter.getNumFormat(stockparam[i].getEnteringQty()));
					//#CM5222
					// Display the stock case qty only in the mode Print qty enabled.
					if (viewState.getString(PRINT_CHECK)
						.equals(StockControlParameter.PRINTINGCONDITION_QTYPRINTING_ON))
					{
						lst_InventoryList.setValue(5,
							WmsFormatter.getNumFormat(stockparam[i].getStockCaseQty()));
					}
					lst_InventoryList.setValue(6, stockparam[i].getITF());
					lst_InventoryList.setValue(7, stockparam[i].getUseByDate());
					lst_InventoryList.setValue(8, stockparam[i].getItemName());
					lst_InventoryList.setValue(
						9,
						WmsFormatter.getNumFormat(stockparam[i].getBundleEnteringQty()));
					//#CM5223
					// Display Stock Piece only when there is Qty printing
					if (viewState.getString(PRINT_CHECK)
						.equals(StockControlParameter.PRINTINGCONDITION_QTYPRINTING_ON))
					{
						lst_InventoryList.setValue(10,
							WmsFormatter.getNumFormat(stockparam[i].getStockPieceQty()));
					}
					lst_InventoryList.setValue(11, stockparam[i].getBundleITF());
	
					//#CM5224
					// Compile the data for ToolTip
					ToolTipHelper toolTip = new ToolTipHelper();
					//#CM5225
					// Item name
					toolTip.add(label_itemname, stockparam[i].getItemName());
	
					//#CM5226
					// Set ToolTip to current line
					lst_InventoryList.setToolTip(lst_InventoryList.getCurrentRow(), toolTip.getText());
				}
			}
		}
		else
		{
			//#CM5227
			// Set the value for the Pager.
			//#CM5228
			// Maximum Count
			pgr_U.setMax(0);
			//#CM5229
			// Display Counts per 1 page
			pgr_U.setPage(0);
			//#CM5230
			// Start Position
			pgr_U.setIndex(0);
			//#CM5231
			// Maximum Count
			pgr_D.setMax(0);
			//#CM5232
			// Display Counts per 1 page
			pgr_D.setPage(0);
			//#CM5233
			// Start Position
			pgr_D.setIndex(0);

			//#CM5234
			// Execute search result count check
			String errorMsg = listbox.checkLength();
			//#CM5235
			// Hide the header.
			lst_InventoryList.setVisible(false);
			//#CM5236
			// Error message display
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM5237
	// Event handler methods -----------------------------------------
	//#CM5238
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5239
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5240
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5241
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5242
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5243
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5244
	/** 
	 * Clicking Close button executes its process. <BR>
	 *  <BR>
	 * Close listbox, and<BR>
	 *  <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		btn_Close_D_Click(e);
	}

	//#CM5245
	/** 
	 * Execute processing when ">" button is pressed down. <BR>
	 * <BR>
	 * Display the next page. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	//#CM5246
	/** 
	 * Clicking on "< " button executes its process. <BR>
	 * <BR>
	 * Show prior one page<BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
		pgr_D_Prev(e);
	}

	//#CM5247
	/** 
	 * Clicking ">> " button executes its process. <BR>
	 * <BR>
	 * Display the final page. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		pgr_D_Last(e);
	}

	//#CM5248
	/** 
	 * Execute processing when "<<" button is pressed down. <BR>
	 * <BR>
	 * Display the top page. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		pgr_D_First(e);
	}

	//#CM5249
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5250
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InventoryList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM5251
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InventoryList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM5252
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InventoryList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM5253
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InventoryList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM5254
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InventoryList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5255
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InventoryList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM5256
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InventoryList_Click(ActionEvent e) throws Exception
	{
	}

	//#CM5257
	/** 
	 * Execute processing when ">" button is pressed down. <BR>
	 * <BR>
	 * Display the next page. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		//#CM5258
		// Maintain the listbox in the Session
		SessionInventoryListRet listbox =
			(SessionInventoryListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM5259
	/** 
	 * Clicking on "< " button executes its process. <BR>
	 * <BR>
	 * Show prior one page<BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		//#CM5260
		// Maintain the listbox in the Session
		SessionInventoryListRet listbox =
			(SessionInventoryListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}
	//#CM5261
	/** 
	 * Clicking ">> " button executes its process. <BR>
	 * <BR>
	 * Display the final page. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		//#CM5262
		// Maintain the listbox in the Session
		SessionInventoryListRet listbox =
			(SessionInventoryListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM5263
	/** 
	 * Execute processing when "<<" button is pressed down. <BR>
	 * <BR>
	 * Display the top page. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM5264
		// Maintain the listbox in the Session
		SessionInventoryListRet listbox =
			(SessionInventoryListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM5265
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5266
	/** 
	 * Clicking Close button executes its process. <BR>
	 *  <BR>
	 * Close listbox, and<BR>
	 *  <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM5267
		// Store listbox to the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM5268
		// When value exists in the Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM5269
				// Close the statement
				finder.close();
			}
			//#CM5270
			// Close the connection
			sessionret.closeConnection();
		}
		//#CM5271
		// Delete from the Session
		this.getSession().removeAttribute("LISTBOX");
		//#CM5272
		// Return to the parent screen.
		parentRedirect(null);
	}
	
	//#CM5273
	//Private Methods
	//#CM5274
	/**
	 * This method obtains the aggregated record info<BR>
	 * <BR>
	 * Summary:In the case such locations where items are aggregated in the same location are found, search operation is made again to obtain the latest item name, packing qty and ITF based on the aggregated result of Location, item and expiry date and the quantity obtained is merged in accordance with Case/Piece division.<BR>
	 * <BR>
	 * <BR>
	 * aggregation result is translated into <CODE>StockControlParameter</CODE>.<BR>
	 * 
	 * @param param Parameter object in which search value is set.
	 * @return StockControlParameter[] Parameter object in which re-search value is set.
	 * @throws Exception Reports all the exceptions.
	 */
	private StockControlParameter[] getValues(StockControlParameter[] param) throws Exception 
	{
		StockControlParameter[] resultParam = null ;
	    
		String consignorCode = "" ;
		String locationNo = "" ;
		String itemCode = "" ;
		long stockQty = 0;
		String useByDate = "" ;
		
		//#CM5275
		// Next record flag(When next record is found, true)
		boolean isNext = false ;
	    
		Vector vector = new Vector() ;
	    
	    //#CM5276
	    // aggregation record for re-search
	    consignorCode = param[0].getConsignorCode();
		itemCode = param[0].getItemCode();
	    
	    //#CM5277
	    // Set search key
		StockSearchKey sKey = new StockSearchKey() ;
		//#CM5278
		// Consignor code
		sKey.setConsignorCode(consignorCode);
		//#CM5279
		// Stock status(Center Inventory)
		sKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);

		//#CM5280
		// Stock qty shall be 1 or more
		sKey.setStockQty(0, ">");


		//#CM5281
		// Set the search conditions (array).
		for(int i=0; i < param.length ; i++)
		{
			//#CM5282
			// When print SQL initially (Two  "(")
			if (i == 0)
			{
				//#CM5283
				// Location
				sKey.setLocationNo(param[i].getLocationNo(),"=","((","","AND") ;
			}
			else
			{
				//#CM5284
				// Location
				sKey.setLocationNo(param[i].getLocationNo(),"=","(","","AND") ;
			}
			//#CM5285
			// When print SQL normally
			//#CM5286
			// If the item code is entered
			if (StringUtil.isBlank(param[i].getItemCode()))
			{
				sKey.setItemCode(param[i].getItemCode(),"","","","AND" ) ;
			}
			else
			{
				sKey.setItemCode(param[i].getItemCode(),"=","","","AND" ) ;
			}
			//#CM5287
			// When SQL completed (Two  "(")
			if (i == param.length -1 )
			{
				//#CM5288
				// Set the If expiry date is entered
				if (StringUtil.isBlank(param[i].getUseByDate()))
				{
					sKey.setUseByDate(param[i].getUseByDate(),"","","))","OR" ) ;
				}
				else
				{
					sKey.setUseByDate(param[i].getUseByDate(),"=","","))","OR" ) ;
				}
			}
			else
			{
				//#CM5289
				// Set the If expiry date is entered
				if (StringUtil.isBlank(param[i].getUseByDate()))
				{
					sKey.setUseByDate(param[i].getUseByDate(),"","",")","OR" ) ;
				}
				else
				{
					sKey.setUseByDate(param[i].getUseByDate(),"=","",")","OR" ) ;
				}
			}
		}
		
		//#CM5290
		// Set COLLECT
		sKey.setLocationNoCollect("") ;
		sKey.setItemCodeCollect("") ;
		sKey.setItemName1Collect("");
		sKey.setCasePieceFlagCollect("") ;
		sKey.setEnteringQtyCollect("") ;
		sKey.setBundleEnteringQtyCollect("") ;
		sKey.setStockQtyCollect("") ;
		sKey.setUseByDateCollect("");
		sKey.setLastUpdateDateCollect("") ;
	    
		//#CM5291
		// Set ORDER BY
		sKey.setLocationNoOrder(1,true) ;
		sKey.setItemCodeOrder(2,true) ;
		sKey.setUseByDateOrder(3,true) ;
		sKey.setLastUpdateDateOrder(4,false) ;
		
		//#CM5292
		// Obtain the connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	    
		StockHandler sHandler = new StockHandler(conn) ;
		
		Stock[] stock = (Stock[])sHandler.find(sKey) ;

		StockControlParameter tempParam = new StockControlParameter() ;

		for(int i=0; i < stock.length; i++)
		{
			//#CM5293
			//	Next record flag:true
			isNext = true ;
			//#CM5294
			// Set for the variable for swapping the initial value
			if(i==0)
			{
				itemCode = stock[i].getItemCode() ;
				locationNo = stock[i].getLocationNo() ;
				useByDate = stock[i].getUseByDate() ;
			}
		    
			//#CM5295
			// Aggragate if Location, Item, Expiry Date are same.
			if(locationNo.equals(stock[i].getLocationNo()) 
				&& itemCode.equals(stock[i].getItemCode())
					&& useByDate.equals(stock[i].getUseByDate()))
			{
				//#CM5296
				// Stock qty accumlation
				stockQty = (long) stockQty + (long) stock[i].getStockQty();
			}		    
			else
			{
				//#CM5297
				// If it breaks search again and set aggregated record to the parameter
				//#CM5298
				// and move to the next record swapping current record

				tempParam = new StockControlParameter() ;
				
				//#CM5299
				// Obtain the latest packed qty per case, the packed qty per bundle, Case ITF, bundle ITF, and item name.
				getRecentParams(conn, consignorCode, locationNo, itemCode, useByDate, tempParam);
			    
				//#CM5300
				// Set the value for the parameter.
				tempParam.setLocationNo(locationNo) ;
				tempParam.setItemCode(itemCode) ;
				tempParam.setUseByDate(useByDate) ;
				//#CM5301
				// Stock Case Qty
				tempParam.setTotalStockCaseQty(
					DisplayUtil.getCaseQty(stockQty, tempParam.getEnteringQty()));
				//#CM5302
				// Stock piece qty
				tempParam.setTotalStockPieceQty(
					DisplayUtil.getPieceQty(stockQty, tempParam.getEnteringQty()));

				//#CM5303
				// Swap the record after Breaking
				locationNo = stock[i].getLocationNo() ;
				itemCode = stock[i].getItemCode() ;
				useByDate = stock[i].getUseByDate();
				stockQty = 0;
		        
				//#CM5304
				// Return the cursor 1 step.
				i = i - 1 ;
		        
				vector.add(tempParam) ;
			}
			//#CM5305
			// Make the next record flag false when the cursor becomes the last cursor.
			if(i == (stock.length-1))
				isNext = false ;
		    
			//#CM5306
			// Add the last object to the Vector.
			if(!isNext)
			{
				//#CM5307
				// If it breaks search again and set aggregated record to the parameter
				//#CM5308
				// and move to the next record swapping current record
				tempParam = new StockControlParameter() ;

				//#CM5309
				// Obtain the latest packed qty per case, the packed qty per bundle, Case ITF, bundle ITF, and item name.
				getRecentParams(conn, consignorCode, locationNo, itemCode, useByDate, tempParam);
			    
				//#CM5310
				// Set the value for the parameter.
				tempParam.setLocationNo(locationNo) ;
				tempParam.setItemCode(itemCode) ;
				tempParam.setUseByDate(useByDate) ;
				tempParam.setTotalStockCaseQty(
					DisplayUtil.getCaseQty(stockQty, tempParam.getEnteringQty()));
				tempParam.setTotalStockPieceQty(
					DisplayUtil.getPieceQty(stockQty, tempParam.getEnteringQty()));
			    
				vector.add(tempParam) ;
			}
		}
		conn.close() ;
		
		resultParam = new StockControlParameter[vector.size()] ;
		vector.copyInto(resultParam) ;
		
		return resultParam ;
	}
	
	//#CM5311
	/** 
	 * Obtain the latest packed qty per case, the packed qty per bundle, Case ITF, bundle ITF, and item name based on the search conditions.<BR>
	 *  <BR>
	 * Summary:Obtain the latest packed qty per case, the packed qty per bundle, Case ITF, bundle ITF, and item name based on the search conditions.<BR>
	 * Store the result to the parameter.<BR>
	 *  <BR>
	 * @param conn Instance to maintain database connection.
	 * @param consignorCode Try again to Search:Consignor code
	 * @param locationNo Try again to Search:Location
	 * @param itemCode Try again to Search:Item code
	 * @param useByDate Try again to Search:Expiry Date
	 * @param param search Parameter class to maintain result
	 * @throws Exception Reports all the exceptions.
	 */
	private void getRecentParams(Connection conn, String consignorCode, String locationNo, 
		String itemCode, String useByDate, StockControlParameter param) throws Exception
	{
		
		//#CM5312
		// Obtain the latest packed qty per case, the packed qty per bundle, Case ITF, bundle ITF, and item name.
		//#CM5313
		// Set search key
		StockSearchKey recentKey = new StockSearchKey() ;
		//#CM5314
		// Consignor code
		recentKey.setConsignorCode(consignorCode);
		//#CM5315
		// Location
		recentKey.setLocationNo(locationNo) ;
		//#CM5316
		// Item code
		recentKey.setItemCode(itemCode) ;
		//#CM5317
		// Expiry Date
		recentKey.setUseByDate(useByDate) ;
				
		//#CM5318
		// Obtain the packed qty per case.
		recentKey.setEnteringQtyCollect("");
		//#CM5319
		// Obtain packed qty per bundle
		recentKey.setBundleEnteringQtyCollect("");
		//#CM5320
		// Obtain the Case ITF.
		recentKey.setItfCollect("");
		//#CM5321
		// Obtain Bundle ITF
		recentKey.setBundleItfCollect("");
		//#CM5322
		// Obtain Item name
		recentKey.setItemName1Collect("");
		//#CM5323
		// Obtain the last update date.
		recentKey.setLastUpdateDateCollect("");

		//#CM5324
		// Descending order of the last update dates
		recentKey.setLastUpdateDateOrder(1, false);
	
		StockFinder recentFinder = new StockFinder(conn);
		recentFinder.open();
		int nameCount = ((StockFinder) recentFinder).search(recentKey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			Stock recentstock[] = (Stock[]) ((StockFinder) recentFinder).getEntities(0, 1);
	
			if (recentstock != null && recentstock.length != 0)
			{
				//#CM5325
				// Set the value for the parameter.
				param.setEnteringQty(recentstock[0].getEnteringQty()) ;
				param.setBundleEnteringQty(recentstock[0].getBundleEnteringQty()) ;
				param.setITF(recentstock[0].getItf()) ;
				param.setBundleITF(recentstock[0].getBundleItf()) ;
				param.setItemName(recentstock[0].getItemName1()) ;
			}
		}
		recentFinder.close();
		
	}	
	
	//#CM5326
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_GroupFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5327
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetGroupFlag_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM5328
//end of class
