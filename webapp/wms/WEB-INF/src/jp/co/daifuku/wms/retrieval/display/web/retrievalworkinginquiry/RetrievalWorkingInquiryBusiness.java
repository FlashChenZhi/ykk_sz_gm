// $Id: RetrievalWorkingInquiryBusiness.java,v 1.2 2007/02/07 04:19:33 suresh Exp $

//#CM719542
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalworkinginquiry;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.PullDownItem;
import jp.co.daifuku.bluedog.util.Formatter;
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
import jp.co.daifuku.wms.base.display.web.PulldownHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalWorkingInquiryParameter;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalWorkingInquirySCH;

//#CM719543
/**
 * Designer : K.Mukai <BR>
 * Maker : T.Kuroda <BR>
 * 
 * <BR>
 * Allow this class of screen to inquire the Inquiry for picking status. <BR>
 * Set the contents entered via screen for the parameter, and allow the schedule to search data to be displayed based on the parameter. <BR>
 * Receive data to be output to the display area from the schedule and output it to the display area. <BR>
 * <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process by clicking "Display" button (<CODE>btn_View_Click()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *    Search through the Picking Plan Info using the field items input in the Input area as a condition, and display the work progress in a list. <BR>
 * <BR>
 *    [Parameter]  *Mandatory Input<BR>
 * <BR>
 *    <DIR>
 *       Consignor Code <BR>
 *       Planned Picking Date* <BR>
 *    </DIR>
 * <BR>
 *   [Returned data]  <BR>
 * <BR>
 *    <DIR>
 *		- Order aggregation <BR>
 *        Order Count (Total, Not Processed, Working, Completed, and Progress rate) <BR>
 *        Work Count(Total, Not Processed, Working, Completed, or Progress rate) <BR>
 *        Case Qty (Total, Not Processed, Working, Completed, and Progress rate) <BR>
 *        Piece Qty (Total, Not Processed, Working, Completed, or Progress rate) <BR>
 *        Number of consignors (Total, "Not Processed", "Working", "Completed", and Progress rate) <BR>
 * 		- Item aggregation <BR>
 * 		  Work Count(Total, Not Processed, Working, Completed, or Progress rate) <BR>
 *        Case Qty (Total, Not Processed, Working, Completed, and Progress rate) <BR>
 *        Piece Qty (Total, Not Processed, Working, Completed, or Progress rate) <BR>
 *        Number of consignors (Total, "Not Processed", "Working", "Completed", and Progress rate) <BR>
 *    </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/26</TD><TD>T.Kuroda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:33 $
 * @author  $Author: suresh $
 */
public class RetrievalWorkingInquiryBusiness extends RetrievalWorkingInquiry implements WMSConstants
{
	//#CM719544
	// Class fields --------------------------------------------------

	//#CM719545
	/** 
	 * For blank Planned Picking Date 
	 */
	private static final String BLANK_DATE = "        ";

	//#CM719546
	// Class variables -----------------------------------------------

	//#CM719547
	// Class method --------------------------------------------------

	//#CM719548
	// Constructors --------------------------------------------------

	//#CM719549
	// Public methods ------------------------------------------------

