// $Id: RetrievalItemQtyListBusiness.java,v 1.2 2007/02/07 04:19:16 suresh Exp $

//#CM714541
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.web.retrievalitemqtylist;
import java.sql.Connection;
import java.sql.SQLException;

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
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievaldate.ListRetrievalDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitemlist.ListRetrievalItemListBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalItemQtyListSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM714542
/**
 * Designer : K.Mukai <BR>
 * Maker : T.Yoshino<BR>
 * Allow this class of screen to print the Item Picking Result Report. <BR>
 * Pass the parameter to the schedule to execute the process to print the Item Picking Result Report. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process by clicking "Display" button (<CODE>btn_PDisplay_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 *  Set the contents entered via screen, and
 *   allow the schedule to search for the data to be displayed, based on the parameter.<BR>
 *  Receive the search result from the schedule, and invoke the Item Picking Result Report list screen. <BR>
 *  <BR>
 *  [Parameter]  *Mandatory Input<BR>
 *  <DIR>
 *   Consignor Code*<BR>
 *   Start Picking Date<BR>
 *   End Picking Date<BR>
 *   Item Code<BR>
 *   Case/Piece division <BR>
 *   <DIR>
 *   All <BR>
 *   Case <BR>
 *   Piece <BR>
 *   None <BR>
 *   </DIR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Print" button (<CODE>btn_Print_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 *  Set the contents entered via screen, and<BR>
 *  allow the schedule to search for the databased on the parameter and print it.  <BR>
 *  Allow the schedule to return true when printing completed successfully or return false when it failed. <BR>
 *  <BR>
 *  [Parameter]  *Mandatory Input<BR>
 *  <DIR>
 *   Consignor Code*<BR>
 *   Start Picking Date<BR>
 *   End Picking Date<BR>
 *   Item Code<BR>
 *   Case/Piece division <BR>
 *   <DIR>
 *   All <BR>
 *   Case <BR>
 *   Piece <BR>
 *   None <BR>
 *   </DIR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/25</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:16 $
 * @author  $Author: suresh $
 */
public class RetrievalItemQtyListBusiness extends RetrievalItemQtyList implements WMSConstants
{
	//#CM714543
	// Class fields --------------------------------------------------
	
	//#CM714544
	// Maintain the control that invokes the dialog: Print button 
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";

	//#CM714545
	// Class variables -----------------------------------------------

	//#CM714546
	// Class method --------------------------------------------------

	//#CM714547
	// Constructors --------------------------------------------------

	//#CM714548
	// Public methods ------------------------------------------------

