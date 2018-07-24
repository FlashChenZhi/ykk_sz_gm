// $Id: RetrievalOrderListBusiness.java,v 1.2 2007/02/07 04:19:22 suresh Exp $

//#CM716050
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalorderlist;
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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderlist.ListRetrievalOrderListBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderListSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM716051
/**
 * Designer : T.Yoshino<BR>
 * Maker : T.Yoshino<BR>
 * <BR>
 * Allow this class of screen to print the Order Picking Work report. <BR>
 * Pass the parameter to the schedule to print the Order Picking Work report. <BR>
 * <BR>
 * <DIR>
 * 1. Process by clicking "Display" button (<CODE>btn_PDisplay_Click<CODE>) <BR>
 * <BR>
 * <DIR>
 * 	 Set the contents entered via screen, and
 *  allow the schedule to search for the data for display based on the parameter and display the result on the popup screen. <BR>
 *  <BR>
 *  [Parameter]  *Mandatory Input<BR>
 *  <DIR>
 * 	 Consignor Code *<BR>
 * 	 Planned Picking Date *<BR>
 * 	 Order No.<BR>
 * 	 Case/Piece division  *<BR>
 * 	 Work status: *<BR>
 * 	</DIR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Print" button (<CODE>btn_PDisplay_Click<CODE>) <BR>
 * <BR>
 * <DIR>
 *  Set the contents entered via screen, and
 *  allow the schedule to search for the databased on the parameter and print it. <BR>
 *  Allow the schedule to return true when printing completed successfully or return false when it failed. <BR>
 *  <BR>
 *  [Parameter]  *Mandatory Input<BR>
 *  <DIR>
 *   Consignor Code *<BR>
 * 	 Planned Picking Date *<BR>
 *   Order No.<BR>
 * 	 Case/Piece division  *<BR>
 * 	 Work status: *<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/25</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:22 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderListBusiness extends RetrievalOrderList implements WMSConstants
{
	//#CM716052
	// Class fields --------------------------------------------------
	
	//#CM716053
	// Maintain the control that invokes the dialog: Print button 
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";

	//#CM716054
	// Class variables -----------------------------------------------

	//#CM716055
	// Class method --------------------------------------------------

	//#CM716056
	// Constructors --------------------------------------------------

	//#CM716057
	// Public methods ------------------------------------------------

	//#CM716058
	/**
	 * Initialize the screen. 
	 * <BR>
	 * <DIR>
	 *  Field item: name [Initial Value] 
	 *  <DIR>
	 *  Consignor Code        [If there is only one Consignor code that corresponds to the condition, show the Initial Display.] <BR>
	 *  Planned Picking Date  	  [None] <BR>
	 *  Order No.       [None] <BR>
	 * 	 Case/Piece division  [All] <BR>
	 * 	 Work status: [All] <BR>
	 *  </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM716059
		// Set the initial value for each input field. 
		txt_ConsignorCode.setText(getConsignorCode());
		txt_RtrivlPlanDate.setText("");
		txt_OrderNo.setText("");
		rdo_CpfAll.setChecked(true);

		//#CM716060
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
	}
	//#CM716061
	/**
	 * Returning from a popup window invokes this method.
	 * Override the page_DlgBack defined for Page. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM716062
		// Obtain the parameter selected in the listbox. 
		//#CM716063
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM716064
		// Planned Picking Date
		Date retrievalplandate = WmsFormatter.toDate(param.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY));
		//#CM716065
		// Order No.
		String orderno = param.getParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY);
		//#CM716066
		// Set a value if not empty. 
		//#CM716067
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM716068
		// Planned Picking Date
		if (!StringUtil.isBlank(retrievalplandate))
		{
			txt_RtrivlPlanDate.setDate(retrievalplandate);
		}
		//#CM716069
		// Order No.
		if (!StringUtil.isBlank(orderno))
		{
			txt_OrderNo.setText(orderno);
		}
		//#CM716070
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
	}

	//#CM716071
	/**
	 * Returning from the Dialog button invokes this method.
	 * Override the page_ConfirmBack defined for Page. 
	 * <BR>
	 * Summary: Executes the selected process if selected Yes in the dialog. <BR>
	 * <BR>
	 * 1. Check which dialog returns it. <BR>
	 * 2. Check for choice of Yes or No clicked in the dialog box. <BR>
	 * 3. Selecting Yes starts the schedule. <BR>
	 * 4. For print dialog, <BR>
	 *   <DIR>
	 *   4-1. Set the input field for the parameter. <BR>
	 *   4-2. Start the printing schedule. <BR>
	 *   4-3. Display the schedule result in the message area. <BR>
	 *	 </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		try
		{
			//#CM716072
			// Set the focus for the Consignor code. 
			setFocus(txt_ConsignorCode);

			//#CM716073
			// Identify which dialog returns it. 
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM716074
			// Clicking on Yes in the dialog box returns true. 
			//#CM716075
			// Clicking on No in the dialog box returns false. 
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM716076
			// Clicking NO button closes the process. 
			//#CM716077
			// Not required to set any error message here. 
			if (!isExecute)
			{
				return;
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			//#CM716078
			// Ensure to turn the flag OFF after closed the operation of the dialog. 
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM716079
		// Start the printing schedule. 
		Connection conn = null;
		try
		{
			//#CM716080
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM716081
			// Set the input value for the parameter. 
			RetrievalSupportParameter[] param = new RetrievalSupportParameter[1];
			param[0] = createParameter();

			//#CM716082
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalOrderListSCH();
			schedule.startSCH(conn, param);
			
			//#CM716083
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
				//#CM716084
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
	
	//#CM716085
	/**
	* Invoke this before invoking each control event.  <BR>
	* <BR>
	* <DIR>
	*  Summary: Displays a dialog.  <BR>
	* </DIR>
	* 
	* @param e ActionEvent Allow this class to store event information. 
	* @throws Exception Report all exceptions. 
	*/
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM716086
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM716087
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM716088
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
			//#CM716089
			// Set the path to the help file. 
			btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()));
		}

	}

	//#CM716090
	// Package methods -----------------------------------------------

	//#CM716091
	// Protected methods ---------------------------------------------
	
	//#CM716092
	/**
	 * Generate a parameter object for which the input value of the input area is set. <BR>
	 * 
	 * @return Parameter object that contains input values in the input area. 
	 */
	protected RetrievalSupportParameter createParameter()
	{
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		
		param.setConsignorCode(txt_ConsignorCode.getText());
		param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		param.setOrderNo(txt_OrderNo.getText());
		//#CM716093
		// Case/Piece division 
		//#CM716094
		// All 
		if (rdo_CpfAll.getChecked())
		{
			param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM716095
		// Case 
		else if (rdo_CpfCase.getChecked())
		{
			param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM716096
		// Piece 
		else if (rdo_CpfPiece.getChecked())
		{
			param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM716097
		// None 
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM716098
		// Work status:
		//#CM716099
		// All 
		if (pul_WorkStatusRetrieval.getSelectedIndex() == 0)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_ALL);
		}
		//#CM716100
		// Standby 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == 1)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		}
		//#CM716101
		// "Started" 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == 2)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_STARTED);
		}
		//#CM716102
		// Working 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == 3)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_WORKING);
		}
		//#CM716103
		// Completed 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == 4)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
		}
		//#CM716104
		// Area Type flag (exclude ASRS) 
		param.setAreaTypeFlag(RetrievalSupportParameter.AREA_TYPE_FLAG_NOASRS);
		return param;
	}

	//#CM716105
	// Private methods -----------------------------------------------
	//#CM716106
	/** 
	 * Allow this method to obtain the Consignor code from the schedule. <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule. <BR>
	 * 
	 * @return Consignor Code
	 * @throws Exception Report all exceptions. 
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM716107
			// Obtain Connection 	
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			RetrievalSupportParameter param = new RetrievalSupportParameter();

			//#CM716108
			// Obtain the Consignor code from the schedule. 
			WmsScheduler schedule = new RetrievalOrderListSCH();
			param = (RetrievalSupportParameter) schedule.initFind(conn, param);

			if (param != null)
			{
				return param.getConsignorCode();
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM716109
				// Close the connection. 
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
		return null;
	}

	//#CM716110
	// Event handler methods -----------------------------------------
	//#CM716111
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716112
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716113
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716114
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

	//#CM716115
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716116
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716117
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716118
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716119
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716120
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716121
	/** 
	 * Clicking on the Search Consignor button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for Consignor using the search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM716122
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM716123
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM716124
		// "Search" flag (Work) 
		param.setParameter(ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM716125
		// for Order 
		param.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM716126
		// Area Type flag (Exclude ASRS before displaying list and exclude also the data that evdenced the Shortage) 
		param.setParameter(ListRetrievalConsignorBusiness.AREA_TYPE_KEY
		        , RetrievalSupportParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM716127
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do", param, "/progress.do");
	}

	//#CM716128
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716129
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716130
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716131
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716132
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716133
	/** 
	 * Clicking on the Search Planned Picking Date button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Planned Picking Date using this search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  Planned Picking Date<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchRtrivlPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM716134
		// Set the search condition in the Search screen of Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM716135
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM716136
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM716137
		// "Search" flag 
		param.setParameter(ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM716138
		// for Order 
		param.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM716139
		// Area Type flag (Exclude ASRS before displaying list and exclude also the data that evdenced the Shortage) 
		param.setParameter(ListRetrievalPlanDateBusiness.AREA_TYPE_KEY
		        , RetrievalSupportParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM716140
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", param, "/progress.do");
	}

	//#CM716141
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_OrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716142
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716143
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716144
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716145
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716146
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716147
	/** 
	 * Clicking on the Search Order No. button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Order No. using this search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  Planned Picking Date<BR>
	 *  Order No.<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchOrderNo_Click(ActionEvent e) throws Exception
	{
		//#CM716148
		// Set the search condition in the Search screen of Order No. 
		ForwardParameters param = new ForwardParameters();
		//#CM716149
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM716150
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM716151
		// Order No.
		param.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, txt_OrderNo.getText());
		//#CM716152
		// "Search" flag 
		param.setParameter(ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM716153
		// for Order 
		param.setParameter(ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM716154
		// Area Type flag (Exclude ASRS before displaying list and exclude also the data that evdenced the Shortage) 
		param.setParameter(ListRetrievalOrdernoBusiness.AREA_TYPE_KEY
		        , RetrievalSupportParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM716155
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do", param, "/progress.do");
	}

	//#CM716156
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716157
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716158
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM716159
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716160
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM716161
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716162
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM716163
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716164
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM716165
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716166
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatusRetrieval_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716167
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatusRetrieval_Change(ActionEvent e) throws Exception
	{
	}

	//#CM716168
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716169
	/** 
	 * Clicking on the Display button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the field item entered in the Input area for the parameter and<BR>
	 * displays the listbox for Order Picking work list in the different screen.<BR>
	 * This method processes as below. <BR>
	 * <BR>
	 * <DIR>
	 * 1. Set the input field for the parameter. <BR>
	 * 2.Displays the listbox for Order Picking Work list. <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		//#CM716170
		// Set the search condition in the screen of Order Picking Work list. 
		ForwardParameters forwardParam = new ForwardParameters();
		//#CM716171
		// Consignor Code
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM716172
		// Planned Picking Date
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM716173
		// Order No.
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, txt_OrderNo.getText());

		//#CM716174
		// Case/Piece division 
		//#CM716175
		// All 
		if (rdo_CpfAll.getChecked())
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM716176
		// Case 
		else if (rdo_CpfCase.getChecked())
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM716177
		// Piece 
		else if (rdo_CpfPiece.getChecked())
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM716178
		// None 
		else if (rdo_CpfAppointOff.getChecked())
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}

		//#CM716179
		// Work status:
		//#CM716180
		// All 
		if (pul_WorkStatusRetrieval.getSelectedIndex() == 0)
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_ALL);
		}
		//#CM716181
		// Standby 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == 1)
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		}
		//#CM716182
		// "Started" 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == 2)
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_STARTED);
		}
		//#CM716183
		// Working 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == 3)
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_WORKING);
		}
		//#CM716184
		// Completed 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == 4)
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
		}

		//#CM716185
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalorderlist/ListRetrievalOrderList.do", forwardParam, "/progress.do");
	}

	//#CM716186
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716187
	/** 
	 * Clicking on the Print button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the input field item in the input area for the parameter and obtains the count of data to be printed, and then 
	 * displays the dialog box to allow to confirm to print it or not. <BR>
	 * <BR>
	 * 1. Check for input and the count of print targets. <BR>
	 * 2-1. Display the dialog box to allow to confirm it if the print target data found. <BR>
	 * <DIR>
	 *   There are xxx (number) target data. Do you print it? <BR>
	 * </DIR>
	 * 2-2. If no print target data, obtain the message and display it. <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		//#CM716188
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
		//#CM716189
		// Check for input. 
		txt_ConsignorCode.validate();
		txt_RtrivlPlanDate.validate();
		txt_OrderNo.validate(false);

		Connection conn = null;

		try
		{
			//#CM716190
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM716191
			// Set the schedule parameter. 
			RetrievalSupportParameter param = createParameter();

			//#CM716192
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalOrderListSCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM716193
				// MSG-W0061={0} data corresponded.<BR> Do you print it? 
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM716194
				// Store the fact that the dialog was displayed via Print button. 
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM716195
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());
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
				//#CM716196
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

	//#CM716197
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716198
	/** 
	 * Clicking on the Clear button invokes this. <BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 *  <DIR>
	 *  For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method. 
	 *  </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM716199
		// Set the initial value for each input field. 
		txt_ConsignorCode.setText(getConsignorCode());
		txt_RtrivlPlanDate.setText("");
		txt_OrderNo.setText("");
		pul_WorkStatusRetrieval.setSelectedIndex(0);
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);

		//#CM716200
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
	}

}
//#CM716201
//end of class
