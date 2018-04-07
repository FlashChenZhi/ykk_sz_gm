// $Id: StoragePlanListBusiness.java,v 1.2 2006/12/07 08:57:15 suresh Exp $

//#CM572442
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.storageplanlist;
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
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplandate.ListStoragePlanDateBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplanlist.ListStoragePlanListBusiness;
import jp.co.daifuku.wms.storage.schedule.StoragePlanListSCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM572443
/**
 * Designer : A.Nagasawa<BR>
 * Maker : A.Nagasawa<BR>
 * <BR>
 * The screen class which does the Storage Plan list print. <BR>
 * Hand over Parameter to the schedule which does the Storage Plan list print. <BR>
 * <BR>
 * <DIR>
 * 		1.Processing when Display button is pressed(<CODE>btn_PDisplay_Click</CODE>) <BR>
 * 		<BR>
 * 		<DIR>
 * 			Set the content input from the screen in Parameter.  
 * 			The schedule must retrieve data for the display based on the Parameter, and display on the pop up screen. <BR>
 * 			<BR>
 * 			[Parameter]*Mandatory input<BR>
 * 			<DIR>
 * 				Consignor Code*<BR>
 * 				Start Storage plan date<BR>
 * 				End Storage plan date<BR>
 * 				Item Code<BR>
 * 				Case/Piece flag*<BR>
 * 				Work Status*<BR>
 * 			</DIR>
 * 		</DIR>
 * 		<BR>
 * 		2.Processing when Print button is pressed(<CODE>btn_Print_Click</CODE>) <BR>
 * 		<BR>
 * 		<DIR>
 * 			Set the content input from the screen in Parameter.  
 * 			The schedule retrieves and prints data based on the Parameter. <BR>
 * 			The schedule must return true when it succeeds in the print and return false when failing. <BR>
 * 			<BR>
 * 			[Parameter]*Mandatory input<BR>
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
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:15 $
 * @author  $Author: suresh $
 */
public class StoragePlanListBusiness extends StoragePlanList implements WMSConstants
{
	
	//#CM572444
	// Class fields --------------------------------------------------
	
	//#CM572445
	/**
	 * Dialog call origin : Print button
	 */
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";

	//#CM572446
	// Class variables -----------------------------------------------

	//#CM572447
	// Class method --------------------------------------------------

	//#CM572448
	// Constructors --------------------------------------------------

	//#CM572449
	// Public methods ------------------------------------------------

	//#CM572450
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Outline:Display initial data in the screen. <BR>
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
	public void page_Load(ActionEvent e) throws Exception
	{

		//#CM572451
		// Set Initial value in each input field. 
		txt_ConsignorCode.setText(getConsignorCode());
		txt_StrtStrgPlanDate.setText("");
		txt_EdStrgPlanDate.setText("");
		txt_ItemCode.setText("");
		rdo_CpfAll.setChecked(true);
		
		//#CM572452
		// Set focus in Consignor Code. 
		setFocus(txt_ConsignorCode);

	}
	
