// $Id: StoragePlanDeleteBusiness.java,v 1.2 2006/12/07 08:57:17 suresh Exp $

//#CM571970
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.storageplandelete;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.Formatter;
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
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplandate.ListStoragePlanDateBusiness;
import jp.co.daifuku.wms.storage.schedule.StoragePlanDeleteSCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM571971
/**
 * Designer : T.Uehata <BR>
 * Maker    : T.Uehata <BR>
 * <BR>
 * Storage Plan data Deletion class. <BR>
 * Set the content input from the screen in the parameter, pass to the schedule, and do the display processing. <BR>
 * Receive True when the result is received from the schedule, and processing Completes normally, <BR>
 * and false when the schedule does not do Completed because of the condition error etc.<BR>
 * The result of the schedule outputs the message acquired from the schedule to the screen. <BR>
 * Set the content displayed in Preset area in Parameter, pass to the schedule, and process Deletion. <BR>
 * Receive True when the result is received from the schedule, and processing Completes normally, <BR>
 * and false when the schedule does not do Completed because of the condition error etc.<BR>
 * The result of the schedule outputs the message acquired from the schedule to the screen. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Display button is pressed (<CODE>btn_View_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 		Check the input item and if it is correct, set in the parameter and pass it. <BR>
 * 		Set the result of the schedule in the Message area and display the result in Preset area. <BR>
 * 		<BR>
 *   	[Parameter] *Mandatory input <BR>
 * 		<BR>
 * 		<DIR>
 * 			Worker Code* <BR>
 * 			Password*  <BR>
 * 			Consignor Code*  <BR>
 * 			Start Storage plan date <BR>
 * 			End Storage plan date <BR>
 * 			Display consolidating* <BR>
 * 			Plan making Flag* <BR>
 * 		</DIR>
 * 		<BR>
 * 		[Return Data] <BR>
 * 		<BR>
 * 		<DIR>
 * 			Consignor Code <BR>
 * 			Consignor Name <BR>
 * 			Last updated date and time <BR>
 * 			Storage plan date <BR>
 * 			Plan making Flag <BR>
 * 			Item Code <BR>
 * 			Item Name <BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * 2.Processing when Delete Button is pressed (<CODE>btn_Delete_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 		Pass all data of the content displayed in Preset area as Parameter.  <BR>
 * 		As a result, process Deletion. Acquire Message and display it.  <BR>
 * 		When information on Preset area is lost by the Deletion processing <BR>
 * 		Make each Button of Select all, the Unselect all, Deletion, and Clear list unuseable.  <BR>
 * 		<BR>
 * 			[Parameter] *Mandatory input <BR>
 * 		<BR>
 * 		<DIR>
 *     		Worker Code*<BR>
 *     		Password*<BR>
 *     		List print Flag*<BR>
 *     		Display consolidating*<BR>
 *     		ViewState.Consignor Code<BR>
 *     		ViewState.Start Storage plan date<BR>
 *     		ViewState.End Storage plan date<BR>
 *     		ViewState.Plan making Flag<BR>
 *     		Preset.Storage plan date<BR>
 *     		Preset.Item Code<BR>
 *     		Preset.Item Name<BR>
 *     		Terminal No.<BR>
 *     		Preset Line No..<BR>
 * 		</DIR>
 * 		<BR>
 * 		[Return Data] <BR>
 * 		<BR>
 * 		<DIR>
 * 			Consignor Code <BR>
 * 			Consignor Name <BR>
 * 			Last updated date and time <BR>
 * 			Storage plan date <BR>
 * 			Plan making Flag <BR>
 * 			Item Code <BR>
 * 			Item Name <BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/15</TD><TD>T.Uehata</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/05/18</TD><TD>T.Nakajima</TD><TD>Addition of Corresponding Add Flag [2 : For Receiving]</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:17 $
 * @author  $Author: suresh $
 */
public class StoragePlanDeleteBusiness extends StoragePlanDelete implements WMSConstants
{
	//#CM571972
	// Address transition ahead
	//#CM571973
	/**
	 * Consignor retrieval pop up address
	 */
	private static final String DO_SRCH_CONSIGNOR = "/storage/listbox/liststorageconsignor/ListStorageConsignor.do";
	
	//#CM571974
	/**
	 * Storage plan date retrieval pop up address
	 */
	protected static final String DO_SRCH_STORAGEPLANDATE = "/storage/listbox/liststorageplandate/ListStoragePlanDate.do";
	
	//#CM571975
	/**
	 * Screen address when Retrieval pop up is being called
	 */
	protected static final String DO_SRCH_PROCESS = "/progress.do";
	
	//#CM571976
	// List cell row specification variable
	//#CM571977
	/**
	 * Concealed Item's List cell number
	 */
	private static final int LST_HDN = 0;
	
