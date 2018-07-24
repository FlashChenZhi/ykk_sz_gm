// $Id: StockMoveMaintenanceBusiness.java,v 1.3 2006/12/07 08:57:19 suresh Exp $

//#CM571477
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.stockmovemaintenance;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.schedule.StockMoveMaintenanceSCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM571478
/**
 * Designer : Y.Hirata <BR>
 * Maker : Y.Hirata <BR>
 * <BR>
 * The Stock Movement maintenance screen class. <BR>
 * Hand over Parameter to the schedule which does the Stock Movement maintenance processing. <BR>
 * Moreover, do Commit  of Transaction and Rollback on this screen. <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Display button is pressed(<CODE>btn_View_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 *  	The content input from the screen is set in Parameter, and the schedule retrieves data for the display based on the Parameter. <BR>
 *  	Receive the retrieval result from the schedule, and output to Preset area. <BR>
 *  	<BR>
 *  	[Parameter] *Mandatory input<BR>
 *  	<BR>
 *  	<DIR>
 *   		Worker Code*<BR>
 *   		Password*<BR>
 *   		Consignor Code*<BR>
 *   		Item Code<BR>
 *   		The order of display<BR>
 *   		Movement Completed data display Flag<BR>
 *   		Movement Work list print Flag<BR>
 *  	</DIR>
 *  	<BR>
 *  	[Return Data]<BR>
 *  	<DIR>
 *   		Consignor Code <BR>
 *   		Consignor Name <BR>
 *   		Item Code <BR>
 *   		Item Name <BR>
 *   		Location before movement <BR>
 *   		Location after movement <BR>
 *   		Packed qty per case <BR>
 *   		PlanCase qty <BR>
 *   		PlanPiece qty <BR>
 *   		Movement Case qty <BR>
 *   		Movement Piece qty <BR>
 *   		Status flag <BR>
 *   		Expiry Date <BR>
 *   		Picking Worker Code <BR>
 *   		Picking Worker Name <BR>
 *   		Work No. <BR>
 *   		Last updated date and time <BR>
 *  	</DIR>
 * </DIR> 
 * <BR>
 * 2Processing when Add/Modify button is pressed (<CODE>btn_ModifySubmit_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 * 		The content input from Preset area is set in Parameter, and the schedule maintains it based on the Parameter. <BR>
 *  	Receive true when processing normally Completes and receive false when the schedule does not do Complete because of the condition error etc. <BR>
 *  	Message acquired from the schedule is output to the screen as a result of the schedule, and Display Preset area again.  <BR>
 *  	<BR>
 *  	[Parameter] *Mandatory input<BR>
 *  	<BR>
 *  	<DIR>
 *   		Worker Code* <BR>
 *   		Password* <BR>
 *   		Consignor Code* <BR>
 *   		Item Code <BR>
 *   		The order of display* <BR>
 *   		Movement Completed data display Flag* <BR>
 *   		Movement Work list print Flag* <BR>
 *   		Movement CancelFlag* <BR>
 *   		Location before movement <BR>
 *   		Location after movement* <BR>
 *   		Movement Case qty* <BR>
 *   		Movement Piece qty* <BR>
 *   		Status flag* <BR>
 *   		Preset Line No..* <BR>
 *   		Work No.* <BR>
 *   		Last updated date and time* <BR>
 *  	</DIR>
 *  	<BR>
 *  	[Return Data]<BR>
 *  	<DIR>
 *   		Consignor Code <BR>
 *   		Consignor Name <BR>
 *   		Item Code <BR>
 *   		Item Name <BR>
 *   		Location before movement <BR>
 *   		Location after movement <BR>
 *   		Packed qty per case <BR>
 *   		PlanCase qty <BR>
 *   		PlanPiece qty <BR>
 *   		Movement Case qty <BR>
 *   		Movement Piece qty <BR>
 *   		Status flag <BR>
 *   		Expiry Date <BR>
 *   		Picking Worker Code <BR>
 *   		Picking Worker Name <BR>
 *   		Work No. <BR>
 *   		Last updated date and time <BR>
 *  	</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/12/07 08:57:19 $
 * @author  $Author: suresh $
 */
public class StockMoveMaintenanceBusiness extends StockMoveMaintenance implements WMSConstants
{
	//#CM571479
	// Class fields --------------------------------------------------
	
	//#CM571480
	// List cell column index
	//#CM571481
	/**
	 * Concealed Item's List cell number
	 */
	private static final int LSTHDN = 0;
	
	//#CM571482
	/**
	 * Cancel's List cell number
	 */
	private static final int LSTCANCEL = 1;
	
	//#CM571483
	/**
	 * Item Code's List cell number
	 */
	private static final int LSTITEMCODE = 2;
	
	//#CM571484
	/**
	 * Location before movement's List cell number
	 */
	private static final int LSTLOCATIONFROM = 3;
	
	//#CM571485
	/**
	 * Location after movement's List cell number
	 */
	private static final int LSTLOCATIONTO = 4;
	
	//#CM571486
	/**
	 * Packed qty per case's List cell number
	 */
	private static final int LSTENTQTY = 5;
	
	//#CM571487
	/**
	 * PlanCase qty's List cell number
	 */
	private static final int LSTPLANCASEQTY = 6;
	
	//#CM571488
	/**
	 * Movement Case qty's List cell number
	 */
	private static final int LSTMOVECASEQTY = 7;
	
	//#CM571489
	/**
	 * Status's List cell number
	 */
	private static final int LSTSTATUS = 8;
	
	//#CM571490
	/**
	 * Expiry Date's List cell number
	 */
	private static final int LSTUSEBYDATE = 9;

	//#CM571491
	/**
	 * Picking Worker Code's List cell number
	 */
	private static final int LSTWORKERCODE = 10;
	
	//#CM571492
	/**
	 * Item Name's List cell number
	 */
	private static final int LSTITEMNAME = 11;
	
	//#CM571493
	/**
	 * PlanPiece qty's List cell number
	 */
	private static final int LSTPLANPIECEQTY = 12;
	
