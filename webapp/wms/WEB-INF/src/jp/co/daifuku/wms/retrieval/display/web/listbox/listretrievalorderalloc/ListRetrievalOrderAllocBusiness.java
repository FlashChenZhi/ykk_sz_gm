// $Id: ListRetrievalOrderAllocBusiness.java,v 1.2 2007/02/07 04:18:50 suresh Exp $

//#CM710720
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderalloc;
import java.sql.Connection;

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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderlist.ListRetrievalOrderListBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalOrderAllocListRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM710721
/**
 * Designer : Y.Osawa <BR>
 * Maker : Y.Osawa <BR>
 * <BR>
 * Allow this listbox class to search for Order Picking Allocation Work Report list. <BR>
 * Search for the databased on Consignor Code, Planned Picking Date, Order No., and Case/Piece division entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Consignor Code, Planned Picking Date, Order No., and Case/Piece division entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/22</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:50 $
 * @author  $Author: suresh $
 */
public class ListRetrievalOrderAllocBusiness extends ListRetrievalOrderAlloc implements WMSConstants
{
	//#CM710722
	// Class fields --------------------------------------------------

	//#CM710723
	// Class variables -----------------------------------------------

	//#CM710724
	// Class method --------------------------------------------------

	//#CM710725
	// Constructors --------------------------------------------------

	//#CM710726
	// Public methods ------------------------------------------------

	//#CM710727
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Field item  <BR>
	 *	<DIR>
	 *		1.Order No. <BR>
	 *		2. Case/Piece division  <BR>
	 *		3.Customer Code <BR>
	 *		4.Customer Name <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM710728
		// Set the screen name. 
		//#CM710729
		// Order Picking allocation Work Report list 
		lbl_ListName.setText(DisplayText.getText("TLE-W0525"));

