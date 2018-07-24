// $Id: StoragePlanModify2Business.java,v 1.2 2006/12/07 08:57:14 suresh Exp $

//#CM572618
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.storageplanmodify;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
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
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststoragelocation.ListStorageLocationBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplandate.ListStoragePlanDateBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplanmodify.ListStoragePlanModifyBusiness;
import jp.co.daifuku.wms.storage.schedule.StoragePlanModifySCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM572619
/**
 * Designer :T.Uehata<BR>
 * Maker    : T.Uehata <BR>
 * <BR>
 * Stock Plan correction and Deletion (detailed information correction Deletion) class.  <BR>
 * Display input information on the Essential information input screen in the above part display area, and display Preset area Output data in Preset area.  <BR>
 * Hand over Parameter to the schedule which does Stock Plan correction and Deletion.  <BR>
 * Moreover, do Commit  of Transaction and Rollback on this screen.  <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Input button is pressed(<CODE>btn_Input_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 		Set the content input from the input area in Parameter, and the schedule checks the input condition based on the Parameter. <BR>
 * 		Receive True when the result is received from the schedule, and processing Completes normally, <BR>
 * 		and false when the schedule does not do Completed because of the condition error etc.<BR>
 * 		The result of the schedule outputs the message acquired from the schedule to the screen. <BR>
 * 		Add information on the input area to Preset area when the result is true. <BR>
 * 		Update the data of Preset for the modification in information on the input area when you press the input button after the modify button is pressed. <BR>
 * 		<BR>
 * 		[Parameter] *Mandatory input <BR>
 * 		<BR>
 * 		<DIR>
 * 			Consignor Code <BR>
 * 			Consignor Name <BR>
 * 			Item Code <BR>
 * 			Item Name <BR>
 * 			Storage plan date <BR>
 * 			CaseStorage Location <BR>
 * 			PieceStorage Location <BR>
 * 			Packed qty per case*2 <BR>
 * 			Packed qty per bundle <BR>
 * 			Host Plan Case qty*3 <BR>
 * 			Host Plan Piece qty*3 <BR>
 * 			Plan total qty <BR>
 * 			CaseITF <BR>
 * 			Bundle ITF <BR>
 * 			Storage Plan unique key <BR>
 * 			Last updated date and time <BR>
 * 			Case/Piece flag <BR>
 * 			Plan making Flag <BR>
 * 			<BR>
 * 			*2 <BR>
 * 			When entered Host Plan Case qty(>0), Mandatory input <BR>
 * 			*3 <BR>
 * 			The input of one or more is Mandatory condition in either Host Plan Case qty or Host Plan Piece qty.  <BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * 2Processing when Add/Modify button is pressed (<CODE>btn_ModifySubmit_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 		The content input from Preset area is set in Parameter, and the schedule does the Storage Plan data correction processing based on the Parameter.  <BR>
 * 		When the result is received from the schedule, and processing Completed normally
 * 		Acquire Preset area Output data from the schedule again, and display Preset area again.  <BR>
 * 		Receive null when the schedule does not Completed.  <BR>
 * 		The result of the schedule outputs the message acquired from the schedule to the screen.  <BR>
 * 		<BR>
 * 		[Parameter] *Mandatory input <BR>
 * 		<BR>
 * 		<DIR>
 * 			Preset.CaseStorage Location <BR>
 * 			Preset.PieceStorage Location <BR>
 * 			Preset.Packed qty per case <BR>
 * 			Preset.Packed qty per bundle <BR>
 * 			Preset.Host Plan Case qty <BR>
 * 			Preset.Host Plan Piece qty <BR>
 * 			Preset.CaseITF <BR>
 * 			Preset.Bundle ITF <BR>
 * 			Preset.Last updated date and time* <BR>
 * 			Preset.Preset Line No..* <BR>
 * 			Preset.Storage Plan unique key* <BR>
 * 			Preset.Case/Piece flag* <BR>
 * 			Screen.Plan making Flag* <BR>
 * 		</DIR>
 * 		<BR>
 * 		[Return Data] <BR>
 * 		<BR>
 * 		<DIR>
 * 			CaseStorage Location <BR>
 * 			PieceStorage Location <BR>
 * 			Packed qty per case <BR>
 * 			Packed qty per bundle <BR>
 * 			Host Plan Case qty <BR>
 * 			Host Plan Piece qty <BR>
 * 			CaseITF <BR>
 * 			Bundle ITF <BR>
 * 			Last updated date and time <BR>
 * 			Preset Line No.. <BR>
 * 			Storage Plan unique key <BR>
 * 			Case/Piece flag <BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * 4.Deletion Processing when Delete Button is pressed(<CODE>lst_SShpPlnDaMdDlt_Click</CODE> Method, <CODE>btn_AllDelete_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 		The content input from Preset area is set in Parameter, and the schedule does Storage Plan Deletion based on the Parameter.  <BR>
 * 		Receive True when the result is received from the schedule, and processing Completes normally,  <BR>
 * 		and false when the schedule does not do Completed because of the condition error etc. <BR>
 * 		The result of the schedule outputs the message acquired from the schedule to the screen.  <BR>
 * 		<BR>
 * 		[Parameter] *Mandatory input <BR>
 * 		<BR>
 * 		<DIR>
 * 			Consignor Code <BR>
 * 			Item Code <BR>
 * 			Storage plan date <BR>
 * 			CaseStorage Location <BR>
 * 			PieceStorage Location <BR>
 * 			Preset.Storage Plan unique key <BR>
 * 			Preset.Last updated date and time <BR>
 * 			Preset Line No.. <BR>
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
public class StoragePlanModify2Business extends StoragePlanModify2 implements WMSConstants
{

	//#CM572620
	// For ViewStateKEY(Concealment Parameter)
	//#CM572621
	/**
	 * For ViewState information : Preset Line No.
	 */	
	private static final String LINENO = "LINENO";
	
	//#CM572622
	/**
	 * For ViewState information : Storage Plan unique key
	 */	
	private static final String STORAGEID = "STORAGEID";
	
	//#CM572623
	/**
	 * For ViewState information : Last updated date and time
	 */	
	private static final String LASTUPDDATE = "LASTUPDDATE";
	
	//#CM572624
	/**
	 * For ViewState information : Case/Piece flag
	 */	
	private static final String CASEPIECEFLG = "CASEPIECEFLG";

	//#CM572625
	/**
	 * For ViewState information : Plan making Flag
	 */	
	protected static final String REGISTKIND = "REGISTKIND";

	//#CM572626
	// List cell row specification variable
	//#CM572627
	/**
	 * Concealed ItemList cell column index
	 */	
	private static final int LST_HDN = 0;
	
	//#CM572628
	/**
	 * DeletionButtonList cell column index
	 */	
	private static final int LST_DELBTN = 1;
	
	//#CM572629
	/**
	 * Correction Button List cell column index
	 */	
	private static final int LST_MODBTN = 2;
	
	//#CM572630
	/**
	 * CaseStorage LocationList cell column index
	 */	
	private static final int LST_CASESTRGLCT = 3;
	
	//#CM572631
	/**
	 * Packed qty per caseList cell column index
	 */	
	private static final int LST_CASEQTY = 4;
	
	//#CM572632
	/**
	 * Host Plan Case qtyList cell column index
	 */	
	private static final int LST_HOSTCASEQTY = 5;
	
	//#CM572633
	/**
	 * CaseITFList cell column index
	 */	
	private static final int LST_CASEITF = 6;
	
	//#CM572634
	/**
	 * PieceStorage LocationList cell column index
	 */	
	private static final int LST_PIECESTRGLCT = 7;
	
	//#CM572635
	/**
	 * Packed qty per bundleList cell column index
	 */	
	private static final int LST_BUNDLEQTY = 8;
	
	//#CM572636
	/**
	 * Host Plan Piece qtyList cell column index
	 */	
	private static final int LST_HOSTPIECEQTY = 9;
	
	//#CM572637
	/**
	 * Bundle ITFList cell column index
	 */	
	private static final int LST_BUNDLEITF = 10;

	//#CM572638
	// The List cell Concealed Item order
	//#CM572639
	/**
	 * For Concealed Item : Storage Plan unique key
	 */
	private static final int HDNIDX_UKEY = 0;
	
	//#CM572640
	/**
	 * For Concealed Item : Last updated date and time
	 */
	private static final int HDNIDX_UPDAY = 1;
	
	//#CM572641
	/**
	 * For Concealed Item : Case/Piece flag
	 */
	private static final int HDNIDX_CPF = 2;
	
	//#CM572642
	/**
	 * For Concealed Item : Update flag
	 */
	private static final int HDNUPDATEFLAG_IDX = 3;
	
	//#CM572643
	/**
	 * For Concealed Item : CaseStorage Location
	 */
	private static final int HDNCASELOCATION_IDX = 4;
	
	//#CM572644
	/**
	 * For Concealed Item : PieceStorage Location
	 */
	private static final int HDNPIECELOCATION_IDX = 5;
	
	//#CM572645
	/**
	 * For Concealed Item : Packed qty per case
	 */
	private static final int HDNENTERINGQTY_IDX = 6;
	
	//#CM572646
	/**
	 * For Concealed Item : Packed qty per bundle
	 */
	private static final int HDNBUNDLEENTERINGQTY_IDX = 7;
	
	//#CM572647
	/**
	 * For Concealed Item : PlanCase qty
	 */
	private static final int HDNPLANCASEQTY_IDX = 8;
	
	//#CM572648
	/**
	 * For Concealed Item : PlanPiece qty
	 */
	private static final int HDNPLANPIECEQTY_IDX = 9;
	
	//#CM572649
	/**
	 * For Concealed Item : CaseITF
	 */
	private static final int HDNITF_IDX = 10;

	//#CM572650
	/**
	 * For Concealed Item : Bundle ITF
	 */
	private static final int HDNBUNDLEITF_IDX = 11;
	


	//#CM572651
	// Class fields --------------------------------------------------

	//#CM572652
	// Class variables -----------------------------------------------

	//#CM572653
	// Class method --------------------------------------------------

	//#CM572654
	// Constructors --------------------------------------------------

	//#CM572655
	// Public methods ------------------------------------------------

	//#CM572656
	/**
	 * It is called before the call of each control event. <BR>
	 * <BR>
	 * Outline:Display the Confirmation dialog.<BR>
	 * Or, Set the cursor in Case Storage Location.<BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions.  
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM572657
			//Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM572658
			//Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM572659
			//Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
		//#CM572660
		//  Confirmation Dialog when Add/Modify button is pressed "Do you commit modification?"
		btn_ModifySubmit.setBeforeConfirm("MSG-W0033");

		//#CM572661
		//  Confirmation Dialog when Delete All button is pressed "Delete all data?"
		btn_AllDelete.setBeforeConfirm("MSG-W0031");
		
		setFocus(txt_CaseStorageLocation);
	}

	//#CM572662
	/**
	 * It is called when initial data of the screen is displayed.<BR>
	 * <BR>
	 * Outline:Display initial data in the screen. <BR>
	 * <BR>
	 * <DIR>
	 * 	 1.Start Schedule.
	 *   <DIR>
	 *  	[Parameter] *Mandatory input<BR>
	 *   	<DIR>
	 * 			Worker Code* <BR>
	 * 			Password* <BR>
	 * 			Consignor Code* <BR>
	 * 			Storage plan date* <BR>
	 * 			Item Code* <BR>
	 * 			CaseStorage Location <BR>
	 * 			PieceStorage Location <BR>
	 * 			Plan making Flag* <BR>
	 *		</DIR>
	 * 	 </DIR>
	 *	 2.Display the input area and Preset area based on Preset area Output data acquired from the schedule when processing normally Completes. <BR>
	 *   <DIR>
	 *   	Input area Item name [Initial value]<BR>
	 *   	<DIR>
	 * 			Consignor Code[Essential information.Consignor Code] <BR>
	 * 			Consignor Name[Return Data[0].Consignor Name] <BR>
	 * 			Storage plan date[Essential information.Storage plan date] <BR>
	 * 			Item Code[Essential information.Item Code] <BR>
	 * 			Item Name[Return Data[0].Item Name] <BR>
	 * 			Plan making Flag[Essential information.Plan making Flag Description] <BR>
	 * 			CaseStorage Location[None] <BR>
	 * 			PieceStorage Location[None] <BR>
	 * 			Packed qty per case[None] <BR>
	 * 			Packed qty per bundle[None] <BR>
	 * 			Host Plan Case qty[None] <BR>
	 * 			Host Plan Piece qty[None] <BR>
	 * 			CaseITF[None] <BR>
	 * 			Bundle ITF[None] <BR>
	 * 	 	</DIR>
	 *   	Preset areaItem Name[Initial value]<BR>
	 *   	<DIR>
	 * 			CaseStorage Location[Return Data.CaseStorage Location] <BR>
	 * 			PieceStorage Location[Return Data.PieceStorage Location] <BR>
	 * 			Packed qty per case[Return Data.Packed qty per case] <BR>
	 * 			Packed qty per bundle[Return Data.Packed qty per bundle] <BR>
	 * 			Host Plan Case qty[Return Data.Host Plan Case qty] <BR>
	 * 			Host Plan Piece qty[Return Data.Host Plan Piece qty] <BR>
	 * 			CaseITF[Return Data.CaseITF] <BR>
	 * 			Bundle ITF[Return Data.Bundle ITF] <BR>
	 * 	 	</DIR>
	 *   </DIR>
	 *	 Null is received when the condition error etc. occur, and it returns to the previous screen in empty array Parameters when pertinent data is not found. <BR>
	 *   3.The result of the schedule outputs the message acquired from the schedule to the screen. <BR>
	 *   4.Initial value of Preset Line No. of ViewState : Set -1.<BR>
	 *   5.Set the cursor in initial Case Storage Location. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM572663
		// Set Title
		lbl_SettingName.setText(DisplayText.getText(this.getViewState().getString(StoragePlanModifyBusiness.TITLE)));		
		
		Connection conn = null;

		try
		{

			//#CM572664
			// Correction and Deletion tab of the detailed information is put out. 
			tab_BscDtlMdfyDlt.setSelectedIndex(2);

			//#CM572665
			// Set in schedule Parameter.
			StorageSupportParameter param = new StorageSupportParameter();
			//#CM572666
			// Worker Code
			param.setWorkerCode(
				this.getViewState().getString(StoragePlanModifyBusiness.WORKERCODE));
			//#CM572667
			// Password
			param.setPassword(this.getViewState().getString(StoragePlanModifyBusiness.PASSWORD));
			//#CM572668
			// Consignor Code
			param.setConsignorCode(
				this.getViewState().getString(ListStorageConsignorBusiness.CONSIGNORCODE_KEY));
			//#CM572669
			// Storage plan date
			param.setStoragePlanDate(
				WmsFormatter.toParamDate(
					this.getViewState().getString(ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY),
					this.httpRequest.getLocale()));
			//#CM572670
			// Item Code
			param.setItemCode(this.getViewState().getString(ListStorageItemBusiness.ITEMCODE_KEY));
			//#CM572671
			// CaseStorage Location
			param.setCaseLocationCondition(
				this.getViewState().getString(ListStoragePlanModifyBusiness.CASESTRGLOCATION_KEY));
			//#CM572672
			// PieceStorage Location
			param.setPieceLocationCondition(
				this.getViewState().getString(ListStoragePlanModifyBusiness.PIECESTRGLOCATION_KEY));
			//#CM572673
			// Plan making Flag
			param.setRegistKbn(this.getViewState().getString(REGISTKIND));

			//#CM572674
			// Acquire the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new StoragePlanModifySCH();

			//#CM572675
			// Acquire the data displayed in Preset area. 
			StorageSupportParameter[] viewparam =
				(StorageSupportParameter[]) schedule.query(conn, param);

			//#CM572676
			// Message is received, and it returns to Essential information Setting Screen when the error etc. occur. 
			if (viewparam == null || (viewparam != null && viewparam.length == 0))
			{
				//#CM572677
				// Set Message in ViewState
				this.getViewState().setString(
					StoragePlanModifyBusiness.MESSAGE,
					schedule.getMessage());
				//#CM572678
				// Deletion and correction Screen of detailed information ->Essential information Setting Screen
				forward(StoragePlanModifyBusiness.DO_MODIFY);

				return;
			}

			//#CM572679
			// Set Input area and label Item. 
			//#CM572680
			// Consignor Code
			lbl_JavaSetCnsgnrCd.setText(
				this.getViewState().getString(ListStorageConsignorBusiness.CONSIGNORCODE_KEY));
			//#CM572681
			// Consignor Name
			lbl_JavaSetCnsgnrNm.setText(viewparam[0].getConsignorName());
			//#CM572682
			// Storage plan date
			txt_FDate.setText(
				this.getViewState().getString(ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY));
			//#CM572683
			// Item Code
			lbl_JavaSetItemCd.setText(
				this.getViewState().getString(ListStorageItemBusiness.ITEMCODE_KEY));
			//#CM572684
			// Item Name
			lbl_JavaSetItemNm.setText(viewparam[0].getItemName());
			//#CM572685
			// Plan making Flag
			lbl_JavaSetRegistKind.setText(getRegistKindName(this.getViewState().getString(REGISTKIND)));
			
			//#CM572686
			// Preset area.Display the acquisition data in List cell. 
			setList(viewparam);

			//#CM572687
			// Initializes Input area. 
			inputAreaClear();

			//#CM572688
			// Set Message
			message.setMsgResourceKey(schedule.getMessage());

			//#CM572689
			// When there is no data in Preset line
			if (lst_StoragePlanModify.getMaxRows() == 1)
			{
				//#CM572690
				// Nullify correction and all DeletionButton. 
				//#CM572691
				// AddCorrection Button 
				btn_ModifySubmit.setEnabled(false);
				//#CM572692
				// All Delete button
				btn_AllDelete.setEnabled(false);
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
				//#CM572693
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

	}

	//#CM572694
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
		//#CM572695
		// CaseStorage Location
		String casestoragelocation =
			param.getParameter(ListStoragePlanModifyBusiness.CASESTRGLOCATION_KEY);
		//#CM572696
		// PieceStorage Location
		String piecestoragelocation =
			param.getParameter(ListStoragePlanModifyBusiness.PIECESTRGLOCATION_KEY);

		//#CM572697
		// Set the value when it is not empty. 
		//#CM572698
		// CaseStorage Location
		if (!StringUtil.isBlank(casestoragelocation))
		{
			txt_CaseStorageLocation.setText(casestoragelocation);
		}
		//#CM572699
		// PieceStorage Location
		if (!StringUtil.isBlank(piecestoragelocation))
		{
			txt_PieseStorageLocation.setText(piecestoragelocation);
		}
	}
	//#CM572700
	// Package methods -----------------------------------------------

	//#CM572701
	// Protected methods ---------------------------------------------
	//#CM572702
	/**
	 * Do the Input Check.<BR>
	 * <BR>
	 * Outline : Set Message and Return false when abnormality is found. <BR>
	 * <BR>
	 * @return Result of Input Check (true:Normal false:Abnormal) 
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
		if (!checker.checkContainNgText(txt_CaseStorageLocation))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_PieseStorageLocation))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_CaseItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_BundleItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		return true;
		
	}

	//#CM572703
	// Private methods -----------------------------------------------
	//#CM572704
	/**
	 * Clear each input Item from Input area.<BR>
	 * <BR>
	 * Outline:Clear all input Items. <BR>
	 * <BR>
	 * @throws Exception It reports on all exceptions. 
	 */
	private void inputDataClear() throws Exception
	{
		//#CM572705
		// CaseStorage Location
		txt_CaseStorageLocation.setText("");
		//#CM572706
		// PieceStorage Location
		txt_PieseStorageLocation.setText("");
		//#CM572707
		// Packed qty per case
		txt_CaseEntering.setText("");
		//#CM572708
		// Packed qty per bundle
		txt_BundleEntering.setText("");
		//#CM572709
		// Host Plan Case qty
		txt_HostCasePlanQty.setText("");
		//#CM572710
		// Host Plan Piece qty
		txt_HostPiesePlanQty.setText("");
		//#CM572711
		// CaseITF
		txt_CaseItf.setText("");
		//#CM572712
		// Bundle ITF
		txt_BundleItf.setText("");
	}
	
	//#CM572713
	/**
	 * Clear the Input area.<BR>
	 * <BR>
	 * Outline : Initialize the input area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Clear input Items. <BR>
	 * 		2.Clears Button of Input area. <BR>
	 * 		3.Set Initial value in the color of Selection Preset Line No and Selection Preset line. <BR>
	 * 		4.Set the cursor in Case Storage Location.<BR>
	 * </DIR>
	 * <BR>
	 * @throws Exception It reports on all exceptions. 
	 */
	private void inputAreaClear() throws Exception
	{
		//#CM572714
		// Empty input Item. 
		inputDataClear();

		//#CM572715
		// Nullify Button of Input area. 
		//#CM572716
		// Input Button
		btn_Input.setEnabled(false);
		//#CM572717
		// ClearButton
		btn_Clear.setEnabled(false);

		//#CM572718
		// Reset correction Status (Preset Line  No.) in Initial value. 
		this.viewState.setInt(LINENO, -1);

		//#CM572719
		// Reset the color of the Selection line in Initial value. 
		lst_StoragePlanModify.resetHighlight();

		//#CM572720
		// Set the cursor in CaseStorage Location. 
		setFocus(txt_CaseStorageLocation);
	}

	//#CM572721
	/**
	 * Method which sets the data of Preset area in the Parameter array. <BR>
	 * <BR>
	 * Outline:Set the data of Preset area in the Parameter array, and return it. <BR>
	 * Return null when the set data does not exist. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set Preset data of All in case of new input or Movement Button pressing. (Preset Line No. for correction = -1)<BR>
	 * 		2.Set Preset data in case of the input data under the correction except the line for the correction. <BR>
	 * 		<DIR>
	 *   		[Return Data]<BR>
	 *   		<DIR>
	 * 				Worker Code<BR>
	 * 				Password<BR>
	 * 				Consignor Code<BR>
	 * 				Consignor Name<BR>
	 * 				Storage plan date<BR>
	 * 				Item Code<BR>
	 * 				Item Name<BR>
	 * 				CaseStorage Location(For another Retrieval)<BR>
	 * 				PieceStorage Location(For another Retrieval)<BR>
	 * 				Preset.CaseStorage Location<BR>
	 * 				Preset.PieceStorage Location<BR>
	 * 				Preset.Item Code<BR>
	 * 				Preset.Item Name<BR>
	 * 				Preset.Packed qty per case<BR>
	 * 				Preset.Packed qty per bundle<BR>
	 * 				Preset.Host Plan Case qty<BR>
	 * 				Preset.Host Plan Piece qty<BR>
	 * 				Preset.Plan total qty<BR>
	 * 				Preset.CaseITF<BR>
	 * 				Preset.Bundle ITF<BR>
	 * 				Preset.Storage Plan unique key<BR>
	 * 				Preset.Last updated date and time<BR>
	 * 				Preset.Case , PieceFlag<BR>
	 * 				Preset Line No..<BR>
	 * 				Terminal No.<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param lineno int Line No. for modification
	 * @return <CODE>StorageSupportParameter</CODE> array which stores information on Preset area. 
	 * @throws Exception It reports on all exceptions.  
	 */
	private StorageSupportParameter[] setListParam(int lineno) throws Exception
	{
		Vector vecParam = new Vector();

		Locale locale = this.getHttpRequest().getLocale();

		String searchcasestrglct =
			this.viewState.getString(ListStoragePlanModifyBusiness.CASESTRGLOCATION_KEY);
		String searchpiecestrglct =
			this.viewState.getString(ListStoragePlanModifyBusiness.PIECESTRGLOCATION_KEY);
		String workercd = viewState.getString(StoragePlanModifyBusiness.WORKERCODE);
		String password = viewState.getString(StoragePlanModifyBusiness.PASSWORD);

		for (int i = 1; i < lst_StoragePlanModify.getMaxRows(); i++)
		{
			//#CM572722
			// Do not process the line for the correction. 
			if (i == lineno)
			{
				continue;
			}

			//#CM572723
			// Specify the edit line. 
			lst_StoragePlanModify.setCurrentRow(i);

			//#CM572724
			// Do not set the data which has not been changed. 
			if (lineno < 0
				&& CollectionUtils.getString(
					HDNUPDATEFLAG_IDX,
					lst_StoragePlanModify.getValue(LST_HDN)).equals(
					StorageSupportParameter.UPDATEFLAG_NO))
			{
				continue;
			}

			//#CM572725
			// Set in schedule Parameter.
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM572726
			// Basic Screen information
			//#CM572727
			// Worker Code
			param.setWorkerCode(workercd);
			//#CM572728
			// Password
			param.setPassword(password);
			//#CM572729
			// Consignor Code
			param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
			//#CM572730
			// Consignor Name
			param.setConsignorName(lbl_JavaSetCnsgnrNm.getText());
			//#CM572731
			// Storage plan date
			param.setStoragePlanDate(
				WmsFormatter.toParamDate(txt_FDate.getText(), locale));
			//#CM572732
			// Item Code
			param.setItemCode(lbl_JavaSetItemCd.getText());
			//#CM572733
			// Item Name
			param.setItemName(lbl_JavaSetItemNm.getText());
			//#CM572734
			// CaseStorage Location(For another Retrieval)
			param.setCaseLocationCondition(searchcasestrglct);
			//#CM572735
			// PieceStorage Location(For another Retrieval)
			param.setCaseLocationCondition(searchpiecestrglct);

			//#CM572736
			// Preset area.Content of List cell
			//#CM572737
			// CaseStorage Location
			param.setCaseLocation(lst_StoragePlanModify.getValue(LST_CASESTRGLCT));
			//#CM572738
			// PieceStorage Location
			param.setPieceLocation(lst_StoragePlanModify.getValue(LST_PIECESTRGLCT));
			//#CM572739
			// Packed qty per case
			param.setEnteringQty(Formatter.getInt(lst_StoragePlanModify.getValue(LST_CASEQTY)));
			//#CM572740
			// Packed qty per bundle
			param.setBundleEnteringQty(
				Formatter.getInt(lst_StoragePlanModify.getValue(LST_BUNDLEQTY)));
			//#CM572741
			// Host Plan Case qty
			param.setPlanCaseQty(Formatter.getInt(lst_StoragePlanModify.getValue(LST_HOSTCASEQTY)));
			//#CM572742
			// Host Plan Piece qty
			param.setPlanPieceQty(
				Formatter.getInt(lst_StoragePlanModify.getValue(LST_HOSTPIECEQTY)));	

			//#CM572743
			// Plan total qty calculates the Case/Piece flag distinction way in Storage Plan Operator.
			int enteringQty = param.getEnteringQty();
			int totalQty = param.getPlanCaseQty() * enteringQty + param.getPlanPieceQty();
			//#CM572744
			// Plan total qty
			param.setTotalPlanQty(totalQty);

			//#CM572745
			// CaseITF
			param.setITF(lst_StoragePlanModify.getValue(LST_CASEITF));
			//#CM572746
			// Bundle ITF
			param.setBundleITF(lst_StoragePlanModify.getValue(LST_BUNDLEITF));
			//#CM572747
			// Preset Line No.
			param.setRowNo(i);
			//#CM572748
			// Concealed Item
			//#CM572749
			// Storage Plan unique key
			param.setStoragePlanUKey(
				CollectionUtils.getString(HDNIDX_UKEY, lst_StoragePlanModify.getValue(LST_HDN)));
			//#CM572750
			// Last updated date and time
			param.setLastUpdateDate(
				WmsFormatter.getTimeStampDate(
					CollectionUtils.getString(
						HDNIDX_UPDAY,
						lst_StoragePlanModify.getValue(LST_HDN))));
			//#CM572751
			// Case/Piece flag
			param.setCasePieceflg(
				CollectionUtils.getString(HDNIDX_CPF, lst_StoragePlanModify.getValue(LST_HDN)));

			//#CM572752
			// Set Item other than Preset area. 
			//#CM572753
			// Worker Code
			param.setWorkerCode(this.viewState.getString(StoragePlanModifyBusiness.WORKERCODE));
			//#CM572754
			// Password
			param.setPassword(this.viewState.getString(StoragePlanModifyBusiness.PASSWORD));

			//#CM572755
			// Terminal No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());

			//#CM572756
			// One line is added in a lot. 
			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			//#CM572757
			// The value is set if there is set Preset data.
			StorageSupportParameter[] listparam = new StorageSupportParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			//#CM572758
			// Null is set if there is no set Preset data.
			return null;
		}
	}

	//#CM572759
	/**
	 * Method which sets the value of the Parameter array in Preset area. <BR>
	 * <BR>
	 * Outline:Set the value of the Parameter array in Preset area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set Concealed Item. <BR>
	 * 		2.Set data in each Item of the list cell. <BR>
	 * </DIR>
	 * <DIR>
	 * 		[Concealed Item]
	 * 		<DIR>
	 * 			0.Storage Plan unique key <BR>
	 * 			1.Last updated date and time <BR>
	 * 			2.Case , PieceFlag <BR>
	 * 			3.Update flag <BR>
	 * 			4.CaseStorage Location <BR>
	 * 			5.PieceStorage Location <BR>
	 * 			6.Packed qty per case <BR>
	 * 			6.Packed qty per bundle <BR>
	 * 			7.PlanCase qty <BR>
	 * 			8.PlanPiece qty <BR>
	 * 			9.CaseITF <BR>
	 * 			10.Bundle ITF <BR>
	 *	 	</DIR>
	 * </DIR>
	 * <DIR>
	 * 		[List cell row number list]
	 * 		<DIR>
	 * 			0.Concealed Item <BR>
	 * 			3.CaseStorage Location <BR>
	 * 			4.Packed qty per case <BR>
	 * 			5.Host Plan Case qty <BR>
	 * 			6.CaseITF <BR>
	 * 			7.PieceStorage Location <BR>
	 * 			8.Packed qty per bundle <BR>
	 * 			9.Host Plan Piece qty <BR>
	 * 			10.Bundle ITF <BR>
	 *	 	</DIR>
	 * </DIR>
	 * <BR>
	 * @param param StorageSupportParameter[] Information displayed in Preset area. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setList(StorageSupportParameter[] param) throws Exception
	{

		for (int i = 0; i < param.length; i++)
		{
			//#CM572760
			// Add row
			lst_StoragePlanModify.addRow();

			//#CM572761
			// Specify the edit line. 
			lst_StoragePlanModify.setCurrentRow(i + 1);

			//#CM572762
			// Concealment Parameter
			Vector hdnpara = new Vector();
			//#CM572763
			// Concealment Parameter.Storage Plan unique key
			hdnpara.add(param[i].getStoragePlanUKey());
			//#CM572764
			// Concealment Parameter.Last updated date and time
			hdnpara.add(WmsFormatter.getTimeStampString(param[i].getLastUpdateDate()));
			//#CM572765
			// Concealment Parameter.Case/Piece flag
			hdnpara.add(param[i].getCasePieceflg());
			//#CM572766
			// Update flag
			hdnpara.add(StorageSupportParameter.UPDATEFLAG_NO);
			//#CM572767
			// Concealment Parameter.CaseStorage Location
			hdnpara.add(param[i].getCaseLocation());
			//#CM572768
			// Concealment Parameter.PieceStorage Location
			hdnpara.add(param[i].getPieceLocation());
			//#CM572769
			// Concealment Parameter.Packed qty per case
			hdnpara.add(WmsFormatter.getNumFormat(param[i].getEnteringQty()));
			//#CM572770
			// Concealment Parameter.Packed qty per bundle
			hdnpara.add(WmsFormatter.getNumFormat(param[i].getBundleEnteringQty()));
			//#CM572771
			// Concealment Parameter.PlanCase qty
			hdnpara.add(WmsFormatter.getNumFormat(param[i].getPlanCaseQty()));
			//#CM572772
			// Concealment Parameter.PlanPiece qty
			hdnpara.add(WmsFormatter.getNumFormat(param[i].getPlanPieceQty()));
			//#CM572773
			// Concealment Parameter.CaseITF
			hdnpara.add(param[i].getITF());
			//#CM572774
			// Concealment Parameter.Bundle ITF
			hdnpara.add(param[i].getBundleITF());

			//#CM572775
			// Concealment Parameter is stored in List cell. 
			lst_StoragePlanModify.setValue(LST_HDN, CollectionUtils.getConnectedString(hdnpara));

			//#CM572776
			// CaseStorage Location
			lst_StoragePlanModify.setValue(LST_CASESTRGLCT, param[i].getCaseLocation());
			//#CM572777
			// Packed qty per case
			lst_StoragePlanModify.setValue(
				LST_CASEQTY,
				Formatter.getNumFormat(param[i].getEnteringQty()));
			//#CM572778
			// Host Plan Case qty
			lst_StoragePlanModify.setValue(
				LST_HOSTCASEQTY,
				Formatter.getNumFormat(param[i].getPlanCaseQty()));
			//#CM572779
			// CaseITF
			lst_StoragePlanModify.setValue(LST_CASEITF, param[i].getITF());
			//#CM572780
			// PieceStorage Location
			lst_StoragePlanModify.setValue(LST_PIECESTRGLCT, param[i].getPieceLocation());
			//#CM572781
			// Packed qty per bundle
			lst_StoragePlanModify.setValue(
				LST_BUNDLEQTY,
				Formatter.getNumFormat(param[i].getBundleEnteringQty()));
			//#CM572782
			// Host Plan Piece qty
			lst_StoragePlanModify.setValue(
				LST_HOSTPIECEQTY,
				Formatter.getNumFormat(param[i].getPlanPieceQty()));
			//#CM572783
			// Bundle ITF
			lst_StoragePlanModify.setValue(LST_BUNDLEITF, param[i].getBundleITF());

		}
	}

	//#CM572784
	/**
	 * Method which sets the correction data of Input area in Preset area.  <BR>
	 * <BR>
	 * Outline:Input area is corrected to Preset area Set data.  <BR>
	 * List cell is setCurrentRow(int) before calling this Method Specify the line for the edit Method. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set Concealed Item. <BR>
	 * 		2.The value and Set Concealed Item of Input area in List cell.  <BR>
	 * </DIR>
	 * <BR>
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setListRow() throws Exception
	{
		boolean updateflag = updateCheck();

		//#CM572785
		// Concealment Parameter
		ArrayList hdnpara = (ArrayList)CollectionUtils.getList(lst_StoragePlanModify.getValue(LST_HDN));
		//#CM572786
		// Concealment Parameter.Storage Plan unique key
		hdnpara.set(HDNIDX_UKEY,this.viewState.getString(STORAGEID));
		//#CM572787
		// Concealment Parameter.Last updated date and time
		hdnpara.set(HDNIDX_UPDAY,this.viewState.getString(LASTUPDDATE));
		//#CM572788
		// Concealment Parameter.Case/Piece flag
		hdnpara.set(HDNIDX_CPF, this.viewState.getString(CASEPIECEFLG));
		//#CM572789
		// Modify flag
		if (updateflag)
		{
			hdnpara.set(HDNUPDATEFLAG_IDX, StorageSupportParameter.UPDATEFLAG_YES);
		}
		else
		{
			hdnpara.set(HDNUPDATEFLAG_IDX, StorageSupportParameter.UPDATEFLAG_NO);
		}
		//#CM572790
		// Concealment Parameter is stored in List cell. 
		lst_StoragePlanModify.setValue(LST_HDN, CollectionUtils.getConnectedString(hdnpara));

		//#CM572791
		// CaseStorage Location
		lst_StoragePlanModify.setValue(LST_CASESTRGLCT, txt_CaseStorageLocation.getText());
		//#CM572792
		// Packed qty per case
		lst_StoragePlanModify.setValue(LST_CASEQTY, WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));
		//#CM572793
		// Host Plan Case qty
		lst_StoragePlanModify.setValue(LST_HOSTCASEQTY, WmsFormatter.getNumFormat(txt_HostCasePlanQty.getInt()));
		//#CM572794
		// CaseITF
		lst_StoragePlanModify.setValue(LST_CASEITF, txt_CaseItf.getText());
		//#CM572795
		// PieceStorage Location
		lst_StoragePlanModify.setValue(LST_PIECESTRGLCT, txt_PieseStorageLocation.getText());
		//#CM572796
		// Packed qty per bundle
		lst_StoragePlanModify.setValue(LST_BUNDLEQTY, WmsFormatter.getNumFormat(txt_BundleEntering.getInt()));
		//#CM572797
		// Host Plan Piece qty
		lst_StoragePlanModify.setValue(LST_HOSTPIECEQTY, WmsFormatter.getNumFormat(txt_HostPiesePlanQty.getInt()));
		//#CM572798
		// Bundle ITF
		lst_StoragePlanModify.setValue(LST_BUNDLEITF, txt_BundleItf.getText());

	}

	//#CM572799
	/**
	 * Method which judges whether Input area, Preset area, and there is a change part. <BR>
	 * <BR>
	 * Outline:Return true if Input area is compared with Preset area, and there is a change. <BR>
	 * <BR>
	 * @return Result of check (Changed  : true Unchanged : false) 
	 * @throws Exception It reports on all exceptions. 
	 */
	private boolean updateCheck() throws Exception
	{
		//#CM572800
		// CaseLocation
		if (!txt_CaseStorageLocation
			.getText()
			.equals(CollectionUtils.getString(HDNCASELOCATION_IDX, lst_StoragePlanModify.getValue(LST_HDN))))
		{
			return true;
		}
		//#CM572801
		// PieceLocation
		if (!txt_PieseStorageLocation
			.getText()
			.equals(CollectionUtils.getString(HDNPIECELOCATION_IDX, lst_StoragePlanModify.getValue(LST_HDN))))
		{
			return true;
		}
		//#CM572802
		// Packed qty per case
		if (txt_CaseEntering.getInt() !=
			WmsFormatter.getInt(CollectionUtils.getString(HDNENTERINGQTY_IDX, lst_StoragePlanModify.getValue(0))))
		{
			return true;
		}
		//#CM572803
		// Packed qty per bundle
		if (txt_BundleEntering.getInt() !=
			WmsFormatter.getInt(CollectionUtils.getString(HDNBUNDLEENTERINGQTY_IDX, lst_StoragePlanModify.getValue(0))))
		{
			return true;
		}
		//#CM572804
		// PlanCase qty
		if (txt_HostCasePlanQty.getInt() !=
			WmsFormatter.getInt(CollectionUtils.getString(HDNPLANCASEQTY_IDX, lst_StoragePlanModify.getValue(0))))
		{
			return true;
		}
		//#CM572805
		// PlanPiece qty
		if (txt_HostPiesePlanQty.getInt() !=
			WmsFormatter.getInt(CollectionUtils.getString(HDNPLANPIECEQTY_IDX, lst_StoragePlanModify.getValue(0))))
		{
			return true;
		}
		//#CM572806
		// CaseITF
		if (!txt_CaseItf
			.getText()
			.equals(CollectionUtils.getString(HDNITF_IDX, lst_StoragePlanModify.getValue(LST_HDN))))
		{
			return true;
		}
		//#CM572807
		// Bundle ITF
		if (!txt_BundleItf
			.getText()
			.equals(CollectionUtils.getString(HDNBUNDLEITF_IDX, lst_StoragePlanModify.getValue(LST_HDN))))
		{
			return true;
		}

		return false;
	}
	
	//#CM572808
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
		//#CM572809
		// Plan making Flag
		if (registKind.equals(StorageSupportParameter.REGIST_KIND_NOT_INSTOCK))
		{
			//#CM572810
			// Add Loading Screen
			return DisplayText.getText("LBL-W0454");
		}
		else if (registKind.equals(SystemDefine.REGIST_KIND_INSTOCK))
		{
			//#CM572811
			// For Receiving
			return DisplayText.getText("LBL-W0455");
		}

		return "";
	}
	
	//#CM572812
	// Event handler methods -----------------------------------------
	//#CM572813
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572814
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572815
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Back_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572816
	/**
	 * It is called when the Return button is pressed.<BR>
	 * <BR>
	 * Outline:It changes to Stock Plan correction and Deletion (Essential information Setting) Screen. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		//#CM572817
		// Detailed information Add screen->Essential information Setting Screen
		forward(StoragePlanModifyBusiness.DO_MODIFY);
	}

	//#CM572818
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572819
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

	//#CM572820
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572821
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572822
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572823
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572824
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetStrgPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572825
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Item_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572826
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetItemCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572827
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetItemNm_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572828
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572829
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStorageLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572830
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStorageLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572831
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStorageLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572832
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseStorageLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM572833
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchCaseStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572834
	/** 
	 * CaseIt is called when the retrieval button of Storage Location is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Storage Location list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       CaseStorage Location <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PsearchCaseStrgLct_Click(ActionEvent e) throws Exception
	{
		//#CM572835
		// Set the search condition on the Item retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM572836
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			lbl_JavaSetCnsgnrCd.getText());
		//#CM572837
		// CaseStorage Location
		param.setParameter(
			ListStoragePlanModifyBusiness.CASESTRGLOCATION_KEY,
			txt_CaseStorageLocation.getText());
		//#CM572838
		// Flag for Retrieval
		param.setParameter(
			ListStorageLocationBusiness.SEARCH_CASEPIECE_FLAG_KEY,
			StorageSupportParameter.CASEPIECE_FLAG_CASE);
		//#CM572839
		// Retrieval flag(Plan)
		param.setParameter(
			ListStorageLocationBusiness.SEARCH_STORAGE_LOCATION_KEY,
			StorageSupportParameter.SEARCH_STORAGE_PLAN);
		//#CM572840
		// Status
		param.setParameter(
			ListStorageLocationBusiness.SEARCH_WORKSTATUSLOCATION_KEY,
			StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		//#CM572841
		// Processing Screen -> Result screen
		redirect(
			StoragePlanModifyBusiness.DO_SRCH_STRGLCT,
			param,
			StoragePlanModifyBusiness.DO_SRCH_PROCESS);
	}

	//#CM572842
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PiceStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572843
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieseStorageLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572844
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieseStorageLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572845
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieseStorageLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572846
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieseStorageLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM572847
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchPieceStrgLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572848
	/** 
	 * PieceIt is called when the retrieval button of Storage Location is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Storage Location list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] <BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       PieceStorage Location <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PsearchPieceStrgLct_Click(ActionEvent e) throws Exception
	{
		//#CM572849
		// Set the search condition on the Item retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM572850
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			lbl_JavaSetCnsgnrCd.getText());
		//#CM572851
		// PieceStorage Location
		param.setParameter(
			ListStoragePlanModifyBusiness.PIECESTRGLOCATION_KEY,
			txt_PieseStorageLocation.getText());
		//#CM572852
		// Flag for Retrieval
		param.setParameter(
			ListStorageLocationBusiness.SEARCH_CASEPIECE_FLAG_KEY,
			StorageSupportParameter.CASEPIECE_FLAG_PIECE);
		//#CM572853
		// Retrieval flag(Plan)
		param.setParameter(
			ListStorageLocationBusiness.SEARCH_STORAGE_LOCATION_KEY,
			StorageSupportParameter.SEARCH_STORAGE_PLAN);
		//#CM572854
		// Status
		param.setParameter(
			ListStorageLocationBusiness.SEARCH_WORKSTATUSLOCATION_KEY,
			StorageSupportParameter.STATUS_FLAG_UNSTARTED);
		//#CM572855
		// Processing Screen -> Result screen
		redirect(
			StoragePlanModifyBusiness.DO_SRCH_STRGLCT,
			param,
			StoragePlanModifyBusiness.DO_SRCH_PROCESS);
	}

	//#CM572856
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572857
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572858
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572859
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572860
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_HostCasePlanQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572861
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostCasePlanQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572862
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostCasePlanQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572863
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostCasePlanQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572864
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572865
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572866
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572867
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572868
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_HostPiesePlanQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572869
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostPiesePlanQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572870
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostPiesePlanQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572871
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_HostPiesePlanQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572872
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572873
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572874
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572875
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572876
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM572877
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572878
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572879
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572880
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572881
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM572882
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Input_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572883
	/**
	 * It is called when the input button is pressed.<BR>
	 * <BR>
	 * Outline : Do Input Check of Input area, and update the data of Preset for the correction. <BR>
	 * <BR>
	 * <DIR>
	 *		1.Set the cursor in Case Storage Location.<BR>
	 * 		2.Check Input area input Item. (Mandatory input, Number of characters, Character attribute)<BR>
	 * 		3.Start Schedule.<BR>
	 * 		<DIR>
	 *			[Parameter] *Mandatory input <BR>
	 *			<DIR>
	 * 				Consignor Code <BR>
	 * 				Consignor Name <BR>
	 * 				Item Code <BR>
	 * 				Item Name <BR>
	 * 				Storage plan date <BR>
	 * 				CaseStorage Location <BR>
	 * 				PieceStorage Location <BR>
	 * 				Packed qty per case*2 <BR>
	 * 				Packed qty per bundle <BR>
	 * 				Host Plan Case qty*3 <BR>
	 * 				Host Plan Piece qty*3 <BR>
	 * 				Plan total qty <BR>
	 * 				CaseITF <BR>
	 * 				Bundle ITF <BR>
	 * 				Storage Plan unique key <BR>
	 * 				Last updated date and time <BR>
	 * 				Case/Piece flag <BR>
	 * 				Plan making Flag <BR>
	 * 			<BR>
	 * 			*2 <BR>
	 * 			When entered Host Plan Case qty(>0), Mandatory input <BR>
	 * 			*3 <BR>
	 * 			The input of one or more is Mandatory condition in either Host Plan Case qty or Host Plan Piece qty.  <BR>
	 *			</DIR>
	 *		</DIR>
	 *		When the result of the schedule is true<BR>
	 *		4.Update the data of Preset for the correction in information on Input area. <BR>
	 *		5.Return it based on the color of the line for the modification. <BR>
	 *		6.Set Preset Line No. of ViewState to Default(Initial value:-1). <BR>
	 *		7.Clears the content of Input area.<BR>
	 *		8.Output the Message acquired from the schedule to the screen. <BR>
	 *		When the result of the schedule is false <BR>
	 *		9.Output the error Message acquired from the schedule to the screen. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		//#CM572884
		// Set the cursor in CaseStorage Location. 
		setFocus(txt_CaseStorageLocation);

		//#CM572885
		// Do Pattern matching. 
		//#CM572886
		// CaseStorage Location
		txt_CaseStorageLocation.validate(false);
		//#CM572887
		// PieceStorage Location
		txt_PieseStorageLocation.validate(false);
		//#CM572888
		// Packed qty per case
		txt_CaseEntering.validate(false);
		//#CM572889
		// Packed qty per bundle
		txt_BundleEntering.validate(false);
		//#CM572890
		// Host Plan Case qty
		txt_HostCasePlanQty.validate(false);
		//#CM572891
		// Host Plan Piece qty
		txt_HostPiesePlanQty.validate(false);
		//#CM572892
		// CaseITF
		txt_CaseItf.validate(false);
		//#CM572893
		// Bundle ITF
		txt_BundleItf.validate(false);

		//#CM572894
		// Input character check for eWareNavi
		if (!checkContainNgText())
		{
			return;
		}

		Connection conn = null;

		try
		{
			//#CM572895
			// Set the content of the screen in schedule Parameter. 
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM572896
			// Value that user cannot input (Label item, Concealment Parameter)
			//#CM572897
			// Storage Plan unique key
			param.setStoragePlanUKey(this.viewState.getString(STORAGEID));
			//#CM572898
			// Last updated date and time
			param.setLastUpdateDate(
				WmsFormatter.getTimeStampDate(this.getViewState().getString(LASTUPDDATE)));
			//#CM572899
			// Consignor Code
			param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
			//#CM572900
			// Consignor Name
			param.setConsignorName(lbl_JavaSetCnsgnrNm.getText());
			//#CM572901
			// Storage plan date
			param.setStoragePlanDate(
				WmsFormatter.toParamDate(
			txt_FDate.getText(),
					this.httpRequest.getLocale()));
			//#CM572902
			// Item Code
			param.setItemCode(lbl_JavaSetItemCd.getText());
			//#CM572903
			// Item Name
			param.setItemName(lbl_JavaSetItemNm.getText());

			//#CM572904
			// Value that user can input (Text box)
			//#CM572905
			// CaseStorage Location
			param.setCaseLocation(txt_CaseStorageLocation.getText());
			//#CM572906
			// PieceStorage Location
			param.setPieceLocation(txt_PieseStorageLocation.getText());
			//#CM572907
			// Packed qty per case
			param.setEnteringQty(txt_CaseEntering.getInt());
			//#CM572908
			// Packed qty per bundle
			param.setBundleEnteringQty(txt_BundleEntering.getInt());
			//#CM572909
			// Host Plan Case qty
			param.setPlanCaseQty(txt_HostCasePlanQty.getInt());
			//#CM572910
			// Host Plan Piece qty
			param.setPlanPieceQty(txt_HostPiesePlanQty.getInt());

			//#CM572911
			// Plan total qty calculates the Case/Piece flag distinction way in Storage Plan Operator.
			int enteringQty = param.getEnteringQty();
			int totalQty = param.getPlanCaseQty() * enteringQty + param.getPlanPieceQty();
			//#CM572912
			// Plan total qty
			param.setTotalPlanQty(totalQty);	
			
			//#CM572913
			// CaseITF
			param.setITF(txt_CaseItf.getText());
			//#CM572914
			// Bundle ITF
			param.setBundleITF(txt_BundleItf.getText());
			
			//#CM572915
			// CasePieceFlag
			param.setCasePieceflg(this.viewState.getString(CASEPIECEFLG));

			//#CM572916
			// Plan making Flag
			param.setRegistKbn(this.viewState.getString(REGISTKIND));

			//#CM572917
			// Set Preset information in schedule Parameter (It needs it in Input Check on the schedule side)
			//#CM572918
			// Preset area
			StorageSupportParameter[] listparam = null;

			//#CM572919
			// Null is set if there is no data in Preset. 
			if (lst_StoragePlanModify.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				//#CM572920
				// If data exists, the value is set. 
				listparam = setListParam(this.getViewState().getInt(LINENO));
			}

			//#CM572921
			// Acquire the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM572922
			// Generate instance of schedule class. 
			WmsScheduler schedule = new StoragePlanModifySCH();

			//#CM572923
			// Begin scheduling. 
			//#CM572924
			// In the schedule class, do Input Check and the existence check. 
			if (schedule.check(conn, param, listparam))
			{
				//#CM572925
				// Edit the line for the correction. 
				lst_StoragePlanModify.setCurrentRow(this.getViewState().getInt(LINENO));

				//#CM572926
				// The value of Input area is set in Preset. 
				setListRow();

				//#CM572927
				// Do Initialization of Input area. 
				inputAreaClear();
			}

			//#CM572928
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
				//#CM572929
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

	//#CM572930
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572931
	/**
	 * It is called when the clear button is pressed.<BR>
	 * <BR>
	 * Outline : Clear the Input area.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Return Initial value Item of Input area. <BR>
	 * 		2.Set the cursor in Case Storage Location.<BR>
	 * 		<DIR>
	 *   		Item Name[Initial value]<BR>
	 *   		<DIR>
	 * 				Item Code[None] <BR>
	 * 				Item Name[None] <BR>
	 * 				Packed qty per case[None] <BR>
	 * 				Packed qty per bundle[None] <BR>
	 * 				Inventory Case qty[None] <BR>
	 * 				Inventory Piece qty[None] <BR>
	 * 				Location[None] <BR>
	 * 				Expiry Date[None] <BR>
	 * 	 		</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM572932
		// Empty input data. 
		inputDataClear();
		//#CM572933
		// Set focus in Case Storage Location. 
		setFocus(txt_CaseStorageLocation);
	}

	//#CM572934
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ModifySubmit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572935
	/**
	 * It is called when Add/Modify button is pressed. <BR>
	 * <BR>
	 * Outline:Do Stock Plan correction by information on Preset area.  <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the cursor in Case Storage Location. <BR>
	 * 		2.Display the dialog box, and confirm whether to correct it.<BR>
	 * 		<DIR>
	 * 			[Confirmation Dialog Cancel]<BR>
	 * 			<DIR>
	 * 				Do Nothing.
	 * 			</DIR>
	 *			[Confirmation Dialog OK]<BR>
	 * 			<DIR>
	 * 				1.Start Schedule.<BR>
	 * 				<DIR>
	 * 					[Parameter]<BR>
	 * 					<DIR>
	 * 						Worker Code<BR>
	 * 						Password<BR>
	 * 						Consignor Code<BR>
	 * 						Consignor Name<BR>
	 * 						Storage plan date<BR>
	 * 						Item Code<BR>
	 * 						Item Name<BR>
	 * 						CaseStorage Location(For another Retrieval)<BR>
	 * 						PieceStorage Location(For another Retrieval)<BR>
	 * 						Preset.CaseStorage Location<BR>
	 * 						Preset.PieceStorage Location<BR>
	 * 						Preset.Item Code<BR>
	 * 						Preset.Item Name<BR>
	 * 						Preset.Packed qty per case<BR>
	 * 						Preset.Packed qty per bundle<BR>
	 * 						Preset.Host Plan Case qty<BR>
	 * 						Preset.Host Plan Piece qty<BR>
	 * 						Preset.Plan total qty<BR>
	 * 						Preset.CaseITF<BR>
	 * 						Preset.Bundle ITF<BR>
	 * 						Preset.Storage Plan unique key<BR>
	 * 						Preset.Last updated date and time<BR>
	 * 						Preset.Case , PieceFlag<BR>
	 * 						Preset Line No..<BR>
	 * 						Terminal No.<BR>
	 *					</DIR>
	 * 					<BR>
	 * 					[Return Data]<BR>
	 * 					<DIR>
	 * 						CaseStorage Location <BR>
	 * 						PieceStorage Location <BR>
	 * 						Packed qty per case <BR>
	 * 						Packed qty per bundle <BR>
	 * 						Host Plan Case qty <BR>
	 * 						Host Plan Piece qty <BR>
	 * 						CaseITF <BR>
	 * 						Bundle ITF <BR>
	 * 						Last updated date and time <BR>
	 * 						Preset Line No.. <BR>
	 * 						Storage Plan unique key <BR>
	 * 						Case/Piece flag <BR>
	 *					</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.Display the data of Preset again based on Return Data when the result of the schedule is true. <BR>
	 *				3.When the result of the schedule is true, Clear all Items of Input area. <BR>
	 *				4.If the result of the schedule is true, Set Preset Line No. of ViewState to Default(Initial value:-1). <BR>
	 *				5.If the result of the schedule is true, Return it based on the color of the line for the modification. <BR>
	 *				6.Output the Message acquired from the schedule to the screen. <BR>
	 *			</DIR>
	 *		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_ModifySubmit_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM572936
			// Set the cursor in CaseStorage Location. 
			setFocus(txt_CaseStorageLocation);

			//#CM572937
			// Set in schedule Parameter.
			StorageSupportParameter[] param = null;
			//#CM572938
			// All data of Preset area is set.
			param = setListParam(-1);
			
			if (param == null || param.length <= 0)
			{
				//#CM572939
				// Set Message
				//#CM572940
				// 6003013=There was no target data to modify.
				message.setMsgResourceKey("6003013");
				return;
			}

			//#CM572941
			// Set CaseStorage Location For another Retrieval and PieceStorage Location to the 0th Preset data. 
			param[0].setCaseLocationCondition(
				this.getViewState().getString(ListStoragePlanModifyBusiness.CASESTRGLOCATION_KEY));
			param[0].setPieceLocationCondition(
				this.getViewState().getString(ListStoragePlanModifyBusiness.PIECESTRGLOCATION_KEY));
			
			//#CM572942
			// Plan making Flag
			//#CM572943
			// Use it to judge the repetition check processing of DB. 
			param[0].setRegistKbn(this.getViewState().getString(REGISTKIND));

			//#CM572944
			// Acquire the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM572945
			// Stock Plan correction , DeletionGenerate instance of schedule class. 
			WmsScheduler schedule = new StoragePlanModifySCH();

			//#CM572946
			// Execute the correction processing of the stock. (The display data after correction is returned. )
			StorageSupportParameter[] viewParam =
				(StorageSupportParameter[]) schedule.startSCHgetParams(conn, param);

			//#CM572947
			// Receive Message and end when the error etc. occur. 
			if (viewParam == null)
			{
				//#CM572948
				// Rollback the connection. 
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM572949
			// Commit when Completed normally
			conn.commit();

			//#CM572950
			// Display Preset area again based on Return Data when processing normally Completes. 
			//#CM572951
			// Last Preset area is deleted.  
			lst_StoragePlanModify.clearRow();

			//#CM572952
			// Display Preset area again. 
			setList(viewParam);

			//#CM572953
			// Clear the input area.
			//#CM572954
			// (Clear of Preset Line  No. and the change in the background color are done at the same time. )
			inputAreaClear();

			//#CM572955
			// Set Message from the schedule. 
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			ex.printStackTrace();

			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM572956
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

	//#CM572957
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllDelete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572958
	/**
	 * When All Delete Button is pressed, it is called.  <BR>
	 * <BR>
	 * Outline:All Deletion displays information on Preset.  <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Display the dialog box, and confirm whether to do all Deletion of Preset information.  <BR>
	 * 		<DIR>
	 * 			[Confirmation Dialog Cancel] <BR>
	 * 			<DIR>
	 * 				Do Nothing. <BR>
	 * 			</DIR>
	 * 			[Confirmation Dialog OK] <BR>
	 * 			<DIR>
	 * 				1.Start Schedule. <BR>
	 * 				<DIR>
	 * 					[Parameter]<BR>
	 * 					<DIR>
	 * 						Preset Line No..<BR>
	 * 						Preset.Storage Plan unique key<BR>
	 * 						Last updated date and time<BR>
	 * 						Consignor Code<BR>
	 * 						Storage plan date<BR>
	 * 						Item Code<BR>
	 * 						CaseStorage Location<BR>
	 * 						PieceStorage Location<BR>
	 * 					</DIR>
	 * 				</DIR>
	 * 				2.When the result of the schedule is true, Clear all Items of Input area.  <BR>
	 * 				3.Displays All Deletion information on Preset when result of schedule is true.   <BR>
	 * 				4.Set Preset Line No of ViewState to Default when result of schedule is true(Initial value:-1).  <BR>
	 * 				5.Invalidate correction, all Deletion, the input and Clear Button when the result of the schedule is true.  <BR>
	 * 				6.Output the Message acquired from the schedule to the screen.  <BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_AllDelete_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		Locale locale = this.httpRequest.getLocale();

		try
		{
			//#CM572959
			// Set in schedule Parameter.
			StorageSupportParameter[] listparam = null;

			Vector vecParam = new Vector();

			//#CM572960
			// Set Parameter of Preset line
			for (int i = 1; i < lst_StoragePlanModify.getMaxRows(); i++)
			{
				//#CM572961
				// Specify the line number of List cell to be acquired. 
				lst_StoragePlanModify.setCurrentRow(i);

				//#CM572962
				// Make the Parameter class which stores one line's worth of data. 
				StorageSupportParameter param = new StorageSupportParameter();

				//#CM572963
				// Set Parameter
				//#CM572964
				// Object Line No.
				param.setRowNo(i);
				//#CM572965
				// Storage Plan unique key
				param.setStoragePlanUKey(
					CollectionUtils.getString(0, lst_StoragePlanModify.getValue(LST_HDN)));
				//#CM572966
				// Last updated date and time
				param.setLastUpdateDate(
					WmsFormatter.getTimeStampDate(
						CollectionUtils.getString(1, lst_StoragePlanModify.getValue(LST_HDN))));
				//#CM572967
				// Consignor Code
				param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
				//#CM572968
				// Storage plan date
				param.setStoragePlanDate(
					WmsFormatter.toParamDate(txt_FDate.getText(), locale));
				//#CM572969
				// Item Code
				param.setItemCode(lbl_JavaSetItemCd.getText());
				//#CM572970
				// CaseStorage Location
				param.setCaseLocation(
					this.getViewState().getString(
						ListStoragePlanModifyBusiness.CASESTRGLOCATION_KEY));
				//#CM572971
				// PieceStorage Location
				param.setPieceLocation(
					this.getViewState().getString(
						ListStoragePlanModifyBusiness.PIECESTRGLOCATION_KEY));

				//#CM572972
				// Set data to the array by every line. 
				vecParam.addElement(param);
			}

			if (vecParam.size() > 0)
			{
				//#CM572973
				// Set the value when Preset line exists. 
				listparam = new StorageSupportParameter[vecParam.size()];
				vecParam.copyInto(listparam);
			}

			//#CM572974
			// Stock Plan correction , DeletionGenerate instance of schedule class. 
			WmsScheduler schedule = new StoragePlanModifySCH();

			//#CM572975
			// The connection is acquired. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM572976
			// The Deletion processing is executed. 
			if (schedule.startSCH(conn, listparam))
			{
				//#CM572977
				// Commit if processing completes normally.
				conn.commit();

				//#CM572978
				// Deletes all Preset lines. 
				lst_StoragePlanModify.clearRow();

				//#CM572979
				// Clear the input area.
				inputAreaClear();

				//#CM572980
				// Nullify Button. 
				//#CM572981
				// Add/Modify button
				btn_ModifySubmit.setEnabled(false);
				//#CM572982
				// All Delete button
				btn_AllDelete.setEnabled(false);

				//#CM572983
				// Set Message
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				//#CM572984
				// Rollback when terminating abnormally
				conn.rollback();
				//#CM572985
				// Set Message
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
				//#CM572986
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

	//#CM572987
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanModify_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM572988
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanModify_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM572989
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanModify_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM572990
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanModify_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM572991
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanModify_Server(ActionEvent e) throws Exception
	{
	}

	//#CM572992
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StoragePlanModify_Change(ActionEvent e) throws Exception
	{
	}

	//#CM572993
	/**
	 * It is called when the deletion and the modification button are pressed. <BR>
	 * <BR>
	 * Deletion button Outline:Delete pertinent data of Preset.  <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Display the dialog box and confirm whether to delete Preset information.  <BR>
	 * 		<DIR>
	 * 			[Confirmation Dialog Cancel] <BR>
	 * 			<DIR>
	 * 				Do Nothing. <BR>
	 * 			</DIR>
	 * 			[Confirmation Dialog OK] <BR>
	 * 			<DIR>
	 * 				1.Start Schedule. <BR>
	 * 				<DIR>
	 * 					[Parameter]<BR>
	 * 					<DIR>
	 * 						Preset Line No..<BR>
	 * 						Preset.Storage Plan unique key<BR>
	 * 						Last updated date and time<BR>
	 * 						Case , PieceFlag<BR>
	 * 						Consignor Code<BR>
	 * 						Storage plan date<BR>
	 * 						Item Code<BR>
	 * 						CaseStorage Location<BR>
	 * 						PieceStorage Location<BR>
	 * 					</DIR>
	 * 				</DIR>
	 * 				2.When the result of the schedule is true, Clear all Items of Input area.  <BR>
	 * 				3.Delete pertinent data of Preset when result of schedule is true.   <BR>
	 * 				4.Set Preset Line No of ViewState to Default when result of schedule is true(Initial value:-1).  <BR>
	 * 				5.Invalidate the input and ClearButton when the result of the schedule is true.  <BR>
	 * 				6.Invalidate correction and All Delete button when Preset information does not exist.  <BR>
	 * 				7.Output the Message acquired from the schedule to the screen.  <BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * Modification button Outline:Make pertinent data of Preset modification Status.  <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Display Preset line information that is Selected in upper Input area.  <BR>
	 * 		2.Make Preset line that is Selected thin yellow.  <BR>
	 * 		3.Set the line for the correction to ViewState.Preset Line No.  <BR>
	 * 		4.Make Input Button and ClearButton effective.  <BR>
	 * 		5.Set the cursor in Case Storage Location.  <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void lst_StoragePlanModify_Click(ActionEvent e) throws Exception
	{
		//#CM572994
		// Click Delete button
		if (lst_StoragePlanModify.getActiveCol() == LST_DELBTN)
		{
			Connection conn = null;

			try
			{
				//#CM572995
				// Treat the data of the line to which Delete Button is pressed. 
				lst_StoragePlanModify.setCurrentRow(lst_StoragePlanModify.getActiveRow());

				//#CM572996
				// Set in schedule Parameter.
				StorageSupportParameter[] param = new StorageSupportParameter[1];
				param[0] = new StorageSupportParameter();

				//#CM572997
				// Storage Plan unique key
				param[0].setStoragePlanUKey(
					CollectionUtils.getString(
						HDNIDX_UKEY,
						lst_StoragePlanModify.getValue(LST_HDN)));
				//#CM572998
				// Last update time
				param[0].setLastUpdateDate(
					WmsFormatter.getTimeStampDate(
						CollectionUtils.getString(
							HDNIDX_UPDAY,
							lst_StoragePlanModify.getValue(LST_HDN))));
				//#CM572999
				// Case/Piece flag
				param[0].setCasePieceflg(
					CollectionUtils.getString(HDNIDX_CPF, lst_StoragePlanModify.getValue(LST_HDN)));
				//#CM573000
				// Consignor Code
				param[0].setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
				//#CM573001
				// Storage plan date
				param[0].setStoragePlanDate(
					WmsFormatter.toParamDate(
				txt_FDate.getText(),
						this.httpRequest.getLocale()));
				//#CM573002
				// Item Code
				param[0].setItemCode(lbl_JavaSetItemCd.getText());
				//#CM573003
				// CaseStorage Location
				param[0].setCaseLocation(
					this.getViewState().getString(
						ListStoragePlanModifyBusiness.CASESTRGLOCATION_KEY));
				//#CM573004
				// PieceStorage Location
				param[0].setPieceLocation(
					this.getViewState().getString(
						ListStoragePlanModifyBusiness.PIECESTRGLOCATION_KEY));
				//#CM573005
				// Preset Line No..
				param[0].setRowNo(lst_StoragePlanModify.getCurrentRow());
				
				//#CM573006
				// Stock Plan correction , DeletionGenerate instance of schedule class. 
				WmsScheduler schedule = new StoragePlanModifySCH();

				//#CM573007
				// Acquire the connection. 
				conn = ConnectionManager.getConnection(DATASOURCE_NAME);

				//#CM573008
				// The Deletion processing is executed. 
				if (schedule.startSCH(conn, param))
				{
					//#CM573009
					// Commit if processing completes normally.
					conn.commit();

					//#CM573010
					// Deleted Preset line is deleted. 
					lst_StoragePlanModify.removeRow(lst_StoragePlanModify.getActiveRow());

					//#CM573011
					// Input areaClear
					inputAreaClear();

					//#CM573012
					// Nullify correction and all Delete Button when Preset information does not exist. 
					if (lst_StoragePlanModify.getMaxRows() == 1)
					{
						//#CM573013
						// Nullify Button. 
						//#CM573014
						// Add/Modify button
						btn_ModifySubmit.setEnabled(false);
						//#CM573015
						// All Delete button
						btn_AllDelete.setEnabled(false);
					}

					//#CM573016
					// Set Message
					message.setMsgResourceKey(schedule.getMessage());
				}
				else
				{
					//#CM573017
					// Rollback when terminating abnormally
					conn.rollback();
					//#CM573018
					// Set Message
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
					//#CM573019
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
		//#CM573020
		// When Modify button is clicked 
		else if (lst_StoragePlanModify.getActiveCol() == LST_MODBTN)
		{
			//#CM573021
			// Treat the data of the line to which Correction Button is pushed. 
			lst_StoragePlanModify.setCurrentRow(lst_StoragePlanModify.getActiveRow());

			//#CM573022
			// Progress the content of Preset area to Input area. 
			//#CM573023
			// CaseStorage Location
			txt_CaseStorageLocation.setText(lst_StoragePlanModify.getValue(LST_CASESTRGLCT));
			//#CM573024
			// PieceStorage Location
			txt_PieseStorageLocation.setText(lst_StoragePlanModify.getValue(LST_PIECESTRGLCT));
			//#CM573025
			// Packed qty per case
			txt_CaseEntering.setText(lst_StoragePlanModify.getValue(LST_CASEQTY));
			//#CM573026
			// Packed qty per bundle
			txt_BundleEntering.setText(lst_StoragePlanModify.getValue(LST_BUNDLEQTY));
			//#CM573027
			// Host Plan Case qty
			txt_HostCasePlanQty.setText(lst_StoragePlanModify.getValue(LST_HOSTCASEQTY));
			//#CM573028
			// Host Plan Piece qty
			txt_HostPiesePlanQty.setText(lst_StoragePlanModify.getValue(LST_HOSTPIECEQTY));
			//#CM573029
			// CaseITF
			txt_CaseItf.setText(lst_StoragePlanModify.getValue(LST_CASEITF));
			//#CM573030
			// Bundle ITF
			txt_BundleItf.setText(lst_StoragePlanModify.getValue(LST_BUNDLEITF));

			//#CM573031
			// Set Concealment Parameter in ViewState. 
			//#CM573032
			// Correction Selection line
			this.viewState.setInt(LINENO, lst_StoragePlanModify.getActiveRow());
			//#CM573033
			// Storage Plan unique key
			this.viewState.setString(
				STORAGEID,
				CollectionUtils.getString(0, lst_StoragePlanModify.getValue(LST_HDN)));
			//#CM573034
			// Last updated date and time
			this.viewState.setString(
				LASTUPDDATE,
				CollectionUtils.getString(1, lst_StoragePlanModify.getValue(LST_HDN)));
			//#CM573035
			// CasePieceFlag
			this.viewState.setString(
				CASEPIECEFLG,
				CollectionUtils.getString(2, lst_StoragePlanModify.getValue(LST_HDN)));
			//#CM573036
			// Change the object correction line to yellow. 
			lst_StoragePlanModify.setHighlight(lst_StoragePlanModify.getActiveRow());

			//#CM573037
			// Make Button in Input area effective. 
			//#CM573038
			// Input Button
			btn_Input.setEnabled(true);
			//#CM573039
			// ClearButton
			btn_Clear.setEnabled(true);

			//#CM573040
			// Set the cursor in CaseStorage Location. 
			setFocus(txt_CaseStorageLocation);
		}
	}
	
	//#CM573041
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_BscDtlMdfyDlt_Click(ActionEvent e) throws Exception
	{
	}

	//#CM573042
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573043
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573044
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573045
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Regist_Kind_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573046
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetRegistKind_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM573047
//end of class
