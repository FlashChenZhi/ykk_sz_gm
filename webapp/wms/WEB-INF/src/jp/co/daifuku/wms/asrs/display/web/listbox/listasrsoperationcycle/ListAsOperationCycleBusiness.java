// $Id: ListAsOperationCycleBusiness.java,v 1.2 2006/10/26 05:33:00 suresh Exp $

//#CM39212
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.listasrsoperationcycle;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsOperationCycleRet;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.InOutResultHandler;
import jp.co.daifuku.wms.base.dbhandler.InOutResultSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.InOutResult;

//#CM39213
/**
 * Designer : M.Koyama <BR>
 * Maker : M.Koyama <BR>
 * <BR>
 *RM operation result list box class<BR>
 * Retrieve it based on Warehouse input from the parents screen and the date range. <BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.initial display (<CODE>page_Load(ActionEvent e) </CODE> method)<BR>
 * <BR>
 * <DIR>
 * 	Make Warehouse input from the parents screen and the date range as a key, retrieve, and display it on the screen. <BR>
 * <BR>
 * </DIR>
 * 2.Button of selected line(<CODE>lst_OperationCycle_Click</CODE> Method )<BR>
 * <BR>
 * <DIR>
 * 	Pass the content of results of the selection line to the parents screen, and close the list box. <BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:33:00 $
 * @author  $Author: suresh $
 */
public class ListAsOperationCycleBusiness extends ListAsOperationCycle implements WMSConstants
{
	//#CM39214
	// Class fields --------------------------------------------------
	//#CM39215
	/** 
	 * key to transfer warehouse
	 */
	public static final String WAREHOUSE_KEY = "WAREHOUSE_KEY";

	//#CM39216
	/** 
	 * key to transfer start date
	 */
	public static final String STARTDATE_KEY = "STARTDATE_KEY";

	//#CM39217
	/** 
	 * key to transfer start time
	 */
	public static final String STARTTIME_KEY = "STARTTIME_KEY";

	//#CM39218
	/** 
	 * key to transfer end date
	 */
	public static final String ENDDATE_KEY = "ENDDATE_KEY";

	//#CM39219
	/** 
	 * key to transfer end date
	 */
	public static final String ENDTIME_KEY = "ENDTIME_KEY";

	//#CM39220
	/** 
	 * key to transfer terminal no.
	 */
	public static final String RMNO_KEY = "RMNO_KEY";

	//#CM39221
	/** 
	 * key to transfer total no. of operation
	 */
	public static final String TOTALCOUNT_KEY = "TOTALCOUNT_KEY";

	//#CM39222
	/** 
	 * key to transfer storage operation
	 */
	public static final String STORAGECOUNT_KEY = "STORAGECOUNT_KEY";

	//#CM39223
	/** 
	 * key to transfer picking operation
	 */
	public static final String RETRIEVALCOUNT_KEY = "RETRIEVALCOUNT_KEY";

	//#CM39224
	/** 
	 * key to transfer storage item qty
	 */
	public static final String STORAGEITEMCOUNT_KEY = "STORAGEITEMCOUNT_KEY";

	//#CM39225
	/** 
	 * key to transfer picking item qty
	 */
	public static final String RETRIEVALITEMCOUNT_KEY = "RETRIEVALITEMCOUNT_KEY";

	//#CM39226
	// Class variables -----------------------------------------------

	//#CM39227
	// Class method --------------------------------------------------

	//#CM39228
	// Constructors --------------------------------------------------

	//#CM39229
	// Public methods ------------------------------------------------

