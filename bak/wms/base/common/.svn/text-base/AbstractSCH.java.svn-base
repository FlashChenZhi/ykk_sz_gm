//#CM642449
//$Id: AbstractSCH.java,v 1.3 2006/11/21 04:22:34 suresh Exp $
package jp.co.daifuku.wms.base.common;
//#CM642450
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.Worker;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.utility.WareNaviSystemManager;
import jp.co.daifuku.wms.base.utility.WorkerManager;

//#CM642451
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * Mount the WmsScheduler interface by running necessary process.<BR>
 * If necessary, override each class and mount individual behavior of the schedule processing.<BR>
 * 
 * Moreover, mount common processing in the schedule processing on this class. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/07/15</TD><TD>Y.Okamura</TD><TD>v.2.4 New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:22:34 $
 * @author  $Author: suresh $
 */
public abstract class AbstractSCH extends Object implements WmsScheduler
{
	//#CM642452
	// Class variables -----------------------------------------------
	//#CM642453
	/**
	 * The maximum value of total number of rows(The one that comma was edited for screen display)
	 */
	public static final String MAX_TOTAL_QTY_DISP = WmsFormatter.getNumFormat(WmsParam.MAX_TOTAL_QTY);

	//#CM642454
	/**
	 * List cell(Filtering area)The maximum display qty(The one that comma was edited for screen display)
	 */
	public static final String MAX_NUMBER_OF_DISP_DISP = WmsFormatter.getNumFormat(WmsParam.MAX_NUMBER_OF_DISP);

	//#CM642455
	/**
	 * Maximum value of Stock qty (The one that comma was edited for screen display)
	 */
	public static final String MAX_STOCK_QTY_DISP = WmsFormatter.getNumFormat(WmsParam.MAX_STOCK_QTY);
	
	//#CM642456
	/**
	 * The maximum qty acquired when SELECT sentence is printed by using HANDLER(The one that comma was edited for screen display)
	 */
	public static final String MAX_NUMBER_OF_SQL_FIND_DISP = WmsFormatter.getNumFormat(WmsParam.MAX_NUMBER_OF_SQL_FIND);

	//#CM642457
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM642458
	/**
	 * Message maintenance area<BR>
	 * Use it to maintain the content when the condition error etc. occur by calling each method. 
	 */
	protected String wMessage = "";

	//#CM642459
	/**
	 * Display the filtering qty
	 */
	private int wDisplayNumber = 0;

	//#CM642460
	/**
	 * WareNaviSystemManager
	 */
	private WareNaviSystemManager wWmsManager = null;

	//#CM642461
	/**
	 * WorkerManager
	 */
	private WorkerManager wWorkerManager = null;

