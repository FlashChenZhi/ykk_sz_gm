//#CM695722
//$Id: DailyUpdateSCH.java,v 1.2 2006/11/13 06:03:13 suresh Exp $

//#CM695723
/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.tool.CompressToZipFile;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MovementHandler;
import jp.co.daifuku.wms.base.dbhandler.MovementSearchKey;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ResultHandler;
import jp.co.daifuku.wms.base.dbhandler.ResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.base.entity.NextProcessInfo;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.system.dbhandler.SystemHostSendFinder;
import jp.co.daifuku.wms.base.system.dbhandler.SystemHostSendHandler;
import jp.co.daifuku.wms.base.system.dbhandler.SystemResultHandler;
import jp.co.daifuku.wms.base.system.dbhandler.SystemStockFinder;
import jp.co.daifuku.wms.base.system.dbhandler.SystemStockHandler;
import jp.co.daifuku.wms.base.system.dbhandler.SystemWorkingInformationFinder;
import jp.co.daifuku.wms.base.system.dbhandler.SystemWorkingInformationHandler;
import jp.co.daifuku.wms.base.utility.FileClear;
import jp.co.daifuku.wms.master.MasterPrefs;
import jp.co.daifuku.wms.master.remover.ConsignorAutoRemover;
import jp.co.daifuku.wms.master.remover.CustomerAutoRemover;
import jp.co.daifuku.wms.master.remover.ItemAutoRemover;
import jp.co.daifuku.wms.master.remover.SupplierAutoRemover;

//#CM695724
/**
 * Designer : M.Inoue <BR>
 * Maker : M.Inoue   <BR>
 *
 * Allow this class to execute the process for daily update<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind(Connection conn, Parameter searchParam)</CODE> method)<BR>
 * <BR>
 * Execute the process for searching a work date.<BR>
 * Search for the System definition table (WARENAVI_SYSTEM).<BR>
 * <BR>
 * 2.Input check process(<CODE>check(Connection conn, Parameter checkParam)</CODE> method)<BR>
 * <BR>
 * Check the Worker Code, password, and privilege.<BR>
 * Check whether other terminal is processing the daily update.<NR>
 * Disable to execute daily update if Work Status data includes one or more data with status "Processing".<BR>
 * If there is a Storage data In Process in the Relocation work status data, disable to execute daily update.<BR>
 * Process for starting a daily update invokes this.<BR>
 * 
 * <BR>
 * 3.execute the check process.(<CODE>check(Connection conn, Parameter checkParam)</CODE> method)<BR>
 * <BR>
 *     <DIR>
 *     <Outline of processes>
 *        <DIR>
 *        Check the parameter and check whether to enable to execute daily update.<BR>
 *        </DIR>
 * <BR>
 *     <Details of processes>
 *        <DIR>
 *        1.Check the worker code.<BR>
 *        2.Check the password.<BR>
 *        3.Check the work condition.<BR>
 *        </DIR>
 *     </DIR>
 * <BR>
 * 4.Process by clicking "Start" button (<CODE>startSCH(Connection conn, Parameter[] checkParam)</CODE> method)<BR>
 * <BR>
 *     <DIR>
 *     Daily Update Process<BR>
 *     <Outline of processes>
 *        <DIR>
 *        1.Process for starting the daily update<BR>
 *        2.Execute the process for deleting data no longer needed.<BR>
 *        3.Execute the process for deleting the log file.<BR>
 *        4.Execute the process for updating the work date.<BR>
 *        5.Process for ending the daily update.<BR>
 *        </DIR>
 * <BR>
 *     <Details of processes>
 *        <DIR>
 *        1.Process for starting the daily update<BR>
 *          <DIR>
 *          Change the Daily Update flag of WareNaviSystem to ON.<BR>
 *          </DIR>
 * <BR>
 *        2.Execute the process for deleting data no longer needed.<BR>
 *          <DIR>
 *          2-1.Delete the Plan data.<BR>
 *            <DIR>
 *            Plan data subject to delete:<BR>
 *              <DIR>
 *              Planned Date of Plan data <= Work Date of warenavi_system info, and<BR>
 *              Status flag of Plan data = Completed, Standby, or Deleted.<BR>
 *              </DIR>
 *            To hold over Plan data with status Not Worked:<BR>
 *              <DIR>
 *              Planned Date of Plan data <= Work Date of warenavi_system info, and<BR>
 *              Status flag of Plan data = Completed or Deleted,<BR>
 *              </DIR>
 *              and<BR>
 *              <DIR>
 *              Added Date of Plan data < Current date/time - Plan retention period (days) of warenavi_system info, and<BR>
 *              Status flag of Plan data = Standby or Partially Completed<BR>
 *              </DIR>
 * <BR>
 *            Execute the following processes for each plan data of Receiving, Storage, Picking, Sorting, and Shipping.<BR>
 *              <DIR>
 *              a.Obtain the Stock ID subject to delete in the inventory information.<BR>
 *              b.Delete the inventory information using all the obtained Stock ID as Delete key.<BR>
 *              c.Obtain the Plan unique key of the work status subject to delete.<BR>
 *              d.Delete the work status of all the obtained plan unique keys using plan unique key as a Delete key.<BR>
 *              e.Delete the Plan info subject to delete.<BR>  
 *              </DIR>
 *            </DIR>
 * <BR>
 *          2-2.Delete the inventory check data.<BR>
 *            <DIR>
 *            Data subject to delete:<BR>
 *              <DIR>
 *              Data subject to delete: Process flag of Inventory Check Work Status = Submitted or Deleted<BR>
 *              </DIR>
 *            <BR>
 *            </DIR>
 * <BR>
 *          2-3.Delete the Relocation work data.<BR>
 *            <DIR>
 *            Data subject to delete:<BR>
 *              <DIR>
 *              Data with Status flag of Relocation work Status = Completed or Deleted<BR>
 * <BR>
 *              If Stock inquiry package is available, data subject to delete:<BR>
 *                <DIR>
 *                Data with Added Date < Current date/time - Plan retention period (days) of warenavi_system info<BR>
 *                Data with Status flag of Relocation work Status = Standby Storage, and<BR>
 *                </DIR>
 *            <BR>
 *              </DIR>
 * <BR>
 *            Execute the following processes.<BR>
 *              <DIR>
 *              a.Clear the Allocated stock.<BR>
 *                <DIR>
 *                If inventory inquiry package is "Available", clear the allocated inventory information that matches to the conditions of the Relocation work Status: Status flag = Standby Storage.<BR>
 *                <BR>
 *                The data to be cleared are inventory information with the same Stock ID in the Relocation work status.<BR>
 *                  <DIR>
 *                  -  Obtain the Stock ID subject to delete in the Relocation work Status and also obtain the total of planned qty by Stock ID from the work status.<BR>
 *                  -  Subtract the total planned qty from the allocated qty of the inventory information for the number of obtained Stock ID using Stock ID as Update key.<BR>
 *                  </DIR>
 *                </DIR>
 *              b.Delete the relocation work status subject to delete.<BR>  
 *              </DIR>
 *            </DIR>
 * <BR>
 *          2-4.Delete the Center inventory data.<BR>
 *            <DIR>
 *            Data subject to delete:<BR>
 *              <DIR>
 *              Data subject to delete: Stock status of Inventory information = Center Inventory, and Stock Qty = 0<BR>
 *              </DIR>
 *            <BR>
 *            </DIR>
 * <BR>
 *          2-5.Move the result data and delete it.<BR>
 *            <DIR>
 *            a.Move the Sending Result Information and delete it.<BR>
 *              <DIR>
 *              -  Write the data, of which all the Sent flag per aggregation = Sent, of Sending Result Information into the result info.<BR>
 *              -  Delete the data corresponding to the condition that All the Sent flag of one aggregation of Sending Result Information = Sent, from the Sending Result Information.<BR>
 *              </DIR>
 *            b.Delete the Sending Result Information.<BR>
 *            c.Delete the Result information.<BR>
 *            </DIR>
 * <BR>
 *          2-6.Delete the worker result data.<BR>
 *          </DIR>
 * <BR>
 *        3.Delete the master data.<BR>
 *          <DIR>
 *          Update the Last date/time of using each master in the Sending result info and delete the master data not used.<BR>
 *          </DIR>
 * <BR>
 *        4.Reset the counter.<BR>
 * <BR>
 *        5.Execute the process for deleting the log file.<BR>
 *          <DIR>
 *          5-1.Delete the Message log file that passed its retention period (days).<BR>
 *          5-2.Delete the loading-data history file that passed its retention period (days).<BR>
 *          5-3.Delete the ticket image file that passed its retention period (days).<BR>
 *          5-4.Delete the TOMCAT log file that passed its retention period (days).<BR>
 *          </DIR>
 * <BR>
 *        6.Execute the process for updating the work date.<BR>
 *          <DIR>
 *          Set the Work Date in the warenavi_system information forward by one day.<BR>
 *          </DIR>
 * <BR>
 *        7.Process for ending the daily update.<BR>
 *          <DIR>
 *          Change the Daily Update flag of WareNaviSystem to OFF<BR>
 *          </DIR>
 *        </DIR>
 *     </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/02</TD><TD>M.Inoue</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:13 $
 * @author  $Author: suresh $
 */
public class DailyUpdateSCH extends AbstractSystemSCH
{
	//#CM695725
	// Class fields --------------------------------------------------

	//#CM695726
	// Class variables -----------------------------------------------

	//#CM695727
	/**
	 * Class Name (Daily Update)
	 */
	private final String wProcessName = "DailyUpdateSCH";

	//#CM695728
	/**
	 * Define the WareNavi system.
	 */
	protected WareNaviSystem wWareNaviSystem = null;

	//#CM695729
	/**
	 * Boundary date/time as the expiry subject to delete, given from converting the Plan retention period (days) in the WareNaviSystem into date.
	 */
	protected String wDelPlanDate = null;

	//#CM695730
	/**
	 * Boundary date/time as the expiry subject to delete, given from converting the Result retention period (days) in the WareNaviSystem into date.
	 */
	protected String wDelResultDate = null;

	//#CM695731
	/**
	 * Retention period (days) of Master in the WareNaviSystem.
	 */
	protected int wHoldDays = 0;

	//#CM695732
	// Class method --------------------------------------------------

	//#CM695733
	// Constructors --------------------------------------------------
	//#CM695734
	/**
	 * Constructor
	 */
	public DailyUpdateSCH()
	{
	}

