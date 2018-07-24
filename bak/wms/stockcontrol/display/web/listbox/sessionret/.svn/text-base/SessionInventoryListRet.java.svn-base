package jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret;
//#CM6166
/*
 * Created on Sep 15, 2004
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
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

//#CM6167
/**
 * Designer :   Muneendra <BR>
 * Maker :   Muneendra <BR>
 * <BR> 
 * This is the class to search and display the Stock info.<BR>
 * Store the instance to the Session when you use this class.
 * Delete from the Session after using.<BR>
 * Execute following processes in this class.<BR>
 * 1.Search Process(<CODE>SessionInventoryListRet(Connection conn, StockControlParameter param)</CODE>Method)<BR>
 * <DIR>
 *   Execute when listbox screen is initial/default displayed.<BR>
 *   Invoke <CODE>find (StockControlParameter param)</CODE> method and search for the Stock info.<BR>
 * <BR>
 *   <search conditions>*Mandatory item (count)
 *   <DIR>
 *     Work division:other than Delete<BR>
 *     Consignor code<BR>
 *     Start location<BR>
 *     End location<BR>
 *     Item code<BR>
 *     Stock status(Center Inventory)<BR>
 *     Stock qty shall be 1 or more<BR>
 *     Area other than ASRS<BR>
 *   </DIR>
 *   <Search table> <BR>
 *   <DIR>
 *     DMSTOCK<BR>
 *   </DIR>
 * </DIR>
 * 2.Display Process(<CODE>getEntities()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Obtain the data needed to display on the screen.<BR>
 *   Obtain the display info that was obtained in the search process.<BR>
 *   Set the search result back to <CODE>StockControlParameter</CODE> array. <BR>
 * <BR>
 *   <Items to be displayed>
 *   <DIR>
 *     Consignor code<BR>
 *     Consignor name<BR>
 *     Location<BR>
 *     Item code<BR>
 *     Item name<BR>
 *     Division<BR>
 *     Packing qty per case<BR>
 *     packed qty per bundle<BR>
 *     Stock Case Qty<BR>
 *     Stock piece qty<BR>
 *     Case ITF<BR>
 *     Bundle ITF<BR>
 *     Expiry Date<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:12:15 $
 * @author  $Author: suresh $
 */
public class SessionInventoryListRet extends SessionRet
{

	//#CM6168
	// Class fields --------------------------------------------------

	//#CM6169
	// Class variables -----------------------------------------------
	//#CM6170
	/**
	 * Consignor name
	 */
	private String wConsignorName;
	
