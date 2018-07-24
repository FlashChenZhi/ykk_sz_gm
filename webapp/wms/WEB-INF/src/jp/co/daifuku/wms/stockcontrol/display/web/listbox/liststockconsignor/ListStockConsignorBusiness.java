// $Id: ListStockConsignorBusiness.java,v 1.2 2006/10/04 05:09:46 suresh Exp $

//#CM4907
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret.SessionNoPlanConsignorRet;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret.SessionStockConsignorRet;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM4908
/**
 * Designer : K.Mukai<BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * This is Consignor search listbox class.<BR>
 * Search for the data using Consignor code entered via parent screen as a key.<BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * 1.Initial screen(<CODE>page_Load(ActionEvent e)</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Consignor code entered via parent screen as a key<BR>
 * <BR>
 * </DIR>
 * 2.The button on the line selected(<CODE>lst_ConsignorSearch_Click</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	Pass the Consignor code<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/07</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:09:46 $
 * @author  $Author: suresh $
 */
public class ListStockConsignorBusiness extends ListStockConsignor implements WMSConstants
{
	//#CM4909
	// Class fields --------------------------------------------------
	//#CM4910
	/** 
	 * Use this key to pass the Consignor code.
	 */
	public static final String CONSIGNORCODE_KEY = "CONSIGNORCODE_KEY";

	//#CM4911
	/** 
	 * Use this key to pass the Consignor name.
	 */
	public static final String CONSIGNORNAME_KEY = "CONSIGNORNAME_KEY";
	
	//#CM4912
	/** 
	 * Use this key to pass the search flag.
	 */
	public static final String SEARCHCONSIGNOR_KEY = "SEARCHCONSIGNOR_KEY";	

	//#CM4913
	/** 
	 * Use this key to pass the Area type flag.
	 */
	public static final String AREA_TYPE_KEY = "AREA_TYPE_KEY";	

	//#CM4914
	// Class variables -----------------------------------------------

	//#CM4915
	// Class method --------------------------------------------------

	//#CM4916
	// Constructors --------------------------------------------------