	//#CM571494
	/**
	 * Movement Piece qty's List cell number
	 */
	private static final int LSTMOVEPIECEQTY = 13;
	
	//#CM571495
	/**
	 * Picking Worker Name's List cell number
	 */
	private static final int LSTWORKERNAME = 14;
	
	
	//#CM571496
	// Concealment Parameter
	//#CM571497
	/**
	 * For HIDDEN item : Work No.(Number of array elements)
	 */
	private static final int HDNARYJOBNO = 0;
	
	//#CM571498
	/**
	 * For HIDDEN item : Last updated date and time(Number of array elements)
	 */
	private static final int HDNARYLASTUPDATEDATE = 1;
	
	//#CM571499
	/**
	 * For HIDDEN item : Location after movement
	 */
	private static final int HDNLOCATIONTO = 2;
	
	//#CM571500
	/**
	 * For HIDDEN item : Movement Case qty
	 */
	private static final int HDNMOVECASEQTY = 3;
	
	//#CM571501
	/**
	 * For HIDDEN item : Movement Piece qty
	 */
	private static final int HDNMOVEPIECEQTY = 4;
	
	//#CM571502
	/**
	 * For HIDDEN item : Status
	 */
	private static final int HDNSTATUS = 5;
	
	//#CM571503
	/**
	 * For HIDDEN item : Consolidating Work No.
	 */
	private static final int HDNCOLLECTJOBNO = 6;
	
	//#CM571504
	/**
	 * For HIDDEN item : Work No.
	 */
	private static final int HDNJOBNO = 7;
	
	//#CM571505
	// PresetWork Status
	//#CM571506
	/**
	 * PresetWork Status : Waiting for Storage
	 */
	private static final String STATUSUNSTART = "0";
	
	//#CM571507
	/**
	 * PresetWork Status : During Storage
	 */
	private static final String STATUSWORKING = "1";
	
	//#CM571508
	/**
	 * PresetWork Status : Movement Completed
	 */
	private static final String STATUSCOMP = "2";
	
	
	//#CM571509
	// For ViewState
	//#CM571510
	/**
	 * For ViewState : Consignor Code
	 */
	private static final String VSTCONSIGNOR = "CONSIGNOR_CODE";
	
	//#CM571511
	/**
	 * For ViewState : Item Code
	 */
	private static final String VSTITEMCODE = "ITEM_CODE";
	
	//#CM571512
	/**
	 * For ViewState : The order of display
	 */
	private static final String VSTSORT = "SORT";
	
	//#CM571513
	/**
	 * For ViewState : Display of Movement Completed data
	 */
	private static final String VSTDISPCOMP = "DISPLAY_COMPLETE";
	
	//#CM571514
	// Class variables -----------------------------------------------

	//#CM571515
	// Class method --------------------------------------------------

	//#CM571516
	// Constructors --------------------------------------------------

	//#CM571517
	// Public methods ------------------------------------------------

