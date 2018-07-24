package jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret;
//#CM6107
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
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM6108
/**
 * Designer : Muneendra<BR>
 * Maker 	: Muneendra<BR> 
 * <BR>
 * This is the class to search and display the bundle ITF list from Stock info.<BR>
 * Store the instance to the Session when you use this class.
 * Delete from the Session after using. <BR>
 * Execute following processes in this class. <BR>
 * <BR>
 * 1.Search Process(<CODE>SessionBundleItfRet(Connection conn, StockControlParameter param)</CODE>Method) <BR>
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
 *     Bundle ITF<BR>
 *     Stock status is in Center Inventory <BR>
 *     Stock qty:1 or more* <BR>
 *     Bundle ITF:Other than NULL* <BR>
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
 *   <Show subject on prior one page>
 *   <DIR>
 *    Bundle ITF<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/16</TD><TD>Muneendra</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:12:16 $
 * @author  $Author: suresh $
 */
public class SessionBundleItfRet extends SessionRet
{
	//#CM6109
	// Class fields --------------------------------------------------

	//#CM6110
	// Class variables -----------------------------------------------

	//#CM6111
	// Class method --------------------------------------------------
	//#CM6112
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/04 05:12:16 $");
	}

	//#CM6113
	// Constructors --------------------------------------------------
	//#CM6114
	/**
	 * Invoke <CODE>find(StockControlParameter param)</CODE> Method to execute search.<BR>
	 * Invoke the count of obtained data by <CODE>find (StockControlParameter param)</CODE> method.<BR>
	 *  Require to invoke the <CODE>getEntities</CODE> method to obtain the search result.<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter including <code>StockControlParameter</code>search result
	 * @throws Exception Announce this when any exception generates.
	 */
	public SessionBundleItfRet(Connection conn, StockControlParameter stParam) throws Exception
	{
		wConn = conn;
		find(stParam);
	}

	//#CM6115
	// Public methods ------------------------------------------------
	//#CM6116
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

	//#CM6117
	// Package methods -----------------------------------------------

	//#CM6118
	// Protected methods ---------------------------------------------

	//#CM6119
	// Private methods -----------------------------------------------
	//#CM6120
	/**
	 * This method obtains search conditions from the parameter and search through the Stock info.<BR>
	 * 
	 * @param stParam   Parameter to obtain search conditions
	 * @throws Exception Reports all the exceptions.
	 */
	private void find(StockControlParameter stParam) throws Exception
	{
		StockSearchKey stKey = new StockSearchKey();

		if (!StringUtil.isBlank(stParam.getConsignorCode()))
		{
			stKey.setConsignorCode(stParam.getConsignorCode());
		}

		if (!StringUtil.isBlank(stParam.getItemCode()))
		{
			stKey.setItemCode(stParam.getItemCode());
		}

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
			//#CM6121
			// Disable
		}

		if (!StringUtil.isBlank(stParam.getFromLocationNo()))
		{
			stKey.setLocationNo(stParam.getFromLocationNo(), ">=");
		}

		if (!StringUtil.isBlank(stParam.getToLocationNo()))
		{
			stKey.setLocationNo(stParam.getToLocationNo(), "<=");
		}

		if (!StringUtil.isBlank(stParam.getITF()))
		{
			stKey.setItf(stParam.getITF());
		}

		if (!StringUtil.isBlank(stParam.getBundleITF()))
		{
			stKey.setBundleItf(stParam.getBundleITF());
		}

		//#CM6122
		// Stock status(Center Inventory)
		stKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM6123
		// Stock qty is 1 or more
		stKey.setStockQty(0, ">");

		if (stParam.getAreaTypeFlag().equals(StockControlParameter.AREA_TYPE_FLAG_NOASRS))
		{
			AreaOperator AreaOperator = new AreaOperator(wConn);
			
			String[] areaNo = null;
			int[] areaType = new int[2];
			areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
			areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
			
			//#CM6124
			// Obtain the Area other than ASRS and add it to the search conditions.
			//#CM6125
			// Search IS NULL if no corresponding area found.
			areaNo = AreaOperator.getAreaNo(areaType);
			stKey.setAreaNo(areaNo);
		}

		//#CM6126
		// Bundle ITF is other than NULL
		stKey.setBundleItf("", "IS NOT NULL");

		//#CM6127
		// Set the obtaining sequence.
		stKey.setBundleItfCollect("");

		//#CM6128
		// Set sort sequence			
		stKey.setBundleItfOrder(1, true);

		//#CM6129
		// Set group sequence
		stKey.setBundleItfGroup(1);

		wFinder = new StockFinder(wConn);
		//#CM6130
		// Cursor open
		wFinder.open();

		int count = wFinder.search(stKey);
		wLength = count;
		wCurrent = 0;

	}

	//#CM6131
	/**
	 * This Method changes <CODE>Stock</CODE>Entity to <CODE>StockControlParameter</CODE>parameter.<BR>
	 * @param stock Stock info
	 * @return StockControlParameter[] <CODE>StockControlParameter</CODE>parameter in which Stock info is set
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
				if ((stock[i].getBundleItf() != null) && !stock[i].getBundleItf().equals(""))
				{
					stParam[i].setBundleITF(stock[i].getBundleItf()); // Bundle ITF
				}
			}
		}
		return stParam;
	}

}
//#CM6132
//end of class
