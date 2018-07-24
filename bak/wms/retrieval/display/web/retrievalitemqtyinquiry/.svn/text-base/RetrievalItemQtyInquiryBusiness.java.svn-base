// $Id: RetrievalItemQtyInquiryBusiness.java,v 1.2 2007/02/07 04:19:15 suresh Exp $

//#CM714336
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalitemqtyinquiry;

import java.util.Date;

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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievaldate.ListRetrievalDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalItemQtyInquirySCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM714337
/**
 * Designer : T.Yoshino<BR>
 * Maker : T.Kuroda<BR>
 * <BR>
 * Allow this class of screen to inquire the Item Picking Result. <BR>
 * Set the contents entered via screen for the parameter, and allow the schedule to search data to be displayed based on the parameter. <BR>
 * Receive data to be output to the preset area from the schedule and output it to the preset area. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process by clicking "Display" button (<CODE>btn_View_Click()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Set the contents entered via screen for the parameter, and allow the schedule to search data to be displayed based on the parameter. <BR>
 *   Receive data to be output to the preset area from the schedule and output it to the preset area. <BR>
 *   If no corresponding data is found, receive the <CODE>Parameter</CODE> array with number of elements equal to 0. Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 * <BR>
 *   [Parameter]  *Mandatory Input<BR>
 * <BR>
 *     <DIR>
 *     Consignor Code* <BR>
 *     Start Shipping Date  <BR>
 *     End Shipping Date  <BR>
 *     Item Code <BR>
 * 	   Case/Piece division <BR>
 *     Display order  <BR>
 *     </DIR>
 * <BR>
 *   [Returned data]  <BR>
 * <BR>
 *     <DIR>
 *    Consignor Code <BR>
 *    Consignor Name <BR>
 * 	  Picking Date <BR>
 * 	  Planned Picking Date <BR>
 *    Item Code <BR>
 *    Item Name <BR>
 *	  Case/Piece division (name)  <BR>
 *    Packed Qty per Case <BR>
 *    Packed qty per bundle <BR>
 *    Planned Work Case Qty <BR>
 *    Planned Work Piece Qty <BR>
 *    Work Result Case Qty  <BR>
 *    Work Result Piece Qty  <BR>
 *    Work Shortage Case Qty  <BR>
 *    Work Shortage Piece Qty  <BR>
 *    Picking Location  <BR>
 *    Expiry Date <BR>
 *    Case ITF <BR>
 *    Bundle ITF <BR>
 *    Worker Code <BR>
 *    Worker Name <BR>
 *     </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/25</TD><TD>T.Kuroda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:15 $
 * @author  $Author: suresh $
 */
public class RetrievalItemQtyInquiryBusiness extends RetrievalItemQtyInquiry implements WMSConstants
{
	//#CM714338
	// Class fields --------------------------------------------------

	//#CM714339
	// Class variables -----------------------------------------------

	//#CM714340
	// Class method --------------------------------------------------

	//#CM714341
	// Constructors --------------------------------------------------

	//#CM714342
	// Public methods ------------------------------------------------

