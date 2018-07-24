// $Id: ListRetrievalItemPlanListBusiness.java,v 1.2 2007/02/07 04:18:48 suresh Exp $

//#CM710289
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitemplanlist;
import java.sql.Connection;
import java.util.Locale;

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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitemlist.ListRetrievalItemListBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalItemPlanListRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM710290
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this listbox class to search for Item Picking Plan Report list. <BR>
 * Search for the databased on Consignor Code, Start Planned Picking Date, End Planned Picking Date, Item Code, and work status entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Consignor Code, Start Planned Picking Date, End Planned Picking Date, Item Code, and work status entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/22</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:48 $
 * @author  $Author: suresh $
 */
public class ListRetrievalItemPlanListBusiness extends ListRetrievalItemPlanList implements WMSConstants
{
	//#CM710291
	// Class fields --------------------------------------------------

	//#CM710292
	// Class variables -----------------------------------------------

	//#CM710293
	// Class method --------------------------------------------------

	//#CM710294
	// Constructors --------------------------------------------------

	//#CM710295
	// Public methods ------------------------------------------------

	//#CM710296
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Field item  <BR>
	 *	<DIR>
	 *		1.Planned Picking Date <BR>
	 *		2.Item Code <BR>
	 *		3. Division  <BR>
	 *		4.Planned total qty <BR>
	 *		5.Packed Qty per Case <BR>
	 *		6. Host planned Case qty  <BR>
	 *		7. Picking Case Qty  <BR>
	 *		8.Case Picking Location <BR>
	 *		9. status  <BR>
	 *		10.Item Name <BR>
	 *		11.Packed qty per bundle <BR>
	 *		12.Host Planned Piece Qty <BR>
	 *		13. Picking Piece Qty  <BR>
	 *		14. Piece Picking Location  <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM710297
		// Set the screen name. 
		//#CM710298
		// Item Picking Plan list 
		lbl_ListName.setText(DisplayText.getText("TLE-W0078"));

