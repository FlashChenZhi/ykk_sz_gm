// $Id: NoPlanRetrievalQtyInquiryBusiness.java,v 1.2 2006/10/04 05:04:37 suresh Exp $

//#CM7057
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.noplanretrievalqtyinquiry;
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
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.listnoplancustomer.ListNoPlanCustomerBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.listnoplanretrievaldate.ListNoPlanRetrievalDateBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.schedule.NoPlanRetrievalQtyInqirySCH;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM7058
/**
 * Designer : T.Nakajima(UTC) <BR>
 * Maker : T.Nakajima(UTC) <BR>
 * This is Unplanned Picking Result inquiry screen class.<BR>
 * Pass the parameter to the schedule that execute Unplanned Picking Result inquiry process.<BR>
 * Execute following processes in this class. <BR>
 * 1.Display button press down process(<CODE>btn_View_Click</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *  Set contents from screen to the parameter<BR>
 *  Display data of search result to list cell.<BR>
 *  <BR>
 *  [parameter]*Mandatory Input <BR>
 *  Consignor code* : ConsignorCode <BR>
 *  Start picking date : FromWorkDate <BR>
 *  End picking date : ToWorkDate  <BR>
 *  Item code : ItemCode <BR>
 *  Customer code : CustomerCode <BR>
 *  Case/Piece division* : CasePieceFlag <BR>
 *      <DIR>
 *      All <BR>
 *      Case <BR>
 *      Piece <BR>
 *      None <BR>
 *      </DIR>
 *  <BR>
 *  Display Sequence* : DspOrder <BR>
 *      <DIR>
 *      Item code sequence <BR>
 *      Customer code-Item code sequence<BR>
 *      </DIR>
 *  <BR>
 *  [Output data]
 *      Consignor code : ConsignorCode <BR>
 *      Consignor name : ConsignorName <BR>
 *      Picking date : RetrievalDateString <BR>
 *      Item code : ItemCode <BR>
 *      Item name : ItemName <BR>
 *      Division : CasePieceFlag <BR>
 *      Packing qty per case : EnteringQty <BR>
 *      packed qty per bundle : BundleEnteringQty <BR>
 *      Reslt Case qty : ResultCaseQty <BR>
 *      Result Piece Qty : ResultPieceQty <BR>
 *      Picking Location : LocationNo <BR>
 *      Expiry Date : UseByDate <BR>
 *      Case ITF : ITF <BR>
 *      Bundle ITF : BundleITF <BR>
 *      Customer code : CustomerCode <BR>
 *      Customer name : CustomerName <BR>
 *      Worker Code : WorkerCode <BR>
 *      Worker Name : WorkerName <BR>
 * 	</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:04:37 $
 * @author  $Author: suresh $
 */
public class NoPlanRetrievalQtyInquiryBusiness extends NoPlanRetrievalQtyInquiry implements WMSConstants
{
	//#CM7059
	// Class fields --------------------------------------------------

	//#CM7060
	// Class variables -----------------------------------------------

	//#CM7061
	// Class method --------------------------------------------------

	//#CM7062
	// Constructors --------------------------------------------------

	//#CM7063
	// Public methods ------------------------------------------------

