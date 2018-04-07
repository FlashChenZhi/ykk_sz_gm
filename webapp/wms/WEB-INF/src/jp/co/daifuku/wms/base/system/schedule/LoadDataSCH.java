//#CM698416
//$Id: LoadDataSCH.java,v 1.3 2006/11/21 04:22:38 suresh Exp $

//#CM698417
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkerHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerSearchKey;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.Worker;

//#CM698418
/**
 * Designer : T.Yamashita <BR>
 * Maker : T.Yamashita   <BR>
 *
 * Allow the <CODE>LoadDataSCH</CODE> class to execute the process for loading data.<BR>
 * To execute the process for actual loading, however, use the implemented class by using an common interface for each package.<BR>
 * <BR>
 * Make each instance of class for loading dynamically.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for generating an instance.(<CODE>makeExternalDataLoaderInstance(Connection conn, String classname)</CODE>method)<BR>
 * <BR>
 * Generate an instance for the class that implements the <code>ExternalDataLoader</code> interface using the designated class name.<BR>
 *  <BR>
 * <BR>
 * 2.Process for loading data(<CODE>startSCH(Connection conn, Parameter[] startParams)</CODE>method)<BR>
 * <BR>
 * .Invoke the method for "1: Generate Instance" in this method. To execute the process for actual loading, use the implemented class by using an common interface for each package.<BR>
 * <BR>
 * <BR>
 * Allow the eWareNavi-Basic to invoke the list of the following classes for loading data.<BR>
 * <DIR>
 * -  Process for loading Receiving Plan (InStockDataLoader)<BR>
 * -  Process for loading Storage Plan (StorageDataLoader)<BR>
 * -  Process for loading Picking Plan (RetrievalDataLoader)<BR>
 * -  Process for loading Sorting Plan (SortingDataLoader)<BR>
 * -  Process for loading Shipping Plan (ShippingDataLoader)<BR>
 * </DIR>
 * <BR>
 * 3.Initial Display Process(<CODE>initFind(Connection conn, Parameter searchParam)</CODE> method)<BR>
 * <BR>
 * Execute the process for initial display of buttons in the Load External Data screen.<BR>
 * Referring to the System definition table(WARENAVI_SYSTEM), set true as a value only for a checkbox of the Plan data with package flag "1: Available", and return it.<BR>
 * <BR>
 * <BR>
 * 4.Input check process(<CODE>check(Connection conn, Parameter checkParam)</CODE> method)<BR>
 * <BR>
 * Check the Worker Code, password, and privilege.<BR>
 * 2.Invoke this from the process for loading data.<BR>
 * <BR>
 * Other methods are not available in the eWareNavi-Basic.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/02</TD><TD>T.Yamashita</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:22:38 $
 * @author  $Author: suresh $
 */
public class LoadDataSCH extends AbstractSystemSCH
{

	//#CM698419
	// Class fields --------------------------------------------------
	//#CM698420
	/**
	 * Load Data class (Receiving Inspection)<BR>
	 */
	public static final String INSTOCK_LOAD_CLASS =
		"jp.co.daifuku.wms.instockreceive.schedule.InstockDataLoader";

	//#CM698421
	/**
	 * Load Data class (Storage)<BR>
	 */
	public static final String STORAGE_LOAD_CLASS =
		"jp.co.daifuku.wms.storage.schedule.StorageDataLoader";

	//#CM698422
	/**
	 * Load Data class (Picking)<BR>
	 */
	public static final String RETRIEVAL_LOAD_CLASS =
		"jp.co.daifuku.wms.retrieval.schedule.RetrievalDataLoader";

	//#CM698423
	/**
	 * Load Data class (Sorting)<BR>
	 */
	public static final String SORTING_LOAD_CLASS =
		"jp.co.daifuku.wms.sorting.schedule.SortingDataLoader";

	//#CM698424
	/**
	 * Load Data class (Shipping Inspection)<BR>
	 */
	public static final String SHIPPING_LOAD_CLASS =
		"jp.co.daifuku.wms.shipping.schedule.ShippingDataLoader";

