// $Id: NoPlanRetrievalBusiness.java,v 1.2 2006/10/04 05:04:27 suresh Exp $

//#CM6791
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.noplanretrieval;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockbundleitf.ListStockBundleItfBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockcaseitf.ListStockCaseItfBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocation.ListStockLocationBusiness;
import jp.co.daifuku.wms.stockcontrol.schedule.NoPlanRetrievalSCH;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM6792
/**
 * Designer : A.Nagasawa <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * This is Unplanned retrieval screen class.<BR>
 * Pass the parameter to the schedule that execute Unplanned retrieval.<BR>
 * Execute commit rollback of the transaction in this screen. Set the contents input from the screen to the parameter, and the schedule search the data for display based on the parameter.<BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * 1.Display button press down process(<CODE>btn_View_Click()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Set the contents input from the screen to the parameter, and the schedule search the data for display based on the parameter.<BR>
 *   Receive data to be output to the preset are from the schedule and output it to the preset area.<BR>
 *   <BR>
 *   [parameter] *Mandatory Input<BR>
 *   <BR>
 *   Worker Code* <BR>
 *   Password* <BR>
 *   Consignor code* <BR>
 *   Item code  <BR>
 *   Case/Piece division <BR>
 *   Start location <BR>
 *   End location <BR>
 *   Case ITF <BR>
 *   Bundle ITF <BR>
 *   Customer code <BR>
 *   Customer name <BR>
 *   <BR>
 *   [Output data] <BR>
 *   <BR>
 *   Consignor code <BR>
 *   Consignor name <BR>
 *   Item code <BR>
 *   Item name <BR>
 *   Case/Piece division <BR>
 *   Location No. <BR>
 *   Packing qty per case <BR>
 *   packed qty per bundle <BR>
 *   allocation possible Case qty((Stock qty-allocated qty)/Packing qty per case) <BR>
 *   allocation possible Piece qty((Stock qty-allocated qty)%Packing qty per case) <BR>
 *   Picking Case qty<BR>
 *   Picking Piece qty<BR>
 *   Expiry Date <BR>
 *   Case ITF <BR>
 *   Bundle ITF <BR>
 *   Stock ID <BR>
 *   last update date/time <BR>
 * </DIR>
 * <BR>
 * 2.Picking start button press down process(<CODE>btn_RetrievalStart_Click()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *   Set the contents input via preset area for the parameter. The schedule executes the Unplanned retrieval based on the parameter.<BR>
 *   Receive the result from the schedule and receive true when the process normally completed<BR>
 *   Receive false if schedule is not completed due to condition error and others.<BR>
 *   Output the message obtained from the schedule to the screen<BR>
 *   <BR>
 *   [parameter] *Mandatory Input <BR>
 *   <BR>
 *   Worker Code* <BR>
 *   Password* <BR>
 *   Consignor code <BR>
 *   Consignor name* <BR>
 *   Item code* <BR>
 *   Item name <BR>
 *   Customer code<BR>
 *   Customer name<BR>
 *   Terminal No.<BR>
 *   Case/Piece division* <BR>
 *   Location No.* <BR>
 *   Packing qty per case* <BR>
 *   packed qty per bundle* <BR>
 *   Case Picking qty* <BR>
 *   Piece Picking qty* <BR>
 *   Case ITF <BR>
 *   Bundle ITF <BR>
 *   Expiry Date <BR>
 *   Unplanned picking work report Printing Division <BR>
 *   All qty Division <BR>
 *   Stock ID <BR>
 *   last update date/time <BR>
 *   Line No.<BR>
 *   <BR>
 *   [Output data] <BR>
 *   <BR>
 *   Consignor code <BR>
 *   Consignor name <BR>
 *   Item code <BR>
 *   Item name <BR>
 *   Case/Piece division <BR>
 *   Location No. <BR>
 *   Packing qty per case <BR>
 *   packed qty per bundle <BR>
 *   allocation possible Case qty((Stock qty-allocated qty)/Packing qty per case) <BR>
 *   allocation possible Piece qty((Stock qty-allocated qty)%Packing qty per case) <BR>
 *   Picking Case qty<BR>
 *   Picking Piece qty<BR>
 *   Expiry Date<BR>
 *   Case ITF <BR>
 *   Bundle ITF <BR>
 *   Stock ID <BR>
 *   last update date/time <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/29</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:04:27 $
 * @author  $Author: suresh $
 */
public class NoPlanRetrievalBusiness extends NoPlanRetrieval implements WMSConstants
{
	//#CM6793
	// Class fields --------------------------------------------------
	
	//#CM6794
	// For ViewState
	//#CM6795
	// Consignor code
	private static final String VSTCONSIGNOR = "CONSIGNOR_CODE";
	//#CM6796
	// Item code
	private static final String VSTITEMCODE = "ITEM_CODE";
	//#CM6797
	// Case/Piece division
	private static final String VSTCASEPIECE = "CASE_PIECE_FLAG";
	//#CM6798
	// Start location
	private static final String VSTLOCATIONFROM = "LOCATION_FROM";
	//#CM6799
	// End location
	private static final String VSTLOCATIONTO = "LOCATION_TO";
	//#CM6800
	// Case ITF
	private static final String VSTCASEITF = "CASE_ITF";
	//#CM6801
	// Bundle ITF
	private static final String VSTBUNDLEITF = "BUNDLE_ITF";
	
	//#CM6802
	// Class variables -----------------------------------------------

	//#CM6803
	// Class method --------------------------------------------------

	//#CM6804
	// Constructors --------------------------------------------------

	//#CM6805
	// Public methods ------------------------------------------------

