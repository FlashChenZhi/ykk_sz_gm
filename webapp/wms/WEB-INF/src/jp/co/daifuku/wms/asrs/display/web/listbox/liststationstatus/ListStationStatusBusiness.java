// $Id: ListStationStatusBusiness.java,v 1.2 2006/10/26 05:46:55 suresh Exp $

//#CM40258
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.liststationstatus;
import java.sql.Connection;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionStationStatusRet;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;

//#CM40259
/**
 * <FONT COLOR="BLUE">
 * Designer : M.Koyama <BR>
 * Maker : M.Koyama <BR>
 * <BR>
 * station list search class<BR>
 * <BR>
 * 1.initial display (<CODE>page_Load(ActionEvent e) </CODE> method)<BR>
 * <BR>
 * <DIR>
 *  search all station<BR>
 * <BR>
 * </DIR>
 * 2.Button of selected line (<CODE>lst_StationStatus_Click</CODE> method)<BR>
 * <BR>
 * <DIR>
 * 	Pass the following item of the selection line to the parents screen, and close the list box. <BR>
 *  <DIR>
 *      station no. <BR>
 *  	station name <BR>
 *      machine status name <BR>
 *      work status <BR>
 *      work status name <BR>
 *      work qty <BR>
 *      operation mode name <BR>
 *      work mode name <BR>
 *      work mode change request name<BR>
 *      operation mode <BR>
 *      work mode name <BR>
 *      work mode change request <BR>
 *  </DIR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/31</TD><TD>M.Koyama</TD><TD>new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:46:55 $
 * @author  $Author: suresh $
 */
public class ListStationStatusBusiness extends ListStationStatus implements WMSConstants
{
	//#CM40260
	// Class fields --------------------------------------------------
	//#CM40261
	/** 
	 * key to transfer terminal no.
	 */
	public static final String TERMINALNO_KEY = "TERMINALNO_KEY";

	//#CM40262
	/** 
	 * key to transfer station no.
	 */
	public static final String STATIONNO_KEY = "STATIONNO_KEY";

	//#CM40263
	/** 
	 * key to transfer station name
	 */
	public static final String STATIONNAME_KEY = "STATIONNAME_KEY";

	//#CM40264
	/** 
	 * key to transfer operation mode
	 */
	public static final String MODETYPE_KEY = "MODETYPE_KEY";

	//#CM40265
	/** 
	 * key to transfer operation mode name
	 */
	public static final String MODETYPENAME_KEY = "MODETYPENAME_KEY";

	//#CM40266
	/** 
	 * key to transfer work mode
	 */
	public static final String CURRENTMODE_KEY = "CURRENTMODE_KEY";

	//#CM40267
	/** 
	 * key to transfer work mode name
	 */
	public static final String CURRENTMODENAME_KEY = "CURRENTMODENAME_KEY";

	//#CM40268
	/** 
	 * key to transfer machine status
	 */
	public static final String STATUSNAME_KEY = "STATUSNAME_KEY";

	//#CM40269
	/** 
	 * key to transfer work status
	 */
	public static final String SUSPEND_KEY = "SUSPEND_KEY";

	//#CM40270
	/** 
	 * key to transfer work status name
	 */
	public static final String SUSPENDNAME_KEY = "SUSPENDNAME_KEY";

	//#CM40271
	/** 
	 * key to transfer work qty
	 */
	public static final String WORKINGCOUNT_KEY = "WORKINGCOUNT_KEY";

	//#CM40272
	/** 
	 * key to transfer work mode change request
	 */
	public static final String MODEREQUEST_KEY = "MODEREQUEST_KEY";

	//#CM40273
	/** 
	 * key to transfer work mode change request name
	 */
	public static final String MODEREQUESTNAME_KEY = "MODEREQUESTNAME_KEY";

	//#CM40274
	// Class variables -----------------------------------------------

	//#CM40275
	// Class method --------------------------------------------------

	//#CM40276
	// Constructors --------------------------------------------------

	//#CM40277
	// Public methods ------------------------------------------------

