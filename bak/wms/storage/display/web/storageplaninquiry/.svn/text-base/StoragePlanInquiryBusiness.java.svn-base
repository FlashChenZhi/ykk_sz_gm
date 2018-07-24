// $Id: StoragePlanInquiryBusiness.java,v 1.2 2006/12/07 08:57:16 suresh Exp $

//#CM572238
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.storageplaninquiry;

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
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplandate.ListStoragePlanDateBusiness;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;
import jp.co.daifuku.wms.storage.schedule.StoragePlanInquirySCH;

//#CM572239
/**
 * Designer : H.Murakado <BR>
 * Maker : H.Murakado <BR>
 * <BR>
 * The Storage Plan inquiry screen class. <BR>
 * The content input from the screen is set in Parameter, and the schedule retrieves data for the display based on the Parameter. <BR>
 * Receive Preset area Output data from the schedule and output it to Preset area.<BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Display button is pressed (<CODE>btn_View_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 *   The content input from the screen is set in Parameter, and the schedule retrieves data for the display based on the Parameter. <BR>
 *   Receive empty array of <CODE>Parameter</CODE> when pertinent data is not found. Moreover, receive null when the condition error etc. occur. <BR>
 * <BR>
 *   [Parameter] *Mandatory input<BR>
 * <BR>
 *     <DIR>
 *     Consignor Code* <BR>
 *     Start Storage plan date <BR>
 *     End Storage plan date <BR>
 *     Item Code <BR>
 *     Case/Piece flag <BR>
 *     Work Status <BR>
 *     </DIR>
 * <BR>
 *   [Return Data] <BR>
 * <BR>
 *    <DIR>
 *    Consignor Code <BR>
 *    Consignor Name <BR>
 *    Storage plan date <BR>
 *    Item Code <BR>
 *    Item Name <BR>
 *    Case/Piece flag <BR>
 *    Case item Storage Location <BR>
 *    Piece item Storage Location <BR>
 *    Packed qty per case <BR>
 *    Packed qty per bundle <BR>
 *    Host Plan Case qty <BR>
 *    Host Plan Piece qty <BR>
 *    Result Case qty <BR>
 *    Result Piece qty <BR>
 *    Status <BR>
 *    CaseITF <BR>
 *    Bundle ITF <BR>
 *    </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>H.Murakado</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:16 $
 * @author  $Author: suresh $
 */

public class StoragePlanInquiryBusiness extends StoragePlanInquiry implements WMSConstants
{
	//#CM572240
	// Class fields --------------------------------------------------

	//#CM572241
	// Class variables -----------------------------------------------

	//#CM572242
	// Class method --------------------------------------------------

	//#CM572243
	// Constructors --------------------------------------------------

	//#CM572244
	// Public methods ------------------------------------------------

	//#CM572245
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Outline:Display initial data in the screen. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Initialize the input area. <BR>
	 * 		2.Clear the list cell and Each Read only Item of Preset area. <BR>
	 * 		<BR>
	 * 		Item[Initial value] <BR>
	 * 		<DIR>
	 * 			Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Start Storage plan date[None] <BR>
	 * 			End Storage plan date[None] <BR>
	 * 			Item Code[None] <BR>
	 * 			Case/Piece flag[All] <BR>
	 * 			Work Status[true]<BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		setInitDsp();
		
