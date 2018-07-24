// $Id: AsAddStorage1Business.java,v 1.2 2006/10/26 04:03:40 suresh Exp $

//#CM34556
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsaddstorage;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocation.ListAsLocationBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocationdetaillist.ListAsLocationDetailListBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstockdetail.ListAsStockDetailBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstockdetailnobtn.ListAsStockDetailNoBtnBusiness;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.asrs.schedule.AsAddStorageSCH;
import jp.co.daifuku.wms.base.entity.WareHouse;

//#CM34557
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * AS/RS product increase unplanned storage setting (location select) screen class<BR>
 * Set the screen input to view state and move those parameters to AS/RS product increase unplanned storage setting (storage info input) screen<BR>
 * <BR>
 * The following process are called in this class<BR>
 * <BR>
 * Next button click process (<CODE>btn_Next_Click</CODE> method)<BR>
 * <BR>
 * <DIR>
 * 		Set input area contents to a parameter and the scheduler uses this parameter to check the input conditions
 * 		Fetch result from schedule and return true if normal. Return false in case of error<BR>
 * 		output the schedule result, schedule message to screen<BR>
 * 		If the result is true, set the input area items to viewstate. The screen moves to AS/RS product 
 *		increase unplanned storage (storage info input) screen<BR>
 * 		<BR>
 * 		[ViewState info]*mandatory input<BR>
 * 		<BR>
 * 		<DIR>
 * 			worker code * <BR>
 * 			password * <BR>
 * 			warehouse station no.* <BR>
 * 			location * <BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:03:40 $
 * @author  $Author: suresh $
 */
public class AsAddStorage1Business extends AsAddStorage1 implements WMSConstants
{
	//#CM34558
	// Class fields --------------------------------------------------

	//#CM34559
	// ViewState use
	//#CM34560
	/**
	 * ViewState use:worker code
	 */
	protected static final String VS_WORKERCODE = "WORKER_CODE";
	
	//#CM34561
	/**
	 * ViewState use:Password
	 */
	protected static final String VS_PASSWORD = "PASSWORD";
	
	//#CM34562
	/**
	 * ViewState use:Consignor code
	 */
	protected static final String VS_CONSIGNORCODE = "CONSIGNOR_CODE";
	
	//#CM34563
	/**
	 * ViewState use:Consignor name
	 */
	protected static final String VS_CONSIGNORNAME = "CONSIGNOR_NAME";
	
	//#CM34564
	/**
	 * ViewState use:Item code
	 */
	protected static final String VS_ITEMCODE = "ITEM_CODE";
	
	//#CM34565
	/**
	 * ViewState use:Item name
	 */
	protected static final String VS_ITEMNAME = "ITEM_NAME";
	
	//#CM34566
	/**
	 * ViewState use:Case Piece type
	 */
	protected static final String VS_CASEPIECE = "CASE_PIECE_FLAG";
	
	//#CM34567
	/**
	 * ViewState use:Location no.
	 */
	protected static final String VS_LOCATION = "LOCATION";
	
	//#CM34568
	/**
	 * ViewState use:warehouse station no.
	 */
	protected static final String VS_WHNUMBER = "WH_STATIONNUMBER";
	
	//#CM34569
	/**
	 * ViewState use:Title
	 */
	protected static final String TITLE = "TITLE";
	
	//#CM34570
	/**
	 *  message
	 */
	protected static final String MESSAGE = "MESSAGE";

	//#CM34571
	/**
	 * Unplanned storage setting (AS/RS product increase) location select screen address
	 */
	protected static final String DO_ADDSTORAGE1 = "/asrs/asrsaddstorage/AsAddStorage1.do";
	
	//#CM34572
	/**
	 * Unplanned storage setting (AS/RS product increase) storage info input screen address
	 */
	private static final String DO_ADDSTORAGE2 = "/asrs/asrsaddstorage/AsAddStorage2.do";

	//#CM34573
	// Class variables -----------------------------------------------

	//#CM34574
	// Class method --------------------------------------------------

	//#CM34575
	// Constructors --------------------------------------------------

	//#CM34576
	// Public methods ------------------------------------------------

