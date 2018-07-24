// $Id: ListRetrievalOrdernoBusiness.java,v 1.2 2007/02/07 04:18:52 suresh Exp $

//#CM710966
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalcustomer.ListRetrievalCustomerBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievaldate.ListRetrievalDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievallocation.ListRetrievalLocationBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalCaseOrdernoRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalOrdernoRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalPieceOrdernoRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalResultOrdernoRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalWorkInfoOrderNoRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM710967
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this listbox class to search for Order No. <BR>
 * Search for the databased on Order No. entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Order No. entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * 2. Button for the selected line (<CODE>lst_ShpAcpDateSrch_Click</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Pass the Order No. on the selected line to the parent screen and close the listbox.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/21</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:52 $
 * @author  $Author: suresh $
 */
public class ListRetrievalOrdernoBusiness extends ListRetrievalOrderno implements WMSConstants
{
	//#CM710968
	// Class fields --------------------------------------------------
	//#CM710969
	// Key for pass 
	//#CM710970
	/** 
	 * Use this key to pass the Order No.
	 */
	public static final String ORDERNO_KEY = "ORDERNO_KEY";

	//#CM710971
	/** 
	 * Use this key to pass the Case Order No.
	 */
	public static final String CASEORDERNO_KEY = "CASEORDERNO_KEY";

	//#CM710972
	/** 
	 * Use this key to pass the Piece Order No.
	 */
	public static final String PIECEORDERNO_KEY = "PIECEORDERNO_KEY";

	//#CM710973
	/** 
	 * Use this key to pass the Batch No.
	 */
	public static final String BATCHNO_KEY = "BATCHNO_KEY";

	//#CM710974
	/** 
	 * Use this key to pass the work status.
	 */
	public static final String WORKSTATUSORDERNO_KEY = "WORKSTATUSORDERNO_KEY";

	//#CM710975
	// Flag for search
	//#CM710976
	/** 
	 * Use this key to pass the Case/Piece flag to search a listbox.
	 */
	public static final String CASE_PIECE_KEY = "CASE_PIECE_KEY";

	//#CM710977
	/** 
	 * Use this key to pass the search flag. 
	 */
	public static final String SEARCH_RETRIEVAL_ORDERNO_KEY = "SEARCH_RETRIEVAL_ORDERNO_KEY";

	//#CM710978
	/** 
	 * Use this key to distinguish the data for Order/Item 
	 */
	public static final String ORDER_ITEM_FLAG = "ORDER_ITEM_FLAG";

	//#CM710979
	/** 
	 * Use this key to pass the Order No. Range flag.
	 */
	public static final String RANGE_KEY = "RANGE_KEY";

	//#CM710980
	/** 
	 * Use this key to pass the Start Order No.
	 */
	public static final String STARTORDERNO_KEY = "STARTORDERNO_KEY";

	//#CM710981
	/** 
	 * Use this key to pass the End Order No.
	 */
	public static final String ENDORDERNO_KEY = "ENDORDERNO_KEY";

	//#CM710982
	/** 
	 * Use this key to pass the Schedule process flag.
	 */
	public static final String SCHEDULEFLAG_KEY = "SCHEDULEFLAG_KEY";
	
	//#CM710983
	/** 
	 * Use this key to pass the area type flag. 
	 */
	public static final String AREA_TYPE_KEY = "AREA_TYPE_KEY";	

	//#CM710984
	// Class variables -----------------------------------------------

	//#CM710985
	// Class method --------------------------------------------------

	//#CM710986
	// Constructors --------------------------------------------------

	//#CM710987
	// Public methods ------------------------------------------------

	//#CM710988
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Field item  <BR>
	 *	<DIR>
	 *		 Select <BR>
	 *		Order No. <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM710989
		// Set the screen name. 
		//#CM710990
		// Search for the Order No. 
		lbl_ListName.setText(DisplayText.getText("TLE-W0003"));

		//#CM710991
		// Obtain the parameter. 
		//#CM710992
		// Consignor Code
		String consignorcode = request.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM710993
		// Planned Picking Date
		String retrievlplandate = request.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM710994
		// Start Planned Picking Date
		String startretrievlplandate = request.getParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY);
		//#CM710995
		// End Planned Picking Date
		String endretrievlplandate = request.getParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY);
		//#CM710996
		// Start Picking Date
		String startretrievldate = request.getParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY);
		//#CM710997
		// End Picking Date
		String endretrievldate = request.getParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY);
		//#CM710998
		// Item Code
		String itemcode = request.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);
		//#CM710999
		// Customer Code
		String customercode = request.getParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY);
		//#CM711000
		// Order No.
		String orderno = request.getParameter(ORDERNO_KEY);
		//#CM711001
		// Case Order No.
		String caseorderno = request.getParameter(CASEORDERNO_KEY);
		//#CM711002
		// Piece Order No.
		String pieceorderno = request.getParameter(PIECEORDERNO_KEY);
		//#CM711003
		// Case/Piece division 
		String casepieceflag = request.getParameter(ListRetrievalLocationBusiness.CASEPIECEFLAG_KEY);
		//#CM711004
		// Picking Location 
		String retrievallocation = request.getParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY);
		//#CM711005
		// Case Picking Location
		String caseretrievallocation = request.getParameter(ListRetrievalLocationBusiness.CASERETRIEVALLOCATION_KEY);
		//#CM711006
		// Piece Picking Location 
		String pieceretrievallocation = request.getParameter(ListRetrievalLocationBusiness.PIECERETRIEVALLOCATION_KEY);
		//#CM711007
		// "Search" flag 
		//#CM711008
		// Flag to determine Case Order No./Piece Order No. 
		String casepiece = request.getParameter(CASE_PIECE_KEY);
		//#CM711009
		// "Search" flag 
		String searchretrievalordernokey = request.getParameter(SEARCH_RETRIEVAL_ORDERNO_KEY);
		//#CM711010
		// Order/Item 
		String orderitem = request.getParameter(ORDER_ITEM_FLAG);
		//#CM711011
		// Work status:
		String[] search = request.getParameterValues(WORKSTATUSORDERNO_KEY);
		//#CM711012
		// Start Order No. 
		String startorderno = request.getParameter(STARTORDERNO_KEY);
		//#CM711013
		// End Order No. 
		String endorderno = request.getParameter(ENDORDERNO_KEY);
		//#CM711014
		// Schedule process flag 
		String scheduleflag = request.getParameter(SCHEDULEFLAG_KEY);
		//#CM711015
		// Range flag 
		String range = request.getParameter(RANGE_KEY);
		//#CM711016
		// Batch No.
		String batchno = request.getParameter(BATCHNO_KEY);
		//#CM711017
		// Area Type flag 
		String areatypeflag = request.getParameter(AREA_TYPE_KEY);
		//#CM711018
		// Allocation flag 
		String allocationflag = request.getParameter(ListRetrievalConsignorBusiness.ALLOCATION_FLAG_KEY);
		
		//#CM711019
		// Maintain the Search flag in viewState. 
		viewState.setString(CASE_PIECE_KEY, casepiece);
		viewState.setString(SEARCH_RETRIEVAL_ORDERNO_KEY, searchretrievalordernokey);
		viewState.setString(RANGE_KEY, range);

		//#CM711020
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM711021
			// Close the connection. 
			sRet.closeConnection();
			//#CM711022
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM711023
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM711024
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM711025
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM711026
		// Planned Picking Date
		param.setRetrievalPlanDate(retrievlplandate);
		//#CM711027
		// Start Planned Picking Date
		param.setFromRetrievalPlanDate(startretrievlplandate);
		//#CM711028
		// End Planned Picking Date
		param.setToRetrievalPlanDate(endretrievlplandate);
		//#CM711029
		// Start Picking Date
		param.setFromRetrievalDate(startretrievldate);
		//#CM711030
		// End Picking Date
		param.setToRetrievalDate(endretrievldate);
		//#CM711031
		// Item Code
		param.setItemCode(itemcode);
		//#CM711032
		// Customer Code
		param.setCustomerCode(customercode);
		//#CM711033
		// Order No.
		param.setOrderNo(orderno);
		//#CM711034
		// Case Order No.
		param.setCaseOrderNo(caseorderno);
		//#CM711035
		// Piece Order No.
		param.setPieceOrderNo(pieceorderno);
		//#CM711036
		// Case/Piece division 
		param.setCasePieceflg(casepieceflag);
		//#CM711037
		// Picking Location 
		param.setRetrievalLocation(retrievallocation);
		//#CM711038
		// Case Picking Location
		param.setCaseLocation(caseretrievallocation);
		//#CM711039
		// Piece Picking Location 
		param.setPieceLocation(pieceretrievallocation);
		//#CM711040
		// Work status:
		param.setSearchStatus(search);
		//#CM711041
		// Order/Item 
		param.setItemOrderFlag(orderitem);
		//#CM711042
		// Start Order No. 
		param.setStartOrderNo(startorderno);
		//#CM711043
		// End Order No. 
		param.setEndOrderNo(endorderno);
		//#CM711044
		// Schedule process flag 
		param.setScheduleFlag(scheduleflag);
		//#CM711045
		// Batch No.
		param.setBatchNo(batchno);
		//#CM711046
		// Area Type key 
		param.setAreaTypeFlag(areatypeflag);
		//#CM711047
		// Allocation flag 
		param.setAllocationFlag(allocationflag);
		
		//#CM711048
		// Determine whether the Order No. to be searched is Case or Piece. 
		//#CM711049
		// "Search" flag: Plan 
		if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM711050
			// Case Order No.+Piece Order No. 
			if(StringUtil.isBlank(casepiece) || RetrievalSupportParameter.LISTBOX_RETRIEVAL.equals(casepiece))
			{
				//#CM711051
				// Generate the SessionRetrievalOrdernoRet instance. 
				SessionRetrievalOrdernoRet listbox = new SessionRetrievalOrdernoRet(conn, param);
				//#CM711052
				// Maintain the list box in the Session 
				this.getSession().setAttribute("LISTBOX", listbox);
				setCasePieceList(listbox, "first");	
			}
			//#CM711053
			// Case Order No.
			else if(RetrievalSupportParameter.LISTBOX_CASE.equals(casepiece))
			{
				//#CM711054
				// Generate the SessionRetrievalCaseOrdernoRet instance. 
				SessionRetrievalCaseOrdernoRet listbox = new SessionRetrievalCaseOrdernoRet(conn, param);
				//#CM711055
				// Maintain the list box in the Session 
				this.getSession().setAttribute("LISTBOX", listbox);
				setCaseList(listbox, "first");	
			}
			//#CM711056
			// Piece Order No.
			else if(RetrievalSupportParameter.LISTBOX_PIECE.equals(casepiece))
			{
				//#CM711057
				// Generate the SessionRetrievalPieceOrdernoRet instance. 
				SessionRetrievalPieceOrdernoRet listbox = new SessionRetrievalPieceOrdernoRet(conn, param);
				//#CM711058
				// Maintain the list box in the Session 
				this.getSession().setAttribute("LISTBOX", listbox);
				setPieceList(listbox, "first");
			}
		}
		//#CM711059
		// "Search" flag: Result 
		else if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM711060
			// Generate the SessionRetrievalResultOrdernoRet instance. 
			SessionRetrievalResultOrdernoRet listbox = new SessionRetrievalResultOrdernoRet(conn, param);
			//#CM711061
			// Maintain the list box in the Session 
			this.getSession().setAttribute("LISTBOX", listbox);
			setResultList(listbox, "first");
		}
		//#CM711062
		// "Search" flag: Work 
		else if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM711063
			// Generate the SessionRetrievalWorkInfoOrderNoRet instance. 
			SessionRetrievalWorkInfoOrderNoRet listbox = new SessionRetrievalWorkInfoOrderNoRet(conn, param);
			//#CM711064
			// Maintain the list box in the Session 
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

	//#CM711065
	// Package methods -----------------------------------------------

	//#CM711066
	// Protected methods ---------------------------------------------

	//#CM711067
	// Private methods -----------------------------------------------
	//#CM711068
	/**
	 * Allow this method to change a page.  <BR>
	 * Search for Order No.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(SessionRetrievalWorkInfoOrderNoRet listbox, String actionName) throws Exception
	{
		//#CM711069
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM711070
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM711071
			// Set a value for Pager. 
			//#CM711072
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM711073
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM711074
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM711075
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM711076
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM711077
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM711078
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM711079
			// Delete all lines. 
			lst_OrderNoSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM711080
				// Obtain the end line. 
				int count = lst_OrderNoSearch.getMaxRows();
				//#CM711081
				// Add a line. 
				lst_OrderNoSearch.addRow();

				//#CM711082
				// Move to the end line. 
				lst_OrderNoSearch.setCurrentRow(count);
				lst_OrderNoSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_OrderNoSearch.setValue(2, rsparam[i].getOrderNo());
			}
		}
		else
		{
			//#CM711083
			// Set a value for Pager. 
			//#CM711084
			// Max count 
			pgr_U.setMax(0);
			//#CM711085
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM711086
			// Start Position 
			pgr_U.setIndex(0);
			//#CM711087
			// Max count 
			pgr_D.setMax(0);
			//#CM711088
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM711089
			// Start Position 
			pgr_D.setIndex(0);

			//#CM711090
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM711091
			// Hide the header. 
			lst_OrderNoSearch.setVisible(false);
			//#CM711092
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM711093
	/**
	 * Allow this method to change a page.  <BR>
	 * Search for Case Order No. + Piece Order No.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setCasePieceList(SessionRetrievalOrdernoRet listbox, String actionName) throws Exception
	{
		//#CM711094
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM711095
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM711096
			// Set a value for Pager. 
			//#CM711097
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM711098
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM711099
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM711100
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM711101
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM711102
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM711103
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM711104
			// Delete all lines. 
			lst_OrderNoSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM711105
				// Obtain the end line. 
				int count = lst_OrderNoSearch.getMaxRows();
				//#CM711106
				// Add a line. 
				lst_OrderNoSearch.addRow();

				//#CM711107
				// Move to the end line. 
				lst_OrderNoSearch.setCurrentRow(count);
				lst_OrderNoSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_OrderNoSearch.setValue(2, rsparam[i].getCaseOrderNo());
			}
		}
		else
		{
			//#CM711108
			// Set a value for Pager. 
			//#CM711109
			// Max count 
			pgr_U.setMax(0);
			//#CM711110
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM711111
			// Start Position 
			pgr_U.setIndex(0);
			//#CM711112
			// Max count 
			pgr_D.setMax(0);
			//#CM711113
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM711114
			// Start Position 
			pgr_D.setIndex(0);

			//#CM711115
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM711116
			// Hide the header. 
			lst_OrderNoSearch.setVisible(false);
			//#CM711117
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM711118
	/**
	 * Allow this method to change a page.  <BR>
	 * Case Search for Order No.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setCaseList(SessionRetrievalCaseOrdernoRet listbox, String actionName) throws Exception
	{
		//#CM711119
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM711120
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM711121
			// Set a value for Pager. 
			//#CM711122
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM711123
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM711124
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM711125
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM711126
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM711127
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM711128
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM711129
			// Delete all lines. 
			lst_OrderNoSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM711130
				// Obtain the end line. 
				int count = lst_OrderNoSearch.getMaxRows();
				//#CM711131
				// Add a line. 
				lst_OrderNoSearch.addRow();

				//#CM711132
				// Move to the end line. 
				lst_OrderNoSearch.setCurrentRow(count);
				lst_OrderNoSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_OrderNoSearch.setValue(2, rsparam[i].getCaseOrderNo());
			}
		}
		else
		{
			//#CM711133
			// Set a value for Pager. 
			//#CM711134
			// Max count 
			pgr_U.setMax(0);
			//#CM711135
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM711136
			// Start Position 
			pgr_U.setIndex(0);
			//#CM711137
			// Max count 
			pgr_D.setMax(0);
			//#CM711138
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM711139
			// Start Position 
			pgr_D.setIndex(0);

			//#CM711140
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM711141
			// Hide the header. 
			lst_OrderNoSearch.setVisible(false);
			//#CM711142
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM711143
	/**
	 * Allow this method to change a page.  <BR>
	 * Search for Piece Order No.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setPieceList(SessionRetrievalPieceOrdernoRet listbox, String actionName) throws Exception
	{
		//#CM711144
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM711145
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM711146
			// Set a value for Pager. 
			//#CM711147
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM711148
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM711149
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM711150
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM711151
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM711152
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM711153
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM711154
			// Delete all lines. 
			lst_OrderNoSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM711155
				// Obtain the end line. 
				int count = lst_OrderNoSearch.getMaxRows();
				//#CM711156
				// Add a line. 
				lst_OrderNoSearch.addRow();

				//#CM711157
				// Move to the end line. 
				lst_OrderNoSearch.setCurrentRow(count);
				lst_OrderNoSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_OrderNoSearch.setValue(2, rsparam[i].getPieceOrderNo());
			}
		}
		else
		{
			//#CM711158
			// Set a value for Pager. 
			//#CM711159
			// Max count 
			pgr_U.setMax(0);
			//#CM711160
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM711161
			// Start Position 
			pgr_U.setIndex(0);
			//#CM711162
			// Max count 
			pgr_D.setMax(0);
			//#CM711163
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM711164
			// Start Position 
			pgr_D.setIndex(0);

			//#CM711165
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM711166
			// Hide the header. 
			lst_OrderNoSearch.setVisible(false);
			//#CM711167
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM711168
	/**
	 * Allow this method to change a page.  <BR>
	 * Case Search for Order No.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setResultList(SessionRetrievalResultOrdernoRet listbox, String actionName) throws Exception
	{
		//#CM711169
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM711170
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM711171
			// Set a value for Pager. 
			//#CM711172
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM711173
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM711174
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM711175
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM711176
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM711177
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM711178
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM711179
			// Delete all lines. 
			lst_OrderNoSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM711180
				// Obtain the end line. 
				int count = lst_OrderNoSearch.getMaxRows();
				//#CM711181
				// Add a line. 
				lst_OrderNoSearch.addRow();

				//#CM711182
				// Move to the end line. 
				lst_OrderNoSearch.setCurrentRow(count);
				lst_OrderNoSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_OrderNoSearch.setValue(2, rsparam[i].getOrderNo());
			}
		}
		else
		{
			//#CM711183
			// Set a value for Pager. 
			//#CM711184
			// Max count 
			pgr_U.setMax(0);
			//#CM711185
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM711186
			// Start Position 
			pgr_U.setIndex(0);
			//#CM711187
			// Max count 
			pgr_D.setMax(0);
			//#CM711188
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM711189
			// Start Position 
			pgr_D.setIndex(0);

			//#CM711190
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM711191
			// Hide the header. 
			lst_OrderNoSearch.setVisible(false);
			//#CM711192
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM711193
	// Event handler methods -----------------------------------------
	//#CM711194
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711195
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711196
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

	//#CM711197
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

	//#CM711198
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

	//#CM711199
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

	//#CM711200
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

	//#CM711201
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711202
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OrderNoSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM711203
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OrderNoSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM711204
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OrderNoSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM711205
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OrderNoSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM711206
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OrderNoSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711207
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OrderNoSearch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM711208
	/** 
	 * Clicking on the Select button of listcell executes its proper process.  <BR>
	 * <BR>
	 *	Pass the Order No. to the parent screen and close the listbox.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lst_OrderNoSearch_Click(ActionEvent e) throws Exception
	{
		//#CM711209
		// Obtain the Case/Piece flag. 
		String casepiece = viewState.getString(CASE_PIECE_KEY);
		//#CM711210
		// Obtain the search flag. 
		String searchretrievalordernokey = viewState.getString(SEARCH_RETRIEVAL_ORDERNO_KEY);
		//#CM711211
		// Obtain the range. 
		String range = viewState.getString(RANGE_KEY);

		//#CM711212
		// Set the current line. 
		lst_OrderNoSearch.setCurrentRow(lst_OrderNoSearch.getActiveRow());
		lst_OrderNoSearch.getValue(1);

		//#CM711213
		// Set the parameter needed to return to the parent screen. 
		ForwardParameters param = new ForwardParameters();
		if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM711214
			// Case Order No. + Piece Order No. 
			if(StringUtil.isBlank(casepiece))
			{
				if (range.equals(RetrievalSupportParameter.RANGE_START))
				{
					//#CM711215
					// Start Order No. 
					param.setParameter(STARTORDERNO_KEY, lst_OrderNoSearch.getValue(2));
				}
				else if (range.equals(RetrievalSupportParameter.RANGE_END))
				{
					//#CM711216
					// End Order No. 
					param.setParameter(ENDORDERNO_KEY, lst_OrderNoSearch.getValue(2));
				}
				else
				{
					//#CM711217
					// Order No.
					param.setParameter(ORDERNO_KEY, lst_OrderNoSearch.getValue(2));
				}
			}
			//#CM711218
			// Case Order No.
			else if(RetrievalSupportParameter.LISTBOX_CASE.equals(casepiece))
			{
				//#CM711219
				// Case Order No.
				param.setParameter(CASEORDERNO_KEY, lst_OrderNoSearch.getValue(2));
			}
			//#CM711220
			// Piece Order No.
			else if(RetrievalSupportParameter.LISTBOX_PIECE.equals(casepiece))
			{
				//#CM711221
				// Piece Order No.
				param.setParameter(PIECEORDERNO_KEY, lst_OrderNoSearch.getValue(2));
			}
			else if(RetrievalSupportParameter.LISTBOX_RETRIEVAL.equals(casepiece))
			{
				//#CM711222
				// Order No.
				param.setParameter(ORDERNO_KEY, lst_OrderNoSearch.getValue(2));
			}
		}
		//#CM711223
		// "Search" flag: Result 
		else if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM711224
			// Order No.
			param.setParameter(ORDERNO_KEY, lst_OrderNoSearch.getValue(2));
		}
		//#CM711225
		// "Search" flag: Work 
		else if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM711226
			// Start Order No. to End Order No. 
			if (!StringUtil.isBlank(range))
			{
				if (range.equals(AsScheduleParameter.RANGE_START))
				{
					//#CM711227
					// Start Order No. 
					param.setParameter(STARTORDERNO_KEY, lst_OrderNoSearch.getValue(2));
				}
				else if (range.equals(AsScheduleParameter.RANGE_END))
				{
					//#CM711228
					// End Order No. 
					param.setParameter(ENDORDERNO_KEY, lst_OrderNoSearch.getValue(2));
				}
			}
			else
			{
				//#CM711229
				// Order No.
				param.setParameter(ORDERNO_KEY, lst_OrderNoSearch.getValue(2));
			}
		}

		//#CM711230
		// Shift to the parent screen. 
		parentRedirect(param);
	}

	//#CM711231
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
		//#CM711232
		// Obtain the Case/Piece flag. 
		String casepiece = viewState.getString(CASE_PIECE_KEY);
		//#CM711233
		// Obtain the search flag. 
		String searchretrievalordernokey = viewState.getString(SEARCH_RETRIEVAL_ORDERNO_KEY);

		if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM711234
			// Case Order No. + Piece Order No. 
			if(StringUtil.isBlank(casepiece) || RetrievalSupportParameter.LISTBOX_RETRIEVAL.equals(casepiece))
			{
				//#CM711235
				// Maintain the list box in the Session 
				SessionRetrievalOrdernoRet listbox = (SessionRetrievalOrdernoRet) this.getSession().getAttribute("LISTBOX");
				setCasePieceList(listbox, "next");
			}
			//#CM711236
			// Case Order No.
			else if(casepiece.equals(RetrievalSupportParameter.LISTBOX_CASE))
			{
				//#CM711237
				// Maintain the list box in the Session 
				SessionRetrievalCaseOrdernoRet listbox = (SessionRetrievalCaseOrdernoRet) this.getSession().getAttribute("LISTBOX");
				setCaseList(listbox, "next");
			}
			//#CM711238
			// Piece Order No.
			else if(casepiece.equals(RetrievalSupportParameter.LISTBOX_PIECE))
			{
				//#CM711239
				// Maintain the list box in the Session 
				SessionRetrievalPieceOrdernoRet listbox = (SessionRetrievalPieceOrdernoRet) this.getSession().getAttribute("LISTBOX");
				setPieceList(listbox, "next");
			}
		}
		//#CM711240
		// "Search" flag: Result 
		else if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM711241
			// Maintain the list box in the Session 
			SessionRetrievalResultOrdernoRet listbox = (SessionRetrievalResultOrdernoRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "next");
		}
		//#CM711242
		// "Search" flag: Work 
		else if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM711243
			// Maintain the list box in the Session 
			SessionRetrievalWorkInfoOrderNoRet listbox = (SessionRetrievalWorkInfoOrderNoRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "next");
		}
	}

	//#CM711244
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
		//#CM711245
		// Obtain the Case/Piece flag. 
		String casepiece = viewState.getString(CASE_PIECE_KEY);
		//#CM711246
		// Obtain the search flag. 
		String searchretrievalordernokey = viewState.getString(SEARCH_RETRIEVAL_ORDERNO_KEY);

		if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM711247
			// Case Order No. + Piece Order No. 
			//#CM711248
			/* 2006/6/7 v2.6.0 START Y.Osawa UPD Conditions for Case/Piece division None was added.  */

			if(StringUtil.isBlank(casepiece) || RetrievalSupportParameter.LISTBOX_RETRIEVAL.equals(casepiece))
			//#CM711249
			/* 2006/6/7 END  */

			{
				//#CM711250
				// Maintain the list box in the Session 
				SessionRetrievalOrdernoRet listbox = (SessionRetrievalOrdernoRet) this.getSession().getAttribute("LISTBOX");
				setCasePieceList(listbox, "previous");
			}
			//#CM711251
			// Case Order No.
			else if(casepiece.equals(RetrievalSupportParameter.LISTBOX_CASE))
			{
				//#CM711252
				// Maintain the list box in the Session 
				SessionRetrievalCaseOrdernoRet listbox = (SessionRetrievalCaseOrdernoRet) this.getSession().getAttribute("LISTBOX");
				setCaseList(listbox, "previous");
			}
			//#CM711253
			// Piece Order No.
			else if(casepiece.equals(RetrievalSupportParameter.LISTBOX_PIECE))
			{
				//#CM711254
				// Maintain the list box in the Session 
				SessionRetrievalPieceOrdernoRet listbox = (SessionRetrievalPieceOrdernoRet) this.getSession().getAttribute("LISTBOX");
				setPieceList(listbox, "previous");
			}
		}
		//#CM711255
		// "Search" flag: Result 
		else if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM711256
			// Maintain the list box in the Session 
			SessionRetrievalResultOrdernoRet listbox = (SessionRetrievalResultOrdernoRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "previous");
		}
		//#CM711257
		// "Search" flag: Work 
		else if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM711258
			// Maintain the list box in the Session 
			SessionRetrievalWorkInfoOrderNoRet listbox = (SessionRetrievalWorkInfoOrderNoRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "previous");
		}
	}

	//#CM711259
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
		//#CM711260
		// Obtain the Case/Piece flag. 
		String casepiece = viewState.getString(CASE_PIECE_KEY);
		//#CM711261
		// Obtain the search flag. 
		String searchretrievalordernokey = viewState.getString(SEARCH_RETRIEVAL_ORDERNO_KEY);

		if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM711262
			// Case Order No. + Piece Order No. 
			if(StringUtil.isBlank(casepiece) || RetrievalSupportParameter.LISTBOX_RETRIEVAL.equals(casepiece))
			{
				//#CM711263
				// Maintain the list box in the Session 
				SessionRetrievalOrdernoRet listbox = (SessionRetrievalOrdernoRet) this.getSession().getAttribute("LISTBOX");
				setCasePieceList(listbox, "last");
			}
			//#CM711264
			// Case Order No.
			else if(casepiece.equals(RetrievalSupportParameter.LISTBOX_CASE))
			{
				//#CM711265
				// Maintain the list box in the Session 
				SessionRetrievalCaseOrdernoRet listbox = (SessionRetrievalCaseOrdernoRet) this.getSession().getAttribute("LISTBOX");
				setCaseList(listbox, "last");
			}
			//#CM711266
			// Piece Order No.
			else if(casepiece.equals(RetrievalSupportParameter.LISTBOX_PIECE))
			{
				//#CM711267
				// Maintain the list box in the Session 
				SessionRetrievalPieceOrdernoRet listbox = (SessionRetrievalPieceOrdernoRet) this.getSession().getAttribute("LISTBOX");
				setPieceList(listbox, "last");
			}
		}
		//#CM711268
		// "Search" flag: Result 
		else if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM711269
			// Maintain the list box in the Session 
			SessionRetrievalResultOrdernoRet listbox = (SessionRetrievalResultOrdernoRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "last");
		}
		//#CM711270
		// "Search" flag: Work 
		else if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM711271
			// Maintain the list box in the Session 
			SessionRetrievalWorkInfoOrderNoRet listbox = (SessionRetrievalWorkInfoOrderNoRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "last");
		}
	}

	//#CM711272
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
		//#CM711273
		// Obtain the Case/Piece flag. 
		String casepiece = viewState.getString(CASE_PIECE_KEY);
		//#CM711274
		// Obtain the search flag. 
		String searchretrievalordernokey = viewState.getString(SEARCH_RETRIEVAL_ORDERNO_KEY);

		if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_PLAN))
		{
			//#CM711275
			// Case Order No. + Piece Order No. 
			if(StringUtil.isBlank(casepiece) || RetrievalSupportParameter.LISTBOX_RETRIEVAL.equals(casepiece))
			{
				//#CM711276
				// Maintain the list box in the Session 
				SessionRetrievalOrdernoRet listbox = (SessionRetrievalOrdernoRet) this.getSession().getAttribute("LISTBOX");
				setCasePieceList(listbox, "first");
			}
			//#CM711277
			// Case Order No.
			else if(casepiece.equals(RetrievalSupportParameter.LISTBOX_CASE))
			{
				//#CM711278
				// Maintain the list box in the Session 
				SessionRetrievalCaseOrdernoRet listbox = (SessionRetrievalCaseOrdernoRet) this.getSession().getAttribute("LISTBOX");
				setCaseList(listbox, "first");
			}
			//#CM711279
			// Piece Order No.
			else if(casepiece.equals(RetrievalSupportParameter.LISTBOX_PIECE))
			{
				//#CM711280
				// Maintain the list box in the Session 
				SessionRetrievalPieceOrdernoRet listbox = (SessionRetrievalPieceOrdernoRet) this.getSession().getAttribute("LISTBOX");
				setPieceList(listbox, "first");
			}
		}
		//#CM711281
		// "Search" flag: Result 
		else if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_RESULT))
		{
			//#CM711282
			// Maintain the list box in the Session 
			SessionRetrievalResultOrdernoRet listbox = (SessionRetrievalResultOrdernoRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "first");
		}
		//#CM711283
		// "Search" flag: Work 
		else if (searchretrievalordernokey.equals(RetrievalSupportParameter.SEARCHFLAG_WORKINFO))
		{
			//#CM711284
			// Maintain the list box in the Session 
			SessionRetrievalWorkInfoOrderNoRet listbox = (SessionRetrievalWorkInfoOrderNoRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "first");
		}
	}

	//#CM711285
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711286
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
		//#CM711287
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM711288
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM711289
				// Close the statement. 
				finder.close();
			}
			//#CM711290
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM711291
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM711292
		// Return to the parent screen. 
		parentRedirect(null);
	}
}
//#CM711293
//end of class
