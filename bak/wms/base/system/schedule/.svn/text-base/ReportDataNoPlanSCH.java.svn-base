//#CM698716
//$Id: ReportDataNoPlanSCH.java,v 1.4 2006/11/21 04:39:26 suresh Exp $

//#CM698717
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

//#CM698718
/**
 * Designer : T.Nakai <BR>
 * Maker : T.Nakai   <BR>
 *
 * Allow the <CODE>ReportDataNoPlanSCH</CODE> class to execute the process for reporting data of unplanned work.<BR>
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
 * Allow the eWareNavi-Basic to invoke the list of the following classes for reporting unplanned data.<BR>
 * <DIR>
 * -  Process for reporting Relocation result (MovementReportDataCreator)<BR>
 * -  Process for reporting Inventory Check Result (InventoryReportDataCreator)<BR>
 * -  Process for reporting Unplanned Storage Result (NoPlanStorageReportDataCreator)<BR>
 * -  Process for reporting Unplanned Picking Result (NoPlanRetrievalReportDataCreator)<BR>
 * </DIR>
 * <BR>
 * 3.Initial Display Process(<CODE>initFind(Connection conn, Parameter startParam)</CODE>method)<BR>
 * <BR>
 * Execute the process for initial display of buttons in the Report Data screen.<BR>
 * Referring to System definition table(WARENAVI_SYSTEM), set true as a value only for a checkbox of the Report data with package flag "1: Available", and return it.<BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/03</TD><TD>T.Nakai</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2006/11/21 04:39:26 $
 * @author  $Author: suresh $
 */
public class ReportDataNoPlanSCH extends AbstractSystemSCH
{

	//#CM698719
	// Class fields --------------------------------------------------
	//#CM698720
	/**
	 * Report Data class (Stock Relocation)<BR>
	 */
	protected static final String MOVEMENT_REPORT_CLASS =
		"jp.co.daifuku.wms.storage.schedule.MoveReportDataCreator";

	//#CM698721
	/**
	 * Report Data class (Inventory Check)<BR>
	 */
	protected static final String INVENTORY_REPORT_CLASS =
		"jp.co.daifuku.wms.storage.schedule.InventoryReportDataCreator";

	//#CM698722
	/**
	 * Report Data class (Unplanned Storage)<BR>
	 */
	protected static final String NOPLANSTORAGE_REPORT_CLASS =
		"jp.co.daifuku.wms.stockcontrol.schedule.NoPlanStorageReportDataCreator";

	//#CM698723
	/**
	 * Report Data class (Unplanned Picking)<BR>
	 */
	protected static final String NOPLANRETRIEVAL_REPORT_CLASS =
		"jp.co.daifuku.wms.stockcontrol.schedule.NoPlanRetrievalReportDataCreator";
		
	//#CM698724
	/**
	 * Stock relocation message storage key<BR>
	 */
	protected static final String STOCKMOVE = "STOCKMOVE";

	//#CM698725
	/**
	 * Inventory Check message storage key<BR>
	 */
	protected static final String INVENTORY = "INVENTORY";

	//#CM698726
	/**
	 * Unplanned Storage message storage key<BR>
	 */
	protected static final String NOPLANSTORAGE = "NOPLANSTORAGE";

	//#CM698727
	/**
	 * Unplanned picking message storage key<BR>
	 */
	protected static final String NOPLANRETRIEVAL = "NOPLANRETRIEVAL";

	//#CM698728
	// Class variables -----------------------------------------------

	//#CM698729
	/**
	 * Class Name (Report Unplanned Data)
	 */
	private final String wProcessName = "ReportDataNoPlanSCH";

	//#CM698730
	/**
	 * Instance for reporting data maintained by this ReportDataNoPlanSCH instance.<BR>
	 */
	protected ExternalReportDataCreator wExternalReportDataCreator = null;

	//#CM698731
	/**
	 * Delimiter
	 * A separator between parameters of MessageDef Message when exception occurs.<BR>
	 */
	public static String wDelimta = MessageResource.DELIM;
	
	//#CM698732
	/**
	 * Area to maintain the message<BR>
	 * Use this to maintain the contents when condition error etc occurs by invoking a method.
	 */
	protected String[] wMessageList;

	//#CM698733
	// Class method --------------------------------------------------
	//#CM698734
	/**
	 * Generate an instance for the class that implements the <code>ExternalReportDataCreator</code> interface using the designated class name.
	 * 
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
			Class loadClass = Class.forName(className);

			tgt = loadClass.newInstance();
			if ((tgt instanceof ExternalReportDataCreator) == false)
			{
				//#CM698735
				// Set a message: "Object other than {0} was returned".
				RmiMsgLogClient.write(
					"6006008" + wDelimta + "ExternalReportDataCreator",
					"ReportDataNoPlanSCH");
				throw (new InvalidDefineException());
			}
		}
		catch (Exception e)
		{
			//#CM698736
			//Output the error log.
			e.printStackTrace();
			//#CM698737
			//  Failed to generate instance. ClassName={0} Exception: {1}
			RmiMsgLogClient.write("6006003" + wDelimta + className, "ReportDataNoPlanSCH");
			throw (new InvalidDefineException("6006003" + wDelimta + className));
		}
		return (ExternalReportDataCreator) tgt;
	}

	//#CM698738
	// Constructors --------------------------------------------------
	//#CM698739
	/**
	 * Constructor
	 */
	public ReportDataNoPlanSCH()
	{
	}

