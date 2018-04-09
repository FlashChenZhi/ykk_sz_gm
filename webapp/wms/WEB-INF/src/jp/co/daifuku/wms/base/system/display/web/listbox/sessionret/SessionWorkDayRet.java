//#CM693322
//$Id: SessionWorkDayRet.java,v 1.2 2006/11/13 08:21:01 suresh Exp $

//#CM693323
/*
 * Created on Aug 23, 2004
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.listbox.sessionret;
import java.sql.Connection;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultSearchKey;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM693324
/**
 * Designer : Muneendra <BR>
 * Maker : Muneendra <BR>
 * <BR>
 * Allow this class to search for the Work Date List through the Worker result info and display it. <BR>
 * Maintain the instance in the session to use this class.
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionWorkDayRet(Connection conn,	SystemParameter param)</CODE>method)<BR>
 * <DIR>
 *   Execute this when displaying the listbox screen as default. <BR>
 *   Invoke the <CODE>find(SystemParameter param)</CODE> method and search for the Worker result info. <BR>
 * <BR>
 *   <Search conditions> *Mandatory Input<BR>
 *   <DIR>
 *     *Work date<BR>
 *     *Status flag other than Deleted <BR>
 *   </DIR>
 *   <Search table> <BR>
 *   <DIR>
 *     DNWORKERRESULT<BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.Process for displaying(<CODE>getEntities()</CODE>method)<BR>
 * <BR>
 * <DIR>
 *   Obtain the data to display on the screen.<BR>
 *   1.Obtain the display info that was obtained in the search process.<BR>
 *   Set the search result in the array of the work status and return it.<BR>
 * <BR>
 *   <Field items to displayed>
 *   <DIR>
 *     Work date<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2008/08/23</TD><TD>Muneendra</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:01 $
 * @author  $Author: suresh $
 */
public class SessionWorkDayRet extends SessionRet
{
	
	//#CM693325
	// Class fields --------------------------------------------------

	//#CM693326
	// Class variables -----------------------------------------------

