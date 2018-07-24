// $Id: SetStorageBusiness.java,v 1.3 2006/12/07 08:57:21 suresh Exp $

//#CM570867
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.setstorage;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplandate.ListStoragePlanDateBusiness;
import jp.co.daifuku.wms.storage.schedule.SetStorageSCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570868
/**
 * Designer : K.Mukai <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * Storage The setting screen class. <BR>
 * Storage Hand over Parameter to the set schedule. <BR>
 * Moreover, do Commit  of Transaction and Rollback on this screen. <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Display button is pressed(<CODE>btn_View_Click()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 * 		Set the content input from the screen in Parameter.  Retrieve the data for displaying the schedule based on the parameter.<BR>
 * 		Receive Preset area Output data from the schedule and output it to Preset area.<BR>
 * 		<BR>
 * 		[Parameter] *Mandatory input<BR>
 * 		<BR>
 * 		<DIR>
 * 			Worker Code* <BR>
 * 			Password* <BR>
 * 			Consignor Code* <BR>
 * 			Start Storage plan date <BR>
 * 			End Storage plan date <BR>
 * 			Item Code <BR>
 * 			Case/Piece flag* <BR>
 * 			Input initial of the remianing Storage qty. <BR>
 * 		</DIR>
 * 		<BR>
 * 		[Output data] <BR>
 * 		<BR>
 * 		<DIR>
 * 			Consignor Code <BR>
 * 			Consignor Name <BR>
 * 			Storage plan date <BR>
 * 			Item Code <BR>
 * 			Item Name <BR>
 * 			Flag <BR>
 * 			Storage Location <BR>
 * 			Storage total<BR>
 * 			Packed qty per case <BR>
 * 			Packed qty per bundle <BR>
 * 			Storage Case qty <BR>
 * 			Storage Piece qty <BR>
 * 			Expiry Date <BR>
 * 			CaseITF <BR>
 * 			Bundle ITF <BR>
 * 			Work No. <BR>
 * 			Last updated date and time <BR>
 * 			Work plan case qty <BR>
 * 			Work plan piece qty <BR>
 * 			Case/Piece flag <BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * 2.Processing when Start Storage button is pressed (<CODE>btn_StorageStart_Click()</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 		The content input from Preset area is set in Parameter, and the schedule sets Storage based on the Parameter. <BR>
 * 		Receive True when the result is received from the schedule, and processing Completes normally, <BR>
 * 		and false when the schedule does not do Completed because of the condition error etc.<BR>
 * 		Moreover, output Message acquired from the schedule result to the screen. <BR>
 * 		<BR>
 * 		[Parameter] *Mandatory input <BR>
 * 		<BR>
 * 		<DIR>
 * 			Worker Code* <BR>
 * 			Password* <BR>
 * 			Start Storage plan date <BR>
 * 			End Storage plan date <BR>
 * 			Check of remaining Storage qty initial Input. <BR>
 * 			Preset.Consignor Code <BR>
 *			Preset.Item Code <BR>
 *			Preset.Flag <BR>
 *			Preset.Storage Location <BR>
 *          Preset.Plan total qty <BR>
 *			Preset.Packed qty per case <BR>
 *			Preset.Packed qty per bundle <BR>
 *			Preset.Storage Case qty <BR>
 *			Preset.Storage Piece qty <BR>
 *			Preset.Flag of additional delivery <BR>
 *			Preset.Flag of shortage <BR>
 *			Preset.Expiry Date <BR>
 *			Preset.Work No. <BR>
 *          Preset.Last updated date and time <BR>
 * 			Preset.Work plan case qty <BR>
 * 			Preset.Work plan piece qty <BR>
 * 			Terminal No.<BR>
 * 			Preset Line No.<BR>
 * 		</DIR>
 * 		<BR>
 * 		[Output data] <BR>
 * 		<BR>
 * 		<DIR>
 * 			Consignor Code <BR>
 * 			Consignor Name <BR>
 * 			Storage plan date <BR>
 * 			Item Code <BR>
 * 			Item Name <BR>
 * 			Flag <BR>
 * 			Storage Location <BR>
 * 			Storage total<BR>
 * 			Packed qty per case <BR>
 * 			Packed qty per bundle <BR>
 * 			Storage Case qty <BR>
 * 			Storage Piece qty <BR>
 * 			Expiry Date <BR>
 * 			CaseITF <BR>
 * 			Bundle ITF <BR>
 * 			Work No. <BR>
 * 			Last updated date and time <BR>
 * 			Work plan case qty <BR>
 * 			Work plan piece qty <BR>
 * 			Case/Piece flag <BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/12/07 08:57:21 $
 * @author  $Author: suresh $
 */
