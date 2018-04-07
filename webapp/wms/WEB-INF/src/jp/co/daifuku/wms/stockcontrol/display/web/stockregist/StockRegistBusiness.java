// $Id: StockRegistBusiness.java,v 1.2 2006/10/04 05:05:14 suresh Exp $

//#CM8594
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.stockregist;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
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
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.utility.DateUtil;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocation.ListStockLocationBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockmaintenance.ListStockMaintenanceBusiness;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;
import jp.co.daifuku.wms.stockcontrol.schedule.StockRegistSCH;

//#CM8595
/**
 * Designer : T.Yoshino <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * This is Inventory maintenance add class.<BR>
 * Set the contents input from the screen to the parameter, and<BR>
 * Schedule add Inventory maintenance based on the parameter.<BR>
 * Receive the result from the schedule and receive true when the process normally completed<BR>
 * Receive false when failed to schedule completely due to condition error or otether causes.<BR>
 * Output the message obtained from the schedule to the screen<BR>
 *  Set the contents input from the screen to the parameter, and the schedule search the data for display based on the parameter.<BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * 1.Process by clicking on the Input button(<CODE>btn_Input_Click()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *  Set the and contents entered via the input area for the parameter, and the schedule checks the input condition based on the parameter.<BR>
 *  Receive the result from the schedule and receive true when the process normally completed<BR>
 *  Receive false when failed to schedule completely due to condition error or otether causes.<BR>
 *  Output the message obtained from the schedule to the screen<BR>
 *  Add input area info to the preset area if the schedule result is true.<BR>
 *  Clicking on the Input button after clicking the Modify button updates the target preset data to be modified with the info in the input area.<BR>
 *  <BR>
 * 	[parameter] *Mandatory Input<BR>
 * 	<BR>
 * 	<DIR>
 * 		Worker Code* <BR>
 * 		Password* <BR>
 * 		Consignor code* <BR>
 * 		Consignor name <BR>
 * 		Item code* <BR>
 * 		Item name <BR>
 * 		Case/PieceDivision* <BR>
 * 		Location* <BR>
 * 		Packing qty per case*2 *4<BR>
 * 		Stock Case Qty*2 <BR>
 * 		Case ITF <BR>
 * 		packed qty per bundle <BR>
 *		Stock piece qty *4 <BR>
 *		Bundle ITF <BR>
 *		Storage Date* <BR>
 *		Storage Time <BR>
 *		Expiry Date <BR>
 *		Prints Unplanned storage list.<BR>
 *		<BR>
 * 		*2 <BR>
 * 		When Case/PieceDivision is 1:Case, Mandatory Input <BR>
 * 		*3 <BR>
 * 		When Case/PieceDivision is 2:Piece, Mandatory Input <BR>
 * 		*4 <BR>
 * 		When Case/PieceDivision is 0:None, <BR>
 *		storage case qty <BR>
 * 		case qty x packed qty per case+ piece qty should not be 0.(>0) <BR>
 * 	</DIR>
 *  <BR>
 *  [Output data] <BR>
 *  <BR>
 * 	<DIR>
 *  	Consignor code <BR>
 *  	Consignor name <BR>
 *  	Item code <BR>
 *  	Item name <BR>
 *  	Case/Piece division <BR>
 *  	Location <BR>
 *  	Packing qty per case <BR>
 *  	packed qty per bundle <BR>
 *  	Stock Case Qty <BR>
 *  	Stock piece qty <BR>
 *  	Case ITF <BR>
 *  	Bundle ITF <BR>
 *  	Storage Date <BR>
 *  	Storage Time <BR>
 *  	Expiry Date <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.Add button press down process(<CODE>btn_StorageStart_Click()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *  Set the contents input via preset area for the parameter, and the schedule execte inventory maintenance ass based on the parameter.<BR>
 *  Receive the result from the schedule and receive true when the process normally completed<BR>
 *  Receive false when failed to schedule completely due to condition error or otether causes.<BR>
 *  Output the message obtained from the schedule to the screen<BR>
 * 	<DIR>
 *   [parameter] *Mandatory Input <BR>
 *   <BR>
 *   Worker Code* <BR>
 *   Password* <BR>
 *   Consignor code* <BR>
 *   Consignor name*<BR>
 *   Item code* <BR>
 *   Item name <BR>
 *   Case/Piece division* <BR>
 *   Location No.* <BR>
 *   Packing qty per case* <BR>
 *   packed qty per bundle* <BR>
 *   Stock Case Qty* <BR>
 *   Stock piece qty* <BR>
 *   Case ITF <BR>
 *   Bundle ITF <BR>
 *   Picking date* <BR>
 *   Picking time<BR>
 *   Expiry Date <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/9/29</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:05:14 $
 * @author  $Author: suresh $
 */
public class StockRegistBusiness extends StockRegist implements WMSConstants
{
	//#CM8596
	// Class fields --------------------------------------------------

	//#CM8597
	// Class variables -----------------------------------------------

	//#CM8598
	// Class method --------------------------------------------------

	//#CM8599
	// Constructors --------------------------------------------------

	//#CM8600
	// Public methods ------------------------------------------------

