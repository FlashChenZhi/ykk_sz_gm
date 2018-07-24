// $Id: SessionLocationWorkingInquiryRet.java,v 1.2 2006/10/04 05:12:13 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret;

//#CM6422
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.RmiMsgLogClient;
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

//#CM6423
/**
 * Designer : C.Kaminishizono<BR>
 * Maker 	: C.Kaminishizono<BR> 
 * <BR>
 * This is the class to search the data for stock list listbox per location.<BR>
 * Receive and search search conditions as a parameter to search the stock list by location.<BR>
 * Store the instance to the Session when you use this class. <BR>
 * Delete from the Session after using. <BR>
 * Execute following processes in this class. <BR>
 * <BR>
 * 1.Search Process(<CODE>SessionLocationWorkingInquiryRet(Connection conn,StockControlParameter param)</CODE>Method) <BR>
 * <DIR>
 *   Execute when listbox screen is initial/default displayed. <BR>
 *   Invoke <CODE>find (StockControlParameter param)</CODE> method and search for the target info. <BR>
 * <BR>
 *   <search conditions> Mandatory item (count)* <BR>
 *   <DIR>
 *     -Consignor code* <BR>
 *     -Start location <BR>
 *     -End location <BR>
 *     -Item code <BR>
 *     -Case/Piece division <BR>
 *     -Stock status is in Center Inventory *<BR>
 *     -stock qty is 1 or larger*<BR>
 *     -Stock other than AS/RS <BR>
 *   </DIR>
 *   <Search table> <BR>
 *   <DIR>
 *     -DMSTOCK <BR>
 *   </DIR>
 *   <Returned data> <BR>
 *   <DIR>
 *    -Count of the targets<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 
 * 2.Display Process(<CODE>getEntities()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Obtain the data needed to display on the screen.<BR>
 *   1.Obtain the display info that was obtained in the search process.<BR>
 *   Set the search result back to the inventory information array.<BR>
 *   <Items to be displayed><BR>
 *   <DIR>
 *     -Consignor code <BR>
 *     -Consignor name <BR>
 *     -LocationNo <BR>
 *     -Item code <BR>
 *     -Item name <BR>
 *     -Packing qty per case <BR>
 *     -packed qty per bundle <BR>
 *     -Stock Case Qty <BR>
 *     -Stock piece qty <BR>
 *     -allocation possible Case qty <BR>
 *     -allocation possible Piece qty <BR>
 *     -Case/Piece division <BR>
 *     -Case/Piece division name <BR>
 *     -Case ITF <BR>
 *     -Bundle ITF <BR>
 *     -Storage Date/Time <BR>
 *     -Expiry Date<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/17</TD><TD>C.Kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:12:13 $
 * @author  $Author: suresh $
 */
public class SessionLocationWorkingInquiryRet extends SessionRet
{
	//#CM6424
	// Class fields --------------------------------------------------

	//#CM6425
	// Class variables -----------------------------------------------
	//#CM6426
	/**
	 * For obtaining Consignor name
	 */
	private String wConsignorName = "";

