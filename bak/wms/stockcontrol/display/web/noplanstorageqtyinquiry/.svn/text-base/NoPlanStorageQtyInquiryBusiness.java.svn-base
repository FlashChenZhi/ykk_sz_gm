// $Id: NoPlanStorageQtyInquiryBusiness.java,v 1.2 2006/10/04 05:04:56 suresh Exp $

//#CM7651
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.noplanstorageqtyinquiry;
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
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.listnoplanstoragedate.ListNoPlanStorageDateBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.schedule.NoPlanStorageQtyInquirySCH;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM7652
/**
 * Designer : T.Nakajima(UTC) <BR>
 * Maker : T.Nakajima(UTC) <BR>
 * This is Unplanned storage result inquiry screen class.<BR>
 * Pass the parameter to the schedule class that executes Unplanned storage result inquiry process.<BR>
 * Execute following processes in this class. <BR>
 * <BR>
 * 1.Display button press down process(<CODE>btn_View_Click</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *  Set the and contents input via screen for the parameter. <BR>
 *  Search for the display data using the schedule class based on the parameter and display it in the list cell. <BR>
 *  <BR>
 *  [parameter]*Mandatory Input <BR>
 *  <DIR>
 *  <BR>
 *  Consignor code* : ConsignorCode <BR>
 *  Start Storage Date : FromWorkDate <BR>
 *  End Storage Date : ToWorkDate <BR>
 *  Item code : ItemCode <BR>
 *  Case/Piece division* : CasePieceFlag <BR>
 *	    <DIR>
 *      All <BR>
 *      Case <BR>
 *      Piece <BR>
 *      None <BR>
 *      </DIR>
 *  <BR>
 *  [Output data] <BR>
 *  <DIR>
 *  <BR>
 *      Consignor code : ConsignorCode <BR>
 *      Consignor name : ConsignorName <BR>
 *      Storage Date : StorageDateString <BR>
 *      Item code : ItemCode <BR>
 *      Item name : ItemName <BR>
 *      Division : CasePieceFlag <BR>
 *      Packing qty per case : EnteringQty <BR>
 *      packed qty per bundle : BundleEnteringQty <BR>
 *      Reslt Case qty : ResultCaseQty <BR>
 *      Result Piece Qty : ResultPieceQty <BR>
 *      Store Location : LocationNo <BR>
 *      Expiry Date : UseByDate <BR>
 *      Case ITF : ITF <BR>
 *      Bundle ITF : BundleITF <BR>
 *      Worker Code : WorkerCode <BR>
 *      Worker Name : WorkerName <BR>
 * 	</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/06</TD><TD>T.Nakajima(UTC)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:04:56 $
 * @author  $Author: suresh $
 */
public class NoPlanStorageQtyInquiryBusiness extends NoPlanStorageQtyInquiry implements WMSConstants
{
	//#CM7653
	// Class fields --------------------------------------------------

	//#CM7654
	// Class variables -----------------------------------------------

	//#CM7655
	// Class method --------------------------------------------------

	//#CM7656
	// Constructors --------------------------------------------------

	//#CM7657
	// Public methods ------------------------------------------------

