//#CM40674
//$Id: SessionAsOperationCycleRet.java,v 1.2 2006/10/26 06:34:22 suresh Exp $

//#CM40675
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
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.dbhandler.InOutResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InOutResultFinder;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.InOutResult;
import jp.co.daifuku.wms.base.entity.Aisle;

//#CM40676
/**
 * Designer : M.Koyama <BR>
 * Maker : M.Koyama <BR>
 * <BR>
 * The class to retrieve data for the operation results list information list box according to RM. <BR>
 * Receive the search condition as a parameter, and retrieve AS/RS operation results information. <BR>
 * in addition, keep the instance of this class in a session<BR>
 * delete from session after use<BR>
 * The following process are called in this class<BR>
 * <BR>
 * <B>1.search process(<CODE>SessionAsOperationCycleRet(Connection, AsScheduleParameter)</CODE>method )<BR></B>
 * <DIR>
 *   executed during listbox screen initialization<BR>
 *   <CODE>find(AsScheduleParameter)</CODE>method is called and AS/RS operation results information is retrieved. <BR>
 * <BR>
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
 * <BR>
 *   <return data>
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>screen name</th><td>:</td><th>parameter name</th></tr>
 *     <tr><td></td><td>Title machine</td><td>:</td><td>RackmasterNo</td></tr>
 *     <tr><td></td><td>total no. of operation</td><td>:</td><td>TotalInOutResultCount</td></tr>
 *     <tr><td></td><td>Storage Operations</td><td>:</td><td>StorageResultCount</td></tr>
 *     <tr><td></td><td>Picking Operations</td><td>:</td><td>RetrievalResultCount</td></tr>
 *     <tr><td></td><td>storage item qty</td><td>:</td><td>ItemStorageCount</td></tr>
 *     <tr><td></td><td>picking item qty</td><td>:</td><td>ItemRetrievalCount</td></tr>
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
public class SessionAsOperationCycleRet extends SessionRet
{
	//#CM40677
	/**
	 * call <CODE>find(AsScheduleParameter param)</CODE> method to do search<BR>
	 * set any qty retrieved by <CODE>find(AsScheduleParameter param)</CODE> method<BR>
	 * and it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param conn  instance to store database connection
	 * @param stParam AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public SessionAsOperationCycleRet(Connection conn, AsScheduleParameter stParam)throws Exception
	{
		wConn = conn;
		find(stParam);
	}
	
	//#CM40678
	/**
	 * Return search result of <CODE>InOutResult</CODE>. 
	 * @return InOutResult search result
	 */
	public Parameter[] getEntities()
	{
		AsScheduleParameter[] resultArray = null;
		InOutResult[] rData = null ;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{			
			try
			{
				rData = (InOutResult[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (AsScheduleParameter[]) convertToParams(rData);
			} catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM40679
	/**
	 * execute SQL based on input parameter<BR>
	 * Maintain retrieved <code>InOutResultFinder</code> as an instance variable. <BR>
	 * it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param stParam AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public void find(AsScheduleParameter stParam) throws Exception
	{
		//#CM40680
		// All StationNo of the aisle are acquired which belongs to WarehousestationNo of parameter. 
		AisleHandler aisleHandler = new AisleHandler(wConn);
		AisleSearchKey aisleKey = new AisleSearchKey();
		aisleKey.setWHStationNumber(stParam.getWareHouseNo());
		Aisle[] aisle = (Aisle[]) aisleHandler.find(aisleKey);

		String[] aisleNo = null;
		if( aisle != null )
		{
			aisleNo = new String[aisle.length];
			for( int i = 0 ; i < aisle.length ; i++ )
			{
				//#CM40681
				// StationNo of the aisle is acquired. 
				aisleNo[i] = aisle[i].getStationNumber();
			}
		}

		//#CM40682
		// Acquire the insertion delivery results of each aisle (title machine) from AS/RS operation results information. 
		InOutResultSearchKey ioKey = new InOutResultSearchKey();
		ioKey.setAisleStationNumberCollect("");

		//#CM40683
		// set the fetch condition
		//#CM40684
		// warehouse station no.
		ioKey.setWHStationNumber(stParam.getWareHouseNo());
		//#CM40685
		// aisle no
		ioKey.setAisleStationNumber(aisleNo);

		if( !StringUtil.isBlank(stParam.getStartDate()) )
		{
			//#CM40686
			// start date/time
			String str = stParam.getStartDate() + stParam.getStartTime();
			ioKey.setStoreDate( WmsFormatter.getTimeStampDate(str), ">=");
		}
		if( !StringUtil.isBlank(stParam.getEndDate()) )
		{
			//#CM40687
			// end date/time
			String str = stParam.getEndDate() + stParam.getEndTime();
			ioKey.setStoreDate( WmsFormatter.getTimeStampDate(str), "<=");
		}

		//#CM40688
		// set group by clause
		ioKey.setAisleStationNumberGroup(1);
		//#CM40689
		// set sorting order
		ioKey.setAisleStationNumberOrder(1, true);

		wFinder = new InOutResultFinder(wConn);
		//#CM40690
		//open cursor
		wFinder.open();
		int count = wFinder.search(ioKey);
		wLength = count;
		wCurrent = 0;
	}

	//#CM40691
	/**
	 * This class converts the ASInOutResult entity into the AsScheduleParameter parameter. 
	 * @param ety entity retrieved
	 * @return <CODE>AsScheduleParameter</CODE> class which set AS/RS operation results information
	 */
	private Parameter[] convertToParams(Entity[] ety) throws Exception
	{
		AisleHandler aisleHandler = new AisleHandler(wConn);
		AisleSearchKey aisleKey = new AisleSearchKey();

		InOutResult[] rData = (InOutResult[]) ety;
		if (rData == null || rData.length==0)
		{
			return null;
		}
		AsScheduleParameter[] stParam = new AsScheduleParameter[rData.length];
		for (int i = 0; i < rData.length; i++)
		{
			//#CM40692
			//Parameter added to list
			stParam[i] = new AsScheduleParameter();

			aisleKey.KeyClear();
			//#CM40693
			// RMterminal no
			aisleKey.setStationNumber(rData[i].getAisleStationNumber());
			Aisle aisle = (Aisle)aisleHandler.findPrimary(aisleKey);
			stParam[i].setRackmasterNo(Integer.toString(aisle.getAisleNumber()));
		}
		
		return stParam;
	}
}
//#CM40694
//end of class
