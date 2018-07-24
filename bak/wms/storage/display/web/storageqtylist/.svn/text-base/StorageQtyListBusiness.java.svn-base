// $Id: StorageQtyListBusiness.java,v 1.2 2006/12/07 08:57:11 suresh Exp $

//#CM573866
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.storageqtylist;
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
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststoragedate.ListStorageDateBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageresultlist.ListStorageResultListBusiness;
import jp.co.daifuku.wms.storage.schedule.StorageQtyListSCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM573867
/**
 * Designer : Yoshino<BR>
 * Maker : Yoshino <BR>
 * <BR>
 * The screen class which prints the stock results list.<BR>
 * Hand over Parameter to the schedule which prints the stock results list.<BR>
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
 * 			</DIR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/18</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:11 $
 * @author  $Author: suresh $
 */
public class StorageQtyListBusiness extends StorageQtyList implements WMSConstants
{
	//#CM573868
	// Class fields --------------------------------------------------

	//#CM573869
	/**
	 * Dialog call origin : Print button
	 */	
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";

	//#CM573870
	// Class variables -----------------------------------------------

	//#CM573871
	// Class method --------------------------------------------------

	//#CM573872
	// Constructors --------------------------------------------------

	//#CM573873
	// Public methods ------------------------------------------------

	//#CM573874
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
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		
		//#CM573875
		// Set Initial value in each input field. 
		//#CM573876
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM573877
		// Start Storage date
		txt_StartStorageDate.setText("");
		//#CM573878
		// End Storage date
		txt_EndStorageDate.setText("");
		//#CM573879
		// Item Code
		txt_ItemCode.setText("");
		//#CM573880
		// Case/Piece flag
		rdo_Cpf_All.setChecked(true);
		rdo_Cpf_Case.setChecked(false);
		rdo_Cpf_Piece.setChecked(false);
		rdo_Cpf_AppointOff.setChecked(false);
		
