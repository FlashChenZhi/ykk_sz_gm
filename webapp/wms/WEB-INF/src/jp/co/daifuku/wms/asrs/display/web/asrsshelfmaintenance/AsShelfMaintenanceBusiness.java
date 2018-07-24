// $Id: AsShelfMaintenanceBusiness.java,v 1.2 2006/10/26 04:50:06 suresh Exp $

//#CM36691
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsshelfmaintenance;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
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
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstockdetail.ListAsStockDetailBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstockdetailnobtn.ListAsStockDetailNoBtnBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocationdetailstatuslist.ListAsLocationDetailStatusListBusiness;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.asrs.schedule.AsShelfMaintenanceSCH;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;
import jp.co.daifuku.wms.master.operator.AreaOperator;


//#CM36692
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * ASRS Stock maintenance (change) class. <BR>
 * set the input contents from the screen to a parameter
 * The schedule is stock maintenance registration, corrects, and deletes it based on the parameter. <BR>
 * Receive true when the result is received from the schedule, and processing is completed normally. 
 * Receive false when it does not complete the schedule because of the condition error etc.<BR>
 * output the schedule result, schedule message to screen<BR>
 * and transaction commit and rollback are done in this screen<BR>
 * <BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.Process when Add button is pressed (<CODE>btn_Submit_Click</CODE> Method )<BR>
 * <BR>
 * <DIR>
 *  Check the input condition by the content of the input. <BR>
 *  When the result is true, status becomes input details (stock info) input area. <BR>
 *  <BR>
 * 	[parameter] *mandatory input<BR>
 * 	<BR>
 * 	<DIR>
 * 		worker code * <BR>
 * 		password * <BR>
 * 		Warehouse No.* <BR>
 * 		location no. * <BR>
 * 	</DIR>
 *  <BR>
 *  [data for output use] <BR>
 *  <BR>
 * 	<DIR>
 *  	location no.(For display) <BR>
 *  	location status <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.Process when Modify button is pressed (<CODE>btn_Modify_Click</CODE> Method )<BR>
 * <BR>
 * <DIR>
 *  Check the input condition by the content of the input. <BR>
 *  When the result is true, the location details list is displayed. <BR>
 *  Details (stock info) are displayed from the location details list by selecting modified information. <BR>
 *  <BR>
 * 	[parameter] *mandatory input<BR>
 * 	<BR>
 * 	<DIR>
 * 		worker code * <BR>
 * 		password * <BR>
 * 		Warehouse No.* <BR>
 * 		location no. * <BR>
 * 	</DIR>
 *  <BR>
 *  [data for output use] <BR>
 *  <BR>
 * 	<DIR>
 *  	location no.(For display) <BR>
 *  	location status <BR>
 *      consignor code <BR>
 *      consignor name <BR>
 *      item code <BR>
 *      item name <BR>
 *      case piece flag <BR>
 *      packed qty per case <BR>
 *      packed qty per piece <BR>
 *      stock case qty <BR>
 *      stock piece qty <BR>
 *      Case ITF <BR>
 *      bundle itf <BR>
 *      storage type <BR>
 *      expiry date <BR>
 *      storage date <BR>
 *      storage time <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 3.Process when Delete button is pressed (<CODE>btn_Delete_Click</CODE> Method )<BR>
 * <BR>
 * <DIR>
 *  Check the input condition by the content of the input. <BR>
 *  When the result is true, the location details list is displayed. <BR>
 *  Details (stock info) are displayed from the location details list by selecting modified information. <BR>
 *  <BR>
 * 	[parameter] *mandatory input<BR>
 * 	<BR>
 * 	<DIR>
 * 		worker code * <BR>
 * 		password * <BR>
 * 		Warehouse No.* <BR>
 * 		location no. * <BR>
 * 	</DIR>
 *  <BR>
 *  [data for output use] <BR>
 *  <BR>
 * 	<DIR>
 *  	location no.(For display) <BR>
 *  	location status <BR>
 *      consignor code <BR>
 *      consignor name <BR>
 *      item code <BR>
 *      item name <BR>
 *      case piece flag <BR>
 *      packed qty per case <BR>
 *      packed qty per piece <BR>
 *      stock case qty <BR>
 *      stock piece qty <BR>
 *      Case ITF <BR>
 *      bundle itf <BR>
 *      storage type <BR>
 *      expiry date <BR>
 *      storage date <BR>
 *      storage time <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 4.set button click event(<CODE>btn_Setting_Click</CODE>method ) <BR>
 * <BR>
 * <DIR>
 *  The input content is set in the parameter, and the schedule does stock maintenance change Process based on the parameter. <BR>
 *  Receive true when the result is received from the schedule, and processing is completed normally. 
 *  Receive false when it does not complete the schedule because of the condition error etc.<BR>
 *  output the schedule result, schedule message to screen<BR>
 * 	<DIR>
 *   [parameter] *mandatory input <BR>
 *   <BR>
 * 		worker code *<BR>
 * 		password *<BR>
 * 		warehouse *<BR>
 * 		Area No*<BR>
 * 		location no. *<BR>
 * 		Process flag*<BR>
 * 		Consignor code*<BR>
 * 		consignor name<BR>
 * 		Item code*<BR>
 * 		item name<BR>
 * 		Case/Piece type *<BR>
 * 		packed qty per case*2<BR>
 * 		packed qty per piece<BR>
 * 		Stock case qty *2 *4<BR>
 * 		Stock piece qty *3 *4<BR>
 * 		Case ITF<BR>
 * 		bundle itf<BR>
 * 		storage type<BR>
 * 		expiry date<BR>
 * 		storage date/time<BR>
 * 		ViewState.stock id<BR>
 * 		ViewState.update date/time<BR>
 * 		terminal no.<BR>
 *		<BR>
 * 		*2 <BR>
 * 		mandatory input if case/piece flag is 1:case<BR>
 * 		*3 <BR>
 * 		mandatory input if case/piece flag is 2:piece<BR>
 * 		*4 <BR>
 * 		If case/piece type is 0:none<BR>
 *		Either storage case qty or storage piece qty is mandatory <BR>
 * 		case qty x packed qty per case + piece qty is not 0(>0) <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/9/29</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:50:06 $
 * @author  $Author: suresh $
 */
public class AsShelfMaintenanceBusiness extends AsShelfMaintenance implements WMSConstants
{
	//#CM36693
	// Class fields --------------------------------------------------
	
	//#CM36694
	// Class variables -----------------------------------------------
	//#CM36695
	/**
	 * Key to maintain which Process of Add, Modify, and delete was selected
	 */
	private final static String PROCESSTYPE_KEY = "PROCESSTYPE_KEY";

	//#CM36696
	/**
	 * Key to maintain stockID when modifying, and deleting it
	 */
	private final static String MNTSTOCK_ID = "MNTSTOCK_ID";

	//#CM36697
	/**
	 * Key to maintain last updated date and time when modifying, and deleting it
	 */
	private final static String MNTLAST_UPDATE = "MNTLAST_UPDATE";

	//#CM36698
	// Class method --------------------------------------------------

	//#CM36699
	// Constructors --------------------------------------------------

	//#CM36700
	// Public methods ------------------------------------------------

