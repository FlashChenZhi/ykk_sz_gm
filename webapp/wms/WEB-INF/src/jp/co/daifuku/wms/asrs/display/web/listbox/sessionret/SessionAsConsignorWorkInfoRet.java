// $Id: SessionAsConsignorWorkInfoRet.java,v 1.2 2006/10/26 08:02:34 suresh Exp $

//#CM40453
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

//#CM40454
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * This class is used to search data for ASRS Consignor list box<BR>
 * Receive the search condition as a parameter, and retrieve the consignor list. <BR>
 * in addition, keep the instance of this class in a session
 * delete from session after use<BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.search process(<CODE>SessionAsConsignoerRet(Connection conn,AsScheduleParameter param)</CODE>method )<BR>
 * <DIR>
 *   executed during listbox screen initialization<BR>
 *   <CODE>find(AsScheduleParameter param)</CODE> Call work method information is retrieved. <BR>
 * <BR>
 *   <search condition> not mandatory<BR>
 *   <DIR>
 *     consignor code<BR>
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
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:02:34 $
 * @author  $Author: suresh $
 */

public class SessionAsConsignorWorkInfoRet extends SessionRet
{
	//#CM40455
	// Class fields --------------------------------------------------

	//#CM40456
	// Class variables -----------------------------------------------

	//#CM40457
	// Class method --------------------------------------------------
	//#CM40458
	/**
	 * returns the version of this class
	 * @return version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:02:34 $");
	}

	//#CM40459
	/**
	 * call <CODE>find(AsScheduleParameter param)</CODE> method to do search<BR>
	 * set any qty retrieved by <CODE>find(AsScheduleParameter param)</CODE> method<BR>
	 * and it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param conn  instance to store database connection
	 * @param param AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public SessionAsConsignorWorkInfoRet(Connection conn, AsScheduleParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	//#CM40460
	// Public methods ------------------------------------------------
	//#CM40461
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
				resultArray = convertToAsControlParams(temp);
			}
			catch (Exception e)
			{
				//#CM40462
				//Drop the error to the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM40463
	// Package methods -----------------------------------------------

	//#CM40464
	// Protected methods ---------------------------------------------

	//#CM40465
	// Private methods -----------------------------------------------
	//#CM40466
	/**
	 * execute SQL based on input parameter<BR>
	 * Maintain retrieved <code>StockFinder</code> as an instance variable. <BR>
	 * it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param param AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	private void find(AsScheduleParameter param) throws Exception
	{	

		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		//#CM40467
		// do search
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		else
		{
			skey.setConsignorCode("","IS NOT NULL");
		}

		//#CM40468
		// status flag is standby
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		//#CM40469
		// job type is storage
		skey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		
		//#CM40470
		// set group by clause
		skey.setConsignorCodeGroup(1);
		skey.setConsignorNameGroup(2);

		skey.setConsignorCodeCollect("");
		skey.setConsignorNameCollect("");
		
		//#CM40471
		// set sorting order
		skey.setConsignorCodeOrder(1, true);
		skey.setConsignorNameOrder(2, true);
		//#CM40472
		//WFinder is an instance variable of parents class SessionRet. 
		wFinder = new WorkingInformationFinder(wConn);
		//#CM40473
		// open cursor
		wFinder.open();
		int count = ((WorkingInformationFinder)wFinder).search(skey);
		//#CM40474
		// Initialization. WLength is an instance variable of parents class SessionRet. 
		wLength = count;
		wCurrent = 0;
	}
	
	//#CM40475
	/**
	 * This class converts the Working Information entity into the AsScheduleParameter parameter. 
	 * @param entityView entity retrieved
	 * @return <CODE>AsScheduleParameter</CODE> class which set work information
	 */
	private AsScheduleParameter[] convertToAsControlParams(WorkingInformation[] entityView)
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
//#CM40476
//end of class
