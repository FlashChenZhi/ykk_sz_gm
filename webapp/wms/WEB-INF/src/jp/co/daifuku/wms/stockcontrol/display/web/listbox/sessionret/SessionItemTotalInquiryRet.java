package jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret;
//#CM6226
/*
 * Created on 2004/09/27 Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is
 * subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.stockcontrol.dbhandler.StockControlItemTotalInquiryFinder;
import jp.co.daifuku.wms.stockcontrol.entity.StockControlStock;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM6227
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * This is the class to search and display stock list per item (item aggregation) from Stock info.<BR>
 * Store the instance to the Session when you use this class.
 * Delete from the Session after using. <BR>
 * Execute following processes in this class. <BR>
 * <BR>
 * 1.Search Process(<CODE>SessionItemTotalInquiryRet(Connection conn, StockControlParameter param)</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *   Execute when listbox screen is initial/default displayed.<BR>
 *   Invoke <CODE>find (StockControlParameter param)</CODE> method and search for the Stock info. <BR>
 * <BR>
 *  <DIR>
 *   <search conditions>Mandatory item (count)* <BR>
 *   <DIR>
 *     Consignor code* <BR>
 *     Case/Piece division<BR>
 *     Start item code<BR>
 *     End item code<BR>
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
 *      Consignor code<BR>
 * 		Consignor name<BR>
 * 		Item code<BR>
 * 		Item name<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/09/27</TD>
 * <TD>suresh kayamboo</TD>
 * <TD>New</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:12:15 $
 * @author  $Author: suresh $
 * 
 */
public class SessionItemTotalInquiryRet extends SessionRet
{
	//#CM6228
	// Class fields --------------------------------------------------

	//#CM6229
	// Class variables -----------------------------------------------
	//#CM6230
	/**
	 * Consignor name to be displayed
	 */
	private String wConsignorName = "";