	//#CM36701
	/**
	 * screen initialization<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * <BR>
	 * <DIR>
	 * 		1.initialize pulldown<BR>
	 * 		2.initialize input area<BR>
	 * 		3.set the cursor in worker code<BR>
	 * 		<BR>
	 * 		item[initial value] <BR>
	 * 		<DIR>
	 * 			worker code[nil]<BR>
	 * 			password[nil]<BR>
	 * 			Empty location checkbox[true]<BR>
	 * 			Empty Palette checkbox[false]<BR>
	 * 			result location checkbox[false]<BR>
	 * 			location[nil]<BR>
	 * 			location/location status[nil]<BR>
	 * 			consignor code[nil(Read only)] <BR>
	 * 			consignor name[nil(Read only)]<BR>
	 * 			item code[nil(Read only)] <BR>
	 * 			item name[nil(Read only)] <BR>
	 * 			case piece flag[nil(Invalid)] <BR>
	 * 			packed qty per case[nil(Read only)] <BR>
	 * 			packed qty per piece[nil(Read only)] <BR>
	 * 			stock case qty[nil(Read only)] <BR>
	 * 			stock piece qty[nil(Read only)] <BR>
	 * 			Case ITF[nil(Read only)]<BR>
	 * 			bundle itf[nil(Read only)]<BR>
	 * 			expiry date[nil(Read only)] <BR>
	 * 			storage date[nil(Read only)]<BR>
	 * 			storage time[nil(Read only)]<BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{

		//#CM36702
		// To judge whether it is update status by the Modify button pressing, line No is set in ViewState. 
		//#CM36703
		// (default:-1)
		this.getViewState().setInt("LineNo", -1);

		//#CM36704
		// disable button click
		//#CM36705
		// Set button
		btn_Setting.setEnabled(false);
		//#CM36706
		// clear button
		btn_Clear.setEnabled(false);

		//#CM36707
		// location status(empty location)
		chk_LocationStatus_Empty.setChecked(true);
		//#CM36708
		// location status(Empty Palette)
		chk_LocationStatus_Empty_PB.setChecked(false);
		//#CM36709
		// location status(result location)
		chk_LocationStatus_Unit.setChecked(false);
		
		//#CM36710
		// location no.
		txt_Location.setText("");
		//#CM36711
		// initial display
		setFirstDisp();
	}

	//#CM36712
	/**
	 * call this before calling the respective control events<BR>
	 * <BR>
	 * overview ::display confirmation dialog<BR>
	 * and set the cursor in worker code<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM36713
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM36714
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM36715
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}
		//#CM36716
		// Confirmation dialog when Add button is pressed "Do you want to Add?"
		btn_Setting.setBeforeConfirm("MSG-W0009");

		//#CM36717
		// set the cursor in worker code input
		setFocus(txt_WorkerCode);
	}

	//#CM36718
	// Package methods -----------------------------------------------

	//#CM36719
	/**
	 * This method is called when returning from popup window<BR>
	 * override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE><BR>
	 * <BR>
	 * overview ::fetch the value from search screen and set it<BR>
	 * <BR>
	 * <DIR>
	 *      1.fetch the value set from popup search screen<BR>
	 *      2.set value if it is not null<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			DialogParameters param = ((DialogEvent) e).getDialogParameters();
			String type = param.getParameter(ListAsStockDetailBusiness.PROCESSTYPE_KEY);
			//#CM36720
			// fetch parameter selected from listbox
			//#CM36721
			// consignor code
			String consignorcode = "";
			//#CM36722
			// consignor name
			String consignorname = "";
			//#CM36723
			// item code
			String itemcode = "";
			//#CM36724
			// item name
			String itemname = "";
			//#CM36725
			// case piece flag
			String casepieseflag = "";
			//#CM36726
			// location
			String locationNo = "";
			//#CM36727
			// case stock qty
			String stockcaseqty = "";
			//#CM36728
			// bundle stock qty
			String stockbundleqty = "";
			//#CM36729
			// packed qty per case
			String caseqty = "";
			//#CM36730
			// packed qty per piece
			String bundleqty = "";
			//#CM36731
			// Case ITF
			String caseitf = "";
			//#CM36732
			// bundle itf
			String bundleitf = "";
			//#CM36733
			// storage date/time
			Date instockday = null;
			//#CM36734
			// expiry date
			String usebydate = "";
			//#CM36735
			// storage type
			String restoring = "";
			//#CM36736
			//return from modify, delete button
			if (!StringUtil.isBlank(type))
			{
				int proctype = Integer.parseInt(type);
				if (AsShelfMaintenanceSCH.M_MODIFY == proctype || AsShelfMaintenanceSCH.M_DELETE == proctype)
				{
					//#CM36737
					//fetch connection
					conn = ConnectionManager.getConnection(DATASOURCE_NAME);
					//#CM36738
					//check location status
					if (!checkLocation(conn, proctype))
					{
						//#CM36739
						//return to initial status
						btn_Clear_Click(null);
						return;
					}
					//#CM36740
					// consignor code
					consignorcode = param.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
					//#CM36741
					// consignor name
					consignorname = param.getParameter(ListAsConsignorBusiness.CONSIGNORNAME_KEY);
					//#CM36742
					// item code
					itemcode = param.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
					//#CM36743
					// item name
					itemname = param.getParameter(ListAsItemBusiness.ITEMNAME_KEY);
					//#CM36744
					// case piece flag
					casepieseflag = param.getParameter(ListAsStockDetailBusiness.CASEPIECEFLAG_KEY);
					//#CM36745
					// location
					locationNo = param.getParameter(ListAsLocationDetailStatusListBusiness.LOCATION_KEY);
					//#CM36746
					// case stock qty
					stockcaseqty = param.getParameter(ListAsStockDetailBusiness.CASEQTY_KEY);
					//#CM36747
					// bundle stock qty
					stockbundleqty = param.getParameter(ListAsStockDetailBusiness.PIECEQTY_KEY);
					//#CM36748
					// packed qty per case
					caseqty = param.getParameter(ListAsStockDetailBusiness.ENTERING_KEY);
					//#CM36749
					// packed qty per piece
					bundleqty = param.getParameter(ListAsStockDetailBusiness.BUNDLEENTERING_KEY);
					//#CM36750
					// Case ITF
					caseitf = param.getParameter(ListAsStockDetailBusiness.ITF_KEY);
					//#CM36751
					// bundle itf
					bundleitf = param.getParameter(ListAsStockDetailBusiness.BUNDLEITF_KEY);
					//#CM36752
					// storage date/time
					instockday = WmsFormatter.getTimeStampDate(param.getParameter(ListAsStockDetailBusiness.STORINGDATE_KEY));
					//#CM36753
					// expiry date
					usebydate = param.getParameter(ListAsStockDetailBusiness.USEBYDATE_KEY);
					//#CM36754
					// storage type
					restoring = param.getParameter(ListAsStockDetailBusiness.RESTORING_KEY);
					//#CM36755
					// stock id
					this.getViewState().setString(MNTSTOCK_ID, param.getParameter(ListAsStockDetailBusiness.STOCKID_KEY));				
					//#CM36756
					// update date/time
					this.getViewState().setString(MNTLAST_UPDATE, param.getParameter(ListAsStockDetailBusiness.LASTUPDATE_KEY));				

					//#CM36757
					// The input area is clear. 
					changeInputArea(proctype);	
				}
			}
			else
			{
				//#CM36758
				// location
				locationNo = param.getParameter(ListAsLocationDetailStatusListBusiness.LOCATION_KEY);
				//#CM36759
				// consignor code
				consignorcode = param.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
				//#CM36760
				// consignor name
				consignorname = param.getParameter(ListAsConsignorBusiness.CONSIGNORNAME_KEY);
				//#CM36761
				// item code
				itemcode = param.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
				//#CM36762
				// item name
				itemname = param.getParameter(ListAsItemBusiness.ITEMNAME_KEY);
			}
			
			//#CM36763
			// set the value if not empty
			//#CM36764
			// consignor code
			if (!StringUtil.isBlank(consignorcode))
			{
				txt_ConsignorCode.setText(consignorcode);
			}
			//#CM36765
			// consignor name
			if (!StringUtil.isBlank(consignorname))
			{
				txt_ConsignorName.setText(consignorname);
			}
			//#CM36766
			// item code
			if (!StringUtil.isBlank(itemcode))
			{
				txt_ItemCode.setText(itemcode);
			}
			//#CM36767
			// item name
			if (!StringUtil.isBlank(itemcode))
			{
				txt_ItemName.setText(itemname);
			}
			//#CM36768
			// case piece flag
			if (!StringUtil.isBlank(casepieseflag))
			{
				if (casepieseflag.equals(StockControlParameter.CASEPIECE_FLAG_CASE))
				{
					rdo_Cpf_Case.setChecked(true);
					rdo_Cpf_Piece.setChecked(false);
					rdo_Cpf_AppointOff.setChecked(false);
				}
				else if (casepieseflag.equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
				{
					rdo_Cpf_Case.setChecked(false);
					rdo_Cpf_Piece.setChecked(true);
					rdo_Cpf_AppointOff.setChecked(false);
				}
				else if (casepieseflag.equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
				{
					rdo_Cpf_Case.setChecked(false);
					rdo_Cpf_Piece.setChecked(false);
					rdo_Cpf_AppointOff.setChecked(true);
				}
			}
			//#CM36769
			// location
			if (!StringUtil.isBlank(locationNo))
			{
				txt_Location.setText(DisplayText.formatLocationnumber(locationNo));
			}
			//#CM36770
			// packed qty per case
			if (!StringUtil.isBlank(caseqty))
			{
				txt_CaseEntering.setText(caseqty);
			}
			//#CM36771
			// packed qty per piece
			if (!StringUtil.isBlank(bundleqty))
			{
				txt_BundleEntering.setText(bundleqty);
			}
			//#CM36772
			// case stock qty
			if (!StringUtil.isBlank(stockcaseqty))
			{
				txt_StockCaseQty.setText(stockcaseqty);
			}
			//#CM36773
			// bundle stock qty
			if (!StringUtil.isBlank(stockbundleqty))
			{
				txt_StockPieceQty.setText(stockbundleqty);
			}
			//#CM36774
			// Case ITF
			if (!StringUtil.isBlank(caseitf))
			{
				txt_CaseItf.setText(caseitf);
			}
			//#CM36775
			// bundle itf
			if (!StringUtil.isBlank(bundleitf))
			{
				txt_BundleItf.setText(bundleitf);
			}
			//#CM36776
			// storage type
			if (!StringUtil.isBlank(restoring))
			{
				pul_StorageFlag.setSelectedIndex(Integer.parseInt(restoring));
			}
			if (!StringUtil.isBlank(instockday))
			{
				//#CM36777
				// storage date
				txt_StorageDate.setDate(instockday);
				//#CM36778
				// storage time
				txt_StorageTime.setTime(instockday);
			}
			//#CM36779
			// expiry date
			if (!StringUtil.isBlank(usebydate))
			{
				txt_UseByDate.setText(usebydate);
			}
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM36780
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}

	}

	//#CM36781
	// Protected methods ---------------------------------------------

	//#CM36782
	/**
	 * input check<BR>
	 * <BR>
	 * Abstract :Set the message, and return false when abnormality is found. <BR>
	 * <BR>
	 * @return input check result(true:Normal false:Abnormal)
	 */
	private boolean checkContainNgText()
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

