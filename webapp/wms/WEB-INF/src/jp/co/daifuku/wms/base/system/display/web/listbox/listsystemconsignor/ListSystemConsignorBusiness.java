// $Id: ListSystemConsignorBusiness.java,v 1.2 2006/11/13 08:20:31 suresh Exp $

//#CM692630
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.listbox.listsystemconsignor;
import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ShortageInfo;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.system.display.web.listbox.sessionret.SessionSystemShortageInfoConsignorRet;
import jp.co.daifuku.wms.base.system.display.web.listbox.sessionret.SessionSystemStockConsignorRet;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM692631
/**
 * Designer : T.Yosino<BR>
 * Maker : T.Yosino <BR>
 * <BR>
 * This list box class searches for consignor.<BR>
 * Search for the data using Consignor code entered via parent screen as a key.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method)<BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Consignor code entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * 2.Button for the selected line(<CODE>lst_ConsignorSearch_Click</CODE>method)<BR>
 * <BR>
 * <DIR>
 * 	Pass the Consignor code and Consignor name of the selected line to the parent screen and close the listbox.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>T.Yosino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:20:31 $
 * @author  $Author: suresh $
 */
public class ListSystemConsignorBusiness extends ListSystemConsignor implements WMSConstants
{
	//#CM692632
	// Class fields --------------------------------------------------
	//#CM692633
	/** 
	 * Use this key to pass Use this key to pass the Consignor code.
	 */
	public static final String CONSIGNORCODE_KEY = "CONSIGNORCODE_KEY";

	//#CM692634
	/** 
	 * Use this key to pass Use this key to pass the Consignor name.
	 */
	public static final String CONSIGNORNAME_KEY = "CONSIGNORNAME_KEY";
	
	//#CM692635
	/**
	 * Use this key to pass Added Date/Time.
	 */
	public static final String REGISTDATE_KEY = "REGISTDATE_KEY";
		
	//#CM692636
	/** 
	 * Use this key to pass Use this key to pass the search flag.
	 */
	public static final String SEARCHCONSIGNOR_KEY = "SEARCHCONSIGNOR_KEY";
	
	//#CM692637
	// Class variables -----------------------------------------------

	//#CM692638
	// Class method --------------------------------------------------

	//#CM692639
	// Constructors --------------------------------------------------

	//#CM692640
	// Public methods ------------------------------------------------

