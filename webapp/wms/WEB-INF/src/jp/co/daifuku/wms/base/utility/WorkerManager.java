package jp.co.daifuku.wms.base.utility;

//#CM687051
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.dbhandler.WorkerHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerSearchKey;
import jp.co.daifuku.wms.base.entity.Worker;

//#CM687052
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * <DIR>
 *   This class operates the DmWorker table<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/07/25</TD><TD>Y.Okamura</TD><TD>v.2.4 New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 07:13:29 $
 * @author  $Author: suresh $
 */
public class WorkerManager 
{
	//#CM687053
	// Class variables -----------------------------------------------
	//#CM687054
	/**
	 * connection
	 */
	private Connection wConn = null;
	
	//#CM687055
	/**
	 * Worker entity
	 */
	private Worker wWorker = null;
	
	//#CM687056
	// Class method --------------------------------------------------
	//#CM687057
	/**
	 * Return the version of this class
	 * @return version and timestamp
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 07:13:29 $");
	}
	
	//#CM687058
	// Constructors --------------------------------------------------
	//#CM687059
	/**
	 * create instance of this class<BR>
	 * 
	 * @param conn database connection object
	 */
	public WorkerManager(Connection conn)
	{
		wConn = conn;
		
	}
	
	//#CM687060
	// Public methods ------------------------------------------------
	//#CM687061
	/**
	 * Search worker master and find
	 * whether the worker code supplied as a parameter exist or not<BR>
	 * <BR>
	 * Search condition :
	 * <DIR>
	 *   worker code
	 *   delete flag:available
	 * </DIR>
	 * @param pWorkerCode worker code
	 * @return true:exist false:does not exist
	 * @throws ReadWriteException if abnormal error occurs in database access
	 */
	public boolean isExistWorkerCode(String pWorkerCode) throws ReadWriteException
	{
		return isExistWorkerCode(pWorkerCode, true);
		
	}
	
