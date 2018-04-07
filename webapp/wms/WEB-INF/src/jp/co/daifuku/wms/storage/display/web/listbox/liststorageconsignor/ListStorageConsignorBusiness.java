// $Id: ListStorageConsignorBusiness.java,v 1.2 2006/12/07 08:57:43 suresh Exp $

//#CM568082
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionInventoryConsignorRet;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionInventoryStockConsignorRet;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStorageMovementConsignoreRet;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStoragePlanConsignorRet;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStorageResultConsignorRet;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStorageStockConsignorRet;
import jp.co.daifuku.wms.storage.display.web.listbox.sessionret.SessionStorageWorkInfoConsignorRet;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM568083
/**
 * Designer : A.Nagasawa<BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * The Consignor retrieval list box class. <BR>
 * Retrieve it based on Consignor Code input from the parents screen. <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Initial screen (<CODE>page_Load</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 	Retrieve by making Consignor Code input from the parents screen as a key, and display it on the screen. <BR>
 * </DIR>
 * <BR>
 * 2.Button of selected line (<CODE>lst_ConsignorSearch_Click</CODE> Method) <BR>
 * <BR>
 * <DIR>
 * 	Pass Consignor Code and Consignor Name of the Selected line to the parents screen, and close the list box. <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/15</TD><TD>A.Nagasawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:43 $
 * @author  $Author: suresh $
 */
public class ListStorageConsignorBusiness extends ListStorageConsignor implements WMSConstants
{
	//#CM568084
	// Class fields --------------------------------------------------
	//#CM568085
	/**
	 * The key used to hand over Consignor Code.
	 */
	public static final String CONSIGNORCODE_KEY = "CONSIGNORCODE_KEY";

	//#CM568086
	/**
	 * The key used to hand over Consignor Name.
	 */
	public static final String CONSIGNORNAME_KEY = "CONSIGNORNAME_KEY";

	//#CM568087
	/**
	 * The key used to hand over Storage plan date.
	 */
	public static final String STORAGEPLANDATE_KEY = "STORAGEPLANDATE_KEY";

	//#CM568088
	/**
	 * The key used to hand over Retrieval flag.
	 */
	public static final String SEARCHCONSIGNOR_KEY = "SEARCHCONSIGNOR_KEY";

	//#CM568089
	/**
	 * Work The key used to hand over Status.
	 */
	public static final String WORKSTATUSCONSIGNOR_KEY = "WORKSTATUSCONSIGNOR_KEY";

	//#CM568090
	/**
	 * The key used to hand over Area type flag.
	 */
	public static final String AREA_TYPE_KEY = "AREA_TYPE_KEY";

	//#CM568091
	// Class variables -----------------------------------------------

	//#CM568092
	// Class method --------------------------------------------------

	//#CM568093
	// Constructors --------------------------------------------------

	//#CM568094
	// Public methods ------------------------------------------------

