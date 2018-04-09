// $Id: SessionAsConsignorStockRet.java,v 1.2 2006/10/26 08:02:55 suresh Exp $

//#CM40426
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.sessionret;

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM40427
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * This class is used to search data for ASRS Consignor list box<BR>
 * fetch the search conditions as parameter and search stockinfo<BR>
 * in addition, keep the instance of this class in a session
 * delete from session after use<BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.search process(<CODE>SessionAsConsignoerRet(Connection conn,AsScheduleParameter param)</CODE>method )<BR>
 * <DIR>
 *   executed during listbox screen initialization<BR>
 *   <CODE>find(AsScheduleParameter param)</CODE>method is called and stock info is retrieved. <BR>
 * <BR>
 *   <search condition> not mandatory<BR>
 *   <DIR>
 *     warehouse (area no.)<BR>
 *     consignor code<BR>
 *     status flag is "stock" <BR>
 *     stock qty is 1 or more <BR>
 *   </DIR>
 *   <search table><BR>
 *   <DIR>
 *     DMSTOCK <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.display process(<CODE>getEntities()</CODE>method )<BR>
 * <BR>
 * <DIR>
 *   fetch data to display in screen<BR>
 *   1.fetch the display info from the search result<BR>
 *   Set search result in the stock info array and return it. <BR>
 * <BR>
 *   <display items>
 *   <DIR>
 *     consignor code<BR>
 *     consignor name<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/05/17</TD><TD>kaminishizono</TD><TD>new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:02:55 $
 * @author  $Author: suresh $
 */

public class SessionAsConsignorStockRet extends SessionRet
{
	//#CM40428
	// Class fields --------------------------------------------------

	//#CM40429
	// Class variables -----------------------------------------------

	//#CM40430
	// Class method --------------------------------------------------
	//#CM40431
	/**
	 * returns the version of this class
	 * @return version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:02:55 $");
	}

	//#CM40432
	/**
	 * call <CODE>find(AsScheduleParameter param)</CODE> method to do search<BR>
	 * set any qty retrieved by <CODE>find(AsScheduleParameter param)</CODE> method<BR>
	 * and it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param conn  instance to store database connection
	 * @param param AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public SessionAsConsignorStockRet(Connection conn, AsScheduleParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	//#CM40433
	// Public methods ------------------------------------------------
	//#CM40434
	/**
	 * Return search result of <CODE>DMSTOCK</CODE>. 
	 * @return DMSTOCK search result
	 */
	public Parameter[] getEntities()
	{
		AsScheduleParameter[] resultArray = null;
		Stock temp[] = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{	
			try
			{	
				temp = (Stock[])((StockFinder)wFinder).getEntities(wStartpoint, wEndpoint);
				resultArray = convertToAsControlParams(temp);
			}
			catch (Exception e)
			{
				//#CM40435
				//Drop the error to the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM40436
	// Package methods -----------------------------------------------

	//#CM40437
	// Protected methods ---------------------------------------------

	//#CM40438
	// Private methods -----------------------------------------------
	//#CM40439
	/**
	 * execute SQL based on input parameter<BR>
	 * Maintain retrieved <code>StockFinder</code> as an instance variable. <BR>
	 * it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param param AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	private void find(AsScheduleParameter param) throws Exception
	{	

		StockSearchKey skey = new StockSearchKey();
		//#CM40440
		// do search
		//#CM40441
		// warehouse (area no.)
		if (!StringUtil.isBlank(param.getAreaNo()))
		{
			skey.setAreaNo(param.getAreaNo());
		}
		//#CM40442
		// consignor code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		else
		{
			skey.setConsignorCode("","IS NOT NULL");
		}

		//#CM40443
		// status flag is "stock"
		skey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM40444
		// stock qty is 1 or more
		skey.setStockQty(0, ">");

		//#CM40445
		// fetch stock from AS/RS
		AreaOperator areaOpe = new AreaOperator(wConn);
		int[] areaType = {Area.SYSTEM_DISC_KEY_ASRS};
		skey.setAreaNo(areaOpe.getAreaNo(areaType));

		//#CM40446
		// set group by clause
		skey.setConsignorCodeGroup(1);
		skey.setConsignorNameGroup(2);

		skey.setConsignorCodeCollect("");
		skey.setConsignorNameCollect("");
		
		//#CM40447
		// set sorting order
		skey.setConsignorCodeOrder(1, true);
		skey.setConsignorNameOrder(2, true);
		//#CM40448
		//WFinder is an instance variable of parents class SessionRet. 
		wFinder = new StockFinder(wConn);
		//#CM40449
		// open cursor
		wFinder.open();
		int count = ((StockFinder)wFinder).search(skey);
		//#CM40450
		// Initialization. WLength is an instance variable of parents class SessionRet. 
		wLength = count;
		wCurrent = 0;
	}
	
	//#CM40451
	/**
	 * This class converts stock entity to AsScheduleParameter
	 * @param entityView entity retrieved
	 * @return <CODE>AsScheduleParameter</CODE>class set with stock info
	 */
	private AsScheduleParameter[] convertToAsControlParams(Stock[] entityView)
	{
		AsScheduleParameter[] stParam = null;
		
		if (entityView == null || entityView.length==0)
		{	
		 	return null;
		}
			stParam = new AsScheduleParameter[entityView.length];
			for (int i = 0; i < entityView.length; i++)
			{
					stParam[i] = new AsScheduleParameter();
					stParam[i].setConsignorCode(entityView[i].getConsignorCode());
					stParam[i].setConsignorName(entityView[i].getConsignorName());
				
			}
			
		return stParam;
	}
}
//#CM40452
//end of class
