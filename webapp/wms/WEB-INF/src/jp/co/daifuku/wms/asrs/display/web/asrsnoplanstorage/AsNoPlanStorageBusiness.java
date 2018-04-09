// $Id: AsNoPlanStorageBusiness.java,v 1.2 2006/10/26 04:45:07 suresh Exp $

//#CM36193
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsnoplanstorage;
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
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.schedule.AsNoPlanStorageSCH;
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

//#CM36194
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * AS/RS No Plan Storage Submit screen class.<BR>
 * Hand over the parameter to the schedule which does AS/RS no plan Storage. <BR>
 * and transaction commit and rollback are done in this screen<BR>
 * <BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.Input button click process(<CODE>btn_Input_Click</CODE>method )<BR>
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
 *   		worker code *<BR>
 *   		password *<BR>
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
 * 		The content of the filtering area is set in the parameter, and the AS/RS No plan Storage based on the parameter. <BR>
 * 		Receive true when the result is received from the schedule, and processing is completed normally. 
 * 		Receive false when it does not complete the schedule because of the condition error etc.<BR>
 * 		output the schedule result, schedule message to screen<BR>
 * 		<BR>
 * 		[parameter]<BR>
 * 		<BR>
 * 		<DIR>
 * 			worker code<BR>
 * 			password<BR>
 * 			warehouse<BR>
 * 			zone<BR>
 * 			sending station<BR>
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
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:45:07 $
 * @author  $Author: suresh $
 */
public class AsNoPlanStorageBusiness extends AsNoPlanStorage implements WMSConstants
{
	//#CM36195
	// Class fields --------------------------------------------------

	//#CM36196
	/**
	 * PulldownStorage
	 */
	private static final String STATION_STORAGE = "10";

	//#CM36197
	// Class variables -----------------------------------------------

	//#CM36198
	// Class method --------------------------------------------------

	//#CM36199
	// Constructors --------------------------------------------------

	//#CM36200
	// Public methods ------------------------------------------------

	//#CM36201
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
	 * 			consignor code[if search results a single consignor, display it] <BR>
	 * 			consignor name[Consignor search result[0].consignor name]<BR>
	 * 			item code[nil] <BR>
	 * 			item name[nil] <BR>
	 * 			case piece flag[Case] <BR>
	 * 			packed qty per case[nil] <BR>
	 * 			packed qty per piece[nil] <BR>
	 * 			storage case qty[nil] <BR>
	 * 			storage piece qty[nil] <BR>
	 * 			Case ITF[nil]<BR>
	 * 			bundle itf[nil]<BR>
	 * 			expiry date[nil] <BR>
	 * 			print unplanned storage working list[true]<BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM36202
			// To judge whether it is update status by the Modify button pressing, line No is set in ViewState. 
			//#CM36203
			// (default:-1)
			this.getViewState().setInt("LineNo", -1);

