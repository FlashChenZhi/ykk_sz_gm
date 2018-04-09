package jp.co.daifuku.wms.storage.schedule;
/*
 * Created on Sep 27, 2004
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.storage.report.StoragePlanDeleteWriter;

/**
 * 設計者 :   Muneendra <BR>
 * 製作者 :   Muneendra <BR>
 * <BR>
<BR>
 * Display this class based on the search conditions of the Storage Plan info. <BR>
 * Enable to allow users to delete any data from the storage Plan info and refer to the storage Plan info after updated. <BR>
 * Obtain the data needed to use in the operations from the storage Plan info table. <BR>
 * <BR>
 *1.<CODE>Query()</CODE> Method : This method obtains data based on the input search conditions. <BR>
 * <BR>
 * <DIR>
 *    Check for presence of the Worker code and password from the Worker info table. <BR>
 *    Obtain the data based on the input search conditions from storage Plan info table. <BR>
 *    Obtain the search conditions from the argument StorageSupportParameter. <BR>
 *
 *   Enable to refer to the error contents using <CODE>getMessage()</CODE> method.<BR>
 * 	<BR>
 *  	 <Parameter> <Input data> Mandatory Input* <BR>
 * 	<BR>
 * 	   <BR>
 *          Worker Code * : WorkerCode <BR>
 *          Password * : Password <BR>
 *          Consignor code * : ConsignorCode <BR>
 *          Planned start storage Date : FromStoragePlanDate <BR>
 *          Planned end storage Date : ToStoragePlanDate <BR>
 *          Aggregation to be displayed  [Aggregation by Planned storage date/Item Code, and Aggregation by Planned storage date ] * : AggregateDisplay <BR>
 *          Category of generating plan [Extract All/Add via ScreenLink with Receiving] * : RegistKbn <BR>
 *     <BR>
 * 	<BR>
 *  	 <Parameter> <Returned data> <BR>
 * 	<BR>
 * 	   <BR>
 *          Consignor code : ConsignorCode <BR>
 *          Consignor name : ConsignorName <BR>
 *          Last update date/time : LastUpdateDateList <BR>
 * 			Planned storage date : StoragePlanDate <BR>
 *          Category of generating plan : RegistKbn <BR>
 * 			Item Code : ItemCode <BR>
 *	 		Item Name : ItemName <BR>
 *     <BR>
 * </DIR>
 *
 * <BR>
 * 2.<CODE>startSCHgetParams()</CODE>Method :  Invoke this method  from Business clas to delete the selected Storage Plan infos.<BR>
 * <BR>
 * <DIR>
 *      Select a Storage Plan data to be deleted from the preset area. <BR>
 *      Update the Status_Flag of Worker info, Storage Plan info, and inventory information to Deleted status. <BR>
 *      <BR>
 *		    1. Obtain the Storage Plan unique key using parameter from the Storage Plan info. <BR>
 *          2. Obtain the Stock ID and Work status unique key from Work status using the Storage Plan info unique key. (Build a relation between Worker info and Storage Plan info to be 1 : 1) <BR>
 *	        3. Update Status_flag of Work status to Deleted using Work status unique key. <BR>
 *			4. Using the Storage Plan info unique key, update the Status_flag of the Storage Plan info to Delete. <BR>
 *          5. Update Stock ID is used to update Status_flag of inventory information to Deleted. <BR>
 *
 *   Enable to refer to the error contents using <CODE>getMessage()</CODE> method.<BR>
 *
 * <BR>
 *   <Parameter> <Input data> Mandatory Input* <BR>
 * <BR>
 *     <BR>
 *      Worker Code * : WorkerCode <BR>
 *      Password * : Password <BR>
 *      Consignor code * : ConsignorCode <BR>
 *      Item Code : ItemCode <BR>
 *      Planned storage date * : StoragePlanDate <BR>
 *      Aggregation to be displayed  [Aggregation by Planned storage date/Item Code, and Aggregation by Planned storage date ] * : AggregateDisplay <BR>
 *      Category of generating plan [Extract All/Add via ScreenLink with Receiving] * : RegistKbn <BR>
 *      List Print Type * : StorageListFlg <BR>
 *      Last update date/time * : LastUpdateDateList <BR>
 *      Planned start storage Date(Repeat to search conditions) : FromStoragePlanDate <BR>
 *      Planned end storage Date(Repeat to search conditions) : ToStoragePlanDate <BR>
 *     <BR>
 *
 *  <Parameter> <Returned data> <BR>
 * <BR>
 *      Consignor code : ConsignorCode <BR>
 *      Consignor name : ConsignorName <BR>
 *      Last update date/time : LastUpdateDateList <BR>
 * 		Planned storage date : StoragePlanDate <BR>
 *      Category of generating plan : RegistKbn <BR>
 * 		Item Code : ItemCode <BR>
 *		Item Name : ItemName <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/27</TD><TD>Muneendra Y</TD><TD>Create New</TD></TR>
 * <TR><TD>2005/05/18</TD><TD>T.Nakajima</TD><TD>This was solved by adding the Add division "2: Linked to Receiving".</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:23:10 $
 * @author  $Author: suresh $
 */
