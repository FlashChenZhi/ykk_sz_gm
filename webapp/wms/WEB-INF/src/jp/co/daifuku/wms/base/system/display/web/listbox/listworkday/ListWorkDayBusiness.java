// $Id: ListWorkDayBusiness.java,v 1.2 2006/11/13 08:20:38 suresh Exp $

//#CM692763
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.listbox.listworkday;
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
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.system.display.web.listbox.sessionret.SessionWorkDayRet;
import jp.co.daifuku.wms.base.system.display.web.workerqtyinquiry.WorkerQtyInquiryBusiness;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM692764
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this class to provide a Listbox for searching the "Work date".<BR>
 * Search for the data using the Work Date entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method)<BR>
 * <BR>
 * <DIR>
 * 	Search for the data using the Work Date entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * 2.Button for the selected line(<CODE>lst_WorkDateSrch_Click</CODE>method)<BR>
 * <BR>
 * <DIR>
 * 	Pass the Work Date of the selected line to the parent screen and close the listbox.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/17</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:20:38 $
 * @author  $Author: suresh $
 */
public class ListWorkDayBusiness extends ListWorkDay implements WMSConstants
{
	//#CM692765
	// Class fields --------------------------------------------------
	//#CM692766
	/** 
	 * Use this key to pass Work Date.
	 */
	public static final String WORKDAY_KEY = "WORKDAY_KEY";

	//#CM692767
	/** 
	 * Use this key to pass Start Work Date.
	 */
	public static final String STARTWORKDAY_KEY = "STARTWORKDAY_KEY";

	//#CM692768
	/** 
	 * Use this key to pass End Work Date.
	 */
	public static final String ENDWORKDAY_KEY = "ENDWORKDAY_KEY";

	//#CM692769
	/** 
	 * Use this key to pass Work Date Range flag.
	 */
	public static final String RANGEWORKDAY_KEY = "RANGEWORKDAY_KEY";

	//#CM692770
	// Class variables -----------------------------------------------

	//#CM692771
	// Class method --------------------------------------------------

	//#CM692772
	// Constructors --------------------------------------------------

	//#CM692773
	// Public methods ------------------------------------------------

