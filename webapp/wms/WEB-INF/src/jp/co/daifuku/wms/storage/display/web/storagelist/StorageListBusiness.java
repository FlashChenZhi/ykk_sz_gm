// $Id: StorageListBusiness.java,v 1.2 2006/12/07 08:57:18 suresh Exp $

//#CM571794
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.storagelist;
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
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststoragelist.ListStorageListBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplandate.ListStoragePlanDateBusiness;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;
import jp.co.daifuku.wms.storage.schedule.StorageWorkListSCH;

//#CM571795
/**
 * Designer : A.Nagasawa<BR>
 * Maker : A.Nagasawa<BR>
 * <BR>
 * The screen class which does the Storage Work list print. <BR>
 * Hand over Parameter to the schedule which does the Storage Work list print. <BR>
 * <BR>
 * <DIR>
 * 		1.Processing when Display button is pressed(<CODE>btn_PDisplay_Click<CODE>) <BR>
 * 		<BR>
 * 		<DIR>
 * 			Set the content input from the screen in Parameter.  
 * 			The schedule must retrieve data for the display based on the Parameter, and display on the pop up screen. <BR>
 * 			<BR>
 * 			[Parameter] *Mandatory input<BR>
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
 * 		2.Processing when Print button is pressed(<CODE>btn_Print_Click<CODE>) <BR>
 * 		<BR>
 * 		<DIR>
 * 			Set the content input from the screen in Parameter.  
 * 			The schedule retrieves and prints data based on the Parameter. <BR>
 * 			The schedule must return true when it succeeds in the print and return false when failing. <BR>
 * 			<BR>
 * 			[Parameter] *Mandatory input<BR>
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
 * <TR><TD>2004/02/13</TD><TD>A.Nagasawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:18 $
 * @author  $Author: suresh $
 */
public class StorageListBusiness extends StorageList implements WMSConstants
{
	//#CM571796
	// Class fields --------------------------------------------------

	//#CM571797
	/**
	 * Dialog call origin : Print button
	 */
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";

	//#CM571798
	// Class variables -----------------------------------------------

	//#CM571799
	// Class method --------------------------------------------------

	//#CM571800
	// Constructors --------------------------------------------------

	//#CM571801
	// Public methods ------------------------------------------------

	//#CM571802
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

		//#CM571803
		// Set Initial value in each input field. 
		txt_ConsignorCode.setText(getConsignorCode());
		txt_StrtStrgPlanDate.setText("");
		txt_EdStrgPlanDate.setText("");
		txt_ItemCode.setText("");
		rdo_CpfAll.setChecked(true);
		