	//#CM571518
	/**
	 * It is called when initial data of the screen is displayed.<BR>
	 * <BR>
	 * Outline : Display initial data in the screen. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Initialize the input area. <BR>
	 * 		2.Invalidate all buttons of Preset area. <BR>
	 * 		<BR>
	 * 		<DIR>
	 *  		Item Name[Initial value]
	 *  		<DIR>
	 *  			Worker Code[None]<BR>
	 *  			Password[None]<BR>
	 *  			Consignor Code[Display only in case of one in Consignor retrieval result is available]<BR>
	 *  			Item Code[None]<BR>
	 *  			The order of display[The order of Item Code]<BR>
	 *  			Display the Movement Completed data. [false]<BR>
	 *  			Print the Movement Work list[false]<BR>
	 *  		</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		
		//#CM571519
		// Set Initial value in each input field. 
		txt_WorkerCode.setText("");
		txt_Password.setText("");
		txt_ConsignorCode.setText(getConsignorCode());
		txt_ItemCode.setText("");
		rdo_ItemCode.setChecked(true);
		rdo_FromMoveLocation.setChecked(false);
		chk_CommonUseCompData.setChecked(false);
		chk_CommonUseWorkList.setChecked(false);
		
		
		//#CM571520
		// Make Add/Modify button, Movement cancellation all Selection, Movement cancellation Unselect all, and Clear list Button unuseable. 
		btn_ModifySubmit.setEnabled(false);
		btn_MovCancelAllSlt.setEnabled(false);
		btn_MovCancelAllSltOff.setEnabled(false);
		btn_ListClear.setEnabled(false);

	}
	
	//#CM571521
	/**
	 * It is called before the call of each control event. <BR>
	 * <BR>
	 * Outline:Display the Confirmation dialog.<BR>
	 * Or, Set the cursor in Worker Code. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM571522
			//Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM571523
			//Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM571524
			//Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
		//#CM571525
		// MSG-W0008=Do you commit modification?
		btn_ModifySubmit.setBeforeConfirm("MSG-W0033");

		//#CM571526
		// MSG-W0012 = Do you clear the list?
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		//#CM571527
		// Set the cursor in Worker Code. 		
		setFocus(txt_WorkerCode);
	}
	
	//#CM571528
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
		//#CM571529
		// Parameter selected from list box is acquired. 
		//#CM571530
		// Consignor Code
		String consignorcode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM571531
		// Item Code
		String itemcode = param.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);


		//#CM571532
		// Set the value when it is not empty. 
		//#CM571533
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}

		//#CM571534
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}

	}
	
	//#CM571535
	// Package methods -----------------------------------------------

	//#CM571536
	// Protected methods ---------------------------------------------
	//#CM571537
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

		lst_SStockMoveMaintenance.setCurrentRow(rowNo);

		if(!checker.checkContainNgText(
				lst_SStockMoveMaintenance.getValue(LSTLOCATIONTO) ,
				rowNo,
				lst_SStockMoveMaintenance.getListCellColumn(LSTLOCATIONTO).getResourceKey() )
				)
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}

		return true;
		
	}

	//#CM571538
	// Private methods -----------------------------------------------
	//#CM571539
	/**
	 * Method to acquire Consignor from the schedule.  <BR>
	 * <BR>
	 * Outline : Set Consignor Code acquired from the schedule in Input area.  <BR>
	 * When the schedule result is null, this Method returns null. <BR>
	 * <BR>
	 * @return Consignor Code.
	 * @throws Exception It reports on all exceptions. 
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null ;
		
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM571540
			// Acquire Consignor Code from the schedule. 
			WmsScheduler schedule = new StockMoveMaintenanceSCH();
			param = (StorageSupportParameter)schedule.initFind(conn, param);
			
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
				//#CM571541
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


	//#CM571542
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
	 * 				0.Concealed Item<BR>
	 * 				1.Cancel<BR>
	 * 				2.Item Code<BR>
	 * 				3.Location before movement<BR>
	 * 				4.Location after movement<BR>
	 * 				5.Packed qty per case<BR>
	 * 				6.PlanCase qty<BR>
	 * 				7.Movement Case qty<BR>
	 * 				8.Status<BR>
	 * 				9.Expiry Date<BR>
	 * 				10.Picking Worker Code<BR>
	 * 				11.Item Name<BR>
	 * 				12.PlanPiece qty<BR>
	 * 				13.Movement Piece qty<BR>
	 * 				14.Picking Worker Name<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param listParams Parameter[] Parameter array which stores display data. 
	 * @throws Exception It reports on all exceptions.  
	 */
	private void setList(Parameter[] listParams) throws Exception
	{
		//#CM571543
		// Delete all rows
		lst_SStockMoveMaintenance.clearRow();

		StorageSupportParameter param = (StorageSupportParameter) listParams[0];

		//#CM571544
		// Consignor Code(Only for Reading)
		txt_SRConsignorCode.setText(param.getConsignorCode());
		//#CM571545
		// Consignor Name(Only for Reading)
		txt_SRConsignorName.setText(param.getConsignorName());

		StorageSupportParameter[] viewParam = (StorageSupportParameter[]) listParams;

		//#CM571546
		// Description acquisition for balloon help
		//#CM571547
		// Item Name
		String itemName = DisplayText.getText("LBL-W0103");
		//#CM571548
		// Status
		String status = DisplayText.getText("LBL-W0229");
		//#CM571549
		// Expiry Date
		String useByDate = DisplayText.getText("LBL-W0270");
		//#CM571550
		// Worker code
		String workerCode = DisplayText.getText("LBL-W0400");
		//#CM571551
		// Worker Name
		String workerName = DisplayText.getText("LBL-W0185");
		
		for (int i = 0; i < viewParam.length; i++)
		{
			//#CM571552
			// Add row
			lst_SStockMoveMaintenance.setCurrentRow(i + 1);
			lst_SStockMoveMaintenance.addRow();
			
			//#CM571553
			// The value is set in the list cell. 
			//#CM571554
			// Cancel
			lst_SStockMoveMaintenance.setChecked(LSTCANCEL,false);
			//#CM571555
			// Item Code
			lst_SStockMoveMaintenance.setValue(LSTITEMCODE,viewParam[i].getItemCode());
			//#CM571556
			// Item Name
			lst_SStockMoveMaintenance.setValue(LSTITEMNAME,viewParam[i].getItemName());
			//#CM571557
			// Location before movement
			lst_SStockMoveMaintenance.setValue(LSTLOCATIONFROM,viewParam[i].getSourceLocationNo());
			//#CM571558
			// Location after movement
			lst_SStockMoveMaintenance.setValue(LSTLOCATIONTO,viewParam[i].getDestLocationNo());
			//#CM571559
			//  Case Packed qty
			lst_SStockMoveMaintenance.setValue(LSTENTQTY,WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));
			//#CM571560
			// Plan Case qty
			lst_SStockMoveMaintenance.setValue(LSTPLANCASEQTY,WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
			//#CM571561
			// Plan Piece qty
			lst_SStockMoveMaintenance.setValue(LSTPLANPIECEQTY,WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
			//#CM571562
			// Movement  Case qty
			lst_SStockMoveMaintenance.setValue(LSTMOVECASEQTY,WmsFormatter.getNumFormat(viewParam[i].getResultCaseQty()));
			//#CM571563
			// Movement  Piece qty
			lst_SStockMoveMaintenance.setValue(LSTMOVEPIECEQTY,WmsFormatter.getNumFormat(viewParam[i].getResultPieceQty()));
			//#CM571564
			// Status
			if(viewParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED))
			{
				//#CM571565
				// Waiting for Storage
				lst_SStockMoveMaintenance.setValue(LSTSTATUS, STATUSUNSTART);
			}
			else if(viewParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_WORKING))
			{
				//#CM571566
				// During Storage
				lst_SStockMoveMaintenance.setValue(LSTSTATUS, STATUSWORKING);
			}
			else if(viewParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
			{
				//#CM571567
				// Movement Completed
				lst_SStockMoveMaintenance.setValue(LSTSTATUS, STATUSCOMP);
			}
			
			//#CM571568
			// Expiry Date
			lst_SStockMoveMaintenance.setValue(LSTUSEBYDATE,viewParam[i].getUseByDate());
			//#CM571569
			// Picking Worker code
			lst_SStockMoveMaintenance.setValue(LSTWORKERCODE,viewParam[i].getRetrievalWorkerCode());
			//#CM571570
			// Picking Worker Name
			lst_SStockMoveMaintenance.setValue(LSTWORKERNAME,viewParam[i].getRetrievalWorkerName());



			//#CM571571
			// Concealed Item
			setHidden(viewParam[i]);


			//#CM571572
			//Set the tool tip
			ToolTipHelper toolTip = new ToolTipHelper();
			//#CM571573
			// Item Name
			toolTip.add(itemName,viewParam[i].getItemName());
			//#CM571574
			// Status
			if(viewParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED))
			{
				//#CM571575
				// Waiting for Storage
				toolTip.add(status, DisplayUtil.getMoveStatusValue(Movement.STATUSFLAG_UNSTART));
			}
			else if(viewParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_WORKING))
			{
				//#CM571576
				// During Storage
				toolTip.add(status, DisplayUtil.getMoveStatusValue(Movement.STATUSFLAG_NOWWORKING));
			}
			else if(viewParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
			{
				//#CM571577
				// Movement Completed
				toolTip.add(status, DisplayUtil.getMoveStatusValue(Movement.STATUSFLAG_COMPLETION));
			}
			//#CM571578
			// Expiry Date
			toolTip.add(useByDate, viewParam[i].getUseByDate());
			//#CM571579
			// Picking Worker Code
			toolTip.add(workerCode,viewParam[i].getRetrievalWorkerCode());
			//#CM571580
			// Picking Worker Name 
			toolTip.add(workerName,viewParam[i].getRetrievalWorkerName());

			lst_SStockMoveMaintenance.setToolTip(i+1, toolTip.getText());
			
			
		}

