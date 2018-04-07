package jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret;
//#CM6133
/*
 * Created on 2004/09/09 Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is
 * subject to license terms.
 */

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM6134
/**
 * This this displays the old inventory list by Consignor code or Storage date. <BR>
 * Invoke this class form the ListDeadStockListBusiness class.<BR>
 * Display the obtained result in the listbox.<BR>
 * Store the instance to the Session when you use this class.
 * Delete from the Session after using.<BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * Input the following parameters.<BR>
 * 1.Search Process(<CODE>SessionDeadStockListRet(Connection conn, StockControlParameter stParam)</CODE>Method)<BR>
 * <DIR>
 *   Execute when listbox screen is initial/default displayed.<BR>
 *   Invoke <CODE>find (StockControlParameter stParam)</CODE> method and search for the Stock info.<BR>
 * <BR>
 *   <search conditions>
 *   <DIR>
 *     Consignor code (Mandatory item (count)<BR>
 *     Storage Date (Mandatory item (count)<BR>
 *     Stock status(Center Inventory)<BR>
 *     Stock qty shall be 1 or more<BR>
 *     ASRS area<BR>
 *   </DIR>
 *   <Search table> <BR>
 *   <DIR>
 *     DMSTOCK<BR>
 *   </DIR>
 * </DIR>
 * 2.User clicks the display button.
 * <DIR>
 * Execute <CODE>find</CODE>. Pass the input parameter to <CODE>stParam</CODE>. <BR>
 * <CODE>find</CODE> searches through the DMSTOCK table and return the count. </DIR> <BR>
 * The result becomes following parameter to execute <CODE>getEntities ()</CODE>when there is the count.<BR>
 * <BR>
 * 
 * <DIR>Consignor code<BR>
 * Consignor name<BR>
 * Storage Date<BR>
 * Item code<BR>
 * Item name<BR>
 * Division<BR>
 * Location<BR>
 * Packing qty per case<BR>
 * Packing qty per piece<BR>
 * Inventory packing qty per case<BR>
 * Inventory packing qty per piece<BR>
 * Case ITF<BR>
 * Bundle ITF<BR>
 * Expiry Date<BR>
 * Area No.<BR>
 * Area Name<BR>
 * </DIR>
 * 
 * Display the Navigation bar if the record counts exceeds 20. <BR>
 * Enable to display the data using Navigation bar.<BR>
 * <BR>
 * <BR>
 * 3.Pre-requisite<BR>
 * <DIR>
 * Possible to be used if user login is executed. <BR>
 * </DIR> <BR>
 * <BR>
 * 4.Post-process status<BR>
 * <DIR>
 * Display the result correctly. <BR>
 * Error: Return null. <BR>
 * </DIR>
 * 
 * <BR>
 * <BR>
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <CODE>SessionDeadStockListRet.java</CODE><BR>
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/09/09</TD>
 * <TD>suresh kayamboo</TD>
 * <TD>New</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:12:16 $
 * @author  $Author: suresh $
 */
public class SessionDeadStockListRet extends SessionRet
{
	//#CM6135
	// Class fields --------------------------------------------------

	//#CM6136
	// Class variables -----------------------------------------------
	//#CM6137
	/**
	 * Area No. of AS/RS(for Display hyphenation)
	 */
	private String[] wAsAreaNo;

