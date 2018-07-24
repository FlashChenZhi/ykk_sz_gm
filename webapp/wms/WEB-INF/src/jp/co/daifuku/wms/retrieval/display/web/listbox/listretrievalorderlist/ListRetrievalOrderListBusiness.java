// $Id: ListRetrievalOrderListBusiness.java,v 1.2 2007/02/07 04:18:51 suresh Exp $

//#CM710836
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderlist;
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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalOrderListRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM710837
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this listbox class to search for Order Picking Work Report list. <BR>
 * Search for the databased on Consignor Code, Planned Picking Date, Order No., Case/Piece division, and work status entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Consignor Code, Planned Picking Date, Order No., Case/Piece division, and work status entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/22</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:51 $
 * @author  $Author: suresh $
 */
public class ListRetrievalOrderListBusiness extends ListRetrievalOrderList implements WMSConstants
{
	//#CM710838
	// Class fields --------------------------------------------------
	//#CM710839
	/** 
	 * Use this key to pass the Case/Piece division.
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";

	//#CM710840
	/** 
	 * Use this key to pass the work status.
	 */
	public static final String WORKSTATUS_KEY = "WORKSTATUS_KEY";

	//#CM710841
	// Class variables -----------------------------------------------

	//#CM710842
	// Class method --------------------------------------------------

	//#CM710843
	// Constructors --------------------------------------------------

	//#CM710844
	// Public methods ------------------------------------------------

	//#CM710845
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Field item  <BR>
	 *	<DIR>
	 *		1.Customer Code <BR>
	 *		2.Order No. <BR>
	 *		3. Picking Location  <BR>
	 *		4. Division  <BR>
	 *		5.Item Code <BR>
	 *		6.Planned total qty <BR>
	 *		7.Packed Qty per Case <BR>
	 *		8.Planned Work Case Qty <BR>
	 *		9. Result Case Qty <BR>
	 *		10.Case ITF<BR>
	 *		11.Expiry Date<BR>
	 *		12. status <BR>
	 *		13.Customer Name <BR>
	 *		14.Item Name <BR>
	 *		15.Packed qty per bundle <BR>
	 *		16.Planned Work Piece Qty <BR>
	 *		17. Result Piece Qty <BR>
	 *		18.Bundle ITF<BR>	
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM710846
		// Set the screen name. 
		//#CM710847
		// Order Picking Work list 
		lbl_ListName.setText(DisplayText.getText("TLE-W0075"));

