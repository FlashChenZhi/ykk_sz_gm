// $Id: AsAddStorage2Business.java,v 1.2 2006/10/26 04:02:39 suresh Exp $

//#CM34735
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
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Label;
import jp.co.daifuku.bluedog.ui.control.NumberTextBox;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstockdetail.ListAsStockDetailBusiness;
import jp.co.daifuku.wms.asrs.schedule.AsAddStorageSCH;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;

//#CM34736
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * AS/RS product increase unplanned storage setting (storage info input) screen class<BR>
 * Display the previous screen input in the upper part of current screen<BR>
 * Set schedule parameter to AS/RS product incraese unplanned storage setting <BR>
 * and transaction commit and rollback are done in this screen<BR>
 * <BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.Input button click process (<CODE>btn_Input_Click</CODE>method)<BR>
 * <BR>
 * <DIR>
 * 		The content input from input area is set in the parameter, and the schedule checks the input condition based on the parameter. <BR>
 * 		Receive true when the result is received from the schedule, and processing is completed normally. 
 * 		Receive false when it does not complete the schedule because of the condition error etc.<BR>
 * 		output the schedule result, schedule message to screen<BR>
 * 		Filter information on input area and add it to the area when the result is true. <BR>
 * 		Update our data for the correction in information on input area when you press the input button after the correction button is pressed. <BR>
 * 		<BR>
 * 		[parameter] *mandatory input<BR>
 * 		<BR>
 * 		<DIR>
 * 			consignor code *<BR>
 * 			consignor name <BR>
 * 			item code *<BR>
 * 			item name <BR>
 * 			case/piece flag *<BR>
 * 			packed qty per case *2<BR>
 * 			storage case qty *2 *4<BR>
 * 			Case ITF <BR>
 * 			packed qty per piece <BR>
 *			storage piece qty *3 *4<BR>
 *			bundle itf <BR>
 *			expiry date <BR>
 *			list print flag<BR>
 *		<BR>
 * 			*2 <BR>
 * 			mandatory input if case/piece flag is 1:case<BR>
 * 			*3 <BR>
 * 			mandatory input if case/piece flag is 2:piece<BR>
 * 			*4 <BR>
 * 			If case/piece type is 0:none<BR>
 *			Either storage case qty or storage piece qty is mandatory <BR>
 * 			case qty x packed qty per case + piece qty is not 0(>0) <BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * 2.Processing when Start Storage button is pressed(<CODE>btn_StorageStart_Click</CODE>method )<BR>
 * <BR>
 * <DIR>
 * 		The filtered content of the area is set in the parameter, and the schedule does the setting of the stock outside AS/RS schedule based on the parameter. <BR>
 * 		Receive true when the result is received from the schedule, and processing is completed normally. 
 * 		Receive false when it does not complete the schedule because of the condition error etc.<BR>
 * 		output the schedule result, schedule message to screen<BR>
 * 		<BR>
 * 		[parameter]<BR>
 * 		<BR>
 * 		<DIR>
 * 			ViewState.worker code<BR>
 * 			ViewState.area no.<BR>
 * 			ViewState.warehouse<BR>
 * 			ViewState.location no.<BR>
 * 			ViewState.sending station<BR>
 * 			pulldown.receiving station<BR>
 * 			list print flag<BR>
 * 			preset area.case piece flag(Concealment item)<BR>
 * 			preset area.consignor code<BR>
 * 			preset area.consignor name<BR>
 * 			preset area.item code<BR>
 * 			preset area.item name<BR>
 * 			preset area.storage case qty<BR>
 * 			preset area.storage piece qty<BR>
 * 			preset area.packed qty per case<BR>
 * 			preset area.packed qty per piece<BR>
 * 			preset area.case piece flag<BR>
 * 			preset area.Case ITF<BR>
 * 			preset area.bundle itf<BR>
 * 			preset area.expiry date<BR>
 * 			preset area line no.<BR>
 * 			terminal no.<BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:02:39 $
 * @author  $Author: suresh $
 */
public class AsAddStorage2Business extends AsAddStorage2 implements WMSConstants
{
	//#CM34737
	// Class fields --------------------------------------------------

	//#CM34738
	// ViewState useKEY(Concealment parameter)
	//#CM34739
	/**
	 * preset area line no.
	 */
	private static final String LineNo = "LINENO";
	//#CM34740
	/**
	 * Location details button
	 */
	private static final String btnShelfDetail = "SHELFDETAIL";

	//#CM34741
	// Class variables -----------------------------------------------

	//#CM34742
	// Class method --------------------------------------------------

	//#CM34743
	// Constructors --------------------------------------------------

	//#CM34744
	// Public methods ------------------------------------------------

