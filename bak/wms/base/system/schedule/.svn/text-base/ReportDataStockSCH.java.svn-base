//#CM698938
//$Id: ReportDataStockSCH.java,v 1.3 2006/11/21 04:22:39 suresh Exp $

//#CM698939
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.io.File;
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.IniFileOperation;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Stock;

//#CM698940
/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Yoshino   <BR>
 *
 * Allow the <CODE>ReportDataStockSCH</CODE> class to execute the process for reporting data of inventory information.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for generating an instance.(<CODE>makeExternalReportDataCreatorInstance(Connection conn, String classname)</CODE>method)<BR>
 * <DIR>
 * Generate an instance for the class that implements the <code>ExternalReportDataCreator</code> interface using the inventory package.<BR>
 *  <BR>
 * </DIR>
 * <BR>
 * 2.Process for reporting data(<CODE>startSCH(Connection conn, Parameter startParam)</CODE>method)<BR>
 * <DIR>
 * Invoke the method for "Generate Instance" in this method.<BR>
 * Use the class by using an common interface.<BR>
 * </DIR>
 * <BR>
 * 3.Initial Display Process(<CODE>initFind(Connection conn, Parameter startParam)</CODE>method)<BR>
 * <DIR>
 * Execute the process for initial display of the screen for reporting the inventory information.<BR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/02/15</TD><TD>T.Yoshino</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:22:39 $
 * @author  $Author: suresh $
 */
public class ReportDataStockSCH extends AbstractSystemSCH
{
	//#CM698941
	// Class fields --------------------------------------------------
	//#CM698942
	/**
	 * Report Data class (inventory information)<BR>
	 */
	public static final String STOCK_REPORT_CLASS =
		"jp.co.daifuku.wms.stockcontrol.schedule.StockReportDataCreator";

	//#CM698943
	// Class variables -----------------------------------------------

	//#CM698944
	/** Data type: Inventory information **/

	private static final int REPORTTYPE_STOCK = 0;

	//#CM698945
	/** Key for setting data environment **/

	private static final String[] LOADTYPE_KEY = { "STOCK_SUPPORT" };

	//#CM698946
	/** Section name of Load Data folder **/

	private static final String REPORTDATA_FOLDER = "REPORTDATA_FOLDER";

	//#CM698947
	/** Section name of Load Data file name **/

	private static final String REPORTDATA_FILENAME = "REPORTDATA_FILENAME";

	//#CM698948
	/**
	 * Class Name (Report Inventory Information)
	 */
	private final String wProcessName = "ReportDataStockSCH";

	//#CM698949
	/**
	 * Instance for reporting data maintained by this ReportDataStockSCH instance.<BR>
	 */
	protected ExternalReportDataCreator wExternalReportDataCreator = null;

	//#CM698950
	/**
	 * Delimiter
	 * A separator between parameters of MessageDef Message when exception occurs.<BR>
	 * 	Example String msginfo = "9000000" + wDelimta + "Palette" + wDelim + "Stock" ;
	 */
	public static String wDelimta = MessageResource.DELIM;

	//#CM698951
	// Class method --------------------------------------------------
	//#CM698952
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
			Class loadClass = Class.forName(className);