			//#CM36204
			// disable button click
			//#CM36205
			// Picking start button
			btn_StorageStart.setEnabled(false);
			//#CM36206
			// list clear button
			btn_AllCancel.setEnabled(false);
			//#CM36207
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM36208
			// fetch Http Locale
			Locale locale = this.getHttpRequest().getLocale();

			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			//#CM36209
			// pull down display data(storage area)
			String[] areaid = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);
			//#CM36210
			// pull down display data(workplace)
			String[] sagyoba = pull.getSagyobaPulldownData(conn, STATION_STORAGE, true, "");
			//#CM36211
			// pull down display data(station)
			String[] stno = pull.getStationPulldownData(conn, STATION_STORAGE, true, "");
			//#CM36212
			// pull down display data(hard zone)
			String[] zoneno = pull.getHardZonePulldownData(conn, "");

			//#CM36213
			// set pulldown data
			PulldownHelper.setPullDown(pul_WareHouse, areaid);
			PulldownHelper.setLinkedPullDown(pul_WorkPlace, sagyoba);
			PulldownHelper.setLinkedPullDown(pul_Station, pul_WorkPlace, stno);
			PulldownHelper.setLinkedPullDown(pul_Zone, zoneno);

			//#CM36214
			// add child pull down
			pul_WareHouse.addChild(pul_WorkPlace);
			pul_WorkPlace.addChild(pul_Station);
			pul_WareHouse.addChild(pul_Zone);

			//#CM36215
			// initial display
			setFirstDisp();
		}
		catch (Exception ex)
		{
			//#CM36216
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
				//#CM36217
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

	//#CM36218
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
			//#CM36219
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			
			//#CM36220
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			
			//#CM36221
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}
		//#CM36222
		// Confirmation dialog when Start Storage button is pressed "Want to set?"
		btn_StorageStart.setBeforeConfirm("MSG-9000");
		//#CM36223
		// When the Clear list button is pressed, list input information confirmation dialog "Is it OK to clear the list?"
		btn_AllCancel.setBeforeConfirm("MSG-W0012");
	}

	//#CM36224
	/**
	 * This method is called when returning from popup window<BR>
	 * override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE><BR>
	 * <BR>
	 * overview ::fetch the value from search screen and set it<BR>
	 * <BR>
	 * <DIR>
	 *      1.fetch the value set from popup search screen<BR>
	 *      2.set value if it is not null<BR>
	 *      3.set cursor in worker code<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		
		//#CM36225
		// fetch parameter selected from listbox
		//#CM36226
		// consignor code
		String consignorcode = param.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM36227
		// consignor name
		String consignorname = param.getParameter(ListAsConsignorBusiness.CONSIGNORNAME_KEY);
		//#CM36228
		// item code
		String itemcode = param.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
		//#CM36229
		// item name
		String itemname = param.getParameter(ListAsItemBusiness.ITEMNAME_KEY);

		//#CM36230
		// set the value if not empty
		//#CM36231
		// consignor code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM36232
		// consignor name
		if (!StringUtil.isBlank(consignorname))
		{
			txt_ConsignorName.setText(consignorname);
		}
		//#CM36233
		// item code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM36234
		// item name
		if (!StringUtil.isBlank(itemname))
		{
			txt_ItemName.setText(itemname);
		}

		//#CM36235
		// set the cursor in worker code input
		setFocus(txt_WorkerCode);
	}

	//#CM36236
	// Package methods -----------------------------------------------

	//#CM36237
	// Protected methods ---------------------------------------------
	//#CM36238
	/**
	 * input check<BR>
	 * <BR>
	 * Abstract :Set the message, and return false when abnormality is found. <BR>
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

	//#CM36239
	// Private methods -----------------------------------------------

	//#CM36240
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
	 * 				consignor code[if search results a single consignor, display it] <BR>
	 * 				consignor name[Consignor search result[0].consignor name]<BR>
	 * 				item code[nil] <BR>
	 * 				item name[nil] <BR>
	 * 				case piece flag[Case] <BR>
	 * 				packed qty per case[nil] <BR>
	 * 				packed qty per piece[nil] <BR>
	 * 				storage case qty[nil] <BR>
	 * 				storage piece qty[nil] <BR>
	 *				Case ITF[nil]<BR>
	 * 				bundle itf[nil]<BR>
	 * 				expiry date[nil] <BR>
	 * 				print unplanned storage working list[true]<BR>
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
			//#CM36241
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new AsNoPlanStorageSCH();

			AsScheduleParameter param = (AsScheduleParameter) schedule.initFind(conn, null);

			//#CM36242
			// Set Case/Piece flag as Case.
			rdo_Cpf_Case.setChecked(true);
			
			//#CM36243
			// display customer code in the init screen, when there is single record
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

			//#CM36244
			// item code
			txt_ItemCode.setText("");
			//#CM36245
			// item name
			txt_ItemName.setText("");
			//#CM36246
			// case piece flag(Case)
			rdo_Cpf_Case.setChecked(true);
			//#CM36247
			// case piece flag(Piece)
			rdo_Cpf_Piece.setChecked(false);
			//#CM36248
			// case piece flag(None)
			rdo_Cpf_AppointOff.setChecked(false);
			//#CM36249
			// packed qty per case
			txt_CaseEntering.setText("");
			//#CM36250
			// storage case qty
			txt_StrgCaseQty.setText("");
			//#CM36251
			// Case ITF
			txt_CaseItf.setText("");
			//#CM36252
			// packed qty per piece
			txt_PieceEntering.setText("");
			//#CM36253
			// storage piece qty
			txt_StrgPieceQty.setText("");
			//#CM36254
			// bundle itf
			txt_BundleItf.setText("");
			//#CM36255
			// expiry date
			txt_UseByDate.setText("");
			//#CM36256
			// check [Print storage work list] check box
			chk_CommonUse.setChecked(true);
			//#CM36257
			// set the cursor in worker code input
			setFocus(txt_WorkerCode);
		}
		catch (Exception ex)
		{
			//#CM36258
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
				//#CM36259
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

	//#CM36260
	/** 
	 * The method of checking the qty. <BR>
	 * <BR>
	 * overview ::Check if the values of the entering qty and the storage qty are 0 or more. <BR>
	 * <BR>
	 * @param figure     Use the digit to check the value. 
	 * @param name       Item name which checks the value. 
	 * @return Return null when item name is returned when the use digit or the position is not one or more, and it is not so. 
	 * @throws Exception report all the exceptions
	 */
	private String checkNumber(NumberTextBox figure, Label name) throws Exception
	{
		String itemName = null;

		if (!StringUtil.isBlank(Integer.toString(figure.getInt())))
		{
			if (figure.getInt() < 0)
			{
				//#CM36261
				// The item name is set in itemName. 
				itemName = DisplayText.getText(name.getResourceKey());
				return itemName;
			}
		}
		return itemName;
	}

	//#CM36262
	/**
	 * this method sets preset area data to Parameter class<BR>
	 * <BR>
	 * overview ::Set the data of the area in the Parameter class. <BR>
	 * <BR>
	 * 1.Set data in the Parameter class for all if you press the Start Storage button. (Line No of filtered correction objects = -1)<BR>
	 * 2.Set data in the Parameter class because the line for the correction was excluded in case of the input data under the correction. <BR>
	 * <DIR>
	 *   	[parameter] *mandatory input<BR>
	 *   	<DIR>
	 * 			worker code<BR>
	 * 			password<BR>
	 * 			warehouse<BR>
	 * 			zone<BR>
	 * 			sending station<BR>
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

		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

		for (int i = 1; i < lst_NoPlanStorage.getMaxRows(); i++)
		{
			//#CM36263
			// Exclude the line for the correction. 
			if (i == lineno)
			{
				continue;
			}

			//#CM36264
			// specify line
			lst_NoPlanStorage.setCurrentRow(i);

			//#CM36265
			// set schedule parameter
			AsScheduleParameter param = new AsScheduleParameter();

			//#CM36266
			// warehouse
			param.setWareHouseNo(pul_WareHouse.getSelectedValue());
			//#CM36267
			// zone
			param.setHardZone(pul_Zone.getSelectedValue());
			//#CM36268
			// sending station
			param.setFromStationNo(pul_Station.getSelectedValue());
			if( pul_Station.getSelectedValue().equals(Station.AUTO_SELECT_STATIONNO))
			{
				//#CM36269
				// Set workplace when [Automatic division] is specified for station. 
				param.setFromStationNo(pul_WorkPlace.getSelectedValue());
			}

			//#CM36270
			// input area info
			//#CM36271
			// worker code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM36272
			// password
			param.setPassword(txt_Password.getText());
			//#CM36273
			// terminal no.
			param.setTerminalNumber(userHandler.getTerminalNo());
			
			//#CM36274
			// list print flag
			param.setListFlg(chk_CommonUse.getChecked());
			//#CM36275
			// Case/Piece flag(Concealment item)
			param.setCasePieceFlag(lst_NoPlanStorage.getValue(0));
			//#CM36276
			// consignor code
			param.setConsignorCode(lst_NoPlanStorage.getValue(3));
			//#CM36277
			// item code
			param.setItemCode(lst_NoPlanStorage.getValue(4));
			//#CM36278
			// storage case qty
			param.setStorageCaseQty(Formatter.getInt(lst_NoPlanStorage.getValue(5)));			
			//#CM36279
			// packed qty per case
			param.setEnteringQty(Formatter.getInt(lst_NoPlanStorage.getValue(6)));
			//#CM36280
			// case piece flag
			param.setCasePieceFlagNameDisp(lst_NoPlanStorage.getValue(7));
			//#CM36281
			// Case ITF
			param.setITF(lst_NoPlanStorage.getValue(8));
			//#CM36282
			// expiry date
			param.setUseByDate(lst_NoPlanStorage.getValue(9));
			//#CM36283
			// consignor name
			param.setConsignorName(lst_NoPlanStorage.getValue(10));
			//#CM36284
			// item name
			param.setItemName(lst_NoPlanStorage.getValue(11));
			//#CM36285
			// storage piece qty
			param.setStoragePieceQty(Formatter.getInt(lst_NoPlanStorage.getValue(12)));
			//#CM36286
			// packed qty per piece
			param.setBundleEnteringQty(Formatter.getInt(lst_NoPlanStorage.getValue(13)));
			//#CM36287
			// bundle itf
			param.setBundleITF(lst_NoPlanStorage.getValue(14));

			//#CM36288
			// save the line no.
			param.setRowNo(i);

			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			//#CM36289
			// set preset area data if value is not null
			AsScheduleParameter[] listparam = new AsScheduleParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			//#CM36290
			// if there is no preset area data, set null
			return null;
		}
	}

	//#CM36291
	/**
	 * AS/RS parameter which corresponds from input area.Case/Piece flag radiobutton. Acquire Case/piece division. <BR>
	 * <BR>
	 * @return case piece flag
	 * @throws Exception report all the exceptions 
	 */
	private String getCasePieceFlagFromInputArea() throws Exception
	{
		//#CM36292
		// case piece flag
		if (rdo_Cpf_AppointOff.getChecked())
		{
			//#CM36293
			// None
			return AsScheduleParameter.CASEPIECE_FLAG_NOTHING;
		}
		else if (rdo_Cpf_Case.getChecked())
		{
			//#CM36294
			// Case
			return AsScheduleParameter.CASEPIECE_FLAG_CASE;
		}
		else if (rdo_Cpf_Piece.getChecked())
		{
			//#CM36295
			// Piece
			return AsScheduleParameter.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}

	//#CM36296
	/**
	 * Acquire the corresponding case/piece flag from AS/RS parameter Case/Piece flag. <BR>
	 * <BR>
	 * @param value AS/RS parameter corresponding to radiobutton. case piece flag
	 * @return case piece flag
	 * @throws Exception report all the exceptions  
	 */
	private String getCasePieceFlag(String value) throws Exception
	{
		//#CM36297
		// case piece flag
		if (value.equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM36298
			// None
			return SystemDefine.CASEPIECE_FLAG_NOTHING;
		}
		else if (value.equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
		{
			//#CM36299
			// Case
			return SystemDefine.CASEPIECE_FLAG_CASE;
		}
		else if (value.equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM36300
			// Piece
			return SystemDefine.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}

	//#CM36301
	/**
	 * Display the content of list cell flag to case/piece flag check box of input area. <BR>
	 * <BR>
	 * @throws Exception report all the exceptions 
	 */
	private void setCasePieceRBFromList() throws Exception
	{
		if (lst_NoPlanStorage.getValue(0).equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM36302
			// check none
			rdo_Cpf_AppointOff.setChecked(true);
			rdo_Cpf_Case.setChecked(false);
			rdo_Cpf_Piece.setChecked(false);
		}
		else if (lst_NoPlanStorage.getValue(0).equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
		{
			//#CM36303
			// check case
			rdo_Cpf_Case.setChecked(true);
			rdo_Cpf_Piece.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);
		}
		else if (lst_NoPlanStorage.getValue(0).equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM36304
			// check piece
			rdo_Cpf_Piece.setChecked(true);
			rdo_Cpf_Case.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);
		}
	}

	//#CM36305
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
		//#CM36306
		//add data to tool tip
		ToolTipHelper toolTip = new ToolTipHelper();
		//#CM36307
		// consignor name
		toolTip.add(DisplayText.getText("LBL-W0026"), txt_ConsignorName.getText());
		//#CM36308
		// item name
		toolTip.add(DisplayText.getText("LBL-W0103"), txt_ItemName.getText());
		//#CM36309
		// expiry date
		toolTip.add(DisplayText.getText("LBL-W0270"), txt_UseByDate.getText());

		//#CM36310
		//Set tool tip in the current line. 
		lst_NoPlanStorage.setToolTip(lst_NoPlanStorage.getCurrentRow(), toolTip.getText());

		//#CM36311
		// Case/Piece flag(Concealment item)
		lst_NoPlanStorage.setValue(0, getCasePieceFlagFromInputArea());
		//#CM36312
		// consignor code
		lst_NoPlanStorage.setValue(3, txt_ConsignorCode.getText());
		//#CM36313
		// item code
		lst_NoPlanStorage.setValue(4, txt_ItemCode.getText());
		//#CM36314
		// storage case qty
		if (StringUtil.isBlank(txt_StrgCaseQty.getText()))
		{
			lst_NoPlanStorage.setValue(5, "0");
		}
		else
		{
			lst_NoPlanStorage.setValue(5, WmsFormatter.getNumFormat(txt_StrgCaseQty.getInt()));
		}
		//#CM36315
		// packed qty per case
		if (StringUtil.isBlank(txt_CaseEntering.getText()))
		{
			lst_NoPlanStorage.setValue(6, "0");
		}
		else
		{
			lst_NoPlanStorage.setValue(6, WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));

		}
		//#CM36316
		// classification
		lst_NoPlanStorage.setValue(7, DisplayUtil.getPieceCaseValue(getCasePieceFlag(getCasePieceFlagFromInputArea())));
		//#CM36317
		// Case ITF
		lst_NoPlanStorage.setValue(8, txt_CaseItf.getText());
		//#CM36318
		// expiry date
		lst_NoPlanStorage.setValue(9, txt_UseByDate.getText());
		//#CM36319
		// consignor name
		lst_NoPlanStorage.setValue(10, txt_ConsignorName.getText());
		//#CM36320
		// item name
		lst_NoPlanStorage.setValue(11, txt_ItemName.getText());
		//#CM36321
		// storage piece qty
		if (StringUtil.isBlank(txt_StrgPieceQty.getText()))
		{
			lst_NoPlanStorage.setValue(12, "0");
		}
		else
		{
			lst_NoPlanStorage.setValue(12, WmsFormatter.getNumFormat(txt_StrgPieceQty.getInt()));
		}

		//#CM36322
		// packed qty per piece
		if (StringUtil.isBlank(txt_PieceEntering.getText()))
		{
			lst_NoPlanStorage.setValue(13, "0");
		}
		else
		{
			lst_NoPlanStorage.setValue(13, WmsFormatter.getNumFormat(txt_PieceEntering.getInt()));
		}
		//#CM36323
		// bundle itf
		lst_NoPlanStorage.setValue(14, txt_BundleItf.getText());
	}


	//#CM36324
	// Event handler methods -----------------------------------------
	//#CM36325
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

	//#CM36326
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
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM36327
		// set search conditions to consignor search screen
		ForwardParameters param = new ForwardParameters();
		//#CM36328
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM36329
		// search flag
		param.setParameter(ListAsConsignorBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);
		//#CM36330
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsconsignor/ListAsConsignor.do", param, "/progress.do");
	}

	//#CM36331
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
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM36332
		// Set the search conditions to item search screen
		ForwardParameters param = new ForwardParameters();
		//#CM36333
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM36334
		// item code
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM36335
		// search flag
		param.setParameter(ListAsItemBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);
		//#CM36336
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsitem/ListAsItem.do", param, "/progress.do");
	}

	//#CM36337
	/**
	 * it is called when the input button is pressed.<BR>
	 * <BR>
	 * Abstract :Check the input item of input area, and display it after filtering. <BR>
	 * <BR>
	 * <DIR>
	 *   1.set cursor in worker code<BR>
	 *   2.Check the input area input items(mandatory input, character count, character attribute)<BR>
	 *   3.start scheduler<BR>
	 * 	 <DIR>
	 *   	[parameter] *mandatory input<BR>
	 *   	<DIR>
	 * 			worker code * <BR>
	 * 			password * <BR>
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
	 *   5.If the result of the schedule is true,set default value (-1) for preset area row no. in viewstate<BR>
	 *   6.If the result of the schedule is true,Make the start storage button and the clear list button enabled. <BR>
	 *   7.Output the message acquired from the schedule to the screen. <BR>
	 * 	 8.save input area contents<BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		//#CM36338
		// set the cursor in worker code input
		setFocus(txt_WorkerCode);

		//#CM36339
		// mandatory input check
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		txt_ItemCode.validate();
		
		//#CM36340
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

		String itemname = null;

		itemname = checkNumber(txt_CaseEntering, lbl_CaseEntering);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_PieceEntering, lbl_BundleEntering);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_StrgCaseQty, lbl_StorageCaseQty);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_StrgPieceQty, lbl_StoragePieceQty);

		if (!StringUtil.isBlank(itemname))
		{
			//#CM36341
			// Display the error message, and finish.
			//#CM36342
			// 6023057 = Input the value {0} more than {1}. 
			message.setMsgResourceKey("6023057" + wDelim + itemname + wDelim + "0");
			return;
		}

		//#CM36343
		// Input character check for eWareNavi
		if (!checkContainNgText())
		{
			return;
		}

		Connection conn = null;

		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM36344
			// set schedule parameter
			//#CM36345
			// input area
			AsScheduleParameter param = new AsScheduleParameter();
			//#CM36346
			// warehouse station no.
			param.setWareHouseNo(pul_WareHouse.getSelectedValue());
			//#CM36347
			// worker code 
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM36348
			// password
			param.setPassword(txt_Password.getText());
			//#CM36349
			// consignor code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM36350
			// consignor name
			param.setConsignorName(txt_ConsignorName.getText());
			//#CM36351
			// item code
			param.setItemCode(txt_ItemCode.getText());
			//#CM36352
			// item name
			param.setItemName(txt_ItemName.getText());
			//#CM36353
			// Case piece flag
			param.setCasePieceFlag(getCasePieceFlagFromInputArea());
			//#CM36354
			// packed qty per case
			param.setEnteringQty(txt_CaseEntering.getInt());
			//#CM36355
			// storage case qty
			param.setStorageCaseQty(txt_StrgCaseQty.getInt());
			//#CM36356
			// Case ITF
			param.setITF(txt_CaseItf.getText());
			//#CM36357
			// packed qty per piece
			param.setBundleEnteringQty(txt_PieceEntering.getInt());
			//#CM36358
			// storage piece qty
			param.setStoragePieceQty(txt_StrgPieceQty.getInt());
			//#CM36359
			// bundle itf
			param.setBundleITF(txt_BundleItf.getText());
			//#CM36360
			// expiry date
			param.setUseByDate(txt_UseByDate.getText());
			//#CM36361
			// print unplanned storage list
			param.setListFlg(chk_CommonUse.getChecked());

			//#CM36362
			// set schedule parameter
			//#CM36363
			// preset area
			AsScheduleParameter[] listparam = null;

			//#CM36364
			//set null if there is no data in preset area
			if (lst_NoPlanStorage.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				//#CM36365
				// set value if data exists
				listparam = setListParam(this.getViewState().getInt("LineNo"));
			}

			WmsScheduler schedule = new AsNoPlanStorageSCH();

			if (schedule.check(conn, param, listparam))
			{
				//#CM36366
				// If the result is true, data is set in preset area. 
				if (this.getViewState().getInt("LineNo") == -1)
				{
					//#CM36367
					// Adds to the filter in case of a new input. 
					lst_NoPlanStorage.addRow();
					lst_NoPlanStorage.setCurrentRow(lst_NoPlanStorage.getMaxRows() - 1);
					setList();
				}
				else
				{
					//#CM36368
					// The Line No data for the correction is updated in case of the input data is under the correction. 
					lst_NoPlanStorage.setCurrentRow(this.getViewState().getInt("LineNo"));
					setList();
					//#CM36369
					// change color of selected row
					lst_NoPlanStorage.resetHighlight();
				}

				//#CM36370
				// return the modify status to default
				this.getViewState().setInt("LineNo", -1);

				//#CM36371
				// enable button
				//#CM36372
				// Storage start button
				btn_StorageStart.setEnabled(true);
				//#CM36373
				// list clear button
				btn_AllCancel.setEnabled(true);
			}

			//#CM36374
			// set message
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			//#CM36375
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
				//#CM36376
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

	//#CM36377
	/**
	 * called when clear button is clicked<BR>
	 * <BR>
	 * Abstract :clear the input area<BR>
	 * <BR>
	 * <DIR>
	 * 		1.return input area items to initial state<BR>
	 * 		2.set cursor in worker code<BR>
	 * 		<DIR>
	 *   	item name[initial value]<BR>
	 *   		<DIR>
	 * 				worker code[as it is]<BR>
	 * 				password[as it is]<BR>
	 * 				consignor code[if search results a single consignor, display it] <BR>
	 * 				consignor name[Consignor search result[0].consignor name]<BR>
	 * 				item code[nil] <BR>
	 * 				item name[nil] <BR>
	 * 				case piece flag[Case] <BR>
	 * 				packed qty per case[nil] <BR>
	 * 				packed qty per piece[nil] <BR>
	 * 				storage case qty[nil] <BR>
	 * 				storage piece qty[nil] <BR>
	 * 				Case ITF[nil]<BR>
	 * 				bundle itf[nil]<BR>
	 * 				expiry date[nil] <BR>
	 * 				print unplanned storage working list[true]<BR>
	 * 	 		</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM36378
		// Do the clear processing.
		setFirstDisp();
	}

	//#CM36379
	/** 
	 * It is called when the Start Storage button is pressed.<BR>
	 * <BR>
	 * overview ::Do the ASRS No Plan Storage by information on preset area. <BR>
	 * <BR>
	 * <DIR>
	 *	  1.set cursor in worker code<BR>
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
	 * 						worker code<BR>
	 * 						password<BR>
	 * 						warehouse<BR>
	 * 						zone<BR>
	 * 						sending station<BR>
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
	 *    			Output the message acquired from the schedule to the screen when false. <BR>
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
			//#CM36380
			// set the cursor in worker code input
			setFocus(txt_WorkerCode);

			//#CM36381
			// set schedule parameter
			AsScheduleParameter[] param  = null ;
			//#CM36382
			// set all the data to preset area
			param = setListParam(-1);
			
			//#CM36383
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new AsNoPlanStorageSCH();

			//#CM36384
			// start schedule
			if (schedule.startSCH(conn, param))
			{
				//#CM36385
				// commit if the result is true
				conn.commit();
				
				//#CM36386
				// start sending storage instruction
				SendRequestor req = new SendRequestor();
				req.storage();
				
				//#CM36387
				// set message
				message.setMsgResourceKey(schedule.getMessage());

				//#CM36388
				// clear list
				lst_NoPlanStorage.clearRow();

				//#CM36389
				// return the modify status to default
				this.getViewState().setInt("LineNo", -1);

				//#CM36390
				// disable button click
				//#CM36391
				// Storage start button
				btn_StorageStart.setEnabled(false);
				//#CM36392
				// list clear button
				btn_AllCancel.setEnabled(false);
			}
			else
			{
				conn.rollback();
				//#CM36393
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			//#CM36394
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
				//#CM36395
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

	//#CM36396
	/**
	 * It is called when the cancellation and the correction button are pressed.<BR>
	 * <BR>
	 * Cancel buttonoverview ::Clear the corresponding filtered data.<BR>
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
	 *				4.set cursor in worker code<BR>
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
	 *    4.set cursor in worker code<BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void lst_NoPlanStorage_Click(ActionEvent e) throws Exception
	{
		//#CM36397
		// if cancel button is clicked
		if (lst_NoPlanStorage.getActiveCol() == 1)
		{
			//#CM36398
			// delete list contents
			lst_NoPlanStorage.removeRow(lst_NoPlanStorage.getActiveRow());

			//#CM36399
			// Invalidate the Start Storage button and the Clear list button when the filtered information does not exist.
			//#CM36400
			//set null if there is no data in preset area
			if (lst_NoPlanStorage.getMaxRows() == 1)
			{
				//#CM36401
				// disable button click
				//#CM36402
				// Storage start button
				btn_StorageStart.setEnabled(false);
				//#CM36403
				// list clear button
				btn_AllCancel.setEnabled(false);
			}

			//#CM36404
			// return the modify status to default
			this.getViewState().setInt("LineNo", -1);

			//#CM36405
			// change color of selected row
			lst_NoPlanStorage.resetHighlight();

			//#CM36406
			// set the cursor in worker code input
			setFocus(txt_WorkerCode);

		}
		//#CM36407
		// if modify button is clicked (modify movement)
		else if (lst_NoPlanStorage.getActiveCol() == 2)
		{
			//#CM36408
			// set the current row
			lst_NoPlanStorage.setCurrentRow(lst_NoPlanStorage.getActiveRow());
			//#CM36409
			// consignor code
			txt_ConsignorCode.setText(lst_NoPlanStorage.getValue(3));
			//#CM36410
			// item code
			txt_ItemCode.setText(lst_NoPlanStorage.getValue(4));
			//#CM36411
			// storage case qty
			txt_StrgCaseQty.setText(lst_NoPlanStorage.getValue(5));
			//#CM36412
			// packed qty per case
			txt_CaseEntering.setText(lst_NoPlanStorage.getValue(6));
			//#CM36413
			// case piece flag
			setCasePieceRBFromList();
			//#CM36414
			// Case ITF
			txt_CaseItf.setText(lst_NoPlanStorage.getValue(8));
			//#CM36415
			// expiry date
			txt_UseByDate.setText(lst_NoPlanStorage.getValue(9));
			//#CM36416
			// consignor name
			txt_ConsignorName.setText(lst_NoPlanStorage.getValue(10));
			//#CM36417
			// item name
			txt_ItemName.setText(lst_NoPlanStorage.getValue(11));
			//#CM36418
			// storage piece qty
			txt_StrgPieceQty.setText(lst_NoPlanStorage.getValue(12));
			//#CM36419
			// packed qty per piece
			txt_PieceEntering.setText(lst_NoPlanStorage.getValue(13));
			//#CM36420
			// bundle itf
			txt_BundleItf.setText(lst_NoPlanStorage.getValue(14));

			//#CM36421
			// save in viewstate
			//#CM36422
			// To judge whether it is update status by the Modify button pressing, line No is set in ViewState. 
			this.getViewState().setInt("LineNo", lst_NoPlanStorage.getActiveRow());

			//#CM36423
			//change color of row under editing
			lst_NoPlanStorage.setHighlight(lst_NoPlanStorage.getActiveRow());

			//#CM36424
			// set the cursor in worker code input
			setFocus(txt_WorkerCode);
		}
	}
	//#CM36425
	/**
	 * called when the list clear button is pressed<BR>
	 * <BR>
	 * overview ::Clear all filtered display information. <BR>
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
	 *				1.Clear all filtered display information. <BR>
	 *				2.Invalidate the Start Storage button and the Clear list button. <BR>
	 *				3.Clear all items of input area.<BR>
	 * 				4.set default value (-1) for preset area row no. in viewstate<BR>
	 *				5.set cursor in worker code<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_AllCancel_Click(ActionEvent e) throws Exception
	{
		//#CM36426
		// eliminate all lines
		lst_NoPlanStorage.clearRow();

		//#CM36427
		// disable button click
		//#CM36428
		// Storage start button
		btn_StorageStart.setEnabled(false);
		//#CM36429
		// list clear button
		btn_AllCancel.setEnabled(false);

		//#CM36430
		// return the modify status to default
		this.getViewState().setInt("LineNo", -1);

		//#CM36431
		// set the cursor in worker code input
		setFocus(txt_WorkerCode);
	}


}
//#CM36432
//end of class
