// $Id: ListNoPlanStorageDateBusiness.java,v 1.2 2006/10/04 05:05:41 suresh Exp $

//#CM4596
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.listnoplanstoragedate;
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

//#CM4597
/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * This is storage date (for Unplanned work) search listbox class.<BR>
 * Search for the data using Consignor code and Storage date entered via parent screen as a key.<BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * 1.Initial screen(<CODE>page_Load(ActionEvent e)</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Consignor code and Storage date entered via parent screen as a key<BR>
 * <BR>
 * </DIR>
 * 2.The button on the line selected(<CODE>lst_ListStorageDate_Click</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	Pass the storage date of the selected line to the parent screen and close the listbox.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/12</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:05:41 $
 * @author  $Author: suresh $
 */
public class ListNoPlanStorageDateBusiness extends ListNoPlanStorageDate implements WMSConstants
{
	//#CM4598
	// Class fields --------------------------------------------------
	//#CM4599
	/** 
	 * Use this key to pass the Start storage date.
	 */
	public static final String STARTSTORAGEDATE_KEY = "STARTSTORAGEDATE_KEY";

	//#CM4600
	/** 
	 * Use this key to pass the End storage date.
	 */
	public static final String ENDSTORAGEDATE_KEY = "ENDSTORAGEDATE_KEY";

	//#CM4601
	/** 
	 * Use this key to pass the storage date range flag.
	 */
	public static final String RANGESTORAGEDATE_KEY = "RANGESTORAGEDATE_KEY";

	//#CM4602
	/** 
	 * Use this key to pass the search flag.
	 */
	public static final String SEARCHDATE_KEY = "SEARCHDATE_KEY";	
	
	//#CM4603
	// Class variables -----------------------------------------------

	//#CM4604
	// Class method --------------------------------------------------

	//#CM4605
	// Constructors --------------------------------------------------

	//#CM4606
	// Public methods ------------------------------------------------

	//#CM4607
	/**
	 * Initialize the screen <BR>
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *		Selection <BR>
	 *		Storage Date <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM4608
		// Obtain the locale.
		Locale locale = this.getHttpRequest().getLocale();

		//#CM4609
		// Set the screen name
		//#CM4610
		// Search Storage Date
		lbl_ListName.setText(DisplayText.getText("TLE-W0087"));
		//#CM4611
		// Obtain parameter
		//#CM4612
		// Consignor code
		String consignorcode = request.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM4613
		// Start Storage Date
		String startstoragedate = request.getParameter(STARTSTORAGEDATE_KEY);
		//#CM4614
		// End Storage Date
		String endstoragedate = request.getParameter(ENDSTORAGEDATE_KEY);
		//#CM4615
		// Storage Date area flag
		String rangestoragedate = request.getParameter(RANGESTORAGEDATE_KEY);
		//#CM4616
		// Search flag
		String searchdate = request.getParameter(SEARCHDATE_KEY);

		viewState.setString(RANGESTORAGEDATE_KEY, rangestoragedate);

		//#CM4617
		// Close the connection of the object remained at the Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM4618
			// Close the connection
			sRet.closeConnection();
			//#CM4619
			// Delete from the Session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM4620
		// Obtain the connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM4621
		// Set the parameter.
		StockControlParameter param = new StockControlParameter();
		//#CM4622
		// Consignor code
		param.setConsignorCode(consignorcode);
		//#CM4623
		// Start Storage Date
		param.setFromWorkDate(startstoragedate);
		//#CM4624
		// End Storage Date
		param.setToWorkDate(endstoragedate);
		//#CM4625
		// Search flag
		param.setSearchFlag(searchdate);
		
		//#CM4626
		// Generate SessionStorageDateRet instance.
		SessionNoPlanDateRet listbox = new SessionNoPlanDateRet(conn, param);
		//#CM4627
		// Store listbox to the Session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM4628
	// Package methods -----------------------------------------------

	//#CM4629
	// Protected methods ---------------------------------------------

	//#CM4630
	// Private methods -----------------------------------------------
	//#CM4631
	/**
	 * Method to change the page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Reports all the exceptions.
	 */
	private void setList(SessionNoPlanDateRet listbox, String actionName) throws Exception
	{
		//#CM4632
		// Obtain the locale.
		Locale locale = this.getHttpRequest().getLocale();

		//#CM4633
		// Set the Page info.
		listbox.setActionName(actionName);

		//#CM4634
		// Obtain search result
		ResultView[] result = listbox.getEntities();
		int len = 0;
		if (result != null)
			len = result.length;
		if (len > 0)
		{
			//#CM4635
			// Set the value for the Pager.
			//#CM4636
			// Maximum Count
			pgr_U.setMax(listbox.getLength());
			//#CM4637
			// Display Counts per 1 page
			pgr_U.setPage(listbox.getCondition());
			//#CM4638
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM4639
			// Maximum Count
			pgr_D.setMax(listbox.getLength());
			//#CM4640
			// Display Counts per 1 page
			pgr_D.setPage(listbox.getCondition());
			//#CM4641
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM4642
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM4643
			// Delete all lines.
			lst_ListStorageDate.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM4644
				// Obtain the tailing line.
				int count = lst_ListStorageDate.getMaxRows();
				//#CM4645
				// Add line
				lst_ListStorageDate.addRow();

				//#CM4646
				// Move to the end line.
				lst_ListStorageDate.setCurrentRow(count);
				lst_ListStorageDate.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListStorageDate.setValue(2, WmsFormatter.toDispDate(result[i].getWorkDate(), locale));
			}
		}
		else
		{
			//#CM4647
			// Set the value for the Pager.
			//#CM4648
			// Maximum Count
			pgr_U.setMax(0);
			//#CM4649
			// Display Counts per 1 page
			pgr_U.setPage(0);
			//#CM4650
			// Start Position
			pgr_U.setIndex(0);
			//#CM4651
			// Maximum Count
			pgr_D.setMax(0);
			//#CM4652
			// Display Counts per 1 page
			pgr_D.setPage(0);
			//#CM4653
			// Start Position
			pgr_D.setIndex(0);

			//#CM4654
			// Execute search result count check
			String errorMsg = listbox.checkLength();
			//#CM4655
			// Hide the header.
			lst_ListStorageDate.setVisible(false);
			//#CM4656
			// Error message display
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM4657
	// Event handler methods -----------------------------------------
	//#CM4658
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4659
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4660
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

	//#CM4661
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

	//#CM4662
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

	//#CM4663
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

	//#CM4664
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

	//#CM4665
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4666
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM4667
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM4668
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageDate_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM4669
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageDate_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM4670
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4671
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM4672
	/** 
	 * Clicking Select list cell button executes its process. <BR>
	 * <BR>
	 *	Pass the storage date to the parent screen and close the listbox. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void lst_ListStorageDate_Click(ActionEvent e) throws Exception
	{
		//#CM4673
		// Obtain storage date range flag.
		String flug = viewState.getString(RANGESTORAGEDATE_KEY);

		//#CM4674
		// Set the current line.
		lst_ListStorageDate.setCurrentRow(lst_ListStorageDate.getActiveRow());
		lst_ListStorageDate.getValue(1);

		//#CM4675
		// Set the parameter needed to return to the parent screen.
		ForwardParameters param = new ForwardParameters();
		if (flug == null)
		{
			//#CM4676
			// Disable to search for storage date.
		}
		else if (flug.equals(StockControlParameter.RANGE_START))
		{
			//#CM4677
			// Start Storage Date
			param.setParameter(STARTSTORAGEDATE_KEY, lst_ListStorageDate.getValue(2));
		}
		else if (flug.equals(StockControlParameter.RANGE_END))
		{
			//#CM4678
			// End Storage Date
			param.setParameter(ENDSTORAGEDATE_KEY, lst_ListStorageDate.getValue(2));
		}
		//#CM4679
		// shift to the parent screen.
		parentRedirect(param);
	}

	//#CM4680
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
		//#CM4681
		// Store listbox to the Session
		SessionNoPlanDateRet listbox = (SessionNoPlanDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM4682
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
		//#CM4683
		// Store listbox to the Session
		SessionNoPlanDateRet listbox = (SessionNoPlanDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM4684
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
		//#CM4685
		// Store listbox to the Session
		SessionNoPlanDateRet listbox = (SessionNoPlanDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM4686
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
		//#CM4687
		// Store listbox to the Session
		SessionNoPlanDateRet listbox = (SessionNoPlanDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM4688
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4689
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
		//#CM4690
		// Store listbox to the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM4691
		// When value exists in the Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM4692
				// Close the statement
				finder.close();
			}
			//#CM4693
			// Close the connection
			sessionret.closeConnection();
		}
		//#CM4694
		// Delete from the Session
		this.getSession().removeAttribute("LISTBOX");
		//#CM4695
		// Return to the parent screen.
		parentRedirect(null);
	}


}
//#CM4696
//end of class
