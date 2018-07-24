// $Id: ListRetrievalCustomerBusiness.java,v 1.2 2007/02/07 04:18:43 suresh Exp $

//#CM709559
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalcustomer;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievaldate.ListRetrievalDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievallocation.ListRetrievalLocationBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievaWorkInfoCustomerRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalPlanCustomerRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalResultCustomerRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM709560
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this listbox class to search for Customer. <BR>
 * Search for the data using Customer code entered via parent screen. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	 Search for the data using Customer Code entered via parent screen as a key, and display it on the screen. <BR>
 * <BR>
 * </DIR>
 * 2. Button for the selected line (<CODE>lst_CustomerSearch_Click</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	 Pass the Customer Code, Customer Name on the selected line to the parent screen and close the listbox. <BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/21</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:43 $
 * @author  $Author: suresh $
 */
public class ListRetrievalCustomerBusiness extends ListRetrievalCustomer implements WMSConstants
{
	//#CM709561
	// Class fields --------------------------------------------------
	//#CM709562
	/** 
	 * Use this key to pass the customer code. 
	 */
	public static final String CUSTOMERCODE_KEY = "CUSTOMERCODE_KEY";

	//#CM709563
	/** 
	 * Use this key to pass the customer name. 
	 */
	public static final String CUSTOMERNAME_KEY = "CUSTOMERNAME_KEY";

	//#CM709564
	/** 
	 * Use this key to pass the search flag. 
	 */
	public static final String SEARCHCUSTOMER_KEY = "SEARCHCUSTOMER_KEY";

	//#CM709565
	/** 
	 * Use this key to pass the work status.
	 */
	public static final String WORKSTATUSCUSTOMER_KEY = "WORKSTATUSCUSTOMER_KEY";

	//#CM709566
	/** 
	 * Use this key to distinguish the data for Order/Item 
	 */
	public static final String ORDER_ITEM_FLAG = "ORDER_ITEM_FLAG";

	//#CM709567
	// Class variables -----------------------------------------------

	//#CM709568
	// Class method --------------------------------------------------

	//#CM709569
	// Constructors --------------------------------------------------

	//#CM709570
	// Public methods ------------------------------------------------

	//#CM709571
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Field item  <BR>
	 *	<DIR>
	 *		 Select <BR>
	 *		Customer Code <BR>
	 *		Customer Name <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM709572
		// Set the screen name. 
		//#CM709573
		// Search for the Customer.
		lbl_ListName.setText(DisplayText.getText("TLE-W0031"));

		//#CM709574
		// Obtain the parameter. 
		//#CM709575
		// Consignor Code
		String consignorcode = request.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM709576
		// Planned Picking Date
		String retrievalplandate = request.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM709577
		// Start Planned Picking Date
		String startretrievalplandate = request.getParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY);
		//#CM709578
		// End Planned Picking Date
		String endretrievalplandate = request.getParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY);
		//#CM709579
		// Start Picking Date
		String startretrievaldate = request.getParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY);
		//#CM709580
		// End Picking Date
		String endretrievaldate = request.getParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY);
		//#CM709581
		// Item Code
		String itemcode = request.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);
		//#CM709582
		// Customer Code
		String customercode = request.getParameter(CUSTOMERCODE_KEY);
		//#CM709583
		// Picking Location  
		String retrievallocation = request.getParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY);
		//#CM709584
		// Case Picking Location
		String caseretrievallocation = request.getParameter(ListRetrievalLocationBusiness.CASERETRIEVALLOCATION_KEY);
		//#CM709585
		// Piece Picking Location 
		String pieceretrievallocation = request.getParameter(ListRetrievalLocationBusiness.PIECERETRIEVALLOCATION_KEY);
		//#CM709586
		// Case/Piece flag 
		String casepieceflag = request.getParameter(ListRetrievalLocationBusiness.CASEPIECEFLAG_KEY);
		//#CM709587
		// Search flag (Plan or Result or Work) 
		String searchcustomer = request.getParameter(SEARCHCUSTOMER_KEY);
		//#CM709588
		// Order/Item 
		String orderitem = request.getParameter(ORDER_ITEM_FLAG);
		//#CM709589
		// Work status:
		String[] search = request.getParameterValues(WORKSTATUSCUSTOMER_KEY);

		viewState.setString(SEARCHCUSTOMER_KEY, searchcustomer);

		//#CM709590
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM709591
			// Close the connection. 
			sRet.closeConnection();
			//#CM709592
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM709593
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM709594
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM709595
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM709596
		// Planned Picking Date
		param.setRetrievalPlanDate(retrievalplandate);
		//#CM709597
		// Start Planned Picking Date
		param.setFromRetrievalPlanDate(startretrievalplandate);
		//#CM709598
		// End Planned Picking Date
		param.setToRetrievalPlanDate(endretrievalplandate);
		//#CM709599
		// Start Picking Date
		param.setFromRetrievalDate(startretrievaldate);
		//#CM709600
		// End Picking Date
		param.setToRetrievalDate(endretrievaldate);
		//#CM709601
		// Item Code
		param.setItemCode(itemcode);
		//#CM709602
		// Customer Code
		param.setCustomerCode(customercode);
		//#CM709603
		// Picking Location 
		param.setRetrievalLocation(retrievallocation);
		//#CM709604
		// Case Picking Location
		param.setCaseLocation(caseretrievallocation);
		//#CM709605
		// Piece Picking Location 
		param.setPieceLocation(pieceretrievallocation);
		//#CM709606
		// Case/Piece flag 
		param.setCasePieceflg(casepieceflag);
		//#CM709607
		// Array of work status 
		param.setSearchStatus(search);
		//#CM709608
		// Order/Item 
		param.setItemOrderFlag(orderitem);

		//#CM709609
		// Determine whether the Customer to be searched is Plan or Result or Work. 
		if (searchcustomer.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM709610
			// Generate the SessionRetrievalPlanCustomerRet instance. 
			SessionRetrievalPlanCustomerRet listbox = new SessionRetrievalPlanCustomerRet(conn, param);
			//#CM709611
			// Maintain the list box in the Session 
			this.getSession().setAttribute("LISTBOX", listbox);
			setPlanList(listbox, "first");
		}
		else if (searchcustomer.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM709612
			// Generate the SessionRetrievalResultCustomerRet instance. 
			SessionRetrievalResultCustomerRet listbox = new SessionRetrievalResultCustomerRet(conn, param);
			//#CM709613
			// Maintain the list box in the Session 
			this.getSession().setAttribute("LISTBOX", listbox);
			setResultList(listbox, "first");
		}
		else if (searchcustomer.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM709614
			// Generate the SessionRetrievaWorkInfoCustomerRet instance. 
			SessionRetrievaWorkInfoCustomerRet listbox = new SessionRetrievaWorkInfoCustomerRet(conn, param);
			//#CM709615
			// Maintain the list box in the Session 
			this.getSession().setAttribute("LISTBOX", listbox);
			setWorkInfoList(listbox, "first");
		}
		else
		{
			Object[] tObj = new Object[1];
			tObj[0] = DisplayText.getText("LBL-W0033");
			String classname = this.getClass().getName();
			RmiMsgLogClient.write(6007039, LogMessage.F_ERROR, classname, tObj);
			throw (new InvalidStatusException("6007039" + wDelim + tObj[0]));
		}
	}

	//#CM709616
	// Package methods -----------------------------------------------

	//#CM709617
	// Protected methods ---------------------------------------------

	//#CM709618
	// Private methods -----------------------------------------------
	//#CM709619
	/**
	 * Allow this method to change a page.  <BR>
	 * Search through the Plan table.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setPlanList(SessionRetrievalPlanCustomerRet listbox, String actionName) throws Exception
	{
		//#CM709620
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM709621
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM709622
			// Set a value for Pager. 
			//#CM709623
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM709624
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM709625
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM709626
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM709627
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM709628
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM709629
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM709630
			// Delete all lines. 
			lst_CustomerSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM709631
				// Obtain the end line. 
				int count = lst_CustomerSearch.getMaxRows();
				//#CM709632
				// Add a line. 
				lst_CustomerSearch.addRow();

				//#CM709633
				// Move to the end line. 
				lst_CustomerSearch.setCurrentRow(count);
				lst_CustomerSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_CustomerSearch.setValue(2, rsparam[i].getCustomerCode());
				lst_CustomerSearch.setValue(3, rsparam[i].getCustomerName());
			}
		}
		else
		{
			//#CM709634
			// Set a value for Pager. 
			//#CM709635
			// Max count 
			pgr_U.setMax(0);
			//#CM709636
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM709637
			// Start Position 
			pgr_U.setIndex(0);
			//#CM709638
			// Max count 
			pgr_D.setMax(0);
			//#CM709639
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM709640
			// Start Position 
			pgr_D.setIndex(0);

			//#CM709641
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM709642
			// Hide the header. 
			lst_CustomerSearch.setVisible(false);
			//#CM709643
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM709644
	/**
	 * Allow this method to change a page.  <BR>
	 * Search through the result table.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setResultList(SessionRetrievalResultCustomerRet listbox, String actionName) throws Exception
	{
		//#CM709645
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM709646
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM709647
			// Set a value for Pager. 
			//#CM709648
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM709649
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM709650
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM709651
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM709652
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM709653
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM709654
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM709655
			// Delete all lines. 
			lst_CustomerSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM709656
				// Obtain the end line. 
				int count = lst_CustomerSearch.getMaxRows();
				//#CM709657
				// Add a line. 
				lst_CustomerSearch.addRow();

				//#CM709658
				// Move to the end line. 
				lst_CustomerSearch.setCurrentRow(count);
				lst_CustomerSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_CustomerSearch.setValue(2, rsparam[i].getCustomerCode());
				lst_CustomerSearch.setValue(3, rsparam[i].getCustomerName());
			}
		}
		else
		{
			//#CM709659
			// Set a value for Pager. 
			//#CM709660
			// Max count 
			pgr_U.setMax(0);
			//#CM709661
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM709662
			// Start Position 
			pgr_U.setIndex(0);
			//#CM709663
			// Max count 
			pgr_D.setMax(0);
			//#CM709664
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM709665
			// Start Position 
			pgr_D.setIndex(0);

			//#CM709666
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM709667
			// Hide the header. 
			lst_CustomerSearch.setVisible(false);
			//#CM709668
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM709669
	/**
	 * Allow this method to change a page.  <BR>
	 * Search through the Work table.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setWorkInfoList(SessionRetrievaWorkInfoCustomerRet listbox, String actionName) throws Exception
	{
		//#CM709670
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM709671
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM709672
			// Set a value for Pager. 
			//#CM709673
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM709674
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM709675
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM709676
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM709677
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM709678
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM709679
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM709680
			// Delete all lines. 
			lst_CustomerSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM709681
				// Obtain the end line. 
				int count = lst_CustomerSearch.getMaxRows();
				//#CM709682
				// Add a line. 
				lst_CustomerSearch.addRow();

				//#CM709683
				// Move to the end line. 
				lst_CustomerSearch.setCurrentRow(count);
				lst_CustomerSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_CustomerSearch.setValue(2, rsparam[i].getCustomerCode());
				lst_CustomerSearch.setValue(3, rsparam[i].getCustomerName());
			}
		}
		else
		{
			//#CM709684
			// Set a value for Pager. 
			//#CM709685
			// Max count 
			pgr_U.setMax(0);
			//#CM709686
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM709687
			// Start Position 
			pgr_U.setIndex(0);
			//#CM709688
			// Max count 
			pgr_D.setMax(0);
			//#CM709689
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM709690
			// Start Position 
			pgr_D.setIndex(0);

			//#CM709691
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM709692
			// Hide the header. 
			lst_CustomerSearch.setVisible(false);
			//#CM709693
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM709694
	// Event handler methods -----------------------------------------
	//#CM709695
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709696
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709697
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

	//#CM709698
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

	//#CM709699
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

	//#CM709700
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

	//#CM709701
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

	//#CM709702
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709703
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_CustomerSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM709704
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_CustomerSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM709705
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_CustomerSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM709706
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_CustomerSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM709707
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_CustomerSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709708
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_CustomerSearch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM709709
	/** 
	 * Clicking on the Select button of listcell executes its proper process.  <BR>
	 * <BR>
	 *	 Pass the Customer Code, and Customer Name to the parent screen and close the listbox.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lst_CustomerSearch_Click(ActionEvent e) throws Exception
	{
		//#CM709710
		// Set the current line. 
		lst_CustomerSearch.setCurrentRow(lst_CustomerSearch.getActiveRow());
		lst_CustomerSearch.getValue(1);

		//#CM709711
		// Set the parameter needed to return to the parent screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM709712
		// Customer Code
		param.setParameter(CUSTOMERCODE_KEY, lst_CustomerSearch.getValue(2));
		//#CM709713
		// Customer Name
		param.setParameter(CUSTOMERNAME_KEY, lst_CustomerSearch.getValue(3));

		//#CM709714
		// Shift to the parent screen. 
		parentRedirect(param);
	}

	//#CM709715
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
		//#CM709716
		// Obtain the search flag. 
		String flag = viewState.getString(SEARCHCUSTOMER_KEY);

		if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM709717
			// Maintain the list box in the Session 
			SessionRetrievalPlanCustomerRet listbox = (SessionRetrievalPlanCustomerRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "next");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM709718
			// Maintain the list box in the Session 
			SessionRetrievalResultCustomerRet listbox = (SessionRetrievalResultCustomerRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "next");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM709719
			// Maintain the list box in the Session 
			SessionRetrievaWorkInfoCustomerRet listbox = (SessionRetrievaWorkInfoCustomerRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "next");
		}
	}

	//#CM709720
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
		//#CM709721
		// Obtain the search flag. 
		String flag = viewState.getString(SEARCHCUSTOMER_KEY);

		if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM709722
			// Maintain the list box in the Session 
			SessionRetrievalPlanCustomerRet listbox = (SessionRetrievalPlanCustomerRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "previous");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM709723
			// Maintain the list box in the Session 
			SessionRetrievalResultCustomerRet listbox = (SessionRetrievalResultCustomerRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "previous");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM709724
			// Maintain the list box in the Session 
			SessionRetrievaWorkInfoCustomerRet listbox = (SessionRetrievaWorkInfoCustomerRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "previous");
		}
	}

	//#CM709725
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
		//#CM709726
		// Obtain the search flag. 
		String flag = viewState.getString(SEARCHCUSTOMER_KEY);

		if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM709727
			// Maintain the list box in the Session 
			SessionRetrievalPlanCustomerRet listbox = (SessionRetrievalPlanCustomerRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "last");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM709728
			// Maintain the list box in the Session 
			SessionRetrievalResultCustomerRet listbox = (SessionRetrievalResultCustomerRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "last");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM709729
			// Maintain the list box in the Session 
			SessionRetrievaWorkInfoCustomerRet listbox = (SessionRetrievaWorkInfoCustomerRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "last");
		}
	}

	//#CM709730
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
		//#CM709731
		// Obtain the search flag. 
		String flag = viewState.getString(SEARCHCUSTOMER_KEY);

		if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM709732
			// Maintain the list box in the Session 
			SessionRetrievalPlanCustomerRet listbox = (SessionRetrievalPlanCustomerRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "first");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM709733
			// Maintain the list box in the Session 
			SessionRetrievalResultCustomerRet listbox = (SessionRetrievalResultCustomerRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "first");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM709734
			// Maintain the list box in the Session 
			SessionRetrievaWorkInfoCustomerRet listbox = (SessionRetrievaWorkInfoCustomerRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "first");
		}
	}

	//#CM709735
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709736
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
		//#CM709737
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM709738
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM709739
				// Close the statement. 
				finder.close();
			}
			//#CM709740
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM709741
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM709742
		// Return to the parent screen. 
		parentRedirect(null);
	}
}
//#CM709743
//end of class
