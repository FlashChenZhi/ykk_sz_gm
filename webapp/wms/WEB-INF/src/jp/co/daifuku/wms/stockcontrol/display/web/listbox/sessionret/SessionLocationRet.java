package jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret;
//#CM6388
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM6389
/**
 * Designer : Muneendra<BR>
 * Maker 	: Muneendra<BR> 
 * <BR>
 * This is the class to search and display location list from Stock info.<BR>
 * Store the instance to the Session when you use this class.
 * Delete from the Session after using. <BR>
 * Execute following processes in this class. <BR>
 * <BR>
 * 1.Search Process(<CODE>SessionLocationRet(Connection conn, StockControlParameter param)</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *   Execute when listbox screen is initial/default displayed.<BR>
 *   Invoke <CODE>find (StockControlParameter param)</CODE> method and search for the Stock info. <BR>
 * <BR>
 *  <DIR>
 *   <search conditions>Mandatory item (count)* <BR>
 *   <DIR>
 *     	Consignor code<BR>
 * 		Item code<BR>
 * 		Start item code<BR>
 * 		End item code<BR>
 * 		Case/Piece division<BR>
 * 		Location<BR>
 * 		Start location<BR>
 * 		End location<BR>
 *      Stock status is in Center Inventory <BR>
 *     	Stock qty:1 or more* <BR>
 *   </DIR>
 *   <Search table> <BR>
 *   <DIR>
 *     DMSTOCK <BR>
 *   </DIR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.Display Process(<CODE>getEntities()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *   Obtain the data needed to display on the screen. <BR>
 * <BR>
 *   1.Obtain the display info that was obtained in the search process. <BR>
 *   Set the search result back to the inventory information array. <BR>
 * <BR>
 *   <Items to be displayed>
 *   <DIR>
 *    Location No.<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/17</TD><TD>Muneendra</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:12:13 $
 * @author  $Author: suresh $
 */
public class SessionLocationRet extends SessionRet
{
	//#CM6390
	// Class fields --------------------------------------------------

	//#CM6391
	// Class variables -----------------------------------------------