    //#CM714343
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
			//#CM714344
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM714345
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM714346
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
	}
    
	//#CM714347
	/**
	 * Initialize the screen.  <BR>
	 * <BR>
	 *   <DIR>
	 *   Consignor Code [Consignor Code]  <BR>
	 *     <DIR>
	 *     (When Consignor of Result info is one.)  <BR>
	 *     </DIR>
	 *   Start Picking Date [None]  <BR>
	 *   End Picking Date [None]  <BR>
	 *   Item Code [None]  <BR>
	 *   Case/Piece division [All]  <BR>
	 *   Display order [Picking Date ]  <BR>
	 *     <DIR>
	 *     In the order of Picking Date [true] <BR>
	 *     In the order of Planned Picking Date [false] <BR>
	 *     </DIR>
	 *   </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		setInitDsp();
		
		//#CM714348
		// Consignor Code (Preset) 
		this.txt_RConsignorCode.setText("");
		//#CM714349
		// Consignor Name (Preset) 
		this.txt_RConsignorName.setText("");
		//#CM714350
		// Listcell 
		this.lst_SRetrievalQtyInquiry.clearRow();
	}

	//#CM714351
	/**
	 * Returning from a popup window invokes this method. 
	 * Override the page_DlgBack defined for Page. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		
		//#CM714352
		// Obtain the parameter selected in the listbox. 
		//#CM714353
		// Consignor Code
		String consignorCode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM714354
		// Start Picking Date
		Date startDate = WmsFormatter.toDate(param.getParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY)
								   			,this.getHttpRequest().getLocale());
		//#CM714355
		// End Picking Date
		Date endDate = WmsFormatter.toDate(param.getParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY)
											,this.getHttpRequest().getLocale());
		//#CM714356
		// Item Code
		String itemCode = param.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);
		
		//#CM714357
		// Set a value if not empty. 
		//#CM714358
		// Consignor Code
		if (!StringUtil.isBlank(consignorCode))
		{
			txt_ConsignorCode.setText(consignorCode);
		}
		
		//#CM714359
		// Start Picking Date
		if (!StringUtil.isBlank(startDate))
		{
		    this.txt_StartRetrievalDate.setDate(startDate);
		}
		//#CM714360
		// End Picking Date
		if (!StringUtil.isBlank(endDate))
		{
		    this.txt_EndRetrievalDate.setDate(endDate);
		}
		//#CM714361
		// Item Code
		if (!StringUtil.isBlank(itemCode))
		{
			this.txt_ItemCode.setText(itemCode);
		}
		//#CM714362
		// Move the cursor to the Consignor code. 
		setFocus(txt_ConsignorCode);
	}

	//#CM714363
	// Package methods -----------------------------------------------

	//#CM714364
	// Protected methods ---------------------------------------------

	//#CM714365
	// Private methods -----------------------------------------------
	//#CM714366
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
			//#CM714367
			// Move the cursor to the Consignor code. 
			setFocus(txt_ConsignorCode);
			
			//#CM714368
			// Consignor Code
			txt_ConsignorCode.setText("");
			//#CM714369
			// Start Picking Date
			this.txt_StartRetrievalDate.setText("");
			//#CM714370
			// End Picking Date
			this.txt_EndRetrievalDate.setText("");
			//#CM714371
			// Item Code
			this.txt_ItemCode.setText("");
			//#CM714372
			// Case/Piece division (All) 
			this.rdo_CpfCase.setChecked(false);
			this.rdo_CpfPiece.setChecked(false);
			this.rdo_CpfAppointOff.setChecked(false);
			this.rdo_CpfAll.setChecked(true);
			//#CM714373
			// Display order(Picking Date) 
			this.rdo_RetrievalPlanDate.setChecked(false);
			this.rdo_RetrievalDate.setChecked(true);
			
			//#CM714374
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalItemQtyInquirySCH();
			RetrievalSupportParameter param = (RetrievalSupportParameter)schedule.initFind(conn, null);
			
			//#CM714375
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
				//#CM714376
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
	
	//#CM714377
	// Event handler methods -----------------------------------------
	//#CM714378
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714379
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714380
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714381
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

	//#CM714382
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714383
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714384
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714385
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714386
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714387
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714388
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
	    //#CM714389
	    // Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		
		//#CM714390
		// Consignor Code
		param.setParameter(
		    ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		//#CM714391
		// Screen information 
		param.setParameter(
		    ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY,
		    RetrievalSupportParameter.SEARCHFLAG_RESULT);
		//#CM714392
		// For Item 
		param.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM714393
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do",
			param,
			"/progress.do");
	}

	//#CM714394
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lbl_StartRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714395
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_StartRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714396
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_StartRetrievalDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714397
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_StartRetrievalDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714398
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchStartRetrievalDate_Server(ActionEvent e) throws Exception
	{
	    
	}

	//#CM714399
	/** 
	 * Clicking on the Search Start Picking Date button invokes this. 
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Search Picking Date using this search condition. <BR>
	 * <BR>
	 *   [Parameter]  *Mandatory Input<BR>
	 * <BR>
	 *     <DIR>
	 *     Consignor Code <BR>
	 *     Start Picking Date <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchStartRetrievalDate_Click(ActionEvent e) throws Exception
	{
		//#CM714400
		// StartSet the search condition in the Search screen of Picking Date. 
		ForwardParameters param = new ForwardParameters();
		
		//#CM714401
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM714402
		// Start Picking Date
		param.setParameter(
			ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY,
			WmsFormatter.toParamDate(this.txt_StartRetrievalDate.getDate()));

		//#CM714403
		// Set the Start flag. 
		param.setParameter(
			ListRetrievalDateBusiness.RANGERETRIEVALDATE_KEY,
			RetrievalSupportParameter.RANGE_START);
		//#CM714404
		// For Item 
		param.setParameter(ListRetrievalDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM714405
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievaldate/ListRetrievalDate.do",
			param,
			"/progress.do");
	}

	//#CM714406
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714407
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lbl_EndRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714408
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_EndRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714409
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_EndRetrievalDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714410
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_EndRetrievalDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714411
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchEndRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714412
	/** 
	 * Clicking on the Search End Picking Date button invokes this. 
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Search Picking Date using this search condition. <BR>
	 * <BR>
	 *   [Parameter]  *Mandatory Input<BR>
	 * <BR>
	 *     <DIR>
	 *     Consignor Code <BR>
	 *     End Picking Date <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchEndRetrievalDate_Click(ActionEvent e) throws Exception
	{
	    //#CM714413
	    // Set the search condition in the Search screen of End Picking Date. 
		ForwardParameters param = new ForwardParameters();
		
		//#CM714414
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		//#CM714415
		// End Picking Date
		param.setParameter(
			ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY,
			WmsFormatter.toParamDate(this.txt_EndRetrievalDate.getDate()));

		//#CM714416
		// Set the Close flag. 
		param.setParameter(
			ListRetrievalDateBusiness.RANGERETRIEVALDATE_KEY,
			RetrievalSupportParameter.RANGE_END);
		//#CM714417
		// For Item 
		param.setParameter(ListRetrievalDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM714418
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievaldate/ListRetrievalDate.do",
			param,
			"/progress.do");
	}

	//#CM714419
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714420
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714421
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714422
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714423
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714424
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714425
	/** 
	 * Clicking on the Search Item Code button invokes this. 
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for Item using the search condition. <BR>
	 * <BR>
	 *   [Parameter]  *Mandatory Input<BR>
	 * <BR>
	 *     <DIR>
	 *     Consignor Code <BR>
	 *     Start Picking Date <BR>
	 *     End Picking Date <BR>
	 * 	   Item Code <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
	    //#CM714426
	    // Set the search condition in the Search screen of Picking destination. 
		ForwardParameters param = new ForwardParameters();
		
		//#CM714427
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		//#CM714428
		// Start Picking Date
		param.setParameter(
			ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY,
			WmsFormatter.toParamDate(this.txt_StartRetrievalDate.getDate()));
			
		//#CM714429
		// End Picking Date
		param.setParameter(
			ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY,
			WmsFormatter.toParamDate(this.txt_EndRetrievalDate.getDate()));
			
		//#CM714430
		// Item Code
		param.setParameter(
			ListRetrievalItemBusiness.ITEMCODE_KEY,
			this.txt_ItemCode.getText());
		
		//#CM714431
		// Screen information 
		param.setParameter(
			ListRetrievalItemBusiness.SEARCHITEM_KEY,
			RetrievalSupportParameter.SEARCHFLAG_RESULT);
		//#CM714432
		// For Item 
		param.setParameter(ListRetrievalItemBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM714433
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalitem/ListRetrievalItem.do",
			param,
			"/progress.do");
	}

	//#CM714434
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714435
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714436
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM714437
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714438
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM714439
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714440
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM714441
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714442
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM714443
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lbl_DspOrder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714444
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void rdo_RetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714445
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void rdo_RetrievalDate_Click(ActionEvent e) throws Exception
	{
	}

	//#CM714446
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void rdo_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714447
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void rdo_RetrievalPlanDate_Click(ActionEvent e) throws Exception
	{
	}

	//#CM714448
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714449
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
	 *       (Start Picking Date <= End Picking Date)  <BR>
	 *       </DIR>
     *     2. Start the schedule.  <BR>
     *       <DIR>
	 *       [Parameter]  *Mandatory Input<BR>
	 *       <BR>
	 *         <DIR>
	 *         Consignor Code* <BR>
	 *         Start Picking Date <BR>
	 *         End Picking Date <BR>
	 *         Item Code <BR>
	 * 		   Case/Piece division * <BR>
	 *         Display order * <BR>
	 *         </DIR>
	 *       </DIR>
     *     3. Display the schedule result in the preset area.  <BR>
     *     4. Set the cursor on the Consignor code.  <BR>
     * </DIR>
     * <BR>
     * <BR>
	 * Row No. list of listcell  <BR>
	 * <DIR>
	 *     1:Picking Date <BR>
	 *     2:Item Code <BR>
	 * 	   3: Division  <BR>
	 *     4:Packed Qty per Case <BR>
	 *     5:Planned Work Case Qty <BR>
	 *     6: Result Case Qty  <BR>
	 *     7:Shortage Case Qty <BR>
	 * 	   8: Picking Location  <BR>
	 *     9:Expiry Date <BR>
	 * 	  10:Case ITF <BR>
	 *    11:Worker Code <BR>
	 *    12:Planned Picking Date <BR>
	 *    13:Item Name <BR>
	 *    14:Packed qty per bundle <BR>
	 *    15:Planned Work Piece Qty <BR>
	 *    16: Result Piece Qty  <BR>
	 *    17:Shortage Piece Qty <BR>
	 *    18:Bundle ITF <BR>
	 *    19:Worker Name <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
	    Connection conn = null;
		
		try
		{
			//#CM714450
			// Move the cursor to the Consignor code. 
			setFocus(txt_ConsignorCode);
			
			//#CM714451
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM714452
			// Clear the Preset area. 
			this.lst_SRetrievalQtyInquiry.clearRow();
			this.txt_RConsignorCode.setText("");
			this.txt_RConsignorName.setText("");
						
			//#CM714453
			// Check for input. (format, mandatory, prohibited characters) 
			//#CM714454
			// Consignor Code
			this.txt_ConsignorCode.validate();
			//#CM714455
			// Start Picking Date
			this.txt_StartRetrievalDate.validate(false);
			//#CM714456
			// End Picking Date
			this.txt_EndRetrievalDate.validate(false);
			//#CM714457
			// Item Code
			this.txt_ItemCode.validate(false);
			
			//#CM714458
			// Check the value of picking date for precedence. 
			if(!StringUtil.isBlank(this.txt_StartRetrievalDate.getDate()) && !StringUtil.isBlank(this.txt_EndRetrievalDate.getDate()))
			{
				
				if (this.txt_StartRetrievalDate.getDate().after(this.txt_EndRetrievalDate.getDate()))
				{
					//#CM714459
					// Display the error message and close it. 
					//#CM714460
					// 6023107=Starting picking date must precede the end picking date. 
					message.setMsgResourceKey("6023107");
					return ;
				}
			}
			
			//#CM714461
			// Set the schedule parameter. 
			RetrievalSupportParameter param = new RetrievalSupportParameter();
			//#CM714462
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM714463
			// Start Picking Date
			param.setFromRetrievalDate(WmsFormatter.toParamDate(this.txt_StartRetrievalDate.getDate()));
			//#CM714464
			// End Picking Date
			param.setToRetrievalDate(WmsFormatter.toParamDate(this.txt_EndRetrievalDate.getDate()));
			//#CM714465
			// Item Code
			param.setItemCode(this.txt_ItemCode.getText());
			
			//#CM714466
			// Case/Piece division 
			if ( this.rdo_CpfAll.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
			}
			else if (this.rdo_CpfCase.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
			}
			else if (this.rdo_CpfPiece.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
			}
			else if (this.rdo_CpfAppointOff.getChecked())
			{
				param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
			}
			
			//#CM714467
			// Display order 
			if(this.rdo_RetrievalDate.getChecked())
			{
			    param.setDisplayOrder(RetrievalSupportParameter.DISPLAY_ORDER_WORK_DATE);
			}
			else if (this.rdo_RetrievalPlanDate.getChecked())
			{
			    param.setDisplayOrder(RetrievalSupportParameter.DISPLAY_ORDER_PLAN_DATE);
			}

			//#CM714468
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalItemQtyInquirySCH();
			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[])schedule.query(conn, param);
			
			//#CM714469
			// Close the process when some error occurred or no display data was found. 
			if( viewParam == null || viewParam.length == 0 )
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			
			//#CM714470
			// Display the data if obtained the display data when the schedule normally completed. 
			//#CM714471
			// Consignor Code (Preset) 
			this.txt_RConsignorCode.setText(viewParam[0].getConsignorCode());
			//#CM714472
			// Consignor Name (Preset) 
			this.txt_RConsignorName.setText(viewParam[0].getConsignorName());

			//#CM714473
			// Item Name
			String label_itemname = DisplayText.getText("LBL-W0103");
			//#CM714474
			// Worker Code
			String label_workercode = DisplayText.getText("LBL-W0274");
			//#CM714475
			// Worker Name
			String label_workername = DisplayText.getText("LBL-W0276");
			//#CM714476
			// Picking Location 
			String label_retrievallocation = DisplayText.getText("LBL-W0172");
			//#CM714477
			// Expiry Date
			String label_usebydate = DisplayText.getText("LBL-W0270");
			//#CM714478
			// Case ITF
			String label_caseitf = DisplayText.getText("LBL-W0338");
			//#CM714479
			// Bundle ITF
			String label_bundleitf = DisplayText.getText("LBL-W0337");
			
			//#CM714480
			// LBL-W0719=----
			String noDisp = DisplayText.getText("LBL-W0719");
			String title_AreaTypeName = DisplayText.getText("LBL-W0569");


			//#CM714481
			// Set a value in the listcell. 
			for(int i = 0; i < viewParam.length; i++)
			{
				String workerCode = "";
				String workerName = "";
				//#CM714482
				// Worker Code
				//#CM714483
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

				//#CM714484
				// Add a line. 
				this.lst_SRetrievalQtyInquiry.addRow();
				this.lst_SRetrievalQtyInquiry.setCurrentRow(i + 1);
				
				int col = 1;
				
				//#CM714485
				// Set the search result in each cell. 
				//#CM714486
				// (1st row) 
				//#CM714487
				// Picking Date
				this.lst_SRetrievalQtyInquiry.setValue(col++, WmsFormatter.toDispDate(viewParam[i].getRetrievalDate(),this.getHttpRequest().getLocale()));
				//#CM714488
				// Item Code
				this.lst_SRetrievalQtyInquiry.setValue(col++, viewParam[i].getItemCode());
				//#CM714489
				// Division 
				this.lst_SRetrievalQtyInquiry.setValue(col++, viewParam[i].getCasePieceflgName());
				//#CM714490
				// Packed Qty per Case
				this.lst_SRetrievalQtyInquiry.setValue(col++, WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));
				//#CM714491
				// Planned Work Case Qty
				this.lst_SRetrievalQtyInquiry.setValue(col++, WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				//#CM714492
				// Result Case Qty 
				this.lst_SRetrievalQtyInquiry.setValue(col++, WmsFormatter.getNumFormat(viewParam[i].getResultCaseQty()));
				//#CM714493
				// Shortage Case Qty
				this.lst_SRetrievalQtyInquiry.setValue(col++, WmsFormatter.getNumFormat(viewParam[i].getShortageCaseQty()));
				//#CM714494
				// Picking Location  
				this.lst_SRetrievalQtyInquiry.setValue(col++, 
							WmsFormatter.toDispLocation(
							viewParam[i].getRetrievalLocation(), viewParam[i].getSystemDiscKey()));
				//#CM714495
				// Expiry Date
				this.lst_SRetrievalQtyInquiry.setValue(col++, viewParam[i].getUseByDate());
				//#CM714496
				// Case ITF
				this.lst_SRetrievalQtyInquiry.setValue(col++, viewParam[i].getITF());
				//#CM714497
				// Worker Code
				this.lst_SRetrievalQtyInquiry.setValue(col++, workerCode);
				
				//#CM714498
				// (2nd row) 
				//#CM714499
				// Planned Picking Date
				this.lst_SRetrievalQtyInquiry.setValue(col++, WmsFormatter.toDispDate(viewParam[i].getRetrievalPlanDate(),this.getHttpRequest().getLocale()));
				//#CM714500
				// Item Name
				this.lst_SRetrievalQtyInquiry.setValue(col++, viewParam[i].getItemName());
				//#CM714501
				// Packed qty per bundle
				this.lst_SRetrievalQtyInquiry.setValue(col++, WmsFormatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
				//#CM714502
				// Planned Work Piece Qty
				this.lst_SRetrievalQtyInquiry.setValue(col++, WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
				//#CM714503
				// Result Piece Qty 
				this.lst_SRetrievalQtyInquiry.setValue(col++, WmsFormatter.getNumFormat(viewParam[i].getResultPieceQty()));
				//#CM714504
				// Shortage Piece Qty
				this.lst_SRetrievalQtyInquiry.setValue(col++, WmsFormatter.getNumFormat(viewParam[i].getShortagePieceQty()));
				//#CM714505
				// Bundle ITF
				this.lst_SRetrievalQtyInquiry.setValue(col++, viewParam[i].getBundleITF());
				//#CM714506
				// Worker Name
				this.lst_SRetrievalQtyInquiry.setValue(col++, workerName);
				
				//#CM714507
				// Set the tool tip. 
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM714508
				// Item Name
				toolTip.add(label_itemname, viewParam[i].getItemName());
				//#CM714509
				// Picking Location 
				toolTip.add(label_retrievallocation,
							WmsFormatter.toDispLocation(
							viewParam[i].getRetrievalLocation(), viewParam[i].getSystemDiscKey()));
				//#CM714510
				// Expiry Date
				toolTip.add(label_usebydate, viewParam[i].getUseByDate());
				//#CM714511
				// Case ITF
				toolTip.add(label_caseitf, viewParam[i].getITF());
				//#CM714512
				// Bundle ITF
				toolTip.add(label_bundleitf, viewParam[i].getBundleITF());
				//#CM714513
				// Worker Code
				toolTip.add(label_workercode, workerCode);
				//#CM714514
				// Worker Name
				toolTip.add(label_workername, workerName);
				
				//#CM714515
				// Area Name 
				toolTip.add(title_AreaTypeName, viewParam[i].getRetrievalAreaName());
				
					
				this.lst_SRetrievalQtyInquiry.setToolTip(i+1, toolTip.getText());				
			}
			
			//#CM714516
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
				//#CM714517
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

	//#CM714518
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714519
	/** 
	 * Clicking on the Clear button invokes this.  <BR>
	 * <BR>
	 * Summary: Clears the input area.  <BR>
	 * </BR>
	 * <DIR>
	 *     1. Return the field item in the input area to the initial value. 
	 *     For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method. 
	 * </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
	    setInitDsp();
	}

	//#CM714520
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714521
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714522
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714523
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714524
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714525
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714526
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714527
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714528
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714529
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRetrievalQtyInquiry_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714530
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRetrievalQtyInquiry_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714531
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRetrievalQtyInquiry_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714532
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRetrievalQtyInquiry_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM714533
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRetrievalQtyInquiry_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714534
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRetrievalQtyInquiry_Change(ActionEvent e) throws Exception
	{
	}

	//#CM714535
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRetrievalQtyInquiry_Click(ActionEvent e) throws Exception
	{
	}
}
//#CM714536
//end of class