	//#CM572453
	/**
	 * This Method is called when returning from the dialog button.<BR>
	 * Override <CODE>page_ConfirmBack</CODE> defined in <CODE>Page</CODE>.<BR>
	 * <BR>
	 * Outline : Execute the corresponding processing when "Yes" is selected from the dialog.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the cursor in Consignor Code. 
	 * 	    2.Check from which dialog returns.<BR>
	 *      3.Check whether "Yes" or "No" was selected from the dialog.<BR>
	 *      4.Start Schedule when [Yes] is selected.<BR>
	 *      5.Display the result of the schedule in the Message area. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		try
		{
			//#CM572454
			// Set focus in Consignor Code. 
			setFocus(txt_ConsignorCode);
			
			//#CM572455
			// Check from which dialog return
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM572456
			// True when [Yes] is pressed in dialog
			//#CM572457
			// False when [No] is pressed in dialog
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM572458
			// End processing when [No] is pressed. 
			//#CM572459
			// Setting Message here is unnecessary. 
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
			//#CM572460
			// Turn off the flag because the operation of the dialog ended. 
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM572461
		// Start Print scheduling.
		Connection conn = null;
		try
		{
			//#CM572462
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM572463
			// Set the input value in Parameter. 
			StorageSupportParameter[] param = new StorageSupportParameter[1];
			param[0] = createParameter();

			//#CM572464
			// Start scheduling.
			WmsScheduler schedule = new StoragePlanListSCH();
			schedule.startSCH(conn, param);
			
			//#CM572465
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
				//#CM572466
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
	
	//#CM572467
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
			//#CM572468
			//Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM572469
			//Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM572470
			//Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}

	}
	
	//#CM572471
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
		//#CM572472
		// Parameter selected from list box is acquired. 
		//#CM572473
		// Consignor Code
		String consignorcode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM572474
		// Start Storage plan date
		Date startstorageplandate = WmsFormatter.toDate(
			param.getParameter(ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY)
			,this.getHttpRequest().getLocale());

		//#CM572475
		// End Storage plan date
		Date endstorageplandate = WmsFormatter.toDate(
			param.getParameter(ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY)
			,this.getHttpRequest().getLocale());

		//#CM572476
		// Item Code
		String item = param.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM572477
		// Set the value when it is not empty. 
		//#CM572478
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM572479
		// Start Storage plan date
		if (!StringUtil.isBlank(startstorageplandate))
		{
			txt_StrtStrgPlanDate.setDate(startstorageplandate);
		}
		//#CM572480
		// End Storage plan date
		if (!StringUtil.isBlank(endstorageplandate))
		{
			txt_EdStrgPlanDate.setDate(endstorageplandate);
		}
		//#CM572481
		// Item Code
		if (!StringUtil.isBlank(item))
		{
			txt_ItemCode.setText(item);
		}
		//#CM572482
		// Set focus in Consignor Code
		setFocus(txt_ConsignorCode);
	}




	//#CM572483
	// Package methods -----------------------------------------------

	//#CM572484
	// Protected methods ---------------------------------------------
	
	//#CM572485
	/**
	 * Generate the Parameter object which sets the input value of the input area. <BR>
	 * <BR>
	 * @return Parameter object which contains input value of input area
	 */
	protected StorageSupportParameter createParameter()
	{
		StorageSupportParameter param = new StorageSupportParameter();

		param.setConsignorCode(txt_ConsignorCode.getText());
		param.setFromStoragePlanDate(WmsFormatter.toParamDate(txt_StrtStrgPlanDate.getDate()));
		param.setToStoragePlanDate(WmsFormatter.toParamDate(txt_EdStrgPlanDate.getDate()));
		param.setItemCode(txt_ItemCode.getText());
		//#CM572486
		// Case/Piece flag
		//#CM572487
		// All
		if (rdo_CpfAll.getChecked())
		{
			param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM572488
		// Case
		else if (rdo_CpfCase.getChecked())
		{
			param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM572489
		// Piece
		else if (rdo_CpfPiece.getChecked())
		{
			param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM572490
		// Unspecified
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM572491
		// Case , Piece
		else if (rdo_CpfCasePiece.getChecked())
		{
			param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_MIXED);
		}
		
		//#CM572492
		// Work Status
		//#CM572493
		// All
		if(pul_WorkStatusStorage.getSelectedIndex() == 0)
		{
			param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_ALL);
		}
		//#CM572494
		// Stand by
		else if(pul_WorkStatusStorage.getSelectedIndex() == 1)
		{
			param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		}
		//#CM572495
		// Working
		else if(pul_WorkStatusStorage.getSelectedIndex() == 2)
		{
			param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_WORKING);
		}
		//#CM572496
		// Working
		else if(pul_WorkStatusStorage.getSelectedIndex() == 3)
		{
			param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		}
		//#CM572497
		// Completed
		else if(pul_WorkStatusStorage.getSelectedIndex() == 4)
		{
			param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_COMPLETION);
		}
		return param;
	}

	//#CM572498
	// Private methods -----------------------------------------------

	//#CM572499
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
			//#CM572500
			// Connection acquisition	
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM572501
			// Acquire Consignor Code from the schedule. 
			WmsScheduler schedule = new StoragePlanListSCH();
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
				//#CM572502
				// Close the connection
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

	

	//#CM572503
	// Event handler methods -----------------------------------------
	//#CM572504
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572505
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572506
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572507
	/** 
	 * It is called when the menu button is pressed.<BR>
	 * <BR>
	 * Outline : Change to the menu panel. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM572508
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572509
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572510
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572511
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572512
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM572513
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572514
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
		//#CM572515
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM572516
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM572517
		// Retrieval flag(Plan)
		param.setParameter(
			ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY,
			StorageSupportParameter.SEARCH_STORAGE_PLAN);
		
		//#CM572518
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageconsignor/ListStorageConsignor.do",
			param,
			"/progress.do");
	}

	//#CM572519
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrtStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572520
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572521
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtStrgPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572522
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtStrgPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572523
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrtPlan_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572524
	/** 
	 * Start It is called when the retrieval button of Storage plan date is pressed.<BR>
	 * <BR>
	 * Outline:Set Search condition in Parameter, and display the Storage plan date list box with the Search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]<BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Start Storage plan date <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchStrtPlan_Click(ActionEvent e) throws Exception
	{
		//#CM572525
		// Set the search condition on the Storage plan date retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM572526
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM572527
		// Start Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtStrgPlanDate.getDate()));
			
		//#CM572528
		// Set Start flag
		param.setParameter(
			ListStoragePlanDateBusiness.RANGESTORAGEPLANDATE_KEY,
			StorageSupportParameter.RANGE_START);
			
		//#CM572529
		// Retrieval flag
		param.setParameter(ListStoragePlanDateBusiness.SEARCHSTORAGEPLANDATE_KEY, 
			StorageSupportParameter.SEARCH_STORAGE_PLAN);

		//#CM572530
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageplandate/ListStoragePlanDate.do",
			param,
			"/progress.do");
	}

	//#CM572531
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572532
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndStoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572533
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572534
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdStrgPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572535
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdStrgPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572536
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEdPlan_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572537
	/** 
	 * End It is called when the retrieval button of Storage plan date is pressed.<BR>
	 * <BR>
	 * Outline:Set Search condition in Parameter, and display the Storage plan date list box with the Search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]<BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       End Storage plan date <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchEdPlan_Click(ActionEvent e) throws Exception
	{
		//#CM572538
		// Set the search condition on the Storage plan date retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM572539
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM572540
		// End Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdStrgPlanDate.getDate()));
			
		//#CM572541
		// Set the End flag
		param.setParameter(
			ListStoragePlanDateBusiness.RANGESTORAGEPLANDATE_KEY,
			StorageSupportParameter.RANGE_END);

		//#CM572542
		// Retrieval flag
		param.setParameter(ListStoragePlanDateBusiness.SEARCHSTORAGEPLANDATE_KEY, 
			StorageSupportParameter.SEARCH_STORAGE_PLAN);
		
		//#CM572543
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageplandate/ListStoragePlanDate.do",
			param,
			"/progress.do");
	}

	//#CM572544
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572545
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572546
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572547
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572548
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM572549
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItem_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572550
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
		//#CM572551
		// Set the search condition on the Item retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM572552
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM572553
		// Start Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtStrgPlanDate.getDate()));
			
		//#CM572554
		// End Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdStrgPlanDate.getDate()));

		//#CM572555
		// Item Code
		param.setParameter(
			ListStorageItemBusiness.ITEMCODE_KEY,
			txt_ItemCode.getText());

		//#CM572556
		// Retrieval flag(Plan)
		param.setParameter(
			ListStorageItemBusiness.SEARCHITEM_KEY,
			StorageSupportParameter.SEARCH_STORAGE_PLAN);
						
		//#CM572557
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageitem/ListStorageItem.do",
			param,
			"/progress.do");
	}

	//#CM572558
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572559
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572560
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM572561
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572562
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM572563
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572564
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM572565
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572566
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM572567
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCasePiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572568
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCasePiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM572569
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572570
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatusStorage_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572571
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatusStorage_Change(ActionEvent e) throws Exception
	{
	}

	//#CM572572
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572573
	/** 
	 * It is called when the display button is pressed.<BR>
	 * <BR>
	 * Outline : Set input Item of Input area in Parameter, and display the Storage Plan list box with the Search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory input<BR>
	 * <BR>
	 * <DIR>
	 * 		Consignor Code*<BR>
	 * 		Start Storage plan date<BR>
	 * 		End Storage plan date<BR>
	 * 		Item Code<BR>
	 * 		Case/Piece flag*<BR>
	 * 		Work Status*<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		//#CM572574
		// Set the Search condition to the Storage Plan list screen. 
		ForwardParameters forwardParam = new ForwardParameters();
		//#CM572575
		// Consignor Code
		forwardParam.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM572576
		// Start Storage plan date
		forwardParam.setParameter(
			ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtStrgPlanDate.getDate()));
			
		//#CM572577
		// End Storage plan date
		forwardParam.setParameter(
			ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdStrgPlanDate.getDate()));

		//#CM572578
		// Item Code
		forwardParam.setParameter(
			ListStorageItemBusiness.ITEMCODE_KEY,
			txt_ItemCode.getText());
			
		//#CM572579
		// Case/Piece flag
		//#CM572580
		// All
		if (rdo_CpfAll.getChecked())
		{
			forwardParam.setParameter(
				ListStoragePlanListBusiness.CASEPIECEFLAG_KEY,
				StorageSupportParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM572581
		// Case
		else if (rdo_CpfCase.getChecked())
		{
			forwardParam.setParameter(
			ListStoragePlanListBusiness.CASEPIECEFLAG_KEY,
				StorageSupportParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM572582
		// Piece
		else if (rdo_CpfPiece.getChecked())
		{
			forwardParam.setParameter(
			ListStoragePlanListBusiness.CASEPIECEFLAG_KEY,
					StorageSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM572583
		// Unspecified
		else if (rdo_CpfAppointOff.getChecked())
		{
			forwardParam.setParameter(
			ListStoragePlanListBusiness.CASEPIECEFLAG_KEY,
				StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM572584
		// Case/Piece
		else if (rdo_CpfCasePiece.getChecked())
		{
			forwardParam.setParameter(
			ListStoragePlanListBusiness.CASEPIECEFLAG_KEY,
				StorageSupportParameter.CASEPIECE_FLAG_MIXED);
		}

		//#CM572585
		// Work Status
		//#CM572586
		// All
		if(pul_WorkStatusStorage.getSelectedIndex() == 0)
		{
			forwardParam.setParameter(
				ListStoragePlanListBusiness.STATUS_FLAG_KEY,
				StorageSupportParameter.STATUS_FLAG_ALL);
		}
		//#CM572587
		// Stand by
		else if(pul_WorkStatusStorage.getSelectedIndex() == 1)
		{
			forwardParam.setParameter(
				ListStoragePlanListBusiness.STATUS_FLAG_KEY,
				StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		}
		//#CM572588
		// Working
		else if(pul_WorkStatusStorage.getSelectedIndex() == 2)
		{
			forwardParam.setParameter(
				ListStoragePlanListBusiness.STATUS_FLAG_KEY,
				StorageSupportParameter.STATUS_FLAG_WORKING);
		}
		//#CM572589
		// Partially Completed
		else if(pul_WorkStatusStorage.getSelectedIndex() == 3)
		{
			forwardParam.setParameter(
				ListStoragePlanListBusiness.STATUS_FLAG_KEY,
				StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		}
		//#CM572590
		// Completed
		else if(pul_WorkStatusStorage.getSelectedIndex() == 4)
		{
			forwardParam.setParameter(
				ListStoragePlanListBusiness.STATUS_FLAG_KEY,
				StorageSupportParameter.STATUS_FLAG_COMPLETION);
		}

		//#CM572591
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageplanlist/ListStoragePlanList.do",
			forwardParam,
			"/progress.do");
	}

	//#CM572592
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572593
	/** 
	 * It is called when Print button is pressed.<BR>
	 * <BR>
	 * Outline : Display the Confirmation dialog whether to print or not after input Item of input area is set in Parameter and print qty is acquired.  <BR>
	 * after input Item of input area is set in Parameter and print qty is acquired.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the cursor in Consignor Code. <BR>
	 * 		2.Check input Item of Input area. <BR>
	 * 		3.Check the print qty objects.<BR>
	 * 		4-1.Display the Confirmation dialog when there is data for print. <BR>
	 * 		4-2.Acquire Message when there is no data for the print and display it. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		//#CM572594
		// Set focus in Consignor Code. 
		setFocus(txt_ConsignorCode);
		//#CM572595
		// Input Check
		txt_ConsignorCode.validate();
		txt_StrtStrgPlanDate.validate(false);
		txt_EdStrgPlanDate.validate(false);
		txt_ItemCode.validate(false);
		
		//#CM572596
		// Start Storage plan date is smaller than End Storage plan date. 
		if (!StringUtil.isBlank(txt_StrtStrgPlanDate.getDate())
			&& !StringUtil.isBlank(txt_EdStrgPlanDate.getDate()))
		{
			if (txt_StrtStrgPlanDate.getDate().after(txt_EdStrgPlanDate.getDate()))
			{
				//#CM572597
				// 6023199 = Starting planned storage date must precede the end planned storage date.
				message.setMsgResourceKey("6023199");
				return;
			}
		}
		
		Connection conn = null;
		
		try
		{
			//#CM572598
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM572599
			// Set schedule Parameter. 
			StorageSupportParameter param = createParameter();
			
			//#CM572600
			//Start scheduling.
			WmsScheduler schedule = new StoragePlanListSCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM572601
				// MSG-W0061={0} data match. Do you print?
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM572602
				// Memorize the dialog display by Print button. 
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM572603
				// Set Message
				message.setMsgResourceKey(schedule.getMessage());
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
				//#CM572604
				// Close the connection
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
	}

	//#CM572605
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572606
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
		//#CM572607
		// Set Initial value in each input field. 
		txt_ConsignorCode.setText(getConsignorCode());
		txt_StrtStrgPlanDate.setText("");
		txt_EdStrgPlanDate.setText("");
		txt_ItemCode.setText("");
		pul_WorkStatusStorage.setSelectedIndex(0);
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);
		rdo_CpfCasePiece.setChecked(false);
		
		//#CM572608
		// Set focus in Consignor Code. 
		setFocus(txt_ConsignorCode);

	}
}
//#CM572609
//end of class
