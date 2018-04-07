// $Id: ListStockLocationWorkingInquiryBusiness.java,v 1.2 2006/10/04 05:11:50 suresh Exp $

//#CM5833
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocationworkinginquiry;
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
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocation.ListStockLocationBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret.SessionLocationWorkingInquiryRet;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM5834
/**
 * Designer :T.Uehata<BR>
 * Maker : T.Uehata <BR>
 * <BR>
 * This is stock inquiry per location listbox class.<BR>
 * Search for the data using Consignor code <BR>
 * Execute following processes in this class. <BR>
 * <BR>
 * 1.Initial screen( <CODE>page_Load(ActionEvent e)</CODE> Method) <BR>
 * <BR>
 * <DIR>Search for the data using Consignor code <BR>
 * <BR>
 * [parameter] *Mandatory Input <BR>
 * <BR>
 * Consignor code* <BR>
 * Start location <BR>
 * End location <BR>
 * Item code <BR>
 * Case/PieceDivision* <BR>
 * <BR>
 * [Output data] <BR>
 * <BR>
 * Consignor code <BR>
 * Consignor name <BR>
 * Start location <BR>
 * End location <BR>
 * Item code <BR>
 * Location <BR>
 * Item code <BR>
 * Item name <BR>
 * Packing qty per case <BR>
 * packed qty per bundle <BR>
 * Stock Case Qty <BR>
 * Stock piece qty <BR>
 * allocation possible Case qty <BR>
 * allocation possible Piece qty <BR>
 * Division <BR>
 * Case ITF <BR>
 * Bundle ITF <BR>
 * Storage Date/Time <BR>
 * Expiry Date <BR>
 * </DIR> </DIR> <BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/09/28</TD>
 * <TD>T.Uehata</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:11:50 $
 * @author $Author: suresh $
 */
public class ListStockLocationWorkingInquiryBusiness extends ListStockLocationWorkingInquiry implements WMSConstants
{
	//#CM5835
	// Class fields --------------------------------------------------
	//#CM5836
	// KEY for maintaining in the session.
	//#CM5837
	// list cell contents
	private static final String LISTBOX = "LISTBOX";

	//#CM5838
	// Class variables -----------------------------------------------

	//#CM5839
	// Class method --------------------------------------------------

	//#CM5840
	// Constructors --------------------------------------------------

	//#CM5841
	// Public methods ------------------------------------------------