		//#CM573881
		// Cursor transition to Consignor Code
		setFocus(txt_ConsignorCode);
	}
	
	//#CM573882
	/**
	 * It is called before the call of each control event. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions.  
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM573883
			// Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM573884
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM573885
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}

	}
	
	//#CM573886
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
		//#CM573887
		// Parameter selected from list box is acquired. 
		//#CM573888
		// Consignor Code
		String consignorcode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM573889
		// Start Storage date
		String startstoragedate = param.getParameter(ListStorageDateBusiness.STARTSTORAGEDATE_KEY);
		//#CM573890
		// End Storage date
		String endstoragedate = param.getParameter(ListStorageDateBusiness.ENDSTORAGEDATE_KEY);
		//#CM573891
		// Item Code
		String itemcode = param.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM573892
		// Set the value when it is not empty. 
		//#CM573893
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM573894
		// Start Storage date
		if (!StringUtil.isBlank(startstoragedate))
		{
			txt_StartStorageDate.setText(startstoragedate);
		}
		//#CM573895
		// End Storage date
		if (!StringUtil.isBlank(endstoragedate))
		{
			txt_EndStorageDate.setText(endstoragedate);
		}
		//#CM573896
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM573897
		// Set focus in Consignor Code
		setFocus(txt_ConsignorCode);
	}
	
	//#CM573898
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
			//#CM573899
			// Set focus in Consignor Code
			setFocus(txt_ConsignorCode);

			//#CM573900
			// Check from which dialog return
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM573901
			// True when [Yes] is pressed in dialog
			//#CM573902
			// False when [No] is pressed in dialog
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM573903
			// End processing when [No] is pressed. 
			//#CM573904
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
			//#CM573905
			// Turn off the flag because the operation of the dialog ended. 
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM573906
		// Start Print scheduling.
		Connection conn = null;
		try
		{
			//#CM573907
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM573908
			// Set the input value in Parameter. 
			StorageSupportParameter[] param = new StorageSupportParameter[1];
			param[0] = createParameter();

			//#CM573909
			// Start scheduling.
			WmsScheduler schedule = new StorageQtyListSCH();
			schedule.startSCH(conn, param);
			
			//#CM573910
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
				//#CM573911
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
	//#CM573912
	// Package methods -----------------------------------------------

	//#CM573913
	// Protected methods ---------------------------------------------
	
	//#CM573914
	/**
	 * Generate the Parameter object which sets the input value of the input area. <BR>
	 * <BR>
	 * @return Parameter object which contains input value of input area
	 */
	protected StorageSupportParameter createParameter()
	{
		//#CM573915
		// Set schedule Parameter. 
		StorageSupportParameter param = new StorageSupportParameter();
		param = new StorageSupportParameter();
		
		//#CM573916
		// Consignor Code
		param.setConsignorCode(txt_ConsignorCode.getText());
		//#CM573917
		// Start Storage date
		param.setFromStorageDate(txt_StartStorageDate.getDate());
		//#CM573918
		// End Storage date
		param.setToStorageDate(txt_EndStorageDate.getDate());
		//#CM573919
		// Item Code
		param.setItemCode(txt_ItemCode.getText());	
		//#CM573920
		// Case/Piece flag				
		if (rdo_Cpf_All.getChecked())
		{
			param.setCasePieceflg(
				StorageSupportParameter.CASEPIECE_FLAG_ALL);
		}
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setCasePieceflg(
				StorageSupportParameter.CASEPIECE_FLAG_CASE);
		}
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setCasePieceflg(
				StorageSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setCasePieceflg(
			StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
		}			
		return param;
	}


	//#CM573921
	// Private methods -----------------------------------------------
	//#CM573922
	/**
	 * Method to acquire Consignor Code from the schedule.  <BR>
	 * <BR>
	 * Outline : Return Consignor Code acquired from the schedule. <BR>
	 * <DIR>
	 *   1.Return corresponding Consignor when the schedule result is not null. Return the empty string for null.  <BR>
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
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM573923
			// Acquire Consignor Code from the schedule. 
			WmsScheduler schedule = new StorageQtyListSCH();
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
			//#CM573924
			// Close the connection
			if (conn != null)
			{
				conn.close();
			}
		}
		return "";
	}
	//#CM573925
	// Event handler methods -----------------------------------------
	//#CM573926
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573927
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573928
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573929
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

	//#CM573930
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573931
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573932
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573933
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573934
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573935
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573936
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
	public void btn_PsearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM573937
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573938
		// Consignor Code
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM573939
		// Retrieval flag
		param.setParameter(
			ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY,
			StorageSupportParameter.SEARCH_STORAGE_RESULT);
		//#CM573940
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageconsignor/ListStorageConsignor.do",
			param,
			"/progress.do");		
	}

	//#CM573941
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573942
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573943
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartStorageDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573944
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartStorageDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573945
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchStartStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573946
	/** 
	 * It is called when the retrieval button of Start Storage date is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the list box of the stock day by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]<BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Start Storage date <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PsearchStartStorageDate_Click(ActionEvent e) throws Exception
	{
		//#CM573947
		//Set the search condition on the Start Storage date retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573948
		//Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY,txt_ConsignorCode.getText());
		//#CM573949
		// Start Storage date
		param.setParameter(ListStorageDateBusiness.STARTSTORAGEDATE_KEY,WmsFormatter.toParamDate(txt_StartStorageDate.getDate()));
		//#CM573950
		// Start flag (Range flag of stock day)
		param.setParameter(ListStorageDateBusiness.RANGESTORAGEDATE_KEY,StorageSupportParameter.RANGE_START);
		//#CM573951
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststoragedate/ListStorageDate.do",
			param,
			"/progress.do");
	}

	//#CM573952
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573953
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573954
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573955
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndStorageDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573956
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndStorageDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573957
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchEndStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573958
	/** 
	 * It is called when the retrieval button of End Storage date is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the list box of the stock day by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]<BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       End Storage date <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PsearchEndStorageDate_Click(ActionEvent e) throws Exception
	{
		//#CM573959
		// Set the search condition on the Location retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573960
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY,txt_ConsignorCode.getText());
		//#CM573961
		// End Storage date
		param.setParameter(ListStorageDateBusiness.ENDSTORAGEDATE_KEY,WmsFormatter.toParamDate(txt_EndStorageDate.getDate()));
		//#CM573962
		// End flag (Range flag of stock day)
		param.setParameter(ListStorageDateBusiness.RANGESTORAGEDATE_KEY,StorageSupportParameter.RANGE_END);
		//#CM573963
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststoragedate/ListStorageDate.do",
			param,
			"/progress.do");
	}

	//#CM573964
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573965
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573966
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573967
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573968
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573969
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573970
	/** 
	 * It is called when the retrieval button of Item Code is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the item list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]<BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *       Start Storage date <BR>
	 *       End Storage date <BR>
	 *       Item Code <BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PsearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM573971
		// Set the search condition on the Item retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573972
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM573973
		// Start Storage date
		param.setParameter(ListStorageDateBusiness.STARTSTORAGEDATE_KEY, WmsFormatter.toParamDate(txt_StartStorageDate.getDate()));
		//#CM573974
		// End Storage date
		param.setParameter(ListStorageDateBusiness.ENDSTORAGEDATE_KEY, WmsFormatter.toParamDate(txt_EndStorageDate.getDate()));
		//#CM573975
		// Item Code
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM573976
		// Retrieval flag
		param.setParameter(
			ListStorageItemBusiness.SEARCHITEM_KEY,
			StorageSupportParameter.SEARCH_STORAGE_RESULT);		
		//#CM573977
		// Processing Screen -> Result screen
		redirect(
			"/storage/listbox/liststorageitem/ListStorageItem.do",
			param,
			"/progress.do");
	}

	//#CM573978
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573979
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_All_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573980
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_All_Click(ActionEvent e) throws Exception
	{
	}

	//#CM573981
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Case_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573982
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Case_Click(ActionEvent e) throws Exception
	{
	}

	//#CM573983
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Piece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573984
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Piece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM573985
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_AppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573986
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_AppointOff_Click(ActionEvent e) throws Exception
	{
	}

  	//#CM573987
  	/** 
   	* 
	* @param e ActionEvent 
	* @throws Exception 
	*/
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}
	
	//#CM573988
	/** 
	 * It is called when the display button is pressed.<BR>
	 * <BR>
	 * Outline : Set input Item of Input area in Parameter, and display the stock results list box by the search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory input<BR>
	 * <BR>
	 * <DIR>
	 * 		Consignor Code*<BR>
	 * 		Start Storage plan date<BR>
	 * 		End Storage plan date<BR>
	 * 		Item Code<BR>
	 * 		Case/Piece flag*<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		//#CM573989
		// The instance to set the search condition is declared. 
		ForwardParameters forwardParam = new ForwardParameters();
		//#CM573990
		// Set Consignor Code
		forwardParam.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM573991
		// Start Storage date is set. 
		forwardParam.setParameter(ListStorageDateBusiness.STARTSTORAGEDATE_KEY, WmsFormatter.toParamDate(txt_StartStorageDate.getDate()));	
		//#CM573992
		// End Storage date is set. 
		forwardParam.setParameter(ListStorageDateBusiness.ENDSTORAGEDATE_KEY, WmsFormatter.toParamDate(txt_EndStorageDate.getDate()));	
		//#CM573993
		// Set Item Code
		forwardParam.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM573994
		// Case/Piece flag is set. 
		if (rdo_Cpf_All.getChecked())
		{
			forwardParam.setParameter(ListStorageResultListBusiness.CASEPIECEFLAG_KEY, StorageSupportParameter.CASEPIECE_FLAG_ALL);
		}
		else if (rdo_Cpf_Case.getChecked())
		{
			forwardParam.setParameter(ListStorageResultListBusiness.CASEPIECEFLAG_KEY, StorageSupportParameter.CASEPIECE_FLAG_CASE);
		}
		else if (rdo_Cpf_Piece.getChecked())
		{
			forwardParam.setParameter(ListStorageResultListBusiness.CASEPIECEFLAG_KEY, StorageSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			forwardParam.setParameter(ListStorageResultListBusiness.CASEPIECEFLAG_KEY, StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM573995
		// Display the stock results list box. 
		redirect("/storage/listbox/liststorageresultlist/ListStorageResultList.do", forwardParam, "/progress.do");
	}

	//#CM573996
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573997
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
		//#CM573998
		// Cursor transition to Consignor Code
		setFocus(txt_ConsignorCode);
		//#CM573999
		// Do the Input Check.
		if(!checkInputData())
		{
			return;
		}
		
		//#CM574000
		// Start Storage date is smaller than End Storage date. 
		if (!StringUtil.isBlank(txt_StartStorageDate.getDate())
			&& !StringUtil.isBlank(txt_EndStorageDate.getDate()))
		{
			if (txt_StartStorageDate.getDate().compareTo(txt_EndStorageDate.getDate()) > 0)
			{
				//#CM574001
				// 6023106=Starting storage date must precede the end storage date.
				message.setMsgResourceKey("6023106");
				return;
			}
		}
		
		Connection conn = null;
		
		try
		{
			//#CM574002
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			StorageSupportParameter param = createParameter();
			//#CM574003
			// Start scheduling.
			WmsScheduler schedule = new StorageQtyListSCH();
			
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM574004
				// MSG-W0061={0} data match. Do you print?
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM574005
				// Memorize the dialog display by Print button. 
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM574006
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
				//#CM574007
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


	//#CM574008
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM574009
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
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM574010
		// Set Initial value
		//#CM574011
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM574012
		// Start Storage date
		txt_StartStorageDate.setText("");
		//#CM574013
		// End Storage date
		txt_EndStorageDate.setText("");		
		//#CM574014
		// Item Code
		txt_ItemCode.setText("");
		//#CM574015
		// Case/Piece flag
		rdo_Cpf_All.setChecked(true);
		rdo_Cpf_Case.setChecked(false);
		rdo_Cpf_Piece.setChecked(false);
		rdo_Cpf_AppointOff.setChecked(false);
		//#CM574016
		// Set focus in Consignor Code
		setFocus(txt_ConsignorCode);
	}

	//#CM574017
	/** 
	 * Method for Doing the input check. <BR>
	 * <BR>
	 * Outline : Return true if no mistake is found and false if the mistake are found in the input. <BR>
	 * <BR>
	 * @return Result of Input Check(Normal : true Abnormal : false)
	 * @throws Exception It reports on all exceptions. 
	 */
	private boolean checkInputData() throws Exception
	{
		//#CM574018
		// Input Check
		//#CM574019
		// Consignor Code
		txt_ConsignorCode.validate();
		//#CM574020
		// Start Storage date
		txt_StartStorageDate.validate(false);
		//#CM574021
		// End Storage date
		txt_EndStorageDate.validate(false);		
		//#CM574022
		// Item Code
		txt_ItemCode.validate(false);
	
		return true;
	}



}
//#CM574023
//end of class
