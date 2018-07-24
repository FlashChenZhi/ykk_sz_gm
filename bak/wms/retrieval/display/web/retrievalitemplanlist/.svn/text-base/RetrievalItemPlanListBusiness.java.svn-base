// $Id: RetrievalItemPlanListBusiness.java,v 1.2 2007/02/07 04:19:14 suresh Exp $

//#CM714181
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalitemplanlist;
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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitemlist.ListRetrievalItemListBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalItemPlanListSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM714182
/**
 * Designer : T.Yoshino<BR>
 * Maker : T.Yoshino<BR>
 * Allow this class of screen to print the Item Picking Plan report. <BR>
 * Pass the parameter to the schedule to print the Item Picking Plan report. <BR>
 * <BR>
 * <DIR>
 * 1. Process by clicking "Display" button (<CODE>btn_PDisplay_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 * 	 Set the contents entered via screen, and
 *  allow the schedule to search for the data for display based on the parameter and display the result on the popup screen. <BR>
 *  <BR>
 *  [Parameter]  *Mandatory Input<BR>
 *  <DIR>
 * 	 Consignor Code *<BR>
 * 	 Start Planned Picking Date<BR>
 * 	 End Planned Picking Date<BR>
 * 	 Item Code<BR>
 * 	 Work status: *<BR>
 * 	</DIR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Print" button (<CODE>btn_PDisplay_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 *  Set the contents entered via screen, and
 *  allow the schedule to search for the databased on the parameter and print it. <BR>
 *  Allow the schedule to return true when printing completed successfully or return false when it failed. <BR>
 *  <BR>
 *  [Parameter]  *Mandatory Input<BR>
 *  <DIR>
 * 	 Consignor Code *<BR>
 * 	 Start Planned Picking Date<BR>
 * 	 End Planned Picking Date<BR>
 * 	 Item Code<BR>
 * 	 Work status: *<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/25</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:14 $
 * @author  $Author: suresh $
 */
public class RetrievalItemPlanListBusiness extends RetrievalItemPlanList implements WMSConstants
{
	//#CM714183
	// Class fields --------------------------------------------------

	//#CM714184
	// Maintain the control that invokes the dialog: Print button 
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";

	//#CM714185
	// Class variables -----------------------------------------------

	//#CM714186
	// Class method --------------------------------------------------

	//#CM714187
	// Constructors --------------------------------------------------

	//#CM714188
	// Public methods ------------------------------------------------

