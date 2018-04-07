// $Id: ListWorkerQtyInquiryBusiness.java,v 1.2 2006/11/13 08:20:52 suresh Exp $

//#CM693022
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.listbox.listworkerqtyinquiry;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.system.display.web.listbox.listworkday.ListWorkDayBusiness;
import jp.co.daifuku.wms.base.system.display.web.listbox.listworker.ListWorkerBusiness;
import jp.co.daifuku.wms.base.system.display.web.listbox.sessionret.SessionWorkerQtyInquiryRet;
import jp.co.daifuku.wms.base.system.display.web.workerqtyinquiry.WorkerQtyInquiryBusiness;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM693023
/**
 * Designer : B.Shibayama <BR>
 * Maker    : B.Shibayama <BR>
 * <BR>
 * Allow this class to provide a Listbox for inquiring the "Result by worker".<BR>
 * Search for the data using the conditions entered via parent screen. <BR>
 * <BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/16</TD><TD>B.Shibayama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:20:52 $
 * @author  $Author: suresh $
 */
public class ListWorkerQtyInquiryBusiness extends ListWorkerQtyInquiry implements WMSConstants
{
	//#CM693024
	// Class fields --------------------------------------------------

	//#CM693025
	/** 
	 * Use this key to pass aggregated view.
	 */
	public static final String GROUPCONDITION_KEY = "GROUPCONDITION_KEY";

	//#CM693026
	// Class variables -----------------------------------------------

	//#CM693027
	// Class method --------------------------------------------------

	//#CM693028
	// Constructors --------------------------------------------------

	//#CM693029
	// Public methods ------------------------------------------------

