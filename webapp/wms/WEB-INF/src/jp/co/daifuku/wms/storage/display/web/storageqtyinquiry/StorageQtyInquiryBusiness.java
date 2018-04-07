// $Id: StorageQtyInquiryBusiness.java,v 1.2 2006/12/07 08:57:12 suresh Exp $

//#CM573659
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.storageqtyinquiry;
import java.sql.Connection;
import java.sql.SQLException;

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
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststoragedate.ListStorageDateBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.schedule.StorageQtyInquirySCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM573660
/**
 * Designer : T.Yoshino<BR>
 * Maker : T.Yoshino<BR>
 * <BR>
 * The stock results inquiry screen class. <BR>
 * The content input from the screen is set in Parameter, and the schedule retrieves data for the display based on the Parameter. <BR>
 * Receive Preset area Output data from the schedule and output it to Preset area.<BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Display button is pressed(<CODE>btn_View_Click</CODE>)<BR>
 * <BR>
 * <DIR>
 *   The content input from the screen is set in Parameter, and the schedule retrieves data for the display based on the Parameter. <BR>
 *   Receive empty array of <CODE>Parameter</CODE> when pertinent data is not found. <BR>
 *   Moreover, receive null when the condition error etc. occur. <BR>
 * 	<BR>
 * 	[Parameter] *Mandatory input<BR>
 * 	<DIR>
 *		Consignor Code*<BR>
 *		Start Storage date<BR>
 *		End Storage date<BR>
 *		Item Code<BR>
 *		Case/Piece flag*<BR>
 *		The order of display*<BR>
 *	</DIR>
 * 	<BR>
 *  [Output data] <BR>
 * 	<DIR>
 *		Storage date<BR>
 *		Storage plan date<BR>
 *		Item Code <BR>
 *		Item Name <BR>
 *		Flag <BR>
 *		Storage Location<BR>
 *		Packed qty per case <BR>
 *		Packed qty per bundle <BR>
 *		Work plan case qty<BR>
 *		Work plan piece qty<BR>
 *		Result Case qty<BR>
 *		Result Piece qty<BR>
 *		Shortage Case qty<BR>
 *		Shortage Piece qty<BR>
 *		CaseITF <BR>
 *		Bundle ITF <BR>
 *		Expiry Date<BR>
 *		Worker Code<BR>
 *		Worker Name <BR>
 * 	</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/15</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:12 $
 * @author  $Author: suresh $
 */
public class StorageQtyInquiryBusiness extends StorageQtyInquiry implements WMSConstants
{
	//#CM573661
	// Class fields --------------------------------------------------

	//#CM573662
	// Class variables -----------------------------------------------

	//#CM573663
	// Class method --------------------------------------------------

	//#CM573664
	// Constructors --------------------------------------------------

	//#CM573665
	// Public methods ------------------------------------------------

	//#CM573666
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Outline:Display initial data in the screen. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Initialize the input area. <BR>
	 * 		2.Clear the list cell and Each Read only Item of Preset area. <BR>
	 * 		<BR>
	 * 		Item[Initial value] <BR>
	 * 		<DIR>
	 * 			Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Start Storage plan date[None] <BR>
	 * 			End Storage plan date[None] <BR>
	 * 			Item Code[None] <BR>
	 * 			Case/Piece flag[All] <BR>
	 * 			The order of display[Storage date order]<BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		setInitDsp();