public class SetStorageBusiness extends SetStorage implements WMSConstants
{
	//#CM570869
	/**
	 * Start Storage date (For another Retrieval) 
	 */
	private final static String VK_STARTSTORAGEDATE = "VK_STARTSTORAGEDATE";
	//#CM570870
	/**
	 * End Storage date (For another Retrieval) 
	 */
	private final static String VK_ENDSTORAGEDATE = "VK_ENDSTORAGEDATE";
	//#CM570871
	/**
	 * Item Code (For another Retrieval) 
	 */
	private final static String VK_ITEMCODE = "VK_ITEMCODE";
	//#CM570872
	/**
	 * CasePieceFlag : All (For another Retrieval) 
	 */
	private final static String VK_CASEPIECE_ALL = "VK_CASEPIECE_ALL";
	//#CM570873
	/**
	 * CasePieceFlag : Case (For another Retrieval) 
	 */
	private final static String VK_CASEPIECE_CASE = "VK_CASEPIECE_CASE";
	//#CM570874
	/**
	 * CasePieceFlag : Piece (For another Retrieval) 
	 */
	private final static String VK_CASEPIECE_PIECE = "VK_CASEPIECE_PIECE";
	//#CM570875
	/**
	 * CasePieceFlag : Unspecified (For another Retrieval) 
	 */
	private final static String VK_CASEPIECE_APPOINTOFF = "VK_STRGFLG";
	
	//#CM570876
	// Class fields --------------------------------------------------

	//#CM570877
	// Class variables -----------------------------------------------

	//#CM570878
	// Class method --------------------------------------------------

	//#CM570879
	// Constructors --------------------------------------------------

	//#CM570880
	// Public methods ------------------------------------------------

	//#CM570881
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Outline:Display initial data in the screen. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Initialize the input area. <BR>
	 * 		2.Invalidate Start Storage button and Clear list Button. <BR>
	 * 		<BR>
	 * 		Item[Initial value] <BR>
	 * 		<DIR>
	 * 			Worker Code[None] <BR>
	 * 			Password[None] <BR>
	 * 			Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Start Storage plan date[None] <BR>
	 * 			End Storage plan date[None] <BR>
	 * 			Item Code[None] <BR>
	 * 			Case/Piece flag[All] <BR>
	 * 			Display initial of the remaining Storage qty.[false]<BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{

		txt_ConsignorCode.setText(getConsignorCode());
		//#CM570882
		// Set Case/Piece flag in All. 
		rdo_CpfAll.setChecked(true);
		//#CM570883
		// The initial remaining Storage qty display setting check box
		chk_CommonUseStrgRest.setChecked(false);			
		
		//#CM570884
		// Button not pressed properly
		//#CM570885
		// Start Storage button
		btn_StorageStart.setEnabled(false);
		//#CM570886
		// Clear list Button
		btn_ListClear.setEnabled(false);

	}

	//#CM570887
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
		if (menuparam != null)
		{
			//#CM570888
			//Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM570889
			//Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM570890
			//Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
		//#CM570891
		// Confirmation Dialog when Start Storage button is pressed MSG-W0019=Do you start?
		btn_StorageStart.setBeforeConfirm("MSG-W0019");

		//#CM570892
		// Confirmation Dialog when Start Clear list button is pressed MSG-W0012=Do you clear the list?
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		//#CM570893
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
	}