	//#CM34745
	/**
	 * It is called when default value of the screen is displayed.<BR>
	 * <BR>
	 * overview ::initial screen display<BR>
	 * <BR>
	 * <DIR>
	 *   	item name[initial value]<BR>
	 *   	<DIR>
	 * 			warehouse[Location selection-warehouse] <BR>
	 * 			location[Location selection-location] <BR>
	 * 			workplace[Workplace name] <BR>
	 * 			station[Automatic division] <BR>
	 * 			consignor code[if search results a single consignor, display it] <BR>
	 * 			consignor name[Consignor search result[0]-consignor name] <BR>
	 * 			item code[nil] <BR>
	 * 			item name[nil] <BR>
	 * 			case piece flag[Case]<BR>
	 * 			packed qty per case[nil] <BR>
	 * 			packed qty per piece[nil] <BR>
	 * 			storage case qty[nil] <BR>
	 * 			storage piece qty[nil] <BR>
	 * 			Case ITF[nil]<BR>
	 * 			bundle itf[nil]<BR>
	 * 			expiry date[nil] <BR>
	 * 	 	</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM34746
		// title
		lbl_SettingName.setText(DisplayText.getText(this.getViewState().getString(AsAddStorage1Business.TITLE)));

		Connection conn = null;

		try
		{
			//#CM34747
			// The tab of the stock information input former is put out. 
			tab_Add_Storage.setSelectedIndex(2);

			//#CM34748
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM34749
			// Fetch warehouse number from warehouse station no.
			ASFindUtil util = new ASFindUtil(conn);
			String whstno = this.getViewState().getString(AsAddStorage1Business.VS_WHNUMBER);
			lbl_JavaSetWareHouse.setText(util.getWareHouseName(whstno));

			//#CM34750
			// location no.
			lbl_JavaSetLocationNo.setText(DisplayText.formatDispLocation(this.getViewState().getString(AsAddStorage1Business.VS_LOCATION)));

			//#CM34751
			// fetch Http Locale
			Locale locale = this.getHttpRequest().getLocale();

			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			//#CM34752
			// pull down display data(storage area)
			String[] areaid = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, whstno, false);
			//#CM34753
			// pull down display data(workplace)
			String[] sagyoba = pull.getSagyobaPulldownData(conn, PulldownData.STATION_ADD_STORAGE, true, "");
			//#CM34754
			// pull down display data(station)
			String[] stno = pull.getStationPulldownData(conn, PulldownData.STATION_ADD_STORAGE, true, "");

			//#CM34755
			// set pulldown data
			PulldownHelper.setPullDown(pul_WareHouse, areaid);
			PulldownHelper.setLinkedPullDown(pul_WorkPlace, sagyoba);
			PulldownHelper.setLinkedPullDown(pul_Station, pul_WorkPlace, stno);

			//#CM34756
			// add child pull down
			pul_WareHouse.addChild(pul_WorkPlace);
			pul_WorkPlace.addChild(pul_Station);

			//#CM34757
			// Do not display warehouse Pulldown because it is parents Pulldown for synchronization Pulldown. 
			pul_WareHouse.setVisible(false);

			//#CM34758
			// Initialize input area. 
			inputAreaClear();
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM34759
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
	}

	//#CM34760
	/**
	 * call this before calling the respective control events<BR>
	 * <BR>
	 * overview ::display confirmation dialog<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM34761
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM34762
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM34763
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}
		//#CM34764
		// Confirmation dialog when Start Storage button is pressed "Want to set?"
		btn_StorageStart.setBeforeConfirm("MSG-9000");

		//#CM34765
		// When the Clear list button is pressed, list input information confirmation dialog "Is it OK to clear the list?"
		btn_AllCancel.setBeforeConfirm("MSG-W0012");
	}

	//#CM34766
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

		String type = param.getParameter(ListAsStockDetailBusiness.PROCESSTYPE_KEY);
		if( type == null )
		{
			type = "";
		}

		//#CM34767
		// fetch parameter selected from listbox
		//#CM34768
		// consignor code
		String consignorcode = param.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM34769
		// consignor name
		String consignorname = param.getParameter(ListAsConsignorBusiness.CONSIGNORNAME_KEY);
		//#CM34770
		// item code
		String itemcode = param.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
		//#CM34771
		// item name
		String itemname = param.getParameter(ListAsItemBusiness.ITEMNAME_KEY);
		//#CM34772
		// case piece flag
		String casePiece = param.getParameter(ListAsStockDetailBusiness.CASEPIECEFLAG_KEY);
		//#CM34773
		// packed qty per case
		String enteringQty = param.getParameter(ListAsStockDetailBusiness.ENTERING_KEY);
		//#CM34774
		// Case ITF
		String caseItf = param.getParameter(ListAsStockDetailBusiness.ITF_KEY);
		//#CM34775
		// packed qty per piece
		String BundleEnteringQty = param.getParameter(ListAsStockDetailBusiness.BUNDLEENTERING_KEY);
		//#CM34776
		// bundle itf
		String bundleItf = param.getParameter(ListAsStockDetailBusiness.BUNDLEITF_KEY);
		//#CM34777
		// expiry date
		String useByDate = param.getParameter(ListAsStockDetailBusiness.USEBYDATE_KEY);

		//#CM34778
		// set the value if not empty
		//#CM34779
		// consignor code, consignor name
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
			txt_ConsignorName.setText(consignorname);
		}
		//#CM34780
		// item code, item name
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
			txt_ItemName.setText(itemname);
		}
		if( type.equals(btnShelfDetail) )
		{
			if (casePiece.equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
			{
				//#CM34781
				// check none
				rdo_Cpf_AppointOff.setChecked(true);
				rdo_Cpf_Case.setChecked(false);
				rdo_Cpf_Piece.setChecked(false);
			}
			else if (casePiece.equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
			{
				//#CM34782
				// check case
				rdo_Cpf_Case.setChecked(true);
				rdo_Cpf_Piece.setChecked(false);
				rdo_Cpf_AppointOff.setChecked(false);
			}
			else if (casePiece.equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
			{
				//#CM34783
				// check piece
				rdo_Cpf_Piece.setChecked(true);
				rdo_Cpf_Case.setChecked(false);
				rdo_Cpf_AppointOff.setChecked(false);
			}
			//#CM34784
			// packed qty per case
			txt_CaseEntering.setText(enteringQty);
			//#CM34785
			// Case ITF
			txt_CaseItf.setText(caseItf);
			//#CM34786
			// packed qty per piece
			txt_PieceEntering.setText(BundleEnteringQty);
			//#CM34787
			// bundle itf
			txt_BundleItf.setText(bundleItf);
			//#CM34788
			// expiry date
			txt_UseByDate.setText(useByDate);
		}
	}

	//#CM34789
	// Package methods -----------------------------------------------

	//#CM34790
	// Protected methods ---------------------------------------------
	//#CM34791
	/**
	 * input check�B<BR>
	 * <BR>
	 * Abstract : Set the message, and return false when abnormality is found. <BR>
	 * <BR>
	 * @return input check result(true:Normal false:Abnormal)
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

	//#CM34792
	// Private methods -----------------------------------------------

	//#CM34793
	/**
	 * Make input area first stage status. <BR>
	 * <BR>
	 * overview ::Empty all input area, and nullify the input button and clear button. <BR>
	 * and Set the and cursor in the Consignor code. <BR>
	 * <BR>
	 * @throws Exception report all the exceptions
	 */
	private void inputAreaClear() throws Exception
	{
		//#CM34794
		// Empty the input item. 
		inputDataClear();

		//#CM34795
		// Nullify the button of input area. 
		//#CM34796
		// Storage start button
		btn_StorageStart.setEnabled(false);
		//#CM34797
		// All cancel button
		btn_AllCancel.setEnabled(false);

		//#CM34798
		// Correction status (filtered line No) is reset in the default value. 
		this.getViewState().setInt(LineNo, -1);

		//#CM34799
		// Reset the color of the selection line in the default value.
		lst_AddStorage.resetHighlight();

		//#CM34800
		// Set the cursor in the consignor code. 
		setFocus(txt_ConsignorCode);
	}

