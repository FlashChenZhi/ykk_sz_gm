// $Id: InventoryCompleteBusiness.java,v 1.2 2006/12/07 08:57:49 suresh Exp $

//#CM566570
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.inventorycomplete;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.storage.display.web.listbox.listinventorylocation.ListInventoryLocationBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.schedule.InventoryCompleteSCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM566571
/**
 * Designer : H.Akiyama <BR>
 * Maker    : H.Akiyama <BR>
 * <BR>
 * Inventory Setting (stock update) class. <BR>
 * Set the content input from the screen in the parameter, pass to the schedule, and do the display processing. <BR>
 * Receive true when the result is received from the schedule, and processing is completed normally <BR>
 * and receive false when it does not complete the schedule because of the condition error etc. <BR>
 * The result of the schedule outputs the message acquired from the schedule to the screen. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Display button is pressed (<CODE>btn_View_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 *   Check the input item and if it is correct, set in the parameter and pass it. <BR>
 *   Set the result of the schedule in the message area and display it. <BR>
 * <BR>
 *   [Parameter] *Mandatory input <BR>
 * <BR>
 *   <DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Consignor Code* <BR>
 *     Start Location No. <BR>
 * 	   End Location No. <BR>
 *   </DIR>
 * <BR>
 *    [Output data] <BR>
 * <BR>
 *    <DIR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     Location No. <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Packed qty per case <BR>
 *     Packed qty per bundle <BR>
 *     Inventory Case qty(Inventory Stock qty/Packed qty per case) <BR>
 *     Inventory Piece qty(Inventory Stock qty%Packed qty per case) <BR>
 *     Stock Case qty(Stock qty/Packed qty per case) <BR>
 *     Stock Piece qty(Stock qty%Packed qty per case) <BR>
 *     Expiry Date <BR>
 *     Worker Code <BR>
 *     Worker Name <BR>
 *    </DIR>
 * </DIR>
 * <BR>
 * 2.Processing when Inventory Result Button is pressed (<CODE>btn_InventoryResult_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 *   Pass the data that requires check in setting the content displayed in the Preset area as Parameter. <BR>
 *   As a result, do the inventory result setting processing. Acquire the message and display it.  <BR>
 * <BR>
 *   [Parameter] *Mandatory input<BR>
 * <BR>
 *   <DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Start Location No. <BR>
 *     End Location No. <BR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     Location No. <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Packed qty per case <BR>
 *     Packed qty per bundle <BR>
 *     Stock Case qty <BR>
 *     Stock Piece qty <BR>
 *     Inventory Case qty <BR>
 *     Inventory Piece qty <BR>
 *     Expiry Date <BR>
 *     Preset Line No. <BR>
 *   </DIR>
 * <BR>
 *    [Return Data] <BR>
 * <BR>
 *    <DIR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     Location No. <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Packed qty per case <BR>
 *     Packed qty per bundle <BR>
 *     Inventory Case qty(Inventory Stock qty/Packed qty per case) <BR>
 *     Inventory Piece qty(Inventory Stock qty%Packed qty per case) <BR>
 *     Stock Case qty(Stock qty/Packed qty per case) <BR>
 *     Stock Piece qty(Stock qty%Packed qty per case) <BR>
 *     Expiry Date <BR>
 *     Worker Code <BR>
 *     Worker Name <BR>
 *    </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/24</TD><TD>H.Akiyama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:49 $
 * @author  $Author: suresh $
 */
public class InventoryCompleteBusiness extends InventoryComplete implements WMSConstants
{
	//#CM566572
	// Class fields --------------------------------------------------

	//#CM566573
	/**
	 * For ViewState : Consignor Code
	 */
	protected static final String CONSIGNORCODE = "CONSIGNOR_CODE";
	
	//#CM566574
	/**
	 * For ViewState : Start Location
	 */
	protected static final String STARTLOCATION = "START_LOCATION";

	//#CM566575
	/**
	 * For ViewState : End Location
	 */
	protected static final String ENDLOCATION = "END_LOCATION";

	//#CM566576
	/**
	 * For HIDDEN item : Work No.
	 */
	protected static final int JOBNO = 0;
	
