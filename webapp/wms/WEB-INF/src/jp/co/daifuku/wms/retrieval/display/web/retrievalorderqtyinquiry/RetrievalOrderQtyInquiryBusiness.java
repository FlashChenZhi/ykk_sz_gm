// $Id: RetrievalOrderQtyInquiryBusiness.java,v 1.2 2007/02/07 04:19:24 suresh Exp $

//#CM716677
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalorderqtyinquiry;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalcustomer.ListRetrievalCustomerBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievaldate.ListRetrievalDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderQtyInquirySCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM716678
/**
 * Designer : T.Yoshino<BR>
 * Maker : T.Yoshino<BR>
 * Allow this class of screen to inquire the Order Picking Result. <BR>
 * Pass the parameter to the schedule to execute the process to inquire Order Picking Result. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process by clicking "Display" button (<CODE>btn_View_Click</CODE>)<BR>
 * <BR>
 * <DIR>
 * 	 Set the contents entered via screen, and
 * 	 allow the schedule to search for the data to be displayed, based on the parameter, and display it in the preset area. <BR>
 * 	<BR>
 * 	 [Parameter]  *Mandatory Input<BR>
 * 	<DIR>
 *	Consignor Code*<BR>
 *	Start Picking Date<BR>
 *	End Picking Date<BR>
 *	Customer Code<BR>
 *	Order No.<BR>
 *	 Case/Piece division *<BR>
 *		<DIR>
 *   	 All <BR>
 *   	 Case <BR>
 *   	 Piece <BR>
 *   	 None <BR>
 *		</DIR>
 *	 Display order *
 *		<DIR>
 *   	In the order of Picking Date <BR>
 *   	In the order of Planned Picking Date <BR>
 *		</DIR>
 * <BR>
 *   [Data for Output]  <BR>
 * 	 <DIR>
 *   <BR>
 *		Picking Date<BR>
 *		Planned Picking Date<BR>
 *		Customer Code<BR>
 *		Customer Name<BR>
 *		Order No.<BR>
 *		Item Code<BR>
 *		Item Name<BR>
 *		 Division  <BR>
 *		Packed Qty per Case<BR>
 *		Packed qty per bundle<BR>
 *		Planned Work Case Qty<BR>
 *		Planned Work Piece Qty<BR>
 *		 Result Case Qty <BR>
 *		 Result Piece Qty <BR>
 *		Shortage Case Qty<BR>
 *		Shortage Piece Qty<BR>
 *		 Picking Location <BR>
 *		Expiry Date<BR>
 *		Case ITF<BR>
 *		Bundle ITF<BR>
 *		Worker Code<BR>
 *		Worker Name <BR>
 * 	   </DIR>
 * 	  </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/25</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:24 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderQtyInquiryBusiness extends RetrievalOrderQtyInquiry implements WMSConstants
{
	//#CM716679
	// Class fields --------------------------------------------------

	//#CM716680
	// Class variables -----------------------------------------------

	//#CM716681
	// Class method --------------------------------------------------

	//#CM716682
	// Constructors --------------------------------------------------

	//#CM716683
	// Public methods ------------------------------------------------

	//#CM716684
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Summary: shows the Initial Display. <BR>
	 * <BR>
	 * <DIR>
	 *    1. Initialize the input area. <BR>
	 *    2. Start the schedule.  <BR>
	 *    3. Set the cursor on the Consignor code. <BR>
	 * </DIR>
	 * <BR>
	 * Field item [Initial Value]  <BR>
	 * <BR>
	 * Consignor Code		 [Search Value]  <BR>
	 * Start Picking Date		 [None]  <BR>
	 * End Picking Date		 [None]  <BR>
	 * Customer Code		 [None]  <BR>
	 * Order No.		 [None]  <BR>
	 * Case/Piece division 	 [All]  <BR>
	 * Display order 				 [Order of Picking Date]  <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		setInitDsp();

		//#CM716685
		// Consignor Code (Preset) 
		txt_RConsignorCode.setText("");
		//#CM716686
		// Consignor Name (Preset) 
		txt_RConsignorName.setText("");
		//#CM716687
		// Listcell 
		lst_RetrievalOrderQtyInquiry.clearRow();
	}
	//#CM716688
	/**
	 * Invoke this before invoking each control event. <BR>
	 * <BR>
	 * Summary: Displays a dialog. <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM716689
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM716690
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM716691
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
	}
	//#CM716692
	/**
	 * Returning from a popup window invokes this method.
	 * Override the page_DlgBack defined for Page. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM716693
		// Obtain the parameter selected in the listbox. 
		//#CM716694
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM716695
		// Start Picking Date
		String startretrievaldate = param.getParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY);
		//#CM716696
		// End Picking Date
		String endretrievaldate = param.getParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY);
		//#CM716697
		// Customer Code
		String customercode = param.getParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY);
		//#CM716698
		// Order No.
		String orderno = param.getParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY);

		//#CM716699
		// Set a value if not empty. 
		//#CM716700
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM716701
		// Start Picking Date
		if (!StringUtil.isBlank(startretrievaldate))
		{
			txt_StartRetrievalDate.setText(startretrievaldate);
		}
		//#CM716702
		// End Picking Date
		if (!StringUtil.isBlank(endretrievaldate))
		{
			txt_EndRetrievalDate.setText(endretrievaldate);
		}
		//#CM716703
		// Customer Code
		if (!StringUtil.isBlank(customercode))
		{
			txt_CustomerCode.setText(customercode);
		}
		//#CM716704
		// Order No.
		if (!StringUtil.isBlank(orderno))
		{
			txt_OrderNo.setText(orderno);
		}

		//#CM716705
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
	}
	//#CM716706
	// Package methods -----------------------------------------------

	//#CM716707
	// Protected methods ---------------------------------------------

	//#CM716708
	// Private methods -----------------------------------------------
	//#CM716709
	/**
	 * Allow this method to obtain the Consignor code from the schedule on the initial display.  <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule.  <BR>
	 * 
	 * @return Consignor Code <BR>
	 *         Return the string of the Consignor code when one corresponding data exists. <BR>
	 *         Return null character when 0 or multiple corresponding data exist.  <BR>
	 * 
	 * @throws Exception
	 *             Report all exceptions. 
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			RetrievalSupportParameter param = new RetrievalSupportParameter();

			//#CM716710
			// Obtain the Consignor code from the schedule. 
			WmsScheduler schedule = new RetrievalOrderQtyInquirySCH();
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
			//#CM716711
			// Close the connection. 
			if (conn != null)
			{
				conn.close();
			}
		}
		return "";
	}
	//#CM716712
	/** 
	 * Initialize the input area. 
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	private void setInitDsp() throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM716713
			// Move the cursor to the Consignor code. 
			setFocus(txt_ConsignorCode);

			//#CM716714
			// Consignor Code
			txt_ConsignorCode.setText("");
			//#CM716715
			// Start Picking Date
			txt_StartRetrievalDate.setText("");
			//#CM716716
			// End Picking Date
			txt_EndRetrievalDate.setText("");
			//#CM716717
			// Customer Code
			txt_CustomerCode.setText("");
			//#CM716718
			// Order No.
			txt_OrderNo.setText("");
			//#CM716719
			// Case/Piece division 
			rdo_CpfAll.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);

			//#CM716720
			// Set Display order in Picking order. 
			rdo_RetrievalDate.setChecked(true);
			rdo_RetrievalPlanDate.setChecked(false);

			//#CM716721
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalOrderQtyInquirySCH();
			RetrievalSupportParameter param = (RetrievalSupportParameter) schedule.initFind(conn, null);

			//#CM716722
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
				//#CM716723
				// Close the connection. 
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}
	//#CM716724
	// Event handler methods -----------------------------------------
	//#CM716725
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716726
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716727
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716728
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

	//#CM716729
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716730
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716731
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716732
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716733
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716734
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716735
	/** 
	 * Clicking on the Search Consignor Code button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for a parameter and displays the Consignor list listbox using the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]  *Mandatory Input<BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM716736
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM716737
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM716738
		// "Search" flag 
		param.setParameter(ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY, RetrievalSupportParameter.SEARCHFLAG_RESULT);
		//#CM716739
		// for Order 
		param.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM716740
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do", param, "/progress.do");
	}

	//#CM716741
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716742
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716743
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartRetrievalDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716744
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartRetrievalDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716745
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStartRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716746
	/** 
	 * Clicking on the Search Start Picking Date button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Picking Date list using this search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]  *Mandatory Input<BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Start Picking Date<BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchStartRetrievalDate_Click(ActionEvent e) throws Exception
	{
		//#CM716747
		//Set the search condition in the Search screen of Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM716748
		//Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM716749
		// Start Picking Date
		param.setParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_StartRetrievalDate.getDate()));
		//#CM716750
		// Start flag (Picking date range flag) 
		param.setParameter(ListRetrievalDateBusiness.RANGERETRIEVALDATE_KEY, RetrievalSupportParameter.RANGE_START);
		//#CM716751
		// for Order 
		param.setParameter(ListRetrievalDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM716752
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievaldate/ListRetrievalDate.do", param, "/progress.do");
	}

	//#CM716753
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716754
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716755
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716756
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndRetrievalDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716757
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndRetrievalDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716758
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEndRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716759
	/** 
	 * Clicking on the Search End Picking Date button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Picking Date list using this search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]  *Mandatory Input<BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       End Picking Date<BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchEndRetrievalDate_Click(ActionEvent e) throws Exception
	{
		//#CM716760
		// Set the search condition in the Search screen of Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM716761
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM716762
		// End Picking Date
		param.setParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_EndRetrievalDate.getDate()));
		//#CM716763
		// "End" flag (Picking date range flag) 
		param.setParameter(ListRetrievalDateBusiness.RANGERETRIEVALDATE_KEY, RetrievalSupportParameter.RANGE_END);
		//#CM716764
		// for Order 
		param.setParameter(ListRetrievalDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM716765
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievaldate/ListRetrievalDate.do", param, "/progress.do");
	}

	//#CM716766
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716767
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716768
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716769
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716770
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716771
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716772
	/** 
	 * Clicking on Search Customer Code button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Customer list using this search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]  *Mandatory Input<BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Start Storage Date <BR>
	 *       End Storage Date <BR>
	 *       Customer Code<BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchCustomerCode_Click(ActionEvent e) throws Exception
	{
		//#CM716773
		// Set the search condition in the Search screen of Customer. 
		ForwardParameters param = new ForwardParameters();
		//#CM716774
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM716775
		// Start Picking Date
		param.setParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_StartRetrievalDate.getDate()));
		//#CM716776
		// End Picking Date
		param.setParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_EndRetrievalDate.getDate()));
		//#CM716777
		// Customer Code
		param.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		//#CM716778
		// for Order 
		param.setParameter(ListRetrievalCustomerBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM716779
		// "Search" flag 
		param.setParameter(ListRetrievalCustomerBusiness.SEARCHCUSTOMER_KEY, RetrievalSupportParameter.SEARCHFLAG_RESULT);
		//#CM716780
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalcustomer/ListRetrievalCustomer.do", param, "/progress.do");
	}

	//#CM716781
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_OrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716782
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716783
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716784
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716785
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_OrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716786
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716787
	/** 
	 * Clicking on the Search Order No. button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for the parameter and displays a listbox for the Order No. list using this search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]  *Mandatory Input<BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Start Picking Date<BR>
	 *       End Picking Date<BR>
	 *       Customer Code<BR>
	 * 		 Order No.<BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchOrderNo_Click(ActionEvent e) throws Exception
	{
		//#CM716788
		// Set the search condition in the Search screen of Order No. 
		ForwardParameters param = new ForwardParameters();
		//#CM716789
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM716790
		// Start Picking Date
		param.setParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_StartRetrievalDate.getDate()));
		//#CM716791
		// End Picking Date
		param.setParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_EndRetrievalDate.getDate()));
		//#CM716792
		// Customer Code
		param.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		//#CM716793
		// Order No.
		param.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, txt_OrderNo.getText());
		//#CM716794
		// for Order 
		param.setParameter(ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM716795
		// "Search" flag 
		param.setParameter(ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY, RetrievalSupportParameter.SEARCHFLAG_RESULT);
		//#CM716796
		// Case/Piece division flag 
		param.setParameter(ListRetrievalOrdernoBusiness.CASE_PIECE_KEY, RetrievalSupportParameter.LISTBOX_RETRIEVAL);
		//#CM716797
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do", param, "/progress.do");
	}

	//#CM716798
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716799
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716800
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM716801
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716802
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM716803
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716804
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM716805
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716806
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM716807
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DspOrder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716808
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_RetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716809
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_RetrievalDate_Click(ActionEvent e) throws Exception
	{
	}

	//#CM716810
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716811
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_RetrievalPlanDate_Click(ActionEvent e) throws Exception
	{
	}

	//#CM716812
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716813
	/** 
	 * Clicking on the Display button invokes this. <BR>
	 * <BR>
	 * Summary: Searches for Order Picking Result info using field items input in the Input area as conditions and displays it in the Preset area.<BR>
	 * <BR>
	 * <DIR>
	 *   1. Check for input in the input field item in the input area. (Mandatory Input, number of characters, and attribution of characters) <BR>
	 *   2. Start the schedule. <BR>
	 *   3. Display it in the preset area. <BR>
	 *   4. Maintain the contents of the input area. <BR>
	 * </DIR>
	 * <BR>
	 * <DIR>
	 *   [Row No. list of listcell] <BR>
	 *   <BR>
	 *   <DIR>
	 *      1.Picking Date<BR>
	 *      2.Customer Code<BR>
	 * 		3.Order No.<BR>
	 * 		4.Item Code<BR>
	 * 		5. Division <BR>
	 * 		6.Packed Qty per Case<BR>
	 * 		7.Planned Work Case Qty<BR>
	 * 		8. Result Case Qty <BR>
	 * 		9.Shortage Case Qty<BR>
	 * 		10. Picking Location <BR>
	 * 		11.Expiry Date<BR>
	 * 		12.Case ITF<BR>
	 * 		13.Worker Code<BR>
	 * 		14.Planned Picking Date<BR>
	 * 		15.Customer Name<BR>
	 * 		16.Item Name<BR>
	 * 		17.Packed qty per bundle<BR>
	 * 		18.Planned Work Piece Qty<BR>
	 * 		19. Result Piece Qty 
	 * 		20.Shortage Piece Qty<BR>
	 * 		21.Bundle ITF<BR>
	 * 		22.Worker Name<BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM716814
			// Move the cursor to the Consignor code. 
			setFocus(txt_ConsignorCode);

			//#CM716815
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM716816
			// Clear the Preset area. 
			lst_RetrievalOrderQtyInquiry.clearRow();
			txt_RConsignorCode.setText("");
			txt_RConsignorName.setText("");

			//#CM716817
			// Check for input. 
			txt_ConsignorCode.validate();
			//#CM716818
			// Pattern matching characters 
			txt_StartRetrievalDate.validate(false);
			txt_EndRetrievalDate.validate(false);
			txt_CustomerCode.validate(false);
			txt_OrderNo.validate(false);

			//#CM716819
			// Require that the value of the Start Picking Date is equal to or smaller than the value of the End Picking Date. 
			if (!StringUtil.isBlank(txt_StartRetrievalDate.getDate()) && !StringUtil.isBlank(txt_EndRetrievalDate.getDate()))
			{
				if (txt_StartRetrievalDate.getDate().compareTo(txt_EndRetrievalDate.getDate()) > 0)
				{
					//#CM716820
					// 6023107=Starting picking date must precede the end picking date. 
					message.setMsgResourceKey("6023107");
					return;
				}
			}
			//#CM716821
			// Set for the schedule parameter: 
			RetrievalSupportParameter param = new RetrievalSupportParameter();
			//#CM716822
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM716823
			// Start Picking Date
			param.setFromRetrievalDate(WmsFormatter.toParamDate(txt_StartRetrievalDate.getDate()));
			//#CM716824
			// End Picking Date
			param.setToRetrievalDate(WmsFormatter.toParamDate(txt_EndRetrievalDate.getDate()));
			//#CM716825
			// Customer Code
			param.setCustomerCode(txt_CustomerCode.getText());
			//#CM716826
			// Order No.
			param.setOrderNo(txt_OrderNo.getText());

			//#CM716827
			// Case/Piece division 
			if (rdo_CpfAll.getChecked())
			{
				//#CM716828
				// All 
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
			}
			else if (rdo_CpfCase.getChecked())
			{
				//#CM716829
				// Case 
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_CpfPiece.getChecked())
			{
				//#CM716830
				// Piece 
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
			}
			else if (rdo_CpfAppointOff.getChecked())
			{
				//#CM716831
				// None 
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
			}

			//#CM716832
			// Display order 
			if (rdo_RetrievalDate.getChecked())
			{
				//#CM716833
				// In the order of Picking Date 
				param.setDisplayOrder(RetrievalSupportParameter.DISPLAY_ORDER_WORK_DATE);
			}
			else if (rdo_RetrievalPlanDate.getChecked())
			{
				//#CM716834
				// In the order of Planned Picking Date 
				param.setDisplayOrder(RetrievalSupportParameter.DISPLAY_ORDER_PLAN_DATE);
			}

			WmsScheduler schedule = new RetrievalOrderQtyInquirySCH();
			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) schedule.query(conn, param);

			//#CM716835
			// Close the process when some error occurred or no display data was found. 
			if (viewParam == null || viewParam.length == 0)
			{
				//#CM716836
				// Close the connection. 
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM716837
			// Display the data if obtained the display data when the schedule normally completed. 
			//#CM716838
			// Consignor Code (Preset) 
			txt_RConsignorCode.setText(viewParam[0].getConsignorCode());
			//#CM716839
			// Consignor Name (Preset) 
			txt_RConsignorName.setText(viewParam[0].getConsignorName());

			//#CM716840
			// Customer Name
			String label_customername = DisplayText.getText("LBL-W0036");
			//#CM716841
			// Item Name
			String label_itemname = DisplayText.getText("LBL-W0103");
			//#CM716842
			// Worker Code
			String label_workercode = DisplayText.getText("LBL-W0325");
			//#CM716843
			// Worker Name
			String label_workername = DisplayText.getText("LBL-W0276");
			//#CM716844
			// Planned Work Case Qty
			String label_plancaseqty = DisplayText.getText("LBL-W0436");
			//#CM716845
			// Planned Work Piece Qty
			String label_planpieceqty = DisplayText.getText("LBL-W0437");
			//#CM716846
			// Result Case Qty 
			String label_resultcaseqty = DisplayText.getText("LBL-W0418");
			//#CM716847
			// Result Piece Qty 
			String label_resultpieceqty = DisplayText.getText("LBL-W0417");
			//#CM716848
			// Shortage Case Qty
			String label_shortagecaseqty = DisplayText.getText("LBL-W0438");
			//#CM716849
			// Shortage Piece Qty
			String label_shortagepieceqty = DisplayText.getText("LBL-W0439");
			//#CM716850
			// Picking Location 
			String label_retrievallocation = DisplayText.getText("LBL-W0172");
			//#CM716851
			// Expiry Date
			String label_usebydate = DisplayText.getText("LBL-W0270");
			//#CM716852
			// Case ITF
			String label_caseitf = DisplayText.getText("LBL-W0338");
			//#CM716853
			// Bundle ITF
			String label_bundleitf = DisplayText.getText("LBL-W0337");

			//#CM716854
			// LBL-W0719=----
			String noDisp = DisplayText.getText("LBL-W0719");

			String title_AreaTypeName = DisplayText.getText("LBL-W0569");


			//#CM716855
			// Set a value in the listcell. 
			for (int i = 0; i < viewParam.length; i++)
			{
				String workerCode = "";
				String workerName = "";
				//#CM716856
				// Worker Code
				//#CM716857
				// Worker Name
				if (viewParam[i].getSystemDiscKey() == RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS)
				{
					workerCode = noDisp;
					workerName = noDisp;
				}
				else
				{
					workerCode = viewParam[i].getWorkerCode();
					workerName = viewParam[i].getWorkerName();
				}

				//#CM716858
				// Add a line. 
				lst_RetrievalOrderQtyInquiry.addRow();
				lst_RetrievalOrderQtyInquiry.setCurrentRow(i + 1);

				int col = 1;

				//#CM716859
				// Set the search result in each cell. 
				//#CM716860
				// (1st row) 
				//#CM716861
				// Picking Date
				lst_RetrievalOrderQtyInquiry.setValue(
					col++,
					WmsFormatter.toDispDate(viewParam[i].getRetrievalDate(), this.getHttpRequest().getLocale()));
				//#CM716862
				// Customer Code
				lst_RetrievalOrderQtyInquiry.setValue(col++, viewParam[i].getCustomerCode());
				//#CM716863
				// Order No.
				lst_RetrievalOrderQtyInquiry.setValue(col++, viewParam[i].getOrderNo());
				//#CM716864
				// Item Code
				lst_RetrievalOrderQtyInquiry.setValue(col++, viewParam[i].getItemCode());
				//#CM716865
				// Division 
				lst_RetrievalOrderQtyInquiry.setValue(col++, viewParam[i].getCasePieceflgName());
				//#CM716866
				// Packed Qty per Case
				lst_RetrievalOrderQtyInquiry.setValue(col++, WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));
				//#CM716867
				// Planned Work Case Qty
				lst_RetrievalOrderQtyInquiry.setValue(col++, WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				//#CM716868
				// Result Case Qty 
				lst_RetrievalOrderQtyInquiry.setValue(col++, WmsFormatter.getNumFormat(viewParam[i].getResultCaseQty()));
				//#CM716869
				// Shortage Case Qty
				lst_RetrievalOrderQtyInquiry.setValue(col++, WmsFormatter.getNumFormat(viewParam[i].getShortageCaseQty()));
				//#CM716870
				// Picking Location   
				lst_RetrievalOrderQtyInquiry.setValue(
					col++,
					WmsFormatter.toDispLocation(viewParam[i].getRetrievalLocation(), viewParam[i].getSystemDiscKey()));
				//#CM716871
				// Expiry Date
				lst_RetrievalOrderQtyInquiry.setValue(col++, viewParam[i].getUseByDate());
				//#CM716872
				// Case ITF
				lst_RetrievalOrderQtyInquiry.setValue(col++, viewParam[i].getITF());
				//#CM716873
				// Worker Code
				lst_RetrievalOrderQtyInquiry.setValue(col++, workerCode);

				//#CM716874
				// (2nd row) 
				//#CM716875
				// Planned Picking Date
				lst_RetrievalOrderQtyInquiry.setValue(
					col++,
					WmsFormatter.toDispDate(viewParam[i].getRetrievalPlanDate(), this.getHttpRequest().getLocale()));
				//#CM716876
				// Customer Name
				lst_RetrievalOrderQtyInquiry.setValue(col++, viewParam[i].getCustomerName());
				//#CM716877
				// Item Name
				lst_RetrievalOrderQtyInquiry.setValue(col++, viewParam[i].getItemName());
				//#CM716878
				// Packed qty per bundle
				lst_RetrievalOrderQtyInquiry.setValue(col++, WmsFormatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
				//#CM716879
				// Planned Work Piece Qty
				lst_RetrievalOrderQtyInquiry.setValue(col++, WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
				//#CM716880
				// Result Piece Qty 
				lst_RetrievalOrderQtyInquiry.setValue(col++, WmsFormatter.getNumFormat(viewParam[i].getResultPieceQty()));
				//#CM716881
				// Shortage Piece Qty
				lst_RetrievalOrderQtyInquiry.setValue(col++, WmsFormatter.getNumFormat(viewParam[i].getShortagePieceQty()));
				//#CM716882
				// Bundle ITF
				lst_RetrievalOrderQtyInquiry.setValue(col++, viewParam[i].getBundleITF());
				//#CM716883
				// Worker Name
				lst_RetrievalOrderQtyInquiry.setValue(col++, workerName);

				//#CM716884
				// Set the tool tip (balloon field item). 
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM716885
				// Customer Name
				toolTip.add(label_customername, viewParam[i].getCustomerName());
				//#CM716886
				// Item Name
				toolTip.add(label_itemname, viewParam[i].getItemName());
				//#CM716887
				// Worker Code
				toolTip.add(label_workercode, workerCode);
				//#CM716888
				// Worker Name
				toolTip.add(label_workername, workerName);
				//#CM716889
				// Planned Work Case Qty
				toolTip.add(label_plancaseqty, viewParam[i].getPlanCaseQty());
				//#CM716890
				// Planned Work Piece Qty
				toolTip.add(label_planpieceqty, viewParam[i].getPlanPieceQty());
				//#CM716891
				// Result Case Qty 
				toolTip.add(label_resultcaseqty, viewParam[i].getResultCaseQty());
				//#CM716892
				// Result Piece Qty 
				toolTip.add(label_resultpieceqty, viewParam[i].getResultPieceQty());
				//#CM716893
				// Shortage Case Qty
				toolTip.add(label_shortagecaseqty, viewParam[i].getShortageCaseQty());
				//#CM716894
				// Shortage Piece Qty
				toolTip.add(label_shortagepieceqty, viewParam[i].getShortagePieceQty());
				//#CM716895
				// Picking Location 
				toolTip.add(
					label_retrievallocation,
					WmsFormatter.toDispLocation(viewParam[i].getRetrievalLocation(), viewParam[i].getSystemDiscKey()));
				//#CM716896
				// Expiry Date
				toolTip.add(label_usebydate, viewParam[i].getUseByDate());
				//#CM716897
				// Case ITF
				toolTip.add(label_caseitf, viewParam[i].getITF());
				//#CM716898
				// Bundle ITF
				toolTip.add(label_bundleitf, viewParam[i].getBundleITF());
				//#CM716899
				// Area Name 
				toolTip.add(title_AreaTypeName, viewParam[i].getRetrievalAreaName());

				lst_RetrievalOrderQtyInquiry.setToolTip(i + 1, toolTip.getText());

			}

			//#CM716900
			// Set the message. 
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM716901
				// Close the connection. 
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM716902
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716903
	/** 
	 * Clicking on the Clear button invokes this. <BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 * <DIR>
	 *    1. Return the field item in the input area to the initial value. <BR>
	 *    <DIR>
	 *  	- For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method. <BR>
	 *    </DIR>
	 *    2. Set the cursor on the Consignor code. <BR>
	 *    3. Maintain the contents of preset area. <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		setInitDsp();
	}

	//#CM716904
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716905
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716906
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716907
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716908
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716909
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716910
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716911
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716912
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716913
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderQtyInquiry_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM716914
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderQtyInquiry_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM716915
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderQtyInquiry_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM716916
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderQtyInquiry_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM716917
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderQtyInquiry_Server(ActionEvent e) throws Exception
	{
	}

	//#CM716918
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderQtyInquiry_Change(ActionEvent e) throws Exception
	{
	}

	//#CM716919
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_RetrievalOrderQtyInquiry_Click(ActionEvent e) throws Exception
	{
	}

}
//#CM716920
//end of class
