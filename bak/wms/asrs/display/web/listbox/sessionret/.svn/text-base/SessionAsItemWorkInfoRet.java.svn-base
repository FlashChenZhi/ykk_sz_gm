// $Id: SessionAsItemWorkInfoRet.java,v 1.2 2006/10/26 07:47:55 suresh Exp $

//#CM40508
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
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM40509
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * The class to retrieve data for the ASRSItem code list list box. <BR>
 * Receive the search condition as a parameter, and retrieve work information. <BR>
 * in addition, keep the instance of this class in a session
 * delete from session after use<BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.search process(<CODE>SessionAsItemRet(Connection conn,AsScheduleParameter param)</CODE>method )<BR>
 * <DIR>
 *   executed during listbox screen initialization<BR>
 *   <CODE>find(AsScheduleParameter param)</CODE> Call work method information is retrieved. <BR>
 * <BR>
 *   <search condition> not mandatory<BR>
 *   <DIR>
 *     consignor code <BR>
 *     item code <BR>
 *     status flag is standby (0) <BR>
 *     job type is storage (02)<BR>
 *   </DIR>
 *   <search table><BR>
 *   <DIR>
 *     DNWORKINFO <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.display process(<CODE>getEntities()</CODE>method )<BR>
 * <BR>
 * <DIR>
 *   fetch data to display in screen<BR>
 *   1.fetch the display info from the search result<BR>
 *   Set search result in the work information array and return it. <BR>
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
 * @version $Revision: 1.2 $, $Date: 2006/10/26 07:47:55 $
 * @author  $Author: suresh $
 */
public class SessionAsItemWorkInfoRet extends SessionRet
{
	//#CM40510
	// Class fields --------------------------------------------------

	//#CM40511
	// Class variables -----------------------------------------------

	//#CM40512
	// Class method --------------------------------------------------
	//#CM40513
	/**
	 * returns the version of this class
	 * @return version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 07:47:55 $");
	}

	//#CM40514
	/**
	 * call <CODE>find(AsScheduleParameter param)</CODE> method to do search<BR>
	 * set any qty retrieved by <CODE>find(AsScheduleParameter param)</CODE> method<BR>
	 * and it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param conn  instance to store database connection
	 * @param param AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public SessionAsItemWorkInfoRet(Connection conn, AsScheduleParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	//#CM40515
	// Public methods ------------------------------------------------
	//#CM40516
	/**
	 * Return search result of <CODE>DNWORKINFO</CODE>. 
	 * @return DNWORKINFO search result
	 */
	public Parameter[] getEntities()
	{
		AsScheduleParameter[] resultArray = null;
		WorkingInformation temp[] = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{	
			try
			{	
				temp = (WorkingInformation[])((WorkingInformationFinder)wFinder).getEntities(wStartpoint, wEndpoint);
				resultArray = convertToWkinfoParams(temp);
			}
			catch (Exception e)
			{
				//#CM40517
				//Drop the error to the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}
	
	//#CM40518
	// Package methods -----------------------------------------------

	//#CM40519
	// Protected methods ---------------------------------------------

	//#CM40520
	// Private methods -----------------------------------------------
	//#CM40521
	/**
	 * execute SQL based on input parameter<BR>
	 * Maintain retrieved <code>StockFinder</code> as an instance variable. <BR>
	 * it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param param AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	private void find(AsScheduleParameter param) throws Exception
	{	
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey() ;
			
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode()) ;
		}
		
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			skey.setItemCode(param.getItemCode()) ;
		}

		if (!StringUtil.isBlank(param.getStartItemCode()))
		{
			skey.setItemCode(param.getStartItemCode(), ">=") ;
		}
			
		if (!StringUtil.isBlank(param.getEndItemCode()))
		{
			skey.setItemCode(param.getEndItemCode(),"<=") ;
		}
		//#CM40522
		// storage plan date
		if (!StringUtil.isBlank(param.getPlanDate()))
		{
			skey.setPlanDate(param.getPlanDate()) ;
		}

		//#CM40523
		// status flag is standby
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		//#CM40524
		// job type is storage
		skey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
				
		//#CM40525
		// if item code is not null
		skey.setConsignorCode("","IS NOT NULL");
		
		//#CM40526
		// set group by clause
		skey.setItemCodeGroup(1);
		skey.setItemName1Group(2);

		skey.setItemCodeCollect("");
		skey.setItemName1Collect("");
			
		//#CM40527
		// set sorting order
		skey.setItemCodeOrder(1, true);
		skey.setItemName1Order(2,true);
	
		wFinder = new WorkingInformationFinder(wConn);
		//#CM40528
		// open cursor
		wFinder.open();
		int count = ((WorkingInformationFinder)wFinder).search(skey);
		//#CM40529
		// initialize
		wLength = count;
		wCurrent = 0;
			
	}
	
	//#CM40530
	/**
	 * This class converts the Working Information entity into the AsScheduleParameter parameter. 
	 * @param wkinfo entity retrieved
	 * @return <CODE>AsScheduleParameter</CODE> class which set work information
	 */
	private AsScheduleParameter[] convertToWkinfoParams(WorkingInformation[] wkinfo)
	{
		AsScheduleParameter[] stParam = null;		
		if (wkinfo == null || wkinfo.length==0)
		{	
		 	return null;
		}
		stParam = new AsScheduleParameter[wkinfo.length];
		for (int i = 0; i < wkinfo.length; i++)
		{
				stParam[i] = new AsScheduleParameter();
				stParam[i].setItemCode(wkinfo[i].getItemCode());
				stParam[i].setItemName(wkinfo[i].getItemName1());
		}
			
		return stParam;
	}
}
//#CM40531
//end of class