	//#CM36783
	/**
	 * This method initializes screen<BR>
	 * <BR>
	 * overview ::initial screen display <BR>
	 * and set the cursor in worker code<BR>
	 * <DIR>
	 * 		[parameter] <BR>
	 * 		<DIR>
	 * 			item name[initial value] <BR>
	 * 			<DIR>
	 * 				worker code[nil]<BR>
	 * 				password[nil]<BR>
	 * 				Empty location checkbox[true]<BR>
	 * 				Empty Palette checkbox[false]<BR>
	 * 				result location checkbox[false]<BR>
	 * 				location[nil]<BR>
	 * 				location/location status[nil]<BR>
	 * 				consignor code[nil(Read only)] <BR>
	 * 				consignor name[nil(Read only)]<BR>
	 * 				item code[nil(Read only)] <BR>
	 * 				item name[nil(Read only)] <BR>
	 * 				case piece flag[nil(Invalid)] <BR>
	 * 				packed qty per case[nil(Read only)] <BR>
	 * 				packed qty per piece[nil(Read only)] <BR>
	 * 				stock case qty[nil(Read only)] <BR>
	 * 				stock piece qty[nil(Read only)] <BR>
	 * 				Case ITF[nil(Read only)]<BR>
	 * 				bundle itf[nil(Read only)]<BR>
	 * 				expiry date[nil(Read only)] <BR>
	 * 				storage date[nil(Read only)]<BR>
	 * 				storage time[nil(Read only)]<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * @throws Exception report all the exceptions
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;

		try
		{
			Locale locale = this.httpRequest.getLocale();
			//#CM36784
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM36785
			// consignor code
			txt_ConsignorCode.setText("");
			txt_ConsignorCode.setReadOnly(true);
			//#CM36786
			// Consignor Retrieval Button
			btn_PSConsignorcode.setEnabled(false);
			//#CM36787
			// consignor name
			txt_ConsignorName.setText("");
			txt_ConsignorName.setReadOnly(true);
			//#CM36788
			// item code
			txt_ItemCode.setText("");
			txt_ItemCode.setReadOnly(true);
			//#CM36789
			// item search button
			btn_PSItemCode.setEnabled(false);
			//#CM36790
			// item name
			txt_ItemName.setText("");
			txt_ItemName.setReadOnly(true);
			//#CM36791
			// case piece flag(Case)
			rdo_Cpf_Case.setChecked(true);
			rdo_Cpf_Case.setEnabled(false);
			//#CM36792
			// case piece flag(Piece)
			rdo_Cpf_Piece.setChecked(false);
			rdo_Cpf_Piece.setEnabled(false);
			//#CM36793
			// case piece flag(None)
			rdo_Cpf_AppointOff.setChecked(false);
			rdo_Cpf_AppointOff.setEnabled(false);
			//#CM36794
			// packed qty per case
			txt_CaseEntering.setText("");
			txt_CaseEntering.setReadOnly(true);
			//#CM36795
			// storage case qty
			txt_StockCaseQty.setText("");
			txt_StockCaseQty.setReadOnly(true);
			//#CM36796
			// Case ITF
			txt_CaseItf.setText("");
			txt_CaseItf.setReadOnly(true);
			//#CM36797
			// packed qty per piece
			txt_BundleEntering.setText("");
			txt_BundleEntering.setReadOnly(true);
			//#CM36798
			// storage piece qty
			txt_StockPieceQty.setText("");
			txt_StockPieceQty.setReadOnly(true);
			//#CM36799
			// bundle itf
			txt_BundleItf.setText("");
			txt_BundleItf.setReadOnly(true);
			//#CM36800
			// expiry date
			txt_UseByDate.setText("");
			txt_UseByDate.setReadOnly(true);
			//#CM36801
			// storage date
			txt_StorageDate.setText("");
			txt_StorageDate.setReadOnly(true);
			//#CM36802
			// storage time
			txt_StorageTime.setText("");
			txt_StorageTime.setReadOnly(true);

			//#CM36803
			//set terminal no.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo)this.getUserInfo());
			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			//#CM36804
			// pull down display data(Warehouse)
			String[] whno = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);
			//#CM36805
			//set pulldown data
			PulldownHelper.setPullDown(pul_WareHouse, whno);
		
	
			//#CM36806
			// pull down display data(Storage flag)
			String[] storagestatus = pull.getReStoringPullDownData("");

			//#CM36807
			//set pulldown data
			PulldownHelper.setPullDown(pul_StorageFlag, storagestatus);
			pul_StorageFlag.setEnabled(false);

			//#CM36808
			// Add Button
			btn_Submit.setEnabled(true);
			//#CM36809
			// Modify Button
			btn_Modify.setEnabled(true);
			//#CM36810
			// Delete Button
			btn_Delete.setEnabled(true);
			//#CM36811
			// Input Warehouse No.
			pul_WareHouse.setEnabled(true);
			//#CM36812
			// Input Location No. 
			txt_Location.setReadOnly(false);

			//#CM36813
			// set the cursor in worker code input
			setFocus(txt_WorkerCode);
		}
		catch (Exception ex)
		{
			//#CM36814
			// rollback connection
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
				//#CM36815
				// close connection
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM36816
	// Private methods -----------------------------------------------

	//#CM36817
	/**
	 * Status of the shelf is checked, and whether it is possible to maintain it is decided.
	 * @param conn instance to store database connection
	 * @param processType job type 
	 * @return Check result(true:It is possible to maintain it. false:It is not possible to maintain it. ) 
	 * @throws Exception report all the exceptions
	 */
	private boolean checkLocation(Connection conn, int processType) throws Exception
	{
		WareHouseHandler wWhHandler = new WareHouseHandler(conn);
		WareHouseSearchKey wWhSearchKey = new WareHouseSearchKey();
		ShelfHandler wShHandler = new ShelfHandler(conn);
		ShelfSearchKey wShSearchKey = new ShelfSearchKey();

		wWhSearchKey.KeyClear();
		//#CM36818
		// Acquire the Warehouse division from Warehouse Station No.
		wWhSearchKey.setStationNumber(pul_WareHouse.getSelectedValue());
		
		WareHouse wWareHouse = (WareHouse)wWhHandler.findPrimary(wWhSearchKey);
				
		wShSearchKey.KeyClear();
		//#CM36819
		// warehouse type + input location no.
		String currstnumber = DisplayText.formatLocation(wWareHouse.getWareHouseNumber(), txt_Location.getString());
		//#CM36820
		// warehouse type + input location no.
		wShSearchKey.setStationNumber(currstnumber);
		
		Shelf wShelf = (Shelf)wShHandler.findPrimary(wShSearchKey);

		if (wShelf == null)
		{
			//#CM36821
			// Please enter the existing location no.
			this.message.setMsgResourceKey("6013090");
			return false;
		}

		//#CM36822
		// It is not possible to maintain it in accessible location.
		if (wShelf.getAccessNgFlag() == Shelf.ACCESS_NG)
		{		
			//#CM36823
			// The location is inaccessible. Unable to make corrections.
			this.message.setMsgResourceKey("6013160");	
			return false;
		}

		//#CM36824
		// check restricted location
		if (wShelf.getStatus() == Shelf.STATUS_NG)
		{		
			//#CM36825
			// The location is restricted. Unable to make corrections.
			this.message.setMsgResourceKey("6013159");	
			return false;
		}
		
		//#CM36826
		// Check the location status.
		if (wShelf.getPresence() == (Shelf.PRESENCE_RESERVATION))
		{
			//#CM36827
			// The location is reserved. Unable to make corrections.
			this.message.setMsgResourceKey("6013158");	
			return false;
		}
		
		if (processType == AsShelfMaintenanceSCH.M_MODIFY || processType == AsShelfMaintenanceSCH.M_DELETE)
		{
			if (wShelf.getPresence() == Shelf.PRESENCE_EMPTY)
			{
				//#CM36828
				// There is no stock in specified location.
				this.message.setMsgResourceKey("6013134");	
				return false;
			}
		}

		Palette[] wPalette = null;
		//#CM36829
		// If the location is occupied, check the allcation status and erroe location.
		if (wShelf.getPresence() == Shelf.PRESENCE_STORAGED)
		{
			PaletteHandler wPlHandler = new PaletteHandler(conn);
			PaletteSearchKey wPlSearchKey = new PaletteSearchKey();
			
			//#CM36830
			// set the search condition
			wPlSearchKey.KeyClear();
			wPlSearchKey.setCurrentStationNumber(currstnumber);
			
			wPalette = (Palette[])wPlHandler.find(wPlSearchKey);
			
			int pltstatus = wPalette[0].getStatus();
			switch (pltstatus)
			{
				//#CM36831
				// occupied location
				case Palette.REGULAR:
					break;
				//#CM36832
				// reserved for storage
				case Palette.STORAGE_PLAN:
				//#CM36833
				// reserved for retrieval
				case Palette.RETRIEVAL_PLAN:
				//#CM36834
				// being retrieved
				case Palette.RETRIEVAL:
					//#CM36835
					// Specified location is allocated at moment.
					this.message.setMsgResourceKey("6013135");	
					return false;
				//#CM36836
				// error
				case Palette.IRREGULAR:
					//#CM36837
					// Unable to make correction of error locations if rpocess type is 
					// either registeration or modification.
					if (processType == AsShelfMaintenanceSCH.M_CREATE || processType == AsShelfMaintenanceSCH.M_MODIFY)
					{
						//#CM36838
						// Error location is specified. Unable to make corrections.
						this.message.setMsgResourceKey("6013157");	
						return false;
					}
			}
		}

		//#CM36839
		// Retrieve the status of the location no. entered via screen.
		//#CM36840
		//Inaccessible location
		String str = "";
		if ( wShelf.getAccessNgFlag() == Shelf.ACCESS_OK )
		{
			//#CM36841
			//Restricted location
			if ( wShelf.getStatus() == Shelf.STATUS_NG )
			{
				str = DisplayText.getText("FINDUTIL_UNAVAILABLE");
			}
			else
			{
				//#CM36842
				// Empth location
				if ( wShelf.getPresence() == Shelf.PRESENCE_EMPTY )
				{
					str = DisplayText.getText("FINDUTIL_EMPTY");
				}
				//#CM36843
				// Occupied location
				else if ( wShelf.getPresence() == Shelf.PRESENCE_STORAGED )
				{

					//#CM36844
					// In case the location is occupied and restricted, it is displayed as restricted location.
					if ( wPalette[0].getStatus() == Shelf.STATUS_NG )
					{
						str = DisplayText.getText("FINDUTIL_UNAVAILABLE");
					}
					//#CM36845
					//Retrieval in progress (shown as working location)
					else if(wPalette[0].getStatus() == Palette.RETRIEVAL_PLAN ||
					wPalette[0].getStatus() == Palette.RETRIEVAL)
					{
						str = DisplayText.getText("FINDUTIL_WORK");
					}
					else
					{
						//#CM36846
						//Empty pallet
						if(wPalette[0].getEmpty() == Palette.STATUS_EMPTY)
						{
							str = DisplayText.getText("FINDUTIL_EMPTYPALETTE");
						}
						else
						{
							//#CM36847
							//Error location
							if(wPalette[0].getStatus() == Palette.IRREGULAR)
							{
								str = DisplayText.getText("FINDUTIL_IRREGULAR");
							}
							//#CM36848
							//Occupied location
							else
							{
								str = DisplayText.getText("FINDUTIL_STORAGED");
							}
						}
					}
				}
				//#CM36849
				// Reserved location (shown as working location)
				else if ( wShelf.getPresence() == Shelf.PRESENCE_RESERVATION )
				{
					str = DisplayText.getText("FINDUTIL_WORK");
				}
			}
		}
		//#CM36850
		// Inacessible location
		else
		{
			//#CM36851
			//In case the location is inaccessible and restricted at the same time, it is dispalyed as
			//restricted location. (Status of restriction is represented with priority)
			if ( wShelf.getStatus() == Shelf.STATUS_NG)
			{
				str = DisplayText.getText("FINDUTIL_UNAVAILABLE");
			}
			else
			{
				str = DisplayText.getText("FINDUTIL_ACCESSNGFLAG");
			}
		}
		lbl_JavaSetLocationStatus.setText(str);
		lbl_JavaSetLocationNo.setText(DisplayText.formatLocation(currstnumber));
		
		return true;
	}

