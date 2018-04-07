//#CM698807
//$Id: ReportDataPlanSCH.java,v 1.4 2006/11/21 04:39:26 suresh Exp $

//#CM698808
/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;

//#CM698809
/**
 * Designer : T.Nakai <BR>
 * Maker : T.Nakai   <BR>
 *
 * Allow the <CODE>ReportDataPlanSCH</CODE> class to execute the process for reporting data.<BR>
 * To execute the process for actual reporting, however, use the implemented class by using an common interface for each package.<BR>
 * <BR>
 * Make the instance of the class for reporting dynamically.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for generating an instance.(<CODE>makeExternalReportDataCreatorInstance(Connection conn, String classname)</CODE>method)<BR>
 * <BR>
 * Generate an instance for the class that implements the <code>ExternalReportDataCreator</code> interface using the designated class name.<BR>
 *  <BR>
 * <BR>
 * 2.Process for reporting data(<CODE>startSCH(Connection conn, Parameter startParam)</CODE>method)<BR>
 * <BR>
 * .Invoke the method for "1: Generate Instance" in this method. To execute the process for actual reporting, use the implemented class by using an common interface for each package.<BR>
 * <BR>
 * <BR>
 * Allow the eWareNavi-Basic to invoke the list of the following classes for reporting data.<BR>
 * <DIR>
 * -  Process for reporting Receiving result (InStockReportDataCreator)<BR>
 * -  Execute the process for reporting Storage result (StorageReportDataCreator)<BR>
 * -  Process for reporting Picking result (RetrievalReportDataCreator)<BR>
 * -  Process for reporting Sorting result (SortingReportDataCreator)<BR>
 * -  Process for reporting Shipping result (ShippingReportDataCreator)<BR>
 * </DIR>
 * <BR>
 * 3.Initial Display Process(<CODE>initFind(Connection conn, Parameter startParam)</CODE>method)<BR>
 * <BR>
 * Execute the process for initial display of buttons in the Report Data screen.<BR>
 * Referring to System definition table(WARENAVI_SYSTEM), set true as a value only for a checkbox of the Report data with package flag "1: Available", and return it.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/18</TD><TD>T.Nakai</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2006/11/21 04:39:26 $
 * @author  $Author: suresh $
 */
public class ReportDataPlanSCH extends AbstractSystemSCH
{

	//#CM698810
	// Class fields --------------------------------------------------
	//#CM698811
	/**
	 * Report Data class (Receiving Inspection)<BR>
	 */
	protected static final String INSTOCK_REPORT_CLASS =
		"jp.co.daifuku.wms.instockreceive.schedule.InstockReportDataCreator";

	//#CM698812
	/**
	 * Report Data class (Storage)<BR>
	 */
	protected static final String STORAGE_REPORT_CLASS =
		"jp.co.daifuku.wms.storage.schedule.StorageReportDataCreator";

	//#CM698813
	/**
	 * Report Data class (Picking)<BR>
	 */
	protected static final String RETRIEVAL_REPORT_CLASS =
		"jp.co.daifuku.wms.retrieval.schedule.RetrievalReportDataCreator";

	//#CM698814
	/**
	 * Report Data class (Sorting)<BR>
	 */
	protected static final String SORTING_REPORT_CLASS =
		"jp.co.daifuku.wms.sorting.schedule.SortingReportDataCreator";

	//#CM698815
	/**
	 * Report Data class (Shipping Inspection)<BR>
	 */
	protected static final String SHIPPING_REPORT_CLASS =
		"jp.co.daifuku.wms.shipping.schedule.ShippingReportDataCreator";
	
	//#CM698816
	/**
	 * Receiving message storage key<BR>
	 */
	protected static final String INSTOCK = "INSTOCK";

	//#CM698817
	/**
	 * Storage message storage key<BR>
	 */
	protected static final String STORAGE = "STORAGE";

	//#CM698818
	/**
	 * Picking message storage key<BR>
	 */
	protected static final String RETRIEVAL = "RETRIEVAL";

	//#CM698819
	/**
	 * Sorting message storage key<BR>
	 */
	protected static final String SORTING = "SORTING";

	//#CM698820
	/**
	 * Shipping message storage key<BR>
	 */
	protected static final String SHIPPING = "SHIPPING";