	//#CM714549
	/**
	 * Initialize the screen. 
	 * Summary: Shows the initial display. Disable Clear button to allow to invoke page_load.  <BR>
	 * <BR>
	 * Field item: name [Initial Value]  <BR>
	 * <DIR>
	 * Consignor Code [If there is only one Consignor code that corresponds to the condition, show the Initial Display.]  <BR>
	 * Start Picking Date [None] <BR>
	 * End Picking Date [None] <BR>
	 * Item Code [None] <BR>
	 * Case/Piece division[All] <BR>
	 * </DIR>
	 *
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM714550
		// Set the initial value for each input field. 
		//#CM714551
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM714552
		// Start Picking Date
		txt_StartRetrievalDate.setText("");
		//#CM714553
		// End Picking Date
		txt_EndRetrievalDate.setText("");		
		//#CM714554
		// Item Code
		txt_ItemCode.setText("");
		//#CM714555
		// Case/Piece division 
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);
		
		//#CM714556
		// Move the cursor to the Consignor code. 
		setFocus(txt_ConsignorCode);
	}
	//#CM714557
	/**
	 * Invoke this before invoking each control event.  <BR>
	 * <BR>
	 * <DIR>
	 *  Summary: Displays a dialog.  <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM714558
			// Obtain the parameter. 
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM714559
			// Maintain it in ViewState. 
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM714560
			// Set the screen name. 
			lbl_SettingName.setResourceKey(title);
		}

	}
	
	//#CM714561
	/**
	 * Returning from the Dialog button invokes this method.
	 * Override the page_ConfirmBack defined for Page. 
	 * <BR>
	 * Summary: Executes the selected process if selected Yes in the dialog. <BR>
	 * <BR>
	 * 1. Check which dialog returns it. <BR>
	 * 2. Check for choice of Yes or No clicked in the dialog box. <BR>
	 * 3. Selecting Yes starts the schedule. <BR>
	 * 4. For print dialog, <BR>
	 *   <DIR>
	 *   4-1. Set the input field for the parameter. <BR>
	 *   4-2. Start the printing schedule. <BR>
	 *   4-3. Display the schedule result in the message area. <BR>
	 *	 </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		try
		{
			//#CM714562
			// Set the focus for the Consignor code. 
			setFocus(txt_ConsignorCode);

			//#CM714563
			// Identify which dialog returns it. 
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM714564
			// Clicking on Yes in the dialog box returns true. 
			//#CM714565
			// Clicking on No in the dialog box returns false. 
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM714566
			// Clicking NO button closes the process. 
			//#CM714567
			// Not required to set any error message here. 
			if (!isExecute)
			{
				return;
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			//#CM714568
			// Ensure to turn the flag OFF after closed the operation of the dialog. 
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM714569
		// Start the printing schedule. 
		Connection conn = null;
		try
		{
			//#CM714570
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM714571
			// Set the input value for the parameter. 
			RetrievalSupportParameter[] param = new RetrievalSupportParameter[1];
			param[0] = createParameter();

			//#CM714572
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalItemQtyListSCH();
			schedule.startSCH(conn, param);
			
			//#CM714573
			// Set the message. 
			message.setMsgResourceKey(schedule.getMessage());
	
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
	
		}
		finally
		{
			try
			{
				//#CM714574
				// Close the connection. 
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
	
		}
		
	}

	
	//#CM714575
	/**
	 * Returning from a popup window invokes this method.
	 * Override the page_DlgBack defined for Page. 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		//#CM714576
		// Obtain the parameter selected in the listbox. 
		//#CM714577
		// Consignor Code
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM714578
		// Start Picking Date
		String startretrievaldate = param.getParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY);
		//#CM714579
		// End Picking Date
		String endretrievaldate = param.getParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY);
		//#CM714580
		// Item Code
		String itemcode = param.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);
		//#CM714581
		// Set a value if not empty. 
		//#CM714582
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		//#CM714583
		// Start Picking Date
		if (!StringUtil.isBlank(startretrievaldate))
		{
			txt_StartRetrievalDate.setText(startretrievaldate);
		}
		//#CM714584
		// End Picking Date
		if (!StringUtil.isBlank(endretrievaldate))
		{
			txt_EndRetrievalDate.setText(endretrievaldate);
		}
		//#CM714585
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		//#CM714586
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
	}
	//#CM714587
	// Package methods -----------------------------------------------

	//#CM714588
	// Protected methods ---------------------------------------------

	//#CM714589
	/**
	 * Generate a parameter object for which the input value of the input area is set. <BR>
	 * 
	 * @return Parameter object that contains input values in the input area. 
	 */
	protected RetrievalSupportParameter createParameter()
	{
		RetrievalSupportParameter param = new RetrievalSupportParameter();
		
		//#CM714590
		// Consignor Code
		param.setConsignorCode(txt_ConsignorCode.getText());
		//#CM714591
		// Start Picking Date
		param.setFromRetrievalDate(WmsFormatter.toParamDate(txt_StartRetrievalDate.getDate()));
		//#CM714592
		// End Picking Date
		param.setToRetrievalDate(WmsFormatter.toParamDate(txt_EndRetrievalDate.getDate()));
		//#CM714593
		// Item Code
		param.setItemCode(txt_ItemCode.getText());
		
		//#CM714594
		// Case/Piece division 				
		if (rdo_CpfAll.getChecked())
		{
			param.setCasePieceflg(
				RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
		}
		else if (rdo_CpfCase.getChecked())
		{
			param.setCasePieceflg(
				RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		else if (rdo_CpfPiece.getChecked())
		{
			param.setCasePieceflg(
				RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setCasePieceflg(
				RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		return param;
	}


	//#CM714595
	// Private methods -----------------------------------------------
	//#CM714596
	/**
	 * Allow this method to obtain the Consignor code from the schedule on the initial display.  <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule.  <BR>
	 * 
	 * @return Consignor Code <BR>
	 *         Return the string of the Consignor code when one corresponding data exists. <BR>
	 *         Return null character when 0 or multiple corresponding data exist.  <BR>
	 * 
	 * @throws Exception
	 *             Report all exceptions. 
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			RetrievalSupportParameter param = new RetrievalSupportParameter();

			//#CM714597
			// Obtain the Consignor code from the schedule. 
			WmsScheduler schedule = new RetrievalItemQtyListSCH();
			param = (RetrievalSupportParameter) schedule.initFind(conn, param);

			if (param != null)
			{
				return param.getConsignorCode();
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			//#CM714598
			// Close the connection. 
			if (conn != null)
			{
				conn.close();
			}
		}
		return "";
	}
	//#CM714599
	// Event handler methods -----------------------------------------
	//#CM714600
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714601
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714602
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714603
	/** 
	 * Clicking on the Menu button invokes this. <BR>
	 * Summary: Shifts to the Menu screen. <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM714604
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714605
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714606
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714607
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714608
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714609
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714610
	/** 
	 * Clicking on the Search Consignor button invokes this. <BR>
	 * Summary: This method sets the search conditions for the parameter and <BR>
	 * displays the listbox for searching through the Consignor list using the search condition. <BR>
	 * [Parameter]  *Mandatory Input
	 * <DIR>
	 * Consignor Code<BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		//#CM714611
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM714612
		// Consignor Code
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		//#CM714613
		// "Search" flag 
		param.setParameter(
			ListRetrievalConsignorBusiness.SEARCHCONSIGNOR_KEY,
			RetrievalSupportParameter.SEARCHFLAG_RESULT);
		//#CM714614
		// For Item 
		param.setParameter(ListRetrievalConsignorBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM714615
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalconsignor/ListRetrievalConsignor.do",
			param,
			"/progress.do");		
	}

	//#CM714616
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714617
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714618
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartRetrievalDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714619
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartRetrievalDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714620
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PsearchStartRtrivlDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714621
	/** 
	 * Clicking on the Search Start Search Picking Date button invokes this. <BR>
	 * Summary: This method sets the search conditions for the parameter and <BR>
	 * displays the listbox for searching through the Picking Date list using this search condition. <BR>
	 * [Parameter]  *Mandatory Input
	 * <DIR>
	 * Consignor Code<BR>
	 * Start Picking Date<BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PsearchStartRtrivlDate_Click(ActionEvent e) throws Exception
	{
		//#CM714622
		// Set the search condition in the Search screen of Picking Date. 
		ForwardParameters param = new ForwardParameters();
		//#CM714623
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,txt_ConsignorCode.getText());
		//#CM714624
		// Start Picking Date
		param.setParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY,WmsFormatter.toParamDate(txt_StartRetrievalDate.getDate()));
		//#CM714625
		// Start flag (Picking date range flag) 
		param.setParameter(ListRetrievalDateBusiness.RANGERETRIEVALDATE_KEY,RetrievalSupportParameter.RANGE_START);
		//#CM714626
		// For Item 
		param.setParameter(ListRetrievalDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM714627
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievaldate/ListRetrievalDate.do",
			param,
			"/progress.do");
	}

	//#CM714628
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714629
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714630
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndRetrievalDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714631
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndRetrievalDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714632
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndRetrievalDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714633
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEndRtrivlDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714634
	/** 
	 * Clicking on the Search End Picking Date button invokes this. <BR>
	 * Summary: This method sets the search conditions for the parameter and <BR>
	 * displays the listbox for searching through the Picking Date list using this search condition. <BR>
	 * [Parameter]  *Mandatory Input
	 * <DIR>
	 * Consignor Code<BR>
	 * End Picking Date<BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchEndRtrivlDate_Click(ActionEvent e) throws Exception
	{
		//#CM714635
		// Set the search condition in the Search screen of Location. 
		ForwardParameters param = new ForwardParameters();
		//#CM714636
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,txt_ConsignorCode.getText());
		//#CM714637
		// End Picking Date
		param.setParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY,WmsFormatter.toParamDate(txt_EndRetrievalDate.getDate()));
		//#CM714638
		// "End" flag (Picking date range flag) 
		param.setParameter(ListRetrievalDateBusiness.RANGERETRIEVALDATE_KEY,RetrievalSupportParameter.RANGE_END);
		//#CM714639
		// For Item 
		param.setParameter(ListRetrievalDateBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM714640
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievaldate/ListRetrievalDate.do",
			param,
			"/progress.do");
	}

	//#CM714641
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714642
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714643
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM714644
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM714645
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM714646
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714647
	/** 
	 * Clicking on the Search Item button invokes this. <BR>
	 * Summary: This method sets the search conditions for the parameter and <BR>
	 * displays the listbox for searching through the Item list using this search condition. <BR>
	 * [Parameter]  *Mandatory Input
	 * <DIR>
	 * Consignor Code<BR>
	 * Start Picking Date<BR>
	 * End Picking Date<BR>
	 * Item Code<BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM714648
		// Set the search condition in the Search Consignor screen. 
		ForwardParameters param = new ForwardParameters();
		//#CM714649
		// Consignor Code
		param.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM714650
		// Start Picking Date
		param.setParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_StartRetrievalDate.getDate()));
		//#CM714651
		// End Picking Date
		param.setParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_EndRetrievalDate.getDate()));
		//#CM714652
		// Item Code
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM714653
		// "Search" flag 
		param.setParameter(
			ListRetrievalItemBusiness.SEARCHITEM_KEY,
			RetrievalSupportParameter.SEARCHFLAG_RESULT);
		//#CM714654
		// For Item 
		param.setParameter(ListRetrievalItemBusiness.ORDER_ITEM_FLAG, RetrievalSupportParameter.ITEMORDERFLAG_ITEM);
		//#CM714655
		// Processing screen ->"Result" screen 
		redirect(
			"/retrieval/listbox/listretrievalitem/ListRetrievalItem.do",
			param,
			"/progress.do");
	}

	//#CM714656
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714657
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714658
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM714659
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714660
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM714661
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714662
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM714663
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714664
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM714665
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714666
	/** 
	 * Clicking on the "Display" button invokes this. <BR>
	 * Summary: Sets the field item input in the Input area and displays a listbox of Item Picking Result list in the different screen.<BR>
	 * <BR><BR>
	 * [Parameter]  *Mandatory Input<BR>
	 * <DIR>
	 * Consignor Code*<BR>
	 * Start Picking Date<BR>
	 * End Picking Date<BR>
	 * Item Code<BR>
	 * Case/Piece division <BR>
	 * </DIR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		//#CM714667
		// Declare the instance to set the search conditions. 
		ForwardParameters forwardParam = new ForwardParameters();
		//#CM714668
		// Set the Consignor Code. 
		forwardParam.setParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM714669
		// Set the Start Picking Date. 
		forwardParam.setParameter(ListRetrievalDateBusiness.STARTRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_StartRetrievalDate.getDate()));	
		//#CM714670
		// Set the End Picking Date. 
		forwardParam.setParameter(ListRetrievalDateBusiness.ENDRETRIEVALDATE_KEY, WmsFormatter.toParamDate(txt_EndRetrievalDate.getDate()));	
		//#CM714671
		// Set the Item Code. 
		forwardParam.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM714672
		// Set the Case/Piece division. 
		if (rdo_CpfAll.getChecked())
		{
			forwardParam.setParameter(ListRetrievalItemListBusiness.CASEPIECEFLAG_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_ALL);
		}
		else if (rdo_CpfCase.getChecked())
		{
			forwardParam.setParameter(ListRetrievalItemListBusiness.CASEPIECEFLAG_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		else if (rdo_CpfPiece.getChecked())
		{
			forwardParam.setParameter(ListRetrievalItemListBusiness.CASEPIECEFLAG_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		else if (rdo_CpfAppointOff.getChecked())
		{
			forwardParam.setParameter(ListRetrievalItemListBusiness.CASEPIECEFLAG_KEY, RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		//#CM714673
		// Displays the listbox for Item Picking Result Report list. 
		redirect("/retrieval/listbox/listretrievalitemqtylist/ListRetrievalItemQtyList.do", forwardParam, "/progress.do");
	}

	//#CM714674
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714675
	/** 
	 * Clicking on the "Print" button invokes this. <BR>
	 * Summary: Sets the input field item in the input area for the parameter and obtains the count of data to be printed, and then 
	 * displays the dialog box to allow to confirm to print it or not. <BR>
	 * <BR>
	 * 1. Check for input and the count of print targets. <BR>
	 * 2-1. Display the dialog box to allow to confirm it if the print target data found. <BR>
	 * <DIR>
	 *   There are xxx (number) target data. Do you print it? <BR>
	 * </DIR>
	 * 2-2. If no print target data, obtain the message and display it. <BR>
	 * 
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		//#CM714676
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
		//#CM714677
		// Check for input. 
		if(!checkInputData())
		{
			return;
		}
		
		//#CM714678
		// Require that the value of Start Picking Date is larger than the value of End Picking Date. 
		if (!StringUtil.isBlank(txt_StartRetrievalDate.getDate())
			&& !StringUtil.isBlank(txt_EndRetrievalDate.getDate()))
		{
			if (txt_StartRetrievalDate.getDate().compareTo(txt_EndRetrievalDate.getDate()) > 0)
			{
				//#CM714679
				// 6023107=Starting picking date must precede the end picking date. 
				message.setMsgResourceKey("6023107");
				return;
			}
		}
		Connection conn = null;
		try
		{
			//#CM714680
			// Obtain the connection. 
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM714681
			// Set the schedule parameter. 
			RetrievalSupportParameter param = createParameter();			
			//#CM714682
			// Start the schedule. 
			WmsScheduler schedule = new RetrievalItemQtyListSCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM714683
				// MSG-W0061={0} data corresponded.<BR> Do you print it? 
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM714684
				// Store the fact that the dialog was displayed via Print button. 
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM714685
				// Set the message. 
				message.setMsgResourceKey(schedule.getMessage());
			}
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM714686
				// Close the connection. 
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
	
		}
	
	}
	
	//#CM714687
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM714688
	/** 
	 * Clicking on the "Clear" button invokes this. <BR>
	 * Summary: Clears the input area. <BR><BR>
	 * Initialize the screen.  <BR>
	 * @param e ActionEvent Allow this class to store event information. 
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM714689
		// Set Initial Value. 
		//#CM714690
		// Consignor Code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM714691
		// Start Picking Date
		txt_StartRetrievalDate.setText("");
		//#CM714692
		// End Picking Date
		txt_EndRetrievalDate.setText("");		
		//#CM714693
		// Item Code
		txt_ItemCode.setText("");

		//#CM714694
		// Case/Piece division 
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);
		//#CM714695
		// Set the focus for the Consignor code. 
		setFocus(txt_ConsignorCode);
	}
	//#CM714696
	/** 
	 * This method checks for input. <BR>
	 * Correct input returns true, or incorrect input returns false. <BR>
	 * <BR>
	 * 
	 * @throws Exception Report all exceptions. 
	 * 
	 * @return Result of check for input (true: OK  false: NG)
	 */
	private boolean checkInputData() throws Exception
	{
		//#CM714697
		// Check for input. 
		//#CM714698
		// Consignor Code
		txt_ConsignorCode.validate();
		//#CM714699
		// Start Picking Date
		txt_StartRetrievalDate.validate(false);
		//#CM714700
		// End Picking Date
		txt_EndRetrievalDate.validate(false);		
		//#CM714701
		// Item Code
		txt_ItemCode.validate(false);

		return true;
	}
}
//#CM714702
//end of class