	//#CM698740
	// Public methods ------------------------------------------------
	//#CM698741
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
		//#CM698742
		// Translate the type of startParams.
		SystemParameter param = (SystemParameter) searchParam;

		//#CM698743
		// Work date
		param.setPlanDate(getWorkDate(conn));
		//#CM698744
		// Relocation Result
		param.setSelectReportMovementData(isStoragePack(conn));
		//#CM698745
		// Inventory Check Result
		param.setSelectReportInventoryData(isStoragePack(conn));
		//#CM698746
		// Unplanned Storage Result
		param.setSelectReportNoPlanStorageData(isStockPack(conn));
		//#CM698747
		// Unplanned Picking Result
		param.setSelectReportNoPlanRetrievalData(isStockPack(conn));

		return param;
	}

	//#CM698748
	/**
	 * Check that the contents of Worker Code and password are correct.<BR>
	 * Check for generating in progress for reporting data.<BR>
	 * Return true if the content is proper, or return false if improper.<BR>
	 * 
	 * @param conn       Database connection object
	 * @param checkParam This parameter class includes contents to be checked for input.
	 * @return Return true if the content is proper, or return false if improper.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean check(Connection conn, Parameter checkParam)
		throws ReadWriteException, ScheduleException
	{
		//#CM698749
		// Translate the data type of checkParam.
		SystemParameter param = (SystemParameter) checkParam;

		//#CM698750
		// Check the Worker Code, password, and Access Privileges.
		if (!checkWorker(conn, param, true))
		{
			return false;
		}

		//#CM698751
		// Check for generating in progress for reporting data.
		if (isReportData(conn))
		{
			return false;
		}
	
		return true;
	}

	//#CM698752
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

			//#CM698753
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

			//#CM698754
			// Check the Daily update process.
			if (isDailyUpdate(conn))
			{
				return false;
			}

			//#CM698755
			// Check whether a checkbox is ticked off or not.
			if (!param[0].getSelectReportMovementData()
				&& !param[0].getSelectReportInventoryData()
				&& !param[0].getSelectReportNoPlanStorageData()
				&& !param[0].getSelectReportNoPlanRetrievalData())
			{
				//#CM698756
				// 6023028=Please select the target data.
				wMessage = "6023028";
				return false;
			}
			
			//#CM698757
			// List for storing messages.
			Vector messageList = new Vector();
			//#CM698758
			// Area that stores keys for obtaining messages
			Vector key = new Vector();

			//#CM698759
			// List that stores paths for classes.
			Vector classPathList = new Vector();

			//#CM698760
			// Inventory relocation
			if (isStoragePack(conn) && param[0].getSelectReportMovementData())
			{
				classPathList.add(MOVEMENT_REPORT_CLASS);
				key.add(STOCKMOVE);
			}
			messageList.add(" ");
			//#CM698761
			// Inventory Check
			if (isStoragePack(conn) && param[0].getSelectReportInventoryData())
			{
				classPathList.add(INVENTORY_REPORT_CLASS);
				key.add(INVENTORY);
			}
			messageList.add(" ");
			//#CM698762
			// Unplanned Storage
			if (isStockPack(conn) && param[0].getSelectReportNoPlanStorageData())
			{
				classPathList.add(NOPLANSTORAGE_REPORT_CLASS);
				key.add(NOPLANSTORAGE);
			}
			messageList.add(" ");
			//#CM698763
			// Unplanned Picking
			if (isStockPack(conn) && param[0].getSelectReportNoPlanRetrievalData())
			{
				classPathList.add(NOPLANRETRIEVAL_REPORT_CLASS);
				key.add(NOPLANRETRIEVAL);
			}
			messageList.add(" ");

			//#CM698764
			// Copy the contents to be stored.
			String[] path = new String[classPathList.size()];
			classPathList.copyInto(path);
			
			//#CM698765
			// Copy the storage key.
			String[] keyCode = new String[key.size()];
			key.copyInto(keyCode);
			
			//#CM698766
			// Report Message flag
			boolean noErrorFlag  = false;
			boolean exceptionFlag = false;
			
			//#CM698767
			// Area that stores messages.
			Hashtable messageTabale = new Hashtable();

			//#CM698768
			// Index for obtaining a key.
			int keyIndex = 0;

			//#CM698769
			// Start the process for reporting data according to the stored path.
			for (int i = 0; i < path.length; i++)
			{
				try
				{
					//#CM698770
					// Obtain the Index.
					//#CM698771
					// to determine the message storage key in the catch.
					keyIndex = i;
					
					wExternalReportDataCreator =
						ReportDataNoPlanSCH.makeExternalReportDataCreatorInstance(path[i]);
					//#CM698772
					// Execute the process for reporting data.
					if (wExternalReportDataCreator.report(conn, param[0]))
					{
						//#CM698773
						// Commit per package.
						try
						{
							conn.commit();
							//#CM698774
							// Set the message.
							messageTabale.put(keyCode[keyIndex], MessageResource.getMessage(wExternalReportDataCreator.getMessage()));
						}
						catch (SQLException sqle)
						{
							//#CM698775
							// 6007002=Database error occurred. See log.
							RmiMsgLogClient.write(new TraceHandler(6006002, sqle), this.getClass().getName());
							//#CM698776
							// Change the flag to true because exception occurred.
							exceptionFlag = true;
							//#CM698777
							// Set the message.
							messageTabale.put(keyCode[keyIndex], (MessageResource.getMessage("6007002")));
						}
						//#CM698778
						// Set the flag true as there is a normal process.
						noErrorFlag = true;
					}
					else
					{
						//#CM698779
						// Roll back per package.
						try
						{
							conn.rollback();
							//#CM698780
							// Set the message.
							messageTabale.put(keyCode[keyIndex], MessageResource.getMessage(wExternalReportDataCreator.getMessage()));
						}
						catch (SQLException sqle)
						{
							//#CM698781
							// 6007002=Database error occurred. See log.
							RmiMsgLogClient.write(new TraceHandler(6006002, sqle), this.getClass().getName());
							//#CM698782
							// Set the message.
							messageTabale.put(keyCode[keyIndex], (MessageResource.getMessage("6007002")));
						}	
						//#CM698783
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
						//#CM698785
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
					//#CM698784
					// Change the flag to true because exception occurred.
					exceptionFlag = true;
				}
			}
			
			//#CM698786
			// Set the message per package.
			setPackageMessage(messageList, messageTabale);
			
			//#CM698787
			// Set the message.
			if (noErrorFlag && !exceptionFlag) 
			{
				//#CM698788
				// Set the message: "Completed".
				wMessage = "6001014";
				resultFlag = true;
			}
			else if (!noErrorFlag && !exceptionFlag)
			{
				//#CM698789
				// No target data was found.
				wMessage = "6003011";
			}
			else if (exceptionFlag)
			{
				//#CM698790
				// Error occurred.
				wMessage = "6023404";
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
	
	//#CM698791
	/**
	 * When <CODE>startSCH</CODE> method returns false, obtain a message to display the reason.
	 * Use this method to obtain the content to be displayed in the Message area on the screen.
	 * @return Details of message.
	 */
	public String[] getMessageList()
	{
		return wMessageList;
	}

	//#CM698792
	// Package methods -----------------------------------------------

	//#CM698793
	// Protected methods ---------------------------------------------

	//#CM698794
	// Private methods -----------------------------------------------
	
	//#CM698795
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
			//#CM698796
			// Internal error occurred. See log.
			message = "6027005";
		}
		else if (e instanceof ReadWriteException)
		{
			//#CM698797
			// Database error occurred. See log.
			message = "6007002";
		}
		else if (e instanceof SQLException)
		{
			//#CM698798
			// Database error occurred. See log.
			message = "6007002";
		}
		else
		{
			//#CM698799
			// 6006001=Unexpected error occurred.{0}
			message = "6027009";
		}
		return message;
	}
	
	//#CM698800
	/**
	 * Set the message per package.
	 * @param  messageList    List for storing messages.
	 * @param messageTabale   Area that stores messages of results of each package process.
	 */
	private void setPackageMessage(Vector messageList, Hashtable messageTabale)
	{
		//#CM698801
		// Maintain the message.
		wMessageList = new String[messageList.size()];
		//#CM698802
		// Inventory relocation
		if (!StringUtil.isBlank((String)messageTabale.get(STOCKMOVE)))
		{
			wMessageList[0] = (String)messageTabale.get(STOCKMOVE);
		}
		else
		{
			wMessageList[0] = (String)messageList.get(0);
		}
		//#CM698803
		// Inventory Check
		if (!StringUtil.isBlank((String)messageTabale.get(INVENTORY)))
		{
			wMessageList[1] = (String)messageTabale.get(INVENTORY);
		}
		else
		{
			wMessageList[1] = (String)messageList.get(1);
		}
		//#CM698804
		// Unplanned Storage
		if (!StringUtil.isBlank((String)messageTabale.get(NOPLANSTORAGE)))
		{
			wMessageList[2] = (String)messageTabale.get(NOPLANSTORAGE);
		}
		else
		{
			wMessageList[2] = (String)messageList.get(0);
		}
		//#CM698805
		// Unplanned Picking
		if (!StringUtil.isBlank((String)messageTabale.get(NOPLANRETRIEVAL)))
		{
			wMessageList[3] = (String)messageTabale.get(NOPLANRETRIEVAL);
		}
		else
		{
			wMessageList[3] = (String)messageList.get(0);
		}
	}
}
//#CM698806
//end of class