	//#CM695735
	// Public methods ------------------------------------------------
	//#CM695736
	/**
	 * This method obtains the data required for initial display.<BR>
	 * @param conn Database connection object
	 * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions.
	 * @return A class that implements the <CODE>Parameter</CODE> interface that contains the search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		WareNaviSystem WareNaviSystem = new WareNaviSystem();

		SystemParameter param = (SystemParameter)searchParam;
		//#CM695737
		//Obtain the WareNaviSystem object.
		WareNaviSystem = findWareNaviSystem(conn);
		param = makeSystemParam(WareNaviSystem);
		return param;
	}

	//#CM695738
	/**
	 * Check the contents of the parameter for its properness.According to the contents set for the parameter designated in <CODE>checkParam</CODE>,<BR>
	 * execute the process for checking the input in the parameter.Implement of the check process depends on the class that implements this interface.<BR>
	 * Return true if the contents of the parameter is correct.<BR>
	 * Return false if the content of the parameter has some problem,Use the <CODE>getMessage()</CODE> method to obtain the contents .
	 * @param conn Database connection object
	 * @param checkParam This parameter class includes contents to be checked for input.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean check(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
		//#CM695739
		// Translate the data type of checkParam.
		SystemParameter param = (SystemParameter)checkParam;
			
		//#CM695740
		// Check the Worker Code, password, and Access Privileges.
		if (!checkWorker(conn, param, true))
		{
			return false;
		}

		//#CM695741
		// Check the daily update in process.
		if (isDailyUpdate(conn))
		{
			return false;
		}

		//#CM695742
		// Check for loading the Plan data in progress.
		if (isLoadingData(conn))
		{
			return false;
		}	

		//#CM695743
		// Check for generating in progress for reporting data.
		if (isReportData(conn))
		{
			return false;
		}
		
        //#CM695744
        // Check whether the Work Date has been changed.
		if (!getWorkDate(conn).equals(param.getWorkDate()))
		{
			//#CM695745
			// 6023400=Work date is changed.
			wMessage = "6023400";
			return false;
		}

		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
		
		//#CM695746
		// Disable to execute daily update if any work with status "Started" or "Processing" exists.
		String[] status = { WorkingInformation.STATUS_FLAG_START, WorkingInformation.STATUS_FLAG_NOWWORKING };
		workInfoSearchKey.setStatusFlag(status);
		if (workInfoHandler.count(workInfoSearchKey) > 0)
		{
			//#CM695747
			// 6007040=Working data exists. Unable to process.
			wMessage = "6007040";
			return false;
		}
		
		//#CM695748
		// Disable to execute daily update if there is a relocation info with status "Storage In Process".
		MovementHandler moveHandle = new MovementHandler(conn);
		MovementSearchKey moveSearchKey = new MovementSearchKey();
		
		//#CM695749
		// Disable to execute daily update if any work with status "Processing" exists.
		moveSearchKey.setStatusFlag(Movement.STATUSFLAG_NOWWORKING);
		if (moveHandle.count(moveSearchKey) > 0)
		{
			//#CM695750
			// 6007040=Working data exists. Unable to process.
			wMessage = "6007040";
			return false;
		}

		return true;
	}

	//#CM695751
	/**
	 * Start the schedule. According to the contents set in the parameter array designated in the <CODE>startParams</CODE>,<BR>
	 * execute the process for the schedule. Implement a scheduling process depending on the class implementing this interface.<BR>
	 * Return true when the schedule process completed successfully.<BR>
	 * Return false if failed to schedule due to condition error or other causes.<BR>
	 * In this case, use the <CODE>getMessage()</CODE> method to obtain the contents.
	 * @param conn Database connection object
	 * @param startParams Array of Schedule parameter.
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		boolean resultFlag = false;
		boolean dailyUpdate = false;

RmiMsgLogClient.write(6020013, "DailyUpdate Start");
		try
		{
			SystemParameter[] param = (SystemParameter[])startParams;

			//#CM695752
			// Check whether starting the daily update is possible or not.
			if (!check(conn, param[0]))
			{
				return false;
			}

			//#CM695753
			// Change the Daily Update flag of WareNaviSystem to ON.
			//#CM695754
			// If error occurs in the following process, turn the Daily Update flag OFF and close the daily update.
			changeDailyUpdateFlag(conn, true);
			doCommit(conn, wProcessName);
			dailyUpdate = true;

			//#CM695755
			// Obtain the value required for the Daily Update Process.
			getDailyUpdateValues(conn);

			//#CM695756
			// Delete unnecessary data.
			deleteData(conn, param);

			//#CM695757
			// Set the Work Date forward by one day.
			changeWorkDate(conn, wWareNaviSystem);

			//#CM695758
			// 6021003=Daily cleanup completed.
			wMessage = "6021003";
			resultFlag = true;
RmiMsgLogClient.write(6020013, "DailyUpdate End");
		}
		catch (Exception e)
		{
			doRollBack(conn, wProcessName);
			//#CM695759
			// 6006001=Unexpected error occurred.{0}{0}
			RmiMsgLogClient.write(new TraceHandler(6006001, e), this.getClass().getName());
			wMessage = "6006001";
			throw new ScheduleException(e.getMessage());
		}
		finally
		{
			if(dailyUpdate)
			{
				//#CM695760
				// Set the daily update in process flag to OFF.
				changeDailyUpdateFlag(conn, false);
				doCommit(conn, wProcessName);
			}
		}
		return resultFlag;
	}

	//#CM695761
	/**
	 * Start the schedule. According to the contents set in the parameter array designated in the <CODE>startParams</CODE>,<BR>
	 * execute the process for the schedule. Implement a scheduling process depending on the class implementing this interface.<BR>
	 * Use this method to display the content displayed on screen again based on the schedule result.
	 * Return null if failed to schedule due to condition error or other causes.<BR>
	 * In this case, use the <CODE>getMessage()</CODE> method to obtain the contents.
	 * @param conn Database connection object
	 * @param startParams Database connection object
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		startSCH(conn, startParams);
		SystemParameter paramArray[] = new SystemParameter[1];
		paramArray[0] = (SystemParameter)initFind(conn, startParams[0]);
		return paramArray;
	}

	//#CM695762
	// Package methods -----------------------------------------------

	//#CM695763
	// Protected methods ---------------------------------------------

	//#CM695764
	/**
	 * Obtain the value required for the Daily Update Process.
	 * @param conn Database connection object
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	protected void getDailyUpdateValues(Connection conn) throws ReadWriteException, ScheduleException
	{
		//#CM695765
		// Obtain the retention period (days).
		wWareNaviSystem = findWareNaviSystem(conn);

		//#CM695766
		// Convert the retention period (days) of the Plan obtained from the WareNaviSystem into date and obtain the boundary date/time as the expiry of the data subject to delete.
		wDelPlanDate = getKeepDateString(wWareNaviSystem.getWorkDate(), wWareNaviSystem.getPlanHoldPeriod());

		//#CM695767
		// Convert the retention period (days) of the Result obtained from the WareNaviSystem into date and obtain the boundary date/time as the expiry of the data subject to delete.
		wDelResultDate = getKeepDateString(wWareNaviSystem.getWorkDate(), wWareNaviSystem.getResultHoldPeriod());

		//#CM695768
		// Obtain the value required for the daily update master process.
		getDailyUpdateValuesMaster(conn);
	}

	//#CM695769
	/**
	 * Obtain the value required for the daily update master process.
	 * @param conn Database connection object
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	protected void getDailyUpdateValuesMaster(Connection conn) throws ReadWriteException
	{
		//#CM695770
		//Obtain the value of the Master retention period (days).
		MasterPrefs wMPrefs = new MasterPrefs(); 
		wHoldDays = wMPrefs.getHoldDays();
	}

	//#CM695771
	/**
	 * Delete unnecessary data.
	 * @param conn Database connection object
	 * @param param Array of Schedule parameter.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	protected void deleteData(Connection conn, SystemParameter[] param)
														throws ReadWriteException, ScheduleException
	{
		//#CM695772
		// Delete unnecessary Planned work data.
		deleteWorkingData(conn, param);

		//#CM695773
		// Delete the Master.
		deleteDataMaster(conn);

		//#CM695774
		// Delete the log file.
		deleteLog();
	}

	//#CM695775
	/**
	 * Delete unnecessary Planned work data.
	 * @param conn Database connection object
	 * @param param Array of Schedule parameter.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	protected void deleteWorkingData(Connection conn, SystemParameter[] param)
														throws ReadWriteException, ScheduleException
	{
		String workday = wWareNaviSystem.getWorkDate();

		//#CM695776
		// Obtain the result of wheter Inventory package exists or not.
		//#CM695777
		//   stockPack:true --> Inventory package Available
		boolean stockPack = isStockPack(conn);

		//#CM695778
		/*************************************
		 * Delete the xxxPlan data.
		 *************************************/

		//#CM695779
		// Refer the flag to determine whether to remain or delete the work status Not Processed.
		boolean unWorkDelete = false;
		unWorkDelete = param[0].getSelectUnworkingInformation().equals(SystemParameter.SELECTUNWORKINGINFORMATION_DELETE);

		//#CM695780
		// Delete the Plan data (Planned xxx info/ Work Status/ Inventory information).
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start planDelete ****");
		planDelete(conn, unWorkDelete, workday, wDelPlanDate, stockPack);
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   planDelete ****");

		//#CM695781
		/*************************************
		 * Delete the Inventory Check data.
		 *************************************/
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start deleteInventoryCheck ****");
		deleteInventoryCheck(conn);
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   deleteInventoryCheck ****");

		//#CM695782
		/*************************************
		 * Delete the Relocation work data.
		 *************************************/
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start deleteMovement ****");
		deleteMovement(conn, wDelPlanDate, stockPack);
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   deleteMovement ****");

		//#CM695783
		/*************************************
		 * Delete the Center inventory data.
		 *************************************/
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start deleteCenterStock ****");
		deleteCenterStock(conn);
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   deleteCenterStock ****");

		//#CM695784
		/*************************************
		 * Move the result data and delete it.
		 *************************************/
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start moveResult ****");
		moveResult(conn);
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   moveResult ****");

DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start deleteResult ****");
		deleteResult(conn, wDelResultDate);
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   deleteResult ****");

		//#CM695785
		/*************************************
		 * Delete the Worker result info.
		 *************************************/
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start deleteWokerResult ****");
		deleteWokerResult(conn, wDelResultDate);
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   deleteWokerResult ****");

		//#CM695786
		/*************************************
		 * Delete the next work status.
		 *************************************/
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start deleteNextProcessInfo ****");
		deleteNextProcessInfo(conn, unWorkDelete, workday, wDelPlanDate);
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   deleteNextProcessInfo ****");
	}

	//#CM695787
	/**
	 * Delete unnecessary master.
	 * @param conn Database connection object
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	protected void deleteDataMaster(Connection conn) throws ReadWriteException
	{
		//#CM695788
		/*************************************
		 * Delete the Consignor master info.
		 *************************************/
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start consignorAutoRemover.suppress(holdDays) ****");
		ConsignorAutoRemover consignorAutoRemover = new ConsignorAutoRemover(conn);
		consignorAutoRemover.setClassName(wProcessName);
		consignorAutoRemover.suppress(wHoldDays);
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   consignorAutoRemover.suppress(holdDays) ****");

		//#CM695789
		/*************************************
		 * Delete the Supplier master info.
		 *************************************/
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start supplierAutoRemover.suppress(holdDays) ****");
		SupplierAutoRemover supplierAutoRemover = new SupplierAutoRemover(conn);
		supplierAutoRemover.setClassName(wProcessName);
		supplierAutoRemover.suppress(wHoldDays);
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   supplierAutoRemover.suppress(holdDays) ****");

		//#CM695790
		/*************************************
		 * Delete the Customer master info.
		 *************************************/
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start customerAutoRemover.suppress(holdDays) ****");
		CustomerAutoRemover customerAutoRemover = new CustomerAutoRemover(conn);
		customerAutoRemover.setClassName(wProcessName);
		customerAutoRemover.suppress(wHoldDays);
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   customerAutoRemover.suppress(holdDays) ****");

		//#CM695791
		/*************************************
		 * Delete the Item master info.
		 *************************************/
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start itemAutoRemover.suppress(holdDays) ****");
		ItemAutoRemover itemAutoRemover = new ItemAutoRemover(conn);
		itemAutoRemover.setClassName(wProcessName);
		itemAutoRemover.suppress(wHoldDays);
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   itemAutoRemover.suppress(holdDays) ****");
	}

	//#CM695792
	/**
	 * Delete every type of log files.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	protected void deleteLog() throws ScheduleException
	{
		try
		{
			//#CM695793
			/***********************************************************************************
			 * Delete the log file.
			 ************************************************************************************/

			//#CM695794
			/* Delete the Message log that passes its retention period. */

DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start clearMessageLog ****");
			deleteMessageLog();
			FileClear.clearMessageLog();
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   clearMessageLog ****");

			//#CM695795
			/* Delete the External data history file that passes its retention period. */

DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start clearOutData ****");
			FileClear.clearOutData();
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   clearOutData ****");

			//#CM695796
			/* Delete the report image file that passes its retention period. */

DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start clearPrintHistory ****");
			FileClear.clearPrintHistory();
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   clearPrintHistory ****");

			//#CM695797
			/* Delete the TOMCAT log file that passes its retention period. */

DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start clearTomcatLog ****");
			FileClear.clearTomcatLog();
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   clearTomcatLog ****");

			//#CM695798
			/* Delete the IIS(FTP) log file that passes its retention period. */

DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start clear IIS(FTP)Log ****") ;
			FileClear.clearIISLog();
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   clear IIS(FTP)Log ****") ;

			//#CM695799
			/* Delete other log files that passes its retention period. */

DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start clear OtherLog ****") ;
			FileClear.clearOtherLog();
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   clear OtherLog ****") ;

			//#CM695800
			/* Compress FTP History File */

DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start RFT-FTP CompressToZipFile  ****") ;
			CompressToZipFile.makeZipFile(WmsParam.FTP_FILE_HISTORY_PATH);
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   RFT-FTP CompressToZipFile ****") ;

			//#CM695801
			/* Delete FTP history file that passes its retention period. */

DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start clear RFT-FTPLog(Zip) File ****") ;
			FileClear.clearFTPBackupFile();
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   clear RFT-FTPLog(Zip) File ****") ;
		}
		catch (Exception e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM695802
	/**
	 * Referring to the System definition table(WARENAVI_SYSTEM), obtain the <CODE>WareNaviSystem<CODE> object package.<BR>
	 * @param conn Database connection object
	 * @return WareNaviSystem object
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	protected WareNaviSystem findWareNaviSystem(Connection conn) throws ReadWriteException, ScheduleException
	{
		WareNaviSystem WareNaviSystem = new WareNaviSystem();
		WareNaviSystemSearchKey skey = new WareNaviSystemSearchKey();
		WareNaviSystemHandler wnhdl = new WareNaviSystemHandler(conn);

		try
		{
			WareNaviSystem = (WareNaviSystem)wnhdl.findPrimary(skey);
			if (WareNaviSystem == null)
			{
				//#CM695803
				//6006002=Database error occurred.{0}
				RmiMsgLogClient.write(6006039, LogMessage.F_ERROR, this.getClass().getName(), null);
				throw (new ScheduleException("6007039" + wDelim + "WARENAVI_SYSTEM"));
			}
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		return WareNaviSystem;
	}

	//#CM695804
	/**
	 * Update the Daily Update flag of <code>WareNaviSystem</code>.<BR>
	 * Caution)<BR>
	 * Submit the transaction after changing the flag in this method.<BR>
	 * @param conn Database connection object
	 * @param status true: Change to "Daily Update in Progress".
	 * 				  false: Reset the process of updating of daily maintenance in progress.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void changeDailyUpdateFlag(Connection conn, boolean status) throws ReadWriteException
	{
		try
		{
			WareNaviSystemAlterKey akey = new WareNaviSystemAlterKey();
			WareNaviSystemHandler wnhdl = new WareNaviSystemHandler(conn);
			akey.setSystemNo(WareNaviSystem.SYSTEM_NO);
			if (status)
			{
				akey.updateDailyUpdate(WareNaviSystem.DAILYUPDATE_LOADING);
			}
			else
			{
				akey.updateDailyUpdate(WareNaviSystem.DAILYUPDATE_STOP);
			}
			wnhdl.modify(akey);
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

	//#CM695805
	/**
	 * Return the retained date.<BR>
	 * @param keepDays Retention Period (days)
	 * @return Retained Date
	 */
	protected String getKeepDateString(int keepDays)
	{
		if (keepDays <= 0)
		{
			return null;
		}
		//#CM695806
		// Obtain the date to be deleted.
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return  getKeepDateStringConvert(cal, keepDays);
	}

	//#CM695807
	/**
	 * Return the retained date.<BR>
	 * @param workDay Work date
	 * @param keepDays Retention Period (days)
	 * @return Retained Date
	 */
	protected String getKeepDateString(String workDay, int keepDays)
	{
		if (keepDays <= 0)
		{
			return null;
		}
		//#CM695808
		// Obtain the date to be deleted.
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(workDay.substring(0, 4)), Integer.parseInt(workDay.substring(4, 6)) - 1, Integer.parseInt(workDay.substring(6, 8)), 0, 0, 0);
		return  getKeepDateStringConvert(cal, keepDays);
	}

	//#CM695809
	/**
	 * Return the retained date.<BR>
	 * @param cal Date
	 * @param keepDays Retention Period (days)
	 * @return Retained Date
	 */
	protected String getKeepDateStringConvert(Calendar cal, int keepDays)
	{
		cal.add(Calendar.DATE, - (keepDays-1));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, -1);

		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");

