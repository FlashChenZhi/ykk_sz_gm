// $Id: RetrievalItemListBusiness.java,v 1.2 2007/02/07 04:19:12 suresh Exp $

//#CM713809
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalitemlist;
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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderlist.ListRetrievalOrderListBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalItemListSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM713810
/**
 * Designer : T.Yoshino<BR>
 * Maker : T.Yoshino<BR>
 * <BR>
 * Allow this class of screen to print the Item Picking Work report. <BR>
 * Pass the parameter to the schedule to print the Item Picking Work report. <BR>
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
 * 	 Planned Picking Date *<BR>
 * 	 Item Code<BR>
 * 	 Case/Piece division  *<BR>
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
 *   Consignor Code *<BR>
 * 	 Planned Picking Date *<BR>
 *   Item Code<BR>
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
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:12 $
 * @author  $Author: suresh $
 */
public class RetrievalItemListBusiness extends RetrievalItemList implements WMSConstants
{
	//#CM713811
	// Class fields --------------------------------------------------
	

	//#CM713812
	// Maintain the control that invokes the dialog: Print button 
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";
	

	//#CM713813
	// Class variables -----------------------------------------------

	//#CM713814
	// Class method --------------------------------------------------

	//#CM713815
	// Constructors --------------------------------------------------

	//#CM713816
	// Public methods ------------------------------------------------

