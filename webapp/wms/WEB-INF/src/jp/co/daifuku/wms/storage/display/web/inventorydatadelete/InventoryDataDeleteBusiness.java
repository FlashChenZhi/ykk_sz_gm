// $Id: InventoryDataDeleteBusiness.java,v 1.2 2006/12/07 08:57:49 suresh Exp $

//#CM566829
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.inventorydatadelete;
import java.sql.Connection;
import java.sql.SQLException;

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
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.storage.display.web.listbox.listinventorylocation.ListInventoryLocationBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.schedule.InventoryDeleteSCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM566830
/**
 * Designer : H.Akiyama <BR>
 * Maker    : H.Akiyama <BR>
 * <BR>
 * The Inventory information deletion class. <BR>
 * Set the content input from the screen in Parameter, pass to the schedule, and do the Inventory information deletion processing. <BR>
 * Receive True when the result is received from the schedule, and processing Completes normally, <BR>
 * and false when the schedule does not do Completed because of the condition error etc.<BR>
 * The result of the schedule outputs the message acquired from the schedule to the screen. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Inventory data deletion button is pressed (<CODE>btn_InvntryItemDlt_Click</CODE> Method) <BR>
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
 *    [Return Data] <BR>
 * <BR>
 *    <DIR>
 * 		Processing result(true or false)<BR>
 * 		Message<BR>
 *    </DIR>
 * </DIR>
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
public class InventoryDataDeleteBusiness extends InventoryDataDelete implements WMSConstants
{
	//#CM566831
	// Class fields --------------------------------------------------

	//#CM566832
	// Class variables -----------------------------------------------

	//#CM566833
	// Class method --------------------------------------------------

	//#CM566834
	// Constructors --------------------------------------------------

	//#CM566835
	// Public methods ------------------------------------------------

	//#CM566836
	/**
	 * It is called when the screen is read.<BR>
	 * <BR>
	 * Outline:Display initial data in the screen. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Display the title. <BR>
	 *    2.Initialize the input area. <BR>
	 *    3.Set the cursor in Worker Code. <BR>
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
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		
		//#CM566837
		// Set Initial value in each input field. 
		txt_WorkerCode.setText("");
		txt_Password.setText("");
		//#CM566838
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM566839
		// Start Location
		txt_StartLocation.setText("");
		//#CM566840
		// End Location
		txt_EndLocation.setText("");

		//#CM566841
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
	}

	//#CM566842
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
			//#CM566843
			//Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM566844
			//Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM566845
			//Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
		
		//#CM566846
		// At present, the Message switch by the Consignor Code presence is none. 
		//#CM566847
		// Display the dialog. MSG-W0045=Do you delete matching results of Inventory Check Data?
		btn_InvntryItemDlt.setBeforeConfirm("MSG-W0045");

	}

	//#CM566848
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
		//#CM566849
		// Parameter selected from list box is acquired. 
		//#CM566850
		// Consignor Code
		String consignorCode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM566851
		// Start Location
		String startLocation = param.getParameter(ListInventoryLocationBusiness.STARTLOCATION_KEY);
		//#CM566852
		// End Location
		String endLocation = param.getParameter(ListInventoryLocationBusiness.ENDLOCATION_KEY);

		//#CM566853
		// Consignor Code
		if (!StringUtil.isBlank(consignorCode))
		{
			txt_ConsignorCode.setText(consignorCode);
		}
		//#CM566854
		// Start Location
		if (!StringUtil.isBlank(startLocation))
		{
			txt_StartLocation.setText(startLocation);
		}
		//#CM566855
		// End Location
		if (!StringUtil.isBlank(endLocation))
		{
			txt_EndLocation.setText(endLocation);
		}
		
		//#CM566856
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
	}


	//#CM566857
	// Package methods -----------------------------------------------

	//#CM566858
	// Protected methods ---------------------------------------------

	//#CM566859
	// Private methods -----------------------------------------------

	//#CM566860
	// Event handler methods -----------------------------------------
	//#CM566861
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566862
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566863
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566864
	/**
	 * It is called when the menu button is pressed.<BR>
	 * <BR>
	 * Outline : Change to the menu panel.
	 *
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		//#CM566865
		// Change to the menu panel.
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM566866
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566867
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566868
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM566869
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM566870
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM566871
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566872
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566873
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM566874
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM566875
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM566876
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566877
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566878
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM566879
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM566880
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM566881
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566882
	/**
	 * It is called when the retrieval of Consignor Code button is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Consignor list box by the search condition.<BR>
	 * <BR>
	 *   [Parameter] *Mandatory input<BR>
	 * <BR>
	 *     <DIR>
	 *     Consignor Code <BR>
	 *     </DIR>
	 * <BR>
	 *
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		//#CM566883
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM566884
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM566885
		// Retrieval flag : Inventory work information retrieval
		param.setParameter(ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY,
			StorageSupportParameter.SEARCH_INVENTORY);		
		//#CM566886
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageconsignor/ListStorageConsignor.do", param,	"/progress.do");		
	}

	//#CM566887
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566888
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566889
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM566890
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM566891
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM566892
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStartLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566893
	/**
	 * It is called when the retrieval of Start Location button is pressed.
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the location list box by the search condition. <BR>
	 * <BR>
	 *   [Parameter] *Mandatory input<BR>
	 * <BR>
	 *     <DIR>
	 *     Consignor Code <BR>
	 *     Start Location <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchStartLct_Click(ActionEvent e) throws Exception
	{
		//#CM566894
		//	Set the search condition on the Start Location retrieval screen. 
		ForwardParameters param = new ForwardParameters();

		//#CM566895
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM566896
		// Retrieval flag : For Inventory
		param.setParameter(ListInventoryLocationBusiness.SEARCHLOCATION_KEY, StorageSupportParameter.SEARCH_INVENTORY);
		//#CM566897
		// Start Location
		param.setParameter(ListInventoryLocationBusiness.STARTLOCATION_KEY,	txt_StartLocation.getText());
		//#CM566898
		// Set Start flag
		param.setParameter(ListInventoryLocationBusiness.RANGELOCATION_KEY,
			StorageSupportParameter.RANGE_START);
		//#CM566899
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/listinventorylocation/ListInventoryLocation.do", param, "/progress.do");				
	}

	//#CM566900
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromToLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566901
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566902
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566903
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM566904
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM566905
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM566906
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEndLct_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566907
	/**
	 * It is called when the retrieval of End Location button is pressed.
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the location list box by the search condition. <BR>
	 * <BR>
	 *   [Parameter] *Mandatory input<BR>
	 * <BR>
	 *     <DIR>
	 *     Consignor Code <BR>
	 *     End Location <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PSearchEndLct_Click(ActionEvent e) throws Exception
	{
		//#CM566908
		//	Set the search condition on the End Location retrieval screen. 
		ForwardParameters param = new ForwardParameters();

		//#CM566909
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM566910
		// Retrieval flag : For Inventory
		param.setParameter(ListInventoryLocationBusiness.SEARCHLOCATION_KEY, StorageSupportParameter.SEARCH_INVENTORY);
		//#CM566911
		// End Location
		param.setParameter(ListInventoryLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getText());
		//#CM566912
		// Set the End flag
		param.setParameter(ListInventoryLocationBusiness.RANGELOCATION_KEY,
			StorageSupportParameter.RANGE_END);
		//#CM566913
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/listinventorylocation/ListInventoryLocation.do", param, "/progress.do");		
	}

	//#CM566914
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_InvntryItemDlt_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566915
	/**
	 * It is called when the Inventory information deletion button is pressed. <BR>
	 * <BR>
	 * Outline:Delete Inventory information by information on the input area.  <BR>
	 * <BR>
	 * <DIR>
	 *     1.Set the cursor in Worker Code.  <BR>
	 *     2.Check the Input item of Input area.<BR>
	 *       <DIR>
	 *       (Mandatory input, Number of characters, Character attribute)  <BR>
	 *       (Start Location must be lesser than End Location.) <BR>
	 *       </DIR>
	 *     3.Start Schedule. <BR>
	 *     4.Display Message acquired from the schedule.  <BR>
	 *     5.Maintain the content of the input area.  <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_InvntryItemDlt_Click(ActionEvent e) throws Exception
	{
		
		//#CM566916
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);
		
		//#CM566917
		// Do the input check (Format, Mandatory, Restricted characters) 
		txt_WorkerCode.validate();
		txt_Password.validate();
		
		txt_ConsignorCode.validate(false);
		txt_StartLocation.validate(false);
		txt_EndLocation.validate(false);
		
		//#CM566918
		// Output the error message when End Location is not less than Start Location.
		if (!StringUtil.isBlank(txt_StartLocation.getText())
			&& !StringUtil.isBlank(txt_EndLocation.getText()))
		{
			if (txt_StartLocation.getText().compareTo(txt_EndLocation.getText()) > 0)
			{
				//#CM566919
				// 6023057 = Please enter {1} or greater for {0}.
				//#CM566920
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
			//#CM566921
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM566922
			// Declaration of Parameter
			StorageSupportParameter param[] = new StorageSupportParameter[1];
			
			param[0] = new StorageSupportParameter();
			
			//#CM566923
			// Set schedule Parameter. 
			//#CM566924
			// Worker Code
			param[0].setWorkerCode(txt_WorkerCode.getText());
			//#CM566925
			// Password
			param[0].setPassword(txt_Password.getText());
			//#CM566926
			// Consignor Code
			param[0].setConsignorCode(txt_ConsignorCode.getText());
			//#CM566927
			// Start Location
			param[0].setFromLocation(txt_StartLocation.getText());
			//#CM566928
			// End Location
			param[0].setToLocation(txt_EndLocation.getText());
			
			//#CM566929
			// Terminal No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param[0].setTerminalNumber(userHandler.getTerminalNo());

			//#CM566930
			// Declaration of schedule
			WmsScheduler schedule = new InventoryDeleteSCH();
			//#CM566931
			// Start scheduling.
			if(schedule.startSCH(conn, param))
			{
				//#CM566932
				// Commit when processing ends normally
				conn.commit();
			}
			else
			{
				//#CM566933
				// Rollback when processing terminates abnormally
				conn.rollback();
			}

			//#CM566934
			// Display Message from the schedule. 
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
				//#CM566935
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

	//#CM566936
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM566937
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
		//#CM566938
		// Set Initial value in each input field. 
		//#CM566939
		//Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM566940
		// Start Location
		txt_StartLocation.setText("");
		//#CM566941
		// End Location
		txt_EndLocation.setText("");

		//#CM566942
		// Set the cursor in Worker Code. 
		setFocus(txt_WorkerCode);		
		
	}

	//#CM566943
	// Private methods -----------------------------------------------
	//#CM566944
	/**
	 * Method to acquire Consignor Code from the schedule. <BR>
	 * <BR>
	 * Outline : Return Consignor Code acquired from the schedule. <BR>
	 * <DIR>
	 *   1.Return a pertinent shipper when there is only one Consignor Code. Return null when it is not so.  <BR>
	 * <DIR>
	 * @return Consignor Code
	 * @throws Exception It reports on all exceptions. 
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM566945
			// Acquire the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM566946
			// Declaration of Parameter
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM566947
			// Acquire Consignor Code from the schedule. 
			
			WmsScheduler schedule = new InventoryDeleteSCH();
			//#CM566948
			// Start Schedule.
			param = (StorageSupportParameter) schedule.initFind(conn, param);

			//#CM566949
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
				//#CM566950
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


}
//#CM566951
//end of class