	//#CM570894
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
		//#CM570895
		// Parameter selected from list box is acquired. 
		//#CM570896
		// Consignor Code
		String consignorcode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM570897
		// Start Storage plan date
		String startstorageplandate = param.getParameter(ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY);
		//#CM570898
		// End Storage plan date
		String endstorageplandate = param.getParameter(ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY);
		//#CM570899
		// Item Code
		String itemcode = param.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);

		//#CM570900
		// Set the value when it is not empty. 
		//#CM570901
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM570902
		// Start Storage plan date
		if (!StringUtil.isBlank(startstorageplandate))
		{
			txt_StrtStrgPlanDate.setDate(WmsFormatter.toDate(startstorageplandate, this.httpRequest.getLocale()));
		}
		//#CM570903
		// End Storage plan date
		if (!StringUtil.isBlank(endstorageplandate))
		{
			txt_EdRtrivlPlanDate.setDate(WmsFormatter.toDate(endstorageplandate, this.httpRequest.getLocale()));
		}
		//#CM570904
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
	}

	//#CM570905
	// Package methods -----------------------------------------------

	//#CM570906
	// Protected methods ---------------------------------------------
	//#CM570907
	/**
	 * Do the Input Check.<BR>
	 * <BR>
	 * Outline : Set Message and Return false when abnormality is found. <BR>
	 * <BR>
	 * @param rowNo int Checked Line  No.
	 * @return Result of Input Check (true:Normal false:Abnormal) 
	 */
	protected boolean checkContainNgText(int rowNo)
	{
		WmsCheckker checker = new WmsCheckker();

		lst_SSetStorage.setCurrentRow(rowNo);

		if(!checker.checkContainNgText(
				lst_SSetStorage.getValue(4) ,
				rowNo,
				lst_SSetStorage.getListCellColumn(4).getResourceKey() )
				)
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}

		if(!checker.checkContainNgText(
				lst_SSetStorage.getValue(10) ,
				rowNo,
				lst_SSetStorage.getListCellColumn(10).getResourceKey() )
				)
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}

		return true;
		
	}

	//#CM570908
	// Private methods -----------------------------------------------
	//#CM570909
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
			//#CM570910
			// Connection acquisition	
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM570911
			// Acquire Consignor Code from the schedule. 
			WmsScheduler schedule = new SetStorageSCH();
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
				//#CM570912
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
	
	//#CM570913
	/**
	 * Set the value in Preset area. <BR>
	 * <BR>
	 * Outline : Set the value of Parameter in Preset area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set data in Preset area. <BR>
	 * 		2.Set data in Concealed Item. <BR>
	 * 		3.Set data in balloon information. <BR>
	 * 		4.Make the buttons in Preset area effective. <BR>
	 * 		<DIR>
	 * 			[List of row number of list cell]<BR>
	 * 			<DIR>
	 * 				0.Concealed Item <BR>
	 * 				1.Storage plan date <BR>
	 * 				2.Item Code <BR>
	 * 				3.Flag <BR>
	 * 				4.Storage Location <BR>
	 * 				5.Storage total <BR>
	 * 				6.Packed qty per case <BR>
	 * 				7.Storage Case qty <BR>
	 * 				8.Additional delivery <BR>
	 * 				9.Shortage <BR>
	 * 				10.Expiry Date <BR>
	 * 				11.CaseITF <BR>
	 * 				12.Item Name <BR>
	 * 				13.Packed qty per bundle <BR>
	 * 				14.Storage Piece qty <BR>
	 * 				15.Bundle ITF <BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param listParams Parameter[] Parameter array which stores display data. 
	 * @throws Exception It reports on all exceptions.  
	 */
	private void setList(Parameter[] listParams) throws Exception
	{
		//#CM570914
		// Delete all rows
		lst_SSetStorage.clearRow();

		StorageSupportParameter param = (StorageSupportParameter) listParams[0];

		//#CM570915
		// Consignor Code(Only for Reading)
		txt_RConsignorCode.setText(param.getConsignorCode());
		//#CM570916
		// Consignor Name(Only for Reading)
		txt_RConsignorName.setText(param.getConsignorName());

		StorageSupportParameter[] viewParam = (StorageSupportParameter[]) listParams;

		String label_name = DisplayText.getText("LBL-W0103");
		String label_plancaseqty = DisplayText.getText("LBL-W0444");
		String label_planpieceqty = DisplayText.getText("LBL-W0445");
		String label_caseitf = DisplayText.getText("LBL-W0010");
		String label_bundleitf = DisplayText.getText("LBL-W0006");
				
		for (int i = 0; i < viewParam.length; i++)
		{
			//#CM570917
			// Add row
			lst_SSetStorage.setCurrentRow(i + 1);
			lst_SSetStorage.addRow();

			lst_SSetStorage.setValue(1, WmsFormatter.toDispDate(viewParam[i].getStoragePlanDate(), this.httpRequest.getLocale()));
			lst_SSetStorage.setValue(2, viewParam[i].getItemCodeL());
			lst_SSetStorage.setValue(3, viewParam[i].getCasePieceflgName());


			if (!StringUtil.isBlank(viewParam[i].getStorageLocation()))
			{
				lst_SSetStorage.setValue(4, viewParam[i].getStorageLocation());
			}

			//#CM570918
			// Convert it into the character string that the comma is edited from a numeric type. 
			lst_SSetStorage.setValue(5, WmsFormatter.getNumFormat(viewParam[i].getTotalPlanQty()));
			lst_SSetStorage.setValue(6, WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));

			//#CM570919
			// Storage Case qty and Storage Piece qty processing diverge by Status of the check box where initial is displayed. 
			if(chk_CommonUseStrgRest.getChecked())
			{
				//#CM570920
				// Storage Case qty
				lst_SSetStorage.setValue(7, WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				//#CM570921
				// Storage Piece qty
				lst_SSetStorage.setValue(14, WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
			}
			else
			{
				//#CM570922
				// Storage Case qty
				lst_SSetStorage.setValue(7, "");
				//#CM570923
				// Storage Piece qty
				lst_SSetStorage.setValue(14, "");
			}
			
			//#CM570924
			// Additional delivery
			lst_SSetStorage.setChecked(8, false);			
			//#CM570925
			// Shortage
			lst_SSetStorage.setChecked(9, false);
		
			if (!StringUtil.isBlank(param.getUseByDate()))
			{
				lst_SSetStorage.setValue(10, viewParam[i].getUseByDate());
			}

			lst_SSetStorage.setValue(11, viewParam[i].getITF());
			lst_SSetStorage.setValue(12, viewParam[i].getItemName());
			lst_SSetStorage.setValue(13, WmsFormatter.getNumFormat(viewParam[i].getBundleEnteringQty()));

			lst_SSetStorage.setValue(15, viewParam[i].getBundleITF());

			//#CM570926
			// Connect HIDDEN Item. 
			String hidden = createHiddenList(viewParam[i]);
			//#CM570927
			// Set the HIDDEN item
			lst_SSetStorage.setValue(0, hidden);

			//#CM570928
			// Set the tool tip
			ToolTipHelper toolTip = new ToolTipHelper();
			//#CM570929
			// Item Name
			toolTip.add(label_name, viewParam[i].getItemName());
			//#CM570930
			// Storage Plan Case qty
			toolTip.add(label_plancaseqty, viewParam[i].getPlanCaseQty());
			//#CM570931
			// Storage Plan Piece qty
			toolTip.add(label_planpieceqty, viewParam[i].getPlanPieceQty());
			
			//#CM570932
			// Case ITF
			toolTip.add(label_caseitf, viewParam[i].getITF());
			//#CM570933
			// Bundle ITF
			toolTip.add(label_bundleitf, viewParam[i].getBundleITF());
			//#CM570934
			//Set the tool tip in Current line. 
			lst_SSetStorage.setToolTip(lst_SSetStorage.getCurrentRow(), toolTip.getText());
		}
		//#CM570935
		// Enable the button pressing. 
		//#CM570936
		// Start Storage button
		btn_StorageStart.setEnabled(true);
		//#CM570937
		// Clear list Button
		btn_ListClear.setEnabled(true);
	}

	//#CM570938
	/**
	 * Method to connect HIDDEN Item with one character string. <BR>
	 * <BR>
	 * Outline : Acquire Concealed Item, and convert it into the character string. <BR>
	 * <BR>
	 * <DIR>
	 * 		The Item order list of HIDDEN <BR>
	 * 		<DIR>
	 * 			0.Work No. <BR>
	 * 			1.Last updated date and time <BR>
	 * 			2.PlanCase qty <BR>
	 * 			3.PlanPiece qty <BR>
	 * 			4.Case , PieceFlag <BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param viewParam StorageSupportParameter Parameter including HIDDEN Item.
	 * @return Connected HIDDEN Item. 
	 */
	private String createHiddenList(StorageSupportParameter viewParam)
	{
		String hidden = null;

		//#CM570939
		// HIDDEN Item character string making 
		ArrayList hiddenList = new ArrayList();
		hiddenList.add(0, viewParam.getJobNo());
		hiddenList.add(1, WmsFormatter.getTimeStampString(viewParam.getLastUpdateDate()));
		hiddenList.add(2, WmsFormatter.getNumFormat(viewParam.getPlanCaseQty()));
		hiddenList.add(3, WmsFormatter.getNumFormat(viewParam.getPlanPieceQty()));
		hiddenList.add(4, viewParam.getCasePieceflg());
		hidden = CollectionUtils.getConnectedString(hiddenList);

		return hidden;
	}
	
	//#CM570940
	/** 
	 * Check whether input information on the list cell is an update object. <BR>
	 * <BR>
	 * Outline : the data of the list cell returns True when updating and when it is not so, returns false.  <BR>
	 * <BR> 
	 * @return Check result (When updating it : True When not updating it : false) 
	 * @throws Exception It reports on all exceptions. 
	 */
	private boolean isChangeLine() throws Exception
	{	
		//#CM570941
		// Additional delivery and Shortage check boxes are not checked. 
		//#CM570942
		// And, when Storage Case qty and Storage Piece qty are empty : false
		if (!lst_SSetStorage.getChecked(8) 
			&& !lst_SSetStorage.getChecked(9)
			&& lst_SSetStorage.getValue(7).equals("") 
			&& lst_SSetStorage.getValue(14).equals(""))
		{
			return false;
		}
		return true;
	}
	//#CM570943
	// Event handler methods -----------------------------------------
	//#CM570944
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570945
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570946
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570947
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM570948
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Worker_Code_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570949
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570950
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM570951
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM570952
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM570953
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570954
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570955
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM570956
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM570957
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM570958
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570959
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570960
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM570961
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM570962
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM570963
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570964
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
	public void btn_PSearchConsignor_Click(ActionEvent e) throws Exception
	{
		//#CM570965
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM570966
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM570967
		// Retrieval flag
		param.setParameter(ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY, StorageSupportParameter.SEARCH_STORAGE_WORK);
		
		//#CM570968
		// Work Status(Stand by and Partially Completed of Work information)
		String[] search = new String[2];
		search[0] = new String(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		param.setParameter(ListStorageConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
			 
		//#CM570969
		// Processing Screen -> Result screen
		redirect("/storage/listbox/liststorageconsignor/ListStorageConsignor.do", param, "/progress.do");
	}

	//#CM570970
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrtStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570971
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570972
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtStrgPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM570973
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtStrgPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM570974
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrtStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570975
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
	public void btn_PSearchStrtStrgPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM570976
		// Set the search condition on the Storage plan date retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM570977
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM570978
		// Start Storage plan date
		param.setParameter(ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY, WmsFormatter.toParamDate(txt_StrtStrgPlanDate.getDate()));
		//#CM570979
		// Start flag 
		param.setParameter(ListStoragePlanDateBusiness.RANGESTORAGEPLANDATE_KEY, StorageSupportParameter.RANGE_START);
		//#CM570980
		// Retrieval flag
		param.setParameter(ListStoragePlanDateBusiness.SEARCHSTORAGEPLANDATE_KEY, StorageSupportParameter.SEARCH_STORAGE_WORK);
		
		//#CM570981
		// Work Status(Stand by and Partially Completed of Work information)
		String[] search = new String[2];
		search[0] = new String(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		param.setParameter(ListStoragePlanDateBusiness.WORKSTATUSSTORAGEPLANDATE_KEY, search);
		
		//#CM570982
		// Processing Screen -> Result screen
		redirect("/storage/listbox/liststorageplandate/ListStoragePlanDate.do", param, "/progress.do");
	}

	//#CM570983
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570984
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndStoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570985
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdRtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570986
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdRtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM570987
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdRtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM570988
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEndStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570989
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
	public void btn_PSearchEndStrgPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM570990
		// Set the search condition on the Storage plan date retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM570991
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM570992
		// End Storage plan date
		param.setParameter(ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY, WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));
		//#CM570993
		// End flag 
		param.setParameter(ListStoragePlanDateBusiness.RANGESTORAGEPLANDATE_KEY, StorageSupportParameter.RANGE_END);
		//#CM570994
		// Retrieval flag
		param.setParameter(ListStoragePlanDateBusiness.SEARCHSTORAGEPLANDATE_KEY, StorageSupportParameter.SEARCH_STORAGE_WORK);
		//#CM570995
		// Work Status(Stand by and Partially Completed of Work information)
		String[] search = new String[2];
		search[0] = new String(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		param.setParameter(ListStoragePlanDateBusiness.WORKSTATUSSTORAGEPLANDATE_KEY, search);
			 
		//#CM570996
		// Processing Screen -> Result screen
		redirect("/storage/listbox/liststorageplandate/ListStoragePlanDate.do", param, "/progress.do");
	}

	//#CM570997
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570998
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM570999
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571000
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571001
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571002
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571003
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
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM571004
		// Set the search condition on the Item retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM571005
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM571006
		// Start Storage plan date
		param.setParameter(ListStoragePlanDateBusiness.STARTSTORAGEPLANDATE_KEY, WmsFormatter.toParamDate(txt_StrtStrgPlanDate.getDate()));
		//#CM571007
		// End Storage plan date
		param.setParameter(ListStoragePlanDateBusiness.ENDSTORAGEPLANDATE_KEY, WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));
		//#CM571008
		// Item Code
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM571009
		// Retrieval flag
		param.setParameter(ListStorageItemBusiness.SEARCHITEM_KEY, StorageSupportParameter.SEARCH_STORAGE_WORK);
		
		//#CM571010
		// Work Status(Stand by and Partially Completed of Work information)
		String[] search = new String[2];
		search[0] = new String(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		param.setParameter(ListStorageItemBusiness.WORKSTATUSITEM_KEY, search);
			 
		//#CM571011
		// Processing Screen -> Result screen
		redirect("/storage/listbox/liststorageitem/ListStorageItem.do", param, "/progress.do");
	}

	//#CM571012
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571013
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571014
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM571015
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571016
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM571017
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571018
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM571019
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571020
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM571021
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageInstruction_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571022
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571023
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Change(ActionEvent e) throws Exception
	{
	}

	//#CM571024
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571025
	/** 
	 * It is called when the display button is pressed.<BR>
	 * <BR>
	 * Outline:Retrieve work information from search condition and display input item of input area in the preset.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Check input Item of Input area. (Mandatory input, Number of characters, Character attribute)<BR>
	 * 		2.Start Schedule.<BR>
	 * 		3.Display in the Preset area.<BR>
	 * 		4.Make Start Storage button and Clear list Button effective. <BR>
	 * 		5.Maintain ViewState information. <BR>
	 * 		<DIR>
	 * 			[ViewState information] <BR>
	 * 			<DIR>
	 * 				Start Storage plan date <BR>
	 * 				End Storage plan date <BR>
	 * 				Item Code <BR>
	 * 				Case , PieceFlag <BR>
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
			//#CM571026
			// Clear the Preset area in case of input error. 
			btn_ListClear_Click(e);

			//#CM571027
			// Input Check
			txt_WorkerCode.validate();
			txt_Password.validate();
			txt_ConsignorCode.validate();
			//#CM571028
			// Pattern match character
			txt_StrtStrgPlanDate.validate(false);
			txt_EdRtrivlPlanDate.validate(false);
			txt_ItemCode.validate(false);

			//#CM571029
			// Start Storage plan date should be smaller than End Storage plan date.
			if (!StringUtil.isBlank(txt_StrtStrgPlanDate.getDate()) && !StringUtil.isBlank(txt_EdRtrivlPlanDate.getDate()))
			{
				if (txt_StrtStrgPlanDate.getDate().compareTo(txt_EdRtrivlPlanDate.getDate()) > 0)
				{
					//#CM571030
					// 6023199=Starting planned storage date must precede the end planned storage date.
					message.setMsgResourceKey("6023199");
					return;
				}
			}
			//#CM571031
			// Set in schedule Parameter.
			StorageSupportParameter param = new StorageSupportParameter();
			//#CM571032
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM571033
			// Password
			param.setPassword(txt_Password.getText());
			//#CM571034
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM571035
			// Start Storage plan date
			param.setFromStoragePlanDate(WmsFormatter.toParamDate(txt_StrtStrgPlanDate.getDate()));
			//#CM571036
			// End Storage plan date
			param.setToStoragePlanDate(WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));
			//#CM571037
			// Item Code
			param.setItemCodeCondition(txt_ItemCode.getText());

			//#CM571038
			// Case/Piece flag
			if (rdo_CpfAll.getChecked())
			{
				//#CM571039
				// All
				param.setCasePieceflgCondition(StorageSupportParameter.CASEPIECE_FLAG_ALL);
			}
			else if (rdo_CpfCase.getChecked())
			{
				//#CM571040
				// Case
				param.setCasePieceflgCondition(StorageSupportParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_CpfPiece.getChecked())
			{
				//#CM571041
				// Piece
				param.setCasePieceflgCondition(StorageSupportParameter.CASEPIECE_FLAG_PIECE);
			}
			else if (rdo_CpfAppointOff.getChecked())
			{
				//#CM571042
				// Unspecified
				param.setCasePieceflgCondition(StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
			}

			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new SetStorageSCH();

			StorageSupportParameter[] viewParam = (StorageSupportParameter[]) schedule.query(conn, param);

			if (viewParam == null || viewParam.length == 0)
			{
				//#CM571043
				// Set Message
				message.setMsgResourceKey(schedule.getMessage());

				return;
			}

			//#CM571044
			// Set List cell
			setList(viewParam);

		 	//#CM571045
		 	// Maintain it in ViewState. 
		 	//#CM571046
		 	// Start Storage plan date
			this.getViewState().setString(VK_STARTSTORAGEDATE, WmsFormatter.toParamDate(txt_StrtStrgPlanDate.getDate()));
			//#CM571047
			// End Storage plan date
			this.getViewState().setString(VK_ENDSTORAGEDATE, WmsFormatter.toParamDate(txt_EdRtrivlPlanDate.getDate()));
			//#CM571048
			// Item Code
		 	this.getViewState().setString(VK_ITEMCODE, txt_ItemCode.getText());
			
		 	//#CM571049
		 	// Case , PieceFlag
		 	this.getViewState().setBoolean(VK_CASEPIECE_ALL, rdo_CpfAll.getChecked());
		 	this.getViewState().setBoolean(VK_CASEPIECE_CASE, rdo_CpfCase.getChecked());
		 	this.getViewState().setBoolean(VK_CASEPIECE_PIECE, rdo_CpfPiece.getChecked());
			this.getViewState().setBoolean(VK_CASEPIECE_APPOINTOFF, rdo_CpfAppointOff.getChecked());

			//#CM571050
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
				//#CM571051
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

	//#CM571052
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571053
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
	 * 				Item Code[None] <BR>
	 * 				Case/Piece flag[All] <BR>
	 * 				Display initial of the remaining Storage qty.[false]<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM571054
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM571055
		// Start Storage plan date
		txt_StrtStrgPlanDate.setText("");
		//#CM571056
		// End Storage plan date
		txt_EdRtrivlPlanDate.setText("");

		//#CM571057
		// Item Code
		txt_ItemCode.setText("");

		//#CM571058
		// Set Case/Piece flag in All. 
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);
		//#CM571059
		// Exclude the Check of Input initial of the remianing Storage qty
		chk_CommonUseStrgRest.setChecked(false);
	}

	//#CM571060
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_StorageStart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571061
	/** 
	 * It is called when Start Storage button is pressed.<BR>
	 * <BR>
	 * Outline:Set Storage by information on Preset area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the cursor in Worker Code. <BR>
	 * 		2.Display the dialog box, and confirm whether to Add. <BR>
	 * 		<DIR>
	 * 			[Confirmation Dialog Cancel]<BR>
	 * 			<DIR>
	 * 				Do Nothing.
	 * 			</DIR>
	 * 			[Confirmation Dialog OK]<BR>
	 * 			<DIR>
	 * 				1.Start Schedule.<BR>
	 * 				<DIR>
	 * 					[Parameter]<BR>
	 * 					<DIR>
	 * 						Worker Code <BR>
	 * 						Password <BR>
	 * 						Start Storage plan date <BR>
	 * 						End Storage plan date <BR>
	 * 						Check of remaining Storage qty initial Input. <BR>
	 * 						Preset.Consignor Code <BR>
	 *						Preset.Item Code <BR>
	 *						Preset.Flag <BR>
	 *						Preset.Storage Location <BR>
	 *                      Preset.Plan total qty <BR>
	 *						Preset.Packed qty per case <BR>
	 *						Preset.Packed qty per bundle <BR>
	 *						Preset.Storage Case qty <BR>
	 *						Preset.Storage Piece qty <BR>
	 *						Preset.Flag of additional delivery <BR>
	 *						Preset.Flag of shortage <BR>
	 *						Preset.Expiry Date <BR>
	 *						Preset.Work No. <BR>
	 *                      Preset.Last updated date and time <BR>
	 * 						Preset.Work plan case qty <BR>
	 * 						Preset.Work plan piece qty <BR>
	 * 						Terminal No.<BR>
	 * 						Preset Line No..<BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.When the result of the schedule is true, Clears Add information on Input area and Preset area. <BR>
	 *				3.Display information on Preset again.  <BR>
	 *              4.Initialize Preset area when Preset information is 0.   <BR>
	 *    			Output the Message acquired from the schedule to the screen when schedule result is false.  <BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_StorageStart_Click(ActionEvent e) throws Exception
	{
		//#CM571062
		// Input Check
		txt_WorkerCode.validate();
		txt_Password.validate();

		//#CM571063
		// The maximum number of lines are acquired. 
		int index = lst_SSetStorage.getMaxRows();

		for (int i = 1; i < index; i++)
		{
			try
			{
				//#CM571064
				// Line specification
				lst_SSetStorage.setCurrentRow(i);
				if (isChangeLine())
				{
					if((Formatter.getInt(lst_SSetStorage.getValue(7)) * 
					        Formatter.getInt(lst_SSetStorage.getValue(6))) + 
					        (Formatter.getInt(lst_SSetStorage.getValue(14))) != 0 ){
							//#CM571065
							// Mandatory Check of specified row (Storage Location). 
							lst_SSetStorage.validate(4, true);
					}				    
					
				}
				//#CM571066
				// Check Pattern match character of specified row (Expiry Date). 
				lst_SSetStorage.validate(10, false);

				//#CM571067
				// Input character check for eWareNavi
				if (!checkContainNgText(i))
				{
					return;
				}

			}
			catch (ValidateException ve)
			{
				//#CM571068
				// Set Message
				String errorMessage = MessageResources.getText(ve.getErrorNo(), ve.getBinds().toArray());
				//#CM571069
				// 6023273 = No.{0}{1}
				throw new ValidateException("6023273", Integer.toString(i), errorMessage);
			}
		}

		
		Connection conn = null;
		try
		{
			//#CM571070
			// Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			Vector vecParam = new Vector(index);
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			String terminalno = userHandler.getTerminalNo();


			for (int i = 1; i < index; i++)
			{
				//#CM571071
				// Line specification
				lst_SSetStorage.setCurrentRow(i);

				if (isChangeLine())
				{
					//#CM571072
					// Set in schedule Parameter.
					StorageSupportParameter param = new StorageSupportParameter();
					
					//#CM571073
					// Input area information (For re-display) 
					//#CM571074
					// Worker Code
					param.setWorkerCode(txt_WorkerCode.getText());
					//#CM571075
					// Password
					param.setPassword(txt_Password.getText());
					//#CM571076
					// Start Storage plan date
					param.setFromStoragePlanDate(this.getViewState().getString(VK_STARTSTORAGEDATE));
					//#CM571077
					// End Storage plan date
					param.setToStoragePlanDate(this.getViewState().getString(VK_ENDSTORAGEDATE));
					param.setItemCodeCondition(this.getViewState().getString(VK_ITEMCODE));
	
					//#CM571078
					// Case/Piece flag
					if (this.getViewState().getBoolean(VK_CASEPIECE_ALL))
					{
						//#CM571079
						// All
						param.setCasePieceflgCondition(StorageSupportParameter.CASEPIECE_FLAG_ALL);
					}
					else if (this.getViewState().getBoolean(VK_CASEPIECE_APPOINTOFF))
					{
						//#CM571080
						// Unspecified
						param.setCasePieceflgCondition(StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
					}
					else if (this.getViewState().getBoolean(VK_CASEPIECE_CASE))
					{
						//#CM571081
						// Case
						param.setCasePieceflgCondition(StorageSupportParameter.CASEPIECE_FLAG_CASE);
					}
					else if (this.getViewState().getBoolean(VK_CASEPIECE_PIECE))
					{
						//#CM571082
						// Piece
						param.setCasePieceflgCondition(StorageSupportParameter.CASEPIECE_FLAG_PIECE);
					}					

					//#CM571083
					// For exclusion and update
					//#CM571084
					// Consignor Code(Only for Reading)
					param.setConsignorCode(txt_RConsignorCode.getText());
					//#CM571085
					// Set Item Code (For update)
					param.setItemCode(lst_SSetStorage.getValue(2));					

					//#CM571086
					// Location No.
					if (!StringUtil.isBlank(lst_SSetStorage.getValue(4)))
					{
						param.setStorageLocation(lst_SSetStorage.getValue(4));
					}									
					//#CM571087
					// Expiry Date
					if (!StringUtil.isBlank(lst_SSetStorage.getValue(10)))
					{
						param.setUseByDate(lst_SSetStorage.getValue(10));
					}
									
					//#CM571088
					// For update
					//#CM571089
					// Convert it into a numeric type from the numerical value from which the comma is edited. 
					//#CM571090
					// Plan total qty
					param.setTotalPlanQty(WmsFormatter.getInt(lst_SSetStorage.getValue(5)));
					//#CM571091
					// Packed qty per case
					param.setEnteringQty(WmsFormatter.getInt(lst_SSetStorage.getValue(6)));
					//#CM571092
					// Packed qty per bundle
					param.setBundleEnteringQty(WmsFormatter.getInt(lst_SSetStorage.getValue(13)));
					//#CM571093
					// Result Case qty
					param.setResultCaseQty(WmsFormatter.getInt(lst_SSetStorage.getValue(7)));
					//#CM571094
					// Result Piece qty
					param.setResultPieceQty(WmsFormatter.getInt(lst_SSetStorage.getValue(14)));
					//#CM571095
					// Flag of additional delivery
					param.setRemnantFlag(lst_SSetStorage.getChecked(8));
					//#CM571096
					// Flag of shortage
					param.setShortage(lst_SSetStorage.getChecked(9));
					//#CM571097
					// Terminal No.
					param.setTerminalNumber(terminalno);
	
					//#CM571098
					// Set HIDDEN Item (Work No.(0),Last updated date and time(1),Work plan case qty(2),Work plan piece qty(3),Case , PieceFlag(4))
					String hidden = lst_SSetStorage.getValue(0);
					param.setJobNo(CollectionUtils.getString(0, hidden));
					//#CM571099
					// Last updated date and time
					//#CM571100
					// Convert it into the Date type from String type (YYYYMMDDHHMMSS). 
					param.setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(1, hidden)));
					//#CM571101
					// Work plan case qty
					param.setPlanCaseQty(WmsFormatter.getInt(CollectionUtils.getString(2, hidden)));
					//#CM571102
					// Work plan piece qty
					param.setPlanPieceQty(WmsFormatter.getInt(CollectionUtils.getString(3, hidden)));
					//#CM571103
					// Case , PieceFlag
					param.setCasePieceflg(CollectionUtils.getString(4, hidden));
					//#CM571104
					// Line No. is maintained. 
					param.setRowNo(i);
	
					vecParam.addElement(param);
				}
			}
			if (vecParam == null || vecParam.size() <= 0)
			{
				//#CM571105
				// 6023154 = There is no data to update.
				message.setMsgResourceKey("6023154");
				return;
			}

			StorageSupportParameter[] paramArray = new StorageSupportParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			WmsScheduler schedule = new SetStorageSCH();
			//#CM571106
			// Schedule start
			StorageSupportParameter[] viewParam = (StorageSupportParameter[]) schedule.startSCHgetParams(conn, paramArray);

			if (viewParam == null)
			{
				conn.rollback();
				//#CM571107
				// Set Message
				message.setMsgResourceKey(schedule.getMessage());
			}
			else if (viewParam!= null && viewParam.length >= 0)
			{
				conn.commit();
				//#CM571108
				// Set Message
				message.setMsgResourceKey(schedule.getMessage());
				//#CM571109
				// Clear the Preset area when object data is lost. 
				btn_ListClear_Click(e);

				if (viewParam.length > 0)
				{
					//#CM571110
					// Set List cell
					setList(viewParam);
				}
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
				//#CM571111
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

	//#CM571112
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571113
	/** 
	 * It is called when the clear list button is pressed.<BR>
	 * <BR>
	 * Outline:Clears all display information on Preset. <BR>
	 * <BR>
	 * <DIR>
	 *    <DIR>
	 * 	    "Do you clear the list?"<BR>
	 * 		[Confirmation Dialog Cancel]<BR>
	 *			<DIR>
	 *				Do Nothing.
	 *			</DIR>
	 * 		[Confirmation Dialog OK]<BR>
	 *			<DIR>
	 *				1.Clears all List cells.<BR>
	 *				2.Clears Consignor Code  and Consignor Name (Only for Reading). 
	 *				3.Invalidate Start Storage button  and Clear list Button. <BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		//#CM571114
		// Delete all rows
		lst_SSetStorage.clearRow();

		//#CM571115
		// Consignor Code(Only for Reading)
		txt_RConsignorCode.setText("");
		//#CM571116
		// Consignor Name(Only for Reading)
		txt_RConsignorName.setText("");

		//#CM571117
		// Button not pressed properly
		//#CM571118
		// Start Storage button
		btn_StorageStart.setEnabled(false);
		//#CM571119
		// Clear list Button
		btn_ListClear.setEnabled(false);
	}

	//#CM571120
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571121
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571122
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571123
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571124
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571125
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571126
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571127
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571128
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571129
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SSetStorage_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571130
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SSetStorage_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571131
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SSetStorage_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571132
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SSetStorage_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM571133
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SSetStorage_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571134
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SSetStorage_Change(ActionEvent e) throws Exception
	{
	}

	//#CM571135
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Set_Click(ActionEvent e) throws Exception
	{
	}

	//#CM571136
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrgRestFirstInp_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571137
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUseStrgRest_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571138
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUseStrgRest_Change(ActionEvent e) throws Exception
	{
	}


}
//#CM571139
//end of class
