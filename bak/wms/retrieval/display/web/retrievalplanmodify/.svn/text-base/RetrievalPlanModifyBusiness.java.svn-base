// $Id: RetrievalPlanModifyBusiness.java,v 1.2 2007/02/07 04:19:32 suresh Exp $

//#CM718863
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalplanmodify;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalcustomer.ListRetrievalCustomerBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievallocation.ListRetrievalLocationBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalPlanModifySCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM718864
/**
 * Designer : K.Mukai <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * Allow this class to modify/delete Picking plan data (to set Basic information). <BR>
 * Set the content entered via screen in the ViewState, and shift to the screen to modify/delete the Plan data, based on the parameter.  <BR>
 * <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1. Process by clicking "Next" button (<CODE>btn_Next_Click</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *    Set the input field item of the Input area in the ViewState, and shift to the screen to modify/delete the Plan data, based on the parameter.  <BR>
 * <BR>
 *    [ViewStateParameter]  *Mandatory Input <BR>
 * <BR>
 *    <DIR>
 * 		Worker Code* <BR>
 * 		 Password * <BR>
 * 		Consignor Code* <BR>
 * 		Planned Picking Date* <BR>
 * 		Item Code* <BR>
 * 		Case Order No. <BR>
 * 		Piece Order No. <BR>
 * 		Customer Code <BR>
 * 		Case Picking Location <BR>
 * 		 Piece Picking Location  <BR>
 *    </DIR>
 * <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/27</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:32 $
 * @author  $Author: suresh $
 */
public class RetrievalPlanModifyBusiness extends RetrievalPlanModify implements WMSConstants
{
	//#CM718865
	// Class fields --------------------------------------------------
	//#CM718866
	/**
	 * Use this key for ViewState to pass Worker Code. 
	 */
	public static final String WORKERCODE_KEY = "WORKERCODE_KEY";

	//#CM718867
	/**
	 * Use this key for ViewState to pass Password. 
	 */
	public static final String PASSWORD_KEY = "PASSWORD_KEY";

	//#CM718868
	/**
	 * Use this key for ViewState to pass Consignor Code. 
	 */
	public static final String CONSIGNORCODE_KEY = "CONSIGNORCODE_KEY";

	//#CM718869
	/**
	 * Use this key for ViewState to pass Planned Picking Date. 
	 */
	public static final String RETRIEVALPLANDATE_KEY = "RETRIEVALPLANDATE_KEY";

	//#CM718870
	/**
	 * Use this key for ViewState to pass Item Code. 
	 */
	public static final String ITEMCODE_KEY = "ITEMCODE_KEY";

	//#CM718871
	/**
	 * Use this key for ViewState to pass Case Order No. 
	 */
	public static final String CASEORDERNO_KEY = "CASEORDERNO_KEY";

	//#CM718872
	/**
	 * Use this key for ViewState to pass Piece Order No. 
	 */
	public static final String PIECEORDERNO_KEY = "PIECEORDERNO_KEY";

	//#CM718873
	/**
	 * Use this key for ViewState to pass Customer Code. 
	 */
	public static final String CUSTOMERCODE_KEY = "CUSTOMERCODE_KEY";

	//#CM718874
	/**
	 * Use this key for ViewState to pass Case Picking Location. 
	 */
	public static final String CASERETRIEVALLOCATION_KEY = "CASERETRIEVALLOCATION_KEY";

	//#CM718875
	/**
	 * Use this key for ViewState to pass Piece Picking Location. 
	 */
	public static final String PIECERETRIEVALLOCATION_KEY = "PIECERETRIEVALLOCATION_KEY";

	//#CM718876
	/**
	 * Use this key for ViewState to pass Message. 
	 */
	public static final String MESSAGE_KEY = "MESSAGE_KEY";

	//#CM718877
	/**
	 * Use this key for ViewState to pass a Title. 
	 */
	public static final String TITLE_KEY = "TITLE_KEY";

	//#CM718878
	// Class variables -----------------------------------------------

	//#CM718879
	// Class method --------------------------------------------------

