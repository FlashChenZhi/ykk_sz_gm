// $Id: RetrievalShortageInquiryBusiness.java,v 1.2 2006/11/13 08:22:04 suresh Exp $

//#CM694698
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.retrievalshortageinquiry;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.PullDownItem;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.PulldownHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.system.display.web.listbox.listretrievalshortage.ListRetrievalShortageBusiness;
import jp.co.daifuku.wms.base.system.display.web.listbox.listsystemconsignor.ListSystemConsignorBusiness;
import jp.co.daifuku.wms.base.system.schedule.RetrievalShortageInquirySCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM694699
/**
 * Designer : Y.Okamura<BR>
 * Maker : Y.Okamura<BR>
 * <BR>
 * Allow this class to print a Shortage checklist.<BR>
 * Pass the parameter to the schedule that executes the Shortage checklist process.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * <DIR>
 * 1.Process by clicking "Display" button(<CODE>btn_PDisplay_Click</CODE>)<BR>
 * <BR>
 * <DIR>
 * 	Set the contents entered via screen for the parameter.
 * 	Allow the schedule to search for the data to be displayed based on the parameter and displays the result on the popup screen.<BR>
 * 	<BR>
 * 	[Parameter] *Mandatory Input
 * 	<DIR>
 * 		Added date*<BR>
 * 		Consignor Code<BR>
 *		Aggregation Conditions*<BR>
 * 	</DIE>
 * </DIR>
 * <BR>
 * 2.Process by clicking "Print" button(<CODE>btn_Print_Click</CODE>)<BR>
 * <BR>
 * <DIR>
 *  Set the contents entered via screen for the parameter.
 *  Allow the schedule to search for the data based on the parameter and print it.<BR>
 *  Return true when the schedule completed printing successfully, or return false when it failed.<BR>
 *  <BR>
 *  [Parameter] *Mandatory Input<BR>
 *  <DIR>
 * 		Added date*<BR>
 * 		Consignor Code<BR>
 *		Aggregation Conditions*<BR>
 * 	</DIR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/02/10</TD><TD>Y.Okamura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:22:04 $
 * @author  $Author: suresh $
 */
public class RetrievalShortageInquiryBusiness extends RetrievalShortageInquiry implements WMSConstants
{
	//#CM694700
	// Class fields --------------------------------------------------
	//#CM694701
	/** 
	 * For blank in Added Date/Time.
	 */
	public final String BLANK_DATE = "                   ";
	
	//#CM694702
	/**
	 * Shortage List URI of Picking plan data
	 */
	public final String URI_SHORTAGE_CHECK = "/system/listbox/listretrievalshortage/ListRetrievalShortage.do";
	
	//#CM694703
	/**
	 * URI Listbox for showing the "Consignor list"
	 */
	public final String URI_CONSIGNOR_SEARCH = "/system/listbox/listsystemconsignor/ListSystemConsignor.do";
	
	//#CM694704
	/**
	 * Next Screen URI
	 */
	public final String URI_PROGRESS = "/progress.do";
	//#CM694705
	// Maintain the control that invokes the dialog: Print button
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";

	//#CM694706
	// Class variables -----------------------------------------------

	//#CM694707
	// Class method --------------------------------------------------

	//#CM694708
	// Constructors --------------------------------------------------

