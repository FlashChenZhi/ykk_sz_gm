// $Id: StoragePlanModifyBusiness.java,v 1.2 2006/12/07 08:57:14 suresh Exp $

//#CM573048
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.storageplanmodify;
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
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststoragelocation.ListStorageLocationBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplandate.ListStoragePlanDateBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplanmodify.ListStoragePlanModifyBusiness;
import jp.co.daifuku.wms.storage.schedule.StoragePlanModifySCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM573049
/**
 * Designer : T.Uehata<BR>
 * Maker    : T.Uehata <BR>
 * <BR>
 * Stock Plan correction , and the Deletion(Essential information Setting) class.  <BR>
 * The content input from Screen is set in ViewState, and it changes to Stock Plan correction  and Deletion Screen (detailed information correction and Deletion) based on the Parameter. <BR>
 * Receive true when it is normal in Input Check and receive false at the condition error. <BR>
 * Result of Input Check, Output the Message acquired from the schedule to the screen. <BR>
 * <BR>
 * Process it in this class as follows.  <BR>
 * <BR>
 * 1.processing when Next button is pressed( <CODE>btn_Next_Click</CODE>  Method) <BR>
 * <BR>
 * <DIR>
 * 		Set the content input from the input area in Parameter, and the schedule checks the input condition based on the Parameter. <BR>
 * 		Receive the result from the schedule, receive true normally, and receive false at the error. <BR>
 * 		The result of the schedule outputs the message acquired from the schedule to the screen. <BR>
 * 		Input Item of Input area is set in ViewState, and it changes to Stock Plan correction  and Deletion Screen (detailed information correction and Deletion) based on the Parameter when the result is true. <BR>
 * 		<BR>
 * 		[ViewState information]*Mandatory input <BR>
 * 		<BR>
 * 		<DIR>
 * 			Worker Code* <BR>
 * 			Password* <BR>
 * 			Consignor Code* <BR>
 * 			Storage plan date* <BR>
 * 			Item Code* <BR>
 * 			CaseStorage Location <BR>
 * 			PieceStorage Location <BR>
 * 			Plan making Flag* <BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>T.Uehata</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/05/17</TD><TD>T.Nakajima</TD><TD>Addition of Corresponding Add Flag [2 : For Receiving]</TD></TR>
 * </TABLE> 
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:14 $
 * @author  $Author: suresh $
 */
public class StoragePlanModifyBusiness extends StoragePlanModify implements WMSConstants
{
	//#CM573050
	// Address transition ahead
	//#CM573051
	/**
	 * Consignor retrieval pop up address
	 */
	private static final String DO_SRCH_CONSIGNOR =
		"/storage/listbox/liststorageconsignor/ListStorageConsignor.do";
	
	//#CM573052
	/**
	 * Storage plan date retrieval pop up address
	 */
	private static final String DO_SRCH_STORAGEPLANDATE =
		"/storage/listbox/liststorageplandate/ListStoragePlanDate.do";
	
	//#CM573053
	/**
	 * Item list Retrieval Popup address
	 */
	private static final String DO_SRCH_ITEM =
		"/storage/listbox/liststorageitem/ListStorageItem.do";
	
	//#CM573054
	/**
	 * Storage Location retrieval Popup address
	 */
	protected static final String DO_SRCH_STRGLCT =
		"/storage/listbox/liststoragelocation/ListStorageLocation.do";

	//#CM573055
	/**
	 * Storage PlanRetrieval Popup address
	 */
	private static final String DO_SRCH_STORAGEPLAN =
		"/storage/listbox/liststorageplanmodify/ListStoragePlanModify.do";

	//#CM573056
	/**
	 * Stock Plan correctionDeletion(Essential information Setting Screen) address
	 */
	protected static final String DO_MODIFY = "/storage/storageplanmodify/StoragePlanModify.do";
	
	//#CM573057
	/**
	 * Stock Plan correctionDeletion(Detailed information Correction/Deletion Screen) address
	 */
	private static final String DO_MODIFY2 = "/storage/storageplanmodify/StoragePlanModify2.do";
	
	//#CM573058
	/**
	 * Screen address when Retrieval pop up is being called
	 */
	protected static final String DO_SRCH_PROCESS = "/progress.do";

