// $Id: SessionStockLocationRet.java,v 1.2 2006/12/07 08:57:31 suresh Exp $
package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;

//#CM570070
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

//#CM570071
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * The class to do data retrieval for Location list list box Retrieval processing  (inventory information).<BR>
 * Receive Search condition with Parameter, and do Retrieval of the Location list. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStockLocationRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   Do the Retrieval of inventory information by calling <CODE>find</CODE> Method. <BR>
 * <BR>
 *   <Search condition> MandatoryItem*<BR>
 *   <DIR>
 *      , Consignor Code*<BR>
 *      , Item Code<BR>
 *      , Location<BR>
 *      , Status flag : Central stock*<BR>
 *      , Stock qty : More than 1*<BR>
 *   </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *      , DMSTOCK <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Display processing(<CODE>getEntities</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Acquire the data to display it on the screen. <BR>
 *   1.Acquire display information from the retrieval result of obtaining in Retrieval processing. <BR>
 * <BR>
 *   <Display Item>
 *   <DIR>
 *      , Location<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2008/10/18</TD><TD>Y.Okamura</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:31 $
 * @author  $Author: suresh $
 */
public class SessionStockLocationRet extends SessionRet
{
	//#CM570072
	// Class fields --------------------------------------------------

	//#CM570073
	// Class variables -----------------------------------------------

	//#CM570074
	// Class method --------------------------------------------------
	//#CM570075
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:31 $");
	}

	//#CM570076
	// Constructors --------------------------------------------------
	//#CM570077
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStockLocationRet(Connection conn, StorageSupportParameter param) throws Exception
	{
		this.wConn = conn;

		//#CM570078
		// Retrieval
		find(param);
	}

	//#CM570079
	// Public methods ------------------------------------------------
	//#CM570080
	/**
	 * Return partial qty of Retrieval result of <CODE>DmStock</CODE>.<BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *  , Location<BR>
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
				//#CM570081
				// 6006002=The data base error occurred. {0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM570082
	// Package methods -----------------------------------------------

	//#CM570083
	// Protected methods ---------------------------------------------

	//#CM570084
	// Private methods -----------------------------------------------
	//#CM570085
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

		//#CM570086
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		//#CM570087
		// Item Code
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			skey.setItemCode(param.getItemCode());
		}
		//#CM570088
		// Location
		if (!StringUtil.isBlank(param.getLocation()))
		{
			skey.setLocationNo(param.getLocation());
		}
		//#CM570089
		// Status flag : Central stock
		skey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM570090
		// Stock qty : More than 1
		skey.setStockQty(1, ">=");

		if (param.getAreaTypeFlag().equals(StorageSupportParameter.AREA_TYPE_FLAG_NOASRS))
		{
			AreaOperator AreaOperator = new AreaOperator(wConn);
			
			String[] areaNo = null;
			int[] areaType = new int[2];
			areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
			areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
			
			//#CM570091
			// Acquire the areas other than ASRS, and add to Search condition. 
			//#CM570092
			// IS NULL Retrieval when there is no pertinent area
			areaNo = AreaOperator.getAreaNo(areaType);
			skey.setAreaNo(areaNo);
		}

		skey.setLocationNoGroup(1);
		skey.setLocationNoCollect("");
		skey.setLocationNoOrder(1, true);

		wFinder = new StockFinder(wConn);
		//#CM570093
		// Cursor open
		wFinder.open();
		count = ((StockFinder) wFinder).search(skey);
		//#CM570094
		// Initialization
		wLength = count;
		wCurrent = 0;
	}
}
//#CM570095
//end of class
