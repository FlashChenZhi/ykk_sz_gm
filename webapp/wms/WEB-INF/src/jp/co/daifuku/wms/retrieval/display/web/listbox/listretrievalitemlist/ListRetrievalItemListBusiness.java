// $Id: ListRetrievalItemListBusiness.java,v 1.2 2007/02/07 04:18:47 suresh Exp $

//#CM710169
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitemlist;
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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalItemListRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM710170
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this listbox class to search for Item Picking Work Report list. <BR>
 * Search for the databased on Consignor Code, Planned Picking Date, Item Code, Case/Piece division, and work status entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Consignor Code, Planned Picking Date, Item Code, Case/Piece division, and work status entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/22</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:47 $
 * @author  $Author: suresh $
 */
public class ListRetrievalItemListBusiness extends ListRetrievalItemList implements WMSConstants
{
	//#CM710171
	// Class fields --------------------------------------------------
	//#CM710172
	/** 
	 * Use this key to pass the Case/Piece division.
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";

	//#CM710173
	/** 
	 * Use this key to pass the work status.
	 */
	public static final String WORKSTATUS_KEY = "WORKSTATUS_KEY";
	//#CM710174
	// Class variables -----------------------------------------------

	//#CM710175
	// Class method --------------------------------------------------

	//#CM710176
	// Constructors --------------------------------------------------

	//#CM710177
	// Public methods ------------------------------------------------

	//#CM710178
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Field item  <BR>
	 *	<DIR>
	 *		1. Picking Location  <BR>
	 *		2.Item Code <BR>
	 *		3. Division  <BR>
	 *		4.Planned total qty <BR>
	 *		5.Packed Qty per Case <BR>
	 *		6.Planned Work Case Qty <BR>
	 *		7. Result Case Qty  <BR>
	 *		8.Case ITF <BR>
	 *		9.Expiry Date <BR>
	 *		10. status  <BR>
	 *		11.Item Name <BR>
	 *		12.Packed qty per bundle <BR>
	 *		13.Planned Work Piece Qty <BR>
	 *		14. Result Piece Qty  <BR>
	 *		15.Bundle ITF <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM710179
		// Set the screen name. 
		//#CM710180
		// Item Picking Work list 
		lbl_ListName.setText(DisplayText.getText("TLE-W0076"));

