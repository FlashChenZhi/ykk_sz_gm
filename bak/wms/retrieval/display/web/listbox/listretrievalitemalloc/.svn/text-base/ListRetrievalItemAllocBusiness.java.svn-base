// $Id: ListRetrievalItemAllocBusiness.java,v 1.2 2007/02/07 04:18:46 suresh Exp $

//#CM710052
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitemalloc;
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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderlist.ListRetrievalOrderListBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalItemAllocListRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM710053
/**
 * Designer : Y.Osawa <BR>
 * Maker : Y.Osawa <BR>
 * <BR>
 * Allow this listbox class to search for Item Picking Allocation Work Report list. <BR>
 * Search for the databased on Consignor Code, Planned Picking Date, Item Code, and Case/Piece division entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 *  Search for the data using Consignor Code, Planned Picking Date, Item Code, and Case/Piece division entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/22</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:46 $
 * @author  $Author: suresh $
 */public class ListRetrievalItemAllocBusiness extends ListRetrievalItemAlloc implements WMSConstants
{
	//#CM710054
	// Class fields --------------------------------------------------

	//#CM710055
	// Class variables -----------------------------------------------

	//#CM710056
	// Class method --------------------------------------------------

	//#CM710057
	// Constructors --------------------------------------------------

	//#CM710058
	// Public methods ------------------------------------------------

	//#CM710059
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Field item  <BR>
	 *	<DIR>
	 *		1.Item Code <BR>
	 *		2. Case/Piece division  <BR>
	 *		3. Picking Location  <BR>
	 *		4.Item Name <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM710060
		// Set the screen name. 
		//#CM710061
		// Item Picking allocation Work Report Report
		lbl_ListName.setText(DisplayText.getText("TLE-W0526"));

		//#CM710062
		// Obtain the parameter. 
		//#CM710063
		// Consignor Code
		String consignorcode = request.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM710064
		// Planned Picking Date
		String retrievalplandate = request.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM710065
		// Start Item Code 
		String startitemcode = request.getParameter(ListRetrievalItemBusiness.STARTITEMCODE_KEY);
		//#CM710066
		// End Item Code 
		String enditemcode = request.getParameter(ListRetrievalItemBusiness.ENDITEMCODE_KEY);
		//#CM710067
		// Case/Piece division 
		String casepieceflag = request.getParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY);

		//#CM710068
		// Check for mandatory input and forbidden character in the Consignor code. 
		if (!WmsCheckker.consignorCheck(consignorcode, lst_ItemRtrivlAllocWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710069
		// Check for mandatory input and prohibited characters in the Picking Plan Info. 
		if (!WmsCheckker.retrievalplandateCheck(retrievalplandate, lst_ItemRtrivlAllocWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710070
		// Check the Start item code for forbidden character. 
		if (!WmsCheckker.charCheck(startitemcode, lst_ItemRtrivlAllocWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710071
		// Check the End item code for forbidden character. 
		if (!WmsCheckker.charCheck(enditemcode, lst_ItemRtrivlAllocWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710072
		// Check the values for precedence between Start Item Code and End Item Code. 
		if (!WmsCheckker.rangeItemCodeCheck(startitemcode, enditemcode, lst_ItemRtrivlAllocWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710073
		// Display the search conditions on the screen. 
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		txt_FDate.setDate(WmsFormatter.toDate(retrievalplandate));

		//#CM710074
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM710075
			// Close the connection. 
			sRet.closeConnection();
			//#CM710076
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM710077
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);
		
		//#CM710078
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM710079
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM710080
		// Planned Picking Date
		param.setRetrievalPlanDate(retrievalplandate);
		//#CM710081
		// Start Item Code 
		param.setStartItemCode(startitemcode);
		//#CM710082
		// End Item Code 
		param.setEndItemCode(enditemcode);
		//#CM710083
		// Case/Piece division 
		param.setCasePieceflg(casepieceflag);

		//#CM710084
		// Generate the SessionRetrievalOrderQtyListRet instance. 
		SessionRetrievalItemAllocListRet listbox = new SessionRetrievalItemAllocListRet(conn, param);
		//#CM710085
		// Maintain the list box in the Session 
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM710086
	/**
	 * Invoke this before invoking each control event. <BR>
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
	}

	//#CM710087
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710088
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710089
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710090
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710091
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710092
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710093
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710094
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM710095
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM710096
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710097
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

	//#CM710098
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

	//#CM710099
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

	//#CM710100
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

	//#CM710101
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

	//#CM710102
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710103
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
		//#CM710104
		// Maintain the list box in the Session 
		SessionRetrievalItemAllocListRet listbox = (SessionRetrievalItemAllocListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM710105
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
		//#CM710106
		// Maintain the list box in the Session 
		SessionRetrievalItemAllocListRet listbox = (SessionRetrievalItemAllocListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM710107
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
		//#CM710108
		// Maintain the list box in the Session 
		SessionRetrievalItemAllocListRet listbox = (SessionRetrievalItemAllocListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM710109
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
		//#CM710110
		// Maintain the list box in the Session 
		SessionRetrievalItemAllocListRet listbox = (SessionRetrievalItemAllocListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM710111
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710112
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
		//#CM710113
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM710114
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM710115
				// Close the statement. 
				finder.close();
			}
			//#CM710116
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM710117
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM710118
		// Return to the parent screen. 
		parentRedirect(null);
	}

	//#CM710119
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlAllocWorkList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM710120
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlAllocWorkList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM710121
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlAllocWorkList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM710122
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlAllocWorkList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM710123
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlAllocWorkList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710124
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlAllocWorkList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM710125
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlAllocWorkList_Click(ActionEvent e) throws Exception
	{
	}

	//#CM710126
	// Package methods -----------------------------------------------

	//#CM710127
	// Protected methods ---------------------------------------------

	//#CM710128
	// Private methods -----------------------------------------------
	
	//#CM710129
	/**
	 * Allow this method to change a page.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(SessionRetrievalItemAllocListRet listbox, String actionName) throws Exception
	{
		//#CM710130
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM710131
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM710132
			// Set the Consignor name as a search condition. 
			lbl_JavaSetCnsgnrNm.setText(rsparam[0].getConsignorName());

			//#CM710133
			// Set a value for Pager. 
			//#CM710134
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM710135
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM710136
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM710137
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM710138
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM710139
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM710140
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM710141
			// Delete all lines. 
			lst_ItemRtrivlAllocWorkList.clearRow();

			//#CM710142
			// Item Name
			String label_itemname = DisplayText.getText("LBL-W0103");

			for (int i = 0; i < len; i++)
			{
				//#CM710143
				// Obtain the end line. 
				int count = lst_ItemRtrivlAllocWorkList.getMaxRows();
				//#CM710144
				// Add a line. 
				lst_ItemRtrivlAllocWorkList.addRow();

				//#CM710145
				// Move to the end line. 
				lst_ItemRtrivlAllocWorkList.setCurrentRow(count);

				//#CM710146
				// Item Code
				lst_ItemRtrivlAllocWorkList.setValue(1, rsparam[i].getItemCode());		
				//#CM710147
				// Case/Piece division 
				lst_ItemRtrivlAllocWorkList.setValue(2, rsparam[i].getCasePieceflgName());
				//#CM710148
				// Picking Location 
				lst_ItemRtrivlAllocWorkList.setValue(3, 
						WmsFormatter.toDispLocation(rsparam[i].getRetrievalLocation(), rsparam[i].getSystemDiscKey()));
				//#CM710149
				// Item Name
				lst_ItemRtrivlAllocWorkList.setValue(4, rsparam[i].getItemName());
				//#CM710150
				// Compile the ToolTip data. 
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM710151
				// Item Name
				toolTip.add(label_itemname, rsparam[i].getItemName());

				//#CM710152
				// Set the TOOL TIP in the current line. 
				lst_ItemRtrivlAllocWorkList.setToolTip(lst_ItemRtrivlAllocWorkList.getCurrentRow(), toolTip.getText());
			}
		}
		else
		{
			//#CM710153
			// Set a value for Pager. 
			//#CM710154
			// Max count 
			pgr_U.setMax(0);
			//#CM710155
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM710156
			// Start Position 
			pgr_U.setIndex(0);
			//#CM710157
			// Max count 
			pgr_D.setMax(0);
			//#CM710158
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM710159
			// Start Position 
			pgr_D.setIndex(0);

			//#CM710160
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM710161
			// Hide the header. 
			lst_ItemRtrivlAllocWorkList.setVisible(false);
			//#CM710162
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM710163
	// Event handler methods -----------------------------------------
}
//#CM710164
//end of class
