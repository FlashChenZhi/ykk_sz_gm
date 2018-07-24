// $Id: SessionAsBundleItfStockRet.java,v 1.2 2006/10/26 08:03:41 suresh Exp $

//#CM40374
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

//#CM40375
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * This class is used to search data for ASRS Bundle ITF list box<BR>
 * fetch the search conditions as parameter and search stockinfo<BR>
 * in addition, keep the instance of this class in a session
 * delete from session after use<BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.search process(<CODE>SessionAsBundleItfRet(Connection conn,AsScheduleParameter stParam)</CODE>method )<BR>
 * <DIR>
 *   executed during listbox screen initialization<BR>
 *   <CODE>find(AsScheduleParameter stParam)</CODE>method is called and stock info is retrieved. <BR>
 * <BR>
 *   <search condition> not mandatory<BR>
 *   <DIR>
 *     warehouse No<BR>
 *     consignor code<BR>
 *     item code<BR>
 *     start location<BR>
 *     end location<BR>
 *     case piece flag<BR>
 *     Case ITF<BR>
 *     bundle itf<BR>
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
 *     bundle itf<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/05/17</TD><TD>kaminishizono</TD><TD>new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:03:41 $
 * @author  $Author: suresh $
 */
public class SessionAsBundleItfStockRet extends SessionRet
{
	//#CM40376
	// Class fields --------------------------------------------------

	//#CM40377
	// Class variables -----------------------------------------------

	//#CM40378
	// Class method --------------------------------------------------
	//#CM40379
	/**
	 * call <CODE>find(AsScheduleParameter param)</CODE> method to do search<BR>
	 * Set how many acquisition numbers are there in <CODE>find(AsScheduleParameter stParam) </CODE> method. <BR>
	 * and it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param conn  instance to store database connection
	 * @param stParam AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public SessionAsBundleItfStockRet(Connection conn, AsScheduleParameter stParam) throws Exception
	{
		wConn = conn ;		
		find(stParam) ;
	}
	
	//#CM40380
	// Public methods ------------------------------------------------
	//#CM40381
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
	
	//#CM40382
	// Package methods -----------------------------------------------

	//#CM40383
	// Protected methods ---------------------------------------------

	//#CM40384
	// Private methods -----------------------------------------------
	//#CM40385
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

		if(!StringUtil.isBlank(stParam.getFromLocationNo()))
		{
			stKey.setLocationNo(stParam.getFromLocationNo(), ">=") ;
		}
		
		if(!StringUtil.isBlank(stParam.getToLocationNo()))
		{
			stKey.setLocationNo(stParam.getToLocationNo(),"<=") ;
		}
		
		if(!StringUtil.isBlank(stParam.getITF()))
		{
			stKey.setItf(stParam.getITF()) ;
		}
		
		if(!StringUtil.isBlank(stParam.getBundleITF()))
		{
			stKey.setBundleItf(stParam.getBundleITF()) ;
		}
		
		//#CM40386
		// status flag is "stock"
		stKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM40387
		// stock qty is 1 or more
		stKey.setStockQty(0,">");
		
		//#CM40388
		// Bundle ITF : excluding Null. 
		stKey.setBundleItf("","IS NOT NULL");

		//#CM40389
		// fetch stock from AS/RS
		AreaOperator areaOpe = new AreaOperator(wConn);
		int[] areaType = {Area.SYSTEM_DISC_KEY_ASRS};
		stKey.setAreaNo(areaOpe.getAreaNo(areaType));

		//#CM40390
		// consignor name
		stKey.setConsignorNameCollect("MIN");
		//#CM40391
		// item name
		stKey.setItemName1Collect("MIN");
		stKey.setBundleItfCollect("");
						
		//#CM40392
		// set sorting order			
		stKey.setBundleItfOrder(1,true);

		//#CM40393
		// set group by clause
		stKey.setBundleItfGroup(1);
		
		wFinder = new StockFinder(wConn) ;
		//#CM40394
		// Open the cursor. 
		wFinder.open() ;
		
		int count = wFinder.search(stKey);
		wLength = count ;
		wCurrent = 0 ;
		
	}
	
	//#CM40395
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
				if((stock[i].getBundleItf() != null) && !stock[i].getBundleItf().equals(""))
				{
					//#CM40396
					// bundle itf
					stParam[i].setBundleITF(stock[i].getBundleItf()) ; 
					//#CM40397
					// consignor name
					stParam[i].setConsignorName(stock[i].getConsignorName());
					//#CM40398
					// item name
					stParam[i].setItemName(stock[i].getItemName1());
				}			
			}
		}		
		return stParam ;
	}
}
//#CM40399
//end of class
