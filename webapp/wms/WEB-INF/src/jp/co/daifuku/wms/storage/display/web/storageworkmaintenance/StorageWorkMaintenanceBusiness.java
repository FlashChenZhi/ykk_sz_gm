// $Id: StorageWorkMaintenanceBusiness.java,v 1.3 2006/12/07 08:57:09 suresh Exp $

//#CM574124
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.storageworkmaintenance;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.ListCellColumn;
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
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingitem.ListShippingItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplandate.ListStoragePlanDateBusiness;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;
import jp.co.daifuku.wms.storage.schedule.StorageWorkMaintenanceSCH;

//#CM574125
/**
 * Designer : H.Akiyama <BR>
 * Maker : H.Akiyama <BR>
 * <BR>
 * Stock Work maintenance screen class. <BR>
 * Hand over Parameter to the schedule which does stock Work maintenance processing. <BR>
 * Moreover, do Commit  of Transaction and Rollback on this screen. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Display button is pressed(<CODE>btn_View_Click<CODE> Method) <BR>
 * <BR>
 * <DIR>
 *  The content input from the screen is set in Parameter, and the schedule retrieves data for the display based on the Parameter. <BR>
 *  Receive the retrieval result from the schedule, and output to Preset area. <BR>
 *  <BR>
 *  [Parameter] *Mandatory input<BR>
 *  <DIR>
 *   	Worker Code*<BR>
 *   	Password*<BR>
 *   	Consignor Code*<BR>
 *   	Work Status*<BR>
 *   	Storage plan date*<BR>
 *   	Item Code<BR>
 *   	Case/Piece flag*<BR>
 *  </DIR>
 * 	<BR>
 *  [Return Data]<BR>
 *  <DIR>
 *   	Consignor Code <BR>
 *   	Consignor Name <BR>
 *   	Storage plan date <BR>
 *   	Item Code <BR>
 *   	Item Name <BR>
 *   	Packed qty per case <BR>
 *   	Packed qty per bundle <BR>
 *   	Work plan case qty <BR>
 *   	Work plan piece qty <BR>
 *   	Storage Case qty <BR>
 *   	Storage Piece qty <BR>
 *   	Storage Location<BR>
 *   	Status flag <BR>
 *   	Expiry Date<BR>
 *   	Case/Piece flag<BR>
 *   	Case/Piece flag description<BR>
 *   	Results report (Not Reported/Reported) <BR>
 *   	Work place <BR>
 *   	Work No. <BR>
 *   	Last updated date and time <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2Processing when Add/Modify button is pressed (<CODE>btn_ModifySubmit_Click<CODE> Method) <BR>
 * <BR>
 * <DIR>
 *  The content input from Preset area is set in Parameter, and the schedule does stock Work maintenance based on the Parameter. <BR>
 *  Receive true when processing normally Completes and receive false when the schedule does not do Complete because of the condition error etc. <BR>
 *  The message acquired from the schedule is output to the screen as a result of the schedule, and Display Preset area again.  <BR>
 *  <BR>
 *  [Parameter] *Mandatory input<BR>
 *  <DIR>
 * 		Worker Code*<BR>
 * 		Password*<BR>
 * 		ViewState.Consignor Code<BR>
 * 		ViewState.Work Status<BR>
 * 		ViewState.Storage plan date<BR>
 * 		ViewState.Item Code<BR>
 * 		ViewState.Case/Piece flag<BR>
 * 		Preset.Storage Case qty<BR>
 * 		Preset.Storage Piece qty<BR>
 * 		Preset.Storage Location<BR>
 * 		Preset.Status<BR>
 * 		Preset.Expiry Date<BR>
 * 		Preset.Packed qty per case<BR>
 * 		Push button<BR>
 * 		Preset Line No..<BR>
 * 		Terminal No.<BR>
 * 		System identification key<BR>
 * 		Preset.Work No.<BR>
 * 		Preset.Last updated date and time<BR>
 * 		Preset.Work place<BR>
 *  </DIR>
 * 	<BR>
 *  [Return Data]<BR>
 *  <DIR>
 *   	Consignor Code <BR>
 *   	Consignor Name <BR>
 *   	Storage plan date <BR>
 *   	Item Code <BR>
 *   	Item Name <BR>
 *   	Packed qty per case <BR>
 *   	Packed qty per bundle <BR>
 *   	Work plan case qty <BR>
 *   	Work plan piece qty <BR>
 *   	Storage Case qty <BR>
 *   	Storage Piece qty <BR>
 *   	Storage Location<BR>
 *   	Status flag <BR>
 *   	Expiry Date<BR>
 *   	Case/Piece flag<BR>
 *   	Case/Piece flag description<BR>
 *   	Results report (Not Reported/Reported) <BR>
 *   	Work place <BR>
 *   	Work No. <BR>
 *   	Last updated date and time <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>H.Akiyama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/12/07 08:57:09 $
 * @author  $Author: suresh $
 */
public class StorageWorkMaintenanceBusiness extends StorageWorkMaintenance implements WMSConstants
{
	//#CM574126
	// Class fields --------------------------------------------------

	//#CM574127
	/**
	 * For ViewState : Worker Code
	 */
	protected static final String WORKERCODE = "WORKER_CODE";
	
	//#CM574128
	/**
	 * For ViewState : Password
	 */
	protected static final String PASSWORD = "PASSWORD";
	
	//#CM574129
	/**
	 * For ViewState : Consignor Code
	 */
	protected static final String CONSIGNORCODE = "CONSIGNOR_CODE";
	
	//#CM574130
	/**
	 * For ViewState : Consignor Name
	 */
	protected static final String CONSIGNORNAME = "CONSIGNOR_NAME";
	
	//#CM574131
	/**
	 * For ViewState : Work Status
	 */
	protected static final String WORKSTATUS = "WORK_STATUS";
	
	//#CM574132
	/**
	 * For ViewState : Storage plan date
	 */
	protected static final String STORAGEPLANDATE = "STORAGE_PLAN_DATE";
	
	//#CM574133
	/**
	 * For ViewState : Item Code
	 */
	protected static final String ITEMCODE = "ITEM_CODE";
	
	//#CM574134
	/**
	 * For ViewState : Case/Piece flag
	 */
	protected static final String CASEPIECEFLAG = "CASE_PIECE_FLAG";
	
	//#CM574135
	/**
	 * For ViewState : System identification key
	 */
	public static final String SYSTEM_DISC_KEY = "SYSTEM_DISC_KEY";
	
	//#CM574136
	/**
	 * For HIDDEN item : 0 = Storage Case qty
	 */
	protected static final int HIDDEN0 = 0;
	
	//#CM574137
	/**
	 * For HIDDEN item : 1 = Storage Piece qty
	 */
	protected static final int HIDDEN1 = 1;
	
	//#CM574138
	/**
	 * For HIDDEN item : 2 = Storage Location
	 */
	protected static final int HIDDEN2 = 2;
	
	//#CM574139
	/**
	 * For HIDDEN item : 3 = Status
	 */
	protected static final int HIDDEN3 = 3;
	
