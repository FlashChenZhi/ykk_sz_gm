//#CM40729
//$Id: SessionAsStoragePlanDateRet.java,v 1.2 2006/10/26 06:32:58 suresh Exp $

//#CM40730
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
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM40731
/**
 * Designer : M.Koyama <BR>
 * Maker : M.Koyama <BR>
 * <BR>
 * This class is used to search data for ASRS storage plan date <BR>
 * Receive the search condition as a parameter, and retrieve work information. <BR>
 * in addition, keep the instance of this class in a session<BR>
 * delete from session after use<BR>
 * The following process are called in this class<BR>
 * <BR>
 * <B>1.search process(<CODE>SessionAsPlanDateRet(Connection, AsScheduleParameter)</CODE>method )<BR></B>
 * <DIR>
 *   executed during listbox screen initialization<BR>
 *   <CODE>find(AsScheduleParameter)</CODE> Call work method information is retrieved. <BR>
 * <BR>
 *   <Input data>*mandatory items
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>screen name</th><td>:</td><th>parameter name</th><tr>
 *     <tr><td></td><td>consignor code</td><td>:</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>storage plan date</td><td>:</td><td>PlanDate</td></tr>
 *   </table>
 *   </DIR>
 *   <search table>
 *   <DIR>
 *     DNWORKINFO<BR>
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
 *     <tr><td></td><td>storage plan date</td><td>:</td><td>PlanDate</td></tr>
 *   </table>
 *   </DIR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/28</TD><TD>M.Koyama</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author M.Koyama
 * @version $Revision 1.1 2005/10/28
 */
public class SessionAsStoragePlanDateRet extends SessionRet
{
	//#CM40732
	/**
	 * call <CODE>find(AsScheduleParameter param)</CODE> method to do search<BR>
	 * set any qty retrieved by <CODE>find(AsScheduleParameter param)</CODE> method<BR>
	 * and it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param conn instance to store database connection
	 * @param stParam      AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public SessionAsStoragePlanDateRet(Connection conn, AsScheduleParameter stParam)throws Exception
	{
		wConn = conn;
		find(stParam);
	}
	
	//#CM40733
	/**
	 * Return search result of <CODE>DNWORKINFO</CODE>. 
	 * <DIR>
	 * <search result>
	 * storage plan date<BR>
	 * </DIR>
	 * @return DNWORKINFO search result
	 */
	public Parameter[] getEntities()
	{
		AsScheduleParameter[] resultArray = null;
		WorkingInformation[] rData = null ;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{			
			try
			{
				rData = (WorkingInformation[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (AsScheduleParameter[]) convertToParams(rData);
			} catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM40734
	/**
	 * execute SQL based on input parameter<BR>
	 * Maintain retrieved <code>WorkingInformationFinder</code> as an instance variable. <BR>
	 * it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param stParam AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public void find(AsScheduleParameter stParam) throws Exception
	{
			WorkingInformationSearchKey sKey = new WorkingInformationSearchKey();
			//#CM40735
			//do search
			//#CM40736
			//consignor code
			if (!StringUtil.isBlank(stParam.getConsignorCode()))
			{
				sKey.setConsignorCode(stParam.getConsignorCode());
			}
			//#CM40737
			// When Storage Plan Date is set
			if (!StringUtil.isBlank(stParam.getPlanDate()))
			{
				sKey.setPlanDate(stParam.getPlanDate());
			}
			//#CM40738
			// job type is storage
			sKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
			//#CM40739
			// status flag is standby
			sKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			
			//#CM40740
			// Set the order of acquisition. 
			sKey.setPlanDateCollect("DISTINCT");
			//#CM40741
			// set group by clause
			sKey.setPlanDateGroup(1);
			//#CM40742
			// set sorting order
			sKey.setPlanDateOrder(1, true);
			
			wFinder = new WorkingInformationFinder(wConn);
			//#CM40743
			//open cursor
			wFinder.open();
			int count = wFinder.search(sKey);
			wLength = count;
			wCurrent = 0;
	}
	
	//#CM40744
	/**
	 * This class converts the Working Information entity into the AsScheduleParameter parameter. 
	 * @param ety entity retrieved
	 * @return <CODE>AsScheduleParameter</CODE> class which set work information
	 */
	private Parameter[] convertToParams(Entity[] ety)
	{
	
		WorkingInformation[] rData = (WorkingInformation[]) ety;
		if (rData == null || rData.length==0)
		{	
		 	return null;
		}
		AsScheduleParameter[] stParam = new AsScheduleParameter[rData.length];
			for (int i = 0; i < rData.length; i++)
			{
				stParam[i] = new AsScheduleParameter();				
				stParam[i].setPlanDate(rData[i].getPlanDate()); //入荷予定日
			
			}
		
		return stParam;
	}
}
//#CM40745
//end of class
