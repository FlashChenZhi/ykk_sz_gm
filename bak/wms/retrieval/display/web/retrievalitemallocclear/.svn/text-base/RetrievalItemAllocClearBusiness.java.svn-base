// $Id: RetrievalItemAllocClearBusiness.java,v 1.2 2007/02/07 04:19:10 suresh Exp $

//#CM713363
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalitemallocclear;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitemlist.ListRetrievalItemListBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalItemAllocClearSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM713364
/**
 * Designer : Y.Osawa<BR>
 * Maker : Y.Osawa<BR>
 * <BR>
 * Allow this class to cancel the Item Picking Allocation. <BR>
 * Set the contents entered via screen, and<BR>
 *  allow the schedule to cancel the allocation of the Item Picking based on the parameter.<BR>
 * Receive the result from the schedule. Receive true if the process completed normally.<BR>
 * Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 * Output the message obtained from the schedule to the screen, as the schedule result. <BR>
 * Allow this screen to commit and roll back the transaction. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process by clicking "Display" button (<CODE>btn_PDisplay_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 * 	 Set the contents entered via screen, and
 *  allow the schedule to search for the data for display based on the parameter and display the result on the popup screen. <BR>
 *  <BR>
 *  [Parameter]  *Mandatory Input<BR>
 *  <DIR>
 * 		Worker Code* <BR>
 * 		 Password * <BR>
 * 		Consignor Code* <BR>
 * 		Planned Picking Date* <BR>
 * 		 Start Item Code  <BR>
 * 		 End Item Code  <BR>
 * 		 Case/Piece division * <BR>
 * 	</DIR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Setup" button (<CODE>btn_StorageStart_Click()</CODE> method)  <BR>
 * <BR>
 * <DIR>
 * Set the contents entered via screen, and<BR>
 *  allow the schedule to cancel the allocation of the Item Picking based on the parameter.<BR>
 * Receive the result from the schedule. Receive true if the process completed normally.<BR>
 * Or, receive false if the schedule failed to complete due to condition error etc. <BR>
 * Output the message obtained from the schedule to the screen, as the schedule result. <BR>
 * 	<BR>
 *   [Parameter]  *Mandatory Input <BR>
 *   <BR>
 *   <DIR>
 *   	Worker Code* <BR>
 *   	 Password * <BR>
 * 		Consignor Code* <BR>
 * 		Planned Picking Date* <BR>
 * 		 Start Item Code  <BR>
 * 		 End Item Code  <BR>
 * 		 Case/Piece division * <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/25</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:10 $
 * @author  $Author: suresh $
 */
public class RetrievalItemAllocClearBusiness extends RetrievalItemAllocClear implements WMSConstants
{
	//#CM713365
	// Class fields --------------------------------------------------

	//#CM713366
	// Class variables -----------------------------------------------

	//#CM713367
	// Class method --------------------------------------------------

	//#CM713368
	// Constructors --------------------------------------------------

	//#CM713369
	// Public methods ------------------------------------------------