	//#CM719550
	/**
	 * Invoke this before invoking each control event. <BR>
	 * <BR>
	 * Summary: This method executes the following processes.<BR>
	 * <BR>
	
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM719551
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM719552
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM719553
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}
	}

	//#CM719554
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 * Summary: shows the Initial Display. <BR>
	 * <BR>
	 * <DIR>
	 *    1.Set the cursor on the Pulldown. <BR>
	 *    2. Set 0 for all field items to be displayed in the list (Set the Progress rate up to first decimal place), and show the Initial Display <BR>
	 * <BR>
	 *    Field item: name [Initial Value] <BR>
	 *    <DIR>
	 *       Consignor Code [If there is only one Consignor code that corresponds to the condition, show the initial display of Consignor Code.] <BR>
	 *       Planned Picking Date [show the leading date of the Search Planned Picking Date in the Initial Display] <BR>
	 *       Listcell [Set 0 in all listcells (Set the Progress rate up to first decimal place), and show the Initial Display] 
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;

		try
		{
			//#CM719555
			// Disable a pulldown. 
			pul_RtrivlPlanDate.setEnabled(false);

			//#CM719556
			// Obtain Connection 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			RetrievalWorkingInquiryParameter initParam = new RetrievalWorkingInquiryParameter();

			WmsScheduler schedule = new RetrievalWorkingInquirySCH();
			RetrievalWorkingInquiryParameter param = (RetrievalWorkingInquiryParameter) schedule.initFind(conn, initParam);

			//#CM719557
			// For data with only one Consignor code, display the initial display. 
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
			}

			//#CM719558
			// Default value of Planned Picking Date
			if (param.getPlanDateP() != null)
			{
				//#CM719559
				// Disable a pulldown. 
				pul_RtrivlPlanDate.setEnabled(true);
				
				//#CM719560
				// Obtain the number of data with blank Planned Picking Date. 
				int blankCount = 0;
				for (int i = 0; i < param.getPlanDateP().length; i++)
				{
					if (StringUtil.isBlank(param.getPlanDateP()[i]))
					{
						blankCount++;
					}
				}

				String planDate[] = new String[param.getPlanDateP().length - blankCount];

				//#CM719561
				// Set a value in the Planned Picking Date. 
				blankCount = 0;
				for (int i = 0; i < param.getPlanDateP().length; i++)
				{
					if (!StringUtil.isBlank(param.getPlanDateP()[i]))
					{
						planDate[blankCount] = WmsFormatter.toDispDate(param.getPlanDateP()[i], this.getHttpRequest().getLocale());
						blankCount++;
					}
				}
				PulldownHelper.setPullDown(pul_RtrivlPlanDate, planDate);
				pul_RtrivlPlanDate.setSelectedIndex(0);
				//#CM719562
				// Set the cursor on the Pulldown. 
				setFocus(pul_RtrivlPlanDate);
			}
			//#CM719563
			// If there is no data of Planned Picking Shipping Date (for example, after updating the date/time), disable the pulldown of Planned Picking Date.
			//#CM719564
			// 
			else if (param.getPlanDateP() == null || param.getPlanDateP().length == 0)
			{
				//#CM719565
				// Generate pulldown options. 
				PullDownItem pItem = new PullDownItem();
				//#CM719566
				// Set blank for pulldown options. 
				pItem.setValue(BLANK_DATE);
				//#CM719567
				// Add it to the pulldown. 
				pul_RtrivlPlanDate.addItem(pItem);
				//#CM719568
				// Display the leading item in the pulldown. 
				pul_RtrivlPlanDate.setSelectedIndex(0);
				//#CM719569
				// Set cursor on the Consignor Code.
				setFocus(txt_ConsignorCode);
			}
			//#CM719570
			// Default of listcell 
			clearListCell();
		}
		catch (Exception ex)
		{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				//#CM719571
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

	//#CM719572
	/**
	 * Returning from a popup window invokes this method. 
	 * Override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE>. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM719573
		// Obtain the parameter selected in the listbox. 
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);

		//#CM719574
		// Set a value if not empty. 
		//#CM719575
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
			//#CM719576
			// Set focus for Consignor Code. 
			setFocus(txt_ConsignorCode);
		}

	}

	//#CM719577
	// Package methods -----------------------------------------------

	//#CM719578
	// Protected methods ---------------------------------------------
	//#CM719579
	/**
	 * Allow this method to obtain the Planned Picking Date selected via pulldown. 
	 * @return Planned Picking Date(String)
	 */
	protected String getSelectedDateString()
	{
		//#CM719580
		// Return null if no value exists in the pulldown. 
		if (!pul_RtrivlPlanDate.getEnabled())
		{
			return null;
		}
		
		//#CM719581
		// Obtain the Planned Picking Date from Pulldown.
		String dateText = null;
		dateText = pul_RtrivlPlanDate.getSelectedItem().getValue();

		String selectedDate = WmsFormatter.toParamDate(dateText, this.getHttpRequest().getLocale());
		
		return selectedDate;
	}
	//#CM719582
	// Private methods -----------------------------------------------

