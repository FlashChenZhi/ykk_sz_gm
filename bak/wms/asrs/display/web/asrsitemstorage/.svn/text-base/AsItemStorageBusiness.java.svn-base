// $Id: AsItemStorageBusiness.java,v 1.2 2006/10/26 04:15:53 suresh Exp $

//#CM35380
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsitemstorage;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
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
import jp.co.daifuku.common.DateOperator;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstorageplan.ListAsStoragePlanBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstorageplandate.ListAsStoragePlanDateBusiness;
import jp.co.daifuku.wms.asrs.schedule.AsItemStorageSCH;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM35381
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * The AS/RS stock setting screen class. <BR>
 * Hand over the parameter to the schedule which does the AS/RS stock setting. <BR>
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
 *   	<DIR>
 * 			worker code * <BR>
 * 			password * <BR>
 * 			consignor code *<BR>
 *          Storage Plan Date* <BR>
 * 			item code *<BR>
 * 			case/piece flag *<BR>
 * 			storage case qty *2 *4<BR>
 *			storage piece qty *3 *4<BR>
 *			expiry date <BR>
 *		<BR>
 * 			*2 <BR>
 * 			mandatory input if case/piece flag is 1:case<BR>
 * 			*3 <BR>
 * 			mandatory input if case/piece flag is 2:piece<BR>
 * 			*4 <BR>
 * 			If case/piece type is 0:none
 *			Either storage case qty or storage piece qty is mandatory <BR>
 * 			case qty x packed qty per case + piece qty is not 0(>0) <BR>
 *	 	</DIR>
 * </DIR>
 * <BR>
 * 2.Processing when Start Storage button is pressed(<CODE>btn_StorageStart_Click</CODE>method )<BR>
 * <BR>
 * <DIR>
 * 		The content of the filtering area is set in the parameter, and the schedule takes an inventory based on the parameter.<BR>
 * 		Receive true when the result is received from the schedule, and processing is completed normally. 
 * 		Receive false when it does not complete the schedule because of the condition error etc.<BR>
 * 		output the schedule result, schedule message to screen<BR>
 * 		<BR>
 * 		[parameter]<BR>
 * 		<BR>
 * 		<DIR>
 * 			pulldown.warehouse<BR>
 * 			pulldown.zone<BR>
 * 			pulldown.sending station<BR>
 * 			worker code<BR>
 * 			password<BR>
 * 			list print flag<BR>
 * 			preset area.case piece flag(Concealment item)<BR>
 * 			preset area.job no..(Concealment item)<BR>
 * 			preset area.update date/time<BR>
 * 			preset area.storage plan date<BR>
 * 			preset area.consignor code<BR>
 * 			preset area.consignor name<BR>
 * 			preset area.item code<BR>
 * 			preset area.item name<BR>
 * 			preset area.classification<BR>
 * 			preset area.storage case qty<BR>
 * 			preset area.storage piece qty<BR>
 * 			preset area.packed qty per case<BR>
 * 			preset area.packed qty per piece<BR>
 * 			preset area.expiry date<BR>
 * 			preset area.Case ITF<BR>
 * 			preset area.bundle itf<BR>
 * 			preset area line no.<BR>
 * 			terminal no.<BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/22</TD><TD>K.Toda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:15:53 $
 * @author  $Author: suresh $
 */
public class AsItemStorageBusiness extends AsItemStorage implements WMSConstants
{
	//#CM35382
	// Class fields --------------------------------------------------
	//#CM35383
	/**
	 * PulldownStorage
	 */
	private static final String STATION_STORAGE = "10";
	
	//#CM35384
	/**
	 * The dialog called from which control to be maintained : Print button
	 */
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";

	//#CM35385
	/**
	 * Key to maintain work No
	 */
	private final static String SELECT_JOBNO = "SELECT_JOBNO";

	//#CM35386
	/**
	 * Key to maintain last updated date and time
	 */
	private final static String SELECT_LASTUPDATE = "SELECT_LASTUPDATE";

	//#CM35387
	// Class variables -----------------------------------------------

	//#CM35388
	// Class method --------------------------------------------------

	//#CM35389
	// Constructors --------------------------------------------------

	//#CM35390
	// Public methods ------------------------------------------------

	//#CM35391
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
	 * 			storage plan date[nil] <BR>
	 * 			item code[nil] <BR>
	 * 			case piece flag [ALL] <BR>
	 * 			storage case qty[nil]<BR>
	 * 			storage piece qty[nil]<BR>
	 * 			expiry date[nil]<BR>
	 * 			print storage work list[true]<BR>
	 * 			packed qty per case[nil]<BR>
	 * 			packed qty per piece[nil]<BR>
	 * 			remaining qty per case[nil]<BR>
	 * 			remaining qty per piece[nil]<BR>
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
			//#CM35392
			// To judge whether it is update status by the Modify button pressing, line No is set in ViewState. 
			//#CM35393
			// (default:-1)
			this.getViewState().setInt("LineNo", -1);

			//#CM35394
			// disable button click
			//#CM35395
			// Storage start button
			btn_StorageStart.setEnabled(false);
			//#CM35396
			// All cancel button
			btn_AllCancel.setEnabled(false);