	//#CM687062
	/**
	 * Search worker master and return whether target data exist or not<BR>
	 * Specify the delete flag in the argument<BR>
	 * If true search only the data with delete flag "available". if false, search everything<BR>
	 * Return true if target data exist. else false.<BR>
	 * <BR>
	 * Search condition:
	 * <DIR>
	 *   worker code<BR>
	 *   delete flag (optional) <BR>
	 * </DIR>
	 * 
	 * @param pWorkerCode worker cdde
	 * @param pOperationFlag delete flag to search true:available false:all
	 * @throws ReadWriteException if abnormal error occurs in database access
	 * @return true:target data exist false:target data does not exist
	 */
	public boolean isExistWorkerCode(String pWorkerCode, boolean pOperationFlag) throws ReadWriteException
	{
		WorkerSearchKey workerKey = new WorkerSearchKey();
		WorkerHandler workerHandle = new WorkerHandler(wConn);
		
		workerKey.setWorkerCode(pWorkerCode);
		if (pOperationFlag)
		{
			//#CM687063
			// delete flag:available
			workerKey.setDeleteFlag(Worker.DELETE_FLAG_OPERATION);
		}
		
		
		if (workerHandle.count(workerKey) > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	//#CM687064
	/**
	 * Search worker master for the worker code supplied as argument
	 * and return the corresponding worker name<BR>
	 * <BR>
	 * Search condition:
	 * <DIR>
	 *   worker code
	 * </DIR>
	 * <U>delete flag is not included in the search condition</U>
	 * @param pWorkerCode worker code
	 * @return worker name
	 * @throws ReadWriteException if abnormal error occurs in database access
	 */
	public String getName(String pWorkerCode) throws ReadWriteException
	{
		WorkerSearchKey workerKey = new WorkerSearchKey();
		WorkerHandler workerHandle = new WorkerHandler(wConn);
		
		workerKey.setWorkerCode(pWorkerCode);
		workerKey.setNameCollect("");
		
		Worker work = null;
		try
		{
			work = (Worker) workerHandle.findPrimary(workerKey);
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		
		String workerName = "";
		if (work != null)
		{
			workerName = work.getName();
		}
		
		work = null;
		
		return workerName;
		
	}
	
	//#CM687065
	/**
	 * Search worker master<BR>
	 * Specify the delete flag in the argument<BR>
	 * If true search only the data with delete flag "available". if false, search everything<BR>
	 * Return true if target data exist. else false.<BR>
	 * <BR>
	 * Search condition:
	 * <DIR>
	 *   worker code<BR>
	 *   delete flag (optional)<BR>
	 * </DIR>
	 * 
	 * @param pWorkerCode worker code
	 * @param pOperationFlag delete flag to search true:available false:all
	 * @throws ReadWriteException if abnormal error occurs in database access
	 * @return true:target data exist false:target data does not exist
	 */
	public boolean getWorkerInstance(String pWorkerCode, boolean pOperationFlag) throws ReadWriteException
	{
		WorkerSearchKey workerKey = new WorkerSearchKey();
		WorkerHandler workerHandle = new WorkerHandler(wConn);
		
		workerKey.setWorkerCode(pWorkerCode);
		if (pOperationFlag)
		{
			//#CM687066
			// delete flag:available
			workerKey.setDeleteFlag(Worker.DELETE_FLAG_OPERATION);
		}
		
		try
		{
			wWorker = (Worker) workerHandle.findPrimary(workerKey);
			
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		
		if (wWorker == null)
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}
	
	//#CM687067
	/**
	 * Fetch worker name<BR>
	 * Specify worker code and fetch instance before using this method<BR>
	 * @return worker name
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	public String getName() throws ScheduleException
	{
		if (wWorker == null)
		{
			//#CM687068
			// 6006038=No matching data is found.
			throw new ScheduleException("6006038");
		}
		
		return wWorker.getName();
		
	}
	
	//#CM687069
	/**
	 * Fetch the job type<BR>
	 * Specify worker code and fetch instance before using this method<BR>
	 * @return job type
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	public String getWorkerJobType() throws ScheduleException
	{
		if (wWorker == null)
		{
			//#CM687070
			// 6006038=No matching data is found.
			throw new ScheduleException("6006038");
		}
		
		return wWorker.getWorkerJobType();
		
	}
	
	//#CM687071
	/**
	 * Fetch furigana<BR>
	 * Specify worker code and fetch instance before using this method<BR>
	 * @return furigana
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	public String getFurigana() throws ScheduleException
	{
		if (wWorker == null)
		{
			//#CM687072
			// 6006038=No matching data is found.
			throw new ScheduleException("6006038");
		}
		
		return wWorker.getFurigana();
		
	}
	
	//#CM687073
	/**
	 * Fetch gender<BR>
	 * Specify worker code and fetch instance before using this method<BR>
	 * @return gender
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	public String getSex() throws ScheduleException
	{
		if (wWorker == null)
		{
			//#CM687074
			// 6006038=No matching data is found.
			throw new ScheduleException("6006038");
		}
		
		return wWorker.getSex();
		
	}
	
	//#CM687075
	/**
	 * Fetch access privilege<BR>
	 * Specify worker code and fetch instance before using this method<BR>
	 * @return access privilege
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	public String getAccessAuthority() throws ScheduleException
	{
		if (wWorker == null)
		{
			//#CM687076
			// 6006038=No matching data is found.
			throw new ScheduleException("6006038");
		}
		
		return wWorker.getAccessAuthority();
		
	}
	
	//#CM687077
	/**
	 * Fetch password<BR>
	 * Specify worker code and fetch instance before using this method<BR>
	 * @return password
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	public String getPassword() throws ScheduleException
	{
		if (wWorker == null)
		{
			//#CM687078
			// 6006038=No matching data is found.
			throw new ScheduleException("6006038");
		}
		
		return wWorker.getPassword();
		
	}
	
	//#CM687079
	/**
	 * Fetch memo 1<BR>
	 * Specify worker code and fetch instance before using this method<BR>
	 * @return memo 1
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	public String getMemo1() throws ScheduleException
	{
		if (wWorker == null)
		{
			//#CM687080
			// 6006038=No matching data is found.
			throw new ScheduleException("6006038");
		}
		
		return wWorker.getMemo1();
		
	}
	
	//#CM687081
	/**
	 * Fetch memo 2<BR>
	 * Specify worker code and fetch instance before using this method<BR>
	 * @return memo 2
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	public String getMemo2() throws ScheduleException
	{
		if (wWorker == null)
		{
			//#CM687082
			// 6006038=No matching data is found.
			throw new ScheduleException("6006038");
		}
		
		return wWorker.getMemo2();
		
	}
	
	//#CM687083
	/**
	 * Fetch delete flag<BR>
	 * Specify worker code and fetch instance before using this method<BR>
	 * @return delete flag
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	public String getDeleteFlag() throws ScheduleException
	{
		if (wWorker == null)
		{
			//#CM687084
			// 6006038=No matching data is found.
			throw new ScheduleException("6006038");
		}
		
		return wWorker.getDeleteFlag();
		
	}
	
	//#CM687085
	/**
	 * Fetch registered date/time<BR>
	 * Specify worker code and fetch instance before using this method<BR>
	 * @return date/time
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	public Date getRegistDate() throws ScheduleException
	{
		if (wWorker == null)
		{
			//#CM687086
			// 6006038=No matching data is found.
			throw new ScheduleException("6006038");
		}
		
		return wWorker.getRegistDate();
		
	}
	
	//#CM687087
	/**
	 * Fetch registering process name<BR>
	 * Specify worker code and fetch instance before using this method<BR>
	 * @return registering process name
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	public String getRegistPname() throws ScheduleException
	{
		if (wWorker == null)
		{
			//#CM687088
			// 6006038=No matching data is found.
			throw new ScheduleException("6006038");
		}
		
		return wWorker.getRegistPname();
		
	}
	
	//#CM687089
	/**
	 * Fetch last update date/time<BR>
	 * Specify worker code and fetch instance before using this method<BR>
	 * @return last update date/time
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	public Date getLastUpdateDate() throws ScheduleException
	{
		if (wWorker == null)
		{
			//#CM687090
			// 6006038=No matching data is found.
			throw new ScheduleException("6006038");
		}
		
		return wWorker.getLastUpdateDate();
		
	}
	
	//#CM687091
	/**
	 * Fetch last update process name<BR>
	 * Specify worker code and fetch instance before using this method<BR>
	 * @return last update process name
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	public String getLastUpdatePname() throws ScheduleException
	{
		if (wWorker == null)
		{
			//#CM687092
			// 6006038=No matching data is found.
			throw new ScheduleException("6006038");
		}
		
		return wWorker.getLastUpdatePname();
		
	}
	
	//#CM687093
	// Package methods -----------------------------------------------

	//#CM687094
	// Protected methods ---------------------------------------------
	
	//#CM687095
	// Private methods -----------------------------------------------
	
}
//#CM687096
//end of class