	//#CM8601
	/**
	 * Initialize the screen<BR>
	 * <BR>
	 * Summary: Shows initial display of the screen.<BR>
	 * <BR>
	 * <DIR>
	 *    1.When Consignor is one<BR>
	 *    2.Set the cursor to the worker code.<BR>
	 *    3.Make Preset button(Add, List clear)unvalid.<BR>
	 * <BR>
	 *    Subject name [Initial value]<BR>
	 *    <DIR>
	 * 		Worker Code	[None] <BR>
	 * 		Password		[None] <BR>
	 * 		Consignor code		[When it is one count, Set Consignor code of Stock info] <BR>
	 * 		Consignor name		[When it is one count, Set Consignor code of Stock name] <BR>
	 * 		Item code		[None] <BR>
	 * 		Item name		[None] <BR>
	 * 		Case/PieceDivision[Case] <BR>
	 * 		Store Location			[None] <BR>
	 * 		Packing qty per case		[None] <BR>
	 * 		Store Case qty	[None] <BR>
	 * 		Case ITF		[None] <BR>
	 * 		packed qty per bundle		[None] <BR>
	 * 		Storage Piece Qty	[None] <BR>
	 * 		Bundle ITF		[None] <BR>
	 * 		Storage Date			[None] <BR>
	 * 		Storage Time		[None] <BR>
	 * 		Expiry Date		[None] <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{

		//#CM8602
		// Set the a preset line No. in ViewState to determine the status being in Modify by clicking the Modify button.
		//#CM8603
		// (Default:-1)
		this.getViewState().setInt("LineNo", -1);

		//#CM8604
		// Disable button press down
		//#CM8605
		// Start Storage button
		btn_Submit.setEnabled(false);
		//#CM8606
		// List clear button
		btn_ListClear.setEnabled(false);

		//#CM8607
		// Show initial display
		setFirstDisp();
	}

	//#CM8608
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
			//#CM8609
			// Obtain parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM8610
			// Store to ViewState
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM8611
			// Set the screen name
			lbl_SettingName.setResourceKey(title);
		}
		//#CM8612
		// Clicking on the Add button, display Check dialog "Do you add this?"
		btn_Submit.setBeforeConfirm("MSG-W0009");

		//#CM8613
		// Clicking on the List Clear button invokes dialog to confirm: " This clears the list input info. Do you confirm it? "
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		//#CM8614
		// Set the cursor to the worker code
		setFocus(txt_WorkerCode);
	}

	//#CM8615
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
		//#CM8616
		// Obtain the parameter selected in the listbox.
		//#CM8617
		// Consignor code
		String consignorcode = param.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM8618
		// Consignor name
		String consignorname = param.getParameter(ListStockConsignorBusiness.CONSIGNORNAME_KEY);
		//#CM8619
		// Item code
		String itemcode = param.getParameter(ListStockItemBusiness.ITEMCODE_KEY);
		//#CM8620
		// Item name
		String itemname = param.getParameter(ListStockItemBusiness.ITEMNAME_KEY);
		//#CM8621
		// Case/PieceDivision
		String casepieseflag = param.getParameter(ListStockLocationBusiness.CASEPIECEFLAG_KEY);
		//#CM8622
		// Location
		String storagelocation = param.getParameter(ListStockLocationBusiness.LOCATION_KEY);
		//#CM8623
		// Packing qty per case
		String caseqty = param.getParameter(ListStockMaintenanceBusiness.ENTERINGQTY_KEY);
		//#CM8624
		// packed qty per bundle
		String bundleqty = param.getParameter(ListStockMaintenanceBusiness.BUNDLE_ENTERINGQTY_KEY);
		//#CM8625
		// Case ITF
		String caseitf = param.getParameter(ListStockMaintenanceBusiness.CASEITF_KEY);
		//#CM8626
		// Bundle ITF
		String bundleitf = param.getParameter(ListStockMaintenanceBusiness.BUNDLEITF_KEY);
		//#CM8627
		// Storage Date
		String instockday = param.getParameter(ListStockMaintenanceBusiness.INSTOCKDAY_KEY);
		//#CM8628
		// Storage Date/Time
		String instocktime = param.getParameter(ListStockMaintenanceBusiness.INSTOCKTIME_KEY);
		//#CM8629
		// Expiry Date
		String usebydate = param.getParameter(ListStockMaintenanceBusiness.USEBYDATE_KEY);
		

		//#CM8630
		// Set the value if not empty.
		//#CM8631
		// Consignor code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM8632
		// Consignor name
		if (!StringUtil.isBlank(consignorname))
		{
			txt_ConsignorName.setText(consignorname);
		}
		//#CM8633
		// Item code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM8634
		// Item name
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemName.setText(itemname);
		}
		//#CM8635
		// Case/PieceDivision
		if (!StringUtil.isBlank(casepieseflag))
		{
			if (casepieseflag.equals(StockControlParameter.CASEPIECE_FLAG_CASE))
			{
				rdo_CpfCase.setChecked(true);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAppointOff.setChecked(false);
			}
			else if (casepieseflag.equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
			{
				rdo_CpfCase.setChecked(false);
				rdo_CpfPiece.setChecked(true);
				rdo_CpfAppointOff.setChecked(false);
			}
			else if (casepieseflag.equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				rdo_CpfCase.setChecked(false);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAppointOff.setChecked(true);
			}
		}
		//#CM8636
		// Location
		if (!StringUtil.isBlank(storagelocation))
		{
			txt_Location.setText(storagelocation);
		}
		//#CM8637
		// Packing qty per case
		if (!StringUtil.isBlank(caseqty))
		{
			txt_CaseEntering.setText(caseqty);
		}
		//#CM8638
		// packed qty per bundle
		if (!StringUtil.isBlank(bundleqty))
		{
			txt_BundleEntering.setText(bundleqty);
		}
		//#CM8639
		// Case ITF
		if (!StringUtil.isBlank(caseitf))
		{
			txt_CaseItf.setText(caseitf);
		}
		//#CM8640
		// Bundle ITF
		if (!StringUtil.isBlank(bundleitf))
		{
			txt_BundleItf.setText(bundleitf);
		}
		//#CM8641
		// Storage Date
		if (!StringUtil.isBlank(instockday))
		{
			txt_StorageDate.setText(instockday);
		}
		//#CM8642
		// Storage Date/Time
		if (!StringUtil.isBlank(instocktime))
		{
			txt_StorageTime.setText(instocktime);
		}
		//#CM8643
		// Expiry Date
		if (!StringUtil.isBlank(usebydate))
		{
			txt_UseByDate.setText(usebydate);
		}

	}

	//#CM8644
	// Package methods -----------------------------------------------

	//#CM8645
	// Protected methods ---------------------------------------------
	//#CM8646
	/**
	 * Execute input check.
	 * Set the message if any error and return false.
	 * 
	 * @return true:Normal false: Abnormal
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
		if (!checker.checkContainNgText(txt_ConsignorCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ConsignorName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ItemCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ItemName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_Location))
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
		if (!checker.checkContainNgText(txt_UseByDate))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
	
		return true;
		
	}

	//#CM8647
	// Private methods -----------------------------------------------

	//#CM8648
	/**
	 * Invoke this method to display the initial display or clear.
	 * @throws Exception Reports all the exceptions. 
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM8649
			// 20050822 modify start Y.Kagawa
			//#CM8650
			// Consignor code
			txt_ConsignorCode.setText("");
			//#CM8651
			// Consignor name
			txt_ConsignorName.setText("");
			//#CM8652
			// Item code
			txt_ItemCode.setText("");
			//#CM8653
			// Item name
			txt_ItemName.setText("");
			//#CM8654
			// Case/PieceDivision
			rdo_CpfCase.setChecked(true);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);
			//#CM8655
			// Location
			txt_Location.setText("");
			//#CM8656
			// Packing qty per case
			txt_CaseEntering.setText("");
			//#CM8657
			// Stock Case Qty
			txt_StcCaseQty.setText("");
			//#CM8658
			// Case ITF
			txt_CaseItf.setText("");
			//#CM8659
			// packed qty per bundle
			txt_BundleEntering.setText("");
			//#CM8660
			// Stock piece qty
			txt_StcPieceQty.setText("");
			//#CM8661
			// Bundle ITF
			txt_BundleItf.setText("");
			//#CM8662
			// Stock time
			txt_StorageTime.setText("");
			//#CM8663
			// Expiry Date
			txt_UseByDate.setText("");
			
			//#CM8664
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new StockRegistSCH();

			StockControlParameter param = (StockControlParameter) schedule.initFind(conn, null);
			//#CM8665
			// When Consignor code is 1
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
				txt_ConsignorName.setText(param.getConsignorName());
			}
			else
			{
				txt_ConsignorCode.setText("");
				txt_ConsignorName.setText("");
			}
			//#CM8666
			// Initialize the Storage Date (Set the System date.)
			txt_StorageDate.setDate(new Date());

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM8667
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

	//#CM8668
	/** This method sets the preset area data in the Parameter class.<BR>
	 * <BR>
	 * Summary: Sets the preset area data in the Parameter class.<BR>
	 * <BR>
	 * 1.if it is new input<BR>
	 * 2.If the input data is in process of modifying<BR>
	 * 3.Clicking on the Add button sets all the preset data in the Parameter class. (Preset line No. to be modified=-1)<BR>
	 * <DIR>
	 *   	[parameter] *Mandatory Input<BR>
	 *   	<DIR>
	 * 			Preset Line No. to be corrected* <BR>
	 * 		</DIR>
	 *   	[Returned data]<BR>
	 *   	<DIR>
	 * 			Instance array of <CODE>StockControlParameter</CODE> that has the contents of the Preset area<BR>
	 * 		</DIR>
	 * </DIR>
	 * @param lineno Line No. of the list cell
	 * @return StockControlParameter[] Object array that has the contents of the Preset area
	 * @throws Exception Reports all the exceptions. 
	 */
	private StockControlParameter[] setListParam(int lineno) throws Exception
	{

		Vector vecParam = new Vector();

		for (int i = 1; i < lst_SStockRegist.getMaxRows(); i++)
		{
			//#CM8669
			// Exclude the line to be modefied.
			if (i == lineno)
			{
				continue;
			}

			//#CM8670
			// Designate the line.
			lst_SStockRegist.setCurrentRow(i);

			//#CM8671
			// Set to the schedule parameter
			StockControlParameter param = new StockControlParameter();
			//#CM8672
			// Input area info
			//#CM8673
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM8674
			// Password
			param.setPassword(txt_Password.getText());
			//#CM8675
			// Terminal No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());
			//#CM8676
			// Consignor code
			param.setConsignorCode(lst_SStockRegist.getValue(3));
			//#CM8677
			// Item code
			param.setItemCode(lst_SStockRegist.getValue(4));
			//#CM8678
			// Case/PieceDivision
			if (lst_SStockRegist.getValue(5).equals(DisplayText.getText("LBL-W0374")))
			{
				//#CM8679
				// None
				param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_NOTHING);
			}
			else if (lst_SStockRegist.getValue(5).equals(DisplayText.getText("LBL-W0375")))
			{
				//#CM8680
				// Case
				param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_CASE);
			}
			else if (lst_SStockRegist.getValue(5).equals(DisplayText.getText("LBL-W0376")))
			{
				//#CM8681
				// Piece
				param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_PIECE);
			}
			//#CM8682
			// Location
			param.setLocationNo(lst_SStockRegist.getValue(6));
			//#CM8683
			// Packing qty per case
			param.setEnteringQty(Formatter.getInt(lst_SStockRegist.getValue(7)));
			//#CM8684
			// Stock Case Qty
			param.setStockCaseQty(Formatter.getInt(lst_SStockRegist.getValue(8)));
			//#CM8685
			// Case ITF
			param.setITF(lst_SStockRegist.getValue(9));
			//#CM8686
			// Expiry Date
			param.setUseByDate(lst_SStockRegist.getValue(11));
			//#CM8687
			// Consignor name
			param.setConsignorName(lst_SStockRegist.getValue(12));
			//#CM8688
			// Item name
			param.setItemName(lst_SStockRegist.getValue(13));
			//#CM8689
			// packed qty per bundle
			param.setBundleEnteringQty(Formatter.getInt(lst_SStockRegist.getValue(14)));
			//#CM8690
			// Stock piece qty
			param.setStockPieceQty(Formatter.getInt(lst_SStockRegist.getValue(15)));
			//#CM8691
			// Bundle ITF
			param.setBundleITF(lst_SStockRegist.getValue(16));

			//#CM8692
			// Maintain the line No.
			param.setRowNo(i);

			//#CM8693
			// Storage Date and storage Time
			param.setStorageDate(
				WmsFormatter.getTimeStampDate(
					lst_SStockRegist.getValue(10),
					lst_SStockRegist.getValue(17),
					this.getHttpRequest().getLocale()));

			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			//#CM8694
			// Set the value if any preset data to be set.
			StockControlParameter[] listparam = new StockControlParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			//#CM8695
			// Set null if no preset data to be set.
			return null;
		}
	}

	//#CM8696
	/**
	 * This method sets the value in the preset area.<BR>
	 * <BR>
	 * Summary: Sets value for preset area<BR>
	 * 
	 * @param storageTime Storage Time
	 * @throws Exception Reports all the exceptions. 
	 */
	private void setList(String storageTime) throws Exception
	{
		//#CM8697
		// Compile the data for ToolTip
		ToolTipHelper toolTip = new ToolTipHelper();
		//#CM8698
		// Consignor name
		toolTip.add(DisplayText.getText("LBL-W0026"), txt_ConsignorName.getText());
		//#CM8699
		// Item name
		toolTip.add(DisplayText.getText("LBL-W0103"), txt_ItemName.getText());
		//#CM8700
		// Case ITF
		toolTip.add(DisplayText.getText("LBL-W0010"), txt_CaseItf.getText());
		//#CM8701
		// Bundle ITF
		toolTip.add(DisplayText.getText("LBL-W0006"), txt_BundleItf.getText());
		//#CM8702
		// Storage Date
		toolTip.add(DisplayText.getText("LBL-W0237"), txt_StorageDate.getText());
		//#CM8703
		// Stock time
		if (!StringUtil.isBlank(txt_StorageTime.getTime()))
		{
			toolTip.add(DisplayText.getText("LBL-W0368"), txt_StorageTime.getText());
		}
		else
		{
			toolTip.add(DisplayText.getText("LBL-W0368"), storageTime);
		}
		//#CM8704
		// Expiry Date
		toolTip.add(DisplayText.getText("LBL-W0270"), txt_UseByDate.getText());

		//#CM8705
		// Set ToolTip to current line
		lst_SStockRegist.setToolTip(lst_SStockRegist.getCurrentRow(), toolTip.getText());

		//#CM8706
		// Consignor code
		lst_SStockRegist.setValue(3, txt_ConsignorCode.getText());
		//#CM8707
		// Item code
		lst_SStockRegist.setValue(4, txt_ItemCode.getText());
		//#CM8708
		// Division
		if (rdo_CpfAppointOff.getChecked())
		{
			//#CM8709
			// None
			lst_SStockRegist.setValue(5, DisplayText.getText("LBL-W0374"));
		}
		else if (rdo_CpfCase.getChecked())
		{
			//#CM8710
			// Case
			lst_SStockRegist.setValue(5, DisplayText.getText("LBL-W0375"));
		}
		else if (rdo_CpfPiece.getChecked())
		{
			//#CM8711
			// Piece
			lst_SStockRegist.setValue(5, DisplayText.getText("LBL-W0376"));
		}
		//#CM8712
		// Location
		lst_SStockRegist.setValue(6, txt_Location.getText());
		//#CM8713
		// Packing qty per case
		if (StringUtil.isBlank(txt_CaseEntering.getText()))
		{
			lst_SStockRegist.setValue(7, "0");
		}
		else
		{
			lst_SStockRegist.setValue(7, WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));

		}
		//#CM8714
		// Stock Case Qty
		if (StringUtil.isBlank(txt_StcCaseQty.getText()))
		{
			lst_SStockRegist.setValue(8, "0");
		}
		else
		{
			lst_SStockRegist.setValue(8, WmsFormatter.getNumFormat(txt_StcCaseQty.getInt()));
		}
		//#CM8715
		// Case ITF
		lst_SStockRegist.setValue(9, txt_CaseItf.getText());
		//#CM8716
		// Storage Date
		lst_SStockRegist.setValue(10, txt_StorageDate.getText());
		//#CM8717
		// Expiry Date
		lst_SStockRegist.setValue(11, txt_UseByDate.getText());
		//#CM8718
		// Consignor name
		lst_SStockRegist.setValue(12, txt_ConsignorName.getText());
		//#CM8719
		// Item name
		lst_SStockRegist.setValue(13, txt_ItemName.getText());
		//#CM8720
		// packed qty per bundle
		if (StringUtil.isBlank(txt_BundleEntering.getText()))
		{
			lst_SStockRegist.setValue(14, "0");
		}
		else
		{
			lst_SStockRegist.setValue(14, WmsFormatter.getNumFormat(txt_BundleEntering.getInt()));
		}
		//#CM8721
		// Stock piece qty
		if (StringUtil.isBlank(txt_StcPieceQty.getText()))
		{
			lst_SStockRegist.setValue(15, "0");
		}
		else
		{
			lst_SStockRegist.setValue(15, WmsFormatter.getNumFormat(txt_StcPieceQty.getInt()));
		}
		//#CM8722
		// Bundle ITF
		lst_SStockRegist.setValue(16, txt_BundleItf.getText());
		//#CM8723
		// Stock time
		if (!StringUtil.isBlank(txt_StorageTime.getTime()))
		{
			lst_SStockRegist.setValue(17, txt_StorageTime.getText());
		}
		else
		{
			lst_SStockRegist.setValue(17, storageTime);
		}
	}

	//#CM8724
	// Event handler methods -----------------------------------------
	//#CM8725
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8726
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8727
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8728
	/** 
	 * Clicking on the Clicking the Menu button invokes this.<BR>
	 * <BR>
	 * Summary: Move to menu screen.<BR>
	 * <BR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM8729
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8730
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8731
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8732
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8733
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8734
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8735
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8736
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8737
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8738
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8739
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8740
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8741
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8742
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8743
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8744
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8745
	/** 
	 * Clicking on the Search Consignor Code button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displays the Consignor list listbox using the search conditions.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] <BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_PsearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM8746
		// Set the search condition for the Search Consignor screen.
		ForwardParameters param = new ForwardParameters();
		//#CM8747
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM8748
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM8749
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststockconsignor/ListStockConsignor.do",
			param,
			"/progress.do");
	}

	//#CM8750
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8751
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8752
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8753
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8754
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8755
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8756
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8757
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8758
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8759
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8760
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8761
	/** 
	 * Clicking on the Search Item Code button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displays the item list listbox using the search conditions.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] <BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *       Item code <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_PsearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM8762
		// Set the search conditions for the Search Item screen.
		ForwardParameters param = new ForwardParameters();
		//#CM8763
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM8764
		// Item code
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM8765
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM8766
		// In process screen->Result screen 
		redirect("/stockcontrol/listbox/liststockitem/ListStockItem.do", param, "/progress.do");
	}

	//#CM8767
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Pstocksearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8768
	/** 
	 * Clicking on the Search Stock button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displays the stock inquiry list listbox using the search conditions.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] *Mandatory Input<BR>
	 *    <DIR>
	 *       Consignor code* <BR>
	 *       Item code <BR>
	 *       Case/PieceDivision <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_Pstocksearch_Click(ActionEvent e) throws Exception
	{
		//#CM8769
		// Set the search conditions for the Search Stock screen.
		ForwardParameters param = new ForwardParameters();
		//#CM8770
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM8771
		// Item code
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM8772
		// Case/PieceDivision
		if (rdo_CpfCase.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_CASE);
		}
		else if (rdo_CpfPiece.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_PIECE);
		}
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_NOTHING);
		}

		//#CM8773
		// Location
		param.setParameter(ListStockLocationBusiness.LOCATION_KEY, txt_Location.getText());
		//#CM8774
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM8775
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststockmaintenance/ListStockMaintenance.do",
			param,
			"/progress.do");
	}

	//#CM8776
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8777
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8778
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8779
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8780
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8781
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8782
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Location_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8783
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Location_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8784
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Location_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8785
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Location_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8786
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Location_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8787
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8788
	/** 
	 * Invoked when search button at the location is pressed down.<BR>
	 * <BR>
	 * Summary: Sets the a search condition for a parameter and displays the location list listbox using the search conditions.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] <BR>
	 *    <DIR>
	 *       Consignor code <BR>
	 *       Item code <BR>
	 *       Case/PieceDivision <BR>
	 *       Location <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_PsearchLocation_Click(ActionEvent e) throws Exception
	{
		//#CM8789
		// Set the search condition for the Search Location screen.
		ForwardParameters param = new ForwardParameters();
		//#CM8790
		// Consignor code
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM8791
		// Item code
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM8792
		// Case/PieceDivision
		if (rdo_CpfCase.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_CASE);
		}
		else if (rdo_CpfPiece.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_PIECE);
		}
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM8793
		// Location
		param.setParameter(ListStockLocationBusiness.LOCATION_KEY, txt_Location.getText());
		//#CM8794
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);
		//#CM8795
		// In process screen->Result screen 
		redirect(
			"/stockcontrol/listbox/liststocklocation/ListStockLocation.do",
			param,
			"/progress.do");
	}

	//#CM8796
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8797
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8798
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8799
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8800
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StockCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8801
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StcCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8802
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StcCaseQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8803
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StcCaseQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8804
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8805
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8806
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8807
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8808
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8809
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8810
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8811
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8812
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8813
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StockPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8814
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StcPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8815
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StcPieceQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8816
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StcPieceQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8817
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8818
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8819
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8820
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8821
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8822
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8823
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8824
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8825
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8826
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_StrageTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8827
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8828
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageTime_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8829
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageTime_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8830
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8831
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8832
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8833
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8834
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8835
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Input_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8836
	/** 
	 * Clicking on the Input button invokes this.<BR>
	 * <BR>
	 * Summary: Checks the input item (count) in the input area and displays it in the preset area.<BR>
	 * <BR>
	 * <DIR>
	 *   1.Set the cursor to the worker code.<BR>
	 *   2.Check input field of input area (mandatory input)<BR>
	 *   3.Start schedule<BR>
	 * 	 <DIR>
	 *   	[parameter] *Mandatory Input<BR>
	 *   	<DIR>
	 * 			Worker Code* <BR>
	 * 			Password* <BR>
	 * 			Consignor code* <BR>
	 * 			Consignor name <BR>
	 * 			Item code* <BR>
	 * 			Item name <BR>
	 * 			Case/PieceDivision* <BR>
	 * 			Location* <BR>
	 * 			Packing qty per case*2 *4 <BR>
	 * 			Stock Case Qty*2 <BR>
	 * 			Case ITF <BR>
	 * 			packed qty per bundle <BR>
	 *			Stock piece qty*3 <BR>
	 *			Bundle ITF <BR>
	 *			Storage Date <BR>
	 *			Storage Time <BR>
	 *			Expiry Date <BR>
	 *		<BR>
	 * 			*2 <BR>
	 * 			When Case/Piece Division is 1:Case, Mandatory Input <BR>
	 * 			*3 <BR>
	 * 			When Case/Piece Division is 2:Piece, Mandatory Input <BR>
	 * 			*4 <BR>
	 * 			When Case/Piece Division is 0:None, <BR>
	 *			storage case qty <BR>
	 * 			case qty x packed qty per case+ piece qty is not 0(>0) <BR>
	 *	 	</DIR>
	 *   </DIR>
	 *   <BR>
	 *   4.Add to the preset area if the schedule result is true.<BR>
	 *     Clicking on the Input button after clicking the Modify button updates the target preset data to be modified with the info in the input area.<BR>
	 *   5.If the schedule result is trueEnable the Enable List Clear button.<BR>
	 *   6.If the schedule result is true, Set the default (initial/default value:-1) for the preset line No. of ViewState.<BR>
	 *   7.Output the message obtained from the schedule to the screen.<BR>
	 * 	 8.Maintain the contents of the input area is still maintained.<BR>
	 *   <BR>
	 * 	 Line No. list of the list cell<BR>
	 *   <DIR>
	 * 		3.Consignor code <BR>
	 * 		4.Item code <BR>
	 * 		5.Division <BR>
	 * 		6.Location <BR>
	 * 		7.Packing qty per case <BR>
	 * 		8.Stock Case Qty <BR>
	 * 		9.Case ITF <BR>
	 * 		10.Storage Date <BR>
	 * 		11.Expiry Date <BR>
	 * 		11.Consignor name <BR>
	 * 		12.Item name <BR>
	 * 		13.packed qty per bundle <BR>
	 * 		14.Stock piece qty <BR>
	 * 		15.Bundle ITF <BR>
	 * 		16.Storage Time <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions.  
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		//#CM8837
		// mandatory input check
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		txt_ItemCode.validate();
		txt_Location.validate();
		txt_StorageDate.validate();

		//#CM8838
		// Pattern matching characters
		txt_ConsignorName.validate(false);
		txt_ItemName.validate(false);
		txt_CaseEntering.validate(false);
		txt_StcCaseQty.validate(false);
		txt_CaseItf.validate(false);
		txt_BundleEntering.validate(false);
		txt_StcPieceQty.validate(false);
		txt_BundleItf.validate(false);
		txt_StorageTime.validate(false);
		txt_UseByDate.validate(false);

		//#CM8839
		// Input characters check for eWareNavi
		if (!checkContainNgText())
		{
			return;
		}

		Connection conn = null;

		try
		{
			//#CM8840
			// Set to the schedule parameter
			//#CM8841
			// Input area
			StockControlParameter param = new StockControlParameter();
			//#CM8842
			// Worker Code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM8843
			// Password
			param.setPassword(txt_Password.getText());
			//#CM8844
			// Consignor code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM8845
			// Consignor name
			param.setConsignorName(txt_ConsignorName.getText());
			//#CM8846
			// Item code
			param.setItemCode(txt_ItemCode.getText());
			//#CM8847
			// Item name
			param.setItemName(txt_ItemName.getText());
			//#CM8848
			// Case/PieceDivision
			if (rdo_CpfCase.getChecked())
			{
				param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_CpfPiece.getChecked())
			{
				param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_PIECE);
			}
			else if (rdo_CpfAppointOff.getChecked())
			{
				param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_NOTHING);
			}
			//#CM8849
			// Location
			param.setLocationNo(txt_Location.getText());
			//#CM8850
			// Packing qty per case
			param.setEnteringQty(txt_CaseEntering.getInt());
			//#CM8851
			// Stock Case Qty
			param.setStockCaseQty(txt_StcCaseQty.getInt());
			//#CM8852
			// Case ITF
			param.setITF(txt_CaseItf.getText());
			//#CM8853
			// packed qty per bundle
			param.setBundleEnteringQty(txt_BundleEntering.getInt());
			//#CM8854
			// Stock piece qty
			param.setStockPieceQty(txt_StcPieceQty.getInt());
			//#CM8855
			// Bundle ITF
			param.setBundleITF(txt_BundleItf.getText());
			//#CM8856
			// Aggregate Storage Date and storage Time and set it to the parameter
			DateUtil dateutil = new DateUtil();
			Date storageDate = dateutil.getDate(txt_StorageDate.getDate(), txt_StorageTime.getTime());
			if (storageDate == null) storageDate = new Date();
			param.setStorageDate(storageDate);
			//#CM8857
			// Expiry Date		
			param.setUseByDate(txt_UseByDate.getText());

			//#CM8858
			// Set to the schedule parameter
			//#CM8859
			// Preset area
			StockControlParameter[] listparam = null;

			//#CM8860
			// Set null if no data in the preset area.
			if (lst_SStockRegist.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				//#CM8861
				// Set the value, when data exists
				listparam = setListParam(this.getViewState().getInt("LineNo"));
			}

			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new StockRegistSCH();

			if (schedule.check(conn, param, listparam))
			{
				//#CM8862
				// Set the data for the preset area if the result is true.
				if (this.getViewState().getInt("LineNo") == -1)
				{
					//#CM8863
					// if it is new input, add it to preset.
					lst_SStockRegist.addRow();
					lst_SStockRegist.setCurrentRow(lst_SStockRegist.getMaxRows() - 1);
					setList(WmsFormatter.getTimeFormat(storageDate, ""));
				}
				else
				{
					//#CM8864
					// If it is the input data under modifying, update the data at correction target line.
					lst_SStockRegist.setCurrentRow(this.getViewState().getInt("LineNo"));
					setList(WmsFormatter.getTimeFormat(storageDate, ""));
					//#CM8865
					// Return selected line color to source
					lst_SStockRegist.resetHighlight();
				}

				//#CM8866
				// Return modify status to default
				this.getViewState().setInt("LineNo", -1);

				//#CM8867
				// Enable to click a button.
				//#CM8868
				// Add button
				btn_Submit.setEnabled(true);
				//#CM8869
				// List clear button
				btn_ListClear.setEnabled(true);

			}

			//#CM8870
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
				//#CM8871
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

	//#CM8872
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8873
	/** 
	 * Clicking on the Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area.<BR>
	 * <BR>
	 * <DIR>
	 *    1.Return the item (count) in the input area to the initial value.<BR>
	 *    <DIR>
	 *  Refer to <CODE>page_Load(ActionEvent e)</CODE>Method in regard with the initial value.<BR>
	 *    </DIR>
	 *    2.Set the cursor to the worker code.<BR>
	 *    3.Maintain the contents of preset area.<BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM8874
		// Execute the Clear process.
		setFirstDisp();
	}

	//#CM8875
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8876
	/** 
	 * Clicking Add start button invokes this.<BR>
	 * <BR>
	 * Summary:Execute store maintenance add based on the Preset area info.<BR>
	 * <BR>
	 * <DIR>
	 *	  1.Set the cursor to the worker code.<BR>
	 *    2.Display the dialog box to allow to confirm to add it or not.<BR>
	 *    <DIR>
	 *      "Do you add this?"<BR>
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
	 * 						Preset.Consignor code <BR>
	 * 						Preset.Consignor name <BR>
	 *						Preset.Item code <BR>
	 *						Preset.Item name <BR>
	 *						Preset.Division <BR>
	 *						Preset.Store Location <BR>
	 *						Preset.Packing qty per case <BR>
	 *						Preset.packed qty per bundle <BR>
	 *						Preset.Store Case qty <BR>
	 *						Preset.Storage Piece Qty <BR>
	 *						Preset.Case ITF <BR>
	 *						Preset.Bundle ITF <BR>
	 *						Preset.Storage Date <BR>
	 *						Preset.Storage Time <BR>
	 *						Preset.Expiry Date <BR>
	 *						Preset Line No.<BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.When the result of the schedule is true, clear adding info of preset in the input area<BR>
	 *				3.Cancel the Modify status. ( Set default:-1 for the ViewState preset line No.)<BR>
	 *    			Output the message obtained from the schedule to the screen<BR>
	 *			</DIR>
	 *    </DIR>
	 * <BR>
	 * 	 Line No.list of list cell<BR>
	 *   <DIR>
	 * 		3.Consignor code <BR>
	 * 		4.Item code <BR>
	 * 		5.Division <BR>
	 * 		6.Location <BR>
	 * 		7.Packing qty per case <BR>
	 * 		8.Stock Case Qty <BR>
	 * 		9.Case ITF <BR>
	 * 		10.Storage Date <BR>
	 * 		11.Expiry Date <BR>
	 * 		11.Consignor name <BR>
	 * 		12.Item name <BR>
	 * 		13.packed qty per bundle <BR>
	 * 		14.Stock piece qty <BR>
	 * 		15.Bundle ITF <BR>
	 * 		16.Storage Time <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM8877
			// Set the cursor to the worker code
			setFocus(txt_WorkerCode);

			//#CM8878
			// Set to the schedule parameter
			StockControlParameter[] param = null;
			//#CM8879
			// Set all the data in the preset area.
			param = setListParam(-1);

			//#CM8880
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new StockRegistSCH();

			//#CM8881
			// Start schedule
			if (schedule.startSCH(conn, param))
			{
				//#CM8882
				// if result is true
				conn.commit();
				//#CM8883
				// Set the message
				message.setMsgResourceKey(schedule.getMessage());

				//#CM8884
				// List clear
				lst_SStockRegist.clearRow();

				//#CM8885
				// Return modify status to default
				this.getViewState().setInt("LineNo", -1);

				//#CM8886
				// Disable button press down
				//#CM8887
				// Start Storage button
				btn_Submit.setEnabled(false);
				//#CM8888
				// List clear button
				btn_ListClear.setEnabled(false);
			}
			else
			{
				conn.rollback();
				//#CM8889
				// Set the message
				message.setMsgResourceKey(schedule.getMessage());
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
				//#CM8890
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

	//#CM8891
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8892
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
	 *				2.Revoke add button, List clear button.<BR>
	 *				3.Clear input area Items all together.<BR>
	 * 				4.Set the default (initial/default value:-1) for the preset line No. of ViewState.<BR>
	 *				5.Set the cursor to the worker code.<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		//#CM8893
		// Delete all lines
		lst_SStockRegist.clearRow();

		//#CM8894
		// Disable button press down
		//#CM8895
		// Add button
		btn_Submit.setEnabled(false);
		//#CM8896
		// List clear button
		btn_ListClear.setEnabled(false);

		//#CM8897
		// Return modify status to default
		this.getViewState().setInt("LineNo", -1);
	}

	//#CM8898
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStockRegist_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8899
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStockRegist_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM8900
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStockRegist_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM8901
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStockRegist_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM8902
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStockRegist_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8903
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SStockRegist_Change(ActionEvent e) throws Exception
	{
	}

	//#CM8904
	/** 
	 * Clicking on the Cancel or Modify button invokes this.<BR>
	 * <BR>
	 * Summary of Cancel button : Clears the corresponding data in the preset area.<BR>
	 * <BR>
	 * <DIR>
	 *    1.Display the dialog box to allow to confirm to check the preset info.<BR>
	 *    <DIR>
	 *      "Do you cancel it?"<BR>
	 * 		[Check dialog cancel]<BR>
	 *			<DIR>
	 *				Disable
	 *			</DIR>
	 * 		[Check dialog OK]<BR>
	 *			<DIR>
	 *				1.Clear corresponding data of Input area, preset<BR>
	 * 				2.Set the default (initial/default value:-1) for the preset line No. of ViewState.<BR>
	 *              3.When no Preset info is found, disable the Add button/List Clear button.<BR>
	 *				4.Set the cursor to the worker code.<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * <BR>
	 * Summary of Modify button: Change the status of the corresponding data in the preset area to Modify.<BR>
	 * <BR>
	 * <DIR>
	 *    1.Displaies the selected info in the upper input area.<BR>
	 *    2.Change the color of Selected info section to light yellow.<BR>
	 *    3.Set the current line for the preset line No. of ViewState.<BR>
	 *    4.Set the cursor to the worker code.<BR>
	 * </DIR>
	 * @param e ActionEvent This is the class to store event info. 
	 * @throws Exception Reports all the exceptions. 
	 */
	public void lst_SStockRegist_Click(ActionEvent e) throws Exception
	{
		//#CM8905
		// when clicking on the Cancel button
		if (lst_SStockRegist.getActiveCol() == 1)
		{
			//#CM8906
			// Delete list
			lst_SStockRegist.removeRow(lst_SStockRegist.getActiveRow());

			//#CM8907
			// When no Preset info is found, disable the Add button/List Clear button.
			//#CM8908
			// Set null if no data in the preset area.
			if (lst_SStockRegist.getMaxRows() == 1)
			{
				//#CM8909
				// Disable button press down
				//#CM8910
				// Add button
				btn_Submit.setEnabled(false);
				//#CM8911
				// List clear button
				btn_ListClear.setEnabled(false);
			}

			//#CM8912
			// Return modify status to default
			this.getViewState().setInt("LineNo", -1);

			//#CM8913
			// Return selected line color to source
			lst_SStockRegist.resetHighlight();

			//#CM8914
			// Set the cursor to the worker code
			setFocus(txt_WorkerCode);

		}
		//#CM8915
		// When correction button is clicked (Executing modify operation)
		else if (lst_SStockRegist.getActiveCol() == 2)
		{
			//#CM8916
			// Set the current line.
			lst_SStockRegist.setCurrentRow(lst_SStockRegist.getActiveRow());
			//#CM8917
			// Consignor code
			txt_ConsignorCode.setText(lst_SStockRegist.getValue(3));
			//#CM8918
			// Item code
			txt_ItemCode.setText(lst_SStockRegist.getValue(4));
			//#CM8919
			// Case/PieceDivision
			if (lst_SStockRegist.getValue(5).equals(DisplayText.getText("LBL-W0374")))
			{
				//#CM8920
				// None
				rdo_CpfCase.setChecked(false);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAppointOff.setChecked(true);
			}
			else if (lst_SStockRegist.getValue(5).equals(DisplayText.getText("LBL-W0375")))
			{
				//#CM8921
				// Case
				rdo_CpfCase.setChecked(true);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAppointOff.setChecked(false);
			}
			else if (lst_SStockRegist.getValue(5).equals(DisplayText.getText("LBL-W0376")))
			{
				//#CM8922
				// Piece
				rdo_CpfCase.setChecked(false);
				rdo_CpfPiece.setChecked(true);
				rdo_CpfAppointOff.setChecked(false);
			}
			//#CM8923
			// Location
			txt_Location.setText(lst_SStockRegist.getValue(6));
			//#CM8924
			// Packing qty per case
			txt_CaseEntering.setText(lst_SStockRegist.getValue(7));
			//#CM8925
			// Stock Case Qty
			txt_StcCaseQty.setText(lst_SStockRegist.getValue(8));
			//#CM8926
			// Case ITF
			txt_CaseItf.setText(lst_SStockRegist.getValue(9));
			//#CM8927
			// Storage Date
			txt_StorageDate.setText(lst_SStockRegist.getValue(10));
			//#CM8928
			// Expiry Date
			txt_UseByDate.setText(lst_SStockRegist.getValue(11));
			//#CM8929
			// Consignor name
			txt_ConsignorName.setText(lst_SStockRegist.getValue(12));
			//#CM8930
			// Item name
			txt_ItemName.setText(lst_SStockRegist.getValue(13));
			//#CM8931
			// packed qty per bundle
			txt_BundleEntering.setText(lst_SStockRegist.getValue(14));
			//#CM8932
			// Stock piece qty
			txt_StcPieceQty.setText(lst_SStockRegist.getValue(15));
			//#CM8933
			// Bundle ITF
			txt_BundleItf.setText(lst_SStockRegist.getValue(16));
			//#CM8934
			// Stock time
			txt_StorageTime.setText(lst_SStockRegist.getValue(17));

			//#CM8935
			// Store to ViewState
			//#CM8936
			// Set the a preset line No. in ViewState to determine the status being in Modify by clicking the Modify button.
			this.getViewState().setInt("LineNo", lst_SStockRegist.getActiveRow());

			//#CM8937
			// Change the color of the modified lineto yellow.
			lst_SStockRegist.setHighlight(lst_SStockRegist.getActiveRow());
		}
	}

	//#CM8938
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8939
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockPieceQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM8940
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockPieceQty_TabKey(ActionEvent e) throws Exception
	{
	}
	//#CM8941
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8942
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM8943
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8944
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM8945
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM8946
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}


}
//#CM8947
//end of class