		//#CM571581
		// Make Add/Modify button, Movement Cancel all Selection, Movement CancelUnselect all, and Clear list Button unuseable. 
		btn_ModifySubmit.setEnabled(true);
		btn_MovCancelAllSlt.setEnabled(true);
		btn_MovCancelAllSltOff.setEnabled(true);
		btn_ListClear.setEnabled(true);

	}

	//#CM571582
	/** 
	 * Check whether input information on the list cell is an update object. <BR>
	 * <BR>
	 * Outline : the data of the list cell returns True when updating and when it is not so, returns false.  <BR>
	 * <BR> 
	 * @return Check result (When updating it : True When not updating it : false) 
	 * @throws Exception It reports on all exceptions. 
	 */
	private boolean isChangeData() throws Exception
	{
		
		//#CM571583
		// Acquire HIDDEN Item.
		String hidden = lst_SStockMoveMaintenance.getValue(LSTHDN);
		String orgLocationTo = CollectionUtils.getString(HDNLOCATIONTO, hidden);
		String orgMoveCaseQty = CollectionUtils.getString(HDNMOVECASEQTY, hidden);
		String orgMovePieceQty = CollectionUtils.getString(HDNMOVEPIECEQTY, hidden);
		String orgStatus = CollectionUtils.getString(HDNSTATUS, hidden);
		
		
		//#CM571584
		// Do not correct it when former Status is [Movement Completed]. 
		if(orgStatus.equals(STATUSCOMP))
		{
			return false;
		}
		

		//#CM571585
		// Do not correct it when nothing has not been changed to the input text. 
		//#CM571586
		// Location after movement
		if(!lst_SStockMoveMaintenance.getValue(LSTLOCATIONTO).equals(orgLocationTo))
		{
			return true;
		}
		//#CM571587
		// Movement  Case qty
		else if(!lst_SStockMoveMaintenance.getValue(LSTMOVECASEQTY).equals(orgMoveCaseQty))
		{
			return true;
		}
		//#CM571588
		// Movement  Piece qty
		else if(!lst_SStockMoveMaintenance.getValue(LSTMOVEPIECEQTY).equals(orgMovePieceQty))
		{
			return true;
		}
		//#CM571589
		// Status
		else if(!lst_SStockMoveMaintenance.getValue(LSTSTATUS).equals(orgStatus))
		{
			return true;
		}
		//#CM571590
		// Cancel
		else if(lst_SStockMoveMaintenance.getChecked(LSTCANCEL))
		{
			return true;
		}
		//#CM571591
		// When nothing has been changed
		else
		{
			return false;
		}
		

	}	
	
	//#CM571592
	/**
	 * Connect HIDDEN Item with one character string, and set it in List cell. <BR>
	 * <BR>
	 * Outline : Acquire Concealed Item, and convert it into the character string. <BR>
	 * Set the converted character string in List cell. <BR>
	 * <BR>
	 * <DIR>
	 * 		The Item order list of HIDDEN <BR>
	 * 		<DIR>
	 * 			0.Work No.(Number of array elements) <BR>
	 * 			1.Last updated date and time(Number of array elements) <BR>
	 * 			2.Location after movement <BR>
	 * 			3.Movement Case qty <BR>
	 * 			4.Movement Piece qty <BR>
	 * 			5.Status <BR>
	 * 			6.Consolidating Work No. <BR>
	 * 			7.Work No. <BR>
	 * 			8.Last updated date and time <BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param listParams Parameter Parameter including HIDDEN Item.
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setHidden(Parameter listParams) throws Exception
	{
		StorageSupportParameter viewParam = (StorageSupportParameter) listParams;
		
		//#CM571593
		// Making of Concealed Item character string
		ArrayList hiddenList = new ArrayList();
		
		//#CM571594
		// Concealed Item
		//#CM571595
		// Work No. (Number of elements) 
		hiddenList.add(HDNARYJOBNO, Integer.toString(viewParam.getJobNoList().size()));
		
		//#CM571596
		// Last updated date and time (Number of elements) 
		hiddenList.add(HDNARYLASTUPDATEDATE, Integer.toString(viewParam.getLastUpdateDateList().size()));
		
		//#CM571597
		// Location after movement
		hiddenList.add(HDNLOCATIONTO, viewParam.getDestLocationNo());
		
		//#CM571598
		// Movement  Case qty
		hiddenList.add(HDNMOVECASEQTY, Formatter.getNumFormat(viewParam.getResultCaseQty()));
		
		//#CM571599
		// Movement  Piece qty
		hiddenList.add(HDNMOVEPIECEQTY, Formatter.getNumFormat(viewParam.getResultPieceQty()));
		
		//#CM571600
		// Status
		if(viewParam.getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED))
		{
			//#CM571601
			// Waiting for Storage
			hiddenList.add(HDNSTATUS, STATUSUNSTART);
		}
		else if(viewParam.getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_WORKING))
		{
			//#CM571602
			// During Storage
			hiddenList.add(HDNSTATUS, STATUSWORKING);	
		}
		else if(viewParam.getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
		{
			//#CM571603
			// Movement Completed
			hiddenList.add(HDNSTATUS, STATUSCOMP);
		}
		
		//#CM571604
		// Consolidating Work No.
		hiddenList.add(HDNCOLLECTJOBNO, viewParam.getCollectJobNo());
		
		
		//#CM571605
		// Work No.
		Vector vecJobNo = viewParam.getJobNoList();
		for(int i = 0; i < vecJobNo.size(); i++)
		{
			hiddenList.add(vecJobNo.get(i));
		}
		
		
		//#CM571606
		// Last updated date and time 	
		Vector vecLastDate = viewParam.getLastUpdateDateList();
		for(int i = 0; i < vecLastDate.size(); i++)
		{
			hiddenList.add(WmsFormatter.getTimeStampString((Date)vecLastDate.get(i)));
		}
		
		//#CM571607
		// The value is set in Concealed Item. 
		lst_SStockMoveMaintenance.setValue(LSTHDN,CollectionUtils.getConnectedString(hiddenList));
		
	}

	//#CM571608
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
		//#CM571609
		// Clear the Preset area.
		//#CM571610
		// Make Add/Modify button, Movement cancellation all Selection, Movement cancellation Unselect all, and Clear list Button unuseable. 
		btn_ModifySubmit.setEnabled(false);
		btn_MovCancelAllSlt.setEnabled(false);
		btn_MovCancelAllSltOff.setEnabled(false);
		btn_ListClear.setEnabled(false);
		
		//#CM571611
		// Clear the Preset area.
		lst_SStockMoveMaintenance.clearRow();
		txt_SRConsignorCode.setText("");
		txt_SRConsignorName.setText("");
	}
	
	//#CM571612
	// Event handler methods -----------------------------------------
	//#CM571613
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571614
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571615
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571616
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
		//#CM571617
		// Change to the menu panel.
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM571618
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571619
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571620
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571621
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571622
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571623
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571624
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571625
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571626
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571627
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571628
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571629
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571630
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571631
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571632
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571633
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_SearchConsignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571634
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
	public void btn_SearchConsignor_Click(ActionEvent e) throws Exception
	{
		//#CM571635
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM571636
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM571637
		// Retrieval flag
		param.setParameter(
			ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY,
			StorageSupportParameter.SEARCH_STOCKMOVE);
		//#CM571638
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageconsignor/ListStorageConsignor.do",
			param,
			"/progress.do");
	}

	//#CM571639
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571640
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571641
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571642
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571643
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571644
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_SearchItem_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571645
	/** 
	 * It is called when the item retrieval button is pressed.<BR>
	 * <BR>
	 * Outline : Set the search condition in Parameter, and display the item retrieval list box by the search condition. <BR>
	 * <BR>
	 * [Parameter]*Mandatory input
	 *  <DIR>
	 *  	Consignor Code<BR>
	 *  	Item Code<BR>
	 *  </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_SearchItem_Click(ActionEvent e) throws Exception
	{
		//#CM571646
		// Set the search condition on the Item retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM571647
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM571648
		// Item Code
		param.setParameter(
		    ListStorageItemBusiness.ITEMCODE_KEY, 
		    txt_ItemCode.getText());
		//#CM571649
		// Retrieval flag
		param.setParameter(
			ListStorageItemBusiness.SEARCHITEM_KEY,
			StorageSupportParameter.SEARCH_STOCKMOVE);
		//#CM571650
		// Processing Screen -> Result screen
		redirect("/storage/listbox/liststorageitem/ListStorageItem.do", param, "/progress.do");
	}

	//#CM571651
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DspOrder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571652
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571653
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ItemCode_Click(ActionEvent e) throws Exception
	{
	}

	//#CM571654
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_FromMoveLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571655
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_FromMoveLocation_Click(ActionEvent e) throws Exception
	{
	}

	//#CM571656
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DspMoveComplete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571657
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUseCompData_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571658
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUseCompData_Change(ActionEvent e) throws Exception
	{
	}

	//#CM571659
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MoveWorkingList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571660
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUseWorkList_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571661
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUseWorkList_Change(ActionEvent e) throws Exception
	{
	}

	//#CM571662
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571663
	/** 
	 * It is called when the display button is pressed.<BR>
	 * <BR>
	 * Outline : Input Item of the input area is set in Parameter, and Start Schedule. <BR>
	 * Display the retrieval result of the schedule in Preset area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Input Check (Mandatory input, Number of characters, Character attribute) <BR>
	 * 		2.Begin scheduling. (Retrieve it)<BR>
	 * 		3.Maintain the search condition in ViewState. <BR>
	 * 		4.Display it in Preset area if there is display Item. <BR>
	 * 		<BR>
	 * 		<DIR>
	 * 			[ViewState information]
	 * 			<DIR>
	 * 				Consignor Code<BR>
	 * 				Item Code<BR>
	 * 				The order of display<BR>
	 * 				Movement Completed data display Flag<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		
		//#CM571664
		// Make Add/Modify button, Movement Cancel all Selection, Movement CancelUnselect all, and Clear list Button unuseable and Clear the Preset area. 
		listCellClear();
		//#CM571665
		// Worker Code
		txt_WorkerCode.validate();
		//#CM571666
		// Password
		txt_Password.validate();
		//#CM571667
		// Do the input check (Format, Mandatory, Restricted characters) 
		txt_ConsignorCode.validate();
		txt_ItemCode.validate(false);
		
		Connection conn = null;
		
		try
		{
			//#CM571668
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			
			//#CM571669
			// Set schedule Parameter. 
			StorageSupportParameter param = new StorageSupportParameter();
			//#CM571670
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM571671
			// Password
			param.setPassword(txt_Password.getText());
			//#CM571672
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM571673
			// Item Code
			param.setItemCode(txt_ItemCode.getText());
			//#CM571674
			// The order of display
			if(rdo_ItemCode.getChecked())
			{
				//#CM571675
				// The order of Item Code
				param.setDisplayOrder(StorageSupportParameter.DISPLAY_ORDER_ITEM_CODE);
			}
			else
			{
				//#CM571676
				// The order of Location before movement
				param.setDisplayOrder(StorageSupportParameter.DISPLAY_ORDER_SOURCE_LOCATION);
			}
			//#CM571677
			// Movement Completed data display Flag
			param.setMoveCompDisplayFlg(chk_CommonUseCompData.getChecked());
			//#CM571678
			// Movement Work list print Flag
			param.setMoveWorkListFlag(chk_CommonUseWorkList.getChecked());
			
			
			//#CM571679
			// Preservation to ViewState (For another Retrieval after processing) 
			//#CM571680
			// Consignor Code
			this.getViewState().setString(VSTCONSIGNOR, param.getConsignorCode());
			//#CM571681
			// Item Code
			this.getViewState().setString(VSTITEMCODE, param.getItemCode());
			//#CM571682
			// The order of display
			this.getViewState().setString(VSTSORT, param.getDisplayOrder());
			//#CM571683
			// Movement Completed data display Flag
			this.getViewState().setBoolean(VSTDISPCOMP, param.getMoveCompDisplayFlg());
			
			
			//#CM571684
			// Start scheduling.
			WmsScheduler schedule = new StockMoveMaintenanceSCH();
			StorageSupportParameter[] viewParam = (StorageSupportParameter[])schedule.query(conn, param);
			
			//#CM571685
			// End processing when some errors occur or there is no display data. 
			if( viewParam == null || viewParam.length <= 0 )
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			
			//#CM571686
			// Display the retrieval result in Preset area. 
			setList(viewParam);
			
			//#CM571687
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
				//#CM571688
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

	//#CM571689
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571690
	/** 
	 * It is called when the clear button is pressed.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Initialize the input area. <BR>
	 * 		<BR>
	 * 		<DIR>
	 *  		Item Name[Initial value]
	 *  		<DIR>
	 *  			Worker Code[As it is]<BR>
	 *  			Password[As it is]<BR>
	 *  			Consignor Code[Display only in case of one in Consignor retrieval result is available]<BR>
	 *  			Item Code[None]<BR>
	 *  			The order of display[The order of Item Code]<BR>
	 *  			Display the Movement Completed data. [false]<BR>
	 *  			Print the Movement Work list[false]<BR>
	 *  		</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM571691
		// Set Initial value in each input field. 
		txt_ConsignorCode.setText(getConsignorCode());
		txt_ItemCode.setText("");
		rdo_ItemCode.setChecked(true);
		rdo_FromMoveLocation.setChecked(false);
		chk_CommonUseCompData.setChecked(false);
		chk_CommonUseWorkList.setChecked(false);
	}

	//#CM571692
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ModifySubmit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571693
	/** 
	 * It is called when Add/Modify button is pressed.<BR>
	 * <BR>
	 * Outline:Maintain it by information on Preset area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Do Input Check of Worker Code and Password. <BR>
	 * 		2.Display the dialog box, and confirm whether to Add. <BR>
	 * 		<DIR>
	 * 			[Confirmation Dialog Cancel]<BR>
	 * 			<DIR>
	 * 				Do Nothing.
	 * 			</DIR>
	 * 			[Confirmation Dialog OK]<BR>
	 * 			<DIR>
	 * 				1.Do the Input Check of Preset area.<BR>
	 * 				2.Only the line for the update is set in Parameter, and Start Schedule. <BR>
	 * 				<DIR>
	 * 					[Parameter]<BR>
	 * 					<DIR>
	 * 						Worker Code<BR>
	 * 						Password<BR>
	 * 						Consignor Code<BR>
	 * 						Item Code<BR>
	 * 						The order of display<BR>
	 * 						Movement Completed data display Flag<BR>
	 * 						Movement Work list print Flag<BR>
	 * 						Preset.CancelFlag<BR>
	 * 						Preset.Item Code<BR>
	 * 						Preset.Location before movement<BR>
	 * 						Preset.Location after movement<BR>
	 * 						Preset.Packed qty per case<BR>
	 * 						Preset.PlanCase qty<BR>
	 * 						Preset.PlanPiece qty<BR>
	 * 						Preset.Movement Case qty<BR>
	 * 						Preset.Movement Piece qty<BR>
	 * 						Preset.Status<BR>
	 * 						Preset.Work No.<BR>
	 * 						Preset.Consolidating Work No.<BR>
	 * 						Preset.Last updated date and time<BR>
	 * 						Terminal No.<BR>
	 * 						Preset Line No.<BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				3.Display information on Preset again. <BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_ModifySubmit_Click(ActionEvent e) throws Exception
	{

		//#CM571694
		// Do the input check of Worker Code, Password.
		txt_WorkerCode.validate();
		txt_Password.validate();
		
		Connection conn = null;
			
		try
		{
			//#CM571695
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM571696
			// The Preset elements are checked and only the updated line sets the value in Vector. 
			Vector vecParam = new Vector(lst_SStockMoveMaintenance.getMaxRows());
			for(int i = 1; i < lst_SStockMoveMaintenance.getMaxRows(); i++)
			{
				//#CM571697
				// Select the work line. 
				lst_SStockMoveMaintenance.setCurrentRow(i);
				
				lst_SStockMoveMaintenance.validate(LSTLOCATIONTO, false);
				
				//#CM571698
				// Input character check for eWareNavi
				if (!checkContainNgText(i))
				{
					return;
				}

				//#CM571699
				// Acquire HIDDEN Item.
				String hidden = lst_SStockMoveMaintenance.getValue(LSTHDN);
				String orgAryJobNo = CollectionUtils.getString(HDNARYJOBNO, hidden);
				String orgAryLastDate = CollectionUtils.getString(HDNARYLASTUPDATEDATE, hidden);
				String orgLocationTo = CollectionUtils.getString(HDNLOCATIONTO, hidden);
				String orgMoveCaseQty = CollectionUtils.getString(HDNMOVECASEQTY, hidden);
				String orgMovePieceQty = CollectionUtils.getString(HDNMOVEPIECEQTY, hidden);
				String orgStatus = CollectionUtils.getString(HDNSTATUS, hidden);
				String orgCollect = CollectionUtils.getString(HDNCOLLECTJOBNO, hidden);;
				
				
				//#CM571700
				// Work No of Concealed Item and Last updated date and time maintain coverage in intStrPos and intEndPos for changeability. 
				int intStrPos = HDNJOBNO;
				int intEndPos = HDNJOBNO + Formatter.getInt(orgAryJobNo);
				
				
				//#CM571701
				// Work  No. is acquired. 
				Vector orgJobNoList = new Vector();
				for(int j = intStrPos; j < intEndPos; j++)
				{
					orgJobNoList.add(CollectionUtils.getList(hidden).get(j));
				}
				
				
				//#CM571702
				// Last updated date and time is acquired. 
				Vector orgLastDateList = new Vector();
				intStrPos = intEndPos;
				intEndPos = intEndPos + Formatter.getInt(orgAryLastDate);
				for(int j = intStrPos; j < intEndPos; j++)
				{
					String strLastUpDate = (String) CollectionUtils.getList(hidden).get(j);
					orgLastDateList.add(WmsFormatter.getTimeStampDate(strLastUpDate));
				}
				
				//#CM571703
				// Check on Cancel Flag
				if(lst_SStockMoveMaintenance.getChecked(LSTCANCEL))
				{
					//#CM571704
					// During Storage
					if(lst_SStockMoveMaintenance.getValue(LSTSTATUS).equals(STATUSWORKING))
					{
						//#CM571705
						// Display error Message, and finish.
						//#CM571706
						// 6023301=No.{0} If status is Storage in Process, you cannot cancel Relocation.
						message.setMsgResourceKey("6023301" + wDelim + i);
						return;
					}
					//#CM571707
					// Movement Completed
					if(lst_SStockMoveMaintenance.getValue(LSTSTATUS).equals(STATUSCOMP))
					{
						//#CM571708
						// Display error Message, and finish.
						//#CM571709
						// 6023302=No.{0} If status is Relocation Completed, you cannot cancel Relocation.
						message.setMsgResourceKey("6023302" + wDelim + i);
						return;
					}
					
				}

				//#CM571710
				// When former Status is [Movement Completed]
				if(orgStatus.equals(STATUSCOMP))
				{
					//#CM571711
					// Not revokable in other Status. 
					if(!lst_SStockMoveMaintenance.getValue(LSTSTATUS).equals(STATUSCOMP))
					{
						//#CM571712
						// Display error Message, and finish. 
						//#CM571713
						// 6023308=No.{0} If status is Relocation Completed, you cannot change it to other status.
						message.setMsgResourceKey("6023308" + wDelim + i);
						return;
					}

					//#CM571714
					// Location after movement is not revokable. 
					if(!lst_SStockMoveMaintenance.getValue(LSTLOCATIONTO).equals(orgLocationTo))
					{
						//#CM571715
						// Display error Message, and finish. 
						//#CM571716
						// 6023305=No.{0} If status is Relocation Completed, you cannot change Destination Location No.
						message.setMsgResourceKey("6023305" + wDelim + i);
						return;
					}

					//#CM571717
					// Movement  Case qty is not revokable. 
					if(!lst_SStockMoveMaintenance.getValue(LSTMOVECASEQTY).equals(orgMoveCaseQty))
					{
						//#CM571718
						// Display error Message, and finish. 
						//#CM571719
						// 6023306=No.{0} If status is Relocation Completed, you cannot change Relocation Case Qty.
						message.setMsgResourceKey("6023306" + wDelim + i);
						return;
					}

					//#CM571720
					// Movement  Piece qty is not revokable. 
					if(!lst_SStockMoveMaintenance.getValue(LSTMOVEPIECEQTY).equals(orgMovePieceQty))
					{
						//#CM571721
						// Display error Message, and finish. 
						//#CM571722
						// 6023307=No.{0} If status is Relocation Completed, you cannot change Relocation Piece Qty.
						message.setMsgResourceKey("6023307" + wDelim + i);
						return;
					}
				}
				
				

				//#CM571723
				// Set the value in Vector to process it in case of the update object. 
				if(isChangeData())
				{
					StorageSupportParameter param = new StorageSupportParameter();
					
					//#CM571724
					// The value is set in Parameter. 
					//#CM571725
					// Worker Code
					param.setWorkerCode(txt_WorkerCode.getText());
					//#CM571726
					// Password
					param.setPassword(txt_Password.getText());
					//#CM571727
					// Consignor Code
					param.setConsignorCode(this.getViewState().getString(VSTCONSIGNOR));
					//#CM571728
					// Item Code
					param.setItemCode(this.getViewState().getString(VSTITEMCODE));
					//#CM571729
					// The order of display
					param.setDisplayOrder(this.getViewState().getString(VSTSORT));
					//#CM571730
					// Movement Completed data display Flag
					param.setMoveCompDisplayFlg(this.getViewState().getBoolean(VSTDISPCOMP));
					//#CM571731
					// Movement Work list print Flag
					param.setMoveWorkListFlag(chk_CommonUseWorkList.getChecked());
					//#CM571732
					// CancelFlag
					param.setMoveCancelFlg(lst_SStockMoveMaintenance.getChecked(LSTCANCEL));
					//#CM571733
					// Item Code(List cell)
					param.setItemCodeL(lst_SStockMoveMaintenance.getValue(LSTITEMCODE));
					//#CM571734
					// Location before movement
					param.setSourceLocationNo(lst_SStockMoveMaintenance.getValue(LSTLOCATIONFROM));
					//#CM571735
					// Location after movement
					param.setDestLocationNo(lst_SStockMoveMaintenance.getValue(LSTLOCATIONTO));
					//#CM571736
					// Packed qty per case
					param.setEnteringQty(Formatter.getInt(lst_SStockMoveMaintenance.getValue(LSTENTQTY)));
					//#CM571737
					// Plan Case qty
					param.setPlanCaseQty(Formatter.getInt(lst_SStockMoveMaintenance.getValue(LSTPLANCASEQTY)));
					//#CM571738
					// Plan Piece qty
					param.setPlanPieceQty(Formatter.getInt(lst_SStockMoveMaintenance.getValue(LSTPLANPIECEQTY)));
					//#CM571739
					// Movement  Case qty
					param.setResultCaseQty(Formatter.getInt(lst_SStockMoveMaintenance.getValue(LSTMOVECASEQTY)));
					//#CM571740
					// Movement  Piece qty
					param.setResultPieceQty(Formatter.getInt(lst_SStockMoveMaintenance.getValue(LSTMOVEPIECEQTY)));
					//#CM571741
					// Status
					if(lst_SStockMoveMaintenance.getValue(LSTSTATUS).equals(STATUSUNSTART))
					{
						//#CM571742
						// Waiting for Storage
						param.setStatusFlagL(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
					}
					else if(lst_SStockMoveMaintenance.getValue(LSTSTATUS).equals(STATUSWORKING))
					{
						//#CM571743
						// During Storage
						param.setStatusFlagL(StorageSupportParameter.STATUS_FLAG_WORKING);
					}
					else if(lst_SStockMoveMaintenance.getValue(LSTSTATUS).equals(STATUSCOMP))
					{
						//#CM571744
						// Movement Completed
						param.setStatusFlagL(StorageSupportParameter.STATUS_FLAG_COMPLETION);
					}
					//#CM571745
					// Preset Line No.
					param.setRowNo(lst_SStockMoveMaintenance.getCurrentRow());
					//#CM571746
					// Work No.
					param.setJobNoList(orgJobNoList);
					//#CM571747
					// Consolidating Work No.
					param.setCollectJobNo(orgCollect);
					//#CM571748
					// Last updated date and time
					param.setLastUpdateDateList(orgLastDateList);
					
					//#CM571749
					// Terminal No.
					UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
					param.setTerminalNumber(userHandler.getTerminalNo());
					
					vecParam.addElement(param);
				}
			}
				
			//#CM571750
			// "There is no data for the update" in case of Number of elements is 0 and Processing is finished. 
			if(vecParam.size() == 0)
			{
				message.setMsgResourceKey("6023154");
				return;
			}

			
			//#CM571751
			// Copy the value onto Parameter. 
			StorageSupportParameter[] paramArray = new StorageSupportParameter[vecParam.size()];
			vecParam.copyInto(paramArray);
				
			//#CM571752
			// Schedule start
			WmsScheduler schedule = new StockMoveMaintenanceSCH();
			StorageSupportParameter[] viewParam = (StorageSupportParameter[])schedule.startSCHgetParams(conn, paramArray);
			
			//#CM571753
			// It fails in the update if Return Data is null. 
			//#CM571754
			// Set Rollback and the message. (Preset with the data displayed before)
			if( viewParam == null )
			{
				//#CM571755
				// Rollback
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
				
			//#CM571756
			// It succeeds in the update if the number of elements of Return Data is 0 or more. 
			//#CM571757
			// Do the setting of the transaction and display again. 
			else if( viewParam.length >= 0 )
			{
				//#CM571758
				// Commit
				conn.commit();
				//#CM571759
				// Display it when the schedule is normally completed and the display data is acquired. 
				listCellClear();
				if( viewParam.length > 0 )
				{
					//#CM571760
					// Display Preset area again. 
					setList(viewParam);
				}
			}
			
			//#CM571761
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
				//#CM571762
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

	//#CM571763
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_MovCancelAllSlt_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571764
	/**
	 * When Movement Cancel all Selection button is pressed, it is called.  <BR>
	 * <BR>
	 * Outline:Check the Cancel check box of Preset area.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_MovCancelAllSlt_Click(ActionEvent e) throws Exception
	{
		
		for (int i = 1; i < lst_SStockMoveMaintenance.getMaxRows(); i++)
		{
			//#CM571765
			// Select the Work line. 
			lst_SStockMoveMaintenance.setCurrentRow(i);
			lst_SStockMoveMaintenance.setChecked(LSTCANCEL, true);
		}
		
	}

	//#CM571766
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_MovCancelAllSltOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571767
	/**
	 * Movement CancelUnIt is called when the Select all button is pressed. <BR>
	 * <BR>
	 * Outline:Clear all checks of the Cancel check box of Preset area.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_MovCancelAllSltOff_Click(ActionEvent e) throws Exception
	{
		
		for (int i = 1; i < lst_SStockMoveMaintenance.getMaxRows(); i++)
		{
			//#CM571768
			// Select the Work line. 
			lst_SStockMoveMaintenance.setCurrentRow(i);
			lst_SStockMoveMaintenance.setChecked(LSTCANCEL, false);
		}
		
	}

	//#CM571769
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571770
	/** 
	 * It is called when the clear list button is pressed.<BR>
	 * <BR>
	 * Outline : Clear all displays in Preset area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Clears Consignor Code and Consignor Name (Read only Item). <BR>
	 * 		2.Invalidate all Buttons of Preset area. <BR>
	 * 		3.Clear the list cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		//#CM571771
		// Preset area is deleted.
		txt_SRConsignorCode.setText("");
		txt_SRConsignorName.setText("");

		//#CM571772
		// Make Add/Modify button, Movement cancellation all Selection, Movement cancellation Unselect all, and Clear list Button unuseable. 
		btn_ModifySubmit.setEnabled(false);
		btn_MovCancelAllSlt.setEnabled(false);
		btn_MovCancelAllSltOff.setEnabled(false);
		btn_ListClear.setEnabled(false);

		lst_SStockMoveMaintenance.clearRow();
	}

	//#CM571773
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571774
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571775
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571776
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571777
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571778
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571779
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571780
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571781
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571782
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStockMoveMaintenance_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM571783
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStockMoveMaintenance_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM571784
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStockMoveMaintenance_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM571785
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStockMoveMaintenance_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM571786
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStockMoveMaintenance_Server(ActionEvent e) throws Exception
	{
	}

	//#CM571787
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStockMoveMaintenance_Change(ActionEvent e) throws Exception
	{
	}

	//#CM571788
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStockMoveMaintenance_Click(ActionEvent e) throws Exception
	{
	}


}
//#CM571789
//end of class