	//#CM7658
	/**
	 * Initialize the screen <BR>
	 * <BR>
	 * Summary: Make initial/default display of the screen. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Initialize the input area. <BR>
	 *    2.Start schedule<BR>
	 *    3.Set the focus for the Consignor code. <BR>
	 * </DIR>
	 * <BR>
	 * Subject [Initial value] <BR>
	 * <BR>
	 * Consignor code         [search result] <BR>
	 * Start Storage Date         [None] <BR>
	 * End Storage Date         [None] <BR>
	 * Item code         [None] <BR>
	 * Case/Piece division  [All] <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM7659
		// Execute initial setting of the input area.
		setInitDsp();

		//#CM7660
		// Consignor code (Preset)
		txt_RConsignorCode.setText("");
		//#CM7661
		// Consignor name (Preset)
		txt_RConsignorName.setText("");
		//#CM7662
		// List cell
		lst_SNoPlanStrgQtyIqr.clearRow();
	}

	//#CM7663
	/**
	 * Invoke this before invoking each control event. <BR>
	 * <BR>
	 * Summary: Display dialog<BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM7664
			// Obtain parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM7665
			// Store to ViewState
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM7666
			// Set the screen name
			lbl_SettingName.setResourceKey(title);
		}
	}

	//#CM7667
	/**
	 * Invoke this method when returning from the popup window. <BR>
	 * Override page_DlgBack defined on the page.<BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters wParam = ((DialogEvent) e).getDialogParameters();
		//#CM7668
		// Obtain the parameter selected in the listbox.
		//#CM7669
		// Consignor code
		String consignorcode = wParam.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM7670
		// Start Storage Date
		String startstoragedate = wParam.getParameter(ListNoPlanStorageDateBusiness.STARTSTORAGEDATE_KEY);
		//#CM7671
		// End Storage Date
		String endstoragedate = wParam.getParameter(ListNoPlanStorageDateBusiness.ENDSTORAGEDATE_KEY);
		//#CM7672
		// Item code
		String itemcode = wParam.getParameter(ListStockItemBusiness.ITEMCODE_KEY);

		//#CM7673
		// Set the obtained item (count) it not empty for the corresponding input item (count).
		//#CM7674
		// Consignor code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM7675
		// Start Storage Date
		if (!StringUtil.isBlank(startstoragedate))
		{
			txt_StartStorageDate.setText(startstoragedate);
		}
		//#CM7676
		// End Storage Date
		if (!StringUtil.isBlank(endstoragedate))
		{
			txt_EndStorageDate.setText(endstoragedate);
		}
		//#CM7677
		// Item code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}

		//#CM7678
		// Set the focus for the Consignor code.
		setFocus(txt_ConsignorCode);
	}

	//#CM7679
	// Package methods -----------------------------------------------

	//#CM7680
	// Protected methods ---------------------------------------------

	//#CM7681
	// Private methods -----------------------------------------------

	//#CM7682
	/**
	 * Clicking on the Clear button or opening initial display allows to initialize the input area. <BR>
	 *   
	 * @throws Exception Reports all the exceptions.
	 */
	private void setInitDsp() throws Exception
	{
		//#CM7683
		// Connection
		Connection wConn = null;

		try
		{
			//#CM7684
			// Set the focus for the Consignor code.
			setFocus(txt_ConsignorCode);

			//#CM7685
			// Consignor code
			txt_ConsignorCode.setText("");
			//#CM7686
			// Start Storage Date
			txt_StartStorageDate.setText("");
			//#CM7687
			// End Storage Date
			txt_EndStorageDate.setText("");
			//#CM7688
			// Item code
			txt_ItemCode.setText("");
			//#CM7689
			// Case/Piece division
			rdo_CpfAll.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);

			//#CM7690
			// Obtain the connection.
			wConn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM7691
			// Generate instance for Unplanned Storage Result inquiry schedule class.
			WmsScheduler wSchedule = new NoPlanStorageQtyInquirySCH();
			//#CM7692
			// Pass null if no search conditions needed.
			StockControlParameter wParam = (StockControlParameter) wSchedule.initFind(wConn, null);

			//#CM7693
			// When Consignor code is 1, display initial/default.
			if (wParam != null)
			{
				txt_ConsignorCode.setText(wParam.getConsignorCode());
			}
			else
			{
				txt_ConsignorCode.setText("");
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
				//#CM7694
				// Connection close
				if (wConn != null)
					wConn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}

	}

	//#CM7695
	// Event handler methods -----------------------------------------
	//#CM7696
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7697
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7698
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Inquiry_Click(ActionEvent e) throws Exception
	{
	}