	//#CM6171
	// Class method --------------------------------------------------
	//#CM6172
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/04 05:12:15 $");
	}

	//#CM6173
	// Constructors --------------------------------------------------
	//#CM6174
	/**
	 * Invoke <CODE>find(StockControlParameter param)</CODE> Method to execute search.<BR>
     * Invoke the count of obtained data by <CODE>find (StockControlParameter param)</CODE> method.<BR>
     *  Require to invoke the <CODE>getEntities</CODE> method to obtain the search result.<BR>
     * <BR>
     * <search conditions>*Mandatory item (count)
     *   <DIR>
     *     Work division:other than Delete<BR>
     *     Consignor code<BR>
     *     Start location<BR>
     *     End location<BR>
     *     Item code<BR>
     *     Stock status(Center Inventory)<BR>
     *     Stock qty shall be 1 or more<BR>
     *     Area other than ASRS<BR>
     *   </DIR>
     * 
     * @param conn       <code>Connection</code>
     * @param param      Parameter including <code>StockControlParameter</code>search result
     * @throws Exception Announce this when any exception generates.
	 */
	public SessionInventoryListRet(Connection conn, StockControlParameter param) throws Exception
	{
		//#CM6175
		// Maintain the connection.
		wConn = conn;

		//#CM6176
		// Search
		find(param);
	}

	//#CM6177
	// Public methods ------------------------------------------------
	//#CM6178
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
     * <BR>
     * <search result><BR>
     *   <DIR>
     *     Consignor code<BR>
     *     Consignor name<BR>
     *     Location<BR>
     *     Item code<BR>
     *     Item name<BR>
     *     Division<BR>
     *     Packing qty per case<BR>
     *     packed qty per bundle<BR>
     *     Stock Case Qty<BR>
     *     Stock piece qty<BR>
     *     Case ITF<BR>
     *     Bundle ITF<BR>
     *     Expiry Date<BR>
     *   </DIR>
     * @return <CODE>StockControlParameter</CODE> class including display info.
	 */
	public StockControlParameter[] getEntities()
	{
		Stock[] resultArray = null;
		StockControlParameter param[] = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				//#CM6179
				// Obtain the search result.
				StockFinder finder = (StockFinder) wFinder;

				resultArray = (Stock[]) finder.getEntities(wStartpoint, wEndpoint);
				//#CM6180
				// Set again to StockControlParameter
				param = (StockControlParameter[]) convertToStockControlParams(resultArray);
			}
			catch (Exception e)
			{
				//#CM6181
				//Record the error in the log file.
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}

		}
		wCurrent = wEndpoint;
		return param;
	}

	//#CM6182
	// Package methods -----------------------------------------------

	//#CM6183
	// Protected methods ---------------------------------------------

	//#CM6184
	// Private methods -----------------------------------------------
    //#CM6185
    /**
     * This method obtains search conditions from the parameter and search through the result View info.<BR>
     * 
     * @param param   Parameter to obtain search conditions
     */
	private void find(StockControlParameter param) throws Exception
	{
		//#CM6186
		// Search Key
		StockSearchKey searchKey = new StockSearchKey();
		//#CM6187
		// Search key to obtain the latest name.
		StockSearchKey nameKey = new StockSearchKey();

		//#CM6188
		// Work division:other than Delete
		searchKey.setStatusFlag(Stock.STATUS_FLAG_DELETE, "!=");
		nameKey.setStatusFlag(Stock.STATUS_FLAG_DELETE, "!=");

		//#CM6189
		// Consignor code
		searchKey.setConsignorCode(param.getConsignorCode());
		nameKey.setConsignorCode(param.getConsignorCode());

		//#CM6190
		// Start location
		if (!StringUtil.isBlank(param.getFromLocationNo()))
		{
			searchKey.setLocationNo(param.getFromLocationNo(), ">=");
			nameKey.setLocationNo(param.getFromLocationNo(), ">=");
		}
		//#CM6191
		// End location
		if (!StringUtil.isBlank(param.getToLocationNo()))
		{
			searchKey.setLocationNo(param.getToLocationNo(), "<=");
			nameKey.setLocationNo(param.getToLocationNo(), "<=");
		}

		//#CM6192
		// Item code
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			searchKey.setItemCode(param.getItemCode());
			nameKey.setItemCode(param.getItemCode());
		}

		//#CM6193
		// Stock status(Center Inventory)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		nameKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);

		//#CM6194
		// Stock qty shall be 1 or more
		searchKey.setStockQty(1, ">=");
		nameKey.setStockQty(1, ">=");

		AreaOperator AreaOperator = new AreaOperator(wConn);
		
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;

		//#CM6195
		// Obtain the Area other than ASRS and add it to the search conditions.
		//#CM6196
		// Search IS NULL if no corresponding area found.
		areaNo = AreaOperator.getAreaNo(areaType);
		searchKey.setAreaNo(areaNo);
		nameKey.setAreaNo(areaNo);

		//#CM6197
		// In the case where items aggregated in the same location are found
		if (param.getPrintCondition1().equals(StockControlParameter.PRINTINGCONDITION_INTENSIVEPRINTING_ON))
		{
			//#CM6198
			//Collect field names		
			searchKey.setConsignorCodeCollect("");
			searchKey.setLocationNoCollect("");
			searchKey.setItemCodeCollect("");
			searchKey.setUseByDateCollect("");

			//#CM6199
			// Aggregating conditions
			searchKey.setLocationNoGroup(1);
			searchKey.setItemCodeGroup(2);
			searchKey.setUseByDateGroup(3);
			searchKey.setConsignorCodeGroup(4);

			//#CM6200
			// Sorting sequence
			searchKey.setLocationNoOrder(1, true);
			searchKey.setItemCodeOrder(2, true);
			searchKey.setUseByDateOrder(3, true);
		}
		//#CM6201
		// When not aggregated
		else
		{
			//#CM6202
			// Sorting sequence
			searchKey.setLocationNoOrder(1, true);
			searchKey.setItemCodeOrder(2, true);
			searchKey.setCasePieceFlagOrder(3, true);
			searchKey.setUseByDateOrder(4, true);
			searchKey.setLastUpdateDateOrder(5, false);
		}

		wFinder = new StockFinder(wConn);
		//#CM6203
		// Cursor open
		wFinder.open();

		int count = wFinder.search(searchKey);
		//#CM6204
		// Initialize
		wLength = count;
		wCurrent = 0;

		//#CM6205
		// Obtain the latest Consignor name.
		nameKey.setConsignorNameCollect("");
		nameKey.setLastUpdateDateOrder(1, false);

		StockFinder consignorFinder = new StockFinder(wConn);
		consignorFinder.open();
		int nameCount = consignorFinder.search(nameKey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			Stock stock[] = (Stock[]) consignorFinder.getEntities(0, 1);

			if (stock != null && stock.length != 0)
			{
				wConsignorName = stock[0].getConsignorName();
			}
		}
		consignorFinder.close();

	}

    //#CM6206
    /**
     *
     * This class sets the Stock info for <CODE>StockControlParameter</CODE>.<BR>
     * 
     * @param result Stock info
     * @return StockControlParameter[] <CODE>StockControlParameter</CODE> class in which Stock info is set.
     */
  
	private Parameter[] convertToStockControlParams(Entity[] ety)
	{
		StockControlParameter[] stParam = null;
		Stock[] stock = (Stock[]) ety;

		//#CM6207
		// Translate Stock Entity into StockControlParameter.
		if ((stock != null) && (stock.length != 0))
		{
			stParam = new StockControlParameter[stock.length];
			for (int i = 0; i < stock.length; i++)
			{
				stParam[i] = new StockControlParameter();

				//#CM6208
				// Consignor code
				stParam[i].setConsignorCode(stock[i].getConsignorCode());
				//#CM6209
				// Consignor name
				stParam[i].setConsignorName(wConsignorName);
				//#CM6210
				// Location
				stParam[i].setLocationNo(stock[i].getLocationNo());
				//#CM6211
				// Item code
				stParam[i].setItemCode(stock[i].getItemCode());
				//#CM6212
				// Item name
				stParam[i].setItemName(stock[i].getItemName1());
				//#CM6213
				// Division
				stParam[i].setCasePieceFlagName(DisplayUtil.getPieceCaseValue(stock[i].getCasePieceFlag()));
				//#CM6214
				// Packing qty per case
				stParam[i].setEnteringQty(stock[i].getEnteringQty());
				//#CM6215
				// packed qty per bundle
				stParam[i].setBundleEnteringQty(stock[i].getBundleEnteringQty());
				//#CM6216
				// If Case/Piece division is Case or Not specified, both packed qty per case and stock case qty are obtained from total case qty.
				if (stock[i].getCasePieceFlag().equals(Stock.CASEPIECE_FLAG_CASE) || stock[i].getCasePieceFlag().equals(Stock.CASEPIECE_FLAG_NOTHING))
				{
					//#CM6217
					// Stock Case Qty
					stParam[i].setStockCaseQty(DisplayUtil.getCaseQty(stock[i].getStockQty(), stock[i].getEnteringQty()));
					//#CM6218
					// Stock piece qty
					stParam[i].setStockPieceQty(DisplayUtil.getPieceQty(stock[i].getStockQty(), stock[i].getEnteringQty()));
				}
				//#CM6219
				// If Case/Piece division is Piece, assign total qty to Stock Piece qty.
				else if (stock[i].getCasePieceFlag().equals(Stock.CASEPIECE_FLAG_PIECE))
				{
					//#CM6220
					// Stock Case Qty
					stParam[i].setStockCaseQty(0);
					//#CM6221
					// Stock piece qty
					stParam[i].setStockPieceQty(stock[i].getStockQty());
				}
				//#CM6222
				// Case ITF
				stParam[i].setITF(stock[i].getItf());
				//#CM6223
				// Bundle ITF
				stParam[i].setBundleITF(stock[i].getBundleItf());
				//#CM6224
				// Expiry Date
				stParam[i].setUseByDate(stock[i].getUseByDate());
			}
		}

		return stParam;

	}

}
//#CM6225
//end of class
