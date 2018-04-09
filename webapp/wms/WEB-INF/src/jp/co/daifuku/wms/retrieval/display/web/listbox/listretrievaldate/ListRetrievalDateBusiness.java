// $Id: ListRetrievalDateBusiness.java,v 1.2 2007/02/07 04:18:44 suresh Exp $

//#CM709748
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievaldate;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp
	.co
	.daifuku
	.wms
	.retrieval
	.display
	.web
	.listbox
	.listretrievalconsignor
	.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalResultDateRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM709749
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this listbox class to search for Picking acceptance date. <BR>
 * Search for the databased on Picking Date entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	 Search for the data using Picking Date entered via parent screen as a key, and display it on the screen. <BR>
 * <BR>
 * </DIR>
 * 2. Button for the selected line (<CODE>lst_ShpAcpDateSrch_Click</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	 Pass the picking Date on the selected line to the parent screen and close the listbox. <BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/21</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:44 $
 * @author  $Author: suresh $
 */
public class ListRetrievalDateBusiness extends ListRetrievalDate implements WMSConstants
{
	//#CM709750
	// Class fields --------------------------------------------------
	//#CM709751
	/** 
	 * Use this key to pass the Start picking date. 
	 */
	public static final String STARTRETRIEVALDATE_KEY = "STARTRETRIEVALDATE_KEY";

	//#CM709752
	/** 
	 * Use this key to pass the End picking date. 
	 */
	public static final String ENDRETRIEVALDATE_KEY = "ENDRETRIEVALDATE_KEY";

	//#CM709753
	/** 
	 * Use this key to pass the picking date range flag. 
	 */
	public static final String RANGERETRIEVALDATE_KEY = "RANGERETRIEVALDATE_KEY";
	
	//#CM709754
	/** 
	 * Use this key to distinguish the data for Order/Item 
	 */
	public static final String ORDER_ITEM_FLAG = "ORDER_ITEM_FLAG";

	//#CM709755
	// Class variables -----------------------------------------------

	//#CM709756
	// Class method --------------------------------------------------

	//#CM709757
	// Constructors --------------------------------------------------

	//#CM709758
	// Public methods ------------------------------------------------

	//#CM709759
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Field item  <BR>
	 *	<DIR>
	 *		 Select <BR>
	 *		Picking Date <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM709760
		// Set the screen name. 
		//#CM709761
		// Search Picking Date 
		lbl_ListName.setText(DisplayText.getText("TLE-W0074"));

		//#CM709762
		// Obtain the parameter. 
		//#CM709763
		// Consignor Code
		String consignorcode =
			request.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM709764
		// Start Picking Date
		String startretrievldate = request.getParameter(STARTRETRIEVALDATE_KEY);
		//#CM709765
		// End Picking Date
		String endretrievldate = request.getParameter(ENDRETRIEVALDATE_KEY);
		//#CM709766
		// Order/Item 
		String orderitem = request.getParameter(ORDER_ITEM_FLAG);
		//#CM709767
		// Picking date range flag 
		String rangeretrievldate = request.getParameter(RANGERETRIEVALDATE_KEY);

		viewState.setString(RANGERETRIEVALDATE_KEY, rangeretrievldate);

		//#CM709768
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM709769
			// Close the connection. 
			sRet.closeConnection();
			//#CM709770
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM709771
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM709772
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM709773
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM709774
		// Start Picking Date
		param.setFromRetrievalDate(startretrievldate);
		//#CM709775
		// End Picking Date
		param.setToRetrievalDate(endretrievldate);
		//#CM709776
		// Order/Item 
		param.setItemOrderFlag(orderitem);
		//#CM709777
		// Generate the SessionRetrievalResultDateRet instance. 
		SessionRetrievalResultDateRet listbox = new SessionRetrievalResultDateRet(conn, param);
		//#CM709778
		// Maintain the list box in the Session 
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM709779
	// Package methods -----------------------------------------------

	//#CM709780
	// Protected methods ---------------------------------------------

	//#CM709781
	// Private methods -----------------------------------------------
	//#CM709782
	/**
	 * Allow this method to change a page.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(SessionRetrievalResultDateRet listbox, String actionName) throws Exception
	{
		//#CM709783
		// Obtain locale.
		Locale locale = this.getHttpRequest().getLocale();

		//#CM709784
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM709785
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM709786
			// Set a value for Pager. 
			//#CM709787
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM709788
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM709789
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM709790
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM709791
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM709792
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM709793
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM709794
			// Delete all lines. 
			lst_RtrivlDateSrch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM709795
				// Obtain the end line. 
				int count = lst_RtrivlDateSrch.getMaxRows();
				//#CM709796
				// Add a line. 
				lst_RtrivlDateSrch.addRow();

				//#CM709797
				// Move to the end line. 
				lst_RtrivlDateSrch.setCurrentRow(count);
				lst_RtrivlDateSrch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_RtrivlDateSrch.setValue(
					2,
					WmsFormatter.toDispDate(rsparam[i].getWorkDate(), locale));
			}
		}
		else
		{
			//#CM709798
			// Set a value for Pager. 
			//#CM709799
			// Max count 
			pgr_U.setMax(0);
			//#CM709800
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM709801
			// Start Position 
			pgr_U.setIndex(0);
			//#CM709802
			// Max count 
			pgr_D.setMax(0);
			//#CM709803
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM709804
			// Start Position 
			pgr_D.setIndex(0);

			//#CM709805
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM709806
			// Hide the header. 
			lst_RtrivlDateSrch.setVisible(false);
			//#CM709807
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM709808
	// Event handler methods -----------------------------------------
	//#CM709809
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709810
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709811
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

	//#CM709812
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

	//#CM709813
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

	//#CM709814
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

	//#CM709815
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

	//#CM709816
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709817
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlDateSrch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM709818
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlDateSrch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM709819
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlDateSrch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM709820
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlDateSrch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM709821
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlDateSrch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709822
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlDateSrch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM709823
	/** 
	 * Clicking on the Select button of listcell executes its proper process.  <BR>
	 * <BR>
	 *	 Pass the picking Date to the parent screen and close the listbox.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lst_RtrivlDateSrch_Click(ActionEvent e) throws Exception
	{
		//#CM709824
		// Obtain the picking date range flag. 
		String flug = viewState.getString(RANGERETRIEVALDATE_KEY);

		//#CM709825
		// Set the current line. 
		lst_RtrivlDateSrch.setCurrentRow(lst_RtrivlDateSrch.getActiveRow());
		lst_RtrivlDateSrch.getValue(1);

		//#CM709826
		// Set the parameter needed to return to the parent screen. 
		ForwardParameters param = new ForwardParameters();
		if (flug == null)
		{
			//#CM709827
			// Disable to search for picking date. 
		}
		else if (flug.equals(RetrievalSupportParameter.RANGE_START))
		{
			//#CM709828
			// Start Picking Date
			param.setParameter(STARTRETRIEVALDATE_KEY, lst_RtrivlDateSrch.getValue(2));
		}
		else if (flug.equals(RetrievalSupportParameter.RANGE_END))
		{
			//#CM709829
			// End Picking Date
			param.setParameter(ENDRETRIEVALDATE_KEY, lst_RtrivlDateSrch.getValue(2));
		}
		//#CM709830
		// Shift to the parent screen. 
		parentRedirect(param);
	}

	//#CM709831
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
		//#CM709832
		// Maintain the list box in the Session 
		SessionRetrievalResultDateRet listbox =
			(SessionRetrievalResultDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM709833
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
		//#CM709834
		// Maintain the list box in the Session 
		SessionRetrievalResultDateRet listbox =
			(SessionRetrievalResultDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM709835
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
		//#CM709836
		// Maintain the list box in the Session 
		SessionRetrievalResultDateRet listbox =
			(SessionRetrievalResultDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM709837
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
		//#CM709838
		// Maintain the list box in the Session 
		SessionRetrievalResultDateRet listbox =
			(SessionRetrievalResultDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM709839
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709840
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
		//#CM709841
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM709842
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM709843
				// Close the statement. 
				finder.close();
			}
			//#CM709844
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM709845
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM709846
		// Return to the parent screen. 
		parentRedirect(null);
	}
}
//#CM709847
//end of class