	//#CM39230
	/**
	 * screen initialization
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM39231
		// fetch locale
		Locale locale = this.getHttpRequest().getLocale();

		//#CM39232
		// set screen name
		//#CM39233
		//RM operation result list
		lbl_ListName.setText(DisplayText.getText("TLE-W0932"));

		//#CM39234
		// fetch parameter
		//#CM39235
		// warehouse
		String warehouse = request.getParameter(WAREHOUSE_KEY);
		//#CM39236
		// start date
		String startdate = request.getParameter(STARTDATE_KEY);
		//#CM39237
		// start time
		String starttime = request.getParameter(STARTTIME_KEY);
		//#CM39238
		// end date
		String enddate = request.getParameter(ENDDATE_KEY);
		//#CM39239
		// end time
		String endtime = request.getParameter(ENDTIME_KEY);

		//#CM39240
		// fetch connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM39241
		// set header item other than listcell
		//#CM39242
		// warehouse name
		ASFindUtil util = new ASFindUtil(conn);
		lbl_JavaSetWareHouse.setText(util.getWareHouseName(warehouse));
		//#CM39243
		// start date
		if( !StringUtil.isBlank(startdate) )
		{
			txt_FDateStrt.setText(WmsFormatter.toDispDate(startdate, locale));
		}
		//#CM39244
		// start time
		if( !StringUtil.isBlank(starttime) )
		{
			txt_FTimeStrt.setText(WmsFormatter.toDispTime(starttime, locale));
		}
		//#CM39245
		// end date
		if( !StringUtil.isBlank(enddate) )
		{
			txt_FDateEnd.setText(WmsFormatter.toDispDate(enddate, locale));
		}
		//#CM39246
		// end time
		if( !StringUtil.isBlank(endtime) )
		{
			txt_FTimeEnd.setText(WmsFormatter.toDispTime(endtime, locale));
		}

		//#CM39247
		// close connection in session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM39248
			// close connection
			sRet.closeConnection();
			//#CM39249
			// delete from session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM39250
		// set parameter
		AsScheduleParameter param = new AsScheduleParameter();
		//#CM39251
		// warehouse
		param.setWareHouseNo(warehouse);
		//#CM39252
		// start date
		param.setStartDate(startdate);
		//#CM39253
		// start time
		param.setStartTime(starttime);
		//#CM39254
		// end date
		param.setEndDate(enddate);
		//#CM39255
		// end time
		param.setEndTime(endtime);

		//#CM39256
		// generate SessionRet instance
		SessionAsOperationCycleRet listbox = new SessionAsOperationCycleRet(conn, param);
		//#CM39257
		//save the listbox in session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM39258
	/**
	 * call this before calling the respective control events<BR>
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
	}

	//#CM39259
	// Package methods -----------------------------------------------

	//#CM39260
	// Protected methods ---------------------------------------------

	//#CM39261
	// Private methods -----------------------------------------------
	//#CM39262
	/**
	 * method to change page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setList(SessionAsOperationCycleRet listbox, String actionName) throws Exception
	{
		//#CM39263
		// set page info
		listbox.setActionName(actionName);

		//#CM39264
		// fetch search result
		AsScheduleParameter[] rparam = (AsScheduleParameter[])listbox.getEntities();

		int len = 0;
		if (rparam != null)
			len = rparam.length;
		if (len > 0)
		{
			//#CM39265
			// set value in pager
			//#CM39266
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM39267
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM39268
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM39269
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM39270
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM39271
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM39272
			// hide message
			lbl_InMsg.setVisible(false);

			//#CM39273
			// delete all rows
			lst_OperationCycle.clearRow();

			Connection conn = null;

			try
			{
				//#CM39274
				// fetch connection
				conn = ConnectionManager.getConnection(DATASOURCE_NAME);

				AisleHandler aisleHandler = new AisleHandler(conn);
				AisleSearchKey aisleKey = new AisleSearchKey();

				for (int i = 0; i < len; i++)
				{
					//#CM39275
					// fetch last row
					int count = lst_OperationCycle.getMaxRows();
					//#CM39276
					// add row
					lst_OperationCycle.addRow();

					//#CM39277
					// move to last record
					lst_OperationCycle.setCurrentRow(count);
					lst_OperationCycle.setValue(1, Integer.toString(count + listbox.getCurrent()));
					lst_OperationCycle.setValue(2, rparam[i].getRackmasterNo());

					//#CM39278
					// start date,start time
					Date startDate = null;
					String strStartDate = request.getParameter(STARTDATE_KEY) + request.getParameter(STARTTIME_KEY);
					if( !StringUtil.isBlank(strStartDate) )
					{
						startDate = WmsFormatter.getTimeStampDate(strStartDate);
					}
					//#CM39279
					// end date,end time
					Date endDate = null;
					String strEndDate = request.getParameter(ENDDATE_KEY) + request.getParameter(ENDTIME_KEY);
					if( !StringUtil.isBlank(strEndDate) )
					{
						endDate = WmsFormatter.getTimeStampDate(strEndDate);
					}

					aisleKey.KeyClear();
					//#CM39280
					// fetch station no. with refer to terminal
					aisleKey.setAisleNumber(new Integer(rparam[i].getRackmasterNo()).intValue());
					Aisle aisle = (Aisle)aisleHandler.findPrimary(aisleKey);

					//#CM39281
					// Storage Operations is acquired. 
					int storageQty = getOperationCount(conn, startDate, endDate, InOutResult.STORAGE, aisle.getStationNumber());
					//#CM39282
					// Picking Operations is acquired. 
					int retrievalQty = getOperationCount(conn, startDate, endDate, InOutResult.RETRIEVAL, aisle.getStationNumber());
					//#CM39283
					// Storage item qty is acquired. 
					int itemStorageQty = getItemOperationCount(conn, startDate, endDate, InOutResult.STORAGE, aisle.getStationNumber());
					//#CM39284
					// Picking item qty is acquired. 
					int itemRetrievalQty = getItemOperationCount(conn, startDate, endDate, InOutResult.RETRIEVAL, aisle.getStationNumber());

					//#CM39285
					// total no. of operation
					String qty = WmsFormatter.getNumFormat(storageQty+retrievalQty);
					lst_OperationCycle.setValue(3, qty);
					//#CM39286
					// Storagetotal no. of operation
					qty = WmsFormatter.getNumFormat(storageQty);
					lst_OperationCycle.setValue(4, qty);
					//#CM39287
					// total no. of picking operation
					qty = WmsFormatter.getNumFormat(retrievalQty);
					lst_OperationCycle.setValue(5, qty);
					//#CM39288
					// storage item qty
					qty = WmsFormatter.getNumFormat(itemStorageQty);
					lst_OperationCycle.setValue(6, qty);
					//#CM39289
					// picking item qty
					qty = WmsFormatter.getNumFormat(itemRetrievalQty);
					lst_OperationCycle.setValue(7, qty);
				}
			}
			catch (Exception ex)
			{
				//#CM39290
				// rollback connection
				if (conn != null)
				{
					conn.rollback();
				}
			}
			finally
			{
				try
				{
					//#CM39291
					// close connection
					if (conn != null)
					{
						conn.close();
					}
				}
				catch (SQLException se)
				{
				}
			}
		}
		else
		{
			//#CM39292
			// set value to Pager
			//#CM39293
			// max. number
			pgr_U.setMax(0);
			//#CM39294
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM39295
			// start position
			pgr_U.setIndex(0);
			//#CM39296
			// max. number
			pgr_D.setMax(0);
			//#CM39297
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM39298
			// start position
			pgr_D.setIndex(0);

			//#CM39299
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM39300
			// hide the header
			lst_OperationCycle.setVisible(false);
			//#CM39301
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM39302
	/**
	 * acquire operation qty<BR>
	 * @param conn connection
	 * @param Date startDate start date/time
	 * @param Date endDate end date/time
	 * @param int result result creation type
	 * @param String aisleNo aisle no
	 * @return int operation qty
	 * @throws Exception report all the exceptions
	 */
	private int getOperationCount(Connection conn, Date startDate, Date endDate, int result, String aisleNo) throws Exception
	{
		InOutResultHandler ioHandler = new InOutResultHandler(conn);
		InOutResultSearchKey ioKey = new InOutResultSearchKey();
			
		//#CM39303
		// set the fetch condition
		//#CM39304
		//
		//#CM39305
		// SELECT COUNT(AisleStationNumber) COUNT FROM INOUTRESULT
		//#CM39306
		//     WHERE {0} {1} {2} {3} ORDER BY ITEMKEY,STATIONNUMBER,LOCATIONNUMBER
		//#CM39307
		//
		//#CM39308
		//           {0} : AISLESTATIONNUMBER = 'aisleNo'
		//#CM39309
		//           if (fromDate != null)
		//#CM39310
		//           {
		//#CM39311
		//               {1} : AND INOUTRESULT.STOREDATE >= DBFormat.format(fromDate)
		//#CM39312
		//           }
		//#CM39313
		//           if (toDate != null)
		//#CM39314
		//           {
		//#CM39315
		//               {2} : AND INOUTRESULT.STOREDATE <= DBFormat.format(toDate)
		//#CM39316
		//           }
		//#CM39317
		//           {3} : AND RESULTKIND = result
		ioKey.setAisleStationNumberCollect("COUNT");
		ioKey.setAisleStationNumber(aisleNo);

		if (startDate != null)
		{
			ioKey.setStoreDate(startDate, ">=");
		}
		if (endDate != null)
		{
			ioKey.setStoreDate(endDate, "<=");
		}
		ioKey.setResultKind(result);

		//#CM39318
		//do search
		return ioHandler.count(ioKey);
	}

