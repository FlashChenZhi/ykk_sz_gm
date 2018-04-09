// $Id: ListAsOperationCycleItemBusiness.java,v 1.2 2006/10/26 05:33:55 suresh Exp $

//#CM39401
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.listasrsoperationcycleitem;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.asrs.display.web.listbox.sessionret.SessionAsOperationCycleItemRet;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.asrs.dbhandler.ASInOutResultHandler;
import jp.co.daifuku.wms.base.dbhandler.InOutResultSearchKey;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.asrs.entity.ASInOutResult;

//#CM39402
/**
 * Designer : M.Koyama <BR>
 * Maker : M.Koyama <BR>
 * <BR>
 * The Results details list according to item list box class. <BR>
 * Retrieve it based on Warehouse input from the parents screen, the date range, and terminal no. <BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.initial display (<CODE>page_Load(ActionEvent e) </CODE> method)<BR>
 * <BR>
 * <DIR>
 * 	Make Warehouse input from the parents screen, the date range, and terminal no as a key, retrieve, and display it on the screen. <BR>
 * <BR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 05:33:55 $
 * @author  $Author: suresh $
 */
public class ListAsOperationCycleItemBusiness extends ListAsOperationCycleItem implements WMSConstants
{
	//#CM39403
	// Class fields --------------------------------------------------
	//#CM39404
	/** 
	 * key to transfer warehouse
	 */
	public static final String WAREHOUSE_KEY = "WAREHOUSE_KEY";

	//#CM39405
	/** 
	 * key to transfer start date
	 */
	public static final String STARTDATE_KEY = "STARTDATE_KEY";

	//#CM39406
	/** 
	 * key to transfer start time
	 */
	public static final String STARTTIME_KEY = "STARTTIME_KEY";

	//#CM39407
	/** 
	 * key to transfer end date
	 */
	public static final String ENDDATE_KEY = "ENDDATE_KEY";

	//#CM39408
	/** 
	 * key to transfer end date
	 */
	public static final String ENDTIME_KEY = "ENDTIME_KEY";

	//#CM39409
	/** 
	 * key to transfer terminal no.
	 */
	public static final String RMNO_KEY = "RMNO_KEY";

	//#CM39410
	// Class variables -----------------------------------------------

	//#CM39411
	// Class method --------------------------------------------------

	//#CM39412
	// Constructors --------------------------------------------------

	//#CM39413
	// Public methods ------------------------------------------------

