// $Id: ListRetrievalOrderQtyListBusiness.java,v 1.2 2007/02/07 04:18:54 suresh Exp $

//#CM711435
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderqtylist;
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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalcustomer.ListRetrievalCustomerBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievaldate.ListRetrievalDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderlist.ListRetrievalOrderListBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalOrderQtyListRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM711436
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this listbox class to search for Order Picking Result Report list. <BR>
 * Search for the databased on Consignor Code, Picking Date, Customer Code, Order No., Case/Piece division entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Consignor Code, Picking Date, Customer Code, Order No., Case/Piece division entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/22</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:54 $
 * @author  $Author: suresh $
 */
public class ListRetrievalOrderQtyListBusiness extends ListRetrievalOrderQtyList implements WMSConstants
{
	//#CM711437
	// Class fields --------------------------------------------------

	//#CM711438
	// Class variables -----------------------------------------------

	//#CM711439
	// Class method --------------------------------------------------

	//#CM711440
	// Constructors --------------------------------------------------

	//#CM711441
	// Public methods ------------------------------------------------

	//#CM711442
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Field item  <BR>
	 *	<DIR>
	 *		1.Picking Date <BR>
	 *		2.Customer Code <BR>
	 *		3.Order No. <BR>
	 *		4.Item Code <BR>
	 *		5. Division  <BR>
	 *		6.Packed Qty per Case <BR>
	 *		7.Planned Work Case Qty <BR>
	 *		8. Result Case Qty  <BR>
	 *		9.Shortage Case Qty <BR>
	 *		10. Picking Location  <BR>
	 *		11.Expiry Date <BR>
	 *		12.Planned Picking Date <BR>
	 *		13.Customer Name <BR>
	 *		14.Item Name <BR>
	 *		15.Packed qty per bundle <BR>
	 *		16.Planned Work Piece Qty <BR>
	 *		17. Result Piece Qty  <BR>
	 *		18.Shortage Piece Qty <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM711443
		// Set the screen name. 
		//#CM711444
		// Order Picking Result list 
		lbl_ListName.setText(DisplayText.getText("TLE-W0079"));