			tgt = loadClass.newInstance();
			if ((tgt instanceof ExternalReportDataCreator) == false)
			{
				//#CM698953
				// Set a message: "Object other than {0} was returned".
				RmiMsgLogClient.write(
					"6006008" + wDelimta + "ExternalReportDataCreator",
					"ReportDataStockSCH");
				throw (new InvalidDefineException());
			}
		}
		catch (Exception e)
		{
			//#CM698954
			//Output the error log.
			e.printStackTrace();
			//#CM698955
			//  Failed to generate instance. ClassName={0} Exception: {1}
			RmiMsgLogClient.write("6006003" + wDelimta + className, "ReportDataStockSCH");
			throw (new InvalidDefineException("6006003" + wDelimta + className));
		}
		return (ExternalReportDataCreator) tgt;
	}

	//#CM698956
	// Constructors --------------------------------------------------
	//#CM698957
	/**
	 * Constructor
	 */
	public ReportDataStockSCH()
	{
	}

	//#CM698958
	// Public methods ------------------------------------------------
	//#CM698959
	/**
	 * This method obtains the data required for initial display.<BR>
	 * Search conditions passes a class that inherits the<CODE>Parameter</CODE> class.<BR>
	 * If only one Consignor code exist in the inventory information , return the corresponding Consignor code.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. 
	 * @param conn        Database connection object
	 * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions.
	 * @return A class that implements the <CODE>Parameter</CODE> interface that contains the search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		SystemParameter param = new SystemParameter();
		StockReportFinder stockReportFinder = new StockReportFinder(conn);
		StockSearchKey stockSearchKey = new StockSearchKey();

		//#CM698961
		// Status flag (Center Inventory)
		stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		stockSearchKey.setStockQty(0, ">");
		stockSearchKey.setConsignorCodeGroup(1);
		stockSearchKey.setConsignorCodeCollect("");

		try
		{
			Stock[] stock = null;
			if (stockReportFinder.search(stockSearchKey) == 1)
			{
				stock = (Stock[]) ((StockReportFinder) stockReportFinder).getEntities(1);
				param.setConsignorCode(stock[0].getConsignorCode());
			}

			//#CM698962
			// Obtain the data storage folder and file name from the environment setting file.
			IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);

			if (isStockPack(conn))
			{
				param.setFolder_ReportDataStock(
					IO.get(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_STOCK]));
				param.setFileName_ReportDataStock(
					IO.get(REPORTDATA_FILENAME, LOADTYPE_KEY[REPORTTYPE_STOCK]));
				param.setSelectReportDataStock(true);
			}
			else
			{
				param.setSelectReportDataStock(false);
			}
		}
		catch (ReadWriteException e)
		{
			//#CM698963
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));

		}

		return param;
	}

	//#CM698964
	/**
	 * Check for correctness of the Worker Code and the password.<BR>
	 * Check for presence of consignor code.<BR>
	 * Check whether generating data for reporting is in progress or not.<BR>
	 * @param conn       Database connection object
	 * @param checkParam This parameter class includes contents to be checked for input.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean check(Connection conn, Parameter checkParam)
		throws ReadWriteException, ScheduleException
	{
		//#CM698965
		// Translate the data type of checkParam.
		SystemParameter param = (SystemParameter) checkParam;

		//#CM698966
		// Obtain Worker Code, Password, and Consignor code from parameters.
		String consignorcode = param.getConsignorCode();

		//#CM698967
		// Check the Worker Code, password, and Access Privileges.
		if (!checkWorker(conn, param, true))
		{
			return false;
		}

		//#CM698968
		// Check the consignor code.
		if (consignorcode != null && !consignorcode.equals(""))
		{
			StockHandler stockHandler = new StockHandler(conn);
			StockSearchKey searchKey = new StockSearchKey();
			searchKey.setConsignorCode(consignorcode);

			//#CM698969
			// Check the consignor code.
			if (stockHandler.count(searchKey) <= 0)
			{
				//#CM698970
				// Set a message: "Enter a valid consignor code".
				wMessage = "6023381";
				return false;
			}
		}
		
		String Stock = DisplayText.getText("RDB-W0125");
		//#CM698971
		// Check the number of characters used in the folder and the path.
		if(param.getFolder_ReportDataStock().getBytes().length + 
		param.getFileName_ReportDataStock().getBytes().length > SystemParameter.FILE_PATHS_MAXLENGTH)
		{
			//#CM698972
			// 6023509=The folder and file name length of {0} can't exceed {1}.
			wMessage = "6023509" + wDelim + Stock + wDelim + SystemParameter.FILE_PATHS_MAXLENGTH;
			return false;
		}
		
		//#CM698973
		// Check for generating in progress for reporting data.
		if (isReportData(conn))
		{
			return false;
		}

		return true;
	}

	//#CM698974
	/**
	 * Start the schedule.Check for input and execute the process for reporting the inventory information
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
			//#CM698975
			// Translate the type of startParams.
			SystemParameter[] param = (SystemParameter[]) startParams;

			//#CM698976
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

			//#CM698977
			// Check for presence of the designated inventory information data storage folder.
			if (!chkDir(param[0].getFolder_ReportDataStock()))
			{
				//#CM698978
				// 6023030=Please select the correct folder.
				wMessage = "6023030";
				return false;
			}

			//#CM698979
			// Check the daily update in process.
			if (isDailyUpdate(conn))
			{
				return false;
			}

			//#CM698980
			// Read the Environment setting file (EnvironmentInformation).
			IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);
			String wYenmark = "\\";
			String wFolder = null;

			//#CM698981
			// Check whether the data storage folder is suffixed with "\". If not suffixed, suffix it and submit it.		
			int len = param[0].getFolder_ReportDataStock().length();
			if (!param[0].getFolder_ReportDataStock().substring(len - 1, len).equals(wYenmark))
			{
				wFolder = param[0].getFolder_ReportDataStock() + "\\";
			}
			else
			{
				wFolder = param[0].getFolder_ReportDataStock();
			}

			IO.set(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_STOCK], wFolder);
			IO.set(
				REPORTDATA_FILENAME,
				LOADTYPE_KEY[REPORTTYPE_STOCK],
				param[0].getFileName_ReportDataStock());
			IO.flush();

			//#CM698982
			// Generate a Report Data class.
			wExternalReportDataCreator =
				ReportDataStockSCH.makeExternalReportDataCreatorInstance(STOCK_REPORT_CLASS);
			//#CM698983
			// Execute the process for reporting data.
			if (wExternalReportDataCreator.report(conn, param[0]))
			{
				wMessage = wExternalReportDataCreator.getMessage();
				resultFlag = true;
			}
			else
			{
				wMessage = wExternalReportDataCreator.getMessage();
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

	//#CM698984
	// Package methods -----------------------------------------------

	//#CM698985
	// Protected methods ---------------------------------------------

	//#CM698986
	// Private methods -----------------------------------------------
	/**
	 * Examine whether the designated folder is Enabled or not.
	 *
	 * @param		cheDirName	Process identifier
	 * @return		boolean		True if Enabled. true
	 * @exception	IOException Announce when failed to process input/output or when interruption occurred.
	 */
	private boolean chkDir(String cheDirName) throws java.io.IOException
	{
		// File check
		File objFile = new File(cheDirName); 
		return (objFile.exists() && objFile.isDirectory());
	}
}
//#CM698991
//end of class