	//#CM6427
	// Class method --------------------------------------------------
	//#CM6428
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/04 05:12:13 $");
	}

	//#CM6429
	// Constructors --------------------------------------------------
	//#CM6430
	/**
	 * Set the search key and print the SQL statement.<BR>
	 * <DIR>
	 *   <search conditions> Mandatory item (count)* <BR>
	 *   <DIR>
	 *     -Consignor code* <BR>
	 *     -Start location <BR>
	 *     -End location <BR>
	 *     -Item code <BR>
	 *     -Case/Piece division <BR>
	 *     -Stock status is in Center Inventory *<BR>
	 *     -stock qty is 1 or larger*<BR>
	 *     -Stock other than AS/RS*<BR>
	 *   </DIR>
	 *   <Returned data> <BR>
	 *   <DIR>
	 *    -Count of the targets<BR>
	 *   </DIR>
	 * </DIR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter including <code>ShippingParameter</code>search result
	 * @throws Exception Announce this when any exception generates.
	 */
	public SessionLocationWorkingInquiryRet(Connection conn, StockControlParameter param) throws Exception
	{
		this.wConn = conn;

		//#CM6431
		// Search
		find(param);
	}

	//#CM6432
	// Public methods ------------------------------------------------
	//#CM6433
	/**
	 * Return the search result of <CODE> Stock info</CODE>. <BR>
	 * <DIR>
	 * <search result> <BR>
	 *   <DIR>
	 *     -Consignor code <BR>
	 *     -Consignor name <BR>
	 *     -LocationNo <BR>
	 *     -Item code <BR>
	 *     -Item name <BR>
	 *     -Packing qty per case <BR>
	 *     -packed qty per bundle <BR>
	 *     -Stock Case Qty <BR>
	 *     -Stock piece qty <BR>
	 *     -allocation possible Case qty <BR>
	 *     -allocation possible Piece qty <BR>
	 *     -Case/Piece division <BR>
	 *     -Case/Piece division name<BR>
	 *     -Case ITF <BR>
	 *     -Bundle ITF <BR>
	 *     -Storage Date/Time <BR>
	 *     -Expiry Date <BR>
	 *   </DIR>
	 * </DIR>
	 * @return search result of Stock info
	 */
	public StockControlParameter[] getEntities()
	{
		StockControlParameter[] resultArray = null;
		Stock[] wStock = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				wStock = (Stock[]) ((StockFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				//#CM6434
				// Translate the obtained value into the return data and set it for StockControlParameter.
				resultArray = getDispData(wStock);

			}
			catch (Exception e)
			{
				//#CM6435
				//Record the error in the log file.
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM6436
	// Package methods -----------------------------------------------

	//#CM6437
	// Protected methods ---------------------------------------------

	//#CM6438
	// Private methods -----------------------------------------------
	//#CM6439
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain the <code>StockFinder</code> for searching as an instance variable. <BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param param      Parameter including <code>StockControlParameter</code>search result
	 * @throws Exception Announce this when any exception generates.
	 */
	private void find(StockControlParameter param) throws Exception
	{
		int count = 0;

		StockSearchKey wkey = new StockSearchKey();
		StockSearchKey consignorkey = new StockSearchKey();
		//#CM6440
		// Execute Search
		//#CM6441
		// Consignor code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			wkey.setConsignorCode(param.getConsignorCode());
			consignorkey.setConsignorCode(param.getConsignorCode());
		}
		//#CM6442
		// Start Location No.
		if (!StringUtil.isBlank(param.getFromLocationNo()))
		{
			wkey.setLocationNo(param.getFromLocationNo(), ">=");
			consignorkey.setLocationNo(param.getFromLocationNo(), ">=");
		}
		//#CM6443
		// Closed Location No.
		if (!StringUtil.isBlank(param.getToLocationNo()))
		{
			wkey.setLocationNo(param.getToLocationNo(), "<=");
			consignorkey.setLocationNo(param.getToLocationNo(), "<=");
		}
		//#CM6444
		// Item code
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			wkey.setItemCode(param.getItemCode());
			consignorkey.setItemCode(param.getItemCode());
		}
		//#CM6445
		// Case/Piece division (Set nothing on the search key when search condition is all)
		if (!StringUtil.isBlank(param.getCasePieceFlag()))
		{
			if (param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				wkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_NOTHING);
				consignorkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_NOTHING);
			}
			else if (param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_CASE))
			{
				wkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_CASE);
				consignorkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_CASE);
			}
			else if (param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
			{
				wkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_PIECE);
				consignorkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_PIECE);
			}
		}

		//#CM6446
		// Stock status is Center stock only
		wkey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		consignorkey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM6447
		// stock qty is 1 or larger
		wkey.setStockQty(0, ">");
		consignorkey.setStockQty(0, ">");
		AreaOperator AreaOperator = new AreaOperator(wConn);	
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
		//#CM6448
		// Obtain the Area other than ASRS and add it to the search conditions.
		//#CM6449
		// Search IS NULL if no corresponding area found.
		areaNo = AreaOperator.getAreaNo(areaType);
		wkey.setAreaNo(areaNo);
		consignorkey.setAreaNo(areaNo);

		wkey.setLocationNoOrder(1, true);
		wkey.setItemCodeOrder(2, true);
		wkey.setCasePieceFlagOrder(3, true);
		wkey.setUseByDateOrder(4,true);
		wFinder = new StockFinder(wConn);
		//#CM6450
		// Cursor open
		wFinder.open();
		count = ((StockFinder) wFinder).search(wkey);
		//#CM6451
		// Initialize
		wLength = count;
		wCurrent = 0;

		//#CM6452
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

	//#CM6453
	/**
	 * This class sets the Stock info for <CODE>StockControlParameter</CODE>.<BR>
	 * 
	 * @param result Stock info
	 * @return StockControlParameter[] <CODE>StockControlParameter</CODE> class in which Stock info is set.
	 */
	private StockControlParameter[] getDispData(Stock[] result)
	{
		Vector vec = new Vector();
		StockControlParameter[] resultArray = null;

		for (int lc = 0; lc < result.length; lc++)
		{
			StockControlParameter param = new StockControlParameter();
			
			//#CM6454
			// Maintain the Case/Piece division.
			String casepieceFlag = result[lc].getCasePieceFlag(); 
			
			//#CM6455
			// Consignor code
			param.setConsignorCode(result[lc].getConsignorCode());
			//#CM6456
			// Consignor name
			param.setConsignorName(wConsignorName);
			//#CM6457
			// LocationNo
			param.setLocationNo(result[lc].getLocationNo());
			//#CM6458
			// Item code
			param.setItemCode(result[lc].getItemCode());
			//#CM6459
			// Item name
			param.setItemName(result[lc].getItemName1());
			//#CM6460
			// Packing qty per case
			param.setEnteringQty(result[lc].getEnteringQty());
			//#CM6461
			// packed qty per bundle
			param.setBundleEnteringQty(result[lc].getBundleEnteringQty());

			//#CM6462
			// Stock Case Qty
			param.setStockCaseQty(DisplayUtil.getCaseQty(result[lc].getStockQty(), result[lc].getEnteringQty(), casepieceFlag));
			//#CM6463
			// Stock piece qty
			param.setStockPieceQty(DisplayUtil.getPieceQty(result[lc].getStockQty(), result[lc].getEnteringQty(), casepieceFlag));
			//#CM6464
			// allocation possible Case qty
			param.setAllocateCaseQty(DisplayUtil.getCaseQty((result[lc].getAllocationQty()), result[lc].getEnteringQty(), casepieceFlag));
			//#CM6465
			// allocation possible Piece qty
			param.setAllocatePieceQty(DisplayUtil.getPieceQty((result[lc].getAllocationQty()), result[lc].getEnteringQty(), casepieceFlag));

			//#CM6466
			// Case/Piece division name
			param.setCasePieceFlagName(DisplayUtil.getPieceCaseValue(casepieceFlag));
			//#CM6467
			// Case ITF
			param.setITF(result[lc].getItf());
			//#CM6468
			// Bundle ITF
			param.setBundleITF(result[lc].getBundleItf());
			//#CM6469
			// Storage Date/Time
			param.setStorageDate(result[lc].getInstockDate());
			//#CM6470
			// Expiry Date
			param.setUseByDate(result[lc].getUseByDate());

			vec.addElement(param);
		}
		resultArray = new StockControlParameter[vec.size()];
		vec.copyInto(resultArray);

		return resultArray;

	}

}
//#CM6471
//end of class
