// $Id: SessionAsItemStockRet.java,v 1.2 2006/10/26 07:48:23 suresh Exp $

//#CM40477
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

//#CM40478
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * This class is used to search data for ASRS Item list box<BR>
 * fetch the search conditions as parameter and search stockinfo<BR>
 * in addition, keep the instance of this class in a session
 * delete from session after use<BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.search process(<CODE>SessionAsItemRet(Connection conn,AsScheduleParameter param)</CODE>method )<BR>
 * <DIR>
 *   executed during listbox screen initialization<BR>
 *   <CODE>find(AsScheduleParameter param)</CODE>method is called and stock info is retrieved. <BR>
 * <BR>
 *   <search condition> not mandatory<BR>
 *   <DIR>
 *     warehouse (area no.)<BR>
 *     consignor code <BR>
 *     start location no. <BR>
 *     end locationNo <BR>
 *     item code <BR>
 *     status is empty location <BR>
 *     area no. is mobile rack area no.<BR>
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
 *     item code <BR>
 *     item name <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/05/17</TD><TD>kaminishizono</TD><TD>new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 07:48:23 $
 * @author  $Author: suresh $
 */
public class SessionAsItemStockRet extends SessionRet
{
	//#CM40479
	// Class fields --------------------------------------------------

	//#CM40480
	// Class variables -----------------------------------------------

	//#CM40481
	// Class method --------------------------------------------------
	//#CM40482
	/**
	 * returns the version of this class
	 * @return version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 07:48:23 $");
	}

	//#CM40483
	/**
	 * Call <CODE>find(AsScheduleParameter param) </CODE>method to retrieve it. <BR>
	 * The acquisition number in <CODE>find(AsScheduleParameter param) </CODE> method sets how many are.<BR>
	 * and it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param conn  instance to store database connection
	 * @param param AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public SessionAsItemStockRet(Connection conn, AsScheduleParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	//#CM40484
	// Public methods ------------------------------------------------
	//#CM40485
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
				resultArray = convertToStockParams(temp);
			}
			catch (Exception e)
			{
				//#CM40486
				//Drop the error to the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}
	
	//#CM40487
	// Package methods -----------------------------------------------

	//#CM40488
	// Protected methods ---------------------------------------------

	//#CM40489
	// Private methods -----------------------------------------------
	//#CM40490
	/**
	 * execute SQL based on input parameter<BR>
	 * Maintain retrieved <code>StockFinder</code> as an instance variable. <BR>
	 * it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param param AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	private void find(AsScheduleParameter param) throws Exception
	{	
		StockSearchKey skey = new StockSearchKey() ;
			
		//#CM40491
		// warehouse (area no.)
		if (!StringUtil.isBlank(param.getAreaNo()))
		{
			skey.setAreaNo(param.getAreaNo());
		}
		//#CM40492
		// consignor code
		if(!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode()) ;
		}
		//#CM40493
		// item code		
		if(!StringUtil.isBlank(param.getItemCode()))
		{
			skey.setItemCode(param.getItemCode()) ;
		}
		//#CM40494
		// start item code		
		if(!StringUtil.isBlank(param.getStartItemCode()))
		{
			skey.setItemCode(param.getStartItemCode()) ;
		}
		//#CM40495
		// end item code			
		if(!StringUtil.isBlank(param.getEndItemCode()))
		{
			skey.setItemCode(param.getEndItemCode()) ;
		}
		//#CM40496
		// start location no.
		if (!StringUtil.isBlank(param.getFromLocationNo()))
			skey.setLocationNo(param.getFromLocationNo(), ">=");
		//#CM40497
		// end locationNo
		if (!StringUtil.isBlank(param.getToLocationNo()))
			skey.setLocationNo(param.getToLocationNo(), "<=");

		//#CM40498
		// status flag is "stock"
		skey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM40499
		// stock qty is 1 or more
		skey.setStockQty(0, ">");
				
		//#CM40500
		// if item code is not null
		skey.setConsignorCode("","IS NOT NULL");

		//#CM40501
		// fetch stock from AS/RS
		AreaOperator areaOpe = new AreaOperator(wConn);
		int[] areaType = {Area.SYSTEM_DISC_KEY_ASRS};
		skey.setAreaNo(areaOpe.getAreaNo(areaType));

		//#CM40502
		// set group by clause
		skey.setItemCodeGroup(1);
		skey.setItemName1Group(2);

		skey.setItemCodeCollect("");
		skey.setItemName1Collect("");
		//#CM40503
		// set sorting order
		skey.setItemCodeOrder(1, true);
		skey.setItemName1Order(2,true);
	
		wFinder = new StockFinder(wConn);
		//#CM40504
		// open cursor
		wFinder.open();
		int count = ((StockFinder)wFinder).search(skey);
		//#CM40505
		// initialize
		wLength = count;
		wCurrent = 0;
			
	}
	
	//#CM40506
	/**
	 * This class converts stock entity to AsScheduleParameter
	 * @param stock entity retrieved
	 * @return <CODE>AsScheduleParameter</CODE> class which set work information
	 */
	private AsScheduleParameter[] convertToStockParams(Stock[] stock)
	{
		AsScheduleParameter[] stParam = null;		
		if (stock == null || stock.length==0)
		{	
		 	return null;
		}
		stParam = new AsScheduleParameter[stock.length];
		for (int i = 0; i < stock.length; i++)
		{
				stParam[i] = new AsScheduleParameter();
				stParam[i].setItemCode(stock[i].getItemCode());
				stParam[i].setItemName(stock[i].getItemName1());
		}
			
		return stParam;
	}
}
//#CM40507
//end of class
