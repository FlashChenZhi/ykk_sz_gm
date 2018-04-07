// $Id: SessionAsLocationStockRet.java,v 1.2 2006/10/26 07:47:07 suresh Exp $

//#CM40573
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
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM40574
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * The class to retrieve data for the ASRSRetrieval location list list box. <BR>
 * fetch the search conditions as parameter and search stockinfo<BR>
 * in addition, keep the instance of this class in a session
 * delete from session after use<BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.search process(<CODE>SessionAsLocationStockRet(Connection conn,AsScheduleParameter stParam)</CODE>method )<BR>
 * <DIR>
 *   executed during listbox screen initialization<BR>
 *   <CODE>find(AsScheduleParameter stParam)</CODE>method is called and stock info is retrieved. <BR>
 * <BR>
 *   <search condition> not mandatory<BR>
 *   <DIR>
 *     warehouse No <BR>
 *     consignor code<BR>
 *     item code<BR>
 *     start location<BR>
 *     end location<BR>
 *     case piece flag<BR>
 *     status flag is "stock" <BR>
 *     stock qty is 1 or more <BR>
 *     Area No is a movable rack.  <BR>
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
 *     Location No<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/05/17</TD><TD>kaminishizono</TD><TD>new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 07:47:07 $
 * @author  $Author: suresh $
 */
public class SessionAsLocationStockRet extends SessionRet
{
	//#CM40575
	// Class fields --------------------------------------------------

	//#CM40576
	// Class variables -----------------------------------------------

	//#CM40577
	// Class method --------------------------------------------------	
	//#CM40578
	/**
	 * call <CODE>find(AsScheduleParameter param)</CODE> method to do search<BR>
	 * set any qty retrieved by <CODE>find(AsScheduleParameter param)</CODE> method<BR>
	 * and it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param conn  instance to store database connection
	 * @param stParam AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public SessionAsLocationStockRet(Connection conn, AsScheduleParameter stParam) throws Exception
	{
		wConn = conn ;		
		find(stParam) ;
	}
	
	//#CM40579
	// Public methods ------------------------------------------------
	//#CM40580
	/**
	 * Return search result of <CODE>DMSTOCK</CODE>. 
	 * @return DMSTOCK search result
	 */
	public AsScheduleParameter[] getEntities()
	{		
		AsScheduleParameter[] resultArray = null ;
		Stock[] stock = null ;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{	
			try
			{
				stock = (Stock[])wFinder.getEntities(wStartpoint,wEndpoint) ;
				resultArray = convertToAsParams(stock) ;
			}
			catch (Exception e)
			{	    		
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		
		return resultArray;
	}
	
	//#CM40581
	// Package methods -----------------------------------------------

	//#CM40582
	// Protected methods ---------------------------------------------

	//#CM40583
	// Private methods -----------------------------------------------
	//#CM40584
	/**
	 * execute SQL based on input parameter<BR>
	 * Maintain retrieved <code>StockFinder</code> as an instance variable. <BR>
	 * it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param stParam AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public void find(AsScheduleParameter stParam) throws Exception
	{	
		StockSearchKey stKey = new StockSearchKey() ;

		if(!StringUtil.isBlank(stParam.getAreaNo()))
		{
			stKey.setAreaNo(stParam.getAreaNo()) ;
		}
		
		if(!StringUtil.isBlank(stParam.getConsignorCode()))
		{
			stKey.setConsignorCode(stParam.getConsignorCode()) ;
		}
	
		if(!StringUtil.isBlank(stParam.getItemCode()))
		{
			stKey.setItemCode(stParam.getItemCode()) ;
		}

		if(!StringUtil.isBlank(stParam.getCasePieceFlag()))
		{		
			if(stParam.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
			{
				stKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE) ;
			}
			else if(stParam.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
			{
				stKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE) ;
			}
			else if(stParam.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
			{
				stKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING) ;
			}
			else if(stParam.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_ALL))
			{
				//#CM40585
				//do nothing
			}
		}
		//#CM40586
		// When the shelf is set
		if (!StringUtil.isBlank(stParam.getLocationNo()))
		{
			stKey.setLocationNo(stParam.getLocationNo());
		}
		//#CM40587
		// when start location or End location is set.
		else
		{
			//#CM40588
			// Start location is set.
			if (!StringUtil.isBlank(stParam.getFromLocationNo()))
			{
				stKey.setLocationNo(stParam.getFromLocationNo());
			}
			//#CM40589
			// End location is set.
			if (!StringUtil.isBlank(stParam.getToLocationNo()))
			{
				stKey.setLocationNo(stParam.getToLocationNo());
			}
		}

		//#CM40590
		// status flag is "stock"
		stKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM40591
		// stock qty is 1 or more
		stKey.setStockQty(0,">");

		//#CM40592
		// fetch stock from AS/RS
		AreaOperator areaOpe = new AreaOperator(wConn);
		int[] areaType = {Area.SYSTEM_DISC_KEY_ASRS};
		stKey.setAreaNo(areaOpe.getAreaNo(areaType));

		//#CM40593
		// location no.
		stKey.setLocationNoCollect("DISTINCT");
		//#CM40594
		// consignor name
		stKey.setConsignorNameCollect("MIN");
		//#CM40595
		// item name
		stKey.setItemName1Collect("MIN");
		
		//#CM40596
		// set sorting order
		stKey.setLocationNoOrder(1,true);
		//#CM40597
		// set group by clause
		stKey.setLocationNoGroup(1);
		
		wFinder = new StockFinder(wConn) ;
		//#CM40598
		// open cursor
		wFinder.open() ;
		
		int count = wFinder.search(stKey);
		wLength = count ;
		wCurrent = 0 ;
		
	}

	//#CM40599
	/**
	 * This class converts stock entity to AsScheduleParameter
	 * @param stock entity retrieved
	 * @return <CODE>AsScheduleParameter</CODE>class set with stock info
	 */
	private AsScheduleParameter[] convertToAsParams(Stock[] stock)
	{
		AsScheduleParameter[] stParam = null ;
		if((stock != null) && (stock.length != 0))
		{
			stParam = new AsScheduleParameter[stock.length] ;
			
			for(int i=0; i < stock.length; i++)
			{
				stParam[i] = new AsScheduleParameter() ;
				//#CM40600
				// Location No
				stParam[i].setLocationNo(stock[i].getLocationNo()) ;
				stParam[i].setConsignorName(stock[i].getConsignorName()) ; // 荷主名称
				stParam[i].setItemName(stock[i].getItemName1()) ; // 商品名称
			}
		}		
		return stParam ;
	}
}
//#CM40601
//end of class