	//#CM698821
	// Class variables -----------------------------------------------

	//#CM698822
	/**
	 * Class Name (Report Data)
	 */
	private final String wProcessName = "ReportDataPlanSCH";

	//#CM698823
	/**
	 * Instance for reporting data maintained by this ReportDataPlanSCH instance.<BR>
	 */
	protected ExternalReportDataCreator wExternalReportDataCreator = null;

	//#CM698824
	/**
	 * Delimiter
	 * A separator between parameters of MessageDef Message when exception occurs.<BR>
	 * 	Example String msginfo = "9000000" + wDelimta + "Palette" + wDelim + "Stock";
	 */
	public static String wDelimta = MessageResource.DELIM;
	
	//#CM698825
	/**
	 * Area to maintain the message<BR>
	 * Use this to maintain the contents when condition error etc occurs by invoking a method.
	 */
	protected String[] wMessageList;

	//#CM698826
	// Class method --------------------------------------------------
	//#CM698827
	/**
	 * Generate an instance for the class that implements the <code>ExternalReportDataCreator</code> interface using the designated class name.
	 * 
	 * @param classname A class that implements the <code>Scheduler</code> interface to make an instance.
	 * @return A class that implements the <code>ScheduleChecker</code> interface.
	 * @throws InvalidDefineException Announce when failed to generate instance.
	 */
	protected static ExternalReportDataCreator makeExternalReportDataCreatorInstance(String className)
		throws InvalidDefineException
	{
		Object tgt = null;

		try
		{
			//#CM698828
			// load class
			Class loadClass = Class.forName(className);

			tgt = loadClass.newInstance();
			if ((tgt instanceof ExternalReportDataCreator) == false)
			{
				//#CM698829
				// Set a message: "Object other than {0} was returned".
				RmiMsgLogClient.write(
					"6006008" + wDelimta + "ExternalReportDataCreator",
					"ReportDataPlanSCH");
				throw (new InvalidDefineException());
			}
		}
		catch (Exception e)
		{
			//#CM698830
			//Output the error log.
			e.printStackTrace();
			//#CM698831
			//  Failed to generate instance. ClassName={0} Exception: {1}
			RmiMsgLogClient.write("6006003" + wDelimta + className, "ReportDataPlanSCH");
			throw (new InvalidDefineException("6006003" + wDelimta + className));
		}
		return (ExternalReportDataCreator) tgt;
	}

	//#CM698832
	// Constructors --------------------------------------------------
	//#CM698833
	/**
	 * Constructor
	 */
	public ReportDataPlanSCH()
	{
	}