public class StoragePlanDeleteSCH extends AbstractStorageSCH
{
	//	 Class variables ----------------------------------------------
	/**
	 * Class Name(Delete Storage Plan)
	 */
	public static String wProcessName = "StoragePlanDeleteSCH";

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2006/11/21 04:23:10 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public StoragePlanDeleteSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * If only one Consignor Code found in the Storage Plan info, return the corresponding Consignor Code.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Connection Database connection object
	 * @param searchParam Parameter This Class inherits the <CODE>Parameter</CODE> class with search conditions
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		StoragePlanSearchKey searchKey = new StoragePlanSearchKey();

		searchKey.KeyClear();
		// Status flag: Standby
		searchKey.setStatusFlag(StoragePlan.STATUS_FLAG_UNSTART);
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect();
		StoragePlanHandler storageHandle = new StoragePlanHandler(conn);

		StorageSupportParameter parameter = null;

		if (storageHandle.count(searchKey) == 1)
		{
			StoragePlan stock[] = (StoragePlan[]) storageHandle.find(searchKey);

			// If the search result count is one:
			if (stock.length == 1)
			{
				parameter = new StorageSupportParameter();
				parameter.setConsignorCode(stock[0].getConsignorCode());
				parameter.setConsignorName(stock[0].getConsignorName());
			}
		}
		return parameter;

	}

	/**
	 * Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param conn Instance to maintain database connection.
	 * @param searchParam Instance of <CODE>StorageSupportParameter</CODE> class with conditions to obtain the display data.<BR>
	 *         Designating any instance other than <CODE>StorageSupportParameter</CODE> throws ScheduleException.<BR>
	 * @return Array of <CODE>StorageSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record found, return the array of the number of elements equal to 0.<BR>
	 *          Return null if the search result count exceeds 1000 or when input condition error occurs.<BR>
	 *          Returning array with element qty 0 (zero) or null allows to obtain the error contents as a message using <CODE>getMessage()</CODE> method.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter[] query(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		StorageSupportParameter param = (StorageSupportParameter) searchParam;

		// Check the Worker code and password
		if (!checkWorker(conn, param))
		{
			return null;
		}

		StoragePlanSearchKey storageSearchKey = new StoragePlanSearchKey();
		StoragePlanSearchKey wKey = new StoragePlanSearchKey();

		// Consignor code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			storageSearchKey.setConsignorCode(param.getConsignorCode());
			wKey.setConsignorCode(param.getConsignorCode());
		}
		// Planned start storage Date
		if (!StringUtil.isBlank(param.getFromStoragePlanDate()))
		{
			storageSearchKey.setPlanDate(param.getFromStoragePlanDate(), ">=");
			wKey.setPlanDate(param.getFromStoragePlanDate(), ">=");
		}
		// Planned end storage Date
		if (!StringUtil.isBlank(param.getToStoragePlanDate()))
		{
			storageSearchKey.setPlanDate(param.getToStoragePlanDate(), "<=");
			wKey.setPlanDate(param.getToStoragePlanDate(), "<=");
		}

        // search conditions
		wKey.setStatusFlag(StoragePlan.STATUS_FLAG_UNSTART);
		storageSearchKey.setStatusFlag(StoragePlan.STATUS_FLAG_UNSTART);

        // Item (count) to be obtained
		storageSearchKey.setConsignorCodeCollect("");
		storageSearchKey.setPlanDateCollect("");
		storageSearchKey.setLastUpdateDateCollect("");
		storageSearchKey.setRegistKindCollect("");

		// Set Aggregation by Planned Storage Date
		storageSearchKey.setConsignorCodeGroup(1);
		storageSearchKey.setRegistKindGroup(2);
		storageSearchKey.setPlanDateGroup(3);
		storageSearchKey.setLastUpdateDateGroup(4);

        // Set Sort Order.
		storageSearchKey.setPlanDateOrder(1, true);
		storageSearchKey.setRegistKindOrder(2, true);

		// Determine Aggregation
		boolean aggreFlag = false;

        // Set Aggregation by Planned storage date / Item Code
		if (StorageSupportParameter
			.AGGREGATEDISPLAY_ITEM_AND_LOCATION
			.equals(param.getAggregateDisplay()))
		{
			// Item (count) to be obtained
			storageSearchKey.setItemCodeCollect("");
			storageSearchKey.setItemName1Collect("");
			// Set Aggregation by Planned storage date / Item Code
			storageSearchKey.setItemCodeGroup(5);
			storageSearchKey.setItemName1Group(6);
			// Set Sort Order.
			storageSearchKey.setItemCodeOrder(3, true);

			aggreFlag = true;
		}

		if (StorageSupportParameter
			.REGIST_KIND_NOT_INSTOCK
			.equals(param.getRegistKbn()))
		{
			// Extract/Add via Screen
			storageSearchKey.setRegistKind(SystemDefine.REGIST_KIND_INSTOCK,"!=");
		}
		else if (SystemDefine
			.REGIST_KIND_INSTOCK
			.equals(param.getRegistKbn()))
		{
			// Link with Receiving
			storageSearchKey.setRegistKind(SystemDefine.REGIST_KIND_INSTOCK, "=");
		}

		StoragePlanHandler handler = new StoragePlanHandler(conn);
		if (!canLowerDisplay(handler.count(storageSearchKey)))
		{
			return returnNoDisplayParameter();
		}

		StoragePlan[] storageplan = (StoragePlan[]) handler.find(storageSearchKey);

		/* Compile and aggregate the last update date/time based on the search result. Display the data based on the aggregation condition entered via screen.
		 * Allow to maintain the array of the last update date/time linked with a single record in the preset area as a hidden item (count).
		 * Execute aggregation based on the condition, looping the search result.
		 * Planning division is also one of aggregation conditions.
		 */

		// Use this to determine the aggregation by Planned Storage Date, Item Code, or Planning division.
		String tempPlanDate = "";
		String tempItemCode = "";
		String tempRegistKind = "";
		String registKind = "";

		// Maintain the last update date/time based on the aggregation condition in the array.
		Vector lastModifiedDate = new Vector();

		// Store the aggregation result.
		// Finally Copy to the StorageSupportParameter.
		Vector vectorList = new Vector();

		// Obtain the Consignor Name.
		wKey.setRegistDateOrder(1, false);
		StoragePlanReportFinder finder = new StoragePlanReportFinder(conn);
		StoragePlan[] storage = null;
		String consignorName = "";
		if (finder.search(wKey) > 0)
		{
			storage = (StoragePlan[]) finder.getEntities(1);
			consignorName = storage[0].getConsignorName();
		}

		int i = 0;
		for (; i < storageplan.length; ++i)
		{
			if (i == 0)
			{
				// Planned storage date, Item Code, and Planning division of the leading data
				tempPlanDate = storageplan[i].getPlanDate();
				tempItemCode = storageplan[i].getItemCode();
				if (SystemDefine.REGIST_KIND_HOST.equals(storageplan[i].getRegistKind()) ||
					SystemDefine.REGIST_KIND_WMS.equals(storageplan[i].getRegistKind()))
				{
					tempRegistKind = StorageSupportParameter.REGIST_KIND_NOT_INSTOCK;
				}
				else
				{
					tempRegistKind = SystemDefine.REGIST_KIND_INSTOCK;
				}
			}

			StorageSupportParameter tempParam = new StorageSupportParameter();
			// Set the Consignor Name.
			tempParam.setConsignorName(consignorName);
			// Set the Add division.
			if (SystemDefine.REGIST_KIND_HOST.equals(storageplan[i].getRegistKind()) ||
				SystemDefine.REGIST_KIND_WMS.equals(storageplan[i].getRegistKind()))
			{
				registKind = StorageSupportParameter.REGIST_KIND_NOT_INSTOCK;
			}
			else
			{
				registKind = SystemDefine.REGIST_KIND_INSTOCK;
			}
			// To set Aggregation by Planned Storage Date / Item Code:
			if (aggreFlag)
			{
				// Determine for the consistency of the Planned storage date, Item Code, and submitted/added division with the last data.
				if (!tempPlanDate.equals(storageplan[i].getPlanDate())
					|| !tempItemCode.equals(storageplan[i].getItemCode())
					|| !tempRegistKind.equals(registKind)
					)
				{
					// Disable to display any data with status other than Standby or Delete.
					wKey.KeyClear();
					// Consignor code
					wKey.setConsignorCode(storageplan[i - 1].getConsignorCode());
					// Planned storage date
					wKey.setPlanDate(storageplan[i - 1].getPlanDate());
					// Item Code
					wKey.setItemCode(storageplan[i - 1].getItemCode());
					// Category of generating plan
					wKey.setRegistKind(storageplan[i - 1].getRegistKind());
					// Status other than Standby
					wKey.setStatusFlag(StoragePlan.STATUS_FLAG_UNSTART, "!=");
					// Status other than Deleted
					wKey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");
					// Obtain Count
					int cnt = handler.count(wKey);
					if (cnt == 0)
					{
						// Obtain the contents to be displayed in the preset area.
						// Planned storage date
						tempParam.setStoragePlanDate(storageplan[i - 1].getPlanDate());
						// Item Code
						tempParam.setItemCode(storageplan[i - 1].getItemCode());
						// Item Name
						tempParam.setItemName(storageplan[i - 1].getItemName1());
						// Category of generating plan
						tempParam.setRegistKbn(storageplan[i - 1].getRegistKind());

						Vector tempModiDate = new Vector();

						// Obtain the item (count) hidden in the preset area.
						// The last update date/time (Set all the last update date/time linked with the aggregation condition)
						tempModiDate = (Vector)lastModifiedDate.clone();
						tempParam.setLastUpdateDateList(tempModiDate);

						lastModifiedDate.clear();

						// Store the preset area info.
						vectorList.addElement(tempParam);

						// Swap it for determining for aggregation.
						// Planned storage date
						tempPlanDate = storageplan[i].getPlanDate();
						// Item Code
						tempItemCode = storageplan[i].getItemCode();

					    if (SystemDefine.REGIST_KIND_HOST.equals(storageplan[i].getRegistKind()) ||
						    SystemDefine.REGIST_KIND_WMS.equals(storageplan[i].getRegistKind()))
					    {
					 	    tempRegistKind = StorageSupportParameter.REGIST_KIND_NOT_INSTOCK;
					    }
					    else
					    {
						    tempRegistKind = SystemDefine.REGIST_KIND_INSTOCK;
					    }
					}
				}

			}
			// To set Aggregate Planned Storage Date:
			else
			{
				// Determine the consistency of the Planned storage date and the Add division with the last data.
				if (!tempPlanDate.equals(storageplan[i].getPlanDate())
					|| !tempRegistKind.equals(registKind))
				{
					// Disable to display any data with status other than Standby or Delete.
					wKey.KeyClear();
					// Consignor code
					wKey.setConsignorCode(storageplan[i - 1].getConsignorCode());
					// Planned storage date
					wKey.setPlanDate(storageplan[i - 1].getPlanDate());
					// Category of generating plan
   					wKey.setRegistKind(storageplan[i - 1].getRegistKind());
					// Status other than Standby
					wKey.setStatusFlag(StoragePlan.STATUS_FLAG_UNSTART, "!=");
					// Status other than Deleted
					wKey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");
					// Obtain Count
					if (handler.count(wKey) == 0)
					{
						// Obtain the contents to be displayed in the preset area.
						// Aggregating by Planned Storage Date here disables to display Item Code and Item Name.
						// Planned storage date
						tempParam.setStoragePlanDate(storageplan[i - 1].getPlanDate());

						// Category of generating plan
						tempParam.setRegistKbn(storageplan[i - 1].getRegistKind());

						// Obtain the item (count) hidden in the preset area.
						// The last update date/time (Set all the last update date/time linked with the aggregation condition)
						Vector tempModiDate = new Vector();
						tempModiDate = (Vector)lastModifiedDate.clone();
						tempParam.setLastUpdateDateList(tempModiDate);

						lastModifiedDate.clear();

						// Store the preset area info.
						vectorList.addElement(tempParam);

						// Swap it for determining for aggregation.
						// Planned storage date
						tempPlanDate = storageplan[i].getPlanDate();

						// Add division
						if (SystemDefine.REGIST_KIND_HOST.equals(storageplan[i].getRegistKind()) ||
							SystemDefine.REGIST_KIND_WMS.equals(storageplan[i].getRegistKind()))
						{
							tempRegistKind = StorageSupportParameter.REGIST_KIND_NOT_INSTOCK;
						}
						else
						{
							tempRegistKind = SystemDefine.REGIST_KIND_INSTOCK;
						}
					}
				}
			}
			// Disable to display any data with status other than Standby or Delete.
			wKey.KeyClear();
			// Consignor code
			wKey.setConsignorCode(storageplan[i].getConsignorCode());
			// Planned storage date
			wKey.setPlanDate(storageplan[i].getPlanDate());
			// Status other than Standby
			wKey.setStatusFlag(StoragePlan.STATUS_FLAG_UNSTART, "!=");
			// Status other than Deleted
			wKey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");
			// To set Aggregation by Planned Storage Date / Item Code:
			if (aggreFlag)
			{
				// Item Code
				wKey.setItemCode(storageplan[i].getItemCode());
			}
			// Obtain Count
			if (handler.count(wKey) > 0)
			{
				continue;
			}
			// Store the last update date/time linked with search conditions until satisfying the aggregation condition.
			lastModifiedDate.addElement(storageplan[i].getLastUpdateDate());
		}
		// Execute the process for the last one data.
		StorageSupportParameter tempParam = new StorageSupportParameter();
		// Disable to display any data with status other than Standby or Delete.
		wKey.KeyClear();
		// Consignor code
		wKey.setConsignorCode(storageplan[i - 1].getConsignorCode());
		// Planned storage date
		wKey.setPlanDate(storageplan[i - 1].getPlanDate());

  		// To set Aggregation by Planned Storage Date / Item Code:
		if (aggreFlag)
		{
			// Item Code
			wKey.setItemCode(storageplan[i - 1].getItemCode());
		}
		// Status other than Standby
		wKey.setStatusFlag(StoragePlan.STATUS_FLAG_UNSTART, "!=");
		// Item Code
		wKey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");
		// Obtain Count
		if (handler.count(wKey) == 0)
		{
			// Consignor name
			tempParam.setConsignorName(consignorName);
			// Planned storage date
			tempParam.setStoragePlanDate(storageplan[i - 1].getPlanDate());
			// Category of generating plan
		 	tempParam.setRegistKbn(storageplan[i - 1].getRegistKind());
   			// To set Aggregation by Planned Storage Date / Item Code:
			if (aggreFlag)
			{
				// Item Code
				tempParam.setItemCode(storageplan[i - 1].getItemCode());
				// Item Name
				tempParam.setItemName(storageplan[i - 1].getItemName1());
			}
			// Last update date/time
			tempParam.setLastUpdateDateList(lastModifiedDate);
			vectorList.addElement(tempParam);
		}

		if (vectorList.size() > 0)
		{
			// Copy to the StorageSupportParameter.
			StorageSupportParameter[] storageSupport = new StorageSupportParameter[vectorList.size()];
			vectorList.copyInto(storageSupport);

			// 6001013 = Data is shown.
			wMessage = "6001013";

			return storageSupport;
		}
		else
		{
			// 6023373 = Status flag other than Standby exists. Cannot display.
			wMessage = "6023373";
			return new StorageSupportParameter[0];
		}

	}

	/**
	 * Receive the contents entered via screen as a parameter and start the schedule for deleting the Storage Plan data.<BR>
	 * Assume that two or more data may be input via preset area or others. So, require the parameter to receive them in the form of array.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * When process normally completed, obtain the data to be output in the preset area from database again using a condition entered in the initial display, and return it. <BR>
	 * Return null when the schedule failed to complete due to condition error or other causes.
	 * @param conn Instance to maintain database connection.
	 * @param startParams Array of <CODE>StorageParameter</CODE> classinstance with contents of commitment. <BR>
	 *         Designating any instance other than StorageSupportParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *         Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		StorageSupportParameter sparam[] = (StorageSupportParameter[]) startParams;
		// Check the Worker code and password
		if (!checkWorker(conn, sparam[0]))
		{
			return null;
		}
		// Check the Daily Update Processing.
		if (isDailyUpdate(conn))
		{
			return null;
		}
		// Check Extraction Processing
		if (isLoadingData(conn))
		{
			return null;
		}

		// Define HANDLER of Work status definition
		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
		WorkingInformationAlterKey workInfoAlterKey = new WorkingInformationAlterKey();

		// Define HANDLER for Storage Plan information
		StoragePlanHandler storageHandler = new StoragePlanHandler(conn);
		StoragePlanSearchKey storageSearchKey = new StoragePlanSearchKey();
		StoragePlanAlterKey storageAlterKey = new StoragePlanAlterKey();

		// Define HANDLER for inventory information HANDLER
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey stockSearchKey = new StockSearchKey();
		StockAlterKey stockAlterKey = new StockAlterKey();

		// For printing ticket
		Date writerDate = new Date();

		// For deciding DB commit and rollback
		boolean deleteFlag = false;
		// This flag determines whether "Processing Extraction" flag is updated in its own class.
		boolean updateLoadDataFlag = false;

		// Initialize Process Counter
		int deleteConuter = 0;

		try
		{
			// Update the extraction flag: "Processing Extract"
			if (!updateLoadDataFlag(conn, true))
			{
				return null;
			}
			doCommit(conn, wProcessName);
			updateLoadDataFlag = true;

			// Count of records that have been selected
			for (int i = 0; i < sparam.length; ++i)
			{
				// Lock the Standby data.
				// Set Search Condition
				storageSearchKey.KeyClear();
				setCommonSearchCondition(sparam[i], storageSearchKey);
				// Standby
				storageSearchKey.setStatusFlag(StoragePlan.STATUS_FLAG_UNSTART, "=");
				// Search
				StoragePlan[] storagePlan = (StoragePlan[]) storageHandler.findForUpdate(storageSearchKey);

				// Check for presence of data with status other than Standby or Delete in the same condition.
				// Set Search Condition
				storageSearchKey.KeyClear();
				setCommonSearchCondition(sparam[i], storageSearchKey);
				// other than Standby or Delete
				storageSearchKey.setStatusFlag(StoragePlan.STATUS_FLAG_UNSTART, "!=");
				storageSearchKey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");
				// Search
				int count = storageHandler.count(storageSearchKey);
				if (count > 0)
				{
					// No.{0} Unable to process this data. It has been updated via other work station.
					wMessage = "6023209" + wDelim + sparam[i].getRowNo();
					return null;
				}

				// Check for exclusion and execute the process for deleting.
				// Search for the Work status linked with the obtained Storage Plan info.
				for (int j = 0; j < storagePlan.length; ++j)
				{
					workInfoSearchKey.KeyClear();
					// Unique key of the Storage Plan obtained from Storage Plan information.
					// Build a relation between Work status and Storage Plan info to 1: 1.
					workInfoSearchKey.setPlanUkey(storagePlan[j].getStoragePlanUkey());
					// Search
					WorkingInformation[] workingInfo =
						(WorkingInformation[]) workInfoHandler.findForUpdate(workInfoSearchKey);
					// Only when target info exists in Work status, process.
					if (workingInfo != null && workingInfo.length > 0)
					{
						try
						{
							// Update the Work Status status flag to Delete.
							workInfoAlterKey.KeyClear();
							// Storage Plan unique key
							workInfoAlterKey.setPlanUkey(storagePlan[j].getStoragePlanUkey());
							// Update the status flag to Delete.
							workInfoAlterKey.updateStatusFlag(
								WorkingInformation.STATUS_FLAG_DELETE);
							// Update the last Update process name.
							workInfoAlterKey.updateLastUpdatePname(wProcessName);

							// Update
							workInfoHandler.modify(workInfoAlterKey);


						}
						catch (InvalidDefineException ei)
						{
							throw (
								new ReadWriteException(
									"6006039" + wDelim + "DNWORKINGINFORMATION"));
						}
						catch (NotFoundException en)
						{
							throw (
								new ReadWriteException(
									"6006039" + wDelim + "DNWORKINGINFORMATION"));
						}

					}

					// Update the Status flag of Storage Plan information to Deleted status.
					storageAlterKey.KeyClear();
					// Target the information that corresponds to the Storage Plan information unique key.
					storageAlterKey.setStoragePlanUkey(storagePlan[j].getStoragePlanUkey());
					// Update the status flag to Delete.
					storageAlterKey.updateStatusFlag(StoragePlan.STATUS_FLAG_DELETE);

					// Update the last Update process name.
					storageAlterKey.updateLastUpdatePname(wProcessName);

					try
					{
						// Update
						storageHandler.modify(storageAlterKey);
						// Count the number of processed data.
						deleteConuter++;
					}
					catch (NotFoundException en)
					{
						throw (new ReadWriteException("6006039" + wDelim + "DNSTORAGEPLAN"));
					}
					// Update the Status_flag of inventory information to Deleted.
					// Build a relation between Work status and inventory information is 1 : 1.
					// Enable relationship to Stock ID.
					for (int k = 0; k < workingInfo.length; ++k)
					{

						try
						{
							stockSearchKey.KeyClear();
							// Obtain the Info with the corresponding Stock ID.
							stockSearchKey.setStockId(workingInfo[k].getStockId());

							// Obtain the target info by designating line lock.
							stockHandler.findPrimaryForUpdate(stockSearchKey);

							stockAlterKey.KeyClear();
							// Target the Info with the corresponding Stock ID.
							stockAlterKey.setStockId(workingInfo[k].getStockId());
							// Update of status to Completed.
							stockAlterKey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
							// Update the last Update process name.
							stockAlterKey.updateLastUpdatePname(wProcessName);

							// Update
							stockHandler.modify(stockAlterKey);
						}
						catch (NoPrimaryException ep)
						{
							throw (new ReadWriteException("6006039" + wDelim + "DMSTOCK"));
						}
						catch (InvalidDefineException ei)
						{
							throw (new ReadWriteException("6006039" + wDelim + "DMSTOCK"));
						}
						catch (NotFoundException en)
						{
							throw (new ReadWriteException("6006039" + wDelim + "DMSTOCK"));
						}
					} // Stock info with respect to single work info
				} // Work info that refers to single plan
			} // Selected record
			//
			sparam[0].setRegistKbn(sparam[0].getRegistKbnCondition());

			StorageSupportParameter[] viewParam =
				(StorageSupportParameter[])this.query(conn, sparam[0]);

			// Determine Print List
			if (deleteConuter != 0)
			{

				if (sparam[0].getStorageListFlg())
				{
					// Instance of a class for generating a ticket
					StoragePlanDeleteWriter deleteWriter = new StoragePlanDeleteWriter(conn);
					String[] planDate = new String[sparam.length];
					String[] itemCode = new String[sparam.length];

					for (int i = 0; i < sparam.length; i++)
					{
						// Aggregate Planned storage date / Item Code
						if (StorageSupportParameter.AGGREGATEDISPLAY_ITEM_AND_LOCATION
							.equals(sparam[0].getAggregateDisplay()))
						{
							planDate[i] = sparam[i].getStoragePlanDate();
							itemCode[i] = sparam[i].getItemCode();
						}
						else
						{
							planDate[i] = sparam[i].getStoragePlanDate();
						}
					}

					deleteWriter.setConsignorCode(sparam[0].getConsignorCode());
					deleteWriter.setPlanDate(planDate);
					deleteWriter.setItemCode(itemCode);
					deleteWriter.setLastUpdateDate(writerDate);

					if (deleteWriter.startPrint())
					{
						// Printing has been normally completed after deleted.
						wMessage = "6021013";
					}
					else
					{
						// Failed to print it after deleted. See log.
						wMessage = "6007043";
					}
				}
				else
				{
					// 6001005 = Deleted.
					wMessage = "6001005";
				}
			}
			else
			{
				// No delete target data found.
				wMessage = "6003014";
			}
			deleteFlag = true;
			return viewParam;

		}
		catch (InvalidDefineException e)
		{
			doRollBack(conn, wProcessName);
			throw new ReadWriteException(e.getMessage());
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
			// If failed to delete, Roll-back the transaction.
			if (!deleteFlag)
			{
				doRollBack(conn, wProcessName);
			}

			// If "Processing Extraction" flag was updated in its own class,
			// change the Processing Extract flag to 0: Stopping.
			if( updateLoadDataFlag )
			{
				updateLoadDataFlag(conn, false);
				doCommit(conn, wProcessName);
			}

		}

	}

	/**
	 * Set search conditions of Storage Plan info that is used commonly in the <CODE>StoragePlanDeleteSCH</CODE> class
	 * for searchKey.
	 *
	 * @param	param               Parameter input from teh screen
	 * @param	storageSearchKey    Key object search conditions to be set.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private void setCommonSearchCondition(
		StorageSupportParameter param,
		StoragePlanSearchKey storageSearchKey)
		throws ReadWriteException
	{
		// Planned storage date
		storageSearchKey.setPlanDate(param.getStoragePlanDate());
		// Consignor code
		storageSearchKey.setConsignorCode(param.getConsignorCode());
		// Item Code
		if (StorageSupportParameter.AGGREGATEDISPLAY_ITEM_AND_LOCATION.equals(param.getAggregateDisplay()))
		{
			storageSearchKey.setItemCode(param.getItemCode());
		}
		if (SystemDefine.REGIST_KIND_INSTOCK.equals(param.getRegistKbn()))
		{
			// Link with Receiving
			storageSearchKey.setRegistKind(SystemDefine.REGIST_KIND_INSTOCK);
		}
		else
		{
			//other than Linked to Receiving
			storageSearchKey.setRegistKind(SystemDefine.REGIST_KIND_INSTOCK, "!=");
		}
	}

}
