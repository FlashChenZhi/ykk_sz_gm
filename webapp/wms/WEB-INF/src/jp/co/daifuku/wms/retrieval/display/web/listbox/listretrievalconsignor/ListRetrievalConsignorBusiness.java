// $Id: ListRetrievalConsignorBusiness.java,v 1.2 2007/02/07 04:18:42 suresh Exp $

//#CM709380
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalPlanConsignorRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalResultConsignorRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalWorkInfoConsignorRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM709381
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this listbox class to search for Consignor. <BR>
 * Search for the databased on Consignor code entered via parent screen as a key entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	 Search for the data using Consignor Code entered via parent screen as a key, and display it on the screen. <BR>
 * <BR>
 * </DIR>
 * 2. Button for the selected line (<CODE>lst_ConsignorSearch_Click</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	 Pass the Consignor Code, and Consignor Name on the selected line to the parent screen and close the listbox. <BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/20</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:42 $
 * @author  $Author: suresh $
 */
public class ListRetrievalConsignorBusiness extends ListRetrievalConsignor implements WMSConstants
{
	//#CM709382
	// Class fields --------------------------------------------------
	//#CM709383
	/** 
	 * Use this key to pass the Consignor code. 
	 */
	public static final String CONSIGNORCODE_KEY = "CONSIGNORCODE_KEY";

	//#CM709384
	/** 
	 * Use this key to pass the Consignor name. 
	 */
	public static final String CONSIGNORNAME_KEY = "CONSIGNORNAME_KEY";

	//#CM709385
	/** 
	 * Use this key to pass the Planned Picking Date.
	 */
	public static final String RETRIEVALPLANDATE_KEY = "RETRIEVALPLANDATE_KEY";

	//#CM709386
	/** 
	 * Use this key to pass the search flag. 
	 */
	public static final String SEARCHCONSIGNOR_KEY = "SEARCHCONSIGNOR_KEY";

	//#CM709387
	/** 
	 * Use this key to pass the work status.
	 */
	public static final String WORKSTATUSCONSIGNOR_KEY = "WORKSTATUSCONSIGNOR_KEY";
	
	//#CM709388
	/** 
	 * Use this key to distinguish the data for Order/Item 
	 */
	public static final String ORDER_ITEM_FLAG = "ORDER_ITEM_FLAG";
	
	//#CM709389
	/** 
	 * Use this key to pass the area type flag. 
	 */
	public static final String AREA_TYPE_KEY = "AREA_TYPE_KEY";	

	//#CM709390
	/** 
	 * Use this key to pass the allocation flag.
	 */
	public static final String ALLOCATION_FLAG_KEY = "ALLOCATION_FLAG_KEY";	

	//#CM709391
	/** 
	 * Use this key to pass the schedule flag.
	 */
	public static final String SCHEDULE_FLAG_KEY = "SCHEDULE_FLAG_KEY";
	
	//#CM709392
	// Class variables -----------------------------------------------

	//#CM709393
	// Class method --------------------------------------------------

	//#CM709394
	// Constructors --------------------------------------------------

	//#CM709395
	// Public methods ------------------------------------------------

	//#CM709396
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Field item  <BR>
	 *	<DIR>
	 *		 Select <BR>
	 *		Consignor Code <BR>
	 *		Consignor Name <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM709397
		// Set the screen name. 
		//#CM709398
		// Search Consignor 
		lbl_ListName.setText(DisplayText.getText("TLE-W0012"));

		//#CM709399
		// Obtain the parameter. 
		//#CM709400
		// Consignor Code
		String consignorcode = request.getParameter(CONSIGNORCODE_KEY);
		//#CM709401
		// Planned Picking Date
		String retrievalplandate = request.getParameter(RETRIEVALPLANDATE_KEY);
		//#CM709402
		// "Search" flag 
		String searchconsignor = request.getParameter(SEARCHCONSIGNOR_KEY);
		//#CM709403
		// Order/Item 
		String orderitem = request.getParameter(ORDER_ITEM_FLAG);
		//#CM709404
		// Work status:
		String[] search = request.getParameterValues(WORKSTATUSCONSIGNOR_KEY);
		//#CM709405
		// Area Type flag 
		String areatypeflag = request.getParameter(AREA_TYPE_KEY);
		//#CM709406
		// Allocation flag 
		String allocationflag = request.getParameter(ALLOCATION_FLAG_KEY);
		//#CM709407
		// Schedule flag 
		String[] schFlag = request.getParameterValues(SCHEDULE_FLAG_KEY);

		viewState.setString(SEARCHCONSIGNOR_KEY, searchconsignor);

		//#CM709408
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM709409
			// Close the connection. 
			sRet.closeConnection();
			//#CM709410
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM709411
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM709412
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM709413
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM709414
		// Planned Picking Date
		param.setRetrievalPlanDate(retrievalplandate);				
		//#CM709415
		// Work status:
		param.setSearchStatus(search);
		//#CM709416
		// Order/Item 
		param.setItemOrderFlag(orderitem);
		//#CM709417
		// Area Type flag 
		param.setAreaTypeFlag(areatypeflag);
		//#CM709418
		// Allocation flag 
		param.setAllocationFlag(allocationflag);
		//#CM709419
		// Schedule flag 
		param.setScheduleFlagArray(schFlag);

		//#CM709420
		// Determine whether the Consignor to be searched is Work or Plan or Result. 
		if (searchconsignor.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM709421
			// Generate the SessionRetrievalPlanConsignorRet instance. 
			SessionRetrievalPlanConsignorRet listbox =
				new SessionRetrievalPlanConsignorRet(conn, param);
			//#CM709422
			// Maintain the list box in the Session 
			this.getSession().setAttribute("LISTBOX", listbox);
			setPlanList(listbox, "first");
		}
		else if (searchconsignor.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM709423
			// Generate the SessionRetrievalResultConsignorRet instance. 
			SessionRetrievalResultConsignorRet listbox =
				new SessionRetrievalResultConsignorRet(conn, param);
			//#CM709424
			// Maintain the list box in the Session 
			this.getSession().setAttribute("LISTBOX", listbox);
			setResultList(listbox, "first");
		}
		else if (searchconsignor.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM709425
			// Generate the SessionRetrievalWorkInfoConsignorRet instance. 
			SessionRetrievalWorkInfoConsignorRet listbox =
				new SessionRetrievalWorkInfoConsignorRet(conn, param);
			//#CM709426
			// Maintain the list box in the Session 
			this.getSession().setAttribute("LISTBOX", listbox);
			setWorkInfoList(listbox, "first");
		}
		else
		{
			Object[] tObj = new Object[1];
			tObj[0] = DisplayText.getText("LBL-W0024");
			String classname = this.getClass().getName();
			RmiMsgLogClient.write(6007039, LogMessage.F_ERROR, classname, tObj);
			throw (new InvalidStatusException("6007039" + wDelim + tObj[0]));
		}
	}

	//#CM709427
	// Package methods -----------------------------------------------

	//#CM709428
	// Protected methods ---------------------------------------------

	//#CM709429
	// Private methods -----------------------------------------------

	//#CM709430
	/**
	 * Allow this method to change a page.  <BR>
	 * Search through the Plan table.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setPlanList(SessionRetrievalPlanConsignorRet listbox, String actionName)
		throws Exception
	{
		//#CM709431
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM709432
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM709433
			// Set a value for Pager. 
			//#CM709434
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM709435
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM709436
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM709437
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM709438
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM709439
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM709440
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM709441
			// Delete all lines. 
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM709442
				// Obtain the end line. 
				int count = lst_ConsignorSearch.getMaxRows();
				//#CM709443
				// Add a line. 
				lst_ConsignorSearch.addRow();

				//#CM709444
				// Move to the end line. 
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, rsparam[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, rsparam[i].getConsignorName());
			}
		}
		else
		{
			//#CM709445
			// Set a value for Pager. 
			//#CM709446
			// Max count 
			pgr_U.setMax(0);
			//#CM709447
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM709448
			// Start Position 
			pgr_U.setIndex(0);
			//#CM709449
			// Max count 
			pgr_D.setMax(0);
			//#CM709450
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM709451
			// Start Position 
			pgr_D.setIndex(0);

			//#CM709452
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM709453
			// Hide the header. 
			lst_ConsignorSearch.setVisible(false);
			//#CM709454
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM709455
	/**
	 * Allow this method to change a page.  <BR>
	 * Search through the result table.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setResultList(SessionRetrievalResultConsignorRet listbox, String actionName)
		throws Exception
	{
		//#CM709456
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM709457
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM709458
			// Set a value for Pager. 
			//#CM709459
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM709460
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM709461
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM709462
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM709463
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM709464
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM709465
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM709466
			// Delete all lines. 
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM709467
				// Obtain the end line. 
				int count = lst_ConsignorSearch.getMaxRows();
				//#CM709468
				// Add a line. 
				lst_ConsignorSearch.addRow();

				//#CM709469
				// Move to the end line. 
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, rsparam[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, rsparam[i].getConsignorName());
			}
		}
		else
		{
			//#CM709470
			// Set a value for Pager. 
			//#CM709471
			// Max count 
			pgr_U.setMax(0);
			//#CM709472
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM709473
			// Start Position 
			pgr_U.setIndex(0);
			//#CM709474
			// Max count 
			pgr_D.setMax(0);
			//#CM709475
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM709476
			// Start Position 
			pgr_D.setIndex(0);

			//#CM709477
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM709478
			// Hide the header. 
			lst_ConsignorSearch.setVisible(false);
			//#CM709479
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM709480
	/**
	 * Allow this method to change a page.  <BR>
	 * Search through the Work table.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setWorkInfoList(SessionRetrievalWorkInfoConsignorRet listbox, String actionName)
		throws Exception
	{
		//#CM709481
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM709482
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM709483
			// Set a value for Pager. 
			//#CM709484
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM709485
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM709486
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM709487
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM709488
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM709489
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM709490
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM709491
			// Delete all lines. 
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM709492
				// Obtain the end line. 
				int count = lst_ConsignorSearch.getMaxRows();
				//#CM709493
				// Add a line. 
				lst_ConsignorSearch.addRow();

				//#CM709494
				// Move to the end line. 
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, rsparam[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, rsparam[i].getConsignorName());
			}
		}
		else
		{
			//#CM709495
			// Set a value for Pager. 
			//#CM709496
			// Max count 
			pgr_U.setMax(0);
			//#CM709497
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM709498
			// Start Position 
			pgr_U.setIndex(0);
			//#CM709499
			// Max count 
			pgr_D.setMax(0);
			//#CM709500
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM709501
			// Start Position 
			pgr_D.setIndex(0);

			//#CM709502
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM709503
			// Hide the header. 
			lst_ConsignorSearch.setVisible(false);
			//#CM709504
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM709505
	// Event handler methods -----------------------------------------
	//#CM709506
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709507
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709508
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

	//#CM709509
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

	//#CM709510
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

	//#CM709511
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

	//#CM709512
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

	//#CM709513
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709514
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM709515
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM709516
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM709517
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM709518
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709519
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM709520
	/** 
	 * Clicking on the Select button of listcell executes its proper process.  <BR>
	 * <BR>
	 *	 Pass the Consignor Code, and Consignor Name to the parent screen and close the listbox.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lst_ConsignorSearch_Click(ActionEvent e) throws Exception
	{
		//#CM709521
		// Set the current line. 
		lst_ConsignorSearch.setCurrentRow(lst_ConsignorSearch.getActiveRow());
		lst_ConsignorSearch.getValue(1);

		//#CM709522
		// Set the parameter needed to return to the parent screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM709523
		// Consignor Code
		param.setParameter(CONSIGNORCODE_KEY, lst_ConsignorSearch.getValue(2));
		//#CM709524
		// Consignor Name
		param.setParameter(CONSIGNORNAME_KEY, lst_ConsignorSearch.getValue(3));

		//#CM709525
		// Shift to the parent screen. 
		parentRedirect(param);
	}

	//#CM709526
	/** 
	 * Execute the process defined for clicking on > button.  <BR>
	 * <BR>
	 * Display the next page.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		//#CM709527
		// Obtain the search flag. 
		String flag = viewState.getString(SEARCHCONSIGNOR_KEY);

		if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM709528
			// Maintain the list box in the Session 
			SessionRetrievalPlanConsignorRet listbox =
				(SessionRetrievalPlanConsignorRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "next");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM709529
			// Maintain the list box in the Session 
			SessionRetrievalResultConsignorRet listbox =
				(SessionRetrievalResultConsignorRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "next");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM709530
			// Maintain the list box in the Session 
			SessionRetrievalWorkInfoConsignorRet listbox =
				(SessionRetrievalWorkInfoConsignorRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "next");
		}
	}

	//#CM709531
	/** 
	 * Clicking on < button executes its process.  <BR>
	 * <BR>
	 * Display the previous one page.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		//#CM709532
		// Obtain the search flag. 
		String flag = viewState.getString(SEARCHCONSIGNOR_KEY);

		if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM709533
			// Maintain the list box in the Session 
			SessionRetrievalPlanConsignorRet listbox =
				(SessionRetrievalPlanConsignorRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "previous");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM709534
			// Maintain the list box in the Session 
			SessionRetrievalResultConsignorRet listbox =
				(SessionRetrievalResultConsignorRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "previous");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM709535
			// Maintain the list box in the Session 
			SessionRetrievalWorkInfoConsignorRet listbox =
				(SessionRetrievalWorkInfoConsignorRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "previous");
		}
	}

	//#CM709536
	/** 
	 * Clicking >> button executes its process.  <BR>
	 * <BR>
	 * Display the end page.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		//#CM709537
		// Obtain the search flag. 
		String flag = viewState.getString(SEARCHCONSIGNOR_KEY);

		if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM709538
			// Maintain the list box in the Session 
			SessionRetrievalPlanConsignorRet listbox =
				(SessionRetrievalPlanConsignorRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "last");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM709539
			// Maintain the list box in the Session 
			SessionRetrievalResultConsignorRet listbox =
				(SessionRetrievalResultConsignorRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "last");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM709540
			// Maintain the list box in the Session 
			SessionRetrievalWorkInfoConsignorRet listbox =
				(SessionRetrievalWorkInfoConsignorRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "last");
		}
	}

	//#CM709541
	/** 
	 * Execute the process defined for clicking on << button.  <BR>
	 * <BR>
	 * Display the top page.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM709542
		// Obtain the search flag. 
		String flag = viewState.getString(SEARCHCONSIGNOR_KEY);

		if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM709543
			// Maintain the list box in the Session 
			SessionRetrievalPlanConsignorRet listbox =
				(SessionRetrievalPlanConsignorRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "first");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM709544
			// Maintain the list box in the Session 
			SessionRetrievalResultConsignorRet listbox =
				(SessionRetrievalResultConsignorRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "first");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM709545
			// Maintain the list box in the Session 
			SessionRetrievalWorkInfoConsignorRet listbox =
				(SessionRetrievalWorkInfoConsignorRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "first");
		}
	}

	//#CM709546
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709547
	/** 
	 * Execute the process defined for clicking on the Close button. <BR>
	 *  <BR>
	 * Close the listbox and shift to the parent screen.  <BR>
	 *  <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM709548
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM709549
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM709550
				// Close the statement. 
				finder.close();
			}
			//#CM709551
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM709552
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM709553
		// Return to the parent screen. 
		parentRedirect(null);
	}
}
//#CM709554
//end of class