	//#CM718880
	// Constructors --------------------------------------------------

	//#CM718881
	// Public methods ------------------------------------------------

	//#CM718882
	/**
	 * Initialize the screen.  <BR>
	 * <BR>
	 * Summary: shows the Initial Display.  <BR>
	 * <BR>
	 * <DIR>
	 *	1. Set the initial value of the cursor to Worker Code.  <BR>
	 * <BR>
	 *	Field item: name [Initial Value]  <BR>
	 *	<DIR>
	 *		Worker Code [None]  <BR>
	 *		 Password [None]  <BR>
	 *		Consignor Code[If 1 (one) Consignor data is in status "Not Worked" in the Picking Plan Info, show the initial display of the Consignor Code.] <BR>
	 *		Planned Picking Date [None]  <BR>
	 *		Item Code [None]  <BR>
	 *		 Case Order No.[None]  <BR>
	 *		 Piece Order No.[None]  <BR>
	 *		Customer Code [None]  <BR>
	 *		Case Picking Location [None]  <BR>
	 *		 Piece Picking Location[None]  <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		
		//#CM718883
		// Title 
		if (!StringUtil.isBlank(this.viewState.getString(TITLE_KEY)))
		{
			lbl_SettingName.setResourceKey(this.getViewState().getString(TITLE_KEY));
		}
		//#CM718884
		// Prefix a tab for setting Basic information. 
		tab_BscDtlMdfyDlt.setSelectedIndex(1);

		//#CM718885
		// Show the initial display. 
		setFirstDisp();

		//#CM718886
		// Worker Code
		String workercode = this.getViewState().getString(WORKERCODE_KEY);
		//#CM718887
		// Password 
		String password = this.getViewState().getString(PASSWORD_KEY);
		//#CM718888
		// Consignor Code
		String consignorcode = this.getViewState().getString(CONSIGNORCODE_KEY);
		//#CM718889
		// Planned Picking Date
		Date retrievalplandate = WmsFormatter.toDate(this.getViewState().getString(RETRIEVALPLANDATE_KEY));
		//#CM718890
		// Item Code
		String itemcode = this.getViewState().getString(ITEMCODE_KEY);
		//#CM718891
		// Case Order No.
		String caseorderno = this.getViewState().getString(CASEORDERNO_KEY);
		//#CM718892
		// Piece Order No.
		String pieceorderno = this.getViewState().getString(PIECEORDERNO_KEY);
		//#CM718893
		// Customer Code
		String customercode = this.getViewState().getString(CUSTOMERCODE_KEY);
		//#CM718894
		// Case Picking Location
		String caseretrievallocation = this.getViewState().getString(CASERETRIEVALLOCATION_KEY);
		//#CM718895
		// Piece Picking Location 
		String pieceretrievallocation = this.getViewState().getString(PIECERETRIEVALLOCATION_KEY);
		//#CM718896
		// Message 
		String messagekey = this.getViewState().getString(MESSAGE_KEY);

		//#CM718897
		// Clicking on Return button sets the value that has been set in ViewState if any. 
		//#CM718898
		// Worker Code
		if (!StringUtil.isBlank(workercode))
		{
			txt_WorkerCode.setText(workercode);
		}
		//#CM718899
		// Password 
		if (!StringUtil.isBlank(password))
		{
			txt_Password.setText(password);
		}
		//#CM718900
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM718901
		// Planned Picking Date
		if (!StringUtil.isBlank(retrievalplandate))
		{
			txt_RtrivlPlanDate.setDate(retrievalplandate);
		}
		//#CM718902
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM718903
		// Case Order No.
		if (!StringUtil.isBlank(caseorderno))
		{
			txt_CaseOrderNo.setText(caseorderno);
		}
		//#CM718904
		// Piece Order No.
		if (!StringUtil.isBlank(pieceorderno))
		{
			txt_PieceOrderNo.setText(pieceorderno);
		}
		//#CM718905
		// Customer Code
		if (!StringUtil.isBlank(customercode))
		{
			txt_CustomerCode.setText(customercode);
		}
		//#CM718906
		// Case Picking Location
		if (!StringUtil.isBlank(caseretrievallocation))
		{
			txt_CaseRtrivlLct.setText(caseretrievallocation);
		}
		//#CM718907
		// Piece Picking Location 
		if (!StringUtil.isBlank(pieceretrievallocation))
		{
			txt_PieceRtrivlLct.setText(pieceretrievallocation);
		}
		//#CM718908
		// Error Message 
		if (!StringUtil.isBlank(messagekey))
		{
			//#CM718909
			// Display the Error Message. 
			message.setMsgResourceKey(messagekey);
			//#CM718910
			// Display the message and then clear the message of ViewState. 
			this.getViewState().setString(MESSAGE_KEY, "");
		}
	}

	//#CM718911
	/**
	 * Invoke this before invoking each control event. <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM718912
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM718913
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM718914
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
	}

	//#CM718915
	/**
	 * Returning from a popup window invokes this method.
	 * Override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE>. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM718916
		// Obtain the parameter selected in the listbox. 
		//#CM718917
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM718918
		// Planned Picking Date
		Date retrievalplandate = WmsFormatter.toDate(param.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY));
		//#CM718919
		// Item Code
		String itemcode = param.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);
		//#CM718920
		// Case Order No.
		String caseorderno = param.getParameter(ListRetrievalOrdernoBusiness.CASEORDERNO_KEY);
		//#CM718921
		// Piece Order No.
		String pieceorderno = param.getParameter(ListRetrievalOrdernoBusiness.PIECEORDERNO_KEY);
		//#CM718922
		// Customer Code
		String customercode = param.getParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY);
		//#CM718923
		// Case Picking Location
		String caseretrievallocation = param.getParameter(ListRetrievalLocationBusiness.CASERETRIEVALLOCATION_KEY);
		//#CM718924
		// Piece Picking Location 
		String pieceretrievallocation = param.getParameter(ListRetrievalLocationBusiness.PIECERETRIEVALLOCATION_KEY);

		//#CM718925
		// Set a value if not empty. 
		//#CM718926
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM718927
		// Planned Picking Date
		if (!StringUtil.isBlank(retrievalplandate))
		{
			txt_RtrivlPlanDate.setDate(retrievalplandate);
		}
		//#CM718928
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM718929
		// Case Order No.
		if (!StringUtil.isBlank(caseorderno))
		{
			txt_CaseOrderNo.setText(caseorderno);
		}
		//#CM718930
		// Piece Order No.
		if (!StringUtil.isBlank(pieceorderno))
		{
			txt_PieceOrderNo.setText(pieceorderno);
		}
		//#CM718931
		// Customer Code
		if (!StringUtil.isBlank(customercode))
		{
			txt_CustomerCode.setText(customercode);
		}
		//#CM718932
		// Case Picking Location
		if (!StringUtil.isBlank(caseretrievallocation))
		{
			txt_CaseRtrivlLct.setText(caseretrievallocation);
		}
		//#CM718933
		// Piece Picking Location 
		if (!StringUtil.isBlank(pieceretrievallocation))
		{
			txt_PieceRtrivlLct.setText(pieceretrievallocation);
		}

		//#CM718934
		// Set focus for Worker Code. 
		setFocus(txt_WorkerCode);
	}

	//#CM718935
	// Package methods -----------------------------------------------

	//#CM718936
	// Protected methods ---------------------------------------------

	//#CM718937
	// Private methods -----------------------------------------------
	//#CM718938
	/**
	 * Invoke this method to display/clear the initial display. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM718939
			// Set the cursor on the Worker code. 
			setFocus(txt_WorkerCode);
			
			//#CM718940
			// Consignor Code
			txt_ConsignorCode.setText("");
			//#CM718941
			// Planned Picking Date
			txt_RtrivlPlanDate.setText("");
			//#CM718942
			// Item Code
			txt_ItemCode.setText("");
			//#CM718943
			// Case Order No.
			txt_CaseOrderNo.setText("");
			//#CM718944
			// Piece Order No.
			txt_PieceOrderNo.setText("");
			//#CM718945
			// Customer Code
			txt_CustomerCode.setText("");
			//#CM718946
			// Case Picking Location
			txt_CaseRtrivlLct.setText("");
			//#CM718947
			// Piece Picking Location 
			txt_PieceRtrivlLct.setText("");
			
			//#CM718948
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalPlanModifySCH();

			RetrievalSupportParameter param = (RetrievalSupportParameter) schedule.initFind(conn, null);
			//#CM718949
			// For data with only one Consignor code, display the initial display. 
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM718950
				// Close the connection. 
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM718951
	// Event handler methods -----------------------------------------
	//#CM718952
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718953
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718954
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718955
	/**
	 * Clicking on the Menu button invokes this. <BR>
	 * <BR>
	 * Summary: Shifts to the Menu screen. <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM718956
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718957
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718958
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718959
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718960
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718961
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718962
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718963
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718964
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718965
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718966
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718967
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718968
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718969
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718970
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718971
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718972
	/** 
	 * Clicking on the Search Consignor Code button invokes this.  <BR>
	 * <BR>
	 * Summary: Sets the search condition for a parameter and displays the Consignor list listbox using the search condition.  <BR>
	 * <BR>
	 * <DIR>
	 *	 [Parameter]  *Mandatory Input <BR>
	 *	<DIR>
	 *		Consignor Code <BR>
	 *	</DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM718973
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM718974
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM718975
		// "Search" flag 
		param.setParameter(ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM718976
		// Work Status (Standby) 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		//#CM718977
		// Set the work status. 
		param.setParameter(ListRetrievalConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		//#CM718978
		// Both Order/Item 
		param.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ALL);
		//#CM718979
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do", param, "/progress.do");
	}

	//#CM718980
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718981
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718982
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718983
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718984
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchRetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718985
	/** 
	 * Clicking on the Search Planned Picking Date button invokes this.  <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Planned Picking Date list using this search condition.  <BR>
	 * <BR>
	 * <DIR>
	 *	 [Parameter]  *Mandatory Input <BR>
	 *	<DIR>
	 *		Consignor Code <BR>
	 *		Planned Picking Date <BR>
	 *	</DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchRetrievalPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM718986
		// Set the search condition in the Search screen of Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM718987
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM718988
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM718989
		// "Search" flag 
		param.setParameter(ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM718990
		// Work Status (Standby) 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		//#CM718991
		// Set the work status. 
		param.setParameter(ListRetrievalPlanDateBusiness.WORKSTATUSRETRIEVALPLANDATE_KEY, search);
		//#CM718992
		// Both Order/Item 
		param.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ALL);

		//#CM718993
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", param, "/progress.do");
	}

	//#CM718994
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718995
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM718996
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM718997
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM718998
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM718999
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719000
	/** 
	 * Clicking on the Search Item Code button invokes this.  <BR>
	 * <BR>
	 * Summary: Sets the search condition for a parameter and displays the item list listbox using the search condition.  <BR>
	 * <BR>
	 * <DIR>
	 *	 [Parameter]  *Mandatory Input <BR>
	 *	<DIR>
	 *		Consignor Code <BR>
	 *		Planned Picking Date <BR>
	 *		Item Code <BR>
	 *	</DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM719001
		// Set the search condition in the Search screen of Item. 
		ForwardParameters param = new ForwardParameters();
		//#CM719002
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM719003
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM719004
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM719005
		// "Search" flag 
		param.setParameter(ListRetrievalItemBusiness.SEARCHITEM_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM719006
		// Work Status (Standby) 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		//#CM719007
		// Set the work status. 
		param.setParameter(ListRetrievalItemBusiness.WORKSTATUSITEM_KEY, search);
		//#CM719008
		// Both Order/Item 
		param.setParameter(ListRetrievalItemBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ALL);

		//#CM719009
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalitem/ListRetrievalItem.do", param, "/progress.do");
	}

	//#CM719010
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PRtrivlPlanSrch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719011
	/** 
	 * Clicking on the Search Picking Plan button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Picking plan data list using this search condition. <BR>
	 * <BR>
	 * <DIR>
	 *	 [Parameter]  *Mandatory Input<BR>
	 *	<DIR>
	 *		Consignor Code* <BR>
	 *		Planned Picking Date <BR>
	 *		Item Code <BR>
	 *		Case Picking Location <BR>
	 *		 Piece Picking Location  <BR>
	 *		Customer Code <BR>
	 *		Case Order No. <BR>
	 *		Piece Order No. <BR>
	 *	</DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PRtrivlPlanSrch_Click(ActionEvent e) throws Exception
	{
		//#CM719012
		// Set the search condition in the Search screen of Picking plan data. 
		ForwardParameters param = new ForwardParameters();
		//#CM719013
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM719014
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM719015
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM719016
		// Case Picking Location
		param.setParameter(ListRetrievalLocationBusiness.CASERETRIEVALLOCATION_KEY, txt_CaseRtrivlLct.getText());
		//#CM719017
		// Piece Picking Location 
		param.setParameter(ListRetrievalLocationBusiness.PIECERETRIEVALLOCATION_KEY, txt_PieceRtrivlLct.getText());
		//#CM719018
		// Customer Code
		param.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		//#CM719019
		// Case Order No.
		param.setParameter(ListRetrievalOrdernoBusiness.CASEORDERNO_KEY, txt_CaseOrderNo.getText());
		//#CM719020
		// Piece Order No.
		param.setParameter(ListRetrievalOrdernoBusiness.PIECEORDERNO_KEY, txt_PieceOrderNo.getText());
		//#CM719021
		// "Search" flag 
		//#CM719022
		// Do not require to set. Search for Standby data only.

		//#CM719023
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalplanmodify/ListRetrievalPlanModify.do", param, "/progress.do");
	}

	//#CM719024
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseOrder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719025
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719026
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM719027
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM719028
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseOrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM719029
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCaseOrder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719030
	/** 
	 * Clicking on the Search Case Order No. button invokes this.  <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Order No. list using this search condition.  <BR>
	 * <BR>
	 * <DIR>
	 *	 [Parameter]  *Mandatory Input <BR>
	 *	<DIR>
	 *		Consignor Code <BR>
	 *		Planned Picking Date <BR>
	 *		Item Code <BR>
	 * 		Case Picking Location <BR>
	 *      Customer Code<BR>
	 *		Case Order No. <BR>
	 *	</DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchCaseOrder_Click(ActionEvent e) throws Exception
	{
		//#CM719031
		// Set the search condition in the Search screen of Order No. 
		ForwardParameters param = new ForwardParameters();
		//#CM719032
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM719033
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM719034
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM719035
		// Case Picking Location
		param.setParameter(ListRetrievalLocationBusiness.CASERETRIEVALLOCATION_KEY, txt_CaseRtrivlLct.getText());
		//#CM719036
		// Customer Code
		param.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		//#CM719037
		// Case Order No.
		param.setParameter(ListRetrievalOrdernoBusiness.CASEORDERNO_KEY, txt_CaseOrderNo.getText());
		//#CM719038
		// Case/Piece flag 
		param.setParameter(ListRetrievalOrdernoBusiness.CASE_PIECE_KEY, RetrievalSupportParameter.LISTBOX_CASE);
		//#CM719039
		// Work Status (Standby) 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		//#CM719040
		// Set the work status. 
		param.setParameter(ListRetrievalOrdernoBusiness.WORKSTATUSORDERNO_KEY, search);
		//#CM719041
		// "Search" flag 
		param.setParameter(ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM719042
		// Both Order/Item 
		param.setParameter(ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ALL);

		//#CM719043
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do", param, "/progress.do");
	}

	//#CM719044
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PieceOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719045
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719046
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM719047
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM719048
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceOrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM719049
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchPieceOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719050
	/** 
	 * Clicking on the Search Piece Order No. button invokes this.  <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Order No. list using this search condition.  <BR>
	 * <BR>
	 * <DIR>
	 *	 [Parameter]  *Mandatory Input <BR>
	 *	<DIR>
	 *		Consignor Code <BR>
	 *		Planned Picking Date <BR>
	 *		Item Code <BR>
	 *		 Piece Picking Location  <BR>
	 *      Customer Code<BR>
	 *		Piece Order No. <BR>
	 *	</DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchPieceOrderNo_Click(ActionEvent e) throws Exception
	{
		//#CM719051
		// Set the search condition in the Search screen of Order No. 
		ForwardParameters param = new ForwardParameters();
		//#CM719052
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM719053
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM719054
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM719055
		// Piece Picking Location 
		param.setParameter(ListRetrievalLocationBusiness.PIECERETRIEVALLOCATION_KEY, txt_PieceRtrivlLct.getText());
		//#CM719056
		// Customer Code
		param.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		//#CM719057
		// Piece Order No.
		param.setParameter(ListRetrievalOrdernoBusiness.PIECEORDERNO_KEY, txt_PieceOrderNo.getText());
		//#CM719058
		// Case/Piece flag 
		param.setParameter(ListRetrievalOrdernoBusiness.CASE_PIECE_KEY, RetrievalSupportParameter.LISTBOX_PIECE);
		//#CM719059
		// Work Status (Standby) 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		//#CM719060
		// Set the work status. 
		param.setParameter(ListRetrievalOrdernoBusiness.WORKSTATUSORDERNO_KEY, search);
		//#CM719061
		// "Search" flag 
		param.setParameter(ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM719062
		// Both Order/Item 
		param.setParameter(ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ALL);

		//#CM719063
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do", param, "/progress.do");
	}

	//#CM719064
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719065
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719066
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM719067
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM719068
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM719069
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719070
	/** 
	 * Clicking on Search Customer Code button invokes this.  <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Customer list using this search condition.  <BR>
	 * <BR>
	 * <DIR>
	 *	 [Parameter]  *Mandatory Input <BR>
	 *	<DIR>
	 *		Consignor Code <BR>
	 *		Planned Picking Date <BR>
	 *		Item Code <BR>
	 *		Case Picking Location <BR>
	 *		 Piece Picking Location  <BR>
	 *		Customer Code <BR>
	 *	</DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchCustomerCode_Click(ActionEvent e) throws Exception
	{
		//#CM719071
		// Set the search condition in the Search screen of Customer. 
		ForwardParameters param = new ForwardParameters();
		//#CM719072
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM719073
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM719074
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM719075
		// Case Picking Location
		param.setParameter(ListRetrievalLocationBusiness.CASERETRIEVALLOCATION_KEY, txt_CaseRtrivlLct.getText());
		//#CM719076
		// Piece Picking Location 
		param.setParameter(ListRetrievalLocationBusiness.PIECERETRIEVALLOCATION_KEY, txt_PieceRtrivlLct.getText());
		//#CM719077
		// Customer Code
		param.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		//#CM719078
		// "Search" flag 
		param.setParameter(ListRetrievalCustomerBusiness.SEARCHCUSTOMER_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM719079
		// Work Status (Standby) 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		//#CM719080
		// Set the work status. 
		param.setParameter(ListRetrievalCustomerBusiness.WORKSTATUSCUSTOMER_KEY, search);
		//#CM719081
		// Both Order/Item 
		param.setParameter(ListRetrievalCustomerBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ALL);

		//#CM719082
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalcustomer/ListRetrievalCustomer.do", param, "/progress.do");
	}

	//#CM719083
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719084
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719085
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLct_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM719086
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLct_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM719087
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseRtrivlLct_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM719088
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCaseRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719089
	/** 
	 * Clicking on the Search Case Picking Location button invokes this.  <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Picking Location list using this search condition.  <BR>
	 * <BR>
	 * <DIR>
	 *	 [Parameter]  *Mandatory Input <BR>
	 *	<DIR>
	 *		Consignor Code <BR>
	 *		Planned Picking Date <BR>
	 *		Item Code <BR>
	 *		Customer Code <BR>
	 *		Case Picking Location <BR>
	 *	</DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchCaseRtrivlLct_Click(ActionEvent e) throws Exception
	{
		//#CM719090
		// Set the search condition in the Search screen of Picking Location. 
		ForwardParameters param = new ForwardParameters();
		//#CM719091
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM719092
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM719093
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM719094
		// Case Picking Location
		param.setParameter(ListRetrievalLocationBusiness.CASERETRIEVALLOCATION_KEY, txt_CaseRtrivlLct.getText());
		//#CM719095
		// Case Picking Location/Piece Picking Location flag 
		param.setParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY, RetrievalSupportParameter.LISTBOX_CASE);
		//#CM719096
		// Work Status (Standby) 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		//#CM719097
		// Set the work status. 
		param.setParameter(ListRetrievalLocationBusiness.WORKSTATUSLOCATION_KEY, search);

		//#CM719098
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievallocation/ListRetrievalLocation.do", param, "/progress.do");
	}

	//#CM719099
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PiceRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719100
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719101
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLct_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM719102
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLct_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM719103
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceRtrivlLct_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM719104
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchPiceRtrivlLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719105
	/** 
	 * Clicking on the Search Piece Picking Location button invokes this.  <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Picking Location list using this search condition.  <BR>
	 * <BR>
	 * <DIR>
	 *	 [Parameter]  *Mandatory Input <BR>
	 *	<DIR>
	 *		Consignor Code <BR>
	 *		Planned Picking Date <BR>
	 *		Item Code <BR>
	 *		Customer Code <BR>
	 *		 Piece Picking Location  <BR>
	 *	</DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchPiceRtrivlLct_Click(ActionEvent e) throws Exception
	{
		//#CM719106
		// Set the search condition in the Search screen of Picking Location. 
		ForwardParameters param = new ForwardParameters();
		//#CM719107
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM719108
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM719109
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM719110
		// Piece Picking Location 
		param.setParameter(ListRetrievalLocationBusiness.PIECERETRIEVALLOCATION_KEY, txt_PieceRtrivlLct.getText());
		//#CM719111
		// Case Picking Location/Piece Picking Location flag 
		param.setParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY, RetrievalSupportParameter.LISTBOX_PIECE);
		//#CM719112
		// Work Status (Standby) 
		String[] search = new String[1];
		search[0] = new String(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		//#CM719113
		// Set the work status. 
		param.setParameter(ListRetrievalLocationBusiness.WORKSTATUSLOCATION_KEY, search);

		//#CM719114
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievallocation/ListRetrievalLocation.do", param, "/progress.do");
	}

	//#CM719115
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Next_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719116
	/**
	 * Clicking on Next button invokes this.  <BR>
	 * <BR>
	 * Summary: Shifts to the Modify/Delete Plan Data (Detail) screen using the field items that are input in the Input area as conditions.  <BR>
	 * <BR>
	 * <DIR>
	 *	1. Check for mandatory input in the items (count) of the input area. (mandatory input, Number of characters, character properties). <BR>
	 *	2. Start the schedule. <BR>
	 *	<DIR>
	 *		 [Parameter]  *Mandatory Input<BR>
	 *		<DIR>
	 *			Worker Code* <BR>
	 *			 Password * <BR>
	 *			Consignor Code* <BR>
	 *			Planned Picking Date* <BR>
	 *			Item Code <BR>
	 *			Case Order No. <BR>
	 *			Piece Order No. <BR>
	 *			Customer Code <BR>
	 *			Case Picking Location <BR>
	 *			 Piece Picking Location  <BR>
	 *		</DIR>
	 *	</DIR>
	 *	3. Shift to the Next screen (Detailed Modify/Delete Plan) using the input field item as a condition, if the schedule result is true.<BR>
	 *	<DIR>
	 *		 [ViewStateParameter]  *Mandatory Input<BR>
	 *		<DIR>
	 *			Worker Code* <BR>
	 *			 Password * <BR>
	 *			Consignor Code* <BR>
	 *			Planned Picking Date* <BR>
	 *			Item Code <BR>
	 *			Case Order No. <BR>
	 *			Piece Order No. <BR>
	 *			Customer Code <BR>
	 *			Case Picking Location <BR>
	 *			 Piece Picking Location  <BR>
	 *		</DIR>
	 *	</DIR>
	 *	 Output the message obtained from the schedule to the screen, if false. <BR>
	 *	4. Maintain the contents of the input area. <BR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_Next_Click(ActionEvent e) throws Exception
	{
		//#CM719117
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);

		//#CM719118
		// Check for mandatory input. 
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		txt_RtrivlPlanDate.validate();
		txt_ItemCode.validate();

		//#CM719119
		// Pattern matching characters 
		txt_CaseRtrivlLct.validate(false);
		txt_PieceRtrivlLct.validate(false);
		txt_CustomerCode.validate(false);						
		txt_CaseOrderNo.validate(false);
		txt_PieceOrderNo.validate(false);

		Connection conn = null;

		try
		{
			//#CM719120
			// Set for the schedule parameter: 
			RetrievalSupportParameter param = new RetrievalSupportParameter();
			//#CM719121
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM719122
			// Password 
			param.setPassword(txt_Password.getText());
			//#CM719123
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM719124
			// Planned Picking Date
			param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
			//#CM719125
			// Item Code
			param.setItemCode(txt_ItemCode.getText());
			//#CM719126
			// Case Order No.
			param.setCaseOrderNo(txt_CaseOrderNo.getText());
			//#CM719127
			// Piece Order No.
			param.setPieceOrderNo(txt_PieceOrderNo.getText());
			//#CM719128
			// Customer Code
			param.setCustomerCode(txt_CustomerCode.getText());
			//#CM719129
			// Case Picking Location
			param.setCaseLocation(txt_CaseRtrivlLct.getText());
			//#CM719130
			// Piece Picking Location 
			param.setPieceLocation(txt_PieceRtrivlLct.getText());

			//#CM719131
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalPlanModifySCH();

			if (schedule.nextCheck(conn, param))
			{
				//#CM719132
				// If the result of Check is true, set a value in ViewState and shift to the next screen. 
				//#CM719133
				// Title 
				this.getViewState().setString(TITLE_KEY, lbl_SettingName.getResourceKey());
				//#CM719134
				// Worker Code
				this.getViewState().setString(WORKERCODE_KEY, txt_WorkerCode.getText());
				//#CM719135
				// Password 
				this.getViewState().setString(PASSWORD_KEY, txt_Password.getText());
				//#CM719136
				// Consignor Code
				this.getViewState().setString(CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
				//#CM719137
				// Planned Picking Date
				this.getViewState().setString(RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
				//#CM719138
				// Item Code
				this.getViewState().setString(ITEMCODE_KEY, txt_ItemCode.getText());
				//#CM719139
				// Case Order No.
				this.getViewState().setString(CASEORDERNO_KEY, txt_CaseOrderNo.getText());
				//#CM719140
				// Piece Order No.
				this.getViewState().setString(PIECEORDERNO_KEY, txt_PieceOrderNo.getText());
				//#CM719141
				// Customer Code
				this.getViewState().setString(CUSTOMERCODE_KEY, txt_CustomerCode.getText());
				//#CM719142
				// Case Picking Location
				this.getViewState().setString(CASERETRIEVALLOCATION_KEY, txt_CaseRtrivlLct.getText());
				//#CM719143
				// Piece Picking Location 
				this.getViewState().setString(PIECERETRIEVALLOCATION_KEY, txt_PieceRtrivlLct.getText());

				//#CM719144
				// Basic information setting screen to Add Detail Info screen 
				forward("/retrieval/retrievalplanmodify/RetrievalPlanModify2.do");
			}
			else
			{
				//#CM719145
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM719146
				// Close the connection. 
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM719147
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719148
	/**
	 * Clicking on the Clear button invokes this. <BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 * <DIR>
	 *    1. Return the field items in the input area to the initial value. Disable to clear Worker Code and Password. <BR>
	 *    <DIR>
	 *  	- For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method. <BR>
	 *    </DIR>
	 *    2. Set the cursor on the Worker code. <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM719149
		// Clear the screen. 
		setFirstDisp();
	}

}
//#CM719150
//end of class