	//#CM574140
	/**
	 * For HIDDEN item : 4 = Expiry Date
	 */
	protected static final int HIDDEN4 = 4;
	
	//#CM574141
	/**
	 * For HIDDEN item : 5 = Work No. 
	 */
	protected static final int HIDDEN5 = 5;
	
	//#CM574142
	/**
	 * For HIDDEN item : 6 = Last updated date and time
	 */
	protected static final int HIDDEN6 = 6;
	
	//#CM574143
	/**
	 * For HIDDEN item : 7 = Work place
	 */
	protected static final int HIDDEN7 = 7;
	
	//#CM574144
	// Status flag
	//#CM574145
	/**
	 * For Input area Pulldown : Work Status flag All = 0
	 */
	protected static final int STATUSFLAGALL = 0;
	
	//#CM574146
	/**
	 * For Input area Pulldown : Work Status flag Stand by = 1
	 */
	protected static final int STATUSFLAGUNSTARTED = 1;
	
	//#CM574147
	/**
	 * For Input area Pulldown : Work Status flag Working = 2
	 */
	protected static final int STATUSFLAGWORKING = 2;
	
	//#CM574148
	/**
	 * For Input area Pulldown : Work Status flag Completed = 3
	 */
	protected static final int STATUSFLAGCOMPLETION = 3;
	
	//#CM574149
	/**
	 * For Preset Pulldown : Work Status flag Stand by = 0
	 */
	protected static final String L_STATUSFLAGUNSTARTED = "0";
	
	//#CM574150
	/**
	 * For Preset Pulldown : Work Status flag Working = 1
	 */
	protected static final String L_STATUSFLAGWORKING = "1";
	
	//#CM574151
	/**
	 * For Preset Pulldown : Work Status flag Completed = 2
	 */
	protected static final String L_STATUSFLAGCOMPLETION = "2";
	
	//#CM574152
	// Class variables -----------------------------------------------

	//#CM574153
	// Class method --------------------------------------------------

	//#CM574154
	// Constructors --------------------------------------------------

	//#CM574155
	// Public methods ------------------------------------------------

	//#CM574156
	/**
	 * It is called when initial data of the screen is displayed.<BR>
	 * <BR>
	 * Outline : Display initial data in the screen. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Initialize the input area. <BR>
	 * 		2.Invalidate all buttons of Preset area. <BR>
	 * 		3.Set the cursor in Worker Code. <BR>
	 * 		<BR>
	 * 		<DIR>
	 *  		Item Name[Initial value]
	 *  		<DIR>
	 *  			Worker Code[None]<BR>
	 *  			Password[None]<BR>
	 *  			Consignor Code[Display only in case of one in Consignor retrieval result is available]<BR>
	 *  			Work Status[All]<BR>
	 *  			Storage plan date[None]<BR>
	 *  			Item Code[None]<BR>
	 *  			Case/Piece flag[All]<BR>
	 *  		</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		
		//#CM574157
		// Set Initial value in each input field. 
		txt_WorkerCode.setText("");
		txt_Password.setText("");
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM574158
		// Work Status Pulldown : All
		pul_WorkStatus.setSelectedIndex(0);
		txt_StoragePlanDate.setText("");
		txt_ItemCode.setText("");
		//#CM574159
		// Case/Piece flag radio button : All
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);
		
		//#CM574160
		// Make the Add/Modify button, Release button when being working in the lump, and the Clear list button unuseable.
		btn_ModifySubmit.setEnabled(false);
		btn_AllWorkingClear.setEnabled(false);
		btn_ListClear.setEnabled(false);

		//#CM574161
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
	}

	//#CM574162
	/**
	 * It is called before the call of each control event. <BR>
	 * <BR>
	 * Outline:Display the Confirmation dialog.<BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM574163
			//Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM574164
			//Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM574165
			//Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
		
		//#CM574166
		// Message when Add/Modify button is pressed
		//#CM574167
		// MSG-W0008=Do you commit modification?
		btn_ModifySubmit.setBeforeConfirm("MSG-W0033");

		//#CM574168
		// Message when Release button being working in the lump is pressed
		//#CM574169
		// MSG-W0013=Status will become Standby.
		btn_AllWorkingClear.setBeforeConfirm("MSG-W0013");
		
		//#CM574170
		// Message when Clear list button is pressed
		//#CM574171
		// MSG-W0012 = Do you clear the list?
		btn_ListClear.setBeforeConfirm("MSG-W0012");
		
	}

	//#CM574172
	/**
	 * When returning from the pop up window, this Method is called. <BR>
	 * Override the <CODE>page_DlgBack</CODE> set in <CODE>Page</CODE>.<BR>
	 * <BR>
	 * Outline:Acquire and set Return Data of the retrieval screen. <BR>
	 * <BR>
	 * <DIR>
	 *      1.Acquire the value returned from the retrieval screen of pop up. <BR>
	 *      2.Set it on the screen when the value is not empty. <BR>
	 *      3.Set the cursor in Worker Code. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM574173
		// Parameter selected from list box is acquired. 
		//#CM574174
		// Consignor Code
		String consignorCode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM574175
		// Storage plan date
		Date storagePlanDate = 
			WmsFormatter.toDate(
				param.getParameter(ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY), 
					this.getHttpRequest().getLocale());
		//#CM574176
		// Item Code
		String itemCode = param.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);

		//#CM574177
		// Set the value when it is not empty. 
		//#CM574178
		// Consignor Code
		if (!StringUtil.isBlank(consignorCode))
		{
			txt_ConsignorCode.setText(consignorCode);
		}
		//#CM574179
		// Storage plan date
		if (!StringUtil.isBlank(storagePlanDate))
		{
			txt_StoragePlanDate.setDate(storagePlanDate);
		}
		//#CM574180
		// Item Code
		if (!StringUtil.isBlank(itemCode))
		{
			txt_ItemCode.setText(itemCode);
		}

		//#CM574181
		// Focus is set in Worker Code. 
		setFocus(txt_WorkerCode);
	}

	//#CM574182
	// Package methods -----------------------------------------------

	//#CM574183
	// Protected methods ---------------------------------------------
	//#CM574184
	/**
	 * Do the Input Check.<BR>
	 * <BR>
	 * Outline : Set Message and Return false when abnormality is found. <BR>
	 * <BR>
	 * @return Result of Input Check (true:Normal false:Abnormal) 
	 */
	protected boolean checkContainNgText(int rowNo)
	{
		WmsCheckker checker = new WmsCheckker();

		lst_SStorageWorkMtn.setCurrentRow(rowNo);

		if(!checker.checkContainNgText(
				lst_SStorageWorkMtn.getValue(5) ,
				rowNo,
				lst_SStorageWorkMtn.getListCellColumn(5).getResourceKey() )
				)
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}