DEBUG.MSG("SCHEDULE", "削除日" + f.format(cal.getTime()));
		return f.format(cal.getTime());
	}

	//#CM695810
	/**
	 * Set the Work Date of WareNaviSystem forward by one day.
	 * @param conn Database connection object
	 * @return WareNaviSystem object
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	protected void changeWorkDate(Connection conn, WareNaviSystem wareNaviSystem) throws ReadWriteException, ScheduleException
	{
		//#CM695811
		// Variable for storing the Work Date data.
		int workYear = 0;
		int workMonth = 0;
		int workDay = 0;

		//#CM695812
		// Obtain the Work Date.
		workYear = Integer.parseInt( wareNaviSystem.getWorkDate().substring( 0 , 4 ) );
		workMonth = Integer.parseInt( wareNaviSystem.getWorkDate().substring( 4 , 6 ) ) - 1;		// 月だけは1引く
		workDay = Integer.parseInt( wareNaviSystem.getWorkDate().substring( 6 , 8 ) );

		//#CM695813
		// Generate a Calendar object for work.
		GregorianCalendar gc =  new GregorianCalendar();
		gc.set( workYear, workMonth, workDay );

		//#CM695814
		// Set the Work Date forward by one day.
		gc.add(GregorianCalendar.DATE, 1);

		//#CM695815
		// Set the work date forward.
		String nextYear = "";
		String nextMonth = "";
		String nextDay = "";

		//#CM695816
		// Set the Year.
		nextYear = Integer.toString(gc.get(GregorianCalendar.YEAR));

		//#CM695817
		// Set the month.
		if(gc.get(GregorianCalendar.MONTH) + 1 < 10)	// 月だけは1引いたので足しておく
		{
			nextMonth = "0" + Integer.toString(gc.get(GregorianCalendar.MONTH) + 1);
		}
		else
		{
			nextMonth = Integer.toString(gc.get(GregorianCalendar.MONTH) + 1);
		}

		//#CM695818
		// Set the Day.
		if(gc.get(GregorianCalendar.DATE) < 10)
		{
			nextDay = "0" + Integer.toString(gc.get(GregorianCalendar.DATE));
		}
		else
		{
			nextDay = Integer.toString(gc.get(GregorianCalendar.DATE));
		}
		WareNaviSystemHandler wmsHandle = new WareNaviSystemHandler(conn);
		WareNaviSystemAlterKey wareNaviSystemAlterKey = new WareNaviSystemAlterKey();
		wareNaviSystemAlterKey.setSystemNo(WareNaviSystem.SYSTEM_NO);
		wareNaviSystemAlterKey.updateWorkDate(nextYear+nextMonth+nextDay);
		try
		{
			wmsHandle.modify(wareNaviSystemAlterKey);
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//#CM695819
	/**
	 * Set the initial value of screen for <CODE>SystemParameter<CODE> object from <CODE>WareNaviSystem<CODE> object.<BR>に
	 * Set the initial values of screen.
	 * @param WareNaviSystem WareNaviSystem object
	 * @return SystemParameter object
	 */
	protected SystemParameter makeSystemParam(WareNaviSystem wnsys)
	{
		SystemParameter param = new SystemParameter();
		//#CM695820
		// Planned Work Date
		param.setWorkDate(wnsys.getWorkDate());
		return param;
	}

	//#CM695821
	/**
	 * Execute the process for deleting the Plan data.<BR>
	 * @param conn Connection with database
	 * @param unWorkDelete Presence of deletion of data with Not Worked.
	 * @param workDate Work date
	 * @param deleteDate Boundary Date/Time for Delete
	 * @param stockPack Presence of Inventory package.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void planDelete(Connection conn, boolean unWorkDelete, String workDate, String deleteDate, boolean stockPack)
																									throws ReadWriteException
	{
		//#CM695822
		// Delete the receiving plan info.
		deleteInStockPlan(conn, unWorkDelete, workDate, deleteDate);

		//#CM695823
		// Delete the Storage Plan info.
		deleteStoragePlan(conn, unWorkDelete, workDate, deleteDate);

		//#CM695824
		// If Inventory package is "Available":
		if (stockPack)
		{
			//#CM695825
			// Delete the picking plan info.
			deleteRetrievalPlanAddStock(conn, unWorkDelete, workDate, deleteDate, stockPack);
		}
		//#CM695826
		// If Inventory package is "Not Available":
		else
		{
			//#CM695827
			// Delete the picking plan info.
			deleteRetrievalPlan(conn, unWorkDelete, workDate, deleteDate, stockPack);
		}

		//#CM695828
		// Delete the sorting plan info.
		deleteSortingPlan(conn, unWorkDelete, workDate, deleteDate);

		//#CM695829
		// Delete the shipping plan info.
		deleteShippingPlan(conn, unWorkDelete, workDate, deleteDate);
	}

	//#CM695830
	/**
	 * Execute the process for deleting the Receiving plan data.<BR>
	 * @param conn Connection with database
	 * @param unWorkDelete Presence of deletion of data with Not Worked.
	 * @param workDate Work date
	 * @param deleteDate Boundary Date/Time for Delete
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void deleteInStockPlan(Connection conn, boolean unWorkDelete, String workDate, String deleteDate)
																						throws ReadWriteException
	{
		//#CM695831
		// Requirements for status flags to be deleted if the data with status Not Worked are included.
		//#CM695832
		// Planned Work Date <= Work Date (Completed, Standby, or Deleted)
		String[] workStatus1 = { InstockPlan.STATUS_FLAG_COMPLETION,
								 InstockPlan.STATUS_FLAG_UNSTART,
								 InstockPlan.STATUS_FLAG_DELETE
		};
		//#CM695833
		// Data that passed the retention period (days) (Partially Completed)
		String[] saveStatus1 = { InstockPlan.STATUS_FLAG_COMPLETE_IN_PART
		};

		//#CM695834
		// Requirements for status flags to be deleted if the data with status Not Worked are held over.
		//#CM695835
		// Planned Work Date <= Work Date (Completed or Deleted)
		String[] workStatus2 = { InstockPlan.STATUS_FLAG_COMPLETION,
								 InstockPlan.STATUS_FLAG_DELETE
		};
		//#CM695836
		//   Data that passed the retention period (days) (Standby, Partially Completed)
		String[] saveStatus2 = { InstockPlan.STATUS_FLAG_UNSTART,
								 InstockPlan.STATUS_FLAG_COMPLETE_IN_PART
		};

		int count = 0;

		//#CM695837
		// Delete the inventory information.
		//#CM695838
		// Find the Stock ID of the inventory information linked to the delete target data in the receiving plan info. .
		SystemStockFinder systemStockFinder = new SystemStockFinder(conn);
		if( unWorkDelete )
		{
			//#CM695839
			// Search for the data through the inventory information including data with status Not Worked in conditions.
			count = systemStockFinder.InstockPlanStockIdSearch(workDate, workStatus1, deleteDate, saveStatus1);
		}
		else
		{
			//#CM695840
			// Search for the Inventory information using a condition to hold over the data with status Not Worked.
			count = systemStockFinder.InstockPlanStockIdSearch(workDate, workStatus2, deleteDate, saveStatus2);
		}
DEBUG.MSG("SCHEDULE", wProcessName + "    (InStockPlan Delete) --> Stock Delete Data Count(" + count + ")");
		if( count > 0 )
		{
			//#CM695841
			// Delete the inventory information linked to the delete target in the receiving plan info .
			deleteStock_StockId(conn, systemStockFinder);
		}

		//#CM695842
		// Delete the work status.
		//#CM695843
		// Find the work status linked to the delete target data in the receiving plan info .
		SystemWorkingInformationFinder workfinder = new SystemWorkingInformationFinder(conn);
		if( unWorkDelete )
		{
			//#CM695844
			// Search for the data through the Work status including data with status Not Worked in conditions.
			count = workfinder.InstockPlanUkeySearch(workDate, workStatus1, deleteDate, saveStatus1);
		}
		else
		{
			//#CM695845
			// Search for the Work status. using a condition to hold over the data with status Not Worked.
			count = workfinder.InstockPlanUkeySearch(workDate, workStatus2, deleteDate, saveStatus2);
		}
DEBUG.MSG("SCHEDULE", wProcessName + "    (InStockPlan Delete) --> WorkingInformation Delete Data Count(" + count + ")");
		if( count > 0 )
		{
			//#CM695846
			// Delete the work status linked to the delete target in the receiving plan info .
			deleteWorkingInformation_PlanUkey(conn, workfinder);
		}

		//#CM695847
		// Delete the receiving plan info.
		InstockPlanHandler instockHandler = new InstockPlanHandler(conn);
		InstockPlanSearchKey instockSearchKey = new InstockPlanSearchKey();

		if( unWorkDelete )
		{
			//#CM695848
			// Delete the receiving plan info including data with status Not Worked in conditions.
			//#CM695849
			//	 ( ( Status flag = Standby or Completed or Deleted ) and Planned Date <= Work Date of WareNavi System table) 
			instockSearchKey.setStatusFlag(workStatus1[0], "=", "((", "", "or");
			instockSearchKey.setStatusFlag(workStatus1[1], "=", "", "", "or");
			instockSearchKey.setStatusFlag(workStatus1[2], "=", "", ")", "and");
			instockSearchKey.setPlanDate(workDate, "<=", "", ")", "or");

			//#CM695850
			// Delete the receiving plan info that passed its retention period (days).
			//#CM695851
			//	 ( Status flag = Partially Completed and Planned Receiving Date <= Expiry date of retention )
			instockSearchKey.setStatusFlag(saveStatus1[0], "=", "(", "", "and");
			instockSearchKey.setPlanDate(deleteDate, "<=", "", ")", "or");
		}
		else
		{
			//#CM695852
			// Delete the receiving plan info using a condition to hold over the data with status Not Worked.
			//#CM695853
			//	 ( ( Status flag = Completed or Deleted ) and Planned Date <= Work Date of WareNavi System table)
			instockSearchKey.setStatusFlag(workStatus2[0], "=", "((", "", "or");
			instockSearchKey.setStatusFlag(workStatus2[1], "=", "", ")", "and");
			instockSearchKey.setPlanDate(workDate, "<=", "", ")", "or");

			//#CM695854
			// Delete the receiving plan info that passed its retention period (days).
			//#CM695855
			//	 ( ( Status flag = Standby or Partially Completed ) and Planned Receiving Date <= Expiry date of retention )
			instockSearchKey.setStatusFlag(saveStatus2[0], "=", "((", "", "or");
			instockSearchKey.setStatusFlag(saveStatus2[1], "=", "", ")", "and");
			instockSearchKey.setPlanDate(deleteDate, "<=", "", ")", "or");
		}

		try
		{
			count = instockHandler.count(instockSearchKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (InStockPlan Delete) --> InStockPlan Delete Data Count(" + count + ")");
			if (count > 0)
			{
				//#CM695856
				// Delete the receiving plan info.
				instockHandler.drop(instockSearchKey);
			}
		}
		catch (NotFoundException e)
		{
		}
	}

	//#CM695857
	/**
	 * Execute the process for deleting the Storage plan data.<BR>
	 * @param conn Connection with database
	 * @param unWorkDelete Presence of deletion of data with Not Worked.
	 * @param workDate Work date
	 * @param deleteDate Boundary Date/Time for Delete
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void deleteStoragePlan(Connection conn, boolean unWorkDelete, String workDate, String deleteDate)
																						throws ReadWriteException
	{
		//#CM695858
		// Requirements for status flags to be deleted if the data with status Not Worked are included.
		//#CM695859
		// Planned Work Date <= Work Date (Completed, Standby, or Deleted)
		String[] workStatus1 = { StoragePlan.STATUS_FLAG_COMPLETION,
								 StoragePlan.STATUS_FLAG_UNSTART,
								 StoragePlan.STATUS_FLAG_DELETE
		};
		//#CM695860
		// Data that passed the retention period (days) (Partially Completed)
		String[] saveStatus1 = { StoragePlan.STATUS_FLAG_COMPLETE_IN_PART
		};

		//#CM695861
		// Requirements for status flags to be deleted if the data with status Not Worked are held over.
		//#CM695862
		// Planned Work Date <= Work Date (Completed or Deleted)
		String[] workStatus2 = { StoragePlan.STATUS_FLAG_COMPLETION,
								 StoragePlan.STATUS_FLAG_DELETE
		};
		//#CM695863
		//   Data that passed the retention period (days) (Standby, Partially Completed)
		String[] saveStatus2 = { StoragePlan.STATUS_FLAG_UNSTART,
								 StoragePlan.STATUS_FLAG_COMPLETE_IN_PART
		};

		int count = 0;

		//#CM695864
		// Delete the inventory information.
		//#CM695865
		// Find the Stock ID of the inventory information linked to the delete target in the Storage Plan info. .
		SystemStockFinder systemStockFinder = new SystemStockFinder(conn);
		if( unWorkDelete )
		{
			//#CM695866
			// Search for the data through the inventory information including data with status Not Worked in conditions.
			count = systemStockFinder.StoragePlanStockIdSearch(workDate, workStatus1, deleteDate, saveStatus1);
		}
		else
		{
			//#CM695867
			// Search for the Inventory information using a condition to hold over the data with status Not Worked.
			count = systemStockFinder.StoragePlanStockIdSearch(workDate, workStatus2, deleteDate, saveStatus2);
		}
DEBUG.MSG("SCHEDULE", wProcessName + "    (StoragePlan Delete) --> Stock Delete Data Count(" + count + ")");
		if( count > 0 )
		{
			//#CM695868
			// Delete the inventory information linked to the delete target in the Storage Plan info. .
			deleteStock_StockId(conn, systemStockFinder);
		}

		//#CM695869
		// Delete the work status.
		//#CM695870
		// Find the work status linked to the delete target in the Storage Plan info .
		SystemWorkingInformationFinder sysWorkInfoFinder = new SystemWorkingInformationFinder(conn);
		if( unWorkDelete )
		{
			//#CM695871
			// Search for the data through the Work status including data with status Not Worked in conditions.
			count = sysWorkInfoFinder.StoragePlanUkeySearch(workDate, workStatus1, deleteDate, saveStatus1);
		}
		else
		{
			//#CM695872
			// Search for the Work status. using a condition to hold over the data with status Not Worked.
			count = sysWorkInfoFinder.StoragePlanUkeySearch(workDate, workStatus2, deleteDate, saveStatus2);
		}
DEBUG.MSG("SCHEDULE", wProcessName + "    (StoragePlan Delete) --> WorkInformation Delete Data Count(" + count + ")");
		if( count > 0 )
		{
			//#CM695873
			// Delete the Work status linked to the Storage plan info subject to delete.
			deleteWorkingInformation_PlanUkey(conn, sysWorkInfoFinder);
		}

		//#CM695874
		// Delete the Storage Plan info.
		StoragePlanHandler storageHandler = new StoragePlanHandler(conn);
		StoragePlanSearchKey storageSerchKey = new StoragePlanSearchKey();

		if( unWorkDelete )
		{
			//#CM695875
			// Delete the Storage Plan info including data with status Not Worked in conditions.
			//#CM695876
			//	 ( ( Status flag = Standby or Completed or Deleted ) and Planned Date <= Work Date of WareNavi System table) 
			storageSerchKey.setStatusFlag(workStatus1[0], "=", "((", "", "or");
			storageSerchKey.setStatusFlag(workStatus1[1], "=", "", "", "or");
			storageSerchKey.setStatusFlag(workStatus1[2], "=", "", ")", "and");
			storageSerchKey.setPlanDate(workDate, "<=", "", ")", "or");

			//#CM695877
			// Delete the Storage Plan info that exceeds retention period (days).
			//#CM695878
			//	 ( Status flag = Partially Completed and Planned Storage Date <= Expiry date of retention )
			storageSerchKey.setStatusFlag(saveStatus1[0], "=", "(", "", "and");
			storageSerchKey.setPlanDate(deleteDate, "<=", "", ")", "or");
		}
		else
		{
			//#CM695879
			// Delete the Storage Plan info using a condition to hold over the data with status Not Worked.
			//#CM695880
			//	 ( ( Status flag = Completed or Deleted ) and Planned Date <= Work Date of WareNavi System table)
			storageSerchKey.setStatusFlag(workStatus2[0], "=", "((", "", "or");
			storageSerchKey.setStatusFlag(workStatus2[1], "=", "", ")", "and");
			storageSerchKey.setPlanDate(workDate, "<=", "", ")", "or");

			//#CM695881
			// Delete the Storage Plan info that exceeds retention period (days).
			//#CM695882
			//	 ( ( Status flag = Standby or Partially Completed ) and Planned Storage Date <= Expiry date of retention )
			storageSerchKey.setStatusFlag(saveStatus2[0], "=", "((", "", "or");
			storageSerchKey.setStatusFlag(saveStatus2[1], "=", "", ")", "and");
			storageSerchKey.setPlanDate(deleteDate, "<=", "", ")", "or");
		}

		try
		{
			count = storageHandler.count(storageSerchKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (StoragePlan Delete) --> StoragePlan Delete Data Count(" + count + ")");
			if (count > 0)
			{
				//#CM695883
				// Delete the Storage Plan info.
				storageHandler.drop(storageSerchKey);
			}
		}
		catch (NotFoundException e)
		{
		}
	}

	//#CM695884
	/**
	 * Execute the process for deleting the Picking plan data (Inventory package Not Available)<BR>
	 * @param conn Connection with database
	 * @param unWorkDelete Presence of deletion of data with Not Worked.
	 * @param workDate Work date
	 * @param deleteDate Boundary Date/Time for Delete
	 * @param stockPack Presence of Inventory package.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void deleteRetrievalPlan(Connection conn, boolean unWorkDelete, String workDate
													  , String deleteDate, boolean stockPack)
																				throws ReadWriteException
	{
		//#CM695885
		// Requirements for status flags to be deleted if the data with status Not Worked are included.
		//#CM695886
		// Planned Work Date <= Work Date (Completed, Standby, or Deleted)
		String[] workStatus1 = { RetrievalPlan.STATUS_FLAG_COMPLETION,
								 RetrievalPlan.STATUS_FLAG_UNSTART,
								 RetrievalPlan.STATUS_FLAG_DELETE
		};
		//#CM695887
		// Data that passed the retention period (days) (Partially Completed)
		String[] saveStatus1 = { RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART
		};

		//#CM695888
		// Requirements for status flags to be deleted if the data with status Not Worked are held over.
		//#CM695889
		// Planned Work Date <= Work Date (Completed or Deleted)
		String[] workStatus2 = { RetrievalPlan.STATUS_FLAG_COMPLETION,
								 RetrievalPlan.STATUS_FLAG_DELETE
		};
		//#CM695890
		//   Data that passed the retention period (days) (Standby, Partially Completed)
		String[] saveStatus2 = { RetrievalPlan.STATUS_FLAG_UNSTART,
								 RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART
		};

		int count = 0;

		//#CM695891
		// Delete the inventory information.
		//#CM695892
		// Find the Stock ID of the inventory information linked to the delete target data in the picking plan info .
		SystemStockFinder systemStockFinder = new SystemStockFinder(conn);
		if( unWorkDelete )
		{
			//#CM695893
			// Search for the data through the inventory information including data with status Not Worked in conditions.
			count = systemStockFinder.RetrievalPlanStockIdSearch(workDate, workStatus1, deleteDate, saveStatus1);
DEBUG.MSG("SCHEDULE", wProcessName + "    (RetrievalPlan Delete) --> Stock Delete Data Count(" + count + ")");
			if( count > 0 )
			{
				//#CM695894
				// Delete the inventory information linked to the delete target in the picking plan info .
				deleteStock_StockId(conn, systemStockFinder);
			}
		}
		else
		{
			//#CM695895
			// Search for the Inventory information using a condition to hold over the data with status Not Worked.
			count = systemStockFinder.RetrievalPlanStockIdSearch(workDate, workStatus2, deleteDate, saveStatus2);
DEBUG.MSG("SCHEDULE", wProcessName + "    (RetrievalPlan Delete) --> Stock Delete Data Count(" + count + ")");
			if( count > 0 )
			{
				//#CM695896
				// Delete the inventory information linked to the delete target in the picking plan info .
				deleteStock_StockId(conn, systemStockFinder);
			}
		}

		//#CM695897
		// Delete the work status.
		//#CM695898
		// Find the work status linked to the delete target data in the picking plan info .
		SystemWorkingInformationFinder sysWorkInfoFinder = new SystemWorkingInformationFinder(conn);
		if( unWorkDelete )
		{
			//#CM695899
			// Search for the data through the Work status including data with status Not Worked in conditions.
			count = sysWorkInfoFinder.RetrievalPlanUkeySearch(workDate, workStatus1, deleteDate, saveStatus1);
		}
		else
		{
			//#CM695900
			// Search for the Work status. using a condition to hold over the data with status Not Worked.
			count = sysWorkInfoFinder.RetrievalPlanUkeySearch(workDate, workStatus2, deleteDate, saveStatus2);
		}
DEBUG.MSG("SCHEDULE", wProcessName + "    (RetrievalPlan Delete) --> WorkInformation Delete Data Count(" + count + ")");
		if( count > 0 )
		{
			//#CM695901
			// Delete the work status linked to the delete target in the picking plan info. .
			deleteWorkingInformation_PlanUkey(conn, sysWorkInfoFinder);
		}

		//#CM695902
		// Delete the picking plan info.
		RetrievalPlanHandler retrievalHandler = new RetrievalPlanHandler(conn);
		RetrievalPlanSearchKey retrievalSearchKey = new RetrievalPlanSearchKey();

		if( unWorkDelete )
		{
			//#CM695903
			// Delete the picking plan info including data with status Not Worked in conditions.
			//#CM695904
			//	 ( ( Status flag = Standby or Completed or Deleted ) and Planned Date <= Work Date of WareNavi System table)
			retrievalSearchKey.setStatusFlag(workStatus1[0], "=", "((", "", "or");
			retrievalSearchKey.setStatusFlag(workStatus1[1], "=", "", "", "or");
			retrievalSearchKey.setStatusFlag(workStatus1[2], "=", "", ")", "and");
			retrievalSearchKey.setPlanDate(workDate, "<=", "", ")", "or");

			//#CM695905
			// Delete the picking plan info that passed its retention period (days).
			//#CM695906
			//	 ( Status flag = Partially Completed and Planned Picking Date <= Expiry date of retention )
			retrievalSearchKey.setStatusFlag(saveStatus1[0], "=", "(", "", "and");
			retrievalSearchKey.setPlanDate(deleteDate, "<=", "", ")", "or");
		}
		else
		{
			//#CM695907
			// Delete the picking plan info using a condition to hold over the data with status Not Worked.
			//#CM695908
			//	 ( ( Status flag = Completed or Deleted ) and Planned Date <= Work Date of WareNavi System table)
			retrievalSearchKey.setStatusFlag(workStatus2[0], "=", "((", "", "or");
			retrievalSearchKey.setStatusFlag(workStatus2[1], "=", "", ")", "and");
			retrievalSearchKey.setPlanDate(workDate, "<=", "", ")", "or");

			//#CM695909
			// Delete the picking plan info that passed its retention period (days).
			//#CM695910
			//	 ( ( Status flag = Standby or Partially Completed ) and Planned Picking Date <= Expiry date of retention )
			retrievalSearchKey.setStatusFlag(saveStatus2[0], "=", "((", "", "or");
			retrievalSearchKey.setStatusFlag(saveStatus2[1], "=", "", ")", "and");
			retrievalSearchKey.setPlanDate(deleteDate, "<=", "", ")", "or");
		}

		try
		{
			count = retrievalHandler.count(retrievalSearchKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (RetrievalPlan Delete) --> RetrievalPlan Delete Data Count(" + count + ")");
			if (count > 0)
			{
				//#CM695911
				// Delete the picking plan info.
				retrievalHandler.drop(retrievalSearchKey);
			}
		}
		catch (NotFoundException e)
		{
		}
	}

	//#CM695912
	/**
	 * Execute the process for deleting the Picking plan data (Inventory package Available)<BR>
	 * @param conn Connection with database
	 * @param unWorkDelete Presence of deletion of data with Not Worked.
	 * @param workDate Work date
	 * @param deleteDate Boundary Date/Time for Delete
	 * @param stockPack Presence of Inventory package.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void deleteRetrievalPlanAddStock(Connection conn, boolean unWorkDelete, String workDate
													  , String deleteDate, boolean stockPack)
																				throws ReadWriteException
	{
		//#CM695913
		// Requirements for status flags to be deleted if the data with status Not Worked are included.
		//#CM695914
		// Planned Work Date <= Work Date (Completed, Deleted, or Standby)
		String[] workStatus1 = { RetrievalPlan.STATUS_FLAG_COMPLETION,
				                 RetrievalPlan.STATUS_FLAG_DELETE,
				                 RetrievalPlan.STATUS_FLAG_UNSTART
		};
		
		//#CM695915
		// Conditions to delete the work status when selected to Delete Plan (Completed or Deleted)
		String[] workStatusAdd = { RetrievalPlan.STATUS_FLAG_COMPLETION,
				                   RetrievalPlan.STATUS_FLAG_DELETE
		};

		//#CM695916
		// Requirements for status flags to be deleted if the data with status Not Worked are held over.
		//#CM695917
		// Planned Work Date <= Work Date (Completed or Deleted)
		String[] workStatus2 = { RetrievalPlan.STATUS_FLAG_COMPLETION,
								 RetrievalPlan.STATUS_FLAG_DELETE
		};

		//#CM695918
		//   Data that passed the retention period (days) (Standby)
		String[][] saveStatus2 = { { RetrievalPlan.STATUS_FLAG_UNSTART, RetrievalPlan.SCH_FLAG_UNSTART }
		};
		int count = 0;

		//#CM695919
		// Delete the work status.
		//#CM695920
		// Find the work status linked to the delete target data in the picking plan info .
		SystemWorkingInformationFinder sysWorkInfoFinder = new SystemWorkingInformationFinder(conn);
		if( unWorkDelete )
		{
			//#CM695921
			// Search for data with status Not Worked even after processing the schedule through the work status using a hold-over condition.
			//#CM695922
			// Do not include any data with work status "Standby" in the conditions for delete.
			count = sysWorkInfoFinder.RetrievalPlanUkeySearch(workDate, workStatusAdd);
		}
		else
		{
			//#CM695923
			// Search for the Work status. using a condition to hold over the data with status Not Worked.
			count = sysWorkInfoFinder.RetrievalPlanUkeySearch(workDate, workStatus2, deleteDate, saveStatus2);
		}
DEBUG.MSG("SCHEDULE", wProcessName + "    (RetrievalPlan Add Stock Delete) --> WorkInformation Add Stock Delete Data Count(" + count + ")");
		if( count > 0 )
		{
			//#CM695924
			// Delete the work status linked to the delete target in the picking plan info. .
			deleteWorkingInformation_PlanUkey(conn, sysWorkInfoFinder);
		}

		//#CM695925
		// Delete the picking plan info.
		RetrievalPlanHandler retrievalHandler = new RetrievalPlanHandler(conn);
		RetrievalPlanSearchKey retrievalSearchKey = new RetrievalPlanSearchKey();

		if( unWorkDelete )
		{
			//#CM695926
			// Delete the picking plan info including data with status Not Worked in conditions.
			//#CM695927
			//	 ( ( Status flag = Completed or Deleted or (Standby and with Schedule Processing flag "Standby")) and Planned Date <= Work Date of WareNavi System table)
			retrievalSearchKey.setStatusFlag(workStatus1[0], "=", "((", "", "or");
			retrievalSearchKey.setStatusFlag(workStatus1[1], "=", "", "", "or");
			retrievalSearchKey.setStatusFlag(workStatus1[2], "=", "(", "", "and");
			retrievalSearchKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "", "))", "and");
			retrievalSearchKey.setPlanDate(workDate, "<=", "", ")", "or");

		}
		else
		{
			//#CM695928
			// Delete the picking plan info using a condition to hold over the data with status Not Worked.
			//#CM695929
			//	 ( ( Status flag = Completed or Deleted ) and Planned Date <= Work Date of WareNavi System table)
			retrievalSearchKey.setStatusFlag(workStatus2[0], "=", "((", "", "or");
			retrievalSearchKey.setStatusFlag(workStatus2[1], "=", "", ")", "and");
			retrievalSearchKey.setPlanDate(workDate, "<=", "", ")", "or");

			//#CM695930
			// Delete the picking plan info that passed its retention period (days).
			//#CM695931
			//	 ( Status flag = Standby and Schedule Processing flag = Standby) and Planned Picking Date <= Expiry date of retention )
			retrievalSearchKey.setStatusFlag(saveStatus2[0][0], "=", "((", "", "and");
			retrievalSearchKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "", ")", "and");
			retrievalSearchKey.setPlanDate(deleteDate, "<=", "", ")", "or");
		}

		try
		{
			count = retrievalHandler.count(retrievalSearchKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (RetrievalPlan Add Stock Delete) --> RetrievalPlan Add Stock Delete Data Count(" + count + ")");
			if (count > 0)
			{
				//#CM695932
				// Delete the picking plan info.
				retrievalHandler.drop(retrievalSearchKey);
			}
		}
		catch (NotFoundException e)
		{
		}
	}

	//#CM695933
	/**
	 * Execute the process for deleting the Sorting plan data.<BR>
	 * @param conn Connection with database
	 * @param unWorkDelete Presence of deletion of data with Not Worked.
	 * @param workDate Work date
	 * @param deleteDate Boundary Date/Time for Delete
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void deleteSortingPlan(Connection conn, boolean unWorkDelete, String workDate, String deleteDate)
																						throws ReadWriteException
	{
		//#CM695934
		// Requirements for status flags to be deleted if the data with status Not Worked are included.
		//#CM695935
		// Planned Work Date <= Work Date (Completed, Standby, or Deleted)
		String[] workStatus1 = { SortingPlan.STATUS_FLAG_COMPLETION,
								 SortingPlan.STATUS_FLAG_UNSTART,
								 SortingPlan.STATUS_FLAG_DELETE
		};
		//#CM695936
		// Data that passed the retention period (days) (Partially Completed)
		String[] saveStatus1 = { SortingPlan.STATUS_FLAG_COMPLETE_IN_PART
		};

		//#CM695937
		// Requirements for status flags to be deleted if the data with status Not Worked are held over.
		//#CM695938
		// Planned Work Date <= Work Date (Completed or Deleted)
		String[] workStatus2 = { SortingPlan.STATUS_FLAG_COMPLETION,
								 SortingPlan.STATUS_FLAG_DELETE
		};
		//#CM695939
		//   Data that passed the retention period (days) (Standby, Partially Completed)
		String[] saveStatus2 = { SortingPlan.STATUS_FLAG_UNSTART,
								 SortingPlan.STATUS_FLAG_COMPLETE_IN_PART
		};

		int count = 0;

		//#CM695940
		// Delete the inventory information.
		//#CM695941
		// Find the Stock ID of the inventory information linked to the delete target data in the sorting plan info. .
		SystemStockFinder systemStockFinder = new SystemStockFinder(conn);
		if( unWorkDelete )
		{
			//#CM695942
			// Search for the data through the inventory information including data with status Not Worked in conditions.
			count = systemStockFinder.SortingPlanStockIdSearch(workDate, workStatus1, deleteDate, saveStatus1);
		}
		else
		{
			//#CM695943
			// Search for the Inventory information using a condition to hold over the data with status Not Worked.
			count = systemStockFinder.SortingPlanStockIdSearch(workDate, workStatus2, deleteDate, saveStatus2);
		}
DEBUG.MSG("SCHEDULE", wProcessName + "    (SortingPlan Delete) --> Stock Delete Data Count(" + count + ")");
		if( count > 0 )
		{
			//#CM695944
			// Delete the inventory information linked to the delete target in the sorting plan info .
			deleteStock_StockId(conn, systemStockFinder);
		}

		//#CM695945
		// Delete the work status.
		//#CM695946
		// Find the work status linked to the delete target data in the sorting plan info. .
		SystemWorkingInformationFinder sysWorkInfoFinder = new SystemWorkingInformationFinder(conn);
		if( unWorkDelete )
		{
			//#CM695947
			// Search for the data through the Work status including data with status Not Worked in conditions.
			count = sysWorkInfoFinder.SortingPlanUkeySearch(workDate, workStatus1, deleteDate, saveStatus1);
		}
		else
		{
			//#CM695948
			// Search for the Work status. using a condition to hold over the data with status Not Worked.
			count = sysWorkInfoFinder.SortingPlanUkeySearch(workDate, workStatus2, deleteDate, saveStatus2);
		}
DEBUG.MSG("SCHEDULE", wProcessName + "    (SortingPlan Delete) --> WorkInformation Delete Data Count(" + count + ")");
		if( count > 0 )
		{
			//#CM695949
			// Delete the work status linked to the delete target in the sorting plan info .
			deleteWorkingInformation_PlanUkey(conn, sysWorkInfoFinder);
		}

		//#CM695950
		// Delete the sorting plan info.
		SortingPlanHandler sortingHandler = new SortingPlanHandler(conn);
		SortingPlanSearchKey sortingSearchKey = new SortingPlanSearchKey();

		if( unWorkDelete )
		{
			//#CM695951
			// Delete the sorting plan info including data with status Not Worked in conditions.
			//#CM695952
			//	 ( ( Status flag = Standby or Completed or Deleted ) and Planned Date <= Work Date of WareNavi System table) 
			sortingSearchKey.setStatusFlag(workStatus1[0], "=", "((", "", "or");
			sortingSearchKey.setStatusFlag(workStatus1[1], "=", "", "", "or");
			sortingSearchKey.setStatusFlag(workStatus1[2], "=", "", ")", "and");
			sortingSearchKey.setPlanDate(workDate, "<=", "", ")", "or");

			//#CM695953
			// Delete the sorting plan info that passed its retention period (days).
			//#CM695954
			//	 ( Status flag = Partially Completed and Planned Sorting Date <= Expiry date of retention )
			sortingSearchKey.setStatusFlag(saveStatus1[0], "=", "(", "", "and");
			sortingSearchKey.setPlanDate(deleteDate, "<=", "", ")", "or");
		}
		else
		{
			//#CM695955
			// Delete the sorting plan info using a condition to hold over the data with status Not Worked.
			//#CM695956
			//	 ( ( Status flag = Completed or Deleted ) and Planned Date <= Work Date of WareNavi System table)
			sortingSearchKey.setStatusFlag(workStatus2[0], "=", "((", "", "or");
			sortingSearchKey.setStatusFlag(workStatus2[1], "=", "", ")", "and");
			sortingSearchKey.setPlanDate(workDate, "<=", "", ")", "or");

			//#CM695957
			// Delete the sorting plan info that passed its retention period (days).
			//#CM695958
			//	 ( ( Status flag = Standby or Partially Completed ) and Planned Sorting Date <= Expiry date of retention )
			sortingSearchKey.setStatusFlag(saveStatus2[0], "=", "((", "", "or");
			sortingSearchKey.setStatusFlag(saveStatus2[1], "=", "", ")", "and");
			sortingSearchKey.setPlanDate(deleteDate, "<=", "", ")", "or");
		}

		try
		{
			count = sortingHandler.count(sortingSearchKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (SortingPlan Delete) --> SortingPlan Delete Data Count(" + count + ")");
			if (count > 0)
			{
				//#CM695959
				// Delete the sorting plan info.
				sortingHandler.drop(sortingSearchKey);
			}
		}
		catch (NotFoundException e)
		{
		}
	}

	//#CM695960
	/**
	 * Execute the process for deleting the Shipping plan data.<BR>
	 * @param conn Connection with database
	 * @param unWorkDelete Presence of deletion of data with Not Worked.
	 * @param workDate Work date
	 * @param deleteDate Boundary Date/Time for Delete
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void deleteShippingPlan(Connection conn, boolean unWorkDelete, String workDate, String deleteDate)
																						throws ReadWriteException
	{
		//#CM695961
		// Requirements for status flags to be deleted if the data with status Not Worked are included.
		//#CM695962
		// Planned Work Date <= Work Date (Completed, Standby, or Deleted)
		String[] workStatus1 = { ShippingPlan.STATUS_FLAG_COMPLETION,
								 ShippingPlan.STATUS_FLAG_UNSTART,
								 ShippingPlan.STATUS_FLAG_DELETE
		};
		//#CM695963
		// Data that passed the retention period (days) (Partially Completed)
		String[] saveStatus1 = { ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART
		};

		//#CM695964
		//Requirements for status flags to be deleted if the data with status Not Worked are held over.
		//#CM695965
		// Planned Work Date <= Work Date (Completed or Deleted)
		String[] workStatus2 = { ShippingPlan.STATUS_FLAG_COMPLETION,
								 ShippingPlan.STATUS_FLAG_DELETE
		};
		//#CM695966
		//   Data that passed the retention period (days) (Standby, Partially Completed)
		String[] saveStatus2 = { ShippingPlan.STATUS_FLAG_UNSTART,
								 ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART
		};

		int count = 0;

		//#CM695967
		// Delete the inventory information.
		//#CM695968
		// Find the Stock ID of the inventory information linked to the delete target data in the shipping plan info.
		SystemStockFinder systemStockFinder = new SystemStockFinder(conn);
		if( unWorkDelete )
		{
			//#CM695969
			// Search for the data through the inventory information including data with status Not Worked in conditions.
			count = systemStockFinder.ShippingPlanStockIdSearch(workDate, workStatus1, deleteDate, saveStatus1);
		}
		else
		{
			//#CM695970
			// Search for the Inventory information using a condition to hold over the data with status Not Worked.
			count = systemStockFinder.ShippingPlanStockIdSearch(workDate, workStatus2, deleteDate, saveStatus2);
		}
DEBUG.MSG("SCHEDULE", wProcessName + "    (ShippingPlan Delete) --> Stock Delete Data Count(" + count + ")");
		if( count > 0 )
		{
			//#CM695971
			// Delete the inventory information linked to the delete target in the shipping plan info.
			deleteStock_StockId(conn, systemStockFinder);
		}

		//#CM695972
		//Delete the work status.
		//#CM695973
		// Find the work status linked to the delete target data in the shipping plan info. .
		SystemWorkingInformationFinder sysWorkInfoFinder = new SystemWorkingInformationFinder(conn);
		if( unWorkDelete )
		{
			//#CM695974
			// Search for the data through the Work status including data with status Not Worked in conditions.
			count = sysWorkInfoFinder.ShippingPlanUkeySearch(workDate, workStatus1, deleteDate, saveStatus1);
		}
		else
		{
			//#CM695975
			// Search for the Work status. using a condition to hold over the data with status Not Worked.
			count = sysWorkInfoFinder.ShippingPlanUkeySearch(workDate, workStatus2, deleteDate, saveStatus2);
		}
DEBUG.MSG("SCHEDULE", wProcessName + "    (ShippingPlan Delete) --> WorkInformation Delete Data Count(" + count + ")");
		if( count > 0 )
		{
			//#CM695976
			// Delete the work status linked to the delete target in the shipping plan info.
			deleteWorkingInformation_PlanUkey(conn, sysWorkInfoFinder);
		}

		//#CM695977
		//Delete the shipping plan info.
		ShippingPlanHandler shippingHandler = new ShippingPlanHandler(conn);
		ShippingPlanSearchKey shippingSearchKey = new ShippingPlanSearchKey();

		if( unWorkDelete )
		{
			//#CM695978
			// Delete the shipping plan info including data with status Not Worked in conditions.
			//#CM695979
			//	 ( ( Status flag = Standby or Completed or Deleted ) and Planned Date <= Work Date of WareNavi System table) 
			shippingSearchKey.setStatusFlag(workStatus1[0], "=", "((", "", "or");
			shippingSearchKey.setStatusFlag(workStatus1[1], "=", "", "", "or");
			shippingSearchKey.setStatusFlag(workStatus1[2], "=", "", ")", "and");
			shippingSearchKey.setPlanDate(workDate, "<=", "", ")", "or");

			//#CM695980
			// Delete the shipping plan info that passed its retention period (days).
			//#CM695981
			//	 ( Status flag = Partially Completed and Planned Shipping Date <= Expiry date of retention )
			shippingSearchKey.setStatusFlag(saveStatus1[0], "=", "(", "", "and");
			shippingSearchKey.setPlanDate(deleteDate, "<=", "", ")", "or");
		}
		else
		{
			//#CM695982
			// Delete the shipping plan info using a condition to hold over the data with status Not Worked.
			//#CM695983
			//	 ( ( Status flag = Completed or Deleted ) and Planned Date <= Work Date of WareNavi System table)
			shippingSearchKey.setStatusFlag(workStatus2[0], "=", "((", "", "or");
			shippingSearchKey.setStatusFlag(workStatus2[1], "=", "", ")", "and");
			shippingSearchKey.setPlanDate(workDate, "<=", "", ")", "or");

			//#CM695984
			// Delete the shipping plan info that passed its retention period (days).
			//#CM695985
			//	 ( ( Status flag = Standby or Partially Completed ) and Planned Shipping Date <= Expiry date of retention )
			shippingSearchKey.setStatusFlag(saveStatus2[0], "=", "((", "", "or");
			shippingSearchKey.setStatusFlag(saveStatus2[1], "=", "", ")", "and");
			shippingSearchKey.setPlanDate(deleteDate, "<=", "", ")", "or");
		}

		try
		{
			count = shippingHandler.count(shippingSearchKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (ShippingPlan Delete) --> ShippingPlan Delete Data Count(" + count + ")");
			if (count > 0)
			{
				//#CM695986
				// Delete the shipping plan info.
				shippingHandler.drop(shippingSearchKey);
			}
		}
		catch (NotFoundException e)
		{
		}
	}

	//#CM695987
	/**
	 * Delete the inventory using Stock ID key.<BR>
	 * @param conn Connection with database
	 * @param vfinder StockFinder instance
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void deleteStock_StockId(Connection conn, SystemStockFinder vfinder) throws ReadWriteException
	{
		try
		{
			SystemStockHandler systemStockHandler = new SystemStockHandler(conn);

			while (vfinder.isNext())
			{
				//#CM695988
				// Obtain every 100 the search results.
				String[] stockId = vfinder.getStockIdArray(100);
				if( stockId.length > 0 )
				{
					//#CM695989
					// Delete the inventory information using the obtained Stock ID.
					systemStockHandler.dropStock_MultiStockId(stockId);
				}
			}
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM695990
	/**
	 * Delete the work data using a Plan unique key.<BR>
	 * @param conn Connection with database
	 * @param vfinder StockFinder instance
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void deleteWorkingInformation_PlanUkey(Connection conn, SystemWorkingInformationFinder vfinder)
																					throws ReadWriteException
	{
		try
		{
			SystemWorkingInformationHandler sysWorkInfoHandler = new SystemWorkingInformationHandler(conn);

			while (vfinder.isNext())
			{
				//#CM695991
				// Obtain every 100 the search results.
				String[] planUkey = vfinder.getPlanUkeyArray(100);
				if( planUkey.length > 0 )
				{
					//#CM695992
					// Delete the work status using the obtained plan unique key.
					sysWorkInfoHandler.dropWorkInfo_MultiPlanUkey(planUkey);
				}
			}
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM695993
	/**
	 * Execute the process for canceling the allocation. <BR>
	 * Execute only the process for restoring the allocation of the inventory information. Disable to update or delete any work status. <BR>
	 * @param conn        Connection for database connection
	 * @param planUkey    Picking Plan unique key
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected boolean allocateCancel(Connection conn, String planUkey) throws ReadWriteException
	{
		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey workInfoSearckKey = new WorkingInformationSearchKey();

		//#CM695994
		// Read the Work Status (by designating exclusively).
		workInfoSearckKey.KeyClear();
		//#CM695995
		// Plan unique key matches.
		workInfoSearckKey.setPlanUkey(planUkey);

		WorkingInformation[] workInfo = (WorkingInformation[]) workInfoHandler.findForUpdate(workInfoSearckKey);

		//#CM695996
		// Give false if no target work status was found.
		if (workInfo.length <= 0)
		{
			return false;
		}

		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey stockSKey = new StockSearchKey();
		StockAlterKey stockAltKey = new StockAlterKey();

		//#CM695997
		// Execute the process for Work Status.
		for (int count = 0; count < workInfo.length; count++)
		{
			allocateCancelStock(stockHandler, stockSKey, stockAltKey
											, workInfo[count].getStockId(), workInfo[count].getPlanEnableQty());
		}
		return true;
	}

	//#CM695998
	/**
	 * Execute the process for canceling the allocated inventory information. <BR>
	 * @param handler     Stock handler instance
	 * @param skey        Stock Search key Picking Plan unique key
	 * @param akey        Stock Search key
	 * @param stockID     Stock ID
	 * @param allocQty    Allocated qty
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected boolean allocateCancelStock(StockHandler handler, StockSearchKey skey, StockAlterKey akey
															 , String stockID, int allocQty) throws ReadWriteException
	{
		try
		{
			//#CM695999
			// Read the Inventory information (by designating exclusively).
			skey.KeyClear();
			//#CM696000
			// It matches to the stock ID in the Work Status.
			skey.setStockId(stockID);

			Stock stock = (Stock) handler.findPrimaryForUpdate(skey);
			if( stock == null )
			{
				return false;
			}

			//#CM696001
			// Update the inventory information .
			akey.KeyClear();
			//#CM696002
			// Stock ID matches.
			akey.setStockId(stock.getStockId());
			//#CM696003
			// Increase the allocatable qty by possible work qty of Work Status.
			akey.updateAllocationQty(stock.getAllocationQty() + allocQty);
			//#CM696004
			// Update the Update the last Update process name.
			akey.updateLastUpdatePname(wProcessName);
			handler.modify(akey);

			return true;
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM696005
	/**
	 * Delete the Inventory check info.<BR>
	 * @param conn Connection with database
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void deleteInventoryCheck(Connection conn) throws ReadWriteException
	{
		InventoryCheckHandler inventoryHandler = new InventoryCheckHandler(conn);
		InventoryCheckSearchKey inventorySearckKey = new InventoryCheckSearchKey();

		try
		{
			String status[] = { InventoryCheck.STATUS_FLAG_DECISION, InventoryCheck.STATUS_FLAG_DELETE };
			inventorySearckKey.setStatusFlag(status);
			int count = inventoryHandler.count(inventorySearckKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (deleteInventoryCheck) --> InventoryCheck Delete Data Count(" + count + ")");
			if(count > 0)
			{
				inventoryHandler.drop(inventorySearckKey);
			}
		}
		catch (NotFoundException e)
		{
		}
	}

	//#CM696006
	/**
	 * Delete the relocation work status.<BR>
	 * @param conn Connection with database
	 * @param delPlanDate Date of the day when Plan data was deleted.
	 * @param stockPack Presence of Inventory package.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void deleteMovement(Connection conn, String delPlanDate, boolean stockPack) throws ReadWriteException
	{
		//#CM696007
		// Delete the relocation work status.
		MovementHandler moveHandler = new MovementHandler(conn);
		MovementSearchKey moveSearchKey = new MovementSearchKey();

		//#CM696008
		// Delete the data with Status flag = Completed or Deleted.
		String status[] = { Movement.STATUSFLAG_COMPLETION, Movement.STATUSFLAG_DELETE };
		moveSearchKey.setStatusFlag(status);
		try
		{
			int count = moveHandler.count(moveSearchKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (deleteMovement) --> Movement Delete Data Count(" + count + ")");
			if(count > 0)
			{
				moveHandler.drop(moveSearchKey);
			}
		}
		catch (NotFoundException e)
		{
		}

		try
		{
			//#CM696009
			// Refer to the Presence of Inventory package.
			if( stockPack )
			{
				//#CM696010
				// Presence of Inventory package
				//#CM696011
				// Clear the allocation of data, which passed the Plan data retention period (days), with Status flag = Standby Storage.
				moveSearchKey.KeyClear();
				moveSearchKey.setStatusFlag(Movement.STATUSFLAG_UNSTART);
				moveSearchKey.setWorkDate(delPlanDate, "<=");

				//#CM696012
				// Check whether there is allocation of relocation work data to be cleared.
				int count = moveHandler.count(moveSearchKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (deleteMovement) --> Stock Allocate Clear Data Count(" + count + ")");
				if(count > 0)
				{
					StockHandler stockHandler = new StockHandler(conn);
					StockSearchKey stockSKey = new StockSearchKey();
					StockAlterKey stockAltKey = new StockAlterKey();

					//#CM696013
					// Here, clear the allocated inventory only. Allow the following process to delete the work status of the allocated inventory that is cleared here.
					//#CM696014
					// 
					Movement[] mov = (Movement[]) moveHandler.find(moveSearchKey);
					for( int i = 0; i < mov.length; i++ )
					{
						allocateCancelStock(stockHandler, stockSKey, stockAltKey, mov[i].getStockId(), mov[i].getPlanQty());
					}
				}
			}
			//#CM696015
			// Delete the data with Status flag = Standby Storage and that passes the retention period (days) of Plan data.
			moveSearchKey.KeyClear();
			moveSearchKey.setStatusFlag(Movement.STATUSFLAG_UNSTART);
			moveSearchKey.setWorkDate(delPlanDate, "<=");
			int count = moveHandler.count(moveSearchKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (deleteMovement) --> Hold Date Over Movement Delete Data Count(" + count + ")");
			if(count > 0)
			{
				moveHandler.drop(moveSearchKey);
			}
		}
		catch (NotFoundException e)
		{
		}
	}

	//#CM696016
	/**
	 * Delete the Center Inventory.<BR>
	 * @param conn Connection with database
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void deleteCenterStock(Connection conn) throws ReadWriteException
	{
		//#CM696017
		// Delete the inventory information.
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey stockSearchKey = new StockSearchKey();

		//#CM696018
		// Delete the data of Center Inventory with Stock Qty equal to 0.
		stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		stockSearchKey.setStockQty(0,"<=");
		try
		{
			int count = stockHandler.count(stockSearchKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (deleteCenterStock) --> Stock Delete Data Count(" + count + ")");
			if(count > 0)
			{
				stockHandler.drop(stockSearchKey);
			}
		}
		catch (NotFoundException e)
		{
		}
	}

	//#CM696019
	/**
	 * Move the result data.<BR>
	 * @param conn Connection with database
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void moveResult(Connection conn) throws ReadWriteException
	{
		//#CM696020
		// Move the result data.
		try
		{
			SystemResultHandler sysResultHandler = new SystemResultHandler(conn);
			
			//#CM696021
			// Search for data with all status per plan unique key "Sent" through the Sending Result Information and insert them into the Result.
			if( sysResultHandler.moveHostResultData() > 0 )
			{
				SystemHostSendFinder hfinder = new SystemHostSendFinder(conn);

				//#CM696022
				// Find a plan unique key subject to delete in the Sending result info.
				int count = hfinder.ReportPlanUkeySearch();
DEBUG.MSG("SCHEDULE", wProcessName + "    (moveResult) --> Result Move Data Count(" + count + ")");
				if( count > 0 )
				{
					SystemHostSendHandler shandle = new SystemHostSendHandler(conn);

					while (hfinder.isNext())
					{
						//#CM696023
						// Obtain every 100 the search results.
						String[] planUkey = hfinder.getPlanUkeyArray(100);
						if( planUkey.length > 0 )
						{
							//#CM696024
							// Delete the Sending Result Information using the obtained plan unique key.
							shandle.dropHostSend_MultiPlanUkey(planUkey);
						}
					}
				}
			}

			//#CM696025
			// Process defined for Unplanned storage/picking
			if( sysResultHandler.moveHostResultNoPlanData() > 0 )
			{
				SystemHostSendFinder sysHostSendFinder = new SystemHostSendFinder(conn);

				//#CM696026
				// Find the work No. subject to delete from the Sending result info.
				int count = sysHostSendFinder.ReportJobNoSearch();

				if( count > 0 )
				{
					SystemHostSendHandler shandle = new SystemHostSendHandler(conn);

					while (sysHostSendFinder.isNext())
					{
						//#CM696027
						// Obtain every 100 the search results.
						String[] jobNo = sysHostSendFinder.getJobNoArray(100);
						if( jobNo.length > 0 )
						{
							//#CM696028
							// Delete the Sending Result Information using the obtained Work No.
							shandle.dropHostSend_MultiJobNo(jobNo);
						}
					}
				}
			}
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

	}

	//#CM696029
	/**
	 * Delete the result data.<BR>
	 * @param conn Connection with database
	 * @param deleteDate Boundary Date/Time for Delete
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void deleteResult(Connection conn, String deleteDate) throws ReadWriteException
	{
		//#CM696030
		// Delete the Sending Result Information.
		//#CM696031
		// Delete data that passes its result retention period (days).
		HostSendHandler hostSendHandler = new HostSendHandler(conn);
		HostSendSearchKey hostSendSearckKey = new HostSendSearchKey();

		hostSendSearckKey.setWorkDate(deleteDate, "<=");
		try
		{
			int count = hostSendHandler.count(hostSendSearckKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (deleteResult) --> HostSend Delete Data Count(" + count + ")");
			if(count > 0)
			{
				hostSendHandler.drop(hostSendSearckKey);
			}
		}
		catch (NotFoundException e)
		{
		}

		//#CM696032
		// Delete the Result information.
		//#CM696033
		// Delete data that passes its result retention period (days).
		if (!StringUtil.isBlank(deleteDate))
		{
			ResultHandler rHandle = new ResultHandler(conn);
			ResultSearchKey rsKey = new ResultSearchKey();
			rsKey.setWorkDate(deleteDate, "<=");
			try
			{
				int count = rHandle.count(rsKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (deleteResult) --> Result Delete Data Count(" + count + ")");
				if(count > 0)
				{
					rHandle.drop(rsKey);
				}
			}
			catch (NotFoundException e)
			{
			}
		}
	}

	//#CM696034
	/**
	 * Delete the Result information.
	 * (Sending result info, Result info, Worker result info)
	 * @param conn Connection with database
	 * @param deleteDate Boundary Date/Time for Delete
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void deleteWokerResult(Connection conn, String deleteDate) throws ReadWriteException
	{
		//#CM696035
		// Delete the Worker result info.
		WorkerResultHandler workerResultHandler = new WorkerResultHandler(conn);
		WorkerResultSearchKey workerResultSearchKey = new WorkerResultSearchKey();
		workerResultSearchKey.setWorkDate(deleteDate, "<=");
		try
		{
			int count = workerResultHandler.count(workerResultSearchKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (deleteWokerResult) --> WokerResult Delete Data Count(" + count + ")");
			if (count > 0)
			{
				workerResultHandler.drop(workerResultSearchKey);
			}
		}
		catch (NotFoundException e)
		{
		}
	}

	//#CM696036
	/**
	 * Execute the process for deleting the next work data.<BR>
	 * @param conn Connection with database
	 * @param unWorkDelete Presence of deletion of data with Not Worked.
	 * @param workDate Work date
	 * @param deleteDate Boundary Date/Time for Delete
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void deleteNextProcessInfo(Connection conn, boolean unWorkDelete, String workDate, String deleteDate)
																							throws ReadWriteException
	{
		int count = 0;

		NextProcessInfoHandler nextProcessHandler = new NextProcessInfoHandler(conn);
		NextProcessInfoSearchKey nextProcessSearchKey = new NextProcessInfoSearchKey();

		if( unWorkDelete )
		{
			//#CM696037
			//To delete Plan data with status Not Worked:
			//#CM696038
			//	 ( Planned Shipping Date <= Work Date of WareNavi System table and Status flag <> other than Processing )
			nextProcessSearchKey.setShipPlanDate(workDate, "<=", "(", "", "and");
			nextProcessSearchKey.setStatusFlag(NextProcessInfo.STATUS_FLAG_PROCESSING, "<>", "", ")", "or");

			//#CM696039
			// Delete the next work status that passed its retention period (days).
			//#CM696040
			//	 ( Status flag = Processing and Planned Shipping Date <= Expiry date of retention )
			nextProcessSearchKey.setStatusFlag(NextProcessInfo.STATUS_FLAG_PROCESSING, "=", "(", "", "and");
			nextProcessSearchKey.setShipPlanDate(deleteDate, "<=", "", ")", "or");
		}
		else
		{
			//#CM696041
			//To hold over the data with status "Not Worked":
			//#CM696042
			//	 ( Planned Shipping Date <= Work Date of WareNavi System table and Status flag = Completed )
			nextProcessSearchKey.setStatusFlag(NextProcessInfo.STATUS_FLAG_PROCESSING_FINISH, "=", "(", "", "and");
			nextProcessSearchKey.setShipPlanDate(workDate, "<=", "", ")", "or");

			//#CM696043
			// Delete the next work status that passed its retention period (days).
			//#CM696044
			//	 ( Status flag = Not Processed or Processing, and Planned Shipping Date <= Expiry date of retention )
			nextProcessSearchKey.setStatusFlag(NextProcessInfo.STATUS_FLAG_UNPROCESSING, "=", "((", "", "or");
			nextProcessSearchKey.setStatusFlag(NextProcessInfo.STATUS_FLAG_PROCESSING, "=", "", ")", "and");
			nextProcessSearchKey.setShipPlanDate(deleteDate, "<=", "", ")", "or");
		}

		try
		{
			count = nextProcessHandler.count(nextProcessSearchKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (NextProcessInfo Delete) --> NextProcessInfo 1 Delete Data Count(" + count + ")");
			if (count > 0)
			{
				//#CM696045
				// Delete the next work status.
				nextProcessHandler.drop(nextProcessSearchKey);
			}
		}
		catch (NotFoundException e)
		{
		}

		//#CM696046
		// Delete the next work status that with no connection.
		//#CM696047
		// ( Planned Shipping Date <= Work Date of WareNavi System table and Status flag = Not Processed )
		nextProcessSearchKey.KeyClear();
		nextProcessSearchKey.setShipPlanDate(workDate, "<=", "(", "", "and");
		nextProcessSearchKey.setStatusFlag(NextProcessInfo.STATUS_FLAG_UNPROCESSING, "=", "", ")", "and");

		String strNull = null;
		nextProcessSearchKey.setPlanUkey(strNull, "=", "((", "", "or");
		nextProcessSearchKey.setPlanUkey(NextProcessInfo.PLAN_UKEY_DUMMY, "=", "", ")", "and");

		nextProcessSearchKey.setInstockPlanUkey(strNull, "=", "(", "", "or");
		nextProcessSearchKey.setInstockPlanUkey(NextProcessInfo.PLAN_UKEY_DUMMY, "=", "", ")", "and");

		nextProcessSearchKey.setStoragePlanUkey(strNull, "=", "(", "", "or");
		nextProcessSearchKey.setStoragePlanUkey(NextProcessInfo.PLAN_UKEY_DUMMY, "=", "", ")", "and");

		nextProcessSearchKey.setRetrievalPlanUkey(strNull, "=", "(", "", "or");
		nextProcessSearchKey.setRetrievalPlanUkey(NextProcessInfo.PLAN_UKEY_DUMMY, "=", "", ")", "and");

		nextProcessSearchKey.setSortingPlanUkey(strNull, "=", "(", "", "or");
		nextProcessSearchKey.setSortingPlanUkey(NextProcessInfo.PLAN_UKEY_DUMMY, "=", "", ")", "and");

		nextProcessSearchKey.setShippingPlanUkey(strNull, "=", "(", "", "or");
		nextProcessSearchKey.setShippingPlanUkey(NextProcessInfo.PLAN_UKEY_DUMMY, "=", "", "))", "and");

		try
		{
			count = nextProcessHandler.count(nextProcessSearchKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (NextProcessInfo Delete) --> NextProcessInfo 2 Delete Data Count(" + count + ")");
			if (count > 0)
			{
				//#CM696048
				// Delete the next work status that with no connection.
				nextProcessHandler.drop(nextProcessSearchKey);
			}
		}
		catch (NotFoundException e)
		{
		}

		//#CM696049
		// Delete the next work status with no connection, using the cross TC.
		//#CM696050
		// ( Next Work division = Sorting and Status flag = Not Processed and TC/DC Division = Cross Docking and 
		//#CM696051
		//   Planned Shipping Date <= Work Date of warenavi_system table)
		nextProcessSearchKey.KeyClear();
		nextProcessSearchKey.setWorkKind(NextProcessInfo.JOB_TYPE_SORTING);
		nextProcessSearchKey.setStatusFlag(NextProcessInfo.STATUS_FLAG_UNSTART);
		nextProcessSearchKey.setTcdcFlag(NextProcessInfo.TCDC_FLAG_CROSSTC);
		nextProcessSearchKey.setShipPlanDate(workDate, "<=");

		try
		{
			NextProcessInfo[] info = (NextProcessInfo[]) nextProcessHandler.find(nextProcessSearchKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (NextProcessInfo Delete) --> NextProcessInfo 3 Check Data Count(" + info.length + ")");
			if( info.length > 0 )
			{
				//#CM696052
				// Search for the sorting plan info using Plan unique key of the obtained next Work Status.
				SortingPlanHandler sorthandle = new SortingPlanHandler(conn);
				SortingPlanSearchKey sortKey = new SortingPlanSearchKey();

				for( int i = 0; i < info.length; i++ )
				{
					sortKey.KeyClear();
					sortKey.setSortingPlanUkey(info[i].getPlanUkey());
					count = sorthandle.count(sortKey);
					if( count <= 0 )
					{
						//#CM696053
						// If Plan unique key data of the next work status does not exist in the sorting plan info,
						//#CM696054
						// delete the next work status
						nextProcessSearchKey.KeyClear();
						nextProcessSearchKey.setNextProcUkey(info[i].getNextProcUkey());
						nextProcessHandler.drop(nextProcessSearchKey);
					}
				}
			}
		}
		catch (NotFoundException e)
		{
		}
	}

	//#CM696055
	/**
	 * Delete the Message log that passed its retention period (days).
	 * @throws Exception Report all exceptions.
	 */
	protected void deleteMessageLog() throws Exception
	{
		//#CM696056
		// Path of a log file
		String logFilePath = WmsParam.LOGS_PATH + WmsParam.MESSAGELOG_FILE;

		//#CM696057
		// Message log work file name
		String messageLogWork = WmsParam.LOGS_PATH + "message_work.txt";

		//#CM696058
		// Format type of log date.
		String dateFormat = "yyyyMMdd";

		//#CM696059
		// Encode type of log
		String logEncode = "UTF-8";

		//#CM696060
		// Obtain the deleted date (Obtain the log backup retention period (days) from WMSParam),
		String deleteDate = getKeepDateString(Integer.parseInt(WmsParam.WMS_LOGFILE_KEEP_DAYS));

		//#CM696061
		// Variable for storing Message files.
		BufferedReader reader = null;
		BufferedWriter writer = null;

		//#CM696062
		// Variable for maintaining the date of file record
		String recordDate = "";

		//#CM696063
		// Date Check flag
		boolean dateCheck = false;

		//#CM696064
		// Generate a message log file object.
		File messageLog = new File(logFilePath);
		//#CM696065
		// Generate a work file object.
		File workFile = new File(messageLogWork);
		
		try
		{
			//#CM696066
			// Process for copying a file
			//#CM696067
			// Open the Message log file.
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(messageLog), logEncode));
	
			//#CM696068
			// Open the Work file.
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(workFile, false), logEncode));
	
			//#CM696069
			// Read a file every line and process it.
			while(reader.ready())
			{
				//#CM696070
				// Read the data in one line.
				String str = reader.readLine();
	
				//#CM696071
				// If no check has been placed in the date fields, obtain
				if (str != null && dateCheck == false)
				{
					//#CM696072
					// the year written in the file,
					String recordYear = str.substring(0,4);
					//#CM696073
					// the month written in the file,
					String recordMonth = str.substring(5,7);
					//#CM696074
					// the day written in the file,
					String recordDay = str.substring(8,10);
	
					//#CM696075
					// and the date written in the file.
					StringBuffer recordDateBuffer = new StringBuffer();
					recordDateBuffer.append(recordYear);
					recordDateBuffer.append(recordMonth);
					recordDateBuffer.append(recordDay);
	
					recordDate = recordDateBuffer.toString();
	
					//#CM696076
					// Compare the date written in the file and the date of deleting the relevant data.
					if(new SimpleDateFormat(dateFormat).parse(deleteDate).compareTo(new SimpleDateFormat(dateFormat).parse(recordDate)) < 0)
					{
						//#CM696077
						// If the date written in the file is later than the date of deleting the relevant data,
						//#CM696078
						// write the data tentatively in the work file.
						dateCheck = true;
					}
				}
	
				//#CM696079
				// If the date check is passed:
				if(str != null && dateCheck)
				{
					//#CM696080
					// Suffix a line feed code to the string to be written.
					str = str + "\n";
	
					//#CM696081
					// Write it to a tentative file.
					writer.write(str);
	
					//#CM696082
					// Clear the written stream.
					writer.flush();
				}
			}
			//#CM696083
			// Close the file.
			reader.close();
			writer.close();
	
			//#CM696084
			// Delete the original file.
			messageLog.delete();
	
			//#CM696085
			// Change the work file name to the log file name.
			workFile.renameTo(messageLog);
		}
		catch(FileNotFoundException e)
		{
			wMessage = "6003009" + wDelim + "message.log";
			return;	
		}
	}
	//#CM696086
	//	Private methods -----------------------------------------------
}
//#CM696087
//end of class
