//#CM40695
//$Id: SessionAsStockDetailRet.java,v 1.2 2006/10/26 06:33:25 suresh Exp $

//#CM40696
/*
 * Copyright 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
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
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Stock;

//#CM40697
/**
 * Designer : M.Koyama <BR>
 * Maker : M.Koyama <BR>
 * <BR>
 * This class is used to search data for ASRS stock list box<BR>
 * fetch the search conditions as parameter and search stockinfo<BR>
 * in addition, keep the instance of this class in a session<BR>
 * delete from session after use<BR>
 * The following process are called in this class<BR>
 * <BR>
 * <B>1.search process(<CODE>SessionAsStockDetailRet(Connection, AsScheduleParameter)</CODE>method )<BR></B>
 * <DIR>
 *   executed during listbox screen initialization<BR>
 *   <CODE>find(AsScheduleParameter)</CODE>method is called and stock info is retrieved. <BR>
 * <BR>
 *   <Input data>*mandatory items
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>screen name</th><td>:</td><th>parameter name</th><tr>
 *     <tr><td></td><td>location no.</td><td>:</td><td>LocationNo</td></tr>
 *   </table>
 *   </DIR>
 *   <search table>
 *   <DIR>
 *     DMSTOCK<BR>
 *   </DIR>
 * </DIR>
 * 
 * <B>2.display process(<CODE>getEntities()</CODE>method )<BR></B>
 * <BR>
 * <DIR>
 *   fetch data to display in screen<BR>
 *   fetch the display info from the search result<BR>
 *   set and return the search results as <CODE>AsScheduleParameter</CODE> array<BR>
 *   Display it in the order of Consignor code, Item code, Case/Piece flag, and Expiry date.<BR>
 * <BR>
 *   <return data>
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>screen name</th><td>:</td><th>parameter name</th></tr>
 *     <tr><td></td><td>consignor code</td><td>:</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>item code</td><td>:</td><td>ItemCode</td></tr>
 *     <tr><td></td><td>case stock qty</td><td>:</td><td>StockCaseQty</td></tr>
 *     <tr><td></td><td>packed qty per case</td><td>:</td><td>EnteringQty</td></tr>
 *     <tr><td></td><td>Case ITF</td><td>:</td><td>Itf</td></tr>
 *     <tr><td></td><td>case piece flag</td><td>:</td><td>CasePieceFlag</td></tr>
 *     <tr><td></td><td>storage date</td><td>:</td><td>StoringDate</td></tr>
 *     <tr><td></td><td>expiry date</td><td>:</td><td>UseByDate</td></tr>
 *     <tr><td></td><td>consignor name</td><td>:</td><td>ConsignorName</td></tr>
 *     <tr><td></td><td>item name</td><td>:</td><td>ItemName</td></tr>
 *     <tr><td></td><td>piece stock qty</td><td>:</td><td>StockPieceQty</td></tr>
 *     <tr><td></td><td>packed qty per piece</td><td>:</td><td>BundleEnteringQty</td></tr>
 *     <tr><td></td><td>bundle itf</td><td>:</td><td>BundleEnteringQty</td></tr>
 *     <tr><td></td><td>storage time</td><td>:</td><td>StoringTime</td></tr>
 *   </table>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/28</TD><TD>M.Koyama</TD><TD>New</TD></TR></TABLE>
 * <BR>
 * 
 * @author $Author M.Koyama
 * @version $Revision 1.1 2005/10/28
 */
