// $Id: ListRetrievalShortageBusiness.java,v 1.2 2006/11/13 08:20:18 suresh Exp $

//#CM692413
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.listbox.listretrievalshortage;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.system.display.web.listbox.sessionret.SessionRetrievalShortageRet;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM692414
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * Allow this class to provide a Listbox for searching through the "Shortage info list".<BR>
 * Search for the data using the Added Date etc. entered via parent screen.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/02/14</TD><TD>Y.Okamura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:20:18 $
 * @author  $Author: suresh $
 */
public class ListRetrievalShortageBusiness extends ListRetrievalShortage implements WMSConstants
{
	//#CM692415
	// Class fields --------------------------------------------------
	//#CM692416
	/** 
	 * Use this key to pass Added Date.
	 */
	public static final String REGISTDATE_KEY = "REGISTDATE_KEY";

	//#CM692417
	/** 
	 * Use this key to pass Use this key to pass the Consignor code.
	 */
	public static final String CONSIGNORCODE_KEY = "CONSIGNORCODE_KEY";

	//#CM692418
	/** 
	 * Use this key to pass Aggregation Conditions.
	 */
	public static final String DISPTYPE_KEY = "DISPTYPE_KEY";

	//#CM692419
	// Class variables -----------------------------------------------

	//#CM692420
	// Class method --------------------------------------------------

	//#CM692421
	// Constructors --------------------------------------------------

	//#CM692422
	// Public methods ------------------------------------------------

	//#CM692423
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 *  Summary: Shows the Initial Display.<BR>
	 * <BR><DIR>
	 *       1.Obtain the parameters passed from the screen. <BR>
	 *       2.Display the title. <BR>
	 *       3.Close the existing sessions. <BR>
	 *       4.Display the search conditions on the screen. <BR>
	 *       5.Obtain the connection. <BR>
	 *       6.Generate an instance of session. <BR>
	 *     </DIR>
	 * [Parameter]<BR>
	 * <DIR>
	 *   Added Date/Time <BR>
	 *	 Consignor Code <BR>
	 *	 Aggregation Conditions <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM692424
		// Set the screen name.
		//#CM692425
		// View of Shortage check list
		lbl_ListName.setText(DisplayText.getText("TLE-W0098"));

		//#CM692426
		// Obtain the parameter that was set in the invoking source screen.
		//#CM692427
		// Added date
		String registDate = request.getParameter(REGISTDATE_KEY);
		//#CM692428
		// Consignor Code
		String consignorCode = request.getParameter(CONSIGNORCODE_KEY);
		//#CM692429
		// Aggregation Conditions
		String dispType = request.getParameter(DISPTYPE_KEY);

		//#CM692430
		// Check for input