	//#CM714189
	/**
	 * Initialize the screen. 
	 * <BR>
	 * <DIR>
	 *  Field item: name [Initial Value] 
	 *  <DIR>
	 *  Consignor Code       [If there is only one Consignor code that corresponds to the condition, show the Initial Display.] <BR>
	 *  Start Planned Picking Date   [None] <BR>
	 *  End Planned Picking Date   [None] <BR>
	 * 	Item Code		 [None] <BR>
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
		//#CM714190
		// Set the initial value for each input field. 
		txt_ConsignorCode.setText(getConsignorCode());
		txt_StrtRtrivlPlanDate.setText("");
		txt_EdRtrivlPlanDate.setText("");
		txt_ItemCode.setText("");
		pul_WkStsRtrivlPlan.setSelectedIndex(0);
		//#CM714191
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
	}
	//#CM714192
	/**
	 * Invoke this before invoking each control event.  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM714193
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM714194
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM714195
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}

	}
	
	//#CM714196
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
			//#CM714197
			// Set the focus for the Consignor code. 
			setFocus(txt_ConsignorCode);

			//#CM714198
			// Identify which dialog returns it. 
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM714199
			// Clicking on Yes in the dialog box returns true. 
			//#CM714200
			// Clicking on No in the dialog box returns false. 
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM714201
			// Clicking NO button closes the process. 
			//#CM714202
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
			//#CM714203
			// Ensure to turn the flag OFF after closed the operation of the dialog. 
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM714204
		// Start the printing schedule. 
		Connection conn = null;
		try
		{
			//#CM714205
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM714206
			// Set the input value for the parameter. 
			RetrievalSupportParameter[] param = new RetrievalSupportParameter[1];
			param[0] = createParameter();

			//#CM714207
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalItemPlanListSCH();
			schedule.startSCH(conn, param);
			
			//#CM714208
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
				//#CM714209
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
	
	//#CM714210
	/**
	 * Returning from a popup window invokes this method.
	 * Override the page_DlgBack defined for Page. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{

		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM714211
		// Obtain the parameter selected in the listbox. 
		//#CM714212
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM714213
		// Start Planned Picking Date
		Date startretrievalplandate =
			WmsFormatter.toDate(param.getParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY), this.getHttpRequest().getLocale());

		//#CM714214
		// End Planned Picking Date
		Date endretrievalplandate = WmsFormatter.toDate(param.getParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY), this.getHttpRequest().getLocale());

		//#CM714215
		// Item Code
		String itemcode = param.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);

		//#CM714216
		// Set a value if not empty. 
		//#CM714217
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM714218
		// Start Planned Picking Date
		if (!StringUtil.isBlank(startretrievalplandate))
		{
			txt_StrtRtrivlPlanDate.setDate(startretrievalplandate);
		}
		//#CM714219
		// End Planned Picking Date
		if (!StringUtil.isBlank(endretrievalplandate))
		{
			txt_EdRtrivlPlanDate.setDate(endretrievalplandate);
		}
		//#CM714220
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}

		//#CM714221
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
	}
	//#CM714222
	// Package methods -----------------------------------------------

	//#CM714223
	// Protected methods ---------------------------------------------
	
	//#CM714224
	/**
	 * Generate a parameter object for which the input value of the input area is set. <BR>
	 * 
	 * @return Parameter object that contains input values in the input area. 
	 */
	protected RetrievalSupportParameter createParameter()
	{
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		
		param.setConsignorCode(txt_ConsignorCode.getText());
		param.setFromRetrievalPlanDate(WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));
		param.setToRetrievalPlanDate(WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));
		param.setItemCode(txt_ItemCode.getText());
		
		//#CM714225
		// Work status:
		//#CM714226
		// All 
		if (pul_WkStsRtrivlPlan.getSelectedIndex() == 0)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_ALL);
		}
		//#CM714227
		// Standby 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 1)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		}
		//#CM714228
		// "Started" 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 2)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_STARTED);
		}
		//#CM714229
		// Working 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 3)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_WORKING);
		}
		//#CM714230
		// Partially Completed 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 4)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		}
		//#CM714231
		// Completed 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 5)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
		}
		return param;
	}

	//#CM714232
	// Private methods -----------------------------------------------
	//#CM714233
	/**
	 * Allow this method to obtain the Consignor code from the schedule on the initial display.  <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule.  <BR>
	 * 
	 * @return Consignor Code <BR>
	 *         Return the string of the Consignor code when one corresponding data exists. <BR>
	 *         Return null if 0 or multiple corresponding data exist.  <BR>
	 * 
	 * @throws Exception Report all exceptions. 
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM714234
			// Obtain Connection 	
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			RetrievalSupportParameter param = new RetrievalSupportParameter();

			//#CM714235
			// Obtain the Consignor code from the schedule. 
			WmsScheduler schedule = new RetrievalItemPlanListSCH();
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
				//#CM714236
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
	//#CM714237
	// Event handler methods -----------------------------------------
	//#CM714238
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714239
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714240
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714241
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

	//#CM714242
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714243
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714244
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714245
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714246
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714247
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714248
	/** 
	 * Clicking on the Search Consignor button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for Consignor using the search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code <BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM714249
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM714250
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM714251
		// "Search" flag (Plan) 
		param.setParameter(ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);
		//#CM714252
		// For Item 
		param.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM714253
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do", param, "/progress.do");
	}

	//#CM714254
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrtRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714255
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714256
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtRtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714257
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtRtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714258
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrtRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714259
	/** 
	 * Clicking on the Search Start Planned Picking Date button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Planned Picking Date using this search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  Start Planned Picking Date<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchStrtRtrivlPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM714260
		// Set the search condition in the Search screen of Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM714261
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM714262
		// Start Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));

		//#CM714263
		// Set the Start flag. 
		param.setParameter(ListRetrievalPlanDateBusiness.RANGERETRIEVALPLANDATE_KEY, RetrievalSupportParameter.RANGE_START);

		//#CM714264
		// "Search" flag 
		param.setParameter(ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);

		//#CM714265
		// For Item 
		param.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);

		//#CM714266
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", param, "/progress.do");
	}

	//#CM714267
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714268
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EdRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714269
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714270
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdRtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714271
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdRtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714272
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEdRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714273
	/** 
	 * Clicking on the Search End Planned Picking Date button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Planned Picking Date using this search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  End Planned Picking Date<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchEdRtrivlPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM714274
		// Set the search condition in the Search screen of Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM714275
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM714276
		// End Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));

		//#CM714277
		// Set the Close flag. 
		param.setParameter(ListRetrievalPlanDateBusiness.RANGERETRIEVALPLANDATE_KEY, RetrievalSupportParameter.RANGE_END);

		//#CM714278
		// "Search" flag 
		param.setParameter(ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);

		//#CM714279
		// For Item 
		param.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);

		//#CM714280
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", param, "/progress.do");
	}

	//#CM714281
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714282
	/** 
	
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714283
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714284
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714285
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714286
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714287
	/** 
	 * Clicking on the Search Item Code button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for Item using the search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  Start Planned Picking Date<BR>
	 *  End Planned Picking Date<BR>
	 *  Item Code<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM714288
		// Set the search condition in the Search screen of Item. 
		ForwardParameters param = new ForwardParameters();
		//#CM714289
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM714290
		// Start Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));

		//#CM714291
		// End Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));

		//#CM714292
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());

		//#CM714293
		// "Search" flag 
		param.setParameter(ListRetrievalItemBusiness.SEARCHITEM_KEY, RetrievalSupportParameter.SEARCHFLAG_PLAN);

		//#CM714294
		// For Item 
		param.setParameter(ListRetrievalItemBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);

		//#CM714295
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalitem/ListRetrievalItem.do", param, "/progress.do");
	}

	//#CM714296
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714297
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WkStsRtrivlPlan_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714298
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WkStsRtrivlPlan_Change(ActionEvent e) throws Exception
	{
	}

	//#CM714299
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714300
	/** 
	 * Clicking on the Display button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the field item entered in the Input area for the parameter and displays the listbox for Item Picking Plan list in the different screen.<BR>
	 *  [Parameter]  *Mandatory Input<BR>
	 *  <DIR>
	 * 	 Consignor Code *<BR>
	 * 	 Start Planned Picking Date<BR>
	 * 	 End Planned Picking Date<BR>
	 * 	 Item Code<BR>
	 * 	 Work status: *<BR>
	 * 	</DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		//#CM714301
		// Set the search condition in the screen of Picking Plan list. 
		ForwardParameters forwardParam = new ForwardParameters();
		//#CM714302
		// Consignor Code
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM714303
		// Start Planned Picking Date
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.STARTRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_StrtRtrivlPlanDate.getDate()));

		//#CM714304
		// End Planned Picking Date
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.ENDRETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));

		//#CM714305
		// Item Code
		forwardParam.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());

		//#CM714306
		// Work status:
		//#CM714307
		// All 
		if (pul_WkStsRtrivlPlan.getSelectedIndex() == 0)
		{
			forwardParam.setParameter(ListRetrievalItemListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_ALL);
		}
		//#CM714308
		// Standby 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 1)
		{
			forwardParam.setParameter(ListRetrievalItemListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		}
		//#CM714309
		// "Started" 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 2)
		{
			forwardParam.setParameter(ListRetrievalItemListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_STARTED);
		}
		//#CM714310
		// Working 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 3)
		{
			forwardParam.setParameter(ListRetrievalItemListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_WORKING);
		}
		//#CM714311
		// Partially Completed 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 4)
		{
			forwardParam.setParameter(ListRetrievalItemListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		}
		//#CM714312
		// Completed 
		else if (pul_WkStsRtrivlPlan.getSelectedIndex() == 5)
		{
			forwardParam.setParameter(ListRetrievalItemListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
		}

		//#CM714313
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalitemplanlist/ListRetrievalItemPlanList.do", forwardParam, "/progress.do");
	}

	//#CM714314
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714315
	/** 
	 * Clicking on the Print button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the input field item in the input area for the parameter and obtains the count of data to be printed, and then 
	 * displays the dialog box to allow to confirm to print it or not. <BR>
	 * <BR>
	 * 1. Check for input and the count of print targets. <BR>
	 * 2-1. Display the dialog box to allow to confirm it if the print target data found. <BR>
	 * <DIR>
	 *   There are xxx (number) target data. \nDo you print it? <BR>
	 * </DIR>
	 * 2-2. If no print target data, obtain the message and display it. <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		//#CM714316
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
		//#CM714317
		// Check for input. 
		txt_ConsignorCode.validate();
		txt_StrtRtrivlPlanDate.validate(false);
		txt_EdRtrivlPlanDate.validate(false);
		txt_ItemCode.validate(false);

		//#CM714318
		// The value of the Start Planned Picking Date is larger than the value of the End Planned Picking Date:
		if (!StringUtil.isBlank(txt_StrtRtrivlPlanDate.getDate()) && !StringUtil.isBlank(txt_EdRtrivlPlanDate.getDate()))
		{
			if (txt_StrtRtrivlPlanDate.getDate().compareTo(txt_EdRtrivlPlanDate.getDate()) > 0)
			{
				//#CM714319
				// 6023108=Starting planned picking date must precede the end date. 
				message.setMsgResourceKey("6023108");
				return;
			}
		}

		Connection conn = null;

		try
		{
			//#CM714320
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM714321
			// Set the schedule parameter. 
			RetrievalSupportParameter param = createParameter();

			//#CM714322
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalItemPlanListSCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM714323
				// MSG-W0061={0} data corresponded.<BR> Do you print it? 
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM714324
				// Store the fact that the dialog was displayed via Print button. 
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM714325
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
				//#CM714326
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

	//#CM714327
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714328
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
		//#CM714329
		// Set the initial value for each input field. 
		txt_ConsignorCode.setText(getConsignorCode());
		txt_StrtRtrivlPlanDate.setText("");
		txt_EdRtrivlPlanDate.setText("");
		txt_ItemCode.setText("");
		pul_WkStsRtrivlPlan.setSelectedIndex(0);

		//#CM714330
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
	}

}
//#CM714331
//end of class