	//#CM40278
	/**
	 * screen initialization
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM40279
		// set screen name
		//#CM40280
		//search stock list
		lbl_ListName.setText(DisplayText.getText("TLE-W0918"));

		//#CM40281
		// fetch parameter
		//#CM40282
		// terminal no.
		String terminalno = request.getParameter(TERMINALNO_KEY);

		//#CM40283
		// fetch connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM40284
		// set header items, other than listcell

		//#CM40285
		// close connection in session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM40286
			// close connection
			sRet.closeConnection();
			//#CM40287
			// delete from session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM40288
		// set parameter
		AsScheduleParameter param = new AsScheduleParameter();
		//#CM40289
		// terminal no.
		param.setTerminalNumber(terminalno);

		//#CM40290
		// generate SessionRet instance
		SessionStationStatusRet listbox = new SessionStationStatusRet(conn, param);
		//#CM40291
		//save the listbox in session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM40292
	/**
	 * call this before calling the respective control events<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
	}

	//#CM40293
	// Package methods -----------------------------------------------

	//#CM40294
	// Protected methods ---------------------------------------------

	//#CM40295
	// Private methods -----------------------------------------------
	//#CM40296
	/**
	 * method to change page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setList(SessionStationStatusRet listbox, String actionName)
	{
		//#CM40297
		// set page info
		listbox.setActionName(actionName);

		//#CM40298
		// fetch search result
		AsScheduleParameter[] rparam = (AsScheduleParameter[])listbox.getEntities();

		int len = 0;
		if (rparam != null)
			len = rparam.length;
		if (len > 0)
		{
			//#CM40299
			// set value in pager
			//#CM40300
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM40301
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM40302
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM40303
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM40304
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM40305
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM40306
			// hide message
			lbl_InMsg.setVisible(false);

			//#CM40307
			// delete all rows
			lst_StationStatus.clearRow();

			//#CM40308
			// used in tool tip
			String title_Station = DisplayText.getText("LBL-W0523");
			String title_StationName = DisplayText.getText("LBL-W0524");
			String title_ModeTypeName = DisplayText.getText("LBL-W0526");
			String title_CurrentModeName = DisplayText.getText("LBL-W0527");
			String title_StatusName = DisplayText.getText("LBL-W0525");
			String title_SuspendName = DisplayText.getText("LBL-W0281");
			String title_WorkingCount = DisplayText.getText("LBL-W0271");
			String title_ModeRequestName = DisplayText.getText("LBL-W0528");

			for (int i = 0; i < len; i++)
			{
				//#CM40309
				// fetch last row
				int count = lst_StationStatus.getMaxRows();
				//#CM40310
				// add row
				lst_StationStatus.addRow();

				//#CM40311
				// move to last record
				lst_StationStatus.setCurrentRow(count);
				lst_StationStatus.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_StationStatus.setValue(2, rparam[i].getStationNo());
				lst_StationStatus.setValue(3, rparam[i].getDispModeTypeName());
				lst_StationStatus.setValue(4, rparam[i].getDispCurrentModeName());
				lst_StationStatus.setValue(5, rparam[i].getDispControllerStatusName());
				lst_StationStatus.setValue(6, rparam[i].getDispWorkingStatusName());
				lst_StationStatus.setValue(7, Formatter.getNumFormat(rparam[i].getWorkingCount()));
				lst_StationStatus.setValue(8, rparam[i].getDispWorkingModeRequestName());

				//#CM40312
				// set hidden item
				//#CM40313
				// store case piece flag + stock id
				List Hiden = new Vector();
				Hiden.add(rparam[i].getDispStationName());
				Hiden.add(rparam[i].getModeType());					
				Hiden.add(rparam[i].getCurrentMode());					
				Hiden.add(rparam[i].getWorkingStatus());					
				Hiden.add(Integer.toString(rparam[i].getWorkingCount()));					
				Hiden.add(rparam[i].getWorkingModeRequest());					
				lst_StationStatus.setValue(0, CollectionUtils.getConnectedString(Hiden));

				
				//#CM40314
				// add data to tool tip
				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(title_Station, rparam[i].getStationNo());
				toolTip.add(title_StationName, rparam[i].getDispStationName());
				toolTip.add(title_ModeTypeName, rparam[i].getDispModeTypeName());
				toolTip.add(title_CurrentModeName, rparam[i].getDispCurrentModeName());
				toolTip.add(title_StatusName, rparam[i].getDispControllerStatusName());
				toolTip.add(title_SuspendName, rparam[i].getDispWorkingStatusName());
				toolTip.add(title_WorkingCount, Formatter.getNumFormat(rparam[i].getWorkingCount()));
				toolTip.add(title_ModeRequestName, rparam[i].getDispWorkingModeRequestName());

				//#CM40315
				// set tool tip	
				lst_StationStatus.setToolTip(count, toolTip.getText());
			}
		}
		else
		{
			//#CM40316
			// set value to Pager
			//#CM40317
			// max. number
			pgr_U.setMax(0);
			//#CM40318
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM40319
			// start position
			pgr_U.setIndex(0);
			//#CM40320
			// max. number
			pgr_D.setMax(0);
			//#CM40321
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM40322
			// start position
			pgr_D.setIndex(0);

			//#CM40323
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM40324
			// hide the header
			lst_StationStatus.setVisible(false);
			//#CM40325
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM40326
	// Event handler methods -----------------------------------------
	//#CM40327
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40328
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40329
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

	//#CM40330
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

	//#CM40331
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

	//#CM40332
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

	//#CM40333
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

	//#CM40334
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40335
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StationStatus_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM40336
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StationStatus_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM40337
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StationStatus_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM40338
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StationStatus_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM40339
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StationStatus_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40340
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StationStatus_Change(ActionEvent e) throws Exception
	{
	}

	//#CM40341
	/** 
	 * call the list cell select button click event process <BR>
	 * <BR>
	 *	send station status info to caller screen and close the list box <BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void lst_StationStatus_Click(ActionEvent e) throws Exception
	{
		//#CM40342
		// set the current row
		lst_StationStatus.setCurrentRow(lst_StationStatus.getActiveRow());
		lst_StationStatus.getValue(1);

		//#CM40343
		// set parameter that is used to return to the caller screen
		ForwardParameters param = new ForwardParameters();
		//#CM40344
		// station no.
		param.setParameter(STATIONNO_KEY,  lst_StationStatus.getValue(2));
		//#CM40345
		// station name
		param.setParameter(STATIONNAME_KEY, CollectionUtils.getString(0, lst_StationStatus.getValue(0)));
		//#CM40346
		// operation mode
		param.setParameter(MODETYPE_KEY,  CollectionUtils.getString(1, lst_StationStatus.getValue(0)));
		//#CM40347
		// operation mode name
		param.setParameter(MODETYPENAME_KEY, lst_StationStatus.getValue(3));
		//#CM40348
		// work mode name
		param.setParameter(CURRENTMODE_KEY,  CollectionUtils.getString(2, lst_StationStatus.getValue(0)));
		//#CM40349
		// work mode name
		param.setParameter(CURRENTMODENAME_KEY, lst_StationStatus.getValue(4));
		//#CM40350
		// machine status name
		param.setParameter(STATUSNAME_KEY, lst_StationStatus.getValue(5));
		//#CM40351
		// work status
		param.setParameter(SUSPEND_KEY,  CollectionUtils.getString(3, lst_StationStatus.getValue(0)));
		//#CM40352
		// work status name
		param.setParameter(SUSPENDNAME_KEY, lst_StationStatus.getValue(6));
		//#CM40353
		// work qty
		param.setParameter(WORKINGCOUNT_KEY, CollectionUtils.getString(4, lst_StationStatus.getValue(0)));
		//#CM40354
		// work mode change request
		param.setParameter(MODEREQUEST_KEY,  CollectionUtils.getString(5, lst_StationStatus.getValue(0)));
		//#CM40355
		// work mode change request name
		param.setParameter(MODEREQUESTNAME_KEY, lst_StationStatus.getValue(8));

		//#CM40356
		// move to the caller screen
		parentRedirect(param);
	}

	//#CM40357
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
		//#CM40358
		//save the listbox in session
		SessionStationStatusRet listbox = (SessionStationStatusRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM40359
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
		//#CM40360
		//save the listbox in session
		SessionStationStatusRet listbox = (SessionStationStatusRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM40361
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
		//#CM40362
		//save the listbox in session
		SessionStationStatusRet listbox = (SessionStationStatusRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM40363
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
		//#CM40364
		//save the listbox in session
		SessionStationStatusRet listbox = (SessionStationStatusRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM40365
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM40366
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

		//#CM40367
		//save the listbox in session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM40368
		// if value exists in session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM40369
				// close the statement object
				finder.close();
			}
			//#CM40370
			// close the connection object
			sessionret.closeConnection();
		}
		//#CM40371
		// delete from session
		this.getSession().removeAttribute("LISTBOX");
		//#CM40372
		// return to origin screen
		parentRedirect(null);
	}


}
//#CM40373
//end of class