	//#CM7064
	/**
	 * Initialize the screen <BR>
	 * <BR>
	 * Summary: Make initial/default of the screen.<BR>
	 * <BR>
	 * <DIR>
	 *    1.Initialize the input area. <BR>
	 *    2.Start schedule <BR>
	 *    3.Set the focus for the Consignor code. <BR>
	 * </DIR>
	 * <BR>
	 * Subject [Initial value] <BR>
	 * <BR>
	 * Consignor code         [search result] <BR>
	 * Start Storage Date         [None] <BR>
	 * End Storage Date         [None] <BR>
	 * Item code         [None] <BR>
	 * Customer code		  [None] <BR>
	 * Case/Piece division  [All] <BR>
	 * Display Sequence             [Item code sequence] <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM7065
		// Execute initial setting of the input area.
		setInitDsp();

		//#CM7066
		// Consignor code (preset)
		txt_RConsignorCode.setText("");
		//#CM7067
		// Consignor name (preset)
		txt_RConsignorName.setText("");
		//#CM7068
		// List cell
		lst_SNoPlanRtrvlQtyIqr.clearRow();
	}

	//#CM7069
	/**
	 * Invoke this before invoking each control event.<BR>
	 * <BR>
	 * Summary: Display dialog<BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM7070
			// Obtain parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM7071
			// Store to ViewState
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM7072
			// Set the screen name
			lbl_SettingName.setResourceKey(title);
		}
	}

	//#CM7073
	/**
	 * Invoke this method when returning from the popup window. <BR>
	 * Override page_DlgBack defined on the page.<BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters wParam = ((DialogEvent) e).getDialogParameters();
		//#CM7074
		// Obtain the parameter selected in the listbox.
		//#CM7075
		// Consignor code
		String consignorcode = wParam.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM7076
		// Start picking date
		String startretrievaldate = wParam.getParameter(ListNoPlanRetrievalDateBusiness.STARTRETRIEVALDATE_KEY);
		//#CM7077
		// End picking date
		String endretrievaldate = wParam.getParameter(ListNoPlanRetrievalDateBusiness.ENDRETRIEVALDATE_KEY);
		//#CM7078
		// Item code
		String itemcode = wParam.getParameter(ListStockItemBusiness.ITEMCODE_KEY);
		//#CM7079
		// Customer code
		String customercode = wParam.getParameter(ListNoPlanCustomerBusiness.CUSTOMERCODE_KEY);

		//#CM7080
		// Set the obtained item (count) it not empty for the corresponding input item (count).
		//#CM7081
		// Consignor code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM7082
		// Start Storage Date
		if (!StringUtil.isBlank(startretrievaldate))
		{
			txt_StartRetrievalDate.setText(startretrievaldate);
		}
		//#CM7083
		// End Storage Date
		if (!StringUtil.isBlank(endretrievaldate))
		{
			txt_EndRetrievalDate.setText(endretrievaldate);
		}
		//#CM7084
		// Item code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM7085
		// Customer code
		if (!StringUtil.isBlank(customercode))
		{
			txt_CustomerCode.setText(customercode);
		}
		//#CM7086
		// Set the focus for the Consignor code.
		setFocus(txt_ConsignorCode);
	}

	//#CM7087
	// Package methods -----------------------------------------------

	//#CM7088
	// Protected methods ---------------------------------------------

	//#CM7089
	// Private methods -----------------------------------------------

	//#CM7090
	/**
	 * Clicking on the Clear button or opening initial display allows to initialize the input area. <BR>
	 *   
	 * @throws Exception Reports all the exceptions.
	 */
	private void setInitDsp() throws Exception
	{
		//#CM7091
		// Connection
		Connection wConn = null;

		try
		{
			//#CM7092
			// 20050822 modify start Y.Kagawa
			//#CM7093
			// Set the focus for the Consignor code.
			setFocus(txt_ConsignorCode);

			//#CM7094
			// Consignor code
			txt_ConsignorCode.setText("");
			//#CM7095
			// Start picking date
			txt_StartRetrievalDate.setText("");
			//#CM7096
			// End picking date
			txt_EndRetrievalDate.setText("");
			//#CM7097
			// Item code
			txt_ItemCode.setText("");
			//#CM7098
			// Customer code
			txt_CustomerCode.setText("");
			//#CM7099
			// Case/Piece division
			rdo_CpfAll.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);
			//#CM7100
			// Display Sequence
			rdo_ItemCode.setChecked(true);
			rdo_CustomerCodeItemCode.setChecked(false);

			//#CM7101
			// Obtain the connection.
			wConn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM7102
			// Generate instance for Unplanned Picking Result inquiry schedule class.
			WmsScheduler wSchedule = new NoPlanRetrievalQtyInqirySCH();
			//#CM7103
			// Pass null if no search conditions needed.
			StockControlParameter wParam = (StockControlParameter) wSchedule.initFind(wConn, null);

			if (wParam != null)
			{
				txt_ConsignorCode.setText(wParam.getConsignorCode());
			}
			else
			{
				txt_ConsignorCode.setText("");
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
				//#CM7104
				// Connection close
				if (wConn != null)
					wConn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM7105
	// Event handler methods -----------------------------------------
	//#CM7106
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7107
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7108
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Inquiry_Click(ActionEvent e) throws Exception
	{
	}

	//#CM7109
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7110
	/** 
	 * Clicking on the Clicking the Menu button invokes this. <BR>
	 * <BR>
	 * Summary: Move to menu screen. <BR>
	 * <BR>
	 *
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM7111
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7112
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7113
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7114
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7115
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7116
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7117
	/** 
	 * Clicking Consignor search button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displaysthe Consignor search listbox. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] *Mandatory Input<BR>
	 *    <DIR>
	 *       Consignor code<BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM7118
		// Set the search conditions parameter.
		ForwardParameters wParam = new ForwardParameters();
		//#CM7119
		// Consignor code
		wParam.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM7120
		// Search flag (Set search destination table)
		wParam.setParameter(ListStockConsignorBusiness.SEARCHCONSIGNOR_KEY, StockControlParameter.SEARCHFLAG_EX_RETRIEVAL);
		//#CM7121
		// In process screen->Result screen 
		redirect("/stockcontrol/listbox/liststockconsignor/ListStockConsignor.do", wParam, "/progress.do");
	}

	//#CM7122
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7123
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7124
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartRetrievalDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7125
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartRetrievalDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7126
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStartRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7127
	/** 
	 * Clicking on the Search Start Picking Date button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displaysthe picking date search listbox. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] *Mandatory Input <BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *       Start picking date <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchStartRetrievalDate_Click(ActionEvent e) throws Exception
	{
		//#CM7128
		// Set the search conditions parameter.
		ForwardParameters wParam = new ForwardParameters();
		//#CM7129
		// Consignor code
		wParam.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM7130
		// Start picking date
		wParam.setParameter(ListNoPlanRetrievalDateBusiness.STARTRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_StartRetrievalDate.getDate()));
		//#CM7131
		// Start flag(picking date range flag)
		wParam.setParameter(ListNoPlanRetrievalDateBusiness.RANGERETRIEVALDATE_KEY, StockControlParameter.RANGE_START);
		//#CM7132
		// Search flag(Unplanned picking or storage)
		wParam.setParameter(ListNoPlanRetrievalDateBusiness.SEARCHDATE_KEY, StockControlParameter.SEARCHFLAG_EX_RETRIEVAL);
		//#CM7133
		// In process screen->Result screen 
		redirect("/stockcontrol/listbox/listnoplanretrievaldate/ListNoPlanRetrievalDate.do", wParam, "/progress.do");
	}

	//#CM7134
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7135
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7136
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7137
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndRetrievalDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7138
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndRetrievalDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7139
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEndRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7140
	/** 
	 * Clicking on the Search End Picking Date button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displaysthe picking date search listbox. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] *Mandatory Input <BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *       End picking date <BR>
	 *    </DIR>
	 * <BR> 
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchEndRetrievalDate_Click(ActionEvent e) throws Exception
	{
		//#CM7141
		// Set the search conditions parameter.
		ForwardParameters wParam = new ForwardParameters();
		//#CM7142
		// Consignor code
		wParam.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM7143
		// End picking date
		wParam.setParameter(ListNoPlanRetrievalDateBusiness.ENDRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_EndRetrievalDate.getDate()));
		//#CM7144
		// End flag(picking date range flag)
		wParam.setParameter(ListNoPlanRetrievalDateBusiness.RANGERETRIEVALDATE_KEY, StockControlParameter.RANGE_END);
		//#CM7145
		// Search flag(Unplanned picking or storage)
		wParam.setParameter(ListNoPlanRetrievalDateBusiness.SEARCHDATE_KEY, StockControlParameter.SEARCHFLAG_EX_RETRIEVAL);
		//#CM7146
		// In process screen->Result screen 
		redirect("/stockcontrol/listbox/listnoplanretrievaldate/ListNoPlanRetrievalDate.do", wParam, "/progress.do");
	}

	//#CM7147
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7148
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7149
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7150
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7151
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7152
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7153
	/** 
	 * Clicking on the Search Item Code button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displaysthe item code search listbox. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] *Mandatory Input <BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *       Start picking date <BR>
	 *       End picking date <BR>
	 *       Item code <BR>
	 *    </DIR>
	 * <BR> 
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM7154
		// Set the search conditions parameter.
		ForwardParameters wParam = new ForwardParameters();
		//#CM7155
		// Consignor code
		wParam.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM7156
		// Start picking date
		wParam.setParameter(ListNoPlanRetrievalDateBusiness.STARTRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_StartRetrievalDate.getDate()));
		//#CM7157
		// End picking date
		wParam.setParameter(ListNoPlanRetrievalDateBusiness.ENDRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_EndRetrievalDate.getDate()));
		//#CM7158
		// Item code
		wParam.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM7159
		// Search flag (Set search destination table)
		wParam.setParameter(ListStockItemBusiness.SEARCHITEM_KEY, StockControlParameter.SEARCHFLAG_EX_RETRIEVAL);
		//#CM7160
		// In process screen->Result screen 
		redirect("/stockcontrol/listbox/liststockitem/ListStockItem.do", wParam, "/progress.do");
	}

	//#CM7161
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7162
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7163
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7164
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7165
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7166
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7167
	/** 
	 * Clicking on the Search Customer Code button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displaysthe customer code search listbox. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] *Mandatory Input <BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *       Start picking date <BR>
	 *       End picking date <BR>
	 *       Item code <BR>
	 *       Customer code <BR>
	 *    </DIR>
	 * <BR> 
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchCustomerCode_Click(ActionEvent e) throws Exception
	{
		//#CM7168
		// Set the search conditions parameter.
		ForwardParameters wParam = new ForwardParameters();
		//#CM7169
		// Consignor code
		wParam.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM7170
		// Start picking date
		wParam.setParameter(ListNoPlanRetrievalDateBusiness.STARTRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_StartRetrievalDate.getDate()));
		//#CM7171
		// End picking date
		wParam.setParameter(ListNoPlanRetrievalDateBusiness.ENDRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_EndRetrievalDate.getDate()));
		//#CM7172
		// Item code
		wParam.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM7173
		// Customer code
		wParam.setParameter(ListNoPlanCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		//#CM7174
		// Search flag (Set search destination table)
		wParam.setParameter(ListNoPlanCustomerBusiness.SEARCHCUSTOMER_KEY, StockControlParameter.SEARCHFLAG_EX_RETRIEVAL);
		//#CM7175
		// In process screen->Result screen 
		redirect("/stockcontrol/listbox/listnoplancustomer/ListNoPlanCustomer.do", wParam, "/progress.do");
	}

	//#CM7176
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7177
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7178
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM7179
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7180
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM7181
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7182
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM7183
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7184
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM7185
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DspOrder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7186
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7187
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ItemCode_Click(ActionEvent e) throws Exception
	{
	}

	//#CM7188
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CustomerCodeItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7189
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CustomerCodeItemCode_Click(ActionEvent e) throws Exception
	{
	}

	//#CM7190
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7191
	/** 
	 * Clicking on the Display button invokes this. <BR>
	 * <BR>
	 * Summary: Searches for the Result data (DvResultView) and displays it in the preset area. <BR>
	 * <BR>
	 * <DIR>
	 *   1.Check input field of input area (mandatory input)<BR>
	 *   2.Start schedule <BR>
	 *   3.Display it in the preset area. <BR>
	 *   4.Maintain the contents of the input area is still maintained. <BR>
	 * </DIR>
	 * <BR>
	 * <DIR>
	 *    [Line No. list of the list cell] <BR>
	 *    <BR>
	 *    <DIR>
	 *      1.Picking date <BR>
	 *      2.Item code <BR>
	 * 		3.Division <BR>
	 * 		4.Packing qty per Case <BR>
	 * 		5.Result Case qty <BR>
	 * 		6.Picking Location <BR>
	 * 		7.Expiry Date <BR>
	 * 		8.Case ITF <BR>
	 * 		9.Customer code <BR>
	 * 		10.Worker Code <BR> 
	 * 		11.Item name <BR>
	 * 		12.Packing qty per Bundle <BR>
	 * 		13.Result Piece qty <BR>
	 * 		14.Bundle ITF <BR>  
	 * 		15.Customer name <BR> 
	 * 		16.Worker Name <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		//#CM7192
		// Connection
		Connection wConn = null;

		try
		{
			//#CM7193
			// Set the focus for the Consignor code.
			setFocus(txt_ConsignorCode);

			//#CM7194
			// Clear Consignor code (preset)
			txt_RConsignorCode.setText("");
			//#CM7195
			// Clear Consignor Name (preset)
			txt_RConsignorName.setText("");
			//#CM7196
			// Clear preset area
			lst_SNoPlanRtrvlQtyIqr.clearRow();

			//#CM7197
			// Execute input check.
			//#CM7198
			// Consignor code
			txt_ConsignorCode.validate();
			//#CM7199
			// Start picking date
			txt_StartRetrievalDate.validate(false);
			//#CM7200
			// End picking date
			txt_EndRetrievalDate.validate(false);
			//#CM7201
			// Item code
			txt_ItemCode.validate(false);
			//#CM7202
			// Customer code
			txt_CustomerCode.validate(false);

			//#CM7203
			// Check picking data size
			if (!StringUtil.isBlank(txt_StartRetrievalDate.getDate()) && !StringUtil.isBlank(txt_EndRetrievalDate.getDate()))
			{
				if (txt_StartRetrievalDate.getDate().after(txt_EndRetrievalDate.getDate()))
				{
					//#CM7204
					// Display the error message and close it.
					//#CM7205
					// 6023107=Starting picking date must precede the end picking date.
					message.setMsgResourceKey("6023107");
					return;
				}
			}

			//#CM7206
			// Generate stock parameter class instance.
			StockControlParameter wParam = new StockControlParameter();

			//#CM7207
			// Set the schedule parameter.
			//#CM7208
			// Consignor code
			wParam.setConsignorCode(txt_ConsignorCode.getText());
			//#CM7209
			// Start picking date
			wParam.setFromWorkDate(WmsFormatter.toParamDate(txt_StartRetrievalDate.getDate()));
			//#CM7210
			// End picking date
			wParam.setToWorkDate(WmsFormatter.toParamDate(txt_EndRetrievalDate.getDate()));
			//#CM7211
			// Item code
			wParam.setItemCode(txt_ItemCode.getText());
			//#CM7212
			// Customer code
			wParam.setCustomerCode(txt_CustomerCode.getText());
			//#CM7213
			// Case/Piece division
			if (rdo_CpfAll.getChecked())
			{
				//#CM7214
				// All
				wParam.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_ALL);
			}
			else if (rdo_CpfCase.getChecked())
			{
				//#CM7215
				// Case
				wParam.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_CpfPiece.getChecked())
			{
				//#CM7216
				// Piece
				wParam.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_PIECE);
			}
			else if (rdo_CpfAppointOff.getChecked())
			{
				//#CM7217
				// None
				wParam.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_NOTHING);
			}

			//#CM7218
			// Display Sequence
			if (rdo_ItemCode.getChecked())
			{
				//#CM7219
				// Item code sequence
				wParam.setDspOrder(StockControlParameter.DSPORDER_ITEM);
			}
			else if (rdo_CustomerCodeItemCode.getChecked())
			{
				//#CM7220
				// Customer code-Item code sequence
				wParam.setDspOrder(StockControlParameter.DSPORDER_CUSTOMER_ITEM);
			}

			//#CM7221
			// Obtain the connection.
			wConn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM7222
			// Generate instance for Unplanned Picking Result inquiry schedule.
			WmsScheduler wSchedule = new NoPlanRetrievalQtyInqirySCH();
			//#CM7223
			// Start schedule.
			StockControlParameter[] wViewParam = (StockControlParameter[]) wSchedule.query(wConn, wParam);

			//#CM7224
			// Close the process when some error occurred or no display data was found.
			if (wViewParam == null || wViewParam.length == 0)
			{
				message.setMsgResourceKey(wSchedule.getMessage());
				return;
			}

			//#CM7225
			// Display the data if obtaining the display data when the schedule normally completed.
			//#CM7226
			// Consignor code (preset)
			txt_RConsignorCode.setText(wViewParam[0].getConsignorCode());
			//#CM7227
			// Consignor name (preset)
			txt_RConsignorName.setText(wViewParam[0].getConsignorName());

			//#CM7228
			// Obtain the item (count) name needed to display in the balloon.			
			//#CM7229
			// Item name
			String wItenName = DisplayText.getText("LBL-W0103");
			//#CM7230
			// Case ITF
			String wCaseItf = DisplayText.getText("LBL-W0010");
			//#CM7231
			// Bundle ITF
			String wBundleItf = DisplayText.getText("LBL-W0006");
			//#CM7232
			// Customer Code
			String wCustomerCode = DisplayText.getText("LBL-W0324");
			//#CM7233
			// Customer name
			String wCustomerName = DisplayText.getText("LBL-W0036");
			//#CM7234
			// Worker Code
			String wWorkerCode = DisplayText.getText("LBL-W0325");
			//#CM7235
			// Worker Name
			String wWorkerName = DisplayText.getText("LBL-W0276");

			//#CM7236
			// LBL-W0719=----
			String noDisp = DisplayText.getText("LBL-W0719");


			String title_AreaTypeName = DisplayText.getText("LBL-W0569");			

			//#CM7237
			// Set the value for the list cell.
			for (int i = 0; i < wViewParam.length; i++)
			{
				String workerCode = "";
				String workerName = "";
				//#CM7238
				// Worker Code
				//#CM7239
				// Worker Name
				if (wViewParam[i].getSystemDiskKey() == StockControlParameter.SYSTEM_DISC_KEY_ASRS)
				{
					workerCode = noDisp;
					workerName = noDisp;
				}
				else
				{
					workerCode = wViewParam[i].getWorkerCode();
					workerName = wViewParam[i].getWorkerName();
				}
				//#CM7240
				// Add line
				lst_SNoPlanRtrvlQtyIqr.addRow();
				lst_SNoPlanRtrvlQtyIqr.setCurrentRow(i + 1);

				//#CM7241
				// Set the search result for each cell.
				//#CM7242
				// (1st line)
				//#CM7243
				// Picking date
				lst_SNoPlanRtrvlQtyIqr.setValue(
					1,
					WmsFormatter.toDispDate(wViewParam[i].getRetrievalDateString(), this.getHttpRequest().getLocale()));
				//#CM7244
				// Item code
				lst_SNoPlanRtrvlQtyIqr.setValue(2, wViewParam[i].getItemCode());
				//#CM7245
				// Division
				lst_SNoPlanRtrvlQtyIqr.setValue(3, wViewParam[i].getCasePieceFlagName());
				//#CM7246
				// Packing qty per case
				lst_SNoPlanRtrvlQtyIqr.setValue(4, WmsFormatter.getNumFormat(wViewParam[i].getEnteringQty()));
				//#CM7247
				// Reslt Case qty
				lst_SNoPlanRtrvlQtyIqr.setValue(5, WmsFormatter.getNumFormat(wViewParam[i].getResultCaseQty()));
				//#CM7248
				// Picking Location
				lst_SNoPlanRtrvlQtyIqr.setValue(6, WmsFormatter.toDispLocation(wViewParam[i].getLocationNo(), wViewParam[i].getSystemDiskKey()));
				//#CM7249
				// Expiry Date
				lst_SNoPlanRtrvlQtyIqr.setValue(7, wViewParam[i].getUseByDate());
				//#CM7250
				// Case ITF
				lst_SNoPlanRtrvlQtyIqr.setValue(8, wViewParam[i].getITF());
				//#CM7251
				// Customer code
				lst_SNoPlanRtrvlQtyIqr.setValue(9, wViewParam[i].getCustomerCode());
				//#CM7252
				// Worker Code
				lst_SNoPlanRtrvlQtyIqr.setValue(10, workerCode);

				//#CM7253
				//(2nd line)
				//#CM7254
				// Item name
				lst_SNoPlanRtrvlQtyIqr.setValue(11, wViewParam[i].getItemName());
				//#CM7255
				// packed qty per bundle
				lst_SNoPlanRtrvlQtyIqr.setValue(12, WmsFormatter.getNumFormat(wViewParam[i].getBundleEnteringQty()));
				//#CM7256
				// Result Piece Qty
				lst_SNoPlanRtrvlQtyIqr.setValue(13, WmsFormatter.getNumFormat(wViewParam[i].getResultPieceQty()));
				//#CM7257
				// Bundle ITF
				lst_SNoPlanRtrvlQtyIqr.setValue(14, wViewParam[i].getBundleITF());
				//#CM7258
				// Customer name
				lst_SNoPlanRtrvlQtyIqr.setValue(15, wViewParam[i].getCustomerName());
				//#CM7259
				// Worker Name
				lst_SNoPlanRtrvlQtyIqr.setValue(16, workerName);

				//#CM7260
				// Set the tool tip (balloon item (count).
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM7261
				// Item name
				toolTip.add(wItenName, wViewParam[i].getItemName());
				//#CM7262
				// Case ITF
				toolTip.add(wCaseItf, wViewParam[i].getITF());
				//#CM7263
				// Bundle ITF
				toolTip.add(wBundleItf, wViewParam[i].getBundleITF());
				//#CM7264
				// Customer code
				toolTip.add(wCustomerCode, wViewParam[i].getCustomerCode());
				//#CM7265
				// Customer name
				toolTip.add(wCustomerName, wViewParam[i].getCustomerName());
				//#CM7266
				// Worker Code
				toolTip.add(wWorkerCode, workerCode);
				//#CM7267
				// Worker Name
				toolTip.add(wWorkerName, workerName);
				//#CM7268
				// Area Name
				toolTip.add(title_AreaTypeName, wViewParam[i].getAreaName());


				lst_SNoPlanRtrvlQtyIqr.setToolTip(i + 1, toolTip.getText());
			}

			//#CM7269
			// Set the message.
			message.setMsgResourceKey(wSchedule.getMessage());

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));

		}
		finally
		{
			try
			{
				if (wConn != null)
				{
					//#CM7270
					// Close the connection
					wConn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
	}

	//#CM7271
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7272
	/** 
	 * Clicking on the Clear button invokes this. <BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Return the item (count) in the input area to the initial value. <BR>
	 *    <DIR>
	 *  Refer to <CODE>page_Load(ActionEvent e)</CODE>Method in regard with the initial value.<BR>
	 *    </DIR>
	 *    2.Set the cursor for Consignor code. <BR>
	 *    3.Maintain the contents of preset area. <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM7273
		// Execute initial setting of the input area.
		setInitDsp();
	}

	//#CM7274
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7275
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7276
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7277
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7278
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7279
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7280
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7281
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7282
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}
	//#CM7283
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanRtrvlQtyIqr_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7284
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanRtrvlQtyIqr_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7285
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanRtrvlQtyIqr_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7286
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanRtrvlQtyIqr_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM7287
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanRtrvlQtyIqr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7288
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanRtrvlQtyIqr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM7289
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanRtrvlQtyIqr_Click(ActionEvent e) throws Exception
	{
	}

}
//#CM7290
//end of class
