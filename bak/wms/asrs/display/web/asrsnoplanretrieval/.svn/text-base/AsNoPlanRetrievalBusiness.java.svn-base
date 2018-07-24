// $Id: AsNoPlanRetrievalBusiness.java,v 1.2 2006/10/26 04:33:42 suresh Exp $

//#CM35904
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrsnoplanretrieval;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
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
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsbundleitf.ListAsBundleItfBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrscaseitf.ListAsCaseItfBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocation.ListAsLocationBusiness;
import jp.co.daifuku.wms.asrs.schedule.AsNoPlanRetrievalSCH;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WareHouse;

//#CM35905
/**
 * Designer : <BR>
 * Maker : <BR>
 * <BR>
 * No plan Retrieval for ASRS screen class. <BR>
 * Hand over the parameter to the schedule which sets the ASRS No plan retrieval. <BR>
 * and transaction commit and rollback are done in this screen<BR>
 * <BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.display button click process(<CODE>btn_View_Click</CODE>method )<BR>
 * <BR>
 * <DIR>
 *   The schedule retrieves data for the display based on the input contents set from the screen to a parameter.<BR>
 *   Receive the filtering area data for output use from the schedule, and output it to the area. <BR>
 *   <BR>
 *   [parameter] *mandatory input<BR>
 *   <BR>
 *   worker code * <BR>
 *   password * <BR>
 *   consignor code *<BR>
 *   item code  <BR>
 *   Case/Piece type * <BR>
 *   start location <BR>
 *   end location <BR>
 *   Case ITF <BR>
 *   bundle itf <BR>
 *   customer code <BR>
 *   customer name <BR>
 *   <BR>
 *   [data for output use] <BR>
 *   <BR>
 *   consignor code <BR>
 *   consignor name <BR>
 *   item code <BR>
 *   item name <BR>
 *   case piece flag <BR>
 *   location no.<BR>
 *   packed qty per case <BR>
 *   packed qty per piece <BR>
 *   available case qty((stock qty - allocation qty)/packed qty per case) <BR>
 *   available piece qty((stock qty - allocation qty)%packed qty per case) <BR>
 *   expiry date <BR>
 *   Case ITF <BR>
 *   bundle itf <BR>
 *   stock id <BR>
 *   update date/time <BR>
 * </DIR>
 * <BR>
 * 2.Retrieval Processing when Start button is pressed(<CODE>btn_RetrievalStart_Click</CODE>method ) <BR>
 * <BR>
 * <DIR>
 *   The content input from the filtering area is set in the parameter, and the schedule sets the ASRS No plan retrieval based on the parameter. <BR>
 *   Receive true when the result is received from the schedule, and processing is completed normally. 
 *   Receive false when it does not complete the schedule because of the condition error etc.<BR>
 *   output the schedule result, schedule message to screen<BR>
 *   <BR>
 *   [parameter] *mandatory input <BR>
 *   <BR>
 *   worker code * <BR>
 *   password * <BR>
 *   consignor code <BR>
 *   consignor name<BR>
 *   item code *<BR>
 *   item name <BR>
 *   Case/Piece type * <BR>
 *   location no.* <BR>
 *   packed qty per case* <BR>
 *   packed qty per bundle* <BR>
 *   Retrieval case qty* <BR>
 *   Retrieval piece qty* <BR>
 *   Case ITF <BR>
 *   bundle itf <BR>
 *   expiry date <BR>
 *   No plan retrieval work list print flag<BR>
 *   All qty flag <BR>
 *   stock id <BR>
 *   update date/time <BR>
 *   <BR>
 *   [data for output use] <BR>
 *   <BR>
 *   consignor code <BR>
 *   consignor name <BR>
 *   item code <BR>
 *   item name <BR>
 *   case piece flag <BR>
 *   location no.<BR>
 *   packed qty per case <BR>
 *   packed qty per piece <BR>
 *   available case qty((stock qty - allocation qty)/packed qty per case) <BR>
 *   available piece qty((stock qty - allocation qty)%packed qty per case) <BR>
 *   Case ITF <BR>
 *   bundle itf <BR>
 *   stock id <BR>
 *   update date/time <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/08</TD><TD>toda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:33:42 $
 * @author  $Author: suresh $
 */
public class AsNoPlanRetrievalBusiness extends AsNoPlanRetrieval implements WMSConstants
{
	//#CM35906
	// Class fields --------------------------------------------------

	//#CM35907
	// ViewState use
	//#CM35908
	/**
	 * ViewState use:Consignor code
	 */
	private static final String VSTCONSIGNOR = "CONSIGNOR_CODE";
	
	//#CM35909
	/**
	 * ViewState use:Item code
	 */
	private static final String VSTITEMCODE = "ITEM_CODE";
	
	//#CM35910
	/**
	 * ViewState use:Case/Piece flag
	 */
	private static final String VSTCASEPIECE = "CASE_PIECE_FLAG";
	
	//#CM35911
	/**
	 * ViewState use:Start Location
	 */
	private static final String VSTLOCATIONFROM = "LOCATION_FROM";
	
	//#CM35912
	/**
	 * ViewState use:end location
	 */
	private static final String VSTLOCATIONTO = "LOCATION_TO";
	
	//#CM35913
	/**
	 * ViewState use:Case ITF
	 */
	private static final String VSTCASEITF = "CASE_ITF";
	
	//#CM35914
	/**
	 * ViewState use:Bundle ITF
	 */
	private static final String VSTBUNDLEITF = "BUNDLE_ITF";

	//#CM35915
	/**
	 * Pulldown Retrieval
	 */
	private static final String STATION_RETRIEVAL = "11";

	//#CM35916
	// Class variables -----------------------------------------------

	//#CM35917
	// Class method --------------------------------------------------

	//#CM35918
	// Constructors --------------------------------------------------

	//#CM35919
	// Public methods ------------------------------------------------

