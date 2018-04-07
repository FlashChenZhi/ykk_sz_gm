// $Id: ListRetrievalPlanRegistBusiness.java,v 1.2 2007/02/07 04:18:56 suresh Exp $

//#CM711857
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplanregist;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievallocation.ListRetrievalLocationBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret.SessionRetrievalPlanRegistRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.common.text.StringUtil;

//#CM711858
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this listbox class for Picking Plan list (to add). <BR>
 * Search for the databased on Consignor Code, Planned Picking Date, Item Code, Case/Piece division, Picking Location Customer Code, and Order No. entered via parent screen.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Initial Display (<CODE>page_Load(ActionEvent e)</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Search for the data using Consignor Code, Planned Picking Date, Item Code, Case/Piece division, Picking Location Customer Code, and Order No. entered via parent screen as a key, and display it on the screen.<BR>
 * <BR>
 * </DIR>
 * 2. Button for the selected line (<CODE>lst_ConsignorSearch_Click</CODE> method) <BR>
 * <BR>
 * <DIR>
 * 	Pass the Consignor Code, Consignor Name, Planned Picking Date, Item Code, Item Name, Case/Piece division, Picking Location, Customer Code, Customer Name, Order No., Packed Qty per Case, Packed qty per bundle, Case ITF, and Bundle ITF on the selected line to t<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/25</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:18:56 $
 * @author  $Author: suresh $
 */
public class ListRetrievalPlanRegistBusiness extends ListRetrievalPlanRegist implements WMSConstants
{
	//#CM711859
	// Class fields --------------------------------------------------
	//#CM711860
	/** 
	 * Use this key to pass the Case ITF. 
	 */
	public static final String CASEITF_KEY = "CASEITF_KEY";
	//#CM711861
	/** 
	 * Use this key to pass the bundle ITF. 
	 */
	public static final String BUNDLEITF_KEY = "BUNDLEITF_KEY";
	//#CM711862
	/** 
	 * Use this key to pass the Packed Qty per Case.
	 */
	public static final String CASEETR_KEY = "CASEETR_KEY";
	//#CM711863
	/** 
	 * Use this key to pass the Packed qty per bundle.	
	 */
	public static final String BUNDLEETR_KEY = "BUNDLEETR_KEY";


	//#CM711864
	// Class variables -----------------------------------------------

	//#CM711865
	// Class method --------------------------------------------------

	//#CM711866
	// Constructors --------------------------------------------------

	//#CM711867
	// Public methods ------------------------------------------------

	//#CM711868
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Field item  <BR>
	 *	<DIR>
	 *		1. Select<BR>
	 *		2.Planned Picking Date <BR>
	 *		3.Item Code <BR>
	 *		4. Division  <BR>
	 *		5. Picking Location  <BR>
	 *		6.Customer Code <BR>
	 *		7.Case/Piece division <BR>
	 *		8.Order No. <BR>
	 *		9.Packed Qty per Case <BR>
	 *		10.Case ITF <BR>
	 *		11.Item Name <BR>
	 *		12.Customer Name <BR>
	 *		13.Packed qty per bundle <BR>
	 *		14.Bundle ITF <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM711869
		// Set the screen name. 
		//#CM711870
		// Picking Plan list 
		lbl_ListName.setText(DisplayText.getText("TLE-W0073"));

		//#CM711871
		// Obtain the parameter. 
		//#CM711872
		// Consignor Code
		String consignorcode = request.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM711873
		// Planned Picking Date
		String retrievalplandate = request.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM711874
		// Item Code
		String itemcode = request.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);
		//#CM711875
		// Picking Location 
		String retrievallocation = request.getParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY);
		//#CM711876
		// Customer Code
		String customercode = request.getParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY);
		//#CM711877
		// Case/Piece flag 
		String casepieceflag = request.getParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY);
		//#CM711878
		// Order No.
		String orderno = request.getParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY);

		//#CM711879
		// Check for mandatory input and forbidden character in the Consignor code. 
		if (!WmsCheckker.consignorCheck(consignorcode, lst_ListRtrivlPlanRegist, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711880
		// Check for forbidden character in the Item code. 
		if (!WmsCheckker.charCheck(itemcode, lst_ListRtrivlPlanRegist, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711881
		// Check the Picking Location for prohibited character.
		if (!WmsCheckker.charCheck(retrievallocation, lst_ListRtrivlPlanRegist, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711882
		// Check the Customer Code for prohibited character.
		if (!WmsCheckker.charCheck(customercode, lst_ListRtrivlPlanRegist, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711883
		// Check the Order No. for prohibited character.
		if (!WmsCheckker.charCheck(orderno, lst_ListRtrivlPlanRegist, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM711884
		// Display the search conditions on the screen. 
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		if (RetrievalSupportParameter.CASEPIECE_FLAG_CASE.equals(request.getParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY)))
		{
			lbl_JavaSetCasePieceFlag.setText(DisplayUtil.getPieceCaseValue(SystemDefine.CASEPIECE_FLAG_CASE));
		}
		else if (RetrievalSupportParameter.CASEPIECE_FLAG_PIECE.equals(request.getParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY)))
		{
			lbl_JavaSetCasePieceFlag.setText(DisplayUtil.getPieceCaseValue(SystemDefine.CASEPIECE_FLAG_PIECE));
		}
		else
		{
			lbl_JavaSetCasePieceFlag.setText(DisplayUtil.getPieceCaseValue(SystemDefine.CASEPIECE_FLAG_NOTHING));
		}

		//#CM711885
		// Close the connection of object remained in the Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM711886
			// Close the connection. 
			sRet.closeConnection();
			//#CM711887
			// Delete from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM711888
		// Obtain the connection. 
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM711889
		// Set the parameter. 
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		//#CM711890
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM711891
		// Planned Picking Date
		param.setRetrievalPlanDate(retrievalplandate);
		//#CM711892
		// Item Code
		param.setItemCode(itemcode);
		//#CM711893
		// Case/Piece division
		param.setCasePieceflg(casepieceflag);
		//#CM711894
		// Picking Location 
		param.setRetrievalLocation(retrievallocation);
		//#CM711895
		// Customer Code
		param.setCustomerCode(customercode);
		//#CM711896
		// Order No.
		param.setOrderNo(orderno);

		//#CM711897
		// Generate the SessionRetrievalPlanRegistRet instance. 
		SessionRetrievalPlanRegistRet listbox = new SessionRetrievalPlanRegistRet(conn, param);
		//#CM711898
		// Maintain the list box in the Session 
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM711899
	// Package methods -----------------------------------------------

	//#CM711900
	// Protected methods ---------------------------------------------

	//#CM711901
	// Private methods -----------------------------------------------
	//#CM711902
	/**
	 * Allow this method to change a page.  <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception Report all exceptions. 
	 */
	private void setList(SessionRetrievalPlanRegistRet listbox, String actionName) throws Exception
	{
		//#CM711903
		// Obtain the Local. 
		Locale locale = this.getHttpRequest().getLocale();

		//#CM711904
		// Set the Page info. 
		listbox.setActionName(actionName);

		//#CM711905
		// Obtain the search result. 
		RetrievalSupportParameter[] rsparam = (RetrievalSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (rsparam != null)
			len = rsparam.length;
		if (len > 0)
		{
			//#CM711906
			// Set the Consignor name as a search condition. 
			lbl_JavaSetCnsgnrNm.setText(rsparam[0].getConsignorName());

			//#CM711907
			// Set a value for Pager. 
			//#CM711908
			// Max count 
			pgr_U.setMax(listbox.getLength());
			//#CM711909
			// Count of displayed data per Page 
			pgr_U.setPage(listbox.getCondition());
			//#CM711910
			// Start Position 
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM711911
			// Max count 
			pgr_D.setMax(listbox.getLength());
			//#CM711912
			// Count of displayed data per Page 
			pgr_D.setPage(listbox.getCondition());
			//#CM711913
			// Start Position 
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM711914
			// Hide the message. 
			lbl_InMsg.setVisible(false);

			//#CM711915
			// Delete all lines. 
			lst_ListRtrivlPlanRegist.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM711916
				// Obtain the end line. 
				int count = lst_ListRtrivlPlanRegist.getMaxRows();
				//#CM711917
				// Add a line. 
				lst_ListRtrivlPlanRegist.addRow();

				//#CM711918
				// Move to the end line. 
				lst_ListRtrivlPlanRegist.setCurrentRow(count);
				lst_ListRtrivlPlanRegist.setValue(0, rsparam[i].getCasePieceflg());
				lst_ListRtrivlPlanRegist.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListRtrivlPlanRegist.setValue(2, WmsFormatter.toDispDate(rsparam[i].getRetrievalPlanDate(), locale));
				lst_ListRtrivlPlanRegist.setValue(3, rsparam[i].getItemCode());
				lst_ListRtrivlPlanRegist.setValue(4, rsparam[i].getCaseLocation());
				lst_ListRtrivlPlanRegist.setValue(5, rsparam[i].getCustomerCode());
				lst_ListRtrivlPlanRegist.setValue(6, rsparam[i].getCaseOrderNo());
				lst_ListRtrivlPlanRegist.setValue(7, WmsFormatter.getNumFormat(rsparam[i].getEnteringQty()));
				lst_ListRtrivlPlanRegist.setValue(8, rsparam[i].getITF());

				lst_ListRtrivlPlanRegist.setValue(9, rsparam[i].getItemName());
				lst_ListRtrivlPlanRegist.setValue(10, rsparam[i].getPieceLocation());
				lst_ListRtrivlPlanRegist.setValue(11, rsparam[i].getCustomerName());
				lst_ListRtrivlPlanRegist.setValue(12, rsparam[i].getPieceOrderNo());
				lst_ListRtrivlPlanRegist.setValue(13, WmsFormatter.getNumFormat(rsparam[i].getBundleEnteringQty()));
				lst_ListRtrivlPlanRegist.setValue(14, rsparam[i].getBundleITF());

				//#CM711919
				// Compile the ToolTip data. 
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM711920
				// Item Name
				toolTip.add(DisplayText.getText("LBL-W0103"), rsparam[i].getItemName());
				//#CM711921
				// Customer Name
				toolTip.add(DisplayText.getText("LBL-W0036"), rsparam[i].getCustomerName());
				//#CM711922
				// Case ITF
				toolTip.add(DisplayText.getText("LBL-W0010"), rsparam[i].getITF());
				//#CM711923
				// Bundle ITF
				toolTip.add(DisplayText.getText("LBL-W0006"), rsparam[i].getBundleITF());

				//#CM711924
				// Set the TOOL TIP in the current line. 
				lst_ListRtrivlPlanRegist.setToolTip(lst_ListRtrivlPlanRegist.getCurrentRow(), toolTip.getText());
			}
		}
		else
		{
			//#CM711925
			// Set a value for Pager. 
			//#CM711926
			// Max count 
			pgr_U.setMax(0);
			//#CM711927
			// Count of displayed data per Page 
			pgr_U.setPage(0);
			//#CM711928
			// Start Position 
			pgr_U.setIndex(0);
			//#CM711929
			// Max count 
			pgr_D.setMax(0);
			//#CM711930
			// Count of displayed data per Page 
			pgr_D.setPage(0);
			//#CM711931
			// Start Position 
			pgr_D.setIndex(0);

			//#CM711932
			// Check the count of the search results. 
			String errorMsg = listbox.checkLength();
			//#CM711933
			// Hide the header. 
			lst_ListRtrivlPlanRegist.setVisible(false);
			//#CM711934
			// Display the error message. 
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM711935
	// Event handler methods -----------------------------------------
	//#CM711936
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711937
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711938
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711939
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711940
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711941
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711942
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

	//#CM711943
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

	//#CM711944
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

	//#CM711945
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

	//#CM711946
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

	//#CM711947
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711948
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlPlanRegist_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM711949
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlPlanRegist_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM711950
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlPlanRegist_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM711951
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlPlanRegist_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM711952
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlPlanRegist_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711953
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListRtrivlPlanRegist_Change(ActionEvent e) throws Exception
	{
	}

	//#CM711954
	/** 
	 * Clicking on the Select button of listcell executes its proper process.  <BR>
	 * <BR>
	 *	Pass the Consignor Code, Consignor Name, Planned Picking Date, Item Code, Item Name, Case/Piece division, Picking Location, Customer Code, Customer Name, Order No., Packed Qty per Case, Packed qty per bundle, Case ITF, and Bundle ITF to the parent screen and  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void lst_ListRtrivlPlanRegist_Click(ActionEvent e) throws Exception
	{
		//#CM711955
		// Set the current line. 
		lst_ListRtrivlPlanRegist.setCurrentRow(lst_ListRtrivlPlanRegist.getActiveRow());
		lst_ListRtrivlPlanRegist.getValue(1);

		//#CM711956
		// Set the parameter needed to return to the parent screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM711957
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetCnsgnrCd.getText());
		//#CM711958
		// Consignor Name
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORNAME_KEY, lbl_JavaSetCnsgnrNm.getText());
		//#CM711959
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(lst_ListRtrivlPlanRegist.getValue(2), this.getHttpRequest().getLocale()));
		//#CM711960
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, lst_ListRtrivlPlanRegist.getValue(3));
		//#CM711961
		// Item Name
		param.setParameter(ListRetrievalItemBusiness.ITEMNAME_KEY, lst_ListRtrivlPlanRegist.getValue(9));
		if (SystemDefine.CASEPIECE_FLAG_CASE.equals(lst_ListRtrivlPlanRegist.getValue(0)))
		{
			//#CM711962
			// Picking Location 
			param.setParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY, lst_ListRtrivlPlanRegist.getValue(4));
			//#CM711963
			// Order No.
			param.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, lst_ListRtrivlPlanRegist.getValue(6));
		}
		else if (SystemDefine.CASEPIECE_FLAG_PIECE.equals(lst_ListRtrivlPlanRegist.getValue(0)))
		{
			//#CM711964
			// Picking Location 
			param.setParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY, lst_ListRtrivlPlanRegist.getValue(10));
			//#CM711965
			// Order No.
			param.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, lst_ListRtrivlPlanRegist.getValue(12));
		}
		else
		{
			//#CM711966
			// Picking Location 
			if (!StringUtil.isBlank(lst_ListRtrivlPlanRegist.getValue(4)))
			{
				param.setParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY, lst_ListRtrivlPlanRegist.getValue(4));
			}
			else
			{
				param.setParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY, lst_ListRtrivlPlanRegist.getValue(10));
			}
			//#CM711967
			// Order No.
			if (!StringUtil.isBlank(lst_ListRtrivlPlanRegist.getValue(6)))
			{
				param.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, lst_ListRtrivlPlanRegist.getValue(6));
			}
			else
			{
				param.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, lst_ListRtrivlPlanRegist.getValue(12));
			}
		}
		//#CM711968
		// Customer Code
		param.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, lst_ListRtrivlPlanRegist.getValue(5));
		//#CM711969
		// Customer Name
		param.setParameter(ListRetrievalCustomerBusiness.CUSTOMERNAME_KEY, lst_ListRtrivlPlanRegist.getValue(11));
		//#CM711970
		// Case/Piece division
		param.setParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY, request.getParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY));
		//#CM711971
		// Packed Qty per Case (as it is in comma format) 
		param.setParameter(CASEETR_KEY, lst_ListRtrivlPlanRegist.getValue(7));
		//#CM711972
		// Packed qty per bundle (as it is in comma format) 
		param.setParameter(BUNDLEETR_KEY, lst_ListRtrivlPlanRegist.getValue(13));
		//#CM711973
		// Case ITF
		param.setParameter(CASEITF_KEY, lst_ListRtrivlPlanRegist.getValue(8));
		//#CM711974
		// Bundle ITF
		param.setParameter(BUNDLEITF_KEY, lst_ListRtrivlPlanRegist.getValue(14));
		//#CM711975
		// Shift to the parent screen. 
		parentRedirect(param);
	}

	//#CM711976
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
		//#CM711977
		// Maintain the list box in the Session 
		SessionRetrievalPlanRegistRet listbox = (SessionRetrievalPlanRegistRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM711978
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
		//#CM711979
		// Maintain the list box in the Session 
		SessionRetrievalPlanRegistRet listbox = (SessionRetrievalPlanRegistRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM711980
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
		//#CM711981
		// Maintain the list box in the Session 
		SessionRetrievalPlanRegistRet listbox = (SessionRetrievalPlanRegistRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM711982
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
		//#CM711983
		// Maintain the list box in the Session 
		SessionRetrievalPlanRegistRet listbox = (SessionRetrievalPlanRegistRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM711984
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711985
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
		//#CM711986
		// Maintain the list box in the Session 
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM711987
		// If there is any value in the Session: 
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM711988
				// Close the statement. 
				finder.close();
			}
			//#CM711989
			// Close the connection. 
			sessionret.closeConnection();
		}
		//#CM711990
		// Delete from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM711991
		// Return to the parent screen. 
		parentRedirect(null);
	}
	//#CM711992
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM711993
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM711994
//end of class
