// $Id: MessageBusiness.java,v 1.2 2006/11/13 08:21:23 suresh Exp $

//#CM693764
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.message;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.system.schedule.MessageSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM693765
/**
 * Designer :  A.Nagasawa<BR>
 * Maker :     A.Nagasawa<BR>
 * <BR>
 * Allow this class to inquire the Message log.<BR>
 * Set the contents entered via screen for the parameter. Allow the schedule to search data to be displayed based on the parameter.<BR>
 * Receive data to be output to the preset area from the schedule and output it to the preset area.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process by clicking "Display" button(<CODE>btn_View_Click(ActionEvent e)</CODE>method) <BR>
 * <BR>
 * <DIR>
 *   Search through the message.log using the field items entered in the Input area as conditions. display the search results in the View.<BR> 
 * <BR>
 *     [Parameter] *Mandatory Input<BR>
 *     <BR>
 *	   <DIR>
 *	     Starting date/time of searching <BR>
 * 	     End date/time of searching (time) <BR>
 *	     Display condition* <BR>
 *	   </DIR>
 *     <BR>
 *	   [Returned data]<BR>
 *     <BR>
 *	   <DIR>
 *	     Date/Time <BR>
 *	     Content <BR>
 *	  	 Order class <BR>
 *	  	 Message <BR>
 *     <BR>
 *     </DIR>
 *     [Check Process Condition] <BR>
 * <BR>
 * 	   Starting date/time of searching<=End date/time of searching<BR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/06</TD><TD>A.Nagasawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:23 $
 * @author  $Author: suresh $
 */
public class MessageBusiness extends Message implements WMSConstants
{
	//#CM693766
	// Class fields --------------------------------------------------

	//#CM693767
	// Class variables -----------------------------------------------

	//#CM693768
	// Class method --------------------------------------------------

	//#CM693769
	// Constructors --------------------------------------------------

	//#CM693770
	// Public methods ------------------------------------------------

	//#CM693771
	/**
	 * Show the Initial Display.<BR>
	 * <BR>
	 * <DIR>
	 *   Starting date/time of searching [None] <BR>
	 *   End date/time of searching [None] <BR>
	 *   Display condition [All] <BR>
	 *   <BR>
	 *   Set the cursor to Starting date/time of searching.<BR>
	 * </DIR>  
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM693772
		// Set the Display condition to All.
		rdo_DcdAll.setChecked(true);

		//#CM693773
		// Set focus for Starting date/time of searching.
		setFocus(txt_SerchStartDateDate);
	}

	//#CM693774
	/**
	 * Invoke this before invoking each control event.<BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
		{
			//#CM693775
			//Obtain the parameter.
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//#CM693776
			//Maintain it in ViewState.
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//#CM693777
			//Set the screen name.
			lbl_SettingName.setResourceKey(title);
			//#CM693778
			//Set the path to the help file.
			//#CM693779
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
	}

	//#CM693780
	// Package methods -----------------------------------------------

	//#CM693781
	// Protected methods ---------------------------------------------

	//#CM693782
	// Private methods -----------------------------------------------
	//#CM693783
	/**
	 * Set the value for the preset area.
	 * @param listParams Value to be set in the preset area.
	 */
	private void setList(Parameter[] listParams)
	{
		//#CM693784
		// Store the contents.
		String sdcondition = "";

		//#CM693785
		// Set the data in the list cell.
		lst_SMsgLogIqrIqr.clearRow();

		SystemParameter[] viewParam = (SystemParameter[]) listParams;

		//#CM693786
		// Use it for Tool Tip.
		//#CM693787
		// Date/Time
		String tyt_LogDate = DisplayText.getText("LBL-W0039");
		//#CM693788
		// Content
		String tyt_Error = DisplayText.getText("LBL-W0291");
		//#CM693789
		// Class where error occurred.
		String tyt_ErrorClass = DisplayText.getText("LBL-W0302");

		for (int i = 0; i < viewParam.length; i++)
		{
			if (viewParam[i]
				.getError()
				.equals(SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_ATTENTION))
			{
				//#CM693790
				// Caution
				sdcondition = DisplayText.getText("RDB-W0055");
			}
			else if (
				viewParam[i].getError().equals(
					SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_ERROR))
			{
				//#CM693791
				// Error
				sdcondition = DisplayText.getText("RDB-W0057");
			}
			else if (
				viewParam[i].getError().equals(
					SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_INFORMATION))
			{
				//#CM693792
				// Guidance
				sdcondition = DisplayText.getText("RDB-W0054");
			}
			else if (
				viewParam[i].getError().equals(
					SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_WARNING))
			{
				//#CM693793
				// Warning
				sdcondition = DisplayText.getText("RDB-W0056");
			}

			//#CM693794
			// Feed a line.
			lst_SMsgLogIqrIqr.setCurrentRow(i + 1);
			lst_SMsgLogIqrIqr.addRow();

			//#CM693795
			// Set in the list cell.
			lst_SMsgLogIqrIqr.setValue(1, viewParam[i].getMessageLogDate());
			lst_SMsgLogIqrIqr.setValue(2, sdcondition);
			lst_SMsgLogIqrIqr.setValue(3, viewParam[i].getErrorClass());
			lst_SMsgLogIqrIqr.setValue(4, viewParam[i].getMessage());

			//#CM693796
			// Compile the ToolTip data.
			ToolTipHelper toolTip = new ToolTipHelper();
			toolTip.add(tyt_LogDate, viewParam[i].getMessageLogDate());
			toolTip.add(tyt_Error, sdcondition);
			toolTip.add(tyt_ErrorClass, viewParam[i].getErrorClass());

			//#CM693797
			//  Set the TOOL TIP.	
			lst_SMsgLogIqrIqr.setToolTip(i + 1, toolTip.getText());
		}

	}

