// $Id: ListStockCaseItfBusiness.java,v 1.2 2006/10/04 05:09:37 suresh Exp $

//#CM4805
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockcaseitf;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp
	.co
	.daifuku
	.wms
	.stockcontrol
	.display
	.web
	.listbox
	.liststockconsignor
	.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp
	.co
	.daifuku
	.wms
	.stockcontrol
	.display
	.web
	.listbox
	.liststocklocation
	.ListStockLocationBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret.SessionItfRet;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM4806
/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * This is Case ITF search class.<BR>
 * Consignor code, Item code, Case/PieceDivision, that are input from parent screen<BR>
 * Start Location<BR>
 * <BR>
 * 1.Initial screen(<CODE>page_Load(ActionEvent e)</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *  Search for the data using Consignor code<BR>
 * <BR>
 * </DIR>
 * <BR>
 * 2.The button on the line selected(<CODE>lst_StockCaseItf_Click</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	Pass the Case ITF of the selected line to the parent screen and close the listbox.<BR>
 * <BR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/04</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:09:37 $
 * @author  $Author: suresh $
 */
public class ListStockCaseItfBusiness extends ListStockCaseItf implements WMSConstants
{
	//#CM4807
	// Class fields --------------------------------------------------
	//#CM4808
	/** 
	 * Use this key to pass the Case ITF.
	 */
	public static final String CASEITF_KEY = "CASEITF_KEY";

	//#CM4809
	// Class variables -----------------------------------------------

	//#CM4810
	// Class method --------------------------------------------------

	//#CM4811
	// Constructors --------------------------------------------------

	//#CM4812
	// Public methods ------------------------------------------------
	//#CM4813
	/**
	 * Initialize the screen<BR>
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *		Selection <BR>
	 *		Case ITF <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM4814
		// Set the screen name
		//#CM4815
		// Case ITF search
		lbl_ListName.setText(DisplayText.getText("TLE-W0062"));

		//#CM4816
		// Obtain parameter
		//#CM4817
		// Consignor code
		String consignorcode = request.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM4818
		// Item code
		String itemcode = request.getParameter(ListStockItemBusiness.ITEMCODE_KEY);
		//#CM4819
		// Case/PieceDivision
		String casepieceflag = request.getParameter(ListStockLocationBusiness.CASEPIECEFLAG_KEY);
		//#CM4820
		// Start location
		String startlocation = request.getParameter(ListStockLocationBusiness.STARTLOCATION_KEY);
		//#CM4821
		// End location
		String endlocation = request.getParameter(ListStockLocationBusiness.ENDLOCATION_KEY);
		//#CM4822
		// Case ITF
		String caseitf = request.getParameter(CASEITF_KEY);
		//#CM4823
		// Area type flag
		String areatypeflag = request.getParameter(ListStockConsignorBusiness.AREA_TYPE_KEY);

		//#CM4824
		// Close the connection of the object remained at the Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM4825
			// Close the connection
			sRet.closeConnection();
			//#CM4826
			// Delete from the Session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM4827
		// Obtain the connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM4828
		// Set the parameter.
		StockControlParameter param = new StockControlParameter();
		//#CM4829
		// Consignor code
		param.setConsignorCode(consignorcode);
		//#CM4830
		// Item code
		param.setItemCode(itemcode);
		//#CM4831
		// Case/PieceDivision
		param.setCasePieceFlag(casepieceflag);
		//#CM4832
		// Start location
		param.setFromLocationNo(startlocation);
		//#CM4833
		// End location
		param.setToLocationNo(endlocation);
		//#CM4834
		// Case ITF
		param.setITF(caseitf);
		//#CM4835
		// Area type flag
		param.setAreaTypeFlag(areatypeflag);

		//#CM4836
		// Generate SessionRet instance.
		SessionItfRet listbox = new SessionItfRet(conn, param);
		//#CM4837
		// Store listbox to the Session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM4838
	// Package methods -----------------------------------------------

	//#CM4839
	// Protected methods ---------------------------------------------

	//#CM4840
	// Private methods -----------------------------------------------
	//#CM4841
	/**
	 * Method to change the page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Reports all the exceptions.
	 */
	private void setList(SessionItfRet listbox, String actionName)
	{
		//#CM4842
		// Set the Page info.
		listbox.setActionName(actionName);

		//#CM4843
		// Obtain search result
		StockControlParameter[] stockparam = listbox.getEntities();

		int len = 0;
		if (stockparam != null)
			len = stockparam.length;
		if (len > 0)
		{
			//#CM4844
			// Set the value for the pager.
			//#CM4845
			// Maximum Count
			pgr_U.setMax(listbox.getLength());
			//#CM4846
			// Display Counts per 1 page
			pgr_U.setPage(listbox.getCondition());
			//#CM4847
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM4848
			// Maximum Count
			pgr_D.setMax(listbox.getLength());
			//#CM4849
			// Display Counts per 1 page
			pgr_D.setPage(listbox.getCondition());
			//#CM4850
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM4851
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM4852
			// Delete all lines.
			lst_ListStockCaseItf.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM4853
				// Obtain the tailing line.
				int count = lst_ListStockCaseItf.getMaxRows();
				//#CM4854
				// Add line
				lst_ListStockCaseItf.addRow();

				//#CM4855
				// Move to the end line.
				lst_ListStockCaseItf.setCurrentRow(count);
				lst_ListStockCaseItf.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListStockCaseItf.setValue(2, stockparam[i].getITF());
			}
		}
		else
		{
			//#CM4856
			// Set the value for the Pager.
			//#CM4857
			// Maximum Count
			pgr_U.setMax(0);
			//#CM4858
			// Display Counts per 1 page
			pgr_U.setPage(0);
			//#CM4859
			// Start Position
			pgr_U.setIndex(0);
			//#CM4860
			// Maximum Count
			pgr_D.setMax(0);
			//#CM4861
			// Display Counts per 1 page
			pgr_D.setPage(0);
			//#CM4862
			// Start Position
			pgr_D.setIndex(0);

			//#CM4863
			// Execute search result count check
			String errorMsg = listbox.checkLength();
			//#CM4864
			// Hide the header.
			lst_ListStockCaseItf.setVisible(false);
			//#CM4865
			// Error message display
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM4866
	// Event handler methods -----------------------------------------
	//#CM4867
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4868
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4869
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

	//#CM4870
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

	//#CM4871
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

	//#CM4872
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

	//#CM4873
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

	//#CM4874
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4875
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockCaseItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM4876
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockCaseItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM4877
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockCaseItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM4878
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockCaseItf_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM4879
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockCaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4880
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStockCaseItf_Change(ActionEvent e) throws Exception
	{
	}

	//#CM4881
	/** 
	 * Clicking Select list cell button executes its process. <BR>
	 * <BR>
	 *	Pass the Case ITF to the parent screen and close the listbox. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void lst_ListStockCaseItf_Click(ActionEvent e) throws Exception
	{
		//#CM4882
		// Set the current line.
		lst_ListStockCaseItf.setCurrentRow(lst_ListStockCaseItf.getActiveRow());
		lst_ListStockCaseItf.getValue(1);

		//#CM4883
		// Set the parameter needed to return to the parent screen.
		ForwardParameters param = new ForwardParameters();
		//#CM4884
		// Case ITF
		param.setParameter(CASEITF_KEY, lst_ListStockCaseItf.getValue(2));

		//#CM4885
		// shift to the parent screen.
		parentRedirect(param);
	}

	//#CM4886
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
		//#CM4887
		// Store listbox to the Session
		SessionItfRet listbox = (SessionItfRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM4888
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
		//#CM4889
		// Store listbox to the Session
		SessionItfRet listbox = (SessionItfRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM4890
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
		//#CM4891
		// Store listbox to the Session
		SessionItfRet listbox = (SessionItfRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM4892
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
		//#CM4893
		// Store listbox to the Session
		SessionItfRet listbox = (SessionItfRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM4894
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4895
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
		//#CM4896
		// Store listbox to the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM4897
		// When value exists in the Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM4898
				// Close the statement
				finder.close();
			}
			//#CM4899
			// Close the connection
			sessionret.closeConnection();
		}
		//#CM4900
		// Delete from the Session
		this.getSession().removeAttribute("LISTBOX");
		//#CM4901
		// Return to the parent screen.
		parentRedirect(null);
	}
}
//#CM4902
//end of class
