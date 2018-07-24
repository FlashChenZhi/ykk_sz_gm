//#CM40652
//$Id: SessionAsOperationCycleItemRet.java,v 1.2 2006/10/26 07:46:14 suresh Exp $

//#CM40653
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
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InOutResultSearchKey;
import jp.co.daifuku.wms.asrs.dbhandler.ASInOutResultFinder;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.InOutResult;
import jp.co.daifuku.wms.asrs.entity.ASInOutResult;

//#CM40654
/**
 * Designer : M.Koyama <BR>
 * Maker : M.Koyama <BR>
 * <BR>
 * The class to retrieve data for the results details list information list box according to the item. <BR>
 * Receive the search condition as a parameter, and retrieve AS/RS operation results information. <BR>
 * in addition, keep the instance of this class in a session
 * delete from session after use<BR>
 * The following process are called in this class<BR>
 * <BR>
 * <B>1.search process(<CODE>SessionAsOperationCycleItemRet(Connection, AsScheduleParameter)</CODE>method )<BR></B>
 * <DIR>
 *   executed during listbox screen initialization<BR>
 *   <CODE>find(AsScheduleParameter)</CODE>method is called and AS/RS operation results information is retrieved. <BR>
 * <BR>
 *   <Input data>*mandatory items
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>screen name</th><td>:</td><th>parameter name</th><tr>
 *   </table>
 *   </DIR>
 *   <search table>
 *   <DIR>
 *     InOutResult<BR>
 *   </DIR>
 * </DIR>
 * 
 * <B>2.display process(<CODE>getEntities()</CODE>method )<BR></B>
 * <BR>
 * <DIR>
 *   fetch data to display in screen<BR>
 *   fetch the display info from the search result<BR>
 *   set and return the search results as <CODE>AsScheduleParameter</CODE> array<BR>
 *   Display it in Consignor code and order of Item code. <BR>
 * <BR>
 *   <return data>
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>screen name</th><td>:</td><th>parameter name</th></tr>
 *     <tr><td></td><td>consignor code</td><td>:</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>consignor name</td><td>:</td><td>ConsignorName</td></tr>
 *     <tr><td></td><td>item code</td><td>:</td><td>ItemCode</td></tr>
 *     <tr><td></td><td>item name</td><td>:</td><td>ItemName</td></tr>
 *     <tr><td></td><td>storage case qty</td><td>:</td><td>StorageCaseQty</td></tr>
 *     <tr><td></td><td>storage piece qty</td><td>:</td><td>StoragePieceQty</td></tr>
 *     <tr><td></td><td>picking case qty</td><td>:</td><td>RetrievalCaseQty</td></tr>
 *     <tr><td></td><td>Retrieval piece qty</td><td>:</td><td>RetrievalPieceQty</td></tr>
 *   </table>
 *   </DIR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/28</TD><TD>M.Koyama</TD><TD>New</TD></TR></TABLE>
 * <BR>
 * 
 * @author $Author M.Koyama
 * @version $Revision 1.1 2005/10/28
 */
