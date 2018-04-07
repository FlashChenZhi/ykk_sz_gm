// $Id: AsLocationWorkingInquiryBusiness.java,v 1.2 2006/10/26 04:31:49 suresh Exp $

//#CM35720
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.asrslocationworkinginquiry;
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
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocation.ListAsLocationBusiness;
import jp.co.daifuku.wms.asrs.schedule.AsLocationWorkingInquirySCH;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;


//#CM35721
/**
 * Designer : Y.Osawa <BR>
 * Maker : Y.Osawa <BR>
 * <BR>
 * The stock inquiry screen class according to the ASRS location.<BR>
 * Hand over the parameter to the schedule which does the stock inquiry according to the ASRS location.<BR>
 * and transaction commit and rollback are done in this screen <BR>
 * <BR>
 * The following process are called in this class <BR>
 * <BR>
 * 1.display button click process( <CODE>btn_PDisplay_Click</CODE> method ) <BR>
 * <BR>
 * <DIR>
 * 		The schedule retrieves data for the display based on the input contents set from the screen to a parameter.<BR>
 * 		Receive data for the stock list screen according to the ASRS location from the schedule, and call the stock list according to the ASRS location. <BR>
 * 		<BR>
 * 		[parameter] *mandatory input <BR>
 * 		<BR>
 * 		<DIR>
 * 			warehouse * <BR>
 * 			consignor code *<BR>
 * 			start location <BR>
 * 			end location <BR>
 * 			item code <BR>
 * 			case/piece flag *<BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * 2.Processing when Print button is pressed(<CODE>btn_Print_Click<CODE>)<BR>
 * <BR>
 * <DIR>
 * 		set the input contents from the screen to a parameter The schedule retrieves and prints data based on the parameter.  <BR>
 * 		The schedule must return true when it succeeds in the print and return false when failing.  <BR>
 * 		<BR>
 * 		[parameter] *mandatory input <BR>
 * 		<DIR>
 * 			warehouse * <BR>
 * 			consignor code *<BR>
 * 			start location <BR>
 * 			end location <BR>
 * 			item code <BR>
 * 			case/piece flag *<BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/27</TD><TD>T.Uehata</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 04:31:49 $
 * @author $Author: suresh $
 */
public class AsLocationWorkingInquiryBusiness extends AsLocationWorkingInquiry implements WMSConstants
{
	//#CM35722
	// Address transition ahead
	//#CM35723
	/**
	 * Stock inquiry (display) list box address according to location
	 */
	public static final String DO_LOCATIONVIEW = "/asrs/listbox/listasrslocationworkinginquiry/ListAsLocationWorkingInquiry.do";
	
	//#CM35724
	/**
	 * Consignor retrieval pop up address
	 */
	public static final String DO_SRCH_CONSIGNOR = "/asrs/listbox/listasrsconsignor/ListAsConsignor.do";
	
	//#CM35725
	/**
	 * Location list retrieval pop up address
	 */
	public static final String DO_SRCH_LOCATION = "/asrs/listbox/listasrslocation/ListAsLocation.do";
	
	//#CM35726
	/**
	 * Item list retrieval pop up address
	 */
	public static final String DO_SRCH_ITEM = "/asrs/listbox/listasrsitem/ListAsItem.do";
	
	//#CM35727
	/**
	 * Screen address when retrieval pop up is being called
	 */
	public static final String DO_SRCH_PROCESS = "/progress.do";
	
	//#CM35728
	/**
	 * The dialog called from which control to be maintained:Print button
	 */
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";
	
	//#CM35729
	// Class fields --------------------------------------------------

	//#CM35730
	// Class variables -----------------------------------------------

	//#CM35731
	// Class method --------------------------------------------------

	//#CM35732
	// Constructors --------------------------------------------------

	//#CM35733
	// Public methods ------------------------------------------------

