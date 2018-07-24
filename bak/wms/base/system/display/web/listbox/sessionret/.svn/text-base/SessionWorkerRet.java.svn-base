// $Id: SessionWorkerRet.java,v 1.2 2006/11/13 08:21:00 suresh Exp $

//#CM693441
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.listbox.sessionret;
import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkerFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkerSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Worker;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM693442
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * Allow this class to search for the Worker info and display it.<BR>
 * Maintain the instance in the session to use this class.
 * Delete it from the session after use.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionWorkerRet(Connection conn, SystemParameter param)</CODE>method)<BR>
 * <DIR>
 *   Execute this when displaying the listbox screen as default.<BR>
 *   Invoke <CODE>find(SystemParameter param)</CODE>method and search for Worker info.<BR>
 * <BR>
 *   <Search conditions>
 *   <DIR>
 *     Worker Code<BR>
 *   </DIR>
 *   <Search table>
 *   <DIR>
 *     DMWORKER<BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.Process for displaying(<CODE>getEntities()</CODE>method)<BR>
 * <BR>
 * <DIR>
 *   Obtain the data to display on the screen.<BR>
 *   Obtain the display info that was obtained in the search process.<BR>
 *   Set the search result in the <CODE>Worker</CODE> array and return it.<BR>
 * <BR>
 *   <Field items to displayed>
 *   <DIR>
 *     Worker Code<BR>
 *     Person Name<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/20</TD><TD>Y.Okamura</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:00 $
 * @author  $Author: suresh $
 */
public class SessionWorkerRet extends SessionRet
{
	//#CM693443
	// Class fields --------------------------------------------------

	//#CM693444
	// Class variables -----------------------------------------------

	//#CM693445
	// Class method --------------------------------------------------
	//#CM693446
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 08:21:00 $");
	}

	//#CM693447
	// Constructors --------------------------------------------------
	//#CM693448
	/**
	 * Invoke the <CODE>find(SystemParameter param)</CODE> method for searching.<BR>
	 * Allow the <CODE>find(SystemParameter param)</CODE> method to set the count of obtained data.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result.<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the <code>SystemParameter</code> search result.
	 * @throws Exception Announce when some error occurs during loading data.
	 */
	public SessionWorkerRet(Connection conn, SystemParameter param) throws Exception
	{
		this.wConn = conn;
		
		//#CM693449
		// Search
		find(param);
	}

	//#CM693450
	// Public methods ------------------------------------------------
	//#CM693451
	/**
	 * Return the designated number of the search results of the Worker info.<BR>
	 * Allow this method to execute the following processes.<BR>
	 * <BR>
	 * <DIR>
	 *   1.Calculate to specify the count of display data to be obtained.<BR>
	 *   2.Obtain the Worker info . from the result set.<BR>
	 *   3.Obtain the display data from the Worker info and set it in <CODE>Worder</CODE>.<BR>
	 *   4.Return information to be displayed.<BR>
	 * </DIR>
	 * 
	 * @return <CODE>Worker</CODE> class that contains information to be displayed.
	 */
	public Worker[] getEntities()
	{
		Worker[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				//#CM693452
				// Obtain the search result.
				resultArray = (Worker[]) ((WorkerFinder) wFinder).getEntities(wStartpoint, wEndpoint);
			}
			catch (Exception e)
			{
				//#CM693453
				//Record the error in the log file.
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;

		return resultArray;
	}

	//#CM693454
	// Package methods -----------------------------------------------

	//#CM693455
	// Protected methods ---------------------------------------------

	//#CM693456
	// Private methods -----------------------------------------------
	//#CM693457
	/**
	 * Allow this method to obtain the search conditions from parameter and search for the Worker info.<BR>
	 * 
	 * @param param   Parameter to obtain search conditions.
	 * @throws Exception Announce when some error occurs during loading data.
	 */
	private void find(SystemParameter param) throws Exception
	{
		int count = 0;

		WorkerSearchKey workerKey = new WorkerSearchKey();
		//#CM693458
		// Set the search conditions.
		if(!StringUtil.isBlank(param.getWorkerCode()))
		{
			workerKey.setWorkerCode(param.getWorkerCode());
		}
		workerKey.setWorkerCodeOrder(1, true);
		workerKey.setNameOrder(2, true);

		wFinder = new WorkerFinder(wConn);
		//#CM693459
		// Open the cursor.
		wFinder.open();
		//#CM693460
		// Execute the search.
		count = ((WorkerFinder) wFinder).search(workerKey);
		//#CM693461
		// Initialize.
		wLength = count;
		wCurrent = 0;
	}

}
//#CM693462
//end of class