	//#CM692641
	/**
	 * Initialize the screen.
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM692642
		// Set the screen name.
		//#CM692643
		// TLE-W0012=Search Consignor
		lbl_ListName.setText(DisplayText.getText("TLE-W0012"));

		//#CM692644
		// Obtain the parameter.
		//#CM692645
		// Consignor Code
		String consignorcode = request.getParameter(CONSIGNORCODE_KEY);
		//#CM692646
		// Added Date/Time
		Date registDate = WmsFormatter.getDate(request.getParameter(REGISTDATE_KEY), 0, this.getHttpRequest().getLocale());
		//#CM692647
		// Search flag (designate the search table)
		String searchFlag = request.getParameter(SEARCHCONSIGNOR_KEY);

		//#CM692648
		// Maintain the Search flag in the ViewState.
		this.getViewState().setString(SEARCHCONSIGNOR_KEY, searchFlag);
		
		//#CM692649
		// Close the connection of object remained in the Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM692650
			// Close the connection.
			sRet.closeConnection();
			//#CM692651
			// Delete from the session.
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM692652
		// Obtain the connection.
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM692653
		// Set the parameter.
		SystemParameter param = new SystemParameter();
		//#CM692654
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM692655
		// Added Date/Time
		param.setRegistDate(registDate);
		
		//#CM692656
		// To search the table of inventory information:
		if (searchFlag.equals(SystemParameter.SEARCHFLAG_STOCK))
		{
			//#CM692657
			// Generate a SessionSystemStockConsignorRet instance
			SessionSystemStockConsignorRet listbox = new SessionSystemStockConsignorRet(conn, param);

			//#CM692658
			// Maintain the list box in the Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setList(listbox, "first");
			
		}
		//#CM692659
		// To search the table of shortage info:
		else if (searchFlag.equals(SystemParameter.SEARCHFLAG_SHORTAGE))
		{
			//#CM692660
			// Generate a SessionSystemShortageInfoConsignorRet instance
			SessionSystemShortageInfoConsignorRet listbox = new SessionSystemShortageInfoConsignorRet(conn, param);

			//#CM692661
			// Maintain the list box in the Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setList(listbox, "first");
		
		}
		else
		{
			Object[] tObj = new Object[1];
			//#CM692662
			// LBL-W0024= Consignor
			tObj[0] = DisplayText.getText("LBL-W0024");
			String classname = this.getClass().getName();
			RmiMsgLogClient.write(6007039, LogMessage.F_ERROR, classname, tObj);
			//#CM692663
			// 6007039={0} search failed. See log.
			throw (new InvalidStatusException("6007039" + wDelim + tObj[0]));
		}
	}

	//#CM692664
	// Package methods -----------------------------------------------
	//#CM692665
	/**
	 * Allow this method to change a page. <BR>
	 * Search for the inventory information table. <BR>
	 * @param listbox Session of this listbox
	 * @param actionName actionName for a clicked button
	 * @throws Exception Report all exceptions.
	 */
	private void setList(SessionSystemStockConsignorRet listbox, String actionName) throws Exception
	{
		//#CM692666
		// Set the Set the Page info.
		listbox.setActionName(actionName);

		//#CM692667
		// Obtain the search result.
		Stock[] stock = listbox.getEntities();
		int len = 0;
		if (stock != null)
			len = stock.length;
		if (len > 0)
		{
			//#CM692668
			// Set a value for Pager.
			//#CM692669
			// Max count
			pgr_U.setMax(listbox.getLength());
			//#CM692670
			// Count of displayed data per Page
			pgr_U.setPage(listbox.getCondition());
			//#CM692671
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM692672
			// Max count
			pgr_D.setMax(listbox.getLength());
			//#CM692673
			// Count of displayed data per Page
			pgr_D.setPage(listbox.getCondition());
			//#CM692674
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM692675
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM692676
			// Delete all lines.
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM692677
				// Obtain the end line.
				int count = lst_ConsignorSearch.getMaxRows();
				//#CM692678
				// Add a line.
				lst_ConsignorSearch.addRow();

				//#CM692679
				// Move to the end line.
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, stock[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, stock[i].getConsignorName());
			}
		}
		else
		{
			//#CM692680
			// Set a value for Pager.
			//#CM692681
			// Max count
			pgr_U.setMax(0);
			//#CM692682
			// Count of displayed data per Page
			pgr_U.setPage(0);
			//#CM692683
			// Start Position
			pgr_U.setIndex(0);
			//#CM692684
			// Max count
			pgr_D.setMax(0);
			//#CM692685
			// Count of displayed data per Page
			pgr_D.setPage(0);
			//#CM692686
			// Start Position
			pgr_D.setIndex(0);

			//#CM692687
			// Check the count of the search results.
			String errorMsg = listbox.checkLength();
			//#CM692688
			// Hide the header.
			lst_ConsignorSearch.setVisible(false);
			//#CM692689
			// Display the error message.
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	
	//#CM692690
	/**
	 * Allow this method to change a page. <BR>
	 * Search through the table of shortage info. <BR>
	 * @param listbox Session of this listbox
	 * @param actionName actionName for a clicked button
	 * @throws Exception Report all exceptions.
	 */
	private void setList(SessionSystemShortageInfoConsignorRet listbox, String actionName) throws Exception
	{
		//#CM692691
		// Set the Set the Page info.
		listbox.setActionName(actionName);

		//#CM692692
		// Obtain the search result.
		ShortageInfo[] shortage = listbox.getEntities();
		
		int len = 0;
		if (shortage != null)
			len = shortage.length;
			
		if (len > 0)
		{
			//#CM692693
			// Set a value for Pager.
			//#CM692694
			// Max count
			pgr_U.setMax(listbox.getLength());
			//#CM692695
			// Count of displayed data per Page
			pgr_U.setPage(listbox.getCondition());
			//#CM692696
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM692697
			// Max count
			pgr_D.setMax(listbox.getLength());
			//#CM692698
			// Count of displayed data per Page
			pgr_D.setPage(listbox.getCondition());
			//#CM692699
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM692700
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM692701
			// Delete all lines.
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM692702
				// Obtain the end line.
				int count = lst_ConsignorSearch.getMaxRows();
				//#CM692703
				// Add a line.
				lst_ConsignorSearch.addRow();

				//#CM692704
				// Move to the end line.
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, shortage[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, shortage[i].getConsignorName());
				
			}
		}
		else
		{
			//#CM692705
			// Set a value for Pager.
			//#CM692706
			// Max count
			pgr_U.setMax(0);
			//#CM692707
			// Count of displayed data per Page
			pgr_U.setPage(0);
			//#CM692708
			// Start Position
			pgr_U.setIndex(0);
			//#CM692709
			// Max count
			pgr_D.setMax(0);
			//#CM692710
			// Count of displayed data per Page
			pgr_D.setPage(0);
			//#CM692711
			// Start Position
			pgr_D.setIndex(0);

			//#CM692712
			// Check the count of the search results.
			String errorMsg = listbox.checkLength();
			//#CM692713
			// Hide the header.
			lst_ConsignorSearch.setVisible(false);
			//#CM692714
			// Display the error message.
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM692715
	// Protected methods ---------------------------------------------

	//#CM692716
	// Private methods -----------------------------------------------

	//#CM692717
	// Event handler methods -----------------------------------------
	//#CM692718
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692719
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692720
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

	//#CM692721
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

	//#CM692722
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

	//#CM692723
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

	//#CM692724
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

	//#CM692725
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692726
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692727
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692728
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM692729
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM692730
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692731
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692732
	/** 
	 * Execute the process defined for clicking on Select List Cell button. <BR>
	 * <BR>
	 *	Pass the Consignor code and Consignor name to the parent screen and close the listbox. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void lst_ConsignorSearch_Click(ActionEvent e) throws Exception
	{
		//#CM692733
		// Set the current line.
		lst_ConsignorSearch.setCurrentRow(lst_ConsignorSearch.getActiveRow());
		lst_ConsignorSearch.getValue(1);

		//#CM692734
		// Set the Set the parameter needed to return to the parent screen.
		ForwardParameters param = new ForwardParameters();
		//#CM692735
		// Consignor Code
		param.setParameter(CONSIGNORCODE_KEY, lst_ConsignorSearch.getValue(2));
		//#CM692736
		// Consignor Name
		param.setParameter(CONSIGNORNAME_KEY, lst_ConsignorSearch.getValue(3));

		//#CM692737
		// Shift to the parent screen.
		parentRedirect(param);
	}

	//#CM692738
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
		String searchFlag = this.getViewState().getString(SEARCHCONSIGNOR_KEY);
		
		if (searchFlag.equals(SystemParameter.SEARCHFLAG_STOCK))
		{
			//#CM692739
			// Maintain the list box in the Session
			SessionSystemStockConsignorRet listbox =
				(SessionSystemStockConsignorRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "next");
		}
		else
		{
			//#CM692740
			// Maintain the list box in the Session
			SessionSystemShortageInfoConsignorRet listbox =
				(SessionSystemShortageInfoConsignorRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "next");
		}
		
	}

	//#CM692741
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
		String searchFlag = this.getViewState().getString(SEARCHCONSIGNOR_KEY);

		if (searchFlag.equals(SystemParameter.SEARCHFLAG_STOCK))
		{
			//#CM692742
			// Maintain the list box in the Session
			SessionSystemStockConsignorRet listbox =
				(SessionSystemStockConsignorRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "previous");
		}
		else
		{
			//#CM692743
			// Maintain the list box in the Session
			SessionSystemShortageInfoConsignorRet listbox =
				(SessionSystemShortageInfoConsignorRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "previous");
		}
	}

	//#CM692744
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
		String searchFlag = this.getViewState().getString(SEARCHCONSIGNOR_KEY);
		
		if (searchFlag.equals(SystemParameter.SEARCHFLAG_STOCK))
		{
			//#CM692745
			// Maintain the list box in the Session
			SessionSystemStockConsignorRet listbox =
				(SessionSystemStockConsignorRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "last");
		}
		else
		{
			//#CM692746
			// Maintain the list box in the Session
			SessionSystemShortageInfoConsignorRet listbox =
				(SessionSystemShortageInfoConsignorRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "last");
		}
	}

	//#CM692747
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
		String searchFlag = this.getViewState().getString(SEARCHCONSIGNOR_KEY);
		
		if (searchFlag.equals(SystemParameter.SEARCHFLAG_STOCK))
		{
			//#CM692748
			// Maintain the list box in the Session
			SessionSystemStockConsignorRet listbox =
				(SessionSystemStockConsignorRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "first");
		}
		else
		{
			//#CM692749
			// Maintain the list box in the Session
			SessionSystemShortageInfoConsignorRet listbox =
				(SessionSystemShortageInfoConsignorRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "first");
		}
	}

	//#CM692750
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692751
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
		//#CM692752
		// Maintain the list box in the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM692753
		// If there is any value in the Session:
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM692754
				// Close the statement.
				finder.close();
			}
			//#CM692755
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM692756
		// Delete from the session.
		this.getSession().removeAttribute("LISTBOX");
		//#CM692757
		// Return to the parent screen.
		parentRedirect(null);
	}
}
//#CM692758
//end of class
