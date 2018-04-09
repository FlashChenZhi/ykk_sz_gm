//#CM698312
//$Id: LoadDataEnvironmentSCH.java,v 1.2 2006/11/13 06:03:08 suresh Exp $

//#CM698313
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.IniFileOperation;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;

//#CM698314
/**
 * Designer : T.Yamashita <BR>
 * Maker : T.Yamashita   <BR>
 *
 * Allow the <CODE>LoadDataEnvironmentSCH</CODE> class to execute the process to set the environment for loading data.<BR>
 * Set the Plan file storage folder and the Plan file name by introduced package.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for setting the environment.(<CODE>startSCH(Connection conn, Parameter[] startParams)</CODE>method)<BR>
 * <BR>
 *   [Parameter] *Mandatory Input + checkbox ON(TRUE): Mandatory Input<BR>
 *   <DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Receiving plan data <BR>
 *     <DIR>
 *       Receiving Plan checkbox* <BR>
 *       Folder name for Receiving plan data + <BR>
 *       File name for Receiving plan data + <BR>
 *     </DIR>
 *     Storage plan data <BR>
 *     <DIR>
 *       Storage Plan checkbox* <BR>
 *       Folder name for Storage plan data + <BR>
 *       File name for Storage plan data + <BR>
 *     </DIR>
 *     Picking plan data <BR>
 *     <DIR>
 *       Picking Plan checkbox* <BR>
 *       Folder name for Picking plan data + <BR>
 *       File name for Picking plan data + <BR>
 *     </DIR>
 *     Sorting Plan data <BR>
 *     <DIR>
 *       Sorting Plan checkbox* <BR>
 *       Folder name for Sorting plan data + <BR>
 *       File name for Sorting plan data + <BR>
 *     </DIR>
 *     Shipping Inspection Plan data <BR>
 *     <DIR>
 *       Shipping Inspection Plan checkbox* <BR>
 *       Folder name for Shipping Inspection Plan data + <BR>
 *       File name for Shipping Inspection Plan data + <BR>
 *     </DIR>
 *   </DIR>
 *   [Returned data] <BR>
 *   <DIR>
 *     Give the result. <BR>
 *   </DIR>
 *   [Update Process] <BR>
 *   <DIR>
 *     1-1.Ensure to define Worker code and password in the Worker master. <BR>
 *     Execute the following processes only for data of the package of which checkbox is ON (True). <BR>
 *     1-2.Check for presence of the entered folder.<BR>
 *     1-3.Add "\" if it is not suffixed to the entered folder.<BR>
 *     1-4.Update the storage folder and the file name set for the Environment setting file (EnvironmentInformation).<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Initial Display Process(<CODE>initFind(Connection conn, Parameter searchParam)</CODE> method)<BR>
 * <BR>
 * <DIR>
 *   Execute the process for initial display of buttons in the Load Data screen Load Data (environment setting).<BR>
 *   Return the storage folder and the file name of the Plan data. Obtain the data to be set from the Environment setting file(EnvironmentInformation). <BR>
 *   <BR>
 *   [Returned data] + Return the followings if the relevant checkbox is ON (TRUE). <BR>
 *   <DIR>
 *     Receiving plan data <BR>
 *     <DIR>
 *       Receiving Plan checkbox* <BR>
 *       Folder name for Receiving plan data + <BR>
 *       File name for Receiving plan data + <BR>
 *     </DIR>
 *     Storage plan data <BR>
 *     <DIR>
 *       Storage Plan checkbox* <BR>
 *       Folder name for Storage plan data + <BR>
 *       File name for Storage plan data + <BR>
 *     </DIR>
 *     Picking plan data <BR>
 *     <DIR>
 *       Picking Plan checkbox* <BR>
 *       Folder name for Picking plan data + <BR>
 *       File name for Picking plan data + <BR>
 *     </DIR>
 *     Sorting Plan data <BR>
 *     <DIR>
 *       Sorting Plan checkbox* <BR>
 *       Folder name for Sorting plan data + <BR>
 *       File name for Sorting plan data + <BR>
 *     </DIR>
 *     Shipping Inspection Plan data <BR>
 *     <DIR>
 *       Shipping Inspection Plan checkbox* <BR>
 *       Folder name for Shipping Inspection Plan data + <BR>
 *       File name for Shipping Inspection Plan data + <BR>
 *     </DIR>
 *   </DIR>
 *   <BR>
 *   [Process for obtaining information] <BR>
 *   2-1.Obtain the information using the following conditions. <BR>
 *   <DIR>
 *     [Determine for Checkbox] <BR>
 *     <DIR>
 *       Refer to the System definition table (WARENAVI_SYSTEM). If the package flag is set to "1: Available", <BR>
 *       announce the Display condition by ON (TRUE). <BR>
 *     </DIR>
 *     [Obtain Folder/File names] <BR> 
 *     <DIR>
 *       Compile only the checkbox to return ON (True) to each package.
 *       Referring to the Environment setting file (EnvironmentInformation), obtain the folder name and the file name for loading data by package. <BR>
 *     </DIR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 3.Input check process(<CODE>check(Connection conn, Parameter checkParam)</CODE> method)<BR>
 * <BR>
 * <DIR>
 *   Check the Worker code and password.(<CODE>check(Connection conn, Parameter checkParam)</CODE> method) <BR>
 * </DIR>
 * <BR>
 * Disable to use the following methods in this process. <BR>
 * Invoking this throws <CODE>ScheduleException</CODE>. <BR>
 * <DIR>
 *   Display in the screen (<CODE>Query()</CODE> Method) <BR> 
 *   Schedule process: Returned information available (<CODE>startSCHgetParams()</CODE> method) <BR> 
 *   Process of checking for shifting to the next screen (<CODE>nextCheck()</CODE> method) <BR> 
 * </DIR>
* <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/24</TD><TD>T.Yamashita</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:08 $
 * @author  $Author: suresh $
 */