	//#CM4917
	// Public methods ------------------------------------------------
	//#CM4918
	/**
	 * Initialize the screen <BR>
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *		Selection <BR>
	 *		Consignor code <BR>
	 *		Consignor name <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM4919
		// Set the screen name
		//#CM4920
		// Search Consignor search
		lbl_ListName.setText(DisplayText.getText("TLE-W0012"));

		//#CM4921
		// Obtain parameter
		//#CM4922
		// Consignor code
		String consignorcode = request.getParameter(CONSIGNORCODE_KEY);
		//#CM4923
		// Search Consignor Search flag
		String searchconsignor = request.getParameter(SEARCHCONSIGNOR_KEY);
		//#CM4924
		// Area type flag
		String areatypeflag = request.getParameter(AREA_TYPE_KEY);
		
		viewState.setString(SEARCHCONSIGNOR_KEY, searchconsignor);
				
		//#CM4925
		// Close the connection of the object remained at the Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM4926
			// Close the connection
			sRet.closeConnection();
			//#CM4927
			// Delete from the Session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM4928
		// Obtain the connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM4929
		// Set the parameter.
		StockControlParameter param = new StockControlParameter();
		//#CM4930
		// Consignor code
		param.setConsignorCode(consignorcode);
		param.setSearchFlag(searchconsignor);
		param.setAreaTypeFlag(areatypeflag);
		
		if(searchconsignor == null)
		{
			//#CM4931
			// Generate SessionStockConsignorRet instance.
			SessionStockConsignorRet listbox =
				new SessionStockConsignorRet(conn, param);
			//#CM4932
			// Store listbox to the Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setList(listbox, "first");
		}
		//#CM4933
		//  Consignor to be searched decide if it is stock or Unplanned work.
		else if (searchconsignor.equals(StockControlParameter.SEARCHFLAG_EX_STORAGE))
		{
			//#CM4934
			// Generate SessionNoPlanConsignorRet instance.
			SessionNoPlanConsignorRet listbox =
				new SessionNoPlanConsignorRet(conn, param);
			//#CM4935
			// Store listbox to the Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setResultList(listbox, "first");
		}
		else if (searchconsignor.equals(StockControlParameter.SEARCHFLAG_EX_RETRIEVAL))
		{
			//#CM4936
			// Generate SessionNoPlanConsignorRet instance.
			SessionNoPlanConsignorRet listbox =
				new SessionNoPlanConsignorRet(conn, param);
			//#CM4937
			// Store listbox to the Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setResultList(listbox, "first");
		}

	}

	//#CM4938
	// Package methods -----------------------------------------------

	//#CM4939
	// Protected methods ---------------------------------------------

	//#CM4940
	// Private methods -----------------------------------------------
	//#CM4941
	/**
	 * Method to change the page <BR>
	 * Search the table <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Reports all the exceptions.
	 */
	private void setList(SessionStockConsignorRet listbox, String actionName) throws Exception
	{
		//#CM4942
		// Set the Page info.
		listbox.setActionName(actionName);

		//#CM4943
		// Obtain search result
		Stock[] stock = listbox.getEntities();
		int len = 0;
		if (stock != null)
			len = stock.length;
		if (len > 0)
		{
			//#CM4944
			// Set the value for the Pager.
			//#CM4945
			// Maximum Count
			pgr_U.setMax(listbox.getLength());
			//#CM4946
			// Display Counts per 1 page
			pgr_U.setPage(listbox.getCondition());
			//#CM4947
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM4948
			// Maximum Count
			pgr_D.setMax(listbox.getLength());
			//#CM4949
			// Display Counts per 1 page
			pgr_D.setPage(listbox.getCondition());
			//#CM4950
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM4951
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM4952
			// Delete all lines.
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM4953
				// Obtain the tailing line.
				int count = lst_ConsignorSearch.getMaxRows();
				//#CM4954
				// Add line
				lst_ConsignorSearch.addRow();

				//#CM4955
				// Move to the end line.
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, stock[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, stock[i].getConsignorName());
			}
		}
		else
		{
			//#CM4956
			// Set the value for the Pager.
			//#CM4957
			// Maximum Count
			pgr_U.setMax(0);
			//#CM4958
			// Display Counts per 1 page
			pgr_U.setPage(0);
			//#CM4959
			// Start Position
			pgr_U.setIndex(0);
			//#CM4960
			// Maximum Count
			pgr_D.setMax(0);
			//#CM4961
			// Display Counts per 1 page
			pgr_D.setPage(0);
			//#CM4962
			// Start Position
			pgr_D.setIndex(0);

			//#CM4963
			// Execute search result count check
			String errorMsg = listbox.checkLength();
			//#CM4964
			// Hide the header.
			lst_ConsignorSearch.setVisible(false);
			//#CM4965
			// Error message display
			lbl_InMsg.setResourceKey(errorMsg);
		}		
	}
	//#CM4966
	/**
	 * Method to change the page <BR>
	 * search the result table. <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Reports all the exceptions.
	 */
	private void setResultList(SessionNoPlanConsignorRet listbox, String actionName)
		throws Exception
	{
		//#CM4967
		// Set the Page info.
		listbox.setActionName(actionName);

		//#CM4968
		// Obtain search result
		ResultView[] result = listbox.getEntities();
		int len = 0;
		if (result != null)
			len = result.length;
		if (len > 0)
		{
			//#CM4969
			// Set the value for the Pager.
			//#CM4970
			// Maximum Count
			pgr_U.setMax(listbox.getLength());
			//#CM4971
			// Display Counts per 1 page
			pgr_U.setPage(listbox.getCondition());
			//#CM4972
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM4973
			// Maximum Count
			pgr_D.setMax(listbox.getLength());
			//#CM4974
			// Display Counts per 1 page
			pgr_D.setPage(listbox.getCondition());
			//#CM4975
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM4976
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM4977
			// Delete all lines.
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM4978
				// Obtain the tailing line.
				int count = lst_ConsignorSearch.getMaxRows();
				//#CM4979
				// Add line
				lst_ConsignorSearch.addRow();

				//#CM4980
				// Move to the end line.
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, result[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, result[i].getConsignorName());
			}
		}
		else
		{
			//#CM4981
			// Set the value for the Pager.
			//#CM4982
			// Maximum Count
			pgr_U.setMax(0);
			//#CM4983
			// Display Counts per 1 page
			pgr_U.setPage(0);
			//#CM4984
			// Start Position
			pgr_U.setIndex(0);
			//#CM4985
			// Maximum Count
			pgr_D.setMax(0);
			//#CM4986
			// Display Counts per 1 page
			pgr_D.setPage(0);
			//#CM4987
			// Start Position
			pgr_D.setIndex(0);

			//#CM4988
			// Execute search result count check
			String errorMsg = listbox.checkLength();
			//#CM4989
			// Hide the header.
			lst_ConsignorSearch.setVisible(false);
			//#CM4990
			// Error message display
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM4991
	// Event handler methods -----------------------------------------
	//#CM4992
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4993
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4994
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

	//#CM4995
	/** 
	 * Execute processing when ">" button is pressed down. <BR>
	 * <BR>
	 * Display the next page. <BR>
	 * <BR>
	 * 
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	//#CM4996
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

	//#CM4997
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

	//#CM4998
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

	//#CM4999
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5000
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM5001
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM5002
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM5003
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM5004
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5005
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM5006
	/** 
	 * Clicking Select list cell button executes its process. <BR>
	 * <BR>
	 *	Pass the Consignor code and Consignor name to the parent screen and close the listbox. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void lst_ConsignorSearch_Click(ActionEvent e) throws Exception
	{
		//#CM5007
		// Set the current line.
		lst_ConsignorSearch.setCurrentRow(lst_ConsignorSearch.getActiveRow());
		lst_ConsignorSearch.getValue(1);

		//#CM5008
		// Set the parameter needed to return to the parent screen.
		ForwardParameters param = new ForwardParameters();
		//#CM5009
		// Consignor code
		param.setParameter(CONSIGNORCODE_KEY, lst_ConsignorSearch.getValue(2));
		//#CM5010
		// Consignor name
		param.setParameter(CONSIGNORNAME_KEY, lst_ConsignorSearch.getValue(3));

		//#CM5011
		// shift to the parent screen.
		parentRedirect(param);
	}

	//#CM5012
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
		//#CM5013
		// Obtain the search flag.
		String flag = viewState.getString(SEARCHCONSIGNOR_KEY);
		
		if (flag == null)
		{
			//#CM5014
			// Store listbox to the Session
			SessionStockConsignorRet listbox =
				(SessionStockConsignorRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "next");
		}
		else if (flag.equals(StockControlParameter.SEARCHFLAG_EX_STORAGE))
		{
			//#CM5015
			// Store listbox to the Session
			SessionNoPlanConsignorRet listbox =
				(SessionNoPlanConsignorRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "next");
		}
		else if (flag.equals(StockControlParameter.SEARCHFLAG_EX_RETRIEVAL))
		{
			//#CM5016
			// Store listbox to the Session
			SessionNoPlanConsignorRet listbox =
				(SessionNoPlanConsignorRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "next");
		}
	}

	//#CM5017
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
		//#CM5018
		// Obtain the search flag.
		String flag = viewState.getString(SEARCHCONSIGNOR_KEY);
		
		if (flag == null)
		{
			//#CM5019
			// Store listbox to the Session
			SessionStockConsignorRet listbox =
				(SessionStockConsignorRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "previous");
		}
		else if (flag.equals(StockControlParameter.SEARCHFLAG_EX_STORAGE))
		{
			//#CM5020
			// Store listbox to the Session
			SessionNoPlanConsignorRet listbox =
				(SessionNoPlanConsignorRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "previous");
		}
		else if (flag.equals(StockControlParameter.SEARCHFLAG_EX_RETRIEVAL))
		{
			//#CM5021
			// Store listbox to the Session
			SessionNoPlanConsignorRet listbox =
				(SessionNoPlanConsignorRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "previous");
		}
	}

	//#CM5022
	/** 
	 * Clicking ">> " button executes its process. <BR>
	 * <BR>
	 * Display the final page. <BR>
	 * <BR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		//#CM5023
		// Obtain the search flag.
		String flag = viewState.getString(SEARCHCONSIGNOR_KEY);
		
		if (flag == null)
		{
			//#CM5024
			// Store listbox to the Session
			SessionStockConsignorRet listbox =
				(SessionStockConsignorRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "last");
		}
		else if (flag.equals(StockControlParameter.SEARCHFLAG_EX_STORAGE))
		{
			//#CM5025
			// Store listbox to the Session
			SessionNoPlanConsignorRet listbox =
				(SessionNoPlanConsignorRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "last");
		}
		else if (flag.equals(StockControlParameter.SEARCHFLAG_EX_RETRIEVAL))
		{
			//#CM5026
			// Store listbox to the Session
			SessionNoPlanConsignorRet listbox =
				(SessionNoPlanConsignorRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "last");
		}				
	}

	//#CM5027
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
		//#CM5028
		// Obtain the search flag.
		String flag = viewState.getString(SEARCHCONSIGNOR_KEY);
		
		if (flag == null)
		{
			//#CM5029
			// Store listbox to the Session
			SessionStockConsignorRet listbox =
				(SessionStockConsignorRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "first");
		}
		else if (flag.equals(StockControlParameter.SEARCHFLAG_EX_STORAGE))
		{
			//#CM5030
			// Store listbox to the Session
			SessionNoPlanConsignorRet listbox =
				(SessionNoPlanConsignorRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "first");
		}		
		else if (flag.equals(StockControlParameter.SEARCHFLAG_EX_RETRIEVAL))
		{
			//#CM5031
			// Store listbox to the Session
			SessionNoPlanConsignorRet listbox =
				(SessionNoPlanConsignorRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "first");
		}		
	}

	//#CM5032
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM5033
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
		//#CM5034
		// Store listbox to the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM5035
		// When value exists in the Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM5036
				// Close the statement
				finder.close();
			}
			//#CM5037
			// Close the connection
			sessionret.closeConnection();
		}
		//#CM5038
		// Delete from the Session
		this.getSession().removeAttribute("LISTBOX");
		//#CM5039
		// Return to the parent screen.
		parentRedirect(null);
	}

}
//#CM5040
//end of class
