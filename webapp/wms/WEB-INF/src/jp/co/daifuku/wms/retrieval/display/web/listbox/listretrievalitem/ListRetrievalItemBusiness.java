// $Id: ListRetrievalItemBusiness.java,v 1.2 2007/02/07 04:18:45 suresh Exp $

//#CM709852
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listbatchno.ListBatchNoBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievaldate.ListRetrievalDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalPlanItemRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalResultItemRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalWorkInfoItemRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM709853
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this listbox class to search for This list box class searches item. <BR>
 * Search for the databased on Item Code, entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Item Code, entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * 2. Button for the selected line (<CODE>lst_ItemSearch_Click</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	 Pass the Item Code, and Item Name on the selected line to the parent screen and close the listbox. <BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/21</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:45 $
 * @author  $Author: suresh $
 */
public class ListRetrievalItemBusiness extends ListRetrievalItem implements WMSConstants
{
	//#CM709854
	// Class fields --------------------------------------------------
	//#CM709855
	/** 
	 * Use this key to pass the item code. 
	 */
	public static final String ITEMCODE_KEY = "ITEMCODE_KEY";

	//#CM709856
	/** 
	 * Use this key to pass the item name. 
	 */
	public static final String ITEMNAME_KEY = "ITEMNAME_KEY";

	//#CM709857
	/** 
	 * Use this key to pass the search flag. 
	 */
	public static final String SEARCHITEM_KEY = "SEARCHITEM_KEY";

	//#CM709858
	/** 
	 * Use this key to pass the work status.
	 */
	public static final String WORKSTATUSITEM_KEY = "WORKSTATUSITEM_KEY";
	
	//#CM709859
	/** 
	 * Use this key to distinguish the data for Order/Item 
	 */
	public static final String ORDER_ITEM_FLAG = "ORDER_ITEM_FLAG";

	//#CM709860
	/** 
	 * Use this key to pass the Start item code. 
	 */
	public static final String STARTITEMCODE_KEY = "STARTITEMCODE_KEY";

	//#CM709861
	/** 
	 * Use this key to pass the End item code. 
	 */
	public static final String ENDITEMCODE_KEY = "ENDITEMCODE_KEY";

	//#CM709862
	/** 
	 * Use this key to pass the range.
	 */
	public static final String RANGE_KEY = "RANGE_KEY";
	
	//#CM709863
	/** 
	 * Use this key to pass the Schedule division.
	 */
	public static final String SCHEDULE_FLAG_KEY = "SCHEDULE_FLAG_KEY";
	//#CM709864
	/** 
	 * Use this key to pass the area type flag. 
	 */
	public static final String AREA_TYPE_KEY = "AREA_TYPE_KEY";	
	//#CM709865
	// Class variables -----------------------------------------------

	//#CM709866
	// Class method --------------------------------------------------

	//#CM709867
	// Constructors --------------------------------------------------

	//#CM709868
	// Public methods ------------------------------------------------

	//#CM709869
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Field item  <BR>
	 *	<DIR>
	 *		 Select <BR>
	 *		Item Code <BR>
	 *		Item Name <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM709870
		// Set the screen name. 
		//#CM709871
		// Search Item 
		lbl_ListName.setText(DisplayText.getText("TLE-W0043"));

		//#CM709872
		// Obtain the parameter. 
		//#CM709873
		// Consignor Code
		String consignorcode =
			request.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM709874
		// Planned Picking Date
		String retrievalplandate =
			request.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM709875
		// Start Planned Picking Date
		String startretrievalplandate =
			request.getParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY);
		//#CM709876
		// End Planned Picking Date
		String endretrievalplandate =
			request.getParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY);
		//#CM709877
		// Start Picking Date
		String startretrievaldate =
			request.getParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY);
		//#CM709878
		// End Picking Date
		String endretrievaldate =
			request.getParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY);
		//#CM709879
		// Item Code
		String itemcode = request.getParameter(ITEMCODE_KEY);
		//#CM709880
		// Start Item Code 
		String startitemcode = request.getParameter(STARTITEMCODE_KEY);
		//#CM709881
		// End Item Code 
		String enditemcode = request.getParameter(ENDITEMCODE_KEY);
		//#CM709882
		// Search flag (Plan or Work  or Result) 
		String searchitem = request.getParameter(SEARCHITEM_KEY);
		//#CM709883
		// Order/Item 
		String orderitem = request.getParameter(ORDER_ITEM_FLAG);
		//#CM709884
		// Work status:
		String[] search = request.getParameterValues(WORKSTATUSITEM_KEY);
		//#CM709885
		// Range 
		String range = request.getParameter(RANGE_KEY);
		//#CM709886
		// Schedule flag 
		String[] schFlag = request.getParameterValues(SCHEDULE_FLAG_KEY);
		//#CM709887
		// Batch No.
		String batchno = request.getParameter(ListBatchNoBusiness.BATCHNO_KEY);
		//#CM709888
		// Area Type flag 
		String areatypeflag = request.getParameter(AREA_TYPE_KEY);
		//#CM709889
		// Allocation flag 
		String allocationflag = request.getParameter(ListRetrievalConsignorBusiness.ALLOCATION_FLAG_KEY);

		viewState.setString(SEARCHITEM_KEY, searchitem);
		viewState.setString(RANGE_KEY, range);

		//#CM709890
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM709891
			// Close the connection. 
			sRet.closeConnection();
			//#CM709892
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM709893
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM709894
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM709895
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM709896
		// Planned Picking Date
		param.setRetrievalPlanDate(retrievalplandate);
		//#CM709897
		// Start Planned Picking Date
		param.setFromRetrievalPlanDate(startretrievalplandate);
		//#CM709898
		// End Planned Picking Date
		param.setToRetrievalPlanDate(endretrievalplandate);
		//#CM709899
		// Start Picking Date
		param.setFromRetrievalDate(startretrievaldate);
		//#CM709900
		// End Picking Date
		param.setToRetrievalDate(endretrievaldate);
		//#CM709901
		// Item Code
		param.setItemCode(itemcode);
		//#CM709902
		// Start Item Code 
		param.setStartItemCode(startitemcode);
		//#CM709903
		// End Item Code 
		param.setEndItemCode(enditemcode);
		//#CM709904
		// Array of work status 
		param.setSearchStatus(search);
		//#CM709905
		// Order/Item 
		param.setItemOrderFlag(orderitem);
		//#CM709906
		// Schedule flag 
		param.setScheduleFlagArray(schFlag);
		//#CM709907
		// Batch No.
		param.setBatchNo(batchno);
		//#CM709908
		// Area Type flag 
		param.setAreaTypeFlag(areatypeflag);
		//#CM709909
		// Allocation flag 
		param.setAllocationFlag(allocationflag);
		
		//#CM709910
		// Determine whether the Item to be searched is Plan or Result 
		if (searchitem.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM709911
			// Generate the SessionRetrievalPlanItemRet instance. 
			SessionRetrievalPlanItemRet listbox = new SessionRetrievalPlanItemRet(conn, param);
			//#CM709912
			// Maintain the list box in the Session 
			this.getSession().setAttribute("LISTBOX", listbox);
			setPlanList(listbox, "first");
		}
		else if (searchitem.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM709913
			// Generate the SessionRetrievalResultItemRet instance. 
			SessionRetrievalResultItemRet listbox = new SessionRetrievalResultItemRet(conn, param);
			//#CM709914
			// Maintain the list box in the Session 
			this.getSession().setAttribute("LISTBOX", listbox);
			setResultList(listbox, "first");
		}
		else if (searchitem.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM709915
			// Generate the SessionRetrievalWorkInfoItemRet instance. 
			SessionRetrievalWorkInfoItemRet listbox = new SessionRetrievalWorkInfoItemRet(conn, param);
			//#CM709916
			// Maintain the list box in the Session 
			this.getSession().setAttribute("LISTBOX", listbox);
			setWorkInfoList(listbox, "first");
		}
		else
		{
			Object[] tObj = new Object[1];
			tObj[0] = DisplayText.getText("LBL-W0099");
			String classname = this.getClass().getName();
			RmiMsgLogClient.write(6007039, LogMessage.F_ERROR, classname, tObj);
			throw (new InvalidStatusException("6007039" + wDelim + tObj[0]));
		}
	}

	//#CM709917
	// Package methods -----------------------------------------------

	//#CM709918
	// Protected methods ---------------------------------------------

	//#CM709919
	// Private methods -----------------------------------------------
	//#CM709920
	/**
	 * Allow this method to change a page.  <BR>
	 * Search through the Plan table.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setPlanList(SessionRetrievalPlanItemRet listbox, String actionName)
		throws Exception
	{
		//#CM709921
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM709922
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM709923
			// Set a value for Pager. 
			//#CM709924
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM709925
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM709926
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM709927
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM709928
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM709929
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM709930
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM709931
			// Delete all lines. 
			lst_ItemSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM709932
				// Obtain the end line. 
				int count = lst_ItemSearch.getMaxRows();
				//#CM709933
				// Add a line. 
				lst_ItemSearch.addRow();

				//#CM709934
				// Move to the end line. 
				lst_ItemSearch.setCurrentRow(count);
				lst_ItemSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemSearch.setValue(2, rsparam[i].getItemCode());
				lst_ItemSearch.setValue(3, rsparam[i].getItemName());
			}
		}
		else
		{
			//#CM709935
			// Set a value for Pager. 
			//#CM709936
			// Max count 
			pgr_U.setMax(0);
			//#CM709937
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM709938
			// Start Position 
			pgr_U.setIndex(0);
			//#CM709939
			// Max count 
			pgr_D.setMax(0);
			//#CM709940
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM709941
			// Start Position 
			pgr_D.setIndex(0);

			//#CM709942
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM709943
			// Hide the header. 
			lst_ItemSearch.setVisible(false);
			//#CM709944
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM709945
	/**
	 * Allow this method to change a page.  <BR>
	 * Search through the result table.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setResultList(SessionRetrievalResultItemRet listbox, String actionName)
		throws Exception
	{
		//#CM709946
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM709947
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM709948
			// Set a value for Pager. 
			//#CM709949
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM709950
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM709951
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM709952
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM709953
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM709954
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM709955
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM709956
			// Delete all lines. 
			lst_ItemSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM709957
				// Obtain the end line. 
				int count = lst_ItemSearch.getMaxRows();
				//#CM709958
				// Add a line. 
				lst_ItemSearch.addRow();

				//#CM709959
				// Move to the end line. 
				lst_ItemSearch.setCurrentRow(count);
				lst_ItemSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemSearch.setValue(2, rsparam[i].getItemCode());
				lst_ItemSearch.setValue(3, rsparam[i].getItemName());
			}
		}
		else
		{
			//#CM709960
			// Set a value for Pager. 
			//#CM709961
			// Max count 
			pgr_U.setMax(0);
			//#CM709962
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM709963
			// Start Position 
			pgr_U.setIndex(0);
			//#CM709964
			// Max count 
			pgr_D.setMax(0);
			//#CM709965
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM709966
			// Start Position 
			pgr_D.setIndex(0);

			//#CM709967
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM709968
			// Hide the header. 
			lst_ItemSearch.setVisible(false);
			//#CM709969
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM709970
	/**
	 * Allow this method to change a page.  <BR>
	 * Search through the Work table.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setWorkInfoList(SessionRetrievalWorkInfoItemRet listbox, String actionName)
		throws Exception
	{
		//#CM709971
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM709972
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM709973
			// Set a value for Pager. 
			//#CM709974
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM709975
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM709976
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM709977
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM709978
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM709979
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM709980
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM709981
			// Delete all lines. 
			lst_ItemSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM709982
				// Obtain the end line. 
				int count = lst_ItemSearch.getMaxRows();
				//#CM709983
				// Add a line. 
				lst_ItemSearch.addRow();

				//#CM709984
				// Move to the end line. 
				lst_ItemSearch.setCurrentRow(count);
				lst_ItemSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemSearch.setValue(2, rsparam[i].getItemCode());
				lst_ItemSearch.setValue(3, rsparam[i].getItemName());
			}
		}
		else
		{
			//#CM709985
			// Set a value for Pager. 
			//#CM709986
			// Max count 
			pgr_U.setMax(0);
			//#CM709987
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM709988
			// Start Position 
			pgr_U.setIndex(0);
			//#CM709989
			// Max count 
			pgr_D.setMax(0);
			//#CM709990
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM709991
			// Start Position 
			pgr_D.setIndex(0);

			//#CM709992
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM709993
			// Hide the header. 
			lst_ItemSearch.setVisible(false);
			//#CM709994
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}
	//#CM709995
	// Event handler methods -----------------------------------------
	//#CM709996
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709997
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM709998
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

	//#CM709999
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

	//#CM710000
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

	//#CM710001
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

	//#CM710002
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

	//#CM710003
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710004
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM710005
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM710006
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM710007
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM710008
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710009
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM710010
	/** 
	 * Clicking on the Select button of listcell executes its proper process.  <BR>
	 * <BR>
	 *	 Pass the Item Code, and Item Name to the parent screen and close the listbox.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lst_ItemSearch_Click(ActionEvent e) throws Exception
	{
		//#CM710011
		// Set the current line. 
		lst_ItemSearch.setCurrentRow(lst_ItemSearch.getActiveRow());
		lst_ItemSearch.getValue(1);

		//#CM710012
		// Set the parameter needed to return to the parent screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM710013
		// Range 
		String range = viewState.getString(RANGE_KEY);

		if (!StringUtil.isBlank(range))
		{
			if (range.equals(RetrievalSupportParameter.RANGE_START))
			{
				//#CM710014
				// Start Item Code 
				param.setParameter(STARTITEMCODE_KEY, lst_ItemSearch.getValue(2));
			}
			else if (range.equals(RetrievalSupportParameter.RANGE_END))
			{
				//#CM710015
				// End Item Code 
				param.setParameter(ENDITEMCODE_KEY, lst_ItemSearch.getValue(2));
			}
		}
		else
		{
			//#CM710016
			// Item Code
			param.setParameter(ITEMCODE_KEY, lst_ItemSearch.getValue(2));
			//#CM710017
			// Item Name
			param.setParameter(ITEMNAME_KEY, lst_ItemSearch.getValue(3));
		}
		

		//#CM710018
		// Shift to the parent screen. 
		parentRedirect(param);
	}

	//#CM710019
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
		//#CM710020
		// Obtain the search flag. 
		String flag = viewState.getString(SEARCHITEM_KEY);

		if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM710021
			// Maintain the list box in the Session 
			SessionRetrievalPlanItemRet listbox =
				(SessionRetrievalPlanItemRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "next");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM710022
			// Maintain the list box in the Session 
			SessionRetrievalResultItemRet listbox =
				(SessionRetrievalResultItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "next");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM710023
			// Maintain the list box in the Session 
			SessionRetrievalWorkInfoItemRet listbox =
				(SessionRetrievalWorkInfoItemRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "next");
		}
	}

	//#CM710024
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
		//#CM710025
		// Obtain the search flag. 
		String flag = viewState.getString(SEARCHITEM_KEY);

		if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM710026
			// Maintain the list box in the Session 
			SessionRetrievalPlanItemRet listbox =
				(SessionRetrievalPlanItemRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "previous");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM710027
			// Maintain the list box in the Session 
			SessionRetrievalResultItemRet listbox =
				(SessionRetrievalResultItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "previous");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM710028
			// Maintain the list box in the Session 
			SessionRetrievalWorkInfoItemRet listbox =
				(SessionRetrievalWorkInfoItemRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "previous");
		}
	}

	//#CM710029
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
		//#CM710030
		// Obtain the search flag. 
		String flag = viewState.getString(SEARCHITEM_KEY);

		if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM710031
			// Maintain the list box in the Session 
			SessionRetrievalPlanItemRet listbox =
				(SessionRetrievalPlanItemRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "last");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM710032
			// Maintain the list box in the Session 
			SessionRetrievalResultItemRet listbox =
				(SessionRetrievalResultItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "last");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM710033
			// Maintain the list box in the Session 
			SessionRetrievalWorkInfoItemRet listbox =
				(SessionRetrievalWorkInfoItemRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "last");
		}
	}

	//#CM710034
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
		//#CM710035
		// Obtain the search flag. 
		String flag = viewState.getString(SEARCHITEM_KEY);

		if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM710036
			// Maintain the list box in the Session 
			SessionRetrievalPlanItemRet listbox =
				(SessionRetrievalPlanItemRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "first");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM710037
			// Maintain the list box in the Session 
			SessionRetrievalResultItemRet listbox =
				(SessionRetrievalResultItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "first");
		}
		else if (flag.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM710038
			// Maintain the list box in the Session 
			SessionRetrievalWorkInfoItemRet listbox =
				(SessionRetrievalWorkInfoItemRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "first");
		}
	}

	//#CM710039
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710040
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
		//#CM710041
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM710042
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM710043
				// Close the statement. 
				finder.close();
			}
			//#CM710044
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM710045
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM710046
		// Return to the parent screen. 
		parentRedirect(null);
	}
}
//#CM710047
//end of class