	//#CM573059
	// Delivery key of ViewState information
	//#CM573060
	/**
	 * For ViewState information : Worker Code
	 */
	protected static final String WORKERCODE = "WORKERCODE";

	//#CM573061
	/**
	 * For ViewState information : Password
	 */
	protected static final String PASSWORD = "PASSWORD";

	//#CM573062
	/**
	 * For ViewState information : Message
	 */
	protected static final String MESSAGE = "MESSAGE";

	//#CM573063
	/**
	 * For ViewState information : Title
	 */
	protected static final String TITLE = "TITLE";

	//#CM573064
	/**
	 * For ViewState information : Plan making Flag
	 */
	protected static final String REGISTKIND = "REGISTKIND";

	//#CM573065
	// Class fields --------------------------------------------------

	//#CM573066
	// Class variables -----------------------------------------------

	//#CM573067
	// Class method --------------------------------------------------

	//#CM573068
	// Constructors --------------------------------------------------

	//#CM573069
	// Public methods ------------------------------------------------

	//#CM573070
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
			//#CM573071
			//Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM573072
			//Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM573073
			//Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
		//#CM573074
		// Focus is set in Worker Code. 
		setFocus(txt_WorkerCode);
	}

	//#CM573075
	/**
	 * When returning from the pop up window, this Method is called. <BR>
	 * Override the <CODE>page_DlgBack</CODE> set in <CODE>Page</CODE>.<BR>
	 * <BR>
	 * Outline:Acquire and set Return Data of the retrieval screen. <BR>
	 * <BR>
	 * <DIR>
	 *      1.Acquire the value returned from the retrieval screen of pop up. <BR>
	 *      2.Set it on the screen when the value is not empty. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM573076
		// Parameter selected from list box is acquired. 
		//#CM573077
		// Consignor Code
		String consignorcode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM573078
		// Storage plan date
		String storageplandate =
			param.getParameter(ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY);
		//#CM573079
		// Item Code
		String itemcode = param.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM573080
		// CaseStorage Location
		String casestoragelocation =
			param.getParameter(ListStoragePlanModifyBusiness.CASESTRGLOCATION_KEY);
		//#CM573081
		// PieceStorage Location
		String piecestoragelocation =
			param.getParameter(ListStoragePlanModifyBusiness.PIECESTRGLOCATION_KEY);

		//#CM573082
		// Set the value when it is not empty. 
		//#CM573083
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM573084
		// Storage plan date
		if (!StringUtil.isBlank(storageplandate))
		{
			txt_StoragePlanDate.setText(storageplandate);
		}
		//#CM573085
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM573086
		// CaseStorage Location
		if (!StringUtil.isBlank(casestoragelocation))
		{
			txt_CaseStorageLocation.setText(casestoragelocation);
		}
		//#CM573087
		// PieceStorage Location
		if (!StringUtil.isBlank(piecestoragelocation))
		{
			txt_PieseStorageLocation.setText(piecestoragelocation);
		}
	}

	//#CM573088
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Outline:Display initial data in the screen. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Display the title. <BR>
	 * 		2.Initialize the input area. <BR>
	 * 		3.Set the value in Input area when the value is set in ViewState when the returning button is pressed. <BR>
	 * 		<BR>
	 * 		<DIR>
	 * 			Item Name[Initial value]<BR>
	 * 			<DIR>
	 * 				Worker Code*1[None]<BR>
	 * 				Password*1[None]<BR>
	 * 				Consignor Code*1[Display only in case of one in Consignor retrieval result is available]<BR>
	 * 				Storage plan date*1[None]<BR>
	 * 				Item Code*1[None]<BR>
	 * 				CaseStorage Location*1[None]<BR>
	 * 				PieceStorage Location*1[None]<BR>
	 * 				Plan making Flag*1[Loading , ScreenAdd]<BR>
	 * 				<BR>
	 * 				*1<BR>
	 * 				Set the value of ViewState information when returning from the second screen. <BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM573089
		// Set Title when returning from 2nd Screen. 
		if (!StringUtil.isBlank(this.viewState.getString(TITLE)))
		{
			lbl_SettingName.setResourceKey(this.viewState.getString(TITLE));
		}

		//#CM573090
		// The tab of Essential information Setting former is put out. 
		tab_Bsc_DtlMdfyDlt.setSelectedIndex(1);

		//#CM573091
		// Make Screen first stage Status. 
		setInitView(true);

		//#CM573092
		// Storage Plan maintenance correction and Deletion (details). When return Button is pressed 
		//#CM573093
		// Set the value if the value is set in ViewState. 
		//#CM573094
		// Worker code
		if (!StringUtil.isBlank(this.viewState.getString(WORKERCODE)))
		{
			txt_WorkerCode.setText(this.viewState.getString(WORKERCODE));
		}
		//#CM573095
		// Password
		if (!StringUtil.isBlank(this.getViewState().getString(PASSWORD)))
		{
			txt_Password.setText(this.getViewState().getString(PASSWORD));
		}
		//#CM573096
		// Consignor Code
		if (!StringUtil
			.isBlank(this.getViewState().getString(ListStorageConsignorBusiness.CONSIGNORCODE_KEY)))
		{
			txt_ConsignorCode.setText(
				this.getViewState().getString(ListStorageConsignorBusiness.CONSIGNORCODE_KEY));
		}
		//#CM573097
		// Storage plan date
		if (!StringUtil
			.isBlank(
				this.getViewState().getString(ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY)))
		{
			txt_StoragePlanDate.setText(
				this.getViewState().getString(ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY));
		}
		//#CM573098
		// Item Code
		if (!StringUtil
			.isBlank(this.getViewState().getString(ListStorageItemBusiness.ITEMCODE_KEY)))
		{
			txt_ItemCode.setText(
				this.getViewState().getString(ListStorageItemBusiness.ITEMCODE_KEY));
		}
		//#CM573099
		//  CaseStorage Location
		if (!StringUtil
			.isBlank(
				this.getViewState().getString(ListStoragePlanModifyBusiness.CASESTRGLOCATION_KEY)))
		{
			txt_CaseStorageLocation.setText(
				this.getViewState().getString(ListStoragePlanModifyBusiness.CASESTRGLOCATION_KEY));
		}
		//#CM573100
		// Piece Storage Location
		if (!StringUtil
			.isBlank(
				this.getViewState().getString(
					ListStoragePlanModifyBusiness.PIECESTRGLOCATION_KEY)))
		{
			txt_PieseStorageLocation.setText(
				this.getViewState().getString(ListStoragePlanModifyBusiness.PIECESTRGLOCATION_KEY));
		}

		//#CM573101
		// Plan making Flag
		if (StorageSupportParameter.REGIST_KIND_NOT_INSTOCK.equals(this.viewState.getString(REGISTKIND)))
		{
			rdo_Rgst_Kind_Strg.setChecked(true);
			rdo_Rgst_Kind_Inst.setChecked(false);
		}
		else if (SystemDefine.REGIST_KIND_INSTOCK.equals(this.viewState.getString(REGISTKIND)))
	    {
			rdo_Rgst_Kind_Strg.setChecked(false);
			rdo_Rgst_Kind_Inst.setChecked(true);
		}

		//#CM573102
		// Message
		if (!StringUtil.isBlank(this.getViewState().getString(MESSAGE)))
		{
			message.setMsgResourceKey(this.getViewState().getString(MESSAGE));
		}
	}

	//#CM573103
	// Package methods -----------------------------------------------

	//#CM573104
	// Protected methods ---------------------------------------------

	//#CM573105
	// Private methods -----------------------------------------------

	//#CM573106
	/**
	 * Method to acquire Consignor Code from the schedule.  <BR>
	 * <BR>
	 * Outline : Return Consignor Code acquired from the schedule. <BR>
	 * <DIR>
	 *   1.Return corresponding Consignor when the schedule result is not null. Return the empty string for null. <BR>
	 * <DIR>
	 * @return Consignor Code
	 * @throws Exception It reports on all exceptions. 
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM573107
			// Acquire the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM573108
			// Acquire Consignor Code from the schedule. 
			WmsScheduler schedule = new StoragePlanModifySCH();
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
			//#CM573109
			// Close the connection
			if (conn != null)
			{
				conn.close();
			}
		}
		return "";
	}

	//#CM573110
	/**
	 * Initialize the input area. <BR>
	 * <BR>
	 * Outline : Set Initial value to each Item of Input area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set Initial value in each Item. <BR>
	 * 		2.Set the cursor in Worker Code. <BR>
	 * 		<BR>
	 *    	Item Name[Initial value]<BR>
	 *		<BR>
	 *    	<DIR>
	 * 			Worker Code[*1] <BR>
	 * 			Password[*1] <BR>
	 * 			Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Storage plan date[None] <BR>
	 * 			Item Code[None] <BR>
	 * 			CaseStorage Location[None] <BR>
	 * 			PieceStorage Location[None]
	 * 			Plan making Flag[Loading , ScreenAdd] <BR>
	 * 			<BR>
	 * 			*1<BR>
	 * 			<DIR>
	 * 				Clear when wkrClrFlg is true. <BR>
	 * 				Do not Clear when wkrClrFlg is false. <BR>
	 * 			</DIR>
	 *   	</DIR>
	 * </DIR>
	 * <BR>
	 * @param wkrClrFlg boolean Clear flag.
	 * @throws Exception It reports on all exceptions.  
	 */
	private void setInitView(boolean wkrClrFlg) throws Exception
	{
		//#CM573111
		// Initialize input Item. 
		if (wkrClrFlg)
		{
			//#CM573112
			// Worker code
			txt_WorkerCode.setText("");
			//#CM573113
			// Password
			txt_Password.setText("");
		}
		//#CM573114
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM573115
		// Storage plan date
		txt_StoragePlanDate.setText("");
		//#CM573116
		// Item Code
		txt_ItemCode.setText("");
		//#CM573117
		//  CaseStorage Location
		txt_CaseStorageLocation.setText("");
		//#CM573118
		// Piece Storage Location
		txt_PieseStorageLocation.setText("");

		//#CM573119
		// Plan making Flag
		rdo_Rgst_Kind_Strg.setChecked(true);
		rdo_Rgst_Kind_Inst.setChecked(false);

		//#CM573120
		// Focus Movement in Worker code. 
		setFocus(txt_WorkerCode);
	}

	//#CM573121
	// Event handler methods -----------------------------------------
	//#CM573122
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573123
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573124
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573125
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

	//#CM573126
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573127
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573128
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573129
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573130
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573131
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573132
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573133
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573134
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573135
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573136
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573137
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573138
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573139
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573140
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573141
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573142
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
	public void btn_PsearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		//#CM573143
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573144
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM573145
		// Retrieval flag : Plan
		param.setParameter(
			ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY,
			StorageSupportParameter.SEARCH_STORAGE_PLAN);
		//#CM573146
		// Status
		param.setParameter(
			ListStorageConsignorBusiness.WORKSTATUSCONSIGNOR_KEY,
			StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		
		//#CM573147
		// Processing Screen -> Result screen
		redirect(DO_SRCH_CONSIGNOR, param, DO_SRCH_PROCESS);
	}

	//#CM573148
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573149
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573150
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StoragePlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573151
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StoragePlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573152
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573153
	/** 
	 * It is called when the retrieval button of Storage plan date is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Storage plan date list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Storage plan date <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PsearchStrgPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM573154
		// Set the search condition on the Storage plan date retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573155
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM573156
		// Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));			
		//#CM573157
		// Retrieval flag
		param.setParameter(ListStoragePlanDateBusiness.SEARCHSTORAGEPLANDATE_KEY, 
			StorageSupportParameter.SEARCH_STORAGE_PLAN);
		//#CM573158
		// Status
		param.setParameter(
				ListStoragePlanDateBusiness.WORKSTATUSSTORAGEPLANDATE_KEY,
			StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		
		//#CM573159
		// Processing Screen -> Result screen
		redirect(DO_SRCH_STORAGEPLANDATE, param, DO_SRCH_PROCESS);
	}

	//#CM573160
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573161
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573162
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573163
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573164
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573165
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchItem_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573166
	/** 
	 * It is called when the retrieval button of Item Code is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the item list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]<BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Storage plan date <BR>
	 *       Item Code <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PsearchItem_Click(ActionEvent e) throws Exception
	{
		//#CM573167
		// Set the search condition on the Item retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573168
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM573169
		// Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));		
		//#CM573170
		// Item Code
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM573171
		// Retrieval flag : Plan
		param.setParameter(
			ListStorageItemBusiness.SEARCHITEM_KEY,
			StorageSupportParameter.SEARCH_STORAGE_PLAN);
		//#CM573172
		// Status
		param.setParameter(
			ListStorageItemBusiness.WORKSTATUSITEM_KEY,
			StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		
		//#CM573173
		// Processing Screen -> Result screen
		redirect(DO_SRCH_ITEM, param, DO_SRCH_PROCESS);
	}

	//#CM573174
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PStrgPlanSrch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573175
	/** 
	 * It is called when stock Plan retrieval button is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display stock Plan retrieval list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input <BR>
	 *    <DIR>
	 *       Consignor Code* <BR>
	 *       Storage plan date <BR>
	 *       Item Code <BR>
	 *       CaseStorage Location <BR>
	 *       PieceStorage Location <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PStrgPlanSrch_Click(ActionEvent e) throws Exception
	{
		//#CM573176
		// Set the search condition on the Item retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573177
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM573178
		// Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
		//#CM573179
		// Item Code
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM573180
		// CaseStorage Location
		param.setParameter(
			ListStoragePlanModifyBusiness.CASESTRGLOCATION_KEY,
			txt_CaseStorageLocation.getText());
		//#CM573181
		// PieceStorage Location
		param.setParameter(
			ListStoragePlanModifyBusiness.PIECESTRGLOCATION_KEY,
			txt_PieseStorageLocation.getText());

		//#CM573182
		// Status
		param.setParameter(
			ListStoragePlanModifyBusiness.STATUS_KEY,
			StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		
		//#CM573183
		// Processing Screen -> Result screen
		redirect(DO_SRCH_STORAGEPLAN, param, DO_SRCH_PROCESS);
	}

	//#CM573184
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573185
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStorageLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573186
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStorageLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573187
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStorageLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573188
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStorageLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573189
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchCaseStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573190
	/** 
	 * CaseIt is called when the retrieval button of Storage Location is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Storage Location list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Storage plan date <BR>
	 *       Item Code <BR>
	 *       CaseStorage Location <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PsearchCaseStrgLct_Click(ActionEvent e) throws Exception
	{
		//#CM573191
		// Set the search condition on the Item retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573192
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM573193
		// Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
		//#CM573194
		// Item Code
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM573195
		// CaseStorage Location
		param.setParameter(
			ListStoragePlanModifyBusiness.CASESTRGLOCATION_KEY,
			txt_CaseStorageLocation.getText());
		//#CM573196
		// Flag for Retrieval
		param.setParameter(
			ListStorageLocationBusiness.SEARCH_CASEPIECE_FLAG_KEY,
			StorageSupportParameter.CASEPIECE_FLAG_CASE);
		//#CM573197
		// Retrieval flag(Plan)
		param.setParameter(
			ListStorageLocationBusiness.SEARCH_STORAGE_LOCATION_KEY,
			StorageSupportParameter.SEARCH_STORAGE_PLAN);

		//#CM573198
		// Status
		param.setParameter(
			ListStorageLocationBusiness.SEARCH_WORKSTATUSLOCATION_KEY,
			StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		
		//#CM573199
		// Processing Screen -> Result screen
		redirect(DO_SRCH_STRGLCT, param, DO_SRCH_PROCESS);
	}

	//#CM573200
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PiceStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573201
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieseStorageLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573202
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieseStorageLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573203
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieseStorageLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573204
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieseStorageLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573205
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchPieceStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573206
	/** 
	 * PieceIt is called when the retrieval button of Storage Location is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Storage Location list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Storage plan date <BR>
	 *       Item Code <BR>
	 *       CaseStorage Location <BR>
	 *       PieceStorage Location <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PsearchPieceStrgLct_Click(ActionEvent e) throws Exception
	{
		//#CM573207
		// Set the search condition on the Item retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573208
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM573209
		// Storage plan date
		param.setParameter(
			ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
		//#CM573210
		// Item Code
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM573211
		// PieceStorage Location
		param.setParameter(
			ListStoragePlanModifyBusiness.PIECESTRGLOCATION_KEY,
			txt_PieseStorageLocation.getText());
		//#CM573212
		// Flag for Retrieval
		param.setParameter(
			ListStorageLocationBusiness.SEARCH_CASEPIECE_FLAG_KEY,
			StorageSupportParameter.CASEPIECE_FLAG_PIECE);
		//#CM573213
		// Retrieval flag(Plan)
		param.setParameter(
			ListStorageLocationBusiness.SEARCH_STORAGE_LOCATION_KEY,
			StorageSupportParameter.SEARCH_STORAGE_PLAN);
		//#CM573214
		// Status
		param.setParameter(
			ListStorageLocationBusiness.SEARCH_WORKSTATUSLOCATION_KEY,
			StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		//#CM573215
		// Processing Screen -> Result screen
		redirect(DO_SRCH_STRGLCT, param, DO_SRCH_PROCESS);
	}

	//#CM573216
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Next_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573217
	/**
	 * It is called when the Next button is pressed.<BR>
	 * <BR>
	 * Outline : Changes to Stock Plan correction  and Deletion Screen (detailed information correction and Deletion) contingent on input Item of Input area. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Set the cursor in Worker Code. <BR>
	 *    2.Check Input area input Item. (Mandatory input, Number of characters, Character attribute)<BR>
	 *    3.Start Schedule.<BR>
	 * 	  <DIR>
	 *   	[Parameter] *Mandatory input<BR>
	 *   	<DIR>
	 * 			Worker Code* <BR>
	 * 			Password* <BR>
	 * 			Consignor Code* <BR>
	 * 			Storage plan date* <BR>
	 * 			Item Code* <BR>
	 * 			CaseStorage Location <BR>
	 * 			PieceStorage Location <BR>
	 * 			Plan making Flag* <BR>
	 *      </DIR>
	 *    </DIR>
	 *    4-1.If the result of the schedule is true and input Item are stored in ViewState, and it changes to Stock Plan correction  and Deletion Screen (detailed information correction and Deletion). <BR>
	 *    4-2.When the result of the schedule is false, Output the Message acquired from the schedule to the screen.  <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Next_Click(ActionEvent e) throws Exception
	{
		//#CM573218
		// Acquire the connection. 
		Connection conn = null;
		
		try
		{
		
			//#CM573219
			// Set the cursor in Worker Code. 
			setFocus(txt_WorkerCode);
	
			//#CM573220
			// Mandatory Input check
			//#CM573221
			// Worker Code
			txt_WorkerCode.validate();
			//#CM573222
			// Password
			txt_Password.validate();
			//#CM573223
			// Consignor Code
			txt_ConsignorCode.validate();
			//#CM573224
			// Storage plan date
			txt_StoragePlanDate.validate();
			//#CM573225
			// Item Code
			txt_ItemCode.validate();
	
			//#CM573226
			// Pattern matching
			//#CM573227
			// CaseStorage Location
			txt_CaseStorageLocation.validate(false);
			//#CM573228
			// PieceStorage Location
			txt_PieseStorageLocation.validate(false);
	
			//#CM573229
			// Set Parameter
			StorageSupportParameter sparam = new StorageSupportParameter();
	
			//#CM573230
			// Worker Code
			sparam.setWorkerCode(txt_WorkerCode.getText());
			//#CM573231
			// Password
			sparam.setPassword(txt_Password.getText());
			//#CM573232
			// Consignor Code
			sparam.setConsignorCode(txt_ConsignorCode.getText());
			//#CM573233
			// Storage plan date
			sparam.setStoragePlanDate(WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
			//#CM573234
			// Item Code
			sparam.setItemCode(txt_ItemCode.getText());
			//#CM573235
			// CaseStorage Location
			sparam.setCaseLocation(txt_CaseStorageLocation.getText());
			//#CM573236
			// PieceStorage Location
			sparam.setPieceLocation(txt_PieseStorageLocation.getText());
			//#CM573237
			// Plan making Flag
			if (rdo_Rgst_Kind_Strg.getChecked())
			{
				//#CM573238
				// Loading , Screen
				sparam.setRegistKbn(StorageSupportParameter.REGIST_KIND_NOT_INSTOCK);
			}
			else if (rdo_Rgst_Kind_Inst.getChecked())
			{
				//#CM573239
				// For Receiving
				sparam.setRegistKbn(SystemDefine.REGIST_KIND_INSTOCK);
			}
	
			//#CM573240
			// Do instance generation of the schedule class. 
			WmsScheduler schedule = new StoragePlanModifySCH();
			//#CM573241
			// Acquire the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM573242
			// If input Parameter is checked and there is no problem in the schedule class
			//#CM573243
			// ScreenSet Parameter is done to viewState and it changes to Stock Plan correction  and Deletion (details). 
			if (schedule.nextCheck(conn, sparam))
			{
				//#CM573244
				// Title
				viewState.setString(TITLE, lbl_SettingName.getResourceKey());
				//#CM573245
				// Worker code
				viewState.setString(WORKERCODE, txt_WorkerCode.getText());
				//#CM573246
				// Password
				viewState.setString(PASSWORD, txt_Password.getText());
				//#CM573247
				// Consignor Code
				viewState.setString(
					ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
					txt_ConsignorCode.getText());
				//#CM573248
				// Storage plan date
				viewState.setString(
					ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY,
					txt_StoragePlanDate.getText());
				//#CM573249
				// Item Code
				viewState.setString(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
				//#CM573250
				// CaseStorage Location
				viewState.setString(
					ListStoragePlanModifyBusiness.CASESTRGLOCATION_KEY,
					txt_CaseStorageLocation.getText());
				//#CM573251
				// PieceStorage Location
				viewState.setString(
					ListStoragePlanModifyBusiness.PIECESTRGLOCATION_KEY,
					txt_PieseStorageLocation.getText());
				//#CM573252
				// Plan making Flag
				if (rdo_Rgst_Kind_Strg.getChecked())
				{
					//#CM573253
					// Loading , Screen
					viewState.setString(
						REGISTKIND,
						StorageSupportParameter.REGIST_KIND_NOT_INSTOCK);

				}
				else if (rdo_Rgst_Kind_Inst.getChecked())
				{
					//#CM573254
					// For Receiving
					viewState.setString(
						REGISTKIND,
						SystemDefine.REGIST_KIND_INSTOCK);
				}
					
				//#CM573255
				// ScreenTitle
				viewState.setString(TITLE, lbl_SettingName.getResourceKey());

				//#CM573256
				// Essential information Setting Screen->Deletion and correction Screen of detailed information 
				forward(DO_MODIFY2);
			}
			else
			{
				//#CM573257
				// Error Display Message when object data does not exist. 
				message.setMsgResourceKey(schedule.getMessage());
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
				//#CM573258
				// Close the connection.
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

	//#CM573259
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573260
	/**
	 * It is called when the clear button is pressed.<BR>
	 * <BR>
	 * Outline : Clear the Input area.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Initialize the input area. <BR>
	 * 		<BR>
	 * 		<DIR>
	 * 		Item Name[Initial value]<BR>
	 * 			<DIR>
	 * 				Worker Code[As it is]<BR>
	 * 				Password[As it is]<BR>
	 * 				Consignor Code[Display only in case of one in Consignor retrieval result is available]<BR>
	 * 				Storage plan date[None]<BR>
	 * 				Item Code[None]<BR>
	 * 				CaseStorage Location[None]<BR>
	 * 				PieceStorage Location[None]<BR>
	 * 				Plan making Flag[Loading , ScreenAdd]<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM573261
		// Call Method for Screen initial display. (Clear Neither Worker Code nor Password. )
		setInitView(false);
	}
	//#CM573262
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Bsc_DtlMdfyDlt_Click(ActionEvent e) throws Exception
	{
	}

	//#CM573263
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Regist_Kind_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573264
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Rgst_Kind_Strg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573265
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Rgst_Kind_Strg_Click(ActionEvent e) throws Exception
	{
	}

	//#CM573266
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Rgst_Kind_Inst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573267
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Rgst_Kind_Inst_Click(ActionEvent e) throws Exception
	{
	}


}
//#CM573268
//end of class
