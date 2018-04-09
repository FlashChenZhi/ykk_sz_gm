// $Id: SessionWorkerResultRet.java,v 1.2 2006/11/13 08:21:00 suresh Exp $

//#CM693402
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
import jp.co.daifuku.wms.base.dbhandler.WorkerResultFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM693403
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * Allow this class to search for the Worker result info and display it.<BR>
 * Maintain the instance in the session to use this class.
 * Delete it from the session after use.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionWorkerResultRet(Connection conn, SystemParameter param)</CODE>method)<BR>
 * <DIR>
 *   Execute this when displaying the listbox screen as default.<BR>
 *   Invoke the <CODE>find(SystemParameter param)</CODE> method and search for the Worker result info.<BR>
 * <BR>
 *   <Search conditions>
 *   <DIR>
 *     Worker Code<BR>
 *   </DIR>
 *   <Search table>
 *   <DIR>
 *     DNWORKERRESULT<BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.Process for displaying(<CODE>getEntities()</CODE>method)<BR>
 * <BR>
 * <DIR>
 *   Obtain the data to display on the screen.<BR>
 *   Obtain the display info that was obtained in the search process.<BR>
 *   Set the search result in the <CODE>WorkerResult</CODE> array and return it.<BR>
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
 * <TR><TD>2004/08/24</TD><TD>Y.Okamura</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:00 $
 * @author  $Author: suresh $
 */
public class SessionWorkerResultRet extends SessionRet
{
	//#CM693404
	// Class fields --------------------------------------------------

	//#CM693405
	// Class variables -----------------------------------------------