		//#CM573667
		// Consignor Code (Preset) 
		txt_RConsignorCode.setText("");
		//#CM573668
		// Consignor Name (Preset) 
		txt_RConsignorName.setText("");
		//#CM573669
		// List cell
		lst_SStorageQtyInquiry.clearRow();
	}
	
	//#CM573670
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
			//#CM573671
			//Parameter is acquired. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM573672
			//Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM573673
			//Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
	}
	
	//#CM573674
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
		//#CM573675
		// Parameter selected from list box is acquired. 
		//#CM573676
		// Consignor Code
		String consignorcode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM573677
		// Start Storage date
		String startstoragedate = param.getParameter(ListStorageDateBusiness.STARTSTORAGEDATE_KEY);
		//#CM573678
		// End Storage date
		String endstoragedate = param.getParameter(ListStorageDateBusiness.ENDSTORAGEDATE_KEY);
		//#CM573679
		// Item Code
		String itemcode = param.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		//#CM573680
		// Set the value when it is not empty. 
		//#CM573681
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM573682
		// Start Storage date
		if (!StringUtil.isBlank(startstoragedate))
		{
			txt_StartStorageDate.setText(startstoragedate);
		}
		//#CM573683
		// End Storage date
		if (!StringUtil.isBlank(endstoragedate))
		{
			txt_EndStorageDate.setText(endstoragedate);
		}
		//#CM573684
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}

		//#CM573685
		// Set focus in Consignor Code
		setFocus(txt_ConsignorCode);
	}
	//#CM573686
	// Package methods -----------------------------------------------

	//#CM573687
	// Protected methods ---------------------------------------------

	//#CM573688
	// Private methods -----------------------------------------------
	//#CM573689
	/**
	 * Method to acquire Consignor Code from the schedule.  <BR>
	 * <BR>
	 * Outline : Return Consignor Code acquired from the schedule. <BR>
	 * <DIR>
	 *   1.Return corresponding Consignor when the schedule result is not null. Return the empty string for null.  <BR>
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
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StorageSupportParameter param = new StorageSupportParameter();

			//#CM573690
			// Acquire Consignor Code from the schedule. 
			WmsScheduler schedule = new StorageQtyInquirySCH();
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
			//#CM573691
			// Close the connection
			if (conn != null)
			{
				conn.close();
			}
		}
		return "";
	}
	
	//#CM573692
	/**
	 * Clear the Input area.<BR>
	 * <BR>
	 * Outline : The cursor is set in Consignor Code, and Clears each Item of Input area. <BR>
	 * <BR>
	 * <DIR>
	 * 		Item[Initial value] <BR>
	 * 		<DIR>
	 * 			Consignor Code[Display only in case of one in Consignor retrieval result is available] <BR>
	 * 			Start Storage plan date[None] <BR>
	 * 			End Storage plan date[None] <BR>
	 * 			Item Code[None] <BR>
	 * 			Case/Piece flag[All] <BR>
	 * 			The order of display[Storage date order]<BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @throws Exception It reports on all exceptions. 
	 */
	private void setInitDsp() throws Exception
	{
		
		try
		{
			//#CM573693
			// Consignor Code
			txt_ConsignorCode.setText(getConsignorCode());
			//#CM573694
			// Start Storage date
			txt_StartStorageDate.setText("");
			//#CM573695
			// End Storage date
			txt_EndStorageDate.setText("");
			//#CM573696
			// Item Code
			txt_ItemCode.setText("");
			//#CM573697
			// Case/Piece flag
			rdo_Cpf_All.setChecked(true);
			rdo_Cpf_Case.setChecked(false);
			rdo_Cpf_Piece.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);

			//#CM573698
			// Set the order of display in Storage date order. 
			rdo_StorageDate.setChecked(true);
			rdo_StoragePlanDate.setChecked(false);

			//#CM573699
			// Cursor transition to Consignor Code
			setFocus(txt_ConsignorCode);

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
	}
	//#CM573700
	// Event handler methods -----------------------------------------
	
	//#CM573701
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573702
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573703
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573704
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

	//#CM573705
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573706
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573707
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573708
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573709
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573710
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573711
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
		//#CM573712
		// Set the search condition on the Consignor retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573713
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM573714
		// Retrieval flag
		param.setParameter(ListStorageConsignorBusiness.SEARCHCONSIGNOR_KEY, StorageSupportParameter.SEARCH_STORAGE_RESULT);
		//#CM573715
		// Processing Screen -> Result screen
		redirect("/storage/listbox/liststorageconsignor/ListStorageConsignor.do", param, "/progress.do");
	}

	//#CM573716
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573717
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573718
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartStorageDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573719
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartStorageDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573720
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchStartStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573721
	/** 
	 * It is called when the retrieval button of Start Storage date is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Storage date list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]<BR>
	 *    <DIR>
	 *       Consignor Code<BR>
	 *       Start Storage date<BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PsearchStartStorageDate_Click(ActionEvent e) throws Exception
	{
		//#CM573722
		//Set the search condition on the Start Storage date retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573723
		//Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM573724
		// Start Storage date
		param.setParameter(ListStorageDateBusiness.STARTSTORAGEDATE_KEY, WmsFormatter.toParamDate(txt_StartStorageDate.getDate()));
		//#CM573725
		// Start flag (Range flag of stock day)
		param.setParameter(ListStorageDateBusiness.RANGESTORAGEDATE_KEY, StorageSupportParameter.RANGE_START);
		//#CM573726
		// Processing Screen -> Result screen
		redirect("/storage/listbox/liststoragedate/ListStorageDate.do", param, "/progress.do");
	}

	//#CM573727
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573728
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573729
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573730
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndStorageDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573731
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndStorageDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573732
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchEndStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573733
	/** 
	 * It is called when the retrieval button of End Storage date is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the Storage date list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter<BR>
	 *    <DIR>
	 *       Consignor Code<BR>
	 *       End Storage date<BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_PsearchEndStorageDate_Click(ActionEvent e) throws Exception
	{
		//#CM573734
		// Set the search condition on the Location retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573735
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM573736
		// End Storage date
		param.setParameter(ListStorageDateBusiness.ENDSTORAGEDATE_KEY, WmsFormatter.toParamDate(txt_EndStorageDate.getDate()));
		//#CM573737
		// End flag (Range flag of stock day)
		param.setParameter(ListStorageDateBusiness.RANGESTORAGEDATE_KEY, StorageSupportParameter.RANGE_END);
		//#CM573738
		// Processing Screen -> Result screen
		redirect("/storage/listbox/liststoragedate/ListStorageDate.do", param, "/progress.do");
	}

	//#CM573739
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573740
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573741
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573742
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573743
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573744
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573745
	/** 
	 * It is called when the retrieval button of Item Code is pressed.<BR>
	 * <BR>
	 * Outline:Set the search condition in Parameter, and display the item list box by the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]<BR>
	 *    <DIR>
	 *       Consignor Code<BR>
	 *       Start Storage date<BR>
	 *       End Storage date<BR>
	 *       Item Code<BR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions.  
	 */
	public void btn_PsearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM573746
		// Set the search condition on the Item retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM573747
		// Consignor Code
		param.setParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM573748
		// Start Storage date
		param.setParameter(ListStorageDateBusiness.STARTSTORAGEDATE_KEY, WmsFormatter.toParamDate(txt_StartStorageDate.getDate()));
		//#CM573749
		// End Storage date
		param.setParameter(ListStorageDateBusiness.ENDSTORAGEDATE_KEY, WmsFormatter.toParamDate(txt_EndStorageDate.getDate()));
		//#CM573750
		// Item Code
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM573751
		// Retrieval flag
		param.setParameter(ListStorageItemBusiness.SEARCHITEM_KEY, StorageSupportParameter.SEARCH_STORAGE_RESULT);
		//#CM573752
		// Processing Screen -> Result screen
		redirect("/storage/listbox/liststorageitem/ListStorageItem.do", param, "/progress.do");
	}

	//#CM573753
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573754
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_All_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573755
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_All_Click(ActionEvent e) throws Exception
	{
	}

	//#CM573756
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Case_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573757
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Case_Click(ActionEvent e) throws Exception
	{
	}

	//#CM573758
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Piece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573759
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Piece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM573760
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_AppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573761
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_AppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM573762
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DspOrder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573763
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_StorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573764
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_StorageDate_Click(ActionEvent e) throws Exception
	{
	}

	//#CM573765
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_StoragePlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573766
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_StoragePlanDate_Click(ActionEvent e) throws Exception
	{
	}

	//#CM573767
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573768
	/** 
	 * It is called when the display button is pressed.<BR>
	 * <BR>
	 * Outline : Input Item of Input area is set in Parameter, and Display the retrieval result in Preset area.  <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the cursor in Consignor Code. <BR>
	 * 		2.Check input Item of Input area. (Mandatory input, Number of characters, Character attribute)<BR>
	 * 		3.Start Schedule.<BR>
	 * 		4.Display the result of the schedule in Preset area. <BR>
	 * 		5.Set balloon information. <BR>
	 * 		<BR>
	 * 		<DIR>
	 * 			[Parameter] *Mandatory input<BR>
	 * 			<BR>
	 * 			<DIR>
	 * 				Consignor Code*<BR>
	 * 				Start Storage plan date<BR>
	 * 				End Storage plan date<BR>
	 * 				Item Code<BR>
	 * 				Case/Piece flag*<BR>
	 * 				The order of display*<BR>
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
			//#CM573769
			// Cursor transition to Consignor Code
			setFocus(txt_ConsignorCode);

			//#CM573770
			// Acquisition of connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM573771
			// Clear the Preset area.
			lst_SStorageQtyInquiry.clearRow();
			txt_RConsignorCode.setText("");
			txt_RConsignorName.setText("");

			//#CM573772
			// Input Check
			txt_ConsignorCode.validate();
			//#CM573773
			// Pattern match character
			txt_StartStorageDate.validate(false);
			txt_EndStorageDate.validate(false);
			txt_ItemCode.validate(false);
			//#CM573774
			// Start Storage date must be smaller than End Storage date
			if (!StringUtil.isBlank(txt_StartStorageDate.getText()) && !StringUtil.isBlank(txt_EndStorageDate.getText()))
			{
				if (txt_StartStorageDate.getText().compareTo(txt_EndStorageDate.getText()) > 0)
				{
					//#CM573775
					// 6023106=Starting storage date must precede the end storage date.
					message.setMsgResourceKey("6023106");
					return;
				}
			}
			//#CM573776
			// Set in schedule Parameter.
			StorageSupportParameter param = new StorageSupportParameter();
			//#CM573777
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM573778
			// Start Storage date
			param.setFromStoragePlanDate(WmsFormatter.toParamDate(txt_StartStorageDate.getDate()));
			//#CM573779
			// End Storage date
			param.setToStoragePlanDate(WmsFormatter.toParamDate(txt_EndStorageDate.getDate()));
			//#CM573780
			// Item Code
			param.setItemCode(txt_ItemCode.getText());

			//#CM573781
			// Case/Piece flag
			if (rdo_Cpf_All.getChecked())
			{
				//#CM573782
				// All
				param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_ALL);
			}
			else if (rdo_Cpf_Case.getChecked())
			{
				//#CM573783
				// Case
				param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_Cpf_Piece.getChecked())
			{
				//#CM573784
				// Piece
				param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_PIECE);
			}
			else if (rdo_Cpf_AppointOff.getChecked())
			{
				//#CM573785
				// Unspecified
				param.setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
			}

			//#CM573786
			// The order of display
			if (rdo_StorageDate.getChecked())
			{
				//#CM573787
				// Storage date order
				param.setDisplayOrder(StorageSupportParameter.DISPLAY_ORDER_WORK_DATE);
			}
			else if (rdo_StoragePlanDate.getChecked())
			{
				//#CM573788
				// The order of Storage plan date
				param.setDisplayOrder(StorageSupportParameter.DISPLAY_ORDER_PLAN_DATE);
			}

			WmsScheduler schedule = new StorageQtyInquirySCH();
			StorageSupportParameter[] viewParam = (StorageSupportParameter[]) schedule.query(conn, param);

			//#CM573789
			// End processing when some errors occur or there is no display data. 
			if (viewParam == null || viewParam.length == 0)
			{
				//#CM573790
				// Close Connection
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM573791
			// Display it when the schedule is normally completed and the display data is acquired. 
			//#CM573792
			// Consignor Code (Preset) 
			txt_RConsignorCode.setText(viewParam[0].getConsignorCode());
			//#CM573793
			// Consignor Name (Preset) 
			txt_RConsignorName.setText(viewParam[0].getConsignorName());

			//#CM573794
			// Item Name
			String label_itemname = DisplayText.getText("LBL-W0103");
			//#CM573795
			// Shortage Case qty
			String label_shortagecaseqty = DisplayText.getText("LBL-W0208");
			//#CM573796
			// Shortage Piece qty
			String label_shortagepieceqty = DisplayText.getText("LBL-W0209");
			//#CM573797
			// Storage Location
			String label_storagelocation = DisplayText.getText("LBL-W0238");
			//#CM573798
			// CaseITF
			String label_caseitf = DisplayText.getText("LBL-W0010");
			//#CM573799
			// Bundle ITF
			String label_bundleitf = DisplayText.getText("LBL-W0006");
			//#CM573800
			// Expiry Date
			String label_usebydate = DisplayText.getText("LBL-W0270");
			//#CM573801
			// Worker Code
			String label_workercode = DisplayText.getText("LBL-W0325");
			//#CM573802
			// Worker Name
			String label_workername = DisplayText.getText("LBL-W0276");
			
			//#CM573803
			// LBL-W0719=----
			String noDisp = DisplayText.getText("LBL-W0719");

			String title_AreaTypeName = DisplayText.getText("LBL-W0569");

			
			//#CM573804
			// Set the value in the list cell. 
			for (int i = 0; i < viewParam.length; i++)
			{
				String workerCode = "";
				String workerName = "";
				//#CM573805
				// Worker Code
				//#CM573806
				// Worker Name
				if (viewParam[i].getSystemDiscKey() == StorageSupportParameter.SYSTEM_DISC_KEY_ASRS)
				{
					workerCode = noDisp;
					workerName = noDisp;
				}
				else
				{
					workerCode = viewParam[i].getWorkerCode();
					workerName = viewParam[i].getWorkerName();
				}
				
				//#CM573807
				// Add row
				lst_SStorageQtyInquiry.addRow();
				lst_SStorageQtyInquiry.setCurrentRow(i + 1);

				int j = 1;

				//#CM573808
				// Set the retrieval result in each cell. 
				//#CM573809
				//  (First row) 
				//#CM573810
				// Storage date
				lst_SStorageQtyInquiry.setValue(j++, WmsFormatter.toDispDate
					(viewParam[i].getStorageDate(),this.getHttpRequest().getLocale()));
				//#CM573811
				// Item Code
				lst_SStorageQtyInquiry.setValue(j++, viewParam[i].getItemCode());
				//#CM573812
				// Flag
				lst_SStorageQtyInquiry.setValue(j++, viewParam[i].getCasePieceflgName());
				//#CM573813
				// Packed qty per case
				lst_SStorageQtyInquiry.setValue(j++, WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));
				//#CM573814
				// Work plan case qty
				lst_SStorageQtyInquiry.setValue(j++, WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				//#CM573815
				// Result Case qty
				lst_SStorageQtyInquiry.setValue(j++, WmsFormatter.getNumFormat(viewParam[i].getResultCaseQty()));
				//#CM573816
				// Shortage Case qty
				lst_SStorageQtyInquiry.setValue(j++, WmsFormatter.getNumFormat(viewParam[i].getShortageQty()));
				//#CM573817
				// Storage Location 
				lst_SStorageQtyInquiry.setValue(j++, WmsFormatter.toDispLocation(
				        viewParam[i].getStorageLocation(), viewParam[i].getSystemDiscKey()));
				//#CM573818
				// Expiry Date
				lst_SStorageQtyInquiry.setValue(j++, viewParam[i].getUseByDate());
				//#CM573819
				// CaseITF
				lst_SStorageQtyInquiry.setValue(j++, viewParam[i].getITF());
				//#CM573820
				// Worker Code
				lst_SStorageQtyInquiry.setValue(j++, workerCode);

				//#CM573821
				//  (Second row) 
				//#CM573822
				// Storage plan date
				lst_SStorageQtyInquiry.setValue(j++, WmsFormatter.toDispDate
					(viewParam[i].getStoragePlanDate(),this.getHttpRequest().getLocale()));
				//#CM573823
				// Item Name
				lst_SStorageQtyInquiry.setValue(j++, viewParam[i].getItemName());
				//#CM573824
				// Packed qty per bundle
				lst_SStorageQtyInquiry.setValue(j++, WmsFormatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
				//#CM573825
				// Work plan piece qty
				lst_SStorageQtyInquiry.setValue(j++, WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
				//#CM573826
				// Result Piece qty
				lst_SStorageQtyInquiry.setValue(j++, WmsFormatter.getNumFormat(viewParam[i].getResultPieceQty()));
				//#CM573827
				// Shortage Piece qty
				lst_SStorageQtyInquiry.setValue(j++, WmsFormatter.getNumFormat(viewParam[i].getShortagePieceQty()));
				//#CM573828
				// Bundle ITF
				lst_SStorageQtyInquiry.setValue(j++, viewParam[i].getBundleITF());
				//#CM573829
				// Worker Name
				lst_SStorageQtyInquiry.setValue(j++, workerName);

				//#CM573830
				// Set tool tip (Balloon Item)
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM573831
				// Item Name
				toolTip.add(label_itemname, viewParam[i].getItemName());
				//#CM573832
				// Shortage Case qty
				toolTip.add(label_shortagecaseqty, WmsFormatter.getNumFormat(viewParam[i].getShortageQty()));
				//#CM573833
				// Shortage Piece qty
				toolTip.add(label_shortagepieceqty, WmsFormatter.getNumFormat(viewParam[i].getShortagePieceQty()));
				//#CM573834
				// Storage Location
				toolTip.add(label_storagelocation, 
						WmsFormatter.toDispLocation(
						viewParam[i].getStorageLocation(), viewParam[i].getSystemDiscKey()));
				//#CM573835
				// Expiry Date
				toolTip.add(label_usebydate, viewParam[i].getUseByDate());
				//#CM573836
				// CaseITF
				toolTip.add(label_caseitf, viewParam[i].getITF());
				//#CM573837
				// Bundle ITF
				toolTip.add(label_bundleitf, viewParam[i].getBundleITF());
				//#CM573838
				// Worker Code
				toolTip.add(label_workercode, workerCode);
				//#CM573839
				// Worker Name
				toolTip.add(label_workername, workerName);
				//#CM573840
				// Area Name
				toolTip.add(title_AreaTypeName, viewParam[i].getRetrievalAreaName());

				lst_SStorageQtyInquiry.setToolTip(i + 1, toolTip.getText());

			}

			//#CM573841
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
				//#CM573842
				// Close Connection
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM573843
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573844
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
	 * 			The order of display[Storage date order]<BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		setInitDsp();
	}

	//#CM573845
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573846
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573847
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573848
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573849
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573850
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573851
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573852
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573853
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573854
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStorageQtyInquiry_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM573855
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStorageQtyInquiry_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM573856
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStorageQtyInquiry_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM573857
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStorageQtyInquiry_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM573858
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStorageQtyInquiry_Server(ActionEvent e) throws Exception
	{
	}

	//#CM573859
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStorageQtyInquiry_Change(ActionEvent e) throws Exception
	{
	}

	//#CM573860
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStorageQtyInquiry_Click(ActionEvent e) throws Exception
	{
	}

}
//#CM573861
//end of class