	//#CM698834
	// Public methods ------------------------------------------------
	//#CM698835
	/**
	 * This method obtains the data required for initial display.<BR>
	 * Set the work date and the status of each package introduced or not, for the parameter, and return them.<BR>
	 * 
	 * @param conn        Database connection object
	 * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions.
	 * @return A class that implements the <CODE>Parameter</CODE> interface that contains the search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		//#CM698836
		// Translate the type of startParams.
		SystemParameter param = (SystemParameter) searchParam;

		//#CM698837
		// Work date
		param.setPlanDate(getWorkDate(conn));
		//#CM698838
		// Receiving Result checkbox
		param.setSelectReportInstockData(isInstockPack(conn));
		//#CM698839
		// Storage Result checkbox
		param.setSelectReportStorageData(isStoragePack(conn));
		//#CM698840
		// Picking Result checkbox
		param.setSelectReportRetrievalData(isRetrievalPack(conn));
		//#CM698841
		// Sorting Result checkbox
		param.setSelectReportSortingData(isSortingPack(conn));
		//#CM698842
		// Shipping Result checkbox
		param.setSelectReportShippingData(isShippingPack(conn));

		return param;
	}

	//#CM698843
	/**
	 * Check that the contents of Worker Code and password are correct.<BR>
	 * Check for generating in progress for reporting data.<BR>
	 * Return true if the content is proper, or return false if improper.<BR>
	 * 
	 * @param conn       Database connection object
	 * @param checkParam This parameter class includes contents to be checked for input.
	 * @return Return true if the content is proper, or return false if improper.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean check(Connection conn, Parameter checkParam)
		throws ReadWriteException, ScheduleException
	{
		//#CM698844
		// Translate the data type of checkParam.
		SystemParameter param = (SystemParameter) checkParam;

		//#CM698845
		// Check the Worker Code, password, and Access Privileges.
		if (!checkWorker(conn, param, true))
		{
			return false;
		}

		//#CM698846
		// Check for generating in progress for reporting data.
		if (isReportData(conn))
		{
			return false;
		}

		return true;
	}

	//#CM698847
	/**
	 * Start the schedule.Execute the process for reporting Check for input and execute the process for reporting the data.
	 * 
	 * @param conn        Database connection object
	 * @param startParams A parameter class that contains the contents entered via screen.
	 * @return Return true if a process for reporting data completed normally. Return false if failed.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		// This flag checks whether to have done the report processing.
		boolean resultFlag = false;
		// This flag determines whether "Processing Report" flag is updated in its own class.
		boolean updateReportDataFlag = false;

		try
		{
			SystemParameter[] param = (SystemParameter[]) startParams;

			//#CM698848
			// execute the check process.			
			if (!check(conn, param[0]))
			{
				return false;
			}
			// 
			if (!changeReportDataFlag(conn, true))
			{
				return false;
			}
			doCommit(conn, wProcessName);
			updateReportDataFlag = true;

			//#CM698849
			// Check the Daily update process.
			if (isDailyUpdate(conn))
			{
				return false;
			}

			//#CM698850
			// Check whether a checkbox is ticked off or not.
			if (!param[0].getSelectReportInstockData()
				&& !param[0].getSelectReportStorageData()
				&& !param[0].getSelectReportRetrievalData()
				&& !param[0].getSelectReportSortingData()
				&& !param[0].getSelectReportShippingData())
			{
				//#CM698851
				// 6023028=Please select the target data. 
				wMessage = "6023028";
				return false;
			}

			//#CM698852
			// Check for presence of any processing data and execute the process for storing the path of the class for reporting data.
			//#CM698853
			// List that stores paths for classes.
			Vector classPathList = new Vector();
			//#CM698854
			// List for storing messages.
			Vector messageList = new Vector();
			//#CM698855
			// Area that stores keys for obtaining messages
			Vector key = new Vector();

			//#CM698856
			// Receiving Plan
			if (isInstockPack(conn) && param[0].getSelectReportInstockData())
			{
				//#CM698857
				// Check for presence of data Processing.
				if (InstockWorkingCheck(conn))
				{
					//#CM698858
					// 6023494=Process stopped. Please confirm the message.
				    wMessage = "6023494";
					//#CM698859
					// 6007040=Working data exists. Unable to process.
					messageList.add(MessageResource.getMessage("6007040"));
				}
				else
				{
					messageList.add(" ");
				}
				//#CM698860
				// Store a Report Data path.
				classPathList.add(INSTOCK_REPORT_CLASS);
				//#CM698861
				// Store the keys for obtaining messages.
				key.add(INSTOCK);
			}
			else
			{
				messageList.add(" ");
			}
			//#CM698862
			// Storage Plan
			if (isStoragePack(conn) && param[0].getSelectReportStorageData())
			{
				//#CM698863
				// Check for presence of data Processing.
				if (StorageWorkingCheck(conn))
				{
					//#CM698864
					// 6023494=Process stopped. Please confirm the message.
					wMessage = "6023494";
					//#CM698865
					// 6007040=Working data exists. Unable to process.
					messageList.add(MessageResource.getMessage("6007040"));
				}
				else
				{
					messageList.add(" ");
				}
				//#CM698866
				// Store a Report Data path.
				classPathList.add(STORAGE_REPORT_CLASS);
				//#CM698867
				// Store the keys for obtaining messages.
				key.add(STORAGE);
			}
			else
			{
				messageList.add(" ");
			}
			//#CM698868
			// Picking Plan
			if (isRetrievalPack(conn) && param[0].getSelectReportRetrievalData())
			{
				//#CM698869
				// Check for presence of data Processing.
				if (RetrievalWorkingCheck(conn))
				{
				    //#CM698870
				    // 6023494=Process stopped. Please confirm the message.
					wMessage = "6023494";
					//#CM698871
					// 6007040=Working data exists. Unable to process.
					messageList.add(MessageResource.getMessage("6007040"));
				}
				else
				{
					messageList.add(" ");
				}
				//#CM698872
				// Store a Report Data path.
				classPathList.add(RETRIEVAL_REPORT_CLASS);
				//#CM698873
				// Store the keys for obtaining messages.
				key.add(RETRIEVAL);
			}
			else
			{
				messageList.add(" ");
			}
			//#CM698874
			// Sorting Plan
			if (isSortingPack(conn) && param[0].getSelectReportSortingData())
			{
				//#CM698875
				// Check for presence of data Processing.
				if (SortingWorkingCheck(conn))
				{
				    //#CM698876
				    // 6023494=Process stopped. Please confirm the message.
					wMessage = "6023494";
					//#CM698877
					// 6007040=Working data exists. Unable to process.
					messageList.add(MessageResource.getMessage("6007040"));
				}
				else
				{
					messageList.add(" ");
				}
				//#CM698878
				// Store a Report Data path.
				classPathList.add(SORTING_REPORT_CLASS);
				//#CM698879
				// Store the keys for obtaining messages.
				key.add(SORTING);
			}
			else
			{
				messageList.add(" ");
			}
			//#CM698880
			// Shipping Plan
			if (isShippingPack(conn) && param[0].getSelectReportShippingData())
			{
				//#CM698881
				// Check for presence of data Processing.
				if (ShippingWorkingCheck(conn))
				{
				    //#CM698882
				    // 6023494=Process stopped. Please confirm the message.
					wMessage = "6023494";
					//#CM698883
					// 6007040=Working data exists. Unable to process.
					messageList.add(MessageResource.getMessage("6007040"));
				}
				else
				{
					messageList.add(" ");
				}
				//#CM698884
				// Store a Report Data path.
				classPathList.add(SHIPPING_REPORT_CLASS);
				//#CM698885
				// Store the keys for obtaining messages.
				key.add(SHIPPING);
			}
			else
			{
				messageList.add(" ");
			}

			//#CM698886
			// Return data with status "Processing" if any found.
			if (!StringUtil.isBlank(wMessage))
			{
				//#CM698887
				// Maintain the message.
				wMessageList = new String[messageList.size()];
				messageList.copyInto(wMessageList);
				return false;
			}

			//#CM698888
			// Copy the storage path.
			String[] path = new String[classPathList.size()];
			classPathList.copyInto(path);
			
			//#CM698889
			// Copy the storage key.
			String[] keyCode = new String[key.size()];
			key.copyInto(keyCode);
			
			//#CM698890
			// Report Message flag
			boolean noErrorFlag  = false;
			boolean exceptionFlag = false;
			
			//#CM698891
			// Area that stores messages.
			Hashtable messageTabale = new Hashtable();

			//#CM698892
			// Index for obtaining a key.
			int keyIndex = 0;

			//#CM698893
			// Start the process for reporting data according to the stored path.
			for (int i = 0; i < path.length; i++)
			{
				try
				{
					//#CM698894
					// Obtain the Index.
					//#CM698895
					// to determine the message storage key in the catch.
					keyIndex = i;
					
					wExternalReportDataCreator =
						ReportDataPlanSCH.makeExternalReportDataCreatorInstance(path[i]);
					//#CM698896
					// Execute the process for reporting data.
					if (wExternalReportDataCreator.report(conn, param[0]))
					{
						//#CM698897
						// Commit per package.
						try
						{
							conn.commit();
							//#CM698898
							// Set the message.
							messageTabale.put(keyCode[keyIndex], MessageResource.getMessage(wExternalReportDataCreator.getMessage()));
						}
						catch (SQLException sqle)
						{
							//#CM698899
							// 6007002=Database error occurred. See log.
							RmiMsgLogClient.write(new TraceHandler(6006002, sqle), this.getClass().getName());
							//#CM698900
							// Change the flag to true because exception occurred.
							exceptionFlag = true;
							//#CM698901
							// Set the message.
							messageTabale.put(keyCode[keyIndex], (MessageResource.getMessage("6007002")));
						}
						//#CM698902
						// Set the flag true as there is a normal process.
						noErrorFlag = true;
					}
					else
					{
						//#CM698903
						// Roll back per package.
						try
						{
							conn.rollback();
							//#CM698904
							// Set the message.
							messageTabale.put(keyCode[keyIndex], MessageResource.getMessage(wExternalReportDataCreator.getMessage()));
						}
						catch (SQLException sqle)
						{
							//#CM698905
							// 6007002=Database error occurred. See log.
							RmiMsgLogClient.write(new TraceHandler(6006002, sqle), this.getClass().getName());
							//#CM698906
							// Change the flag to true because exception occurred.
							//#CM698907
							// Set the message.
							messageTabale.put(keyCode[keyIndex], (MessageResource.getMessage("6007002")));
						}	
						//#CM698908
						// Change the flag to true because error occurred.
						exceptionFlag = true;
					}
				}
				catch (Exception e)
				{
					// Roll back per package.
					try
					{
						conn.rollback();
						//#CM698910
						// Set the message.
						if (wExternalReportDataCreator.getMessage() != null)
						{
							messageTabale.put(keyCode[keyIndex], MessageResource.getMessage(wExternalReportDataCreator.getMessage()));
						}
						else
						{
							messageTabale.put(keyCode[keyIndex], MessageResource.getMessage(getDisplayMessage(e)));
						}
					}
					catch (SQLException sqle)
					{
						// 6007002=Database error occurred. See log.
						RmiMsgLogClient.write(new TraceHandler(6006002, sqle), this.getClass().getName());
						// Set the message.
						messageTabale.put(keyCode[keyIndex], (MessageResource.getMessage("6007002")));
					}	
					//#CM698909
					// Change the flag to true because exception occurred.
					exceptionFlag = true;
				}
			}
			
			//#CM698911
			// Set the message per package.
			setPackageMessage(messageList, messageTabale);
			
			//#CM698912
			// Set the message.
			if (noErrorFlag && !exceptionFlag) 
			{
				//#CM698913
				// Set the message: "Completed".
				wMessage = "6001014";
				resultFlag = true;
			}
			else if (!noErrorFlag && !exceptionFlag)
			{
				//#CM698914
				// No target data was found.
				wMessage = "6003011";
				resultFlag = false;
			}
			else if (exceptionFlag)
			{
				//#CM698915
				// Error occurred.
				wMessage = "6023404";
				resultFlag = false;
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
			if (!resultFlag)
			{
				doRollBack(conn, wProcessName);
			}
			// If "Processing Report" flag was updated in its own class,
			// change the "Processing Report" flag to "0: Stopping".
			if (updateReportDataFlag)
			{
				changeReportDataFlag(conn, false);
				doCommit(conn, wProcessName);
			}
		}
		return resultFlag;
	}
	
	//#CM698916
	/**
	 * When <CODE>startSCH</CODE>method returns false, obtain a message to display the reason.
	 * Use this method to obtain the content to be displayed in the Message area on the screen.
	 * @return Details of message.
	 */
	public String[] getMessageList()
	{
		return wMessageList;
	}