	//#CM6138
	// Class method --------------------------------------------------
	//#CM6139
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/04 05:12:16 $");
	}

	//#CM6140
	// Constructors --------------------------------------------------
	//#CM6141
	/**
	 * Invoke <CODE>find(StockControlParameter param)</CODE> Method to execute search. <BR>
     * Invoke the count of obtained data by <CODE>find (StockControlParameter param)</CODE> method. <BR>
     *  Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
     * <BR>
     * <DIR>
     * [search conditions] <BR>
     * <DIR>
     * AS/RS Area No.<BR>
     * Storage Date/Time<BR>
     * Consignor code <BR>
     * Stock status is in Center Inventory  <BR>
     * Stock qty shall be 1 or more <BR>
     * </DIR>
     * </DIR>
     * @param conn       <CODE>Connection</CODE>
     * @param stParam    Parameter including <CODE>StockControlParameter</CODE>search result
     * @throws Exception Announce this when any exception generates.
	 */
	public SessionDeadStockListRet(Connection conn, StockControlParameter stParam) throws Exception
	{
		wConn = conn;
		find(stParam);
	}

	//#CM6142
	// Public methods ------------------------------------------------
	//#CM6143
	/**
	 * Return the designated number of resurch results of the stock info <CODE> (DMSTOCK)</CODE>.<BR>
     * <BR>
     * <DIR>
     * Return search result to array.<BR>
     * </DIR>
     * @return search result sequence of Stock info<CODE>(DMSTOCK)</CODE>
	 */
	public Parameter[] getEntities()
	{
		StockControlParameter[] resultArray = null;
		Stock[] stock = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				stock = (Stock[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (StockControlParameter[]) convertToStockParams(stock);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM6144
	// Package methods -----------------------------------------------

	//#CM6145
	// Protected methods ---------------------------------------------

	//#CM6146
	// Private methods -----------------------------------------------
	//#CM6147
	/**
	 * Set the input parameter in the stock search key.<BR>
     * Print the SQL statement based on the input parameter and search for the Stock info<CODE> (Dmstock)</CODE>. <BR>
     * Maintain the <code>StockFinder</code> for searching as an instance variable. <BR>
     * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
     * @param stParam<CODE>StockControlParameter</CODE> Parameter including search result
	 * 
	 */
	private void find(StockControlParameter stParam) throws Exception
	{
		try
		{
			//#CM6148
			// Obtain the AS/RS area No. of the Area for display hyphenation. 
			AreaOperator ao = new AreaOperator(wConn);
			int[] asSystemDiscKey = {Area.SYSTEM_DISC_KEY_ASRS};
			wAsAreaNo = ao.getAreaNo(asSystemDiscKey);
		}
		catch (ScheduleException se)
		{
			//#CM6149
			// Do not regard as an error when no area is available for AS/RS.
		}
		
		StockSearchKey stKey = new StockSearchKey();

		if (!StringUtil.isBlank(stParam.getStorageDate()))
		{
			//#CM6150
			// One day advance from the date of screen input
			//#CM6151
			// Set the time to 00:00:00.
			Calendar compareDate = Calendar.getInstance();

			compareDate.setTime(stParam.getStorageDate());
			compareDate.set(Calendar.HOUR_OF_DAY, 0);
			compareDate.set(Calendar.MINUTE, 0);
			compareDate.set(Calendar.SECOND, 0);
			compareDate.add(Calendar.DAY_OF_MONTH, 1);

			Date instockDate = compareDate.getTime();

			//#CM6152
			// Searches for storage dates older than the compiled date.
			stKey.setInstockDate(instockDate, "<");
		}

		//#CM6153
		// Set search conditions
		if (!StringUtil.isBlank(stParam.getConsignorCode()))
		{
			stKey.setConsignorCode(stParam.getConsignorCode()); //consignor code
		}
		//#CM6154
		// Center Inventory
		stKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM6155
		// Stock qty 1 or more
		stKey.setStockQty(0, ">");

		//#CM6156
		// Set the obtaining condition.
		stKey.setConsignorCodeCollect("");
		stKey.setConsignorNameCollect("");
		stKey.setInstockDateCollect("");
		stKey.setItemCodeCollect("");
		stKey.setItemName1Collect("");
		stKey.setCasePieceFlagCollect("");
		stKey.setLocationNoCollect("");
		stKey.setEnteringQtyCollect("");
		stKey.setBundleEnteringQtyCollect("");
		stKey.setStockQtyCollect("");
		stKey.setItfCollect("");
		stKey.setBundleItfCollect("");
		stKey.setUseByDateCollect("");
		stKey.setAreaNoCollect();
		
		//#CM6157
		//Adding date. This is used to set the latest Consignor Name
		stKey.setRegistDateCollect("");

		//#CM6158
		// Set the obtaining sequence.
		stKey.setInstockDateOrder(1, true);
		stKey.setItemCodeOrder(2, true);
		stKey.setCasePieceFlagOrder(3, true);
		stKey.setLocationNoOrder(4, true);

		wFinder = (DatabaseFinder) new StockFinder(wConn);
		wFinder.open();
		int count = wFinder.search(stKey);
		wLength = count;
		wCurrent = 0;

	}

	//#CM6159
	/**
	 * This Method is used for the purpose to set Stock info to <CODE>StockParameter</CODE>.<BR>
     * 
     * @param ety Stock info
     * @return <CODE>StockParameter</CODE> in whcih Stock info is set.
	 */
	private Parameter[] convertToStockParams(Entity[] ety) throws Exception
	{
		StockControlParameter[] stParam = null;
		Stock[] stock = (Stock[]) ety;
		String registDate = "1";
		if ((stock != null) && (stock.length != 0))
		{
			AreaOperator ao = new AreaOperator(wConn);
			stParam = new StockControlParameter[stock.length];
			for (int i = 0; i < stock.length; i++)
			{
				stParam[i] = new StockControlParameter();

				//#CM6160
				// Maps the search results.
				//#CM6161
				// For the added date with the same Consignor code and different Consignor Name
				if ((i == 0) || (Long.parseLong(registDate) < Long.parseLong(getDateValue(stock[i].getRegistDate()))))
				{
					registDate = getDateValue(stock[i].getRegistDate());
					stParam[0].setConsignorCode(stock[i].getConsignorCode());
					stParam[0].setConsignorName(stock[i].getConsignorName());
				}
				stParam[i].setStorageDate(stock[i].getInstockDate());
				stParam[i].setItemCode(stock[i].getItemCode());
				stParam[i].setItemName(stock[i].getItemName1());
				stParam[i].setCasePieceFlagName(DisplayUtil.getPieceCaseValue(stock[i].getCasePieceFlag()));
				stParam[i].setLocationNo(stock[i].getLocationNo());
				stParam[i].setAreaNo(stock[i].getAreaNo());
				stParam[i].setEnteringQty(stock[i].getEnteringQty());
				stParam[i].setBundleEnteringQty(stock[i].getBundleEnteringQty());
				stParam[i].setStockCaseQty(
						DisplayUtil.getCaseQty(
								stock[i].getStockQty(), 
								stock[i].getEnteringQty(), 
								stock[i].getCasePieceFlag()));
				stParam[i].setStockPieceQty(
						DisplayUtil.getPieceQty(
								stock[i].getStockQty(), 
								stock[i].getEnteringQty(), 
								stock[i].getCasePieceFlag()));
				stParam[i].setITF(stock[i].getItf());
				stParam[i].setBundleITF(stock[i].getBundleItf());
				stParam[i].setUseByDate(stock[i].getUseByDate());
				//#CM6162
				// Set the instance variable Area No. for screen display.
				stParam[i].setAreaNoArray(wAsAreaNo);
				stParam[i].setAreaName(ao.getAreaName(stock[i].getAreaNo()));
			}
		}
		return stParam;
	}

	//#CM6163
	/**
	 * This private method generate characters from the date.
	 * Set the added date using the parameter.
	 * Return the added date in characters.
	 * @param date
	 * @return
	 */
	private String getDateValue(Date date)
	{
		String datNumS = null;

		if (date != null)
		{
			//#CM6164
			// Create the pattern matching to 24 hours.
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			datNumS = sdf.format(date).trim();
		}

		return datNumS;
	}

}
//#CM6165
//end of class