	//#CM6806
	/**
	 * Initialize the screen<BR>
	 * <BR>
	 * Summary: Shows initial display of the screen.<BR>
	 * <BR>
	 * <DIR>
	 *    1.Initialize input area.<BR>
	 *    2.Start schedule <BR>
	 *    3.Disable Picking start button, Picking qty clear button, Select All button, Release select all button and List clear button.<BR>
	 *    4.Set the cursor to the worker code.<BR>
	 * </DIR>
	 * <BR>
	 * Item[Initial value] <BR>
	 * <BR>
	 * Worker Code     [None] <BR>
	 * Password		[None] <BR>
	 * Consignor code		[Execute initial display when there is only one corresponding Consignor] <BR>
	 * Item code		[None] <BR>
	 * Case/Piece division	[All] <BR>
	 * Start location			[None] <BR>
	 * End location			[None] <BR>
	 * Case ITF		[None] <BR>
	 * Bundle ITF		[None] <BR>
	 * Customer code		[None] <BR>
	 * Customer name		[None] <BR>
	 * Print the Unplanned picking work list [true]. <BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM6807
			// Set the Case/Piece division to All.
			rdo_Cpf_All.setChecked(true);

			//#CM6808
			// Place a check on "Print a planned retrieval work report ".
			chk_CommonUse.setChecked(true);

			//#CM6809
			// Disable button press down
			//#CM6810
			// Picking start button
			btn_RetrievalStart.setEnabled(false);
			//#CM6811
			// Picking qty clear button
			btn_RetrievalQtyClear.setEnabled(false);
			//#CM6812
			// Select all button
			btn_AllCheck.setEnabled(false);
			//#CM6813
			// Release select all button
			btn_AllCheckClear.setEnabled(false);
			//#CM6814
			// List clear button
			btn_ListClear.setEnabled(false);

			//#CM6815
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new NoPlanRetrievalSCH();
			StockControlParameter param = (StockControlParameter) schedule.initFind(conn, null);

			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				//#CM6816
				// When Consignor code is 1
				txt_ConsignorCode.setText(param.getConsignorCode());
			}

		}
		catch (Exception ex)
		{
			//#CM6817
			// Connection roll-back
			if (conn != null)
			{
				conn.rollback();
			}
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM6818
				// Connection close
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM6819
	/**
	 * Invoke this before invoking each control event.<BR>
	 * <BR>
	 * Summary: Displays a dialog.<BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM6820
			// Obtain parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM6821
			// Store to ViewState
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM6822
			// Set the screen name
			lbl_SettingName.setResourceKey(title);
		}
		//#CM6823
		// Clicking on the Picking start button, display Check dialogMSG-W0032=Do you pick it??
		btn_RetrievalStart.setBeforeConfirm("MSG-W0032");

		//#CM6824
		// Clicking on the Dialog to confirm when clicking the List Clear button MSG-W0012= Clear the list input info. Do you confirm it?
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		//#CM6825
		// Set the cursor to the worker code
		setFocus(txtl_WorkerCode);
	}

	//#CM6826
	/**
	 * Invoke this method when returning from the popup window.
	 * <BR>
	 * Override <CODE>page_DlgBack</CODE> defined on <CODE>Page</CODE>.
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM6827
		// Obtain the parameter selected in the listbox.
		//#CM6828
		// Consignor code
		String consignorcode = param.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM6829
		// Item code
		String itemcode = param.getParameter(ListStockItemBusiness.ITEMCODE_KEY);
		//#CM6830
		// Start location
		String startlocation = param.getParameter(ListStockLocationBusiness.STARTLOCATION_KEY);
		//#CM6831
		// End location
		String endlocation = param.getParameter(ListStockLocationBusiness.ENDLOCATION_KEY);
		//#CM6832
		// Case ITF
		String caseitf = param.getParameter(ListStockCaseItfBusiness.CASEITF_KEY);
		//#CM6833
		// Bundle ITF
		String bundleitf = param.getParameter(ListStockBundleItfBusiness.BUNDLEITF_KEY);

		//#CM6834
		// Set the value if not empty.
		//#CM6835
		// Consignor code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM6836
		// Item code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM6837
		// Start location
		if (!StringUtil.isBlank(startlocation))
		{
			txt_StartLocation.setText(startlocation);
		}
		//#CM6838
		// End location
		if (!StringUtil.isBlank(endlocation))
		{
			txt_EndLocation.setText(endlocation);
		}
		//#CM6839
		// Case ITF
		if (!StringUtil.isBlank(caseitf))
		{
			txt_CaseItf.setText(caseitf);
		}
		//#CM6840
		// Bundle ITF
		if (!StringUtil.isBlank(bundleitf))
		{
			txt_BundleItf.setText(bundleitf);
		}
	}

	//#CM6841
	// Package methods -----------------------------------------------

	//#CM6842
	// Protected methods ---------------------------------------------

	//#CM6843
	// Private methods -----------------------------------------------

	//#CM6844
	/**
	 * Set the value for the preset area.<BR>
	 * 
	 * @param listParams  Parameter including <code>StockControlParameter</code>display result
	 * @throws Exception Reports all the exceptions. 
	 */
	private void setList(Parameter[] listParams) throws Exception
	{
		//#CM6845
		// Delete all lines
		lst_SNoPlanRetrieval.clearRow();

		StockControlParameter param = (StockControlParameter) listParams[0];

		//#CM6846
		// Consignor code(Read out only)
		txt_RConsignorCode.setText(param.getConsignorCode());
		//#CM6847
		// Consignor name(Read out only)
		txt_RConsignorName.setText(param.getConsignorName());

		StockControlParameter[] viewParam = (StockControlParameter[]) listParams;

		//#CM6848
		// Item name
		String label_itemname = DisplayText.getText("LBL-W0103");
		//#CM6849
		// Case ITF
		String label_caseitf = DisplayText.getText("LBL-W0010");
		//#CM6850
		// Bundle ITF
		String label_bundleitf = DisplayText.getText("LBL-W0006");

		for (int i = 0; i < viewParam.length; i++)
		{
			//#CM6851
			// Add line
			lst_SNoPlanRetrieval.setCurrentRow(i + 1);
			lst_SNoPlanRetrieval.addRow();

			lst_SNoPlanRetrieval.setValue(1, viewParam[i].getItemCode());
			lst_SNoPlanRetrieval.setValue(2, viewParam[i].getCasePieceFlagName());
			lst_SNoPlanRetrieval.setValue(3, viewParam[i].getLocationNo());
			lst_SNoPlanRetrieval.setValue(9, viewParam[i].getITF());
			lst_SNoPlanRetrieval.setValue(10, viewParam[i].getItemName());
			lst_SNoPlanRetrieval.setValue(14, viewParam[i].getBundleITF());

			//#CM6852
			// Translate from value type to comma compiled string.
			lst_SNoPlanRetrieval.setValue(
				4,
				WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));
			lst_SNoPlanRetrieval.setValue(
				5,
				WmsFormatter.getNumFormat(viewParam[i].getAllocateCaseQty()));
			lst_SNoPlanRetrieval.setValue(
				11,
				WmsFormatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
			lst_SNoPlanRetrieval.setValue(
				12,
				WmsFormatter.getNumFormat(viewParam[i].getAllocatePieceQty()));
			
			//#CM6853
			// last update date/time
			String lastupdate = WmsFormatter.getTimeStampString(viewParam[i].getLastUpdateDate());
			
			//#CM6854
			// Division+Stock ID+last update date/time+Picking Case qty+Picking Piece qty+Stock qty
			List list = new Vector();
			list.add(viewParam[i].getCasePieceFlag());
			list.add(viewParam[i].getStockId());
			list.add(lastupdate);
			list.add("");
			list.add("");
			lst_SNoPlanRetrieval.setValue(0, CollectionUtils.getConnectedString(list));

			//#CM6855
			// Set blank for the shipping case qty and the shipping piece qty.
			lst_SNoPlanRetrieval.setValue(6, (""));
			lst_SNoPlanRetrieval.setValue(13, (""));

			//#CM6856
			// Set the expiry date.
			lst_SNoPlanRetrieval.setValue(8, viewParam[i].getUseByDate());
			
			//#CM6857
			// Set the tool tip.
			ToolTipHelper toolTip = new ToolTipHelper();
			//#CM6858
			// Item name
			toolTip.add(label_itemname, viewParam[i].getItemName());
			//#CM6859
			// Case ITF			
			toolTip.add(label_caseitf, viewParam[i].getITF());
			//#CM6860
			// Bundle ITF		
			toolTip.add(label_bundleitf, viewParam[i].getBundleITF());

			lst_SNoPlanRetrieval.setToolTip(i + 1, toolTip.getText());

		}

		//#CM6861
		// Enable to click a button.
		//#CM6862
		// Picking start button
		btn_RetrievalStart.setEnabled(true);
		//#CM6863
		// Picking qty clear button
		btn_RetrievalQtyClear.setEnabled(true);
		//#CM6864
		// Select All button
		btn_AllCheck.setEnabled(true);
		//#CM6865
		// Release select all button
		btn_AllCheckClear.setEnabled(true);
		//#CM6866
		// List clear button
		btn_ListClear.setEnabled(true);
	}

