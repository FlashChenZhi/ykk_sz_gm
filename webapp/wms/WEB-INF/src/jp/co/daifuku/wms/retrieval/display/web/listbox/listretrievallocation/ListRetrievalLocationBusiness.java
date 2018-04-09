// $Id: ListRetrievalLocationBusiness.java,v 1.2 2007/02/07 04:18:49 suresh Exp $

//#CM710540
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievallocation;
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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalCaseLocationRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalPieceLocationRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalPlanLocationRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM710541
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this listbox class to search for Picking locations. <BR>
 * Search for the databased on Picking Location entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Picking Location entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * 2. Button for the selected line (<CODE>lst_ShpAcpDateSrch_Click</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Pass the Picking Location on the selected line to the parent screen and close the listbox.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/21</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:49 $
 * @author  $Author: suresh $
 */
public class ListRetrievalLocationBusiness extends ListRetrievalLocation implements WMSConstants
{
	//#CM710542
	// Class fields --------------------------------------------------
	//#CM710543
	/** 
	 * Use this key to pass the Case Picking Location.
	 */
	public static final String CASERETRIEVALLOCATION_KEY = "CASERETRIEVALLOCATION_KEY";

	//#CM710544
	/** 
	 * Use this key to pass the Piece Picking Location.
	 */
	public static final String PIECERETRIEVALLOCATION_KEY = "PIECERETRIEVALLOCATION_KEY";

	//#CM710545
	/** 
	 * Use this key to pass the Picking Location.
	 */
	public static final String RETRIEVALLOCATION_KEY = "RETRIEVALLOCATION_KEY";

	//#CM710546
	/** 
	 * Use this key to pass the Case/Piece flag.
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";

	//#CM710547
	/** 
	 * Use this key to pass the Case/Piece flag to search a listbox.
	 * Determine the Case Picking Location or Piece Picking Location size. 
	 */
	public static final String CASE_PIECE_KEY = "CASE_PIECE_KEY";

	//#CM710548
	/** 
	 * Use this key to pass the work status.
	 */
	public static final String WORKSTATUSLOCATION_KEY = "WORKSTATUSLOCATION_KEY";

	//#CM710549
	// Class variables -----------------------------------------------

	//#CM710550
	// Class method --------------------------------------------------

	//#CM710551
	// Constructors --------------------------------------------------

	//#CM710552
	// Public methods ------------------------------------------------

	//#CM710553
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Field item  <BR>
	 *	<DIR>
	 *		 Select <BR>
	 *		 Picking Location  <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM710554
		// Set the screen name. 
		//#CM710555
		// Search for the Picking Location.
		lbl_ListName.setText(DisplayText.getText("TLE-W0072"));

		//#CM710556
		// Obtain the parameter. 
		//#CM710557
		// Consignor Code
		String consignorcode = request.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM710558
		// Planned Picking Date
		String retrievlplandate = request.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM710559
		// Item Code
		String itemcode = request.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);
		//#CM710560
		// Picking Location 
		String retrievallocation = request.getParameter(RETRIEVALLOCATION_KEY);
		//#CM710561
		// Case Picking Location
		String caseretrievallocation = request.getParameter(CASERETRIEVALLOCATION_KEY);
		//#CM710562
		// Piece Picking Location 
		String pieceretrievallocation = request.getParameter(PIECERETRIEVALLOCATION_KEY);
		//#CM710563
		// Case/Piece flag 
		String casepieceflag = request.getParameter(CASEPIECEFLAG_KEY);
		//#CM710564
		// Flag to determine Case Picking Location/Piece Picking Location. 
		String casepiece = request.getParameter(CASE_PIECE_KEY);
		//#CM710565
		// Work status:
		String[] search = request.getParameterValues(WORKSTATUSLOCATION_KEY);

		viewState.setString(CASE_PIECE_KEY, casepiece);

		//#CM710566
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM710567
			// Close the connection. 
			sRet.closeConnection();
			//#CM710568
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM710569
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM710570
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM710571
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM710572
		// Planned Picking Date
		param.setRetrievalPlanDate(retrievlplandate);
		//#CM710573
		// Item Code
		param.setItemCode(itemcode);
		//#CM710574
		// Picking Location 
		param.setRetrievalLocation(retrievallocation);
		//#CM710575
		// Case Picking Location
		param.setCaseLocation(caseretrievallocation);
		//#CM710576
		// Piece Picking Location 
		param.setPieceLocation(pieceretrievallocation);
		//#CM710577
		// Work status:
		param.setSearchStatus(search);
		//#CM710578
		// Case/Piece flag 
		param.setCasePieceflg(casepieceflag);

		//#CM710579
		// Determine whether the Location to be searched is Case or Piece. 
		if (casepiece.equals(RetrievalSupportParameter.LISTBOX_CASE))
		{
			//#CM710580
			// Generate the SessionRetrievalCaseLocationRet instance. 
			SessionRetrievalCaseLocationRet listbox = new SessionRetrievalCaseLocationRet(conn, param);
			//#CM710581
			// Maintain the list box in the Session 
			this.getSession().setAttribute("LISTBOX", listbox);
			setCaseList(listbox, "first");
		}
		else if (casepiece.equals(RetrievalSupportParameter.LISTBOX_PIECE))
		{
			//#CM710582
			// Generate the SessionRetrievalPieceLocationRet instance. 
			SessionRetrievalPieceLocationRet listbox = new SessionRetrievalPieceLocationRet(conn, param);
			//#CM710583
			// Maintain the list box in the Session 
			this.getSession().setAttribute("LISTBOX", listbox);
			setPieceList(listbox, "first");
		}
		else if (casepiece.equals(RetrievalSupportParameter.LISTBOX_RETRIEVAL))
		{
			//#CM710584
			// Use it via Add Plan screen. 
			//#CM710585
			// Generate the SessionRetrievalPlanLocationRet instance. 
			SessionRetrievalPlanLocationRet listbox = new SessionRetrievalPlanLocationRet(conn, param);
			this.getSession().setAttribute("LISTBOX", listbox);
			setList(listbox, "first");
		}
		else
		{
			Object[] tObj = new Object[1];
			tObj[0] = DisplayText.getText("LBL-W0172");
			String classname = this.getClass().getName();
			RmiMsgLogClient.write(6007039, LogMessage.F_ERROR, classname, tObj);
			throw (new InvalidStatusException("6007039" + wDelim + tObj[0]));
		}
	}

	//#CM710586
	// Package methods -----------------------------------------------

	//#CM710587
	// Protected methods ---------------------------------------------

	//#CM710588
	// Private methods -----------------------------------------------
	//#CM710589
	/**
	 * Allow this method to change a page.  <BR>
	 * Search for Case Picking Location.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setCaseList(SessionRetrievalCaseLocationRet listbox, String actionName) throws Exception
	{
		//#CM710590
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM710591
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM710592
			// Set a value for Pager. 
			//#CM710593
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM710594
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM710595
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM710596
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM710597
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM710598
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM710599
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM710600
			// Delete all lines. 
			lst_ListRtrivlLocation.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM710601
				// Obtain the end line. 
				int count = lst_ListRtrivlLocation.getMaxRows();
				//#CM710602
				// Add a line. 
				lst_ListRtrivlLocation.addRow();

				//#CM710603
				// Move to the end line. 
				lst_ListRtrivlLocation.setCurrentRow(count);
				lst_ListRtrivlLocation.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListRtrivlLocation.setValue(2, rsparam[i].getCaseLocation());
			}
		}
		else
		{
			//#CM710604
			// Set a value for Pager. 
			//#CM710605
			// Max count 
			pgr_U.setMax(0);
			//#CM710606
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM710607
			// Start Position 
			pgr_U.setIndex(0);
			//#CM710608
			// Max count 
			pgr_D.setMax(0);
			//#CM710609
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM710610
			// Start Position 
			pgr_D.setIndex(0);

			//#CM710611
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM710612
			// Hide the header. 
			lst_ListRtrivlLocation.setVisible(false);
			//#CM710613
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM710614
	/**
	 * Allow this method to change a page.  <BR>
	 * Search for Piece Picking Location.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setPieceList(SessionRetrievalPieceLocationRet listbox, String actionName) throws Exception
	{
		//#CM710615
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM710616
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM710617
			// Set a value for Pager. 
			//#CM710618
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM710619
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM710620
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM710621
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM710622
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM710623
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM710624
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM710625
			// Delete all lines. 
			lst_ListRtrivlLocation.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM710626
				// Obtain the end line. 
				int count = lst_ListRtrivlLocation.getMaxRows();
				//#CM710627
				// Add a line. 
				lst_ListRtrivlLocation.addRow();

				//#CM710628
				// Move to the end line. 
				lst_ListRtrivlLocation.setCurrentRow(count);
				lst_ListRtrivlLocation.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListRtrivlLocation.setValue(2, rsparam[i].getPieceLocation());
			}
		}
		else
		{
			//#CM710629
			// Set a value for Pager. 
			//#CM710630
			// Max count 
			pgr_U.setMax(0);
			//#CM710631
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM710632
			// Start Position 
			pgr_U.setIndex(0);
			//#CM710633
			// Max count 
			pgr_D.setMax(0);
			//#CM710634
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM710635
			// Start Position 
			pgr_D.setIndex(0);

			//#CM710636
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM710637
			// Hide the header. 
			lst_ListRtrivlLocation.setVisible(false);
			//#CM710638
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM710639
	/**
	 * Allow this method to change a page.  <BR>
	 * Search for Picking Location.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(SessionRetrievalPlanLocationRet listbox, String actionName) throws Exception
	{
		//#CM710640
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM710641
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM710642
			// Set a value for Pager. 
			//#CM710643
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM710644
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM710645
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM710646
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM710647
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM710648
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM710649
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM710650
			// Delete all lines. 
			lst_ListRtrivlLocation.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM710651
				// Obtain the end line. 
				int count = lst_ListRtrivlLocation.getMaxRows();
				//#CM710652
				// Add a line. 
				lst_ListRtrivlLocation.addRow();

				//#CM710653
				// Move to the end line. 
				lst_ListRtrivlLocation.setCurrentRow(count);
				lst_ListRtrivlLocation.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListRtrivlLocation.setValue(2, rsparam[i].getRetrievalLocation());
			}
		}
		else
		{
			//#CM710654
			// Set a value for Pager. 
			//#CM710655
			// Max count 
			pgr_U.setMax(0);
			//#CM710656
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM710657
			// Start Position 
			pgr_U.setIndex(0);
			//#CM710658
			// Max count 
			pgr_D.setMax(0);
			//#CM710659
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM710660
			// Start Position 
			pgr_D.setIndex(0);

			//#CM710661
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM710662
			// Hide the header. 
			lst_ListRtrivlLocation.setVisible(false);
			//#CM710663
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM710664
	// Event handler methods -----------------------------------------
	//#CM710665
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710666
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710667
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

	//#CM710668
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

	//#CM710669
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

	//#CM710670
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

	//#CM710671
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

	//#CM710672
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710673
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM710674
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM710675
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM710676
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlLocation_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM710677
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710678
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlLocation_Change(ActionEvent e) throws Exception
	{
	}

	//#CM710679
	/** 
	 * Clicking on the Select button of listcell executes its proper process.  <BR>
	 * <BR>
	 *	Pass the Picking Location to the parent screen and close the listbox. <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lst_ListRtrivlLocation_Click(ActionEvent e) throws Exception
	{
		//#CM710680
		// Obtain the Case/Piece flag. 
		String flug = viewState.getString(CASE_PIECE_KEY);

		//#CM710681
		// Set the current line. 
		lst_ListRtrivlLocation.setCurrentRow(lst_ListRtrivlLocation.getActiveRow());
		lst_ListRtrivlLocation.getValue(1);

		//#CM710682
		// Set the parameter needed to return to the parent screen. 
		ForwardParameters param = new ForwardParameters();
		if (flug.equals(RetrievalSupportParameter.LISTBOX_RETRIEVAL))
		{
			//#CM710683
			// Picking Location 
			param.setParameter(RETRIEVALLOCATION_KEY, lst_ListRtrivlLocation.getValue(2));
		}
		else if (flug.equals(RetrievalSupportParameter.LISTBOX_CASE))
		{
			//#CM710684
			// Case Picking Location
			param.setParameter(CASERETRIEVALLOCATION_KEY, lst_ListRtrivlLocation.getValue(2));
		}
		else if (flug.equals(RetrievalSupportParameter.LISTBOX_PIECE))
		{
			//#CM710685
			// Piece Picking Location 
			param.setParameter(PIECERETRIEVALLOCATION_KEY, lst_ListRtrivlLocation.getValue(2));
		}
		//#CM710686
		// Shift to the parent screen. 
		parentRedirect(param);
	}

	//#CM710687
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
		//#CM710688
		// Obtain the Case/Piece flag. 
		String flag = viewState.getString(CASE_PIECE_KEY);

		if (flag.equals(RetrievalSupportParameter.LISTBOX_CASE))
		{
			//#CM710689
			// Maintain the list box in the Session 
			SessionRetrievalCaseLocationRet listbox = (SessionRetrievalCaseLocationRet) this.getSession().getAttribute("LISTBOX");
			setCaseList(listbox, "next");
		}
		else if (flag.equals(RetrievalSupportParameter.LISTBOX_PIECE))
		{
			//#CM710690
			// Maintain the list box in the Session 
			SessionRetrievalPieceLocationRet listbox = (SessionRetrievalPieceLocationRet) this.getSession().getAttribute("LISTBOX");
			setPieceList(listbox, "next");
		}
		else if (flag.equals(RetrievalSupportParameter.LISTBOX_RETRIEVAL))
		{
			//#CM710691
			// Maintain the list box in the Session 
			SessionRetrievalPlanLocationRet listbox =
				(SessionRetrievalPlanLocationRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "next");
		}		
	}

	//#CM710692
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
		//#CM710693
		// Obtain the Case/Piece flag. 
		String flag = viewState.getString(CASE_PIECE_KEY);

		if (flag.equals(RetrievalSupportParameter.LISTBOX_CASE))
		{
			//#CM710694
			// Maintain the list box in the Session 
			SessionRetrievalCaseLocationRet listbox = (SessionRetrievalCaseLocationRet) this.getSession().getAttribute("LISTBOX");
			setCaseList(listbox, "previous");
		}
		else if (flag.equals(RetrievalSupportParameter.LISTBOX_PIECE))
		{
			//#CM710695
			// Maintain the list box in the Session 
			SessionRetrievalPieceLocationRet listbox = (SessionRetrievalPieceLocationRet) this.getSession().getAttribute("LISTBOX");
			setPieceList(listbox, "previous");
		}
		else if (flag.equals(RetrievalSupportParameter.LISTBOX_RETRIEVAL))
		{
			//#CM710696
			// Maintain the list box in the Session 
			SessionRetrievalPlanLocationRet listbox =
				(SessionRetrievalPlanLocationRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "previous");
		}
	}

	//#CM710697
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
		//#CM710698
		// Obtain the Case/Piece flag. 
		String flag = viewState.getString(CASE_PIECE_KEY);

		if (flag.equals(RetrievalSupportParameter.LISTBOX_CASE))
		{
			//#CM710699
			// Maintain the list box in the Session 
			SessionRetrievalCaseLocationRet listbox = (SessionRetrievalCaseLocationRet) this.getSession().getAttribute("LISTBOX");
			setCaseList(listbox, "last");
		}
		else if (flag.equals(RetrievalSupportParameter.LISTBOX_PIECE))
		{
			//#CM710700
			// Maintain the list box in the Session 
			SessionRetrievalPieceLocationRet listbox = (SessionRetrievalPieceLocationRet) this.getSession().getAttribute("LISTBOX");
			setPieceList(listbox, "last");
		}
		else if (flag.equals(RetrievalSupportParameter.LISTBOX_RETRIEVAL))
		{
			//#CM710701
			// Maintain the list box in the Session 
			SessionRetrievalPlanLocationRet listbox =
				(SessionRetrievalPlanLocationRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "last");
		}
	}

	//#CM710702
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
		//#CM710703
		// Obtain the Case/Piece flag. 
		String flag = viewState.getString(CASE_PIECE_KEY);

		if (flag.equals(RetrievalSupportParameter.LISTBOX_CASE))
		{
			//#CM710704
			// Maintain the list box in the Session 
			SessionRetrievalCaseLocationRet listbox = (SessionRetrievalCaseLocationRet) this.getSession().getAttribute("LISTBOX");
			setCaseList(listbox, "first");
		}
		else if (flag.equals(RetrievalSupportParameter.LISTBOX_PIECE))
		{
			//#CM710705
			// Maintain the list box in the Session 
			SessionRetrievalPieceLocationRet listbox = (SessionRetrievalPieceLocationRet) this.getSession().getAttribute("LISTBOX");
			setPieceList(listbox, "first");
		}
		else if (flag.equals(RetrievalSupportParameter.LISTBOX_RETRIEVAL))
		{
			//#CM710706
			// Maintain the list box in the Session 
			SessionRetrievalPlanLocationRet listbox =
				(SessionRetrievalPlanLocationRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "first");
		}
	}

	//#CM710707
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710708
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
		//#CM710709
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM710710
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM710711
				// Close the statement. 
				finder.close();
			}
			//#CM710712
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM710713
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM710714
		// Return to the parent screen. 
		parentRedirect(null);
	}
}
//#CM710715
//end of class