	//#CM34801
	/**
	 * Make input area first stage status. <BR>
	 * <BR>
	 * overview ::Empty all input area.  <BR>
	 * <BR>
	 *  
	 * @throws Exception report all the exceptions
	 */
	private void inputDataClear() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM34802
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new AsAddStorageSCH();
			AsScheduleParameter param = (AsScheduleParameter) schedule.initFind(conn, null);
	
			//#CM34803
			// consignor code
			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				//#CM34804
				//If there is only one consignor code, set it to initial display
				txt_ConsignorCode.setText(param.getConsignorCode());
				txt_ConsignorName.setText(param.getConsignorName());
			}
			else
			{
				//#CM34805
				// consignor code
				txt_ConsignorCode.setText("");
				//#CM34806
				// consignor name
				txt_ConsignorName.setText("");
			}
			
			//#CM34807
			// item code
			txt_ItemCode.setText("");
			//#CM34808
			// item name
			txt_ItemName.setText("");
			//#CM34809
			// case piece flag
			rdo_Cpf_Case.setChecked(true);
			rdo_Cpf_Piece.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);
			//#CM34810
			// packed qty per case
			txt_CaseEntering.setText("");
			//#CM34811
			// case storage qty
			txt_StrgCaseQty.setText("");
			//#CM34812
			// Case ITF
			txt_CaseItf.setText("");
			//#CM34813
			// packed qty per piece
			txt_PieceEntering.setText("");
			//#CM34814
			// piece storage qty
			txt_StrgPieceQty.setText("");
			//#CM34815
			// bundle itf
			txt_BundleItf.setText("");
			//#CM34816
			// expiry date
			txt_UseByDate.setText("");
			//#CM34817
			// check [Print storage work list] check box
			chk_CommonUse.setChecked(true);
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM34818
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

	//#CM34819
	/** 
	 * The method of checking the qty. <BR>
	 * <BR>
	 * overview ::Check if the values of the entering qty and the storage qty are 0 or more. <BR>
	 * <BR>
	 * @param figure     Use the digit to check the value. 
	 * @param name       The item name which checks the value. 
	 * @return Return null when the item name is returned when the use digit or the position is not one or more, and it is not so. 
	 * @throws Exception report all the exceptions
	 */
	private String checkNumber(NumberTextBox figure, Label name) throws Exception
	{
		String itemName = null;

		if (!StringUtil.isBlank(Integer.toString(figure.getInt())))
		{
			if (figure.getInt() < 0)
			{
				//#CM34820
				// The item name is set in itemName. 
				itemName = DisplayText.getText(name.getResourceKey());
				return itemName;
			}
		}
		return itemName;
	}

	//#CM34821
	/**
	 * this method sets preset area data to Parameter class<BR>
	 * <BR>
	 * overview :: Set the data of the area in the Parameter class. <BR>
	 * <BR>
	 * 1.Set data in the Parameter class for all if you press the Start Storage button. (Line No of filtered correction objects = -1)<BR>
	 * 2.Set data in the Parameter class because the line for the correction was excluded in case of the input data under the correction. <BR>
	 * <DIR>
	 *   	[parameter] *mandatory input<BR>
	 *   	<DIR>
	 * 			ViewState.worker code<BR>
	 * 			ViewState.area no.<BR>
	 * 			ViewState.warehouse<BR>
	 * 			ViewState.location no.<BR>
	 * 			ViewState.sending station<BR>
	 * 			pulldown.receiving station<BR>
	 * 			list print flag<BR>
	 * 			preset area.case piece flag(Concealment item)<BR>
	 * 			preset area.consignor code<BR>
	 * 			preset area.consignor name<BR>
	 * 			preset area.item code<BR>
	 * 			preset area.item name<BR>
	 * 			preset area.storage case qty<BR>
	 * 			preset area.storage piece qty<BR>
	 * 			preset area.packed qty per case<BR>
	 * 			preset area.packed qty per piece<BR>
	 * 			preset area.case piece flag<BR>
	 * 			preset area.Case ITF<BR>
	 * 			preset area.bundle itf<BR>
	 * 			preset area.expiry date<BR>
	 * 			preset area line no.<BR>
	 * 			terminal no.<BR>
	 * 		</DIR>
	 * </DIR>
	 * @param lineno int Line No. for correction
	 * @return <code>AsScheduleParameter</code> array which maintains information on filtering area. Set Null when there is no data. 
	 * @throws Exception report all the exceptions
	 */
	private AsScheduleParameter[] setListParam(int lineno) throws Exception
	{
		Vector vecParam = new Vector();

		for (int i = 1; i < lst_AddStorage.getMaxRows(); i++)
		{
			//#CM34822
			// Exclude the line for the correction. 
			if (i == lineno)
			{
				continue;
			}

			//#CM34823
			// specify line
			lst_AddStorage.setCurrentRow(i);

			//#CM34824
			// set schedule parameter
			AsScheduleParameter param = new AsScheduleParameter();
			//#CM34825
			// worker code
			param.setWorkerCode(this.getViewState().getString(AsAddStorage1Business.VS_WORKERCODE));
			//#CM34826
			// area no.
			param.setAreaNo(this.getViewState().getString(AsAddStorage1Business.VS_WHNUMBER));
			//#CM34827
			// warehouse
			param.setWareHouseNo(this.getViewState().getString(AsAddStorage1Business.VS_WHNUMBER));
			//#CM34828
			// location no.
			param.setLocationNo(this.getViewState().getString(AsAddStorage1Business.VS_LOCATION));
			//#CM34829
			// sending station
			param.setFromStationNo(this.getViewState().getString(AsAddStorage1Business.VS_LOCATION));
			//#CM34830
			// receiving station
			param.setToStationNo(pul_Station.getSelectedValue());
			if( pul_Station.getSelectedValue().equals(Station.AUTO_SELECT_STATIONNO))
			{
				//#CM34831
				// Set workplace when [Automatic division] is specified for station. 
				param.setSagyoba(pul_WorkPlace.getSelectedValue());
			}
			
			//#CM34832
			// input area info
			//#CM34833
			// terminal no.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());
			
			//#CM34834
			// list print flag
			param.setListFlg(chk_CommonUse.getChecked());
			//#CM34835
			// Case/Piece flag(Concealment item)
			param.setCasePieceFlag(lst_AddStorage.getValue(0));
			//#CM34836
			// consignor code
			param.setConsignorCode(lst_AddStorage.getValue(3));
			//#CM34837
			// item code
			param.setItemCode(lst_AddStorage.getValue(4));
			//#CM34838
			// storage case qty
			param.setStorageCaseQty(Formatter.getInt(lst_AddStorage.getValue(5)));
			//#CM34839
			// packed qty per case
			param.setEnteringQty(Formatter.getInt(lst_AddStorage.getValue(6)));
			//#CM34840
			// case piece flag
			param.setCasePieceFlagNameDisp(lst_AddStorage.getValue(7));
			//#CM34841
			// Case ITF
			param.setITF(lst_AddStorage.getValue(8));
			//#CM34842
			// expiry date
			String str = lst_AddStorage.getValue(9);
			if (str.length() <= 8)
			{
				param.setUseByDate(str);
			}
			else
			{
				param.setUseByDate(str.substring(0,4) +	str.substring(5,7) + str.substring(8,10));
			}
			//#CM34843
			// consignor name
			param.setConsignorName(lst_AddStorage.getValue(10));
			//#CM34844
			// item name
			param.setItemName(lst_AddStorage.getValue(11));
			//#CM34845
			// storage piece qty
			param.setStoragePieceQty(Formatter.getInt(lst_AddStorage.getValue(12)));
			//#CM34846
			// packed qty per piece
			param.setBundleEnteringQty(Formatter.getInt(lst_AddStorage.getValue(13)));
			//#CM34847
			// bundle itf
			param.setBundleITF(lst_AddStorage.getValue(14));

			//#CM34848
			// save the line no.
			param.setRowNo(i);

			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			//#CM34849
			// set preset area data if value is not null
			AsScheduleParameter[] listparam = new AsScheduleParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			//#CM34850
			// if there is no preset area data, set null
			return null;
		}
	}

	//#CM34851
	/**
	 * AS/RS parameter which corresponds from input area.Case/Piece flag radiobutton. Acquire Case/piece flag. <BR>
	 * <BR>
	 * @return case piece flag
	 * @throws Exception report all the exceptions 
	 */
	private String getCasePieceFlagFromInputArea() throws Exception
	{
		//#CM34852
		// case piece flag
		if (rdo_Cpf_AppointOff.getChecked())
		{
			//#CM34853
			// None
			return AsScheduleParameter.CASEPIECE_FLAG_NOTHING;
		}
		else if (rdo_Cpf_Case.getChecked())
		{
			//#CM34854
			// Case
			return AsScheduleParameter.CASEPIECE_FLAG_CASE;
		}
		else if (rdo_Cpf_Piece.getChecked())
		{
			//#CM34855
			// Piece
			return AsScheduleParameter.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}

	//#CM34856
	/**
	 * Acquire the corresponding case/piece flag from AS/RS parameter Case/Piece flag. <BR>
	 * <BR>
	 * @param value AS/RS parameter corresponding to radiobutton. case piece flag
	 * @return case piece flag
	 * @throws Exception report all the exceptions  
	 */
	private String getCasePieceFlag(String value) throws Exception
	{
		//#CM34857
		// case piece flag
		if (value.equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM34858
			// None
			return SystemDefine.CASEPIECE_FLAG_NOTHING;
		}
		else if (value.equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
		{
			//#CM34859
			// Case
			return SystemDefine.CASEPIECE_FLAG_CASE;
		}
		else if (value.equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM34860
			// Piece
			return SystemDefine.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}

	//#CM34861
	/**
	 * Display the content of list cell flag to case/piece flag check box of input area. <BR>
	 * <BR>
	 * @throws Exception report all the exceptions 
	 */
	private void setCasePieceRBFromList() throws Exception
	{
		if (lst_AddStorage.getValue(0).equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM34862
			// check none
			rdo_Cpf_AppointOff.setChecked(true);
			rdo_Cpf_Case.setChecked(false);
			rdo_Cpf_Piece.setChecked(false);
		}
		else if (lst_AddStorage.getValue(0).equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
		{
			//#CM34863
			// check case
			rdo_Cpf_Case.setChecked(true);
			rdo_Cpf_Piece.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);
		}
		else if (lst_AddStorage.getValue(0).equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM34864
			// check piece
			rdo_Cpf_Piece.setChecked(true);
			rdo_Cpf_Case.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);
		}
	}

	//#CM34865
	/**
	 * The method of filtering and setting the value in the area. <BR>
	 * <BR>
	 * overview ::Filter and set the value in the area. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Edit data for the balloon display. <BR>
	 * 		2.Set Concealment item. <BR>
	 * 		3.Set data in the every particular item of the list cell. <BR>
	 * </DIR>
	 * <DIR>
	 * 		[Concealment item]
	 * 		<DIR>
	 * 			0.case piece flag<BR>
	 *	 	</DIR>
	 * </DIR>
	 * <DIR>
	 * 		[list cell line no. list]
	 * 		<DIR>
	 * 			0.Concealment item <BR>
	 * 			3.consignor code <BR>
	 * 			4.item code <BR>
	 * 			5.storage case qty <BR>
	 * 			6.packed qty per case <BR>
	 * 			7.classification <BR>
	 * 			8.Case ITF <BR>
	 * 			9.expiry date <BR>
	 * 			10.consignor name <BR>
	 * 			11.item name <BR>
	 * 			12.storage piece qty <BR>
	 * 			13.packed qty per piece <BR>
	 * 			14.bundle itf <BR>
	 *	 	</DIR>
	 * </DIR>
	 * <BR>
	 * @throws Exception report all the exceptions
	 */
	private void setList() throws Exception
	{
		//#CM34866
		//add data to tool tip
		ToolTipHelper toolTip = new ToolTipHelper();
		//#CM34867
		// consignor name
		toolTip.add(DisplayText.getText("LBL-W0026"), txt_ConsignorName.getText());
		//#CM34868
		// item name
		toolTip.add(DisplayText.getText("LBL-W0103"), txt_ItemName.getText());
		//#CM34869
		// expiry date
		toolTip.add(DisplayText.getText("LBL-W0270"), txt_UseByDate.getText());

		//#CM34870
		//Set tool tip in the current line. 
		lst_AddStorage.setToolTip(lst_AddStorage.getCurrentRow(), toolTip.getText());

		//#CM34871
		// Case/Piece flag(Concealment item)
		lst_AddStorage.setValue(0, getCasePieceFlagFromInputArea());
		//#CM34872
		// consignor code
		lst_AddStorage.setValue(3, txt_ConsignorCode.getText());
		//#CM34873
		// item code
		lst_AddStorage.setValue(4, txt_ItemCode.getText());
		//#CM34874
		// storage case qty
		if (StringUtil.isBlank(txt_StrgCaseQty.getText()))
		{
			lst_AddStorage.setValue(5, "0");
		}
		else
		{
			lst_AddStorage.setValue(5, WmsFormatter.getNumFormat(txt_StrgCaseQty.getInt()));
		}
		//#CM34875
		// packed qty per case
		if (StringUtil.isBlank(txt_CaseEntering.getText()))
		{
			lst_AddStorage.setValue(6, "0");
		}
		else
		{
			lst_AddStorage.setValue(6, WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));

		}
		//#CM34876
		// classification
		lst_AddStorage.setValue(7, DisplayUtil.getPieceCaseValue(getCasePieceFlag(getCasePieceFlagFromInputArea())));
		//#CM34877
		// Case ITF
		lst_AddStorage.setValue(8, txt_CaseItf.getText());
		//#CM34878
		// expiry date
		lst_AddStorage.setValue(9, txt_UseByDate.getText());
		//#CM34879
		// consignor name
		lst_AddStorage.setValue(10, txt_ConsignorName.getText());
		//#CM34880
		// item name
		lst_AddStorage.setValue(11, txt_ItemName.getText());
		//#CM34881
		// storage piece qty
		if (StringUtil.isBlank(txt_StrgPieceQty.getText()))
		{
			lst_AddStorage.setValue(12, "0");
		}
		else
		{
			lst_AddStorage.setValue(12, WmsFormatter.getNumFormat(txt_StrgPieceQty.getInt()));
		}

		//#CM34882
		// packed qty per piece
		if (StringUtil.isBlank(txt_PieceEntering.getText()))
		{
			lst_AddStorage.setValue(13, "0");
		}
		else
		{
			lst_AddStorage.setValue(13, WmsFormatter.getNumFormat(txt_PieceEntering.getInt()));
		}
		//#CM34883
		// bundle itf
		lst_AddStorage.setValue(14, txt_BundleItf.getText());
	}

	//#CM34884
	// Event handler methods -----------------------------------------
	//#CM34885
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34886
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34887
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Add_Storage_Click(ActionEvent e) throws Exception
	{
	}

	//#CM34888
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34889
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

	//#CM34890
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_1_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34891
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetWareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34892
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LocationNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34893
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetLocationNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34894
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_LocationDetails_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34895
	/** 
	 * This method is called when the location detail button is clicked<BR>
	 * Displays the location detail list box<BR>
	 * <BR>
	 * <search condition><BR>
	 * <DIR>
	 *   warehouse station no.<BR>
	 *   warehouse name<BR>
	 *   location<BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_LocationDetails_Click(ActionEvent e) throws Exception
	{
		//#CM34896
		// Set the search condition on the shelf details screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM34897
		// warehouse station no.
		param.setParameter(ListAsStockDetailBusiness.WAREHOUSE_KEY, pul_WareHouse.getSelectedValue());
		//#CM34898
		// warehouse name
		param.setParameter(ListAsStockDetailBusiness.WAREHOUSENAME_KEY, pul_WareHouse.getSelectedItem().getText());
		//#CM34899
		// location no.
		String tno = this.getViewState().getString(AsAddStorage1Business.VS_LOCATION);
		param.setParameter(ListAsStockDetailBusiness.LOCATION_KEY, tno);
		//#CM34900
		// Delivery information button
		param.setParameter(ListAsStockDetailBusiness.PROCESSTYPE_KEY , btnShelfDetail);
		//#CM34901
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsstockdetail/ListAsStockDetail.do", param, "/progress.do");
	}

	//#CM34902
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkPlace_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34903
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkPlace_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34904
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkPlace_Change(ActionEvent e) throws Exception
	{
	}

	//#CM34905
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Station_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34906
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Station_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34907
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_Station_Change(ActionEvent e) throws Exception
	{
	}

	//#CM34908
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34909
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34910
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM34911
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM34912
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM34913
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34914
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
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM34915
		// set search conditions to consignor search screen
		ForwardParameters param = new ForwardParameters();
		//#CM34916
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM34917
		// search flag
		param.setParameter(ListAsConsignorBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);
		//#CM34918
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsconsignor/ListAsConsignor.do", param, "/progress.do");
	}

	//#CM34919
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34920
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34921
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM34922
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM34923
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM34924
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34925
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34926
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM34927
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM34928
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM34929
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34930
	/** 
	 * This process is called upon clicking item code search button<BR>
	 * <BR>
	 * overview ::set search condition in parameter��and display the item code list box<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       consignor code <BR>
	 *       item code <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM34931
		// Set the search conditions to item search screen
		ForwardParameters param = new ForwardParameters();
		//#CM34932
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM34933
		// item code
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM34934
		// search flag
		param.setParameter(ListAsItemBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);
		//#CM34935
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsitem/ListAsItem.do", param, "/progress.do");
	}

	//#CM34936
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34937
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34938
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM34939
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM34940
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM34941
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34942
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Case_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34943
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Case_Click(ActionEvent e) throws Exception
	{
	}

	//#CM34944
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Piece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34945
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Piece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM34946
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_AppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34947
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_AppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM34948
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34949
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34950
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34951
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34952
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM34953
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM34954
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM34955
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PieceEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34956
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34957
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM34958
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PieceEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM34959
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StoragePieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34960
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34961
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPieceQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM34962
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPieceQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM34963
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34964
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34965
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM34966
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM34967
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM34968
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34969
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34970
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM34971
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM34972
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Input_Server(ActionEvent e) throws Exception
	{
	}

	//#CM34973
	/** 
	 * it is called when the input button is pressed.<BR>
	 * <BR>
	 * Abstract : Check the input item of input area, and display it after filtering. <BR>
	 * <BR>
	 * <DIR>
	 *   1.set the cursor in consignor code<BR>
	 *   2.Check the input area input items(mandatory input, character count, character attribute)<BR>
	 *   3.start scheduler<BR>
	 * 	 <DIR>
	 *   	[parameter] *mandatory input<BR>
	 *   	<DIR>
	 * 			consignor code *<BR>
	 * 			consignor name <BR>
	 * 			item code *<BR>
	 * 			item name <BR>
	 * 			case/piece flag *<BR>
	 * 			packed qty per case *2<BR>
	 * 			storage case qty *2 *4<BR>
	 * 			Case ITF <BR>
	 * 			packed qty per piece <BR>
	 *			storage piece qty *3 *4<BR>
	 *			bundle itf <BR>
	 *			expiry date <BR>
	 *			list print flag<BR>
	 *		<BR>
	 * 			*2 <BR>
	 * 			mandatory input if case/piece flag is 1:case<BR>
	 * 			*3 <BR>
	 * 			mandatory input if case/piece flag is 2:piece<BR>
	 * 			*4 <BR>
	 * 			If case/piece type is 0:none<BR>
	 *			Either storage case qty or storage piece qty is mandatory <BR>
	 * 			case qty x packed qty per case + piece qty is not 0(>0) <BR>
	 *	 	</DIR>
	 *   </DIR>
	 *   <BR>
	 *   4.Add it to the filtering area if the result of the schedule is true.<BR>
	 *     Update our data for the correction in information on input area when you press the input button after the correction button is pressed. <BR>
	 *   5.If the result of the schedule is true, set default value (-1) for preset area row no. in viewstate<BR>
	 *   6.If the result of the schedule is true, Make the start storage button and the clear list button enabled. <BR>
	 *   7.Output the message acquired from the schedule to the screen. <BR>
	 * 	 8.save input area contents<BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		//#CM34974
		// set the cursor in consignor code
		setFocus(txt_ConsignorCode);

		//#CM34975
		// mandatory input check
		txt_ConsignorCode.validate();
		txt_ItemCode.validate();
		
		//#CM34976
		// pattern matching character
		txt_ConsignorName.validate(false);
		txt_ItemName.validate(false);
		txt_CaseEntering.validate(false);
		txt_StrgCaseQty.validate(false);
		txt_CaseItf.validate(false);
		txt_PieceEntering.validate(false);
		txt_StrgPieceQty.validate(false);
		txt_BundleItf.validate(false);
		txt_UseByDate.validate(false);

		//#CM34977
		// Check the Minus value for the Storage qty of Packed qty per Case and Piece.
		String itemname = null;

		itemname = checkNumber(txt_CaseEntering, lbl_CaseEntering);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_PieceEntering, lbl_PieceEntering);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_StrgCaseQty, lbl_StorageCaseQty);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_StrgPieceQty, lbl_StoragePieceQty);

		if (!StringUtil.isBlank(itemname))
		{
			//#CM34978
			// Display the error message, and finish.
			//#CM34979
			// 6023057 = Input the value {0} more than {1}. 
			message.setMsgResourceKey("6023057" + wDelim + itemname + wDelim + "0");
			return;
		}

		//#CM34980
		// Input character check for eWareNavi
		if (!checkContainNgText())
		{
			return;
		}

		Connection conn = null;

		try
		{
			//#CM34981
			// set schedule parameter
			//#CM34982
			// input area
			AsScheduleParameter param = new AsScheduleParameter();

			//#CM34983
			// worker code
			param.setWorkerCode(this.getViewState().getString(AsAddStorage1Business.VS_WORKERCODE));
			//#CM34984
			// warehouse
			param.setWareHouseNo(this.getViewState().getString(AsAddStorage1Business.VS_WHNUMBER));
			//#CM34985
			// location no.
			param.setLocationNo(this.getViewState().getString(AsAddStorage1Business.VS_LOCATION));
			//#CM34986
			// sending station
			param.setFromStationNo(pul_Station.getSelectedValue());
			
			//#CM34987
			// input area info
			//#CM34988
			// terminal no.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());

			//#CM34989
			// consignor code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM34990
			// consignor name
			param.setConsignorName(txt_ConsignorName.getText());
			//#CM34991
			// item code
			param.setItemCode(txt_ItemCode.getText());
			//#CM34992
			// item name
			param.setItemName(txt_ItemName.getText());
			//#CM34993
			// Case piece flag
			param.setCasePieceFlag(getCasePieceFlagFromInputArea());
			//#CM34994
			// packed qty per case
			param.setEnteringQty(txt_CaseEntering.getInt());
			//#CM34995
			// storage case qty
			param.setStorageCaseQty(txt_StrgCaseQty.getInt());
			//#CM34996
			// Case ITF
			param.setITF(txt_CaseItf.getText());
			//#CM34997
			// packed qty per piece
			param.setBundleEnteringQty(txt_PieceEntering.getInt());
			//#CM34998
			// storage piece qty
			param.setStoragePieceQty(txt_StrgPieceQty.getInt());
			//#CM34999
			// bundle itf
			param.setBundleITF(txt_BundleItf.getText());
			//#CM35000
			// expiry date
			param.setUseByDate(txt_UseByDate.getText());
			//#CM35001
			// print storage list
			param.setListFlg(chk_CommonUse.getChecked());

			//#CM35002
			// set schedule parameter
			//#CM35003
			// preset area
			AsScheduleParameter[] listparam = null;

			//#CM35004
			//set null if there is no data in preset area
			if (lst_AddStorage.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				//#CM35005
				// set value if data exists
				listparam = setListParam(this.getViewState().getInt(LineNo));
			}

			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new AsAddStorageSCH();

			if (schedule.check(conn, param, listparam))
			{
				//#CM35006
				// If the result is true, the data is set in the filtering area. 
				if (this.getViewState().getInt(LineNo) == -1)
				{
					//#CM35007
					// Adds to the filter in case of a new input. 
					lst_AddStorage.addRow();
					lst_AddStorage.setCurrentRow(lst_AddStorage.getMaxRows() - 1);
					setList();
				}
				else
				{
					//#CM35008
					// The Line No data for the correction is updated in case of the input data is under the correction. 
					lst_AddStorage.setCurrentRow(this.getViewState().getInt(LineNo));
					setList();
					//#CM35009
					// change color of selected row
					lst_AddStorage.resetHighlight();
				}

				//#CM35010
				// return the modify status to default
				this.getViewState().setInt(LineNo, -1);

				//#CM35011
				// enable button
				//#CM35012
				// Storage start button
				btn_StorageStart.setEnabled(true);
				//#CM35013
				// All cancel button
				btn_AllCancel.setEnabled(true);
			}

			//#CM35014
			// set message
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			//#CM35015
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
				//#CM35016
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

	//#CM35017
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Back_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35018
	/** 
	 * It is called when the Return button is pressed.<BR>
	 * <BR>
	 * overview :: Changes to setting (Location selection) screen of the stock outside AS/RS schedule.<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		//#CM35019
		// Input information input screen -> Location selection screen
		forward(AsAddStorage1Business.DO_ADDSTORAGE1);
	}

	//#CM35020
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_StorageStart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35021
	/** 
	 * It is called when the Start Storage button is pressed.<BR>
	 * <BR>
	 * overview :: Do the No Plan Storage with the data in filtering area.<BR>
	 * <BR>
	 * <DIR>
	 *	  1.set the cursor in consignor code<BR>
	 *    2.Display the dialog box, and confirm whether to Start Storage. <BR>
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
	 * 						ViewState.worker code<BR>
	 * 						ViewState.area no.<BR>
	 * 						ViewState.warehouse<BR>
	 * 						ViewState.location no.<BR>
	 * 						ViewState.sending station<BR>
	 * 						pulldown.receiving station<BR>
	 * 						list print flag<BR>
	 * 						preset area.case piece flag(Concealment item)<BR>
	 * 						preset area.consignor code<BR>
	 * 						preset area.consignor name<BR>
	 * 						preset area.item code<BR>
	 * 						preset area.item name<BR>
	 * 						preset area.storage case qty<BR>
	 * 						preset area.storage piece qty<BR>
	 * 						preset area.packed qty per case<BR>
	 * 						preset area.packed qty per piece<BR>
	 * 						preset area.case piece flag<BR>
	 * 						preset area.Case ITF<BR>
	 * 						preset area.bundle itf<BR>
	 * 						preset area.expiry date<BR>
	 * 						preset area line no.<BR>
	 * 						terminal no.<BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.Clear the Input area and filtered information when the result of the schedule is true. <BR>
	 *				3.Release correction status. (Set default:-1 to line No. for ViewState. )<BR>
	 *    			If false,Output the message acquired from the schedule to the screen. <BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_StorageStart_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM35022
			// set the cursor in consignor code
			setFocus(txt_ConsignorCode);

			//#CM35023
			// set schedule parameter
			AsScheduleParameter[] param  = null ;
			//#CM35024
			// set all the data to preset area
			param = setListParam(-1);
			
			//#CM35025
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new AsAddStorageSCH();

			//#CM35026
			// start schedule
			if (schedule.startSCH(conn, param))
			{
				//#CM35027
				// commit if the result is true
				conn.commit();
				//#CM35028
				// set message
				message.setMsgResourceKey(schedule.getMessage());

				//#CM35029
				// while sending picking send instruction, use RMI message for picking request
				SendRequestor req = new SendRequestor();
				req.retrieval();

				//#CM35030
				// clear list
				lst_AddStorage.clearRow();

				//#CM35031
				// return the modify status to default
				this.getViewState().setInt(LineNo, -1);

				//#CM35032
				// disable button click
				//#CM35033
				// Storage start button
				btn_StorageStart.setEnabled(false);
				//#CM35034
				// All cancel button
				btn_AllCancel.setEnabled(false);
			}
			else
			{
				conn.rollback();
				//#CM35035
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			//#CM35036
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
				//#CM35037
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

	//#CM35038
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AddStorage_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM35039
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AddStorage_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM35040
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AddStorage_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM35041
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AddStorage_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM35042
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AddStorage_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35043
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AddStorage_Change(ActionEvent e) throws Exception
	{
	}

	//#CM35044
	/** 
	 * It is called when the cancellation and the correction button are pressed.<BR>
	 * <BR>
	 * Cancel buttonoverview :: Clear the corresponding filtered data.<BR>
	 * <BR>
	 * <DIR>
	 *    1.Display the dialog box, and confirm whether to straighten and to clear information. <BR>
	 *    <DIR>
	 * 		[confirmation dialog CANCEL]<BR>
	 *			<DIR>
	 *				do nothing
	 *			</DIR>
	 * 		[confirmation dialog OK]<BR>
	 *			<DIR>
	 *				1.input area,Clear the corresponding filtered data.<BR>
	 * 				2.set default value (-1) for preset area row no. in viewstate<BR>
	 *              3.Invalidate the Start Storage button and the Clear list button when it filters and information does not exist. <BR>
	 *				4.set the cursor in consignor code<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * Modify button overview :: Make the pertinent data modification status.<BR>
	 * <BR>
	 * <DIR>
	 *    1.Display selection information in the upper input area.<BR>
	 *    2.Make the selection information part thin yellow. <BR>
	 *    3.Set the line to line No. for ViewState now. 
	 *    4.set the cursor in consignor code<BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void lst_AddStorage_Click(ActionEvent e) throws Exception
	{
		//#CM35045
		// if cancel button is clicked
		if (lst_AddStorage.getActiveCol() == 1)
		{
			//#CM35046
			// delete list contents
			lst_AddStorage.removeRow(lst_AddStorage.getActiveRow());

			//#CM35047
			// Invalidate the Start Storage button and the Clear list button when the filtered information does not exist.
			//#CM35048
			//set null if there is no data in preset area
			if (lst_AddStorage.getMaxRows() == 1)
			{
				//#CM35049
				// disable button click
				//#CM35050
				// Storage start button
				btn_StorageStart.setEnabled(false);
				//#CM35051
				// All cancel button
				btn_AllCancel.setEnabled(false);
			}

			//#CM35052
			// return the modify status to default
			this.getViewState().setInt(LineNo, -1);

			//#CM35053
			// change color of selected row
			lst_AddStorage.resetHighlight();

			//#CM35054
			// set the cursor in consignor code
			setFocus(txt_ConsignorCode);

		}
		//#CM35055
		// if modify button is clicked  (modify movement)
		else if (lst_AddStorage.getActiveCol() == 2)
		{
			//#CM35056
			// set the current row
			lst_AddStorage.setCurrentRow(lst_AddStorage.getActiveRow());
			//#CM35057
			// consignor code
			txt_ConsignorCode.setText(lst_AddStorage.getValue(3));
			//#CM35058
			// item code
			txt_ItemCode.setText(lst_AddStorage.getValue(4));
			//#CM35059
			// storage case qty
			txt_StrgCaseQty.setText(lst_AddStorage.getValue(5));
			//#CM35060
			// packed qty per case
			txt_CaseEntering.setText(lst_AddStorage.getValue(6));
			//#CM35061
			// case piece flag
			setCasePieceRBFromList();
			//#CM35062
			// Case ITF
			txt_CaseItf.setText(lst_AddStorage.getValue(8));
			//#CM35063
			// expiry date
			txt_UseByDate.setText(lst_AddStorage.getValue(9));
			//#CM35064
			// consignor name
			txt_ConsignorName.setText(lst_AddStorage.getValue(10));
			//#CM35065
			// item name
			txt_ItemName.setText(lst_AddStorage.getValue(11));
			//#CM35066
			// storage piece qty
			txt_StrgPieceQty.setText(lst_AddStorage.getValue(12));
			//#CM35067
			// packed qty per piece
			txt_PieceEntering.setText(lst_AddStorage.getValue(13));
			//#CM35068
			// bundle itf
			txt_BundleItf.setText(lst_AddStorage.getValue(14));

			//#CM35069
			// save in viewstate
			//#CM35070
			// To judge whether it is update status by the Modify button pressing, line No is set in ViewState. 
			this.getViewState().setInt(LineNo, lst_AddStorage.getActiveRow());

			//#CM35071
			//change color of row under editing
			lst_AddStorage.setHighlight(lst_AddStorage.getActiveRow());

			//#CM35072
			// set the cursor in consignor code
			setFocus(txt_ConsignorCode);
		}
	}

	//#CM35073
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35074
	/**
	 * called when clear button is clicked<BR>
	 * <BR>
	 * Abstract :clear the input area<BR>
	 * <BR>
	 * <DIR>
	 * 		1.return input area items to initial state<BR>
	 * 		2.set the cursor in consignor code<BR>
	 * 		<DIR>
	 *   	item name[initial value]<BR>
	 *   		<DIR>
	 * 				consignor code[if search results a single consignor, display it] <BR>
	 * 				consignor name[Consignor search result[0].consignor name] <BR>
	 * 				item code[nil] <BR>
	 * 				item name[nil] <BR>
	 * 				case piece flag[Case]<BR>
	 * 				packed qty per case[nil] <BR>
	 * 				packed qty per piece[nil] <BR>
	 * 				storage case qty[nil] <BR>
	 * 				storage piece qty[nil] <BR>
	 * 				Case ITF[nil]<BR>
	 * 				bundle itf[nil]<BR>
	 * 				expiry date[nil] <BR>
	 * 	 		</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		inputDataClear();

		//#CM35075
		// set the cursor in consignor code
		setFocus(txt_ConsignorCode);
	}

	//#CM35076
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCancel_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35077
	/**
	 * called when the list clear button is pressed<BR>
	 * <BR>
	 * Abstract :clear preset area contents<BR>
	 * <BR>
	 * <DIR>
	 * 		1.clear the preset area<BR>
	 * 		2.disable all the preset area buttons<BR>
	 * 		3.set default value (-1) for preset area row no. in viewstate<BR>
	 * 		4.set the cursor in consignor code
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_AllCancel_Click(ActionEvent e) throws Exception
	{
		//#CM35078
		// eliminate all lines
		lst_AddStorage.clearRow();

		//#CM35079
		// disable button click
		//#CM35080
		// Storage start button
		btn_StorageStart.setEnabled(false);
		//#CM35081
		// All cancel button
		btn_AllCancel.setEnabled(false);

		//#CM35082
		// return the modify status to default
		this.getViewState().setInt(LineNo, -1);

		//#CM35083
		// set the cursor in consignor code
		setFocus(txt_ConsignorCode);
	}

	//#CM35084
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35085
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM35086
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM35087
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35088
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgCaseQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM35089
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgCaseQty_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM35090
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35091
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Change(ActionEvent e) throws Exception
	{
	}

	//#CM35092
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM35093
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Unschstwoli_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35094
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35095
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Change(ActionEvent e) throws Exception
	{
	}


}
//#CM35096
//end of class
