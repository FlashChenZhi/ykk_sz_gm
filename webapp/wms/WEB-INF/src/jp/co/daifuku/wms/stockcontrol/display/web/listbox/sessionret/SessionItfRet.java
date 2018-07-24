package jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret;
//#CM6312
/*
 * Created on 2004/09/15
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
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

//#CM6313
/**
 * Designer : suresh kayamboo<BR>
 * Maker 	: suresh kayamboo<BR> 
 * <BR>
 * This is the class to search and display Case ITF list from Stock info.<BR>
 * Store the instance to the Session when you use this class.
 * Delete from the Session after using. <BR>
 * Execute following processes in this class. <BR>
 * <BR>
 * 1.Search Process(<CODE>SessionItfRet(Connection conn, StockControlParameter param)</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *   Execute when listbox screen is initial/default displayed.<BR>
 *   Invoke <CODE>find (StockControlParameter param)</CODE> method and search for the Stock info. <BR>
 * <BR>
 *  <DIR>
 *   <search conditions>Mandatory item (count)* <BR>
 *   <DIR>
 *     Consignor code <BR>
 *     Item code<BR>
 *     Case/Piece division<BR>
 *     Start location<BR>
 *     End location<BR>
 *     Case ITF<BR>
 *     Stock status is in Center Inventory <BR>
 *     Stock qty:1 or more* <BR>
 *     Case ITF:Other than NULL* <BR>
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
 *      Case ITF<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/15</TD><TD>suresh kayamboo</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:12:14 $
 * @author  $Author: suresh $
 */
public class SessionItfRet extends SessionRet
{
	//#CM6314
	// Class fields --------------------------------------------------

	//#CM6315
	// Class variables -----------------------------------------------

	//#CM6316
	// Class method --------------------------------------------------
	//#CM6317
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/04 05:12:14 $");
	}

	//#CM6318
	// Constructors --------------------------------------------------
	//#CM6319
	/**
	 * Invoke <CODE>find(StockControlParameter param)</CODE> Method to execute search.<BR>
	 * Invoke the count of obtained data by <CODE>find (StockControlParameter param)</CODE> method.<BR>
	 *  Require to invoke the <CODE>getEntities</CODE> method to obtain the search result.<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter including <code>StockControlParameter</code>search result
	 * @throws Exception Announce this when any exception generates.
	 */
	public SessionItfRet(Connection conn, StockControlParameter stParam) throws Exception
	{
		wConn = conn;
		find(stParam);
	}

	//#CM6320
	// Public methods ------------------------------------------------
	//#CM6321
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
	 * @throws Exception Announce this when any exception generates.
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

	//#CM6322
	// Package methods -----------------------------------------------

	//#CM6323
	// Protected methods ---------------------------------------------

	//#CM6324
	// Private methods -----------------------------------------------
	//#CM6325
	/**
	 * This method obtains search conditions from the parameter and search through the Stock info.<BR>
	 * 
	 * @param stParam   Parameter to obtain search conditions
	 * @throws Exception Announce this when any exception generates.
	 */
	private void find(StockControlParameter stParam) throws Exception
	{
		StockSearchKey stKey = new StockSearchKey();

		//#CM6326
		// Consignor code
		if ((stParam.getConsignorCode() != null) && !stParam.getConsignorCode().equals(""))
		{
			stKey.setConsignorCode(stParam.getConsignorCode());
		}
		//#CM6327
		// Item code
		if ((stParam.getItemCode() != null) && !stParam.getItemCode().equals(""))
		{
			stKey.setItemCode(stParam.getItemCode());
		}
		//#CM6328
		// Case/Piece division
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
			//#CM6329
			//do nothing
		}
		//#CM6330
		// Starting Location
		if ((stParam.getFromLocationNo() != null) && !stParam.getFromLocationNo().equals(""))
		{
			stKey.setLocationNo(stParam.getFromLocationNo(), ">=");
		}
		//#CM6331
		// Ending Location
		if ((stParam.getToLocationNo() != null) && !stParam.getToLocationNo().equals(""))
		{
			stKey.setLocationNo(stParam.getToLocationNo(), "<=");
		}
		//#CM6332
		// ITF
		if ((stParam.getITF() != null) && !stParam.getITF().equals(""))
		{
			stKey.setItf(stParam.getITF());
		}

		//#CM6333
		// Stock status(Center Inventory)
		stKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM6334
		// stock > 0
		stKey.setStockQty(0, ">");

		if (stParam.getAreaTypeFlag().equals(StockControlParameter.AREA_TYPE_FLAG_NOASRS))
		{
			AreaOperator AreaOperator = new AreaOperator(wConn);
			
			String[] areaNo = null;
			int[] areaType = new int[2];
			areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
			areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
			
			//#CM6335
			// Obtain the Area other than ASRS and add it to the search conditions.
			//#CM6336
			// Search IS NULL if no corresponding area found.
			areaNo = AreaOperator.getAreaNo(areaType);
			stKey.setAreaNo(areaNo);
		}

		//#CM6337
		// Case ITF is Other than NULL
		stKey.setItf("", "IS NOT NULL");

		//#CM6338
		// Set the obtaining sequence.

		//#CM6339
		// ITF
		stKey.setItfCollect("");

		//#CM6340
		// Set group sequence

		//#CM6341
		// ITF
		stKey.setItfGroup(1);

		//#CM6342
		// Set sort sequence
		stKey.setItfOrder(1, true);

		wFinder = new StockFinder(wConn);
		wFinder.open();
		int count = wFinder.search(stKey);
		wLength = count;
		wCurrent = 0;

	}

	//#CM6343
	/**
	 * This class translates <CODE>Stock</CODE> entity into <CODE>StockControlParameter</CODE> parameter.<BR>
	 * @param ety Stock info
	 * @return Parameter[] <CODE>Parameter</CODE>parameter in which Stock info is set.
	 */
	private Parameter[] convertToStockParams(Entity[] ety)
	{
		StockControlParameter[] stParam = null;

		Stock[] stock = (Stock[]) ety;
		if ((stock != null) && (stock.length != 0))
		{
			stParam = new StockControlParameter[stock.length];

			for (int i = 0; i < stock.length; i++)
			{
				stParam[i] = new StockControlParameter();
				if ((stock[i].getItf() != null) && !stock[i].getItf().equals(""))
				{
					stParam[i].setITF(stock[i].getItf()); // ケースＩＴＦ
				}
			}
		}
		return stParam;

	}

}
//#CM6344
//end of class
