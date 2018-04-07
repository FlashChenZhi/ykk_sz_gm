// $Id: ListStockBundleItfBusiness.java,v 1.2 2006/10/04 05:05:51 suresh Exp $

//#CM4701
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockbundleitf;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockcaseitf.ListStockCaseItfBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocation.ListStockLocationBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret.SessionBundleItfRet;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM4702
/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * This is bundle ITF search class.<BR>
 * Consignor code, Item code, Case/PieceDivision, input from parent screen<BR>
 * Start Location<BR>
 * <BR>
 * 1.Initial screen(<CODE>page_Load(ActionEvent e)</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *  Search for the data using Consignor code<BR>
 * <BR>
 * </DIR>
 * 2.The button on the line selected(<CODE>lst_StockBundleItf_Click</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	Pass the bundle ITF of the selected line to the parent screen and close the listbox.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/06</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:05:51 $
 * @author  $Author: suresh $
 */
public class ListStockBundleItfBusiness extends ListStockBundleItf implements WMSConstants
{
	//#CM4703
	// Class fields --------------------------------------------------
	//#CM4704
	/** 
	 * Use this key to pass the bundle ITF.
	 */
	public static final String BUNDLEITF_KEY = "BUNDLEITF_KEY";
	//#CM4705
	// Class variables -----------------------------------------------

	//#CM4706
	// Class method --------------------------------------------------

	//#CM4707
	// Constructors --------------------------------------------------

	//#CM4708
	// Public methods ------------------------------------------------

	//#CM4709
	/**
	 * Initialize the screen<BR>
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *		Selection <BR>
	 *		Bundle ITF <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM4710
		// Set the screen name
		//#CM4711
		// Bundle ITF search
		lbl_ListName.setText(DisplayText.getText("TLE-W0063"));

		//#CM4712
		// Obtain parameter
		//#CM4713
		// Consignor code
		String consignorcode = request.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM4714
		// Item code
		String itemcode = request.getParameter(ListStockItemBusiness.ITEMCODE_KEY);
		//#CM4715
		// Case/PieceDivision
		String casepieceflag = request.getParameter(ListStockLocationBusiness.CASEPIECEFLAG_KEY);
		//#CM4716
		// Start location
		String startlocation = request.getParameter(ListStockLocationBusiness.STARTLOCATION_KEY);
		//#CM4717
		// End location
		String endlocation = request.getParameter(ListStockLocationBusiness.ENDLOCATION_KEY);
		//#CM4718
		// Case ITF
		String caseitf = request.getParameter(ListStockCaseItfBusiness.CASEITF_KEY);
		//#CM4719
		// Bundle ITF
		String bundleitf = request.getParameter(BUNDLEITF_KEY);
		//#CM4720
		// Area type flag
		String areatypeflag = request.getParameter(ListStockConsignorBusiness.AREA_TYPE_KEY);

		//#CM4721
		// Close the connection of the object remained at the Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM4722
			// Close the connection
			sRet.closeConnection();
			//#CM4723
			// Delete from the Session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM4724
		// Obtain the connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM4725
		// Set the parameter.
		StockControlParameter param = new StockControlParameter();
		//#CM4726
		// Consignor code
		param.setConsignorCode(consignorcode);
		//#CM4727
		// Item code
		param.setItemCode(itemcode);
		//#CM4728
		// Case/PieceDivision
		param.setCasePieceFlag(casepieceflag);
		//#CM4729
		// Start location
		param.setFromLocationNo(startlocation);
		//#CM4730
		// End location
		param.setToLocationNo(endlocation);
		//#CM4731
		// Case ITF
		param.setITF(caseitf);
		//#CM4732
		// Bundle ITF
		param.setBundleITF(bundleitf);
		//#CM4733
		// Area type flag
		param.setAreaTypeFlag(areatypeflag);

		//#CM4734
		// Generate SessionRet instance.
		SessionBundleItfRet listbox = new SessionBundleItfRet(conn, param);
		//#CM4735
		// Store listbox to the Session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM4736
	// Package methods -----------------------------------------------

	//#CM4737
	// Protected methods ---------------------------------------------

	//#CM4738
	// Private methods -----------------------------------------------
	//#CM4739
	/**
	 * Method to change the page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Reports all the exceptions.
	 */
	private void setList(SessionBundleItfRet listbox, String actionName)
	{
		//#CM4740
		// Set the Page info.
		listbox.setActionName(actionName);

		//#CM4741
		// Obtain search result
		StockControlParameter[] stockparam = listbox.getEntities();

		int len = 0;
		if (stockparam != null)
			len = stockparam.length;
		if (len > 0)
		{
			//#CM4742
			// Set the value for the pager.
			//#CM4743
			// Maximum Count
			pgr_U.setMax(listbox.getLength());
			//#CM4744
			// Display Counts per 1 page
			pgr_U.setPage(listbox.getCondition());
			//#CM4745
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM4746
			// Maximum Count
			pgr_D.setMax(listbox.getLength());
			//#CM4747
			// Display Counts per 1 page
			pgr_D.setPage(listbox.getCondition());
			//#CM4748
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM4749
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM4750
			// Delete all lines.
			lst_ListStockBundleItf.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM4751
				// Obtain the tailing line.
				int count = lst_ListStockBundleItf.getMaxRows();
				//#CM4752
				// Add line
				lst_ListStockBundleItf.addRow();

				//#CM4753
				// Move to the end line.
				lst_ListStockBundleItf.setCurrentRow(count);
				lst_ListStockBundleItf.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListStockBundleItf.setValue(2, stockparam[i].getBundleITF());
			}
		}
		else
		{
			//#CM4754
			// Set the value for the Pager.
			//#CM4755
			// Maximum Count
			pgr_U.setMax(0);
			//#CM4756
			// Display Counts per 1 page
			pgr_U.setPage(0);
			//#CM4757
			// Start Position
			pgr_U.setIndex(0);
			//#CM4758
			// Maximum Count
			pgr_D.setMax(0);
			//#CM4759
			// Display Counts per 1 page
			pgr_D.setPage(0);
			//#CM4760
			// Start Position
			pgr_D.setIndex(0);

			//#CM4761
			// Execute search result count check
			String errorMsg = listbox.checkLength();
			//#CM4762
			// Hide the header.
			lst_ListStockBundleItf.setVisible(false);
			//#CM4763
			// Error message display
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM4764
	// Event handler methods -----------------------------------------
	//#CM4765
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4766
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4767
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

	//#CM4768
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

	//#CM4769
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

	//#CM4770
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

	//#CM4771
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

	//#CM4772
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4773
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockBundleItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM4774
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockBundleItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM4775
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockBundleItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM4776
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockBundleItf_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM4777
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockBundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4778
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockBundleItf_Change(ActionEvent e) throws Exception
	{
	}

	//#CM4779
	/** 
	 * Clicking Select list cell button executes its process. <BR>
	 * <BR>
	 *	Pass Bundle ITF to parent screen and close list box.<BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void lst_ListStockBundleItf_Click(ActionEvent e) throws Exception
	{
		//#CM4780
		// Set the current line.
		lst_ListStockBundleItf.setCurrentRow(lst_ListStockBundleItf.getActiveRow());
		lst_ListStockBundleItf.getValue(1);

		//#CM4781
		// Set the parameter needed to return to the parent screen.
		ForwardParameters param = new ForwardParameters();
		//#CM4782
		// Bundle ITF
		param.setParameter(BUNDLEITF_KEY, lst_ListStockBundleItf.getValue(2));

		//#CM4783
		// shift to the parent screen.
		parentRedirect(param);
	}

	//#CM4784
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
		//#CM4785
		// Store listbox to the Session
		SessionBundleItfRet listbox = (SessionBundleItfRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM4786
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
		//#CM4787
		// Store listbox to the Session
		SessionBundleItfRet listbox = (SessionBundleItfRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM4788
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
		//#CM4789
		// Store listbox to the Session
		SessionBundleItfRet listbox = (SessionBundleItfRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM4790
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
		//#CM4791
		// Store listbox to the Session
		SessionBundleItfRet listbox = (SessionBundleItfRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM4792
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4793
	/** 
	 * Clicking Close button executes its process. <BR>
	 * <BR>
	 * Close listbox, and<BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM4794
		// Store listbox to the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM4795
		// When value exists in the Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM4796
				// Close the statement
				finder.close();
			}
			//#CM4797
			// Close the connection
			sessionret.closeConnection();
		}
		//#CM4798
		// Delete from the Session
		this.getSession().removeAttribute("LISTBOX");
		//#CM4799
		// Return to the parent screen.
		parentRedirect(null);
	}


}
//#CM4800
//end of class
