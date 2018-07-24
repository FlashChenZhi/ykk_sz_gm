// $Id: SessionStockUseByDateRet.java,v 1.2 2006/12/07 08:57:30 suresh Exp $
package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;

//#CM570096
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
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570097
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * The class to do data retrieval for Expiry Date list box Retrieval processing.<BR>
 * Receive Search condition with Parameter, and do Retrieval of the Expiry Date list. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStockUseByDateRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   Do the Retrieval of inventory information by calling <CODE>find</CODE> Method. <BR>
 * <BR>
 *   <Search condition> MandatoryItem*<BR>
 *   <DIR>
 *      , Consignor Code*<BR>
 *      , Item Code<BR>
 *      , Location<BR>
 *      , Expiry Date<BR>
 *      , Status flag : Central stock*<BR>
 *      , Stock qty : More than 1*<BR>
 *   </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *      , DMSTOCK <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Display processing(<CODE>getEntities()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Acquire the data to display it on the screen. <BR>
 *   1.Acquire display information from the retrieval result of obtaining in Retrieval processing. <BR>
 * <BR>
 *   <Display Item>
 *   <DIR>
 *      , Expiry Date<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2008/10/18</TD><TD>Y.Okamura</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:30 $
 * @author  $Author: suresh $
 */
public class SessionStockUseByDateRet extends SessionRet
{
	//#CM570098
	// Class fields --------------------------------------------------

	//#CM570099
	// Class variables -----------------------------------------------

	//#CM570100
	// Class method --------------------------------------------------
	//#CM570101
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:30 $");
	}

	//#CM570102
	// Constructors --------------------------------------------------
	//#CM570103
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStockUseByDateRet(Connection conn, StorageSupportParameter param) throws Exception
	{
		this.wConn = conn;

		//#CM570104
		// Retrieval
		find(param);
	}

	//#CM570105
	// Public methods ------------------------------------------------
	//#CM570106
	/**
	 * Return partial qty of Retrieval result of <CODE>DmStock</CODE>.<BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *  , Expiry Date<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DmStock
	 */
	public Stock[] getEntities()
	{
		Stock[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray = (Stock[]) ((StockFinder) wFinder).getEntities(wStartpoint, wEndpoint);
			}
			catch (Exception e)
			{
				//#CM570107
				// 6006002=The data base error occurred. {0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM570108
	// Package methods -----------------------------------------------

	//#CM570109
	// Protected methods ---------------------------------------------

	//#CM570110
	// Private methods -----------------------------------------------
	//#CM570111
	/**
	 * Issue SQL sentence based on input Parameter. <BR>
	 * Maintain <code>StockFinder</code> as instance variable for retrieval. <BR>
	 * It is necessary to call < code>getEntities</code> Method to acquire The retrieval result.<BR>
	 * <BR>
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void find(StorageSupportParameter param) throws Exception
	{
		int count = 0;

		StockSearchKey skey = new StockSearchKey();

		//#CM570112
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		//#CM570113
		// Item Code
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			skey.setItemCode(param.getItemCode());
		}
		//#CM570114
		// Location
		if (!StringUtil.isBlank(param.getLocation()))
		{
			skey.setLocationNo(param.getLocation());
		}
		//#CM570115
		// Expiry Date
		if (!StringUtil.isBlank(param.getUseByDate()))
		{
			skey.setUseByDate(param.getUseByDate());
		}
		else
		{
			skey.setUseByDate("", "IS NOT NULL");
		}
		//#CM570116
		// Status flag : Central stock
		skey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM570117
		// Stock qty : More than 1
		skey.setStockQty(0, ">");

		if (param.getAreaTypeFlag().equals(StorageSupportParameter.AREA_TYPE_FLAG_NOASRS))
		{
			AreaOperator AreaOperator = new AreaOperator(wConn);
			
			String[] areaNo = null;
			int[] areaType = new int[2];
			areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
			areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
			
			//#CM570118
			// Acquire the areas other than ASRS, and add to Search condition. 
			//#CM570119
			// IS NULL Retrieval when there is no pertinent area
			areaNo = AreaOperator.getAreaNo(areaType);
			skey.setAreaNo(areaNo);
		}

		skey.setUseByDateGroup(1);
		skey.setUseByDateCollect("");
		skey.setUseByDateOrder(1, true);

		wFinder = new StockFinder(wConn);
		//#CM570120
		// Cursor open
		wFinder.open();
		count = ((StockFinder) wFinder).search(skey);
		//#CM570121
		// Initialization
		wLength = count;
		wCurrent = 0;
	}
}
//#CM570122
//end of class
