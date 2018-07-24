// $Id: ListRetrievalOrderPlanListBusiness.java,v 1.2 2007/02/07 04:18:53 suresh Exp $

//#CM711298
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderplanlist;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalcustomer.ListRetrievalCustomerBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderlist.ListRetrievalOrderListBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalOrderPlanListRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM711299
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this listbox class to search for Order Picking Plan Report list. <BR>
 * Search for the databased on Consignor Code, Planned Picking Date, Case Order No., Piece Order No., and work status entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Consignor Code, Planned Picking Date, Case Order No., Piece Order No., and work status entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/22</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:53 $
 * @author  $Author: suresh $
 */
public class ListRetrievalOrderPlanListBusiness
	extends ListRetrievalOrderPlanList
	implements WMSConstants
{
	//#CM711300
	// Class fields --------------------------------------------------

	//#CM711301
	// Class variables -----------------------------------------------

	//#CM711302
	// Class method --------------------------------------------------

	//#CM711303
	// Constructors --------------------------------------------------

	//#CM711304
	// Public methods ------------------------------------------------

	//#CM711305
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Field item  <BR>
	 *	<DIR>
	 *		1.Planned Picking Date <BR>
	 *		2.Customer Code <BR>
	 *		3.Case Order No. <BR>
	 *		4.Item Code <BR>
	 *		5. Division  <BR>
	 *		6.Packed Qty per Case <BR>
	 *		7. Host planned Case qty  <BR>
	 *		8. Result Case Qty  <BR>
	 *		9.Case Picking Location <BR>
	 *		10. status  <BR>
	 *		11.Customer Name <BR>
	 *		12.Piece Order No. <BR>
	 *		13.Item Name <BR>
	 *		14.Packed qty per bundle <BR>
	 *		15.Host Planned Piece Qty <BR>
	 *		16. Result Piece Qty  <BR>
	 *		17. Piece Picking Location  <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM711306
		// Set the screen name. 
		//#CM711307
		// Order Picking Plan list 
		lbl_ListName.setText(DisplayText.getText("TLE-W0077"));

		//#CM711308
		// Obtain the parameter. 
		//#CM711309
		// Consignor Code
		String consignorcode = request.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM711310
		// Start Planned Picking Date
		String startretrievalplandate =	request.getParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY);
		//#CM711311
		// End Planned Picking Date
		String endretrievalplandate = request.getParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY);
		//#CM711312
		// Customer Code
		String customercode = request.getParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY);
		//#CM711313
		// Case Order No.
		String caseorderno = request.getParameter(ListRetrievalOrdernoBusiness.CASEORDERNO_KEY);
		//#CM711314
		// Piece Order No.
		String pieceorderno = request.getParameter(ListRetrievalOrdernoBusiness.PIECEORDERNO_KEY);
		//#CM711315
		// Work status:
		String workstatus = request.getParameter(ListRetrievalOrderListBusiness.WORKSTATUS_KEY);

		//#CM711316
		// Check for mandatory input and forbidden character in the Consignor code. 
		if (!WmsCheckker.consignorCheck(consignorcode, lst_OdrRtrivlPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711317
		// Check the range of Planned Picking Date. 
		if (!WmsCheckker.rangeRetrievalPlanDateCheck(
				startretrievalplandate,
				endretrievalplandate,
				lst_OdrRtrivlPlanList,
				pgr_U,
				pgr_D,
				lbl_InMsg))
		{
			return;
		}
		
		//#CM711318
		// Check for input of the Customer Code. 
		if (!WmsCheckker.charCheck(customercode, lst_OdrRtrivlPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711319
		// Check the Case Order No. for prohibited character.
		if (!WmsCheckker.charCheck(caseorderno, lst_OdrRtrivlPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711320
		// Check the Piece Order No. for prohibited character.
		if (!WmsCheckker.charCheck(pieceorderno, lst_OdrRtrivlPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711321
		// Display the search conditions on the screen. 
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		txt_FDateStart.setDate(WmsFormatter.toDate(startretrievalplandate));
		txt_FDateEnd.setDate(WmsFormatter.toDate(endretrievalplandate));

		//#CM711322
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM711323
			// Close the connection. 
			sRet.closeConnection();
			//#CM711324
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM711325
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM711326
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM711327
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM711328
		// Start Planned Picking Date
		param.setFromRetrievalPlanDate(startretrievalplandate);
		//#CM711329
		// End Planned Picking Date
		param.setToRetrievalPlanDate(endretrievalplandate);
		//#CM711330
		// Customer Code
		param.setCustomerCode(customercode);
		//#CM711331
		// Case Order No.
		param.setCaseOrderNo(caseorderno);
		//#CM711332
		// Piece Order No.
		param.setPieceOrderNo(pieceorderno);
		//#CM711333
		// Work status:
		param.setWorkStatus(workstatus);

		//#CM711334
		// Generate the SessionRetrievalOrderPlanListRet instance. 
		SessionRetrievalOrderPlanListRet listbox = new SessionRetrievalOrderPlanListRet(conn, param);
		//#CM711335
		// Maintain the list box in the Session 
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM711336
	// Package methods -----------------------------------------------

	//#CM711337
	// Protected methods ---------------------------------------------

	//#CM711338
	// Private methods -----------------------------------------------
	//#CM711339
	/**
	 * Allow this method to change a page.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(SessionRetrievalOrderPlanListRet listbox, String actionName)
		throws Exception
	{
		//#CM711340
		// Obtain the Local. 
		Locale locale = this.getHttpRequest().getLocale();

		//#CM711341
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM711342
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM711343
			// Set the Consignor name as a search condition. 
			lbl_JavaSetCnsgnrNm.setText(rsparam[0].getConsignorName());

			//#CM711344
			// Set a value for Pager. 
			//#CM711345
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM711346
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM711347
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM711348
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM711349
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM711350
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM711351
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM711352
			// Delete all lines. 
			lst_OdrRtrivlPlanList.clearRow();

			//#CM711353
			// Customer Name
		String label_customername = DisplayText.getText("LBL-W0036");
			//#CM711354
			// Item Name
		String label_itemname = DisplayText.getText("LBL-W0103");
			//#CM711355
			// Host planned Case qty 
		String label_plancaseqty = DisplayText.getText("LBL-W0385");
			//#CM711356
			// Host Planned Piece Qty
		String label_planpieceqty = DisplayText.getText("LBL-W0386");
			//#CM711357
			// Result Case Qty 
		String label_resultcaseqty = DisplayText.getText("LBL-W0418");
			//#CM711358
			// Result Piece Qty 
		String label_resultpieceqty = DisplayText.getText("LBL-W0417");
			//#CM711359
			// Case Picking Location
		String label_caselocation = DisplayText.getText("LBL-W0017");
			//#CM711360
			// Piece Picking Location 
		String label_piecelocation = DisplayText.getText("LBL-W0152");
			//#CM711361
			// status 
		String label_status = DisplayText.getText("LBL-W0229");

			for (int i = 0; i < len; i++)
			{
				//#CM711362
				// Obtain the end line. 
				int count = lst_OdrRtrivlPlanList.getMaxRows();
				//#CM711363
				// Add a line. 
				lst_OdrRtrivlPlanList.addRow();

				//#CM711364
				// Move to the end line. 
				lst_OdrRtrivlPlanList.setCurrentRow(count);
				lst_OdrRtrivlPlanList.setValue(
					1,
					WmsFormatter.toDispDate(rsparam[i].getRetrievalPlanDate(), locale));
				lst_OdrRtrivlPlanList.setValue(2, rsparam[i].getCustomerCode());
				lst_OdrRtrivlPlanList.setValue(3, rsparam[i].getCaseOrderNo());
				lst_OdrRtrivlPlanList.setValue(4, rsparam[i].getItemCode());
				lst_OdrRtrivlPlanList.setValue(5, DisplayUtil.getPieceCaseValue(rsparam[i].getCasePieceflgName()));
				lst_OdrRtrivlPlanList.setValue(
					6,
					WmsFormatter.getNumFormat(rsparam[i].getEnteringQty()));
				lst_OdrRtrivlPlanList.setValue(
					7,
					WmsFormatter.getNumFormat(rsparam[i].getPlanCaseQty()));
				lst_OdrRtrivlPlanList.setValue(
					8,
					WmsFormatter.getNumFormat(rsparam[i].getResultCaseQty()));
				lst_OdrRtrivlPlanList.setValue(9, rsparam[i].getCaseLocation());
				lst_OdrRtrivlPlanList.setValue(10, DisplayUtil.getRetrievalPlanStatusValue(rsparam[i].getStatusFlagL()));
				lst_OdrRtrivlPlanList.setValue(11, rsparam[i].getCustomerName());
				lst_OdrRtrivlPlanList.setValue(12, rsparam[i].getPieceOrderNo());
				lst_OdrRtrivlPlanList.setValue(13, rsparam[i].getItemName());
				lst_OdrRtrivlPlanList.setValue(
					14,
					WmsFormatter.getNumFormat(rsparam[i].getBundleEnteringQty()));
				lst_OdrRtrivlPlanList.setValue(
					15,
					WmsFormatter.getNumFormat(rsparam[i].getPlanPieceQty()));
				lst_OdrRtrivlPlanList.setValue(
					16,
					WmsFormatter.getNumFormat(rsparam[i].getResultPieceQty()));
				lst_OdrRtrivlPlanList.setValue(17, rsparam[i].getPieceLocation());

				//#CM711365
				// Compile the ToolTip data. 
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM711366
				// Customer Name
				toolTip.add(label_customername, rsparam[i].getCustomerName());
				//#CM711367
				// Item Name
				toolTip.add(label_itemname, rsparam[i].getItemName());
				//#CM711368
				// Host planned Case qty 
				toolTip.add(label_plancaseqty, WmsFormatter.getNumFormat(rsparam[i].getPlanCaseQty()));
				//#CM711369
				// Host Planned Piece Qty
				toolTip.add(label_planpieceqty, WmsFormatter.getNumFormat(rsparam[i].getPlanPieceQty()));
				//#CM711370
				// Result Case Qty 
				toolTip.add(label_resultcaseqty, WmsFormatter.getNumFormat(rsparam[i].getResultCaseQty()));
				//#CM711371
				// Result Piece Qty 
				toolTip.add(label_resultpieceqty, WmsFormatter.getNumFormat(rsparam[i].getResultPieceQty()));
				//#CM711372
				// Case Picking Location
				toolTip.add(label_caselocation, rsparam[i].getCaseLocation());
				//#CM711373
				// Piece Picking Location 
				toolTip.add(label_piecelocation, rsparam[i].getPieceLocation());
				//#CM711374
				// status 
				toolTip.add(label_status, DisplayUtil.getRetrievalPlanStatusValue(rsparam[i].getStatusFlagL()));

				//#CM711375
				// Set the TOOL TIP. 	
				lst_OdrRtrivlPlanList.setToolTip(lst_OdrRtrivlPlanList.getCurrentRow(), toolTip.getText());

			}
		}
		else
		{
			//#CM711376
			// Set a value for Pager. 
			//#CM711377
			// Max count 
			pgr_U.setMax(0);
			//#CM711378
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM711379
			// Start Position 
			pgr_U.setIndex(0);
			//#CM711380
			// Max count 
			pgr_D.setMax(0);
			//#CM711381
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM711382
			// Start Position 
			pgr_D.setIndex(0);

			//#CM711383
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM711384
			// Hide the header. 
			lst_OdrRtrivlPlanList.setVisible(false);
			//#CM711385
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM711386
	// Event handler methods -----------------------------------------
	//#CM711387
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711388
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711389
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711390
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711391
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711392
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711393
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711394
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStart_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM711395
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStart_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM711396
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711397
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711398
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM711399
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM711400
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711401
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

	//#CM711402
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

	//#CM711403
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

	//#CM711404
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

	//#CM711405
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

	//#CM711406
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711407
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlPlanList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM711408
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlPlanList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM711409
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlPlanList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM711410
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlPlanList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM711411
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlPlanList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711412
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlPlanList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM711413
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlPlanList_Click(ActionEvent e) throws Exception
	{
	}

	//#CM711414
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
		//#CM711415
		// Maintain the list box in the Session 
		SessionRetrievalOrderPlanListRet listbox =
			(SessionRetrievalOrderPlanListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM711416
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
		//#CM711417
		// Maintain the list box in the Session 
		SessionRetrievalOrderPlanListRet listbox =
			(SessionRetrievalOrderPlanListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM711418
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
		//#CM711419
		// Maintain the list box in the Session 
		SessionRetrievalOrderPlanListRet listbox =
			(SessionRetrievalOrderPlanListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM711420
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
		//#CM711421
		// Maintain the list box in the Session 
		SessionRetrievalOrderPlanListRet listbox =
			(SessionRetrievalOrderPlanListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM711422
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711423
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
		//#CM711424
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM711425
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM711426
				// Close the statement. 
				finder.close();
			}
			//#CM711427
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM711428
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM711429
		// Return to the parent screen. 
		parentRedirect(null);
	}
}
//#CM711430
//end of class