	//#CM568095
	/**
	 * Initialize the screen.  <BR>
	 * <DIR>
	 *	Item <BR>
	 *	<DIR>
	 *		Selection <BR>
	 *		Consignor Code <BR>
	 *		Consignor Name <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent The class which stores information on the event.
	 * @throws Exception It reports on all exceptions.
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM568096
		// Set the Screen name
		//#CM568097
		// Consignor retrieval
		lbl_ListName.setText(DisplayText.getText("TLE-W0012"));

		//#CM568098
		// Parameter is acquired.
		//#CM568099
		// Consignor Code
		String consignorcode = request.getParameter(CONSIGNORCODE_KEY);
		//#CM568100
		// Storage plan date
		String storageplandate = request.getParameter(STORAGEPLANDATE_KEY);
		//#CM568101
		// ConsignorRetrieval flag
		String searchconsignor = request.getParameter(SEARCHCONSIGNOR_KEY);
		//#CM568102
		// Work Status
		String[] search = request.getParameterValues(WORKSTATUSCONSIGNOR_KEY);
		//#CM568103
		// Area type flag
		String areatypeflag = request.getParameter(AREA_TYPE_KEY);

		viewState.setString(SEARCHCONSIGNOR_KEY, searchconsignor);

		//#CM568104
		// Close the connection of the object left in Session.
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM568105
			// Connection close
			sRet.closeConnection();
			//#CM568106
			// Delete it from the session.
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM568107
		// Acquisition of connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM568108
		// Set Parameter
		StorageSupportParameter param = new StorageSupportParameter();
		//#CM568109
		// Consignor Code
		param.setConsignorCode(consignorcode);
		//#CM568110
		// Storage plan date
		param.setStoragePlanDate(storageplandate);
		//#CM568111
		// Work Status
		param.setSearchStatus(search);
		//#CM568112
		// Area type flag
		param.setAreaTypeFlag(areatypeflag);

		//#CM568113
		// The table retrieved with Retrieval flag is decided.
		if (searchconsignor.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM568114
			// SessionStoragePlanConsignorRet  instance generation
			SessionStoragePlanConsignorRet listbox =
				new SessionStoragePlanConsignorRet(conn, param);
			//#CM568115
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setPlanList(listbox, "first");
		}
		else if (searchconsignor.equals(StorageSupportParameter.SEARCH_STORAGE_RESULT))
		{
			//#CM568116
			// SessionStorageResultConsignorRet  instance generation
			SessionStorageResultConsignorRet listbox =
				new SessionStorageResultConsignorRet(conn, param);
			//#CM568117
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setResultList(listbox, "first");
		}
		else if (searchconsignor.equals(StorageSupportParameter.SEARCH_STORAGE_STOCK))
		{
			//#CM568118
			// SessionStorageStockConsignorRet  instance generation
			SessionStorageStockConsignorRet listbox =
				new SessionStorageStockConsignorRet(conn, param);
			//#CM568119
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setStockList(listbox, "first");
		}
		else if (searchconsignor.equals(StorageSupportParameter.SEARCH_INVENTORY))
		{
			//#CM568120
			// SessionInventoryConsignorRet  instance generation
			SessionInventoryConsignorRet listbox = new SessionInventoryConsignorRet(conn, param);
			//#CM568121
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setInventoryList(listbox, "first");
		}
		else if (searchconsignor.equals(StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK))
		{
			//#CM568122
			// SessionInventoryStockConsignorRet  instance generation
			SessionInventoryStockConsignorRet listbox = new SessionInventoryStockConsignorRet(conn, param);
			//#CM568123
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setInventoryStockList(listbox, "first");
		}
		else if (searchconsignor.equals(StorageSupportParameter.SEARCH_STOCKMOVE))
		{
			//#CM568124
			// SessionStorageMovementConsignoreRet  instance generation
			SessionStorageMovementConsignoreRet listbox =
				new SessionStorageMovementConsignoreRet(conn, param);
			//#CM568125
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setMoveList(listbox, "first");
		}
		else if (searchconsignor.equals(StorageSupportParameter.SEARCH_STORAGE_WORK))
		{
			//#CM568126
			// SessionStorageWorkInfoConsignorRet  instance generation
			SessionStorageWorkInfoConsignorRet listbox =
				new SessionStorageWorkInfoConsignorRet(conn, param);
			//#CM568127
			// Listbox is maintained in Session
			this.getSession().setAttribute("LISTBOX", listbox);
			setWorkList(listbox, "first");
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

	//#CM568128
	// Package methods -----------------------------------------------

	//#CM568129
	// Protected methods ---------------------------------------------

	//#CM568130
	// Private methods -----------------------------------------------

	//#CM568131
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
	 * @param listbox SessionStorageWorkInfoConsignorRet SessionRet instance.
	 * @param actionName String Kind of button action.
	 * @throws Exception It reports on all exceptions.
	 */
	private void setWorkList(SessionStorageWorkInfoConsignorRet listbox, String actionName)
	{
		//#CM568132
		// The page information is set.
		listbox.setActionName(actionName);

		//#CM568133
		// The retrieval result is acquired.
		StorageSupportParameter[] workparam = (StorageSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (workparam != null)
			len = workparam.length;
		if (len > 0)
		{
			//#CM568134
			// Value set to Pager
			//#CM568135
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568136
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568137
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568138
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568139
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568140
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568141
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568142
			// Delete all lines
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568143
				// The final line is acquired
				int count = lst_ConsignorSearch.getMaxRows();
				//#CM568144
				// Add row
				lst_ConsignorSearch.addRow();

				//#CM568145
				// It moves to the final line.
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, workparam[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, workparam[i].getConsignorName());
			}
		}
		else
		{
			//#CM568146
			// Value set to Pager
			//#CM568147
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568148
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568149
			// Starting position
			pgr_U.setIndex(0);
			//#CM568150
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568151
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568152
			// Starting position
			pgr_D.setIndex(0);

			//#CM568153
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568154
			// Conceal the header
			lst_ConsignorSearch.setVisible(false);
			//#CM568155
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}

	}

