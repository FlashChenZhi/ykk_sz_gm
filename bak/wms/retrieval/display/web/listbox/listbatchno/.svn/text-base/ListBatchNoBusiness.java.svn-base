// $Id: ListBatchNoBusiness.java,v 1.2 2007/02/07 04:18:42 suresh Exp $

//#CM709280
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listbatchno;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalBatchNoRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM709281
/**
 * Designer :  <BR>
 * Maker :  <BR>
 * <BR>
 * Note: This class is not available in the Basic version. <BR>
 * Allow this listbox class to search for Batch No. <BR>
 * Search for the databased on Batch No. entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Batch No. entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * 2. Button for the selected line (<CODE>lst_WBatchNo_Click</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	 Batch No. on the selected line to the parent screen and close the listbox.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:42 $
 * @author  $Author: suresh $
 */
public class ListBatchNoBusiness extends ListBatchNo implements WMSConstants
{
	//#CM709282
	// Class fields --------------------------------------------------
	//#CM709283
	/** 
	 * Use this key to pass the Batch No.
	 */
	public static final String BATCHNO_KEY = "BATCHNO_KEY";

	//#CM709284
	// Class variables -----------------------------------------------

	//#CM709285
	// Class methods -------------------------------------------------
	
	//#CM709286
	// Constructors --------------------------------------------------

	//#CM709287
	// Public methods ------------------------------------------------

	//#CM709288
	/**
	 * Initialize the screen. 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM709289
		// Set the screen name. 
		//#CM709290
		// Search for Batch No.
		lbl_ListName.setText(DisplayText.getText("TLE-W0904"));

		//#CM709291
		// Obtain the parameter. 
		//#CM709292
		// Consignor Code
		String consignorcode = request.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM709293
		// Planned Picking Date
		String retrievldate = request.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM709294
		// Batch No.
		String batchno = request.getParameter(BATCHNO_KEY);
		//#CM709295
		// Order/Item 
		String orderitem = request.getParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG);
		//#CM709296
		// Status flag
		String[] status = request.getParameterValues(ListRetrievalPlanDateBusiness.WORKSTATUSRETRIEVALPLANDATE_KEY);

		//#CM709297
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM709298
			// Close the connection. 
			sRet.closeConnection();
			//#CM709299
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM709300
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM709301
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM709302
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM709303
		// Planned Picking Date
		param.setRetrievalPlanDate(retrievldate);
		//#CM709304
		// Batch No.
		param.setBatchNo(batchno);
		//#CM709305
		// Order/Item 
		param.setItemOrderFlag(orderitem);
		//#CM709306
		// Status flag
		param.setSearchStatus(status);

		//#CM709307
		// Generate the SessionRetrievalBatchNoRet instance. 
		SessionRetrievalBatchNoRet listbox = new SessionRetrievalBatchNoRet(conn, param);
		//#CM709308
		// Maintain the list box in the Session 
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM709309
	/**
	 * Invoke this before invoking each control event. <BR>
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
	}

	//#CM709310
	// Package methods -----------------------------------------------

	//#CM709311
	// Protected methods ---------------------------------------------

	//#CM709312
	// Private methods -----------------------------------------------
	//#CM709313
	/**
	 * Allow this method to change a page.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(SessionRetrievalBatchNoRet listbox, String actionName) throws Exception
	{
		//#CM709314
		// Obtain locale.
		Locale locale = this.getHttpRequest().getLocale();

		//#CM709315
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM709316
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM709317
			// Set a value for Pager. 
			//#CM709318
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM709319
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM709320
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM709321
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM709322
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM709323
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM709324
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM709325
			// Delete all lines. 
			lst_WBatchNo.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM709326
				// Obtain the end line. 
				int count = lst_WBatchNo.getMaxRows();
				//#CM709327
				// Add a line. 
				lst_WBatchNo.addRow();

				//#CM709328
				// Move to the end line. 
				lst_WBatchNo.setCurrentRow(count);
				lst_WBatchNo.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_WBatchNo.setValue(2, rsparam[i].getBatchNo());
			}
		}
		else
		{
			//#CM709329
			// Set a value for Pager. 
			//#CM709330
			// Max count 
			pgr_U.setMax(0);
			//#CM709331
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM709332
			// Start Position 
			pgr_U.setIndex(0);
			//#CM709333
			// Max count 
			pgr_D.setMax(0);
			//#CM709334
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM709335
			// Start Position 
			pgr_D.setIndex(0);

			//#CM709336
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM709337
			// Hide the header. 
			lst_WBatchNo.setVisible(false);
			//#CM709338
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM709339
	// Event handler methods -----------------------------------------
	//#CM709340
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709341
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709342
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

	//#CM709343
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

	//#CM709344
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

	//#CM709345
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

	//#CM709346
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

	//#CM709347
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709348
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WBatchNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM709349
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WBatchNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM709350
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WBatchNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM709351
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WBatchNo_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM709352
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WBatchNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709353
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WBatchNo_Change(ActionEvent e) throws Exception
	{
	}

	//#CM709354
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WBatchNo_Click(ActionEvent e) throws Exception
	{

		//#CM709355
		// Set the current line. 
		lst_WBatchNo.setCurrentRow(lst_WBatchNo.getActiveRow());
		lst_WBatchNo.getValue(1);

		//#CM709356
		// Set the parameter needed to return to the parent screen. 
		ForwardParameters param = new ForwardParameters();

		//#CM709357
		// Batch No.
		param.setParameter(BATCHNO_KEY, lst_WBatchNo.getValue(2));

		//#CM709358
		// Shift to the parent screen. 
		parentRedirect(param);
	}

	//#CM709359
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		//#CM709360
		// Maintain the list box in the Session 
		SessionRetrievalBatchNoRet listbox = (SessionRetrievalBatchNoRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM709361
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		//#CM709362
		// Maintain the list box in the Session 
		SessionRetrievalBatchNoRet listbox = (SessionRetrievalBatchNoRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM709363
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		//#CM709364
		// Maintain the list box in the Session 
		SessionRetrievalBatchNoRet listbox = (SessionRetrievalBatchNoRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM709365
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM709366
		// Maintain the list box in the Session 
		SessionRetrievalBatchNoRet listbox = (SessionRetrievalBatchNoRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM709367
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709368
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM709369
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM709370
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM709371
				// Close the statement. 
				finder.close();
			}
			//#CM709372
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM709373
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM709374
		// Return to the parent screen. 
		parentRedirect(null);
	}

}
//#CM709375
//end of class