	//#CM693798
	// Event handler methods -----------------------------------------
	//#CM693799
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693800
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693801
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693802
	/** 
	 * Clicking on Menu button invokes this.<BR>
	 * <BR>
	 * Summary: Allow this method to execute the following processes.<BR>
	 * <BR>
	 * <DIR>
	 *   Shift to the Menu screen.<BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	//#CM693803
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SrchStrtDateTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693804
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SerchStartDateDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693805
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SerchStartDateDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693806
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SerchStartDateDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693807
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SearchStartTimeDateTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693808
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SearchStartTimeDateTime_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693809
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SearchStartTimeDateTime_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693810
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693811
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndSearchDateTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693812
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SerchEndDateDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693813
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SerchEndDateDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693814
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SerchEndDateDate_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693815
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SearchEndTimeDateTime_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693816
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SearchEndTimeDateTime_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693817
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SearchEndTimeDateTime_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693818
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DspCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693819
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DcdAll_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693820
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DcdAll_Click(ActionEvent e) throws Exception
	{
	}

	//#CM693821
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DcdGuidance_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693822
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DcdGuidance_Click(ActionEvent e) throws Exception
	{
	}

	//#CM693823
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DcdAttention_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693824
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DcdAttention_Click(ActionEvent e) throws Exception
	{
	}

	//#CM693825
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DcdWarning_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693826
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DcdWarning_Click(ActionEvent e) throws Exception
	{
	}

	//#CM693827
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DcdError_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693828
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_DcdError_Click(ActionEvent e) throws Exception
	{
	}

	//#CM693829
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693830
	/** 
	 * Clicking on Clear Button for Message Log Inquiry invokes this.<BR>
	 * <BR>
	 * Summary: Clears the input area.<BR>
	 * <BR>
	 * <DIR>
	 *   1.Clear the field item in Input area.<BR>
	 *   2.Set the cursor to Starting date/time of searching.<BR>
	 * </DIR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions. 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		//#CM693831
		// Clear the field item in Input area.
		txt_SerchStartDateDate.setText("");
		txt_SearchStartTimeDateTime.setText("");
		txt_SerchEndDateDate.setText("");
		txt_SearchEndTimeDateTime.setText("");
		rdo_DcdGuidance.setChecked(false);
		rdo_DcdAttention.setChecked(false);
		rdo_DcdWarning.setChecked(false);
		rdo_DcdError.setChecked(false);
		rdo_DcdAll.setChecked(true);

		//#CM693832
		// Set focus for Starting date/time of searching.
		setFocus(txt_SerchStartDateDate);
	}

	//#CM693833
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SMsgLogIqrIqr_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693834
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SMsgLogIqrIqr_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693835
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SMsgLogIqrIqr_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693836
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SMsgLogIqrIqr_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM693837
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SMsgLogIqrIqr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693838
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SMsgLogIqrIqr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM693839
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SMsgLogIqrIqr_Click(ActionEvent e) throws Exception
	{
	}

	//#CM693840
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693841
	/** 
	 * Clicking on Display Button for Message Log Inquiry invokes this.<BR>
	 * <BR>
	 * Summary: Search for a message.log using the input items in the Input area as conditions and display it in the view. <BR>
	 * <BR>
	 * <DIR>
	 *   1.Check for input in the input item (count) in the input area. (Mandatory Input, number of characters, and attribution of characters)<BR>
	 *   2.Start the schedule.<BR>
	 *   3.Display it in the preset area.<BR>
	 *   4.Set the cursor to Starting date/time of searching.<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		//#CM693842
		// Set focus for Starting date/time of searching.
		setFocus(txt_SerchStartDateDate);
		
		Connection conn = null;
		try
		{
			//#CM693843
			// Check for input.
			txt_SerchStartDateDate.validate(false);
			txt_SearchStartTimeDateTime.validate(false);
			txt_SerchEndDateDate.validate(false);
			txt_SearchEndTimeDateTime.validate(false);

			//#CM693844
			// Starting date of searching <= End date of searching
			if (txt_SerchStartDateDate.getDate() != null && txt_SerchEndDateDate.getDate() != null)
			{
				if (txt_SerchStartDateDate.getDate().after(txt_SerchEndDateDate.getDate()))
				{
					//#CM693845
					// 6023182=Starting date of search must precede the end date.
					message.setMsgResourceKey("6023182");
					return;
				}
				else if (txt_SerchStartDateDate.getDate().equals(txt_SerchEndDateDate.getDate()))
				{
					if (txt_SearchStartTimeDateTime.getTime() != null
						&& txt_SearchEndTimeDateTime.getTime() != null)
					{
						if (txt_SearchStartTimeDateTime
							.getTime()
							.after(txt_SearchEndTimeDateTime.getTime()))
						{
							//#CM693846
							// 6023182=Starting date of search must precede the end date.
							message.setMsgResourceKey("6023182");
							return;
						}
					}
				}
			}
			//#CM693847
			//Set the parameter.      
			SystemParameter param = new SystemParameter();
			//#CM693848
			// Start Date (Year/Month/Day)
			param.setFromFindDate(WmsFormatter.toParamDate(txt_SerchStartDateDate.getDate()));
			//#CM693849
			// Start Time
			param.setFromFindTime(WmsFormatter.toParamTime(txt_SearchStartTimeDateTime.getTime()));
			//#CM693850
			// End Year/Month/Day
			param.setToFindDate(WmsFormatter.toParamDate(txt_SerchEndDateDate.getDate()));
			//#CM693851
			// End Time
			param.setToFindTime(WmsFormatter.toParamTime(txt_SearchEndTimeDateTime.getTime()));

			//#CM693852
			// All
			if (rdo_DcdAll.getChecked())
			{
				param.setSelectDisplayCondition_MessageLog(
					SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_ALL);
				rdo_DcdAll.setChecked(true);
				rdo_DcdGuidance.setChecked(false);
				rdo_DcdAttention.setChecked(false);
				rdo_DcdWarning.setChecked(false);
				rdo_DcdError.setChecked(false);
			}
			//#CM693853
			// Guidance
			else if (rdo_DcdGuidance.getChecked())
			{
				param.setSelectDisplayCondition_MessageLog(
					SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_INFORMATION);
				rdo_DcdAll.setChecked(false);
				rdo_DcdGuidance.setChecked(true);
				rdo_DcdAttention.setChecked(false);
				rdo_DcdWarning.setChecked(false);
				rdo_DcdError.setChecked(false);
			}
			//#CM693854
			// Caution
			else if (rdo_DcdAttention.getChecked())
			{
				param.setSelectDisplayCondition_MessageLog(
					SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_ATTENTION);
				rdo_DcdAll.setChecked(false);
				rdo_DcdGuidance.setChecked(false);
				rdo_DcdAttention.setChecked(true);
				rdo_DcdWarning.setChecked(false);
				rdo_DcdError.setChecked(false);
			}
			//#CM693855
			// Warning
			else if (rdo_DcdWarning.getChecked())
			{
				param.setSelectDisplayCondition_MessageLog(
					SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_WARNING);
				rdo_DcdAll.setChecked(false);
				rdo_DcdGuidance.setChecked(false);
				rdo_DcdAttention.setChecked(false);
				rdo_DcdWarning.setChecked(true);
				rdo_DcdError.setChecked(false);

			}
			//#CM693856
			// Error
			else if (rdo_DcdError.getChecked())
			{
				param.setSelectDisplayCondition_MessageLog(
					SystemParameter.SELECTDISPLAYCONDITION_MESSAGELOG_ERROR);
				rdo_DcdAll.setChecked(false);
				rdo_DcdGuidance.setChecked(false);
				rdo_DcdAttention.setChecked(false);
				rdo_DcdWarning.setChecked(false);
				rdo_DcdError.setChecked(true);
			}

			//#CM693857
			// Start the schedule.
			WmsScheduler schedule = new MessageSCH();
			SystemParameter[] viewParam = (SystemParameter[]) schedule.query(conn, param);

			if (viewParam == null || viewParam.length == 0)
			{
				//#CM693858
				//Clear the list cell.
				lst_SMsgLogIqrIqr.clearRow();

				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			//#CM693859
			// Set the list cell.
			setList(viewParam);
			//#CM693860
			// Set the message.
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//#CM693861
				// Close the connection.
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
	}
}
//#CM693862
//end of class