public class LoadDataEnvironmentSCH extends AbstractSystemSCH
{

	//#CM698315
	// Class fields --------------------------------------------------

	//#CM698316
	// Class variables -----------------------------------------------

	//#CM698317
	/**
	 * Define the INDEX value for obtaining a key for setting data environment <BR>
	 * Data type: Receiving
	 */
	private static final int LOADTYPE_INSTOCKRECEIVE = 0;
	//#CM698318
	/**
	 * Define the INDEX value for obtaining a key for setting data environment <BR>
	 * Data type: Storage
	 */
	private static final int LOADTYPE_STRAGESUPPORT = 1;
	//#CM698319
	/**
	 * Define the INDEX value for obtaining a key for setting data environment <BR>
	 * Data type: Picking
	 */
	private static final int LOADTYPE_RETRIEVALSUPPORT = 2;
	//#CM698320
	/**
	 * Define the INDEX value for obtaining a key for setting data environment <BR>
	 * Data type: Sorting
	 */
	private static final int LOADTYPE_PICKINGSUPPORT = 3;
	//#CM698321
	/**
	 * Define the INDEX value for obtaining a key for setting data environment <BR>
	 * Data type: Shipping
	 */
	private static final int LOADTYPE_SHIPPINGINSPECTION = 4;

	//#CM698322
	/**
	 * Define the key for setting data environment in the form of array. <BR> 
	 */
	private static final String[] LOADTYPE_KEY =
		{
			"INSTOCK_RECEIVE",
			"STRAGE_SUPPORT",
			"RETRIEVAL_SUPPORT",
			"PICKING_SUPPORT",
			"SHIPPING_INSPECTION" };

	//#CM698323
	/** Section name of Report Data folder **/

	private static final String REPORTDATA_FOLDER = "REPORTDATA_FOLDER";

	//#CM698324
	/** Section name of Report Data file name **/

	private static final String REPORTDATA_FILENAME = "REPORTDATA_FILENAME";
	
	//#CM698325
	/**
	 * Define the section name of the folder of data to be loaded. <BR>
	 */
	private static final String DATALOAD_FOLDER = "DATALOAD_FOLDER";

	//#CM698326
	/**
	 * Define the section name of the file name of data to be loaded. <BR>
	 */
	private static final String DATALOAD_FILENAME = "DATALOAD_FILENAME";

