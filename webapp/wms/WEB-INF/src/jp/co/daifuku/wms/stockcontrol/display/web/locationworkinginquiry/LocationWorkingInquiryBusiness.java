// $Id: LocationWorkingInquiryBusiness.java,v 1.2 2006/10/04 05:04:19 suresh Exp $

//#CM6625
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stockcontrol.display.web.locationworkinginquiry;
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
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocation.ListStockLocationBusiness;
import jp.co.daifuku.wms.stockcontrol.schedule.LocationWorkingInquirySCH;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;
//#CM6626
//import jp.co.daifuku.wms.stockcontrol.schedule.StockCorrectSCH;

//#CM6627
/**
 * Designer :<BR>
 * Maker : T.Uehata <BR>
 * <BR>
 * This is stock inquiry per location (per location) screen class<BR>
 * Pass the parameter to the schedule that execute stock inquiry (per location) process per location.<BR>
 *  Set the contents input from the screen to the parameter, and the schedule search the data for display based on the parameter. <BR>
 * Execute following processes in this class. <BR>
 * <BR>
 * 1.Display button press down process( <CODE>btn_PDisplay_Click(ActionEvent e)</CODE> Method) <BR>
 * <BR>
 * <DIR>Set the contents input from the screen to the parameter, and the schedule search the data for display based on the parameter. <BR>
 * Receive the data for stock inquiry (display) screen per location from schedule and invoke stock inquiry (display) screen.<BR>
 * <BR>
 * [parameter] *Mandatory Input <BR>
 * <BR>
 * Consignor code* <BR>
 * Start location <BR>
 * End location <BR>
 * Item code <BR>
 * Case/PieceDivision* <BR>
 * <BR>
 * <BR>
 * 2.Print button press down process( <CODE>btn_Print_Click(ActionEvent e)</CODE>)<BR>
 * <BR>
 * <DIR>Set the contents input from the screen to the parameter, and the schedule search and print the data based on the parameter.<BR>
 * Return true when the schedule completed the print successfully <BR>
 * <BR>
 * [parameter] *Mandatory Input <BR>
 * <DIR>Consignor code* <BR>
 * Start location <BR>
 * End location <BR>
 * Item code <BR>
 * Case/PieceDivision* <BR>
 * </DIR> </DIR> <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/09/27</TD>
 * <TD>T.Uehata</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:04:19 $
 * @author $Author: suresh $
 */
public class LocationWorkingInquiryBusiness extends LocationWorkingInquiry implements WMSConstants
{
	//#CM6628
	// Address of the shift target.
	//#CM6629
	// Stock inquiry per location (display) list box address
	public static final String DO_LOCATIONVIEW = "/stockcontrol/listbox/liststocklocationworkinginquiry/ListStockLocationWorkingInquiry.do";
	//#CM6630
	// Popup address to search for Consignor.
	public static final String DO_SRCH_CONSIGNOR = "/stockcontrol/listbox/liststockconsignor/ListStockConsignor.do";
	//#CM6631
	// Pop-up address for location list search
	public static final String DO_SRCH_LOCATION = "/stockcontrol/listbox/liststocklocation/ListStockLocation.do";
	//#CM6632
	// Popup address for searching item list.
	public static final String DO_SRCH_ITEM = "/stockcontrol/listbox/liststockitem/ListStockItem.do";
	//#CM6633
	// The screen address while invoking the popup screen for searching.
	public static final String DO_SRCH_PROCESS = "/progress.do";
	//#CM6634
	// Maintain the control that invokes the dialog: Print button
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";
	
	//#CM6635
	// Class fields --------------------------------------------------

	//#CM6636
	// Class variables -----------------------------------------------

	//#CM6637
	// Class method --------------------------------------------------

	//#CM6638
	// Constructors --------------------------------------------------

	//#CM6639
	// Public methods ------------------------------------------------