	//#CM36852
	/**
	 * It is possible to input, and switch impropriety of the input item by Process flag. 
	 * @param procType job type
	 * @throws Exception report all the exceptions
	 */
	private void changeInputArea(int procType) throws Exception
	{
		//#CM36853
		// Maintain the Process type in ViewState. 
		this.getViewState().setInt(PROCESSTYPE_KEY, procType);
		switch (procType)
		{
			//#CM36854
			// Add
			case AsShelfMaintenanceSCH.M_CREATE:
				//#CM36855
				// consignor code
				txt_ConsignorCode.setText("");
				txt_ConsignorCode.setReadOnly(false);
				//#CM36856
				// Consignor RetrievalButton
				btn_PSConsignorcode.setEnabled(true);
				//#CM36857
				// consignor name
				txt_ConsignorName.setText("");
				txt_ConsignorName.setReadOnly(false);
				//#CM36858
				// item code
				txt_ItemCode.setText("");
				txt_ItemCode.setReadOnly(false);
				//#CM36859
				// item search button
				btn_PSItemCode.setEnabled(true);
				//#CM36860
				// item name
				txt_ItemName.setText("");
				txt_ItemName.setReadOnly(false);
				//#CM36861
				// case piece flag(Case)
				rdo_Cpf_Case.setChecked(true);
				rdo_Cpf_Case.setEnabled(true);
				//#CM36862
				// case piece flag(Piece)
				rdo_Cpf_Piece.setChecked(false);
				rdo_Cpf_Piece.setEnabled(true);
				//#CM36863
				// case piece flag(None)
				rdo_Cpf_AppointOff.setChecked(false);
				rdo_Cpf_AppointOff.setEnabled(true);
				//#CM36864
				// packed qty per case
				txt_CaseEntering.setText("");
				txt_CaseEntering.setReadOnly(false);
				//#CM36865
				// storage case qty
				txt_StockCaseQty.setText("");
				txt_StockCaseQty.setReadOnly(false);
				//#CM36866
				// Case ITF
				txt_CaseItf.setText("");
				txt_CaseItf.setReadOnly(false);
				//#CM36867
				// packed qty per piece
				txt_BundleEntering.setText("");
				txt_BundleEntering.setReadOnly(false);
				//#CM36868
				// storage piece qty
				txt_StockPieceQty.setText("");
				txt_StockPieceQty.setReadOnly(false);
				//#CM36869
				// bundle itf
				txt_BundleItf.setText("");
				txt_BundleItf.setReadOnly(false);
				//#CM36870
				// expiry date
				txt_UseByDate.setText("");
				txt_UseByDate.setReadOnly(false);
				//#CM36871
				// storage date
				txt_StorageDate.setText("");
				txt_StorageDate.setReadOnly(false);
				//#CM36872
				// storage time
				txt_StorageTime.setText("");
				txt_StorageTime.setReadOnly(false);
				//#CM36873
				// storage type
				pul_StorageFlag.setEnabled(true);

				//#CM36874
				// inquiry button
				btn_Query.setEnabled(false);
				//#CM36875
				// Add Button
				btn_Submit.setEnabled(false);
				//#CM36876
				// Modify Button
				btn_Modify.setEnabled(false);
				//#CM36877
				// Delete Button
				btn_Delete.setEnabled(false);
				//#CM36878
				// Set button
				btn_Setting.setEnabled(true);
				//#CM36879
				// clear button
				btn_Clear.setEnabled(true);
				//#CM36880
				// Input Location No. 
				txt_Location.setReadOnly(true);
				//#CM36881
				// Input Warehouse No.
				pul_WareHouse.setEnabled(false);
				//#CM36882
				// location status(empty location)
				chk_LocationStatus_Empty.setEnabled(false);
				//#CM36883
				// location status(Empty Palette)
				chk_LocationStatus_Empty_PB.setEnabled(false);
				//#CM36884
				// location status(result location)
				chk_LocationStatus_Unit.setEnabled(false);
		
				//#CM36885
				// Confirmation dialog when Add button is pressed "Do you want to Add?"
				btn_Setting.setBeforeConfirm("MSG-W0009");

				break;
			//#CM36886
			// Modify
			case AsShelfMaintenanceSCH.M_MODIFY:
				//#CM36887
				// case piece flag(Case)
				rdo_Cpf_Case.setChecked(true);
				rdo_Cpf_Case.setEnabled(true);
				//#CM36888
				// case piece flag(Piece)
				rdo_Cpf_Piece.setChecked(false);
				rdo_Cpf_Piece.setEnabled(true);
				//#CM36889
				// case piece flag(None)
				rdo_Cpf_AppointOff.setChecked(false);
				rdo_Cpf_AppointOff.setEnabled(true);
				//#CM36890
				// packed qty per case
				txt_CaseEntering.setText("");
				txt_CaseEntering.setReadOnly(false);
				//#CM36891
				// storage case qty
				txt_StockCaseQty.setText("");
				txt_StockCaseQty.setReadOnly(false);
				//#CM36892
				// Case ITF
				txt_CaseItf.setText("");
				txt_CaseItf.setReadOnly(false);
				//#CM36893
				// packed qty per piece
				txt_BundleEntering.setText("");
				txt_BundleEntering.setReadOnly(false);
				//#CM36894
				// storage piece qty
				txt_StockPieceQty.setText("");
				txt_StockPieceQty.setReadOnly(false);
				//#CM36895
				// bundle itf
				txt_BundleItf.setText("");
				txt_BundleItf.setReadOnly(false);
				//#CM36896
				// expiry date
				txt_UseByDate.setText("");
				txt_UseByDate.setReadOnly(false);
				//#CM36897
				// storage date
				txt_StorageDate.setText("");
				txt_StorageDate.setReadOnly(false);
				//#CM36898
				// storage time
				txt_StorageTime.setText("");
				txt_StorageTime.setReadOnly(false);
				//#CM36899
				// storage type
				pul_StorageFlag.setEnabled(true);
	
				//#CM36900
				// inquiry button
				btn_Query.setEnabled(false);
				//#CM36901
				// Add Button
				btn_Submit.setEnabled(false);
				//#CM36902
				// Modify Button
				btn_Modify.setEnabled(false);
				//#CM36903
				// Delete Button
				btn_Delete.setEnabled(false);
				//#CM36904
				// Set button
				btn_Setting.setEnabled(true);
				//#CM36905
				// clear button
				btn_Clear.setEnabled(true);
				//#CM36906
				// Input Location No. 
				txt_Location.setReadOnly(true);
				//#CM36907
				// Input Warehouse No.
				pul_WareHouse.setEnabled(false);
				//#CM36908
				// location status(empty location)
				chk_LocationStatus_Empty.setEnabled(false);
				//#CM36909
				// location status(Empty Palette)
				chk_LocationStatus_Empty_PB.setEnabled(false);
				//#CM36910
				// location status(result location)
				chk_LocationStatus_Unit.setEnabled(false);
		
				//#CM36911
				// Confirmation dialog when Modify button is pressed "Do you want to Modify?"
				btn_Setting.setBeforeConfirm("MSG-W0014");

				break;
			//#CM36912
			// delete
			case AsShelfMaintenanceSCH.M_DELETE:
				//#CM36913
				// inquiry button
				btn_Query.setEnabled(false);
				//#CM36914
				// Add Button
				btn_Submit.setEnabled(false);
				//#CM36915
				// Modify Button
				btn_Modify.setEnabled(false);
				//#CM36916
				// Delete Button
				btn_Delete.setEnabled(false);
				//#CM36917
				// Set button
				btn_Setting.setEnabled(true);
				//#CM36918
				// clear button
				btn_Clear.setEnabled(true);
				//#CM36919
				// Input Location No. 
				txt_Location.setReadOnly(true);
				//#CM36920
				// Input Warehouse No.
				pul_WareHouse.setEnabled(false);
				//#CM36921
				// location status(empty location)
				chk_LocationStatus_Empty.setEnabled(false);
				//#CM36922
				// location status(Empty Palette)
				chk_LocationStatus_Empty_PB.setEnabled(false);
				//#CM36923
				// location status(result location)
				chk_LocationStatus_Unit.setEnabled(false);
		
				//#CM36924
				// Confirmation dialog when Delete button is pressed "Do you want to Delete?"
				btn_Setting.setBeforeConfirm("MSG-W0007");

				break;
		}
	}