	//#CM698327
	// Class method --------------------------------------------------

	//#CM698328
	// Constructors --------------------------------------------------
	//#CM698329
	/**
	 * Constructor
	 */
	public LoadDataEnvironmentSCH()
	{
	}

	//#CM698330
	// Public methods ------------------------------------------------
	//#CM698331
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

		SystemParameter param = new SystemParameter();
		try
		{
			//#CM698332
			// Obtain the environment info descriptor.
			IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);

			//#CM698333
			// Obtain the storage folder and the file name from the Environment setting file.
			//#CM698334
			// Receiving plan data
			//#CM698335
			// Compile only when Package is "Add-ON".
			if (isInstockPack(conn))
			{
				//#CM698336
				// Set the checkbox ON (TRUE).
				param.setSelectLoadInstockData(true);
				//#CM698337
				// Set the folder for storing the data.
				param.setFolder_LoadInstockData(
					IO.get(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_INSTOCKRECEIVE]));
				//#CM698338
				// Set a file name.
				param.setFileName_LoadInstockData(
					IO.get(DATALOAD_FILENAME, LOADTYPE_KEY[LOADTYPE_INSTOCKRECEIVE]));
			}
			else
			{
				//#CM698339
				// Set the checkbox OFF (FALSE).
				param.setSelectLoadInstockData(false);
			}

			//#CM698340
			// Storage plan data
			//#CM698341
			// Compile only when Package is "Add-ON".
			if (isStoragePack(conn))
			{
				//#CM698342
				// Set the checkbox ON (TRUE).
				param.setSelectLoadStorageData(true);
				//#CM698343
				// Set the folder for storing the data.
				param.setFolder_LoadStorageData(
					IO.get(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_STRAGESUPPORT]));
				//#CM698344
				// Set a file name.
				param.setFileName_LoadStorageData(
					IO.get(DATALOAD_FILENAME, LOADTYPE_KEY[LOADTYPE_STRAGESUPPORT]));
			}
			else
			{
				//#CM698345
				// Set the checkbox OFF (FALSE).
				param.setSelectLoadStorageData(false);
			}

			//#CM698346
			// Picking plan data
			//#CM698347
			// Compile only when Package is "Add-ON".
			if (isRetrievalPack(conn))
			{
				//#CM698348
				// Set the checkbox ON (TRUE).
				param.setSelectLoadRetrievalData(true);
				//#CM698349
				// Set the folder for storing the data.
				param.setFolder_LoadRetrievalData(
					IO.get(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_RETRIEVALSUPPORT]));
				//#CM698350
				// Set a file name.
				param.setFileName_LoadRetrievalData(
					IO.get(DATALOAD_FILENAME, LOADTYPE_KEY[LOADTYPE_RETRIEVALSUPPORT]));
			}
			else
			{
				//#CM698351
				// Set the checkbox OFF (FALSE).
				param.setSelectLoadRetrievalData(false);
			}

			//#CM698352
			// Sorting Plan data
			//#CM698353
			// Compile only when Package is "Add-ON".
			if (isSortingPack(conn))
			{
				//#CM698354
				// Set the checkbox ON (TRUE).
				param.setSelectLoadSortingData(true);
				//#CM698355
				// Set the folder for storing the data.
				param.setFolder_LoadSortingData(
					IO.get(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_PICKINGSUPPORT]));
				//#CM698356
				// Set a file name.
				param.setFileName_LoadSortingData(
					IO.get(DATALOAD_FILENAME, LOADTYPE_KEY[LOADTYPE_PICKINGSUPPORT]));
			}
			else
			{
				//#CM698357
				// Set the checkbox OFF (FALSE).
				param.setSelectLoadSortingData(false);
			}

			//#CM698358
			// Shipping Inspection Plan data
			//#CM698359
			// Compile only when Package is "Add-ON".
			if (isShippingPack(conn))
			{
				//#CM698360
				// Set the checkbox ON (TRUE).
				param.setSelectLoadShippingData(true);
				//#CM698361
				// Set the folder for storing the data.
				param.setFolder_LoadShippingData(
					IO.get(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_SHIPPINGINSPECTION]));
				//#CM698362
				// Set a file name.
				param.setFileName_LoadShippingData(
					IO.get(DATALOAD_FILENAME, LOADTYPE_KEY[LOADTYPE_SHIPPINGINSPECTION]));
			}
			else
			{
				//#CM698363
				// Set the checkbox OFF (FALSE).
				param.setSelectLoadShippingData(false);
			}

		}
		catch (ScheduleException es)
		{
			throw new ScheduleException(es.getMessage());
		}
		return param;
	}

	//#CM698364
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

		//#CM698365
		// Translate the data type of checkParam.
		SystemParameter param = (SystemParameter) checkParam;

		//#CM698366
		// Check the Worker Code, password, and Access Privileges.
		if (!checkWorker(conn, param, true))
		{
			return false;
		}
		
		String Instock = DisplayText.getText("RDB-W0032");
		String Storage = DisplayText.getText("RDB-W0033");
		String Retrieval = DisplayText.getText("RDB-W0034");
		String Sorting = DisplayText.getText("RDB-W0035");
		String Shipping = DisplayText.getText("RDB-W0036");
	    
		//#CM698367
		// Check the number of characters used in the folder and the path.
		if(param.getSelectLoadInstockData())
		{
			if(param.getFolder_LoadInstockData().getBytes().length + 
			 param.getFileName_LoadInstockData().getBytes().length > SystemParameter.FILE_PATHS_MAXLENGTH)
			{
				//#CM698368
				// 6023509=The folder and file name length of {0} can't exceed {1}.
				wMessage = "6023509" + wDelim + Instock+ wDelim + SystemParameter.FILE_PATHS_MAXLENGTH;
				return false;
			}
		}
		
		if(param.getSelectLoadStorageData())
		{
			if(param.getFolder_LoadStorageData().getBytes().length + 
			 param.getFileName_LoadStorageData().getBytes().length > SystemParameter.FILE_PATHS_MAXLENGTH)
			{
				//#CM698369
				// 6023509=The folder and file name length of {0} can't exceed {1}.
				wMessage = "6023509" + wDelim + Storage+ wDelim + SystemParameter.FILE_PATHS_MAXLENGTH;
				return false;
			}
		}
		
		if(param.getSelectLoadRetrievalData())
		{
			if(param.getFolder_LoadRetrievalData().getBytes().length + 
			 param.getFileName_LoadRetrievalData().getBytes().length > SystemParameter.FILE_PATHS_MAXLENGTH)
			{
				//#CM698370
				// 6023509=The folder and file name length of {0} can't exceed {1}.
				wMessage = "6023509" + wDelim + Retrieval+ wDelim + SystemParameter.FILE_PATHS_MAXLENGTH;
				return false;
			}
		}
		
		if(param.getSelectLoadSortingData())
		{
			if(param.getFolder_LoadSortingData().getBytes().length + 
			 param.getFileName_LoadSortingData().getBytes().length > SystemParameter.FILE_PATHS_MAXLENGTH)
			{
				//#CM698371
				// 6023509=The folder and file name length of {0} can't exceed {1}.
				wMessage = "6023509" + wDelim + Sorting+ wDelim + SystemParameter.FILE_PATHS_MAXLENGTH;
				return false;
			}
		}
		
		if(param.getSelectLoadShippingData())
		{
			if(param.getFolder_LoadShippingData().getBytes().length + 
			 param.getFileName_LoadShippingData().getBytes().length > SystemParameter.FILE_PATHS_MAXLENGTH)
			{
				//#CM698372
				// 6023509=The folder and file name length of {0} can't exceed {1}.
				wMessage = "6023509" + wDelim + Shipping+ wDelim + SystemParameter.FILE_PATHS_MAXLENGTH;
				return false;
			}
		}
		
		SystemParameter sysparam = (SystemParameter) getDirectory(conn, null);			
				
		if(param.getSelectLoadInstockData() && 
		        sysparam.getFolder_ReportInstockData().toUpperCase().equals(param.getFolder_LoadInstockData().toUpperCase()) &&
		        sysparam.getFileName_ReportInstockData().toUpperCase().equals(param.getFileName_LoadInstockData().toUpperCase())){
			    wMessage = "6023496" + wDelim + Instock;
			    return false;
		}
			
		if(param.getSelectLoadStorageData() &&
		        sysparam.getFolder_ReportStorageData().toUpperCase().equals(param.getFolder_LoadStorageData().toUpperCase()) &&
		        sysparam.getFileName_ReportStorageData().toUpperCase().equals(param.getFileName_LoadStorageData().toUpperCase())){
		    wMessage = "6023496" + wDelim + Storage;
			return false;
		}
		
		if(param.getSelectLoadRetrievalData() &&
		        sysparam.getFolder_ReportRetrievalData().toUpperCase().equals(param.getFolder_LoadRetrievalData().toUpperCase()) &&
		        sysparam.getFileName_ReportRetrievalData().toUpperCase().equals(param.getFileName_LoadRetrievalData().toUpperCase())){
		    wMessage = "6023496" + wDelim + Retrieval;
			return false;
		}
			
		if(param.getSelectLoadSortingData() &&
		        sysparam.getFolder_ReportSortingData().toUpperCase().equals(param.getFolder_LoadSortingData().toUpperCase()) &&
		        sysparam.getFileName_ReportSortingData().toUpperCase().equals(param.getFileName_LoadSortingData().toUpperCase())){
		    wMessage = "6023496" + wDelim + Sorting;
			return false;
		}
			
		if(param.getSelectLoadShippingData() && 
		        sysparam.getFolder_ReportShippingData().toUpperCase().equals(param.getFolder_LoadShippingData().toUpperCase()) &&
		        sysparam.getFileName_ReportShippingData().toUpperCase().equals(param.getFileName_LoadShippingData().toUpperCase())){
		    wMessage = "6023496" + wDelim + Shipping;
			return false;
		}

		return true;
	}

	//#CM698373
	/**
	 * Start the schedule. According to the contents set in the parameter array designated in the <CODE>startParams</CODE>,<BR>
	 * execute the process for the schedule. Implement a scheduling process depending on the class implementing this interface.<BR>
	 * Return true when the schedule process completed successfully.<BR>
	 * Return false if failed to schedule due to condition error or other causes.<BR>
	 * In this case, use the <CODE>getMessage()</CODE> method to obtain the contents.
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * Invoking this process in unavailable case throws <CODE>ScheduleException</CODE>. <BR>
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

		try
		{
			//#CM698374
			// Translate the type of startParams.
			SystemParameter param = (SystemParameter) startParams[0];

			//#CM698375
			// execute the check process.			
			if (!check(conn, param))
			{
				return false;
			}

			//#CM698376
			// Check for presence of data storage folder that is designated for the parameter.
			//#CM698377
			// Receiving Plan
			if (param.getSelectLoadInstockData() == true)
			{
				if (!chkDir(param.getFolder_LoadInstockData()))
				{
					wMessage = "6023030";
					return false;
				}
			}
			//#CM698378
			// Storage Plan
			if (param.getSelectLoadStorageData() == true)
			{
				if (!chkDir(param.getFolder_LoadStorageData()))
				{
					wMessage = "6023030";
					return false;
				}
			}
			//#CM698379
			// Picking Plan
			if (param.getSelectLoadRetrievalData() == true)
			{
				if (!chkDir(param.getFolder_LoadRetrievalData()))
				{
					wMessage = "6023030";
					return false;
				}
			}
			//#CM698380
			// Sorting Plan
			if (param.getSelectLoadSortingData() == true)
			{
				if (!chkDir(param.getFolder_LoadSortingData()))
				{
					wMessage = "6023030";
					return false;
				}
			}
			//#CM698381
			// Shipping Inspection Plan
			if (param.getSelectLoadShippingData() == true)
			{
				if (!chkDir(param.getFolder_LoadShippingData()))
				{
					wMessage = "6023030";
					return false;
				}
			}

			//#CM698382
			// Write the Environment setting file (EnvironmentInformation).
			//#CM698383
			// Check whether the data storage folder is suffixed with "\". If not suffixed, suffix it.
			String wSetFolder = "";
			String wFolderSing = "\\";

			//#CM698384
			// Obtain the environment info descriptor.
			IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);

			//#CM698385
			// Receiving Plan
			if (param.getSelectLoadInstockData() == true)
			{
				wSetFolder = param.getFolder_LoadInstockData();
				if (!param
					.getFolder_LoadInstockData()
					.substring(param.getFolder_LoadInstockData().length() - 1)
					.equals(wFolderSing))
				{
					wSetFolder += wFolderSing;
				}
				//#CM698386
				// Update the Data Storage Folder.
				IO.set(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_INSTOCKRECEIVE], wSetFolder);
				//#CM698387
				// Update the file name.
				IO.set(
					DATALOAD_FILENAME,
					LOADTYPE_KEY[LOADTYPE_INSTOCKRECEIVE],
					param.getFileName_LoadInstockData());
			}

			//#CM698388
			// Storage Plan
			if (param.getSelectLoadStorageData() == true)
			{
				wSetFolder = param.getFolder_LoadStorageData();
				if (!param
					.getFolder_LoadStorageData()
					.substring(param.getFolder_LoadStorageData().length() - 1)
					.equals(wFolderSing))
				{
					wSetFolder += wFolderSing;
				}
				//#CM698389
				// Update the Data Storage Folder.
				IO.set(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_STRAGESUPPORT], wSetFolder);
				//#CM698390
				// Update the file name.
				IO.set(
					DATALOAD_FILENAME,
					LOADTYPE_KEY[LOADTYPE_STRAGESUPPORT],
					param.getFileName_LoadStorageData());
			}

			//#CM698391
			// Picking Plan
			if (param.getSelectLoadRetrievalData() == true)
			{
				wSetFolder = param.getFolder_LoadRetrievalData();
				if (!param
					.getFolder_LoadRetrievalData()
					.substring(param.getFolder_LoadRetrievalData().length() - 1)
					.equals(wFolderSing))
				{
					wSetFolder += wFolderSing;
				}
				//#CM698392
				// Update the Data Storage Folder.
				IO.set(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_RETRIEVALSUPPORT], wSetFolder);
				//#CM698393
				// Update the file name.
				IO.set(
					DATALOAD_FILENAME,
					LOADTYPE_KEY[LOADTYPE_RETRIEVALSUPPORT],
					param.getFileName_LoadRetrievalData());
			}

			//#CM698394
			// Sorting Plan
			if (param.getSelectLoadSortingData() == true)
			{
				wSetFolder = param.getFolder_LoadSortingData();
				if (!param
					.getFolder_LoadSortingData()
					.substring(param.getFolder_LoadSortingData().length() - 1)
					.equals(wFolderSing))
				{
					wSetFolder += wFolderSing;
				}
				//#CM698395
				// Update the Data Storage Folder.
				IO.set(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_PICKINGSUPPORT], wSetFolder);
				//#CM698396
				// Update the file name.
				IO.set(
					DATALOAD_FILENAME,
					LOADTYPE_KEY[LOADTYPE_PICKINGSUPPORT],
					param.getFileName_LoadSortingData());
			}

			//#CM698397
			// Shipping Inspection Plan
			if (param.getSelectLoadShippingData() == true)
			{
				wSetFolder = param.getFolder_LoadShippingData();
				if (!param
					.getFolder_LoadShippingData()
					.substring(param.getFolder_LoadShippingData().length() - 1)
					.equals(wFolderSing))
				{
					wSetFolder += wFolderSing;
				}
				//#CM698398
				// Update the Data Storage Folder.
				IO.set(DATALOAD_FOLDER, LOADTYPE_KEY[LOADTYPE_SHIPPINGINSPECTION], wSetFolder);
				//#CM698399
				// Update the file name.
				IO.set(
					DATALOAD_FILENAME,
					LOADTYPE_KEY[LOADTYPE_SHIPPINGINSPECTION],
					param.getFileName_LoadShippingData());
			}

			//#CM698400
			// Write data into the environment info.
			IO.flush();

			//#CM698401
			// Set the message: "Updated".			
			wMessage = "6001018";
			//#CM698402
			// Announce when that the process completed normally.
			resultFlag = true;
			return resultFlag;
		}
		catch (IOException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		finally
		{

		}
	}

	//#CM698403
	// Package methods -----------------------------------------------

	//#CM698404
	// Protected methods ---------------------------------------------

	//#CM698405
	// Private methods -----------------------------------------------
	//#CM698406
	/**
	 * Examine whether the designated folder is Enabled or not.
	 *
	 * @param cheDirName	Process identifier
	 * @return boolean		True if Enabled. true
	 * @throws IOException Announce it when I/O error occurs.
	 */
	private boolean chkDir(String cheDirName) throws java.io.IOException
	{
		//#CM698407
		// Check the file.
		File objFile = new File(cheDirName);
		return (objFile.exists() && objFile.isDirectory());
	}
	
	//#CM698408
	/**
	 * Allow this method to support the operation to obtain data from a folder for reporting data.<BR>
	 * Search conditions passes a class that inherits the<CODE>Parameter</CODE> class.<BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Database connection object
	 * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions.
	 * @return A class that implements the <CODE>Parameter</CODE> interface that contains the search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter getDirectory(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{

		SystemParameter param = new SystemParameter();

		//#CM698409
		// Obtain the data storage folder and file name from the environmetnt setting file.
		IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);

		//#CM698410
		// Receiving Result
		if (isInstockPack(conn))
		{
			param.setFolder_ReportInstockData(
				IO.get(REPORTDATA_FOLDER, LOADTYPE_KEY[LOADTYPE_INSTOCKRECEIVE]));
			param.setFileName_ReportInstockData(
				IO.get(REPORTDATA_FILENAME, LOADTYPE_KEY[LOADTYPE_INSTOCKRECEIVE]));
			param.setSelectReportInstockData(true);
		}
		else
		{
			param.setSelectReportInstockData(false);
		}

		//#CM698411
		// Storage Result
		if (isStoragePack(conn))
		{
			param.setFolder_ReportStorageData(
				IO.get(REPORTDATA_FOLDER, LOADTYPE_KEY[LOADTYPE_STRAGESUPPORT]));
			param.setFileName_ReportStorageData(
				IO.get(REPORTDATA_FILENAME, LOADTYPE_KEY[LOADTYPE_STRAGESUPPORT]));
			param.setSelectReportStorageData(true);
		}
		else
		{
			param.setSelectReportStorageData(false);
		}

		//#CM698412
		// Picking Result
		if (isRetrievalPack(conn))
		{
			param.setFolder_ReportRetrievalData(
				IO.get(REPORTDATA_FOLDER, LOADTYPE_KEY[LOADTYPE_RETRIEVALSUPPORT]));
			param.setFileName_ReportRetrievalData(
				IO.get(REPORTDATA_FILENAME, LOADTYPE_KEY[LOADTYPE_RETRIEVALSUPPORT]));
			param.setSelectReportRetrievalData(true);
		}
		else
		{
			param.setSelectReportRetrievalData(false);
		}

		//#CM698413
		// Sorting Result
		if (isSortingPack(conn))
		{
			param.setFolder_ReportSortingData(
				IO.get(REPORTDATA_FOLDER, LOADTYPE_KEY[LOADTYPE_PICKINGSUPPORT]));
			param.setFileName_ReportSortingData(
				IO.get(REPORTDATA_FILENAME, LOADTYPE_KEY[LOADTYPE_PICKINGSUPPORT]));
			param.setSelectReportSortingData(true);
		}
		else
		{
			param.setSelectReportSortingData(false);
		}

		//#CM698414
		// Shipping Result
		if (isShippingPack(conn))
		{
			param.setFolder_ReportShippingData(
				IO.get(REPORTDATA_FOLDER, LOADTYPE_KEY[LOADTYPE_SHIPPINGINSPECTION]));
			param.setFileName_ReportShippingData(
				IO.get(REPORTDATA_FILENAME, LOADTYPE_KEY[LOADTYPE_SHIPPINGINSPECTION]));
			param.setSelectReportShippingData(true);
		}
		else
		{
			param.setSelectReportShippingData(false);
		}
		return param;
	}

}
//#CM698415
//end of class