	//#CM713817
	/**
	 * Initialize the screen. 
	 * <BR>
	 * <DIR>
	 *  Field item: name [Initial Value] 
	 *  <DIR>
	 *  Consignor Code      [If there is only one Consignor code that corresponds to the condition, show the Initial Display.] <BR>
	 *  Planned Picking Date  	 [None] <BR>
	 *  Item Code      [None] <BR>
	 * 	 Case/Piece division  [All] <BR>
	 * 	 Work status: [All] <BR>
	 *  </DIR>
	 * </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM713818
		// Set the initial value for each input field. 
		txt_ConsignorCode.setText(getConsignorCode());
		txt_RtrivlPlanDate.setText("");
		txt_ItemCode.setText("");
		rdo_CpfAll.setChecked(true);

		//#CM713819
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
	}
	
	//#CM713820
	/**
	 * Returning from a popup window invokes this method.<BR>
	 * Override the page_DlgBack defined for Page. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM713821
		// Obtain the parameter selected in the listbox. 
		//#CM713822
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM713823
		// Planned Picking Date
		Date startretrievalplandate = WmsFormatter.toDate(param.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY));
		//#CM713824
		// Item Code
		String itemcode = param.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);
		//#CM713825
		// Set a value if not empty. 
		//#CM713826
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM713827
		// Planned Picking Date
		if (!StringUtil.isBlank(startretrievalplandate))
		{
			txt_RtrivlPlanDate.setDate(startretrievalplandate);
		}
		//#CM713828
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM713829
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
	}
	
	//#CM713830
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
			//#CM713831
			// Set the focus for the Consignor code. 
			setFocus(txt_ConsignorCode);

			//#CM713832
			// Identify which dialog returns it. 
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM713833
			// Clicking on Yes in the dialog box returns true. 
			//#CM713834
			// Clicking on No in the dialog box returns false. 
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM713835
			// Clicking NO button closes the process. 
			//#CM713836
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
			//#CM713837
			// Ensure to turn the flag OFF after closed the operation of the dialog. 
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM713838
		// Start the printing schedule. 
		Connection conn = null;
		try
		{
			//#CM713839
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM713840
			// Set the input value for the parameter. 
			RetrievalSupportParameter[] param = new RetrievalSupportParameter[1];
			param[0] = createParameter();

			//#CM713841
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalItemListSCH();
			schedule.startSCH(conn, param);
			
			//#CM713842
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
				//#CM713843
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


	//#CM713844
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
			//#CM713845
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM713846
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM713847
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
			//#CM713848
			// Set the path to the help file. 
			btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()));
		}

	}

	//#CM713849
	// Package methods -----------------------------------------------

	//#CM713850
	// Protected methods ---------------------------------------------
	
	//#CM713851
	/**
	 * Generate a parameter object for which the input value of the input area is set. <BR>
	 * 
	 * @return Parameter object that contains input values in the input area. 
	 */
	private RetrievalSupportParameter createParameter()
	{
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		
		param.setConsignorCode(txt_ConsignorCode.getText());
		param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		param.setItemCode(txt_ItemCode.getText());
		//#CM713852
		// Case/Piece division 
		//#CM713853
		// All 
		if (rdo_CpfAll.getChecked())
		{
			param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM713854
		// Case 
		else if (rdo_CpfCase.getChecked())
		{
			param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		
		}
		//#CM713855
		// Piece 
		else if (rdo_CpfPiece.getChecked())
		{
			param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		
		}
		//#CM713856
		// None 
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM713857
		// Work status:
		//#CM713858
		// All 
		if (pul_WorkStatusRetrieval.getSelectedIndex() == 0)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_ALL);
		}
		//#CM713859
		// Standby 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == 1)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		}
		//#CM713860
		// "Started" 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == 2)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_STARTED);
		}
		//#CM713861
		// Working 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == 3)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_WORKING);
		}
		//#CM713862
		// Completed 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == 4)
		{
			param.setWorkStatus(RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
		}
		//#CM713863
		// Area Type flag (exclude ASRS) 
		param.setAreaTypeFlag(RetrievalSupportParameter.AREA_TYPE_FLAG_NOASRS);
		return param;
	}

	//#CM713864
	// Private methods -----------------------------------------------
	//#CM713865
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
			//#CM713866
			// Obtain Connection 	
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			RetrievalSupportParameter param = new RetrievalSupportParameter();

			//#CM713867
			// Obtain the Consignor code from the schedule. 
			WmsScheduler schedule = new RetrievalItemListSCH();
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
				//#CM713868
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

	//#CM713869
	// Event handler methods -----------------------------------------
	//#CM713870
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713871
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713872
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713873
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

	//#CM713874
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713875
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713876
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713877
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713878
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM713879
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713880
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
		//#CM713881
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM713882
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM713883
		// "Search" flag (Work) 
		param.setParameter(ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM713884
		// For Item 
		param.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM713885
		// Area Type flag (Exclude ASRS before displaying list) 
		param.setParameter(ListRetrievalConsignorBusiness.AREA_TYPE_KEY
		        , RetrievalSupportParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM713886
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do", param, "/progress.do");
	}

	//#CM713887
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713888
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713889
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713890
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713891
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713892
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
		//#CM713893
		// Set the search condition in the Search screen of Planned Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM713894
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM713895
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));

		//#CM713896
		// "Search" flag 
		param.setParameter(ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM713897
		// For Item 
		param.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM713898
		// Area Type flag (Exclude ASRS before displaying list) 
		param.setParameter(ListRetrievalPlanDateBusiness.AREA_TYPE_KEY
		        , RetrievalSupportParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM713899
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", param, "/progress.do");
	}

	//#CM713900
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713901
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713902
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713903
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713904
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM713905
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713906
	/** 
	 * Clicking on the Search Item button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for Item using the search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  Planned Picking Date<BR>
	 *  Item Code<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM713907
		// Set the search condition in the Search screen of Item. 
		ForwardParameters param = new ForwardParameters();
		//#CM713908
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM713909
		// Planned Picking Date
		param.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));

		//#CM713910
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());

		//#CM713911
		// "Search" flag 
		param.setParameter(ListRetrievalItemBusiness.SEARCHITEM_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		
		//#CM713912
		// For Item 
		param.setParameter(ListRetrievalItemBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM713913
		// Area Type flag (Exclude ASRS before displaying list) 
		param.setParameter(ListRetrievalItemBusiness.AREA_TYPE_KEY
		        , RetrievalSupportParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM713914
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalitem/ListRetrievalItem.do", param, "/progress.do");
	}

	//#CM713915
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713916
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713917
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM713918
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713919
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM713920
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713921
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM713922
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713923
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM713924
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713925
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatusRetrieval_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713926
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatusRetrieval_Change(ActionEvent e) throws Exception
	{
	}

	//#CM713927
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713928
	/** 
	 * Clicking on the Display button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the field item entered in the Input area for the parameter and<BR>
	 * Displays the listbox for Order Picking work list in the different screen.<BR>
	 * [Parameter]  *Mandatory Input<BR>
	 * <DIR>
	 * Consignor Code*<BR>
	 * Planned Picking Date<BR>
	 * Item Code<BR>
	 * Case/Piece division <BR>
	 * Work status:<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		//#CM713929
		// Set the search condition in the screen of Order Picking Work list. 
		ForwardParameters forwardParam = new ForwardParameters();
		//#CM713930
		// Consignor Code
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM713931
		// Planned Picking Date
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));

		//#CM713932
		// Order No.
		forwardParam.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());

		//#CM713933
		// Case/Piece division 
		//#CM713934
		// All 
		if (rdo_CpfAll.getChecked())
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM713935
		// Case 
		else if (rdo_CpfCase.getChecked())
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM713936
		// Piece 
		else if (rdo_CpfPiece.getChecked())
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM713937
		// None 
		else if (rdo_CpfAppointOff.getChecked())
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}

		//#CM713938
		// Work status:
		//#CM713939
		// All 
		if (pul_WorkStatusRetrieval.getSelectedIndex() == 0)
		{
			forwardParam.setParameter(ListRetrievalItemListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_ALL);
		}
		//#CM713940
		// Standby 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == 1)
		{
			forwardParam.setParameter(ListRetrievalItemListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_UNSTARTED);
		}
		//#CM713941
		// "Started" 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == 2)
		{
			forwardParam.setParameter(ListRetrievalItemListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_STARTED);
		}
		//#CM713942
		// Working 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == 3)
		{
			forwardParam.setParameter(ListRetrievalItemListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_WORKING);
		}
		//#CM713943
		// Completed 
		else if (pul_WorkStatusRetrieval.getSelectedIndex() == 4)
		{
			forwardParam.setParameter(ListRetrievalItemListBusiness.WORKSTATUS_KEY, RetrievalSupportParameter.STATUS_FLAG_COMPLETION);
		}
		//#CM713944
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalitemlist/ListRetrievalItemList.do", forwardParam, "/progress.do");
	}

	//#CM713945
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713946
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
		//#CM713947
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
		//#CM713948
		// Check for input. 
		txt_ConsignorCode.validate();
		txt_RtrivlPlanDate.validate();
		txt_ItemCode.validate(false);

		Connection conn = null;

		try
		{
			//#CM713949
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM713950
			// Set the schedule parameter. 
			RetrievalSupportParameter param = createParameter();

			//#CM713951
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalItemListSCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM713952
				// MSG-W0061={0} data corresponded.<BR> Do you print it? 
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM713953
				// Store the fact that the dialog was displayed via Print button. 
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM713954
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
				//#CM713955
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

	//#CM713956
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713957
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
		//#CM713958
		// Set the initial value for each input field. 
		txt_ConsignorCode.setText(getConsignorCode());
		txt_RtrivlPlanDate.setText("");
		txt_ItemCode.setText("");
		pul_WorkStatusRetrieval.setSelectedIndex(0);
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);

		//#CM713959
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
	}

}
//#CM713960
//end of class