		//#CM710181
		// Obtain the parameter. 
		//#CM710182
		// Consignor Code
		String consignorcode = request.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM710183
		// Planned Picking Date
		String retrievalplandate = request.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM710184
		// Item Code
		String itemcode = request.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);
		//#CM710185
		// Case/Piece division 
		String casepieceflag = request.getParameter(CASEPIECEFLAG_KEY);
		//#CM710186
		// Work status:
		String workstatus = request.getParameter(WORKSTATUS_KEY);

		//#CM710187
		// Check for mandatory input and forbidden character in the Consignor code. 
		if (!WmsCheckker.consignorCheck(consignorcode, lst_ItemRtrivlWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710188
		// Check for mandatory input and prohibited characters in the Picking Plan Info. 
		if (!WmsCheckker.retrievalplandateCheck(retrievalplandate, lst_ItemRtrivlWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710189
		// Check the Order No. for prohibited character.
		if (!WmsCheckker.charCheck(itemcode, lst_ItemRtrivlWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710190
		// Display the search conditions on the screen. 
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		txt_FDate.setDate(WmsFormatter.toDate(retrievalplandate));

		//#CM710191
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM710192
			// Close the connection. 
			sRet.closeConnection();
			//#CM710193
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM710194
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM710195
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM710196
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM710197
		// Planned Picking Date
		param.setRetrievalPlanDate(retrievalplandate);
		//#CM710198
		// Item Code
		param.setItemCode(itemcode);
		//#CM710199
		// Case/Piece division 
		param.setCasePieceflg(casepieceflag);
		//#CM710200
		// Work status:
		param.setWorkStatus(workstatus);

		//#CM710201
		// Generate the SessionRetrievalItemStartRet instance. 
		SessionRetrievalItemListRet listbox = new SessionRetrievalItemListRet(conn, param);
		//#CM710202
		// Maintain the list box in the Session 
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM710203
	// Package methods -----------------------------------------------

	//#CM710204
	// Protected methods ---------------------------------------------

	//#CM710205
	// Private methods -----------------------------------------------
	//#CM710206
	/**
	 * Allow this method to change a page.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(SessionRetrievalItemListRet listbox, String actionName) throws Exception
	{
		//#CM710207
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM710208
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM710209
			// Set the Consignor name as a search condition. 
			lbl_JavaSetCnsgnrNm.setText(rsparam[0].getConsignorName());

			//#CM710210
			// Set a value for Pager. 
			//#CM710211
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM710212
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM710213
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM710214
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM710215
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM710216
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM710217
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM710218
			// Delete all lines. 
			lst_ItemRtrivlWorkList.clearRow();

			//#CM710219
			// Item Name
			String label_itemname = DisplayText.getText("LBL-W0103");
			//#CM710220
			// Case ITF
			String label_caseitf = DisplayText.getText("LBL-W0010");
			//#CM710221
			// Bundle ITF
			String label_bundleitf = DisplayText.getText("LBL-W0006");
			//#CM710222
			// Expiry Date
			String label_usebydate = DisplayText.getText("LBL-W0270");
			//#CM710223
			// status 
			String label_status = DisplayText.getText("LBL-W0229");

			for (int i = 0; i < len; i++)
			{
				//#CM710224
				// Obtain the end line. 
				int count = lst_ItemRtrivlWorkList.getMaxRows();
				//#CM710225
				// Add a line. 
				lst_ItemRtrivlWorkList.addRow();

				//#CM710226
				// Move to the end line. 
				lst_ItemRtrivlWorkList.setCurrentRow(count);
				lst_ItemRtrivlWorkList.setValue(1, rsparam[i].getRetrievalLocation());
				lst_ItemRtrivlWorkList.setValue(2, rsparam[i].getItemCode());
				lst_ItemRtrivlWorkList.setValue(3, rsparam[i].getCasePieceflgName());
				lst_ItemRtrivlWorkList.setValue(4, WmsFormatter.getNumFormat(rsparam[i].getTotalPlanQty()));
				lst_ItemRtrivlWorkList.setValue(5, WmsFormatter.getNumFormat(rsparam[i].getEnteringQty()));
				lst_ItemRtrivlWorkList.setValue(6, WmsFormatter.getNumFormat(rsparam[i].getPlanCaseQty()));
				lst_ItemRtrivlWorkList.setValue(7, WmsFormatter.getNumFormat(rsparam[i].getResultCaseQty()));
				lst_ItemRtrivlWorkList.setValue(8, rsparam[i].getITF());
				lst_ItemRtrivlWorkList.setValue(9, rsparam[i].getUseByDate());
				lst_ItemRtrivlWorkList.setValue(10, rsparam[i].getWorkStatusName());
				lst_ItemRtrivlWorkList.setValue(11, rsparam[i].getItemName());
				lst_ItemRtrivlWorkList.setValue(12, WmsFormatter.getNumFormat(rsparam[i].getBundleEnteringQty()));
				lst_ItemRtrivlWorkList.setValue(13, WmsFormatter.getNumFormat(rsparam[i].getPlanPieceQty()));
				lst_ItemRtrivlWorkList.setValue(14, WmsFormatter.getNumFormat(rsparam[i].getResultPieceQty()));
				lst_ItemRtrivlWorkList.setValue(15, rsparam[i].getBundleITF());

				//#CM710227
				// Compile the ToolTip data. 
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM710228
				// Item Name
				toolTip.add(label_itemname, rsparam[i].getItemName());
				//#CM710229
				// Case ITF
				toolTip.add(label_caseitf, rsparam[i].getITF());
				//#CM710230
				// Bundle ITF
				toolTip.add(label_bundleitf, rsparam[i].getBundleITF());
				//#CM710231
				// Expiry Date
				toolTip.add(label_usebydate, rsparam[i].getUseByDate());
				//#CM710232
				// status 
				toolTip.add(label_status, rsparam[i].getWorkStatusName());

				//#CM710233
				// Set the TOOL TIP. 	
				lst_ItemRtrivlWorkList.setToolTip(lst_ItemRtrivlWorkList.getCurrentRow(), toolTip.getText());

			}
		}
		else
		{
			//#CM710234
			// Set a value for Pager. 
			//#CM710235
			// Max count 
			pgr_U.setMax(0);
			//#CM710236
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM710237
			// Start Position 
			pgr_U.setIndex(0);
			//#CM710238
			// Max count 
			pgr_D.setMax(0);
			//#CM710239
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM710240
			// Start Position 
			pgr_D.setIndex(0);

			//#CM710241
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM710242
			// Hide the header. 
			lst_ItemRtrivlWorkList.setVisible(false);
			//#CM710243
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM710244
	// Event handler methods -----------------------------------------
	//#CM710245
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710246
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710247
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710248
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710249
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710250
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710251
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710252
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM710253
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM710254
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710255
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

	//#CM710256
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

	//#CM710257
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

	//#CM710258
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

	//#CM710259
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

	//#CM710260
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710261
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlWorkList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM710262
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlWorkList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM710263
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlWorkList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM710264
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlWorkList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM710265
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlWorkList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710266
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlWorkList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM710267
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlWorkList_Click(ActionEvent e) throws Exception
	{
	}

	//#CM710268
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
		//#CM710269
		// Maintain the list box in the Session 
		SessionRetrievalItemListRet listbox = (SessionRetrievalItemListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM710270
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
		//#CM710271
		// Maintain the list box in the Session 
		SessionRetrievalItemListRet listbox = (SessionRetrievalItemListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM710272
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
		//#CM710273
		// Maintain the list box in the Session 
		SessionRetrievalItemListRet listbox = (SessionRetrievalItemListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM710274
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
		//#CM710275
		// Maintain the list box in the Session 
		SessionRetrievalItemListRet listbox = (SessionRetrievalItemListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM710276
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710277
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
		//#CM710278
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM710279
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM710280
				// Close the statement. 
				finder.close();
			}
			//#CM710281
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM710282
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM710283
		// Return to the parent screen. 
		parentRedirect(null);
	}
}
//#CM710284
//end of class