	//#CM719583
	// Event handler methods -----------------------------------------
	//#CM719584
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719585
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719586
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719587
	/**
	 * Clicking on the Menu button invokes this. <BR>
	 * <BR>
	 * Summary: Shifts to the Menu screen. <BR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		//#CM719588
		// Shift to the Menu screen. 
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM719589
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719590
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719591
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM719592
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM719593
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM719594
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719595
	/** 
	 * Clicking on the Search Consignor Code button invokes this. <BR>
	 * <BR>
	 * Summary: Sets the search condition for a parameter and displays the Consignor list listbox using the search condition. <BR>
	 * <BR>
	 * <DIR>
	 *    [Parameter]  *Mandatory Input<BR>
	 *    <DIR>
	 *       Consignor Code <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_PSearch_Click(ActionEvent e) throws Exception
	{
		//#CM719596
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM719597
		// Planned Picking Date
		param.setParameter(ListRetrievalConsignorBusiness.RETRIEVALPLANDATE_KEY, getSelectedDateString());		
		//#CM719598
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM719599
		// "Search" flag 
		param.setParameter(ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY, RetrievalSupportParameter.SEARCHFLAG_WORKINFO);
		//#CM719600
		// Processing screen ->"Result" screen 
		redirect("/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do", param, "/progress.do");
	}

	//#CM719601
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lbl_RetrievalPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719602
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void pul_RtrivlPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719603
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void pul_RtrivlPlanDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM719604
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719605
	/**
	 * Clicking on the Display button invokes this. <BR>
	 * <BR>
	 * Summary: Searches through Shipping Plan Info using the field items that are input in the Input area as conditions, and display the work progress in the form of a list.<BR>
	 * <BR>
	 * <DIR>
	 *    1. Check for mandatory input in the items (count) of the input area. (mandatory input, Number of characters, character properties). <BR>
	 *    2. Start the schedule. <BR>
	 *    <BR>
	 *    <DIR>
	 *       [Parameter]  *Mandatory Input<BR>
	 *       <DIR>
	 *          Consignor Code <BR>
	 *          Planned Picking Date* <BR>
	 *       </DIR>
	 *    </DIR>
	 *    <BR>
	 *    3.Display it in the list. <BR>
	 *    4. Set the cursor on the Consignor code. <BR>
	 * <BR>
	 *    Row No. list of listcell <BR>
	 *    <DIR>
	 *       1: Total <BR>
	 *       2: Not Processed <BR>
	 *       3: Working <BR>
	 *       4: Completed <BR>
	 *       5: Progress rate <BR>
	 *    </DIR>
	 * <BR>
	 *    List of listcell line No.<BR>
	 *    <DIR>
	 *       1: Order Count <BR>
	 *       2: Work Count <BR>
	 *       3: Item Number <BR>
	 *       4: Case Qty <BR>
	 *       5: Piece Qty <BR>
	 *       6: Number of consignors <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		//#CM719606
		// Clear the listcell. 
		clearListCell();

		//#CM719607
		// Set the cursor on the Consignor code. 
		setFocus(txt_ConsignorCode);

		//#CM719608
		// Pattern matching characters 
		txt_ConsignorCode.validate(false);

		Connection conn = null;

		try
		{
			//#CM719609
			// Set the schedule parameter. 
			RetrievalWorkingInquiryParameter param = new RetrievalWorkingInquiryParameter();
			//#CM719610
			// Consignor Code
			param.setConsignorCode(txt_ConsignorCode.getText());
			//#CM719611
			// Planned Picking Date
			param.setPlanDate(WmsFormatter.toParamDate(pul_RtrivlPlanDate.getSelectedValue(), this.getHttpRequest().getLocale()));

			//#CM719612
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM719613
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalWorkingInquirySCH();
			RetrievalWorkingInquiryParameter[] viewParam = (RetrievalWorkingInquiryParameter[]) schedule.query(conn, param);

			//#CM719614
			// Close the process when some error occurred or no display data was found. 		
			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM719615
			// Set the search result in each cell. 
			dispCell(viewParam);

			if (!StringUtil.isBlank(schedule.getMessage()))
			{
				//#CM719616
				// Display a message. 
				message.setMsgResourceKey(schedule.getMessage());
			}
		}
		catch (Exception ex)
		{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));			
		}
		finally
		{
			try
			{
				//#CM719617
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

	//#CM719618
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRtrivlWorkingIqrOrder_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM719619
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRtrivlWorkingIqrOrder_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM719620
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRtrivlWorkingIqrOrder_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM719621
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRtrivlWorkingIqrOrder_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM719622
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRtrivlWorkingIqrOrder_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719623
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRtrivlWorkingIqrOrder_Change(ActionEvent e) throws Exception
	{
	}

	//#CM719624
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRtrivlWorkingIqrOrder_Click(ActionEvent e) throws Exception
	{
	}

	//#CM719625
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRtrivlWorkingIqrItem_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM719626
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SRtrivlWorkingIqrItem_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM719627
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRtrivlWorkingIqrItem_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM719628
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRtrivlWorkingIqrItem_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM719629
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRtrivlWorkingIqrItem_Server(ActionEvent e) throws Exception
	{
	}

	//#CM719630
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRtrivlWorkingIqrItem_Change(ActionEvent e) throws Exception
	{
	}

	//#CM719631
	/** 
	 * 
	 * @param e ActionEvent Allow this class to store event information.  
	 * @throws Exception Report all exceptions.  
	 */
	public void lst_SRtrivlWorkingIqrItem_Click(ActionEvent e) throws Exception
	{
	}