		//#CM711445
		// Obtain the parameter. 
		//#CM711446
		// Consignor Code
		String consignorcode = request.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM711447
		// Start Picking Date
		String startretrievaldate = request.getParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY);
		//#CM711448
		// End Picking Date
		String endretrievaldate = request.getParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY);
		//#CM711449
		// Customer Code
		String customercode = request.getParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY);
		//#CM711450
		// Order No.
		String orderno = request.getParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY);
		//#CM711451
		// Case/Piece division 
		String casepieceflag = request.getParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY);

		//#CM711452
		// Check for mandatory input and forbidden character in the Consignor code. 
		if (!WmsCheckker.consignorCheck(consignorcode, lst_OdrRtrivlRsltList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711453
		// Check for the range of Picking Date. 
		if (!WmsCheckker.rangeRetrievalDateCheck(startretrievaldate, endretrievaldate, lst_OdrRtrivlRsltList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711454
		// Check the Customer Code for prohibited character.
		if (!WmsCheckker.charCheck(customercode, lst_OdrRtrivlRsltList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711455
		// Check the Order No. for prohibited character.
		if (!WmsCheckker.charCheck(orderno, lst_OdrRtrivlRsltList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711456
		// Display the search conditions on the screen. 
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		txt_FDateStart.setDate(WmsFormatter.toDate(startretrievaldate));
		txt_FDateEnd.setDate(WmsFormatter.toDate(endretrievaldate));

		//#CM711457
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM711458
			// Close the connection. 
			sRet.closeConnection();
			//#CM711459
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM711460
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM711461
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM711462
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM711463
		// Start Picking Date
		param.setFromRetrievalDate(startretrievaldate);
		//#CM711464
		// End Picking Date
		param.setToRetrievalDate(endretrievaldate);
		//#CM711465
		// Customer Code
		param.setCustomerCode(customercode);
		//#CM711466
		// Order No.
		param.setOrderNo(orderno);
		//#CM711467
		// Case/Piece division 
		param.setCasePieceflg(casepieceflag);

		//#CM711468
		// Generate the SessionRetrievalOrderQtyListRet instance. 
		SessionRetrievalOrderQtyListRet listbox = new SessionRetrievalOrderQtyListRet(conn, param);
		//#CM711469
		// Maintain the list box in the Session 
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM711470
	// Package methods -----------------------------------------------

	//#CM711471
	// Protected methods ---------------------------------------------

	//#CM711472
	// Private methods -----------------------------------------------
	//#CM711473
	/**
	 * Allow this method to change a page.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(SessionRetrievalOrderQtyListRet listbox, String actionName) throws Exception
	{
		//#CM711474
		// Obtain the Local. 
		Locale locale = this.getHttpRequest().getLocale();

		//#CM711475
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM711476
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM711477
			// Set the Consignor name as a search condition. 
			lbl_JavaSetCnsgnrNm.setText(rsparam[0].getConsignorName());

			//#CM711478
			// Set a value for Pager. 
			//#CM711479
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM711480
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM711481
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM711482
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM711483
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM711484
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM711485
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM711486
			// Delete all lines. 
			lst_OdrRtrivlRsltList.clearRow();

			//#CM711487
			// Customer Name
			String label_customername = DisplayText.getText("LBL-W0036");
			//#CM711488
			// Item Name
			String label_itemname = DisplayText.getText("LBL-W0103");
			//#CM711489
			// Planned Work Case Qty
			String label_plancaseqty = DisplayText.getText("LBL-W0436");
			//#CM711490
			// Planned Work Piece Qty
			String label_planpieceqty = DisplayText.getText("LBL-W0437");
			//#CM711491
			// Result Case Qty 
			String label_resultcaseqty = DisplayText.getText("LBL-W0418");
			//#CM711492
			// Result Piece Qty 
			String label_resultpieceqty = DisplayText.getText("LBL-W0417");
			//#CM711493
			// Shortage Case Qty
			String label_shortagecaseqty = DisplayText.getText("LBL-W0208");
			//#CM711494
			// Shortage Piece Qty
			String label_shortagepieceqty = DisplayText.getText("LBL-W0209");
			//#CM711495
			// Picking Location 
			String label_retrievallocation = DisplayText.getText("LBL-W0172");
			//#CM711496
			// Expiry Date
			String label_usebydate = DisplayText.getText("LBL-W0270");
			//#CM711497
			// Area Name 
			String title_AreaTypeName = DisplayText.getText("LBL-W0569");

			for (int i = 0; i < len; i++)
			{
				//#CM711498
				// Obtain the end line. 
				int count = lst_OdrRtrivlRsltList.getMaxRows();
				//#CM711499
				// Add a line. 
				lst_OdrRtrivlRsltList.addRow();

				//#CM711500
				// Move to the end line. 
				lst_OdrRtrivlRsltList.setCurrentRow(count);
				lst_OdrRtrivlRsltList.setValue(1, WmsFormatter.toDispDate(rsparam[i].getRetrievalDate(), locale));
				lst_OdrRtrivlRsltList.setValue(2, rsparam[i].getCustomerCode());
				lst_OdrRtrivlRsltList.setValue(3, rsparam[i].getOrderNo());
				lst_OdrRtrivlRsltList.setValue(4, rsparam[i].getItemCode());
				lst_OdrRtrivlRsltList.setValue(5, rsparam[i].getCasePieceflgName());
				lst_OdrRtrivlRsltList.setValue(6, WmsFormatter.getNumFormat(rsparam[i].getEnteringQty()));
				lst_OdrRtrivlRsltList.setValue(7, WmsFormatter.getNumFormat(rsparam[i].getPlanCaseQty()));
				lst_OdrRtrivlRsltList.setValue(8, WmsFormatter.getNumFormat(rsparam[i].getResultCaseQty()));
				lst_OdrRtrivlRsltList.setValue(9, WmsFormatter.getNumFormat(rsparam[i].getShortageCaseQty()));
				lst_OdrRtrivlRsltList.setValue(10, WmsFormatter.toDispLocation(
				        rsparam[i].getRetrievalLocation(), rsparam[i].getSystemDiscKey()));
				
				lst_OdrRtrivlRsltList.setValue(11, rsparam[i].getUseByDate());
				lst_OdrRtrivlRsltList.setValue(12, WmsFormatter.toDispDate(rsparam[i].getRetrievalPlanDate(), locale));
				lst_OdrRtrivlRsltList.setValue(13, rsparam[i].getCustomerName());
				lst_OdrRtrivlRsltList.setValue(14, rsparam[i].getItemName());
				lst_OdrRtrivlRsltList.setValue(15, WmsFormatter.getNumFormat(rsparam[i].getBundleEnteringQty()));
				lst_OdrRtrivlRsltList.setValue(16, WmsFormatter.getNumFormat(rsparam[i].getPlanPieceQty()));
				lst_OdrRtrivlRsltList.setValue(17, WmsFormatter.getNumFormat(rsparam[i].getResultPieceQty()));
				lst_OdrRtrivlRsltList.setValue(18, WmsFormatter.getNumFormat(rsparam[i].getShortagePieceQty()));

				//#CM711501
				// Compile the ToolTip data. 
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM711502
				// Customer Name
				toolTip.add(label_customername, rsparam[i].getCustomerName());
				//#CM711503
				// Item Name
				toolTip.add(label_itemname, rsparam[i].getItemName());
				//#CM711504
				// Planned Work Case Qty
				toolTip.add(label_plancaseqty, WmsFormatter.getNumFormat(rsparam[i].getPlanCaseQty()));
				//#CM711505
				// Planned Work Piece Qty
				toolTip.add(label_planpieceqty, WmsFormatter.getNumFormat(rsparam[i].getPlanPieceQty()));
				//#CM711506
				// Result Case Qty 
				toolTip.add(label_resultcaseqty, WmsFormatter.getNumFormat(rsparam[i].getResultCaseQty()));
				//#CM711507
				// Result Piece Qty 
				toolTip.add(label_resultpieceqty, WmsFormatter.getNumFormat(rsparam[i].getResultPieceQty()));
				//#CM711508
				// Shortage Case Qty
				toolTip.add(label_shortagecaseqty, WmsFormatter.getNumFormat(rsparam[i].getShortageCaseQty()));
				//#CM711509
				// Shortage Piece Qty
				toolTip.add(label_shortagepieceqty, WmsFormatter.getNumFormat(rsparam[i].getShortagePieceQty()));
				//#CM711510
				// Picking Location 
				toolTip.add(label_retrievallocation, WmsFormatter.toDispLocation(rsparam[i].getRetrievalLocation(),
				        rsparam[i].getSystemDiscKey()));
				//#CM711511
				// Expiry Date
				toolTip.add(label_usebydate, rsparam[i].getUseByDate());

				toolTip.add(title_AreaTypeName, rsparam[i].getRetrievalAreaName());
				//#CM711512
				// Set the TOOL TIP in the current line. 
				lst_OdrRtrivlRsltList.setToolTip(lst_OdrRtrivlRsltList.getCurrentRow(), toolTip.getText());
			}
		}
		else
		{
			//#CM711513
			// Set a value for Pager. 
			//#CM711514
			// Max count 
			pgr_U.setMax(0);
			//#CM711515
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM711516
			// Start Position 
			pgr_U.setIndex(0);
			//#CM711517
			// Max count 
			pgr_D.setMax(0);
			//#CM711518
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM711519
			// Start Position 
			pgr_D.setIndex(0);

			//#CM711520
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM711521
			// Hide the header. 
			lst_OdrRtrivlRsltList.setVisible(false);
			//#CM711522
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM711523
	// Event handler methods -----------------------------------------
	//#CM711524
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711525
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711526
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711527
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711528
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711529
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711530
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711531
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStart_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM711532
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStart_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM711533
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711534
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711535
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM711536
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM711537
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711538
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

	//#CM711539
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

	//#CM711540
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

	//#CM711541
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

	//#CM711542
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

	//#CM711543
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711544
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlRsltList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM711545
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlRsltList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM711546
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlRsltList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM711547
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlRsltList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM711548
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlRsltList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711549
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlRsltList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM711550
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlRsltList_Click(ActionEvent e) throws Exception
	{
	}

	//#CM711551
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
		//#CM711552
		// Maintain the list box in the Session 
		SessionRetrievalOrderQtyListRet listbox = (SessionRetrievalOrderQtyListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM711553
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
		//#CM711554
		// Maintain the list box in the Session 
		SessionRetrievalOrderQtyListRet listbox = (SessionRetrievalOrderQtyListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM711555
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
		//#CM711556
		// Maintain the list box in the Session 
		SessionRetrievalOrderQtyListRet listbox = (SessionRetrievalOrderQtyListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM711557
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
		//#CM711558
		// Maintain the list box in the Session 
		SessionRetrievalOrderQtyListRet listbox = (SessionRetrievalOrderQtyListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM711559
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711560
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
		//#CM711561
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM711562
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM711563
				// Close the statement. 
				finder.close();
			}
			//#CM711564
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM711565
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM711566
		// Return to the parent screen. 
		parentRedirect(null);
	}
}
//#CM711567
//end of class