	//#CM698425
	// Class variables -----------------------------------------------

	//#CM698426
	/**
	 * Class Name (Load Data)
	 */
	private final String wProcessName = "LoadDataSCH";

	//#CM698427
	/**
	 * Instance for loading data maintained by this LoadDataSCH instance.<BR>
	 */
	protected ExternalDataLoader wExternalDataLoader = null;

	//#CM698428
	/**
	 * Delimiter
	 * A separator between parameters of MessageDef Message when exception occurs.<BR>
	 * 	Example String msginfo = "9000000" + wDelimta + "Palette" + wDelim + "Stock";
	 */
	public static String wDelimta = MessageResource.DELIM;

	//#CM698429
	// Class method --------------------------------------------------
	//#CM698430
	/**
	 * Generate an instance for the class that implements the <code>ExternalDataLoader</code> interface using the designated class name.
	 * 
	 * @param classname A class that implements the <code>Scheduler</code> interface to make an instance.
	 * @return A class that implements the <code>ScheduleChecker</code> interface.
	 * @throws InvalidDefineException Announce when generating any instance other than <code>Scheduler</code>.
	 * @throws InvalidDefineException Announce when failed to generate instance.
	 */
	protected static ExternalDataLoader makeExternalDataLoaderInstance(String className)
		throws InvalidDefineException
	{
		Object tgt = null;

		try
		{
			//#CM698431
			// load class
			Class loadClass = Class.forName(className);

			tgt = loadClass.newInstance();
			if ((tgt instanceof ExternalDataLoader) == false)
			{
				//#CM698432
				// Set a message: "Object other than {0} was returned".
				RmiMsgLogClient.write("6006008" + wDelimta + "ExternalDataLoader", "LoadDataSCH");
				throw (new InvalidDefineException());
			}
		}
		catch (Exception e)
		{
			//#CM698433
			//  Failed to generate instance. ClassName={0} Exception: {1}
			Object[] msg = new Object[1];
			msg[0] = className;
			RmiMsgLogClient.write(new TraceHandler(6006003, e), "LoadDataSCH", msg);
			throw (new InvalidDefineException());
		}
		return (ExternalDataLoader) tgt;
	}

	//#CM698434
	// Constructors --------------------------------------------------
	//#CM698435
	/**
	 * Constructor
	 */
	public LoadDataSCH()
	{
	}