	//#CM571978
	/**
	 * Selection button 's List cell number
	 */
	private static final int LST_SLTBTN = 1;
	
	//#CM571979
	/**
	 * Storage plan date's List cell number
	 */
	private static final int LST_STRGPLANDATE = 2;

	//#CM571980
	/**
	 * Plan making Flag's List cell number
	 */
	private static final int LST_REGIST_KIND = 3;

	//#CM571981
	/**
	 * Item Code's List cell number
	 */
	private static final int LST_ITEMCD = 4;
	
	//#CM571982
	/**
	 * Item Name's List cell number
	 */
	private static final int LST_ITEMNM = 5;
	
	//#CM571983
	// The List cell Concealed Item order
	//#CM571984
	/**
	 * For Concealed Item : Last updated date and time(Number of elements)
	 */
	private static final int HDNIDX_UPDAY_SIZE = 0;
	
	//#CM571985
	/**
	 * For Concealed Item : Last updated date and time
	 */
	private static final int HDNIDX_UPDAY = 1;

	//#CM571986
	// Key of viewState
	//#CM571987
	/**
	 * For ViewState information : The order of display
	 */
	private static final String DISP_GROUP = "DISP_GROUP";

	//#CM571988
	/**
	 * For ViewState information : Plan making Flag
	 */
	private static final String REGIST = "REGIST";

	
	//#CM571989
	// Class fields --------------------------------------------------

	//#CM571990
	// Class variables -----------------------------------------------

	//#CM571991
	// Class method --------------------------------------------------

	//#CM571992
	// Constructors --------------------------------------------------

	//#CM571993
	// Public methods ------------------------------------------------