	//#CM39319
	/**
	 * acquire item operation qty<BR>
	 * @param conn connection
	 * @param Date startDate start date/time
	 * @param Date endDate end date/time
	 * @param int result result creation type
	 * @param String aisleNo aisle no
	 * @return int item operation qty
	 * @throws Exception report all the exceptions
	 */
	private int getItemOperationCount(Connection conn, Date startDate, Date endDate, int result, String aisleNo) throws Exception
	{
		InOutResultHandler ioHandler = new InOutResultHandler(conn);
		InOutResultSearchKey ioKey = new InOutResultSearchKey();
			
		//#CM39320
		// set the fetch condition
		//#CM39321
		//
		//#CM39322
		// SELECT COUNT(INOUTRESULT.ITEMKEY)
		//#CM39323
		//     FROM INOUTRESULT
		//#CM39324
		//         WHERE {0} {1} {2} {3} GROUP BY ITEMKEY
		//#CM39325
		//
		//#CM39326
		//               {0} : AISLESTATIONNUMBER = 'aisleNo'
		//#CM39327
		//               if (fromdate != null)
		//#CM39328
		//               {
		//#CM39329
		//                   {1} : AND INOUTRESULT.STOREDATE >= DBFormat.format(fromDate)
		//#CM39330
		//               }
		//#CM39331
		//
		//#CM39332
		//               if (todate != null)
		//#CM39333
		//               {
		//#CM39334
		//                   {2} : AND INOUTRESULT.STOREDATE <= " +DBFormat.format(toDate)
		//#CM39335
		//               }
		//#CM39336
		//               {3} : AND  RESULTKIND = resultkind

		ioKey.setItemCodeCollect("COUNT");
		ioKey.setAisleStationNumber(aisleNo);

		if (startDate != null)
		{
			ioKey.setStoreDate(startDate, ">=");
		}
		if (endDate != null)
		{
			ioKey.setStoreDate(endDate, "<=");
		}
		ioKey.setResultKind(result);
		
		ioKey.setConsignorCodeGroup(1);
		ioKey.setItemCodeGroup(2);

		//#CM39337
		//do search
		return ioHandler.count(ioKey);
	}

