// $Id: ListRetrievalPlanModifyBusiness.java,v 1.2 2007/02/07 04:18:55 suresh Exp $

//#CM711729
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplanmodify;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp
	.co
	.daifuku
	.wms
	.retrieval
	.display
	.web
	.listbox
	.listretrievalconsignor
	.ListRetrievalConsignorBusiness;
import jp
	.co
	.daifuku
	.wms
	.retrieval
	.display
	.web
	.listbox
	.listretrievalcustomer
	.ListRetrievalCustomerBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievallocation.ListRetrievalLocationBusiness;
import jp
	.co
	.daifuku
	.wms
	.retrieval
	.display
	.web
	.listbox
	.listretrievalorderno
	.ListRetrievalOrdernoBusiness;
import jp
	.co
	.daifuku
	.wms
	.retrieval
	.display
	.web
	.listbox
	.listretrievalplandate
	.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalPlanModifyRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM711730
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Listbox class for Picking Plan list (Modify/Delete). <BR>
 * Search for the databased on Consignor Code, Planned Picking Date, Item Code, Case Picking Location, Piece Picking Location, Customer Code, Case Order No., and Piece Order No. entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Consignor Code, Planned Picking Date, Item Code, Case Picking Location, Piece Picking Location, Customer Code, Case Order No., and Piece Order No. entered via parent screen as keys, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * 2. Button for the selected line (<CODE>lst_ConsignorSearch_Click</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Pass the Consignor Code, Planned Picking Date, Item Code, Case Picking Location, Piece Picking Location, Customer Code, Case Order No., Piece Order No. on the selected line to the parent screen and close the listbox.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/25</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:55 $
 * @author  $Author: suresh $
 */
public class ListRetrievalPlanModifyBusiness
	extends ListRetrievalPlanModify
	implements WMSConstants
{
	//#CM711731
	// Class fields --------------------------------------------------

	//#CM711732
	// Class variables -----------------------------------------------

	//#CM711733
	// Class method --------------------------------------------------

	//#CM711734
	// Constructors --------------------------------------------------

	//#CM711735
	// Public methods ------------------------------------------------

	//#CM711736
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Field item  <BR>
	 *	<DIR>
	 *		1. Select<BR>
	 *		2.Planned Picking Date <BR>
	 *		3.Item Code <BR>
	 *		4.Case Picking Location <BR>
	 *		5.Customer Code <BR>
	 *		6.Case Order No. <BR>
	 *		7.Item Name <BR>
	 *		8. Piece Picking Location  <BR>
	 *		9.Customer Name <BR>
	 *		10.Piece Order No. <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM711737
		// Set the screen name. 
		//#CM711738
		// Picking Plan list 
		lbl_ListName.setText(DisplayText.getText("TLE-W0073"));

		//#CM711739
		// Obtain the parameter. 
		//#CM711740
		// Consignor Code
		String consignorcode =
			request.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM711741
		// Planned Picking Date
		String retrievalplandate = request.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM711742
		// Item Code
		String itemcode = request.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);
		//#CM711743
		// Case Picking Location
		String caseretrievallocation = request.getParameter(ListRetrievalLocationBusiness.CASERETRIEVALLOCATION_KEY);
		//#CM711744
		// Piece Picking Location 
		String pieceretrievallocation = request.getParameter(ListRetrievalLocationBusiness.PIECERETRIEVALLOCATION_KEY);
		//#CM711745
		// Customer Code
		String customercode = request.getParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY);
		//#CM711746
		// Case Order No.
		String caseorderno = request.getParameter(ListRetrievalOrdernoBusiness.CASEORDERNO_KEY);
		//#CM711747
		// Piece Order No.
		String pieceorderno = request.getParameter(ListRetrievalOrdernoBusiness.PIECEORDERNO_KEY);

		//#CM711748
		// Check for mandatory input and forbidden character in the Consignor code. 
		if (!WmsCheckker.consignorCheck(consignorcode, lst_ListRtrivlPlanModify, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711749
		// Check for forbidden character in the Item code. 
		if (!WmsCheckker.charCheck(itemcode, lst_ListRtrivlPlanModify, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		
		//#CM711750
		// Check the Case Picking Location for prohibited character.
		if (!WmsCheckker.charCheck(caseretrievallocation, lst_ListRtrivlPlanModify, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711751
		// Check the Piece Picking Location for prohibited character.
		if (!WmsCheckker.charCheck(pieceretrievallocation, lst_ListRtrivlPlanModify, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711752
		// Check the Customer Code for prohibited character.
		if (!WmsCheckker.charCheck(customercode, lst_ListRtrivlPlanModify, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		
		//#CM711753
		// Check the Case Order No. for prohibited character.
		if (!WmsCheckker.charCheck(caseorderno, lst_ListRtrivlPlanModify, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711754
		// Check the Piece Order No. for prohibited character.
		if (!WmsCheckker.charCheck(pieceorderno, lst_ListRtrivlPlanModify, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711755
		// Display the search conditions on the screen. 
		lbl_JavaSetCnsgnrCd.setText(consignorcode);

		//#CM711756
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM711757
			// Close the connection. 
			sRet.closeConnection();
			//#CM711758
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM711759
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM711760
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM711761
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM711762
		// Planned Picking Date
		param.setRetrievalPlanDate(retrievalplandate);
		//#CM711763
		// Item Code
		param.setItemCode(itemcode);
		//#CM711764
		// Case Picking Location
		param.setCaseLocation(caseretrievallocation);
		//#CM711765
		// Piece Picking Location 
		param.setPieceLocation(pieceretrievallocation);
		//#CM711766
		// Customer Code
		param.setCustomerCode(customercode);
		//#CM711767
		// Case Order No.
		param.setCaseOrderNo(caseorderno);
		//#CM711768
		// Piece Order No.
		param.setPieceOrderNo(pieceorderno);

		//#CM711769
		// Generate the SessionRetrievalPlanModifyRet instance. 
		SessionRetrievalPlanModifyRet listbox = new SessionRetrievalPlanModifyRet(conn, param);
		//#CM711770
		// Maintain the list box in the Session 
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM711771
	// Package methods -----------------------------------------------

	//#CM711772
	// Protected methods ---------------------------------------------

	//#CM711773
	// Private methods -----------------------------------------------
	//#CM711774
	/**
	 * Allow this method to change a page.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(SessionRetrievalPlanModifyRet listbox, String actionName) throws Exception
	{
		//#CM711775
		// Obtain the Local. 
		Locale locale = this.getHttpRequest().getLocale();

		//#CM711776
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM711777
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM711778
			// Set the Consignor name as a search condition. 
			lbl_JavaSetCnsgnrNm.setText(rsparam[0].getConsignorName());

			//#CM711779
			// Set a value for Pager. 
			//#CM711780
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM711781
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM711782
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM711783
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM711784
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM711785
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM711786
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM711787
			// Delete all lines. 
			lst_ListRtrivlPlanModify.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM711788
				// Obtain the end line. 
				int count = lst_ListRtrivlPlanModify.getMaxRows();
				//#CM711789
				// Add a line. 
				lst_ListRtrivlPlanModify.addRow();

				//#CM711790
				// Move to the end line. 
				lst_ListRtrivlPlanModify.setCurrentRow(count);
				lst_ListRtrivlPlanModify.setValue(1,Integer.toString(count + listbox.getCurrent()));
				lst_ListRtrivlPlanModify.setValue(2,WmsFormatter.toDispDate(rsparam[i].getRetrievalPlanDate(), locale));
				lst_ListRtrivlPlanModify.setValue(3, rsparam[i].getItemCode());
				lst_ListRtrivlPlanModify.setValue(4, rsparam[i].getCaseLocation());
				lst_ListRtrivlPlanModify.setValue(5, rsparam[i].getCustomerCode());
				lst_ListRtrivlPlanModify.setValue(6, rsparam[i].getCaseOrderNo());
				lst_ListRtrivlPlanModify.setValue(7, rsparam[i].getItemName());
				lst_ListRtrivlPlanModify.setValue(8, rsparam[i].getPieceLocation());
				lst_ListRtrivlPlanModify.setValue(9, rsparam[i].getCustomerName());
				lst_ListRtrivlPlanModify.setValue(10, rsparam[i].getPieceOrderNo());

				//#CM711791
				// Compile the ToolTip data. 
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM711792
				// Item Name
				toolTip.add(DisplayText.getText("LBL-W0103"), rsparam[i].getItemName());
				//#CM711793
				// Customer Name
				toolTip.add(DisplayText.getText("LBL-W0036"), rsparam[i].getCustomerName());

				//#CM711794
				// Set the TOOL TIP in the current line. 
				lst_ListRtrivlPlanModify.setToolTip(
					lst_ListRtrivlPlanModify.getCurrentRow(),
					toolTip.getText());
			}
		}
		else
		{
			//#CM711795
			// Set a value for Pager. 
			//#CM711796
			// Max count 
			pgr_U.setMax(0);
			//#CM711797
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM711798
			// Start Position 
			pgr_U.setIndex(0);
			//#CM711799
			// Max count 
			pgr_D.setMax(0);
			//#CM711800
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM711801
			// Start Position 
			pgr_D.setIndex(0);

			//#CM711802
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM711803
			// Hide the header. 
			lst_ListRtrivlPlanModify.setVisible(false);
			//#CM711804
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM711805
	// Event handler methods -----------------------------------------
	//#CM711806
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711807
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711808
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711809
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711810
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711811
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711812
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

	//#CM711813
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

	//#CM711814
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

	//#CM711815
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

	//#CM711816
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

	//#CM711817
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711818
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlPlanModify_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM711819
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlPlanModify_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM711820
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlPlanModify_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM711821
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlPlanModify_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM711822
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlPlanModify_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711823
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlPlanModify_Change(ActionEvent e) throws Exception
	{
	}

	//#CM711824
	/** 
	 * Clicking on the Select button of listcell executes its proper process.  <BR>
	 * <BR>
	 *	Pass the Consignor Code, Planned Picking Date, Item Code, Case Picking Location, Piece Picking Location, Customer Code, Case Order No., Piece Order No. to the parent screen and close the listbox. <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lst_ListRtrivlPlanModify_Click(ActionEvent e) throws Exception
	{
		//#CM711825
		// Set the current line. 
		lst_ListRtrivlPlanModify.setCurrentRow(lst_ListRtrivlPlanModify.getActiveRow());
		lst_ListRtrivlPlanModify.getValue(1);

		//#CM711826
		// Set the parameter needed to return to the parent screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM711827
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			lbl_JavaSetCnsgnrCd.getText());
		//#CM711828
		// Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(
				lst_ListRtrivlPlanModify.getValue(2),
				this.getHttpRequest().getLocale()));
		//#CM711829
		// Item Code
		param.setParameter(
			ListRetrievalItemBusiness.ITEMCODE_KEY,
			lst_ListRtrivlPlanModify.getValue(3));
		//#CM711830
		// Case Picking Location
		param.setParameter(
			ListRetrievalLocationBusiness.CASERETRIEVALLOCATION_KEY,
			lst_ListRtrivlPlanModify.getValue(4));
		//#CM711831
		// Piece Picking Location 
		param.setParameter(
		ListRetrievalLocationBusiness.PIECERETRIEVALLOCATION_KEY,
			lst_ListRtrivlPlanModify.getValue(8));
		//#CM711832
		// Customer Code
		param.setParameter(
			ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY,
			lst_ListRtrivlPlanModify.getValue(5));
		//#CM711833
		// Case Order No.
		param.setParameter(
			ListRetrievalOrdernoBusiness.CASEORDERNO_KEY,
			lst_ListRtrivlPlanModify.getValue(6));
		//#CM711834
		// Piece Order No.
		param.setParameter(
			ListRetrievalOrdernoBusiness.PIECEORDERNO_KEY,
			lst_ListRtrivlPlanModify.getValue(10));

		//#CM711835
		// Shift to the parent screen. 
		parentRedirect(param);
	}

	//#CM711836
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
		//#CM711837
		// Maintain the list box in the Session 
		SessionRetrievalPlanModifyRet listbox =
			(SessionRetrievalPlanModifyRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM711838
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
		//#CM711839
		// Maintain the list box in the Session 
		SessionRetrievalPlanModifyRet listbox =
			(SessionRetrievalPlanModifyRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM711840
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
		//#CM711841
		// Maintain the list box in the Session 
		SessionRetrievalPlanModifyRet listbox =
			(SessionRetrievalPlanModifyRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM711842
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
		//#CM711843
		// Maintain the list box in the Session 
		SessionRetrievalPlanModifyRet listbox =
			(SessionRetrievalPlanModifyRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM711844
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711845
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
		//#CM711846
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM711847
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM711848
				// Close the statement. 
				finder.close();
			}
			//#CM711849
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM711850
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM711851
		// Return to the parent screen. 
		parentRedirect(null);
	}
}
//#CM711852
//end of class