	//#CM694709
	// Public methods ------------------------------------------------
	//#CM694710
	/**
	 * Initialize the screen.
	 * <BR>
	 * <DIR>
	 * Field item: name [Initial Value]
	 *  <DIR>
	 *  	Added date		[Obtain the Added Date/Time from DB. Display blank if no corresponding data]<BR>
	 *  	Consignor Code	[Show the initial display if only one consignor corresponds]<BR>
	 *		Aggregation Conditions	[By Item]<BR>
	 *	</DIR>
	 *	<BR>
	 *  Set the initial value of the focus to Added Date.<BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM694711
		// Initialize the screen.
		setFirstDisp();
	}

	//#CM694712
	/** 
	 * Returning from the Dialog button invokes this method.
	 * Override the page_ConfirmBack defined for Page.
	 * <BR>
	 * Summary: Executes the selected process if selected "Yes" in the dialog.<BR>
	 * <BR>
	 * 1.Check which dialog returns it.<BR>
	 * 2.Check for choice of "Yes" or "No" clicked in the dialog box.<BR>
	 * 3.Selecting "Yes" starts the schedule.<BR>
	 * 4.For print dialog,<BR>
	 *   <DIR>
	 *   4-1.Set the parameter in the input field.<BR>
	 *   4-2.Start the printing schedule.<BR>
	 *   4-3.Display the schedule result in the Message area.<BR>
	 *	 </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		try
		{
			//#CM694713
			// Set focus for If the pulldown is enabled.
			if (pul_RegistDate.getEnabled())
			{
				//#CM694714
				// Set the focus to Added Date.
				setFocus(pul_RegistDate);
			}
			else
			{
				//#CM694715
				// Set the focus for the Consignor code.
				setFocus(txt_ConsignorCode);
			}

			//#CM694716
			// Identify which dialog returns it.
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			//#CM694717
			// Clicking on "Yes" in the dialog box returns true.
			//#CM694718
			// Clicking on "No" in the dialog box returns false.
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			//#CM694719
			// Clicking "NO" button closes the process.
			//#CM694720
			// Require to set no message here.
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
			//#CM694721
			// Ensure to turn the flag OFF after closed the operation of the dialog.
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		//#CM694722
		// Start the printing schedule.
		Connection conn = null;
		try
		{
			//#CM694723
			// Obtain the connection.
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM694724
			// Set the input value for the parameter.
			SystemParameter[] param = new SystemParameter[1];
			param[0] = createParameter();

			//#CM694725
			// Start the schedule.
			WmsScheduler schedule = new RetrievalShortageInquirySCH();
			schedule.startSCH(conn, param);
			
			//#CM694726
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
				//#CM694727
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
	
	//#CM694728
	/**
	 * Invoke this before invoking each control event.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//#CM694729
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM694730
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM694731
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
		}
	}
	
	//#CM694732
	/**
	 * Invoke this method when returning from the popup window.<BR>
	 * Override the page_DlgBack defined for Page.<BR>
	 * <BR>
	 * Summary: Obtains the Returned data in the Search screen and set it.<BR>
	 * <BR><DIR>
	 *      1.Obtain the value returned from the Search popup screen.<BR>
	 *      2.Set the screen if the value is not empty.<BR>
	 * <BR></DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		
		//#CM694733
		// Obtain the parameter selected in the listbox.
		String consignorCode = param.getParameter(ListSystemConsignorBusiness.CONSIGNORCODE_KEY);

		//#CM694734
		// Set a value if not empty.
		//#CM694735
		// Consignor Code
		if (!StringUtil.isBlank(consignorCode))
		{
			
			txt_ConsignorCode.setText(consignorCode);
		}

		//#CM694736
		// Set the initial value for pulldown If the pulldown is enabled.
		if (pul_RegistDate.getEnabled())
		{
			//#CM694737
			// Set for Added Date
			setFocus(pul_RegistDate);
		}
		else
		{
			//#CM694738
			// Set for Consignor Code.
			setFocus(txt_ConsignorCode);
		}			
	}

	//#CM694739
	// Package methods -----------------------------------------------

	//#CM694740
	// Protected methods ---------------------------------------------
	//#CM694741
	/** 
	 * Generate a parameter object for which the input value of the input area is set.<BR>
	 * 
	 * @return Parameter object that contains input values in the input area.
	 */
	protected SystemParameter createParameter()
	{
		//#CM694742
		// Set the schedule parameter.
		SystemParameter param = new SystemParameter();
		param = new SystemParameter();
		param.setRegistDate(getSelectedDate());
		param.setConsignorCode(txt_ConsignorCode.getText());
		if (rdo_DispTypeItem.getChecked())
		{
			param.setDispType(SystemParameter.DISP_TYPE_ITEM);
		}
		else
		{
			param.setDispType(SystemParameter.DISP_TYPE_PLAN);
		}
		return param;	

	}
	
	//#CM694743
	/**
	 * Allow this method to obtain the added date selected in the pulldown menu.
	 * @return Added Date/Time (String)
	 */
	protected String getSelectedDateString()
	{
		//#CM694744
		// Return null if no value exists in the pulldown.
		if (!pul_RegistDate.getEnabled())
		{
			return null;
		}
		
		//#CM694745
		// Obtain the added date/time from the pulldown menu.
		//#CM694746
		// Obtain the first date as selecting 0th date displays the latest date.
		String dateText = null;
		if (pul_RegistDate.getSelectedIndex() == 0)
		{
			dateText = pul_RegistDate.getItem(1).getValue();
		}
		else
		{
			dateText = pul_RegistDate.getSelectedItem().getValue();
		}
		
		return dateText;
	}