		//#CM572246
		// Consignor Code (Preset) 
		txt_SRConsignorCode.setText("");
		//#CM572247
		// Consignor Name (Preset) 
		txt_SRConsignorName.setText("");
		//#CM572248
		// List cell
		lst_SStoragePlanInquiry.clearRow();
	}

	//#CM572249
	/**
	 * It is called before the call of each control event. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions.  
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM572250
			//Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM572251
			//Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM572252
			//Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
	}

	//#CM572253
	/**
	 * When returning from the pop up window, this Method is called. <BR>
	 * Override the <CODE>page_DlgBack</CODE> set in <CODE>Page</CODE>.<BR>
	 * <BR>
	 * Outline:Acquire and set Return Data of the retrieval screen. <BR>
	 * <BR>
	 * <DIR>
	 *      1.Acquire the value returned from the retrieval screen of pop up. <BR>
	 *      2.Set it on the screen when the value is not empty. <BR>
	 *      3.Set the cursor in Consignor Code. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		
		//#CM572254
		// Parameter selected from list box is acquired. 
		//#CM572255
		// Consignor Code
		String consignorcode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);		
		//#CM572256
		// Start Storage plan date
		Date startdate = WmsFormatter.toDate(param.getParameter(ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY)
											,this.getHttpRequest().getLocale());
		//#CM572257
		// End Storage plan date
		Date enddate = WmsFormatter.toDate(param.getParameter(ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY)
											,this.getHttpRequest().getLocale());
		//#CM572258
		// Item Code
		String itemcode = param.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);

		//#CM572259
		// Set the value when it is not empty. 

		//#CM572260
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM572261
		// Start Storage plan date
		if (!StringUtil.isBlank(startdate))
		{
			txt_StrtStrgPlanDate.setDate(startdate);
		}
		//#CM572262
		// End Storage plan date
		if (!StringUtil.isBlank(enddate))
		{
			txt_EdStrgPlanDate.setDate(enddate);
		}
		//#CM572263
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		
		//#CM572264
		// Cursor transition to Consignor Code
		setFocus(txt_ConsignorCode);
	}


	//#CM572265
	// Package methods -----------------------------------------------

	//#CM572266
	// Protected methods ---------------------------------------------

	//#CM572267
	// Private methods -----------------------------------------------
	//#CM572268
	/**
	 * Method to acquire Consignor Code from the schedule.  <BR>
	 * <BR>
	 * Outline : Return Consignor Code acquired from the schedule. <BR>
	 * <DIR>
	 *   1.Return a pertinent shipper when there is only one Consignor Code. Return null when it is not so.  <BR>
	 * </DIR>
	 * <BR>
	 * @return Consignor Code
	 * @throws Exception It reports on all exceptions. 
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM572269
			// Connection acquisition	
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM572270
			// Acquire Consignor Code from the schedule. 
			WmsScheduler schedule = new StoragePlanInquirySCH();
			param = (StorageSupportParameter) schedule.initFind(conn, param);

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
				//#CM572271
				// Close the connection
				if (conn != null)
				{
					conn.rollback();
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
	
	//#CM572272
	/**
	 * Clear the Input area.<BR>
	 * <BR>
	 * Outline : The cursor is set in Consignor Code, and Clears each Item of Input area. <BR>
	 * <BR>
	 * <DIR>
	 * 		Item[Initial value] <BR>
	 * 		<DIR>
	 * 			Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Start Storage plan date[None] <BR>
	 * 			End Storage plan date[None] <BR>
	 * 			Item Code[None] <BR>
	 * 			Case/Piece flag[All] <BR>
	 * 			Work Status[true]<BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setInitDsp() throws Exception
	{
		Connection conn = null;
		
		try
		{
			//#CM572273
			// Consignor Code
			txt_ConsignorCode.setText(getConsignorCode());
			//#CM572274
			// Start Storage plan date
			txt_StrtStrgPlanDate.setText("");
			//#CM572275
			// End Storage plan date
			txt_EdStrgPlanDate.setText("");
			//#CM572276
			// Item Code
			txt_ItemCode.setText("");
			//#CM572277
			// Case/Piece flag
			rdo_CpfAll.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);
			rdo_CpfCasePiece.setChecked(false);
			//#CM572278
			// Work Status
			pul_WorkStatusStorage.setSelectedIndex(0);

			//#CM572279
			// Cursor transition to Consignor Code
			setFocus(txt_ConsignorCode);
			
			//#CM572280
			// Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new StoragePlanInquirySCH();
			StorageSupportParameter param = (StorageSupportParameter)schedule.initFind(conn, null);
			
			//#CM572281
			// Display initially when Consignor Code is one. 
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
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
				//#CM572282
				// Close Connection
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


	//#CM572283
	// Event handler methods -----------------------------------------
	//#CM572284
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572285
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572286
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572287
	/**
	 * It is called when the menu button is pressed.<BR>
	 * <BR>
	 * Outline:Change to the menu panel.<BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM572288
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572289
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572290
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572291
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572292
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM572293
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572294
	/** 
	 * It is called when the retrieval of Consignor Code button is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Consignor list box by the search condition.<BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]<BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		//#CM572295
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		
		//#CM572296
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,	
			txt_ConsignorCode.getText());
		
		//#CM572297
		// Screen information
		param.setParameter(
			ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY,
			StorageSupportParameter.SEARCH_STORAGE_PLAN);
								
		//#CM572298
		// Work Status(Stand by, Working, Partially Completed, and Completed of Storage Plan information)
		String[] search = new String[4];
		search[0] = new String(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(StorageSupportParameter.STATUS_FLAG_WORKING);
		search[2] = new String(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		search[3] = new String(StorageSupportParameter.STATUS_FLAG_COMPLETION);
		param.setParameter(ListStorageConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		
		//#CM572299
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageconsignor/ListStorageConsignor.do",
			param,
			"/progress.do");

	}

	//#CM572300
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrtStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572301
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572302
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtStrgPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572303
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtStrgPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572304
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrtStrg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572305
	/** 
	 * Start It is called when the retrieval button of Storage plan date is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Storage plan date list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Start Storage plan date <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PSearchStrtStrg_Click(ActionEvent e) throws Exception
	{
		
		//#CM572306
		// Start Set the search condition on the Storage plan date retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		

		//#CM572307
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM572308
		// Start Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtStrgPlanDate.getDate()));

		//#CM572309
		// Set Start flag
		param.setParameter(
			ListStoragePlanDateBusiness.RANGESTORAGEPLANDATE_KEY,
			StorageSupportParameter.RANGE_START);
			
		//#CM572310
		// Retrieval flag
		param.setParameter(ListStoragePlanDateBusiness.SEARCHSTORAGEPLANDATE_KEY,
			StorageSupportParameter.SEARCH_STORAGE_PLAN);
			
		//#CM572311
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageplandate/ListStoragePlanDate.do",
			param,
			"/progress.do");

	}

	//#CM572312
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromToStrg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572313
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndStoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572314
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572315
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdStrgPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572316
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdStrgPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572317
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEdStrg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572318
	/** 
	 * End It is called when the retrieval button of Storage plan date is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Storage plan date list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       End Storage plan date <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PSearchEdStrg_Click(ActionEvent e) throws Exception
	{

		//#CM572319
		// End Set the search condition on the Storage plan date retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		
		//#CM572320
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		//#CM572321
		// End Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdStrgPlanDate.getDate()));
			
		//#CM572322
		// Set the End flag
		param.setParameter(
			ListStoragePlanDateBusiness.RANGESTORAGEPLANDATE_KEY,
			StorageSupportParameter.RANGE_END);
			
		//#CM572323
		// Retrieval flag
		param.setParameter(ListStoragePlanDateBusiness.SEARCHSTORAGEPLANDATE_KEY, 
			StorageSupportParameter.SEARCH_STORAGE_PLAN);
			
		//#CM572324
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageplandate/ListStoragePlanDate.do",
			param,
			"/progress.do");

	}

	//#CM572325
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572326
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572327
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572328
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572329
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM572330
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItem_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572331
	/** 
	 * It is called when the retrieval button of Item Code is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the item list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]<BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Start Storage plan date <BR>
	 *       End Storage plan date <BR>
	 *       Item Code <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PSearchItem_Click(ActionEvent e) throws Exception
	{

		//#CM572332
		// Set the search condition on the Item retrieval screen. 
		ForwardParameters param = new ForwardParameters();

		//#CM572333
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		//#CM572334
		// Start Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtStrgPlanDate.getDate()));
			
		//#CM572335
		// End Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdStrgPlanDate.getDate()));
			
		//#CM572336
		// Item Code
		param.setParameter(
			ListStorageItemBusiness.ITEMCODE_KEY,
			txt_ItemCode.getText());
			
		//#CM572337
		// Screen information
		param.setParameter(
			ListStorageItemBusiness.SEARCHITEM_KEY,
			StorageSupportParameter.SEARCH_STORAGE_PLAN);
						
		//#CM572338
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageitem/ListStorageItem.do",
			param,
			"/progress.do");
		
	}

	//#CM572339
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572340
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572341
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM572342
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572343
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM572344
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572345
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM572346
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572347
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM572348
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCasePiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572349
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCasePiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM572350
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572351
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatusStorage_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572352
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatusStorage_Change(ActionEvent e) throws Exception
	{
	}

	//#CM572353
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572354
	/** 
	 * It is called when the display button is pressed.<BR>
	 * <BR>
	 * Outline : Input Item of Input area is set in Parameter, and Display the retrieval result in Preset area.  <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the cursor in Consignor Code. <BR>
	 * 		2.Check input Item of Input area. (Mandatory input, Number of characters, Character attribute)<BR>
	 * 		3.Start Schedule.<BR>
	 * 		4.Display the result of the schedule in Preset area. <BR>
	 * 		5.Set balloon information. <BR>
	 * 		<BR>
	 * 		<DIR>
	 * 			[Parameter] *Mandatory input<BR>
	 * 			<BR>
	 * 			<DIR>
	 * 				Consignor Code*<BR>
	 * 				Start Storage plan date<BR>
	 * 				End Storage plan date<BR>
	 * 				Item Code<BR>
	 * 				Case/Piece flag*<BR>
	 * 				Work Status*<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{


		Connection conn = null;
		
		try
		{
			//#CM572355
			// Cursor transition to Consignor Code
			setFocus(txt_ConsignorCode);
			
			//#CM572356
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM572357
			// Clear the Preset area.
			lst_SStoragePlanInquiry.clearRow();
			txt_SRConsignorCode.setText("");
			txt_SRConsignorName.setText("");
			
			//#CM572358
			// Do the input check (Format, Mandatory, Restricted characters) 
			//#CM572359
			// Consignor Code
			txt_ConsignorCode.validate();
			//#CM572360
			// Start Storage plan date
			txt_StrtStrgPlanDate.validate(false);
			//#CM572361
			// End Storage plan date
			txt_EdStrgPlanDate.validate(false);
			//#CM572362
			// Item Code
			txt_ItemCode.validate(false);
			//#CM572363
			// Work Status
			pul_WorkStatusStorage.validate(false);
			//#CM572364
			// Big and small check of Storage plan date. 
			if (txt_StrtStrgPlanDate.getDate() != null && txt_EdStrgPlanDate.getDate() != null)
			{

				if (txt_StrtStrgPlanDate.getDate().after(txt_EdStrgPlanDate.getDate()))
				{
					//#CM572365
					// Display error Message, and finish.
					//#CM572366
					// 6023199 = Starting planned storage date must precede the end planned storage date.
					message.setMsgResourceKey("6023199");
					return ;
				}
			}
			
			//#CM572367
			// Set schedule Parameter. 
			StorageSupportParameter param = new StorageSupportParameter();
			
			//#CM572368
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM572369
			// Start Storage plan date
			param.setFromStoragePlanDate(WmsFormatter.toParamDate(txt_StrtStrgPlanDate.getDate()));
			//#CM572370
			// End Storage plan date
			param.setToStoragePlanDate(WmsFormatter.toParamDate(txt_EdStrgPlanDate.getDate()));
			//#CM572371
			// Item Code
			param.setItemCode(txt_ItemCode.getText());

			//#CM572372
			// Case/Piece flag
			//#CM572373
			// All
			if (rdo_CpfAll.getChecked())
			{
				param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_ALL);
			}
			//#CM572374
			// Case
			else if (rdo_CpfCase.getChecked())
			{
				param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_CASE);
			}
			//#CM572375
			// Piece
			else if (rdo_CpfPiece.getChecked())
			{
				param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_PIECE);
			}
			//#CM572376
			// Unspecified
			else if (rdo_CpfAppointOff.getChecked())
			{
				param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
			}
			//#CM572377
			// Case , Piece
			else if (rdo_CpfCasePiece.getChecked())
			{
				param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_MIXED);
			}
			//#CM572378
			// Work Status
			//#CM572379
			// All
			if (pul_WorkStatusStorage.getSelectedIndex() == 0)
			{
				param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_ALL);
			}
			//#CM572380
			// Stand by
			else if (pul_WorkStatusStorage.getSelectedIndex() == 1)
			{
				param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
			}
			//#CM572381
			// Working
			else if (pul_WorkStatusStorage.getSelectedIndex() == 2)
			{
				param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_WORKING);
			}
			//#CM572382
			// Partially Completed
			else if (pul_WorkStatusStorage.getSelectedIndex() == 3)
			{
				param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
			}
			//#CM572383
			// Completed
			else if (pul_WorkStatusStorage.getSelectedIndex() == 4)
			{
				param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_COMPLETION);
			}

			//#CM572384
			// Start scheduling.
			WmsScheduler schedule = new StoragePlanInquirySCH();
			StorageSupportParameter[] viewParam = (StorageSupportParameter[])schedule.query(conn, param);
			
			//#CM572385
			// End processing when some errors occur or there is no display data. 
			if( viewParam == null || viewParam.length == 0 )
			{
				//#CM572386
				// Close Connection
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			
			//#CM572387
			// Display it when the schedule is normally completed and the display data is acquired. 
			//#CM572388
			// Consignor Code (Preset) 
			txt_SRConsignorCode.setText(viewParam[0].getConsignorCode());
			//#CM572389
			// Consignor Name (Preset) 
			txt_SRConsignorName.setText(viewParam[0].getConsignorName());

			//#CM572390
			// Item Name
			String label_itemname = DisplayText.getText("LBL-W0103");
			//#CM572391
			// CaseITF
			String label_caseitf = DisplayText.getText("LBL-W0010");
			//#CM572392
			// Bundle ITF
			String label_bundleitf = DisplayText.getText("LBL-W0006");
						
			//#CM572393
			// Set the value in the list cell. 
			for(int i = 0; i < viewParam.length; i++)
			{

				//#CM572394
				// Add row
				lst_SStoragePlanInquiry.addRow();
				lst_SStoragePlanInquiry.setCurrentRow(i + 1);
	
				int j = 1;
				
				//#CM572395
				// Set the retrieval result in each cell. 
				//#CM572396
				//  (First row) 
				//#CM572397
				// Storage plan date
				lst_SStoragePlanInquiry.setValue(j++, WmsFormatter.toDispDate(viewParam[i].getStoragePlanDate(),
																				this.getHttpRequest().getLocale()));
				//#CM572398
				// Item Code
				lst_SStoragePlanInquiry.setValue(j++, viewParam[i].getItemCode());
				//#CM572399
				// Flag
				lst_SStoragePlanInquiry.setValue(j++, viewParam[i].getCasePieceflgName());
				//#CM572400
				// CaseStorage Location
				lst_SStoragePlanInquiry.setValue(j++, viewParam[i].getCaseLocation());
				//#CM572401
				// Packed qty per case
				lst_SStoragePlanInquiry.setValue(j++, Formatter.getNumFormat(viewParam[i].getEnteringQty()));
				//#CM572402
				// Host Plan Case qty
				lst_SStoragePlanInquiry.setValue(j++, Formatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				//#CM572403
				// Result Case qty
				lst_SStoragePlanInquiry.setValue(j++, Formatter.getNumFormat(viewParam[i].getResultCaseQty()));
				//#CM572404
				// Status
				lst_SStoragePlanInquiry.setValue(j++, viewParam[i].getWorkStatusName());
				//#CM572405
				// CaseITF
				lst_SStoragePlanInquiry.setValue(j++, viewParam[i].getITF());
				
				//#CM572406
				//  (Second row) 
				//#CM572407
				// Item Name
				lst_SStoragePlanInquiry.setValue(j++, viewParam[i].getItemName());
				//#CM572408
				// PieceStorage Location
				lst_SStoragePlanInquiry.setValue(j++, viewParam[i].getPieceLocation());
				//#CM572409
				// Packed qty per bundle
				lst_SStoragePlanInquiry.setValue(j++, Formatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
				//#CM572410
				// Host Plan Piece qty
				lst_SStoragePlanInquiry.setValue(j++, Formatter.getNumFormat(viewParam[i].getPlanPieceQty()));
				//#CM572411
				// Result Piece qty
				lst_SStoragePlanInquiry.setValue(j++, Formatter.getNumFormat(viewParam[i].getResultPieceQty()));
				//#CM572412
				// Bundle ITF
				lst_SStoragePlanInquiry.setValue(j++, viewParam[i].getBundleITF());
				
				
				//#CM572413
				// Set tool tip (Balloon Item)
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM572414
				// Item Name
				toolTip.add(label_itemname,viewParam[i].getItemName());
				//#CM572415
				// CaseITF
				toolTip.add(label_caseitf,viewParam[i].getITF());
				//#CM572416
				// Bundle ITF
				toolTip.add(label_bundleitf,viewParam[i].getBundleITF());
				
				lst_SStoragePlanInquiry.setToolTip(i+1, toolTip.getText());

			}
			
			
			//#CM572417
			// Set Message
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
				//#CM572418
				// Close the connection
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

	//#CM572419
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572420
	/** 
	 * It is called when the clear button is pressed.<BR>
	 * <BR>
	 * Outline : Clear the Input area.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Initialize the input area. <BR>
	 * 		2.Set the cursor in Consignor Code. <BR>
	 * 		<BR>
	 * 		Item[Initial value] <BR>
	 * 		<DIR>
	 * 			Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Start Storage plan date[None] <BR>
	 * 			End Storage plan date[None] <BR>
	 * 			Item Code[None] <BR>
	 * 			Case/Piece flag[All] <BR>
	 * 			Work Status[All]<BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		setInitDsp();
	}

	//#CM572421
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572422
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572423
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572424
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572425
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM572426
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572427
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572428
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572429
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM572430
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStoragePlanInquiry_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572431
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStoragePlanInquiry_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572432
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStoragePlanInquiry_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM572433
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStoragePlanInquiry_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM572434
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStoragePlanInquiry_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572435
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStoragePlanInquiry_Change(ActionEvent e) throws Exception
	{
	}

	//#CM572436
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStoragePlanInquiry_Click(ActionEvent e) throws Exception
	{
	}


}
//#CM572437
//end of class
