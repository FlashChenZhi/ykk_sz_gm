// $Id: ListRetrievalPlanDateBusiness.java,v 1.2 2007/02/07 04:18:54 suresh Exp $

//#CM711572
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate;
import java.sql.Connection;
import java.util.Locale;

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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalPlanDateRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalWorkInfoDateRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM711573
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this listbox class to search for Planned Picking Date. <BR>
 * Search for the databased on Planned Picking Date entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Planned Picking Date entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * 2. Button for the selected line (<CODE>lst_ShpPlanDateSrch_Click</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Pass the Planned Picking Date on the selected line to the parent screen and close the listbox.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/21</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:54 $
 * @author  $Author: suresh $
 */
public class ListRetrievalPlanDateBusiness extends ListRetrievalPlanDate implements WMSConstants
{
	//#CM711574
	// Class fields --------------------------------------------------
	//#CM711575
	/** 
	 * Use this key to pass the Planned Picking Date.
	 */
	public static final String RETRIEVALPLANDATE_KEY = "RETRIEVALPLANDATE_KEY";

	//#CM711576
	/** 
	 * Use this key to pass the Start Planned Picking Date.
	 */
	public static final String STARTRETRIEVALPLANDATE_KEY = "STARTRETRIEVALPLANDATE_KEY";

	//#CM711577
	/** 
	 * Use this key to pass the End Planned Picking Date.
	 */
	public static final String ENDRETRIEVALPLANDATE_KEY = "ENDRETRIEVALPLANDATE_KEY";

	//#CM711578
	/** 
	 * Use this key to pass the Planned Picking Date Range flag.
	 */
	public static final String RANGERETRIEVALPLANDATE_KEY = "RANGERETRIEVALPLANDATE_KEY";

	//#CM711579
	/** 
	 * Use this key to pass the work status.
	 */
	public static final String WORKSTATUSRETRIEVALPLANDATE_KEY = "WORKSTATUSRETRIEVALPLANDATE_KEY";

	//#CM711580
	/** 
	 * Use this key to pass the search flag. 
	 */
	public static final String SEARCHRETRIEVALPLANDATE_KEY = "SEARCHRETRIEVALPLANDATE_KEY";

	//#CM711581
	/** 
	 * Use this key to distinguish the data for Order/Item 
	 */
	public static final String ORDER_ITEM_FLAG = "ORDER_ITEM_FLAG";

	//#CM711582
	/** 
	 * Use this key to pass the Schedule process flag.
	 */
	public static final String SCHEDULE_FLAG_KEY = "SCHEDULE_FLAG_KEY";
	
	//#CM711583
	/** 
	 * Use this key to pass the area type flag. 
	 */
	public static final String AREA_TYPE_KEY = "AREA_TYPE_KEY";	
	//#CM711584
	// Class variables -----------------------------------------------

	//#CM711585
	// Class method --------------------------------------------------

	//#CM711586
	// Constructors --------------------------------------------------

	//#CM711587
	// Public methods ------------------------------------------------

	//#CM711588
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Field item  <BR>
	 *	<DIR>
	 *		 Select <BR>
	 *		Planned Picking Date <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM711589
		// Set the screen name. 
		//#CM711590
		// Search for the Planned Picking Date.
		lbl_ListName.setText(DisplayText.getText("TLE-W0071"));

		//#CM711591
		// Obtain the parameter. 
		//#CM711592
		// Consignor Code
		String consignorcode = request.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM711593
		// Planned Picking Date
		String retrievalplandate = request.getParameter(RETRIEVALPLANDATE_KEY);
		//#CM711594
		// Start Planned Picking Date
		String startretrievalplandate = request.getParameter(STARTRETRIEVALPLANDATE_KEY);
		//#CM711595
		// End Planned Picking Date
		String endretrievalplandate = request.getParameter(ENDRETRIEVALPLANDATE_KEY);
		//#CM711596
		// Planned Picking Date Range flag 
		String rangeretrievalplandate = request.getParameter(RANGERETRIEVALPLANDATE_KEY);
		//#CM711597
		// Work status:
		String[] search = request.getParameterValues(WORKSTATUSRETRIEVALPLANDATE_KEY);
		//#CM711598
		// Search table 
		String searchconsignor = request.getParameter(SEARCHRETRIEVALPLANDATE_KEY);
		//#CM711599
		// Order/Item 
		String orderitem = request.getParameter(ORDER_ITEM_FLAG);
		//#CM711600
		// Schedule process flag 
		String[] schFlag= request.getParameterValues(SCHEDULE_FLAG_KEY);
		//#CM711601
		// Area Type flag 
		String areatypeflag = request.getParameter(AREA_TYPE_KEY);
		//#CM711602
		// Allocation flag 
		String allocationflag = request.getParameter(ListRetrievalConsignorBusiness.ALLOCATION_FLAG_KEY);

		viewState.setString(SEARCHRETRIEVALPLANDATE_KEY, searchconsignor);
		viewState.setString(RANGERETRIEVALPLANDATE_KEY, rangeretrievalplandate);

		//#CM711603
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM711604
			// Close the connection. 
			sRet.closeConnection();
			//#CM711605
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM711606
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM711607
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM711608
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM711609
		// Planned Picking Date
		param.setRetrievalPlanDate(retrievalplandate);
		//#CM711610
		// Start Planned Picking Date
		param.setFromRetrievalPlanDate(startretrievalplandate);
		//#CM711611
		// End Planned Picking Date
		param.setToRetrievalPlanDate(endretrievalplandate);
		//#CM711612
		// Array of work status 
		param.setSearchStatus(search);
		//#CM711613
		// Order/Item 
		param.setItemOrderFlag(orderitem);
		//#CM711614
		// Schedule process flag 
		param.setScheduleFlagArray(schFlag);
		//#CM711615
		// Area Type flag 
		param.setAreaTypeFlag(areatypeflag);
		//#CM711616
		// Allocation flag 
		param.setAllocationFlag(allocationflag);
		
		//#CM711617
		// Determine whether the Consignor to be searched is Work or Plan or Result. 
		if (searchconsignor.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM711618
			// Generate the SessionRetrievalPlanDateRet instance. 
			SessionRetrievalPlanDateRet listbox = new SessionRetrievalPlanDateRet(conn, param);
			//#CM711619
			// Maintain the list box in the Session 
			this.getSession().setAttribute("LISTBOX", listbox);
			setPlanList(listbox, "first");
		}
		else if (searchconsignor.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM711620
			// Generate the SessionRetrievalWorkInfoConsignorRet instance. 
			SessionRetrievalWorkInfoDateRet listbox = new SessionRetrievalWorkInfoDateRet(conn, param);
			//#CM711621
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

	//#CM711622
	// Package methods -----------------------------------------------

	//#CM711623
	// Protected methods ---------------------------------------------

	//#CM711624
	// Private methods -----------------------------------------------
	//#CM711625
	/**
	 * Allow this method to change a page.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setPlanList(SessionRetrievalPlanDateRet listbox, String actionName) throws Exception
	{
		//#CM711626
		// Obtain locale.
		Locale locale = this.getHttpRequest().getLocale();

		//#CM711627
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM711628
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM711629
			// Set a value for Pager. 
			//#CM711630
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM711631
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM711632
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM711633
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM711634
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM711635
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM711636
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM711637
			// Delete all lines. 
			lst_RtrivlPlanDateSrch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM711638
				// Obtain the end line. 
				int count = lst_RtrivlPlanDateSrch.getMaxRows();
				//#CM711639
				// Add a line. 
				lst_RtrivlPlanDateSrch.addRow();

				//#CM711640
				// Move to the end line. 
				lst_RtrivlPlanDateSrch.setCurrentRow(count);
				lst_RtrivlPlanDateSrch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_RtrivlPlanDateSrch.setValue(2, WmsFormatter.toDispDate(rsparam[i].getRetrievalPlanDate(), locale));
			}
		}
		else
		{
			//#CM711641
			// Set a value for Pager. 
			//#CM711642
			// Max count 
			pgr_U.setMax(0);
			//#CM711643
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM711644
			// Start Position 
			pgr_U.setIndex(0);
			//#CM711645
			// Max count 
			pgr_D.setMax(0);
			//#CM711646
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM711647
			// Start Position 
			pgr_D.setIndex(0);

			//#CM711648
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM711649
			// Hide the header. 
			lst_RtrivlPlanDateSrch.setVisible(false);
			//#CM711650
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM711651
	/**
	 * Allow this method to change a page.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setWorkInfoList(SessionRetrievalWorkInfoDateRet listbox, String actionName) throws Exception
	{
		//#CM711652
		// Obtain locale.
		Locale locale = this.getHttpRequest().getLocale();

		//#CM711653
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM711654
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM711655
			// Set a value for Pager. 
			//#CM711656
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM711657
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM711658
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM711659
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM711660
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM711661
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM711662
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM711663
			// Delete all lines. 
			lst_RtrivlPlanDateSrch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM711664
				// Obtain the end line. 
				int count = lst_RtrivlPlanDateSrch.getMaxRows();
				//#CM711665
				// Add a line. 
				lst_RtrivlPlanDateSrch.addRow();

				//#CM711666
				// Move to the end line. 
				lst_RtrivlPlanDateSrch.setCurrentRow(count);
				lst_RtrivlPlanDateSrch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_RtrivlPlanDateSrch.setValue(2, WmsFormatter.toDispDate(rsparam[i].getRetrievalPlanDate(), locale));
			}
		}
		else
		{
			//#CM711667
			// Set a value for Pager. 
			//#CM711668
			// Max count 
			pgr_U.setMax(0);
			//#CM711669
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM711670
			// Start Position 
			pgr_U.setIndex(0);
			//#CM711671
			// Max count 
			pgr_D.setMax(0);
			//#CM711672
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM711673
			// Start Position 
			pgr_D.setIndex(0);

			//#CM711674
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM711675
			// Hide the header. 
			lst_RtrivlPlanDateSrch.setVisible(false);
			//#CM711676
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM711677
	// Event handler methods -----------------------------------------
	//#CM711678
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711679
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711680
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

	//#CM711681
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

	//#CM711682
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

	//#CM711683
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

	//#CM711684
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

	//#CM711685
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711686
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlPlanDateSrch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM711687
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlPlanDateSrch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM711688
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlPlanDateSrch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM711689
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlPlanDateSrch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM711690
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlPlanDateSrch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711691
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RtrivlPlanDateSrch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM711692
	/** 
	 * Clicking on the Select button of listcell executes its proper process.  <BR>
	 * <BR>
	 *	Pass the Planned Picking Date to the parent screen and close the listbox. <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lst_RtrivlPlanDateSrch_Click(ActionEvent e) throws Exception
	{
		//#CM711693
		// Obtain the Planned Picking Date Range flag. 
		String flug = viewState.getString(RANGERETRIEVALPLANDATE_KEY);

		//#CM711694
		// Set the current line. 
		lst_RtrivlPlanDateSrch.setCurrentRow(lst_RtrivlPlanDateSrch.getActiveRow());
		lst_RtrivlPlanDateSrch.getValue(1);

		//#CM711695
		// Set the parameter needed to return to the parent screen. 
		ForwardParameters param = new ForwardParameters();
		if (flug == null)
		{
			//#CM711696
			// Planned Picking Date
			param.setParameter(RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(lst_RtrivlPlanDateSrch.getValue(2), this.getHttpRequest().getLocale()));
		}
		else if (flug.equals(RetrievalSupportParameter.RANGE_START))
		{
			//#CM711697
			// Start Planned Picking Date
			param.setParameter(STARTRETRIEVALPLANDATE_KEY, lst_RtrivlPlanDateSrch.getValue(2));
		}
		else if (flug.equals(RetrievalSupportParameter.RANGE_END))
		{
			//#CM711698
			// End Planned Picking Date
			param.setParameter(ENDRETRIEVALPLANDATE_KEY, lst_RtrivlPlanDateSrch.getValue(2));
		}
		//#CM711699
		// Shift to the parent screen. 
		parentRedirect(param);
	}

	//#CM711700
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
		//#CM711701
		// Obtain the search flag. 
		String flag = viewState.getString(SEARCHRETRIEVALPLANDATE_KEY);

		if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM711702
			// Maintain the list box in the Session 
			SessionRetrievalPlanDateRet listbox = (SessionRetrievalPlanDateRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "next");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM711703
			// Maintain the list box in the Session 
			SessionRetrievalWorkInfoDateRet listbox = (SessionRetrievalWorkInfoDateRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "next");
		}
	}

	//#CM711704
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

		//#CM711705
		// Obtain the search flag. 
		String flag = viewState.getString(SEARCHRETRIEVALPLANDATE_KEY);

		if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM711706
			// Maintain the list box in the Session 
			SessionRetrievalPlanDateRet listbox = (SessionRetrievalPlanDateRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "previous");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM711707
			// Maintain the list box in the Session 
			SessionRetrievalWorkInfoDateRet listbox = (SessionRetrievalWorkInfoDateRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "previous");
		}
	}

	//#CM711708
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
		//#CM711709
		// Obtain the search flag. 
		String flag = viewState.getString(SEARCHRETRIEVALPLANDATE_KEY);

		if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM711710
			// Maintain the list box in the Session 
			SessionRetrievalPlanDateRet listbox = (SessionRetrievalPlanDateRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "last");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM711711
			// Maintain the list box in the Session 
			SessionRetrievalWorkInfoDateRet listbox = (SessionRetrievalWorkInfoDateRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "last");
		}
	}

	//#CM711712
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
		//#CM711713
		// Obtain the search flag. 
		String flag = viewState.getString(SEARCHRETRIEVALPLANDATE_KEY);

		if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM711714
			// Maintain the list box in the Session 
			SessionRetrievalPlanDateRet listbox = (SessionRetrievalPlanDateRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "first");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM711715
			// Maintain the list box in the Session 
			SessionRetrievalWorkInfoDateRet listbox = (SessionRetrievalWorkInfoDateRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "first");
		}
	}

	//#CM711716
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711717
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
		//#CM711718
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM711719
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM711720
				// Close the statement. 
				finder.close();
			}
			//#CM711721
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM711722
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM711723
		// Return to the parent screen. 
		parentRedirect(null);
	}
}
//#CM711724
//end of class
