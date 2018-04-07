// $Id: ListStorageDateBusiness.java,v 1.2 2006/12/07 08:57:42 suresh Exp $

//#CM568376
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.listbox.liststoragedate;

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
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStorageResultDateRet;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM568377
/**
 * Designer : H.Murakdo <BR>
 * Maker : H.Murakado <BR>
 * <BR>
 * The Storage date retrieval list box class. <BR>
 * Retrieve it based on Consignor Code and Storage date input from the parents screen. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Initial screen (<CODE>page_Load</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 	Retrieve by making Storage date input from the parents screen as a key, and display it on the screen. <BR>
 * </DIR>
 * <BR>
 * 2.Button of selected line (<CODE>lst_ListStorageDate_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 	Pass Storage date of the Selected line to the parents screen, and close the list box. <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/19</TD><TD>H.Murakado</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:42 $
 * @author  $Author: suresh $
 */
public class ListStorageDateBusiness extends ListStorageDate implements WMSConstants
{
	//#CM568378
	// Class fields --------------------------------------------------
	//#CM568379
	/** 
	 * The key used to hand over Storage date. 
	 */
	public static final String STORAGEDATE_KEY = "STORAGEPLANDATE_KEY";

	//#CM568380
	/** 
	 * The key used to hand over Start Storage date. 
	 */
	public static final String STARTSTORAGEDATE_KEY = "STARTSTORAGEDATE_KEY";

	//#CM568381
	/** 
	 * The key used to hand over End Storage date. 
	 */
	public static final String ENDSTORAGEDATE_KEY = "ENDSTORAGEDATE_KEY";

	//#CM568382
	/** 
	 * The key used to hand over Range flag of storage date. 
	 */
	public static final String RANGESTORAGEDATE_KEY = "RANGESTORAGEDATE_KEY";

	//#CM568383
	// Class variables -----------------------------------------------

	//#CM568384
	// Class method --------------------------------------------------

	//#CM568385
	// Constructors --------------------------------------------------

	//#CM568386
	// Public methods ------------------------------------------------

