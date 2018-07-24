// $Id: ListRetrievalItemQtyListBusiness.java,v 1.2 2007/02/07 04:18:48 suresh Exp $

//#CM710424
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitemqtylist;
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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievaldate.ListRetrievalDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitemlist.ListRetrievalItemListBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalItemQtyListRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM710425
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this listbox class to search for Item Picking Result Report list. <BR>
 * Search for the databased on Consignor Code, Start Picking Date, End Picking Date, Item Code, Case/Piece division entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Consignor Code, Start Picking Date, End Picking Date, Item Code, Case/Piece division entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/22</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:48 $
 * @author  $Author: suresh $
 */
public class ListRetrievalItemQtyListBusiness extends ListRetrievalItemQtyList implements WMSConstants
{
	//#CM710426
	// Class fields --------------------------------------------------

	//#CM710427
	// Class variables -----------------------------------------------

	//#CM710428
	// Class method --------------------------------------------------

	//#CM710429
	// Constructors --------------------------------------------------

	//#CM710430
	// Public methods ------------------------------------------------

	//#CM710431
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Field item  <BR>
	 *	<DIR>
	 *		1.Picking Date <BR>
	 *		2.Item Code <BR>
	 *		3. Division  <BR>
	 *		4.Packed Qty per Case <BR>
	 *		5.Planned Work Case Qty <BR>
	 *		6. Result Case Qty  <BR>
	 *		7.Shortage Case Qty <BR>
	 *		8. Picking Location  <BR>
	 *		9.Expiry Date <BR>
	 *		10.Planned Picking Date <BR>
	 *		11.Item Name <BR>
	 *		12.Packed qty per bundle <BR>
	 *		13.Planned Work Piece Qty <BR>
	 *		14. Result Piece Qty  <BR>
	 *		15.Shortage Piece Qty <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM710432
		// Set the screen name. 
		//#CM710433
		// Item Picking Result list 
		lbl_ListName.setText(DisplayText.getText("TLE-W0080"));