	//#CM35920
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
	 * 			item code[nil] <BR>
	 * 			case piece flag [ALL] <BR>
	 * 			start location[nil]<BR>
	 * 			end location[nil]<BR>
	 * 			Case ITF[nil]<BR>
	 * 			bundle itf[nil]<BR>
	 * 			customer code[nil]<BR>
	 * 			customer name[nil]<BR>
	 * 			Print No plan retrieval work list[true]<BR>
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
			//#CM35921
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM35922
			// fetch Http Locale
			Locale locale = this.getHttpRequest().getLocale();

			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			//#CM35923
			// pull down display data(storage area)
			String[] areaid = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);
			//#CM35924
			// pull down display data(workplace)
			String[] sagyoba = pull.getSagyobaPulldownData(conn, STATION_RETRIEVAL, true, "");
			//#CM35925
			// pull down display data(station)
			String[] stno = pull.getStationPulldownData(conn, STATION_RETRIEVAL, true, "");

			//#CM35926
			// set pulldown data
			PulldownHelper.setPullDown(pul_WareHouse, areaid);
			PulldownHelper.setLinkedPullDown(pul_WorkPlace, sagyoba);
			PulldownHelper.setLinkedPullDown(pul_Station, pul_WorkPlace, stno);

			//#CM35927
			// add child pull down
			pul_WareHouse.addChild(pul_WorkPlace);
			pul_WorkPlace.addChild(pul_Station);
			
			//#CM35928
			// initial display
			setFirstDisp();
		}
		catch (Exception ex)
		{
			//#CM35929
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
				//#CM35930
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

	//#CM35931
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
			//#CM35932
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);

			//#CM35933
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);

			//#CM35934
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}
		//#CM35935
		// Confirmation dialog when Start Retrieval button is pressed MSG-W0032=Do Retrieval??
		btn_RetrievalStart.setBeforeConfirm("MSG-W0032");

		//#CM35936
		// When the Clear list button is pressed, confirmation dialog MSG-W0012 = list input information is cleared. Is it ok?
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		//#CM35937
		// set the cursor in worker code input
		setFocus(txt_WorkerCode);
	}

	//#CM35938
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

		//#CM35939
		// fetch parameter selected from listbox
		//#CM35940
		// consignor code
		String consignorcode = param.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM35941
		// item code
		String itemcode = param.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
		//#CM35942
		// start location
		String startlocation = param.getParameter(ListAsLocationBusiness.STARTLOCATION_KEY);
		//#CM35943
		// end location
		String endlocation = param.getParameter(ListAsLocationBusiness.ENDLOCATION_KEY);
		//#CM35944
		// Case ITF
		String caseitf = param.getParameter(ListAsCaseItfBusiness.CASEITF_KEY);
		//#CM35945
		// bundle itf
		String bundleitf = param.getParameter(ListAsBundleItfBusiness.BUNDLEITF_KEY);

		//#CM35946
		// set the value if not empty
		//#CM35947
		// consignor code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM35948
		// item code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM35949
		// start location
		if (!StringUtil.isBlank(startlocation))
		{
			txt_StartLocation.setText(DisplayText.formatLocationnumber(startlocation));
		}
		//#CM35950
		// end location
		if (!StringUtil.isBlank(endlocation))
		{
			txt_EndLocation.setText(DisplayText.formatLocationnumber(endlocation));
		}
		//#CM35951
		// Case ITF
		if (!StringUtil.isBlank(caseitf))
		{
			txt_CaseItf.setText(caseitf);
		}
		//#CM35952
		// bundle itf
		if (!StringUtil.isBlank(bundleitf))
		{
			txt_BundleItf.setText(bundleitf);
		}
	}

	//#CM35953
	// Package methods -----------------------------------------------

	//#CM35954
	// Protected methods ---------------------------------------------

	//#CM35955
	// Private methods -----------------------------------------------

	//#CM35956
	/**
	 * This method initializes screen<BR>
	 * <BR>
	 * overview :: Clear input area and nullify the button of filtering area.<BR>
	 * and set the cursor in worker code<BR>
	 * <DIR>
	 * 		[parameter] <BR>
	 * 		<DIR>
	 * 			item name[initial value] <BR>
	 * 			<DIR>
	 * 				worker code[nil]<BR>
	 * 				password[nil]<BR>
	 * 				consignor code[if search results a single consignor, display it] <BR>
	 * 				item code[nil] <BR>
	 * 				case piece flag [ALL] <BR>
	 * 				start location[nil]<BR>
	 * 				end location[nil]<BR>
	 * 				Case ITF[nil]<BR>
	 * 				bundle itf[nil]<BR>
	 * 				customer code[nil]<BR>
	 * 				customer name[nil]<BR>
	 * 				Print No plan retrieval work list[true]<BR>
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
			//#CM35957
			// set the cursor in worker code input
			setFocus(txt_WorkerCode);

			//#CM35958
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new AsNoPlanRetrievalSCH();

			AsScheduleParameter param = (AsScheduleParameter) schedule.initFind(conn, null);

			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				//#CM35959
				// display customer code in the init screen, when there is single record
				txt_ConsignorCode.setText(param.getConsignorCode());
			}

			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				//#CM35960
				// display customer code in the init screen, when there is single record
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
			else
			{
				txt_ConsignorCode.setText("");
			}

			//#CM35961
			// item code
			txt_ItemCode.setText("");

			//#CM35962
			// select "ALL" for case piece flag
			rdo_Cpf_All.setChecked(true);
			rdo_Cpf_Case.setChecked(false);
			rdo_Cpf_Piece.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);

			//#CM35963
			// start location
			txt_StartLocation.setText("");

			//#CM35964
			// end location
			txt_EndLocation.setText("");

			//#CM35965
			// Case ITF
			txt_CaseItf.setText("");
			//#CM35966
			// bundle itf
			txt_BundleItf.setText("");
			//#CM35967
			// customer code
			txt_CustomerCode.setText("");
			//#CM35968
			// customer name
			txt_CustomerName.setText("");
			
			//#CM35969
			// Select the picking plan list print option
			chk_RetrievalPlanData.setChecked(true);

			//#CM35970
			// disable button click
			//#CM35971
			// Picking start button
			btn_RetrievalStart.setEnabled(false);
			//#CM35972
			// Picking qty clear button
			btn_RetrievalQtyClear.setEnabled(false);
			//#CM35973
			// Select all button
			btn_AllCheck.setEnabled(false);
			//#CM35974
			// Cancel all button
			btn_allCheckClear.setEnabled(false);
			//#CM35975
			// list clear button
			btn_ListClear.setEnabled(false);
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM35976
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

	//#CM35977
	/**
	 * The method of filtering and setting the value in the area. <BR>
	 * <BR>
	 * overview ::Filter and set the value in the area. <BR>
	 * and Make the button of the filtering area effective. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set data in the every particular item of the list cell. <BR>
	 * 		2.Edit data for the balloon display. <BR>
	 * </DIR>
	 * <DIR>
	 * 		[Concealment item]
	 * 		<DIR>
	 * 			0.case piece flag<BR>
	 * 			1.stock id<BR>
	 * 			2.update date/time<BR>
	 * 			5.location no.<BR>
	 *	 	</DIR>
	 * </DIR>
	 * <DIR>
	 * 		[list cell line no. list]
	 * 		<DIR>
	 * 			0.Concealment item<BR>
	 * 			1.item code <BR>
	 * 			2.classification <BR>
	 * 			3.Retrieval location <BR>
	 * 			4.packed qty per case <BR>
	 * 			5.available case qty <BR>
	 * 			6.picking case qty <BR>
	 * 			7.All qty checkbox <BR>
	 * 			8.expiry date <BR>
	 * 			9.Case ITF <BR>
	 * 			10.item name <BR>
	 * 			11.packed qty per piece <BR>
	 * 			12.available piece qty <BR>
	 * 			13.Retrieval piece qty <BR>
	 * 			14.bundle itf <BR>
	 *	 	</DIR>
	 * </DIR>
	 * <BR>
	 * @param listParams Set parameter.
	 * @throws Exception report all the exceptions
	 */
	private void setList(Parameter[] listParams) throws Exception
	{
		//#CM35978
		// eliminate all lines
		lst_NoPlanRetrieval.clearRow();

		AsScheduleParameter[] viewParam = (AsScheduleParameter[]) listParams;

		//#CM35979
		// item name
		String label_itemname = DisplayText.getText("LBL-W0103");
		//#CM35980
		// Case ITF
		String label_caseitf = DisplayText.getText("LBL-W0010");
		//#CM35981
		// Bundle ITF
		String label_bundleitf = DisplayText.getText("LBL-W0006");

		for (int i = 0; i < viewParam.length; i++)
		{
			//#CM35982
			// add row
			lst_NoPlanRetrieval.setCurrentRow(i + 1);
			lst_NoPlanRetrieval.addRow();

			lst_NoPlanRetrieval.setValue(1, viewParam[i].getItemCode());
			lst_NoPlanRetrieval.setValue(2, viewParam[i].getCasePieceFlagNameDisp());
			lst_NoPlanRetrieval.setValue(3, DisplayText.formatLocation(viewParam[i].getLocationNo()));
			lst_NoPlanRetrieval.setValue(9, viewParam[i].getITF());
			lst_NoPlanRetrieval.setValue(10, viewParam[i].getItemName());
			lst_NoPlanRetrieval.setValue(14, viewParam[i].getBundleITF());

			//#CM35983
			// Convert it into the character string that the comma is edited from a numeric type. 
			lst_NoPlanRetrieval.setValue(
				4,
				WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));
			lst_NoPlanRetrieval.setValue(
				5,
				WmsFormatter.getNumFormat(viewParam[i].getAllocateCaseQty()));
			lst_NoPlanRetrieval.setValue(
				11,
				WmsFormatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
			lst_NoPlanRetrieval.setValue(
				12,
				WmsFormatter.getNumFormat(viewParam[i].getAllocatePieceQty()));

			//#CM35984
			// update date/time
			String lastupdate = WmsFormatter.getTimeStampString(viewParam[i].getLastUpdateDate());

			//#CM35985
			// Set Concealment item
			List list = new Vector();
			list.add(viewParam[i].getCasePieceFlag());
			list.add(viewParam[i].getStockId());
			list.add(lastupdate);
			list.add("");
			list.add("");
			list.add(viewParam[i].getLocationNo());
			lst_NoPlanRetrieval.setValue(0, CollectionUtils.getConnectedString(list));

			//#CM35986
			// The blank is set in the Retrieval case qty and Retrieval piece qty. 
			lst_NoPlanRetrieval.setValue(6, (""));
			lst_NoPlanRetrieval.setValue(13, (""));

			//#CM35987
			// Set the expiry date.
			lst_NoPlanRetrieval.setValue(8, viewParam[i].getUseByDate());

			//#CM35988
			// Set the tool tip.
			ToolTipHelper toolTip = new ToolTipHelper();
			//#CM35989
			// item name
			toolTip.add(label_itemname, viewParam[i].getItemName());
			//#CM35990
			// Case ITF			
			toolTip.add(label_caseitf, viewParam[i].getITF());
			//#CM35991
			// Bundle ITF		
			toolTip.add(label_bundleitf, viewParam[i].getBundleITF());

			lst_NoPlanRetrieval.setToolTip(i + 1, toolTip.getText());

		}

		//#CM35992
		// enable button
		//#CM35993
		// Start Retrieval button
		btn_RetrievalStart.setEnabled(true);
		//#CM35994
		// Picking qty clear button
		btn_RetrievalQtyClear.setEnabled(true);
		//#CM35995
		// Select all button
		btn_AllCheck.setEnabled(true);
		//#CM35996
		// Cancel all button
		btn_allCheckClear.setEnabled(true);
		//#CM35997
		// list clear button
		btn_ListClear.setEnabled(true);
	}

	//#CM35998
	/**
	 * input check.<BR>
	 * <BR>
	 * Abstract :Set the message, and return false when abnormality is found. <BR>
	 * <BR>
	 * @return input check result(true:Normal false:Abnormal)
	 */
	private boolean checkContainNgText()
	{
		WmsCheckker checker = new WmsCheckker();

		//#CM35999
		// worker code
		if (!checker.checkContainNgText(txt_WorkerCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}

		//#CM36000
		// password
		if (!checker.checkContainNgText(txt_Password))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}

		//#CM36001
		// consignor code
		if (!checker.checkContainNgText(txt_ConsignorCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		//#CM36002
		// item code
		if (!checker.checkContainNgText(txt_ItemCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}

		//#CM36003
		// Case ITF
		if (!checker.checkContainNgText(txt_CaseItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		//#CM36004
		// bundle itf
		if (!checker.checkContainNgText(txt_BundleItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		//#CM36005
		// customer code
		if (!checker.checkContainNgText(txt_CustomerCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		//#CM36006
		// customer name
		if (!checker.checkContainNgText(txt_CustomerName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		return true;
	}

	//#CM36007
	/**
	 * Input check of item of expiry date of filtering area.<BR>
	 * <BR>
	 * Abstract :Set the message, and return false when abnormality is found. <BR>
	 * <BR>
	 * @param rowNo preset area line no.
	 * @return input check result(true:Normal false:Abnormal)
	 */
	private boolean checkContainNgText(int rowNo)
	{
		WmsCheckker checker = new WmsCheckker();

		lst_NoPlanRetrieval.setCurrentRow(rowNo);

		if(!checker.checkContainNgText(
				lst_NoPlanRetrieval.getValue(8) ,
				rowNo,
				lst_NoPlanRetrieval.getListCellColumn(8).getResourceKey() )
				)
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		return true;
	}
	
	//#CM36008
	// Event handler methods -----------------------------------------
	//#CM36009
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

	//#CM36010
	/** 
	 * called when "consignor code" search button is clicked<BR>
	 * <BR>
	 * overview ::set search condition in parameter and display the search results in consignor list box<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *    		warehouse <BR>
	 *       	consignor code <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM36011
		// set search conditions to consignor search screen
		ForwardParameters param = new ForwardParameters();
		//#CM36012
		// Warehouse No.
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM36013
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM36014
		// search flag
		param.setParameter(ListAsConsignorBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);
		//#CM36015
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsconsignor/ListAsConsignor.do", param, "/progress.do");
	}

	//#CM36016
	/** 
	 * This process is called upon clicking item code search button<BR>
	 * <BR>
	 * overview ::set search condition in parameter and display the item code list box<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *    		warehouse <BR>
	 *       	consignor code <BR>
	 *       	item code <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM36017
		// Set the search conditions to item search screen
		ForwardParameters param = new ForwardParameters();
		//#CM36018
		// Warehouse No.
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM36019
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM36020
		// item code
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM36021
		// search flag
		param.setParameter(ListAsItemBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);
		//#CM36022
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsitem/ListAsItem.do", param, "/progress.do");
	}

	//#CM36023
	/** 
	 * called when "start location" button is clicked<BR>
	 * <BR>
	 * overview ::set search condition in parameter and display the search results in a list box<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       warehouse <BR>
	 *       consignor code <BR>
	 *       item code <BR>
	 * 		 case piece flag <BR>
	 *       start location <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_PSStartLocation_Click(ActionEvent e) throws Exception
	{
		//#CM36024
		// set search conditions in location search screen
		ForwardParameters param = new ForwardParameters();
		//#CM36025
		// Warehouse No.
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM36026
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM36027
		// item code
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM36028
		// case piece flag
		//#CM36029
		// All
		if (rdo_Cpf_All.getChecked())
		{
			param.setParameter(
				ListAsLocationBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM36030
		// Case
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setParameter(
				ListAsLocationBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM36031
		// Piece
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setParameter(
				ListAsLocationBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM36032
		// None
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setParameter(
				ListAsLocationBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_NOTHING);
		}

		//#CM36033
		// start location
		if (!StringUtil.isBlank(txt_StartLocation.getString()))
		{
			String w_stlocation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_StartLocation.getString());
			param.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, w_stlocation);
		}
		else
		{
			param.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, txt_StartLocation.getString());
		}

		//#CM36034
		// start flag
		param.setParameter(
			ListAsLocationBusiness.RANGELOCATION_KEY,
			AsScheduleParameter.RANGE_START);
		//#CM36035
		// search flag
		param.setParameter(ListAsLocationBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);
		//#CM36036
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrslocation/ListAsLocation.do", param, "/progress.do");
	}

	//#CM36037
	/** 
	 * called when "end location" button is clicked<BR>
	 * <BR>
	 * overview ::set search condition in parameter and display the search results in a list box<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       warehouse <BR>
	 *       consignor code <BR>
	 *       item code <BR>
	 * 		 case piece flag <BR>
	 *       end location <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_PSEndLocation_Click(ActionEvent e) throws Exception
	{
		//#CM36038
		// set search conditions in location search screen
		ForwardParameters param = new ForwardParameters();
		//#CM36039
		// Warehouse No.
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM36040
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM36041
		// item code
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM36042
		// case piece flag
		//#CM36043
		// All
		if (rdo_Cpf_All.getChecked())
		{
			param.setParameter(
				ListAsLocationBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM36044
		// Case
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setParameter(
				ListAsLocationBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM36045
		// Piece
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setParameter(
				ListAsLocationBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM36046
		// None
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setParameter(
				ListAsLocationBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_NOTHING);
		}

		//#CM36047
		// end location
		if (!StringUtil.isBlank(txt_EndLocation.getString()))
		{
			String w_enlocation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_EndLocation.getString());
			param.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, w_enlocation);
		}
		else
		{
			param.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getString());
		}

		//#CM36048
		// end flag
		param.setParameter(ListAsLocationBusiness.RANGELOCATION_KEY, AsScheduleParameter.RANGE_END);

		//#CM36049
		// search flag
		param.setParameter(ListAsLocationBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);

		//#CM36050
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrslocation/ListAsLocation.do", param, "/progress.do");
	}

	//#CM36051
	/** 
	 * It is called when the retrieval button of Case ITF is pressed.<BR>
	 * <BR>
	 * overview ::Display the Case ITF list box by the set search condition in parameter and the search condition.<BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       warehouse <BR>
	 *       consignor code <BR>
	 *       item code <BR>
	 * 		 case piece flag <BR>
	 *       start location <BR>
	 *       end location <BR>
	 *       Case ITF <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_PSCaseItf_Click(ActionEvent e) throws Exception
	{
		//#CM36052
		// Set the search condition on the Case ITF retrieval screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM36053
		// Warehouse No.
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM36054
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM36055
		// item code
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());

		//#CM36056
		// case piece flag
		//#CM36057
		// All
		if (rdo_Cpf_All.getChecked())
		{
			param.setParameter(
				ListAsCaseItfBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM36058
		// Case
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setParameter(
				ListAsCaseItfBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM36059
		// Piece
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setParameter(
				ListAsCaseItfBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM36060
		// None
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setParameter(
				ListAsCaseItfBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_NOTHING);
		}

		//#CM36061
		// start location
		if (!StringUtil.isBlank(txt_StartLocation.getString()))
		{
			String w_stlocation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_StartLocation.getString());
			param.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, w_stlocation);
		}
		else
		{
			param.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, txt_StartLocation.getString());
		}

		//#CM36062
		// end location
		if (!StringUtil.isBlank(txt_EndLocation.getString()))
		{
			String w_enlocation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_EndLocation.getString());
			param.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, w_enlocation);
		}
		else
		{
			param.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getString());
		}

		//#CM36063
		// Case ITF
		param.setParameter(ListAsCaseItfBusiness.CASEITF_KEY, txt_CaseItf.getText());

		//#CM36064
		// search flag
		param.setParameter(ListAsCaseItfBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);

		//#CM36065
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrscaseitf/ListAsCaseItf.do", param, "/progress.do");
	}

	//#CM36066
	/** 
	 * It is called when the retrieval button of Bundle ITF is pressed.<BR>
	 * <BR>
	 * overview :: Display the Bundle ITF list box by the set search condition in parameter and the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [parameter]<BR>
	 *    <DIR>
	 *       warehouse <BR>
	 *       consignor code <BR>
	 *       item code <BR>
	 * 		 case piece flag <BR>
	 *       start location <BR>
	 *       end location <BR>
	 *       Case ITF <BR>
	 *       bundle itf <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_PSBundleItf_Click(ActionEvent e) throws Exception
	{
		//#CM36067
		// Set the search condition on the search bundle ITF screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM36068
		// Warehouse No.
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM36069
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM36070
		// item code
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());

		//#CM36071
		// case piece flag
		//#CM36072
		// All
		if (rdo_Cpf_All.getChecked())
		{
			param.setParameter(
				ListAsBundleItfBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_ALL);
		}
		//#CM36073
		// Case
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setParameter(
				ListAsBundleItfBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_CASE);
		}
		//#CM36074
		// Piece
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setParameter(
				ListAsBundleItfBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_PIECE);
		}
		//#CM36075
		// None
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setParameter(
				ListAsBundleItfBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_NOTHING);
		}

		//#CM36076
		// start location
		if (!StringUtil.isBlank(txt_StartLocation.getString()))
		{
			String w_stlocation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_StartLocation.getString());
			param.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, w_stlocation);
		}
		else
		{
			param.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, txt_StartLocation.getString());
		}

		//#CM36077
		// end location
		if (!StringUtil.isBlank(txt_EndLocation.getString()))
		{
			String w_enlocation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_EndLocation.getString());
			param.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, w_enlocation);
		}
		else
		{
			param.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getString());
		}

		//#CM36078
		// Case ITF
		param.setParameter(ListAsCaseItfBusiness.CASEITF_KEY, txt_CaseItf.getText());
		//#CM36079
		// bundle itf
		param.setParameter(ListAsBundleItfBusiness.BUNDLEITF_KEY, txt_BundleItf.getText());

		//#CM36080
		// search flag
		param.setParameter(ListAsBundleItfBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);

		//#CM36081
		// in process screen -> result screen
		redirect("/asrs/listbox/listasrsbundleitf/ListAsBundleItf.do", param, "/progress.do");
	}

	//#CM36082
	/** 
	 * It is called when the display button is pressed.<BR>
	 * <BR>
	 * overview :: Retrieve stock info contingent on the input item of input area, and display it after filtering. <BR>
	 * <BR>
	 * <DIR>
	 *   1.Check the input item of input area. (mandatory input, character count, character attribute)<BR>
	 *   2.Maintain ViewState information. <BR>
	 *   3.start scheduler<BR>
	 *   4.Display it in the filtering area. <BR>
	 *   5.Make all buttons of the filtering area effective. <BR>
	 *   6.save input area contents<BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM36083
			// Clear the filtering area when there is an input error.
			btn_ListClear_Click(e);

			//#CM36084
			// input check
			txt_WorkerCode.validate();
			txt_Password.validate();
			txt_ConsignorCode.validate();

			//#CM36085
			// pattern matching character
			txt_ItemCode.validate(false);
			txt_StartLocation.validate(false);
			txt_EndLocation.validate(false);
			txt_CaseItf.validate(false);
			txt_BundleItf.validate(false);
			txt_CustomerCode.validate(false);
			txt_CustomerName.validate(false);

			//#CM36086
			// Input character check for eWareNavi
			if (!checkContainNgText())
			{
				return;
			}
			
			String startLocation = txt_StartLocation.getText();
			String endLocation = txt_EndLocation.getText();

			//#CM36087
			// Start Location is lower than end location.
			if (!StringUtil.isBlank(txt_StartLocation.getText())
				&& !StringUtil.isBlank(txt_EndLocation.getText()))
			{
				if (startLocation.compareTo(endLocation) > 0)
				{
					//#CM36088
					// 6023057 = Input the value {0} more than {1}. 
					message.setMsgResourceKey(
						"6023057"
							+ Message.MSG_DELIM
							+ DisplayText.getText(lbl_StartLocation.getResourceKey())
							+ Message.MSG_DELIM
							+ DisplayText.getText(lbl_EndLocation.getResourceKey()));
					return;
				}
			}

			//#CM36089
			// set schedule parameter
			AsScheduleParameter param = new AsScheduleParameter();
			//#CM36090
			// worker code
			param.setWorkerCode(txt_WorkerCode.getText());
			//#CM36091
			// password
			param.setPassword(txt_Password.getText());
			//#CM36092
			// Warehouse No.
			param.setAreaNo(pul_WareHouse.getSelectedValue());
			//#CM36093
			// consignor code
			param.setConsignorCodeDisp(txt_ConsignorCode.getText());
			//#CM36094
			// item code
			param.setItemCodeDisp(txt_ItemCode.getText());

			//#CM36095
			// case piece flag
			if (rdo_Cpf_All.getChecked())
			{
				//#CM36096
				// All
				param.setCasePieceFlagDisp(AsScheduleParameter.CASEPIECE_FLAG_ALL);
			}
			else if (rdo_Cpf_Case.getChecked())
			{
				//#CM36097
				// Case
				param.setCasePieceFlagDisp(AsScheduleParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_Cpf_Piece.getChecked())
			{
				//#CM36098
				// Piece
				param.setCasePieceFlagDisp(AsScheduleParameter.CASEPIECE_FLAG_PIECE);
			}
			else if (rdo_Cpf_AppointOff.getChecked())
			{
				//#CM36099
				// Unspecified
				param.setCasePieceFlagDisp(AsScheduleParameter.CASEPIECE_FLAG_NOTHING);
			}

			//#CM36100
			// start location
			if (!StringUtil.isBlank(txt_StartLocation.getText()))
			{
				String startlocation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_StartLocation.getString());
				param.setFromLocationNo(startlocation);
			}
			else
			{
				param.setFromLocationNo(txt_StartLocation.getText());
			}

			//#CM36101
			// end location
			if (!StringUtil.isBlank(txt_EndLocation.getText()))
			{
				String endlocation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_EndLocation.getString());
				param.setToLocationNo(endlocation);
			}
			else
			{
				param.setToLocationNo(txt_EndLocation.getText());
			}

			//#CM36102
			// Case ITF
			param.setITFDisp(txt_CaseItf.getText());
			//#CM36103
			// bundle itf
			param.setBundleITFDisp(txt_BundleItf.getText());
			//#CM36104
			// customer code
			param.setCustomerCode(txt_CustomerCode.getText());
			//#CM36105
			// customer name
			param.setCustomerName(txt_CustomerName.getText());

			//#CM36106
			// Preservation to ViewState(For re-retrieval after processing)
			//#CM36107
			// consignor code
			this.getViewState().setString(VSTCONSIGNOR, param.getConsignorCodeDisp());
			//#CM36108
			// item code
			this.getViewState().setString(VSTITEMCODE, param.getItemCodeDisp());
			//#CM36109
			// Case piece flag
			this.getViewState().setString(VSTCASEPIECE, param.getCasePieceFlagDisp());
			//#CM36110
			// start location
			this.getViewState().setString(VSTLOCATIONFROM, param.getFromLocationNo());
			//#CM36111
			// end location
			this.getViewState().setString(VSTLOCATIONTO, param.getToLocationNo());
			//#CM36112
			// Case ITF
			this.getViewState().setString(VSTCASEITF, param.getITFDisp());
			//#CM36113
			// bundle itf
			this.getViewState().setString(VSTBUNDLEITF, param.getBundleITFDisp());

			WmsScheduler schedule = new AsNoPlanRetrievalSCH();
			AsScheduleParameter[] viewParam = (AsScheduleParameter[]) schedule.query(conn, param);

			if (viewParam == null || viewParam.length == 0)
			{
				//#CM36114
				// set message
				message.setMsgResourceKey(schedule.getMessage());

				return;
			}

			//#CM36115
			// set listcell
			setList(viewParam);

			//#CM36116
			// set message
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
				//#CM36117
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

	//#CM36118
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
	 * 				item code[nil] <BR>
	 * 				case piece flag [ALL] <BR>
	 * 				start location[nil]<BR>
	 * 				end location[nil]<BR>
	 * 				Case ITF[nil]<BR>
	 * 				bundle itf[nil]<BR>
	 * 				customer code[nil]<BR>
	 * 				customer name[nil]<BR>
	 * 				Print No plan retrieval work list[true]<BR>
	 * 	 		</DIR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM36119
			// set the cursor in worker code input
			setFocus(txt_WorkerCode);

			//#CM36120
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new AsNoPlanRetrievalSCH();

			AsScheduleParameter param = (AsScheduleParameter) schedule.initFind(conn, null);

			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				//#CM36121
				// display customer code in the init screen, when there is single record
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
			else
			{
				txt_ConsignorCode.setText("");
			}

			//#CM36122
			// item code
			txt_ItemCode.setText("");

			//#CM36123
			// select "ALL" for case piece flag
			rdo_Cpf_All.setChecked(true);
			rdo_Cpf_Case.setChecked(false);
			rdo_Cpf_Piece.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);

			//#CM36124
			// start location
			txt_StartLocation.setText("");

			//#CM36125
			// end location
			txt_EndLocation.setText("");

			//#CM36126
			// Case ITF
			txt_CaseItf.setText("");
			//#CM36127
			// bundle itf
			txt_BundleItf.setText("");
			//#CM36128
			// customer code
			txt_CustomerCode.setText("");
			//#CM36129
			// customer name
			txt_CustomerName.setText("");
			
			//#CM36130
			// Select the picking plan list print option
			chk_RetrievalPlanData.setChecked(true);

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM36131
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

	//#CM36132
	/** 
	 * It is called when the Start Retrieval button is pressed.<BR>
	 * <BR>
	 * overview :: Set the ASRS schedule going out warehouse by information on the filtering area. <BR>
	 * <BR>
	 * <DIR>
	 *	  1.set cursor in worker code<BR>
	 *    2.Display the dialog box, and confirm whether to register. <BR>
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
	 * 						worker code <BR>
	 * 						password <BR>
	 * 						pulldown.warehouse<BR>
	 * 						pulldown.receiving station<BR>
	 * 						customer code <BR>
	 * 						customer name <BR>
	 * 						No plan retrieval work list print flag<BR>
	 *						preset area.item code <BR>
	 *						preset area.item name <BR>
	 *						preset area.case piece flag <BR>
	 *						preset area.location no.<BR>
	 *						preset area.packed qty per case <BR>
	 *						preset area.packed qty per piece <BR>
	 *						preset area.available case qty<BR>
	 *						preset area.available piece qty<BR>
	 *						preset area.Retrieval case qty <BR>
	 *						preset area.Retrieval piece qty <BR>
	 *						preset area.Case ITF <BR>
	 *						preset area.bundle itf <BR>
	 *						preset area.expiry date <BR>
	 *						preset area.All qty flag <BR>
	 *						preset area.stock id <BR>
	 *						preset area.update date/time <BR>
	 *						terminal no.<BR>
	 *						preset area line no.<BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.Clear the Input area and filtering area when the result of the schedule is true. <BR>
	 *				3.Display filtered information again. <BR>
	 *              4.When the filtering information becomes 0, all buttons in filtering area cannot be used.<BR>
	 *    			Output the message acquired from the schedule to the screen when false. <BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_RetrievalStart_Click(ActionEvent e) throws Exception
	{
		//#CM36133
		// input check
		txt_WorkerCode.validate();
		txt_Password.validate();

		txt_CustomerCode.validate(false);
		txt_CustomerName.validate(false);
		//#CM36134
		// Input character check for eWareNavi
		if (!checkContainNgText())
		{
			return;
		}
		
		//#CM36135
		//fetch max. line no
		int index = lst_NoPlanRetrieval.getMaxRows();

		for (int i = 1; i < index; i++)
		{
			try
			{
				//#CM36136
				// specify line
				lst_NoPlanRetrieval.setCurrentRow(i);
				//#CM36137
				// Check pattern matching character of specified row (expiry date). 
				lst_NoPlanRetrieval.validate(8, false);
				

				//#CM36138
				// Input character check for eWareNavi
				if (!checkContainNgText(i))
				{
					return;
				}
			}
			catch (ValidateException ve)
			{
				//#CM36139
				// set message
				String errorMessage =
					MessageResources.getText(ve.getErrorNo(), ve.getBinds().toArray());
				//#CM36140
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
				//#CM36141
				// specify line
				lst_NoPlanRetrieval.setCurrentRow(i);

				//#CM36142
				// Check it only when the check does not start picking all qty.
				if (lst_NoPlanRetrieval.getChecked(7) == false)
				{
					//#CM36143
					// Set the value in the parameter when parameters connected from the list cell are acquired, and there are an input value and a difference. 
					//#CM36144
					// picking case qty
					if (CollectionUtils
						.getString(3, lst_NoPlanRetrieval.getValue(0))
						.equals(lst_NoPlanRetrieval.getValue(6)))
					{
						//#CM36145
						// Retrieval piece qty
						if (CollectionUtils
							.getString(4, lst_NoPlanRetrieval.getValue(0))
							.equals(lst_NoPlanRetrieval.getValue(13)))
						{
							continue;
						}
					}
				}

				//#CM36146
				// set schedule parameter
				AsScheduleParameter param = new AsScheduleParameter();
				//#CM36147
				// area no.
				param.setAreaNo(pul_WareHouse.getSelectedValue());
				//#CM36148
				// worker code
				param.setWorkerCode(txt_WorkerCode.getText());
				//#CM36149
				// password
				param.setPassword(txt_Password.getText());
				//#CM36150
				// customer code
				param.setCustomerCode(txt_CustomerCode.getText());
				//#CM36151
				// customer name
				param.setCustomerName(txt_CustomerName.getText());
				//#CM36152
				// receiving station
				param.setToStationNo(pul_Station.getSelectedValue());
				if( pul_Station.getSelectedValue().equals(Station.AUTO_SELECT_STATIONNO))
				{
					//#CM36153
					// Set workplace when [Automatic division] is specified for station. 
					param.setSagyoba(pul_WorkPlace.getSelectedValue());
				}

				//#CM36154
				// terminal no.
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());

				//#CM36155
				// No plan retrieval work list print
				param.setListFlg(chk_RetrievalPlanData.getChecked());
				param.setItemCode(lst_NoPlanRetrieval.getValue(1));

				//#CM36156
				// Convert it into a numeric type from the numerical value from which the comma is edited. 
				param.setEnteringQty(WmsFormatter.getInt(lst_NoPlanRetrieval.getValue(4)));
				param.setAllocateCaseQty(WmsFormatter.getInt(lst_NoPlanRetrieval.getValue(5)));

				//#CM36157
				// All qty picking to the number of pickings when the check starts All qty retrieval. 
				if (lst_NoPlanRetrieval.getChecked(7) == true)
				{
					param.setRetrievalCaseQty(WmsFormatter.getInt(lst_NoPlanRetrieval.getValue(5)));
				}
				else
				{
					if (lst_NoPlanRetrieval.getValue(6).equals(""))
					{
						param.setRetrievalCaseQty(0);
					}
					else
					{
						param.setRetrievalCaseQty(WmsFormatter.getInt(lst_NoPlanRetrieval.getValue(6)));
					}
				}

				param.setTotalFlag(lst_NoPlanRetrieval.getChecked(7));
				param.setUseByDate(lst_NoPlanRetrieval.getValue(8));
				param.setITF(lst_NoPlanRetrieval.getValue(9));
				param.setItemName(lst_NoPlanRetrieval.getValue(10));

				//#CM36158
				// Convert it into a numeric type from the numerical value from which the comma is edited. 
				param.setBundleEnteringQty(WmsFormatter.getInt(lst_NoPlanRetrieval.getValue(11)));
				param.setAllocatePieceQty(WmsFormatter.getInt(lst_NoPlanRetrieval.getValue(12)));

				//#CM36159
				// All qty picking to the number of pickings when the check starts All qty retrieval. 
				if (lst_NoPlanRetrieval.getChecked(7) == true)
				{
					param.setRetrievalPieceQty(
							WmsFormatter.getInt(lst_NoPlanRetrieval.getValue(12)));
				}
				else
				{
					if (lst_NoPlanRetrieval.getValue(13).equals(""))
					{
						param.setRetrievalPieceQty(0);
					}
					else
					{
						param.setRetrievalPieceQty(
							WmsFormatter.getInt(lst_NoPlanRetrieval.getValue(13)));
					}
				}

				param.setBundleITF(lst_NoPlanRetrieval.getValue(14));

				//#CM36160
				// HIDDEN item set(classification(0),stock id(1),update date/time(2))
				String hidden = lst_NoPlanRetrieval.getValue(0);
				param.setCasePieceFlag(CollectionUtils.getString(0, hidden));
				param.setStockId(CollectionUtils.getString(1, hidden));
				//#CM36161
				// update date/time
				//#CM36162
				// Convert it into the Date type from String type (YYYYMMDDHHMMSS). 
				param.setLastUpdateDate(
					WmsFormatter.getTimeStampDate(CollectionUtils.getString(2, hidden)));
				param.setLocationNo(CollectionUtils.getString(5, hidden));

				//#CM36163
				// save the line no.
				param.setRowNo(i);

				//#CM36164
				// The search condition of the re-retrieval after it begins to deliver it is set. 
				param.setConsignorCodeDisp(this.getViewState().getString(VSTCONSIGNOR));
				param.setItemCodeDisp(this.getViewState().getString(VSTITEMCODE));
				param.setCasePieceFlagDisp(this.getViewState().getString(VSTCASEPIECE));
				param.setFromLocationNo(this.getViewState().getString(VSTLOCATIONFROM));
				param.setToLocationNo(this.getViewState().getString(VSTLOCATIONTO));
				param.setITFDisp(this.getViewState().getString(VSTCASEITF));
				param.setBundleITFDisp(this.getViewState().getString(VSTBUNDLEITF));

				vecParam.addElement(param);
			}
			if (vecParam.size() <= 0)
			{
				//#CM36165
				// 6023154 = modification target data does not exist
				message.setMsgResourceKey("6023154");
				return;
			}

			AsScheduleParameter[] paramArray = new AsScheduleParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			WmsScheduler schedule = new AsNoPlanRetrievalSCH();
			//#CM36166
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			//#CM36167
			// start schedule
			AsScheduleParameter[] viewParam =
				(AsScheduleParameter[]) schedule.startSCHgetParams(conn, paramArray);

			if (viewParam == null)
			{
				conn.rollback();
				//#CM36168
				// set message
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.commit();

				//#CM36169
				// while sending picking send instruction, use RMI message for picking request
				SendRequestor req = new SendRequestor();
				req.retrieval();

				//#CM36170
				// set message
				message.setMsgResourceKey(schedule.getMessage());
				//#CM36171
				// Clear the filtering area when the object data is lost.
				btn_ListClear_Click(e);

				if (viewParam.length != 0)
				{
					//#CM36172
					// set listcell
					setList(viewParam);
				}

			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			conn.rollback();
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM36173
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

	//#CM36174
	/** 
	 * Called when Retrieval qty clear button is clicked <BR>
	 * <BR>
	 * overview ::Clear Retrieval case qty, Retrieval piece qty and all qty in filtering area.<BR>
	 * <BR>
	 * <DIR>
	 *     1.Clear Retrieval case qty, Retrieval piece qty and all qty in filtering area.<BR>
	 *     2.Make all qty of filtering as false.<BR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_RetrievalQtyClear_Click(ActionEvent e) throws Exception
	{
		//#CM36175
		// set listcell
		for (int i = 1; i < lst_NoPlanRetrieval.getMaxRows(); i++)
		{
			lst_NoPlanRetrieval.setCurrentRow(i);
			lst_NoPlanRetrieval.setValue(6, (""));
			lst_NoPlanRetrieval.setChecked(7, false);
			lst_NoPlanRetrieval.setValue(13, (""));
		}
	}

	//#CM36176
	/** 
	 * This is called when "Select All" button is clicked <BR>
	 * <BR>
	 * overview ::Make Status as All qty for all filtering information.<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_AllCheck_Click(ActionEvent e) throws Exception
	{
		//#CM36177
		// set listcell
		for (int i = 1; i < lst_NoPlanRetrieval.getMaxRows(); i++)
		{
			lst_NoPlanRetrieval.setCurrentRow(i);
			lst_NoPlanRetrieval.setChecked(7, true);
		}
	}

	//#CM36178
	/** 
	 * This is called when "All cancel" button is clicked <BR>
	 * <BR>
	 * overview ::Make Status as No check for all filtering information.<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_allCheckClear_Click(ActionEvent e) throws Exception
	{
		//#CM36179
		// set listcell
		for (int i = 1; i < lst_NoPlanRetrieval.getMaxRows(); i++)
		{
			lst_NoPlanRetrieval.setCurrentRow(i);
			lst_NoPlanRetrieval.setChecked(7, false);
		}
	}

	//#CM36180
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
	 *				do nothing<BR>
	 *			</DIR>
	 * 		[confirmation dialog OK]<BR>
	 *			<DIR>
	 *				1.Clear all filtered display information. <BR>
	 *				2.disable all the preset area buttons<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info 
	 * @throws Exception report all the exceptions 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		//#CM36181
		// eliminate all lines
		lst_NoPlanRetrieval.clearRow();

		//#CM36182
		// disable button click
		//#CM36183
		// Picking start button
		btn_RetrievalStart.setEnabled(false);
		//#CM36184
		// Picking qty clear button
		btn_RetrievalQtyClear.setEnabled(false);
		//#CM36185
		// Select all
		btn_AllCheck.setEnabled(false);
		//#CM36186
		// Unselect all
		btn_allCheckClear.setEnabled(false);
		//#CM36187
		// list clear button
		btn_ListClear.setEnabled(false);
	}

}
//#CM36188
//end of class