	//#CM568387
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *		Selection <BR>
	 *		Storage date <BR>
	 *	</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{

		//#CM568388
		// Set the Screen name
		//#CM568389
		// Storage date retrieval
		lbl_ListName.setText(DisplayText.getText("TLE-W0087"));
		//#CM568390
		// Parameter is acquired. 
		//#CM568391
		// Consignor Code
		String consignorcode = request.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM568392
		// Start Storage date
		String startstoragedate = request.getParameter(STARTSTORAGEDATE_KEY);
		//#CM568393
		// End Storage date
		String endstoragedate = request.getParameter(ENDSTORAGEDATE_KEY);
		//#CM568394
		// Range flag of stock day
		String rangestoragedate = request.getParameter(RANGESTORAGEDATE_KEY);

		viewState.setString(RANGESTORAGEDATE_KEY, rangestoragedate);

		//#CM568395
		// Close the connection of the object left in Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM568396
			// Connection close
			sRet.closeConnection();
			//#CM568397
			// Delete it from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM568398
		// Acquisition of connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM568399
		// Set Parameter
		StorageSupportParameter param = new StorageSupportParameter();
		//#CM568400
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM568401
		// Start Storage date
		param.setFromWorkDate(startstoragedate);
		//#CM568402
		// End Storage date
		param.setToWorkDate(endstoragedate);

		//#CM568403
		// SessionStorageDateRet  instance generation
		SessionStorageResultDateRet listbox = new SessionStorageResultDateRet(conn, param);
		//#CM568404
		// Listbox is maintained in Session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM568405
	// Package methods -----------------------------------------------

	//#CM568406
	// Protected methods ---------------------------------------------

	//#CM568407
	// Private methods -----------------------------------------------
	//#CM568408
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired by the displayed page and data is set in List cell.  .<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionStorageResultDateRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setList(SessionStorageResultDateRet listbox, String actionName) throws Exception
	{
		//#CM568409
		// The locale is acquired. 
		Locale locale = this.getHttpRequest().getLocale();

		//#CM568410
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM568411
		// The retrieval result is acquired. 
		StorageSupportParameter[] storageplan = (StorageSupportParameter[])listbox.getEntities();
		int len = 0;
		if (storageplan != null)
			len = storageplan.length;
		if (len > 0)
		{
			//#CM568412
			// Value set to Pager
			//#CM568413
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568414
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568415
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568416
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568417
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568418
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568419
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568420
			// Delete all lines
			lst_ListStorageDate.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568421
				// The final line is acquired
				int count = lst_ListStorageDate.getMaxRows();
				//#CM568422
				// Add row
				lst_ListStorageDate.addRow();

				//#CM568423
				// It moves to the final line. 
				lst_ListStorageDate.setCurrentRow(count);
				lst_ListStorageDate.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListStorageDate.setValue(2, WmsFormatter.toDispDate(storageplan[i].getWorkDate(), locale));
			}
		}
		else
		{
			//#CM568424
			// Value set to Pager
			//#CM568425
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568426
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568427
			// Starting position
			pgr_U.setIndex(0);
			//#CM568428
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568429
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568430
			// Starting position
			pgr_D.setIndex(0);

			//#CM568431
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568432
			// Conceal the header
			lst_ListStorageDate.setVisible(false);
			//#CM568433
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM568434
	// Event handler methods -----------------------------------------
	//#CM568435
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568436
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568437
	/** 
	 * Do the Processing when the Close button is pressed. <BR>
	 *  <BR>
	 * The list box is closed, and changes to the parents screen.  <BR>
	 *  <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		btn_Close_D_Click(e);
	}

	//#CM568438
	/** 
	 * Do the Processing when the [>] button is pressed. <BR>
	 * <BR>
	 * Display the subsequent page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	//#CM568439
	/** 
	 * Do the Processing when the [<] button is pressed. <BR>
	 * <BR>
	 * Display the previous page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
		pgr_D_Prev(e);
	}

	//#CM568440
	/** 
	 * Do the Processing when the [>>] button is pressed. <BR>
	 * <BR>
	 * Display the last page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		pgr_D_Last(e);
	}

	//#CM568441
	/** 
	 * Do the Processing when the [<<] button is pressed. <BR>
	 * <BR>
	 * Display the first page. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		pgr_D_First(e);
	}

	//#CM568442
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568443
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM568444
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM568445
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageDate_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM568446
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageDate_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM568447
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568448
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListStorageDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM568449
	/** 
	 * Process when Selection button of List cell is pressed.  <BR>
	 * <BR>
	 *	Pass Storage date to the parents screen, and close the list box.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void lst_ListStorageDate_Click(ActionEvent e) throws Exception
	{
		//#CM568450
		// Range flag of storage date is acquired. 
		String flug = viewState.getString(RANGESTORAGEDATE_KEY);

		//#CM568451
		// A present line is set. 
		lst_ListStorageDate.setCurrentRow(lst_ListStorageDate.getActiveRow());
		lst_ListStorageDate.getValue(1);

		//#CM568452
		// Set Parameter to be returned to parents screen
		ForwardParameters param = new ForwardParameters();
		if (flug == null)
		{
			//#CM568453
			// Storage date
			param.setParameter(STORAGEDATE_KEY, lst_ListStorageDate.getValue(2));
		}
		else if (flug.equals(StorageSupportParameter.RANGE_START))
		{
			//#CM568454
			// Start Storage date
			param.setParameter(STARTSTORAGEDATE_KEY, lst_ListStorageDate.getValue(2));
		}
		else if (flug.equals(StorageSupportParameter.RANGE_END))
		{
			//#CM568455
			// End Storage date
			param.setParameter(ENDSTORAGEDATE_KEY, lst_ListStorageDate.getValue(2));
		}
		//#CM568456
		// Changes to the parents screen. 
		parentRedirect(param);
	}

	//#CM568457
	/** 
	 * Do the Processing when the [>] button is pressed. <BR>
	 * <BR>
	 * Display the subsequent page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		//#CM568458
		// Listbox is maintained in Session
		SessionStorageResultDateRet listbox = (SessionStorageResultDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM568459
	/** 
	 * Do the Processing when the [<] button is pressed. <BR>
	 * <BR>
	 * Display the previous page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		//#CM568460
		// Listbox is maintained in Session
		SessionStorageResultDateRet listbox = (SessionStorageResultDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM568461
	/** 
	 * Do the Processing when the [>>] button is pressed. <BR>
	 * <BR>
	 * Display the last page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		//#CM568462
		// Listbox is maintained in Session
		SessionStorageResultDateRet listbox = (SessionStorageResultDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM568463
	/** 
	 * Do the Processing when the [<<] button is pressed. <BR>
	 * <BR>
	 * Display the first page. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM568464
		// Listbox is maintained in Session
		SessionStorageResultDateRet listbox = (SessionStorageResultDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM568465
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568466
	/** 
	 * Do the Processing when the Close button is pressed. <BR>
	 *  <BR>
	 * The list box is closed, and changes to the parents screen.  <BR>
	 *  <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM568467
		// Listbox is maintained in Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM568468
		// When there is a value in Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM568469
				// Close the statement.
				finder.close();
			}
			//#CM568470
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM568471
		// Delete it from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM568472
		// Return to the parents screen
		parentRedirect(null);
	}


}
//#CM568473
//end of class
