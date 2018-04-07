// $Id: SessionListStockMaintenanceRet.java,v 1.2 2006/10/04 05:12:14 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.display.web.listbox.sessionret;

//#CM6345
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
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
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM6346
/**
 * Designer : C.Kaminishizono<BR>
 * Maker 	: C.Kaminishizono<BR> 
 * <BR>
 * This is the class to search the data for stock list listbox ( stock)<BR>
 * Receive the search conditions as a parameter to search stock list.<BR>
 * Store the instance to the Session when you use this class. <BR>
 * Delete from the Session after using. <BR>
 * Execute following processes in this class. <BR>
 * <BR>
 * 1.Search Process(<CODE>SessionListStockMaintenanceRet(Connection conn,	StockControlParameter param)</CODE>Method) <BR>
 * <DIR>
 *   Execute when listbox screen is initial/default displayed.<BR>
 *   Invoke <CODE>find (StockControlParameter param)</CODE> method and search for the target info. <BR>
 * <BR>
 *   <search conditions> Mandatory item (count)* <BR>
 *   <DIR>
 *     -Consignor code* <BR>
 *     -Item code <BR>
 *     -Case/Piece division <BR>
 *     -LocationNo <BR>
 *     -Stock status is in Center Inventory *<BR>
 *     -stock qty is 1 or larger*<BR>
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
 *   Set the search result back to <CODE>StockControlParameter</CODE> array.<BR>
 *   <Items to be displayed><BR>
 *   <DIR>
 *     -Consignor code <BR>
 *     -Consignor name <BR>
 *     -Item code <BR>
 *     -Item name <BR>
 *     -Case/Piece division <BR>
 *     -Case/Piece division name <BR>
 *     -Packing qty per case <BR>
 *     -packed qty per bundle <BR>
 *     -Case ITF <BR>
 *     -Bundle ITF <BR>
 *     -LocationNo <BR>
 *     -Storage Date/Time <BR>
 *     -Expiry Date <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/14</TD><TD>C.Kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/10/04 05:12:14 $
 * @author  $Author: suresh $
 */
public class SessionListStockMaintenanceRet extends SessionRet
{
	//#CM6347
	// Class fields --------------------------------------------------

	//#CM6348
	// Class variables -----------------------------------------------
	//#CM6349
	/**
	 *  Maintain Consignor name
	 */
	protected String wConsignorName = null;