	//#CM7699
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7700
	/** 
	 * Clicking on the Clicking the Menu button invokes this. <BR>
	 * <BR>
	 * Summary: Move to menu screen. <BR>
	 * <BR>
	 *
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM7701
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7702
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7703
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7704
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7705
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7706
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7707
	/** 
	 * Clicking Consignor search button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displaysthe Consignor search listbox. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] *Mandatory Input<BR>
	 *    <DIR>
	 *       Consignor code<BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM7708
		// Set the search conditions parameter.
		ForwardParameters wParam = new ForwardParameters();
		//#CM7709
		// Consignor code
		wParam.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM7710
		// Search flag (Set search destination table)
		wParam.setParameter(ListStockConsignorBusiness.SEARCHCONSIGNOR_KEY, StockControlParameter.SEARCHFLAG_EX_STORAGE);
		//#CM7711
		// In process screen->Result screen 
		redirect("/stockcontrol/listbox/liststockconsignor/ListStockConsignor.do", wParam, "/progress.do");
	}

	//#CM7712
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7713
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7714
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartStorageDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7715
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartStorageDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7716
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PStartStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7717
	/** 
	 * Clicking on the Search Start Storage Date button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displaysthe storage date search listbox. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] *Mandatory Input <BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *       Start Storage Date <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PStartStorageDate_Click(ActionEvent e) throws Exception
	{
		//#CM7718
		// Set the search conditions parameter.
		ForwardParameters wParam = new ForwardParameters();
		//#CM7719
		// Consignor code
		wParam.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM7720
		// Start Storage Date
		wParam.setParameter(ListNoPlanStorageDateBusiness.STARTSTORAGEDATE_KEY, WmsFormatter.toParamDate(txt_StartStorageDate.getDate()));
		//#CM7721
		// Start flag(Storage Date area flag)
		wParam.setParameter(ListNoPlanStorageDateBusiness.RANGESTORAGEDATE_KEY, StockControlParameter.RANGE_START);
		//#CM7722
		// Search flag(Unplanned picking or storage)
		wParam.setParameter(ListNoPlanStorageDateBusiness.SEARCHDATE_KEY, StockControlParameter.SEARCHFLAG_EX_STORAGE);
		//#CM7723
		// In process screen->Result screen 
		redirect("/stockcontrol/listbox/listnoplanstoragedate/ListNoPlanStorageDate.do", wParam, "/progress.do");
	}

	//#CM7724
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7725
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7726
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7727
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndStorageDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7728
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndStorageDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7729
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PEndStorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7730
	/** 
	 * Clicking End Storage Date search button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displaysthe storage date search listbox. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] *Mandatory Input <BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *       End Storage Date <BR>
	 *    </DIR>
	 * <BR> 
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PEndStorageDate_Click(ActionEvent e) throws Exception
	{
		//#CM7731
		// Set the search conditions parameter.
		ForwardParameters wParam = new ForwardParameters();
		//#CM7732
		// Consignor code
		wParam.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM7733
		// End Storage Date
		wParam.setParameter(ListNoPlanStorageDateBusiness.ENDSTORAGEDATE_KEY, WmsFormatter.toParamDate(txt_EndStorageDate.getDate()));
		//#CM7734
		// End flag (Storage Date area flag)
		wParam.setParameter(ListNoPlanStorageDateBusiness.RANGESTORAGEDATE_KEY, StockControlParameter.RANGE_END);
		//#CM7735
		// Search flag(Unplanned picking or storage)
		wParam.setParameter(ListNoPlanStorageDateBusiness.SEARCHDATE_KEY, StockControlParameter.SEARCHFLAG_EX_STORAGE);
		//#CM7736
		// In process screen->Result screen 
		redirect("/stockcontrol/listbox/listnoplanstoragedate/ListNoPlanStorageDate.do", wParam, "/progress.do");
	}

	//#CM7737
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7738
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7739
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7740
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7741
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7742
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7743
	/** 
	 * Clicking on the Search Item Code button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displaysthe item code search listbox. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] *Mandatory Input <BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *       Start Storage Date <BR>
	 *       End Storage Date <BR>
	 *       Item code <BR>
	 *    </DIR>
	 * <BR> 
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM7744
		// Set the search conditions parameter.
		ForwardParameters wParam = new ForwardParameters();
		//#CM7745
		// Consignor code
		wParam.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM7746
		// Start Storage Date
		wParam.setParameter(ListNoPlanStorageDateBusiness.STARTSTORAGEDATE_KEY, WmsFormatter.toParamDate(txt_StartStorageDate.getDate()));
		//#CM7747
		// End Storage Date
		wParam.setParameter(ListNoPlanStorageDateBusiness.ENDSTORAGEDATE_KEY, WmsFormatter.toParamDate(txt_EndStorageDate.getDate()));
		//#CM7748
		// Item code
		wParam.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM7749
		// Search flag (Set search destination table)
		wParam.setParameter(ListStockItemBusiness.SEARCHITEM_KEY, StockControlParameter.SEARCHFLAG_EX_STORAGE);
		//#CM7750
		// In process screen->Result screen 
		redirect("/stockcontrol/listbox/liststockitem/ListStockItem.do", wParam, "/progress.do");
	}

	//#CM7751
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7752
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7753
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM7754
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7755
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM7756
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7757
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM7758
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7759
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM7760
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7761
	/** 
	 * Clicking on the Display button invokes this. <BR>
	 * <BR>
	 * Summary: Searches for the Result data (DvResultView) and displays it in the preset area. <BR>
	 * <BR>
	 * <DIR>
	 *   1.Check input field of input area (mandatory input) <BR>
	 *   2.Start schedule <BR>
	 *   3.Display it in the preset area. <BR>
	 *   4.Maintain the contents of the input area is still maintained. <BR>
	 * </DIR>
	 * <BR>
	 * <DIR>
	 *    [Line No. list of the list cell] <BR>
	 *    <BR>
	 *    <DIR>
	 *      1.Storage Date <BR>
	 *      2.Item code <BR>
	 * 		3.Division <BR>
	 * 		4.Packing qty per Case <BR>
	 * 		5.Result Case qty <BR>
	 * 		6.Store Location <BR>
	 * 		7.Expiry Date <BR>
	 * 		8.Case ITF <BR>
	 * 		9.Worker Code <BR> 
	 * 		10.Item name <BR>
	 * 		11.Packing qty per Bundle <BR>
	 * 		12.Result Piece qty <BR>
	 * 		13.Bundle ITF <BR>  
	 * 		14.Worker Name <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{

		//#CM7762
		// Connection
		Connection wConn = null;

		try
		{
			//#CM7763
			// Set the focus for the Consignor code.
			setFocus(txt_ConsignorCode);

			//#CM7764
			// Clear Consignor code (preset)
			txt_RConsignorCode.setText("");
			//#CM7765
			// Clear Consignor Name (preset)
			txt_RConsignorName.setText("");
			//#CM7766
			// Clear preset area
			lst_SNoPlanStrgQtyIqr.clearRow();

			//#CM7767
			// Check input field of input area
			//#CM7768
			// Consignor code
			txt_ConsignorCode.validate();
			//#CM7769
			// Start Storage Date
			txt_StartStorageDate.validate(false);
			//#CM7770
			// End Storage Date
			txt_EndStorageDate.validate(false);
			//#CM7771
			// Item code
			txt_ItemCode.validate(false);

			//#CM7772
			// Chack storage date size
			if (!StringUtil.isBlank(txt_StartStorageDate.getDate()) && !StringUtil.isBlank(txt_EndStorageDate.getDate()))
			{
				if (txt_StartStorageDate.getDate().after(txt_EndStorageDate.getDate()))
				{
					//#CM7773
					// Display the error message and close it.
					//#CM7774
					// 6023106=Starting storage date must precede the end storage date.
					message.setMsgResourceKey("6023106");
					return;
				}
			}

			//#CM7775
			// Generate stock parameter class instance.
			StockControlParameter wParam = new StockControlParameter();

			//#CM7776
			// Set the schedule parameter
			//#CM7777
			// Consignor code
			wParam.setConsignorCode(txt_ConsignorCode.getText());
			//#CM7778
			// Start Storage Date
			wParam.setFromWorkDate(WmsFormatter.toParamDate(txt_StartStorageDate.getDate()));
			//#CM7779
			// End Storage Date
			wParam.setToWorkDate(WmsFormatter.toParamDate(txt_EndStorageDate.getDate()));
			//#CM7780
			// Item code
			wParam.setItemCode(txt_ItemCode.getText());
			//#CM7781
			// Case/Piece division
			if (rdo_CpfAll.getChecked())
			{
				//#CM7782
				// All
				wParam.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_ALL);
			}
			else if (rdo_CpfCase.getChecked())
			{
				//#CM7783
				// Case
				wParam.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_CpfPiece.getChecked())
			{
				//#CM7784
				// Piece
				wParam.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_PIECE);
			}
			else if (rdo_CpfAppointOff.getChecked())
			{
				//#CM7785
				// None
				wParam.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_NOTHING);
			}

			//#CM7786
			// Obtain the connection.
			wConn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM7787
			// Generate instance for Unplanned Storage Result inquiry schedule.
			WmsScheduler wSchedule = new NoPlanStorageQtyInquirySCH();
			//#CM7788
			// Start schedule.
			StockControlParameter[] wViewParam = (StockControlParameter[]) wSchedule.query(wConn, wParam);

			//#CM7789
			// Close the process when some error occurred or no display data was found.
			if (wViewParam == null || wViewParam.length == 0)
			{
				message.setMsgResourceKey(wSchedule.getMessage());
				return;
			}

			//#CM7790
			// Display the data if obtaining the display data when the schedule normally completed.
			//#CM7791
			// Consignor code (preset)
			txt_RConsignorCode.setText(wViewParam[0].getConsignorCode());
			//#CM7792
			// Consignor name (preset)
			txt_RConsignorName.setText(wViewParam[0].getConsignorName());

			//#CM7793
			// Obtain the item (count) name needed to display in the balloon.			
			//#CM7794
			// Item name
			String wItenName = DisplayText.getText("LBL-W0103");
			//#CM7795
			// Case ITF
			String wCaseItf = DisplayText.getText("LBL-W0010");
			//#CM7796
			// Bundle ITF
			String wBundleItf = DisplayText.getText("LBL-W0006");
			//#CM7797
			// Worker Code
			String wWorkerCode = DisplayText.getText("LBL-W0325");
			//#CM7798
			// Worker Name
			String wWorkerName = DisplayText.getText("LBL-W0276");

			//#CM7799
			// LBL-W0719=----
			String noDisp = DisplayText.getText("LBL-W0719");

			String title_AreaTypeName = DisplayText.getText("LBL-W0569");

			//#CM7800
			// Set the value for the list cell.
			for (int i = 0; i < wViewParam.length; i++)
			{
				String workerCode = "";
				String workerName = "";
				//#CM7801
				// Worker Code
				//#CM7802
				// Worker Name
				if (wViewParam[i].getSystemDiskKey() == StockControlParameter.SYSTEM_DISC_KEY_ASRS)
				{
					workerCode = noDisp;
					workerName = noDisp;
				}
				else
				{
					workerCode = wViewParam[i].getWorkerCode();
					workerName = wViewParam[i].getWorkerName();
				}

				//#CM7803
				// Add line
				lst_SNoPlanStrgQtyIqr.addRow();
				lst_SNoPlanStrgQtyIqr.setCurrentRow(i + 1);

				//#CM7804
				// Set the search result for each cell.
				//#CM7805
				// (1st line)
				//#CM7806
				// Storage Date
				lst_SNoPlanStrgQtyIqr.setValue(1, WmsFormatter.toDispDate(wViewParam[i].getStorageDateString(), this.getHttpRequest().getLocale()));
				//#CM7807
				// Item code
				lst_SNoPlanStrgQtyIqr.setValue(2, wViewParam[i].getItemCode());
				//#CM7808
				// Division
				lst_SNoPlanStrgQtyIqr.setValue(3, wViewParam[i].getCasePieceFlagName());
				//#CM7809
				// Packing qty per case
				lst_SNoPlanStrgQtyIqr.setValue(4, WmsFormatter.getNumFormat(wViewParam[i].getEnteringQty()));
				//#CM7810
				// Reslt Case qty
				lst_SNoPlanStrgQtyIqr.setValue(5, WmsFormatter.getNumFormat(wViewParam[i].getResultCaseQty()));
				//#CM7811
				// Store Location
				lst_SNoPlanStrgQtyIqr.setValue(6, WmsFormatter.toDispLocation(wViewParam[i].getLocationNo(), wViewParam[i].getSystemDiskKey()));
				//#CM7812
				// Expiry Date
				lst_SNoPlanStrgQtyIqr.setValue(7, wViewParam[i].getUseByDate());
				//#CM7813
				// Case ITF
				lst_SNoPlanStrgQtyIqr.setValue(8, wViewParam[i].getITF());
				//#CM7814
				// Worker Code
				lst_SNoPlanStrgQtyIqr.setValue(9, workerCode);

				//#CM7815
				//(2nd line)
				//#CM7816
				// Item name
				lst_SNoPlanStrgQtyIqr.setValue(10, wViewParam[i].getItemName());
				//#CM7817
				// packed qty per bundle
				lst_SNoPlanStrgQtyIqr.setValue(11, WmsFormatter.getNumFormat(wViewParam[i].getBundleEnteringQty()));
				//#CM7818
				// Result Piece Qty
				lst_SNoPlanStrgQtyIqr.setValue(12, WmsFormatter.getNumFormat(wViewParam[i].getResultPieceQty()));
				//#CM7819
				// Bundle ITF
				lst_SNoPlanStrgQtyIqr.setValue(13, wViewParam[i].getBundleITF());
				//#CM7820
				// Worker Name
				lst_SNoPlanStrgQtyIqr.setValue(14, workerName);

				//#CM7821
				// Set the tool tip (balloon item (count).
				ToolTipHelper toolTip = new ToolTipHelper();
				//#CM7822
				// Item name
				toolTip.add(wItenName, wViewParam[i].getItemName());
				//#CM7823
				// Case ITF
				toolTip.add(wCaseItf, wViewParam[i].getITF());
				//#CM7824
				// Bundle ITF
				toolTip.add(wBundleItf, wViewParam[i].getBundleITF());
				//#CM7825
				// Worker Code
				toolTip.add(wWorkerCode, workerCode);
				//#CM7826
				// Worker Name
				toolTip.add(wWorkerName, workerName);
				//#CM7827
				// Area Name
				toolTip.add(title_AreaTypeName, wViewParam[i].getAreaName());

				lst_SNoPlanStrgQtyIqr.setToolTip(i + 1, toolTip.getText());
			}

			//#CM7828
			// Set the message.
			message.setMsgResourceKey(wSchedule.getMessage());

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));

		}
		finally
		{
			try
			{
				if (wConn != null)
				{
					//#CM7829
					// Close the connection
					wConn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
	}

	//#CM7830
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7831
	/** 
	 * Clicking on the Clear button invokes this. <BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Return the item (count) in the input area to the initial value. <BR>
	 *    <DIR>
	 *  	-Refer to <CODE>page_Load(ActionEvent e)</CODE>Method in regard with the initialvalue.<BR>
	 *    </DIR>
	 *    2.Set the cursor for Consignor code. <BR>
	 *    3.Maintain the contents of preset area. <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM7832
		// Execute initial setting of the input area.
		setInitDsp();

	}

	//#CM7833
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7834
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7835
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7836
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7837
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7838
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7839
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7840
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7841
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7842
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanStrgQtyIqr_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM7843
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanStrgQtyIqr_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM7844
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanStrgQtyIqr_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM7845
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanStrgQtyIqr_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM7846
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanStrgQtyIqr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM7847
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanStrgQtyIqr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM7848
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SNoPlanStrgQtyIqr_Click(ActionEvent e) throws Exception
	{
	}

}
//#CM7849
//end of class