	//#CM693327
	// Class method --------------------------------------------------
	//#CM693328
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 08:21:01 $");
	}

	//#CM693329
	// Constructors --------------------------------------------------
	//#CM693330
	/**
	 * Invoke the <CODE>find(SystemParameter param)</CODE> method for searching.<BR>
	 * Allow the <CODE>find(SystemParameter param)</CODE> method to set the count of obtained data.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result.<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the <code>SystemParameter</code> search result.
	 * @throws Exception Announce when some error occurs during loading data.
	 */
	public SessionWorkDayRet(Connection conn, SystemParameter param) throws Exception
	{
		this.wConn = conn;
		
		//#CM693331
		// Search
		find(param);
	}
	
	//#CM693332
	// Public methods ------------------------------------------------
	//#CM693333
	/**
	 * Return the search result of <CODE>DNWORKERRESULT</CODE>.
	 * <DIR>
	 * <Search result>
	 * - Consignor Code<BR>
	 * - Consignor Name<BR>
	 * </DIR>
	 * @return Search result of DnWorkerResult
	 */
	public WorkerResult[] getEntities()	
	{
		WorkerResult[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{			
			try
			{
				resultArray = (WorkerResult[]) ((WorkerResultFinder)wFinder).getEntities(wStartpoint, wEndpoint);
			}
			catch (Exception e)
			{
				//#CM693334
				//Record the error in the log file.
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}
	
	//#CM693335
	// Package methods -----------------------------------------------

	//#CM693336
	// Protected methods ---------------------------------------------

	//#CM693337
	// Private methods -----------------------------------------------
	//#CM693338
	/**
	 * Allow this method to obtain the search conditions from parameter and search for the Worker info.<BR>
	 * 
	 * @param param   Parameter to obtain search conditions.
	 * @throws Exception Announce when some error occurs during loading data.
	 */
	private void find(SystemParameter param) throws Exception
	{
		int count = 0;

		WorkerResultSearchKey workerSearchKey= new WorkerResultSearchKey();
		//#CM693339
		// Set the search conditions.
		//#CM693340
		// - If the Shipping Date is set
		if(!StringUtil.isBlank(param.getWorkDate()))
		{
			workerSearchKey.setWorkDate(param.getWorkDate());
		}
		//#CM693341
		// - If the Start Shipping Date or the End Shipping Date is set
		else
		{
			//#CM693342
			// - If the Start Shipping Date is set
			if(!StringUtil.isBlank(param.getFromWorkDate()))
			{
				workerSearchKey.setWorkDate(param.getFromWorkDate());
			}
			//#CM693343
			// - If the End Shipping Date is set
			if(!StringUtil.isBlank(param.getToWorkDate()))
			{
				workerSearchKey.setWorkDate(param.getToWorkDate());
			}
		}
		if(!StringUtil.isBlank(param.getSelectWorkDetail()))
		{
			if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_INSTOCK))
			{
				//#CM693344
				// Receiving
				workerSearchKey.setJobType(WorkerResult.JOB_TYPE_INSTOCK);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_STORAGE))
			{
				//#CM693345
				// Storage
				workerSearchKey.setJobType(WorkerResult.JOB_TYPE_STORAGE);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_RETRIEVAL))
			{
				//#CM693346
				// Picking
				workerSearchKey.setJobType(WorkerResult.JOB_TYPE_RETRIEVAL);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_SORTING))
			{
				//#CM693347
				// Sorting
				workerSearchKey.setJobType(WorkerResult.JOB_TYPE_SORTING);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_SHIPPING))
			{
				//#CM693348
				// Shipping
				workerSearchKey.setJobType(WorkerResult.JOB_TYPE_SHIPINSPECTION);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_MOVEMENT_STORAGE))
			{
				//#CM693349
				// Relocation for Storage
				workerSearchKey.setJobType(WorkerResult.JOB_TYPE_MOVEMENT_STORAGE);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_MOVEMENT_RETRIEVAL))
			{
				//#CM693350
				// Relocation for Retrieval
				workerSearchKey.setJobType(WorkerResult.JOB_TYPE_MOVEMENT_RETRIEVAL);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDSTORAGE))
			{
				//#CM693351
				// Unplanned Storage
				workerSearchKey.setJobType(WorkerResult.JOB_TYPE_EX_STORAGE);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDRETRIEVAL))
			{
				//#CM693352
				// Unplanned Picking
				workerSearchKey.setJobType(WorkerResult.JOB_TYPE_EX_RETRIEVAL);
			}
			else if (param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_INVENTORY))
			{
				//#CM693353
				// Inventory Check
				workerSearchKey.setJobType(WorkerResult.JOB_TYPE_INVENTORY);
			}
			else if (param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_INVENTORY_INCREASE))
			{
				//#CM693354
				// Increase Inventory Check
				workerSearchKey.setJobType(WorkerResult.JOB_TYPE_INVENTORY_PLUS);
			}
			else if (param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_INVENTORY_DECREASE))
			{
				//#CM693355
				// Decrease Inventory Check
				workerSearchKey.setJobType(WorkerResult.JOB_TYPE_INVENTORY_MINUS);
			}
			else if (param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_MAINTENANCE_INCREASE))
			{
				//#CM693356
				// Increase in Maintenance
				workerSearchKey.setJobType(WorkerResult.JOB_TYPE_MAINTENANCE_PLUS);
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_MAINTENANCE_DECREASE))
			{
				//#CM693357
				// Increase in Maintenance
				workerSearchKey.setJobType(WorkerResult.JOB_TYPE_MAINTENANCE_MINUS);
			}
		}

		workerSearchKey.setWorkDateOrder(1, true);
		workerSearchKey.setWorkDateGroup(1);
		workerSearchKey.setWorkDateCollect("");
		
		wFinder = new WorkerResultFinder(wConn);
		//#CM693358
		// Open the cursor.
		wFinder.open();
		//#CM693359
		// Execute the search.
		count = ((WorkerResultFinder) wFinder).search(workerSearchKey);
		//#CM693360
		// Initialize.
		wLength = count;
		wCurrent = 0;
	}
}
//#CM693361
//end of class