	//#CM34577
	/**
	 * screen initialization<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Display title<BR>
	 * 		2.initialize input area<BR>
	 * 		3.On clicking Return button, set the value stored to ViewState in the input area<BR>
	 * 		<BR>
	 * 		<DIR>
	 * 			item name[initial value]<BR>
	 * 			<DIR>
	 * 				worker code*1[nil]<BR>
	 * 				password*1[nil]<BR>
	 * 				warehouse*1[warehouse name]<BR>
	 * 				consignor code*1[if search results a single consignor, display it]<BR>
	 * 				item code*1[nil]<BR>
	 * 				case piece flag[All]<BR>
	 * 				location *1[nil]<BR>
	 * 				<BR>
	 * 				*1<BR>
	 * 				On returning from second screen, set the viewstate info values<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM34578
		// On returning from second screen, set the title
		if (!StringUtil.isBlank(this.getViewState().getString(TITLE)))
		{
			lbl_SettingName.setResourceKey(this.getViewState().getString(TITLE));
		}
		//#CM34579
		// Set the screen to initial state
		setInitView(true);

		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

		Connection conn = null;
		try
		{
			//#CM34580
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM34581
			// fetch Http Locale
			Locale locale = this.getHttpRequest().getLocale();

			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			//#CM34582
			// pull down display data(storage area)
			String[] areaid = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);

			//#CM34583
			// set pulldown data
			PulldownHelper.setPullDown(pul_WareHouse, areaid);

			//#CM34584
			//Bring front the location select tab
			tab_Add_Storage.setSelectedIndex(1);

			//#CM34585
			// Clicking storage info input, return button
			//#CM34586
			// If ViewState has values stored, set those values
			//#CM34587
			// worker code
			if (!StringUtil.isBlank(this.getViewState().getString(VS_WORKERCODE)))
			{
				txt_WorkerCode.setText(this.getViewState().getString(VS_WORKERCODE));
			}
			//#CM34588
			// password
			if (!StringUtil.isBlank(this.getViewState().getString(VS_PASSWORD)))
			{
				txt_Password.setText(this.getViewState().getString(VS_PASSWORD));
			}
			//#CM34589
			// consignor code
			if (!StringUtil.isBlank(this.getViewState().getString(VS_CONSIGNORCODE)))
			{
				txt_ConsignorCode.setText(this.getViewState().getString(VS_CONSIGNORCODE));
			}
			//#CM34590
			// item code
			if (!StringUtil.isBlank(this.getViewState().getString(VS_ITEMCODE)))
			{
				txt_ItemCode.setText(this.getViewState().getString(VS_ITEMCODE));
			}
			//#CM34591
			// location no.
			if (!StringUtil.isBlank(this.getViewState().getString(VS_LOCATION)))
			{
				txt_Location.setText(DisplayText.formatLocationnumber(this.getViewState().getString(VS_LOCATION)));
			}
		}
		catch (Exception ex)
		{
			//#CM34592
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
				//#CM34593
				// close connection
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM34594
	/**
	 * call this before calling the respective control events<BR>
	 * and Set the cursor in supplier code<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM34595
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM34596
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM34597
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}

		//#CM34598
		// set the cursor in worker code input
		setFocus(txt_WorkerCode);
	}

	//#CM34599
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
		DialogParameters param = ((DialogEvent) e).getDialogParameters();

		//#CM34600
		// fetch parameter selected from listbox
		//#CM34601
		// consignor code
		String consignorcode = param.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM34602
		// item code
		String itemcode = param.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
		//#CM34603
		// location no.
		String locationno = param.getParameter(ListAsLocationDetailListBusiness.LOCATION_KEY);

		//#CM34604
		// set the value if not empty
		//#CM34605
		// consignor code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM34606
		// item code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM34607
		// location no.
		if (!StringUtil.isBlank(locationno))
		{
			txt_Location.setText(DisplayText.formatLocationnumber(locationno));
		}
	}

	//#CM34608
	// Package methods -----------------------------------------------

	//#CM34609
	// Protected methods ---------------------------------------------

	//#CM34610
	// Private methods -----------------------------------------------

	//#CM34611
	/**
	 * This method initializes screen<BR>
	 * <BR>
	 * overview ::initial screen display <BR>
	 * <DIR>
	 * 		[parameter] <BR>
	 * 		<DIR>
	 * 			wkrClrFlg if true reset all the screens to initial state. If false reset the screens to initial state based on clear range<BR>
	 * 			<DIR>
	 * 				item name[initial value] <BR>
	 * 				<DIR>
	 * 					worker code[nil/as it is, while clicking clear button]<BR>
	 * 					password[nil/as it is, while clicking clear button]<BR>
	 * 					consignor code [Set to initial display if only one consignor exist]<BR>
	 * 					item code[nil]<BR>
	 * 					case piece flag [ALL]<BR>
	 * 					location no.[nil/as it is, while clicking clear button]<BR>
	 * 				</DIR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * @param wkrClrFlg Clear flag
	 * @throws Exception report all the exceptions
	 */
	private void setInitView(boolean wkrClrFlg) throws Exception
	{
		//#CM34612
		// Initialize input items
		if (wkrClrFlg)
		{
			//#CM34613
			// worker code
			txt_WorkerCode.setText("");
			//#CM34614
			// password
			txt_Password.setText("");
			//#CM34615
			// location no.
			txt_Location.setText("");
		}

		//#CM34616
		// consignor code
		txt_ConsignorCode.setText("");
		//#CM34617
		// item code
		txt_ItemCode.setText("");
		//#CM34618
		// Reset case, piece, none of case/piece type
		rdo_Cpf_Case.setChecked(false);
		rdo_Cpf_Piece.setChecked(false);
		rdo_Cpf_AppointOff.setChecked(false);
		//#CM34619
		// select "ALL" for case piece flag
		rdo_Cpf_All.setChecked(true);

		Connection conn = null;

		try
		{
			//#CM34620
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new AsAddStorageSCH();
			AsScheduleParameter param = (AsScheduleParameter) schedule.initFind(conn, null);

			//#CM34621
			// consignor code
			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				//#CM34622
				//If there is only one consignor code, set it to initial display
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
		}
		catch (Exception ex)
		{
			//#CM34623
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
				//#CM34624
				// close connection
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM34625
	// Event handler methods -----------------------------------------
	//#CM34626
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

	//#CM34627
	/** 
	 * called when "consignor code" search button is clicked<BR>
	 * <BR>
	 * overview ::set search condition in parameter and display the search results in consignor list box<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 * 			Warehouse No.<BR>
	 * 			consignor code <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_PSConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM34628
		// set search conditions to consignor search screen
		ForwardParameters param = new ForwardParameters();
		//#CM34629
		// Warehouse No.
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM34630
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM34631
		// search flag
		param.setParameter(ListAsConsignorBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);
		//#CM34632
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsconsignor/ListAsConsignor.do", param, "/progress.do");
	}

	//#CM34633
	/** 
	 * This process is called upon clicking item code search button<BR>
	 * <BR>
	 * overview ::set search condition in parameter, and display the item code list box<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *    		Warehouse No.<BR>
	 *       	consignor code <BR>
	 *       	item code <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_PSItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM34634
		// Set the search conditions to item search screen
		ForwardParameters param = new ForwardParameters();
		//#CM34635
		// Warehouse No.
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM34636
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM34637
		// item code
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM34638
		// search flag
		param.setParameter(ListAsItemBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);
		//#CM34639
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsitem/ListAsItem.do", param, "/progress.do");
	}

	//#CM34640
	/** 
	 * This method is called upon clicking the inquiry button<BR>
	 * Search empty location and result location and display the stock list based on location in a pop up list<BR>
	 * <BR>
	 * <search condition><BR>
	 * <DIR>
	 *   warehouse station no.<BR>
	 *   consignor code<BR>
	 *   item code<BR>
	 *   Case piece flag<BR>
	 *   status(result location�EEmpty Palette)<BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_Query_Click(ActionEvent e) throws Exception
	{
		//#CM34641
		// Set the search conditions to location based stock list inquiry screen
		ForwardParameters param = new ForwardParameters();

		//#CM34642
		// Fetch warehouse number from warehouse station no.
		param.setParameter(ListAsLocationDetailListBusiness.WAREHOUSE_KEY, pul_WareHouse.getSelectedValue());
		param.setParameter(ListAsLocationDetailListBusiness.WAREHOUSENAME_KEY, pul_WareHouse.getSelectedItem().getText());
		//#CM34643
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM34644
		// item code
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM34645
		// case piece flag
		String casePiece = "";
		if (rdo_Cpf_AppointOff.getChecked())
		{
			//#CM34646
			// None
			casePiece = AsScheduleParameter.CASEPIECE_FLAG_NOTHING;
		}
		else if (rdo_Cpf_Case.getChecked())
		{
			//#CM34647
			// Case
			casePiece = AsScheduleParameter.CASEPIECE_FLAG_CASE;
		}
		else if (rdo_Cpf_Piece.getChecked())
		{
			//#CM34648
			// Piece
			casePiece = AsScheduleParameter.CASEPIECE_FLAG_PIECE;
		}
		else if(rdo_Cpf_All.getChecked())
		{
			//#CM34649
			// All
			casePiece = AsScheduleParameter.CASEPIECE_FLAG_ALL;
		}
		param.setParameter(ListAsLocationDetailListBusiness.CASEPIECEFLAG_KEY, casePiece);
		//#CM34650
		// status that becomes search target
		String[] searchStatus = {Integer.toString(AsScheduleParameter.STATUS_STORAGED), 
								 Integer.toString(AsScheduleParameter.STATUS_EMPTYPALETTE)};
		param.setParameter(ListAsLocationDetailListBusiness.STATUS_KEY, searchStatus);

		//#CM34651
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrslocationdetaillist/ListAsLocationDetailList.do", param, "/progress.do");
		
	}

	//#CM34652
	/**
	 * called when clear button is clicked<BR>
	 * <BR>
	 * Abstract :clear the input area<BR>
	 * <BR>
	 * <DIR>
	 * 		1.initialize input area<BR>
	 * 		2.set cursor in worker code<BR>
	 * </DIR>
	 * <BR>
	 * item name[initial value]<BR>
	 * <DIR>
	 * 		worker code[as it is]<BR>
	 * 		password[as it is]<BR>
	 * 		warehouse[as it is]<BR>
	 * 		consignor code[if search results a single consignor, display it]<BR>
	 * 		item code[nil]<BR>
	 * 		case piece flag[All]<BR>
	 * 		location[as it is]<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM34653
		// Initialize the screen
		setInitView(false);

		//#CM34654
		// set the cursor in worker code input
		setFocus(txt_WorkerCode);
	}

	//#CM34655
	/** 
	 * This method is called when the location detail button is clicked<BR>
	 * Displays the location detail list box<BR>
	 * <BR>
	 * <search condition><BR>
	 * <DIR>
	 *   warehouse station no.<BR>
	 *   warehouse name<BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Detail_Click(ActionEvent e) throws Exception
	{
		//#CM34656
		// Set the search conditions to location detail list inquiry screen
		ForwardParameters param = new ForwardParameters();
		Connection conn = null;
		try
		{
			if( !StringUtil.isBlank(txt_Location.getText()) )
			{
				//#CM34657
				// Create station no. from location and set to parameter
				String stationNo = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_Location.getString());
				param.setParameter(ListAsStockDetailBusiness.LOCATION_KEY, stationNo);
			}
			else
			{
				//#CM34658
				// location no. is mandatory input
				throw new ScheduleException();
			}

			//#CM34659
			// warehouse station no.
			param.setParameter(ListAsStockDetailNoBtnBusiness.WAREHOUSE_KEY, pul_WareHouse.getSelectedValue());
			//#CM34660
			// warehouse name
			param.setParameter(ListAsStockDetailNoBtnBusiness.WAREHOUSENAME_KEY, pul_WareHouse.getSelectedItem().getText());
		
			//#CM34661
			// in process screen -> result screen
			redirect("/asrs/listbox/listasrsstockdetailnobtn/ListAsStockDetailNoBtn.do", param, "/progress.do");
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM34662
				// close connection
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

	//#CM34663
	/**
	 * This method is called upon clicking next button<BR>
	 * <BR>
	 * Abstract :Based on input item conditions, move to AS/RS product increase unplanned storage setting (storage info input) screen<BR>
	 * <BR>
	 * <DIR>
	 *    1.Check the input area input items (mandatory input, character count, character attribute)<BR>
	 *    2.start scheduler<BR>
	 * 	  <DIR>
	 *   	[parameter]*mandatory input<BR>
	 *   	<DIR>
	 * 			worker code * <BR>
	 * 			password * <BR>
	 * 			location no. * <BR>
	 *      </DIR>
	 *    </DIR>
	 *    3-1.If the schedule result is true, store the input items to 
         *    viewstate and move to AS/RS product increase unplanned
	 *    storage setting (storage info input) screen<BR>
	 *    3-2.If the schedule result is false, display the message received from schedule in the screen<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Next_Click(ActionEvent e) throws Exception
	{
		//#CM34664
		// set the cursor in worker code input
		setFocus(txt_WorkerCode);

		//#CM34665
		// mandatory input check
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_Location.validate();

		//#CM34666
		// set parameter
		AsScheduleParameter param = new AsScheduleParameter();

		//#CM34667
		// worker code
		param.setWorkerCode(txt_WorkerCode.getText());
		//#CM34668
		// password
		param.setPassword(txt_Password.getText());

		Connection conn = null;
		try
		{
			//#CM34669
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM34670
			// location no.
			String tstation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_Location.getString());
			param.setLocationNo(tstation);

			//#CM34671
			// create instance of schedule class
			WmsScheduler schedule = new AsAddStorageSCH();

			//#CM34672
			// if input parameter of schedule class is valid
			//#CM34673
			// set the screen parameter to viewstate and move to AS/RS product increase unplanned storage (storage info input) screen
			if (schedule.nextCheck(conn, param))
			{
				//#CM34674
				// worker code
				viewState.setString(VS_WORKERCODE, txt_WorkerCode.getText());
				//#CM34675
				// password
				viewState.setString(VS_PASSWORD, txt_Password.getText());
				//#CM34676
				// warehouse station no.
				viewState.setString(VS_WHNUMBER, pul_WareHouse.getSelectedValue());
				//#CM34677
				// location no.
				viewState.setString(VS_LOCATION, tstation);
				//#CM34678
				// title
				viewState.setString(TITLE, lbl_SettingName.getResourceKey());

				//#CM34679
				// location selection screen -> storage info input screen
				forward(DO_ADDSTORAGE2);

				if (schedule.getMessage() != null)
				{
					//#CM34680
					// set message
					message.setMsgResourceKey(schedule.getMessage());
				}
			}
			else
			{
				//#CM34681
				// display error message if target data does not exist
				if (schedule.getMessage() != null)
				{
					message.setMsgResourceKey(schedule.getMessage());
				}
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
				//#CM34682
				// close connection
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
	//#CM34683
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34684
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34685
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Add_Storage_Click(ActionEvent e) throws Exception
	{
	}

	//#CM34686
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34687
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34688
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34689
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM34690
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM34691
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM34692
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34693
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34694
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM34695
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM34696
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM34697
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34698
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34699
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Change(ActionEvent e) throws Exception
	{
	}

	//#CM34700
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34701
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34702
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM34703
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM34704
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM34705
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34706
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34707
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34708
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM34709
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM34710
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM34711
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34712
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34713
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_All_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34714
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_All_Click(ActionEvent e) throws Exception
	{
	}

	//#CM34715
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Case_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34716
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Case_Click(ActionEvent e) throws Exception
	{
	}

	//#CM34717
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Piece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34718
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Piece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM34719
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_AppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34720
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_AppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM34721
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LocationStockDisp_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34722
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Query_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34723
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34724
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LocationNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34725
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Location_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34726
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Location_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM34727
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Location_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM34728
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Detail_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34729
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Next_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM34730
//end of class