	//#CM642462
	// Class method --------------------------------------------------
	//#CM642463
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2006/11/21 04:22:34 $");
	}

	//#CM642464
	// Constructors --------------------------------------------------

	//#CM642465
	// Public methods ------------------------------------------------
	//#CM642466
	/**
	 * The method corresponding to the operation to acquire necessary data when default is displayed the screen. <BR>
	 * Mount in each succession class if necessary. 
	 * @param conn Connection object with data base
	 * @param searchParam Class which succeeds to <CODE>Parameter</CODE> class with search condition
	 * @return Instance to mount <CODE>Parameter</CODE> class where retrieval result is included
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		throw new ScheduleException("This method is not supported.");
	}

	//#CM642467
	/**
	 * Acquire the data displayed to the screen. The method corresponding to the operation when the display button is pressed. <BR>
	 * Mount in each succession class if necessary. 
	 * @param conn Connection object with data base
	 * @param searchParam Class which succeeds to <CODE>Parameter</CODE> class with search condition
	 * @return Instance to mount <CODE>Parameter</CODE> class where retrieval result is included
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		throw new ScheduleException("This method is not supported.");
	}

	//#CM642468
	/**
	 * Check whether the content of the parameter is correct. <BR>
	 * Do the parameter input content check processing according to the content set in the 
	 * parameter specified by <CODE>checkParam</CODE>. <BR>
	 * Mount in each succession class if necessary. 
	 * @param conn Connection object with data base
	 * @param checkParam Parameter class where content which does input check is included
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return True: When the content of the input is normal  False : Otherwise
	 */
	public boolean check(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
		throw new ScheduleException("This method is not supported.");
	}

	//#CM642469
	/**
	 * Check whether the content of the parameter is correct. 
	 * Do the parameter input content check processing according to the content set in the parameter specified by <CODE>checkParam</CODE>. <BR>
	 * Do the input check on the content of the parameter input and Filtering area. <BR>
	 * Mount in each succession class if necessary. 
	 * @param conn Connection object with data base
	 * @param checkParam Parameter class where content which does input check is included
	 * @param inputParams Array of parameter class which passes it as data which has been input
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return True: When the content of the input is normal  False : Otherwise
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams) throws ReadWriteException, ScheduleException
	{
		throw new ScheduleException("This method is not supported.");
	}

	//#CM642470
	/**
	 * Check whether the content of the parameter is correct. 
	 * Do the parameter input content check processing according to the content set in the parameter specified by <CODE>checkParam</CODE>. <BR>
	 * This method mounts the input check when changing from the 1st screen to the second screen in two screen transition.
	 * Mount in each succession class if necessary. 
	 * @param conn Connection object with data base
	 * @param checkParam Parameter class where content which does input check is included
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return True: When the content of the input is normal  False : Otherwise
	 */
	public boolean nextCheck(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
		throw new ScheduleException("This method is not supported.");
	}

	//#CM642471
	/**
	 * Begin scheduling. 
	 * Do the schedule processing according to the content set in the parameter array specified by <CODE>startParams</CODE>.<BR>
	 * Mount in each succession class if necessary. 
	 * @param conn Connection object with data base
	 * @param startParams Array of parameter class where set content is included
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return The schedule processing returns true in case of normality and false when the schedule processing fails.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		throw new ScheduleException("This method is not supported.");
	}

	//#CM642472
	/**
	 * Begin scheduling. 
	 * Do the schedule processing according to the content set in the parameter array specified by <CODE>startParams</CODE>.<BR>
	 * Mount in each succession class if necessary. 
	 * @param conn Connection object with data base
	 * @param startParams Array of parameter class where set content is included
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return Instance to mount <CODE>Parameter</CODE> class where retrieval result is included
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		throw new ScheduleException("This method is not supported.");
	}
	
	//#CM642473
	/**
	 * The specified parameter is acquired with <CODE>countParam</CODE> and the number of cases of the object data is acquired in the search condition.<BR>
	 * Mounting the schedule processing is different according to the class which mounts this interface. <BR>
	 * Return 0 when there is no object data or is an input error. <BR>
	 * The content can be acquired by using <CODE>getMessage() </CODE> method. 
	 * @param conn Connection object with data base
	 * @param countParam Parameter including search condition
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return Object qty(0 when there is input error or there is no object)
	 */
	public int count(Connection conn, Parameter countParam) throws ReadWriteException, ScheduleException
	{
		throw new ScheduleException("This method is not supported");
	}

	//#CM642474
	/**
	 * <CODE>startSCH() </CODE> method acquire the message to display the reason when 
	 * false is returned by <CODE>check() </CODE> method.<BR>
	 * Use this method to acquire the content of the display of the screen to the message area. 
	 * @return Message
	 */
	public String getMessage()
	{
		return wMessage;
	}

	//#CM642475
	// Protected methods ---------------------------------------------
	//#CM642476
	/**
	 * Check whether the content of the worker code and the password are correct. <BR>
	 * <BR>
	 * In the following cases, it is considered NG. <BR>
	 *  - When the worker code neither useable nor includable is input<BR>
	 *  - When the worker code and the password are not matching with the one set with DmWorker<BR>
	 * 
	 * Acquire the result by using <CODE>getMessage() </CODE> method.<BR>
	 * 
	 * @param  conn               Connection object with data base
	 * @param  pWorkerCode  Worker Code
	 * @param  pPassword  Password
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return The content of the input is correct: True Incorrect : False.
	 */
	protected boolean correctWorker(Connection conn, String pWorkerCode, String pPassword) throws ReadWriteException, ScheduleException
	{
		//#CM642477
		// Generate WorkerManager. 
		getWorkerManager(conn);

		//#CM642478
		// Existence check of Worker Code
		if (!wWorkerManager.getWorkerInstance(pWorkerCode, true))
		{
			//#CM642479
			// 6023024 = Input effective Worker Code. 
			wMessage = "6023024";
			return false;
		}

		//#CM642480
		// Password Check
		if (!pPassword.equals(wWorkerManager.getPassword()))
		{
			//#CM642481
			// 6023026 = Enter correct Password.
			wMessage = "6023026";
			return false;
		}

		return true;
	}

	//#CM642482
	/**
	 * Check the manager authority arbitrarily with the check on whether the 
	 * content of Worker Code and Password is correct. <BR>
	 * Check the manager authority when true is specified for pAdminFlag. 
	 * Do not check the manager authority when it is false.<BR>
	 * <BR>
	 * 
	 * Acquire the result by using <CODE>getMessage() </CODE> method.<BR>
	 * 
	 * @param  conn               Connection object with data base
	 * @param  pWorkerCode  Worker Code
	 * @param  pPassword  Password
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return The content of the input is correct: True Incorrect : False.
	 */
	protected boolean correctWorker(Connection conn, String pWorkerCode, String pPassword, boolean pAdminFlag) 
		throws ReadWriteException, ScheduleException
	{
		//#CM642483
		// Do the check of Worker Code and Password
		if (!correctWorker(conn, pWorkerCode, pPassword))
		{
			return false;
		}

		if (pAdminFlag)
		{
			//#CM642484
			// Check the access authority. 
			if (!wWorkerManager.getAccessAuthority().equals(Worker.ACCESS_AUTHORITY_SYSTEMADMINISTRATOR) &&
				!wWorkerManager.getAccessAuthority().equals(Worker.ACCESS_AUTHORITY_ADMINISTRATOR))
			{
				//#CM642485
				// 6023029=Input manager's Worker Code and Password. 
				wMessage = "6023029";
				return false;
			}
		}

		return true;
	}

	//#CM642486
	/**
	 * Check it whether to processing the next update on the day. <BR>
	 * <BR>
	 * Retrieve the WarenaviSystem table, and check the flag while updating the date.<BR>
	 * Return true when the updating on the next day, Return false when the not updating on the next day. <BR>
	 * 
	 * Acquire the result by using <CODE>getMessage() </CODE> method.<BR>
	 * 
	 * @param  conn               Connection object with data base
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return The update is being processed on the next day : True The update is not being processed on the next day : False
	 */
	protected boolean isDailyUpdate(Connection conn) throws ReadWriteException, ScheduleException
	{
		//#CM642487
		// Generate WareNaviSystemManager. 
		getWareNaviSystemManager(conn);

		if (wWmsManager.isDailyUpdate())
		{
			//#CM642488
			// 6023321 = It is not treatable because it updates on the next day. 
			wMessage = "6023321";
			return true;
		}
		return false;

	}

	//#CM642489
	/**
	 * Check it referring to the WareNaviSystem table whether to taking data.<BR>
	 * Return true while loading, false while not loading.<BR>
	 * 
	 * Acquire the result by using <CODE>getMessage() </CODE> method.<BR>
	 * 
	 * @param conn Connection object with data base
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return True : While loading False : While not loading
	 */
	protected boolean isLoadingData(Connection conn) throws ReadWriteException, ScheduleException
	{
		//#CM642490
		// Generate WareNaviSystemManager. 
		getWareNaviSystemManager(conn);

		if (wWmsManager.isLoadData())
		{
			//#CM642491
			// 6021016=It is not treatable because data is loaded.
			wMessage = "6021016";
			return true;
		}
		return false;

	}
	
	//#CM642492
	/**
	 * Check it referring to the WareNaviSystem table whether reporting on data. <BR>
	 * Return true when reporting and return false when not reporting. <BR>
	 * 
	 * Acquire the result by using <CODE>getMessage() </CODE> method.<BR>
	 * 
	 * @param conn Connection object with data base
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return True : Reporting False : Not Reporting
	 */
	protected boolean isReportData(Connection conn) throws ReadWriteException, ScheduleException
	{
		//#CM642493
		// Generate WareNaviSystemManager. 
		getWareNaviSystemManager(conn);

		if (wWmsManager.isReportData())
		{
			//#CM642494
			// 6021015=It is not treatable because it is reporting on data. 
			wMessage = "6021015";
			return true;
		}
		return false;

	}
	
	//#CM642495
	/**
	 * Check whether the object data can be displayed in Filtering area. <BR>
	 * When a pertinent number exceeds 0 or less or the maximum display qty, it is assumed NG. (Return false. )<BR>
	 * Moreover, maintain Display the filtering qty. 
	 * <BR>
	 * Acquire the result by using <CODE>getMessage() </CODE> method.<BR>
	 * Moreover, mount the setting of the return value to the business class with Abstract...SCH of each package. <BR>
	 * 
	 * @param pCount Filtering area display qty
	 * @return True : It is possible to display in the Filtering area, False: It is not possible to display it. 
	 */
	protected boolean canLowerDisplay(int pCount)
	{
		wDisplayNumber = pCount;
		
		if (wDisplayNumber <= 0)
		{
			//#CM642496
			// There was no object data.
			wMessage = "6003018";
			return false;
		}
		
		if (wDisplayNumber > WmsParam.MAX_NUMBER_OF_DISP)
		{
			//#CM642497
			// The matter {0} corresponded. Narrow the search condition so that the number of cases may exceed the {1} matter. 
			String msg = "6003012" + wDelim + WmsFormatter.getNumFormat(wDisplayNumber) + wDelim + MAX_NUMBER_OF_DISP_DISP;
			wMessage = msg;
			return false;
		}
		
		return true;
	}
	
	//#CM642498
	/**
	 * Acquire Display of the filtering qty.
	 * 
	 * @return Display the filtering qty
	 */
	protected int getDisplayNumber()
	{
		return wDisplayNumber;
	}

	//#CM642499
	/**
	 * Register or renew worker results information table (DNWORKERRESULT).  <BR>
	 * Retrieve the worker name and the work day of DB by this method. <BR>
	 * <BR>
	 * @param conn       Connection object with data base
	 * @param pWorkerCode Worker Code
	 * @param pTerminalNo Terminal No.
	 * @param pJobType    Work flag
	 * @param pWorkQty    Work qty(Total the actual number of work information for one setting.)
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected void updateWorkerResult(Connection conn, String pWorkerCode, String pTerminalNo, String pJobType, int pWorkQty) throws ReadWriteException, ScheduleException
	{
		//#CM642500
		// Retrieve and set the worker name and the work day. 
		updateWorkerResult(
				conn, 
				pWorkerCode, 
				getWorkerName(conn, pWorkerCode), 
				getWorkDate(conn), 
				pTerminalNo, 
				pJobType, 
				pWorkQty);

	}

	//#CM642501
	/**
	 * Register or renew worker results information table (DNWORKERRESULT).  <BR>
	 * <BR>
	 * Retrieve worker results information based on Terminal  No. and Work flag on Worker Code and the work day. <BR>
	 * @param conn       Connection object with data base
	 * @param pWorkerCode Worker Code
	 * @param pWorkerName Worker Name
	 * @param pSysDate Work date
	 * @param pTerminalNo Terminal No.
	 * @param pJobType    Work flag
	 * @param pWorkQty    Work qty(Total the actual number of work information for one setting. )
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	protected void updateWorkerResult(
			Connection conn, 
			String pWorkerCode, 
			String pWorkerName, 
			String pSysDate, 
			String pTerminalNo, 
			String pJobType, 
			int pWorkQty) throws ReadWriteException
	{
		//#CM642502
		// Retrieve worker results information based on Terminal  No. and Work flag on Worker Code and the work day. 
		WorkerResultHandler workerResultHandle = new WorkerResultHandler(conn);
		WorkerResultSearchKey workerResultKey = new WorkerResultSearchKey();
		workerResultKey.setWorkDate(pSysDate);
		workerResultKey.setWorkerCode(pWorkerCode);
		workerResultKey.setTerminalNo(pTerminalNo);
		workerResultKey.setJobType(pJobType);

		//#CM642503
		// Update Worker Name, the work end date, Work qty and the work frequency when data exists.
		if (workerResultHandle.count(workerResultKey) > 0)
		{
			WorkerResult[] wresult = (WorkerResult[]) workerResultHandle.find(workerResultKey);

			updateWorkerResult(conn, pWorkerName, pWorkQty, wresult[0]);
		}
		//#CM642504
		// Register worker results information when data does not exist. 
		else
		{
			createWorkerResult(conn, 
							pSysDate, 
							pWorkerCode, 
							pWorkerName, 
							pTerminalNo, 
							pJobType, 
							pWorkQty);
		}

	}

	//#CM642505
	/**
	 * Renew the flag while taking the WareNaviSystem table. <BR>
	 * Renew the loading flag to turning on when true is set in the argument. <BR>
	 * Renew the loading flag to turning off when false is set in the argument. <BR>
	 * 
	 * @param conn Connection object with data base
	 * @param pStatus True: Load and change while updating it.
	 * 				  False: Load and release it while updating it.
	 * @return Return true when succeeded in the update, or return false when failed in the update.
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean updateLoadDataFlag(Connection conn, boolean pStatus) throws ReadWriteException, ScheduleException
	{
		// Load and change while updating it.
		if (pStatus)
		{
			try
			{
				WareNaviSystemAlterKey wmsAltKey = new WareNaviSystemAlterKey();
				WareNaviSystemHandler wmsHandler = new WareNaviSystemHandler(conn);
				
				wmsAltKey.setSystemNo(WareNaviSystem.SYSTEM_NO);
				wmsAltKey.setLoadData(WareNaviSystem.LOADDATA_STOP);
				wmsAltKey.updateLoadData(WareNaviSystem.LOADDATA_LOADING);
				
				wmsHandler.modify(wmsAltKey);
				return true;
			}
			catch (NotFoundException e)
			{
				// 6021016=In process of data loading. Cannot process.
				wMessage = "6021016";
				return false;
			}
			catch (InvalidDefineException e)
			{
				throw new ReadWriteException(e.getMessage());
			}
		}
		// Load and release it while updating it.
		else
		{
			try
			{
				WareNaviSystemAlterKey wmsAltKey = new WareNaviSystemAlterKey();
				WareNaviSystemHandler wmsHandler = new WareNaviSystemHandler(conn);
				
				wmsAltKey.setSystemNo(WareNaviSystem.SYSTEM_NO);
				wmsAltKey.setLoadData(WareNaviSystem.LOADDATA_LOADING);
				wmsAltKey.updateLoadData(WareNaviSystem.LOADDATA_STOP);
				
				wmsHandler.modify(wmsAltKey);
				return true;
			}
			catch (NotFoundException e)
			{
				// メンテナンス画面などより、warenaviSystemテーブルを更新された場合に発生する可能性があります。
				// ただし、報告処理終了後のフラグOFFの処理のため、例外扱いにはせずロギングしfalseを返します。
				Object[] tObj = new Object[1] ;
				tObj[0] = "warenavi_system";
				// 6026017=Cannot update. The data you try to update was updated in other process. {0}
				RmiMsgLogClient.write(6026017, this.getClass().getName(), tObj);
				return false;
			}
			catch (InvalidDefineException e)
			{
				throw new ReadWriteException(e.getMessage());
			}
		}
		
	}

	//#CM642506
	/**
	 * Set Work qty. <BR>
	 * Set a regulated value of WmsParam in Work qty when Work qty exceeds a regulated 
	 * value of WmsParam and return it. 
	 * @param pOriginalQty Previous Work qty in addition and crowding. 
	 * @param pWorkQty Work qty for this time
	 * @return Work qty which adds this time
	 */
	protected int addWorkQty(int pOriginalQty, int pWorkQty)
	{
		//#CM642507
		// Work qty after updating = Work qty before updating + Current Work qty
		int returnWorkQty = pOriginalQty + pWorkQty;

		//#CM642508
		// Overflow check of Work qty
		if (returnWorkQty > WmsParam.WORKER_MAX_TOTAL_QTY)
		{
			returnWorkQty = WmsParam.WORKER_MAX_TOTAL_QTY;
		}

		return returnWorkQty;

	}
	
	//#CM642509
	/**
	 * Acquire Work date of WareNaviSystem.<BR>
	 * 
	 * @param conn Connection object with data base
	 * @return Work date of WareNaviSystem
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected String getWorkDate(Connection conn) throws ScheduleException, ReadWriteException
	{
		getWareNaviSystemManager(conn);
		
		return wWmsManager.getWorkDate();
	}

	//#CM642510
	/**
	 * Acquire whether the crossing dock package is introduced. <BR>
	 * 
	 * @param conn Connection object with data base
	 * @return True : Package introduced False : Package not introduced
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean isCrossDockPack(Connection conn) throws ScheduleException, ReadWriteException
	{
		getWareNaviSystemManager(conn);
		
		return wWmsManager.isCrossDockPack();
	}

	//#CM642511
	/**
	 * Acquire whether the receiving package is introduced. <BR>
	 * 
	 * @param conn Connection object with data base
	 * @return True : Package introduced False : Package not introduced
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean isInstockPack(Connection conn) throws ScheduleException, ReadWriteException
	{
		getWareNaviSystemManager(conn);
		
		return wWmsManager.isInstockPack();
	}

	//#CM642512
	/**
	 * Acquire whether the Storage package is introduced. <BR>
	 * 
	 * @param conn Connection object with data base
	 * @return True : Package introduced False : Package not introduced
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean isStoragePack(Connection conn) throws ScheduleException, ReadWriteException
	{
		getWareNaviSystemManager(conn);
		
		return wWmsManager.isStoragePack();
	}

	//#CM642513
	/**
	 * Acquire whether the Picking package is introduced. <BR>
	 * 
	 * @param conn Connection object with data base
	 * @return True : Package introduced False : Package not introduced
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean isRetrievalPack(Connection conn) throws ScheduleException, ReadWriteException
	{
		getWareNaviSystemManager(conn);
		
		return wWmsManager.isRetrievalPack();
	}

	//#CM642514
	/**
	 * Acquire whether the Sorting package is introduced. <BR>
	 * 
	 * @param conn Connection object with data base
	 * @return True : Package introduced False : Package not introduced
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean isSortingPack(Connection conn) throws ScheduleException, ReadWriteException
	{
		getWareNaviSystemManager(conn);
		
		return wWmsManager.isSortingPack();
	}

	//#CM642515
	/**
	 * Acquire whether the Shipping package is introduced. <BR>
	 * 
	 * @param conn Connection object with data base
	 * @return True : Package introduced False : Package not introduced
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean isShippingPack(Connection conn) throws ScheduleException, ReadWriteException
	{
		getWareNaviSystemManager(conn);
		
		return wWmsManager.isShippingPack();
	}

	//#CM642516
	/**
	 * Acquire whether the Stock package is introduced. <BR>
	 * 
	 * @param conn Connection object with data base
	 * @return True : Package introduced False : Package not introduced
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean isStockPack(Connection conn) throws ScheduleException, ReadWriteException
	{
		getWareNaviSystemManager(conn);
		
		return wWmsManager.isStockPack();
	}

	//#CM642517
	/**
	 * Acquire whether the Movement Rack package is introduced. <BR>
	 * 
	 * @param conn Connection object with data base
	 * @return True : Package introduced False : Package not introduced
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean isIdmPack(Connection conn) throws ScheduleException, ReadWriteException
	{
		getWareNaviSystemManager(conn);
		
		return wWmsManager.isIdmPack();
	}

	//#CM642518
	/**
	 * Acquire whether the cross dock operation is introduced. <BR>
	 * 
	 * @param conn Connection object with data base
	 * @return True : Cross dock operation introduced False : Cross dock operation not introduced
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean isCrossDockOperation(Connection conn) throws ScheduleException, ReadWriteException
	{
		getWareNaviSystemManager(conn);
		
		return wWmsManager.isCrossDockOperation();
	}

	//#CM642519
	/**
	 * Acquire whether the TC operation is introduced. <BR>
	 * 
	 * @param conn Connection object with data base
	 * @return True : TC operation introduced False : TC operation not introduced
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected boolean isTcOperation(Connection conn) throws ScheduleException, ReadWriteException
	{
		getWareNaviSystemManager(conn);
		
		return wWmsManager.isTcOperation();
	}

	//#CM642520
	/**
	 * Acquire Worker Name of DmWorker. <BR>
	 * 
	 * @param conn Connection object with data base
	 * @param pWorkerCode Worker Code
	 * @return Worker Name
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	protected String getWorkerName(Connection conn, String pWorkerCode) throws ReadWriteException
	{
		getWorkerManager(conn);
		
		return wWorkerManager.getName(pWorkerCode);
	}

	//#CM642521
	/**
	 * Lock work information and the inventory information which corresponds to work No. passed by the argument. 
	 * 
	 * @param conn Connection object with data base
	 * @param pJobNoArray Work No. of work information to be locked(Array)
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @return True when there is lock object, false otherwise.
	 */
	protected boolean lockAll(Connection conn, String[] pJobNoArray) throws ReadWriteException
	{
		WorkingInformationHandler workHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey workKey = new WorkingInformationSearchKey();

		workKey.setJobNo(pJobNoArray);
		workKey.setJobNoCollect();
		
		//#CM642522
		// Lock work information. 
		return workHandler.lock(workKey);

	}

	//#CM642523
	/**
	 * Lock work information and the inventory information which corresponds to scheduling the unique key passed by the argument. 
	 * 
	 * @param conn Instance to maintain connection with database. 
	 * @param pPlanUkeyArray Scheduling unique key to work information to be locked(Array)
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @return True when there is lock object, false otherwise.
	 */
	protected boolean lockPlanUkeyAll(Connection conn, String[] pPlanUkeyArray) throws ReadWriteException
	{
		WorkingInformationHandler workHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey workKey = new WorkingInformationSearchKey();

		workKey.setPlanUkey(pPlanUkeyArray);
		workKey.setJobNoCollect();

		//#CM642524
		// Lock work information. 
		return workHandler.lock(workKey);

	}
	
	//#CM642525
	/**
	 * Do the handling processing when ScheduleException happens. <BR>
	 * 
	 * @param pClassName Class name where exception is generated
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected void doScheduleExceptionHandling(String pClassName) 
			throws ScheduleException, ReadWriteException
	{
		doExceptionHandling(new ScheduleException(), pClassName);
	}
	
	//#CM642526
	/**
	 * Fix the transaction. <BR>
	 * Do the rollback. <BR>
	 * 
	 * @param pConn Connection object with data base
	 * @param pClassName Call former class name
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected void doRollBack(Connection pConn, String pClassName) 
			throws ReadWriteException, ScheduleException
	{
		try
		{
			pConn.rollback();
		}
		catch (SQLException e)
		{
			doExceptionHandling(e, pClassName);
		}

	}
	
	//#CM642527
	/**
	 * Fix the transaction. <BR>
	 * Do the commit. <BR>
	 * 
	 * @param pConn Connection object with data base
	 * @param pClassName Call former class name
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected void doCommit(Connection pConn, String pClassName) 
			throws ReadWriteException, ScheduleException
	{
		try
		{
			pConn.commit();
		}
		catch (SQLException e)
		{
			doExceptionHandling(e, pClassName);
		}

	}

	//#CM642528
	/**
	 * Do the exception handling. 
	 * 
	 * @param e Exception
	 * @param pClassName Class name
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected void doExceptionHandling(Exception e, String pClassName) 
			throws ScheduleException, ReadWriteException
	{
		if (e instanceof ScheduleException)
		{
			//#CM642529
			// 6006001=The error not anticipated occurred. {0}
			RmiMsgLogClient.write(new TraceHandler(6006001, e), pClassName);
			throw (new ScheduleException(e.getMessage()));
		}
		else if (e instanceof SQLException)
		{
			//#CM642530
			// 6006002 = The data base error occurred. {0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), pClassName);
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	//#CM642531
	// Private methods ---------------------------------------------
	//#CM642532
	/**
	 * Generate the WareNaviSystemManager instance. <BR>
	 * Generate the WareNaviSystemManager instance only once in this class. 
	 * 
	 * @param conn Database connection
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException  It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	private void getWareNaviSystemManager(Connection conn) throws ReadWriteException, ScheduleException
	{
		if (wWmsManager == null)
		{
			wWmsManager = new WareNaviSystemManager(conn);
		}
	}

	//#CM642533
	/**
	 * Generate the WorkerManager instance. <BR>
	 * Generate the WorkerManager instance only once in this class. 
	 * 
	 * @param conn Database connection
	 */
	private void getWorkerManager(Connection conn)
	{
		if (wWorkerManager == null)
		{
			wWorkerManager = new WorkerManager(conn);
		}
	}

	//#CM642534
	/**
	 * Register worker results information when data does not exist. <BR>
	 *   <BR>
	 *   <DIR>
	 *   Work date sets the system definition date. <BR>
	 *   Set the date now in both the work beginning date and the work end date. <BR>
	 *   Work qty sets the actual qty of work information. <BR>
	 *   The work frequency sets one. <BR>
	 *   </DIR>
	 * <BR>
	 * Make the worker results newly. <BR>
	 * 
	 * @param conn Connection object to data base
	 * @param pWorkDate Work date of WMS
	 * @param pWorkerCode Worker Code
	 * @param pWorkerName Worker Name
	 * @param pTerminalNo Terminal No.
	 * @param pJobType Work type
	 * @param pWorkQty Work qty
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	private void createWorkerResult(Connection conn, String pWorkDate, String pWorkerCode, String pWorkerName, String pTerminalNo, String pJobType, int pWorkQty) throws ReadWriteException
	{
		WorkerResultHandler workerResultHandle = new WorkerResultHandler(conn);
		WorkerResult woresult = new WorkerResult();

		//#CM642535
		// Work date sets Work date of WareNaviSystem. 
		woresult.setWorkDate(pWorkDate);
		//#CM642536
		// Set the current date in the work beginning date and the work end date. 
		Date sysDate = new Date();
		woresult.setWorkStartTime(sysDate);
		woresult.setWorkEndTime(sysDate);
		woresult.setWorkerCode(pWorkerCode);
		woresult.setWorkerName(pWorkerName);
		woresult.setTerminalNo(pTerminalNo);
		woresult.setJobType(pJobType);
		woresult.setWorkQty(addWorkQty(0, pWorkQty));
		woresult.setWorkCnt(1);

		try
		{
			//#CM642537
			//Registration of data
			workerResultHandle.create(woresult);
		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

	}

	//#CM642538
	/**
	 * Update the worker results. <BR>
	 * <BR>
	 * Update information on existing worker results passed by the argument to the key. <BR>
	 *   <DIR>
	 *   <Search condition><BR>
	 *    - Work date<BR>
	 *    - Worker Code<BR>
	 *    - Terminal No.<BR>
	 *    - Work type<BR>
	 *   <BR>
	 *   <Content of update><BR>
	 *    - Work end date:Current Date<BR>
	 *    - Worker Name<BR>
	 *    - Work qty:Former Work qty+Current actual qty<BR>
	 *    - Work frequency:Former Work frequency+1<BR>
	 *    - <BR>
	 *   </DIR>
	 * 
	 * @param conn Connection object with data base
	 * @param pWorkerName Worker Name
	 * @param pWorkQty Work qty
	 * @param pOriginalResult Existing worker results
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	private void updateWorkerResult(Connection conn, String pWorkerName, int pWorkQty, WorkerResult pOriginalResult) throws ReadWriteException
	{
		WorkerResultHandler workerResultHandle = new WorkerResultHandler(conn);
		WorkerResultAlterKey workerResultAltKey = new WorkerResultAlterKey();

		//#CM642539
		// The update condition is set. 
		workerResultAltKey.setWorkDate(pOriginalResult.getWorkDate());
		workerResultAltKey.setWorkerCode(pOriginalResult.getWorkerCode());
		workerResultAltKey.setTerminalNo(pOriginalResult.getTerminalNo());
		workerResultAltKey.setJobType(pOriginalResult.getJobType());

		//#CM642540
		// The update data is set. 
		workerResultAltKey.updateWorkEndTime(new Date());
		workerResultAltKey.updateWorkerName(pWorkerName);
		workerResultAltKey.updateWorkQty(addWorkQty(pOriginalResult.getWorkQty(), pWorkQty));
		workerResultAltKey.updateWorkCnt(pOriginalResult.getWorkCnt() + 1);
		try
		{
			//#CM642541
			// Update of data
			workerResultHandle.modify(workerResultAltKey);
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

	}

}
//#CM642542
//end of class