	//#CM571994
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
			//#CM571995
			//Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM571996
			//Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM571997
			//Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
		//#CM571998
		// Display the dialog. MSG-W0007=Do you delete?
		btn_Delete.setBeforeConfirm("MSG-W0007");
		//#CM571999
		// Display the dialog. MSG-W0012=Do you clear the list?
		btn_ListClear.setBeforeConfirm("MSG-W0012");
	}

	//#CM572000
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Outline:Display initial data in the screen. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the cursor in Worker Code. <BR>
	 * 		2.Initialize the input area. <BR>
	 * 		3.Invalidate Button of Preset area.  <BR>
	 * 		<BR>
	 * 		Item[Initial value] <BR>
	 * 		<DIR>
	 * 			Worker Code[None] <BR>
	 * 			Password[None] <BR>
	 * 			Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Start Storage plan date[None] <BR>
	 * 			End Storage plan date[None] <BR>
	 * 			Display consolidating[Storage plan date and Item Code consolidating] <BR>
	 * 			Plan making Flag[All] <BR>
	 * 			Print the Storage Plan Deletion list. [true]<BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		
		//#CM572001
		// Initializes Input area. 
		//#CM572002
		// Set the cursor in Worker Code. 
		inputDataClear(true);
		
		//#CM572003
		// Initializes Preset area.
		setBtnEnabled(false);
	}

	//#CM572004
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
		//#CM572005
		// Parameter selected from list box is acquired. 
		//#CM572006
		// Consignor Code
		String consignorcode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM572007
		// Start Storage plan date
		String startstorageplandate = param.getParameter(ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY);
		//#CM572008
		// End Storage plan date
		String endstorageplandate = param.getParameter(ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY);

		//#CM572009
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM572010
		// Start Storage plan date
		if (!StringUtil.isBlank(startstorageplandate))
		{
			txt_StrtStrgPlanDate.setText(startstorageplandate);
		}
		//#CM572011
		// End Storage plan date
		if (!StringUtil.isBlank(endstorageplandate))
		{
			txt_EdStrgPlanDate.setText(endstorageplandate);
		}
		
		//#CM572012
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
	}
	//#CM572013
	// Package methods -----------------------------------------------

	//#CM572014
	// Protected methods ---------------------------------------------

	//#CM572015
	// Private methods -----------------------------------------------

	//#CM572016
	/**
	 * Method to acquire Consignor Code from the schedule.  <BR>
	 * <BR>
	 * Outline : Return Consignor Code acquired from the schedule. <BR>
	 * <DIR>
	 *   1.Return a pertinent shipper when there is only one Consignor Code. Return null when it is not so.  <BR>
	 * <DIR>
	 * <BR>
	 * @return Consignor Code
	 * @throws Exception It reports on all exceptions. 
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM572017
			// Acquire the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM572018
			// Declaration of Parameter
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM572019
			// Acquire Consignor Code from the schedule. 
			WmsScheduler schedule = new StoragePlanDeleteSCH();
			//#CM572020
			// Start Schedule.
			param = (StorageSupportParameter) schedule.initFind(conn, param);

			//#CM572021
			// Return the result when the result exists. 
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
				//#CM572022
				// Close the connection. 
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
	
	//#CM572023
	/**
	 * Method which sets all the Selection check boxes of List cell. <BR>
	 * <BR>
	 * Outline : Set the Selection/Selection release of the Selected check box based on the condition of receiving it by the argument. <BR>
	 * Select all when argument is true. Unselect all when argument is false. <BR>
	 * <BR>
	 * @param flg boolean Check flag. (Select all : true Unselect all : false) 
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setListCellCheckBox(boolean flg) throws Exception
	{
		for (int i = 1; i < lst_StoragePlanDelete.getMaxRows(); i++)
		{
			//#CM572024
			// Select the Work line. 
			lst_StoragePlanDelete.setCurrentRow(i);
			lst_StoragePlanDelete.setChecked(LST_SLTBTN, flg);
		}
	}

	//#CM572025
	/**
	 * Set the value in Preset area. <BR>
	 * <BR>
	 * Outline : Set the value of Parameter in Preset area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Make the buttons in Preset area effective. <BR>
	 * 		1.Set data in Preset area. <BR>
	 * 		2.Set data in Concealed Item. <BR>
	 * 		3.Set data in balloon information. <BR>
	 * 		<DIR>
	 * 			[List of row number of list cell]<BR>
	 * 			<DIR>
	 * 				0.Concealed Item <BR>
	 * 				2.Storage plan date <BR>
	 * 				3.Plan making Flag <BR>
	 * 				4.Item Code <BR>
	 * 				5.Item Name <BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param param StorageSupportParameter[] Parameter array which stores display data. 
	 * @throws Exception It reports on all exceptions.  
	 */
	private void addList(StorageSupportParameter[] param) throws Exception
	{
		//#CM572026
		// Setting of Button of Preset area Item
		setBtnEnabled(true);
		
		//#CM572027
		// Display the retrieval result in Preset area. 
		//#CM572028
		// Consignor Code
		txt_JavaSetConsignorCode.setText(this.getViewState().getString(ListStorageConsignorBusiness.CONSIGNORCODE_KEY));
		//#CM572029
		// Consignor Name
		txt_JavaSetConsignorName.setText(param[0].getConsignorName());
		
		Locale locale = this.getHttpRequest().getLocale();
		
		for (int i = 0; i < param.length; i++)
		{
			//#CM572030
			// The line is added to the list cell.
			lst_StoragePlanDelete.addRow();
			//#CM572031
			// Specify the line which sets the value. 
			lst_StoragePlanDelete.setCurrentRow(i + 1);

			//#CM572032
			// Concealment Parameter is stored in List cell. 
			lst_StoragePlanDelete.setValue(LST_HDN, createHiddenList(param[i]));
			
			//#CM572033
			// Storage plan date
			lst_StoragePlanDelete.setValue(LST_STRGPLANDATE, WmsFormatter.toDispDate(param[i].getStoragePlanDate(), locale));

			//#CM572034
			// Plan making Flag
			lst_StoragePlanDelete.setValue(LST_REGIST_KIND, getRegistKindName(param[i].getRegistKbn()));

			//#CM572035
			// Item Code
			lst_StoragePlanDelete.setValue(LST_ITEMCD, param[i].getItemCode());
			//#CM572036
			// Item Name
			lst_StoragePlanDelete.setValue(LST_ITEMNM, param[i].getItemName());
		}
	}
	
	
	//#CM572037
	/**
	 * Initialize the input area. <BR>
	 * <BR>
	 * Outline : Set Initial value to each Item of Input area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the cursor in Worker Code. <BR>
	 * 		2.Set Initial value in each Item. <BR>
	 * 		<BR>
	 *    	Item Name[Initial value]<BR>
	 *		<BR>
	 *    	<DIR>
	 * 			Worker Code[*1] <BR>
	 * 			Password[*1] <BR>
	 * 			Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Start Storage plan date[None] <BR>
	 * 			End Storage plan date[None] <BR>
	 * 			Display consolidating[Storage plan date and Item Code consolidating] <BR>
	 * 			Plan making Flag[All] <BR>
	 * 			Print the Storage Plan Deletion list. [true]<BR>
	 * 			<BR>
	 * 			*1<BR>
	 * 			<DIR>
	 * 				Clear when workerclearFlg is true. <BR>
	 * 				Do not Clear when workerclearFlg is false. <BR>
	 * 			</DIR>
	 *   	</DIR>
	 * </DIR>
	 * <BR>
	 * @param workerclearFlg boolean Clear flag.
	 * @throws Exception It reports on all exceptions.  
	 */
	private void inputDataClear(boolean workerclearFlg) throws Exception
	{
		//#CM572038
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
		
		if(workerclearFlg)
		{
			//#CM572039
			// Worker Code
			txt_WorkerCode.setText("");
			//#CM572040
			// Password
			txt_Password.setText("");
		}
		
		//#CM572041
		// Consignor Code(Display the Consignor Code only when data is one. )
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM572042
		// Start Storage plan date
		txt_StrtStrgPlanDate.setText("");
		//#CM572043
		// End Storage plan date
		txt_EdStrgPlanDate.setText("");
		//#CM572044
		// Display consolidating
		rdo_StrgPlanDateItemCd.setChecked(true);
		rdo_Grp_StrgPlanDate.setChecked(false);

		//#CM572045
		// Plan making Flag
   		rdo_Rgst_Kind_All.setChecked(true);
   		rdo_Rgst_Kind_Strg.setChecked(false);
   		rdo_Rgst_Kind_Inst.setChecked(false);

		//#CM572046
		// List print check box
		chk_StragePlanData.setChecked(true);
		
	}
	
	//#CM572047
	/**
	 * Method which sets the editablity of the button of Preset area.<BR>
	 * <BR>
	 * Outline : Receive false or true and set editability of the button. <BR>
	 * <BR>
	 * @param  flg boolean Editability of the button. 
	 */
	private void setBtnEnabled(boolean flg)
	{
		//#CM572048
		// Button of Preset area
		btn_AllCheck.setEnabled(flg);
		btn_AllCheckClear.setEnabled(flg);
		btn_Delete.setEnabled(flg);
		btn_ListClear.setEnabled(flg);
		lst_StoragePlanDelete.clearRow();
	}
	
	//#CM572049
	/**
	 * Method to connect HIDDEN Item with one character string. <BR>
	 * <BR>
	 * Outline : Acquire Concealed Item, and convert it into the character string. <BR>
	 * <BR>
	 * <DIR>
	 * 		The Item order list of HIDDEN <BR>
	 * 		<DIR>
	 * 			0.Last updated date and time(Number of elements) <BR>
	 * 			1.Last updated date and time <BR>
	 * 			2.Plan making Flag <BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param param StorageSupportParameter Parameter including HIDDEN Item.
	 * @return Connected HIDDEN Item. 
	 */
	private String createHiddenList(StorageSupportParameter param)
	{
		//#CM572050
		// Making of Concealed Item character string
		ArrayList hdnpara = new ArrayList();
		
		//#CM572051
		// Concealed Item
		//#CM572052
		// Last updated date and time (Number of elements) 
		hdnpara.add(HDNIDX_UPDAY_SIZE, Integer.toString(param.getLastUpdateDateList().size() + 1));
		
		//#CM572053
		// Last updated date and time 	
		Vector vecLastDate = param.getLastUpdateDateList();
		for(int i = 0; i < vecLastDate.size(); i++)
		{
			hdnpara.add(WmsFormatter.getTimeStampString((Date)vecLastDate.get(i)));
		}

		//#CM572054
		// Acquire AddFlag as Concealed Item. 
		hdnpara.add(param.getRegistKbn());
		
		return CollectionUtils.getConnectedString(hdnpara);
	}
	
	//#CM572055
	/**
	 * Acquire corresponding Description from Plan making Flag. <BR>
	 * <BR>
	 * Outline : Return the Description if there is corresponding Description. <BR>
	 * Return the empty string if there is no corresponding Description. <BR>
	 * <BR>
	 * @param registKind Plan making Flag.
	 * @return Plan making Flag Description.
	 * @throws Exception It reports on all exceptions.  
	 */
	private String getRegistKindName(String registKind) throws Exception
	{
		//#CM572056
		// Plan making Flag
		if (registKind.equals(SystemDefine.REGIST_KIND_INSTOCK))
		{
			//#CM572057
			// For Receiving
			return DisplayText.getText("LBL-W0455");
		}
		else if (registKind.equals(SystemDefine.REGIST_KIND_HOST) ||
					registKind.equals(SystemDefine.REGIST_KIND_WMS))
		{
			//#CM572058
			// For other than Receiving
			return DisplayText.getText("LBL-W0458");
		}

		return "";
	}
	
	//#CM572059
	// Event handler methods -----------------------------------------
	//#CM572060
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572061
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572062
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572063
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
		//#CM572064
		// Change to the menu panel.
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM572065
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572066
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572067
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572068
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572069
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM572070
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572071
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572072
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572073
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572074
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM572075
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572076
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572077
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572078
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572079
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM572080
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572081
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
		//#CM572082
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM572083
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM572084
		// Work Status(Stand by)
		String[] search = new String[1];
		search[0] = new String(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		//#CM572085
		// Work Set Status
		param.setParameter(ListStorageConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		//#CM572086
		// Retrieval flag : Plan
		param.setParameter(ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY, StorageSupportParameter.SEARCH_STORAGE_PLAN);
		//#CM572087
		// Processing Screen -> Result screen
		redirect(DO_SRCH_CONSIGNOR, param, DO_SRCH_PROCESS);
	}

	//#CM572088
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrtStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572089
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572090
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtStrgPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572091
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtStrgPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572092
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchFromStrgDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572093
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
	public void btn_PsearchFromStrgDate_Click(ActionEvent e) throws Exception
	{
		//#CM572094
		// Set the search condition on the Storage plan date retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		
		//#CM572095
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM572096
		// Start Storage plan date
		param.setParameter(ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY, WmsFormatter.toParamDate(this.txt_StrtStrgPlanDate.getDate()));
		//#CM572097
		// Range specification (Start)
		param.setParameter(ListStoragePlanDateBusiness.RANGESTORAGEPLANDATE_KEY, StorageSupportParameter.RANGE_START);
		//#CM572098
		// Work Status(Stand by)
		String[] search = new String[1];
		search[0] = new String(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		//#CM572099
		// Work Set Status
		param.setParameter(ListStoragePlanDateBusiness.WORKSTATUSSTORAGEPLANDATE_KEY, search);
		//#CM572100
		// Retrieval flag
		param.setParameter(ListStoragePlanDateBusiness.SEARCHSTORAGEPLANDATE_KEY, 
			StorageSupportParameter.SEARCH_STORAGE_PLAN);
		//#CM572101
		// Processing Screen -> Result screen
		redirect(DO_SRCH_STORAGEPLANDATE, param, DO_SRCH_PROCESS);
	}

	//#CM572102
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572103
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_EndStoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572104
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572105
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdStrgPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572106
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdStrgPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572107
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchToStrgDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572108
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
	public void btn_PsearchToStrgDate_Click(ActionEvent e) throws Exception
	{
		//#CM572109
		// Set the search condition on the Storage plan date retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM572110
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM572111
		// End Storage plan date
		param.setParameter(ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY, WmsFormatter.toParamDate(this.txt_EdStrgPlanDate.getDate()));
		//#CM572112
		// Range specification (Finish)
		param.setParameter(ListStoragePlanDateBusiness.RANGESTORAGEPLANDATE_KEY, StorageSupportParameter.RANGE_END);
		//#CM572113
		// Work Status(Stand by)
		String[] search = new String[1];
		search[0] = new String(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		//#CM572114
		// Work Set Status
		param.setParameter(ListStoragePlanDateBusiness.WORKSTATUSSTORAGEPLANDATE_KEY, search);
		//#CM572115
		// Retrieval flag
		param.setParameter(ListStoragePlanDateBusiness.SEARCHSTORAGEPLANDATE_KEY, 
			StorageSupportParameter.SEARCH_STORAGE_PLAN);
		//#CM572116
		// Processing Screen -> Result screen
		redirect(DO_SRCH_STORAGEPLANDATE, param, DO_SRCH_PROCESS);
	}

	//#CM572117
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DspGroup_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572118
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_StrgPlanDateItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572119
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_StrgPlanDateItemCd_Click(ActionEvent e) throws Exception
	{
	}

	//#CM572120
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Grp_StrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572121
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Grp_StrgPlanDate_Click(ActionEvent e) throws Exception
	{
	}

	//#CM572122
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrgPlanDltList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572123
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_StragePlanData_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572124
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_StragePlanData_Change(ActionEvent e) throws Exception
	{
	}

	//#CM572125
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572126
	/** 
	 * It is called when the display button is pressed.<BR>
	 * <BR>
	 * Outline:Retrieve Storage Plan information in condition and display input item of input area in Preset area.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Check input Item of Input area. (Mandatory input, Number of characters, Character attribute)<BR>
	 * 		2.Start Schedule.<BR>
	 * 		3.Display in the Preset area.<BR>
	 * 		4.Maintain ViewState information. <BR>
	 * 		<DIR>
	 * 			[ViewState information] <BR>
	 * 			<DIR>
	 * 				Consignor Code <BR>
	 * 				Start Storage plan date <BR>
	 * 				End Storage plan date <BR>
	 * 				The order of display <BR>
	 * 				Plan making Flag <BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		//#CM572127
		// Initialize Preset area.
		btn_ListClear_Click(e);
		
		//#CM572128
		// Do the input check
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		
		//#CM572129
		// Do Pattern matching. 
		txt_StrtStrgPlanDate.validate(false);
		txt_EdStrgPlanDate.validate(false);
		
		//#CM572130
		// The Start Picking Plan date must be smaller than the End Picking Plan date. 
		if (!StringUtil.isBlank(this.txt_StrtStrgPlanDate.getDate()) && !StringUtil.isBlank(this.txt_EdStrgPlanDate.getDate()))
		{
			if (this.txt_StrtStrgPlanDate.getDate().after(this.txt_EdStrgPlanDate.getDate()))
			{
				//#CM572131
				// 6023199=Starting planned storage date must precede the end planned storage date.
				message.setMsgResourceKey("6023199");
				return;
			}
		}
		
		Connection conn = null;

		try
		{
			//#CM572132
			// Acquire the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			//#CM572133
			// Set the content of the screen in schedule Parameter. 
			StorageSupportParameter param = new StorageSupportParameter();
			
			//#CM572134
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM572135
			// Password
			param.setPassword(txt_Password.getText());
			//#CM572136
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM572137
			// Start Storage plan date
			param.setFromStoragePlanDate(WmsFormatter.toParamDate(txt_StrtStrgPlanDate.getDate()));
			//#CM572138
			// End Storage plan date
			param.setToStoragePlanDate(WmsFormatter.toParamDate(txt_EdStrgPlanDate.getDate()));
			//#CM572139
			// Display consolidating
			if(rdo_StrgPlanDateItemCd.getChecked())
			{
				param.setAggregateDisplay(StorageSupportParameter.AGGREGATEDISPLAY_ITEM_AND_LOCATION);
			}
			else if(rdo_Grp_StrgPlanDate.getChecked())
			{
				param.setAggregateDisplay(StorageSupportParameter.AGGREGATEDISPLAY_PLAN_DATE);	
			}
			else
			{
				param.setAggregateDisplay("");
			}
			
			//#CM572140
			// Plan making Flag
			if(rdo_Rgst_Kind_All.getChecked())
			{
				param.setRegistKbn(StorageSupportParameter.REGIST_KIND_ALL);
			}
			else if(rdo_Rgst_Kind_Strg.getChecked())
			{
				param.setRegistKbn(StorageSupportParameter.REGIST_KIND_NOT_INSTOCK);	
			}
			else if(rdo_Rgst_Kind_Inst.getChecked())
			{
				param.setRegistKbn(SystemDefine.REGIST_KIND_INSTOCK);
			}
			
			//#CM572141
			// Schedule class Generate instance. 
			WmsScheduler schedule = new StoragePlanDeleteSCH();
			
            //#CM572142
            // Start scheduling.
			StorageSupportParameter[] viewParam = (StorageSupportParameter[]) schedule.query(conn, param);

			//#CM572143
			// End processing when some errors happen or there is no display data. 
			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			//#CM572144
			// Maintain Search condition with viewState. (After Deletion is processed, it becomes the condition For another Retrieval. )
			//#CM572145
			// Consignor Code
			this.viewState.setString(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, param.getConsignorCode());
			//#CM572146
			// Start Storage plan date
			this.viewState.setString(ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY, param.getFromStoragePlanDate());
			//#CM572147
			// End Storage plan date
			this.viewState.setString(ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY, param.getToStoragePlanDate());
			//#CM572148
			// Display consolidating
			this.viewState.setString(DISP_GROUP, param.getAggregateDisplay());
			//#CM572149
			// Plan making Flag
			this.viewState.setString(REGIST, param.getRegistKbn());
			//#CM572150
			// Set Preset area. 
			addList(viewParam);

			//#CM572151
			// Set Message
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
				//#CM572152
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
	}

	//#CM572153
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572154
	/** 
	 * It is called when the clear button is pressed.<BR>
	 * <BR>
	 * Outline : Clear the Input area.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Return Initial value Item of Input area. <BR>
	 * 		<DIR>
	 * 			Item[Initial value] <BR>
	 * 			<DIR>
	 * 				Worker Code[As it is] <BR>
	 * 				Password[As it is] <BR>
	 * 				Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 				Start Storage plan date[None] <BR>
	 * 				End Storage plan date[None] <BR>
	 * 				Display consolidating[Storage plan date and Item Code consolidating] <BR>
	 * 				Plan making Flag[All] <BR>
	 * 				Print the Storage Plan Deletion list. [true]<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		inputDataClear(false);
	}

	//#CM572155
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheck_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572156
	/**
	 * It is called when the Select all button is pressed. <BR>
	 * <BR>
	 * Outline:Check all Selection check boxes of Preset area.  <BR>
	 * <BR>
	 * <DIR>
	 *     1.Check all Selection check boxes.  <BR>
	 *     2.Set the cursor in Worker Code.  <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_AllCheck_Click(ActionEvent e) throws Exception
	{
		//#CM572157
		// Check Selection check boxes. 
		setListCellCheckBox(true);
		//#CM572158
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
	}

	//#CM572159
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheckClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572160
	/**
	 * UnIt is called when the Select all button is pressed. <BR>
	 * <BR>
	 * Outline:Clear all checked Final check boxes of Preset area.  <BR>
	 * <BR>
	 * <DIR>
	 *     1.Remove the check on All Final check boxes.<BR>
	 *     2.Set the cursor in Worker Code.  <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_AllCheckClear_Click(ActionEvent e) throws Exception
	{
		//#CM572161
		// Uncheck the Selection check box. 
		setListCellCheckBox(false);
		//#CM572162
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
	}

	//#CM572163
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Delete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572164
	/**
	 * When Deletion Button is pressed, it is called.  <BR>
	 * <BR>
	 * Outline:Delete shipment Plan information by information on Preset area.  <BR>
	 * </BR>
	 * <DIR>
	 *     	1.Set the cursor in Worker Code.  <BR>
	 *     	2.Set the Deletion object data in Parameter. <BR>
	 *     	3.Start Schedule. <BR>
	 *     	4.Take again and display information from the schedule after it updates it to Preset area.  <BR>
	 *     	5.Invalidate Select all Button, Unselect all Button, Delete Button and Clear list Button when Preset information does not exist.  <BR>
	 *     	<BR>
	 *     	<DIR>
	 *     		[Parameter]*MandatoryItem<BR>
	 *     		<BR>
	 *     		<DIR>
	 *     			Worker Code*<BR>
	 *     			Password*<BR>
	 *     			List print Flag*<BR>
	 *     			Display consolidating*<BR>
	 *     			ViewState.Consignor Code<BR>
	 *     			ViewState.Start Storage plan date<BR>
	 *     			ViewState.End Storage plan date<BR>
	 *     			ViewState.Plan making Flag<BR>
	 *     			Preset.Storage plan date<BR>
	 *     			Preset.Item Code<BR>
	 *     			Preset.Item Name<BR>
	 *     			Terminal No.<BR>
	 *     			Preset Line No..<BR>
	 *     		</DIR>
	 *     </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Delete_Click(ActionEvent e) throws Exception
	{
		//#CM572165
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
		
		//#CM572166
		// Mandatory Input check
		txt_WorkerCode.validate();
		txt_Password.validate();
		

		Connection conn = null;

		try
		{
			//#CM572167
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			Locale locale = this.getHttpRequest().getLocale();

			//#CM572168
			// Checked whether there is deleted data. 
			int wCheck = 0;

			//#CM572169
			// The element of Preset to the check
			//#CM572170
			// Only the updated line sets the value in the array. 
			Vector vecParam = new Vector(lst_StoragePlanDelete.getMaxRows());
			for (int i = 1; i < lst_StoragePlanDelete.getMaxRows(); i++)
			{
				//#CM572171
				// Acquire the operated line. 
				lst_StoragePlanDelete.setCurrentRow(i);
				
				if (lst_StoragePlanDelete.getChecked(LST_SLTBTN))
				{
					wCheck++;
					
					StorageSupportParameter param = new StorageSupportParameter();
					
					//#CM572172
					// Acquire Concealed Item. 
					String hidden = lst_StoragePlanDelete.getValue(LST_HDN);

					String orgAryLastDate = CollectionUtils.getString(HDNIDX_UPDAY_SIZE, hidden);
					
					//#CM572173
					// Plan making Flag
					param.setRegistKbn((String)CollectionUtils.getString(Formatter.getInt(orgAryLastDate), hidden));
					
					//#CM572174
					// Last updated date and time
					Vector orgLastDateList = new Vector();
					int endPoint = HDNIDX_UPDAY + Formatter.getInt(orgAryLastDate);
					for(int j = HDNIDX_UPDAY; j < endPoint - 1; j++)
					{
						String strLastUpDate = (String) CollectionUtils.getList(hidden).get(j);
						orgLastDateList.add(WmsFormatter.getTimeStampDate(strLastUpDate));
					}
					param.setLastUpdateDateList(orgLastDateList);
					
					//#CM572175
					// Worker Code
					param.setWorkerCode(txt_WorkerCode.getText());
					//#CM572176
					// Password
					param.setPassword(txt_Password.getText());
					//#CM572177
					// Set Whether the Storage Plan Delete list print is done.
					param.setDeleteStorageListFlag(chk_StragePlanData.getChecked());
					if (chk_StragePlanData.getChecked())
					{
						param.setStorageListFlg(true);
					}

					//#CM572178
					// Set search condition for re-display(Consignor, Start Storage plan date, End Storage plan date, Plan making Flag)
					param.setConsignorCode(this.getViewState().getString(ListStorageConsignorBusiness.CONSIGNORCODE_KEY));
					param.setFromStoragePlanDate(this.getViewState().getString(ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY));
					param.setToStoragePlanDate(this.getViewState().getString(ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY));
					param.setRegistKndCondition(this.getViewState().getString(REGIST));
			
					//#CM572179
					// Display consolidating
					if(rdo_StrgPlanDateItemCd.getChecked())
					{
						param.setAggregateDisplay(StorageSupportParameter.AGGREGATEDISPLAY_ITEM_AND_LOCATION);
					}
					else if(rdo_Grp_StrgPlanDate.getChecked())
					{
						param.setAggregateDisplay(StorageSupportParameter.AGGREGATEDISPLAY_PLAN_DATE);	
					}
					//#CM572180
					// Storage plan date
					param.setStoragePlanDate(WmsFormatter.toParamDate(lst_StoragePlanDelete.getValue(LST_STRGPLANDATE), locale));
					//#CM572181
					// Item Code
					param.setItemCode(lst_StoragePlanDelete.getValue(LST_ITEMCD));
					//#CM572182
					// Item Name
					param.setItemName(lst_StoragePlanDelete.getValue(LST_ITEMNM));
					//#CM572183
					// Preset Line No..
					param.setRowNo(i);
					
					//#CM572184
					// Terminal No.
					UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
					param.setTerminalNumber(userHandler.getTerminalNo());

					vecParam.addElement(param);
				}
			}

			//#CM572185
			// When the Selection box is not checked, Finish processes it. 
			if (wCheck == 0)
			{
				//#CM572186
				// 6023111 = Please check the data to delete.
				message.setMsgResourceKey("6023111");
				return;
			}

			//#CM572187
			// Declaration of Parameter
			StorageSupportParameter[] paramArray = new StorageSupportParameter[vecParam.size()];
            //#CM572188
            // Copy the value onto Parameter. 
			vecParam.copyInto(paramArray);

			//#CM572189
			// Generate instance of schedule class. 
			WmsScheduler schedule = new StoragePlanDeleteSCH();

			//#CM572190
			// Begin scheduling. 
			StorageSupportParameter[] viewParam =
				(StorageSupportParameter[]) schedule.startSCHgetParams(conn, paramArray);

			//#CM572191
			// It fails in the update if Return Data is null. 
			//#CM572192
			// Set Rollback and Message. (Preset with the data displayed before)
			if (viewParam == null)
			{
				//#CM572193
				// Rollback
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			//#CM572194
			// It succeeds in the update if the number of elements of Return Data is 0 or more. 
			//#CM572195
			// Do the setting of the transaction and display again. 
			else if (viewParam.length >= 0)
			{
				//#CM572196
				// Commit
				conn.commit();

				//#CM572197
				// When there is no displayed data
				if (viewParam.length == 0)
				{
					//#CM572198
					// Clear the Preset area
					btn_ListClear_Click(e);
				}
				else
				{
					//#CM572199
					// Display it when the schedule normally Completes and the display data is acquired. 
					lst_StoragePlanDelete.clearRow();
					//#CM572200
					// Display Preset area again. 
					addList(viewParam);
				}

				//#CM572201
				// Display it if there is Message. 
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
				//#CM572202
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
	}

	//#CM572203
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572204
	/** 
	 * It is called when the clear list button is pressed.<BR>
	 * <BR>
	 * Outline:Clears all display information on Preset. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Display the dialog box, and confirm whether to Clear Preset information. <BR>
	 *    <DIR>
	 * 		[Confirmation Dialog Cancel]<BR>
	 *			<DIR>
	 *				Do Nothing.
	 *			</DIR>
	 * 		[Confirmation Dialog OK]<BR>
	 *			<DIR>
	 *				1.Clears all List cells.<BR>
	 *				2.Clears Consignor Code  and Consignor Name (Only for Reading). <BR>
	 *				3.Invalidate Button of Preset area. <BR>
	 *				4.Set the cursor in Worker Code. <BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		//#CM572205
		// Deletes all line information. 
		lst_StoragePlanDelete.clearRow();
		//#CM572206
		// Empty the Consignor part of Preset area. 
		txt_JavaSetConsignorCode.setText("");
		txt_JavaSetConsignorName.setText("");
		//#CM572207
		// Invalidate Buttons of Preset area. 
		setBtnEnabled(false);
		//#CM572208
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
	}

	//#CM572209
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}


	//#CM572210
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572211
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572212
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572213
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM572214
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572215
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572216
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572217
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}
	
	//#CM572218
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanDelete_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572219
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanDelete_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572220
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanDelete_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM572221
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanDelete_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM572222
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanDelete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572223
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanDelete_Change(ActionEvent e) throws Exception
	{
	}

	//#CM572224
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanDelete_Click(ActionEvent e) throws Exception
	{
	}
	//#CM572225
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Delete_Click(ActionEvent e) throws Exception
	{
	}

	//#CM572226
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Regist_Kind_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572227
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Rgst_Kind_All_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572228
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Rgst_Kind_All_Click(ActionEvent e) throws Exception
	{
	}

	//#CM572229
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Rgst_Kind_Strg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572230
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Rgst_Kind_Strg_Click(ActionEvent e) throws Exception
	{
	}

	//#CM572231
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Rgst_Kind_Inst_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572232
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Rgst_Kind_Inst_Click(ActionEvent e) throws Exception
	{
	}


}
//#CM572233
//end of class