	//#CM6867
	/**
	 * Execute input check.
	 * Set the message if any error and return false.
	 * 
	 * @return true:Normal false: Abnormal
	 */
	private boolean checkContainNgText()
	{
		WmsCheckker checker = new WmsCheckker();

		//#CM6868
		// Worker Code
		if (!checker.checkContainNgText(txtl_WorkerCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}

		//#CM6869
		// Password
		if (!checker.checkContainNgText(txt_Password))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}

		//#CM6870
		// Consignor code
		if (!checker.checkContainNgText(txt_ConsignorCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		//#CM6871
		// Item code
		if (!checker.checkContainNgText(txt_ItemCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}

		//#CM6872
		// Start location
		if (!checker.checkContainNgText(txt_StartLocation))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}

		//#CM6873
		// End location
		if (!checker.checkContainNgText(txt_EndLocation))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		//#CM6874
		// Case ITF
		if (!checker.checkContainNgText(txt_CaseItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		//#CM6875
		// Bundle ITF
		if (!checker.checkContainNgText(txt_BundleItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		//#CM6876
		// Customer code
		if (!checker.checkContainNgText(txt_CustomerCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		//#CM6877
		// Customer name
		if (!checker.checkContainNgText(txt_CustomerName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		return true;
	}

	//#CM6878
	/**
	 * Execute input check.
	 * Set the message if any error and return false.
	 * 
	 * @param rowNo  Line No. of the list cell  
	 * @return true:Normal false: Abnormal
	 */
	private boolean checkContainNgText(int rowNo)
	{
		WmsCheckker checker = new WmsCheckker();

		lst_SNoPlanRetrieval.setCurrentRow(rowNo);

		if(!checker.checkContainNgText(
				lst_SNoPlanRetrieval.getValue(8) ,
				rowNo,
				lst_SNoPlanRetrieval.getListCellColumn(8).getResourceKey() )
				)
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		return true;
	}
	
	//#CM6879
	// Event handler methods -----------------------------------------
	//#CM6880
	/** 
	 * Clicking on the Clicking the Menu button invokes this.<BR>
	 * <BR>
	 * Summary: Move to menu screen.
	 *
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM6881
	/** 
	 * Clicking on the Search Consignor Code button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displays the Consignor list listbox using the search conditions.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		//#CM6882
		// Set the search condition for the Search Consignor screen.
		ForwardParameters param = new ForwardParameters();
		//#CM6883
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM6884
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM6885
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststockconsignor/ListStockConsignor.do",
			param,
			"/progress.do");
	}

	//#CM6886
	/** 
	 * Clicking on the Search Item Code button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displays the item list listbox using the search conditions.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *       Item code <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_PSearchItem_Click(ActionEvent e) throws Exception
	{
		//#CM6887
		// Set the search conditions for the Search Item screen.
		ForwardParameters param = new ForwardParameters();
		//#CM6888
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM6889
		// Item code
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM6890
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM6891
		// In process screen->Result screen 
		redirect("/stockcontrol/listbox/liststockitem/ListStockItem.do", param, "/progress.do");
	}

	//#CM6892
	/** 
	 * Clicking on the Search Start Location button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displays the location list listbox using the search conditions.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *       Item code <BR>
	 * 		 Case/Piece division <BR>
	 *       Start location <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchStrt_Click(ActionEvent e) throws Exception
	{
		//#CM6893
		// Set the search condition for the Search Location screen.
		ForwardParameters param = new ForwardParameters();
		//#CM6894
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM6895
		// Item code
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM6896
		// Case/PieceDivision
		//#CM6897
		// All
		if (rdo_Cpf_All.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM6898
		// Case
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM6899
		// Piece
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM6900
		// None
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM6901
		// Start location
		param.setParameter(ListStockLocationBusiness.LOCATION_KEY, txt_StartLocation.getText());
		//#CM6902
		// Start flag
		param.setParameter(
			ListStockLocationBusiness.RANGELOCATION_KEY,
			StockControlParameter.RANGE_START);
		//#CM6903
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM6904
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststocklocation/ListStockLocation.do",
			param,
			"/progress.do");
	}

	//#CM6905
	/** 
	 * Clicking on the Search End Location button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displays the location list listbox using the search conditions.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *       Item code <BR>
	 * 		 Case/Piece division <BR>
	 *       End location <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchEd_Click(ActionEvent e) throws Exception
	{
		//#CM6906
		// Set the search condition for the Search Location screen.
		ForwardParameters param = new ForwardParameters();
		//#CM6907
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM6908
		// Case/PieceDivision
		//#CM6909
		// All
		if (rdo_Cpf_All.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM6910
		// Case
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM6911
		// Piece
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM6912
		// None
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM6913
		// Item code
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM6914
		// End location
		param.setParameter(ListStockLocationBusiness.LOCATION_KEY, txt_EndLocation.getText());
		//#CM6915
		// End flag
		param.setParameter(
			ListStockLocationBusiness.RANGELOCATION_KEY,
			StockControlParameter.RANGE_END);
		//#CM6916
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM6917
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststocklocation/ListStockLocation.do",
			param,
			"/progress.do");
	}

	//#CM6918
	/** 
	 * Clicking on the Case ITF Search button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displays the Case ITF list listbox using the search conditions.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *       Item code <BR>
	 * 		 Case/Piece division <BR>
	 *       Start location <BR>
	 *       End location <BR>
	 *       Case ITF <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchCase_Click(ActionEvent e) throws Exception
	{
		//#CM6919
		// Set the search condition for the Search Case ITF screen.
		ForwardParameters param = new ForwardParameters();
		//#CM6920
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM6921
		// Item code
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM6922
		// Case/PieceDivision
		//#CM6923
		// All
		if (rdo_Cpf_All.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM6924
		// Case
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM6925
		// Piece
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM6926
		// None
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM6927
		// Start location
		param.setParameter(
			ListStockLocationBusiness.STARTLOCATION_KEY,
			txt_StartLocation.getText());
		//#CM6928
		// End location
		param.setParameter(ListStockLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getText());
		//#CM6929
		// Case ITF
		param.setParameter(ListStockCaseItfBusiness.CASEITF_KEY, txt_CaseItf.getText());
		//#CM6930
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM6931
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststockcaseitf/ListStockCaseItf.do",
			param,
			"/progress.do");
	}

	//#CM6932
	/** 
	 * Clicking on the Search bundle ITF button invokes this.<BR>
	 * <BR>
	 * Summary:set search conditions to the parameter, and display Bundle ITF listbox by these search conditions.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *       Item code <BR>
	 * 		 Case/Piece division <BR>
	 *       Start location <BR>
	 *       End location <BR>
	 *       Case ITF <BR>
	 *       Bundle ITF <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchBdl_Click(ActionEvent e) throws Exception
	{
		//#CM6933
		// Set the search condition for the Search Bundle ITF screen.
		ForwardParameters param = new ForwardParameters();
		//#CM6934
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM6935
		// Item code
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM6936
		// Case/PieceDivision
		//#CM6937
		// All
		if (rdo_Cpf_All.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM6938
		// Case
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM6939
		// Piece
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM6940
		// None
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM6941
		// Start location
		param.setParameter(
			ListStockLocationBusiness.STARTLOCATION_KEY,
			txt_StartLocation.getText());
		//#CM6942
		// End location
		param.setParameter(ListStockLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getText());
		//#CM6943
		// Case ITF
		param.setParameter(ListStockCaseItfBusiness.CASEITF_KEY, txt_CaseItf.getText());
		//#CM6944
		// Bundle ITF
		param.setParameter(ListStockBundleItfBusiness.BUNDLEITF_KEY, txt_BundleItf.getText());
		//#CM6945
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM6946
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststockbundleitf/ListStockBundleItf.do",
			param,
			"/progress.do");
	}

	//#CM6947
	/** 
	 * Clicking on the Display button invokes this.<BR>
	 * <BR>
	 * Summary: Stock info using the input item (count) in the input area as a condition and display it in the preset area.<BR>
	 * <BR>
	 * <DIR>
	 *   1.Check input field of input area (mandatory input)<BR>
	 *   2.Start schedule<BR>
	 *   3.Display it in the preset area.<BR>
	 *   4.Disable Picking start button, Picking qty clear button, Select All button, Release select all button and enable the Enable List Clear button.<BR>
	 *   5.Maintain the contents of the input area is still maintained.<BR>
	 * </DIR>
	 * <DIR>
	 *   [Line No. list of the list cell]<BR>
	 *   <BR>
	 *   <DIR>
	 *      1.Item code <BR>
	 *      2.Division <BR>
	 * 		3.Picking Location <BR>
	 * 		4.Packing qty per case <BR>
	 * 		5.allocation possible Case qty <BR>
	 * 		6.Picking Case qty <BR>
	 * 		7.All qty <BR>
	 * 		8.Expiry Date <BR>
	 * 		9.Case ITF <BR>
	 * 		10.Item name <BR>
	 * 		11.packed qty per bundle <BR>
	 * 		12.allocation possible Piece qty <BR>
	 * 		13.Picking Piece qty <BR>
	 * 		14.Bundle ITF <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM6948
			// Clear the preset area for input error.
			btn_ListClear_Click(e);

			//#CM6949
			// Input check
			txtl_WorkerCode.validate();
			txt_Password.validate();
			txt_ConsignorCode.validate();
			//#CM6950
			// Pattern matching characters
			txt_ItemCode.validate(false);
			txt_StartLocation.validate(false);
			txt_EndLocation.validate(false);
			txt_CaseItf.validate(false);
			txt_BundleItf.validate(false);
			txt_CustomerCode.validate(false);
			txt_CustomerName.validate(false);

			//#CM6951
			// Input characters check for eWareNavi
			if (!checkContainNgText())
			{
				return;
			}
			
			//#CM6952
			// Start Location shall locate lower than End Location
			if (!StringUtil.isBlank(txt_StartLocation.getText())
				&& !StringUtil.isBlank(txt_EndLocation.getText()))
			{
				if (txt_StartLocation.getText().compareTo(txt_EndLocation.getText()) > 0)
				{
					//#CM6953
					// 6023057=Please enter {1} or greater for {0}.
					message.setMsgResourceKey(
						"6023057"
							+ Message.MSG_DELIM
							+ DisplayText.getText(lbl_StartLocation.getResourceKey())
							+ Message.MSG_DELIM
							+ DisplayText.getText(lbl_EndLocation.getResourceKey()));
					return;
				}
			}
			//#CM6954
			// Set to the schedule parameter
			StockControlParameter param = new StockControlParameter();
			//#CM6955
			// Worker Code
			param.setWorkerCode(txtl_WorkerCode.getText());
			//#CM6956
			// Password
			param.setPassword(txt_Password.getText());
			//#CM6957
			// Consignor code
			param.setConsignorCodeDisp(txt_ConsignorCode.getText());
			//#CM6958
			// Item code
			param.setItemCodeDisp(txt_ItemCode.getText());

			//#CM6959
			// Case/Piece division
			if (rdo_Cpf_All.getChecked())
			{
				//#CM6960
				// All
				param.setCasePieceFlagDisp(StockControlParameter.CASEPIECE_FLAG_ALL);
			}
			else if (rdo_Cpf_Case.getChecked())
			{
				//#CM6961
				// Case
				param.setCasePieceFlagDisp(StockControlParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_Cpf_Piece.getChecked())
			{
				//#CM6962
				// Piece
				param.setCasePieceFlagDisp(StockControlParameter.CASEPIECE_FLAG_PIECE);
			}
			else if (rdo_Cpf_AppointOff.getChecked())
			{
				//#CM6963
				// None
				param.setCasePieceFlagDisp(StockControlParameter.CASEPIECE_FLAG_NOTHING);
			}

			//#CM6964
			// Start location
			param.setFromLocationNoDisp(txt_StartLocation.getText());
			//#CM6965
			// End location
			param.setToLocationNoDisp(txt_EndLocation.getText());
			//#CM6966
			// Case ITF
			param.setITFDisp(txt_CaseItf.getText());
			//#CM6967
			// Bundle ITF
			param.setBundleITFDisp(txt_BundleItf.getText());
			//#CM6968
			// Customer code
			param.setCustomerCode(txt_CustomerCode.getText());
			//#CM6969
			// Customer name
			param.setCustomerName(txt_CustomerName.getText());


			//#CM6970
			// Store to ViewState (for re-search after process)
			//#CM6971
			// Consignor code
			this.getViewState().setString(VSTCONSIGNOR, param.getConsignorCodeDisp());
			//#CM6972
			// Item code
			this.getViewState().setString(VSTITEMCODE, param.getItemCodeDisp());
			//#CM6973
			// Case/Piece division
			this.getViewState().setString(VSTCASEPIECE, param.getCasePieceFlagDisp());
			//#CM6974
			// Start location
			this.getViewState().setString(VSTLOCATIONFROM, param.getFromLocationNoDisp());
			//#CM6975
			// End location
			this.getViewState().setString(VSTLOCATIONTO, param.getToLocationNoDisp());
			//#CM6976
			// Case ITF
			this.getViewState().setString(VSTCASEITF, param.getITFDisp());
			//#CM6977
			// Bundle ITF
			this.getViewState().setString(VSTBUNDLEITF, param.getBundleITFDisp());
			
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new NoPlanRetrievalSCH();
			StockControlParameter[] viewParam =
				(StockControlParameter[]) schedule.query(conn, param);

			if (viewParam == null || viewParam.length == 0)
			{
				//#CM6978
				// Set the message
				message.setMsgResourceKey(schedule.getMessage());

				return;
			}

			//#CM6979
			// Set the list cell.
			setList(viewParam);

			//#CM6980
			// Set the message
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
				//#CM6981
				// Connection close
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM6982
	/** 
	 * Clicking on the Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area.<BR>
	 * <BR>
	 * <DIR>
	 *    1.Return the item (count) in the input area to the initial value.<BR>
	 *    <DIR>
	 *      Refer to <CODE>page_Load(ActionEvent e)</CODE>Method in regard with the initial value.<BR>
	 *    </DIR>
	 *    2.Set the cursor to the worker code.<BR>
	 *    3.Maintain the contents of preset area.<BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM6983
			// Consignor code
			txt_ConsignorCode.setText("");			
			//#CM6984
			// Item code
			txt_ItemCode.setText("");

			//#CM6985
			// Set the Case/Piece division to All.
			rdo_Cpf_All.setChecked(true);
			rdo_Cpf_Case.setChecked(false);
			rdo_Cpf_Piece.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);

			//#CM6986
			// Start location
			txt_StartLocation.setText("");
			//#CM6987
			// End location
			txt_EndLocation.setText("");
			//#CM6988
			// Case ITF
			txt_CaseItf.setText("");
			//#CM6989
			// Bundle ITF
			txt_BundleItf.setText("");
			//#CM6990
			// Customer code
			txt_CustomerCode.setText("");
			//#CM6991
			// Customer name
			txt_CustomerName.setText("");

			//#CM6992
			// Place a check on "Print a planned retrieval work report ".
			chk_CommonUse.setChecked(true);
			
			//#CM6993
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new NoPlanRetrievalSCH();
			StockControlParameter param = (StockControlParameter) schedule.initFind(conn, null);

			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				//#CM6994
				// When Consignor code is 1
				txt_ConsignorCode.setText(param.getConsignorCode());
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
				//#CM6995
				// Connection close
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

	//#CM6996
	/** 
	 * Clicking on the Start Picking button invokes this.<BR>
	 * <BR>
	 * Summary: With the info at preset area<BR>
	 * <BR>
	 * <DIR>
	 *	  1.Set the cursor to the worker code.<BR>
	 *    2.Display the dialog box to allow to confirm to add it or not.<BR>
	 *    <DIR>
	 *      "Do you pick it?"<BR>
	 * 		[Check dialog cancel]<BR>
	 *			<DIR>
	 *				Disable
	 *			</DIR>
	 * 		[Check dialog OK]<BR>
	 *			<DIR>
	 *				1.Start the schedule.<BR>
	 *				<DIR>
	 *   				[parameter]<BR>
	 * 					<DIR>
	 * 						Worker Code <BR>
	 * 						Password <BR>
	 * 						Customer code <BR>
	 * 						Customer name <BR>
	 * 						Unplanned picking work report Printing Division <BR>
	 *                      Consignor code <BR>
	 *                      Consignor name <BR>
	 *						Preset.Item code <BR>
	 *						Preset.Item name <BR>
	 *						Preset.Case/Piece division <BR>
	 *						Preset.Location No. <BR>
	 *						Preset.Packing qty per case <BR>
	 *						Preset.packed qty per bundle <BR>
	 *						Preset.Case picking qty<BR>
	 *						Preset.Piece picking qty <BR>
	 *						Preset.Case ITF <BR>
	 *						Preset.Bundle ITF <BR>
	 *						Preset.Expiry Date <BR>
	 *						Preset.Full qty Division <BR>
	 *						Preset.Stock ID <BR>
	 *						Preset.last update date/time <BR>
	 *						Line No.<BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.Input are when result of the schedule is true.<BR>
	 *				3.re-display info of preset<BR>
	 *              4.When preset info is 0, then, revoke Picking start, Picking qty clear, Select All, Release select all, List clear button.<br>
	 *                to disable to use.<BR>
	 *              5.When preset info is 0, then, Clear Consignor code, Consignor name of preset.<BR>
	 *    			Output the message obtained from the schedule to the screen<BR>
	 *			</DIR>
	 *    </DIR>
	 * <BR>
	 * 	 Line No. list of the list cell<BR>
	 *   <DIR>
	 *      1.Item code <BR>
	 *      2.Division <BR>
	 * 		3.Picking Location <BR>
	 * 		4.Packing qty per case <BR>
	 * 		5.allocation possible Case qty <BR>
	 * 		6.Picking Case qty <BR>
	 * 		7.All qty<BR>
	 * 		8.Expiry Date <BR>
	 * 		9.Case ITF <BR>
	 * 		10.Item name <BR>
	 * 		11.packed qty per bundle <BR>
	 * 		12.allocationEnd Start No.<BR>
	 * 		13.Picking Piece qty <BR>
	 * 		14.Bundle ITF <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_RetrievalStart_Click(ActionEvent e) throws Exception
	{
		//#CM6997
		// Input check
		txtl_WorkerCode.validate();
		txt_Password.validate();

		txt_CustomerCode.validate(false);
		txt_CustomerName.validate(false);
		//#CM6998
		// Input characters check for eWareNavi
		if (!checkContainNgText())
		{
			return;
		}
		
		//#CM6999
		// Obtain maximum (max) number of lines.
		int index = lst_SNoPlanRetrieval.getMaxRows();

		for (int i = 1; i < index; i++)
		{
			try
			{
				//#CM7000
				// Designate the line.
				lst_SNoPlanRetrieval.setCurrentRow(i);
				//#CM7001
				// Check for caracters matching to the pattern in the designated row (expiry date).
				lst_SNoPlanRetrieval.validate(8, false);

				//#CM7002
				// Input characters check for eWareNavi
				if (!checkContainNgText(i))
				{
					return;
				}
			}
			catch (ValidateException ve)
			{
				//#CM7003
				// Set the message
				String errorMessage =
					MessageResources.getText(ve.getErrorNo(), ve.getBinds().toArray());
				//#CM7004
				// 6023273 = No.{0}{1}
				throw new ValidateException("6023273", Integer.toString(i), errorMessage);
			}
		}

		Connection conn = null;
		try
		{
			Vector vecParam = new Vector(index);
			for (int i = 1; i < index; i++)
			{
				//#CM7005
				// Designate the line.
				lst_SNoPlanRetrieval.setCurrentRow(i);
				
				//#CM7006
				// Place a check in All picking when no check placed in All.
				if (lst_SNoPlanRetrieval.getChecked(7) == false)
				{
					//#CM7007
					// Obtain the concatenated parameter from the list cell. If any difference with the input value
					//#CM7008
					// Picking Case qty
					if (CollectionUtils.getString(3, lst_SNoPlanRetrieval.getValue(0)).equals(lst_SNoPlanRetrieval.getValue(6)))
					{
						//#CM7009
						// Picking Piece qty
						if (CollectionUtils.getString(4, lst_SNoPlanRetrieval.getValue(0)).equals(lst_SNoPlanRetrieval.getValue(13)))
						{
							continue;
						}
					}
				}
				
				//#CM7010
				// Set to the schedule parameter
				StockControlParameter param = new StockControlParameter();
				//#CM7011
				// Worker Code
				param.setWorkerCode(txtl_WorkerCode.getText());
				//#CM7012
				// Password
				param.setPassword(txt_Password.getText());
				//#CM7013
				// Customer code
				param.setCustomerCode(txt_CustomerCode.getText());
				//#CM7014
				// Customer name
				param.setCustomerName(txt_CustomerName.getText());
				
				//#CM7015
				// Terminal No.
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());	
							
				//#CM7016
				// Print Unplanned picking work report
				param.setListFlg(chk_CommonUse.getChecked());
				//#CM7017
				// Consignor code(Read out only)
				param.setConsignorCode(txt_RConsignorCode.getText());
				//#CM7018
				// Consignor name(Read out only)
				param.setConsignorName(txt_RConsignorName.getText());

				param.setItemCode(lst_SNoPlanRetrieval.getValue(1));
				param.setLocationNo(lst_SNoPlanRetrieval.getValue(3));

				//#CM7019
				// Translate from value type to comma compiled string.
				param.setEnteringQty(WmsFormatter.getInt(lst_SNoPlanRetrieval.getValue(4)));
				param.setAllocateCaseQty(WmsFormatter.getInt(lst_SNoPlanRetrieval.getValue(5)));

				//#CM7020
				// Placing a check in All picking enables to pick all picking qty.
				if (lst_SNoPlanRetrieval.getChecked(7) == true)
				{
					param.setRetrievalCaseQty(
							WmsFormatter.getInt(lst_SNoPlanRetrieval.getValue(5)));
				}
				else
				{
					if (lst_SNoPlanRetrieval.getValue(6).equals(""))
					{
						param.setRetrievalCaseQty(0);
					}
					else
					{
						param.setRetrievalCaseQty(
							WmsFormatter.getInt(lst_SNoPlanRetrieval.getValue(6)));
					}
				}

				param.setTotalFlag(lst_SNoPlanRetrieval.getChecked(7));
				param.setUseByDate(lst_SNoPlanRetrieval.getValue(8));
				param.setITF(lst_SNoPlanRetrieval.getValue(9));
				param.setItemName(lst_SNoPlanRetrieval.getValue(10));

				//#CM7021
				// Translate from value type to comma compiled string.
				param.setBundleEnteringQty(WmsFormatter.getInt(lst_SNoPlanRetrieval.getValue(11)));
				param.setAllocatePieceQty(WmsFormatter.getInt(lst_SNoPlanRetrieval.getValue(12)));

				//#CM7022
				// Placing a check in All picking enables to pick all picking qty.
				if (lst_SNoPlanRetrieval.getChecked(7) == true)
				{
					param.setRetrievalPieceQty(
							WmsFormatter.getInt(lst_SNoPlanRetrieval.getValue(12)));
				}
				else
				{
					if (lst_SNoPlanRetrieval.getValue(13).equals(""))
					{
						param.setRetrievalPieceQty(0);
					}
					else
					{
						param.setRetrievalPieceQty(
							WmsFormatter.getInt(lst_SNoPlanRetrieval.getValue(13)));
					}
				}

				param.setBundleITF(lst_SNoPlanRetrieval.getValue(14));

				//#CM7023
				// Set HIDDENItem(Division(0),Stock ID(1),last update date/time(2)
				String hidden = lst_SNoPlanRetrieval.getValue(0);
				param.setCasePieceFlag(CollectionUtils.getString(0, hidden));
				param.setStockId(CollectionUtils.getString(1, hidden));
				//#CM7024
				// last update date/time
				//#CM7025
				// Translate from String type (YYYYMMDDHHMMSS) to Date type.
				param.setLastUpdateDate(
					WmsFormatter.getTimeStampDate(CollectionUtils.getString(2, hidden)));
					
				//#CM7026
				// Maintain line No.
				param.setRowNo(i);


				//#CM7027
				// Set the search conditions for re-search after starting the picking.
				param.setConsignorCodeDisp(this.getViewState().getString(VSTCONSIGNOR));
				param.setItemCodeDisp(this.getViewState().getString(VSTITEMCODE));
				param.setCasePieceFlagDisp(this.getViewState().getString(VSTCASEPIECE));
				param.setFromLocationNoDisp(this.getViewState().getString(VSTLOCATIONFROM));
				param.setToLocationNoDisp(this.getViewState().getString(VSTLOCATIONTO));
				param.setITFDisp(this.getViewState().getString(VSTCASEITF));
				param.setBundleITFDisp(this.getViewState().getString(VSTBUNDLEITF));
				
				
				vecParam.addElement(param);
			}
			if (vecParam.size() <= 0)
			{
				//#CM7028
				// 6023154=There is no data to update.
				message.setMsgResourceKey("6023154");
				return;
			}

			
			StockControlParameter[] paramArray = new StockControlParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			WmsScheduler schedule = new NoPlanRetrievalSCH();
			//#CM7029
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM7030
			// Start schedule
			StockControlParameter[] viewParam =
				(StockControlParameter[]) schedule.startSCHgetParams(conn, paramArray);

			if (viewParam == null)
			{
				conn.rollback();
				//#CM7031
				// Set the message
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.commit();
				//#CM7032
				// Set the message
				message.setMsgResourceKey(schedule.getMessage());
				//#CM7033
				// When the target data is lost
				btn_ListClear_Click(e);
				
				if (viewParam.length != 0)
				{
					//#CM7034
					// Set the list cell.
					setList(viewParam);
				}

			}
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
				//#CM7035
				// Connection close
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

	//#CM7036
	/** 
	 * Clicking on the Clear Picking Qty button invokes this. <BR>
	 * <BR>
	 * Summary:Clear off all Nos. placed in preset Picking Case qty and preset Picking Piece qty. <BR>
	 * <BR>
	 * <DIR>
	 *     1.Clear all checks placed in al preset Picking Case qty and preset Picking Piece qty.<BR>
	 *     2.Make All option false for all preset info. <BR>
	 *     3.Set the cursor to the worker code.<BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_RetrievalQtyClear_Click(ActionEvent e) throws Exception
	{
		//#CM7037
		// Set the list cell.
		for (int i = 1; i < lst_SNoPlanRetrieval.getMaxRows(); i++)
		{
			lst_SNoPlanRetrieval.setCurrentRow(i);
			lst_SNoPlanRetrieval.setValue(6, (""));
			lst_SNoPlanRetrieval.setChecked(7, false);
			lst_SNoPlanRetrieval.setValue(13, (""));
		}
	}

	//#CM7038
	/** 
	 * Clicking on the Select All button invokes this. <BR>
	 * <BR>
	 * Summary: Place checks in All option for the all preset info.<BR>
	 * <BR>
	 * <DIR>
	 *     1.Place checks in All option for all preset info. <BR>
	 *     2.Place all allocatable case qty value in preset picking case qty. <BR>
	 *     3.Place all allocatable Piece qty value in preset picking case qty.<BR>
	 *     4.Set the cursor to the worker code. <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_AllCheck_Click(ActionEvent e) throws Exception
	{
		//#CM7039
		// Set the list cell.
		for (int i = 1; i < lst_SNoPlanRetrieval.getMaxRows(); i++)
		{
			lst_SNoPlanRetrieval.setCurrentRow(i);
			lst_SNoPlanRetrieval.setChecked(7, true);
		}
	}

	//#CM7040
	/** 
	 * Clicking on the Cancel Select All button invokes this. <BR>
	 * <BR>
	 * Summary: Remove all checks placed in All option for the preset info.<BR>
	 * <BR>
	 * <DIR>
	 *     1.Make all presets to no-check.<BR>
	 *     2.Set the cursor to the worker code. <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_AllCheckClear_Click(ActionEvent e) throws Exception
	{
		//#CM7041
		// Set the list cell.
		for (int i = 1; i < lst_SNoPlanRetrieval.getMaxRows(); i++)
		{
			lst_SNoPlanRetrieval.setCurrentRow(i);
			lst_SNoPlanRetrieval.setChecked(7, false);
		}
	}

	//#CM7042
	/** 
	 * Clicking on the List Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clear all the display info of preset<BR>
	 * <BR>
	 * <DIR>
	 *    1.Display the dialog box to allow to confirm to check the preset info.<BR>
	 *    <DIR>
	 * 	    "This clears the list input info. Do you confirm it?"<BR>
	 * 		[Check dialog cancel]<BR>
	 *			<DIR>
	 *				Disable
	 *			</DIR>
	 * 		[Check dialog OK]<BR>
	 *			<DIR>
	 *				1.Clear all the display info of preset<BR>
	 *				2.Revoke Picking start button, Picking qty clear button, Select All, Release select all, List clear button<BR>
	 *				3.Set the cursor to the worker code.<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		//#CM7043
		// Delete all lines
		lst_SNoPlanRetrieval.clearRow();

		//#CM7044
		// Consignor code(Read out only)
		txt_RConsignorCode.setText("");
		//#CM7045
		// Consignor name(Read out only)
		txt_RConsignorName.setText("");

		//#CM7046
		// Disable button press down
		//#CM7047
		//Picking start button
		btn_RetrievalStart.setEnabled(false);
		//#CM7048
		// Picking qty clear button
		btn_RetrievalQtyClear.setEnabled(false);
		//#CM7049
		// Select All
		btn_AllCheck.setEnabled(false);
		//#CM7050
		// Release select all
		btn_AllCheckClear.setEnabled(false);
		//#CM7051
		// List clear button
		btn_ListClear.setEnabled(false);
	}

}
//#CM7052
//end of class