	//#CM713370
	/**
	 * Initialize the screen. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			//#CM713371
			// Set the initial value for each input field. 
			txt_WorkerCode.setText("");
			txt_PassWord.setText("");

			//#CM713372
			// show the Initial Display. 
			setFirstDisp();
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//#CM713373
				// Close the connection. 
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM713374
	/**
	 * Returning from a popup window invokes this method.
	 * Override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE>. <BR>
	 * <BR>
	 * Summary: Obtains the Returned data in the Search screen and set it. <BR>
	 * <BR><DIR>
	 *      1. Obtain the value returned from the Search popup screen. <BR>
	 *      2. Set the screen if the value is not empty. <BR>
	 * <BR></DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		//#CM713375
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);		

		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM713376
		// Obtain the parameter selected in the listbox. 
		//#CM713377
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM713378
		// Planned Picking Date
		String retPlan = param.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM713379
		// Start Item Code 
		String startItemCode = param.getParameter(ListRetrievalItemBusiness.STARTITEMCODE_KEY);
		//#CM713380
		// End Item Code 
		String endItemCode = param.getParameter(ListRetrievalItemBusiness.ENDITEMCODE_KEY);

		//#CM713381
		// Set a value if not empty. 
		//#CM713382
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM713383
		// Planned Picking Date
		if (!StringUtil.isBlank(retPlan))
		{
			txt_RtrivlPlanDate.setDate(WmsFormatter.toDate(retPlan));
		}
		//#CM713384
		// Start Item Code 
		if (!StringUtil.isBlank(startItemCode))
		{
			txt_StartItemCode.setText(startItemCode);
		}
		//#CM713385
		// End Item Code 
		if (!StringUtil.isBlank(endItemCode))
		{
			txt_EndItemCode.setText(endItemCode);
		}
	}

	//#CM713386
	/**
	 * Invoke this before invoking each control event. <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM713387
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM713388
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM713389
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}

		//#CM713390
		// Dialog to confirm before clicking on "Commit" button: "Do you commit it?"
		btn_Setting.setBeforeConfirm("MSG-9000");
	}

	//#CM713391
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713392
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713393
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_AllocClear_Click(ActionEvent e) throws Exception
	{
	}

	//#CM713394
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713395
	/** 
	 * Clicking on the Menu button invokes this. <BR>
	 * <BR>
	 * Summary: Shifts to the Menu screen.  
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		//#CM713396
		// Shift to the Menu screen. 
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM713397
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713398
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713399
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713400
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713401
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM713402
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PassWord_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713403
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713404
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713405
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713406
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM713407
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713408
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713409
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713410
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713411
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM713412
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PS_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713413
	/** 
	 * Clicking on the Search Consignor button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for Consignor using the search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PS_ConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM713414
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM713415
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM713416
		// "Search" flag: Work 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM713417
		// For Item 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM713418
		// Allocation flag: Allocated 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.ALLOCATION_FLAG_KEY, RetrievalSupportParameter.ALLOCATION_COMPLETION);
		//#CM713419
		// Display the Search Consignor listbox.
		redirect("/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do", forwardParam, "/progress.do");
	}

	//#CM713420
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713421
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713422
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713423
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713424
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PS_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713425
	/** 
	 * Clicking on the Search Planned Picking Date button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Planned Picking Date using this search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  Planned Picking Date<BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PS_RetrievalPlanDate_Click(ActionEvent e) throws Exception
	{
		//#CM713426
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM713427
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM713428
		// Set the Planned Picking date. 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM713429
		// "Search" flag 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM713430
		// For Item 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM713431
		// Allocation flag: Allocated 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.ALLOCATION_FLAG_KEY, RetrievalSupportParameter.ALLOCATION_COMPLETION);
		//#CM713432
		// Display the listbox for searching for Planned Picking Date. 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", forwardParam, "/progress.do");
	}

	//#CM713433
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713434
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713435
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713436
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713437
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM713438
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PS_StartItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713439
	/** 
	 * StartClicking on the Search Item Code button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Item Code using this search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  Planned Picking Date<BR>
	 *  Start Item Code <BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PS_StartItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM713440
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM713441
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM713442
		// Set the Planned Picking date. 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM713443
		// Set the Start item code. 
		forwardParam.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_StartItemCode.getText());
		//#CM713444
		// "Search" flag 
		forwardParam.setParameter(ListRetrievalItemBusiness.SEARCHITEM_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM713445
		// Set the Start flag. 
		forwardParam.setParameter(ListRetrievalItemBusiness.RANGE_KEY, RetrievalSupportParameter.RANGE_START);
		//#CM713446
		// For Item 
		forwardParam.setParameter(ListRetrievalItemBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM713447
		// Allocation flag: Allocated 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.ALLOCATION_FLAG_KEY, RetrievalSupportParameter.ALLOCATION_COMPLETION);
		//#CM713448
		// Displays the listbox for Item Code. 
		redirect("/retrieval/listbox/listretrievalitem/ListRetrievalItem.do", forwardParam, "/progress.do");
	}

	//#CM713449
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713450
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713451
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713452
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM713453
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM713454
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM713455
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PS_EndItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713456
	/** 
	 * Clicking on the Search End Item Code button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Item Code using this search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  Planned Picking Date<BR>
	 *  End Item Code <BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PS_EndItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM713457
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM713458
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM713459
		// Set the Planned Picking date. 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM713460
		// Set the End item code. 
		forwardParam.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_EndItemCode.getText());
		//#CM713461
		// "Search" flag 
		forwardParam.setParameter(ListRetrievalItemBusiness.SEARCHITEM_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM713462
		// Set the Close flag. 
		forwardParam.setParameter(ListRetrievalItemBusiness.RANGE_KEY, RetrievalSupportParameter.RANGE_END);
		//#CM713463
		// For Item 
		forwardParam.setParameter(ListRetrievalItemBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM713464
		// Allocation flag: Allocated 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.ALLOCATION_FLAG_KEY, RetrievalSupportParameter.ALLOCATION_COMPLETION);
		//#CM713465
		// Displays the listbox for Item Code. 
		redirect("/retrieval/listbox/listretrievalitem/ListRetrievalItem.do", forwardParam, "/progress.do");
	}

	//#CM713466
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713467
	/**
	 * Clicking on the Display button invokes this.  <BR>
	 * Summary: Sets the field item entered in the Input area for the parameter and displays the listbox for Item Picking allocation work list in the different screen. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input <BR>
	 * <DIR>Consignor Code* <BR>
	 * Planned Picking Date* <BR>
	 * Start Item Code  <BR>
	 * End Item Code  <BR>
	 * Case/Piece division * <BR>
	 * </DIR></BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		//#CM713468
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();
		//#CM713469
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM713470
		// Set the Planned Picking date. 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM713471
		// Set the Start item code. 
		forwardParam.setParameter(ListRetrievalItemBusiness.STARTITEMCODE_KEY, txt_StartItemCode.getText());
		//#CM713472
		// Set the End item code. 
		forwardParam.setParameter(ListRetrievalItemBusiness.ENDITEMCODE_KEY, txt_EndItemCode.getText());

		//#CM713473
		// Case/Piece division 
		if (rdo_CpfAll.getChecked())
		{
			//#CM713474
			// All 
			forwardParam.setParameter(ListRetrievalItemListBusiness.CASEPIECEFLAG_KEY,
					RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
		}
		if(rdo_CpfCase.getChecked())
		{
			//#CM713475
			// Case 
			forwardParam.setParameter(ListRetrievalItemListBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		if(rdo_CpfPiece.getChecked())
		{
			//#CM713476
			// Piece 
			forwardParam.setParameter(ListRetrievalItemListBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		if(rdo_CpfAppointOff.getChecked())
		{
			//#CM713477
			// None 
			forwardParam.setParameter(ListRetrievalItemListBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM713478
		// "Search" flag 
		forwardParam.setParameter(ListRetrievalItemBusiness.SEARCHITEM_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);

		//#CM713479
		// Displays the listbox for Item Picking allocation work list. 
		redirect("/retrieval/listbox/listretrievalitemalloc/ListRetrievalItemAlloc.do", forwardParam, "/progress.do");
	}

	//#CM713480
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Setting_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713481
	/**
	 * Clicking on Commit button invokes this.  <BR>
	 * Summary: Set the contents entered via screen, and allow the schedule to cancel the allocation of the Item Picking based on the parameter.
	 * <DIR>
	 * 1. Set the cursor on the Worker code. <BR>
	 * 2. Check for input.  <BR>
	 * 3. Start the schedule.  <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Setting_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		
		//#CM713482
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
		
		//#CM713483
		// Check for mandatory input. 
		txt_WorkerCode.validate();
		txt_PassWord.validate();
		txt_ConsignorCode.validate();
		txt_RtrivlPlanDate.validate();
		
		//#CM713484
		// Pattern matching characters 
		txt_StartItemCode.validate(false);
		txt_EndItemCode.validate(false);

		try
		{			
			//#CM713485
			// Check for input. 
			if(!checkInputData())
			{
				return;
			}
			
			//#CM713486
			// for storing data to be updated 
			RetrievalSupportParameter[] param = null;

			//#CM713487
			// Set the data of the input field item. 
			param = setParam();

			//#CM713488
			// Obtain connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalItemAllocClearSCH();

			//#CM713489
			// Start the schedule. 
			if (schedule.startSCH(conn, param))
			{
				//#CM713490
				// If the result is true, commit it. 
				conn.commit();
			}
			else
			{
				//#CM713491
				// Roll-back if the result is false. 
				conn.rollback();
			}

			//#CM713492
			// Set the message obtained from the schedule. 
			message.setMsgResourceKey(schedule.getMessage());

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
				//#CM713493
				// Close the connection. 
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

	//#CM713494
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713495
	/**
	 * Clicking on the Clear button invokes this.  <BR>
	 * <BR>
	 * Summary: Clears the input area.  <BR>
	 * <BR>
	 * <DIR>1. Initialize the screen.  <BR>
	 * For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method.  <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM713496
		// show the Initial Display. 
		setFirstDisp();
	}

	//#CM713497
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713498
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713499
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM713500
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713501
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM713502
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713503
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM713504
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM713505
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM713506
	// Package methods -----------------------------------------------

	//#CM713507
	// Protected methods ---------------------------------------------

	//#CM713508
	// Private methods -----------------------------------------------
	//#CM713509
	/**
	 * Invoke this method to display/clear the initial display. 
	 * @throws Exception Report all exceptions.  
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM713510
			// Set the cursor on the Worker code. 
			setFocus(txt_WorkerCode);

			//#CM713511
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			WmsScheduler schedule = new RetrievalItemAllocClearSCH();

			RetrievalSupportParameter param = (RetrievalSupportParameter) schedule.initFind(conn, null);

			//#CM713512
			// For data with only one Consignor code, display the initial display. 
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
			else
			{
				txt_ConsignorCode.setText("");
			}
			
			//#CM713513
			// Planned Picking Date
			txt_RtrivlPlanDate.setText("");
			//#CM713514
			// Start Item Code 
			txt_StartItemCode.setText("");
			//#CM713515
			// End Item Code 
			txt_EndItemCode.setText("");
			//#CM713516
			// Case/Piece division 
			rdo_CpfAll.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);						
		}
		catch (Exception ex)
		{
			//#CM713517
			// Roll-back the connection. 
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
				//#CM713518
				// Close the connection. 
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM713519
	/**
	 * Allow this method to set the input field item data in the parameter class. <BR>
	 * <BR>
	 * Summary: Sets the data of input field item in the parameter class. <BR>
	 * <BR>
	 * <DIR>
	 *   	 [Parameter]  *Mandatory Input<BR>
	 *   	<DIR>
	 * 			 None <BR>
	 * 		</DIR>
	 *   	 [Returned data] <BR>
	 *   	<DIR>
	 * 			Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has contents of input field item<BR>
	 * 		</DIR>
	 * </DIR>
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has contents of input field item
	 * @throws Exception Report all exceptions.  
	 */
	private RetrievalSupportParameter[] setParam() throws Exception
	{
		Vector vecParam = new Vector();

		//#CM713520
		// Set for the schedule parameter: 
		RetrievalSupportParameter param = new RetrievalSupportParameter();

		//#CM713521
		// Info in the Input area info 
		//#CM713522
		// Worker Code
		param.setWorkerCode(txt_WorkerCode.getText());
		//#CM713523
		// Password 
		param.setPassword(txt_PassWord.getText());
		//#CM713524
		// Terminal No.
		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
		param.setTerminalNumber(userHandler.getTerminalNo());

		//#CM713525
		// Consignor Code
		param.setConsignorCode(txt_ConsignorCode.getText());
		//#CM713526
		// Set the Planned Picking date. 
		param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM713527
		// Set the Start item code. 
		param.setStartItemCode(txt_StartItemCode.getText());
		//#CM713528
		// Set the End item code. 
		param.setEndItemCode(txt_EndItemCode.getText());
		//#CM713529
		// Case/Piece division 
		if (rdo_CpfAll.getChecked())
		{
			//#CM713530
			// All 
			param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
		}
		if(rdo_CpfCase.getChecked())
		{
			//#CM713531
			// Case 
			param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		if(rdo_CpfPiece.getChecked())
		{
			//#CM713532
			// Piece 
			param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		if(rdo_CpfAppointOff.getChecked())
		{
			//#CM713533
			// None 
			param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}

		vecParam.addElement(param);
	
		if (vecParam.size() > 0)
		{
			//#CM713534
			// Set a value if any preset data to be set. 
			RetrievalSupportParameter[] listparam = new RetrievalSupportParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			//#CM713535
			// Set null if no preset data to be set. 
			return null;
		}
	}

	//#CM713536
	/** 
	 * This method checks for input. <BR>
	 * Correct input returns true, or incorrect input returns false. <BR>
	 * <BR>
	 * 
	 * @throws Exception Report all exceptions. 
	 * @return Result of check for input (true: OK  false: NG)
	 */
	private boolean checkInputData() throws Exception
	{
		//#CM713537
		// Check for input. 
		//#CM713538
		// Require that the value of Start item code is smaller than the value of End item code.
		if(!StringUtil.isBlank(txt_StartItemCode.getText()) && !StringUtil.isBlank(txt_EndItemCode.getText()))
		{
			if(txt_StartItemCode.getText().compareTo(txt_EndItemCode.getText()) > 0)			{
				//#CM713539
				// 6023109=Starting item code must be smaller than the end item code. 
				message.setMsgResourceKey("6023109");
				return false;
			}
		}		
		
		return true;
	}

	//#CM713540
	// Event handler methods -----------------------------------------
}
//#CM713541
//end of class