public class SessionAsOperationCycleItemRet extends SessionRet
{
	//#CM40655
	/**
	 * call <CODE>find(AsScheduleParameter param)</CODE> method to do search<BR>
	 * set any qty retrieved by <CODE>find(AsScheduleParameter param)</CODE> method<BR>
	 * and it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param conn  instance to store database connection
	 * @param stParam AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public SessionAsOperationCycleItemRet(Connection conn, AsScheduleParameter stParam)throws Exception
	{
		wConn = conn;
		find(stParam);
	}
	
	//#CM40656
	/**
	 * Return search result of <CODE>InOutResult</CODE>. 
	 * @return InOutResult search result
	 */
	public Parameter[] getEntities()
	{
		AsScheduleParameter[] resultArray = null;
		ASInOutResult[] rData = null ;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{			
			try
			{
				rData = (ASInOutResult[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (AsScheduleParameter[]) convertToParams(rData);
			} catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM40657
	/**
	 * execute SQL based on input parameter<BR>
	 * Maintain retrieved <code>InOutResultFinder</code> as an instance variable. <BR>
	 * it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param stParam AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public void find(AsScheduleParameter stParam) throws Exception
	{
		//#CM40658
		// set the fetch condition
		InOutResultSearchKey ioKey = new InOutResultSearchKey();
		
		//#CM40659
		// set the fetch condition
		ioKey.setConsignorCodeCollect();
		ioKey.setItemCodeCollect();
		ioKey.setConsignorNameCollect("MAX");
		ioKey.setItemName1Collect("MAX");
		ioKey.setEnteringQtyCollect("MAX");
		ioKey.setInOutQuantityCollect("SUM");

		//#CM40660
		// fetch station no. with refer to terminal
		AisleHandler aisleHandler = new AisleHandler(wConn);
		AisleSearchKey aisleKey = new AisleSearchKey();
		aisleKey.setAisleNumber(new Integer(stParam.getRackmasterNo()).intValue());
		Aisle aisle = (Aisle)aisleHandler.findPrimary(aisleKey);
		ioKey.setAisleStationNumber(aisle.getStationNumber());

		if( !StringUtil.isBlank(stParam.getStartDate()) )
		{
			//#CM40661
			// start date/time
			String str = stParam.getStartDate() + stParam.getStartTime();
			ioKey.setStoreDate( WmsFormatter.getTimeStampDate(str), ">=");
		}
		if( !StringUtil.isBlank(stParam.getEndDate()) )
		{
			//#CM40662
			// end date/time
			String str = stParam.getEndDate() + stParam.getEndTime();
			ioKey.setStoreDate( WmsFormatter.getTimeStampDate(str), "<=");
		}

		//#CM40663
		// result creation type
		int[] result = { InOutResult.STORAGE, InOutResult.RETRIEVAL };
		ioKey.setResultKind(result);

		ioKey.setConsignorCodeGroup(1);
		ioKey.setItemCodeGroup(2);

		//#CM40664
		// set sorting order
		ioKey.setConsignorCodeOrder(1, true);
		ioKey.setItemCodeOrder(2, true);

		wFinder = new ASInOutResultFinder(wConn);
		//#CM40665
		//open cursor
		wFinder.open();
		int count = wFinder.search(ioKey);
		wLength = count;
		wCurrent = 0;
	}
	
	//#CM40666
	/**
	 * This class converts the ASInOutResult entity into the AsScheduleParameter parameter. 
	 * @param ety entity retrieved
	 * @return <CODE>AsScheduleParameter</CODE> class which set AS/RS operation results information
	 */
	private Parameter[] convertToParams(Entity[] ety) throws Exception
	{
		ASInOutResult[] rData = (ASInOutResult[]) ety;
		if (rData == null || rData.length==0)
		{	
			return null;
		}

		AsScheduleParameter[] stParam = new AsScheduleParameter[rData.length];
		for (int i = 0; i < rData.length; i++)
		{
			stParam[i] = new AsScheduleParameter();
			//#CM40667
			// consignor code
			stParam[i].setConsignorCode(rData[i].getConsignorCode());
			//#CM40668
			// consignor name
			stParam[i].setConsignorName(rData[i].getConsignorName());
			//#CM40669
			// item code
			stParam[i].setItemCode(rData[i].getItemCode());
			//#CM40670
			// item name
			stParam[i].setItemName(rData[i].getItemName1());
			//#CM40671
			// packed qty per case
			stParam[i].setEnteringQty(rData[i].getEnteringQty());
			//#CM40672
			// total no. of operation(storage qty + picking qty)
			stParam[i].setTotalInOutResultQty(rData[i].getSumInOutQuantity());
		}
		
		return stParam;
	}
}
//#CM40673
//end of class
