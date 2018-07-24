// $Id: ListAsWorkBusiness.java,v 1.2 2006/10/26 05:38:15 suresh Exp $

//#CM40033
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.listasrswork;

import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsWorkRet;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;

//#CM40034
/**
 * Carry data list list box screen (Button available) class.<BR>
 * Retrieve from the parents screen based on terminal No, workplace, and stationNo input.<BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.initial display (<CODE>page_Load(ActionEvent e) </CODE> method)<BR>
 * <BR>
 * <DIR>
 * 	Make terminal No, workplace, and stationNo input from the parents screen a key, retrieve, and display it on the screen. <BR>
 * <BR>
 * </DIR>
 * 2.Button of selected line(<CODE>lst_AsWork_Click</CODE> Method )<BR>
 * <BR>
 * <DIR>
 * 	Pass Carry data information on the selection line to the parents screen, and close the list box.<BR>
 * <BR>
 * </DIR>
 * <BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:38:15 $
 * @author  $Author: suresh $
 */
public class ListAsWorkBusiness extends ListAsWork implements WMSConstants
{
	//#CM40035
	// Class fields --------------------------------------------------

	//#CM40036
	/** 
	 * key to transfer terminal no.(IN)
	 */
	public static final String TERMINALNO_KEY = "TERMINALNO_KEY";

	//#CM40037
	/** 
	 * key to transfer workplace(IN)
	 */
	public static final String WORKPLACE_KEY = "WORKPLACE_KEY";

	//#CM40038
	/** 
	 * key to transfer station no.(IN)
	 */
	public static final String STATIONNO_KEY = "STATIONNO_KEY";

	//#CM40039
	/**
	 * The key used to hand over transportation Key. 
	 */
	public static final String CARRYKEY_KEY = "CARRYKEY_KEY";

	//#CM40040
	/**
	 * The key used to hand over station. 
	 */
	public static final String STATION_KEY = "STATION_KEY";

	//#CM40041
	/**
	 * The key used to hand over the detailed delivery instruction. 
	 */
	public static final String RETRIEVALDETAIL_KEY = "RETRIEVALDETAIL_KEY";

	//#CM40042
	/**
	 * key to transfer location no
	 */
	public static final String LOCATIONNO_KEY = "LOCATIONNO_KEY";

	//#CM40043
	/** 
	 * The key used to hand over the transportation origin. 
	 */
	public static final String SOURCESTATION_KEY = "SOURCESTATION_KEY";

	//#CM40044
	/** 
	 * The key used for the delivery at the transportation destination. 
	 */
	public static final String DESTSTATION_KEY = "DESTSTATION_KEY";

	//#CM40045
	/** 
	 * The key used to hand over Work Status. 
	 */
	public static final String CMDSTATUS_KEY = "CMDSTATUS_KEY";

	//#CM40046
	/** 
	 * The key used to hand over the work flag.
	 */
	public static final String CARRYKIND_KEY = "CARRYKIND_KEY";

	//#CM40047
	/** 
	 * The key used to hand over Work No. 
	 */
	public static final String WORKNUMBER_KEY = "WORKNUMBER_KEY";

	//#CM40048
	/** 
	 * The key used to hand over the work type. 
	 */
	public static final String WORKTYPE_KEY = "WORKTYPE_KEY";

	//#CM40049
	// Class variables -----------------------------------------------

	//#CM40050
	// Class method --------------------------------------------------

	//#CM40051
	// Constructors --------------------------------------------------

	//#CM40052
	// Public methods ------------------------------------------------