	//#CM6392
	// Class method --------------------------------------------------
	//#CM6393
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/04 05:12:13 $");
	}

	//#CM6394
	// Constructors --------------------------------------------------
	//#CM6395
	/**
	 * Invoke <CODE>find(StockControlParameter param)</CODE> Method to execute search. <BR>
	 * Invoke the count of obtained data by <CODE>find (StockControlParameter param)</CODE> method.<BR>
	 *  Require to invoke the <CODE>getEntities</CODE> method to obtain the search result.<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param stParam      Parameter including <code>StockControlParameter</code>search result
	 * @throws Exception Announce this when any exception generates.
	 */
	public SessionLocationRet(Connection conn, StockControlParameter stParam) throws Exception
	{
		wConn = conn;
		find(stParam);
	}

	//#CM6396
	// Public methods ------------------------------------------------
	//#CM6397
	/**
	 * Return the designated number of resurch results of the stock info<BR>
	 * Execute This method executes the following processes.<BR>
	 * <BR>
	 * <DIR>
	 *   1.Calculate to specify the count of display data to be obtained.<BR>
	 *   2.Obtain the Stock info from the result set.<BR>
	 *   3.Obtain the display data from the Stock info and set it for <CODE>StockControlParameter</CODE>.<BR>
	 *   4.Return information to be displayed.<BR>
	 * </DIR>
	 * @return <CODE>StockControlParameter</CODE> class including display info.
	 */
	public StockControlParameter[] getEntities()
	{
		StockControlParameter[] resultArray = null;
		Stock[] stock = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				stock = (Stock[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = convertToStockParams(stock);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;

		return resultArray;
	}

	//#CM6398
	// Package methods -----------------------------------------------

	//#CM6399
	// Protected methods ---------------------------------------------

	//#CM6400
	// Private methods -----------------------------------------------
	//#CM6401
	/**
	 * This method obtains search conditions from the parameter and search through the Stock info.<BR>
	 * 
	 * @param stParam   Parameter to obtain search conditions
	 * @throws Exception Announce this when any exception generates.
	 */
	private void find(StockControlParameter stParam) throws Exception
	{
		StockSearchKey stKey = new StockSearchKey();

		if (!StringUtil.isBlank(stParam.getConsignorCode()))
		{
			//#CM6402
			// Consignor code
			stKey.setConsignorCode(stParam.getConsignorCode());
		}

		if (!StringUtil.isBlank(stParam.getItemCode()))
		{
			//#CM6403
			// Item code
			stKey.setItemCode(stParam.getItemCode());
		}

		if (!StringUtil.isBlank(stParam.getFromItemCode()))
		{
			//#CM6404
			// Start item code
			stKey.setItemCode(stParam.getFromItemCode(), ">=");
		}

		if (!StringUtil.isBlank(stParam.getToItemCode()))
		{
			//#CM6405
			// End item code
			stKey.setItemCode(stParam.getToItemCode(), "<=");
		}

		//#CM6406
		// Case/Piece division
		if (!StringUtil.isBlank(stParam.getCasePieceFlag()))
		{
			if (stParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_CASE))
			{
				stKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
			}
			else if (stParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
			{
				stKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
			}
			else if (stParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				stKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
			}
			else if (stParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_ALL))
			{
				//#CM6407
				//do nothing
			}
		}
		//#CM6408
		// When Location is set
		if (!StringUtil.isBlank(stParam.getLocationNo()))
		{
			stKey.setLocationNo(stParam.getLocationNo());
		}
		//#CM6409
		// When Start Location or End Location is set
		else
		{
			//#CM6410
			// Require to set the Start Location
			if (!StringUtil.isBlank(stParam.getFromLocationNo()))
			{
				stKey.setLocationNo(stParam.getFromLocationNo());
			}
			//#CM6411
			// Require to set the End Location.
			if (!StringUtil.isBlank(stParam.getToLocationNo()))
			{
				stKey.setLocationNo(stParam.getToLocationNo());
			}
		}
		//#CM6412
		// Stock status(Center Inventory)
		stKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM6413
		// Stock qty shall be 1 or more
		stKey.setStockQty(1, ">=");

		if (StockControlParameter.AREA_TYPE_FLAG_NOASRS.equals(stParam.getAreaTypeFlag()))
		{
			AreaOperator AreaOperator = new AreaOperator(wConn);
			
			String[] areaNo = null;
			int[] areaType = new int[2];
			areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
			areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
			
			//#CM6414
			// Obtain the Area other than ASRS and add it to the search conditions.
			//#CM6415
			// Search IS NULL if no corresponding area found.
			areaNo = AreaOperator.getAreaNo(areaType);
			stKey.setAreaNo(areaNo);
		}

		//#CM6416
		//Set the obtaining sequence.
		stKey.setLocationNoCollect("");

		//#CM6417
		//Set sort sequence
		stKey.setLocationNoOrder(1, true);

		//#CM6418
		//Set group sequence
		stKey.setLocationNoGroup(1);

		wFinder = new StockFinder(wConn);
		//#CM6419
		// Open cursor
		wFinder.open();

		int count = wFinder.search(stKey);
		wLength = count;
		wCurrent = 0;

	}

	//#CM6420
	/**
	 * This Method modifies <CODE>Stock</CODE>Entity to <CODE>StockControlParameter</CODE>parameter.<BR>
	 * @param stock Stock info
	 * @return StockControlParameter[] <CODE>StockControlParameter</CODE>parameter in which Stock info is set.
	 */
	private StockControlParameter[] convertToStockParams(Stock[] stock)
	{
		StockControlParameter[] stParam = null;
		if ((stock != null) && (stock.length != 0))
		{
			stParam = new StockControlParameter[stock.length];

			for (int i = 0; i < stock.length; i++)
			{
				stParam[i] = new StockControlParameter();
				if (stock[i].getLocationNo() != null && !" ".equals(stock[i].getLocationNo()))
				{
					stParam[i].setLocationNo(stock[i].getLocationNo());
				}
			}
		}
		return stParam;
	}

}
//#CM6421
//end of class