	//#CM36925
	/**
	 * Method to acquire Consignor code from the data base. <BR>
	 * <BR>
	 * Abstract :Acquire Consignor code from the data base. <BR>
	 * <DIR>
	 *   1.Set a corresponding consignor in input area when there is only one Consignor code. <BR>
	 * <DIR>
	 * <BR>
	 * @throws Exception report all the exceptions
	 */
	private void dispConsignorInfo(Connection conn) throws Exception
	{
		StockReportFinder stockFinder = new StockReportFinder(conn);
		StockSearchKey searchKey = new StockSearchKey();

		//#CM36926
		// Set of search condition
		//#CM36927
		// Stock status(center stock)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM36928
		// The stock qty must be one or more. 
		searchKey.setStockQty(1, ">=");
		//#CM36929
		// Acquire the area of ASRS, and add to the search condition. 
		//#CM36930
		// IS NULL retrieval when there is no corresponding area
		int[] areaType = {Area.SYSTEM_DISC_KEY_ASRS};
		AreaOperator areaOperator = new AreaOperator(conn);
		String[] areaNo = areaOperator.getAreaNo(areaType);
		searchKey.setAreaNo(areaNo);

		//#CM36931
		// Set of acquisition condition
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		if (stockFinder.search(searchKey) == 1)
		{
			//#CM36932
			// Data retrieval
			searchKey.KeyClear();
			//#CM36933
			// Stock status(center stock) and Stock qty are more than 1
			searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			searchKey.setStockQty(1, ">=");
			//#CM36934
			// Acquire new Consignor description while Adding date. 
			searchKey.setLastUpdateDateOrder(1, false);
			//#CM36935
			// area no.
			searchKey.setAreaNo(areaNo);

			searchKey.setConsignorCodeCollect();
			searchKey.setConsignorNameCollect();

			if (stockFinder.search(searchKey) > 0)
			{
				Stock[] consignor = (Stock[]) stockFinder.getEntities(1);
				
				txt_ConsignorCode.setText(consignor[0].getConsignorCode());
				txt_ConsignorName.setText(consignor[0].getConsignorName());
			}
		}
		stockFinder.close();
	}