	//#CM39414
	/**
	 * screen initialization
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		//#CM39415
		// fetch locale
		Locale locale = this.getHttpRequest().getLocale();

		//#CM39416
		// set screen name
		//#CM39417
		// Results details list according to item
		lbl_ListName.setText(DisplayText.getText("TLE-W0933"));

		//#CM39418
		// fetch parameter
		//#CM39419
		// warehouse
		String warehouse = request.getParameter(WAREHOUSE_KEY);
		//#CM39420
		// start date
		String startdate = request.getParameter(STARTDATE_KEY);
		//#CM39421
		// start time
		String starttime = request.getParameter(STARTTIME_KEY);
		//#CM39422
		// end date
		String enddate = request.getParameter(ENDDATE_KEY);
		//#CM39423
		// end time
		String endtime = request.getParameter(ENDTIME_KEY);
		//#CM39424
		// terminal no
		String rmno = request.getParameter(RMNO_KEY);
		this.getViewState().setString(RMNO_KEY, rmno);

		//#CM39425
		// fetch connection
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		//#CM39426
		// set header item other than listcell
		//#CM39427
		// warehouse name
		ASFindUtil util = new ASFindUtil(conn);
		lbl_JavaSetWareHouse.setText(util.getWareHouseName(warehouse));
		//#CM39428
		// start date
		if( !StringUtil.isBlank(startdate) )
		{
			txt_FDateStrt.setText(WmsFormatter.toDispDate(startdate, locale));
		}
		//#CM39429
		// start time
		if( !StringUtil.isBlank(starttime) )
		{
			txt_FTimeStrt.setText(WmsFormatter.toDispTime(starttime, locale));
		}
		//#CM39430
		// end date
		if( !StringUtil.isBlank(enddate) )
		{
			txt_FDateEnd.setText(WmsFormatter.toDispDate(enddate, locale));
		}
		//#CM39431
		// end time
		if( !StringUtil.isBlank(endtime) )
		{
			txt_FTimeEnd.setText(WmsFormatter.toDispTime(endtime, locale));
		}
		//#CM39432
		// terminal no
		lbl_JavaSetMachineNo.setText(rmno);

		//#CM39433
		// close connection in session
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//#CM39434
			// close connection
			sRet.closeConnection();
			//#CM39435
			// delete from session
			this.getSession().removeAttribute("LISTBOX");
		}

		//#CM39436
		// set parameter
		AsScheduleParameter param = new AsScheduleParameter();
		//#CM39437
		// warehouse
		param.setWareHouseNo(warehouse);
		//#CM39438
		// start date
		param.setStartDate(startdate);
		//#CM39439
		// start time
		param.setStartTime(starttime);
		//#CM39440
		// end date
		param.setEndDate(enddate);
		//#CM39441
		// end time
		param.setEndTime(endtime);
		//#CM39442
		// terminal no
		param.setRackmasterNo(rmno);

		//#CM39443
		// generate SessionRet instance
		SessionAsOperationCycleItemRet listbox = new SessionAsOperationCycleItemRet(conn, param);
		//#CM39444
		//save the listbox in session
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	//#CM39445
	/**
	 * call this before calling the respective control events<BR>
	 * @param e ActionEvent this class stores the event info
	 * @throws Exception report all the exceptions
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
	}

	//#CM39446
	// Package methods -----------------------------------------------

	//#CM39447
	// Protected methods ---------------------------------------------

	//#CM39448
	// Private methods -----------------------------------------------
	//#CM39449
	/**
	 * method to change page <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception report all the exceptions
	 */
	private void setList(SessionAsOperationCycleItemRet listbox, String actionName) throws Exception
	{
		//#CM39450
		// set page info
		listbox.setActionName(actionName);

		//#CM39451
		// fetch search result
		AsScheduleParameter[] rparam = (AsScheduleParameter[])listbox.getEntities();

		int len = 0;
		if (rparam != null)
			len = rparam.length;
		if (len > 0)
		{
			//#CM39452
			// set value in pager
			//#CM39453
			// max. number
			pgr_U.setMax(listbox.getLength());
			//#CM39454
			// variables for 1 Page display
			pgr_U.setPage(listbox.getCondition());
			//#CM39455
			// start position
			pgr_U.setIndex(listbox.getCurrent() + 1);
			//#CM39456
			// max. number
			pgr_D.setMax(listbox.getLength());
			//#CM39457
			// variables for 1 Page display
			pgr_D.setPage(listbox.getCondition());
			//#CM39458
			// start position
			pgr_D.setIndex(listbox.getCurrent() + 1);

			//#CM39459
			// hide message
			lbl_InMsg.setVisible(false);

			//#CM39460
			// delete all rows
			lst_AsrsOperationCycleItem.clearRow();

			//#CM39461
			// used in tool tip
			//#CM39462
			// consignor name
			String title_ConsignorName = DisplayText.getText("LBL-W0026");
			//#CM39463
			// item name
			String title_ItemName = DisplayText.getText("LBL-W0103");

			Connection conn = null;

			try
			{
				//#CM39464
				// fetch connection
				conn = ConnectionManager.getConnection(DATASOURCE_NAME);

				//#CM39465
				// fetch station no. with refer to terminal
				AisleHandler aisleHandler = new AisleHandler(conn);
				AisleSearchKey aisleKey = new AisleSearchKey();
				aisleKey.setAisleNumber(new Integer(this.getViewState().getString(RMNO_KEY)).intValue());
				Aisle aisle = (Aisle)aisleHandler.findPrimary(aisleKey);

				ASInOutResultHandler ioHandler = new ASInOutResultHandler(conn);
				InOutResultSearchKey ioKey = new InOutResultSearchKey();

				for (int i = 0; i < len; i++)
				{
					//#CM39466
					// fetch last row
					int count = lst_AsrsOperationCycleItem.getMaxRows();
					//#CM39467
					// add row
					lst_AsrsOperationCycleItem.addRow();

					//#CM39468
					// move to last record
					lst_AsrsOperationCycleItem.setCurrentRow(count);
					lst_AsrsOperationCycleItem.setValue(1, Integer.toString(count + listbox.getCurrent()));

					//#CM39469
					// The Storage qty of search condition + Consignor code+item code is acquired.
					//#CM39470
					// start date,start time
					Date startDate = null;
					String strStartDate = request.getParameter(STARTDATE_KEY) + request.getParameter(STARTTIME_KEY);
					if( !StringUtil.isBlank(strStartDate) )
					{
						startDate = WmsFormatter.getTimeStampDate(strStartDate);
					}
					//#CM39471
					// end date,end time
					Date endDate = null;
					String strEndDate = request.getParameter(ENDDATE_KEY) + request.getParameter(ENDTIME_KEY);
					if( !StringUtil.isBlank(strEndDate) )
					{
						endDate = WmsFormatter.getTimeStampDate(strEndDate);
					}

					//#CM39472
					// set the fetch condition
					//#CM39473
					//
					//#CM39474
					// SELECT SUM(INOUTQUANTITY)
					//#CM39475
					//    FROM INOUTRESULT
					//#CM39476
					//        WHERE {0} {1} {2} {3} {4} {5}
					//#CM39477
					//
					//#CM39478
					//            {0} : AISLESTATIONNUMBER = 'aisleNo'
					//#CM39479
					//            if (fromdate != null)
					//#CM39480
					//            {
					//#CM39481
					//                {1} : AND INOUTRESULT.STOREDATE >= DBFormat.format(fromDate)
					//#CM39482
					//            }
					//#CM39483
					//
					//#CM39484
					//            if (todate != null)
					//#CM39485
					//            {
					//#CM39486
					//                {2} : AND INOUTRESULT.STOREDATE <= DBFormat.format(toDate)
					//#CM39487
					//            }
					//#CM39488
					//            {3} : AND RESULTKIND = resultkind
					//#CM39489
					//            {4} : AND CONSIGNOR_CODE = 'consignor code'
					//#CM39490
					//            {5} : AND ITEMKEY = 'item code'

					ioKey.KeyClear();
					ioKey.setInOutQuantityCollect("SUM");

					//#CM39491
					// aisle station,No
					ioKey.setAisleStationNumber(aisle.getStationNumber());
					//#CM39492
					// start date
					if (startDate != null)
					{
						ioKey.setStoreDate(startDate, ">=");
					}
					//#CM39493
					// end date
					if (endDate != null)
					{
						ioKey.setStoreDate(endDate, "<=");
					}
					//#CM39494
					// result creation type(Acquire only the Storage qty here. )
					ioKey.setResultKind(ASInOutResult.STORAGE);
					//#CM39495
					// consignor code
					ioKey.setConsignorCode(rparam[i].getConsignorCode());
					//#CM39496
					// item code
					ioKey.setItemCode(rparam[i].getItemCode());

					//#CM39497
					//do search
					long storageQty = 0;
					ASInOutResult[] ioResult = (ASInOutResult[]) ioHandler.find(ioKey);
					if( ioResult.length > 0 )
					{
						//#CM39498
						// The Storage qty of search condition + Consignor code+item code is acquired. 
						storageQty = ioResult[0].getSumInOutQuantity();
					}

					//#CM39499
					// consignor code
					lst_AsrsOperationCycleItem.setValue(2, rparam[i].getConsignorCode());
					//#CM39500
					// item code
					lst_AsrsOperationCycleItem.setValue(3, rparam[i].getItemCode());

					//#CM39501
					// case storage qty
					long storageCaseQty = DisplayUtil.getCaseQty(storageQty, rparam[i].getEnteringQty());
					lst_AsrsOperationCycleItem.setValue(4, WmsFormatter.getNumFormat(storageCaseQty));
					//#CM39502
					// Retrieval case qty
					long retrievalQty = Math.abs(rparam[i].getTotalInOutResultQty() - storageQty);
					long retrievalCaseQty = DisplayUtil.getCaseQty(retrievalQty, rparam[i].getEnteringQty());
					lst_AsrsOperationCycleItem.setValue(5, WmsFormatter.getNumFormat(retrievalCaseQty));

					//#CM39503
					// consignor name
					lst_AsrsOperationCycleItem.setValue(6, rparam[i].getConsignorName());
					//#CM39504
					// item name
					lst_AsrsOperationCycleItem.setValue(7, rparam[i].getItemName());

					//#CM39505
					// piece storage qty
					long storagePieceQty = DisplayUtil.getPieceQty(storageQty, rparam[i].getEnteringQty());
					lst_AsrsOperationCycleItem.setValue(8, WmsFormatter.getNumFormat(storagePieceQty));
					//#CM39506
					// Retrieval piece qty
					long retrievalPieceQty = DisplayUtil.getPieceQty(retrievalQty, rparam[i].getEnteringQty());
					lst_AsrsOperationCycleItem.setValue(9, WmsFormatter.getNumFormat(retrievalPieceQty));

					//#CM39507
					// add data to tool tip
					ToolTipHelper toolTip = new ToolTipHelper();
					toolTip.add(title_ConsignorName, rparam[i].getConsignorName());
					toolTip.add(title_ItemName, rparam[i].getItemName());

					//#CM39508
					// set tool tip	
					lst_AsrsOperationCycleItem.setToolTip(count, toolTip.getText());
				}
			}
			catch (Exception ex)
			{
				//#CM39509
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
					//#CM39510
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
			//#CM39511
			// set value to Pager
			//#CM39512
			// max. number
			pgr_U.setMax(0);
			//#CM39513
			// variables for 1 Page display
			pgr_U.setPage(0);
			//#CM39514
			// start position
			pgr_U.setIndex(0);
			//#CM39515
			// max. number
			pgr_D.setMax(0);
			//#CM39516
			// variables for 1 Page display
			pgr_D.setPage(0);
			//#CM39517
			// start position
			pgr_D.setIndex(0);

			//#CM39518
			// check the search result count
			String errorMsg = listbox.checkLength();
			//#CM39519
			// hide the header
			lst_AsrsOperationCycleItem.setVisible(false);
			//#CM39520
			// display error message
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	//#CM39521
	// Event handler methods -----------------------------------------
	//#CM39522
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

	//#CM39523
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

	//#CM39524
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

	//#CM39525
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

	//#CM39526
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

	//#CM39527
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
		//#CM39528
		//save the listbox in session
		SessionAsOperationCycleItemRet listbox = (SessionAsOperationCycleItemRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	//#CM39529
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
		//#CM39530
		//save the listbox in session
		SessionAsOperationCycleItemRet listbox = (SessionAsOperationCycleItemRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	//#CM39531
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
		//#CM39532
		//save the listbox in session
		SessionAsOperationCycleItemRet listbox = (SessionAsOperationCycleItemRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	//#CM39533
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
		//#CM39534
		//save the listbox in session
		SessionAsOperationCycleItemRet listbox = (SessionAsOperationCycleItemRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	//#CM39535
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	//#CM39536
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
		//#CM39537
		//save the listbox in session
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		//#CM39538
		// if value exists in session
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				//#CM39539
				// close the statement object
				finder.close();
			}
			//#CM39540
			// close the connection object
			sessionret.closeConnection();
		}
		//#CM39541
		// delete from session
		this.getSession().removeAttribute("LISTBOX");
		//#CM39542
		// return to origin screen
		parentRedirect(null);
	}


}
//#CM39543
//end of class