	//#CM39338
	// Event handler methods -----------------------------------------
	//#CM39339
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39340
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39341
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39342
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetWareHouse_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39343
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DayTimeRange_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39344
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39345
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM39346
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM39347
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FTimeStrt_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39348
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FTimeStrt_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM39349
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FTimeStrt_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM39350
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39351
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39352
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM39353
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEnd_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM39354
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FTimeEnd_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39355
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FTimeEnd_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM39356
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FTimeEnd_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM39357
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39358
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

	//#CM39359
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

	//#CM39360
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

	//#CM39361
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

	//#CM39362
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

	//#CM39363
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39364
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OperationCycle_EnterKey(ActionEvent e) throws Exception
	{
	}

	//#CM39365
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OperationCycle_TabKey(ActionEvent e) throws Exception
	{
	}

	//#CM39366
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OperationCycle_InputComplete(ActionEvent e) throws Exception
	{
	}

	//#CM39367
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OperationCycle_ColumClick(ActionEvent e) throws Exception
	{
	}

	//#CM39368
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OperationCycle_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39369
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_OperationCycle_Change(ActionEvent e) throws Exception
	{
	}

	//#CM39370
	/** 
	 * call the list cell select button click event process <BR>
	 * <BR>
	 *	set the result contents to caller screen and close the list box<BR>
	 * <BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void lst_OperationCycle_Click(ActionEvent e) throws Exception
	{
		//#CM39371
		// set the current row
		lst_OperationCycle.setCurrentRow(lst_OperationCycle.getActiveRow());
		lst_OperationCycle.getValue(1);

		//#CM39372
		// set parameter that is used to return to the caller screen
		ForwardParameters param = new ForwardParameters();
		//#CM39373
		// terminal no
		param.setParameter(RMNO_KEY, lst_OperationCycle.getValue(2));
		//#CM39374
		// total no. of operation
		param.setParameter(TOTALCOUNT_KEY, lst_OperationCycle.getValue(3));
		//#CM39375
		// Storage Operations
		param.setParameter(STORAGECOUNT_KEY, lst_OperationCycle.getValue(4));
		//#CM39376
		// Picking Operations
		param.setParameter(RETRIEVALCOUNT_KEY, lst_OperationCycle.getValue(5));
		//#CM39377
		// storage item qty
		param.setParameter(STORAGEITEMCOUNT_KEY, lst_OperationCycle.getValue(6));
		//#CM39378
		// picking item qty
		param.setParameter(RETRIEVALITEMCOUNT_KEY, lst_OperationCycle.getValue(7));

		//#CM39379
		// move to the caller screen
		parentRedirect(param);
	}

	//#CM39380
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
		//#CM39381
		//save the listbox in session
		SessionAsOperationCycleRet listbox = (SessionAsOperationCycleRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM39382
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
		//#CM39383
		//save the listbox in session
		SessionAsOperationCycleRet listbox = (SessionAsOperationCycleRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM39384
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
		//#CM39385
		//save the listbox in session
		SessionAsOperationCycleRet listbox = (SessionAsOperationCycleRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM39386
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
		//#CM39387
		//save the listbox in session
		SessionAsOperationCycleRet listbox = (SessionAsOperationCycleRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM39388
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39389
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
		//#CM39390
		//save the listbox in session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM39391
		// if value exists in session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM39392
				// close the statement object
				finder.close();
			}
			//#CM39393
			// close the connection object
			sessionret.closeConnection();
		}
		//#CM39394
		// delete from session
		this.getSession().removeAttribute("LISTBOX");
		//#CM39395
		// return to origin screen
		parentRedirect(null);
	}
}
//#CM39396
//end of class