	//#CM566577
	/**
	 * For HIDDEN item : Last updated date and time
	 */
	protected static final int LASTUPDATE = 1;
	
	

	//#CM566578
	// Class variables -----------------------------------------------

	//#CM566579
	// Class method --------------------------------------------------

	//#CM566580
	// Constructors --------------------------------------------------

	//#CM566581
	// Public methods ------------------------------------------------

	//#CM566582
	/**
	 * It is called when the screen is read.<BR>
	 * <BR>
	 * Outline:Display initial data in the screen. <BR>
	 * <BR>
	 * <DIR>
	 *   	1.Display the title. <BR>
	 *    	2.Initialize the input area. <BR>
	 *    	3.Invalidate the inventory result setting button, all selection release buttons and the Clear list buttons. <BR>
	 *    	4.Set the cursor in Worker Code. <BR>
	 * 		<BR>
	 * 		Item[Initial value] <BR>
	 * 		<BR>
	 * 		<DIR>
	 * 			Worker Code[None] <BR>
	 * 			Password[None] <BR>
	 * 			Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Start Location[None] <BR>
	 * 			End Location[None] <BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		
		//#CM566583
		// Set Initial value in each input field. 
		txt_WorkerCode.setText("");
		txt_Password.setText("");
		//#CM566584
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM566585
		// Start Location
		txt_StartLocation.setText("");
		//#CM566586
		// End Location
		txt_EndLocation.setText("");

		//#CM566587
		// Invalidate the button of the Preset part. 
		setBtnTrueFalse(false);

		//#CM566588
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
	}

	//#CM566589
	/**
	 * It is called before the call of each control event.  <BR>
	 * <BR>
	 * <DIR>
	 *  Outline:Display the dialog. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM566590
			//Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM566591
			//Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM566592
			//Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
		//#CM566593
		// Display the dialog. MSG-W0044=Do you submit Inventory Check?
		btn_InventoryResult.setBeforeConfirm("MSG-W0044");
		//#CM566594
		// Display the dialog. MSG-W0012=Do you clear the list?
		btn_ListClear.setBeforeConfirm("MSG-W0012");
	}

	//#CM566595
	/**
	 * This Method is called when returning from the pop up window.<BR>
	 * Override page_DlgBack defined in Page. <BR>
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
		//#CM566596
		// Parameter selected from list box is acquired. 
		//#CM566597
		// Consignor Code
		String consignorCode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM566598
		// Start Location
		String startLocation = param.getParameter(ListInventoryLocationBusiness.STARTLOCATION_KEY);
		//#CM566599
		// End Location
		String endLocation = param.getParameter(ListInventoryLocationBusiness.ENDLOCATION_KEY);

		//#CM566600
		// Consignor Code
		if (!StringUtil.isBlank(consignorCode))
		{
			txt_ConsignorCode.setText(consignorCode);
		}
		//#CM566601
		// Start Location
		if (!StringUtil.isBlank(startLocation))
		{
			txt_StartLocation.setText(startLocation);
		}
		//#CM566602
		// End Location
		if (!StringUtil.isBlank(endLocation))
		{
			txt_EndLocation.setText(endLocation);
		}
		//#CM566603
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
	}

	//#CM566604
	// Package methods -----------------------------------------------

	//#CM566605
	// Protected methods ---------------------------------------------

	//#CM566606
	// Private methods -----------------------------------------------

	//#CM566607
	// Event handler methods -----------------------------------------
	//#CM566608
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566609
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566610
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566611
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
		//#CM566612
		// Change to the menu panel.
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM566613
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566614
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566615
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM566616
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM566617
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM566618
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566619
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566620
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM566621
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM566622
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM566623
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566624
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566625
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM566626
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM566627
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM566628
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566629
	/**
	 * It is called when the retrieval of Consignor Code button is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Consignor list box by the search condition.<BR>
	 * <BR>
	 * <DIR>
	 *   	[Parameter] *Mandatory input<BR>
	 * 		<BR>
	 *     	<DIR>
	 *     		Consignor Code <BR>
	 *     	</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		//#CM566630
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM566631
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM566632
		// Retrieval flag : Inventory work information retrieval
		param.setParameter(ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY,
			StorageSupportParameter.SEARCH_INVENTORY);
		//#CM566633
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageconsignor/ListStorageConsignor.do", param,	"/progress.do");
	}

	//#CM566634
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566635
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566636
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM566637
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM566638
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM566639
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrtLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566640
	/**
	 * It is called when the retrieval of Start Location button is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the location list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *   	[Parameter] *Mandatory input<BR>
	 * 		<BR>
	 *     	<DIR>
	 *     		Consignor Code <BR>
	 *     		Start Location <BR>
	 *     	</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchStrtLct_Click(ActionEvent e) throws Exception
	{
		//#CM566641
		//	Set the search condition on the Start Location retrieval screen. 
		ForwardParameters param = new ForwardParameters();

		//#CM566642
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM566643
		// Retrieval flag : For Inventory
		param.setParameter(ListInventoryLocationBusiness.SEARCHLOCATION_KEY, StorageSupportParameter.SEARCH_INVENTORY);
		//#CM566644
		// Start Location
		param.setParameter(ListInventoryLocationBusiness.STARTLOCATION_KEY,	txt_StartLocation.getText());
		//#CM566645
		// Set Start flag
		param.setParameter(ListInventoryLocationBusiness.RANGELOCATION_KEY,
			StorageSupportParameter.RANGE_START);
		//#CM566646
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/listinventorylocation/ListInventoryLocation.do", param, "/progress.do");		
	}

	//#CM566647
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566648
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566649
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566650
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM566651
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM566652
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM566653
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEdLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566654
	/**
	 * It is called when the retrieval of End Location button is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the location list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *   	[Parameter] *Mandatory input<BR>
	 *   	<BR>
	 *     	<DIR>
	 *     		Consignor Code <BR>
	 *     		End Location <BR>
	 *     	</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchEdLct_Click(ActionEvent e) throws Exception
	{
		//#CM566655
		//	Set the search condition on the End Location retrieval screen. 
		ForwardParameters param = new ForwardParameters();

		//#CM566656
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM566657
		// Retrieval flag : For Inventory
		param.setParameter(ListInventoryLocationBusiness.SEARCHLOCATION_KEY, StorageSupportParameter.SEARCH_INVENTORY);
		//#CM566658
		// End Location
		param.setParameter(ListInventoryLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getText());
		//#CM566659
		// Set the End flag
		param.setParameter(ListInventoryLocationBusiness.RANGELOCATION_KEY,
			StorageSupportParameter.RANGE_END);
		//#CM566660
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/listinventorylocation/ListInventoryLocation.do", param, "/progress.do");
	}

	//#CM566661
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566662
	/**
	 * It is called when the display button is pressed.<BR>
	 * <BR>
	 * Outline:Display the input item of input area in condition and Preset area.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Initialize the Preset part and Set the cursor in Worker Code. <BR>
	 *     	2.Check the Input item of Input area.<BR>
	 *      <DIR>
	 *      	 (Mandatory input, Number of characters, Character attribute)  <BR>
	 *      	 (Start Location must be lesser than End Location.) <BR>
	 *      </DIR>
	 *     	3.Start Schedule. <BR>
	 *     	4.Display the result of the schedule in the Preset area.  <BR>
	 *     	5.Make the inventory result Setting, Select all button, Select none button and the Clear list buttons effective.  <BR>
	 *     	6.Maintain the content of the condition of the input area.  <BR>
	 * 		<BR>
	 * 		[List of row number of list cell] <BR>
	 * 		<DIR>
	 *     		1:Final check box <BR>
	 *     		2:Location <BR>
	 *     		3:Item Code <BR>
	 *     		4:Packed qty per case <BR>
	 *     		5:Inventory Case qty <BR>
	 *     		6:Stock Case qty <BR>
	 *     		7:Expiry Date <BR>
	 *     		8:Worker Code <BR>
	 *     		9:Item Name <BR>
	 *     		10:Packed qty per bundle <BR>
	 *     		11:Inventory Piece qty <BR>
	 *     		12:Stock Piece qty <BR>
	 *     		13:Worker Name <BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		//#CM566663
		// Initialize all the filttering parts. 
		listClear();
		
		//#CM566664
		// Do the input check (Format, Mandatory, Restricted characters) 
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		
		txt_StartLocation.validate(false);
		txt_EndLocation.validate(false);
		
		//#CM566665
		// Output the error message when End Location is not less than Start Location.
		if (!StringUtil.isBlank(txt_StartLocation.getText())
			&& !StringUtil.isBlank(txt_EndLocation.getText()))
		{
			if (txt_StartLocation.getText().compareTo(txt_EndLocation.getText()) > 0)
			{
				//#CM566666
				// 6023057 = Please enter {1} or greater for {0}.
				//#CM566667
				// 0 : End Location 1 : Start Location
				message.setMsgResourceKey("6023057"	+ wDelim + 
					DisplayText.getText(lbl_EndLocation.getResourceKey()) + wDelim
						+ DisplayText.getText(lbl_StartLocation.getResourceKey()));
				return;
			}
		}

		Connection conn = null;

		try
		{
			//#CM566668
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM566669
			// Declaration of Parameter
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM566670
			// Set schedule Parameter. 
			//#CM566671
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM566672
			// Password
			param.setPassword(txt_Password.getText());
			//#CM566673
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM566674
			// Start Location
			param.setFromLocation(txt_StartLocation.getText());
			//#CM566675
			// End Location
			param.setToLocation(txt_EndLocation.getText());

			//#CM566676
			// Declaration of schedule
			WmsScheduler schedule = new InventoryCompleteSCH();
			//#CM566677
			// Start scheduling.
			StorageSupportParameter[] viewParam = (StorageSupportParameter[]) schedule.query(conn, param);

			//#CM566678
			// End processing when some errors happen or there is no display data. 
			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM566679
			// Maintain the retrieval value in ViewState.  
			//#CM566680
			// Consignor Code
			this.getViewState().setString(CONSIGNORCODE, txt_ConsignorCode.getText());
			//#CM566681
			// Start Location
			this.getViewState().setString(STARTLOCATION, txt_StartLocation.getText());
			//#CM566682
			// End Location
			this.getViewState().setString(ENDLOCATION, txt_EndLocation.getText());

			//#CM566683
			// Display the Preset value.
			addList(viewParam);

			//#CM566684
			// Set Message
			message.setMsgResourceKey(schedule.getMessage());
			
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM566685
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

	//#CM566686
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566687
	/**
	 * It is called when the clear button is pressed. <BR>
	 * <BR>
	 * Outline:Clear the Input area. <BR>
	 * <BR>
	 * <DIR>
	 *     1.Clear the items of Input area. <BR>
	 *     <DIR>
	 *         Worker Code[As it is] <BR>
	 *         Password[As it is] <BR>
	 *         Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 *         Start Location[Clear] <BR>
	 *         End Location[Clear] <BR>
	 *     </DIR>
	 *     2.Set the cursor in Worker Code.  <BR>
	 *     3.Maintain the contents of Preset area.  <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM566688
		// Set Initial value in each input field. 
		//#CM566689
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM566690
		// Start Location
		txt_StartLocation.setText("");
		//#CM566691
		// End Location
		txt_EndLocation.setText("");

		//#CM566692
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);		
	}

	//#CM566693
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_InventoryResult_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566694
	/**
	 * It is called when the Inventory result setting button is pressed. <BR>
	 * <BR>
	 * Outline:Set Inventory work information by information on Preset area.  <BR>
	 * <BR>
	 * <DIR>
	 *     1.Set the cursor in Worker Code.  <BR>
	 *     2.Acquire the value from viewState and set it in Parameter. <BR>
	 *     3.Set information on Preset area that Final check box is checked in Parameter.  <BR>
	 *     4.Start Schedule. <BR>
	 *     5.Take again and display information from the schedule after it updates it to Preset area.  <BR>
	 *     6.Invalidate Inventory result setting , Select all , Unselect all , and the Clear list button when Preset information does not exist. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_InventoryResult_Click(ActionEvent e) throws Exception
	{
		//#CM566695
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);

		//#CM566696
		// Do the input check of Worker Code and Password. 
		txt_WorkerCode.validate();
		txt_Password.validate();

		Connection conn = null;

		try
		{
			//#CM566697
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM566698
			// Checked whether there is deleted data. 
			boolean wCheck = false;

			//#CM566699
			// The Preset elements are checked and only the updated line sets the value in Vector. 
			Vector vecParam = new Vector(lst_SInventoryComplete.getMaxRows());
			
			for (int i = 1; i < lst_SInventoryComplete.getMaxRows(); i++)
			{
				//#CM566700
				// Acquire the operated line. 
				lst_SInventoryComplete.setCurrentRow(i);
				
				//#CM566701
				// Stored in Parameter if the Final check box is checked.
				if (lst_SInventoryComplete.getChecked(1))
				{
					wCheck = true;

					StorageSupportParameter param = new StorageSupportParameter();
	
					//#CM566702
					// Worker Code
					param.setWorkerCode(txt_WorkerCode.getText());
					//#CM566703
					// Set Password
					param.setPassword(txt_Password.getText());
	
					//#CM566704
					// Set search condition for re-display(Consignor, Start Location, End Location)
					//#CM566705
					// Consignor Code
					param.setConsignorCode(this.getViewState().getString(CONSIGNORCODE));
					//#CM566706
					// Consignor Name
					param.setConsignorName(txt_SRConsignorName.getText());
					//#CM566707
					// Start Location
					param.setFromLocation(this.getViewState().getString(STARTLOCATION));
					//#CM566708
					// End Location
					param.setToLocation(this.getViewState().getString(ENDLOCATION));
	
					//#CM566709
					// Data for the update is set. 
					//#CM566710
					// Location
					param.setLocation(lst_SInventoryComplete.getValue(2));
					//#CM566711
					// Item Code
					param.setItemCode(lst_SInventoryComplete.getValue(3));
					//#CM566712
					// Packed qty per case
					param.setEnteringQty(Formatter.getInt(lst_SInventoryComplete.getValue(4)));
					//#CM566713
					// Inventory Case qty
					param.setInventoryCheckCaseQty(Formatter.getInt(lst_SInventoryComplete.getValue(5)));
					//#CM566714
					// Stock Case qty
					param.setStockCaseQty(Formatter.getInt(lst_SInventoryComplete.getValue(6)));
					//#CM566715
					// Expiry Date
					param.setUseByDate(lst_SInventoryComplete.getValue(7));
					//#CM566716
					// Item Name
					param.setItemName(lst_SInventoryComplete.getValue(9));
					//#CM566717
					// Packed qty per bundle
					param.setBundleEnteringQty(Formatter.getInt(lst_SInventoryComplete.getValue(10)));
					//#CM566718
					// Inventory Piece qty
					param.setInventoryCheckPieceQty(Formatter.getInt(lst_SInventoryComplete.getValue(11)));
					//#CM566719
					// Stock Piece qty
					param.setStockPieceQty(Formatter.getInt(lst_SInventoryComplete.getValue(12)));
					//#CM566720
					// Preset Line No.
					param.setRowNo(i);
					//#CM566721
					// Terminal No.
					UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
					param.setTerminalNumber(userHandler.getTerminalNo());
					//#CM566722
					// Acquire Concealed Item. 
					String hidden = lst_SInventoryComplete.getValue(0);
					String jobNo = CollectionUtils.getString(JOBNO, hidden);
					String lastupdate = CollectionUtils.getString(LASTUPDATE, hidden);
					
					//#CM566723
					// Last updated date and time
					param.setLastUpdateDate(WmsFormatter.getTimeStampDate(lastupdate));
					//#CM566724
					// Work No.
					param.setJobNo(jobNo);
						
					vecParam.addElement(param);
				}
			}

			//#CM566725
			// The error message is output if Final check box is not checked, and processing is ended. 
			if (wCheck == false)
			{
				//#CM566726
				// 6023154 = There is no data to update.
				message.setMsgResourceKey("6023154");
				return;
			}

			//#CM566727
			// Declaration of Parameter
			StorageSupportParameter[] paramArray = new StorageSupportParameter[vecParam.size()];
			//#CM566728
			// Copy the value onto Parameter. 
			vecParam.copyInto(paramArray);

			//#CM566729
			// Schedule start
			WmsScheduler schedule = new InventoryCompleteSCH();

			StorageSupportParameter[] viewParam =
				(StorageSupportParameter[]) schedule.startSCHgetParams(conn, paramArray);

			//#CM566730
			// It fails in the update if Return Data is null. 
			//#CM566731
			// Set Rollback and the message. (Preset with the data displayed before)
			if (viewParam == null)
			{
				//#CM566732
				// Rollback
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			
			//#CM566733
			// It succeeds in the update if the number of elements of Return Data is 0 or more. 
			//#CM566734
			// Do the setting of the transaction and display again. 
			else if (viewParam.length >= 0)
			{
				//#CM566735
				// Commit 
				conn.commit();

				//#CM566736
				// When there is no displayed data
				if (viewParam.length == 0)
				{
					//#CM566737
					// Clear the Preset area
					listClear();
				}
				else
				{
					//#CM566738
					// Display it when the schedule normally Completes and the display data is acquired. 
					lst_SInventoryComplete.clearRow();
					//#CM566739
					// Display in Preset again. 
					addList(viewParam);
				}

			}

			//#CM566740
			// Set Message
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM566741
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

	//#CM566742
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheck_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566743
	/**
	 * It is called when the Select all button is pressed. <BR>
	 * <BR>
	 * Outline:Check All in Final check box of Preset area.  <BR>
	 * <BR>
	 * <DIR>
	 *     1.Check All in Final check box. <BR>
	 *     2.Set the cursor in Worker Code.  <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_AllCheck_Click(ActionEvent e) throws Exception
	{
		//#CM566744
		// Check All in Final check box.
		for (int i = 1; i < lst_SInventoryComplete.getMaxRows(); i++)
		{
			//#CM566745
			// Select the Work line. 
			lst_SInventoryComplete.setCurrentRow(i);
			lst_SInventoryComplete.setChecked(1, true);
		}

		//#CM566746
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);		
	}

	//#CM566747
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheckClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566748
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
		//#CM566749
		// Remove the check on All Final check boxes.
		for (int i = 1; i < lst_SInventoryComplete.getMaxRows(); i++)
		{
			//#CM566750
			// Select the Work line. 
			lst_SInventoryComplete.setCurrentRow(i);
			lst_SInventoryComplete.setChecked(1, false);
		}
		//#CM566751
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);		
	}

	//#CM566752
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566753
	/**
	 * It is called when the clear list button is pressed. <BR>
	 * <BR>
	 * Outline:Display the dialog box, and confirm whether to clear the display of Preset area. <BR>
	 * <BR>
	 * <DIR>
	 *     1.Display Confirm dialog box. "List input information is Cleared. Is it OK?" <BR>
	 *       [Confirmation Dialog OK] <BR>
	 *       <DIR>
	 *         1.Initialize the list cell.  <BR>
	 *         2.Delete the condition of Preset area.  <BR>
	 *         3.Invalidate Inventory result setting , Select all , Unselect all , and the Clear list buttons.  <BR>
	 *         4.Set the cursor in Worker Code.  <BR>
	 *       </DIR>
	 *       [Confirmation Dialog Cancel] <BR>
	 *       <DIR>
	 *          Do Nothing. <BR>
	 *       </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		//#CM566754
		// Initialize the Preset area.
		listClear();
	}

	//#CM566755
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SConsignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566756
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566757
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM566758
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM566759
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM566760
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566761
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM566762
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM566763
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM566764
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInventoryComplete_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM566765
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInventoryComplete_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM566766
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInventoryComplete_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM566767
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInventoryComplete_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM566768
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInventoryComplete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566769
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInventoryComplete_Change(ActionEvent e) throws Exception
	{
	}

	//#CM566770
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInventoryComplete_Click(ActionEvent e) throws Exception
	{
	}

	//#CM566771
	// Private methods -----------------------------------------------
	//#CM566772
	/**
	 * Method to acquire Consignor Code from the schedule. <BR>
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
			//#CM566773
			// Acquire the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM566774
			// Declaration of Parameter
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM566775
			// Acquire Consignor Code from the schedule. 
			
			WmsScheduler schedule = new InventoryCompleteSCH();
			//#CM566776
			// Start Schedule.
			param = (StorageSupportParameter) schedule.initFind(conn, param);

			//#CM566777
			// Display initially when Consignor Code is one. 			
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
				//#CM566778
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

	//#CM566779
	/**
	 * Method which sets the editablity of the button of Preset area.<BR>
	 * <BR>
	 * Outline : Receive false or true and set editability of the button. <BR>
	 * <BR>
	 * @param  arg boolean Editability of the button. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setBtnTrueFalse(boolean arg) throws Exception
	{
		//#CM566780
		// Button of Preset area
		btn_InventoryResult.setEnabled(arg);
		btn_AllCheck.setEnabled(arg);
		btn_AllCheckClear.setEnabled(arg);
		btn_ListClear.setEnabled(arg);
	}

	//#CM566781
	/**
	 * Method which clears the Preset area.<BR>
	 * <BR>
	 * Outline : Invalidate Preset area and invalidate the button in the Clear doing and the Preset area.<BR>
	 * Or, Set the cursor in Worker Code. <BR>
	 * <BR>
	 * @throws Exception It reports on all exceptions. 
	 */
	private void listClear() throws Exception
	{
		//#CM566782
		// Delete all line information.
		lst_SInventoryComplete.clearRow();
		//#CM566783
		// Erase the Consignor part of Preset area. 
		txt_SRConsignorCode.setText("");
		txt_SRConsignorName.setText("");
		//#CM566784
		// Invalidate Button of Preset area. 
		setBtnTrueFalse(false);
		//#CM566785
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
	}
	