	//#CM719632
	//	 Common method------------------------------------------
	//#CM719633
	/** 
	 * Allow this method to clear the information in the listcell. <BR>
	 * <BR>
	 * Summary: Sets a default value for the field items in the listcell. <BR>
	 * 
	 * @throws Exception Report all exceptions. 
	 */
	public void clearListCell() throws Exception
	{
		//#CM719634
		// Inquiry for picking status (Order Aggregation Work) 
		for (int i = 1; i < 6; i++)
		{
			this.lst_SRtrivlWorkingIqrOrder.setCurrentRow(i);
			for (int j = 1; j < 5; j++)
			{
				this.lst_SRtrivlWorkingIqrOrder.setValue(j, "0");
			}
			//#CM719635
			// Set the Progress rate up to first decimal place (0.0). 
			this.lst_SRtrivlWorkingIqrOrder.setValue(5, WmsFormatter.changeProgressRate(0));
		}

		//#CM719636
		// Inquiry for picking status (Item Aggregation Work) 
		for (int i = 1; i < 5; i++)
		{
			this.lst_SRtrivlWorkingIqrItem.setCurrentRow(i);
			for (int j = 1; j < 5; j++)
			{
				this.lst_SRtrivlWorkingIqrItem.setValue(j, "0");
			}
			//#CM719637
			// Set the Progress rate up to first decimal place (0.0). 
			this.lst_SRtrivlWorkingIqrItem.setValue(5, WmsFormatter.changeProgressRate(0));
		}
	}