	//#CM698917
	// Package methods -----------------------------------------------

	//#CM698918
	// Protected methods ---------------------------------------------

	//#CM698919
	// Private methods -----------------------------------------------

	//#CM698920
	/**
	 * Search for presence of data of the designated work date with status "Processing" in the Receiving plan info.
	 * @param  conn      Database connection object
	 * @return boolean   True if any data with Processing is included.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private boolean InstockWorkingCheck(Connection conn) throws ReadWriteException
	{
		InstockPlanHandler instockHandler = new InstockPlanHandler(conn);
		InstockPlanSearchKey searchKey = new InstockPlanSearchKey();
		searchKey.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);
		if (instockHandler.count(searchKey) == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	//#CM698921
	/**
	 * Search for presence of data of the designated work date with status "Processing" in the Storage Plan info.
	 * @param  conn      Database connection object
	 * @return boolean   True if any data with Processing is included.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private boolean StorageWorkingCheck(Connection conn) throws ReadWriteException
	{
		StoragePlanHandler storageHandler = new StoragePlanHandler(conn);
		StoragePlanSearchKey searchKey = new StoragePlanSearchKey();
		searchKey.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);
		if (storageHandler.count(searchKey) == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	//#CM698922
	/**
	 * Search for presence of data of the designated work date with status "Processing" in the Picking plan info.
	 * @param  conn      Database connection object
	 * @return boolean   True if any data with Processing is included.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private boolean RetrievalWorkingCheck(Connection conn) throws ReadWriteException
	{
		RetrievalPlanHandler retrievalHandler = new RetrievalPlanHandler(conn);
		RetrievalPlanSearchKey searchKey = new RetrievalPlanSearchKey();
		String[] status = {RetrievalPlan.STATUS_FLAG_START, RetrievalPlan.STATUS_FLAG_NOWWORKING};
		searchKey.setStatusFlag(status);
		if (retrievalHandler.count(searchKey) == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	//#CM698923
	/**
	 * Search for presence of data of the designated work date with status "Processing" in the Sorting plan info.
	 * @param  conn      Database connection object
	 * @return boolean   True if any data with Processing is included.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private boolean SortingWorkingCheck(Connection conn) throws ReadWriteException
	{
		SortingPlanHandler sortingHandler = new SortingPlanHandler(conn);
		SortingPlanSearchKey searchKey = new SortingPlanSearchKey();
		searchKey.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);
		if (sortingHandler.count(searchKey) == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	//#CM698924
	/**
	 * Search for presence of data of the designated work date with status "Processing" in the Shipping plan info.
	 * @param  conn      Database connection object
	 * @return boolean   True if any data with Processing is included.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private boolean ShippingWorkingCheck(Connection conn) throws ReadWriteException
	{
		ShippingPlanHandler shipHandler = new ShippingPlanHandler(conn);
		ShippingPlanSearchKey searchKey = new ShippingPlanSearchKey();
		searchKey.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);
		if (shipHandler.count(searchKey) == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	//#CM698925
	/**
	 * Set the message matching to the occurred exception.
	 * 
	 * @param  Exception class.
	 * @return Message corresponding to the exception.
	 * @throws Exception Report all exceptions. 
	 */
	private String getDisplayMessage(Exception e) throws Exception
	{
		String message = null;
		if (e instanceof ScheduleException)
		{
			//#CM698926
			// Internal error occurred. See log.
			message = "6027005";
		}
		else if (e instanceof ReadWriteException)
		{
			//#CM698927
			// Database error occurred. See log.
			message = "6007002";
		}
		else if (e instanceof SQLException)
		{
			//#CM698928
			// Database error occurred. See log.
			message = "6007002";
		}
		else
		{
			//#CM698929
			// 6006001=Unexpected error occurred.{0}
			message = "6027009";
		}
		return message;
	}
	
