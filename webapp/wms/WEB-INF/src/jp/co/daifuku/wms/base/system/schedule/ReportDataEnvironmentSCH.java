//#CM698610
//$Id: ReportDataEnvironmentSCH.java,v 1.2 2006/11/13 06:03:06 suresh Exp $

//#CM698611
/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
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
import jp.co.daifuku.wms.base.entity.WareNaviSystem;

//#CM698612
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * Allow the <CODE>ReportDataEnvironmentSCH</CODE> class to execute the process to set the environment for reporting data. <BR>
 * Set the result file storage folder and the result file name by introduced package. <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1.Process for setting the environment.(<CODE>startSCH(Connection conn, Parameter[] startParams)</CODE>method) <BR>
 * <BR>
 * <DIR>
 *   [Parameter] <BR>
 *   <DIR>
 *     Receiving result data storage folder <BR>
 *     Receiving result data file name <BR>
 *     Receiving result data checkbox (true or false) <BR>
 *     Storage result data storage folder <BR>
 *     Storage result data file name <BR>
 *     Storage result data checkbox (true or false) <BR>
 *     Picking result data storage folder <BR>
 *     Picking result data file name <BR>
 *     Picking result data checkbox (true or false) <BR>
 *     Sorting result data storage folder <BR>
 *     Sorting result data file name <BR>
 *     Sorting result data checkbox (true or false) <BR>
 *     Shipping result data storage folder <BR>
 *     Shipping result data file name <BR>
 *     Shipping result data checkbox (true or false) <BR>
 *     Stock relocation result data storage folder <BR>
 *     Stock relocation result data file name <BR>
 *     Stock relocation result data checkbox (true or false) <BR>
 *     Inventory Check result data storage folder <BR>
 *     Inventory Check result data file name <BR>
 *     Inventory Check result data checkbox (true or false) <BR>
 *     Unplanned storage data storage folder <BR>
 *     Unplanned storage data file name <BR>
 *     Unplanned storage data checkbox (true or false) <BR>
 *     Unplanned picking data storage folder <BR>
 *     Unplanned picking data file name <BR>
 *     Unplanned picking data checkbox (true or false) <BR>
 *   </DIR>
 *   <BR>
 *   -Only when the checkbox of the parameter is determined as true, execute the following processes. <BR>
 *   <DIR>
 *     Check for presence of the entered folder. <BR>
 *     Add "\" if it is not suffixed to the entered folder. <BR>
 *     Add the storage folder and the file name set for the Environment setting file (EnvironmentInformation). <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * 2.Initial Display Process(<CODE>initFind(Connection conn, Parameter searchParam)</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Execute the process for initial display of buttons in the Load Data screen (setting of environment). <BR>
 *   <BR>
 *   [Returned data] <BR>
 *   <DIR>
 *     Receiving result data storage folder <BR>
 *     Receiving result data file name <BR>
 *     Receiving result data checkbox (true or false) <BR>
 *     Storage result data storage folder <BR>
 *     Storage result data file name <BR>
 *     Storage result data checkbox (true or false) <BR>
 *     Picking result data storage folder <BR>
 *     Picking result data file name <BR>
 *     Picking result data checkbox (true or false) <BR>
 *     Sorting result data storage folder <BR>
 *     Sorting result data file name <BR>
 *     Sorting result data checkbox (true or false) <BR>
 *     Shipping result data storage folder <BR>
 *     Shipping result data file name <BR>
 *     Shipping result data checkbox (true or false) <BR>
 *     Stock relocation result data storage folder <BR>
 *     Stock relocation result data file name <BR>
 *     Stock relocation result data checkbox (true or false) <BR>
 *     Inventory Check result data storage folder <BR>
 *     Inventory Check result data file name <BR>
 *     Inventory Check result data checkbox (true or false) <BR>
 *     Unplanned storage result data storage folder <BR>
 *     Unplanned storage result data file name <BR>
 *     Unplanned storage result data checkbox (true or false) <BR>
 *     Unplanned picking result data storage folder <BR>
 *     Unplanned picking result data file name <BR>
 *     Unplanned picking result data checkbox (true or false) <BR>
 *   </DIR>
 *   <BR>
 *   Referring to the System definition table(WARENAVI_SYSTEM), <BR>
 *   set true for a checkbox of the result data with package flag "1: Available", or<BR>
 *   set false for a checkbox of the result data with package flag "0: Not Available". <BR>
 *   Disable to return folder name and file name. <BR>
 *   Refer to the Inventory package for details of Stock relocation result data and Inventory Check result data. <BR>
 *   Obtain the data to be set from the Environment setting file (EnvironmentInformation). <BR>
 *   <BR>
 *   <BR>
 * </DIR>
 * 3.Input check process(<CODE>check(Connection conn, Parameter checkParam)</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Check the Worker Code, password, and privilege. <BR>
 *   1.Process for setting environment invokes this. <BR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/25</TD><TD>K.Toda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:06 $
 * @author  $Author: suresh $
 */
public class ReportDataEnvironmentSCH extends AbstractSystemSCH
{

	//#CM698613
	// Class fields --------------------------------------------------

	//#CM698614
	// Class variables -----------------------------------------------

	//#CM698615
	/** Data type: Receiving **/

	private static final int REPORTTYPE_INSTOCKRECEIVE = 0;

	//#CM698616
	/** Data type: Storage **/

	private static final int REPORTTYPE_STRAGESUPPORT = 1;

	//#CM698617
	/** Data type: Picking **/

	private static final int REPORTTYPE_RETRIEVALSUPPORT = 2;

	//#CM698618
	/** Data type: Sorting **/

	private static final int REPORTTYPE_PICKINGSUPPORT = 3;

	//#CM698619
	/** Data type: Shipping **/

	private static final int REPORTTYPE_SHIPPINGINSPECTION = 4;

	//#CM698620
	/** Data type: Stock relocation **/

	private static final int REPORTTYPE_STOCKMOVING = 5;

	//#CM698621
	/** Data type: Inventory Check **/

	private static final int REPORTTYPE_STOCKTAKINGSUPPORT = 6;

	//#CM698622
	/** Data type: Unplanned Storage **/

	private static final int REPORTTYPE_NOPLANSTRAGESUPPORT = 7;

	//#CM698623
	/** Data type: Unplanned Picking **/

	private static final int REPORTTYPE_NOPLANRETRIEVALSUPPORT = 8;

	//#CM698624
	/** Key for setting data environment **/

	private static final String[] LOADTYPE_KEY =
		{
			"INSTOCK_RECEIVE",
			"STRAGE_SUPPORT",
			"RETRIEVAL_SUPPORT",
			"PICKING_SUPPORT",
			"SHIPPING_INSPECTION",
			"MOVING_SUPPORT",
			"STOCKTAKING_SUPPORT",
			"NOPLANSTRAGE_SUPPORT",
			"NOPLANRETRIEVAL_SUPPORT" };

	//#CM698625
	/** Section name of Report Data folder **/

	private static final String REPORTDATA_FOLDER = "REPORTDATA_FOLDER";

	//#CM698626
	/** Section name of Report Data file name **/

	private static final String REPORTDATA_FILENAME = "REPORTDATA_FILENAME";

	//#CM698627
	/**
	 * Define the section name of the folder of data to be loaded. <BR>
	 */
	private static final String DATALOAD_FOLDER = "DATALOAD_FOLDER";

	//#CM698628
	/**
	 * Define the section name of the file name of data to be loaded. <BR>
	 */
	private static final String DATALOAD_FILENAME = "DATALOAD_FILENAME";

	//#CM698629
	// Class method --------------------------------------------------

	//#CM698630
	// Constructors --------------------------------------------------
	//#CM698631
	/**
	 * Constructor
	 */
	public ReportDataEnvironmentSCH()
	{
	}

	//#CM698632
	// Public methods ------------------------------------------------
	//#CM698633
	/**
	 * This method obtains the data required for initial display.<BR>
	 * 
	 * @param conn        Database connection object
	 * @param searchParam Set null as this method does not use it.
	 * @return A class that implements the <CODE>Parameter</CODE> interface that contains the result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		WareNaviSystem wareNaviSystem = new WareNaviSystem();

		SystemParameter param = new SystemParameter();

		if (wareNaviSystem == null)
		{
			return null;
		}

		//#CM698634
		// Obtain the data storage folder and file name from the environment setting file.
		IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);

		//#CM698635
		// Receiving Result
		if (isInstockPack(conn))
		{
			param.setFolder_ReportInstockData(
				IO.get(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_INSTOCKRECEIVE]));
			param.setFileName_ReportInstockData(
				IO.get(REPORTDATA_FILENAME, LOADTYPE_KEY[REPORTTYPE_INSTOCKRECEIVE]));
			param.setSelectReportInstockData(true);
		}
		else
		{
			param.setSelectReportInstockData(false);
		}

		//#CM698636
		// Storage Result
		if (isStoragePack(conn))
		{
			param.setFolder_ReportStorageData(
				IO.get(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_STRAGESUPPORT]));
			param.setFileName_ReportStorageData(
				IO.get(REPORTDATA_FILENAME, LOADTYPE_KEY[REPORTTYPE_STRAGESUPPORT]));
			param.setSelectReportStorageData(true);
		}
		else
		{
			param.setSelectReportStorageData(false);
		}

		//#CM698637
		// Picking Result
		if (isRetrievalPack(conn))
		{
			param.setFolder_ReportRetrievalData(
				IO.get(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_RETRIEVALSUPPORT]));
			param.setFileName_ReportRetrievalData(
				IO.get(REPORTDATA_FILENAME, LOADTYPE_KEY[REPORTTYPE_RETRIEVALSUPPORT]));
			param.setSelectReportRetrievalData(true);
		}
		else
		{
			param.setSelectReportRetrievalData(false);
		}

		//#CM698638
		// Sorting Result
		if (isSortingPack(conn))
		{
			param.setFolder_ReportSortingData(
				IO.get(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_PICKINGSUPPORT]));
			param.setFileName_ReportSortingData(
				IO.get(REPORTDATA_FILENAME, LOADTYPE_KEY[REPORTTYPE_PICKINGSUPPORT]));
			param.setSelectReportSortingData(true);
		}
		else
		{
			param.setSelectReportSortingData(false);
		}

		//#CM698639
		// Shipping Result
		if (isShippingPack(conn))
		{
			param.setFolder_ReportShippingData(
				IO.get(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_SHIPPINGINSPECTION]));
			param.setFileName_ReportShippingData(
				IO.get(REPORTDATA_FILENAME, LOADTYPE_KEY[REPORTTYPE_SHIPPINGINSPECTION]));
			param.setSelectReportShippingData(true);
		}
		else
		{
			param.setSelectReportShippingData(false);
		}

		//#CM698640
		// Relocation Result
		if (isStoragePack(conn))
		{
			param.setFolder_ReportMovementData(
				IO.get(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_STOCKMOVING]));
			param.setFileName_ReportMovementData(
				IO.get(REPORTDATA_FILENAME, LOADTYPE_KEY[REPORTTYPE_STOCKMOVING]));
			param.setSelectReportMovementData(true);
		}
		else
		{
			param.setSelectReportMovementData(false);
		}

		//#CM698641
		// Inventory Check Result
		if (isStoragePack(conn))
		{
			param.setFolder_ReportInventoryData(
				IO.get(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_STOCKTAKINGSUPPORT]));
			param.setFileName_ReportInventoryData(
				IO.get(REPORTDATA_FILENAME, LOADTYPE_KEY[REPORTTYPE_STOCKTAKINGSUPPORT]));
			param.setSelectReportInventoryData(true);
		}
		else
		{
			param.setSelectReportInventoryData(false);
		}
		//#CM698642
		// Unplanned Storage Result
		if (isStockPack(conn))
		{
			param.setFolder_ReportNoPlanStorageData(
				IO.get(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_NOPLANSTRAGESUPPORT]));
			param.setFileName_ReportNoPlanStorageData(
				IO.get(REPORTDATA_FILENAME, LOADTYPE_KEY[REPORTTYPE_NOPLANSTRAGESUPPORT]));
			param.setSelectReportNoPlanStorageData(true);
		}
		else
		{
			param.setSelectReportNoPlanStorageData(false);
		}

		//#CM698643
		// Unplanned Picking Result
		if (isStockPack(conn))
		{
			param.setFolder_ReportNoPlanRetrievalData(
				IO.get(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_NOPLANRETRIEVALSUPPORT]));
			param.setFileName_ReportNoPlanRetrievalData(
				IO.get(REPORTDATA_FILENAME, LOADTYPE_KEY[REPORTTYPE_NOPLANRETRIEVALSUPPORT]));
			param.setSelectReportNoPlanRetrievalData(true);
		}
		else
		{
			param.setSelectReportNoPlanRetrievalData(false);
		}

		return param;
	}

	//#CM698644
	/**
	 * Check that the contents of Worker Code and password are correct.<BR>
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

		//#CM698645
		// Translate the data type of checkParam.
		SystemParameter param = (SystemParameter) checkParam;

		//#CM698646
		// Check the Worker Code, password, and Access Privileges.
		if (!checkWorker(conn, param, true))
		{
			return false;
		}

		return true;
	}

	//#CM698647
	/**
	 * Start the schedule.Check for input and write it to the EnvironmentInformation.
	 * Return true if proper writing succeeded. Or set a message and return false if failed.
	 * @param conn        Database connection object
	 * @param startParams A parameter class that contains the contents entered via screen.
	 * @return Return true if a process for writing data completed normally. Return false if failed.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		boolean resultFlag = false;

		try
		{
			String Instock = DisplayText.getText("RDB-W0037");
			String Storage = DisplayText.getText("RDB-W0038");
			String Retrieval = DisplayText.getText("RDB-W0039");
			String Sorting = DisplayText.getText("RDB-W0040");
			String Shipping = DisplayText.getText("RDB-W0041");
			String Movement = DisplayText.getText("RDB-W0042");
			String Inventory = DisplayText.getText("RDB-W0043");
			String NoPlanStorage = DisplayText.getText("RDB-W0071");
			String NoPlanRetrieval = DisplayText.getText("RDB-W0072");
		    
			//#CM698648
			// Translate the type of startParams.
			SystemParameter[] param = (SystemParameter[]) startParams;

			//#CM698649
			// execute the check process.			
			if (!check(conn, param[0]))
			{
				return false;
			}
			
			//#CM698650
			// Check the number of characters used in the folder and the path.
			if(param[0].getSelectReportInstockData())
			{
				if(param[0].getFolder_ReportInstockData().getBytes().length + 
				 param[0].getFileName_ReportInstockData().getBytes().length > SystemParameter.FILE_PATHS_MAXLENGTH)
				{
					//#CM698651
					// 6023509=The folder and file name length of {0} can't exceed {1}.
					wMessage = "6023509" + wDelim + Instock + wDelim + SystemParameter.FILE_PATHS_MAXLENGTH;
					return false;
				}
			}
			
			if(param[0].getSelectReportStorageData())
			{
				if(param[0].getFolder_ReportStorageData().getBytes().length + 
				 param[0].getFileName_ReportStorageData().getBytes().length > SystemParameter.FILE_PATHS_MAXLENGTH)
				{
					//#CM698652
					// 6023509=The folder and file name length of {0} can't exceed {1}.
					wMessage = "6023509" + wDelim + Storage + wDelim + SystemParameter.FILE_PATHS_MAXLENGTH;
					return false;
				}
			}
			
			if(param[0].getSelectReportRetrievalData())
			{
				if(param[0].getFolder_ReportRetrievalData().getBytes().length + 
				 param[0].getFileName_ReportRetrievalData().getBytes().length > SystemParameter.FILE_PATHS_MAXLENGTH)
				{
					//#CM698653
					// 6023509=The folder and file name length of {0} can't exceed {1}.
					wMessage = "6023509" + wDelim + Retrieval + wDelim + SystemParameter.FILE_PATHS_MAXLENGTH;
					return false;
				}
			}
			
			if(param[0].getSelectReportSortingData())
			{
				if(param[0].getFolder_ReportSortingData().getBytes().length + 
				 param[0].getFileName_ReportSortingData().getBytes().length > SystemParameter.FILE_PATHS_MAXLENGTH)
				{
					//#CM698654
					// 6023509=The folder and file name length of {0} can't exceed {1}.
					wMessage = "6023509" + wDelim + Sorting + wDelim + SystemParameter.FILE_PATHS_MAXLENGTH;
					return false;
				}
			}
			
			if(param[0].getSelectReportShippingData())
			{
				if(param[0].getFolder_ReportShippingData().getBytes().length + 
				 param[0].getFileName_ReportShippingData().getBytes().length > SystemParameter.FILE_PATHS_MAXLENGTH)
				{
					//#CM698655
					// 6023509=The folder and file name length of {0} can't exceed {1}.
					wMessage = "6023509" + wDelim + Shipping + wDelim + SystemParameter.FILE_PATHS_MAXLENGTH;
					return false;
				}
			}
			
			if(param[0].getSelectReportMovementData())
			{
				if(param[0].getFolder_ReportMovementData().getBytes().length + 
				 param[0].getFileName_ReportMovementData().getBytes().length > SystemParameter.FILE_PATHS_MAXLENGTH)
				{
					//#CM698656
					// 6023509=The folder and file name length of {0} can't exceed {1}.
					wMessage = "6023509" + wDelim + Movement + wDelim + SystemParameter.FILE_PATHS_MAXLENGTH;
					return false;
				}
			}
			
			if(param[0].getSelectReportInventoryData())
			{
				if(param[0].getFolder_ReportInventoryData().getBytes().length + 
				 param[0].getFileName_ReportInventoryData().getBytes().length > SystemParameter.FILE_PATHS_MAXLENGTH)
				{
					//#CM698657
					// 6023509=The folder and file name length of {0} can't exceed {1}.
					wMessage = "6023509" + wDelim + Inventory + wDelim + SystemParameter.FILE_PATHS_MAXLENGTH;
					return false;
				}
			}
			
			if(param[0].getSelectReportNoPlanStorageData())
			{
				if(param[0].getFolder_ReportNoPlanStorageData().getBytes().length + 
				 param[0].getFileName_ReportNoPlanStorageData().getBytes().length > SystemParameter.FILE_PATHS_MAXLENGTH)
				{
					//#CM698658
					// 6023509=The folder and file name length of {0} can't exceed {1}.
					wMessage = "6023509" + wDelim + NoPlanStorage + wDelim + SystemParameter.FILE_PATHS_MAXLENGTH;
					return false;
				}
			}
			
			if(param[0].getSelectReportNoPlanRetrievalData())
			{
				if(param[0].getFolder_ReportNoPlanRetrievalData().getBytes().length + 
				 param[0].getFileName_ReportNoPlanRetrievalData().getBytes().length > SystemParameter.FILE_PATHS_MAXLENGTH)
				{
					//#CM698659
					// 6023509=The folder and file name length of {0} can't exceed {1}.
					wMessage = "6023509" + wDelim + NoPlanRetrieval + wDelim + SystemParameter.FILE_PATHS_MAXLENGTH;
					return false;
				}
			}
			
			SystemParameter sysparam = (SystemParameter) getDirectory(conn, null);			

			//#CM698660
			// Check whether a single file path is provided for both loading and reporting.
			if(param[0].getSelectReportInstockData() && 
			    param[0].getFolder_ReportInstockData().toUpperCase().equals(sysparam.getFolder_LoadInstockData().toUpperCase()) &&
			    param[0].getFileName_ReportInstockData().toUpperCase().equals(sysparam.getFileName_LoadInstockData().toUpperCase())){
			    wMessage = "6023495" + wDelim + Instock;
			    return false;
			}
			
			if(param[0].getSelectReportStorageData() &&
				param[0].getFolder_ReportStorageData().toUpperCase().equals(sysparam.getFolder_LoadStorageData().toUpperCase()) &&
			    param[0].getFileName_ReportStorageData().toUpperCase().equals(sysparam.getFileName_LoadStorageData().toUpperCase())){
			    wMessage = "6023495" + wDelim + Storage;
				return false;
			}
			
			if(param[0].getSelectReportRetrievalData() &&
			    param[0].getFolder_ReportRetrievalData().toUpperCase().equals(sysparam.getFolder_LoadRetrievalData().toUpperCase()) &&
			    param[0].getFileName_ReportRetrievalData().toUpperCase().equals(sysparam.getFileName_LoadRetrievalData().toUpperCase())){
			    wMessage = "6023495" + wDelim + Retrieval;
				return false;
			}
			
			if(param[0].getSelectReportSortingData() &&
				param[0].getFolder_ReportSortingData().toUpperCase().equals(sysparam.getFolder_LoadSortingData().toUpperCase()) &&
			    param[0].getFileName_ReportSortingData().toUpperCase().equals(sysparam.getFileName_LoadSortingData().toUpperCase())){
			    wMessage = "6023495" + wDelim + Sorting;
				return false;
			}
			
			if(param[0].getSelectReportShippingData() && 
			    param[0].getFolder_ReportShippingData().toUpperCase().equals(sysparam.getFolder_LoadShippingData().toUpperCase()) &&
			    param[0].getFileName_ReportShippingData().toUpperCase().equals(sysparam.getFileName_LoadShippingData().toUpperCase())){
			    wMessage = "6023495" + wDelim + Shipping;
				return false;
			}

			//#CM698661
			// Check for presence of data storage folder that is designated for the parameter.
			if ((param[0].getSelectReportInstockData()
				&& !chkDir(param[0].getFolder_ReportInstockData()))
				|| (param[0].getSelectReportStorageData()
					&& !chkDir(param[0].getFolder_ReportStorageData()))
				|| (param[0].getSelectReportRetrievalData()
					&& !chkDir(param[0].getFolder_ReportRetrievalData()))
				|| (param[0].getSelectReportSortingData()
					&& !chkDir(param[0].getFolder_ReportSortingData()))
				|| (param[0].getSelectReportShippingData()
					&& !chkDir(param[0].getFolder_ReportShippingData()))
				|| (param[0].getSelectReportMovementData()
					&& !chkDir(param[0].getFolder_ReportMovementData()))
				|| (param[0].getSelectReportInventoryData()
					&& !chkDir(param[0].getFolder_ReportInventoryData()))
				|| (param[0].getSelectReportNoPlanStorageData()
					&& !chkDir(param[0].getFolder_ReportNoPlanStorageData()))
				|| (param[0].getSelectReportNoPlanRetrievalData()
					&& !chkDir(param[0].getFolder_ReportNoPlanRetrievalData())))
			{
				wMessage = "6023030";
				return false;
			}
			//#CM698662
			// Write the Environment setting file (EnvironmentInformation).
			IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);
			String wYenmark = "\\";
			String wFolder = null;

			//#CM698663
			// Execute the process only when the relevant package is true.
			//#CM698664
			// Receiving Result
			if (param[0].getSelectReportInstockData())
			{
				//#CM698665
				// Check whether the data storage folder is suffixed with "\". If not suffixed, suffix it and submit it.		
				int len = param[0].getFolder_ReportInstockData().length();
				if (!param[0]
					.getFolder_ReportInstockData()
					.substring(len - 1, len)
					.equals(wYenmark))
				{
					wFolder = param[0].getFolder_ReportInstockData() + "\\";
				}
				else
				{
					wFolder = param[0].getFolder_ReportInstockData();
				}
				IO.set(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_INSTOCKRECEIVE], wFolder);
				IO.set(
					REPORTDATA_FILENAME,
					LOADTYPE_KEY[REPORTTYPE_INSTOCKRECEIVE],
					param[0].getFileName_ReportInstockData());
			}

			//#CM698666
			// Storage Result
			if (param[0].getSelectReportStorageData())
			{
				//#CM698667
				// Check whether the data storage folder is suffixed with "\". If not suffixed, suffix it and submit it.		
				int len = param[0].getFolder_ReportStorageData().length();
				if (!param[0]
					.getFolder_ReportStorageData()
					.substring(len - 1, len)
					.equals(wYenmark))
				{
					wFolder = param[0].getFolder_ReportStorageData() + "\\";
				}
				else
				{
					wFolder = param[0].getFolder_ReportStorageData();
				}

				IO.set(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_STRAGESUPPORT], wFolder);
				IO.set(
					REPORTDATA_FILENAME,
					LOADTYPE_KEY[REPORTTYPE_STRAGESUPPORT],
					param[0].getFileName_ReportStorageData());
			}

			//#CM698668
			// Picking Result
			if (param[0].getSelectReportRetrievalData())
			{
				//#CM698669
				// Check whether the data storage folder is suffixed with "\". If not suffixed, suffix it and submit it.		
				int len = param[0].getFolder_ReportRetrievalData().length();
				if (!param[0]
					.getFolder_ReportRetrievalData()
					.substring(len - 1, len)
					.equals(wYenmark))
				{
					wFolder = param[0].getFolder_ReportRetrievalData() + "\\";
				}
				else
				{
					wFolder = param[0].getFolder_ReportRetrievalData();
				}

				IO.set(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_RETRIEVALSUPPORT], wFolder);
				IO.set(
					REPORTDATA_FILENAME,
					LOADTYPE_KEY[REPORTTYPE_RETRIEVALSUPPORT],
					param[0].getFileName_ReportRetrievalData());
			}

			//#CM698670
			// Sorting Result
			if (param[0].getSelectReportSortingData())
			{
				//#CM698671
				// Check whether the data storage folder is suffixed with "\". If not suffixed, suffix it and submit it.		
				int len = param[0].getFolder_ReportSortingData().length();
				if (!param[0]
					.getFolder_ReportSortingData()
					.substring(len - 1, len)
					.equals(wYenmark))
				{
					wFolder = param[0].getFolder_ReportSortingData() + "\\";
				}
				else
				{
					wFolder = param[0].getFolder_ReportSortingData();
				}

				IO.set(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_PICKINGSUPPORT], wFolder);
				IO.set(
					REPORTDATA_FILENAME,
					LOADTYPE_KEY[REPORTTYPE_PICKINGSUPPORT],
					param[0].getFileName_ReportSortingData());
			}

			//#CM698672
			// Shipping Result
			if (param[0].getSelectReportShippingData())
			{
				//#CM698673
				// Check whether the data storage folder is suffixed with "\". If not suffixed, suffix it and submit it.		
				int len = param[0].getFolder_ReportShippingData().length();
				if (!param[0]
					.getFolder_ReportShippingData()
					.substring(len - 1, len)
					.equals(wYenmark))
				{
					wFolder = param[0].getFolder_ReportShippingData() + "\\";
				}
				else
				{
					wFolder = param[0].getFolder_ReportShippingData();
				}

				IO.set(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_SHIPPINGINSPECTION], wFolder);
				IO.set(
					REPORTDATA_FILENAME,
					LOADTYPE_KEY[REPORTTYPE_SHIPPINGINSPECTION],
					param[0].getFileName_ReportShippingData());
			}

			//#CM698674
			// Relocation Result
			if (param[0].getSelectReportMovementData())
			{
				//#CM698675
				// Check whether the data storage folder is suffixed with "\". If not suffixed, suffix it and submit it.		
				int len = param[0].getFolder_ReportMovementData().length();
				if (!param[0]
					.getFolder_ReportMovementData()
					.substring(len - 1, len)
					.equals(wYenmark))
				{
					wFolder = param[0].getFolder_ReportMovementData() + "\\";
				}
				else
				{
					wFolder = param[0].getFolder_ReportMovementData();
				}

				IO.set(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_STOCKMOVING], wFolder);
				IO.set(
					REPORTDATA_FILENAME,
					LOADTYPE_KEY[REPORTTYPE_STOCKMOVING],
					param[0].getFileName_ReportMovementData());
			}

			//#CM698676
			// Inventory Check Result
			if (param[0].getSelectReportInventoryData())
			{
				//#CM698677
				// Check whether the data storage folder is suffixed with "\". If not suffixed, suffix it and submit it.		
				int len = param[0].getFolder_ReportInventoryData().length();
				if (!param[0]
					.getFolder_ReportInventoryData()
					.substring(len - 1, len)
					.equals(wYenmark))
				{
					wFolder = param[0].getFolder_ReportInventoryData() + "\\";
				}
				else
				{
					wFolder = param[0].getFolder_ReportInventoryData();
				}

				IO.set(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_STOCKTAKINGSUPPORT], wFolder);
				IO.set(
					REPORTDATA_FILENAME,
					LOADTYPE_KEY[REPORTTYPE_STOCKTAKINGSUPPORT],
					param[0].getFileName_ReportInventoryData());
			}

			//#CM698678
			// Unplanned Storage Result
			if (param[0].getSelectReportNoPlanStorageData())
			{
				//#CM698679
				// Check whether the data storage folder is suffixed with "\". If not suffixed, suffix it and submit it.		
				int len = param[0].getFolder_ReportNoPlanStorageData().length();
				if (!param[0]
					.getFolder_ReportNoPlanStorageData()
					.substring(len - 1, len)
					.equals(wYenmark))
				{
					wFolder = param[0].getFolder_ReportNoPlanStorageData() + "\\";
				}
				else
				{
					wFolder = param[0].getFolder_ReportNoPlanStorageData();
				}

				IO.set(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_NOPLANSTRAGESUPPORT], wFolder);
				IO.set(
					REPORTDATA_FILENAME,
					LOADTYPE_KEY[REPORTTYPE_NOPLANSTRAGESUPPORT],
					param[0].getFileName_ReportNoPlanStorageData());
			}

			//#CM698680
			// Unplanned Picking Result
			if (param[0].getSelectReportNoPlanRetrievalData())
			{
				//#CM698681
				// Check whether the data storage folder is suffixed with "\". If not suffixed, suffix it and submit it.		
				int len = param[0].getFolder_ReportNoPlanRetrievalData().length();
				if (!param[0]
					.getFolder_ReportNoPlanRetrievalData()
					.substring(len - 1, len)
					.equals(wYenmark))
				{
					wFolder = param[0].getFolder_ReportNoPlanRetrievalData() + "\\";
				}
				else
				{
					wFolder = param[0].getFolder_ReportNoPlanRetrievalData();
				}

				IO.set(REPORTDATA_FOLDER, LOADTYPE_KEY[REPORTTYPE_NOPLANRETRIEVALSUPPORT], wFolder);
				IO.set(
					REPORTDATA_FILENAME,
					LOADTYPE_KEY[REPORTTYPE_NOPLANRETRIEVALSUPPORT],
					param[0].getFileName_ReportNoPlanRetrievalData());
			}

			IO.flush();
			wMessage = "6001018";
			resultFlag = true;
		}
		catch (IOException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		return resultFlag;
	}

	//#CM698682
	// Package methods -----------------------------------------------

	//#CM698683
	// Protected methods ---------------------------------------------

	//#CM698684
	// Private methods -----------------------------------------------
	//#CM698685
	/**
	 * Examine whether the designated folder is Enabled or not.
	 *
	 * @param		cheDirName	Process identifier
	 * @return		boolean		Return true if Enabled.
	 * @exception	IOException Announce when failed to process input/output or when interruption occurred.
	 */
	private boolean chkDir(String cheDirName) throws java.io.IOException
	{
		//#CM698686
		// Check the file.
		File objFile = new File(cheDirName);
		return (objFile.exists() && objFile.isDirectory());
	}

	//#CM698687
	/**
	 * Allow this method to support the operation to obtain data from a folder for reporting data.<BR>
	 * 
	 * @param conn        Database connection object
	 * @param searchParam Set null as this method does not use it.
	 * @return A class that implements the <CODE>Parameter</CODE> interface that contains the search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter getDirectory(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{

		SystemParameter param = new SystemParameter();
		try
		{
			//#CM698688
			// Obtain the environment info descriptor.
			IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);

			//#CM698689
			// Obtain the storage folder and the file name from the Environment setting file.
			//#CM698690
			// Receiving plan data
			if (isInstockPack(conn))
			{
				//#CM698691
				// Set the checkbox ON (TRUE).
				param.setSelectLoadInstockData(true);
				//#CM698692
				// Set the folder for storing the data.
				param.setFolder_LoadInstockData(
					IO.get(DATALOAD_FOLDER, LOADTYPE_KEY[REPORTTYPE_INSTOCKRECEIVE]));
				//#CM698693
				// Set a file name.
				param.setFileName_LoadInstockData(
					IO.get(DATALOAD_FILENAME, LOADTYPE_KEY[REPORTTYPE_INSTOCKRECEIVE]));
			}
			else
			{
				//#CM698694
				// Set the checkbox OFF (FALSE).
				param.setSelectLoadInstockData(false);
			}

			//#CM698695
			// Storage plan data
			if (isStoragePack(conn))
			{
				//#CM698696
				// Set the checkbox ON (TRUE).
				param.setSelectLoadStorageData(true);
				//#CM698697
				// Set the folder for storing the data.
				param.setFolder_LoadStorageData(
					IO.get(DATALOAD_FOLDER, LOADTYPE_KEY[REPORTTYPE_STRAGESUPPORT]));
				//#CM698698
				// Set a file name.
				param.setFileName_LoadStorageData(
					IO.get(DATALOAD_FILENAME, LOADTYPE_KEY[REPORTTYPE_STRAGESUPPORT]));
			}
			else
			{
				//#CM698699
				// Set the checkbox OFF (FALSE).
				param.setSelectLoadStorageData(false);
			}

			//#CM698700
			// Picking plan data
			if (isRetrievalPack(conn))
			{
				//#CM698701
				// Set the checkbox ON (TRUE).
				param.setSelectLoadRetrievalData(true);
				//#CM698702
				// Set the folder for storing the data.
				param.setFolder_LoadRetrievalData(
					IO.get(DATALOAD_FOLDER, LOADTYPE_KEY[REPORTTYPE_RETRIEVALSUPPORT]));
				//#CM698703
				// Set a file name.
				param.setFileName_LoadRetrievalData(
					IO.get(DATALOAD_FILENAME, LOADTYPE_KEY[REPORTTYPE_RETRIEVALSUPPORT]));
			}
			else
			{
				//#CM698704
				// Set the checkbox OFF (FALSE).
				param.setSelectLoadRetrievalData(false);
			}

			//#CM698705
			// Sorting Plan data
			if (isSortingPack(conn))
			{
				//#CM698706
				// Set the checkbox ON (TRUE).
				param.setSelectLoadSortingData(true);
				//#CM698707
				// Set the folder for storing the data.
				param.setFolder_LoadSortingData(
					IO.get(DATALOAD_FOLDER, LOADTYPE_KEY[REPORTTYPE_PICKINGSUPPORT]));
				//#CM698708
				// Set a file name.
				param.setFileName_LoadSortingData(
					IO.get(DATALOAD_FILENAME, LOADTYPE_KEY[REPORTTYPE_PICKINGSUPPORT]));
			}
			else
			{
				//#CM698709
				// Set the checkbox OFF (FALSE).
				param.setSelectLoadSortingData(false);
			}

			//#CM698710
			// Shipping Inspection Plan data
			if (isShippingPack(conn))
			{
				//#CM698711
				// Set the checkbox ON (TRUE).
				param.setSelectLoadShippingData(true);
				//#CM698712
				// Set the folder for storing the data.
				param.setFolder_LoadShippingData(
					IO.get(DATALOAD_FOLDER, LOADTYPE_KEY[REPORTTYPE_SHIPPINGINSPECTION]));
				//#CM698713
				// Set a file name.
				param.setFileName_LoadShippingData(
					IO.get(DATALOAD_FILENAME, LOADTYPE_KEY[REPORTTYPE_SHIPPINGINSPECTION]));
			}
			else
			{
				//#CM698714
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
}
//#CM698715
//end of class
