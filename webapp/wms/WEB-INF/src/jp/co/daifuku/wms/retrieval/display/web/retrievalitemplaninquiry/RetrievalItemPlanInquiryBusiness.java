// $Id: RetrievalItemPlanInquiryBusiness.java,v 1.2 2007/02/07 04:19:13 suresh Exp $

//#CM713965
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalitemplaninquiry;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalItemPlanInquirySCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM713966
/**
 * Designer : K.Mukai <BR>
 * Maker : M.Fujii<BR>
 * <BR>
 * Allow this class of screen to inquire the Item Picking Plan. <BR>
 * Set the contents entered via screen for the parameter, and allow the schedule to search data to be displayed based on the parameter. <BR>
 * Receive data to be output to the preset area from the schedule and output it to the preset area. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process by clicking "Display" button (<CODE>btn_View_Click()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Set the contents entered via screen for the parameter, and allow the schedule to search data to be displayed based on the parameter. <BR>
 *   If no corresponding data is found, receive the <CODE>Parameter</CODE> array with number of elements equal to 0. Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 * <BR>
 *   [Parameter]  *Mandatory Input<BR>
 * <BR>
 *     <DIR>
 *     Consignor Code* <BR>
 *     Start Planned Picking Date <BR>
 *     End Planned Picking Date <BR>
 *     Item Code <BR>
 *     Work status: <BR>
 *     </DIR>
 * <BR>
 *   [Returned data]  <BR>
 * <BR>
 *    <DIR>
 *    Consignor Code <BR>
 *    Consignor Name <BR>
 *    Planned Picking Date <BR>
 *    Item Code <BR>
 *    Item Name <BR>
 *    Division  <BR>
 *    Case Picking Location <BR>
 *    Piece Picking Location  <BR>
 *    Packed Qty per Case <BR>
 *    Packed qty per bundle <BR>
 *    Host planned Case qty  <BR>
 *    Host Planned Piece Qty <BR>
 *    Result Case Qty  <BR>
 *    Result Piece Qty  <BR>
 *    Case ITF <BR>
 *    Bundle ITF <BR>
 *    status  <BR>
 *    </DIR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/25</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:13 $
 * @author  $Author: suresh $
 */
public class RetrievalItemPlanInquiryBusiness extends RetrievalItemPlanInquiry implements WMSConstants
{
	//#CM713967
	// Class fields --------------------------------------------------
	//#CM713968
	// Row No. of listcell 
	//#CM713969
	// Planned Picking Date
	private static final int LSTPLANDATE = 1;
	//#CM713970
	// Item Code
	private static final int LSTITEMCODE = 2;
	//#CM713971
	// Division 
	private static final int LSTCASEPIECE = 3;
	//#CM713972
	// Case Picking Location
	private static final int LSTCASELOCATION = 4;
	//#CM713973
	// Packed Qty per Case
	private static final int LSTENTQTY = 5;
	//#CM713974
	// Host planned Case qty 
	private static final int LSTPLANCASEQTY = 6;
	//#CM713975
	// Result Case Qty 
	private static final int LSTCASEQTY = 7;
	//#CM713976
	// Case ITF
	private static final int LSTCASEITF = 8;
	//#CM713977
	// status 
	private static final int LSTSTATUS = 9;
	//#CM713978
	// Item Name
	private static final int LSTITEMNAME = 10;
	//#CM713979
	// Piece Picking Location 
	private static final int LSTPIECELOCATION = 11;
	//#CM713980
	// Packed qty per bundle
	private static final int LSTBUNDLEENTQTY = 12;
	//#CM713981
	// Host Planned Piece Qty
	private static final int LSTPLANPIECEQTY = 13;
	//#CM713982
	// Result Piece Qty 
	private static final int LSTPIECEQTY = 14;
	//#CM713983
	// Bundle ITF
	private static final int LSTBUNDLEITF = 15;

	//#CM713984
	// Class variables -----------------------------------------------

	//#CM713985
	// Class method --------------------------------------------------

	//#CM713986
	// Constructors --------------------------------------------------

