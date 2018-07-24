// $Id: RetrievalOrderQtyListBusiness.java,v 1.2 2007/02/07 04:19:25 suresh Exp $

//#CM716925
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalorderqtylist;
import java.sql.Connection;
import java.sql.SQLException;

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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievaldate.ListRetrievalDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderlist.ListRetrievalOrderListBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderQtyListSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM716926
/**
 * Designer : K.Mukai <BR>
 * Maker : T.Yoshino<BR>
 * Allow this class of screen to print the Order Picking Result Report. <BR>
 * Pass the parameter to the schedule to execute the process to print the Order Picking Result Report. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process by clicking "Display" button (<CODE>btn_PDisplay_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 *  Set the contents entered via screen, and
 *   allow the schedule to search for the data to be displayed, based on the parameter.<BR>
 *  Receive the search result from the schedule, and invoke the Order Picking Result Report list screen. <BR>
 *  <BR>
 *  [Parameter]  *Mandatory Input<BR>
 *  <DIR>
 *   Consignor Code*<BR>
 *   Start Picking Date<BR>
 *   End Picking Date<BR>
 *   Customer Code<BR>
 *   Order No.<BR>
 *   Case/Piece division <BR>
 *   <DIR>
 *   All <BR>
 *   Case <BR>
 *   Piece <BR>
 *   None <BR>
 *   </DIR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Print" button (<CODE>btn_Print_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 *  Set the contents entered via screen, and<BR>
 *  allow the schedule to search for the databased on the parameter and print it.  <BR>
 *  Allow the schedule to return true when printing completed successfully or return false when it failed. <BR>
 *  <BR>
 *  [Parameter]  *Mandatory Input<BR>
 *  <DIR>
 *   Consignor Code*<BR>
 *   Start Picking Date<BR>
 *   End Picking Date<BR>
 *   Customer Code<BR>
 *   Order No.<BR>
 *   Case/Piece division <BR>
 *   <DIR>
 *   All <BR>
 *   Case <BR>
 *   Piece <BR>
 *   None <BR>
 *   </DIR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/25</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:25 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderQtyListBusiness extends RetrievalOrderQtyList implements WMSConstants
{
	//#CM716927
	// Class fields --------------------------------------------------

	//#CM716928
	// Maintain the control that invokes the dialog: Print button 
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";

	//#CM716929
	// Class variables -----------------------------------------------

	//#CM716930
	// Class method --------------------------------------------------

	//#CM716931
	// Constructors --------------------------------------------------

	//#CM716932
	// Public methods ------------------------------------------------

	//#CM716933
	/**
	 * Initialize the screen. <BR>
	 * Summary: Shows the initial display. Disable Clear button to allow to invoke page_load.  <BR>
	 * <BR>
	 * Field item: name [Initial Value]  <BR>
	 * <DIR>
	 * Consignor Code [If there is only one Consignor code that corresponds to the condition, show the Initial Display.]  <BR>
	 * Start Picking Date [None] <BR>
	 * End Picking Date [None] <BR>
	 * Customer Code [None] <BR>
	 * Order No.  [None] <BR>
	 * Case/Piece division  [All] <BR>
	 * </DIR>
	 *
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{		
		//#CM716934
		// Set the initial value for each input field. 
		//#CM716935
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM716936
		// Start Picking Date
		txt_StartRetrievalDate.setText("");
		//#CM716937
		// End Picking Date
		txt_EndRetrievalDate.setText("");		
		//#CM716938
		// Customer Code
		txt_CustomerCode.setText("");
		//#CM716939
		// Order No.
		txt_OrderNo.setText("");	
		//#CM716940
		// Case/Piece division 
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);
		
		//#CM716941
		// Move the cursor to the Consignor code. 
		setFocus(txt_ConsignorCode);
	}
	//#CM716942
	/**
	 * Invoke this before invoking each control event.  <BR>
	 * <BR>
	 * <DIR>
	 *  Summary: Displays a dialog.  <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM716943
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM716944
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM716945
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}

	}
	
	//#CM716946
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
			//#CM716947
			// Set the focus for the Consignor code. 
			setFocus(txt_ConsignorCode);

			//#CM716948
			// Identify which dialog returns it. 
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM716949
			// Clicking on Yes in the dialog box returns true. 
			//#CM716950
			// Clicking on No in the dialog box returns false. 
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM716951
			// Clicking NO button closes the process. 
			//#CM716952
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
			//#CM716953
			// Ensure to turn the flag OFF after closed the operation of the dialog. 
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM716954
		// Start the printing schedule. 
		Connection conn = null;
		try
		{
			//#CM716955
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM716956
			// Set the input value for the parameter. 
			RetrievalSupportParameter[] param = new RetrievalSupportParameter[1];
			param[0] = createParameter();

			//#CM716957
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalOrderQtyListSCH();
			schedule.startSCH(conn, param);
			
			//#CM716958
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
				//#CM716959
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
	
	//#CM716960
	/**
	 * Returning from a popup window invokes this method.
	 * Override the page_DlgBack defined for Page. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM716961
		// Obtain the parameter selected in the listbox. 
		//#CM716962
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM716963
		// Start Picking Date
		String startretrievaldate = param.getParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY);
		//#CM716964
		// End Picking Date
		String endretrievaldate = param.getParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY);
		//#CM716965
		// Customer Code
		String customercode = param.getParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY);
		//#CM716966
		// Order No.
		String orderno = param.getParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY);
		//#CM716967
		// Set a value if not empty. 
		//#CM716968
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM716969
		// Start Picking Date
		if (!StringUtil.isBlank(startretrievaldate))
		{
			txt_StartRetrievalDate.setText(startretrievaldate);
		}
		//#CM716970
		// End Picking Date
		if (!StringUtil.isBlank(endretrievaldate))
		{
			txt_EndRetrievalDate.setText(endretrievaldate);
		}
		//#CM716971
		// Customer Code
		if (!StringUtil.isBlank(customercode))
		{
			txt_CustomerCode.setText(customercode);
		}
		//#CM716972
		// Order No.
		if (!StringUtil.isBlank(orderno))
		{
			txt_OrderNo.setText(orderno);
		}		
		//#CM716973
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
	}
	//#CM716974
	// Package methods -----------------------------------------------

	//#CM716975
	// Protected methods ---------------------------------------------
	
	//#CM716976
	/**
	 * Generate a parameter object for which the input value of the input area is set. <BR>
	 * 
	 * @return Parameter object that contains input values in the input area. 
	 */
	protected RetrievalSupportParameter createParameter()
	{
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		
		//#CM716977
		// Consignor Code
		param.setConsignorCode(txt_ConsignorCode.getText());
		//#CM716978
		// Start Picking Date
		param.setFromRetrievalDate(WmsFormatter.toParamDate(txt_StartRetrievalDate.getDate()));
		//#CM716979
		// End Picking Date
		param.setToRetrievalDate(WmsFormatter.toParamDate(txt_EndRetrievalDate.getDate()));
		//#CM716980
		// Customer Code
		param.setCustomerCode(txt_CustomerCode.getText());
		//#CM716981
		// Order No.
		param.setOrderNo(txt_OrderNo.getText());				
		//#CM716982
		// Case/Piece division 				
		if (rdo_CpfAll.getChecked())
		{
			param.setCasePieceflg(
				RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
		}
		else if (rdo_CpfCase.getChecked())
		{
			param.setCasePieceflg(
				RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		else if (rdo_CpfPiece.getChecked())
		{
			param.setCasePieceflg(
				RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setCasePieceflg(
				RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}			
		return param;
	}


	//#CM716983
	// Private methods -----------------------------------------------
	//#CM716984
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
			//#CM716985
			// Obtain Connection 	
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			RetrievalSupportParameter param = new RetrievalSupportParameter();

			//#CM716986
			// Obtain the Consignor code from the schedule. 
			WmsScheduler schedule = new RetrievalOrderQtyListSCH();
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
				//#CM716987
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
	//#CM716988
	// Event handler methods -----------------------------------------
	//#CM716989
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716990
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716991
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716992
	/** 
	 * Clicking on the Menu button invokes this. <BR>
	 * Summary: Shifts to the Menu screen. <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM716993
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716994
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716995
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716996
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716997
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716998
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716999
	/** 
	 * Clicking on the Search Consignor button invokes this. <BR>
	 * Summary: This method sets the search conditions for the parameter and <BR>
	 * displays the listbox for searching through the Consignor list using the search condition. <BR>
	 * [Parameter]  *Mandatory Input
	 * <DIR>
	 * Consignor Code<BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM717000
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM717001
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM717002
		// "Search" flag 
		param.setParameter(
			ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY,
			RetrievalSupportParameter.SEARCHFLAG_RESULT);
		//#CM717003
		// for Order 
		param.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM717004
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do",
			param,
			"/progress.do");		
	}

	//#CM717005
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717006
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717007
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartRetrievalDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717008
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartRetrievalDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717009
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStartRtrivlDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717010
	/** 
	 * Clicking on the Search Start Search Picking Date button invokes this. <BR>
	 * Summary: This method sets the search conditions for the parameter and <BR>
	 * displays the listbox for searching through the Picking Date list using this search condition. <BR>
	 * [Parameter]  *Mandatory Input
	 * <DIR>
	 * Consignor Code<BR>
	 * Start Picking Date<BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchStartRtrivlDate_Click(ActionEvent e) throws Exception
	{
		//#CM717011
		// Set the search condition in the Search screen of Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM717012
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,txt_ConsignorCode.getText());
		//#CM717013
		// Start Picking Date
		param.setParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY,WmsFormatter.toParamDate(txt_StartRetrievalDate.getDate()));
		//#CM717014
		// Start flag (Picking date range flag) 
		param.setParameter(ListRetrievalDateBusiness.RANGERETRIEVALDATE_KEY,RetrievalSupportParameter.RANGE_START);
		//#CM717015
		// for Order 
		param.setParameter(ListRetrievalDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM717016
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievaldate/ListRetrievalDate.do",
			param,
			"/progress.do");
	}

	//#CM717017
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717018
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717019
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717020
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndRetrievalDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717021
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndRetrievalDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717022
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEndRtrivlDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717023
	/** 
	 * Clicking on the Search End Picking Date button invokes this. <BR>
	 * Summary: This method sets the search conditions for the parameter and <BR>
	 * displays the listbox for searching through the Picking Date list using this search condition. <BR>
	 * [Parameter]  *Mandatory Input
	 * <DIR>
	 * Consignor Code<BR>
	 * End Picking Date<BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchEndRtrivlDate_Click(ActionEvent e) throws Exception
	{
		//#CM717024
		// Set the search condition in the Search screen of Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM717025
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,txt_ConsignorCode.getText());
		//#CM717026
		// End Picking Date
		param.setParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY,WmsFormatter.toParamDate(txt_EndRetrievalDate.getDate()));
		//#CM717027
		// "End" flag (Picking date range flag) 
		param.setParameter(ListRetrievalDateBusiness.RANGERETRIEVALDATE_KEY,RetrievalSupportParameter.RANGE_END);
		//#CM717028
		// for Order 
		param.setParameter(ListRetrievalDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM717029
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievaldate/ListRetrievalDate.do",
			param,
			"/progress.do");
	}

	//#CM717030
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717031
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717032
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717033
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717034
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717035
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717036
	/** 
	 * Clicking on Search Customer button invokes this. <BR>
	 * Summary: This method sets the search conditions for the parameter and <BR>
	 * displays the listbox for searching through the Customer list using this search condition. <BR>
	 * [Parameter]  *Mandatory Input
	 * <DIR>
	 * Consignor Code<BR>
	 * Start Picking Date<BR>
	 * End Picking Date<BR>
	 * Customer Code<BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchCustomerCode_Click(ActionEvent e) throws Exception
	{
		//#CM717037
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM717038
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM717039
		// Start Picking Date
		param.setParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_StartRetrievalDate.getDate()));
		//#CM717040
		// End Picking Date
		param.setParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_EndRetrievalDate.getDate()));
		//#CM717041
		// Customer Code
		param.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		//#CM717042
		// "Search" flag 
		param.setParameter(
			ListRetrievalCustomerBusiness.SEARCHCUSTOMER_KEY,
			RetrievalSupportParameter.SEARCHFLAG_RESULT);
		//#CM717043
		// for Order 
		param.setParameter(ListRetrievalCustomerBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM717044
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalcustomer/ListRetrievalCustomer.do",
			param,
			"/progress.do");
	}

	//#CM717045
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_OrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717046
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717047
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM717048
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM717049
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM717050
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717051
	/** 
	 * Clicking on "Search Order No." button invokes this.<BR>
	 * Summary: This method sets the search conditions for the parameter and <BR>
	 * displays the listbox for searching through the Order No. list using this search condition. <BR>
	 * [Parameter]  *Mandatory Input
	 * <DIR>
	 * Consignor Code<BR>
	 * Start Picking Date<BR>
	 * End Picking Date<BR>
	 * Customer Code<BR>
	 * Order No.<BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchOrderNo_Click(ActionEvent e) throws Exception
	{
		//#CM717052
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM717053
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM717054
		// Start Picking Date
		param.setParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_StartRetrievalDate.getDate()));
		//#CM717055
		// End Picking Date
		param.setParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_EndRetrievalDate.getDate()));
		//#CM717056
		// Customer Code
		param.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		//#CM717057
		// Order No.
		param.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, txt_OrderNo.getText());
		//#CM717058
		// "Search" flag 
	 	param.setParameter(
	 		ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY, 
	 		RetrievalSupportParameter.SEARCHFLAG_RESULT);
		//#CM717059
		// Case/Piece division flag 
		param.setParameter(
			ListRetrievalOrdernoBusiness.CASE_PIECE_KEY,
			RetrievalSupportParameter.LISTBOX_RETRIEVAL);
		//#CM717060
		// for Order 
		param.setParameter(ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM717061
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do",
			param,
			"/progress.do");
	}

	//#CM717062
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717063
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717064
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717065
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717066
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717067
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717068
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717069
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717070
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM717071
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717072
	/** 
	 * Clicking on the "Display" button invokes this. <BR>
	 * Summary: Sets the field item input in the Input area and displays a listbox of Order Picking Result list in the different screen.<BR>
	 * <BR><BR>
	 * [Parameter]  *Mandatory Input<BR>
	 * <DIR>
	 * Consignor Code*<BR>
	 * Start Picking Date<BR>
	 * End Picking Date<BR>
	 * Customer Code<BR>
	 * Order No.<BR>
	 * Case/Piece division <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		//#CM717073
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();
		//#CM717074
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM717075
		// Set the Start Picking Date. 
		forwardParam.setParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_StartRetrievalDate.getDate()));	
		//#CM717076
		// Set the End Picking Date. 
		forwardParam.setParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_EndRetrievalDate.getDate()));	
		//#CM717077
		// Set the Customer Code. 
		forwardParam.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		//#CM717078
		// Set the Order No. 
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, txt_OrderNo.getText());		
		//#CM717079
		// Set the Case/Piece division. 
		if (rdo_CpfAll.getChecked())
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
		}
		else if (rdo_CpfCase.getChecked())
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		else if (rdo_CpfPiece.getChecked())
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		else if (rdo_CpfAppointOff.getChecked())
		{
			forwardParam.setParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM717080
		// Displays the listbox for Order Picking Result Report list. 
		redirect("/retrieval/listbox/listretrievalorderqtylist/ListRetrievalOrderQtyList.do", forwardParam, "/progress.do");
	}

	//#CM717081
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717082
	/** 
	 * Clicking on the "Print" button invokes this. <BR>
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
		//#CM717083
		// Move the cursor to the Consignor code. 
		setFocus(txt_ConsignorCode);
		//#CM717084
		// Check for input. 
		txt_ConsignorCode.validate();
		txt_StartRetrievalDate.validate(false);
		txt_EndRetrievalDate.validate(false);		
		txt_CustomerCode.validate(false);
		txt_OrderNo.validate(false);		
	
		//#CM717085
		// Require that the value of Start Picking Date is larger than the value of End Picking Date. 
		if (!StringUtil.isBlank(txt_StartRetrievalDate.getDate())
			&& !StringUtil.isBlank(txt_EndRetrievalDate.getDate()))
		{
			if (txt_StartRetrievalDate.getDate().compareTo(txt_EndRetrievalDate.getDate()) > 0)
			{
				//#CM717086
				// 6023107=Starting picking date must precede the end picking date. 
				message.setMsgResourceKey("6023107");
				return;
			}
		}
		Connection conn = null;
		try
		{
			//#CM717087
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM717088
			// Set the schedule parameter. 
			RetrievalSupportParameter param = createParameter();
			
			//#CM717089
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalOrderQtyListSCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM717090
				// MSG-W0061={0} data corresponded.<BR> Do you print it? 
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM717091
				// Store the fact that the dialog was displayed via Print button. 
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM717092
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
				//#CM717093
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

	//#CM717094
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM717095
	/** 
	 * Clicking on the "Clear" button invokes this. <BR>
	 * Summary: Clears the input area. <BR><BR>
	 * Initialize the screen.  <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM717096
		// Set Initial Value. 
		//#CM717097
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM717098
		// Start Picking Date
		txt_StartRetrievalDate.setText("");
		//#CM717099
		// End Picking Date
		txt_EndRetrievalDate.setText("");		
		//#CM717100
		// Customer Code
		txt_CustomerCode.setText("");
		//#CM717101
		// Order No.
		txt_OrderNo.setText("");	
		//#CM717102
		// Case/Piece division 
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);
		//#CM717103
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
	}

}
//#CM717104
//end of class
