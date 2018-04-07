// $Id: ListAsWorkNoBtnBusiness.java,v 1.2 2006/10/26 05:46:05 suresh Exp $

//#CM40161
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.listasrsworknobtn;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsWorkRet;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;

//#CM40162
/**
 * <FONT COLOR="BLUE">
 * Designer : M.Koyama <BR>
 * Maker : M.Koyama <BR>
 * <BR>
 * The The remainder work list display class. <BR>
 * <BR>
 * 1.initial display (<CODE>page_Load(ActionEvent e) </CODE> method)<BR>
 * <BR>
 * <DIR>
 *  Display the remainder work list by carry info table(CARRYINFO). <BR>
 * <BR>
 * </DIR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/31</TD><TD>M.Koyama</TD><TD>new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:46:05 $
 * @author  $Author: suresh $
 */
public class ListAsWorkNoBtnBusiness extends ListAsWorkNoBtn implements WMSConstants
{
	//#CM40163
	// Class fields --------------------------------------------------

	//#CM40164
	// Class variables -----------------------------------------------

	//#CM40165
	// Class method --------------------------------------------------

	//#CM40166
	// Constructors --------------------------------------------------

	//#CM40167
	// Public methods ------------------------------------------------

	//#CM40168
	/**
	 * screen initialization
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM40169
		// set screen name
		//#CM40170
		//search stock list
		lbl_ListName.setText(DisplayText.getText("TLE-W0930"));

		//#CM40171
		// fetch parameter

		//#CM40172
		// fetch connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM40173
		// set header item other than listcell

		//#CM40174
		// close connection in session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM40175
			// close connection
			sRet.closeConnection();
			//#CM40176
			// delete from session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM40177
		// set parameter
		AsScheduleParameter param = new AsScheduleParameter();

		//#CM40178
		// generate SessionRet instance
		SessionAsWorkRet listbox = new SessionAsWorkRet(conn, param);
		//#CM40179
		//save the listbox in session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM40180
	/**
	 * call this before calling the respective control events<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
	}

	//#CM40181
	// Package methods -----------------------------------------------

	//#CM40182
	// Protected methods ---------------------------------------------
	//#CM40183
	/**
	 * method to change page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setList(SessionAsWorkRet listbox, String actionName) throws Exception
	{
		//#CM40184
		// set page info
		listbox.setActionName(actionName);

		//#CM40185
		// fetch search result
		AsScheduleParameter[] rparam = (AsScheduleParameter[])listbox.getEntities();

		int len = 0;
		if (rparam != null)
			len = rparam.length;
		if (len > 0)
		{
			//#CM40186
			// set value in pager
			//#CM40187
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM40188
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM40189
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM40190
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM40191
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM40192
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM40193
			// hide message
			lbl_InMsg.setVisible(false);

			//#CM40194
			// delete all rows
			lst_WorkNoBtn.clearRow();

			//#CM40195
			// used in tool tip
			//#CM40196
			// station
			String title_Station = DisplayText.getText("LBL-W0505");
			//#CM40197
			// conveyance origin
			String title_From = DisplayText.getText("LBL-W0585");
			//#CM40198
			// conveyance destination
			String title_To = DisplayText.getText("LBL-W0586");
			//#CM40199
			// job no.
			String title_WorkingNo = DisplayText.getText("LBL-W0539");
			//#CM40200
			// LBL-A0035=work type
			String title_WorkType = DisplayText.getText("LBL-A0035");
			//#CM40201
			// LBL-A0034=The detailed delivery instruction.
			String title_RetDetail = DisplayText.getText("LBL-A0034");
			
			//#CM40202
			// LBL-A0183= -->
			String arrow = DisplayText.getText("LBL-A0183");
			//#CM40203
			// ----
			String unknown = DisplayText.getText("CARRYINFO_RETRIEVALDETAIL_9");

			for (int i = 0; i < len; i++)
			{
				//#CM40204
				// fetch last row
				int count = lst_WorkNoBtn.getMaxRows();
				//#CM40205
				// add row
				lst_WorkNoBtn.addRow();

				//#CM40206
				// move to last record
				lst_WorkNoBtn.setCurrentRow(count);
				lst_WorkNoBtn.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_WorkNoBtn.setValue(2, rparam[i].getDispPriority());
				lst_WorkNoBtn.setValue(3, rparam[i].getCarryKey());
				lst_WorkNoBtn.setValue(4, rparam[i].getDispStationName());
				lst_WorkNoBtn.setValue(5, DisplayText.formatLocation(rparam[i].getLocationNo())); 
				lst_WorkNoBtn.setValue(6, rparam[i].getFromStationNo() + arrow + rparam[i].getToStationNo());
				lst_WorkNoBtn.setValue(7, rparam[i].getDispCarryKind());
				lst_WorkNoBtn.setValue(8, rparam[i].getDispCarringStatusName());
				lst_WorkNoBtn.setValue(9, rparam[i].getWorkingNo());
				
				//#CM40207
				// add data to tool tip
				//#CM40208
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

				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(title_Station, rparam[i].getDispStationName());
				toolTip.add(title_From, rparam[i].getFromStationName());
				toolTip.add(title_To, rparam[i].getToStationName());
				toolTip.add(title_WorkingNo, rparam[i].getWorkingNo());
				toolTip.add(title_WorkType, rparam[i].getDispWorkType());
				toolTip.add(title_RetDetail, retrievalDetailName);

				//#CM40209
				// set tool tip	
				lst_WorkNoBtn.setToolTip(count, toolTip.getText());
			}
			
		}
		else
		{
			//#CM40210
			// set value to Pager
			//#CM40211
			// max. number
			pgr_U.setMax(0);
			//#CM40212
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM40213
			// start position
			pgr_U.setIndex(0);
			//#CM40214
			// max. number
			pgr_D.setMax(0);
			//#CM40215
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM40216
			// start position
			pgr_D.setIndex(0);

			//#CM40217
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM40218
			// hide the header
			lst_WorkNoBtn.setVisible(false);
			//#CM40219
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM40220
	// Private methods -----------------------------------------------

	//#CM40221
	// Event handler methods -----------------------------------------
	//#CM40222
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40223
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40224
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

	//#CM40225
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

	//#CM40226
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

	//#CM40227
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

	//#CM40228
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

	//#CM40229
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40230
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkNoBtn_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM40231
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkNoBtn_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM40232
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkNoBtn_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM40233
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkNoBtn_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM40234
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkNoBtn_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40235
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkNoBtn_Change(ActionEvent e) throws Exception
	{
	}

	//#CM40236
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_WorkNoBtn_Click(ActionEvent e) throws Exception
	{
	}

	//#CM40237
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
		//#CM40238
		//save the listbox in session
		SessionAsWorkRet listbox = (SessionAsWorkRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM40239
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
		//#CM40240
		//save the listbox in session
		SessionAsWorkRet listbox = (SessionAsWorkRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM40241
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
		//#CM40242
		//save the listbox in session
		SessionAsWorkRet listbox = (SessionAsWorkRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM40243
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
		//#CM40244
		//save the listbox in session
		SessionAsWorkRet listbox = (SessionAsWorkRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM40245
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40246
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

		//#CM40247
		//save the listbox in session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM40248
		// if value exists in session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM40249
				// close the statement object
				finder.close();
			}
			//#CM40250
			// close the connection object
			sessionret.closeConnection();
		}
		//#CM40251
		// delete from session
		this.getSession().removeAttribute("LISTBOX");
		//#CM40252
		// return to origin screen
		parentRedirect(null);
	}

}
//#CM40253
//end of class
