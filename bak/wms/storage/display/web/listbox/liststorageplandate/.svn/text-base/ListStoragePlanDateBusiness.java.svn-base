// $Id: ListStoragePlanDateBusiness.java,v 1.2 2006/12/07 08:57:38 suresh Exp $

//#CM569109
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.listbox.liststorageplandate;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStorageDateRet;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStorageWorkInfoDateRet;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM569110
/**
 * Designer : A.Nagasawa <BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * The Storage plan date retrieval list box class. <BR>
 * Retrieve it based on Consignor Code and Storage plan date input from the parents screen. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Initial screen (<CODE>page_Load</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 	Retrieve by making Consignor Code and Storage plan date a key input from the parents screen, and display it on the screen. <BR>
 * </DIR>
 * <BR>
 * 2.Button of selected line (<CODE>lst_StrgPlanDateSrch_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 	Pass Storage plan date of the Selected line to the parents screen, and close the list box. <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>A.Nagasawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:38 $
 * @author  $Author: suresh $
 */
public class ListStoragePlanDateBusiness extends ListStoragePlanDate implements WMSConstants
{

	//#CM569111
	// Class fields --------------------------------------------------
	//#CM569112
	/** 
	 * The key used to hand over Storage plan date. 
	 */
	public static final String STORAGEPLANDATE_KEY = "STORAGEPLANDATE_KEY";

	//#CM569113
	/** 
	 * Start The key used to hand over Storage plan date. 
	 */
	public static final String STARTSTORAGEPLANDATE_KEY = "STARTSTORAGEPLANDATE_KEY";

	//#CM569114
	/** 
	 * End The key used to hand over Storage plan date. 
	 */
	public static final String ENDSTORAGEPLANDATE_KEY = "ENDSTORAGEPLANDATE_KEY";
	
	//#CM569115
	/** 
	 * The key used to hand over Storage plan date Range flag. 
	 */
	public static final String RANGESTORAGEPLANDATE_KEY = "RANGESTORAGEPLANDATE_KEY";

	//#CM569116
	/** 
	 * Work The key used to hand over Status. 
	 */
	public static final String WORKSTATUSSTORAGEPLANDATE_KEY = "WORKSTATUSSTORAGEPLANDATE_KEY";

	//#CM569117
	/** 
	 * The key used to hand over Retrieval flag. 
	 */
	public static final String SEARCHSTORAGEPLANDATE_KEY = "SEARCHSTORAGEPLANDATE_KEY";

	//#CM569118
	/** 
	 * The key used to hand over Area type flag. 
	 */
	public static final String AREA_TYPE_KEY = "AREA_TYPE_KEY";	

	//#CM569119
	// Class variables -----------------------------------------------

	//#CM569120
	// Class method --------------------------------------------------

	//#CM569121
	// Constructors --------------------------------------------------

	//#CM569122
	// Public methods ------------------------------------------------

	//#CM569123
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *		Selection <BR>
	 *		Storage plan date <BR>
	 *	</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM569124
		// Set the Screen name
		//#CM569125
		// Storage plan date retrieval
		lbl_ListName.setText(DisplayText.getText("TLE-W0067"));

		//#CM569126
		// Parameter is acquired. 
		//#CM569127
		// Consignor Code
		String consignorcode = request.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		//#CM569128
		// Storage plan date
		String storageplandate = request.getParameter(STORAGEPLANDATE_KEY);
		//#CM569129
		// Start Storage plan date
		String startstorageplandate = request.getParameter(STARTSTORAGEPLANDATE_KEY);
		//#CM569130
		// End Storage plan date
		String endstorageplandate = request.getParameter(ENDSTORAGEPLANDATE_KEY);
		//#CM569131
		// Storage plan dateRange flag
		String rangeshippingplandate = request.getParameter(RANGESTORAGEPLANDATE_KEY);
		//#CM569132
		// Work Status
		String[] search = request.getParameterValues(WORKSTATUSSTORAGEPLANDATE_KEY);
		//#CM569133
		// Retrieval table
		String searchplandate = request.getParameter(SEARCHSTORAGEPLANDATE_KEY);
		//#CM569134
		// Area type flag
		String areatypeflag = request.getParameter(AREA_TYPE_KEY);
		viewState.setString(RANGESTORAGEPLANDATE_KEY, rangeshippingplandate);
		viewState.setString(SEARCHSTORAGEPLANDATE_KEY, searchplandate);