		if(!checker.checkContainNgText(
				lst_SStorageWorkMtn.getValue(7) ,
				rowNo,
				lst_SStorageWorkMtn.getListCellColumn(7).getResourceKey() )
				)
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		return true;
		
	}

	//#CM574185
	// Private methods -----------------------------------------------

	//#CM574186
	// Event handler methods -----------------------------------------
	//#CM574187
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574188
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574189
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574190
	/** 
	 * It is called when the menu button is pressed.<BR>
	 * <BR>
	 * Outline : Change to the menu panel.<BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		//#CM574191
		// Change to the menu panel.
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}


	//#CM574192
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574193
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574194
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM574195
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM574196
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM574197
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574198
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574199
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM574200
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM574201
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM574202
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574203
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574204
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM574205
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM574206
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM574207
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574208
	/** 
	 * It is called when the consignor retrieval button is pressed.<BR>
	 * <BR>
	 * Outline : Set the search condition in Parameter, and display the Consignor list box by the search condition.<BR>
	 * <BR>
	 * [Parameter]*Mandatory input
	 * <DIR>
	 *  	Consignor Code<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		//#CM574209
		// The instance to set the search condition is declared. 
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM574210
		// Set Consignor Code
		forwardParam.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, 
			txt_ConsignorCode.getText());
		//#CM574211
		// Retrieval flag : Work
		forwardParam.setParameter(ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY, 
			StorageSupportParameter.SEARCH_STORAGE_WORK);

		//#CM574212
		// Display the consignor retrieval list box. 
		redirect(
			"/storage/listbox/liststorageconsignor/ListStorageConsignor.do", forwardParam, "/progress.do");
		
	}

	//#CM574213
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574214
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574215
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatus_Change(ActionEvent e) throws Exception
	{
	}

	//#CM574216
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574217
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574218
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StoragePlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM574219
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StoragePlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM574220
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574221
	/** 
	 * It is called when the Storage plan date retrieval button is pressed.<BR>
	 * <BR>
	 * Outline : Set the search condition in Parameter, and display the retrieval list box of the shipping plan date by the search condition. <BR>
	 * <BR>
	 * [Parameter]<BR>
	 *  <DIR>
	 *  	Consignor Code<BR>
	 * 		Work Status<BR>
	 *  	Storage plan date<BR>
	 *  </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchStrgPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM574222
		// The instance to set the search condition is declared. 
		ForwardParameters forwardParam = new ForwardParameters();
		
		//#CM574223
		// Set Consignor Code
		forwardParam.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());


		//#CM574224
		// Pulldown : All
		if(pul_WorkStatus.getSelectedIndex() != 0)
		{

			//#CM574225
			// Pulldown : Stand by
			if(pul_WorkStatus.getSelectedIndex() == 1)
			{
				//#CM574226
				// Stand by, Partially Completed
				//#CM574227
				// Work Status
				String[] search = new String[2];
				search[0] = new String(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
				search[1] = new String(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(ListStoragePlanDateBusiness.WORKSTATUSSTORAGEPLANDATE_KEY, search);
			}
			//#CM574228
			// Pulldown : Working
			else if(pul_WorkStatus.getSelectedIndex() == 2)
			{
				//#CM574229
				// Working
				//#CM574230
				// Work Status
				String[] search = new String[1];
				search[0] = new String(StorageSupportParameter.STATUS_FLAG_WORKING);
				forwardParam.setParameter(ListStoragePlanDateBusiness.WORKSTATUSSTORAGEPLANDATE_KEY, search);
			}
			//#CM574231
			// Pulldown : Completed
			else if(pul_WorkStatus.getSelectedIndex() == 3)
			{
				//#CM574232
				// Completed, Partially Completed
				//#CM574233
				// Work Status
				String[] search = new String[2];
				search[0] = new String(StorageSupportParameter.STATUS_FLAG_COMPLETION);
				search[1] = new String(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(ListStoragePlanDateBusiness.WORKSTATUSSTORAGEPLANDATE_KEY, search);
			}
		}
		

		//#CM574234
		// Set Storage plan date
		forwardParam.setParameter(ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY, 
			WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));

		//#CM574235
		// Retrieval flag : Work
		forwardParam.setParameter(ListStoragePlanDateBusiness.SEARCHSTORAGEPLANDATE_KEY, 
			StorageSupportParameter.SEARCH_STORAGE_WORK);
			
		//#CM574236
		// Display the Storage plan date retrieval list box. 
		redirect(
			"/storage/listbox/liststorageplandate/ListStoragePlanDate.do", forwardParam, "/progress.do");
	
	}

	//#CM574237
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574238
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574239
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM574240
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM574241
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM574242
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574243
	/** 
	 * It is called when the item retrieval button is pressed.<BR>
	 * <BR>
	 * Outline : Set the search condition in Parameter, and display the item retrieval list box by the search condition. <BR>
	 * <BR>
	 * [Parameter]
	 *  <DIR>
	 *  	Consignor Code<BR>
	 * 		Work Status<BR>
	 *  	Storage plan date<BR>
	 *  	Item Code<BR>
	 *  </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchItemCd_Click(ActionEvent e) throws Exception
	{
		//#CM574244
		// The instance to set the search condition is declared. 
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM574245
		// Set Consignor Code
		forwardParam.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM574246
		// Pulldown : All
		if(pul_WorkStatus.getSelectedIndex() != 0)
		{

			//#CM574247
			// Pulldown : Stand by
			if(pul_WorkStatus.getSelectedIndex() == 1)
			{
				//#CM574248
				// Work Status
				String[] search = new String[2];
				//#CM574249
				// Stand by, Partially Completed
				search[0] = new String(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
				search[1] = new String(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(ListStorageItemBusiness.WORKSTATUSITEM_KEY, search);
			}
			//#CM574250
			// Pulldown : Working
			else if(pul_WorkStatus.getSelectedIndex() == 2)
			{
				//#CM574251
				// Work Status
				String[] search = new String[1];
				//#CM574252
				// Working
				search[0] = new String(StorageSupportParameter.STATUS_FLAG_WORKING);
				forwardParam.setParameter(ListStorageItemBusiness.WORKSTATUSITEM_KEY, search);
			}
			//#CM574253
			// Pulldown : Completed
			else if(pul_WorkStatus.getSelectedIndex() == 3)
			{
				//#CM574254
				// Work Status
				String[] search = new String[2];
				//#CM574255
				// Completed, Partially Completed
				search[0] = new String(StorageSupportParameter.STATUS_FLAG_COMPLETION);
				search[1] = new String(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(ListStorageItemBusiness.WORKSTATUSITEM_KEY, search);
			}
		}
		
		//#CM574256
		// Set Storage plan date
		forwardParam.setParameter(ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY, 
			WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
		//#CM574257
		// Set Item Code
		forwardParam.setParameter(ListShippingItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM574258
		// Retrieval flag : Work
		forwardParam.setParameter(ListStorageItemBusiness.SEARCHITEM_KEY, 
			StorageSupportParameter.SEARCH_STORAGE_WORK);
	
		//#CM574259
		// Display the item retrieval list box. 
		redirect("/storage/listbox/liststorageitem/ListStorageItem.do", forwardParam, "/progress.do");
		
	}

	//#CM574260
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574261
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574262
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM574263
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574264
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM574265
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574266
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM574267
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574268
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM574269
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574270
	/** 
	 * It is called when the display button is pressed.<BR>
	 * <BR>
	 * Outline : Input Item of the input area is set in Parameter, and Start Schedule. <BR>
	 * Display the retrieval result of the schedule in Preset area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the cursor in Worker Code. <BR>
	 * 		2.Input Check (Mandatory input, Number of characters, Character attribute) <BR>
	 * 		3.Begin scheduling. (Retrieve it)<BR>
	 * 		4.Maintain the search condition in ViewState. <BR>
	 * 		5.Display it in Preset area if there is display Item. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		//#CM574271
		// Make the Add/Modify button, Release button when being working in the lump, and the Clear list button unuseable.
		btn_ModifySubmit.setEnabled(false);
		btn_AllWorkingClear.setEnabled(false);
		btn_ListClear.setEnabled(false);
		
		//#CM574272
		// Clear the Preset area.
		listCellClear();
			
		//#CM574273
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);

		//#CM574274
		// Do the input check (Format, Mandatory, Restricted characters) 
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		pul_WorkStatus.validate();
		txt_StoragePlanDate.validate();
		txt_ItemCode.validate(false);
		
		Connection conn = null;
		
		try
		{
			//#CM574275
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM574276
			// Set schedule Parameter. 
			StorageSupportParameter param = new StorageSupportParameter();
			//#CM574277
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM574278
			// Password
			param.setPassword(txt_Password.getText());
			//#CM574279
			// Consignor Code 
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM574280
			// Work Status : All
			if(pul_WorkStatus.getSelectedIndex() == STATUSFLAGALL)
			{
				param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_ALL);
			}
			//#CM574281
			// Work Status : Stand by
			else if(pul_WorkStatus.getSelectedIndex() == STATUSFLAGUNSTARTED)
			{
				param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
			}
			//#CM574282
			// Work Status : Working
			else if(pul_WorkStatus.getSelectedIndex() == STATUSFLAGWORKING)
			{
				param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_WORKING);
			}
			//#CM574283
			// Work Status : Completed
			else if(pul_WorkStatus.getSelectedIndex() == STATUSFLAGCOMPLETION)
			{
				param.setWorkStatus(StorageSupportParameter.STATUS_FLAG_COMPLETION);
			}
			//#CM574284
			// Storage plan date
			param.setStoragePlanDate(WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
			//#CM574285
			// Item Code
			param.setItemCode(txt_ItemCode.getText());
			//#CM574286
			// Case/Piece flag
			//#CM574287
			// All
			if (rdo_CpfAll.getChecked())
			{
				param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_ALL);
			}
			//#CM574288
			// Case
			else if (rdo_CpfCase.getChecked())
			{
				param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_CASE);
			}
			//#CM574289
			// Piece
			else if (rdo_CpfPiece.getChecked())
			{
				param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_PIECE);
			}
			//#CM574290
			// Unspecified
			else if (rdo_CpfAppointOff.getChecked())
			{
				param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
			}
			
			//#CM574291
			// Start scheduling.
			WmsScheduler schedule = new StorageWorkMaintenanceSCH();
			StorageSupportParameter[] viewParam = (StorageSupportParameter[])schedule.query(conn, param);
			
			//#CM574292
			// End processing when some errors occur or there is no display data. 
			if( viewParam == null || viewParam.length == 0 )
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			
			//#CM574293
			// Maintain the retrieval value in ViewState. 
			setViewState(viewParam[0]);
			//#CM574294
			// Display the retrieval result in Preset area. 
			addList(viewParam);
			
			//#CM574295
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
				//#CM574296
				// Close the connection
				if(conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
		
	}

	//#CM574297
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574298
	/** 
	 * It is called when the clear button is pressed.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Initialize the input area. <BR>
	 * 		2.Set the cursor in Worker Code. <BR>
	 * 		<BR>
	 * 		<DIR>
	 *  		Item Name[Initial value]
	 *  		<DIR>
	 *  			Worker Code[As it is]<BR>
	 *  			Password[As it is]<BR>
	 *  			Consignor Code[Display only in case of one in Consignor retrieval result is available]<BR>
	 *  			Work Status[All]<BR>
	 *  			Storage plan date[None]<BR>
	 *  			Item Code[None]<BR>
	 *  			Case/Piece flag[All]<BR>
	 *  		</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM574299
		// Set Initial value in each input field. 
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM574300
		// Work Status Pulldown : All
		pul_WorkStatus.setSelectedIndex(0);
		txt_StoragePlanDate.setText("");
		txt_ItemCode.setText("");
		//#CM574301
		// Case/Piece flag radio button : All
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);

		//#CM574302
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
	}

	//#CM574303
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ModifySubmit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574304
	/** 
	 * It is called when Add/Modify button is pressed.<BR>
	 * <BR>
	 * Outline : Call <CODE>updateList(String) </CODE> Method in this Method. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_ModifySubmit_Click(ActionEvent e) throws Exception
	{
		updateList(StorageSupportParameter.BUTTON_MODIFYSUBMIT);
	}

	//#CM574305
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllWorkingClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574306
	/** 
	 * It is called when Release button being working in the lump is pressed.<BR>
	 * <BR>
	 * Outline : Call <CODE>updateList(String) </CODE> Method in this Method. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_AllWorkingClear_Click(ActionEvent e) throws Exception
	{
		updateList(StorageSupportParameter.BUTTON_ALLWORKINGCLEAR);		
	}

	//#CM574307
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574308
	/** 
	 * It is called when the clear list button is pressed.<BR>
	 * <BR>
	 * Outline : Clear all displays in Preset area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Initialize Preset area. <BR>
	 * 		2.Set the cursor in Worker Code. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		
		//#CM574309
		// Clears Preset area. 
		listCellClear();
		
		//#CM574310
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
	}


	//#CM574311
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574312
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM574313
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM574314
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM574315
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574316
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM574317
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM574318
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM574319
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SStoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574320
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRStoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574321
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRStoragePlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM574322
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRStoragePlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM574323
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStorageWorkMtn_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM574324
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStorageWorkMtn_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM574325
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStorageWorkMtn_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM574326
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStorageWorkMtn_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM574327
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStorageWorkMtn_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574328
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStorageWorkMtn_Change(ActionEvent e) throws Exception
	{
	}

	//#CM574329
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStorageWorkMtn_Click(ActionEvent e) throws Exception
	{
	}
	
	//#CM574330
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SConsignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574331
	// private method -------------------------------------------------------
	//#CM574332
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
		Connection conn = null ;
		
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM574333
			// Acquire Consignor Code from the schedule. 
			WmsScheduler schedule = new StorageWorkMaintenanceSCH();
			param = (StorageSupportParameter)schedule.initFind(conn, param);

			//#CM574334
			// Display initially when Consignor Code is one. 
			if ( param != null )
			{
				return param.getConsignorCode();
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
				//#CM574335
				// Close the connection
				if(conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
		return null;	
		
	}

	//#CM574336
	/** 
	 * Method to set the search condition in ViewState. <BR>
	 * <BR>
	 * Outline : Maintain the search condition in ViewState. <BR>
	 * <BR>
	 * <DIR>
	 * 		[ViewState information]<BR>
	 * 		<DIR>
	 * 			Worker Code<BR>
	 * 			Password<BR>
	 * 			Consignor Code<BR>
	 * 			Consignor Name<BR>
	 * 			Work Status<BR>
	 * 			Storage plan date<BR>
	 * 			Item Code<BR>
	 * 			Case/Piece flag<BR>
	 * 			Work place<BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param param StorageSupportParameter Information to display in Preset area. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setViewState(StorageSupportParameter param) throws Exception
	{
		//#CM574337
		// Worker Code
		this.getViewState().setString(WORKERCODE, txt_WorkerCode.getText());
		
		//#CM574338
		// Password
		this.getViewState().setString(PASSWORD, txt_Password.getText());
		
		//#CM574339
		// Consignor Code
		this.getViewState().setString(CONSIGNORCODE, txt_ConsignorCode.getText());
		
		//#CM574340
		// Consignor Name
		this.getViewState().setString(CONSIGNORNAME, param.getConsignorName());
		
		//#CM574341
		// Work Status
		if(pul_WorkStatus.getSelectedIndex() == STATUSFLAGALL)
		{
			//#CM574342
			// All
			this.getViewState().setString(WORKSTATUS, StorageSupportParameter.STATUS_FLAG_ALL);
		}
		else if(pul_WorkStatus.getSelectedIndex() == STATUSFLAGUNSTARTED)
		{
			//#CM574343
			// Stand by
			this.getViewState().setString(WORKSTATUS, StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		}
		else if(pul_WorkStatus.getSelectedIndex() == STATUSFLAGWORKING)
		{
			//#CM574344
			// Working
			this.getViewState().setString(WORKSTATUS, StorageSupportParameter.STATUS_FLAG_WORKING);
		}
		else if(pul_WorkStatus.getSelectedIndex() == STATUSFLAGCOMPLETION)
		{
			//#CM574345
			// Completed
			this.getViewState().setString(WORKSTATUS, StorageSupportParameter.STATUS_FLAG_COMPLETION);
		}
		//#CM574346
		// Storage plan date
		this.getViewState().setString(STORAGEPLANDATE, txt_StoragePlanDate.getText());
		
		//#CM574347
		// Item Code
		this.getViewState().setString(ITEMCODE, txt_ItemCode.getText());
		
		//#CM574348
		// Case/Piece flag
		//#CM574349
		// All
		if (rdo_CpfAll.getChecked())
		{
			this.getViewState().setString(CASEPIECEFLAG, StorageSupportParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM574350
		// Case
		else if (rdo_CpfCase.getChecked())
		{
			this.getViewState().setString(CASEPIECEFLAG, StorageSupportParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM574351
		// Piece
		else if (rdo_CpfPiece.getChecked())
		{
			this.getViewState().setString(CASEPIECEFLAG, StorageSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM574352
		// Unspecified
		else if (rdo_CpfAppointOff.getChecked())
		{
			this.getViewState().setString(CASEPIECEFLAG, StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		
		//#CM574353
		// Work place
		if (param.getSystemDiscKey() == (StorageSupportParameter.SYSTEM_DISC_KEY_FLOOR))
		{
			this.getViewState().setInt(SYSTEM_DISC_KEY, StorageSupportParameter.SYSTEM_DISC_KEY_FLOOR);
		}
		else if(param.getSystemDiscKey() == (StorageSupportParameter.SYSTEM_DISC_KEY_MOVEMENT_RACK))
		{
			this.getViewState().setInt(SYSTEM_DISC_KEY, StorageSupportParameter.SYSTEM_DISC_KEY_MOVEMENT_RACK);
		}	
		else if(param.getSystemDiscKey() == (StorageSupportParameter.SYSTEM_DISC_KEY_ASRS))
		{
			this.getViewState().setInt(SYSTEM_DISC_KEY, StorageSupportParameter.SYSTEM_DISC_KEY_ASRS);
		}
	}

	//#CM574354
	/**
	 * Set the value in Preset area. <BR>
	 * <BR>
	 * Outline : Set the value of Parameter in Preset area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set data in Concealed Item. <BR>
	 * 		2.Set data in Preset area. <BR>
	 * 		3.Set data in balloon information. <BR>
	 * 		4.Make the buttons in Preset area effective. <BR>
	 * 		<DIR>
	 * 			[List of row number of list cell]<BR>
	 * 			<DIR>
	 * 				0.Concealed Item<BR>
	 * 				1.Item Code<BR>
	 * 				2.Packed qty per case<BR>
	 * 				3.Work plan case qty<BR>
	 * 				4.Storage Case qty<BR>
	 * 				5.Storage Location<BR>
	 * 				6.Status<BR>
	 * 				7.Expiry Date<BR>
	 * 				8.Status<BR>
	 * 				9.Expiry Date<BR>
	 * 				10.Flag<BR>
	 * 				11.Results report<BR>
	 * 				12.Work place<BR>
	 * 				13.Item Name<BR>
	 * 				14.Packed qty per bundle<BR>
	 * 				15.Work plan piece qty<BR>
	 * 				16.Storage Piece qty<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param param StorageSupportParameter[] Parameter array which stores display data. 
	 * @throws Exception It reports on all exceptions.  
	 */
	private void addList(StorageSupportParameter[] param) throws Exception
	{
		StorageSupportParameter[] viewParam = param;
		
		//#CM574355
		// Consignor Code
		txt_SRConsignorCode.setText(this.getViewState().getString(CONSIGNORCODE));
		//#CM574356
		// Consignor Name
		txt_SRConsignorName.setText(this.getViewState().getString(CONSIGNORNAME));
		//#CM574357
		// Storage plan date
		txt_SRStoragePlanDate.setDate(WmsFormatter.toDate
			(this.getViewState().getString(STORAGEPLANDATE),this.getHttpRequest().getLocale()));
			
		//#CM574358
		// Item Name
		String label_itemname = DisplayText.getText("LBL-W0103");
		//#CM574359
		// Worker Code
		String label_workercode = DisplayText.getText("LBL-W0274");
		//#CM574360
		// Worker Name
		String label_workername = DisplayText.getText("LBL-W0276");
		//#CM574361
		// Expiry Date
		String label_usebydate = DisplayText.getText("LBL-W0270");
		//#CM574362
		// Case/Piece flag
		String label_casepieceflag = DisplayText.getText("LBL-W0015");
		//#CM574363
		// Results report
		String label_reportflagname = DisplayText.getText("LBL-W0389");
		//#CM574364
		// Work place
		String label_systemdisckey = DisplayText.getText("LBL-W0504");

		//#CM574365
		// LBL-W0719=----
		String noDisp = DisplayText.getText("LBL-W0719");

		//#CM574366
		// Set the value in the list cell. 
		for(int i = 0; i < viewParam.length; i++)
		{
			//#CM574367
			// Add the line to the list cell. 
			lst_SStorageWorkMtn.addRow();
			//#CM574368
			// Select the line which sets the value. 
			lst_SStorageWorkMtn.setCurrentRow(lst_SStorageWorkMtn.getMaxRows() - 1);
			
			//#CM574369
			// Connect HIDDEN Item. 
			String hidden = createHiddenList(viewParam[i]);
			
			//#CM574370
			// Set the retrieval result in each cell. 
			//#CM574371
			// HIDDEN Item
			lst_SStorageWorkMtn.setValue(0, hidden);
			//#CM574372
			// Item Code
			lst_SStorageWorkMtn.setValue(1, viewParam[i].getItemCode());
			//#CM574373
			// Packed qty per case
			lst_SStorageWorkMtn.setValue(2, WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));
			//#CM574374
			// Work plan case qty
			lst_SStorageWorkMtn.setValue(3, WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
			//#CM574375
			// Storage Case qty
			lst_SStorageWorkMtn.setValue(4, WmsFormatter.getNumFormat(viewParam[i].getResultCaseQty()));
			//#CM574376
			// Storage Location
			lst_SStorageWorkMtn.setValue(5, WmsFormatter.toDispLocation(
			        viewParam[i].getStorageLocation(), viewParam[i].getSystemDiscKey()));

			//#CM574377
			// Status
			if(viewParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED))
			{
				//#CM574378
				// Stand by
				lst_SStorageWorkMtn.setValue(6, L_STATUSFLAGUNSTARTED);
			}
			else if(viewParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_WORKING))
			{
				//#CM574379
				// Working
				lst_SStorageWorkMtn.setValue(6, L_STATUSFLAGWORKING);
			}
			else if(viewParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
			{
				//#CM574380
				// Completed
				lst_SStorageWorkMtn.setValue(6, L_STATUSFLAGCOMPLETION);
			}

			//#CM574381
			// Expiry Date
			lst_SStorageWorkMtn.setValue(7, viewParam[i].getUseByDate());
			//#CM574382
			// Case/Piece flag
			lst_SStorageWorkMtn.setValue(8, viewParam[i].getCasePieceflgName());
			//#CM574383
			// Results report
			lst_SStorageWorkMtn.setValue(9, viewParam[i].getReportFlagName());
			//#CM574384
			// Work place
			lst_SStorageWorkMtn.setValue(10, viewParam[i].getSystemDiscKeyName());			
			//#CM574385
			// Item Name
			lst_SStorageWorkMtn.setValue(11, viewParam[i].getItemName());
			//#CM574386
			// Packed qty per bundle
			lst_SStorageWorkMtn.setValue(12, WmsFormatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
			//#CM574387
			// Work plan piece qty
			lst_SStorageWorkMtn.setValue(13, WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
			//#CM574388
			// Storage Piece qty
			lst_SStorageWorkMtn.setValue(14, WmsFormatter.getNumFormat(viewParam[i].getResultPieceQty()));
			
				
			//#CM574389
			//Set the tool tip
			ToolTipHelper toolTip = new ToolTipHelper();
			toolTip.add(label_itemname, viewParam[i].getItemName());
			if (viewParam[i].getSystemDiscKey() == StorageSupportParameter.SYSTEM_DISC_KEY_ASRS)
			{
				toolTip.add(label_workercode, noDisp);
				toolTip.add(label_workername, noDisp);
			}
			else
			{
				toolTip.add(label_workercode, viewParam[i].getWorkerCode());
				toolTip.add(label_workername, viewParam[i].getWorkerName());
			}
			toolTip.add(label_usebydate, viewParam[i].getUseByDate());
			toolTip.add(label_casepieceflag, viewParam[i].getCasePieceflgName());
			toolTip.add(label_reportflagname, viewParam[i].getReportFlagName());
			toolTip.add(label_systemdisckey, viewParam[i].getSystemDiscKeyName());
			lst_SStorageWorkMtn.setToolTip(i + 1, toolTip.getText());
			
		}
		//#CM574390
		// Enable the use of Add/Modify button, Release button when being working in the lump, and the Clear list button. 
		btn_ModifySubmit.setEnabled(true);
		btn_AllWorkingClear.setEnabled(true);
		btn_ListClear.setEnabled(true);
	}

	//#CM574391
	/**
	 * Connect HIDDEN Item with one character string. <BR>
	 * <BR>
	 * Outline : Acquire Concealed Item, convert into the character string, and return it.<BR>
	 * <BR>
	 * <DIR>
	 * 		The Item order list of HIDDEN <BR>
	 * 		<DIR>
	 * 			0.Storage Case qty <BR>
	 * 			1.Storage Piece qty <BR>
	 * 			2.Storage Location <BR>
	 * 			3.Status <BR>
	 * 			4.Expiry Date <BR>
	 * 			5.Work No. <BR>
	 * 			6.Last updated date and time <BR>
	 * 			7.Work place <BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param viewParam StorageSupportParameter Parameter including HIDDEN Item.
	 * @return Connected Concealed Item. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private String createHiddenList(StorageSupportParameter viewParam)
	{
		String hidden = null;
		
		//#CM574392
		// HIDDEN Item character string making 
		ArrayList hiddenList = new ArrayList();
		//#CM574393
		// Storage Case qty
		hiddenList.add(HIDDEN0, WmsFormatter.getNumFormat(viewParam.getResultCaseQty()));
		//#CM574394
		// Storage Piece qty
		hiddenList.add(HIDDEN1, WmsFormatter.getNumFormat(viewParam.getResultPieceQty()));
		//#CM574395
		// Storage Location
		hiddenList.add(HIDDEN2, WmsFormatter.toDispLocation(
		        viewParam.getStorageLocation(), viewParam.getSystemDiscKey()));
		//#CM574396
		// Status
		if(viewParam.getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED))
		{
			//#CM574397
			// Stand by
			hiddenList.add(HIDDEN3, L_STATUSFLAGUNSTARTED);
		}
		else if(viewParam.getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_WORKING))
		{
			//#CM574398
			// Working
			hiddenList.add(HIDDEN3, L_STATUSFLAGWORKING);
		}
		else if(viewParam.getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
		{
			//#CM574399
			// Completed
			hiddenList.add(HIDDEN3, L_STATUSFLAGCOMPLETION);
		}
		//#CM574400
		// Expiry Date
		hiddenList.add(HIDDEN4, viewParam.getUseByDate());
		//#CM574401
		// Work No.
		hiddenList.add(HIDDEN5, viewParam.getJobNo());
		//#CM574402
		// Last updated date and time
		hiddenList.add(HIDDEN6, WmsFormatter.getTimeStampString(viewParam.getLastUpdateDate()));
		//#CM574403
		// Work place
		hiddenList.add(HIDDEN7, Integer.toString(viewParam.getSystemDiscKey()));
		//#CM574404
		// Connect HIDDEN Item.
		hidden = CollectionUtils.getConnectedString(hiddenList);
		
		return hidden;
	}
	
	//#CM574405
	/** 
	 * It is called when Add/Modify button and Release button being working in the lump are pressed.<BR>
	 * <BR>
	 * Outline : Update only the data to be updated among lines of Preset area. <BR>
	 * Information necessary for the update maintained in Preset area is set in Parameter, and Start Schedule. <BR>
	 * Display the retrieval result of pressing the display button and the same condition in Preset area again when the schedule completes normally.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the cursor in Worker Code. <BR>
	 * 		2.Input Item check.  (Mandatory, Character type, Restricted characters) <BR>
	 * 		3.Check on whether there is data for update. <BR>
	 * 		4.Updated information is set in Parameter and the schedule starts.<BR>
	 * 		5.Commit and Rollback of Transaction.<BR>
	 * 		6.Display Preset area again. <BR>
	 * 		<BR>
	 * 		<DIR>
	 * 			[Parameter]*MandatoryItem<BR>
	 * 			<DIR>
	 * 				Worker Code*<BR>
	 * 				Password*<BR>
	 * 				ViewState.Consignor Code<BR>
	 * 				ViewState.Work Status<BR>
	 * 				ViewState.Storage plan date<BR>
	 * 				ViewState.Item Code<BR>
	 * 				ViewState.Case/Piece flag<BR>
	 * 				Preset.Storage Case qty<BR>
	 * 				Preset.Storage Piece qty<BR>
	 * 				Preset.Storage Location<BR>
	 * 				Preset.Status<BR>
	 * 				Preset.Expiry Date<BR>
	 * 				Preset.Packed qty per case<BR>
	 * 				Push button<BR>
	 * 				Preset Line No..<BR>
	 * 				Terminal No.<BR>
	 * 				System identification key<BR>
	 * 				Preset.Work No.<BR>
	 * 				Preset.Last updated date and time<BR>
	 * 				Preset.Work place<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param pButtonType String Kind of pressed button. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private void updateList(String pButtonType) throws Exception
	{
		//#CM574406
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);

		//#CM574407
		// Do the input check of Worker Code, Password.
		txt_WorkerCode.validate();
		txt_Password.validate();
		
		Connection conn = null;
			
		try
		{
			//#CM574408
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			//#CM574409
			// The title of the list cell is acquired, and it stores it in ListCellColumn. 
			ArrayList lst = (ArrayList) lst_SStorageWorkMtn.getListCellColumns();
			ListCellColumn[] List_Title = new ListCellColumn[lst.size() + 1];
			for (int l = 0; l < lst.size(); l++)
			{
				ListCellColumn listtemp = (ListCellColumn) lst.get(l);
				List_Title[l + 1] = new ListCellColumn();
				List_Title[l + 1] = (ListCellColumn) listtemp;
			}

			//#CM574410
			// The Preset elements are checked and only the updated line sets the value in Vector. 
			Vector vecParam = new Vector(lst_SStorageWorkMtn.getMaxRows());
			for(int i = 1; i < lst_SStorageWorkMtn.getMaxRows(); i++)
			{
				//#CM574411
				// Select the work line. 
				lst_SStorageWorkMtn.setCurrentRow(i);
				
				//#CM574412
				// Do the input check of Preset area.(Storage Case qty, Storage Location, Status, Expiry Date, Storage Piece qty)
				//#CM574413
				// Storage Case qty
				lst_SStorageWorkMtn.validate(4, false);
				//#CM574414
				// Storage Location
				lst_SStorageWorkMtn.validate(5, false);
				//#CM574415
				// Status
				lst_SStorageWorkMtn.validate(6, true);
				//#CM574416
				// Expiry Date
				lst_SStorageWorkMtn.validate(7, false);
				//#CM574417
				// Storage Piece qty
				lst_SStorageWorkMtn.validate(14, false); //13→14  2005.12.07 ishizaki changed

				//#CM574418
				// Input character check for eWareNavi
				if (!checkContainNgText(i))
				{
					return;
				}

				//#CM574419
				// Acquire HIDDEN Item.
				String hidden = lst_SStorageWorkMtn.getValue(0);
				//#CM574420
				// Set the value in Vector to process it in case of the update object. 
				if(isChangeData(pButtonType))
				{
				    //#CM574421
				    // Location cannot be changed in case of the work of AS/RS. 
				    if (!lst_SStorageWorkMtn.getValue(5).equals(CollectionUtils.getString(HIDDEN2, hidden)) && 
				            Integer.parseInt(CollectionUtils.getString(HIDDEN7, hidden)) ==
				                    SystemDefine.SYSTEM_DISC_KEY_ASRS)
				    {
				        //#CM574422
				        //Set Message
				        message.setMsgResourceKey("6023453");
				        return;
				    }
				    
					StorageSupportParameter param = new StorageSupportParameter();
						
					//#CM574423
					// Set Worker Code and Password
					param.setWorkerCode(txt_WorkerCode.getText());
					param.setPassword(txt_Password.getText());
					
					//#CM574424
					// Set search condition for re-display
					//#CM574425
					// (Consignor Code, Work Status, Storage plan date, Item Code, Case/Piece flag)
					//#CM574426
					// Consignor Code
					param.setConsignorCode(this.getViewState().getString(CONSIGNORCODE));
					//#CM574427
					// Work Status
					param.setWorkStatus(this.getViewState().getString(WORKSTATUS));
					//#CM574428
					// Storage plan date
					param.setStoragePlanDate
						(WmsFormatter.toParamDate(this.getViewState().getString
							(STORAGEPLANDATE), this.getHttpRequest().getLocale()));
					//#CM574429
					// Item Code
					param.setItemCode(this.getViewState().getString(ITEMCODE));
					//#CM574430
					// Case/Piece flag
					param.setCasePieceflg(this.getViewState().getString(CASEPIECEFLAG));
					
					
					//#CM574431
					// The update value is set. 
					//#CM574432
					// (Storage Case qty, Storage Piece qty, Storage Location, Status, Expiry Date, Packed qty per case, Push button, Preset line, Terminal No.)
					//#CM574433
					// Storage Case qty	
					param.setResultCaseQty(WmsFormatter.getInt(lst_SStorageWorkMtn.getValue(4)));
					//#CM574434
					// Storage Piece qty
					param.setResultPieceQty(WmsFormatter.getInt(lst_SStorageWorkMtn.getValue(14)));
					//#CM574435
					// Storage Location 
					param.setStorageLocation(lst_SStorageWorkMtn.getValue(5));
					//#CM574436
					// Status
					//#CM574437
					// Status : Stand by
					if(lst_SStorageWorkMtn.getValue(6).equals(L_STATUSFLAGUNSTARTED))
					{
						param.setStatusFlagL(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
					}
					//#CM574438
					// Status : Working
					else if(lst_SStorageWorkMtn.getValue(6).equals(L_STATUSFLAGWORKING))
					{
						param.setStatusFlagL(StorageSupportParameter.STATUS_FLAG_WORKING);
					}
					//#CM574439
					// Status : Completed
					else if(lst_SStorageWorkMtn.getValue(6).equals(L_STATUSFLAGCOMPLETION))
					{
						param.setStatusFlagL(StorageSupportParameter.STATUS_FLAG_COMPLETION);
						
						if((WmsFormatter.getInt(lst_SStorageWorkMtn.getValue(4)) * 
							WmsFormatter.getInt(lst_SStorageWorkMtn.getValue(2))) + 
					        (WmsFormatter.getInt(lst_SStorageWorkMtn.getValue(14))) != 0 ){
						    //#CM574440
						    // Storage Location Mandatory Input check
						    lst_SStorageWorkMtn.validate(5, true);
						}
						
					}
					//#CM574441
					// Expiry Date
					param.setUseByDate(lst_SStorageWorkMtn.getValue(7));
					//#CM574442
					// Packed qty per case
					param.setEnteringQty(WmsFormatter.getInt(lst_SStorageWorkMtn.getValue(2)));
					//#CM574443
					// Push button
					param.setButtonType(pButtonType);
					//#CM574444
					// Preset line
					param.setRowNo(lst_SStorageWorkMtn.getCurrentRow());
					//#CM574445
					// Terminal No.
					UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
					param.setTerminalNumber(userHandler.getTerminalNo());
					//#CM574446
					// System identification key
					param.setSystemDiscKey(this.getViewState().getInt(SYSTEM_DISC_KEY));
					//#CM574447
					// Set the HIDDEN item
					//#CM574448
					// (Work No.(5), Last updated date and time(6), Work place(7))
					param.setJobNo(CollectionUtils.getString(HIDDEN5, hidden));
					param.setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(HIDDEN6, hidden)));
					param.setSystemDiscKeyName(CollectionUtils.getString(HIDDEN7, hidden));
					vecParam.addElement(param);
				}
			}
				
			//#CM574449
			// "There is no data for update" processing is ended in case of number 0 of elements. 
			if(vecParam.size() == 0)
			{
				message.setMsgResourceKey("6023154");
				return;
			}
				
			//#CM574450
			// Copy the value onto Parameter. 
			StorageSupportParameter[] paramArray = new StorageSupportParameter[vecParam.size()];
			vecParam.copyInto(paramArray);
				
			//#CM574451
			// Schedule start
			WmsScheduler schedule = new StorageWorkMaintenanceSCH();
			StorageSupportParameter[] viewParam = (StorageSupportParameter[])schedule.startSCHgetParams(conn, paramArray);
			
			//#CM574452
			// It fails in the update if Return Data is null. 
			//#CM574453
			// Set Rollback and the message. (Preset with the data displayed before)
			if( viewParam == null )
			{
				//#CM574454
				// Rollback
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
				
			//#CM574455
			// It succeeds in the update if the number of elements of Return Data is 0 or more. 
			//#CM574456
			// Do the setting of the transaction and display again. 
			else if( viewParam.length >= 0 )
			{
				//#CM574457
				// Commit
				conn.commit();
				//#CM574458
				// Display it when the schedule is normally completed and the display data is acquired. 
				//#CM574459
				// Clear the Preset area.
				listCellClear();
				if( viewParam.length > 0 )
				{
					//#CM574460
					// Display Preset area again. 
					addList(viewParam);
				}

			}
				
			//#CM574461
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
				//#CM574462
				// Close the connection
				if(conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
	}

	//#CM574463
	/** 
	 * Check whether input information on the list cell is an update object. <BR>
	 * <BR>
	 * Outline : the data of the list cell returns True when updating and when it is not so, returns false.  <BR>
	 * <BR> 
	 * @param pButtonType String Kind of pushed button. 
	 * @return Check result (When updating it : True When not updating it : false) 
	 * @throws Exception It reports on all exceptions. 
	 */
	private boolean isChangeData(String pButtonType) throws Exception
	{
		//#CM574464
		// When the update registration button is pressed
		if(pButtonType.equals(StorageSupportParameter.BUTTON_MODIFYSUBMIT))
		{
			//#CM574465
			// Acquire HIDDEN Item.
			String hidden = lst_SStorageWorkMtn.getValue(0);
			//#CM574466
			// Storage Case qty
			String orgCaseQty = CollectionUtils.getString(HIDDEN0, hidden);
			//#CM574467
			// Storage Piece qty
			String orgPieceQty = CollectionUtils.getString(HIDDEN1, hidden);
			//#CM574468
			// Storage Location
			String orgLocation = CollectionUtils.getString(HIDDEN2, hidden);
			//#CM574469
			// Status
			String orgStatus = CollectionUtils.getString(HIDDEN3, hidden);
			//#CM574470
			// Expiry Date
			String orgUseByDate = CollectionUtils.getString(HIDDEN4, hidden);
			
			//#CM574471
			// Do not process it when nothing has been changed to the input text. 
			//#CM574472
			// Storage Case qty
			if(!lst_SStorageWorkMtn.getValue(4).equals(orgCaseQty))
			{
				return true;
			}
			//#CM574473
			// Location
			else if(!lst_SStorageWorkMtn.getValue(5).equals(orgLocation))
			{
			    return true;
			}
			//#CM574474
			// Status
			else if(!lst_SStorageWorkMtn.getValue(6).equals(orgStatus))
			{
				return true;
			}
			else if( lst_SStorageWorkMtn.getValue(6).equals(L_STATUSFLAGCOMPLETION)
			 && !lst_SStorageWorkMtn.getValue(7).equals(orgUseByDate))
			{
				return true;
			}
			//#CM574475
			// Storage Piece qty
			else if(!lst_SStorageWorkMtn.getValue(14).equals(orgPieceQty))
			{
				return true;
			}
			//#CM574476
			// When nothing has been changed
			else
			{
				return false;
			}
		}
		//#CM574477
		// When release is pressed while working in the lump
		else
		{
			//#CM574478
			// Acquire HIDDEN Item.
			String hidden = lst_SStorageWorkMtn.getValue(0);
			String orgStatus = CollectionUtils.getString(HIDDEN3, hidden);

			//#CM574479
			// Do not process if "Status" is Stand by or Completed when initial data is displayed.
			if(orgStatus.equals(L_STATUSFLAGUNSTARTED) || orgStatus.equals(L_STATUSFLAGCOMPLETION))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}

	//#CM574480
	/** 
	 * Method which clears the Preset area.<BR>
	 * <BR>
	 * Outline : Initialize Preset area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Invalidate all buttons of Preset area. <BR>
	 * 		2.Clear the list cell, consignor code and consignor name (Read only item).<BR>
	 * </DIR>
	 * <BR>
	 * @throws Exception It reports on all exceptions. 
	 */
	private void listCellClear() throws Exception
	{
		//#CM574481
		// Preset area is deleted.
		//#CM574482
		// Item only for reading is deleted. 
		txt_SRConsignorCode.setText("");
		txt_SRConsignorName.setText("");
		txt_SRStoragePlanDate.setText("");

		//#CM574483
		// Preset area is deleted. 
		lst_SStorageWorkMtn.clearRow();
		
		//#CM574484
		// Make the Add/Modify button, Release button when being working in the lump, Clear list button unuseable.
		btn_ModifySubmit.setEnabled(false);
		btn_AllWorkingClear.setEnabled(false);
		btn_ListClear.setEnabled(false);
	}
	//#CM574485
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Maintenance_Click(ActionEvent e) throws Exception
	{
	}


}
//#CM574486
//end of class
