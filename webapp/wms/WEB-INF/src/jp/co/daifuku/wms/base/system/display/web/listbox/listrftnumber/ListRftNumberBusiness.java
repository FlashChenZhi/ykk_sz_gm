// $Id: ListRftNumberBusiness.java,v 1.2 2006/11/13 08:20:24 suresh Exp $

//#CM692542
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.listbox.listrftnumber;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.system.display.web.listbox.sessionret.SessionRftNumberRet;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM692543
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * This listbox class searches for RFT machine No.<BR>
 * Search for the data using the RFT Machine No. entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method)<BR>
 * <BR>
 * <DIR>
 * 	Search for the data using the RFT Machine No. entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * 2.Button for the selected line(<CODE>lst_RftSearch_Click</CODE>method)<BR>
 * <BR>
 * <DIR>
 * 	Pass the RFT Machine No. of the selected line to the parent screen and close the listbox.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/17</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:20:24 $
 * @author  $Author: suresh $
 */
public class ListRftNumberBusiness extends ListRftNumber implements WMSConstants
{
	//#CM692544
	// Class fields --------------------------------------------------
	//#CM692545
	/** 
	 * Use this key to pass RFT Machine No.
	 */
	public static final String RFTNUMBER_KEY = "RFTNUMBER_KEY";
	
	//#CM692546
	// Class variables -----------------------------------------------

	//#CM692547
	// Class method --------------------------------------------------

	//#CM692548
	// Constructors --------------------------------------------------

	//#CM692549
	// Public methods ------------------------------------------------

	//#CM692550
	/**
	 * Initialize the screen. <BR>
	 * <DIR>
	 *	Field item <BR>
	 *	<DIR>
	 *		Selected <BR>
	 *		RFT Machine No.. <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM692551
		// Set the screen name.
		//#CM692552
		// Search Consignor
		lbl_ListName.setText(DisplayText.getText("TLE-W0001"));

		//#CM692553
		// Obtain the parameter.
		String rftnumber = request.getParameter(RFTNUMBER_KEY);

		//#CM692554
		// Close the connection of object remained in the Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM692555
			// Close the connection.
			sRet.closeConnection();
			//#CM692556
			// Delete from the session.
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM692557
		// Obtain the connection.
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM692558
		// Set the parameter.
		SystemParameter param = new SystemParameter();
		param.setRftNo(rftnumber);

		//#CM692559
		// Generate a SessionRftNumberRet instance
		SessionRftNumberRet listbox = new SessionRftNumberRet(conn, param);
		
		//#CM692560
		// Maintain the list box in the Session
		this.getSession().setAttribute("LISTBOX", listbox);
		
		//#CM692561
		// Display the search result.
		setList(listbox, "first");
	}

	//#CM692562
	// Package methods -----------------------------------------------

	//#CM692563
	// Protected methods ---------------------------------------------

	//#CM692564
	// Private methods -----------------------------------------------

	//#CM692565
	/**
	 * Allow this method to change a page. <BR>
	 * @param listbox Session of this listbox
	 * @param actionName actionName for a clicked button
	 * @throws Exception Report all exceptions.
	 */
	private void setList(SessionRftNumberRet listbox, String actionName) throws Exception
	{
		//#CM692566
		// Set the Set the Page info.
		listbox.setActionName(actionName);

		//#CM692567
		// Obtain the search result.
		Rft[] rft = listbox.getEntities();
		int len = 0;
		if (rft != null)
			len = rft.length;
		if (len > 0)
		{
			//#CM692568
			// Set a value for Pager.
			//#CM692569
			// Max count
			pgr_U.setMax(listbox.getLength());
			//#CM692570
			// Count of displayed data per Page
			pgr_U.setPage(listbox.getCondition());
			//#CM692571
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM692572
			// Max count
			pgr_D.setMax(listbox.getLength());
			//#CM692573
			// Count of displayed data per Page
			pgr_D.setPage(listbox.getCondition());
			//#CM692574
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);
			
			//#CM692575
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM692576
			// Delete all lines.
			lst_RftSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM692577
				// Obtain the end line.
				int count = lst_RftSearch.getMaxRows();
				//#CM692578
				// Add a line.
				lst_RftSearch.addRow();

				//#CM692579
				// Move to the end line.
				lst_RftSearch.setCurrentRow(count);
				lst_RftSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_RftSearch.setValue(2, rft[i].getRftNo());
			}
		}
		else
		{
			//#CM692580
			// Set a value for Pager.
			//#CM692581
			// Max count
			pgr_U.setMax(0);
			//#CM692582
			// Count of displayed data per Page
			pgr_U.setPage(0);
			//#CM692583
			// Start Position
			pgr_U.setIndex(0);
			//#CM692584
			// Max count
			pgr_D.setMax(0);
			//#CM692585
			// Count of displayed data per Page
			pgr_D.setPage(0);
			//#CM692586
			// Start Position
			pgr_D.setIndex(0);

			//#CM692587
			// Hide the header.
			lst_RftSearch.setVisible(false);
			//#CM692588
			// MSG-9016 = No target data was found.
			lbl_InMsg.setResourceKey("MSG-9016");
		}
	}

	//#CM692589
	// Event handler methods -----------------------------------------
	//#CM692590
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692591
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692592
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

	//#CM692593
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

	//#CM692594
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

	//#CM692595
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

	//#CM692596
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

	//#CM692597
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692598
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RftSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692599
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RftSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692600
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RftSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM692601
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RftSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM692602
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RftSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692603
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RftSearch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692604
	/** 
	 * Execute the process defined for clicking on Select List Cell button. <BR>
	 * <BR>
	 *	Pass the Worker Code to the parent screen and close the listbox. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void lst_RftSearch_Click(ActionEvent e) throws Exception
	{
		//#CM692605
		// Set the current line.
		lst_RftSearch.setCurrentRow(lst_RftSearch.getActiveRow());
		lst_RftSearch.getValue(1);

		//#CM692606
		// Set the Set the parameter needed to return to the parent screen.
		ForwardParameters param = new ForwardParameters();
		//#CM692607
		// RFT Machine No..
		param.setParameter(RFTNUMBER_KEY, lst_RftSearch.getValue(2));

		//#CM692608
		// Shift to the parent screen.
		parentRedirect(param);
	}

	//#CM692609
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
		//#CM692610
		// Maintain the list box in the Session
		SessionRftNumberRet listbox = (SessionRftNumberRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM692611
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
		//#CM692612
		// Maintain the list box in the Session
		SessionRftNumberRet listbox = (SessionRftNumberRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM692613
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
		//#CM692614
		// Maintain the list box in the Session
		SessionRftNumberRet listbox = (SessionRftNumberRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM692615
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
		//#CM692616
		// Maintain the list box in the Session
		SessionRftNumberRet listbox = (SessionRftNumberRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM692617
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692618
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
		//#CM692619
		// Maintain the list box in the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM692620
		// If there is any value in the Session:
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM692621
				// Close the statement.
				finder.close();
			}
			//#CM692622
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM692623
		// Delete from the session.
		this.getSession().removeAttribute("LISTBOX");
		//#CM692624
		// Return to the parent screen.
		parentRedirect(null);
	}
}
//#CM692625
//end of class