			//#CM35397
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM35398
			// fetch Http Locale
			Locale locale = this.getHttpRequest().getLocale();

			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			//#CM35399
			// pull down display data(storage area)
			String[] areaid = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);
			//#CM35400
			// pull down display data(workplace)
			String[] sagyoba = pull.getSagyobaPulldownData(conn, STATION_STORAGE, true, "");
			//#CM35401
			// pull down display data(station)
			String[] stno = pull.getStationPulldownData(conn, STATION_STORAGE, true, "");
			//#CM35402
			// pull down display data(Hard zone)
			String[] zoneno = pull.getHardZonePulldownData(conn, "");

			//#CM35403
			// set pulldown data
			PulldownHelper.setPullDown(pul_WareHouse, areaid);
			PulldownHelper.setLinkedPullDown(pul_WorkPlace, sagyoba);
			PulldownHelper.setLinkedPullDown(pul_Station, pul_WorkPlace, stno);
			PulldownHelper.setLinkedPullDown(pul_Zone, zoneno);

			//#CM35404
			// add child pull down
			pul_WareHouse.addChild(pul_WorkPlace);
			pul_WorkPlace.addChild(pul_Station);
			pul_WareHouse.addChild(pul_Zone);

			//#CM35405
			// job no.
			this.getViewState().setString(SELECT_JOBNO, "");				
			//#CM35406
			// set the cursor in worker code input
			//#CM35407
			// update date/time
			this.getViewState().setString(SELECT_LASTUPDATE, "");				

			//#CM35408
			// initial display
			setFirstDisp(true);
		}
		catch (Exception ex)
		{
			//#CM35409
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
				//#CM35410
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

	//#CM35411
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
			//#CM35412
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);

			//#CM35413
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			
			//#CM35414
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}
		//#CM35415
		// Confirmation dialog when Start Storage button is pressed "Want to set?"
		btn_StorageStart.setBeforeConfirm("MSG-9000");

		//#CM35416
		// When the Clear list button is pressed, list input information confirmation dialog "Is it OK to clear the list?"
		btn_AllCancel.setBeforeConfirm("MSG-W0012");

	}

	//#CM35417
	/**
	 * This method is called when returning from popup window<BR>
	 * override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE><BR>
	 * <BR>
	 * overview ::fetch the value from search screen and set it<BR>
	 * <BR>
	 * <DIR>
	 *      1.fetch the value set from popup search screen<BR>
	 *      2.set value if it is not null<BR>
	 *      3.set the cursor in worker code<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		
		//#CM35418
		// fetch parameter selected from listbox
		//#CM35419
		// consignor code
		String consignorcode = param.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM35420
		// storage plan date
		String plandate = param.getParameter(ListAsStoragePlanDateBusiness.PLANDATE_KEY);
		//#CM35421
		// item code
		String itemcode = param.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
		//#CM35422
		// Storage Case Qty(Used as remaining case qty)
		String caseqty = param.getParameter(ListAsStoragePlanBusiness.CASEQTY_KEY);
		//#CM35423
		// Storage Piece Qty(Used as remaining piece qty)
		String pieceqty = param.getParameter(ListAsStoragePlanBusiness.PIECEQTY_KEY);
		//#CM35424
		// packed qty per case
		String enteringqty = param.getParameter(ListAsStoragePlanBusiness.ENTERINGQTY_KEY);
		//#CM35425
		// packed qty per piece
		String bundleenteringqty = param.getParameter(ListAsStoragePlanBusiness.BUNDLEENTERINGQTY_KEY);
		//#CM35426
		// Case piece flag
		String casepieceflag = param.getParameter(ListAsStoragePlanBusiness.CASEPIECEFLAG_KEY);
		//#CM35427
		// job no.
		String jobno = param.getParameter(ListAsStoragePlanBusiness.JOBNO_KEY);
		//#CM35428
		// update date/time
		String lastupdate = param.getParameter(ListAsStoragePlanBusiness.LASTUPDATE_KEY);

		//#CM35429
		// set the value if not empty
		//#CM35430
		// consignor code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM35431
		// storage plan date
		if (!StringUtil.isBlank(plandate))
		{
			txt_StoragePlanDate.setText(plandate);
		}
		//#CM35432
		// item code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		if (!StringUtil.isBlank(casepieceflag))
		{
			//#CM35433
			// Case piece flag
			if (AsScheduleParameter.CASEPIECE_FLAG_NOTHING.equals(casepieceflag))
			{
				//#CM35434
				// case piece flag(All)
				rdo_CpfAll.setChecked(false);
				//#CM35435
				// case piece flag(Case)
				rdo_Cpf_Case.setChecked(false);
				//#CM35436
				// case piece flag(Piece)
				rdo_Cpf_Piece.setChecked(false);
				//#CM35437
				// case piece flag(None)
				rdo_Cpf_AppointOff.setChecked(true);
			}
			else if (AsScheduleParameter.CASEPIECE_FLAG_CASE.equals(casepieceflag))
			{
				//#CM35438
				// case piece flag(All)
				rdo_CpfAll.setChecked(false);
				//#CM35439
				// case piece flag(Case)
				rdo_Cpf_Case.setChecked(true);
				//#CM35440
				// case piece flag(Piece)
				rdo_Cpf_Piece.setChecked(false);
				//#CM35441
				// case piece flag(None)
				rdo_Cpf_AppointOff.setChecked(false);
			}
			else if (AsScheduleParameter.CASEPIECE_FLAG_PIECE.equals(casepieceflag))
			{
				//#CM35442
				// case piece flag(All)
				rdo_CpfAll.setChecked(false);
				//#CM35443
				// case piece flag(Case)
				rdo_Cpf_Case.setChecked(false);
				//#CM35444
				// case piece flag(Piece)
				rdo_Cpf_Piece.setChecked(true);
				//#CM35445
				// case piece flag(None)
				rdo_Cpf_AppointOff.setChecked(false);
			}
			else
			{
				//#CM35446
				// case piece flag(All)
				rdo_CpfAll.setChecked(true);
				//#CM35447
				// case piece flag(Case)
				rdo_Cpf_Case.setChecked(false);
				//#CM35448
				// case piece flag(Piece)
				rdo_Cpf_Piece.setChecked(false);
				//#CM35449
				// case piece flag(None)
				rdo_Cpf_AppointOff.setChecked(false);
			}		
		}
		
		int pnt = this.getViewState().getInt("LineNo");
		//#CM35450
		// storage case qty
		if (!StringUtil.isBlank(caseqty))
		{
			int total_planqty = Formatter.getInt(caseqty) * Formatter.getInt(enteringqty) + Formatter.getInt(pieceqty);
			int disp_caseqty = calcCaseQty(total_planqty, WmsFormatter.getInt(enteringqty), pnt, casepieceflag, jobno);
			txt_StrgCaseQty.setText(WmsFormatter.getNumFormat(disp_caseqty));
			lbl_JavaSetZanCaseQty.setText(WmsFormatter.getNumFormat(disp_caseqty));
		}
		//#CM35451
		// storage piece qty
		if (!StringUtil.isBlank(pieceqty))
		{
			int total_planqty = Formatter.getInt(caseqty) * Formatter.getInt(enteringqty) + Formatter.getInt(pieceqty);
			int disp_pieceqty = calcPieceQty(total_planqty, WmsFormatter.getInt(enteringqty), pnt, casepieceflag, jobno);
			txt_StrgPieceQty.setText(WmsFormatter.getNumFormat(disp_pieceqty));
			lbl_JavaSetZanPiece.setText(WmsFormatter.getNumFormat(disp_pieceqty));
		}
		//#CM35452
		// packed qty per case
		if (!StringUtil.isBlank(enteringqty))
		{
			lbl_JavaSetCaseEntering.setText(enteringqty);
		}
		//#CM35453
		// packed qty per piece
		if (!StringUtil.isBlank(bundleenteringqty))
		{
			lbl_JavaSetBundleEntering.setText(bundleenteringqty);
		}
		//#CM35454
		// job no.
		if (!StringUtil.isBlank(jobno))
		{
			//#CM35455
			// job no.
			this.getViewState().setString(SELECT_JOBNO, jobno);				
		}
		//#CM35456
		// update date/time
		if (!StringUtil.isBlank(lastupdate))
		{
			//#CM35457
			// update date/time
			this.getViewState().setString(SELECT_LASTUPDATE, lastupdate);				
		}

		//#CM35458
		// set the cursor in worker code input
		setFocus(txt_WorkerCode);
	}

	//#CM35459
	// Package methods -----------------------------------------------

	//#CM35460
	// Protected methods ---------------------------------------------
	//#CM35461
	/**
	 * input check.<BR>
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
		if (!checker.checkContainNgText(txt_ItemCode))
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

	//#CM35462
	// Private methods -----------------------------------------------
	//#CM35463
	/**
	 * This method initializes screen<BR>
	 * <BR>
	 * overview ::initial screen display <BR>
	 * and set the cursor in worker code<BR>
	 * <DIR>
	 * 		[parameter] <BR>
	 * 		<DIR>
	 * 			All screens are initialized for wkrClrFlg true, and only a clear range is initialized as false. <BR>
	 * 			<DIR>
	 * 				item name[initial value] <BR>
	 * 				<DIR>
	 * 					worker code[nil/as it is, while clicking clear button]<BR>
	 * 					password[nil/as it is, while clicking clear button]<BR>
	 * 					consignor code[Default display when there is only one corresponding consignor./as it is, while clicking clear button]<BR>
	 * 					item code[nil/as it is, while clicking clear button]<BR>
	 * 					case piece flag[All/as it is, while clicking clear button]<BR>
	 * 					storage case qty[nil]<BR>
	 * 					storage piece qty[nil]<BR>
	 * 					expiry date[nil]<BR>
	 * 					print storage work list[true]<BR>
	 * 					packed qty per case[nil]<BR>
	 * 					packed qty per piece[nil]<BR>
	 * 					remaining qty per case[nil]<BR>
	 * 					remaining qty per piece[nil]<BR>
	 * 				</DIR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * @param wkrClrFlg Clear Flag
	 * @throws Exception report all the exceptions
	 */
	private void setFirstDisp(boolean wkrClrFlg) throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM35464
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			if (StringUtil.isBlank(this.getViewState().getString(SELECT_JOBNO)))
			{
				WmsScheduler schedule = new AsItemStorageSCH();
	
				AsScheduleParameter param = (AsScheduleParameter) schedule.initFind(conn, null);

				//#CM35465
				// don't initialize on pressing the clear button
				if (wkrClrFlg)
				{
					//#CM35466
					// display customer code in the init screen, when there is single record
					if (param != null)
					{
						txt_ConsignorCode.setText(param.getConsignorCode());
					}
					else
					{
						txt_ConsignorCode.setText("");
					}
					//#CM35467
					// storage plan date
					txt_StoragePlanDate.setText("");
					//#CM35468
					// item code
					txt_ItemCode.setText("");
					//#CM35469
					// case piece flag(All)
					rdo_CpfAll.setChecked(true);
					//#CM35470
					// case piece flag(Case)
					rdo_Cpf_Case.setChecked(false);
					//#CM35471
					// case piece flag(Piece)
					rdo_Cpf_Piece.setChecked(false);
					//#CM35472
					// case piece flag(None)
					rdo_Cpf_AppointOff.setChecked(false);
				}
				
				//#CM35473
				// storage case qty
				txt_StrgCaseQty.setText("");
				//#CM35474
				// storage piece qty
				txt_StrgPieceQty.setText("");
				//#CM35475
				// expiry date
				txt_UseByDate.setText("");
				//#CM35476
				// packed qty per case
				lbl_JavaSetCaseEntering.setText("");
				//#CM35477
				// packed qty per piece
				lbl_JavaSetBundleEntering.setText("");
				//#CM35478
				// remaining qty per case
				lbl_JavaSetZanCaseQty.setText("");
				//#CM35479
				// remaining qty per piece
				lbl_JavaSetZanPiece.setText("");
				//#CM35480
				// check [Print storage work list] check box
				chk_CommonUse.setChecked(true);
			}
			else
			{
				//#CM35481
				// search work info
				WorkingInformationHandler whandler = new WorkingInformationHandler(conn);
				WorkingInformationSearchKey wkey = new WorkingInformationSearchKey();

				int pnt = this.getViewState().getInt("LineNo");
	
				//#CM35482
				// job no.
				wkey.setJobNo(this.getViewState().getString(SELECT_JOBNO));

				//#CM35483
				// search work info process
				WorkingInformation workInfo = (WorkingInformation) whandler.findPrimary(wkey);

				//#CM35484
				// packed qty per case
				lbl_JavaSetCaseEntering.setText(WmsFormatter.getNumFormat(workInfo.getEnteringQty()));
				//#CM35485
				// remaining qty per case
				int CaseQty = calcCaseQty(workInfo.getPlanEnableQty(), workInfo.getEnteringQty(),
									pnt, workInfo.getCasePieceFlag(),
									workInfo.getJobNo());
				lbl_JavaSetZanCaseQty.setText(WmsFormatter.getNumFormat(CaseQty));
				//#CM35486
				// packed qty per piece
				lbl_JavaSetBundleEntering.setText(WmsFormatter.getNumFormat(workInfo.getBundleEnteringQty()));
				//#CM35487
				// remaining qty per piece
				int PieceQty = calcPieceQty(workInfo.getPlanEnableQty(), workInfo.getEnteringQty(),
									pnt, workInfo.getCasePieceFlag(),
									workInfo.getJobNo());
				lbl_JavaSetZanPiece.setText(WmsFormatter.getNumFormat(PieceQty));
			}
			//#CM35488
			// set cursor in worker code
			setFocus(txt_WorkerCode);
		}
		catch (Exception ex)
		{
			//#CM35489
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
				//#CM35490
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

	//#CM35491
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
				//#CM35492
				// The item name is set in itemName. 
				itemName = DisplayText.getText(name.getResourceKey());
				return itemName;
			}
		}
		return itemName;
	}

	//#CM35493
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
	 * 			pulldown.warehouse<BR>
	 * 			pulldown.zone<BR>
	 * 			pulldown.sending station<BR>
	 * 			worker code<BR>
	 * 			password<BR>
	 * 			list print flag<BR>
	 * 			preset area.case piece flag(Concealment item)<BR>
	 * 			preset area.job no..(Concealment item)<BR>
	 * 			preset area.update date/time<BR>
	 * 			preset area.storage plan date<BR>
	 * 			preset area.consignor code<BR>
	 * 			preset area.consignor name<BR>
	 * 			preset area.item code<BR>
	 * 			preset area.item name<BR>
	 * 			preset area.classification<BR>
	 * 			preset area.storage case qty<BR>
	 * 			preset area.storage piece qty<BR>
	 * 			preset area.packed qty per case<BR>
	 * 			preset area.packed qty per piece<BR>
	 * 			preset area.expiry date<BR>
	 * 			preset area.Case ITF<BR>
	 * 			preset area.bundle itf<BR>
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
		
		Locale locale = this.httpRequest.getLocale();

		for (int i = 1; i < lst_ItemStorage.getMaxRows(); i++)
		{
			//#CM35494
			// Exclude the line for the correction. 
			if (i == lineno)
			{
				continue;
			}

			//#CM35495
			// specify line
			lst_ItemStorage.setCurrentRow(i);

			//#CM35496
			// set schedule parameter
			AsScheduleParameter param = new AsScheduleParameter();

			//#CM35497
			// warehouse
			param.setWareHouseNo(pul_WareHouse.getSelectedValue());
			//#CM35498
			// zone
			param.setHardZone(pul_Zone.getSelectedValue());
			//#CM35499
			// sending station
			param.setFromStationNo(pul_Station.getSelectedValue());
			if( pul_Station.getSelectedValue().equals(Station.AUTO_SELECT_STATIONNO))
			{
				//#CM35500
				// Set workplace when [Automatic division] is specified for station. 
				param.setFromStationNo(pul_WorkPlace.getSelectedValue());
			}

			//#CM35501
			// input area info
			//#CM35502
			// worker code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM35503
			// password
			param.setPassword(txt_Password.getText());
			//#CM35504
			// terminal no.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());
			//#CM35505
			// list print flag
			param.setListFlg(chk_CommonUse.getChecked());
			//#CM35506
			// Case/Piece flag(Concealment item)
			param.setCasePieceFlag(CollectionUtils.getString(0,lst_ItemStorage.getValue(0)));
			//#CM35507
			// storage plan date
			param.setPlanDate(WmsFormatter.toParamDate(lst_ItemStorage.getValue(3), locale));
			//#CM35508
			// consignor code
			param.setConsignorCode(lst_ItemStorage.getValue(4));
			//#CM35509
			// item code
			param.setItemCode(lst_ItemStorage.getValue(5));
			//#CM35510
			// case piece flag
			param.setCasePieceFlagNameDisp(lst_ItemStorage.getValue(6));
			//#CM35511
			// storage case qty
			param.setStorageCaseQty(Formatter.getInt(lst_ItemStorage.getValue(7)));			
			//#CM35512
			// packed qty per case
			param.setEnteringQty(Formatter.getInt(lst_ItemStorage.getValue(8)));
			//#CM35513
			// expiry date
			param.setUseByDate(lst_ItemStorage.getValue(9));
			//#CM35514
			// Case ITF
			param.setITF(lst_ItemStorage.getValue(10));
			//#CM35515
			// consignor name
			param.setConsignorName(lst_ItemStorage.getValue(11));
			//#CM35516
			// item name
			param.setItemName(lst_ItemStorage.getValue(12));
			//#CM35517
			// storage piece qty
			param.setStoragePieceQty(Formatter.getInt(lst_ItemStorage.getValue(13)));
			//#CM35518
			// packed qty per piece
			param.setBundleEnteringQty(Formatter.getInt(lst_ItemStorage.getValue(14)));
			//#CM35519
			// bundle itf
			param.setBundleITF(lst_ItemStorage.getValue(15));
			//#CM35520
			// Work No(Concealment item)
			param.setWorkingNo(CollectionUtils.getString(1,lst_ItemStorage.getValue(0)));
			//#CM35521
			// Last updated date and time(Concealment item)
			param.setLastUpdateDate(DateOperator.parse(CollectionUtils.getString(2,lst_ItemStorage.getValue(0)), "yyyy/MM/dd HH:mm:ss"));

			//#CM35522
			// save the line no.
			param.setRowNo(i);

			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			//#CM35523
			// set preset area data if value is not null
			AsScheduleParameter[] listparam = new AsScheduleParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			//#CM35524
			// if there is no preset area data, set null
			return null;
		}
	}

	//#CM35525
	/**
	 * Display the content of list cell flag to case/piece flag check box of input area. <BR>
	 * <BR>
	 * @param cpflag case piece flag
	 * @throws Exception report all the exceptions 
	 */
	private void setCasePieceRBFromList(String cpflag) throws Exception
	{
		if (cpflag.equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM35526
			// check none
			rdo_CpfAll.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(true);
			rdo_Cpf_Case.setChecked(false);
			rdo_Cpf_Piece.setChecked(false);
		}
		else if (cpflag.equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
		{
			//#CM35527
			// check case
			rdo_CpfAll.setChecked(false);
			rdo_Cpf_Case.setChecked(true);
			rdo_Cpf_Piece.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);
		}
		else if (cpflag.equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM35528
			// check piece
			rdo_CpfAll.setChecked(false);
			rdo_Cpf_Piece.setChecked(true);
			rdo_Cpf_Case.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);
		}
		else if (cpflag.equals(AsScheduleParameter.CASEPIECE_FLAG_ALL))
		{
			//#CM35529
			// check all
			rdo_CpfAll.setChecked(true);
			rdo_Cpf_Piece.setChecked(false);
			rdo_Cpf_Case.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);
		}
	}

	//#CM35530
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
	 * 			1.job no..<BR>
	 * 			2.update date/time<BR>
	 * 			3.Workable qty<BR>
	 *	 	</DIR>
	 * </DIR>
	 * <DIR>
	 * 		[list cell line no. list]
	 * 		<DIR>
	 * 			0.Concealment item <BR>
	 * 			3.storage plan date<BR>
	 * 			4.consignor code <BR>
	 * 			5.item code <BR>
	 * 			6.classification <BR>
	 * 			7.storage case qty <BR>
	 * 			8.packed qty per case <BR>
	 * 			9.expiry date <BR>
	 * 			10.Case ITF <BR>
	 * 			11.consignor name <BR>
	 * 			12.item name <BR>
	 * 			13.storage piece qty <BR>
	 * 			14.packed qty per piece <BR>
	 * 			15.bundle itf <BR>
	 *	 	</DIR>
	 * </DIR>
	 * <BR>
	 * @param workInfo work into to be set.
	 * @throws Exception report all the exceptions
	 */
	private void setList(WorkingInformation workInfo) throws Exception
	{
		//#CM35531
		//add data to tool tip
		ToolTipHelper toolTip = new ToolTipHelper();
		
		//#CM35532
		// consignor name
		toolTip.add(DisplayText.getText("LBL-W0026"), workInfo.getConsignorName());
		//#CM35533
		// item name
		toolTip.add(DisplayText.getText("LBL-W0103"), workInfo.getItemName1());
		//#CM35534
		// expiry date
		toolTip.add(DisplayText.getText("LBL-W0270"), txt_UseByDate.getText());
		//#CM35535
		// Case ITF
		toolTip.add(DisplayText.getText("LBL-W0010"), workInfo.getItf());
		//#CM35536
		// Bundle ITF
		toolTip.add(DisplayText.getText("LBL-W0006"), workInfo.getBundleItf());

		//#CM35537
		//Set tool tip in the current line. 
		lst_ItemStorage.setToolTip(lst_ItemStorage.getCurrentRow(), toolTip.getText());

		//#CM35538
		// Keep case piece flag + Work No + Last updated date and time.
		List Hiden = new Vector();
		Hiden.add(workInfo.getCasePieceFlag());
		Hiden.add(workInfo.getJobNo());
		Hiden.add(DateOperator.changeDateTime(workInfo.getLastUpdateDate()));
		Hiden.add(Integer.toString(workInfo.getPlanEnableQty()));

		lst_ItemStorage.setValue(0, CollectionUtils.getConnectedString(Hiden));
		//#CM35539
		// storage plan date
		lst_ItemStorage.setValue(3, txt_StoragePlanDate.getText());		
		//#CM35540
		// consignor code
		lst_ItemStorage.setValue(4, txt_ConsignorCode.getText());
		//#CM35541
		// item code
		lst_ItemStorage.setValue(5, txt_ItemCode.getText());
		//#CM35542
		// classification
		lst_ItemStorage.setValue(6, DisplayUtil.getPieceCaseValue(workInfo.getCasePieceFlag()));
		//#CM35543
		// storage case qty
		if (StringUtil.isBlank(txt_StrgCaseQty.getText()))
		{
			lst_ItemStorage.setValue(7, "0");
		}
		else
		{
			lst_ItemStorage.setValue(7, WmsFormatter.getNumFormat(txt_StrgCaseQty.getInt()));
		}
		//#CM35544
		// packed qty per case
		lst_ItemStorage.setValue(8, WmsFormatter.getNumFormat(workInfo.getEnteringQty()));
		//#CM35545
		// expiry date
		lst_ItemStorage.setValue(9, txt_UseByDate.getText());
		//#CM35546
		// Case ITF
		lst_ItemStorage.setValue(10, workInfo.getItf());
		//#CM35547
		// consignor name
		lst_ItemStorage.setValue(11, workInfo.getConsignorName());
		//#CM35548
		// item name
		lst_ItemStorage.setValue(12, workInfo.getItemName1());
		//#CM35549
		// storage piece qty
		if (StringUtil.isBlank(txt_StrgPieceQty.getText()))
		{
			lst_ItemStorage.setValue(13, "0");
		}
		else
		{
			lst_ItemStorage.setValue(13, WmsFormatter.getNumFormat(txt_StrgPieceQty.getInt()));
		}
		//#CM35550
		// packed qty per piece
		lst_ItemStorage.setValue(14, WmsFormatter.getNumFormat(workInfo.getBundleEnteringQty()));
		//#CM35551
		// bundle itf
		lst_ItemStorage.setValue(15, workInfo.getBundleItf());
	}

	//#CM35552
	// Event handler methods -----------------------------------------
	//#CM35553
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

	//#CM35554
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
		//#CM35555
		// set search conditions to consignor search screen
		ForwardParameters param = new ForwardParameters();
		//#CM35556
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM35557
		// search flag
		param.setParameter(ListAsConsignorBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_WORKINFO);
		//#CM35558
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsconsignor/ListAsConsignor.do", param, "/progress.do");
	}

	//#CM35559
	/** 
	 * It is called when the retrieval button of Storage Plan Date is pressed.<BR>
	 * <BR>
	 * overview :: Display the Storage Plan Date list list box by the set search condition in parameter and the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] <BR>
	 *    <DIR>
	 *       consignor code <BR>
	 *       storage plan date <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_PSearchStoragePlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM35560
		// Set the search condition on the Storage Plan Date retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM35561
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM35562
		// storage plan date
		param.setParameter(ListAsStoragePlanDateBusiness.PLANDATE_KEY, WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
		//#CM35563
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsstorageplandate/ListAsStoragePlanDate.do", param, "/progress.do");
	}

	//#CM35564
	/** 
	 * This process is called upon clicking item code search button<BR>
	 * <BR>
	 * overview ::set search condition in parameter and display the item code list box<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] <BR>
	 *    <DIR>
	 *       consignor code <BR>
	 *       storage plan date <BR>
	 *       item code <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM35565
		// Set the search conditions to item search screen
		ForwardParameters param = new ForwardParameters();
		//#CM35566
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM35567
		// storage plan date
		param.setParameter(ListAsStoragePlanDateBusiness.PLANDATE_KEY, WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
		//#CM35568
		// item code
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM35569
		// search flag
		param.setParameter(ListAsItemBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_WORKINFO);
		//#CM35570
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsitem/ListAsItem.do", param, "/progress.do");
	}

	//#CM35571
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
	 *          storage plan date* <BR>
	 * 			item code *<BR>
	 * 			case/piece flag *<BR>
	 * 			storage case qty *2 *4<BR>
	 *			storage piece qty *3 *4<BR>
	 *			expiry date <BR>
	 *		<BR>
	 * 			*2 <BR>
	 * 			mandatory input if case/piece flag is 1:case<BR>
	 * 			*3 <BR>
	 * 			mandatory input if case/piece flag is 2:piece<BR>
	 * 			*4 <BR>
	 * 			if case/piece flag is 0:none or All,<BR>
	 *			Either storage case qty or storage piece qty is mandatory <BR>
	 * 			case qty x packed qty per case + piece qty is not 0(>0) <BR>
	 *	 	</DIR>
	 *   </DIR>
	 *   <BR>
	 *   4.Add it to the filtering area if the result of the schedule is true.<BR>
	 *     Update our data for the correction in information on input area when you press the input button after the correction button is pressed. <BR>
	 *   5.If the result of the schedule is true,Make the start storage button and the clear list button enabled. <BR>
	 *   6.If the result of the schedule is true,set default value (-1) for preset area row no. in viewstate<BR>
	 *   7.Output the message acquired from the schedule to the screen. <BR>
	 * 	 8.save input area contents<BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		//#CM35572
		// set the cursor in worker code input
		setFocus(txt_WorkerCode);

		//#CM35573
		// mandatory input check
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		txt_StoragePlanDate.validate();
		txt_ItemCode.validate();
		
		//#CM35574
		// pattern matching character
		txt_StrgCaseQty.validate(false);
		txt_StrgPieceQty.validate(false);
		txt_UseByDate.validate(false);

		String itemname = null;

		itemname = checkNumber(txt_StrgCaseQty, lbl_StorageCaseQty);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_StrgPieceQty, lbl_StoragePieceQty);

		if (!StringUtil.isBlank(itemname))
		{
			//#CM35575
			// Display the error message, and finish.
			//#CM35576
			// 6023057 = Input the value {0} more than {1}. 
			message.setMsgResourceKey("6023057" + wDelim + itemname + wDelim + "0");
			return;
		}

		//#CM35577
		// Input character check for eWareNavi
		if (!checkContainNgText())
		{
			return;
		}

		Connection conn = null;
		
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM35578
			// set schedule parameter
			//#CM35579
			// input area
			AsScheduleParameter param = new AsScheduleParameter();

			//#CM35580
			// search work info
			WorkingInformationHandler whandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey wkey = new WorkingInformationSearchKey();
						
			//#CM35581
			// consignor code
			wkey.setConsignorCode(txt_ConsignorCode.getText());
			//#CM35582
			// storage plan date
			wkey.setPlanDate(WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));			
			//#CM35583
			// item code
			wkey.setItemCode(txt_ItemCode.getText());
			//#CM35584
			// Case piece flag
			if(rdo_Cpf_Case.getChecked())
			{
				wkey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_CASE) ;
			}
			else if(rdo_Cpf_Piece.getChecked())
			{
				wkey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_PIECE) ;
			}
			else if(rdo_Cpf_AppointOff.getChecked())
			{
				wkey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING) ;
			}
			else if(rdo_CpfAll.getChecked())
			{
				//#CM35585
				// 6023491 = Select a case, piece or a specified for Case/Piece flag.
				message.setMsgResourceKey("6023491");
				return;
			}

			//#CM35586
			// Status flag(Storage)
			wkey.setJobType(WorkingInformation.JOB_TYPE_STORAGE) ;
			//#CM35587
			// Work Status(Stand by)
			wkey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);

			WorkingInformation[] workInfo = (WorkingInformation[]) whandler.find(wkey);
			
			int find_datacnt = 0;
			if (workInfo.length == 0 || workInfo == null)
			{
				//#CM35588
				// Display the error message, and finish.
			    //#CM35589
			    // 6003011=There was no object data.
				message.setMsgResourceKey("6003011");
				return;
			}
			
			//#CM35590
			// Process it as follows by acquired work information only of one work information. 
			if (workInfo.length == 1)
			{
				find_datacnt = 0;
			}
			else
			{
				boolean find_flag = false;
				String sv_jobno = getViewState().getString(SELECT_JOBNO);
				if (!StringUtil.isBlank(sv_jobno))
				{
					//#CM35591
					// Process it as follows when acquired work information and kept Work No are corresponding to kept work information.
					for (int plc=0; plc<workInfo.length; plc++)
					{
						if (sv_jobno.equals(workInfo[plc].getJobNo()))
						{
							find_flag = true;
							find_datacnt = plc;
							break;
						}
					}
				}
				if (!find_flag)
				{
					//#CM35592
					// There is two or more schedule information which becomes an object. Select it with the Storage schedule retrieval button. 
					message.setMsgResourceKey("6023444");
					return;
				}
			}			

			//#CM35593
			// warehouse station no.
			param.setWareHouseNo(pul_WareHouse.getSelectedValue());
			//#CM35594
			// worker code 
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM35595
			// password
			param.setPassword(txt_Password.getText());
			//#CM35596
			// consignor code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM35597
			// storage plan date
			param.setPlanDate(WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
			//#CM35598
			// item code
			param.setItemCode(txt_ItemCode.getText());
			//#CM35599
			// Case piece flag
			param.setCasePieceFlag(workInfo[find_datacnt].getCasePieceFlag());
			//#CM35600
			// storage case qty
			param.setStorageCaseQty(txt_StrgCaseQty.getInt());
			//#CM35601
			// storage piece qty
			param.setStoragePieceQty(txt_StrgPieceQty.getInt());
			//#CM35602
			// packed qty per case
			param.setEnteringQty(workInfo[find_datacnt].getEnteringQty());
			//#CM35603
			// packed qty per piece
			param.setBundleEnteringQty(workInfo[find_datacnt].getBundleEnteringQty());
			//#CM35604
			// expiry date
			param.setUseByDate(txt_UseByDate.getText());
			//#CM35605
			// remaining qty per case
			param.setAllocateCaseQty(calcCaseQty(workInfo[find_datacnt].getPlanEnableQty(),
								workInfo[find_datacnt].getEnteringQty(), this.getViewState().getInt("LineNo"),
								workInfo[find_datacnt].getCasePieceFlag(), workInfo[find_datacnt].getJobNo()));
			//#CM35606
			// remaining qty per piece
			param.setAllocatePieceQty(calcPieceQty(workInfo[find_datacnt].getPlanEnableQty(),
								workInfo[find_datacnt].getEnteringQty(), this.getViewState().getInt("LineNo"),
								workInfo[find_datacnt].getCasePieceFlag(), workInfo[find_datacnt].getJobNo()));
			//#CM35607
			// job no.
			param.setWorkingNo(workInfo[find_datacnt].getJobNo());
			//#CM35608
			// update date/time
			param.setLastUpdateDate(workInfo[find_datacnt].getLastUpdateDate());
			//#CM35609
			// print storage list
			param.setListFlg(chk_CommonUse.getChecked());

			//#CM35610
			// set schedule parameter
			//#CM35611
			// preset area
			AsScheduleParameter[] listparam = null;
		
			//#CM35612
			//set null if there is no data in preset area
			if (lst_ItemStorage.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				//#CM35613
				// set value if data exists
				listparam = setListParam(this.getViewState().getInt("LineNo"));
			}
			
			WmsScheduler schedule = new AsItemStorageSCH();

			if (schedule.check(conn, param, listparam))
			{
				//#CM35614
				// If the result is true, the data is set in the filtering area. 
				if (this.getViewState().getInt("LineNo") == -1)
				{
					//#CM35615
					// Adds to the filter in case of a new input. 
					lst_ItemStorage.addRow();
					lst_ItemStorage.setCurrentRow(lst_ItemStorage.getMaxRows() - 1);
					setList(workInfo[find_datacnt]);
				}
				else
				{
					//#CM35616
					// The Line No data for the correction is updated in case of the input data is under the correction. 
					lst_ItemStorage.setCurrentRow(this.getViewState().getInt("LineNo"));
					setList(workInfo[find_datacnt]);
					//#CM35617
					// change color of selected row
					lst_ItemStorage.resetHighlight();
				}

				//#CM35618
				// return the modify status to default
				this.getViewState().setInt("LineNo", -1);

				//#CM35619
				// stock id
				this.getViewState().setString(SELECT_JOBNO, workInfo[find_datacnt].getJobNo());				
				//#CM35620
				// update date/time
				this.getViewState().setString(SELECT_LASTUPDATE,
						DateOperator.changeDateTime(workInfo[find_datacnt].getLastUpdateDate()));				
				
				//#CM35621
				// refresh the screen display
				setFirstDisp(true);

				//#CM35622
				// enable button
				//#CM35623
				// Storage start button
				btn_StorageStart.setEnabled(true);
				//#CM35624
				// list clear button
				btn_AllCancel.setEnabled(true);
			}

			//#CM35625
			// set message
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			//#CM35626
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
				//#CM35627
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

	//#CM35628
	/**
	 * called when clear button is clicked<BR>
	 * <BR>
	 * Abstract :clear the input area<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Clear ViewState information. <BR>
	 * 		2.return input area items to initial state<BR>
	 * 		<DIR>
	 *   		item name[initial value]<BR>
	 * 			<DIR>
	 * 				worker code[as it is]<BR>
	 * 				password[as it is]<BR>
	 * 				consignor code[as it is]<BR>
	 * 				item code[as it is]<BR>
	 * 				case piece flag[as it is]<BR>
	 * 				storage case qty[nil]<BR>
	 * 				storage piece qty[nil]<BR>
	 * 				expiry date[nil]<BR>
	 * 				print storage work list[true]<BR>
	 * 				packed qty per case[nil]<BR>
	 * 				packed qty per piece[nil]<BR>
	 * 				remaining qty per case[nil]<BR>
	 * 				remaining qty per piece[nil]<BR>
	 * 			</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM35629
		// stock id
		this.getViewState().setString(SELECT_JOBNO, "");
		//#CM35630
		// update date/time
		this.getViewState().setString(SELECT_LASTUPDATE, "");				
		//#CM35631
		// Do the clear processing.
		setFirstDisp(false);
	}

	//#CM35632
	/**
	 * It is called when the Start Storage button is pressed.<BR>
	 * <BR>
	 * overview :: Set Storage information on the filtering area. <BR>
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
	 * 						pulldown.warehouse<BR>
	 * 						pulldown.zone<BR>
	 * 						pulldown.sending station<BR>
	 * 						worker code<BR>
	 * 						password<BR>
	 * 						list print flag<BR>
	 * 						preset area.case piece flag(Concealment item)<BR>
	 * 						preset area.job no..(Concealment item)<BR>
	 * 						preset area.update date/time<BR>
	 * 						preset area.storage plan date<BR>
	 * 						preset area.consignor code<BR>
	 * 						preset area.consignor name<BR>
	 * 						preset area.item code<BR>
	 * 						preset area.item name<BR>
	 * 						preset area.classification<BR>
	 * 						preset area.storage case qty<BR>
	 * 						preset area.storage piece qty<BR>
	 * 						preset area.packed qty per case<BR>
	 * 						preset area.packed qty per piece<BR>
	 * 						preset area.expiry date<BR>
	 * 						preset area.Case ITF<BR>
	 * 						preset area.bundle itf<BR>
	 * 						preset area line no.<BR>
	 * 						terminal no.<BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.Clear the Input area and filtered information when the result of the schedule is true. <BR>
	 *				3.Release correction status. (Set default:-1 to line No. for ViewState. )<BR>
	 *    			If false, output the message acquired from the schedule to the screen. <BR>
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
			//#CM35633
			// set the cursor in worker code input
			setFocus(txt_WorkerCode);

			//#CM35634
			// set schedule parameter
			AsScheduleParameter[] param  = null ;
			//#CM35635
			// set all the data to preset area
			param = setListParam(-1);
			
			//#CM35636
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new AsItemStorageSCH();

			//#CM35637
			// start schedule
			if (schedule.startSCH(conn, param))
			{
				//#CM35638
				// commit if the result is true
				conn.commit();
				
				//#CM35639
				// start sending storage instruction
				SendRequestor req = new SendRequestor();
				req.storage();
				
				//#CM35640
				// set message
				message.setMsgResourceKey(schedule.getMessage());

				//#CM35641
				// clear list
				lst_ItemStorage.clearRow();

				//#CM35642
				// return the modify status to default
				this.getViewState().setInt("LineNo", -1);

				//#CM35643
				// refresh the screen display
				setFirstDisp(true);

				//#CM35644
				// disable button click
				//#CM35645
				// Storage start button
				btn_StorageStart.setEnabled(false);
				//#CM35646
				// list clear button
				btn_AllCancel.setEnabled(false);
			}
			else
			{
				conn.rollback();
				//#CM35647
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			//#CM35648
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
				//#CM35649
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

	//#CM35650
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
	public void lst_ItemStorage_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM35651
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM35652
			// if cancel button is clicked
			if (lst_ItemStorage.getActiveCol() == 1)
			{
				//#CM35653
				// delete list contents
				lst_ItemStorage.removeRow(lst_ItemStorage.getActiveRow());
	
				//#CM35654
				// Invalidate the Start Storage button and the Clear list button when the filtered information does not exist.
				//#CM35655
				//set null if there is no data in preset area
				if (lst_ItemStorage.getMaxRows() == 1)
				{
					//#CM35656
					// disable button click
					//#CM35657
					// Storage start button
					btn_StorageStart.setEnabled(false);
					
					//#CM35658
					// list clear button
					btn_AllCancel.setEnabled(false);
				}
				//#CM35659
				// return the modify status to default
				this.getViewState().setInt("LineNo", -1);
				//#CM35660
				// change color of selected row
				lst_ItemStorage.resetHighlight();
				//#CM35661
				// refresh the screen display
				setFirstDisp(true);
				//#CM35662
				// set the cursor in worker code input
				setFocus(txt_WorkerCode);
	
			}
			//#CM35663
			// if modify button is clicked (modify movement)
			else if (lst_ItemStorage.getActiveCol() == 2)
			{
				//#CM35664
				// search work info
				WorkingInformationHandler whandler = new WorkingInformationHandler(conn);
				WorkingInformationSearchKey wkey = new WorkingInformationSearchKey();

				//#CM35665
				// set the current row
				lst_ItemStorage.setCurrentRow(lst_ItemStorage.getActiveRow());
	
				//#CM35666
				// job no.
				wkey.setJobNo(CollectionUtils.getString(1,lst_ItemStorage.getValue(0)));

				//#CM35667
				// search work info process
				WorkingInformation workInfo = (WorkingInformation) whandler.findPrimary(wkey);
				//#CM35668
				// storage plan date
				txt_StoragePlanDate.setText(lst_ItemStorage.getValue(3));
				//#CM35669
				// consignor code
				txt_ConsignorCode.setText(lst_ItemStorage.getValue(4));
				//#CM35670
				// item code
				txt_ItemCode.setText(lst_ItemStorage.getValue(5));
				//#CM35671
				// case piece flag
				setCasePieceRBFromList(CollectionUtils.getString(0,lst_ItemStorage.getValue(0)));
				//#CM35672
				// storage case qty
				txt_StrgCaseQty.setText(lst_ItemStorage.getValue(7));
				//#CM35673
				// packed qty per case
				lbl_JavaSetCaseEntering.setText(lst_ItemStorage.getValue(8));
				//#CM35674
				// remaining qty per case
				int CaseQty = calcCaseQty(workInfo.getPlanEnableQty(), workInfo.getEnteringQty(),
									lst_ItemStorage.getActiveRow(), CollectionUtils.getString(0,lst_ItemStorage.getValue(0)),
									CollectionUtils.getString(1,lst_ItemStorage.getValue(0)));
				lbl_JavaSetZanCaseQty.setText(WmsFormatter.getNumFormat(CaseQty));
				//#CM35675
				// expiry date
				txt_UseByDate.setText(lst_ItemStorage.getValue(9));
				//#CM35676
				// storage piece qty
				txt_StrgPieceQty.setText(lst_ItemStorage.getValue(13));			
				//#CM35677
				// packed qty per piece
				lbl_JavaSetBundleEntering.setText(lst_ItemStorage.getValue(14));
				//#CM35678
				// remaining qty per piece
				int PieceQty = calcPieceQty(workInfo.getPlanEnableQty(), workInfo.getEnteringQty(),
									lst_ItemStorage.getActiveRow(), CollectionUtils.getString(0,lst_ItemStorage.getValue(0)),
									CollectionUtils.getString(1,lst_ItemStorage.getValue(0)));
				lbl_JavaSetZanPiece.setText(WmsFormatter.getNumFormat(PieceQty));

				//#CM35679
				// stock id
				this.getViewState().setString(SELECT_JOBNO, CollectionUtils.getString(1,lst_ItemStorage.getValue(0)));				
				//#CM35680
				// update date/time
				this.getViewState().setString(SELECT_LASTUPDATE, CollectionUtils.getString(2,lst_ItemStorage.getValue(0)));				

				//#CM35681
				// save in viewstate
				//#CM35682
				// To judge whether it is update status by the Modify button pressing, line No is set in ViewState. 
				this.getViewState().setInt("LineNo", lst_ItemStorage.getActiveRow());
	
				//#CM35683
				//change color of row under editing
				lst_ItemStorage.setHighlight(lst_ItemStorage.getActiveRow());
	
				//#CM35684
				// set the cursor in worker code input
				setFocus(txt_WorkerCode);
			}
		}
		catch (Exception ex)
		{
			//#CM35685
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
				//#CM35686
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

	//#CM35687
	/** 
	 * It is called when the retrieval button of the Storage schedule is pressed.<BR>
	 * <BR>
	 * overview :: Display the Storage schedule list box by the set search condition in parameter and the search condition.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter] <BR>
	 *    <DIR>
	 *       consignor code <BR>
	 *       storage plan date <BR>
	 *       item code <BR>
	 *       case piece flag <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_P_StrgPlanSrch_Click(ActionEvent e) throws Exception
	{
		//#CM35688
		// set the search conditions in storage plan search screen
		ForwardParameters param = new ForwardParameters();
		//#CM35689
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM35690
		// storage plan date
		param.setParameter(ListAsStoragePlanDateBusiness.PLANDATE_KEY, WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
		//#CM35691
		// item code
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM35692
		// case piece flag
		//#CM35693
		// All
		if (rdo_CpfAll.getChecked())
		{
			param.setParameter(
				ListAsStoragePlanBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM35694
		// Case
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setParameter(
				ListAsStoragePlanBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM35695
		// Piece
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setParameter(
				ListAsStoragePlanBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM35696
		// None
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setParameter(
				ListAsStoragePlanBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_NOTHING);
		}

		//#CM35697
		// search flag
		param.setParameter(ListAsItemBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_WORKINFO);
		//#CM35698
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsstorageplan/ListAsrsStoragePlan.do", param, "/progress.do");
	}

	//#CM35699
	/**
	 * called when the list clear button is pressed<BR>
	 * <BR>
	 * overview :: Clear all filtered display information. <BR>
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
		//#CM35700
		// eliminate all lines
		lst_ItemStorage.clearRow();

		//#CM35701
		// disable button click
		//#CM35702
		// Storage start button
		btn_StorageStart.setEnabled(false);
		//#CM35703
		// list clear button
		btn_AllCancel.setEnabled(false);

		//#CM35704
		// return the modify status to default
		this.getViewState().setInt("LineNo", -1);

		//#CM35705
		// refresh the screen display
		setFirstDisp(true);

		//#CM35706
		// set the cursor in worker code input
		setFocus(txt_WorkerCode);
	}
	
	//#CM35707
	/**
	 * The method of acquiring the remaining case qty.<BR>
	 * @param  plan_qty    Remaining plan qty
	 * @param  enteringqty packed qty per case
	 * @param  lineno      Correction line No
	 * @param  cpflag      Case piece flag
	 * @param  jobno       job no.
	 * @return caseqty     remaining qty per case 
	 */
	private int calcCaseQty(int plan_qty, int enteringqty, int lineno, String cpflag, String jobno)
	{

		int zan_qty = plan_qty;
		for (int plc = 1; plc < lst_ItemStorage.getMaxRows(); plc++)
		{
			//#CM35708
			// Exclude the line for the correction. 
			if (plc == lineno)
			{
				continue;
			}

			//#CM35709
			// specify line
			lst_ItemStorage.setCurrentRow(plc);

			//#CM35710
			// Off the subject of subtraction when Work No is not corresponding
			if (!CollectionUtils.getString(1,lst_ItemStorage.getValue(0)).equals(jobno))
			{
				continue;
			}

			zan_qty = zan_qty 
				- (Formatter.getInt(lst_ItemStorage.getValue(7)) * Formatter.getInt(lst_ItemStorage.getValue(8))
				+ Formatter.getInt(lst_ItemStorage.getValue(13)));
		}
		if (lineno >= 1)
		{
			lst_ItemStorage.setCurrentRow(lineno);
		}

		return  DisplayUtil.getCaseQty(zan_qty, enteringqty, cpflag);
	}

	//#CM35711
	/**
	 * The method of acquiring the remaining piece qty.<BR>
	 * @param  plan_qty    Remaining plan qty
	 * @param  enteringqty packed qty per case
	 * @param  lineno      Correction line No
	 * @param  cpflag      Case piece flag
	 * @param  jobno       job no.
	 * @return caseqty     remaining qty per case
	 */
	private int calcPieceQty(int plan_qty, int enteringqty, int lineno, String cpflag, String jobno)
	{

		int zan_qty = plan_qty;
		for (int plc = 1; plc < lst_ItemStorage.getMaxRows(); plc++)
		{
			//#CM35712
			// Exclude the line for the correction. 
			if (plc == lineno)
			{
				continue;
			}

			//#CM35713
			// specify line
			lst_ItemStorage.setCurrentRow(plc);

			//#CM35714
			// Off the subject of subtraction when Work No is not corresponding
			if (!CollectionUtils.getString(1,lst_ItemStorage.getValue(0)).equals(jobno))
			{
				continue;
			}

			zan_qty = zan_qty 
				- (Formatter.getInt(lst_ItemStorage.getValue(7)) * Formatter.getInt(lst_ItemStorage.getValue(8))
				+ Formatter.getInt(lst_ItemStorage.getValue(13)));
		}
		if (lineno >= 1)
		{
			lst_ItemStorage.setCurrentRow(lineno);
		}

		return  DisplayUtil.getPieceQty(zan_qty, enteringqty, cpflag);
	}
}
//#CM35715
//end of class