public class SessionAsStockDetailRet extends SessionRet
{
	//#CM40698
	/**
	 * call <CODE>find(AsScheduleParameter param)</CODE> method to do search<BR>
	 * set any qty retrieved by <CODE>find(AsScheduleParameter param)</CODE> method<BR>
	 * and it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param conn  instance to store database connection
	 * @param stParam AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public SessionAsStockDetailRet(Connection conn, AsScheduleParameter stParam)throws Exception
	{
		wConn = conn;
		find(stParam);
	}
	
	
	//#CM40699
	/**
	 * Return search result of <CODE>DMSTOCK</CODE>. 
	 * @return DMSTOCK search result
	 */
	public Parameter[] getEntities()
	{
		AsScheduleParameter[] resultArray = null;
		Stock[] rData = null ;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{			
			try
			{
				rData = (Stock[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (AsScheduleParameter[]) convertToParams(rData);
			} catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM40700
	/**
	 * execute SQL based on input parameter<BR>
	 * Maintain retrieved <code>StockFinder</code> as an instance variable. <BR>
	 * it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param stParam AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public void find(AsScheduleParameter stParam) throws Exception
	{
			StockSearchKey sKey = new StockSearchKey();
			//#CM40701
			//do search
			//#CM40702
			//warehouse
			if (!StringUtil.isBlank(stParam.getWareHouseNo()))
			{
				sKey.setAreaNo(stParam.getWareHouseNo());
			}

			//#CM40703
			//location no.
			if (!StringUtil.isBlank(stParam.getLocationNo()))
			{
				sKey.setLocationNo(stParam.getLocationNo());
			}
			
			//#CM40704
			// The status flag : excluding the deletion. & center stock or waiting for arrival of goods --> Only center stock
			//#CM40705
			// The status flag : excluding the deletion. 
			sKey.setStatusFlag(Stock.STATUS_FLAG_DELETE, "!=");
			String[] status = {Stock.STOCK_STATUSFLAG_OCCUPIED, Stock.STOCK_STATUSFLAG_RECEIVINGRESERVE};
			sKey.setStatusFlag(status);
			
			//#CM40706
			// set the fetch condition
			//#CM40707
			// set group by clause
			//#CM40708
			// set sorting order
			sKey.setConsignorCodeOrder(1, true);
			sKey.setItemCodeOrder(2, true);
			sKey.setCasePieceFlagOrder(3, true);
			sKey.setUseByDateOrder(4, true);
			
			wFinder = new StockFinder(wConn);
			//#CM40709
			//open cursor
			wFinder.open();
			int count = wFinder.search(sKey);
			wLength = count;
			wCurrent = 0;

	}
	
	//#CM40710
	/**
	 * This class converts stock entity to AsScheduleParameter
	 * @param ety entity retrieved
	 * @return <CODE>AsScheduleParameter</CODE>class set with stock info
	 */
	private Parameter[] convertToParams(Entity[] ety)
	{
	
		Stock[] rData = (Stock[]) ety;
		if (rData == null || rData.length==0)
		{	
		 	return null;
		}
		AsScheduleParameter[] stParam = new AsScheduleParameter[rData.length];
			for (int i = 0; i < rData.length; i++)
			{
				stParam[i] = new AsScheduleParameter();	
				//#CM40711
				// stock id
				stParam[i].setStockId(rData[i].getStockId());
				//#CM40712
				// consignor code
				stParam[i].setConsignorCode(rData[i].getConsignorCode());
				//#CM40713
				// item code
				stParam[i].setItemCode(rData[i].getItemCode()); 
				//#CM40714
				// case stock qty
				stParam[i].setStockCaseQty(DisplayUtil.getCaseQty(rData[i].getStockQty(),
										rData[i].getEnteringQty(), rData[i].getCasePieceFlag())); 
				//#CM40715
				// packed qty per case
				stParam[i].setEnteringQty(rData[i].getEnteringQty());
				//#CM40716
				// Case ITF
				stParam[i].setITF(rData[i].getItf()); 
				//#CM40717
				// case piece flag
				stParam[i].setCasePieceFlag(rData[i].getCasePieceFlag()); 
				//#CM40718
				// storage date/time
				stParam[i].setInStockDate(rData[i].getInstockDate()); 
				//#CM40719
				// lot no.
				stParam[i].setLotNo(rData[i].getLotNo()); 
				//#CM40720
				// consignor name
				stParam[i].setConsignorName(rData[i].getConsignorName()); 
				//#CM40721
				// item code
				stParam[i].setItemName(rData[i].getItemName1()); 
				//#CM40722
				// case stock qty
				stParam[i].setStockPieceQty(DisplayUtil.getPieceQty(rData[i].getStockQty(),
										rData[i].getEnteringQty(), rData[i].getCasePieceFlag())); 
				//#CM40723
				// packed qty per piece
				stParam[i].setBundleEnteringQty(rData[i].getBundleEnteringQty()); 
				//#CM40724
				// bundle itf
				stParam[i].setBundleITF(rData[i].getBundleItf()); 
				//#CM40725
				// storage type
				stParam[i].setStoringStatus(Integer.toString(rData[i].getRestoring()));
				//#CM40726
				// expiry date 
				stParam[i].setUseByDate(rData[i].getUseByDate()); 
				//#CM40727
				// update date/time
				stParam[i].setLastUpdateDate(rData[i].getLastUpdateDate()); 
			}
		
		return stParam;
	}
}
//#CM40728
//end of class