		//#CM710434
		// Obtain the parameter. 
		//#CM710435
		// Consignor Code
		String consignorcode = request.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM710436
		// Start Picking Date
		String startretrievaldate = request.getParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY);
		//#CM710437
		// End Picking Date
		String endretrievaldate = request.getParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY);
		//#CM710438
		// Item Code
		String itemcode = request.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);
		//#CM710439
		// Case/Piece division 
		String casepieceflag = request.getParameter(ListRetrievalItemListBusiness.CASEPIECEFLAG_KEY);

		//#CM710440
		// Check for mandatory input and forbidden character in the Consignor code. 
		if (!WmsCheckker.consignorCheck(consignorcode, lst_ItemRtrivlRsltList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710441
		// Check for the range of Picking Date. 
		if (!WmsCheckker.rangeRetrievalDateCheck(startretrievaldate, endretrievaldate, lst_ItemRtrivlRsltList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710442
		// Check for forbidden character in the Item code. 
		if (!WmsCheckker.charCheck(itemcode, lst_ItemRtrivlRsltList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710443
		// Display the search conditions on the screen. 
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		txt_FDateStart.setDate(WmsFormatter.toDate(startretrievaldate));
		txt_FDateEnd.setDate(WmsFormatter.toDate(endretrievaldate));

		//#CM710444
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM710445
			// Close the connection. 
			sRet.closeConnection();
			//#CM710446
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM710447
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM710448
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM710449
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM710450
		// Start Picking Date
		param.setFromRetrievalDate(startretrievaldate);
		//#CM710451
		// End Picking Date
		param.setToRetrievalDate(endretrievaldate);
		//#CM710452
		// Item Code
		param.setItemCode(itemcode);
		//#CM710453
		// Case/Piece division 
		param.setCasePieceflg(casepieceflag);

		//#CM710454
		// Generate the SessionRetrievalItemQtyListRet instance. 
		SessionRetrievalItemQtyListRet listbox = new SessionRetrievalItemQtyListRet(conn, param);
		//#CM710455
		// Maintain the list box in the Session 
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM710456
	// Package methods -----------------------------------------------

	//#CM710457
	// Protected methods ---------------------------------------------

	//#CM710458
	// Private methods -----------------------------------------------
	//#CM710459
	/**
	 * Allow this method to change a page.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(SessionRetrievalItemQtyListRet listbox, String actionName) throws Exception
	{
		//#CM710460
		// Obtain the Local. 
		Locale locale = this.getHttpRequest().getLocale();

		//#CM710461
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM710462
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM710463
			// Set the Consignor name as a search condition. 
			lbl_JavaSetCnsgnrNm.setText(rsparam[0].getConsignorName());

			//#CM710464
			// Set a value for Pager. 
			//#CM710465
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM710466
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM710467
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM710468
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM710469
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM710470
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM710471
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM710472
			// Delete all lines. 
			lst_ItemRtrivlRsltList.clearRow();

			//#CM710473
			// Item Name
			String label_itemname = DisplayText.getText("LBL-W0103");
			//#CM710474
			// Picking Location 
			String label_retrievallocation = DisplayText.getText("LBL-W0172");
			//#CM710475
			// Expiry Date
			String label_usebydate = DisplayText.getText("LBL-W0270");

			String title_AreaTypeName = DisplayText.getText("LBL-W0569");
			
			for (int i = 0; i < len; i++)
			{
				//#CM710476
				// Obtain the end line. 
				int count = lst_ItemRtrivlRsltList.getMaxRows();
				//#CM710477
				// Add a line. 
				lst_ItemRtrivlRsltList.addRow();

				//#CM710478
				// Move to the end line. 
				lst_ItemRtrivlRsltList.setCurrentRow(count);
				lst_ItemRtrivlRsltList.setValue(1, WmsFormatter.toDispDate(rsparam[i].getRetrievalDate(), locale));
				lst_ItemRtrivlRsltList.setValue(2, rsparam[i].getItemCode());
				lst_ItemRtrivlRsltList.setValue(3, rsparam[i].getCasePieceflgName());
				lst_ItemRtrivlRsltList.setValue(4, WmsFormatter.getNumFormat(rsparam[i].getEnteringQty()));
				lst_ItemRtrivlRsltList.setValue(5, WmsFormatter.getNumFormat(rsparam[i].getPlanCaseQty()));
				lst_ItemRtrivlRsltList.setValue(6, WmsFormatter.getNumFormat(rsparam[i].getResultCaseQty()));
				lst_ItemRtrivlRsltList.setValue(7, WmsFormatter.getNumFormat(rsparam[i].getShortageCaseQty()));
				lst_ItemRtrivlRsltList.setValue(8, WmsFormatter.toDispLocation(
				        rsparam[i].getRetrievalLocation(), rsparam[i].getSystemDiscKey()));
				lst_ItemRtrivlRsltList.setValue(9, rsparam[i].getUseByDate());
				lst_ItemRtrivlRsltList.setValue(10, WmsFormatter.toDispDate(rsparam[i].getRetrievalPlanDate(), locale));
				lst_ItemRtrivlRsltList.setValue(11, rsparam[i].getItemName());
				lst_ItemRtrivlRsltList.setValue(12, WmsFormatter.getNumFormat(rsparam[i].getBundleEnteringQty()));
				lst_ItemRtrivlRsltList.setValue(13, WmsFormatter.getNumFormat(rsparam[i].getPlanPieceQty()));
				lst_ItemRtrivlRsltList.setValue(14, WmsFormatter.getNumFormat(rsparam[i].getResultPieceQty()));
				lst_ItemRtrivlRsltList.setValue(15, WmsFormatter.getNumFormat(rsparam[i].getShortagePieceQty()));

				//#CM710479
				// Compile the ToolTip data. 
				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(label_itemname, rsparam[i].getItemName());
				toolTip.add(label_retrievallocation, WmsFormatter.toDispLocation(
						rsparam[i].getRetrievalLocation(), rsparam[i].getSystemDiscKey()));
				toolTip.add(label_usebydate, rsparam[i].getUseByDate());
				
				toolTip.add(title_AreaTypeName, rsparam[i].getRetrievalAreaName());
				//#CM710480
				// Set the TOOL TIP. 	
				lst_ItemRtrivlRsltList.setToolTip(lst_ItemRtrivlRsltList.getCurrentRow(), toolTip.getText());
			}
		}
		else
		{
			//#CM710481
			// Set a value for Pager. 
			//#CM710482
			// Max count 
			pgr_U.setMax(0);
			//#CM710483
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM710484
			// Start Position 
			pgr_U.setIndex(0);
			//#CM710485
			// Max count 
			pgr_D.setMax(0);
			//#CM710486
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM710487
			// Start Position 
			pgr_D.setIndex(0);

			//#CM710488
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM710489
			// Hide the header. 
			lst_ItemRtrivlRsltList.setVisible(false);
			//#CM710490
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM710491
	// Event handler methods -----------------------------------------
	//#CM710492
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710493
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710494
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710495
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710496
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710497
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710498
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710499
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStart_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM710500
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStart_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM710501
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710502
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710503
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM710504
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM710505
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710506
	/** 
	 * Execute the process defined for clicking on the Close button. <BR>
	 *  <BR>
	 * Close the listbox and shift to the parent screen.  <BR>
	 *  <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		btn_Close_D_Click(e);
	}

	//#CM710507
	/** 
	 * Execute the process defined for clicking on > button.  <BR>
	 * <BR>
	 * Display the next page.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	//#CM710508
	/** 
	 * Clicking on < button executes its process.  <BR>
	 * <BR>
	 * Display the previous one page.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
		pgr_D_Prev(e);
	}

	//#CM710509
	/** 
	 * Clicking >> button executes its process.  <BR>
	 * <BR>
	 * Display the end page.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		pgr_D_Last(e);
	}

	//#CM710510
	/** 
	 * Execute the process defined for clicking on << button.  <BR>
	 * <BR>
	 * Display the top page.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		pgr_D_First(e);
	}

	//#CM710511
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710512
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlRsltList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM710513
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlRsltList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM710514
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlRsltList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM710515
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlRsltList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM710516
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlRsltList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710517
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlRsltList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM710518
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlRsltList_Click(ActionEvent e) throws Exception
	{
	}

	//#CM710519
	/** 
	 * Execute the process defined for clicking on > button.  <BR>
	 * <BR>
	 * Display the next page.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		//#CM710520
		// Maintain the list box in the Session 
		SessionRetrievalItemQtyListRet listbox = (SessionRetrievalItemQtyListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM710521
	/** 
	 * Clicking on < button executes its process.  <BR>
	 * <BR>
	 * Display the previous one page.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		//#CM710522
		// Maintain the list box in the Session 
		SessionRetrievalItemQtyListRet listbox = (SessionRetrievalItemQtyListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM710523
	/** 
	 * Clicking >> button executes its process.  <BR>
	 * <BR>
	 * Display the end page.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		//#CM710524
		// Maintain the list box in the Session 
		SessionRetrievalItemQtyListRet listbox = (SessionRetrievalItemQtyListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM710525
	/** 
	 * Execute the process defined for clicking on << button.  <BR>
	 * <BR>
	 * Display the top page.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM710526
		// Maintain the list box in the Session 
		SessionRetrievalItemQtyListRet listbox = (SessionRetrievalItemQtyListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM710527
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710528
	/** 
	 * Execute the process defined for clicking on the Close button. <BR>
	 *  <BR>
	 * Close the listbox and shift to the parent screen.  <BR>
	 *  <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM710529
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM710530
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM710531
				// Close the statement. 
				finder.close();
			}
			//#CM710532
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM710533
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM710534
		// Return to the parent screen. 
		parentRedirect(null);
	}
}
//#CM710535
//end of class