	//#CM568156
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
	 * @param listbox SessionStoragePlanConsignorRet SessionRet instance.
	 * @param actionName String Kind of button action.
	 * @throws Exception It reports on all exceptions.
	 */
	private void setPlanList(SessionStoragePlanConsignorRet listbox, String actionName)
	{
		//#CM568157
		// The page information is set.
		listbox.setActionName(actionName);

		//#CM568158
		// The retrieval result is acquired.
		StorageSupportParameter[] storageparam = (StorageSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (storageparam != null)
			len = storageparam.length;
		if (len > 0)
		{
			//#CM568159
			// Value set to Pager
			//#CM568160
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568161
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568162
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568163
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568164
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568165
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568166
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568167
			// Delete all lines
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568168
				// The final line is acquired
				int count = lst_ConsignorSearch.getMaxRows();
				//#CM568169
				// Add row
				lst_ConsignorSearch.addRow();

				//#CM568170
				// It moves to the final line.
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, storageparam[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, storageparam[i].getConsignorName());
			}
		}
		else
		{
			//#CM568171
			// Value set to Pager
			//#CM568172
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568173
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568174
			// Starting position
			pgr_U.setIndex(0);
			//#CM568175
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568176
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568177
			// Starting position
			pgr_D.setIndex(0);

			//#CM568178
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568179
			// Conceal the header
			lst_ConsignorSearch.setVisible(false);
			//#CM568180
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM568181
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired and Set data in List cell from results View information according to the displayed page. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionStorageResultConsignorRet SessionRet instance.
	 * @param actionName String Kind of button action.
	 * @throws Exception It reports on all exceptions.
	 */
	private void setResultList(SessionStorageResultConsignorRet listbox, String actionName)
	{
		//#CM568182
		// The page information is set.
		listbox.setActionName(actionName);

		//#CM568183
		// The retrieval result is acquired.
		StorageSupportParameter[] ssparam = (StorageSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (ssparam != null)
			len = ssparam.length;
		if (len > 0)
		{
			//#CM568184
			// Value set to Pager
			//#CM568185
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568186
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568187
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568188
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568189
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568190
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568191
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568192
			// Delete all lines
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568193
				// The final line is acquired
				int count = lst_ConsignorSearch.getMaxRows();
				//#CM568194
				// Add row
				lst_ConsignorSearch.addRow();

				//#CM568195
				// It moves to the final line.
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, ssparam[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, ssparam[i].getConsignorName());
			}
		}
		else
		{
			//#CM568196
			// Value set to Pager
			//#CM568197
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568198
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568199
			// Starting position
			pgr_U.setIndex(0);
			//#CM568200
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568201
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568202
			// Starting position
			pgr_D.setIndex(0);

			//#CM568203
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568204
			// Conceal the header
			lst_ConsignorSearch.setVisible(false);
			//#CM568205
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM568206
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired and Set data in List cell from the inventory information according to the displayed page. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionStorageStockConsignorRet SessionRet instance.
	 * @param actionName String Kind of button action.
	 * @throws Exception It reports on all exceptions.
	 */
	private void setStockList(SessionStorageStockConsignorRet listbox, String actionName)
	{
		//#CM568207
		// The page information is set.
		listbox.setActionName(actionName);

		//#CM568208
		// The retrieval result is acquired.
		StorageSupportParameter[] storageparam = (StorageSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (storageparam != null)
			len = storageparam.length;
		if (len > 0)
		{
			//#CM568209
			// Value set to Pager
			//#CM568210
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568211
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568212
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568213
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568214
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568215
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568216
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568217
			// Delete all lines
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568218
				// The final line is acquired
				int count = lst_ConsignorSearch.getMaxRows();
				//#CM568219
				// Add row
				lst_ConsignorSearch.addRow();

				//#CM568220
				// It moves to the final line.
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, storageparam[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, storageparam[i].getConsignorName());
			}
		}
		else
		{
			//#CM568221
			// Value set to Pager
			//#CM568222
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568223
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568224
			// Starting position
			pgr_U.setIndex(0);
			//#CM568225
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568226
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568227
			// Starting position
			pgr_D.setIndex(0);

			//#CM568228
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568229
			// Conceal the header
			lst_ConsignorSearch.setVisible(false);
			//#CM568230
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM568231
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired and Set data in List cell  from Inventory information according to the displayed page. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionInventoryConsignorRet SessionRet instance.
	 * @param actionName String Kind of button action.
	 * @throws Exception It reports on all exceptions.
	 */
	private void setInventoryList(SessionInventoryConsignorRet listbox, String actionName)
	{
		//#CM568232
		// The page information is set.
		listbox.setActionName(actionName);

		//#CM568233
		// The retrieval result is acquired.
		InventoryCheck[] inventory = listbox.getEntities();
		int len = 0;
		if (inventory != null)
			len = inventory.length;
		if (len > 0)
		{
			//#CM568234
			// Value set to Pager
			//#CM568235
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568236
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568237
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568238
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568239
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568240
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568241
			// Cancel Message
			lbl_InMsg.setVisible(false);

			//#CM568242
			// Delete all lines
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568243
				// The final line is acquired
				int count = lst_ConsignorSearch.getMaxRows();
				//#CM568244
				// Add row
				lst_ConsignorSearch.addRow();

				//#CM568245
				// It moves to the final line.
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, inventory[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, inventory[i].getConsignorName());
			}
		}
		else
		{
			//#CM568246
			// Value set to Pager
			//#CM568247
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568248
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568249
			// Starting position
			pgr_U.setIndex(0);
			//#CM568250
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568251
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568252
			// Starting position
			pgr_D.setIndex(0);

			//#CM568253
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568254
			// Conceal the header
			lst_ConsignorSearch.setVisible(false);
			//#CM568255
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM568256
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired from the displayed page and Set data in List cell from Inventory Work + Inventory Work information<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionInventoryStockConsignorRet SessionRet instance.
	 * @param actionName String Kind of button action.
	 * @throws Exception It reports on all exceptions.
	 */
	private void setInventoryStockList(SessionInventoryStockConsignorRet listbox, String actionName)
	{
		//#CM568257
		// The page information is set.
		listbox.setActionName(actionName);

		//#CM568258
		// The retrieval result is acquired.
		StorageSupportParameter[] inventorystock = listbox.getEntities();
		int len = 0;
		if (inventorystock != null)
			len = inventorystock.length;
		if (len > 0)
		{
			//#CM568259
			// Value set to Pager
			//#CM568260
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568261
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568262
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568263
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568264
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568265
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568266
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568267
			// Delete all lines
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568268
				// The final line is acquired
				int count = lst_ConsignorSearch.getMaxRows();
				//#CM568269
				// Add row
				lst_ConsignorSearch.addRow();

				//#CM568270
				// It moves to the final line.
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, inventorystock[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, inventorystock[i].getConsignorName());
			}
		}
		else
		{
			//#CM568271
			// Value set to Pager
			//#CM568272
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568273
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568274
			// Starting position
			pgr_U.setIndex(0);
			//#CM568275
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568276
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568277
			// Starting position
			pgr_D.setIndex(0);

			//#CM568278
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568279
			// Conceal the header
			lst_ConsignorSearch.setVisible(false);
			//#CM568280
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM568281
	/**
	 * Method used when the displayed page is changed.  <BR>
	 * <BR>
	 * Outline : The retrieval result is acquired and Set data in List cell from movement Work information according to the displayed page. <BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set the page information, and acquire the retrieval result. <BR>
	 * 		2.Set data in List cell. .<BR>
	 * </DIR>
	 * <BR>
	 * @param listbox SessionStorageMovementConsignoreRet SessionRet instance.
	 * @param actionName String Kind of button action.
	 * @throws Exception It reports on all exceptions.
	 */
	private void setMoveList(SessionStorageMovementConsignoreRet listbox, String actionName)
	{
		//#CM568282
		// The page information is set.
		listbox.setActionName(actionName);

		//#CM568283
		// The retrieval result is acquired.
		StorageSupportParameter[] move = (StorageSupportParameter[]) listbox.getEntities();
		int len = 0;
		if (move != null)
			len = move.length;
		if (len > 0)
		{
			//#CM568284
			// Value set to Pager
			//#CM568285
			// The maximum qty
			pgr_U.setMax(listbox.getLength());
			//#CM568286
			// 1Page display qty
			pgr_U.setPage(listbox.getCondition());
			//#CM568287
			// Starting position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM568288
			// The maximum qty
			pgr_D.setMax(listbox.getLength());
			//#CM568289
			// 1Page display qty
			pgr_D.setPage(listbox.getCondition());
			//#CM568290
			// Starting position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM568291
			// Conceal Message
			lbl_InMsg.setVisible(false);

			//#CM568292
			// Delete all lines
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				//#CM568293
				// The final line is acquired
				int count = lst_ConsignorSearch.getMaxRows();
				//#CM568294
				// Add row
				lst_ConsignorSearch.addRow();

				//#CM568295
				// It moves to the final line.
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, move[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, move[i].getConsignorName());
			}
		}
		else
		{
			//#CM568296
			// Value set to Pager
			//#CM568297
			// The maximum qty
			pgr_U.setMax(0);
			//#CM568298
			// 1Page display qty
			pgr_U.setPage(0);
			//#CM568299
			// Starting position
			pgr_U.setIndex(0);
			//#CM568300
			// The maximum qty
			pgr_D.setMax(0);
			//#CM568301
			// 1Page display qty
			pgr_D.setPage(0);
			//#CM568302
			// Starting position
			pgr_D.setIndex(0);

			//#CM568303
			// Check the number of retrieval results
			String errorMsg = listbox.checkLength();
			//#CM568304
			// Conceal the header
			lst_ConsignorSearch.setVisible(false);
			//#CM568305
			// Display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM568306
	// Event handler methods -----------------------------------------
	//#CM568307
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568308
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568309
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

	//#CM568310
	/**
	 * Do the Processing when the [>] button is pressed. <BR>
	 * <BR>
	 * Display the subsequent page.  <BR>
	 * <BR>
	 *
	 * @param e ActionEvent The class which stores information on the event.
	 * @throws Exception It reports on all exceptions.
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	//#CM568311
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

	//#CM568312
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

	//#CM568313
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

	//#CM568314
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568315
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_ConsignorSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM568316
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_ConsignorSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM568317
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_ConsignorSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM568318
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_ConsignorSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM568319
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_ConsignorSearch_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568320
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_ConsignorSearch_Change(ActionEvent e) throws Exception
	{
	}

	//#CM568321
	/**
	 * Process when Selection button of List cell is pressed.  <BR>
	 * <BR>
	 *	Pass Consignor Code and Consignor Name to the parents screen, and close the list box.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.
	 * @throws Exception It reports on all exceptions.
	 */
	public void lst_ConsignorSearch_Click(ActionEvent e) throws Exception
	{

		//#CM568322
		// A present line is set.
		lst_ConsignorSearch.setCurrentRow(lst_ConsignorSearch.getActiveRow());
		lst_ConsignorSearch.getValue(1);

		//#CM568323
		// Set Parameter to be returned to parents screen
		ForwardParameters param = new ForwardParameters();
		//#CM568324
		// Consignor Code
		param.setParameter(CONSIGNORCODE_KEY, lst_ConsignorSearch.getValue(2));
		//#CM568325
		// Consignor Name
		param.setParameter(CONSIGNORNAME_KEY, lst_ConsignorSearch.getValue(3));

		//#CM568326
		// Changes to the parents screen.
		parentRedirect(param);
	}

	//#CM568327
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
		//#CM568328
		// Retrieval flag is acquired.
		String flag = viewState.getString(SEARCHCONSIGNOR_KEY);

		if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM568329
			// Listbox is maintained in Session
			SessionStoragePlanConsignorRet listbox =
				(SessionStoragePlanConsignorRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "next");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_RESULT))
		{
			//#CM568330
			// Listbox is maintained in Session
			SessionStorageResultConsignorRet listbox =
				(SessionStorageResultConsignorRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "next");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_STOCK))
		{
			//#CM568331
			// Listbox is maintained in Session
			SessionStorageStockConsignorRet listbox =
				(SessionStorageStockConsignorRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "next");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY))
		{
			//#CM568332
			// Listbox is maintained in Session
			SessionInventoryConsignorRet listbox =
				(SessionInventoryConsignorRet) this.getSession().getAttribute("LISTBOX");
			setInventoryList(listbox, "next");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK))
		{
			//#CM568333
			// Listbox is maintained in Session
			SessionInventoryStockConsignorRet listbox =
				(SessionInventoryStockConsignorRet) this.getSession().getAttribute("LISTBOX");
			setInventoryStockList(listbox, "next");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STOCKMOVE))
		{
			//#CM568334
			// Listbox is maintained in Session
			SessionStorageMovementConsignoreRet listbox =
				(SessionStorageMovementConsignoreRet) this.getSession().getAttribute("LISTBOX");
			setMoveList(listbox, "next");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_WORK))
		{
			//#CM568335
			// Listbox is maintained in Session
			SessionStorageWorkInfoConsignorRet listbox =
				(SessionStorageWorkInfoConsignorRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "next");
		}
	}

	//#CM568336
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
		//#CM568337
		// Retrieval flag is acquired.
		String flag = viewState.getString(SEARCHCONSIGNOR_KEY);

		if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM568338
			// Listbox is maintained in Session
			SessionStoragePlanConsignorRet listbox =
				(SessionStoragePlanConsignorRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "previous");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_RESULT))
		{
			//#CM568339
			// Listbox is maintained in Session
			SessionStorageResultConsignorRet listbox =
				(SessionStorageResultConsignorRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "previous");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_STOCK))
		{
			//#CM568340
			// Listbox is maintained in Session
			SessionStorageStockConsignorRet listbox =
				(SessionStorageStockConsignorRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "previous");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY))
		{
			//#CM568341
			// Listbox is maintained in Session
			SessionInventoryConsignorRet listbox =
				(SessionInventoryConsignorRet) this.getSession().getAttribute("LISTBOX");
			setInventoryList(listbox, "previous");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK))
		{
			//#CM568342
			// Listbox is maintained in Session
			SessionInventoryStockConsignorRet listbox =
				(SessionInventoryStockConsignorRet) this.getSession().getAttribute("LISTBOX");
			setInventoryStockList(listbox, "previous");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STOCKMOVE))
		{
			//#CM568343
			// Listbox is maintained in Session
			SessionStorageMovementConsignoreRet listbox =
				(SessionStorageMovementConsignoreRet) this.getSession().getAttribute("LISTBOX");
			setMoveList(listbox, "previous");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_WORK))
		{
			//#CM568344
			// Listbox is maintained in Session
			SessionStorageWorkInfoConsignorRet listbox =
				(SessionStorageWorkInfoConsignorRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "previous");
		}
	}

	//#CM568345
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
		//#CM568346
		// Retrieval flag is acquired.
		String flag = viewState.getString(SEARCHCONSIGNOR_KEY);

		if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM568347
			// Listbox is maintained in Session
			SessionStoragePlanConsignorRet listbox =
				(SessionStoragePlanConsignorRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "last");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_RESULT))
		{
			//#CM568348
			// Listbox is maintained in Session
			SessionStorageResultConsignorRet listbox =
				(SessionStorageResultConsignorRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "last");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_STOCK))
		{
			//#CM568349
			// Listbox is maintained in Session
			SessionStorageStockConsignorRet listbox =
				(SessionStorageStockConsignorRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "last");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY))
		{
			//#CM568350
			// Listbox is maintained in Session
			SessionInventoryConsignorRet listbox =
				(SessionInventoryConsignorRet) this.getSession().getAttribute("LISTBOX");
			setInventoryList(listbox, "last");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK))
		{
			//#CM568351
			// Listbox is maintained in Session
			SessionInventoryStockConsignorRet listbox =
				(SessionInventoryStockConsignorRet) this.getSession().getAttribute("LISTBOX");
			setInventoryStockList(listbox, "last");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STOCKMOVE))
		{
			//#CM568352
			// Listbox is maintained in Session
			SessionStorageMovementConsignoreRet listbox =
				(SessionStorageMovementConsignoreRet) this.getSession().getAttribute("LISTBOX");
			setMoveList(listbox, "last");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_WORK))
		{
			//#CM568353
			// Listbox is maintained in Session
			SessionStorageWorkInfoConsignorRet listbox =
				(SessionStorageWorkInfoConsignorRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "last");
		}
	}

	//#CM568354
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
		//#CM568355
		// Retrieval flag is acquired.
		String flag = viewState.getString(SEARCHCONSIGNOR_KEY);

		if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_PLAN))
		{
			//#CM568356
			// Listbox is maintained in Session
			SessionStoragePlanConsignorRet listbox =
				(SessionStoragePlanConsignorRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "first");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_RESULT))
		{
			//#CM568357
			// Listbox is maintained in Session
			SessionStorageResultConsignorRet listbox =
				(SessionStorageResultConsignorRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "first");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_STOCK))
		{
			//#CM568358
			// Listbox is maintained in Session
			SessionStorageStockConsignorRet listbox =
				(SessionStorageStockConsignorRet) this.getSession().getAttribute("LISTBOX");
			setStockList(listbox, "first");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY))
		{
			//#CM568359
			// Listbox is maintained in Session
			SessionInventoryConsignorRet listbox =
				(SessionInventoryConsignorRet) this.getSession().getAttribute("LISTBOX");
			setInventoryList(listbox, "first");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK))
		{
			//#CM568360
			// Listbox is maintained in Session
			SessionInventoryStockConsignorRet listbox =
				(SessionInventoryStockConsignorRet) this.getSession().getAttribute("LISTBOX");
			setInventoryStockList(listbox, "first");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STOCKMOVE))
		{
			//#CM568361
			// Listbox is maintained in Session
			SessionStorageMovementConsignoreRet listbox =
				(SessionStorageMovementConsignoreRet) this.getSession().getAttribute("LISTBOX");
			setMoveList(listbox, "first");
		}
		else if (flag.equals(StorageSupportParameter.SEARCH_STORAGE_WORK))
		{
			//#CM568362
			// Listbox is maintained in Session
			SessionStorageWorkInfoConsignorRet listbox =
				(SessionStorageWorkInfoConsignorRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "first");
		}
	}

	//#CM568363
	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM568364
	/**
	 * Do the Processing when the Close button is pressed. <BR>
	 * <BR>
	 * The list box is closed, and changes to the parents screen.  <BR>
	 * <BR>
	 * @param e ActionEvent The class which stores information on the event.
	 * @throws Exception It reports on all exceptions.
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		//#CM568365
		// Listbox is maintained in Session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM568366
		// When there is a value in Session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM568367
				// Close the statement.
				finder.close();
			}
			//#CM568368
			// Close the connection.
			sessionret.closeConnection();
		}
		//#CM568369
		// Delete it from the session.
		this.getSession().removeAttribute("LISTBOX");
		//#CM568370
		// Return to the parents screen
		parentRedirect(null);
	}
}
//#CM568371
//end of class