	//#CM36936
	// Event handler methods -----------------------------------------
	//#CM36937
	/**
	 * called when menu button is clicked<BR>
	 * <BR>
	 * overview ::go back to menu screen<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM36938
	/** 
	 * It is called when inquiry button is pressed.<BR>
	 * <BR>
	 * overview ::Display the stock list list box according to location no by the set search condition in parameter and the search condition.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *    	Warehouse No.<BR>
	 *    	warehouse name<BR>
	 *      Empty location checkbox <BR>
	 *      Empty Palette checkbox <BR>
	 *      result location checkbox <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_Query_Click(ActionEvent e) throws Exception
	{
		//#CM36939
		//Acquire location status. 
		Vector statusVec = new Vector();
		//#CM36940
		//empty location
		if (chk_LocationStatus_Empty.getChecked())
		{
			statusVec.add(Integer.toString(AsScheduleParameter.STATUS_EMPTY));
		}
		//#CM36941
		//Empty Palette
		if (chk_LocationStatus_Empty_PB.getChecked())
		{
			statusVec.add(Integer.toString(AsScheduleParameter.STATUS_EMPTYPALETTE));
		}
		//#CM36942
		//result location
		if (chk_LocationStatus_Unit.getChecked())
		{
			statusVec.add(Integer.toString(AsScheduleParameter.STATUS_STORAGED));
		}
		String[] statusList = new String[statusVec.size()];
		statusVec.copyInto(statusList);
		
		//#CM36943
		// Set the search condition in the stock list according to Location No. 
		ForwardParameters param = new ForwardParameters();
		param.setParameter(ListAsLocationDetailStatusListBusiness.WAREHOUSE_KEY , pul_WareHouse.getSelectedValue());
		param.setParameter(ListAsLocationDetailStatusListBusiness.WAREHOUSENAME_KEY, pul_WareHouse.getSelectedItem().getText());		
		param.setParameter(ListAsLocationDetailStatusListBusiness.LOCATIONSTATUS_KEY , statusList);

		//#CM36944
		// Stock list display according to Location No
		redirect("/asrs/listbox/listasrslocationdetailstatuslist/ListAsLocationDetailStatusList.do", param, "/progress.do");
		
	}

	//#CM36945
	/** 
	 * This method is called when the location detail button is clicked<BR>
	 * <BR>
	 * overview ::set search condition in parameter and Displays the location detail list box. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *    	Warehouse No.<BR>
	 *    	warehouse name<BR>
	 *      location<BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_LocationDetails_Click(ActionEvent e) throws Exception
	{
		//#CM36946
		// Set the search condition in the location details list display. 
		ForwardParameters param = new ForwardParameters();
		param.setParameter(ListAsStockDetailNoBtnBusiness.WAREHOUSE_KEY , pul_WareHouse.getSelectedValue());
		param.setParameter(ListAsStockDetailNoBtnBusiness.WAREHOUSENAME_KEY , pul_WareHouse.getSelectedItem().getText());
		param.setParameter(ListAsStockDetailNoBtnBusiness.LOCATION_KEY, 
							DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_Location.getString()));

		//#CM36947
		// Shelf details list display
		redirect("/asrs/listbox/listasrsstockdetailnobtn/ListAsStockDetailNoBtn.do", param, "/progress.do");	
	}

	//#CM36948
	/** 
	 * It is called when Add Button is pressed.<BR>
	 * <BR>
	 * Abstract :Initialize input area. <BR>
	 * <DIR>
	 * 		1.Check shelf status. <BR>
	 * 		2.Maintain the Process type in ViewState information. <BR>
	 * 		3.Initialize input area. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		//#CM36949
		//input check
		txt_Location.validate();
			
		try
		{
			//#CM36950
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM36951
			//check location status
			if (checkLocation(conn, AsShelfMaintenanceSCH.M_CREATE))
			{
				//#CM36952
				// Maintain the Process type in ViewState. 
				this.getViewState().setInt(PROCESSTYPE_KEY, AsShelfMaintenanceSCH.M_CREATE);
				//#CM36953
				// The input area is clear. 
				changeInputArea(AsShelfMaintenanceSCH.M_CREATE);
				//#CM36954
				// Do Initial display if the consignor is one. 
				dispConsignorInfo(conn);
				//#CM36955
				// Initial display does the work day (system date) on the Storage day. 
				txt_StorageDate.setDate(new Date());
			}
			else
			{
				//#CM36956
				// return to initial status
				btn_Clear_Click(null);
			}
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM36957
				//close connection
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM36958
	/** 
	 * It is called when Modify Button is pressed.<BR>
	 * <BR>
	 * overview ::Display shelf details list (It is possible to select it) list box by the set search condition in parameter and the search condition.<BR>
	 * and Maintain the Process type in ViewState information. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *    	warehouse<BR>
	 *      location<BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_Modify_Click(ActionEvent e) throws Exception
	{
		//#CM36959
		//Set the search condition in the location details list display. 
		ForwardParameters param = new ForwardParameters();
		param.setParameter(ListAsStockDetailBusiness.WAREHOUSE_KEY, pul_WareHouse.getSelectedValue());
		param.setParameter(ListAsStockDetailBusiness.WAREHOUSENAME_KEY, pul_WareHouse.getSelectedItem().getText());
		param.setParameter(ListAsStockDetailBusiness.LOCATION_KEY,
				DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_Location.getString()));

		param.setParameter(ListAsStockDetailBusiness.PROCESSTYPE_KEY , Integer.toString(AsShelfMaintenanceSCH.M_MODIFY));
		//#CM36960
		// Maintain the Process type in ViewState. 
		this.getViewState().setInt(PROCESSTYPE_KEY, AsShelfMaintenanceSCH.M_MODIFY);
	
		//#CM36961
		//Shelf details list display
		redirect("/asrs/listbox/listasrsstockdetail/ListAsStockDetail.do", param, "/progress.do");	
	}

	//#CM36962
	/** 
	 * It is called when Delete Button is pressed.<BR>
	 * <BR>
	 * overview ::Display shelf details list (It is possible to select it) list box by the set search condition in parameter and the search condition.<BR>
	 * and Maintain the Process type in ViewState information. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *    	warehouse<BR>
	 *      location<BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_Delete_Click(ActionEvent e) throws Exception
	{
		//#CM36963
		//Set the search condition in the location details list display. 
		ForwardParameters param = new ForwardParameters();
		param.setParameter(ListAsStockDetailBusiness.WAREHOUSE_KEY, pul_WareHouse.getSelectedValue());
		param.setParameter(ListAsStockDetailBusiness.WAREHOUSENAME_KEY, pul_WareHouse.getSelectedItem().getText());
		param.setParameter(ListAsStockDetailBusiness.LOCATION_KEY,
			DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_Location.getString()));
		param.setParameter(ListAsStockDetailBusiness.PROCESSTYPE_KEY , Integer.toString(AsShelfMaintenanceSCH.M_DELETE));
		//#CM36964
		// Maintain the Process type in ViewState. 
		this.getViewState().setInt(PROCESSTYPE_KEY, AsShelfMaintenanceSCH.M_DELETE);
		
		//#CM36965
		//Shelf details list display
		redirect("/asrs/listbox/listasrsstockdetail/ListAsStockDetail.do", param, "/progress.do");	
	}

	//#CM36966
	/** 
	 * called when "consignor code" search button is clicked<BR>
	 * <BR>
	 * overview ::set search condition in parameter and display the search results in consignor list box<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       consignor code <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_PSConsignorcode_Click(ActionEvent e) throws Exception
	{
		//#CM36967
		// Set the search conditions to item search screen
		ForwardParameters param = new ForwardParameters();
		//#CM36968
		// Search condition
		param.setParameter(ListAsConsignorBusiness.SEARCHITEM_KEY, AsScheduleParameter.SEARCHFLAG_STOCK);
		//#CM36969
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM36970
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsconsignor/ListAsConsignor.do", param, "/progress.do");
	}

	//#CM36971
	/** 
	 * This process is called upon clicking item code search button<BR>
	 * <BR>
	 * overview ::set search condition in parameter and display the item code list box<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] <BR>
	 *    <DIR>
	 *       consignor code <BR>
	 *       item code <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_PSItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM36972
		// Set the search conditions to item search screen
		ForwardParameters param = new ForwardParameters();
		//#CM36973
		// Search condition
		param.setParameter(ListAsItemBusiness.SEARCHITEM_KEY, AsScheduleParameter.SEARCHFLAG_STOCK);
		//#CM36974
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM36975
		// item code
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM36976
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsitem/ListAsItem.do", param, "/progress.do");
	}

	//#CM36977
	/** 
	 * It is called when Submit Button is pressed.<BR>
	 * <BR>
	 * Abstract :Do stock maintenance Add, Modify and deletion Process by information on input area. <BR>
	 * <BR>
	 * <DIR>
	 *	  1.set cursor in worker code<BR>
	 *    2.Display the dialog box, and confirm whether to correspond according to the Process type Process. <BR>
	 *    <DIR>
	 * 		[confirmation dialog CANCEL]<BR>
	 *			<DIR>
	 *				do nothing
	 *			</DIR>
	 * 		[confirmation dialog OK]<BR>
	 *			<DIR>
	 *				1.start scheduler<BR>
	 *				<DIR>
	 *   				[parameter]<BR>
	 * 					<DIR>
	 * 						worker code<BR>
	 * 						password<BR>
	 * 						warehouse<BR>
	 * 						area no.<BR>
	 * 						location no.<BR>
	 * 						job type<BR>
	 * 						consignor code<BR>
	 * 						consignor name<BR>
	 * 						item code<BR>
	 * 						item name<BR>
	 * 						case piece flag<BR>
	 * 						packed qty per case<BR>
	 * 						packed qty per piece<BR>
	 * 						stock case qty<BR>
	 * 						stock piece qty<BR>
	 * 						Case ITF<BR>
	 * 						bundle itf<BR>
	 * 						storage type<BR>
	 * 						expiry date<BR>
	 * 						storage date/time<BR>
	 * 						ViewState.stock id<BR>
	 * 						ViewState.update date/time<BR>
	 * 						terminal no.<BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.Initialize input area and ViewState information when the result of the schedule is true. <BR>
	 *    			If false, output the message acquired from the schedule to the screen. <BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_Setting_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		//#CM36978
		// set the cursor in worker code input
		setFocus(txt_WorkerCode);

		//#CM36979
		// The Process type is acquired ViewState. 
		int processkey = this.getViewState().getInt(PROCESSTYPE_KEY);
	
		//#CM36980
		// Mandatory Input check
		//#CM36981
		// pattern matching character
		if (processkey == AsShelfMaintenanceSCH.M_CREATE)
		{
			//#CM36982
			// Confirmation dialog when Add button is pressed "Do you want to Add?"
			btn_Setting.setBeforeConfirm("MSG-W0009");
			txt_WorkerCode.validate();
			txt_PassWord.validate();
			txt_ConsignorCode.validate();
			txt_ItemCode.validate();
			txt_StorageDate.validate();
			txt_ConsignorName.validate(false);
			txt_ItemName.validate(false);
		}
		else if (processkey == AsShelfMaintenanceSCH.M_MODIFY)
		{
			//#CM36983
			// Confirmation dialog when Modify button is pressed "Do you want to Modify?"
			btn_Setting.setBeforeConfirm("MSG-W0014");
			txt_WorkerCode.validate();
			txt_PassWord.validate();
			txt_StorageDate.validate();
		}
		else
		{
			//#CM36984
			// Confirmation dialog when Delete button is pressed "Do you want to Delete?"
			btn_Setting.setBeforeConfirm("MSG-W0007");
			txt_WorkerCode.validate();
			txt_PassWord.validate();
		}
	
		txt_CaseEntering.validate(false);
		txt_StockCaseQty.validate(false);
		txt_CaseItf.validate(false);
		txt_BundleEntering.validate(false);
		txt_StockPieceQty.validate(false);
		txt_BundleItf.validate(false);
		txt_UseByDate.validate(false);
		txt_StorageDate.validate(false);
		txt_StorageTime.validate(false);
	
		//#CM36985
		// Input character check for eWareNavi
		if (!checkContainNgText())
		{
			return;
		}

		try
		{
			//#CM36986
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			ASFindUtil findutil = new ASFindUtil(conn);
	
			//#CM36987
			// set schedule parameter
			AsScheduleParameter param = new AsScheduleParameter();

			param.setWorkerCode(txt_WorkerCode.getText());
			param.setPassword(txt_PassWord.getText());
			param.setWareHouseNo(pul_WareHouse.getSelectedValue());
			param.setAreaNo(pul_WareHouse.getSelectedValue());
			param.setLocationNo(DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_Location.getString()));
			param.setProcessStatus(Integer.toString(processkey));
			param.setConsignorCode(txt_ConsignorCode.getText());
			param.setConsignorName(txt_ConsignorName.getText());
			param.setItemCode(txt_ItemCode.getText());
			param.setItemName(txt_ItemName.getText());
			if (rdo_Cpf_Case.getChecked())
			{
				param.setCasePieceFlag(AsScheduleParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_Cpf_Piece.getChecked())
			{
				param.setCasePieceFlag(AsScheduleParameter.CASEPIECE_FLAG_PIECE);
			}
			else
			{
				param.setCasePieceFlag(AsScheduleParameter.CASEPIECE_FLAG_NOTHING);
			}
			param.setEnteringQty(txt_CaseEntering.getInt());
			param.setStockCaseQty(txt_StockCaseQty.getInt());
			param.setITF(txt_CaseItf.getText());
			param.setBundleEnteringQty(txt_BundleEntering.getInt());
			param.setStockPieceQty(txt_StockPieceQty.getInt());
			param.setBundleITF(txt_BundleItf.getText());
			param.setStoringStatus(pul_StorageFlag.getSelectedValue());
			param.setUseByDate(txt_UseByDate.getText());
			Date storageDate = findutil.getDate(txt_StorageDate.getDate(), txt_StorageTime.getTime());
			if (storageDate == null) storageDate = new Date();
			param.setInStockDate(storageDate);
			if (processkey != AsShelfMaintenanceSCH.M_CREATE)
			{
				param.setStockId(this.getViewState().getString(MNTSTOCK_ID));
				param.setLastUpdateDate(WmsFormatter.getTimeStampDate(this.getViewState().getString(MNTLAST_UPDATE)));
			}
			else
			{
				param.setStockId("");
			}

			//#CM36988
			// terminal no.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());
	
			WmsScheduler schedule = new AsShelfMaintenanceSCH();
	
	
			//#CM36989
			// input check
			if (schedule.check(conn, param))
			{
				AsScheduleParameter[] paramArray = new AsScheduleParameter[1];
				paramArray[0] = param;
				//#CM36990
				// start schedule
				if (schedule.startSCH(conn, paramArray))
				{
					//#CM36991
					// commit if the result is true
					conn.commit();
					//#CM36992
					// set message
					message.setMsgResourceKey(schedule.getMessage());

					//#CM36993
					// consignor code
					txt_ConsignorCode.setReadOnly(true);
					//#CM36994
					// Consignor RetrievalButton
					btn_PSConsignorcode.setEnabled(false);
					//#CM36995
					// consignor name
					txt_ConsignorName.setReadOnly(true);
					//#CM36996
					// item code
					txt_ItemCode.setReadOnly(true);
					//#CM36997
					// item search button
					btn_PSItemCode.setEnabled(false);
					//#CM36998
					// item name
					txt_ItemName.setReadOnly(true);
					//#CM36999
					// case piece flag(Case)
					rdo_Cpf_Case.setEnabled(false);
					//#CM37000
					// case piece flag(Piece)
					rdo_Cpf_Piece.setEnabled(false);
					//#CM37001
					// case piece flag(None)
					rdo_Cpf_AppointOff.setEnabled(false);
					//#CM37002
					// packed qty per case
					txt_CaseEntering.setReadOnly(true);
					//#CM37003
					// storage case qty
					txt_StockCaseQty.setReadOnly(true);
					//#CM37004
					// Case ITF
					txt_CaseItf.setReadOnly(true);
					//#CM37005
					// packed qty per piece
					txt_BundleEntering.setReadOnly(true);
					//#CM37006
					// storage piece qty
					txt_StockPieceQty.setReadOnly(true);
					//#CM37007
					// bundle itf
					txt_BundleItf.setReadOnly(true);
					//#CM37008
					// expiry date
					txt_UseByDate.setReadOnly(true);
					//#CM37009
					// storage date
					txt_StorageDate.setReadOnly(true);
					//#CM37010
					// storage time
					txt_StorageTime.setReadOnly(true);
					//#CM37011
					// storage type
					pul_StorageFlag.setEnabled(false);

					//#CM37012
					// inquiry button
					btn_Query.setEnabled(true);
					//#CM37013
					// Add Button
					btn_Submit.setEnabled(true);
					//#CM37014
					// Modify Button
					btn_Modify.setEnabled(true);
					//#CM37015
					// Delete Button
					btn_Delete.setEnabled(true);
					//#CM37016
					// Set button
					btn_Setting.setEnabled(false);
					//#CM37017
					// clear button
					btn_Clear.setEnabled(false);
					//#CM37018
					// Input Location No. 
					txt_Location.setReadOnly(false);
					//#CM37019
					// Input Warehouse No.
					pul_WareHouse.setEnabled(true);
					if (processkey != AsShelfMaintenanceSCH.M_MODIFY)
					{
						btn_Clear_Click(null);
					}
					//#CM37020
					// location status(empty location)
					chk_LocationStatus_Empty.setEnabled(true);
					//#CM37021
					// location status(Empty Palette)
					chk_LocationStatus_Empty_PB.setEnabled(true);
					//#CM37022
					// location status(result location)
					chk_LocationStatus_Unit.setEnabled(true);
		

					//#CM37023
					// Initialize the value of ViewState. 
					this.getViewState().setInt(PROCESSTYPE_KEY, AsShelfMaintenanceSCH.M_NOPROCESS);				

				}
				else
				{
					conn.rollback();
					//#CM37024
					// set message
					message.setMsgResourceKey(schedule.getMessage());
				}
			}
			else
			{
				conn.rollback();
				//#CM37025
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}
		}
		catch (Exception ex)
		{
			//#CM37026
			// rollback connection
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
				//#CM37027
				// close connection
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM37028
	/**
	 * called when clear button is clicked<BR>
	 * <BR>
	 * Abstract :clear the input area<BR>
	 * <BR>
	 * <DIR>
	 * 		1.return input area items to initial state<BR>
	 * 		2.Clear ViewState information. <BR>
	 * 		<DIR>
	 *   	item name[initial value]<BR>
	 *   		<DIR>
	 * 				worker code[as it is]<BR>
	 * 				password[as it is]<BR>
	 * 				Empty location checkbox[as it is]<BR>
	 * 				Empty Palette checkbox[as it is]<BR>
	 * 				result location checkbox[as it is]<BR>
	 * 				location[as it is]<BR>
	 * 				location/location status[nil]<BR>
	 * 				consignor code[nil(Read only)] <BR>
	 * 				consignor name[nil(Read only)]<BR>
	 * 				item code[nil(Read only)] <BR>
	 * 				item name[nil(Read only)] <BR>
	 * 				case piece flag[nil(Invalid)] <BR>
	 * 				packed qty per case[nil(Read only)] <BR>
	 * 				packed qty per piece[nil(Read only)] <BR>
	 * 				stock case qty[nil(Read only)] <BR>
	 * 				stock piece qty[nil(Read only)] <BR>
	 * 				Case ITF[nil(Read only)]<BR>
	 * 				bundle itf[nil(Read only)]<BR>
	 * 				expiry date[nil(Read only)] <BR>
	 * 				storage date[nil(Read only)]<BR>
	 * 				storage time[nil(Read only)]<BR>
	 * 	 		</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM37029
		// Display Location No
		lbl_JavaSetLocationNo.setText("");
		//#CM37030
		// Display location status
		lbl_JavaSetLocationStatus.setText("");
		//#CM37031
		// consignor code
		txt_ConsignorCode.setText("");
		txt_ConsignorCode.setReadOnly(true);
		//#CM37032
		// Consignor RetrievalButton
		btn_PSConsignorcode.setEnabled(false);
		//#CM37033
		// consignor name
		txt_ConsignorName.setText("");
		txt_ConsignorName.setReadOnly(true);
		//#CM37034
		// item code
		txt_ItemCode.setText("");
		txt_ItemCode.setReadOnly(true);
		//#CM37035
		// item search button
		btn_PSItemCode.setEnabled(false);
		//#CM37036
		// item name
		txt_ItemName.setText("");
		txt_ItemName.setReadOnly(true);
		//#CM37037
		// case piece flag(Case)
		rdo_Cpf_Case.setChecked(true);
		rdo_Cpf_Case.setEnabled(false);
		//#CM37038
		// case piece flag(Piece)
		rdo_Cpf_Piece.setChecked(false);
		rdo_Cpf_Piece.setEnabled(false);
		//#CM37039
		// case piece flag(None)
		rdo_Cpf_AppointOff.setChecked(false);
		rdo_Cpf_AppointOff.setEnabled(false);
		//#CM37040
		// packed qty per case
		txt_CaseEntering.setText("");
		txt_CaseEntering.setReadOnly(true);
		//#CM37041
		// storage case qty
		txt_StockCaseQty.setText("");
		txt_StockCaseQty.setReadOnly(true);
		//#CM37042
		// Case ITF
		txt_CaseItf.setText("");
		txt_CaseItf.setReadOnly(true);
		//#CM37043
		// packed qty per piece
		txt_BundleEntering.setText("");
		txt_BundleEntering.setReadOnly(true);
		//#CM37044
		// storage piece qty
		txt_StockPieceQty.setText("");
		txt_StockPieceQty.setReadOnly(true);
		//#CM37045
		// bundle itf
		txt_BundleItf.setText("");
		txt_BundleItf.setReadOnly(true);
		//#CM37046
		// expiry date
		txt_UseByDate.setText("");
		txt_UseByDate.setReadOnly(true);
		//#CM37047
		// storage date
		txt_StorageDate.setText("");
		txt_StorageDate.setReadOnly(true);
		//#CM37048
		// storage time
		txt_StorageTime.setText("");
		txt_StorageTime.setReadOnly(true);
		//#CM37049
		// storage type
		pul_StorageFlag.setSelectedIndex(0);
		pul_StorageFlag.setEnabled(false);

		//#CM37050
		// inquiry button
		btn_Query.setEnabled(true);
		//#CM37051
		// Add Button
		btn_Submit.setEnabled(true);
		//#CM37052
		// Modify Button
		btn_Modify.setEnabled(true);
		//#CM37053
		// Delete Button
		btn_Delete.setEnabled(true);
		//#CM37054
		// Set button
		btn_Setting.setEnabled(false);
		//#CM37055
		// clear button
		btn_Clear.setEnabled(false);
		//#CM37056
		// Input Location No. 
		txt_Location.setReadOnly(false);
		//#CM37057
		// Input Warehouse No.
		pul_WareHouse.setEnabled(true);
		//#CM37058
		// location status(empty location)
		chk_LocationStatus_Empty.setEnabled(true);
		//#CM37059
		// location status(Empty Palette)
		chk_LocationStatus_Empty_PB.setEnabled(true);
		//#CM37060
		// location status(result location)
		chk_LocationStatus_Unit.setEnabled(true);
		
		//#CM37061
		// Initialize the value of ViewState. 
		this.getViewState().setInt(PROCESSTYPE_KEY, AsShelfMaintenanceSCH.M_NOPROCESS);				
	}
	//#CM37062
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37063
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37064
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Set_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37065
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37066
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37067
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37068
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37069
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37070
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM37071
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PassWord_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37072
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37073
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37074
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37075
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM37076
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37077
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37078
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Change(ActionEvent e) throws Exception
	{
	}

	//#CM37079
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LocationStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37080
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_LocationStatus_Empty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37081
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_LocationStatus_Empty_Change(ActionEvent e) throws Exception
	{
	}

	//#CM37082
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_LocationStatus_Empty_PB_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37083
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_LocationStatus_Empty_PB_Change(ActionEvent e) throws Exception
	{
	}

	//#CM37084
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_LocationStatus_Unit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37085
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_LocationStatus_Unit_Change(ActionEvent e) throws Exception
	{
	}

	//#CM37086
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LocationDetail_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37087
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Query_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37088
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LocationNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37089
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Location_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37090
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Location_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37091
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Location_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37092
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_LocationDetails_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37093
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ProcessFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37094
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37095
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Modify_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37096
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Delete_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37097
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LocationNoFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37098
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetLocationNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37099
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetLocationStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37100
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37101
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37102
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37103
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37104
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM37105
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSConsignorcode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37106
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37107
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37108
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37109
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37110
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM37111
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37112
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37113
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37114
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37115
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM37116
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37117
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37118
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37119
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37120
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37121
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM37122
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37123
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Case_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37124
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Case_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37125
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Piece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37126
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Piece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37127
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_AppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37128
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_AppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM37129
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37130
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37131
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37132
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37133
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StockCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37134
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37135
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockCaseQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37136
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockCaseQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37137
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37138
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37139
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37140
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37141
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM37142
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37143
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37144
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37145
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37146
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StockPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37147
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37148
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockPieceQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37149
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockPieceQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37150
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37151
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37152
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37153
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37154
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM37155
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37156
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StorageFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37157
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StorageFlag_Change(ActionEvent e) throws Exception
	{
	}

	//#CM37158
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37159
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37160
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37161
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37162
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM37163
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageDay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37164
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37165
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37166
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37167
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37168
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37169
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageTime_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM37170
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageTime_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM37171
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Setting_Server(ActionEvent e) throws Exception
	{
	}

	//#CM37172
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

}
//#CM37173
//end of class