	//#CM713987
	// Public methods ------------------------------------------------

	//#CM713988
	/**
	 * show the Initial Display. <BR>
	 * <BR>
	 * <DIR>
	 *  Field item: name [Initial Value] 
	 *  <DIR>
	 *  Consignor Code                [If there is only one Consignor code that corresponds to the condition, show the Initial Display.] <BR>
	 *  Start Picking Date                [None] <BR>
	 *  End Picking Date                [None] <BR>
	 *  Item Code				  [None] <BR>
	 *  Work status:                  [All] <BR>
	 *  </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM713989
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM713990
		// Start Planned Picking Date
		txt_StrtRtrivlPlanDate.setText("");
		//#CM713991
		// End Planned Picking Date
		txt_EdRtrivlPlanDate.setText("");
		//#CM713992
		// Item Code
		txt_ItemCode.setText("");
		//#CM713993
		// Work status:
		pul_WorkStatusStorage.setSelectedIndex(0);
		
		
		//#CM713994
		// Consignor Code (Preset) 
		txt_RConsignorCode.setText("");
		//#CM713995
		// Consignor Name (Preset) 
		txt_RConsignorName.setText("");
		//#CM713996
		// Listcell 
		lst_RetrievalPlanInquiry.clearRow();	
		
	}
	//#CM713997
	/**
	 * Invoke this before invoking each control event.  <BR>
	 * <BR>
	 * Summary: This method executes the following processes.<BR>
	 * <BR>
	 * <DIR>
	 *       1. Set the cursor on the Consignor code.  <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM713998
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM713999
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM714000
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}

		//#CM714001
		// Set the cursor on the Consignor code. 		
		setFocus(txt_ConsignorCode);
	}
	
	//#CM714002
	/**
	 * Returning from a popup window invokes this method.<BR>
	 * Override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE>. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM714003
		// Obtain the parameter selected in the listbox. 
		//#CM714004
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM714005
		// Start Planned Picking Date
		Date startplandate = WmsFormatter.toDate(
			param.getParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY),
				this.getHttpRequest().getLocale());
		//#CM714006
		// End Planned Picking Date
		Date endplandate = WmsFormatter.toDate(
			param.getParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY),
				this.getHttpRequest().getLocale());
		//#CM714007
		// Item Code
		String itemcode = param.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);


		//#CM714008
		// Set a value if not empty. 
		//#CM714009
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM714010
		// Start Planned Picking Date
		if (!StringUtil.isBlank(startplandate))
		{
			txt_StrtRtrivlPlanDate.setDate(startplandate);
		}
		//#CM714011
		// End Planned Picking Date
		if (!StringUtil.isBlank(endplandate))
		{
			txt_EdRtrivlPlanDate.setDate(endplandate);
		}
		//#CM714012
		//  Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}


	}

	//#CM714013
	// Package methods -----------------------------------------------

	//#CM714014
	// Protected methods ---------------------------------------------

	//#CM714015
	// Private methods -----------------------------------------------
	//#CM714016
	/** 
	 * Allow this method to obtain the Consignor code from the schedule. <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule. <BR>
	 * Allow the schedule to return the corresponding Consignor if the data has only one Consignor Code. 
	 * if there are two or more data of Consignor or the count of data is 0, return null. <BR>
	 * 
	 * @throws Exception Report all exceptions. 
	 * @return Consignor Code
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null ;
		
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			RetrievalSupportParameter param = new RetrievalSupportParameter();

			//#CM714017
			// Obtain the Consignor code from the schedule. 
			WmsScheduler schedule = new RetrievalItemPlanInquirySCH();
			param = (RetrievalSupportParameter)schedule.initFind(conn, param); 
			 
			if ( param != null )
			{
				return param.getConsignorCode();
			}
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM714018
				// Close the connection. 
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
		return null;	

	}

	//#CM714019
	// Event handler methods -----------------------------------------
	//#CM714020
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714021
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714022
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714023
	/** 
	 * Clicking on the Menu button invokes this. <BR>
	 * <BR>
	 * Summary: Shifts to the Menu screen.  
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM714024
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714025
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714026
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714027
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714028
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714029
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714030
	/** 
	 * Clicking on the Search Consignor Code button invokes this. 
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for Consignor using the search condition. <BR>
	 * <BR>
	 *   [Parameter]  *Mandatory Input<BR>
	 * <BR>
	 *     <DIR>
	 *     Consignor Code <BR>
	 *     </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM714031
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		
		//#CM714032
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		//#CM714033
		// Screen information 
		param.setParameter(
			ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);
		
		//#CM714034
		// Flag to distinguish Order/Item 
		param.setParameter(
			ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG,
			RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
			
		//#CM714035
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do",
			param,
			"/progress.do");

	}

	//#CM714036
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrtRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714037
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714038
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtRtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714039
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtRtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714040
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrtPlanRtrivl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714041
	/** 
	 * Clicking on the Search Start Planned Picking Date button invokes this. 
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Planned Picking Date using this search condition. <BR>
	 * <BR>
	 *   [Parameter]  *Mandatory Input<BR>
	 * <BR>
	 *     <DIR>
	 *     Consignor Code <BR>
	 *     Start Planned Picking Date <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchStrtPlanRtrivl_Click(ActionEvent e) throws Exception
	{
		//#CM714042
		// Set the search condition in the Search screen of Start Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();
		
		//#CM714043
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM714044
		// Start Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));

		//#CM714045
		// Set the Start flag. 
		param.setParameter(
			ListRetrievalPlanDateBusiness.RANGERETRIEVALPLANDATE_KEY,
			RetrievalSupportParameter.RANGE_START);

		//#CM714046
		// "Search" flag 
		param.setParameter(
			ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);

		//#CM714047
		// Flag to distinguish Order/Item 
		param.setParameter(
			ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG,
			RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		
		//#CM714048
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do",
			param,
			"/progress.do");
	}

	//#CM714049
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714050
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EdRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714051
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714052
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdRtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714053
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdRtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714054
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEdPlanRtrivl_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714055
	/** 
	 * Clicking on the Search End Planned Picking Date button invokes this. 
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Planned Picking Date using this search condition. <BR>
	 * <BR>
	 *   [Parameter]  *Mandatory Input<BR>
	 * <BR>
	 *     <DIR>
	 *     Consignor Code <BR>
	 *     End Planned Picking Date <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchEdPlanRtrivl_Click(ActionEvent e) throws Exception
	{
		//#CM714056
		// Set the search condition in the Search screen of End Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();
		
		//#CM714057
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM714058
		// End Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));

		//#CM714059
		// Set the Close flag. 
		param.setParameter(
			ListRetrievalPlanDateBusiness.RANGERETRIEVALPLANDATE_KEY,
			RetrievalSupportParameter.RANGE_END);

		//#CM714060
		// "Search" flag 
		param.setParameter(
			ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);

		//#CM714061
		// Flag to distinguish Order/Item 
		param.setParameter(
			ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG,
			RetrievalSupportParameter.ITEMORDERFLAG_ITEM);	
			
		//#CM714062
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do",
			param,
			"/progress.do");

	}

	//#CM714063
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714064
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714065
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714066
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714067
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714068
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714069
	/** 
	 * Clicking on the Search Item Code button invokes this. 
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Item Code using this search condition. <BR>
	 * <BR>
	 *   [Parameter]  *Mandatory Input<BR>
	 * <BR>
	 *     <DIR>
	 *     Consignor Code <BR>
	 *     Start Planned Picking Date <BR>
	 *     End Planned Picking Date <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchItemCd_Click(ActionEvent e) throws Exception
	{
		//#CM714070
		// Set the search condition in the Search screen of Item. 
		ForwardParameters param = new ForwardParameters();		
		//#CM714071
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM714072
		// Start Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));

		//#CM714073
		// End Planned Picking Date
		param.setParameter(
			ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));

		//#CM714074
		// Item Code
		param.setParameter(
		    ListRetrievalItemBusiness.ITEMCODE_KEY,
		    txt_ItemCode.getText());
			
		//#CM714075
		// "Search" flag 
		param.setParameter(
			ListRetrievalItemBusiness.SEARCHITEM_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);

		//#CM714076
		// Flag to distinguish Order/Item 
		param.setParameter(
			ListRetrievalItemBusiness.ORDER_ITEM_FLAG,
			RetrievalSupportParameter.ITEMORDERFLAG_ITEM);	
		
		//#CM714077
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalitem/ListRetrievalItem.do",
			param,
			"/progress.do");

	}

	//#CM714078
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lblW_WorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714079
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatusStorage_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714080
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatusStorage_Change(ActionEvent e) throws Exception
	{
	}

	//#CM714081
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714082
	/** 
	 * Clicking on the Display button invokes this. <BR>
	 * <BR>
	 * Summary: Displays using field items input in the Input area as conditions and display data in the Preset area.<BR>
	 * <BR>
	 * <DIR>
	 *     Execute the following processes.  <BR>
	 *     1. Check for input in the input field item in the input area.  <BR>
	 *       <DIR>
	 *      (Mandatory input, number of characters, and attribute of character)  <BR>
	 *       (Start Planned Picking Date <= End Planned Picking Date)  <BR>
	 *       </DIR>
	 *     2. Start the schedule.  <BR>
	 *       <DIR>
	 *       [Parameter]  *Mandatory Input<BR>
	 *       <BR>
	 *         <DIR>
	 *         Consignor Code* <BR>
	 *         Start Planned Picking Date <BR>
	 *         End Planned Picking Date <BR>
	 *         Item Code <BR>
	 * 		   Work status:* <BR>
	 *         </DIR>
	 *       </DIR>
	 *     3. Display the schedule result in the preset area.  <BR>
	 *     4. Set the cursor on the Consignor code.  <BR>
	 * </DIR>
	 * <BR>
	 * <BR>
	 * Row No. list of listcell  <BR>
	 * <DIR>
	 *     1:Planned Picking Date <BR>
	 *     2:Item Code <BR>
	 * 	   3: Division  <BR>
	 *     4:Case Picking Location <BR>
	 *     5:Packed Qty per Case <BR>
	 *     6: Host planned Case qty  <BR>
	 *     7: Result Case Qty  <BR>
	 * 	   8:Case ITF <BR>
	 *     9: status  <BR>
	 * 	  10:Item Name <BR>
	 *    11: Piece Picking Location  <BR>
	 *    12:Packed qty per bundle <BR>
	 *    13:Host Planned Piece Qty <BR>
	 *    14: Result Piece Qty  <BR>
	 *    15:Bundle ITF <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{

		Connection conn = null;
		
		try
		{
			//#CM714083
			// Move the cursor to the Consignor code. 
			setFocus(txt_ConsignorCode);
			
			//#CM714084
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM714085
			// Clear the Preset area. 
			lst_RetrievalPlanInquiry.clearRow();
			txt_RConsignorCode.setText("");
			txt_RConsignorName.setText("");
			
			//#CM714086
			// Check for input. (format, mandatory, prohibited characters) 
			//#CM714087
			// Consignor Code
			txt_ConsignorCode.validate();
			//#CM714088
			// Start Planned Picking Date
			txt_StrtRtrivlPlanDate.validate(false);
			//#CM714089
			// End Planned Picking Date
			txt_EdRtrivlPlanDate.validate(false);
			//#CM714090
			// Item Code
			txt_ItemCode.validate(false);
			//#CM714091
			// Work status:
			pul_WorkStatusStorage.validate(false);
			//#CM714092
			// Check the value of Planned Picking Date for precedence. 
			if (txt_StrtRtrivlPlanDate.getDate() != null && txt_EdRtrivlPlanDate.getDate() != null)
			{

				if (txt_StrtRtrivlPlanDate.getDate().after(txt_EdRtrivlPlanDate.getDate()))
				{
					//#CM714093
					// Display the error message and close it. 
					//#CM714094
					// 6023108=Starting planned picking date must precede the end date. 
					message.setMsgResourceKey("6023108");
					return ;
				}
			}
			
			//#CM714095
			// Set the schedule parameter. 
			RetrievalSupportParameter param = new RetrievalSupportParameter();
			
			//#CM714096
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM714097
			// Start Planned Picking Date
			param.setFromRetrievalPlanDate(WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));
			//#CM714098
			// End Planned Picking Date
			param.setToRetrievalPlanDate(WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));
			//#CM714099
			// Item Code
			param.setItemCode(txt_ItemCode.getText());

			//#CM714100
			// Work status:
			//#CM714101
			// All 
			if (pul_WorkStatusStorage.getSelectedIndex() == 0)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_ALL);
			}
			//#CM714102
			// Standby 
			else if (pul_WorkStatusStorage.getSelectedIndex() == 1)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
			}
			//#CM714103
			// "Started" 
			else if (pul_WorkStatusStorage.getSelectedIndex() == 2)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_STARTED);
			}
			//#CM714104
			// Working 
			else if (pul_WorkStatusStorage.getSelectedIndex() == 3)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_WORKING);
			}
			//#CM714105
			// Partially Completed 
			else if (pul_WorkStatusStorage.getSelectedIndex() == 4)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
			}
			//#CM714106
			// Completed 
			else if (pul_WorkStatusStorage.getSelectedIndex() == 5)
			{
				param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
			}

			//#CM714107
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalItemPlanInquirySCH();
			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[])schedule.query(conn, param);
			
			//#CM714108
			// Close the process when some error occurred or no display data was found. 
			if( viewParam == null || viewParam.length == 0 )
			{
				//#CM714109
				// Close the connection. 
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			
			//#CM714110
			// Display the data if obtained the display data when the schedule normally completed. 
			//#CM714111
			// Consignor Code (Preset) 
			txt_RConsignorCode.setText(viewParam[0].getConsignorCode());
			//#CM714112
			// Consignor Name (Preset) 
			txt_RConsignorName.setText(viewParam[0].getConsignorName());

			//#CM714113
			// Item Name
			String label_itemname = DisplayText.getText("LBL-W0103");
			//#CM714114
			// Result Case Qty 
			String label_resultcaseqty = DisplayText.getText("LBL-W0418");
			//#CM714115
			// Result Piece Qty 
			String label_resultpieceqty = DisplayText.getText("LBL-W0417");
			//#CM714116
			// Case ITF
			String label_caseitf = DisplayText.getText("LBL-W0338");
			//#CM714117
			// Bundle ITF
			String label_bundleitf = DisplayText.getText("LBL-W0337");
			//#CM714118
			// status 
			String label_status = DisplayText.getText("LBL-W0229");
			
			//#CM714119
			// Set a value in the listcell. 
			for(int i = 0; i < viewParam.length; i++)
			{

				//#CM714120
				// Add a line. 
				lst_RetrievalPlanInquiry.addRow();
				lst_RetrievalPlanInquiry.setCurrentRow(i + 1);
	
				int j = 1;
				
				//#CM714121
				// Set the search result in each cell. 
				//#CM714122
				// (1st row) 
				//#CM714123
				// Planned Picking Date
				lst_RetrievalPlanInquiry.setValue(j++, WmsFormatter.toDispDate(viewParam[i].getRetrievalPlanDate(),
																				this.getHttpRequest().getLocale()));
				//#CM714124
				// Item Code
				lst_RetrievalPlanInquiry.setValue(LSTITEMCODE, viewParam[i].getItemCode());
				//#CM714125
				// Division 
				lst_RetrievalPlanInquiry.setValue(LSTCASEPIECE, viewParam[i].getCasePieceflgName());
				//#CM714126
				// Case Picking Location
				lst_RetrievalPlanInquiry.setValue(LSTCASELOCATION, viewParam[i].getCaseLocation());
				//#CM714127
				// Packed Qty per Case
				lst_RetrievalPlanInquiry.setValue(LSTENTQTY, Formatter.getNumFormat(viewParam[i].getEnteringQty()));
				//#CM714128
				// Host planned Case qty 
				lst_RetrievalPlanInquiry.setValue(LSTPLANCASEQTY, Formatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				//#CM714129
				// Result Case Qty 
				lst_RetrievalPlanInquiry.setValue(LSTCASEQTY, Formatter.getNumFormat(viewParam[i].getResultCaseQty()));
				//#CM714130
				// Case ITF
				lst_RetrievalPlanInquiry.setValue(LSTCASEITF, viewParam[i].getITF());
				//#CM714131
				// status 
				lst_RetrievalPlanInquiry.setValue(LSTSTATUS, viewParam[i].getWorkStatusName());


				//#CM714132
				// (2nd row) 
				//#CM714133
				// Item Name
				lst_RetrievalPlanInquiry.setValue(LSTITEMNAME, viewParam[i].getItemName());
				//#CM714134
				// Piece Picking Location 
				lst_RetrievalPlanInquiry.setValue(LSTPIECELOCATION, viewParam[i].getPieceLocation());
				//#CM714135
				// Packed qty per bundle
				lst_RetrievalPlanInquiry.setValue(LSTBUNDLEENTQTY, Formatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
				//#CM714136
				// Host Planned Piece Qty
				lst_RetrievalPlanInquiry.setValue(LSTPLANPIECEQTY, Formatter.getNumFormat(viewParam[i].getPlanPieceQty()));
				//#CM714137
				// Result Piece Qty 
				lst_RetrievalPlanInquiry.setValue(LSTPIECEQTY, Formatter.getNumFormat(viewParam[i].getResultPieceQty()));
				//#CM714138
				// Bundle ITF
				lst_RetrievalPlanInquiry.setValue(LSTBUNDLEITF, viewParam[i].getBundleITF());
				
				
				//#CM714139
				// Set the tool tip (balloon field item). 
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM714140
				// Item Name
				toolTip.add(label_itemname, viewParam[i].getItemName());
				//#CM714141
				// Result Case Qty 
				toolTip.add(label_resultcaseqty, Formatter.getNumFormat(viewParam[i].getResultCaseQty()));
				//#CM714142
				// Result Piece Qty 
				toolTip.add(label_resultpieceqty, Formatter.getNumFormat(viewParam[i].getResultPieceQty()));
				//#CM714143
				// Case ITF
				toolTip.add(label_caseitf, viewParam[i].getITF());
				//#CM714144
				// Bundle ITF
				toolTip.add(label_bundleitf, viewParam[i].getBundleITF());
				//#CM714145
				// status 
				toolTip.add(label_status, viewParam[i].getWorkStatusName());

				lst_RetrievalPlanInquiry.setToolTip(i+1, toolTip.getText());

			}
			
			//#CM714146
			// Set the message. 
			message.setMsgResourceKey(schedule.getMessage());
			
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
			
		}
		finally
		{
			try
			{
				//#CM714147
				// Close the connection. 
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
			
		}
		
	}

	//#CM714148
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714149
	/**
	 * Clicking on the Clear button invokes this.  <BR>
	 * <BR>
	 * Summary: Clears the input area.  <BR>
	 * <BR>
	 * <DIR>
	 * 1. Initialize the screen.  <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		
		//#CM714150
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM714151
		// Start Planned Picking Date
		txt_StrtRtrivlPlanDate.setText("");
		//#CM714152
		// End Planned Picking Date
		txt_EdRtrivlPlanDate.setText("");
		//#CM714153
		// Item Code
		txt_ItemCode.setText("");
		//#CM714154
		// Work status:
		pul_WorkStatusStorage.setSelectedIndex(0);
		
	}

	//#CM714155
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714156
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714157
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714158
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714159
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_R_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714160
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_R_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714161
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_R_ConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714162
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_R_ConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714163
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_R_ConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714164
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalPlanInquiry_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714165
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalPlanInquiry_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714166
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalPlanInquiry_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714167
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalPlanInquiry_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM714168
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalPlanInquiry_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714169
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalPlanInquiry_Change(ActionEvent e) throws Exception
	{
	}

	//#CM714170
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalPlanInquiry_Click(ActionEvent e) throws Exception
	{
	}



	//#CM714171
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714172
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714173
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714174
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714175
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}


}
//#CM714176
//end of class