	//#CM698436
	// Public methods ------------------------------------------------
	//#CM698437
	/**
	 * This method obtains the data required for initial display.<BR>
	 * Search conditions passes a class that inherits the<CODE>Parameter</CODE> class.<BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Database connection object
	 * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions.
	 * @return A class that implements the <CODE>Parameter</CODE> interface that contains the search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		//#CM698438
		// Translate the type of searchParam.
		SystemParameter param = (SystemParameter) searchParam;

		//#CM698439
		// Planned Work Date
		param.setPlanDate(getWorkDate(conn));
		//#CM698440
		// Receiving Plan checkbox
		param.setSelectLoadInstockData(isInstockPack(conn));
		//#CM698441
		// Storage Plan checkbox
		param.setSelectLoadStorageData(isStoragePack(conn));
		//#CM698442
		// Picking Plan checkbox
		param.setSelectLoadRetrievalData(isRetrievalPack(conn));
		//#CM698443
		// Sorting Plan checkbox
		param.setSelectLoadSortingData(isSortingPack(conn));
		//#CM698444
		// Shipping Plan checkbox
		param.setSelectLoadShippingData(isShippingPack(conn));
		//#CM698445
		// Checkbox for Automated decision-making of Storage Location
		param.setSelectLoadIdmAuto(isIdmPack(conn));

		return param;
	}

	//#CM698446
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
	public boolean check(Connection conn, Parameter checkParam)
		throws ReadWriteException, ScheduleException
	{
		//#CM698447
		// Translate the data type of checkParam.
		SystemParameter param = (SystemParameter) checkParam;

		//#CM698448
		// Check the Worker Code, password, and Access Privileges.
		if (!checkWorker(conn, param, true))
		{
			return false;
		}

		//#CM698449
		// Check for loading the Plan data in progress.
		if (isLoadingData(conn))
		{
			return false;
		}

		return true;
	}

	//#CM698450
	/**
	 * Start the schedule. According to the contents set in the parameter array designated in the <CODE>startParams</CODE>,<BR>
	 * execute the process for the schedule. Implement a scheduling process depending on the class implementing this interface.<BR>
	 * Return true when the schedule process completed successfully.<BR>
	 * Return false if failed to schedule due to condition error or other causes.<BR>
	 * In this case, use the <CODE>getMessage()</CODE> method to obtain the contents.
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param conn Database connection object
	 * @param startParams Database connection object
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		boolean resultFlag = false;

		//#CM698451
		// This flag determines whether "Processing Load" flag is updated in its own class.
		boolean updateLoadDataFlag = false;

		try
		{
			//#CM698452
			// Translate the type of startParams.
			SystemParameter[] param = (SystemParameter[]) startParams;

			//#CM698453
			// execute the check process.
			if (!check(conn, param[0]))
			{
				return false;
			}

			//#CM698454
			// Change the Load Data flag of the WareNavi System table to 1 before executing the process for loading.
			if (!updateLoadDataFlag(conn, true))
			{
				return false;
			}
			doCommit(conn, wProcessName);

			updateLoadDataFlag = true;

			//#CM698455
			// Check the daily update in process.
			if (isDailyUpdate(conn))
			{
				return false;
			}

			//#CM698456
			// Check whether a checkbox is ticked off or not.
			if (!param[0].getSelectLoadInstockData()
				&& !param[0].getSelectLoadStorageData()
				&& !param[0].getSelectLoadRetrievalData()
				&& !param[0].getSelectLoadSortingData()
				&& !param[0].getSelectLoadShippingData())
			{
				//#CM698457
				// 6023028=Please select the target data.
				wMessage = "6023028";
				return false;
			}

			//#CM698458
			// Obtain the worker name.
			WorkerHandler workerHandler = new WorkerHandler(conn);
			WorkerSearchKey searchKey = new WorkerSearchKey();
			searchKey.setWorkerCode(param[0].getWorkerCode());
			//#CM698459
			// Check whether Worker Code is valid or not.
			searchKey.setDeleteFlag(Worker.DELETE_FLAG_OPERATION);
			Worker[] worker = (Worker[]) workerHandler.find(searchKey);
			if (worker != null && worker.length > 0)
			{
				param[0].setWorkerName(worker[0].getName());
			}

			//#CM698460
			// Generate a Load Data class.
			boolean skip_flag = false;
			boolean overwrite_flag = false;
			boolean regist_flag = false;

			//#CM698461
			// List that stores paths for classes.
			Vector classPathList = new Vector();

			//#CM698462
			// Receiving Plan
			if (isInstockPack(conn) && param[0].getSelectLoadInstockData())
			{
				classPathList.add(INSTOCK_LOAD_CLASS);
			}
			//#CM698463
			// Storage Plan
			if (isStoragePack(conn) && param[0].getSelectLoadStorageData())
			{
				classPathList.add(STORAGE_LOAD_CLASS);
			}
			//#CM698464
			// Picking Plan
			if (isRetrievalPack(conn) && param[0].getSelectLoadRetrievalData())
			{
				classPathList.add(RETRIEVAL_LOAD_CLASS);
			}
			//#CM698465
			// Sorting Plan
			if (isSortingPack(conn) && param[0].getSelectLoadSortingData())
			{
				classPathList.add(SORTING_LOAD_CLASS);
			}
			//#CM698466
			// Shipping Plan
			if (isShippingPack(conn) && param[0].getSelectLoadShippingData())
			{
				classPathList.add(SHIPPING_LOAD_CLASS);
			}

			//#CM698467
			// Copy the contents to be stored.
			String[] path = new String[classPathList.size()];
			classPathList.copyInto(path);

			WareNaviSystem wnsys = new WareNaviSystem();
			wnsys = WareNaviSystemPackageCheck(conn);

			//#CM698468
			// Start the process for loading data according to the path which stores it.
			for (int i = 0; i < path.length; i++)
			{
				wExternalDataLoader = LoadDataSCH.makeExternalDataLoaderInstance(path[i]);
				//#CM698469
				// Execute the process for loading.
				if (wExternalDataLoader.load(conn, wnsys, param[0]))
				{
					wMessage = wExternalDataLoader.getMessage();
					if (wExternalDataLoader.isSkipFlag())
						skip_flag = true;
					if (wExternalDataLoader.isOverWriteFlag())
						overwrite_flag = true;
					if (wExternalDataLoader.isRegistFlag())
						regist_flag = true;
					resultFlag = true;
				}
				else
				{
					wMessage = wExternalDataLoader.getMessage();
					resultFlag = false;
					break;
				}
			}

			//#CM698470
			// Set the message if all the data are loaded normally.
			if (resultFlag)
			{
				if (regist_flag == true)
				{
					if (skip_flag && overwrite_flag)
						wMessage = "6021007"; // Data was skipped and loaded. (duplicate of data was found.)
					else if (overwrite_flag)
						wMessage = "6021006"; // Data was loaded successfully. (duplicate of data was found.)
					else if (skip_flag)
						wMessage = "6021004"; // Data was skipped and the loading completed.
					else
						wMessage = "6001008"; // Loading of data completed successfully.
				}
				else
				{
					if (skip_flag)
						wMessage = "6021008"; // Data was skipped and no target data was found.
					else
						wMessage = "6003018"; // No target data was found.
				}
			}
		}
		catch (ReadWriteException e)
		{
			doRollBack(conn, wProcessName);
			throw new ReadWriteException(e.getMessage());
		}
		catch (Exception e)
		{
			doRollBack(conn, wProcessName);
			throw new ScheduleException(e.getMessage());
		}
		finally
		{
			//#CM698471
			// Failing to add rolls back the transaction.
			if (!resultFlag)
			{
				doRollBack(conn, wProcessName);
			}

			//#CM698472
			// If "Processing Load" flag was updated in its own class,
			//#CM698473
			// change the "Processing Load" flag to "0: Stopping".
			if (updateLoadDataFlag)
			{
				updateLoadDataFlag(conn, false);
				doCommit(conn, wProcessName);
			}
		}
		return resultFlag;
	}

	//#CM698474
	// Package methods -----------------------------------------------

	//#CM698475
	// Protected methods ---------------------------------------------

	//#CM698476
	// Private methods -----------------------------------------------

	//#CM698477
	/**
	 * Referring to the System definition table(WARENAVI_SYSTEM), obtain the <CODE>WareNaviSystem<CODE> object package.<BR>
	 * @param conn Database connection object
	 * @return WareNaviSystem object
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	protected WareNaviSystem WareNaviSystemPackageCheck(Connection conn) throws ReadWriteException,ScheduleException
	{

		WareNaviSystem WareNaviSystem = new WareNaviSystem();

		WareNaviSystemSearchKey skey = new WareNaviSystemSearchKey();
		WareNaviSystemHandler wnhdl = new WareNaviSystemHandler(conn);

		try
		{
			WareNaviSystem = (WareNaviSystem) wnhdl.findPrimary(skey);
			if (WareNaviSystem == null)
			{
				//#CM698478
				//6006039=Failed to search for {0}.
				RmiMsgLogClient.write(6006039, LogMessage.F_ERROR, "WareNaviSystemHandler", null);
				throw (new ReadWriteException());
			}
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		return WareNaviSystem;
	}

}
//#CM698479
//end of class