	//#CM566786
	/**
	 * Method to do the retrieval result to Preset area in the mapping. <BR>
	 * <BR>
	 * Outline : Use it to display the retrieval result of acquisition from the schedule in Preset area. <BR>
	 * Display Consignor Code, Consignor Name, and the list cell in Preset area. <BR>
	 * Moreover, set balloon information, and make the button in the Preset area effective. <BR>
	 * <BR>
	 * List of row number of list cell
	 * <DIR>
	 *     0:Last updated date and time(HIDDEN) <BR>
	 *     1:Final check box <BR>
	 *     2:Location <BR>
	 *     3:Item Code <BR>
	 *     4:Packed qty per case <BR>
	 *     5:Inventory Case qty <BR>
	 *     6:Stock Case qty <BR>
	 *     7:Expiry Date <BR>
	 *     8:Worker Code <BR>
	 *     9:Item Name <BR>
	 *     10:Packed qty per bundle <BR>
	 *     11:Inventory Piece qty <BR>
	 *     12:Stock Piece qty <BR>
	 *     13:Worker Name <BR>
	 * </DIR>
	 * <BR>
	 * @param param StorageSupportParameter[] Information to display in Preset area. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private void addList(StorageSupportParameter[] param) throws Exception
	{
		//#CM566787
		// Display the retrieval result in Preset area. 
		//#CM566788
		// Consignor Code
		txt_SRConsignorCode.setText(this.getViewState().getString(CONSIGNORCODE));
		//#CM566789
		// Consignor Name
		txt_SRConsignorName.setText(param[0].getConsignorName());

		//#CM566790
		// Consignor Name
		String label_suppliername = DisplayText.getText("LBL-W0026");
		//#CM566791
		// Item Name
		String label_itemname = DisplayText.getText("LBL-W0103");
		//#CM566792
		// Packed qty per case
		String label_caseqty = DisplayText.getText("LBL-W0340");
		//#CM566793
		// Expiry Date
		String label_usebydate = DisplayText.getText("LBL-W0270");
		//#CM566794
		// Worker Code
		String label_workercode = DisplayText.getText("LBL-W0274");
		//#CM566795
		// Worker Name
		String label_workername = DisplayText.getText("LBL-W0276");

		//#CM566796
		// The value is set in the list cell. 
		for (int i = 0; i < param.length; i++)
		{
			//#CM566797
			// The line is added to the list cell. 
			lst_SInventoryComplete.addRow();
			//#CM566798
			// Select the line which sets the value. 
			lst_SInventoryComplete.setCurrentRow(i + 1);
			//#CM566799
			// Location
			lst_SInventoryComplete.setValue(2, param[i].getLocation());
			//#CM566800
			// Item Code
			lst_SInventoryComplete.setValue(3, param[i].getItemCode());
			//#CM566801
			// Packed qty per case
			lst_SInventoryComplete.setValue(4, Formatter.getNumFormat(param[i].getEnteringQty()));
			//#CM566802
			// Inventory Case qty
			lst_SInventoryComplete.setValue(5, Formatter.getNumFormat(param[i].getInventoryCheckCaseQty()));
			//#CM566803
			// Stock Case qty
			lst_SInventoryComplete.setValue(6, Formatter.getNumFormat(param[i].getStockCaseQty()));
			//#CM566804
			// Expiry Date
			lst_SInventoryComplete.setValue(7, param[i].getUseByDate());
			//#CM566805
			// Worker Code
			lst_SInventoryComplete.setValue(8, param[i].getWorkerCode());
			//#CM566806
			// Item Name
			lst_SInventoryComplete.setValue(9, param[i].getItemName());
			//#CM566807
			// Packed qty per bundle
			lst_SInventoryComplete.setValue(10, Formatter.getNumFormat(param[i].getBundleEnteringQty()));
			//#CM566808
			// Packed Inventory Piece qty
			lst_SInventoryComplete.setValue(11, Formatter.getNumFormat(param[i].getInventoryCheckPieceQty()));
			//#CM566809
			// Stock Piece qty
			lst_SInventoryComplete.setValue(12, Formatter.getNumFormat(param[i].getStockPieceQty()));
			//#CM566810
			// Worker Name
			lst_SInventoryComplete.setValue(13, param[i].getWorkerName());
			
			//#CM566811
			//Set the tool tip
			ToolTipHelper toolTip = new ToolTipHelper();
			//#CM566812
			// Consignor Name
			toolTip.add(label_suppliername,param[i].getConsignorName());
			//#CM566813
			// Item Name
			toolTip.add(label_itemname,param[i].getItemName());
			//#CM566814
			// Packed qty per case
			toolTip.add(label_caseqty,
				Formatter.getNumFormat(param[i].getEnteringQty()));
			//#CM566815
			// Expiry Date
			toolTip.add(label_usebydate,param[i].getUseByDate());
			//#CM566816
			// Worker Code
			toolTip.add(label_workercode,param[i].getWorkerCode());
			//#CM566817
			// Worker Name
			toolTip.add(label_workername,param[i].getWorkerName());
				
			lst_SInventoryComplete.setToolTip(lst_SInventoryComplete.getCurrentRow(), toolTip.getText());
			
			//#CM566818
			// Set the HIDDEN item
			lst_SInventoryComplete.setValue(0, CollectionUtils.getConnectedString(setHidden(param[i])));

			
		}
		
		//#CM566819
		// Make the button of Preset effective.
		setBtnTrueFalse(true);

	}
	
	//#CM566820
	/**
	 * Return ArrayList which sets Concealed Item. <BR>
	 * <BR>
	 * @param param StorageSupportParameter Parameter including Concealed Item. 
	 * @return ArrayList which connected Concealed Item. 
	 */
	private ArrayList setHidden(StorageSupportParameter param)
	{
		//#CM566821
		// Making of Concealed Item character string
		ArrayList hiddenList = new ArrayList();
		
		//#CM566822
		// Work No.
		hiddenList.add(JOBNO, param.getJobNo());
		
		//#CM566823
		// Last updated date and time
		hiddenList.add(LASTUPDATE, WmsFormatter.getTimeStampString(param.getLastUpdateDate()));
		
		return hiddenList;
		
	}

}
//#CM566824
//end of class
