// $Id: ListStockDeadStockBusiness.java,v 1.2 2006/10/04 05:11:02 suresh Exp $

//#CM5045
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockdeadstock;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret.SessionDeadStockListRet;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM5046
/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * This is old inventory list print ( display) class.<BR>
 * Search for the data using Consignor code and Storage date entered via parent screen as a key. <BR>
 * <BR>
 * 1.Initial screen(<CODE>page_Load(ActionEvent e)</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *  Search for the data using Consignor code and Storage date entered via parent screen as a key<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/07</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:11:02 $
 * @author  $Author: suresh $
 */
public class ListStockDeadStockBusiness extends ListStockDeadStock implements WMSConstants
{
	//#CM5047
	// Class fields --------------------------------------------------
	//#CM5048
	/** 
	 * Use this key to pass the storage date.
	 */
	public static final String STORAGEDATE_KEY = "STORAGEDATE_KEY";

	//#CM5049
	// Class variables -----------------------------------------------

	//#CM5050
	// Class method --------------------------------------------------

	//#CM5051
	// Constructors --------------------------------------------------

	//#CM5052
	// Public methods ------------------------------------------------
	
	//#CM5053
	/**
	 * Initialize the screen <BR>
	 * <DIR>
	 * Item <BR>
	 *	<DIR>
	 *	  Storage Date<BR>
	 *	  Item code<BR>
	 *    Division<BR>
	 *    Location<BR>
	 *    Packing qty per Case<BR>
	 *    Stock Case qty<BR>
	 *    Case ITF<BR>
	 *    Expiry Date<BR>
	 *    Storage Time<BR>
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
		//#CM5054
		// Set the screen name.
		//#CM5055
		// old inventory list
		lbl_ListName.setText(DisplayText.getText("TLE-W0084"));

		//#CM5056
		// Obtain parameter
		//#CM5057
		// Consignor code
		String consignorcode = request.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM5058
		// Storage Date
		String storagedate = request.getParameter(STORAGEDATE_KEY);
		
		//#CM5059
		// Check the Consignor code for mandatory input and forbidden character.
		if (!WmsCheckker.consignorCheck(consignorcode, lst_DeadStockList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM5060
		// Check the Storage date for mandatory input and forbidden character.
		if (!WmsCheckker.storagedateCheck(storagedate, lst_DeadStockList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}		
		
		//#CM5061
		// Show search conditions
		lbl_JavaSetCnsgnrCd.setText(consignorcode);

		//#CM5062
		// Close the connection of the object remained at the Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM5063
			// Close the connection
			sRet.closeConnection();
			//#CM5064
			// Delete from the Session
			this.getSession().removeAttribute("LISTBOX");
		}
		//#CM5065
		// Obtain the connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);
		//#CM5066
		// Set the parameter.
		StockControlParameter stockparam = new StockControlParameter();	
		//#CM5067
		// Consignor code
		stockparam.setConsignorCode(consignorcode);
		//#CM5068
		// Storage Date
		
		stockparam.setStorageDate(WmsFormatter.toDate(storagedate));

		//#CM5069
		// Generate SessionDeadStockList instance.
		SessionDeadStockListRet listbox = new SessionDeadStockListRet(conn, stockparam);
		//#CM5070
		// Maintain the listbox in the Session
		this.getSession().setAttribute("LISTBOX", listbox);
		
		setList(listbox, "first");
	}
	
	//#CM5071
	// Package methods -----------------------------------------------

	//#CM5072
	// Protected methods ---------------------------------------------

	//#CM5073
	// Private methods -----------------------------------------------
	//#CM5074
	/**
	 * Method to change the page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Reports all the exceptions.
	 */
	private void setList(SessionDeadStockListRet listbox, String actionName) throws Exception
	{
		//#CM5075
		// Obtain the locale.
		Locale locale = this.getHttpRequest().getLocale();
		//#CM5076
		// Set the Page info.
		listbox.setActionName(actionName);

		//#CM5077
		// Obtain search result
		StockControlParameter[] stockparam = (StockControlParameter[])listbox.getEntities();

		int len = 0;
		if (stockparam != null)
		{
			len = stockparam.length;
		}
		if (len > 0)
		{
			//#CM5078
			// Set the Consignor name for the search conditions.
			lbl_JavaSetCnsgnrNm.setText(stockparam[0].getConsignorName());

			//#CM5079
			// Set the value for the pager.
			//#CM5080
			// Maximum Count
			pgr_U.setMax(listbox.getLength());
			//#CM5081
			// Display Counts per 1 page
			pgr_U.setPage(listbox.getCondition());
			//#CM5082
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM5083
			// Maximum Count
			pgr_D.setMax(listbox.getLength());
			//#CM5084
			// Display Counts per 1 page
			pgr_D.setPage(listbox.getCondition());
			//#CM5085
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM5086
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM5087
			// Delete all lines.
			lst_DeadStockList.clearRow();

			//#CM5088
			// Item name
			String label_itemname = DisplayText.getText("LBL-W0103");
			//#CM5089
			// Case ITF
			String label_caseitf = DisplayText.getText("LBL-W0010");
			//#CM5090
			// Bundle ITF
			String label_bundleitf = DisplayText.getText("LBL-W0006");
			//#CM5091
			// Expiry Date
			String label_usebydate = DisplayText.getText("LBL-W0270");

			String title_AreaTypeName = DisplayText.getText("LBL-W0569");
			
			for (int i = 0; i < len; i++)
			{
				//#CM5092
				// Obtain the tailing line.
				int count = lst_DeadStockList.getMaxRows();
				//#CM5093
				// Add line
				lst_DeadStockList.addRow();

				//#CM5094
				// Move to the end line.
				lst_DeadStockList.setCurrentRow(count);
				lst_DeadStockList.setValue(0, stockparam[i].getCasePieceFlag());
				lst_DeadStockList.setValue(1, WmsFormatter.toDispDate(WmsFormatter.toParamDate(stockparam[i].getStorageDate()),locale));
				lst_DeadStockList.setValue(2, stockparam[i].getItemCode());
				lst_DeadStockList.setValue(3, stockparam[i].getCasePieceFlagName());
				lst_DeadStockList.setValue(4, WmsFormatter.toDispLocation(
				        stockparam[i].getLocationNo()
				        , stockparam[i].getAreaNo(), stockparam[i].getAreaNoArray()));
				lst_DeadStockList.setValue(5, WmsFormatter.getNumFormat(stockparam[i].getEnteringQty()));
				lst_DeadStockList.setValue(6, WmsFormatter.getNumFormat(stockparam[i].getStockCaseQty()));
				lst_DeadStockList.setValue(7, stockparam[i].getITF());
				lst_DeadStockList.setValue(8, stockparam[i].getUseByDate());
				lst_DeadStockList.setValue(9, WmsFormatter.getTimeFormat(stockparam[i].getStorageDate(), ""));
				lst_DeadStockList.setValue(10, stockparam[i].getItemName());
				lst_DeadStockList.setValue(11, WmsFormatter.getNumFormat(stockparam[i].getBundleEnteringQty()));
				lst_DeadStockList.setValue(12, WmsFormatter.getNumFormat(stockparam[i].getStockPieceQty()));
				lst_DeadStockList.setValue(13, stockparam[i].getBundleITF());

				//#CM5095
				// Compile the data for ToolTip
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM5096
				// Item name
				toolTip.add(label_itemname, stockparam[i].getItemName());
				//#CM5097
				// Case ITF
				toolTip.add(label_caseitf, stockparam[i].getITF());
				//#CM5098
				// Bundle ITF
				toolTip.add(label_bundleitf, stockparam[i].getBundleITF());
				//#CM5099
				// Expiry Date
				toolTip.add(label_usebydate, stockparam[i].getUseByDate());

				toolTip.add(title_AreaTypeName, stockparam[i].getAreaName());

				//#CM5100
				// Set ToolTip to current line
				lst_DeadStockList.setToolTip(
					lst_DeadStockList.getCurrentRow(),
					toolTip.getText());
			}
		}
		else
		{
			//#CM5101
			// Set the value for the Pager.
			//#CM5102
			// Maximum Count
			pgr_U.setMax(0);
			//#CM5103
			// Display Counts per 1 page
			pgr_U.setPage(0);
			//#CM5104
			// Start Position
			pgr_U.setIndex(0);
			//#CM5105
			// Maximum Count
			pgr_D.setMax(0);
			//#CM5106
			// Display Counts per 1 page
			pgr_D.setPage(0);
			//#CM5107
			// Start Position
			pgr_D.setIndex(0);

			//#CM5108
			// Execute search result count check
			String errorMsg = listbox.checkLength();
			//#CM5109
			// Hide the header.
			lst_DeadStockList.setVisible(false);
			//#CM5110
			// Error message display
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM5111
	// Event handler methods -----------------------------------------
	//#CM5112
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5113
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5114
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5115
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5116
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5117
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5118
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

	//#CM5119
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

	//#CM5120
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

	//#CM5121
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

	//#CM5122
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

	//#CM5123
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5124
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_DeadStockList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM5125
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_DeadStockList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM5126
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_DeadStockList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM5127
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_DeadStockList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM5128
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_DeadStockList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5129
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_DeadStockList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM5130
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_DeadStockList_Click(ActionEvent e) throws Exception
	{
	}

	//#CM5131
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
		//#CM5132
		// Store listbox to the Session
		SessionDeadStockListRet listbox =
			(SessionDeadStockListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM5133
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
		//#CM5134
		// Store listbox to the Session
		SessionDeadStockListRet listbox =
			(SessionDeadStockListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM5135
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
		//#CM5136
		// Store listbox to the Session
		SessionDeadStockListRet listbox =
			(SessionDeadStockListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM5137
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
		//#CM5138
		// Store listbox to the Session
		SessionDeadStockListRet listbox =
			(SessionDeadStockListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM5139
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5140
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
		//#CM5141
		// Store listbox to the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM5142
		// When value exists in the Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM5143
				// Close the statement
				finder.close();
			}
			//#CM5144
			// Close the connection
			sessionret.closeConnection();
		}
		//#CM5145
		// Delete from the Session
		this.getSession().removeAttribute("LISTBOX");
		//#CM5146
		// Return to the parent screen.
		parentRedirect(null);
	}


}
//#CM5147
//end of class