		//#CM692431
		// Check for prohibited characters used in the consignor code.
		if (!WmsCheckker
			.charCheck(consignorCode, lst_ShortageInfoList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM692432
		//Close the connection of object remained in the Session.
		SessionRet session = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (session != null)
		{
			DatabaseFinder finder = session.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM692433
			//	Close the connection.
			session.closeConnection();
			//#CM692434
			// Delete from the session.
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM692435
		// Obtain the connection.
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM692436
		// Set the parameter.
		SystemParameter param = new SystemParameter();

		param.setRegistDate(WmsFormatter.getDate(registDate, 0, this.getHttpRequest().getLocale()));
		param.setConsignorCode(consignorCode);
		param.setDispType(dispType);

		//#CM692437
		//Generate a SessionWorkerQtyInquiryRet instance
		SessionRetrievalShortageRet listbox = new SessionRetrievalShortageRet(conn, param);

		this.getSession().setAttribute("LISTBOX", listbox);

		//#CM692438
		//Display the first page.
		setList(listbox, "first");

	}

	//#CM692439
	/**
	 * Invoke this before invoking each control event.<BR>
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
	}

	//#CM692440
	// Package methods -----------------------------------------------

	//#CM692441
	// Protected methods ---------------------------------------------

	//#CM692442
	// Private methods -----------------------------------------------
	//#CM692443
	/**
	 * Allow this method to change a page. <BR>
	 * @param listbox Session of this listbox
	 * @param actionName actionName for a clicked button
	 * @throws Exception Report all exceptions.
	 */
	private void setList(SessionRetrievalShortageRet listbox, String actionName) throws Exception
	{
		//#CM692444
		// Set the Set the Page info.
		listbox.setActionName(actionName);

		//#CM692445
		// Obtain the search result.
		SystemParameter[] param = (SystemParameter[]) listbox.getEntities();
		int len = 0;
		if (param != null)
			len = param.length;
			
		if (len > 0)
		{
			//#CM692446
			// Set a value for Pager.
			//#CM692447
			// Max count
			pgr_U.setMax(listbox.getLength());
			//#CM692448
			// Count of displayed data per Page
			pgr_U.setPage(listbox.getCondition());
			//#CM692449
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM692450
			// Max count
			pgr_D.setMax(listbox.getLength());
			//#CM692451
			// Count of displayed data per Page
			pgr_D.setPage(listbox.getCondition());
			//#CM692452
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM692453
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM692454
			// Delete all lines.
			lst_ShortageInfoList.clearRow();

			//#CM692455
			// Use it in ToolTip.
			//#CM692456
			// Consignor Name
			String titleConsignorName = DisplayText.getText("LBL-W0026");
			//#CM692457
			// Item Name
			String titleItemName = DisplayText.getText("LBL-W0103");
			//#CM692458
			// Case Order No.
			String titleCaseOrderNo = DisplayText.getText("LBL-W0011");
			//#CM692459
			// Piece Order No.
			String titlePieceOrderNo = DisplayText.getText("LBL-W0147");
			//#CM692460
			// Customer Name
			String titleCustomerName = DisplayText.getText("LBL-W0036");
			//#CM692461
			// Shipping Ticket No
			String titleShippingTicket = DisplayText.getText("LBL-W0206");
			//#CM692462
			// Shipping ticket line No.
			String titleShippingLineNo = DisplayText.getText("LBL-W0205");
			
			//#CM692463
			// Case Picking Location
			String titleCaseLocation = DisplayText.getText("LBL-W0017");
			//#CM692464
			// Piece Picking Location
			String titlePieceLocation = DisplayText.getText("LBL-W0152");

			//#CM692465
			// Planned Picking Date
			lbl_JavaSetPlanDate.setText(WmsFormatter.toDispDate(param[0].getPlanDate(), this.getHttpRequest().getLocale()));

			for (int i = 0; i < len; i++)
			{
				//#CM692466
				// Add a line.
				lst_ShortageInfoList.addRow();

				//#CM692467
				// Designate the Work Line to End Line.
				lst_ShortageInfoList.setCurrentRow(i + 1);
				//#CM692468
				// Consignor Code
				lst_ShortageInfoList.setValue(1, param[i].getConsignorCode());
				//#CM692469
				// Item Code
				lst_ShortageInfoList.setValue(2, param[i].getItemCode());
				//#CM692470
				// Packed Qty per Case
				lst_ShortageInfoList.setValue(3, WmsFormatter.getNumFormat(param[i].getEnteringQty()));
				//#CM692471
				// Planned Case Qty
				lst_ShortageInfoList.setValue(4, WmsFormatter.getNumFormat(param[i].getPlanCaseQty()));
				//#CM692472
				// Allocatable Case Qty
				lst_ShortageInfoList.setValue(5, WmsFormatter.getNumFormat(param[i].getEnableCaseQty()));
				//#CM692473
				// Shortage Case Qty
				lst_ShortageInfoList.setValue(6, WmsFormatter.getNumFormat(param[i].getShortageCaseQty()));
				//#CM692474
				// Case Picking Location
				lst_ShortageInfoList.setValue(7, param[i].getCaseLocation());
				//#CM692475
				// Consignor Name
				lst_ShortageInfoList.setValue(8, param[i].getConsignorName());
				//#CM692476
				// Item Name
				lst_ShortageInfoList.setValue(9, param[i].getItemName());
				//#CM692477
				// Packed qty per bundle
				lst_ShortageInfoList.setValue(10, WmsFormatter.getNumFormat(param[i].getBundleEnteringQty()));
				//#CM692478
				// Planned Piece Qty
				lst_ShortageInfoList.setValue(11, WmsFormatter.getNumFormat(param[i].getPlanPieceQty()));
				//#CM692479
				// Allocatable Piece Qty
				lst_ShortageInfoList.setValue(12, WmsFormatter.getNumFormat(param[i].getEnablePieceQty()));
				//#CM692480
				// Shortage Piece Qty
				lst_ShortageInfoList.setValue(13, WmsFormatter.getNumFormat(param[i].getShortagePieceQty()));
				//#CM692481
				// Piece Picking Location
				lst_ShortageInfoList.setValue(14, param[i].getPieceLocation());

				//#CM692482
				// Compile the ToolTip data.
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM692483
				// Consignor Name
				toolTip.add(titleConsignorName, param[i].getConsignorName());
				//#CM692484
				// Item Name
				toolTip.add(titleItemName, param[i].getItemName());
				//#CM692485
				// Case Order No.
				toolTip.add(titleCaseOrderNo, param[i].getCaseOrderNo());
				//#CM692486
				// Piece Order No.
				toolTip.add(titlePieceOrderNo, param[i].getPieceOrderNo());
				//#CM692487
				// Customer Name
				toolTip.add(titleCustomerName, param[i].getCustomerName());
				//#CM692488
				// Shipping Ticket No
				toolTip.add(titleShippingTicket, param[i].getShippingTicketNo());
				//#CM692489
				// Shipping ticket line No.
				toolTip.add(titleShippingLineNo, param[i].getShippingTicketLineNo());
				
				//#CM692490
				// Case Picking Location
				toolTip.add(titleCaseLocation, param[i].getCaseLocation());
				//#CM692491
				// Piece Picking Location
				toolTip.add(titlePieceLocation, param[i].getPieceLocation());

				//#CM692492
				// Set the TOOL TIP.	
				lst_ShortageInfoList.setToolTip(i + 1, toolTip.getText());
			}
		}
		else
		{
			//#CM692493
			// Set a value for Pager.
			//#CM692494
			// Max count
			pgr_U.setMax(0);
			//#CM692495
			// Count of displayed data per Page
			pgr_U.setPage(0);
			//#CM692496
			// Start Position
			pgr_U.setIndex(0);
			//#CM692497
			// Max count
			pgr_D.setMax(0);
			//#CM692498
			// Count of displayed data per Page
			pgr_D.setPage(0);
			//#CM692499
			// Start Position
			pgr_D.setIndex(0);

			//#CM692500
			// Check the count of the search results.
			String errorMsg = listbox.checkLength();
			//#CM692501
			// Hide the header.
			lst_ShortageInfoList.setVisible(false);
			//#CM692502
			// Display the error message.
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM692503
	// Event handler methods -----------------------------------------
	//#CM692504
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692505
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692506
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

	//#CM692507
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

	//#CM692508
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

	//#CM692509
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

	//#CM692510
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

	//#CM692511
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692512
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
		//#CM692513
		// Maintain the list box in the Session
		SessionRetrievalShortageRet listbox = (SessionRetrievalShortageRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM692514
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
		//#CM692515
		// Maintain the list box in the Session
		SessionRetrievalShortageRet listbox = (SessionRetrievalShortageRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM692516
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
		//#CM692517
		// Maintain the list box in the Session
		SessionRetrievalShortageRet listbox = (SessionRetrievalShortageRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM692518
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
		//#CM692519
		// Maintain the list box in the Session
		SessionRetrievalShortageRet listbox = (SessionRetrievalShortageRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM692520
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692521
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
		//#CM692522
		// Maintain the list box in the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM692523
		// If there is any value in the Session:
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM692524
				// Close the statement.
				finder.close();
			}
			//#CM692525
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM692526
		// Delete from the session.
		this.getSession().removeAttribute("LISTBOX");
		//#CM692527
		// Return to the parent screen.
		parentRedirect(null);
	}

	//#CM692528
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ShortageInfoList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM692529
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ShortageInfoList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM692530
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ShortageInfoList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM692531
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ShortageInfoList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM692532
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ShortageInfoList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692533
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ShortageInfoList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM692534
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ShortageInfoList_Click(ActionEvent e) throws Exception
	{
	}

	//#CM692535
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM692536
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetPlanDate_Server(ActionEvent e) throws Exception
	{
	}

}
//#CM692537
//end of class
