// $Id: ListNoPlanRetrievalDateBusiness.java,v 1.2 2006/10/04 05:05:34 suresh Exp $

//#CM4492
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.listnoplanretrievaldate;
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
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret.SessionNoPlanDateRet;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM4493
/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * This is picking date (for Unplanned work) search listbox class.<BR>
 * Search for the data using Consignor code and Picking date entered via parent screen as a key.<BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * 1.Initial screen(<CODE>page_Load(ActionEvent e)</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Picking date entered via parent screen as a key<BR>
 * <BR>
 * </DIR>
 * 2.The button on the line selected(<CODE>lst_RtrivlDateSrch_Click</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	Pass the picking date of the selected line to the parent screen and close the listbox.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/12</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:05:34 $
 * @author  $Author: suresh $
 */
public class ListNoPlanRetrievalDateBusiness extends ListNoPlanRetrievalDate implements WMSConstants
{
	//#CM4494
	// Class fields --------------------------------------------------
	//#CM4495
	/** 
	 * Use this key to pass the start picking date.
	 */
	public static final String STARTRETRIEVALDATE_KEY = "STARTRETRIEVALDATE_KEY";

	//#CM4496
	/** 
	 * Use this key to pass the Close picking date.
	 */
	public static final String ENDRETRIEVALDATE_KEY = "ENDRETRIEVALDATE_KEY";

	//#CM4497
	/** 
	 * Use this key to pass the picking date range flag.
	 */
	public static final String RANGERETRIEVALDATE_KEY = "RANGERETRIEVALDATE_KEY";

	//#CM4498
	/** 
	 * Use this key to pass the search flag.
	 */
	public static final String SEARCHDATE_KEY = "SEARCHDATE_KEY";

	//#CM4499
	// Class variables -----------------------------------------------

	//#CM4500
	// Class method --------------------------------------------------

	//#CM4501
	// Constructors --------------------------------------------------

	//#CM4502
	// Public methods ------------------------------------------------

	//#CM4503
	/**
	 * Initialize the screen <BR>
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *		Selection <BR>
	 *		Picking date <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM4504
		// Set the screen name
		//#CM4505
		// Search picking date search
		lbl_ListName.setText(DisplayText.getText("TLE-W0074"));

		//#CM4506
		// Obtain parameter
		//#CM4507
		// Consignor code
		String consignorcode = request.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM4508
		// Start picking date
		String startretrievldate = request.getParameter(STARTRETRIEVALDATE_KEY);
		//#CM4509
		// End picking date
		String endretrievldate = request.getParameter(ENDRETRIEVALDATE_KEY);
		//#CM4510
		// picking date range flag
		String rangeretrievldate = request.getParameter(RANGERETRIEVALDATE_KEY);
		//#CM4511
		// Search flag
		String searchdate = request.getParameter(SEARCHDATE_KEY);
		
		viewState.setString(RANGERETRIEVALDATE_KEY, rangeretrievldate);

		//#CM4512
		// Close the connection of the object remained at the Session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM4513
			// Close the connection
			sRet.closeConnection();
			//#CM4514
			// Delete from the Session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM4515
		// Obtain the connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM4516
		// Set the parameter.
		StockControlParameter param = new StockControlParameter();
		//#CM4517
		// Consignor code
		param.setConsignorCode(consignorcode);
		//#CM4518
		// Start picking date
		param.setFromWorkDate(startretrievldate);
		//#CM4519
		// End picking date
		param.setToWorkDate(endretrievldate);
		//#CM4520
		// Search flag
		param.setSearchFlag(searchdate);

		//#CM4521
		// Generate SessionNoPlanRetrievalDateRet instance.
		SessionNoPlanDateRet listbox = new SessionNoPlanDateRet(conn, param);
		//#CM4522
		// Store listbox to the Session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM4523
	// Package methods -----------------------------------------------

	//#CM4524
	// Protected methods ---------------------------------------------

	//#CM4525
	// Private methods -----------------------------------------------
	//#CM4526
	/**
	 * Method to change the page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Reports all the exceptions.
	 */
	private void setList(SessionNoPlanDateRet listbox, String actionName) throws Exception
	{
		//#CM4527
		// Obtain the locale.
		Locale locale = this.getHttpRequest().getLocale();

		//#CM4528
		// Set the Page info.
		listbox.setActionName(actionName);

		//#CM4529
		// Obtain search result
		ResultView[] result = listbox.getEntities();
		int len = 0;
		if (result != null)
			len = result.length;
		if (len > 0)
		{
			//#CM4530
			// Set the value for the Pager.
			//#CM4531
			// Maximum Count
			pgr_U.setMax(listbox.getLength());
			//#CM4532
			// Display Counts per 1 page
			pgr_U.setPage(listbox.getCondition());
			//#CM4533
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM4534
			// Maximum Count
			pgr_D.setMax(listbox.getLength());
			//#CM4535
			// Display Counts per 1 page
			pgr_D.setPage(listbox.getCondition());
			//#CM4536
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM4537
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM4538
			// Delete all lines.
			lst_RtrivlDateSrch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM4539
				// Obtain the tailing line.
				int count = lst_RtrivlDateSrch.getMaxRows();
				//#CM4540
				// Add line
				lst_RtrivlDateSrch.addRow();

				//#CM4541
				// Move to the end line.
				lst_RtrivlDateSrch.setCurrentRow(count);
				lst_RtrivlDateSrch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_RtrivlDateSrch.setValue(2, WmsFormatter.toDispDate(result[i].getWorkDate(), locale));
			}
		}
		else
		{
			//#CM4542
			// Set the value for the Pager.
			//#CM4543
			// Maximum Count
			pgr_U.setMax(0);
			//#CM4544
			// Display Counts per 1 page
			pgr_U.setPage(0);
			//#CM4545
			// Start Position
			pgr_U.setIndex(0);
			//#CM4546
			// Maximum Count
			pgr_D.setMax(0);
			//#CM4547
			// Display Counts per 1 page
			pgr_D.setPage(0);
			//#CM4548
			// Start Position
			pgr_D.setIndex(0);

			//#CM4549
			// Execute search result count check
			String errorMsg = listbox.checkLength();
			//#CM4550
			// Hide the header.
			lst_RtrivlDateSrch.setVisible(false);
			//#CM4551
			// Error message display
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM4552
	// Event handler methods -----------------------------------------
	//#CM4553
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4554
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4555
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

	//#CM4556
	/** 
	 * Execute processing when ">" button is pressed down.<BR>
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

	//#CM4557
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

	//#CM4558
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

	//#CM4559
	/** 
	 * Execute processing when "<<" button is pressed down.<BR>
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

	//#CM4560
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4561
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlDateSrch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM4562
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlDateSrch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM4563
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlDateSrch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM4564
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlDateSrch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM4565
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlDateSrch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4566
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlDateSrch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM4567
	/** 
	 * Clicking Select list cell button executes its process. <BR>
	 * <BR>
	 *  Pass the picking date to the parent screen and close the listbox. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void lst_RtrivlDateSrch_Click(ActionEvent e) throws Exception
	{
		//#CM4568
		// Obtain picking date range flag.
		String flug = viewState.getString(RANGERETRIEVALDATE_KEY);

		//#CM4569
		// Set the current line.
		lst_RtrivlDateSrch.setCurrentRow(lst_RtrivlDateSrch.getActiveRow());
		lst_RtrivlDateSrch.getValue(1);

		//#CM4570
		// Set the parameter needed to return to the parent screen.
		ForwardParameters param = new ForwardParameters();
		if (flug == null)
		{
			//#CM4571
			// Disable to search for picking date.
		}
		else if (flug.equals(StockControlParameter.RANGE_START))
		{
			//#CM4572
			// Start picking date
			param.setParameter(STARTRETRIEVALDATE_KEY, lst_RtrivlDateSrch.getValue(2));
		}
		else if (flug.equals(StockControlParameter.RANGE_END))
		{
			//#CM4573
			// End picking date
			param.setParameter(ENDRETRIEVALDATE_KEY, lst_RtrivlDateSrch.getValue(2));
		}
		//#CM4574
		// shift to the parent screen.
		parentRedirect(param);
	}

	//#CM4575
	/** 
	 * Execute processing when ">" button is pressed down.<BR>
	 * <BR>
	 * Display the next page. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		//#CM4576
		// Store listbox to the Session
		SessionNoPlanDateRet listbox =
			(SessionNoPlanDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM4577
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
		//#CM4578
		// Store listbox to the Session
		SessionNoPlanDateRet listbox =
			(SessionNoPlanDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM4579
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
		//#CM4580
		// Store listbox to the Session
		SessionNoPlanDateRet listbox =
			(SessionNoPlanDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM4581
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
		//#CM4582
		// Store listbox to the Session
		SessionNoPlanDateRet listbox =
			(SessionNoPlanDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM4583
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4584
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
		//#CM4585
		// Store listbox to the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM4586
		// When value exists in the Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM4587
				// Close the statement
				finder.close();
			}
			//#CM4588
			// Close the connection
			sessionret.closeConnection();
		}
		//#CM4589
		// Delete from the Session
		this.getSession().removeAttribute("LISTBOX");
		//#CM4590
		// Return to the parent screen.
		parentRedirect(null);
	}


}
//#CM4591
//end of class