	//#CM719638
	/** 
	 * Allow this method to set a value in the listcell.  <BR>
	 * <BR>
	 * Summary: Sets the respective status values in the target cells. 
	 * @param param Value to be set
	 * @throws Exception Report all exceptions. 
	 */
	private void dispCell(RetrievalWorkingInquiryParameter[] param) throws Exception
	{
		//#CM719639
		// Set a value in the 1st line of the Order-aggregated work listcell.
		lst_SRtrivlWorkingIqrOrder.setCurrentRow(1);
		lst_SRtrivlWorkingIqrOrder.setValue(1, Formatter.getNumFormat(param[0].getOrderTotal()));
		lst_SRtrivlWorkingIqrOrder.setValue(2, Formatter.getNumFormat(param[0].getUnstartOrderCount()));
		lst_SRtrivlWorkingIqrOrder.setValue(3, Formatter.getNumFormat(param[0].getNowOrderCount()));
		lst_SRtrivlWorkingIqrOrder.setValue(4, Formatter.getNumFormat(param[0].getFinishOrderCount()));
		lst_SRtrivlWorkingIqrOrder.setValue(5, param[0].getOrderRate());

		//#CM719640
		// Set a value in the 2nd line of the Order-aggregated work listcell.
		lst_SRtrivlWorkingIqrOrder.setCurrentRow(2);
		lst_SRtrivlWorkingIqrOrder.setValue(1, Formatter.getNumFormat(param[0].getWorkTotal()));
		lst_SRtrivlWorkingIqrOrder.setValue(2, Formatter.getNumFormat(param[0].getUnstartWorkCount()));
		lst_SRtrivlWorkingIqrOrder.setValue(3, Formatter.getNumFormat(param[0].getNowWorkCount()));
		lst_SRtrivlWorkingIqrOrder.setValue(4, Formatter.getNumFormat(param[0].getFinishWorkCount()));
		lst_SRtrivlWorkingIqrOrder.setValue(5, param[0].getWorkRate());

		//#CM719641
		// Set a value in the 3rd line of the Order-aggregated work listcell.
		lst_SRtrivlWorkingIqrOrder.setCurrentRow(3);
		lst_SRtrivlWorkingIqrOrder.setValue(1, Formatter.getNumFormat(param[0].getCaseTotal()));
		lst_SRtrivlWorkingIqrOrder.setValue(2, Formatter.getNumFormat(param[0].getUnstartCaseCount()));
		lst_SRtrivlWorkingIqrOrder.setValue(3, Formatter.getNumFormat(param[0].getNowCaseCount()));
		lst_SRtrivlWorkingIqrOrder.setValue(4, Formatter.getNumFormat(param[0].getFinishCaseCount()));
		lst_SRtrivlWorkingIqrOrder.setValue(5, param[0].getCaseRate());

		//#CM719642
		// Set a value in the 4th line of the Order-aggregated work listcell.
		lst_SRtrivlWorkingIqrOrder.setCurrentRow(4);
		lst_SRtrivlWorkingIqrOrder.setValue(1, Formatter.getNumFormat(param[0].getPieceTotal()));
		lst_SRtrivlWorkingIqrOrder.setValue(2, Formatter.getNumFormat(param[0].getUnstartPieceCount()));
		lst_SRtrivlWorkingIqrOrder.setValue(3, Formatter.getNumFormat(param[0].getNowPieceCount()));
		lst_SRtrivlWorkingIqrOrder.setValue(4, Formatter.getNumFormat(param[0].getFinishPieceCount()));
		lst_SRtrivlWorkingIqrOrder.setValue(5, param[0].getPieceRate());

		//#CM719643
		// Set a value in the 5th line of the Order-aggregated work listcell.
		lst_SRtrivlWorkingIqrOrder.setCurrentRow(5);
		lst_SRtrivlWorkingIqrOrder.setValue(1, Formatter.getNumFormat(param[0].getConsignorTotal()));
		lst_SRtrivlWorkingIqrOrder.setValue(2, Formatter.getNumFormat(param[0].getUnstartConsignorCount()));
		lst_SRtrivlWorkingIqrOrder.setValue(3, Formatter.getNumFormat(param[0].getNowConsignorCount()));
		lst_SRtrivlWorkingIqrOrder.setValue(4, Formatter.getNumFormat(param[0].getFinishConsignorCount()));
		lst_SRtrivlWorkingIqrOrder.setValue(5, param[0].getConsignorRate());

		//#CM719644
		// Set a value in the 1st line of the Item-aggregated work listcell.
		lst_SRtrivlWorkingIqrItem.setCurrentRow(1);
		lst_SRtrivlWorkingIqrItem.setValue(1, Formatter.getNumFormat(param[1].getWorkTotal()));
		lst_SRtrivlWorkingIqrItem.setValue(2, Formatter.getNumFormat(param[1].getUnstartWorkCount()));
		lst_SRtrivlWorkingIqrItem.setValue(3, Formatter.getNumFormat(param[1].getNowWorkCount()));
		lst_SRtrivlWorkingIqrItem.setValue(4, Formatter.getNumFormat(param[1].getFinishWorkCount()));
		lst_SRtrivlWorkingIqrItem.setValue(5, param[1].getWorkRate());

		//#CM719645
		// Set a value in the 2nd line of the Item-aggregated work listcell.
		lst_SRtrivlWorkingIqrItem.setCurrentRow(2);
		lst_SRtrivlWorkingIqrItem.setValue(1, Formatter.getNumFormat(param[1].getCaseTotal()));
		lst_SRtrivlWorkingIqrItem.setValue(2, Formatter.getNumFormat(param[1].getUnstartCaseCount()));
		lst_SRtrivlWorkingIqrItem.setValue(3, Formatter.getNumFormat(param[1].getNowCaseCount()));
		lst_SRtrivlWorkingIqrItem.setValue(4, Formatter.getNumFormat(param[1].getFinishCaseCount()));
		lst_SRtrivlWorkingIqrItem.setValue(5, param[1].getCaseRate());

		//#CM719646
		// Set a value in the 3rd line of the Item-aggregated work listcell.
		lst_SRtrivlWorkingIqrItem.setCurrentRow(3);
		lst_SRtrivlWorkingIqrItem.setValue(1, Formatter.getNumFormat(param[1].getPieceTotal()));
		lst_SRtrivlWorkingIqrItem.setValue(2, Formatter.getNumFormat(param[1].getUnstartPieceCount()));
		lst_SRtrivlWorkingIqrItem.setValue(3, Formatter.getNumFormat(param[1].getNowPieceCount()));
		lst_SRtrivlWorkingIqrItem.setValue(4, Formatter.getNumFormat(param[1].getFinishPieceCount()));
		lst_SRtrivlWorkingIqrItem.setValue(5, param[1].getPieceRate());

		//#CM719647
		// Set a value in the 4th line of the Item-aggregated work listcell.
		lst_SRtrivlWorkingIqrItem.setCurrentRow(4);
		lst_SRtrivlWorkingIqrItem.setValue(1, Formatter.getNumFormat(param[1].getConsignorTotal()));
		lst_SRtrivlWorkingIqrItem.setValue(2, Formatter.getNumFormat(param[1].getUnstartConsignorCount()));
		lst_SRtrivlWorkingIqrItem.setValue(3, Formatter.getNumFormat(param[1].getNowConsignorCount()));
		lst_SRtrivlWorkingIqrItem.setValue(4, Formatter.getNumFormat(param[1].getFinishConsignorCount()));
		lst_SRtrivlWorkingIqrItem.setValue(5, param[1].getConsignorRate());
	}
}
//#CM719648
//end of class