		//#CM569135
		// Close the connection of the object left in Session. 
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM569136
			//Connection close
			sRet.closeConnection();
			//#CM569137
			//Delete it from the session. 
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM569138
		// Acquisition of connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM569139
		// Set Parameter
		StorageSupportParameter param = new StorageSupportParameter();
		//#CM569140
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM569141
		// Storage plan date
		param.setStoragePlanDate(storageplandate);
		//#CM569142
		// Start Storage plan date
		param.setFromStoragePlanDate(startstorageplandate);
		//#CM569143
		// End Storage plan date
		param.setToStoragePlanDate(endstorageplandate);
		//#CM569144
		// Work Status array
		param.setSearchStatus(search);
		//#CM569145
		// Area type flag
		param.setAreaTypeFlag(areatypeflag);

		//#CM569146
		// Determine whether retrieved Consignor is Work or plan.
		if (searchplandate.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM569147
			// SessionStorageDateRet  instance generation
			SessionStorageDateRet listbox = new SessionStorageDateRet(conn, param);
			//#CM569148
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setList(listbox, "first");
		}
		else if (searchplandate.equals(StorageSupportParameter.SEARCH_STORAGE_WORK))
		{
			//#CM569149
			// SessionStorageWorkInfoDateRet  instance generation
			SessionStorageWorkInfoDateRet listbox =
				new SessionStorageWorkInfoDateRet(conn, param);
			//#CM569150
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setWorkInfoList(listbox, "first");
		}
		else
		{
			Object[] tObj = new Object[1];
			tObj[0] = DisplayText.getText("LBL-W0024");
			String classname = this.getClass().getName();
			RmiMsgLogClient.write(6007039, LogMessage.F_ERROR, classname, tObj);
			throw (new InvalidStatusException("6007039" + wDelim + tObj[0]));
		}
		

	}


	//#CM569151
	// Package methods -----------------------------------------------

	//#CM569152
	// Protected methods ---------------------------------------------

	//#CM569153
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired and Set data in List cell  from Work information according to the displayed page. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionStorageWorkInfoDateRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 */
	private void setWorkInfoList(SessionStorageWorkInfoDateRet listbox, String actionName)
	{
		//#CM569154
		// The locale is acquired. 
		Locale locale = this.getHttpRequest().getLocale();

		//#CM569155
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM569156
		// The retrieval result is acquired. 
		StorageSupportParameter[] workparam = (StorageSupportParameter[])listbox.getEntities();
		int len = 0;
		if (workparam != null)
			len = workparam.length;
		if (len > 0)
		{
			//#CM569157
			// Value set to Pager
			//#CM569158
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM569159
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM569160
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM569161
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM569162
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM569163
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM569164
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM569165
			// Delete all lines
			lst_StrgPlanDateSrch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM569166
				// The final line is acquired
				int count = lst_StrgPlanDateSrch.getMaxRows();
				//#CM569167
				// Add row
				lst_StrgPlanDateSrch.addRow();

				//#CM569168
				// It moves to the final line. 
				lst_StrgPlanDateSrch.setCurrentRow(count);
				lst_StrgPlanDateSrch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_StrgPlanDateSrch.setValue(2, WmsFormatter.toDispDate(workparam[i].getStoragePlanDate(), locale));
			}
		}
		else
		{
			//#CM569169
			// Value set to Pager
			//#CM569170
			// The maximum qty
			pgr_U.setMax(0);
			//#CM569171
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM569172
			// Starting position
			pgr_U.setIndex(0);
			//#CM569173
			// The maximum qty
			pgr_D.setMax(0);
			//#CM569174
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM569175
			// Starting position
			pgr_D.setIndex(0);

			//#CM569176
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM569177
			// Conceal the header
			lst_StrgPlanDateSrch.setVisible(false);
			//#CM569178
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
		
		
	}


	//#CM569179
	// Private methods -----------------------------------------------
	//#CM569180
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired and Set data in List cell  from stock Plan information according to the displayed page. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionStorageDateRet SessionRet instance.
	 * @param actionName String Kind of button action. 
	 */
	private void setList(SessionStorageDateRet listbox, String actionName)
	{
		//#CM569181
		// The locale is acquired. 
		Locale locale = this.getHttpRequest().getLocale();

		//#CM569182
		// The page information is set. 
		listbox.setActionName(actionName);

		//#CM569183
		// The retrieval result is acquired. 
		StorageSupportParameter[] storageparam = (StorageSupportParameter[])listbox.getEntities();
		int len = 0;
		if (storageparam != null)
			len = storageparam.length;
		if (len > 0)
		{
			//#CM569184
			// Value set to Pager
			//#CM569185
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM569186
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM569187
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM569188
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM569189
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM569190
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM569191
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM569192
			// Delete all lines
			lst_StrgPlanDateSrch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM569193
				// The final line is acquired
				int count = lst_StrgPlanDateSrch.getMaxRows();
				//#CM569194
				// Add row
				lst_StrgPlanDateSrch.addRow();

				//#CM569195
				// It moves to the final line. 
				lst_StrgPlanDateSrch.setCurrentRow(count);
				lst_StrgPlanDateSrch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_StrgPlanDateSrch.setValue(2, WmsFormatter.toDispDate(storageparam[i].getStoragePlanDate(), locale));
			}
		}
		else
		{
			//#CM569196
			// Value set to Pager
			//#CM569197
			// The maximum qty
			pgr_U.setMax(0);
			//#CM569198
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM569199
			// Starting position
			pgr_U.setIndex(0);
			//#CM569200
			// The maximum qty
			pgr_D.setMax(0);
			//#CM569201
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM569202
			// Starting position
			pgr_D.setIndex(0);

			//#CM569203
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM569204
			// Conceal the header
			lst_StrgPlanDateSrch.setVisible(false);
			//#CM569205
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
		
	}
	//#CM569206
	// Event handler methods -----------------------------------------
	//#CM569207
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569208
	/**
	 * Do the Processing when the Close button is pressed. <BR>
	 *  <BR>
	 * The list box is closed, and changes to the parents screen.  <BR>
	 *  <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		btn_Close_D_Click(e);
	}

	//#CM569209
	/** 
	 * Do the Processing when the [>] button is pressed. <BR>
	 * <BR>
	 * Display the subsequent page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.  
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	//#CM569210
	/** 
	 * Do the Processing when the [<] button is pressed. <BR>
	 * <BR>
	 * Display the previous page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
		pgr_D_Prev(e);
	}

	//#CM569211
	/** 
	 * Do the Processing when the [>>] button is pressed. <BR>
	 * <BR>
	 * Display the last page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		pgr_D_Last(e);
	}

	//#CM569212
	/** 
	 * Do the Processing when the [<<] button is pressed. <BR>
	 * <BR>
	 * Display the first page. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		pgr_D_First(e);
	}

	//#CM569213
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569214
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StrgPlanDateSrch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM569215
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StrgPlanDateSrch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM569216
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StrgPlanDateSrch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM569217
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StrgPlanDateSrch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM569218
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StrgPlanDateSrch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569219
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_StrgPlanDateSrch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM569220
	/** 
	 * Process when Selection button of List cell is pressed.  <BR>
	 * <BR>
	 *	Pass Storage plan date to the parents screen, and close the list box.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void lst_StrgPlanDateSrch_Click(ActionEvent e) throws Exception
	{
		//#CM569221
		// Storage plan dateRange flag is acquired.
		String flug = viewState.getString(RANGESTORAGEPLANDATE_KEY);

		//#CM569222
		// A present line is set. 
		lst_StrgPlanDateSrch.setCurrentRow(lst_StrgPlanDateSrch.getActiveRow());
		lst_StrgPlanDateSrch.getValue(1);

		//#CM569223
		// Set Parameter to be returned to parents screen
		ForwardParameters param = new ForwardParameters();
		if (flug == null)
		{
			//#CM569224
			// Storage plan date
			param.setParameter(STORAGEPLANDATE_KEY, lst_StrgPlanDateSrch.getValue(2));
		}
		else if (flug.equals(StorageSupportParameter.RANGE_START))
		{
			//#CM569225
			// Start Storage plan date
			param.setParameter(STARTSTORAGEPLANDATE_KEY, lst_StrgPlanDateSrch.getValue(2));
		}
		else if (flug.equals(StorageSupportParameter.RANGE_END))
		{
			//#CM569226
			// End Storage plan date
			param.setParameter(ENDSTORAGEPLANDATE_KEY, lst_StrgPlanDateSrch.getValue(2));
		}
		//#CM569227
		// Changes to the parents screen. 
		parentRedirect(param);
	}

	//#CM569228
	/** 
	 * Do the Processing when the [>] button is pressed. <BR>
	 * <BR>
	 * Display the subsequent page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		//#CM569229
		// Retrieval flag is acquired. 
		String flag = viewState.getString(SEARCHSTORAGEPLANDATE_KEY);
		
		if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM569230
			// Listbox is maintained in Session
			SessionStorageDateRet listbox = (SessionStorageDateRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "next");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_WORK))
		{
			//#CM569231
			// Listbox is maintained in Session
			SessionStorageWorkInfoDateRet listbox =
				(SessionStorageWorkInfoDateRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "next");
		}
	}

	//#CM569232
	/** 
	 * Do the Processing when the [<] button is pressed. <BR>
	 * <BR>
	 * Display the previous page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		//#CM569233
		// Retrieval flag is acquired. 
		String flag = viewState.getString(SEARCHSTORAGEPLANDATE_KEY);
		
		if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM569234
			// Listbox is maintained in Session
			SessionStorageDateRet listbox = (SessionStorageDateRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "previous");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_WORK))
		{
			//#CM569235
			// Listbox is maintained in Session
			SessionStorageWorkInfoDateRet listbox =
				(SessionStorageWorkInfoDateRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "previous");
		}
	}

	//#CM569236
	/** 
	 * Do the Processing when the [>>] button is pressed. <BR>
	 * <BR>
	 * Display the last page.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		//#CM569237
		// Retrieval flag is acquired. 
		String flag = viewState.getString(SEARCHSTORAGEPLANDATE_KEY);
		
		if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM569238
			// Listbox is maintained in Session
			SessionStorageDateRet listbox = (SessionStorageDateRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "last");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_WORK))
		{
			//#CM569239
			// Listbox is maintained in Session
			SessionStorageWorkInfoDateRet listbox =
				(SessionStorageWorkInfoDateRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "last");
		}
		
	}

	//#CM569240
	/** 
	 * Do the Processing when the [<<] button is pressed. <BR>
	 * <BR>
	 * Display the first page. <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		//#CM569241
		// Retrieval flag is acquired. 
		String flag = viewState.getString(SEARCHSTORAGEPLANDATE_KEY);
		
		if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM569242
			// Listbox is maintained in Session
			SessionStorageDateRet listbox = (SessionStorageDateRet) this.getSession().getAttribute("LISTBOX");
			setList(listbox, "first");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_WORK))
		{
			//#CM569243
			// Listbox is maintained in Session
			SessionStorageWorkInfoDateRet listbox =
				(SessionStorageWorkInfoDateRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "first");
		}
		
	}

	//#CM569244
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM569245
	/** 
	 * Do the Processing when the Close button is pressed. <BR>
	 *  <BR>
	 * The list box is closed, and changes to the parents screen.  <BR>
	 *  <BR>
	 * @param e ActionEvent The class which stores information on the event. 
	 * @throws Exception It reports on all exceptions. 
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM569246
		// Listbox is maintained in Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM569247
		// When there is a value in Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM569248
				// Close the statement.
				finder.close();
			}
			//#CM569249
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM569250
		// Delete it from the session. 
		this.getSession().removeAttribute("LISTBOX");
		//#CM569251
		// Return to the parents screen
		parentRedirect(null);
	}


}
//#CM569252
//end of class