	//#CM692774
	/**
	 * Initialize the screen. <BR>
	 * <DIR>
	 *	Field item <BR>
	 *	<DIR>
	 *		Selected <BR>
	 *		Work date <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM692775
		// Set the screen name.
		//#CM692776
		// Search for the work date.
		lbl_ListName.setText(DisplayText.getText("TLE-W0019"));

		//#CM692777
		// Obtain the parameter.
		//#CM692778
		// Work date
		String workday = request.getParameter(WORKDAY_KEY);
		//#CM692779
		// Start work date
		String startworkday = request.getParameter(STARTWORKDAY_KEY);
		//#CM692780
		// End Work Date
		String endworkday = request.getParameter(ENDWORKDAY_KEY);
		//#CM692781
		// Work Date Range flag
		String rangeworkday = request.getParameter(RANGEWORKDAY_KEY);
		//#CM692782
		// Work type
		String workdetails = request.getParameter(WorkerQtyInquiryBusiness.WORKDETAILS_KEY);

		viewState.setString(RANGEWORKDAY_KEY, rangeworkday);

		//#CM692783
		//Close the connection of object remained in the Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM692784
			//Close the connection.
			sRet.closeConnection();
			//#CM692785
			//Delete from the session.
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM692786
		//Obtain the connection.
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM692787
		// Set the parameter.
		SystemParameter param = new SystemParameter();
		//#CM692788
		// Work date
		param.setWorkDate(workday);
		//#CM692789
		// Start work date
		param.setFromWorkDate(startworkday);
		//#CM692790
		// End Work Date
		param.setToWorkDate(endworkday);
		//#CM692791
		// Work type
		param.setSelectWorkDetail(workdetails);

		//#CM692792
		//Generate a SessionWorkDayRet instance
		SessionWorkDayRet listbox = new SessionWorkDayRet(conn, param);
		//#CM692793
		//Maintain the list box in the Session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM692794
	// Package methods -----------------------------------------------

	//#CM692795
	// Protected methods ---------------------------------------------

	//#CM692796
	// Private methods -----------------------------------------------

	//#CM692797
	/**
	 * Allow this method to change a page. <BR>
	 * @param listbox Session of this listbox
	 * @param actionName actionName for a clicked button
	 * @throws Exception Report all exceptions.
	 */
	private void setList(SessionWorkDayRet listbox, String actionName) throws Exception
	{
		//#CM692798
		// Obtain the locale.
		Locale locale = this.getHttpRequest().getLocale();

		//#CM692799
		// Set the Set the Page info.
		listbox.setActionName(actionName);

		//#CM692800
		// Obtain the search result.
		WorkerResult[] workerResult = listbox.getEntities();
		int len = 0;
		if (workerResult != null)
			len = workerResult.length;
		if (len > 0)
		{
			//#CM692801
			// Set a value for Pager.
			//#CM692802
			// Max count
			pgr_U.setMax(listbox.getLength());
			//#CM692803
			// Count of displayed data per Page
			pgr_U.setPage(listbox.getCondition());
			//#CM692804
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM692805
			// Max count
			pgr_D.setMax(listbox.getLength());
			//#CM692806
			// Count of displayed data per Page
			pgr_D.setPage(listbox.getCondition());
			//#CM692807
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM692808
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM692809
			// Delete all lines.
			lst_WorkDateSrch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM692810
				// Obtain the end line.
				int count = lst_WorkDateSrch.getMaxRows();
				//#CM692811
				// Add a line.
				lst_WorkDateSrch.addRow();

				//#CM692812
				// Move to the end line.
				lst_WorkDateSrch.setCurrentRow(count);
				lst_WorkDateSrch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_WorkDateSrch.setValue(2, WmsFormatter.toDispDate(workerResult[i].getWorkDate(), locale));
			}
		}
		else
		{
			//#CM692813
			// Set a value for Pager.
			//#CM692814
			// Max count
			pgr_U.setMax(0);
			//#CM692815
			// Count of displayed data per Page
			pgr_U.setPage(0);
			//#CM692816
			// Start Position
			pgr_U.setIndex(0);
			//#CM692817
			// Max count
			pgr_D.setMax(0);
			//#CM692818
			// Count of displayed data per Page
			pgr_D.setPage(0);
			//#CM692819
			// Start Position
			pgr_D.setIndex(0);

			//#CM692820
			// Check the count of the search results.
			String errorMsg = listbox.checkLength();
			//#CM692821
			// Hide the header.
			lst_WorkDateSrch.setVisible(false);
			//#CM692822
			// Display the error message.
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM692823
	// Event handler methods -----------------------------------------

	//#CM692824
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692825
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692826
	/** 
	 * Clicking on "Close" button executes its process. <BR>
	 *  <BR>
	 * Close the listbox and shift to the parent screen. <BR>
	 *  <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		btn_Close_D_Click(e);
	}

	//#CM692827
	/** 
	 * Execute the process defined for clicking on ">" button. <BR>
	 * <BR>
	 * Display the next page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	//#CM692828
	/** 
	 * Execute the process defined for clicking on "<" button. <BR>
	 * <BR>
	 * Display the previous one page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
		pgr_D_Prev(e);
	}

	//#CM692829
	/** 
	 * Clicking ">>" button. <BR>
	 * <BR>
	 * Display the end page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		pgr_D_Last(e);
	}

	//#CM692830
	/** 
	 * Execute the process defined for clicking on "<<" button. <BR>
	 * <BR>
	 * Display the top page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		pgr_D_First(e);
	}

	//#CM692831
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692832
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkDateSrch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692833
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkDateSrch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692834
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkDateSrch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM692835
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkDateSrch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM692836
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkDateSrch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692837
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkDateSrch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692838
	/** 
	 * Execute the process defined for clicking on Select List Cell button. <BR>
	 * <BR>
	 *	Pass the Work Date to the parent screen and close the listbox. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void lst_WorkDateSrch_Click(ActionEvent e) throws Exception
	{
		//#CM692839
		// Obtain the Range flag of planned shipping date.
		String flug = viewState.getString(RANGEWORKDAY_KEY);

		//#CM692840
		// Set the current line.
		lst_WorkDateSrch.setCurrentRow(lst_WorkDateSrch.getActiveRow());
		lst_WorkDateSrch.getValue(1);

		//#CM692841
		// Set the Set the parameter needed to return to the parent screen.
		ForwardParameters param = new ForwardParameters();
		if (flug == null)
		{
			//#CM692842
			// Work date
			param.setParameter(WORKDAY_KEY, lst_WorkDateSrch.getValue(2));
		}
		else if (flug.equals(SystemParameter.RANGE_START))
		{
			//#CM692843
			// Start work date
			param.setParameter(STARTWORKDAY_KEY, lst_WorkDateSrch.getValue(2));
		}
		else if (flug.equals(SystemParameter.RANGE_END))
		{
			//#CM692844
			// End Work Date
			param.setParameter(ENDWORKDAY_KEY, lst_WorkDateSrch.getValue(2));
		}
		//#CM692845
		// Shift to the parent screen.
		parentRedirect(param);
	}

	//#CM692846
	/** 
	 * Execute the process defined for clicking on ">" button. <BR>
	 * <BR>
	 * Display the next page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		//#CM692847
		// Maintain the list box in the Session
		SessionWorkDayRet listbox = (SessionWorkDayRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM692848
	/** 
	 * Execute the process defined for clicking on "<" button. <BR>
	 * <BR>
	 * Display the previous one page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		//#CM692849
		// Maintain the list box in the Session
		SessionWorkDayRet listbox = (SessionWorkDayRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM692850
	/** 
	 * Clicking ">>" button. <BR>
	 * <BR>
	 * Display the end page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		//#CM692851
		// Maintain the list box in the Session
		SessionWorkDayRet listbox = (SessionWorkDayRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM692852
	/** 
	 * Execute the process defined for clicking on "<<" button. <BR>
	 * <BR>
	 * Display the top page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM692853
		// Maintain the list box in the Session
		SessionWorkDayRet listbox = (SessionWorkDayRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM692854
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692855
	/** 
	 * Clicking on "Close" button executes its process. <BR>
	 *  <BR>
	 * Close the listbox and shift to the parent screen. <BR>
	 *  <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM692856
		// Maintain the list box in the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM692857
		// If there is any value in the Session:
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM692858
				// Close the statement.
				finder.close();
			}
			//#CM692859
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM692860
		// Delete from the session.
		this.getSession().removeAttribute("LISTBOX");
		//#CM692861
		// Return to the parent screen.
		parentRedirect(null);
	}
}
//#CM692862
//end of class
