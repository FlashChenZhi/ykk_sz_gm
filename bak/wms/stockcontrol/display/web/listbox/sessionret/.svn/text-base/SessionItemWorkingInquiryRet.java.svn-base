// $Id: SessionItemWorkingInquiryRet.java,v 1.2 2006/10/04 05:12:14 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret;

//#CM6275
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM6276
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * This is the class to search and display Stock info.<BR>
 * Store the instance to the Session when you use this class.<BR>
 * Delete from the Session after using.<BR>
 * Execute following processes in this class.<BR>
 * <BR>
 * 1.Search Process(<CODE>SessionItemWorkingInquiryRet(Connection conn, StockControlParameter param)</CODE>Method)<BR>
 * <DIR>
 *   Execute when listbox screen is initial/default displayed.<BR>
 *   Invoke <CODE>find(StockControlParameter param)</CODE>Method and execute search of result View info.<BR>
 *   <BR>
 *   <parameter> *Mandatory Input <BR>
 *   <BR>
 *   <DIR>
 *   Consignor code* <BR>
 *   Start item code <BR>
 *   End item code <BR>
 *   Case/Piece division* <BR>
 *   <BR>
 *   Stock status is in Center Inventory <BR>
 *   stock qty is 1 or larger <BR>
 *   </DIR>
 *   <BR>
 *   <Search table> <BR>
 *   <DIR>
 *     RMSTOCK <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Display Process(<CODE>getEntities()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Obtain the data needed to display on the screen.<BR>
 *   Obtain the display info that was obtained in the search process.<BR>
 *   Set the search result back to <CODE>StockControlParameter</CODE> array.<BR>
 * <BR>
 *   <Items to be displayed>
 *   <DIR>
 *   Consignor code <BR>
 *   Consignor name <BR>
 *   Item code <BR>
 *   Item name <BR>
 *   Packing qty per case <BR>
 *   packed qty per bundle <BR>
 *   Stock Case Qty <BR>
 *   Stock piece qty <BR>
 *   allocation possible Case qty <BR>
 *   allocation possible Piece qty <BR>
 *   Case/Piece division <BR>
 *   Case/Piece division name <BR>
 *   Location <BR>
 *   Case ITF <BR>
 *   Bundle ITF <BR>
 *   Storage Date/Time <BR>
 *   Expiry Date <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/16</TD><TD>K.Toda</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:12:14 $
 * @author  $Author: suresh $
 */
public class SessionItemWorkingInquiryRet extends SessionRet
{
	//#CM6277
	// Class fields --------------------------------------------------

	//#CM6278
	// Class variables -----------------------------------------------
	//#CM6279
	/**
	 * For obtaining Consignor name
	 */
	private String wConsignorName = "";

	//#CM6280
	/**
	 * Area No. of AS/RS(For display hyphenation)
	 */
	private String[] wAsAreaNo;


	//#CM6281
	// Class method --------------------------------------------------
	//#CM6282
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/04 05:12:14 $");
	}

	//#CM6283
	// Constructors --------------------------------------------------
	//#CM6284
	/**
	 * Invoke <CODE>find(StockControlParameter param)</CODE> Method to execute search.<BR>
	 * Set the count of obtained data by <CODE>find (ShippingParameter param)</CODE> method.<BR>
	 *  Require to invoke the <CODE>getEntities</CODE> method to obtain the search result.<BR>
	 * <BR>
	 * <search conditions>*Mandatory item (count)
     *   <DIR>
     *   Consignor code* <BR>
     *   Start item code <BR>
     *   End item code <BR>
     *   Case/Piece division* <BR>
     *   <BR>
     *   Stock status is in Center Inventory <BR>
     *   stock qty is 1 or larger <BR>
     *   </DIR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter including <code>StockControlParameter</code>search result
	 * @throws Exception Announce this when any exception generates.
	 */
	public SessionItemWorkingInquiryRet(Connection conn, StockControlParameter param) throws Exception
	{
		this.wConn = conn;

		//#CM6285
		// Search
		find(param);
	}

	//#CM6286
	// Public methods ------------------------------------------------
	//#CM6287
	/**
	 * Return the designated number of resurch results of the stock info<BR>
	 * Execute This method executes the following processes.<BR>
	 * <BR>
	 * <DIR>
	 *   1.Calculate to specify the count of display data to be obtained.<BR>
	 *   2.Obtain Work Status from result set<BR>
	 *   3.Obtain the display data from the Work status and set it for <CODE>StockControlParameter</CODE>.<BR>
	 *   4.Return information to be displayed.<BR>
	 * </DIR>
	 * <BR>
	 * <search result><BR>
     *   <DIR>
     *   Consignor code <BR>
     *   Consignor name <BR>
     *   Item code <BR>
     *   Item name <BR>
     *   Packing qty per case <BR>
     *   packed qty per bundle <BR>
     *   Stock Case Qty <BR>
     *   Stock piece qty <BR>
     *   allocation possible Case qty <BR>
     *   allocation possible Piece qty <BR>
     *   Case/Piece division <BR>
     *   Case/Piece division name <BR>
     *   Location <BR>
     *   Case ITF <BR>
     *   Bundle ITF <BR>
     *   Storage Date/Time <BR>
     *   Expiry Date <BR>
     *   </DIR>
	 * @return <CODE>StockControlParameter</CODE> class including display info.
	 */
	public StockControlParameter[] getEntities()
	{
		Stock[] stock = null;
		StockControlParameter[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				//#CM6288
				// Obtain the search result.
				stock = (Stock[]) ((StockFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				//#CM6289
				// Translate the obtained value into the return data and set it for StockControlParameter.
				resultArray = getDispData(stock);
			}
			catch (Exception e)
			{
				//#CM6290
				// 6006002=Database error occurred.{0}{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;

		return resultArray;
	}

	//#CM6291
	// Package methods -----------------------------------------------

	//#CM6292
	// Protected methods ---------------------------------------------

	//#CM6293
	// Private methods -----------------------------------------------
	//#CM6294
	/**
	 * This method obtains search conditions from the parameter and search through the result View info.<BR>
	 * 
	 * @param param   Parameter to obtain search conditions
	 * @throws Exception Announce this when any exception generates.
	 */
	private void find(StockControlParameter param) throws Exception
	{
		try
		{
			//#CM6295
			// Obtain the AS/RS area No. of the Area for display hyphenation. 
			AreaOperator ao = new AreaOperator(wConn);
			int[] asSystemDiscKey = {Area.SYSTEM_DISC_KEY_ASRS};
			wAsAreaNo = ao.getAreaNo(asSystemDiscKey);
		}
		catch (ScheduleException se)
		{
			//#CM6296
			// Do not regard as an error when no area is available for AS/RS.
		}
		
		StockSearchKey rkey = new StockSearchKey();
		StockSearchKey consignorkey = new StockSearchKey();
		//#CM6297
		// Set the search conditions.
		//#CM6298
		// Consignor code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			rkey.setConsignorCode(param.getConsignorCode());
			consignorkey.setConsignorCode(param.getConsignorCode());
		}
		//#CM6299
		// Start item code
		if (!StringUtil.isBlank(param.getFromItemCode()))
		{
			rkey.setItemCode(param.getFromItemCode(), ">=");
			consignorkey.setItemCode(param.getFromItemCode(), ">=");
		}
		//#CM6300
		// End item code
		if (!StringUtil.isBlank(param.getToItemCode()))
		{
			rkey.setItemCode(param.getToItemCode(), "<=");
			consignorkey.setItemCode(param.getToItemCode(), "<=");
		}
		//#CM6301
		// Case/Piece division (Set nothing on the search key when search condition is all)
		if (!StringUtil.isBlank(param.getCasePieceFlag()))
		{
			if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				rkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_NOTHING);
				consignorkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_NOTHING);
			}
			else if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_CASE))
			{
				rkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_CASE);
				consignorkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_CASE);
			}
			else if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
			{
				rkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_PIECE);
				consignorkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_PIECE);
			}
		}
		
		//#CM6302
		// Center Inventory
		rkey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		rkey.setStockQty(1, ">=");
		consignorkey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		consignorkey.setStockQty(1, ">=");
		
		rkey.setItemCodeOrder(1, true);
		rkey.setCasePieceFlagOrder(2, true);
		rkey.setLocationNoOrder(3, true);
		rkey.setUseByDateOrder(4, true);

		wFinder = new StockFinder(wConn);
		//#CM6303
		// Cursor open
		wFinder.open();
		//#CM6304
		// Execute the search.
		int count = ((StockFinder) wFinder).search(rkey);
		//#CM6305
		// Initialize
		wLength = count;
		wCurrent = 0;

		//#CM6306
		// Obtain the Consignor Name.
		consignorkey.setConsignorNameCollect("");
		consignorkey.setLastUpdateDateOrder(1, false);

		StockFinder consignorFinder = new StockFinder(wConn);
		consignorFinder.open();
		int nameCount = consignorFinder.search(consignorkey);
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

	//#CM6307
	/**
	 * This class sets the Stock info for <CODE>StockControlParameter</CODE>.<BR>
	 * 
	 * @param result Stock info
	 * @return StockControlParameter[] <CODE>StockControlParameter</CODE> class in which Stock info is set.
	 * @throws Exception Announce this when any exception generates.
	 */
	private StockControlParameter[] getDispData(Stock[] result) throws Exception
	{
		StockControlParameter[] param = new StockControlParameter[result.length];
		AreaOperator ao = new AreaOperator(wConn);
		for (int i = 0; i < result.length; i++)
		{
			//#CM6308
			// Allocatable Qty
			int possibleQty = result[i].getAllocationQty();
			
			//#CM6309
			// Set the Stock info for StockControlParameter.
			param[i] = new StockControlParameter();
			param[i].setConsignorCode(result[i].getConsignorCode());
			param[i].setConsignorName(wConsignorName);
			param[i].setItemCode(result[i].getItemCode());
			param[i].setItemName(result[i].getItemName1());
			param[i].setEnteringQty(result[i].getEnteringQty());
			param[i].setBundleEnteringQty(result[i].getBundleEnteringQty());
			param[i].setStockCaseQty(DisplayUtil.getCaseQty(result[i].getStockQty(), result[i].getEnteringQty(), result[i].getCasePieceFlag()));
			param[i].setStockPieceQty(DisplayUtil.getPieceQty(result[i].getStockQty(), result[i].getEnteringQty(), result[i].getCasePieceFlag()));
			param[i].setAllocateCaseQty(DisplayUtil.getCaseQty(possibleQty, result[i].getEnteringQty(), result[i].getCasePieceFlag()));
			param[i].setAllocatePieceQty(DisplayUtil.getPieceQty(possibleQty, result[i].getEnteringQty(), result[i].getCasePieceFlag()));
			param[i].setCasePieceFlag(result[i].getCasePieceFlag());
			param[i].setCasePieceFlagName(DisplayUtil.getPieceCaseValue(result[i].getCasePieceFlag()));
			param[i].setLocationNo(result[i].getLocationNo());
			param[i].setITF(result[i].getItf());
			param[i].setBundleITF(result[i].getBundleItf());
			param[i].setStorageDate(result[i].getInstockDate());
			param[i].setUseByDate(result[i].getUseByDate());
			param[i].setAreaNo(result[i].getAreaNo());
			//#CM6310
			// Set the instance variable Area No. for screen display.
			param[i].setAreaNoArray(wAsAreaNo);
			param[i].setAreaName(ao.getAreaName(result[i].getAreaNo()));
		}

		return param;
	}

}
//#CM6311
//end of class