	//#CM5842
	/**
	 * Initialize the screen <BR>
	 * <DIR>Item <BR>
	 * [Except list cell] <BR>
	 * <DIR>
	 * Consignor code <BR>
	 * Consignor name <BR>
	 * Start location <BR>
	 * End location <BR>
	 * Item Code <BR>
	 * </DIR>
	 * [List cell] <BR>
	 * <DIR>
	 * Location <BR>
	 * Item Code <BR>
	 * Packing qty per Case <BR>
	 * Stock Case qty <BR>
	 * allocation possible case qty <BR>
	 * Division <BR>
	 * Case ITF <BR>
	 * Storage Date/Time <BR>
	 * Expiry Date <BR>
	 * Item name <BR>
	 * Packing qty per Bundle <BR>
	 * Stock Piece qty <BR>
	 * allocation Possible Piece qty  <BR>
	 * </DIR>
	 * [Balloon Help] <BR>
	 * <DIR>
	 * Item name <BR>
	 * Storage Date <BR>
	 * Storage Time <BR>
	 * Expiry Date <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM5843
		// Set the screen name
		//#CM5844
		// Search Location
		lbl_ListName.setText(DisplayText.getText("TLE-W0097"));
		
		//#CM5845
		// Obtain the parameter from the screen for stock inquiry by location.
		//#CM5846
		// Consignor code
		String _consignorcode = request.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM5847
		// Start location
		String _startlocation = request.getParameter(ListStockLocationBusiness.STARTLOCATION_KEY);
		//#CM5848
		// End location
		String _endlocation = request.getParameter(ListStockLocationBusiness.ENDLOCATION_KEY);
		//#CM5849
		// Item code
		String _itemcode = request.getParameter(ListStockItemBusiness.ITEMCODE_KEY);
		//#CM5850
		// Case/PieceDivision(Code)
		String _cpfcode = request.getParameter(ListStockLocationBusiness.CASEPIECEFLAG_KEY);

		//#CM5851
		// Input check
		//#CM5852
		// Consignor code
		if (!WmsCheckker.consignorCheck(_consignorcode, lst_LocationWorkingInquiry, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM5853
		// Start location
		if (!WmsCheckker.charCheck(_startlocation, lst_LocationWorkingInquiry, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM5854
		// End location
		if (!WmsCheckker.charCheck(_endlocation, lst_LocationWorkingInquiry, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM5855
		// Check which comes earlier Start Location or End Location
		if (!WmsCheckker.rangeLocationCheck(_startlocation, _endlocation, lst_LocationWorkingInquiry, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		//#CM5856
		// Item code
		if (!WmsCheckker.charCheck(_itemcode, lst_LocationWorkingInquiry, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM5857
		// Set the header item (count) other than list cell.
		//#CM5858
		// Consignor code
		lbl_JavaSetCnsgnrCd.setText(_consignorcode);
		//#CM5859
		// Start location
		lbl_JavaSetStartLocation.setText(_startlocation);
		//#CM5860
		// End location
		lbl_JavaSetEndLocation.setText(_endlocation);
		//#CM5861
		// Item code
		lbl_JavaSetItemCd.setText(_itemcode);

		//#CM5862
		// Close the connection of the object remained at the Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute(LISTBOX);
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM5863
			// Close the connection
			sRet.closeConnection();
			//#CM5864
			// Delete from the Session
			this.getSession().removeAttribute(LISTBOX);
		}

		//#CM5865
		// Obtain the connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM5866
		// Obtain the information displayed on the screen.
		//#CM5867
		// Set the search conditions parameter.
		StockControlParameter paramCont = new StockControlParameter();
		//#CM5868
		// Consignor code
		paramCont.setConsignorCode(_consignorcode);
		//#CM5869
		// Start location
		paramCont.setFromLocationNo(_startlocation);
		//#CM5870
		// End location
		paramCont.setToLocationNo(_endlocation);
		//#CM5871
		// Item code
		paramCont.setItemCode(_itemcode);
		//#CM5872
		// Case/PieceDivision
		paramCont.setCasePieceFlag(_cpfcode);

		//#CM5873
		// Obtain the result.
		//#CM5874
		// Generate Instance
		SessionLocationWorkingInquiryRet listbox = new SessionLocationWorkingInquiryRet(conn, paramCont);
		//#CM5875
		// Store listbox to the Session
		this.getSession().setAttribute(LISTBOX, listbox);
		setList(listbox, "first");

	}

	//#CM5876
	// Package methods -----------------------------------------------

	//#CM5877
	// Protected methods ---------------------------------------------

	//#CM5878
	// Private methods -----------------------------------------------
	//#CM5879
	/**
	 * Method to change the page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Reports all the exceptions.
	 */
	private void setList(SessionLocationWorkingInquiryRet listbox, String actionName) throws Exception
	{
		//#CM5880
		// Obtain the locale.
		Locale locale = this.getHttpRequest().getLocale();

		//#CM5881
		// Set the Page info.
		listbox.setActionName(actionName);

		//#CM5882
		// Obtain search result
		StockControlParameter[] stocks = listbox.getEntities();

		//#CM5883
		// Obtain the number of elements.
		int len = 0;
		if (stocks != null)
		{
			len = stocks.length;
		}

		if (len > 0)
		{
			//#CM5884
			// Set the value for the Pager.
			//#CM5885
			// Maximum Count
			pgr_U.setMax(listbox.getLength());
			//#CM5886
			// Display Counts per 1 page
			pgr_U.setPage(listbox.getCondition());
			//#CM5887
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM5888
			// Maximum Count
			pgr_D.setMax(listbox.getLength());
			//#CM5889
			// Display Counts per 1 page
			pgr_D.setPage(listbox.getCondition());
			//#CM5890
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM5891
			// Hide the message.
			lbl_InMsg.setVisible(false);

			lbl_JavaSetCnsgnrName.setText(stocks[0].getConsignorName());

			//#CM5892
			// Delete all lines.
			lst_LocationWorkingInquiry.clearRow();
			
			//#CM5893
			// Item name
			String label_itemname = DisplayText.getText("LBL-W0103");
			//#CM5894
			// Storage Date
			String label_storagedate = DisplayText.getText("LBL-W0237");
			//#CM5895
			// Storage Time
			String label_storagetime = DisplayText.getText("LBL-W0368");
			//#CM5896
			// Expiry Date
			String label_usebydate = DisplayText.getText("LBL-W0270");
			
			//#CM5897
			// Generate a content of the list cell.
			for (int i = 0; i < len; i++)
			{
				//#CM5898
				// Obtain the tailing line No.
				int count = lst_LocationWorkingInquiry.getMaxRows();
				//#CM5899
				// Add 1 line to the List cell (for the 1st line)
				lst_LocationWorkingInquiry.addRow();
				//#CM5900
				// Move to the end line.
				lst_LocationWorkingInquiry.setCurrentRow(count);

				//#CM5901
				// Set the search result for list cell.
				//#CM5902
				// 1st line
				//#CM5903
				// Location
				lst_LocationWorkingInquiry.setValue(1, stocks[i].getLocationNo());
				//#CM5904
				// Item code
				lst_LocationWorkingInquiry.setValue(2, stocks[i].getItemCode());
				//#CM5905
				// Packing qty per case
				lst_LocationWorkingInquiry.setValue(3, WmsFormatter.getNumFormat(stocks[i].getEnteringQty()));
				//#CM5906
				// Stock Case Qty
				lst_LocationWorkingInquiry.setValue(4, WmsFormatter.getNumFormat(stocks[i].getStockCaseQty()));
				//#CM5907
				// allocation possible Case qty
				lst_LocationWorkingInquiry.setValue(5, WmsFormatter.getNumFormat(stocks[i].getAllocateCaseQty()));
				//#CM5908
				// Division
				lst_LocationWorkingInquiry.setValue(6, stocks[i].getCasePieceFlagName());
				//#CM5909
				// Case ITF
				lst_LocationWorkingInquiry.setValue(7, stocks[i].getITF());
				//#CM5910
				// Storage Date/Time
				lst_LocationWorkingInquiry.setValue(8, WmsFormatter.toDispDate(WmsFormatter.toParamDate(stocks[i].getStorageDate()), locale));
				//#CM5911
				// Expiry Date
				lst_LocationWorkingInquiry.setValue(9, stocks[i].getUseByDate());

				//#CM5912
				// 2nd line
				//#CM5913
				// Item name
				lst_LocationWorkingInquiry.setValue(10, stocks[i].getItemName());
				//#CM5914
				// packed qty per bundle
				lst_LocationWorkingInquiry.setValue(11, WmsFormatter.getNumFormat(stocks[i].getBundleEnteringQty()));
				//#CM5915
				// Stock piece qty
				lst_LocationWorkingInquiry.setValue(12, WmsFormatter.getNumFormat(stocks[i].getStockPieceQty()));
				//#CM5916
				// allocation possible Piece qty
				lst_LocationWorkingInquiry.setValue(13, WmsFormatter.getNumFormat(stocks[i].getAllocatePieceQty()));
				//#CM5917
				// Bundle ITF
				lst_LocationWorkingInquiry.setValue(14, stocks[i].getBundleITF());
				//#CM5918
				// Storage Date/Time
				lst_LocationWorkingInquiry.setValue(15, WmsFormatter.getTimeFormat(stocks[i].getStorageDate(), ""));

				//#CM5919
				// Set the ToolTip.
				ToolTipHelper tTip = new ToolTipHelper();
				//#CM5920
				// Item name
				tTip.add(label_itemname, stocks[i].getItemName());
				//#CM5921
				// Storage Date
				tTip.add(label_storagedate, WmsFormatter.toDispDate(WmsFormatter.toParamDate(stocks[i].getStorageDate()), locale));
				//#CM5922
				// Storage Time
				tTip.add(label_storagetime, WmsFormatter.getTimeFormat(stocks[i].getStorageDate(), ""));
				//#CM5923
				// Expiry Date
				tTip.add(label_usebydate, stocks[i].getUseByDate());
				
				lst_LocationWorkingInquiry.setToolTip(count, tTip.getText());
			}
		}
		else
		{
			//#CM5924
			// Set the value for the Pager.
			//#CM5925
			// Maximum Count
			pgr_U.setMax(0);
			//#CM5926
			// Display Counts per 1 page
			pgr_U.setPage(0);
			//#CM5927
			// Start Position
			pgr_U.setIndex(0);
			//#CM5928
			// Maximum Count
			pgr_D.setMax(0);
			//#CM5929
			// Display Counts per 1 page
			pgr_D.setPage(0);
			//#CM5930
			// Start Position
			pgr_D.setIndex(0);

			//#CM5931
			// Execute search result count check
			String errorMsg = listbox.checkLength();
			//#CM5932
			// Hide the header.
			lst_LocationWorkingInquiry.setVisible(false);
			//#CM5933
			// Error message display
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM5934
	// Event handler methods -----------------------------------------
	//#CM5935
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5936
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5937
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5938
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5939
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5940
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Location_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5941
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetStartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5942
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5943
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetEndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5944
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5945
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5946
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5947
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5948
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

	//#CM5949
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

	//#CM5950
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

	//#CM5951
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

	//#CM5952
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

	//#CM5953
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationWorkingInquiry_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM5954
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationWorkingInquiry_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM5955
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationWorkingInquiry_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM5956
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationWorkingInquiry_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM5957
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationWorkingInquiry_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5958
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationWorkingInquiry_Change(ActionEvent e) throws Exception
	{
	}

	//#CM5959
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_LocationWorkingInquiry_Click(ActionEvent e) throws Exception
	{
	}

	//#CM5960
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
		//#CM5961
		// Store listbox to the Session
		SessionLocationWorkingInquiryRet listbox = (SessionLocationWorkingInquiryRet) this.getSession().getAttribute(LISTBOX);
		setList(listbox, "next");
	}

	//#CM5962
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
		//#CM5963
		// Store listbox to the Session
		SessionLocationWorkingInquiryRet listbox = (SessionLocationWorkingInquiryRet) this.getSession().getAttribute(LISTBOX);
		setList(listbox, "previous");
	}

	//#CM5964
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
		//#CM5965
		// Store listbox to the Session
		SessionLocationWorkingInquiryRet listbox = (SessionLocationWorkingInquiryRet) this.getSession().getAttribute(LISTBOX);
		setList(listbox, "last");
	}

	//#CM5966
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
		//#CM5967
		// Store listbox to the Session
		SessionLocationWorkingInquiryRet listbox = (SessionLocationWorkingInquiryRet) this.getSession().getAttribute(LISTBOX);
		setList(listbox, "first");
	}

	//#CM5968
	/**
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5969
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
		//#CM5970
		// Store listbox to the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute(LISTBOX);
		//#CM5971
		// When value exists in the Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM5972
				// Close the statement
				finder.close();
			}
			//#CM5973
			// Close the connection
			sessionret.closeConnection();
		}
		//#CM5974
		// Delete from the Session
		this.getSession().removeAttribute(LISTBOX);
		//#CM5975
		// Return to the parent screen.
		parentRedirect(null);
	}

	//#CM5976
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

}
//#CM5977
//end of class