	//#CM6231
	// Class method --------------------------------------------------
	//#CM6232
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/04 05:12:15 $");
	}

	//#CM6233
	// Constructors --------------------------------------------------
	//#CM6234
	/**
	 * Invoke <CODE>find(StockControlParameter param)</CODE> Method to execute search. <BR>
	 * Invoke the count of obtained data by <CODE>find (StockControlParameter param)</CODE> method.<BR>
	 *  Require to invoke the <CODE>getEntities</CODE> method to obtain the search result.<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param stParam      Parameter including <code>StockControlParameter</code>search result
	 * @throws Exception Announce this when any exception generates.
	 */
	public SessionItemTotalInquiryRet(Connection conn, StockControlParameter stParam) throws Exception
	{
		wConn = conn;
		find(stParam);
	}

	//#CM6235
	// Public methods ------------------------------------------------
	//#CM6236
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
	public StockControlStock[] getEntities()
	{
		StockControlStock[] workInfo = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				workInfo = (StockControlStock[]) wFinder.getEntities(wStartpoint, wEndpoint);
				workInfo[0].setConsignorName(wConsignorName);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return workInfo;
	}

	//#CM6237
	// Package methods -----------------------------------------------

	//#CM6238
	// Protected methods ---------------------------------------------

	//#CM6239
	// Private methods -----------------------------------------------
	//#CM6240
	/**
	 * This method obtains search conditions from the parameter and search through the Stock info.<BR>
	 * 
	 * @param stParam   Parameter to obtain search conditions
	 * @throws Exception Reports all the exceptions.
	 */
	private void find(StockControlParameter stParam) throws Exception
	{
		StockSearchKey sKey = new StockSearchKey();
		//#CM6241
		// Set search conditions
		//#CM6242
		// Consignor code
		if (!StringUtil.isBlank(stParam.getConsignorCode()))
		{
			sKey.setConsignorCode(stParam.getConsignorCode());
		}
		//#CM6243
		// Start item code
		if (!StringUtil.isBlank(stParam.getFromItemCode()))
		{
			sKey.setItemCode(stParam.getFromItemCode(), ">=");
		}
		//#CM6244
		// End item code
		if (!StringUtil.isBlank(stParam.getToItemCode()))
		{
			sKey.setItemCode(stParam.getToItemCode(), "<=");
		}
		//#CM6245
		// Case/Piece division
		if (!StringUtil.isBlank(stParam.getCasePieceFlag()))
		{
			//#CM6246
			// None
			if (stParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				sKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
				sKey.setCasePieceFlagCollect("");
				sKey.setCasePieceFlagGroup(4);

			}
			//#CM6247
			// Case
			else if (stParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_CASE))
			{
				sKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
				sKey.setCasePieceFlagCollect("");
				sKey.setCasePieceFlagGroup(4);
			}
			//#CM6248
			// Piece
			else if (stParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
			{
				sKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
				sKey.setCasePieceFlagCollect("");
				sKey.setCasePieceFlagGroup(4);
			}
			//#CM6249
			// All
			else if (stParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_ALL))
			{
				//#CM6250
				// do nothing 
			}
		}

		//#CM6251
		// Set the sorting sequence.
		//#CM6252
		// Consignor code
		sKey.setConsignorCodeOrder(1, true);
		//#CM6253
		// Item code
		sKey.setItemCodeOrder(2, true);

		//#CM6254
		// Generate Instance
		wFinder = new StockControlItemTotalInquiryFinder(wConn);
		//#CM6255
		// Cursor open
		wFinder.open();
		int count = ((StockControlItemTotalInquiryFinder)wFinder).search(sKey);
		wLength = count;
		wCurrent = 0;

		//#CM6256
		// Set the Consignor name to display on the List.
		getDisplayConsignorName(stParam);
	}

	//#CM6257
	/**
	 * Obtain the Consignor name to display in the List.<BR>
	 * Search for Work status with the latest added date/time using using the search conditions set for the parameter<BR>
	 * Obtain the Consignor name of the leading data.<BR>
	 * 
	 * @param	stParam	<CODE>StockControlParameter</CODE> class<BR>
	 * @throws Exception Reports all the exceptions.
	 */
	private void getDisplayConsignorName(StockControlParameter stParam) throws Exception
	{

		//#CM6258
		// Set search conditions
		if ((stParam != null) || !stParam.equals(""))
		{
			StockSearchKey consignorKey = new StockSearchKey();
			//#CM6259
			// Set the search conditions.
			//#CM6260
			// Consignor code
			if (!StringUtil.isBlank(stParam.getConsignorCode()))
			{
			    consignorKey.setConsignorCode(stParam.getConsignorCode());
			}
			//#CM6261
			// Start item code
			if (!StringUtil.isBlank(stParam.getFromItemCode()))
			{
			    consignorKey.setItemCode(stParam.getFromItemCode(), ">=");
			}
			//#CM6262
			// End item code
			if (!StringUtil.isBlank(stParam.getToItemCode()))
			{
			    consignorKey.setItemCode(stParam.getToItemCode(), "<=");
			}
			//#CM6263
			// Case/Piece division
			if (!StringUtil.isBlank(stParam.getCasePieceFlag()))
			{
				if (stParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
				{
				    consignorKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING); //指定なし
					//#CM6264
					// Division
				    consignorKey.setCasePieceFlagCollect("");
				}
				else if (stParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_CASE))
				{
				    consignorKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE); //ケース
					//#CM6265
					// Division
				    consignorKey.setCasePieceFlagCollect("");

				}
				else if (stParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
				{
				    consignorKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE); //ピース
					//#CM6266
					// Division
				    consignorKey.setCasePieceFlagCollect("");

				}
				else if (stParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_ALL))
				{
					//#CM6267
					//	do nothing
				    //#CM6268
				    // All Division

				}
			}
			consignorKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			consignorKey.setStockQty(1, ">=");
			//#CM6269
			// Consignor code
			consignorKey.setConsignorCodeCollect("");
			//#CM6270
			// Consignor name
			consignorKey.setConsignorNameCollect("");
			//#CM6271
			// Order sequence
			consignorKey.setLastUpdateDateOrder(1, false);
			
			//#CM6272
			// Generate Finder Instance
			StockFinder consignorFinder = new StockFinder(wConn);
			//#CM6273
			// Search Consignor name search
			consignorFinder.open();
			int nameCount = consignorFinder.search(consignorKey);
			if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
			{
				Stock[] stock = (Stock[]) consignorFinder.getEntities(0, 1);

				if (stock != null && stock.length != 0)
				{
					wConsignorName = stock[0].getConsignorName();
				}
			}

			consignorFinder.close();
		}

	}
}
//#CM6274
//end of class