	//#CM40053
	/**
	 * screen initialization
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM40054
		// set screen name
		//#CM40055
		//search stock list
		lbl_ListName.setText(DisplayText.getText("TLE-W0931"));

		//#CM40056
		// fetch parameter

		//#CM40057
		// fetch connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);
		
		//#CM40058
		// set header item other than listcell

		//#CM40059
		// close connection in session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM40060
			// close connection
			sRet.closeConnection();
			//#CM40061
			// delete from session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM40062
		// fetch parameter
		//#CM40063
		// terminal no
		String termno = request.getParameter(TERMINALNO_KEY);
		//#CM40064
		// workplace
		String wp = request.getParameter(WORKPLACE_KEY);
		//#CM40065
		// station no.
		String stno = request.getParameter(STATIONNO_KEY);

		//#CM40066
		// set parameter
		AsScheduleParameter param = new AsScheduleParameter();
		param.setTerminalNumber(termno);
		param.setSagyoba(wp);
		param.setStationNo(stno);

		//#CM40067
		// generate SessionRet instance
		SessionAsWorkRet listbox = new SessionAsWorkRet(conn, param);
		//#CM40068
		//save the listbox in session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM40069
	/**
	 * call this before calling the respective control events<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
	}

	//#CM40070
	// Package methods -----------------------------------------------

	//#CM40071
	// Protected methods ---------------------------------------------

	//#CM40072
	// Private methods -----------------------------------------------

	//#CM40073
	/**
	 * method to change page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setList(SessionAsWorkRet listbox, String actionName) throws Exception
	{
		//#CM40074
		// set page info
		listbox.setActionName(actionName);

		//#CM40075
		// fetch search result
		AsScheduleParameter[] rparam = (AsScheduleParameter[])listbox.getEntities();

		int len = 0;
		if (rparam != null)
			len = rparam.length;
		if (len > 0)
		{
			//#CM40076
			// set value in pager
			//#CM40077
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM40078
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM40079
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM40080
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM40081
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM40082
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM40083
			// hide message
			lbl_InMsg.setVisible(false);

			//#CM40084
			// delete all rows
			lst_AsWork.clearRow();

			//#CM40085
			// Used by the display at the transportation origin and transportation destinations. 
			//#CM40086
			// LBL-A0183= -->
			String arrow = DisplayText.getText("LBL-A0183");
			//#CM40087
			// ----
			String unknown = DisplayText.getText("CARRYINFO_RETRIEVALDETAIL_9");
			
			//#CM40088
			// used in tool tip
			//#CM40089
			// station
			String title_Station = DisplayText.getText("LBL-W0505");
			//#CM40090
			// conveyance origin
			String title_From   = DisplayText.getText("LBL-W0585");
			//#CM40091
			// conveyance destination
			String title_To = DisplayText.getText("LBL-W0586");
			//#CM40092
			// job no.
			String title_WorkingNo = DisplayText.getText("LBL-W0539");
			//#CM40093
			// LBL-A0035=work type
			String title_WorkType = DisplayText.getText("LBL-A0035");
			//#CM40094
			// LBL-A0034=The detailed delivery instruction.
			String title_RetDetail = DisplayText.getText("LBL-A0034");

			for (int i = 0; i < len; i++)
			{
				//#CM40095
				// For display of detailed delivery instructions.
				String retrievalDetailName = "";
				if (rparam[i].getRetrievalDetail().equals(AsScheduleParameter.RETRIEVALDETAIL_PICKING) ||
					rparam[i].getRetrievalDetail().equals(AsScheduleParameter.RETRIEVALDETAIL_UNIT))
				{
					retrievalDetailName = rparam[i].getDispRetrievalDetail();
				}
				else
				{
					retrievalDetailName = unknown;
				}

				//#CM40096
				// Make Hidden List.
				ArrayList hiddenList = new ArrayList(4);
				hiddenList.add(rparam[i].getFromStationName());
				hiddenList.add(rparam[i].getToStationName());
				hiddenList.add(rparam[i].getDispWorkType());
				hiddenList.add(retrievalDetailName);

				//#CM40097
				// fetch last row
				int count = lst_AsWork.getMaxRows();
				//#CM40098
				// add row
				lst_AsWork.addRow();

				lst_AsWork.setCurrentRow(count);
				lst_AsWork.setValue(0, CollectionUtils.getConnectedString(hiddenList));
				lst_AsWork.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_AsWork.setValue(2, rparam[i].getDispPriority());
				lst_AsWork.setValue(3, rparam[i].getCarryKey());
				lst_AsWork.setValue(4, rparam[i].getDispStationName());
				lst_AsWork.setValue(5, DisplayText.formatLocation(rparam[i].getLocationNo()));
				lst_AsWork.setValue(6, rparam[i].getFromStationNo() + arrow + rparam[i].getToStationNo());
				lst_AsWork.setValue(7, rparam[i].getDispCarryKind());
				lst_AsWork.setValue(8, rparam[i].getDispCarringStatusName());
				lst_AsWork.setValue(9, rparam[i].getWorkingNo());

				//#CM40099
				// add data to tool tip
				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(title_Station, rparam[i].getDispStationName());
				toolTip.add(title_From, rparam[i].getFromStationName());
				toolTip.add(title_To, rparam[i].getToStationName());
				toolTip.add(title_WorkingNo, rparam[i].getWorkingNo());
				toolTip.add(title_WorkType, rparam[i].getDispWorkType());
				toolTip.add(title_RetDetail, retrievalDetailName);
				//#CM40100
				// set tool tip	
				lst_AsWork.setToolTip(count, toolTip.getText());
			}
		}
		else
		{
			//#CM40101
			// set value to Pager
			//#CM40102
			// max. number
			pgr_U.setMax(0);
			//#CM40103
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM40104
			// start position
			pgr_U.setIndex(0);
			//#CM40105
			// max. number
			pgr_D.setMax(0);
			//#CM40106
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM40107
			// start position
			pgr_D.setIndex(0);

			//#CM40108
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM40109
			// hide the header
			lst_AsWork.setVisible(false);
			//#CM40110
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM40111
	// Event handler methods -----------------------------------------
	//#CM40112
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40113
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40114
	/** 
	 * call the [Close] button click event process <BR>
	 *  <BR>
	 * close the listbox and return to the caller screen<BR>
	 *  <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		btn_Close_D_Click(e);
	}

	//#CM40115
	/** 
	 * call the [>] button click event process <BR>
	 * <BR>
	 * display the next page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	//#CM40116
	/** 
	 * call the [<] button click event process <BR>
	 * <BR>
	 * display the previous page<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
		pgr_D_Prev(e);
	}

	//#CM40117
	/** 
	 * call the [>>] button click event process <BR>
	 * <BR>
	 * display the last page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		pgr_D_Last(e);
	}

	//#CM40118
	/** 
	 * call the [<<] button click event process<BR>
	 * <BR>
	 *display the first page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		pgr_D_First(e);
	}

	//#CM40119
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40120
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsWork_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM40121
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsWork_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM40122
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsWork_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM40123
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsWork_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM40124
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsWork_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40125
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_AsWork_Change(ActionEvent e) throws Exception
	{
	}

	//#CM40126
	/** 
	 * call the list cell select button click event process <BR>
	 * <BR>
	 *	Pass Carry data information to the parents screen, and close the list box.  <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void lst_AsWork_Click(ActionEvent e) throws Exception
	{
		//#CM40127
		// set the current row
		lst_AsWork.setCurrentRow(lst_AsWork.getActiveRow());
		lst_AsWork.getValue(1);

		//#CM40128
		// set parameter that is used to return to the caller screen
		ForwardParameters param = new ForwardParameters();
		//#CM40129
		// conveyance key
		param.setParameter(CARRYKEY_KEY, lst_AsWork.getValue(3));
		//#CM40130
		// station(For display)
		param.setParameter(STATION_KEY, lst_AsWork.getValue(4));
		//#CM40131
		// location no.(The display has been formatted. )
		param.setParameter(LOCATIONNO_KEY, lst_AsWork.getValue(5));
		//#CM40132
		// sending station no..
		param.setParameter(SOURCESTATION_KEY, CollectionUtils.getString(0, lst_AsWork.getValue(0)));
		//#CM40133
		// receiving station no.
		param.setParameter(DESTSTATION_KEY, CollectionUtils.getString(1, lst_AsWork.getValue(0)));
		//#CM40134
		// conveyance type(For display)
		param.setParameter(CARRYKIND_KEY, lst_AsWork.getValue(7));
		//#CM40135
		// conveyance status(For display)
		param.setParameter(CMDSTATUS_KEY, lst_AsWork.getValue(8));
		//#CM40136
		// job no.
		param.setParameter(WORKNUMBER_KEY, lst_AsWork.getValue(9));
		//#CM40137
		// work type(For display)
		param.setParameter(WORKTYPE_KEY, CollectionUtils.getString(2, lst_AsWork.getValue(0)));
		//#CM40138
		// picking instruction detail(For display)
		param.setParameter(RETRIEVALDETAIL_KEY, CollectionUtils.getString(3, lst_AsWork.getValue(0)));
		//#CM40139
		// move to the caller screen
		parentRedirect(param);
	}

	//#CM40140
	/** 
	 * call the [>] button click event process <BR>
	 * <BR>
	 * display the next page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		//#CM40141
		//save the listbox in session
		SessionAsWorkRet listbox = (SessionAsWorkRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM40142
	/** 
	 * call the [<] button click event process <BR>
	 * <BR>
	 * display the previous page<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		//#CM40143
		//save the listbox in session
		SessionAsWorkRet listbox = (SessionAsWorkRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM40144
	/** 
	 * call the [>>] button click event process <BR>
	 * <BR>
	 * display the last page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		//#CM40145
		//save the listbox in session
		SessionAsWorkRet listbox = (SessionAsWorkRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM40146
	/** 
	 * call the [<<] button click event process<BR>
	 * <BR>
	 *display the first page <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM40147
		//save the listbox in session
		SessionAsWorkRet listbox = (SessionAsWorkRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM40148
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40149
	/** 
	 * call the [Close] button click event process <BR>
	 *  <BR>
	 * close the listbox and return to the caller screen<BR>
	 *  <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM40150
		//save the listbox in session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM40151
		// if value exists in session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM40152
				// close the statement object
				finder.close();
			}
			//#CM40153
			// close the connection object
			sessionret.closeConnection();
		}
		//#CM40154
		// delete from session
		this.getSession().removeAttribute("LISTBOX");
		//#CM40155
		// return to origin screen
		parentRedirect(null);
	}


}
//#CM40156
//end of class