	//#CM6640
	/**
	 * Invoke this before invoking each control event. <BR>
	 * <BR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM6641
			// Obtain parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM6642
			// Store to ViewState
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM6643
			// Set the screen name
			lbl_SettingName.setResourceKey(title);
		}

	}
	
	//#CM6644
	/**
	 * Returning from the Dialog button invokes this method.
	 * Override page_ConfirmBack defined on the page.
	 * <BR>
	 * Summary: When select "yes" in the dialog<BR>
	 * <BR>
	 * 1.Set the focus for the Consignor code.<BR>
	 * 2.Check which dialog returns it.<BR>
	 * 3.Check for selection of "Yes " or "No " clicked in the dialog box.<BR>
	 * 4.Selecting "Yes " starts the schedule.<BR>
	 * 5.In the case of print dialog<BR>
	 *   <DIR>
	 *   5-1.Set the parameter in the input field.<BR>
	 *   5-2.Start print schedule.<BR>
	 *   5-3.Show schedule result to the message area.<BR>
	 *	 </DIR>
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		try
		{
			//#CM6645
			// Set the focus for the Consignor code.
			setFocus(txt_ConsignorCode);

			//#CM6646
			// Check which dialog returns it.
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM6647
			// Clicking "Yes" in the dialog box returns true.
			//#CM6648
			// Clicking "No" in the dialog box returns false.
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM6649
			// Clicking "NO " button closes the process.
			//#CM6650
			// Message set here is not necessary
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
			//#CM6651
			// Turn the flag off after closed the operation of the dialog.
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM6652
		// Start print schedule
		Connection conn = null;
		try
		{
			//#CM6653
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM6654
			// Set the input value for the parameter.
			StockControlParameter[] param = new StockControlParameter[1];
			param[0] = createParameter();

			//#CM6655
			// Start schedule
			WmsScheduler schedule = new LocationWorkingInquirySCH();
			schedule.startSCH(conn, param);
			
			//#CM6656
			// Set the message
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
				//#CM6657
				// Close the connection
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

	//#CM6658
	/**
	 * Initialize the screen <BR>
	 * <BR>
	 * Summary: Display the initial display. Clicking the Clear button also executes this process. <BR>
	 * <DIR>Item name [Initial value]<BR>
	 * <DIR>Consignor code [Execute initial/default display if only one corresponding Consignor exists.] <BR>
	 * Start location [None]<BR>
	 * End location [None] <BR>
	 * Item code [None] <BR>
	 * Piece/CaseDivision [Select "All"] <BR>
	 * </DIR> </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{

		//#CM6659
		// Set the initial value for each input field.
		//#CM6660
		// Consignor code
		txt_ConsignorCode.setText(getConsignorCode());
		//#CM6661
		// Start location
		txt_StartLocation.setText("");
		//#CM6662
		// End location
		txt_EndLocation.setText("");
		//#CM6663
		// Item code
		txt_ItemCode.setText("");
		//#CM6664
		// Case/PieceDivision
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfAppointOff.setChecked(false);

		//#CM6665
		// Move the cursor to the Consignor code.
		setFocus(txt_ConsignorCode);
	}

	//#CM6666
	// Package methods -----------------------------------------------

	//#CM6667
	// Protected methods ---------------------------------------------

	//#CM6668
	/**
	 * Generate parameter object that set input value of the input area<BR>
	 * 
	 * @return parameter object including input value of Input area
	 */
	protected StockControlParameter createParameter()
	{
		StockControlParameter param = new StockControlParameter();
		
		//#CM6669
		// Consignor code
		param.setConsignorCode(txt_ConsignorCode.getText());
		//#CM6670
		// Location No.(Area)
		param.setFromLocationNo(txt_StartLocation.getText());
		param.setToLocationNo(txt_EndLocation.getText());
		//#CM6671
		// Item code
		param.setItemCode(txt_ItemCode.getText());
		//#CM6672
		// Case/Piece division
		if (rdo_CpfAll.getChecked())
		{
			param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_ALL);
		}
		else if (rdo_CpfCase.getChecked())
		{
			param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_CASE);
		
		}
		else if (rdo_CpfPiece.getChecked())
		{
			param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_PIECE);
		
		}
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_NOTHING);
		}
		return param;
	}

	//#CM6673
	// Private methods -----------------------------------------------
	//#CM6674
	/**
	 * This method obtains the Consignor code from the schedule on the initial display. <BR>
	 * <BR>
	 * Summary: Returns the Consignor code obtained from the schedule. <BR>
	 * 
	 * @return Consignor code <BR>
	 *         Return the string of the Consignor code when one corresponding data exists. <BR>
	 *         Return empty character when 0 or multiple corresponding data exist. <BR>
	 * 
	 * @throws Exception Reports all the exceptions.
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StockControlParameter param = new StockControlParameter();

			//#CM6675
			// Obtain the Consignor code from the schedule.
			WmsScheduler schedule = new LocationWorkingInquirySCH();
			param = (StockControlParameter) schedule.initFind(conn, param);

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
			//#CM6676
			// Close the connection
			if (conn != null)
			{
				conn.close();
			}
		}
		return "";
	}

	//#CM6677
	// Event handler methods -----------------------------------------
	//#CM6678
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6679
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6680
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6681
	/**
	 * Clicking on the Clicking the Menu button invokes this. <BR>
	 * <BR>
	 * Summary: Move to menu screen.
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM6682
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6683
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6684
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM6685
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM6686
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM6687
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6688
	/**
	 * Clicking on the Search Consignor Code button invokes this. <BR>
	 * Summary: This Method set search conditions to the parameter, and display Consignor code search listbox by these search conditions.<BR>
	 * <BR>
	 * [parameter] <BR>
	 * <DIR>Consignor code <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		//#CM6689
		// Set the search condition for the Search Consignor screen.
		ForwardParameters param = new ForwardParameters();

		//#CM6690
		// Consignor code
		param.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM6691
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM6692
		// In process screen->Result screen 
		redirect(DO_SRCH_CONSIGNOR, param, DO_SRCH_PROCESS);
	}

	//#CM6693
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_StartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6694
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6695
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM6696
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM6697
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM6698
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchStartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6699
	/**
	 * Clicking on the Search Start Location button invokes this. <BR>
	 * Summary: This Method set search conditions to the parameter, andDisplay the location list search listbox using the search conditions. <BR>
	 * <BR>
	 * [parameter]*Mandatory Input <BR>
	 * <DIR>Consignor code <BR>
	 * Start location <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchStartLocation_Click(ActionEvent e) throws Exception
	{
		//#CM6700
		// Set the search condition for the Search Location List screen.
		ForwardParameters param = new ForwardParameters();

		//#CM6701
		// Consignor code
		param.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM6702
		// Start location
		param.setParameter(ListStockLocationBusiness.STARTLOCATION_KEY, txt_StartLocation.getText());

		//#CM6703
		// Set start flag
		param.setParameter(
			ListStockLocationBusiness.RANGELOCATION_KEY,
			StockControlParameter.RANGE_START);
		
		//#CM6704
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM6705
		// In process screen->Result screen 
		redirect(DO_SRCH_LOCATION, param, DO_SRCH_PROCESS);
	}

	//#CM6706
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6707
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_EndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6708
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6709
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM6710
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM6711
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM6712
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchEndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6713
	/**
	 * Clicking on the Search End Location button invokes this. <BR>
	 * Summary: This Method set search conditions to the parameter, and
	 * Display the location list search listbox using the search conditions. <BR>
	 * <BR>
	 * [parameter]*Mandatory Input <BR>
	 * <DIR>Consignor code <BR>
	 * End location <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchEndLocation_Click(ActionEvent e) throws Exception
	{
		//#CM6714
		// Set the search condition for the Search Location List screen.
		ForwardParameters param = new ForwardParameters();

		//#CM6715
		// Consignor code
		param.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM6716
		// End location
		param.setParameter(ListStockLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getText());

		//#CM6717
		// Set the Close flag.
		param.setParameter(ListStockLocationBusiness.RANGELOCATION_KEY, StockControlParameter.RANGE_END);
		
		//#CM6718
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM6719
		// In process screen->Result screen 
		redirect(DO_SRCH_LOCATION, param, DO_SRCH_PROCESS);
	}

	//#CM6720
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6721
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6722
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM6723
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM6724
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM6725
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6726
	/**
	 * Clicking on the Search Item Code button invokes this. <BR>
	 * Summary: This Method set search conditions to the parameter, and display Item code search listbox by these search conditions.<BR>
	 * <BR>
	 * [parameter] <BR>
	 * <DIR>Consignor code <BR>
	 * Start location <BR>
	 * End location <BR>
	 * Item code <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM6727
		// Set the search condition for the Search Location List screen.
		ForwardParameters param = new ForwardParameters();

		//#CM6728
		// Consignor code
		param.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		//#CM6729
		// Start location
		param.setParameter(ListStockLocationBusiness.STARTLOCATION_KEY, txt_StartLocation.getText());

		//#CM6730
		// End location
		param.setParameter(ListStockLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getText());

		//#CM6731
		// Item code
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());

		//#CM6732
		// Area type flag (Skip ASRS on list display)
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);

		//#CM6733
		// In process screen->Result screen 
		redirect(DO_SRCH_ITEM, param, DO_SRCH_PROCESS);
	}

	//#CM6734
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6735
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6736
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM6737
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_Case_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6738
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_Case_Click(ActionEvent e) throws Exception
	{
	}

	//#CM6739
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_Piece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6740
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_Piece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM6741
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_AppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6742
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_AppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM6743
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6744
	/** 
	 * Clicking on the Print button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the input item (count) in the input area for the parameter and obtains the print count
	 * Displays the dialog box to allow to confirm to print it or not.<BR>
	 * <BR>
	 * 1.Check for input and the count of print targets.<BR>
	 * 2-1.Displays the dialog box to allow to confirm it if the print target data found.<BR>
	 * <DIR>
	 *   "There are xxx (number) target data. Do you print it?"<BR>
	 * </DIR>
	 * 2-2.If no print target data<BR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		//#CM6745
		// Set the focus for the Consignor code.
		setFocus(txt_ConsignorCode);
		//#CM6746
		// Input check
		txt_ConsignorCode.validate();
		txt_StartLocation.validate(false);
		txt_EndLocation.validate(false);
		txt_ItemCode.validate(false);

		//#CM6747
		// Start Location is smaller than End Location
		if (!StringUtil.isBlank(txt_StartLocation.getText())
			&& !StringUtil.isBlank(txt_EndLocation.getText()))
		{
			if (txt_StartLocation.getText().compareTo(txt_EndLocation.getText()) > 0)
			{
				//#CM6748
				// 6023357=Enter smaller value than End Location in Start Location field.
				message.setMsgResourceKey("6023357");
				return;
			}
		}

		Connection conn = null;
		try
		{
			//#CM6749
			// Obtain the connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM6750
			// Set the schedule parameter
			StockControlParameter param = createParameter();

			//#CM6751
			// Start schedule
			WmsScheduler schedule = new LocationWorkingInquirySCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM6752
				// Hit MSG-W0061={0} count(s)<BR>Do you print it out?
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM6753
				// Store the fact that the dialog was displayed via the Print button.
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM6754
				// Set the message
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
				//#CM6755
				// Close the connection
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

	//#CM6756
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6757
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM6758
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6759
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM6760
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6761
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM6762
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6763
	/**
	 * Clicking on the Clear button invokes this. <BR>
	 * <BR>
	 * Summary: Clears the input area. <BR>
	 * <BR>
	 * <DIR>1.Initialize the screen. <BR>
	 * Enable to refer to the <CODE>page_Load (ActionEvent e)</CODE> method for the initial value. <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM6764
		// Invoke the method to display the initial display.
		page_Load(e);
	}

	//#CM6765
	/**
	 * Invoke this method when returning from the popup window. <BR>
	 * Override page_DlgBack defined on the page.<BR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();

		//#CM6766
		// Obtain the parameter selected in the listbox.
		//#CM6767
		// Consignor code
		String _consignorcode = param.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);

		//#CM6768
		// Start location
		String _startlocation = param.getParameter(ListStockLocationBusiness.STARTLOCATION_KEY);

		//#CM6769
		// End location
		String _endlocation = param.getParameter(ListStockLocationBusiness.ENDLOCATION_KEY);

		//#CM6770
		// Item code
		String _itemcd = param.getParameter(ListStockItemBusiness.ITEMCODE_KEY);

		//#CM6771
		// when parameter is not empty
		//#CM6772
		// Consignor code
		if (!StringUtil.isBlank(_consignorcode))
		{
			txt_ConsignorCode.setText(_consignorcode);
		}
		//#CM6773
		// Start location
		if (!StringUtil.isBlank(_startlocation))
		{
			txt_StartLocation.setText(_startlocation);
		}
		//#CM6774
		// End location
		if (!StringUtil.isBlank(_endlocation))
		{
			txt_EndLocation.setText(_endlocation);
		}
		//#CM6775
		// Item code
		if (!StringUtil.isBlank(_itemcd))
		{
			txt_ItemCode.setText(_itemcd);
		}

		//#CM6776
		// Set the cursor on the Consignor code.
		setFocus(txt_ConsignorCode);
	}
	//#CM6777
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM6778
	/**
	 * Clicking on the Display button invokes this. <BR>
	 * Summary: Sets the input item (count) in the input area for the parameter and displays the stock inquiry (by location) listbox in another screen. <BR>
	 * <BR>
	 * [parameter]*Mandatory Input <BR>
	 * <DIR>Consignor code* <BR>
	 * Start location <BR>
	 * End location <BR>
	 * Item code <BR>
	 * Case/PieceDivision* <BR>
	 * </DIR></BR>
	 * 
	 * @param e ActionEvent This is the class to store event info.
	 * @throws Exception Reports all the exceptions.
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		//#CM6779
		// Declare the instance to set the search conditions.
		ForwardParameters forwardParam = new ForwardParameters();

		//#CM6780
		// Set Consignor code
		forwardParam.setParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM6781
		// Set the Start Location.
		forwardParam.setParameter(ListStockLocationBusiness.STARTLOCATION_KEY, txt_StartLocation.getText());
		//#CM6782
		// Set the End Location.
		forwardParam.setParameter(ListStockLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getText());
		//#CM6783
		// Set Item code
		forwardParam.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM6784
		// Set Case/PieceDivision
		if (rdo_CpfAll.getChecked())
		{
			forwardParam.setParameter(ListStockLocationBusiness.CASEPIECEFLAG_KEY, StockControlParameter.CASEPIECE_FLAG_ALL);
		}
		else if (rdo_CpfCase.getChecked())
		{
			forwardParam.setParameter(ListStockLocationBusiness.CASEPIECEFLAG_KEY, StockControlParameter.CASEPIECE_FLAG_CASE);
		}
		else if (rdo_CpfPiece.getChecked())
		{
			forwardParam.setParameter(ListStockLocationBusiness.CASEPIECEFLAG_KEY, StockControlParameter.CASEPIECE_FLAG_PIECE);
		}
		else if (rdo_CpfAppointOff.getChecked())
		{
			forwardParam.setParameter(ListStockLocationBusiness.CASEPIECEFLAG_KEY, StockControlParameter.CASEPIECE_FLAG_NOTHING);
		}

		//#CM6785
		// Display the stock inquiry listbox by location.
		redirect(DO_LOCATIONVIEW, forwardParam, DO_SRCH_PROCESS);
	}
}
//#CM6786
//end of class
