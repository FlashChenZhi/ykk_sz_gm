// $Id: RetrievalOrderAllocClearBusiness.java,v 1.2 2007/02/07 04:19:20 suresh Exp $

//#CM715598
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalorderallocclear;
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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderlist.ListRetrievalOrderListBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderAllocClearSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM715599
/**
 * Designer : Y.Osawa<BR>
 * Maker : Y.Osawa<BR>
 * <BR>
 * Allow this class to cancel the Order Picking Allocation. <BR>
 * Set the contents entered via screen, and<BR>
 *  allow the schedule to cancel the allocation of the Order Picking based on the parameter.<BR>
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
 * 		 Start Order No.  <BR>
 * 		 End Order No.  <BR>
 * 		 Case/Piece division * <BR>
 * 	</DIR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Setup" button (<CODE>btn_StorageStart_Click()</CODE> method)  <BR>
 * <BR>
 * <DIR>
 * Set the contents entered via screen, and<BR>
 *  allow the schedule to cancel the allocation of the Order Picking based on the parameter.<BR>
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
 * 		 Start Order No.  <BR>
 * 		 End Order No.  <BR>
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
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:20 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderAllocClearBusiness extends RetrievalOrderAllocClear implements WMSConstants
{
	//#CM715600
	// Class fields --------------------------------------------------

	//#CM715601
	// Class variables -----------------------------------------------

	//#CM715602
	// Class method --------------------------------------------------

	//#CM715603
	// Constructors --------------------------------------------------

	//#CM715604
	// Public methods ------------------------------------------------

	//#CM715605
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
			//#CM715606
			// Set the initial value for each input field. 
			txt_WorkerCode.setText("");
			txt_PassWord.setText("");

			//#CM715607
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
				//#CM715608
				// Close the connection. 
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	//#CM715609
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
		//#CM715610
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);		

		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM715611
		// Obtain the parameter selected in the listbox. 
		//#CM715612
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM715613
		// Planned Picking Date
		String retPlan = param.getParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY);
		//#CM715614
		// Start Order No. 
		String startOrderNo = param.getParameter(ListRetrievalOrdernoBusiness.STARTORDERNO_KEY);
		//#CM715615
		// End Order No. 
		String endOrderNo = param.getParameter(ListRetrievalOrdernoBusiness.ENDORDERNO_KEY);

		//#CM715616
		// Set a value if not empty. 
		//#CM715617
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM715618
		// Planned Picking Date
		if (!StringUtil.isBlank(retPlan))
		{
			txt_RtrivlPlanDate.setDate(WmsFormatter.toDate(retPlan));
		}
		//#CM715619
		// Start Order No. 
		if (!StringUtil.isBlank(startOrderNo))
		{
			txt_StartOrderNo.setText(startOrderNo);
		}
		//#CM715620
		// End Order No. 
		if (!StringUtil.isBlank(endOrderNo))
		{
			txt_EndOrderNo.setText(endOrderNo);
		}
	}

	//#CM715621
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
			//#CM715622
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM715623
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM715624
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}

		//#CM715625
		// Dialog to confirm before clicking on "Commit" button: "Do you commit it?"
		btn_Setting.setBeforeConfirm("MSG-9000");
	}

	//#CM715626
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715627
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715628
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_AllocClear_Click(ActionEvent e) throws Exception
	{
	}

	//#CM715629
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715630
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
		//#CM715631
		// Shift to the Menu screen. 
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM715632
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715633
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715634
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715635
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715636
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM715637
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PassWord_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715638
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715639
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715640
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715641
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM715642
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715643
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715644
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715645
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715646
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM715647
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PS_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715648
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
		//#CM715649
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM715650
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM715651
		// "Search" flag: Work 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM715652
		// for Order 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM715653
		// Allocation flag: Allocated 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.ALLOCATION_FLAG_KEY, RetrievalSupportParameter.ALLOCATION_COMPLETION);
		//#CM715654
		// Display the Search Consignor listbox.
		redirect("/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do", forwardParam, "/progress.do");
	}

	//#CM715655
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715656
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715657
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715658
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RtrivlPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715659
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PS_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715660
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
		//#CM715661
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM715662
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM715663
		// Set the Planned Picking date. 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM715664
		// "Search" flag 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM715665
		// for Order 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM715666
		// Allocation flag: Allocated 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.ALLOCATION_FLAG_KEY, RetrievalSupportParameter.ALLOCATION_COMPLETION);
		//#CM715667
		// Display the listbox for searching for Planned Picking Date. 
		redirect("/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do", forwardParam, "/progress.do");
	}

	//#CM715668
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715669
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715670
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartOrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715671
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartOrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715672
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartOrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM715673
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PS_StartOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715674
	/** 
	 * Clicking on the Search Start Order No. button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Order No. using this search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  Planned Picking Date<BR>
	 *  Start Order No. <BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PS_StartOrderNo_Click(ActionEvent e) throws Exception
	{
		//#CM715675
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM715676
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM715677
		// Set the Planned Picking date. 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM715678
		// Set the Start Order No. 
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, txt_StartOrderNo.getText());
		//#CM715679
		// "Search" flag 
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM715680
		// Set the Start flag. 
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.RANGE_KEY, RetrievalSupportParameter.RANGE_START);
		//#CM715681
		// for Order 
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM715682
		// Allocation flag: Allocated 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.ALLOCATION_FLAG_KEY, RetrievalSupportParameter.ALLOCATION_COMPLETION);
		//#CM715683
		// Displays the listbox for Order No. 
		redirect("/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do", forwardParam, "/progress.do");
	}

	//#CM715684
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715685
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715686
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715687
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndOrderNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM715688
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndOrderNo_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM715689
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndOrderNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM715690
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PS_EndOrderNo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715691
	/** 
	 * Clicking on the Search End Order No. button invokes this. <BR>
	 * <BR>
	 * Summary: This method sets the search conditions for the parameter and 
	 * displays the listbox for searching for the Order No. using this search condition. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input
	 *  <DIR>
	 *  Consignor Code<BR>
	 *  Planned Picking Date<BR>
	 *  End Order No. <BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PS_EndOrderNo_Click(ActionEvent e) throws Exception
	{
		//#CM715692
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM715693
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM715694
		// Set the Planned Picking date. 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM715695
		// Set the End Order No. 
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, txt_EndOrderNo.getText());
		//#CM715696
		// "Search" flag 
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM715697
		// Set the Close flag. 
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.RANGE_KEY, RetrievalSupportParameter.RANGE_END);
		//#CM715698
		// for Order 
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ORDER);
		//#CM715699
		// Allocation flag: Allocated 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.ALLOCATION_FLAG_KEY, RetrievalSupportParameter.ALLOCATION_COMPLETION);
		//#CM715700
		// Displays the listbox for Order No. 
		redirect("/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do", forwardParam, "/progress.do");
	}

	//#CM715701
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715702
	/**
	 * Clicking on the Display button invokes this.  <BR>
	 * Summary: Sets the field item entered in the Input area for the parameter and displays the listbox for Order Picking allocation work list in the different screen. <BR>
	 * <BR>
	 * [Parameter] *Mandatory Input <BR>
	 * <DIR>Consignor Code* <BR>
	 * Planned Picking Date* <BR>
	 * Start Order No.  <BR>
	 * End Order No.  <BR>
	 * Case/Piece division * <BR>
	 * </DIR></BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
	    
		//#CM715703
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();
		//#CM715704
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM715705
		// Set the Planned Picking date. 
		forwardParam.setParameter(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY, WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM715706
		// Set the Start Order No. 
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.STARTORDERNO_KEY, txt_StartOrderNo.getText());
		//#CM715707
		// Set the End Order No. 
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.ENDORDERNO_KEY, txt_EndOrderNo.getText());
		
		//#CM715708
		// Case/Piece division 
		if (rdo_CpfAll.getChecked())
		{
			//#CM715709
			// All 
			forwardParam.setParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY,
					RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
		}
		if(rdo_CpfCase.getChecked())
		{
			//#CM715710
			// Case 
			forwardParam.setParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		if(rdo_CpfPiece.getChecked())
		{
			//#CM715711
			// Piece 
			forwardParam.setParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		if(rdo_CpfAppointOff.getChecked())
		{
			//#CM715712
			// None 
			forwardParam.setParameter(ListRetrievalOrderListBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM715713
		// "Search" flag 
		forwardParam.setParameter(ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);

		//#CM715714
		// Displays the listbox for Order Picking allocation work list. 
		redirect("/retrieval/listbox/listretrievalorderalloc/ListRetrievalOrderAlloc.do", forwardParam, "/progress.do");
	}

	//#CM715715
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Setting_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715716
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
		
		//#CM715717
		// Set the cursor on the Worker code. 
		setFocus(txt_WorkerCode);
		
		//#CM715718
		// Check for mandatory input. 
		txt_WorkerCode.validate();
		txt_PassWord.validate();
		txt_ConsignorCode.validate();
		txt_RtrivlPlanDate.validate();
		
		//#CM715719
		// Pattern matching characters 
		txt_StartOrderNo.validate(false);
		txt_EndOrderNo.validate(false);

		try
		{			
			//#CM715720
			// Check for input. 
			if(!checkInputData())
			{
				return;
			}
			
			//#CM715721
			// for storing data to be updated 
			RetrievalSupportParameter[] param = null;

			//#CM715722
			// Set the data of the input field item. 
			param = setParam();

			//#CM715723
			// Obtain connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalOrderAllocClearSCH();

			//#CM715724
			// Start the schedule. 
			if (schedule.startSCH(conn, param))
			{
				//#CM715725
				// If the result is true, commit it. 
				conn.commit();
			}
			else
			{
				//#CM715726
				// Roll-back if the result is false. 
				conn.rollback();
			}

			//#CM715727
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
				//#CM715728
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

	//#CM715729
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715730
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
		//#CM715731
		// show the Initial Display. 
		setFirstDisp();
	}
	//#CM715732
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715733
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715734
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM715735
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715736
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM715737
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715738
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM715739
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM715740
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM715741
	// Package methods -----------------------------------------------

	//#CM715742
	// Protected methods ---------------------------------------------

	//#CM715743
	// Private methods -----------------------------------------------
	//#CM715744
	/**
	 * Invoke this method to display/clear the initial display. 
	 * @throws Exception Report all exceptions.  
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM715745
			// Set the cursor on the Worker code. 
			setFocus(txt_WorkerCode);

			//#CM715746
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			WmsScheduler schedule = new RetrievalOrderAllocClearSCH();
			
			RetrievalSupportParameter param = (RetrievalSupportParameter) schedule.initFind(conn, null);

			//#CM715747
			// For data with only one Consignor code, display the initial display. 
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
			else
			{
				txt_ConsignorCode.setText("");
			}
			
			//#CM715748
			// Planned Picking Date
			txt_RtrivlPlanDate.setText("");
			//#CM715749
			// Start Order No. 
			txt_StartOrderNo.setText("");
			//#CM715750
			// End Order No. 
			txt_EndOrderNo.setText("");
			//#CM715751
			// Case/Piece division 
			rdo_CpfAll.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);						
		}
		catch (Exception ex)
		{
			//#CM715752
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
				//#CM715753
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

	//#CM715754
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
	 * @throws Exception Report all exceptions.  
	 */
	private RetrievalSupportParameter[] setParam() throws Exception
	{
		Vector vecParam = new Vector();

		//#CM715755
		// Set for the schedule parameter: 
		RetrievalSupportParameter param = new RetrievalSupportParameter();

		//#CM715756
		// Info in the Input area info 
		//#CM715757
		// Worker Code
		param.setWorkerCode(txt_WorkerCode.getText());
		//#CM715758
		// Password 
		param.setPassword(txt_PassWord.getText());
		//#CM715759
		// Terminal No.
		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
		param.setTerminalNumber(userHandler.getTerminalNo());

		//#CM715760
		// Consignor Code
		param.setConsignorCode(txt_ConsignorCode.getText());
		//#CM715761
		// Set the Planned Picking date. 
		param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		//#CM715762
		// Set the Start Order No. 
		param.setStartOrderNo(txt_StartOrderNo.getText());
		//#CM715763
		// Set the End Order No. 
		param.setEndOrderNo(txt_EndOrderNo.getText());
		//#CM715764
		// Case/Piece division 
		if (rdo_CpfAll.getChecked())
		{
			//#CM715765
			// All 
			param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
		}
		if(rdo_CpfCase.getChecked())
		{
			//#CM715766
			// Case 
			param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		if(rdo_CpfPiece.getChecked())
		{
			//#CM715767
			// Piece 
			param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		if(rdo_CpfAppointOff.getChecked())
		{
			//#CM715768
			// None 
			param.setCasePieceflg(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}

		vecParam.addElement(param);
	
		if (vecParam.size() > 0)
		{
			//#CM715769
			// Set a value if any preset data to be set. 
			RetrievalSupportParameter[] listparam = new RetrievalSupportParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			//#CM715770
			// Set null if no preset data to be set. 
			return null;
		}
	}

	//#CM715771
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
		//#CM715772
		// Check for input. 
		//#CM715773
		// Require that the value of Start Order No. is smaller than the value of End Order No.
		if(!StringUtil.isBlank(txt_StartOrderNo.getText()) && !StringUtil.isBlank(txt_EndOrderNo.getText()))
		{
			if(txt_StartOrderNo.getText().compareTo(txt_EndOrderNo.getText()) > 0)			{
				//#CM715774
				// 6023136= Starting order No. must precede the end order No. 
				message.setMsgResourceKey("6023136");
				return false;
			}
		}		
		
		return true;
	}

	//#CM715775
	// Event handler methods -----------------------------------------
}
//#CM715776
//end of class