	//#CM693030
	/**
	 * Initialize the screen. <BR>
	 * <BR>
	 *  Summary: Shows the Initial Display.<BR>
	 * <BR><DIR>
	 *       1.Obtain the parameters passed from the screen. <BR>
	 *       2.Display the title. <BR>
	 *       3.Close the existing sessions. <BR>
	 *       4.Display the search conditions on the screen. <BR>
	 *       5.Obtain the connection. <BR>
	 *       6.Generate an instance of session. <BR>
	 *     </DIR>
	 * [Parameter]<BR>
	 * <DIR>
	 *   Work Status <BR>
	 *	 Start work date <BR>
	 *	 End Work Date <BR>
	 *	 Worker Code <BR>
	 *	 Aggregation Conditions <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		
		//#CM693031
		// Obtain the parameter that was set in the invoking source screen.
		//#CM693032
		// Work Status
		String workdetails = request.getParameter(WorkerQtyInquiryBusiness.WORKDETAILS_KEY);
		//#CM693033
		// Start work date
		String startworkdate = request.getParameter(ListWorkDayBusiness.STARTWORKDAY_KEY);
		//#CM693034
		// End Work Date
		String endworkdate = request.getParameter(ListWorkDayBusiness.ENDWORKDAY_KEY);
		//#CM693035
		// Worker Code
		String workercode = request.getParameter(ListWorkerBusiness.WORKERCODE_KEY);
		//#CM693036
		// Aggregation Conditions
		String groupcondition = request.getParameter(GROUPCONDITION_KEY);
        //#CM693037
        // Set the screen name.
		lbl_ListName.setText(DisplayUtil.getSelectAggregateCondition(groupcondition));

		//#CM693038
		// Use it for classification of types to display a list cell.
		this.viewState.setString(GROUPCONDITION_KEY, groupcondition);

		//#CM693039
		// Check for input

		//#CM693040
		// Check for prohibited character used in Worker Code.
		if (!WmsCheckker
			.charCheck(workercode, lst_SWkrRsltIqr, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM693041
		// Check for the date range.
		if (!WmsCheckker.rangeWorkDateCheck(startworkdate, endworkdate, lst_SWkrRsltIqr, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		//#CM693042
		//Close the connection of object remained in the Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM693043
			//	Close the connection.
			sRet.closeConnection();
			//#CM693044
			// Delete from the session.
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM693045
		// Work type	
		lbl_JavaSetWkCtx.setText(DisplayUtil.getSelectWorkDetail(workdetails));

		//#CM693046
		// Set a value if not empty.
		//#CM693047
		// Start work date
		if (!StringUtil.isBlank(startworkdate))
		{
			txt_FDateStart.setDate(WmsFormatter.toDate(startworkdate));
		}
		//#CM693048
		// End Work Date
		if (!StringUtil.isBlank(endworkdate))
		{
			txt_FDateEnd.setDate(WmsFormatter.toDate(endworkdate));
		}
		//#CM693049
		// Worker Code
		if (!StringUtil.isBlank(workercode))
		{
			lbl_JavaSetWkCd.setText(workercode);
		}
		//#CM693050
		// Aggregation Conditions
		lbl_JavaSetGcd.setText(DisplayUtil.getSelectAggregateCondition(groupcondition));

		//#CM693051
		// Obtain the connection.
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM693052
		// Set the parameter.
		SystemParameter param = new SystemParameter();

		param.setSelectWorkDetail(workdetails);
		param.setFromWorkDate(startworkdate);
		param.setToWorkDate(endworkdate);
		param.setWorkerCode(workercode);
		param.setSelectAggregateCondition(groupcondition);

		//#CM693053
		//Generate a SessionWorkerQtyInquiryRet instance
		SessionWorkerQtyInquiryRet listbox = new SessionWorkerQtyInquiryRet(conn, param);

		this.getSession().setAttribute("LISTBOX", listbox);

		//#CM693054
		//Display the first page.
		setList(listbox, "first");

	}

	//#CM693055
	// Package methods -----------------------------------------------

	//#CM693056
	// Protected methods ---------------------------------------------

	//#CM693057
	// Private methods -----------------------------------------------
	//#CM693058
	/**
	 * Allow this method to change a page. <BR>
	 * @param listbox Session of this listbox
	 * @param actionName actionName for a clicked button
	 * @throws Exception Report all exceptions.
	 */
	private void setList(SessionWorkerQtyInquiryRet listbox, String actionName) throws Exception
	{
		//#CM693059
		// Set the Set the Page info.
		listbox.setActionName(actionName);

		String groupcondition = this.viewState.getString(GROUPCONDITION_KEY);

		//#CM693060
		// Obtain the search result.
		SystemParameter[] param = (SystemParameter[]) listbox.getEntities();
		int len = 0;
		if (param != null)
			len = param.length;
		if (len > 0)
		{
			//#CM693061
			// Set a value for Pager.
			//#CM693062
			// Max count
			pgr_U.setMax(listbox.getLength());
			//#CM693063
			// Count of displayed data per Page
			pgr_U.setPage(listbox.getCondition());
			//#CM693064
			// Start Position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM693065
			// Max count
			pgr_D.setMax(listbox.getLength());
			//#CM693066
			// Count of displayed data per Page
			pgr_D.setPage(listbox.getCondition());
			//#CM693067
			// Start Position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM693068
			// Hide the message.
			lbl_InMsg.setVisible(false);

			//#CM693069
			// Delete all lines.
			lst_SWkrRsltIqr.clearRow();

			//#CM693070
			//Use it in ToolTip.
			//#CM693071
			// Worker Name
			String title_WorkerName = DisplayText.getText("LBL-W0276");

			//#CM693072
			//Total within the period
			if (groupcondition.equals(SystemParameter.SELECTAGGREGATECONDITION_TERM))
			{
				String startTime = "";
				String endTime = "";
				String workTime ="";
				for (int i = 0; i < len; i++)
				{
					//#CM693073
					// Add a line.
					lst_SWkrRsltIqr.addRow();
					//#CM693074
					// Obtain the last data.
					lst_SWkrRsltIqr.setCurrentRow(i + 1);
					//#CM693075
					// Work date 1
					lst_SWkrRsltIqr.setValue(1, DisplayText.getText("LBL-W0078"));
					//#CM693076
					// Worker Code 2 
					lst_SWkrRsltIqr.setValue(2, param[i].getWorkerCode());
					//#CM693077
					// Work type 3 
					lst_SWkrRsltIqr.setValue(3, param[i].getSelectWorkDetail());
					//#CM693078
					// Start Time 4
					if(StringUtil.isBlank(param[i].getWorkStartTime()))
					{
						startTime = DisplayText.getText("LBL-W0078");
					}
					else
					{
						startTime =param[i].getWorkStartTime();
					}
					lst_SWkrRsltIqr.setValue(4, startTime);
					//#CM693079
					// Work Time 5
					if(StringUtil.isBlank(param[i].getWorkTime()))
					{
						workTime = DisplayText.getText("LBL-W0078");
					}
					else
					{
						workTime = param[i].getWorkTime();
					}
					lst_SWkrRsltIqr.setValue(5, workTime);
					//#CM693080
					// Work Quantity 6
					lst_SWkrRsltIqr.setValue(6, Formatter.getNumFormat(param[i].getWorkQty()));
					//#CM693081
					// Work Quantity/h 7
					lst_SWkrRsltIqr.setValue(7,Formatter.getNumFormat(param[i].getWorkQtyPerHour()));
					//#CM693082
					// RFT Machine No.. 8
					lst_SWkrRsltIqr.setValue(8, DisplayText.getText("LBL-W0078"));
					//#CM693083
					// Worker Name 9
					lst_SWkrRsltIqr.setValue(9, param[i].getWorkerName());
					//#CM693084
					// End Time 10
					if(StringUtil.isBlank(param[i].getWorkEndTime()))
					{
						endTime = DisplayText.getText("LBL-W0078");
					}
					else
					{
						endTime = param[i].getWorkEndTime();
					}
					lst_SWkrRsltIqr.setValue(10, endTime);
					//#CM693085
					// Work Count 11
					lst_SWkrRsltIqr.setValue(11, Formatter.getNumFormat(param[i].getWorkCnt()));
					//#CM693086
					// Work Count/h 12
					lst_SWkrRsltIqr.setValue(12,Formatter.getNumFormat(param[i].getWorkCntPerHour()));

					//#CM693087
					//Set the tool tip.
					ToolTipHelper toolTip = new ToolTipHelper();
					//#CM693088
					// Worker Name
					toolTip.add(title_WorkerName, param[i].getWorkerName());

					lst_SWkrRsltIqr.setToolTip(i + 1, toolTip.getText());

				}
			}
			//#CM693089
			//Display the daily total.
			else if (groupcondition.equals(SystemParameter.SELECTAGGREGATECONDITION_DAILY))
			{
				for (int i = 0; i < len; i++)
				{
					//#CM693090
					// Add a line.
					lst_SWkrRsltIqr.addRow();
					//#CM693091
					// Obtain the last data.
					lst_SWkrRsltIqr.setCurrentRow(i + 1);

					//#CM693092
					// Work date 
					lst_SWkrRsltIqr.setValue(1,WmsFormatter.toDispDate(param[i].getWorkDate(),this.getHttpRequest().getLocale()));
					//#CM693093
					// Worker Code 2
					lst_SWkrRsltIqr.setValue(2, param[i].getWorkerCode());
					//#CM693094
					// Work type 3
					lst_SWkrRsltIqr.setValue(3, param[i].getSelectWorkDetail());
					//#CM693095
					// Start Time 4
					lst_SWkrRsltIqr.setValue(4, param[i].getWorkStartTime());
					//#CM693096
					// Work Time 5
					lst_SWkrRsltIqr.setValue(5, param[i].getWorkTime());
					//#CM693097
					// Work Quantity 6
					lst_SWkrRsltIqr.setValue(6, Formatter.getNumFormat(param[i].getWorkQty()));
					//#CM693098
					// Work Quantity/h 7
					lst_SWkrRsltIqr.setValue(7,Formatter.getNumFormat(param[i].getWorkQtyPerHour()));
					//#CM693099
					// RFT Machine No.. 8 x
					lst_SWkrRsltIqr.setValue(8, DisplayText.getText("LBL-W0078"));
					//#CM693100
					// Worker Name 9
					lst_SWkrRsltIqr.setValue(9, param[i].getWorkerName());
					//#CM693101
					// End Time 10
					lst_SWkrRsltIqr.setValue(10, param[i].getWorkEndTime());
					//#CM693102
					// Work Count 11
					lst_SWkrRsltIqr.setValue(11, Formatter.getNumFormat(param[i].getWorkCnt()));
					//#CM693103
					// Work Count/h 12
					lst_SWkrRsltIqr.setValue(12,Formatter.getNumFormat(param[i].getWorkCntPerHour()));
					//#CM693104
					//Set the tool tip.
					ToolTipHelper toolTip = new ToolTipHelper();
					//#CM693105
					// Worker Name
					toolTip.add(title_WorkerName, param[i].getWorkerName());

					lst_SWkrRsltIqr.setToolTip(i + 1, toolTip.getText());

				}
			}
			//#CM693106
			// Display the detail.
			else
			{
				for (int i = 0; i < len; i++)
				{
					//#CM693107
					// Add a line.
					lst_SWkrRsltIqr.addRow();
					//#CM693108
					// Obtain the end line.
					lst_SWkrRsltIqr.setCurrentRow(i + 1);

					//#CM693109
					// Work date 
					lst_SWkrRsltIqr.setValue(1,WmsFormatter.toDispDate(param[i].getWorkDate(),this.getHttpRequest().getLocale()));
					//#CM693110
					// Worker Code 2
					lst_SWkrRsltIqr.setValue(2, param[i].getWorkerCode());
					//#CM693111
					// Work type 3
					lst_SWkrRsltIqr.setValue(3, param[i].getSelectWorkDetail());
					//#CM693112
					// Start Time 4
					lst_SWkrRsltIqr.setValue(4, param[i].getWorkStartTime());
					//#CM693113
					// Work Time 5
					lst_SWkrRsltIqr.setValue(5, param[i].getWorkTime());
					//#CM693114
					// Work Quantity 6
					lst_SWkrRsltIqr.setValue(6, Formatter.getNumFormat(param[i].getWorkQty()));
					//#CM693115
					// Work Quantity/h 7
					lst_SWkrRsltIqr.setValue(7,Formatter.getNumFormat(param[i].getWorkQtyPerHour()));
					//#CM693116
					// RFT Machine No.. 8 
					lst_SWkrRsltIqr.setValue(8, param[i].getTerminalNumber());
					//#CM693117
					// Worker Name 9
					lst_SWkrRsltIqr.setValue(9, param[i].getWorkerName());
					//#CM693118
					// End Time 10
					lst_SWkrRsltIqr.setValue(10, param[i].getWorkEndTime());
					//#CM693119
					// Work Count 11
					lst_SWkrRsltIqr.setValue(11, Formatter.getNumFormat(param[i].getWorkCnt()));
					//#CM693120
					// Work Count/h 12
					lst_SWkrRsltIqr.setValue(12,Formatter.getNumFormat(param[i].getWorkCntPerHour()));

					//#CM693121
					//Set the tool tip.
					ToolTipHelper toolTip = new ToolTipHelper();
					//#CM693122
					// Worker Name
					toolTip.add(title_WorkerName, param[i].getWorkerName());

					lst_SWkrRsltIqr.setToolTip(i + 1, toolTip.getText());

				}
			}

		}
		else
		{
			//#CM693123
			// Set a value for Pager.
			//#CM693124
			// Max count
			pgr_U.setMax(0);
			//#CM693125
			// Count of displayed data per Page
			pgr_U.setPage(0);
			//#CM693126
			// Start Position
			pgr_U.setIndex(0);
			//#CM693127
			// Max count
			pgr_D.setMax(0);
			//#CM693128
			// Count of displayed data per Page
			pgr_D.setPage(0);
			//#CM693129
			// Start Position
			pgr_D.setIndex(0);

			//#CM693130
			// Check the count of the search results.
			String errorMsg = listbox.checkLength();
			//#CM693131
			// Hide the header.
			lst_SWkrRsltIqr.setVisible(false);
			//#CM693132
			// Display the error message.
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM693133
	// Event handler methods -----------------------------------------
	//#CM693134
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693135
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693136
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkDetails_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693137
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetWkCtx_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693138
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkDate_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693139
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStart_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693140
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStart_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693141
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStart_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693142
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693143
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693144
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693145
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693146
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693147
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetWkCd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693148
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_GroupCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693149
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetGcd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693150
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693151
	/** 
	 * Clicking on "Close" button executes its process. <BR>
	 *  <BR>
	 * Close the listbox and shift to the parent screen. <BR>
	 *  <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		btn_Close_D_Click(e);
	}

	//#CM693152
	/** 
	 * Execute the process defined for clicking on ">" button. <BR>
	 * <BR>
	 * Display the next page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	//#CM693153
	/** 
	 * Execute the process defined for clicking on "<" button. <BR>
	 * <BR>
	 * Display the previous one page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
		pgr_D_Prev(e);
	}

	//#CM693154
	/** 
	 * Clicking ">>" button. <BR>
	 * <BR>
	 * Display the end page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		pgr_D_Last(e);
	}

	//#CM693155
	/** Execute the process defined for clicking on "<<" button. <BR>
	 * <BR>
	 * Display the top page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		pgr_D_First(e);
	}

	//#CM693156
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SWkrRsltIqr_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM693157
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SWkrRsltIqr_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM693158
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SWkrRsltIqr_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM693159
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SWkrRsltIqr_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM693160
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SWkrRsltIqr_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693161
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SWkrRsltIqr_Change(ActionEvent e) throws Exception
	{
	}

	//#CM693162
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SWkrRsltIqr_Click(ActionEvent e) throws Exception
	{
	}

	//#CM693163
	/** 
	 * Execute the process defined for clicking on ">" button. <BR>
	 * <BR>
	 * Display the next page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		//#CM693164
		// Maintain the list box in the Session
		SessionWorkerQtyInquiryRet listbox =
			(SessionWorkerQtyInquiryRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM693165
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		//#CM693166
		// Maintain the list box in the Session
		SessionWorkerQtyInquiryRet listbox =
			(SessionWorkerQtyInquiryRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM693167
	/** 
	 * Clicking ">>" button. <BR>
	 * <BR>
	 * Display the end page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		//#CM693168
		// Maintain the list box in the Session
		SessionWorkerQtyInquiryRet listbox =
			(SessionWorkerQtyInquiryRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM693169
	/** 
	 * Execute the process defined for clicking on "<<" button. <BR>
	 * <BR>
	 * Display the top page. <BR>
	 * <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM693170
		// Maintain the list box in the Session
		SessionWorkerQtyInquiryRet listbox =
			(SessionWorkerQtyInquiryRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM693171
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM693172
	/** 
	 * Clicking on "Close" button executes its process. <BR>
	 *  <BR>
	 * Close the listbox and shift to the parent screen. <BR>
	 *  <BR>
	 * @param e ActionEvent A class to store event information.
	 * @throws Exception Report all exceptions.
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM693173
		// Maintain the list box in the Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM693174
		// If there is any value in the Session:
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM693175
				// Close the statement.
				finder.close();
			}
			//#CM693176
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM693177
		// Delete from the session.
		this.getSession().removeAttribute("LISTBOX");
		//#CM693178
		// Return to the parent screen.
		parentRedirect(null);
	}
	//#CM693179
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

}
//#CM693180
//end of class