	//#CM693406
	// Class method --------------------------------------------------
	//#CM693407
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 08:21:00 $");
	}

	//#CM693408
	// Constructors --------------------------------------------------
	//#CM693409
	/**
	 * Invoke the <CODE>find(SystemParameter param)</CODE> method for searching.<BR>
	 * Allow the <CODE>find(SystemParameter param)</CODE> method to set the count of obtained data.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result.<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the <code>SystemParameter</code> search result.
	 * @throws Exception Announce when some error occurs during loading data.
	 */
	public SessionWorkerResultRet(Connection conn, SystemParameter param) throws Exception
	{
		this.wConn = conn;
		
		//#CM693410
		// Search
		find(param);
	}

	//#CM693411
	// Public methods ------------------------------------------------
	//#CM693412
	/**
	 * Return the designated number of the search results of the Worker result info.<BR>
	 * Allow this method to execute the following processes.<BR>
	 * <BR>
	 * <DIR>
	 *   1.Calculate to specify the count of display data to be obtained.<BR>
	 *   2.Obtain the Worker info . from the result set.<BR>
	 *   3.Obtain the display data from the Worker result info and set it in <CODE>WorkerResult</CODE>.<BR>
	 *   4.Return information to be displayed.<BR>
	 * </DIR>
	 * 
	 * @return <CODE>WorkerResult</CODE> class that contains information to be displayed.
	 */
	public WorkerResult[] getEntities()
	{
		WorkerResult[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				//#CM693413
				// Obtain the search result.
				resultArray = (WorkerResult[]) ((WorkerResultFinder) wFinder).getEntities(wStartpoint, wEndpoint);
			}
			catch (Exception e)
			{
				//#CM693414
				//Record the error in the log file.
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;

		return resultArray;
	}

	//#CM693415
	// Package methods -----------------------------------------------

	//#CM693416
	// Protected methods ---------------------------------------------

	//#CM693417
	// Private methods -----------------------------------------------
	//#CM693418
	/**
	 * Allow this method to obtain the search conditions from parameter and search for the Worker result info.<BR>
	 * 
	 * @param param   Parameter to obtain search conditions.
	 * @throws Exception Announce when some error occurs during loading data.
	 */
	private void find(SystemParameter param) throws Exception
	{
		int count = 0;

		WorkerResultSearchKey workerResultKey = new WorkerResultSearchKey();
		//#CM693419
		// Set the search conditions.
		if(!StringUtil.isBlank(param.getSelectWorkDetail()))
		{
			if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_INSTOCK))
			{
				//#CM693420
				// Receiving
				workerResultKey.setJobType(WorkerResult.JOB_TYPE_INSTOCK);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_STORAGE))
			{
				//#CM693421
				// Storage
				workerResultKey.setJobType(WorkerResult.JOB_TYPE_STORAGE);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_RETRIEVAL))
			{
				//#CM693422
				// Picking
				workerResultKey.setJobType(WorkerResult.JOB_TYPE_RETRIEVAL);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_SORTING))
			{
				//#CM693423
				// Sorting
				workerResultKey.setJobType(WorkerResult.JOB_TYPE_SORTING);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_SHIPPING))
			{
				//#CM693424
				// Shipping
				workerResultKey.setJobType(WorkerResult.JOB_TYPE_SHIPINSPECTION);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_MOVEMENT_STORAGE))
			{
				//#CM693425
				// Relocation for Storage
				workerResultKey.setJobType(WorkerResult.JOB_TYPE_MOVEMENT_STORAGE);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_MOVEMENT_RETRIEVAL))
			{
				//#CM693426
				// Relocation for Retrieval
				workerResultKey.setJobType(WorkerResult.JOB_TYPE_MOVEMENT_RETRIEVAL);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDSTORAGE))
			{
				//#CM693427
				// Unplanned Storage
				workerResultKey.setJobType(WorkerResult.JOB_TYPE_EX_STORAGE);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDRETRIEVAL))
			{
				//#CM693428
				// Unplanned Picking
				workerResultKey.setJobType(WorkerResult.JOB_TYPE_EX_RETRIEVAL);
			}
			else if (param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_INVENTORY))
			{
				//#CM693429
				// Inventory Check
				workerResultKey.setJobType(WorkerResult.JOB_TYPE_INVENTORY);
			}
			else if (param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_INVENTORY_INCREASE))
			{
				//#CM693430
				// Increase Inventory Check
				workerResultKey.setJobType(WorkerResult.JOB_TYPE_INVENTORY_PLUS);
			}
			else if (param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_INVENTORY_DECREASE))
			{
				//#CM693431
				// Decrease Inventory Check
				workerResultKey.setJobType(WorkerResult.JOB_TYPE_INVENTORY_MINUS);
			}
			else if (param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_MAINTENANCE_INCREASE))
			{
				//#CM693432
				// Increase in Maintenance
				workerResultKey.setJobType(WorkerResult.JOB_TYPE_MAINTENANCE_PLUS);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_MAINTENANCE_DECREASE))
			{
				//#CM693433
				// Increase in Maintenance
				workerResultKey.setJobType(WorkerResult.JOB_TYPE_MAINTENANCE_MINUS);
			}
		}
		if(!StringUtil.isBlank(param.getFromWorkDate()))
		{
			workerResultKey.setWorkDate(param.getFromWorkDate(), ">=");
		}
		if(!StringUtil.isBlank(param.getToWorkDate()))
		{
			workerResultKey.setWorkDate(param.getToWorkDate(), "<=");
		}
		if(!StringUtil.isBlank(param.getWorkerCode()))
		{
			workerResultKey.setWorkerCode(param.getWorkerCode());
		}
		
		//#CM693434
		// Set the sorting order.
		workerResultKey.setWorkerCodeOrder(1, true);
		workerResultKey.setWorkerNameOrder(2, true);
		//#CM693435
		// Set the Sequence of group.
		workerResultKey.setWorkerCodeGroup(1);
		workerResultKey.setWorkerNameGroup(2);
		//#CM693436
		// Set the obtaining sequence.
		workerResultKey.setWorkerCodeCollect("");
		workerResultKey.setWorkerNameCollect("");

		wFinder = new WorkerResultFinder(wConn);
		//#CM693437
		// Open the cursor.
		wFinder.open();
		//#CM693438
		// Execute the search.
		count = ((WorkerResultFinder) wFinder).search(workerResultKey);
		//#CM693439
		// Initialize.
		wLength = count;
		wCurrent = 0;
	}

}
//#CM693440
//end of class