	//#CM6350
	// Class method --------------------------------------------------
	//#CM6351
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/04 05:12:14 $");
	}

	//#CM6352
	// Constructors --------------------------------------------------
	//#CM6353
	/**
	 * Set the search key and print the SQL statement.<BR>
	 * <DIR>
	 *   <search conditions> Mandatory item (count)* <BR>
	 *   <DIR>
	 *     -Consignor code* <BR>
	 *     -Item code <BR>
	 *     -Case/Piece division <BR>
	 *     -LocationNo <BR>
	 *     -Stock status is in Center Inventory  <BR>
	 *     -stock qty is 1 or larger <BR>
	 *   </DIR>
	 *   <Returned data> <BR>
	 *   <DIR>
	 *    -Count of the targets<BR>
	 *   </DIR>
	 * </DIR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter including <code>StockControlParameter</code>search result
	 * @throws Exception Announce this when any exception generates.
	 */
	public SessionListStockMaintenanceRet(Connection conn, StockControlParameter param) throws Exception
	{
		this.wConn = conn;

		//#CM6354
		// Search
		find(param);
	}

	//#CM6355
	// Public methods ------------------------------------------------
	//#CM6356
	/**
	 * Return the search result of <CODE> Stock info</CODE>. <BR>
	 * <DIR>
	 * <search result> <BR>
	 *   <DIR>
	 *     -Consignor code <BR>
	 *     -Consignor name <BR>
	 *     -Item code <BR>
	 *     -Item name <BR>
	 *     -Case/Piece division <BR>
	 *     -Case/Piece division name <BR>
	 *     -Packing qty per case <BR>
	 *     -packed qty per bundle <BR>
	 *     -Case ITF <BR>
	 *     -Bundle ITF <BR>
	 *     -LocationNo <BR>
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
		Vector vec = new Vector() ;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				wStock = (Stock[]) ((StockFinder)wFinder).getEntities(wStartpoint, wEndpoint);

				for (int lc=0; lc<wStock.length; lc++)
				{
					StockControlParameter vStock = new StockControlParameter() ;

					//#CM6357
					// Consignor code
					vStock.setConsignorCode(wStock[lc].getConsignorCode());
					//#CM6358
					// Consignor name
					vStock.setConsignorName(wConsignorName);
					//#CM6359
					// Item code
					vStock.setItemCode(wStock[lc].getItemCode());
					//#CM6360
					// Item name
					vStock.setItemName(wStock[lc].getItemName1());
					//#CM6361
					// Case/Piece division
					vStock.setCasePieceFlag(wStock[lc].getCasePieceFlag());
					//#CM6362
					// Case/Piece division name
					vStock.setCasePieceFlagName(DisplayUtil.getPieceCaseValue(wStock[lc].getCasePieceFlag()));
					//#CM6363
					// Packing qty per case
					vStock.setEnteringQty(wStock[lc].getEnteringQty());
					//#CM6364
					// packed qty per bundle
					vStock.setBundleEnteringQty(wStock[lc].getBundleEnteringQty());
					//#CM6365
					// Case ITF
					vStock.setITF(wStock[lc].getItf());
					//#CM6366
					// Bundle ITF
					vStock.setBundleITF(wStock[lc].getBundleItf());
					//#CM6367
					// LocationNo
					vStock.setLocationNo(wStock[lc].getLocationNo());
					//#CM6368
					// Storage Date/Time
					vStock.setStorageDate(wStock[lc].getInstockDate());
					//#CM6369
					// Expiry Date
					vStock.setUseByDate(wStock[lc].getUseByDate());

					vec.addElement(vStock);
				}
				resultArray = new StockControlParameter[vec.size()];
				vec.copyInto(resultArray);
							
			}
			catch (Exception e)
			{
				//#CM6370
				// Record the error in the log file.
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM6371
	// Package methods -----------------------------------------------

	//#CM6372
	// Protected methods ---------------------------------------------

	//#CM6373
	// Private methods -----------------------------------------------
	//#CM6374
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain the <code>StockControlFinder</code> for searching as an instance variable. <BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param param      Parameter including <code>StockControlParameter</code>search result
	 * @throws Exception Announce this when any exception generates.
	 */
	private void find(StockControlParameter param) throws Exception
	{
		int count = 0;

		StockSearchKey wkey = new StockSearchKey();
		//#CM6375
		// Execute Search
		//#CM6376
		// Consignor code
		if (!StringUtil.isBlank(param.getConsignorCode()))
			wkey.setConsignorCode(param.getConsignorCode());
		//#CM6377
		// Item code
		if (!StringUtil.isBlank(param.getItemCode()))
			wkey.setItemCode(param.getItemCode());
		//#CM6378
		// Case/Piece division
		if (!StringUtil.isBlank(param.getCasePieceFlag()))
			wkey.setCasePieceFlag(param.getCasePieceFlag());
		//#CM6379
		// LocationNo
		if (!StringUtil.isBlank(param.getLocationNo()))
			wkey.setLocationNo(param.getLocationNo());

		//#CM6380
		// stock status is Center stock only
		wkey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		
		//#CM6381
		// stock qty is 1 or larger
		wkey.setStockQty(0, ">");

		if (StockControlParameter.AREA_TYPE_FLAG_NOASRS.equals(param.getAreaTypeFlag()))
		{
			AreaOperator AreaOperator = new AreaOperator(wConn);
			
			String[] areaNo = null;
			int[] areaType = new int[2];
			areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
			areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
			
			//#CM6382
			// Obtain the Area other than ASRS and add it to the search conditions.
			//#CM6383
			// Search IS NULL if no corresponding area found.
			areaNo = AreaOperator.getAreaNo(areaType);
			wkey.setAreaNo(areaNo);
		}

		wkey.setItemCodeOrder(1, true);
		
		wkey.setCasePieceFlagOrder(2, true);
		wkey.setLocationNoOrder(3, true);
		wkey.setItfOrder(4,true);
		wkey.setBundleItfOrder(5, true);
		wkey.setInstockDateOrder(6, true);
		wkey.setUseByDateOrder(7, true);
		

		wFinder = new StockFinder(wConn);
		//#CM6384
		// Cursor open
		wFinder.open();
		count = ((StockFinder)wFinder).search(wkey);
		//#CM6385
		// Initialize
		wLength = count;
		wCurrent = 0;

		//#CM6386
		// Obtain the Consignor name for header output.
		StockSearchKey namekey = new StockSearchKey();
		StockReportFinder consignorFinder = new StockReportFinder(wConn);
		namekey.setConsignorCode(param.getConsignorCode());
		namekey.setLastUpdateDateOrder(1, false);
		
		consignorFinder.open();
		int nameCountCsg = consignorFinder.search(namekey);
		if (nameCountCsg > 0 && nameCountCsg <= DatabaseFinder.MAXDISP)
		{
			Stock view[] = (Stock[]) consignorFinder.getEntities(1);

			if (view != null && view.length != 0)
			{
				wConsignorName = view[0].getConsignorName();
			}
		}
		consignorFinder.close();
		
	}
}
//#CM6387
//end of class