	//#CM694747
	/**
	 * Allow this method to obtain the added date selected in the pulldown menu and convert it into date.
	 * @return Added Date/Time (Date)
	 */
	protected Date getSelectedDate()
	{
		//#CM694748
		// Return null if no value exists in the pulldown.
		if (!pul_RegistDate.getEnabled())
		{
			return null;
		}
		
		//#CM694749
		// Obtain the added date/time from the pulldown menu.
		//#CM694750
		// Obtain the first date as selecting 0th date displays the latest date.
		String dateText = null;
		if (pul_RegistDate.getSelectedIndex() == 0)
		{
			dateText = pul_RegistDate.getItem(1).getValue();
		}
		else
		{
			dateText = pul_RegistDate.getSelectedItem().getValue();
		}
		Date selectedDate = WmsFormatter.getDate(dateText, 0, this.getHttpRequest().getLocale());
		
		return selectedDate;
	}

	//#CM694751
	// Private methods -----------------------------------------------
	//#CM694752
	/**
	 * Invoke this method to display/clear the initial display.
	 * @throws Exception Report all exceptions. 
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;
		
		try
		{
		    //#CM694753
		    // Generate pulldown options.
		    PullDownItem pullItem = new PullDownItem();
		    //#CM694754
		    // Set blank for pulldown options.
		    pullItem.setValue(BLANK_DATE);
		    //#CM694755
		    // Add it to the pulldown.
		    pul_RegistDate.addItem(pullItem);
		    //#CM694756
		    // Display the leading item in the pulldown.
		    pul_RegistDate.setSelectedIndex(0);
			//#CM694757
			// Disable a pulldown.
			pul_RegistDate.setEnabled(false);

		    //#CM694758
		    // Display a radio button.
		    rdo_DispTypeItem.setChecked(true);
		    rdo_DispTypePlan.setChecked(false);
			
			//#CM694759
			// Set for Consignor Code.
			setFocus(txt_ConsignorCode);

			//#CM694760
			// Obtain Connection
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new RetrievalShortageInquirySCH();
			SystemParameter param = null;
			param = (SystemParameter) schedule.initFind(conn, (new SystemParameter()));

			//#CM694761
			// For data with only one Consignor code, display the initial display.
			txt_ConsignorCode.setText(param.getConsignorCode());

			//#CM694762
			// Set the Added Date/Time for the pulldown.
			if (param.getRegistDateArray() != null && param.getRegistDateArray().length != 0)
			{
				//#CM694763
				// Initialize the pulldown.
				pul_RegistDate.clearItem();
				
				Date[] dateFromSCH = param.getRegistDateArray();
				Vector dateVec = new Vector();
				//#CM694764
				// LBL-W0446= Latest added date/time		
				dateVec.add(DisplayText.getText("LBL-W0446"));
				
				//#CM694765
				// Disable to set if the Added Date/Time is blank.
				for (int i = 0; i < dateFromSCH.length; i++)
				{
					if (!StringUtil.isBlank(dateFromSCH[i]))
					{
						dateVec.add(WmsFormatter.getDateFormat(dateFromSCH[i], 0, this.getHttpRequest().getLocale()));
					}
				}
				
				String[] resultDate = new String[dateVec.size()];
				dateVec.copyInto(resultDate);
				
				PulldownHelper.setPullDown(pul_RegistDate, resultDate);
				pul_RegistDate.setSelectedIndex(0);
				//#CM694766
				// Enable a pulldown.
				pul_RegistDate.setEnabled(true);
			}
			
			//#CM694767
			// Set the initial value for pulldown If the pulldown is enabled.
			if (pul_RegistDate.getEnabled())
			{
				//#CM694768
				// Set for Added Date
				setFocus(pul_RegistDate);
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
				//#CM694769
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
	
	//#CM694770
	// Event handler methods -----------------------------------------
	//#CM694771
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694772
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694773
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Click(ActionEvent e) throws Exception
	{
	}

	//#CM694774
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694775
	/** 
	 * Clicking on Menu button invokes this.<BR>  
	 * Summary: Shifts to the Menu screen.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM694776
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_ListOutput_Click(ActionEvent e) throws Exception
	{
	}

	//#CM694777
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_RegistDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694778
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_RegistDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694779
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_RegistDate_Change(ActionEvent e) throws Exception
	{
	}

	//#CM694780
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694781
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694782
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM694783
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM694784
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM694785
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DispType_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694786
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DispTypeItem_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694787
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DispTypeItem_Click(ActionEvent e) throws Exception
	{
	}

	//#CM694788
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DispTypePlan_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694789
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DispTypePlan_Click(ActionEvent e) throws Exception
	{
	}

	//#CM694790
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694791
	/** 
	 * Clicking on Display button invokes this.<BR>
	 * <BR>
	 * Summary: Sets the field item entered in the Input area for the parameter and
	 * displays a listbox for checking shortage in the Picking plan data on another screen.<BR>
	 * Executed the input check via the invoked screen.<BR>
	 * This method processes as below.<BR>
	 * <BR>
	 * <DIR>
	 * 1.Display a listbox for showing the "Printed inventory check list form".<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		//#CM694792
		// Set the search conditions into the Search Consignor screen.
		ForwardParameters param = new ForwardParameters();
		//#CM694793
		// Added Date/Time
		param.setParameter(ListRetrievalShortageBusiness.REGISTDATE_KEY, getSelectedDateString());
		//#CM694794
		// Consignor Code
		param.setParameter(ListRetrievalShortageBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM694795
		// Unit of View
		if (rdo_DispTypeItem.getChecked())
		{
			param.setParameter(ListRetrievalShortageBusiness.DISPTYPE_KEY, SystemParameter.DISP_TYPE_ITEM);
		}
		else
		{
			param.setParameter(ListRetrievalShortageBusiness.DISPTYPE_KEY, SystemParameter.DISP_TYPE_PLAN);
		}
		//#CM694796
		// Processing screen ->"Result" screen
		redirect(URI_SHORTAGE_CHECK, param, URI_PROGRESS);
	}

	//#CM694797
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694798
	/** 
	 * Clicking on Print button invokes this.<BR>
	 * <BR>
	 * Summary: Check for input and set the input field item for the parameter, and then start the schedule.<BR>
	 * Allow the schedule to return the number of the entered search conditions.<BR>
	 * <DIR>
	 * 1.Check for input (Mandatory Input, number of characters, and attribution of characters)<BR>
	 * 2.Set the parameter in the input field.<BR>
	 * 3.Start the print schedule and obtain the count of searched data.<BR>
	 * 4.Display a dialog box to allow to confirm it. it.<BR>
	 * "X (number) data matched. Do you print these data?"<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		//#CM694799
		// Set the initial value for pulldown If the pulldown is enabled.
		if (pul_RegistDate.getEnabled())
		{
			//#CM694800
			// Set for Added Date
			setFocus(pul_RegistDate);
		}
		else
		{
			//#CM694801
			// Set for Consignor Code.
			setFocus(txt_ConsignorCode);
		}
		//#CM694802
		// Check for input.
		pul_RegistDate.validate(true);
		txt_ConsignorCode.validate(false);
		
		Connection conn = null;
		
		try
		{
			//#CM694803
			// Obtain the connection.
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//#CM694804
			// Set the input value .
			SystemParameter param = createParameter();
			
			//#CM694805
			// Check the count of data to be printed. If there is such a data,
			//#CM694806
			// display a dialog for confirming to print it.
			//#CM694807
			// If there is no data to be printed, close the process without displaying a dialog.
			WmsScheduler schedule = new RetrievalShortageInquirySCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				//#CM694808
				// MSG-W0061={0} data corresponded.<BR>Do you print it?
				setConfirm("MSG-W0061" + wDelim + reportCount);
				//#CM694809
				// Store the fact that the dialog was displayed via the Print button.
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				//#CM694810
				// Set the message
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
				//#CM694811
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

	//#CM694812
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694813
	/** 
	 * Clicking on Clear button invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area.<BR>
	 * <BR>
	 *  <DIR>
	 *  For the initial value, refer to the <CODE>page_Load(ActionEvent e)</CODE> method.
	 *  </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM694814
		// Initialize the screen.
		setFirstDisp();
	}

	//#CM694815
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Psearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM694816
	/** 
	 * Clicking on Search Consignor Code button invokes this.<BR>
	 * <BR>
	 * Summary: Searches for consignor code.<BR>
	 * <BR>
	 * <DIR>
	 * 		�P.Check for input in the input item (count). <BR>
	 * </DIR>
	 * <DIR>
	 * [Parameter] <BR>
	 * <DIR> 
	 *      Added Date/Time <BR>
	 *      Consignor Code <BR>
	 * </DIR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Psearch_Click(ActionEvent e) throws Exception
	{
		//#CM694817
		// Set the search conditions into the Worker List screen.
		ForwardParameters param = new ForwardParameters();

		//#CM694818
		// Added Date/Time
		param.setParameter(ListSystemConsignorBusiness.REGISTDATE_KEY, getSelectedDateString());
		//#CM694819
		// Consignor Code
		param.setParameter(ListSystemConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		//#CM694820
		// Set the Search table.
		param.setParameter(ListSystemConsignorBusiness.SEARCHCONSIGNOR_KEY, SystemParameter.SEARCHFLAG_SHORTAGE);

		//#CM694821
		// Processing screen ->"Result" screen
		redirect(URI_CONSIGNOR_SEARCH, param, URI_PROGRESS);
	}
}
//#CM694822
//end of class