		//#CM710730
		// Obtain the parameter. 
		//#CM710731
		// Consignor Code
		String consignorcode = request.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM710732
		// Planned Picking Date
		String retrievalplandate = request.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM710733
		// Start Order No. 
		String startorderno = request.getParameter(ListRetrievalOrdernoBusiness.STARTORDERNO_KEY);
		//#CM710734
		// End Order No. 
		String endorderno = request.getParameter(ListRetrievalOrdernoBusiness.ENDORDERNO_KEY);
		//#CM710735
		// Case/Piece division 
		String casepieceflag = request.getParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY);
		
		//#CM710736
		// Check for mandatory input and forbidden character in the Consignor code. 
		if (!WmsCheckker.consignorCheck(consignorcode, lst_OrderRtrivlAllocWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710737
		// Check for mandatory input and prohibited characters in the Picking Plan Info. 
		if (!WmsCheckker.retrievalplandateCheck(retrievalplandate, lst_OrderRtrivlAllocWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710738
		// Check for prohibited characters of Start Order No. 
		if (!WmsCheckker.charCheck(startorderno, lst_OrderRtrivlAllocWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710739
		// Check the End Order No. for prohibited character.
		if (!WmsCheckker.charCheck(endorderno, lst_OrderRtrivlAllocWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710740
		// Check for the values of Start Order No. and End Order No. for precedence. 
		if (!WmsCheckker.rangeOrderNoCheck(startorderno, endorderno, lst_OrderRtrivlAllocWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		
		//#CM710741
		// Display the search conditions on the screen. 
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		txt_FDate.setDate(WmsFormatter.toDate(retrievalplandate));

		//#CM710742
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM710743
			// Close the connection. 
			sRet.closeConnection();
			//#CM710744
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM710745
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);
		
		//#CM710746
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM710747
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM710748
		// Planned Picking Date
		param.setRetrievalPlanDate(retrievalplandate);
		//#CM710749
		// Start Order No. 
		param.setStartOrderNo(startorderno);
		//#CM710750
		// End Order No. 
		param.setEndOrderNo(endorderno);
		//#CM710751
		// Case/Piece division 
		param.setCasePieceflg(casepieceflag);
		
		//#CM710752
		// Generate the SessionRetrievalOrderQtyListRet instance. 
		SessionRetrievalOrderAllocListRet listbox = new SessionRetrievalOrderAllocListRet(conn, param);
		//#CM710753
		// Maintain the list box in the Session 
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	public void page_Initialize(ActionEvent e) throws Exception
	{
	}

	//#CM710754
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710755
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710756
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710757
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710758
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710759
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710760
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710761
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM710762
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM710763
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710764
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

	//#CM710765
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

	//#CM710766
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

	//#CM710767
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

	//#CM710768
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

	//#CM710769
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710770
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
		//#CM710771
		// Maintain the list box in the Session 
		SessionRetrievalOrderAllocListRet listbox = (SessionRetrievalOrderAllocListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM710772
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
		//#CM710773
		// Maintain the list box in the Session 
		SessionRetrievalOrderAllocListRet listbox = (SessionRetrievalOrderAllocListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM710774
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
		//#CM710775
		// Maintain the list box in the Session 
		SessionRetrievalOrderAllocListRet listbox = (SessionRetrievalOrderAllocListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM710776
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
		//#CM710777
		// Maintain the list box in the Session 
		SessionRetrievalOrderAllocListRet listbox = (SessionRetrievalOrderAllocListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM710778
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710779
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
		//#CM710780
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM710781
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM710782
				// Close the statement. 
				finder.close();
			}
			//#CM710783
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM710784
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM710785
		// Return to the parent screen. 
		parentRedirect(null);
	}
	//#CM710786
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OrderRtrivlAllocWorkList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM710787
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OrderRtrivlAllocWorkList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM710788
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OrderRtrivlAllocWorkList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM710789
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OrderRtrivlAllocWorkList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM710790
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OrderRtrivlAllocWorkList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710791
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OrderRtrivlAllocWorkList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM710792
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OrderRtrivlAllocWorkList_Click(ActionEvent e) throws Exception
	{
	}
	
	//#CM710793
	// Package methods -----------------------------------------------

	//#CM710794
	// Protected methods ---------------------------------------------

	//#CM710795
	// Private methods -----------------------------------------------
	
	//#CM710796
	/**
	 * Allow this method to change a page.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(SessionRetrievalOrderAllocListRet listbox, String actionName) throws Exception
	{
		//#CM710797
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM710798
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM710799
			// Set the Consignor name as a search condition. 
			lbl_JavaSetCnsgnrNm.setText(rsparam[0].getConsignorName());

			//#CM710800
			// Set a value for Pager. 
			//#CM710801
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM710802
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM710803
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM710804
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM710805
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM710806
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM710807
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM710808
			// Delete all lines. 
			lst_OrderRtrivlAllocWorkList.clearRow();

			//#CM710809
			// Customer Name
			String label_customername = DisplayText.getText("LBL-W0036");

			for (int i = 0; i < len; i++)
			{
				//#CM710810
				// Obtain the end line. 
				int count = lst_OrderRtrivlAllocWorkList.getMaxRows();
				//#CM710811
				// Add a line. 
				lst_OrderRtrivlAllocWorkList.addRow();

				//#CM710812
				// Move to the end line. 
				lst_OrderRtrivlAllocWorkList.setCurrentRow(count);
				
				//#CM710813
				// Order No.
				lst_OrderRtrivlAllocWorkList.setValue(1, rsparam[i].getOrderNo());
				//#CM710814
				// Case/Piece division 
				lst_OrderRtrivlAllocWorkList.setValue(2, rsparam[i].getCasePieceflgName());
				//#CM710815
				// Customer Code
				lst_OrderRtrivlAllocWorkList.setValue(3, rsparam[i].getCustomerCode());		
				//#CM710816
				// Customer Name
				lst_OrderRtrivlAllocWorkList.setValue(4, rsparam[i].getCustomerName());

				//#CM710817
				// Compile the ToolTip data. 
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM710818
				// Customer Name
				toolTip.add(label_customername, rsparam[i].getCustomerName());

				//#CM710819
				// Set the TOOL TIP in the current line. 
				lst_OrderRtrivlAllocWorkList.setToolTip(lst_OrderRtrivlAllocWorkList.getCurrentRow(), toolTip.getText());
			}
		}
		else
		{
			//#CM710820
			// Set a value for Pager. 
			//#CM710821
			// Max count 
			pgr_U.setMax(0);
			//#CM710822
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM710823
			// Start Position 
			pgr_U.setIndex(0);
			//#CM710824
			// Max count 
			pgr_D.setMax(0);
			//#CM710825
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM710826
			// Start Position 
			pgr_D.setIndex(0);

			//#CM710827
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM710828
			// Hide the header. 
			lst_OrderRtrivlAllocWorkList.setVisible(false);
			//#CM710829
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM710830
	// Event handler methods -----------------------------------------
}
//#CM710831
//end of class