	//#CM35734
	/**
	 * call this before calling the respective control events<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM35735
			//fetch parameter
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM35736
			//save to viewstate
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM35737
			//set screen name
			lbl_SettingName.setResourceKey(title);
		}

	}
	
	//#CM35738
	/**
	 * When returning from the dialog button, this method is called. 
	 * Override page_Confirm Back defined in Page. 
	 * <BR>
	 * Abstract :Execute the processing of the correspondence when [Yes] is selected by the dialog. <BR>
	 * <BR>
	 * 1.Check from which dialog return. <BR>
	 * 2.Check whether the selected item from dialog is [Yes] or [No].<BR>
	 * 3.Begin scheduling when yes is selected. <BR>
	 * 4.In case of Print Dialog<BR>
	 *   <DIR>
	 *   4-1.Set the input item in the parameter. <BR>
	 *   4-2.Start the print schedule. <BR>
	 *   4-3.Display the result of the schedule in the message area. <BR>
	 *	 </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		try
		{
			//#CM35739
			// move cursor to warehouse
			setFocus(pul_WareHouse);			

			//#CM35740
			// Check from which dialog return. 
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM35741
			// True when pushed button is [True]
			//#CM35742
			// False when [No] is pressed
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM35743
			// End processing when [No] is pressed. 
			//#CM35744
			// The set of the message here is unnecessary. 
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
			//#CM35745
			// Turn off the flag because the operation of the dialog ended. 
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM35746
		// Start the print schedule. 
		Connection conn = null;
		try
		{
			//#CM35747
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM35748
			// set input value to parameter
			AsScheduleParameter[] param = new AsScheduleParameter[1];
			param[0] = createParameter();

			//#CM35749
			// start schedule
			WmsScheduler schedule = new AsLocationWorkingInquirySCH();
			schedule.startSCH(conn, param);
			
			//#CM35750
			// set message
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
				//#CM35751
				// close the connection object
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

	//#CM35752
	/**
	 * initial screen display<BR>
	 * <BR>
	 * overview ::initial screen display <BR>
	 * <DIR>
	 * 		item name[initial value] <BR>
	 * 		<DIR>
	 * 			warehouse[First display the retrieval Warehouse] <BR>
	 * 			consignor code[if search results a single consignor, display it] <BR>
	 * 			start location[nil] <BR>
	 * 			end location[nil] <BR>
	 * 			item code[nil] <BR>
	 * 			Case/Piece flag[All] <BR>
	 * 		</DIR>
	 * </DIR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{

		Connection conn = null;
		try
		{	
			//#CM35753
			// fetch Http Locale
			Locale locale = this.getHttpRequest().getLocale();
			//#CM35754
			//fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	
			//#CM35755
			//set terminal no.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo)this.getUserInfo());
			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());
		
			//#CM35756
			// Set an initial value in each input field. 
			//#CM35757
			// pull down display data(Warehouse)
			String[] whno = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);
			//#CM35758
			//set pulldown data
			PulldownHelper.setPullDown(pul_WareHouse, whno);
			//#CM35759
			// 	set initial focus to warehouse
			setFocus(pul_WareHouse);
			//#CM35760
			// initial display
			setFirstDisp();
		}
		catch (Exception ex)
		{
			//#CM35761
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
				//#CM35762
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

	//#CM35763
	// Package methods -----------------------------------------------

	//#CM35764
	// Protected methods ---------------------------------------------

	//#CM35765
	/**
	 * create a parameter object with input area value <BR>
	 * @return parameter object containing the input value
	 * @throws Exception report all the exceptions
	 */
	protected AsScheduleParameter createParameter() throws Exception
	{
		AsScheduleParameter param = new AsScheduleParameter();

		//#CM35766
		// warehouse
		param.setAreaNo(pul_WareHouse.getSelectedValue());
		//#CM35767
		// consignor code
		param.setConsignorCode(txt_ConsignorCode.getText());
		//#CM35768
		// location no.(range)
		param.setFromLocationNo(DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_StartLocation.getString()));
		param.setToLocationNo(DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_EndLocation.getString()));
		//#CM35769
		// item code
		param.setItemCode(txt_ItemCode.getText());
		//#CM35770
		// Case piece flag
		if (rdo_CpfAll.getChecked())
		{
			param.setCasePieceFlag(AsScheduleParameter.CASEPIECE_FLAG_ALL);
		}
		else if (rdo_CpfCase.getChecked())
		{
			param.setCasePieceFlag(AsScheduleParameter.CASEPIECE_FLAG_CASE);
		
		}
		else if (rdo_CpfPiece.getChecked())
		{
			param.setCasePieceFlag(AsScheduleParameter.CASEPIECE_FLAG_PIECE);
		
		}
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setCasePieceFlag(AsScheduleParameter.CASEPIECE_FLAG_NOTHING);
		}
		
		return param;
	}

	//#CM35771
	// Private methods -----------------------------------------------
	//#CM35772
	/**
	 * This method initializes screen<BR>
	 * <BR>
	 * overview ::initial screen display <BR>
	 * and set the cursor in warehouse<BR>
	 * <DIR>
	 * 		[parameter] <BR>
	 * 		<DIR>
	 * 			item name[initial value] <BR>
	 * 			<DIR>
	 * 				consignor code[Default display when there is only one corresponding consignorDefault display.]<BR>
	 * 				start location[nil]<BR>
	 * 				end location[nil]<BR>
	 * 				item code[nil]<BR>
	 * 				case piece flag [ALL]<BR>
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
			//#CM35773
			// move cursor to warehouse
			setFocus(pul_WareHouse);
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			AsScheduleParameter param = new AsScheduleParameter();

			//#CM35774
			// Acquire the consignor code from the schedule.
			WmsScheduler schedule = new AsLocationWorkingInquirySCH();
			param = (AsScheduleParameter) schedule.initFind(conn, param);

			//#CM35775
			// display customer code in the init screen, when there is single record
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
			else
			{
				txt_ConsignorCode.setText("");
			}
			//#CM35776
			// warehouse
			pul_WareHouse.setSelectedIndex(0);
			//#CM35777
			// start location
			txt_StartLocation.setText("");
			//#CM35778
			// end location
			txt_EndLocation.setText("");
			//#CM35779
			// item code
			txt_ItemCode.setText("");
			//#CM35780
			// case piece flag
			rdo_CpfAll.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);		
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM35781
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

	//#CM35782
	// Event handler methods -----------------------------------------
	//#CM35783
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35784
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35785
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35786
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

	//#CM35787
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35788
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Change(ActionEvent e) throws Exception
	{
	}

	//#CM35789
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35790
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35791
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM35792
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM35793
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM35794
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35795
	/**
	 * called when "consignor code" search button is clicked <BR>
	 * <BR>
	 * Abstract : This method displays the consignor code retrieval list box by the set search condition in parameter and the search condition. <BR>
	 * <BR>
	 * [parameter] <BR>
	 * <DIR>
	 * 		warehouse <BR>
	 * 		consignor code <BR>
	 * </DIR>
	 * </BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_PSearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		//#CM35796
		// set search conditions to consignor search screen
		ForwardParameters param = new ForwardParameters(); 
		
		//#CM35797
		// Warehouse No.
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM35798
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM35799
		// search flag
		param.setParameter(ListAsConsignorBusiness.SEARCHITEM_KEY, AsScheduleParameter.SEARCHFLAG_STOCK);

		//#CM35800
		// in process screen -> result screen
		redirect(DO_SRCH_CONSIGNOR, param, DO_SRCH_PROCESS);
	}

	//#CM35801
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_StartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35802
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35803
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM35804
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM35805
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StartLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM35806
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchStartLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35807
	/**
	 * called when "start location" button is clicked <BR>
	 * <BR>
	 * Abstract : This method displays the location retrieval list box by the set search condition in parameter and the search condition. <BR>
	 * <BR>
	 * [parameter] <BR>
	 * <DIR>
	 * 		warehouse <BR>
	 * 		consignor code <BR>
	 * 		start location <BR>
	 * </DIR>
	 * </BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_PSearchStartLocation_Click(ActionEvent e) throws Exception
	{
		//#CM35808
		// Set the search condition on the location list retrieval screen. 
		ForwardParameters param = new ForwardParameters();

		//#CM35809
		// Warehouse No.
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM35810
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM35811
		// start location
		param.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, 
					DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), 
					txt_StartLocation.getString()));
		//#CM35812
		// set Start flag
		param.setParameter(ListAsLocationBusiness.RANGELOCATION_KEY, AsScheduleParameter.RANGE_START);
		//#CM35813
		// search flag
		param.setParameter(ListAsLocationBusiness.SEARCHITEM_KEY, AsScheduleParameter.SEARCHFLAG_STOCK);
		
		//#CM35814
		// in process screen -> result screen
		redirect(DO_SRCH_LOCATION, param, DO_SRCH_PROCESS);
	}

	//#CM35815
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35816
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_EndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35817
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35818
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM35819
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM35820
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EndLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM35821
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchEndLocation_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35822
	/**
	 * called when "end location" button is clicked <BR>
	 * <BR>
	 * Abstract : Display the location list retrieval list box by the set search condition in parameter and the search condition. <BR>
	 * <BR>
	 * [parameter] <BR>
	 * <DIR>
	 * 		warehouse <BR>
	 * 		consignor code <BR>
	 * 		end location <BR>
	 * </DIR>
	 * </BR>
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchEndLocation_Click(ActionEvent e) throws Exception
	{
		//#CM35823
		// Set the search condition on the location list retrieval screen. 
		ForwardParameters param = new ForwardParameters();

		//#CM35824
		// Warehouse No.
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM35825
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM35826
		// end location
		param.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, 
					DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), 
					txt_EndLocation.getString()));
		//#CM35827
		// Set End flag
		param.setParameter(ListAsLocationBusiness.RANGELOCATION_KEY, AsScheduleParameter.RANGE_END);
		//#CM35828
		// search flag
		param.setParameter(ListAsLocationBusiness.SEARCHITEM_KEY, AsScheduleParameter.SEARCHFLAG_STOCK);
		
		//#CM35829
		// in process screen -> result screen
		redirect(DO_SRCH_LOCATION, param, DO_SRCH_PROCESS);
	}

	//#CM35830
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35831
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35832
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM35833
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM35834
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM35835
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35836
	/**
	 * This process is called upon clicking item code search button <BR>
	 * <BR>
	 * Abstract : Display the commodity code retrieval list box by the set search condition in parameter and the search condition. <BR>
	 * <BR>
	 * [parameter] <BR>
	 * <DIR>
	 * 		warehouse <BR>
	 * 		consignor code <BR>
	 * 		start location <BR>
	 * 		end location <BR>
	 * 		item code <BR>
	 * </DIR>
	 * </BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		//#CM35837
		// set search result to item code search screen
		ForwardParameters param = new ForwardParameters();

		//#CM35838
		// Warehouse No.
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM35839
		// consignor code
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM35840
		// start location
		param.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, 
					DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), 
					txt_StartLocation.getString()));
		//#CM35841
		// end location
		param.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, 
					DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), 
					txt_EndLocation.getString()));
		//#CM35842
		// item code
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM35843
		// search flag
		param.setParameter(ListAsLocationBusiness.SEARCHITEM_KEY, AsScheduleParameter.SEARCHFLAG_STOCK);

		//#CM35844
		// in process screen -> result screen
		redirect(DO_SRCH_ITEM, param, DO_SRCH_PROCESS);
	}

	//#CM35845
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35846
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35847
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM35848
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_Case_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35849
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_Case_Click(ActionEvent e) throws Exception
	{
	}

	//#CM35850
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_Piece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35851
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_Piece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM35852
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_AppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35853
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_Cpf_AppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM35854
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35855
	/** 
	 * It is called when Print button is pressed.<BR>
	 * <BR>
	 * Abstract : The input item of input area is set in the parameter and display confirmation dialog
	 * whether print after the print number is acquired. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.set the cursor in warehouse input<BR>
	 * 		2.Check the input item of input area. <BR>
	 * 		3.Check the number of print objects. <BR>
	 * 		4-1.Display confirmation dialog when there is data for print<BR>
	 * 		4-2.Acquire the message when there is no data for the print and display it. <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		//#CM35856
		// move cursor to warehouse
		setFocus(pul_WareHouse);		// 入力チェック
		txt_ConsignorCode.validate();
		txt_StartLocation.validate(false);
		txt_EndLocation.validate(false);
		txt_ItemCode.validate(false);

		//#CM35857
		// The Start location is smaller than end location. 
		if (!StringUtil.isBlank(txt_StartLocation.getText())
			&& !StringUtil.isBlank(txt_EndLocation.getText()))
		{
			if (txt_StartLocation.getText().compareTo(txt_EndLocation.getText()) > 0)
			{
				//#CM35858
				// 6023357=start location should be lesser than end location
				message.setMsgResourceKey("6023357");
				return;
			}
		}

		Connection conn = null;
		try
		{
			//#CM35859
			// fetch connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM35860
			// Set the schedule parameter. 
			AsScheduleParameter param = createParameter();

			//#CM35861
			//start schedule
			WmsScheduler schedule = new AsLocationWorkingInquirySCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM35862
				// MSG-W0061={0}The matter corresponded. <BR> Do print?
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM35863
				// Memorize the dialog display by Print button. 
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM35864
				// set message
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
				//#CM35865
				// close the connection object
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

	//#CM35866
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35867
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	//#CM35868
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35869
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	//#CM35870
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35871
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	//#CM35872
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35873
	/** 
	 * called when clear button is clicked<BR>
	 * <BR>
	 * Abstract :clear the input area<BR>
	 * <BR>
	 * <DIR>
	 * 		1.initialize input area<BR>
	 * 		2.set the cursor in warehouse<BR>
	 * 		<BR>
	 * 		item[initial value] <BR>
	 * 		<DIR>
	 * 			warehouse[First display the retrieval Warehouse] <BR>
	 * 			consignor code[if search results a single consignor, display it] <BR>
	 * 			start location[nil] <BR>
	 * 			end location[nil] <BR>
	 * 			item code[nil] <BR>
	 * 			Case/Piece flag[All] <BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM35874
		// initial display
		setFirstDisp();
	}

	//#CM35875
	/**
	 * This method is called when returning from popup window<BR>
	 * override <CODE>page_DlgBack</CODE> defined in <CODE>Page</CODE><BR>
	 * <BR>
	 * overview ::fetch the value from search screen and set it<BR>
	 * <BR>
	 * <DIR>
	 *      1.fetch the value set from popup search screen<BR>
	 *      2.set value if it is not null<BR>
	 *      3.set the cursor in warehouse<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();

		//#CM35876
		// fetch parameter selected from listbox
		//#CM35877
		// consignor code
		String _consignorcode = param.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);

		//#CM35878
		// start location
		String _startlocation = param.getParameter(ListAsLocationBusiness.STARTLOCATION_KEY);

		//#CM35879
		// end location
		String _endlocation = param.getParameter(ListAsLocationBusiness.ENDLOCATION_KEY);

		//#CM35880
		// item code
		String _itemcd = param.getParameter(ListAsItemBusiness.ITEMCODE_KEY);

		//#CM35881
		// Display the parameter on the screen when the parameter is not empty. 
		//#CM35882
		// consignor code
		if (!StringUtil.isBlank(_consignorcode))
		{
			txt_ConsignorCode.setText(_consignorcode);
		}
		//#CM35883
		// start location
		if (!StringUtil.isBlank(_startlocation))
		{
			txt_StartLocation.setText(DisplayText.formatLocationnumber(_startlocation));
		}
		//#CM35884
		// end location
		if (!StringUtil.isBlank(_endlocation))
		{
			txt_EndLocation.setText(DisplayText.formatLocationnumber(_endlocation));
		}
		//#CM35885
		// item code
		if (!StringUtil.isBlank(_itemcd))
		{
			txt_ItemCode.setText(_itemcd);
		}

		//#CM35886
		// The cursor is set in Warehouse.
		setFocus(pul_WareHouse);
	}
	//#CM35887
	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM35888
	/**
	 * It is called when the display button is pressed.<BR>
	 * <BR>
	 * Abstract : Set the input item of input area in the parameter, and display the stock list box according to the ASRS location on another screen.<BR>
	 * <BR>
	 * [parameter]*mandatory input <BR>
	 * <DIR>
	 * 		warehouse * <BR>
	 * 		consignor code *<BR>
	 * 		start location <BR>
	 * 		end location <BR>
	 * 		item code <BR>
	 * 		case/piece flag *<BR>
	 * </DIR>
	 * </BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		//#CM35889
		// The instance to set the search condition is declared. 
		ForwardParameters forwardParam = new ForwardParameters();
		//#CM35890
		// Warehouse No.
		forwardParam.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		//#CM35891
		// Set Consignor code
		forwardParam.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM35892
		// Set Start location
		forwardParam.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, 
					DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), 
					txt_StartLocation.getString()));
		//#CM35893
		// Set End location
		forwardParam.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, 
					DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), 
					txt_EndLocation.getString()));
		//#CM35894
		// Set Item code
		forwardParam.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		//#CM35895
		// Set Case/Piece flag
		if (rdo_CpfAll.getChecked())
		{
			forwardParam.setParameter(ListAsLocationBusiness.CASEPIECEFLAG_KEY, AsScheduleParameter.CASEPIECE_FLAG_ALL);
		}
		else if (rdo_CpfCase.getChecked())
		{
			forwardParam.setParameter(ListAsLocationBusiness.CASEPIECEFLAG_KEY, AsScheduleParameter.CASEPIECE_FLAG_CASE);
		}
		else if (rdo_CpfPiece.getChecked())
		{
			forwardParam.setParameter(ListAsLocationBusiness.CASEPIECEFLAG_KEY, AsScheduleParameter.CASEPIECE_FLAG_PIECE);
		}
		else if (rdo_CpfAppointOff.getChecked())
		{
			forwardParam.setParameter(ListAsLocationBusiness.CASEPIECEFLAG_KEY, AsScheduleParameter.CASEPIECE_FLAG_NOTHING);
		}

		//#CM35896
		// Display the stock list list box according to the ASRS location.
		redirect(DO_LOCATIONVIEW, forwardParam, DO_SRCH_PROCESS);
	}

	//#CM35897
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}

	//#CM35898
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouse_Server(ActionEvent e) throws Exception
	{
	}


}
//#CM35899
//end of class