		//#CM571804
		// Set focus in Consignor Code. 
		setFocus(txt_ConsignorCode);

	}

	//#CM571805
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
		//#CM571806
		// Parameter selected from list box is acquired. 
		//#CM571807
		// Consignor Code
		String consignorcode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM571808
		// Start Storage plan date
		Date startstorageplandate = WmsFormatter.toDate(
			param.getParameter(ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY)
			,this.getHttpRequest().getLocale());

		//#CM571809
		// End Storage plan date
		Date endstorageplandate = WmsFormatter.toDate(
			param.getParameter(ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY)
			,this.getHttpRequest().getLocale());

		//#CM571810
		// Item Code
		String item = param.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM571811
		// Set the value when it is not empty. 
		//#CM571812
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM571813
		// Start Storage plan date
		if (!StringUtil.isBlank(startstorageplandate))
		{
			txt_StrtStrgPlanDate.setDate(startstorageplandate);
		}
		//#CM571814
		// End Storage plan date
		if (!StringUtil.isBlank(endstorageplandate))
		{
			txt_EdStrgPlanDate.setDate(endstorageplandate);
		}
		//#CM571815
		// Item Code
		if (!StringUtil.isBlank(item))
		{
			txt_ItemCode.setText(item);
		}
		//#CM571816
		// Set focus in Consignor Code
		setFocus(txt_ConsignorCode);
	}

	//#CM571817
	/**
	 * It is called before the call of each control event. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions.  
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM571818
			// Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM571819
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM571820
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
			//#CM571821
			// Set Path to the Help file. 
			btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}

	}
	
	//#CM571822
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
			//#CM571823
			// Set focus in Consignor Code. 
			setFocus(txt_ConsignorCode);
			
			//#CM571824
			// Check from which dialog return
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM571825
			// True when [Yes] is pressed in dialog
			//#CM571826
			// False when [No] is pressed in dialog
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM571827
			// End processing when [No] is pressed. 
			//#CM571828
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
			//#CM571829
			// Turn off the flag because the operation of the dialog ended. 
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM571830
		// Start Print scheduling.
		Connection conn = null;
		try
		{
			//#CM571831
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM571832
			// Set the input value in Parameter. 
			StorageSupportParameter[] param = new StorageSupportParameter[1];
			param[0] = createParameter();

			//#CM571833
			// Start scheduling.
			WmsScheduler schedule = new StorageWorkListSCH();
			schedule.startSCH(conn, param);
			
			//#CM571834
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
				//#CM571835
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



	//#CM571836
	// Package methods -----------------------------------------------

	//#CM571837
	// Protected methods ---------------------------------------------
	
	//#CM571838
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
		//#CM571839
		// Case/Piece flag
		//#CM571840
		// All
		if (rdo_CpfAll.getChecked())
		{
			param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM571841
		// Case
		else if (rdo_CpfCase.getChecked())
		{
			param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_CASE);
		
		}
		//#CM571842
		// Piece
		else if (rdo_CpfPiece.getChecked())
		{
			param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_PIECE);
		
		}
		//#CM571843
		// Unspecified
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM571844
		// Work Status
		//#CM571845
		// All
		if(pul_WorkStatus.getSelectedIndex() == 0)
		{
			param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_ALL);
		}
		//#CM571846
		// Stand by
		else if(pul_WorkStatus.getSelectedIndex() == 1)
		{
			param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		}
		//#CM571847
		// Working
		else if(pul_WorkStatus.getSelectedIndex() == 2)
		{
			param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_WORKING);
		}
		//#CM571848
		// Completed
		else if(pul_WorkStatus.getSelectedIndex() == 3)
		{
			param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_COMPLETION);
		}
		//#CM571849
		// Area type flag (Save ASRS) 
		param.setAreaTypeFlag(StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);
		
		return param;
	}

	//#CM571850
	// Private methods -----------------------------------------------
	//#CM571851
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
			//#CM571852
			// Connection acquisition	
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM571853
			// Acquire Consignor Code from the schedule. 
			WmsScheduler schedule = new StorageWorkListSCH();
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
				//#CM571854
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

	//#CM571855
	// Event handler methods -----------------------------------------
	//#CM571856
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571857
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571858
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571859
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

	//#CM571860
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571861
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571862
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571863
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571864
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571865
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571866
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
		//#CM571867
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM571868
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM571869
		// Retrieval flag(Plan)
		param.setParameter(
			ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY,
			StorageSupportParameter.SEARCH_STORAGE_WORK);
			
		//#CM571870
		// Work Status(Stand by, Working, Partially Completed, and Completed of Work information)
		String[] search = new String[4];
		search[0] = new String(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(StorageSupportParameter.STATUS_FLAG_WORKING);
		search[2] = new String(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		search[3] = new String(StorageSupportParameter.STATUS_FLAG_COMPLETION);
		param.setParameter(ListStorageConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		
		//#CM571871
		// Area type flag (Save AS/RS when you display the list. ) 
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY
		        , StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM571872
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageconsignor/ListStorageConsignor.do",
			param,
			"/progress.do");
	}

	//#CM571873
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrtStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571874
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571875
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtStrgPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571876
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtStrgPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571877
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrtPlan_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571878
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
		//#CM571879
		// Set the search condition on the Storage plan date retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM571880
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM571881
		// Start Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtStrgPlanDate.getDate()));
			
		//#CM571882
		// Set Start flag
		param.setParameter(
			ListStoragePlanDateBusiness.RANGESTORAGEPLANDATE_KEY,
			StorageSupportParameter.RANGE_START);
			
		//#CM571883
		// Work Status(Stand by, Working, Partially Completed, and Completed of Work information)
		String[] search = new String[4];
		search[0] = new String(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(StorageSupportParameter.STATUS_FLAG_WORKING);
		search[2] = new String(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		search[3] = new String(StorageSupportParameter.STATUS_FLAG_COMPLETION);
		param.setParameter(ListStorageConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		
		//#CM571884
		// Retrieval flag
		param.setParameter(ListStoragePlanDateBusiness.SEARCHSTORAGEPLANDATE_KEY, 
			StorageSupportParameter.SEARCH_STORAGE_WORK);
		//#CM571885
		// Area type flag (Save AS/RS when you display the list. ) 
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY
		        , StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM571886
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageplandate/ListStoragePlanDate.do",
			param,
			"/progress.do");
	}

	//#CM571887
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571888
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndStoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571889
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571890
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdStrgPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571891
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdStrgPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571892
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEdPlan_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571893
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
		//#CM571894
		// Set the search condition on the Storage plan date retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM571895
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM571896
		// End Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdStrgPlanDate.getDate()));
			
		//#CM571897
		// Set the End flag
		param.setParameter(
			ListStoragePlanDateBusiness.RANGESTORAGEPLANDATE_KEY,
			StorageSupportParameter.RANGE_END);
		//#CM571898
		// Work Status(Stand by, Working, Partially Completed, and Completed of Work information)
		String[] search = new String[4];
		search[0] = new String(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(StorageSupportParameter.STATUS_FLAG_WORKING);
		search[2] = new String(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		search[3] = new String(StorageSupportParameter.STATUS_FLAG_COMPLETION);
		param.setParameter(ListStorageConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		
		//#CM571899
		// Retrieval flag
		param.setParameter(ListStoragePlanDateBusiness.SEARCHSTORAGEPLANDATE_KEY, 
			StorageSupportParameter.SEARCH_STORAGE_WORK);
		
		//#CM571900
		// Area type flag (Save AS/RS when you display the list. ) 
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, 
		        StockControlParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM571901
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageplandate/ListStoragePlanDate.do",
			param,
			"/progress.do");
	}

	//#CM571902
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571903
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571904
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571905
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571906
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571907
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItem_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571908
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
		//#CM571909
		// Set the search condition on the Item retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM571910
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM571911
		// Start Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtStrgPlanDate.getDate()));
			
		//#CM571912
		// End Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdStrgPlanDate.getDate()));

		//#CM571913
		// Item Code
		param.setParameter(
			ListStorageItemBusiness.ITEMCODE_KEY,
			txt_ItemCode.getText());

		//#CM571914
		// Retrieval flag(Work)
		param.setParameter(
			ListStorageItemBusiness.SEARCHITEM_KEY,
			StorageSupportParameter.SEARCH_STORAGE_WORK);
			
		//#CM571915
		// Work Status(Stand by, Working, Partially Completed, and Completed of Work information)
		String[] search = new String[4];
		search[0] = new String(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(StorageSupportParameter.STATUS_FLAG_WORKING);
		search[2] = new String(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		search[3] = new String(StorageSupportParameter.STATUS_FLAG_COMPLETION);
		param.setParameter(ListStorageConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		//#CM571916
		// Area type flag (Save AS/RS when you display the list. ) 
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY,
		        StockControlParameter.AREA_TYPE_FLAG_NOASRS);
			
		//#CM571917
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageitem/ListStorageItem.do",
			param,
			"/progress.do");
			
	}

	//#CM571918
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571919
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571920
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM571921
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571922
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM571923
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571924
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM571925
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571926
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM571927
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571928
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571929
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatus_Change(ActionEvent e) throws Exception
	{
	}

	//#CM571930
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571931
	/** 
	 * It is called when the display button is pressed.<BR>
	 * <BR>
	 * Outline : Set input Item of Input area in Parameter, and display the Storage Work list box with the Search condition. <BR>
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
		//#CM571932
		// Set the Search condition to the Storage Work list screen. 
		ForwardParameters forwardParam = new ForwardParameters();
		//#CM571933
		// Consignor Code
		forwardParam.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		//#CM571934
		// Start Storage plan date
		forwardParam.setParameter(
			ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtStrgPlanDate.getDate()));
			
		//#CM571935
		// End Storage plan date
		forwardParam.setParameter(
			ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdStrgPlanDate.getDate()));

		//#CM571936
		// Item Code
		forwardParam.setParameter(
			ListStorageItemBusiness.ITEMCODE_KEY,
			txt_ItemCode.getText());
			
		//#CM571937
		// Case/Piece flag
		//#CM571938
		// All
		if (rdo_CpfAll.getChecked())
		{
			forwardParam.setParameter(
				ListStorageListBusiness.CASEPIECEFLAG_KEY,
				StorageSupportParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM571939
		// Case
		else if (rdo_CpfCase.getChecked())
		{
			forwardParam.setParameter(
				ListStorageListBusiness.CASEPIECEFLAG_KEY,
				StorageSupportParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM571940
		// Piece
		else if (rdo_CpfPiece.getChecked())
		{
			forwardParam.setParameter(
				ListStorageListBusiness.CASEPIECEFLAG_KEY,
					StorageSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM571941
		// Unspecified
		else if (rdo_CpfAppointOff.getChecked())
		{
			forwardParam.setParameter(
				ListStorageListBusiness.CASEPIECEFLAG_KEY,
				StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
		}

		//#CM571942
		// Work Status
		//#CM571943
		// All
		if(pul_WorkStatus.getSelectedIndex() == 0)
		{
			forwardParam.setParameter(
				ListStorageListBusiness.STATUS_FLAG_KEY,
				StorageSupportParameter.STATUS_FLAG_ALL);
		}
		//#CM571944
		// Stand by
		else if(pul_WorkStatus.getSelectedIndex() == 1)
		{
			forwardParam.setParameter(
				ListStorageListBusiness.STATUS_FLAG_KEY,
				StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		}
		//#CM571945
		// Working
		else if(pul_WorkStatus.getSelectedIndex() == 2)
		{
			forwardParam.setParameter(
				ListStorageListBusiness.STATUS_FLAG_KEY,
				StorageSupportParameter.STATUS_FLAG_WORKING);
		}
		//#CM571946
		// Completed
		else if(pul_WorkStatus.getSelectedIndex() == 3)
		{
			forwardParam.setParameter(
				ListStorageListBusiness.STATUS_FLAG_KEY,
				StorageSupportParameter.STATUS_FLAG_COMPLETION);
		}

		//#CM571947
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststoragelist/ListStorageList.do",
			forwardParam,
			"/progress.do");

	}

	//#CM571948
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571949
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
		//#CM571950
		// Set focus in Consignor Code. 
		setFocus(txt_ConsignorCode);
		//#CM571951
		// Input Check
		txt_ConsignorCode.validate();
		txt_StrtStrgPlanDate.validate(false);
		txt_EdStrgPlanDate.validate(false);
		txt_ItemCode.validate(false);
		
		//#CM571952
		// Start Storage plan date is smaller than End Storage plan date. 
		if (!StringUtil.isBlank(txt_StrtStrgPlanDate.getDate())
			&& !StringUtil.isBlank(txt_EdStrgPlanDate.getDate()))
		{
			if (txt_StrtStrgPlanDate.getDate().after(txt_EdStrgPlanDate.getDate()))
			{
				//#CM571953
				// 6023199=Starting planned storage date must precede the end planned storage date.
				message.setMsgResourceKey("6023199");
				return;
			}
		}
		
		Connection conn = null;
		
		try
		{
			//#CM571954
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM571955
			// Set schedule Parameter. 
			StorageSupportParameter param = createParameter();
			
			//#CM571956
			//Start scheduling.
			WmsScheduler schedule = new StorageWorkListSCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM571957
				// MSG-W0061={0} data match. Do you print?
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM571958
				// Memorize the dialog display by Print button. 
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM571959
				// Set Message
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
				//#CM571960
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


	//#CM571961
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571962
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
		//#CM571963
		// Set Initial value in each input field. 
		txt_ConsignorCode.setText(getConsignorCode());
		txt_StrtStrgPlanDate.setText("");
		txt_EdStrgPlanDate.setText("");
		txt_ItemCode.setText("");
		pul_WorkStatus.setSelectedIndex(0);
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);
		
		//#CM571964
		// Set focus in Consignor Code. 
		setFocus(txt_ConsignorCode);
	}
}
//#CM571965
//end of class
