// $Id: InventoryMaintenanceBusiness.java,v 1.2 2006/12/07 08:57:46 suresh Exp $

//#CM567539
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.inventorymaintenance;
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
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.storage.display.web.listbox.listinventorylocation.ListInventoryLocationBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.schedule.InventoryMaintenanceSCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM567540
/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * The Inventory (Essential information Setting) class. <BR>
 * The content input from the screen is set in ViewState, and it changes to Inventory (setting) screen based on the Parameter. <BR>
 * Receive true when it is normal in Input Check and receive false at the condition error. <BR>
 * Result of Input Check is, Output the Message acquired from the schedule to the screen. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.processing when Next button is pressed (<CODE>btn_Next_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 		Set the content input from the input area in Parameter, and the schedule checks the input condition based on the Parameter. <BR>
 * 		Receive the result from the schedule, receive true normally, and receive false at the error. <BR>
 * 		The result of the schedule outputs the message acquired from the schedule to the screen. <BR>
 * 		Input Item of Input area is set in ViewState and it changes to Inventory (setting) screen based on the Parameter when the result is true. <BR>
 * 		<BR>
 * 		[ViewState information]*Mandatory input<BR>
 * 		<BR>
 * 		<DIR>
 * 			Worker Code* <BR>
 * 			Password* <BR>
 * 			Consignor Code* <BR>
 * 			Start Location <BR>
 * 			End Location <BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/13</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:46 $
 * @author  $Author: suresh $
 */
public class InventoryMaintenanceBusiness extends InventoryMaintenance implements WMSConstants
{
	//#CM567541
	// Class fields --------------------------------------------------
	//#CM567542
	/**
	 * The key used to hand over Title For ViewState.
	 */
	public static final String TITLE_KEY = "TITLE_KEY";

	//#CM567543
	/**
	 * The key used to hand over Worker Code For ViewState.
	 */
	public static final String WORKERCODE_KEY = "WORKERCODE_KEY";

	//#CM567544
	/**
	 * The key used to hand over Password For ViewState.
	 */
	public static final String PASSWORD_KEY = "PASSWORD_KEY";

	//#CM567545
	/**
	 * The key used to hand over Consignor Code For ViewState.
	 */
	public static final String CONSIGNORCODE_KEY = "CONSIGNORCODE_KEY";

	//#CM567546
	/**
	 * Start The key used to hand over Location for ViewState.
	 */
	public static final String STARTLOCATION_KEY = "STARTLOCATION_KEY";

	//#CM567547
	/**
	 * End The key used to hand over Location for ViewState.
	 */
	public static final String ENDLOCATION_KEY = "ENDLOCATION_KEY";
	
	//#CM567548
	// Class variables -----------------------------------------------

	//#CM567549
	// Class method --------------------------------------------------

	//#CM567550
	// Constructors --------------------------------------------------

	//#CM567551
	// Public methods ------------------------------------------------

	//#CM567552
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
	 * 				Start Location*1[None]<BR>
	 * 				End Location*1[None]<BR>
	 * 				<BR>
	 * 				*1<BR>
	 * 				Set the value of ViewState information when returning from the second screen. <BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM567553
		// Title
		if (!StringUtil.isBlank(this.viewState.getString(TITLE_KEY)))
		{
			lbl_SettingName.setResourceKey(this.viewState.getString(TITLE_KEY));
		}

		//#CM567554
		// The tab of Essential information Setting put out first.
		tab_Inventory.setSelectedIndex(1);

		//#CM567555
		// Display initial. 
		setFirstDisp();

		//#CM567556
		// Set the value if the value is set in ViewState when the Return button is pressed. 
		String workercode = this.getViewState().getString(WORKERCODE_KEY);
		String password = this.getViewState().getString(PASSWORD_KEY);
		String consignorcode = this.getViewState().getString(CONSIGNORCODE_KEY);
		String startlocation = this.getViewState().getString(STARTLOCATION_KEY);
		String endlocation = this.getViewState().getString(ENDLOCATION_KEY);

		//#CM567557
		// Worker Code
		if (!StringUtil.isBlank(workercode))
		{
			txt_WorkerCode.setText(workercode);
		}

		//#CM567558
		// Password
		if (!StringUtil.isBlank(password))
		{
			txt_Password.setText(password);
		}

		//#CM567559
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}

		//#CM567560
		// Start Location
		if (!StringUtil.isBlank(startlocation))
		{
			txt_StartLocation.setText(startlocation);
		}

		//#CM567561
		// End Location
		if (!StringUtil.isBlank(endlocation))
		{
			txt_EndLocation.setText(endlocation);
		}
	}

	//#CM567562
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
			//#CM567563
			//Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM567564
			//Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM567565
			//Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
	}

	//#CM567566
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
		//#CM567567
		// Parameter selected from list box is acquired. 
		//#CM567568
		// Consignor Code
		String consignorcode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM567569
		// Start Location
		String startlocation = param.getParameter(ListInventoryLocationBusiness.STARTLOCATION_KEY);
		//#CM567570
		// End Location
		String endlocation = param.getParameter(ListInventoryLocationBusiness.ENDLOCATION_KEY);

		//#CM567571
		// Set the value when it is not empty. 
		//#CM567572
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM567573
		// Start Location
		if (!StringUtil.isBlank(startlocation))
		{
			txt_StartLocation.setText(startlocation);
		}
		//#CM567574
		// End Location
		if (!StringUtil.isBlank(endlocation))
		{
			txt_EndLocation.setText(endlocation);
		}

		//#CM567575
		// Focus is set in Worker Code.
		setFocus(txt_WorkerCode);
	}

	//#CM567576
	// Package methods -----------------------------------------------

	//#CM567577
	// Protected methods ---------------------------------------------
	//#CM567578
	/**
	 * Do the Input Check.<BR>
	 * <BR>
	 * Outline : Set Message and Return false when abnormality is found in the input. <BR>
	 * <BR>
	 * @return Result of Input Check (true:Normal false:Abnormal) 
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
		if (!checker.checkContainNgText(txt_ConsignorCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		return true;
		
	}

	//#CM567579
	// Private methods -----------------------------------------------
	//#CM567580
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
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM567581
			// Acquire Consignor Code from the schedule. 
			WmsScheduler schedule = new InventoryMaintenanceSCH();
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
			//#CM567582
			// Close the connection
			if (conn != null)
			{
				conn.close();
			}
		}
		return "";
	}
	
	//#CM567583
	/**
	 * This Method is called during the initial display of a screen and the Clear processing. <BR>
	 * Outline:Initialize the input area. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Initialize the input area. <BR>
	 *    2.Set the cursor in Worker Code. <BR>
	 * </DIR>
	 * <BR>
	 * Item[Initial value] <BR>
	 * <DIR>
	 * 		Worker Code[None] <BR>
	 * 		Password[None] <BR>
	 * 		Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 		Start Location[None] <BR>
	 * 		End Location[None] <BR>
	 * </DIR>
	 * <BR>
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setFirstDisp() throws Exception
	{
		//#CM567584
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM567585
		// Start Location
		txt_StartLocation.setText("");
		//#CM567586
		// End Location
		txt_EndLocation.setText("");
		//#CM567587
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
	}
	//#CM567588
	// Event handler methods -----------------------------------------
	//#CM567589
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567590
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567591
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567592
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

	//#CM567593
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567594
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567595
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM567596
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM567597
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM567598
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567599
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567600
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM567601
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM567602
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM567603
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567604
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567605
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM567606
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM567607
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM567608
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567609
	/**
	 * It is called when the retrieval of Consignor Code button is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Consignor list box by the search condition.<BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
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
		//#CM567610
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM567611
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM567612
		// Retrieval flag
		param.setParameter(
			ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY,
			StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK);
		//#CM567613
		// Area type flag (Save AS/RS when you display the list. ) 
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM567614
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageconsignor/ListStorageConsignor.do",
			param,
			"/progress.do");
	}

	//#CM567615
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_StartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567616
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567617
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM567618
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM567619
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM567620
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchStLoc_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567621
	/**
	 * It is called when the retrieval of Start Location button is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the location list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *		Consignor Code <BR>
	 * 		Start Location <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchStLoc_Click(ActionEvent e) throws Exception
	{
		//#CM567622
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM567623
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM567624
		// Start Location
		param.setParameter(
			ListInventoryLocationBusiness.STARTLOCATION_KEY,
			txt_StartLocation.getText());
		//#CM567625
		// Range flag
		param.setParameter(
			ListInventoryLocationBusiness.RANGELOCATION_KEY,
			StorageSupportParameter.RANGE_START);
		//#CM567626
		// Retrieval flag
		param.setParameter(
			ListInventoryLocationBusiness.SEARCHLOCATION_KEY,
			StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK);
		//#CM567627
		// Area type flag (Save AS/RS when you display the list. ) 
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM567628
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/listinventorylocation/ListInventoryLocation.do",
			param,
			"/progress.do");
	}

	//#CM567629
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567630
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_EndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567631
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567632
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM567633
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM567634
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM567635
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchEndLoc_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567636
	/**
	 * It is called when the retrieval of End Location button is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the location list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter] *Mandatory input<BR>
	 *    <DIR>
	 *		Consignor Code <BR>
	 * 		End Location <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchEndLoc_Click(ActionEvent e) throws Exception
	{
		//#CM567637
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM567638
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM567639
		// End Location
		param.setParameter(
			ListInventoryLocationBusiness.ENDLOCATION_KEY,
			txt_EndLocation.getText());
		//#CM567640
		// Range flag
		param.setParameter(
			ListInventoryLocationBusiness.RANGELOCATION_KEY,
			StorageSupportParameter.RANGE_END);
		//#CM567641
		// Retrieval flag
		param.setParameter(
			ListInventoryLocationBusiness.SEARCHLOCATION_KEY,
			StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK);
		//#CM567642
		// Area type flag (Save AS/RS when you display the list. ) 
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM567643
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/listinventorylocation/ListInventoryLocation.do",
			param,
			"/progress.do");
	}

	//#CM567644
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Next_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567645
	/**
	 * It is called when the Next button is pressed.<BR>
	 * <BR>
	 * Outline : It changes to Inventory (setting) screen contingent on input Item of Input area. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Check Input area input Item. (Mandatory input, Number of characters, Character attribute)<BR>
	 *    2.Start Schedule.<BR>
	 * 	  <DIR>
	 *   	[Parameter]*Mandatory input<BR>
	 *   	<DIR>
	 * 			Worker Code* <BR>
	 * 			Password* <BR>
	 * 			Consignor Code* <BR>
	 * 			Start Location <BR>
	 * 			End Location <BR>
	 *      </DIR>
	 *    </DIR>
	 *    3-1.If the result of the schedule is true and input Item are stored in ViewState, and it changes to Inventory (setting) screen. <BR>
	 *    3-2.When the result of the schedule is false, Output the Message acquired from the schedule to the screen.  <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Next_Click(ActionEvent e) throws Exception
	{
		//#CM567646
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);

		//#CM567647
		// Mandatory Input check
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		//#CM567648
		// Pattern match character
		txt_StartLocation.validate(false);
		txt_EndLocation.validate(false);

		//#CM567649
		// Input character check for eWareNavi
		if (!checkContainNgText())
		{
			return;
		}

		//#CM567650
		// Start Location must be lesser than End Location.
		if (!StringUtil.isBlank(txt_StartLocation.getText())
			&& !StringUtil.isBlank(txt_EndLocation.getText()))
		{
			if (txt_StartLocation.getText().compareTo(txt_EndLocation.getText()) > 0)
			{
				//#CM567651
				// 6023124=Starting location No. must precede the end location No.
				message.setMsgResourceKey("6023124");
				return;
			}
		}

		Connection conn = null;

		try
		{
			//#CM567652
			// Set in schedule Parameter.
			StorageSupportParameter param = new StorageSupportParameter();
			
			//#CM567653
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM567654
			// Password
			param.setPassword(txt_Password.getText());

			//#CM567655
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM567656
			// Start Location
			param.setFromLocation(txt_StartLocation.getText());
			//#CM567657
			// End Location
			param.setToLocation(txt_EndLocation.getText());

			//#CM567658
			// Connection acquisition
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new InventoryMaintenanceSCH();

			if (schedule.nextCheck(conn, param))
			{
				//#CM567659
				// The value is set in ViewState when the result of Check is true, and it changes to the next screen. 
				//#CM567660
				// Title
				this.getViewState().setString(TITLE_KEY, lbl_SettingName.getResourceKey());
				//#CM567661
				// Worker Code
				this.getViewState().setString(WORKERCODE_KEY, txt_WorkerCode.getText());
				//#CM567662
				// Password
				this.getViewState().setString(PASSWORD_KEY, txt_Password.getText());
				//#CM567663
				// Consignor Code
				this.getViewState().setString(CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
				//#CM567664
				// Start Location
				this.getViewState().setString(STARTLOCATION_KEY, txt_StartLocation.getText());
				//#CM567665
				// End Location
				this.getViewState().setString(ENDLOCATION_KEY, txt_EndLocation.getText());
				//#CM567666
				// Essential information Setting Screen->Detailed information Add screen
				forward("/storage/inventorymaintenance/InventoryMaintenance2.do");
			}
			else
			{
				//#CM567667
				// Set Message
				message.setMsgResourceKey(schedule.getMessage());
			}
		}
		catch (Exception ex)
		{
			//#CM567668
			// Connection Rollback
			if (conn != null)
			{

			}
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM567669
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

	//#CM567670
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM567671
	/**
	 * It is called when the clear button is pressed.<BR>
	 * <BR>
	 * Outline : Clear the Input area.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Initialize the input area. <BR>
	 * </DIR>
	 * <BR>
	 * Item Name[Initial value]<BR>
	 * <DIR>
	 * 		Worker Code[As it is]<BR>
	 * 		Password[As it is]<BR>
	 * 		Consignor Code[Display only in case of one in Consignor retrieval result is available]<BR>
	 * 		Start Location[None]<BR>
	 * 		End Location[None]<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM567672
		// Do the Clear Process.
		setFirstDisp();
	}
}
//#CM567673
//end of class