		//#CM710848
		// Obtain the parameter. 
		//#CM710849
		// Consignor Code
		String consignorcode = request.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM710850
		// Planned Picking Date
		String retrievalplandate = request.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM710851
		// Order No.
		String orderno = request.getParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY);
		//#CM710852
		// Case/Piece division 
		String casepieceflag = request.getParameter(CASEPIECEFLAG_KEY);
		//#CM710853
		// Work status:
		String workstatus = request.getParameter(WORKSTATUS_KEY);

		//#CM710854
		// Check for mandatory input and forbidden character in the Consignor code. 
		if (!WmsCheckker.consignorCheck(consignorcode, lst_OdrRtrivlWrkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710855
		// Check for mandatory input and prohibited characters in the Picking Plan Info. 
		if (!WmsCheckker.retrievalplandateCheck(retrievalplandate, lst_OdrRtrivlWrkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710856
		// Check the Order No. for prohibited character.
		if (!WmsCheckker.charCheck(orderno, lst_OdrRtrivlWrkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM710857
		// Display the search conditions on the screen. 
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		txt_FDate.setDate(WmsFormatter.toDate(retrievalplandate));

		//#CM710858
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM710859
			// Close the connection. 
			sRet.closeConnection();
			//#CM710860
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM710861
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM710862
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM710863
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM710864
		// Planned Picking Date
		param.setRetrievalPlanDate(retrievalplandate);
		//#CM710865
		// Order No.
		param.setOrderNo(orderno);
		//#CM710866
		// Case/Piece division 
		param.setCasePieceflg(casepieceflag);
		//#CM710867
		// Work status:
		param.setWorkStatus(workstatus);

		//#CM710868
		// Generate the SessionRetrievalOrderStartListRet instance. 
		SessionRetrievalOrderListRet listbox = new SessionRetrievalOrderListRet(conn, param);
		//#CM710869
		// Maintain the list box in the Session 
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM710870
	// Package methods -----------------------------------------------

	//#CM710871
	// Protected methods ---------------------------------------------

	//#CM710872
	// Private methods -----------------------------------------------
	//#CM710873
	/**
	 * Allow this method to change a page.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(SessionRetrievalOrderListRet listbox, String actionName) throws Exception
	{
		//#CM710874
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM710875
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM710876
			// Set the Consignor name as a search condition. 
			lbl_JavaSetCnsgnrNm.setText(rsparam[0].getConsignorName());

			//#CM710877
			// Set a value for Pager. 
			//#CM710878
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM710879
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM710880
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM710881
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM710882
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM710883
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM710884
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM710885
			// Delete all lines. 
			lst_OdrRtrivlWrkList.clearRow();
			
			//#CM710886
			// Customer Name
			String label_customername = DisplayText.getText("LBL-W0036");
			//#CM710887
			// Item Name
			String label_itemname = DisplayText.getText("LBL-W0103");
			//#CM710888
			// Planned Work Case Qty
			String label_plancaseqty = DisplayText.getText("LBL-W0436");
			//#CM710889
			// Planned Work Piece Qty
			String label_planpieceqty = DisplayText.getText("LBL-W0437");
			//#CM710890
			// Result Case Qty 
			String label_resultcaseqty = DisplayText.getText("LBL-W0418");
			//#CM710891
			// Result Piece Qty 
			String label_resultpieceqty = DisplayText.getText("LBL-W0417");
			//#CM710892
			// Case ITF
			String label_caseitf = DisplayText.getText("LBL-W0010");
			//#CM710893
			// Bundle ITF
			String label_bundleitf = DisplayText.getText("LBL-W0006");
			//#CM710894
			// Expiry Date
			String label_usebydate = DisplayText.getText("LBL-W0270");
			//#CM710895
			// status 
			String label_status = DisplayText.getText("LBL-W0229");

			for (int i = 0; i < len; i++)
			{
				//#CM710896
				// Obtain the end line. 
				int count = lst_OdrRtrivlWrkList.getMaxRows();
				//#CM710897
				// Add a line. 
				lst_OdrRtrivlWrkList.addRow();

				//#CM710898
				// Move to the end line. 
				lst_OdrRtrivlWrkList.setCurrentRow(count);
				lst_OdrRtrivlWrkList.setValue(1, rsparam[i].getCustomerCode());
				lst_OdrRtrivlWrkList.setValue(2, rsparam[i].getOrderNo());
				lst_OdrRtrivlWrkList.setValue(3, rsparam[i].getRetrievalLocation());
				lst_OdrRtrivlWrkList.setValue(4, rsparam[i].getCasePieceflgName());
				lst_OdrRtrivlWrkList.setValue(5, rsparam[i].getItemCode());
				lst_OdrRtrivlWrkList.setValue(6, WmsFormatter.getNumFormat(rsparam[i].getTotalPlanQty()));
				lst_OdrRtrivlWrkList.setValue(7, WmsFormatter.getNumFormat(rsparam[i].getEnteringQty()));
				lst_OdrRtrivlWrkList.setValue(8, WmsFormatter.getNumFormat(rsparam[i].getPlanCaseQty()));
				lst_OdrRtrivlWrkList.setValue(9, WmsFormatter.getNumFormat(rsparam[i].getResultCaseQty()));
				lst_OdrRtrivlWrkList.setValue(10, rsparam[i].getITF());
				lst_OdrRtrivlWrkList.setValue(11, rsparam[i].getUseByDate());
				lst_OdrRtrivlWrkList.setValue(12, rsparam[i].getWorkStatusName());

				lst_OdrRtrivlWrkList.setValue(13, rsparam[i].getCustomerName());
				lst_OdrRtrivlWrkList.setValue(14, rsparam[i].getItemName());
				lst_OdrRtrivlWrkList.setValue(15, WmsFormatter.getNumFormat(rsparam[i].getBundleEnteringQty()));
				lst_OdrRtrivlWrkList.setValue(16, WmsFormatter.getNumFormat(rsparam[i].getPlanPieceQty()));
				lst_OdrRtrivlWrkList.setValue(17, WmsFormatter.getNumFormat(rsparam[i].getResultPieceQty()));
				lst_OdrRtrivlWrkList.setValue(18, rsparam[i].getBundleITF());

				//#CM710899
				// Compile the ToolTip data. 
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM710900
				// Customer Name
				toolTip.add(label_customername, rsparam[i].getCustomerName());
				//#CM710901
				// Item Name
				toolTip.add(label_itemname, rsparam[i].getItemName());
				//#CM710902
				// Planned Work Case Qty
				toolTip.add(label_plancaseqty, WmsFormatter.getNumFormat(rsparam[i].getPlanCaseQty()));
				//#CM710903
				// Planned Work Piece Qty
				toolTip.add(label_planpieceqty, WmsFormatter.getNumFormat(rsparam[i].getPlanPieceQty()));
				//#CM710904
				// Result Case Qty 
				toolTip.add(label_resultcaseqty, WmsFormatter.getNumFormat(rsparam[i].getResultCaseQty()));
				//#CM710905
				// Result Piece Qty 
				toolTip.add(label_resultpieceqty, WmsFormatter.getNumFormat(rsparam[i].getResultPieceQty()));
				//#CM710906
				// Case ITF
				toolTip.add(label_caseitf, rsparam[i].getITF());
				//#CM710907
				// Bundle ITF
				toolTip.add(label_bundleitf, rsparam[i].getBundleITF());
				//#CM710908
				// Expiry Date
				toolTip.add(label_usebydate, rsparam[i].getUseByDate());
				//#CM710909
				// status 
				toolTip.add(label_status, rsparam[i].getWorkStatusName());

				//#CM710910
				// Set the TOOL TIP. 	
				lst_OdrRtrivlWrkList.setToolTip(lst_OdrRtrivlWrkList.getCurrentRow(), toolTip.getText());

			}
		}
		else
		{
			//#CM710911
			// Set a value for Pager. 
			//#CM710912
			// Max count 
			pgr_U.setMax(0);
			//#CM710913
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM710914
			// Start Position 
			pgr_U.setIndex(0);
			//#CM710915
			// Max count 
			pgr_D.setMax(0);
			//#CM710916
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM710917
			// Start Position 
			pgr_D.setIndex(0);

			//#CM710918
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM710919
			// Hide the header. 
			lst_OdrRtrivlWrkList.setVisible(false);
			//#CM710920
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM710921
	// Event handler methods -----------------------------------------
	//#CM710922
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710923
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710924
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710925
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710926
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710927
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710928
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710929
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM710930
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM710931
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710932
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

	//#CM710933
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

	//#CM710934
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

	//#CM710935
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

	//#CM710936
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

	//#CM710937
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710938
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlWrkList_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM710939
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlWrkList_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM710940
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlWrkList_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM710941
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlWrkList_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM710942
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlWrkList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710943
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlWrkList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM710944
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OdrRtrivlWrkList_Click(ActionEvent e) throws Exception
	{
	}

	//#CM710945
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
		//#CM710946
		// Maintain the list box in the Session 
		SessionRetrievalOrderListRet listbox = (SessionRetrievalOrderListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM710947
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
		//#CM710948
		// Maintain the list box in the Session 
		SessionRetrievalOrderListRet listbox = (SessionRetrievalOrderListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM710949
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
		//#CM710950
		// Maintain the list box in the Session 
		SessionRetrievalOrderListRet listbox = (SessionRetrievalOrderListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM710951
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
		//#CM710952
		// Maintain the list box in the Session 
		SessionRetrievalOrderListRet listbox = (SessionRetrievalOrderListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM710953
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM710954
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
		//#CM710955
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM710956
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM710957
				// Close the statement. 
				finder.close();
			}
			//#CM710958
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM710959
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM710960
		// Return to the parent screen. 
		parentRedirect(null);
	}
}
//#CM710961
//end of class