	//#CM698930
	/**
	 * Set the message per package.
	 * @param  messageList     List for storing messages.
	 * @return messageTabale   Area that stores messages of results of each package process.
	 */
	private void setPackageMessage(Vector messageList, Hashtable messageTabale)
	{
		//#CM698931
		// Maintain the message.
		wMessageList = new String[messageList.size()];
		//#CM698932
		// Receiving
		if (!StringUtil.isBlank((String)messageTabale.get(INSTOCK)))
		{
			wMessageList[0] = (String)messageTabale.get(INSTOCK);
		}
		else
		{
			wMessageList[0] = (String)messageList.get(0);
		}
		//#CM698933
		// Storage
		if (!StringUtil.isBlank((String)messageTabale.get(STORAGE)))
		{
			wMessageList[1] = (String)messageTabale.get(STORAGE);
		}
		else
		{
			wMessageList[1] = (String)messageList.get(1);
		}
		//#CM698934
		// Picking
		if (!StringUtil.isBlank((String)messageTabale.get(RETRIEVAL)))
		{
			wMessageList[2] = (String)messageTabale.get(RETRIEVAL);
		}
		else
		{
			wMessageList[2] = (String)messageList.get(0);
		}
		//#CM698935
		// Sorting
		if (!StringUtil.isBlank((String)messageTabale.get(SORTING)))
		{
			wMessageList[3] = (String)messageTabale.get(SORTING);
		}
		else
		{
			wMessageList[3] = (String)messageList.get(0);
		}
		//#CM698936
		// Shipping
		if (!StringUtil.isBlank((String)messageTabale.get(SHIPPING)))
		{
			wMessageList[4] = (String)messageTabale.get(SHIPPING);
		}
		else
		{
			wMessageList[4] = (String)messageList.get(0);
		}
	}

}
//#CM698937
//end of class