		//#CM710299
		// Obtain the parameter. 
		//#CM710300
		// Consignor Code
		String consignorcode = request.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM710301
		// Start Planned Picking Date
		String startretrievalplandate = request.getParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY);
		//#CM710302
		// End Planned Picking Date
		String endretrievalplandate = request.getParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY);
		//#CM710303
		// Item Code
		String itemcode = request.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);
		//#CM710304
		// Work status:
		String workstatus = request.getParameter(ListRetrievalItemListBusiness.WORKSTATUS_KEY);

		//#CM710305
		// Check for mandatory input and forbidden character in the Consignor code. 
		if (!WmsCheckker.consignorCheck(consignorcode, lst_ItemRtrivlPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710306
		// Check the range of Planned Picking Date. 
		if (!WmsCheckker.rangeRetrievalPlanDateCheck(startretrievalplandate, endretrievalplandate, lst_ItemRtrivlPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710307
		// Check for forbidden character in the Item code. 
		if (!WmsCheckker.charCheck(itemcode, lst_ItemRtrivlPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710308
		// Display the search conditions on the screen. 
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		txt_FDateStart.setDate(WmsFormatter.toDate(startretrievalplandate));
		txt_FDateEnd.setDate(WmsFormatter.toDate(endretrievalplandate));

		//#CM710309
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM710310
			// Close the connection. 
			sRet.closeConnection();
			//#CM710311
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM710312
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM710313
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM710314
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM710315
		// Start Planned Picking Date
		param.setFromRetrievalPlanDate(startretrievalplandate);
		//#CM710316
		// End Planned Picking Date
		param.setToRetrievalPlanDate(endretrievalplandate);
		//#CM710317
		// Item Code
		param.setItemCode(itemcode);
		//#CM710318
		// Work status:
		param.setWorkStatus(workstatus);

		//#CM710319
		// Generate the SessionRetrievalItemPlanListRet instance. 
		SessionRetrievalItemPlanListRet listbox = new SessionRetrievalItemPlanListRet(conn, param);
		//#CM710320
		// Maintain the list box in the Session 
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM710321
	// Package methods -----------------------------------------------

	//#CM710322
	// Protected methods ---------------------------------------------

	//#CM710323
	// Private methods -----------------------------------------------
	//#CM710324
	/**
	 * Allow this method to change a page.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(SessionRetrievalItemPlanListRet listbox, String actionName) throws Exception
	{
		//#CM710325
		// Obtain the Local. 
		Locale locale = this.getHttpRequest().getLocale();

		//#CM710326
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM710327
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM710328
			// Set the Consignor name as a search condition. 
			lbl_JavaSetCnsgnrNm.setText(rsparam[0].getConsignorName());

			//#CM710329
			// Set a value for Pager. 
			//#CM710330
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM710331
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM710332
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM710333
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM710334
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM710335
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM710336
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM710337
			// Delete all lines. 
			lst_ItemRtrivlPlanList.clearRow();

			//#CM710338
			// Item Name
			String label_itemname = DisplayText.getText("LBL-W0103");
			//#CM710339
			// Case Picking Location
			String label_caselocation = DisplayText.getText("LBL-W0017");
			//#CM710340
			// Piece Picking Location 
			String label_piecelocation = DisplayText.getText("LBL-W0152");
			//#CM710341
			// status 
			String label_status = DisplayText.getText("LBL-W0229");

			for (int i = 0; i < len; i++)
			{
				//#CM710342
				// Obtain the end line. 
				int count = lst_ItemRtrivlPlanList.getMaxRows();
				//#CM710343
				// Add a line. 
				lst_ItemRtrivlPlanList.addRow();

				//#CM710344
				// Move to the end line. 
				lst_ItemRtrivlPlanList.setCurrentRow(count);
				//#CM710345
				// Planned Picking Date
				lst_ItemRtrivlPlanList.setValue(1, WmsFormatter.toDispDate(rsparam[i].getRetrievalPlanDate(), locale));
				//#CM710346
				// Item Code
				lst_ItemRtrivlPlanList.setValue(2, rsparam[i].getItemCode());
				//#CM710347
				// Case/Piece division (name) 
				lst_ItemRtrivlPlanList.setValue(3, rsparam[i].getCasePieceflgName());
				//#CM710348
				// Planned total qty
				lst_ItemRtrivlPlanList.setValue(4, WmsFormatter.getNumFormat(rsparam[i].getTotalPlanQty()));
				//#CM710349
				// Packed Qty per Case
				lst_ItemRtrivlPlanList.setValue(5, WmsFormatter.getNumFormat(rsparam[i].getEnteringQty()));
				//#CM710350
				// Host planned Case qty 
				lst_ItemRtrivlPlanList.setValue(6, WmsFormatter.getNumFormat(rsparam[i].getPlanCaseQty()));
				//#CM710351
				// Picking Case Qty 
				lst_ItemRtrivlPlanList.setValue(7, WmsFormatter.getNumFormat(rsparam[i].getResultCaseQty()));
				//#CM710352
				// Case Picking Location
				lst_ItemRtrivlPlanList.setValue(8, rsparam[i].getCaseLocation());
				//#CM710353
				// Work status:
				lst_ItemRtrivlPlanList.setValue(9, rsparam[i].getWorkStatusName());
				//#CM710354
				// Item Name
				lst_ItemRtrivlPlanList.setValue(10, rsparam[i].getItemName());
				//#CM710355
				// Packed qty per bundle
				lst_ItemRtrivlPlanList.setValue(11, WmsFormatter.getNumFormat(rsparam[i].getBundleEnteringQty()));
				//#CM710356
				// Host Planned Piece Qty
				lst_ItemRtrivlPlanList.setValue(12, WmsFormatter.getNumFormat(rsparam[i].getPlanPieceQty()));
				//#CM710357
				// Picking Piece Qty 
				lst_ItemRtrivlPlanList.setValue(13, WmsFormatter.getNumFormat(rsparam[i].getResultPieceQty()));
				//#CM710358
				// Piece Picking Location 
				lst_ItemRtrivlPlanList.setValue(14, rsparam[i].getPieceLocation());

				//#CM710359
				// Compile the ToolTip data. 
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM710360
				// Item Name
				toolTip.add(label_itemname, rsparam[i].getItemName());
				//#CM710361
				// Case Picking Location
				toolTip.add(label_caselocation, rsparam[i].getCaseLocation());
				//#CM710362
				// Piece Picking Location 
				toolTip.add(label_piecelocation, rsparam[i].getPieceLocation());
				//#CM710363
				// status 
				toolTip.add(label_status, rsparam[i].getWorkStatusName());

				//#CM710364
				// Set the TOOL TIP. 	
				lst_ItemRtrivlPlanList.setToolTip(lst_ItemRtrivlPlanList.getCurrentRow(), toolTip.getText());
			}
		}
		else
		{
			//#CM710365
			// Set a value for Pager. 
			//#CM710366
			// Max count 
			pgr_U.setMax(0);
			//#CM710367
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM710368
			// Start Position 
			pgr_U.setIndex(0);
			//#CM710369
			// Max count 
			pgr_D.setMax(0);
			//#CM710370
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM710371
			// Start Position 
			pgr_D.setIndex(0);

			//#CM710372
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM710373
			// Hide the header. 
			lst_ItemRtrivlPlanList.setVisible(false);
			//#CM710374
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM710375
	// Event handler methods -----------------------------------------
	//#CM710376
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710377
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710378
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710379
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710380
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710381
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710382
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710383
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStart_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM710384
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStart_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM710385
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710386
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710387
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM710388
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM710389
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710390
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

	//#CM710391
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

	//#CM710392
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

	//#CM710393
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

	//#CM710394
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

	//#CM710395
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710396
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlPlanList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM710397
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlPlanList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM710398
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlPlanList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM710399
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlPlanList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM710400
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlPlanList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710401
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlPlanList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM710402
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemRtrivlPlanList_Click(ActionEvent e) throws Exception
	{
	}

	//#CM710403
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
		//#CM710404
		// Maintain the list box in the Session 
		SessionRetrievalItemPlanListRet listbox = (SessionRetrievalItemPlanListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM710405
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
		//#CM710406
		// Maintain the list box in the Session 
		SessionRetrievalItemPlanListRet listbox = (SessionRetrievalItemPlanListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM710407
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
		//#CM710408
		// Maintain the list box in the Session 
		SessionRetrievalItemPlanListRet listbox = (SessionRetrievalItemPlanListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM710409
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
		//#CM710410
		// Maintain the list box in the Session 
		SessionRetrievalItemPlanListRet listbox = (SessionRetrievalItemPlanListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM710411
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710412
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
		//#CM710413
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM710414
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM710415
				// Close the statement. 
				finder.close();
			}
			//#CM710416
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM710417
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM710418
		// Return to the parent screen. 
		parentRedirect(null);
	}
}
//#CM710419
//end of class
