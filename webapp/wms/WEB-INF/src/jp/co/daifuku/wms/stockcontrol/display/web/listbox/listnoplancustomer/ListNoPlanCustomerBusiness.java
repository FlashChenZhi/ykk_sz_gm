// $Id: ListNoPlanCustomerBusiness.java,v 1.2 2006/10/04 05:05:26 suresh Exp $

//#CM4386
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.listnoplancustomer;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.listnoplanretrievaldate.ListNoPlanRetrievalDateBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.listnoplanstoragedate.ListNoPlanStorageDateBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret.SessionNoPlanCustomerRet;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM4387
/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * This is customer search (for Unplanned work) listbox class.<BR>
 * Search for the data using Customer code entered via parent screen as a key.<BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * 1.Initial screen(<CODE>page_Load(ActionEvent e)</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Customer code entered via parent screen as a key<BR>
 * <BR>
 * </DIR>
 * 2.The button on the line selected(<CODE>lst_CustomerSearch_Click</CODE>Method)<BR>
 * <BR>
 * <DIR>
 * 	Pass the customer code<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/12</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:05:26 $
 * @author  $Author: suresh $
 */
public class ListNoPlanCustomerBusiness extends ListNoPlanCustomer implements WMSConstants
{
	//#CM4388
	// Class fields --------------------------------------------------
	//#CM4389
	/** 
	 * Use this key to pass the customer code.
	 */
	public static final String CUSTOMERCODE_KEY = "CUSTOMERCODE_KEY";

	//#CM4390
	/** 
	 * Use this key to pass the customer name.
	 */
	public static final String CUSTOMERNAME_KEY = "CUSTOMERNAME_KEY";
	
	//#CM4391
	/** 
	 * Use this key to pass the search flag.
	 */
	public static final String SEARCHCUSTOMER_KEY = "SEARCHCUSTOMER_KEY";
	//#CM4392
	// Class variables -----------------------------------------------

	//#CM4393
	// Class method --------------------------------------------------

	//#CM4394
	// Constructors --------------------------------------------------

	//#CM4395
	// Public methods ------------------------------------------------

	//#CM4396
	/**
	 * Initialize the screen <BR>
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *		Selection <BR>
	 *		Customer code <BR>
	 *		Customer name <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM4397
		// Set the screen name
		//#CM4398
		// Customer search
		lbl_ListName.setText(DisplayText.getText("TLE-W0031"));

		//#CM4399
		// Obtain parameter
		//#CM4400
		// Consignor code
		String consignorcode =
			request.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM4401
		// Start Storage Date
		String startstoragedate =
			request.getParameter(ListNoPlanStorageDateBusiness.STARTSTORAGEDATE_KEY);
		//#CM4402
		// End Storage Date
		String endstoragedate =
			request.getParameter(ListNoPlanStorageDateBusiness.ENDSTORAGEDATE_KEY);
		//#CM4403
		// Start picking date
		String startretrievaldate =
			request.getParameter(ListNoPlanRetrievalDateBusiness.STARTRETRIEVALDATE_KEY);
		//#CM4404
		// End picking date
		String endretrievaldate =
			request.getParameter(ListNoPlanRetrievalDateBusiness.ENDRETRIEVALDATE_KEY);			
		//#CM4405
		// Item code
		String itemcode =
			request.getParameter(ListStockItemBusiness.ITEMCODE_KEY);
		//#CM4406
		// Customer code
		String customercode = request.getParameter(CUSTOMERCODE_KEY);
		//#CM4407
		// Search flag
		String searchcustomer = request.getParameter(SEARCHCUSTOMER_KEY);
		

		//#CM4408
		// Close the connection of the object remained at the Session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM4409
			// Close the connection
			sRet.closeConnection();
			//#CM4410
			// Delete from the Session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM4411
		// Obtain the connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM4412
		// Set the parameter.
		StockControlParameter param = new StockControlParameter();
		//#CM4413
		// Consignor code
		param.setConsignorCode(consignorcode);
		
		if(searchcustomer.equals(StockControlParameter.SEARCHFLAG_EX_STORAGE))
		{
			//#CM4414
			// Start Storage Date
			param.setFromWorkDate(startstoragedate);
			//#CM4415
			// End Storage Date
			param.setToWorkDate(endstoragedate);
		}
		else if(searchcustomer.equals(StockControlParameter.SEARCHFLAG_EX_RETRIEVAL))
		{
			//#CM4416
			// Start picking date
			param.setFromWorkDate(startretrievaldate);
			//#CM4417
			// End picking date
			param.setToWorkDate(endretrievaldate);	
		}
		//#CM4418
		// Item code
		param.setItemCode(itemcode);
		//#CM4419
		// Customer code
		param.setCustomerCode(customercode);

		//#CM4420
		// Generate SessionNoPlanCustomerRet Instance
		SessionNoPlanCustomerRet listbox =
		new SessionNoPlanCustomerRet(conn, param);
		//#CM4421
		// Store listbox to the Session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM4422
	// Package methods -----------------------------------------------

	//#CM4423
	// Protected methods ---------------------------------------------

	//#CM4424
	// Private methods -----------------------------------------------
	//#CM4425
	/**
	 * Method to change the page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Reports all the exceptions.
	 */
	private void setList(SessionNoPlanCustomerRet listbox, String actionName) throws Exception
	{
		//#CM4426
		// Set the Page info.
		listbox.setActionName(actionName);

		//#CM4427
		// Obtain search result
		ResultView[] result = listbox.getEntities();
		int len = 0;
		if (result != null)
			len = result.length;
		if (len > 0)
		{
			//#CM4428
			// Set the value for the Pager.
			//#CM4429
			// Maximum Count
			pgr_U.setMax(listbox.getLength());
			//#CM4430
			// Display Counts per 1 page
			pgr_U.setPage(listbox.getCondition());
			//#CM4431
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM4432
			// Maximum Count
			pgr_D.setMax(listbox.getLength());
			//#CM4433
			// Display Counts per 1 page
			pgr_D.setPage(listbox.getCondition());
			//#CM4434
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM4435
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM4436
			// Delete all lines.
			lst_CustomerSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM4437
				// Obtain the tailing line.
				int count = lst_CustomerSearch.getMaxRows();
				//#CM4438
				// Add line
				lst_CustomerSearch.addRow();

				//#CM4439
				// Move to the end line.
				lst_CustomerSearch.setCurrentRow(count);
				lst_CustomerSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_CustomerSearch.setValue(2, result[i].getCustomerCode());
				lst_CustomerSearch.setValue(3, result[i].getCustomerName1());
			}
		}
		else
		{
			//#CM4440
			// Set the value for the Pager.
			//#CM4441
			// Maximum Count
			pgr_U.setMax(0);
			//#CM4442
			// Display Counts per 1 page
			pgr_U.setPage(0);
			//#CM4443
			// Start Position
			pgr_U.setIndex(0);
			//#CM4444
			// Maximum Count
			pgr_D.setMax(0);
			//#CM4445
			// Display Counts per 1 page
			pgr_D.setPage(0);
			//#CM4446
			// Start Position
			pgr_D.setIndex(0);

			//#CM4447
			// Execute search result count check
			String errorMsg = listbox.checkLength();
			//#CM4448
			// Hide the header.
			lst_CustomerSearch.setVisible(false);
			//#CM4449
			// Error message display
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM4450
	// Event handler methods -----------------------------------------
	//#CM4451
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4452
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4453
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

	//#CM4454
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

	//#CM4455
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

	//#CM4456
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

	//#CM4457
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

	//#CM4458
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4459
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_CustomerSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM4460
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_CustomerSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM4461
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_CustomerSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM4462
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_CustomerSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM4463
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_CustomerSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4464
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_CustomerSearch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM4465
	/** 
	 * Clicking Select list cell button executes its process. <BR>
	 * <BR>
	 *	Pass the customer code and customer name to the parent screen and close the listbox. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void lst_CustomerSearch_Click(ActionEvent e) throws Exception
	{
		//#CM4466
		// Set the current line.
		lst_CustomerSearch.setCurrentRow(lst_CustomerSearch.getActiveRow());
		lst_CustomerSearch.getValue(1);

		//#CM4467
		// Set the parameter needed to return to the parent screen.
		ForwardParameters param = new ForwardParameters();
		//#CM4468
		// Customer code
		param.setParameter(CUSTOMERCODE_KEY, lst_CustomerSearch.getValue(2));
		//#CM4469
		// Customer name
		param.setParameter(CUSTOMERNAME_KEY, lst_CustomerSearch.getValue(3));

		//#CM4470
		// shift to the parent screen.
		parentRedirect(param);
	}

	//#CM4471
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
		//#CM4472
		// Store listbox to the Session
		SessionNoPlanCustomerRet listbox = (SessionNoPlanCustomerRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM4473
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
		//#CM4474
		// Store listbox to the Session
		SessionNoPlanCustomerRet listbox = (SessionNoPlanCustomerRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM4475
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
		//#CM4476
		// Store listbox to the Session
		SessionNoPlanCustomerRet listbox = (SessionNoPlanCustomerRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM4477
	/** 
	 * Execute processing when "<<" button is pressed down.<BR>
	 * <BR>
	 * Display the top page. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM4478
		// Store listbox to the Session
		SessionNoPlanCustomerRet listbox = (SessionNoPlanCustomerRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM4479
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM4480
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
		//#CM4481
		// Store listbox to the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM4482
		// When value exists in the Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM4483
				// Close the statement
				finder.close();
			}
			//#CM4484
			// Close the connection
			sessionret.closeConnection();
		}
		//#CM4485
		// Delete from the Session
		this.getSession().removeAttribute("LISTBOX");
		//#CM4486
		// Return to the parent screen.
		parentRedirect(null);
	}


}
//#CM4487
//end of class
